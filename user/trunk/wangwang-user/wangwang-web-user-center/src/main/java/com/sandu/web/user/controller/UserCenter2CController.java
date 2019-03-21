package com.sandu.web.user.controller;

import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.exception.BizException;
import com.sandu.pay.order.service.PayAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Api(value = "C端用户中心")
@RequestMapping("/v2/user/center")
public class UserCenter2CController {

    private Logger logger = LoggerFactory.getLogger(UserCenter2CController.class);

    @Autowired
    private SysUserService sysUserService;

    @Resource(name = "payAcctService")
    private PayAccountService payAccountService;

    @ApiOperation(value = "判断用户是否绑定手机号")
    @GetMapping(value = "/isUserMobileBinded")
    public Object isUserMobileBinded() {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        try {
            SysUser sysUser = sysUserService.get(loginUser.getId().intValue());

            if (sysUser != null && StringUtils.isNotEmpty(sysUser.getMobile())) {
                return new ResponseEnvelope<>(true, 1, "已绑定", null);
            }
        } catch (BizException e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(true, 0, "未绑定", null);
    }

    @ApiOperation("绑定手机号")
    @ApiImplicitParam(name = "mobile", value = "用户手机号", paramType = "query", dataType = "String")
    @PostMapping(value = "/bindUserMobile")
    public Object bing2CUserMobile(String mobile, String authCode) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (StringUtils.isEmpty(mobile) || !checkMobileFormat(mobile)) {
            return new ResponseEnvelope<>(false, "请输入正确的手机号");
        }
        try {
            SysUser sysUser = sysUserService.get(loginUser.getId().intValue());

            if (sysUser != null && StringUtils.isNotEmpty(sysUser.getMobile())) {
                return new ResponseEnvelope<>(false, "已绑定");
            }
            int resultId = sysUserService.modifyUserMobile2C(loginUser.getId().intValue(), mobile, authCode);
            if (resultId > 0) {
                //绑定成功赠送300积分 =>{} 随选网赠送度币
//                if (Objects.equals(sysUser.getAppId(),"wx42e6b214e6cdaed3")){
//                    logger.info("开始调用支付服务赠送积分");
//                    payAccountService.givingIntegral(loginUser.getId().intValue());
//                }
                return new ResponseEnvelope<>(true, "绑定手机号成功");
            }
        } catch (BizException e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }

        return new ResponseEnvelope<>(false, "绑定手机号失败");
    }

    @GetMapping("/checkUserSecondRender")
    public Object checkUserSecondRender() {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        boolean ExitsSecond = Boolean.FALSE;
        try {
            ExitsSecond = sysUserService.checkUserSecondRender(loginUser.getId());
            if (ExitsSecond) {
                return new ResponseEnvelope<>(ExitsSecond, 1);
            }
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope<>(false,"系统错误");
        }
        return new ResponseEnvelope<>(ExitsSecond, 0);
    }

    /**
     * 手机号验证
     *
     * @param str 手机号
     * @return 验证通过返回true
     */
    private static boolean checkMobileFormat(String str) {
        boolean b = false;
        Pattern p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[56789])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
