package com.sandu.config;

import com.sandu.api.grouppurchase.model.GroupPurchaseOpenDetail;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseBizService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.util.Utils;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 * @ClassName ScheduleConfig
 * @date 2018/11/6
 */
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {

	@Autowired
	private GroupPurchaseBizService groupPurchaseBizService;

	@Autowired
	private PayOrderService payOrderService;

	@Qualifier
	@Resource(name = "miniProPay")
	private PayService payService;

	@Value("${wxpay.ip}")
	private String wxPayIp;

	@Autowired
	private SysUserService sysUserService;


	@Value("${service.pay.groupPurchase.refund.notifyUrl}")
	private String refundCallBack;

	private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");


	@Scheduled(cron = "0 0/1 * * * ? ")	
	public void groupPurchaseEndSyncData() {
		//处理到期团购活动(处理订单状态,库存)
		log.info("##########处理到期团购活动 start ##########");
		List<GroupPurchaseOpenDetail> details = groupPurchaseBizService.syncGroupPurchaseStatus();

		log.info("退款订单Ids:{}", details.stream().map(GroupPurchaseOpenDetail::getOrderId).toArray());
		//退款
		for (GroupPurchaseOpenDetail detail : details) {


			//发起退款
			SysUser user = sysUserService.get(detail.getUserId().intValue());
			if (user == null) {
				continue;
			}
			RefundParam refundParam = this.buildRefundParam(detail.getOrderNo(), detail.getUserId().intValue(), "miniProgram");
			int payOrderId = this.createMallOrderRefundInfo(14, user, detail.getOrderNo(), refundParam.getInternalRefundNo());
			String result = payService.doRefund(refundParam);
			Map resultMap = GsonUtil.fromJson(result, Map.class);

			//更新退款流水编号
			modifyMallOrderRefundInfo(payOrderId, resultMap);
			log.debug("##########处理退款,param :{}##########", refundParam);
			log.debug("detail id : {}, result: {}", detail.getId(), resultMap);

		}
		log.info("##########处理到期团购活动 end ##########");
	}


	private void modifyMallOrderRefundInfo(Integer payOrderId, Map resultMap) {
		String payRefundNo = resultMap.get("payRefundNo").toString();
		PayOrder payOrder = new PayOrder();
		payOrder.setId(payOrderId);
		payOrder.setPayRefundNo(payRefundNo);
		payOrder.setGmtModified(new Date());
		payOrderService.update(payOrder);
	}

	private String getRefundNo(Integer userId) {
		String nowStr = df.format(new Date());
		return nowStr + (10000000000L - userId);
	}

	private PayOrder getHistoryPayOrder(String orderNo) {
		if (com.sandu.common.util.StringUtils.isBlank(orderNo)) {
			return null;
		}
		PayOrder payOrder = new PayOrder();
		payOrder.setIsDeleted(0);
		payOrder.setOrderNo(orderNo);
		payOrder.setPayState(PayState.SUCCESS);
		List<PayOrder> list = payOrderService.getList(payOrder);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	private int createMallOrderRefundInfo(Integer platformId, SysUser user, String orderNo, String refundNo) {
		PayOrder historyPayOrder = getHistoryPayOrder(orderNo);
		if (historyPayOrder == null) {
			throw new BizException("订单未支付!");
		}
		PayOrder payOrder = new PayOrder();
		payOrder.setUserId(user.getId().intValue());
		payOrder.setProductId(null);
		payOrder.setProductName("团购商品-退款");
		payOrder.setProductType("groupPurchase");
		payOrder.setOrderNo(orderNo);
		payOrder.setTotalFee(historyPayOrder.getTotalFee());
		payOrder.setPayType(PayType.WXPAY);
		payOrder.setPayState(PayState.REFUND);
		payOrder.setOpenId(user.getOpenId());
		payOrder.setOrderDate(new Date());
		payOrder.setTradeType(TradeType.MINIPAY);
		payOrder.setFinanceType(FinanceType.IN);
		payOrder.setBizType(BizType.REFUND);
		payOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		payOrder.setCreator(user.getMobile());
		payOrder.setGmtCreate(new Date());
		payOrder.setGmtModified(new Date());
		payOrder.setModifier(user.getMobile());
		payOrder.setIsDeleted(0);
		payOrder.setPlatformId(platformId);
		payOrder.setTradeNo(historyPayOrder.getTradeNo());
		payOrder.setPayTradeNo(historyPayOrder.getPayTradeNo());
		payOrder.setRefundNo(refundNo);

		return payOrderService.add(payOrder);
	}


	private RefundParam buildRefundParam(String orderNo, Integer userId, String platformCode) {
		RefundParam refundParam = new RefundParam();
		//找到之前的支付凭证
		PayOrder historyPayOrder = getHistoryPayOrder(orderNo);
		if (historyPayOrder == null) {
			throw new BizException("未找到支付凭证!");
		}
		Long totalFee = historyPayOrder.getTotalFee().longValue();
		refundParam.setOriginInternalTradeNo(historyPayOrder.getTradeNo());
		refundParam.setInternalRefundNo(getRefundNo(userId));
		refundParam.setTotalFee(totalFee);
		refundParam.setRefundFee(totalFee);
		refundParam.setRefundDesc("拼团失败退款");
		refundParam.setIp(wxPayIp);
		refundParam.setNotifyUrl(refundCallBack);
		refundParam.setOperator(userId.longValue());
		refundParam.setPlatformCode(platformCode);
		refundParam.setSource(PayParam.SOURCE_ORDER);
		return refundParam;
	}


}
