package com.sandu.api.servicepurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class ServicesUpdateInfoVO implements Serializable {

    private static final long serialVersionUID = 7018263015762492004L;
    /**
     * 套餐id
     */
    @ApiModelProperty(value = "套餐id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long id;
    /**
     * 套餐名称
     */
    @ApiModelProperty(value = "套餐名称")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String servicesName;
    /**
     * 套餐角色
     */
    @ApiModelProperty(value = "套餐名称")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<ServicesRoleVO> servicesRole;

    /**
     * 套餐描述
     */
    @ApiModelProperty(value = "套餐描述")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String serviceDesc;
}
