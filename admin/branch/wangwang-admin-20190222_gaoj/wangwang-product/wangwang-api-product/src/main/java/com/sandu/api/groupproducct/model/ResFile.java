package com.sandu.api.groupproducct.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
@Data
public class ResFile implements Serializable {
    private Long id;

    /**
     * 文件编码
     */
    private String fileCode;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件原名称
     */
    private String fileOriginalName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件级别
     */
    private String fileLevel;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件描述
     */
    private String fileDesc;

    /**
     * 文件排序
     */
    private Integer fileOrdering;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

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
    private String fileKey;

    private String fileKeys;

    private String businessIds;

    /**
     * 字符备用4
     */
    private String att4;

    /**
     * 字符备用5
     */
    private String att5;

    /**
     * 字符备用6
     */
    private String att6;

    /**
     * 时间备用1
     */
    private Date dateAtt1;

    /**
     * 时间备用2
     */
    private Date dateAtt2;

    /**
     * 整数备用1
     */
    private Integer businessId;

    /**
     * 整数备用2
     */
    private Integer numAtt2;

    /**
     * 数字备用1
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2
     */
    private BigDecimal numAtt4;

    /**
     * 备注
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

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
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

    public String getFileKeys() {
        return fileKeys;
    }

    public void setFileKeys(String fileKeys) {
        this.fileKeys = fileKeys;
    }

    public String getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(String businessIds) {
        this.businessIds = businessIds;
    }

    public String getAtt4() {
        return att4;
    }

    public void setAtt4(String att4) {
        this.att4 = att4;
    }

    public String getAtt5() {
        return att5;
    }

    public void setAtt5(String att5) {
        this.att5 = att5;
    }

    public String getAtt6() {
        return att6;
    }

    public void setAtt6(String att6) {
        this.att6 = att6;
    }

    public Date getDateAtt1() {
        return dateAtt1;
    }

    public void setDateAtt1(Date dateAtt1) {
        this.dateAtt1 = dateAtt1;
    }

    public Date getDateAtt2() {
        return dateAtt2;
    }

    public void setDateAtt2(Date dateAtt2) {
        this.dateAtt2 = dateAtt2;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getNumAtt2() {
        return numAtt2;
    }

    public void setNumAtt2(Integer numAtt2) {
        this.numAtt2 = numAtt2;
    }

    public BigDecimal getNumAtt3() {
        return numAtt3;
    }

    public void setNumAtt3(BigDecimal numAtt3) {
        this.numAtt3 = numAtt3;
    }

    public BigDecimal getNumAtt4() {
        return numAtt4;
    }

    public void setNumAtt4(BigDecimal numAtt4) {
        this.numAtt4 = numAtt4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}