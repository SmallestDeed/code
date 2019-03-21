package com.sandu.service.user.impl;

import com.google.common.base.Splitter;
import com.sandu.api.redis.RedisService;
import com.sandu.api.user.input.RoleGroupUpdate;
import com.sandu.api.user.input.RoleUpdate;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.model.bo.UserRoleDetailBO;
import com.sandu.api.user.output.RoleGroupVO;
import com.sandu.api.user.output.UserRoleDetailVO;
import com.sandu.api.user.output.UserRoleGroupDetailVO;
import com.sandu.api.user.service.SysRoleGroupService;
import com.sandu.common.constant.UserConstant;
import com.sandu.service.user.dao.SysRoleGroupDao;
import com.sandu.service.user.dao.SysUserDao;
import com.sandu.service.user.dao.SysUserRoleDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("sysRoleGroupService")
public class SysRoleGroupServiceImpl implements SysRoleGroupService {
	private Logger logger = LoggerFactory.getLogger(SysRoleGroupServiceImpl.class);
	@Autowired
	private SysRoleGroupDao sysRoleGroupDao;
	
	@Autowired
	private RedisService redisService;

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public Set<SysUserRoleGroup> getUserRoleGroupByUserId(Long userId) {
		Set<SysUserRoleGroup> userRoleGroupSet = redisService.smembersObject(UserConstant.RBAC_USER_ROLE_GROUP_PREFIX+userId,SysUserRoleGroup.class);
		logger.info("从缓存获取用户角色组:{}",userRoleGroupSet==null?0:userRoleGroupSet.size());
		if(userRoleGroupSet!=null && userRoleGroupSet.size()>0) {
			return userRoleGroupSet;
		}
		userRoleGroupSet = sysRoleGroupDao.selectRoleGroupsByUserId(userId);
		logger.info("缓存没有用户角色组数据,需从数据库读取:{}",userRoleGroupSet==null?0:userRoleGroupSet.size());
		if(userRoleGroupSet!=null && userRoleGroupSet.size()>0) {
			redisService.saddObject(UserConstant.RBAC_USER_ROLE_GROUP_PREFIX+userId, userRoleGroupSet);
			logger.info("将数据库查询结果更新到缓存");
		}
		return userRoleGroupSet;
	}
	@Override
	public void batchSaveUserRoleGroupSet(Set<SysUserRoleGroup> urgs) {
		// TODO Auto-generated method stub
		sysRoleGroupDao.batchInsertUserRoleGroupSet(urgs);
	}

	@Override
	public List<RoleGroupVO> listRoleGroup(Integer type) {
		return sysRoleGroupDao.listRoleGroup(type);
	}

	@Override
	@Transactional
	public Integer updateUserRoleGroup(RoleGroupUpdate roleGroup, LoginUser loginUser) {
		SysUser sysUser = sysUserDao.selectByPrimaryKey(roleGroup.getUserId());
		if (sysUser == null || sysUser.getIsDeleted() == null) {
			logger.error("更新用户权限组：用户不存在或被删除， {}", roleGroup);
			throw new RuntimeException("Error:用户不存在或被删除");
		}

		// 验证授权的权限是否存在
		if (roleGroup.getRoleGroups() != null  && !roleGroup.getRoleGroups().isEmpty()) {
			List<RoleGroupVO> listRoleGroup = this.listRoleGroup(null);
			boolean retainAll = listRoleGroup.retainAll(roleGroup.getRoleGroups());
			if (!retainAll || listRoleGroup.size() != roleGroup.getRoleGroups().size()) {
				logger.error("更新用户权限组：该权限组不存在，不能授权， {}", roleGroup);
				throw new RuntimeException("Error:该权限组不存在，不能授权");
			}

			// 去重
			roleGroup.setRoleGroups(roleGroup.getRoleGroups().stream().distinct().collect(Collectors.toList()));
		}

		// 下面两行Code不要动，有魔法
		// removeAll 处理会把原始集合里的元素删除，需要Copy一份出来处理，否则后面执行 retainAll 逻辑会有错误！！！
		// SysUserRoleGroup 类里的 equals 和 hashCode 方法已被重写，不要动里面的逻辑（WTF!!!!!!!!），否则整段垮掉！！！！！！
		List<SysUserRoleGroup> listAvailableRoleGroup = sysRoleGroupDao.listAvailableRoleGroup(roleGroup.getUserId());
		// 更新和删除
		Integer updateCount = this.handlerUserRoleGroupForUpdate(listAvailableRoleGroup, roleGroup);

		// 新增权限
		SysUserRoleGroup compare = new SysUserRoleGroup();
		Set<SysUserRoleGroup> roleGroupNew = new HashSet<>();

		for (Long item : roleGroup.getRoleGroups()) {
			// 这两行Code不要动，有魔法！！！！！！！WTF
			compare.setRoleGroupId(item);
			compare.setUserId(roleGroup.getUserId().longValue());

			// 排除已存在的、只添加不存在的权限
			if (CollectionUtils.isEmpty(listAvailableRoleGroup) || !listAvailableRoleGroup.contains(compare)) {
				roleGroupNew.add(SysUserRoleGroup.builder()
						.userId(roleGroup.getUserId().longValue())
						.roleGroupId(item)
						.creator(loginUser.getLoginName())
						.modifier(loginUser.getLoginName())
						.gmtCreate(new Date())
						.gmtModified(new Date())
						.isDeleted(0).build());
			}
		}

		if (!roleGroupNew.isEmpty()) {
			logger.info("更新用户权限组：userId => {} 更新权限组 => {}", roleGroup.getUserId(), roleGroup.getRoleGroups());
			updateCount += sysRoleGroupDao.batchInsertUserRoleGroupSet(roleGroupNew);
		}

		// 清除缓存
		if (updateCount > 0) {
			logger.info("更新用户权限组：userId => {} 清除Redis缓存key => {}", roleGroup.getUserId(), UserConstant.RBAC_USER_ROLE_GROUP_PREFIX + roleGroup.getUserId());
			redisService.del(UserConstant.RBAC_USER_ROLE_GROUP_PREFIX + roleGroup.getUserId());
		}

		return updateCount;
	}

	/**
	 * 更新和删除
	 *
	 * @param listAvailableRoleGroup
	 * @param roleGroup
	 * @return
	 */
	private Integer handlerUserRoleGroupForUpdate(List<SysUserRoleGroup> listAvailableRoleGroup, RoleGroupUpdate roleGroup) {
		if (listAvailableRoleGroup == null || listAvailableRoleGroup.isEmpty()) {
			return 0;
		}

		Integer updateCount = 0;

		// 下面两行Code不要动，有魔法
		// removeAll处理会把原始的集合里的元素删除，需要Copy一份出来处理，否则后面执行 retainAll逻辑会有错误！！！
		// SysUserRoleGroup 类里的 equals 和 hashCode 方法已被重写，不要动里面的逻辑（WTF!!!!!!!!），否则整段垮掉！！！！！！
		List<SysUserRoleGroup> deleteRoleGroups = listAvailableRoleGroup.stream().filter(item -> !Objects.equals(item.getIsDeleted(), 1))
				.map(item -> SysUserRoleGroup.builder().id(item.getId()).roleGroupId(item.getRoleGroupId())
						.userId(item.getUserId()).build()).collect(Collectors.toList());
		// 需要处理的权限组
		List<SysUserRoleGroup> prepRoleGroups = roleGroup.getRoleGroups().stream().map(item -> SysUserRoleGroup.builder()
				.roleGroupId(item).userId(roleGroup.getUserId().longValue()).build()).collect(Collectors.toList());

		// 删除已授权，但本次不需要授权的权限组
		deleteRoleGroups.removeAll(prepRoleGroups);
		if (!deleteRoleGroups.isEmpty()) {
			logger.info("更新用户权限组：userId => {} 删除权限组 => {}", roleGroup.getUserId(), deleteRoleGroups);
			updateCount += sysRoleGroupDao.updateUserRoleGroup(1, deleteRoleGroups);
		}

		// 找出删除已存在之后的交集的，这些都是不需要添加的数据，进行更新操作
		listAvailableRoleGroup.retainAll(prepRoleGroups);
		List<SysUserRoleGroup> existsRoleGroups = listAvailableRoleGroup.stream().filter(item ->
				!Objects.equals(item.getIsDeleted(), 0)).distinct().collect(Collectors.toList());
		if (!existsRoleGroups.isEmpty()) {
			logger.info("更新用户权限组：userId => {} 更新权限组 => {}", roleGroup.getUserId(), existsRoleGroups);
			sysRoleGroupDao.updateUserRoleGroup(0, existsRoleGroups);
		}

		return updateCount;
	}

	@Override
	public UserRoleGroupDetailVO getUserRoleGroup(Integer userId) {
		Set<SysUserRoleGroup> roleGroups = this.getUserRoleGroupByUserId(Long.valueOf(userId));
		if (roleGroups == null || roleGroups.isEmpty()) {
			return UserRoleGroupDetailVO.builder().userId(userId).build();
		}

		List<Long> listRoleGroup = roleGroups.stream().map(SysUserRoleGroup::getRoleGroupId).collect(Collectors.toList());
		return UserRoleGroupDetailVO.builder().userId(userId).roleGroups(listRoleGroup).build();
	}

	@Override
	public List<UserRoleDetailVO> getUserRole(Integer userId) {
		List<UserRoleDetailBO> listUserRoleBO = sysUserRoleDao.listUserRole(userId);
		if (listUserRoleBO == null || listUserRoleBO.isEmpty()) {
			logger.info("获取用户权限：userId => {} 没有权限信息", userId);
			return new ArrayList<>(0);
		}

		List<UserRoleDetailVO> items = new ArrayList<>();

		// 第一层为平台
		// 第二层才是权限
		for (UserRoleDetailBO itemBO : listUserRoleBO) {
			List<String> listRoleId = !StringUtils.isEmpty(itemBO.getRoleIds())
					? Splitter.on(",").trimResults().omitEmptyStrings().splitToList(itemBO.getRoleIds())
					: null;
			List<String> listRoleName = !StringUtils.isEmpty(itemBO.getRoleNames())
					? Splitter.on(",").trimResults().omitEmptyStrings().splitToList(itemBO.getRoleNames())
					: null;

			// 因为是group_concat函数拼接的，所有roleIds和roleNames的数量可能不一致，导致处理异常
			if (listRoleId == null || listRoleId.isEmpty() || listRoleName == null || listRoleName.isEmpty()
					|| listRoleId.size() != listRoleName.size()) {
				logger.info("获取用户权限：userId => {} 的权限信息异常", userId);
				return new ArrayList<>(0);
			}

			// 权限处理
			List<UserRoleDetailVO> children = new ArrayList<>();
			for (int index = 0; index < listRoleId.size(); index++) {
				children.add(UserRoleDetailVO.builder()
						.id(Integer.valueOf(listRoleId.get(index)))
						.label(listRoleName.get(index))
						.parentId(itemBO.getPlatformId()).build());
			}

			items.add(UserRoleDetailVO.builder()
					.id(itemBO.getPlatformId())
					.label(itemBO.getPlatformName())
					.children(children).build());
		}

		return items;
	}

	@Override
	@Transactional
	public Integer updateUserRole(RoleUpdate update, LoginUser loginUser) {
		SysUser sysUser = sysUserDao.selectByPrimaryKey(update.getUserId());
		if (sysUser == null || sysUser.getIsDeleted() == 1) {
			logger.error("更新用户权限：用户不存在或被删除，userId => {}", update.getUserId());
			throw new RuntimeException("Error:用户不存在或被删除");
		}

		List<Long> listRoleId = !StringUtils.isEmpty(update.getRoleIds())
				? Splitter.on(",").trimResults().omitEmptyStrings().splitToList(update.getRoleIds())
					.stream().map(Long::valueOf).distinct().collect(Collectors.toList())
				: new ArrayList<>(0);

		List<SysUserRole> listAvailableRole = sysUserRoleDao.listAvailableUserRole(update.getUserId());
		// 处理用户权限的更新和删除
		Integer updateCount = this.handlerUserRoleForUpdate(update.getUserId(), listRoleId, listAvailableRole);

		SysUserRole compare = new SysUserRole();
		List<SysUserRole> listUserRoleNew = new ArrayList<>();

		for (Long item : listRoleId) {
			compare.setRoleId(item);
			compare.setUserId(update.getUserId().longValue());

			// 排除已存在的、只添加不存在的权限
			if (CollectionUtils.isEmpty(listAvailableRole) || !listAvailableRole.contains(compare)) {
				listUserRoleNew.add(SysUserRole.builder()
						.userId(update.getUserId().longValue())
						.roleId(item)
						.creator(loginUser.getLoginName())
						.modifier(loginUser.getLoginName())
						.gmtCreate(new Date())
						.gmtModified(new Date())
						.isDeleted(0).build());
			}
		}

		if (!listUserRoleNew.isEmpty()) {
			updateCount += sysUserRoleDao.batchNewUserRole(listUserRoleNew);
		}

		// 清除Redis缓存
		if (updateCount > 0) {
			logger.info("更新用户权限：userId => {} 清除Redis缓存key => {}", update.getUserId(), UserConstant.RBAC_USER_ROLE_PREFIX + update.getUserId());
			redisService.del(UserConstant.RBAC_USER_ROLE_PREFIX + update.getUserId());
		}

		return updateCount;
	}

	/**
	 * 处理用户权限的更新和删除
	 * 相关的逻辑请看更新权限组的逻辑{@link SysRoleGroupServiceImpl#updateUserRoleGroup(com.sandu.api.user.input.RoleGroupUpdate, com.sandu.api.user.model.LoginUser)}
	 *
	 * @param userId
	 * @param listRoleId
	 * @param listAvailableUserRole
	 * @return
	 */
	private Integer handlerUserRoleForUpdate(Integer userId, List<Long> listRoleId, List<SysUserRole> listAvailableUserRole) {
		if (listAvailableUserRole == null || listAvailableUserRole.isEmpty()) {
			logger.info("更新用户权限：userId => {} 没有存在的权限", userId);
			return 0;
		}

		Integer updateCount = 0;

		List<SysUserRole> deleteRoles = listAvailableUserRole.stream().filter(item -> !Objects.equals(item.getIsDeleted(), 1))
				.map(item -> SysUserRole.builder().id(item.getId()).userId(item.getUserId()).roleId(item.getRoleId())
						.isDeleted(item.getIsDeleted()).build()).collect(Collectors.toList());
		// 需要处理的
		List<SysUserRole> prepRoles = listRoleId.stream().map(item -> SysUserRole.builder()
				.userId(userId.longValue()).roleId(item).build()).collect(Collectors.toList());

		// 删除已授权，但本次不需要授权的权限组
		deleteRoles.removeAll(prepRoles);
		if (!deleteRoles.isEmpty()) {
			logger.info("更新用户权限：userId => {} 删除权限 => {}", userId, deleteRoles);
			updateCount += sysUserRoleDao.updateUserRole(1, deleteRoles);
		}

		// 找出删除已存在之后的交集的，这些都是不需要添加的数据，进行更新操作
		listAvailableUserRole.retainAll(prepRoles);
		List<SysUserRole> existsRoles = listAvailableUserRole.stream().filter(item -> !Objects.equals(item.getIsDeleted(), 0)).distinct().collect(Collectors.toList());
		if (!existsRoles.isEmpty()) {
			logger.info("更新用户权限：userId => {} 更新权限 => {}", userId, existsRoles);
			sysUserRoleDao.updateUserRole(0, existsRoles);
		}

		return updateCount;
	}

    @Override
    public void batchDel(Long id, List<Integer> roleGroupIds) {
        sysRoleGroupDao.batchDel(id,roleGroupIds);
    }
}
