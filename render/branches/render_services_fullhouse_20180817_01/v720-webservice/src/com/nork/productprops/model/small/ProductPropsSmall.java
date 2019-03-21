package com.nork.productprops.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: ProductProps.java 
 * @Package com.nork.productprops.model.small
 * @Description:产品属性-产品属性表
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 10:40:03
 * @version V1.0   
 */
public class ProductPropsSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	/**  属性编码  **/
	private String code;
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

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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


}
