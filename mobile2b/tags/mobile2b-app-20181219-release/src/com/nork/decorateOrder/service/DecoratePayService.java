package com.nork.decorateOrder.service;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nork.common.async.ExecutorServiceUtils;
import com.nork.common.exception.BizException;
import com.nork.decorateOrder.constant.DecorateOrderConstants;
import com.nork.decorateOrder.constant.DecoratePayConstants;
import com.nork.decorateOrder.constant.DecoratePayConstants.DECORATEPAYDTO_ORDERTYPE_ENUM;
import com.nork.decorateOrder.constant.DecoratePayConstants.DECORATEPAY_PAY_PAYSTATUS_ENUM;
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.dto.DecoratePayDTO;
import com.nork.decorateOrder.model.dto.DecoratePayModulePayResultDTO;
import com.nork.decorateOrder.model.dto.DecoratePayServicePayReturnDTO;
import com.nork.pay.PayConfig;
import com.nork.pay.constant.PayAccountConstants;
import com.nork.pay.model.PayAccount;
import com.nork.pay.service.PayAccountService;
import com.nork.pay.service.PayOrderService;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserMessageService;

public abstract class DecoratePayService {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecoratePayService.class);
	
	private final static String LOGPREFIX = "[装修订单支付模块]:";
	
	@Autowired 
	private PayOrderService payOrderService;
	
	@Autowired
	private SysUserMessageService sysUserMessageService;
	
	@Autowired
	private PayAccountService payAccountService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private RAMCacheService ramCacheService;
	
	/**
	 * 支付订单
	 * 
	 * @param id 订单id(限时快抢id/平台派单id)
	 * @param userId 用户id
	 * 
	 * @return 支付状态
	 * payStatus = 0: 支付成功
	 * payStatus = 1: 支付失败, 余额不足
	 * @throws BizException 
	 */
	public DecoratePayModulePayResultDTO pay(Long orderId, Long userId) throws BizException {
		// ------参数验证 ->start
		if(orderId == null) {
			LOGGER.error(LOGPREFIX + "orderId = null");
			throw new BizException("支付失败(orderId = null)");
		}
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			throw new BizException("支付失败(userId = null)");
		}
		// ------参数验证 ->end
		
		DECORATEPAY_PAY_PAYSTATUS_ENUM statusEnum = null;
		
		// ------获取订单信息 ->start
		DecoratePayDTO decoratePayDTO = this.getDecoratePayDTO(orderId, userId);
		// ------获取订单信息 ->end
		
		// 如果订单已支付, 取消支付 ->start
		if(decoratePayDTO.getPayStatus() == DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_HAVE_PAY) {
			return this.getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed_alreadyPaid, decoratePayDTO.getDecorateOrderId());
		}
		// 如果订单已支付, 取消支付 ->end
		
		// 如果订单超时支付, 则支付失败 ->start
		if(decoratePayDTO.getPayStatus() == DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_PAY_OVERTIME) {
			return this.getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed_overtime, decoratePayDTO.getDecorateOrderId());
		}
		// 如果订单超时支付, 则支付失败 ->end
		
		// ------支付 ->start
		DecoratePayServicePayReturnDTO payReturnDTO = this.pay(userId, decoratePayDTO.getFeeAmount());
		// ------支付 ->end
		
		// 支付状态
		int payStatus = payReturnDTO.getPayStatus();
		
		// ------支付失败 return ->start
		if(payStatus != DECORATEPAY_PAY_PAYSTATUS_ENUM.success.getValue().intValue()) {
			statusEnum = DECORATEPAY_PAY_PAYSTATUS_ENUM.get(payStatus);
			return this.getDecoratePayModulePayResultDTO(statusEnum, decoratePayDTO.getDecorateOrderId());
		}
		// ------支付失败 return ->end
		
		decoratePayDTO.setCurrentBalance(
				new BigDecimal(
					payReturnDTO.getCurrentBalance() 
					/ PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI
					/ PayConfig.MULRIPLE_BETWEEN_DUBI_AND_YUAN
				)
			);
		
		// ------支付成功, 处理订单状态 ->start
		decorateOrderService.updateOrderStatusById(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_WAIT_TO_COMMUNICATE, decoratePayDTO.getDecorateOrderId());
		// ------支付成功, 处理订单状态 ->end
		
		ExecutorServiceUtils.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				
				// ------支付成功的后续动作 ->start
				doSomeThing(userId, decoratePayDTO.getCustomerId(), orderId);
				// ------支付成功的后续动作 ->end
				
				// ------insert payOrder ->start
				payOrderService.add(decoratePayDTO);
				// ------insert payOrder ->end
				
				// ------insert sys_user_message and 推送消息 ->start
				sysUserMessageService.add(
						decoratePayDTO.getOrderType().getMessage(),
						("-" + (decoratePayDTO.getFeeAmount().intValue() / PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI) + "度币"),
						decoratePayDTO.getSysUser()
						);
				// 主动推送
				/*sss;*/
				// ------insert sys_user_message and 推送消息 ->end
			}
			
		});
		
		return this.getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM.success, decoratePayDTO.getDecorateOrderId());
	}

	/**
	 * 支付后续动作
	 * 1.[限时快抢]: 支付之后,需要更新redis的纪录, 以便查询今日剩余抢单次数
	 * 2.[限时快抢]: update decorate_customer.first_seckill_company
	 * 
	 * @author huangsongbo
	 * @param orderId 限时快抢id/平台派单id
	 */
	public abstract void doSomeThing(Long userId, Long customerId, Long orderId);

	private DecoratePayModulePayResultDTO getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM statusEnum, Long orderId) {
		if(statusEnum == null) {
			LOGGER.error(LOGPREFIX + "statusEnum == null");
			statusEnum = DECORATEPAY_PAY_PAYSTATUS_ENUM.failed;
		}
		
		return new DecoratePayModulePayResultDTO(statusEnum.getValue(), statusEnum.getMessage(), orderId);
	}

	/**
	 * 支付, 修改用户度币
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @param feeAmount
	 * @return
	 */
	public DecoratePayServicePayReturnDTO pay(Long userId, Double feeAmount) {
		DecoratePayServicePayReturnDTO returnDTO = new DecoratePayServicePayReturnDTO();
		
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed.getValue());
			return returnDTO;
		}
		if(feeAmount == null) {
			LOGGER.error(LOGPREFIX + "feeAmount = null");
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed.getValue());
			return returnDTO;
		}
		// ------参数验证 ->end
		
		// ------查看用户余额够不够 ->start
		PayAccount payAccount = payAccountService.get(userId, PayAccountConstants.PAYACCOUNT_PLATFORMBUSSINESSTYPE_2B);
		if(payAccount == null) {
			LOGGER.error("{} payAccount = null; payAccount.userId = {}", LOGPREFIX, userId);
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed.getValue());
			return returnDTO;
		}
		if(payAccount.getBalanceAmount() == null) {
			LOGGER.error("{} payAccount.getBalanceAmount() == null; payAccount.userId = {}", LOGPREFIX, userId);
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed.getValue());
			return returnDTO;
		}
		if(payAccount.getBalanceAmount() >= feeAmount) {
			
		}else {
			// 余额不足
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed_notSufficientFunds.getValue());
			return returnDTO;
		}
		// ------查看用户余额够不够 ->end
		
		returnDTO.setCurrentBalance(payAccount.getBalanceAmount() - feeAmount);
		
		// ------更新余额(扣费) ->start
		PayAccount payAccountForUpdate = new PayAccount();
		payAccountForUpdate.setConsumAmount((payAccount.getConsumAmount() == null ? new Double(0) : payAccount.getConsumAmount()) + feeAmount);
		payAccountForUpdate.setBalanceAmount(payAccount.getBalanceAmount() - feeAmount);
		payAccountForUpdate.setGmtModified(new Date());
		int updateCount = payAccountService.updateBalance(payAccountForUpdate, payAccount.getId(), payAccount.getBalanceAmount());
		// ------更新余额(扣费) ->end
		
		if(updateCount > 0) {
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.success.getValue());
			return returnDTO;
		}else {
			returnDTO.setPayStatus(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed.getValue());
			return returnDTO;
		}
	}

	/**
	 * 根据订单(抢单/平台派单)获得信息(支付信息)
	 * 
	 * @author huangsongbo
	 * @param orderId
	 * @param userId 
	 * @return
	 * @throws BizException 
	 */
	public abstract DecoratePayDTO getDecoratePayDTO(Long orderId, Long userId) throws BizException;
	
	/**
	 * 变更订单状态为"已支付"
	 * 
	 * @param orderId
	 * @param userId
	 * @return 返回对应装修订单的id(decoreate_order.id)
	 */
	public abstract Long updateStatus(Long orderId);

	/**
	 * 专门为内部推荐支付做的方法
	 * 
	 * @author huangsongbo
	 * @param id
	 * @param longValue
	 * @return
	 * @throws BizException 
	 */
	public DecoratePayModulePayResultDTO payForInternalReferral(Long orderId, Long userId) throws BizException {
		// ------参数验证 ->start
		if(orderId == null) {
			LOGGER.error(LOGPREFIX + "orderId = null");
			throw new BizException("支付失败(orderId = null)");
		}
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			throw new BizException("支付失败(userId = null)");
		}
		DecorateOrder decorateOrder = decorateOrderService.get(orderId);
		if(decorateOrder == null) {
			LOGGER.error(LOGPREFIX + "decorateOrder = null, decorateOrder.id = {}", orderId);
			throw new BizException("支付失败(decorateOrder = null, decorateOrder.id = " + orderId + ")");
		}
		
		// ------校验订单状态 ->start
		if(decorateOrder.getOrderStatus() == null) {
			LOGGER.error(LOGPREFIX + "decorateOrder.getOrderStatus() == null");
			throw new BizException("支付失败(订单状态未知, decorateOrder.id = " + orderId + ")");
		}
		
		// 校验订单是否超时
		if(
				decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME
				|| (
						decorateOrder.getOrderTimeout() != null
						&& decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_UNPAID
						&& new Date().after(decorateOrder.getOrderTimeout())
						)) {
			return this.getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed_overtime, orderId);
		}
		
		// 校验订单是否已支付
		if(decorateOrder.getOrderStatus().intValue() != DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_UNPAID) {
			return this.getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM.failed_alreadyPaid, orderId);
		}
		// ------校验订单状态 ->end
		
		DECORATEPAY_PAY_PAYSTATUS_ENUM statusEnum = null;
		
		// ------支付 ->start
		DecoratePayServicePayReturnDTO payReturnDTO = this.pay(userId, decorateOrder.getPrice().multiply(new BigDecimal(PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI)).doubleValue());
		// ------支付 ->end
		
		// 支付状态
		int payStatus = payReturnDTO.getPayStatus();
		
		// ------支付失败 return ->start
		if(payStatus != DECORATEPAY_PAY_PAYSTATUS_ENUM.success.getValue().intValue()) {
			statusEnum = DECORATEPAY_PAY_PAYSTATUS_ENUM.get(payStatus);
			return this.getDecoratePayModulePayResultDTO(statusEnum, orderId);
		}
		// ------支付失败 return ->end
		
		// ------获取bean: DecoratePayDTO 记录支付的一些信息 ->start
		DecoratePayDTO decoratePayDTO = new DecoratePayDTO();
		SysUser sysUser = ramCacheService.getSysUser(userId);
		decoratePayDTO.setSysUser(sysUser);
		decoratePayDTO.setPayType("innerRecommend");
		decoratePayDTO.setPayMessage("内部推荐扣除度币");
		decoratePayDTO.setPayDesc("内部推荐扣除度币");
		decoratePayDTO.setFeeAmount(decorateOrder.getPrice().multiply(new BigDecimal(PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI)).doubleValue());
		decoratePayDTO.setCurrentBalance(
				new BigDecimal(
					payReturnDTO.getCurrentBalance() 
					/ PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI
					/ PayConfig.MULRIPLE_BETWEEN_DUBI_AND_YUAN
				)
			);
		decoratePayDTO.setOrderType(DECORATEPAYDTO_ORDERTYPE_ENUM.innerRecommend);
		// ------获取bean: DecoratePayDTO 记录支付的一些信息 ->end
		
		// ------支付成功, 处理订单状态 ->start
		decorateOrderService.updateOrderStatusById(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_WAIT_TO_COMMUNICATE, orderId);
		// ------支付成功, 处理订单状态 ->end
		
		ExecutorServiceUtils.getInstance().execute(new Runnable() {

			@Override
			public void run() {

				// ------insert payOrder ->start
				payOrderService.add(decoratePayDTO);
				// ------insert payOrder ->end
				
				// ------insert sys_user_message and 推送消息 ->start
				sysUserMessageService.add(
						decoratePayDTO.getOrderType().getMessage(),
						("-" + (decoratePayDTO.getFeeAmount().intValue() / PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI) + "度币"),
						decoratePayDTO.getSysUser()
						);
				// 主动推送
				/*sss;*/
				// ------insert sys_user_message and 推送消息 ->end
			}
			
		});
		
		return this.getDecoratePayModulePayResultDTO(DECORATEPAY_PAY_PAYSTATUS_ENUM.success, orderId);
	}

}
