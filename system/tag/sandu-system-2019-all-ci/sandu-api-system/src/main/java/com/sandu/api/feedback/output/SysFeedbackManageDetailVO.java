package com.sandu.api.feedback.output;

import com.sandu.api.pic.model.po.ResPicPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台管理--反馈详情输出实体
 * @author WangHaiLin
 * @date 2018/12/13  18:27
 */
@Data
public class SysFeedbackManageDetailVO implements Serializable{

    @ApiModelProperty(value = "主键Id")
    private Integer feedbackId;

    @ApiModelProperty(value = "反馈用户Id")
    private Integer userId;

    @ApiModelProperty(value = "平台Id")
    private Integer platformId;

    @ApiModelProperty(value = "反馈用户openId")
    private String openId;

    @ApiModelProperty(value = "反馈用户登录名")
    private String userName;

    @ApiModelProperty(value = "企业Id")
    private Integer companyId;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "微信昵称")
    private String wxNickName;

    @ApiModelProperty(value = "用户头像Id")
    private Integer headPicId;

    @ApiModelProperty(value = "用户头像路径")
    private String headPicPath;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "反馈图像Ids")
    private String feedbackPics;

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
