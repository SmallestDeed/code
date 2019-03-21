package com.sandu.resource.model;

import com.sandu.base.model.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 文件资源
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "文件资源", description = "文件资源")
public class ResFile extends BaseVo<ResFile> {
	private static final long serialVersionUID = 1120844438010321091L;
	@ApiModelProperty(value = "文件编码")
	private String fileCode;
	@ApiModelProperty(value = "文件名称")
	private String fileName;
	@ApiModelProperty(value = "文件路径")
	private String filePath;

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
