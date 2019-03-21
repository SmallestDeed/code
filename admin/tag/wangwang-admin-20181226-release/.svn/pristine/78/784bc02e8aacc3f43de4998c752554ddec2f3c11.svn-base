package com.sandu.api.solution.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.product.model.bo.SolutionProductListBO;
import com.sandu.api.solution.input.*;
import com.sandu.api.solution.model.bo.*;
import com.sandu.api.solution.model.po.CopyShareDesignPlanPO;
import com.sandu.api.solution.model.po.DesignPlanChannelShelfPO;
import com.sandu.api.solution.model.po.DesignPlanDeliveryPO;
import com.sandu.common.BaseQuery;

import java.util.List;
import java.util.Map;

/**
 * @author bvvy
 */
public interface DesignPlanBizService {


    /**
     * 设计方案的产品列表
     *
     * @param query query
     * @return page
     */
    PageInfo<SolutionProductListBO> listDesignPlanProducts(DesignPlanProductQuery query);


    /**
     * 设置一键方案的默认图片
     *
     * @param designPicUpdate po
     */
    void setOneKeyPlanDefaultDiagram(DesignPicUpdate designPicUpdate);


    /**
     * 删除效果去
     *
     * @param id id
     */
    void deleteResRenderPic(Long id);

    /**
     * 禁用效果图
     *
     * @param id id
     */
    void banResRenderPic(Long id);

    /**
     * 一键方案在渠道上架
     *
     * @param shelfPO po
     */
    void onekeyChannelShelf(DesignPlanChannelShelfPO shelfPO);

    void handleCancelShelf(Integer planId, List<String> shelfStatus);

    /**
     * 普通方案在渠道上架
     *
     * @param shelfPO shelfpo
     */
    void commonChannelShelf(DesignPlanChannelShelfPO shelfPO);

    /**
     * 获取内容库一键方案列表
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listOnekeyDesignPlan(DesignPlanRecommendedQuery query);

    /**
     * 获取内容库普通方案列表
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listCommonDesignPlan(DesignPlanRecommendedQuery query);

    /**
     * 获取渠道一键方案列表
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listOnekeyChannelDesignPlan(DesignPlanRecommendedQuery query);


    PageInfo<DesignPlanBO> onkeyPlanStoreList(DesignPlanRecommendedQuery query);

    PageInfo<DesignPlanBO> commonPlanStoreList(DesignPlanRecommendedQuery query);

    /**
     * 获取渠道普通方案雷彪
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listCommonChannelDesignPlan(DesignPlanRecommendedQuery query);

    /**
     * 获取线上一键方案列表
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listOnekeyOnlineDesignPlan(DesignPlanRecommendedQuery query);

    /**
     * 获取方案已经交付的公司列表
     *
     * @param deliverCompanyId 交付公司
     * @param planId           方案
     * @return 被交付公司id
     */
    List<Integer> deliveredCompanys(Integer deliverCompanyId, Integer planId);

    /**
     * 获取方案的交付信息
     *
     * @param planId    方案
     * @param baseQuery 分页
     * @return page
     */
    PageInfo<DesignPlanDeliverBO> deliveredLog(Integer planId, BaseQuery baseQuery);

    /**
     * 分发
     *
     * @param designPlanDelivery param
     * @return id
     */
    List<Integer> deliver(DesignPlanDeliveryPO designPlanDelivery, boolean isBatch);

    /**
     * 批量分发(不能取消分发)
     */
//    List<Integer> deliverBatch(DesignPlanDeliveryPO designPlanDelivery);

    /**
     * 获取一键分享方案
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listOnekeyShareDesignPlan(ShareDesignPlanQuery query);

    /**
     * 获取普通分享方案
     *
     * @param query query
     * @return page
     */
    PageInfo<DesignPlanBO> listCommonShareDesignPlan(ShareDesignPlanQuery query);

    /**
     * Selective 获取内容库方案
     *
     * @param record 对象
     * @return list数据集合
     */
    PageInfo<DesignPlanBO> listDesignPlan(DesignPlanRecommendedQuery record);

    /**
     * 获取渠道方案
     *
     * @param record rec
     * @return page
     */
    PageInfo<DesignPlanBO> listChannelDesignPlan(DesignPlanRecommendedQuery record);

    /**
     * 获取线上方案
     *
     * @param record record
     * @return page
     */
    PageInfo<DesignPlanBO> listOnlineDesignPlan(DesignPlanRecommendedQuery record);

    /**
     * 拷贝方案去自己公司
     *
     * @param copyShareDesignPlanPO po
     * @return 方案  id list
     */
    Long copyDesignPlanToCompany2(CopyShareDesignPlanPO copyShareDesignPlanPO);

    /**
     * 拷贝方案去自己公司
     *
     * @param copyShareDesignPlanPO po
     * @return 方案  id list
     */
    void copyDesignPlanToCompany(CopyShareDesignPlanPO copyShareDesignPlanPO);

    /**
     * 初始化上架 定时器
     */
    void initDesignPlanStatus();

    /**
     * 同步上架 定时器
     */
    void synDesignPlanToPlatform();

    /**
     * 获取方案全部的可以交付的公司 和 已经交付的公司
     *
     * @param companyId 公司
     * @param planId    方案
     * @return 公司
     */
    List<CompanyWithDeliveredBO> listCompanyWithDelivered(Integer companyId, Integer planId);

    int removePlanBrand(Integer planId);

    int removeLog(Integer targetId,Integer planType);

    /**
     * 设置老数据公司 定时器
     */
    void setDesignPlanCompanyId();

    /**
     * 定时器copy design plan
     */
    void copySharePlan2Company();

    /**
     * 方案上下架
     * @param planId
     * @param platformIds
     * @param designPlanType
     * @return
     */
	public Map<String,Object> putlibraryDesignPlan(String planId,String platformIds,String designPlanType);


	/**
	 * 方案公开和取消公开
	 * @param planIds
	 * @param secrecy
	 * @return
	 */
	boolean batchChangePlanSecrecy(List<Integer> planIds, Integer secrecy);

    /**
     * 设置方案设计描述
     * @param config
     */
    void configDesignPlan(DesignPlanConfig config);

    /**
     * 获取方案设计描述
     * @param planId planId
     * @return content
     */
    String showDesignPlanConfig(Integer planId);
    int editPlanPrice(Long planId, Double planPrice, Integer chargeType);

    PageInfo<CompanyDesignPlanIncomeBO> listCompanyIncome(CompanyIncomeQuery query);

    CompanyDesignPlanIncomeAggregatedBO getCompanyDesignPlanIncomeAggregated(Long companyId);

    Double queryCompanyTotalIncome(Integer companyId);

    int editSalePrice(Long planId, Double salePrice, Integer salePriceChargeType);

    Map<String,String> getRenderPicResult(Integer planId);

    void transResourceForPathError();

    /**
     * 修复方案企业一对多问题
     */
    void fixPlanCompanyRelation();
}
