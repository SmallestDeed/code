package com.sandu.api.restexture.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class ProductTextureVO implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "贴图名称")
    private String name;
    @ApiModelProperty(value = "贴图编码")
    private String code;
    @ApiModelProperty(value = "贴图图片路径")
    private String picPath;
    @ApiModelProperty(value = "贴图编码")
    private String textureCode;
    @ApiModelProperty(value = "贴图型号")
    private String modelNumber;
}
