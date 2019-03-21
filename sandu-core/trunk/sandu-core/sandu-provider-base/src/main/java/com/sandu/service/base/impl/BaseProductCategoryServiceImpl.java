package com.sandu.service.base.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.model.BaseProductCategorySearch;
import com.sandu.api.base.model.BaseProductCategory;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.base.service.BaseProductCategoryService;
import com.sandu.api.base.service.RedisService;
import com.sandu.service.base.dao.BaseProductCategoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/19
 * @since : sandu_yun_1.0
 */
@Service("baseProductCategoryService")
public class BaseProductCategoryServiceImpl implements BaseProductCategoryService {

    private static final String NEW_PRODUCT_CATEGORY_CACHE_MAP_NAME = "NEW_PRODUCT_CATEGORY_CACHE_MAP_NAME";

    private static final String NEW_PRODUCT_CATEGORY_FILTER_CACHE_MAP_NAME = "NEW_PRODUCT_CATEGORY_FILTER_CACHE_MAP_NAME";

    private static final Gson gson = new Gson();

    @Autowired
    private RedisService redisService;

    @Autowired
    private BaseProductCategoryMapper baseProductCategoryMapper;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Override
    public List<BaseProductCategory> getBaseProductCategoryList(BaseProductCategorySearch search) {
        String appId = getCompanyIdFromMiniProgramUrl(search.getReferStr());
        BaseCompany company = baseCompanyService.getCompanyByConpanyAppId(appId);
        int currentLevel = search.getCurrentLevel()!=null ?search.getCurrentLevel().intValue():0;
        Integer companyId = company.getId();
        int level = currentLevel + 1;
        Integer currentId = search.getCurrentId();
        Integer pid = currentId;
        if(level == 1) {
            pid = Integer.valueOf(1);
        }
        search.setPId(Integer.valueOf(pid));
        search.setLevel(Integer.valueOf(level));
        search.setCompanyId(companyId);
        //CACHE KEY= companyId +level + pid
        String cacheKey = companyId + "_" +level +"_" +pid; ;

        List<BaseProductCategory> result = new ArrayList <>();
        String cacheResultStr = redisService.getMap(NEW_PRODUCT_CATEGORY_CACHE_MAP_NAME, cacheKey);
        if(StringUtils.isNotEmpty(cacheResultStr)) {
            result = gson.fromJson(cacheResultStr, new TypeToken<List<BaseProductCategory>>(){}.getType());
        }else{
            result = baseProductCategoryMapper.getBaseProductCategoryList(search);
            // if the level is 2, then auto get the default select category for the second.
            if(level == 2) {
                result = sortCategoryList(result,companyId);
                BaseProductCategorySearch defaultSelectCategorySearch = new BaseProductCategorySearch();
                defaultSelectCategorySearch.setCompanyId(search.getCompanyId());
                BaseProductCategory defaultSelectCategory = result.get(0);
                defaultSelectCategorySearch.setPId(defaultSelectCategory.getId());
                defaultSelectCategorySearch.setLevel(Integer.valueOf(3));
                List<BaseProductCategory> defaultSelectResult = baseProductCategoryMapper.getBaseProductCategoryList(defaultSelectCategorySearch);
                defaultSelectCategory.setDefaultSelectCategories(defaultSelectResult);
            }
            String resultStr = gson.toJson(result,new TypeToken<List<BaseProductCategory>>(){}.getType());
            redisService.addMap(NEW_PRODUCT_CATEGORY_CACHE_MAP_NAME,cacheKey,resultStr);
        }

        return result;
    }

    private List<BaseProductCategory> sortCategoryList(List<BaseProductCategory> result, Integer companyId) {
        //查询公司的主营分类
        String allCompanyMainCategory = baseProductCategoryMapper.selectAllCompanyMainCategory(companyId);
        if(StringUtils.isNotBlank(allCompanyMainCategory) && result != null) {
            List <String> companyMainCategoryList = getListFromStr(allCompanyMainCategory);
            for (String companyMainCategory : companyMainCategoryList) {
                for (BaseProductCategory proCategoryPo : result) {
                    if (proCategoryPo.getId().intValue() == Integer.valueOf(companyMainCategory).intValue()) {
                        proCategoryPo.setCompanyMainCategory(Integer.valueOf(companyMainCategory));
                    }
                }
            }
            //根据公司主营分类对二级分类进行排序
            Collections.sort(result, new Comparator <BaseProductCategory>() {
                @Override
                public int compare(BaseProductCategory o1, BaseProductCategory o2) {
                    if (o1.getCompanyMainCategory() == null && o2.getCompanyMainCategory() == null) {
                        return 0;
                    }

                    if (o1.getCompanyMainCategory() != null && o2.getCompanyMainCategory() == null) {
                        return -1;
                    }

                    if (o1.getCompanyMainCategory() == null && o2.getCompanyMainCategory() != null) {
                        return 1;
                    }

                    if (o1.getCompanyMainCategory() != null && o2.getCompanyMainCategory() != null) {
                        int i1 = companyMainCategoryList.indexOf(o1.getCompanyMainCategory().toString());
                        int i2 = companyMainCategoryList.indexOf(o2.getCompanyMainCategory().toString());
                        return i1 - i2;
                    }
                    return 0;
                }
            });
        }
        return result;
    }
    @Override
    public List <List <BaseProductCategory>> getBaseProductCategoryFilterList(BaseProductCategorySearch search) {
        String appId = getCompanyIdFromMiniProgramUrl(search.getReferStr());
        BaseCompany company = baseCompanyService.getCompanyByConpanyAppId(appId);
        int currentLevel = search.getCurrentLevel()!=null ?search.getCurrentLevel().intValue():3;
        Integer companyId = company.getId();
        int level = currentLevel + 1;
        Integer currentId = search.getCurrentId();
        Integer pid = currentId;
        search.setLevel(Integer.valueOf(level));
        search.setPId(pid);

        //CACHE KEY= companyId +level + pid
        String cacheKey = companyId + "_4_5_" +pid;
        List<List <BaseProductCategory>>  result = new ArrayList <>();
        String cacheResultStr = redisService.getMap(NEW_PRODUCT_CATEGORY_FILTER_CACHE_MAP_NAME, cacheKey);
        if(StringUtils.isNotEmpty(cacheResultStr)){
            result = gson.fromJson(cacheResultStr, new TypeToken<List<List <BaseProductCategory>>>(){}.getType());
        }else{
            List<Integer> fourthCategoryIds = new ArrayList <>();
            List <BaseProductCategory> fourthLevelCategory = baseProductCategoryMapper.getBaseProductCategoryList(search);
            for(Integer fourthCategoryId : fourthCategoryIds) {
                fourthCategoryIds.add(fourthCategoryId);
            }
            BaseProductCategorySearch fifthSearch = new BaseProductCategorySearch();
            fifthSearch.setPIds(fourthCategoryIds);
            fifthSearch.setLevel(Integer.valueOf(5));
            fifthSearch.setCompanyId(search.getCompanyId());
            List<BaseProductCategory> fifthLevelCategory = baseProductCategoryMapper.getBaseProductCategoryList(fifthSearch);

            result.add(fourthLevelCategory);
            result.add(fifthLevelCategory);
            String resultStr = gson.toJson(result,new TypeToken<List<List <BaseProductCategory>>>(){}.getType());
            redisService.addMap(NEW_PRODUCT_CATEGORY_FILTER_CACHE_MAP_NAME, cacheKey, resultStr);
        }

        return result;
    }

    @Override
    public void refreshCache() {
        redisService.del(NEW_PRODUCT_CATEGORY_CACHE_MAP_NAME);
        redisService.del(NEW_PRODUCT_CATEGORY_FILTER_CACHE_MAP_NAME);
    }


    private  List<String> getListFromStr(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        if (str.startsWith(",")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        String[] strs = str.split(",");
        list = Arrays.asList(strs);
        return list;
    }

    private String getCompanyIdFromMiniProgramUrl(String referer) {
        if (null != referer && !"".equals(referer) && (
                referer.startsWith("http://servicewechat.com/")
                        || referer.startsWith("https://servicewechat.com/")
        )) {
            //去掉前缀
            referer = referer.substring(referer.indexOf("//servicewechat.com/") + 20, referer.length());
            //去掉后缀
            referer = referer.substring(0, referer.indexOf("/"));
        }
        return referer;
    }


}
