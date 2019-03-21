package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.model.*;
import com.nork.design.service.*;
import com.nork.product.model.vo.ProductCeilingVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.AppProperties;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.dao.ProductCategoryRelMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.ProductModelConstant;
import com.nork.product.model.ProductSearchInfoModel;
import com.nork.product.model.ProductTypeConstant;
import com.nork.product.model.SpecialProductTypeInfo;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.CompanyUnionService;
import com.nork.product.service.PrepProductSearchInfoService;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.system.model.BasePlatform;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.BasePlatformService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @version V1.0
 * @Title: ProductCategoryRelServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品与产品类目关联ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Service("productCategoryRelService")
@Transactional
public class ProductCategoryRelServiceImpl implements ProductCategoryRelService {

    /*private static Logger logger = Logger.getLogger(ProductCategoryRelServiceImpl.class);*/
	private static final Logger logger = LoggerFactory.getLogger(ProductCategoryRelServiceImpl.class);
	
    @Autowired
    private ProductCategoryRelMapper productCategoryRelMapper;

    @Autowired
    private AuthorizedConfigService authorizedConfigService;

    @Autowired
    private BaseProductService baseProductService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private DesignPlanProductService designPlanProductService;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private DesignTempletService designTempletService;

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private PrepProductSearchInfoService prepProductSearchInfoService;

    @Autowired
    private BaseBrandService baseBrandService;

    @Autowired
    private ResModelService resModelService;

    @Autowired
    private ResTextureService resTextureService;

    @Autowired
    private ResPicService resPicService;
    @Autowired
    private CompanyUnionService companyUnionService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private DesignPlanService designPlanService;


    public static Map<String, Object> showMoreSmallTypeMap = getshowMoreSmallTypeMap();

    public static Map<String, List<String>> specialProductTypeMap = getSpecialProductTypeMap();

    public static String logPrefix = "customLog:";
    
    /**
     * 解析配置special.productType
     *
     * @return
     * @author huangsongbo
     */
    private static Map<String, List<String>> getSpecialProductTypeMap() {
        Map<String, List<String>> returnMap = new HashMap<String, List<String>>();

        String specialProductTypeStr = Utils.getValueByFileKey(AppProperties.APP, AppProperties.SPECIAL_PRODUCTTYPE_FILEKEY, "[{\"productTypeKey\":\"dim\",\"productSmallTypeKeys\":\"dic,bodx,tijx\"},{\"productTypeKey\":\"meng\",\"productSmallTypeKeys\":\"wosm,yusm,yangtm,weisjm,chufm,fangjm,ruhm,mens,ment,fangjms,fangjmt,fangjmbs\"},{\"productTypeKey\":\"pe\",\"productSmallTypeKeys\":\"arpe,otpe,crpe,pepe,pipe,mipe\"},{\"productTypeKey\":\"ho\",\"productSmallTypeKeys\":\"caho,cuho,boho\"},{\"productTypeKey\":\"el\",\"productSmallTypeKeys\":\"coel,hmel,wael,arel,frel,tvel\"},{\"productTypeKey\":\"li\",\"productSmallTypeKeys\":\"tali,ldli,bdli\"},{\"productTypeKey\":\"kiel\",\"productSmallTypeKeys\":\"hese,diki,ovki,miki,stki,laki,coki\"},{\"productTypeKey\":\"ki\",\"productSmallTypeKeys\":\"agki,juki,soki,elki,riki,meki,dgki,acki,cuki,haki,daki,drki,buki,deki\"},{\"productTypeKey\":\"ba\",\"productSmallTypeKeys\":\"dyba,ltba,mpba,btba,edba,meba,coba,sqba,haba,acba,yuba,asba,baba,toba,miba,ahba,caba\"},{\"productTypeKey\":\"qiangm\",\"productSmallTypeKeys\":\"mengk,chuangk,dians,shaf,cant,chuangt,xingx,beijing,yaox,zuh\"},{\"productTypeKey\":\"kp\",\"productSmallTypeKeys\":\"cskp,hokp,crkp,ctkp,chkp,fpkp,rskp,sskp,kfkp,sikp\"},{\"productTypeKey\":\"bp\",\"productSmallTypeKeys\":\"wbbp,fwbp,tbbp,chbp,ptbp,hobp,trbp,twbp,smbp,tfbp,tcbp\"},{\"productTypeKey\":\"ca\",\"productSmallTypeKeys\":\"tvca,scca\"},{\"productTypeKey\":\"ta\",\"productSmallTypeKeys\":\"cota\"},{\"productTypeKey\":\"cuki\",\"productSmallTypeKeys\":\"cmki,ccki,cski,cxki,czki,cqkii,ctki,ctski,ctjki,ctyki,ctzki\"},{\"productTypeKey\":\"dgki\",\"productSmallTypeKeys\":\"dmki,dfki,dyki,dzki,dqki\"}]");
        JSONArray jsonArray = JSONArray.fromObject(specialProductTypeStr);
        @SuppressWarnings({"deprecation", "unchecked"})
        List<SpecialProductTypeInfo> specialProductTypeInfoList = JSONArray.toList(jsonArray, SpecialProductTypeInfo.class);
        if (Lists.isEmpty(specialProductTypeInfoList)) {
            return null;
        }
        for (SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList) {
            returnMap.put(specialProductTypeInfo.getProductTypeKey(), Utils.getListFromStr(specialProductTypeInfo.getProductSmallTypeKeys()));
        }
        return returnMap;
    }

    /**
     * 新增数据
     *
     * @param productCategoryRel
     * @return int
     */
    @Override
    public int add(ProductCategoryRel productCategoryRel) {
        productCategoryRelMapper.insertSelective(productCategoryRel);
        return productCategoryRel.getId();
    }

    /**
     * 更新数据
     *
     * @param productCategoryRel
     * @return int
     */
    @Override
    public int update(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.updateByPrimaryKeySelective(productCategoryRel);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return productCategoryRelMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return ProductCategoryRel
     */
    @Override
    public ProductCategoryRel get(Integer id) {
        return productCategoryRelMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param productCategoryRel
     * @return List<ProductCategoryRel>
     */
    @Override
    public List<ProductCategoryRel> getList(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.selectList(productCategoryRel);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    public int getCount(ProductCategoryRelSearch productCategoryRelSearch) {
        return productCategoryRelMapper.selectCount(productCategoryRelSearch);
    }

    /**
     * 分页获取数据
     *
     * @return List<ProductCategoryRel>
     */
    @Override
    public List<ProductCategoryRel> getPaginatedList(ProductCategoryRelSearch productCategoryRelSearch) {
        return productCategoryRelMapper.selectPaginatedList(productCategoryRelSearch);
    }

    /**
     * 其他
     *
     */

    /**
     * 根据产品编号和类目编号查询
     *
     * @return
     */
    @Override
    public ProductCategoryRel findByPidAndCid(Map<String, Object> paramsMap) {
        return productCategoryRelMapper.findByPidAndCid(paramsMap);
    }

    /**
     * 根据产品ID删除关联信息
     *
     * @return
     */
    @Override
    public int deletedByProductId(Integer productId) {
        return productCategoryRelMapper.deletedByProductId(productId);
    }

    /**
     * 根据分类编号查询产品
     *
     * @param longCode
     * @return
     */
    @Override
    public List<ProductCategoryRel> findCategoryProductIds(String longCode) {
        return productCategoryRelMapper.findCategoryProductIds(longCode);
    }

    /**
     * 根据分类code和商品名称查询
     *
     * @param productCategoryRel
     * @return
     */
    @Override
    public List<CategoryProductResult> findProductByCategoryCode(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findProductByCategoryCode(productCategoryRel);
    }

    /**
     * 根据分类code和商品名称查询汇总
     *
     * @param productCategoryRel
     * @return
     */
    @Override
    public int findProductByCategoryCodeCount(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findProductByCategoryCodeCount(productCategoryRel);
    }

    @Override
    public int deleteAllByProductId(Integer id) {
        return productCategoryRelMapper.deleteAllByProductId(id);
    }

    @Override
    public List<CategoryProductResult> findCategoryProductResultByLongCode(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findCategoryProductResultByLongCode(productCategoryRel);
    }

    @Override
    public int findCategoryProductResultByLongCodeCount(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findCategoryProductResultByLongCodeCount(productCategoryRel);
    }

    @Override
    public int getProCategoryListCountByCode(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.getProCategoryListCountByCode(productCategoryRel);
    }

    @Override
    public List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.getProCategoryListByCode(productCategoryRel);
    }

    @Override
    public List<CategoryProductResult> findCategoryProductResult(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findCategoryProductResult(productCategoryRel);
    }

    /**
     * 按照序列号过滤list
     *
     * @param list
     * @param userId
     * @return
     * @author huangsongbo
     */
    public List<CategoryProductResult> filterByAuthorizedConfig(List<CategoryProductResult> list, Integer userId,
                                                                String terminalImei) {
        //Map<Integer, List<AuthorizedConfig>> authorizedConfigsMap = authorizedConfigService.getEffectualauthorizedConfigs(userId, terminalImei);
        /*需求改为序列号设备共用*/
        Map<Integer, List<AuthorizedConfig>> authorizedConfigsMap = authorizedConfigService.getEffectualauthorizedConfigs(userId, null);
		/* 检测list是否存在该品牌的产品 */
        boolean judge = isAliveBrandId(list, authorizedConfigsMap);
		/* 检测list是否存在该品牌的产品end */
        if (judge) {
			/* 过滤 */
            for (int i = 0; i < list.size(); i++) {
                boolean isRemove = isRemove(list.get(i), authorizedConfigsMap);
                if (isRemove) {
                    list.remove(i);
                    i--;
                }
            }
			/* 过滤end */
        }
        return list;
    }

    public List<CategoryProductResult> filterByAuthorizedConfig2(List<CategoryProductResult> list, Integer userId,
                                                                 String terminalImei) {
        Map<String, List<AuthorizedConfig>> authorizedConfigsMap = authorizedConfigService.getEffectualauthorizedConfigs2(userId, terminalImei);
        if (authorizedConfigsMap.isEmpty()) {
            return list;
        }
		/* 检测list中数据的brandId有没有存在于mapKey=all的序列号的brandIds中->有->只要该brand的产品 */
        Map<Integer, List<Integer>> brandIdAndProductIdsMap = new HashMap<Integer, List<Integer>>();
        if (authorizedConfigsMap.containsKey("all")) {
            for (AuthorizedConfig authorizedConfig : authorizedConfigsMap.get("all")) {
                for (String str : authorizedConfig.getBrandIds().split(",")) {
                    if (StringUtils.isNotEmpty(str)) {
                        if (brandIdAndProductIdsMap.containsKey(Integer.parseInt(str))) {
							/* list叠加productId */
                            List<Integer> productIdList = brandIdAndProductIdsMap.get(Integer.parseInt(str));
                            for (String productIdStr : getListByStr(authorizedConfig.getProductIds())) {
                                if (productIdList.indexOf(Integer.parseInt(productIdStr)) == -1) {
                                    productIdList.add(Integer.parseInt(productIdStr));
                                }
                            }
                        } else {
							/* map.put */
                            List<Integer> productIdList = new ArrayList<Integer>();
                            for (String productIdStr : getListByStr(authorizedConfig.getProductIds())) {
                                if (productIdList.indexOf(Integer.parseInt(productIdStr)) == -1) {
                                    productIdList.add(Integer.parseInt(productIdStr));
                                }
                            }
                            brandIdAndProductIdsMap.put(Integer.parseInt(str), productIdList);
                        }
                    } else {
                        logger.info("str is null ++++getBrandIds=" + authorizedConfig.getBrandIds());
                    }
                }
            }
        }
		/* 取mapKey=all的序列号的brandIds->end */
        if (!brandIdAndProductIdsMap.isEmpty()) {
			/* 检测list产品的brandId是否存在于brandIdAndProductIdsMap中 */
            boolean flag = false;
            List<CategoryProductResult> returnList = new ArrayList<CategoryProductResult>();
            for (CategoryProductResult categoryProductResult : list) {
                if (brandIdAndProductIdsMap.containsKey(categoryProductResult.getBrandId())) {
                    flag = true;
					/* 得到productIds */
                    List<Integer> productIdList = brandIdAndProductIdsMap.get(categoryProductResult.getBrandId());
                    if (productIdList.size() == 0) {
                        returnList.add(categoryProductResult);
                    } else {
						/* 检测productIdList */
                        if (productIdList.indexOf(categoryProductResult.getProductId()) > -1)
                            returnList.add(categoryProductResult);
                    }
                }
            }
            if (flag) {
                return returnList;
            }
        }
		/* 过滤 */
        for (int i = 0; i < list.size(); i++) {
            boolean isRemove = isRemove2(list.get(i), authorizedConfigsMap);
            if (isRemove) {
                list.remove(i);
                i--;
            }
        }
		/* 过滤end */
        return list;
    }

    /**
     * 判断该categoryProductResult是否会被authorizedConfigs(序列号集合)过滤掉
     *
     * @param categoryProductResult
     * @return
     * @author huangsongbo
     */
    private boolean isRemove(CategoryProductResult categoryProductResult,
                             Map<Integer, List<AuthorizedConfig>> authorizedConfigsMap) {
        List<Integer> brandIdList = new ArrayList<Integer>();
        for (Integer key : authorizedConfigsMap.keySet()) {
            brandIdList.add(key);
        }
        if (brandIdList.indexOf(categoryProductResult.getBrandId()) == -1) {
			/* 该产品的brandId不存在于brandIdList中->移除该产品(返回true) */
            return false;
        } else {
			/* 判断是否要remove掉 */
            List<AuthorizedConfig> authorizedConfigs = authorizedConfigsMap.get(categoryProductResult.getBrandId());
            for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
                if (/* 满足过滤条件 */
                        matchFilter(categoryProductResult, authorizedConfig)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断该categoryProductResult是否会被authorizedConfigs(序列号集合)过滤掉
     *
     * @param categoryProductResult
     * @return false:不过滤(保存);true:过滤掉(去掉)
     * @author huangsongbo
     */
    private boolean isRemove2(CategoryProductResult categoryProductResult,
                              Map<String, List<AuthorizedConfig>> authorizedConfigsMap) {
        boolean isRemoved = true;
        List<String> bigTypeList = new ArrayList<String>();
        for (String key : authorizedConfigsMap.keySet()) {
            bigTypeList.add(key);
        }
		/* 根据categoryProductResult的productId关联查询出bigType,smallTypeId */
        BaseProduct baseProduct = null;
        if (Utils.enableRedisCache()) {
            baseProduct = BaseProductCacher.get(categoryProductResult.getProductId());
        } else {
            baseProduct = baseProductService.get(categoryProductResult.getProductId());
        }

        if (baseProduct == null) {
            return isRemoved;
        }
        SysDictionary bigType = sysDictionaryService.findOneByTypeAndValue("productType",
                Integer.valueOf(baseProduct.getProductTypeValue()));
        if (bigType == null) {
            return isRemoved;
        }
        SysDictionary smallType = sysDictionaryService.findOneByTypeAndValue(bigType.getValuekey(),
                baseProduct.getProductSmallTypeValue());
        if (smallType != null) {
            if (bigTypeList.indexOf(bigType.getValuekey()) == -1) {
				/* 去除 */
                isRemoved = false;
            } else {
                List<AuthorizedConfig> authorizedConfigs = authorizedConfigsMap.get(bigType.getValuekey());
                for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
					/* 符合一个authorizedConfig,返回false,break */
                    List<String> brandIds = getListByStr(authorizedConfig.getBrandIds());
                    List<String> smallTypeIds = getListByStr(authorizedConfig.getSmallTypeIds());
                    List<String> productIds = getListByStr(authorizedConfig.getProductIds());
                    if (brandIds.indexOf("" + categoryProductResult.getBrandId()) == -1) {
                        continue;
                    }
                    if (smallTypeIds.size() > 0 && smallTypeIds.indexOf("" + smallType.getId()) == -1) {
                        continue;
                    }
                    if (productIds.size() > 0 && productIds.indexOf("" + categoryProductResult.getProductId()) == -1) {
                        continue;
                    }
                    isRemoved = false;
                    break;
                }
            }
        }
        return isRemoved;
    }

    /**
     * 判断指定的categoryProductResult是否满足过滤条件authorizedConfig
     *
     * @param categoryProductResult
     * @param authorizedConfig
     * @return
     */
    private boolean matchFilter(CategoryProductResult categoryProductResult, AuthorizedConfig authorizedConfig) {
		/* 根据categoryProductResult的productId关联查询出bigType,smallTypeId */
        BaseProduct baseProduct = baseProductService.get(categoryProductResult.getProductId());
        SysDictionary bigType = sysDictionaryService.findOneByTypeAndValue("productType",
                Integer.valueOf(baseProduct.getProductTypeValue()));
        SysDictionary smallType = sysDictionaryService.findOneByTypeAndValue(bigType.getValuekey(),
                baseProduct.getProductSmallTypeValue());
		/* 根据categoryProductResult的productId关联查询出bigType,smallTypeId->end */
        List<String> bigTypes = getListByStr(authorizedConfig.getBigType());
        List<String> smallTypeIds = getListByStr(authorizedConfig.getSmallTypeIds());
        List<String> productIds = getListByStr(authorizedConfig.getProductIds());
        if (bigTypes.size() == 0) {
            if (productIds.size() == 0) {
				/* 保留 */
                return false;
            } else {
                if (productIds.indexOf("" + categoryProductResult.getProductId()) == -1) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (bigTypes.indexOf(bigType.getValuekey()) == -1) {
				/* 移除 */
                return true;
            } else {
				/* 大类符合->匹配小类 */
                if (smallTypeIds.size() == 0)
					/* 保留 */
                    return false;
                if (smallTypeIds.indexOf("" + smallType.getId()) == -1) {
					/* 移除 */
                    return true;
                } else {
					/* 小类符合->匹配产品id */
                    if (productIds.size() == 0)
						/* 保留 */
                        return false;
                    if (productIds.indexOf("" + categoryProductResult.getProductId()) != -1) {
						/* 保留 */
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 字符串转化为list
     *
     * @param str
     * @return
     * @author huangsongbo
     */
    private List<String> getListByStr(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(str))
            return list;
        for (String str2 : str.split(",")) {
            list.add(str2);
        }
        return list;
    }

    /**
     * 判断list中是否有存在指定品牌的产品
     *
     * @param list 需过滤的list
     * @param map
     * @return
     * @author huangsongbo
     */
    public boolean isAliveBrandId(List<CategoryProductResult> list, Map<Integer, List<AuthorizedConfig>> map) {
        if (map.size() == 0)
            return false;
		/* 获得brandList */
        List<Integer> brandIdList = new ArrayList<Integer>();
        for (Integer key : map.keySet()) {
            brandIdList.add(key);
        }
		/* 获得brandList->end */
        for (CategoryProductResult categoryProductResult : list) {
            if (brandIdList.indexOf(categoryProductResult.getBrandId()) != -1) {
				/* 存在指定品牌的产品 */
                return true;
            }
        }
        return false;
    }


    @Override
    public List<CategoryProductResult> findBuildingHomeProductResult(
            ProductCategoryRel productCategoryRel) {

        return productCategoryRelMapper.findBuildingHomeProductResult(productCategoryRel);
    }

    @Override
    public int findBuildingHomeProductCount(
            ProductCategoryRel productCategoryRel) {

        return productCategoryRelMapper.findBuildingHomeProductCount(productCategoryRel);
    }

    @Override
    public int findCustomizedCategoryProductResultCount(
            ProductCategoryRel productCategoryRel) {
        // TODO Auto-generated method stub
        return productCategoryRelMapper.findCustomizedCategoryProductResultCount(productCategoryRel);
    }

    @Override
    public int findRecommendCategoryProductResultCount(
            ProductCategoryRel productCategoryRel) {
        // TODO Auto-generated method stub
        return productCategoryRelMapper.findRecommendCategoryProductResultCount(productCategoryRel);
    }

    @Override
    public List<CategoryProductResult> findCustomizedCategoryProductResult(
            ProductCategoryRel productCategoryRel) {
        // TODO Auto-generated method stub
        return productCategoryRelMapper.findCustomizedCategoryProductResult(productCategoryRel);
    }

    @Override
    public List<CategoryProductResult> findRecommendCategoryProductResult(
            ProductCategoryRel productCategoryRel) {
        // TODO Auto-generated method stub
        return productCategoryRelMapper.findRecommendCategoryProductResult(productCategoryRel);
    }

    @Override
    public CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.getCategoryProductResultByProductId(productCategoryRel);
    }

    @Override
    public List<CategoryProductResult> getCategoryProductResult(
            ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.getCategoryProductResult(productCategoryRel);
    }

    @Override
    public int getCategoryProductCount(
            ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.getCategoryProductCount(productCategoryRel);
    }

    @Override
    public int getRecommendResultCount(ProductCategoryRel productCategoryRel) {
        // TODO Auto-generated method stub
        return productCategoryRelMapper.findRecommendResultCount(productCategoryRel);
    }

    @Override
    public List<CategoryProductResult> getRecommendResult(
            ProductCategoryRel productCategoryRel) {
        // TODO Auto-generated method stub
        return productCategoryRelMapper.findRecommendResult(productCategoryRel);
    }

    @Override
    public ProductCategoryRel bgWallAndSpecialTypeInfo(ProductCategoryRel productCategoryRel, boolean isShowbgWall, SysDictionary bigSd, SysDictionary smallSd, BaseProduct baimoProduct, BaseProduct productSelected) {
        String specialProductTypes = Utils.getValue("special.productType", "");
        String filterLength = Utils.getValue("app.filter.stretch.length", "0");
        String curtainLength = Utils.getValue("app.filter.3mAbove.stretch.length", "0");

        if (isShowbgWall) {
            //只显示背景墙exceptRecommend设为true
//				exceptRecommend = "true";
            productCategoryRel.setShowBgWall(true);
            if (baimoProduct != null) {
                String fullPaveLength = baimoProduct.getFullPaveLength();
                if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
                    fullPaveLength = baimoProduct.getProductLength();
                }
                String productHeight = baimoProduct.getProductHeight();
                if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
                        && StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0) {
                    //窗帘3米以上过滤40cm
                    if (bigSd.getValue().intValue() == 16 && Utils.getIntValue(fullPaveLength) > 300) {
                        productCategoryRel.setStartLength((Utils.getIntValue(fullPaveLength) - Utils.getIntValue(curtainLength)) + 1);
                        productCategoryRel.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(curtainLength));
                    } else {
                        productCategoryRel.setStartLength((Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength)) + 1);
                        productCategoryRel.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength));
                    }
                    productCategoryRel.setBgWallHeight(productHeight);
                } else {
                    productCategoryRel.setValue(true);
                }
            }
        } else {
            productCategoryRel.setShowBgWall(false);
        }
        //判断是否是特殊分类
        if (StringUtils.isNotBlank(specialProductTypes)) {
            JSONArray productTypeArray = JSONArray.fromObject(specialProductTypes);
            if (productTypeArray != null && productTypeArray.size() > 0) {
                for (int i = 0; i < productTypeArray.size(); i++) {
                    JSONObject jsonObj = (JSONObject) productTypeArray.get(i);
                    String bigTypeKey = jsonObj.getString("productTypeKey");
                    String smallTypeKeys = "," + jsonObj.getString("productSmallTypeKeys") + ",";
                    if (bigTypeKey.equals(bigSd == null ? "" : bigSd.getValuekey())) {
                        productCategoryRel.setSpecialProductType(smallTypeKeys);
                        String smallKey = "";
                        if (smallSd != null) {
                            if ("baimo".equals(smallSd.getAtt3())) {
                                smallKey = Utils.getTypeValueKey(smallSd.getValuekey());
                            } else {
                                smallKey = smallSd.getValuekey();
                            }
                            //背景墙特殊处理，（app必须配置special.productType背景墙类型）
								/*if(isShowbgWall){
									productCategoryRel.setSpecialProductType(null);
								}*/
                            if (smallTypeKeys.indexOf("," + smallKey + ",") != -1/* && !isShowbgWall */) {
                                productCategoryRel.setProductSmallTypeKey(smallKey);
                                break;
                            }
                        }
                    }
                }
            }
        }
        //过滤产品长、 高信息
        String productSmallTypes_LH = Utils.getValue("filter.productLH.productSmallType", "");
        if (Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), productSmallTypes_LH)) {
            if (baimoProduct != null) {
                if (baimoProduct.getProductLength() != null && baimoProduct.getProductHeight() != null) {
                    productCategoryRel.setProductLength(baimoProduct.getProductLength());
                    productCategoryRel.setProductHeight(baimoProduct.getProductHeight());
                } else {
                    productCategoryRel.setProductLength("-1");
                    productCategoryRel.setProductHeight("-1");
                }
            } else {
                if (productSelected.getProductLength() != null && productSelected.getProductHeight() != null) {
                    productCategoryRel.setProductLength(productSelected.getProductLength());
                    productCategoryRel.setProductHeight(productSelected.getProductHeight());
                } else {
                    productCategoryRel.setProductLength("-1");
                    productCategoryRel.setProductHeight("-1");
                }
            }
        }
        //过滤产品长、宽信息
        String productSmallTypes_LW = Utils.getValue("filter.productLW.productSmallType", "");
        if (Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), productSmallTypes_LW)) {
            if (baimoProduct != null) {
                productCategoryRel.setProductLength(baimoProduct.getProductLength());
                productCategoryRel.setProductWidth(baimoProduct.getProductWidth());
            } else {
                productCategoryRel.setProductLength(productSelected.getProductLength());
                productCategoryRel.setProductWidth(productSelected.getProductWidth());
            }
        }
        return productCategoryRel;
    }

    @Override
    public ProductCategoryRel getWallTypeLogic(ProductCategoryRel productCategoryRel, DesignPlanProduct designPlanProduct, SysDictionary bigSd, HttpServletRequest request) {
        String filterLength = Utils.getValue("app.filter.stretch.length", "0");
        //绑定点有多个
        String parentProductId = designPlanProduct.getBindParentProductId();
        List<String> parentProIds = new ArrayList<String>();
        if (StringUtils.isNotBlank(parentProductId)) {
            String arrayParentProId[] = parentProductId.split(",");
            DesignPlanProduct dpp = null;
            //如果该绑定产品存在，就不需查询绑定产品的分类
            for (String arrId : arrayParentProId) {
                dpp = new DesignPlanProduct();
                dpp.setIsDeleted(0);
                dpp.setPlanId(designPlanProduct.getPlanId());
                dpp.setInitProductId(Utils.getIntValue(arrId));

                List<DesignPlanProduct> ls = designPlanProductService.getList(dpp);
                //如果背景墙存在空间则不显示背景墙产品
                if (Lists.isEmpty(ls) && ls.size() == 0) {
                    parentProIds.add(arrId);
                }
            }

            //判断是否是新增背景墙,是则过滤背景墙长高
            if (Lists.isNotEmpty(parentProIds) && parentProIds.size() > 0) {
                productCategoryRel.setTemplateProductId(parentProIds);
                productCategoryRel.setShowBgWall(true);
                if ("3".equals(productCategoryRel.getProductTypeValue())) {
                    productCategoryRel.setBeijing(true);
                } else {
                    productCategoryRel.setStretch(true);
                }
                //白模背景墙产品信息
                BaseProduct product = new BaseProduct();
                if (Utils.enableRedisCache()) {
                    product = BaseProductCacher.get(Utils.getIntValue(arrayParentProId[0]));
                } else {
                    product = baseProductService.get(Utils.getIntValue(arrayParentProId[0]));
                }
                //过滤背景墙长高
                String fullPaveLength = product.getFullPaveLength();
                if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
                    fullPaveLength = product.getProductLength();
                }
                String productHeight = product.getProductHeight();
                if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
                        && StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0) {

                    productCategoryRel.setStartLength((Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength)) + 1);
                    productCategoryRel.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength));
                    productCategoryRel.setBgWallHeight(productHeight);

                    SysDictionary bmSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(), product.getProductSmallTypeValue());
                    SysDictionary sdic = null;
                    if (bmSd != null && "baimo".equals(bmSd.getAtt3())) {
                        sdic = sysDictionaryService.findOneByTypeAndValueKey(bigSd.getValuekey(), Utils.getTypeValueKey(bmSd.getValuekey()));
                    }
                    productCategoryRel.setProductSmallTypeValue(sdic == null ? 0 : sdic.getValue());
                } else {
                    productCategoryRel.setValue(true);
                }
            }
        }
        return productCategoryRel;
    }

    /**
     * findRecommendCategoryProductResult优化版
     *
     * @author huangsongbo
     */
    public List<CategoryProductResult> findRecommendCategoryProductResultV2(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findRecommendCategoryProductResultV2(productCategoryRel);
    }

    /**
     * findCustomizedCategoryProductResult优化版
     *
     * @author huangsongbo
     */
    public List<CategoryProductResult> findCustomizedCategoryProductResultV2(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findCustomizedCategoryProductResultV2(productCategoryRel);
    }

    public List<CategoryProductResult> findCustomizedCategoryProductResultV3(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findCustomizedCategoryProductResultV3(productCategoryRel);
    }

    public List<CategoryProductResult> findRecommendCategoryProductResultV3(ProductCategoryRel productCategoryRel) {
        return productCategoryRelMapper.findRecommendCategoryProductResultV3(productCategoryRel);
    }


    // 按搜索产品属性匹配度排序
    public List<CategoryProductResult> getProductList(List<CategoryProductResult> list,
                                                      ProductCategoryRel productCategoryRel) {


        // 注释原因:旧产品搜索接口太卡了,影响设计部工作效率 created by huangsongbo 20170526 ->start

		/*// 搜索产品的排序属性排序->start
		Long startDate1_1 = new Date().getTime();
		List<ProductProps> propsOrderList = productCategoryRel.getPropsOrderList();
		if (Lists.isNotEmpty(propsOrderList)) {
			ComparatorP cpmparator = new ComparatorP();
			Collections.sort(propsOrderList, cpmparator);

			for (CategoryProductResult productResult : list) {
				BaseProduct baseProduct = null;
				if (Utils.enableRedisCache()) {
					baseProduct = BaseProductCacher.get(productResult.getProductId());
				} else {
					baseProduct = baseProductService.get(productResult.getProductId());
				}
				if (baseProduct == null) {
					logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map = productAttributeService.getPropertyOrderMap(baseProduct.getId());
				if (map.size() == 0) {
					productResult.setProductOrdering(9999999);
					continue;
				}
				StringBuffer orderNumber = new StringBuffer();
				for (ProductProps props : propsOrderList) {
					// 选中产品显示第一个
					if (productResult.getProductId().intValue() == props.getProductId().intValue()) {
						productResult.setProductOrdering(1);
						continue;
					}
					int tmp = 0;
					for (Map.Entry<String, String> entry : map.entrySet()) {
						// 属性类型匹配
						if (entry.getKey().equals(props.getParentCode())) {
							if (StringUtils.isNotBlank(map.get(props.getParentCode()))) {
								ProductProps productProps = productPropsService
										.get(Utils.getIntValue(map.get(props.getParentCode())));
								if (productProps != null) {
									if (productProps.getPropValue().equals(props.getPropValue())) {
										// 1、value相等且属性数量一样2、value相等且属性数量不一样
										if (propsOrderList.size() == map.size()) {
											orderNumber.append(2);
										} else {
											orderNumber.append(3);
										}
										tmp++;
										break;
									} else {
										// 3、value不等、同类型且属性数量一样
										// 4、value不等、同类型且属性数量不一样
										if (propsOrderList.size() == map.size()) {
											orderNumber.append(4);
										} else {
											orderNumber.append(5);
										}
										tmp++;
										break;
									}
								}
							}
						}
					}
					// 5、无匹配的属性
					if (tmp == 0) {
						orderNumber.append(7);
					}
				}
				productResult.setProductOrdering(Utils.getIntValue(orderNumber.toString()));
			}
		}
		Long startDate1_2 = new Date().getTime();
		logger.info("------搜索产品的排序属性排序:" + (startDate1_2 - startDate1_1));
		// 搜索产品的排序属性排序->end*/
        // 注释原因:旧产品搜索接口太卡了,影响设计部工作效率 created by huangsongbo 20170526 ->end

        // 如果该产品关联了色系则关联色系排序->start
        Long startDate2_1 = new Date().getTime();
        baseProductService.productColorsSortV2(list);
        Long startDate2_2 = new Date().getTime();
        logger.info("------色系排序:" + (startDate2_2 - startDate2_1));
        // 如果该产品关联了色系则关联色系排序->end

        // 列表排序,推荐,小类,同属性匹配度,使用量,色系排序->start
        Long startDate3_1 = new Date().getTime();
        ComparatorO cpmparator = new ComparatorO();
        cpmparator.setList(productCategoryRel.getSortSmallTypeValueList());
        Collections.sort(list, cpmparator);
        Long startDate3_2 = new Date().getTime();
        logger.info("------列表排序,推荐,小类,同属性匹配度,使用量,色系排序:" + (startDate3_2 - startDate3_1));
        // 列表排序,推荐,小类,同属性匹配度,使用量,色系排序-->end

        return list;
    }

    private DesignPlanProduct sethouseTypeSearchCondition(ProductCategoryRel productCategoryRel, String houseType,
                                                          Integer planProductId) {
		/* String msg; */
        DesignPlanProduct designPlanProduct = null;
        if (StringUtils.isNotBlank(houseType)) {
			/* 对应出productHouseType的数据字典的value */
            SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
            sysDictionarySearch.setAtt1(houseType);
            sysDictionarySearch.setType("productHouseType");
            List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
            List<String> productHouseTypeList = new ArrayList<String>();// 过滤条件
            List<String> productHouseTypeList3 = new ArrayList<String>();
            if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
                for (SysDictionary sysDictionary : sysDictionaryList) {
                    productHouseTypeList.add("" + sysDictionary.getValue());
                    productHouseTypeList3.add("" + sysDictionary.getValue());
                }
            }
			/* 得到产品空间类型valueList */
            // String productHouseTypesStr=productSelected.getHouseType();
			/* 取白膜的productHouseType */
            if (planProductId != null && planProductId != 0) {
                designPlanProduct = designPlanProductService.get(planProductId);
                if (designPlanProduct == null) {
					/*
					 * msg = "找不到该设计方案产品：" + planProductId; return new
					 * ResponseEnvelope<ProductCategoryRel>(false, msg,
					 * productCategoryRel.getMsgId());
					 */
                    return designPlanProduct;
                }
                Integer baimoProductId = designPlanProduct.getInitProductId();
                BaseProduct baimoProduct = baseProductService.get(baimoProductId);
                if (baimoProduct != null) {
                    String productHouseTypesStr = baimoProduct.getHouseTypeValues();
                    if (StringUtils.isNotBlank(productHouseTypesStr)) {
                        List<String> productHouseTypeList2 = Utils.getListFromStr(productHouseTypesStr);
                        productHouseTypeList.retainAll(productHouseTypeList2);
                    }
                }
            }
            if (productHouseTypeList.size() > 0) {
                productCategoryRel.setHouseTypeList(productHouseTypeList);
            } else {
                productCategoryRel.setHouseTypeList(productHouseTypeList3);
            }
        }
        return designPlanProduct;
    }

    public class ComparatorP implements Comparator<Object> {
        public int compare(Object obj1, Object obj2) {
            ProductProps unity1 = (ProductProps) obj1;
            ProductProps unity2 = (ProductProps) obj2;
            int flag = (unity1.getSequenceNumber() == null ? new Integer(0) : new Integer(unity1.getSequenceNumber()))
                    .compareTo(unity2.getSequenceNumber() == null ? new Integer(0)
                            : new Integer(unity2.getSequenceNumber()));
            if (flag == 0) {
                return (unity1.getSequenceNumber() == null ? new Integer(0) : new Integer(unity1.getSequenceNumber()))
                        .compareTo(unity2.getSequenceNumber() == null ? new Integer(0)
                                : new Integer(unity2.getSequenceNumber()));
            } else {
                return flag;
            }
        }
    }

    public class ComparatorO implements Comparator<Object> {
        /**
         * 优先排序小类
         */
        private List<Integer> list;

        public List<Integer> getList() {
            return list;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }

        public int compare(Object obj1, Object obj2) {
            CategoryProductResult unity1 = (CategoryProductResult) obj1;
            CategoryProductResult unity2 = (CategoryProductResult) obj2;
            // 小类排序.优先小类排前面
            Integer productOrderSmallTypeValue1 = this.getProductOrderSmallTypeValue(unity1);
            Integer productOrderSmallTypeValue2 = this.getProductOrderSmallTypeValue(unity2);
            Integer productOrder1 = unity1.getProductOrdering() == null ? new Integer(0)
                    : new Integer(unity1.getProductOrdering());
            Integer productOrder2 = unity2.getProductOrdering() == null ? new Integer(0)
                    : new Integer(unity2.getProductOrdering());
            Integer colorsOrder1 = unity1.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE)
                    : new Integer(unity1.getColorsOrdering());
            Integer colorsOrder2 = unity2.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE)
                    : new Integer(unity2.getColorsOrdering());

            if (this.list != null && productOrderSmallTypeValue1.compareTo(productOrderSmallTypeValue2) != 0) { // 小类优先排序
                return productOrderSmallTypeValue2.compareTo(productOrderSmallTypeValue1);
            } else if (unity1.getOrderType().compareTo(unity2.getOrderType()) != 0) { // 定制、推荐、小类
                return unity1.getOrderType().compareTo(unity2.getOrderType());
            } else if (productOrder1.compareTo(productOrder2) != 0) {// 属性匹配度
                return productOrder1.compareTo(productOrder2);
            } else if (colorsOrder1.compareTo(colorsOrder2) != 0) {// 色系排序
                return colorsOrder1.compareTo(colorsOrder2);
            } else { // 使用量
                return (unity2.getProductCount() == null ? new Integer(0) : unity2.getProductCount())
                        .compareTo(unity1.getProductCount() == null ? new Integer(0) : unity1.getProductCount());
            }
        }

        private Integer getProductOrderSmallTypeValue(CategoryProductResult categoryProductResult) {
            if (this.list == null || this.list.size() == 0) {
                return 0;
            }
            if (categoryProductResult.getProductSmallTypeValue() != null) {
                if (this.list.indexOf(categoryProductResult.getProductSmallTypeValue()) != -1) {
                    return 1;
                }
            }
            return 0;
        }
    }


    @SuppressWarnings("unchecked")
    public ResponseEnvelope<CategoryProductResult> searchProductV6(
            String templateProductId,
            ProductCategoryRel productCategoryRel,
            LoginUser loginUser,
            String productModelOrBrandName,
            Integer planProductId,
            String houseType,
            Integer productTypeValue,
            Integer spaceCommonId,
            DesignPlan designPlan,
            Integer smallTypeValue,
            String mediaType,
            Integer statusType,
            Integer isStandard,
            String regionMark,
            Integer styleId,
            String measureCode,
            String smallpox,
            Integer productId,
            String style,
            String platformCode
            /*加一个msgId来判断从那张表里去方案产品的数据*/
            ,String msgId
    ) {

        if (StringUtils.isEmpty(style)) {
            style = "online";
        }

        if (StringUtils.equals("online", style)) {
            style = "new";
        } else {
            style = "old";
        }

        Long startTimeTop = new Date().getTime();

        //获取搜索接口配置,实时搜索(old)/预处理搜索(new)
		/*String oldOrNew = Utils.getValue("app.product.searchProduct.oldOrNew", "new");*/

        if ("0".equals(templateProductId)) {
            templateProductId = "";
        }

        Integer limit = productCategoryRel.getLimit();
        Integer start = productCategoryRel.getStart();

        // 设置查询条件 ->start
        Long startTime = new Date().getTime();
        PrepProductSearchInfo prepProductSearchInfo = new PrepProductSearchInfo();

        if (loginUser.getUserType() == 1) {
//			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
            if (loginUser.getUserType() == 1) {// 1:代表内部用户，可以看到，测试，上架，发布的产品
                // ???2017.11.28有个bug,内部用户只能搜出已发布的产品,检测出这里查询条件出错??? 注释 by huangsongbo
                prepProductSearchInfo.setIsInternalUser("yes");
            } else {
                prepProductSearchInfo.setIsInternalUser("false");
            }
            if (statusType != null && statusType != 1) { // 1:代表内部用户，可以看到，测试，上架，发布的产品
                prepProductSearchInfo.setIsInternalUser("false");
            } else {
                prepProductSearchInfo.setIsInternalUser("yes");
            }
        }
        //运营网站和移动端只搜索已发布数据#[(2,web2),(3,pc),(4,mac),(5,ios),(6,adr),(7,ipad)]
        if ("2".equals(mediaType) || "5".equals(mediaType) || "6".equals(mediaType)) {
            prepProductSearchInfo.setIsInternalUser("false");
        }

        // 用户类型=1,可以查询测试+上架的产品
        // 由于统一查询已发布数据,所以去掉该逻辑
		/*if(!new Integer(1).equals(loginUser.getUserType()))
			prepProductSearchInfo.setProductStatus(new Integer(1));
		*/

        // 型号查询条件(型号/品牌名) ->start
        if (StringUtils.isNotBlank(productModelOrBrandName))
            prepProductSearchInfo.setProductModelOrBrandName(productModelOrBrandName.trim());
        // 型号查询条件(型号/品牌名) ->end

        // 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)
        BaseProduct baimoBaseProductInit = null;
        DesignPlanProduct designPlanProduct = designPlanService.getDesignProduct(planProductId, msgId);
        // 点击的产品初始白膜
        if (designPlanProduct != null) {
            Integer initProductId = designPlanProduct.getInitProductId();
            if (initProductId != null && initProductId > 0) {
                baimoBaseProductInit = baseProductService.get(initProductId);
                // 如果没有对应的白膜(说明这个产品可能是新增出来的,则baimoBaseProductInit设置为自己)
            }
        }
        // 得到空间类型搜索条件
        List<String> HouseTypeList = this.getHouseTypeSearchCondition(houseType, baimoBaseProductInit);
        prepProductSearchInfo.setHouseTypeList(HouseTypeList);

        // 获取黑名单配置信息 ->start
		/*Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		prepProductSearchInfoSearchSimple.setBlacklistSet(blacklist);*/
        // 获取黑名单配置信息 ->end

        // 分类code,大类,小类 ->start
        //注:暂时只处理只有一个分类
        Integer productTypeValueInt = productTypeValue;
        if (productTypeValueInt == null || new Integer(0).equals(productTypeValueInt)) {

        } else {
            prepProductSearchInfo.setBigTypeValue(productTypeValueInt);
        }
		/*prepProductSearchInfo.setSmallTypeValue(Integer.valueOf(smallTypeValue));*/
        String categoryCode = productCategoryRel.getCategoryCode();
        ProCategory proCategory = null;
        if (StringUtils.isNotBlank(categoryCode)) {
            proCategory = proCategoryService.findOneByCode(categoryCode);
            if (proCategory != null) {
                Integer level = proCategory.getLevel();
                if (level != null && level.equals(new Integer(0))) {
                    prepProductSearchInfo.setFirstStageCode(categoryCode);
                } else if (level != null && level.equals(new Integer(1))) {
                    prepProductSearchInfo.setSecondStageCode(categoryCode);
                } else if (level != null && level.equals(new Integer(2))) {
                    prepProductSearchInfo.setThirdStageCode(categoryCode);
                } else if (level != null && level.equals(new Integer(3))) {
                    prepProductSearchInfo.setFourthStageCode(categoryCode);
                } else if (level != null && level.equals(new Integer(4))) {
                    prepProductSearchInfo.setFifthStageCode(categoryCode);
                } else {
                    prepProductSearchInfo.setCategoryLongCodeLike("." + categoryCode + ".");
                }
            }
        }
        // 分类code,大类,小类 ->end

        // 设置空间id,样板房id,init白膜id(判断是否是定制)查询条件 ->start
        prepProductSearchInfo.setSpaceCommonId(spaceCommonId);
        prepProductSearchInfo.setDesignTempletId(designPlan.getDesignId());
        if (baimoBaseProductInit != null) {
            prepProductSearchInfo.setBaimoId(baimoBaseProductInit.getId());
        }
        // 设置空间id,样板房id,init白膜id(判断是否是定制)查询条件 ->end

        // 属性查询条件 ->start
        BaseProduct baseProductProp = null;
        // 当前选中的产品
        BaseProduct baseProductChecked = null;
        if (designPlanProduct != null) {
            baseProductChecked = baseProductService.get(designPlanProduct.getProductId());
            if (baimoBaseProductInit == null) {
                baseProductProp = baseProductChecked;
            } else {
                baseProductProp = baimoBaseProductInit;
            }

			/*if(StringUtils.equals("new", oldOrNew)){*/
            if (StringUtils.equals("new", style)) {
                // 预处理搜索属性查询条件
                // 过滤属性是依据该产品对应的初始化白膜的过滤属性
                if (baseProductProp != null) {
                    List<ProductPropsSimple> productPropsSimpleListBaimo = baseProductService.getProductPropsSimpleByProductId(baseProductProp.getId());
                    if (productPropsSimpleListBaimo != null && productPropsSimpleListBaimo.size() > 0) {
                        // 取出排序属性+过滤属性
                        List<ProductPropsSimple> productOrderPropList = new ArrayList<ProductPropsSimple>();
                        List<ProductPropsSimple> productFilterPropList = new ArrayList<ProductPropsSimple>();
                        for (ProductPropsSimple productPropsSimple : productPropsSimpleListBaimo) {
                            if (productPropsSimple.getIsSort() == 1) {
                                // 过滤属性
                                productFilterPropList.add(productPropsSimple);
                            }
                        }

                        // 处理过滤属性 ->start
                        if (productFilterPropList.size() > 0) {
                            prepProductSearchInfo.setHasFilterProps(true);
                            prepProductSearchInfo.setBaimoProductId(baseProductProp.getId());
                        }
                        // 处理过滤属性 ->end

                        // 处理排序属性 ->start
                        // 排序属性是按照点击的产品的排序属性决定匹配度
                        List<ProductPropsSimple> productPropsSimpleList = baseProductService.getProductPropsSimpleByProductId(designPlanProduct.getProductId());
                        if (productPropsSimpleList != null && productPropsSimpleList.size() > 0) {
                            // 取出排序属性+过滤属性
                            for (ProductPropsSimple productPropsSimple : productPropsSimpleList) {
                                if (productPropsSimple.getIsSort() == 0) {
                                    // 排序属性
                                    productOrderPropList.add(productPropsSimple);
                                }
                            }
                        }
                        if (productOrderPropList.size() > 0) {
                            prepProductSearchInfo.setHasOrderProps(true);
                            prepProductSearchInfo.setProductId(designPlanProduct.getProductId());
                        }
                        // 处理排序属性 ->end

                    }
                }
            } else {
                productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
                        (designPlanProduct.getInitProductId() == null || designPlanProduct.getInitProductId().intValue() == 0) ? designPlanProduct.getProductId() : designPlanProduct.getInitProductId());
                prepProductSearchInfo.setAttributeConditionList(productCategoryRel.getAttributeConditionList());
                prepProductSearchInfo.setAttributeConditionSize(productCategoryRel.getAttributeConditionSize());
            }
        } else {
            // 未点击产品搜索(通过分类栏直接搜索)
        }
        // 属性查询条件 ->start

        // 解析配置filter.productLH.productSmallType并设置对应的查询条件 ->start
        String filterProductLH = Utils.getValue("filter.productLH.productSmallType", "");
        // 得到小类数据字典
        SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByTypeAndValueAndValue("productType", productTypeValue, smallTypeValue);
        if (StringUtils.isNotBlank(filterProductLH)) {
            List<String> filterProductLHSmallTypeList = Utils.getListFromStr(filterProductLH);
            if (sysDictionarySmallType != null) {
                String smallTypeCode = sysDictionarySmallType.getValuekey();
                if (filterProductLHSmallTypeList.indexOf(smallTypeCode) != -1) {
                    // 设置长,高过滤条件
                    if (baimoBaseProductInit != null) {
                        Integer productLength = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductLength()) ? baimoBaseProductInit.getProductLength() : "0");
                        prepProductSearchInfo.setProductLength(productLength);
                        Integer productHeight = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductHeight()) ? baimoBaseProductInit.getProductHeight() : "0");
                        prepProductSearchInfo.setProductHeight(productHeight);
                    }
                }
            }
        }
        // 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->end

        // 解析配置special.productType并设置对应的查询条件 ->start
        // 如果点击的产品是白膜,处理小类:basic_mengk变成mengk
        Integer smallTypeValueTemp = smallTypeValue;
        if (designPlanProduct != null && designPlanProduct.getProductId().equals(designPlanProduct.getInitProductId())) {
            // 点击的是白膜
            // 特殊处理背景墙(basic_beij->beijing)
            if (new Integer(3).equals(productTypeValue) && new Integer(16).equals(smallTypeValue)) {
                smallTypeValueTemp = 17;
            } else {
                smallTypeValueTemp = sysDictionaryService.getProductValueByBaimoValue(productTypeValue, smallTypeValue) == null ?
                        smallTypeValueTemp : sysDictionaryService.getProductValueByBaimoValue(productTypeValue, smallTypeValue);
            }
        }
        // in/notIn
        Map<String, Object> specialProductTypeMap = null;
        SysDictionary sysDictionarySmallTypeTemp = sysDictionaryService.findOneByTypeAndValueAndValue("productType", productTypeValue, smallTypeValueTemp);
        Map<String, List<String>> differenceListMap = (Map<String, List<String>>) showMoreSmallTypeMap.get("differenceListMap");
        if (sysDictionarySmallTypeTemp != null) {
            if (differenceListMap.containsKey(sysDictionarySmallTypeTemp.getValuekey())) {
                // 检测白膜是不是tijx
                if (baimoBaseProductInit != null) {
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue("productType", Integer.valueOf(baimoBaseProductInit.getProductTypeValue()), baimoBaseProductInit.getProductSmallTypeValue());
                    if (sysDictionary != null) {
                        String smallTypeValueKey2 = sysDictionary.getValuekey().trim().replace("basic_", "");
                        if (differenceListMap.get(sysDictionarySmallTypeTemp.getValuekey()).indexOf(smallTypeValueKey2) != -1) {
                            SysDictionary sysDictionary2 = sysDictionaryService.findOneByTypeAndValueKey(sysDictionary.getType(), smallTypeValueKey2);
                            specialProductTypeMap = this.getSpecialProductTypeMap(productTypeValue, sysDictionary2.getValue());
                        }
                    }
                }
            }
        }

        if (specialProductTypeMap == null) {
            specialProductTypeMap = this.getSpecialProductTypeMap(productTypeValue, smallTypeValueTemp);
        }
        if (specialProductTypeMap.containsKey("notIn")) {
            prepProductSearchInfo.setExcludeSmallTypeValue((List<Integer>) specialProductTypeMap.get("notIn"));
        }
        List<Integer> smallTypeValueList = new ArrayList<Integer>();
        List<String> smallTypeKeyList = new ArrayList<String>();
        if (specialProductTypeMap.containsKey("in")) {
            smallTypeValueList.add(smallTypeValueTemp);
        }
        // 处理搜出多个小类的情况 ->start
        if (sysDictionarySmallType != null && showMoreSmallTypeMap != null) {
            String smallTypeValueKey = sysDictionarySmallType.getValuekey().replace("basic_", "");
            // 定死处理背景墙
            if (StringUtils.equals("beij", smallTypeValueKey)) {
                smallTypeValueKey = "beijing";
            }
            Map<String, List<String>> showMoreSmallTypeInfoMap = (Map<String, List<String>>) showMoreSmallTypeMap.get("showMoreSmallTypeInfoMap");
            // showMoreSmallTypeInfoMap:{tijx=[tijx, diz], chufm=[chufm, yangtm]}
            // smallTypeValueKey:yangtm
            if (showMoreSmallTypeInfoMap.containsKey(smallTypeValueKey)) {
                // 符合显示多个小类的配置
                smallTypeKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey);
                List<Integer> smallTypeValueList2 = sysDictionaryService.getValueByTypeAndValueKeylist(sysDictionarySmallType.getType(), smallTypeKeyList);
                smallTypeValueList.removeAll(smallTypeValueList2);
                smallTypeValueList.addAll(smallTypeValueList2);
                // 设置排序
                List<Integer> sortSmallTypeValueList = prepProductSearchInfo.getSortSmallTypeValueList();
                if (sortSmallTypeValueList != null) {
                    sortSmallTypeValueList.remove(smallTypeValueTemp);
                    sortSmallTypeValueList.add(smallTypeValueTemp);
                } else {
                    sortSmallTypeValueList = new ArrayList<Integer>();
                    sortSmallTypeValueList.add(smallTypeValueTemp);
                }
                prepProductSearchInfo.setSortSmallTypeValueList(sortSmallTypeValueList);
            } else {
                // 可能这个地砖是从踢脚线白膜换过来的(该地砖对应的白膜是踢脚线白膜)
                if (differenceListMap.containsKey(smallTypeValueKey)) {
                    // 检测对应的白膜是什么小类(init_product_id)
                    if (baimoBaseProductInit != null) {
                        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue("productType", Integer.valueOf(baimoBaseProductInit.getProductTypeValue()), baimoBaseProductInit.getProductSmallTypeValue());
                        if (sysDictionary != null) {
                            String smallTypeValueKey2 = sysDictionary.getValuekey().trim().replace("basic_", "");
                            if (differenceListMap.get(smallTypeValueKey).indexOf(smallTypeValueKey2) != -1) {
                                // 按白膜查询
                                List<String> smallTypeValueKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey2);
                                List<Integer> smallTypeValueList2 = sysDictionaryService.getValueByTypeAndValueKeylist(sysDictionarySmallType.getType(), smallTypeValueKeyList);
                                smallTypeValueList.removeAll(smallTypeValueList2);
                                smallTypeValueList.addAll(smallTypeValueList2);
                                // 设置排序
                                List<Integer> sortSmallTypeValueList = prepProductSearchInfo.getSortSmallTypeValueList();
                                // 找到basic_yangtm对应的yangtm的数据字典
                                SysDictionary sysDictionary2 = sysDictionaryService.findOneByTypeAndValueKey(sysDictionary.getType(), smallTypeValueKey2);
                                if (sysDictionary2 != null) {
                                    if (sortSmallTypeValueList != null) {
                                        sortSmallTypeValueList.remove(sysDictionary2.getValue());
                                        sortSmallTypeValueList.add(sysDictionary2.getValue());
                                    } else {
                                        sortSmallTypeValueList = new ArrayList<Integer>();
                                        sortSmallTypeValueList.add(sysDictionary2.getValue());
                                    }
                                }
                                prepProductSearchInfo.setSortSmallTypeValueList(sortSmallTypeValueList);
                            }
                        }
                    }
                }
            }
        }
        // 处理搜出多个小类的情况 ->end

        prepProductSearchInfo.setSmallTypeValueList(smallTypeValueList);


        // 解析配置special.productType并设置对应的查询条件 ->end

        // 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->start
        String filterProductLW = Utils.getValue("filter.productLW.productSmallType", "");
        if (StringUtils.isNotBlank(filterProductLW)) {
            List<String> filterProductLWSmallTypeList = Utils.getListFromStr(filterProductLW);
            if (sysDictionarySmallType != null) {
                String smallTypeCode = sysDictionarySmallType.getValuekey();
                if (filterProductLWSmallTypeList.indexOf(smallTypeCode) != -1) {
                    // 设置长,宽过滤条件(小于)
                    if (baimoBaseProductInit != null) {
                        Integer productLength = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductLength()) ? baimoBaseProductInit.getProductLength() : "0");
                        prepProductSearchInfo.setLessProductLength(productLength);
                        Integer productWidth = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductWidth()) ? baimoBaseProductInit.getProductWidth() : "0");
                        prepProductSearchInfo.setLessProductWidth(productWidth);
                    }
                }
            }
        }
        // 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->end

        // 序列号查询条件 ->start
        Integer userType = loginUser.getUserType();
        if (userType == 3) {
            List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
            prepProductSearchInfo.setBaseProductList(baseProductList);
        }
        // 序列号查询条件 ->end

        // 点击普通墙面是否要显示背景墙(当背景墙删除的时候) ->start
        // 识别是否要显示背景墙
        /** 2017-11-14 by zhangwj 注释此代码。背景墙搜索逻辑变动。选实体墙时不管实体墙上的绑定点是不是绑定的背景墙，都只走搜索实体墙的逻辑
         this.setBgWallCondition(prepProductSearchInfo, designPlanProduct);	**/
        // 点击普通墙面是否要显示背景墙(当背景墙删除的时候) ->start

        // 需伸缩长度过滤的分类 ->start
        int stretchZoomLength = 0;
        Integer fullPaveLength = 0;
        if (sysDictionarySmallType != null) {
            Map<String, String> stretchZoomMap = baseProductService.getStretchZoomLength(sysDictionarySmallType.getValuekey());
            if (stretchZoomMap != null && stretchZoomMap.size() > 0) {
                if (baseProductProp != null) {
                    // 是背景墙(限制普通背景墙的长高)
                    if (new Integer(3).equals(productTypeValue)) {
                        prepProductSearchInfo.setBeijing(true);
                    } else {
                        prepProductSearchInfo.setStretch(true);
                    }

                    // 设置长高查询条件
                    stretchZoomLength = Utils.getIntValue(stretchZoomMap.get(ProductModelConstant.STRETCH_LENGTH));
                    String isHeightFilter = stretchZoomMap.get(ProductModelConstant.IS_HEIGHT_FILTER);
                    String productHeight = null;
                    String beijingLength = baseProductProp.getProductLength();
                    //是否需要高度过滤，是则设置高度值
                    if (ProductModelConstant.HEIGHT_FILTER_VALUE_YES.equals(isHeightFilter)) {
                        productHeight = baseProductProp.getProductHeight();
                    }
                    prepProductSearchInfo.setBeijingHeight(StringUtils.isNotBlank(productHeight) ? Integer.valueOf(productHeight) : null);
                    Integer beijingLengthStart = 0;
                    Integer beijingLengthEnd = 0;
                    if (StringUtils.isNotBlank(baseProductProp.getFullPaveLength())) {
                        fullPaveLength = Integer.valueOf(baseProductProp.getFullPaveLength());
                        beijingLengthStart = fullPaveLength - stretchZoomLength + 1;
                        beijingLengthEnd = fullPaveLength + stretchZoomLength;
                    } else {
                        //update by huangsongbo 2017.12.25 ->start
                        //由于有的是新增的产品,且还要有伸缩逻辑,所以还要判断initProductId对应的产品是不是白膜,如果是,走老逻辑,如果不是,取产品长度为全铺长度
                        if (baimoBaseProductInit != null && !baimoBaseProductInit.getProductCode().startsWith("baimo_")) {
                            fullPaveLength = Integer.valueOf(baimoBaseProductInit.getProductLength());
                            beijingLengthStart = fullPaveLength - stretchZoomLength + 1;
                            beijingLengthEnd = fullPaveLength + stretchZoomLength;
                        } else {
                            beijingLengthStart = 0;
                        }
						/*beijingLengthEnd = StringUtils.isNotBlank(beijingLength)?Integer.valueOf(beijingLength):null;*/
                        //update by huangsongbo 2017.12.25 ->end
                    }
                    prepProductSearchInfo.setBeijingLengthStart(beijingLengthStart);
                    prepProductSearchInfo.setBeijingLengthEnd(beijingLengthEnd);
                }
            }
        }
        // 普通背景墙长高过滤 ->end

        /**
         * 定制衣柜白模可搜索出成品衣柜（waca）和定制衣柜（dyca）,只有定制衣柜（dyca）按照尺寸范围搜索缩放；成品衣柜（waca）不用按照尺寸搜索缩放
         * @author xiaoxc_20171122
         */
       	this.getStretchZoomFilterCondition(prepProductSearchInfo,smallTypeValueList,baseProductProp);
        	
        // 是否是天花,天花只显示定制产品 ->start
        // 标准天花过滤款式、区域标识、尺寸代码搜索通用天花 ->start
        if (new Integer(1).equals(productTypeValue)) {
            prepProductSearchInfo.setTianh(false);
//            prepProductSearchInfo.setMeasureCode(measureCode);
            
            //增加天花布局标识过滤条件
			/*if(smallpox != null && smallpox != 0) {*/
			/*if(smallpox != null && !StringUtils.equals("0", smallpox)) {
				prepProductSearchInfo.setProductSmallpoxIdentify(smallpox);
			}*/
			/*prepProductSearchInfo.setIdentifyList(Utils.getIdentifyList(smallpox));*/
        }
        // 标准天花过滤款式、区域标识、尺寸代码 ->end
        // 设置授权码查询条件 ->start
		/*if(userType == 3){*/
        if (Utils.isExternalUser(userType)) {
            // 识别需不需要设置授权码查询条件,比如东鹏,点击的是dim,则需要设置授权码过滤条件,其他分类则不需要授权码过滤条件
            boolean isShowWuBoolean = true;
            if (proCategory != null) {
                // 识别点击的分类的大类
                List<String> codeList = Utils.getListFromStr(proCategory.getLongCode(), ".");
                if (codeList != null && codeList.size() >= 3) {
                    // dim
                    String checkCategory = codeList.get(2);
                    // 识别授权码对应公司的大类
                    List<BaseProduct> baseProductList =
                            baseProductService.getSelectConditionByAuthorizedConfigV3(loginUser, checkCategory);
                    prepProductSearchInfo.setBaseProductList(baseProductList);
                    // 检测授权码关联的品牌是否有配置不显示无品牌 ->start
                    for (BaseProduct baseProduct : baseProductList) {
                        if (ProductModelConstant.STATUSSHOWWU_NOSHOW.intValue() == baseProduct.getStatusShowWu()
                        		&& baseProduct.getStatusShowWu() != null) {
                            isShowWuBoolean = false;
                            break;
                        }
                    }
                    // 检测授权码关联的品牌是否有配置不显示无品牌 ->end
                }
            }

            // 检测用户绑定的授权码是否有设计公司的授权码,如果有,则去掉授权码查询条件 ->start
            int count = authorizedConfigService.findCountWhereCompanyTypeIsDesignByUserId(loginUser.getId());
            if (count > 0) {
                // 有设计公司的授权码,则不需要授权码过滤条件
                prepProductSearchInfo.setBaseProductList(null);
            }


            // 设置,查不出产品,是否显示无品牌 ->start
            String isShowWu = Utils.getValue("app.product.searchProduct.isShowWu", "1");
            if (StringUtils.equals("1", isShowWu) && isShowWuBoolean) {
                prepProductSearchInfo.setShowWu(true);
            }
            // 设置,查不出产品,是否显示无品牌 ->end

            // 检测用户绑定的授权码是否有设计公司的授权码,如果有,则去掉授权码查询条件 ->end
        }
        // 设置授权码查询条件 ->end

        // 获取黑名单配置信息 ->start
        // [dib]
        Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
        if (blacklist != null && blacklist.size() > 0) {
            List<PrepProductSearchInfo> prepProductSearchInfoListForBlackList = sysDictionaryService.getbigTypeValueAndSmallTypeValueBySmallTypeValuekeyList(blacklist);
            prepProductSearchInfo.setPrepProductSearchInfoListForBlackList(prepProductSearchInfoListForBlackList);
        }

        // 获取黑名单配置信息 ->end

        // 查找"无品牌"品牌数据id ->start
        BaseBrand baseBrand = baseBrandService.findOneByBrandReferred(ProductModelConstant.BRAND_REFERRED_WU);
        Integer brandIdWuPinPai = 0;
        if (baseBrand != null) {
            brandIdWuPinPai = baseBrand.getId();
        }
        prepProductSearchInfo.setBrandIdWuPinPai(brandIdWuPinPai);
        // 查找"无品牌"品牌数据id ->end

        //userId ->start
        prepProductSearchInfo.setUserId(loginUser.getId());
        //userId ->end

        // 其他查询条件 ->start
        prepProductSearchInfo.setStart(start);
        prepProductSearchInfo.setLimit(limit);

        //添加异业联盟品牌过滤
        if("brand2c".equals(platformCode) || "mobile2c".equals(platformCode)){
//			List<Integer> brandIds = adjustBrandIds(productId);
            List<Integer> brandIds = getBrandIdsByProductId(productId);
            prepProductSearchInfo.setBrandIds(brandIds);
        }

        //添加平台过滤
        if (StringUtils.isNotEmpty(platformCode)) {
            BasePlatform basePlatform = basePlatformService.getBasePlatform(platformCode);
            if (basePlatform != null && basePlatform.getPlatformBussinessType() != null && basePlatform.getId() != null) {
                String platformProductTable = null;
                if ("2b".equals(basePlatform.getPlatformBussinessType())) {
                    platformProductTable = "platform2b_product_rel";
                }
                if ("2c".equals(basePlatform.getPlatformBussinessType())) {
                    platformProductTable = "platform2c_product_rel";
                }
                prepProductSearchInfo.setPlatformId(basePlatform.getId());
                prepProductSearchInfo.setPlatformProductTable(platformProductTable);
            }
        }
        // 其他查询条件 ->end
        // 设置查询条件 ->end

        // 设置缓存key
        ResponseEnvelope<CategoryProductResult> result = null;
        Map<Object, Object> keyMap = null;
        if (Utils.enableRedisCache()) {
            keyMap = this.getKeyMap(prepProductSearchInfo, categoryCode);
            result = CommonCacher.getAll(ModuleType.ProductCategoryRel, "searchProductV6", keyMap);
        }
        if (result == null || result.getDatalist().size() == 0) {
            // 查询 ->start
            Integer count = 0;
            List<CategoryProductResult> returnList = new ArrayList<CategoryProductResult>();
            startTime = new Date().getTime();

            // 识别是不是结构背景墙的主产品,如果走了背景墙组合主产品搜索的特殊逻辑,则不用走默认单品搜索逻辑
            boolean isBeijingMain = false;
            if (designPlanProduct != null) {
                isBeijingMain = this.getIsBeijingMain(designPlanProduct, prepProductSearchInfo.isBeijing());
            }

            // 添加天花相关过滤条件(布局标识) ->start
			/*Integer productSmallpoxIdentify = null;
			if(baimoBaseProductInit != null && baimoBaseProductInit.getProductSmallpoxIdentify() != null && baimoBaseProductInit.getProductSmallpoxIdentify().intValue() != 0) {
				productSmallpoxIdentify = baimoBaseProductInit.getProductSmallpoxIdentify();
			}
			prepProductSearchInfo.setProductSmallpoxIdentify(productSmallpoxIdentify);*/
            if (designPlanProduct != null) {
            	if(productTypeValue != null && 1 == productTypeValue) {
                /*if (StringUtils.equals("10", designPlanProduct.getRegionMark().trim()) && productTypeValue != null && 1 == productTypeValue) {*/
                    if (designPlan != null) {
                        Integer designTempletId = designPlan.getDesignTemplateId();
                        if (designTempletId != null) {
                            DesignTemplet designTemplet = designTempletService.get(designTempletId);
                            if (designTemplet != null) {
                            	
                                // 户型绘制新逻辑,如果来源于户型绘制,则多搜索出有材质的扣板天花update by huangsongbo 2018.2.5 ->start
                            	if(TempletStatuCode.DRAW.intValue() == designTemplet.getPutawayState()){
                            		// 上述判断条件表明该样板房来源户户型绘制
                            		prepProductSearchInfo.setFromDraw(true);
                            	}
                                // 户型绘制新逻辑,如果来源于户型绘制,则多搜索出有材质的扣板天花update by huangsongbo 2018.2.5 ->end
                            	
								/*String productSmallpoxIdentify = null;*/
								/*if(designTemplet.getSmallpoxIdentify() != null && designTemplet.getSmallpoxIdentify().intValue() != 0) {*/
								/*if(designTemplet.getSmallpoxIdentify() != null && !StringUtils.equals("0", designTemplet.getSmallpoxIdentify())) {
									productSmallpoxIdentify = designTemplet.getSmallpoxIdentify();
								}
								prepProductSearchInfo.setProductSmallpoxIdentify(productSmallpoxIdentify);*/
                            	// 只有主天花才需要加上布局标识搜索条件
                            	if(StringUtils.equals("10", designPlanProduct.getRegionMark().trim())) {
                            		prepProductSearchInfo.setIdentifyList(Utils.getIdentifyList(designTemplet.getSmallpoxIdentify()));
                            	}
                            }
                        }
                    }
                }
            }
            // 添加天花相关过滤条件(布局标识) ->end

			/*if(baseProductChecked.getStyleId() != null && baseProductChecked.getStyleId() > 0){*/
            if (isBeijingMain) {

                // 查出关联的设计方案产品(同组,比如三个门框背景墙)
                List<DesignPlanProduct> designPlanProductList = designPlanProductService.getGroupProductList(designPlanProduct.getPlanGroupId(), designPlanProduct.getPlanId());

                // 查询条件设置款式
				/*prepProductSearchInfo.setBeijingStyleId(styleId);*/

                // copy一个查询条件对象(用于其他附属墙查询count)
                PrepProductSearchInfo prepProductSearchInfoCopy = (PrepProductSearchInfo) prepProductSearchInfo.clone();

                if (designPlanProductList != null && designPlanProductList.size() > 0) {
                    for (DesignPlanProduct designPlanProductListItem : designPlanProductList) {

                        // 略过主墙(先检测所有附墙是不是可以搜出产品,再去搜主墙,如果有附墙不能搜出产品,则主墙搜索返回空list) ->start
                        if (getIsBeijingMain(designPlanProductListItem, true)) {
                            continue;
                        }
                        // 略过主墙(先检测所有附墙是不是可以搜出产品,再去搜主墙,如果有附墙不能搜出产品,则主墙搜索返回空list) ->end

                        // 设置全铺长度查询条件,查询count,如果count = 0;则返回空集合(目的是,三面墙,都要有执行的款式,主背景墙才能搜出产品(为了一次把三面墙全部替换成相同款式的产品))
                        // 获取对应白膜
                        Integer baimoId = designPlanProductListItem.getInitProductId();
                        if (baimoId != null) {
                            BaseProduct baseProductBaimo = baseProductService.get(baimoId);
                            if (baseProductBaimo == null) {
                                isBeijingMain = false;
                                break;
                            }

                            // 设置该白膜的全部长度查询条件
                            this.setProductLWHSearchCondition(prepProductSearchInfoCopy, baseProductBaimo);
                            prepProductSearchInfoCopy.setBaimoId(baseProductBaimo.getId());

                            // 查询count(如果count = 0,则意味着附属背景墙查不出同款式的产品,主背景墙返回空List)
                            int countAttach = 0;
								/*if(StringUtils.equals("new", oldOrNew)){*/
                            if (StringUtils.equals("new", style)) {
                                // 未加上搜索款式的逻辑
                                countAttach = prepProductSearchInfoService.getCount(prepProductSearchInfoCopy, loginUser.getId(), categoryCode);
                            } else {
                                // 未加上搜索款式的逻辑
                                countAttach = baseProductService.getCountRealTime(prepProductSearchInfoCopy, loginUser.getId(), categoryCode);
                            }

                            if (countAttach == 0) {
                                // 由于有附墙按款式和全铺长度搜索不出背景墙,则主墙也返回空list
                                return new ResponseEnvelope<CategoryProductResult>(count, returnList, productCategoryRel.getMsgId());
                            }

                        } else {
                            isBeijingMain = false;
                            break;
                        }
                    }

                    // 检测出附墙都能搜出产品,则查询主墙(按照款式和长宽高) ->start
						/*if(StringUtils.equals("new", oldOrNew)){*/
                    if (StringUtils.equals("new", style)) {
                        count = prepProductSearchInfoService.getCount(prepProductSearchInfo, loginUser.getId(), categoryCode);
                        if (count > 0) {
                            returnList = prepProductSearchInfoService.getProductIdListV2(prepProductSearchInfo, loginUser.getId(), categoryCode);
                        }
                    } else {
                        count = baseProductService.getCountRealTime(prepProductSearchInfo, loginUser.getId(), categoryCode);
                        if (count > 0) {
                            returnList = baseProductService.getProductIdListV2RealTime(prepProductSearchInfo, loginUser.getId(), categoryCode);
                        }
                    }
                    // 检测出附墙都能搜出产品,则查询主墙(按照款式和长宽高) ->end

                } else {
                    isBeijingMain = false;
                }
            }
			/*}*/

            // 默认单品搜索逻辑 ->start
            if (!isBeijingMain) {
                // 考虑序列号过滤
                // 旧方法改造,整合到这个方法中
				/*if(StringUtils.equals("new", oldOrNew)){*/
                if (StringUtils.equals("new", style)) {
                    count = prepProductSearchInfoService.getCount(prepProductSearchInfo, loginUser.getId(), categoryCode);
                    if (count > 0) {
                        returnList = prepProductSearchInfoService.getProductIdListV2(prepProductSearchInfo, loginUser.getId(), categoryCode);
                    }
                } else {
                    count = baseProductService.getCountRealTime(prepProductSearchInfo, loginUser.getId(), categoryCode);
                    if (count > 0) {
                        returnList = baseProductService.getProductIdListV2RealTime(prepProductSearchInfo, loginUser.getId(), categoryCode);
                    }
                }
            }
            // 默认单品搜索逻辑 ->end


            // 获取需要自动携带白模产品的配置->start
            Map<String, AutoCarryBaimoProducts> autoCarryMap = this.getAutoCarryMap();
            // 获取需要自动携带白模产品的配置->end

            List<Integer> productIdsList = new ArrayList<>();
            for (CategoryProductResult categoryProductResult : returnList) {
                // 存储产品ID 用于获取天花信息
                if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(categoryProductResult.getProductTypeCode())) {
                    productIdsList.add(categoryProductResult.getProductId());
                }
                // 处理材质返回格式
                this.dealWithTextureInfo(categoryProductResult);
                categoryProductResult.setSearchLength(String.valueOf(stretchZoomLength));
                // 补充信息->start
                categoryProductResult.setModelProductId(categoryProductResult.getProductId());
                // 软硬装:0:软装;1:硬装
                String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1()==null?"0":categoryProductResult.getSmallTypeAtt1().trim();
                categoryProductResult.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1);
                // bgWall:是否是背景墙
                Integer bgWallValue = baseProductService.getBgWallValue(categoryProductResult.getProductTypeCode(), categoryProductResult.getProductSmallTypeCode());
                categoryProductResult.setBgWall(bgWallValue);
                // 产品附加属性->start
                Map<String, String> map = new HashMap<String, String>();
                map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
                categoryProductResult.setPropertyMap(map);
                // 产品附加属性->end
                // 自动携带白膜属性(未优化) ->start
                baseProductService.setBasicModelMap(categoryProductResult, autoCarryMap, map, designPlan, designPlanProduct, mediaType);
                // 自动携带白膜属性(未优化) ->end
                //标准、款式、尺寸代码回填
                categoryProductResult.setStyleId(styleId);
                categoryProductResult.setIsStandard(isStandard);
                categoryProductResult.setRegionMark(regionMark);
                categoryProductResult.setMeasureCode(measureCode);
                //产品需拉伸缩放的长度值
                Map<String,String> isStretchZoom = baseProductService.getStretchZoomLength(categoryProductResult.getProductSmallTypeCode());
                if (isStretchZoom != null && 0 < isStretchZoom.size()) {
                    if (0 == fullPaveLength) {
                        fullPaveLength = Utils.getIntValue(categoryProductResult.getProductLength());
                    }
                    categoryProductResult.setFullPaveLength(fullPaveLength);
                }
                // 补充信息->end
            }

            //新逻辑客户端用到的天花数据存储到单独VO里
            if (null != productIdsList && 0 < productIdsList.size()) {
                List<ProductCeilingVO> productCeilingVOList = baseProductService.getCeilingInfoByProductIds(productIdsList);
                for (CategoryProductResult categoryProductResult : returnList) {
                    for (ProductCeilingVO productCeilingVO : productCeilingVOList) {
                        if (categoryProductResult.getProductId().equals(productCeilingVO.getProductId())) {
                            productCeilingVO.setRegionMark(categoryProductResult.getRegionMark());
                            categoryProductResult.setProductCeilingVO(productCeilingVO);
                        }
                    }
                }
            }
            // 查询 ->end
            result = new ResponseEnvelope<CategoryProductResult>(count, returnList, productCategoryRel.getMsgId());
            if (Utils.enableRedisCache()) {
                if (keyMap == null) {
                    keyMap = this.getKeyMap(prepProductSearchInfo, categoryCode);
                }
                CommonCacher.addAll(ModuleType.ProductCategoryRel, "searchProductV6", keyMap, result);
            }
            Long endTimeDown = new Date().getTime();
        } else {
            Long endTimeDown = new Date().getTime();
        }
        return result;
    }

    /**
     * 定制衣柜白模可搜索出成品衣柜（waca）和定制衣柜（dyca）,只有定制衣柜（dyca）按照尺寸范围搜索缩放；成品衣柜（waca）不用按照尺寸搜索缩放
     * @author xiaoxc_20171122
     */
    private void getStretchZoomFilterCondition(PrepProductSearchInfo prepProductSearchInfo,
    		List<Integer> smallTypeValueList, BaseProduct baseProductProp) {
    	if (null == baseProductProp) {
    		return ;
    	}
    	if (Lists.isNotEmpty(smallTypeValueList)) {
           prepProductSearchInfo.setProductInfoFilter(true);
           List<ProductSearchInfoModel> productSearchInfoModelList = new ArrayList<>();
           for (Integer smallValue : smallTypeValueList) {
               ProductSearchInfoModel productSearchInfoModel = new ProductSearchInfoModel();
               productSearchInfoModel.setSmallTypeValue(smallValue);
               //判断互搜的小分类里是否需要伸缩过滤
               Map<String, String> stretchMap = baseProductService.getStretchZoomLength(prepProductSearchInfo.getBigTypeValue(), smallValue);
               // 设置长高查询条件
               int stretchZoomLength = Utils.getIntValue(stretchMap.get(ProductModelConstant.STRETCH_LENGTH));
               String isHeightFilter = stretchMap.get(ProductModelConstant.IS_HEIGHT_FILTER);
               String productHeight = null;
               if (stretchMap != null && stretchMap.size() > 0) {
                   if (ProductModelConstant.HEIGHT_FILTER_VALUE_YES.equals(isHeightFilter)) {
                       productHeight = baseProductProp.getProductHeight();
                   }
                   productSearchInfoModel.setProductHeight(StringUtils.isEmpty(productHeight) ? null : Integer.valueOf(productHeight));

                   Integer beijingLengthStart = 0;
                   Integer beijingLengthEnd = 0;
                   if (StringUtils.isNotBlank(baseProductProp.getFullPaveLength())) {
                       Integer fullPaveLength = Integer.valueOf(baseProductProp.getFullPaveLength());
                       beijingLengthStart = fullPaveLength - stretchZoomLength + 1;
                       beijingLengthEnd = fullPaveLength + stretchZoomLength;
                   } else {
                       if (baseProductProp != null && !baseProductProp.getProductCode().startsWith("baimo_")) {
                           Integer fullPaveLength = Integer.valueOf(baseProductProp.getProductLength());
                           beijingLengthStart = fullPaveLength - stretchZoomLength + 1;
                           beijingLengthEnd = fullPaveLength + stretchZoomLength;
                       } else {
                           beijingLengthStart = 0;
                       }
                   }
                   productSearchInfoModel.setProductLengthStart(beijingLengthStart);
                   productSearchInfoModel.setProductLengthEnd(beijingLengthEnd);
               }
               productSearchInfoModelList.add(productSearchInfoModel);
           }
           prepProductSearchInfo.setProductInfoModelList(productSearchInfoModelList);
       }
    }


    /*解析固定格式字符串*/
    private Map<String, String> readFileDesc(String fileDesc) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(fileDesc)) {
            String[] strs = fileDesc.split(";");
            for (String str : strs) {
                if (str.split(":").length == 2) {
                    map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
                }
            }
        }
        return map;
    }

    /**
     * 衣柜、浴室柜、书柜增加按照白模全铺长度搜索和缩放功能  >5 <5
     *
     * @param prepProductSearchInfo
     * @param fullPaveLength
     */
    public String shrink(PrepProductSearchInfo prepProductSearchInfo, String fullPaveLength, String shrinkLengthStr) {
        if (prepProductSearchInfo == null || StringUtils.isEmpty(fullPaveLength)
                || StringUtils.isEmpty(shrinkLengthStr) || !StringUtils.isNumeric(shrinkLengthStr)) {
            return "";
        }
        int shrinkLength = Integer.parseInt(shrinkLengthStr);
        prepProductSearchInfo.setShrink(true);
        int length = Integer.parseInt(fullPaveLength);
        int lengthStart = 0;
        int lengthEnd = length + shrinkLength;
        if (length > shrinkLength) {
            lengthStart = length - shrinkLength;
        }
        prepProductSearchInfo.setBeijingLengthStart(lengthStart);
        prepProductSearchInfo.setBeijingLengthEnd(lengthEnd);
        return fullPaveLength;
    }


    /**
     * 设置背景墙长宽高查询条件
     *
     * @param prepProductSearchInfo
     * @param baseProductBaimo
     * @author huangsongbo
     */
    private void setProductLWHSearchCondition(PrepProductSearchInfo prepProductSearchInfo,
                                              BaseProduct baseProductBaimo) {

        // 参数验证 ->start
        if (prepProductSearchInfo == null) {
            return;
        }
        if (baseProductBaimo == null) {
            return;
        }
        // 参数验证 ->end
        // 设置长高查询条件(copy) ->start
        String beijingHeightStr = baseProductBaimo.getProductHeight();
        String beijingLengthStr = baseProductBaimo.getProductLength();
        prepProductSearchInfo.setBeijingHeight(StringUtils.isNotBlank(beijingHeightStr) ? Integer.valueOf(beijingHeightStr) : null);
        Integer beijingLengthStart = 0;
        Integer beijingLengthEnd = 0;
        if (StringUtils.isNotBlank(baseProductBaimo.getFullPaveLength())) {
            Integer fullPaveLength = Integer.valueOf(baseProductBaimo.getFullPaveLength());
            Integer beijingRange = Integer.valueOf(Utils.getValue("app.filter.stretch.length", "10"));
            beijingLengthStart = fullPaveLength - beijingRange + 1;
            beijingLengthEnd = fullPaveLength + beijingRange;
        } else {
            beijingLengthStart = 0;
            beijingLengthEnd = StringUtils.isNotBlank(beijingLengthStr) ? Integer.valueOf(beijingLengthStr) : null;
        }
        prepProductSearchInfo.setBeijingLengthStart(beijingLengthStart);
        prepProductSearchInfo.setBeijingLengthEnd(beijingLengthEnd);
        // 设置长高查询条件(copy) ->end
    }

    /**
     * 判断是否是背景墙&&是否是背景墙组合&&是否是背景墙组合主产品
     *
     * @param designPlanProduct
     * @param isBeijing         是否是背景墙
     * @return
     * @author huangsongbo
     */
    private boolean getIsBeijingMain(DesignPlanProduct designPlanProduct, boolean isBeijing) {
        // 参数验证 ->start
        if (designPlanProduct == null) {
            return false;
        }
        // 参数验证 ->end
        Integer isMainStructureProduct = designPlanProduct.getIsMainStructureProduct();
        Integer isGroupReplaceWay = designPlanProduct.getIsGroupReplaceWay();
        if (
                designPlanProduct.getProductGroupId() != null && 0 != designPlanProduct.getProductGroupId().intValue()
                        && isBeijing
                        && isMainStructureProduct != null && 1 == isMainStructureProduct.intValue() && isGroupReplaceWay != null && 1 == isGroupReplaceWay.intValue()
                ) {
            return true;
        }
        return false;
    }

    private Map<Object, Object> getKeyMap(PrepProductSearchInfo prepProductSearchInfo, String categoryCode) {
        Map<Object, Object> map = new HashMap<>();
        if (prepProductSearchInfo.getProductStatus() != null)
            map.put("ProductStatus", prepProductSearchInfo.getProductStatus());

        if (prepProductSearchInfo.getProductModelOrBrandName() != null)
            map.put("ProductModelOrBrandName", prepProductSearchInfo.getProductModelOrBrandName());

        if (prepProductSearchInfo.getHouseTypeList() != null)
            map.put("HouseTypeList", prepProductSearchInfo.getHouseTypeList());

        if (prepProductSearchInfo.getBigTypeValue() != null)
            map.put("BigTypeValue", prepProductSearchInfo.getBigTypeValue());

        if (categoryCode != null)
            map.put("CategoryCode", categoryCode);

        if (prepProductSearchInfo.getSpaceCommonId() != null)
            map.put("SpaceCommonId", prepProductSearchInfo.getSpaceCommonId());

        if (prepProductSearchInfo.getDesignTempletId() != null)
            map.put("DesignTempletId", prepProductSearchInfo.getDesignTempletId());

        if (prepProductSearchInfo.getBaimoId() != null)
            map.put("BaimoId", prepProductSearchInfo.getBaimoId());

        if (prepProductSearchInfo.getBaimoProductId() != null)
            map.put("BaimoProductId", prepProductSearchInfo.getBaimoProductId());

        map.put("HasOrderProps", prepProductSearchInfo.isHasOrderProps());

        if (prepProductSearchInfo.getProductId() != null)
            map.put("ProductId", prepProductSearchInfo.getProductId());

        if (prepProductSearchInfo.getExcludeSmallTypeValue() != null)
            map.put("ExcludeSmallTypeValue", prepProductSearchInfo.getExcludeSmallTypeValue());

        if (prepProductSearchInfo.getSmallTypeValue() != null)
            map.put("SmallTypeValue", prepProductSearchInfo.getSmallTypeValue());

        if (prepProductSearchInfo.getProductLength() != null)
            map.put("ProductLength", prepProductSearchInfo.getProductLength());

        if (prepProductSearchInfo.getProductHeight() != null)
            map.put("ProductHeight", prepProductSearchInfo.getProductHeight());

        if (prepProductSearchInfo.getLessProductLength() != null)
            map.put("LessProductLength", prepProductSearchInfo.getLessProductLength());

        if (prepProductSearchInfo.getLessProductWidth() != null)
            map.put("LessProductWidth", prepProductSearchInfo.getLessProductWidth());

        if (prepProductSearchInfo.getBaseProductList() != null)
            map.put("BaseProductList", prepProductSearchInfo.getBaseProductList());

        if (prepProductSearchInfo.getExcludeSmallTypeValue() != null)
            map.put("ExcludeSmallTypeValue", prepProductSearchInfo.getExcludeSmallTypeValue());

        if (prepProductSearchInfo.getBaimoIdList() != null)
            map.put("BaimoIdList", prepProductSearchInfo.getBaimoIdList());

        map.put("Beijing", prepProductSearchInfo.isBeijing());

        if (prepProductSearchInfo.getBeijingHeight() != null)
            map.put("BeijingHeight", prepProductSearchInfo.getBeijingHeight());

        if (prepProductSearchInfo.getBeijingLengthStart() != null)
            map.put("beijingLengthStart", prepProductSearchInfo.getBeijingLengthStart());

        if (prepProductSearchInfo.getBeijingLengthEnd() != null)
            map.put("beijingLengthStart", prepProductSearchInfo.getBeijingLengthEnd());

        map.put("Tianh", prepProductSearchInfo.isTianh());

        if (prepProductSearchInfo.getStart() != null)
            map.put("Start", prepProductSearchInfo.getStart());

        if (prepProductSearchInfo.getLimit() != null)
            map.put("Limit", prepProductSearchInfo.getLimit());

        if (prepProductSearchInfo.getMeasureCode() != null)
            map.put("measureCode", prepProductSearchInfo.getMeasureCode());

        if (prepProductSearchInfo.getRegionMark() != null)
            map.put("regionMark", prepProductSearchInfo.getRegionMark());

        if (prepProductSearchInfo.getStyleId() != null)
            map.put("styleId", prepProductSearchInfo.getStyleId());

        // add by huangsongbo 2018.1.9,由于app端单品搜索时,内部用户可以选择是否不显示测试中产品,防止缓存key重复
        if (StringUtils.isNotEmpty(prepProductSearchInfo.getIsInternalUser())) {
            map.put("isInternalUser", prepProductSearchInfo.getIsInternalUser());
        }

        return map;
    }

    /**
     * 得到空间类型搜索条件
     *
     * @param houseType
     * @param baimoBaseProductInit
     * @return
     * @author huangsongbo
     */
    private List<String> getHouseTypeSearchCondition(String houseType, BaseProduct baimoBaseProductInit) {
        if (StringUtils.isNotBlank(houseType)) {
            //找到houseType对应的productHouseType
            SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
            sysDictionarySearch.setAtt1(houseType);
            sysDictionarySearch.setType("productHouseType");
            List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
            List<String> productHouseTypeList = new ArrayList<String>();
            List<String> productHouseTypeList3 = new ArrayList<String>();
            if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
                for (SysDictionary sysDictionary : sysDictionaryList) {
                    productHouseTypeList.add("" + sysDictionary.getValue());
                    productHouseTypeList3.add("" + sysDictionary.getValue());
                }
            }
            if (baimoBaseProductInit != null) {
                //和对应初始化白膜的houseTypeValues取交集
                //eg:进入客餐厅,如果点击的灯的初始化白膜是客厅,则只能搜出客厅的等
                String productHouseTypesStr = baimoBaseProductInit.getHouseTypeValues();
                if (StringUtils.isNotBlank(productHouseTypesStr)) {
                    List<String> productHouseTypeList2 = Utils.getListFromStr(productHouseTypesStr);
                    productHouseTypeList.retainAll(productHouseTypeList2);
                }
            }
            if (productHouseTypeList.size() > 0) {
                return productHouseTypeList;
            } else {
                return productHouseTypeList3;
            }
        }
        return null;
    }

    private Map<String, Object> getSpecialProductTypeMap(Integer bigTypeValue, Integer smallTypeValue) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String specialProductTypeStr = Utils.getValue("special.productType", "");
        if (StringUtils.isNoneBlank(specialProductTypeStr)) {
            JSONArray jsonArray = JSONArray.fromObject(specialProductTypeStr);
            @SuppressWarnings({"deprecation", "unchecked"})
            List<SpecialProductTypeInfo> specialProductTypeInfoList = JSONArray.toList(jsonArray, SpecialProductTypeInfo.class);
            // 取出所有大类查询其对应的value
            List<String> bigTypeList = new ArrayList<String>();
            for (SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList) {
                bigTypeList.add(specialProductTypeInfo.getProductTypeKey());
            }
            // 转换成bigType:value的map
            Map<Integer, String> bigTypeMap = sysDictionaryService.getValueValueKeyMapByTypeAndValueKey("productType", bigTypeList);
            if (bigTypeMap.containsKey(bigTypeValue)) {
                // 找到对应大类配置
                String bigType = bigTypeMap.get(bigTypeValue);
                for (SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList) {
                    if (StringUtils.equals(bigType, specialProductTypeInfo.getProductTypeKey())) {
                        String smallTypeStr = specialProductTypeInfo.getProductSmallTypeKeys();
                        List<String> smallTypeList = Utils.getListFromStr(smallTypeStr);
                        List<Integer> smallTypeValueList = sysDictionaryService.getValueByTypeAndValueKeylist(bigType, smallTypeList);
                        if (smallTypeValueList.indexOf(smallTypeValue) == -1) {
                            // 确定为不包含这些小类
                            returnMap.put("notIn", smallTypeValueList);
                        } else {
                            // 确定为只要改小类
                            returnMap.put("in", smallTypeValue);
                        }
                        break;
                    }
                }
            } else {
                // 在配置范围外,不做处理

            }
        }
        return returnMap;
    }

    private void setBgWallCondition(PrepProductSearchInfo prepProductSearchInfo, DesignPlanProduct designPlanProduct) {
        if (designPlanProduct == null)
            return;
        String BindParentProductIdsStr = designPlanProduct.getBindParentProductId();
        List<Integer> bindParentProductIdList = Utils.getIntegerListFromStringList(BindParentProductIdsStr);
        if (bindParentProductIdList == null || bindParentProductIdList.size() == 0)
            return;
        // 检测绑定点上的产品是否还在
        List<Integer> idList = new ArrayList<Integer>();
        for (Integer id : bindParentProductIdList) {
            DesignPlanProduct designPlanProductBind = designPlanProductService.findIdByInitProductIdAndPlanId(id, designPlanProduct.getPlanId());
            if (designPlanProductBind == null)
                idList.add(id);
        }
        if (idList.size() == 0)
            return;
        List<Integer> smallTypeValueList = new ArrayList<Integer>();
        for (Integer productId : idList) {
            BaseProduct baseProductBaimo = baseProductService.get(productId);
            if (baseProductBaimo == null)
                continue;
            Integer smallTypeValue = sysDictionaryService.getProductValueByBaimoValue
                    (Integer.valueOf(baseProductBaimo.getProductTypeValue()), baseProductBaimo.getProductSmallTypeValue());
            smallTypeValueList.add(smallTypeValue);
        }
        List<Integer> excludeSmallTypeValueList = prepProductSearchInfo.getExcludeSmallTypeValue();
        if (excludeSmallTypeValueList != null && excludeSmallTypeValueList.size() > 0) {
            excludeSmallTypeValueList.removeAll(smallTypeValueList);
            // 设置排序(比如smallTypeValueList = 1 则小类 =1 的排前面)
            prepProductSearchInfo.setSortSmallTypeValueList(smallTypeValueList);
            prepProductSearchInfo.setExcludeSmallTypeValue(excludeSmallTypeValueList);
        }
        List<Integer> baimoIdList = new ArrayList<>();
        if (prepProductSearchInfo.getBaimoId() != null)
            baimoIdList.add(prepProductSearchInfo.getBaimoId());
        baimoIdList.addAll(idList);
        prepProductSearchInfo.setBaimoIdList(baimoIdList);
    }

    /**
     * 获取自动携带白模产品配置
     * @return
     */
    @Override
    public Map<String, AutoCarryBaimoProducts> getAutoCarryMap() {
        Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
        String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
                "design.designPlan.autoCarryBaimoProducts", "");
        if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
            JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
            try {
                @SuppressWarnings("unchecked")
                List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
                        .toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
                if (autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0) {
                    for (AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs) {
                        autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(),
                                autoCarryBaimoProductsConfig);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("获取自动携带白模产品配置异常！");
            }
        }
        return autoCarryMap;
    }

    /**
     * 处理材质信息,填装材质信息(默认材质)
     *
     * @param categoryProductResult
     */
    @SuppressWarnings("unchecked")
    @Override
    public void dealWithTextureInfo(CategoryProductResult categoryProductResult) {
        List<SplitTextureDTO> splitTextureDTOList = new ArrayList<>();
        Integer isSplit = 0;
        if (StringUtils.isNotBlank(categoryProductResult.getSplitTexturesInfoStr())) {
            // 多材质产品
            Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(categoryProductResult.getProductId(), categoryProductResult.getSplitTexturesInfoStr(), "choose");
            isSplit = (Integer) map.get("isSplit");
            splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
        } else {
            if (StringUtils.isNotBlank(categoryProductResult.getMaterialPicId())) {
                //TODO: 材质球文件路径
//				ResFile textureBallFile = null;
                ResModel textureBallModel = null;
                String materialPath = "";
                ResPic normalPic = null;
                String normalParam = "";
                String normalPath = "";
                ResTexture resTexture = resTextureService.get(Integer.valueOf(categoryProductResult.getMaterialPicId()));
                if (resTexture != null && resTexture.getTextureBallFileId() != null) {
                    textureBallModel = resModelService.get(resTexture.getTextureBallFileId());
                    if (textureBallModel != null) {
                        //materialPath = Utils.getValue("app.resources.url", "")+textureBallFile.getFilePath();
                        materialPath = textureBallModel.getModelPath();
						/*materialPath = Utils.dealWithPath(materialPath, "linux");*/
                    }
                }
                if (resTexture != null && resTexture.getNormalPicId() != null) {
                    normalParam = resTexture.getNormalParam();
                    normalPic = resPicService.get(resTexture.getNormalPicId());
                    if (normalPic != null) {
                        //normalPath = Utils.getValue("app.resources.url", "") + normalPic.getPicPath();
                        normalPath = normalPic.getPicPath();
						/*normalPath = Utils.dealWithPath(normalPath, "linux");*/
                    }
                }
                // 单材质产品
                SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
                SplitTextureDTO.ResTextureDTO resTextureDTO
                        = splitTextureDTO.new ResTextureDTO(
                        Integer.valueOf(categoryProductResult.getMaterialPicId()),
                        categoryProductResult.getResTexturePath(),
                        categoryProductResult.getTextureAttrValue(),
                        StringUtils.isNotBlank(categoryProductResult.getTextureHeight()) ? (int) Double.parseDouble(categoryProductResult.getTextureHeight()) : null,
                        StringUtils.isNotBlank(categoryProductResult.getTextureWidth()) ? (int) Double.parseDouble(categoryProductResult.getTextureWidth()) : null,
                        categoryProductResult.getLaymodes(), materialPath, normalParam, normalPath);
                resTextureDTO.setKey("1");
                resTextureDTO.setProductId(categoryProductResult.getProductId());
                List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
                resTextureDTOList.add(resTextureDTO);
                splitTextureDTO.setList(resTextureDTOList);
                splitTextureDTOList.add(splitTextureDTO);
            }
        }
        categoryProductResult.setIsSplit(isSplit);
        categoryProductResult.setSplitTexturesChoose(splitTextureDTOList);
    }

    /**
     * 得到单品搜索相关的配置(同时显示多个小类)
     *
     * @return
     * @author huangsongbo
     */
    public static Map<String, Object> getshowMoreSmallTypeMap() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String showMoreSmallType = Utils.getValue(
                "product.searchProduct.showMoreSmallType",
                "[{\"checkType\":\"yaox\",\"showType\":\"yaox,qiangz\"},{\"checkType\":\"bodx\",\"showType\":\"bodx,diz\"},{\"checkType\":\"tijx\",\"showType\":\"tijx,diz\"},{\"checkType\":\"dians,shaf,cant\",\"showType\":\"dians,shaf,cant\"}]");
        if (StringUtils.isBlank(showMoreSmallType)) {
            return null;
        }
        JSONArray showMoreSmallTypeJson = JSONArray.fromObject(showMoreSmallType);
        // product.searchProduct.showMoreSmallType=[{"checkType":"chufm,yangtm","showType":"chufm,yangtm,weisjm"}]
        Map<String, List<String>> showMoreSmallTypeInfoMap = new HashMap<String, List<String>>();
        // chufm,yangtm
        List<String> keyList = new ArrayList<String>();
        // chufm,yangtm
        List<String> valueList = new ArrayList<String>();
        if (showMoreSmallTypeJson != null && showMoreSmallTypeJson.size() > 0) {
            for (int i = 0; i < showMoreSmallTypeJson.size(); i++) {
                JSONObject jsonObject = showMoreSmallTypeJson.getJSONObject(i);
                String checkType = jsonObject.getString("checkType");
                if (StringUtils.isBlank(checkType)) {
                    continue;
                }
                String showType = jsonObject.getString("showType");
                List<String> checkTypeList = Utils.getListFromStr(checkType);
                List<String> showTypeList = Utils.getListFromStr(showType);
                keyList.removeAll(checkTypeList);
                keyList.addAll(checkTypeList);
                valueList.removeAll(showTypeList);
                valueList.addAll(showTypeList);
                for (String checkTypeItem : checkTypeList) {
                    if (showMoreSmallTypeInfoMap.containsKey(checkTypeItem)) {
                        // 更新
                        List<String> showTypeListUpdate = new ArrayList<String>();
                        showTypeListUpdate.addAll(showMoreSmallTypeInfoMap.get(checkTypeItem));
                        showTypeListUpdate.removeAll(showTypeList);
                        showTypeListUpdate.addAll(showTypeList);
                        showMoreSmallTypeInfoMap.put(checkTypeItem, showTypeListUpdate);
                    } else {
                        // 添加新元素
                        showMoreSmallTypeInfoMap.put(checkTypeItem, showTypeList);
                    }
                }
            }
        }

        // 记录diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线) ->start
        // diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线)
        // valueList和keyList差集(决定搜索是否看白膜,比如腰线能搜出地砖,地砖不能搜出腰线,则,地砖产品搜索看白膜)
        List<String> differenceList = new ArrayList<String>();
        Map<String, List<String>> differenceListMap = new HashMap<String, List<String>>();
		/*List<String> infoList = new ArrayList<String>();*/
        for (String key : showMoreSmallTypeInfoMap.keySet()) {
            for (String str : showMoreSmallTypeInfoMap.get(key)) {
				/*infoList.add(str + ":" + key);*/
                if (showMoreSmallTypeInfoMap.containsKey(str)) {
                    // diz有配置,看看diz能不能搜出tijx
                    if (showMoreSmallTypeInfoMap.get(str).indexOf(key) != -1) {
                        // diz能搜出tijx

                    } else {
                        // diz不能搜出tijx
                        if (differenceListMap.containsKey(str)) {
                            differenceListMap.get(str).remove(key);
                            differenceListMap.get(str).add(key);
                        } else {
                            List<String> list = new ArrayList<String>();
                            list.add(key);
                            differenceListMap.put(str, list);
                        }
                    }
                } else {
                    // 没有diz的配置
                    if (differenceListMap.containsKey(str)) {
                        differenceListMap.get(str).remove(key);
                        differenceListMap.get(str).add(key);
                    } else {
                        List<String> list = new ArrayList<String>();
                        list.add(key);
                        differenceListMap.put(str, list);
                    }
                }
            }
        }
        // 记录diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线) ->end
        returnMap.put("differenceListMap", differenceListMap);
        returnMap.put("differenceList", differenceList);
        returnMap.put("showMoreSmallTypeInfoMap", showMoreSmallTypeInfoMap);
        returnMap.put("keyList", keyList);
        returnMap.put("valueList", valueList);
        return returnMap;
    }

    //获取联盟品牌id
    private List<Integer> adjustBrandIds(Integer productId) {
        Integer companyId = baseProductService.getProductCompanyId(productId);
        List<Integer> companyIds = companyUnionService.getAllCompanyId(companyId);
        if (companyIds == null || companyIds.size() <= 0) {
            companyIds = Collections.singletonList(companyId);
        }
        List<Integer> brandIds = new ArrayList<>();
        for (Integer id : companyIds) {
            //查询公司品牌
            brandIds.addAll(baseBrandService.queryBrandListByCompanyId(id));
        }
        return brandIds;
    }

    /**
     * 获取所有联盟品牌ID
     *
     * @param productId
     * @return
     */
    private List<Integer> getBrandIdsByProductId(Integer productId) {
        List<Integer> brandIds = baseBrandService.getBrandIdsByProductId(productId);
        if (brandIds.size() == 0) {
            brandIds = baseBrandService.getProductOwnBrandIds(productId);
        }
        return brandIds;
    }

	@Override
	public List<SplitTextureDTO> getSplitTexturesChooseForSpellingflower(String parquetTextureIds, Integer productId) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(parquetTextureIds)) {
			logger.error(logPrefix + "StringUtils.isEmpty(parquetTextureIds) = true");
			return null;
		}
		// 参数验证 ->end
		
		List<SplitTextureDTO> splitTextureDTOList = new ArrayList<SplitTextureDTO>();
		List<Integer> textureIdList = null;
		try {
			textureIdList = Utils.getIntegerListFromStringList(parquetTextureIds);
		}catch (Exception e) {
			logger.error(logPrefix + "材质信息格式错误; parquetTextureIds = {}", parquetTextureIds);
			logger.error(e.toString());
			return null;
		}
		
		if(Lists.isNotEmpty(textureIdList)) {
			textureIdList.forEach(item -> splitTextureDTOList.add(resTextureService.getSplitTextureDTOById(item, productId)));
		}
		
		return splitTextureDTOList;
	}

}
