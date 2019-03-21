package com.sandu.api.house.output;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 户型绘制烘焙任务详情表
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/27
 */
@Data
public class DrawBakeTaskDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键id")
	private Long taskDetailId;

	@ApiModelProperty("空间id")
	private Long spaceId;

	@ApiModelProperty("空间编码")
	private String spaceCode;
	
	@ApiModelProperty("空间文件ID")
	private Long spaceFileId;

	@ApiModelProperty("空间文件路径")
	private String spaceFilePath;

	@ApiModelProperty("产品信息")
	private List<DrawProductDataVO> productData;
}
