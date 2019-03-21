package com.sandu.api.gift.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/5/8 15:40
 */
@Data
public class GiftAdd implements Serializable {

    @ApiModelProperty(value = "礼品编号", required = true)
    @Size(min = 1, max = 20, message = "名称长度限{min}-{max}个字符")
    private String code;

    @ApiModelProperty(value = "礼品名称", required = true)
    @NotNull(message = "名称不能为空")
    @Size(min = 1, max = 50, message = "名称长度限{min}-{max}个字符")
    private String name;

    @ApiModelProperty(value = "兑换说明")
    @Size(min = 0, max = 20, message = "兑换说明长度限{min}-{max}个字符")
    private String explain;

    @ApiModelProperty(value = "介绍")
    private String introduce;

    @ApiModelProperty(value = "状态:0未上架1上架")
    @Range(min = 0, max = 1, message = "无效状态,限{min}-{max}间整数；0:未上架,1:上架")
    private Integer isPutaway;

    @ApiModelProperty(value="库存")
    @NotNull(message = "库存不能为空")
    private Integer inventory;

    @ApiModelProperty(value="价格")
    @NotNull(message = "价格不能为空")
    private double price;

    @ApiModelProperty(value="积分")
    @NotNull(message = "积分不能为空")
    private int point;

    @ApiModelProperty(value="配送费用")
    @NotNull(message = "配送费不能为空")
    private double expressFee;

    @ApiModelProperty(value="操作人")
    private String creator;

    @ApiModelProperty(value="系统编码")
    private String sysCode;
}
