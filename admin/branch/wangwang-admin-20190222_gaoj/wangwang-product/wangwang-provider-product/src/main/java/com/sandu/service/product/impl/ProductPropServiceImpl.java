package com.sandu.service.product.impl;

import com.sandu.api.product.model.ProductProp;
import com.sandu.api.product.service.ProductPropService;
import com.sandu.service.product.dao.ProductPropDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sandu
 * @date 2017/12/18
 */
@Service("productPropService")
public class ProductPropServiceImpl implements ProductPropService {
    @Autowired
    private ProductPropDao productPropDao;

    @Override
    public int saveProductProp(ProductProp productProp) {
        return productPropDao.saveProductProp(productProp);
    }

    @Override
    public int deleteProductPropById(long id) {
        return productPropDao.deleteProductPropById(id);
    }

    @Override
    public void updateProductProp(ProductProp productProp) {
        productPropDao.updateProductProp(productProp);
    }

    @Override
    public ProductProp getProductPropInfoById(long id) {
        return productPropDao.getProductPropInfoById(id);
    }

    @Override
    public List<ProductProp> getProductPropByPid(int pid) {
        return productPropDao.getProductPropByPid(pid);
    }

    @Override
    public List<ProductProp> getProductPropByProductId(int productId) {
        return productPropDao.getProductPropByProductId(productId);
    }

    @Override
    public List<ProductProp> getProductPropByLongCode(String longCode) {
        return productPropDao.getProductPropByLongCode(longCode);
    }

    @Override
    public List<ProductProp> getParentAndItselfByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        ids = ids.stream().distinct().collect(Collectors.toList());
        return productPropDao.getParentAndItselfByIds(ids);
    }

    @Override
    public Map<String, List<ProductProp>> getAllParentByCodes(List<String> codes) {
        if (codes.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ProductProp> allParentByCodes = productPropDao.getAllParentByCodes(codes.stream().distinct().collect(Collectors.toList()));
        Map<String, List<ProductProp>> map = new HashMap<>();
        codes.forEach(code -> {
            allParentByCodes.stream().filter(item -> item.getCode().equals(code))
                    .forEach(child -> {
                        Optional<ProductProp> first = allParentByCodes.stream().filter(parent -> parent.getId().equals(child.getId())).findFirst();
                        List<ProductProp> productProps = Arrays.asList(first.get(), child);
                        productProps.sort(Comparator.comparing(ProductProp::getId));
                        map.put(code, productProps);
                    });
//            List<ProductProp> collect = allParentByCodes.stream()
////                    .filter(item -> item.getLongCode().contains(code))
//                    .sorted(Comparator.comparing(ProductProp::getId))
//                    .collect(Collectors.toList());
//            map.put(code,collect);
        });
        return map;
    }


}
