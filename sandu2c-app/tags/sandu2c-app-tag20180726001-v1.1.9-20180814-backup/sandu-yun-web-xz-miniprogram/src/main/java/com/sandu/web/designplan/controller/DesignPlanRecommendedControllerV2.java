package com.sandu.web.designplan.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.model.DesignPlanRecommenInput;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.exception.AppException;
import com.sandu.exception.ServiceException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/miniprogram/designplanV2")
public class DesignPlanRecommendedControllerV2 {
	 private final static String CLASS_LOG_PREFIX = "[选装网推荐方案服务]:";
	 private final static Logger logger = LoggerFactory.getLogger(DesignPlanRecommendedControllerV2.class);
	 
	 @Autowired
	 private DesignPlanRecommendedService designPlanRecommendedService;
	
	 /**
	  * 选装网 1.一键方案 2.样板方案
	  * */
	@ApiOperation(value = "一键方案、样板方案", response = ReturnData.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "spaceType", value = "空间类型   如：客餐厅 = 3",required=true, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "designPlanStyleId", value = "设计方案风格ID", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "spaceArea", value = "空间面积", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "displayType", value = "显示类型 如:样板房方案(public) 一键方案(decorate)",required=true, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "livingName", value = "小区名称 1.样方案搜索(livingName) 2.一键方案搜索(brandName,creator,livingName)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "planName", value = "方案名称(搜索),样板方案、一键方案没有这个字段(忽略掉)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "sort", value = "排序  最新(new) 最热(hot),默认最热", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "curPage", value = "当前页码",required=true, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageSize", value = "每页显示多少条记录",required=true, paramType = "query", dataType = "int")
    })
	@RequestMapping(value = "/designPlanRecommendList",method = RequestMethod.POST)
	public ResponseEnvelope designPlanRecommendList(@ModelAttribute DesignPlanRecommenInput designPlanRecommenInput){
		if(null != designPlanRecommenInput && null==designPlanRecommenInput.getSpaceType()){
			 return new ResponseEnvelope(false, "空间类型不能为空!");
		}
      	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
      	
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        
        List<DesignPlanRecommendedResult> list = designPlanRecommendedService.designPlanRecommendList(designPlanRecommenInput,null == loginUser ? null : loginUser.getId());
        
		return new ResponseEnvelope<DesignPlanRecommendedResult>(true, list);
	}
	
	/**
	 * 选装网   1.公开方案
	 * 
	 * */
	@ApiOperation(value = "公开方案", response = ReturnData.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "spaceType", value = "空间类型   如：客餐厅  卧室 卫生间等",required=true, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "designPlanStyleId", value = "设计方案风格ID", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "spaceArea", value = "空间面积", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "displayType", value = "显示类型,公开方案没有这个字段(忽略掉)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "planName", value = "方案名称(搜索)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "livingName", value = "小区名称,公开方案没有这个字段(忽略掉)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "sort", value = "排序  最新(new) 最热(hot),默认最热", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "curPage", value = "当前页码",required=true, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageSize", value = "每页显示多少条记录",required=true, paramType = "query", dataType = "int"),
    })
	@RequestMapping(value = "/planRecommendedListForFranchiser",method = RequestMethod.POST)
	public Object planRecommendedListForFranchiser(@ModelAttribute DesignPlanRecommenInput designPlanRecommenInput){
		if(null != designPlanRecommenInput && null==designPlanRecommenInput.getSpaceType()){
			 return new ResponseEnvelope(false, "空间类型不能为空!");
		}
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        
        List<DesignPlanRecommendedResult>  list = designPlanRecommendedService.designPlanRecommendOpenList(designPlanRecommenInput, null == loginUser ? null : loginUser.getId());
        
        return new ResponseEnvelope<DesignPlanRecommendedResult>(true, list);
	}
	
	
}
