package com.nork.pano.model.scene;

import com.nork.common.util.FileUploadUtils;
import com.nork.design.model.ProductsCostType;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */
public class SceneVo {

    /** 场景列表 **/
    public String Scenes;
    /** 用户昵称、企业LOGO、企业名称等基本信息 **/
    private PanoramaVo panoramaVo;
    /** 产品清单总价 **/
    private String totalPrice;
    /** 设计方案产品费用清单 **/
    private List<ProductsCostType> productsCostTypeList;
    /** 资源访问url **/
    private String resourceUrl = FileUploadUtils.RESOURCES_URL;

    public String getScenes() {
        return Scenes;
    }

    public void setScenes(String scenes) {
        Scenes = scenes;
    }

    public PanoramaVo getPanoramaVo() {
        return panoramaVo;
    }

    public void setPanoramaVo(PanoramaVo panoramaVo) {
        this.panoramaVo = panoramaVo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductsCostType> getProductsCostTypeList() {
        return productsCostTypeList;
    }

    public void setProductsCostTypeList(List<ProductsCostType> productsCostTypeList) {
        this.productsCostTypeList = productsCostTypeList;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
