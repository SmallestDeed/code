package com.sandu.designplan.service;


import com.sandu.common.exception.BizException;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.model.*;
import com.sandu.designplan.view.SharePlanDTO;
import com.sandu.designplan.vo.SuperiorPlanListVo;
import com.sandu.product.model.BaseCompany;
import com.sandu.user.model.LoginUser;

import java.util.List;

/**
 * @desc:设计方案推荐服务
 * @auth：pengxuangang
 * @date：20170920
 */
public interface DesignPlanRecommendedService {

    int add(DesignPlanRecommended designPlanRecommended);

    int update(DesignPlanRecommended designPlanRecommended);

    int delete(Integer id);

    DesignPlanRecommended get(Integer id);

    List<DesignPlanRecommended> getList(DesignPlanRecommended designPlanRecommended);

    int getCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 方案推荐数据
     *
     * @return
     */
    List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);

    /**
     * 方案推荐列表数据
     *
     * @param model
     * @return
     */
    ResponseEnvelope getPlanRecommendedList(PlanRecommendedListModel model);

    /**
     * 微信小程序方案推荐列表数据
     *
     * @param model
     * @return
     */
    ResponseEnvelope getPlanRecommendedList2(PlanRecommendedListModel model, String companyCode, BaseCompany baseCompany);

    /**
     * 方案推荐总条数
     *
     * @return
     */
    Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 方案推荐详情
     *
     * @param planRecommendedId
     * @return
     */
    DesignPlanRecommended designPlanRecommendedDetails(String planRecommendedId);

    /**
     * 获取方案推荐收藏夹数据量
     *
     * @param designPlanRecommended
     * @return
     */
    Integer getFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取方案推荐收藏夹列表
     *
     * @param designPlanRecommended
     * @return
     */
    List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取推荐方案中的渲染图片数据
     *
     * @param designPlanRecommended
     * @return
     */
    DesignPlanRecommended getRenderPicFromDesignPlanRecommend(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取推荐方案中的渲染视频数据
     *
     * @param designPlanRecommended
     * @return
     */
    DesignPlanRecommended getRenderVideoFromDesignPlanRecommend(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取推荐方案中的渲染图片和渲染视频数据---此接口包含(getRenderPicFromDesignPlanRecommend和getRenderVideoFromDesignPlanRecommend)
     *
     * @param designPlanRecommendedId         推荐方案ID
     * @param designPlanRecommendedCoverPicId 推荐方案图片ID
     * @return
     */
    DesignPlanRecommended getAllRenderFromDesignPlanRecommend(Integer designPlanRecommendedId, Integer designPlanRecommendedCoverPicId);

    List<DesignPlanRecommended> getStatusByIds(List<Long> ids);

    /**
     * 选装网 方案列表
     */
    List<DesignPlanRecommendedResult> designPlanRecommendList(DesignPlanRecommenInput designPlanRecommenInput, Integer userId);

    List<DesignPlanRecommendedResult> designPlanRecommendOpenList(DesignPlanRecommenInput designPlanRecommenInput, Integer userId);

    /**
     * 获取最适合样板房的推荐方案
     *
     * @param designPlanRecommendedVo
     * @return
     */
    DesignPlanRecommendedResult getMatchPlan(DesignPlanRecommendedVo designPlanRecommendedVo) throws BizException;

    List<SharePlanDTO> getSharePlanList(PageModel pageModel);

    Integer getSharePlanCount();

    /**
     * created by zhangchengda
     * 2018/8/31 11:36
     * 小程序查询推荐方案详情
     * last modified by zhangchengda
     * 2018/8/31 17:23
     *
     * @param model
     * @return
     * @throws BizException
     */
    DesignPlanRecommendedResult getRecommendedDesignPlanDetail(PlanRecommendedListModel model) throws BizException;

    /**
     * created by zhangchengda
     * 2018/9/17 18:34
     * 获取收藏总数
     *
     * @param designPlanRecommended
     * @return
     */
    Integer getFavoriteCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 根据方案id查询富文本内容
     *
     * @param id
     * @return
     */
    String showDesignPlanConfig(Integer id);

    /**
     * 根据方案id，产品类型查询产品
     *
     * @param planId
     * @param productBatchType
     * @return
     */
    List<DesignPlanProductVO> listDesignPlanProducts(Integer planId, Integer productBatchType, Integer pageNo);

    /**
     * 根据id查询推荐方案详情
     *
     * @param recommendedPlanGroupPrimaryId
     * @return
     */
    DesignPlanRecommended getBaseInfo(Integer recommendedPlanGroupPrimaryId);

    /**
     * if 该方案是精选方案,then designPlanRecommendedList = 该精选方案包含的所有子方案的list
     * if 该方案非精选方案,then designPlanRecommendedList = 只包含该方案的list
     *
     * @param designPlanRecommended
     * @return
     * @author huangsongbo
     */
    List<DesignPlanRecommended> getGroupDesignPlanRecommendedList(DesignPlanRecommended designPlanRecommended);

    /**
     * 查询点赞和收藏数量
     * @return
     */
    HomePageRecommendDesignPlanInfo getLikeAndCollectNum(HomePageRecommendDesignPlanInfo homePageRecommendDesignPlanInfo, LoginUser loginUser);

    Object getAllDesignPlanRecommendedSuperiorList(Integer id, Integer spaceType, Integer bizType);

    List<SuperiorPlanListVo> getSpaceDesignPlanRecommendedSuperiorList(Integer spaceType, Integer id);

    List<DesignPlanRecommendedResult> setPlanResultInfo(Integer userId, List<DesignPlanRecommendedResult> designPlanRecommendedResult);

    boolean isExistBuyDesignPlanCopyRight(Integer id, Integer planRecommendedId, int planType);
}
