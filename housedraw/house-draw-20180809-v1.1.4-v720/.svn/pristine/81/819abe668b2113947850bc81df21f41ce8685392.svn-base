package com.sandu.api.house.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Data
public class DrawBaseProductBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** */
	private Long id;

	/** 产品编码 */
	private String productCode;
	
	private String productName;

	/** 产品分类 详见数据字典 productType */
	private Integer productType;

	/** 产品小分类 详见数据字典 */
	private Integer productSmallType;

	/** 产品排序 前端传递墙面的序号 用于拼接白膜墙面名称 */
	private String productSort;

	private String productLength;

	private String productWidth;

	private String productHeight;

	/** 空间id */
	private Long spaceId;
	
	private Long spacecomonId;

	@ApiModelProperty("文件id")
	private Long fileId;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件大小")
	private String fileSize;

	@ApiModelProperty("文件路径")
	private String filePath;

	@ApiModelProperty("文件编码")
	private String fileCode;

	@ApiModelProperty("文件后缀")
	private String fileSuffix;

	@ApiModelProperty("文件原名称")
	private String fileOriginalName;
	
	private String uniqueId;
}
