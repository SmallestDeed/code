package com.sandu.designplan.service;

import com.sandu.common.exception.BizException;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.model.DesignPlanRecommendFavoriteRef;
import com.sandu.designplan.model.Favorite;
import com.sandu.designplan.model.FavoriteRecommendedModel;
import com.sandu.user.model.LoginUser;


/**
 * 项目名称：timeSpace 类名称：DesignPlanFavoriteService 类描述： 创建人：Timy.Liu 创建时间：2017-7-7
 * 上午11:08:20 修改人：Timy.Liu 修改时间：2017-7-7 上午11:08:20 修改备注：
 */
public interface DesignPlanRecommendFavoriteService {
    /**
     * addFavorite 方法描述： 添加收藏夹
     *
     * @param favorite
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    String addFavorite(Favorite favorite);

    /**
     * moveInFavorite 方法描述： 收藏推荐方案
     *
     * @param designPlanRecommendFavoriteRef
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    boolean moveInFavorite(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * moveOutFavorite 方法描述： 取消收藏推荐方案
     *
     * @param designPlanRecommendFavoriteRef
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    boolean moveOutFavorite(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * 获取方案推荐收藏夹列表
     *
     * @param model
     * @return
     */
    ResponseEnvelope favoritePlanRecommendedList(FavoriteRecommendedModel model);

    /**
     * 通过bid 查询收藏夹
     *
     * @param businessId
     * @return
     */
    Favorite getFavoritesByBid(String businessId);


    /**
     * 判断是否已经被此次 和 其他收藏夹收藏
     *
     * @param favoriteRef
     * @return
     */
    boolean existInFavoriteNew(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 收藏或取消收藏
     * @param loginUser
     * @param favoriteRef
     * @return
     */
    long setFavoriteOrUnfavorite(LoginUser loginUser, DesignPlanRecommendFavoriteRef favoriteRef) throws BizException;

    /**
     * 添加或修改（缓存同步数据库用）
     * @param favoriteRef
     * @return
     */
    int saveOrUpdate(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 获取用户收藏状态
     * @param favoriteRef
     * @return
     */
    int getPlanFavoriteStatusOfUser(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 全屋方案-收藏或取消收藏
     * @param loginUser
     * @param favoriteRef
     * @return
     */
    long setFullHosueFavoriteOrUnfavorite(LoginUser loginUser, DesignPlanRecommendFavoriteRef favoriteRef) throws BizException;

    /**
     * 获取全屋方案的用户收藏状态
     * @param favoriteRef
     * @return
     */
    Integer getFullHousePlanFavoriteStatusOfUser(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 添加或修改全屋方案
     * @param favoriteRef
     */
    Integer saveOrUpdateFullHouseDesignPlan(DesignPlanRecommendFavoriteRef favoriteRef);

    void syncDeginplanFavorite();
}
