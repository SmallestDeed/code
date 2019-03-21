package com.sandu.search.controller.groupproduct;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sandu.common.LoginContext;
import com.sandu.search.common.constant.ProductTypeValue;
import com.sandu.search.common.tools.ExpireMapUtil;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.common.util.Utils;
import com.sandu.search.entity.elasticsearch.index.GroupProductIndexMappingData;
import com.sandu.search.entity.groupproduct.GroupProductSearchVo;
import com.sandu.search.entity.response.universal.UniversalSearchResultResponse;
import com.sandu.search.exception.GroupProductSearchException;
import com.sandu.search.service.groupproduct.GroupProductSearchService;
import com.sandu.search.storage.groupproduct.GroupProductRelMetaDataStorage;
import com.sandu.user.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/toc/groupProduct/replace")
public class ToCGroupProductReplaceController {

    private static final String CLASS_LOG_PREFIX = "C端及移动B端组合产品替换搜索服务";
    @Value("${app.fullsearchapi.url}")
    private String fullSearchUrl;
    private final Integer DEFAULT_LIMIT = 100;
    private Integer DEFAULT_START = 0;

    private final GroupProductRelMetaDataStorage groupProductRelMetaDataStorage;
    private final GroupProductSearchService groupProductSearchService;

    @Autowired
    public ToCGroupProductReplaceController(GroupProductRelMetaDataStorage groupProductRelMetaDataStorage,
                                            GroupProductSearchService groupProductSearchService) {
        this.groupProductRelMetaDataStorage = groupProductRelMetaDataStorage;
        this.groupProductSearchService = groupProductSearchService;
    }


    /**
     * 同城联盟和小程序的组合产品替换搜索逻辑
     *
     * @param productTypeValue         产品大类
     * @param productTypeSmallValue    产品小类
     * @param start                    数据起始数
     * @param dataSize                 单页数据条数
     * @param productCategoryId        产品分类ID
     * @param productCategoryLongCode  产品分类长编码
     * @param designPlanProductId      设计方案产品ID
     * @param productId                产品ID(用于新增柜类产品长度过滤)
     * @param designPlanType           设计方案类型(Desc: com.sandu.search.common.constant.DesignPlanType)
     * @param productBrandId           产品品牌ID
     * @param bizProductTypeSmallValue 产品小类筛选(只供同城联盟和小程序产品替换搜索通过小类筛选用)
     * @param aggsData
     * @return
     */
    @RequestMapping("/list")
    UniversalSearchResultResponse queryGroupProductReplaceByCondition(Integer productTypeValue,
                                                                      Integer productTypeSmallValue,
                                                                      Integer start,
                                                                      Integer dataSize,
                                                                      Integer productCategoryId,
                                                                      String productCategoryLongCode,
                                                                      Integer designPlanProductId,
                                                                      Integer productId,
                                                                      @RequestParam(required = false) Integer designPlanType,
                                                                      @RequestParam(required = false) Integer productBrandId,
                                                                      @RequestParam(required = false) Integer bizProductTypeSmallValue,
                                                                      @RequestParam(required = false, defaultValue = "N") String aggsData,
                                                                      HttpServletRequest request) {
        long startTime = System.currentTimeMillis();

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            return new UniversalSearchResultResponse(false, "请登录");
        }
        Integer userId = loginUser.getId();
        String key = (userId + "," + productId).hashCode() + "";
        Integer totalCount = 0;

        Map<String, Object> resultMap = new HashMap<>();
        List<GroupProductIndexMappingData> groupProductList = new ArrayList<>();

        // 如果内存map不为空,则从内存map中获取够分页的组合产品数据
        String jsonList = ExpireMapUtil.get(key);
        if (StringUtils.isNotBlank(jsonList)) {
            List<GroupProductIndexMappingData> dataList = JsonUtil.fromJson(jsonList, new TypeToken<List<GroupProductIndexMappingData>>() {
            }.getType());
            if (null != dataList) {
                if ((start + dataSize) <= dataList.size()) {
                    totalCount = dataList.size();
                    groupProductList = dataList.subList(start, start + dataSize);
                    resultMap.put("obj", groupProductList);
                    return new UniversalSearchResultResponse(true, "ok", totalCount, resultMap);
                } else {
                    DEFAULT_START = totalCount;
                }
            }
        }


        String token = request.getHeader("Authorization");
        String platformCode = request.getHeader("Platform-Code");
        String referer = request.getHeader("Referer");
        String customReferer = request.getHeader("Custom-Referer");

        String hostUrl = fullSearchUrl + "/universal/product/replace/list?onlyMainProduct=" + ProductTypeValue.IS_MAIN_PRODUCT;
        StringBuffer sb = new StringBuffer(hostUrl);

        sb.append("&start=" + DEFAULT_START);
        sb.append("&dataSize=" + DEFAULT_LIMIT);

        if (null != productTypeValue) {
            sb.append("&productTypeValue=" + productTypeValue);
        }
        if (null != productTypeSmallValue) {
            sb.append("&productTypeSmallValue=" + productTypeSmallValue);
        }
        if (null != productCategoryId) {
            sb.append("&productCategoryId=" + productCategoryId);
        }
        if (null != productCategoryLongCode) {
            sb.append("&productCategoryLongCode=" + productCategoryLongCode);
        }
        if (null != designPlanProductId) {
            sb.append("&designPlanProductId=" + designPlanProductId);
        }
        if (null != productId) {
            sb.append("&productId=" + productId);
        }
        if (null != designPlanType) {
            sb.append("&designPlanType=" + designPlanType);
        }
        if (null != productBrandId) {
            sb.append("&productBrandId=" + productBrandId);
        }
        if (null != bizProductTypeSmallValue) {
            sb.append("&bizProductTypeSmallValue=" + bizProductTypeSmallValue);
        }
        //组合产品不需要聚合分类，所以写死为N
        sb.append("&aggsData=" + "N");

        String url = sb.toString();
        log.info(CLASS_LOG_PREFIX + "搜索产品列表url：{}", url);

        // 获取可替换的主产品列表
        String jsonResult = Utils.doGetMethod(url, token, platformCode, referer, customReferer);
        log.info(CLASS_LOG_PREFIX + "搜索产品列表完成，jsonResult={}", jsonResult);
        if (StringUtils.isBlank(jsonResult)) {
            return new UniversalSearchResultResponse(false, "搜索产品列表失败");
        }
        log.info(CLASS_LOG_PREFIX + "搜索产品列表完成，共耗时：{}ms", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        UniversalSearchResultResponse searchResult = JsonUtil.fromJson(jsonResult, UniversalSearchResultResponse.class);
        log.info(CLASS_LOG_PREFIX + "搜索产品列表完成，searchResult={}", searchResult);

        if (null != searchResult) {
            if (!searchResult.isSuccess()) {
                return new UniversalSearchResultResponse(false, searchResult.getMessage());
            }

            Map datalist = (Map) searchResult.getDatalist();
            if (null == datalist || 0 >= datalist.size()) {
                return new UniversalSearchResultResponse(false, "获取可替换的主产品清单list为空");
            }

            List<LinkedTreeMap<String, Object>> objList = (List<LinkedTreeMap<String, Object>>) datalist.get("obj");
            if (null == objList || 0 >= objList.size()) {
                return new UniversalSearchResultResponse(false, "获取可替换的主产品清单obj为空");
            }
            log.info(CLASS_LOG_PREFIX + "搜索产品列表完成，可替换的主产品总数：size={}", objList.size());


            for (LinkedTreeMap<String, Object> linkedTreeMap : objList) {
                Double id = (Double) linkedTreeMap.get("id");
                Integer mainProductId = id.intValue();
                if (null == mainProductId || 0 >= mainProductId) {
                    continue;
                }

                // 根据主产品id获取该主产品的所有组合产品id集合
                List<Integer> groupIdList = groupProductRelMetaDataStorage.getGroupIdListByMainProductId(mainProductId);
                if (null == groupIdList || 0 >= groupIdList.size()) {
                    return new UniversalSearchResultResponse(false, "找不到可替换的组合");
                }
                GroupProductSearchVo groupProductSearchVo = new GroupProductSearchVo();
                // groupProductSearchVo.setAllotState(1);
                // groupProductSearchVo.setPutawayState(1);
                // groupProductSearchVo.setPlatformCode(platformCode);
                groupProductSearchVo.setGroupType(0);

                GroupProductIndexMappingData groupProductIndexMappingData;
                for (Integer groupId : groupIdList) {
                    groupProductSearchVo.setGroupId(groupId);
                    try {
                        //通过组合产品id集合检索组合产品信息
                        groupProductIndexMappingData = groupProductSearchService.searchGroupProduct(groupProductSearchVo);
                    } catch (GroupProductSearchException e) {
                        log.error(CLASS_LOG_PREFIX + "通过组合id搜索组合异常：{}", e);
                        continue;
                    }
                    if (null != groupProductIndexMappingData) {
                        groupProductList.add(groupProductIndexMappingData);
                    }
                }
            }
            log.info(CLASS_LOG_PREFIX + "获取组合产品信息完成，组合产品最终总个数：size={}", groupProductList.size());
            if (0 >= groupProductList.size()) {
                log.info(CLASS_LOG_PREFIX + "获取组合产品信息完成，共耗时：{}ms", System.currentTimeMillis() - startTime);
                return new UniversalSearchResultResponse(false,"找不到可替换的组合");
            }
            //数据存入内存map，便于下次分页
            if (ExpireMapUtil.containsKey(key)) {
                jsonList = ExpireMapUtil.get(key);
                List<GroupProductIndexMappingData> dataList = JsonUtil.fromJson(jsonList, new TypeToken<List<GroupProductIndexMappingData>>() {
                }.getType());
                groupProductList.addAll(dataList);
            }
            ExpireMapUtil.put(key, JsonUtil.toJson(groupProductList));

            totalCount = groupProductList.size();

            List<GroupProductIndexMappingData> resultGroupProductList = groupProductList.subList(start, start + dataSize);

            resultMap.put("obj", resultGroupProductList);
            // resultMap.put("aggs",datalist.get("aggs"));
            log.info(CLASS_LOG_PREFIX + "获取组合产品信息完成，共耗时：{}ms", System.currentTimeMillis() - startTime);

            return new UniversalSearchResultResponse(true, "ok", totalCount, resultMap);

        }
        return new UniversalSearchResultResponse(false, "主产品列表数据解析后为空");
    }

}
