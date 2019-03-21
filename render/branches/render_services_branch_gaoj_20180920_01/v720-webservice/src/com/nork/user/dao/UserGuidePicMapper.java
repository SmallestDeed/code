package com.nork.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.user.model.UserGuidePic;
import com.nork.user.model.search.UserGuidePicSearch;

/**
 * @Title: UserGuidePicMapper.java
 * @Package com.nork.user.dao
 * @Description:用户指南-用户指南图片列表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-12-07 14:42:28
 * @version V1.0
 */
@Repository
@Transactional
public interface UserGuidePicMapper {
	int insertSelective(UserGuidePic record);

	int updateByPrimaryKeySelective(UserGuidePic record);

	int deleteByPrimaryKey(Integer id);

	UserGuidePic selectByPrimaryKey(Integer id);

	int selectCount(UserGuidePicSearch userGuidePicSearch);

	List<UserGuidePic> selectPaginatedList(UserGuidePicSearch userGuidePicSearch);

	List<UserGuidePic> selectList(UserGuidePic userGuidePic);

	/**
	 * 获得排序比我大的最小的一条
	 * 
	 * @param sort
	 * @return
	 */
	UserGuidePic getNextUserGuidePic(Integer sort);

	/**
	 * 获得排序比我小的最大的一条
	 * 
	 * @param sort
	 * @return
	 */
	UserGuidePic getUpUserGuidePic(Integer sort);

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
