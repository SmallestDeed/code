package com.sandu.api.user.service.manage;

import com.sandu.api.user.model.SysUserEquipment;

import java.util.List;

public interface SysUserEquipmentService {


    int add(SysUserEquipment sysUserEquipment);

    int getCount(SysUserEquipment sysUserEquipment);


    /**
     * 检测设备号
     *
     * @param userId          用户id
     * @param deviceRestrict  设备限制值
     * @param userImei        PCI网卡Mac地址(PCI设备号)
     * @param usbTerminalImei USB网卡Mac地址(USB设备号)
     * @param equipmentType   登录设备类型  移动端 、pc端？
     * @return
     */
    void equipmentCheck(Long userId, Integer deviceRestrict, String userImei, String usbTerminalImei, String equipmentType);


    List<SysUserEquipment> getList(SysUserEquipment sysUserEquipment);

    void deleteByUserId(Long id, String mobileEquipment);
}
