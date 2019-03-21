package com.sandu.service.user.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.redis.RedisService;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysRoleFunc;
import com.sandu.api.user.model.SysRoleGroupRef;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.common.constant.UserConstant;
import com.sandu.service.user.dao.SysRoleDao;
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	private Logger logger = LoggerFactory.getLogger(SysRoleGroupServiceImpl.class);
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private RedisService redisService;
	
	@Override
	public Set<SysRoleGroupRef> getRoleGroupRefsByRoleGroupIds(Set<Long> roleGrogroupIds) {
		logger.info("当前用户拥有的角色组:{}",roleGrogroupIds);
		if(roleGrogroupIds==null || roleGrogroupIds.size()<=0) return null;
		String[] roleGroupIdArray = new String[roleGrogroupIds.size()];
		int index=0;
		for (Long groupId:roleGrogroupIds){
			roleGroupIdArray[index++] = (UserConstant.RBAC_ROLE_GROUP_REF_PREFIX+groupId);
		}
		
		//判断数据库数据与缓存数据是否一致(如果后台删除一个角色权限缓存数据(角色权限变更时需要删除),则不一致)
		Set<String> roleGroupRefkeys= redisService.keys(UserConstant.RBAC_ROLE_GROUP_REF_PREFIX+"*");
		logger.info("现有缓存所有角色角色组关联数据:{}",roleGroupRefkeys);
		if(roleGroupRefkeys==null) roleGroupRefkeys = new HashSet<String>();
		if(roleGroupRefkeys.size()>=0) {
			for(int i=0;i<roleGroupIdArray.length;i++) {
				//如果缓存数据不存在,则需要从db获取并缓存
				String roleGroupCacheKey = roleGroupIdArray[i];
				if(!roleGroupRefkeys.contains(roleGroupCacheKey)) {
					int strIndex = roleGroupCacheKey.indexOf(UserConstant.RBAC_ROLE_GROUP_REF_PREFIX);
					strIndex += UserConstant.RBAC_ROLE_GROUP_REF_PREFIX.length();
					Long roleGroupId = Long.valueOf(roleGroupCacheKey.substring(strIndex));
					Set<SysRoleGroupRef> roleGroupRefSet = sysRoleDao.selectRoleGroupRefsByRoleGroupId(roleGroupId);
					logger.info("现有缓存没有角色角色组{}关联数据,需要从数据库获取:{}",roleGroupId,roleGroupRefSet==null?0:roleGroupRefSet.size());
					if(roleGroupRefSet!=null && roleGroupRefSet.size()>0) {
						redisService.saddObject(roleGroupCacheKey, roleGroupRefSet);
					}else {
						logger.info("数据库数据异常:角色组{}没有配置角色", roleGroupId);
						//防止异常数据导致缓存穿透
						String[] temp = new String[1];
						temp[0]="";
						redisService.sadd(roleGroupCacheKey, temp);
						
					}
				}
			}
		}
		//批量从缓存获取所有角色权限关联数据
		Set<SysRoleGroupRef> roleGroupRefSet = redisService.sunionObjects(roleGroupIdArray,SysRoleGroupRef.class);
		logger.info("一次性从缓存获取角色角色组关联数据:{}",roleGroupRefSet);
		return roleGroupRefSet;
	}
	
	
	@Override
	public Set<SysUserRole> getUserRolesByUserId(Long userId) {
		Set<SysUserRole> userRoleSet = redisService.smembersObject(UserConstant.RBAC_USER_ROLE_PREFIX+userId, SysUserRole.class);
		logger.info("从缓存获取user{}的用户角色关系数据:{}",userId,userRoleSet==null?0:userRoleSet.size());
		if(userRoleSet!=null && userRoleSet.size()>0) {
			return userRoleSet;
		}
		userRoleSet = sysRoleDao.selectUserRolesByUserId(userId);
		logger.info("缓存不存在user{}的用户角色关系数据,需要从数据库获取:{}",userId,userRoleSet==null?0:userRoleSet.size());
		if(userRoleSet!=null && userRoleSet.size()>0) {
			redisService.saddObject(UserConstant.RBAC_USER_ROLE_PREFIX+userId, userRoleSet);
			logger.info("缓存数据库数据user{}:{}",userId,userRoleSet==null?0:userRoleSet.size());
		}
		return userRoleSet;
		
	}


	@Override
	public Set<SysRoleFunc> getRoleFuncByRoleIds(Set<Long> roleIds) {
		logger.info("当前用户角色id列表:{}",roleIds);
		if(roleIds==null || roleIds.size()==0) {
			return null;
		}
		//获取所有角色对应的权限
		String[] roleIdArray = new String[roleIds.size()];
		int index=0;
		for (Long roleId:roleIds){
			roleIdArray[index++] = (UserConstant.RBAC_ROLE_FUNC_PREFIX+roleId);
		}
		//判断数据库数据与缓存数据是否一致(如果后台删除一个角色权限缓存数据(角色权限变更时需要删除),则不一致)
		Set<String> roleFuncKeys = redisService.keys(UserConstant.RBAC_ROLE_FUNC_PREFIX+"*");
		logger.info("现有缓存已授权的角色id列表:{}",roleFuncKeys);
		if(roleFuncKeys==null) roleFuncKeys = new HashSet<String>();
		if(roleFuncKeys.size()>=0) {
			for(int i=0;i<roleIdArray.length;i++) {
				//如果缓存数据不存在,则需要从db获取并缓存
				String roleCacheKey = roleIdArray[i];
				if(!roleFuncKeys.contains(roleCacheKey)) {
					int strIndex = roleCacheKey.indexOf(UserConstant.RBAC_ROLE_FUNC_PREFIX);
					strIndex += UserConstant.RBAC_ROLE_FUNC_PREFIX.length();
					Long roleId = Long.valueOf(roleCacheKey.substring(strIndex));
					Set<SysRoleFunc> roleFuncSet = sysRoleDao.selectRoleFuncByRoleId(roleId);
					logger.info("现有缓存没有角色{}权限关联数据,需要从数据库获取:{}",roleId,roleFuncSet);
					if(roleFuncSet!=null && roleFuncSet.size()>0) {
						redisService.saddObject(roleCacheKey, roleFuncSet);
					}else {
						logger.info("数据库数据配置异常(角色未配置权限)roleId:{}",roleId);
						//防止异常数据导致缓存穿透
						String[] temp = new String[1];
						temp[0]="";
						redisService.sadd(roleCacheKey, temp);
						
					}
				}
			}
		}		
		//批量从缓存获取所有角色权限关联数据
		Set<SysRoleFunc> roleFuncSet = redisService.sunionObjects(roleIdArray,SysRoleFunc.class);
		logger.info("一次性从缓存获取角色权限关联数据:{}",roleFuncSet==null?0:roleFuncSet.size());
		return roleFuncSet;
	}


	@Override
	public List<SysRole> getRolesByRoleIds(Set<Long> roleIds) {
		logger.info("当前用户角色id列表:{}",roleIds);
		if(roleIds==null || roleIds.size()==0) {
			return null;
		}
		//获取角色
		String[] roleIdArray = new String[roleIds.size()];
		int index=0;
		for (Long roleId:roleIds){
			roleIdArray[index++] = (UserConstant.RBAC_ROLE_PREFIX+roleId);
		}
		//判断数据库数据与缓存数据是否一致(如果后台删除一个角色权限缓存数据(角色权限变更时需要删除),则不一致)
		Set<String> roleKeys = redisService.keys(UserConstant.RBAC_ROLE_PREFIX+"*");
		logger.info("现有缓存的角色id列表:{}",roleKeys);
		if(roleKeys==null) roleKeys = new HashSet<String>();
		if(roleKeys.size()>=0) {
			for(int i=0;i<roleIdArray.length;i++) {
				//如果缓存数据不存在,则需要从db获取并缓存
				String roleCacheKey = roleIdArray[i];
				if(!roleKeys.contains(roleCacheKey)) {
					int strIndex = roleCacheKey.indexOf(UserConstant.RBAC_ROLE_PREFIX);
					strIndex += UserConstant.RBAC_ROLE_PREFIX.length();
					Long roleId = Long.valueOf(roleCacheKey.substring(strIndex));
					SysRole roleRole = sysRoleDao.selectRoleByRoleId(roleId);
					logger.info("现有缓存没有角色{}数据,需要从数据库获取:{}",roleId,roleRole);
					if(roleRole!=null) {
						redisService.setObject(roleCacheKey, roleRole);
					}else {
						logger.info("数据库数据配置异常(角色未找到)roleId:{}",roleId);
						//防止异常数据导致缓存穿透
						redisService.set(roleCacheKey, "");
					}
				}
			}
		}		
		//批量从缓存获取所有角色权限关联数据
		List<SysRole> roleList = redisService.getObjects(roleIdArray,SysRole.class);
		logger.info("一次性从缓存获取角色数据:{}",roleList==null?0:roleList.size());
		return roleList;
	}

	@Override
	public SysRole getRoleByCode(String code) {
		return sysRoleDao.selectRoleByCode(code);
	}

	@Override
	public int saveUserRole(SysUserRole sr) {
		return sysRoleDao.save(sr);
	}

	@Override
	public void delUserRole(Integer userId, List<Integer> oldRoleIds) {
		sysRoleDao.delUserRole(userId,oldRoleIds);
	}

	@Override
	public void batchUserRole(List<SysUserRole> userRoles) {
		sysRoleDao.batchUserRole(userRoles);
	}

	@Override
	public Set<Long> getRolePlatformId(List<Integer> roleIds) {
		return sysRoleDao.getRolePlatformId(roleIds);
	}

	@Override
	public Set<Long> batchRoleByRoleRroupIds(Set<Long> roleGroupIds) {
		return sysRoleDao.batchRoleByRoleRroupIds(roleGroupIds);
	}
}
