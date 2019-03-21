package com.sandu.user.model;

/**
 * Created by yanghz on 2017-08-15.
 */
public class SysUserLevelBo {
    private int id;
    private int price;
    private int userPayType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserPayType() {
        return userPayType;
    }

    public void setUserPayType(int userPayType) {
        this.userPayType = userPayType;
    }
}
