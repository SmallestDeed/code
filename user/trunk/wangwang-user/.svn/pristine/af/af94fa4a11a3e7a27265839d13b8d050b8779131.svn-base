package com.sandu.service.system.dao;

import com.sandu.api.system.model.SysUserLoginAggregated;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserLoginAggregatedDao {

    SysUserLoginAggregated queryUserAggregatedInfo(@Param("userId") Long userId, @Param("platformId") Long platformId);

    void insertUserLoginAggregated(SysUserLoginAggregated sl);

    int updateUserLoginAggregated(@Param("userId") Long userId, @Param("platformId")Long platformId);
}
