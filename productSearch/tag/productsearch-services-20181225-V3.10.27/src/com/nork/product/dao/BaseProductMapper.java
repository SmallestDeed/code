package com.nork.product.dao;
import com.nork.product.model.ProductSimpleModel;
import java.util.List;
import java.util.Map;

import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.vo.ProductCeilingVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.CorrectProduct;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.ProductWeekReport;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.sync.model.ProductEntity;

/**   
 * @Title: BaseProductMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-产品库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseProductMapper {
    int insertSelective(BaseProduct record);

    int updateByPrimaryKeySelective(BaseProduct record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseProduct selectByPrimaryKey(Integer id);
    
    int selectCount(BaseProductSearch baseProductSearch);
    
    String findAllCode(BaseProductSearch baseProductSearch);
    
    String findHardMountedProductCode(BaseProductSearch baseProductSearch);
    
    String findHardMountedWhiteMoldCoding(BaseProductSearch baseProductSearch);
    
    String findSoftloadingProductCode(BaseProductSearch baseProductSearch);
    
    String findSoftFittedWhiteMoldCoding(BaseProductSearch baseProductSearch);
    
	List<BaseProduct> selectPaginatedList(
			BaseProductSearch baseProductSearch);
			
    List<BaseProduct> selectList(BaseProduct baseProduct);
    List<BaseProduct> selectList2(BaseProduct baseProduct);
    List<BaseProduct> selectDicList(BaseProduct baseProduct);
    
	/**
	 * 其他
	 * 
	 */
    int sameTypeProductCount(BaseProduct baseProduct);
    
    List<BaseProduct> sameTypeProductList(BaseProduct baseProduct);
    
    List<BaseProduct> selestProductStyle(Integer planId);

    /**
     * 根据产品名称和品牌ID查询产品
     * @param baseProduct
     * @return
     */
    List<CategoryProductResult> selectProductByNameAndBrandId(BaseProduct baseProduct);

    Integer selectProductByNameAndBrandCount(BaseProduct baseProduct);

	List<String> findAllName();

    /**
     * 产品组工作周报表
     * @param baseProductSearch
     * @return
     */
    List<ProductWeekReport> productExecutionReport(BaseProductSearch baseProductSearch);
    
    List<BaseProduct> findAllSpaceStuffProductList();
    
    String findTempletHardMountedProductCode(BaseProductSearch baseProductSearch);

    
    /**
     * 授权产品列表
     * @param baseProductSearch
     * @return
     */
    List<BaseProduct> authorizedProductList(
			BaseProductSearch baseProductSearch);
    
    int authorizedProductCount(BaseProductSearch baseProductSearch);

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
    
    List<CategoryProductResult> selectBrandProductsList(BaseProduct baseProduct);

    int selectBrandProductsCount(BaseProduct baseProduct);

    int selectBaseCount(BaseProductSearch baseProductSearch);
    
    BaseProduct selectByProductCode(String code);
    
    int selectProCount(BaseProductSearch baseProductSearch);
    
    List<BaseProduct> selectProPaginatedList(BaseProductSearch baseProductSearch);
    
    int selectOtherCount(BaseProductSearch baseProductSearch);
    
    List<BaseProduct> selectOtherPaginatedList(BaseProductSearch baseProductSearch);

	BaseProduct getInfoById(Map<String,Integer> map);

	BaseProduct getDataAndModel(BaseProduct baseProduct);

	List<BaseProduct> getInfoByIdList(
			@Param("idList") List<Integer> idList,
			@Param("designTemplateId") Integer designTemplateId,
			@Param("userId") Integer userId);

	List<ProductPropsSimple> getProductPropsSimpleByProductId(Integer productId);

    List<BaseProduct> getTempletProductData(BaseProductSearch baseProductSearch);

	List<BaseProduct> getProductData(BaseProductSearch baseProductSearch);

	List<Integer> findIdListByStatus(@Param("productStatusList") List<Integer> productStatusList,@Param("limit") int limit);

	List<Integer> getProductListByStatus(@Param("productStatus") Integer productStatus);

	void updateStatus(@Param("oldStatus") int oldStatus, @Param("newStatus") int newStatus);

	void updateBmProductStatus(@Param("oldStatus") int oldStatus, @Param("newStatus") int newStatus);

	void updateProductStatus(@Param("oldStatus") int oldStatus, @Param("newStatus") int newStatus,@Param("productIdList") List<Integer> productIdList);

	List<Integer> getPreprocessProductIdByCategoryCode(
			@Param("categoryCode") String categoryCode, @Param("productStatusList") List<Integer> productStatusList);

	List<Integer> getPreprocessBaimoProductIdByCategoryCode(String categoryCode);

	List<Integer> getProductTypeValueByStatus(@Param("productStatusList") List<Integer> productStatusList);

	List<BaseProduct> getPropInfoByStatusListAndProductTypeValue(
			@Param("productStatusList") List<Integer> productStatusList,
			@Param("productTypeValue") Integer productTypeValue
			);
	List<BaseProduct> findByPlanIdProductList(
	        @Param("planId") Integer planId,
            @Param("productTypeValue") String productTypeValue,
            @Param("productSmallTypeValue") Integer productSmallTypeValue);

	int getCountRealTime(PrepProductSearchInfo prepProductSearchInfo);

	List<CategoryProductResult> getProductIdListV2RealTime(PrepProductSearchInfo prepProductSearchInfo);
	

    List<BaseProduct> getProductsByCodes(@Param("productCodes")List <String>productCodes);

    BaseProduct findDataByCode(@Param("productCode") String productCode);

	 //批量查询
	List<BaseProduct> getBatchData(List<Integer> list);

	List<DesignProductResult> getTempletProductList(@Param("posNameList")List <String>posNameList,@Param("designTempletId") Integer designTempletId);

    List<DesignProductResult> getPlanProductList(@Param("planId")Integer planId);
	/**
	 * 通过样板房产品ids  查询序号 好 产品详细
	 * @param designPlanProductIds
	 * @return
	 */
	List<BaseProduct> getProductListByDesignPlanProductids(@Param("designPlanProductIds")List<Integer> designPlanProductIds);
	/**
	 * 匹配产品
	 * @param baseProduct_2
	 * @return
	 */
	List<BaseProduct> matchingProductList(BaseProduct baseProduct_2);

	List<DesignProductResult> getPlanProductRecommendedList(@Param("planRecommendedId") Integer planRecommendedId);

    /**
     * 通过全铺长度和款式查询
     * @param baseProduct
     * @return
     */
	
	List<SearchProductGroupDetail> getBgWallByFullPaveLengthAndStyle(BaseProduct baseProduct);
	
	List<BaseProduct> selectProductEasy(BaseProductSearch baseProductSearch);
	
	/**
	 * 根据设计方案id查询该方案中可还原的白模产品
	 * @param designPlanId
	 * @return
	 */
	List<BaseProduct> getByDesignPlanId(Integer designPlanId);

	String getPropFilterInfo(@Param("productId") Integer productId);

	List<ProductPropsSimple> getProductPropsSimpleByProductIdAndAttrCode(
			@Param("productId") Integer productId, @Param("productAttrCode") String productAttrCode
			);
	
	/**
	 * 通过结构id 获取结构里的产品 详情	和 windows模型地址	 
	 * @param id
	 * @return
	 */
	List<ProductSimpleModel> getProductListByStructureId(Integer structureProductId);

	List<String> getProductIdentification(@Param("productId")Integer productId);

	List<CategoryProductResult> getSeriesProductData(BaseProductSearch baseProductSearch);

	@Select("select bc.id from base_product bp left join base_brand bb on" +
			" bp.brand_id = bb.id left join base_company bc on bc.id  = bb.company_id where bp.id=#{productId} ")
	@ResultType(Integer.class)
	Integer getProductCompanyId(@Param("productId")Integer productId);
	
	
	/**
	 * 通过idList查询产品详情
	 * add by yangz 2017-12-28 15:16:45
	 * @param idList
	 * @param limit
	 * @param start
	 * @param userId
	 * @return
	 */
	List<CategoryProductResult> getDetailByIds(@Param("idList") List<Integer> idList,@Param("limit") Integer limit,
			@Param("start") Integer start, @Param("userId") Integer userId);

	/**
	 * 获取天花信息
	 * @param productIdsList
	 * @return
	 */
	List<ProductCeilingVO> getCeilingInfoByProductIds(@Param("productIdsList") List<Integer> productIdsList);

	List<BaseProduct> getBaseProductListByProductBatchId(@Param("productBatchId") Integer productBatchId, @Param("fullPaveLength") Integer fullPaveLength, @Param("productHeight") Integer productHeight);
}
