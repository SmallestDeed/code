package com.sandu.product.service;


import com.sandu.common.model.PageModel;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.product.model.*;
import com.sandu.product.model.search.BaseProductSearch;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.UserSO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseProductService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-产品库Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:37
 */
public interface BaseProductService {

    /**
     * 新增数据
     *
     * @param baseProduct
     * @return int
     */
    int add(BaseProduct baseProduct);

    /**
     * 更新数据
     *
     * @param baseProduct
     * @return int
     */
    int update(BaseProduct baseProduct);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseProduct
     */
    BaseProduct get(Integer id);

    /**
     * 所有数据
     *
     * @param baseProduct
     * @return List<BaseProduct>
     */
    List<BaseProduct> getList(BaseProduct baseProduct);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(BaseProductSearch baseProductSearch);

    /**
     * 分页获取数据
     *
     * @return List<BaseProduct>
     */
    List<BaseProduct> getPaginatedList(BaseProductSearch baseProducttSearch);

    /**
     * 根据产品名称和品牌ID查询产品
     *
     * @param baseProduct
     * @return
     */
    List<CategoryProductResult> selectProductByNameAndBrandId(BaseProduct baseProduct);

    Integer selectProductByNameAndBrandCount(BaseProduct baseProduct);

    Integer getDetailProduct(Map map);

    boolean add(BaseProduct baseProduct, String proAttIds, HttpServletRequest request);

    String getU3dModelId(String mediaType, BaseProduct baseProduct);

    /**
     * 获取是否是硬装
     *
     * @return
     */
    boolean isHard(BaseProduct baseProduct);

    /**
     * 通过品牌获取产品列表
     *
     * @param baseProduct
     * @return
     */
    List<CategoryProductResult> getBrandProductsList(BaseProduct baseProduct);

    /**
     * 通过品牌获取产品数量
     *
     * @param baseProduct
     * @return
     */
    int getBrandProductsCount(BaseProduct baseProduct);

    /**
     * 处理拆分材质信息
     *
     * @param splitTexturesInfo
     * @param type
     * @return
     * @author huangsongbo
     */
    Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type);

    /**
     * 获得数据与模型
     *
     * @return
     */
    BaseProduct getDataAndModel(BaseProduct baseProduct);

    /**
     * 通过模板方案ID和产品大小分类查询同类产品
     *
     * @param planId
     * @param productTypeValue
     * @param productSmallTypeValue
     * @return List
     * @author xiaoxc
     */
    List<BaseProduct> getByPlanIdProductList(Integer planId, String productTypeValue, Integer productSmallTypeValue);

    Map<String, CategoryProductResult> setbaimoRuleMap(Integer spaceCommonId, String mediaType,
                                                       Integer userId, String productSmallTypeCode, DesignPlan designPlan, Map<String, String> map);

    /**
     * 产品材质处理
     */
    BaseProduct productMaterial(BaseProduct baseProduct, LoginUser loginUser, ProductVO productVO) throws Exception;

    CategoryProductResult assemblyUnityPanProduct(Integer productId, Integer spaceCommonId, DesignPlan designPlan,
                                                  Integer userId, String mediaType);

    /**
     * 设置查询条件,从序列号信息中获取
     *
     * @param authorizedConfigItem
     * @return
     * @author huangsongbo
     */
    BaseProduct getBaseProductFromAuthorizedConfig(AuthorizedConfig authorizedConfigItem);

	List<List<ProCategoryPo>> getAllProductCategory(Integer companyId);

	ProducDetail getProducDetail(BaseProduct baseProduct, UserSO userSo);

	/*根据产品的list<Integer> ids 查询出产品列表*/
	List<ProductListVo> getAllProductByIds(List<Integer> ids,PageModel pageModel,UserSO userSo);
	
	/*根据产品的list<Integer> ids 查询出产品数量*/
	Integer getAllProductCountByIds(List<Integer> ids);

	String getPicUrlFromProductId(Integer mainProductId);

	//产品id获取公司id
    Integer getProductCompanyId(Integer productId);

    //产品id获取公司域名
    String getProductCompanyDomainName(Integer productId);

    List<BaseProduct> getAllSameTypeProduct(BaseProduct baseProduct);

    //通过产品可见范围id集合获取产品
    List<String> getCodeListByIdList(List<Integer> visibilityRangeIdList);

    List<ProCategoryPo> getExactProductCategory(Integer pid);

    BigDecimal getProductExpense(Integer goodsSpuId);

    Integer getProductPlatformPrice(Integer productId);
}
