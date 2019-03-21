package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DesignPlanAllotPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
@ApiModel("分配")
public class DesignPlanAllotPO implements Serializable{

    @ApiModelProperty(value = "方案id",required = true)
    @NotNull(message = "方案id不能为空")
    @Min(value = 0,message = "方案id不合法")
    private Long planId;

    @ApiModelProperty(value = "分配到哪里 2c线上 2b 渠道",required = true)
    @Size(min = 0,max = 6,message = "size最少为 {min},最大 {max}")
    private List<String> targetPlatform;

}
