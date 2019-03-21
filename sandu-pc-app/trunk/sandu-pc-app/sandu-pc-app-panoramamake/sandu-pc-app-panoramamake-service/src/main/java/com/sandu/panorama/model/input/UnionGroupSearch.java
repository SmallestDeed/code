package com.sandu.panorama.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by chenm on 2018/7/30.
 */
@Data
public class UnionGroupSearch implements Serializable{

    @ApiModelProperty(value="起始行", dataType = "int")
    private Integer start = -1;
    @ApiModelProperty(value="每页数据数", dataType = "int")
    private Integer limit = -1;
    @ApiModelProperty(value="720打组制作UUID", dataType = "String")
    private String uuid ;
    @ApiModelProperty(value="制作人Id", dataType = "int", hidden = true)
    private Integer userId;
    @ApiModelProperty(value="联盟门店分组Id",dataType = "int",hidden = true)
    private Integer unionGroupId;
}
