package com.nork.pano.model.scene;

import com.nork.cityunion.model.vo.UnionStoreVo;
import com.nork.design.model.ProductsCostType;
import com.nork.pano.model.output.DesignPlanStoreReleaseDetailsVo;
import com.nork.pano.model.roam.Roam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public class SingleSceneVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 场景 **/
    private String scene;
    /** 用户昵称、企业LOGO、企业名称等基本信息 **/
    private PanoramaVo panoramaVo;
    /** 产品清单总价 **/
    private String totalPrice;
    /** 设计方案产品费用清单 **/
    private List<ProductsCostType> productsCostTypeList;
    /** 资源访问url **/
    private String resourceUrl;
    /** 同店联盟方案相关信息 **/
    private UnionStoreVo unionStoreVo;
    /** 设计方案渲染原图地址**/
    private String picPath;
    /** 设计方案渲染缩略图地址**/
    private String thumbnailPicPath;
    //多点720资源对象
    private List<Roam> roamList;

    public String getThumbnailPicPath() {
        return thumbnailPicPath;
    }
    /** 是否为切片资源 **/
    private Integer isShear = DesignPlanStoreReleaseDetailsVo.IsShear.NO;

    public Integer getIsShear() {
        return isShear;
    }

    public void setIsShear(Integer isShear) {
        this.isShear = isShear;
    }

    public void setThumbnailPicPath(String thumbnailPicPath) {
        this.thumbnailPicPath = thumbnailPicPath;
    }

    public List<Roam> getRoamList() {
        return roamList;
    }

    public void setRoamList(List<Roam> roamList) {
        this.roamList = roamList;
    }

    public String getPicPath() {
      return picPath;
    }

    public void setPicPath(String picPath) {
      this.picPath = picPath;
    }
    
    public UnionStoreVo getUnionStoreVo() {
      return unionStoreVo;
    }

    public void setUnionStoreVo(UnionStoreVo unionStoreVo) {
      this.unionStoreVo = unionStoreVo;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
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
