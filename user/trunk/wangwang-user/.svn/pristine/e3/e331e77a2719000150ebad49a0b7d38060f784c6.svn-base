/**
 * 文件名：SysResLevelCfgMapper.java
 * <p>
 * 版本信息：
 * 日期：2017年8月9日
 * Copyright 足下 Corporation 2017
 * 版权所有
 */
package com.sandu.service.system.dao;

import com.sandu.api.system.model.SysDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysResLevelCfgDao {

    /**
     * 获取限制设备数量
     * @param userId
     * @param valuekey
     * @return
     */
    public List<SysDictionary> getEquipmentNum(@Param("userId") Long userId, @Param("valuekey") String valuekey);
}
