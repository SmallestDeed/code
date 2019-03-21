package com.sandu.api.system.service;

import com.sandu.api.system.model.SysDictionary;

import java.util.List;

public interface SysResLevelCfgService {
    /**
     * 获取限制设备数量
     *
     * @param userId
     * @param equipmentType
     * @return
     */
    public List<SysDictionary> getEquipmentNum(Long userId, String equipmentType);


}
