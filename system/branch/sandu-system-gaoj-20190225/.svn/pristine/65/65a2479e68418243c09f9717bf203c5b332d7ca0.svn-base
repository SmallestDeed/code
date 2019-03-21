package com.sandu.api.feedback.output;

import com.sandu.api.pic.model.po.ResPicPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 反馈列表查询---管理后台输出实体
 * @author WangHaiLin
 * @date 2018/12/13  18:28
 */
@Data
public class SysFeedbackAdminListVO implements Serializable{

    @ApiModelProperty(value = "反馈Id")
    private Integer feedbackId;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "反馈图像集合")
    private List<ResPicPO> feedbackPicPath;

    @ApiModelProperty(value = "反馈时间")
    private String gmtCreate;

    @ApiModelProperty(value = "处理状态(0:未处理；1:已处理)")
    private Integer dealStatus;

    @ApiModelProperty(value = "答复时间")
    private String gmtReply;

    @ApiModelProperty(value = "答复内容")
    private String replyContent;

    @ApiModelProperty(value = "评价(0:满意；1:不满意)")
    private Integer estimate;



}
