package com.sandu.api.category.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 可见产品范围 出参
 * @Date 2018/6/1 0001 16:15
 * @Modified By
 */
@Data
public class CategoryListVO implements Serializable{

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

}
