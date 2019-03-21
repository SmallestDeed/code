package com.sandu.web.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sandu.api.system.input.SysUserBankcardInfoDTO;
import com.sandu.api.system.output.SysUserBankcardInfoListVO;
import com.sandu.api.system.service.SysUserBankcardInfoService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.Utils;
import com.sandu.common.model.output.CommonDTO;
import com.sandu.config.SystemConfig;

@RestController
@RequestMapping(value = "/v1/bankcard")
public class SysUserBankcardInfoController {

	@Autowired
	private SysUserBankcardInfoService sysUserBankcardInfoService;
	
	/**
	 * 用户银行卡信息列表
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@GetMapping("/list")
	public Object list() {
		
		// ------参数/权限验证 ->start
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null && SystemConfig.DEBUGMODEL) {
        	loginUser = Utils.getDebugUser(request);
		}
		if(loginUser == null || loginUser.getId() == null) {
			return new ResponseEnvelope<>(false, "未检测到登录信息, 请重新登录");
		}
		// ------参数/权限验证 ->end
		
		List<SysUserBankcardInfoListVO> list = null;
		try {
			list = sysUserBankcardInfoService.getSysUserBankcardInfoListVO(loginUser.getId());
		} catch (Exception e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		
		return new ResponseEnvelope<SysUserBankcardInfoListVO>(true, 0, list);
	}
	
	/**
	 * 新建银行卡信息
	 * 
	 * @author huangsongbo
	 * @param paramDTO
	 * @return
	 */
	@PostMapping("/create")
	public Object create(@Valid @RequestBody SysUserBankcardInfoDTO paramDTO, BindingResult validResult) {
		
		// ------参数/权限验证 ->start
		if (validResult.hasFieldErrors()) {
           return new ResponseEnvelope<>(false, validResult.getFieldError().getDefaultMessage());
        }
		
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if(loginUser == null || loginUser.getId() == null) {
			return new ResponseEnvelope<>(false, "未检测到登录信息, 请重新登录");
		}
		paramDTO.setUserId(loginUser.getId());
		// ------参数/权限验证 ->end
		
		try {
			sysUserBankcardInfoService.create(paramDTO, loginUser.getLoginName());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		
		return new ResponseEnvelope<>(true, "success");
	}
	
	@GetMapping("/delete")
	public Object delete(Long id) {
		// ------参数验证 ->start
		if(id == null) {
			return new ResponseEnvelope<>(false, "参数\"id\"不能为空");
		}
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if(loginUser == null || loginUser.getId() == null) {
			return new ResponseEnvelope<>(false, "未检测到登录信息, 请重新登录");
		}
		// ------参数验证 ->end
		
		try {
			sysUserBankcardInfoService.delete(id, loginUser.getId());
		} catch (Exception e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		
		return new ResponseEnvelope<>(true, "success");
	}
	
	/**
	 * 检测是否还可以创建银行卡
	 * 暂时的逻辑有: 一个用户只能创建最多十张银行卡信息
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@GetMapping("/getIsAllowCreate")
	public Object getIsAllowCreate() {
		// ------参数验证 ->start
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null && SystemConfig.DEBUGMODEL) {
        	loginUser = Utils.getDebugUser(request);
		}
		if(loginUser == null || loginUser.getId() == null) {
			return new ResponseEnvelope<>(false, "未检测到登录信息, 请重新登录");
		}
		// ------参数验证 ->start
		
		// check
		CommonDTO isAllowCreateResult = sysUserBankcardInfoService.getIsAllowCreateResult(loginUser.getId());
		
		return new ResponseEnvelope<>(true, isAllowCreateResult);
	}
	
}
