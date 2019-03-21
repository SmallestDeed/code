package com.sandu.common;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.sandu.api.user.model.LoginUser;

public class Utils {

	public static LoginUser getDebugUser(HttpServletRequest request) {
		String authorizationStr = request.getHeader("Authorization");
		Gson gson = new Gson();
		LoginUser loginUser = gson.fromJson(authorizationStr, LoginUser.class);
		return loginUser;
	}

}
