package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: GroupProduct.java 
 * @Package com.nork.product.model.small
 * @Description:产品模块-产品组合主表
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
public class GroupProductSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  组合编码  **/
	private String groupCode;
	/**  组合名  **/
	private String groupName;
	/**  组合类型  **/
	private Integer type;
	/**  组合上下架状态(0上，1下)  **/
	private Integer state;
	/**  排序  **/
	private Integer sorting;
	/**   封面id  **/
	private Integer picId;
	/**  组合编码  **/
	private String code;
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
