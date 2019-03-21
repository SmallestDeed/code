package com.nork.product.model;

import com.nork.system.model.ResPic;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 产品平台信息实体类
 * @Date 2018/5/22 0022 10:51
 * @Modified By
 */
public class ProductPlatform {
    private BigDecimal salePrice;//产品在此平台售价
    private String  description;//产品在此平台描述
    private BigDecimal  advicePrice;//产品建议售价
    private String  styleId;//产品风格
    private Integer picId;//产品默认图片id
    private String  picPath;//产品默认图片路径
    private String  picIds;//产品所有图片ID

    /**产品价格单位名称*/
    private String salePriceValueName;


    /** 价格总和 */
    private BigDecimal salePriceSum;
    private BigDecimal advicePriceSum;


    /** 产品风格、list */
    private List<String> productStyleInfoList;
    private String productStyle;


    /** 产品图片、缩略图 list*/
    private List<String> smallPiclist;
    private List<ResPic> thumbnailList;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getAdvicePrice() {
        return advicePrice;
    }

    public void setAdvicePrice(BigDecimal advicePrice) {
        this.advicePrice = advicePrice;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicIds() {
        return picIds;
    }

    public void setPicIds(String picIds) {
        this.picIds = picIds;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getSalePriceValueName() {
        return salePriceValueName;
    }

    public void setSalePriceValueName(String salePriceValueName) {
        this.salePriceValueName = salePriceValueName;
    }

    public BigDecimal getSalePriceSum() {
        return salePriceSum;
    }

    public void setSalePriceSum(BigDecimal salePriceSum) {
        this.salePriceSum = salePriceSum;
    }

    public BigDecimal getAdvicePriceSum() {
        return advicePriceSum;
    }

    public void setAdvicePriceSum(BigDecimal advicePriceSum) {
        this.advicePriceSum = advicePriceSum;
    }

    public List<String> getProductStyleInfoList() {
        return productStyleInfoList;
    }

    public void setProductStyleInfoList(List<String> productStyleInfoList) {
        this.productStyleInfoList = productStyleInfoList;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public List<String> getSmallPiclist() {
        return smallPiclist;
    }

    public void setSmallPiclist(List<String> smallPiclist) {
        this.smallPiclist = smallPiclist;
    }

    public List<ResPic> getThumbnailList() {
        return thumbnailList;
    }

    public void setThumbnailList(List<ResPic> thumbnailList) {
        this.thumbnailList = thumbnailList;
    }
}
