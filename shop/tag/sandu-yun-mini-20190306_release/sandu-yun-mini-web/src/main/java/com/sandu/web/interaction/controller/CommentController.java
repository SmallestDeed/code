package com.sandu.web.interaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.interaction.model.Comment;
import com.sandu.interaction.model.query.CommentQuery;
import com.sandu.interaction.model.vo.CommentVo;
import com.sandu.interaction.service.CommentService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "互动评论服务", tags = "InteractionComment")
@RestController
@RequestMapping(value = "/v1/sandu/mini/interactioncomment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@ApiOperation(value = "提交评论", response = ResultMessage.class)
    @RequestMapping(value="/add",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultMessage add(@RequestBody Comment comment) {
    	ResultMessage result=new ResultMessage();
    	boolean isAdded=commentService.add(comment);
    	if(isAdded) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
	
	@ApiOperation(value = "删除评论", response = ResultMessage.class)
    @RequestMapping(value="/delete",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage delete(long id,int type) {
    	ResultMessage result=new ResultMessage();
    	Comment comment=new Comment();
    	comment.setType(type);
    	comment.setId(id);
    	boolean isAdded=commentService.delete(comment);
    	if(isAdded) {
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
	
	@ApiOperation(value = "查询评论信息", response = ResultMessage.class)
    @RequestMapping(value="/list",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage list(CommentQuery query) {
    	ResultMessage result=new ResultMessage();
    	Page<CommentVo> page=commentService.getList(query);
    	if(page!=null) {
    		result.setData(page);
    		result.setCode(ResultCode.Success);
    	}
    	return result;
	}
}
