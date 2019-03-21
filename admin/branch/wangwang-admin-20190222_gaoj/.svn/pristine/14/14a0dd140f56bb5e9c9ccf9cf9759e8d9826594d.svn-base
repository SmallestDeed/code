package com.sandu.api.product.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.product.input.EditorProductQuery;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.EditorProductListBO;
import com.sandu.api.product.model.bo.ProductListBO;
import com.sandu.api.product.model.po.ProductQueryPO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sandu
 * @date 2017/12/16
 */
public interface ProductService {

    /**
     * 删除产品
     *
     * @param id
     * @return
     */
    Integer deleteProductById(long id);

    /**
     * 根据产品code删除产品
     *
     * @param productCode
     * @return
     */
    Integer deleteProductByCode(String productCode);

    /**
     * 更新产品
     *
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 增加产品
     *
     * @param product
     * @return
     */
    int saveProduct(Product product);

    /**
     * 根据品牌获取产品
     *
     * @param brandId
     * @return
     */
    List<Product> getProductByBrandId(long brandId, int page, int limit);

    /**
     * 根据ID获取产品详情
     *
     * @param id
     * @return
     */
    Product getProductInfoById(long id);

    /**
     * 根据分类ID获取产品
     *
     * @param categoryId
     * @return
     */
    List<Product> getProductByCategoryId(long categoryId, int page, int limit);

    /**
     * 根据产品组Id获取产品
     *
     * @param groupProductId
     * @return
     */
    List<Product> getProductByGroupProductId(long groupProductId);

    /**
     * 根据分类id集合查询产品
     *
     * @param categoryIds
     * @param page
     * @param limit
     * @return
     */
    List<Integer> getProductIdsByCategoryIds(List<Integer> categoryIds, int page, int limit);

    /**
     * 查询产品
     *
     * @param productQueryPO
     * @return
     */
    PageInfo<ProductListBO> queryProducts(ProductQueryPO productQueryPO);

    int deleteBussinessyProduct(List<Integer> ids);

    PageInfo<ProductListBO> query2bProducts(ProductQueryPO productQueryPO);

    PageInfo<ProductListBO> query2cProducts(ProductQueryPO productQueryPO);

    PageInfo<ProductListBO> querySolutionProducts(ProductQueryPO po);

    Map<Integer,String> mapProductIdandNameByModelIds(List<Long> productIds);

    Map<Integer, Product> mapProductByModelIds(List<Long> modelIds);

    List<Product> getProductByIds(List<Integer> productIds);

    Map<Integer,String> mapProductId2PutStatus(List<Integer> productIds);

    PageInfo<EditorProductListBO> queryProductsInEditor(EditorProductQuery query);

    List<Integer> getNotBeInitProductIdsAndInitProductStatus();

    List<Integer> getAutoSynProductIdsAndInitProductStatus();

    List<Integer> getBePutAwayProduct(List<Integer> productIds);

    List<Integer> getProductDeliveredInfo(Integer productId);

    String getMaxId();

    List<String> getProductNumberWordRecommends(String wordStart, Integer brandId,Integer companyId);

    void synProductCompanyInfoWithBrandId();

    void synProductPutawayState();

    void syncProductTypeMark();

    /**
     * 取得是由商家后台创建的产品并且状态为有效
     * @param param 
     * @return
     */
	List<Product> findMerchantProduct(Map<String, Object> param);

    /**
     * 修复三度后台创建产品未关联spu问题
     */
    void fixedProductToSpu();

    /**
     * 根据主产品ID 获取副产品ID-modelID
     * @param id productId
     * @return  productId to modelId Map ,其中包括主产品键值对
     */
    Map<Integer,Integer> listSecondProductId2ModelIdWithMainId(Long id);

    void hardDeleteProductByIds(Set<Integer> productIds);

    /**
     * 获取软装和硬装的产品ID
     * @param ids
     * @return
     */
	String getSoftHardProduct(List<Integer> ids);

	/**
	 * 获取产品已上架的平台集合
	 * @param ids
	 * @return
	 */
	Map<Integer, String> queryUpPlatForm(List<Integer> ids);

    String getHardProductIds(List<Integer> ids);

    /**
     * 通过productIds查询商品ID集合goodsIds
     * @param productIds
     * @return
     */
    List<Integer> getGoodsIdsByProductIds(List<Integer> productIds);

    /**
     * 校验是否加入拼团活动
     * @param ids
     * @return
     */
    Integer validGroupActivity(List<Integer> ids);

    /**
     * 更新拼团活动状态
     * @param ids
     * @return
     */
    Integer updateGroupActivity(List<Integer> ids);

    /**
     * 移除拼团活动商品
     * @param ids
     * @return
     */
    Integer removeGroupActivity(List<Integer> ids);

    /**
     * 校验是否加入拼团活动
     * @param ids
     * @return
     */
    Integer validGroupActivityBySpuId(List<Integer> ids);

    /**
     * 更新拼团活动商品
     * @param ids
     * @return
     */
    Integer updateGroupActivityBySpuId(List<Integer> ids);

    /**
     * 获得产品的小程序平台上下架状态
     * @param ids
     * @return
     */
    List<Integer> getProductPutInfo(List<Integer> ids);

	List<Product> getSplitTexturesInfo();

	void updateProductBak(Long productId, int isDeleted);
}
