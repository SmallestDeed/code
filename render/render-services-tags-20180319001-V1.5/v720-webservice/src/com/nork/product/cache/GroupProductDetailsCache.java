package com.nork.product.cache;

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
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.product.service.GroupProductDetailsService;

/***
 * 组合产品明细缓存层
 * 
 * @author xiao.xc
 * @date 2016-06-25
 * 
 */
public class GroupProductDetailsCache {
	private static GroupProductDetailsService groupProductDetailsService = SpringContextHolder.getBean(GroupProductDetailsService.class);

	private static PageParameter getPageParameter(GroupProductDetails groupProductDetails) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(groupProductDetails.getStart());
		parameter.setPageSize(groupProductDetails.getLimit());

		if (groupProductDetails.getGroupId() != null && groupProductDetails.getGroupId() != -1) {
			qp = new QueryParameter();
			qp.setName("groupId");
			qp.setValue(String.valueOf(groupProductDetails.getGroupId()));
			lstParameter.add(qp);
		}
		if (groupProductDetails.getIsMain() != null && groupProductDetails.getIsMain() != -1) {
			qp = new QueryParameter();
			qp.setName("isMain");
			qp.setValue(String.valueOf(groupProductDetails.getIsMain()));
			lstParameter.add(qp);
		}
		if (groupProductDetails.getProductId() != null && groupProductDetails.getProductId() != -1){
			qp = new QueryParameter();
			qp.setName("productId");
			qp.setValue(""+groupProductDetails.getProductId());
			lstParameter.add(qp);
		}
		
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
	/***
	 * 根据条件组合Id获取总记录数
	 * 
	 * @param groupId
	 * @return
	 */
	public static int getCount(GroupProductDetailsSearch search) {
		int total = 0;
		Map<String, String> map = new HashMap<String, String>();
		map.put("count","all");
        if(search.getGroupId()!=null && search.getGroupId()!=-1){
        	map.put("userId", String.valueOf(search.getGroupId()));
        }
		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.GroupProductDetails, map);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = groupProductDetailsService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}
	
	/***
     * 获取所有数据
     * @param map
     * @return groupId
     */
    public static List<GroupProductDetails> getList(GroupProductDetails groupProduct){
    	PageParameter parameter= getPageParameter(groupProduct);
    	List<GroupProductDetails> list = new ArrayList<GroupProductDetails>(); 
    	String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.GroupProductDetails,parameter);
    	if( CacheManager.getInstance().getCacher() != null ){
    		list = CacheManager.getInstance().getCacher().getList(GroupProductDetails.class, key);
    		if(CustomerListUtils.isEmpty(list)){
    			list = groupProductDetailsService.getList(groupProduct);
        		if( !CustomerListUtils.isEmpty(list) ){
    				CacheManager.getInstance().getCacher().setObject(key, list, 0);
    			}
    		}
    	}
    	//////System.out.println("********WebGroupProductDetailsController-getGroupProductDetails-getList-key"+key+"********");
    	return list;
    }

}
