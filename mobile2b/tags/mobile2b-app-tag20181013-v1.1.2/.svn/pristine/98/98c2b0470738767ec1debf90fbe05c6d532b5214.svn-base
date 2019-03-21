package com.nork.customerservice.service;

import java.util.List;
import java.util.Map;

import com.nork.customerservice.model.SysFeedback;
import com.nork.customerservice.model.search.SysFeedbackSearch;

/**   
 * @Title: SysFeedbackService.java 
 * @Package com.nork.customerservice.service
 * @Description:客服中心-问题反馈Service
 * @createAuthor pandajun 
 * @CreateDate 2016-04-29 15:34:27
 * @version V1.0   
 */
public interface SysFeedbackService {
	/**
	 * 新增数据
	 *
	 * @param sysFeedback
	 * @return  int 
	 */
	public int add(SysFeedback sysFeedback);

	/**
	 *    更新数据
	 *
	 * @param sysFeedback
	 * @return  int 
	 */
	public int update(SysFeedback sysFeedback);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysFeedback 
	 */
	public SysFeedback get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysFeedback
	 * @return   List<SysFeedback>
	 */
	public List<SysFeedback> getList(SysFeedback sysFeedback);

	/**
	 *    获取数据数量
	 *
	 * @param  sysFeedback
	 * @return   int
	 */
	public int getCount(SysFeedbackSearch sysFeedbackSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysFeedback
	 * @return   List<SysFeedback>
	 */
	public List<SysFeedback> getPaginatedList(
				SysFeedbackSearch sysFeedbacktSearch);

	/**
	 * 新增问题反馈接口
	 * @author huangsongbo
	 * @param title 标题
	 * @param content 正文
	 * @param msgId msgId
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, String> verifyParamsFromCreateFeedback(String title, String content ,String msgId);
    /**
     * 修改用户所有反馈消息状态为已读
     * @param id
     */
	public void updateIsReadFeedBack(Integer userId);
	/**
	 * 获取未读客服反馈消息个数
	 * @param feedback
	 * @return
	 */
	public int countIsNotReadFeedback(SysFeedback feedback);

	/**
	 * 其他
	 * 
	 */

}
