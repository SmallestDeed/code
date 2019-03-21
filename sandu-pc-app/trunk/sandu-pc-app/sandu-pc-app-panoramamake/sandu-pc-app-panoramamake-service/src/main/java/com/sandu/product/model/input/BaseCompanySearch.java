package com.sandu.product.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenm on 2018/8/1.
 */
@Data
public class BaseCompanySearch implements Serializable{
    private static final long serialVersionUID = -526015776952446143L;

    @ApiModelProperty(value="企业Id集合",hidden = true)
    private List<Integer> idList;
    @ApiModelProperty(value="企业名称")
    private String sch_compnyName;
    @ApiModelProperty(value = "是否已删除",hidden = true)
    private Integer isDeleted;
    @ApiModelProperty(value = "需隐藏的企业类型",hidden = true)
    private  List<Integer> hiddenBusinessTypeList;
}
