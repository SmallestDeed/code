package com.nork.product.model.result;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public class DesignProductResult {

    /**  产品Id  **/
    private Integer productId;
    /**  产品编码  **/
    private String productCode;
    /**  产品大分类  **/
    private String bigTypeValue;
    /**  产品小分类  **/
    private Integer smallTypeValue;
    /**  白膜小分类  **/
    private Integer bmSmallTypeValue;
    /**  产品长度  **/
    private String productLength;
    /**  产品宽度  **/
    private String productWidth;
    /**  产品高度  **/
    private String productHeight;
    /**  产品最小高度  **/
    private String minHeight;
    /**  产品全铺长度  **/
    private String fullPaveLength;
    /**  产品序号  **/
    private Integer productIndex;
    /**  产品大分类key  **/
    private String bigTypeKey;
    /**  产品小分类key  **/
    private String smallTypeKey;
    /**  产品款式  **/
    private Integer styleId;
    /**  产品颜色  **/
    private Integer colorId;
    /**  产品风格  **/
    private String productStyleIdInfo;
    /*标准(1标准 0 非标准)*/
    private Integer isStandard;
    /*中心点*/
    private String center;
    /*区域标识*/
    private String regionMark;
    /*尺寸代码*/
    private String measureCode;
    /*是否为结构中的主产品标识（1是，0否）*/
    private Integer isMainStructureProduct;
    /*主产品单品是否可以作为组合方式替换（1是，0否）*/
    private Integer isGroupReplaceWay;
    /*墙体类型*/
    private String wallOrientation;
    /*墙体方位*/
    private String wallType;
    /*posName*/
    private String posName;
    /*多维材质信息*/
    private String splitTexturesChooseInfo;
    /*原始模型*/
    private Integer modelProductId;
    /*是否是主产品*/
    private Integer isMainProduct;
    /*方案组合标识*/
    private String planGroupId;
    /*组合id*/
    private Integer productGroupId;
    /*组合类型*/
    private Integer groupType;
    /*绑定点*/
    private String bindParentProductId;
    /*产品款式*/
    private Integer proStyleId;
    /*匹配标记*/
    private Integer matchSign;
    /*产品型号*/
    private String productModelNumber;
    //匹配日志
    private String matchErrorInfo;
    //系列标志
    private String seriesSign;

    public String getSeriesSign() {
        return seriesSign;
    }

    public void setSeriesSign(String seriesSign) {
        this.seriesSign = seriesSign;
    }

    public String getMatchErrorInfo() {
        return matchErrorInfo;
    }

    public void setMatchErrorInfo(String matchErrorInfo) {
        this.matchErrorInfo = matchErrorInfo;
    }

    public String getProductModelNumber() {
        return productModelNumber;
    }

    public void setProductModelNumber(String productModelNumber) {
        this.productModelNumber = productModelNumber;
    }

    public Integer getMatchSign() {
        return matchSign;
    }

    public void setMatchSign(Integer matchSign) {
        this.matchSign = matchSign;
    }

    public Integer getProStyleId() {
        return proStyleId;
    }

    public void setProStyleId(Integer proStyleId) {
        this.proStyleId = proStyleId;
    }

    public String getBindParentProductId() {
        return bindParentProductId;
    }

    public void setBindParentProductId(String bindParentProductId) {
        this.bindParentProductId = bindParentProductId;
    }

    public String getSplitTexturesChooseInfo() {
        return splitTexturesChooseInfo;
    }

    public void setSplitTexturesChooseInfo(String splitTexturesChooseInfo) {
        this.splitTexturesChooseInfo = splitTexturesChooseInfo;
    }

    public Integer getModelProductId() {
        return modelProductId;
    }

    public void setModelProductId(Integer modelProductId) {
        this.modelProductId = modelProductId;
    }

    public Integer getIsMainProduct() {
        return isMainProduct;
    }

    public void setIsMainProduct(Integer isMainProduct) {
        this.isMainProduct = isMainProduct;
    }

    public String getPlanGroupId() {
        return planGroupId;
    }

    public void setPlanGroupId(String planGroupId) {
        this.planGroupId = planGroupId;
    }

    public Integer getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Integer productGroupId) {
        this.productGroupId = productGroupId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getWallOrientation() {
        return wallOrientation;
    }

    public void setWallOrientation(String wallOrientation) {
        this.wallOrientation = wallOrientation;
    }

    public String getWallType() {
        return wallType;
    }

    public void setWallType(String wallType) {
        this.wallType = wallType;
    }

    public Integer getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Integer isStandard) {
        this.isStandard = isStandard;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getRegionMark() {
        return regionMark;
    }

    public void setRegionMark(String regionMark) {
        this.regionMark = regionMark;
    }

    public String getMeasureCode() {
        return measureCode;
    }

    public void setMeasureCode(String measureCode) {
        this.measureCode = measureCode;
    }

    public Integer getIsMainStructureProduct() {
        return isMainStructureProduct;
    }

    public void setIsMainStructureProduct(Integer isMainStructureProduct) {
        this.isMainStructureProduct = isMainStructureProduct;
    }

    public Integer getIsGroupReplaceWay() {
        return isGroupReplaceWay;
    }

    public void setIsGroupReplaceWay(Integer isGroupReplaceWay) {
        this.isGroupReplaceWay = isGroupReplaceWay;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorcId) {
        colorId = colorId;
    }

    public String getProductStyleIdInfo() {
        return productStyleIdInfo;
    }

    public void setProductStyleIdInfo(String productStyleIdInfo) {
        this.productStyleIdInfo = productStyleIdInfo;
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

    public String getBigTypeValue() {
        return bigTypeValue;
    }

    public void setBigTypeValue(String bigTypeValue) {
        this.bigTypeValue = bigTypeValue;
    }

    public Integer getSmallTypeValue() {
        return smallTypeValue;
    }

    public void setSmallTypeValue(Integer smallTypeValue) {
        this.smallTypeValue = smallTypeValue;
    }

    public Integer getBmSmallTypeValue() {
        return bmSmallTypeValue;
    }

    public void setBmSmallTypeValue(Integer bmSmallTypeValue) {
        this.bmSmallTypeValue = bmSmallTypeValue;
    }

    public String getProductLength() {
        return productLength;
    }

    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    public String getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    public String getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(String minHeight) {
        this.minHeight = minHeight;
    }

    public String getFullPaveLength() {
        return fullPaveLength;
    }

    public void setFullPaveLength(String fullPaveLength) {
        this.fullPaveLength = fullPaveLength;
    }

    public Integer getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(Integer productIndex) {
        this.productIndex = productIndex;
    }

    public String getBigTypeKey() {
        return bigTypeKey;
    }

    public void setBigTypeKey(String bigTypeKey) {
        this.bigTypeKey = bigTypeKey;
    }

    public String getSmallTypeKey() {
        return smallTypeKey;
    }

    public void setSmallTypeKey(String smallTypeKey) {
        this.smallTypeKey = smallTypeKey;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }
}
