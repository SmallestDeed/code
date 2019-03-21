package com.sandu.test.attr;

import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.service.ProductAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProAttrTests {

    @Autowired
    ProductAttributeService productAttributeService;

    @Test
    public void testProAttr() {
        DrawBaseProduct drawProduct = new DrawBaseProduct();
        drawProduct.setProAttrKey(".root.hard.fangjm.type.");
        drawProduct.setProAttrValKey(".root.hard.fangjm.type.type1.");
        drawProduct.setProductCode("productCode");
        drawProduct.setBaseProductId(-100000L);
        drawProduct.setProductSmallTypeMark("basic_fangjm");

        ProductAttribute proAttr = productAttributeService.getProAttr(drawProduct.getProAttrKey(), drawProduct.getProAttrValKey());
        if (proAttr != null) {
            proAttr.setProductCode(drawProduct.getProductCode());
            proAttr.setProductId(drawProduct.getBaseProductId().intValue());
            // 产品小分类（产品属性父级）
            proAttr.setAttributeTypeValue(drawProduct.getProductSmallTypeMark());

            log.info("处理{}(proAttrKey), {}(proAttrValKey)属性，proAttr ==> {}", drawProduct.getProAttrKey(), drawProduct.getProAttrValKey(), proAttr);

            productAttributeService.addProAttr(proAttr);
        }
    }
}
