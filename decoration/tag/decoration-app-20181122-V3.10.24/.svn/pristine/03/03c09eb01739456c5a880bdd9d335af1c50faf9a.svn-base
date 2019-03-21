package com.nork.system.dao;

import com.nork.system.model.SysUserLevelConfig;
import com.nork.system.model.bo.SysUserLevelBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 项目名称：timeSpace
 * 类名称：
 * 类描述：
 * 创建人：yanghuanzhi
 * 创建时间：2017年8月15日 下午2:43:56
 * 修改时间：2017年8月15日 下午2:43:56
 * 修改备注：
 * @version
 *
 */
@Repository
public interface SysUserLevelConfigMapper {
	
    List<SysUserLevelBo> getLevelInfo(SysUserLevelConfig levelConfig);

    void updateById(@Param("id") int id, @Param("levelId") int levelId);

    void initUserLevelByLevelPriceId(@Param("userId") int userId, @Param("levelId") int levelId);
}
