package com.sandu.web.user.controller;

import com.sandu.api.user.input.UserRegister;
import com.sandu.api.user.model.User;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.ReturnData;
import com.sandu.common.exception.BizException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/v2/user/center")
public class UserRegisterController {

    private Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "B端用户注册", response = ReturnData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "电话号码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "authCode", value = "验证码", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@Valid @ModelAttribute UserRegister userRegister, BindingResult validResult) {

        if (validResult.hasFieldErrors()) {
            return new ResponseEnvelope(true, validResult.getFieldError().getDefaultMessage());
        }
        try {
            int returnId = sysUserService.save(userRegister);

            if (returnId > 0) {
                return new ResponseEnvelope<>(true, 1, "注册成功!", null);
            }
        }catch (BizException ex){
            return new ResponseEnvelope<>(false,ex.getMsg());
        }
        catch (Exception e) {
            logger.error("系统错误!", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(false, 0, "注册失败!", null);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "mobile", value = "电话号码", paramType = "query", dataType = "String")})
    @RequestMapping(value = "/checkMobileExist", method = RequestMethod.POST)
    public ResponseEnvelope checkMobileExist(String mobile) {

        logger.info("校验手机号是否存在 =>{}" + mobile);

        if (StringUtils.isEmpty(mobile) || !checkMobileFormat(mobile)) {
            return new ResponseEnvelope(false, "请输入正确的手机号");
        }
        try {
            int count = sysUserService.check2BMobileIsExist(mobile);
            //boolean b = sysUserService.checkPhone(mobile);
            if (count < 1) {
                return new ResponseEnvelope(true, 0, "此手机号未注册", null);
            }
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope(false, "系统错误");
        }
        return new ResponseEnvelope(false, 1, "您输入的手机号码已存在", null);
    }


    /**
     * 手机号验证
     *
     * @param str 手机号
     * @return 验证通过返回true
     */
    private static boolean checkMobileFormat(String str) {
        boolean b = false;
        Pattern p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[3579])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }

}
