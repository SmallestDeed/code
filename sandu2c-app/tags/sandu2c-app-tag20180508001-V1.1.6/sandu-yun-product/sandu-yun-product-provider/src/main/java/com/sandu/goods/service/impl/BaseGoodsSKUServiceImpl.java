package com.sandu.goods.service.impl;


import com.sandu.common.util.NumberUtil;
import com.sandu.goods.dao.BaseGoodsSKUMapper;
import com.sandu.goods.input.GoodsSkuQuery;
import com.sandu.goods.model.BO.GoodsSkuBO;
import com.sandu.goods.model.BO.ProductAttrBO;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.output.GoodsSkuVO;
import com.sandu.goods.service.BaseGoodsSKUService;
import com.sandu.system.service.ResPicService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service("baseGoodsSKUService")
@Log4j
public class BaseGoodsSKUServiceImpl implements BaseGoodsSKUService
{
    @Autowired
    private BaseGoodsSKUMapper baseGoodsSKUMapper;
    @Autowired
    private ResPicService resPicService;

    @Override
    public GoodsSkuVO getSkuInfoByAttrs(GoodsSkuQuery goodsSkuQuery)
    {
        GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
        List<GoodsSkuBO> skus = baseGoodsSKUMapper.getSkusBySpuId(goodsSkuQuery.getSpuId());
        List<Integer> attrValIds = new ArrayList<>();

        if (skus != null && skus.size() != 0)
        {
            for (GoodsSkuBO sku : skus)
            {
                List<Integer> skuAttrIds = new ArrayList<>();
                try
                {
                    skuAttrIds = NumberUtil.convertStrToNumList(sku.getAttributeValueIds(), Integer.class);
                    attrValIds = NumberUtil.convertStrToNumList(goodsSkuQuery.getAttrValueIds(), Integer.class);
                } catch (Exception e)
                {
                    log.error("BaseGoodsSKUService.getSkuInfoByAttrs() row:50",e);
                    e.printStackTrace();
                }
                if (skuAttrIds.size() == 0)
                {
                    skuAttrIds.add(0);
                }

                Integer flag = 0;
                for (Integer valId : attrValIds)
                {
                    for (Integer attrId : skuAttrIds)
                    {
                        if (valId.equals(attrId))
                        {
                            flag += 1;
                        }
                    }
                }
                if (flag == skuAttrIds.size() && flag == attrValIds.size())
                {
                    goodsSkuVO.setSkuId(sku.getSkuId());
                    goodsSkuVO.setInventory(sku.getInventory() == null?0:sku.getInventory());
                    goodsSkuVO.setPrice(sku.getPrice() == null?"0":sku.getPrice().toString());
                    goodsSkuVO.setSalePrice(sku.getSalePrice() == null?"0":sku.getSalePrice().toString());
                    goodsSkuVO.setProductId(sku.getProductId());
                    goodsSkuVO.setProductName(sku.getProductName());
                    goodsSkuVO.setProductPicPath(sku.getSpePicId() == null?
                            (sku.getDefaultPicId() == null?null:resPicService.get(sku.getDefaultPicId()).getPicPath())
                            :resPicService.get(sku.getSpePicId()).getPicPath());
                    goodsSkuVO.setAttribute(sku.getAttributeValueNames());
                    goodsSkuVO.setTransportMoney(sku.getTransportMoney()==null?"0":""+sku.getTransportMoney().intValue());
                    return goodsSkuVO;
                }
            }
        }
        return null;
    }

    @Override
    public List<Integer> getProductIdsBySpuId(Integer spuId)
    {
        return baseGoodsSKUMapper.getProductIdsBySpuId(spuId);
    }

    @Override
    public Integer changeInventory(Integer id,Integer num)
    {
        BaseGoodsSKU sku = baseGoodsSKUMapper.selectByPrimaryKey(id);
        sku.setInventory(sku.getInventory() + num);
        return baseGoodsSKUMapper.updateByPrimaryKey(sku);
    }


}
