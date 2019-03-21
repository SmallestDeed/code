package com.sandu.web.designplan.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sandu.common.LoginContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.common.util.LoginUserCommonConstant;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.UserSessionService;

import net.minidev.json.JSONObject;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 6:10 2018/1/8 0008
 * @Modified By:
 */
@Controller
@RequestMapping("/{style}/tocmobile/designPlanLike")
public class DesignPlanLikeController {

    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DesignPlanLikeController.class);

	@Autowired
	private DesignPlanLikeService designPlanLikeService;
	@Autowired
    private UserSessionService userSessionService;
    /**
     * 方案点赞或取消点赞
     *
     * @param designPlanLike
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setLikeOrDislike")
    @ResponseBody
    public ResponseEnvelope setLikeOrDislike(@RequestBody DesignPlanLike designPlanLike, HttpServletRequest request) {
        if (designPlanLike == null || designPlanLike.getDesignId() == null || designPlanLike.getStatus() == null) {
            return new ResponseEnvelope(false, "参数传递有误！");
        }
        UserSO userSo = null;
//        String token = request.getHeader("Authorization");
//        boolean flat = userSessionService.checkTokenTimeOut(token);
//        if (flat) {
//            Map<String, Object> dataMap = Jwt.validToken(token);
//            JSONObject dataObj = (JSONObject) dataMap.get("data");
//            Long userid = (Long) dataObj.get("uid");
//            if (userid != null) {
//                LoginUser loginUser = userSessionService.getUserFromCache(LoginUserCommonConstant.LOGIN_FROM_TOCMOBILE + userid);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser != null) {
            userSo = new UserSO();
            userSo.setUserId(loginUser.getId());
            userSo.setUserName(loginUser.getLoginName());
            userSo.setUserMobile(loginUser.getLoginPhone());
            userSo.setUserType(loginUser.getUserType());
        }

        long likeCount = designPlanLikeService.setLikeOrDislike(UserObject.parseUserSoToLoginUser(userSo), designPlanLike);

        return new ResponseEnvelope(true, likeCount);
    }

}
