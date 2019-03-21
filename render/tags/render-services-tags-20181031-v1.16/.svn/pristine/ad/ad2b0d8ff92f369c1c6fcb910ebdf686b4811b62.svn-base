package com.nork.design.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nork.design.model.DesignPlanRecommendFavoriteRef;
import com.nork.design.model.Favorite;

/**
 * 
 * 
 * 项目名称：timeSpace 类名称：DesignPlanFavoriteMapper 类描述： 设计方案收藏 创建人：Timy.Liu
 * 创建时间：2017-7-7 上午10:55:20 修改人：Timy.Liu 修改时间：2017-7-7 上午10:55:20 修改备注：
 * 
 * @version
 *
 */
@Repository
public interface SysBusinessFavoriteMapper {
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
    public boolean addFavorite(Favorite favorite);

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
    public boolean deleteFavorite(Favorite favorite);

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
     * 
     * 
     * countMyDesignPlanRecommend 方法描述： 统计所有的我收藏过的推荐方案
     * 
     * @param designPlanRecommendFavoriteRef
     * @return
     * 
     * @return int 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public int countMyDesignPlanRecommend(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);
    /**
     * 
       
     * existInFavorite 方法描述：  判断一个推荐方案是否在指定的分组    
       
     * @param designPlanRecommendFavoriteRef
     * @return
    
     * @return int    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public int existInFavorite(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef);
    /**
     * 
       
     * deleteFavoriteRefByFid 方法描述：      根据收藏夹的业务id，删除与之关联的记录
       
     * @param favorite
    
     * @return void    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void deleteFavoriteRefByFid(Favorite favorite);
    /**
	 * 通过bid 查询收藏夹
	 * @param businessId
	 * @return
	 */
	public Favorite getFavoritesByBid(String businessId);
	/**
	 * 通过recommendId  删 推荐收藏关系
	 * @param recommendId
	 */
	public void deleteFavoriteRefByRecommendId(Integer recommendId);
	/**
	 * 判断是否已经被此次 和 其他收藏夹收藏
	 * @param favoriteRef
	 * @return
	 */
	public int existInFavoriteNew(DesignPlanRecommendFavoriteRef favoriteRef);

    /**
     * 根据recommendId和userId 判断方案是否被收藏
     * @param ref
     * @return bid
     */
    public String getHasCollected(DesignPlanRecommendFavoriteRef ref);
}
