package com.sandu.service.system.impl;

import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.SysResLevelCfgService;
import com.sandu.constant.EquipmentConstant;
import com.sandu.service.system.dao.SysResLevelCfgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysResLevelCfgServiceImpl implements SysResLevelCfgService {

    @Autowired
    private SysResLevelCfgDao sysResLevelCfgDao;

    @Override
    public List<SysDictionary> getEquipmentNum(Long userId, String equipmentType) {
        List<SysDictionary> resList = null;
        if (userId == null || userId.intValue() <= 0) {
            return null;
        }
        String valuekey = "";
        if (EquipmentConstant.MOBILE_EQUIPMENT.equals(equipmentType)) {
            valuekey = "mobile_limit_";
        } else if (EquipmentConstant.PC_EQUIPMENT.equals(equipmentType)) {
            valuekey = "pc_limit_";
        } else {
            return null;
        }
        resList = sysResLevelCfgDao.getEquipmentNum(userId, valuekey);
        return resList;
    }


}
