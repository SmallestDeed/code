package com.nork.system.cache;

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
import com.nork.system.model.BaseMessage;
import com.nork.system.model.search.BaseMessageSearch;
import com.nork.system.service.BaseMessageService;

/***
 * 消息缓存层
 * @author qiu.jun
 * @date 2016-05-13
 */
public class BaseMessageCacher {
    private static BaseMessageService baseMessageService = SpringContextHolder.getBean(BaseMessageService.class);
    
 	private static PageParameter getPageParameter(BaseMessageSearch search) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());

		if (search!=null && search.getUserId()!=null && search.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(search.getUserId()));
			lstParameter.add(qp);
		}

		parameter.setLstParameter(lstParameter);
		return parameter;
	}
 	
    /***
     * 获取消息总记录数
     * @param search
     * @return
     */
	public static int getCount(BaseMessageSearch search) {
		int total = 0;
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.BaseMessage, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = baseMessageService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}
 	
	/***
	 * 获取消息列表
	 * @param search
	 * @return
	 */
	public static List<BaseMessage> getList(BaseMessageSearch search) {
		List<BaseMessage> lstMessage = Lists.newArrayList();
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.BaseMessage, parameter);
		lstMessage = CacheManager.getInstance().getCacher().getList(BaseMessage.class, key);
		if (CustomerListUtils.isEmpty(lstMessage)) {
			lstMessage = baseMessageService.getList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstMessage, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstMessage;
	}
	
	/***
	 * 移除单个消息的缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.BaseMessage, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.BaseMessage,key);
	}
	
	/***
	 * 获取消息列表
	 * @param search
	 * @return
	 */
	public static List<BaseMessage> getPaginatedList(BaseMessageSearch search) {
		List<BaseMessage> lstMessage = Lists.newArrayList();
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.BaseMessage, parameter);
		lstMessage = CacheManager.getInstance().getCacher().getList(BaseMessage.class, key);
		if (CustomerListUtils.isEmpty(lstMessage)) {
			lstMessage = baseMessageService.getPaginatedList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstMessage, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstMessage;
	}
	
}
