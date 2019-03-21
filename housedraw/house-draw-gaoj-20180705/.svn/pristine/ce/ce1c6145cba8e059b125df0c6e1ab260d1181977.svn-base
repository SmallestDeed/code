package com.sandu.service.product.impl;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.redis.RedisService;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.redis.RedisKeyConstant;
import com.sandu.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sandu.api.product.bo.BaseProductBO;
import com.sandu.api.product.bo.GroupProductBO;
import com.sandu.api.product.bo.ProductTypeBO;
import com.sandu.api.house.input.GroupProductQuery;
import com.sandu.api.res.model.ResFile;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.model.ResPic;
import com.sandu.api.product.service.BaseProductService;
import com.sandu.api.product.service.GroupProductService;
import com.sandu.api.res.service.ResFileService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.res.service.ResPicService;
import com.sandu.service.product.dao.GroupProductMapper;
import com.sandu.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018年1月21日
 */

@Slf4j
@Service
public class GroupProductServiceImpl implements GroupProductService {

    @Autowired
    private GroupProductMapper groupProductMapper;

    @Autowired
    private ResModelService resModelService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private ResFileService resFileService;

    @Autowired
    private BaseProductService baseProductService;

    @Autowired
    RedisService redisService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GroupProductService groupProductService;

    /**
     * defaults(4320 min) 3天
     */
    @Value("${redis.soft.group.product.expire:4320}")
    private Long redisSoftGroupProductExpire;

    @Override
    public Long countGroupProduct(GroupProductQuery query) {
        if (query == null) {
            return 0L;
        }
        return groupProductMapper.countGroupProduct(query);
    }

    @Override
    public Map<String, Object> listGroupProduct(GroupProductQuery query, boolean useCache) {
        if (useCache) {
            String hashKey = getHashKey(query);
            String key = RedisKeyConstant.HOUSE_DRAW_GROUP_SOFT_PRODUCT.toString();

            String value = redisService.getCacheOfHash(key, hashKey, redisSoftGroupProductExpire,
                    TimeUnit.MINUTES, () -> {
                        Map<String, Object> map = groupProductService.listGroupProduct(query);
                        try {
                            return objectMapper.writeValueAsString(map);
                        } catch (JsonProcessingException e) {
                            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR);
                        }
                    });

            // get mapper value
            if (!StringUtils.isEmpty(value)) {
                log.info("listGroupProduct get cache by hashKey => {}", hashKey);
                try {
                    return objectMapper.readValue(value, new TypeReference<Map<String, Object>>() {
                    });
                } catch (IOException e) {
                    throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR);
                }
            }
        }

        return this.listGroupProduct(query);
    }

    private String getHashKey(GroupProductQuery query) {
        StringBuilder buf = new StringBuilder(":listGroupProduct");
        if (query.getGroupType() != null) {
            buf.append(":").append(query.getGroupType());
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


    @Override
    public Map<String, Object> listGroupProduct(GroupProductQuery query) {
        Map<String, Object> map = new HashMap<>();
//        List<GroupProductBO> groupProducts = new ArrayList<>();
//
//        Long count = this.countGroupProduct(query);
//        if (count != null && count > 0) {
//            groupProducts = groupProductMapper.listGroupProduct(query);
//        }

        List<GroupProductBO> groupProducts = groupProductMapper.listGroupProduct(query);
        if (groupProducts != null && !groupProducts.isEmpty()) {
            // 图片、文件资源
            Map<String, Set<Long>> mapRes = handlerResource(groupProducts);
            Map<String, ResPic> resPicMap = resPicService.getResPicMap(mapRes.get("pics"));
            Map<String, ResFile> resFileMap = resFileService.getResFileMap(mapRes.get("files"));

            // 获取组合里的产品
            Map<Long, List<BaseProductBO>> proMap = getProductForMap(groupProducts);

            for (GroupProductBO bo : groupProducts) {
                // 图片
                ResPic resPic = resPicMap.get(Objects.toString(bo.getGroupPicId(), Utils._ONE_VALUE));
                if (resPic != null) {
                    bo.setGroupPicPath(resPic.getPicPath());
                }

                // 组合产品位置关系
                ResFile resFile = resFileMap.get(Objects.toString(bo.getConfigId(), Utils._ONE_VALUE));
                if (resFile != null) {
                    bo.setConfigFile(resFile.getFilePath());
                }

                // C# null 处理(Fuck C#)
                bo.setConfigId(bo.getConfigId() == null ? 0 : bo.getConfigId());
                bo.setGroupPicId(bo.getGroupPicId() == null ? 0 : bo.getGroupPicId());
                bo.setGroupType(bo.getGroupType() == null ? -1L : bo.getGroupType());

                // products
                bo.setProducts(proMap.get(bo.getGroupId()));
            }
        }

//        map.put("total", count);
        map.put("list", groupProducts);

        return map;
    }

    /**
     * 获取组合里的产品
     *
     * @param list
     * @return
     */
    private Map<Long, List<BaseProductBO>> getProductForMap(List<GroupProductBO> list) {
        Map<Long, List<BaseProductBO>> proMap = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return proMap;
        }

        List<BaseProductBO> products = groupProductMapper.listBaseProduct(list);
        if (products == null || products.isEmpty()) {
            log.warn("组合产品里的产品数据可能出现异常, groupProducts => {}", Arrays.toString(list.stream().map(GroupProductBO::getGroupId).toArray()));
            return proMap;
        }

        // 处理产品 model资源、分类信息
        this.handlerProduct(products);

        proMap = products.stream().collect(Collectors.groupingBy(BaseProductBO::getGroupId));
        return Optional.of(proMap).orElse(new HashMap<>());
    }

    /**
     * 处理产品 model资源、分类信息
     *
     * @param products
     */
    private void handlerProduct(List<BaseProductBO> products) {
        // 模型资源
        Map<String, ResModel> modelMap = getModelMap(products);
        // 大小分类
        Map<Long, ProductTypeBO> productTypes = baseProductService.listProductType(products);

        if (productTypes == null || productTypes.isEmpty()) {
            log.warn("产品的分类数据可能出现异常, products => {}", Arrays.toString(products.stream().map(BaseProductBO::getId).toArray()));
            return;
        }

        for (BaseProductBO p : products) {
            // 模型
            ResModel resModel = modelMap.get(Objects.toString(p.getModelId(), Utils._ONE_VALUE));
            if (resModel != null) {
                p.setModelName(resModel.getModelName());
                p.setModelPath(resModel.getModelPath());
            }

            // C# null 处理
            p.setModelId(p.getModelId() == null ? 0 : p.getModelId());

            // 大小分类
            baseProductService.buildProductType(productTypes.get(p.getId()), p);
        }
    }

    /**
     * 处理模型
     *
     * @param products
     * @return
     */
    private Map<String, ResModel> getModelMap(List<BaseProductBO> products) {
        Set<Long> models = new HashSet<>();
        if (products != null && !products.isEmpty()) {
            for (BaseProductBO p : products) {
                // 清除Null Object (Fuck NullPointerException)
                if (p.getModelId() != null && p.getModelId() > 0) {
                    models.add(p.getModelId());
                }
            }
        }

        return resModelService.getResModelMap(models);
    }

    /**
     * 处理图片、文件
     *
     * @param gps
     * @return
     */
    private Map<String, Set<Long>> handlerResource(List<GroupProductBO> gps) {
        Set<Long> pics = new HashSet<>();
        Set<Long> files = new HashSet<>();
        Map<String, Set<Long>> map = new HashMap<>();

        if (gps != null && !gps.isEmpty()) {
            for (GroupProductBO gp : gps) {
                // 清除Null Object (Fuck NullPointerException)
                if (gp.getGroupPicId() != null && gp.getGroupPicId() > 0) {
                    pics.add(gp.getGroupPicId());
                }


                if (gp.getConfigId() != null && gp.getConfigId() > 0) {
                    files.add(gp.getConfigId());
                }
            }
        }

        map.put("pics", pics);
        map.put("files", files);

        return map;
    }

    @Getter
    @AllArgsConstructor
    enum GroupType {
        SOFA("sofa", "沙发组"),
        TV_BENCH("TVbench", "电视柜"),
        DININGTABLE("diningTable", "餐桌组"),
        SIDEBOARD("sideboard", "餐柜组"),
        SHOECABINET("shoeCabinet", "鞋柜组"),
        CONSOLECABINET("consoleCabinet", "隔断柜"),
        ZHUANGSHI("zhuangshi", "装饰柜"),
        CHUANGZU("chuangzu", "床组合"),
        YUSHIGUI("yushigui", "浴室柜"),
        SHUZHUO("shuzhuo", "书桌组"),
        CHUANGTOUGUI("chuangtougui", "床头柜"),
        SHUZHUANG("shuzhuang", "梳妆台"),
        SHUGUI("shugui", "书柜组"),
        SHUYI("shuyi", "书椅组");

        final String key;
        final String name;
    }
}
