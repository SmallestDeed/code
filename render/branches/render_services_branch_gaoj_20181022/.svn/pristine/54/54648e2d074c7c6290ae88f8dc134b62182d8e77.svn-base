package com.nork.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.common.model.Mapper;
import com.nork.productprops.model.ProductProps;

/**   
 * @Title: ProductCategoryRel.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品与产品类目关联
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:50:47
 * @version V1.0   
 */
public class ProductCategoryRel  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
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
	/**  产品id  **/
	private Integer productId;
	/**  产品分类id  **/
	private Integer categoryId;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  字符备用3  **/
	private String att3;
	/**  字符备用4  **/
	private String att4;
	/**  字符备用5  **/
	private String att5;
	/**  字符备用6  **/
	private String att6;
	/**  时间备用1  **/
	private Date dateAtt1;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  整数备用1  **/
	private Integer numAtt1;
	/**  整数备用2  **/
	private Integer numAtt2;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	/** 类目CODE **/
	private String categoryCode;
	/** 商品名称 **/
	private String productName;
	/** 商品CODE **/
	private String productCode;
	/** 产品所属房型 **/
	private String houseTypeValues;
	/** 分类ID集合 **/
	private List<String> categoryIdList;
    /** 用户ID **/
    private Integer userId;
	/** 样板房id **/
	private Integer designTempletId;
	/** 样板房产品ID **/
	private Integer designProductId;
	/** 厂商品牌 **/
	private List<String> brandIds;
	/** 是否仅显示推荐产品 **/
	private Boolean onlyShowRecommend;
	/** 是否显示推荐产品 **/
	private Boolean exceptRecommend;
	/** 空间ID **/
	private Integer spaceCommonId;
	/** 选中产品中的样板房产品id **/
	private List<String> templateProductId;
	/** 选中产品中的大类 **/
	private String productTypeValue;
	/** 选中产品中的小类 **/
	private Integer productSmallTypeValue;
	//序列号配置品牌ids
	private List<String> configBrandIdList;
	//序列号配置大类values
	private List<String> configTypeValueList;
	//序列号配置小类values
	private List<String> configSmallTypeIdList;
	//序列号配置产品ids
	private List<String> configProductIdList;
	//设备终端号(查询产品时过滤产品的参数)
	private String terminalImei;
	//特殊产品分类
	private String specialProductType;
	//产品小分类
	private String productSmallTypeKey;
	/**  产品长度  **/
	private String productLength;
	/**  产品宽度  **/
	private String productWidth;
	/**  产品高度  **/
	private String productHeight;
	/*产品小类黑名单*/
	private Set<String> blacklistSet;
	/**  产品型号  **/
	private String productModelNumber;
	/**  显示背景墙 **/
	private boolean showBgWall;
	/**  过滤背景墙开始长度 **/
	private Integer startLength;
	/**  过滤背景墙截止长度 **/
	private Integer endLength;
	/**  过滤背景墙实际高度 **/
	private String bgWallHeight;
	/** 产品多个属性条件 **/
	private List<String> attributeConditionList;
	/** 产品属性数量 **/
	private Integer attributeConditionSize;
	/** 白模背景墙长高是否只值,true没有值 **/
	private boolean isValue;
	//是否过滤或排序（过滤filter,排序order）
	private String isFilterOrder;
	//排序属性集合
	private List<ProductProps> propsOrderList;
	
	private List<String> houseTypeList;
	/*过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProduct;
	/**longCode分解第一级code(查询条件)*/
	private String firstStageCode;
	/**longCode分解第二级code(查询条件)*/
	private String secondStageCode;
	/**longCode分解第三级code(查询条件)*/
	private String thirdStageCode;
	/**longCode分解第四级code(查询条件)*/
	private String fourthStageCode;
	/**longCode分解第五级code(查询条件)*/
	private String fifthStageCode;
	//窗帘、一字淋浴屏
	private boolean isStretch;
	//背景墙
	private boolean isBeijing;
	/**小类valuekeyList(查询条件:用来查询多个小类)*/
	private List<String> smallTypeList;
	/**小类valueList(查询条件:用来查询多个小类)*/
	private List<Integer> smallTypeValueList;
	/**排序用的小类list(对应小类排前面)*/
	private List<Integer> sortSmallTypeValueList;
	/*品牌行业list*/
	private List<BrandIndustryModel> brandIndustryModelList;

	public List<BrandIndustryModel> getBrandIndustryModelList() {
		return brandIndustryModelList;
	}

	public void setBrandIndustryModelList(List<BrandIndustryModel> brandIndustryModelList) {
		this.brandIndustryModelList = brandIndustryModelList;
	}

	public List<Integer> getSortSmallTypeValueList() {
		return sortSmallTypeValueList;
	}

	public void setSortSmallTypeValueList(List<Integer> sortSmallTypeValueList) {
		this.sortSmallTypeValueList = sortSmallTypeValueList;
	}

	public List<Integer> getSmallTypeValueList() {
		return smallTypeValueList;
	}

	public void setSmallTypeValueList(List<Integer> smallTypeValueList) {
		this.smallTypeValueList = smallTypeValueList;
	}

	public List<String> getSmallTypeList() {
		return smallTypeList;
	}

	public void setSmallTypeList(List<String> smallTypeList) {
		this.smallTypeList = smallTypeList;
	}

	public boolean isStretch() {
		return isStretch;
	}

	public void setStretch(boolean stretch) {
		isStretch = stretch;
	}

	public boolean isBeijing() {
		return isBeijing;
	}

	public void setBeijing(boolean beijing) {
		isBeijing = beijing;
	}

	public String getFirstStageCode() {
		return firstStageCode;
	}

	public void setFirstStageCode(String firstStageCode) {
		this.firstStageCode = firstStageCode;
	}

	public String getSecondStageCode() {
		return secondStageCode;
	}

	public void setSecondStageCode(String secondStageCode) {
		this.secondStageCode = secondStageCode;
	}

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

	public String getFifthStageCode() {
		return fifthStageCode;
	}

	public void setFifthStageCode(String fifthStageCode) {
		this.fifthStageCode = fifthStageCode;
	}

	public List<BaseProduct> getBaseProduct() {
		return baseProduct;
	}

	public void setBaseProduct(List<BaseProduct> baseProduct) {
		this.baseProduct = baseProduct;
	}

	public List<String> getHouseTypeList() {
		return houseTypeList;
	}

	public void setHouseTypeList(List<String> houseTypeList) {
		this.houseTypeList = houseTypeList;
	}

	public List<ProductProps> getPropsOrderList() {
		return propsOrderList;
	}

	public void setPropsOrderList(List<ProductProps> propsOrderList) {
		this.propsOrderList = propsOrderList;
	}

	public String getIsFilterOrder() {
		return isFilterOrder;
	}

	public void setIsFilterOrder(String isFilterOrder) {
		this.isFilterOrder = isFilterOrder;
	}

	public boolean isValue() {
		return isValue;
	}

	public void setValue(boolean isValue) {
		this.isValue = isValue;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBgWallHeight() {
		return bgWallHeight;
	}

	public void setBgWallHeight(String bgWallHeight) {
		this.bgWallHeight = bgWallHeight;
	}

	public Integer getStartLength() {
		return startLength;
	}

	public void setStartLength(Integer startLength) {
		this.startLength = startLength;
	}

	public Integer getEndLength() {
		return endLength;
	}

	public void setEndLength(Integer endLength) {
		this.endLength = endLength;
	}

	public boolean getShowBgWall() {
		return showBgWall;
	}

	public void setShowBgWall(boolean showBgWall) {
		this.showBgWall = showBgWall;
	}

	public Set<String> getBlacklistSet() {
		return blacklistSet;
	}

	public void setBlacklistSet(Set<String> blacklistSet) {
		this.blacklistSet = blacklistSet;
	}
	
	public String getProductModelNumber() {
		return productModelNumber;
	}

	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}

	private Integer templetId;
	
	public Integer getTempletId() {
		return templetId;
	}

	public void setTempletId(Integer templetId) {
		this.templetId = templetId;
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

	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	public String getProductSmallTypeKey() {
		return productSmallTypeKey;
	}

	public void setProductSmallTypeKey(String productSmallTypeKey) {
		this.productSmallTypeKey = productSmallTypeKey;
	}

	public String getTerminalImei() {
		return terminalImei;
	}

	public void setTerminalImei(String terminalImei) {
		this.terminalImei = terminalImei;
	}

	public List<String> getConfigBrandIdList() {
		return configBrandIdList;
	}

	public void setConfigBrandIdList(List<String> configBrandIdList) {
		this.configBrandIdList = configBrandIdList;
	}

	public List<String> getConfigTypeValueList() {
		return configTypeValueList;
	}

	public void setConfigTypeValueList(List<String> configTypeValueList) {
		this.configTypeValueList = configTypeValueList;
	}

	public List<String> getConfigSmallTypeIdList() {
		return configSmallTypeIdList;
	}

	public void setConfigSmallTypeIdList(List<String> configSmallTypeIdList) {
		this.configSmallTypeIdList = configSmallTypeIdList;
	}

	public List<String> getConfigProductIdList() {
		return configProductIdList;
	}

	public void setConfigProductIdList(List<String> configProductIdList) {
		this.configProductIdList = configProductIdList;
	}

	public Boolean getOnlyShowRecommend() {
		return onlyShowRecommend;
	}

	public void setOnlyShowRecommend(Boolean onlyShowRecommend) {
		this.onlyShowRecommend = onlyShowRecommend;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public String getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
	public  Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public  Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
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
	public  String getAtt3() {
		return att3;
	}
	public void setAtt3(String att3){
		this.att3 = att3;
	}
	public  String getAtt4() {
		return att4;
	}
	public void setAtt4(String att4){
		this.att4 = att4;
	}
	public  String getAtt5() {
		return att5;
	}
	public void setAtt5(String att5){
		this.att5 = att5;
	}
	public  String getAtt6() {
		return att6;
	}
	public void setAtt6(String att6){
		this.att6 = att6;
	}
	public  Date getDateAtt1() {
		return dateAtt1;
	}
	public void setDateAtt1(Date dateAtt1){
		this.dateAtt1 = dateAtt1;
	}
	public  Date getDateAtt2() {
		return dateAtt2;
	}
	public void setDateAtt2(Date dateAtt2){
		this.dateAtt2 = dateAtt2;
	}
	public  Integer getNumAtt1() {
		return numAtt1;
	}
	public void setNumAtt1(Integer numAtt1){
		this.numAtt1 = numAtt1;
	}
	public  Integer getNumAtt2() {
		return numAtt2;
	}
	public void setNumAtt2(Integer numAtt2){
		this.numAtt2 = numAtt2;
	}
	public  Double getNumAtt3() {
		return numAtt3;
	}
	public void setNumAtt3(Double numAtt3){
		this.numAtt3 = numAtt3;
	}
	public  Double getNumAtt4() {
		return numAtt4;
	}
	public void setNumAtt4(Double numAtt4){
		this.numAtt4 = numAtt4;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
   public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getHouseTypeValues() {
		return houseTypeValues;
	}
	public void setHouseTypeValues(String houseTypeValues) {
		this.houseTypeValues = houseTypeValues;
	}
	public List<String> getCategoryIdList() {
		return categoryIdList;
	}
	public void setCategoryIdList(List<String> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
	public Integer getDesignProductId() {
		return designProductId;
	}
	public void setDesignProductId(Integer designProductId) {
		this.designProductId = designProductId;
	}
	public Integer getDesignTempletId() {
		return designTempletId;
	}
	public void setDesignTempletId(Integer designTempletId) {
		this.designTempletId = designTempletId;
	}
	public List<String> getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(List<String> brandIds) {
		this.brandIds = brandIds;
	}

	public Boolean getExceptRecommend() {
		return exceptRecommend;
	}

	public void setExceptRecommend(Boolean exceptRecommend) {
		this.exceptRecommend = exceptRecommend;
	}

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}
	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}
	
	public List<String> getTemplateProductId() {
		return templateProductId;
	}

	public void setTemplateProductId(List<String> templateProductId) {
		this.templateProductId = templateProductId;
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
        ProductCategoryRel other = (ProductCategoryRel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getNumAtt1() == null ? other.getNumAtt1() == null : this.getNumAtt1().equals(other.getNumAtt1()))
            && (this.getNumAtt2() == null ? other.getNumAtt2() == null : this.getNumAtt2().equals(other.getNumAtt2()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCategoryCode() == null ? other.getCategoryCode() == null : this.getCategoryCode().equals(other.getCategoryCode()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getNumAtt1() == null) ? 0 : getNumAtt1().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCategoryCode() == null) ? 0 : getCategoryCode().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public ProductCategoryRel copy(){
       ProductCategoryRel obj =  new ProductCategoryRel();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setProductId(this.productId);
       obj.setCategoryId(this.categoryId);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setAtt3(this.att3);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setDateAtt1(this.dateAtt1);
       obj.setDateAtt2(this.dateAtt2);
       obj.setNumAtt1(this.numAtt1);
       obj.setNumAtt2(this.numAtt2);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);
       obj.setCategoryCode(this.categoryCode);
       obj.setProductName(this.productName);
       obj.setProductCode(this.productCode);
       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("productId",this.productId);
       map.put("categoryId",this.categoryId);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("att3",this.att3);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("dateAtt1",this.dateAtt1);
       map.put("dateAtt2",this.dateAtt2);
       map.put("numAtt1",this.numAtt1);
       map.put("numAtt2",this.numAtt2);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);
       map.put("categoryCode", categoryCode);
       map.put("productName", productName);
       map.put("productCode", productCode);
       return map;
    }
    
    //是否是内部用户
    private String isInternalUser;

	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}
}
