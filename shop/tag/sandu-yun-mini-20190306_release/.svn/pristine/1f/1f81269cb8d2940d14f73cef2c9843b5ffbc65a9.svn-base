package com.sandu.web.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.company.model.query.ShopActivityQuery;
import com.sandu.company.model.vo.ShopActivityDetailVo;
import com.sandu.company.model.vo.ShopActivityVo;
import com.sandu.company.service.ShopActivityService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "店铺优惠活动服务", tags = "ShopActivity")
@RestController
@RequestMapping(value = "/v1/sandu/mini/shopactivity")
public class ShopActivityController {
	@Autowired
	private ShopActivityService shopActivityService;
	
	@ApiOperation(value = "获取店铺优惠活动详情", response = ResultMessage.class)
    @RequestMapping(value="/detail",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage detail(long id) {
    	ResultMessage result=new ResultMessage();
    	ShopActivityDetailVo detail = shopActivityService.get(id);
		if(detail!=null) {
    		result.setCode(ResultCode.Success);
    		result.setData(detail);
    	}
    	return result;
	}
	
	@ApiOperation(value = "查询店铺优惠活动", response = ResultMessage.class)
    @RequestMapping(value="/list",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage list(ShopActivityQuery query) {
    	ResultMessage result=new ResultMessage();
    	Page<ShopActivityVo> page = shopActivityService.list(query);
    	if (page!=null && page.getList()!=null && page.getList().size()>0) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
    	return result;
	}
}
