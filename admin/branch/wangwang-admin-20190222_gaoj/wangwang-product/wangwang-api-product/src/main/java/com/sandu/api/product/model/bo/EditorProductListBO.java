package com.sandu.api.product.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/22 9:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditorProductListBO implements Serializable {
    private Integer id;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 模型编码
     */
    private String modelCode;
    /**
     * 模型型号
     */
    private String modelNumber;
    /**
     * 模型ID
     */
    private Integer modelId;
    /**
     * 模型分类
     */
    private String categoryNames;

    private String picId;

    private String picPath;
}
