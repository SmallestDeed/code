package com.sandu.home.service;

import com.sandu.home.model.HouseSpaceNew;
import com.sandu.home.model.search.HouseSpaceNewSearch;

import java.util.List;


/**
 * @version V1.0
 * @Title: HouseSpaceNewService.java
 * @Package com.sandu.business.service
 * @Description:户型空间-户型空间U3DService
 * @createAuthor pandajun
 * @CreateDate 2017-05-18 15:19:17
 */
public interface HouseSpaceNewService {
    /**
     * 新增数据
     *
     * @param houseSpaceNew
     * @return int
     */
    int add(HouseSpaceNew houseSpaceNew);

    /**
     * 更新数据
     *
     * @param houseSpaceNew
     * @return int
     */
    int update(HouseSpaceNew houseSpaceNew);

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
     * @return HouseSpaceNew
     */
    HouseSpaceNew get(Integer id);

    /**
     * 所有数据
     *
     * @param houseSpaceNew
     * @return List<HouseSpaceNew>
     */
    List<HouseSpaceNew> getList(HouseSpaceNew houseSpaceNew);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(HouseSpaceNewSearch houseSpaceNewSearch);

    /**
     * 分页获取数据
     *
     * @return List<HouseSpaceNew>
     */
    List<HouseSpaceNew> getPaginatedList(
            HouseSpaceNewSearch houseSpaceNewtSearch);

    /**
     * 其他
     *
     */

    /**
     * 通过户型ID获取该户型下所有的空间类型
     *
     * @param houseId 户型ID
     * @return
     */
    List<String> getSpaceTypeListByHouseId(Integer houseId);
    
    
    /**
     * 通过户型ID获取该户型下有哪些空间类型 
     * @param houseId
     * @return
     * add by yangzhun
     * 2018年1月17日15:09:55
     */
    List<String> getSpaceTypeByHouseId(Integer houseId);
}
