/**    
 * 文件名：DesignPlanRes.java    
 *    
 * 版本信息：    
 * 日期：2017-7-12    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.sandu.api.designplan.model;

import com.sandu.system.model.ResDesign;
import com.sandu.system.model.ResModel;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 项目名称：timeSpace 类名称：DesignPlanRes 类描述： 设计方案关联的所有资源 创建人：Timy.Liu 创建时间：2017-7-12
 * 下午6:48:46 修改人：Timy.Liu 修改时间：2017-7-12 下午6:48:46 修改备注：
 * 
 * @version
 * 
 */
public class DesignPlanRes implements Serializable {

    /**
     * serialVersionUID:
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 6858773943057077565L;

    /**
     * 设计方案信息
     */
    private DesignPlan designPlan;

    /**
     * 设计方案关联的模型文件
     */
    private ResModel resModel;

    /**
     * 设计方法关联的配置文件
     */
    private ResDesign resDesign;

    /**
     * 设计方案使用到的产品表列表
     */
    private List<DesignPlanProduct> designPlanProductList;

    /**
     * 设计方案使用过的产品表。复制设计方案的过程，该数据可以不提供
     */
    private UsedProducts usedProducts;
    //平台ID
    private Integer platformId;
    

    private Integer supplyDemandId;

    private Integer houseId;

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public Integer getSupplyDemandId() {
		return supplyDemandId;
	}

	public void setSupplyDemandId(Integer supplyDemandId) {
		this.supplyDemandId = supplyDemandId;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public DesignPlanRes() {
		super();
	}

	public DesignPlanRes(DesignPlan designPlan, ResModel resModel, ResDesign resDesign,
                         List<DesignPlanProduct> designPlanProductList, UsedProducts usedProducts) {
		super();
		this.designPlan = designPlan;
		this.resModel = resModel;
		this.resDesign = resDesign;
		this.designPlanProductList = designPlanProductList;
		this.usedProducts = usedProducts;
	}
	public DesignPlanRes(DesignPlan designPlan, ResModel resModel, ResDesign resDesign,
                         List<DesignPlanProduct> designPlanProductList, List<ResRenderPic> resRenderPicList, List<ResRenderVideo> resRenderVideoList ) {
		super();
		this.designPlan = designPlan;
		this.resModel = resModel;
		this.resDesign = resDesign;
		this.designPlanProductList = designPlanProductList;
		this.resRenderPicList = resRenderPicList;
		this.resRenderVideoList = resRenderVideoList;
	}
	/**
	 * 自动渲染出的图片
	 */
	private List<ResRenderPic> resRenderPicList;
	/**
	 * 自动渲染出的视频
	 */
	private List<ResRenderVideo> resRenderVideoList;

	public List<ResRenderPic> getResRenderPicList() {
		return resRenderPicList;
	}

	public void setResRenderPicList(List<ResRenderPic> resRenderPicList) {
		this.resRenderPicList = resRenderPicList;
	}

	public List<ResRenderVideo> getResRenderVideoList() {
		return resRenderVideoList;
	}

	public void setResRenderVideoList(List<ResRenderVideo> resRenderVideoList) {
		this.resRenderVideoList = resRenderVideoList;
	}

	public ResModel getResModel() {
        return resModel;
    }

    public void setResModel(ResModel resModel) {
        this.resModel = resModel;
    }

    public ResDesign getResDesign() {
        return resDesign;
    }

    public void setResDesign(ResDesign resDesign) {
        this.resDesign = resDesign;
    }

    public List<DesignPlanProduct> getDesignPlanProductList() {
		return designPlanProductList;
	}

	public void setDesignPlanProductList(List<DesignPlanProduct> designPlanProductList) {
		this.designPlanProductList = designPlanProductList;
	}

	public UsedProducts getUsedProducts() {
        return usedProducts;
    }

    public void setUsedProducts(UsedProducts usedProducts) {
        this.usedProducts = usedProducts;
    }

    public DesignPlan getDesignPlan() {
        return designPlan;
    }

    public void setDesignPlan(DesignPlan designPlan) {
        this.designPlan = designPlan;
    }

}
