package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/22
 * @since : sandu_yun_1.0
 */
@Data
public class GroupProductImageInfo implements Serializable{

    private static final long serialVersionUID = -6235029222204663687L;

    private Integer groupProductId;

    private Integer resPicId;

    private String convertPicPath;

    private String newSmallPath;


}
