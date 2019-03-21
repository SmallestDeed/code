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
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageRecieveSearch;
import com.nork.system.service.BaseMessageRecieveService;

/***
 * 消息接收缓存层
 * @author qiu.jun
 * @date 2016-05-18
 */
public class BaseMessageRecieveCacher {
   private static BaseMessageRecieveService baseMessageRecieveService = SpringContextHolder.getBean(BaseMessageRecieveService.class);
   
	private static PageParameter getParameter(UserMessageDesignPlan message) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		
		if (message.getUserId()!=null && message.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(message.getUserId()));
			lstParameter.add(qp);
		}
		if (message.getMessageType()!=null) {
			qp = new QueryParameter();
			qp.setName("messageType");
			qp.setValue(String.valueOf(message.getMessageType()));
			lstParameter.add(qp);
		}
		if (message.getBusinessTypeId()!=null) {
			qp = new QueryParameter();
			qp.setName("businessTypeId");
			qp.setValue(String.valueOf(message.getBusinessTypeId()));
			lstParameter.add(qp);
		}
		if (message.getMessageId()!=null) {
			qp = new QueryParameter();
			qp.setName("messageId");
			qp.setValue(String.valueOf(message.getMessageId()));
			lstParameter.add(qp);
		}
		
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
	
	private static PageParameter getPageParameter(BaseMessageRecieveSearch search) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());
		
		if (search.getUserId()!=null && search.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(search.getUserId()));
			lstParameter.add(qp);
		}
		if (search.getMessageType()!=null) {
			qp = new QueryParameter();
			qp.setName("messageType");
			qp.setValue(String.valueOf(search.getMessageType()));
			lstParameter.add(qp);
		}
		if (search.getBusinessTypeId()!=null) {
			qp = new QueryParameter();
			qp.setName("businessTypeId");
			qp.setValue(String.valueOf(search.getBusinessTypeId()));
			lstParameter.add(qp);
		}
		if (search.getMessageId()!=null) {
			qp = new QueryParameter();
			qp.setName("messageId");
			qp.setValue(String.valueOf(search.getMessageId()));
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
	public static int getCount(BaseMessageRecieveSearch search) {
		int total = 0;
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.BaseMessage, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = baseMessageRecieveService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}
	
	/***
	 * 获取消息接收列表
	 * @param search
	 * @return
	 */
	public static List<UserMessageDesignPlan> getList(UserMessageDesignPlan message) {
		List<UserMessageDesignPlan> lstMessage = Lists.newArrayList();
		PageParameter parameter = getParameter(message);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.BaseMessageRecieve, parameter);
		lstMessage = CacheManager.getInstance().getCacher().getList(UserMessageDesignPlan.class, key);
		if (CustomerListUtils.isEmpty(lstMessage)) {
			lstMessage = baseMessageRecieveService.getMessageList(message);
			CacheManager.getInstance().getCacher().setObject(key, lstMessage, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstMessage;
	}
	
	/***
	 * 获取消息接收列表
	 * @param search
	 * @return
	 */
	public static List<BaseMessageRecieve> getPageList(BaseMessageRecieveSearch search) {
		List<BaseMessageRecieve> lstMessage = Lists.newArrayList();
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.BaseMessageRecieve, parameter);
		lstMessage = CacheManager.getInstance().getCacher().getList(BaseMessageRecieve.class, key);
		if (CustomerListUtils.isEmpty(lstMessage)) {
			lstMessage = baseMessageRecieveService.getPaginatedList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstMessage, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstMessage;
	}
}
