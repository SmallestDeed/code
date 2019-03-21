package com.sandu.web.designplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.designplan.model.query.DesignPlanQuery;
import com.sandu.designplan.model.vo.DesignPlanVo;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "设计方案", tags = "DesignPlan")
@RestController
@RequestMapping(value = "/v1/sandu/mini/DesignPlan")
public class DesignPlanController {
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	
	@ApiOperation(value = "获取企业设计方案", response = ResultMessage.class)
    @RequestMapping(value="/recommendedlist",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage detail(DesignPlanQuery query) {
    	ResultMessage result=new ResultMessage();
    	Page<DesignPlanVo> page=designPlanRecommendedService.list(query);
		if (page!=null && page.getList()!=null && page.getList().size()>0) {
			result.setData(page);
			result.setCode(ResultCode.Success);
		}
    	return result;
	}
	
}
