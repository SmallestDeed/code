package com.sandu.api.house.input;

import java.io.Serializable;
import java.util.List;

import com.sandu.api.house.model.DrawSpaceCommon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 户型绘制,保存数据DTO->空间数据
 * 备注:功能不完整,需要考虑的:
 * 1.空间/样板房图片问题
 * 
 * @author huangsongbo
 */
@Data
public class DrawSpaceCommonDTO implements Serializable{

	private static final long serialVersionUID = -1051891321442031556L;

	@ApiModelProperty("空间类型")
	private String spaceCommonFunctionValueKey;
	
	@ApiModelProperty("空间面积")
	private String spaceCommonArea;

	@ApiModelProperty("实际面积")
	private String spaceCommonRealArea;

	@ApiModelProperty("空间形状(默认为长方形)")
	private String spaceCommonShape;
	
	@ApiModelProperty("空间还原/烘培文件id")
	private Long spaceCommonFileId;
	
	@ApiModelProperty("空间id")
	private Long spaceCommonId;
	
	@ApiModelProperty("空间长")
	private String spaceCommonMainLength;
	
	@ApiModelProperty("空间宽")
	private String spaceCommonMainWidth;
	
	@ApiModelProperty("空间关联产品数据")
	private List<DrawBaseProductDTO> drawBaseProductDTOList;
	
	@ApiModelProperty("保存之后的空间数据")
	private DrawSpaceCommon drawSpaceCommon;
	
	private Long designTempletId;

	// 是否有天花区域；0、否；1是
	private Integer isRegionalCeiling;
	
	/**
	 * 空间图片
	 */
	private Integer spacePicId;
}
