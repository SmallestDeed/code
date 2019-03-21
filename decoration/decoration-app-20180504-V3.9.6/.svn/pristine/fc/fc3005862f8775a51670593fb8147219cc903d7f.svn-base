package com.nork.sandu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.sandu.metadata.ResultMessage;
import com.nork.sandu.model.dto.TUser;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

@Controller
@RequestMapping("/{style}/web/sandu/user")
public class UserController {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value = "/find")
	@ResponseBody
	public Object findUser(@RequestParam(value = "account", required = true) String account,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = new ResultMessage();
		SysUser sysUser=new SysUser();
		sysUser.setAccount(account);
		SysUser find = sysUserService.findWithAccount(account);
		if(find!=null){
			message.setSuccess(true);
			message.setMessage("OK");
			TUser user=new TUser();
			user.setId(find.getId());
			user.setMobile(find.getMobile());
			user.setNickName(find.getNickName());
			user.setPassword(find.getPassword());
			user.setUserName(find.getUserName());
			user.setUserType(find.getUserType());
			user.setBrandIds(find.getBrandIds());
			message.setData(user);
		}
		return message;
	}
}
