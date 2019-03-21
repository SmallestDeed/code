package com.sandu.user.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.RedisService;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.model.ResponseBo;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.HttpClient;
import com.sandu.common.util.HttpClientUtil;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.system.sms.httpclient.SmsClient;
import com.sandu.user.dao.UserDaoMapper;
import com.sandu.user.model.SmsVo;
import com.sandu.user.model.SysRole;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.SysUserRole;
import com.sandu.user.model.UserPO;
import com.sandu.user.model.UserSO;
import com.sandu.user.model.view.LoginUserVO;
import com.sandu.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements IUserService {

    private final static Gson gson = new Gson();
    private final static Integer VERIFY_COUNT = 3;

    private final static String CLASS_LOG_PREFIX = "[用户中心服务]";
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String BASE_ROLE_GROUP = "baseRoleGroup";
    private static final Integer TOC_PLATFORM_TYPE  = 1;
    
    @Autowired
    private UserDaoMapper userDaoMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private PayAccountService payAccountService;
    @Autowired
    private UserFinanceService userFinanceService;
    /**
     * User Login.
     */
    @Override
    public LoginUserVO login(LoginUserVO loginUser) {
        UserPO user = getUserByAccount(loginUser);
        if (user != null) {
            // validae success
            loginUser = createLoginUser(user);
        }
        return loginUser;
    }

    @Override
    public UserPO getUserByAccount(LoginUserVO loginUser) {
        return userDaoMapper.getUserByLoginUser(loginUser);
    }

    private LoginUserVO createLoginUser(UserPO user) {

        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("uid", user.getId());// 用户ID
        payload.put("appKey", appKey);// 用户ID
        payload.put("utype", user.getUserType());
        payload.put("iat", date.getTime());// 生成时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60);// 过期时间1小时
        String token = Jwt.createToken(payload);

        LoginUserVO loginUser = new LoginUserVO();
        loginUser.setAppKey(appKey);
        loginUser.setToken(token);
        loginUser.setId(user.getId());
        loginUser.setUserType(user.getUserType());
        return loginUser;
    }


    @Override
    public Boolean sendMessage(String mobile, String type) {
        String message = "";
        String code = Utils.generateRandomDigitString(6);
        logger.info(CLASS_LOG_PREFIX + "发送短信验证码---验证码类型:{}, 手机号:{}, 验证码:{}", type, mobile, code);
        try {
            //注册验证信息
            if ("register".equals(type)) {
                message = MessageFormat.format(SmsClient.app.getString("registerContext"), code,
                        SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
                message = URLEncoder.encode(message, "UTF-8");
                //修改密码验证信息
            } else if ("updateMobileByLogin".equals(type)) {
                message = MessageFormat.format(SmsClient.app.getString("updateMobileContext"), code,
                        SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
                message = URLEncoder.encode(message, "UTF-8");
            } else if ("updateMobileByForget".equals(type)) {
                message = MessageFormat.format(SmsClient.app.getString("updateMobileContext"), code,
                        SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
                message = URLEncoder.encode(message, "UTF-8");
            } else {
                return false;
            }

            long seqId = System.currentTimeMillis();
            String params = "phone=" + mobile + "&message=" + message + "&addserial=&seqid=" + seqId;
            String result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
            if ("1".equals(result)) {
                return false;
            }
            SmsVo smsVo = new SmsVo();
            smsVo.setType(type);
            smsVo.setCode(code);
            smsVo.setSendTime(seqId);
            smsVo.setVerifyCount(VERIFY_COUNT);
            redisService.addMap(RedisKeyConstans.SESSION_SMS_CODE, mobile, gson.toJson(smsVo));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ResponseBo updatePassword(String mobile, String newPassword, String code) {
        ResponseBo resBo = new ResponseBo();
        /** 获取redis中存放的手机短信验证码 */
        long currentTime = System.currentTimeMillis();
        long sendCodeTime = 0L;
        Integer verifyCount = 0;
        String yzm = "";
        String type = "";
        String map = redisService.getMap(RedisKeyConstans.SESSION_SMS_CODE, mobile);
        if (StringUtils.isBlank(map)) {
            resBo.setStatus(false);
            resBo.setMsg("请先获取验证码！");
            return resBo;
        }
        SmsVo smsVo = gson.fromJson(map, SmsVo.class);
        type = smsVo.getType();
        sendCodeTime = smsVo.getSendTime();
        yzm = smsVo.getCode();
        verifyCount = smsVo.getVerifyCount();
        if (!"updateMobileByLogin".equals(type) && !"updateMobileByForget".equals(type)) {
            resBo.setStatus(false);
            resBo.setMsg("验证码的类型不对，请重新获取！");
            return resBo;
        }
        if (verifyCount <= 0) {
            redisService.delMap(RedisKeyConstans.SESSION_SMS_CODE, mobile);
            resBo.setStatus(false);
            resBo.setMsg("验证码已失效，请重新获取！");
            return resBo;
        }
        if (!code.equals(yzm)) {
            verifyCount--;
            smsVo.setVerifyCount(verifyCount);
            redisService.addMap(RedisKeyConstans.SESSION_SMS_CODE, mobile, gson.toJson(smsVo));
            resBo.setStatus(false);
            resBo.setMsg("验证码不对，请重新输入！");
            return resBo;
        }
        if ((currentTime - sendCodeTime) > SmsClient.VALID_TIME) {
            resBo.setStatus(false);
            resBo.setMsg("验证码已超时，请重新获取！");
            return resBo;
        }
        SysUser sysUser = new SysUser();
        sysUser.setMobile(mobile);
        sysUser.setIsDeleted(0);
        sysUser.setPlatformType(1);
        List<SysUser> list = sysUserService.getList(sysUser);
        if (CustomerListUtils.isEmpty(list)) {
            resBo.setStatus(false);
            resBo.setMsg("找不到该用户");
            return resBo;
        } else {
            SysUser user = list.get(0);
            user.setPassword(newPassword);
            sysUserService.update(user);
            redisService.delMap(RedisKeyConstans.SESSION_SMS_CODE, mobile);
            resBo.setStatus(true);
            resBo.setMsg("修改密码成功");
            return resBo;
        }
    }

    @Override
    public ResponseBo addUser(String mobile, String password, String code,UserSO userSO,Integer platformId) {
        ResponseBo resBo = new ResponseBo();
        /** 获取redis中存放的手机短信验证码 */
        long currentTime = System.currentTimeMillis();
        long sendCodeTime = 0L;
        Integer verifyCount = 0;
        String yzm = "";
        String type = "";
        String map = redisService.getMap(RedisKeyConstans.SESSION_SMS_CODE, mobile);
        if (StringUtils.isBlank(map)) {
            resBo.setStatus(false);
            resBo.setMsg("请先获取验证码！");
            return resBo;
        }
        SmsVo smsVo = gson.fromJson(map, SmsVo.class);
        sendCodeTime = smsVo.getSendTime();
        yzm = smsVo.getCode();
        type = smsVo.getType();
        verifyCount = smsVo.getVerifyCount();
        if (!"register".equals(type)) {
            resBo.setStatus(false);
            resBo.setMsg("验证码的类型不对，请重新获取！");
            return resBo;
        }
        if (verifyCount <= 0) {
            redisService.delMap(RedisKeyConstans.SESSION_SMS_CODE, mobile);
            resBo.setStatus(false);
            resBo.setMsg("验证码已失效，请重新获取！");
            return resBo;
        }
        if (!code.equals(yzm)) {
            verifyCount--;
            smsVo.setVerifyCount(verifyCount);
            redisService.addMap(RedisKeyConstans.SESSION_SMS_CODE, mobile, gson.toJson(smsVo));
            resBo.setStatus(false);
            resBo.setMsg("验证码不对，请重新输入！");
            return resBo;
        }
        if ((currentTime - sendCodeTime) > SmsClient.VALID_TIME) {
            resBo.setStatus(false);
            resBo.setMsg("验证码已失效，请重新获取！");
            return resBo;
        }
        SysUser sysUser = new SysUser();
        sysUser.setMobile(mobile);
        sysUser.setIsDeleted(0);
        sysUser.setPlatformType(TOC_PLATFORM_TYPE);
        List<SysUser> list = sysUserService.getList(sysUser);
//        SysRole sysRole = sysRoleService.getRoleByName("普通用户");
//	    if (sysRole == null ) {
//	    	resBo.setMsg("数据有误！");
//	        return resBo;
//		}
//	    Integer roleId = sysRole.getId();
        if (userSO != null ) {
        	Integer registerId  = userSO.getUserId();
        	sysUser.setId(registerId);
        	return visitorRegister(password, resBo, sysUser, list,registerId/*,roleId*/);
		}else {
			return userRegister(password, resBo, sysUser, list,/*roleId,*/platformId);
		}
    }
    /**
     * 游客注册
     * @param password
     * @param resBo
     * @param sysUser
     * @param list
     * @return
     */
	private ResponseBo visitorRegister(String password, ResponseBo resBo, SysUser sysUser, List<SysUser> list,
                                       Integer registerId/*,Integer roleId*/) {
        if (CustomerListUtils.isEmpty(list)) {
            return getResponseBoV(password, resBo, sysUser, registerId);
        } /*else if(list.get(0).getPlatformType()==2){
            return getResponseBoV(password, resBo, sysUser, registerId);
        }*/else {
            resBo.setStatus(false);
            resBo.setMsg("该手机号已经被注册！");
            return resBo;
        }
	}

    private ResponseBo getResponseBoV(String password, ResponseBo resBo, SysUser sysUser, Integer registerId) {
        sysUser.setPassword(password);
        //默认昵称，用户名，创建者为手机号
        sysUser.setUserName(sysUser.getMobile());
        sysUser.setNickName(sysUser.getMobile());
        sysUser.setGmtCreate(sysUser.getGmtCreate());
        sysUser.setAppKey(sysUser.getAppKey());
        sysUser.setGroupId(0);
        sysUser.setUserType(8);
        sysUser.setJob("-1");
        // 查询用户已使用户型套数
        Integer userUsedHouseCount = userFinanceService.queryUserUsedHouseCount(registerId);
        //可用户型数为已用+注册赠送，（因为游客身份也记录已用户型数）
        List<SysDictionary> usableHouseNumbers = sysDictionaryService.findAllByType(SysDictionaryConstant.USER_REGISTER_GIFT_HOUSE_TYPE_COUNT);
        if (usableHouseNumbers != null && usableHouseNumbers.size() > 0) {
            sysUser.setUsableHouseNumber(usableHouseNumbers.get(0).getValue() + userUsedHouseCount);
        }

        sysUser.setCreator(sysUser.getMobile());
        //更新游客转正式用户信息
        int res = sysUserService.update(sysUser);

//		    int result = sysUserRoleService.updateByRegisterId(registerId,roleId);
        if (res > 0/* && result > 0*/) {
            resBo.setStatus(true);
            //注册成功新账号送300积分
            ResultMessage message = payAccountService.addGiveIntegralVisitor(registerId);
            if (!message.isSuccess()) {
                resBo.setStatus(false);
                resBo.setMsg(message.getMessage());
                return resBo;
            }
            //注册成功给角色组授权
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(registerId);
            sysUserRole.setCreator(sysUser.getMobile());
            sysUserRole.setGmtCreate(new Date());
            sysUserRole.setModifier(sysUser.getMobile());
            sysUserRole.setGmtModified(new Date());
            sysUserRole.setIsDeleted(0);
            sysUserRole.setBaseRoleGroup(BASE_ROLE_GROUP);
            sysUserRole.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            if (null != sysUserRole) {
                int i = sysUserRoleService.addUserRole(sysUserRole);
                if (i == 0) {
                    resBo.setStatus(false);
                    resBo.setMsg("注册失败:增加角色组权限失败");
                    return resBo;
                }
            }
            //注册成功给用户分配平台权限
//                ResultMessage messageUserJurisdiction =  sysUserRoleService.addUserJurisdiction(registerId);
//                if(!messageUserJurisdiction.isSuccess()) {
//                    resBo.setStatus(false);
//                    resBo.setMsg(messageUserJurisdiction.getMessage());
//                    return resBo;
//                }
            resBo.setMsg("注册成功！");
            return resBo;
        } else {
            logger.error("----------游客注册失败：res:" + res);
            resBo.setStatus(false);
            resBo.setMsg("注册失败！");
            return resBo;
        }
    }

    /**
	 * 用户注册
	 * @param password
	 * @param resBo
	 * @param sysUser
	 * @param list
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public ResponseBo userRegister(String password, ResponseBo resBo, SysUser sysUser, List<SysUser> list,
                                   /*Integer roleId,*/Integer platformId) {
		if (CustomerListUtils.isEmpty(list)) {
            return getResponseBo(password, resBo, sysUser);
		} else if(list.get(0).getPlatformType()==2){
            return getResponseBo(password, resBo, sysUser);
        }else {
			resBo.setStatus(false);
			resBo.setMsg("该手机号已经被注册！");
			return resBo;
		}
	}

    private ResponseBo getResponseBo(String password, ResponseBo resBo, SysUser sysUser) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUser.setPassword(password);
        //默认昵称，用户名，创建者为手机号
        sysUser.setUserName(sysUser.getMobile());
        sysUser.setNickName(sysUser.getMobile());
        sysUser.setGmtCreate(new Date());
        sysUser.setAppKey(Utils.generateRandomDigitString(32));
        sysUser.setGroupId(0);
        sysUser.setUserType(8);
        sysUser.setJob("-1");
        //设置用户类型,1为C端用户,2为B端用户
        sysUser.setPlatformType(1);
        //新账号送5套可渲染户型
        List<SysDictionary> usableHouseNumbers = sysDictionaryService.findAllByType(SysDictionaryConstant.USER_REGISTER_GIFT_HOUSE_TYPE_COUNT);
        if (usableHouseNumbers != null && usableHouseNumbers.size() >0) {
            sysUser.setUsableHouseNumber(usableHouseNumbers.get(0).getValue());
        }

        sysUser.setCreator(sysUser.getMobile());
        int userId = sysUserService.add(sysUser);
        sysUserRole.setUserId(userId);
//	       	sysUserRole.setRoleId(roleId);
        sysUserRole.setCreator(sysUser.getMobile());
        sysUserRole.setGmtCreate(new Date());
        sysUserRole.setModifier(sysUser.getMobile());
        sysUserRole.setGmtModified(new Date());
        sysUserRole.setIsDeleted(0);
        sysUserRole.setBaseRoleGroup(BASE_ROLE_GROUP);
        sysUserRole.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
//	       	int userRoleId = sysUserRoleService.add(sysUserRole);
        if (userId > 0 /*&& userRoleId > 0*/) {
            resBo.setStatus(true);
             //注册成功新账号送300积分
            ResultMessage message = payAccountService.addGiveIntegral(userId);
            if(!message.isSuccess()) {
                resBo.setStatus(false);
                resBo.setMsg(message.getMessage());
                return resBo;
            }
            //注册成功给角色组授权
            int i = sysUserRoleService.addUserRole(sysUserRole);
            if(i==0) {
                resBo.setStatus(false);
                resBo.setMsg("注册失败:增加角色组权限失败");
                return resBo;
            }
/*				//注册成功给用户分配平台权限
            ResultMessage messageUserJurisdiction =  sysUserRoleService.addUserJurisdiction(userId);
            if(!messageUserJurisdiction.isSuccess()) {
                resBo.setStatus(false);
                resBo.setMsg(messageUserJurisdiction.getMessage());
                return resBo;
            }
            */
            resBo.setMsg("注册成功!");
            return resBo;
        } else {
            resBo.setStatus(false);
            resBo.setMsg("注册失败！");
            return resBo;
        }
    }
}
