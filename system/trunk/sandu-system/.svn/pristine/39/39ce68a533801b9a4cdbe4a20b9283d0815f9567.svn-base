package com.sandu.service.platform.dao;

import com.sandu.api.platform.model.BasePlatform;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author WangHaiLin
 * @date 2018/6/4  21:17
 */
@Repository
public interface BasePlatformMapper {
    /**
     * 通过平台编码查询平台
     * @param platformCode 平台编码
     * @return basePlatform 平台信息
     */
    BasePlatform getByPlatformCode(@Param("platformCode") String platformCode);

    BasePlatform selectByPrimaryKey(Integer id);

    List<BasePlatform> selectList(BasePlatform platform);

    List<Integer> groupPlatformWithBussinessType(@Param("list") List<Integer> usedPlatformIds,
                                                 @Param("bussinessType") String bussinessType);

    List<Integer> getPlatformIdsByBussinessTypes(@Param("list") List<String> bussinessTypes);

    Integer getOnePlatformIdByBussinessType(@Param("type") String type);

    List<Map<String, Object>> getAllPlatformIdAndName();

    List<BasePlatform> getPlatformIdAndNameByBussinessType(String type);
}
