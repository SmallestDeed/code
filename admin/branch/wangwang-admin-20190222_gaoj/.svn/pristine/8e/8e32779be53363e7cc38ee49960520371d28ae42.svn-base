package com.sandu.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.sandu.api.platform.model.PlatformProductRel;
import com.sandu.api.product.input.EditorProductQuery;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.EditorProductListBO;
import com.sandu.api.product.model.bo.ProductListBO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.service.ProductService;
import com.sandu.constant.Punctuation;
import com.sandu.service.product.dao.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.util.CodeUtil.formatCode;
import static jdk.nashorn.internal.objects.NativeMath.log;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/15 10:02
 */
@Slf4j
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer deleteProductById(long id) {
        return productDao.deleteProductById(id);
    }

    @Override
    public Integer deleteProductByCode(String productCode) {
        return productDao.deleteProductByCode(productCode);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public int saveProduct(Product product) {
        int i = productDao.saveProduct(product);
        product.setProductCode(formatCode("P", product.getId() - 1 + ""));
        productDao.updateProduct(product);
        return i;
    }

    @Override
    public List<Product> getProductByBrandId(long brandId, int page, int limit) {
        PageHelper.startPage(page, limit);
        productDao.getProductByBrandId(brandId);
        return null;
    }

    @Override
    public Product getProductInfoById(long id) {
        return productDao.getProductInfoById(id);
    }

    @Override
    public List<Product> getProductByCategoryId(long categoryId, int page, int limit) {
        PageHelper.startPage(page, limit);
        return productDao.getProductByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductByGroupProductId(long groupProductId) {
        return productDao.getProductByGroupProductId(groupProductId);
    }

    @Override
    public List<Integer> getProductIdsByCategoryIds(List<Integer> categoryIds, int page, int limit) {
        PageHelper.startPage(page, limit);
        categoryIds = categoryIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        return productDao.getProductIdsByCategoryIds(categoryIds);
    }

    @Override
    public PageInfo<ProductListBO> queryProducts(ProductQueryPO productQueryPO) {
//        PageHelper.startPage(productQueryPO.getPage(), productQueryPO.getLimit(), false);
        productQueryPO.setOffsetNum((productQueryPO.getPage() - 1) * productQueryPO.getLimit());
        List<ProductListBO> list = productDao.queryProducts(productQueryPO);
        long count = PageHelper.count(() -> productDao.queryProductsCount(productQueryPO));
        PageInfo<ProductListBO> ret = new PageInfo(list, (int) Math.ceil(count / productQueryPO.getLimit()));
        ret.setTotal(count);
        return ret;
    }

    @Override
    public int deleteBussinessyProduct(List<Integer> ids) {
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids == null || ids.isEmpty()) {
            return -1;
        }
        productDao.deleteBussinessyProduct(ids);
        return 1;
    }

    @Override
    public PageInfo<ProductListBO> query2bProducts(ProductQueryPO productQueryPO) {
        if (productQueryPO.getPage() < 1) {
            productQueryPO.setPage(1);
        }
        productQueryPO.setPage((productQueryPO.getPage() - 1) * productQueryPO.getLimit());
        List<ProductListBO> list = productDao.query2bProducts(productQueryPO);
        long count = productDao.query2bProductsCount(productQueryPO);
        PageInfo<ProductListBO> ret = new PageInfo(list, (int) Math.ceil(count / productQueryPO.getLimit()));
        ret.setTotal(count);
        return ret;
    }

    @Override
    public PageInfo<ProductListBO> query2cProducts(ProductQueryPO productQueryPO) {
        if (productQueryPO.getPage() < 1) {
            productQueryPO.setPage(1);
        }
        productQueryPO.setPage((productQueryPO.getPage() - 1) * productQueryPO.getLimit());
        List<ProductListBO> list = productDao.query2cProducts(productQueryPO);
        long count = productDao.query2cProductsCount(productQueryPO);
        PageInfo<ProductListBO> ret = new PageInfo<>(list, (int) Math.ceil(count / productQueryPO.getLimit()));
        ret.setTotal(count);
        return ret;
    }

    @Override
    public PageInfo<ProductListBO> querySolutionProducts(ProductQueryPO productQueryPO) {
        PageHelper.startPage(productQueryPO.getPage(), productQueryPO.getLimit());
        List<ProductListBO> list = productDao.querySolutionProducts(productQueryPO);
        return new PageInfo<>(list);
    }

    @Override
    public Map<Integer, String> mapProductIdandNameByModelIds(List<Long> productIds) {
        if (productIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Product> products = productDao.getListByModelIds(productIds.stream().map(Long::intValue).collect(Collectors.toList()));
        Map<Integer, String> map = new HashMap<>(products.size());
        products.forEach(item -> map.put(item.getId().intValue(), item.getProductName()));
        return map;
    }

    @Override
    public Map<Integer, Product> mapProductByModelIds(List<Long> modelIds) {
        Set<Long> modelIdSet = modelIds.stream()
                .filter(Objects::nonNull)
                .filter(m -> m > 0L)
                .collect(Collectors.toSet());

        if (modelIdSet.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Product> list = productDao.getProductByModelIds(modelIdSet);
        Map<Integer, Product> ret = new HashMap<>(list.size());
        list.forEach(item -> {
            Product product = new Product();
            product.setId(item.getId());
            product.setProductCode(item.getProductCode());
            ret.put(item.getModelId(), product);
        });
        return ret;
    }

    @Override
    public List<Product> getProductByIds(List<Integer> productIds) {
        productIds = productIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }
        return productDao.getProductByIds(productIds);
    }

    @Override
    public Map<Integer, String> mapProductId2PutStatus(List<Integer> productIds) {
        productIds = productIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (productIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<PlatformProductRel> platformProductRels = productDao.mapProductId2PutStatus(productIds);
        Map<Integer, String> ret = new HashMap<>(platformProductRels.size());
        platformProductRels.forEach(item -> ret.put(item.getProductId(), item.getAtt1()));
        return ret;
    }

    @Override
    public PageInfo<EditorProductListBO> queryProductsInEditor(EditorProductQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<EditorProductListBO> list = productDao.queryProductsInEditor(query);
        return new PageInfo<>(list);
    }

    @Override
    public List<Integer> getNotBeInitProductIdsAndInitProductStatus() {
        List<Integer> ids = productDao.getNotBeSInitProductIds();
        if (!ids.isEmpty()) {
            productDao.updateProductInitStatus(ids);
        }
        log("init product init allotState ids:{}", ids);
        log("init product init allotState ids size:{}", ids.size());
        return ids;

    }

    @Override
    public List<Integer> getAutoSynProductIdsAndInitProductStatus() {
        List<Integer> ids = productDao.getAutoSynProductIds();
        if (!ids.isEmpty()) {
            productDao.updateProductInitStatus(ids);
        }
        log.info("auto syn product init allotState ids:{}", ids);
        log.info("auto syn product init allotState ids size:{}", ids.size());
        return ids;
    }

    @Override
    public List<Integer> getBePutAwayProduct(List<Integer> productIds) {
        productIds = productIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }
        return productDao.getBePutAwayProduct(productIds);
    }

    @Override
    public List<Integer> getProductDeliveredInfo(Integer productId) {
        String companyIds = productDao.getProductDeliveredInfo(productId);
        if (StringUtils.isBlank(companyIds)) {
            return Collections.emptyList();
        }
        return Splitter.on(Punctuation.COMMA)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(companyIds)
                .stream().map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    public String getMaxId() {
        return productDao.getMaxId();
    }

    @Override
    public List<String> getProductNumberWordRecommends(String wordStart, Integer brandId, Integer companyId) {
        return productDao.getProductNumberWordRecommends(wordStart, brandId, companyId);
    }

    @Override
    public void synProductCompanyInfoWithBrandId() {
        List<Integer> needSynProductIds = productDao.getNeedUpdateProductIds();
        if (needSynProductIds.isEmpty()) {
            return;
        }
        productDao.synProductCompanyInfoWithBrandId(needSynProductIds);
    }

    @Override
    public void synProductPutawayState() {
        List<Integer> needSynProductIds = productDao.getNeedSynProductIds();
        if (needSynProductIds.isEmpty()) {
            return;
        }
        productDao.synProductPutawayState(needSynProductIds);
    }

    @Override
    public void syncProductTypeMark() {
        List<Product> needBeSyncProductInfo = productDao.getNeedBeSyncProductInfo();
        if (needBeSyncProductInfo.isEmpty()) {
            return;
        }
        productDao.updateProductTypeInfo(needBeSyncProductInfo);
    }

    @Override
    public List<Product> findMerchantProduct(Map<String, Object> param) {
        return productDao.selectMerchantProductList();
    }

    @Override
    public void fixedProductToSpu() {
        productDao.fixedProductToSku();
    }

    @Override
    public Map<Integer, Integer> listSecondProductId2ModelIdWithMainId(Long id) {
        List<Product> result = productDao.listSecondProductId2ModelIdWithMainId(id);
        Map<Integer, Integer> ret = new HashMap<>(result.size());
        result.forEach(product -> ret.put(product.getId().intValue(), product.getWindowsU3dmodelId()));
        return ret;
    }

    @Override
    public void hardDeleteProductByIds(Set<Integer> productIds) {
        if (productIds.isEmpty()) {
            return;
        }
        productDao.hardDeleteProductByIds(productIds);
    }

    @Override
    public String getSoftHardProduct(List<Integer> ids) {
        return productDao.getSoftHardProduct(ids);
    }

    @Override
    public Map<Integer, String> queryUpPlatForm(List<Integer> ids) {
        List<Map<String, Object>> list = productDao.queryUpPlatForm(ids);
        Map<Integer, String> map = new HashMap<>(list.size());
        for (Map<String, Object> tmp : list) {
            map.put(Integer.parseInt(tmp.get("productId").toString()), tmp.get("platformId").toString());
        }
        return map;
    }

    @Override
    public String getHardProductIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return "";
        }
        return productDao.getHardProductIds(ids);
    }

    @Override
    public List<Integer> getGoodsIdsByProductIds(List<Integer> productIds) {
        return productDao.getGoodsIdsByProductIds(productIds);
    }

    @Override
    public Integer validGroupActivity(List<Integer> ids) {
        return productDao.validGroupActivity(ids);
    }

    @Override
    public Integer updateGroupActivity(List<Integer> ids) {
        return productDao.updateGroupActivity(ids,"活动商品下架导致活动失效");
    }

    @Override
    public Integer removeGroupActivity(List<Integer> ids) {
        productDao.removeGroupActivity(ids);
        return productDao.updateGroupActivity(ids,"活动商品删除导致活动失效");
    }

    @Override
    public Integer validGroupActivityBySpuId(List<Integer> ids) {
        return productDao.validGroupActivityBySpuId(ids);
    }

    @Override
    public Integer updateGroupActivityBySpuId(List<Integer> ids) {
        return productDao.updateGroupActivityBySpuId(ids);
    }

    @Override
    public List<Integer> getProductPutInfo(List<Integer> ids) {
        return productDao.getProductPutInfo(ids);
    }

	@Override
	public List<Product> getSplitTexturesInfo() {
		return productDao.getSplitTexturesInfo();
	}

	@Override
	public void updateProductBak(Long productId, int isDeleted) {
		productDao.updateProductBak(productId,isDeleted);
	}
}