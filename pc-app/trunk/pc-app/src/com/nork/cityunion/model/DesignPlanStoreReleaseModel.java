package com.nork.cityunion.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;

/**
 * Created by xiaoxc on 2018/1/22 0022.
 */
public class DesignPlanStoreReleaseModel extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;

    //设计方案素材Id
    private Integer storeId;
    //设计方案素材发布名称
    private String storeReleaseName;
    //设计方案素材发布ID
    private Integer releaseId;
    //设计方案素材发布IDs
    private String releaseIds;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreReleaseName() {
        return storeReleaseName;
    }

    public void setStoreReleaseName(String storeReleaseName) {
        this.storeReleaseName = storeReleaseName;
    }

    public Integer getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Integer releaseId) {
        this.releaseId = releaseId;
    }

    public String getReleaseIds() {
        return releaseIds;
    }

    public void setReleaseIds(String releaseIds) {
        this.releaseIds = releaseIds;
    }
}
