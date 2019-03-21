package com.sandu.api.solution.constant;

/**
 * ShelfStatus class
 *
 * @author bvvy
 * @date 2018/04/02
 */
public enum ShelfStatus {

    /**
     * 一键菜单栏目
     */
    ONEKEY("ONEKEY"),

    /**
     * 公开方案栏目
     */
    OPEN("OPEN"),

    /**
     * 模板方案栏目
     */
    TEMPLATE("TEMPLATE");

    private String code;

    ShelfStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
