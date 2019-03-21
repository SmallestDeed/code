package com.sandu.home.service;

import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.search.BaseHouseSearch;

import java.util.List;


/**
 * @version V1.0
 * @Title: BaseHouseService.java
 * @Package com.sandu.business.service
 * @Description:业务-户型Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
public interface BaseHouseService {
    /**
     * 新增数据
     *
     * @param baseHouse
     * @return int
     */
    int add(BaseHouse baseHouse);

    /**
     * 更新数据
     *
     * @param baseHouse
     * @return int
     */
    int update(BaseHouse baseHouse);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseHouse
     */
    BaseHouse get(Integer id);

    /**
     * 所有数据
     *
     * @param baseHouse
     * @return List<BaseHouse>
     */
    List<BaseHouse> getList(BaseHouse baseHouse);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(BaseHouseSearch baseHouseSearch);


    /**
     * 分页获取数据
     *
     * @return List<BaseHouse>
     */
    List<BaseHouse> getPaginatedList(
            BaseHouseSearch baseHousetSearch);

    /**
     * getPaginatedList改造,得到更多的信息
     *
     * @param baseHouseSearch
     * @return
     */
    List<BaseHouse> getPaginatedListMoreInfo(BaseHouseSearch baseHouseSearch);

    /**
     * 查找小区内的户型信息
     *
     * @param baseHouseSearch
     * @return
     */
    List<BaseHouse> getBaseHousesByLivingId(BaseHouseSearch baseHouseSearch);

    /**
     * 小区搜索
     *
     * @param baseHouse
     * @return List<BaseHouse>
     */
    List<BaseHouseResult> getHouseList(
            BaseHouseResult baseHouseResult);


    /**
     * 获取拥有空间的户型
     *
     * @param baseHouseSearch
     * @return
     */
    int getHaveSpaceCount(BaseHouseSearch baseHouseSearch);

    /**
     * 获取拥有空间的户型
     *
     * @param baseHouseSearch
     * @return
     */
    List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch);

    int getHouseCount(BaseHouseResult baseHouseResultSearch);

    /**
     * 根据渲染方案副本主键集合查询所关联的房型信息
     * @param sceneIds 方案副本ID集合
     * @return
     */
    List<BaseHouse> listBaseHouseByDesignPLanReanderSceneId(List<String> sceneIds);

}
