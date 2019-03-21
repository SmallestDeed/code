package com.sandu.api.category.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/7/27  10:01
 */
@Data
public class CategoryAndIndustryVO  implements Serializable{

    @ApiModelProperty(value = "所属行业名称集")
    private String industryNames;

    @ApiModelProperty(value = "所属行业Value集")
    private List<Integer> industryValues;

    @ApiModelProperty(value = "可见产品范围名称集")
    private String categoryNames;

    @ApiModelProperty(value = "可见产品范围Id集")
    private List<Integer> categoryIds;

   /* @ApiModelProperty(value = "可见产品父类Id集")
    private List<Integer> categoryPIds;

    @ApiModelProperty(value = "可见产品父类的父类Id集")
    private List<Integer> categoryGPIds;*/

}
