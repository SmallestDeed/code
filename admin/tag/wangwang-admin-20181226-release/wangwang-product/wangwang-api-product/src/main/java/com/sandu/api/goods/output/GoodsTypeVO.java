package com.sandu.api.goods.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsTypeVO implements Serializable
{
    private String typeCode;

    private String typeName;

    private List<GoodsTypeVO> childType;
}
