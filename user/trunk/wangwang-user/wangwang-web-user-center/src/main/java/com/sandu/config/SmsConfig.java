package com.sandu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsConfig {
	//客服电话
	public static String SERVICEPHONE;
	//短信内容
    public static String REMIND;

    @Value("${sms.service.phone}")
	public void setSERVICEPHONE(String sERVICEPHONE) {
		SERVICEPHONE = sERVICEPHONE;
	}

    @Value("${sms.remind.account.expire}")
	public void setREMIND(String rEMIND) {
		REMIND = rEMIND;
	}

}
