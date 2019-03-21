package com.nork.product.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.AuthorizedConfigService;

/***
 * 授权配置缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-19
 * 
 */
public class AuthorizedConfigCacher {
	private static AuthorizedConfigService authorizedConfigService = SpringContextHolder.getBean(AuthorizedConfigService.class);

	/***
	 * 根据条件获取总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getCount(AuthorizedConfigSearch search) {
		int total = 0;
		Map<String, String> map = new HashMap<String, String>();
		map.put("count","all");
        if(search.getUserId()!=null && search.getUserId()!=-1){
        	map.put("userId", String.valueOf(search.getUserId()));
        }
        if(search.getState()!=null){
        	map.put("state", String.valueOf(search.getState()));
        }
		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.AuthorizedConfig, map);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = authorizedConfigService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}
	
	/***
	 * 根据查询条件分页获取数据
	 * @param search
	 * @return
	 */
	public static List<AuthorizedConfig> getPageList(AuthorizedConfigSearch search){
		List<AuthorizedConfig> lstConfig=Lists.newArrayList();
		Map<String, String> map = new HashMap<String, String>();
		map.put("list","page");
		map.put("limit", String.valueOf(search.getLimit()));
		map.put("start", String.valueOf(search.getStart()));
		if(StringUtils.isNoneBlank(search.getAuthorizedCode())){
		    map.put("authorizedCode", search.getAuthorizedCode());
		}
        if(search.getUserId()!=null && search.getUserId()!=-1){
        	map.put("userId", String.valueOf(search.getUserId()));
        }
        if(search.getState()!=null){
        	map.put("state", String.valueOf(search.getState()));
        }
        if(StringUtils.isNotBlank(search.getTerminalImei())){
        	map.put("terminalImei", search.getTerminalImei());
        }
		String key=KeyGenerator.getAllListKeyWithMap(ModuleType.AuthorizedConfig, map);
		lstConfig=CacheManager.getInstance().getCacher().getList(AuthorizedConfig.class, key);
		if(CustomerListUtils.isEmpty(lstConfig)){
			lstConfig=authorizedConfigService.getPaginatedList(search);
			if(CustomerListUtils.isNotEmpty(lstConfig)){
				CacheManager.getInstance().getCacher().setObject(key, lstConfig, 0);
			}
		}
		return lstConfig;
	}
	
	/***
	 * 根据查询条件分页获取数据
	 * @param search
	 * @return
	 */
	public static List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig){
		List<AuthorizedConfig> lstConfig=Lists.newArrayList();
		Map<String, String> map = new HashMap<String, String>();
		map.put("list","page");
		if(StringUtils.isNoneBlank(authorizedConfig.getAuthorizedCode())){
		    map.put("authorizedCode", authorizedConfig.getAuthorizedCode());
		}
        if(authorizedConfig.getUserId()!=null && authorizedConfig.getUserId()>0){
        	map.put("userId", String.valueOf(authorizedConfig.getUserId()));
        }
        if(authorizedConfig.getState()!=null){
        	map.put("state", String.valueOf(authorizedConfig.getState()));
        }
        if(StringUtils.isNotBlank(authorizedConfig.getTerminalImei())){
        	map.put("terminalImei", authorizedConfig.getTerminalImei());
        }
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.AuthorizedConfig, map);
		lstConfig = CacheManager.getInstance().getCacher().getList(AuthorizedConfig.class, key);
		if(CustomerListUtils.isEmpty(lstConfig)){
			lstConfig = authorizedConfigService.getList(authorizedConfig);
			if( CustomerListUtils.isNotEmpty(lstConfig) ){
				CacheManager.getInstance().getCacher().setObject(key, lstConfig, 0);
			}
		}
		return lstConfig;
	}
	
	/***
	 * 移除单个授权配置
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.AuthorizedConfig, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.AuthorizedConfig,key);
	}
}
