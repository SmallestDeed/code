package com.nork.design.model.output;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/10/31 0031 19:42
 * @Modified By
 */
public class UnityPlanProductresTexture implements Serializable{
    private static final long serialVersionUID = -5578175804243961400L;

    /**材质id*/
    private String id;
    /**材质File名称*/
    private String fileName;
    /**材质路径*/
    private String filePath;
    /**材质材质球id*/
    private String textureBallFileId;
    /**材质模型id*/
    private String modelPath;
    /**材质缩略图id*/
    private String normalPicId;
    /**材质路径*/
    private String picPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTextureBallFileId() {
        return textureBallFileId;
    }

    public void setTextureBallFileId(String textureBallFileId) {
        this.textureBallFileId = textureBallFileId;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getNormalPicId() {
        return normalPicId;
    }

    public void setNormalPicId(String normalPicId) {
        this.normalPicId = normalPicId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
