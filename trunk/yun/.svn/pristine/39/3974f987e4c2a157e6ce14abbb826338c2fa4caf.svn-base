package com.sandu.gateway.pay.config.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.gateway.pay.config.model.BaseCompanyMiniProgramConfig;



/**
 *
 * @date 2018/6/22  14:24
 */
@Repository
public interface BaseCompanyMiniProgramConfigMapper {
    /**
     * 通过AppId查询企业微信信息
     * @param appId APPId
     * @return  BaseCompanyMiniProgramConfig
     */
    BaseCompanyMiniProgramConfig selectByAppId(@Param("appId") String appId);

}
