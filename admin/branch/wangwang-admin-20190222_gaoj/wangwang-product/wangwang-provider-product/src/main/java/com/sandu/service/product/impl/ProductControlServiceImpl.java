package com.sandu.service.product.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.ProductControl;
import com.sandu.api.product.service.ProductControlService;
import com.sandu.api.product.service.ProductService;
import com.sandu.service.product.dao.ProductControlDao;

/**
 * @author Sandu
 */
@Service("productControlService")
public class ProductControlServiceImpl implements ProductControlService {

    @Autowired
    private ProductControlDao productControlDao;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean batchChangeProductSecrecy(List<Integer> productIds, Integer secrecy) {
        String allProductId = productService.getSoftHardProduct(productIds);
        List<Integer> updateIds = new ArrayList<Integer>();
        if(allProductId!=null && !allProductId.isEmpty()) {
        	updateIds = Arrays.asList(allProductId.split(",")).stream().map(s->Integer.parseInt(s)).collect(Collectors.toList());
        	productIds.addAll(updateIds);
        }
        if(productIds!=null && productIds.size() > 0) {
        	productIds.forEach(item -> {
                Product product = new Product();
                product.setId(item.longValue());
                product.setSecrecyFlag(secrecy);
                productService.updateProduct(product);
            });
        }	
        return true;
    }

    @Override
    public int saveProductControl(ProductControl productControl) {
        return productControlDao.insert(productControl);
    }

    @Override
    public int saveProductControlList(List<ProductControl> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return productControlDao.insertList(list);
    }

    @Override
    public int batchUpdate(List<ProductControl> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        for (ProductControl tmp : list) {
            Product product = new Product();
            product.setId(tmp.getId());
            product.setSecrecyFlag((int) tmp.getSecrecy());
            productService.updateProduct(product);
        }
        return 1;
    }

    @Override
    public List<Integer> getExistProductByProductId(List<Integer> productIds) {
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }
        return productControlDao.getExistProductByProductId(productIds);
    }

    @Override
    public int insertList(List<ProductControl> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return productControlDao.insertList(list);
    }

    @Override
    public ProductControl getByProductId(Long id) {
        return productControlDao.getByProductId(id);
    }

    @Override
    public int update(ProductControl productControl) {
        int ret;
        ret = productControlDao.updateSelectiveByProductId(productControl);
        return ret;

    }

    @Override
    public void updateByProductId(ProductControl pc) {
        productControlDao.updateSelectiveByProductId(pc);
    }


}
