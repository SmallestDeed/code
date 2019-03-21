package com.sandu.system.model;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/9/30
 * @since : sandu_yun_1.0
 */
@Data
public class RenderTaskState implements Serializable{
    private static final long serialVersionUID = 7334242447111907871L;

    private Integer id;
    private Integer planId;
    private Integer templateId;
    private Integer fullHousePlanId;
    private Integer newFullHousePlanId;
    private Integer businessId;
    private Integer mainTaskId;
    private Integer houseId;
    private Integer spaceFunctionId;
    private Integer planHouseType;
    private Integer state;
}
