package com.sandu.product.service.impl;

import com.sandu.product.dao.ProductAttributeMapper;
import com.sandu.product.model.ProductAttribute;
import com.sandu.product.model.search.ProductAttributeSearch;
import com.sandu.product.service.ProductAttributeService;
import com.sandu.product.service.ProductPropsService;
import com.sandu.productprops.model.ProductProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @version V1.0
 * @Title: ProductAttributeServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品模块-产品属性ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 13:17:36
 */
@Service("productAttributeService")
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductPropsService productPropsService;
    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    /**
     * 新增数据
     *
     * @param productAttribute
     * @return int
     */
    @Override
    public int add(ProductAttribute productAttribute) {
        productAttributeMapper.insertSelective(productAttribute);
        return productAttribute.getId();
    }

    /**
     * 更新数据
     *
     * @param productAttribute
     * @return int
     */
    @Override
    public int update(ProductAttribute productAttribute) {
        return productAttributeMapper
                .updateByPrimaryKeySelective(productAttribute);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return productAttributeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ProductAttribute
     */
    @Override
    public ProductAttribute get(Integer id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param productAttribute
     * @return List<ProductAttribute>
     */
    @Override
    public List<ProductAttribute> getList(ProductAttribute productAttribute) {
        return productAttributeMapper.selectList(productAttribute);
    }

    /**
     * getPropertyMap
     * <p>
     * 通过产品id  获取属性值 的map   map的结构（ 键是product_attribute表中attribute_key  值是product_props表中的prop_value）
     *
     * @param productId
     * @return
     */
    @Override
    public Map<String, String> getPropertyMap(Integer productId) {
        Map<String, String> map = new HashMap<String, String>();
        ProductAttributeSearch productAttributeSearch = new ProductAttributeSearch();
        productAttributeSearch.setProductId(productId);
        productAttributeSearch.setIsDeleted(0);
        int Count = productAttributeMapper.selectCount(productAttributeSearch);
        if (Count > 0) {
            List<ProductAttribute> AttributeList = productAttributeMapper.selectPaginatedList(productAttributeSearch);
            if (AttributeList != null && AttributeList.size() > 0) {
                for (ProductAttribute productAttribute : AttributeList) {
                    ProductProps productProps = null;
                    String propValue = null;
                    String propCode = null;
                    if (null != productAttribute.getAttributeValueId()) {
                        productProps = productPropsService.get(productAttribute.getAttributeValueId());
                    }
                    if (productProps != null) {
                        propValue = productProps.getPropValue();
                        propCode = productProps.getCode();
                    }
                    map.put(productAttribute.getAttributeKey(), propValue);
                    //如果是结构产品标志 传Code值
                    if ("structureSign".equals(productAttribute.getAttributeKey())) {
                        map.put(productAttribute.getAttributeKey(), propCode);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 查询出满足多个属性的产品
     *
     * @param map
     * @return
     */
    @Override
    public List<ProductAttribute> selectIntersectProductAttribute(Map<String, Object> map) {
        return productAttributeMapper.selectIntersectProductAttribute(map);
    }

    @Override
    public List<ProductAttribute> getMergeAttribute(
            ProductAttribute productAttributet) {
        return productAttributeMapper.getMergeAttribute(productAttributet);
    }
}
