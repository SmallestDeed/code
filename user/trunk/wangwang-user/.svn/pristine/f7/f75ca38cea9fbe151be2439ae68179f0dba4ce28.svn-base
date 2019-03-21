package com.sandu.api.system.model;


import org.mapstruct.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**   
 * @Title: SysVersion.java 
 * @Package com.nork.system.model
 * @Description:系统模块-版本管理
 * @createAuthor pandajun 
 * @CreateDate 2016-05-05 14:18:01
 * @version V1.0   
 */
public class SysVersion implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  平台类型  **/
	private String systemType;
	/**  版本号  **/
	private String version;
	/**  版本状态  **/
	private Integer status;
	/**  标题  **/
	private String title;
	/**  内容  **/
	private String content;
	/**  app地址  **/
	private String appPath;
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
	//状态名称
	private String statusInfo;
	//系统名称
	private String systemTypeInfo;
	/*
	 * 升级方式:
	 * 0:手动升级
	 * 1:强制升级
	 * */
	private String sysVersionType;
	
	private Integer upgradeMethod;
	/*版本文件id*/
	private Integer fileId;
	/*完整路径*/
	private String appPathInfo;
	/*决定是否要显示编辑按钮*/
	private String isRelease;
	/*发布时间*/
	private Date releaseDate;
	/*补丁安装文件关联id*/
	private Integer patchFileId;
	/*appStore地址*/
	private String iosFileUrl;
	/*壳文件id*/
	private Integer shellExeFileId;
	//壳路径
	private String shellExePath;
	//压缩包路径
	private String shellZipPath;
	/*壳版本*/
	private String shellVersion;
	/*壳版本*/
	private String verType;
	/*壳版本*/
	private String telephone;
	/*官网地址*/
	private String officialWebsitePath;
	/*壳常见问题地址*/
	private String shellCommonProblemPath;
	/*app常见问题地址*/
	private String appCommonProblemPath;
	/*壳版本*/
	private String telephoneName;
	/*官网地址*/
	private String officialWebsiteName;
	/*shell常见问题名称*/
	private String shellCommonProblemName;
	/*app常见问题名称*/
	private String appCommonProblemName;
	/*版本更新文件exe*/
	private String verExePath;
	/*版本更新压缩包*/
	private String verZipPath;
	/*版本exe文件ID*/
	private Integer verExeFileId;
	//系统公告路径
	private String noticePath;
	//系统公告名称
	private String noticeName;

	/*更新压缩包32位路径*/
	private String  FilePath32;
	
	
	/*完整安装包32位*/
	private Integer patchFileId32;
	/*更新压缩包32位*/
	private Integer fileId32;
	/** 壳需要访问的新地址 **/
	private String newUrl;
	
	 

	public String getSysVersionType() {
		return sysVersionType;
	}

	public void setSysVersionType(String sysVersionType) {
		this.sysVersionType = sysVersionType;
	}

	public String getFilePath32() {
		return FilePath32;
	}

	public void setFilePath32(String filePath32) {
		FilePath32 = filePath32;
	}

	public Integer getPatchFileId32() {
		return patchFileId32;
	}

	public void setPatchFileId32(Integer patchFileId32) {
		this.patchFileId32 = patchFileId32;
	}

	public Integer getFileId32() {
		return fileId32;
	}

	public void setFileId32(Integer fileId32) {
		this.fileId32 = fileId32;
	}

	public String getNoticePath() {
		return noticePath;
	}

	public void setNoticePath(String noticePath) {
		this.noticePath = noticePath;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	/*内部完整安装包id*/
	private Integer internalFileId;
	
	/*内部压缩包id（里面包含  exe 和 zip）*/
	private Integer internalPatchFileId;
	
	/*测试压缩包id（里面可能包含任何）*/
	private Integer testPatchFileId;
	
	
	
	private String filePath;
	
	private String internalFilePath;
	
	private String testFilePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInternalFilePath() {
		return internalFilePath;
	}

	public void setInternalFilePath(String internalFilePath) {
		this.internalFilePath = internalFilePath;
	}

	public String getTestFilePath() {
		return testFilePath;
	}

	public void setTestFilePath(String testFilePath) {
		this.testFilePath = testFilePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getInternalFileId() {
		return internalFileId;
	}

	public void setInternalFileId(Integer internalFileId) {
		this.internalFileId = internalFileId;
	}

	public Integer getInternalPatchFileId() {
		return internalPatchFileId;
	}

	public void setInternalPatchFileId(Integer internalPatchFileId) {
		this.internalPatchFileId = internalPatchFileId;
	}

	public Integer getTestPatchFileId() {
		return testPatchFileId;
	}

	public void setTestPatchFileId(Integer testPatchFileId) {
		this.testPatchFileId = testPatchFileId;
	}

 
	
	
 
	public String getShellCommonProblemPath() {
		return shellCommonProblemPath;
	}

	public void setShellCommonProblemPath(String shellCommonProblemPath) {
		this.shellCommonProblemPath = shellCommonProblemPath;
	}

	public String getAppCommonProblemPath() {
		return appCommonProblemPath;
	}

	public void setAppCommonProblemPath(String appCommonProblemPath) {
		this.appCommonProblemPath = appCommonProblemPath;
	}

	public String getShellCommonProblemName() {
		return shellCommonProblemName;
	}

	public void setShellCommonProblemName(String shellCommonProblemName) {
		this.shellCommonProblemName = shellCommonProblemName;
	}

	public String getAppCommonProblemName() {
		return appCommonProblemName;
	}

	public void setAppCommonProblemName(String appCommonProblemName) {
		this.appCommonProblemName = appCommonProblemName;
	}

	public String getTelephoneName() {
		return telephoneName;
	}

	public void setTelephoneName(String telephoneName) {
		this.telephoneName = telephoneName;
	}

	public String getOfficialWebsiteName() {
		return officialWebsiteName;
	}

	public void setOfficialWebsiteName(String officialWebsiteName) {
		this.officialWebsiteName = officialWebsiteName;
	}

	public Integer getVerExeFileId() {
		return verExeFileId;
	}

	public void setVerExeFileId(Integer verExeFileId) {
		this.verExeFileId = verExeFileId;
	}

	public String getShellExePath() {
		return shellExePath;
	}

	public void setShellExePath(String shellExePath) {
		this.shellExePath = shellExePath;
	}

	public String getShellZipPath() {
		return shellZipPath;
	}

	public void setShellZipPath(String shellZipPath) {
		this.shellZipPath = shellZipPath;
	}

	public String getVerExePath() {
		return verExePath;
	}

	public void setVerExePath(String verExePath) {
		this.verExePath = verExePath;
	}

	public String getVerZipPath() {
		return verZipPath;
	}

	public void setVerZipPath(String verZipPath) {
		this.verZipPath = verZipPath;
	}

	public String getOfficialWebsitePath() {
		return officialWebsitePath;
	}

	public void setOfficialWebsitePath(String officialWebsitePath) {
		this.officialWebsitePath = officialWebsitePath;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getVerType() {
		return verType;
	}

	public void setVerType(String verType) {
		this.verType = verType;
	}

	public String getShellVersion() {
		return shellVersion;
	}

	public void setShellVersion(String shellVersion) {
		this.shellVersion = shellVersion;
	}

	public Integer getShellExeFileId() {
		return shellExeFileId;
	}

	public void setShellExeFileId(Integer shellExeFileId) {
		this.shellExeFileId = shellExeFileId;
	}

	public String getIosFileUrl() {
		return iosFileUrl;
	}

	public void setIosFileUrl(String iosFileUrl) {
		this.iosFileUrl = iosFileUrl;
	}

	public Integer getPatchFileId() {
		return patchFileId;
	}

	public void setPatchFileId(Integer patchFileId) {
		this.patchFileId = patchFileId;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getAppPathInfo() {
		return appPathInfo;
	}

	public void setAppPathInfo(String appPathInfo) {
		this.appPathInfo = appPathInfo;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getUpgradeMethod() {
		return upgradeMethod;
	}

	public void setUpgradeMethod(Integer upgradeMethod) {
		this.upgradeMethod = upgradeMethod;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public String getSystemTypeInfo() {
		return systemTypeInfo;
	}

	public void setSystemTypeInfo(String systemTypeInfo) {
		this.systemTypeInfo = systemTypeInfo;
	}

	public  String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType){
		this.systemType = systemType;
	}
	public  String getVersion() {
		return version;
	}
	public void setVersion(String version){
		this.version = version;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public  String getContent() {
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public  String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath){
		this.appPath = appPath;
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
        SysVersion other = (SysVersion) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSystemType() == null ? other.getSystemType() == null : this.getSystemType().equals(other.getSystemType()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAppPath() == null ? other.getAppPath() == null : this.getAppPath().equals(other.getAppPath()))
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
        result = prime * result + ((getSystemType() == null) ? 0 : getSystemType().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAppPath() == null) ? 0 : getAppPath().hashCode());
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
    public SysVersion copy(){
       SysVersion obj =  new SysVersion();
       obj.setSystemType(this.systemType);
       obj.setVersion(this.version);
       obj.setStatus(this.status);
       obj.setTitle(this.title);
       obj.setContent(this.content);
       obj.setAppPath(this.appPath);
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
       map.put("systemType",this.systemType);
       map.put("version",this.version);
       map.put("status",this.status);
       map.put("title",this.title);
       map.put("content",this.content);
       map.put("appPath",this.appPath);
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
	
	public String getNewUrl() {
		return newUrl;
	}

	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}
}
