package com.nork.mobile.controller;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignPlan;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.pay.metadata.PayState;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.search.PayOrderSearch;
import com.nork.pay.service.PayOrderService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.controller.web.WebSysUserController;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import com.nork.system.sms.httpclient.SmsClient;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Controller
@RequestMapping(value = "/{style}/mobile/user")
public class MobileUserController  {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	private static Logger logger = Logger.getLogger(WebSysUserController.class);
	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	/*** 获取配置文件 webSocket.properties */
//	private final static ResourceBundle webSocket = ResourceBundle
//			.getBundle("config/webSocket");
	private final String SERVERURL = app.getString("app.server.url");
	private final String RESOURCESURL = app.getString("app.resources.url");
	private final String SITENAME = Utils.getValue("app.server.siteName",
			"nork");
//	private final String SITEKEY = Utils.getValue("app.server.siteKey",
//			"online");

	/**
	 * 移动端登录接口
	 * @param sysUser
	 * @return
	 */
//	@RequestMapping(value = "/login")
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEnvelope login(@RequestBody SysUser sysUser, HttpServletRequest request) {
		String account = sysUser.getAccount();
		String pwd = sysUser.getPassword();
		if(StringUtils.isBlank(account))
			 return new ResponseEnvelope<SysUser>(false, "账号不能为空!");
		if(StringUtils.isBlank(pwd))
			return new ResponseEnvelope<SysUser>(false, "密码不能为空!");
//		SysUser user = mobileUserService.findByMobileAndPwd(account, pwd);
		//判断账号是否存在
		SysUser checkUserExist = sysUserService.findWithAccount(account);
		if(checkUserExist == null) {
			return new ResponseEnvelope<SysUser>(false, "账号未注册，请先在电脑端注册后再登录!");
		}
		
		SysUser user = sysUserService.checkUserAccount(account,pwd);
		if (user == null) {
			return new ResponseEnvelope<SysUser>(false, "账号或密码错误!");
		}
		String sessionId = request.getRequestedSessionId();
		if(StringUtils.isBlank(sessionId)) {
			sessionId = UUID.randomUUID().toString().replace("-", "");
		}
		LoginUser loginUser = this.getLoginUser(user,sessionId);
		
		/*查询用户关联的序列号list*/
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(new Integer(1));
		List<AuthorizedConfig> list = authorizedConfigService.getList(authorizedConfig);
		if(list == null || list.size() == 0) {
			return new ResponseEnvelope<SysUser>(false, "尚未绑定序列号或序列号已过期，请在电脑端绑定后再登录！", "");
		}
		/*查询用户关联的序列号list->end*/
		
		//用户是否开通移动端
		if(user.getExistsMobile() != SystemCommonConstant.HAS_EXISTS_MOBILE) {
			return new ResponseEnvelope<SysUser>(false, "您尚未开通移动版功能，请联系客服开通!");
		}else {
			Date mobileClosedDate = user.getMobileClosedDate();
			Date date = new Date();
			if(mobileClosedDate.before(date)) {
				return new ResponseEnvelope<SysUser>(true, "移动版已到期，请续费开通！");
			}
		}

		loginUser.setServerUrl(SERVERURL);
		loginUser.setResourcesUrl(RESOURCESURL);
		loginUser.setSiteName(SITENAME);
		return new ResponseEnvelope<LoginUser>(true, loginUser);
	}
	
	
	private LoginUser getLoginUser(SysUser user, String sessionId) {
		LoginUser loginUser = new LoginUser();
//		String token = UUID.randomUUID().toString().replace("-", "");
		String appKey = UUID.randomUUID().toString().replace("-", "");
		Map<String , Object> payload=new HashMap<String, Object>();
		Date date=new Date();
		payload.put("uid", user.getId());//用户ID
		payload.put("appKey", appKey);//用户ID
		payload.put("utype", user.getUserType());
		payload.put("iat", date.getTime());//生成时间
		payload.put("uname", user.getNickName());
		payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时 
		
		String token=Jwt.createToken(payload);
		
		loginUser.setAppKey(appKey);
		loginUser.setToken(token);
		loginUser.setId(user.getId());
		loginUser.setUserType(user.getUserType());
		loginUser.setServerUrl(SERVERURL);
		loginUser.setResourcesUrl(RESOURCESURL);
		loginUser.setSiteName(SITENAME);
		loginUser.setBalanceAmount(user.getBalanceAmount());
		loginUser.setConsumAmount(user.getConsumAmount());
		String loginFlag =SystemCommonConstant.LOGIN_FROM_MOBILE; //移动端登陆标识
		String key = loginFlag + appKey;

		String onlineUserKey = loginFlag + user.getId().toString(); //当前登录用户信息key
		
		int expireInSeconds = 60 * 60 * 24 ;
		
		if(Utils.enableRedisCache()) {
		 LoginUser onlineUser = new LoginUser();
		 onlineUser = (LoginUser) CacheManager.getInstance().getCacher().getObject(onlineUserKey);
		 if(onlineUser != null && onlineUser.getAppKey() != null) {
		   //移除之前登录用户的缓存
		   CacheManager.getInstance().getCacher().remove(onlineUserKey);
		   CacheManager.getInstance().getCacher().remove(key);
		 }
		}
		
		CacheManager.getInstance().getCacher().setObject(onlineUserKey, loginUser, expireInSeconds);
		CacheManager.getInstance().getCacher().setObject(key, loginUser, expireInSeconds);
		return loginUser;
	}
	
	/**
	 * 获取 验证码 的接口
	 */
	@RequestMapping(value = "/app/sms")
	@ResponseBody
	public Object sms(@RequestBody SysUser sysUser2,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String msgId = sysUser2.getMsgId();
		String mobile = sysUser2.getMobile();
		String type = "";

		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！！", msgId);
		}
		if (StringUtils.isEmpty(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "手机号码不能为空！", msgId);
		}
		if (!isMobile(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！", msgId);
		}
		
		//判断账号是否存在
		SysUser checkUserExist = sysUserService.findWithAccount(mobile);
		if(checkUserExist == null) {
			return new ResponseEnvelope<SysUser>(false, "账号未注册，请先在电脑端注册后再登录!");
		}
		
		String cacheEnable = Utils.getValue("redisCacheEnable", "0");
		String result = "0";
		/** 短信验证码 */
		String code = Utils.generateRandomDigitString(6);
		if ("1".equals(cacheEnable)) {
			CacheManager.getInstance().getCacher().remove("REGISTER_CODE_" + mobile);
			CacheManager.getInstance().getCacher().set("REGISTER_CODE_" + mobile, code, 0);
			CacheManager.getInstance().getCacher().remove("REGISTER_CODE_TIMEOUT_" + mobile);
			CacheManager.getInstance().getCacher().set("REGISTER_CODE_TIMEOUT_" + mobile,System.currentTimeMillis()+"",0);
			CacheManager.getInstance().getCacher().remove("REGISTER_CODE_Count" + mobile);
			CacheManager.getInstance().getCacher().set("REGISTER_CODE_Count" + mobile, "0", 0);

		} else {
			request.getServletContext().setAttribute("REGISTER_CODE_" + mobile,	code);
			request.getServletContext().setAttribute("REGISTER_CODE_TIMEOUT_" + mobile,System.currentTimeMillis());
			request.getServletContext().setAttribute("REGISTER_CODE_Count" + mobile, "0");
		}
		// //System.out.println("验证码------------------："+code);

		/** 发送短信的方法的参数准备 */
		// 短信内容+随机生成的6位短信验证码
		String message = "";
		if ("register".equals(type)) {
			message=MessageFormat.format(
					SmsClient.app.getString("registerContext"), code,
					SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
		}else{
			message=MessageFormat.format(
					SmsClient.app.getString("updateMobileContext"), code,
					SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
		}
		message = URLEncoder.encode(message, "UTF-8");
		// 短信接口参数
		// mobile = "13906513994,18358100817,15816860922";
		long seqId = System.currentTimeMillis();
		String params = "phone=" + mobile + "&message=" + message
				+ "&addserial=&seqid=" + seqId;
		result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
	
		if ("0".equals(result)) {
			return new ResponseEnvelope<String>(true, "发送成功！", msgId,true);
		} else {
			return new ResponseEnvelope<String>(false, "发送验证短信异常！", msgId);
		}
	}
	
	/**
	 * 手机号验证
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern
				.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	/*
	 * 找回密码接口
	 */
	@RequestMapping(value = "/app/findPassword")
	@ResponseBody
	public Object findPassword(/*String mobile, String newPassword, String code,String msgId,*/
			@RequestBody SysUser sysUser2, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String msgId = sysUser2.getMsgId();
		String newPassword = sysUser2.getPassword();
		String code = sysUser2.getRemark();
		String mobile = sysUser2.getAccount();
		
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数msgId不能为空", msgId);
		}
		if (StringUtils.isEmpty(newPassword)) {
			return new ResponseEnvelope<SysUser>(false, "新密码不能为空", msgId);
		}
		if (StringUtils.isEmpty(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "手机号不能为空", msgId);
		}
		if (!isMobile(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！", msgId);
		}
		if (StringUtils.isEmpty(code)) {
			return new ResponseEnvelope<SysUser>(false, "请输入验证码！", msgId);
		}
		String cacheEnable = Utils.getValue("redisCacheEnable", "0");
		/** 获取session中存放的手机短信验证码 */
		long currentTime = System.currentTimeMillis();
		long sendCodeTime = 0L;
		String yzm = "";
		int num = 0;
		//允许输入验证码次数
		String verify=SmsClient.app.getString("verifyCount");
	    int verifyCount=3;
		if(verify!=null){
			verifyCount=Integer.parseInt(verify);	
		}
		if ("1".equals(cacheEnable)) {
			Object codeObject = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_" + mobile);
			Object timeoutObject = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_TIMEOUT_" + mobile);
			if (codeObject != null) {
				yzm = codeObject.toString();
			}
			if (timeoutObject != null) {
				sendCodeTime = Long.parseLong(timeoutObject.toString());
			}
			Object count = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_Count" + mobile);
			logger.debug("缓存验证码次数获取："+count);
			if (count != null) {
				num = Integer.parseInt(count.toString());
			}
		} else {
			sendCodeTime = (long) request.getServletContext().getAttribute(
					"REGISTER_CODE_TIMEOUT_" + mobile);
			yzm = (String) request.getServletContext().getAttribute(
					"REGISTER_CODE_" + mobile);
			String count = (String) request.getServletContext().getAttribute(
					"REGISTER_CODE_Count" + mobile);
			logger.debug("非缓存验证码次数获取："+count);
			if (count != null) {
				num = Integer.parseInt(count);
			}
		}
		
		logger.debug("session数据：REGISTER_CODE_TIMEOUT=" + sendCodeTime
				+ "    REGISTER_CODE=" + yzm +" 验证码次数：" + num);
		if (StringUtils.isBlank(yzm) || sendCodeTime <= 0) {
			return new ResponseEnvelope<SysUser>(false, "验证码已失效，请重新获取！", msgId);
		}
		// 一分钟验证码失效
		logger.debug("------忘记密码：当前验证码：" + yzm + "     时间(currentTime："
				+ currentTime + ";sendCodeTime：" + sendCodeTime + ")："
				+ (currentTime - sendCodeTime));
		if ((currentTime - sendCodeTime) > SmsClient.VALID_TIME) {
			return new ResponseEnvelope<SysUser>(false, "验证码已超时，请重新获取！", msgId);
		}
		
		if (num < verifyCount) {
			if (!code.equals(yzm)) {
				num++;
				if ("1".equals(cacheEnable)) {
					CacheManager.getInstance().getCacher()
							.set("REGISTER_CODE_Count" + mobile, num + "", 0);
				} else {
					request.getServletContext().setAttribute(
							"REGISTER_CODE_Count" + mobile, num + "");
				}
				return new ResponseEnvelope<SysUser>(false, "验证码不对，请重新输入！",
						msgId);
			}
		} else {
			//success设置为true便于app关闭计时
			return new ResponseEnvelope<SysUser>(false, "验证码已失效，请重新获取！", msgId);
		}
		SysUser sysUser = new SysUser();
		sysUser.setMobile(mobile);
		List<SysUser> list = sysUserService.getList(sysUser);
		/** List<SysUser> list = SysUserCacher.getAllList(sysUser); */
		if (CustomerListUtils.isEmpty(list)) {
			return new ResponseEnvelope<SysUser>(false, "找不到该用户", msgId);
		} else {
			SysUser user = list.get(0);
			user.setPassword(newPassword);
			/** sysUserService.update(sysUser); */
			sysUserService.update(user);
			List<SysUser> list1 = null;
			if (StringUtils.equals("1", cacheEnable)) {
				list1 = SysUserCacher.getSysList();
			} else {
				list1 = sysUserService.getSysList();
			}
			request.getSession().setAttribute("sysUserList", list1);

			if ("1".equals(cacheEnable)) {
				CacheManager.getInstance().getCacher().remove("REGISTER_CODE_Count" + mobile);
				CacheManager.getInstance().getCacher().set("REGISTER_CODE_Count" + mobile, "4", 0);

			} else {
				request.getServletContext().setAttribute("REGISTER_CODE_Count" + mobile, "4");
			}
			
			return new ResponseEnvelope<SysUser>(true, msgId, true);
		}
	}
	
	/**
	 * 个人消费记录列表
	 * @return
	 */
	@RequestMapping(value = "/findExpenseRecordList")
	@ResponseBody
	public Object findExpenseRecordList(@RequestBody MobileRenderingModel model) {
		PayOrderSearch payOrderSearch = new PayOrderSearch();
		Integer userId = model.getUserId();
		if (StringUtils.isEmpty(model.getMsgId())) {
			return new ResponseEnvelope<PayOrder>(false, "参数msgId为空!", "");
		}
		if (userId == 0 || userId.intValue() == 0) {
			return new ResponseEnvelope<PayOrder>(false, "请登录系统!", model.getMsgId());
		}
		//只有支付成功的订单消费记录，排除序列号的订单，sql处理
		payOrderSearch.setUserId(userId);
		payOrderSearch.setStart(model.getStart());
		payOrderSearch.setLimit(model.getLimit());
		payOrderSearch.setPayState(PayState.SUCCESS);
		List<PayOrder> list = new ArrayList<PayOrder>();
		int total = 0;
		total = payOrderService.getCount(payOrderSearch);
		if (total > 0) {
			list = payOrderService.getPaginatedList(payOrderSearch);
		}
		SysUser user = sysUserService.get(userId);
		Double balanceAmount = user.getBalanceAmount();
		return new ResponseEnvelope<PayOrder>(true, "Success",model.getMsgId(),user, total, list);
//		return new ResponseEnvelope<PayOrder>(total, list, model.getMsgId());
	}
	
	
	
	
}




