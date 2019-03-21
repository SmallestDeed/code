package com.nork.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 一键装修副本配置文件表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-07-22 14:43:07.242
 */

public class ResDesignOnekeyTemplate implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * res_design_onekey_template.id
     */
    private Long id;

    /**
     * 原始表id<p>
     * res_design_onekey_template.old_id
     */
    private Integer oldId;

    /**
     * 文件编码<p>
     * res_design_onekey_template.file_code
     */
    private String fileCode;

    /**
     * 文件名称<p>
     * res_design_onekey_template.file_name
     */
    private String fileName;

    /**
     * 文件原名称<p>
     * res_design_onekey_template.file_original_name
     */
    private String fileOriginalName;

    /**
     * 文件类别<p>
     * res_design_onekey_template.file_type
     */
    private String fileType;

    /**
     * 文件大小<p>
     * res_design_onekey_template.file_size
     */
    private String fileSize;

    /**
     * 图片长、模型宽<p>
     * res_design_onekey_template.file_weight
     */
    private String fileWeight;

    /**
     * 图片高、模型高<p>
     * res_design_onekey_template.file_high
     */
    private String fileHigh;

    /**
     * 长（模型）<p>
     * res_design_onekey_template.file_length
     */
    private Integer fileLength;

    /**
     * 文件后缀<p>
     * res_design_onekey_template.file_suffix
     */
    private String fileSuffix;

    /**
     * 文件格式（图片）<p>
     * res_design_onekey_template.file_format
     */
    private String fileFormat;

    /**
     * 文件级别<p>
     * res_design_onekey_template.file_level
     */
    private String fileLevel;

    /**
     * 文件路径<p>
     * res_design_onekey_template.file_path
     */
    private String filePath;

    /**
     * 文件描述<p>
     * res_design_onekey_template.file_desc
     */
    private String fileDesc;

    /**
     * 文件排序<p>
     * res_design_onekey_template.file_ordering
     */
    private Integer fileOrdering;

    /**
     * 系统编码<p>
     * res_design_onekey_template.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * res_design_onekey_template.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * res_design_onekey_template.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * res_design_onekey_template.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * res_design_onekey_template.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * res_design_onekey_template.is_deleted
     */
    private Integer isDeleted;

    /**
     * res_design_onekey_template.file_key
     */
    private String fileKey;

    /**
     * 业务id<p>
     * res_design_onekey_template.business_id
     */
    private Integer businessId;

    /**
     * 缩略图（图片） <p>
     * res_design_onekey_template.small_pic_info
     */
    private String smallPicInfo;

    /**
     * 观察点（渲染图）<p>
     * res_design_onekey_template.view_point
     */
    private Integer viewPoint;

    /**
     * 场景（渲染图）<p>
     * res_design_onekey_template.scene
     */
    private Integer scene;

    /**
     * 顺序（图片）<p>
     * res_design_onekey_template.sequence
     */
    private Integer sequence;

    /**
     * 渲染类型（渲染图）<p>
     * res_design_onekey_template.rendering_type
     */
    private Integer renderingType;

    /**
     * 720度渲染图路径（渲染图）<p>
     * res_design_onekey_template.pano_path
     */
    private String panoPath;

    /**
     * 最小高度（模型）<p>
     * res_design_onekey_template.min_height
     */
    private Integer minHeight;

    /**
     * 是否共享（模型）<p>
     * res_design_onekey_template.is_model_share
     */
    private Integer isModelShare;

    /**
     * 备注<p>
     * res_design_onekey_template.remark
     */
    private String remark;

    /**
     * res_design_onekey_template.source
     */
    private String source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOldId() {
		return oldId;
	}

	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileOriginalName() {
		return fileOriginalName;
	}

	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileWeight() {
		return fileWeight;
	}

	public void setFileWeight(String fileWeight) {
		this.fileWeight = fileWeight;
	}

	public String getFileHigh() {
		return fileHigh;
	}

	public void setFileHigh(String fileHigh) {
		this.fileHigh = fileHigh;
	}

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(String fileLevel) {
		this.fileLevel = fileLevel;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public Integer getFileOrdering() {
		return fileOrdering;
	}

	public void setFileOrdering(Integer fileOrdering) {
		this.fileOrdering = fileOrdering;
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

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getSmallPicInfo() {
		return smallPicInfo;
	}

	public void setSmallPicInfo(String smallPicInfo) {
		this.smallPicInfo = smallPicInfo;
	}

	public Integer getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(Integer viewPoint) {
		this.viewPoint = viewPoint;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getRenderingType() {
		return renderingType;
	}

	public void setRenderingType(Integer renderingType) {
		this.renderingType = renderingType;
	}

	public String getPanoPath() {
		return panoPath;
	}

	public void setPanoPath(String panoPath) {
		this.panoPath = panoPath;
	}

	public Integer getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

	public Integer getIsModelShare() {
		return isModelShare;
	}

	public void setIsModelShare(Integer isModelShare) {
		this.isModelShare = isModelShare;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
    
}