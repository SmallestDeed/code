package com.nork.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.user.dao.UserGuideMapper;
import com.nork.user.model.UserGuide;
import com.nork.user.model.search.UserGuideSearch;
import com.nork.user.service.UserGuideService;

/**
 * @Title: UserGuideServiceImpl.java
 * @Package com.nork.user.service.impl
 * @Description:用户指南-用户指南ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-11-21 20:22:33
 * @version V1.0
 */
@Service("userGuideService")
public class UserGuideServiceImpl implements UserGuideService {

	private UserGuideMapper userGuideMapper;

	@Autowired
	public void setUserGuideMapper(UserGuideMapper userGuideMapper) {
		this.userGuideMapper = userGuideMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userGuide
	 * @return int
	 */
	@Override
	public int add(UserGuide userGuide) {
		userGuideMapper.insertSelective(userGuide);
		return userGuide.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param userGuide
	 * @return int
	 */
	@Override
	public int update(UserGuide userGuide) {
		return userGuideMapper.updateByPrimaryKeySelective(userGuide);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return userGuideMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return UserGuide
	 */
	@Override
	public UserGuide get(Integer id) {
		return userGuideMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param userGuide
	 * @return List<UserGuide>
	 */
	@Override
	public List<UserGuide> getList(UserGuide userGuide) {
		return userGuideMapper.selectList(userGuide);
	}

	/**
	 * 获取数据数量
	 *
	 * @param userGuide
	 * @return int
	 */
	@Override
	public int getCount(UserGuideSearch userGuideSearch) {
		return userGuideMapper.selectCount(userGuideSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param userGuide
	 * @return List<UserGuide>
	 */
	@Override
	public List<UserGuide> getPaginatedList(UserGuideSearch userGuideSearch) {
		return userGuideMapper.selectPaginatedList(userGuideSearch);
	}

	@Override
	public UserGuide getNextUserGuide(Integer sort) {
		return userGuideMapper.getNextUserGuide(sort);
	}

	/**
	 * type ：上移一条， 下移一条，置顶 userGuide_current： 需要上移的 userGuide_replace： 被替换的
	 */
	@Override
	public int exchangeSort(Integer id, Integer sort, String type) {
		UserGuide userGuide_replace = null;
		UserGuide userGuide_current = new UserGuide();
		int state = 2;

		if ("up".equals(type)) {
			userGuide_replace = userGuideMapper.getUpUserGuide(sort);
			if(userGuide_replace == null)
				return 3;
			userGuide_current.setSort(userGuide_replace.getSort());
			userGuide_replace.setSort(sort);
		} else if ("down".equals(type)) {
			userGuide_replace = userGuideMapper.getNextUserGuide(sort);
			/* 最后一位,无法下移 */
			if (userGuide_replace == null)
				return 3;
			userGuide_current.setSort(userGuide_replace.getSort());
			userGuide_replace.setSort(sort);
		} else if ("one".equals(type)) {
			userGuideMapper.placedTop();
			userGuide_current.setSort(1);
		} else {
			return state;
		}

		try {
			userGuide_current.setId(id);
			userGuideMapper.updateByPrimaryKeySelective(userGuide_current);
			if (!"one".equals(type)) {
				userGuideMapper.updateByPrimaryKeySelective(userGuide_replace);
			}
			state = 1;
		} catch (Exception e) {

		}
		return state;
	}

	@Override
	public int getMaxSort() {
		return userGuideMapper.getMaxSort();
	}

	/**
	 * 其他
	 * 
	 */

}
