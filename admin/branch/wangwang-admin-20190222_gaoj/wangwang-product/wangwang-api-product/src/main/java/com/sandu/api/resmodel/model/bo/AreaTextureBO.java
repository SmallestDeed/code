package com.sandu.api.resmodel.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 区域-贴图关系
 * @author Sandu
 */
@Data
public class AreaTextureBO implements Serializable{
    @ApiModelProperty(value = "贴图ID")
    private Integer textureId;
    @ApiModelProperty(value = "贴图影响价格")
    private Integer affectPrice;
    @ApiModelProperty(value = "图片路径")
    private String picPath;
    @ApiModelProperty(value = "贴图编码")
    private String textureCode;
    @ApiModelProperty(value = "贴图型号")
    private String modelNumber;
    @ApiModelProperty(value = "贴图名称")
    private String textureName;

    private Boolean defaultFlag;
}
