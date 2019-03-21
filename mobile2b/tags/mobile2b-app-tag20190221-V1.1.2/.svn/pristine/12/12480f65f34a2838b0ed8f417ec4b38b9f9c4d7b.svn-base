package com.nork.pay.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.system.service.SysResLevelCfgService;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.WeixinUtils;
import com.nork.mgr.dao.MgrRechargeLogMapper;
import com.nork.mgr.model.MgrRechargeLog;
import com.nork.mgr.service.MgrRechargeLogService;
import com.nork.pay.alipay.ScanPayUtil;
import com.nork.pay.alipay.util.AlipayCore;
import com.nork.pay.alipay.util.AlipayNotify;
import com.nork.pay.common.Config;
import com.nork.pay.common.IdGenerator;
import com.nork.pay.common.QrCodeUtil;
import com.nork.pay.metadata.BizType;
import com.nork.pay.metadata.FinanceType;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.PayType;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.metadata.ScanPayReqData;
import com.nork.pay.metadata.TradeType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.ResultMessage;
import com.nork.pay.model.search.PayOrderSearch;
import com.nork.pay.model.small.PayOrderSmall;
import com.nork.pay.service.PayOrderService;
import com.nork.pay.wexin.common.Util;
import com.nork.pay.wexin.common.XMLParser;
import com.nork.pay.wexin.metadata.WxTradeType;
import com.nork.pay.wexin.protocol.AppPayReqData;
import com.nork.pay.wexin.protocol.ResultNotify;
import com.nork.pay.wexin.protocol.UnifiedOrderReqData;
import com.nork.pay.wexin.protocol.UnifiedOrderResData;
import com.nork.pay.wexin.service.UnifiedOrderService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.service.RenderTaskService;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.task.model.SysTask;
import com.nork.task.service.SysTaskService;

/**
 * @Title: PayOrderController.java
 * @Package com.nork.pay.controller
 * @Description:支付-支出凭证Controller
 * @createAuthor pandajun
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/pay")
public class PayOrderController {
	private static Logger logger = Logger.getLogger(PayOrderController.class);
	private final JsonDataServiceImpl<PayOrder> JsonUtil = new JsonDataServiceImpl<PayOrder>();
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "jsp";
	private final String MAIN = "/" + STYLE + "/pay/payOrder";
	private final String BASEMAIN = "/" + STYLE + "/pay/base/payOrder";
	private final String JSPMAIN = "/" + JSPSTYLE + "/pay/payOrder";

	/** 支付开关 **/
	private static final Boolean PAYABLE = true;

	/**未支付订单最大允许数*/
	private static final Integer PAYINGORDERMAX = 10;
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private SysTaskService sysTaskService;
	
	@Autowired
	private AuthorizedConfigService authorizedConfigService;

	@Autowired
	private RenderTaskService renderTaskService;

	@Autowired
	private SysResLevelCfgService sysResLevelCfgService;
	@Autowired
	private MgrRechargeLogService mgrRechargeLogService;
	@Autowired
	private MgrRechargeLogMapper mgrRechargeLogMapper;
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 支出凭证 基础主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 * 支出凭证 主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return MAIN;
	}

	/**
	 * 访问主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN + "/list";
	}

	private PayOrder getOrder(int totalFee, String payType, String productName, String productDesc, String tradeType) {
		PayOrder payOrder = new PayOrder();
		payOrder.setOrderDate(new Date());
		payOrder.setPayState(PayState.NOTPAY);
		payOrder.setOrderNo(IdGenerator.generateNo());
		payOrder.setTotalFee(totalFee);
		payOrder.setPayType(payType);
		payOrder.setProductDesc(productDesc);
		payOrder.setProductName(productName);
		payOrder.setTradeType(tradeType);
		return payOrder;
	}

	private PayOrder getOrder(int totalFee, String payType, int productId, String productType, String productName,
			String productDesc, String tradeType) {
		PayOrder payOrder = new PayOrder();
		payOrder.setOrderDate(new Date());
		payOrder.setPayState(PayState.NOTPAY);
		payOrder.setOrderNo(IdGenerator.generateNo());
		payOrder.setTotalFee(totalFee);
		payOrder.setPayType(payType);
		payOrder.setProductId(productId);
		payOrder.setProductType(productType);
		if(productType.equals(ProductType.COMMON_RENDER)){
			payOrder.setProductName(ProductType.COMMON_RENDER_NAME);
		}else if(productType.equals(ProductType.HD_RENDER)){
			payOrder.setProductName(ProductType.HD_RENDER_NAME);
		}else if(productType.equals(ProductType.UHD_RENDER)){
			payOrder.setProductName(ProductType.UHD_RENDER_NAME);
		}else if(productType.equals(ProductType.AUTHCODE_PURCHASE)){
			payOrder.setProductName(ProductType.AUTHCODE_PURCHASE_NAME);
		}else if(productType.equals(ProductType.AUTHCODE_RENEW)){
			payOrder.setProductName(ProductType.AUTHCODE_RENEW_NAME);
		}else if(productType.equals(ProductType.AUTHCODE_DREDGE)){
			payOrder.setProductName(ProductType.AUTHCODE_DREDGE_NAME);
		}else if(productType.equals(ProductType.PANORAMA_RENDER)){
			payOrder.setProductName(ProductType.PANORAMA_RENDER_NAME);
		}else if(productType.equals(ProductType.ROAM_VIDEO_RENDER)){//漫游视频
			payOrder.setProductName(ProductType.ROAM_VIDEO_RENDER_NAME);
		}else if(productType.equals(ProductType.ROAM_PANORAMA_RENDER)) {//720多点
			payOrder.setProductName(ProductType.ROAM_PANORAMA_RENDER_NAME);
		}
		payOrder.setProductDesc(productDesc);
		payOrder.setTradeType(tradeType);
		return payOrder;
		
	}

	/**
	 * 下支付订单(APP发起支付)
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "totalFee", required = true, defaultValue = "0") double totalFee,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			@RequestParam(value = "payType", required = true) String payType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = new ResultMessage();
		message.setMsgId(msgId);
		if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
				|| Utils.isBlank(productType)) {
			message.setMessage("传参异常.");
			return message;
		}
		if (totalFee == 0) {
			message.setMessage("支付金额不能为0.");
			return message;
		}
		String tradeType = TradeType.APP;
		PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
		try {
			sysSave(payOrder, request);
			payOrder.setBizType(BizType.BUY);
			payOrder.setFinanceType(FinanceType.OUT);
			int id = payOrderService.add(payOrder);
			if (id == 0) {
				message.setMessage("保存支付单失败.");
				return message;
			}
			payOrder.setId(id);
			// 当支付方式为微信支付，走下面的业务分支
			if (payType.equalsIgnoreCase(PayType.WXPAY)) {
				String flag = "SUCCESS";
				UnifiedOrderService orderService = new UnifiedOrderService();
				UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(payOrder.getProductName(),
						payOrder.getOrderNo(), payOrder.getTotalFee());
				UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
				if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
						&& unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
					String prepayId = unifiedOrderResData.getPrepay_id();
					payOrder.setPrepayId(prepayId);
					payOrder.setPayState(PayState.PAYING);
					payOrderService.update(payOrder);
					AppPayReqData reqData = new AppPayReqData(prepayId);
					message.setMessage("成功生成订单");
					message.setObj(reqData);
					message.setSuccess(true);
				}
			}
			if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
				String payInfo = AlipayCore.getOrder(payOrder.getOrderNo(), payOrder.getProductName(),
						payOrder.getProductDesc(), payOrder.getTotalFee());
				if (Utils.isBlank(payInfo)) {
					message.setMessage("订单数据错误.");
				} else {
					message.setMessage("成功生成订单数据");
					message.setObj(payInfo);
					message.setSuccess(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("下单异常.");
		}
		return message;
	}

	/**
	 * 下支付订单(APP发起支付)
	 */
	@RequestMapping(value = "/addrecharge")
	@ResponseBody
	public Object addRecharge(@RequestParam(value = "totalFee", required = true, defaultValue = "0") Integer totalFee,
			@RequestParam(value = "adjustFee", required = false, defaultValue = "0") Integer adjustFee,
			@RequestParam(value = "payType", required = true) String payType,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = new ResultMessage();
		message.setMsgId(msgId);
		String productName = "三度云享家_会员充值";
		String productDesc = "三度云享家_会员充值";
		String productType = "recharge";
		if (!PAYABLE) {
			message.setMessage("支付功能已经关闭.");
			message.setSuccess(false);
			return message;
		}
		if (Utils.isBlank(payType)) {
			message.setMessage("传参异常.");
			return message;
		}
		if (totalFee.intValue() == 0) {
			message.setMessage("支付金额不能为0.");
			return message;
		}
		String tradeType = TradeType.APP;
		PayOrder payOrder = getOrder((int)totalFee, payType, productName, productDesc, tradeType);
		try {
			payOrder.setProductType(productType);
			sysSave(payOrder, request);
			payOrder.setBizType(BizType.RECHARGE);
			payOrder.setFinanceType(FinanceType.IN);
			payOrder.setAdjustFee((int)adjustFee);
			int id = payOrderService.add(payOrder);
			if (id == 0) {
				message.setMessage("保存支付单失败.");
				return message;
			}
			payOrder.setId(id);
			// 当支付方式为微信支付，走下面的业务分支
			if (payType.equalsIgnoreCase(PayType.WXPAY)) {
				String flag = "SUCCESS";
				UnifiedOrderService orderService = new UnifiedOrderService();
				UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(payOrder.getProductName(),
						payOrder.getOrderNo(), payOrder.getTotalFee());
				UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
				logger.info("微信下支付订单："+unifiedOrderResData.getResult_code()+"++++"+unifiedOrderResData.getReturn_code());
				if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
						&& unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
					String prepayId = unifiedOrderResData.getPrepay_id();
					payOrder.setPrepayId(prepayId);
					payOrder.setPayState(PayState.PAYING);
					payOrderService.update(payOrder);
					AppPayReqData reqData = new AppPayReqData(prepayId);
					message.setMessage("成功生成订单");
					message.setObj(reqData);
					message.setSuccess(true);
				}
			}
			if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
				String payInfo = AlipayCore.getOrder(payOrder.getOrderNo(), payOrder.getProductName(),
						payOrder.getProductDesc(), payOrder.getTotalFee());
				if (Utils.isBlank(payInfo)) {
					message.setMessage("订单数据错误.");
				} else {
					message.setMessage("成功生成订单数据");
					message.setObj(payInfo);
					message.setSuccess(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("下单异常.");
		}
		return message;
	}

	/**
	 * 预付款支付购买产品
	 */
	/*@RequestMapping(value = "/addpredepositpay")*/
	@RequestMapping(value = "/addpredepositpay_old")
	@ResponseBody
	public Object addPredepositPay(
			@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "totalFee", required = true, defaultValue = "0") double totalFee,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			HttpServletRequest request, HttpServletResponse response
			) throws Exception {
		ResultMessage message = new ResultMessage();
		message.setMsgId(msgId);
		String payType = PayType.PREDEPOSIT;
		if (userId.intValue() == 0) {
			userId = getCurrentUserId(request);
		}
		if (userId.intValue() == 0) {
			message.setMessage("请登录系统.");
			return message;
		}
		if (!PAYABLE) {
			message.setMessage("支付功能已经关闭.");
			return message;
		}
		if (totalFee == 0) {
			message.setMessage("支付金额为0!");
			return message;
		}
		if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
				|| Utils.isBlank(productType)) {
			message.setMessage("传参异常.");
			return message;
		}
		String tradeType = TradeType.PREDEPOSIT;
		// 直接从账户余额中扣除
		PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
		SysUser user = sysUserService.get(userId);
		if (user != null) {
			
			if (user.getBalanceAmount() < (int)totalFee) {
				message.setMessage("当前用户的账户余额不足.");
			} else {
				try {
					// 插入支付订单
					sysSave(payOrder, request);
					if (userId > 0) {
						payOrder.setUserId(userId);
					}
					payOrder.setBizType(BizType.BUY);
					payOrder.setFinanceType(FinanceType.OUT);
					payOrder.setPayState(PayState.SUCCESS);
					int id = payOrderService.add(payOrder);
					if (id == 0) {
						message.setMessage("保存支付单失败.");
						return message;
					}
					// 更新账户余额和消费金额
					updateUserFinance(payOrder.getOrderNo());

					message.setSuccess(true);
//					message.setMessage("预付款支付成功购买产品.");
					message.setMessage("付款成功.");
					// 通知客户端
					//payOrderService.sendPayState(payOrder.getOrderNo());

				} catch (Exception e) {
					e.printStackTrace();
					message.setSuccess(false);
					message.setMessage("预付款支付购买产品发生异常.");
				}
			}
		} else {
			message.setMessage("当前用户信息为空.");
		}
		//TODO   余额支付临时处理
		message.setOrderNo(payOrder.getOrderNo());
		return message;
	}

	/**
	 * 预付款支付购买产品
	 */
	@RequestMapping(value = "/addpredepositpay_5.1")
	@ResponseBody
	public Object addPredepositPayNew(
			@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, 
			@RequestParam(value = "totalFee", required = false, defaultValue = "0") double totalFee,
			String msgId,
			HttpServletRequest request, HttpServletResponse response
			// 新建高清渲染任务参数 ->start
			,String level, Integer isTurnOn, Integer planId, String params, Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String orderNo, String type,
			Integer mode,// 数据字典value
			// 新建高清渲染任务参数 ->end
			// 登录验证参数 ->start
			String authorization,
			Integer renderChannel
			// 登录验证参数 ->end
			) throws Exception {

		//判断当前时间是否在免费时间段内
		Boolean isFreeTime = renderTaskService.renderFreeTime();

		if(!StringUtils.equals("common_render", productType)&&!StringUtils.equals("panorama_render", productType)
				&&!StringUtils.equals("HD_render", productType)&&!StringUtils.equals("UHD_render", productType)
				&&!StringUtils.equals("video", productType)&&!StringUtils.equals("roam720", productType)
				){
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			String payType = PayType.PREDEPOSIT;
			if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				return message;
			}
			if (!isFreeTime){//判断当前时间是否在免费时间段内
				if (totalFee == 0) {
					message.setMessage("支付金额为0!");
					return message;
				}
			}
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			String tradeType = TradeType.PREDEPOSIT;
			// 直接从账户余额中扣除
			PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
			SysUser user = sysUserService.get(userId);
			if (user != null) {
				if (user.getBalanceAmount() < (int)totalFee) {
					message.setMessage("当前用户的账户余额不足.");
				} else {
					try {
						// 插入支付订单
						sysSave(payOrder, request);
						if (userId > 0) {
							payOrder.setUserId(userId);
						}
						payOrder.setBizType(BizType.BUY);
						payOrder.setFinanceType(FinanceType.OUT);
						payOrder.setPayState(PayState.SUCCESS);
						int id = payOrderService.add(payOrder);
						if (id == 0) {
							message.setMessage("保存支付单失败.");
							return message;
						}
						// 更新账户余额和消费金额
						updateUserFinance(payOrder.getOrderNo());

						message.setSuccess(true);
//						message.setMessage("预付款支付成功购买产品.");
						message.setMessage("付款成功.");
						// 通知客户端
						//payOrderService.sendPayState(payOrder.getOrderNo());

					} catch (Exception e) {
						e.printStackTrace();
						message.setSuccess(false);
						message.setMessage("预付款支付购买产品发生异常.");
					}
				}
			} else {
				message.setMessage("当前用户信息为空.");
			}
			//TODO   余额支付临时处理
			message.setOrderNo(payOrder.getOrderNo());
			return message;
		}else{
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			String payType = PayType.PREDEPOSIT;
			/*if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}*/
			
			// 高清渲染参数验证 ->start
			/*String taskConfig = "";*/
			/*temporaryPic 为高清渲染临时图片*/
			if( planId == null ){
				message.setMessage("planId不能为空!");
				message.setSuccess(false);
				return message;
			}
			 /*配置文件信息    产品模型  坐标 等一些渲染需要的 txt 文本*/
			if( StringUtils.isBlank(params) ){
				message.setMessage("params不能为空!");
				message.setSuccess(false);
				return message;
			}else{
				/*taskConfig = params;*/
			}
			/*优先级，如果等null  那么优先级月底*/
			if( priority == null ){
				priority = 1;
			}
			/*视角*/
			if(viewPoint==null){
				message.setMessage("参数viewPoint不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*场景 白天黑夜 黄昏？*/
			if(scene==null){
				message.setMessage("参数scene不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*渲染类型   720    照片级？*/
			if(renderingType ==null || "".equals(renderingType)){
				message.setMessage("参数renderingType不能为空");
				message.setSuccess(false);
				return message;
			}
			if(!isFreeTime){
				// mode
				if(mode == null){
					message.setMessage("参数mode不能为空");
					message.setSuccess(false);
					return message;
				}
				// type
				if(StringUtils.isBlank(type)){
					message.setMessage("参数type不能为空");
					message.setSuccess(false);
					return message;
				}
				if(renderChannel == null){
					message.setMessage("参数renderChannel不能为空");
					message.setSuccess(false);
					return message;
				}
			}else{
				renderChannel=RenderTypeCode.RENDER_CHANNEL_OF_DELAY;//免费时间段内渲染类型都设置成空闲渲染
			}
			
			// 高清渲染参数验证 ->end
			
			// 验证登录参数 ->start
			LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorization);
			if(loginUser == null){
				message.setMessage("请登录系统.");
				return message;
			}
//			request.getSession().setAttribute("loginUser", loginUser);
			// 验证登录参数 ->end
			
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->start
			int payOrderNonPayment = payOrderService.getCountByUserIdAndStatus(loginUser.getId(), PayState.PAYING);
			if(payOrderNonPayment > PAYINGORDERMAX){
				message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
				message.setSuccess(false);
				return message;
			}
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->end
			
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				return message;
			}
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			
			// 金钱处理 ->start(需要改造成通过renderType得到价格)
			String priceInfo="";
			if (isFreeTime){//判断当前时间是否在免费渲染时间段内：如果在免费渲染时间段内，直接把渲染金额置为0
				totalFee=0;
				priceInfo="渲染免费";
			}else{
				totalFee = 1;
				SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, mode);
				if(sysDictionary == null){
					throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + mode);
				}
				String totalFeeStr = sysDictionary.getAtt1();
				priceInfo = sysDictionary.getName();//金额及尺寸信息
				if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
					totalFee = Integer.parseInt(totalFeeStr);
				}
			}
			// 金钱处理 ->end
			
			String tradeType = TradeType.PREDEPOSIT;
			// 直接从账户余额中扣除
			PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
			userId = loginUser.getId();
			SysUser user = sysUserService.get(userId);
			if (user != null) {
				if (user.getBalanceAmount() < (int)totalFee) {
					message.setMessage("当前用户的账户余额不足.");
				} else {
					try {
						// 插入支付订单
						sysSave(payOrder, request);
						if (userId > 0) {
							payOrder.setUserId(userId);
						}
						payOrder.setBizType(BizType.BUY);
						payOrder.setFinanceType(FinanceType.OUT);
						payOrder.setPayState(PayState.SUCCESS);
						
						// 创建未支付状态的渲染任务 ->start
						SysTask sysTask = sysTaskService.createNonPaymentTask(isTurnOn, planId, params, priority, viewPoint,
								scene, renderingType,renderChannel,priceInfo,request);
						if(sysTask.getId() == null){
							message.setMessage(sysTask.getRemark());
							return message;
						}
						payOrder.setTaskId(sysTask.getId());
						// 创建未支付状态的渲染任务 ->end
						
						int id = payOrderService.add(payOrder);
						if (id == 0) {
							message.setMessage("保存支付单失败.");
							return message;
						}
						// 更新账户余额和消费金额
						updateUserFinance(payOrder.getOrderNo());

						message.setSuccess(true);
//						message.setMessage("预付款支付成功购买产品.");
						message.setMessage("付款成功.");
						
						// 渲染任务状态变更:未支付->待执行 ->start
						sysTaskService.updateNonPaymentTask(sysTask, "" + payOrder.getId(), request);
						// 渲染任务状态变更:未支付->待执行 ->end
						
						// 通知客户端
						//payOrderService.sendPayState(payOrder.getOrderNo());
					} catch (Exception e) {
						logger.error(e);
						message.setSuccess(false);
						message.setMessage("预付款支付购买产品发生异常.");
					}
				}
			} else {
				message.setMessage("当前用户信息为空.");
			}
			//TODO   余额支付临时处理
			message.setOrderNo(payOrder.getOrderNo());
			return message;
		}
	}
	
	/**
	 * 扫码支付购买产品
	 */
	/*@RequestMapping(value = "/addscanpay")*/
	@RequestMapping(value = "/addscanpay_old")
	@ResponseBody
	public Object addScanPay(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "totalFee", required = true, defaultValue = "0") double totalFee,
			@RequestParam(value = "payType", required = true) String payType,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = new ResultMessage();
		message.setMsgId(msgId);

		if (userId.intValue() == 0) {
			userId = getCurrentUserId(request);
		}
		if (userId.intValue() == 0) {
			message.setMessage("请登录系统.");
			return message;
		}
		if (!PAYABLE) {
			message.setMessage("支付功能已经关闭.");
			message.setSuccess(false);
			return message;
		}
		if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
				|| Utils.isBlank(productType)) {
			message.setMessage("传参异常.");
			return message;
		}
		String tradeType = TradeType.SCANCODE;
		try {
			if (totalFee == 0) {
				totalFee = Config.getScan_total_fee();
			}
			PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
			ScanPayReqData reqData = new ScanPayReqData();
			sysSave(payOrder, request);
			if (userId > 0) {
				payOrder.setUserId(userId);
			}
			payOrder.setBizType(BizType.BUY);
			payOrder.setFinanceType(FinanceType.OUT);
			int id = payOrderService.add(payOrder);
			if (id == 0) {
				message.setMessage("保存支付单失败.");
				return message;
			}
			payOrder.setId(id);
			reqData.setOrderId(payOrder.getId());
			reqData.setOrderNo(payOrder.getOrderNo());
			if (payType.equalsIgnoreCase(PayType.WXPAY)) {
				String flag = "SUCCESS";
				UnifiedOrderService orderService = new UnifiedOrderService();
				// 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
				String timeExpire = Utils.getTimeExpire(30);
				UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
						payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire);
				UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
				if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
						&& unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
					String prepayId = unifiedOrderResData.getPrepay_id();
					String wx_codeUrl = unifiedOrderResData.getCode_url();
					String codeUrl = QrCodeUtil.generateQrCode(request, wx_codeUrl, payOrder.getOrderNo());
					/*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/
					reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));
					payOrder.setPrepayId(prepayId);
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(codeUrl);
					payOrderService.update(payOrder);
					message.setMessage("成功生成扫描预付订单");
					message.setObj(reqData);
					message.setSuccess(true);
				}
			}
			if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
				message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
				message.setObj(reqData);
				if (message.isSuccess()) {
					message.setMessage("成功生成扫描预付订单");
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(reqData.getQrCodePath());
					payOrderService.update(payOrder);
				}
				message.setMsgId(msgId);
			}
			//TODO  临时处理余额支付
			if(payType.equalsIgnoreCase(PayType.PREDEPOSIT)){//余额支付
				message.setSuccess(true);
				message.setMessage("余额下订单成功!");
				//message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
				message.setObj(reqData);
				payOrder.setPayState(PayState.PAYING);
				payOrder.setCodeUrl("");
				payOrderService.update(payOrder);
				message.setMsgId(msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("下单异常.");
		}
		return message;
	}

	/**
	 * 扫码支付购买产品
	 */
	@RequestMapping(value = "/addscanpay_5.1")
	@ResponseBody
	public Object addScanPayNew(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "payType", required = true) String payType,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			@RequestParam(value = "totalFee", required = false, defaultValue = "0") double totalFee,
			HttpServletRequest request, HttpServletResponse response,
			// 新建高清渲染任务参数 ->start
			String level, Integer isTurnOn, Integer planId, String params, Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String orderNo, String type,
			Integer mode,// 数据字典value
			// 新建高清渲染任务参数 ->end
			// 登录验证参数 ->start
			String authorization,
			Integer renderChannel// 数据字典value
			// 登录验证参数 ->end
			) throws Exception {
		if(!StringUtils.equals("common_render", productType)&&!StringUtils.equals("panorama_render", productType)
			&&!StringUtils.equals("HD_render", productType)&&!StringUtils.equals("UHD_render", productType)
			&&!StringUtils.equals("video", productType)&&!StringUtils.equals("roam720", productType)
			){
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);

			if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				message.setSuccess(false);
				return message;
			}
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			String tradeType = TradeType.SCANCODE;
			try {
				if (totalFee == 0) {
					totalFee = Config.getScan_total_fee();
				}
				PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
				ScanPayReqData reqData = new ScanPayReqData();
				sysSave(payOrder, request);
				if (userId > 0) {
					payOrder.setUserId(userId);
				}
				payOrder.setBizType(BizType.BUY);
				payOrder.setFinanceType(FinanceType.OUT);
				int id = payOrderService.add(payOrder);
				if (id == 0) {
					message.setMessage("保存支付单失败.");
					return message;
				}
				payOrder.setId(id);
				reqData.setOrderId(payOrder.getId());
				reqData.setOrderNo(payOrder.getOrderNo());
				if (payType.equalsIgnoreCase(PayType.WXPAY)) {
					String flag = "SUCCESS";
					UnifiedOrderService orderService = new UnifiedOrderService();
					// 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
					String timeExpire = Utils.getTimeExpire(30);
					UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
							payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire);
					UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
					if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
							&& unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
						String prepayId = unifiedOrderResData.getPrepay_id();
						String wx_codeUrl = unifiedOrderResData.getCode_url();
						String codeUrl = QrCodeUtil.generateQrCode(request, wx_codeUrl, payOrder.getOrderNo());
						/*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/
						reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));
						payOrder.setPrepayId(prepayId);
						payOrder.setPayState(PayState.PAYING);
						payOrder.setCodeUrl(codeUrl);
						payOrderService.update(payOrder);
						message.setMessage("成功生成扫描预付订单");
						message.setObj(reqData);
						message.setSuccess(true);
					}
				}
				if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
					message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
					message.setObj(reqData);
					if (message.isSuccess()) {
						message.setMessage("成功生成扫描预付订单");
						payOrder.setPayState(PayState.PAYING);
						payOrder.setCodeUrl(reqData.getQrCodePath());
						payOrderService.update(payOrder);
					}
					message.setMsgId(msgId);
				}
				//TODO  临时处理余额支付
				if(payType.equalsIgnoreCase(PayType.PREDEPOSIT)){//余额支付
					message.setSuccess(true);
					message.setMessage("余额下订单成功!");
					//message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
					message.setObj(reqData);
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl("");
					payOrderService.update(payOrder);
					message.setMsgId(msgId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.setSuccess(false);
				message.setMessage("下单异常.");
			}
			return message;
		}else{
			// test(测试图片有没有上传) ->start
			/*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String filePicName = null;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();*/
			// test(测试图片有没有上传) ->end
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			
			// 验证 ->start
			/*if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}*/
			
			// 验证登录参数 ->start
			LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorization);
			if(loginUser == null){
				message.setMessage("请登录系统.");
				return message;
			}
//			request.getSession().setAttribute("loginUser", loginUser);
			// 验证登录参数 ->end
			
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				message.setSuccess(false);
				return message;
			}
			
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->start
			int payOrderNonPayment = payOrderService.getCountByUserIdAndStatus(loginUser.getId(), PayState.PAYING);
			if(payOrderNonPayment > PAYINGORDERMAX){
				message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
				message.setSuccess(false);
				return message;
			}
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->end
			
			// 高清渲染参数验证 ->start
			/*String taskConfig = "";*/
			/*temporaryPic 为高清渲染临时图片*/
			if( planId == null ){
				message.setMessage("planId不能为空!");
				message.setSuccess(false);
				return message;
			}
			 /*配置文件信息    产品模型  坐标 等一些渲染需要的 txt 文本*/
			if( StringUtils.isBlank(params) ){
				message.setMessage("params不能为空!");
				message.setSuccess(false);
				return message;
			}else{
				/*taskConfig = params;*/
			}
			/*优先级，如果等null  那么优先级月底*/
			if( priority == null ){
				priority = 1;
			}
			/*视角*/
			if(viewPoint==null){
				message.setMessage("参数viewPoint不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*场景 白天黑夜 黄昏？*/
			if(scene==null){
				message.setMessage("参数scene不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*渲染类型   720    照片级？*/
			if(renderingType ==null || "".equals(renderingType)){
				message.setMessage("参数renderingType不能为空");
				message.setSuccess(false);
				return message;
			}
			// mode
			if(mode == null){
				message.setMessage("参数mode不能为空");
				message.setSuccess(false);
				return message;
			}
			// type
			if(StringUtils.isBlank(type)){
				message.setMessage("参数type不能为空");
				message.setSuccess(false);
				return message;
			}
			// 高清渲染参数验证 ->end
			
			// 验证 ->end
			
			message = payOrderService.sendMessageAndCreateOrder(productId, productType, productName, productDesc,
					payType, userId, msgId, request, message,
					// 高清渲染参数 ->start
					level, isTurnOn, planId, params, priority, viewPoint, scene, renderingType, temporaryPic, type, mode,renderChannel
					// 高清渲染参数 ->end
					);
			
			return message;
		}
	}
	
	/**
	 * 扫码支付购买产品
	 */
	@RequestMapping(value = "/addscanpay_old")
	@ResponseBody
	public Object addScanPayNew_old(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "payType", required = true) String payType,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			@RequestParam(value = "totalFee", required = false, defaultValue = "0") double totalFee,
			HttpServletRequest request, HttpServletResponse response,
			// 新建高清渲染任务参数 ->start
			String level, Integer isTurnOn, Integer planId, String params, Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String orderNo, String type,
			Integer mode,// 数据字典value
			// 新建高清渲染任务参数 ->end
			// 登录验证参数 ->start
			String authorization,
			Integer renderChanne
			// 登录验证参数 ->end
			) throws Exception {
		if(!StringUtils.equals("common_render", productType)&&!StringUtils.equals("panorama_render", productType)
			&&!StringUtils.equals("HD_render", productType)&&!StringUtils.equals("UHD_render", productType)
			&&!StringUtils.equals("video", productType)&&!StringUtils.equals("roam720", productType)
			){
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);

			if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				message.setSuccess(false);
				return message;
			}
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			String tradeType = TradeType.SCANCODE;
			try {
				if (totalFee == 0) {
					totalFee = Config.getScan_total_fee();
				}
				PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
				ScanPayReqData reqData = new ScanPayReqData();
				sysSave(payOrder, request);
				if (userId > 0) {
					payOrder.setUserId(userId);
				}
				payOrder.setBizType(BizType.BUY);
				payOrder.setFinanceType(FinanceType.OUT);
				int id = payOrderService.add(payOrder);
				if (id == 0) {
					message.setMessage("保存支付单失败.");
					return message;
				}
				payOrder.setId(id);
				reqData.setOrderId(payOrder.getId());
				reqData.setOrderNo(payOrder.getOrderNo());
				if (payType.equalsIgnoreCase(PayType.WXPAY)) {
					String flag = "SUCCESS";
					UnifiedOrderService orderService = new UnifiedOrderService();
					// 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
					String timeExpire = Utils.getTimeExpire(30);
					UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
							payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire);
					UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
					if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
							&& unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
						String prepayId = unifiedOrderResData.getPrepay_id();
						String wx_codeUrl = unifiedOrderResData.getCode_url();
						String codeUrl = QrCodeUtil.generateQrCode(request, wx_codeUrl, payOrder.getOrderNo());
						/*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/
						reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));
						payOrder.setPrepayId(prepayId);
						payOrder.setPayState(PayState.PAYING);
						payOrder.setCodeUrl(codeUrl);
						payOrderService.update(payOrder);
						message.setMessage("成功生成扫描预付订单");
						message.setObj(reqData);
						message.setSuccess(true);
					}
				}
				if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
					message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
					message.setObj(reqData);
					if (message.isSuccess()) {
						message.setMessage("成功生成扫描预付订单");
						payOrder.setPayState(PayState.PAYING);
						payOrder.setCodeUrl(reqData.getQrCodePath());
						payOrderService.update(payOrder);
					}
					message.setMsgId(msgId);
				}
				//TODO  临时处理余额支付
				if(payType.equalsIgnoreCase(PayType.PREDEPOSIT)){//余额支付
					message.setSuccess(true);
					message.setMessage("余额下订单成功!");
					//message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
					message.setObj(reqData);
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl("");
					payOrderService.update(payOrder);
					message.setMsgId(msgId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.setSuccess(false);
				message.setMessage("下单异常.");
			}
			return message;
		}else{
			// test(测试图片有没有上传) ->start
			/*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String filePicName = null;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();*/
			// test(测试图片有没有上传) ->end
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			
			// 验证 ->start
			/*if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}*/
			
			// 验证登录参数 ->start
			LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorization);
			if(loginUser == null){
				message.setMessage("请登录系统.");
				return message;
			}
//			request.getSession().setAttribute("loginUser", loginUser);
			// 验证登录参数 ->end
			
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				message.setSuccess(false);
				return message;
			}
			
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->start
			int payOrderNonPayment = payOrderService.getCountByUserIdAndStatus(loginUser.getId(), PayState.PAYING);
			if(payOrderNonPayment > PAYINGORDERMAX){
				message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
				message.setSuccess(false);
				return message;
			}
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->end
			
			// 高清渲染参数验证 ->start
			/*String taskConfig = "";*/
			/*temporaryPic 为高清渲染临时图片*/
			if( planId == null ){
				message.setMessage("planId不能为空!");
				message.setSuccess(false);
				return message;
			}
			 /*配置文件信息    产品模型  坐标 等一些渲染需要的 txt 文本*/
			if( StringUtils.isBlank(params) ){
				message.setMessage("params不能为空!");
				message.setSuccess(false);
				return message;
			}else{
				/*taskConfig = params;*/
			}
			/*优先级，如果等null  那么优先级月底*/
			if( priority == null ){
				priority = 1;
			}
			/*视角*/
			if(viewPoint==null){
				message.setMessage("参数viewPoint不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*场景 白天黑夜 黄昏？*/
			if(scene==null){
				message.setMessage("参数scene不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*渲染类型   720    照片级？*/
			if(renderingType ==null || "".equals(renderingType)){
				message.setMessage("参数renderingType不能为空");
				message.setSuccess(false);
				return message;
			}
			// mode
			if(mode == null){
				message.setMessage("参数mode不能为空");
				message.setSuccess(false);
				return message;
			}
			// type
			if(StringUtils.isBlank(type)){
				message.setMessage("参数type不能为空");
				message.setSuccess(false);
				return message;
			}
			// 高清渲染参数验证 ->end
			
			// 验证 ->end
			
			message = payOrderService.sendMessageAndCreateOrder(productId, productType, productName, productDesc,
					payType, userId, msgId, request, message,
					// 高清渲染参数 ->start
					level, isTurnOn, planId, params, priority, viewPoint, scene, renderingType, temporaryPic, type, mode,renderChanne
					// 高清渲染参数 ->end
					);
			
			return message;
		}
	}
	
	/**
	 * 扫码在线充值
	 */
	@RequestMapping(value = "/addscanrecharge")
	@ResponseBody
	public Object addScanRecharge(
			@RequestParam(value = "totalFee", required = true, defaultValue = "0") double totalFee,
			@RequestParam(value = "adjustFee", required = false, defaultValue = "0") double adjustFee,
			@RequestParam(value = "payType", required = true) String payType,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, 
			@RequestParam(value = "productId", required = true) Integer productId,String msgId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ResultMessage message = new ResultMessage();
		message.setMsgId(msgId);
		String productName = "三度云享家_会员充值";
		String productDesc = "三度云享家_会员充值";
		String productType = "recharge";
//		String productName = "SuDu_memberCard";
//		String productDesc = "SuDu_memberCard"; 
		if (userId.intValue() == 0) {
			userId = getCurrentUserId(request);
		}
		if (userId.intValue() == 0) {
			message.setMessage("请登录系统.");
			return message;
		}
		if (!PAYABLE) {
			message.setMessage("支付功能已经关闭.");
			message.setSuccess(false);
			return message;
		}
		if (Utils.isBlank(payType)) {
			message.setMessage("payType传参异常.");
			return message;
		}
		if ("0".equals(totalFee)) {
			message.setMessage("支付金额不能为0.");
			return message;
		}
		SysDictionary sysDictionary = sysDictionaryService.get(productId);
		if( sysDictionary == null ){
			message.setMessage("productId传参异常！");
			return message;
		}
		if( (int)totalFee != sysDictionary.getValue().intValue() ){
			logger.error("实际充值："+totalFee+";实际实际充值："+sysDictionary.getValue());
			message.setMessage("充值金额与实际充值金额不一致！");
			return message;
		}
		if( (int)adjustFee != Utils.getIntValue(sysDictionary.getAtt1().trim()) ){
			logger.error("赠送金额："+adjustFee+";实际赠送金额："+sysDictionary.getAtt1());
			message.setMessage("充值金额与实际充值金额不一致！");
			return message;
		}
		String tradeType = TradeType.SCANCODE;
		PayOrder payOrder = getOrder((int)totalFee, payType, productName, productDesc, tradeType);
		ScanPayReqData reqData = new ScanPayReqData();
		try {
			payOrder.setProductType(productType);
			sysSave(payOrder, request);
			if (userId > 0) {
				payOrder.setUserId(userId);
			}
			payOrder.setAdjustFee((int)adjustFee);
			payOrder.setBizType(BizType.RECHARGE);
			payOrder.setFinanceType(FinanceType.IN);
			int id = payOrderService.add(payOrder);
			if (id == 0) {
				message.setMessage("保存在线充值单失败.");
				return message;
			}
			payOrder.setId(id);
			reqData.setOrderId(payOrder.getId());
			if (payType.equalsIgnoreCase(PayType.WXPAY)) {
				String flag = "SUCCESS";
				UnifiedOrderService orderService = new UnifiedOrderService();
				// 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
				String timeExpire = Utils.getTimeExpire(30);
				UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
						payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire);
				UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
				if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
						&& unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
					String prepayId = unifiedOrderResData.getPrepay_id();
					String wx_codeUrl = unifiedOrderResData.getCode_url();
					String codeUrl = QrCodeUtil.generateQrCode(request, wx_codeUrl, payOrder.getOrderNo());
					/*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/
					
					/*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));*/
					reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
					payOrder.setPrepayId(prepayId);
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(codeUrl);
					payOrderService.update(payOrder);
					message.setMessage("成功生成在线充值单");
					message.setObj(reqData);
					message.setSuccess(true);
					
					//FIXME:每次获取充值单就添加一条充值记录
					MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
					mgrRechargeLog.setUserId(userId);
					mgrRechargeLog.setRechargeType(2);
					mgrRechargeLog.setRechargeStatus(2);
					mgrRechargeLog.setRechargeAmount(totalFee/100);
					mgrRechargeLog.setAdministratorId(userId);
					mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
					sysSave(mgrRechargeLog,request);
					mgrRechargeLogService.add(mgrRechargeLog);
				}
			}
			if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
				message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
				message.setObj(reqData);
				if (message.isSuccess()) {
					message.setMessage("成功生成在线充值单");
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(reqData.getQrCodePath());
					payOrderService.update(payOrder);
					
					//FIXME:每次获取充值单就添加一条充值记录
					MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
					mgrRechargeLog.setUserId(userId);
					mgrRechargeLog.setRechargeType(3);
					mgrRechargeLog.setRechargeStatus(2);
					mgrRechargeLog.setRechargeAmount(totalFee/100);
					mgrRechargeLog.setAdministratorId(userId);
					mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
					sysSave(mgrRechargeLog,request);
					mgrRechargeLogService.add(mgrRechargeLog);
					
				}
				message.setMsgId(msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("下单异常.");
		}
		return message;
	}
	
	/*@RequestMapping(value = "/alipaynotify")*/
	@RequestMapping(value = "/alipaynotify_old")
	public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付宝支付回调。。。 ");
		if (!PAYABLE) {
			return;
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}
			// 商户订单号
			String orderNo = request.getParameter("out_trade_no");
			// 支付宝交易号
			String tradeNo = request.getParameter("trade_no");
			// 交易状态
			String tradeStatus = request.getParameter("trade_status");
			// 异步通知ID
			String notify_id = request.getParameter("notify_id");
			// sign
			String sign = request.getParameter("sign");
			PrintWriter writer = response.getWriter();

			if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true")) // 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
				{
					PayOrder order = new PayOrder();
					order.setOrderNo(orderNo);
					order.setPayState(PayState.PAY_ERROR);
					order.setRefId(tradeNo);
					logger.info("使用支付宝公钥验签：" + AlipayNotify.getSignVeryfy(params, sign));
					if (AlipayNotify.getSignVeryfy(params, sign)) // 使用支付宝公钥验签
					{
						// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
						if (tradeStatus.equals("TRADE_FINISHED")) {

						} else if (tradeStatus.equals("TRADE_SUCCESS")) {

							order.setPayState(PayState.SUCCESS);
							payOrderService.updatePayState(order);
							// 给客户端发送支付状态消息
							logger.info("支付结果：" + order.getPayState());
							payOrderService.sendPayState(orderNo);
							updateUserFinance(orderNo);
						}
						writer.print("success");
					}
					// 验证签名失败
					else {
						writer.print("sign fail");
						payOrderService.updatePayState(order);
						// 给客户端发送支付状态消息
						logger.info("支付结果：" + order.getPayState());
						payOrderService.sendPayState(orderNo);
					}
				}
				// 验证是否来自支付宝的通知失败
				else {
					writer.print("response fail");
				}
			} else {
				writer.print("no notify message");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/alipaynotify_5.1")
	public void aliPayNotifyNew(HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付宝支付回调。。。 ");
		if (!PAYABLE) {
			return;
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}
			// 商户订单号
			String orderNo = request.getParameter("out_trade_no");
			// 支付宝交易号
			String tradeNo = request.getParameter("trade_no");
			// 交易状态
			String tradeStatus = request.getParameter("trade_status");
			// 异步通知ID
			String notify_id = request.getParameter("notify_id");
			// sign
			String sign = request.getParameter("sign");
			PrintWriter writer = response.getWriter();

			if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true")) // 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
				{
					PayOrder order = new PayOrder();
					order.setOrderNo(orderNo);
					order.setPayState(PayState.PAY_ERROR);
					order.setRefId(tradeNo);
					logger.info("使用支付宝公钥验签：" + AlipayNotify.getSignVeryfy(params, sign));
					if (AlipayNotify.getSignVeryfy(params, sign)) // 使用支付宝公钥验签
					{
						// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
						if (tradeStatus.equals("TRADE_FINISHED")) {

						} else if (tradeStatus.equals("TRADE_SUCCESS")) {

							order.setPayState(PayState.SUCCESS);
							payOrderService.updatePayState(order);
							// 给客户端发送支付状态消息
							logger.info("支付结果：" + order.getPayState());
							payOrderService.sendPayState(orderNo);
							updateUserFinance(orderNo);
							
							// 渲染任务状态变更:未支付->待执行 ->start
							PayOrder payOrder = payOrderService.findOneByOrderNo(orderNo);
							if(payOrder != null){
								Integer sysTaskId = payOrder.getTaskId();
								if(sysTaskId != null){
									SysTask sysTask = sysTaskService.get(sysTaskId);
									if(sysTask != null){
										sysTaskService.updateNonPaymentTask(sysTask, "" + order.getId(), request);
									}else{
										// 处理??
										logger.error("------sysTask为空,sysTask.id:" + sysTaskId);
									}
								}else{
									// 处理??
									// 可能是序列号支付
									/*logger.error("-----payOrder中的taskId字段为空,payOrder.orderNo:" + order.getOrderNo());*/
								}
							}else{
								// 处理??
								logger.error("------payOrder为空,payOrder.orderNo:" + order.getOrderNo());
							}
							// 渲染任务状态变更:未支付->待执行 ->end
							
						}
						payOrderService.sendPayState(orderNo);
						writer.print("success");
					}
					// 验证签名失败
					else {
						writer.print("sign fail");
						payOrderService.updatePayState(order);
						// 给客户端发送支付状态消息
						logger.info("支付结果：" + order.getPayState());
						payOrderService.sendPayState(orderNo);
					}
				}
				// 验证是否来自支付宝的通知失败
				else {
					writer.print("response fail");
				}
			} else {
				writer.print("no notify message");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*@RequestMapping(value = "/wxpaynotify")*/
	@RequestMapping(value = "/wxpaynotify_old")
	public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("微信支付回调。。。");
		if (!PAYABLE) {
			return;
		}
		try {
			boolean success = false;
			BufferedReader reader = request.getReader();
			String line = "";
			StringBuffer sbResult = new StringBuffer();
			response.reset();
			PrintWriter writer = response.getWriter();
			while ((line = reader.readLine()) != null) {
				sbResult.append(line);
			}
			if (reader != null) {
				reader.close();
			}
			if (sbResult.length() > 0) {
				//////System.out.println("weixin-pay-notify-result:" + sbResult);
				logger.info("微信回调结果:" + sbResult);
				ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult.toString(), ResultNotify.class);
				if (result != null) {
					String flag = "SUCCESS";
					PayOrder order = new PayOrder();
					String orderNo = result.getOut_trade_no();
					String openId = result.getOpenid();
					String refId = result.getTransaction_id();
					order.setOrderNo(orderNo);
					order.setOpenId(openId);
					order.setRefId(refId);
					order.setPayState(PayState.PAY_ERROR);
					if (result.getResult_code().equalsIgnoreCase(flag)
							&& result.getReturn_code().equalsIgnoreCase(flag)) {
						success = true;
						order.setPayState(PayState.SUCCESS);
						updateUserFinance(orderNo);
					}
					payOrderService.updatePayState(order);
					// 给客户端发送支付状态消息
					logger.info("支付结果：" + order.getPayState());
					payOrderService.sendPayState(orderNo);
					updateUserFinance(orderNo);
				}
			}
			if (success) {
				writer.write(XMLParser.getResponseWeixin(true, "OK"));
			} else {
				writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
			}
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/wxpaynotify_5.1")
	public void wxPayNotifyNew(HttpServletRequest request, HttpServletResponse response
			// 模拟回调作用(发布注释),订单号 ->start
			/*,String orderNoTest*/
			// 模拟回调作用(发布注释),订单号 ->end
			) {
		logger.info("微信支付回调。。。");
		if (!PAYABLE) {
			return;
		}
		try {
			boolean success = false;
			BufferedReader reader = request.getReader();
			String line = "";
			StringBuffer sbResult = new StringBuffer();
			response.reset();
			PrintWriter writer = response.getWriter();
			while ((line = reader.readLine()) != null) {
				sbResult.append(line);
			}
			if (reader != null) {
				reader.close();
			}
			if (sbResult.length() > 0) {
				//////System.out.println("weixin-pay-notify-result:" + sbResult);
				logger.info("微信回调结果:" + sbResult);
				// 测试回调接口是否会修改渲染任务状态和生成渲染配置文件所以注释,提交解开注释
				ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult.toString(), ResultNotify.class);
				// test 提交注释掉 ->start
				/*ResultNotify result = new ResultNotify();
				result.setResult_code("SUCCESS");
				result.setReturn_code("SUCCESS");*/
				// test 提交注释掉 ->end
				if (result != null) {
					String flag = "SUCCESS";
					PayOrder order = new PayOrder();
					String orderNo = result.getOut_trade_no();
					String openId = result.getOpenid();
					String refId = result.getTransaction_id();
					// test 提交注释掉 ->start
					/*orderNo = orderNoTest;*/
					// test 提交注释掉 ->end
					order.setOrderNo(orderNo);
					order.setOpenId(openId);
					order.setRefId(refId);
					order.setPayState(PayState.PAY_ERROR);
					if (result.getResult_code().equalsIgnoreCase(flag)
							&& result.getReturn_code().equalsIgnoreCase(flag)) {
						success = true;
						order.setPayState(PayState.SUCCESS);
						updateUserFinance(orderNo);
					}
					payOrderService.updatePayState(order);
					// 给客户端发送支付状态消息
					logger.info("支付结果：" + order.getPayState());
					// 渲染任务状态变更:未支付->待执行 ->start
					PayOrder payOrder = payOrderService.findOneByOrderNo(order.getOrderNo());
					if(payOrder != null){
						Integer sysTaskId = payOrder.getTaskId();
						if(sysTaskId != null){
							SysTask sysTask = sysTaskService.get(sysTaskId);
							if(sysTask != null){
								sysTaskService.updateNonPaymentTask(sysTask, "" + order.getId(), request);
							}else{
								// 处理??
								logger.error("------sysTask为空,sysTask.id:" + sysTaskId);
							}
						}else{
							// 处理??
							// 可能是购买序列号
							/*logger.error("-----payOrder中的taskId字段为空,payOrder.orderNo:" + order.getOrderNo());*/
						}
					}else{
						// 处理??
						logger.error("------payOrder为空,payOrder.orderNo:" + order.getOrderNo());
					}
					// 渲染任务状态变更:未支付->待执行 ->end
					payOrderService.sendPayState(orderNo);
					updateUserFinance(orderNo);
				}
			}
			if (success) {
				writer.write(XMLParser.getResponseWeixin(true, "OK"));
			} else {
				writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
			}
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取 支出凭证详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style, @ModelAttribute("payOrder") PayOrder payOrder,
			HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payOrder = (PayOrder) JsonUtil.getJsonToBean(jsonStr, PayOrder.class);
			if (payOrder == null) {
				return new ResponseEnvelope<PayOrder>(false, "none", "传参异常!");
			}
			id = payOrder.getId();
			msgId = payOrder.getMsgId();
		} else {
			id = payOrder.getId();
			msgId = payOrder.getMsgId();
		}

		if (id == null) {
			return new ResponseEnvelope<PayOrder>(false, "参数缺少id!", msgId);
		}

		try {
			payOrder = payOrderService.get(id);

			if ("small".equals(style) && payOrder != null) {
				String payOrderJson = JsonUtil.getBeanToJsonData(payOrder);
				PayOrderSmall payOrderSmall = new JsonDataServiceImpl<PayOrderSmall>().getJsonToBean(payOrderJson,
						PayOrderSmall.class);

				return new ResponseEnvelope<PayOrderSmall>(payOrderSmall, msgId, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<PayOrder>(payOrder, msgId, true);
	}

	/**
	 * 删除支出凭证,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style, @ModelAttribute("payOrder") PayOrder payOrder,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids = "";
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payOrder = (PayOrder) JsonUtil.getJsonToBean(jsonStr, PayOrder.class);
			if (payOrder == null) {
				return new ResponseEnvelope<PayOrder>(false, "传参异常!", "none");
			}
			ids = payOrder.getIds();
			msgId = payOrder.getMsgId();
		} else {
			ids = payOrder.getIds();
			msgId = payOrder.getMsgId();
		}

		if (ids == null) {
			return new ResponseEnvelope<PayOrder>(false, "参数ids不能为空!", msgId);
		}
		int i = 0;
		try {
			if (ids != null) {
				if (ids.contains(",")) {
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = payOrderService.delete(id);
						logger.info("delete:id=" + id);
					}
				} else {
					Integer id = new Integer(ids);
					i = payOrderService.delete(id);
					logger.info("delete:id=" + id);
				}
			}

			if (i == 0) {
				return new ResponseEnvelope<PayOrder>(false, "记录不存在!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "删除失败!", msgId);
		}
		return new ResponseEnvelope<PayOrder>(true, msgId, true);
	}

	/**
	 * 支出凭证列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style, @ModelAttribute("payOrderSearch") PayOrderSearch payOrderSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		payOrderSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payOrderSearch = (PayOrderSearch) JsonUtil.getJsonToBean(jsonStr, PayOrderSearch.class);
			if (payOrderSearch == null) {
				return new ResponseEnvelope<PayOrder>(false, "传参异常!", "none");
			}
		}

		List<PayOrder> list = new ArrayList<PayOrder>();
		int total = 0;
		try {
			total = payOrderService.getCount(payOrderSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = payOrderService.getPaginatedList(payOrderSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String payOrderJsonList = JsonUtil.getListToJsonData(list);
				List<PayOrderSmall> smallList = new JsonDataServiceImpl<PayOrderSmall>()
						.getJsonToBeanList(payOrderJsonList, PayOrderSmall.class);
				return new ResponseEnvelope<PayOrderSmall>(total, smallList, payOrderSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "数据异常!", payOrderSearch.getMsgId());
		}
		return new ResponseEnvelope<PayOrder>(total, list, payOrderSearch.getMsgId());
	}

	/**
	 * 支出凭证全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style, @ModelAttribute("payOrderSearch") PayOrderSearch payOrderSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payOrderSearch = (PayOrderSearch) JsonUtil.getJsonToBean(jsonStr, PayOrderSearch.class);
			if (payOrderSearch == null) {
				return new ResponseEnvelope<PayOrder>(false, "传参异常!", "none");
			}
		}

		List<PayOrder> list = new ArrayList<PayOrder>();
		int total = 0;
		try {
			total = payOrderService.getCount(payOrderSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = payOrderService.getList(payOrderSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String payOrderJsonList = JsonUtil.getListToJsonData(list);
				List<PayOrderSmall> smallList = new JsonDataServiceImpl<PayOrderSmall>()
						.getJsonToBeanList(payOrderJsonList, PayOrderSmall.class);
				return new ResponseEnvelope<PayOrderSmall>(total, smallList, payOrderSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "数据异常!", payOrderSearch.getMsgId());
		}
		return new ResponseEnvelope<PayOrder>(total, list, payOrderSearch.getMsgId());
	}

	/**
	 * 获取 支出凭证详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		PayOrder payOrder = null;
		try {
			payOrder = payOrderService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "数据异常!");
		}
		ResponseEnvelope<PayOrder> res = new ResponseEnvelope<PayOrder>(payOrder);
		request.setAttribute("res", res);

		String url = "";
		String type = (String) request.getParameter("pageType");
		if ("edit".equals(type)) {
			url = JSPMAIN + "/payOrder_edit";
		} else {
			url = JSPMAIN + "/payOrder_view";
		}
		return Utils.getPageUrl(request, url);
	}

	/**
	 * 支出凭证列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(@ModelAttribute("payOrderSearch") PayOrderSearch payOrderSearch, HttpServletRequest request,
			HttpServletResponse response) {

		List<PayOrder> list = new ArrayList<PayOrder>();
		int total = 0;
		try {
			total = payOrderService.getCount(payOrderSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = payOrderService.getPaginatedList(payOrderSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "数据异常!");
		}

		ResponseEnvelope<PayOrder> res = new ResponseEnvelope<PayOrder>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", payOrderSearch);

		return Utils.getPageUrl(request, JSPMAIN + "/payOrder_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(PayOrder model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setUserId(loginUser.getId());
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	private int getCurrentUserId(HttpServletRequest request) {
		int userId = 0;
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) != null) {
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userId = loginUser.getId();
		}
		return userId;
	}
	private LoginUser getCurrentLoginUser(HttpServletRequest request) {
		LoginUser loginUser = null;
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) != null) {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		return loginUser;
	}
	private void updateUserFinance(String orderNo) {
		PayOrder order = payOrderService.get(orderNo);
		if (order == null) {
			return;
		}
		int userId = order.getUserId();
		SysUser user = new SysUser();
		user.setId(userId);
		logger.info("++++++++++paystate:"+order.getPayState()+";biztype:"+order.getBizType()+";paytype:"+order.getPayType()+";TotalFee:"+order.getTotalFee());
		if (order.getPayState() != null && order.getBizType() != null) {
			if(order.getFinanceType().equalsIgnoreCase(FinanceType.OUT)){
				if (order.getPayState().equalsIgnoreCase(PayState.SUCCESS)
						&& order.getBizType().equalsIgnoreCase(BizType.BUY)
						&& order.getPayType().equalsIgnoreCase(PayType.PREDEPOSIT)) {
					user.setBalanceAmount(0 - Double.parseDouble(order.getTotalFee().toString()));
					user.setConsumAmount(Double.parseDouble(order.getTotalFee().toString()));
					sysUserService.updateFinance(user);
				}
				if (order.getPayState().equalsIgnoreCase(PayState.SUCCESS)
						&& (order.getPayType().equalsIgnoreCase(PayType.ALIPAY)
								|| order.getPayType().equalsIgnoreCase(PayType.WXPAY))) {
					user.setConsumAmount(Double.parseDouble(order.getTotalFee().toString()));
					sysUserService.updateFinance(user);
				}
			}
			if(order.getFinanceType().equalsIgnoreCase(FinanceType.IN)){
				if (order.getPayState().equalsIgnoreCase(PayState.SUCCESS)
						&& order.getBizType().equalsIgnoreCase(BizType.RECHARGE)) {
					user.setBalanceAmount(Double.parseDouble(order.getTotalFee().toString())+order.getAdjustFee().intValue());
					sysUserService.updateFinance(user);
				}
			}
		}
		//更新授权码信息
		if (ProductType.AUTHCODE_RENEW.equals(order.getProductType()) ||
				ProductType.AUTHCODE_PURCHASE.equals(order.getProductType()) ||
				ProductType.AUTHCODE_DREDGE.equals(order.getProductType())) {
			if (order.getProductId() != null) {
				AuthorizedConfig authorizedConfig = authorizedConfigService.get(order.getProductId());
				if( authorizedConfig != null ){
					SysDictionary payStatus = sysDictionaryService.findOneByTypeAndValueKey("payStatus", "payStatus_finish");
					SysDictionary payModel = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
					authorizedConfig.setPayStatusValue(payStatus==null?1:payStatus.getValue());
					authorizedConfig.setPayModelValue(payModel==null?1:payModel.getValue());
					authorizedConfig.setGmtModified(new Date());
					authorizedConfigService.update(authorizedConfig);
				}
			}
		}
	}
	/**
	 * 个人消费记录列表
	 * @return
	 */
	@RequestMapping(value = "/findExpenseRecordList")
	@ResponseBody
	public Object findExpenseRecordList(@ModelAttribute("payOrderSearch") PayOrderSearch payOrderSearch,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId,
			HttpServletRequest request, HttpServletResponse response) {
		
		if (StringUtils.isEmpty(payOrderSearch.getMsgId())) {
			return new ResponseEnvelope<PayOrder>(false, "参数msgId为空!", "");
		}
		if (userId.intValue() == 0) {
			userId = getCurrentUserId(request);
		}
		if (userId == 0) {
			return new ResponseEnvelope<PayOrder>(false, "请登录系统!", payOrderSearch.getMsgId());
		}
		//只有支付成功的订单消费记录，排除序列号的订单，sql处理
		payOrderSearch.setUserId(userId);
		payOrderSearch.setPayState(PayState.SUCCESS);
		List<PayOrder> list = new ArrayList<PayOrder>();
		int total = 0;
		try {
			total = payOrderService.getCount(payOrderSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = payOrderService.getPaginatedList(payOrderSearch);
				for (PayOrder payOrder : list) {
					payOrder.setTotalFee((payOrder.getTotalFee()+payOrder.getAdjustFee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayOrder>(false, "数据异常!", payOrderSearch.getMsgId());
		}
		return new ResponseEnvelope<PayOrder>(total, list, payOrderSearch.getMsgId());
	}



	/**
	 * 微信自定义分享信息
	 * @return
	 */
	@RequestMapping(value = "/getShareInfo")
	public void getShareConfig(
			HttpServletRequest request, String url,HttpServletResponse response) throws Exception {
		ServletContext context = request.getSession().getServletContext();
		String accessToken = (String) context.getAttribute("accessToken");
		if (com.nork.common.util.StringUtils.isEmpty(accessToken)) {
			accessToken = WeixinUtils.getAccessToken();
			context.setAttribute("accessToken", accessToken);
		}
		String jsapiTicket = (String) context.getAttribute("jsapiTicket");
		if (com.nork.common.util.StringUtils.isEmpty(jsapiTicket)) {
			jsapiTicket = WeixinUtils.getTicket(accessToken);
			context.setAttribute("jsapiTicket", jsapiTicket);
		}
		Map<String, Object> ret = WeixinUtils.sign(jsapiTicket, url);
//		request.setAttribute("url", ret.get("url"));
//		request.setAttribute("jsapi_ticket", ret.get("jsapi_ticket"));
//		request.setAttribute("nonceStr", ret.get("nonceStr"));
//		request.setAttribute("timestamp", ret.get("timestamp"));
//		request.setAttribute("signature", ret.get("signature"));
		JSONObject json = JSONObject.fromObject(ret);
		response.getWriter().print(json);

//		return new ResponseEnvelope(ret);
	}
	
	/**
	 * 扫码支付购买产品
	 */
	@RequestMapping(value = "/addscanpay")
	@ResponseBody
	public Object addScanPayOrder(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "payType", required = true) String payType,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, String msgId,
			@RequestParam(value = "totalFee", required = false, defaultValue = "0") Integer totalFee,
			HttpServletRequest request, HttpServletResponse response,
			// 渲染任务参数 ->start
			String level, Integer isTurnOn, Integer planId,Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String orderNo, String type,
			Integer mode,// 数据字典value
			// 渲染任务参数 ->end
			// 登录验证参数 ->start
			String authorization
			// 登录验证参数 ->end
			) throws Exception {
		if(!StringUtils.equals("common_render", productType)&&!StringUtils.equals("panorama_render", productType)
			&&!StringUtils.equals("HD_render", productType)&&!StringUtils.equals("UHD_render", productType)
			&&!StringUtils.equals("video", productType)&&!StringUtils.equals("roam720", productType)
			){
			
			LoginUser loginUser = getCurrentLoginUser(request);
			ResultMessage message = new ResultMessage();

			if (userId.intValue() == 0) {
				userId = loginUser.getId();
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}
			if(Utils.isBlank(payType)){
				message.setMessage("参数payType不能为空");
				return message;
			}
			if(Utils.isBlank(productDesc)){
				message.setMessage("参数productDesc不能为空");
				return message;
			}
			if(Utils.isBlank(productName)){
				message.setMessage("参数productName不能为空");
				return message;
			}
			if(Utils.isBlank(productType)){
				message.setMessage("参数productType不能为空");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				message.setSuccess(false);
				return message;
			}
			
			//获取扫码支付信息(生成二维码信息)
		    message = payOrderService.getPayScanOrderUrlInfo(totalFee,payType,productId,productType,productName,productDesc,loginUser,msgId);
		    message.setMsgId(msgId);
			
			return message;
		}else{
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			
			// 验证登录参数 ->start
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			if(loginUser == null){
				message.setMessage("登录超时");
				message.setSuccess(false);
				return message;
			}else{
				
			}
			// 验证登录参数 ->end
			
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				message.setSuccess(false);
				return message;
			}
			
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->start
			int payOrderNonPayment = payOrderService.getCountByUserIdAndStatus(loginUser.getId(), PayState.PAYING);
			if(payOrderNonPayment > PAYINGORDERMAX){
				message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
				message.setSuccess(false);
				return message;
			}
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->end
			
			// 高清渲染参数验证 ->start
			/*temporaryPic 为高清渲染临时图片*/
			if( planId == null ){
				message.setMessage("planId不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*优先级，如果等null  那么优先级月底*/
			if( priority == null ){
				priority = 1;
			}
			/*视角*/
			if(viewPoint==null){
				message.setMessage("参数viewPoint不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*场景 白天黑夜 黄昏？*/
			if(scene==null){
				message.setMessage("参数scene不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*渲染类型   720    照片级？*/
			if(renderingType ==null){
				message.setMessage("参数renderingType不能为空");
				message.setSuccess(false);
				return message;
			}
			// mode
			if(mode == null){
				message.setMessage("参数mode不能为空");
				message.setSuccess(false);
				return message;
			}
			// type
			if(StringUtils.isBlank(type)){
				message.setMessage("参数type不能为空");
				message.setSuccess(false);
				return message;
			}
			// 高清渲染参数验证 ->end
			
			// 验证 ->end
			
			message = payOrderService.sendMessageAndCreateOrderNew(productId, productType, productName, productDesc,
					payType, userId, msgId, message,
					// 高清渲染参数 ->start
					level, isTurnOn, planId, priority, viewPoint, scene, renderingType, temporaryPic, type, mode,loginUser
					// 高清渲染参数 ->end
					);
			
			return message;
		}
	}
	
	/**
	 * 5.6版本：预付款支付购买产品
	 */
	@RequestMapping(value = "/addpredepositpay")
	@ResponseBody
	public Object addPredepositPayNew(
			@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId, 
			@RequestParam(value = "totalFee", required = false, defaultValue = "0") Integer totalFee,
			String msgId,
			HttpServletRequest request, HttpServletResponse response
			// 新建高清渲染任务参数 ->start
			,String level, Integer isTurnOn, Integer planId,Integer priority, Integer viewPoint,
			Integer scene, String renderingType, String temporaryPic, String orderNo, String type,
			Integer mode,// 数据字典value
			// 新建高清渲染任务参数 ->end
			// 登录验证参数 ->start
			String authorization
			// 登录验证参数 ->end
			) throws Exception {

		//判断当前时间是否在免费时间段内
//		Boolean isFreeTime = renderTaskService.renderFreeTime();

		if(!StringUtils.equals("common_render", productType)&&!StringUtils.equals("panorama_render", productType)
				&&!StringUtils.equals("HD_render", productType)&&!StringUtils.equals("UHD_render", productType)
				&&!StringUtils.equals("video", productType)&&!StringUtils.equals("roam720", productType)
				){
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			String payType = PayType.PREDEPOSIT;
			if (userId.intValue() == 0) {
				userId = getCurrentUserId(request);
			}
			if (userId.intValue() == 0) {
				message.setMessage("请登录系统.");
				return message;
			}
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				return message;
			}
//			if (!isFreeTime){//判断当前时间是否在免费时间段内
				if (totalFee == 0) {
					message.setMessage("支付金额为0!");
					return message;
				}
//			}
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				return message;
			}
			String tradeType = TradeType.PREDEPOSIT;
			// 直接从账户余额中扣除
			PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
			SysUser user = sysUserService.get(userId);
			if (user != null) {
				if (user.getBalanceAmount() < (int)totalFee) {
					message.setMessage("当前用户的账户余额不足.");
				} else {
					try {
						// 插入支付订单
						sysSave(payOrder, request);
						if (userId > 0) {
							payOrder.setUserId(userId);
						}
						payOrder.setBizType(BizType.BUY);
						payOrder.setFinanceType(FinanceType.OUT);
						payOrder.setPayState(PayState.SUCCESS);
						int id = payOrderService.add(payOrder);
						if (id == 0) {
							message.setMessage("保存支付单失败.");
							return message;
						}
						// 更新账户余额和消费金额
						updateUserFinance(payOrder.getOrderNo());

						message.setSuccess(true);
						message.setMessage("付款成功.");
						// 通知客户端
						//payOrderService.sendPayState(payOrder.getOrderNo());

					} catch (Exception e) {
						e.printStackTrace();
						message.setSuccess(false);
						message.setMessage("预付款支付购买产品发生异常.");
					}
				}

			} else {
				message.setMessage("当前用户信息为空.");
			}
			//判断用户是否需要自动升级
			sysResLevelCfgService.processAfterConsume(payOrder.getOrderNo());
			//TODO   余额支付临时处理
			message.setOrderNo(payOrder.getOrderNo());
			return message;
		}else{
			ResultMessage message = new ResultMessage();
			message.setMsgId(msgId);
			String payType = PayType.PREDEPOSIT;
			
			// 高清渲染参数验证 ->start
			if( planId == null ){
				message.setMessage("planId不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*优先级，如果等null  那么优先级月底*/
			if( priority == null ){
				priority = 1;
			}
			/*视角*/
			if(viewPoint==null){
				message.setMessage("参数viewPoint不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*场景 白天黑夜 黄昏？*/
			if(scene==null){
				message.setMessage("参数scene不能为空!");
				message.setSuccess(false);
				return message;
			}
			/*渲染类型   720    照片级？*/
			if(StringUtils.isBlank(renderingType)){
				message.setMessage("参数renderingType不能为空");
				message.setSuccess(false);
				return message;
			}
//			if(!isFreeTime){
			if(RenderTypeCode.COMMON_PICTURE_LEVEL != Integer.parseInt(renderingType)){//照片级渲染免费，这俩字段可以不传
				// mode
				if(mode == null){
					message.setMessage("参数mode不能为空");
					message.setSuccess(false);
					return message;
				}
				// type
				if(StringUtils.isBlank(type)){
					message.setMessage("参数type不能为空");
					message.setSuccess(false);
					return message;
				}
			}else{

			}

//			}else{
//			}
			// 高清渲染参数验证 ->end
			
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			if(loginUser == null){
				message.setMessage("登录超时");
				message.setSuccess(false);
				return message;
			}else{
				
			}
//			request.getSession().setAttribute("loginUser", loginUser);
			// 验证登录参数 ->end
			
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->start
			int payOrderNonPayment = payOrderService.getCountByUserIdAndStatus(loginUser.getId(), PayState.PAYING);
			if(payOrderNonPayment > PAYINGORDERMAX){
				message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
				message.setSuccess(false);
				return message;
			}
			// 验证(超过10个未支付的订单,则无法再次发起订单) ->end
			
			if (!PAYABLE) {
				message.setMessage("支付功能已经关闭.");
				return message;
			}
			if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
					|| Utils.isBlank(productType)) {
				message.setMessage("传参异常.");
				logger.error("参数 payType,productDesc,productName,productType其中某个或多个为空!");
				return message;
			}
			
			// 金钱处理 ->start(需要改造成通过renderType得到价格)
			String priceInfo="";
//			if (isFreeTime && totalFee == 0){//判断当前时间是否在免费渲染时间段内：如果在免费渲染时间段内，直接把渲染金额置为0
////				totalFee=0;
//				priceInfo="渲染免费";
//			}else{
			if(RenderTypeCode.COMMON_PICTURE_LEVEL == Integer.parseInt(renderingType)){
				totalFee=0;
				priceInfo="照片级渲染免费";
			}else{
				totalFee = 1;
				SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, mode);
				if(sysDictionary == null){
					throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + mode);
				}
				String totalFeeStr = sysDictionary.getAtt1();
				priceInfo = sysDictionary.getName();//金额及尺寸信息
				if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
					totalFee = Integer.parseInt(totalFeeStr);
				}
			}
//			}
			// 金钱处理 ->end
			//判断用户是否需要自动升级
			String tradeType = TradeType.PREDEPOSIT;
			// 直接从账户余额中扣除
			PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
			userId = loginUser.getId();
			SysUser user = sysUserService.get(userId);
			SysTask sysTask =null;
			if (user != null) {
				if (user.getBalanceAmount() < (int)totalFee) {
					message.setMessage("当前用户的账户余额不足.");
				} else {
					try {
						// 插入支付订单
						sysSave(payOrder, request);
						if (userId > 0) {
							payOrder.setUserId(userId);
						}
						payOrder.setBizType(BizType.BUY);
						payOrder.setFinanceType(FinanceType.OUT);
						payOrder.setPayState(PayState.SUCCESS);
						
						// 创建未支付状态的渲染任务 ->start
							sysTask = sysTaskService.createNonPaymentTaskNew(isTurnOn, planId,priority, viewPoint,
								scene, Integer.parseInt(renderingType),priceInfo,loginUser);
						if(sysTask.getId() == null){
							message.setMessage(sysTask.getRemark());
							return message;
						}
						payOrder.setTaskId(sysTask.getId());
						// 创建未支付状态的渲染任务 ->end
						
						int id = payOrderService.add(payOrder);
						if (id == 0) {
							message.setMessage("保存支付单失败.");
							return message;
						}
						// 更新账户余额和消费金额
						updateUserFinance(payOrder.getOrderNo());

						message.setSuccess(true);
						message.setMessage("付款成功.");
						
						// 渲染任务状态变更:未支付->待执行 ->start
						sysTaskService.updateNonPaymentTaskNew(sysTask,loginUser,payType,payOrder.getPayState(),null);
						// 渲染任务状态变更:未支付->待执行 ->end
						//判断用户是否需要自动升级
						sysResLevelCfgService.processAfterConsume(payOrder.getOrderNo());
					} catch (Exception e) {
						logger.error(e);
						e.printStackTrace();
						message.setSuccess(false);
						message.setMessage("预付款支付购买产品发生异常.");
						//TODO  :是否需要考虑退款？？
					}
				}
			} else {
				message.setMessage("登录超时,请重新登录!");
				return message;
			}
			message.setOrderNo(payOrder.getOrderNo());
			message.setTaskId(sysTask.getId());
			//返回渲染类型
			if(sysTask != null){
				if(sysTask.getRenderType() != null){
					message.setRenderingType(sysTask.getRenderType()+"");
				}else{
					logger.error("task id="+sysTask.getId() +" param 'renderType' is null!");							
				}
			}
			return message;
		}
	}
	/**
	 * 5.6版本：微信支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/wxpaynotify")
	public void wxPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("微信支付回调。。。");
		if (!PAYABLE) {
			return;
		}
		try {
			boolean success = false;
			BufferedReader reader = request.getReader();
			String line = "";
			StringBuffer sbResult = new StringBuffer();
			response.reset();
			PrintWriter writer = response.getWriter();
			while ((line = reader.readLine()) != null) {
				sbResult.append(line);
			}
			if (reader != null) {
				reader.close();
			}
			if (sbResult.length() > 0) {
				logger.info("微信回调结果:" + sbResult);
				// 测试回调接口是否会修改渲染任务状态和生成渲染配置文件所以注释,提交解开注释
				ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult.toString(), ResultNotify.class);
				if (result != null) {
					String flag = "SUCCESS";
					PayOrder order = new PayOrder();
					String orderNo = result.getOut_trade_no();
					String openId = result.getOpenid();
					String refId = result.getTransaction_id();
					order.setOrderNo(orderNo);
					order.setOpenId(openId);
					order.setRefId(refId);
					order.setPayState(PayState.PAY_ERROR);
					if (result.getResult_code().equalsIgnoreCase(flag)
							&& result.getReturn_code().equalsIgnoreCase(flag)) {
						success = true;
						order.setPayState(PayState.SUCCESS);
						updateUserFinance(orderNo);
					}
					payOrderService.updatePayState(order);
					//FIXME:如果支付成功就回调修改充值状态
					MgrRechargeLog mgr = mgrRechargeLogMapper.getByOrderNo(orderNo);
					if(mgr != null) {
						SysUser sysUser = sysUserService.get(mgr.getUserId());
						mgrRechargeLogService.saveLogAndUpdateUserBalance(mgr, sysUser);
					}
					
					//判断用户是否需要自动升级
					sysResLevelCfgService.processAfterConsume(orderNo);
					// 渲染任务状态变更:未支付->待执行 ->start
					/*PayOrder payOrder = payOrderService.findOneByOrderNo(order.getOrderNo());
					if(payOrder != null){
						Integer sysTaskId = payOrder.getTaskId();
						if(sysTaskId != null && sysTaskId > 0){
							SysTask sysTask = sysTaskService.get(sysTaskId);
							if(sysTask != null){
								//更新任务状态
								sysTaskService.updateNonPaymentTaskNew(sysTask,null,payOrder.getPayType());
							}else{
								// 处理??
								logger.error("------sysTask为空,sysTask.id:" + sysTaskId);
							}
						}else{
							// 处理??
							// 可能是购买序列号
							logger.error("-----payOrder中的taskId字段为空,payOrder.orderNo:" + order.getOrderNo());
						}
					}else{
						// 处理??
						logger.error("------payOrder为空,payOrder.orderNo:" + order.getOrderNo());
					}*/
					// 渲染任务状态变更:未支付->待执行 ->end
					//给客户端发送订单支付状态信息,更新任务状态，退款
					payOrderService.sendPayStateNew(orderNo);
					updateUserFinance(orderNo);
				}
			}
			if (success) {
				writer.write(XMLParser.getResponseWeixin(true, "OK"));
			} else {
				writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
			}
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 5.6版本：支付宝支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/alipaynotify")
	public void aliPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付宝支付回调。。。 ");
		if (!PAYABLE) {
			return;
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			// 商户订单号
			String orderNo = request.getParameter("out_trade_no");
			// 支付宝交易号
			String tradeNo = request.getParameter("trade_no");
			// 交易状态
			String tradeStatus = request.getParameter("trade_status");
			// 异步通知ID
			String notify_id = request.getParameter("notify_id");
			// sign
			String sign = request.getParameter("sign");
			PrintWriter writer = response.getWriter();

			if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true")) // 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
				{
					PayOrder order = new PayOrder();
					order.setOrderNo(orderNo);
					order.setPayState(PayState.PAY_ERROR);
					order.setRefId(tradeNo);
					logger.info("使用支付宝公钥验签：" + AlipayNotify.getSignVeryfy(params, sign));
					if (AlipayNotify.getSignVeryfy(params, sign)) // 使用支付宝公钥验签
					{
						// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
						if (tradeStatus.equals("TRADE_FINISHED")) {

						} else if (tradeStatus.equals("TRADE_SUCCESS")) {

							order.setPayState(PayState.SUCCESS);
							payOrderService.updatePayState(order);
							//FIXME:如果支付成功就回调修改充值状态
							MgrRechargeLog mgr = mgrRechargeLogMapper.getByOrderNo(orderNo);
							if(mgr != null) {
								SysUser sysUser = sysUserService.get(mgr.getUserId());
								mgrRechargeLogService.saveLogAndUpdateUserBalance(mgr, sysUser);
							}
							
							
							// 给客户端发送支付状态消息
							logger.info("支付结果：" + order.getPayState());
//							payOrderService.sendPayState(orderNo);
							updateUserFinance(orderNo);
							
							//判断用户是否需要自动升级
							sysResLevelCfgService.processAfterConsume(orderNo);
						}
						//给客户端发送订单支付状态信息,更新任务状态，退款
						payOrderService.sendPayStateNew(orderNo);
						writer.print("success");
					}
					// 验证签名失败
					else {
						writer.print("sign fail");
						payOrderService.updatePayState(order);
						// 给客户端发送支付状态消息
						logger.info("支付结果：" + order.getPayState());
						payOrderService.sendPayStateNew(orderNo);
					}
				}
				// 验证是否来自支付宝的通知失败
				else {
					writer.print("response fail");
				}
			} else {
				writer.print("no notify message");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(MgrRechargeLog model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setUserId(loginUser.getId());
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
}
