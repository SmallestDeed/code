package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: StructureProduct.java 
 * @Package com.nork.product.model.small
 * @Description:产品模块-结构表
 * @createAuthor pandajun 
 * @CreateDate 2016-12-05 14:46:50
 * @version V1.0   
 */
public class StructureProductSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  结构编码  **/
	private String structureCode;
	/**  结构名称  **/
	private String structureName;
	/**  关联样板房id  **/
	private Integer templetId;
	/**  状态-:未上架;1;上架  **/
	private Integer status;
	/**  封面图片id  **/
	private Integer picId;
	/**  配置文件id  **/
	private Integer configFileId;
	/**  结构组标记  **/
	private String groupFlag;
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

	public  String getStructureCode() {
		return structureCode;
	}
	public void setStructureCode(String structureCode){
		this.structureCode = structureCode;
	}
	public  String getStructureName() {
		return structureName;
	}
	public void setStructureName(String structureName){
		this.structureName = structureName;
	}
	public  Integer getTempletId() {
		return templetId;
	}
	public void setTempletId(Integer templetId){
		this.templetId = templetId;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId){
		this.picId = picId;
	}
	public  Integer getConfigFileId() {
		return configFileId;
	}
	public void setConfigFileId(Integer configFileId){
		this.configFileId = configFileId;
	}
	public  String getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(String groupFlag){
		this.groupFlag = groupFlag;
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
