package com.sandu.goods.model.BO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductAttrBO implements Serializable
{
    private Integer id;

    private String productName;

    private String picPath;

    private String attrValueIds;
}
