package com.sandu.api.gift.output;

import com.sandu.api.gift.model.GiftImage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 15:48
 */
@Data
public class GiftVO implements Serializable {

    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "礼品名称")
    private String name;

    @ApiModelProperty(value = "兑换说明")
    private String explain;

    @ApiModelProperty(value = "介绍")
    private String introduce;

    @ApiModelProperty(value = "0未上架1上架")
    private int isPutaway;

    @ApiModelProperty(value = "库存")
    private int inventory;

    @ApiModelProperty(value = "价格")
    private double price;

    @ApiModelProperty(value = "积分")
    private int point;

    @ApiModelProperty(value = "快递费")
    private double expressFee;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "modified")
    private Date modified;

    @ApiModelProperty(value = "删除0正常1删除")
    private int isDeleted;

    @ApiModelProperty(value = "图片列表")
    private List<GiftImage> images;

    @ApiModelProperty(value = "缩略图名称")
    private String small;
}
