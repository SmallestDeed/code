package com.sandu.service.miniprogram.dao;

import com.sandu.api.miniprogram.model.UnionPlatform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yuxc
 * @date 2018/9/25
 */
@Repository
public interface UnionPlatformMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UnionPlatform record);

    int insertSelective(UnionPlatform record);

    UnionPlatform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UnionPlatform record);

    int updateByPrimaryKey(UnionPlatform record);

    /**
     * 根据平台名称查询平台
     * @param platName
     * @return
     */
    UnionPlatform selectByName(@Param("platName") String platName);

    /**
     * 根据平台名称增加点击量
     * @param platName
     * @return
     */
    int updateByName(@Param("platName") String platName);
}