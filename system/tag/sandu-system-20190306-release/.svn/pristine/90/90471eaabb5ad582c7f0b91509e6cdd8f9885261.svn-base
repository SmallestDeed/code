package com.sandu.api.act.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 领取消息新增实体
 * @author WangHaiLin
 * @date 2018/11/22  15:35
 */
@Data
public class WxActBargainAwardMsgAddForm implements Serializable {

    @ApiModelProperty(value = "活动id",required = true)
    @NotNull(message = "活动id不能为空")
    private String actId;

    @ApiModelProperty(value = "消息内容",required = true)
    @NotNull(message = "消息内容不能为空")
    private String message;

    @ApiModelProperty(value = "appId",required = true)
    @NotNull(message = "appId不能为空")
    private String appId;

}
