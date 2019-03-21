package com.nork.system.service;

import java.util.List;

import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageRecieveSearch;

/**   
 * @Title: BaseMessageRecieveService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-消息接收表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:08:13
 * @version V1.0   
 */
public interface BaseMessageRecieveService {
	/**
	 * 新增数据
	 *
	 * @param baseMessageRecieve
	 * @return  int 
	 */
	public int add(BaseMessageRecieve baseMessageRecieve);

	/**
	 *    更新数据
	 *
	 * @param baseMessageRecieve
	 * @return  int 
	 */
	public int update(BaseMessageRecieve baseMessageRecieve);

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
	 * @return  BaseMessageRecieve 
	 */
	public BaseMessageRecieve get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseMessageRecieve
	 * @return   List<BaseMessageRecieve>
	 */
	public List<BaseMessageRecieve> getList(BaseMessageRecieve baseMessageRecieve);

	/**
	 *    获取数据数量
	 *
	 * @param  baseMessageRecieve
	 * @return   int
	 */
	public int getCount(BaseMessageRecieveSearch baseMessageRecieveSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseMessageRecieve
	 * @return   List<BaseMessageRecieve>
	 */
	public List<BaseMessageRecieve> getPaginatedList(
				BaseMessageRecieveSearch baseMessageRecievetSearch);

	/**其他*****************/
	 
	/**
	 * 查询消息列表
	 * @param baseMessage
	 * @return
	 */
	public List<UserMessageDesignPlan> getMessageList(UserMessageDesignPlan userMessageDesignPlan);
	/**
	 * 获取接收者
	 * @param userMessageDesignPlan
	 * @return
	 */
//	public List<UserMessageDesignPlan> getReceiverList(UserMessageDesignPlan userMessageDesignPlan);

	/**
	 * 通过消息ID和用户ID查询一行数据
	 * @param baseMessageRecieve
	 * @return
	 */
	BaseMessageRecieve getOneByMessageIdAndUserId(BaseMessageRecieve baseMessageRecieve);
}
