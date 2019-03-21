package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/24
 * @since : sandu_yun_1.0
 */
@Data
public class GoodsSpuPriceInfo implements Serializable{

    private Long id;

    private Long spuId;

    private Double price;

}
