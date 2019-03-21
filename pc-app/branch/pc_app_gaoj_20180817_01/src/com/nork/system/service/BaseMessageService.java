package com.nork.system.service;

import java.util.List;

import com.nork.system.model.BaseMessage;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageSearch;
import com.nork.task.model.SysTask;

/**   
 * @Title: BaseMessageService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-消息表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 14:30:45
 * @version V1.0   
 */
public interface BaseMessageService {
	/**
	 * 新增数据
	 *
	 * @param baseMessage
	 * @return  int 
	 */
	public int add(BaseMessage baseMessage);

	/**
	 *    更新数据
	 *
	 * @param baseMessage
	 * @return  int 
	 */
	public int update(BaseMessage baseMessage);

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
	 * @return  BaseMessage 
	 */
	public BaseMessage get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseMessage
	 * @return   List<BaseMessage>
	 */
	public List<BaseMessage> getList(BaseMessage baseMessage);
	
	/**
	 * 查询站内消息列表
	 * @param baseMessage
	 * @return
	 */
	public List<UserMessageDesignPlan> getMessageList(UserMessageDesignPlan userMessageDesignPlan);
	/**
	 * 通过session获得用户id，查询接收表的消息id
	 * @param userMessageDesignPlan
	 * @return
	 */
	public List<UserMessageDesignPlan> getReceiverIdList(UserMessageDesignPlan userMessageDesignPlan);
	/**
	 * 获得web页面的私信列表
	 * @param userMessageDesignPlan
	 * @return
	 */
	public List<UserMessageDesignPlan> getWebLettersList(UserMessageDesignPlan userMessageDesignPlan);
	/**
	 * 获取接收者
	 * @param userMessageDesignPlan
	 * @return
	 */
//	public List<UserMessageDesignPlan> getReceiverList(UserMessageDesignPlan userMessageDesignPlan);

	/**
	 *    获取数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	public int getCount(BaseMessageSearch baseMessageSearch);
	/**
	 *    获取用户所有消息数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	public int getCountAllMessage(BaseMessageSearch baseMessageSearch);
	/**
	 *    获取用户所有未读消息数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	public int getCountUnreaded(BaseMessageSearch baseMessageSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseMessage
	 * @return   List<BaseMessage>
	 */
	public List<BaseMessage> getPaginatedList(
				BaseMessageSearch baseMessagetSearch);
	
	/**
	 * 分页获取用户所有消息数据
	 * 
	 * @param  baseMessage
	 * @return   List<BaseMessage>
	 */
	public List<BaseMessage> getAllMessage(BaseMessageSearch baseMessagetSearch);

	/**
	 * 其他
	 * 
	 */
	
	/**
	 *    获取未读消息数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	public int getUnReadMessageCount(BaseMessage baseMessage);

	/**
	 *
	 * @param task
	 * @param state
	 * @author  yanghz
	 * @return
	 */
	public int sendRenderMessage(SysTask task, Integer state,String msg);

}
