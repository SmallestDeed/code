package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.system.dao.UserAccessMapper;
import com.nork.system.model.UserAccess;
import com.nork.system.model.search.UserAccessSearch;
import com.nork.system.service.UserAccessService;

/**   
 * @Title: UserAccessServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-用户访问表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-11-26 14:17:19
 * @version V1.0   
 */
@Service("userAccessService")
public class UserAccessServiceImpl implements UserAccessService {

	private UserAccessMapper userAccessMapper;

	@Autowired
	public void setUserAccessMapper(
			UserAccessMapper userAccessMapper) {
		this.userAccessMapper = userAccessMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userAccess
	 * @return  int 
	 */
	@Override
	public int add(UserAccess userAccess) {
		userAccessMapper.insertSelective(userAccess);
		return userAccess.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param userAccess
	 * @return  int 
	 */
	@Override
	public int update(UserAccess userAccess) {
		return userAccessMapper
				.updateByPrimaryKeySelective(userAccess);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return userAccessMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserAccess 
	 */
	@Override
	public UserAccess get(Integer id) {
		return userAccessMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userAccess
	 * @return   List<UserAccess>
	 */
	@Override
	public List<UserAccess> getList(UserAccess userAccess) {
	    return userAccessMapper.selectList(userAccess);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userAccess
	 * @return   int
	 */
	@Override
	public int getCount(UserAccessSearch userAccessSearch){
		return  userAccessMapper.selectCount(userAccessSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  userAccess
	 * @return   List<UserAccess>
	 */
	@Override
	public List<UserAccess> getPaginatedList(
			UserAccessSearch userAccessSearch) {
		return userAccessMapper.selectPaginatedList(userAccessSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
