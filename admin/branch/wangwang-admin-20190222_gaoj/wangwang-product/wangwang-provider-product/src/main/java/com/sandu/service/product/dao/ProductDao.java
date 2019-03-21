package com.sandu.service.product.dao;

import com.sandu.api.platform.model.PlatformProductRel;
import com.sandu.api.product.input.EditorProductQuery;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.EditorProductListBO;
import com.sandu.api.product.model.bo.ProductListBO;
import com.sandu.api.product.model.po.ProductQueryPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sandu
 * @date 2017/12/16
 */
@Repository
public interface ProductDao {
    Integer deleteProductById(@Param("id") long id);

    Integer deleteProductByCode(@Param("productCode") String productCode);

    void updateProduct(Product product);

    int saveProduct(Product product);

    List<Product> getProductByBrandId(@Param("brandId") long brandId);

    Product getProductInfoById(@Param("id") long id);

    Product getProductByCode(@Param("code") String code);

    List<Product> getProductByCategoryId(@Param("categoryId") long categoryId);

    List<Product> getProductByGroupProductId(@Param("groupProductId") long groupProductId);

    List<Integer> getProductIdsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);

    Integer queryProductsCount(ProductQueryPO productQueryPO);

    List<ProductListBO> queryProducts(ProductQueryPO productQueryPO);

    int deleteBussinessyProduct(List<Integer> ids);

    List<ProductListBO> query2cProducts(ProductQueryPO productQueryPO);

    Integer query2cProductsCount(ProductQueryPO productQueryPO);

    List<ProductListBO> query2bProducts(ProductQueryPO productQueryPO);

    Integer query2bProductsCount(ProductQueryPO productQueryPO);

    List<Product> getListByIds(List<Integer> list);

    List<ProductListBO> querySolutionProducts(ProductQueryPO productQueryPO);

    List<Product> getListByModelIds(List<Integer> collect);


    List<Product> getProductByModelIds(@Param("modelIds") Set<Long> modelIds);

    List<Product> getProductByIds(List<Integer> productIds);

    List<PlatformProductRel> mapProductId2PutStatus(List<Integer> productIds);

    List<EditorProductListBO> queryProductsInEditor(EditorProductQuery query);

    List<Integer> getNotBeSInitProductIds();

    int updateProductInitStatus(List<Integer> ids);

    List<Integer> getAutoSynProductIds();

    List<Integer> getBePutAwayProduct(List<Integer> productIds);

    String getProductDeliveredInfo(@Param("productId") Integer productId);

    String getMaxId();

    void synProductCompanyInfoWithBrandId(@Param("needSynProductIds")List<Integer> needSynProductIds);

    void synProductPutawayState(@Param("needSynProductIds") List<Integer> needSynProductIds);

    List<Product> getNeedBeSyncProductInfo();

    void updateProductTypeInfo(List<Product> needBeSyncProductInfo);

    List<String> getProductNumberWordRecommends(@Param("wordStart") String wordStart, @Param("brandId") Integer brandId, @Param("companyId") Integer companyId);

	List<Product> selectMerchantProductList();

    List<Integer> getNeedSynProductIds();

	List<Integer> getNeedUpdateProductIds();

    void fixedProductToSku();

    List<Product> listSecondProductId2ModelIdWithMainId(Long id);

    void hardDeleteProductByIds(@Param("productIds") Set<Integer> productIds);

	String getSoftHardProduct(@Param("productIds")List<Integer> ids);

	List<Map<String, Object>> queryUpPlatForm(@Param("productIds")List<Integer> productIds);

    String getHardProductIds(@Param("ids") List<Integer> ids);

    List<Integer> getGoodsIdsByProductIds(@Param("productIds") List<Integer> productIds);

    Integer validGroupActivity(@Param("ids") List<Integer> ids);

    Integer updateGroupActivity(@Param("ids") List<Integer> ids,@Param("remark") String remark);

    Integer removeGroupActivity(@Param("ids") List<Integer> ids);

    Integer validGroupActivityBySpuId(@Param("ids") List<Integer> ids);

    Integer updateGroupActivityBySpuId(@Param("ids") List<Integer> ids);

    List<Integer> getProductPutInfo(@Param("ids") List<Integer> ids);

	List<Product> getSplitTexturesInfo();

	Integer updateProductBak(@Param("productId")Long productId, @Param("isDeleted")int isDeleted);
}
