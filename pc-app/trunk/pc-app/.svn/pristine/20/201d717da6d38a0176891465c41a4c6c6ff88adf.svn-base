package com.nork.design.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.dao.SysBusinessFavoriteMapper;
import com.nork.design.model.DesignPlanRecommendFavoriteRef;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.Favorite;
import com.nork.design.model.FavoriteRecommendedModel;
import com.nork.design.service.DesignPlanRecommendFavoriteService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.system.cache.SysDictionaryCacher;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**
 * 
 *     
 * 项目名称：timeSpace    
 * 类名称：DesignPlanRecommendFavoriteServiceImpl    
 * 类描述：    
 * 创建人：Timy.Liu   
 * 创建时间：2017-7-7 上午11:09:06    
 * 修改人：Timy.Liu    
 * 修改时间：2017-7-7 上午11:09:06    
 * 修改备注：    
 * @version     
 *
 */
@Service
public class DesignPlanRecommendFavoriteServiceImpl implements DesignPlanRecommendFavoriteService {
	
	
    private static Logger logger = Logger.getLogger(DesignPlanRecommendFavoriteServiceImpl.class);
    @Autowired
    private SysBusinessFavoriteMapper sysBusinessFavoriteMapper;
    @Autowired
    private ResRenderPicMapper resRenderPicMapper;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
    @Autowired
    private AuthorizedConfigMapper authorizedConfigMapper;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#addFavorite(com.nork.design.model.Favorite)    
     */
    @Override
    public String addFavorite(Favorite favorite) {
        if(favorite==null)
            return "";
        
        if(StringUtils.isEmpty(favorite.getName()) || favorite.getUserId()<=0)
            return "";
        
        List list= sysBusinessFavoriteMapper.listFavorites(favorite);
        if(list!=null && list.size()>0)//同名存在，认为创建成功
            return ((Favorite)list.get(0)).getBid();
        
        if(favorite.getName().trim().length()>16)
            favorite.setName(favorite.getName().substring(0, 16));
        
        favorite.setBid(UUID.randomUUID().toString().replaceAll("-", ""));
        sysBusinessFavoriteMapper.addFavorite(favorite);
        return favorite.getBid();
    }
    
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#updateFavorite(com.nork.design.model.Favorite)    
     */
    @Override
    public boolean updateFavorite(Favorite favorite) {
        if(favorite==null)
            return false;
        
        if(StringUtils.isEmpty(favorite.getName()) || favorite.getUserId()<=0 || StringUtils.isEmpty(favorite.getBid()))
            return false;
        
        List list= sysBusinessFavoriteMapper.listFavorites(favorite);
        if(list!=null && list.size()>0)//同名存在，认为创建成功
        {
            logger.error("bid="+favorite.getBid()+"_name="+favorite.getName()+"_has_exist");
            return false;
        }
        
        if(favorite.getName().trim().length()>16)
            favorite.setName(favorite.getName().substring(0, 16));
        sysBusinessFavoriteMapper.updateFavorite(favorite);
        return true;
    }
    
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#listFavorites(com.nork.design.model.Favorite)    
     */
    @Override
    public List<Favorite> listFavorites(Favorite favorite) {
        if(favorite==null)
            return null;
        
        if(favorite.getUserId()<=0)
            return null;
        
        return sysBusinessFavoriteMapper.listFavorites(favorite);
    }
    
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#deleteFavorite(com.nork.design.model.Favorite)    
     */
    @Override
    public ResponseEnvelope deleteFavorite(Favorite favorite) {
    	ResponseEnvelope envelope = new ResponseEnvelope();
        if(favorite == null){
        	envelope.setSuccess(false);
        	envelope.setMessage("params error");
        	return envelope;
        }
        if(StringUtils.isEmpty(favorite.getBid())){
        	envelope.setSuccess(false);
        	envelope.setMessage("params error");
        	return envelope;
        }
        if(favorite.getUserId()<=0){
        	 envelope.setSuccess(false);
             envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
             return envelope;
        }
        Favorite favoriteSearch = this.getFavoritesByBid(favorite.getBid());
        if(favoriteSearch!=null){
        	if(favoriteSearch.getUserId() != favorite.getUserId()){
        		 envelope.setSuccess(false);
	   			 envelope.setMessage("无法删除他人收藏夹");
	      		 return envelope;
        	}
        	if("默认".equals(favoriteSearch.getName().trim())){
        		 envelope.setSuccess(false);
	   			 envelope.setMessage("默认收藏夹无法删除");
	      		 return envelope;
   		 	}
        }else{
        	 envelope.setSuccess(false);
        	 envelope.setMessage("已删除，请刷新列表");
     		 return envelope;
        }
        sysBusinessFavoriteMapper.deleteFavorite(favorite);
        sysBusinessFavoriteMapper.deleteFavoriteRefByFid(favorite);
        envelope.setSuccess(true);
        return envelope;
    }
    
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#moveInFavorite(com.nork.design.model.DesignPlanRecommendFavoriteRef)    
     */
    @Override
    public boolean moveInFavorite(DesignPlanRecommendFavoriteRef recommendFavoriteRef) {
        if (recommendFavoriteRef == null)
            return false;

        if (recommendFavoriteRef.getRecommendId() <= 0 || StringUtils.isEmpty(recommendFavoriteRef.getFid()))
            return false;

        DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2
                .get(recommendFavoriteRef.getRecommendId());
        if (null == designPlanRecommended) {
            logger.error(recommendFavoriteRef.getRecommendId() + "_do_not_exist");
            return false;
        }

        /*int count = sysBusinessFavoriteMapper.existInFavorite(recommendFavoriteRef);
        if (count > 0) {// 目的达到，认为成功
            logger.error(recommendFavoriteRef.getRecommendId() + " has_in_favorite_" + recommendFavoriteRef.getFid());
            return true;
        }*/
        
        recommendFavoriteRef.setBid(UUID.randomUUID().toString().replaceAll("-", ""));
		recommendFavoriteRef.setDesignPlanType(0);
        sysBusinessFavoriteMapper.moveInFavorite(recommendFavoriteRef);
        return true;
    }
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#moveOutFavorite(com.nork.design.model.DesignPlanRecommendFavoriteRef)    
     */
    @Override
    public boolean moveOutFavorite(DesignPlanRecommendFavoriteRef recommendFavoriteRef) {
        if (recommendFavoriteRef == null)
            return false;

        if (StringUtils.isEmpty(recommendFavoriteRef.getBid()))
            return false;
        
        sysBusinessFavoriteMapper.moveOutFavorite(recommendFavoriteRef);
        return true;
    }
    
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanFavoriteService#listMyDesignPlanRecommend(com.nork.design.model.DesignPlanRecommendFavoriteRef)    
     */
    @Override
    public List listMyDesignPlanRecommend(DesignPlanRecommendFavoriteRef designPlanRecommendFavoriteRef) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
    /**
     * 获取方案推荐收藏夹列表
     * @param model
     * @return
     */
	@Override
	public ResponseEnvelope<DesignPlanRecommendedResult> favoritePlanRecommendedList(FavoriteRecommendedModel model) {
		
		Integer total = 0;
		List<DesignPlanRecommendedResult> list = null;

		String msgId = model.getMsgId();
		String houseType = model.getHouseType();//空间类型
		String livingName = model.getLivingName();//小区名称
		String areaValue = model.getAreaValue();//面积
		String designRecommendedStyleId = model.getDesignRecommendedStyleId();//风格id
		Integer userId = model.getUserId();//用户id
		Integer userType = model.getUserType();//用户类型
		String limit = model.getLimit();
		String start = model.getStart();
		String favoriteBid = model.getFavoriteBid();  //方案推荐收藏夹id
		if(userId == null || userId.intValue() == -1 || userId.intValue() == 0){
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"请重新登录！",msgId);
		}
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"params error",msgId);
		}

		DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();
		String sysVersionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/* 1为外网 2 为内网 */
		if ("2".equals(sysVersionType) && userType!=null && userType.intValue() == 1) { /*内网内部用户的能看到测试发布中的*/
			designPlanRecommended.setIsInternalUser("yes");
		}
		if (StringUtils.isNotEmpty(favoriteBid)) { /*空间功能类型 */
			designPlanRecommended.setFavoriteBid(favoriteBid);
		}
		if (StringUtils.isNotEmpty(houseType)) { /*空间功能类型 */
			designPlanRecommended.setSpaceFunctionId(Integer.parseInt(houseType));
		}
		if(StringUtils.isNotEmpty(areaValue)){
			designPlanRecommended.setAreaValue(areaValue);
		}
		if (StringUtils.isNotEmpty(livingName)) { /* 小区名称 */
			designPlanRecommended.setLivingName(livingName);
		}
		if (StringUtils.isNotEmpty(designRecommendedStyleId)) { /* 推荐方案风格 */
			designPlanRecommended.setDesignRecommendedStyleId(Integer.parseInt(designRecommendedStyleId));
		}
		if(StringUtils.isNotEmpty(limit)){
			designPlanRecommended.setLimit(Integer.parseInt(limit));
		}
		if(StringUtils.isNotEmpty(start)){
			designPlanRecommended.setStart(Integer.parseInt(start));
		}
		designPlanRecommended.setUserId(userId);
		total = designPlanRecommendedServiceV2.getFavoritePlanRecommendedCount(designPlanRecommended);
		if(total>0){
			list = designPlanRecommendedServiceV2.getFavoritePlanRecommendedList(designPlanRecommended);
			if (list != null && list.size() > 0) {
				for (DesignPlanRecommendedResult result : list) {
					SysDictionary sysDictionary =  null;
					if(Utils.enableRedisCache()){
						sysDictionary = SysDictionaryCacher.getSysDictionaryByValue("houseType",result.getSpaceFunctionId());
					}else{
						sysDictionary = sysDictionaryService.getSysDictionaryByValue("houseType",result.getSpaceFunctionId());
					}
					if (sysDictionary != null) {
						result.setHouseTypeName(sysDictionary.getName());
					}
				}
			}
		}
		return new ResponseEnvelope<DesignPlanRecommendedResult>(total, list, msgId);
	}

	/**
	 * 通过bid 查询收藏夹
	 * @param businessId
	 * @return
	 */
	@Override
	public Favorite getFavoritesByBid(String businessId) {
		return sysBusinessFavoriteMapper.getFavoritesByBid(businessId);
	}

	@Override
	public int existInFavorite(DesignPlanRecommendFavoriteRef favoriteRef) {
		return sysBusinessFavoriteMapper.existInFavorite(favoriteRef);
	}

	/**
	 * 通过recommendId  删 推荐收藏关系
	 * @param recommendId
	 */
	@Override
	public void deleteFavoriteRefByRecommendId(Integer recommendId) {
		sysBusinessFavoriteMapper.deleteFavoriteRefByRecommendId(recommendId);
	}
	
	

	@Override
	public boolean existInFavoriteNew(DesignPlanRecommendFavoriteRef favoriteRef) {
		if(favoriteRef == null){
			return false;
		}
		if(favoriteRef.getRecommendId() < 1 || favoriteRef.getUserId() == null || favoriteRef.getUserId().intValue() < 1){
			return false;
		}
		int count = sysBusinessFavoriteMapper.existInFavoriteNew(favoriteRef);
		if(count > 0 ){
			return false;
		}
		return true;
	}

	/**
	 * 通过recommendId  判断是否被收藏
	 * @param ref
	 */
	@Override
	public String getHasCollected(DesignPlanRecommendFavoriteRef ref) {
		if(ref == null){
			return "-1";//没有设计方案id和用戶id
		}
		String fid = sysBusinessFavoriteMapper.getHasCollected(ref);
		if(fid == null){
			return "0";//0表示没有被收藏
		}
		return fid;
	}
   
}
