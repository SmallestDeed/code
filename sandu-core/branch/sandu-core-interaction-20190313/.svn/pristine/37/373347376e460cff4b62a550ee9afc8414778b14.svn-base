package com.sandu.api.base.service;

import com.sandu.api.base.model.BaseProductCategorySearch;
import com.sandu.api.base.model.BaseProductCategory;
import com.sandu.api.base.model.BaseProductCategorySearch;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/19
 * @since : sandu_yun_1.0
 */
@Component
public interface BaseProductCategoryService {

    List<BaseProductCategory> getBaseProductCategoryList(BaseProductCategorySearch search);


    List<List<BaseProductCategory>> getBaseProductCategoryFilterList(BaseProductCategorySearch search);

    void refreshCache();
}
