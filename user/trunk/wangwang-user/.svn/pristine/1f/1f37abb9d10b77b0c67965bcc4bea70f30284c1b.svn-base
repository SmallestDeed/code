package com.sandu.service.user.impl.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

import com.sandu.api.user.model.bo.UserMenuBO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.service.PayAccountService;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.model.BaseCompanyCustomLoginConfig;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BaseCompanyCustomLoginConfigService;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.redis.RedisService;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.input.UserQuery;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.model.UserJurisdiction;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.PcUserLoginVO;
import com.sandu.api.user.service.SysRoleGroupService;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.UserJurisdictionService;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.api.user.service.manage.SysUserEquipmentService;
import com.sandu.common.AESUtil2;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.exception.BizException;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.sign.MD5SignTool;
import com.sandu.config.ResourceConfig;
import com.sandu.constant.EquipmentConstant;
import com.sandu.service.user.dao.SysUserDao;


@Service("customCompanyUserLoginService")
public class CustomCompanyUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {
    private Logger logger = LoggerFactory.getLogger(CustomCompanyUserLoginServiceImpl.class);
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserEquipmentService sysUserEquipmentService;
    
    @Autowired
    private BaseCompanyService baseCompanyService;
    
    @Autowired
    private BaseCompanyCustomLoginConfigService baseCompanyCustomLoginConfigService;
    
    @Autowired
    private PayAccountService payAccountService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;
    
    @Autowired
    private SysRoleGroupService sysRoleGroupService;
    
    @Autowired
    private SysRoleService sysRoleService;
    
    @Autowired
    private BasePlatformService basePlatformService;
    
    @Autowired
    private UserJurisdictionService userJurisdictionService;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

    public UserInfoBO checkUserAccount(UserLogin userLogin) {
        //根据companyCode获取对应企业id
        BaseCompany company = baseCompanyService.getCompanyByCode(userLogin.getCompanyCode());
        if(company==null) {
        	throw new BizException("未找到相应定制企业!");
        }
        //获取当前定制企业登录配置信息
    	BaseCompanyCustomLoginConfig config = baseCompanyCustomLoginConfigService.getCustomLoginConfig(company.getId());
    	if(config==null) {
    		throw new BizException("请先完善定制企业配置信息.");
    	}
        //验证签名
        validateSign(userLogin,config);
        
        List<UserInfoBO> list = null;
        //如果是三度后台创建的账号,则直接登录
        if(userLogin.getAccount().startsWith(company.getCompanyCode())) {
        	//验证此企业下的该账号是否存在,
            list = sysUserDao.selectUserByNickNameAndCompanyId(userLogin.getAccount(),company.getId());
        }//否则表示定制企业注册的,需要拼接companyCode+account
        else {
        	list = sysUserDao.selectUserByNickNameAndCompanyId(company.getCompanyCode()+userLogin.getAccount(),company.getId());
        }
        
        
        //如果存在,则登录成功
        if (list != null && list.size() > 0) {
            return list.get(0);
        }//如果不存在,则需要为这个企业新增一个账号,并根据定制企业登录配置信息,授予角色,角色组,平台权限等
		else {
			//新增用户
			SysUser user = this.saveCustomCompanyUser(config,company.getCompanyCode(),userLogin.getAccount());
			BasePlatform basePlatform = basePlatformService.queryByPlatformCode(userLogin.getPlatformCode());
			//新增账户
			this.saveCustomCompanyUserAccount(basePlatform.getId(),user);
			List<UserInfoBO> list2 = sysUserDao.selectUserByNickNameAndCompanyId(company.getCompanyCode()+userLogin.getAccount(),company.getId());
			return list2.get(0);
		}
    }
    
    private void saveCustomCompanyUserAccount(Long platformId,SysUser user) {
    	PayAccount payAccount = buildPayAccount(platformId,user);
    	payAccountService.insertPayAccount(payAccount);
	}
    
    private PayAccount buildPayAccount(Long platformId,SysUser user) {
        PayAccount payAccount = new PayAccount();
        Date date = new Date();
        payAccount.setUserId(user.getId());
        payAccount.setPlatformId(platformId);
        payAccount.setCreator(user.getMobile());
        payAccount.setGmtCreate(date);
        payAccount.setModifier(user.getMobile());
        payAccount.setGmtModified(date);
        payAccount.setIsDeleted(0);
        payAccount.setBalanceAmount(0.0);
        payAccount.setConsumeAmount(0.0);
        payAccount.setPlatformBusinessType("2b");
        return payAccount;
    }

	private void validateSign(UserLogin userLogin,BaseCompanyCustomLoginConfig config) {
    	//间隔天数
    	long day = (System.currentTimeMillis()-new Date(userLogin.getTimestamp()).getTime())/ (1000*3600*24);
    	//今天之前的无效 
    	if(day!=0) {
    		throw new BizException("登录已过期,请重新登录");
    	}
    	
    	//使用密钥生成md5签名
    	String sign = MD5SignTool.MD5Encode("account="+userLogin.getAccount()
    			+ "&companyCode="+userLogin.getCompanyCode()
    			+ "&timestamp="+userLogin.getTimestamp()
    			+ "&key="+config.getEncryptKey()).toUpperCase();
    	//验证用户签名是否正确
    	if(!sign.equals(userLogin.getSign())){
    		throw new BizException("签名错误");
    	}
    	
    }

    public static void main(String[] args) throws ParseException {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    	Date date = sdf.parse("20181106230255");
    	System.out.println(System.currentTimeMillis());
    	String sign = MD5SignTool.MD5Encode("account=ccc&companyCode=C0000352&timestamp=1546415897350&key=0985e20c85524182ba0b31d5223dfaae").toUpperCase();
    	System.out.println(sign);
    	
    	//System.out.println(sdf.format(new Date(System.currentTimeMillis())));
    	
    	//long d = ((date.getTime()-System.currentTimeMillis())/ (1000*3600*24));
    	long d = ((System.currentTimeMillis()-date.getTime())/ (1000*3600*24));
    	System.out.println(d);
	}
    /**
     * 检查平台权限
     *
     * @param userId
     * @param platformId
     */
    @Override
    public void checkPlatformJurisdiction(Long userId, Long platformId) {
    	//检查是否有pc2b权限
    	BasePlatform b = basePlatformService.queryByPlatformCode(BasePlatform.PLATFORM_CODE_PC_2B);
    	super.checkPlatformJurisdiction(userId, b.getId());
    }


	private SysUser saveCustomCompanyUser(BaseCompanyCustomLoginConfig config, String companyCode, String account) {
		Date now = new Date();
		SysUser newUser = new SysUser();
		// 设置基本信息
		newUser.setNickName(companyCode + account);
		newUser.setUserName(account);
		newUser.setCompanyId(config.getCompanyId());
		newUser.setBusinessAdministrationId(config.getCompanyId());
		newUser.setUserType(SysUser.USER_TYPE_DESIGNER);
		newUser.setCreator("Login");
		newUser.setGmtCreate(new Date());
		newUser.setIsDeleted(0);
		newUser.setUuid(UUID.randomUUID().toString().replace("-", ""));
		newUser.setPlatformType(SysUser.PLATFORM_TYPE_B);
		newUser.setUseType(1); // 正式用户
		newUser.setGroupId(0);
		newUser.setServicesFlag(0);
		newUser.setPasswordUpdateFlag(0);
		sysUserDao.insertUser(newUser);
		// 设置平台权限
		if (StringUtils.isNotBlank(config.getInitPlatformIds())) {
			String[] ids = config.getInitPlatformIds().split(",");
			List<UserJurisdiction> uJlist = new ArrayList<UserJurisdiction>();
			for (String id : ids) {
				UserJurisdiction uj = new UserJurisdiction();
				uj.setUserId(newUser.getId());
				uj.setPlatformId(Long.valueOf(id));
				uj.setJurisdictionStatus(1);
				uj.setCreator("Login");
				uj.setGmtCreate(now);
				uj.setModifier("Login");
				uj.setGmtModified(now);
				uj.setIsDeleted(0);
				uJlist.add(uj);
			}
			userJurisdictionService.batchUserJurisdictions(uJlist);
		}

		// 设置角色组
		if (StringUtils.isNotBlank(config.getInitRoleGroupIds())) {
			String[] ids = config.getInitRoleGroupIds().split(",");
			Set<SysUserRoleGroup> urgs = new HashSet<SysUserRoleGroup>();
			for (String id : ids) {
				SysUserRoleGroup urg = new SysUserRoleGroup();
				urg.setUserId(newUser.getId());
				urg.setRoleGroupId(Long.valueOf(id));
				urg.setCreator("Login");
				urg.setGmtCreate(now);
				urg.setModifier("Login");
				urg.setGmtModified(now);
				urg.setIsDeleted(0);
				urgs.add(urg);
			}
			sysRoleGroupService.batchSaveUserRoleGroupSet(urgs);
		}

		// 设置角色
		if (StringUtils.isNotBlank(config.getInitRoleIds())) {
			String[] ids = config.getInitRoleIds().split(",");
			List<SysUserRole> urList = new ArrayList<SysUserRole>();
			for (String id : ids) {
				SysUserRole ur = new SysUserRole();
				ur.setUserId(newUser.getId());
				ur.setRoleId(Long.valueOf(id));
				ur.setCreator("Login");
				ur.setGmtCreate(now);
				ur.setModifier("Login");
				ur.setGmtModified(now);
				ur.setIsDeleted(0);
				urList.add(ur);
			}
			sysRoleService.batchUserRole(urList);
		}
		return newUser;
	}
    


    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
    	String mediaType = "3";
    	if(userInfoBO.getMediaType()!=null) {
    		mediaType = userInfoBO.getMediaType().toString();
		}
        logger.info("pc端定制企业设置用户token");
        String userKey = UUID.randomUUID().toString();
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat", UserConstant.PC_2B_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());
        payload.put("uname", userInfoBO.getNickName());
        payload.put("mtype", mediaType);
        payload.put("uphone", userInfoBO.getMobile());
        payload.put("appKey", userKey);
        payload.put("ukey", userKey);
        payload.put("utype", userInfoBO.getUserType());
        payload.put("iat", date.getTime());
        payload.put("ext", date.getTime() + UserConstant.PC_2B_JWT_TOKEN_TIMEOUT * 1000);//jwtToken过期时间
        payload.put("sessionTimeout", UserConstant.PC_2B_USER_SESSION_TIMEOUT);
        String token = Jwt.createToken(payload);
        userInfoBO.setUserKey(userKey);
        userInfoBO.setToken(token);
        userInfoBO.setCryptKey(this.encryptKey(token));
    }

    private String encryptKey(String token) {
        //截取token的前八位
        if (token.length() < 8) {
            //字符长度不够8位则在后面补0
            token = token + String.format("%1$0" + (8 - token.length()) + "d", 0);
        } else {
            token = token.substring(0, 8);
        }
        return AESUtil2.encryptFile(ResourceConfig.AES_RESOURCES_ENCRYPT_KEY, token);
    }

    @Override
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
    	PcUserLoginVO vo = new PcUserLoginVO();
        vo.setId(userInfoBO.getId());
        vo.setUserType(userInfoBO.getUserType());
        vo.setLoginId(userInfoBO.getSysCode());
        vo.setLoginName(userInfoBO.getNickName());
        vo.setLoginPhone(userInfoBO.getMobile() == null ? "" : userInfoBO.getMobile());
        vo.setLoginEmail(userInfoBO.getEmail());
        vo.setName(userInfoBO.getUserName());
        vo.setAppKey(userInfoBO.getAppKey());
        vo.setToken(userInfoBO.getToken());
        vo.setDeviceId(userInfoBO.getMediaType() == null ? "" : userInfoBO.getMediaType().toString());
        vo.setMsgId(userInfoBO.getMsgId());
        vo.setGroupId(userInfoBO.getGroupId());
        vo.setSex(userInfoBO.getSex());
        vo.setBrandIds(userInfoBO.getBrandIds());
        vo.setMediaType(userInfoBO.getMediaType() == null ? "3" : userInfoBO.getMediaType().toString());
        vo.setUserKey(userInfoBO.getUserKey());
        vo.setBusinessAdministrationId(userInfoBO.getBusinessAdministrationId());
        vo.setPermissions(userInfoBO.getPermissions());
        vo.setQueryFields(userInfoBO.getQueryFields());
        vo.setRoleCodeList(userInfoBO.getRoleCodeList());
        vo.setMaturityFlag(userInfoBO.isMaturityFlag());
        vo.setServiceType(userInfoBO.getServiceType());
        vo.setTipsFlag(userInfoBO.isTipsFlag());
        vo.setRemainingDays(userInfoBO.getRemainingDays());
        vo.setLeftTime(userInfoBO.getLeftTime());
        vo.setOldServiceFlag(userInfoBO.getOldServiceFlag());
        vo.setUserName(userInfoBO.getUserName());
        //vo.setCurrentCompany(userInfoBO.getCurrentCompany());
        vo.setCurrentCompanyName(userInfoBO.getCurrentLoginCompanyName());
        vo.setServiceName(userInfoBO.getServiceName());
        vo.setIsExamine(userInfoBO.getIsExamine());
        vo.setSessionId(userInfoBO.getUuid());
        String userInfoJson = GsonUtil.toJson(vo);

        PcUserLoginVO loginUser = null;
        try{
        	loginUser = redisService.getObject(UserConstant.PC_2B_LOGIN_PREFIX + vo.getId(), PcUserLoginVO.class);
        }catch(Exception ex) {
        	logger.error("缓存用户数据有问题:"+UserConstant.PC_2B_LOGIN_PREFIX + vo.getId());
        }
        if (loginUser != null) {
            logger.info("清除用户之前登录信息:用户重新登录或在其他地方登录key1:{},key2:{}", UserConstant.PC_2B_LOGIN_PREFIX + loginUser.getUserKey(), UserConstant.PC_2B_LOGIN_PREFIX + vo.getId());
            redisService.del(UserConstant.PC_2B_LOGIN_PREFIX + vo.getId());
            redisService.del(UserConstant.PC_2B_LOGIN_PREFIX + loginUser.getUserKey());
        }
        
        logger.info("缓存pc端用户数据:{}", UserConstant.PC_2B_LOGIN_PREFIX + vo.getUserKey());
        redisService.set(UserConstant.PC_2B_LOGIN_PREFIX + vo.getUserKey(), userInfoJson, UserConstant.PC_2B_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.PC_2B_LOGIN_PREFIX + vo.getId(), userInfoJson, UserConstant.PC_2B_USER_SESSION_TIMEOUT);

        return userInfoBO;
    }


    @Override
    public void buildMenuTree(UserInfoBO userInfoBO) {
        //对pc端的标题进行排序
        List<UserMenuBO> menuList = userInfoBO.getMenuList();
        if (Objects.nonNull(menuList)){
            List<UserMenuBO> collects = menuList.stream().filter(m -> {
                return m.getParentid() == 147;
            }).sorted(Comparator.comparing(UserMenuBO::getSequence).reversed()).collect(Collectors.toList());

            List<UserMenuBO> collect = menuList.stream().filter(f -> {
                return f.getParentid() != 147;
            }).collect(Collectors.toList());

            collects.addAll(collect);
            userInfoBO.setMenuList(collects);
        }
    }

    @Override
	public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {
		// TODO Auto-generated method stub
		sysUserEquipmentService.equipmentCheck(userInfoBO.getId(), userInfoBO.getNetworkCardRestrict(),
                userLogin.getTerminalImei(), userLogin.getUsbTerminalImei(), EquipmentConstant.PC_EQUIPMENT);
	}




	@Override
	public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
		 logger.info("设置PC端配置信息");
        userInfoBO.setWebSocketMessage(ResourceConfig.PC_WEB_SOCKET_MSG_URL);
        userInfoBO.setPayCallBackServer(ResourceConfig.PC_WEB_SOCKET_PAY_ORDER_URL);
        userInfoBO.setIsLoginIn(0);
        userInfoBO.setMsgId(userLogin.getMsgId());
        userInfoBO.setTerminalImei(userLogin.getTerminalImei());
        userInfoBO.setHeartbeatTime(ResourceConfig.PC_HEART_BEAT_TIME == null ? new Integer(120) : ResourceConfig.PC_HEART_BEAT_TIME);
        userInfoBO.setResourcesUrl(ResourceConfig.PC_RESOURCES_URL);
        userInfoBO.setSitekey(ResourceConfig.PC_SERVER_SITE_KEY);
        userInfoBO.setSiteName(ResourceConfig.PC_SERVER_SITE_NAME);
        // 获取数据字典资源路径
        Map<String, String> map = new HashMap<String, String>();
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setType("resourceDirs");
        sysDictionary.setIsDeleted(0);
        List<SysDictionary> resList = sysDictionaryService.getList(sysDictionary);
        for (SysDictionary dictionary : resList) {
            map.put(dictionary.getValuekey(), dictionary.getAtt1());
        }
        userInfoBO.setResourceMap(map);
        userInfoBO.setResourcesUrls(ResourceConfig.getResourcesUrlList());
        logger.info("serverType:"+userLogin.getServerType());
        if("internal".equals(userLogin.getServerType())) {
        	userInfoBO.setServerUrl(ResourceConfig.PC_SERVER_URL);
            userInfoBO.setServitizationList(ResourceConfig.PC_SERVITIZATION_URLS);
        }else {
        	userInfoBO.setServerUrl(ResourceConfig.YUN_SERVER_URL);
            userInfoBO.setServitizationList(ResourceConfig.YUN_SERVITIZATION_URLS);
        }
        logger.info("ResourceConfig.PC_SERVER_URL:"+ResourceConfig.PC_SERVER_URL);
        logger.info("ResourceConfig.YUN_SERVER_URL:"+ResourceConfig.YUN_SERVER_URL);
        userInfoBO.setUserCenterUrl(ResourceConfig.USER_CENTER_URL);

        super.setUserPic(userInfoBO);
		
	}

}
