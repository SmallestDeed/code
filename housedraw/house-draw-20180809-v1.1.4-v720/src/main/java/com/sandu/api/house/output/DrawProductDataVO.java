package com.sandu.api.house.output;

import java.io.Serializable;
import java.util.List;

import com.sandu.api.house.model.DrawResFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 产品输出类
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Data
public class DrawProductDataVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("产品id")
	private Long productId;

	@ApiModelProperty("产品编码")
	private String productCode;

	@ApiModelProperty("产品分类 详见数据字典 productType")
	private Integer productType;

	@ApiModelProperty("产品小分类 详见数据字典")
	private Integer productSmallType;

	@ApiModelProperty("产品排序 前端传递墙面的序号 用于拼接白膜墙面名称")
	private String productSort;

	@ApiModelProperty("长度")
	private String length;

	@ApiModelProperty("宽度")
	private String width;

	@ApiModelProperty("高度")
	private String height;

	@ApiModelProperty("空间id")
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

	@ApiModelProperty("产品文件信息")
	private List<DrawResFile> productFile;
	
	private String uniqueId;
	
	@ApiModelProperty("产品类型:1:硬装;2:软装;3:public(窗户,栏杆等等)")
	private Integer modelType;
}