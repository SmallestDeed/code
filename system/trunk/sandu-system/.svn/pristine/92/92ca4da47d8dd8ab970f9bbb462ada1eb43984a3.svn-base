package com.sandu.api.feedback.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 反馈问题答复
 * @author WangHaiLin
 * @date 2018/12/13  18:26
 */
@Data
public class SysFeedbackReply implements Serializable{

    @ApiModelProperty(value = "反馈Id",required = true)
    @NotNull(message = "反馈Id不能为空")
    private Long id;

    @ApiModelProperty(value = "反馈用户Id",required = true)
    @NotNull(message = "反馈用户Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "答复内容",required = true)
    @NotNull(message = "答复内容不能为空")
    private String replyContent;

}
