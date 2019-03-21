package com.nork.product.model;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
public class ProductSearchInfoModel {

    //产品小类
    private Integer smallTypeValue;
    //产品开始长度
    private Integer productLengthStart;
    //产品终点长度
    private Integer productLengthEnd;
    //产品高度
    private Integer productHeight;
    //过滤类型
    private String filterType;

    public Integer getSmallTypeValue() {
        return smallTypeValue;
    }

    public void setSmallTypeValue(Integer smallTypeValue) {
        this.smallTypeValue = smallTypeValue;
    }

    public Integer getProductLengthStart() {
        return productLengthStart;
    }

    public void setProductLengthStart(Integer productLengthStart) {
        this.productLengthStart = productLengthStart;
    }

    public Integer getProductLengthEnd() {
        return productLengthEnd;
    }

    public void setProductLengthEnd(Integer productLengthEnd) {
        this.productLengthEnd = productLengthEnd;
    }

    public Integer getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(Integer productHeight) {
        this.productHeight = productHeight;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
}
