package com.sandu.web.user.controller;

import com.sandu.api.user.constant.HeaderConstant;
import com.sandu.api.user.input.UserUpdateMobile;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.ResponseBo;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.UserUpdate;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.ReturnData;
import com.sandu.common.VerifyCodeUtils;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import com.sandu.common.util.DomainUtil;
import com.sandu.common.util.NetworkUtil;
import com.sandu.commons.MessageUtil;
import com.sandu.commons.constant.SmsConstant;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/12/16 17:47
 */
@RestController
@RequestMapping(value = "/v1/center")
public class UserController {
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	private final static String CLASS_LOG_PERFIX = "[用户中心服务]:";

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserManageService sysUserManageService;
	/**
	 * 
	 * 商家后台
	 * 
	 * */
	@GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息..")
    public ResponseEnvelope getUserInfo(Integer userId) {
		logger.info("获取用户信息");
        if (null == userId) {
            return new ResponseEnvelope(false, "用户id不能为空!");
        }
        SysUser user = sysUserService.get(userId);
        //查询手机号是否被注册
        if(null != user) {
        	return new ResponseEnvelope(true,user, "查询用户信息成功!");
        }else {
        	return new ResponseEnvelope(false, "没有该用户信息!");
        }
    }
	
	@PostMapping("/editUser")
    @ApiOperation(value = "编辑用户信息")
    public ReturnData editUser(@RequestBody UserUpdate update) {
		logger.info("编辑用户信息");
        ReturnData data = ReturnData.builder();
        SysUser user = new SysUser();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        BeanUtils.copyProperties(update, user);
        user.setUserName(update.getUsername());
        user.setId(loginUser.getId());
        int i = sysUserService.update(user);
        if (i > 0) {
            return data.success(true).message("更新成功!");
        }
        return data.success(false).code(ResponseEnum.ERROR).message("更新失败!");
    }
	
	/**
	 * 绑定手机号码
	 * 应该需要传用户id
	 * */
	@PostMapping("/updatePhone")
    @ApiOperation(value = "修改手机号码")
	public ReturnData updatePhone(@Valid @RequestBody UserUpdate update, BindingResult validResult) {
		logger.info("绑定手机号");
		 ReturnData data = ReturnData.builder();
		if(null == update.getPhoneCode()) {
       	 return data.success(false).message("短信验证码不能为空!");
       }
       if(null == update.getImgCode()) {
       	return data.success(false).message("图片验证码不能为空!");
       }
       if(null == update.getUserId()) {
       	return data.success(false).message("用户id不能为空!");
       }
       if(null == update.getSysCode()) {
        return data.success(false).message("平台编码不能为空!");
       }
       if(null == update.getPhone()) {
        return data.success(false).message("手机号码不能为空!");
       }
       //校验图片验证码
       ResponseBo imgflag = sysUserService.checkImgCode(update.getSysCode(),update.getUserId().toString(),update.getImgCode());
       if(imgflag.getStatus()) {
    	   //校验手机短信验证
           ResponseBo flags = sysUserService.checkPhone(update.getPhone(),update.getPhoneCode(),update.getUserId());
           if(flags.getStatus()) {
        	   return data.success(true).message("更新成功!");
           }else {
        	   return data.success(false).message(flags.getMsg());
           }
       }else {
    	   return data.success(false).message("图片验证码输入有误!");
       }
	}
	/**
	 * 
	 * 
	 * */
	@GetMapping("/checkCode")
    @ApiOperation(value = "图片验证码校验..")
    public ResponseEnvelope checkCode(String sysCode,String userId,String imgCode) {
		logger.info("图片验证码校验");
		if(StringUtils.isBlank(sysCode)) {
			return new ResponseEnvelope(false, "平台编码不能为空!");
		}
		if(StringUtils.isBlank(userId)) {
			return new ResponseEnvelope(false, "用户id不能为空!");
		}
		if(StringUtils.isBlank(imgCode)) {
			return new ResponseEnvelope(false, "验证码不能为空!");
		}
		//校验图片验证码
       ResponseBo imgflag = sysUserService.checkImgCode(sysCode,userId,imgCode);
       if(imgflag.getStatus()) {
    	   return new ResponseEnvelope(true, "验证成功!");
       }else {
    	   return new ResponseEnvelope(false, "图片验证码输入有误!");
       }
    }
	/**
	 * 
	 * 商家后台
	 * 
	 * */
	@GetMapping("/checkPhone")
    @ApiOperation(value = "用户手机号校验..")
    public ResponseEnvelope checkPhone(String phoneNumber) {
		logger.info("手机号校验");
        if (StringUtils.isBlank(phoneNumber) || phoneNumber.length() != 11) {
            return new ResponseEnvelope(false, "手机号格式错误");
        }
        //查询手机号是否被注册
        boolean flag = sysUserService.checkPhone(phoneNumber);
        if(flag) {
        	return new ResponseEnvelope(false, "此手机号已经被注册");
        }else {
        	return new ResponseEnvelope(true, "此手机号验证通过");
        }
    }
	
	
	/**
	 * 
	 * 手机发送验证码接口
	 * 
	 * */
	@RequestMapping(value = "/getSms")
    @ApiOperation(value = "手机发送验证码接口..")
	public ResponseEnvelope getSms(String phoneNumber,String msgId) {
		logger.info("手机发送验证码接口");
        if (StringUtils.isBlank(phoneNumber)) {
        	 logger.warn("[用户中心服务]:手机验证码服务：mobile is null");
        	 return new ResponseEnvelope(false, "手机号不能为空",msgId);
        }
        if (!isMobile(phoneNumber)) {
            logger.warn("手机验证码服务：mobile is invalid");
            return new ResponseEnvelope(false, "请输入正确的手机号",msgId);
        }
        Boolean status = sysUserService.sendMessage(phoneNumber);
        if (!status) {
            logger.info("调用发送短信服务返回的状态：status is false");
            return new ResponseEnvelope(false, "发送短信失败",msgId);
        }
        return new ResponseEnvelope(true, "发送短信成功",msgId,true);
    }


	/**
	 * 生成验证码图片
	 * 
	 * */
	@GetMapping("/getCode")
	@ApiOperation(value = "生成验证码图片")
	 public ResponseEnvelope getCode(HttpServletResponse response,String userId,String sysCode) {
		logger.info("进入生成验证码图片");
		if(null == userId) {
			 return new ResponseEnvelope(false,"用户id不能为空!");
		}
		if(null == sysCode) {
			return new ResponseEnvelope(false,"平台编码不能为空!");
		}
		 //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
      
        //生成图片 
        int w = 100, h = 30;
        //获取
        String code = sysUserService.getCode(sysCode, userId);
        if (!StringUtils.isBlank(code)) {
        	//删除之前的
            sysUserService.delCode(sysCode, userId);
        }
        //存入缓存
        sysUserService.saveCode(sysCode,userId,verifyCode);
        try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 return new ResponseEnvelope(true);
	}
	
	
	/**
	 * pC端修改用户密码
	 * @RequestBody 
	 * 
	 * comfirmFlag==1 不需要确认
	 * comfirmFlag==0 需要确认
	 * 
	 * */
	@PostMapping("/updatePwd")
    @ApiOperation(value = "pC端修改用户密码..")
    public ResponseEnvelope updatePwd(@Valid SysUser sysUser,String msgId, HttpServletRequest request) {
		logger.info("pC端修改用户密码");
		if(StringUtils.isBlank(sysUser.getPassword())) {
			return new ResponseEnvelope(false, "密码不能为空!",msgId);
		}
		if(StringUtils.isBlank(sysUser.getPhoneCode())){
			return new ResponseEnvelope(false, "短信验证码不能为空!",msgId);
		}
		if(StringUtils.isBlank(sysUser.getMobile())){
			return new ResponseEnvelope(false, "手机号码不能为空!",msgId);
		}
		Long id = (Long) request.getAttribute("tokenUserId");
		//校验手机短信验证
        ResponseBo flags = sysUserService.checkPhone(sysUser.getMobile(),sysUser.getPhoneCode());
        if(flags.getStatus()){
        	CompanyInfoBO bo = new CompanyInfoBO();
        	bo.setUserId(id);//用户id
			bo.setMobile(sysUser.getMobile());//用户手机号码
			
        	//1.同一个企业的手机号是唯一的
        	//2.不同企业，只有经销商类型用户的手机可以不唯一
        	
        	//1.先判断用户类型  
        	SysUser userType = sysUserService.selectUserTypeById(id);
        	if(null !=userType){
        		//查询传过来的额手机号码是否在数据库存在(根据手机号查询)
        		
        		//2.如果用户类型为经销商（3）
        		if(3==userType.getUserType()){
        			//如果该账号已经绑定了，就不需要验证
        			List<SysUser> userTypeS = sysUserService.selectUserTypeByMobile(sysUser.getMobile());
        			
        			if(null != userTypeS){
        				for(SysUser li : userTypeS){
        					if(li.getUserType()!=3){//绑定了非经销商用户
        						return new ResponseEnvelope(false,0, "非同一企业的经销商手机号允许重复，其他账号手机号不允许重复!",msgId);
        					}/*else{
        						
        					}*/
        				}
        			}
        			
        			//3.查询要绑定的手机号的公司id 根据要绑定的手机号码和当前用户id
        			CompanyInfoBO cbo = sysUserService.selectCompanyIdByMobielAndId(bo);
        			
        			if(null != cbo){
        				//如果在后台注册的账号 手机为空，，那么根据要绑定的手机号去查询当前企业有没有相同的手机号
        				if(null == cbo.getMobile() || "".equals(cbo.getMobile())){
        					cbo.setMobile(sysUser.getMobile());
        					int count = sysUserService.selectCountsByUserIdAndMobile(cbo);
        					if(count>0){
        						return new ResponseEnvelope(false,0, "非同一企业的经销商手机号允许重复，其他账号手机号不允许重复!",msgId);
        					}
        				}
        				//要绑定的手机号码
        				cbo.setMobile(sysUser.getMobile());
        				cbo.setUserId(id);
        				//4.查询当前公司下面的手机号是否重复
        				List<CompanyInfoBO> companyInfoBOs = sysUserService.selectCountByIdAndMobile(cbo);
        				if(null != companyInfoBOs){
        					if(companyInfoBOs.size()>0){
        						return new ResponseEnvelope(false,0, "非同一企业的经销商手机号允许重复，其他账号手机号不允许重复!",msgId);
        					}
        					/*for(CompanyInfoBO bos :companyInfoBOs){
        						if(bos.getId().longValue()!=cbo.getId().longValue() && bos.getUserId().longValue() != id.longValue()){
        							return new ResponseEnvelope(false,0, "该手机号已被其他经销商绑定!",msgId);
        						}
        					}*/
        				}
        			}else{
        				return new ResponseEnvelope(false,0, "该经销商没有公司!",msgId);
        			}
        			
        			if(null != sysUser.getComfirmFlag() && 1==sysUser.getComfirmFlag()){
            			//查询要绑定的手机号码有没有被其他经销商绑定
            			int counts = sysUserService.selectCountMobileByMobile(sysUser.getMobile());
    	        			if(counts>1){
    	        				return new ResponseEnvelope(true,1, "该手机号已绑定其他账号!",msgId);
    	        			}
            			}
        			
        			//根据当前用户id去查询该手机号码有没有绑定
        			//默认没有绑定
        			boolean fals = false;
        			SysUser sysUserMobile = sysUserService.selectMobileById(id);
        			if(null != sysUserMobile){
        				if(null != sysUserMobile.getMobile() && sysUserMobile.getMobile().equals(sysUser.getMobile())){
        					//说明该用户已经绑定了
        					fals = true;
        				}
        			}
        			//没有绑定执行的操作
        			if(!fals){
        				//查询非本企业的手机号   根据手机号码 根据当前手机号码和公司id
            			if(null != sysUser.getComfirmFlag() && 1==sysUser.getComfirmFlag()){
            				int count = sysUserService.selectCountsByIdAndMobile(bo);
            				if(count>0){
            					return new ResponseEnvelope(true,1, "该手机号已绑定其他账号!",msgId);
            				}
            			}
        			}
        		}else{
        			//如果是绑定的是经销手机号是不允许的
        			int a = sysUserService.selectMobileAndUserTypeByUserInfo(sysUser.getMobile());
        			if(a>0){
        				return new ResponseEnvelope(false,0, "非同一企业的经销商手机号允许重复，其他账号手机号不允许重复!",msgId);
        			}
        			
        			int count = sysUserService.selectCountById(bo);
        			if(count>0){
        				return new ResponseEnvelope(false,0, "非同一企业的经销商手机号允许重复，其他账号手机号不允许重复!",msgId);
        			}
        		}
        	}else{
        		return new ResponseEnvelope(false,0, "用户类型为空!",msgId);
        	}
        	
        	
        	sysUser.setId(id);
        	//根据用户id 去修改手机号码
    		int userId = sysUserService.updateMobileByUserId(sysUser);
    		if(userId>0){
    			int counts = sysUserService.selectMobileByUserInfo(sysUser.getMobile());
    			if(counts>1){
    				return new ResponseEnvelope(true,0, "数据异常,非经销商用户存在多条数据,请联系管理员!",msgId);
    			}
    			//通过手机号查询 last_login_company_id不为null
    			Long companyId = sysUserService.selectCompanyIdByMobile(sysUser.getMobile());
    			sysUser.setLastLoginCompanyId(companyId);
    			//根据手机号修改密码
    			int i = sysUserService.updateByPhone(sysUser);
        		if(i>0){
        			return new ResponseEnvelope(true,0, "修改成功!",msgId);
        		}else{
        			return new ResponseEnvelope(false,0, "更新失败!",msgId);
        		}
    		}else{
    			return new ResponseEnvelope(false,0, "更新失败!",msgId);
    		}
        	
        	
        }else{
        	return new ResponseEnvelope(false,0,flags.getMsg(),msgId);
        }
    } 
	
	/**
	 * 忘记密码接口(商家后台)
	 * 
	 * */
	@PostMapping("/forgetPwd")
    @ApiOperation(value = "忘记密码..")
    public ResponseEnvelope forgetPwd(@Valid @RequestBody SysUser sysUser) {
		if (StringUtils.isEmpty(sysUser.getPassword())) {
			return new ResponseEnvelope<SysUser>(false, "新密码不能为空");
		}
		if (StringUtils.isEmpty(sysUser.getMobile())) {
			return new ResponseEnvelope<SysUser>(false, "手机号不能为空");
		}
		if (!isMobile(sysUser.getMobile())) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！");
		}
		if (StringUtils.isEmpty(sysUser.getPhoneCode())) {
			return new ResponseEnvelope<SysUser>(false, "请输入验证码！");
		}
		//校验手机短信验证
        ResponseBo flags = sysUserService.checkPhone(sysUser.getMobile(),sysUser.getPhoneCode());
        if(flags.getStatus()){
        	int i = sysUserService.selectMobileByMobile(sysUser.getMobile());
    		if(i>0){
    			sysUser.setPasswordUpdateFlag(0);
    			int a = sysUserService.updatePwdByMobile(sysUser);
    			if(a>0){
    				return new ResponseEnvelope(true,0, "修改成功!");
    			}else{
    				return new ResponseEnvelope(false,0, "修改失败!");
    			}
    		}else{
    			return new ResponseEnvelope(false,0, "该手机号不存在!");
    		}
        }else{
        	return new ResponseEnvelope(false,0,flags.getMsg());
        }
		
	}
	
	@ApiOperation(value = "修改手机号", response = ReturnData.class)
    @PostMapping("/modifyUserMobile")
    public Object modifyUserMobile(HttpServletRequest request,@Valid @ModelAttribute UserUpdateMobile userUpdateMobile, BindingResult validResult) {
		ResponseEnvelope responseenvelope = new ResponseEnvelope();
		if(validResult.hasFieldErrors()) {
        	return new ResponseEnvelope(true, validResult.getFieldError().getDefaultMessage(),userUpdateMobile.getMsgId(),false);
        }
		if(!isMobile(userUpdateMobile.getMobile())){
			return new ResponseEnvelope(false,0,"手机格式不正确!",userUpdateMobile.getMsgId());
		}
		if(null == userUpdateMobile.getPhoneCode() || "".equals(userUpdateMobile.getPhoneCode())){
			 return new ResponseEnvelope(false,0,"短信验证码不能为空!",userUpdateMobile.getMsgId());
		}
	    LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		//校验手机短信验证码
		ResponseBo flag = sysUserService.checkPhone(userUpdateMobile.getMobile(),userUpdateMobile.getPhoneCode());//提到controller去
		if(flag.getStatus()){
			try {
				int a = sysUserService.modifyUserMobile(userUpdateMobile, loginUser.getId(),loginUser.getUserType());
				if(a>0){
					return new ResponseEnvelope(true,0, "绑定成功!", userUpdateMobile.getMsgId(),true);
				}else if(a==0){
					return new ResponseEnvelope(false,0, "绑定失败!", userUpdateMobile.getMsgId());
				}
			} catch (BizException ex) {
				// TODO: handle exception
				if(ex.getCode()== ExceptionCodes.CODE_10010200){
					return new ResponseEnvelope(true,1, ex.getMessage(), userUpdateMobile.getMsgId(),true);
				}else if(ex.getCode()==ExceptionCodes.CODE_10010202){
					return new ResponseEnvelope(true,0, ex.getMessage(), userUpdateMobile.getMsgId(),true);
				}
				//这步有点问题
				return new ResponseEnvelope(false,0, ex.getMessage(), userUpdateMobile.getMsgId());
			} catch(Exception ex) {
	    		logger.error("系统错误:",ex);
	    		return new ResponseEnvelope(false, "系统错误!",userUpdateMobile.getMsgId());
	    	}
		}else{
			return new ResponseEnvelope(false,0,flag.getMsg(),userUpdateMobile.getMsgId());
		}
		return responseenvelope;
	}
	
	//判断是否为多企业经销商账号
	@ApiOperation(value = "判断是否为多企业经销商账号", response = ReturnData.class)
    @PostMapping("/isMultipleFranchiserAccount")
    public Object isMultipleFranchiserAccount(HttpServletRequest request,String msgId) {
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		//根据用户id去查询是否有多个企业
		boolean flag = sysUserService.selectMultipleFranchiserAccount(loginUser.getId());
		if(flag){
			return new ResponseEnvelope(true,1, "是", msgId,true);
		}else{
			return new ResponseEnvelope(true,0, "否", msgId,true);
		}
	}
	
	//经销商用户绑定企业经销商
	@ApiOperation(value = "经销商用户绑定企业经销商", response = ResponseEnvelope.class)
    @PostMapping("/bindCompanyFranchiser")
    public ResponseEnvelope bindCompanyFranchiser(HttpServletRequest request,String account,String password,Long companyFranchiserId,Long companyId, String msgId) {
		SysUser user = sysUserManageService.get2BUser(account, password,companyId);
		if(user==null) {
			return new ResponseEnvelope(true,0, "更新失败", msgId,true);
		}
		//根据用户id去查询是否有多个企业
		boolean flag = sysUserService.addUserFranchiser(user.getId(), companyFranchiserId);
		if(flag){
			return new ResponseEnvelope(true,1, "更新成功", msgId,true);
		}else{
			return new ResponseEnvelope(true,0, "更新失败", msgId,true);
		}
	}


	@GetMapping("/getUsers")
	public Object getCompanyUsers(@RequestParam(value = "companyId",required = true) Long companyId){

		try {
			List<SysUser> sysUsers =  sysUserService.getUsersByCompanyId(companyId);
			return new ResponseEnvelope<>(true,sysUsers);
		} catch (Exception e) {
			logger.error("系统错误",e);
			return new ResponseEnvelope<>(false,"系统错误");
		}
	}

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
        p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[3579])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

	/**
	 * 手机发送验证码接口
	 * @param phoneNumber	手机号
	 * @param functionType 	接口类型(0:注册账号和修改手机号, 1:密码修改)
	 */
	@RequestMapping(value = "/getSmsCode")
	@ApiOperation(value = "发送手机短信验证码接口..")
	public ResponseEnvelope getSmsCode(String phoneNumber, Integer functionType, String msgId, HttpServletRequest request) {

		if (StringUtils.isEmpty(phoneNumber)) {
			logger.error(CLASS_LOG_PERFIX + "手机号为空!");
			return new ResponseEnvelope(false, "手机号不能为空", msgId);
		}
		if (!isMobile(phoneNumber)) {
			logger.error(CLASS_LOG_PERFIX + "手机号格式不正确,phoneNumber：{}", phoneNumber);
			return new ResponseEnvelope(false, "请输入正确的手机号", msgId);
		}

		if( functionType == null ){
			logger.error(CLASS_LOG_PERFIX + "接口类型为空");
			return new ResponseEnvelope(false, "接口类型为空", msgId);
		}

		//获取平台编码
		String platformCode = request.getHeader(HeaderConstant.PLATFORM_CODE);
		if (StringUtils.isEmpty(platformCode)) {
			logger.error(CLASS_LOG_PERFIX + "平台类型为空!platformCode:{}", platformCode);
			return new ResponseEnvelope(false, "Param is empty!", msgId);
		}
		//获取小程序appId
		String customerRefer = request.getHeader(HeaderConstant.REFERER);
		if (null == customerRefer && "".equals(customerRefer)) {
			customerRefer = request.getHeader(HeaderConstant.CUSTOM_REFERER);
		}
		String appId = DomainUtil.getAppIdByReferer(customerRefer);

		//查询手机号是否存在,true:存在,false:不存在
		/*boolean flag = sysUserService.checkPhone(phoneNumber, platformCode, appId);

		functionType = null == functionType ? -1 : functionType;
		switch (functionType) {
			// 用户注册
			case SmsConstant.FUNCTION_USER_REGISTER:
				if (flag) {
					logger.error(CLASS_LOG_PERFIX + "手机号已存在,phoneNumber：{},platformCode:{},appId:{}", phoneNumber, platformCode, appId);
					return new ResponseEnvelope(false, "手机号已存在！", msgId);
				}
				break;
			// 更新手机密码
			case SmsConstant.FUNCTION_UPDATE_LOGIN_PASSWORD:
				if (!flag) {
					logger.error(CLASS_LOG_PERFIX + "手机号不存在,phoneNumber：{},platformCode:{},appId:{}", phoneNumber, platformCode, appId);
					return new ResponseEnvelope(false, "手机号不存在！", msgId);
				}
				break;
			// 绑定手机号
			case SmsConstant.FUNCTION_BINDING_PHONE:
				if (flag) {
					logger.error(CLASS_LOG_PERFIX + "手机号已被使用,phoneNumber：{},platformCode:{},appId:{}", phoneNumber, platformCode, appId);
					return new ResponseEnvelope(false, "手机号已使用！", msgId);
				}
				break;
			default:
				logger.error(CLASS_LOG_PERFIX + "功能类型不在处理逻辑范围类[0,1,2],functionType:{}", functionType);
				return new ResponseEnvelope(false, "Param is error！", msgId);
		}*/

		String clientIp = null;
		try {
			clientIp = NetworkUtil.getIpAddress(request);
		} catch (IOException e) {
			logger.error(CLASS_LOG_PERFIX + "获取客户端IP异常,Execption:{}", e);
			return new ResponseEnvelope(true, "数据异常", msgId);
		}

		// 验证短信频繁请求和限制次数
		MessageUtil messageUtil = sysUserService.verifySmsInfo(phoneNumber, clientIp);
		if (!messageUtil.isStauts()) {
			return new ResponseEnvelope(false, messageUtil.getMessage(), msgId);
		}
		Map<String, Integer> map = (Map<String, Integer>) messageUtil.getObject();

		Boolean status = sysUserService.sendMessage(phoneNumber, clientIp, functionType, map, platformCode);
		if (!status) {
			logger.info("调用发送短信服务返回的状态：status is false");
			return new ResponseEnvelope(false, "发送短信失败", msgId);
		}
		return new ResponseEnvelope(true, "发送短信成功", msgId,true);
	}
    
}
