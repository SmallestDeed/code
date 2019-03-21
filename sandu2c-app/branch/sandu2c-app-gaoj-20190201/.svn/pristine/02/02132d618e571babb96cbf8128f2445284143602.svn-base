package com.sandu.decorateorderscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DecorateOrderScore implements Serializable{
	
    /**
	 */
	private static final long serialVersionUID = -6327890663341441267L;

    @ApiModelProperty(hidden = true)
	private Long id;

	@ApiModelProperty(value = "订单orderId", required = true)
	@NotNull(message = "订单ID不能为空")
	@Min(value = 1, message = "订单ID不能为空")
    private Long orderId;

	@ApiModelProperty(value = "企业ID", required = true)
	@NotNull(message = "企业ID不能为空")
	@Min(value = 1, message = "企业ID不能为空")
    private Long companyId;

	@ApiModelProperty(value = "评分", required = true)
	@NotNull(message = "请打分评价")
	@Min(value = 1, message = "请打分评价")
    private Integer score;

    @ApiModelProperty(value = "评价",name="评价")
    private String remark;

    @ApiModelProperty(value = "图片ID",name="图片ID")
    private String picIds;

    @ApiModelProperty(value = "isAnonymous", name="是否匿名")
    private Integer isAnonymous;
    
    @ApiModelProperty(value = "messageId", name="消息id")
    private Integer messageId;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(hidden = true)
    private String creator;

    @ApiModelProperty(hidden = true)
    private Date gmtCreate;

    @ApiModelProperty(hidden = true)
    private String modifier;

    @ApiModelProperty(hidden = true)
    private Date gmtModified;

    @ApiModelProperty(hidden = true)
    private Integer isDeleted;
}