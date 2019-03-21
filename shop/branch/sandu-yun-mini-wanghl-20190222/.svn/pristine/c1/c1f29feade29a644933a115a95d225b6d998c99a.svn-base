package com.sandu.web.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.sys.model.vo.SysUserVo;
import com.sandu.sys.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户服务", tags = "SysUser")
@RestController
@RequestMapping(value = "/v1/sandu/mini/sysuser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value = {"/tokenempty"},method=RequestMethod.GET)
	@ApiOperation(value = "访问令牌为空", response = ResultMessage.class)
	public ResultMessage tokenempty() {
		ResultMessage message=new ResultMessage();
		message.setMessage("访问令牌为空.");
		return message;
	}
	
	@RequestMapping(value = {"/tokenexpired"},method=RequestMethod.GET)
	@ApiOperation(value = "授权信息过期", response = ResultMessage.class)
	public ResultMessage tokenexpired() {
		ResultMessage message=new ResultMessage();
		message.setMessage("授权信息过期,请重新请求获取用户信息接口.");
		return message;
	}
	
	@RequestMapping(value = {"/refreshToken"},method=RequestMethod.GET)
	@ApiOperation(value = "刷新用户Token", response = ResultMessage.class)
	public ResultMessage refreshToken(long userId,String token) {
		ResultMessage message=new ResultMessage();
		boolean isUpdated=sysUserService.setToken(userId, token);
		if(isUpdated) {
			message.setCode(ResultCode.Success);
		}
		return message;
	}
	
	@RequestMapping(value = {"/validateToken"},method=RequestMethod.GET)
	@ApiOperation(value = "验证用户Token", response = ResultMessage.class)
	public ResultMessage refreshToken(String token) {
		ResultMessage message=new ResultMessage();
		boolean isValidated=sysUserService.validateToken(token);
		if(isValidated) {
			message.setCode(ResultCode.Success);
		}
		return message;
	}
	
	@RequestMapping(value = {"/get"},method=RequestMethod.GET)
	@ApiOperation(value = "根据OpenId获取用户信息", response = ResultMessage.class)
	public ResultMessage get(String openId) {
		ResultMessage message=new ResultMessage();
		SysUserVo user=sysUserService.get(openId);
		if(user!=null) {
			message.setCode(ResultCode.Success);
			message.setData(user);
		}
		return message;
	}
	

}
