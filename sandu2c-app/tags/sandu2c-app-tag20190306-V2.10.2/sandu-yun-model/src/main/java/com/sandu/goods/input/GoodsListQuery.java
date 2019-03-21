package com.sandu.goods.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsListQuery implements Serializable
{
    private String categoryCode;

    private Integer curPage;

    private Integer pageSize;

    private Integer isPresell;

    private Integer isSpecialOffer;
}
