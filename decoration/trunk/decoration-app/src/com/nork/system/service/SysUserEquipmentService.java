package com.nork.system.service;

import java.util.List;
import java.util.Map;

import com.nork.system.model.SysUserEquipment;

public interface SysUserEquipmentService {

	int add(SysUserEquipment sysUserEquipment);

    int update(SysUserEquipment sysUserEquipment);
  
    int delete(Integer id);
        
    SysUserEquipment get(Integer id);
    
    int getCount(SysUserEquipment sysUserEquipment);
 
    List<SysUserEquipment> getList(SysUserEquipment sysUserEquipment);

    /**
     * 检测设备号
     * @param userId 用户id
     * @param deviceRestrict 设备限制值
     * @param userImei  PCI网卡Mac地址(PCI设备号)
     * @param usbTerminalImei USB网卡Mac地址(USB设备号)
     * @param equipmentType 登录设备类型  移动端 、pc端？
     * @return
     */
    Map<String,String> equipmentCheck(Integer userId, Integer deviceRestrict, String userImei, String usbTerminalImei, String equipmentType);
    
    
    /**
     * 添加授权码的时候绑定设备
     * @param userId
     * @param terminalImei
     * @return
     */
    public Map<String,String> authorizedConfigBindingEquipment(Integer userId,String terminalImei) ;

	void deleteByUserId(Integer id, String mobileEquipment);
}
