package com.nork.decorateOrder.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePriceService;
import com.nork.ramCache.service.RAMCacheService;

/**
 * 执行DecorateOrder模块定时钟方法
 * 
 * @author huangsongbo
 *
 */
@Component
public class DecorateOrderScheduled {

	@Autowired
	private RAMCacheService ramCacheService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private DecoratePriceService decoratePriceService;
	
	/**
	 * 定时钟清楚所有用于缓存的map
	 * 30分钟清除一次
	 * 
	 * @author huangsongbo
	 */
	@Scheduled(fixedDelay = 1800000, initialDelay = 1800000)
	public void clearStaticMap() {
		ramCacheService.clearStaticMap();
	}
	
	/**
	 * 识别超时支付的限时抢单订单, 更新其状态
	 * 5分钟更新一次
	 * 
	 * @author huangsongbo
	 */
	@Scheduled(fixedDelay = 300000, initialDelay = 300000)
	public void updateOverTimeOrder() {
		decorateOrderService.updateOverTimeOrder();
	}
	
	/**
	 * 处理报价, 检测已经报价完成的订单, 对这部分订单执行10选3家的逻辑
	 * 一个小时执行一次
	 * 
	 * @author huangsongbo
	 */
	@Scheduled(fixedDelay = 3600000, initialDelay = 3600000)
	public void dealWithDecoratePrice() {
		decoratePriceService.dealWithDecoratePrice();
	}
	
}
