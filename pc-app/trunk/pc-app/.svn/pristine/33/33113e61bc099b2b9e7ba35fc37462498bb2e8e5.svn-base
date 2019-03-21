package com.nork.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.nork.common.model.Mapper;

/**   
 * @Title: UserSpellingflowerCollet.java 
 * @Package com.nork.system.model
 * @Description:拼花-我的瓷砖拼花收藏
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:38:44
 * @version V1.0   
 */
public class UserSpellingflowerCollet  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  用户id  **/
	private Integer userId;
	/**  瓷砖拼花收藏名  **/
	private String colletName;
	/**  拼花信息文件id  **/
	private Integer spellingFlowerFileId;
	/**  拼花图片信息路径id */
	private Integer spellingFlowerPicId;
	/**  瓷砖拼花产品ids  **/
	private String spellingFlowerProduct;
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

	/** 使用记录id */
	private String recordId;

	/** 拼花信息 */
	private String spellingFlower;

	/** 拼花图片路径 */
	private String spellingFlowerPic;

	/**
	 * 拼花产品材质信息
	 */
	Map<String,Object>spellingFlowerProductMap = new HashMap<String,Object>();

	public String getSpellingFlowerPic() {
		return spellingFlowerPic;
	}
	public void setSpellingFlowerPic(String spellingFlowerPic) {
		this.spellingFlowerPic = spellingFlowerPic;
	}
	public Integer getSpellingFlowerPicId() {
		return spellingFlowerPicId;
	}
	public void setSpellingFlowerPicId(Integer spellingFlowerPicId) {
		this.spellingFlowerPicId = spellingFlowerPicId;
	}
	public Map<String, Object> getSpellingFlowerProductMap() {
		return spellingFlowerProductMap;
	}
	public String getSpellingFlower() {
		return spellingFlower;
	}
	public void setSpellingFlower(String spellingFlower) {
		this.spellingFlower = spellingFlower;
	}
	public void setSpellingFlowerProductMap(Map<String, Object> spellingFlowerProductMap) {
		this.spellingFlowerProductMap = spellingFlowerProductMap;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getColletName() {
		return colletName;
	}
	public void setColletName(String colletName){
		this.colletName = colletName;
	}
	public  Integer getSpellingFlowerFileId() {
		return spellingFlowerFileId;
	}
	public void setSpellingFlowerFileId(Integer spellingFlowerFileId){
		this.spellingFlowerFileId = spellingFlowerFileId;
	}
	public  String getSpellingFlowerProduct() {
		return spellingFlowerProduct;
	}
	public void setSpellingFlowerProduct(String spellingFlowerProduct){
		this.spellingFlowerProduct = spellingFlowerProduct;
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
        UserSpellingflowerCollet other = (UserSpellingflowerCollet) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getColletName() == null ? other.getColletName() == null : this.getColletName().equals(other.getColletName()))
            && (this.getSpellingFlowerFileId() == null ? other.getSpellingFlowerFileId() == null : this.getSpellingFlowerFileId().equals(other.getSpellingFlowerFileId()))
            && (this.getSpellingFlowerProduct() == null ? other.getSpellingFlowerProduct() == null : this.getSpellingFlowerProduct().equals(other.getSpellingFlowerProduct()))
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
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getColletName() == null) ? 0 : getColletName().hashCode());
        result = prime * result + ((getSpellingFlowerFileId() == null) ? 0 : getSpellingFlowerFileId().hashCode());
        result = prime * result + ((getSpellingFlowerProduct() == null) ? 0 : getSpellingFlowerProduct().hashCode());
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
    public UserSpellingflowerCollet copy(){
       UserSpellingflowerCollet obj =  new UserSpellingflowerCollet();
       obj.setUserId(this.userId);
       obj.setColletName(this.colletName);
       obj.setSpellingFlowerFileId(this.spellingFlowerFileId);
       obj.setSpellingFlowerProduct(this.spellingFlowerProduct);
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
       map.put("userId",this.userId);
       map.put("colletName",this.colletName);
       map.put("spellingFlowerFileId",this.spellingFlowerFileId);
       map.put("spellingFlowerProduct",this.spellingFlowerProduct);
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
