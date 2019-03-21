package com.nork.system.model.small;

import java.io.Serializable;


/**   
 * @Title: ResFile.java 
 * @Package com.nork.system.model.small
 * @Description:系统模块-文件资源库
 * @createAuthor pandajun 
 * @CreateDate 2015-07-02 17:36:00
 * @version V1.0   
 */
public class ResFileSmall  implements Serializable{
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
	/**  文件大小  **/
	private String fileSize;
	/**  文件后缀  **/
	private String fileSuffix;
	/**  文件路径  **/
	private String filePath;
	/**  文件描述  **/
	private String fileDesc;
	/**  文件排序  **/
	private Integer fileOrdering;
	/**  业务编码  **/
	private Integer businessId;

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
	public  Integer getFileOrdering() {
		return fileOrdering;
	}
	public void setFileOrdering(Integer fileOrdering){
		this.fileOrdering = fileOrdering;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
}
