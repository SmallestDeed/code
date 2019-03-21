package com.nork.cityunion.model.vo;

import java.io.Serializable;

/**
 * Created by xiaoxc on 2018/1/18 0018.
 */
public class UnionDesignPlanStoreCatoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //方案素材分类ID
    private Integer catoryId;
    //方案素材分类名称
    private String catoryName;

    public Integer getCatoryId() {
        return catoryId;
    }

    public void setCatoryId(Integer catoryId) {
        this.catoryId = catoryId;
    }

    public String getCatoryName() {
        return catoryName;
    }

    public void setCatoryName(String catoryName) {
        this.catoryName = catoryName;
    }
}
