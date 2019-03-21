package com.sandu.living.model.input;

import com.sandu.common.model.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseLivingSearch extends Mapper implements Serializable {

    @ApiModelProperty(value = "用户ID", dataType = "Integer", hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "区域CODE", dataType = "String")
    private String areaId;
    @ApiModelProperty(value = "小区名称", dataType = "String")
    private String livingName;

}
