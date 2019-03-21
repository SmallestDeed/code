package com.nork.design.common;

public class HttpResult {
    // 响应码
    private Integer code;

    // 响应体
    private String body;

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public HttpResult() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
