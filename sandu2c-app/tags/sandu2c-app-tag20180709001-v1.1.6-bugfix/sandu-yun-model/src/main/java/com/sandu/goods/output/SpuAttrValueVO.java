package com.sandu.goods.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpuAttrValueVO implements Serializable
{
    private Integer attrValueId;

    private String attrValueName;

    private Integer isSelected;
}
