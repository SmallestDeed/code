package com.sandu.api.fullhouse.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FullHouseDesignPlanCopy implements Serializable {

    @ApiModelProperty(value = "全屋方案id")
    @NotNull(message = "全屋方案id为空")
    private Integer fullHouseDesignPlanId;

    @ApiModelProperty(value = "主任务id")
    @NotNull(message = "主任务id为空")
    private Integer mainTaskId;

    @ApiModelProperty(value = "供求信息id")
    private Integer supplyDemandId;

    //消息id
    @ApiModelProperty(value = "聊天消息id")
    private String msgId;
}
