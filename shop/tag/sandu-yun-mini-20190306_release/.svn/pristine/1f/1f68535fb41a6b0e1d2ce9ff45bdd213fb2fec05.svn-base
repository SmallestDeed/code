package com.sandu.company.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺博文--列表--输出实体
 * @author WangHaiLin
 * @date 2018/8/10  15:11
 */
@Data
public class CompanyShopArticleListVO implements Serializable {

    @ApiModelProperty(value="博文Id")
    private Long articleId;

    @ApiModelProperty(value="博文标题")
    private String articleTitle;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty(value="封面图片Ids")
    private String picIds;
    @ApiModelProperty(value="封面图片路径")
    private String picPath;

    @ApiModelProperty(value = "浏览数量")
    private Integer browseCount;

    @ApiModelProperty(value = "发布时间")
    private Date releaseTime;

    @ApiModelProperty(value = "发布时间String形式")
    private String releaseTimeStr;

}
