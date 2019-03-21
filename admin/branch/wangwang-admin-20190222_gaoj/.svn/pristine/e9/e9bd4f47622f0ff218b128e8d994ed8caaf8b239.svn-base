package com.sandu.service.product.impl.biz;

import com.sandu.api.product.model.ProductCategoryRel;
import com.sandu.api.product.service.biz.ProductCategoryRelBizService;
import com.sandu.service.product.dao.ProductCategoryRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Sandu
 */
@Service
public class ProductCategoryRelBizServiceImpl implements ProductCategoryRelBizService {
    @Autowired
    private ProductCategoryRelDao productCategoryRelDao;

    @Override
    public int updateBiz(ProductCategoryRel productCategoryRel) {
        //上架后台三级分类标志
        productCategoryRel.setNumAtt1(1);
        productCategoryRel.setGmtCreate(new Date());
        productCategoryRel.setGmtModified(new Date());
        productCategoryRel.setIsDeleted(0);
        return  productCategoryRelDao.save(productCategoryRel);
    }
}
