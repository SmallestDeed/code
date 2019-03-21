package com.nork.repair.model;

import java.io.Serializable;
import java.util.List;

public class SplitTexturesInfo implements Serializable{

    private String name;

    private List<AffectTextures> affectTextures;

    private Integer defaultId;

    private String textureIds;

    private String key;

    private String textureRegionName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AffectTextures> getAffectTextures() {
        return affectTextures;
    }

    public void setAffectTextures(List<AffectTextures> affectTextures) {
        this.affectTextures = affectTextures;
    }

    public Integer getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(Integer defaultId) {
        this.defaultId = defaultId;
    }

    public String getTextureIds() {
        return textureIds;
    }

    public void setTextureIds(String textureIds) {
        this.textureIds = textureIds;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTextureRegionName() {
        return textureRegionName;
    }

    public void setTextureRegionName(String textureRegionName) {
        this.textureRegionName = textureRegionName;
    }
}
