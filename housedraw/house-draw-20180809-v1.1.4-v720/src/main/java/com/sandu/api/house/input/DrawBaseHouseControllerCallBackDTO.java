package com.sandu.api.house.input;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DrawBaseHouseController -> callBack 组装json对应的entity
 * 
 * @author huangsongbo
 *
 */
@Data
public class DrawBaseHouseControllerCallBackDTO implements Serializable {

	private static final long serialVersionUID = 5688597716238402119L;
	
	/**
	 * 任务状态 ：0 成功、1、烘焙失败
	 */
	private Integer status;
	
	private String message;
	
	@ApiModelProperty("烘培任务")
	private Long taskDetailId;

	@ApiModelProperty("样板房信息")
	private DrawDesignTempletDTO drawDesignTempletDTO;

}
