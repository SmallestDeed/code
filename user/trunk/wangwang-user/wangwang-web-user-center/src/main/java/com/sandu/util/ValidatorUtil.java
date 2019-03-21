package com.sandu.util;


import com.sandu.api.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static void checkMobileCorrect(String mobile){

        if (StringUtils.isEmpty(mobile)){
            return;
        }

        if(!checkMobileFormat(mobile)){
            throw new IllegalArgumentException("手机号格式有误！");
        }
    }


    /**
     * 手机号验证
     *
     * @param str 手机号
     * @return 验证通过返回true
     */
    private static boolean checkMobileFormat(String str) {
        boolean b = false;
        Pattern p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[3579])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
