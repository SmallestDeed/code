package com.nork.sandu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.sandu.metadata.PageResult;
import com.nork.sandu.model.dto.TDesignSketch;
import com.nork.sandu.util.CollectionUtil;
import com.nork.system.model.search.ResPicSearch;
import com.nork.system.service.ResPicService;

@Controller
@RequestMapping("/{style}/web/sandu/designsketch")
public class DesignSketchController {
	@Autowired
	private ResPicService resPicService;

	@RequestMapping(value = "/find")
	@ResponseBody
	public PageResult getOrderList(@RequestParam(value = "creator", required = true, defaultValue = "0") String creator,
			@RequestParam(value = "brandIds", required = false) String brandIds,
			@RequestParam(value = "pageIndex", required = true) int pageIndex,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "beginDate", required = false) String beginDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageResult message = new PageResult();
		ResPicSearch search = new ResPicSearch();
		search.setCreator(creator);
		search.setLimit(pageSize);
		search.setStart(pageIndex * pageSize);
		//////System.out.println("brandIds:"+brandIds);
		if(StringUtils.isNotBlank(brandIds) && brandIds!="null"){
			search.setBrandIds(CollectionUtil.getList(brandIds));
		}
		/*if (beginDate != null) {
			search.setBeginDate(beginDate);
		}
		if (endDate != null) {
			search.setEndDate(endDate);
		}*/
		int total = resPicService.findCount(search);
		message.setTotal(total);
		List<TDesignSketch> lstOrder = resPicService.findList(search);
		if (lstOrder != null && lstOrder.size() > 0) {
			message.setSuccess(true);
			message.setData(lstOrder);
		}
		return message;
	}
	
	@RequestMapping(value = "/get")
	@ResponseBody
	public PageResult getOrderList(@RequestParam(value = "id", required = true) int id,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageResult message = new PageResult();
		TDesignSketch sketch = resPicService.findById(id);
		if (sketch != null) {
			message.setSuccess(true);
			message.setData(sketch);
		}
		return message;
	}
}
