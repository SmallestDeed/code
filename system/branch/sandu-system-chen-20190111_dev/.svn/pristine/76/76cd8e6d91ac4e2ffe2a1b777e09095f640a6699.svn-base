package com.sandu.api.platform.service;

import com.sandu.api.platform.model.BasePlatform;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author WangHaiLin
 * @date 2018/6/4  21:14
 */
@Component
public interface BasePlatformService {
    /**
     * 根据编码获取平台所属类别
     * @param platformCode
     * @return
     */
    BasePlatform getByPlatformCode(String platformCode);

    /**
     * 获取数据详情
     *
     * @param id
     * @return Platform
     */
    BasePlatform get(Integer id);

    /**
     * 所有数据
     *
     * @param platform
     * @return List<BasePlatform>
     */
    List<BasePlatform> getList(BasePlatform platform);

    /**
     * 将平台ids归类
     *
     * @param usedPlatformIds
     * @return map<业务类型   ,   对应的ID集合>
     */
    Map<String, List<Integer>> groupPlatformWithBussinessType(List<Integer> usedPlatformIds);

    /**
     * 根据业务类型获取平台ID
     *
     * @param bussinessTypes
     * @return
     */
    List<Integer> getPlatformIdsByBussinessTypes(List<String> bussinessTypes);

    /**
     * 根据业务类型获取某一平台ID
     *
     * @param s
     * @return
     */
    Integer getOnePlatformIdByBussinessType(String s);

    /**
     * 获取所有平台ID和name
     *
     * @return
     */
    Map<Integer, String> getAllPlatformIdAndName();

    /**
     * 根据平台业务类型获取其下平台ID和name
     *
     * @param type
     * @return
     */
    Map<Integer, String> getPlatformIdAndNameByBussinessType(String type);
}
