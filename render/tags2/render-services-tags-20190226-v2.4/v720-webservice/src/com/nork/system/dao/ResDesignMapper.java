package com.nork.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.ResDesign;
 

@Repository
@Transactional
public interface ResDesignMapper {

	int insertSelective(ResDesign resDesign);
	
	int updateByPrimaryKeySelective(ResDesign resDesign);
	 
	int deleteByPrimaryKey(Integer id);
	
	ResDesign selectByPrimaryKey(Integer id);
	
	List<ResDesign> selectList(ResDesign resDesign);
	
	int selectCount(ResDesign resDesign);

	ResDesign getByOldId(Integer oldId);

	int filePathCount(String filePath);

    void batchDelTempDesignConfig(@Param("delConfigList") List<Integer> delConfigList);
}
