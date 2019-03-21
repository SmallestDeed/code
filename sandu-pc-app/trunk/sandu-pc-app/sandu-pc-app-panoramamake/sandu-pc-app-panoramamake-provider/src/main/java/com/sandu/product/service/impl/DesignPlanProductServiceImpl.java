package com.sandu.product.service.impl;

import com.sandu.common.util.StringUtils;
import com.sandu.panorama.dao.DesignPlanStoreReleaseDetailsMapper;
import com.sandu.product.dao.DesignPlanProductMapper;
import com.sandu.product.model.input.PlanProductModel;
import com.sandu.product.model.output.BaseProductDetailsVo;
import com.sandu.product.model.output.ProductCostDetail;
import com.sandu.product.model.output.ProductsCost;
import com.sandu.product.model.output.ProductsCostType;
import com.sandu.product.service.DesignPlanProductService;
import com.sandu.system.model.BasePlatform;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.BasePlatformService;
import com.sandu.system.service.ResPicService;
import com.sandu.system.service.SysDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 3:37 2018/6/27 0027
 * @Modified By:
 */
@Service("designPlanProductService")
public class DesignPlanProductServiceImpl implements DesignPlanProductService {

    private static final Logger logger = LoggerFactory.getLogger(DesignPlanProductServiceImpl.class);
    public static final String PC_PLATFORM_CODE = "pc2b";
    @Autowired
    private DesignPlanProductMapper designPlanProductMapper;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
   /* @Autowired
    private ResPicService resPicService;*/
    @Autowired
    private DesignPlanStoreReleaseDetailsMapper designPlanStoreReleaseDetailsMapper;

    @Override
    public List<ProductsCostType> getDesignPlanProductList(PlanProductModel model) {

        Integer recommendedId = model.getRecommendedId();
        Integer renderSceneId = model.getRenderSceneId();
        Integer renderPlanId  = model.getDesignPlanId();
        if (recommendedId == null && renderSceneId == null && renderPlanId == null) {
            return null;
        }

        Integer platformId;
        BasePlatform basePlatform = basePlatformService.getByPlatformCode(PC_PLATFORM_CODE);
        if (basePlatform == null) {
            platformId = 2;
        } else {
            platformId = basePlatform.getId();
        }
        model.setPlatformId(platformId);

        List<ProductCostDetail> productCostDetailList;
        if (recommendedId != null) {
            productCostDetailList = designPlanProductMapper.getRecommendedProductList(model);
        } else if (renderSceneId != null) {
            productCostDetailList = designPlanProductMapper.getRenderSceneProductList(model);
        } else if (renderPlanId != null) {
            productCostDetailList = designPlanProductMapper.getdesignPlanProductList(model);
        } else {
            logger.error("getDesignPlanProductList------方案id都为空！！！");
            return null;
        }
        if (productCostDetailList == null || productCostDetailList.size() <= 0) {
            logger.error("getDesignPlanProductList--获取产品清单为空：productCostDetailList--" + productCostDetailList);
            return null;
        }
        //获取数据字典价格单位
        SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
        if (dictionary == null) {
            logger.error("getDesignPlanProductList--获取数据字典价格单位为空：dictionary--" + dictionary);
            dictionary = new SysDictionary();
            dictionary.setName("元");
        }
        //查询所有分类
        //产品总类别：电器，硬装，软装
        List<ProductsCostType> productsCostTypeList = this.getProductCostType();
        //产品小类别集合
        List<ProductsCost> productsCostList = this.getProductCost();

        if (productsCostList == null || productsCostList.size() <= 0 || productsCostTypeList == null || productsCostTypeList.size() <= 0) {
            logger.error("getDesignPlanProductList--获取产品大小类有一个为空：productsCostTypeList--" + productsCostTypeList + ",productsCostList--" + productsCostList);
            return null;
        }

        //遍历产品列表和产品小类列表，把产品放入对应的小类
        //产品小分类的总价
        BigDecimal costTotalPrice;
        for (ProductsCost productsCost : productsCostList) {
            costTotalPrice = new BigDecimal(0);
            for (ProductCostDetail productCostDetail : productCostDetailList) {
                //装进我家多点 design_plan_product_render_scene表中的is_replace_texture是否材质替换为null
                //如果为null，变为0
                if (null == productCostDetail.getIsReplaceTexture()) {
                    productCostDetail.setIsReplaceTexture(0);
                }
                if (productsCost.getCostTypeCode().equals(productCostDetail.getTypeValueKey())) {
                    productCostDetail.setProductUnit(dictionary.getName());
                    productsCost.getDetailList().add(productCostDetail);
                    productsCost.setSalePriceValueName(dictionary.getName());
                    productsCost.setPlanId(recommendedId==null?renderSceneId:recommendedId);
                    if (productsCost.getProductIds() == null || productsCost.getProductIds().length() == 0) {
                        productsCost.setProductIds(productCostDetail.getProductId() + "");
                    } else {
                        productsCost.setProductIds(productsCost.getProductIds() + "," + productCostDetail.getProductId());
                    }
                    productsCost.setProductCount(productsCost.getProductCount() + 1);
                    //如果产品总价不为空，则把产品总价加到对应的分类总价中
                    if (productCostDetail.getTotalPrice() != null) {
                        costTotalPrice = costTotalPrice.add(productCostDetail.getTotalPrice());
                    }
                }
            }
            //初始变量的值小于=0的话表示分类下没有产品属于用户对应的公司，则不显示价格
            if (costTotalPrice.intValue() <= 0) {
                productsCost.setTotalPrice(null);
            } else {
                productsCost.setTotalPrice(costTotalPrice);
            }
        }
        //把没有产品的小分类移除
        Iterator<ProductsCost> productsCostIterator = productsCostList.iterator();
        while (productsCostIterator.hasNext()) {
            ProductsCost productsCost = productsCostIterator.next();
            if (productsCost.getDetailList().size() == 0) {
                productsCostIterator.remove();
            }
        }
        //把产品小类放入对应的大类
        //产品大分类的总价
        BigDecimal costTypeTotalPrice;
        for (ProductsCostType productsCostType : productsCostTypeList) {
            costTypeTotalPrice = new BigDecimal(0);
            for (ProductsCost productsCost : productsCostList) {
                if (productsCostType.getCostTypeCode().equals(productsCost.getProductTypeValue())) {
                    productsCostType.getDetailList().add(productsCost);
                    productsCostType.setPlanId(recommendedId==null?renderSceneId:recommendedId);
                    productsCostType.setProductCount(productsCost.getProductCount());
                    productsCostType.setSalePriceValueName(dictionary.getName());
                    if (productsCostType.getCostCodes() == null || productsCostType.getCostCodes().length() == 0) {
                        productsCostType.setCostCodes(productsCost.getCostTypeCode());
                    } else {
                        productsCostType.setCostCodes(productsCostType.getCostCodes() + "," + productsCost.getCostTypeCode());
                    }
                    //如果产品小类总价不为空，则把产品小分类总价加到对应的大分类总价中
                    if (productsCost.getTotalPrice() != null) {
                        costTypeTotalPrice = costTypeTotalPrice.add(productsCost.getTotalPrice());
                    }
                }
            }
            //初始变量的值小于=0的话表示分类下没有产品属于用户对应的公司，则不显示价格
            if (costTypeTotalPrice.intValue() <= 0) {
                productsCostType.setTotalPrice(null);
            } else {
                productsCostType.setTotalPrice(costTypeTotalPrice);
            }
        }

        //把没有小分类的大分类移除
        Iterator<ProductsCostType> productsCostTypeIterator = productsCostTypeList.iterator();
        while (productsCostTypeIterator.hasNext()) {
            ProductsCostType productsCostType = productsCostTypeIterator.next();
            if (productsCostType.getDetailList().size() == 0) {
                productsCostTypeIterator.remove();
            }
        }
        return productsCostTypeList;
    }

    //获取数据字典产品总类别
    private List<ProductsCostType> getProductCostType() {
        return sysDictionaryService.getProductTotalType();
    }

    //获取数据字典小类别
    private List<ProductsCost> getProductCost() {
        return sysDictionaryService.getProductCost();
    }

    /**
     * 获取产品详情 TEMP
     * @param productId
     * @return
     */
    @Override
    public BaseProductDetailsVo getProductDetails(Integer productId){
        if( productId == null ){
            return null;
        }
        BaseProductDetailsVo productDetailsVo = designPlanProductMapper.getProductDetails(productId);
        if( productDetailsVo != null ){
            List<String> picPathList = new ArrayList<>();
            String picIds = productDetailsVo.getPicIds();
            if( StringUtils.isNotBlank(picIds) ){
                String[] picIdArr = picIds.split(",");
                for( String picIdsStr : picIdArr ){
                    if( StringUtils.isNotBlank(picIdsStr) ){
                        String picPath = designPlanStoreReleaseDetailsMapper.getResourcePathById(Integer.valueOf(picIdsStr), "res_pic");
                     /*   ResPic resPic = resPicService.get(Integer.valueOf(picIdsStr));
                        if( resPic != null ){*/
                        picPathList.add(picPath);
                       /* }*/
                    }
                }
                productDetailsVo.setPicList(picPathList);
            }
            return productDetailsVo;
        }
        return null;
    }
}
