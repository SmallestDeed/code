package com.nork.design.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanLike;
import com.nork.design.model.search.DesignPlanLikeSearch;
import com.nork.design.service.DesignPlanLikeService;
import com.nork.design.service.DesignPlanService;

/***
 * 设计方案点赞缓存层
 * @author qiu.jun
 * @date 2016-05-13
 */
public class DesignPlanLikeCacher {
	
     private static DesignPlanLikeService designPlanLikeService=SpringContextHolder.getBean(DesignPlanLikeService.class);
     private static DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
     
     
     
     private static PageParameter getPageParameter(DesignPlanLikeSearch search){
 		PageParameter parameter=new PageParameter();
 		List<QueryParameter> lstParameter=Lists.newArrayList();
 		QueryParameter qp=null;
 		parameter.setPageIndex(search.getStart());
 		parameter.setPageSize(search.getLimit());
 		
 		qp=new QueryParameter();
 		qp.setName("designId");
 		qp.setValue(String.valueOf(search.getDesignId()));
 		lstParameter.add(qp);
 		
 		parameter.setLstParameter(lstParameter);
 		return parameter;
 	}
     
 	/***
 	 * 获取带查询参数的设计方案点赞总记录数
 	 * @param spaceCommon
 	 * @return
 	 */
 	public static int getTotalWithParameter(DesignPlanLikeSearch search){
 		int total=0;
 		PageParameter parameter= getPageParameter(search);
 		String key=KeyGenerator.getTotalWithParameter(ModuleType.DesignTemplet, parameter);
 		String temp=CacheManager.getInstance().getCacher().get(key);
 		if(StringUtils.isBlank(temp)){
 			total=designPlanLikeService.getCount(search);
 			if(total>0){
 				CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 			}
 		}else{
 			total=Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:"+key);
 		}
 		return total;
 	}
 	
 	/**
 	 * 
 	 * @param designId
 	 * @return DesignPlan
 	 */
 	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DesignPlan  getDesignPlan(Integer designId){
 		DesignPlan dpc = new DesignPlan();
 		Map map=new HashMap();
 		map.put("designId", designId);                   
 		String key=KeyGenerator.getKeyWithMap(ModuleType.DesignPlanLike, map);
 		dpc=(DesignPlan) CacheManager.getInstance().getCacher().getObject(key);
 		if(dpc==null){
 			dpc = designPlanService.get(designId);
 			if(dpc!=null){
 				CacheManager.getInstance().getCacher().setObject(key, dpc, 0);
 			}
 		}
 		//////System.out.println("********WebDesignPlanLikeController-getlike and cancelLike-getDesignPlan-key"+key+"********");
		return dpc;
 	}
 	
 	/**
 	 * 
 	 * @param designId
 	 * @return DesignPlan
 	 */
 	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DesignPlanLike>  getPaginatedList(DesignPlanLikeSearch designPlanLikeSearch){
 		List<DesignPlanLike> designPlanLikelist=new ArrayList<DesignPlanLike>();
 		Integer designId=designPlanLikeSearch.getDesignId();
 		Map map=new HashMap();
 		map.put("designId", designId);
 		String key=KeyGenerator.getKeyWithMap(ModuleType.DesignPlanLike, map);
 		designPlanLikelist=CacheManager.getInstance().getCacher().getList(DesignPlanLike.class, key);
 		if(CustomerListUtils.isEmpty(designPlanLikelist)){
 			designPlanLikelist= designPlanLikeService.getPaginatedList(designPlanLikeSearch);
 			if(!CustomerListUtils.isEmpty(designPlanLikelist)){
 				CacheManager.getInstance().getCacher().setObject(key, designPlanLikelist, 0);
 			}
 		}
 		//////System.out.println("********WebDesignPlanLikeController-getlike-getPaginatedList-key"+key+"********");
		return designPlanLikelist;
 	}
 	
 	/**
 	 *设计方案 
 	 * @param designId
 	 * @return DesignPlan
 	 */
 	@SuppressWarnings({ "rawtypes", "unchecked" })
 	public static void removeDesignPlan(Integer id){
 		Map map=new HashMap();
 		map.put("id", id);
    	String key=KeyGenerator.getKeyWithMap(ModuleType.DesignPlanLike, map);
    	CacheManager.getInstance().getCacher().remove(ModuleType.DesignPlanLike,key);
 	}
}


   