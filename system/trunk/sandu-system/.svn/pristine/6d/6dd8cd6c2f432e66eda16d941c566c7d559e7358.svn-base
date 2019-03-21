package com.sandu.api.category.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 产品分类 列表 查询 入参
 * @Date 2018/6/1 0001 16:12
 * @Modified By
 */
@Data
public class ProCategoryListQuery implements Serializable{

    @ApiModelProperty(value = "产品分类id",required = true)
    @NotNull(message = "产品分类id不能为空")
    private Integer categoryId;


    @ApiModelProperty(value = "层级")
    private Integer level;
}
