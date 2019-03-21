package com.sandu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsConfig{
	public static String SIGNKEY;
    public static String PASSWORD;
    public static String SEND_MESSAGE;
    public static String REGISTERCONTEXT;
    public static String UPDATEMOBILECONTEXT;
    public static String VERIFYCOUNT;
    public static String ALLOWED_TOS_END;
    public static String VALID_TIME;
    public static String SERVICE_PHONE;
    public static String AUTHORIZED_CONFIG_VALID_TIME7;
    public static String AUTHORIZED_CONFIG_VALID_TIME30;
	//单IP每天最多发送的短信数量
	public static Integer CILENTIP_SEND_SMS_MOST_NUMBER;
	//单手机号每天最多可收到的短信数量
	public static Integer PHONE_RECEIVE_MSOT_NUMBER;
	//发送短信间隔时间
	public static Integer SEND_INTERVAL_TIME;
	
    @Value("${sms.signKey}")
	public void setSIGNKEY(String sIGNKEY) {
		SIGNKEY = sIGNKEY;
	}
    @Value("${sms.password}")
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
    @Value("${sms.sendMessage}")
	public void setSEND_MESSAGE(String sEND_MESSAGE) {
		SEND_MESSAGE = sEND_MESSAGE;
	}
    @Value("${sms.registerContext}")
	public void setREGISTERCONTEXT(String rEGISTERCONTEXT) {
		REGISTERCONTEXT = rEGISTERCONTEXT;
	}
    @Value("${sms.updateMobileContext}")
	public void setUPDATEMOBILECONTEXT(String uPDATEMOBILECONTEXT) {
		UPDATEMOBILECONTEXT = uPDATEMOBILECONTEXT;
	}
    
    @Value("${sms.verifyCount}")
	public void setVERIFYCOUNT(String vERIFYCOUNT) {
		VERIFYCOUNT = vERIFYCOUNT;
	}
    @Value("${sms.allowedToSend}")
	public void setALLOWED_TOS_END(String aLLOWED_TOS_END) {
		ALLOWED_TOS_END = aLLOWED_TOS_END;
	}
    @Value("${sms.validTime}")
	public void setVALID_TIME(String vALID_TIME) {
		VALID_TIME = vALID_TIME;
	}
    @Value("${sms.ServicePhone}")
	public void setSERVICE_PHONE(String sERVICE_PHONE) {
		SERVICE_PHONE = sERVICE_PHONE;
	}
    @Value("${sms.authorizedConfigValidTime7}")
	public void setAUTHORIZED_CONFIG_VALID_TIME7(String aUTHORIZED_CONFIG_VALID_TIME7) {
		AUTHORIZED_CONFIG_VALID_TIME7 = aUTHORIZED_CONFIG_VALID_TIME7;
	}
    @Value("${sms.authorizedConfigValidTime30}")
	public void setAUTHORIZED_CONFIG_VALID_TIME30(String aUTHORIZED_CONFIG_VALID_TIME30) {
		AUTHORIZED_CONFIG_VALID_TIME30 = aUTHORIZED_CONFIG_VALID_TIME30;
	}

	@Value("${sms.cilentIp.everyday.send.most.number}")
	public void setCilentIpSendSmsMostNumber(Integer cilentIpSendSmsMostNumber) {
		CILENTIP_SEND_SMS_MOST_NUMBER = cilentIpSendSmsMostNumber;
	}

	@Value("${sms.phone.everyday.receive.most.number}")
	public void setPhoneReceiveMsotNumber(Integer phoneReceiveMsotNumber) {
		PHONE_RECEIVE_MSOT_NUMBER = phoneReceiveMsotNumber;
	}

	@Value("${sms.send.interval.time}")
	public void setSendIntervalTime(Integer sendIntervalTime) {
		SEND_INTERVAL_TIME = sendIntervalTime;
	}


}
