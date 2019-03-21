package com.sandu.api.customer.input.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerAlotZoneQuery implements Serializable {
    private static final long serialVersionUID = -7228833348783580948L;
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("厂商id")
    private Integer companyId;

    @ApiModelProperty("经销商id")
    private Integer channelCompanyId;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("长编码")
    private String longCode;

    @ApiModelProperty("来源类型")
    private Integer sourceType;

    @ApiModelProperty("分配次数")
    private Integer allotCount;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改者")
    private String modifier;

    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("是否删除（0未删除；1已删除）")
    private Integer isDeleted;

    @ApiModelProperty("当前页码")
    private Integer start;

    @ApiModelProperty("每页行数")
    private Integer limit;


}