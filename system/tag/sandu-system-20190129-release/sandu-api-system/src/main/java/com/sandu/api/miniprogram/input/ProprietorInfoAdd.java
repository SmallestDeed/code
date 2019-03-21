package com.sandu.api.miniprogram.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Yuxc
 * @date 2018/9/4
 */
@Data
public class ProprietorInfoAdd implements Serializable {

    @ApiModelProperty(value = "类型（0：0元设计，1：装修报价）")
    private Byte type;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称不能为空")
    @Size(min = 1, max = 20, message = "名称不能超过20个字符")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = "^(13[0-9]|14[56789]|15[0-3,5-9]|16[6]|17[012345678]|18[0-9]|19[89])\\d{8}$", message = "请输入正确的手机号码")
    private String mobile;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "小区名称")
    private String areaName;

    @ApiModelProperty(value = "房屋面积(平方米)")
    private String houseAcreage;

    @ApiModelProperty(value = "来源类型（0：PC，1：小程序）")
    @NotNull(message = "来源类型不能为空")
    private Integer sourceType;

    @ApiModelProperty(value = "户型")
    private String houseType;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "业务类型(0-业主信息;1-设计师信息;2-设计装修公司信息;3-设计师报名)")
    private Integer businessType;

    @ApiModelProperty(value = "公司类型(0-设计公司;1-装修公司)")
    private Integer companyType;

    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "方案Id")
    private Integer designplanId;

    @ApiModelProperty(value = "方案类型")
    private Integer designplanType;

    @ApiModelProperty(value = "预约用户Id")
    private Integer appointUserId;

    @ApiModelProperty(value = "广告投放渠道")
    @Size(min = 1, max = 100, message = "广告投放渠道，限{min}-{max}个字符")
    private String utmSource;

    @ApiModelProperty(value = "广告投放计划")
    @Size(min = 1, max = 100, message = "广告投放计划，限{min}-{max}个字符")
    private String utmCampaign;

    @ApiModelProperty(value = "广告投放组")
    @Size(min = 1, max = 100, message = "广告投放组，限{min}-{max}个字符")
    private String utmAdgroup;

    @ApiModelProperty(value = "广告投放关键词")
    @Size(min = 1, max = 100, message = "广告投放关键词，限{min}-{max}个字符")
    private String utmTerm;
}