package com.nork.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.user.model.UserGuide;
import com.nork.user.model.search.UserGuideSearch;

/**
 * @Title: UserGuideMapper.java
 * @Package com.nork.user.dao
 * @Description:用户指南-用户指南Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-11-21 20:22:33
 * @version V1.0
 */
@Repository
@Transactional
public interface UserGuideMapper {
	int insertSelective(UserGuide record);

	int updateByPrimaryKeySelective(UserGuide record);

	int deleteByPrimaryKey(Integer id);

	UserGuide selectByPrimaryKey(Integer id);

	int selectCount(UserGuideSearch userGuideSearch);

	List<UserGuide> selectPaginatedList(UserGuideSearch userGuideSearch);

	List<UserGuide> selectList(UserGuide userGuide);

	/**
	 * 获得排序比我大的最小的一条
	 * 
	 * @param sort
	 * @return
	 */
	UserGuide getNextUserGuide(Integer sort);

	/**
	 * 获得排序比我小的最大的一条
	 * 
	 * @param sort
	 * @return
	 */
	UserGuide getUpUserGuide(Integer sort);

	/**
	 * 所有排序+1
	 */
	void placedTop();

	/**
	 * 获得最大排序
	 * 
	 * @return
	 */
	int getMaxSort();

	/**
	 * 其他
	 * 
	 */
}
