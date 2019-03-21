package com.sandu.systemutil;

import com.sandu.api.user.model.LoginUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @ClassName BaseModel
 * @date 2018/11/6
 */

@Data
@ApiModel
public class BaseModel implements Serializable {
	/**
	 * 创建者
	 **/
	@ApiModelProperty(hidden = true)
	private String creator;
	/**
	 * 创建时间
	 **/
	@ApiModelProperty(hidden = true)
	private Date gmtCreate;
	/**
	 * 修改人
	 **/
	@ApiModelProperty(hidden = true)
	private String modifier;
	/**
	 * 修改时间
	 **/
	@ApiModelProperty(hidden = true)
	private Date gmtModified;
	/**
	 * 是否删除
	 **/
	@ApiModelProperty(hidden = true)
	private Integer isDeleted;


	@ApiModelProperty(hidden = true)
	private LoginUser loginUser;

	@ApiModelProperty(hidden = true)
	private String sysCode;

}
