package com.sandu.web.goods.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.common.utils.StringUtils;
import com.sandu.goods.model.GoodsRecommend;
import com.sandu.goods.model.query.GoodsQuery;
import com.sandu.goods.model.query.GoodsRecommendQuery;
import com.sandu.goods.model.vo.GoodsRecommendVo;
import com.sandu.goods.model.vo.GoodsVo;
import com.sandu.goods.service.GoodsRecommendService;
import com.sandu.goods.service.GoodsService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.web.company.controller.CompanyShopController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "商品服务", tags = "Goods")
@RestController
@RequestMapping(value = "/v1/sandu/mini/goods")
public class GoodsController {
	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class.getName());
	@Autowired
	private GoodsService goodsService;
	
	@ApiOperation(value = "分页查询可选用的商品", response = ResultMessage.class)
    @RequestMapping(value="/getAvailableList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getAvailableList(GoodsQuery query) {
		ResultMessage result=new ResultMessage();
		Page<GoodsVo> page = goodsService.getList(query);
		if(page!=null) {
    		result.setData(page);
    	}
		result.setCode(ResultCode.Success);
		return result;
	}
}
