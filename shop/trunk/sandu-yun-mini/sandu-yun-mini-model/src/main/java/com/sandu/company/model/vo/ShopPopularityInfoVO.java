package com.sandu.company.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/14
 * @since : sandu_yun_1.0
 */
@Data
public class ShopPopularityInfoVO implements Serializable{


    private static final long serialVersionUID = 4807548582730830022L;

    private Integer businessTypeValue;
    private String businessTypeName;

    private List<ShopPopularityListVo> ShopPopularityList;

}
