package com.sandu.web.designplan.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.designplan.model.DesignPlanRecommendFavoriteRef;
import com.sandu.designplan.model.FavoriteRecommendedModel;
import com.sandu.designplan.service.DesignPlanRecommendFavoriteService;
import com.sandu.user.model.UserSO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/v1/miniprogram/designplanfavorite")
public class DesignPlanFavoriteController {
    private static Logger logger = Logger.getLogger(DesignPlanFavoriteController.class);
    private final static String CLASS_LOG_PREFIX = "[方案收藏服务]";
    @Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;

  /*  @Autowired
    private RabbitService rabbitService;*/

    /**
     * 将推荐方案添加到个人创建好的收藏夹
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public ResponseEnvelope moveIn(HttpServletRequest request) {
        //推荐方案id
        String recommendId = request.getParameter("designPlanRecommendId");
        if (org.springframework.util.StringUtils.isEmpty(recommendId)) {
            return new ResponseEnvelope(false, "必需参数为空!");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        //构造收藏对象
        DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        favoriteRef.setUserId(loginUser.getId());
        favoriteRef.setRecommendId(Integer.valueOf(recommendId));

        //检查方案是否已被收藏
        boolean flag = designPlanRecommendFavoriteService.existInFavoriteNew(favoriteRef);
        if (!flag) {
            return new ResponseEnvelope(false, "请勿重复收藏!");
        }

        //收藏方案
        boolean favoriteStatus = designPlanRecommendFavoriteService.moveInFavorite(favoriteRef);
        return new ResponseEnvelope(favoriteStatus, favoriteStatus ? "收藏案例成功!" : "收藏案例失败!");
    }

    /**
     * 移除已经收藏的推荐方案
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    public ResponseEnvelope moveOut(HttpServletRequest request) {
        // 推荐方案的业务id
        String recommendRef_busyneesId = request.getParameter("bid");

        HttpSession session = request.getSession();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        favoriteRef.setBid(recommendRef_busyneesId);
        boolean delDesignPlanStatus = designPlanRecommendFavoriteService.moveOutFavorite(favoriteRef);

        return new ResponseEnvelope(delDesignPlanStatus, delDesignPlanStatus ? "取消案例收藏成功!" : "取消案例收藏失败!");
    }

    /**
     * 获取方案推荐收藏夹列表
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResponseEnvelope favoritePlanRecommendedList(HttpServletRequest request, HttpServletResponse response) {
        String livingName = request.getParameter("livingName");
        String limit = request.getParameter("pageSize");
        String start = request.getParameter("curPage");
        String favoriteBid = request.getParameter("fBid");
        String isSort = request.getParameter("isSort");
        String designRecommendedStyleId = request.getParameter("designPlanStyleId");//设计风格ID
        String areaValue = request.getParameter("spaceArea");//面积
        String houseType = request.getParameter("spaceType"); //方案的空间类型
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        FavoriteRecommendedModel model = new FavoriteRecommendedModel();
        model.setHouseType(houseType);
        model.setLivingName(livingName);
        model.setAreaValue(areaValue);
        model.setDesignRecommendedStyleId(designRecommendedStyleId);
        model.setUserId(loginUser.getId());
        model.setUserType(loginUser.getUserType());
        model.setLimit(limit);
        model.setStart(start);
        model.setIsSort(isSort);//收藏时间排序
        if (!"-1".equals(favoriteBid)) {
            model.setFavoriteBid(favoriteBid);
        }
        return designPlanRecommendFavoriteService.favoritePlanRecommendedList(model);
    }

    /**
     * 方案收藏或取消收藏
     *
     * @param favoriteRef
     * @return
     */
    @RequestMapping(value = "/setFavoriteOrUnfavorite")
    @ResponseBody
    public ResponseEnvelope setFavoriteOrUnfavorite(@RequestBody DesignPlanRecommendFavoriteRef favoriteRef, HttpServletRequest request) {
        if (favoriteRef == null || favoriteRef.getRecommendId() == 0 || favoriteRef.getStatus() == null) {
            return new ResponseEnvelope(false, "参数为空！");
        }
        UserSO userSo = null;
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser != null) {
			userSo = new UserSO();
			userSo.setUserId(loginUser.getId());
			userSo.setUserName(loginUser.getLoginName());
			userSo.setUserMobile(loginUser.getLoginPhone());
			userSo.setUserType(loginUser.getUserType());
		}
        com.sandu.user.model.LoginUser loginUser2 = UserObject.parseUserSoToLoginUser(userSo);
        //未登录的话，返回请登录
        if (loginUser2 == null || loginUser2.getId() == null || loginUser2.getId() == 0) {
            return new ResponseEnvelope(false, "登录超时，请重新登录");
        }
        long favoriteNum = 0;
        //if (favoriteRef.getDesignPlanType() == 0){
            favoriteNum = designPlanRecommendFavoriteService.setFavoriteOrUnfavorite(UserObject.parseUserSoToLoginUser(userSo), favoriteRef);
        //}else if (favoriteRef.getDesignPlanType() == 1){
        //    favoriteNum = designPlanRecommendFavoriteService.setFullHosueFavoriteOrUnfavorite(UserObject.parseUserSoToLoginUser(userSo), favoriteRef);
        //}


  /*     *//*统计推荐方案点赞次数--start*//*
        try {
            rabbitService.sendMsg(new DesignPlanMessage(loginUser == null ? 0 : loginUser.getId(),favoriteRef.getRecommendId(), ActionType.COLLECT, PlatformType.PLATFORM_CODE_MINI_PROGRAM));
        } catch (RabbitException e) {
            logger.error(CLASS_LOG_PREFIX, e);
            e.getStackTrace();
            *//*统计推荐方案浏览次数--end*//*
        } finally {
            return new ResponseEnvelope(true, favoriteNum);
        }*/

        return new ResponseEnvelope(true, favoriteNum);

    }

}
