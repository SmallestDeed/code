package com.sandu.service.file.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.res.model.ResFile;
import org.springframework.stereotype.Repository;

@Repository
public interface ResFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ResFile record);

    int insertSelective(ResFile record);

    ResFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResFile record);

    int updateByPrimaryKey(ResFile record);

	void updateBusinessId(@Param("resFileIdList") List<Integer> resFileIdList, @Param("businessId") Integer businessId);

	int copyByPrimaryKey(Long restoreFileId);

	List<ResFile> listResFileById(@Param("args")Set<Long> args);
	
}