package com.sandu.search.storage.product;

import com.google.gson.reflect.TypeToken;
import com.sandu.search.common.constant.RedisConstant;
import com.sandu.search.common.constant.SystemDictionaryType;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.entity.elasticsearch.po.ProductCategoryPo;
import com.sandu.search.entity.elasticsearch.po.metadate.SystemDictionaryPo;
import com.sandu.search.entity.product.dto.ProductTypeCategory;
import com.sandu.search.entity.product.vo.ProductCategoryVo;
import com.sandu.search.service.redis.RedisService;
import com.sandu.search.storage.system.SystemDictionaryMetaDataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * 产品分类元数据存储
 *
 * @date 20171213
 * @auth pengxuangang
 */
@Component
public class ProductCategoryMetaDataStorage {

    //最后更新时间
    private static long lastUpdateTime = System.currentTimeMillis();

    private final RedisService redisService;
    private final SystemDictionaryMetaDataStorage systemDictionaryMetaDataStorage;

    @Autowired
    public ProductCategoryMetaDataStorage(RedisService redisService, SystemDictionaryMetaDataStorage systemDictionaryMetaDataStorage) {
        this.redisService = redisService;
        this.systemDictionaryMetaDataStorage = systemDictionaryMetaDataStorage;
    }

    //产品分类Map元数据
    private Map<String, String> productCategoryPoMap = null;
    //产品父子分类关系Map元数据<parentCategoryId, List<childrenCategoryId>>
    private Map<String, String> parentChildrenCategoryMap = null;
    //产品分类编码找产品分类ID
    private Map<String, String> categoryCodeMap = null;
    //产品分类长编码
    private List<String> categoryLongCodeList = null;

    //获取Map数据方法兼容(时间不能大于5分钟)
    private String getMap(String mapName, String keyName) {
        //更新数据
        if (null == productCategoryPoMap || 0 >= productCategoryPoMap.size()
                || null == parentChildrenCategoryMap || 0 >= parentChildrenCategoryMap.size()
                || null == categoryCodeMap || 0 >= categoryCodeMap.size()
                || System.currentTimeMillis() - lastUpdateTime > 5*60*1000) {
            productCategoryPoMap = redisService.getMap(RedisConstant.PRODUCT_CATEGORY_DATA);
            parentChildrenCategoryMap = redisService.getMap(RedisConstant.PRODUCT_PARENT_CHILD_CATEGORY_DATA);
            categoryCodeMap = redisService.getMap(RedisConstant.PRODUCT_CATEGORY_CODE_AND_ID_DATA);
            //更新时间
            lastUpdateTime = System.currentTimeMillis();
        }

        //获取数据
        if (RedisConstant.PRODUCT_CATEGORY_DATA.equals(mapName)) {
            return productCategoryPoMap.get(keyName);
        } else if (RedisConstant.PRODUCT_PARENT_CHILD_CATEGORY_DATA.equals(mapName)) {
            return parentChildrenCategoryMap.get(keyName);
        } else if (RedisConstant.PRODUCT_CATEGORY_CODE_AND_ID_DATA.equals(mapName)) {
            return categoryCodeMap.get(keyName);
        }

        return null;
    }

    //获取List数据方法兼容
    private List<String> getList(String listName) {
        //缓存模式
        if (null == categoryLongCodeList || 0 >= categoryLongCodeList.size()) {
            categoryLongCodeList = redisService.getList(RedisConstant.PRODUCT_CATEGORY_LONG_CODE_DATA);
        }

        if (RedisConstant.PRODUCT_CATEGORY_LONG_CODE_DATA.equals(listName)) {
            return categoryLongCodeList;
        }
        return null;
    }

    /**
     * 根据分类ID获取产品分类数据
     *
     * @return
     */
    public ProductCategoryPo getProductCategory(Integer productCategoryId) {
        if (null == productCategoryId || 0 == productCategoryId) {
            return null;
        }

        String productCategoryPoStr = getMap(RedisConstant.PRODUCT_CATEGORY_DATA, productCategoryId + "");
        if (StringUtils.isEmpty(productCategoryPoStr)) {
            return null;
        }
        return JsonUtil.fromJson(productCategoryPoStr, ProductCategoryPo.class);
    }

    /**
     * 根据分类Code获取分类ID
     *
     * @return
     */
    public String getProductCategoryCodeById(String productCategoryCode) {
        if (StringUtils.isEmpty(productCategoryCode)) {
            return null;
        }

        String productCategoryPoStr = getMap(RedisConstant.PRODUCT_CATEGORY_CODE_AND_ID_DATA, productCategoryCode);
        if (StringUtils.isEmpty(productCategoryPoStr)) {
            return null;
        }
        return productCategoryPoStr;
    }

    /**
     * 根据父分类ID获取子分类ID列表
     *
     * @return
     */
    public List<Integer> queryChildCategoryIdListByParentCategoryId(Integer parentCategoryId) {
        if (null == parentCategoryId || 0 == parentCategoryId) {
            return null;
        }

        String parentCategoryIdListStr = getMap(RedisConstant.PRODUCT_PARENT_CHILD_CATEGORY_DATA, parentCategoryId + "");
        if (StringUtils.isEmpty(parentCategoryIdListStr)) {
            return null;
        }
        return JsonUtil.fromJson(parentCategoryIdListStr, new TypeToken<List<Integer>>() {}.getType());
    }

    /**
     * 根据分类编码获取产品分类数据
     *
     * @return
     */
    public ProductCategoryPo getProductCategoryByCategoryCode(String productCategoryCode) {
        if (StringUtils.isEmpty(productCategoryCode)) {
            return null;
        }

        //获取分类ID
        String productCategoryId = getProductCategoryCodeById(productCategoryCode);
        if (StringUtils.isEmpty(productCategoryId)) {
            return null;
        }

        //获取分类对象
        return getProductCategory(Integer.parseInt(productCategoryId));
    }

    /**
     * 根据产品分类ID列表获取产品分类长编码列表
     *
     * @param categoryIdList 产品分类ID列表
     * @return
     */
    public List<String> queryProductCategoryLongCodeByCategoryIdList(List<Integer> categoryIdList) {

        if (null == categoryIdList || 0 == categoryIdList.size()) {
            return null;
        }

        List<String> categoryLongCodeList = new ArrayList<>(categoryIdList.size());
        categoryIdList.forEach(categoryId -> {
            if (null != categoryId && 0 != categoryId) {
                ProductCategoryPo productCategory = getProductCategory(categoryId);
                if (null != productCategory) {
                    String productCategoryLongCode = productCategory.getProductCategoryLongCode();
                    if (!StringUtils.isEmpty(productCategoryLongCode)) {
                        categoryLongCodeList.add(productCategoryLongCode);
                    }
                }
            }
        });

        return categoryLongCodeList;
    }

    /**
     * 根据产品分类ID获取所有分类名
     *
     * @param categoryIdList 分类IDList
     * @return
     */
    public List<String> queryProductCategoryIdByProductId(List<Integer> categoryIdList) {
        if (null == categoryIdList || 0 == categoryIdList.size()) {
            return null;
        }

        List<String> categoryNameList = new ArrayList<>(categoryIdList.size());
        categoryIdList.forEach(categoryId -> {
            if (null != categoryId && 0 != categoryId) {
                ProductCategoryPo productCategory = getProductCategory(categoryId);
                if (null != productCategory) {
                    categoryNameList.add(productCategory.getCategoryName());
                }
            }
        });

        return categoryNameList;
    }

    /**
     * 根据部分分类长编码名查询所有包含分类长编码名
     *
     * @param codeName
     * @return
     */
    public List<String> queryAllCategoryCodeByCodeName(String codeName) {

        if (StringUtils.isEmpty(codeName)) {
            return null;
        }

        //使用Map结构是为了去除重复--查询后合并
        Map<String, String> categoryCodeMap = new HashMap<>();

        String[] codeNameArr = codeName.split(",");

        List<String> longCodeList = getList(RedisConstant.PRODUCT_CATEGORY_LONG_CODE_DATA);
        if (null == longCodeList || 0 >= longCodeList.size()) {
            return null;
        }

        for (String nowCodeName : codeNameArr) {
            longCodeList.forEach(productCategoryLongCode -> {
                if (!StringUtils.isEmpty(productCategoryLongCode) && productCategoryLongCode.contains("." + nowCodeName + ".")) {
                    categoryCodeMap.put(productCategoryLongCode, productCategoryLongCode);
                }
            });
        }

        List<String> categoryCodeList = new ArrayList<>(categoryCodeMap.size());
        //转换为List
        if (0 < categoryCodeMap.size()) {
            for (Map.Entry<String, String> entries : categoryCodeMap.entrySet()) {
                categoryCodeList.add(entries.getKey());
            }
        }
        return categoryCodeList;
    }

    /**
     * 根据分类ID列表查询可用分类长编码列表
     * <p>
     * Step：
     * 1.根据3级分类查询出4级，5级所有分类
     * 2.将5级分类的兄弟节点分类去掉(仅保留已选择同级分类)
     * 3.查询公司可使用的分类
     * 4.去掉结果中不包含在公司分类中的分类(合并需要查询的分类和已配置分类)
     *
     * @param thirdLevelCategoryId                产品三级分类ID
     * @param fifthLevelCategoryIdConditionList   产品五级分类ID列表
     * @param companyAliveCategoryIdConditionList 公司有效分类ID列表
     * @return
     */
    public List<Integer> queryAliveCategoryLongCodeByCategoryIdList(int thirdLevelCategoryId, List<Integer> fifthLevelCategoryIdConditionList, List<Integer> companyAliveCategoryIdConditionList) {

        if (null == fifthLevelCategoryIdConditionList || 0 >= fifthLevelCategoryIdConditionList.size() || 0 == thirdLevelCategoryId) {
            return null;
        }

        //可用分类Id列表
        List<Integer> categoryIdList = new ArrayList<>();

        /***************************** 1.根据3级分类查询出4级，5级所有分类 ***************************************/
        //四级分类ID列表
        List<Integer> fourthLevelCategoryIdList = queryCategoryIdListByParentCategoryIdList(Collections.singletonList(thirdLevelCategoryId));
        //五级分类ID列表
        List<Integer> fifthLevelCategoryIdList = queryCategoryIdListByParentCategoryIdList(fifthLevelCategoryIdConditionList);

        //将结果全装入总结果集
        categoryIdList.add(thirdLevelCategoryId);
        categoryIdList.addAll(fourthLevelCategoryIdList);
        categoryIdList.addAll(fifthLevelCategoryIdList);

        /***************************** 2.将5级分类的兄弟节点分类去掉(仅保留已选择同级分类) ***************************************/
        fifthLevelCategoryIdConditionList.forEach(fifthLevelCategoryIdCondition -> {
            //当前选中条件父分类ID
            Integer parentCategoryId = getParentCategoryIdByChildrenCategoryId(fifthLevelCategoryIdCondition);
            //查询出父类下有哪些子分类
            List<Integer> childrenCategoryIdList = queryCategoryIdListByParentCategoryIdList(Collections.singletonList(parentCategoryId));
            //遍历子节点
            if (null != childrenCategoryIdList && 0 < childrenCategoryIdList.size()) {
                //遍历节点，将子节点全部删除
                childrenCategoryIdList.forEach(childrenCategoryId -> {
                    for (Integer categoryId : categoryIdList) {
                        if (categoryId.equals(childrenCategoryId)) {
                            categoryIdList.remove(categoryId);
                            break;
                        }
                    }
                });
                //增加参与条件子节点(这样做每次少遍历一遍列表,提高性能)
                categoryIdList.add(fifthLevelCategoryIdCondition);
            }
        });

        /***************************** 3.查询公司可使用的分类 ***************************************/
        List<Integer> companyAliveCategoryIdList = new ArrayList<>();
        if (null != companyAliveCategoryIdConditionList && 0 < companyAliveCategoryIdConditionList.size()) {
            //加入已配置父分类
            companyAliveCategoryIdList.addAll(companyAliveCategoryIdConditionList);
            //遍历父分类，加入子分类
            companyAliveCategoryIdConditionList.forEach(companyAliveCategoryIdCondition -> {
                //查询第一级分类
                List<Integer> firstCategoryIdList = queryCategoryIdListByParentCategoryIdList(Collections.singletonList(companyAliveCategoryIdCondition));
                if (null != firstCategoryIdList && 0 < firstCategoryIdList.size()) {
                    companyAliveCategoryIdList.addAll(firstCategoryIdList);
                    //查询第二级分类
                    List<Integer> twoCategoryIdList = queryCategoryIdListByParentCategoryIdList(firstCategoryIdList);
                    if (null != twoCategoryIdList && 0 < twoCategoryIdList.size()) {
                        companyAliveCategoryIdList.addAll(twoCategoryIdList);
                        //查询第三级分类
                        List<Integer> threeCategoryIdList = queryCategoryIdListByParentCategoryIdList(twoCategoryIdList);
                        if (null != threeCategoryIdList && 0 < threeCategoryIdList.size()) {
                            companyAliveCategoryIdList.addAll(threeCategoryIdList);
                            //查询第四级分类
                            List<Integer> fourCategoryIdList = queryCategoryIdListByParentCategoryIdList(threeCategoryIdList);
                            if (null != fourCategoryIdList && 0 < fourCategoryIdList.size()) {
                                companyAliveCategoryIdList.addAll(fourCategoryIdList);
                                //查询第五级分类
                                List<Integer> fiveCategoryIdList = queryCategoryIdListByParentCategoryIdList(fourCategoryIdList);
                                if (null != fiveCategoryIdList && 0 < fiveCategoryIdList.size()) {
                                    companyAliveCategoryIdList.addAll(fiveCategoryIdList);
                                }
                            }
                        }
                    }
                }

            });
        }

        /***************************** 4.返回去掉结果中不包含在公司分类中的分类(合并需要查询的分类和已配置分类) ***************************************/
        return categoryIdList.stream().filter(companyAliveCategoryIdList::contains).collect(toList());
    }

    /**
     * 根据分类ID查询分类层级
     *
     * @param categoryId 分类ID
     * @return
     */
    public Integer getCategoryLevelByCategoryId(Integer categoryId) {
        if (null == categoryId || 0 >= categoryId) {
            return null;
        }

        ProductCategoryPo productCategory = getProductCategory(categoryId);
        return null == productCategory ? null : productCategory.getCategoryLevel();
    }

    /**
     * 根据父分类ID查询子分类IDList
     *
     * @param parentCategoryIdList
     * @return
     */
    public List<Integer> queryCategoryIdListByParentCategoryIdList(List<Integer> parentCategoryIdList) {

        if (null == parentCategoryIdList || 0 >= parentCategoryIdList.size()) {
            return null;
        }

        List<Integer> childrenCategoryIdList = new ArrayList<>();
        parentCategoryIdList.forEach(parentCategoryId -> {
            List<Integer> childCategoryIdList = queryChildCategoryIdListByParentCategoryId(parentCategoryId);
            if (null != childCategoryIdList && 0 < childCategoryIdList.size()) {
                childrenCategoryIdList.addAll(childCategoryIdList);
            }
        });

        return childrenCategoryIdList;
    }

    /**
     * 根据子分类ID查询父分类ID
     *
     * @param childrenId 子分类ID
     * @return
     */
    private Integer getParentCategoryIdByChildrenCategoryId(Integer childrenId) {
        if (null == childrenId || 0 >= childrenId) {
            return null;
        }

        ProductCategoryPo productCategory = getProductCategory(childrenId);
        return null == productCategory ? null : productCategory.getParentCategoryId();
    }


    /**
     * 根据分类ID查询分类编码
     *
     * @param categoryIds 分类ID组
     * @return
     */
    public List<String> queryCategoryCodeByCategoryIds(String[] categoryIds) {

        if (null == categoryIds || 0 >= categoryIds.length) {
            return null;
        }

        //分类code列表
        List<String> categoryCodeList = new ArrayList<>(categoryIds.length);

        for (String categoryIdStr : categoryIds) {
            if (!StringUtils.isEmpty(categoryIdStr)) {
                //分类ID
                int categoryId = Integer.parseInt(categoryIdStr);
                ProductCategoryPo productCategory = getProductCategory(categoryId);
                if (null != productCategory) {
                    categoryCodeList.add(productCategory.getProductCategoryCode());
                }
            }
        }

        return categoryCodeList;
    }

    //根据三级分类ID列表获取4,5级分类对象(含3级)
    public List<ProductCategoryVo> queryChildrenNodeDataByThirdLevelCategoryIdList(List<Integer> thirdLevelCategoryIdList) {
        if (null == thirdLevelCategoryIdList || 0 >= thirdLevelCategoryIdList.size()) {
            return null;
        }

        List<ProductCategoryVo> thirdLevelProductCategoryVoList = new ArrayList<>(thirdLevelCategoryIdList.size());
        //遍历3级分类下级分类
        for (Integer thirdthCategoryId : thirdLevelCategoryIdList) {
            //3级分类
            ProductCategoryVo thirdLevelProductCategoryVo = new ProductCategoryVo();
            //获取当前对象
            ProductCategoryPo thirdLevelProductCategoryPo = getProductCategory(thirdthCategoryId);

            //3级小类
            String productSmallTypeValue = systemDictionaryMetaDataStorage.getValueByKey(thirdLevelProductCategoryPo.getProductCategoryCode());
            if (!StringUtils.isEmpty(productSmallTypeValue)) {
                thirdLevelProductCategoryVo.setProductSmallTypeValue(Integer.parseInt(productSmallTypeValue));
            }

            //4级分类ID
            List<Integer> fourthLevelCategoryIdList = queryCategoryIdListByParentCategoryIdList(Arrays.asList(thirdthCategoryId));

            //4级分类对象
            List<ProductCategoryVo> fourthLevelProductCategoryVoList = new ArrayList<>(thirdLevelCategoryIdList.size());
            if (null != fourthLevelCategoryIdList && 0 < fourthLevelCategoryIdList.size()) {
                for (Integer fourthLevelCategoryId : fourthLevelCategoryIdList) {
                    //4级分类
                    ProductCategoryVo fourthLevelProductCategoryVo = new ProductCategoryVo();
                    //获取当前对象
                    ProductCategoryPo fourthLevelProductCategoryPo = getProductCategory(fourthLevelCategoryId);
                    //5级分类ID
                    List<Integer> fifthLevelCategoryIdList = queryCategoryIdListByParentCategoryIdList(Arrays.asList(fourthLevelCategoryId));

                    //5级分类对象
                    List<ProductCategoryVo> fifthLevelProductCategoryVoList = new ArrayList<>(null == fifthLevelCategoryIdList ? 10 : fifthLevelCategoryIdList.size());
                    if (null != fifthLevelCategoryIdList && 0 < fifthLevelCategoryIdList.size()) {
                        for (Integer fifthLevelCategoryId : fifthLevelCategoryIdList) {
                            //5级分类
                            ProductCategoryVo fifthLevelProductCategoryVo = new ProductCategoryVo();
                            //获取当前对象
                            ProductCategoryPo fifthLevelProductCategoryPo = getProductCategory(fifthLevelCategoryId);

                            //装载值
                            fifthLevelProductCategoryVo.setCategoryName(fifthLevelProductCategoryPo.getCategoryName());
                            fifthLevelProductCategoryVo.setCategoryCode(fifthLevelProductCategoryPo.getProductCategoryLongCode());

                            //加入5级列表
                            fifthLevelProductCategoryVoList.add(fifthLevelProductCategoryVo);
                        }

                        //装载值
                        fourthLevelProductCategoryVo.setCategoryName(fourthLevelProductCategoryPo.getCategoryName());
                        fourthLevelProductCategoryVo.setCategoryCode(fourthLevelProductCategoryPo.getProductCategoryLongCode());
                        fourthLevelProductCategoryVo.setProductCategoryVoList(fifthLevelProductCategoryVoList);

                        //加入4级列表
                        fourthLevelProductCategoryVoList.add(fourthLevelProductCategoryVo);
                    }
                }

                //装载值
                thirdLevelProductCategoryVo.setCategoryCode(thirdLevelProductCategoryPo.getProductCategoryLongCode());
                thirdLevelProductCategoryVo.setCategoryName(thirdLevelProductCategoryPo.getCategoryName());
                thirdLevelProductCategoryVo.setProductCategoryVoList(fourthLevelProductCategoryVoList);



                //加入3级列表
                thirdLevelProductCategoryVoList.add(thirdLevelProductCategoryVo);
            }
        }

        return thirdLevelProductCategoryVoList;
    }

    /**
     * 通过Key查询系统字典数据
     *
     * @param valueKeyList Key列表
     * @return
     */
    public ProductTypeCategory querySystemDictionaryValueListByKeyList(List<String> valueKeyList) {
        if (null == valueKeyList || 0 >= valueKeyList.size()) {
            return null;
        }

        //值列表
        List<Integer> valueList = new ArrayList<>(valueKeyList.size());
        //分类编码
        String categoryCodeKey = null;

        for (String valueKey : valueKeyList) {
            if (!StringUtils.isEmpty(valueKey)) {
                //获取分类编码
                ProductCategoryPo productCategoryPo = getProductCategoryByCategoryCode(valueKey);
                //检查分类级别(3级的分类CODE直接对应小类VALUEKEY，不需要转换)
                switch (productCategoryPo.getCategoryLevel()) {
                    case 3:
                        //直接赋值
                        categoryCodeKey = valueKey;
                        break;
                    case 4:
                        //查询3级分类
                        ProductCategoryPo thereLevelProductCategoryPo = getProductCategory(productCategoryPo.getParentCategoryId());
                        categoryCodeKey = thereLevelProductCategoryPo.getProductCategoryCode();
                        break;
                    case 5:
                        //查询4级分类
                        ProductCategoryPo fourLevelProductCategoryPo = getProductCategory(productCategoryPo.getParentCategoryId());
                        //查询3级分类
                        ProductCategoryPo newThereLevelProductCategoryPo = getProductCategory(fourLevelProductCategoryPo.getParentCategoryId());
                        categoryCodeKey = newThereLevelProductCategoryPo.getProductCategoryCode();
                        break;
                }
                //获取Key值
                if (!StringUtils.isEmpty(categoryCodeKey)) {
                    String value = redisService.getMap(RedisConstant.SYSTEM_DICTIONARY_KEY_VALUE_DATA, categoryCodeKey);
                    if (!StringUtils.isEmpty(value)) {
                        valueList.add(Integer.parseInt(value));
                    }
                }
            }
        }

        return new ProductTypeCategory(categoryCodeKey, valueList);
    }

    /**
     * 通过产品编码获取产品大小类(List第一个是产品大类，第二个是小类)
     *
     * @param productCode 产品编码
     * @return
     */
    public List[] getProductTypeByProductCode(String productCode) {

        if (StringUtils.isEmpty(productCode)) {
            return null;
        }

        //结果集
        List[] productTypeListResult = new List[2];
        //大类集合
        List<Integer> productTypeList = new ArrayList<>(1);
        //小类集合
        List<Integer> productSmallTypeList = new ArrayList<>();

        List<SystemDictionaryPo> systemDictionaryList = systemDictionaryMetaDataStorage.querySystemDictionaryListByType(productCode);
        //若包含此编码的类型则是产品大类
        if (null != systemDictionaryList && 0 < systemDictionaryList.size()) {
            //查询出大类ID值
            //根据类型查询出字典数据
            List<SystemDictionaryPo> systemDictionaryPoList = systemDictionaryMetaDataStorage.querySystemDictionaryListByType(SystemDictionaryType.SYSTEM_DICTIONARY_TYPE_PRODUCTTYPE);
            if (null != systemDictionaryPoList && 0 < systemDictionaryPoList.size()) {
                for (SystemDictionaryPo systemDictionaryPo : systemDictionaryPoList) {
                    if (productCode.equals(systemDictionaryPo.getDictionaryKey())) {
                        //加入大类
                        productTypeList.add(systemDictionaryPo.getDictionaryValue());
                        break;
                    }
                }
            }
            //查询出小类集合
            for (SystemDictionaryPo systemDictionary : systemDictionaryList) {
                productSmallTypeList.add(systemDictionary.getDictionaryValue());
            }
        } else {
            //不包含的则是小类
            //根据分类code查询小类ID
            ProductTypeCategory productTypeCategory = querySystemDictionaryValueListByKeyList(Arrays.asList(productCode.split(",")));
            productSmallTypeList = productTypeCategory.getProductSmallTypeValueList();
            if (null == productSmallTypeList || 0 >= productSmallTypeList.size()) {
                return null;
            }

            //根据小类第一个值查询出大类
            String productCategoryCode = productTypeCategory.getProductCategoryCode();
            String dictionaryType = redisService.getMap(RedisConstant.SYSTEM_DICTIONARY_KEY_TYPE_DATA, productCategoryCode);
            if (!StringUtils.isEmpty(dictionaryType)) {
                //获取到小类的Type去反查大类
                List<SystemDictionaryPo> systemDictionaryPoList = systemDictionaryMetaDataStorage.querySystemDictionaryListByType(SystemDictionaryType.SYSTEM_DICTIONARY_TYPE_PRODUCTTYPE);
                for (SystemDictionaryPo parentSystemDictionaryPo : systemDictionaryPoList) {
                    //检查父类valuekey相同和子类类型是否
                    if (parentSystemDictionaryPo.getDictionaryKey().equals(dictionaryType)) {
                        productTypeList.add(parentSystemDictionaryPo.getDictionaryValue());
                        break;
                    }
                }
            }
        }

        //组合数据
        productTypeListResult[0] = productTypeList;
        productTypeListResult[1] = productSmallTypeList;

        return productTypeListResult;
    }
}