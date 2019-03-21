package com.nork.decorateOrder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePriceService;

@Controller
@RequestMapping("/{style}/mobile/decorateOrderScheduled")
public class DecorateOrderScheduledController {

	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private DecoratePriceService decoratePriceService;
	
	@RequestMapping("/testUpdateOverTimeOrder")
	@ResponseBody
	public void testUpdateOverTimeOrder() {
		decorateOrderService.updateOverTimeOrder();
	}
	
	@RequestMapping("/dealWithDecoratePrice")
	@ResponseBody
	public void dealWithDecoratePrice() {
		decoratePriceService.dealWithDecoratePrice();
	}
	
}
