package com.sandu.api.house.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 地理区域小区信息对象
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Data
public class BaseAreaBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("地理区域id")
	private Long id;

	@ApiModelProperty("区域名称")
	private String areaName;

	@ApiModelProperty("区域编码")
	private String areaCode;

	@ApiModelProperty("父子区域关系")
	private String longCode;

	@ApiModelProperty("父id")
	private String pid;

	@ApiModelProperty("等级（1,2,3）")
	private Integer levelId;

	@ApiModelProperty("区域下子区域")
	private List<BaseAreaBO> childAreas;
}
