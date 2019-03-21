package com.sandu.api.gift.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/27 20:45
 */
@Data
public class GiftQuery implements Serializable {

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "礼品名称")
    private String name;

    @ApiModelProperty(value = "状态查询")
    private Integer isPutaway;

    @ApiModelProperty(value = "页码")
    @Min(value = 1, message = "无效页码")
    private Integer page;

    @ApiModelProperty(value = "每页记录数")
    private Integer limit;

    @ApiModelProperty(value = "排序字段")
    private String order;

    @ApiModelProperty(value = "排序方式")
    private String sort;
}
