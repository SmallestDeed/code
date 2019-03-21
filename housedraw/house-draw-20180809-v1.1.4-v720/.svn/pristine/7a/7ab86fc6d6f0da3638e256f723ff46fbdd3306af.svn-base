package com.sandu.api.house.input;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 烘焙回调请求对象
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Data
public class DrawBakeTaskResponseNew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("烘焙任务id")
	private Long taskId;
	
	@ApiModelProperty("任务详情id")
	private Long taskDetailId;
	
	@ApiModelProperty("户型id")
	private Long houseId;

	@ApiModelProperty("状态 1:成功 or 2：失败")
	private Integer status;

	@ApiModelProperty("消息结果")
	private String message;

	@ApiModelProperty("任务中的空间数据")
	private DrawSpaceCommonDataNew spaceCommonData;
}
