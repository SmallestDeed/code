package com.nork.productprops.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: ProductProps.java 
 * @Package com.nork.productprops.model
 * @Description:产品属性-产品属性表
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 10:40:03
 * @version V1.0   
 */
public class ProductProps  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	/**  属性CODE  **/
	private String code;
	/**  属性长CODE  **/
	private String longCode;
	/**  属性名称  **/
	private String name;
	/**  属性值  **/
	private String propValue;
	/**  图片  **/
	private Integer picPath;
	/**  父级ID  **/
	private Integer pid;
	/**  类型  **/
	private Integer type;
	/**  等级  **/
	private Integer level;
	/**  是否子级  **/
	private Integer isLeaf;
	/**  排序  **/
	private Integer ordering;
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

	/** 父级 **/
	private ProductProps parentProps;

	/** 图片路径 **/
	private String picPathVal;
	
	/** 颜色名称 **/
	private String colorName;
	
	/** 材质名称 **/
	private String materialName;
	/**  （排序，过滤）  **/
	private String filterOrder;
	//属性序号
	private Integer sequenceNumber;
	// 属性长序号  
	private String longNumbers;
	
	private String parentCode;
	private Integer productId;
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getLongNumbers() {
		return longNumbers;
	}

	public void setLongNumbers(String longNumbers) {
		this.longNumbers = longNumbers;
	}

	public String getFilterOrder() {
		return filterOrder;
	}

	public void setFilterOrder(String filterOrder) {
		this.filterOrder = filterOrder;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public  String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public  String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue){
		this.propValue = propValue;
	}
	public  Integer getPicPath() {
		return picPath;
	}
	public void setPicPath(Integer picPath){
		this.picPath = picPath;
	}
	public  Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid){
		this.pid = pid;
	}
	public  Integer getType() {
		return type;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public  Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public  Integer getOrdering() {
		return ordering;
	}
	public void setOrdering(Integer ordering){
		this.ordering = ordering;
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
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ProductProps getParentProps() {
		return parentProps;
	}
	public void setParentProps(ProductProps parentProps) {
		this.parentProps = parentProps;
	}
	public String getPicPathVal() {
		return picPathVal;
	}
	public void setPicPathVal(String picPathVal) {
		this.picPathVal = picPathVal;
	}
	public String getLongCode() {
		return longCode;
	}
	public void setLongCode(String longCode) {
		this.longCode = longCode;
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
        ProductProps other = (ProductProps) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
			&& (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
			&& (this.getLongCode() == null ? other.getLongCode() == null : this.getCode().equals(other.getLongCode()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPropValue() == null ? other.getPropValue() == null : this.getPropValue().equals(other.getPropValue()))
            && (this.getPicPath() == null ? other.getPicPath() == null : this.getPicPath().equals(other.getPicPath()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
			&& (this.getIsLeaf() == null ? other.getIsLeaf() == null : this.getIsLeaf().equals(other.getIsLeaf()))
            && (this.getOrdering() == null ? other.getOrdering() == null : this.getOrdering().equals(other.getOrdering()))
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
		result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
		result = prime * result + ((getLongCode() == null) ? 0 : getLongCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPropValue() == null) ? 0 : getPropValue().hashCode());
        result = prime * result + ((getPicPath() == null) ? 0 : getPicPath().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
		result = prime * result + ((getIsLeaf() == null) ? 0 : getIsLeaf().hashCode());
        result = prime * result + ((getOrdering() == null) ? 0 : getOrdering().hashCode());
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
    public ProductProps copy(){
       ProductProps obj =  new ProductProps();
	   obj.setCode(this.code);
	   obj.setRemark(this.longCode);
       obj.setName(this.name);
       obj.setPropValue(this.propValue);
       obj.setPicPath(this.picPath);
       obj.setPid(this.pid);
       obj.setType(this.type);
       obj.setLevel(this.level);
	   obj.setIsLeaf(this.isLeaf);
       obj.setOrdering(this.ordering);
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
	   map.put("code",this.code);
	   map.put("code",this.longCode);
       map.put("name",this.name);
       map.put("propValue",this.propValue);
       map.put("picPath",this.picPath);
       map.put("pid",this.pid);
       map.put("type",this.type);
       map.put("level",this.level);
	   map.put("isLeaf",this.isLeaf);
       map.put("ordering",this.ordering);
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
}
