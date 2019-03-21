package com.sandu.api.banner.output;

import com.sandu.api.banner.model.BaseBannerAd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础-广告 输出实体
 * @author WangHaiLin
 * @date 2018/5/16  20:15
 */
@Data
public class BaseBannerAdVO implements Serializable {

    @ApiModelProperty(value = "主键Id")
    private Integer bannerId;

    @ApiModelProperty(value = "所在位置")
    private Integer positionId;

    @ApiModelProperty(value = "banner名称")
    private String bannerName;

    @ApiModelProperty(value = "资源Id")
    private Integer resBannerPicId;

    @ApiModelProperty(value = "ModelId")
    private Integer refModelId;

    @ApiModelProperty(value = "状态(0:关闭;1:开启)")
    private Integer bannerStatus;

    @ApiModelProperty(value = "排序")
    private Integer bannerSequence;

    @ApiModelProperty(value = "系统编码")
    private String bannerSysCode;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "修改者")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "跳转路径")
    private String skipPath;



}
