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
@RequestMapping("/v1/user")
public class UserSessionController {

    private final static String CLASS_LOG_PREFIX = "[用户会话操作]:";
    private final static Logger logger = LoggerFactory.getLogger(UserSessionController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserSessionService userSessionService;
    
    private final static String OPERATING_MEDIATYPE = "2";


   @SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping(value = "/login", method = RequestMethod.POST)
   @ResponseBody
   public ResponseEnvelope<LoginUser> checkLogin(@RequestBody UserVo userVo, HttpServletRequest request, HttpServletResponse response) {
        String mediaType = null;
        if(null!=request.getHeader("MediaType")){
        	mediaType = request.getHeader("MediaType").toString();
        }else {
        	mediaType = OPERATING_MEDIATYPE;
        }
        if (null == userVo || StringUtils.isEmpty(userVo.getUserName()) || StringUtils.isEmpty(userVo.getUserPassword())) {
            logger.warn(CLASS_LOG_PREFIX + "用户登录->用户登录失败，必需参数为空:UserVo:{}", (null == userVo) ? "null" : userVo.toString());
            return new ResponseEnvelope(false, "用户登录失败，必需参数为空!");
        }

        //登录验证
        logger.info(CLASS_LOG_PREFIX + "用户登录-> Step 1: 验证用户-UserVo:{}", userVo.toString());
        SysUser sysUser = sysUserService.checkUserAccount(userVo.getUserName(), userVo.getUserPassword());

        logger.info(CLASS_LOG_PREFIX + "用户登录-> Step 1: 验证用户完成-SysUser:{}", (null == sysUser ? "null" : sysUser.toString()));

        if (null == sysUser) {
            logger.warn(CLASS_LOG_PREFIX + "用户登录->用户登录失败,用户名或密码错误!UserVo:{}", userVo.toString());
            return new ResponseEnvelope(false, "登录失败,用户名或密码错误!");
        }

        logger.info(CLASS_LOG_PREFIX + "用户登录-> Step 1: 用户登录成功:SysUser:{}", sysUser.toString());

        String sessionId = request.getRequestedSessionId();
        if(StringUtils.isEmpty(sessionId)){
           sessionId= UUID.randomUUID().toString().replace("-","");
        }

       String mobileflat = LoginUserCommonConstant.LOGIN_FROM_MOBILE;//移动端标识
       String blankFlat =LoginUserCommonConstant.LOGIN_FROM_YUYING;//运营网站标识
       String pcFlat =LoginUserCommonConstant.LOGIN_FROM_PC;//pc端标识

       /*LoginUser mobileUser = userSessionService.getUserFromCache(mobileflat+sysUser.getId()); 
       if(!StringUtils.isEmpty(mobileUser)){
           return  new ResponseEnvelope(false, "登录失败,您的账号已经在其他端登录!");
       }

       LoginUser pcUser = userSessionService.getUserFromCache(pcFlat+sysUser.getId());
       if(!StringUtils.isEmpty(pcUser)){
           return  new ResponseEnvelope(false, "登录失败,您的账号已经在其他端登录!");
       } */
       //用户信息转换
        LoginUser loginUser = this.getLoginUser(sysUser,sessionId,mediaType);
        return new ResponseEnvelope<>(true, "success", loginUser);
    }

    private LoginUser getLoginUser(SysUser sysuser,String sessionId,String mediaType){
        LoginUser loginUser = new LoginUser();
        String uuidKey=UUID.randomUUID().toString().replace("-","");
        Map<String , Object> payload=new HashMap<String, Object>();

        Date date=new Date();
        payload.put("uid", sysuser.getId());//用户ID
        payload.put("appKey", uuidKey);//用户ID
        payload.put("signflat",LoginUserCommonConstant.LOGIN_FROM_YUYING);
        payload.put("utype", sysuser.getUserType());
        payload.put("iat", date.getTime());//生成时间
        payload.put("uname", sysuser.getNickName());
        payload.put("mtype",mediaType);
        payload.put("ext",date.getTime()+1000*60*60*3);//过期时间3小时

        String token= Jwt.createToken(payload);

        loginUser.setAppKey(uuidKey);
        loginUser.setToken(token);
        loginUser.setId(sysuser.getId());
        loginUser.setUserType(sysuser.getUserType());
        loginUser.setMediaType(OPERATING_MEDIATYPE);
        loginUser.setBalanceAmount(sysuser.getBalanceAmount());
        loginUser.setConsumAmount(sysuser.getConsumAmount());

        String loginFlag= LoginUserCommonConstant.LOGIN_FROM_YUYING;

        //设置redis超时时间
        int failureTime = LoginUserCommonConstant.REDIS_EXPIRE_TIME;

        //系统标识加userid
        String userKey =loginFlag +loginUser.getId();

        //System.out.println(key);
        userSessionService.loginUserToCache(loginFlag+uuidKey,loginUser,failureTime);
        userSessionService.loginUserToCache(userKey, loginUser, failureTime);
       // LoginUser userFromCache = userSessionService.getUserFromCache(loginFlag + uuidKey);
        logger.info("将LoginUser存储到缓存中:"+loginUser+",redis的key:"+userKey);
        return loginUser;
    }


    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
//    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping("/logout")
    public ResponseEnvelope userLogout(HttpServletRequest request){

       String token = request.getHeader("Authorization");
        //解析token
        Map<String, Object> dataMap = Jwt.validToken(token);

        JSONObject dataObj = (JSONObject)dataMap.get("data");
        if(dataObj == null){
            logger.info("用戶退出操作解析token失敗:"+token);
            return  new ResponseEnvelope(false,"用户退出失败");
        }
        Long userId = (Long) dataObj.get("uid");
        String appKey = (String) dataObj.get("appKey");

        //退出用户
        if(userId==null || StringUtils.isEmpty(appKey)){
           logger.info("从token中获取信息失败");
           return new ResponseEnvelope(false,"退出登录失败");
        }
        String key =LoginUserCommonConstant.LOGIN_FROM_YUYING+userId;
        logger.info(CLASS_LOG_PREFIX + "用户退出登录->删除用户会话-SessionId:{}", request.getRequestedSessionId());
        boolean resultStatus = userSessionService.loginOut(key);
        boolean resultStatus2 = userSessionService.loginOut(LoginUserCommonConstant.LOGIN_FROM_YUYING+appKey);
        logger.info(CLASS_LOG_PREFIX + "用户退出登录->删除用户会话成功-ResultStatus:{}", resultStatus);


        return new ResponseEnvelope(resultStatus, resultStatus ? "退出登录成功!" : "退出登录失败!");
    }



}
