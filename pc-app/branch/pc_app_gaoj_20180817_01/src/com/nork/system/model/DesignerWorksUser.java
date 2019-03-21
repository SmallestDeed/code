package com.nork.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nork.common.model.Mapper;

public class DesignerWorksUser extends Mapper implements Serializable{

	private static final long serialVersionUID = 1L;
	/**  用户名  **/
	private String userName;
	/**  昵称  **/
	private String nickName;
	/**  邮箱  **/
	private String email;
	/**  性别  **/
	private Integer sex;
	private Integer id;
	/**  公司地址 **/
	private String companyAddress;
	/**  移动  **/
	private String mobile;
	/**  身份证验证状态  **/
	private Integer idcardVerifyState;
	/**  设计者id  **/
	private Integer userId;
	/**  图片列表  **/
	private String picIds;
	/**  缩略图  **/
	private String picId;
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	
	List picList = new ArrayList();
	public List getPicList() {
		return picList;
	}
	public void setPicList(List picList) {
		this.picList = picList;
	}

	/**  面积  **/
	private String area;
	/**  作品名称  **/
	private String title;
	/**  作品描述  **/
	private String worksDescription;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  公司电话  **/
	private String companyTel;
	/**  个人专长 **/
	private String specialityValue;
	/**  个人简介  **/
	private String intro;
	private String name;
	private String qq;
	private String picPath;
	private String projectTime;
	public String getProjectTime() {
		return projectTime;
	}
	public void setProjectTime(String projectTime) {
		this.projectTime = projectTime;
	}
	/*渲染图集合*/
	private List renderPicList = new ArrayList();
	
	public List getRenderPicList() {
		return renderPicList;
	}
	public void setRenderPicList(List renderPicList) {
		this.renderPicList = renderPicList;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getSpecialityValue() {
		return specialityValue;
	}
	public void setSpecialityValue(String specialityValue) {
		this.specialityValue = specialityValue;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getIdcardVerifyState() {
		return idcardVerifyState;
	}
	public void setIdcardVerifyState(Integer idcardVerifyState) {
		this.idcardVerifyState = idcardVerifyState;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPicIds() {
		return picIds;
	}
	public void setPicIds(String picIds) {
		this.picIds = picIds;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWorksDescription() {
		return worksDescription;
	}
	public void setWorksDescription(String worksDescription) {
		this.worksDescription = worksDescription;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
