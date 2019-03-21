package com.sandu.api.area.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 行政区域 查询 入参
 * @Date 2018/6/1 0001 13:55
 * @Modified By
 */
@Data
public class BaseAreaListQuery implements Serializable {

    @ApiModelProperty(value = "行政区域code", required = true)
    @NotBlank(message = "区域code不能为空")
    private String areaCode;


    @ApiModelProperty(value = "U3D标识")
    private String msgId;

}
