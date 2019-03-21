package com.nork.common.param;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/11/7 0007.
 */
public class ParamCommonVerification {

    // 判断参数是否有效和完整
    public static boolean checkTheParamIsValid(String msgId, Integer... numberObjs) {
        boolean valid = true;
        if (StringUtils.isBlank(msgId)) {
            valid = false;
            return valid;
        }
        for (Integer numberV : numberObjs) {
            if (numberV == null) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean checkTheParamIsValid(String... args) {
        boolean valid = true;
        for (String arg : args) {
            if (StringUtils.isBlank(arg)) {
                valid = false;
                return valid;
            }
        }
        return valid;
    }

    public static boolean checkTheParamIsValid(Integer... numberObjs) {
        boolean valid = true;
        for (Integer numberV : numberObjs) {
            if (numberV == null) {
                valid = false;
                break;
            }
        }
        return valid;
    }
}
