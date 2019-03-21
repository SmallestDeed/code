package com.sandu.api.banner.input;

import com.google.common.base.Strings;
import com.sandu.api.banner.model.BaseBannerAd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author WangHaiLin
 * @date 2018/5/16  14:17
 */
@Data
public class BaseBannerAdAdd implements Serializable {

    @ApiModelProperty(value = "位置Id", required = true)
    @NotNull(message = "位置Id不能为空")
    private Integer positionId;

    @ApiModelProperty(value = "banner名称")
    @Size(min = 1, max = 80, message = "banner名称长度度限{min}-{max}个字符")
    private String bannerName;

    @ApiModelProperty(value = "图片资源Id", required = true)
    @NotNull(message = "图片资源Id不能为空")
    private Integer resBannerPicId;

    @ApiModelProperty(value = "modelId(店铺id or 企业id or 平台id)", required = true)
    @NotNull(message = "modelId不能为空")
    private Integer refModelId;

    @ApiModelProperty(value = "状态(0:关闭;1:开启(默认开启))")
    @Range(min = 0, max = 1, message = "无效状态,限{min}-{max}间整数")
    private Integer status;

    @ApiModelProperty(value = "排序")
    @Min(value = 0,message = "排序不能小于零")
    private Integer sequence;

    @ApiModelProperty(value = "系统编码")
    @Size(min = 1, max = 80, message = "系统编码长度度限{min}-{max}个字符")
    private String sysCode;

    private String url;



    //数据转换 BaserBannerAdd 转换成 BaseBannerAd
    public BaseBannerAd getBannerFromBannerAdd(BaseBannerAdAdd bannerAdd){
        BaseBannerAd banner=new BaseBannerAd();
        banner.setPositionId(bannerAdd.getPositionId());
        banner.setName(bannerAdd.getBannerName());
        banner.setResBannerPicId(bannerAdd.getResBannerPicId());
        banner.setRefModelId(bannerAdd.getRefModelId());
        banner.setSequence(bannerAdd.getSequence());
        banner.setStatus(bannerAdd.getStatus()==null?1:bannerAdd.getStatus());
        banner.setSysCode(Strings.nullToEmpty(bannerAdd.getSysCode()));
        banner.setUrl(bannerAdd.getUrl());
        return banner;
    }

}
