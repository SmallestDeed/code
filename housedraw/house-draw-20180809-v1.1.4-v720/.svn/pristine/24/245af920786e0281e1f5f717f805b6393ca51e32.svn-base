package com.sandu.api.house.input;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 样板房信息(空间灯光文件/产品信息)
 * 
 * @author huangsongbo
 *
 */
@Data
public class DrawDesignTempletDTO implements Serializable {

	private static final long serialVersionUID = -973418624972522101L;

	@ApiModelProperty("空间pc端灯光文件(白天)")
	private Long daylightPcU3dModelId;

	@ApiModelProperty("空间pc端灯光文件(黄昏)")
	private Long dusklightPcU3dModelId;

	@ApiModelProperty("空间pc端灯光文件(黑夜)")
	private Long nightlightPcU3dModelId;

	@ApiModelProperty("样板房配置文件")
	private Long designTempletConfigFileId;

	@ApiModelProperty("样板房模型文件")
	private Long designTempletPcModelU3dId;

	@ApiModelProperty("样板房产品信息")
	private List<DrawBaseProductDTO> drawBaseProductDTOList;
}