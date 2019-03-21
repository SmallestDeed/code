package com.sandu.service.product.impl;

import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.service.ProductAttributeService;
import com.sandu.service.product.dao.ProductAttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sandu
 * @date 2017/12/19
 */
@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeDao productAttributeDao;

    @Override
    public int saveProductAttribute(ProductAttribute productAttribute) {
        return productAttributeDao.save(productAttribute);
    }

    @Override
    public int deleteProductAttributeById(long id) {
        return productAttributeDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateProductAttribute(ProductAttribute productAttribute) {
        productAttributeDao.update(productAttribute);
    }

    @Override
    public ProductAttribute getProductAttributeById(long id) {
        return productAttributeDao.getProductAttributeById(id);
    }

    @Override
    public List<ProductAttribute> getProductAttributeByProductId(int productId) {
        return productAttributeDao.getProductAttributeByProductId(productId);
    }

    @Override
    public int saveProductAttributeList(List<ProductAttribute> attrs) {
        if (attrs.isEmpty()) {
            return -1;
        }
        return productAttributeDao.saveProductAttributeList(attrs);
    }

    @Override
    public void deleteProductAttributeByProductId(Long productId) {
        productAttributeDao.deleteProductAttributeByProductId(productId);
    }

    @Override
    public void deleteProductAttributeByProductIds(List<Long> productIds) {
        if (productIds != null && productIds.isEmpty()) {
            return;
        }
        productAttributeDao.deleteProductAttributeByProductIds(productIds);
    }
}
