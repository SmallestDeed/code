package com.sandu.designplan.service.impl;

import com.sandu.company.model.vo.ShopPlanInfo;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.interaction.service.CollectionService;
import com.sandu.interaction.service.LikeService;
import com.sandu.matadata.Page;
import com.sandu.matadata.enums.InteractionObjectType;
import com.sandu.designplan.dao.DesignPlanRecommendedDao;
import com.sandu.designplan.model.query.DesignPlanQuery;
import com.sandu.designplan.model.vo.DesignPlanVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by kono on 2018/5/24 0024.
 */
@Service("designPlanRecommendedService")
@Transactional(readOnly = true)
public class DesignPlanRecommendedServiceImpl implements DesignPlanRecommendedService {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private DesignPlanRecommendedDao designPlanRecommendedDao;
   
    @Override
    public int findRecommendedPlanCount(DesignPlanQuery query) {
        return designPlanRecommendedDao.findRecommendedPlanCount(query);
    }

    @Override
    public List<DesignPlanVo> findRecommendedPlanList(DesignPlanQuery query) {
    	List<DesignPlanVo> lstVo=designPlanRecommendedDao.findRecommendedPlanList(query);
    	/*if(lstVo!=null && lstVo.size()>0) {
    		for(DesignPlanVo vo : lstVo) {
    			int collectionCount=collectionService.getCount(InteractionObjectType.Plan.value(), vo.getPlanId());
    			vo.setCollectionCount(collectionCount);
    			int likeCount=likeService.getCount(InteractionObjectType.Plan.value(), vo.getPlanId());
    			vo.setLikeCount(likeCount);
    		}
    	}*/
    	return lstVo;
    }
    
    public Page<DesignPlanVo> list(DesignPlanQuery query){
    	Page<DesignPlanVo> page=new Page<DesignPlanVo>();
		long count=designPlanRecommendedDao.findFontCount(query);
		page.setCount(count);
		page.setPageSize(query.getPageSize());
		List<DesignPlanVo> lstVo=designPlanRecommendedDao.findFontPageList(query);
		if(lstVo!=null && lstVo.size()>0) {
			int type=InteractionObjectType.Plan.value();
			long userId=query.getUserId().longValue();
			for(DesignPlanVo vo : lstVo) {
    			int collectionCount=collectionService.getCount(type, vo.getPlanId());
    			vo.setCollectionCount(collectionCount);
    			int likeCount=likeService.getCount(type, vo.getPlanId());
    			vo.setLikeCount(likeCount);
    			int currentUserCollectionCount=collectionService.getCount(type,userId,vo.getPlanId());
    			vo.setCurrentUserCollectionCount(currentUserCollectionCount);
    			int currentUserLikeCount=likeService.getCount(type,userId, vo.getPlanId());
    			vo.setCurrentUserLikeCount(currentUserLikeCount);
    		}
			page.setList(lstVo);
		}
		return page;
    }


    @Override
	public Integer getPlatformId(String platformCode) {
    	return designPlanRecommendedDao.getPlatformId(platformCode);
	}

	@Override
	public List<ShopPlanInfo> getShopPlanInfo(Map<String,DesignPlanQuery> queryMap) {
    	return designPlanRecommendedDao.findShopPlanInfo(queryMap);
	}
}
