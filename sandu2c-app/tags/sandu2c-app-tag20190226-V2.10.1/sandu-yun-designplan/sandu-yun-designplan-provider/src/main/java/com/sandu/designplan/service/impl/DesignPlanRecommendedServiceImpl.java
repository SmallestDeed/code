package com.sandu.designplan.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.common.exception.BizException;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.properties.ResProperties;
import com.sandu.common.util.Constants;
import com.sandu.common.util.GsonUtil;
import com.sandu.common.util.Utils;
import com.sandu.constant.ResponseEnum;
import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.DesignPlanRecommendedProduct;
import com.sandu.design.model.RecommendedPublicState;
import com.sandu.designplan.dao.DesignPlanRecommendedMapper;
import com.sandu.designplan.model.*;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.designplan.service.DesignPlanRecommendedProductService;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.designplan.service.DesignPlanSummaryInfoService;
import com.sandu.designplan.view.SharePlanDTO;
import com.sandu.designplan.vo.PlanDecoratePriceBO;
import com.sandu.designplan.vo.SuperiorPlanListVo;
import com.sandu.exception.AppException;
import com.sandu.exception.GlobalExceptionResolver;
import com.sandu.fullhouse.dao.FullHouseDesignPlanMapper;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.service.SpaceCommonService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.service.BaseBrandService;
import com.sandu.product.service.BaseProductService;
import com.sandu.product.service.BaseProductStyleService;
import com.sandu.render.model.RenderTypeCode;
import com.sandu.render.model.ResRenderData;
import com.sandu.system.model.ResFile;
import com.sandu.system.model.ResRenderVideo;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.*;
import com.sandu.user.model.*;
import com.sandu.user.service.SysRoleService;
import com.sandu.user.service.SysUserRoleService;
import com.sandu.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sandu.cache.RedisKeyConstans.DESIGN_PLAN_VIEW_NUM_MAP;
import static com.sandu.cache.RedisKeyConstans.DESIGN_PLAN_VIEW_NUM_SYNCHRONIZE_MAP;
import static java.util.stream.Collectors.toList;


/**
 * @desc:设计方案服务
 * @auth：pengxuangang
 * @date：20170920
 */

@Service("designPlanRecommendedService")
public class DesignPlanRecommendedServiceImpl implements DesignPlanRecommendedService {



    @Value("${app.onekey.url}")
    private String appOnekeyUrl;
    @Value("${file.storage.path}")
    private String basePath;
    //Json转换类
    private final static Gson GSON = new Gson();

    private final static Cache<String, List<DesignPlanRecommendedResult>> LOCAL_CACHE_PREFER_PLAN = CacheBuilder
            .newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    private final static String LOCAL_CACHE_PREFER_PLAN_KEY = "LOCAL_CACHE_PREFER_PLAN_KEY";




    private final static String CLASS_LOG_PREFIX = "[方案推荐服务]:";
    private static Logger logger = LoggerFactory.getLogger(DesignPlanRecommendedServiceImpl.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ResRenderVideoService resRenderVideoService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private DesignPlanRecommendedMapper designPlanRecommendedMapper;
    @Autowired
    private ResRenderPicService resRenderPicService;
    @Autowired
    private DesignPlanRecommendedProductService designPlanRecommendedProductService;
    @Autowired
    private BaseProductStyleService baseProductStyleService;
    @Autowired
    private ResFileService resFileService;
    @Autowired
    private DesignPlanLikeService designPlanLikeService;
    @Autowired
    private DesignPlanSummaryInfoService designPlanSummaryInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private BaseBrandService baseBrandService;
    @Autowired
    private NodeInfoBizService nodeInfoBizService;
    @Autowired
    private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;
    @Override
    public int delete(Integer id) {
        return designPlanRecommendedMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DesignPlanRecommended get(Integer id) {
        return designPlanRecommendedMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DesignPlanRecommended> getList(DesignPlanRecommended designPlanRecommended) {
        return designPlanRecommendedMapper.selectList(designPlanRecommended);
    }

    @Override
    public int add(DesignPlanRecommended designPlanRecommended) {
        designPlanRecommendedMapper.insertSelective(designPlanRecommended);
        return designPlanRecommended.getId();
    }

    @Override
    public int update(DesignPlanRecommended designPlanRecommended) {
        return designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
    }

    @Override
    public int getCount(DesignPlanRecommended designPlanRecommended) {
        return designPlanRecommendedMapper.selectCount(designPlanRecommended);
    }

    /**
     * 方案推荐数据
     *
     * @return
     */
    @Override
    public List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended) {
        if (designPlanRecommended == null) {
            return null;
        }
        /*是方案管理员 必须要传空间类型*/
        if ("yes".equals(designPlanRecommended.getCheckAdministrator())) {
            if (designPlanRecommended.getSpaceFunctionIds() == null || designPlanRecommended.getSpaceFunctionIds().size() <= 0) {
                return null;
            }

        }
        return designPlanRecommendedMapper.getPlanRecommendedList(designPlanRecommended);
    }

    /**
     * 方案推荐列表数据
     *
     * @param model
     */
    @Override
    public ResponseEnvelope getPlanRecommendedList(PlanRecommendedListModel model) {

        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据:PlanRecommendedListModel:{}", null == model ? null : model.toString());

        if (null == model) {
            logger.warn(CLASS_LOG_PREFIX + "获取方案推荐列表数据失败,PlanRecommendedListModel is null!");
            return new ResponseEnvelope(false, "获取方案推荐列表数据失败,PlanRecommendedListModel is null!");
        }

        DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();

        String houseType = model.getHouseType();
        String livingName = model.getLivingName();
        String areaValue = model.getAreaValue();
        String designRecommendedStyleId = model.getDesignRecommendedStyleId();
        String displayType = model.getDisplayType();
        String creator = model.getCreator();//搜索条件：创建者
        String brandName = model.getBrandName();//搜索条件：品牌
        LoginUser loginUser = model.getLoginUser();
        Integer limit = model.getLimit();
        Integer start = model.getStart();
        Integer isSortByReleaseTime = model.getIsSortByReleaseTime();
        Integer isSortByRenderCount = model.getIsSortByRenderCount();
        Integer companyId = model.getCompanyId();
        Integer id = model.getId();

        //检查用户是否登录
        if (null == loginUser) {
            loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setUserType(UserTypeCode.USER_TYPE_OUTER_B2C);
        }

        designPlanRecommended.setCompanyId(companyId);
        /* 装着品牌id的list，没有授权码的用户也能看到 ，选择“推荐所有”的设计方案，推荐所有的设计方案品牌ID = -1 ,三度空间*/

        /* 查询 */
        designPlanRecommended.setDisplayType(displayType);
        if ("decorate".equals(displayType)) {/* 1代表祝列表。其他代表一键装修处的小列表。小列表只能查询支持一件装修的数据 */
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
        } else if ("dragDecorate".equals(displayType)) {
            //运营网站用户仅能看到已发布推荐方案
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
        } else if ("test".equals(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_TEST_RELEASE);
        } else if ("check".equals(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.WAITING_CHECK_RELEASE);
            /*判断用户是什么类型管理员*/
            List<Integer> spaceFunctionIds = null;
            spaceFunctionIds = this.designPlanRecommendedCheckType(loginUser.getId());
            if (spaceFunctionIds == null || spaceFunctionIds.size() <= 0) {
                return new ResponseEnvelope(false, "无权限！");
            }
            designPlanRecommended.setSpaceFunctionIds(spaceFunctionIds);
            designPlanRecommended.setCheckAdministrator("yes");/*是方案审核管理员*/
        } else if ("public".equals(displayType) || StringUtils.isEmpty(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_SHARE);
            designPlanRecommended.setIsRelease(RecommendedPublicState.IS_OPEN);
        } else if ("mobile".equals(displayType)) {
            designPlanRecommended.setIsRelease(RecommendedPublicState.IS_OPEN);
        }
        //根据时间排序
        if (null != isSortByReleaseTime && null == isSortByRenderCount) {
            designPlanRecommended.setIsSortByReleaseTime(isSortByReleaseTime);
        }
        //根据渲染次数排序
        if (null == isSortByReleaseTime && null != isSortByRenderCount) {
            designPlanRecommended.setIsSortByRenderCount(isSortByRenderCount);
        }
        if (id != null) {
            designPlanRecommended.setId(id);
        }
        //根据平台过滤
        if (model.getPlatformId() != null && model.getPlatformId() > 0) {
            designPlanRecommended.setPlatformId(model.getPlatformId());
        }

        if (StringUtils.isNotEmpty(brandName)) { /*品牌名*/
            designPlanRecommended.setBrandName(brandName.trim());
        }
        if (StringUtils.isNotEmpty(creator)) { /*创建者*/
            designPlanRecommended.setCreator(creator.trim());
        }
        if (StringUtils.isNotEmpty(houseType) && (!"null".equals(houseType))) { /*空间功能类型 */
            designPlanRecommended.setSpaceFunctionId(Integer.parseInt(houseType));
        }
        if (StringUtils.isNotEmpty(areaValue)) {
            designPlanRecommended.setAreaValue(areaValue);
        }
        if (StringUtils.isNotEmpty(livingName)) { /* 小区名称 */
            designPlanRecommended.setLivingName(livingName);
        }
        if (StringUtils.isNotEmpty(designRecommendedStyleId)) { /* 推荐方案风格 */
            designPlanRecommended.setDesignRecommendedStyleId(Integer.parseInt(designRecommendedStyleId));
        }

        //空间形状
        if (null != model.getSpaceShape()) { /* 小区名称 */
            designPlanRecommended.setSpaceShape(model.getSpaceShape());
        }

        if (limit != null) {
            designPlanRecommended.setLimit(limit);
        }
        if (start != null) {
            designPlanRecommended.setStart(start);
        }
        designPlanRecommended.setUserId(loginUser.getId());
        Integer total = 0;
        List<DesignPlanRecommendedResult> list = null;

        total = this.getPlanRecommendedCount(designPlanRecommended);
        if (total != null && total.intValue() > 0) {
            list = this.getPlanRecommendedList(designPlanRecommended);

            List<Integer> planRecommendIdList = list.stream()
                    .map(DesignPlanRecommendedResult::getPlanRecommendedId)
                    .collect(Collectors.toList());

            List<Integer> designPlanIds = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (DesignPlanRecommendedResult result : list) {

                    //获取用户购买方案版权记录
                    designPlanIds = designPlanRecommendedMapper.selectDesignPlanIdsFromRecord(loginUser.getId());

                    if (result.getChargeType().intValue() == 1 && !designPlanIds.contains(result.getPlanRecommendedId())) {
                        result.setCopyRightPermission(1);
                    } else {
                        //免费方案,无需购买版权
                        result.setCopyRightPermission(0);
                    }

                    logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据:根据推荐方案Id和渲染类型获得最新渲染原图:PlanRecommendedId:{}, renderingType:1.", result.getPlanRecommendedId());
                    ResRenderData res = resRenderPicService.getResRenderPicByPlanRecommended
                            (result.getPlanRecommendedId(), 1);// 得到最新一张照片渲染原图地址
                    logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据:根据推荐方案Id和渲染类型获得最新渲染原图完成:ResRenderPic:{}.", null == res ? null : res.toString());
                    if (res != null) {
                        result.setResRenderPicPath(res.getPicPath());
                    }

                    //添加图片资源HOST拼接
                    result.setCoverPath(result.getCoverPath());
//                    if (StringUtils.isEmpty(result.getBid())) {
//                        result.setBid("0");
//                    }

                    //从缓存中获取方案点赞收藏数量信息、
                    //从缓存中获取用户对方案是否点赞，是否收藏
                    DesignPlanSummaryInfo summaryInfo =
                            designPlanLikeService.getPlanInfoOfCache(loginUser.getId(), result.getPlanRecommendedId());
                    if (null != summaryInfo) {
                        if (null != summaryInfo.getLikeNum()) {
                            result.setLikeNum(summaryInfo.getLikeNum());
                        }
                        if (null != summaryInfo.getCollectNum()) {
                            result.setCollectNum(summaryInfo.getCollectNum());
                        }
                        if (null != summaryInfo.getIsLike()) {
                            result.setIsLike(summaryInfo.getIsLike());
                        }
                        if (null != summaryInfo.getIsFavorite()) {
                            result.setIsFavorite(summaryInfo.getIsFavorite());
                        }
                    }
                }
            }
        }

//        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据完成:total:{}, list:{}.", total, GSON.toJson(list));
        return new ResponseEnvelope(true, "", list, null == total ? 0 : total);
    }

    /**
     * 方案推荐列表数据
     *
     * @param model
     */
    @Override
    public ResponseEnvelope getPlanRecommendedList2(PlanRecommendedListModel model, String companyCode, BaseCompany baseCompany) {

        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据:PlanRecommendedListModel:{}", null == model ? null : model.toString());

        if (null == model) {
            logger.warn(CLASS_LOG_PREFIX + "获取方案推荐列表数据失败,PlanRecommendedListModel is null!");
            return new ResponseEnvelope(false, "获取方案推荐列表数据失败,PlanRecommendedListModel is null!");
        }

        DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();

        String houseType = model.getHouseType();
        String livingName = model.getLivingName();
        String areaValue = model.getAreaValue();
        String designRecommendedStyleId = model.getDesignRecommendedStyleId();
        String displayType = model.getDisplayType();
        String creator = model.getCreator();//搜索条件：创建者
        String brandName = model.getBrandName();//搜索条件：品牌
        LoginUser loginUser = model.getLoginUser();
        Integer limit = model.getLimit();
        Integer start = model.getStart();
        Integer isSortByReleaseTime = model.getIsSortByReleaseTime();
        Integer isSortByRenderCount = model.getIsSortByRenderCount();
        Integer companyId = model.getCompanyId();
        Integer id = model.getId();

        //检查用户是否登录
        if (null == loginUser) {
            loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setUserType(UserTypeCode.USER_TYPE_OUTER_B2C);
        }

        designPlanRecommended.setCompanyId(companyId);


        /* 查询 */
        designPlanRecommended.setDisplayType(displayType);
        if ("decorate".equals(displayType)) {/* 1代表祝列表。其他代表一键装修处的小列表。小列表只能查询支持一件装修的数据 */
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
        } else if ("dragDecorate".equals(displayType)) {
            //运营网站用户仅能看到已发布推荐方案
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
        } else if ("test".equals(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_TEST_RELEASE);
        } else if ("check".equals(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setIsRelease(RecommendedDecorateState.WAITING_CHECK_RELEASE);
            /*判断用户是什么类型管理员*/
            List<Integer> spaceFunctionIds = null;
            spaceFunctionIds = this.designPlanRecommendedCheckType(loginUser.getId());
            if (spaceFunctionIds == null || spaceFunctionIds.size() <= 0) {
                return new ResponseEnvelope(false, "无权限！");
            }
            designPlanRecommended.setSpaceFunctionIds(spaceFunctionIds);
            designPlanRecommended.setCheckAdministrator("yes");/*是方案审核管理员*/
        } else if ("public".equals(displayType) || StringUtils.isEmpty(displayType)) {
            designPlanRecommended.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_SHARE);
            designPlanRecommended.setIsRelease(RecommendedPublicState.IS_OPEN);
        } else if ("mobile".equals(displayType)) {
            designPlanRecommended.setIsRelease(RecommendedPublicState.IS_OPEN);
        }
        if (id != null) {
            designPlanRecommended.setId(id);
        }
        //根据时间排序
        if (null != isSortByReleaseTime && null == isSortByRenderCount) {
            designPlanRecommended.setIsSortByReleaseTime(isSortByReleaseTime);
        }
        //根据渲染次数排序
        if (null == isSortByReleaseTime && null != isSortByRenderCount) {
            designPlanRecommended.setIsSortByRenderCount(isSortByRenderCount);
        }
        //根据平台过滤
        if (model.getPlatformId() != null && model.getPlatformId() > 0) {
            designPlanRecommended.setPlatformId(model.getPlatformId());
        }

        if (StringUtils.isNotEmpty(brandName)) { /*品牌名*/
            designPlanRecommended.setBrandName(brandName.trim());
        }
        if (StringUtils.isNotEmpty(creator)) { /*创建者*/
            designPlanRecommended.setCreator(creator.trim());
        }
        if (StringUtils.isNotEmpty(houseType) && (!"null".equals(houseType))) { /*空间功能类型 */
            designPlanRecommended.setSpaceFunctionId(Integer.parseInt(houseType));
        }
        if (StringUtils.isNotEmpty(areaValue)) {
            designPlanRecommended.setAreaValue(areaValue);
        }
        if (StringUtils.isNotEmpty(livingName)) { /* 小区名称 */
            designPlanRecommended.setLivingName(livingName);
        }
        if (StringUtils.isNotEmpty(designRecommendedStyleId)) { /* 推荐方案风格 */
            designPlanRecommended.setDesignRecommendedStyleId(Integer.parseInt(designRecommendedStyleId));
        }
        if (StringUtils.isNotEmpty(model.getEnterType())) {
            designPlanRecommended.setEnterType(model.getEnterType());
        }
        //空间形状
        if (null != model.getSpaceShape()) { /* 小区名称 */
            designPlanRecommended.setSpaceShape(model.getSpaceShape());
        }
        if (limit != null) {
            designPlanRecommended.setLimit(limit);
        }
        if (start != null) {
            designPlanRecommended.setStart(start);
        }
        //装修报价类型
        if (model.getDecoratePriceType() != null && model.getDecoratePriceType() > 0) {
            designPlanRecommended.setDecoratePriceType(model.getDecoratePriceType());
        }
        //装修报价区间
        if (model.getDecoratePriceRange() != null && model.getDecoratePriceRange() > 0) {
            designPlanRecommended.setDecoratePriceRange(model.getDecoratePriceRange());
        }
        //装修风格
        if (StringUtils.isNotBlank(model.getDesignRecommendedStyleName())) {
            designPlanRecommended.setDesignRecommendedStyleName(model.getDesignRecommendedStyleName());
        }
        designPlanRecommended.setUserId(loginUser.getId());
        Integer total = 0;
        List<DesignPlanRecommendedResult> list = null;
        /*公司为三度云享家展示平台所有已发布的一键方案+公开方案*/
        if (companyCode.equals(baseCompany.getCompanyCode())) {
            designPlanRecommended.setBrandIds(null);
            designPlanRecommended.setCompanyId(null);
            designPlanRecommended.setRecommendedType(null);
            List<Integer> types = new ArrayList<>();
            types.add(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            designPlanRecommended.setRecommendedTypes(types);
            designPlanRecommended.setOnlyInternalPlan(Constants.ONLY_SEARCH_INTERNAL_PLAN);//Add by steve, only search internal plan if the company is sandu
            designPlanRecommended.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
        } else {
            designPlanRecommended.setBrandIds(model.getBrandList());//If the company is not SanDu, add the filter for brand. updated by steve
        }

        total = this.getPlanRecommendedCount(designPlanRecommended);
        if (total != null && total.intValue() > 0) {
            list = this.getPlanRecommendedList(designPlanRecommended);
            list = this.setPlanResultInfo(loginUser.getId(), list);
        }


//        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据完成:total:{}, list:{}.", total, GSON.toJson(list));
        return new ResponseEnvelope(true, "", list, null == total ? 0 : total);
    }

    @Override
    public List<DesignPlanRecommendedResult> setPlanResultInfo(Integer userId, List<DesignPlanRecommendedResult> list) {
        List<Integer> recommendedIds = new ArrayList<Integer>();
        List<Integer> designPlanIds = new ArrayList<>();

        for (DesignPlanRecommendedResult result : list) {
            recommendedIds.add(result.getPlanRecommendedId());
            //获取用户购买方案版权记录
            designPlanIds = designPlanRecommendedMapper.selectDesignPlanIdsFromRecord(userId);

            if (result.getChargeType().intValue() == 1 && !designPlanIds.contains(result.getPlanRecommendedId())) {
                result.setCopyRightPermission(1);
            } else {
                //免费方案,无需购买版权
                result.setCopyRightPermission(0);
            }

            if (designPlanIds.contains(result.getPlanRecommendedId())) {
                result.setHavePurchased(1);
            } else {
                result.setHavePurchased(0);
            }

        }
        List<ResRenderPic> resPicList = resRenderPicService.getResRenderPicListByRecommendedIds(recommendedIds);
        for (DesignPlanRecommendedResult result : list) {
            if (result.getShops() != null && result.getShops().size() > 0) {
                result.setShopId(result.getShops().get(0).getShopId());
                result.setDesignerHeadPic(result.getShops().get(0).getLogo());
            }
            for (ResRenderPic res : resPicList) {
                if (res != null && res.getPlanRecommendedId().intValue() == result.getPlanRecommendedId().intValue()) {
                    result.setResRenderPicPath(res.getPicPath());
                }
            }

            //获取装修报价信息
            List<PlanDecoratePriceBO> planDecoratePriceBOList =
                    designPlanRecommendedMapper.getPlanDecoratePrice(result.getDesignPlanRecommendId());
            result.setPlanDecoratePriceList(planDecoratePriceBOList);

            //从缓存中获取方案点赞收藏数量信息、
            //从缓存中获取用户对方案是否点赞，是否收藏
            setDesignPlanSummary(userId, list);
        }
        return list;
    }


    private void setPlanResultInfo2(Integer userId, List<DesignPlanRecommendedResult> list) {
        List<Integer> recommendedIds = new ArrayList<Integer>();
        List<Integer> designPlanIds = new ArrayList<>();

//        for (DesignPlanRecommendedResult result : list) {
//            if(result.getSpaceFunctionId().intValue() !=13){
//                recommendedIds.add(result.getPlanRecommendedId());
//                //获取用户购买方案版权记录
//                designPlanIds = designPlanRecommendedMapper.selectDesignPlanIdsFromRecord(userId);
//
//                if (result.getChargeType().intValue() == 1 && !designPlanIds.contains(result.getPlanRecommendedId())) {
//                    result.setCopyRightPermission(1);
//                } else {
//                    //免费方案,无需购买版权
//                    result.setCopyRightPermission(0);
//                }
//
//                if (designPlanIds.contains(result.getPlanRecommendedId())) {
//                    result.setHavePurchased(1);
//                } else {
//                    result.setHavePurchased(0);
//                }
//            }
//
//
//        }
        for (DesignPlanRecommendedResult result : list) {
            if (result.getShops() != null && result.getShops().size() > 0) {
                result.setShopId(result.getShops().get(0).getShopId());
                result.setDesignerHeadPic(result.getShops().get(0).getLogo());
            }
            //获取装修报价信息
            List<PlanDecoratePriceBO> planDecoratePriceBOList =
                    designPlanRecommendedMapper.getPlanDecoratePrice(result.getDesignPlanRecommendId());
            result.setPlanDecoratePriceList(planDecoratePriceBOList);


        }
    }

    private void setDesignPlanSummary(Integer userId, List<DesignPlanRecommendedResult> designPlanRecommendedResults) {
        for (DesignPlanRecommendedResult result : designPlanRecommendedResults) {
            DesignPlanSummaryInfo summaryInfo =
                    designPlanLikeService.getPlanInfoOfCache(userId, result.getPlanRecommendedId());
            if (null != summaryInfo) {
                if (null != summaryInfo.getLikeNum()) {
                    result.setLikeNum(summaryInfo.getLikeNum());
                }
                if (null != summaryInfo.getCollectNum()) {
                    result.setCollectNum(summaryInfo.getCollectNum());
                }
                if (null != summaryInfo.getIsLike()) {
                    result.setIsLike(summaryInfo.getIsLike());
                }
                if (null != summaryInfo.getIsFavorite()) {
                    result.setIsFavorite(summaryInfo.getIsFavorite());
                }
                if (null != summaryInfo.getViewNum()) {
                    result.setViewNum(summaryInfo.getViewNum());
                }
            }
        }
    }





    private void setDesignPlanSummary2(Integer userId, List<DesignPlanRecommendedResult> designPlanRecommendedResults) {
        logger.error("数据库 designPlanRecommendedResults=======>"+designPlanRecommendedResults);
        for (DesignPlanRecommendedResult result : designPlanRecommendedResults) {
            if (result.getShops() != null && result.getShops().size() > 0){
                result.setShopId(result.getShops().get(0).getShopId());
                result.setDesignerHeadPic(result.getShops().get(0).getLogo());
            }
            DesignPlanSummaryInfo summaryInfo = null;
            if (result.getSpaceFunctionId() != 13){
                summaryInfo = designPlanLikeService.getPlanInfoOfCache(userId, result.getPlanRecommendedId());
            }else {
                summaryInfo = designPlanLikeService.getFullHousePlanInfoOfCache(userId, result.getPlanRecommendedId());
            }
            if (null != summaryInfo) {
                if (null != summaryInfo.getLikeNum()) {
                    result.setLikeNum(summaryInfo.getLikeNum());
                }
                if (null != summaryInfo.getCollectNum()) {
                    result.setCollectNum(summaryInfo.getCollectNum());
                }
                if (null != summaryInfo.getIsLike()) {
                    result.setIsLike(summaryInfo.getIsLike());
                }
                if (null != summaryInfo.getIsFavorite()) {
                    result.setIsFavorite(summaryInfo.getIsFavorite());
                }
                if (null != summaryInfo.getViewNum()) {
                    result.setViewNum(summaryInfo.getViewNum());
                }
            }
        }
        logger.error("查完缓存 designPlanRecommendedResults=======>"+designPlanRecommendedResults);
    }
    /**
     * 判断该审核管理员能审核多少种空间类型
     *
     * @param userId
     * @return
     */
    public List<Integer> designPlanRecommendedCheckType(Integer userId) {
        List<Integer> spaceFunctionIds;

        if (userId == null) {
            return null;
        }
        List<SysUserRole> sysUserRoleList = null;
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setIsDeleted(0);
        sysUserRoleList = sysUserRoleService.getList(sysUserRole);

        List<SysRole> roleList = null;
        SysRoleSearch sysRoletSearch = new SysRoleSearch();
        sysRoletSearch.setLimit(-1);
        sysRoletSearch.setIsDeleted(0);
        sysRoletSearch.setSch_Code_("RECOMMENDEDCHECK_");
        roleList = sysRoleService.getPaginatedList(sysRoletSearch);

        List<String> spaceTypeList = new ArrayList<String>();
        for (SysUserRole sysUserRole_ : sysUserRoleList) {
            int roleId = sysUserRole_.getRoleId().intValue();
            for (SysRole sysRole : roleList) {
                if (roleId == sysRole.getId().intValue()) {
                    String code = sysRole.getCode();
                    if (StringUtils.isNotEmpty(code) && code.indexOf("_") > -1) {
                        String spaceType = code.substring(code.lastIndexOf("_"));
                        spaceTypeList.add(spaceType.toLowerCase().replace("_", ""));
                    }
                }
            }
        }
        if (spaceTypeList.size() <= 0) {
            return null;
        }
        /*通过ValueKeys  和 type  查列表*/
        List<SysDictionary> syslist = sysDictionaryService.getListByValueKeys(SysDictionaryConstant.DESIGNPLAN_SPACE_TYPE, spaceTypeList);
        if (syslist == null || syslist.size() <= 0) {
            return null;
        }
        spaceFunctionIds = new ArrayList<>();
        for (SysDictionary sysDictionary : syslist) {
            spaceFunctionIds.add(sysDictionary.getValue());
        }
        return spaceFunctionIds;
    }


    /**
     * 方案推荐总条数
     *
     * @return
     */
    @Override
    public Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended) {
        if (designPlanRecommended == null) {
            return 0;
        }
        /*是方案管理员 必须要传空间类型*/
        if ("yes".equals(designPlanRecommended.getCheckAdministrator())) {
            if (designPlanRecommended.getSpaceFunctionIds() == null || designPlanRecommended.getSpaceFunctionIds().size() <= 0) {
                return 0;
            }
        }

        return designPlanRecommendedMapper.getPlanRecommendedCount(designPlanRecommended);
    }

    /**
     * 方案推荐详情
     *
     * @param planRecommendedId
     * @return
     */
    @Override
    public DesignPlanRecommended designPlanRecommendedDetails(String planRecommendedId) {
        DesignPlanRecommended designPlanRecommended = this.get(Integer.parseInt(planRecommendedId));
        if (designPlanRecommended == null) {
            return designPlanRecommended;
        }
        //通过推荐方案风格ID查询推荐方案风格名称
        Integer styleId = designPlanRecommended.getDesignRecommendedStyleId();
        if (styleId != null) {
            String styleName = baseProductStyleService.getNameById(styleId);
            designPlanRecommended.setDesignRecommendedStyleName(styleName);
        }
        SysUser user = sysUserService.get(designPlanRecommended.getUserId());
        designPlanRecommended.setPlanRecommendedUserName(user == null ? "无" : user.getUserName() == null ? "无" : user.getUserName());
        if (designPlanRecommended.getSpaceCommonId() != null) {
            SpaceCommon spaceCommon = spaceCommonService.get(designPlanRecommended.getSpaceCommonId());
            if (spaceCommon != null) {
                designPlanRecommended.setSpaceCode(spaceCommon.getSpaceCode());
                designPlanRecommended.setSpaceName(spaceCommon.getSpaceName());
                designPlanRecommended.setSpaceAreas(spaceCommon.getSpaceAreas());
            }
        }
        if (StringUtils.isEmpty(designPlanRecommended.getRemark())) {
            designPlanRecommended.setRemark("无");
        }
        //获取该方案推荐的白膜产品的数量，如果大于0那么是m+3 未装修完成的推荐（m+3 是不装修直接渲染的快捷键）
        int recommendedDecorateState = designPlanRecommendedMapper.getRecommendedDecorateState(designPlanRecommended.getId());
        if (recommendedDecorateState > 0) {      // '是否装修完成状态 (1.未装修完成 2.已装修完成)'
            designPlanRecommended.setRecommendedDecorateState(1);
        } else {
            designPlanRecommended.setRecommendedDecorateState(2);
        }
        //获取渲染图片
        designPlanRecommended = this.getRenderPicFromDesignPlanRecommend(designPlanRecommended);
        //获取渲染视频
        designPlanRecommended = this.getRenderVideoFromDesignPlanRecommend(designPlanRecommended);

        DesignPlanRecommendedProduct dprp = new DesignPlanRecommendedProduct();
        dprp.setIsDeleted(0);
        dprp.setPlanRecommendedId(designPlanRecommended.getId());
        int count = designPlanRecommendedProductService.getCount(dprp);
        designPlanRecommended.setPlanRecommendedProductCount(count);
        return designPlanRecommended;
    }

    /**
     * 获取渲染视频
     *
     * @param designPlanRecommended
     */
    @Override
    public DesignPlanRecommended getRenderVideoFromDesignPlanRecommend(DesignPlanRecommended designPlanRecommended) {

        ResRenderVideo resRenderVideo = new ResRenderVideo();
        resRenderVideo.setBusinessId(designPlanRecommended.getId());
        //渲染视频
        logger.info(CLASS_LOG_PREFIX + "获取渲染视频:BusinessId:{}", designPlanRecommended.getId());
        List<ResRenderVideo> resRenderVideoList = resRenderVideoService.getList(resRenderVideo);
        logger.info(CLASS_LOG_PREFIX + "获取渲染视频完成:JSON[List<ResRenderVideo>]:{}", GSON.toJson(resRenderVideoList));

        //资源地址
        List<String> videoPathList = new ArrayList<>(null == resRenderVideoList ? 10 : resRenderVideoList.size());
        if (null != resRenderVideoList && resRenderVideoList.size() > 0) {
            //拼接服务器Host
            resRenderVideoList.forEach(renderVideo -> videoPathList.add(Utils.getPropertyName("app", "app.server.siteName", "") + renderVideo.getVideoPath()));
            logger.info(CLASS_LOG_PREFIX + "获取渲染视频---获取资源地址完成:VideoPathList:{}", GSON.toJson(videoPathList));
        }

        if (null != videoPathList && videoPathList.size() > 0) {
            Map<Integer, List<String>> renderMap = designPlanRecommended.getRenderMap();

            if (null != renderMap) {
                renderMap.put(RenderTypeCode.COMMON_VIDEO, videoPathList);
            }
        }

        return designPlanRecommended;
    }

    /**
     * 获取渲染图片
     *
     * @param designPlanRecommended
     */
    @Override
    public DesignPlanRecommended getRenderPicFromDesignPlanRecommend(DesignPlanRecommended designPlanRecommended) {
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setPlanRecommendedId(designPlanRecommended.getId());
        resRenderPic.setIsDeleted(0);
        resRenderPic.setLimit(-1);
        resRenderPic.setOrder(" gmt_create desc ");
        List<String> fileKeys = new ArrayList<String>();
        fileKeys.add(ResProperties.DESIGNPLANRECOMMENDED_RENDER_PIC_FILEKEY);
        fileKeys.add(ResProperties.DESIGNPLANRECOMMENDED_VIDEO_FILEKEY);
        resRenderPic.setFileKeyList(fileKeys);
        List<ResRenderPic> picList = resRenderPicService.getList(resRenderPic);
        if (picList == null || picList.size() <= 0) {  //兼容老数据，老数据filekey 可能还用的 designPlan
            fileKeys.add(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
            fileKeys.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
            picList = resRenderPicService.getList(resRenderPic);
        }
        List<RenderPicInfo> renderPicList = new ArrayList<RenderPicInfo>();
		/*封面摆在图片列表的第一位*/
        Integer coverPicId = designPlanRecommended.getCoverPicId();
        if (coverPicId != null && coverPicId.intValue() > 0) {
            ResRenderPic coverPic = resRenderPicService.get(coverPicId);
            if (coverPic != null && coverPic.getIsDeleted().intValue() == 0) {
                //封面图片拼接URL
                renderPicList.add(new RenderPicInfo(coverPic.getPicPath(), coverPic.getRenderingType(), coverPic.getId(), ""));
            }
        }

		/*有渲染图则优先取渲染原图，无渲染图则取俯瞰图*/
        if (picList != null && picList.size() > 0) {/*取渲染原图*/
            for (ResRenderPic tempPic : picList) {
                //封面摆在图片列表的第一位，所以下面不需要重复设置
                if (coverPicId != null && coverPicId.intValue() > 0) {
                    if (tempPic.getId().intValue() == coverPicId.intValue()) {
                        continue;
                    }
                }
                //资源基本数据
                ResRenderPic tempResRenderPic;
                //数据详情URL
                String dateDetailUrl;
                if (null != tempPic.getSysTaskPicId() && tempPic.getSysTaskPicId() > 0) {
                    //获取资源基本数据
                    tempResRenderPic = resRenderPicService.get(tempPic.getSysTaskPicId());
                    //获取资源详情数据----查看大图
                    dateDetailUrl = resRenderPicService.getQRCodeInfo(tempResRenderPic);


                } else {
                    continue;
                }

                //设置图片类型
                designPlanRecommended.setPicType(1);
                RenderPicInfo renderPicInfo = new RenderPicInfo();

                //PicPath参数增加路径拼接
                renderPicInfo.setPicPath(tempPic.getPicPath());

                //渲染类型
                if (tempPic.getRenderingType() != null) {
                    //根据渲染类型遍历不同数据
                    switch (tempPic.getRenderingType()) {
                        case RenderTypeCode.COMMON_720_LEVEL:
                            //720度普通
                        case RenderTypeCode.HD_720_LEVEL:
                            //720度高清---构造基本数据
                            if (!"".equals(tempResRenderPic.getPicPath())) {
                                renderPicInfo.setRenderingType(tempPic.getRenderingType());
                                renderPicInfo.setOriginalPicId(tempPic.getId());
                            }
                            break;
                        default:
                            renderPicInfo.setRenderingType(tempPic.getRenderingType());
                            renderPicInfo.setOriginalPicId(tempPic.getId());
                            break;
                    }
                } else {
                    renderPicInfo.setRenderingType(tempPic.getRenderingType());
                    renderPicInfo.setOriginalPicId(tempPic.getId());
                }

                //默认普通地址
                renderPicInfo.setDateDetailUrl(dateDetailUrl);

                //拼接URL
                if (RenderTypeCode.COMMON_PICTURE_LEVEL == tempResRenderPic.getRenderingType().intValue()) {
                    //普通照片需拼接PC端URL
                    renderPicInfo.setPicPath(tempPic.getPicPath());
                } else if (RenderTypeCode.COMMON_720_LEVEL == tempResRenderPic.getRenderingType().intValue()) {
                    //720度普通需拼接PC端URL
                    renderPicInfo.setDateDetailUrl(tempPic.getPicCode());
                } else if (RenderTypeCode.ROAM_720_LEVEL == tempResRenderPic.getRenderingType().intValue()) {
                    //720度漫游需拼接PC端URL
                    renderPicInfo.setDateDetailUrl(dateDetailUrl);
                }

                //加入数据
                renderPicList.add(renderPicInfo);
            }

            //将数据转换为Map数据，便于前端使用---根据不同渲染类型组建不同List
            if (null != renderPicList && renderPicList.size() != 0) {
                //初始化4个空间分别对应1,4,6,8,四种渲染类型
                Map<Integer, List<String>> renderMap = new HashMap<>();
                renderPicList.forEach(renderPic -> {
                    List<String> renderList = new ArrayList<>();
                    //检查是否存在数组
                    if (renderMap.containsKey(renderPic.getRenderingType())) {
                        renderList = renderMap.get(renderPic.getRenderingType());
                        if (null != renderList && renderList.size() > 0) {
                            //照片级普通从PicPath参数取值，其他类型从DateDetailUrl取值
                            List<String> stringList = new ArrayList<>(renderList.size() + 1);
                            stringList.add((RenderTypeCode.COMMON_PICTURE_LEVEL == renderPic.getRenderingType().intValue()) ? renderPic.getPicPath() : renderPic.getDateDetailUrl());
                            renderList.forEach(str -> stringList.add(str));
                            renderList = stringList;
                        } else {
                            renderList = Arrays.asList((RenderTypeCode.COMMON_PICTURE_LEVEL == renderPic.getRenderingType().intValue()) ? renderPic.getPicPath() : renderPic.getDateDetailUrl());
                        }
                    } else {
                        logger.debug(CLASS_LOG_PREFIX + "------------获取渲染图片:" + renderPic);
                        renderList = Arrays.asList((RenderTypeCode.COMMON_PICTURE_LEVEL == renderPic.getRenderingType().intValue()) ? renderPic.getPicPath() : renderPic.getDateDetailUrl());
                    }

                    //装入Map
                    renderMap.put(renderPic.getRenderingType(), renderList);
                });

                //装入对象
                designPlanRecommended.setRenderMap(renderMap);
            }

            designPlanRecommended.setPicList(renderPicList);
        }

        return designPlanRecommended;
    }


    @Override
    public Integer getFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended) {
        if (designPlanRecommended == null) {
            return 0;
        }
//        if (designPlanRecommended.getBrandIds() == null || designPlanRecommended.getBrandIds().size() <= 0) {
//            return 0;
//        }
        return designPlanRecommendedMapper.getFavoritePlanRecommendedCount(designPlanRecommended);
    }

    @Override
    public List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended) {
        List<DesignPlanRecommendedResult> resList = new ArrayList<DesignPlanRecommendedResult>();
        if (designPlanRecommended == null) {
            return resList;
        }
//        if (designPlanRecommended.getBrandIds() == null || designPlanRecommended.getBrandIds().size() <= 0) {
//            return resList;
//        }
        resList = designPlanRecommendedMapper.getFavoritePlanRecommendedList(designPlanRecommended);

        return resList;
    }

    @Override
    public DesignPlanRecommended getAllRenderFromDesignPlanRecommend(Integer designPlanRecommendedId, Integer designPlanRecommendedCoverPicId) {
        DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();
        designPlanRecommended.setId(designPlanRecommendedId);
        designPlanRecommended.setCoverPicId(designPlanRecommendedCoverPicId);
        this.getRenderPicFromDesignPlanRecommend(designPlanRecommended);
        this.getRenderVideoFromDesignPlanRecommend(designPlanRecommended);
        return designPlanRecommended;
    }

    @Override
    public List<DesignPlanRecommended> getStatusByIds(List<Long> ids) {

        return designPlanRecommendedMapper.getStatusByIds(ids);
    }


    /**
     * 选装网 方案列表 1.一键方案 2.样板方案
     */
    @Override
    public List<DesignPlanRecommendedResult> designPlanRecommendList(DesignPlanRecommenInput designPlanRecommenInput, Integer userId) {

        PlanRecommendedListQuery pq = new PlanRecommendedListQuery();
        //1.校验参数
        this.checkDesignPlanRecommended(designPlanRecommenInput);
        //2.封装查询数据
        pq = this.packagingDate(designPlanRecommenInput, userId);
        //3.搜索
        pq = this.designPlanRecommendSearch(designPlanRecommenInput, pq);
        //4.查询方案列表数据
        List<DesignPlanRecommendedResult> list = this.designPlanRecommendList(pq);

        return list;
    }

    /**
     * 选装网 1.公开方案
     */
    @Override
    public List<DesignPlanRecommendedResult> designPlanRecommendOpenList(DesignPlanRecommenInput designPlanRecommenInput, Integer userId) {

        PlanRecommendedListQuery pq = new PlanRecommendedListQuery();
        //1.校验参数
        //	this.checkDesignPlanRecommended(designPlanRecommenInput);
        //2.封装查询数据
        pq = this.packagingDate(designPlanRecommenInput, userId);
        //3.搜索
        pq = this.designPlanRecommendOpenSearch(designPlanRecommenInput, pq);
        //4.查询方案列表数据
        List<DesignPlanRecommendedResult> list = this.getPlanRecommendedOpenList(pq);

        return list;
    }

    GlobalExceptionResolver a = new GlobalExceptionResolver();

    public void checkDesignPlanRecommended(DesignPlanRecommenInput input) {
        //显示类型  默认 一键方案(decorate)
		/*if(null == input.getDisplayType()){
			throw new AppException(ResponseEnum.PARAM_ERROR, "显示类型不能为空!");
		}*/
        //空间类型    默认  客餐厅
        if (null == input.getSpaceType()) {
            throw new AppException(ResponseEnum.PARAM_ERROR, "空间类型不能为空!");
        }
    }

    /**
     * 选装网 方案列表  封装数据
     */
    public PlanRecommendedListQuery packagingDate(DesignPlanRecommenInput designPlanRecommenInput, Integer userId) {
        PlanRecommendedListQuery pq = new PlanRecommendedListQuery();
        pq.setLimit((0 == designPlanRecommenInput.getPageSize()) ? PageModel.DEFAULT_PAGE_PAGESIZE : designPlanRecommenInput.getPageSize());
        pq.setStart(designPlanRecommenInput.getCurPage());

        //空间类型    如：客餐厅  卧室 卫生间等
        if (null != designPlanRecommenInput.getSpaceType()) {
            pq.setSpaceFunctionId(designPlanRecommenInput.getSpaceType() + "");
        }
        //空间面积
        if (StringUtils.isNotEmpty(designPlanRecommenInput.getSpaceArea())) {
            pq.setAreaValue(designPlanRecommenInput.getSpaceArea());
        }
        //设计方案风格ID
        if (StringUtils.isNotEmpty(designPlanRecommenInput.getDesignPlanStyleId())) {
            pq.setDesignRecommendedStyleId(designPlanRecommenInput.getDesignPlanStyleId());
        }
        //显示类型
        if (StringUtils.isNotEmpty(designPlanRecommenInput.getDisplayType())) {
            pq.setDisplayType(designPlanRecommenInput.getDisplayType());
        }
        //用户信息
        if (null != userId) {
            pq.setUserId(userId);
            pq.setUserType(UserTypeCode.USER_TYPE_OUTER_B2C);
        }
        //一键方案
		 /*if ("decorate".equals(designPlanRecommenInput.getDisplayType())) {
			 pq.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
			 pq.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
	     }
		 else */
        //样板方案
        if ("public".equals(designPlanRecommenInput.getDisplayType())) {
            pq.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_SHARE);
            pq.setIsRelease(RecommendedPublicState.IS_OPEN);
        }//默认一键方案
        else {
            pq.setRecommendedType(DesignPlanConstants.RECOMMENDED_TYPE_DECORATE);
            pq.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
        }
        //排序
        if (StringUtils.isNotEmpty(designPlanRecommenInput.getSort())) {
            pq.setSort(designPlanRecommenInput.getSort().trim());
        }//默认排序方式为最热
        else {
            pq.setSort("hot".trim());
        }
        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据,获取列表数据->PlanRecommendedListModel:{}", pq.toString());
        return pq;
    }

    //搜索条件 1.公开方案
    public PlanRecommendedListQuery designPlanRecommendOpenSearch(DesignPlanRecommenInput designPlanRecommenInput, PlanRecommendedListQuery pq) {
        if (null != designPlanRecommenInput && null != designPlanRecommenInput.getPlanName()) {
            pq.setPlanName(designPlanRecommenInput.getPlanName());
        }
        return pq;
    }

    //搜索条件   1.一键方案 2.样板方案
    public PlanRecommendedListQuery designPlanRecommendSearch(DesignPlanRecommenInput designPlanRecommenInput, PlanRecommendedListQuery pq) {
        if (null != designPlanRecommenInput && null != designPlanRecommenInput.getLivingName()) {
            //样板方案
            if ("public".equals(designPlanRecommenInput.getDisplayType())) {
                //小区名称
                pq.setLivingName(designPlanRecommenInput.getLivingName());
            }//一键方案
            else if ("decorate".equals(designPlanRecommenInput.getDisplayType())) {
                //小区名称
                pq.setLivingName(designPlanRecommenInput.getLivingName());
                //品牌名称
                pq.setBrandName(designPlanRecommenInput.getLivingName());
                //创建人
                pq.setCreator(designPlanRecommenInput.getLivingName());
            }//默认一键方案
            else {
                //小区名称
                pq.setLivingName(designPlanRecommenInput.getLivingName());
                //品牌名称
                pq.setBrandName(designPlanRecommenInput.getLivingName());
                //创建人
                pq.setCreator(designPlanRecommenInput.getLivingName());
            }

        }
        return pq;
    }

    /**
     * 选装网 方案列表 1.一键方案 2.样板方案
     */
    public List<DesignPlanRecommendedResult> designPlanRecommendList(PlanRecommendedListQuery pq) {
        Integer count = designPlanRecommendedMapper.getPlanRecommendCount(pq);
        List<DesignPlanRecommendedResult> list = null;
        if (count > 0) {
            list = designPlanRecommendedMapper.getPlanRecommendList(pq);
            if (list != null && list.size() > 0) {
                for (DesignPlanRecommendedResult result : list) {
                    logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据:根据推荐方案Id和渲染类型获得最新渲染原图:PlanRecommendedId:{}, renderingType:1.", result.getPlanRecommendedId());
                    ResRenderData res = resRenderPicService.getResRenderPicByPlanRecommended(result.getPlanRecommendedId(), 1);// 得到最新一张照片渲染原图地址
                    logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据:根据推荐方案Id和渲染类型获得最新渲染原图完成:ResRenderPic:{}.", null == res ? null : res.toString());
                    if (res != null) {
                        result.setResRenderPicPath(res.getPicPath());
                    }
                    //添加图片资源HOST拼接
                    result.setCoverPath(result.getCoverPath());

                    //从缓存中获取方案点赞收藏数量信息、
                    //从缓存中获取用户对方案是否点赞，是否收藏
                    DesignPlanSummaryInfo summaryInfo = designPlanLikeService.getPlanInfoOfCache(pq.getUserId(), result.getPlanRecommendedId());
                    if (null != summaryInfo) {
                        if (null != summaryInfo.getLikeNum()) {
                            result.setLikeNum(summaryInfo.getLikeNum());
                        }
                        if (null != summaryInfo.getCollectNum()) {
                            result.setCollectNum(summaryInfo.getCollectNum());
                        }
                        if (null != summaryInfo.getIsLike()) {
                            result.setIsLike(summaryInfo.getIsLike());
                        }
                        if (null != summaryInfo.getIsFavorite()) {
                            result.setIsFavorite(summaryInfo.getIsFavorite());
                        }
                    }
                }
            }
        }
		/*if(null == list){
			logger.warn(CLASS_LOG_PREFIX + "获取方案推荐列表数据,获取列表数据完成->未查询到有效数据:planRecommendedListModel{}", pq.toString());
			throw new ServiceException( ResponseEnum.NOT_CONTENT,"获取方案推荐列表数据,获取列表数据完成->未查询到有效数据!");
		}*/
        return list;
    }

    /**
     * 选装网 方案列表  封装查询数据 1.公开方案
     */
    public List<DesignPlanRecommendedResult> getPlanRecommendedOpenList(PlanRecommendedListQuery pq) {
        Integer count = designPlanRecommendedMapper.getPlanRecommendOpenCount(pq);
        List<DesignPlanRecommendedResult> list = null;
        if (count > 0) {
            list = designPlanRecommendedMapper.getPlanRecommendOpenList(pq);
        }
        return list;
    }

    /**
     * 获取最适合样板房的推荐方案
     *
     * @param designPlanRecommendedVo
     * @return
     */
    @Override
    public DesignPlanRecommendedResult getMatchPlan(DesignPlanRecommendedVo designPlanRecommendedVo) throws BizException {
        Integer planRecommendedId = designPlanRecommendedVo.getDesignPlanRecommendId();
        Integer templateId = designPlanRecommendedVo.getTemplateId();

        String result = getMatchPlanResult(planRecommendedId, templateId);
        logger.error("getMatchPlan -------- onekey result = {}", result);
        if (result == null || "".equals(result)) {
            logger.warn("组合方案远程获取适配子方案失败,result is null");
            return null;
        }
        ResponseEnvelope responseEnvelope = GSON.fromJson(result, ResponseEnvelope.class);
        if (!responseEnvelope.isSuccess()) {
            logger.warn("组合方案远程获取适配子方案失败,isFlag=" + responseEnvelope.isFlag());
            return null;
        }
        if (responseEnvelope.getObj() == null) {
            logger.warn("组合方案远程获取适配子方案失败,obj=" + responseEnvelope.getObj());
            return null;
        }
        planRecommendedId = ((Double) responseEnvelope.getObj()).intValue();
        if (null == planRecommendedId || planRecommendedId.intValue() <= 0) {
            logger.warn("组合方案的所有方案都不适合此样板房");
            return null;
        }
        logger.error("getMatchPlan -------- get the bast match plan id = {}", planRecommendedId);

//        List<DesignPlanRecommendedResult> designPlanRecommendedList = designPlanRecommendedMapper.selectAllById(planRecommendedId);
//        if (null != designPlanRecommendedList && designPlanRecommendedList.size() > 0) {
//            return designPlanRecommendedList.get(0);
//        }
        //减少前端逻辑改动，所以此处返回的子方案信息只包含id即可
        DesignPlanRecommendedResult recommendedResult = new DesignPlanRecommendedResult();
        recommendedResult.setDesignPlanRecommendId(planRecommendedId);
        return recommendedResult;
    }

    @Override
    public List<SharePlanDTO> getSharePlanList(PageModel pageModel) {


        return designPlanRecommendedMapper.selectSharePlanList(pageModel);
    }

    @Override
    public Integer getSharePlanCount() {
        return designPlanRecommendedMapper.selectSharePlanCount();
    }

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
    @Override
    public DesignPlanRecommendedResult getRecommendedDesignPlanDetail(PlanRecommendedListModel model) throws BizException {
        LoginUser loginUser = model.getLoginUser();
        // 更新方案浏览次数
        synchronized (DesignPlanRecommendedServiceImpl.class) {
            //从缓存中获取方案点赞总数,获取不到从数据库查找,再存入缓存
            String designPlanLikeNum = redisService.getMap(DESIGN_PLAN_VIEW_NUM_MAP, model.getId() + "");
            long viewNum = 0;
            if (StringUtils.isEmpty(designPlanLikeNum)) {
                DesignPlanSummaryInfo summaryInfo = designPlanSummaryInfoService.get(model.getId());
                if (null != summaryInfo) {
                    if (summaryInfo.getViewNum() != null) {
                        viewNum = (long) summaryInfo.getViewNum();
                    }
                }
                redisService.addMap(DESIGN_PLAN_VIEW_NUM_MAP, model.getId() + "", viewNum + "");
            }
            viewNum = redisService.mapIncrby(DESIGN_PLAN_VIEW_NUM_MAP, model.getId() + "", 1L);
            redisService.addMap(DESIGN_PLAN_VIEW_NUM_SYNCHRONIZE_MAP, model.getId() + "", viewNum + "");
        }
        // 查询推荐方案详情
        DesignPlanRecommendedResult designDetail = designPlanRecommendedMapper.selectDetail(model.getId());
        if (designDetail == null) {
            throw new BizException("查询推荐方案详情失败");
        }
        if (designDetail.getShops() != null && designDetail.getShops().size() > 0) {
            designDetail.setShopId(designDetail.getShops().get(0).getShopId());
            designDetail.setDesignerHeadPic(designDetail.getShops().get(0).getLogo());
        }
        if (designDetail.getIsFavorite() != null) {
            designDetail.setIsFavorite(1);
        }
        List<Integer> recommendedIds = new ArrayList<>(1);
        recommendedIds.add(model.getId());
        List<ResRenderPic> resPicList = resRenderPicService.getResRenderPicListByRecommendedIds(recommendedIds);
        for (ResRenderPic res : resPicList) {
            designDetail.setResRenderPicPath(res.getPicPath());
        }
        //从缓存中获取方案点赞收藏数量信息、
        //从缓存中获取用户对方案是否点赞，是否收藏
        DesignPlanSummaryInfo summaryInfo =
                designPlanLikeService.getPlanInfoOfCache(loginUser.getId(), designDetail.getPlanRecommendedId());
        if (null != summaryInfo) {
            if (null != summaryInfo.getViewNum()) {
                designDetail.setViewNum(summaryInfo.getViewNum());
            }
            if (null != summaryInfo.getLikeNum()) {
                designDetail.setLikeNum(summaryInfo.getLikeNum());
            }
            if (null != summaryInfo.getCollectNum()) {
                designDetail.setCollectNum(summaryInfo.getCollectNum());
            }
            if (null != summaryInfo.getIsLike()) {
                designDetail.setIsLike(summaryInfo.getIsLike());
            }
            if (null != summaryInfo.getIsFavorite()) {
                designDetail.setIsFavorite(summaryInfo.getIsFavorite());
            }
        }

        // 获取推荐方案详情的描述文件ID
        logger.info("查找推荐方案详情结果:{}", designDetail.toString());
        if (designDetail.getDescFileId() == null) {
            // 没有描述文件，直接返回详情
            logger.info("没有描述文件，直接返回详情");
            return designDetail;
        }
        int fileId = designDetail.getDescFileId();
        // 读取推荐方案详情的描述文件
        ResFile resFile = resFileService.get(fileId);
        if (resFile == null) {
            // 没有找到资源文件，直接返回详情
            logger.info("没有找到资源文件，直接返回详情");
            return designDetail;
        }
        String filePath = basePath + resFile.getFilePath();
        logger.info("推荐方案详情文件路径:{}", filePath);
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String str;
            // 读取文件内容
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            designDetail.setDesc(sb.toString());
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return designDetail;
    }

    /**
     * 调用http请求
     *
     * @param planRecommendedId
     * @param templateId
     * @return
     */
    private String getMatchPlanResult(Integer planRecommendedId, Integer templateId) {
        String url = appOnekeyUrl + "/online/web/design/intelligenceDecoration/getBestMatchInPlanGroup.htm?designTemplateId=" + templateId + "&recommendedPlanId=" + planRecommendedId;
        logger.error("getMatchPlan----------- url : {}", url);

        return Utils.doGetMethod(url);
    }

    @Override
    public Integer getFavoriteCount(DesignPlanRecommended designPlanRecommended) {
        return designPlanRecommendedMapper.getFavoriteCount(designPlanRecommended);
    }

    @Override
    public String showDesignPlanConfig(Integer id) {
        DesignPlanRecommended primaryPlan = designPlanRecommendedMapper.getDesignById(id);
        if (Objects.isNull(primaryPlan)) {
            logger.error("获取方案失败,planId:{}", id);
            return "";
        }
        return resFileService.readFile(primaryPlan.getDescFileId());
    }

    @Override
    public List<DesignPlanProductVO> listDesignPlanProducts(Integer planId, Integer productBatchType, Integer pageNo) {
        DesignPlanRecommended designPlan = getBaseInfo(planId);
        List<DesignPlanProduct> designPlanProducts = designPlanRecommendedMapper.listDesignPlanProducts(planId);
        if (designPlanProducts != null && designPlanProducts.size() > 0) {
            List<Integer> productIds = designPlanProducts.stream().map(DesignPlanProduct::getProductId).collect(toList());
            List<BaseProduct> baseProducts = baseProductService.getProductListByType(productIds, productBatchType);
            List<Integer> brandIds = baseProducts.stream().map(brand -> brand.getBrandId()).collect(toList());
            Map<Integer, String> brandIdAndNameMap = baseBrandService.brandIdAndNameMap(brandIds);

            List<Integer> picIds = baseProducts.stream().map(BaseProduct::getPicId).collect(toList());
            //根据图片id查询图片途径
            Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);


            for (BaseProduct product : baseProducts) {
                product.setBrandName(brandIdAndNameMap.get(product.getBrandId()));
                //设置图片
                product.setPicPath(picPathMap.get(product.getPicId()));
            }
            Integer pageSize = baseProducts.size() > 15 ? 15 : baseProducts.size();
            if (pageNo != null) {
                if (pageNo * 15 > baseProducts.size()) {
                    pageSize = baseProducts.size();
                } else {
                    pageSize = pageNo * 15;
                }
            }
            baseProducts = baseProducts.subList(0, pageSize);
            List<DesignPlanProductVO> planProductVOS = new ArrayList<>();
            DesignPlanProductVO vo = new DesignPlanProductVO();
            vo.setSpaceName(designPlan.getSpaceTypeName());
            vo.setProducts(baseProducts);
            vo.setProductsSize(baseProducts.size());
            planProductVOS.add(vo);
            return planProductVOS;
//            Map<String,List<BaseProduct>> map = new HashMap<>();
//            map.put(designPlan.getSpaceTypeName(),baseProducts);
//            return map;
        }
        return null;
    }

    @Override
    public DesignPlanRecommended getBaseInfo(Integer recommendedPlanGroupPrimaryId) {
        DesignPlanRecommended plan = designPlanRecommendedMapper.getDesignById(recommendedPlanGroupPrimaryId);
        //查询空间类型id
        List<Integer> spaceCommonIds = new ArrayList<>();
        spaceCommonIds.add(plan.getSpaceCommonId());
        //根据方案space_common_id查询空间类型表space_common信息( map(spaceCommonId,spaceTypeId) )
        Map<Integer, Integer> spaceTypeMap = spaceCommonService.idAndSpaceTypeMap(spaceCommonIds);
        //设置空间类型
        plan.setSpaceTypeId(spaceTypeMap.get(plan.getSpaceCommonId()));
        Map<Integer, String> houseType = sysDictionaryService.valueAndName2Map("houseType");
        //设置空间类型名称
        if (plan.getSpaceTypeId() == null) {
            plan.setSpaceTypeName("");
        } else {
            plan.setSpaceTypeName(houseType.get(plan.getSpaceTypeId()));
        }
        return plan;
    }

    @Override
    public List<DesignPlanRecommended> getGroupDesignPlanRecommendedList(DesignPlanRecommended designPlanRecommended) {
        // ------ 参数验证 ->start
        if (designPlanRecommended == null) {
            return null;
        }
        // ------ 参数验证 ->end

        if (
                designPlanRecommended.getGroupPrimaryId() != null
                        && designPlanRecommended.getGroupPrimaryId() > 0
                        && designPlanRecommended.getGroupPrimaryId().intValue() == designPlanRecommended.getId().intValue()) {
            // 判断为精选方案的主方案, 查找所有子方案
            DesignPlanRecommended designPlanRecommendedSearch = new DesignPlanRecommended();
            designPlanRecommendedSearch.setIsDeleted(0);
            designPlanRecommendedSearch.setGroupPrimaryId(designPlanRecommended.getGroupPrimaryId());
            return this.getList(designPlanRecommendedSearch);
        } else {
            List<DesignPlanRecommended> designPlanRecommendedList = new ArrayList<DesignPlanRecommended>();
            designPlanRecommendedList.add(designPlanRecommended);
            return designPlanRecommendedList;
        }
    }

    @Override
    public HomePageRecommendDesignPlanInfo getLikeAndCollectNum(HomePageRecommendDesignPlanInfo homePageRecommendDesignPlanInfo, LoginUser loginUser) {
        List<DesignPlanRecommendedVo> list = new ArrayList<>();
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList3());
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList4());
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList5());
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList6());
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList7());
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList8());
        list.addAll(homePageRecommendDesignPlanInfo.getSpaceTypeVList13());

        for (DesignPlanRecommendedVo designPlanRecommendedVo : list) {
            //从缓存中获取方案点赞收藏数量信息、
            //从缓存中获取用户对方案是否点赞，是否收藏
            DesignPlanSummaryInfo summaryInfo =
                    designPlanLikeService.getPlanInfoOfCache(loginUser.getId(), designPlanRecommendedVo.getDesignPlanId());
            if (null != summaryInfo) {
                if (null != summaryInfo.getLikeNum()) {
                    designPlanRecommendedVo.setLikeNum(summaryInfo.getLikeNum());
                }
                if (null != summaryInfo.getCollectNum()) {
                    designPlanRecommendedVo.setCollectNum(summaryInfo.getCollectNum());
                }
                if (null != summaryInfo.getIsLike()) {
                    designPlanRecommendedVo.setIsLike(summaryInfo.getIsLike());
                }
                if (null != summaryInfo.getIsFavorite()) {
                    designPlanRecommendedVo.setIsFavorite(summaryInfo.getIsFavorite());
                }
                if (null != summaryInfo.getViewNum()) {
                    designPlanRecommendedVo.setViewNum(summaryInfo.getViewNum().toString());
                }
            }
        }
        return homePageRecommendDesignPlanInfo;
    }


    private List<DesignPlanRecommendedResult> getPreferPlanList(Integer userId) {
        List<DesignPlanRecommendedResult> designPlanRecommendedResultList = new ArrayList<>();
        //获取所有精选方案的方案ID
        List<DesignPlanRecommendedSuperior> recommendedSuperiors = designPlanRecommendedMapper.getAllSuperiorPlanList();
        if (null == recommendedSuperiors || 0 == recommendedSuperiors.size()) {
            return null;
        }
        List<Integer> singleSpaceSuperiorPlanIdList = new ArrayList<>();
        List<Integer> fullHouseSuperiorPlanIdList = new ArrayList<>();
        for (DesignPlanRecommendedSuperior planRecommendedSuperior : recommendedSuperiors) {
            if (null != planRecommendedSuperior.getDesignPlanRecommendedId() && 0 != planRecommendedSuperior.getDesignPlanRecommendedId()) {
                if(planRecommendedSuperior.getSpaceType().intValue() !=13){
                    singleSpaceSuperiorPlanIdList.add(planRecommendedSuperior.getDesignPlanRecommendedId());
                }else {
                    fullHouseSuperiorPlanIdList.add(planRecommendedSuperior.getDesignPlanRecommendedId());
                }
            }
        }
        //获取单空间精选方案
        if(null != singleSpaceSuperiorPlanIdList && singleSpaceSuperiorPlanIdList.size() > 0){
            List<DesignPlanRecommendedResult> signleSpaceDesignPlanList = designPlanRecommendedMapper.getAllDesignPlanRecommendedInfoList(userId, singleSpaceSuperiorPlanIdList);
            if(null != signleSpaceDesignPlanList && signleSpaceDesignPlanList.size() > 0){
                for (DesignPlanRecommendedResult result : signleSpaceDesignPlanList){
                    if (Objects.equals(1,result.getChargeType())){
                        //收费
                        int exits = designPlanRecommendedMapper.isExistBuyDesignPlanCopyRight(userId, result.getPlanRecommendedId(), 1);
                        logger.info("置顶方案是否要收费 =>{}"+result.getPlanRecommendedId()+"{}"+exits);
                        if (exits > 0){
                            //已经购买过
                            result.setHavePurchased(1);
                            result.setCopyRightPermission(1);
                        }else{
                            result.setHavePurchased(0);
                            result.setCopyRightPermission(1);
                        }
                    }else{
                        result.setHavePurchased(0);
                        result.setCopyRightPermission(0);
                    }
                }
                //从缓存中获取方案点赞收藏数量信息、
                //从缓存中获取用户对方案是否点赞，是否收藏
                signleSpaceDesignPlanList = nodeInfoBizService.getNodeData(signleSpaceDesignPlanList, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED, userId);
                designPlanRecommendedResultList.addAll(signleSpaceDesignPlanList);
            }
        }
        //获取全屋精选方案
        if( null != fullHouseSuperiorPlanIdList && fullHouseSuperiorPlanIdList.size() > 0){
            List<DesignPlanRecommendedResult> fullHouseDesignPlanList = fullHouseDesignPlanMapper.getSuperiorFullHousePlanList1(userId,fullHouseSuperiorPlanIdList);
            if(null != fullHouseDesignPlanList && fullHouseDesignPlanList.size()>0){

                for (DesignPlanRecommendedResult result : fullHouseDesignPlanList){
                    if (Objects.equals(1,result.getChargeType())){
                        //收费
                        int exits = designPlanRecommendedMapper.isExistBuyDesignPlanCopyRight(userId, result.getPlanRecommendedId(), 1);
                        logger.info("置顶方案是否要收费 =>{}"+result.getPlanRecommendedId()+"{}"+exits);
                        if (exits > 0){
                            //已经购买过
                            result.setHavePurchased(1);
                            result.setCopyRightPermission(1);
                        }else{
                            result.setHavePurchased(0);
                            result.setCopyRightPermission(1);
                        }
                    }else{
                        result.setHavePurchased(0);
                        result.setCopyRightPermission(0);
                    }
                }

                //从缓存中获取方案点赞收藏数量信息、
                //从缓存中获取用户对方案是否点赞，是否收藏
                fullHouseDesignPlanList = nodeInfoBizService.getNodeData(fullHouseDesignPlanList, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_FULL_HOUSE, userId);
                designPlanRecommendedResultList.addAll(fullHouseDesignPlanList);
            }
        }

        designPlanRecommendedResultList.stream().forEach(designPlanRecommendedResult -> {
            Integer favoriteNum = designPlanRecommendedResult.getFavoriteNum() == null ? 0 : designPlanRecommendedResult.getFavoriteNum();
            Integer virtualFavoriteNum = designPlanRecommendedResult.getVirtualFavoriteNum() == null ? 0 : designPlanRecommendedResult.getVirtualFavoriteNum();
            Integer likeNum = designPlanRecommendedResult.getLikeNum() == null ? 0 : designPlanRecommendedResult.getLikeNum();
            Integer virtualLikeNum = designPlanRecommendedResult.getVirtualLikeNum() == null ? 0 : designPlanRecommendedResult.getVirtualLikeNum();
            designPlanRecommendedResult.setCollectNum(favoriteNum + virtualFavoriteNum);
            designPlanRecommendedResult.setLikeNum(likeNum + virtualLikeNum);
        });

        if (null == designPlanRecommendedResultList || 0 == designPlanRecommendedResultList.size()) {
            return null;
        }
        this.setPlanResultInfo2(userId, designPlanRecommendedResultList);
        return designPlanRecommendedResultList;
    }


    @Override
    public Object getAllDesignPlanRecommendedSuperiorList(Integer userId, Integer spaceType, Integer bizType) {
        long a1 = System.currentTimeMillis();
        List<DesignPlanRecommendedResult> designPlanRecommendedResultList = new ArrayList <>();
        // try {
        //     designPlanRecommendedResultList = LOCAL_CACHE_PREFER_PLAN.get(LOCAL_CACHE_PREFER_PLAN_KEY, new Callable <List<DesignPlanRecommendedResult>>() {
        //         @Override
        //         public List<DesignPlanRecommendedResult> call() throws Exception {
        //             return getPreferPlanList(userId);
        //         }
        //     });
        //
        // } catch (ExecutionException e) {
        //     e.printStackTrace();
        // }

        // 修改为直接查数据库，通过内存的的方式有问题
        designPlanRecommendedResultList = getPreferPlanList(userId);
        if(designPlanRecommendedResultList == null ||designPlanRecommendedResultList.size() == 0){
            return null;
        }

        long a2 = System.currentTimeMillis();
        logger.error("a2-a1:" + (a2 - a1));

        List<SuperiorPlanListVo> superiorPlanListVoList = new ArrayList<>();
        if(null == bizType || bizType == 0){
            if( null != spaceType && spaceType > 0){
                List<DesignPlanRecommendedResult> recommendedResults = new ArrayList<>();
                for (DesignPlanRecommendedResult designPlanRecommendedResult : designPlanRecommendedResultList) {
                    if(null != designPlanRecommendedResult.getSpaceFunctionId() && designPlanRecommendedResult.getSpaceFunctionId().intValue()==spaceType.intValue() && recommendedResults.size()<=4){
                        recommendedResults.add(designPlanRecommendedResult);
                    }
                }
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults));
            }else {
                List<DesignPlanRecommendedResult> recommendedResults3 = new ArrayList<>();
                List<DesignPlanRecommendedResult> recommendedResults4 = new ArrayList<>();
                List<DesignPlanRecommendedResult> recommendedResults5 = new ArrayList<>();
                List<DesignPlanRecommendedResult> recommendedResults6 = new ArrayList<>();
                List<DesignPlanRecommendedResult> recommendedResults7 = new ArrayList<>();
                List<DesignPlanRecommendedResult> recommendedResults8 = new ArrayList<>();
                List<DesignPlanRecommendedResult> recommendedResults13 = new ArrayList<>();
                for (DesignPlanRecommendedResult designPlanRecommendedResult : designPlanRecommendedResultList) {
                    if (null != designPlanRecommendedResult.getSpaceFunctionId() && 0 != designPlanRecommendedResult.getSpaceFunctionId()) {
                        switch (designPlanRecommendedResult.getSpaceFunctionId()) {
                            case 3:
                                    recommendedResults3.add(designPlanRecommendedResult);
                                break;
                            case 4:
                                    recommendedResults4.add(designPlanRecommendedResult);
                                break;
                            case 5:
                                    recommendedResults5.add(designPlanRecommendedResult);
                                break;
                            case 6:
                                    recommendedResults6.add(designPlanRecommendedResult);
                                break;
                            case 7:
                                recommendedResults7.add(designPlanRecommendedResult);

                                break;
                            case 8:
                                    recommendedResults8.add(designPlanRecommendedResult);

                                break;
                            case 13:
                                    recommendedResults13.add(designPlanRecommendedResult);

                                break;
                            default:
                                logger.warn("未知的空间" + GsonUtil.bean2Json(designPlanRecommendedResult));
                                break;
                        }
                    }
                    continue;
                }

                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults3));
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults4));
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults5));
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults6));
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults7));
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults8));
                superiorPlanListVoList.add(this.creatSuperiorPlanListVo(recommendedResults13));
            }

            long a3 = System.currentTimeMillis();
            logger.error("a3-a2:" + (a3 - a2));
            //去空
            superiorPlanListVoList.removeAll(Collections.singleton(null));


            return superiorPlanListVoList;
        }else {
            if( null != spaceType && spaceType > 0){
                List<DesignPlanRecommendedResult> recommendedResults = new ArrayList<>(5);
                for (DesignPlanRecommendedResult designPlanRecommendedResult : designPlanRecommendedResultList) {
                    if(designPlanRecommendedResult.getSpaceFunctionId().intValue()==spaceType.intValue() && recommendedResults.size()<=4){
                        recommendedResults.add(designPlanRecommendedResult);
                    }
                }
                return recommendedResults;
            }else {
                return designPlanRecommendedResultList;
            }

        }

    }

    private SuperiorPlanListVo creatSuperiorPlanListVo(List<DesignPlanRecommendedResult> recommendedResults) {
        if (null != recommendedResults && recommendedResults.size() > 0) {
            SuperiorPlanListVo superiorPlanListVo = new SuperiorPlanListVo();
            superiorPlanListVo.setDesignPlanRecommendedSuperiorList(recommendedResults);
            if(recommendedResults.get(0).getSpaceFunctionId()==13){
                superiorPlanListVo.setSpaceName("全屋");
            }else {
                superiorPlanListVo.setSpaceName(recommendedResults.get(0).getHouseTypeName());
            }

            return superiorPlanListVo;
        }
        return null;
    }


    @Override
    public List<SuperiorPlanListVo> getSpaceDesignPlanRecommendedSuperiorList(Integer spaceType, Integer userId) {
        List<DesignPlanRecommendedSuperior> designPlanRecommendedSuperiorList = designPlanRecommendedMapper.getSpaceSuperiorPlanList(spaceType);
        if (null == designPlanRecommendedSuperiorList || 0 == designPlanRecommendedSuperiorList.size()) {
            return null;
        }
        List<Integer> superiorPlanIdList = new ArrayList<>(designPlanRecommendedSuperiorList.size());
        for (DesignPlanRecommendedSuperior planRecommendedSuperior : designPlanRecommendedSuperiorList) {
            if (null != planRecommendedSuperior.getDesignPlanRecommendedId() && 0 != planRecommendedSuperior.getDesignPlanRecommendedId()) {
                superiorPlanIdList.add(planRecommendedSuperior.getDesignPlanRecommendedId());
            }
        }
        //获取所有精选方案的方案详情
        List<DesignPlanRecommendedResult> designPlanRecommendedResultList = designPlanRecommendedMapper.getAllDesignPlanRecommendedInfo(userId, superiorPlanIdList);
        if (null == designPlanRecommendedResultList || 0 == designPlanRecommendedResultList.size()) {
            return null;
        }
        this.setPlanResultInfo2(userId, designPlanRecommendedResultList);

        List<SuperiorPlanListVo> superiorPlanListVoList = new ArrayList<>(5);
        superiorPlanListVoList.add(this.creatSuperiorPlanListVo(designPlanRecommendedResultList));
        return superiorPlanListVoList;
    }

    @Override
    public boolean isExistBuyDesignPlanCopyRight(Integer userId, Integer planRecommendedId, int planType) {
        return designPlanRecommendedMapper.isExistBuyDesignPlanCopyRight(userId,planRecommendedId,planType) > 0;
    }
}
