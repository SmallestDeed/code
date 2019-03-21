package com.sandu.api.house.output;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 烘焙任务响应类
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/27
 */
@Data
public class DrawBakeTaskVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键id")
	private Long taskId;

	@ApiModelProperty("房型id")
	private Long houseId;

	@ApiModelProperty("创建人")
	private String creator;

	@ApiModelProperty("创建时间")
	private Date gmtCreate;

	@ApiModelProperty("修改人")
	private String modifier;

	@ApiModelProperty("修改时间")
	private Date gmtModified;

	@ApiModelProperty("烘焙任务详情")
	private DrawBakeTaskDetailVO taskDetail;
	
}
