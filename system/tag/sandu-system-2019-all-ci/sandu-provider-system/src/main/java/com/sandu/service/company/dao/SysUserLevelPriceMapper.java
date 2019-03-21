package com.sandu.service.company.dao;

import com.sandu.user.model.SysUserLevelPrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目名称：timeSpace
 * 类名称：
 * 类描述：
 * 创建人：yanghuanzhi
 * 创建时间：2017年8月15日 下午2:43:56
 * 修改时间：2017年8月15日 下午2:43:56
 * 修改备注：
 */
@Repository
public interface SysUserLevelPriceMapper {
	List<SysUserLevelPrice> getListByUserId(@Param("userId") Integer userId);

	SysUserLevelPrice getIdByUserType(@Param("userType") int userType);
}
