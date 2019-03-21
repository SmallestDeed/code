package com.nork.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: PrepProductSearchInfo.java 
 * @Package com.nork.product.model
 * @Description:产品模块-预处理表(产品搜索)
 * @createAuthor pandajun 
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0   
 */
public class PrepProductSearchInfo  extends Mapper implements Serializable, Cloneable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  产品id  **/
	private Integer productId;
	/**  分类longCode  **/
	private String categoryLongCode;
	/**  拆分一级code  **/
	private String firstStageCode;
	/**  拆分二级code  **/
	private String secondStageCode;
	/**  拆分三级code  **/
	private String thirdStageCode;
	/**  拆分四级code  **/
	private String fourthStageCode;
	/**  拆分五级code  **/
	private String fifthStageCode;
	/**  大类value  **/
	private Integer bigTypeValue;
	/**  小类value  **/
	private Integer smallTypeValue;
	/**  品牌id  **/
	private Integer brandId;
	/**  空间类型  **/
	private String houseTypeValue;
	/**  是否是定制1定制0非定制  **/
	private Integer isCustom;
	/**  该定制产品对应的白膜id  **/
	private Integer baimoId;
	/**  关联空间id(为了查询是否是定制产品)  **/
	private Integer spaceCommonId;
	/**  关联样板房id(为了查询是否是定制产品)  **/
	private Integer designTempletId;
	/**  使用量(人气/该产品总使用量)  **/
	private Integer usageAmount;
	/**  账户余额  **/
	private Double price;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;
	/**查询条件:空间类型*/
	List<String> houseTypeList;
	/**查询条件:longCode*/
	private String categoryLongCodeLike;
	/**过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProductList;
	/**是否需要属性过滤*/
	private boolean hasFilterProps;
	/**是否需要属性排序*/
	private boolean hasOrderProps;
	/**点击的产品对应的初始化样板房白膜id*/
	private Integer baimoProductId;
	/**过滤小类(not in)*/
	private List<Integer> excludeSmallTypeValue;
	/**产品长度*/
	private Integer productLength;
	/**产品宽度*/
	private Integer productWidth;
	/**产品高度*/
	private Integer productHeight;
	/**查询条件宽度(小于或等于)*/
	private Integer lessProductWidth;
	/**查询条件长度(小于或等于)*/
	private Integer lessProductLength;
	/**baimoIdList*/
	private List<Integer> baimoIdList;
	/**搜索条件:型号/品牌名*/
	private String productModelOrBrandName;
	/**是否是背景墙*/
	private boolean isBeijing;
	/**是否是伸缩产品*/
	private boolean isStretch;
	/**背景墙长过滤条件(start)*/
	private Integer beijingLengthStart;
	/**背景墙长过滤条件(end)*/
	private Integer beijingLengthEnd;
	/**背景墙高过滤条件*/
	private Integer beijingHeight;
	/**查询条件:产品状态*/
	private Integer productStatus;
	/**查询条件:是否是天花*/
	private boolean isTianh;
	/**优先排序的小类,优先级仅次于定制/非定制*/
	private List<Integer> sortSmallTypeValueList;
	/**userId:用于查询收藏状态*/
	private Integer userId;
	/**无品牌id(用于单品搜索,查询条件)*/
	private Integer brandIdWuPinPai;
	/**授权码过滤查不出产品是否显示无品牌*/
	private boolean isShowWu = false;
	/**黑名单搜索条件*/
	private List<PrepProductSearchInfo> prepProductSearchInfoListForBlackList;
	/**小类过滤查询条件*/
	private List<Integer> smallTypeValueList;
	/**产品编码*/
	private String productCode;
	/**品牌名称*/
	private String brandName;
	/**产品型号*/
	private String productModel;
	/**属性过滤查询条件(copy from productCategoryRel)*/
	private List<String> attributeConditionList;
	/** 产品属性数量 **/
	private Integer attributeConditionSize;
	/**查询条件:是否是内部用户*/
	private String isInternalUser;
	/**
	 * 20170627-xiaoxc-add
	 * 款式Id
	 * 区域标识
	 * 尺寸代码
	 * 是否是标准
	 * 产品天花布局过滤
	 * */
	private Integer styleId;
	private String regionMark;
	private String measureCode;
	private Integer isStandard;
	private String productSmallpoxIdentify;
	/**
	 * 背景墙款式id搜索条件
	 */
	private Integer beijingStyleId;
	
	//缩放  
	private boolean isShrink;

	//搜索产品过滤信息集合
	private List<ProductSearchInfoModel> productInfoModelList;
	//是否根据productInfoModelList过滤
	private boolean isProductInfoFilter = false;

	private List<Integer> brandIds;

	//添加平台过滤条件，平台产品关联表表名
	private String platformProductTable;
	//添加平台过滤条件，平台id
	private Integer platformId;

	/**
	 * 是否走户型绘制的特殊搜索逻辑
	 * 现有逻辑:
	 * 搜天花时,多搜出有材质的扣板天花
	 */
	private boolean isFromDraw = false;

	/**
	 * add by xiaoxc_20180314
	 * 天花截面数据
	 */
	private String ceilingCrossSectionData;

	public String getCeilingCrossSectionData() {
		return ceilingCrossSectionData;
	}

	public void setCeilingCrossSectionData(String ceilingCrossSectionData) {
		this.ceilingCrossSectionData = ceilingCrossSectionData;
	}
	
	public boolean isFromDraw() {
		return isFromDraw;
	}

	public void setFromDraw(boolean isFromDraw) {
		this.isFromDraw = isFromDraw;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getPlatformProductTable() {
		return platformProductTable;
	}

	public void setPlatformProductTable(String platformProductTable) {
		this.platformProductTable = platformProductTable;
	}

	public List<Integer> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Integer> brandIds) {
		this.brandIds = brandIds;
	}

	public List<ProductSearchInfoModel> getProductInfoModelList() {
		return productInfoModelList;
	}

	public void setProductInfoModelList(List<ProductSearchInfoModel> productInfoModelList) {
		this.productInfoModelList = productInfoModelList;
	}

	public boolean isProductInfoFilter() {
		return isProductInfoFilter;
	}

	public void setProductInfoFilter(boolean productInfoFilter) {
		isProductInfoFilter = productInfoFilter;
	}

	/**
	 * 布局标识List搜索条件
	 */
	private List<String> identifyList;
	
	public List<String> getIdentifyList() {
		return identifyList;
	}

	public void setIdentifyList(List<String> identifyList) {
		this.identifyList = identifyList;
	}

	public boolean isShrink() {
		return isShrink;
	}

	public void setShrink(boolean isShrink) {
		this.isShrink = isShrink;
	}

	public String getProductSmallpoxIdentify() {
		return productSmallpoxIdentify;
	}

	public void setProductSmallpoxIdentify(String productSmallpoxIdentify) {
		this.productSmallpoxIdentify = productSmallpoxIdentify;
	}

	public Integer getBeijingStyleId() {
		return beijingStyleId;
	}

	public void setBeijingStyleId(Integer beijingStyleId) {
		this.beijingStyleId = beijingStyleId;
	}

	public Integer getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
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

	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}

	public Integer getAttributeConditionSize() {
		return attributeConditionSize;
	}

	public void setAttributeConditionSize(Integer attributeConditionSize) {
		this.attributeConditionSize = attributeConditionSize;
	}

	public List<String> getAttributeConditionList() {
		return attributeConditionList;
	}

	public void setAttributeConditionList(List<String> attributeConditionList) {
		this.attributeConditionList = attributeConditionList;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<Integer> getSmallTypeValueList() {
		return smallTypeValueList;
	}

	public void setSmallTypeValueList(List<Integer> smallTypeValueList) {
		this.smallTypeValueList = smallTypeValueList;
	}

	public List<PrepProductSearchInfo> getPrepProductSearchInfoListForBlackList() {
		return prepProductSearchInfoListForBlackList;
	}

	public void setPrepProductSearchInfoListForBlackList(
			List<PrepProductSearchInfo> prepProductSearchInfoListForBlackList) {
		this.prepProductSearchInfoListForBlackList = prepProductSearchInfoListForBlackList;
	}

	public boolean isShowWu() {
		return isShowWu;
	}

	public void setShowWu(boolean isShowWu) {
		this.isShowWu = isShowWu;
	}

	public Integer getBrandIdWuPinPai() {
		return brandIdWuPinPai;
	}

	public void setBrandIdWuPinPai(Integer brandIdWuPinPai) {
		this.brandIdWuPinPai = brandIdWuPinPai;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getSortSmallTypeValueList() {
		return sortSmallTypeValueList;
	}

	public void setSortSmallTypeValueList(List<Integer> sortSmallTypeValueList) {
		this.sortSmallTypeValueList = sortSmallTypeValueList;
	}

	public boolean isStretch() {
		return isStretch;
	}

	public void setStretch(boolean stretch) {
		this.isStretch = stretch;
	}

	public boolean isTianh() {
		return isTianh;
	}

	public void setTianh(boolean isTianh) {
		this.isTianh = isTianh;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getBeijingLengthStart() {
		return beijingLengthStart;
	}

	public void setBeijingLengthStart(Integer beijingLengthStart) {
		this.beijingLengthStart = beijingLengthStart;
	}

	public Integer getBeijingLengthEnd() {
		return beijingLengthEnd;
	}

	public void setBeijingLengthEnd(Integer beijingLengthEnd) {
		this.beijingLengthEnd = beijingLengthEnd;
	}

	public Integer getBeijingHeight() {
		return beijingHeight;
	}

	public void setBeijingHeight(Integer beijingHeight) {
		this.beijingHeight = beijingHeight;
	}

	public boolean isBeijing() {
		return isBeijing;
	}

	public void setBeijing(boolean isBeijing) {
		this.isBeijing = isBeijing;
	}

	public String getProductModelOrBrandName() {
		return productModelOrBrandName;
	}

	public void setProductModelOrBrandName(String productModelOrBrandName) {
		this.productModelOrBrandName = productModelOrBrandName;
	}

	public List<Integer> getBaimoIdList() {
		return baimoIdList;
	}

	public void setBaimoIdList(List<Integer> baimoIdList) {
		this.baimoIdList = baimoIdList;
	}

	public Integer getLessProductWidth() {
		return lessProductWidth;
	}

	public void setLessProductWidth(Integer lessProductWidth) {
		this.lessProductWidth = lessProductWidth;
	}

	public Integer getLessProductLength() {
		return lessProductLength;
	}

	public void setLessProductLength(Integer lessProductLength) {
		this.lessProductLength = lessProductLength;
	}

	public Integer getProductLength() {
		return productLength;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	public Integer getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(Integer productWidth) {
		this.productWidth = productWidth;
	}

	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	public List<Integer> getExcludeSmallTypeValue() {
		return excludeSmallTypeValue;
	}

	public void setExcludeSmallTypeValue(List<Integer> excludeSmallTypeValue) {
		this.excludeSmallTypeValue = excludeSmallTypeValue;
	}

	public Integer getBaimoProductId() {
		return baimoProductId;
	}

	public void setBaimoProductId(Integer baimoProductId) {
		this.baimoProductId = baimoProductId;
	}

	public boolean isHasOrderProps() {
		return hasOrderProps;
	}

	public void setHasOrderProps(boolean hasOrderProps) {
		this.hasOrderProps = hasOrderProps;
	}

	public boolean isHasFilterProps() {
		return hasFilterProps;
	}

	public void setHasFilterProps(boolean hasFilterProps) {
		this.hasFilterProps = hasFilterProps;
	}

	public List<BaseProduct> getBaseProductList() {
		return baseProductList;
	}

	public void setBaseProductList(List<BaseProduct> baseProductList) {
		this.baseProductList = baseProductList;
	}

	public String getCategoryLongCodeLike() {
		return categoryLongCodeLike;
	}

	public void setCategoryLongCodeLike(String categoryLongCodeLike) {
		this.categoryLongCodeLike = categoryLongCodeLike;
	}

	public List<String> getHouseTypeList() {
		return houseTypeList;
	}

	public void setHouseTypeList(List<String> houseTypeList) {
		this.houseTypeList = houseTypeList;
	}

	public  Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public  String getCategoryLongCode() {
		return categoryLongCode;
	}
	public void setCategoryLongCode(String categoryLongCode){
		this.categoryLongCode = categoryLongCode;
	}
	public  String getFirstStageCode() {
		return firstStageCode;
	}
	public void setFirstStageCode(String firstStageCode){
		this.firstStageCode = firstStageCode;
	}
	public  String getSecondStageCode() {
		return secondStageCode;
	}
	public void setSecondStageCode(String secondStageCode){
		this.secondStageCode = secondStageCode;
	}
	public  String getThirdStageCode() {
		return thirdStageCode;
	}
	public void setThirdStageCode(String thirdStageCode){
		this.thirdStageCode = thirdStageCode;
	}
	public  String getFourthStageCode() {
		return fourthStageCode;
	}
	public void setFourthStageCode(String fourthStageCode){
		this.fourthStageCode = fourthStageCode;
	}
	public  String getFifthStageCode() {
		return fifthStageCode;
	}
	public void setFifthStageCode(String fifthStageCode){
		this.fifthStageCode = fifthStageCode;
	}
	public  Integer getBigTypeValue() {
		return bigTypeValue;
	}
	public void setBigTypeValue(Integer bigTypeValue){
		this.bigTypeValue = bigTypeValue;
	}
	public  Integer getSmallTypeValue() {
		return smallTypeValue;
	}
	public void setSmallTypeValue(Integer smallTypeValue){
		this.smallTypeValue = smallTypeValue;
	}
	public  Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId){
		this.brandId = brandId;
	}
	public  String getHouseTypeValue() {
		return houseTypeValue;
	}
	public void setHouseTypeValue(String houseTypeValue){
		this.houseTypeValue = houseTypeValue;
	}
	public  Integer getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(Integer isCustom){
		this.isCustom = isCustom;
	}
	public  Integer getBaimoId() {
		return baimoId;
	}
	public void setBaimoId(Integer baimoId){
		this.baimoId = baimoId;
	}
	public  Integer getSpaceCommonId() {
		return spaceCommonId;
	}
	public void setSpaceCommonId(Integer spaceCommonId){
		this.spaceCommonId = spaceCommonId;
	}
	public  Integer getDesignTempletId() {
		return designTempletId;
	}
	public void setDesignTempletId(Integer designTempletId){
		this.designTempletId = designTempletId;
	}
	public  Integer getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(Integer usageAmount){
		this.usageAmount = usageAmount;
	}
	public  Double getPrice() {
		return price;
	}
	public void setPrice(Double price){
		this.price = price;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


   @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PrepProductSearchInfo other = (PrepProductSearchInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getCategoryLongCode() == null ? other.getCategoryLongCode() == null : this.getCategoryLongCode().equals(other.getCategoryLongCode()))
            && (this.getFirstStageCode() == null ? other.getFirstStageCode() == null : this.getFirstStageCode().equals(other.getFirstStageCode()))
            && (this.getSecondStageCode() == null ? other.getSecondStageCode() == null : this.getSecondStageCode().equals(other.getSecondStageCode()))
            && (this.getThirdStageCode() == null ? other.getThirdStageCode() == null : this.getThirdStageCode().equals(other.getThirdStageCode()))
            && (this.getFourthStageCode() == null ? other.getFourthStageCode() == null : this.getFourthStageCode().equals(other.getFourthStageCode()))
            && (this.getFifthStageCode() == null ? other.getFifthStageCode() == null : this.getFifthStageCode().equals(other.getFifthStageCode()))
            && (this.getBigTypeValue() == null ? other.getBigTypeValue() == null : this.getBigTypeValue().equals(other.getBigTypeValue()))
            && (this.getSmallTypeValue() == null ? other.getSmallTypeValue() == null : this.getSmallTypeValue().equals(other.getSmallTypeValue()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getHouseTypeValue() == null ? other.getHouseTypeValue() == null : this.getHouseTypeValue().equals(other.getHouseTypeValue()))
            && (this.getIsCustom() == null ? other.getIsCustom() == null : this.getIsCustom().equals(other.getIsCustom()))
            && (this.getBaimoId() == null ? other.getBaimoId() == null : this.getBaimoId().equals(other.getBaimoId()))
            && (this.getSpaceCommonId() == null ? other.getSpaceCommonId() == null : this.getSpaceCommonId().equals(other.getSpaceCommonId()))
            && (this.getDesignTempletId() == null ? other.getDesignTempletId() == null : this.getDesignTempletId().equals(other.getDesignTempletId()))
            && (this.getUsageAmount() == null ? other.getUsageAmount() == null : this.getUsageAmount().equals(other.getUsageAmount()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
            && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getCategoryLongCode() == null) ? 0 : getCategoryLongCode().hashCode());
        result = prime * result + ((getFirstStageCode() == null) ? 0 : getFirstStageCode().hashCode());
        result = prime * result + ((getSecondStageCode() == null) ? 0 : getSecondStageCode().hashCode());
        result = prime * result + ((getThirdStageCode() == null) ? 0 : getThirdStageCode().hashCode());
        result = prime * result + ((getFourthStageCode() == null) ? 0 : getFourthStageCode().hashCode());
        result = prime * result + ((getFifthStageCode() == null) ? 0 : getFifthStageCode().hashCode());
        result = prime * result + ((getBigTypeValue() == null) ? 0 : getBigTypeValue().hashCode());
        result = prime * result + ((getSmallTypeValue() == null) ? 0 : getSmallTypeValue().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getHouseTypeValue() == null) ? 0 : getHouseTypeValue().hashCode());
        result = prime * result + ((getIsCustom() == null) ? 0 : getIsCustom().hashCode());
        result = prime * result + ((getBaimoId() == null) ? 0 : getBaimoId().hashCode());
        result = prime * result + ((getSpaceCommonId() == null) ? 0 : getSpaceCommonId().hashCode());
        result = prime * result + ((getDesignTempletId() == null) ? 0 : getDesignTempletId().hashCode());
        result = prime * result + ((getUsageAmount() == null) ? 0 : getUsageAmount().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public PrepProductSearchInfo copy(){
       PrepProductSearchInfo obj =  new PrepProductSearchInfo();
       obj.setProductId(this.productId);
       obj.setCategoryLongCode(this.categoryLongCode);
       obj.setFirstStageCode(this.firstStageCode);
       obj.setSecondStageCode(this.secondStageCode);
       obj.setThirdStageCode(this.thirdStageCode);
       obj.setFourthStageCode(this.fourthStageCode);
       obj.setFifthStageCode(this.fifthStageCode);
       obj.setBigTypeValue(this.bigTypeValue);
       obj.setSmallTypeValue(this.smallTypeValue);
       obj.setBrandId(this.brandId);
       obj.setHouseTypeValue(this.houseTypeValue);
       obj.setIsCustom(this.isCustom);
       obj.setBaimoId(this.baimoId);
       obj.setSpaceCommonId(this.spaceCommonId);
       obj.setDesignTempletId(this.designTempletId);
       obj.setUsageAmount(this.usageAmount);
       obj.setPrice(this.price);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setNuma1(this.numa1);
       obj.setNuma2(this.numa2);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("productId",this.productId);
       map.put("categoryLongCode",this.categoryLongCode);
       map.put("firstStageCode",this.firstStageCode);
       map.put("secondStageCode",this.secondStageCode);
       map.put("thirdStageCode",this.thirdStageCode);
       map.put("fourthStageCode",this.fourthStageCode);
       map.put("fifthStageCode",this.fifthStageCode);
       map.put("bigTypeValue",this.bigTypeValue);
       map.put("smallTypeValue",this.smallTypeValue);
       map.put("brandId",this.brandId);
       map.put("houseTypeValue",this.houseTypeValue);
       map.put("isCustom",this.isCustom);
       map.put("baimoId",this.baimoId);
       map.put("spaceCommonId",this.spaceCommonId);
       map.put("designTempletId",this.designTempletId);
       map.put("usageAmount",this.usageAmount);
       map.put("price",this.price);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
    
    @Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
    
}
