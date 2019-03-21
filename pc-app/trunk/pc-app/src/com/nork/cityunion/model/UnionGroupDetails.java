package com.nork.cityunion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.nork.common.model.Mapper;

/**   
 * @Title: UnionGroupDetails.java 
 * @Package com.nork.cityunion.model
 * @Description:同城联盟-联盟组明细表
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:02
 * @version V1.0   
 */
public class UnionGroupDetails  extends Mapper implements Serializable{
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
	/**  组ID  **/
	private Integer groupId;
	/**  组成员名称  **/
	private String name;
	/**  品牌id  **/
	private Integer brandId;
	/**  联系人  **/
	private String contact;
	/**  电话  **/
	private String phone;
	/**  地址  **/
	private String address;
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
	
	private String groupName;

	private List<UnionGroupDetails> detailsList;
	//联盟明细id
	private List<Integer> detailsIds;
	//联盟店面名称
	private List<String> detailNames;

	//品牌名称
	private String brandName;

	//关联公司
	private Integer companyId;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<String> getDetailNames() {
		return detailNames;
	}

	public void setDetailNames(List<String> detailNames) {
		this.detailNames = detailNames;
	}

	public List<Integer> getDetailsIds() {
		return detailsIds;
	}

	public void setDetailsIds(List<Integer> detailsIds) {
		this.detailsIds = detailsIds;
	}

	public List<UnionGroupDetails> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<UnionGroupDetails> detailsList) {
		this.detailsList = detailsList;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	public  String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public  Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId){
		this.brandId = brandId;
	}
	public  String getContact() {
		return contact;
	}
	public void setContact(String contact){
		this.contact = contact;
	}
	public  String getPhone() {
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public  String getAddress() {
		return address;
	}
	public void setAddress(String address){
		this.address = address;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UnionGroupDetails that = (UnionGroupDetails) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
		if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
		if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
		if (address != null ? !address.equals(that.address) : that.address != null) return false;
		if (sysCode != null ? !sysCode.equals(that.sysCode) : that.sysCode != null) return false;
		if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
		if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
		if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
		if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null) return false;
		if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
		if (att1 != null ? !att1.equals(that.att1) : that.att1 != null) return false;
		if (att2 != null ? !att2.equals(that.att2) : that.att2 != null) return false;
		if (numa1 != null ? !numa1.equals(that.numa1) : that.numa1 != null) return false;
		if (numa2 != null ? !numa2.equals(that.numa2) : that.numa2 != null) return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
		if (detailsList != null ? !detailsList.equals(that.detailsList) : that.detailsList != null) return false;
		if (detailsIds != null ? !detailsIds.equals(that.detailsIds) : that.detailsIds != null) return false;
		if (detailNames != null ? !detailNames.equals(that.detailNames) : that.detailNames != null) return false;
		if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null) return false;
		return companyId != null ? companyId.equals(that.companyId) : that.companyId == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
		result = 31 * result + (contact != null ? contact.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (sysCode != null ? sysCode.hashCode() : 0);
		result = 31 * result + (creator != null ? creator.hashCode() : 0);
		result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
		result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
		result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (att1 != null ? att1.hashCode() : 0);
		result = 31 * result + (att2 != null ? att2.hashCode() : 0);
		result = 31 * result + (numa1 != null ? numa1.hashCode() : 0);
		result = 31 * result + (numa2 != null ? numa2.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
		result = 31 * result + (detailsList != null ? detailsList.hashCode() : 0);
		result = 31 * result + (detailsIds != null ? detailsIds.hashCode() : 0);
		result = 31 * result + (detailNames != null ? detailNames.hashCode() : 0);
		result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
		result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
		return result;
	}

	/**获取对象的copy**/
    public UnionGroupDetails copy(){
       UnionGroupDetails obj =  new UnionGroupDetails();
       obj.setUserId(this.userId);
       obj.setGroupId(this.groupId);
       obj.setName(this.name);
       obj.setBrandId(this.brandId);
       obj.setContact(this.contact);
       obj.setPhone(this.phone);
       obj.setAddress(this.address);
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
       map.put("groupId",this.groupId);
       map.put("name",this.name);
       map.put("brandId",this.brandId);
       map.put("contact",this.contact);
       map.put("phone",this.phone);
       map.put("address",this.address);
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
	public String toString() {
		return "UnionGroupDetails{" +
				"id=" + id +
				", userId=" + userId +
				", groupId=" + groupId +
				", name='" + name + '\'' +
				", brandId=" + brandId +
				", contact='" + contact + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", att1='" + att1 + '\'' +
				", att2='" + att2 + '\'' +
				", numa1=" + numa1 +
				", numa2=" + numa2 +
				", remark='" + remark + '\'' +
				", groupName='" + groupName + '\'' +
				", detailsList=" + detailsList +
				", detailsIds=" + detailsIds +
				", detailNames=" + detailNames +
				", brandName='" + brandName + '\'' +
				", companyId=" + companyId +
				'}';
	}
}
