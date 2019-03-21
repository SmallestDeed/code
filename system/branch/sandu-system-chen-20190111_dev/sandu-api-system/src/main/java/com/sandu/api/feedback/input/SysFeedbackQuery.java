package com.sandu.api.feedback.input;

import com.sandu.common.BaseQuery;
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
public class SysFeedbackQuery extends BaseQuery implements Serializable {

    @ApiModelProperty(value = "平台Id",required = true)
    @NotNull(message = "平台Id不能为空")
    private Integer platformId;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "微信昵称")
    private String wxNickName;

    @ApiModelProperty(value = "评价")
    private Integer estimate;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "是否处理")
    private Integer dealStatus;

    //分页查询用，非前端传值
    private Integer start;

}
