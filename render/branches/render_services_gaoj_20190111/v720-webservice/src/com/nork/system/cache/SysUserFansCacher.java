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
import com.nork.system.model.SysUserFans;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.service.SysUserFansService;

/***
 * 用户粉丝缓存层
 * @author qiu.jun
 * @date 2016-05-16
 */
public class SysUserFansCacher {
     private static SysUserFansService sysUserFansService=SpringContextHolder.getBean(SysUserFansService.class);
     
 	private static PageParameter getPageParameter(SysUserFans fans) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(fans.getStart());
		parameter.setPageSize(fans.getLimit());

		if (fans.getUserId() != null && fans.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(fans.getUserId()));
			lstParameter.add(qp);
		}
		if (fans.getFansUserId() != null) {
			qp = new QueryParameter();
			qp.setName("fansUserId");
			qp.setValue(String.valueOf(fans.getFansUserId()));
			lstParameter.add(qp);
		}

		parameter.setLstParameter(lstParameter);
		return parameter;
	}
 	
	/***
	 * 获取用户粉丝总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getCount(SysUserFansSearch fans) {
		int total = 0;
		PageParameter parameter = getPageParameter(fans);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.SysUserFans, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = sysUserFansService.getCount(fans);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}

 	
	/***
	 * 根据条件获取所有用户粉丝列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<SysUserFans> getList(SysUserFans fans) {
		List<SysUserFans> lstFans = Lists.newArrayList();
		PageParameter parameter = getPageParameter(fans);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.SysUserFans, parameter);
		lstFans = CacheManager.getInstance().getCacher().getList(SysUserFans.class, key);
		if (CustomerListUtils.isEmpty(lstFans)) {
			lstFans = sysUserFansService.getList(fans);
			CacheManager.getInstance().getCacher().setObject(key, lstFans, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstFans;
	}
	
	/***
	 * 根据条件分页获取用户粉丝列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<SysUserFans> getPageList(SysUserFansSearch fans) {
		List<SysUserFans> lstFans = Lists.newArrayList();
		PageParameter parameter = getPageParameter(fans);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.SysUserFans, parameter);
		lstFans = CacheManager.getInstance().getCacher().getList(SysUserFans.class, key);
		if (CustomerListUtils.isEmpty(lstFans)) {
			lstFans = sysUserFansService.getPaginatedList(fans);
			CacheManager.getInstance().getCacher().setObject(key, lstFans, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstFans;
	}
	
	/***
	 * 移除单个用户粉丝缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.SysUserFans, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.SysUserFans,key);
	}
	
	/***
	 * 批量移除粉丝缓存
	 * 
	 * @param id
	 */
	public static void remove(SysUserFans fans) {
		PageParameter parameter = getPageParameter(fans);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.SysUserFans, parameter);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.SysUserFans,key);
	}
}
