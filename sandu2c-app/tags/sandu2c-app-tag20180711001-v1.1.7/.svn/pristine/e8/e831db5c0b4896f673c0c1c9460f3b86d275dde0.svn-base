package com.sandu.designplan.service.impl;


import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.designplan.dao.DesignPlanLikeMapper;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.model.DesignPlanRecommendFavoriteRef;
import com.sandu.designplan.model.DesignPlanSummaryInfo;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.designplan.service.DesignPlanRecommendFavoriteService;
import com.sandu.designplan.service.DesignPlanSummaryInfoService;
import com.sandu.user.model.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sandu.cache.RedisKeyConstans.*;


/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 6:14 2018/1/8 0008
 * @Modified By:
 */
@Service("designPlanLikeService")
public class DesignPlanLikeServiceImpl implements DesignPlanLikeService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[方案模块-方案点赞服务]:";
    private final static Logger logger = LoggerFactory.getLogger(DesignPlanLikeServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanLikeMapper designPlanLikeMapper;
    @Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;
    @Autowired
    private DesignPlanSummaryInfoService designPlanSummaryInfoService;

    /**
     * 新增数据
     *
     * @param designPlanLike
     * @return int
     */
    @Override
    public int add(DesignPlanLike designPlanLike) {
        designPlanLikeMapper.insertSelective(designPlanLike);
        return designPlanLike.getId();
    }

    /**
     * 更新数据
     *
     * @param designPlanLike
     * @return int
     */
    @Override
    public int update(DesignPlanLike designPlanLike) {
        return designPlanLikeMapper.updateByPrimaryKeySelective(designPlanLike);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return designPlanLikeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignPlanLike
     */
    @Override
    public DesignPlanLike get(Integer id) {
        return designPlanLikeMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param designPlanLike
     * @return List<DesignPlanLike>
     */
    @Override
    public List<DesignPlanLike> getList(DesignPlanLike designPlanLike) {
        return designPlanLikeMapper.selectList(designPlanLike);
    }

    @Override
    public int deleteById(DesignPlanLike designPlanLike) {
        return designPlanLikeMapper.deleteById(designPlanLike);
    }

    /**
     * 用户点赞或取消点赞
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public long setLikeOrDislike(LoginUser loginUser, DesignPlanLike designPlanLike) {

        Integer designId = designPlanLike.getDesignId();

        //从缓存中获取方案点赞总数,获取不到从数据库查找,再存入缓存
        String designPlanLikeNum = redisService.getMap(DESIGN_PLAN_LIKE_NUM_MAP, designId + "");
        long likeNum = 0;
        if (StringUtils.isEmpty(designPlanLikeNum)) {
            DesignPlanSummaryInfo summaryInfo = designPlanSummaryInfoService.get(designId);
            if (null != summaryInfo) {
                likeNum = (long) summaryInfo.getLikeNum();
            }
            redisService.addMap(DESIGN_PLAN_LIKE_NUM_MAP, designId + "", likeNum + "");
        }

        Integer status = designPlanLike.getStatus();
        logger.info("------用户 " + loginUser + " 操作前,方案 " + designId + " 的点赞数为：" + likeNum);
        //根据前台传过来的点赞状态对点赞总数进行修改
        if (status == 0) {
            likeNum = redisService.mapIncrby(DESIGN_PLAN_LIKE_NUM_MAP, designId + "", -1L);
        } else {
            likeNum = redisService.mapIncrby(DESIGN_PLAN_LIKE_NUM_MAP, designId + "", 1L);
        }
        if (likeNum < 0) {
            likeNum = 0;
        }
        logger.info("------用户 " + loginUser + " 操作后,方案 " + designId + " 的收藏数为：" + likeNum);
        //把点赞总数存到同步的缓存中
        redisService.addMap(DESIGN_PLAN_LIKE_NUM_SYNCHRONIZE_MAP, designId + "", likeNum + "");
        //未登录，不记录点赞信息，直接返回结果
        if (loginUser == null || loginUser.getId() == null || loginUser.getId() == 0) {
            return likeNum;
        }

        //已登录记录点赞信息到缓存中
        Integer userId = loginUser.getId();

        //从缓存中获取用户对方案的点赞信息
        String userLikeAndFavoritePlanInfo = redisService.getMap(USER_DESIGN_PLAN_INFO_MAP, userId + ":" + designId);
        Integer favoriteStatus;
        //缓存中没有，查收藏状态；有，获取索引为1的就是收藏状态
        if (StringUtils.isEmpty(userLikeAndFavoritePlanInfo)) {
            DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
            favoriteRef.setUserId(userId);
            favoriteRef.setRecommendId(designId);
            //查询用户收藏状态,已经处理过,肯定会返回整数的
            favoriteStatus = designPlanRecommendFavoriteService.getPlanFavoriteStatusOfUser(favoriteRef);
        } else {
            String[] userLikeAndFavoritePlanInfoSplit = userLikeAndFavoritePlanInfo.split(":");
            favoriteStatus = Integer.parseInt(userLikeAndFavoritePlanInfoSplit[1]);
        }

        //把用户对方案的点赞信息存入缓存
        redisService.addMap(USER_DESIGN_PLAN_INFO_MAP, userId + ":" + designId, status + ":" + favoriteStatus);
        //存到记录的map里，方便同步数据库，不用每次都同步所有的数据
        redisService.addMap(USER_DESIGN_PLAN_INFO_SYNCHRONIZE_MAP, userId + ":" + designId, status + ":" + favoriteStatus);

        return likeNum;
    }

    /**
     * 获取用户是否点赞收藏等
     *
     * @param
     * @return
     */
    @Override
    public String getPlanLikeAndFavoriteOfUser(LoginUser loginUser, Integer designId) {
        //未登录状态，直接返回0:0
        if (loginUser == null || designId == null || loginUser.getId() == null) {
            return "0:0";
        }
        Integer userId = loginUser.getId();

        // 缓存map的 key 为 userId_designId, value 为 (点赞状态：收藏状态（0：未XX，1：已XX）)
        String userInfoStr = redisService.getMap(USER_DESIGN_PLAN_INFO_MAP, userId + ":" + designId);
        if (StringUtils.isNotEmpty(userInfoStr)) {
            return userInfoStr;
        } else {
            Integer likeStatus = 0;

            //缓存中没有从数据库查询，再存入缓存，最后定时存入数据库
            //查询用户点赞状态
            DesignPlanLike designPlanLikeTemp = designPlanLikeMapper.selectByDesignIdAndUserId(userId, designId);
            if (designPlanLikeTemp != null && designPlanLikeTemp.getStatus() != null) {
                likeStatus = designPlanLikeTemp.getStatus();
            }

            DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
            favoriteRef.setUserId(userId);
            favoriteRef.setRecommendId(designId);
            //查询用户收藏状态,已经处理过,肯定会返回整数的
            Integer favoriteStatus = designPlanRecommendFavoriteService.getPlanFavoriteStatusOfUser(favoriteRef);
            //把点赞状态和收藏状态存入缓存
            redisService.addMap(USER_DESIGN_PLAN_INFO_MAP, userId + ":" + designId, likeStatus + ":" + favoriteStatus);

            return likeStatus + ":" + favoriteStatus;
        }
    }

    /**
     * 添加或修改（同步数据库用）
     *
     * @param designPlanLike
     * @return
     */
    @Override
    public int saveOrUpdate(DesignPlanLike designPlanLike) {
        if (designPlanLike == null || designPlanLike.getUserId() == null || designPlanLike.getDesignId() == null || designPlanLike.getStatus() == null) {
            return 0;
        }
        return designPlanLikeMapper.saveOrUpdate(designPlanLike);
    }

    /**
     * 从数据库中获取用户对方案的点赞状态
     *
     * @param userId
     * @param designId
     * @return
     */
    @Override
    public int getStatus(Integer userId, Integer designId) {
        if (userId == null || designId == null) {
            return 0;
        }
        Integer likeStatus = designPlanLikeMapper.getStatus(userId, designId);
        if (null == likeStatus) {
            return 0;
        }
        return likeStatus;
    }

    /**
     * 从缓存中获取方案点赞收藏信息
     *
     * @param userId
     * @param designPlanId
     * @return
     */
    @Override
    public DesignPlanSummaryInfo getPlanInfoOfCache(Integer userId, Integer designPlanId) {
        if (null == userId || null == designPlanId) {
            return null;
        }
        try {
            DesignPlanSummaryInfo summaryInfo = new DesignPlanSummaryInfo();
            //从缓存中获取方案点赞数
            String designPlanLikeNum = redisService.getMap(DESIGN_PLAN_LIKE_NUM_MAP, designPlanId + "");
            if (null != designPlanLikeNum) {
                summaryInfo.setLikeNum(Integer.parseInt(designPlanLikeNum));
            }
            //从缓存中获取方案收藏数
            String designPlanFavoriteNum = redisService.getMap(DESIGN_PLAN_FAVORITE_NUM_MAP, designPlanId + "");
            if (null != designPlanFavoriteNum) {
                summaryInfo.setCollectNum(Integer.parseInt(designPlanFavoriteNum));
            }
            //从缓存中获取用户对方案的点赞收藏信息
            String planInfoOfUserStr = redisService.getMap(USER_DESIGN_PLAN_INFO_MAP, userId + ":" + designPlanId);
            if (null != planInfoOfUserStr) {
                String[] planInfoOfUserArray = planInfoOfUserStr.split(":");
                summaryInfo.setIsLike(Integer.parseInt(planInfoOfUserArray[0]));
                summaryInfo.setIsFavorite(Integer.parseInt(planInfoOfUserArray[1]));
            }
            return summaryInfo;
        } catch (Exception e) {
            logger.error("------从缓存中获取方案点赞收藏信息: getPlanInfoOfCache " + e);
            return null;
        }
    }

}
