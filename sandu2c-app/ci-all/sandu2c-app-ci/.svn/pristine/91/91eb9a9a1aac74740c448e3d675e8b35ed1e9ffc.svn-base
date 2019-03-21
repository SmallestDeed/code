package com.sandu.job;

import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.model.DesignPlanRecommendFavoriteRef;
import com.sandu.designplan.model.DesignPlanSummaryInfo;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.designplan.service.DesignPlanRecommendFavoriteService;
import com.sandu.designplan.service.DesignPlanSummaryInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.sandu.cache.RedisKeyConstans.*;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:47 2018/1/15 0015
 * @Modified By:
 */
@Component
public class DesignPlanInfoJob {

    private final static Logger logger = LoggerFactory.getLogger(DesignPlanInfoJob.class);

    private final static Gson GSON = new Gson();
    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanLikeService designPlanLikeService;
    @Autowired
    private DesignPlanSummaryInfoService designPlanSummaryInfoService;
    @Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;

    public void synchronizeDesignPlanCache() {

        logger.info("--------同步方案点赞收藏相关数据开始--------");
        //从同步的缓存中获取用户点赞收藏状态，获取完成就清空缓存----------
        Map<String, String> userDesignPlanInfoMap = redisService.getMap(USER_DESIGN_PLAN_INFO_SYNCHRONIZE_MAP);
        logger.info("--------同步方案点赞收藏 ===> userDesignPlanInfoMap=" + userDesignPlanInfoMap);
        redisService.del(USER_DESIGN_PLAN_INFO_SYNCHRONIZE_MAP);
        Map<String, String> userFullHouseDesignPlanInfoMap = redisService.getMap(USER_FULL_HOUSE_DESIGN_PLAN_INFO_SYNCHRONIZE_MAP);
        logger.info("--------同步全屋方案点赞收藏 ===> userFullHouseDesignPlanInfoMap=" + userFullHouseDesignPlanInfoMap);
        redisService.del(USER_FULL_HOUSE_DESIGN_PLAN_INFO_SYNCHRONIZE_MAP);
        //从同步的缓存中获取点赞收藏总数，获取完成就清空缓存----------
        Map<String, String> planLikeNum = redisService.getMap(DESIGN_PLAN_LIKE_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步方案点赞收藏 ===> planLikeNum=" + planLikeNum);
        redisService.del(DESIGN_PLAN_LIKE_NUM_SYNCHRONIZE_MAP);
        Map<String, String> planFavoriteNum = redisService.getMap(DESIGN_PLAN_FAVORITE_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步方案点赞收藏 ===> planFavoriteNum=" + planFavoriteNum);
        redisService.del(DESIGN_PLAN_FAVORITE_NUM_SYNCHRONIZE_MAP);
        Map<String, String> planViewNum = redisService.getMap(DESIGN_PLAN_VIEW_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步方案点赞收藏 ===> planViewNum=" + planViewNum);
        redisService.del(DESIGN_PLAN_VIEW_NUM_SYNCHRONIZE_MAP);
        Map<String, String> fullHousePlanLikeNum = redisService.getMap(FULL_HOUSE_DESIGN_PLAN_LIKE_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步全屋方案点赞收藏 ===> fullHousePlanLikeNum=" + fullHousePlanLikeNum);
        redisService.del(FULL_HOUSE_DESIGN_PLAN_LIKE_NUM_SYNCHRONIZE_MAP);
        Map<String, String> fullHousePlanFavoriteNum = redisService.getMap(FULL_HOUSE_DESIGN_PLAN_FAVORITE_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步全屋方案点赞收藏 ===> fullHousePlanFavoriteNum=" + fullHousePlanFavoriteNum);
        redisService.del(FULL_HOUSE_DESIGN_PLAN_FAVORITE_NUM_SYNCHRONIZE_MAP);
        Map<String, String> fullHousePlanViewNum = redisService.getMap(FULL_HOUSE_DESIGN_PLAN_VIEW_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步全屋方案点赞收藏 ===> fullHousePlanViewNum=" + fullHousePlanViewNum);
        redisService.del(FULL_HOUSE_DESIGN_PLAN_VIEW_NUM_SYNCHRONIZE_MAP);


        if (null != userDesignPlanInfoMap && userDesignPlanInfoMap.size() > 0) {
            DesignPlanLike designPlanLike = new DesignPlanLike();
            DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();

            //遍历得到用户点赞收藏记录，插入数据库
            for (Map.Entry<String, String> entry : userDesignPlanInfoMap.entrySet()) {
                //获取点赞收藏记录的key，value
                String userDesignPlanInfoKey = entry.getKey();
                String userInfoValue = entry.getValue();
                //截取得到用户id，方案id
                String[] userDesignPlanInfoKeySplit = userDesignPlanInfoKey.split(":");
                Integer userId = Integer.parseInt(userDesignPlanInfoKeySplit[0]);
                Integer designPlanId = Integer.parseInt(userDesignPlanInfoKeySplit[1]);
                //截取得到点赞收藏状态
                String[] userInfoValueSplit = userInfoValue.split(":");
                Integer likeStatus = Integer.parseInt(userInfoValueSplit[0]);
                Integer favoritestatus = Integer.parseInt(userInfoValueSplit[1]);
                //插入数据库，保存或修改
                designPlanLike.setUserId(userId);
                designPlanLike.setDesignId(designPlanId);
                designPlanLike.setStatus(likeStatus);
                logger.info("--------同步方案点赞收藏 ===> userId:{}, designPlanId:{}, likeStatus:{}", userId, designPlanId, likeStatus);
                designPlanLikeService.saveOrUpdate(designPlanLike);

                favoriteRef.setRecommendId(designPlanId);
                favoriteRef.setUserId(userId);
                favoriteRef.setStatus(favoritestatus);
                logger.info("--------同步方案点赞收藏 ===> favoriteRef:{}", favoriteRef);
                designPlanRecommendFavoriteService.saveOrUpdate(favoriteRef);
            }
        }
        if (null != userFullHouseDesignPlanInfoMap && userFullHouseDesignPlanInfoMap.size() > 0) {
            DesignPlanLike designPlanLike = new DesignPlanLike();
            DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();

            //遍历得到用户点赞收藏记录，插入数据库
            for (Map.Entry<String, String> entry : userFullHouseDesignPlanInfoMap.entrySet()) {
                //获取点赞收藏记录的key，value
                String userFullHouseDesignPlanInfoKey = entry.getKey();
                String userFullHouseInfoValue = entry.getValue();
                //截取得到用户id，方案id
                String[] userDesignPlanInfoKeySplit = userFullHouseDesignPlanInfoKey.split(":");
                Integer userId = Integer.parseInt(userDesignPlanInfoKeySplit[0]);
                Integer fullHouseDesignPlanId = Integer.parseInt(userDesignPlanInfoKeySplit[1]);
                //截取得到点赞收藏状态
                String[] userInfoValueSplit = userFullHouseInfoValue.split(":");
                Integer likeStatus = Integer.parseInt(userInfoValueSplit[0]);
                Integer favoriteStatus = Integer.parseInt(userInfoValueSplit[1]);
                //插入数据库，保存或修改
                designPlanLike.setUserId(userId);
                designPlanLike.setFullHouseDesignPlanId(fullHouseDesignPlanId);
                designPlanLike.setStatus(likeStatus);
                logger.info("--------同步全屋方案点赞收藏 ===> userId:{}, fullHouseDesignPlanId:{}, likeStatus:{}", userId, fullHouseDesignPlanId, likeStatus);
                designPlanLikeService.saveOrUpdateFullHouseDesignPlan(designPlanLike);

                favoriteRef.setFullHouseDesignPlanId(fullHouseDesignPlanId);
                favoriteRef.setUserId(userId);
                favoriteRef.setStatus(favoriteStatus);
                logger.info("--------同步全屋方案点赞收藏 ===> favoriteRef:{}", favoriteRef);
                designPlanRecommendFavoriteService.saveOrUpdateFullHouseDesignPlan(favoriteRef);
            }
        }
        DesignPlanSummaryInfo summaryInfo = new DesignPlanSummaryInfo();
        if (null != planLikeNum && planLikeNum.size() > 0) {
            //得到方案点赞记录，插入数据库
            for (Map.Entry<String, String> entry : planLikeNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String likeNumStr = entry.getValue();
                summaryInfo.setDesignId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setLikeNum(Integer.parseInt(likeNumStr));
                summaryInfo.setFullHouseDesignPlanId(0);
                summaryInfo.setDesignPlanType(0);
                logger.info("--------同步方案点赞收藏 ===> summaryInfo:{}", summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
        if (null != planFavoriteNum && planFavoriteNum.size() > 0) {
            //把之前的点赞数清空
            summaryInfo.setLikeNum(null);
            for (Map.Entry<String, String> entry : planFavoriteNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String collectNumStr = entry.getValue();
                summaryInfo.setDesignId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setCollectNum(Integer.parseInt(collectNumStr));
                summaryInfo.setFullHouseDesignPlanId(0);
                summaryInfo.setDesignPlanType(0);
                logger.info("--------同步方案点赞收藏 ===> summaryInfo:{}", summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
        if (null != planViewNum && planViewNum.size() > 0) {
            //把之前的点赞数清空
            summaryInfo.setCollectNum(null);
            for (Map.Entry<String, String> entry : planViewNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String viewNumStr = entry.getValue();
                summaryInfo.setDesignId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setViewNum(Integer.parseInt(viewNumStr));
                summaryInfo.setFullHouseDesignPlanId(0);
                summaryInfo.setDesignPlanType(0);
                logger.info("--------同步方案点赞收藏 ===> summaryInfo:{}", summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
        if (null != fullHousePlanLikeNum && fullHousePlanLikeNum.size() > 0) {
            summaryInfo.setCollectNum(null);
            //得到方案点赞记录，插入数据库
            for (Map.Entry<String, String> entry : fullHousePlanLikeNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String likeNumStr = entry.getValue();
                summaryInfo.setFullHouseDesignPlanId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setLikeNum(Integer.parseInt(likeNumStr));
                summaryInfo.setDesignId(0);
                summaryInfo.setDesignPlanType(1);
                logger.info("--------同步全屋方案点赞收藏 ===> summaryInfo:{}",summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
        if (null != fullHousePlanFavoriteNum && fullHousePlanFavoriteNum.size() > 0) {
            //把之前的点赞数清空
            summaryInfo.setLikeNum(null);
            for (Map.Entry<String, String> entry : fullHousePlanFavoriteNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String collectNumStr = entry.getValue();
                summaryInfo.setFullHouseDesignPlanId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setCollectNum(Integer.parseInt(collectNumStr));
                summaryInfo.setDesignId(0);
                summaryInfo.setDesignPlanType(1);
                logger.info("--------同步全屋方案点赞收藏 ===> summaryInfo:{}",summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
        if (null != fullHousePlanViewNum && fullHousePlanViewNum.size() > 0) {
            //把之前的点赞数清空
            summaryInfo.setCollectNum(null);
            for (Map.Entry<String, String> entry : fullHousePlanViewNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String viewNumStr = entry.getValue();
                summaryInfo.setFullHouseDesignPlanId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setViewNum(Integer.parseInt(viewNumStr));
                summaryInfo.setDesignId(0);
                summaryInfo.setDesignPlanType(1);
                logger.info("--------同步全屋方案点赞收藏 ===> summaryInfo:{}",summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
        logger.info("--------同步方案点赞收藏相关数据结束--------");

        Map<String, String> planVrViewNum = redisService.getMap(DESIGN_PLAN_VR_VIEW_NUM_SYNCHRONIZE_MAP);
        logger.info("--------同步方案人气值 ===> planFavoriteNum="+planVrViewNum);
        redisService.del(DESIGN_PLAN_VR_VIEW_NUM_SYNCHRONIZE_MAP);
        if (null != planVrViewNum && planVrViewNum.size() > 0) {
            //把之前的点赞数清空
            summaryInfo.setViewNum(null);
            for (Map.Entry<String, String> entry : planVrViewNum.entrySet()) {
                String designPlanIdStr = entry.getKey();
                String vrViewNumStr = entry.getValue();
                summaryInfo.setDesignId(Integer.parseInt(designPlanIdStr));
                summaryInfo.setVrViewNum(Integer.parseInt(vrViewNumStr));
                summaryInfo.setFullHouseDesignPlanId(0);
                summaryInfo.setDesignPlanType(0);
                logger.info("--------同步方案点赞收藏 ===> summaryInfo:{}",summaryInfo);
                designPlanSummaryInfoService.saveOrUpdate(summaryInfo);
            }
        }
    }
}
