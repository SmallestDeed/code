package com.sandu.web.user.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sandu.common.constant.VisitorInfoConstant;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.system.service.BasePlatformService;
import com.sandu.user.model.*;
import com.sandu.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.cache.service.RedisService;
import com.sandu.common.LoginContext;
import com.sandu.common.constant.SystemCommonConstant;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.model.ResponseBo;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.Lists;
import com.sandu.platform.BasePlatform;
import com.sandu.user.model.view.UserInfoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;


/**
 * @version V1.0
 * @Title: SysUserController.java
 * @Package com.sandu.system.controller
 * @Description:系统-用户Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
@Api(tags = "User", value = "用户")
@RestController
@RequestMapping("/v1/tocmobile/user/sysuser")
public class UserInfoController {

    @Value("${app.sso.url}")
    private String appSsoUrl;
    public void setAppSsoUrl(String appSsoUrl) {
        this.appSsoUrl = appSsoUrl;
    }

    private final static Gson gson = new Gson();
    private final static Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    private final static String CLASS_LOG_PREFIX = "[用户中心服务]";

    private final static String MOBILE2C_PLATFORM_CODE = "mobile2c";
    private final static String BASE_PLATFORM_INFO = "basePlatformInfo";
    private static final String BASE_ROLE_GROUP = "baseRoleGroup";
    private static final String MOBILE_VISITOR_ROLE_CODE = "mobile2cvisitor";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private PayAccountService payAccountService;


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     *
     * 手机验证码接口
     *
     * @param mobile 手机号码
     * @param type register 表示注册 updateMobileByLogin 表示必须登录才可以修改密码  updateMobileByForget 表示不必登录也可以修改密码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "手机验证码接口", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query", dataType = "String", required = true)
    })
    @GetMapping(value = "/app/sms")
    public ResponseEnvelope sms(String mobile, String type, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (StringUtils.isEmpty(mobile)) {
            logger.warn(CLASS_LOG_PREFIX + "手机验证码服务：mobile is null");
            return new ResponseEnvelope(false, "手机号不能为空");
        }
        if (!isMobile(mobile)) {
            logger.warn(CLASS_LOG_PREFIX + "手机验证码服务：mobile is invalid");
            return new ResponseEnvelope(false, "请输入正确的手机号");
        }
        if (StringUtils.isNotEmpty(type)) {
            SysUser sysUser = new SysUser();
            sysUser.setMobile(mobile);
            sysUser.setIsDeleted(0);
            sysUser.setPlatformType(1);
            List<SysUser> list = sysUserService.getList(sysUser);
            logger.info(CLASS_LOG_PREFIX + "通过手机号查询用户信息：{}", gson.toJson(list));
            if ("register".equals(type)) {
                if (!list.isEmpty()) {
                    logger.info(CLASS_LOG_PREFIX + "通过手机号查询用户信息为空：when type is 'register', this list is null ");
                    return new ResponseEnvelope(false, "该用户已被注册，请重新输入手机号！");
                }
            } else if ("updateMobileByLogin".equals(type)) {
            	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
                if(null == loginUser){
                    return new ResponseEnvelope(false, SystemCommonConstant.PLEASE_LOGIN);
                }
                sysUser = sysUserService.get(loginUser.getId());
                if (!sysUser.getMobile().equals(mobile)) {
                    logger.info(CLASS_LOG_PREFIX + "输入的号码跟账号不一致，不能获取验证码：when type is 'updateMobileByLogin', the mobile is different from then account mobile ");
                    return new ResponseEnvelope(false, "输入的号码跟账号不一致，不能获取验证码");
                }
            } else if ("updateMobileByForget".equals(type)){
                //用户不必登录也可以修改密码
                if (Lists.isEmpty(list)) {

                    logger.info(CLASS_LOG_PREFIX + "通过手机号查询用户信息为空：when type is 'updateMobileByForget', this list is null ");
                    return new ResponseEnvelope(false, "找不到该用户!");
                }
            } else {
                logger.info(CLASS_LOG_PREFIX + "发送短信失败：type is error");
                return new ResponseEnvelope(true, "发送短信失败：type is error");
            }
        }else {
            logger.info(CLASS_LOG_PREFIX + "发送短信失败：type is null");
            return new ResponseEnvelope(true, "发送短信失败：type is null");
        }
        Boolean status = iUserService.sendMessage(mobile, type);
        logger.info(CLASS_LOG_PREFIX + "调用发送短信服务返回的状态：{}", status);
        if (!status) {
            logger.info(CLASS_LOG_PREFIX + "调用发送短信服务返回的状态：status is false");
            return new ResponseEnvelope(true, "发送短信失败");
        }

        return new ResponseEnvelope(true, "发送短信成功");
    }

    /**
     * 用户信息显示
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "用户信息显示", response = ResponseEnvelope.class)
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public ResponseEnvelope viewCertification(HttpServletRequest request) {
	     LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
	        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
	        if(loginUser==null){
	            return new ResponseEnvelope(false, "请登录!");
	        }

        SysUser sysUser = null;
        UserVo userVo = null;
        try {
            sysUser = sysUserService.get(loginUser.getId());
            logger.info(CLASS_LOG_PREFIX + "查询用户服务：viewCertification:{}", null == sysUser ? null : sysUser.toString());
            userVo = UserObject.parseToUserVOFromSysUser(sysUser);
        } catch (Exception e) {
            logger.info(CLASS_LOG_PREFIX + "查询用户服务：viewCertification:{},Exception:{}.",
                    null == sysUser ? null : sysUser.toString(), e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEnvelope(true, "success", userVo);
    }

    /**
     * 用户信息修改
     */
    @SuppressWarnings("rawtypes")

    @ApiOperation(value = "用户信息修改", response = ResponseEnvelope.class)
    @PostMapping(value = "/update")
    public ResponseEnvelope update(@ModelAttribute UserVo userVo, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        SysUser sysUser = null;
	     LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
	        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
	        if(loginUser==null){
	            return new ResponseEnvelope(false, "请登录!");
	        }

        try {
            sysUser = sysUserService.get(loginUser.getId());
            logger.info(CLASS_LOG_PREFIX + "修改用户信息服务：update:{}", null == sysUser ? null : sysUser.toString());
            sysUser = UserObject.parseToSysUserFromUserVo(userVo, sysUser);
            sysUserService.update(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(CLASS_LOG_PREFIX + "修改用户信息服务：update:{},Exception:{}.",
                    null == sysUser ? null : sysUser.toString(), e.getMessage());
            return new ResponseEnvelope(false, "修改失败！");
        }

        return new ResponseEnvelope(true, "修改成功");
    }

    /**
     * 用户找回密码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "用户找回密码", response = ResponseEnvelope.class)
    @PutMapping(value = "/app/findpassword")
    public ResponseEnvelope findPassword(@RequestBody UserInfoDto userInfoDto, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
    	if(userInfoDto==null) {
    		  logger.warn(CLASS_LOG_PREFIX + "userInfoDto is null");
    		  return new ResponseEnvelope(false, "请输入相关信息");
    	}
    	
        if (StringUtils.isEmpty(userInfoDto.getNewPassword())) {
            logger.warn(CLASS_LOG_PREFIX + "用户找回密码:newPassword is null");
            return new ResponseEnvelope(false, "新密码不能为空");
        }
        if (StringUtils.isEmpty(userInfoDto.getMobile())) {
            logger.warn(CLASS_LOG_PREFIX + "用户找回密码:mobile is null");
            return new ResponseEnvelope(false, "手机号不能为空");
        }
        if (!isMobile(userInfoDto.getMobile())) {
            logger.warn(CLASS_LOG_PREFIX + "用户找回密码:mobile is invalid");
            return new ResponseEnvelope(false, "请输入正确的手机号！");
        }
        if (StringUtils.isEmpty(userInfoDto.getCode())) {
            logger.warn(CLASS_LOG_PREFIX + "用户找回密码:code is null");
            return new ResponseEnvelope(false, "请输入验证码！");
        }
        ResponseBo resBo = iUserService.updatePassword(userInfoDto.getMobile(), userInfoDto.getNewPassword(), userInfoDto.getCode());
        return new ResponseEnvelope(null == resBo ? false : resBo.getStatus(), null == resBo ? null : resBo.getMsg());

    }

    /**
     * 用户注册
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "用户注册", response = ResponseEnvelope.class)
    @PostMapping(value = "/register")
    public ResponseEnvelope register(@RequestBody UserInfoDto userInfoDto
            , HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	if(userInfoDto==null) {
  		  logger.warn(CLASS_LOG_PREFIX + "userInfoDto is null");
  		  return new ResponseEnvelope(false, "请输入相关信息");
    	}
    	
        if (StringUtils.isEmpty(userInfoDto.getMobile())) {
            logger.warn(CLASS_LOG_PREFIX + "用户注册:mobile is null");
            return new ResponseEnvelope(false, "手机号不能为空");
        }
        if (!isMobile(userInfoDto.getMobile())) {
            logger.warn(CLASS_LOG_PREFIX + "用户注册:mobile is invalid");
            return new ResponseEnvelope(false, "请输入正确的手机号！");
        }
        if (StringUtils.isEmpty(userInfoDto.getCode())) {
            logger.warn(CLASS_LOG_PREFIX + "用户注册:code is null");
            return new ResponseEnvelope(false, "请输入验证码！");
        }
        if (StringUtils.isEmpty(userInfoDto.getPassword())) {
            logger.warn(CLASS_LOG_PREFIX + "用户注册:password is null");
            return new ResponseEnvelope(false, "密码不能为空");
        }
        String basePlatformInfo = redisService.getMap(BASE_PLATFORM_INFO, MOBILE2C_PLATFORM_CODE);
    	if(StringUtils.isEmpty(basePlatformInfo)) {
    		return new ResponseEnvelope(false, "未获取到平台信息");
    	}
    	BasePlatform basePlatform =  gson.fromJson(basePlatformInfo ,
				BasePlatform.class);
          ResponseBo resBo = iUserService.addUser(userInfoDto.getMobile(), userInfoDto.getPassword(), userInfoDto.getCode(),null,basePlatform.getId());
        return new ResponseEnvelope(null == resBo ? false : resBo.getStatus(), null == resBo ? null : resBo.getMsg());
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "游客自动登录", response = ResponseEnvelope.class)
    @PostMapping(value = "/visitorAutoLogin")
    public ResponseEnvelope visitorAutoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser sysUser = new SysUser();
        UserLoginVO userLoginVO = new UserLoginVO();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            //没有token，先创建游客账号
            sysUser.setMobile(VisitorInfoConstant.VISITOR_MOBILE);
            sysUser.setPassword(VisitorInfoConstant.VISITOR_PASSWORD);
            sysUser.setUserName("游客"+ Utils.generateRandomDigitString(8));
            sysUser.setNickName(sysUser.getUserName());
            sysUser.setGmtCreate(new Date());
            sysUser.setAppKey(Utils.generateRandomDigitString(32));
            sysUser.setGroupId(VisitorInfoConstant.VISITOR_GROUP_ID);
            sysUser.setUserType(VisitorInfoConstant.VISITOR_USER_TYPE);
            sysUser.setJob(VisitorInfoConstant.VISITOR_JOB);
            sysUser.setVisitorsRenderTimes(VisitorInfoConstant.VISITOR_RENDER_TIMES);
            sysUser.setBalanceAmount(VisitorInfoConstant.VISITOR_BALANCE_AMOUNT);
            sysUser.setUsableHouseNumber(VisitorInfoConstant.VISITOR_USEABLE_HOUSE_NUMBER);
            //设置用户类型,1为C端用户,2为B端用户
            sysUser.setPlatformType(1);
            int userId = sysUserService.add(sysUser);
            if (userId <= 0) {
                return new ResponseEnvelope(false, "游客创建失败！");
            }
            sysUser.setId(userId);
            //积分表插入积分记录
            payAccountService.addGiveIntegralLoginVisitor(userId);

            //添加用户角色
            SysUserRole sysUserRole = new SysUserRole();
            SysRole sysRole = new SysRole();
//            sysRole.setName("游客");
            sysRole.setCode(MOBILE_VISITOR_ROLE_CODE);
            BasePlatform basePlatform = basePlatformService.getByPlatformCode(MOBILE2C_PLATFORM_CODE);
            if (basePlatform == null) {
                return new ResponseEnvelope(false,"平台编码查不到平台！");
            }
            sysRole.setPlatformId(basePlatform.getId());
            sysRole = sysRoleService.getRoleByCodeAndPlatformId(sysRole);
            if (sysRole == null) {
                return new ResponseEnvelope<>(false, "数据有误！");
            }
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(sysRole.getId());
            sysUserRole.setCreator("sanDuAdmin");
            sysUserRole.setGmtCreate(new Date());
            sysUserRole.setModifier("sanDuAdmin");
            sysUserRole.setGmtModified(new Date());
            sysUserRole.setIsDeleted(0);
            sysUserRole.setBaseRoleGroup(BASE_ROLE_GROUP);
            sysUserRole.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            int userRoleId = sysUserRoleService.add(sysUserRole);
            if (userRoleId < 0) {
                return new ResponseEnvelope<>(false, "游客角色身份创建失败！");
            }
/*            //开通平台权限
            ResultMessage messageUserJurisdiction =  sysUserRoleService.addUserJurisdiction(userId);
            if(!messageUserJurisdiction.isSuccess()) {
                return new ResponseEnvelope(messageUserJurisdiction.isSuccess(),messageUserJurisdiction.getMessage());
            }*/

            logger.info("创建游客+角色成功！");
        } else {
            //有token就不用创建游客账号
//            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
//            if (loginUser != null) {
//                //token未失效，拿到用户信息直接返回
//                userLoginVO = this.copyFromLoginUser(loginUser);
//                return new ResponseEnvelope(true,"token有效",userLoginVO);
//            } else {
                //token失效的话，解析token判断是否是游客,不是游客返回什么？？？
                Map<String, Object> map = Jwt.validToken(token);
//                if (!VALID.equals(map.get("state"))) {
                    JSONObject dataObj  = (JSONObject)map.get("data");
                    Long userType = (Long) dataObj.get("utype");
                    if (userType != 9) {
                        //不是游客直接返回
                        userLoginVO.setUserType(userType.intValue());
                        return new ResponseEnvelope(true,"token为正式用户！",userLoginVO);
                    } else {
                        //是游客，获取到游客用户名，准备登陆
                        String userName = (String) dataObj.get("uname");
                        sysUser.setUserName(userName);
                        sysUser.setPassword(VisitorInfoConstant.VISITOR_PASSWORD);
                    }
//                }
//            }
        }

        if (null == sysUser.getUserName() || null == sysUser.getPassword()) {
            return new ResponseEnvelope(false,"游客用户名或密码为空，请联系管理员");
        }
        //调sso登陆
        String url = appSsoUrl + "v1/user/login?account="+sysUser.getUserName()+"&password="+sysUser.getPassword();
        logger.info("-----------游客自动登录 visitorAutoLogin 登陆url："+url);
        Map<String, String> params = new HashMap<>();
        params.put("account",sysUser.getUserName());
        logger.info("------游客自动登录 visitorAutoLogin account："+sysUser.getUserName());
        params.put("password",sysUser.getPassword());
        logger.info("------游客自动登录 visitorAutoLogin password："+sysUser.getPassword());
        params.put("Platform-Code",MOBILE2C_PLATFORM_CODE);
        try {
            String result = Utils.doPostMethod(url, params);
            return gson.fromJson(result, ResponseEnvelope.class);
        } catch (Exception e) {
            logger.error("-----------游客自动登录 visitorAutoLogin Exception"+e);
            return new ResponseEnvelope(false,"自动登陆失败！");
        }

    }
    
    @SuppressWarnings("rawtypes")
   	@ApiOperation(value = "游客注册", response = ResponseEnvelope.class)
    @PostMapping(value = "/visitorRegister")
    public ResponseEnvelope visitorRegister(@RequestBody UserInfoDto userInfoDto,HttpServletRequest request, HttpServletResponse response) throws Exception {
       	
    	UserSO userSo=null;
		String token = request.getHeader("Authorization");
		boolean flat = userSessionService.checkTokenTimeOut(token);
		if(flat){
			Map<String, Object> dataMap = Jwt.validToken(token);
			net.minidev.json.JSONObject dataObj = (net.minidev.json.JSONObject)dataMap.get("data");
            Long userType = (Long) dataObj.get("utype");
            if (userType != 9) {
                return new ResponseEnvelope(false,"token有误，非游客！",userType);
            }
//			Long uid = (Long) dataObj.get("uid");
//			LoginUser loginUser=userSessionService.getUserFromCache(LoginUserCommonConstant.LOGIN_FROM_TOCMOBILE+uid);
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
            if (null == loginUser) {
                return new ResponseEnvelope(false,"请刷新重试！");
            }
			userSo=new UserSO();
			userSo.setUserId(loginUser.getId());
		}
    	if(userInfoDto==null) {
    		  logger.warn(CLASS_LOG_PREFIX + "userInfoDto is null");
    		  return new ResponseEnvelope(false, "请输入相关信息");
      	}
      	
          if (StringUtils.isEmpty(userInfoDto.getMobile())) {
              logger.warn(CLASS_LOG_PREFIX + "用户注册:mobile is null");
              return new ResponseEnvelope(false, "手机号不能为空");
          }
          if (!isMobile(userInfoDto.getMobile())) {
              logger.warn(CLASS_LOG_PREFIX + "用户注册:mobile is invalid");
              return new ResponseEnvelope(false, "请输入正确的手机号！");
          }
          if (StringUtils.isEmpty(userInfoDto.getCode())) {
              logger.warn(CLASS_LOG_PREFIX + "用户注册:code is null");
              return new ResponseEnvelope(false, "请输入验证码！");
          }
          if (StringUtils.isEmpty(userInfoDto.getPassword())) {
              logger.warn(CLASS_LOG_PREFIX + "用户注册:password is null");
              return new ResponseEnvelope(false, "密码不能为空");
          }
          String basePlatformInfo = redisService.getMap(BASE_PLATFORM_INFO, MOBILE2C_PLATFORM_CODE);
    	if(StringUtils.isEmpty(basePlatformInfo)) {
    		return new ResponseEnvelope(false, "未获取到平台信息");
    	}
    	BasePlatform basePlatform =  gson.fromJson(basePlatformInfo ,
				BasePlatform.class);
          ResponseBo resBo = iUserService.addUser(userInfoDto.getMobile(), userInfoDto.getPassword(), userInfoDto.getCode(),userSo,basePlatform.getId());
          return new ResponseEnvelope(null == resBo ? false : resBo.getStatus(), null == resBo ? null : resBo.getMsg());
       }

    public UserLoginVO copyFromLoginUser(LoginUser loginUser) {
        if (loginUser == null) {
            return null;
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setId(loginUser.getId().longValue());
        userLoginVO.setAppKey(loginUser.getAppKey());
        userLoginVO.setBalanceAmount(loginUser.getBalanceAmount());
        userLoginVO.setConsumAmount(loginUser.getConsumAmount());
//        userLoginVO.setMediaType(Integer.parseInt(loginUser.getMediaType()));
        userLoginVO.setSiteName(loginUser.getSiteName());
        userLoginVO.setToken(loginUser.getToken());
        userLoginVO.setUserKey(loginUser.getUserKey());
        userLoginVO.setUserType(loginUser.getUserType());
        userLoginVO.setUserName(loginUser.getName());
        return userLoginVO;
    }
}
