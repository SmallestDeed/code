package com.nork.product.model.vo;



import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/7/12
 * @since : sandu_yun_1.0
 */

public class BaseCompanyMiniProgramConfig implements Serializable {
    private static final long serialVersionUID = 8560628535958576247L;

    private String appId;

    private Integer companyId;
    //可见品牌列表
    private String enableBrands;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnableBrands() {
        return enableBrands;
    }

    public void setEnableBrands(String enableBrands) {
        this.enableBrands = enableBrands;
    }
}
