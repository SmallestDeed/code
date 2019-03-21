package com.nork.mobile.service.impl;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.mobile.dao.MobileDesignPlanProductMapper;
import com.nork.mobile.dao.SysDictionaryProductTypeMapper;
import com.nork.mobile.model.search.MobileDesignPlanProduct;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.mobile.service.MobileDesignPlanProductService;
import com.nork.product.model.BaseCompany;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.BasePlatform;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.BasePlatformService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.nork.system.controller.PlatformController.BASE_PLATFORM_INFO;
import static com.nork.system.controller.PlatformController.MOBILE2B_PLATFORM_CODE;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:35 2018/5/23 0023
 * @Modified By:
 */
@Service("mobileDesignPlanProductService")
public class MobileDesignPlanProductServiceImpl implements MobileDesignPlanProductService {

    private static Logger logger = Logger.getLogger(MobileDesignPlanProductServiceImpl.class);
    private final Gson GSON = new Gson();
    @Autowired
    private MobileDesignPlanProductMapper mobileDesignPlanProductMapper;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysDictionaryProductTypeMapper sysDictionaryProductTypeMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private BasePlatformService basePlatformService;

    /**
     * 查询产品清单
     *
     * @param model
     * @return
     */
    @Override
    public List<ProductsCostType> getDesignPlanProductList(MobileRenderingModel model) {
        Integer planId = model.getPlanId();
        String msgId = model.getMsgId();
        Integer userId = model.getUserId();
        if (userId == null || planId == null) {
            logger.error("getDesignPlanProductList -- userId:" + userId + ",planId" + planId);
            return null;
        }
        //获取最终公司id
        Integer companyId = this.getCompanyIdByUserId(userId);
        if (companyId == null || companyId.intValue() <= 0) {
            logger.error("getDesignPlanProductList -- sysUser.getBusinessAdministrationId() is null:" + companyId);
            return null;
        }
//        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainName("sandu");
//        if (baseCompany != null) {
//            if (companyId.intValue() == baseCompany.getId().intValue()) {
//
//            }
//        }


        //添加平台过滤，平台id
        Integer platformId = null;
        BasePlatform basePlatform;
        String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
            platformId = basePlatform.getId();
        }
        if (null == platformId) {
            basePlatform = basePlatformService.getByPlatformCode(MOBILE2B_PLATFORM_CODE);
            platformId = basePlatform.getId();
        }

        MobileDesignPlanProduct designPlanProduct = new MobileDesignPlanProduct();
        designPlanProduct.setPlanId(planId);
        designPlanProduct.setPlatformId(platformId);
        List<ProductCostDetail> productCostDetailList;
        //获取产品清单
        if ("renderScene".equals(msgId)) {
            productCostDetailList = mobileDesignPlanProductMapper.getRenderSceneProductList(designPlanProduct);
        } else if ("recommended".equals(msgId)) {
            productCostDetailList = mobileDesignPlanProductMapper.getRecommendedProductList(designPlanProduct);
        } else {
            logger.error("getDesignPlanProductList--产品清单列表msgId参数有误：msgId--" + msgId);
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
            return null;
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
                //不是本公司的产品则产品总价和单价都为null
                if (companyId.intValue() != productCostDetail.getCompanyId().intValue()) {
                    productCostDetail.setUnitPrice(null);
                    productCostDetail.setTotalPrice(null);
                }
                //装进我家多点 design_plan_product_render_scene表中的is_replace_texture是否材质替换为null
                //如果为null，变为0
                if (null == productCostDetail.getIsReplaceTexture()) {
                    productCostDetail.setIsReplaceTexture(0);
                }
                if (productsCost.getCostTypeCode().equals(productCostDetail.getTypeValueKey())) {
                    productCostDetail.setProductUnit(dictionary.getName());
                    productsCost.getDetailList().add(productCostDetail);
                    productsCost.setUserId(userId);
                    productsCost.setSalePriceValueName(dictionary.getName());
                    productsCost.setPlanId(planId);
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
                    productsCostType.setPlanId(planId);
                    productsCostType.setProductCount(productsCost.getProductCount());
                    productsCostType.setSalePriceValueName(dictionary.getName());
                    productsCostType.setUserId(userId);
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
        //查询价格

        //查询数量

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
        return sysDictionaryProductTypeMapper.getProductTotalType();
    }

    //获取数据字典小类别
    private List<ProductsCost> getProductCost() {
        return sysDictionaryProductTypeMapper.getProductCost();
    }

    //通过用户id获取对应的最终的公司id
    public Integer getCompanyIdByUserId(Integer userId) {
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            logger.error("getDesignPlanProductList -- sysUser is null:" + sysUser);
            return null;
        }
        Integer companyId = sysUser.getBusinessAdministrationId();
        Integer userType = sysUser.getUserType();
        if (userType.intValue() == 3) {
            BaseCompany baseCompany = baseCompanyService.get(companyId);
            if (baseCompany != null) {
                companyId = baseCompany.getPid();
            }
        }
        return companyId;
    }
}
