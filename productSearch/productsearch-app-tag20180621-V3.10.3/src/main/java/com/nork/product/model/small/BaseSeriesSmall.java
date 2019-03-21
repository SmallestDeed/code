package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: BaseSeries.java 
 * @Package com.nork.product.model.small
 * @Description:product-系列表
 * @createAuthor pandajun 
 * @CreateDate 2017-11-04 11:06:16
 * @version V1.0   
 */
public class BaseSeriesSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  系列编码  **/
	private String code;
	/**  系列长编码  **/
	private String longCode;
	/**  父级ID  **/
	private Integer pid;
	/**  名称  **/
	private String name;
	/**  是否是子节点  **/
	private Integer isLeaf;
	/**  等级  **/
	private Integer level;
	/**  排序  **/
	private Integer ordering;
	/**  图片ID  **/
	private Integer picId;
	/**  是否显示(1是0否)  **/
	private Integer displayStatus;
	/**  第一级code  **/
	private String firstStageCode;
	/**  第二级code  **/
	private String secondStageCode;
	/**  第三级code  **/
	private String thirdStageCode;
	/**  第四级code  **/
	private String fourthStageCode;
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

	public  String getCode() {
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public  String getLongCode() {
		return longCode;
	}
	public void setLongCode(String longCode){
		this.longCode = longCode;
	}
	public  Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid){
		this.pid = pid;
	}
	public  String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public  Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf){
		this.isLeaf = isLeaf;
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
	public  Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId){
		this.picId = picId;
	}
	public  Integer getDisplayStatus() {
		return displayStatus;
	}
	public void setDisplayStatus(Integer displayStatus){
		this.displayStatus = displayStatus;
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
