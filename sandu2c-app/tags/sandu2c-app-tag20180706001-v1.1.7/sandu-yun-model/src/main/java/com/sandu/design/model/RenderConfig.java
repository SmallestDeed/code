package com.sandu.design.model;

/**
 * 高清渲染配置
 * Created by Administrator on 2016/5/25.
 */
public class RenderConfig {

    // 执行渲染压缩任务服务器地址,当storageType为ftp时，此地址同时为ftp服务器地址
    private String renderServer;
    // 渲染文件存储方式.可选值：ftp，local
    private String storageType;
    // 压缩文件存储根目录
    private String renderRoot;

    /**
     * 以下属性在storageType为ftp时使用
     **/
    // ftp登录用户名
    private String userName;
    // ftp登录密码
    private String password;

    public String getRenderServer() {
        return renderServer;
    }

    public void setRenderServer(String renderServer) {
        this.renderServer = renderServer;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getRenderRoot() {
        return renderRoot;
    }

    public void setRenderRoot(String renderRoot) {
        this.renderRoot = renderRoot;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
