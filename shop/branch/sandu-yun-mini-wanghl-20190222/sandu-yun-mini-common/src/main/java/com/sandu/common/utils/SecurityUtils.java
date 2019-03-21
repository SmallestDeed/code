package com.sandu.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class SecurityUtils {
	/**
	 * 根据id生成token
	 *
	 * @param userId
	 *            用户ID
	 * @return 加密token
	 */
	public static String generateToken(int userId, String secret) {
		String token = "";
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			final Date nowTime = new Date();
			token = JWT.create().withClaim("userId", userId).withClaim("iat", nowTime).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return token;
	}
}
