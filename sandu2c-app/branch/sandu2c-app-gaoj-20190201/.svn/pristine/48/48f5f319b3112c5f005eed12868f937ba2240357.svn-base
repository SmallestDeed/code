package com.sandu.fullhouse.service;

import com.nork.common.model.LoginUser;
import com.sandu.designplan.model.*;
import com.sandu.designplan.vo.MydecorationPlanVo;
import com.sandu.designplan.vo.PlanDecoratePriceBO;
import com.sandu.fullhouse.model.FullHouseDesignPlan;
import com.sandu.home.model.vo.BaseHouseVo;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.BaseProduct;

import java.util.List;
import java.util.Map;

public interface FullHouseDesignPlanService {
    int deleteByPrimaryKey(Integer id);

    int insert(FullHouseDesignPlan record);

    int insertSelective(FullHouseDesignPlan record);

    FullHouseDesignPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullHouseDesignPlan record);

    int updateByPrimaryKey(FullHouseDesignPlan record);

    /**
     * created by zhangchengda
     * 2018/8/29 16:59
     * 查询小程序方案列表的全屋方案
     *
     * @param planRecommendedListModel
     * @return
     */
    List<DesignPlanRecommendedResult> selectFullHouseDesignPlanList(PlanRecommendedListModel planRecommendedListModel, String companyCode, BaseCompany baseCompany);

    /**
     * created by zhangchengda
     * 2018/8/29 18:41
     * 查询符合条件的全屋方案总数
     *
     * @param planRecommendedListModel
     * @return
     */
    Integer selectFullHouseDesignPlanCount(PlanRecommendedListModel planRecommendedListModel, String companyCode, BaseCompany baseCompany);

    /**
     * 获取全屋方案装修报价信息
     * @param fullHousePlanId
     * @return
     */
    List<PlanDecoratePriceBO> getPlanDecoratePriceByFullHouseId(Integer fullHousePlanId);

    /**
     * created by zhangchengda
     * 2018/9/4 12:49
     * 查询全屋方案详情
     *
     * @param planRecommendedListModel
     * @param companyCode
     * @param baseCompany
     * @return
     */
    List<DesignPlanRecommendedResult> selectFullHouseDesignPlanDetail(PlanRecommendedListModel planRecommendedListModel, String companyCode, BaseCompany baseCompany);

    /**
     * created by zhangchengda
     * 2018/9/10 15:24
     * 查询收藏的全屋方案数量
     *
     * @param designPlanRecommended
     * @return
     */
    Integer getFavoriteFullHousePlanCount(DesignPlanRecommended designPlanRecommended);

    /**
     * created by zhangcheng
     * 2018/9/10 18:31
     * 查询收藏的全屋方案
     *
     * @param designPlanRecommended
     * @return
     */
    List<DesignPlanRecommendedResult> getFavoriteFullHousePlan(DesignPlanRecommended designPlanRecommended);

    /**
     * 通过全屋方案找到该全屋方案对应的户型
     * 
     * @author huangsongbo
     * @param fullPlanId
     * @return
     */
	Integer getHouseIdByFullPlanId(Integer fullPlanId);

    int getMydecorationPlanCount(MydecorationPlanAdd mydecorationPlanAdd);

    List<MydecorationPlanVo> getMydecorationPlanList(MydecorationPlanAdd mydecorationPlanAdd);

    int getMyPlanCount(Integer id);

    List<MydecorationPlanVo> getMyPlanList(MydecorationPlanAdd mydecorationPlanAdd);

    int updatePlanName(LoginUser loginUser, String planName, Integer planType, Integer planId, Integer taskId, Integer renderState);

    int delMyDecorationPlan(LoginUser loginUser, Integer planType, Integer planId, Integer taskId);

    String showDesignPlanConfig(Integer id);

    List<DesignPlanProductVO> listDesignPlanProducts(Integer planId, Integer productBatchType,String spaceName,Integer pageNo);

    List<ResRenderPic> getResRenderCoverPic(List<Integer> sceneIds);

    DesignPlanRecommendedResult selectFullHouseDesignPlanDetailById(Integer planId, Integer userId);

    ResRenderPic getResRenderCoverPicById(Integer businessId);

    List<DesignPlanRecommendedResult> selectFullHouseDesignPlanDetailList(List<Integer> fullHousePlanIds, Integer userId);

    int delMyDecorationPlanByHouseId(LoginUser loginUser, Integer houseId);

    List<BaseHouseVo> getAllHouse(Integer userId, Integer state);

    Integer setAskState(Integer userId, String askType, Integer taskId);
}
