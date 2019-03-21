package com.sandu.api.dictionary.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 数据字典 by 类型  查询 入参
 * @Date 2018/6/1 0001 17:23
 * @Modified By
 */
@Data
public class DictionaryTypeListQuery implements Serializable{

    @ApiModelProperty(value = "数据字典类型" ,required = true)
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "数据字典名称")
    private String name;

}
