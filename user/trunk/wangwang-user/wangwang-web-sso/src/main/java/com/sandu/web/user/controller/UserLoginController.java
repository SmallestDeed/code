package com.sandu.web.user.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.service.ResPicService;
import com.sandu.api.system.service.SysVersionService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.ReturnData;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import com.sandu.common.spring.SpringContextHolder;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/user")
public class UserLoginController {
    private Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    @Resource
    private UserLoginService anonymousUserLoginService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserManageService sysUserManageService;

    @Resource
    private SysVersionService sysVersionService;

    @Autowired
    private ResPicService resPicService;

    @Value("${app.server.url}")
    private String SERVERURL;

    @Value("${app.sso.url}")
    private String SSOURL;

    @Value("${app.user.url}")
    private String USERURL;

    @Value("${app.resources.url}")
    private String RESOURCESURL;

    @Value("${domain.names}")
    private String domainNames;

    @Value("${upload.base.path}")
    private String rootPath;

    @Value("${upload.default.path}")
    private String defaultPath;

    @ApiOperation(value = "用户登录", response = ReturnData.class)
    @PostMapping("/login")
    public Object login(HttpServletRequest request, @Valid @ModelAttribute UserLogin userLogin, BindingResult validResult) {
        String platformCode = request.getHeader("Platform-Code");
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不能为空", userLogin.getMsgId(), false);
        }
        userLogin.setPlatformCode(platformCode);
        if (validResult.hasFieldErrors() 
        		&& !BasePlatform.PLATFORM_CODE_MINIPROGRAM.equals(platformCode)
        		&& !BasePlatform.PLATFORM_CODE_PC_2B_CUSTOM.equals(platformCode)) {
            return new ResponseEnvelope(true, validResult.getFieldError().getDefaultMessage(), userLogin.getMsgId(), false);
        }
        if(BasePlatform.PLATFORM_CODE_PC_2B_CUSTOM.equals(platformCode)) {
        	ResponseEnvelope r = validateCustomCompanyLogin(userLogin);
        	if(r != null) {
        		return r;
        	}
        }
        if (userLogin.getMediaType() == null) {
            String mediaType = request.getHeader("MediaType");
            if (StringUtils.isNotBlank(mediaType)) {
                userLogin.setMediaType(new Integer(mediaType));
            }
        }
        if (userLogin != null) {
            logger.info("serverTypeWeb:" + userLogin.getServerType());
        }

        if (StringUtils.isEmpty(userLogin.getClientIp())) {
            userLogin.setClientIp(request.getRemoteAddr());
        }

        UserLoginService userLoginService = null;
        try {
            userLoginService = SpringContextHolder.getBean(userLogin.getPlatformCode());
        } catch (Exception ex) {
            return new ResponseEnvelope(true, "平台编码不正确", userLogin.getMsgId(), false);
        }
        if (userLoginService != null) {
            try {
                if (userLogin.getAccount() != null) {
                    userLogin.setAccount(userLogin.getAccount().trim());
                }
                Object user = userLoginService.login(userLogin);
                if (BasePlatform.PLATFORM_CODE_MOBILE_2B.equals(platformCode)) {
                    return new ResponseEnvelope(user, String.valueOf(getValueByKey(user, "id")), true);
                }
                return new ResponseEnvelope(user, userLogin.getMsgId(), true);
            } catch (BizException ex) {
                if (BasePlatform.PLATFORM_CODE_MOBILE_2B.equals(platformCode)) {
                    return new ResponseEnvelope(true, ex.getMessage(), String.valueOf(ex.getCode()), false, ex.getCode());
                }
                if (ex.getCode() == ExceptionCodes.CODE_10010021) {
                    SysUser user = sysUserManageService.get2BUser(userLogin.getAccount(), userLogin.getPassword(), null);
                    return new ResponseEnvelope(true, ex.getMessage(), userLogin.getMsgId(), false, user != null ? user.getLastLoginCompanyId() : null, ex.getCode());
                }
                if (ex.getCode() == ExceptionCodes.CODE_10010040) {
                    SysUser sysUser = sysUserManageService.get2BUser(userLogin.getAccount(), userLogin.getPassword(), userLogin.getLoginCompanyId());
                    Map<String, Integer> resultMap = new HashMap<>();
                    resultMap.put("servicesFlag", sysUser.getServicesFlag());
                    resultMap.put("userId", sysUser.getId().intValue());
                    return new ResponseEnvelope(true, ex.getMessage(), userLogin.getMsgId(), false, resultMap, ex.getCode());
                }
                return new ResponseEnvelope(true, ex.getMessage(), userLogin.getMsgId(), false, ex.getCode());
            } catch (Exception ex) {
                logger.error("系统错误:", ex);
                return new ResponseEnvelope(true, "系统错误", userLogin.getMsgId(), false);
            }
        }
        return new ResponseEnvelope(true, "平台编码有误", userLogin.getMsgId(), false);

    }

    private ResponseEnvelope validateCustomCompanyLogin(UserLogin userLogin) {
    	 if (StringUtils.isBlank(userLogin.getAccount())) {
             return new ResponseEnvelope(true, "账号不能为空", userLogin.getMsgId(), false);
         }
    	 if (StringUtils.isBlank(userLogin.getSign())) {
             return new ResponseEnvelope(true, "签名不能为空", userLogin.getMsgId(), false);
         }
    	 if (userLogin.getTimestamp()==null) {
             return new ResponseEnvelope(true, "时间戳不能为空", userLogin.getMsgId(), false);
         }
    	 if (StringUtils.isBlank(userLogin.getCompanyCode())) {
             return new ResponseEnvelope(true, "企业编码不能为空", userLogin.getMsgId(), false);
         }
    	 return null;
    }

    @ApiOperation(value = "获取匿名用户菜单", response = ReturnData.class)
    @PostMapping("/getMenuTree")
    public Object getMenuTree(HttpServletRequest request) {
        UserLogin userLogin = new UserLogin();
        String platformCode = request.getHeader("Platform-Code");
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不能为空", userLogin.getMsgId(), false);
        } else if (!BasePlatform.PLATFORM_CODE_BRAND_2C.equals(platformCode) && !BasePlatform.PLATFORM_CODE_MOBILE_2C.equals(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不正确", userLogin.getMsgId(), false);
        }
        userLogin.setPlatformCode(platformCode);
        try {
            Object menuTree = anonymousUserLoginService.login(userLogin);
            return new ResponseEnvelope(menuTree, userLogin.getMsgId(), true);
        } catch (BizException ex) {
            return new ResponseEnvelope(true, ex.getMessage(), userLogin.getMsgId(), false);
        } catch (Exception ex) {
            logger.error("系统错误:", ex);
            return new ResponseEnvelope(true, "系统错误", userLogin.getMsgId(), false);
        }

    }

    private Object getValueByKey(Object obj, String key) {
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                if (f.getName().endsWith(key)) {
                    return f.get(obj);
                }
            } catch (Exception e) {
                logger.error("反射取值错误:", e);
            }
        }
        // 没有查到时返回空字符串
        return "";
    }


    @ApiOperation(value = "获取经销商企业列表", response = ReturnData.class)
    @PostMapping("/getFranchiserCompanyList")
    ResponseEnvelope<CompanyInfoBO> getFranchiserCompanyList(HttpServletRequest request, String account, String password, String msgId, Integer loginFlag) {
        String platformCode = request.getHeader("Platform-Code");
        if (StringUtils.isBlank(account)) {
            return new ResponseEnvelope(true, "账号不能为空", msgId, false);
        }
        if (StringUtils.isBlank(password)) {
            return new ResponseEnvelope(true, "密码不能为空", msgId, false);
        }
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不能为空", msgId, false);
        }
        if (loginFlag == null) {
            loginFlag = 0;
        }
        try {
            List<CompanyInfoBO> list = sysUserService.getFranchiserCompanyList(account, password, platformCode);
            int count = list == null ? 0 : list.size();
            return new ResponseEnvelope(true, msgId, count, list);
        } catch (Exception ex) {
            logger.error("系统错误:", ex);
            return new ResponseEnvelope(true, "系统错误", msgId, false);
        }
    }


    @ApiOperation(value = "如果一个经销商有多个企业,选择企业后调用登录接口 (单独给pc端使用)", response = ReturnData.class)
    @PostMapping("/switchCompany")
    public Object switchCompany(HttpServletRequest request, @Valid @ModelAttribute UserLogin userLogin, BindingResult validResult) {
        return this.login(request, userLogin, validResult);
    }

    @ApiOperation(value = "获取微信小程序openid(服务端也会保存)", response = ResponseEnvelope.class)
    @PostMapping("/getOpenid")
    public ResponseEnvelope<String> getOpenid(HttpServletRequest request, String appid, String code) {
        if (StringUtils.isBlank(appid)) {
            return new ResponseEnvelope(true, "appid不能为空", null, false);
        }
        if (StringUtils.isBlank(code)) {
            return new ResponseEnvelope(true, "code不能为空", null, false);
        }
        boolean isFirstLoginBefore = Boolean.FALSE;
        try {
            String openid = sysUserService.getOpenid(appid, code);
            if(openid!=null) {
            	if(!sysUserService.isMiniUserExist(openid)) {
            		sysUserService.saveMiniUserInfo(appid,openid);
                    isFirstLoginBefore = Boolean.TRUE;
            	}
            	return new ResponseEnvelope(openid, null, isFirstLoginBefore);
            }else {
            	return new ResponseEnvelope(false, "获取openId失败", null, isFirstLoginBefore);
            }
            
        } catch (BizException ex) {
            return new ResponseEnvelope(false, ex.getMessage(), null, isFirstLoginBefore);
        } catch (Exception ex) {
            logger.error("系统错误:", ex);
            return new ResponseEnvelope(false, "系统错误", null, isFirstLoginBefore);
        }


    }

    @ApiOperation(value = "保存小程序用户信息", response = ResponseEnvelope.class)
    @PostMapping("/saveMinProUserInfo")
    public ResponseEnvelope saveMinProUserInfo(String openId, String nickName,@RequestParam("file") MultipartFile file) {
        //检验参数
        if (StringUtils.isEmpty(openId)) {
            return new ResponseEnvelope(false, "openId不能为空", null, false);
        }

//        if (StringUtils.isEmpty(nickName)) {
//            return new ResponseEnvelope(false, "nickName不能为空", null, false);
//        }

        try {
            String filterNickName = "";
            //用户没有授权获取用户信息 ==>插入openId作为用户nickName
            if (StringUtils.isNotEmpty(nickName)){
                filterNickName = filterEmoji(nickName);
            }
            //保存用户头像
            String headPic = this.saveUserPic(file,filterNickName);
            //save user nickName
            int flag = sysUserService.updateMinProUserInfo(openId, filterNickName,headPic);
            return new ResponseEnvelope(true, "保存成功!!!");
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope(true, "系统错误", null, false);
        }
    }

    private String saveUserPic(MultipartFile file, String nickName) {
        int result = 0;
        //流验证
        if (file != null || !StringUtils.isBlank(file.getOriginalFilename())) {
            //用户头像不为空,上传用户头像
            String picPath = null;
            try {
                //创建文件存储路径
                picPath = this.executeUploadFile(file);
                /*if (resPic != null) {
                    this.setResPicParameters(nickName, resPic);
                logger.info("图片上传成功,回填数据库数据");
                result = resPicService.save(resPic);
                resPic.setId(result);
            }*/
                return picPath;
            } catch (IOException e) {
                logger.error("上传用户图片异常", e);
            }
        }
        return null;
    }

    private void setResPicParameters(String nickName, ResPic resPic) {
        Date now = new Date();
        resPic.setCreator(nickName);
        resPic.setIsDeleted(0);
        resPic.setGmtCreate(now);
        resPic.setGmtModified(now);
        resPic.setModifier(nickName);
    }

    private String executeUploadFile(MultipartFile file) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(rootPath);
        sb.append(defaultPath);
       // sb.append("C:\\Users\\Sandu\\Desktop\\新建文件夹");
        File dir = new File(sb.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = file.getOriginalFilename();
        //上传文件名
        String filename = StringUtils.substringBeforeLast(name, ".");
        //后缀
        String suffix = StringUtils.substringAfterLast(name, ".");
        //大小
        Long size = file.getSize();
        long millis = System.currentTimeMillis();
        sb.append("/");
        sb.append(millis);
        sb.append(".");
        sb.append(suffix);

        //上传文件
        String filePath = sb.toString();
        File targetFile = new File(filePath);
        file.transferTo(targetFile);
        logger.info("文件上传的路劲"+filePath);
        //路径
        StringBuffer sBuffer = new StringBuffer(defaultPath);
        sBuffer.append("/");
        sBuffer.append(millis);
        sBuffer.append(".");
        sBuffer.append(suffix);
        String savePath = sBuffer.toString();
        logger.error("数据库文件存储路径"+savePath);

        return savePath;
    }

    public String filterEmoji(String nickName){
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(nickName);
        if (emojiMatcher.find()) {
            //将所获取的表情转换为*
            nickName = emojiMatcher.replaceAll("*");
            return nickName;
        }
        return nickName;
    }

    @ApiOperation(value = "B端检验用户是否首次登录", response = ResponseEnvelope.class)
    @PostMapping(value = "/checkUserIsFirstLogin")
    public ResponseEnvelope checkUserIsFirstLogin(@Valid @ModelAttribute UserLogin userLogin, BindingResult validResult) {

        if (validResult.hasFieldErrors()) {
           return new ResponseEnvelope(false,validResult.getFieldError().getDefaultMessage());
        }
        Map<String, Object> returnMap = null;
        try {
            returnMap = sysUserManageService.checkUserIsFirstLogin(userLogin);
            if (returnMap != null) {
                return new ResponseEnvelope(true, returnMap, "该用户是首次登录");
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
        return new ResponseEnvelope(false, null, "该用户不是首次登录");
    }

    @RequestMapping(value = "/getMarkedMessage")
    public Object getMarkedMessage(@RequestParam(value = "userId",required = true)Long userId,
                                   @RequestParam(value = "msgId",required = false)String msgId,
                                   HttpServletRequest request
                                   ){

        String platformCode = request.getHeader("Platform-Code");
        if (StringUtils.isEmpty(platformCode)){
            return new ResponseEnvelope<>(false,"平台编码为空");
        }
        try {
            String message = sysUserService.getMarkedMessage(userId,platformCode);
            return new ResponseEnvelope<>(true,message,msgId);
        } catch (Exception e) {
           logger.error("系统错误",e);
           return new ResponseEnvelope<>(false,"系统错误",msgId);
        }
    }

    @RequestMapping(value = "/getAppUrl")
    public ResponseEnvelope getAppUrl(String  msgId){
        HashMap<String, String> map = new HashMap<>();
        map.put("normalUrl",SERVERURL);
        map.put("ssoUrl",SSOURL);
        map.put("resourcesUrl",RESOURCESURL);
        map.put("userUrl",USERURL);
        return new ResponseEnvelope(true,map,msgId);
    }

    /**
     * 最新版本检测接口
     * @author zhaobl
     * 版本资源存储表从 res_file 换成 res_version
     * 户型绘制版本包需不要壳进行自动更新,所以增加参数 isNeedShell
     * @param systemType 平台类型
     * @param systemTypeDigits 系统类型 32/64 默认为64
     * @param isNeedShell 是否需要校验壳  1(需要)，0 （不需要），为空则默认为需要
     * @return
     */
    @RequestMapping("/versionCheck")
    public Object versionCheck(String msgId,Integer systemType,Integer systemTypeDigits,@RequestParam(value = "isNeedShell",required = false)Integer isNeedShell){

        return sysVersionService.getLatestVersionInfo(msgId, systemType, systemTypeDigits, isNeedShell);
    }

    @GetMapping("/domains")
    public Object allDomainNames(String environmentName){
        List<String> allUrls = Arrays.asList(domainNames.substring(1, domainNames.length() - 1).split(","));

        Map<String, String> resultMap = new HashMap<>();

        Iterator<String> iterator = allUrls.iterator();
        while (iterator.hasNext()) {
            String url = iterator.next();
            String str = url.substring(1, url.length() - 1);
            String[] split = str.split("\\.");
            List<String> one = Arrays.asList(split);
            Iterator<String> it = one.iterator();
            int i = 0;
            String prefix = "";
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                String next1 = it.next();
                if (i == 0) {
                    prefix = next1;
                    if (Objects.equals("dev",environmentName)){
                        sb.append("http://");
                    }else{
                        sb.append("https://");
                    }
                }
                if (i == 1) {
                    sb.append(environmentName);
                    sb.append(".");
                }
                sb.append(next1);
                sb.append(".");
                i++;
            }
            resultMap.put(prefix, sb.toString().substring(0, sb.toString().length() - 1));
        }
        return new ResponseEnvelope<>(true,resultMap);
    }

}
