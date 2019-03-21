package com.nork.user.model.bo;

/**
 * Created by yanghz on 2017-08-07.
 */
public class UserRegisterInfoBo {
    //用户类型
    private int userType;

    //用户id
    private long userId;

    //个人（企业）名字
    private String name;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
