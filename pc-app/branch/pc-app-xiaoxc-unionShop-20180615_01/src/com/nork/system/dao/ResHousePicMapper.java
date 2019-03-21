package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.ResHousePic;
import com.nork.system.model.search.ResHousePicSearch;

/**
 * @Title: ResHousePicMapper.java
 * @Package com.nork.system.dao
 * @Description:系统模块-户型、空间图片资源表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-08-13 16:34:09
 * @version V1.0
 */
@Repository
@Transactional
public interface ResHousePicMapper {
	int insertSelective(ResHousePic record);

	int updateByPrimaryKeySelective(ResHousePic record);

	int deleteByPrimaryKey(Integer id);

	ResHousePic selectByPrimaryKey(Integer id);

	int selectCount(ResHousePicSearch resHousePicSearch);

	List<ResHousePic> selectPaginatedList(ResHousePicSearch resHousePicSearch);

	List<ResHousePic> selectList(ResHousePic resHousePic);
}
