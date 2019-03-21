package com.sandu.test.draw;

import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.service.ProductAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/26
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductAttributeTests {

    @Autowired
    ProductAttributeService productAttributeService;

//    @Test
    public void test() {
        List<ProductAttribute> proAttrs = productAttributeService.listProductAttrByProductId(1139L);
        proAttrs.forEach(attr -> attr.setProductId(-55555));
        productAttributeService.batchAddAttribute(proAttrs);
    }
}
