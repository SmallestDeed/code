package com.sandu.util.auth;

import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.common.HouseRole;
import com.sandu.common.LoginContext;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.UserType;
import com.sandu.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月30日
 */

@Slf4j
public class HouseAuthUtils {
	
	public static boolean hasPermission(String authKey) {
		UserLoginBO loginUser = getUserLoginBO();
		log.debug("用户权限: {}(userId), Roles => {}", loginUser.getId(), loginUser.getRoles());
		if (loginUser.getRoles() != null) {
			boolean isAuth = loginUser.getRoles().contains(authKey);
			log.debug("权限验证: {}(userId), {}(authKey) => {}", loginUser.getId(), authKey, isAuth);
			return isAuth;
		}

		return false;
	}

	/**
	 * 户型绘制员
	 * 
	 * @return
	 */
	public static boolean hasDrawPermission() {
		return hasPermission(HouseRole.INTERNAL_HOUSE_DRAW);
	}

	/**
	 * 户型审核员
	 * 
	 * @return
	 */
	public static boolean hasApprovedPermission() {
		return !hasPermission(HouseRole.HOUSE_DRAW_APPROVED);
	}

	/**
	 * 户型完善员
	 * 
	 * @return
	 */
	public static boolean hasImproverPermission() {
		return hasPermission(HouseRole.HOUSE_DRAW_IMPROVER);
	}

	/**
	 * 外部全流程绘制(内部演示员)
	 * 
	 * @return
	 */
	public static boolean hasInternalDemoPermission() {
		return hasPermission(HouseRole.INTERNAL_DEMO_DRAW);
	}

	/**
	 * 户型修改员
	 * @return
	 */
	public static boolean hasHouseModification() {
		return hasPermission(HouseRole.HOUSE_MODIFICATION);
	}

	/**
	 * 普通绘制员
	 * 
	 * @return 没有任何角色时，默认普通绘制员（true）！
	 */
	public static boolean hasCommonPermission() {
		UserLoginBO login = getUserLoginBO();
		if (login.getRoles() == null || login.getRoles().isEmpty()) {
			return true;
		}
		return login.getRoles().contains(HouseRole.COMMON_HOUSE_DRAW);
	}

	/**
	 * 是否可以跳过户型审核
	 * 
	 * @return 完善绘制员、内部演示员直接跳过审核权限，进入烘焙阶段
	 */
	public static boolean hasJumpHouseCheckPermission() {
		// 户型绘制 > 内部演示员 > 完善绘制员 > 普通绘制员
		if (!hasDrawPermission()) {
			return (hasInternalDemoPermission() || hasImproverPermission() || hasCommonPermission());
		}
		return false;
	}

	/**
	 * 绘制员 ==> 0 普通绘制员、内部演示员 ==> 1
	 * 
	 * @return
	 */
	public static boolean hasDataPermission() {
		// 户型绘制 > 内部演示员 > 普通绘制员
		if (!hasDrawPermission()) {
			return (hasInternalDemoPermission() || hasCommonPermission());
		}
		return false;
	}

	/**
	 * 是否是内部用户
	 * 
	 * @return
	 */
	public static boolean isInternalUser() {
		return UserType.INTERNAL.getValue().equals(getUserType());
	}

	/**
	 * 获取用户类型
	 * 
	 * @return
	 */
	public static Integer getUserType() {
		return getUserLoginBO().getUserType();
	}

	public static UserLoginBO getUserLoginBO() {
		UserLoginBO login = LoginContext.getLoginUser(UserLoginBO.class);
		if (login == null) {
			throw new BusinessException(false, ResponseEnum.UNAUTHORIZED);
		}
		
		log.debug("用户信息: {}(userId), {}(userName), {}(loginName)", login.getId(), login.getName(), login.getLoginName());
		return login;
	}

	/**
	 * 是否登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return getUserLoginBO() != null;
	}
	
	/**
	 * 获取当前请求的userId
	 * 
	 * @return
	 */
	public static Long getRequestUserId() {
		return getUserLoginBO().getId();
	}

	/**
	 * 获取当前请求的userName
	 * 
	 * @return
	 */
	public static String getRequestUserName() {
		return getLoginName();
	}

	/**
	 * 获取登录名
	 * @return
	 */
	public static String getLoginName() {
		return getUserLoginBO().getLoginName();
	}

	/**
	 * 获取一个unknowUser
	 * 
	 * @author huangsongbo
	 * @return
	 */
	public static UserLoginBO getUnknownUser() {
		UserLoginBO loginUser = new UserLoginBO();
		loginUser.setId(-1L);
		loginUser.setLoginId("-1");
		loginUser.setLoginName("unknown");
		loginUser.setLoginPhone("unknown");
		loginUser.setName("unknown");
		return loginUser;
	}
}
