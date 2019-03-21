package com.sandu.api.banner.input;

import com.sandu.api.banner.model.BaseBannerAd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 基础-广告修改入参实体
 * @author WangHaiLin
 * @date 2018/5/17  13:52
 */
@Data
public class BaseBannerAdUpdate implements Serializable {

    @ApiModelProperty(value = "广告Id", required = true)
    @NotNull(message = "广告Id不能为空")
    private Integer bannerId;

    @ApiModelProperty(value = "位置Id")
    private Integer positionId;

    @ApiModelProperty(value = "banner名称")
    @Size(min = 1, max = 80, message = "banner名称长度度限{min}-{max}个字符")
    private String bannerName;

    @ApiModelProperty(value = "图片资源Id")
    private Integer resBannerPicId;

    @ApiModelProperty(value = "modelId(店铺id or 企业id or 平台id)")
    private Integer refModelId;

    @ApiModelProperty(value = "状态(0:关闭;1:开启)")
    @Range(min = 0, max = 1, message = "无效状态,限{min}-{max}间整数")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sequence;

    @ApiModelProperty(value = "跳转路径")
    private String skipPath;



}
