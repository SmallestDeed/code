package com.sandu.api.solution.service;

import com.sandu.api.company.model.Company;
import com.sandu.api.solution.input.CompanyShopDesignPlanAdd;
import com.sandu.api.solution.input.DesignPlanUpdate;
import com.sandu.api.solution.model.DesignPlanRecommended;
import com.sandu.api.solution.model.PlanDecoratePrice;
import com.sandu.api.solution.model.bo.DecoratePriceInfo;
import com.sandu.api.solution.model.bo.DesignPlanBO;
import com.sandu.api.solution.model.bo.DesignPlanDeliverInfoBO;
import com.sandu.api.solution.model.bo.DesignPlanEffectDiagramMergeBO;
import com.sandu.api.solution.model.po.DesignPlanAllotPO;
import com.sandu.api.solution.model.po.DesignPlanProductDisplayPO;
import com.sandu.api.solution.model.po.DesignPlanPushAwayPO;

import java.util.List;
import java.util.Map;

/**
 * @author bvvy
 */
public interface DesignPlanRecommendedService {
    /**
     * 根据id物理删除数据
     *
     * @param id 主键
     * @return 受影响行数
     */
    int deleteDesignPlanRecommended(long id);

    /**
     * Selective新增数据
     *
     * @param record 对象
     * @return 新增的数据的id
     */
    long addDesignPlanRecommended(DesignPlanRecommended record);

    /**
     * 根据id查询数据
     *
     * @param id 主键
     * @return 对象数据
     */
    DesignPlanRecommended getDesignPlanRecommended(long id);

    /**
     * 基础信息
     *
     * @param id        id
     * @param companyId 公司
     * @return bo
     */
    DesignPlanBO getBaseInfo(Long id, Integer companyId);

    /**
     * 根据方案ID和方案类型获取报价
     * @param planId
     * @param planType
     * @return
     */
    List<DecoratePriceInfo> showPlanDecoratePriceInfo(Long planId, Integer planType);

    /***
     * 基础信息集
     * @param id
     * @param companyId
     * @return
     */
    List<DesignPlanBO> getBaseInfos(Long id, Integer companyId);

    /**
     * 获取基础信息
     *
     * @param id id
     * @return bo
     */
    DesignPlanBO getBaseInfo(Long id);

    /**
     * 根据id Selective修改数据
     *
     * @param designPlanUpdate 对象
     * @return 受影响的行数
     */
    Long updateDesignPlanRecommended(DesignPlanUpdate designPlanUpdate);

    /**
     * 根据id逻辑删除数据
     *
     * @param id 主键
     * @return 受影响的行数
     */
    int deleteLogicDesignPlanRecommended(long id);


    List<PlanDecoratePrice> setPlanDecoratePrice(Integer planId, List<DecoratePriceInfo> priceInfos,
                                                 Integer planType);

    /**
     * 分配
     *
     * @param designPlanAllot 参数
     */
    void allot(DesignPlanAllotPO designPlanAllot);

    /**
     * 效果图
     *
     * @param planId planid
     * @return bo
     */
    DesignPlanEffectDiagramMergeBO getEffectDiagram(Long planId);

    void handleChannelOffShelf(Integer planId, Integer platforms2c);

    /**
     * 显示不显示方案产品
     *
     * @param displayBO display
     */
    void toggleDisplay(DesignPlanProductDisplayPO displayBO);


    /**
     * 线上上架
     *
     * @param designPlanPushAwayPO po
     */
    void onlinePushaway(DesignPlanPushAwayPO designPlanPushAwayPO);

    /**
     * 渠道上架
     *
     * @param designPlanPushAwayPO po
     */
    void channelPushaway(DesignPlanPushAwayPO designPlanPushAwayPO);

    void handleOffShelf(Integer planId, Integer platforms2c);

    /**
     * id和是否公开
     *
     * @param planIds 方案
     * @return map(id, secrecy)
     */
    Map<Integer, Integer> idAndSecrecyFlagMap(List<Integer> planIds);

    /**
     * 获取一个没有使用过共享方案的公司
     *
     * @return 公司
     */
    Company getNotUseSharePlanCompanyQueue();

    /**
     * 获取有效的分享
     */
    boolean getIsValidSharePlan(Integer planId);

    List<DesignPlanRecommended> listAllCopy2CompanyPlan();

    /**
     * 保存推荐方案
     *
     * @param recommended
     * @return
     */
    int save(DesignPlanRecommended recommended);

    /**
     * 修复交付主方案未打组bug
     */
    void fixedDeliverPlanByPrimary();

    /**
     * 查询交付主从方案信息
     *
     * @return
     */
    List<DesignPlanRecommended> selectDeliverPlanByPrimary();

    /**
     * 修复交付从方案未打组bug
     *
     * @param id
     * @param groupPrimaryId
     */
    void fixedDeliverPlanBySide(Long id, Integer groupPrimaryId);

    Map<String,Object> upAndDownPlan(String planId, List<String> tocPlatformIds, List<String> tobPlatformIds, Integer putStatusUp, String designPlanType);

    Integer addCompanyShopDesignPlan(List<CompanyShopDesignPlanAdd> companyShopDesignPlanAddList, String userName, Integer shopType);

    Integer updateCompanyShopDesignPlan(List<CompanyShopDesignPlanAdd> companyShopDesignPlanAddList, String userName,Integer isDeleted, Integer shopType);

	Map<Integer, String> getDeliverTimesByPlanId(List<Integer> planIds, Integer companyId);

	Map<Integer, String> getShelfPlatForms(List<Integer> planIds);

	List<Integer> selectMainSubPlanList(List<Integer> planIds);

    List<DesignPlanDeliverInfoBO> mapDeliverTimesByPlanId(List<Integer> planIds, Integer companyId, Integer planType);

    Integer checkDesignPlanIsDeleted(Long planId);
}
