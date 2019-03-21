package com.nork.pay.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.pay.alipay.util.AlipayNotify;
import com.nork.pay.alipay.util.AlipayRefund;
import com.nork.pay.alipay.util.HttpsRequest;
import com.nork.pay.common.IdGenerator;
import com.nork.pay.metadata.RefundState;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.PayRefund;
import com.nork.pay.model.ResultMessage;
import com.nork.pay.model.search.PayRefundSearch;
import com.nork.pay.model.small.PayRefundSmall;
import com.nork.pay.service.PayOrderService;
import com.nork.pay.service.PayRefundService;

/**
 * @Title: PayRefundController.java
 * @Package com.nork.pay.controller
 * @Description:支付-支付退款Controller
 * @createAuthor pandajun
 * @CreateDate 2016-09-22 14:41:47
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/refund")
public class PayRefundController {
	private static Logger logger = Logger.getLogger(PayRefundController.class);
	private final JsonDataServiceImpl<PayRefund> JsonUtil = new JsonDataServiceImpl<PayRefund>();
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "jsp";
	private final String MAIN = "/" + STYLE + "/pay/payRefund";
	private final String BASEMAIN = "/" + STYLE + "/pay/base/payRefund";
	private final String JSPMAIN = "/" + JSPSTYLE + "/pay/payRefund";

	@Autowired
	private PayOrderService payOrderService;

	@Autowired
	private PayRefundService payRefundService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 支付退款 基础主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 * 支付退款 主页面
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

	/**
	 * 退款申请审核处理
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public Object audit(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id,
			@RequestParam(value = "auditState", required = false, defaultValue = "0") Integer auditState,
			@RequestParam(value = "auditOpinion", required = false) String auditOpinion, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = new ResultMessage();
		if (id == null || id.intValue() == 0) {
			message.setMessage("退款单ID为空.");
			return message;
		}
		try {
			PayRefund payRefund = payRefundService.get(id);
			if (payRefund == null) {
				message.setMessage("退款单不存在,审核失败.");
				return message;
			}
			if (payRefund.getRefundState().equalsIgnoreCase(RefundState.SUCCESS)) {
				message.setMessage("退款单已经成功处理过,无需审核.");
				return message;
			}
			if (payRefund.getRefundState().equalsIgnoreCase(RefundState.CLOSED)) {
				message.setMessage("退款单已经关闭.");
				return message;
			}
			payRefund.setAuditState(auditState);
			payRefund.setAuditOpinion(auditOpinion);
			if (auditState == 1) {
				payRefund.setRefundState(RefundState.REFUNING);
			} else {
				payRefund.setRefundState(RefundState.CLOSED);
			}
			payRefundService.updateAuditState(payRefund);
			if (auditState == 1) {
				String requestUrl = AlipayRefund.getRequestUrl(payRefund);
				HttpsRequest.httpRequest(requestUrl, "GET", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("审核退款时发生异常.");
		}
		return message;
	}

	@RequestMapping(value = "/alipaynotify")
	public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter writer = response.getWriter();
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

			// 批次号
			String batchNo = request.getParameter("batch_no");
			// 批量退款数据中的详细信息
			String resultDetails = request.getParameter("result_details");
			if (AlipayNotify.verifyMd5(params)) {// 验证成功
				PayRefund refund = new PayRefund();
				refund.setRefundNo(batchNo);
				refund.setResultDetails(resultDetails);
				if (resultDetails != null && resultDetails.equalsIgnoreCase("SUCCESS")) {
					refund.setRefundState(RefundState.SUCCESS);
				} else {
					refund.setRefundState(RefundState.FAILED);
				}
				payRefundService.updateRefundState(refund);
				writer.println("success");
			} else {// 验证失败
				writer.println("fail");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 退款申请
	 */
	@RequestMapping(value = "/apply")
	@ResponseBody
	public Object apply(@RequestParam(value = "orderId", required = false, defaultValue = "0") Integer orderId,
			@RequestParam(value = "refundReason", required = false) String refundReason, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = new ResultMessage();
		PayRefund payRefund = new PayRefund();
		if (orderId == null || orderId.intValue() == 0) {
			message.setMessage("订单ID为空.");
			return message;
		}
		PayOrder order = payOrderService.get(orderId);
		if (order == null) {
			message.setMessage("原始支付订单为空,不能申请退款.");
			return message;
		}
		try {
			PayRefundSearch payRefundSearch = new PayRefundSearch();
			payRefundSearch.setOrderId(orderId);
			List<PayRefund> lstFind = payRefundService.getPaginatedList(payRefundSearch);
			if (lstFind != null && lstFind.size() > 0) {
				message.setMessage("该支付订单已经申请过退款,不能再次申请.");
				return message;
			}
			sysSave(payRefund, request);
			payRefund.setRefundState(RefundState.APPLIED);
			payRefund.setOrderId(orderId);
			payRefund.setRefId(order.getRefId());
			payRefund.setRefundDate(new Date());
			payRefund.setTotalFee(order.getTotalFee());
			payRefund.setUserId(order.getUserId());
			payRefund.setRefundNo(IdGenerator.generateNo());
			payRefund.setRefundReason(refundReason);
			int id = payRefundService.add(payRefund);
			if (id <= 0) {
				message.setMessage("保存退款申请发生未知错误,请重新申请.");
				return message;
			} else {
				message.setMessage("成功申请退款,系统会尽快处理您的退款申请.");
				message.setSuccess(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("申请退款发生异常.");
		}
		return message;
	}

	/**
	 * 获取 支付退款详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style, @ModelAttribute("payRefund") PayRefund payRefund,
			HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payRefund = (PayRefund) JsonUtil.getJsonToBean(jsonStr, PayRefund.class);
			if (payRefund == null) {
				return new ResponseEnvelope<PayRefund>(false, "none", "传参异常!");
			}
			id = payRefund.getId();
			msgId = payRefund.getMsgId();
		} else {
			id = payRefund.getId();
			msgId = payRefund.getMsgId();
		}

		if (id == null) {
			return new ResponseEnvelope<PayRefund>(false, "参数缺少id!", msgId);
		}

		try {
			payRefund = payRefundService.get(id);

			if ("small".equals(style) && payRefund != null) {
				String payRefundJson = JsonUtil.getBeanToJsonData(payRefund);
				PayRefundSmall payRefundSmall = new JsonDataServiceImpl<PayRefundSmall>().getJsonToBean(payRefundJson,
						PayRefundSmall.class);

				return new ResponseEnvelope<PayRefundSmall>(payRefundSmall, msgId, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayRefund>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<PayRefund>(payRefund, msgId, true);
	}

	/**
	 * 删除支付退款,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style, @ModelAttribute("payRefund") PayRefund payRefund,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids = "";
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payRefund = (PayRefund) JsonUtil.getJsonToBean(jsonStr, PayRefund.class);
			if (payRefund == null) {
				return new ResponseEnvelope<PayRefund>(false, "传参异常!", "none");
			}
			ids = payRefund.getIds();
			msgId = payRefund.getMsgId();
		} else {
			ids = payRefund.getIds();
			msgId = payRefund.getMsgId();
		}

		if (ids == null) {
			return new ResponseEnvelope<PayRefund>(false, "参数ids不能为空!", msgId);
		}
		int i = 0;
		try {
			if (ids != null) {
				if (ids.contains(",")) {
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = payRefundService.delete(id);
						logger.info("delete:id=" + id);
					}
				} else {
					Integer id = new Integer(ids);
					i = payRefundService.delete(id);
					logger.info("delete:id=" + id);
				}
			}

			if (i == 0) {
				return new ResponseEnvelope<PayRefund>(false, "记录不存在!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayRefund>(false, "删除失败!", msgId);
		}
		return new ResponseEnvelope<PayRefund>(true, msgId, true);
	}

	/**
	 * 支付退款列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style, @ModelAttribute("payRefundSearch") PayRefundSearch payRefundSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		payRefundSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payRefundSearch = (PayRefundSearch) JsonUtil.getJsonToBean(jsonStr, PayRefundSearch.class);
			if (payRefundSearch == null) {
				return new ResponseEnvelope<PayRefund>(false, "传参异常!", "none");
			}
		}

		List<PayRefund> list = new ArrayList<PayRefund>();
		int total = 0;
		try {
			total = payRefundService.getCount(payRefundSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = payRefundService.getPaginatedList(payRefundSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String payRefundJsonList = JsonUtil.getListToJsonData(list);
				List<PayRefundSmall> smallList = new JsonDataServiceImpl<PayRefundSmall>()
						.getJsonToBeanList(payRefundJsonList, PayRefundSmall.class);
				return new ResponseEnvelope<PayRefundSmall>(total, smallList, payRefundSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayRefund>(false, "数据异常!", payRefundSearch.getMsgId());
		}
		return new ResponseEnvelope<PayRefund>(total, list, payRefundSearch.getMsgId());
	}

	/**
	 * 支付退款全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@ModelAttribute("payRefundSearch") PayRefundSearch payRefundSearch, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			payRefundSearch = (PayRefundSearch) JsonUtil.getJsonToBean(jsonStr, PayRefundSearch.class);
			if (payRefundSearch == null) {
				return new ResponseEnvelope<PayRefund>(false, "传参异常!", "none");
			}
		}

		List<PayRefund> list = new ArrayList<PayRefund>();
		int total = 0;
		try {
			total = payRefundService.getCount(payRefundSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = payRefundService.getList(payRefundSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String payRefundJsonList = JsonUtil.getListToJsonData(list);
				List<PayRefundSmall> smallList = new JsonDataServiceImpl<PayRefundSmall>()
						.getJsonToBeanList(payRefundJsonList, PayRefundSmall.class);
				return new ResponseEnvelope<PayRefundSmall>(total, smallList, payRefundSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<PayRefund>(false, "数据异常!", payRefundSearch.getMsgId());
		}
		return new ResponseEnvelope<PayRefund>(total, list, payRefundSearch.getMsgId());
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(PayRefund model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
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
