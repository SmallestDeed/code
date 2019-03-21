package com.sandu.web.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.base.model.BaseArea;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.sys.model.vo.ProductCategoryNodeVo;
import com.sandu.sys.model.vo.ProductCategoryVo;
import com.sandu.sys.service.ProductCategoryService;
import com.sandu.sys.service.SysDictionaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "产品分类", tags = "ProductCategory")
@RestController
@RequestMapping(value = "/v1/sandu/mini/productcategory")
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value = {"/firstList"},method=RequestMethod.GET)
	@ApiOperation(value = "获取产品一级分类信息", response = ResultMessage.class)
	public ResultMessage firstList() {
		ResultMessage message=new ResultMessage();
		List<ProductCategoryVo> lstNode=productCategoryService.getFirstList();
		if(lstNode!=null && lstNode.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstNode);
		}
		return message;
	}
	
	@RequestMapping(value = {"/list"},method=RequestMethod.GET)
	@ApiOperation(value = "获取产品一二级分类节点信息", response = ResultMessage.class)
	public ResultMessage list() {
		ResultMessage message=new ResultMessage();
		List<ProductCategoryNodeVo> lstNode=productCategoryService.getNodeList();
		if(lstNode!=null && lstNode.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstNode);
		}
		return message;
	}
}
