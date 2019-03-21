package com.sandu.service.goods.impl;

import com.sandu.api.goods.input.GoodsDetailQuery;
import com.sandu.api.goods.input.GoodsSKUAdd;
import com.sandu.api.goods.input.GoodsSKUListAdd;
import com.sandu.api.goods.model.BaseGoodsSKU;
import com.sandu.api.goods.model.bo.*;
import com.sandu.api.goods.output.GoodsSKUVO;
import com.sandu.api.goods.service.BaseGoodsSKUService;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.service.ProductService;
import com.sandu.service.goods.dao.BaseGoodsSKUMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("baseGoodsSKUService")
public class BaseGoodsSKUServiceImpl implements BaseGoodsSKUService
{
    @Autowired
    private BaseGoodsSKUMapper baseGoodsSKUMapper;

    @Override
    public List<GoodsSKUVO> getGoodsSKUs(GoodsDetailQuery goodsDetailQuery)
    {
        //查询SKU表中的信息
        List<BaseGoodsSKUBO> baseGoodsSKUS = baseGoodsSKUMapper.selectBySPUId(goodsDetailQuery.getSpuId());
        //查询base_product表中的信息
        List<ProductSKUBO> productSKUS = baseGoodsSKUMapper.getProductSKUsBySPUId(goodsDetailQuery);
        List<GoodsSKUVO> goodsSKUVOS = new ArrayList<>();

        for (ProductSKUBO productSKUBO : productSKUS)
        {
            BaseGoodsSKUBO baseGoodsSKUBO = null;
            for (BaseGoodsSKUBO skuBo : baseGoodsSKUS)
            {
                if (skuBo.getProductId().equals(productSKUBO.getProductId()))
                {
                    baseGoodsSKUBO = skuBo;
                }
            }
            GoodsSKUVO goodsSKUVO = new GoodsSKUVO();
            goodsSKUVO.setModelNumber(productSKUBO.getProductModelNumber());
            goodsSKUVO.setSalePrice(productSKUBO.getSalePrice());
            goodsSKUVO.setProductId(productSKUBO.getProductId());

            goodsSKUVO.setSpePic(baseGoodsSKUBO == null? productSKUBO.getPicPath() :
                    baseGoodsSKUBO.getSpecificationPic() == null? productSKUBO.getPicPath() : baseGoodsSKUBO.getSpecificationPic());
            goodsSKUVO.setSpePicId(baseGoodsSKUBO == null? productSKUBO.getPicId() :
                    baseGoodsSKUBO.getSpecificationPicId() == null? productSKUBO.getPicId() : baseGoodsSKUBO.getSpecificationPicId());
            goodsSKUVO.setInventory(baseGoodsSKUBO == null? 0 : baseGoodsSKUBO.getInventory());
            goodsSKUVO.setPrice(baseGoodsSKUBO == null? new BigDecimal(0) : baseGoodsSKUBO.getPrice());

            String attrValueIds = null;
            List<String> attributes = new ArrayList<>();
            List<String> tableHeads = new ArrayList<>();
            if(productSKUBO.getAttributes() == null || productSKUBO.getAttributes().size() == 0)
            {
                attrValueIds = "0";
                attributes.add("默认");
                tableHeads.add("默认");
            }else
            {
                for (ProductAttributeBO attributeBO : productSKUBO.getAttributes())
                {
                    attrValueIds = attrValueIds == null || "".equals(attrValueIds)?
                            attributeBO.getAttributeId().toString() : attrValueIds + "," + attributeBO.getAttributeId();
                    attributes.add(attributeBO.getValue());
                    tableHeads.add(attributeBO.getKey());
                }
            }
            goodsSKUVO.setTableHeads(tableHeads);
            goodsSKUVO.setAttributes(attributes);
            goodsSKUVO.setAttrValueIds(attrValueIds.toString());
            goodsSKUVOS.add(goodsSKUVO);
        }
        return goodsSKUVOS;
    }

    @Override
    public Integer updateGoodsSKU(GoodsSKUListAdd goodsSKUListAdd)
    {
        Integer number = 0;

        for (GoodsSKUAdd goodsSKUAdd : goodsSKUListAdd.getGoodsSKUAddList())
        {
            Date currentDate = new Date();
            BaseGoodsSKU baseGoodsSKU = new BaseGoodsSKU();
            baseGoodsSKU.setProductId(goodsSKUAdd.getProductId());
            baseGoodsSKU.setInventory(goodsSKUAdd.getRepertory());
            baseGoodsSKU.setPrice(goodsSKUAdd.getCost());
            baseGoodsSKU.setSpecificationPicId(goodsSKUAdd.getSpePictureId());
            baseGoodsSKU.setGmtModified(currentDate);
            baseGoodsSKU.setModifier(goodsSKUListAdd.getUserId().toString());

            int i = baseGoodsSKUMapper.updateByProductId(baseGoodsSKU);

            if(i == 0)
            {
                baseGoodsSKU.setSpuId(goodsSKUListAdd.getSpuId());
                baseGoodsSKU.setCreator(goodsSKUListAdd.getUserId().toString());
                baseGoodsSKU.setGmtCreate(currentDate);
                baseGoodsSKU.setAttributeIds(goodsSKUAdd.getAttrValueIds());
                baseGoodsSKU.setIsDeleted(0);
                i = baseGoodsSKUMapper.insertSelective(baseGoodsSKU);
            }
            number += i;
        }
        return number;
    }
}
