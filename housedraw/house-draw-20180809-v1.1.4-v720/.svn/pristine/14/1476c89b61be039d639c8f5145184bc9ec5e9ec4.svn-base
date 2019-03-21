package com.sandu.service.product.impl;

import com.sandu.api.product.model.ProductProps;
import com.sandu.api.product.service.ProductPropsService;
import com.sandu.service.product.dao.ProductPropsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPropsServiceImpl implements ProductPropsService {

    @Autowired
    ProductPropsMapper productPropsMapper;

    @Override
    public ProductProps getProPropsByLongCode(String longCode) {
        if (!StringUtils.isNoneBlank(longCode)) {
            return null;
        }

        return productPropsMapper.getProAttrByLongCode(longCode);
    }
}
