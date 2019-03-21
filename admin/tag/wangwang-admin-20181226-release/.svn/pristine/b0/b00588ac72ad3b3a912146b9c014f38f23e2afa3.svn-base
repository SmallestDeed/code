package com.sandu.api.basesupplydemand.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */
@Data
public class BasesupplydemandVO implements Serializable {



    @ApiModelProperty(value = "id")
    private Integer id;
        
    @ApiModelProperty(value = "信息类别(1:供应，2:需求)")
    private Integer type;
        
    @ApiModelProperty(value = "信息分类")
    private String supplyDemandCategoryId;

    @ApiModelProperty(value = "分类名称")
    private String supplyDemandCategoryName;

    @ApiModelProperty(value = "省编码")
    private String province;
    @ApiModelProperty(value = "市编码")
    private String city;
    @ApiModelProperty(value = "区编码")
    private String district;
    @ApiModelProperty(value = "街道编码")
    private String street;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "封面图id")
    private String coverPicId;

    @ApiModelProperty(value = "封面图路径")
    private String coverPicPath;

    @ApiModelProperty(value = "封面图路径集")
    private List<String> coverPicPaths;

    @ApiModelProperty(value = "信息标题")
    private String title;
        
        
    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    @ApiModelProperty(value = "「推荐」")
    private Long recommendedTime;

    private Date recommendedDate;

    private Integer[] categoryId;
}
