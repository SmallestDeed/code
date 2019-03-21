package com.sandu.web.interaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.interaction.model.Like;
import com.sandu.interaction.model.query.LikeQuery;
import com.sandu.interaction.service.LikeService;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "互动点赞服务", tags = "InteractionLike")
@RestController
@RequestMapping(value = "/v1/sandu/mini/interactionlike")
public class LikeController {
    @Autowired
    private LikeService likeService;
    
	@ApiOperation(value = "提交点赞", response = ResultMessage.class)
    @RequestMapping(value="/add",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultMessage add(Like like) {
    	ResultMessage result=new ResultMessage();
    	boolean isAdded=likeService.add(like);
    	if(isAdded) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
	
	@ApiOperation(value = "取消点赞", response = ResultMessage.class)
    @RequestMapping(value="/cancel",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage cancel(long id,int type) {
    	ResultMessage result=new ResultMessage();
    	Like like=new Like();
    	like.setId(id);
    	like.setLikeType(type);
    	like.setStatus(0);
    	boolean isCanceled=likeService.cancel(like);
    	if(isCanceled) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
}
