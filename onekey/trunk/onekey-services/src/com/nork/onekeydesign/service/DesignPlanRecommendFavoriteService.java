package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.model.DesignPlanRecommendFavoriteRef;
import com.nork.onekeydesign.model.DesignPlanRecommendedResult;
import com.nork.onekeydesign.model.Favorite;
import com.nork.onekeydesign.model.FavoriteRecommendedModel;

/**
 * 
 * 
 * 项目名称：timeSpace 类名称：DesignPlanFavoriteService 类描述： 创建人：Timy.Liu 创建时间：2017-7-7
 * 上午11:08:20 修改人：Timy.Liu 修改时间：2017-7-7 上午11:08:20 修改备注：
 * 
 * @version
 * 
 */
public interface DesignPlanRecommendFavoriteService {
    /**
     * 
     * 
     * addFavorite 方法描述： 添加收藏夹
     * 
     * @param favorite
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public String addFavorite(Favorite favorite);

    /**
     * 
     * 
     * updateFavorite 方法描述： 修改收藏夹
     * 
     * @param favorite
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public boolean updateFavorite(Favorite favorite);

    /**
     * 
     * 
     * listFavorites 方法描述： 列出个人所有的收藏夹
     * 
     * @param favorite
     * @return
     * 
     * @return List<Favorite> 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public List<Favorite> listFavorites(Favorite favorite);

    /**
     * 
     * 
     * deleteFavorite 方法描述： 删除收藏夹
     * 
     * @param favorite
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public ResponseEnvelope deleteFavorite(Favorite favorite);

    /**
     * 
     * 
     * moveInFavorite 方法描述： 收藏推荐方案
     * 
     * @param designPlanRecommendFavoriteRef
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public boolean moveInFavorite(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * 
     * 
     * moveOutFavorite 方法描述： 取消收藏推荐方案
     * 
     * @param designPlanRecommendFavoriteRef
     * @return
     * 
     * @return boolean 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public boolean moveOutFavorite(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);

    /**
     * 
     * 
     * listMyDesignPlanRecommend 方法描述： 分页获取所有的我收藏过的推荐方案
     * 
     * @param designPlanRecommendFavoriteRef
     * @return
     * 
     * @return List 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public List listMyDesignPlanRecommend(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);
    
    /**
     * 获取方案推荐收藏夹列表
     * @param model
     * @return
     */
	public ResponseEnvelope<DesignPlanRecommendedResult> favoritePlanRecommendedList(FavoriteRecommendedModel model);

	/**
	 * 通过bid 查询收藏夹
	 * @param businessId
	 * @return
	 */
	public Favorite getFavoritesByBid(String businessId);

	public int existInFavorite(DesignPlanRecommendFavoriteRef favoriteRef);

	/**
	 * 通过recommendId  删 推荐收藏关系
	 * @param id
	 */
	public void deleteFavoriteRefByRecommendId(Integer recommendId);

	/**
	 * 判断是否已经被此次 和 其他收藏夹收藏
	 * @param favoriteRef
	 * @return
	 */
	public boolean existInFavoriteNew(DesignPlanRecommendFavoriteRef favoriteRef);

	/**
	 * 根据方案id判断方案是否已经被收藏
	 * @param ref
	 * @return "0":未被收藏，"-1":方案id和用戶id为null，其他:已被收藏
	 */
	public String getHasCollected(DesignPlanRecommendFavoriteRef ref);
}
