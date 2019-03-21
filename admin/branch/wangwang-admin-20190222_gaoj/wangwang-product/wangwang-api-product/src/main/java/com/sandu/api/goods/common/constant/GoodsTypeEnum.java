package com.sandu.api.goods.common.constant;

import java.io.Serializable;

public enum GoodsTypeEnum implements Serializable{
    BASE_GOODS(0),SPECIAL_GOODS(1),PRESELL_GOODS(2);

    private Integer goodsType;

    GoodsTypeEnum(Integer goodsType){
        this.goodsType = goodsType;
    }
}
