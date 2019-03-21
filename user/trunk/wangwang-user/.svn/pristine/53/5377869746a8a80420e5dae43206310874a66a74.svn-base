package com.sandu.api.user.model;

import lombok.Data;

@Data
public class SmsVo {
	//验证码类型--注册
	public static final String SMS_CHECK_CODE_TYPE_REGISTER = "register";
	//验证码类型--修改密码
	public static final String SMS_CHECK_CODE_TYPE_MODIFY_PASSWORD = "updateMobile";
	/**
	 * 客户端发送数量
	 */
	public static final String CLIENT_SEND_COUNT= "clientSendCont";

	/**
	 * 手机号接收数量
	 */
	public static final String MOBILE_RECEIVE_COUNT= "mobileReceiveCont";

	private String code;//验证码
	private Long sendTime;//生成时间
	private Integer verifyCount;//验证次数
	private String type;//验证类型

	/**
	 * 记录客户端IP发送时间和数量
	 */
	@Data
	public static class ClientIpSendVo {

		private Long sendTime;

		private Integer sendCount;
	}

	/**
	 * 记录手机号接受时间和数量
	 */
	@Data
	public static class MobileReceiveVo {

		private Long receiveTime;

		private Integer receiveCount;
	}
}
