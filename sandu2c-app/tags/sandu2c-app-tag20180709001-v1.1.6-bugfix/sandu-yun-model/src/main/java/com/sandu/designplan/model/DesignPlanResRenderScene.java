/**
 * 文件名：DesignPlanRes.java
 * <p>
 * 版本信息：
 * 日期：2017-7-12
 * Copyright 足下 Corporation 2017
 * 版权所有
 */
package com.sandu.designplan.model;

import com.sandu.system.model.ResModel;

import java.io.Serializable;
import java.util.List;

/**
 * copy from DesignPlanRes
 * @author huangsongbo
 *
 */
public class DesignPlanResRenderScene implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 289049200881301247L;

    /**
     * 设计方案副本信息
     */
    private DesignPlanRenderScene designPlanRenderScene;

    /**
     * 设计方案副本关联的模型文件
     */
    private ResModel resModel;

    /**
     * 设计方法关联的配置文件副本
     */
    private ResDesignRenderScene resDesignRenderScene;

    /**
     * 设计方案使用到的产品表列表副本
     */
    private List<DesignPlanProductRenderScene> designPlanProductRenderSceneList;

    public DesignPlanResRenderScene() {
        super();
    }

    public DesignPlanResRenderScene(DesignPlanRenderScene designPlanRenderScene, ResModel resModel,
                                    ResDesignRenderScene resDesignRenderScene,
                                    List<DesignPlanProductRenderScene> designPlanProductRenderSceneList) {
        super();
        this.designPlanRenderScene = designPlanRenderScene;
        this.resModel = resModel;
        this.resDesignRenderScene = resDesignRenderScene;
        this.designPlanProductRenderSceneList = designPlanProductRenderSceneList;
    }

    public DesignPlanRenderScene getDesignPlanRenderScene() {
        return designPlanRenderScene;
    }

    public void setDesignPlanRenderScene(DesignPlanRenderScene designPlanRenderScene) {
        this.designPlanRenderScene = designPlanRenderScene;
    }

    public ResModel getResModel() {
        return resModel;
    }

    public void setResModel(ResModel resModel) {
        this.resModel = resModel;
    }

    public ResDesignRenderScene getResDesignRenderScene() {
        return resDesignRenderScene;
    }

    public void setResDesignRenderScene(ResDesignRenderScene resDesignRenderScene) {
        this.resDesignRenderScene = resDesignRenderScene;
    }

    public List<DesignPlanProductRenderScene> getDesignPlanProductRenderSceneList() {
        return designPlanProductRenderSceneList;
    }

    public void setDesignPlanProductRenderSceneList(List<DesignPlanProductRenderScene> designPlanProductRenderSceneList) {
        this.designPlanProductRenderSceneList = designPlanProductRenderSceneList;
    }

}
