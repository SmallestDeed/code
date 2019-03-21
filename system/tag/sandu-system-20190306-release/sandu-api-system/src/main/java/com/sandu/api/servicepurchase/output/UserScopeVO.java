package com.sandu.api.servicepurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 适用用户下拉框选择
 * @author 欧小琼
 */
@Data
public class UserScopeVO implements Serializable {

    private static final long serialVersionUID = 1405225442235375125L;
    /** 价格id*/
    @ApiModelProperty(value = "用户类型id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String userTypeId;

    /** 价格名称*/
    @ApiModelProperty(value = "用户类型名称")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String userTypeName;

}