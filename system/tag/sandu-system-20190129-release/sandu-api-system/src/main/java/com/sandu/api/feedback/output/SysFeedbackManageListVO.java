package com.sandu.api.feedback.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 反馈列表查询---管理后台输出实体
 * @author WangHaiLin
 * @date 2018/12/13  18:28
 */
@Data
public class SysFeedbackManageListVO implements Serializable{

    @ApiModelProperty(value = "反馈Id")
    private Integer feedbackId;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "反馈用户openId(来源是随选网和企业小程序的反馈展示)")
    private String openId;

    @ApiModelProperty(value = "反馈用户登录名(来源是移动端，商家管理后台，通用版展示)")
    private String userName;

    @ApiModelProperty(value = "企业Id(除开来源是随选网其他都展示)")
    private Integer companyId;

    @ApiModelProperty(value = "企业名称(除开来源是随选网其他都展示)")
    private String companyName;

    @ApiModelProperty(value = "微信昵称(来源是随选网和企业小程序的反馈展示)")
    private String wxNickName;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "处理状态(0:未处理；1:已处理)")
    private Integer dealStatus;

    @ApiModelProperty(value = "点评结果(0:满意；1:不满意)")
    private Integer estimate;

    @ApiModelProperty(value = "反馈时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "头像路径")
    private String headPicPath;

}
