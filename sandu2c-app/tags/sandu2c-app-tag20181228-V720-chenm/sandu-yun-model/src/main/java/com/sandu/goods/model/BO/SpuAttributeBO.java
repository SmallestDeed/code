package com.sandu.goods.model.BO;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpuAttributeBO implements Serializable
{
    private Integer attrId;

    private String attrName;

    private Integer attrValueId;

    private String attrValueName;
}
