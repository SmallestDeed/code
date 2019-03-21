package com.sandu.api.resmodel.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class ModelTextureInfoBO implements Serializable {
    /**
     * 贴图区域ID
     */
    private Integer areaId;
    /**
     * 贴图区域名称
     */
    private String areaName;
    /**
     * 模型ID
     */
    private Integer modelId;
    /**
     * 贴图区域编码
     */
    private String areaCode;
    /**
     * 贴图id
     */
    private Integer textureId;
    /**
     * 贴图的影响价格
     */
    private Integer affectPrice;
    /**
     * 是否为默认贴图
     */
    private Integer isDefault;
}
