package com.sandu.product.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.RedisService;
import com.sandu.product.dao.CompanyUnionMapper;
import com.sandu.product.service.CompanyUnionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sandu.cache.RedisKeyConstans.UNION_BRAND_MAP;
import static com.sandu.cache.RedisKeyConstans.UNION_COMPANY_MAP;

/**
 * @version V1.0
 * @Title: CompanyUnionServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品模块-异业联盟表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:45
 */
@Service("companyUnionService")
public class CompanyUnionServiceImpl implements CompanyUnionService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[产品模块-异业联盟服务]:";
    private final static Logger logger = LoggerFactory.getLogger(CompanyUnionServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private CompanyUnionMapper companyUnionMapper;


    /**
     * 根据企业id获取和它同联盟的所有企业id
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Integer> getAllCompanyId(Integer companyId) {

        if (null == companyId) {
            logger.warn(CLASS_LOG_PREFIX + "根据企业id获取和它联盟的所有企业id->公司ID为空!");
            return null;
        }
        //获取企业id Json数据
        String companyIdsJson = redisService.getMap(UNION_COMPANY_MAP, (companyId + ""));

        if (StringUtils.isNotEmpty(companyIdsJson)) {
            return GSON.fromJson(companyIdsJson, new TypeToken<List<Integer>>() {
            }.getType());
        }

        List<Integer> companyIds = companyUnionMapper.selectAllCompanyId(companyId);

        if (companyIds != null && companyIds.size() > 0) {
            redisService.addMap(UNION_COMPANY_MAP, (companyId + ""), GSON.toJson(companyIds));
        }

        return companyIds;
    }

    /**
     * 根据企业id获取和它联盟的所有企业下的品牌id
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Integer> getAllBrandIds(Integer companyId) {
        if (null == companyId) {
            logger.warn(CLASS_LOG_PREFIX + "根据企业id获取和它联盟的所有企业下的品牌id->公司ID为空!");
            return null;
        }
        //获取品牌Json数据
        String brandIdsJson = redisService.getMap(UNION_BRAND_MAP, (companyId + ""));

        if (StringUtils.isNotEmpty(brandIdsJson)) {
            return GSON.fromJson(brandIdsJson, new TypeToken<List<Integer>>() {
            }.getType());
        }

        List<Integer> brandIds = companyUnionMapper.selectAllBrandId(companyId);

        if (brandIds != null && brandIds.size() > 0) {
            redisService.addMap(UNION_BRAND_MAP, (companyId + ""), GSON.toJson(brandIds));
        }

        return brandIds;
    }


}
