package com.sandu.service.user.impl.biz;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BaseBrandService;
import com.sandu.api.redis.RedisService;
import com.sandu.api.system.input.AuthorizedConfigSearch;
import com.sandu.api.system.model.AuthorizedConfig;
import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.model.SysUserLastLoginLog;
import com.sandu.api.system.service.AuthorizedConfigService;
import com.sandu.api.system.service.ResPicService;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.system.service.SysUserLastLoginLogService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.model.bo.UserMenuBO;
import com.sandu.api.user.output.MobileUserLoginVO;
import com.sandu.api.user.output.PcUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.api.user.service.manage.SysUserEquipmentService;
import com.sandu.common.AESUtil2;
import com.sandu.common.NetworkUtil;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.config.ResourceConfig;
import com.sandu.constant.EquipmentConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("pcUserLoginService")
public class PcUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(PcUserLoginServiceImpl.class);

    @Autowired
    private SysUserEquipmentService sysUserEquipmentService;
    @Autowired
    private SysUserLastLoginLogService sysUserLastLoginLogService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {
        /* 检测设备号 */
        sysUserEquipmentService.equipmentCheck(userInfoBO.getId(), userInfoBO.getNetworkCardRestrict(),
                userLogin.getTerminalImei(), userLogin.getUsbTerminalImei(), EquipmentConstant.PC_EQUIPMENT);
        /*AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
        authorizedConfigSearch.setUserId(userInfoBO.getId());
        authorizedConfigSearch.setIsDeleted(0);
        int count = authorizedConfigService.getCount(authorizedConfigSearch);
        if (count <= 0) {
            sysUserEquipmentService.deleteByUserId(userInfoBO.getId(), EquipmentConstant.MOBILE_EQUIPMENT);
            sysUserEquipmentService.deleteByUserId(userInfoBO.getId(), EquipmentConstant.PC_EQUIPMENT);
        }else {
        	//查询品牌“三度空间”的id
			int sanduId = baseBrandService.getIdByBrandName("三度空间");
			//将用户绑定的公司id返回
			List<AuthorizedConfig> authorizedConfigList = authorizedConfigService.getList(authorizedConfigSearch);
			if(authorizedConfigList!=null && authorizedConfigList.size()>0) {
				StringBuffer brandIds = new StringBuffer();
				for(AuthorizedConfig config : authorizedConfigList) {
					Integer brandId = Integer.valueOf(config.getBrandIds());
					//判断用户绑定的品牌是否有三度空间
					if(sanduId == brandId.intValue()) {
						brandIds.append("0");
						break;
					}else {
						brandIds.append(brandId);
					}
					brandIds.append(",");
				}
				//去掉最后一个逗号
				StringBuffer ids = brandIds.deleteCharAt(brandIds.length() - 1);
				userInfoBO.setBrandIds(ids.toString());
			}
        }*/
        
    }

    @Override
    public void saveLoginLog(UserInfoBO userInfoBO, UserLogin userLogin) {

        //记录登录日志
        super.saveLoginLog(userInfoBO,userLogin);

        //记录登录日志
        SysUserLastLoginLog sysUserEndLog = new SysUserLastLoginLog();
        sysUserEndLog.setUserId(userInfoBO.getId());
        sysUserEndLog.setLastLoginTime(new Date());
        sysUserEndLog.setClientIp(userLogin.getClientIp());
        sysUserEndLog.setServerIp(NetworkUtil.getLinuxLocalIp() + "/" + NetworkUtil.getLocalIP());
        sysUserEndLog.setLoginDevice(userLogin.getLoginDevice());
        sysUserEndLog.setSystemModel(userLogin.getSystemModel());
        SysUserLastLoginLog sue = sysUserLastLoginLogService.get(userInfoBO.getId());//查询该用户是否已存在最后登录记录
        if (sue == null) {      //如果没有->add
            sysUserEndLog.setLoginCount(1); //用户登录总次数
            sysUserLastLoginLogService.add(sysUserEndLog);
        } else {                  //否则update最后登录数据
            sysUserEndLog.setLoginCount(sue.getLoginCount() == null ? 1 : (sue.getLoginCount() + 1)); //用户登录总次数
            sysUserLastLoginLogService.update(sysUserEndLog);
        }

        int storageTime = 60 * 60 * 24 * 365;
        redisService.set("userIdLog", userInfoBO.getId().toString(), storageTime);
        redisService.set("loginTimeLog", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), storageTime);
        redisService.set("mobileLog", userInfoBO.getMobile(), storageTime);
        redisService.set("clientIp", userLogin.getClientIp(), storageTime);
        redisService.set("operationLoginDevice", userLogin.getLoginDevice(), storageTime);
        redisService.set("operationSystemModel", userLogin.getSystemModel(), storageTime);
       // saveUserLogToTxtFile(userInfoBO, userLogin); //执行写数据到文件方法
    }

    private void saveUserLogToTxtFile(UserInfoBO userInfoBO, UserLogin userLogin) {
        try {
            if (userInfoBO.getMobile() != null) {
                String fileName = userInfoBO.getMobile() + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
                String path = ResourceConfig.PC_SYSTEM_SYS_USER_LOGIN_LOG_UPLOAD_PATH;
                File file = new File(path);
                if (!file.exists()) {                                                //如果该目录不存在!则创建
                    file.mkdirs();
                }
                RandomAccessFile rout = new RandomAccessFile(path + fileName + ".txt", "rw"); //"rw"表示此文件可读可写
                File fileExist = new File(path + fileName + ".txt");
                if (fileExist.exists()) {                                            //判断该用户今天的txt是否存在!不在则创建
                    rout.seek(rout.length());                                    //将记录指针移动到文件最后
                }
                StringBuilder sb = new StringBuilder("");
                sb.append(userInfoBO.getId() + "#");                                                //登录的用户
                sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "#");               //登录,登出时间
                sb.append("1#");                                          //登录类型 1:登录 2:登出
                sb.append(userLogin.getClientIp() + "#");                                     //客户端IP
                sb.append(NetworkUtil.getLinuxLocalIp() + "/" + NetworkUtil.getLocalIP() + "#");                                        //服务器IP
                sb.append(userLogin.getLoginDevice() + "#");                                   //操作系统
                sb.append(userLogin.getSystemModel() + "#" + "\r\n");                          //操作系统型号
                String transForm = sb.toString();
                byte[] entering = (transForm).getBytes();
                //写入文件，还可以用其他方法如：write(String str)
                rout.write(entering, 0, entering.length);
                //关闭流
                rout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void createUserJwtToken(UserInfoBO userInfoBO) {
    	String mediaType = "3";
    	if(userInfoBO.getMediaType()!=null) {
    		mediaType = userInfoBO.getMediaType().toString();
		}
        logger.info("pc端设置用户token");
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
    public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
        // TODO Auto-generated method stub
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
        vo.setPromptUpdatePassword(userInfoBO.getPromptUpdatePassword());
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
}
