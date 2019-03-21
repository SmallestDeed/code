package com.nork.decorateOrder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.decorateOrder.constant.DecoratePayConstants;
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.DecorateSeckillOrder;
import com.nork.decorateOrder.model.dto.DecoratePayModulePayResultDTO;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePayService;
import com.nork.decorateOrder.service.DecorateSeckillOrderService;
import com.nork.system.config.SystemConfig;
import com.sandu.common.LoginContext;

@Controller
@RequestMapping("/{style}/mobile/decoratePay")
public class DecoratePayController {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecoratePayController.class);
	
	private final static String LOGPREFIX = "[装修订单支付模块]:";
	
	@Autowired
	@Qualifier("decoratePayDecoratePriceService")
	private DecoratePayService decoratePayDecoratePriceService;
	
	@Autowired
	@Qualifier("decoratePayDecorateSeckillService")
	private DecoratePayService decoratePayDecorateSeckillService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private DecorateSeckillOrderService decorateSeckillOrderService;
	
	/**
	 * 支付订单接口
	 * 
	 * @author huangsongbo
	 * 
	 * @param id 订单id(限时快抢订单/平台派单订单)
	 * 
	 * @param orderType 
	 * orderType = 0: 限时快抢订单
	 * orderType = 1: 平台派单订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEnvelope<DecoratePayModulePayResultDTO> pay(Long id, Integer orderType) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return new ResponseEnvelope<>(false, "参数 id 不能为空");
		}
		
		if(orderType == null) {
			orderType = DecoratePayConstants.DECORATEPAY_PAY_ORDERTYPE_DECORATESECKILL;
		}
		
		LoginUser loginUser = null;
		if(SystemConfig.DEBUGMODEL) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	loginUser = Utils.getDebugUser(request);
		}else {
			loginUser = LoginContext.getLoginUser(LoginUser.class);
		}
		if(loginUser == null || loginUser.getId() == null) {
			LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
			return new ResponseEnvelope<>(false, "请重新登录");
		}
		// ------参数验证 ->end
		
		// ------orderType = 2: 以为是直接从我的客户列表支付, 所以要根据decorateOrder判断是平台派单还是限时抢单, 再做对应处理 ->start
		if(orderType.intValue() == DecoratePayConstants.DECORATEPAY_PAY_ORDERTYPE_DECORATEORDER.intValue()) {
			DecorateOrder decorateOrder = decorateOrderService.get(id);
			if(decorateOrder == null) {
				LOGGER.error("{} decorateOrder = null; decorateOrder.id = {}", LOGPREFIX, id); 
				return new ResponseEnvelope<>(false, "订单数据不存在");
			}
			if(decorateOrder.getDecoratePriceId() != null && decorateOrder.getDecoratePriceId() > 0) {
				orderType = DecoratePayConstants.DECORATEPAY_PAY_ORDERTYPE_DECORATEPRICE;
				id = decorateOrder.getDecoratePriceId();
			}else if(decorateOrder.getDecorateSeckillOrderId() != null && decorateOrder.getDecorateSeckillOrderId() > 0) {
				DecorateSeckillOrder decorateSeckillOrder = decorateSeckillOrderService.get(decorateOrder.getDecorateSeckillOrderId());
				if(decorateSeckillOrder == null) {
					return new ResponseEnvelope<>(false, "订单数据异常(decorateSeckillOrder == null, decorateSeckillOrder.id = " + decorateOrder.getDecorateSeckillOrderId() + ")");
				}else {
					orderType = DecoratePayConstants.DECORATEPAY_PAY_ORDERTYPE_DECORATESECKILL;
					id = decorateSeckillOrder.getDecorateSeckillId();
				}
			}else {
				// 内部推荐订单
				/*return new ResponseEnvelope<>(false, "订单数据异常(decorateOrder.id = " + id + ")");*/
				DecoratePayModulePayResultDTO returnDTO;
				try {
					returnDTO = decoratePayDecorateSeckillService.payForInternalReferral(id, loginUser.getId().longValue());
				} catch (BizException e) {
					return new ResponseEnvelope<>(false, e.getMessage());
				}
				return new ResponseEnvelope<>(true, returnDTO);
			}
		}
		// ------orderType = 2: 以为是直接从我的客户列表支付, 所以要根据decorateOrder判断是平台派单还是限时抢单, 再做对应处理 ->end
		
		DecoratePayModulePayResultDTO returnDTO = null;
		
		try {
			
			if(orderType.intValue() == DecoratePayConstants.DECORATEPAY_PAY_ORDERTYPE_DECORATESECKILL.intValue()) {
				returnDTO = decoratePayDecorateSeckillService.pay(id, loginUser.getId().longValue());
			}else if(orderType.intValue() == DecoratePayConstants.DECORATEPAY_PAY_ORDERTYPE_DECORATEPRICE.intValue()) {
				returnDTO = decoratePayDecoratePriceService.pay(id, loginUser.getId().longValue());
			}else {
				LOGGER.error(LOGPREFIX + "orderType = {}, orderType 不支持该值", orderType);
				return new ResponseEnvelope<>(false, "参数 orderType 不支持该值");
			}
			
		}catch (BizException e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		
		return new ResponseEnvelope<>(true, returnDTO);
	}
	
}
