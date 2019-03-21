package com.sandu.web.user.controller;

import com.sandu.api.user.input.UserUpdateMobile;
import com.sandu.api.user.input.UserUpdatePasswordAndMobile;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.ResponseBo;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.ReturnData;
import com.sandu.common.exception.BizException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/v2/user/center")
public class EditPasswordController {

    private Logger logger = LoggerFactory.getLogger(EditPasswordController.class);

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public ResponseEnvelope editPassword(String newPassword,
                                         String authCode,
                                         String mobile,
                                         HttpServletRequest request
    ) {

        if (StringUtils.isEmpty(newPassword))
            return new ResponseEnvelope<>(false, "密码不能为空");

        if (StringUtils.isEmpty(authCode))
            return new ResponseEnvelope<>(false, "验证码不能为空");

        try {
            //先检验手机号是否存在
            int count = sysUserService.check2BMobileIsExist(mobile);
            if (count < 1) {
                return new ResponseEnvelope(false, "您输入的手机号不存在!");
            }
            //执行更新密码
            int result = sysUserService.editPassword(mobile, newPassword, authCode);
            if (result > 0) {
                return new ResponseEnvelope<>(true, mobile, "修改密码成功", null);
            }
        } catch (BizException bz) {
            return new ResponseEnvelope<>(false, bz.getMessage());
        } catch (Exception e) {
            logger.error("系统异常!", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(false, mobile, "修改密码失败", null);
    }


    @PostMapping("/modify2BUserMobileAndPassword")
    @ApiOperation(value = "B端用户绑定手机号及修改用户密码")
    public ResponseEnvelope modify2BUserMobileAndPassword(@Valid UserUpdatePasswordAndMobile userParam, BindingResult validResult) {
        if (validResult.hasFieldErrors()) {
            return new ResponseEnvelope(true, validResult.getFieldError().getDefaultMessage(), userParam.getMsgId(), false);
        }
        if (!isMobile(userParam.getMobile())) {
            return new ResponseEnvelope(false, 0, "手机格式不正确!", userParam.getMsgId());
        }

        try {
            //检查验证码.
            ResponseBo flags = sysUserService.checkPhone(userParam.getMobile(), userParam.getPhoneCode());
            if (!flags.getStatus()) {
                return new ResponseEnvelope(false, 0, flags.getMsg(), userParam.getMsgId());
            }
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //更新手机号和密码.
            sysUserService.modify2BUserMobileAndPassword(userParam, loginUser.getId().intValue());
            return new ResponseEnvelope(true, 0, "修改成功!", userParam.getMsgId());
        } catch (BizException ex) {
            return new ResponseEnvelope<>(true, null, ex.getMessage(), userParam.getMsgId(), ex.getCode());
        } catch (Exception ex) {
            logger.error("未知异常:", ex);
            return new ResponseEnvelope(true, "未知异常,请联系客服!", null, false);
        }
    }


    @ApiOperation(value = "修改B端用户手机号", response = ReturnData.class)
    @PostMapping("/modify2BUserMobile")
    public Object modify2BUserMobile(HttpServletRequest request, @Valid @ModelAttribute UserUpdateMobile userUpdateMobile, BindingResult validResult) {
        if (validResult.hasFieldErrors()) {
            return new ResponseEnvelope(true, validResult.getFieldError().getDefaultMessage(), userUpdateMobile.getMsgId(), false);
        }
        if (!isMobile(userUpdateMobile.getMobile())) {
            return new ResponseEnvelope(false, 0, "手机格式不正确!", userUpdateMobile.getMsgId());
        }
        if (null == userUpdateMobile.getPhoneCode() || "".equals(userUpdateMobile.getPhoneCode())) {
            return new ResponseEnvelope(false, 0, "短信验证码不能为空!", userUpdateMobile.getMsgId());
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //校验手机短信验证码
        ResponseBo flag = sysUserService.checkPhone(userUpdateMobile.getMobile(), userUpdateMobile.getPhoneCode());//提到controller去
        if (flag.getStatus()) {
  //      if (1 == 1) {
            try {
                int updateCount = sysUserService.modify2BUserMobile(userUpdateMobile, loginUser);
                if (updateCount > 0) {
                    return new ResponseEnvelope(true, null, "绑定成功!", userUpdateMobile.getMsgId(), true);
                }
                return new ResponseEnvelope(false, null, "绑定失败!", userUpdateMobile.getMsgId());
            } catch (BizException ex) {
                return new ResponseEnvelope<>(true, null, ex.getMessage(), userUpdateMobile.getMsgId(), ex.getCode());
            } catch (Exception ex) {
                logger.error("系统错误:", ex);
                return new ResponseEnvelope(false, "系统错误!", userUpdateMobile.getMsgId());
            }
        } else {
            return new ResponseEnvelope(false, 0, flag.getMsg(), userUpdateMobile.getMsgId());
        }
    }

    @ApiOperation(value = "商家后台修改密码", response = ResponseEnvelope.class)
    @ApiImplicitParam(name = "新密码", required = true)
    @PostMapping("/updatePasswordByMerchantManage")
    public ResponseEnvelope updatePasswordByMerchantManage(String oldPassword, String newPassword) {

        if (StringUtils.isEmpty(newPassword)) {
            return new ResponseEnvelope(false, "新密码不能为空!");
        }

        if (StringUtils.isEmpty(oldPassword)) {
            return new ResponseEnvelope(false, "旧密码不能为空!");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        try {
            int result = sysUserService.updatePasswordByMerchantManage(loginUser.getId(), newPassword, oldPassword);
            if (result > 0) {
                return new ResponseEnvelope(true, 1, "修改密码成功", null);
            }
        } catch (BizException ex) {
            return new ResponseEnvelope(false, 0, ex.getMessage(), null);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope(false, 0, "系统错误", null);
        }
        return new ResponseEnvelope(false, 0, "修改手机号失败", null);

    }

    @ApiOperation(value = "明文密码修改", response = ResponseEnvelope.class)
    @PostMapping("/modifyPassword")
    public ResponseEnvelope modifyPassword(String oldPassword, String newPassword){

        if (StringUtils.isEmpty(newPassword)) {
            return new ResponseEnvelope(false, "新密码不能为空!");
        }

        if (StringUtils.isEmpty(oldPassword)) {
            return new ResponseEnvelope(false, "旧密码不能为空!");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        try {
            int result = sysUserService.modifyPassword(loginUser.getId(), newPassword, oldPassword);
            if (result > 0) {
                return new ResponseEnvelope(true, 1, "修改密码成功", null);
            }
        } catch (BizException ex) {
            return new ResponseEnvelope(false, 0, ex.getMessage(), null);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope(false, 0, "系统错误", null);
        }
        return new ResponseEnvelope(false, 0, "修改手机号失败", null);
    }

    @ApiOperation(value = "检验旧密码", response = ResponseEnvelope.class)
    @ApiImplicitParam(value = "旧密码", required = true)
    @PostMapping("/checkUserOldPassword")
    public ResponseEnvelope checkUserOldPassword(String oldPassword) {

        if (oldPassword == null) {
            return new ResponseEnvelope(false, 0, "参数不能为空!", null);
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        try {
            SysUser sysUser = sysUserService.get(loginUser.getId().intValue());

            if (oldPassword.equals(sysUser.getPassword())) {
                return new ResponseEnvelope(true, 1, "原密码输入正确!", null);
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            return new ResponseEnvelope(false, 0, "系统错误", null);
        }
        return new ResponseEnvelope(false, 0, "原密码输入错误", null);
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[57])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
