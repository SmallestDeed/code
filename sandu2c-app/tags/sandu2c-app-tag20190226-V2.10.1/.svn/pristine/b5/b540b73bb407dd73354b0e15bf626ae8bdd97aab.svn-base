package com.sandu.web.user;


import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserBehavior;
import com.sandu.user.model.view.UserBehaviorVO;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserBehaviorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/v1/behavior")
public class UserBehaviorController {

    @Resource
    private UserBehaviorService userBehaviorService;
    @Resource
    private SysUserService sysUserService;

    @GetMapping
    public ResponseEnvelope saveBehaviors(String type, Boolean wx, Boolean pyq) {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (loginUser == null) {
            responseEnvelope.setMessage("请登录...");
            return responseEnvelope;
        }
        String invitationCode = Optional.ofNullable(sysUserService.get(loginUser.getId()))
                .orElse(new SysUser())
                .getUsedInvitationCode();
//        String invitationCode = "sdfsd";
        if (wx != null && wx) {
            type = type + UserBehaviorVO.WX_MARK + UserBehaviorVO.FIELD_SUFFIX;
        } else if (pyq != null && pyq) {
            type = type + UserBehaviorVO.PYQ_MARK + UserBehaviorVO.FIELD_SUFFIX;
        } else {
            type = type + UserBehaviorVO.FIELD_SUFFIX;
        }
        Integer behaviorType = StringUtils.isNoneBlank(invitationCode) ? UserBehavior.MEDIATION_USER : UserBehavior.NORMAL_USER;
        type = type + "_" + behaviorType;
        if (StringUtils.isBlank(type) || !UserBehaviorVO.keyMap.containsKey(type)) {
            responseEnvelope.setMessage("参数错误");
            return responseEnvelope;
        }
        userBehaviorService.cacheCount(type);
        responseEnvelope.setMessage("success");
        return responseEnvelope;
    }

    @GetMapping("/sync")
    public Object syncCache() {
        userBehaviorService.syncToDB();
        return "success";
    }
}
