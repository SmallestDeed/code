package com.sandu.api.feedback.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 反馈列表查询---前端输出实体
 * @author WangHaiLin
 * @date 2018/12/13  18:28
 */
@Data
public class SysFeedbackWebListVO implements Serializable{

    @ApiModelProperty(value = "反馈Id")
    private Integer feedbackId;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "反馈时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "恢复状态(0:未处理；1:已处理)")
    private Integer dealStatus;

    @ApiModelProperty(value = "是否已读(0:未读；1:已读)")
    private Integer isRead;

}
