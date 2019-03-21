package com.sandu.product.service.impl;

import com.sandu.product.dao.ProductPropsMapper;
import com.sandu.product.service.ProductPropsService;
import com.sandu.productprops.model.ProductProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @version V1.0
 * @Title: ProductPropsServiceImpl.java
 * @Package com.sandu.productprops.service.impl
 * @Description:产品属性-产品属性表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 10:40:03
 */
@Service("productPropsService")
public class ProductPropsServiceImpl implements ProductPropsService {

    @Autowired
    private ProductPropsMapper productPropsMapper;


    /**
     * 新增数据
     *
     * @param productProps
     * @return int
     */
    @Override
    public int add(ProductProps productProps) {
        productPropsMapper.insertSelective(productProps);
        return productProps.getId();
    }

    /**
     * 更新数据
     *
     * @param productProps
     * @return int
     */
    @Override
    public int update(ProductProps productProps) {
        return productPropsMapper
                .updateByPrimaryKeySelective(productProps);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return productPropsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ProductProps
     */
    @Override
    public ProductProps get(Integer id) {
        return productPropsMapper.selectByPrimaryKey(id);
    }


}
