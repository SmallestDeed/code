package com.sandu.pano.model.scene;

import com.sandu.design.model.ProductsCostType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 * 全景图漫游VO
 */
public class RoamSceneVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 场景集合.xml字符串
     **/
    private String scenes;
    /**
     * 用户昵称、企业LOGO、企业名称等基本信息
     **/
    private PanoramaVo panoramaVo;
    /**
     * 产品清单总价
     **/
    private String totalPrice;
    /**
     * 设计方案产品费用清单
     **/
    private List<ProductsCostType> productsCostTypeList;
    /**
     * 资源访问url
     **/
    private String resourceUrl;

    public String getScenes() {
        return scenes;
    }

    public void setScenes(String scenes) {
        this.scenes = scenes;
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

	@Override
	public String toString() {
		return "RoamSceneVo [scenes=" + scenes + ", panoramaVo=" + panoramaVo + ", totalPrice=" + totalPrice
				+ ", productsCostTypeList=" + productsCostTypeList + ", resourceUrl=" + resourceUrl + "]";
	}
    
}
