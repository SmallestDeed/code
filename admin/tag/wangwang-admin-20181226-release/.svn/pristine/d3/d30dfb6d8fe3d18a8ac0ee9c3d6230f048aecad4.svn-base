package com.sandu.api.solution.model.po;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DesignPlanChannelShelfPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanChannelShelfPO implements Serializable {

    @ApiModelProperty(value = "上架的地方")
    @NotNull(message = "上架的地方不能为空")
    @Size(min = 0,message = "上架的状态不合法")
    private List<String> shelfStatus;

    @ApiModelProperty(value = "方案id", required = true)
    @NotNull(message = "方案id不能为空")
    @Min(value = 0,message = "方案id不合法")
    private Integer planId;
    
    @JsonIgnore
    private boolean flag = false;
}
