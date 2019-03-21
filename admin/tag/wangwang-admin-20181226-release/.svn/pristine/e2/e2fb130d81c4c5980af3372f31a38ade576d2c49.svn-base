package com.sandu.api.solution.service;


import com.sandu.api.solution.model.SpaceCommon;

import java.util.List;
import java.util.Map;

public interface SpaceCommonService {

    /**
     * 根据id集获取空间类型id地址map
     *
     * @param spaceTypeIds
     * @return Map(id, spaceTypeId)
     */
    Map<Long, Integer> idAndSpaceTypeMap(List<String> spaceTypeIds);

    /**
     * 根据functionIds获得space_common Id
     * @param functionIds
     * @return
     */
    List<SpaceCommon> getByFunctionId(List<Integer> functionIds);

    /**
     * 根据id获得space_common信息
     * @param spaceCommonId
     * @return
     */
    SpaceCommon getById(Integer spaceCommonId);
}
