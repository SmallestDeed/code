package com.sandu.goods.output;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/7/12
 * @since : sandu_yun_1.0
 */
@Data
public class BaseCompanyMiniProgramConfig implements Serializable {

    private static final long serialVersionUID = -4959601502648609647L;
    private String appId;

    private Integer companyId;
    //可见品牌列表
    private String enableBrands;

}
