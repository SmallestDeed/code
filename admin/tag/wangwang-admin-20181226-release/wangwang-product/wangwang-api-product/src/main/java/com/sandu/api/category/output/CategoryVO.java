package com.sandu.api.category.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class CategoryVO implements Serializable {

    @ApiModelProperty(value = "分类名称")
    private String name;
    @ApiModelProperty(value = "分类编码")
    private String code;
    @ApiModelProperty(value = "分类子节点")
    private List children;
    @ApiModelProperty(value = "分类产品类型:1.模型产品,2.贴图产品")
    private Integer categoryType;
}
