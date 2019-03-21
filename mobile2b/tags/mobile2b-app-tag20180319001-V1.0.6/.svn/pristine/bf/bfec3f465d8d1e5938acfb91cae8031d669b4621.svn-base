package com.nork.product.service;
import com.nork.product.model.ProductSimpleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.product.model.*;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchStructureProductDetailResult;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.service.impl.BaseProductServiceImpl.getBaseProductPropListMapEnum;
import com.nork.product.service.impl.BaseProductServiceImpl.getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum;
import com.nork.productprops.model.BaseProductProps;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.sync.model.ProductEntity;

/**   
 * @Title: BaseProductService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
public interface BaseProductService {
	/**
	 * 新增数据
	 *
	 * @param baseProduct
	 * @return  int 
	 */
	public int add(BaseProduct baseProduct);

	/**
	 *    更新数据
	 *
	 * @param baseProduct
	 * @return  int 
	 */
	public int update(BaseProduct baseProduct);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseProduct 
	 */
	public BaseProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getList(BaseProduct baseProduct);

	/**
	 *    获取数据数量
	 *
	 * @param  baseProduct
	 * @return   int
	 */
	public int getCount(BaseProductSearch baseProductSearch);
	/**
	 * 产品同类型数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getSameTypeProductList(BaseProduct baseProduct);

	/**
	 *    同类型数据数量
	 *
	 * @param  baseProduct
	 * @return   int
	 */
	public int getSameTypeProductCount(BaseProduct baseProduct);
	
	public String findAllCode(BaseProductSearch baseProductSearch);
	
	public String findHardMountedProductCode(BaseProductSearch baseProductSearch);
	
	public String findHardMountedWhiteMoldCoding(BaseProductSearch baseProductSearch);
	
	public String findSoftloadingProductCode(BaseProductSearch baseProductSearch);
	
	public String findSoftFittedWhiteMoldCoding(BaseProductSearch baseProductSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getPaginatedList(
				BaseProductSearch baseProducttSearch);
	
	/**
	 *    根据产品风格得出设计方案风格
	 *
	 * @param  planId
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getProductStyle(Integer planId);

	/**
	 * 其他
	 * 
	 */


	/**
	 * 根据产品名称和品牌ID查询产品
	 * @param baseProduct
	 * @return
	 */
	public List<CategoryProductResult> selectProductByNameAndBrandId(BaseProduct baseProduct);

	public Integer selectProductByNameAndBrandCount(BaseProduct baseProduct);
	
	/*
	 * 
	 */
	
	public Integer getDetailProduct(Map map);
	
	public boolean add(BaseProduct baseProduct,String proAttIds,HttpServletRequest request);

	public List<String> findAllName();

	public String getU3dModelId(String mediaType,BaseProduct baseProduct);

	/**
	 * 产品组工作周报表
	 * @param baseProductSearch
	 * @return
	 */
	List<ProductWeekReport> productExecutionReport(BaseProductSearch baseProductSearch);
	
	/**
	 * 查询所有空间硬装产品列表
	 * @return
	 */
	List<BaseProduct> findAllSpaceStuffProductList();
	/**
	 * 查询样板房硬装产品编码
	 * @return
	 */
	String findTempletHardMountedProductCode(BaseProductSearch baseProductSearch);
	
	/**
	 * 更新产品所有附件的code
	 * @param baseProduct
	 */
	public void updateCode(BaseProduct baseProduct);
	
	/**
	 * 产品修改先清除资源回填信息
	 * @param baseProduct
	 */
	public void clearBackfill(BaseProduct baseProduct);
	
	/**
	 * 产品新增、修改需回填的信息表
	 * @param baseProduct
	 */
	public void backfill(BaseProduct baseProduct);
	/**
	 * 有多个属性则需复制资源
	 * @param baseProduct
	 */
	public void copyResources(BaseProduct oldProduct,BaseProduct newProduct,HttpServletRequest request);
	
	/**
	 *    授权产品数量
	 *
	 * @param  baseProduct
	 * @return   int
	 */
	public int authorizedProductCount(BaseProductSearch baseProductSearch);
	
	/**
	 *     授权产品列表
	 *
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> authorizedProductList(
				BaseProductSearch baseProductSearch);

	/**
	 * 获取产品列表
	 * @param baseProduct
	 * @return
	 */
	public List<CorrectProduct> getProductList(BaseProduct baseProduct);

	ProductEntity selectProductEntity(Integer id);

	/**
	 * 通过编码删除
	 * @param productCode
	 * @return
	 */
	int deleteByCode(String productCode);

	/**
	 * 新增ProductEntity
	 * @return
	 */
	int insertEntity(ProductEntity productEntity);

	/**
	 * 更新ProductEntity
	 * @param productEntity
	 * @return
	 */
	int updateEntity(ProductEntity productEntity);

	ProductEntity sysSave(ProductEntity baseProduct,LoginUser loginUser);

	/**
	 * 获得产品的缩略图url
	 * @param mainProductId
	 * @return
	 */
	public String getPicUrlFromProductId(Integer mainProductId);
	/**
	 * 获取是否是硬装
	 * @param mainProductId
	 * @return
	 */
	public boolean isHard(BaseProduct baseProduct);
	/**
	 * 通过品牌获取产品列表
	 * @param baseProduct
	 * @return
	 */
	public List<CategoryProductResult> getBrandProductsList(BaseProduct baseProduct);
	/**
	 * 通过品牌获取产品数量
	 * @param baseProduct
	 * @return
	 */
	public int getBrandProductsCount(BaseProduct baseProduct);

	/**
	 * 处理类似于"!xingx"的值
	 * @author huangsongbo
	 * @param categoryBlacklistMap
	 * @return
	 */
	public Map<String, Set<String>> dealWithMap(Map<String, Set<String>> categoryBlacklistMap);
	
	int selectBaseCount(BaseProductSearch baseProductSearch);
	
	/**
	 * 装修space产品列表信息
	 * @param CategoryProductResult
	 * @return
	 */
	public CategoryProductResult decorationProductInfo(CategoryProductResult productResult,BaseProduct baseProduct,DesignPlan designPlan
			,DesignPlanProduct designPlanProduct,Map<String,AutoCarryBaimoProducts> autoCarryMap,HttpServletRequest request);
	
	public CategoryProductResult assemblyUnityPanProduct(Integer productId,Integer spaceCommonId,DesignPlan designPlan,Integer userId,HttpServletRequest request);

	/**
	 * 处理拆分材质信息
	 * @author huangsongbo
	 * @param splitTexturesInfo
	 * @param type 
	 * @return
	 */
	public Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type);

	/**
	 * 通过产品code查找产品
	 * @author huangsongbo
	 * @param productCode
	 * @return
	 */
	public BaseProduct findOneByCode(String productCode);
	
	

	/**
	 * BaseProduct->StructureProductDetailsSearch
	 * @author huangsongbo
	 * @param baseProduct
	 * @return
	 */
	public SearchStructureProductDetailResult getStructureDetailsSearch(BaseProduct baseProduct, String mediaType);

	/**
	 * 设置查询条件,从序列号信息中获取
	 * @author huangsongbo
	 * @param authorizedConfigItem
	 * @return
	 */
	public BaseProduct getBaseProductFromAuthorizedConfig(AuthorizedConfig authorizedConfigItem);
	
	public BaseProduct selectByProductCode(String code);
	
	int selectProCount(BaseProductSearch baseProductSearch);
    
    List<BaseProduct> selectProPaginatedList(BaseProductSearch baseProductSearch);

    /**
     * 得到黑名单配置
     * @author huangsongbo
     * @return
     */
	public Set<String> getBlacklist(Integer userId);

	/**
	 * 从序列号获取查询条件
	 * @author huangsongbo
	 * @param loginUser 
	 * @return
	 */
	public List<BaseProduct> getSelectConditionByAuthorizedConfig(LoginUser loginUser);
	
	/**
	 * 搜索产品方法补充产品信息V2
	 * @author huangsongbo
	 * @param CategoryProductResult
	 * @return
	 */
	public CategoryProductResult decorationProductInfoV2(CategoryProductResult productResult,BaseProduct baseProduct,DesignPlan designPlan
			,DesignPlanProduct designPlanProduct,Map<String,AutoCarryBaimoProducts> autoCarryMap,LoginUser loginUser,HttpServletRequest request);

	/**
	 * 搜索产品方法补充产品信息V3
	 * @author huangsongbo
	 * @param CategoryProductResult
	 * @return
	 */
	public CategoryProductResult decorationProductInfoV3(CategoryProductResult productResult, BaseProduct baseProduct,
			DesignPlan designPlan, DesignPlanProduct designPlanProduct,
			Map<String, AutoCarryBaimoProducts> autoCarryMap,
			/*LoginUser loginUser,*/
			HttpServletRequest request);

	/**
	 * 查询产品,并额外查询
	 * @param productId
	 * @return
	 */
	public BaseProduct getInfoById(Map<String,Integer> map);

	/**
	 * 设置产品色系排序条件
	 * @param list
	 * @param cacheEnable
	 */
	public void productColorsSort(List<CategoryProductResult> list);

	public void productColorsSortV2(List<CategoryProductResult> list);

	/**
	 * 获得数据与模型
	 * @param map
	 * @return
	 */
	public BaseProduct getDataAndModel(BaseProduct baseProduct);

	/**
	 * 一次性查出数据(查询大小类信息,是否是硬装,是否是组合)
	 * @param idList
	 * @param designTemplateId
	 * @return
	 */
	public List<BaseProduct> getInfoByIdList(List<Integer> idList, Integer designTemplateId, Integer userId);

	public Map<String,CategoryProductResult> setbaimoRuleMap(Integer spaceCommonId, HttpServletRequest request, Integer userId,
			String productSmallTypeCode, DesignPlan designPlan, Map<String, String> map);
	
	public Integer getBgWallValue (String productTypeCode,String productSmallTypeCode);

	/**
	 * 通过产品id查询该产品关联的属性信息
	 * @author huangsongbo
	 * @param productId
	 * @return
	 */
	public List<ProductPropsSimple> getProductPropsSimpleByProductId(Integer productId);

	public List<BaseProduct> getSelectConditionByAuthorizedConfigV2(LoginUser loginUser);

	public void setBasicModelMap(CategoryProductResult categoryProductResult,
			Map<String, AutoCarryBaimoProducts> autoCarryMap, Map<String, String> map, DesignPlan designPlan, DesignPlanProduct designPlanProduct, HttpServletRequest request);

	public void setTextureInfo(CategoryProductResult categoryProductResult,BaseProduct baseProduct);

	/**
	 * 设置授权码查询条件
	 * @author huangsongbo
	 * @param loginUser
	 * @param checkCategory
	 * @return
	 */
	public List<BaseProduct> getSelectConditionByAuthorizedConfigV3(LoginUser loginUser, String checkCategory);

	/**
	 * 匹配模板房间产品数据
	 * @author xiaoxc
	 * @param baseProductSearch
	 * @return list
	 */
	public List<BaseProduct> getTempletProductData(BaseProductSearch baseProductSearch);

	/**
	 * 匹配同类型产品数据
	 * @author xiaoxc
	 * @param baseProductSearch
	 * @return list
	 */
	public List<BaseProduct> getProductData(BaseProductSearch baseProductSearch);


	/**
	 * 根据用户类型获取不同状态数据
	 * @author xiaoxc
	 * @param loginUser
	 * @return list
	 */
	public List<Integer> getPutawayList(LoginUser loginUser);

	
	/**
	 * 通过状态查找产品idList
	 * @author huangsongbo
	 * @param productStatusList
	 * @return
	 */
	public List<Integer> findIdListByStatus(List<Integer> productStatusList,int limit);
	
	/**
	 * 批量改变产品状态
	 * @author huangsongbo
	 * @param oldStatus 旧的状态
	 * @param newStatus 新的状态
	 */
	public void updateStatus(int oldStatus, int newStatus);

	/**
	 * 批量改变白膜产品状态
	 * @author xiaoxc
	 * @param oldStatus 旧的状态
	 * @param newStatus 新的状态
	 */
	public void updateBmProductStatus(int oldStatus, int newStatus);

	/**
	 * 批量改变产品状态
	 * @author xiaoxc
	 * @param oldStatus 旧的状态
	 * @param newStatus 新的状态
	 * @param productIdList 产品IDs
	 */
	public void updateProductStatus(int oldStatus, int newStatus,List<Integer> productIdList);
	
	/**
	 * 取出所有有属性的产品的id做成list
	 * @author huangsongbo
	 * @param categoryCode
	 * @return
	 */
	public List<Integer> getPreprocessProductIdByCategoryCode(String categoryCode, List<Integer> productStatusList);
	
	public List<Integer> getPreprocessBaimoProductIdByCategoryCode(String categoryCode);

	/**
	 * 取产品过滤信息
	 * @author xiaoxc
	 * @param searchProduct
	 * @param baimoProduct
	 * @return BaseProductSearch
	 */
	public BaseProductSearch getProductInfoFilter(BaseProductSearch searchProduct,DesignProductResult baimoProduct);


	/**
	 * 通过产品状态(list)查找产品数量
	 * @author huangsongbo
	 * @param productStatusList
	 * @return
	 */
	public int getcountByStatusList(List<Integer> productStatusList);

	/**
	 * 根据产品状态得到去重复的大分类value
	 * @author huangsongbo
	 * @param productStatusList
	 * @return
	 */
	public List<Integer> getProductTypeValueByStatus(List<Integer> productStatusList);

	/**
	 * 得到产品list对应的属性信息(Map存两个元素:一个白膜,一个普通产品)
	 * @author huangsongbo
	 * @param productStatusList
	 * @param productTypeValue
	 * @return
	 */
	public Map<getBaseProductPropListMapEnum, List<BaseProductProps>> getBaseProductPropListByProudctStatusListAndProudctTypeValue(
			List<Integer> productStatusList, Integer productTypeValue);

	/**
	 * 通过产品状态和产品大类value查找产品并且附带其产品属性
	 * @author huangsongbo
	 * @param productStatusList
	 * @param productTypeValue
	 * @return
	 */
	List<BaseProduct> getPropInfoByStatusListAndProductTypeValue(List<Integer> productStatusList,
			Integer productTypeValue);

	/**
	 * 通过模板方案ID和产品大小分类查询同类产品
	 * @author xiaoxc
	 * @param planId
	 * @param productTypeValue
	 * @param productSmallTypeValue
	 * @return List
	 */
	public List<BaseProduct> getByPlanIdProductList(Integer planId,String productTypeValue,Integer productSmallTypeValue);

	/**
	 * getCount的实时搜索方法
	 * @author huangsongbo
	 * @param prepProductSearchInfo
	 * @return
	 */
	public int getCountRealTime(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode);

	/**
	 * getProductIdListV2的实时搜索方法
	 * @author huangsongbo
	 * @param prepProductSearchInfo
	 * @return
	 */
	public List<CategoryProductResult> getProductIdListV2RealTime(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode);

	/**
	 * 通过产品code查找产品（关联数据字典查询）
	 * @author xiaoxc
	 * @param productCode
	 * @return
	 */
	public BaseProduct findDataByCode(String productCode);
	/**
	 * 查询所有
	 * @param baseProduct
	 * @return
	 */
	public List<BaseProduct> getList2(BaseProduct baseProduct);
	public List<BaseProduct> getDicList(BaseProduct baseProduct);

	/**
	 * 获取方案产品列表数据
	 * @author xiaoxc
	 * @param planId
	 * @return
	 */
	public List<DesignProductResult> getPlanProductList(Integer planId);

	/**
	 * 单品替换组合方法（适用于天花地板 功能）
	 * @param request
	 * @param response
	 * @return
	 */
	public List<SearchProductGroupDetail> productSelectGroupReplaceV2(BaseProduct baseProduct, DesignPlan designPlan,
			List<Integer>  designPlanProductIds, int productIndex,Integer userType,Integer spaceCommonId,String mediaType);

	/**
	 * 通过白模背景墙主体，匹配出对应背景墙产品
	 * @param baseProduct
	 * @param designPlan
	 * @param designPlanProductIds
	 * @param productIndex
	 * @param userType
	 * @param spaceCommonId
	 * @param mediaType
	 * @return
	 */
	List<SearchProductGroupDetail> productSelectGroupReplaceV3(BaseProduct baseProduct, DesignPlan designPlan,
				List<Integer> designPlanProductIds,int productIndex, Integer userType,Integer spaceCommonId,String mediaType,Integer styleId);

	/**
	 * 传入白膜小类value,转化为对应小类的value
	 * 
	 * @author huangsongbo
	 * @param templetProductSmallTypeValue
	 * @return
	 */
	public Integer dealWithBaimoSmallTypeValue(Integer bigTypeValue, Integer smallTypeValue);

	/**
	 *	查询出替换产品时，哪些产品需要一起被带出
	 * @param productId	产品ID
	 * @param productSmallTypeCode	产品小类code
	 * @param planId	设计方案ID
	 * @param request	设置这个参数很无奈，decorationProductInfoV3这个方法要传，没办法。本来想改decorationProductInfoV3，无奈用到的点太多，不敢随便改。后面相关开发改了之后会把这个参数去掉。
	 * @return
	 */
	Map<String,CategoryProductResult> autoCarryProduct(Integer productId,String productSmallTypeCode,Integer planId,HttpServletRequest request);

	/**
	 * 设置查询条件:PlanProductInfo ->BaseProductSearch
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @return
	 */
	public BaseProductSearch getBaseProductSearchByPlanProductInfo(PlanProductInfo planProductInfo);

	/**
	 * 产品搜索(简单)
	 * 
	 * @author huangsongbo
	 * @param baseProductSearch
	 * @return
	 */
	public List<BaseProduct> selectProductEasy(BaseProductSearch baseProductSearch);

	/**
	 * 根据产品id查找该产品的过滤属性信息
	 * 
	 * @author huangsongbo
	 * @param productId
	 * @return
	 */
	public String getPropFilterInfo(Integer productId);

	/**
	 * 获取产品的过滤属性信息
	 * 
	 * @author huangsongbo
	 * @param mainProductId
	 * @return
	 */
	public List<ProductPropsSimple> getProductFilterPropList(Integer productId);

	/**
	 * 获取产品属性
	 * 
	 * @param productId 要查询属性的产品id 
	 * @param productattrcodeChaoxiang 产品属性父级code
	 * @return
	 */
	public List<ProductPropsSimple> getProductPropsSimpleByProductId(Integer productId,
			String productattrcodeChaoxiang);

	/**
	 * 通过结构id 获取结构里的产品 详情	和 windows模型地址	 
	 * @param id
	 * @return
	 */
	public List<ProductSimpleModel> getProductListByStructureId(Integer structureProductId);

	/**
	 * 产品标示
	 * @param id
	 * @return
	 */
	public List<String> getProductIdentification(Integer productId);

	 /**
	 * 获取系列产品匹配数据
	 * @param planProduct
	 * @param loginUser
	 * @param seriesId
	 * @author xiaoxc
	 * @return
	 */
	CategoryProductResult getSeriesProductData(DesignProductResult planProduct,LoginUser loginUser,Integer seriesId);

	/**
	 * 获取拉伸缩放长度
	 * @param smallTypeKey
	 * @author xiaoxc
	 * @return Map
	 */
	Map<String,String> getStretchZoomLength(String smallTypeKey);

	/**
	 * 获取拉伸缩放长度
	 * @param productTypevlaue
	 * @param productSmallTypeValue
	 * @author xiaoxc
	 * @return Map
	 */
	Map<String,String> getStretchZoomLength(Integer productTypevlaue, Integer productSmallTypeValue);

	/**
	 * 得到系列标识(返回结果eg:S_1)
	 * 
	 * @author huangsongbo
	 * @param valuekey 小分类valuekey
	 * @return
	 */
	String getSeriesSign(String valuekey);
	
	/**
	 * 得到系列标识(返回结果eg:S_1)
	 * 
	 * @author huangsongbo
	 * @param bigTypeValue 大类value值
	 * @param smallTypeValue 小类value值
	 * @return
	 */
	String getSeriesSign(Integer bigTypeValue, Integer smallTypeValue);
	
	/**
	 * 根据配置,得到要查询的小类valueList
	 * 
	 * @author huangsongbo
	 * @param smallTypeValueKey 传入小类valuekey
	 * @return
	 */
	public Map<getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum, List<Integer>> getsmallTypeValueListBySmallTypeValueKey(String smallTypeValueKey);

	/**
	 * 设置查询条件,从序列号信息中获取
	 * @param bigType
	 * @param smallType
	 * @param productIds
	 * @return
	 */
	BrandIndustryModel getBrandIndustryModelbyAuthorizedInfo (String bigType, String smallType, String productIds);

	/**
	 * 通过idList查询产品详情
	 * add by yangzhun 2017-12-28 15:16:45
	 * @param idList
	 * @param limit
	 * @param start
	 * @param userId
	 * @param msgId
	 * @return ResponseEnvelope
	 */
	ResponseEnvelope getDetailByIds(List<Integer> idList, Integer limit,
			 Integer start, Integer userId, String msgId, Integer isStandard,
			 String regionMark, Integer styleId, String measureCode);
	
}
