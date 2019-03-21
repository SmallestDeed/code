package com.nork.mobile.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.mobile.model.search.MobilePaymentModel;
import com.nork.mobile.service.MobileUnlockingService;
import com.nork.pay.alipay.util.AlipayCore;
import com.nork.pay.common.IdGenerator;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.PayType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.ResultMessage;
import com.nork.pay.service.PayOrderService;
import com.nork.pay.wexin.protocol.AppPayReqData;
import com.nork.pay.wexin.protocol.UnifiedOrderReqData;
import com.nork.pay.wexin.protocol.UnifiedOrderResData;
import com.nork.pay.wexin.service.UnifiedOrderService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

/**
 * 开通移动端的方法
 * 
 * @author yangzhun
 */
@Service("mobileUnlockingService")
public class MobileUnlockingServiceImpl implements MobileUnlockingService {

	private static Logger logger = Logger.getLogger(MobileUnlockingServiceImpl.class);
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PayOrderService payOrderService;

	/**
	 * 开通移动端的
	 */
	public final static Integer UNLOCKING_MOBILE_ID = 1122;

	public final static String UNLOCKING_MOBILE = "unlocking_mobile";

	public final static String UNLOCKING_MOBILE_NAME = "开通移动端功能";
	
	/***
	 * 移动端续费
	 */
	public final static Integer RENEWAL_MOBILE_ID = 1122;

	public final static String RENEWAL_MOBILE = "renewal_mobile";

	public final static String RENEWAL_MOBILE_NAME = "移动端功能续费";

	/**
	 * 使用积分开通和续费移动端功能
	 */
	@Override
	public Object unlockingByIntegral(MobilePaymentModel mobilePaymentModel) {
		String payType = mobilePaymentModel.getPayType();
		Integer totalFee = mobilePaymentModel.getTotalFee();
		String account = mobilePaymentModel.getAccount();
		String password = mobilePaymentModel.getPassword();
		if(StringUtils.isBlank(account)) {
			return new ResponseEnvelope<SysUser>(false, "账号不能为空!");
		}
		if(StringUtils.isBlank(password)) {
			return new ResponseEnvelope<SysUser>(false, "密码不能为空!");
		}
		// 通过电话号码查询到用户信息
		SysUser sysUser = sysUserService.checkOnlyUserAccount(account, password);
		int balance = sysUser.getBalanceAmount().intValue();
		Integer balanceAmount = new Integer(balance) - totalFee;
		Integer consumAmount = new Integer(balance) + totalFee;
		if (balanceAmount < 0) {
			return new ResponseEnvelope<>(false, "积分不足，请先到电脑端进行充值");
		}

		PayOrder payOrder = this.setPayOrder(sysUser, totalFee, payType, balanceAmount);

		//续费移动端功能的订单
		if(sysUser.getExistsMobile() == 1) {
			payOrder.setProductId(RENEWAL_MOBILE_ID);
			payOrder.setProductType(RENEWAL_MOBILE);
			payOrder.setProductName(RENEWAL_MOBILE_NAME);
			payOrder.setProductDesc(RENEWAL_MOBILE_NAME);
			int payOrderId = payOrderService.add(payOrder);
			if(payOrderId > 0) {
				Integer renewalYear = totalFee/135000;//根据传入的金额计算续费的年限
				
				sysUser.setBalanceAmount(balanceAmount.doubleValue());
				sysUser.setConsumAmount(consumAmount.doubleValue());
				sysUser.setRenewNumber(renewalYear);
				sysUserService.renew(sysUser);
			}
			return new ResponseEnvelope<>(true, "续费移动端功能成功");
		}
		
		int payOrderId = payOrderService.add(payOrder);
		if (payOrderId > 0) {
			sysUser.setExistsMobile(new Integer(1));
			sysUser.setBalanceAmount(balanceAmount.doubleValue());
			sysUser.setConsumAmount(consumAmount.doubleValue());
			sysUserService.update(sysUser);
		}
		return new ResponseEnvelope<>(true, "开通移动端功能成功");
	}

	/**
	 * 封装订单对象
	 * 
	 * @param sysUser
	 * @param totalFee
	 * @param payType
	 * @param balanceAmount
	 * @return
	 */
	private PayOrder setPayOrder(SysUser sysUser, Integer totalFee, String payType, Integer balanceAmount) {
		PayOrder payOrder = new PayOrder();
		payOrder.setUserId(sysUser.getId());
		payOrder.setProductId(UNLOCKING_MOBILE_ID);
		payOrder.setProductType(UNLOCKING_MOBILE);
		payOrder.setProductName(UNLOCKING_MOBILE_NAME);
		payOrder.setProductDesc(UNLOCKING_MOBILE_NAME);
		payOrder.setOrderNo(IdGenerator.generateNo());
		payOrder.setTotalFee(totalFee);
		payOrder.setPayType(payType);
		payOrder.setOrderDate(new Date());
		payOrder.setPayState(PayState.SUCCESS);
		payOrder.setCurrentIntegral(balanceAmount);

		payOrder.setGmtCreate(new Date());
		payOrder.setCreator(sysUser.getUserName());
		payOrder.setIsDeleted(0);
		payOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));

		payOrder.setGmtModified(new Date());
		payOrder.setModifier(sysUser.getUserName());
		return payOrder;
	}

	public Object getWXSettings(PayOrder payOrder, ResultMessage message) {
		String payType = payOrder.getPayType();
		int id = payOrderService.add(payOrder);
		if (id == 0) {
			message.setMessage("保存支付单失败.");
			return message;
		}
		payOrder.setId(id);
		// 当支付方式为微信支付，走下面的业务分支
		if (payType.equalsIgnoreCase(PayType.WXPAY)) {
			String flag = "SUCCESS";
			try {
				UnifiedOrderService orderService = new UnifiedOrderService();
				UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(payOrder.getProductName(),
						payOrder.getOrderNo(), payOrder.getTotalFee());
				UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
				logger.info("微信下支付订单：" + unifiedOrderResData.getResult_code() + "++++"
						+ unifiedOrderResData.getReturn_code());
				if(unifiedOrderResData.getResult_code() == null) {
					
				}
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
			} catch (Exception e) {
				e.printStackTrace();
				message.setSuccess(false);
				message.setMessage("下单异常.");
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
		return message;
	}

}
