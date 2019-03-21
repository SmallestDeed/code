package com.sandu.service.base.dao;

import com.sandu.api.base.model.BasePlatform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiaoxc on 2018/6/2 0002.
 */
@Repository
public interface BasePlatformMapper {


    /**
     * 通过平台编码获取平台信息
     * @param platformCode 平台编码
     * @return
     */
    BasePlatform selectPlatformInfoByPlatformCode(@Param("platformCode") String platformCode);

    List<BasePlatform> findAllByPlatformCode(String code);
}
