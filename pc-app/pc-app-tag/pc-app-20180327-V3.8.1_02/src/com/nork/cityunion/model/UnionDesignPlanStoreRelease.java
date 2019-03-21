package com.nork.cityunion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.nork.common.model.Mapper;

/**   
 * @Title: UnionDesignPlanStoreRelease.java 
 * @Package com.nork.cityunion.model
 * @Description:同城联盟-联盟素材发布表
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
public class UnionDesignPlanStoreRelease  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  用户ID  **/
	private Integer userId;
	/**  发布名称  **/
	private String releaseName;
	/**  发布类型(打包发布:1 单独发布：0)  **/
	private String releaseType;
	/**  方案库类别Id  **/
	private Integer catoryId;
	/**  店面名片Id  **/
	private Integer storefrontId;
	/**  联盟组合Id  **/
	private Integer unionGroupId;
	/**  优惠活动Id  **/
	private Integer specialOfferId;
	/**  进入720的第一个设计方案素材Id（优先客餐厅方案）  **/
	private Integer designPlanStoreId;
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

	//发布方案素材传递集合参数
	private List<DesignPlanStoreReleaseModel> storeReleaseModelList;

	public List<DesignPlanStoreReleaseModel> getStoreReleaseModelList() {
		return storeReleaseModelList;
	}

	public void setStoreReleaseModelList(List<DesignPlanStoreReleaseModel> storeReleaseModelList) {
		this.storeReleaseModelList = storeReleaseModelList;
	}

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName){
		this.releaseName = releaseName;
	}
	public  String getReleaseType() {
		return releaseType;
	}
	public void setReleaseType(String releaseType){
		this.releaseType = releaseType;
	}
	public  Integer getCatoryId() {
		return catoryId;
	}
	public void setCatoryId(Integer catoryId){
		this.catoryId = catoryId;
	}

	public Integer getStorefrontId() {
		return storefrontId;
	}

	public void setStorefrontId(Integer storefrontId) {
		this.storefrontId = storefrontId;
	}

	public  Integer getUnionGroupId() {
		return unionGroupId;
	}
	public void setUnionGroupId(Integer unionGroupId){
		this.unionGroupId = unionGroupId;
	}

	public Integer getDesignPlanStoreId() {
		return designPlanStoreId;
	}

	public void setDesignPlanStoreId(Integer designPlanStoreId) {
		this.designPlanStoreId = designPlanStoreId;
	}

	public  Integer getSpecialOfferId() {
		return specialOfferId;
	}
	public void setSpecialOfferId(Integer specialOfferId){
		this.specialOfferId = specialOfferId;
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
        UnionDesignPlanStoreRelease other = (UnionDesignPlanStoreRelease) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getReleaseName() == null ? other.getReleaseName() == null : this.getReleaseName().equals(other.getReleaseName()))
            && (this.getReleaseType() == null ? other.getReleaseType() == null : this.getReleaseType().equals(other.getReleaseType()))
            && (this.getCatoryId() == null ? other.getCatoryId() == null : this.getCatoryId().equals(other.getCatoryId()))
            && (this.getUnionGroupId() == null ? other.getUnionGroupId() == null : this.getUnionGroupId().equals(other.getUnionGroupId()))
            && (this.getSpecialOfferId() == null ? other.getSpecialOfferId() == null : this.getSpecialOfferId().equals(other.getSpecialOfferId()))
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
        result = prime * result + ((getReleaseName() == null) ? 0 : getReleaseName().hashCode());
        result = prime * result + ((getReleaseType() == null) ? 0 : getReleaseType().hashCode());
        result = prime * result + ((getCatoryId() == null) ? 0 : getCatoryId().hashCode());
        result = prime * result + ((getUnionGroupId() == null) ? 0 : getUnionGroupId().hashCode());
        result = prime * result + ((getSpecialOfferId() == null) ? 0 : getSpecialOfferId().hashCode());
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
    public UnionDesignPlanStoreRelease copy(){
       UnionDesignPlanStoreRelease obj =  new UnionDesignPlanStoreRelease();
       obj.setUserId(this.userId);
       obj.setReleaseName(this.releaseName);
       obj.setReleaseType(this.releaseType);
       obj.setCatoryId(this.catoryId);
       obj.setUnionGroupId(this.unionGroupId);
       obj.setSpecialOfferId(this.specialOfferId);
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
       map.put("releaseName",this.releaseName);
       map.put("releaseType",this.releaseType);
       map.put("catoryId",this.catoryId);
       map.put("unionGroupId",this.unionGroupId);
       map.put("specialOfferId",this.specialOfferId);
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
