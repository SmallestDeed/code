package com.sandu.home.dao;

import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.search.BaseHouseSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @version V1.0
 * @Title: BaseHouseMapper.java
 * @Package com.sandu.business.dao
 * @Description:业务-户型Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
@Repository
public interface BaseHouseMapper {
    int insertSelective(BaseHouse record);

    int updateByPrimaryKeySelective(BaseHouse record);

    int deleteByPrimaryKey(Integer id);

    BaseHouse selectByPrimaryKey(Integer id);

    int selectCount(BaseHouseSearch baseHouseSearch);

    int getCountBySearch(BaseHouseSearch baseHouseSearch);


    List<BaseHouse> selectPaginatedList(
            BaseHouseSearch baseHouseSearch);


    int updateByPrimaryKey(@Param("houseDoorCode") String houseDoorCode, @Param("id") Integer id);

    List<BaseHouse> getPaginatedListMoreInfo(BaseHouseSearch baseHouseSearch);

    List<BaseHouse> selectList(BaseHouse baseHouse);
    
	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	int getHaveSpaceCount(BaseHouseSearch baseHouseSearch);
	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch);

	int getHouseCount(BaseHouseResult baseHouseResult);

	List<BaseHouseResult> houseSearchList(BaseHouseResult baseHouseResultSearch);

    /**
     * 根据渲染方案副本ID获取房型部分信息
     * @param sceneId
     * @return
     */
    List<BaseHouse> listBaseHouseByDesignPLanReanderSceneId(@Param("list") List<String> sceneId);
}
