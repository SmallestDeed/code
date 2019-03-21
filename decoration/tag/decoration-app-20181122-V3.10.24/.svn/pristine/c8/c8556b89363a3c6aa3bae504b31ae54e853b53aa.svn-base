package com.nork.design.model.input;

import com.nork.design.model.DesignPlanCustomizedProduct;

import java.io.Serializable;

/**
 * 方案定制产品属性类
 * @thor xiaoxc
 * @data 2018/8/28
 */
public class DesignPlanCustomizedProductModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**  主键ID  **/
    private Integer planCustomizedProductId;
    /**  方案ID  **/
    private Integer planId;
    /**  外部产品ID  **/
    private Integer exteriorProductId;
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

    public DesignPlanCustomizedProduct getDesignPlanCustomizedProduct() {
        DesignPlanCustomizedProduct customizedProduct = new DesignPlanCustomizedProduct();
        customizedProduct.setId(this.planCustomizedProductId);
        customizedProduct.setPlanId(this.planId);
        customizedProduct.setExteriorProductId(this.exteriorProductId);
        customizedProduct.setProductType(this.productType);
        customizedProduct.setCompanySign(this.companySign);
        customizedProduct.setShopSign(this.shopSign);
        return customizedProduct;
    }
}
