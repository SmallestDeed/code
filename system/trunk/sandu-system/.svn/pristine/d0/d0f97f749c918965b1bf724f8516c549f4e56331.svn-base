package com.sandu.api.feedback.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-feedback
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-12-13 18:05
 */
@Data
public class SysFeedbackAdd implements Serializable {

    @ApiModelProperty(value = "反馈用户Id",required = true)
    @NotNull(message = "反馈用户Id不能为空")
    private Integer userId;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "反馈内容",required = true)
    @NotNull(message = "反馈内容不能为空")
    private String feedbackContent;

    @ApiModelProperty(value = "反馈图像Ids(逗号隔开，最多四个)")
    private String feedbackPics;

    @ApiModelProperty(value = "msgId")
    private String msgId;

    private Integer platformId;

}
