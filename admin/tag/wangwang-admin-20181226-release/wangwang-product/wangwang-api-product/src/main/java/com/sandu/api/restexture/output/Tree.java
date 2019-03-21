package com.sandu.api.restexture.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author  by bvvy
 */
@Data
public class Tree implements Serializable{

    @ApiModelProperty("表识名称")
    private Object label;

    @ApiModelProperty("值")
    private Object value;


    @ApiModelProperty("子级")
    private List<Tree> children;

}
