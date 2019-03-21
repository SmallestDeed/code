package com.sandu.api.feedback.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 反馈用户评价
 * @author WangHaiLin
 * @date 2018/12/13  18:23
 */
@Data
public class SysFeedbackEstimate implements Serializable {

    @ApiModelProperty(value = "反馈Id",required = true)
    @NotNull(message = "反馈Id不能为空")
    private Long id;

    @ApiModelProperty(value = "评价(0:满意；1:不满意)",required = true)
    @NotNull(message = "评价不能为空")
    private Integer estimate;

    @ApiModelProperty("msgId")
    private String msgId;

}
