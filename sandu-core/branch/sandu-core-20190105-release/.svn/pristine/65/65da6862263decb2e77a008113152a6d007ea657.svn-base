package com.sandu.api.designplan.service;



import com.sandu.api.designplan.model.DesignPlanLike;
import com.sandu.api.designplan.model.DesignPlanSummaryInfo;

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
    int add(DesignPlanLike designPlanLike);

    /**
     * 更新数据
     *
     * @param designPlanLike
     * @return int
     */
    int update(DesignPlanLike designPlanLike);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 删除数据
     *
     * @param designPlanLike
     * @return int
     */
    int deleteById(DesignPlanLike designPlanLike);

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignPlanLike
     */
    DesignPlanLike get(Integer id);

    /**
     * 所有数据
     *
     * @param designPlanLike
     * @return List<DesignPlanLike>
     */
    List<DesignPlanLike> getList(DesignPlanLike designPlanLike);


    /**
     * 方案点赞或取消点赞
     *
     * @param
     * @return
     */
//    long setLikeOrDislike(LoginUser loginUser, DesignPlanLike designPlanLike);

    /**
     * 获取用户是否点赞收藏等
     *
     * @param
     * @return
     */
//    String getPlanLikeAndFavoriteOfUser(LoginUser loginUser, Integer designId);

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

}
