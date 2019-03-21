package com.nork.system.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.system.model.ResDesignRenderScene;
 

@Repository
public interface ResDesignRenderSceneMapper {

	/**
	 * 新增一条数据
	 * @author huangsongbo
	 * @param resDesignRenderScene
	 * @return
	 */
	long insert(ResDesignRenderScene resDesignRenderScene);

	/**
	 * 根据id查找一条数据
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	ResDesignRenderScene get(Integer id);

	/**
	 * 根据id删除数据
	 * @author huangsongbo
	 * @param id
	 */
	void delete(@Param("id") Integer id);
	
}
