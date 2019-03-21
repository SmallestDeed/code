package com.sandu.service.solution.dao;

import com.sandu.api.company.model.Company;
import com.sandu.api.solution.input.DesignPlanProductQuery;
import com.sandu.api.solution.input.DesignPlanRecommendedQuery;
import com.sandu.api.solution.input.ShareDesignPlanQuery;
import com.sandu.api.solution.model.CompanyShopDesignPlan;
import com.sandu.api.solution.model.DesignPlan;
import com.sandu.api.solution.model.DesignPlanRecommended;
import com.sandu.api.solution.model.bo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Roc Chen
 * @Description 推荐设计方案 Dao
 * @Date:Created Administrator in 20:51 2017/12/20 0020
 * @Modified By:
 */
@Repository
public interface DesignPlanRecommendedMapper {

    /**
     * insert
     * @param record record
     * @return 1 0
     */
    int insertSelective(DesignPlanRecommended record);

    /**
     * get one
     * @param id id
     * @return one
     */
    DesignPlanRecommended selectByPrimaryKey(Long id);

    /**
     * 更新
     * @param record record
     * @return 1, 0
     */
    int updateByPrimaryKeySelective(DesignPlanRecommended record);


    /**
     * 逻辑删除
     * @param id id
     * @return 1 , 0
     */
    int deleteLogicByPrimaryKey(Long id);

    /**
     * 全部列表
     * @param query query
     * @return list
     */
    List<DesignPlanBO> selectListSelective(DesignPlanRecommendedQuery query);

    /**
     * 获取方案基本信息
     * @param id id
     * @param companyId 公司id
     * @return 方案信息
     */
    DesignPlanBO getBaseInfo(@Param("id") Long id, @Param("companyId") Integer companyId);

    /**
     * 获取方案指定的效果图类型的效果图
     * @param planId 方案
     * @param renderingType 效果图类型
     * @return 效果图
     */
    List<DesignPlanEffectDiagramBO> listAllDiagram(@Param("planId") Long planId, @Param("renderingType") Integer renderingType);

    List<DesignPlanEffectDiagramBO> listSiglesDiagram(@Param("planId") Long planId, @Param("renderingType") Integer renderingType);

    /**
     * 获取设计方案的产品列表
     * @param query query
     * @return list
     */
    List<DesignPlanProductBO> listDesignPlanProducts(DesignPlanProductQuery query);

    /**
     * 获取渠道全部方案列表
     * @param query query
     * @return list
     */
    List<DesignPlanBO> listChannelOneKeyDesignPlan(DesignPlanRecommendedQuery query);

    /**
     * 获取线上全部方案列表
     * @param query query
     * @return list
     */
    List<DesignPlanBO> listOnlineOneKeyDesignPlan(DesignPlanRecommendedQuery query);


    /**
     * 线上上架
     *
     * @param planId     方案id
     * @param platformId 平台id
     */
    void putOnOnlineShelf(@Param("planId") Integer planId, @Param("platformId") Integer platformId);

    /**
     * 线上下架
     *
     * @param planId     方案id
     * @param platformId 平台id
     */
    void offOnOnlineShelf(@Param("planId") Integer planId, @Param("platformId") Integer platformId);

    /**
     * 渠道上架
     *
     * @param planId     方案
     * @param platformId 平台id
     */
    void putOnChannelShelf(@Param("planId") Integer planId, @Param("platformId") Integer platformId);

    /**
     * 渠道下架
     *
     * @param planId     方案id
     * @param platformId 平台下架
     */
    void offOnChannelShelf(@Param("planId") Integer planId, @Param("platformId") Integer platformId);

    /**
     * 获取分享方案
     *
     * @param query query
     * @return list
     */
    List<DesignPlanBO> listShareDesignPlan(ShareDesignPlanQuery query);

    /**
     * 获取没有上架分配的 老数据处理 定时任务
     *
     * @param planType 方案类型
     * @return 列表
     */
    List<DesignPlanBO> listNotShelfDesignPlan(@Param("planType") Integer planType);

    /**
     * 获取全部公司 并获取是否已经交付过了
     *
     * @param companyId 公司
     * @param planId    方案
     * @return 全部公司信息
     */
    @Select("select distinct company_name,companyId,if(t2.id is null, 0, 1 ) as delivered from (SELECT DISTINCT bc.company_name,id as companyId " +
            " FROM base_company bc where is_deleted = 0 and business_type!=2 and business_type !=3) t1 " +
            " LEFT JOIN (select  *  from design_plan_copy_log bpdl" +
            " WHERE bpdl.source_id = #{planId} and source_company_id = #{companyId} AND kind = 2 AND plan_type = 1) t2 on t1.companyId = t2.target_company_id where  t1.companyId != #{companyId}  order by delivered desc")
    List<CompanyWithDeliveredBO> listCompanyWithDelivered(@Param("companyId") Integer companyId, @Param("planId") Integer planId);


    /**
     * 定时任务 设置方案公司
     */
    void setDesignPlanCompanyId();

    /**
     * 获取公开状态
     * @param planIds planIds
     * @return planIds
     */
    List<SecrecyFlagBO> listSecrecyFlagByPlanIds(@Param("planIds") List<Integer> planIds);

    /**
     *
     * @return company
     */
    Company getNotUseSharePlanCompanyQueue();

    /**
     * 判断是不是有效的分享 > 0 有效, < 0 无效
     * @return num
     */
    Integer getIsValidSharePlan(Integer planId);



    List<DesignPlanRecommended> listAllCopy2CompanyPlan();

    /**
     * 根据方案id查询方案shelf_status字段
     */
    DesignPlanRecommended getDesignById(@Param("id") Integer id);

    int save(DesignPlanRecommended recommended);

    DesignPlanRecommended getPrimaryPlan(@Param("planId") Integer planId);

    List<DesignPlanRecommended> queryPrimaryPlan(@Param("planId") Integer planId);

    void updateDeleverPlanByPrimary();

    List<DesignPlanRecommended> selectDeliverPlanByPrimary();

    void fixedDeliverPlanBySide(@Param("id") Long id,@Param("groupPrimaryId") Integer groupPrimaryId);

	int updateDesignPlanById(@Param("planIds") List<String> planIds, @Param("shelfStatus")String shelfStatus);

	void batchUpdatePlanSecrecy(@Param("planIds")List<Integer> updateIds, @Param("secrecyFlag")Integer secrecyFlag);

    Integer insertCompanyShopDesignPlan(@Param("companyShopDesignPlanList") List<CompanyShopDesignPlan> companyShopDesignPlanList);

    Integer updateCompanyShopDesignPlan(@Param("companyShopDesignPlanList") List<CompanyShopDesignPlan> companyShopDesignPlanList
    ,@Param("date") Date date,@Param("userName") String userName,@Param("isDeleted")Integer isDeleted);

    List<DesignPlanBO> planStoreList(DesignPlanRecommendedQuery query);


    int offShelf2cFullHouse(@Param("planId") Long planId, @Param("platId") Integer platId);

    int offShelf2bFullHouse(@Param("planId") Long planId, @Param("platId") Integer platId);

    int putOn2cFullHouse(@Param("planId") Integer planId, @Param("platId") Integer platformId);

    int putOn2bFullHouse(@Param("planId") Integer planId, @Param("platId") Integer platformId);


    List<CompanyShopDesignPlan> selectAllStorePlan(DesignPlanRecommendedQuery query);

	List<Map<String, Object>> getDeliverTimesByPlanId(@Param("planIds")List<Integer> planIds, @Param("companyId")Integer companyId);

	List<Map<String, Object>> getShelfPlatForms(@Param("planIds")List<Integer> planIds);

	List<Integer> selectMainSubPlanList(@Param("planIds")List<Integer> planIds);

    String getRenderPic(Integer planId);

    String getVr720Single(Integer planId);

    Integer getVr720Roam(Integer planId);

    String getUUID(Integer renderPicId);

    Integer getVideoPicPath(Integer planId);

    String getPath(Integer planId);

    List<Integer> getPersonalShopIdList(Integer shopId);

    Integer getMainShopId(Integer shopId);

    Integer updateMainCompanyShopDesignPlan(@Param("companyShopDesignPlanList") List<CompanyShopDesignPlan> companyShopDesignPlanList,
                                            @Param("date") Date date,
                                            @Param("userName") String userName,
                                            @Param("mainShopId") Integer mainShopId,
                                            @Param("isDeleted") Integer isDeleted);

    Integer updateCompanyShopDesignPlanSelective(CompanyShopDesignPlan companyShopDesignPlan);
}