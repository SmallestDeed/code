package com.sandu.api.product.service.biz;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.product.input.EditorProductQuery;
import com.sandu.api.product.input.ProductAdd;
import com.sandu.api.product.model.bo.EditorProductListBO;
import com.sandu.api.product.model.bo.ProductBO;
import com.sandu.api.product.model.bo.ProductListBO;
import com.sandu.api.product.model.bo.SolutionProductListBO;
import com.sandu.api.product.model.po.HardProductUpdatePO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.model.po.ProductUpdatePO;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/15 9:54
 */
public interface ProductBizService {


    /**
     * 插入产品记录(业务)
     *
     * @param add
     * @return
     */
    List<Integer> saveProductBiz(ProductAdd add);

    /**
     * 更新
     *
     * @param update 更新参数
     * @return
     */
    boolean updateProductBiz(ProductUpdatePO update);

    /**
     * 更新硬装产品
     * @param updatePO
     */
    List<Integer> updateHardProductBiz(HardProductUpdatePO updatePO);

    /**
     * 根据ID查询产品详细信息
     *
     * @param productId
     * @param platformType 查询渠道类型
     * @return
     * @throws IOException
     */
    ProductBO findProductInfoByProductId(long productId, String platformType);

    /**
     * 分配产品/取消分配
     *
     * @param productIds   要分配的产品ID集合
     * @param platformType 要分配的渠道(平台类型)
     * @param allotMethod  分配操作类型(取消分配/分配)
     * @return
     */
    boolean allotProduct(List<Integer> productIds, List<String> platformType, String allotMethod);

    /**
     * 产品上架/下架
     *
     * @param productIds   要发布的产品ID集合
     * @param platformType 要上下架的平台
     * @param platformIds  上下架的具体平台id(仅2c有)
     * @param putStatus    1:上架操作/0:下架操作
     * @return
     */
    boolean putawayProduct(List<Integer> productIds, List<String> platformType, String platformIds, Integer putStatus);

    /**
     * 过滤筛选产品
     *
     * @param productQueryPO
     * @return
     */
    PageInfo<ProductListBO> queryProductByParam(ProductQueryPO productQueryPO);

    /**
     * 获取设计方案中的产品列表
     *
     * @param productQueryPO
     * @return
     */
    PageInfo<SolutionProductListBO> querySolutionProduct(ProductQueryPO productQueryPO);

    /**
     * 根据产品ID删除产品
     *
     * @param ids
     * @param platformType 2c/2b/library
     * @return
     */
    List<Integer> deleteProductsByIds(List<Integer> ids, String platformType);

    /**
     * 编辑器产品查询
     *
     * @param query
     * @return
     */
    PageInfo<EditorProductListBO> queryInEditorProductList(EditorProductQuery query);

    void initProductStatus();

    /**
     * 定时将未操作过的产品
     * 上架到各平台
     */
    void synProductToPlatform();

    /**
     * 交付产品到企业
     *
     * @param companyIds 被交付的企业IDs
     * @param productId  被交付的产品ID
     */
    void copyProduct(List<Integer> companyIds, List<Integer> productId);

    List<CompanyWithDeliveredBO> getProductDeliveredInfo(Integer productId);

    List<String> getProductNumberWordRecommends(String keyWord,Integer brandId,Integer companyId);

    void synProductCompanyInfoWithBrandId();

    void synProductPutawayState();

    /**
     * 图片迁移
     */
	void picTransfer();

    /**
     *  修复三度后台创建产品未关联spu问题
     */
    void fixedProductToSpu();

    /**
     * 产品库产品上下架
     * @param ids
     * @param allotPlatformType
     * @param tobPlatformIds
     * @param operateType
     * @return
     */
	Map<String,Object> upAndDownProduct(List<Integer> ids, List<String> allotPlatformType, List<String> tobPlatformIds, Integer operateType);

	List<Integer> getAllProductIds(List<Integer> ids);

    /**
     * 通过productIds查询商品ID集合goodsIds
     * @param productIds
     * @return
     */
    List<Integer>getGoodsIdsByProductIds(List<Integer> productIds);

    /**
     * 校验是否加入拼团活动skuId
     * @param ids
     * @return
     */
    Integer validGroupActivity(List<Integer> ids);

    /**
     * 更新拼团活动状态skuId
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
     * 校验是否加入拼团活动根据spuId
     * @param ids
     * @return
     */
    Integer validGroupActivityBySpuId(List<Integer> ids);

    /**
     * 更新拼团活动状态spuId
     * @param ids
     */
    Integer updateGroupActivityBySpuId(List<Integer> ids);

    /**
     * 获得产品的小程序平台上下架状态
     * @param ids
     * @return
     */
    List<Integer> getProductPutInfo(List<Integer> ids);
}
