package com.sandu.designplan.model;

import com.sandu.product.model.BaseProduct;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:34 2018/10/11
 */
@Data
public class DesignPlanProductVO implements Serializable{

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 产品集合
     */
    private List<BaseProduct> products;

    /**
     * 产品集合数
     */
    private Integer productsSize;
}
