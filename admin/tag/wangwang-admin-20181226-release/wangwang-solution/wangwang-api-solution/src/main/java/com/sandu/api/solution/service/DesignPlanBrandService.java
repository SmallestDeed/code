package com.sandu.api.solution.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 11:33 2018/7/12
 */
public interface DesignPlanBrandService {

    /**
     * 根据方案id集获得品牌集
     * @param planIds
     * @return
     */
    Map<Integer, String> idAndSpaceTypeMap(List<Long> planIds);

	Map<Integer, String> idAndSpaceTypeMap(List<Long> brandIdList, Integer companyId);
}
