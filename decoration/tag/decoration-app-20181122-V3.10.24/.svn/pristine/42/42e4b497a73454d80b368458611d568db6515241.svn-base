package com.nork.design.model.output;

import com.nork.design.model.DesignPlanCustomizedProduct;

import java.io.Serializable;

/**
 * Created by kono on 2018/8/28 0028.
 */
public class DesignPlanCustomizedProductVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**  主键ID  **/
    private Integer planCustomizedProductId;
    /**  方案ID  **/
    private Integer planId;
    /**  外部产品ID  **/
    private Integer exteriorProductId;
    /**  模型地址  **/
    private String modelUrl;
    /**  外部产品类型  **/
    private String productType;
    /**  公司标识  **/
    private String companySign;
    /**  店铺标识  **/
    private String shopSign;

    public Integer getPlanCustomizedProductId() {
        return planCustomizedProductId;
    }

    public void setPlanCustomizedProductId(Integer planCustomizedProductId) {
        this.planCustomizedProductId = planCustomizedProductId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getExteriorProductId() {
        return exteriorProductId;
    }

    public void setExteriorProductId(Integer exteriorProductId) {
        this.exteriorProductId = exteriorProductId;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCompanySign() {
        return companySign;
    }

    public void setCompanySign(String companySign) {
        this.companySign = companySign;
    }

    public String getShopSign() {
        return shopSign;
    }

    public void setShopSign(String shopSign) {
        this.shopSign = shopSign;
    }

    public DesignPlanCustomizedProductVo() {}

    public DesignPlanCustomizedProductVo(DesignPlanCustomizedProduct customizedProduct) {
        this.planCustomizedProductId = customizedProduct.getId();
        this.planId = customizedProduct.getPlanId();
        this.exteriorProductId = customizedProduct.getExteriorProductId();
        this.modelUrl = customizedProduct.getModelUrl();
        this.productType = customizedProduct.getProductType();
        this.companySign = customizedProduct.getCompanySign();
        this.shopSign = customizedProduct.getShopSign();
    }

    /**
     * 转换VO
     * @param customizedProduct
     * @return
     */
    public DesignPlanCustomizedProductVo getProductVoFromCustomizedProduct(DesignPlanCustomizedProduct customizedProduct) {
        DesignPlanCustomizedProductVo customizedProductVo = new DesignPlanCustomizedProductVo();
        if (null != customizedProduct) {
            customizedProductVo.setPlanCustomizedProductId(customizedProduct.getId());
            customizedProductVo.setPlanId(customizedProduct.getPlanId());
            customizedProductVo.setExteriorProductId(customizedProduct.getExteriorProductId());
            customizedProductVo.setModelUrl(customizedProduct.getModelUrl());
            customizedProductVo.setProductType(customizedProduct.getProductType());
            customizedProductVo.setCompanySign(customizedProduct.getCompanySign());
            customizedProductVo.setShopSign(customizedProduct.getShopSign());
        }
        return customizedProductVo;
    }
}
