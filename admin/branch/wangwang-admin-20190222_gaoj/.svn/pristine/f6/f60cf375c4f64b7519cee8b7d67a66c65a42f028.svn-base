package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DesignPlanPushAwayPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanPushAwayPO implements Serializable{

    @ApiModelProperty(value = "平台id",required = true)
    @Size(min = 0,max = 6,message = "平台id列表不合法")
    private List<Integer> platformIds;

    @NotNull(message = "方案id不能为空")
    @Min(value = 0,message = "方案id不合法")
    @ApiModelProperty(value = "方案id", required = true)
    private Integer planId;
}
