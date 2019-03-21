package com.nork.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;
import com.nork.productprops.model.ProductPropsSimple;

/**   
 * @Title: GroupProduct.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品组合主表
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
public class GroupProduct  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }
    /**  坐标txt 路径  **/
    private String locationPath;
    
    
    private Integer structureConfigFileId;
    private Integer productStructureId ;
    private String  structureName;
    private String  structureCode;
    private String  structureConfig;
    
	

	/**  组合编码  **/
	private String groupCode;
	/**  组合名  **/
	private String groupName;
	/**  组合类型 (所属房型) **/
	private Integer type;
	
	/** 结构id */
	private Integer structureId;
	/*组合状态
	 * 0:默认
	 * 1:上架
	 * 2:测试*/
	private Integer state;
	/**  排序  **/
	private Integer sorting;
	/**   封面id  **/
	private Integer picId;
	/**  组合编码  **/
	private String code;
	/** 产品组合位置信息 **/
	private String location;
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
	/** 组合价格  **/
	private Double groupPrice;
	/** 样板房id  **/
	private Integer designTempletId;
	/** 户型归属  **/
	private String houseTypeValues;

	
	/**  结构组标记  **/
	private String groupFlag;

	/**  组合宽  **/
	private String groupWidth;
	/**  组合长  **/
	private String groupLength;
	/**  组合高  **/
	private String groupHigh;
	/**
	 * 品牌id(关联base_brand表)
	 */
	private Integer brandId;
	/**
	 * 风格value(关联数据字典value)
	 */
	private Integer styleValue;
	/**
	 * 封面url
	 */
	private String picPath;
	/**产品风格*/
	private String productStyleIdInfo;
	
	/**产品风格(list)*/
	private List<String> productStyleInfoList;
	
	private String productStyle;
	
	/** 产品型号 **/
	private String productType;

	/**  组合类型  **/
	private Integer compositeType;

	/**创建者 Id**/
	private Integer userId;
	
	/**
	 * 主产品的过滤属性信息
	 */
	private List<ProductPropsSimple> productFilterPropList;
	
	public List<ProductPropsSimple> getProductFilterPropList() {
		return productFilterPropList;
	}

	public void setProductFilterPropList(List<ProductPropsSimple> productFilterPropList) {
		this.productFilterPropList = productFilterPropList;
	}

	public Integer getCompositeType() {
		return compositeType;
	}

	public void setCompositeType(Integer compositeType) {
		this.compositeType = compositeType;
	}

	/**
	 * 组合类型,
	 * 0:普通组合
	 * 1:一件装修组合
	 * 对应常量类:GroupProductTypeConstant
	 */
	private Integer groupType;
	
	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getStyleValue() {
		return styleValue;
	}

	public void setStyleValue(Integer styleValue) {
		this.styleValue = styleValue;
	}

	public String getStructureConfig() {
		return structureConfig;
	}

	public void setStructureConfig(String structureConfig) {
		this.structureConfig = structureConfig;
	}

 

	public Integer getStructureConfigFileId() {
		return structureConfigFileId;
	}

	public void setStructureConfigFileId(Integer structureConfigFileId) {
		this.structureConfigFileId = structureConfigFileId;
	}

	public Integer getProductStructureId() {
		return productStructureId;
	}

	public void setProductStructureId(Integer productStructureId) {
		this.productStructureId = productStructureId;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getStructureCode() {
		return structureCode;
	}

	public void setStructureCode(String structureCode) {
		this.structureCode = structureCode;
	}
	
	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	public void setId(Integer id) {
        this.id = id;
    }


	/*空间类型value*/
	private Integer spaceFunctionValue;
	/*组合面积value*/
	private Integer spaceAreaValue;
	/*空间面积*/
	private Integer spaceCommonArea;

	public Integer getSpaceCommonArea() {
		return spaceCommonArea;
	}

	public void setSpaceCommonArea(Integer spaceCommonArea) {
		this.spaceCommonArea = spaceCommonArea;
	}

	public String getGroupWidth() {
		return groupWidth;
	}

	public Integer getSpaceFunctionValue() {
		return spaceFunctionValue;
	}

	public void setSpaceFunctionValue(Integer spaceFunctionValue) {
		this.spaceFunctionValue = spaceFunctionValue;
	}

	public Integer getSpaceAreaValue() {
		return spaceAreaValue;
	}

	public void setSpaceAreaValue(Integer spaceAreaValue) {
		this.spaceAreaValue = spaceAreaValue;
	}

	public void setGroupWidth(String groupWidth) {
		this.groupWidth = groupWidth;
	}

	public String getGroupLength() {
		return groupLength;
	}

	public void setGroupLength(String groupLength) {
		this.groupLength = groupLength;
	}

	public String getGroupHigh() {
		return groupHigh;
	}

	public void setGroupHigh(String groupHigh) {
		this.groupHigh = groupHigh;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getHouseTypeValues() {
		return houseTypeValues;
	}

	public void setHouseTypeValues(String houseTypeValues) {
		this.houseTypeValues = houseTypeValues;
	}

	public Integer getStructureId() {
		return structureId;
	}

	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}

	//组合产品收藏状态1和0
    private Integer collectState;
    //组合产品列表
  	List<BaseProduct> groupProductList = new ArrayList<BaseProduct>();
	
  	private String brandName;
  	/**
  	 * 风格名称
  	 */
  	private String styleName;
  	
	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public List<BaseProduct> getGroupProductList() {
		return groupProductList;
	}

	public void setGroupProductList(List<BaseProduct> groupProductList) {
		this.groupProductList = groupProductList;
	}

	public Integer getDesignTempletId() {
		return designTempletId;
	}

	public void setDesignTempletId(Integer designTempletId) {
		this.designTempletId = designTempletId;
	}

	public Double getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(Double groupPrice) {
		this.groupPrice = groupPrice;
	}

	public  String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode){
		this.groupCode = groupCode;
	}
	public  String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	public  Integer getType() {
		return type;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public  Integer getState() {
		return state;
	}
	public void setState(Integer state){
		this.state = state;
	}
	public  Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting){
		this.sorting = sorting;
	}
	public  Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId){
		this.picId = picId;
	}
	public  String getCode() {
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
        GroupProduct other = (GroupProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupCode() == null ? other.getGroupCode() == null : this.getGroupCode().equals(other.getGroupCode()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getSorting() == null ? other.getSorting() == null : this.getSorting().equals(other.getSorting()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
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
        result = prime * result + ((getGroupCode() == null) ? 0 : getGroupCode().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getSorting() == null) ? 0 : getSorting().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
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
    public GroupProduct copy(){
       GroupProduct obj =  new GroupProduct();
       obj.setGroupCode(this.groupCode);
       obj.setGroupName(this.groupName);
       obj.setType(this.type);
       obj.setState(this.state);
       obj.setSorting(this.sorting);
       obj.setPicId(this.picId);
       obj.setCode(this.code);
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
       map.put("groupCode",this.groupCode);
       map.put("groupName",this.groupName);
       map.put("type",this.type);
       map.put("state",this.state);
       map.put("sorting",this.sorting);
       map.put("picId",this.picId);
       map.put("code",this.code);
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

	public String getProductStyleIdInfo() {
		return productStyleIdInfo;
	}

	public void setProductStyleIdInfo(String productStyleIdInfo) {
		this.productStyleIdInfo = productStyleIdInfo;
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

    public Integer getUserId() {
      return userId;
    }
  
    public void setUserId(Integer userId) {
      this.userId = userId;
    }

    
	
}
