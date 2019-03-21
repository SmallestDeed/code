package com.sandu.web.interaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.interaction.model.Collection;
import com.sandu.interaction.model.query.CollectionQuery;
import com.sandu.interaction.model.vo.CollectionVo;
import com.sandu.interaction.service.CollectionService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "收藏服务", tags = "InteractionCollection")
@RestController
@RequestMapping(value = "/v1/sandu/mini/interactionCollection")
public class CollectionController {
	@Autowired
	private CollectionService collectionService;
	@ApiOperation(value = "添加收藏", response = ResultMessage.class)
    @RequestMapping(value="/add",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultMessage detail(@RequestBody Collection collection) {
    	ResultMessage result=new ResultMessage();
    	boolean isAdded=collectionService.add(collection);
    	if(isAdded) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
	
	@ApiOperation(value = "删除收藏", response = ResultMessage.class)
    @RequestMapping(value="/delete",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage delete(long id,int type) {
    	ResultMessage result=new ResultMessage();
    	Collection collection=new Collection();
    	collection.setType(type);
    	collection.setId(id);
    	boolean isAdded=collectionService.delete(collection);
    	if(isAdded) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
	
	@ApiOperation(value = "分页查询收藏信息", response = ResultMessage.class)
    @RequestMapping(value="/list",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage list(CollectionQuery query) {
    	ResultMessage result=new ResultMessage();
    	Page<CollectionVo> page=collectionService.getList(query);
    	if(page!=null) {
    		result.setData(page);
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
}
