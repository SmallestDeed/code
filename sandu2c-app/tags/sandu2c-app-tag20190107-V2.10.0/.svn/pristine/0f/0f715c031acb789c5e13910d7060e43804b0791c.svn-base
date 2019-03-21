package com.sandu.banner.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础-广告 查询广告列表输出实体
 * @author WangHaiLin
 * @date 2018/5/11  10:13
 */
@Data
public class BaseBannerListVO implements Serializable {

    @ApiModelProperty(value = "所在位置Id")
    private Integer positionId;
    @ApiModelProperty(value = "位置编码(system:module:page:positon)")
    private String positionCode;
    @ApiModelProperty(value = "位置类型(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)")
    private Integer type;
    @ApiModelProperty(value = "位置名称")
    private String positionName;

    @ApiModelProperty(value = "BannerId")
    private Integer bannerId;
    @ApiModelProperty(value = "banner名称")
    private String  bannerName;
    @ApiModelProperty(value = "位置类型对应Id(店铺id,企业id,平台id)")
    private Integer refModelId;

    @ApiModelProperty(value = "图片资源Id")
    private Integer refPicId;
    @ApiModelProperty(value = "图片路径")
    private String picPath;


}
