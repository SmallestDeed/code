package com.sandu.search.controller.design;

import com.sandu.common.LoginContext;
import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.constant.PlatformConstant;
import com.sandu.search.common.constant.RedisConstant;
import com.sandu.search.common.constant.ReturnCode;
import com.sandu.search.common.tools.DomainUtil;
import com.sandu.search.common.tools.EntityCopyUtils;
import com.sandu.search.common.tools.RequestHeaderUtil;
import com.sandu.search.entity.common.PageVo;
import com.sandu.search.entity.designplan.po.RecommendationPlanPo;
import com.sandu.search.entity.designplan.vo.RecommendationPlanSearchVo;
import com.sandu.search.entity.designplan.vo.RecommendationPlanVo;
import com.sandu.search.entity.elasticsearch.index.RecommendationPlanIndexMappingData;
import com.sandu.search.entity.elasticsearch.response.SearchObjectResponse;
import com.sandu.search.entity.response.RecommendationPlanCondition;
import com.sandu.search.entity.response.SearchResultResponse;
import com.sandu.search.entity.user.LoginUser;
import com.sandu.search.exception.RecommendationPlanSearchException;
import com.sandu.search.service.design.RecommendationPlanSearchService;
import com.sandu.search.service.redis.RedisService;
import com.sandu.search.storage.company.CompanyMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 推荐方案接口
 *
 * @date 2018/6/1
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Slf4j
//@RestController
//@EnableAutoConfiguration
//@RequestMapping("/brandsite/recommendationplan/normal")
public class RecommendationPlanController {

    private final static String CLASS_LOG_PREFIX = "[运营网站]推荐方案接口:";

    private final DomainUtil domainUtil;
    private final RedisService redisService;
    private final HttpServletRequest request;
    private final CompanyMetaDataStorage companyMetaDataStorage;
    private final RecommendationPlanSearchService recommendationPlanSearchService;

    @Autowired
    public RecommendationPlanController(DomainUtil domainUtil, RedisService redisService, HttpServletRequest request, CompanyMetaDataStorage companyMetaDataStorage, RecommendationPlanSearchService recommendationPlanSearchService) {
        this.domainUtil = domainUtil;
        this.redisService = redisService;
        this.request = request;
        this.companyMetaDataStorage = companyMetaDataStorage;
        this.recommendationPlanSearchService = recommendationPlanSearchService;
    }

    //查询推荐方案
    @PostMapping("/list")
    SearchResultResponse queryRecommendationPlanListByCondition(@RequestBody RecommendationPlanCondition recommendationPlanCondition) {

        if (null == recommendationPlanCondition) {
            log.warn(CLASS_LOG_PREFIX + "搜索产品失败，必需参数为空, models is null.");
            return new SearchResultResponse(ReturnCode.MUST_PARAM_IS_NULL);
        }

        /************************************************** 检查数据 **********************************************/

        //条件对象
        RecommendationPlanSearchVo recommendationPlanSearchVo = recommendationPlanCondition.getRecommendationPlanSearchVo();
        //分页对象
        PageVo pageVo = recommendationPlanCondition.getPageVo();
        if (null == recommendationPlanSearchVo) {
            recommendationPlanSearchVo = new RecommendationPlanSearchVo();
        }

        //平台来源标识
        String platformCode = RequestHeaderUtil.getPlatformIdByRequest(request);
        //公司信息获取
        int companyId = 0;
        if (PlatformConstant.PLATFORM_CODE_TOC_SITE.equals(platformCode)) {
            //品牌网站HOST解析
            String domainName = DomainUtil.getDomainNameByHost(request);
            log.info(CLASS_LOG_PREFIX + "公司域名-domainName:{}", domainName);

            if (StringUtils.isEmpty(domainName)) {
                log.info(CLASS_LOG_PREFIX + "品牌网站HOST解析失败 domainname is null!");
                return new SearchResultResponse(ReturnCode.NO_ALIVE_COMPANY);
            }

            //获取为空则直接获取公司--(小程序有公司但没有公司域名)
            companyId = companyMetaDataStorage.getCompanyIdByDomainName(domainName);
            log.info(CLASS_LOG_PREFIX + "Brand2C公司ID获取完成! DomainName:{}, CompanyId:{}.", domainName, companyId);
        } else if (PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equals(platformCode)) {
            //小程序获取公司ID
            companyId = domainUtil.getCompanyIdInMiniProgramByRequest(request);
            log.info(CLASS_LOG_PREFIX + "小程序获取公司ID为:{}.", companyId);
        }

        //公司ID
        if (0 < companyId) {
            //三度云享家应用需单独处理--去掉品牌限制|平台限制
            if (companyMetaDataStorage.getSanduCompanyId() != companyId) {
                recommendationPlanSearchVo.setCompanyId(companyId);
                log.info(CLASS_LOG_PREFIX + "当前公司非三度云享家公司,需方案公司过滤,当前公司:{}.", companyId);
            } else {
                log.info(CLASS_LOG_PREFIX + "已识别为三度云享家公司，移除公司限制,排除分享和交付方案来源的推荐方案! CompanyId:{}.", companyId);
                recommendationPlanSearchVo.setPlanSource(Arrays.asList(
                        RecommendationPlanPo.SHARE_PLAN_SOURCE,
                        RecommendationPlanPo.DELIVER_PLAN_SOURCE
                ));
            }
        }

        /************************************************** 构造分页对象 **********************************************/
        if (null == pageVo) {
            pageVo = new PageVo();
        }
        if (0 == pageVo.getPageSize()) {
            pageVo.setPageSize(IndexInfoQueryConfig.DEFAULT_SEARCH_DATA_SIZE);
        }

        /************************************ 构造产品搜索条件 ********************************************************/
        //一键方案
        recommendationPlanSearchVo.setRecommendationPlanType(RecommendationPlanPo.ONEKEY_RECOMMENDATION_PLAN_TYPE);
        //已发布状态方案
        recommendationPlanSearchVo.setReleaseStatus(RecommendationPlanPo.DESIGN_PLAN_IS_PUBLISH);

        /********************************************** 搜索推荐方案 *******************************************************/
        SearchObjectResponse searchObjectResponse;
        try {
            searchObjectResponse = recommendationPlanSearchService.searchRecommendationPlan(recommendationPlanSearchVo, pageVo);
        } catch (RecommendationPlanSearchException e) {
            log.error(CLASS_LOG_PREFIX + "搜索推荐方案失败:RecommendationPlanSearchException:{}.", e);
            return new SearchResultResponse(ReturnCode.SEARCH_RECOMMENDATION_FAIL);
        }

        if (null == searchObjectResponse || 0 == searchObjectResponse.getHitTotal() || null == searchObjectResponse.getHitResult()) {
            log.info(CLASS_LOG_PREFIX + "未搜索到数据.....RecommendationPlanSearchVo:{}, pageVo:{}", recommendationPlanSearchVo.toString(), pageVo.toString());
            return new SearchResultResponse(ReturnCode.SUCCESS, "", 0);
        }

        /******************************************* 登录信息(用户收藏点赞数) *************************************/
        //获取用户登录信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        int userId = 0;
        if (null != loginUser) {
            userId = loginUser.getId();
        }
        log.info(CLASS_LOG_PREFIX + "当前登录用户ID:{}.", userId);

        /******************* 对象转换(RecommendationPlanIndexMappingData->RecommendationPlanVo) **************/
        List<RecommendationPlanIndexMappingData> recommendationPlanIndexMappingDataList = (List<RecommendationPlanIndexMappingData>) searchObjectResponse.getHitResult();
        List<RecommendationPlanVo> recommendationPlanVoList = null;
        if (null != recommendationPlanIndexMappingDataList && 0 < recommendationPlanIndexMappingDataList.size()) {
            //初始化
            recommendationPlanVoList = new ArrayList<>(recommendationPlanIndexMappingDataList.size());

            //转换对象
            for (RecommendationPlanIndexMappingData recommendationPlanIndexMappingData : recommendationPlanIndexMappingDataList) {

                RecommendationPlanVo recommendationPlanVo = new RecommendationPlanVo();
                //复制对象
                EntityCopyUtils.copyData(recommendationPlanIndexMappingData, recommendationPlanVo);
                //使用空间面积
                List<String> applySpaceAreaList = recommendationPlanIndexMappingData.getApplySpaceAreaList();
                if (null != applySpaceAreaList && 0 < applySpaceAreaList.size()) {
                    StringBuffer applySpaceAreaSB = new StringBuffer();
                    applySpaceAreaList.forEach(area -> applySpaceAreaSB.append(area).append(","));
                    //移除最后一个逗号
                    recommendationPlanVo.setApplySpaceAreas(applySpaceAreaSB.substring(0, applySpaceAreaSB.length()).toString());
                }
                //装入对象
                recommendationPlanVoList.add(recommendationPlanVo);
            }

            //点赞|收藏数据
            for (RecommendationPlanVo recommendationPlanVo : recommendationPlanVoList) {
                //点赞|收藏
                if (0 < userId) {
                    //查询点赞收藏
                    String collectLikeStr = redisService.getMap(RedisConstant.RECOMMENDATION_PLAN_USER_COLLECT_LIKE, userId + ":" + recommendationPlanVo.getId());
                    if (!StringUtils.isEmpty(collectLikeStr)) {
                        String[] split = collectLikeStr.split(":");
                        if (2 == split.length) {
                            //点赞
                            recommendationPlanVo.setLike(Integer.parseInt(split[0]));
                            //收藏
                            recommendationPlanVo.setCollect(Integer.parseInt(split[1]));
                        }
                    }
                }

                //收藏数
                String collectCountStr = redisService.getMap(RedisConstant.RECOMMENDATION_PLAN_COLLECT_COUNT, recommendationPlanVo.getId() + "");
                if (!StringUtils.isEmpty(collectCountStr)) {
                    recommendationPlanVo.setCollectCount(Integer.parseInt(collectCountStr));
                }
                //点赞数
                String likeCountStr = redisService.getMap(RedisConstant.RECOMMENDATION_PLAN_LIKE_COUNT, recommendationPlanVo.getId() + "");
                if (!StringUtils.isEmpty(likeCountStr)) {
                    recommendationPlanVo.setLikeCount(Integer.parseInt(likeCountStr));
                }
            }


        }

        return new SearchResultResponse(ReturnCode.SUCCESS, recommendationPlanVoList, searchObjectResponse.getHitTotal());
    }
}