package com.sandu.sys.dao;

import com.sandu.common.persistence.CrudDao;
import com.sandu.sys.model.SysUser;
import com.sandu.sys.model.query.SysUserQuery;
import com.sandu.sys.model.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao extends
CrudDao<SysUser,SysUserQuery,SysUserVo>{
	SysUserVo getByOpenId(SysUserQuery query);

    String selectUUIDByPrimaryKey(int userId);

	SysUserVo getUserById(@Param("userId") Long userId);

}
