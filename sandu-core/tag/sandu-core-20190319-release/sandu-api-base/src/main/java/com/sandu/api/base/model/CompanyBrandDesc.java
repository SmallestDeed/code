package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/19
 * @since : sandu_yun_1.0
 */
@Data
public class CompanyBrandDesc implements Serializable{

    private Long id;

    private Long picId;

    private Long brandDescPicId;

    private String picPath;

    private String introduceTitle;

    private String introduce;

    private String richContext;


}
