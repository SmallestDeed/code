package com.sandu.goods.service.impl;


import com.sandu.common.util.NumberUtil;
import com.sandu.goods.dao.BaseGoodsSKUMapper;
import com.sandu.goods.dao.BaseGoodsSPUMapper;
import com.sandu.goods.model.BO.*;
import com.sandu.goods.model.BaseGoodsSPU;
import com.sandu.goods.model.PO.GoodsListPO;
import com.sandu.goods.output.*;
import com.sandu.goods.service.BaseGoodsSPUService;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Log4j
@Service("baseGoodsSPUService")
public class BaseGoodsSPUServiceImpl implements BaseGoodsSPUService {
    @Autowired
    private BaseGoodsSPUMapper baseGoodsSPUMapper;
    @Autowired
    private BaseGoodsSKUMapper baseGoodsSKUMapper;
    @Autowired
    private ResPicService resPicService;

    @Override
    public BaseGoodsSPU selectByPrimaryKey(Integer id) {
        return baseGoodsSPUMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getGoodsListCount(GoodsListPO goodsListPO) {
        goodsListPO.setStart(null);
        goodsListPO.setLimit(null);

        return baseGoodsSPUMapper.getGoodsSpuIdList(goodsListPO).size();
    }

    @Override
    public List<GoodsVO> getGoodsList(GoodsListPO goodsListPO) {
        // 查询商品id List
        List<Integer> ids = baseGoodsSPUMapper.getGoodsSpuIdList(goodsListPO);
        if (ids == null || ids.size() == 0) {
            return null;
        }

        // 通过in查询查询出商品列表
        List<GoodsBO> goodsBOList = baseGoodsSPUMapper.getGoodsSpuList(ids);
        List<GoodsVO> goodsVOList = new ArrayList<>();

        for (GoodsBO goodsBO : goodsBOList) {
            GoodsVO vo = new GoodsVO();
            vo.setSpuId(goodsBO.getId());
            vo.setPicPath(goodsBO.getPic());
            vo.setProductName(goodsBO.getGoodsName());
            vo.setIsPresell(goodsBO.getIsPresell());
            vo.setIsSpecialOffer(goodsBO.getIsSpecialOffer());
            List<BigDecimal> priceList = null;
            try {
                priceList = NumberUtil.convertStrToNumList(goodsBO.getPrices(), BigDecimal.class);
            } catch (Exception e) {
                log.error("BaseGoodsSPUService.getGoodsList() row:76", e);
            }
            if (priceList == null || priceList.size() == 0) {
                vo.setSalePrice("0");
            } else {
                // 找出价格最低的
                BigDecimal salePrice = NumberUtil.findMin(priceList, new BigDecimal("0"), false);
                vo.setSalePrice(salePrice == null ? "0" :
                        (salePrice.compareTo(new BigDecimal("0")) == 0 ? "0" : salePrice.setScale(2,BigDecimal.ROUND_DOWN).toString()));
            }
            goodsVOList.add(vo);
        }
        // 倒序排列
        goodsVOList.sort((a, b) -> b.getSpuId() - a.getSpuId());
        return goodsVOList;
    }

    @Override
    public GoodsDetailVO getGoodsDetail(Integer id) {
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        GoodsDetailBO goodsDetailBO = baseGoodsSPUMapper.getGoodsDetail(id);
        if (goodsDetailBO == null){
            return null;
        }
        // 轮播图
        List<Integer> picIdList = null;
        // 优惠价
        List<BigDecimal> prices = null;
        // 原价
        List<BigDecimal> salePrices = null;

        try {
            picIdList = NumberUtil.convertStrToNumList(goodsDetailBO.getPicIds(), Integer.class);
            prices = NumberUtil.convertStrToNumList(goodsDetailBO.getPrices(), BigDecimal.class);
            salePrices = NumberUtil.convertStrToNumList(goodsDetailBO.getSalePrices(), BigDecimal.class);
        } catch (Exception e) {
            log.error("BaseGoodsSPUService.getGoodsDetail() row:103", e);
            throw new RuntimeException("BaseGoodsSPUService.getGoodsDetail() row:104\n" + e.toString());
        }

        if (picIdList.size() > 0) {
            List<ResPic> pics = resPicService.getResPicByIds(picIdList);
            List<String> picPathList = new ArrayList<>();
            for (ResPic resPic : pics) {
                picPathList.add(resPic.getPicPath());
            }
            goodsDetailVO.setSmallPiclist(picPathList);
        }

        // 最低优惠价的索引
        int priceIndex = NumberUtil.findMinIndex(prices, new BigDecimal("0"), false) == -1 ?
                NumberUtil.findMinIndex(prices, new BigDecimal("0"), true) :
                NumberUtil.findMinIndex(prices, new BigDecimal("0"), false);

        goodsDetailVO.setLimitation(goodsDetailBO.getLimitation());
        goodsDetailVO.setSalePrice(priceIndex == -1 ? "0" :
                salePrices.get(priceIndex).compareTo(new BigDecimal("0")) == 0? "0" :
                        salePrices.get(priceIndex).setScale(2,BigDecimal.ROUND_DOWN).toString());
        goodsDetailVO.setPrice(priceIndex == -1 ? "0" :
                prices.get(priceIndex).compareTo(new BigDecimal("0")) == 0? "0" :
                        prices.get(priceIndex).setScale(2,BigDecimal.ROUND_DOWN).toString());
        goodsDetailVO.setProductName(goodsDetailBO.getSpuName());
        goodsDetailVO.setProductDesc(goodsDetailBO.getProductDesc());
        goodsDetailVO.setSendOutDay(goodsDetailBO.getDeliveryDay());
        goodsDetailVO.setId(goodsDetailBO.getId());
        goodsDetailVO.setTransportPay(goodsDetailBO.getTransportPay() == null ? "0.00" :
                goodsDetailBO.getTransportPay().setScale(2,BigDecimal.ROUND_DOWN).toString());
        goodsDetailVO.setSendOutPresell(goodsDetailBO.getDeliveryPresell());
        goodsDetailVO.setSendOutDate(goodsDetailBO.getDeliveryDate());

        return goodsDetailVO;
    }

    @Override
    public GoodsSkuVO getSpuAttrList(Integer id) {
        GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
        // 查询商品下的所有产品的所有属性
        List<SpuAttributeBO> spuAttrList = baseGoodsSPUMapper.getSpuAttrList(id);
        // 属于该商品的产品列表
        List<SkuPriceAndAttrBO> paList = baseGoodsSKUMapper.getSkuPriceAndAttr(id);
        // 所有产品的价格集合
        List<BigDecimal> priceList = new ArrayList<>();
        for (SkuPriceAndAttrBO skuPriceAndAttrBO : paList) {
            priceList.add(skuPriceAndAttrBO.getPrice() == null ? new BigDecimal("0") : skuPriceAndAttrBO.getPrice());
        }
        // 找到价格最低的索引
        int minIndex = NumberUtil.findMinIndex(priceList, new BigDecimal("0"), false);
        if (minIndex == -1) {
            minIndex = NumberUtil.findMinIndex(priceList, new BigDecimal("0"), true);
            if (minIndex == -1) {
                return null;
            }
        }
        // 价格最低的产品
        SkuPriceAndAttrBO pa = paList.get(minIndex);
        List<Integer> attrValueIds = new ArrayList<>();
        try {
            attrValueIds = NumberUtil.convertStrToNumList(pa.getAttrValueIds(), Integer.class);
        } catch (Exception e) {
            log.error("BaseGoodsSPUService.getSpuAttrList() row:152", e);
        }
        List<SpuAttributeVO> spuAttributeVOList = new ArrayList<>();

        if (spuAttrList == null || spuAttrList.size() == 0) {
            // 商品下没有属性时，返回默认属性
            SpuAttributeVO spuAttributeVO = new SpuAttributeVO();
            List<SpuAttrValueVO> attrValueList = new ArrayList<>();
            SpuAttrValueVO attrValue = new SpuAttrValueVO();
            attrValue.setAttrValueId(0);
            attrValue.setAttrValueName("默认");
            attrValue.setIsSelected(1);
            attrValueList.add(attrValue);

            spuAttributeVO.setAttrId(0);
            spuAttributeVO.setAttrName("默认");
            spuAttributeVO.setAttrValue(attrValueList);
            spuAttributeVOList.add(spuAttributeVO);

            goodsSkuVO.setInventory(pa.getInventory());
            goodsSkuVO.setSalePrice(pa.getSalePrice() == null ? "0" :
                    pa.getSalePrice().compareTo(new BigDecimal("0"))== 0? "0" :
                            pa.getSalePrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
            goodsSkuVO.setPrice(pa.getPrice() == null ? "0" :
                    pa.getPrice().compareTo(new BigDecimal("0"))== 0? "0" :
                            pa.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
            goodsSkuVO.setProductName(pa.getProductName());
            goodsSkuVO.setProductPicPath(pa.getSpePicPath() == null || "".equals(pa.getSpePicPath()) ? pa.getDefaultPicPath() : pa.getSpePicPath());
            goodsSkuVO.setProductId(pa.getProductId());
            goodsSkuVO.setSkuId(pa.getSkuId());
            goodsSkuVO.setAttr(spuAttributeVOList);
            goodsSkuVO.setTransportMoney(pa.getTransportMoney() == null ? "0.00" : "" +
                    pa.getTransportMoney().setScale(2,BigDecimal.ROUND_DOWN).toString());
            return goodsSkuVO;
        } else {
            // 返回属性列表
            for (SpuAttributeBO spuAttributeBO : spuAttrList) {
                boolean flag = true;
                for (SpuAttributeVO spuAttributeVO : spuAttributeVOList) {
                    if (spuAttributeVO.getAttrId().equals(spuAttributeBO.getAttrId())) {
                        flag = false;
                        SpuAttrValueVO newSpuAttrValueVO = new SpuAttrValueVO();
                        newSpuAttrValueVO.setAttrValueName(spuAttributeBO.getAttrValueName());
                        newSpuAttrValueVO.setAttrValueId(spuAttributeBO.getAttrValueId());
                        newSpuAttrValueVO.setIsSelected(0);
                        spuAttributeVO.getAttrValue().add(newSpuAttrValueVO);
                    }
                }
                if (flag) {
                    SpuAttributeVO newSpuAttrbuteVO = new SpuAttributeVO();
                    newSpuAttrbuteVO.setAttrId(spuAttributeBO.getAttrId());
                    newSpuAttrbuteVO.setAttrName(spuAttributeBO.getAttrName());
                    newSpuAttrbuteVO.setAttrValue(new ArrayList<>());

                    SpuAttrValueVO newSpuAttrValueVO = new SpuAttrValueVO();
                    newSpuAttrValueVO.setAttrValueName(spuAttributeBO.getAttrValueName());
                    newSpuAttrValueVO.setAttrValueId(spuAttributeBO.getAttrValueId());
                    newSpuAttrValueVO.setIsSelected(0);
                    newSpuAttrbuteVO.getAttrValue().add(newSpuAttrValueVO);
                    spuAttributeVOList.add(newSpuAttrbuteVO);
                }
            }
            // 选中价格最低的产品的属性
            for (Integer attrValueId : attrValueIds) {
                for (SpuAttributeVO vo : spuAttributeVOList) {
                    for (SpuAttrValueVO attrVo : vo.getAttrValue()) {
                        if (attrVo.getAttrValueId().equals(attrValueId)) {
                            attrVo.setIsSelected(1);
                        }
                    }
                }
            }
            goodsSkuVO.setInventory(pa.getInventory());
            goodsSkuVO.setSalePrice(pa.getSalePrice() == null ? "0" :
                    pa.getSalePrice().compareTo(new BigDecimal("0"))== 0? "0" :
                            pa.getSalePrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
            goodsSkuVO.setPrice(pa.getPrice() == null ? "0" :
                    pa.getPrice().compareTo(new BigDecimal("0"))== 0? "0" :
                            pa.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
            goodsSkuVO.setProductName(pa.getProductName());
            goodsSkuVO.setProductPicPath(pa.getSpePicPath() == null || "".equals(pa.getSpePicPath()) ? pa.getDefaultPicPath() : pa.getSpePicPath());
            goodsSkuVO.setProductId(pa.getProductId());
            goodsSkuVO.setSkuId(pa.getSkuId());
            goodsSkuVO.setAttr(spuAttributeVOList);
            goodsSkuVO.setTransportMoney(pa.getTransportMoney() == null ? "0.00" : "" +
                    pa.getTransportMoney().setScale(2,BigDecimal.ROUND_DOWN).toString());
            return goodsSkuVO;
        }
    }

    @Override
    public GoodsInfoToOrderBO getGoodsInfoToOrder(Integer productId) {
        return baseGoodsSPUMapper.getGoodsInfoToOrder(productId);
    }

    @Override
    public Integer updateTotalInventoryBySkuId(Integer id, Integer num) {
        return baseGoodsSPUMapper.updateTotalInventoryBySkuId(id,num);
    }

    @Deprecated
    @Override
    public List<GoodsDetailVO> getSpecialSaleGoods(List<String> productModelNumberList) {
        List<GoodsDetailVO> specialSaleGoodsList;
        try {
            List<Integer> specialSaleGoodsIdList = baseGoodsSPUMapper.getSpuIdByProduct(productModelNumberList);
            if (specialSaleGoodsIdList == null || specialSaleGoodsIdList.size() <= 0) {
                log.warn("获取特卖商品spuId集合为空！");
                return null;
            }

            specialSaleGoodsList = baseGoodsSPUMapper.getSpecialSaleGoods(specialSaleGoodsIdList);
        } catch (Exception e) {
            log.error("获取特卖商品 ===> exception："+e);
            return null;
        }
        for (GoodsDetailVO goodsDetailVO : specialSaleGoodsList) {
            goodsDetailVO.setPrice(goodsDetailVO.getPrice().substring(0,goodsDetailVO.getPrice().length()-2));
            goodsDetailVO.setSalePrice(goodsDetailVO.getSalePrice().substring(0,goodsDetailVO.getSalePrice().length()-2));
        }
        return specialSaleGoodsList;

    }

    @Override
    public List<GoodsDetailVO> getSpecialOfferGoods(GoodsListPO goodsListPO) {
        List<GoodsDetailBO> goodsDetailBOList = baseGoodsSPUMapper.getSpecialOfferGoods(goodsListPO);
        return convertDetailBOtoDetailVO(goodsDetailBOList);
    }

    @Override
    public List<GoodsDetailVO> getPresellGoods(GoodsListPO goodsListPO) {
        List<GoodsDetailBO> goodsDetailBOList = baseGoodsSPUMapper.getPresellGoods(goodsListPO);
        return convertDetailBOtoDetailVO(goodsDetailBOList);
    }

    private List<GoodsDetailVO> convertDetailBOtoDetailVO(List<GoodsDetailBO> boList){
        List<GoodsDetailVO> goodsDetailVOList = new ArrayList<>();
        for (GoodsDetailBO goodsDetailBO : boList) {
            GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
            goodsDetailVO.setId(goodsDetailBO.getId());
            goodsDetailVO.setProductName(goodsDetailBO.getSpuName());
            ResPic resPic = resPicService.get(goodsDetailBO.getMainPicId());
            goodsDetailVO.setPicPath(resPic == null? "" : resPic.getPicPath());
            goodsDetailVO.setTotalInventory(goodsDetailBO.getTotalInventory());
            try {
                List<BigDecimal> priceList = NumberUtil.convertStrToNumList(goodsDetailBO.getPrices(), BigDecimal.class);
                List<BigDecimal> salePriceList = NumberUtil.convertStrToNumList(goodsDetailBO.getSalePrices(), BigDecimal.class);
                int minIndex = NumberUtil.findMinIndex(priceList, new BigDecimal("0"), false);
                goodsDetailVO.setPrice(minIndex == -1? "0" :
                        priceList.get(minIndex).compareTo(new BigDecimal("0"))== 0? "0" :
                                priceList.get(minIndex).setScale(2,BigDecimal.ROUND_DOWN).toString());
                goodsDetailVO.setSalePrice(minIndex == -1? "0" :
                        salePriceList.get(minIndex).compareTo(new BigDecimal("0"))== 0? "0" :
                                salePriceList.get(minIndex).setScale(2,BigDecimal.ROUND_DOWN).toString());
            } catch (Exception e) {
                log.error(e.toString());
                throw new RuntimeException("字符串转数字错误");
            }
            goodsDetailVOList.add(goodsDetailVO);
        }
        return goodsDetailVOList;
    }
}
