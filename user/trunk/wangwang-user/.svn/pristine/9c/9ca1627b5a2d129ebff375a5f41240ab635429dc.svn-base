package com.sandu.service.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.sandu.api.system.model.SysUserBankcardInfo;
import com.sandu.api.system.search.SysUserBankcardInfoSearch;

public interface SysUserBankcardInfoMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(SysUserBankcardInfo record);

    int insertSelective(SysUserBankcardInfo record);

    SysUserBankcardInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserBankcardInfo record);

    int updateByPrimaryKey(SysUserBankcardInfo record);

	List<SysUserBankcardInfo> selectListBySearch(SysUserBankcardInfoSearch sysUserBankcardInfoSearch);

	@Update("update sys_user_bankcard_info set is_deleted = 1 where id = #{id} and user_id = #{userId}")
	int deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

	int selectCountBySearch(SysUserBankcardInfoSearch sysUserBankcardInfoSearch);

}