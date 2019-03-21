package com.sandu.commons;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/11 22:58
 */
public enum ResponseEnum
{

    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功"),

    /**
     * 创建成功
     */
    CREATED(201, "创建成功"),

    /**
     * 请求成功，但无内容
     */
    NOT_CONTENT(204, "请求成功，但无内容"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 资源已存在
     */
    IS_EXIST(401, "资源已存在"),

    /**
     * 找不到请求资源
     */
    NOT_FOUND(404, "找不到请求资源"),

    /**
     * 服务器内部错误
     */
    ERROR(500, "服务器内部错误");

    private int code;

    private String remark;

    ResponseEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public int getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
