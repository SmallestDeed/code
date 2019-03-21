package com.sandu.api.resmodel.model;



/**
 * 转化状态 模型
 *
 * @author bvvy
 * @date 2018/4/2
 */
public enum TransStatus {


    /**
     * 没有转化
     */
    NONE("NONE"),
    /**
     * 转化中
     */
    ING("ING"),
    /**
     * 转化成功
     */
    SUCCESS("SUCCESS"),
    /**
     * 转化失败
     */
    FAIL("FAIL"),
    /**
     * 需手动处理
     */
    HANDLE("HANDLE");

    /**
     * 编码
     */
    private String code;

    TransStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
