package com.sandu.service.user.dao;

import com.sandu.api.user.model.SysUserEquipment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserEquipmentDao {


    int insertSelective(SysUserEquipment sysUserEquipment);

    int selectCount(SysUserEquipment sysUserEquipment);

    List<SysUserEquipment> selectList(SysUserEquipment sysUserEquipment);

    /**
     * 检测设备号
     * 通过用户id,和设备类型  删除设备号
     *
     * @param userId
     * @param equipmentType
     */
    void deleteByUserId(@Param("userId") Long userId, @Param("equipmentType") String equipmentType);
}
