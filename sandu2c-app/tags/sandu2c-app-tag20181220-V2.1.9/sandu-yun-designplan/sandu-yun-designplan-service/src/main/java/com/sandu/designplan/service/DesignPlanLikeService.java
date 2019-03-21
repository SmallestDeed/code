package com.sandu.designplan.service;


import com.sandu.common.exception.BizException;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.model.DesignPlanSummaryInfo;
import com.sandu.user.model.LoginUser;

import java.util.List;


/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 6:13 2018/1/8 0008
 * @Modified By:
 */
public interface DesignPlanLikeService {
    /**
     * 新增数据
     *
     * @param designPlanLike
     * @return int
     */
    public int add(DesignPlanLike designPlanLike);

    /**
     * 更新数据
     *
     * @param designPlanLike
     * @return int
     */
    public int update(DesignPlanLike designPlanLike);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    public int delete(Integer id);

    /**
     * 删除数据
     *
     * @param designPlanLike
     * @return int
     */
    public int deleteById(DesignPlanLike designPlanLike);

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignPlanLike
     */
    public DesignPlanLike get(Integer id);

    /**
     * 所有数据
     *
     * @param designPlanLike
     * @return List<DesignPlanLike>
     */
    public List<DesignPlanLike> getList(DesignPlanLike designPlanLike);


    /**
     * 方案点赞或取消点赞
     *
     * @param
     * @return
     */
    long setLikeOrDislike(LoginUser loginUser, DesignPlanLike designPlanLike) throws BizException;

    /**
     * 获取用户是否点赞收藏等
     *
     * @param
     * @return
     */
    String getPlanLikeAndFavoriteOfUser(LoginUser loginUser, Integer designId);

    /**
     * 保存或修改
     *
     * @param designPlanLike
     * @return
     */
    int saveOrUpdate(DesignPlanLike designPlanLike);

    /**
     * 获取用户对方案的点赞状态
     *
     * @param userId
     * @param designId
     * @return
     */
    int getStatus(Integer userId, Integer designId);

    /**
     * 从缓存中获取方案点赞收藏相关信息
     *
     * @param designPlanId userId
     */
    DesignPlanSummaryInfo getPlanInfoOfCache(Integer userId, Integer designPlanId);

    /**
     * 获取用户对全屋方案的点赞状态
     *
     * @param userId
     * @param fullHouseId
     * @return
     */
    Integer getFullHosueStatus(Integer userId, Integer fullHouseId);

    /**
     * 全屋方案点赞或取消点赞
     *
     * @param loginUser
     * @param designPlanLike
     * @return
     */
    long setFullHouseLikeOrDislike(LoginUser loginUser, DesignPlanLike designPlanLike) throws BizException;

    /**
     * 保存或修改全屋方案
     *
     * @param designPlanLike
     */
    int saveOrUpdateFullHouseDesignPlan(DesignPlanLike designPlanLike);

    /**
     * 从缓存中获取全屋方案点赞收藏相关信息
     *
     * @param userId                用户ID
     * @param fullHouseDesignPlanId 全屋方案ID
     * @return
     */
    DesignPlanSummaryInfo getFullHousePlanInfoOfCache(Integer userId, Integer fullHouseDesignPlanId);
}
