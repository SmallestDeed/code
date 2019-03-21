package com.sandu.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/12/22 9:57
 */
@Data
public class BaseQuery implements Serializable {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private Integer page;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页记录数")
    private Integer limit;
}
