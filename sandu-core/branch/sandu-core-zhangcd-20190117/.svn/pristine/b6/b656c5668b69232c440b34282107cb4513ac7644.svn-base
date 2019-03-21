package com.sandu.api.base.output;

import lombok.Data;

/**
 * @ClassName: SmsVo
 * @Auther: gaoj
 * @Date: 2019/1/11 16:24
 * @Description:
 * @Version 1.0
 */
@Data
public class SmsVo {

    //验证码类型--注册
    public static final String SMS_CHECK_CODE_TYPE_REGISTER = "register";
    //验证码类型--修改密码
    public static final String SMS_CHECK_CODE_TYPE_MODIFY_PASSWORD = "updateMobile";

    public static final String REDIS_SMS_CODE_KEY = "SmsCode";

    public static final Integer SMS_CODE_VALID_TIME = 60000 * 3;

    private String code;//验证码
    private Long sendTime;//生成时间
    private Integer verifyCount;//验证次数
    private String type;//验证类型

}
