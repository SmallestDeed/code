package com.nork.product.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.nork.common.model.LoginUser;

@Controller
 public class BaseCommonController {
	
	private static Logger logger = Logger
			.getLogger(BaseCommonController.class);
	
	private static String LOGINUSER = "loginUser";	
	/***
	 * 获取用户登录信息
	 * @param request
	 * @param  
	 * @return
	 */
 public	LoginUser getLoginUser(HttpServletRequest request){
	 LoginUser loginUser = new LoginUser();
     if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
         loginUser.setLoginName("nologin");
         
     } else {
         loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
     }
     return loginUser;
 }
 
 public	LoginUser getLoginUserTwo(HttpServletRequest request){
	 LoginUser loginUser =  com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
     return loginUser;
 }
}
