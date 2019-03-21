package com.sandu.api.user.constant;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/9 16:38
 */
public enum  UserEnum {

    ENABLE(1),
    DISABLE(0),;

    private int code;

    UserEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
