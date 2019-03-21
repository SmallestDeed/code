package com.sandu.web.user.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.LoginUserCommonConstant;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserVo;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserSessionService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @desc 用户会话Controller
 * @date 20171017
 * @auth pengxuangang
 */
@RestController
@RequestMapping("/v1/tocmobile/user")
public class UserSessionController {

	private final static String CLASS_LOG_PREFIX = "[用户会话操作]:";
	private final static Logger logger = LoggerFactory.getLogger(UserSessionController.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private UserSessionService userSessionService;

	// 如果header里取不到mediaType，默认toC移动端为5 ios
	private final static String OPERATING_TOCMOBILE_MEDIATYPE = "5";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEnvelope<LoginUser> checkLogin(@RequestBody UserVo userVo, HttpServletRequest request,
			HttpServletResponse response) {
		String mediaType = null;
		if (null != request.getHeader("MediaType")) {
			mediaType = request.getHeader("MediaType").toString();
		} else {
			mediaType = OPERATING_TOCMOBILE_MEDIATYPE;
		}
		if (null == userVo || StringUtils.isEmpty(userVo.getUserName())
				|| StringUtils.isEmpty(userVo.getUserPassword())) {
			logger.warn(CLASS_LOG_PREFIX + "用户登录->用户登录失败，必需参数为空:UserVo:{}",
					(null == userVo) ? "null" : userVo.toString());
			return new ResponseEnvelope(false, "用户登录失败，必需参数为空!");
		}

		// 登录验证
		logger.info(CLASS_LOG_PREFIX + "用户登录-> Step 1: 验证用户-UserVo:{}", userVo.toString());
		SysUser sysUser = sysUserService.checkUserAccount(userVo.getUserName(), userVo.getUserPassword());

		logger.info(CLASS_LOG_PREFIX + "用户登录-> Step 1: 验证用户完成-SysUser:{}",
				(null == sysUser ? "null" : sysUser.toString()));

		if (null == sysUser) {
			logger.warn(CLASS_LOG_PREFIX + "用户登录->用户登录失败,用户名或密码错误!UserVo:{}", userVo.toString());
			return new ResponseEnvelope(false, "登录失败,用户名或密码错误!");
		}

		logger.info(CLASS_LOG_PREFIX + "用户登录-> Step 1: 用户登录成功:SysUser:{}", sysUser.toString());

		String sessionId = request.getRequestedSessionId();
		if (StringUtils.isEmpty(sessionId)) {
			sessionId = UUID.randomUUID().toString().replace("-", "");
		}

		// 用户信息转换
		LoginUser loginUser = this.getLoginUser(sysUser, sessionId, mediaType);
		return new ResponseEnvelope<LoginUser>(true, loginUser);
	}

	private LoginUser getLoginUser(SysUser sysuser, String sessionId, String mediaType) {
		LoginUser loginUser = new LoginUser();
		String uuidKey = UUID.randomUUID().toString().replace("-", "");
		Map<String, Object> payload = new HashMap<String, Object>();

		Date date = new Date();
		payload.put("uid", sysuser.getId());// 用户ID
		payload.put("appKey", uuidKey);// 用户ID
		payload.put("signflat", LoginUserCommonConstant.LOGIN_FROM_TOCMOBILE);
		payload.put("utype", sysuser.getUserType());
		payload.put("iat", date.getTime());// 生成时间
		payload.put("uname", sysuser.getNickName());
		payload.put("mtype", mediaType);
		payload.put("ext", date.getTime() + 1000 * 60 * 60 * 3);// 过期时间3小时

		String token = Jwt.createToken(payload);

		loginUser.setAppKey(uuidKey);
		loginUser.setToken(token);
		loginUser.setId(sysuser.getId());
		loginUser.setUserType(sysuser.getUserType());
		loginUser.setMediaType(OPERATING_TOCMOBILE_MEDIATYPE);
		loginUser.setBalanceAmount(sysuser.getBalanceAmount());
		loginUser.setConsumAmount(sysuser.getConsumAmount());

		String loginFlag = LoginUserCommonConstant.LOGIN_FROM_TOCMOBILE;

		// 设置redis超时时间
		int failureTime = LoginUserCommonConstant.REDIS_EXPIRE_TIME;

		// 系统标识加userid
		String userKey = loginFlag + loginUser.getId();

		userSessionService.loginUserToCache(loginFlag + uuidKey, loginUser, failureTime);
		userSessionService.loginUserToCache(userKey, loginUser, failureTime);
		logger.info("将LoginUser存储到缓存中:" + loginUser + ",redis的key:" + userKey);
		return loginUser;
	}

	/**
	 * 用户退出登录
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/logout")
	public ResponseEnvelope userLogout(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		// 解析token
		Map<String, Object> dataMap = Jwt.validToken(token);

		JSONObject dataObj = (JSONObject) dataMap.get("data");
		if (dataObj == null) {
			logger.info("用戶退出操作解析token失敗:" + token);
			return new ResponseEnvelope(false, "请登录");
		}
		Long userId = (Long) dataObj.get("uid");
		String appKey = (String) dataObj.get("appKey");

		// 退出用户
		if (userId == null || StringUtils.isEmpty(appKey)) {
			logger.info("从token中获取信息失败");
			return new ResponseEnvelope(false, "请登录");
		}
		String key = LoginUserCommonConstant.LOGIN_FROM_TOCMOBILE + userId;
		logger.info(CLASS_LOG_PREFIX + "用户退出登录->删除用户会话-SessionId:{}", request.getRequestedSessionId());
		boolean resultStatus = userSessionService.loginOut(key);
//		boolean resultStatus2 = userSessionService.loginOut(LoginUserCommonConstant.LOGIN_FROM_TOCMOBILE + appKey);
		logger.info(CLASS_LOG_PREFIX + "用户退出登录->删除用户会话成功-ResultStatus:{}", resultStatus);

		return new ResponseEnvelope(resultStatus, resultStatus ? "退出登录成功!" : "退出登录失败!");
	}

}
