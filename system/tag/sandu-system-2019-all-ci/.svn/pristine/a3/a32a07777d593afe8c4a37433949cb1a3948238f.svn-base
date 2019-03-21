package com.sandu.api.category.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 可见产品范围 选择 出参
 * @Date 2018/6/1 0001 16:15
 * @Modified By
 */
@Data
public class ProCategoryListVO implements Serializable{

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("是否选中：1选中/0未选中")
    private int isChecked = 0;

    @ApiModelProperty("分类子集")
    private List<ProCategoryListVO> list;
}
