package com.sandu.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/9/30
 * @since : sandu_yun_1.0
 */
@Data
public class BaseHousePicFullHousePlanRelSearch implements Serializable{
    private static final long serialVersionUID = -1350263517371992487L;

    private Integer id;

    private Integer fullHousePlanId;

    private List<Integer> templateIds;


}
