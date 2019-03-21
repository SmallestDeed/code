package com.nork.system.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.UserSpellingflowerCollet;
import com.nork.system.model.search.UserSpellingflowerColletSearch;

import java.util.List;


/**   
 * @Title: UserSpellingflowerColletService.java 
 * @Package com.nork.system.service
 * @Description:拼花-我的瓷砖拼花收藏Service
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:38:44
 * @version V1.0   
 */
public interface UserSpellingflowerColletService {
	/**
	 * 新增数据
	 *
	 * @param userSpellingflowerCollet
	 * @return  int 
	 */
	public ResponseEnvelope add(UserSpellingflowerCollet userSpellingflowerCollet, LoginUser loginUser);

	/**
	 *    更新数据
	 *
	 * @param userSpellingflowerCollet
	 * @return  int 
	 */
	public ResponseEnvelope update(UserSpellingflowerCollet userSpellingflowerCollet);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public ResponseEnvelope delete(Integer id,Integer UserId,String msgId);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserSpellingflowerCollet 
	 */
	public UserSpellingflowerCollet get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  userSpellingflowerCollet
	 * @return   List<UserSpellingflowerCollet>
	 */
	public List<UserSpellingflowerCollet> getList(UserSpellingflowerCollet userSpellingflowerCollet);

	/**
	 *    获取数据数量
	 *
	 * @param  userSpellingflowerColletSearch
	 * @return   int
	 */
	public Integer getCount(UserSpellingflowerColletSearch userSpellingflowerColletSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  userSpellingflowerCollettSearch
	 * @return   返回数据
	 */
	public ResponseEnvelope getPaginatedList(UserSpellingflowerColletSearch userSpellingflowerCollettSearch);

	/**
	 * 其他
	 * 
	 */

}
