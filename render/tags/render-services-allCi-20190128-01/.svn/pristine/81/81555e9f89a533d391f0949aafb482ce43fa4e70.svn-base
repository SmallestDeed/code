package com.nork.customerservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.customerservice.dao.SysFeedbackMapper;
import com.nork.customerservice.model.SysFeedback;
import com.nork.customerservice.model.search.SysFeedbackSearch;
import com.nork.customerservice.service.SysFeedbackService;

/**   
 * @Title: SysFeedbackServiceImpl.java 
 * @Package com.nork.customerservice.service.impl
 * @Description:客服中心-问题反馈ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-04-29 15:34:27
 * @version V1.0   
 */
@Service("sysFeedbackService")
public class SysFeedbackServiceImpl implements SysFeedbackService {

	private SysFeedbackMapper sysFeedbackMapper;

	@Autowired
	public void setSysFeedbackMapper(
			SysFeedbackMapper sysFeedbackMapper) {
		this.sysFeedbackMapper = sysFeedbackMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysFeedback
	 * @return  int 
	 */
	@Override
	public int add(SysFeedback sysFeedback) {
		sysFeedbackMapper.insertSelective(sysFeedback);
		return sysFeedback.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysFeedback
	 * @return  int 
	 */
	@Override
	public int update(SysFeedback sysFeedback) {
		return sysFeedbackMapper
				.updateByPrimaryKeySelective(sysFeedback);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysFeedbackMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysFeedback 
	 */
	@Override
	public SysFeedback get(Integer id) {
		return sysFeedbackMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysFeedback
	 * @return   List<SysFeedback>
	 */
	@Override
	public List<SysFeedback> getList(SysFeedback sysFeedback) {
	    return sysFeedbackMapper.selectList(sysFeedback);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysFeedback
	 * @return   int
	 */
	@Override
	public int getCount(SysFeedbackSearch sysFeedbackSearch){
		return  sysFeedbackMapper.selectCount(sysFeedbackSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysFeedback
	 * @return   List<SysFeedback>
	 */
	@Override
	public List<SysFeedback> getPaginatedList(
			SysFeedbackSearch sysFeedbackSearch) {
		return sysFeedbackMapper.selectPaginatedList(sysFeedbackSearch);
	}

	/**
	 * 新建问题反馈接口验证参数
	 * @author huangsongbo
	 * @param title 标题
	 * @param content 正文
	 * @return
	 */
	public Map<String, String> verifyParamsFromCreateFeedback(String title, String content, String msgId) {
		Map<String,String> map=new HashMap<String,String>();
		String success="false";
		String msg="";
		if(StringUtils.isBlank(msgId)){
			msg="参数msgId不能为空";
		}else if(StringUtils.isBlank(title)){
			msg="参数title不能为空";
		}else if(StringUtils.isBlank(content)){
			msg="参数content不能为空";
		}else{
			success="true";
		}
		map.put("success", success);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 修改客服反馈消息为已读状态
	 */
	@Override
	public void updateIsReadFeedBack(Integer userId) {
		sysFeedbackMapper.updateIsReadFeedBack(userId);
	}
	/**
	 * 获取未读客服反馈消息个数
	 */
	@Override
	public int countIsNotReadFeedback(SysFeedback feedback) {
		int ret=0;
		List<SysFeedback> ls=new ArrayList<SysFeedback>();
		ls=sysFeedbackMapper.selectList(feedback);
		//最多只显示十条最新消息
		ret=ls.size()>10?10:ls.size();
		return ret;
	}

}
