package com.sandu.user.model;

import java.io.Serializable;

/**
 * Created by yanghz on 2017-08-15.
 */
public class SysUserLevelConfig implements Serializable {
    private int id;
    /**
     * 用户id
     */
    private int userId;
    private int userLevelId;
    private int businessTypeId;
    private int businessId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(int userLevelId) {
        this.userLevelId = userLevelId;
    }

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(int businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }
}
