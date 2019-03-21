package com.sandu.service.product.impl;

import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.model.ProductProps;
import com.sandu.api.product.service.ProductAttributeService;
import com.sandu.api.product.service.ProductPropsService;
import com.sandu.common.constant.attr.DoorAttr;
import com.sandu.exception.BizException;
import com.sandu.service.product.dao.ProductAttributeMapper;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private DoorAttr doorAttr;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Autowired
    private ProductPropsService productPropsService;

    @Override
    public ProductAttribute getProAttr(String proAttrKey, String proAttrValKey) {
        if (!StringUtils.isNoneBlank(proAttrKey) || !StringUtils.isNoneBlank(proAttrValKey)) {
            log.warn("参数 proAttrKey or proAttrValKey 是空");
            return null;
        }

        if (!doorAttr.containsProAttr(proAttrKey) || !doorAttr.containsProAttrVal(proAttrValKey)) {
            log.debug("{}(proAttrKey), {}(proAttrValKey)不是需要处理门类型", proAttrKey, proAttrValKey);
            return null;
        }

        ProductAttribute proAttr = this.handlerPoops(proAttrKey, proAttrValKey);
        if (proAttr == null) {
            return null;
        }

        Date now = new Date();
        proAttr.setGmtCreate(now);
        proAttr.setGmtModified(now);
        proAttr.setCreator("bake-task");
        proAttr.setModifier("bake-task");
        proAttr.setSysCode(Utils.getSysCode(6));
        proAttr.setIsDeleted(0);
        proAttr.setRemark("绘制户型硬装产品门类属性");

        return proAttr;
    }

    /**
     * @param proAttrValKey
     * @param proAttrValKey
     * @return
     */
    private ProductAttribute handlerPoops(String proAttrKey, String proAttrValKey) {
        ProductAttribute proAttr = new ProductAttribute();

        ProductProps props1 = productPropsService.getProPropsByLongCode(proAttrKey);
        if (props1 == null) {
            throw new BizException("proAttrKey"+ proAttrKey +"的属性是空");
        }

        // attr_id、attr_key、attr_name
        proAttr.setAttributeId(props1.getId().intValue());
        proAttr.setAttributeKey(props1.getCode());
        proAttr.setAttributeName(props1.getName());

        ProductProps props2 = productPropsService.getProPropsByLongCode(proAttrValKey);
        if (props2 == null) {
            throw new BizException("proAttrValKey"+ proAttrValKey +"的属性是空");
        }

        // attr_value_id 、attr_value_key、attr_value_name
        proAttr.setAttributeValueId(props2.getId().intValue());
        proAttr.setAttributeValueKey(props2.getCode());
        proAttr.setAttributeValueName(props2.getName());

        return proAttr;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addProAttr(ProductAttribute proAttr) {
        if (proAttr == null) {
            return -1;
        }

        return productAttributeMapper.insertSelective(proAttr);
    }

    @Override
    public List<ProductAttribute> listProductAttrByProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return productAttributeMapper.listProductAttrByProductId(productId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer batchAddAttribute(List<ProductAttribute> attrs) {
        if (attrs == null || attrs.isEmpty()) {
            return null;
        }

        return productAttributeMapper.batchAddAttribute(attrs);
    }
}
