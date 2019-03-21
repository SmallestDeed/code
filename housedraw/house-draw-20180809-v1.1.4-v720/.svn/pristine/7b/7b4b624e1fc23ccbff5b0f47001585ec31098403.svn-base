package com.sandu.service.product.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.redis.RedisService;
import com.sandu.common.constant.redis.RedisKeyConstant;
import com.sandu.util.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sandu.api.product.bo.BaseProductBO;
import com.sandu.api.product.bo.ProductTypeBO;
import com.sandu.api.product.input.BaseProductQuery;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.model.ResPic;
import com.sandu.api.product.service.BaseProductService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.res.service.ResPicService;
import com.sandu.service.product.dao.BaseProductMapper;
import com.sandu.service.system.dao.SystemDictionaryMapper;
import com.sandu.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月21日
 */

@Slf4j
@Service
public class BaseProductServiceImpl implements BaseProductService {

    private static final String DOOR_TYPE_VALUE = "3";
    private static final Integer DOOR_SMALL_TYPE_VALUE = 19;

    @Autowired
    private BaseProductMapper baseProductMapper;

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    ResPicService resPicService;

    @Autowired
    ResModelService resModelService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BaseProductService baseProductService;

    @Autowired
    RedisService redisService;

    @Value("${redis.soft.product.expire:4320}")
    private Long redisSoftProductExpire;

    @Override
    public Map<String, Object> listBaimoProduct(BaseProductQuery query) {
        Map<String, Object> map = new HashMap<>();

        // 原门框处理 Fuck Door
        if ("-19".equals(query.getProductTypeValue())) {
            // qiangm -> 3
            query.setProductTypeValue(DOOR_TYPE_VALUE);
            // basic_mengk -> 19
            query.setProductSmallTypeValue(DOOR_SMALL_TYPE_VALUE);
        }

//        Long count = baseProductMapper.getProductByTypeCount(query, query.getProductTypeValue().split(","));
//        if (count > 0) {
//            products = baseProductMapper.listProductByType(query, query.getProductTypeValue().split(","));
//            // 处理产品信息
//            this.handlerProduct(products);
//        }
//
//        map.put("total", count);

        List<BaseProductBO> products = baseProductMapper.listProductByType(query, query.getProductTypeValue().split(","));
        // 处理产品信息
        this.handlerProduct(products);

        map.put("list", products);

        return map;
    }

    @Override
    public Map<String, Object> listBaimoProduct(BaseProductQuery query, boolean useCache) {
        if (useCache) {
            String hashKey = getHashKey(query);
            String key = RedisKeyConstant.HOUSE_DRAW_SOFT_PRODUCT.toString();
            String value = redisService.getCacheByHash(key, hashKey, null,
                    TimeUnit.MINUTES, () -> JSON.stringify(baseProductService.listBaimoProduct(query)));

            if (!StringUtils.isEmpty(value)) {
                log.info("listBaimoProduct get cache by hashKey => {}", hashKey);
                return JSON.parse(value, new TypeReference<Map<String, Object>>() {
                });
            }
        }

        return this.listBaimoProduct(query);
    }

    String getHashKey(BaseProductQuery query) {
        StringBuilder buf = new StringBuilder(":listBaimoProduct");
        if (!StringUtils.isEmpty(query.getProductCode())) {
            buf.append(":").append(query.getProductCode());
        }

        if (!StringUtils.isEmpty(query.getProductTypeValue())) {
            buf.append(":").append(query.getProductTypeValue());
        }

        if (query.getProductSmallTypeValue() != null) {
            buf.append(":").append(query.getProductSmallTypeValue());
        }

        if (query.getPageNum() != null) {
            buf.append(":").append(query.getPageNum());
        }

        if (query.getPageSize() != null) {
            buf.append(":").append(query.getPageSize());
        }

        if (query.getPutawayState() != null && query.getPutawayState().length > 0) {
            for (Integer state : query.getPutawayState()) {
                buf.append(":").append(state);
            }
        }

        return buf.toString().replaceFirst(":", "");
    }

    /**
     * 处理产品
     *
     * @param products
     */
    private void handlerProduct(List<BaseProductBO> products) {
        if (products == null || products.isEmpty()) {
            log.warn("需要处理的产品为空，products => {}", products);
            return;
        }

        // 资源处理
        Map<String, Set<Long>> mapRes = handlerResource(products);
        Map<String, ResPic> mapPics = resPicService.getResPicMap(mapRes.get("pics"));
        Map<String, ResModel> mapModels = resModelService.getResModelMap(mapRes.get("models"));

        // 产品分类
        final Map<Long, ProductTypeBO> productTypes = listProductType(products);

        for (BaseProductBO p : products) {
            // 图片
            ResPic resPic = mapPics.get(Objects.toString(p.getPicId(), Utils._ONE_VALUE));
            if (resPic != null) {
                p.setPicName(resPic.getPicName());
                p.setPicPath(resPic.getPicPath());
            }

            // 模型
            ResModel resModel = mapModels.get(Objects.toString(p.getModelId(), Utils._ONE_VALUE));
            if (resModel != null) {
                p.setModelName(resModel.getModelName());
                p.setModelPath(resModel.getModelPath());
            }

            // C# null 处理 (Fuck C#)
            p.setPicId(p.getId() == null ? 0 : p.getId());
            p.setModelId(p.getId() == null ? 0 : p.getId());

            // 大小分类
            this.buildProductType(productTypes.get(p.getId()), p);
        }
    }

    /**
     * 处理图片资源
     *
     * @param products
     * @return
     */
    private Map<String, Set<Long>> handlerResource(List<BaseProductBO> products) {
        Map<String, Set<Long>> map = new HashMap<>();
        Set<Long> pics = new HashSet<>();
        Set<Long> models = new HashSet<>();
        for (BaseProductBO p : products) {
            pics.add(p.getPicId());
            models.add(p.getModelId());
        }

        map.put("pics", pics);
        map.put("models", models);
        return map;
    }

    /**
     * 处理 大小分类
     *
     * @param products
     * @return
     */
    @Override
    public Map<Long, ProductTypeBO> listProductType(List<BaseProductBO> products) {
        List<ProType> proTypes = new ArrayList<>();
        for (BaseProductBO p : products) {
            // productId、大分类、小分类
            proTypes.add(new ProType(p.getId(), p.getProductTypeValue(), p.getProductSmallTypeValue()));
        }

        // Fuck NullPointerException
        proTypes.removeIf(p -> (p == null || p.getProType() == null || p.getProSmallType() == null));

        if (proTypes.isEmpty()) {
            log.warn("产品的分类数据可能出现异常, products => {}", products);
            return new HashMap<>();
        }

        List<ProductTypeBO> types = systemDictionaryMapper.getProductTypes(proTypes.stream().distinct().collect(Collectors.toList()));
        if (types != null && !types.isEmpty()) {
            return types.stream().collect(Collectors.toMap(ProductTypeBO::getProId, t -> t, (p, n) -> n));
        }

        return new HashMap<>();
    }

    @Override
    public void buildProductType(ProductTypeBO proType, BaseProductBO product) {
        if (proType != null) {
            // 大分类
            product.setProductTypeName(proType.getProTypeName());
            product.setProductTypeCode(proType.getProTypeCode());
            product.setProductTypeValue(proType.getProTypeVal());
            // 小分类
            product.setProductSmallTypeName(proType.getProSmallTypeName());
            product.setProductSmallTypeCode(proType.getProSmallTypeCode());
            product.setProductSmallTypeValue(proType.getProSmallTypeVal());
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public class ProType {
        private Long proId;
        private Integer proType;
        private Integer proSmallType;
    }
}
