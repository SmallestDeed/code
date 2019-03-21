package com.sandu.product.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.RedisService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.GsonUtil;
import com.sandu.common.util.RequestUtils;
import com.sandu.common.util.Utils;
import com.sandu.goods.output.CompanyIntroduceVO;
import com.sandu.product.dao.BaseCompanyMapper;
import com.sandu.product.model.AuthorizedConfig;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.search.BaseCompanySearch;
import com.sandu.product.service.AuthorizedConfigService;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @Title: BaseCompanyServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品模块-企业表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:45
 */
@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[产品模块-企业服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseCompanyServiceImpl.class);
    @Autowired
    private BaseCompanyMapper baseCompanyMapper;
    @Autowired
    private AuthorizedConfigService authorizedConfigService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private RedisService redisService;


    /**
     * 新增数据
     *
     * @param baseCompany
     * @return int
     */
    @Override
    public int add(BaseCompany baseCompany) {
        baseCompanyMapper.insertSelective(baseCompany);
        return baseCompany.getId();
    }

    /**
     * 更新数据
     *
     * @param baseCompany
     * @return int
     */
    @Override
    public int update(BaseCompany baseCompany) {
        return baseCompanyMapper
                .updateByPrimaryKeySelective(baseCompany);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return baseCompanyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseCompany
     */
    @Override
    public BaseCompany get(Integer id) {
        return baseCompanyMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param baseCompany
     * @return List<BaseCompany>
     */
    @Override
    public List<BaseCompany> getList(BaseCompany baseCompany) {
        return baseCompanyMapper.selectList(baseCompany);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(BaseCompanySearch baseCompanySearch) {
        return baseCompanyMapper.selectCount(baseCompanySearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<BaseCompany>
     */
    @Override
    public List<BaseCompany> getPaginatedList(
            BaseCompanySearch baseCompanySearch) {
        return baseCompanyMapper.selectPaginatedList(baseCompanySearch);
    }

    /**
     * 获得用户类型:(序列号->公司->小类)
     *
     * @return
     */
    @Override
    public Set<String> getTypeSetByUserId(Integer userId) {
        Set<String> set = new HashSet<String>();
        List<AuthorizedConfig> authorizedConfigList = authorizedConfigService.findAllByUserId(userId);
        for (AuthorizedConfig authorizedConfig : authorizedConfigList) {
            if (StringUtils.isBlank(authorizedConfig.getCompanyId())) {
                continue;
            }
            List<String> strList = Utils.getListFromStr(authorizedConfig.getCompanyId());
            for (String str : strList) {
                BaseCompany baseCompany = get(Integer.valueOf(str));
                if (baseCompany != null && StringUtils.isNotBlank(baseCompany.getSmallType())) {
                    String smallType = baseCompany.getSmallType();
                    List<String> smallTypeList = Utils.getListFromStr(smallType);
                    for (String smallTypeStr : smallTypeList) {
                        SysDictionary sysDictionaryType = null;
                        if (baseCompany.getIndustry() != null && baseCompany.getIndustry() > 0) {
                            sysDictionaryType = sysDictionaryService.findOneByTypeAndValue("industry", baseCompany.getIndustry());
                        }
                        if (sysDictionaryType != null) {
                            SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByTypeAndValue(sysDictionaryType.getValuekey(), Integer.parseInt(smallTypeStr));
                            if (sysDictionarySmallType != null) {
                                set.add(sysDictionarySmallType.getValuekey());
                            }
                        }
                    }
                }
            }
        }
        return set;
    }

	@Override
	public BaseCompany getCompanyByDomainName(String companyDomainName) {
		//根据域名从缓存获取相关数据，如果查不到，则从数据库里面查询，查到则重新放到缓存里面。
        String companyJson = redisService.getMap(RedisKeyConstans.COMPANY_DOMAIN_MAP, companyDomainName);
        BaseCompany baseCompany = null;
        if(StringUtils.isNotEmpty(companyJson)){
        	 baseCompany = GsonUtil.json2Bean(companyJson, BaseCompany.class);
        }else {
        	 BaseCompany queryBaseCompany = new BaseCompany();
        	 queryBaseCompany.setCompanyDomainName(companyDomainName);
        	 queryBaseCompany.setIsDeleted(0);
            logger.info(CLASS_LOG_PREFIX + "根据域名从缓存获取相关数据:CompanyDomainName:{}", companyDomainName);
             List<BaseCompany> baseCompanyList = this.getList(queryBaseCompany);
             logger.info(CLASS_LOG_PREFIX + "根据域名从缓存获取相关数据完成:CompanyDomainName:{},baseCompanyList:{}", companyDomainName,  null == baseCompanyList ? null : GSON.toJson(baseCompanyList));

             if (baseCompanyList != null && baseCompanyList.size() > 0) {
                 if (baseCompanyList.size() > 1) {
                     logger.error(CLASS_LOG_PREFIX + "根据域名从缓存获取相关数据,获得数据超过1条，请重新配置域名信息。:companyDomainName:{},baseCompanyList:{}", companyDomainName, GSON.toJson(baseCompanyList));
                 }
            	 baseCompany = baseCompanyList.get(0);
            	 redisService.addMap(RedisKeyConstans.COMPANY_DOMAIN_MAP, companyDomainName , GsonUtil.bean2Json(baseCompany));
             }
        }
        return baseCompany;
	}

    @Override
    public BaseCompany getCompanyByConpanyAppId(String conpanyAppId)  {
        //根据域名从缓存获取相关数据，如果查不到，则从数据库里面查询，查到则重新放到缓存里面。
        String companyJson = redisService.getMap(RedisKeyConstans.COMPANY_APP_ID , conpanyAppId);
        BaseCompany baseCompany = null;
        if(StringUtils.isNotEmpty(companyJson)){
            baseCompany = GsonUtil.json2Bean(companyJson, BaseCompany.class);
        }else {
            BaseCompany queryBaseCompany = new BaseCompany();
            queryBaseCompany.setAppId(conpanyAppId);
            queryBaseCompany.setIsDeleted(0);
            logger.info(CLASS_LOG_PREFIX + "根据域名从缓存获取相关数据:conpanyAppId:{}", conpanyAppId);
            List<BaseCompany> baseCompanyList = baseCompanyMapper.selectListByCompanyAPPId(queryBaseCompany);
            logger.info(CLASS_LOG_PREFIX + "根据域名从缓存获取相关数据完成:conpanyAppId:{},baseCompanyList:{}", conpanyAppId,  null == baseCompanyList ? null : GSON.toJson(baseCompanyList));

            if (baseCompanyList != null && baseCompanyList.size() > 0) {
                if (baseCompanyList.size() > 1) {
                    logger.error(CLASS_LOG_PREFIX + "根据域名从缓存获取相关数据,获得数据超过1条，请重新配置域名信息。:companyDomainName:{},baseCompanyList:{}", conpanyAppId, GSON.toJson(baseCompanyList));
                }
                baseCompany = baseCompanyList.get(0);
                redisService.addMap(RedisKeyConstans.COMPANY_APP_ID , conpanyAppId , GsonUtil.bean2Json(baseCompany));
            }
        }
        return baseCompany;
    }

    @Override
    public BaseCompany getCompanyByDomainUrl(String domainUrl) {


        BaseCompany company = null;
        if(StringUtils.isNotBlank(domainUrl)) {
            if(domainUrl.contains("servicewechat")) {
                //Mini program
                String appId = domainUrl.substring(RequestUtils.getCharacterPosition(domainUrl,3)+1,RequestUtils.getCharacterPosition(domainUrl,4));
                company = getCompanyByConpanyAppId(appId);
            }else{
                String domainName = domainUrl.substring(domainUrl.indexOf("//")+2,domainUrl.indexOf("."));
                company = getCompanyByDomainName(domainName);
            }

        }
        return company;
    }

    @Override
    public ResponseEnvelope validateCompany(Integer companyId, Integer brandId) {
        logger.debug("Process in BaseCompanyServiceImpl.validateCompany method parameter companyId:{},branId:{}",companyId, brandId);
        ResponseEnvelope response = new ResponseEnvelope(true,"true");
        if(null == companyId || null == brandId){
            logger.error("brandId or companyId is null");
            return new ResponseEnvelope(false,"请求参数异常");
        }

        BaseCompany company = baseCompanyMapper.getCompanyByBrandId(brandId);
        if(null == company){
            logger.error("brandId:{}, not found company data",brandId);
            return new ResponseEnvelope(false,"品牌有且只能属于一个公司");
        }
        if(StringUtils.isBlank(company.getCompanyDomainName())||company.getCompanyDomainName().equals("")) {
        	return new ResponseEnvelope(false,"该公司没有品牌网站", null);
        }
        company.setCompanyUrl(company.getCompanyDomainName());
        if(companyId.intValue() != company.getId().intValue()){
            logger.error("brandId：{} not belong to companyId:{}", brandId, companyId);
            return new ResponseEnvelope(false,"该品牌不属于该公司", company);
        }

        return response;
    }

    @Override
    public BaseCompany getCompanyByBrandId(Integer brandId) {
        return baseCompanyMapper.getCompanyByBrandId(brandId);
    }

    @Override
    public CompanyIntroduceVO getIntroduce(Integer id)
    {
        return baseCompanyMapper.getIntroduce(id);
    }


}
