package com.nork.resource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * 
 * 全屋户型文件
 * 
 * @author chenm
 * 2018-09-21 14:23:39.723
 */

public class ResFullHouseFile implements Serializable {
    
    /**
     * res_full_house_file.id
     */
    private Long id;

    /**
     * 文件编码<p>
     * res_full_house_file.file_code
     */
    private String fileCode;

    /**
     * 文件名称<p>
     * res_full_house_file.file_name
     */
    private String fileName;

    /**
     * 文件类型<p>
     * res_full_house_file.file_type
     */
    private String fileType;

    /**
     * 文件大小<p>
     * res_full_house_file.file_size
     */
    private String fileSize;

    /**
     * 文件后缀<p>
     * res_full_house_file.file_suffix
     */
    private String fileSuffix;

    /**
     * 文件路径<p>
     * res_full_house_file.file_path
     */
    private String filePath;

    /**
     * 文件排序<p>
     * res_full_house_file.file_ordering
     */
    private Integer fileOrdering;

    /**
     * 创建者<p>
     * res_full_house_file.creator
     */
    private String creator;

    /**
     * res_full_house_file.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * res_full_house_file.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * res_full_house_file.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * res_full_house_file.is_deleted
     */
    private Integer isDeleted;

    /**
     * 文件key<p>
     * res_full_house_file.file_key
     */
    private String fileKey;

    /**
     * 关联的业务ID<p>
     * res_full_house_file.business_id
     */
    private Long businessId;

    /**
     * 备注<p>
     * res_full_house_file.remark
     */
    private String remark;

    private static final long serialVersionUID = 1L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileOrdering() {
        return fileOrdering;
    }

    public void setFileOrdering(Integer fileOrdering) {
        this.fileOrdering = fileOrdering;
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

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}