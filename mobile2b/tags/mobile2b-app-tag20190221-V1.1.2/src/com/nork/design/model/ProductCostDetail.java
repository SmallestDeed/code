package com.nork.design.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/12/23.
 */
public class ProductCostDetail extends Mapper implements Serializable {

    /**
     * 产品ID
     **/
    private int productId;
    /**
     * 产品名称
     **/
    private String productName;
    /**
     * 产品图片路径
     **/
    private String productPicPath;
    /**
     * 产品原图路径
     **/
    private String productOriginalPicPath;
    /**
     * 品牌名称
     **/
    private String brandName;
    /**
     * 产品单价
     **/
    private BigDecimal unitPrice;
    /**
     * 产品数量
     **/
    private Integer count;
    /**
     * 总价
     **/
    private BigDecimal totalPrice;
    /**
     * 产品型号
     **/
    private String productModelNumber;
    /**
     * 产品规格
     **/
    private String productSpec;
    /**
     * 产品描述
     **/
    private String productDesc;
    /**
     * 产品价格单位
     **/
    private String productUnit;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品挂节点信息
     */
    private String posIndexPath;

    private Integer planProductId;//方案产品id
    private String categoryCode;//产品分类编码
    private String smallCategoryCode;//产品小分类编码
    private Integer isMainProduct;//是否是主产品
    private Integer spaceCommonId;//空间id
    private String productTypeValue;//产品类别value···大类
    private String productSmallTypeValue;//产品类别value···小类

    private String sourcePlanGroupId;//设计方案组合ID
    private Integer sourceProductGroupId;//产品组合ID
    private String sourceGroupProductUniqueId;//组合产品唯一标识
    private Integer sourceGroupProductId;//组合id
    private String sourceGroupProductCode;//组合code

    private String sourceSplitTexturesChooseInfo;//默认材质信息

    private Integer isStandard;//		<!-- 描述(区域、尺寸代码) -->
    private String regionMark;//		<!-- 区域标识 -->
    private Integer styleId;//		<!-- 款式id -->
    private String measureCode;//	<!-- 尺寸代码 -->
    private Integer structureId;//结构id

    /**
     * 用这个值来判断bgwall应该赋什么值
     */
    private String valuekey;
    /**
     * 前段根据这个值来判断墙面是否可以删除
     **/
    private Integer bgWall;
    /**
     * 是否做了材质替换(0:否;1:是)
     */
    private Integer isReplaceTexture;

    //关联样板房id
    private Integer designTempletId;
    //产品分类id
    private Integer proCategoryId;
    //产品分类长编码
    private String proCategoryLongCode;
    //
    private String att2;
    //
    private String typeValue;
    //
    private Integer brandId;
    //产品是否保密
    private Integer secrecyFlag;
    //公司id
    private Integer companyId;
    //产品分类小类valuekey值
    private String typeValueKey;
    //产品平台关联表的产品分类字符串
    private String styleIdStr;
    //产品三级分类
    private String thirdStageCode;
    //产品四级分类
    private String fourthStageCode;

    public String getThirdStageCode() {
        return thirdStageCode;
    }

    public void setThirdStageCode(String thirdStageCode) {
        this.thirdStageCode = thirdStageCode;
    }

    public String getFourthStageCode() {
        return fourthStageCode;
    }

    public void setFourthStageCode(String fourthStageCode) {
        this.fourthStageCode = fourthStageCode;
    }

    public String getStyleIdStr() {
        return styleIdStr;
    }

    public void setStyleIdStr(String styleIdStr) {
        this.styleIdStr = styleIdStr;
    }

    public String getTypeValueKey() {
        return typeValueKey;
    }

    public void setTypeValueKey(String typeValueKey) {
        this.typeValueKey = typeValueKey;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSecrecyFlag() {
        return secrecyFlag;
    }

    public void setSecrecyFlag(Integer secrecyFlag) {
        this.secrecyFlag = secrecyFlag;
    }

    public Integer getDesignTempletId() {
        return designTempletId;
    }

    public void setDesignTempletId(Integer designTempletId) {
        this.designTempletId = designTempletId;
    }

    public Integer getProCategoryId() {
        return proCategoryId;
    }

    public void setProCategoryId(Integer proCategoryId) {
        this.proCategoryId = proCategoryId;
    }

    public String getProCategoryLongCode() {
        return proCategoryLongCode;
    }

    public void setProCategoryLongCode(String proCategoryLongCode) {
        this.proCategoryLongCode = proCategoryLongCode;
    }

    public Integer getIsReplaceTexture() {
        return isReplaceTexture;
    }

    public void setIsReplaceTexture(Integer isReplaceTexture) {
        this.isReplaceTexture = isReplaceTexture;
    }

    public String getValuekey() {
        return valuekey;
    }

    public void setValuekey(String valuekey) {
        this.valuekey = valuekey;
    }

    public Integer getBgWall() {
        return bgWall;
    }

    public void setBgWall(Integer bgWall) {
        this.bgWall = bgWall;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Integer isStandard) {
        this.isStandard = isStandard;
    }

    public String getRegionMark() {
        return regionMark;
    }

    public void setRegionMark(String regionMark) {
        this.regionMark = regionMark;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getMeasureCode() {
        return measureCode;
    }

    public void setMeasureCode(String measureCode) {
        this.measureCode = measureCode;
    }

    public String getSourceSplitTexturesChooseInfo() {
        return sourceSplitTexturesChooseInfo;
    }

    public void setSourceSplitTexturesChooseInfo(String sourceSplitTexturesChooseInfo) {
        this.sourceSplitTexturesChooseInfo = sourceSplitTexturesChooseInfo;
    }

    public Integer getPlanProductId() {
        return planProductId;
    }

    public void setPlanProductId(Integer planProductId) {
        this.planProductId = planProductId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getSmallCategoryCode() {
        return smallCategoryCode;
    }

    public void setSmallCategoryCode(String smallCategoryCode) {
        this.smallCategoryCode = smallCategoryCode;
    }

    public Integer getIsMainProduct() {
        return isMainProduct;
    }

    public void setIsMainProduct(Integer isMainProduct) {
        this.isMainProduct = isMainProduct;
    }

    public Integer getSpaceCommonId() {
        return spaceCommonId;
    }

    public void setSpaceCommonId(Integer spaceCommonId) {
        this.spaceCommonId = spaceCommonId;
    }

    public String getProductTypeValue() {
        return productTypeValue;
    }

    public void setProductTypeValue(String productTypeValue) {
        this.productTypeValue = productTypeValue;
    }

    public String getProductSmallTypeValue() {
        return productSmallTypeValue;
    }

    public void setProductSmallTypeValue(String productSmallTypeValue) {
        this.productSmallTypeValue = productSmallTypeValue;
    }

    public String getSourcePlanGroupId() {
        return sourcePlanGroupId;
    }

    public void setSourcePlanGroupId(String sourcePlanGroupId) {
        this.sourcePlanGroupId = sourcePlanGroupId;
    }

    public Integer getSourceProductGroupId() {
        return sourceProductGroupId;
    }

    public void setSourceProductGroupId(Integer sourceProductGroupId) {
        this.sourceProductGroupId = sourceProductGroupId;
    }

    public String getSourceGroupProductUniqueId() {
        return sourceGroupProductUniqueId;
    }

    public void setSourceGroupProductUniqueId(String sourceGroupProductUniqueId) {
        this.sourceGroupProductUniqueId = sourceGroupProductUniqueId;
    }

    public Integer getSourceGroupProductId() {
        return sourceGroupProductId;
    }

    public void setSourceGroupProductId(Integer sourceGroupProductId) {
        this.sourceGroupProductId = sourceGroupProductId;
    }

    public String getSourceGroupProductCode() {
        return sourceGroupProductCode;
    }

    public void setSourceGroupProductCode(String sourceGroupProductCode) {
        this.sourceGroupProductCode = sourceGroupProductCode;
    }

    public String getPosIndexPath() {
        return posIndexPath;
    }

    public void setPosIndexPath(String posIndexPath) {
        this.posIndexPath = posIndexPath;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPicPath() {
        return productPicPath;
    }

    public void setProductPicPath(String productPicPath) {
        this.productPicPath = productPicPath;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductOriginalPicPath() {
        return productOriginalPicPath;
    }

    public void setProductOriginalPicPath(String productOriginalPicPath) {
        this.productOriginalPicPath = productOriginalPicPath;
    }

    public String getProductModelNumber() {
        return productModelNumber;
    }

    public void setProductModelNumber(String productModelNumber) {
        this.productModelNumber = productModelNumber;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
