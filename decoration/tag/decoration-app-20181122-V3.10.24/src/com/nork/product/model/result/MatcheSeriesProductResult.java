package com.nork.product.model.result;

import com.google.gson.Gson;
import com.nork.product.model.SplitTextureDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8 0008.
 */
public class MatcheSeriesProductResult implements Serializable {

    //Json转换类
    private final static Gson gson = new Gson();
    private static final long serialVersionUID = 1L;

    //要被替换的产品posName
    private String posName;

    /** 产品ID **/
    private Integer productId;
    /** 产品CODE **/
    private String productCode;
    /** 产品模型地址 **/
    private String u3dModelPath;
    /** 硬装还是软装 默认软装2 **/
    private String rootType = "2";
    /** 产品大类code **/
    private String productTypeCode;
    /** 产品大类 **/
    private String productTypeValue;
    /** 产品小类 **/
    private Integer productSmallTypeValue;
    /** 产品小类code **/
    private String productSmallTypeCode;
    /** 产品宽度 **/
    private String productWidth;
    /** 产品长度 **/
    private String productLength;
    /** 产品高度 **/
    private String productHeight;
    /** 产品属性 **/
    private Map<String,String> propertyMap;
    /** 规则 **/
    private Map<String,String> rulesMap;
    /** 产品材质图片列表 **/
    private String[] materialPicPaths;
    /*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
    private Integer isSplit = new Integer(0);
    /*可拆分材质信息*/
    private List<SplitTextureDTO> splitTexturesChoose;
    //是否是背景墙
    private Integer bgWall = new Integer(0);
    //系列标识
    private String seriesSign;

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getU3dModelPath() {
        return u3dModelPath;
    }

    public void setU3dModelPath(String u3dModelPath) {
        this.u3dModelPath = u3dModelPath;
    }

    public String getRootType() {
        return rootType;
    }

    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getProductTypeValue() {
        return productTypeValue;
    }

    public void setProductTypeValue(String productTypeValue) {
        this.productTypeValue = productTypeValue;
    }

    public Integer getProductSmallTypeValue() {
        return productSmallTypeValue;
    }

    public void setProductSmallTypeValue(Integer productSmallTypeValue) {
        this.productSmallTypeValue = productSmallTypeValue;
    }

    public String getProductSmallTypeCode() {
        return productSmallTypeCode;
    }

    public void setProductSmallTypeCode(String productSmallTypeCode) {
        this.productSmallTypeCode = productSmallTypeCode;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    public String getProductLength() {
        return productLength;
    }

    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    public String getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    public List<SplitTextureDTO> getSplitTexturesChoose() {
        return splitTexturesChoose;
    }

    public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
        this.splitTexturesChoose = splitTexturesChoose;
    }

    public Map<String, String> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, String> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public Map<String, String> getRulesMap() {
        return rulesMap;
    }

    public void setRulesMap(Map<String, String> rulesMap) {
        this.rulesMap = rulesMap;
    }

    public String[] getMaterialPicPaths() {
        return materialPicPaths;
    }

    public void setMaterialPicPaths(String[] materialPicPaths) {
        this.materialPicPaths = materialPicPaths;
    }

    public Integer getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(Integer isSplit) {
        this.isSplit = isSplit;
    }

    public Integer getBgWall() {
        return bgWall;
    }

    public void setBgWall(Integer bgWall) {
        this.bgWall = bgWall;
    }

    public String getSeriesSign() {
        return seriesSign;
    }

    public void setSeriesSign(String seriesSign) {
        this.seriesSign = seriesSign;
    }

    @Override
    public String toString() {
        return "MatcheSeriesProductResult{" +
                "posName=" + posName +
                ", productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", u3dModelPath=" + u3dModelPath +
                ", rootType='" + rootType + '\'' +
                ", productTypeCode=" + productTypeCode +
                ", productTypeValue=" + productTypeValue +
                ", productSmallTypeValue='" + productSmallTypeValue + '\'' +
                ", productSmallTypeCode='" + productSmallTypeCode + '\'' +
                ", productWidth='" + productWidth + '\'' +
                ", productLength='" + productLength + '\'' +
                ", productHeight='" + productHeight + '\'' +
                ", splitTexturesChoose=" + gson.toJson(splitTexturesChoose) +
                ", seriesSign=" + seriesSign +
                ", propertyMap=" + gson.toJson(propertyMap) +
                ", materialPicPaths=" + gson.toJson(materialPicPaths) +
                ", rulesMap=" + gson.toJson(rulesMap) +
                ", bgWall=" + bgWall +
                ", isSplit=" + isSplit +
                '}';
    }
}
