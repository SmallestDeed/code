package com.sandu.web.designplan.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.model.DesignPlanLikeInput;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.UserSessionService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@ApiOperation(value = "方案点赞或取消点赞", response = ReturnData.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "status", value = "点赞状态（0：未点赞，1：已点赞）",required=true, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "designId", value = "方案id",required=true, paramType = "query", dataType = "String")
    })
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setLikeOrDislike",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope setLikeOrDislike(@RequestBody DesignPlanLikeInput designPlanLikeInput, HttpServletRequest request) {
        if (designPlanLikeInput == null || designPlanLikeInput.getDesignId() == null || designPlanLikeInput.getStatus() == null) {
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
        DesignPlanLike  designPlanLike = new DesignPlanLike();
        designPlanLike.setStatus(designPlanLikeInput.getStatus());
        designPlanLike.setDesignId(designPlanLikeInput.getDesignId());
        long likeCount = designPlanLikeService.setLikeOrDislike(UserObject.parseUserSoToLoginUser(userSo), designPlanLike);

        return new ResponseEnvelope(true, likeCount);
    }

}
