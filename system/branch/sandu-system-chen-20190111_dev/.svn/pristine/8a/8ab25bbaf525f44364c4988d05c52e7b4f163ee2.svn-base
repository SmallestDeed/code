package com.sandu.api.shop.output;

import com.sandu.api.pic.model.ResPic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺博文--详情展示--输出实体
 * @author WangHaiLin
 * @date 2018/8/9  10:36
 */
@Data
public class CompanyShopArticleDetailVO  implements Serializable{
    @ApiModelProperty(value = "博文Id")
    private Long articleId;

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;

    @ApiModelProperty(value = "博文标题")
    private String title;

    @ApiModelProperty(value = "博文内容")
    private String content;

    @ApiModelProperty(value = "图片Ids")
    private String picIds;

    @ApiModelProperty(value = "博文图片集合")
    private List<ResPic> resPics;

}
