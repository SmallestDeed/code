package com.nork.base.dao;


import com.nork.base.model.BasePlatform;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author yzw
 * @Date 2018/1/8 15:42
 */

@Repository
@Transactional
public interface BasePlatformMapper {

    /**
     * 删除
     *
     * @param id 平台id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加
     *
     * @param record
     * @return
     */
    int insertSelective(BasePlatform record);

    /**
     * 获取
     *
     * @param id 平台id
     * @return
     */
    BasePlatform selectByPrimaryKey(Integer id);

    /**
     * 更新
     *
     * @param record 平台id
     * @return
     */
    int updateByPrimaryKeySelective(BasePlatform record);

    /**
     * 获取平台信息
     *
     * @param platformCode 平台编码
     * @return
     */
    public BasePlatform getBasePlatformByCode(String platformCode);

	List<BasePlatform> findAllByPlatformCode(@Param("platformCode") String code);
}
