package com.sandu.api.resmodel.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModelAreaTextureRelAdd implements Serializable {
    @ApiModelProperty("区域编码")
    private String areaCode;
    @ApiModelProperty("区域名称")
    private String areaName;
    @ApiModelProperty("区域Id")
    private Integer  areaId;
    @ApiModelProperty("区域贴图信息")
    private List<AreaTextureRel> rels;
    /**
     * 区域材质默认高度
     */
    @ApiModelProperty(value = "区域材质默认高度")
    private Integer height;
    /**
     * 区域材质默认宽度
     */
    @ApiModelProperty(value = "区域材质默认宽度")
    private Integer width;

    @Data
    public static class AreaTextureRel implements Serializable{
        @ApiModelProperty("贴图ID")
        private Integer textureId;
        @ApiModelProperty("贴图价格")
        private Integer price;
        @ApiModelProperty("是否为默认贴图,ture为默认贴图")
        private boolean defaultFlag;
    }
}
