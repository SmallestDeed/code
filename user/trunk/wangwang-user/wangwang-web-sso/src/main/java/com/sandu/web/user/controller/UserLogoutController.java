package com.sandu.web.user.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.user.service.biz.UserLogoutService;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.ReturnData;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/user")
public class UserLogoutController {
	
	@Autowired
	private UserLogoutService userLogoutService;

    @ApiOperation(value = "用户登录", response = ReturnData.class)
    @PostMapping("/logout")
    public Object logout(HttpServletRequest request) {
    	String token = request.getHeader("Authorization");
    	String msgId = request.getParameter("msgId");
    	if(StringUtils.isBlank(token)) {
    		return new ResponseEnvelope(true, "token不能为空.",msgId,false);
    	}
    	try {
    		userLogoutService.logout(token);
    	}catch(Exception ex) {
    		return new ResponseEnvelope(true, "系统异常",msgId,false);
    	} 
    	return new ResponseEnvelope(true, "登出成功",msgId,true);
    	
    }
}
