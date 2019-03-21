package com.sandu.web.designplan.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.designplan.model.DesignPlanRecommendFavoriteRef;
import com.sandu.designplan.model.DesignPlanRecommendFavoriteRefInput;
import com.sandu.designplan.service.DesignPlanRecommendFavoriteService;
import com.sandu.user.model.UserSO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/v1/miniprogram/designplanfavorite")
public class DesignPlanFavoriteController {

    @Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;
    
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
     * 方案收藏或取消收藏
     *
     * @param favoriteRef
     * @return
     */
    @ApiOperation(value = "方案收藏或取消收藏", response = ReturnData.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "status", value = "是否收藏（0：未收藏，1：已收藏）",required=true, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "recommendId", value = "推荐方案id",required=true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/setFavoriteOrUnfavorite",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope setFavoriteOrUnfavorite(@RequestBody DesignPlanRecommendFavoriteRefInput favoriteRefInput, HttpServletRequest request) {
    	DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        if (favoriteRefInput == null || favoriteRefInput.getRecommendId() == 0 || favoriteRefInput.getStatus() == null) {
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
        favoriteRef.setStatus(favoriteRefInput.getStatus());
        favoriteRef.setRecommendId(favoriteRefInput.getRecommendId());
        long favoriteNum = designPlanRecommendFavoriteService.setFavoriteOrUnfavorite(UserObject.parseUserSoToLoginUser(userSo), favoriteRef);

        return new ResponseEnvelope(true, favoriteNum);
    }

}
