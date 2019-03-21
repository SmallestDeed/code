package com.sandu.user.model;

/**
 * 用户临时会话业务对象--用于支付系统做身份认证
 */
public class UserTempSessionBo {

    //用户会话对象
    private UserSO userSO;
    //有效期至(超出当前时间则数据已失效)
    private long validTime;

    public UserSO getUserSO() {
        return userSO;
    }

    public void setUserSO(UserSO userSO) {
        this.userSO = userSO;
    }

    public long getValidTime() {
        return validTime;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }

    @Override
    public String toString() {
        return "UserTempSessionBo{" +
                "userSO=" + userSO +
                ", validTime=" + validTime +
                '}';
    }
}
