package com.sandu.api.platform.service;

import com.sandu.api.platform.model.Platform;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 * @version V1.0
 * @Title: PlatformService.java
 * @Package com.nork.platform.service
 * @Description:基础-平台表Service
 * @createAuthor pandajun
 * @CreateDate 2017-12-29 10:16:41
 */
public interface PlatformService {
    /**
     * 新增数据
     *
     * @param basePlatform
     * @return int
     */
    long add(Platform basePlatform);

    /**
     * 更新数据
     *
     * @param basePlatform
     * @return int
     */
    int update(Platform basePlatform);

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
     * @return Platform
     */
    Platform get(Integer id);

    /**
     * 所有数据
     *
     * @param platform
     * @return List<BasePlatform>
     */
    List<Platform> getList(Platform platform);


    /**
     * 根据编码获取平台所属类别
     *
     * @param platformCode
     * @return
     */
    Platform getByPlatformCode(String platformCode);

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
    /**
     * 其他
     *
     */
	Map<Integer, String> getUpDownPlatformIdAndName();
	
	public String getPlatformBussinessTypeById(String platformId);


}
