package com.sandu.designplan.dao;

import com.sandu.designplan.model.DesignPlanLike;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @Title: Mobile2CDesignPlanLikeMapper.java
 * @Package com.nork.设计方案.dao
 * @Description:设计方案点赞库-设计方案点赞Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-11-25 14:36:56
 */
@Repository
@Transactional
public interface DesignPlanLikeMapper {
    int insertSelective(DesignPlanLike record);

    int updateByPrimaryKeySelective(DesignPlanLike record);

    int deleteByPrimaryKey(Integer id);

    int deleteById(DesignPlanLike designPlanLike);

    DesignPlanLike selectByPrimaryKey(Integer id);

    List<DesignPlanLike> selectList(DesignPlanLike designPlanLike);

    /**
     * 查询用户点赞的方案id集合
     * @param userId
     * @return
     */
    List<Integer> selectDesignIdByUserId(Integer userId);

    /**
     * 根据方案id和用户id查询点赞信息
     * @param
     * @return
     */
    DesignPlanLike selectByDesignIdAndUserId(@Param("userId") Integer userId, @Param("designId")Integer designId);

    /**
     * 保存或修改（缓存同步数据库用）
     * @param designPlanLike
     * @return
     */
    int saveOrUpdate(DesignPlanLike designPlanLike);

    /**
     * 获取点赞状态
     * @param userId
     * @param designId
     * @return
     */
    Integer getStatus(@Param("userId") Integer userId, @Param("designId")Integer designId);

    /**
     * 获取全屋方案的点赞状态
     * @param userId
     * @param fullHouseId
     * @return
     */
    Integer getFullHosueStatus(@Param("userId") Integer userId, @Param("fullHouseId") Integer fullHouseId);

    /**
     * 保存或修改全屋方案
     * @param designPlanLike
     * @return
     */
    int saveOrUpdateFullHouseDesignPlan(DesignPlanLike designPlanLike);
}
