package com.sandu.api.product.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class AlloProductPO implements Serializable {
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 品牌ID
     */
    private Integer brandId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品型号
     */
    private String productType;
    /**
     * 产品系列
     */
    private Integer productSerierId;
    /**
     * 产品风格
     */
    private Integer productStyleId;
    /**
     * 产品材质ID
     */
    private Integer productTextureId;
    /**
     * 分配到的渠道  渠道(2b)/线上(2c)
     */
    private String allotPlatformType;
}
