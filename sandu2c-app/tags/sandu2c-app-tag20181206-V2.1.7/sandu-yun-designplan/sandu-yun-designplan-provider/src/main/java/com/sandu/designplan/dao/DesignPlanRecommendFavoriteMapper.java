package com.sandu.designplan.dao;

import com.sandu.designplan.model.DesignPlanRecommendFavoriteRef;
import com.sandu.designplan.model.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目名称：timeSpace 类名称：DesignPlanFavoriteMapper 类描述： 设计方案收藏 创建人：Timy.Liu
 * 创建时间：2017-7-7 上午10:55:20 修改人：Timy.Liu 修改时间：2017-7-7 上午10:55:20 修改备注：
 */
@Repository
public interface DesignPlanRecommendFavoriteMapper {
    /**
     * addFavorite 方法描述： 添加收藏夹
     *
     * @param favorite
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    boolean addFavorite(Favorite favorite);

    /**
     * updateFavorite 方法描述： 修改收藏夹
     *
     * @param favorite
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    boolean updateFavorite(Favorite favorite);

    /**
     * listFavorites 方法描述： 列出个人所有的收藏夹
     *
     * @param favorite
     * @return List<Favorite> 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    List<Favorite> listFavorites(Favorite favorite);

    /**
     * deleteFavorite 方法描述： 删除收藏夹
     *
     * @param favorite
     * @return boolean 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    boolean deleteFavorite(Favorite favorite);

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
     * listMyDesignPlanRecommend 方法描述： 分页获取所有的我收藏过的推荐方案
     *
     * @param designPlanRecommendFavoriteRef
     * @return List 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    List listMyDesignPlanRecommend(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * countMyDesignPlanRecommend 方法描述： 统计所有的我收藏过的推荐方案
     *
     * @param designPlanRecommendFavoriteRef
     * @return int 返回类型
     * @Exception 异常对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    int countMyDesignPlanRecommend(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * existInFavorite 方法描述：  判断一个推荐方案是否在指定的分组
     *
     * @param designPlanRecommendFavoriteRef
     * @return int    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    int existInFavorite(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * deleteFavoriteRefByFid 方法描述：      根据收藏夹的业务id，删除与之关联的记录
     *
     * @param favorite
     * @return void    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    void deleteFavoriteRefByFid(Favorite favorite);

    /**
     * 通过bid 查询收藏夹
     *
     * @param businessId
     * @return
     */
    Favorite getFavoritesByBid(String businessId);

    /**
     * 通过recommendId  删 推荐收藏关系
     *
     * @param recommendId
     */
    void deleteFavoriteRefByRecommendId(Integer recommendId);

    /**
     * 判断是否已经被此次 和 其他收藏夹收藏
     *
     * @param favoriteRef
     * @return
     */
    int existInFavoriteNew(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 添加或修改操作（缓存同步数据库用）
     * @param favoriteRef
     * @return
     */
    int saveOrUpdate(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 获取方案点赞状态
     * @param favoriteRef
     * @return
     */
    Integer getPlanFavoriteStatusOfUser(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 获取全屋方案收藏状态
     * @param favoriteRef
     * @return
     */
    Integer getFullHousePlanFavoriteStatusOfUser(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 添加或修改全屋方案
     * @param favoriteRef
     * @return
     */
    Integer saveOrUpdateFullHouseDesignPlan(DesignPlanRecommendFavoriteRef favoriteRef);
}
