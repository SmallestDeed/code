package com.sandu.api.account.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangHaiLin
 * @date 2018/6/12  17:52
 */
@Data
public class PayAccount implements Serializable {

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "消费金额")
    private Double consumeAmount;

    @ApiModelProperty(value = "剩余金额")
    private Double balanceAmount;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "平台Id")
    private Long platformId;

    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新者")
    private String modifier;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @ApiModelProperty(value = "所属平台分类")
    private String platformBusinessType;
}
