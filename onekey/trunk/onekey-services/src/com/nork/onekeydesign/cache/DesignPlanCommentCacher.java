package com.nork.onekeydesign.cache;

import java.util.List;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.onekeydesign.model.DesignPlanComment;
import com.nork.onekeydesign.model.search.DesignPlanCommentSearch;
import com.nork.onekeydesign.service.DesignPlanCommentService;

/***
 * 设计方案评论缓存层
 * @author qiu.jun
 * @date 2016-05-13
 */
public class DesignPlanCommentCacher {
   private static DesignPlanCommentService designPlanCommentService=SpringContextHolder.getBean(DesignPlanCommentService.class);
   
   private static PageParameter getPageParameter(DesignPlanCommentSearch search){
		PageParameter parameter=new PageParameter();
		List<QueryParameter> lstParameter=Lists.newArrayList();
		QueryParameter qp=null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());
		
		if(search.getDesignPlanId()!=null){
			qp=new QueryParameter();
			qp.setName("designPlanId");
			qp.setValue(String.valueOf(search.getDesignPlanId()));
			lstParameter.add(qp);
		}
		
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
    
	/***
	 * 获取带查询参数的设计方案评论总记录数
	 * @param spaceCommon
	 * @return
	 */
	public static int getTotalWithParameter(DesignPlanCommentSearch search){
		int total=0;
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getTotalWithParameter(ModuleType.DesignPlanComment, parameter);
		String temp=CacheManager.getInstance().getCacher().get(key);
		if(StringUtils.isBlank(temp)){
			total=designPlanCommentService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		}
		else{
			total=Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:"+key);
		}
		return total;
	}
	
	/***
	 * 根据参数获取评论列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlanComment> getUDPCList(DesignPlanCommentSearch search) {
		List<DesignPlanComment> lstComment = Lists.newArrayList();
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.DesignPlanComment, parameter);
		lstComment = CacheManager.getInstance().getCacher().getList(DesignPlanComment.class, key);
		if (CustomerListUtils.isEmpty(lstComment)) {
			lstComment = designPlanCommentService.getUDPCList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstComment, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstComment;
	}
 
	
	/***
	 * 移除单个设计方案评论
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.DesignPlanComment, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlanComment,key);
	}
	
}
