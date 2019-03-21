package com.sandu.api.resmodel.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 模型-贴图关系实体
 *
 * @author Sandu
 */
@Data
public class ModelAreaBO implements Serializable {
    @ApiModelProperty(value = "贴图区域ID")
    private Integer areaId;
    /**
     * 模型id
     */
    @ApiModelProperty(value = "模型id")
    private Long modelId;
    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String areaCode;
    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称")
    private String areaName;
    /**
     * 区域内贴图信息
     */
    @ApiModelProperty(value = "区域内贴图信息")
    private List<AreaTextureBO> rels;
    /**
     * 区域默认贴图ID
     */
    @ApiModelProperty(value = "区域默认贴图ID")
    private Integer defalutTextureId;

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


}
