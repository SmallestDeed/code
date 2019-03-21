package com.sandu.api.shop.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺博文列表展示实体
 * @author WangHaiLin
 * @date 2018/8/9  10:31
 */
@Data
public class CompanyShopArticleListVO implements Serializable {

    @ApiModelProperty(value="博文Id")
    private Long articleId;

    @ApiModelProperty(value="博文标题")
    private String articleTitle;

    @ApiModelProperty(value="图片路径")
    private String picPath;

    @ApiModelProperty(value="创建时间")
    private Date createDate;

    @ApiModelProperty(value="发布状态(发布：1,未发布：0)")
    private Integer releaseStatus;
}
