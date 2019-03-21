package com.sandu.web.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.interaction.service.CollectionService;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.search.model.SearchHot;
import com.sandu.search.model.SearchUser;
import com.sandu.search.model.query.SearchHotQuery;
import com.sandu.search.model.query.SearchUserQuery;
import com.sandu.search.model.vo.SearchHotVo;
import com.sandu.search.model.vo.SearchUserVo;
import com.sandu.search.service.SearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "搜索服务", tags = "Search")
@RestController
@RequestMapping(value = "/v1/sandu/mini/search")
public class SearchController {
	@Autowired
	private SearchService searchService;
	@ApiOperation(value = "记录用户搜索关键词", response = ResultMessage.class)
    @RequestMapping(value="/add",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultMessage saveSearchUser(SearchUser search) {
		ResultMessage result=new ResultMessage();
		boolean addFlag = searchService.saveSearchUser(search);
		if(addFlag) {
			result.setCode(ResultCode.Success);
		}
		return result;
	}
	@ApiOperation(value = "获取热门搜索", response = ResultMessage.class)
    @RequestMapping(value="/getHotList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getHotList() {
		ResultMessage result=new ResultMessage();
		List<SearchHotVo> list = searchService.getHotList();
		if(list!=null && list.size()>0) {
			result.setData(list);
			result.setCode(ResultCode.Success);
		}
		return result;
	}
	@ApiOperation(value = "获取用户历史搜索", response = ResultMessage.class)
    @RequestMapping(value="/getHistoryList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage getHistoryList(long userId) {
		ResultMessage result=new ResultMessage();
		List<SearchUserVo> list = searchService.getHistoryList(userId);
		if(list!=null && list.size()>0) {
			result.setData(list);
			result.setCode(ResultCode.Success);
		}
		return result;
	}
	
	@ApiOperation(value = "删除用户搜索", response = ResultMessage.class)
    @RequestMapping(value="/deleteSearchUser",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage deleteSearchUser(SearchUser search) {
		ResultMessage result=new ResultMessage();
		boolean deleteFlag = searchService.deleteSearchUser(search);
		if(deleteFlag) {
			result.setCode(ResultCode.Success);
		}
		return result;
	}
}
