package com.sandu.api.servicepurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ServicesInfoVO implements Serializable {
    private static final long serialVersionUID = -8614847997059070172L;
    /**
     * 当前套餐id
     */
    @ApiModelProperty(value = "当前套餐id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long id;
    /**
     * 当前套餐名称
     */
    @ApiModelProperty(value = "当前套餐名称")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String servicesName;
    /**
     * 当前套餐状态(0-禁用;1-启用)
     */
    @ApiModelProperty(value = "当前套餐状态(0-禁用;1-启用)")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String servicesStatus;

    /**
     * 当前套餐类型(0-正式套餐;1-试用套餐)
     */
    @ApiModelProperty(value = "当前套餐类型(0-正式套餐;1-试用套餐)")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer servicesType;

    /**
     * 套餐价格
     */
    @ApiModelProperty(value = "套餐价格")
    @JsonInclude(JsonInclude.Include.ALWAYS)
   private List<ServicesPriceContentVO> servicesPriceVoList;

    /**
     * 套餐描述
     */
    @ApiModelProperty(value = "套餐描述")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String serviceDesc;

    /**
     * 剩余有效期/天
     */
    @ApiModelProperty(value = "剩余有效期/天")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer remainingTime;

    /**
     * 套餐用户类型id
     */
    @ApiModelProperty(value = "套餐用户类型id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String userScope;

    /**
     * 套餐用户类型中文名称
     */
    @ApiModelProperty(value = "套餐用户类型中文名称")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String userScopeName;
}
