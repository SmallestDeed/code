package com.nork.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import com.nork.pay.model.RenderFailPayOrder;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.SendEmail;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.WebSocketUtils;
import com.nork.pay.alipay.ScanPayUtil;
import com.nork.pay.common.Config;
import com.nork.pay.common.IdGenerator;
import com.nork.pay.common.QrCodeUtil;
import com.nork.pay.dao.PayOrderMapper;
import com.nork.pay.metadata.BizType;
import com.nork.pay.metadata.FinanceType;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.PayType;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.metadata.ScanPayReqData;
import com.nork.pay.metadata.TradeType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.ResultMessage;
import com.nork.pay.model.SendStateResult;
import com.nork.pay.model.common.OrderSendMessageState;
import com.nork.pay.model.search.PayOrderSearch;
import com.nork.pay.service.PayOrderService;
import com.nork.pay.wexin.metadata.WxTradeType;
import com.nork.pay.wexin.protocol.UnifiedOrderReqData;
import com.nork.pay.wexin.protocol.UnifiedOrderResData;
import com.nork.pay.wexin.service.UnifiedOrderService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.sandu.model.dto.TOrder;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.service.SysTaskService;

/**   
 * @Title: PayOrderServiceImpl.java 
 * @Package com.nork.pay.service.impl
 * @Description:支付-支出凭证ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0   
 */
@Service("payOrderService")
public class PayOrderServiceImpl implements PayOrderService {

	Logger logger = Logger.getLogger(PayOrderServiceImpl.class);
	
	@Autowired
	private PayOrderMapper payOrderMapper;
	
	@Autowired
	AuthorizedConfigService authorizedConfigService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysTaskService sysTaskService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	public void setPayOrderMapper(
			PayOrderMapper payOrderMapper) {
		this.payOrderMapper = payOrderMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param payOrder
	 * @return  int 
	 */
	@Override
	public int add(PayOrder payOrder) {
		payOrderMapper.insertSelective(payOrder);
		return payOrder.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param payOrder
	 * @return  int 
	 */
	@Override
	public int update(PayOrder payOrder) {
		return payOrderMapper
				.updateByPrimaryKeySelective(payOrder);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return payOrderMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  PayOrder 
	 */
	@Override
	public PayOrder get(Integer id) {
		return payOrderMapper.selectByPrimaryKey(id);
	}

	public PayOrder get(String orderNo){
		return payOrderMapper.selectByOrderNo(orderNo);
	}
	/**
	 * 所有数据
	 * 
	 * @param  payOrder
	 * @return   List<PayOrder>
	 */
	@Override
	public List<PayOrder> getList(PayOrder payOrder) {
	    return payOrderMapper.selectList(payOrder);
	}
	
	public int findCount(PayOrderSearch payOrderSearch){
		return payOrderMapper.findCount(payOrderSearch);
	}
	/**
	 *    获取数据数量
	 *
	 * @param  payOrder
	 * @return   int
	 */
	@Override
	public int getCount(PayOrderSearch payOrderSearch){
		return  payOrderMapper.selectCount(payOrderSearch);	
    }
	
	public List<TOrder> findList(PayOrderSearch payOrderSearch){
		return payOrderMapper.findList(payOrderSearch);
	}
	
	/**
	 *    分页获取数据
	 *
	 * @return   List<PayOrder>
	 */
	@Override
	public List<PayOrder> getPaginatedList(
			PayOrderSearch payOrderSearch) {
		return payOrderMapper.selectPaginatedList(payOrderSearch);
	}

	@Override
	public int updatePayState(PayOrder order) {
		// TODO Auto-generated method stub
		return payOrderMapper.updatePayStateByOrderNo(order);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 给客户端发送订单支付状态信息
	 * @param orderNo
	 * @return
	 */
	@Override
	public boolean sendPayState(String orderNo){
		boolean flag = true;
		logger.info("付款完成，给客户端发送支付结果... 订单No：" + orderNo);
		if(StringUtils.isBlank(orderNo) ){
			return false;
		}
		try {
			// 获取订单
			PayOrder payOrder = new PayOrder();
			payOrder.setOrderNo(orderNo);
			List<PayOrder> orderList = this.getList(payOrder);
			if( orderList != null && orderList.size() == 1 ){
				payOrder = orderList.get(0);
			}else{
				logger.info("没有找到订单No为 " +orderNo+ " 的订单信息！");
				return false;
			}
			//序列号支付成功回填
			if(ProductType.AUTHCODE_PURCHASE.equals(payOrder.getProductType()) ||
					ProductType.AUTHCODE_RENEW.equals(payOrder.getProductType()) ||
					ProductType.AUTHCODE_DREDGE.equals(payOrder.getProductType())){
				if( payOrder.getProductId()!=null && payOrder.getProductId().intValue()>0 ){
					AuthorizedConfig authorizedConfig = authorizedConfigService.get(payOrder.getProductId());
					if( authorizedConfig != null ){
						AuthorizedConfig newAuthorizedConfig = new AuthorizedConfig();
						newAuthorizedConfig.setId(authorizedConfig.getId());
						newAuthorizedConfig.setPayStatusValue(1);
						authorizedConfigService.update(newAuthorizedConfig);
					}
				}else{
					logger.info("该订单业务Id为 " +payOrder.getProductId()+ " ！");
					return false;
				}
			}
			// 获取产生订单的客户端
			Session webSocketSession = null;
			logger.info("payOrder.getPayState:"+ payOrder.getPayState());
			if( payOrder.getPayState().equals(PayState.PAY_ERROR) || payOrder.getPayState().equals(PayState.SUCCESS)){
				// 组装发给客户端的信息
				ResponseEnvelope result = new ResponseEnvelope();
				SendStateResult sendStateResult = new SendStateResult();
				sendStateResult.setOrderNo(payOrder.getOrderNo());
				sendStateResult.setPayState(payOrder.getPayState());
				if( payOrder.getPayState().equals(PayState.SUCCESS) ){
					result.setSuccess(true);
				}else if(payOrder.getPayState().equals(PayState.PAY_ERROR)){
					result.setSuccess(false);
				}
				result.setObj(sendStateResult);
				JSONObject jsonObject = JSONObject.fromObject(result);
				// 发送消息
				logger.info("发送消息：" + jsonObject.toString());
				try {
					WebSocketUtils.sendMessage("app.webSocket.payOrder", payOrder.getId().toString(), jsonObject.toString());
				}catch(Exception e){
					logger.error("payOrder websocket链接异常:"+e);
					// 发送邮件
					List<SysUser> warningUserList = sysUserService.getUserByRoleCode("RENDER_WARNING");
					if( warningUserList != null && warningUserList.size() > 0 ){
						StringBuffer toEmailsStr = new StringBuffer();
						int count = 0;
						for( SysUser warningUser : warningUserList ){
							if( StringUtils.isNotBlank(warningUser.getEmail()) ) {
								if( count < warningUserList.size() ) {
									toEmailsStr.append(warningUser.getEmail() + ",");
								}else{
									toEmailsStr.append(warningUser.getEmail());
								}
								count++;
							}
						}
						if( toEmailsStr.length() > 0 ) {
							String[] toEmails = toEmailsStr.toString().split(",");
							StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
							stringBuffer.append("websocket服务器可能已中断," + WebSocketUtils.webSocket.getString("app.webSocket.payOrder"));
							String subject = "【websocket链接异常】";
							SendEmail.massSend(toEmails, subject, stringBuffer.toString());
						}
					}else{
						logger.error("warningUserList is null");
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	/**
	 * 5.6版本：给客户端发送订单支付状态信息,更新任务状态，退款
	 * @param orderNo
	 * @return
	 */
	@Override
	public boolean sendPayStateNew(String orderNo){
		boolean flag = true;
		logger.error("付款完成，给客户端发送支付结果... 订单No：" + orderNo);
		if(StringUtils.isBlank(orderNo) ){
			return false;
		}
		try {
			// 获取订单
			PayOrder payOrder = new PayOrder();
			payOrder.setOrderNo(orderNo);
			List<PayOrder> orderList = this.getList(payOrder);
			if( orderList != null && orderList.size() == 1 ){
				payOrder = orderList.get(0);
			}else{
				logger.error("没有找到订单No为 " +orderNo+ " 的订单信息！");
				return false;
			}
			//序列号支付成功回填
			if(ProductType.AUTHCODE_PURCHASE.equals(payOrder.getProductType()) ||
					ProductType.AUTHCODE_RENEW.equals(payOrder.getProductType()) ||
					ProductType.AUTHCODE_DREDGE.equals(payOrder.getProductType())){
				if( payOrder.getProductId()!=null && payOrder.getProductId().intValue()>0 ){
					AuthorizedConfig authorizedConfig = authorizedConfigService.get(payOrder.getProductId());
					if( authorizedConfig != null ){
						AuthorizedConfig newAuthorizedConfig = new AuthorizedConfig();
						newAuthorizedConfig.setId(authorizedConfig.getId());
						newAuthorizedConfig.setPayStatusValue(1);
						authorizedConfigService.update(newAuthorizedConfig);
					}
				}else{
					logger.info("该订单业务Id为 " +payOrder.getProductId()+ " ！");
					return false;
				}
			}
			// 获取产生订单的客户端
			logger.info("payOrder.getPayState:"+ payOrder.getPayState());
			if( payOrder.getPayState().equals(PayState.PAY_ERROR) || payOrder.getPayState().equals(PayState.SUCCESS)){
				//获取订单关联的任务
				SysTask sysTask = null;
				if(payOrder.getTaskId() != null && payOrder.getTaskId() > 0){
					sysTask = sysTaskService.get(payOrder.getTaskId());
				}else{
					
				}
				// 组装发给客户端的信息
				ResponseEnvelope result = new ResponseEnvelope();
				SendStateResult sendStateResult = new SendStateResult();
				sendStateResult.setOrderNo(payOrder.getOrderNo());
				sendStateResult.setPayState(payOrder.getPayState());
				//如果该订单有关联任务，则从任务表中查询出渲染类型
				if(sysTask != null){
					if(sysTask.getRenderType() != null ){
						sendStateResult.setRenderingType(sysTask.getRenderType()+"");
					}else{
						logger.error("task id="+sysTask.getId()+"  param 'renderType' is null！");
					}
				}else{
					//处理？可能是序列号支付
				}
				
				if( payOrder.getPayState().equals(PayState.SUCCESS) ){
					result.setSuccess(true);
				}else if(payOrder.getPayState().equals(PayState.PAY_ERROR)){
					result.setSuccess(false);
				}else{
					
				}
				result.setObj(sendStateResult);
				JSONObject jsonObject = JSONObject.fromObject(result);
				
				// 发送消息
				logger.info("发送消息：" + jsonObject.toString());
				try {
					WebSocketUtils.sendMessage("app.webSocket.payOrder", payOrder.getId().toString(), jsonObject.toString());
					//消息发送成功，更新任务状态
					if(sysTask != null){
						//更新任务状态:由待渲染状态》渲染中
						Boolean msgSendIsSuccess = Boolean.TRUE;//消息是否发送成功
						sysTaskService.updateNonPaymentTaskNew(sysTask,null,payOrder.getPayType(),payOrder.getPayState(),msgSendIsSuccess);
					}else{
						//处理？可能是序列号支付
					}
				}catch(Exception e){
					logger.error("payOrder websocket链接异常:"+e);
					//消息发送失败，更新任务状态,退款
					if(sysTask != null){
						//更新任务状态:由待渲染状态》渲染中
						Boolean msgSendIsSuccess = Boolean.FALSE;//消息是否发送成功
						sysTaskService.updateNonPaymentTaskNew(sysTask,null,payOrder.getPayType(),payOrder.getPayState(),msgSendIsSuccess);
					}else{
						//处理？可能是序列号支付
					}
					//websocket异常：发送预警邮件
					this.sendEmail();
					
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	/**
	 * 更新任务状态：渲染失败
	 * add by yanghz
	 * @param msg 
	 * @param sysTask 
	 */
	public void updateTaskFaildState(SysTask sysTask, String msg){
		SysTask task = new SysTask();
		task.setId(sysTask.getId());
		task.setState(SysTaskStatus.RENDER_FAILD);
		task.setRemark(msg);
		sysTaskService.update(task);
		
	}
	
	public void sendEmail(){
		// 发送邮件
		List<SysUser> warningUserList = sysUserService.getUserByRoleCode("RENDER_WARNING");
		if( warningUserList != null && warningUserList.size() > 0 ){
			StringBuffer toEmailsStr = new StringBuffer();
			int count = 0;
			for( SysUser warningUser : warningUserList ){
				if( StringUtils.isNotBlank(warningUser.getEmail()) ) {
					if( count < warningUserList.size() ) {
						toEmailsStr.append(warningUser.getEmail() + ",");
					}else{
						toEmailsStr.append(warningUser.getEmail());
					}
					count++;
				}
			}
			if( toEmailsStr.length() > 0 ) {
				String[] toEmails = toEmailsStr.toString().split(",");
				StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
				stringBuffer.append("websocket服务器可能已中断," + WebSocketUtils.webSocket.getString("app.webSocket.payOrder"));
				String subject = "【websocket链接异常】";
				SendEmail.massSend(toEmails, subject, stringBuffer.toString());
			}
		}else{
			logger.error("warningUserList is null");
		}
	}
	public ResultMessage sendMessageAndCreateOrder(Integer productId, String productType, String productName,
			String productDesc, String payType, Integer userId, String msgId, HttpServletRequest request,
			ResultMessage message, String level, Integer isTurnOn, Integer planId, String params, Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType, String temporaryPic, String type, Integer mode,Integer renderChanne) {
		String tradeType = TradeType.SCANCODE;
		try {
			/*if (totalFee == 0) {
				totalFee = Config.getScan_total_fee();
			}*/
			// 金钱处理 ->start(需要改造成通过renderType得到价格)
			/*int totalFee = 100;*/
			int totalFee = 1;
			SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, mode);
			if(sysDictionary == null){
				throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + mode);
			}
			String totalFeeStr = sysDictionary.getAtt1();
			String priceInfo = sysDictionary.getName();//金额及尺寸信息
			if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
				totalFee = Integer.parseInt(totalFeeStr);
			}
			// 金钱处理 ->end
			PayOrder payOrder = getOrder(new Double(totalFee).intValue(), payType, productId, productType, productName, productDesc, tradeType);
			ScanPayReqData reqData = new ScanPayReqData();
			sysSave(payOrder, request);
			if (userId > 0) {
				payOrder.setUserId(userId);
			}
			payOrder.setBizType(BizType.BUY);
			payOrder.setFinanceType(FinanceType.OUT);
			
			// 创建未支付状态的渲染任务 ->start
			SysTask sysTask = sysTaskService.createNonPaymentTask(isTurnOn, planId, params, priority, viewPoint,
					scene, renderingType,renderChanne,priceInfo,request);
			if(sysTask.getId() == null){
				message.setMessage(sysTask.getRemark());
				return message;
			}
			payOrder.setTaskId(sysTask.getId());
			// 创建未支付状态的渲染任务 ->end
			
			int id = this.add(payOrder);
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
					this.update(payOrder);
					message.setMessage("成功生成扫描预付订单");
					message.setObj(reqData);
					message.setSuccess(true);
				}
			}
			if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
				// 设置支付超时时间
				payOrder.setTimeoutExpress("30m");
				message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
				message.setObj(reqData);
				if (message.isSuccess()) {
					message.setMessage("成功生成扫描预付订单");
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(reqData.getQrCodePath());
					this.update(payOrder);
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
				this.update(payOrder);
				message.setMsgId(msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("下单异常.");
		}
		return message;
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
		payOrder.setProductName(productName);
		if(productType.equals(ProductType.COMMON_RENDER)){
			payOrder.setProductName(ProductType.COMMON_RENDER_NAME);
		}else if(productType.equals(ProductType.HD_RENDER)){
			payOrder.setProductName(ProductType.HD_RENDER_NAME);
		}else if(productType.equals(ProductType.UHD_RENDER)){
			payOrder.setProductName(ProductType.UHD_RENDER_NAME);
		}else if(productType.equals(ProductType.AUTHCODE_PURCHASE)){
			payOrder.setProductName(ProductType.AUTHCODE_PURCHASE_NAME);
		}else if(productType.equals(ProductType.AUTHCODE_RENEW)) {
			payOrder.setProductName(ProductType.AUTHCODE_RENEW_NAME);
		}else if(productType.equals(ProductType.AUTHCODE_DREDGE)){
			payOrder.setProductName(ProductType.AUTHCODE_DREDGE_NAME);
		}else if(productType.equals(ProductType.PANORAMA_RENDER)){
			payOrder.setProductName(ProductType.PANORAMA_RENDER_NAME);
		}
		payOrder.setProductDesc(productDesc);
		payOrder.setTradeType(tradeType);
		return payOrder;
		
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
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(PayOrder model, LoginUser loginUser) {
		if (model != null) {
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

	@Override
	public PayOrder findOneByOrderNo(String orderNo) {
		return payOrderMapper.findOneByOrderNo(orderNo);
	}

	@Override
	public int getCountByUserIdAndStatus(Integer userId, String status) {
		return payOrderMapper.getCountByUserIdAndStatus(userId, status);
	}

	@Override
	public PayOrder findOneByTaskId(Integer taskId) {
		PayOrderSearch payOrderSearch = new PayOrderSearch();
		payOrderSearch.setStart(0);
		payOrderSearch.setLimit(1);
		payOrderSearch.setTaskId(taskId);
		List<PayOrder> payOrderList = this.getPaginatedList(payOrderSearch);
		if(payOrderList != null && payOrderList.size() > 0){
			return payOrderList.get(0);
		}
		return null;
	}

	/**
	 * 5.6版本：获取扫码支付信息（生成二维码）
	 */
	@Override
	public ResultMessage getPayScanOrderUrlInfo(Integer totalFee, String payType, Integer productId, String productType, String productName, String productDesc, LoginUser loginUser,String msgId) {
		ResultMessage message = new ResultMessage();
		String tradeType = TradeType.SCANCODE;
		try {
			if (totalFee == 0) {
				totalFee = Config.getScan_total_fee();
			}
			PayOrder payOrder = getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
			ScanPayReqData reqData = new ScanPayReqData();
			sysSave(payOrder, loginUser);
			if (loginUser  != null) {
				payOrder.setUserId(loginUser.getId());
			}
			payOrder.setBizType(BizType.BUY);
			payOrder.setFinanceType(FinanceType.OUT);
			int id = this.add(payOrder);
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
					String codeUrl = QrCodeUtil.generateQrCode(wx_codeUrl, payOrder.getOrderNo());
					/*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/
					
					/*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));*/
					reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
					payOrder.setPrepayId(prepayId);
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(codeUrl);
					this.update(payOrder);
					message.setMessage("成功生成扫描预付订单");
					message.setObj(reqData);
					message.setSuccess(true);
				}
			}else if(payType.equalsIgnoreCase(PayType.ALIPAY)){
				message = ScanPayUtil.addScanpayOrder(payOrder,reqData);
				message.setObj(reqData);
				if (message.isSuccess()) {
					message.setMessage("成功生成扫描预付订单");
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(reqData.getQrCodePath());
					this.update(payOrder);
				}
				message.setMsgId(msgId);
			}else if (payType.equalsIgnoreCase(PayType.PREDEPOSIT)){//余额支付
				message.setSuccess(true);
				message.setMessage("余额下订单成功!");
				//message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
				message.setObj(reqData);
				payOrder.setPayState(PayState.PAYING);
				payOrder.setCodeUrl("");
				this.update(payOrder);
				message.setMsgId(msgId);
			}else{
				message.setMessage("未知支付类型");
				logger.error("未知支付类型");
			}
			//TODO  临时处理余额支付
		} catch (Exception e) {
			logger.error(e);
			message.setSuccess(false);
			message.setMessage("下单异常.");
		}
		return message;
	}

	//5.6版本：创建任务和支付二维码
	@Override
	public ResultMessage sendMessageAndCreateOrderNew(Integer productId, String productType, String productName,
			String productDesc, String payType, Integer userId, String msgId,
			ResultMessage message, String level, Integer isTurnOn, Integer planId, Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String type, Integer mode,LoginUser loginUser) {
		String tradeType = TradeType.SCANCODE;
		try {
			int totalFee = 0;
			SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, mode);
			if(sysDictionary == null){
				throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + mode);
			}
			String totalFeeStr = sysDictionary.getAtt1();
			String priceInfo = sysDictionary.getName();//金额及尺寸信息
			if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
				totalFee = Integer.parseInt(totalFeeStr);
			}
			// 金钱处理 ->end
			PayOrder payOrder = this.getOrder(new Double(totalFee).intValue(), payType, productId, productType, productName, productDesc, tradeType);
			ScanPayReqData reqData = new ScanPayReqData();
			sysSave(payOrder, loginUser);
			if (userId > 0) {
				payOrder.setUserId(userId);
			}
			payOrder.setBizType(BizType.BUY);
			payOrder.setFinanceType(FinanceType.OUT);
			
			// 创建未支付状态的渲染任务 ->start
			SysTask sysTask = sysTaskService.createNonPaymentTaskNew(isTurnOn, planId, priority, viewPoint,
					scene, renderingType,priceInfo,loginUser);
			if(sysTask.getId() == null){
				message.setMessage(sysTask.getRemark());
				return message;
			}
			payOrder.setTaskId(sysTask.getId());
			// 创建未支付状态的渲染任务 ->end
			
			int id = this.add(payOrder);
			if (id == 0) {
				message.setMessage("保存支付单失败.");
				return message;
			}
			payOrder.setId(id);
			reqData.setOrderId(payOrder.getId());
			reqData.setOrderNo(payOrder.getOrderNo());
			reqData.setTaskId(sysTask.getId());
			
			List<Integer> orderIdList = new ArrayList<Integer>();
			List<String> npaidOrderList = new ArrayList<>();
			PayOrder payOrder1 = new PayOrder();
			payOrder1.setNuma1(OrderSendMessageState.WAIT_NOTICE);
			payOrder1.setIsDeleted(1);
			//查询超时未支付订单
			List<PayOrder> list = this.getList(payOrder1 );
			if(list != null && list.size() > 0){
				for (PayOrder temp : list) {
					if(StringUtils.isNotBlank(temp.getOrderNo())){
						npaidOrderList.add(temp.getOrderNo());
						orderIdList.add(temp.getId());
					}else{
						logger.error("订单id="+temp.getId()+"订单号为空");
					}
				}
			}
			reqData.setNpaidOrderList(npaidOrderList);
			if(orderIdList != null && orderIdList.size() > 0){
				this.deleteByIdList(orderIdList, "半小时未支付订单清理通知App端成功", OrderSendMessageState.NOTICE_SUCESS);
			}
			
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
					String codeUrl = QrCodeUtil.generateQrCode(wx_codeUrl, payOrder.getOrderNo());
					/*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/
					
					/*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));*/
					reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
					payOrder.setPrepayId(prepayId);
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(codeUrl);
					this.update(payOrder);
					message.setMessage("成功生成扫描预付订单");
					message.setObj(reqData);
					message.setSuccess(true);
				}
			}
			if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
				// 设置支付超时时间
				payOrder.setTimeoutExpress("30m");
				message = ScanPayUtil.addScanpayOrder(payOrder, reqData);
				message.setObj(reqData);
				if (message.isSuccess()) {
					message.setMessage("成功生成扫描预付订单");
					payOrder.setPayState(PayState.PAYING);
					payOrder.setCodeUrl(reqData.getQrCodePath());
					this.update(payOrder);
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
				this.update(payOrder);
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
	 * 5.6版本：渲染失败退款到账户余额
	 * @param sysTask
	 */
	public void renderRefund(SysTask sysTask,String renderErroMsg){
		if( sysTask != null ){
			// 找到渲染任务的订单
			List<PayOrder> payOrderList = new ArrayList<>();
			PayOrder order = null;
			// 修改为用payOrder的taskId字段关联
			if(sysTask != null && sysTask.getId() != null){
				order = this.findOneByTaskId(sysTask.getId());
			    payOrderList.add(order);
			}
			PayOrder payOrder=null;
			if( payOrderList != null && payOrderList.size() > 0){
				payOrder = payOrderList.get(0);
				// 付款成功的才会退款
				if( PayState.SUCCESS.equals(payOrder.getPayState()) ) {
					Double totalFee = new Double(payOrder.getTotalFee());// 渲染扣除金额（分）
					logger.info("refund totalFee :" + totalFee);
					// 更新账户余额
					SysUser sysUser = new SysUser();
					sysUser.setId(payOrder.getUserId());
					sysUser.setBalanceAmount(totalFee);
					sysUser.setConsumAmount(-totalFee);
					sysUserService.updateFinance(sysUser);
					
					//更新订单状态
					PayOrder payOrderNew = new PayOrder();
					payOrderNew.setId(payOrder.getId());
					payOrderNew.setPayState(PayState.REFUND_SUCCESS);
					this.update(payOrderNew );
				}else{
					logger.error("order has not been paid successfully... orderNo:"+payOrder.getOrderNo());
				}
				// 发送渲染失败告警邮件
				StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
				stringBuffer.append("  有渲染任务异常结束。渲染任务编码：<br>");
				stringBuffer.append("<font color=\"red\"><strong>"+sysTask.getBusinessCode()+"</strong></font><br>");

                SysDictionary sysDictionary = null;
				if (sysTask.getCreator() != null){
					stringBuffer.append("  用户名:"+sysTask.getCreator()+"<br>");
					List<SysUser> oneByLoginName = sysUserService.findOneByLoginName(sysTask.getCreator());
					if (oneByLoginName != null && oneByLoginName.size() >0){
					  sysDictionary =sysDictionaryService.getSysDictionaryByValue("userType", oneByLoginName.get(0).getUserType());
                    }else{
                      logger.error("未查到该用户："+sysTask.getCreator());
                    }
					}
					if (sysDictionary != null){
					  stringBuffer.append("  用户类型："+sysDictionary.getName()+"<br>");
                    }
					String webSocketUrl = WebSocketUtils.webSocket.getString("app.webSocket.message");
					stringBuffer.append("webSocketUrl：" + webSocketUrl);
					stringBuffer.append("异常信息："+renderErroMsg);
					String subject = "【渲染异常告警】";
					SendEmail.sendEmailForRenderWarning(subject, stringBuffer.toString());//发送邮件
					logger.info("有渲染任务异常结束。渲染任务编码："+sysTask.getBusinessCode());
			}else{
				logger.error("not found payOrder... orderNo:"+payOrder.getOrderNo());
			}
		}
	}

    @Override
    public PayOrder getConsumeSumByOrderNum(String orderNum) {
		if (StringUtils.isEmpty(orderNum)){
			return null;
		}
		return payOrderMapper.getConsumeSumByOrderNum(orderNum);
    }

    /**
	 * 删除超时订单(超时半小时删除)
	 * @author huangsongbo
	 */
	public void deleteTimeOutOrder(){
		// 找出需要删除的订单 ->start
		// 得到当前时间推后半小时的时间
		logger.info("------正在删除超时未支付的订单");
		Date date = Utils.getLateTime(-30);
		List<PayOrder> payOrderList = findAllByStatusAndLessOrderTime("PAYING", date);
		// 找出需要删除的订单 ->end
		if(payOrderList == null || payOrderList.size() == 0){
			return;
		}
		// 删除对应的sysTask任务 ->start
		List<Integer> orderIdList = new ArrayList<Integer>();
		List<Integer> taskIdList = new ArrayList<Integer>();
		for(PayOrder payOrder : payOrderList){
			orderIdList.add(payOrder.getId());
			if(payOrder.getTaskId() != null){
				orderIdList.add(payOrder.getTaskId());
			}
		}
		Map<String, Object> map = new HashMap<>();
		String remark = "未支付订单超半小时自动清理";
		map.put("idList", taskIdList);
		if(taskIdList != null && taskIdList.size() > 0){
			sysTaskService.deleteByIdList(taskIdList,remark);
		}
		// 删除对应的sysTask任务 ->end
		// 删除订单
		if(orderIdList != null && orderIdList.size() > 0){
			Integer numa1 = OrderSendMessageState.WAIT_NOTICE;
			this.deleteByIdList(orderIdList,remark,numa1);
		}
	}

	private void deleteByIdList(List<Integer> orderIdList, String remark, Integer numa1) {
		payOrderMapper.deleteByIdList(orderIdList,remark,numa1);
	}

	private List<PayOrder> findAllByStatusAndLessOrderTime(String status, Date date) {
		PayOrderSearch payOrderSearch = new PayOrderSearch();
		payOrderSearch.setStart(-1);
		payOrderSearch.setLimit(-1);
		payOrderSearch.setPayState(status);
		payOrderSearch.setOrderDateLess(date);
		payOrderSearch.setIsDeleted(0);
		return this.getPaginatedList(payOrderSearch);
	}

	/**
	 * add by xiaoxc
	 * 查询支付成功渲染失败的数据
	 * @return
	 */
	@Override
	public List<RenderFailPayOrder> findRenderFailList(Integer times) {
		return payOrderMapper.findRenderFailList(times);
	}
}
