package com.sandu.service.product.impl;

import com.sandu.api.product.model.ProductCategoryRel;
import com.sandu.api.product.service.ProductCategoryRelService;
import com.sandu.service.product.dao.ProductCategoryRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sandu
 * @date 2017/12/19
 */
@Service("productCategoryRelService")
public class ProductCategoryRelServiceImpl implements ProductCategoryRelService {
    @Autowired
    private ProductCategoryRelDao productCategoryRelDao;

    @Override
    public int saveProductCategoryRel(ProductCategoryRel productCategoryRel) {
        return productCategoryRelDao.save(productCategoryRel);
    }

    @Override
    public int saveProductCategoryRelList(List<ProductCategoryRel> list) {
        if(list == null || list.isEmpty()){
            return -1;
        }
        return productCategoryRelDao.saveProductCategoryRelList(list);
    }

    @Override
    public int deleteProductCategoryRelById(long id) {
        return productCategoryRelDao.delete(id);
    }

    @Override
    public int deleteProductCategoryRelByProductId(long productId) {
        return productCategoryRelDao.deleteProductCategoryRelByProductId(productId);
    }

    @Override
    public void updateProductCategoryRel(ProductCategoryRel productCategoryRel) {
        productCategoryRelDao.update(productCategoryRel);
    }

    @Override
    public ProductCategoryRel getProductCategoryRelById(long id) {
        return productCategoryRelDao.getProductCategoryRelById(id);
    }

    @Override
    public List<ProductCategoryRel> getProductCategoryRelByCategoryId(int categoryId) {
        return productCategoryRelDao.getProductCategoryRelByCategoryId(categoryId);
    }

    @Override
    public List<ProductCategoryRel> getProductCategoryRelByProductId(int productId) {
        return productCategoryRelDao.getProductCategoryRelByProductId(productId);
    }

    @Override
    public void deleteProductCategoryRelByProductIds(List<Integer> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return;
        }
        productCategoryRelDao.deleteProductCategoryRelByProductIds(productIds);
    }

}
