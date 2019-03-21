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
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.decorateOrder.model.dto.DecoratePayDTO;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePayService;
import com.nork.decorateOrder.service.DecoratePriceService;
import com.nork.pay.PayConfig;
import com.nork.pay.constant.PayOrderConstants;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.SysUser;

@Service("decoratePayDecoratePriceService")
public class DecoratePayDecoratePriceServiceImpl extends DecoratePayService {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecoratePayDecoratePriceServiceImpl.class);
	
	private final static String LOGPREFIX = "[报价支付模块]:";
	
	@Autowired
	private DecoratePriceService decoratePriceService;
	
	@Autowired
	private RAMCacheService ramCacheService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
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
		
		// ------获取DecoratePrice(报价)信息 ->start
		DecoratePrice decoratePrice = decoratePriceService.get(orderId);
		if(decoratePrice == null) {
			LOGGER.error("{} decoratePrice = null; decoratePrice.id = {}", LOGPREFIX, orderId);
			throw new BizException("获取订单信息失败(decoratePrice = null, decoratePrice.id = " + orderId + ")");
		}
		// ------获取DecoratePrice(报价)信息 ->end
		
		// ------构造DecoratePayDTO ->start
		SysUser sysUser = ramCacheService.getSysUser(userId);
		// 获取该报价的支付状态
		Integer payStatus = null;
		
		DecorateOrder decorateOrder = decorateOrderService.findOneByPriceId(orderId);
		if(decorateOrder == null) {
			payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_NO_PAY;
		}
		
		if(decorateOrder.getOrderStatus() == null 
				|| decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_UNPAID
				|| decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME
				) {
			// 验证是否支付超时
			if(
					decorateOrder.getOrderStatus() != null && (
					decorateOrder.getOrderStatus().intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME
					|| decorateOrder.getOrderTimeout().before(new Date()))
					) {
				payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_PAY_OVERTIME;
			}else {
				payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_NO_PAY;
			}
		}else {
			payStatus = DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_HAVE_PAY;
		}
		
		DecoratePayDTO decoratePayDTO = new DecoratePayDTO();
		decoratePayDTO.setFeeAmount(decoratePrice.getPrice() == null ? 0 : (decoratePrice.getPrice().doubleValue() * PayConfig.MULRIPLE_BETWEEN_DUBI_AND_YUAN * PayConfig.MULRIPLE_BETWEEN_JIFEN_AND_DUBI));
		decoratePayDTO.setSysUser(sysUser);
		decoratePayDTO.setPayType(PayOrderConstants.PAYORDER_PRODUCTTYPE_DECORATEPRICE_PAY);
		decoratePayDTO.setPayStatus(payStatus);
		decoratePayDTO.setPayMessage("平台报价支付度币");
		decoratePayDTO.setPayDesc("平台报价支付度币");
		decoratePayDTO.setOrderType(DECORATEPAYDTO_ORDERTYPE_ENUM.decoratePrice);
		decoratePayDTO.setDecorateOrderId(decorateOrder.getId());
		decoratePayDTO.setCustomerId(decoratePrice.getCustomerId());
		// ------构造DecoratePayDTO ->end
		
		return decoratePayDTO;
	}

	@Override
	public Long updateStatus(Long decoratePriceId) {
		DecorateOrder decorateOrder = decorateOrderService.findOneByPriceId(decoratePriceId);
		if(decorateOrder == null) {
			LOGGER.error("{} decorateOrder == null, decoratePriceId = {}", LOGPREFIX, decoratePriceId);
			return null;
		}
		decorateOrderService.updateOrderStatusById(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_WAIT_TO_COMMUNICATE, decorateOrder.getId());
		return decorateOrder.getId();
	}

	@Override
	public void doSomeThing(Long userId, Long customerId, Long id) {
		
	}

}
