package com.sandu.api.house.output;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 文件输出类
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Data
public class DrawFileDataVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件大小")
	private String fileSize;

	@ApiModelProperty("文件路径")
	private String filePath;

	@ApiModelProperty("文件类型")
	private String fileType;

	@ApiModelProperty("文件编码")
	private String fileCode;

	@ApiModelProperty("文件后缀")
	private String fileSuffix;

	@ApiModelProperty("文件原名称")
	private String fileOriginalName;

	@ApiModelProperty("图片高度")
	private String height;

	@ApiModelProperty("图片宽度")
	private String width;
}