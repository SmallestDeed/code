package com.nork.onekeydesign.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;

/**
 * 水刀模版表po
 * 
 * @author huangsongbo
 * @date 2018.6.4
 */
public class BaseWaterjetTemplate extends Mapper implements Serializable {

	private static final long serialVersionUID = 1758265251020502281L;

	/**
	 *
	 */
	private Long id;

	/**
	 * 水刀模版编码
	 */
	private String templateCode;

	/**
	 * 水刀模版名称
	 */
	private String templateName;

	/**
	 * 水刀模版品牌
	 */
	private Integer brandId;

	/**
	 * 水刀模版默认长度(cm)
	 */
	private Integer templateLength;

	/**
	 * 水刀模版默认宽度(cm)
	 */
	private Integer templateWidth;

	/**
	 * 水刀模版模型文件
	 */
	private Integer templateFileId;

	/**
	 * 水刀模版展示图片
	 */
	private Integer templatePicId;

	/**
	 * 水刀模版形状value(关联数据字典value)
	 */
	private Integer templateShapeValue;

	/**
	 * CAD源文件
	 */
	private Integer cadSourceFileId;

	/**
	 * 水刀模版描述
	 */
	private String templateDescribe;

	/**
	 * 水刀模版状态（0：未上架、1：已上架、2：已下架）
	 */
	private Integer templateStatus;

	/**
	 * 我的水刀：用户id
	 */
	private Integer userId;

	/**
	 * 系统编码
	 */
	private String sysCode;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	/**
	 * 字符备用1
	 */
	private String att1;

	/**
	 * 字符备用2
	 */
	private String att2;

	/**
	 * 整数备用1
	 */
	private Integer numa1;

	/**
	 * 整数备用2
	 */
	private Integer numa2;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 展示图片url
	 */
	private String templatePicUrl;
	
	/**
	 * 水刀模版模型文件url
	 */
	private String templateFileUrl;
	
	/**
	 * 水刀模板展示图片缩略图信息
	 */
	private String templateSmallPicInfo;
	
	/**
	 * 展示图片ipad端缩略图id
	 */
	private Integer templateIpadSmallPicId;

	/** CAD源文件地址 **/
	private String cadSourceFilePath;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getTemplateLength() {
		return templateLength;
	}

	public void setTemplateLength(Integer templateLength) {
		this.templateLength = templateLength;
	}

	public Integer getTemplateWidth() {
		return templateWidth;
	}

	public void setTemplateWidth(Integer templateWidth) {
		this.templateWidth = templateWidth;
	}

	public Integer getTemplateFileId() {
		return templateFileId;
	}

	public void setTemplateFileId(Integer templateFileId) {
		this.templateFileId = templateFileId;
	}

	public Integer getTemplatePicId() {
		return templatePicId;
	}

	public void setTemplatePicId(Integer templatePicId) {
		this.templatePicId = templatePicId;
	}

	public Integer getTemplateShapeValue() {
		return templateShapeValue;
	}

	public void setTemplateShapeValue(Integer templateShapeValue) {
		this.templateShapeValue = templateShapeValue;
	}

	public Integer getCadSourceFileId() {
		return cadSourceFileId;
	}

	public void setCadSourceFileId(Integer cadSourceFileId) {
		this.cadSourceFileId = cadSourceFileId;
	}

	public String getTemplateDescribe() {
		return templateDescribe;
	}

	public void setTemplateDescribe(String templateDescribe) {
		this.templateDescribe = templateDescribe;
	}

	public Integer getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(Integer templateStatus) {
		this.templateStatus = templateStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAtt1() {
		return att1;
	}

	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	public String getAtt2() {
		return att2;
	}

	public void setAtt2(String att2) {
		this.att2 = att2;
	}

	public Integer getNuma1() {
		return numa1;
	}

	public void setNuma1(Integer numa1) {
		this.numa1 = numa1;
	}

	public Integer getNuma2() {
		return numa2;
	}

	public void setNuma2(Integer numa2) {
		this.numa2 = numa2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTemplatePicUrl() {
		return templatePicUrl;
	}

	public void setTemplatePicUrl(String templatePicUrl) {
		this.templatePicUrl = templatePicUrl;
	}

	public String getTemplateFileUrl() {
		return templateFileUrl;
	}

	public void setTemplateFileUrl(String templateFileUrl) {
		this.templateFileUrl = templateFileUrl;
	}

	public String getTemplateSmallPicInfo() {
		return templateSmallPicInfo;
	}

	public void setTemplateSmallPicInfo(String templateSmallPicInfo) {
		this.templateSmallPicInfo = templateSmallPicInfo;
	}

	public Integer getTemplateIpadSmallPicId() {
		return templateIpadSmallPicId;
	}

	public void setTemplateIpadSmallPicId(Integer templateIpadSmallPicId) {
		this.templateIpadSmallPicId = templateIpadSmallPicId;
	}

	public String getCadSourceFilePath() {
		return cadSourceFilePath;
	}

	public void setCadSourceFilePath(String cadSourceFilePath) {
		this.cadSourceFilePath = cadSourceFilePath;
	}
}
