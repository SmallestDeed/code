package com.nork.system.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: ResVersion.java 
 * @Package com.nork.system.model.small
 * @Description:系统版本-系统版本资源表
 * @createAuthor pandajun 
 * @CreateDate 2017-08-17 11:41:05
 * @version V1.0   
 */
public class ResVersionSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  文件编码  **/
	private String fileCode;
	/**  文件名称  **/
	private String fileName;
	/**  文件原名称  **/
	private String fileOriginalName;
	/**  文件类型  **/
	private String fileType;
	/**  文件大小  **/
	private String fileSize;
	/**  文件后缀  **/
	private String fileSuffix;
	/**  文件级别  **/
	private String fileLevel;
	/**  文件路径  **/
	private String filePath;
	/**  文件描述  **/
	private String fileDesc;
	/**  文件排序  **/
	private String fileOrdering;
	/**  版本Id  **/
	private String businessIds;
	/**  fileKey  **/
	private String fileKey;
	/**  file_keys  **/
	private String fileKeys;
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

	public  String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode){
		this.fileCode = fileCode;
	}
	public  String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public  String getFileOriginalName() {
		return fileOriginalName;
	}
	public void setFileOriginalName(String fileOriginalName){
		this.fileOriginalName = fileOriginalName;
	}
	public  String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	public  String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize){
		this.fileSize = fileSize;
	}
	public  String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix){
		this.fileSuffix = fileSuffix;
	}
	public  String getFileLevel() {
		return fileLevel;
	}
	public void setFileLevel(String fileLevel){
		this.fileLevel = fileLevel;
	}
	public  String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public  String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc){
		this.fileDesc = fileDesc;
	}
	public  String getFileOrdering() {
		return fileOrdering;
	}
	public void setFileOrdering(String fileOrdering){
		this.fileOrdering = fileOrdering;
	}
	public  String getBusinessIds() {
		return businessIds;
	}
	public void setBusinessIds(String businessIds){
		this.businessIds = businessIds;
	}
	public  String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey){
		this.fileKey = fileKey;
	}
	public  String getFileKeys() {
		return fileKeys;
	}
	public void setFileKeys(String fileKeys){
		this.fileKeys = fileKeys;
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
