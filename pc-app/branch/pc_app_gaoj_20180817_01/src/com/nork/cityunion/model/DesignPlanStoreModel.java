package com.nork.cityunion.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;

/**
 * Created by kono on 2018/1/26 0026.
 */
public class DesignPlanStoreModel extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 素材Ids(多个逗号隔开)
     */
    private String storeIds;

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }
}
