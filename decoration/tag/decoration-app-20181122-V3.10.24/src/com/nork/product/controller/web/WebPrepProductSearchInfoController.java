package com.nork.product.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.product.service.PrepProductSearchInfoService;

/**   
 * @Title: PrepProductSearchInfoController.java 
 * @Package com.nork.product.controller
 * @Description:产品模块-预处理表(产品搜索)Controller
 * @createAuthor huangsongbo 
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/product/prepProductSearchInfo")
public class WebPrepProductSearchInfoController {
	
	@Autowired
	private PrepProductSearchInfoService prepProductSearchInfoService;
	
	/**
	 * 全部已上架的产品预处理数据(主表+属性表),空间/样板房/产品从已上架->已发布
	 * @author huangsongbo
	 * @return
	 */
	@RequestMapping("/updatePutawayProduct")
	@ResponseBody
	public String updatePutawayProduct(){
		prepProductSearchInfoService.noTranUpdatePutawayProductAndSetStatus();
		return "增量更新完毕";
	}
	
}
