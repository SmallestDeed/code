package com.sandu.service.templet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.house.model.DrawDesignTemplet;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawDesignTempletMapper {
	Integer deleteByPrimaryKey(Long id);

    Integer insert(DrawDesignTemplet record);

    Integer insertSelective(DrawDesignTemplet record);

    DrawDesignTemplet selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(DrawDesignTemplet record);

    Integer updateByPrimaryKey(DrawDesignTemplet record);

	List<DrawDesignTemplet> findAllBySpaceCommonId(@Param("drawSpaceCommonId") Long drawSpaceCommonId);

	void transformToDesignTemplet(DrawDesignTemplet drawDesignTemplet);

	Integer deleteDrawDesignTemplet(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);

	Integer deleteDesignTemplet(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);
	
	Integer emptyDesignTemplet(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);

	List<Long> getDeleteDesignTemplet(@Param("args")List<Long> args);

	List<Long> getEmptyDesignTemplet(@Param("emptySpaces")List<Long> emptySpaces);

	List<Long> getEmptyDrawDesignTemplet(@Param("emptySpaces")List<Long> emptySpaces);

    Integer batchSaveDrawDesignTemplet(List<DrawDesignTemplet> drawDesignTemplets);

    DrawDesignTemplet getDrawDesignTempletBySpaceCommonId(Long drawSpaceCommonId);
}