package com.sandu.web.designplan.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.user.model.UserSO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 6:10 2018/1/8 0008
 * @Modified By:
 */
@Controller
@RequestMapping("/v1/miniprogram/designPlanLike")
public class DesignPlanLikeController {

    private static Logger logger = Logger.getLogger(DesignPlanLikeController.class);
    private final static String CLASS_LOG_PREFIX = "[方案点赞服务]";

    @Autowired
    private DesignPlanLikeService designPlanLikeService;
  /*  @Autowired
    private RabbitService rabbitService;*/

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
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser != null) {
            userSo = new UserSO();
            userSo.setUserId(loginUser.getId());
            userSo.setUserName(loginUser.getLoginName());
            userSo.setUserMobile(loginUser.getLoginPhone());
            userSo.setUserType(loginUser.getUserType());
        }

        long likeCount = designPlanLikeService.setLikeOrDislike(UserObject.parseUserSoToLoginUser(userSo), designPlanLike);

 /*      *//*统计推荐方案点赞次数--start*//*
        try {
            rabbitService.sendMsg(new DesignPlanMessage(loginUser == null ? 0 : loginUser.getId(), designPlanLike.getDesignId(), ActionType.LIKE, PlatformType.PLATFORM_CODE_MINI_PROGRAM));
        } catch (RabbitException e) {
            logger.error(CLASS_LOG_PREFIX, e);
            e.getStackTrace();
            *//*统计推荐方案浏览次数--end*//*
        } finally {
            return new ResponseEnvelope(true, likeCount);
        }*/
        return new ResponseEnvelope(true, likeCount);

    }

}
