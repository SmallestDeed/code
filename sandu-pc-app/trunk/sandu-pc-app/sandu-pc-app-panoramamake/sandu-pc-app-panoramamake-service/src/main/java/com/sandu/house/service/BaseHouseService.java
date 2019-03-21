package com.sandu.house.service;

import com.sandu.house.model.input.BaseHouseSearch;
import com.sandu.house.model.output.BaseHouseVo;
import com.sandu.system.model.bo.HouseAreaBo;

import java.util.List;
import java.util.Map;

public interface BaseHouseService {

    /**
     * 获取渲染过的户型数量
     * @param search
     * @return
     */
    int getMyUsedCount(BaseHouseSearch search);

    /**
     * 获取渲染过的户型
     * @param search
     * @return
     */
    List<BaseHouseVo> getMyUsed(BaseHouseSearch search);

    /**
     * 查找户型下各类别房型的空间数量,返回处理过的集合
     * @param houseIds 户型Id集合
     * @return
     */
    Map<Integer,String> jointHouseSpaceNum(List<Integer> houseIds);

    /**
     * 查找户型区域地址名称
     * @param houseAreaInfoMap
     * @return
     */
    Map<Integer,HouseAreaBo> getHouseAreaInfoMap(Map<Integer,String> houseAreaInfoMap);
}
