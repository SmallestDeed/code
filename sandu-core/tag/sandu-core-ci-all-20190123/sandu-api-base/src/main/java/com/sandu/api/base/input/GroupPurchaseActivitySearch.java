package com.sandu.api.base.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/24
 * @since : sandu_yun_1.0
 */
@Data
public class GroupPurchaseActivitySearch implements Serializable{

    private static final long serialVersionUID = 5899131173271428145L;
    private Long id;

    private Long companyId;

    private String appId;

    private Integer status;

    private List<Integer> statusList;


}
