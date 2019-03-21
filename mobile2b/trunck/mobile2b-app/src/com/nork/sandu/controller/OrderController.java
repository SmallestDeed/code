package com.nork.sandu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.pay.model.search.PayOrderSearch;
import com.nork.pay.service.PayOrderService;
import com.nork.sandu.metadata.PageResult;
import com.nork.sandu.model.dto.TOrder;

@Controller
@RequestMapping("/{style}/web/sandu/order")
public class OrderController {
	@Autowired
	private PayOrderService payOrderService;

	@RequestMapping(value = "/find")
	@ResponseBody
	public PageResult getOrderList(@RequestParam(value = "bizType", required = true) String bizType,
			@RequestParam(value = "userId", required = true, defaultValue = "0") int userId,
			@RequestParam(value = "pageIndex", required = true) int pageIndex,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "beginDate", required = false) String beginDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageResult message = new PageResult();
		PayOrderSearch search = new PayOrderSearch();
		search.setUserId(userId);
		search.setBizType(bizType);
		search.setLimit(pageSize);
		search.setStart(pageIndex * pageSize);
		if (beginDate != null) {
			search.setBeginDate(beginDate);
		}
		if (endDate != null) {
			search.setEndDate(endDate);
		}
		int total = payOrderService.findCount(search);
		message.setTotal(total);
		List<TOrder> lstOrder = payOrderService.findList(search);
		if (lstOrder != null && lstOrder.size() > 0) {
			message.setSuccess(true);
			message.setData(lstOrder);
		}
		return message;
	}
}
