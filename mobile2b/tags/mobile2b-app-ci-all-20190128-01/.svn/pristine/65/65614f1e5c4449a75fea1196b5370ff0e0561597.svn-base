package com.nork.decorateOrder.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.exception.BizException;
import com.nork.decorateOrder.constant.DecorateOrderConstants;
import com.nork.decorateOrder.constant.DecoratePayConstants;
import com.nork.decorateOrder.constant.DecoratePayConstants.DECORATEPAYDTO_ORDERTYPE_ENUM;
import com.nork.decorateOrder.constant.DecorateSeckillConstants;
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.dto.DecoratePayDTO;
import com.nork.decorateOrder.model.dto.SeckillRecordFromRedisDTO;
import com.nork.decorateOrder.service.DecorateCustomerService;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePayService;
import com.nork.decorateOrder.service.DecorateSeckillService;
import com.nork.pay.PayConfig;
import com.nork.pay.constant.PayOrderConstants;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.SysUser;

@Service("decoratePayDecorateSeckillService")
public class DecoratePayDecorateSeckillServiceImpl extends DecoratePayService {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecoratePayDecorateSeckillServiceImpl.class);
	
	private final static String LOGPREFIX = "[限时快抢支付模块]:";
	
	@Autowired
	private RAMCacheService ramCacheService;
	
	@Autowired
	private DecorateSeckillService decorateSeckillService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private DecorateCustomerService decorateCustomerService;
	
	@Override
	public DecoratePayDTO getDecoratePayDTO(Long orderId, Long userId) throws BizException {
		// ------参数验证 ->start
		if(orderId == null) {
			LOGGER.error("{} orderId = null", LOGPREFIX);
			return null;
		}
		if(userId == null) {
			LOGGER.error("{} userId = null", LOGPREFIX);
			return null;
		}
		// ------参数验证 ->end
		
		// ------获取decorateSeckill(报价)信息 ->start
		DecorateSeckill decorateSeckill = decorateSeckillService.get(orderId);
		if(decorateSeckill == null) {
			LOGGER.error("{} decorateSeckill = null; decorateSeckill.id = {}", LOGPREFIX, orderId);
			throw new BizException("获取订单信息失败(decorateSeckill = null, decorateSeckill.id = " + orderId + ")");
		}
		// ------获取decorateSeckill(报价)信息 ->end
		
		// ------构造DecoratePayDTO ->start
		SysUser sysUser = ramCacheService.getSysUser(userId);
		// 获取该报价的支付状态
		Integer payStatus = null;
		
		DecorateOrder decorateOrder = decorateOrderService.findOneBySeckillId(orderId);
		
		if(decorateOrder == null) {
			payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_NO_PAY;
		}
		
		if(decorateOrder.getOrderStatus() == null 
				|| decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_UNPAID
				|| decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME
				) {
			// 验证是否支付超时
			if(
					decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME
					|| decorateOrder.getOrderTimeout().before(new Date())
					) {
				payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_PAY_OVERTIME;
			}else {
				payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_NO_PAY;
			}
		}else {
			payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_HAVE_PAY;
		}
		
		DecoratePayDTO decoratePayDTO = new DecoratePayDTO();
		decoratePayDTO.setFeeAmount(decorateSeckill.getPrice() == null ? 0 : (decorateSeckill.getPrice().doubleValue()) * PayConfig.MULRIPLE_BETWEEN_DUBI_AND_YUAN * PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI);
		decoratePayDTO.setSysUser(sysUser);
		decoratePayDTO.setPayType(PayOrderConstants.PAYORDER_PRODUCTTYPE_DECORATESECKILL_PAY);
		decoratePayDTO.setPayStatus(payStatus);
		decoratePayDTO.setPayMessage("限时快抢支付度币");
		decoratePayDTO.setPayDesc("限时快抢支付度币");
		decoratePayDTO.setOrderType(DECORATEPAYDTO_ORDERTYPE_ENUM.decorateSeckill);
		decoratePayDTO.setDecorateOrderId(decorateOrder.getId());
		decoratePayDTO.setCustomerId(decorateSeckill.getCustomerId());
		// ------构造DecoratePayDTO ->end
		
		return decoratePayDTO;
	}

	@Override
	public Long updateStatus(Long decorateSeckillId) {
		/*decorateOrderService.updateOrderStatusByDecorateSeckillId(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_WAIT_TO_COMMUNICATE, decorateSeckillId);*/
		DecorateOrder decorateOrder = decorateOrderService.findOneBySeckillId(decorateSeckillId);
		if(decorateOrder == null) {
			LOGGER.error("{} decorateOrder == null, decorateSeckillId = {}", LOGPREFIX, decorateSeckillId);
			return null;
		}
		decorateOrderService.updateOrderStatusById(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_WAIT_TO_COMMUNICATE, decorateOrder.getId());
		return decorateOrder.getId();
	}

	@Override
	public void doSomeThing(Long userId, Long customerId, Long id) {
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error("{} userId = null", LOGPREFIX);
			return;
		}
		if(customerId == null) {
			LOGGER.error("{} customerId = null", LOGPREFIX);
			return;
		}
		// ------参数验证 ->end
		
		// ------支付之后,需要更新redis的纪录, 以便查询今日剩余抢单次数 ->start
		SeckillRecordFromRedisDTO seckillRecordFromRedisDTO = decorateSeckillService.getSeckillRecordFromRedisDTO(DecorateSeckillConstants.SECKILLRECORDFROMREDISDTO_STATUS_PAY);
		decorateSeckillService.setSeckillRecord(userId, seckillRecordFromRedisDTO);
		// ------支付之后,需要更新redis的纪录, 以便查询今日剩余抢单次数 ->end
		
		// ------decorate_customer.first_seckill_company ->start
		SysUser sysUser = ramCacheService.getSysUser(userId);
		if(sysUser != null) {
			decorateCustomerService.updateCompanyId(sysUser.getpCompanyId(), customerId);
		}else {
			LOGGER.error("{} sysUser = null, sysUser.id = {}", LOGPREFIX, userId);
		}
		// ------decorate_customer.first_seckill_company ->end
		
		// ------update decorate_seckill.pay_time 更新支付时间 ->start
		DecorateSeckill decorateSeckillForUpdate = new DecorateSeckill();
		decorateSeckillForUpdate.setId(id);
		decorateSeckillForUpdate.setPayTime(new Date());
		decorateSeckillService.update(decorateSeckillForUpdate);
		// ------update decorate_seckill.pay_time 更新支付时间 ->end
	}
	
}
