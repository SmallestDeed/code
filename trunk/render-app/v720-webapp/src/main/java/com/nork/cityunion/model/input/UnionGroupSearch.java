package com.nork.cityunion.model.input;

import java.io.Serializable;

/**
 * Created by chenm on 2018/10/30.
 */
public class UnionGroupSearch implements Serializable {
    private static final long serialVersionUID = 1L;

    /**门店id**/
    private Integer id;
    /** 场景类型 4:同城联盟 5:720制作**/
    private Integer sceneType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }
}
