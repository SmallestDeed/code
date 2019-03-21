package com.sandu.goods.service.impl;


import com.sandu.common.util.NumberUtil;
import com.sandu.goods.dao.BaseGoodsSKUMapper;
import com.sandu.goods.input.GoodsSkuQuery;
import com.sandu.goods.model.BO.GoodsSkuBO;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.output.GoodsSkuVO;
import com.sandu.goods.service.BaseGoodsSKUService;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("baseGoodsSKUService")
@Log4j
public class BaseGoodsSKUServiceImpl implements BaseGoodsSKUService {
    @Autowired
    private BaseGoodsSKUMapper baseGoodsSKUMapper;
    @Autowired
    private ResPicService resPicService;

    @Override
    public GoodsSkuVO getSkuInfoByAttrs(GoodsSkuQuery goodsSkuQuery) {
        GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
        List<GoodsSkuBO> skus = baseGoodsSKUMapper.getSkusBySpuId(goodsSkuQuery.getSpuId());
        List<Integer> attrValIds = new ArrayList<>();

        if (skus != null && skus.size() != 0) {
            for (GoodsSkuBO sku : skus) {
                List<Integer> skuAttrIds = new ArrayList<>();
                try {
                    skuAttrIds = NumberUtil.convertStrToNumList(sku.getAttributeValueIds(), Integer.class);
                    attrValIds = NumberUtil.convertStrToNumList(goodsSkuQuery.getAttrValueIds(), Integer.class);
                } catch (Exception e) {
                    log.error("BaseGoodsSKUService.getSkuInfoByAttrs() row:50", e);
                }
                if (skuAttrIds.size() == 0) {
                    skuAttrIds.add(0);
                }

                Integer flag = 0;
                for (Integer valId : attrValIds) {
                    for (Integer attrId : skuAttrIds) {
                        if (valId.equals(attrId)) {
                            flag += 1;
                        }
                    }
                }
                String productPicPath = null;
                if (sku.getSpePicId() == null || sku.getSpePicId() == 0){
                    if (sku.getDefaultPicId() == null || sku.getDefaultPicId() == 0){
                    }else {
                        ResPic pic = resPicService.get(sku.getDefaultPicId());
                        if (pic != null){
                            productPicPath = pic.getPicPath();
                        }
                    }
                }else {
                    ResPic pic = resPicService.get(sku.getSpePicId());
                    if (pic != null){
                        productPicPath = pic.getPicPath();
                    }
                }

                if (flag == skuAttrIds.size() && flag == attrValIds.size()) {
                    goodsSkuVO.setSkuId(sku.getSkuId());
                    goodsSkuVO.setInventory(sku.getInventory() == null ? 0 : sku.getInventory());
                    goodsSkuVO.setPrice(sku.getPrice() == null ? "0" :
                            sku.getPrice().compareTo(new BigDecimal("0")) == 0? "0" :
                                    sku.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
                    goodsSkuVO.setSalePrice(sku.getSalePrice() == null ? "0" :
                            sku.getSalePrice().compareTo(new BigDecimal("0")) == 0? "0" :
                                    sku.getSalePrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
                    goodsSkuVO.setProductId(sku.getProductId());
                    goodsSkuVO.setProductName(sku.getProductName());
                    goodsSkuVO.setProductPicPath(productPicPath);
                    goodsSkuVO.setAttribute(sku.getAttributeValueNames() == null ? "默认" : sku.getAttributeValueNames());
                    goodsSkuVO.setTransportMoney(sku.getTransportMoney() == null ? "0.00" :
                            sku.getTransportMoney().setScale(2,BigDecimal.ROUND_DOWN).toString());
                    goodsSkuVO.setProductSpec(sku.getProductSpec());
                    goodsSkuVO.setProductModelNumber(sku.getProductModelNumber());
                    goodsSkuVO.setBrandName(sku.getBrandName());
                    return goodsSkuVO;
                }
            }
        }
        return null;
    }

    @Override
    public List<Integer> getProductIdsBySpuId(Integer spuId) {
        return baseGoodsSKUMapper.getProductIdsBySpuId(spuId);
    }

    @Override
    public Integer changeInventory(Integer id, Integer num) {
        return baseGoodsSKUMapper.changeInventory(id, num);
    }

    @Override
    public List<BaseGoodsSKU> getListByProductIdList(List<Integer> productIdList) {
        return baseGoodsSKUMapper.getListByProductIdList(productIdList);
    }

    @Override
    public Map<Integer, String> mapGoodsSKU(List<Long> listSku) {
        if (listSku == null || listSku.isEmpty()) {
            return new HashMap<>(0);
        }

        List<GoodsSkuBO> listGoodsSKUBO = baseGoodsSKUMapper.mapGoodsSKU(listSku);

        // 解决JDK8的 value = null 时使用{@see java.util.HashMap.merge}报NullPointerException问题
        // 傻13的Oracle(WTF!!!!)在JDK9中修复了这个问题!!!!!!!
        return listGoodsSKUBO.stream().collect(Collectors.toMap(GoodsSkuBO::getSkuId,
                sku -> Optional.ofNullable(sku).map(GoodsSkuBO::getAttributeValueNames).orElse("默认"),
                (p, n) -> n));
    }

    /*@Override
    public List<SpuAttributeVO> getAttributeList(String selected, Integer spuId){
        List<ProductAttrBoV2> productAttrBoV2List = baseGoodsSKUMapper.getAllProductAttribute(spuId);
        List<Integer> selectedIdList = new ArrayList<>();
        try {
            selectedIdList = NumberUtil.convertStrToNumList(selected, Integer.class);
        } catch (Exception e) {
            log.info("字符串转数字集合异常");
            throw new RuntimeException("字符串转数字集合异常");
        }
        if (selectedIdList == null || selectedIdList.size() <= 0){
            ProductAttrBoV2 floor = new ProductAttrBoV2();
            floor.setPrice(new BigDecimal("0"));
            ProductAttrBoV2 minProduct = NumberUtil.findMin(productAttrBoV2List, floor, false);

        }
    }

    private List<SpuAttributeVO> convertToAttribute(List<ProductAttrBoV2> productAttrBoV2List){

    }*/

}
