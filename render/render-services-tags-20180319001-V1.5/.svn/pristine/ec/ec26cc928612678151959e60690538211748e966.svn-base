package com.nork.system.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.model.LoginUser;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.system.model.BaseArea;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysUserSearch;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.SysUserService;

/***
 * 系统用户缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-16
 */
public class SysUserCacher {

	private static SysUserService sysUserService = SpringContextHolder.getBean(SysUserService.class);
	private static BaseAreaService baseAreaService = SpringContextHolder.getBean(BaseAreaService.class);
	private static Logger logger = LoggerFactory.getLogger(SysUserCacher.class);
			
	private static PageParameter getPageParameter(SysUserSearch search) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());

		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 获取用户总记录数
	 * 
	 * @param search
	 * @return
	 */
	public static int getCount(SysUserSearch search) {
		int total = 0;
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.SysUser, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = sysUserService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}

	/***
	 * 根据条件获取所有用户列表
	 * 
	 * @param sysUser
	 * @return
	 */
	public static List<SysUser> getAllList(SysUser sysUser) {
		List<SysUser> lstUser = Lists.newArrayList();
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", sysUser.getMobile());
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.SysUser, map);
		lstUser = CacheManager.getInstance().getCacher().getList(SysUser.class, key);
		if (CustomerListUtils.isEmpty(lstUser)) {
			lstUser = sysUserService.getList(sysUser);
			CacheManager.getInstance().getCacher().setObject(key, lstUser, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstUser;
	}

	/***
	 * 分页获取用户列表
	 * 
	 * @param search
	 * @return
	 */
	public static List<SysUser> getPageList(SysUserSearch search) {
		List<SysUser> lstUser = Lists.newArrayList();
		PageParameter parameter = getPageParameter(search);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.SysUser, parameter);
		lstUser = CacheManager.getInstance().getCacher().getList(SysUser.class, key);
		if (CustomerListUtils.isEmpty(lstUser)) {
			lstUser = sysUserService.getPaginatedList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstUser, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstUser;
	}

	/***
	 * 获取所有系统用户
	 * 
	 * @return
	 */
	public static List<SysUser> getSysList() {
		List<SysUser> lstUser = Lists.newArrayList();
		Map<String, String> map = new HashMap<String, String>();
		map.put("func", "syslist");
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.SysUser, map);
		lstUser = CacheManager.getInstance().getCacher().getList(SysUser.class, key);
		if (CustomerListUtils.isEmpty(lstUser)) {
			lstUser = sysUserService.getSysList();
			CacheManager.getInstance().getCacher().setObject(key, lstUser, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstUser;
	}

	/***
	 * 获取单个用户的详情
	 * 
	 * @param id
	 * @return
	 */
	public static SysUser get(int id) {
		SysUser user = null;
		String key = KeyGenerator.getIdKey(ModuleType.SysUser, id);
		if (CacheManager.getInstance().getCacher() != null) {
			user = (SysUser) CacheManager.getInstance().getCacher().getObject(key);
			if (user == null) {
				user = sysUserService.get(id);
				if (user != null) {
					CacheManager.getInstance().getCacher().setObject(key, user, 0);
				}
			}
		}
		return user;
	}

	/***
	 * 移除单个用户缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.SysUser, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.SysUser,key);
	}

	/***
	 * 获得系统用户 总条数
	 * 
	 * @return total
	 * @param sysUserSearch
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getSysUserCount(SysUserSearch sysUserSearch) {
		int total = 0;
		Map map = new HashMap();
		String areaLongCode = sysUserSearch.getAreaLongCode();
		Integer userType = sysUserSearch.getUserType();
		map.put("areaLongCode", areaLongCode);
		map.put("userType", userType);
		map.put("Count", "Count");

		String key = KeyGenerator.getKeyWithMap(ModuleType.SysUser, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (totalString != null && !"".equals(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = sysUserService.getCount(sysUserSearch);
				if (total > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
				}
			}
		}
		//////System.out.println("********WebSysUserController-topDesignerList-getSysUserCount-key" + key + "********");
		return 0;
	}

	/***
	 * 获得系统用户
	 * 
	 * @return total
	 * @param sysUserSearch
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<SysUser> getPaginatedList(SysUserSearch sysUserSearch) {
		List<SysUser> list = new ArrayList<SysUser>();
		
		Map map = new HashMap();
		String areaLongCode = sysUserSearch.getAreaLongCode();
		Integer userType = sysUserSearch.getUserType();
		map.put("areaLongCode", areaLongCode);
		map.put("userType", userType);
		
		String key = KeyGenerator.getKeyWithMap(ModuleType.SysUser, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(SysUser.class, key);
			if (CustomerListUtils.isEmpty(list)) {
				list = sysUserService.getPaginatedList(sysUserSearch);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		//////System.out.println("********WebSysUserController-topDesignerList-getPaginatedList-key" + key + "********");
		return list;
	}

	/***
	 * 地区基础列表
	 * 
	 * @return areaList
	 * @param BaseArea
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<BaseArea> getbaseAreaList(BaseArea baseArea) {
		List<BaseArea> areaList = new ArrayList<BaseArea>();
		Map map = new HashMap();
		String areaCode = baseArea.getAreaCode();
		map.put("areaCode", areaCode);
		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseArea, map);
		if (CacheManager.getInstance().getCacher() != null) {
			areaList = CacheManager.getInstance().getCacher().getList(BaseArea.class, key);
			if (CustomerListUtils.isEmpty(areaList)) {
				areaList = baseAreaService.getList(baseArea);
				if (!CustomerListUtils.isEmpty(areaList)) {
					CacheManager.getInstance().getCacher().setObject(key, areaList, 0);
				}
			}
		}
		//////System.out.println("********WebSysUserController-topDesignerList-getbaseAreaList-key" + key + "********");
		return areaList;
	}

	/**
	 * 更新用户超时信息
	 * @param user
	 */
	public static void updateTimeOutUser(SysUser user){
		if(user.getId()==null||user.getId()<1){
			logger.info("------更新用户超市信息->用户信息未找到.");
		}
		/*更新user中的登录有效时长信息*/
//		Integer heartbeatTime=Integer.valueOf(Utils.getValue("heartbeatTime", "15"));
//		Integer reserveTime=Integer.valueOf(Utils.getValue("reserveTime", "15"));
//		/*间隔+1分钟*/
//		Long validTime=new Date().getTime()+(heartbeatTime+reserveTime)*1000;
//		user.setValidTime(validTime);
		/*更新user中的登录有效时长信息->end*/
		SysUser userFromCache=checkTimeOutUserByToken(user.getToken());
		//SysUser userFromCache=checkTimeOutUserByToken(user.getToken());
		if(userFromCache==null){
			CacheManager.getInstance().getCacher().setObject(getUserKeyByToken(user.getToken()), user, Utils.OVERTIMESETUP);
			if(StringUtils.isBlank(user.getTerminalImei()))
				logger.info("------缓存中更新超时信息,设备号为空,这种情况不允许存在");
		}else{
			if(StringUtils.isNotBlank(user.getTerminalImei())){
				userFromCache.setTerminalImei(user.getTerminalImei());
			}
			CacheManager.getInstance().getCacher().setObject(getUserKeyByToken(user.getToken()), userFromCache, Utils.OVERTIMESETUP);
			if(StringUtils.isBlank(userFromCache.getTerminalImei()))
				logger.info("------缓存中更新超时信息,设备号为空,这种情况不允许存在");
		}
	}
	
	public static SysUser checkTimeOutUserByToken(String token) {
		SysUser user=(SysUser) CacheManager.getInstance().getCacher().getObject(getUserKeyByToken(token));
		return user;
	}

	private static String getUserKeyByToken(String token) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("token", token);
		String loginSign=Utils.getValue("loginSign", "loginSign").trim();
		if(StringUtils.equals("loginSign", loginSign))
			logger.info("请在app.properties中添加loginSign配置");
		map.put("loginSign", loginSign);
		String key = KeyGenerator.getKeyWithMap(ModuleType.LoginUser, map);
		return key;
	}

	/**
	 * 检测有没有用户超时信息
	 * @param userId
	 * @return
	 */
//	public static SysUser checkTimeOutUser(Integer userId){
//		SysUser user=(SysUser) CacheManager.getInstance().getCacher().getObject(getUserKey(userId));
//		return user;
//	}
	
	/**
	 * 移除用户超时信息
	 * @param userId
	 */
//	public static void removeTimeOutUser(Integer userId){
//		CacheManager.getInstance().getCacher().removeObject((getUserKey(userId)));
//	}
	public static void removeTimeOutUser(Integer userId){
		SysUser sysUser=get(userId);
//		CacheManager.getInstance().getCacher().removeObject(getUserKeyByToken(sysUser.getToken()));
//		removeCacheUserByAppKey(sysUser.getAppKey());
	}
	/**
	 * 获得用于做超时验证的用户信息的key
	 * @param userId
	 * @return
	 */
//	public static String getUserKey(Integer userId){
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("userId", userId);
//		String loginSign=Utils.getValue("loginSign", "loginSign").trim();
//		if(StringUtils.equals("loginSign", loginSign))
//			logger.error("请在app.properties中添加loginSign配置");
//		map.put("loginSign", loginSign);
//		String key = KeyGenerator.getKeyWithMap(ModuleType.LoginUser, map);
//		return key;
//	}
	
	public static void cacheTheLoginUserInfo(LoginUser user, int exSeconds) {
		CacheManager.getInstance().getCacher().setObject(getUserKeyByUserkey(user.getUserKey()), user, exSeconds);
	}

	public static LoginUser getCacheLoginUserByUserkey(String userKey) {
		LoginUser user = (LoginUser) CacheManager.getInstance().getCacher().getObject(getUserKeyByUserkey(userKey));
		return user;
	}
  	
	public static String getUserKeyByUserkey(String userKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userKey", userKey);
		String loginSign = Utils.getValue("loginSign", "loginSign").trim();
		if (StringUtils.equals("loginSign", loginSign))
			logger.info("请在app.properties中添加loginSign配置");
		map.put("loginSign", loginSign);
		String key = KeyGenerator.getKeyWithMap(ModuleType.LoginUser, map);
		return key;
	}
	
	public static void removeCacheUserByUserkey(String userKey){
		CacheManager.getInstance().getCacher().removeObject(getUserKeyByUserkey(userKey));
	}
}
