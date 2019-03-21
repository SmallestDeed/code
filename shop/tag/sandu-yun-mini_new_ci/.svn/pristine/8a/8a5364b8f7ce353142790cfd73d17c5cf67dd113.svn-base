package com.sandu.web.goods.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.common.utils.StringUtils;
import com.sandu.goods.model.GoodsRecommend;
import com.sandu.goods.model.query.GoodsRecommendQuery;
import com.sandu.goods.model.vo.GoodsRecommendVo;
import com.sandu.goods.service.GoodsRecommendService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.web.company.controller.CompanyShopController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "商品推荐服务", tags = "GoodsRecommend")
@RestController
@RequestMapping(value = "/v1/sandu/mini/goodsrecommend")
public class GoodsRecommendController {
	private static final Logger logger = LoggerFactory.getLogger(GoodsRecommendController.class.getName());
	@Autowired
	private GoodsRecommendService goodsRecommendService;
	
	@ApiOperation(value = "批量添加商品推荐", response = ResultMessage.class)
    @RequestMapping(value="/batchAdd",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultMessage batchAdd(@RequestBody GoodsRecommendQuery query) {
		ResultMessage result=new ResultMessage();
		goodsRecommendService.batchDelete(query);
		List<GoodsRecommend> lstRecommend=new ArrayList<GoodsRecommend>();
		if(StringUtils.isNotEmpty(query.getGoodsIds())) {
			String[] goodsIds=query.getGoodsIds().split(",");
			for(String goodId:goodsIds) {
				GoodsRecommend rec=new GoodsRecommend();
				rec.preInsert();
				rec.setCompanyId(query.getCompanyId().longValue());
				rec.setGoodsId(Long.parseLong(goodId));
				rec.setShopId(query.getShopId().longValue());
				lstRecommend.add(rec);
			}
		}
		goodsRecommendService.batchAdd(lstRecommend,query.getCompanyId().longValue());
		result.setCode(ResultCode.Success);
		return result;
	}
	@ApiOperation(value = "批量删除商品推荐", response = ResultMessage.class)
    @RequestMapping(value="/batchDelete",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultMessage batchDelete(@RequestBody GoodsRecommendQuery query) {
		ResultMessage result=new ResultMessage();
		goodsRecommendService.batchDelete(query);
		result.setCode(ResultCode.Success);
		return result;
	}
	
	@ApiOperation(value = "分页查询产品推荐", response = ResultMessage.class)
    @RequestMapping(value="/getList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getList(GoodsRecommendQuery query) {
		ResultMessage result=new ResultMessage();
		Page<GoodsRecommendVo> page = goodsRecommendService.getList(query);
		if(page!=null) {
    		result.setData(page);
    	}
		result.setCode(ResultCode.Success);
		return result;
	}
	
	@ApiOperation(value = "获取产品推荐", response = ResultMessage.class)
    @RequestMapping(value="/getTopList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getTopList(GoodsRecommendQuery query) {
		ResultMessage result=new ResultMessage();
        Map<String, Object> dataMap = goodsRecommendService.getTopList(query);

        result.setData(dataMap);
    	result.setCode(ResultCode.Success);
		return result;
	}
	

}
