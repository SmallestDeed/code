package com.sandu.service.base.dao;

import com.sandu.api.base.model.BaseProductCategorySearch;
import com.sandu.api.base.model.BaseProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/19
 * @since : sandu_yun_1.0
 */
@Repository
public interface BaseProductCategoryMapper {

    List<BaseProductCategory> getBaseProductCategoryList(BaseProductCategorySearch search);

    String selectAllCompanyMainCategory(Integer companyId);


}
