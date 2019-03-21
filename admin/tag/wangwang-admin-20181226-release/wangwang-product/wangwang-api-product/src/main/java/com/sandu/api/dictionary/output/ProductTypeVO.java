package com.sandu.api.dictionary.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ProductTypeVO implements Serializable {
    @ApiModelProperty("属性类型")
    private String typeCode;
    @ApiModelProperty("属性键")
    private String keyCode;
    @ApiModelProperty("属性名")
    private String name;
    @ApiModelProperty("模型1/贴图0")
    private boolean checkModel;
    @ApiModelProperty("子节点")
    private List<ProductTypeVO> children;
}
