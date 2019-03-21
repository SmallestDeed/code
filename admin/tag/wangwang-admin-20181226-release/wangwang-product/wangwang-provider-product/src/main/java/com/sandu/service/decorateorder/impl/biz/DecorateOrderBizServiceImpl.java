package com.sandu.service.decorateorder.impl.biz;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sandu.api.decoratecustomer.model.DecorateCustomer;
import com.sandu.api.decoratecustomer.service.DecorateCustomerService;
import com.sandu.api.decorateorder.constant.OrderEnum;
import com.sandu.api.decorateorder.input.*;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.service.DecorateOrderService;
import com.sandu.api.decorateorder.service.biz.DecorateOrderBizService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.pay.model.PayOrder;
import com.sandu.api.pay.service.PayAccountService;
import com.sandu.api.pay.service.PayOrderService;
import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.api.sysusermessage.service.SysUserMessageService;
import com.sandu.api.user.model.User;
import com.sandu.api.user.service.UserService;
import com.sandu.common.pay.IdGenerator;
import com.sandu.commons.Utils;
import com.sandu.constant.ResponseEnum;
import com.sandu.exception.ServiceException;
import com.sandu.util.SocketIOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * <p>DecorateOrder
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Slf4j
@Service("decorateOrderBizService")
public class DecorateOrderBizServiceImpl implements DecorateOrderBizService {

	@Autowired
	private DecorateOrderService decorateOrderService;

	@Autowired
	private DecorateCustomerService decorateCustomerService;

	@Autowired
	private SysUserMessageService sysUserMessageService;

	@Autowired
	private PayAccountService payAccountService;

	@Autowired
	private PayOrderService payOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private DictionaryService dictionaryService;

	@Value("${contract.approve.success}")
	private String contractApproveSuccess;

	@Value("${contract.approve.fail}")
	private String contractApproveFail;

	@Value("${refund.approve.success}")
	private String refundApproveSuccess;

	@Value("${refund.approve.fail}")
	private String refundApproveFail;

	@Value("${decorate.message.system.innerSet}")
	private String innerSetMessage;

	public static final String PRODUCT_TYPE_REFUND = "contractApprroveRefund";
	public static final String PRODUCT_TYPE_INNER_RECOMMEND = "innerRecommend";
	public static final String REFUND_BIZ = "refund";

	public static Gson gson = new Gson();

	@Override
	public int create(DecorateOrderAdd input) {
		DecorateOrder.DecorateOrderBuilder builder = DecorateOrder.builder();

		DecorateCustomer customer = decorateCustomerService.getById(input.getCustomerId());
		// 设置内部推荐企业
		customer.setInnerCompanyId(input.getCompanyId());
		customer.setGmtModified(new Date());
		customer.setModifier(input.getUser().getId().toString());

		// 扣度币
		Dictionary budget =
				dictionaryService.getByTypeAndValue("decorateBudget", customer.getDecorateBudget());
		if (budget == null || budget.getNumAtt3() == null) {
			throw new RuntimeException("获取客户原客单价失败,customer:{}" + customer);
		}
		Integer price = 100 + budget.getNumAtt3().intValue();
		// 扣费 通知
		Map<User, Boolean> payResult = doDeductionWithCompany(input.getCompanyId(), price);
		User user = payResult.keySet().stream().filter(Objects::nonNull).findFirst().get();
		// 生成订单
		Date date = new Date();
		builder
				.customerId(customer.getId())
				.proprietorInfoId(customer.getProprietorInfoId())
				.companyId(input.getCompanyId())
				.orderType(DecorateOrder.ORDER_TYPE_INNER_SET)
				.orderStatus(payResult.get(user) ? 2 : 0)
				.price(price + "")
				.userId(user.getId())
				.creator(input.getUser().getId().toString())
				.modifier(input.getUser().getId().toString())
				.gmtCreate(date)
				.gmtModified(date)
				.isDeleted(0);

		decorateCustomerService.update(customer);
		return decorateOrderService.insert(builder.build());
	}

	@Override
	public int update(DecorateOrderUpdate input) {
		DecorateOrder.DecorateOrderBuilder builder = DecorateOrder.builder();
		DecorateOrder DecorateOrder = builder.build();
		BeanUtils.copyProperties(input, DecorateOrder);
		// 转换原字段ID
		DecorateOrder.setId(input.getId());
		return decorateOrderService.update(DecorateOrder);
	}

	@Override
	public int delete(String DecorateOrderId) {
		if (Strings.isNullOrEmpty(DecorateOrderId)) {
			return 0;
		}
		Set<Integer> DecorateOrderIds = new HashSet<>();
		List<String> list = Arrays.asList(DecorateOrderId.split(","));
		list.forEach(id -> DecorateOrderIds.add(Integer.valueOf(id)));

		if (DecorateOrderIds.size() == 0) {
			return 0;
		}
		return decorateOrderService.delete(DecorateOrderIds);
	}

	@Override
	public DecorateOrder getById(int DecorateOrderId) {
		return decorateOrderService.getById(DecorateOrderId);
	}

	@Override
	public PageInfo<DecorateOrder> query(DecorateOrderQuery query) {
		return decorateOrderService.findAll(query);
	}

	@Override
	public int uploadContract(DecorateOrderUpdate input, Integer userId, Integer orderUserId) {
		DecorateOrder decorateOrder = new DecorateOrder();
		try {
			SysUserMessage message = null;
			decorateOrder.setContractFee(input.getContractFee() + "");
			decorateOrder.setServiceFee(input.getServiceFee() + "");
			decorateOrder.setContractId(input.getResFileId());
			decorateOrder.setId(input.getId());
			decorateOrder.setContractUploadUserId(userId);
			decorateOrder.setContractUploadTime(new Date());
			decorateOrder.setContractStatus(1);
			decorateOrder.setOrderStatus(7);
			User user = userService.selectById(Long.valueOf(orderUserId));
			if (user == null) {
				throw new ServiceException(ResponseEnum.ERROR, "用户信息非法");
			}
			if (StringUtils.isEmpty(user.getUuid())) {
				throw new ServiceException(ResponseEnum.ERROR, "用户ID:" + orderUserId + ",uuid信息为空!");
			}

			// 商家后台上传直接审核通过并变成财务待收款
			if (input.getPlatformType() == 0) {
				decorateOrder.setContractApproveTime(new Date());
				decorateOrder.setContractApproveUserId(userId);
				decorateOrder.setFinanceReceiptsStatus(0);
				decorateOrder.setContractStatus(2);
			}
			// 新增系统消息
			if (decorateOrder.getContractStatus() == 2) {
				message =
						insertSysUserMessage(
								"合同审核通过", contractApproveSuccess, userId, decorateOrder.getId(), orderUserId);
			}

			// 消息通知
			if (message != null) {
				log.info("updateRefund开始推送消息,开始时间{}", DateUtil.formatDate(new Date()));
				String messageInfo = gson.toJson(message);
				log.info("updateRefund消息体{}", messageInfo);
				SocketIOUtil.handlerSendMessage(
						SocketIOUtil.IM_PUSH_MSG_EVENT,
						SocketIOUtil.IM_PUSH_SYSTEM_MSG_CODE,
						user.getUuid(),
						messageInfo);
			}
		} catch (Exception e) {
			log.error("uploadContract修改合同失败:{}", e);
		}

		return decorateOrderService.uploadOrder(decorateOrder);
	}

	/**
	 * 创建系统消息
	 *
	 * @param title
	 * @param content
	 * @param createUserId
	 * @param businessId
	 * @param userId
	 */
	@Override
	public SysUserMessage insertSysUserMessage(
			String title, String content, Integer createUserId, Integer businessId, Integer userId) {
		log.info("content内容:{}", content);
		SysUserMessage message = new SysUserMessage();
		message.setTitle(title);
		message.setContent(content);
		message.setCreator(String.valueOf(createUserId));
		message.setGmtCreate(new Date());
		message.setModifier(String.valueOf(createUserId));
		message.setGmtModified(new Date());
		message.setTaskId(businessId);
		message.setIsDeleted(0);
		message.setMessageType(Byte.valueOf("2"));
		message.setUserId(userId);
		message.setStatus(Byte.valueOf("1"));
		message.setIsRead(Byte.valueOf("0"));
		message.setPlatformId(1L);
		log.info("message内容:{}", gson.toJson(message));
		sysUserMessageService.insertSysUserMessage(message);
		return message;
	}

	@Override
	public int updateReceipt(DecorateOrderReceiptUpdate input, Integer userId) {
		DecorateOrder.DecorateOrderBuilder builder = DecorateOrder.builder();
		DecorateOrder decorateOrder = builder.build();
		BeanUtils.copyProperties(input, decorateOrder);
		decorateOrder.setFinanceReceiptsStatus(input.getFinanceReceiptsStatus());
		decorateOrder.setFinanceReceiptsTime(new Date());
		if (input.getFinanceReceiptsStatus() == 1) {
			decorateOrder.setOrderStatus(OrderEnum.SIX.getCode());
		}
		return decorateOrderService.uploadOrder(decorateOrder);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateRefund(DecorateOrderRefundUpdate input, Integer userId, DecorateOrder order)
			throws ServiceException {
		log.info("order信息:{}", order);
		try {
			String refundApproveSuccess = "";
			DecorateOrder.DecorateOrderBuilder builder = DecorateOrder.builder();
			DecorateOrder decorateOrder = builder.build();
			BeanUtils.copyProperties(input, decorateOrder);
			SysUserMessage message = null;
			User user = userService.selectById(Long.valueOf(order.getUserId()));
			if (user == null) {
				throw new ServiceException(ResponseEnum.ERROR, "用户信息非法");
			}
			if (StringUtils.isEmpty(user.getUuid())) {
				throw new ServiceException(ResponseEnum.ERROR, "用户ID:" + order.getUserId() + ",uuid信息为空!");
			}

			if (input.getOperateType() != null) {
				switch (input.getOperateType()) {
					case 0:
						decorateOrder.setRefundApproveTime(new Date());
						decorateOrder.setRefundApproveUserId(userId);
						decorateOrder.setRefundStatus(1);
						log.info("退款金额:{}", (int) Double.parseDouble(order.getPrice()) * 10);
						refundApproveSuccess =
								"根据平台审核，您的情况符合退款规则,已将"
										+ (int) Double.parseDouble(order.getPrice()) * 10
										+ "度币退回到你的账户,请注意查收";
            /*refundApproveSuccess =
            MessageFormat.format(refundApproveSuccess, (int) Double.parseDouble(order.getPrice()) * 10);*/
						break;
					case 1:
						decorateOrder.setRefundRejectReason(input.getRefundRejectReason());
						decorateOrder.setRefundRejectTime(new Date());
						decorateOrder.setRefundRejectUserId(userId);
						decorateOrder.setRefundStatus(2);
						refundApproveFail =
								MessageFormat.format(refundApproveFail, input.getRefundRejectReason());
						break;
					default:
						break;
				}
			}
			log.info("refundApproveSuccess信息:{}", refundApproveSuccess);
			log.info("refundApproveFail信息:{}", refundApproveFail);
			// 处理系统消息
			switch (decorateOrder.getRefundStatus()) {
				case 1:
					message =
							insertSysUserMessage(
									"退款完成", refundApproveSuccess, userId, decorateOrder.getId(), order.getUserId());
					break;
				case 2:
					message =
							insertSysUserMessage(
									"退款驳回", refundApproveFail, userId, decorateOrder.getId(), order.getUserId());
					break;
				default:
					break;
			}
			// 退款审核通过，则退回度币
			if (decorateOrder.getRefundStatus() != null && decorateOrder.getRefundStatus() == 1) {
				Map<String, Object> resultMap =
						payAccountService.updatePayAccount(
								order.getUserId(),
								userId + "",
								(int) Double.parseDouble(order.getPrice()) * 10 * 10,
								0);
				log.info("resultMap返回：{}", resultMap);
				if (resultMap != null && resultMap.containsKey("success")) {
					Double currentIntegral = (Double) resultMap.get("currentIntegral");
					PayOrder payOrder =
							buildPayOrder(
									order.getUserId(), PRODUCT_TYPE_REFUND, "客户申请退款审核通过退还度币", user.getNickName());
					int totalFee = (int) Double.parseDouble(order.getPrice()) * 10 * 10;
					payOrder.setTotalFee(totalFee);
					payOrder.setCurrentIntegral(currentIntegral.intValue());
					payOrder.setBizType("refund");
					payOrder.setTradeType("refunds");
					payOrder.setFinanceType("In");
					payOrder.setRefId(decorateOrder.getId() + "");
					// 写入消费记录
					payOrderService.addPayOrder(payOrder);
				}
			}
			// 修改订单状态
			decorateOrderService.uploadOrder(decorateOrder);

			// 消息通知
			if (message != null) {
				log.info("updateRefund开始推送消息,开始时间{}", DateUtil.formatDate(new Date()));
				String messageInfo = gson.toJson(message);
				log.info("updateRefund消息体{}", messageInfo);
				SocketIOUtil.handlerSendMessage(
						SocketIOUtil.IM_PUSH_MSG_EVENT,
						SocketIOUtil.IM_PUSH_SYSTEM_MSG_CODE,
						user.getUuid(),
						messageInfo);
			}
		} catch (Exception e) {
			log.error("updateRefund报错,错误信息{}", e);
		}
		return 1;
	}

	public PayOrder buildPayOrder(
			Integer UserId, String payType, String payMessage, String userName) {
		Date now = new Date();
		PayOrder payOrder = new PayOrder();
		payOrder.setUserId(UserId);
		payOrder.setProductType(payType);
		payOrder.setProductId(0);
		payOrder.setProductName(payMessage);
		payOrder.setProductDesc(payMessage);
		payOrder.setPayType(payType);
		payOrder.setOrderNo(IdGenerator.generateNo());
		payOrder.setPayState("SUCCESS");
		payOrder.setOrderDate(now);
		payOrder.setAdjustFee(0);
		payOrder.setSysCode(
				Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		payOrder.setCreator(userName);
		payOrder.setGmtCreate(now);
		payOrder.setModifier(userName);
		payOrder.setGmtModified(now);
		payOrder.setIsDeleted(0);
		payOrder.setPlatformId(1);
		return payOrder;
	}

	@Override
	public List<DecorateOrder> queryContractOrderList(DecorateOrderQuery query) {
		return decorateOrderService.queryContractOrderList(query);
	}

	@Override
	public List<DecorateOrder> queryRefundOrderList(DecorateOrderQuery query) {
		return decorateOrderService.queryRefundOrderList(query);
	}

	@Override
	public Map<User, Boolean> doDeductionWithCompany(Integer companyId, Integer price) {
		HashMap<User, Boolean> result = new HashMap<>();
		Set<Integer> ids = new HashSet<>();
		ids.add(companyId);
		List<User> users = userService.listUserByCompanyIds(ids);
		if (users.isEmpty()) {
			throw new RuntimeException("获取用户失败...companyId:" + companyId);
		}
		User user = users.get(0);
		Map<String, Object> resultMap = payAccountService.updatePayAccount(user.getId(), user.getUserName(), price * 100, -1);
		log.info("resultMap返回：{}", resultMap);
		// 推荐业主通知
		this.sendMessage("推荐业主", innerSetMessage, user, null);
		// 扣费
		if (resultMap != null && resultMap.containsKey("success")) {
			Double currentIntegral = (Double) resultMap.get("currentIntegral");
			PayOrder payOrder =
					buildPayOrder(user.getId(), PRODUCT_TYPE_INNER_RECOMMEND, "内部推荐扣除度币", user.getNickName());
			int totalFee = price * 100;
			payOrder.setTotalFee(totalFee);
			payOrder.setCurrentIntegral(currentIntegral.intValue());
			payOrder.setBizType("Buy");
			payOrder.setTradeType("Predeposit");
			payOrder.setFinanceType("Out");
			// 写入消费记录
			payOrderService.addPayOrder(payOrder);
			// 扣费通知
			this.sendMessage("内部推荐", "-" + totalFee / 10 + "度币", user, payOrder.getId().intValue());
			result.put(user, true);
			return result;
		}
		result.put(user, false);
		return result;
	}

	@Override
	public DecorateOrder findOrderByPriceId(Integer id) {
		return decorateOrderService.findOrderByPriceId(id);
	}

	private void sendMessage(String title, String content, User user, Integer bussnessId) {
		// save message to db
		SysUserMessage message =
				this.insertSysUserMessage(title, content, -1, bussnessId, user.getId());

		// push message
		String messageFinal = gson.toJson(message);
		log.info("updateRefund开始推送消息,开始时间{}", DateUtil.formatDate(new Date()));
		log.info("updateRefund消息体{}", messageFinal);
		SocketIOUtil.handlerSendMessage(
				SocketIOUtil.IM_PUSH_MSG_EVENT,
				SocketIOUtil.IM_PUSH_SYSTEM_MSG_CODE,
				user.getUuid(),
				messageFinal);
	}

	public static void main(String[] args) {
		String refundApproveSuccess = "根据平台审核，您的情况符合退款规则,已将{0}度币退回到你的账户,请注意查收";
		refundApproveSuccess =
				MessageFormat.format(refundApproveSuccess, Double.parseDouble("899.0000") * 10);
		System.out.println(refundApproveSuccess);
	}
}
