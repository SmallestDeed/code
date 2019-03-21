package com.sandu.api.proprietorinfo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProprietorInfoListQuery implements Serializable {
    /**
     * 需求类型
     */
    private Byte type;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 客户类型
     */
    private Byte customerTypeValue;
    /**
     * 下一次回访时间
     */
    private Date nextTime;
    /**
     * 当前页page
     */
    @NotNull(message = "当前页不能为空")
    @Min(value = 1, message = "当前页最小为1")
    private Integer page;
    /**
     * 每页数量limit
     */
    @NotNull(message = "pageSize不能为空")
    @Min(value = 1, message = "pageSize不能小于等于0")
    private Integer pageSize;

    @ApiModelProperty("预算")
    private Byte decorateBudget;

    @ApiModelProperty("装修类型")
    private Byte decorateHouseType;

    @ApiModelProperty("装修方式")
    private Byte decorateType;

    @ApiModelProperty("倾向风格")
    private Integer decorateStyle;

    @ApiModelProperty("是否有指定设计方案")
    private Boolean hasDesign;

    private String userName;

    private String mobile;
}
