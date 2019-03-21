package com.sandu.search.entity.product.universal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品品牌和名称视图对象
 *
 * @date 20171222
 * @auth pengxuangang
 */
@Data
public class BrandAndNameProductVo implements Serializable {

    private static final long serialVersionUID = -251608493771137221L;

    //产品ID
    private Integer id;
    //产品名
    private String productName;
    //产品品牌名
    private String productBrandName;
    //产品图片路径
    private String picPath;
    //销售价格
    private double productSalePrice;
    //型号
    private String productModelNumber;
}
