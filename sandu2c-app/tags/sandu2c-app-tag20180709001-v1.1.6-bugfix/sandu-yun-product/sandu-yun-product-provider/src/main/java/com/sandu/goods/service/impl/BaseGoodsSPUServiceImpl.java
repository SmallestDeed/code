package com.sandu.goods.service.impl;


import com.sandu.common.util.NumberUtil;
import com.sandu.goods.dao.BaseGoodsSKUMapper;
import com.sandu.goods.dao.BaseGoodsSPUMapper;
import com.sandu.goods.model.BO.*;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.model.BaseGoodsSPU;
import com.sandu.goods.model.PO.GoodsListPO;
import com.sandu.goods.output.*;
import com.sandu.goods.service.BaseGoodsSPUService;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Log4j
@Service("baseGoodsSPUService")
public class BaseGoodsSPUServiceImpl implements BaseGoodsSPUService
{
    @Autowired
    private BaseGoodsSPUMapper baseGoodsSPUMapper;
    @Autowired
    private BaseGoodsSKUMapper baseGoodsSKUMapper;
    @Autowired
    private ResPicService resPicService;

    @Override
    public BaseGoodsSPU selectByPrimaryKey(Integer id)
    {
        return baseGoodsSPUMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getGoodsListCount(GoodsListPO goodsListPO)
    {
        goodsListPO.setStart(null);
        goodsListPO.setLimit(null);

        return baseGoodsSPUMapper.getGoodsSpuIdList(goodsListPO).size();
    }

    @Override
    public List<GoodsVO> getGoodsList(GoodsListPO goodsListPO)
    {
        List<Integer> ids = baseGoodsSPUMapper.getGoodsSpuIdList(goodsListPO);
        if (ids == null || ids.size() == 0)
        {
            return null;
        }

        List<GoodsBO> goodsBOList = baseGoodsSPUMapper.getGoodsSpuList(ids);
        List<GoodsVO> goodsVOList = new ArrayList<>();

        for (GoodsBO goodsBO: goodsBOList)
        {
            GoodsVO vo = new GoodsVO();
            vo.setProductId(goodsBO.getId());
            vo.setPicPath(goodsBO.getPic());
            vo.setProductName(goodsBO.getGoodsName());
            List<BigDecimal> priceList = null;
            try
            {
                priceList = NumberUtil.convertStrToNumList(goodsBO.getPrices(),BigDecimal.class);
            } catch (Exception e)
            {
                log.error("BaseGoodsSPUService.getGoodsList() row:76",e);
                e.printStackTrace();
            }
            if (priceList == null || priceList.size() == 0)
            {
                vo.setSalePrice("0");
            }else
            {
                BigDecimal salePrice = NumberUtil.findMin(priceList,new BigDecimal(0),false);
                vo.setSalePrice(""+(salePrice == null?"0":salePrice.intValue()));
            }
            goodsVOList.add(vo);
        }
        goodsVOList.sort((a,b)->b.getProductId() - a.getProductId());
        return goodsVOList;
    }

    @Override
    public GoodsDetailVO getGoodsDetail(Integer id)
    {
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        GoodsDetailBO goodsDetailBO = baseGoodsSPUMapper.getGoodsDetail(id);

        List<Integer> picIdList = null;
        List<BigDecimal> prices = null;
        List<Integer> salePrices = null;

        try
        {
            picIdList = NumberUtil.convertStrToNumList(goodsDetailBO.getPicIds(), Integer.class);
            prices = NumberUtil.convertStrToNumList(goodsDetailBO.getPrices(), BigDecimal.class);
            salePrices = NumberUtil.convertStrToNumList(goodsDetailBO.getSalePrices(),Integer.class);
        } catch (Exception e)
        {
            log.error("BaseGoodsSPUService.getGoodsDetail() row:109",e);
            e.printStackTrace();
        }

        if (goodsDetailBO.getMainPicId() != null)
        {
            picIdList.add(goodsDetailBO.getMainPicId());
        }
        if (picIdList.size() > 0)
        {
            List<ResPic> pics = resPicService.getResPicByIds(picIdList);
            List<String> picPathList = new ArrayList<>();
            for (ResPic resPic : pics)
            {
                picPathList.add(resPic.getPicPath());
            }
            goodsDetailVO.setSmallPiclist(picPathList);
        }

        int priceIndex = NumberUtil.findMinIndex(prices,new BigDecimal(0),false) == -1?
                NumberUtil.findMinIndex(prices,new BigDecimal(0),true):
                NumberUtil.findMinIndex(prices,new BigDecimal(0),false);
        goodsDetailVO.setLimitation(goodsDetailBO.getLimitation());
        goodsDetailVO.setSalePrice(priceIndex == -1?"0":salePrices.get(priceIndex).toString());
        goodsDetailVO.setPrice(priceIndex == -1?"0":""+prices.get(priceIndex).intValue());
        goodsDetailVO.setProductName(goodsDetailBO.getSpuName());
        goodsDetailVO.setProductDesc(goodsDetailBO.getProductDesc());
        goodsDetailVO.setSendOutDay(goodsDetailBO.getDeliveryDay());
        goodsDetailVO.setId(goodsDetailBO.getId());
        goodsDetailVO.setTransportPay(goodsDetailBO.getTransportPay()==null?"0":""+goodsDetailBO.getTransportPay().intValue());

        return goodsDetailVO;
    }

    @Override
    public GoodsSkuVO getSpuAttrList(Integer id)
    {
        GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
        List<SpuAttributeBO> spuAttrList = baseGoodsSPUMapper.getSpuAttrList(id);
        List<SkuPriceAndAttrBO> paList = baseGoodsSKUMapper.getSkuPriceAndAttr(id);
        List<BigDecimal> priceList = new ArrayList<>();
        for (SkuPriceAndAttrBO skuPriceAndAttrBO : paList)
        {
            priceList.add(skuPriceAndAttrBO.getPrice() == null?new BigDecimal(0):skuPriceAndAttrBO.getPrice());
        }
        int minIndex = NumberUtil.findMinIndex(priceList,new BigDecimal(0),false);
        if (minIndex == -1)
        {
            return null;
        }
        SkuPriceAndAttrBO pa = paList.get(minIndex);
        List<Integer> attrValueIds = new ArrayList<>();
        try
        {
            attrValueIds = NumberUtil.convertStrToNumList(pa.getAttrValueIds(), Integer.class);
        } catch (Exception e)
        {
            log.error("BaseGoodsSPUService.getSpuAttrList() row:152",e);
            e.printStackTrace();
        }
        List<SpuAttributeVO> spuAttributeVOList = new ArrayList<>();
        if (spuAttrList == null || spuAttrList.size() == 0)
        {
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
            goodsSkuVO.setSalePrice(pa.getSalePrice().toString());
            goodsSkuVO.setPrice(pa.getPrice().toString());
            goodsSkuVO.setProductName(pa.getProductName());
            goodsSkuVO.setProductPicPath(pa.getSpePicPath() == null || "".equals(pa.getSalePrice())?pa.getDefaultPicPath():pa.getSpePicPath());
            goodsSkuVO.setProductId(pa.getProductId());
            goodsSkuVO.setSkuId(pa.getSkuId());
            goodsSkuVO.setAttr(spuAttributeVOList);
            goodsSkuVO.setTransportMoney(pa.getTransportMoney()==null?"0":""+pa.getTransportMoney().intValue());
            return goodsSkuVO;
        }else
        {
            for (SpuAttributeBO spuAttributeBO : spuAttrList)
            {
                boolean flag = true;
                for (SpuAttributeVO spuAttributeVO : spuAttributeVOList)
                {
                    if (spuAttributeVO.getAttrId().equals(spuAttributeBO.getAttrId()))
                    {
                        flag = false;
                        SpuAttrValueVO newSpuAttrValueVO = new SpuAttrValueVO();
                        newSpuAttrValueVO.setAttrValueName(spuAttributeBO.getAttrValueName());
                        newSpuAttrValueVO.setAttrValueId(spuAttributeBO.getAttrValueId());
                        newSpuAttrValueVO.setIsSelected(0);
                        spuAttributeVO.getAttrValue().add(newSpuAttrValueVO);
                    }
                }
                if (flag)
                {
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
            for (Integer attrValueId : attrValueIds)
            {
                for (SpuAttributeVO vo : spuAttributeVOList)
                {
                    for (SpuAttrValueVO attrVo : vo.getAttrValue())
                    {
                        if (attrVo.getAttrValueId().equals(attrValueId))
                        {
                            attrVo.setIsSelected(1);
                        }
                    }
                }
            }
            goodsSkuVO.setInventory(pa.getInventory());
            goodsSkuVO.setSalePrice(pa.getSalePrice()==null?"0":pa.getSalePrice().toString());
            goodsSkuVO.setPrice(pa.getPrice()==null?"0":""+pa.getPrice().intValue());
            goodsSkuVO.setProductName(pa.getProductName());
            goodsSkuVO.setProductPicPath(pa.getSpePicPath() == null || "".equals(pa.getSalePrice())?pa.getDefaultPicPath():pa.getSpePicPath());
            goodsSkuVO.setProductId(pa.getProductId());
            goodsSkuVO.setSkuId(pa.getSkuId());
            goodsSkuVO.setAttr(spuAttributeVOList);
            goodsSkuVO.setTransportMoney(pa.getTransportMoney()==null?"0":""+pa.getTransportMoney().intValue());
            return goodsSkuVO;
        }
    }

    @Override
    public GoodsInfoToOrderBO getGoodsInfoToOrder(Integer productId)
    {
        return baseGoodsSPUMapper.getGoodsInfoToOrder(productId);
    }
}
