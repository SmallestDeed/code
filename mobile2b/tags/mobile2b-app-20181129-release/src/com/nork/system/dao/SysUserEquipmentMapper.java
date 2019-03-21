package com.nork.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserEquipment;
 
@Repository
@Transactional
public interface SysUserEquipmentMapper {

	
	int insertSelective(SysUserEquipment sysUserEquipment);

    int updateByPrimaryKeySelective(SysUserEquipment sysUserEquipment);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserEquipment selectByPrimaryKey(Integer id);
    
    int selectCount(SysUserEquipment sysUserEquipment);
 
    List<SysUserEquipment> selectList(SysUserEquipment sysUserEquipment);
    /**
     * 检测设备号 
	 * 通过用户id,和设备类型  删除设备号
	 * @param userId
	 * @param equipmentType
	 */
	void deleteByUserId(@Param("userId")Integer userId,@Param("equipmentType")String equipmentType);
}
