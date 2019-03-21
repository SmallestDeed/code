package com.nork.product.controller2.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.product.service2.ProCategoryService;

/**
 * @version V1.0
 * @Title: ProductCategoryRelController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品与产品类目关联Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Controller
@RequestMapping("/{style}/web/product/proCategory2")
public class WebProductCategoryController {
	@Autowired
	private ProCategoryService proCategoryService2;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 
	 * 产品类目
	 * 分类查询接口供移动端调用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/searchProCategory")
	@ResponseBody
	public Object searchProCategory(  @RequestParam(value = "type", required = false) String type, @RequestParam(value = "cid", required = false) String cid,
			@RequestParam(value = "msgId", required = false) String msgId) {
		return proCategoryService2.searchProCategory(type, cid, msgId);
	}
}
