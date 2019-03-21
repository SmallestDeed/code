package com.sandu.api.goods.model.po;

import lombok.Data;
import java.io.Serializable;

@Data
public class GoodsListQueryPO implements Serializable
{
    private String typeCode;

    private String childTypeCode;

    private Integer isPutaway;

    private Integer isPresell;

    private String spuName;

    private String spuCode;

    private Integer companyId;

    private Integer start = 0;

    private Integer limit = 20;

    private String productModelNumber;
}
