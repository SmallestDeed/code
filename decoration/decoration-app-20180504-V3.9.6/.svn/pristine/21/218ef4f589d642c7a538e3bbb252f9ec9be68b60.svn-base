package com.nork.mobile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.SysDictionaryService;

/**
 * 将数据字典里的数据查出来
 * @author yangzhun
 *
 */
@Controller
@RequestMapping("/{style}/mobile/sysDictionary")
public class MobileSysDictionaryController {

	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	/**
	 * 将数据字典里的数据查出来
	 * @return
	 */
	@RequestMapping(value = "/getAllSysDictionary")
	@ResponseBody
	public Object getAllSysDictionary(/*@RequestBody MobileRenderingModel model*/) {
		String order = "type";//model.getOrder();
		String orderNum = "asc";//model.getOrderNum();
		
		SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
		
		sysDictionarySearch.setOrder(order);
		sysDictionarySearch.setOrderNum(orderNum);
		List<SysDicitonaryOptimize> list = sysDictionaryService.getListOptimize(sysDictionarySearch);
		return list;
	}
	
}
