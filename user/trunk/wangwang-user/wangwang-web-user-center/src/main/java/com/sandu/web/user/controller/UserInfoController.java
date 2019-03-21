package com.sandu.web.user.controller;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.BaseAreaService;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.user.input.MinProUserInfo;
import com.sandu.api.user.input.UserInfoEdit;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Api("B端用户获取用户信息")
@RequestMapping("/v2/user/center")
@RestController
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    private static final String USER_SOURCE_COMPANY = "userSourceCompany";

    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/getUserInfo2b")
    public Object getUserInfo() {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            SysUser sysUser = sysUserService.get(loginUser.getId().intValue());

            if (sysUser != null) {
                this.buildReturnMessage(sysUser);
                return new ResponseEnvelope<>(true, sysUser, "获取用户信息成功", null);
            }
        } catch (Exception e) {
            logger.error("系统错误:",e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(true, null, "获取用户信息失败", null);
    }

    private void buildReturnMessage(SysUser sysUser) {
        //返回区域信息
        setUserAreaName(sysUser);

        //返回用户当前企业名称
        setUserConpanyName(sysUser);

        //返回用户头像路劲
        String s = sysUserService.setPicPath(sysUser);
        sysUser.setPicPath(s);

        //如果用户为中介 =>{}需要返回来源企业名称
        if (sysUser.getSourceCompany() != null && sysUser.getSourceCompany() != 0 && sysUser.getUserType() == 11) {
            setSourceCompanyName(sysUser);
        }

        //如果是套餐用户,查询套餐是否过期
        if (Objects.equals(sysUser.getServicesFlag(),1)){
            //查询该用户套餐是否过期
            Map<String,Object> packMap = sysUserService.getUserPackInfo(sysUser.getId());

            Date effectiveEnd = (Date)packMap.get("effectiveEnd");

            if (Objects.nonNull(packMap.get("servicesId"))){
                //获取套餐类型
                String servicesType = sysUserService.getPackType(Integer.parseInt(packMap.get("servicesId").toString()));
                if (StringUtils.isNotEmpty(servicesType)){
                    sysUser.setServiceType(Integer.parseInt(servicesType));
                }
            }
            //过期了
            sysUser.setMaturityFlag(new Date().compareTo(effectiveEnd) >= 0 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    private void setSourceCompanyName(SysUser sysUser) {
        List<SysDictionary> sourceCompanys = sysDictionaryService.getListByType(USER_SOURCE_COMPANY);

        SysDictionary sysDictionary = sourceCompanys.stream().filter(source -> {
            return Objects.equals(sysUser.getSourceCompany(), source.getValue());
        }).findFirst().get();

        sysUser.setSourceCompanyName(sysDictionary.getName());
    }

    private void setUserConpanyName(SysUser sysUser) {
        BaseCompany baseCompany = baseCompanyService.queryById(sysUser.getCompanyId());
        if (null != baseCompany) {
            sysUser.setCompanyName(baseCompany.getCompanyName());
        }
    }

    private void setUserAreaName(SysUser sysUser) {
        //String areaName = baseAreaService.getAreaName(sysUser.getAreaId());
        if (Optional.ofNullable(sysUser.getProvinceCode()).isPresent()) {
            String provinceName = baseAreaService.selectByAreaCode(sysUser.getProvinceCode());
            sysUser.setProvinceName(provinceName);
        }

        if (Optional.ofNullable(sysUser.getCityCode()).isPresent()) {
            String cityName = baseAreaService.selectByAreaCode(sysUser.getCityCode());
            sysUser.setCityName(cityName);
        }

    }


    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/editUserInfo2b")
    public Object editUserInfo(@ModelAttribute UserInfoEdit userInfoEdit) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        try {
            int result = sysUserService.editUserInfo(loginUser.getId(), userInfoEdit);
            if (result == 1) {
                SysUser sysUser = sysUserService.get(loginUser.getId().intValue());
                sysUser.setAreaName(userInfoEdit.getAreaName());
                this.buildReturnMessage(sysUser);
                return new ResponseEnvelope<>(true, sysUser, "修改信息成功", null);
            }
        } catch (BizException b) {
            return new ResponseEnvelope<>(false, b.getMessage());
        } catch (Exception e) {
            logger.error("系统错误:" + e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(false, userInfoEdit.getAreaName(), "修改信息失败", null);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/modifyUserInfo")
    public ResponseEnvelope modifyUserInfo(MinProUserInfo minProUserInfo) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {

            if (StringUtils.isNotEmpty(minProUserInfo.getNickName())) {
                String filterNickName = filterEmoji(minProUserInfo.getNickName());
                minProUserInfo.setNickName(filterNickName);
            }
            int result = sysUserService.modifyUserInfo(minProUserInfo, loginUser.getId());
            return new ResponseEnvelope(true, "修改成功");
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope(false, "系统错误");
        }
    }

    public String filterEmoji(String nickName) {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(nickName);
        if (emojiMatcher.find()) {
            //将所获取的表情转换为*
            nickName = emojiMatcher.replaceAll("*");
            return nickName;
        }
        return nickName;
    }

    @ApiOperation(value = "修改手机号")
    @PostMapping(value = "/editPhone")
    public Object editPhone(String mobile, String authCode) {

        if (StringUtils.isEmpty(mobile) || !checkMobileFormat(mobile)) {
            return new ResponseEnvelope<>(false, "请输入正确的手机号");
        }

        if (StringUtils.isEmpty(authCode)) {
            return new ResponseEnvelope<>(false, "验证码不能为空");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        try {
            int result = sysUserService.modifiPhone(loginUser.getId(), mobile, authCode);
            if (result == 1) {
                return new ResponseEnvelope<>(true, "修改手机号成功");
            }
        } catch (BizException b) {
            return new ResponseEnvelope<>(false, b.getMessage());
        } catch (Exception e) {
            logger.error("系统异常:" + e);
            return new ResponseEnvelope<>(false, "系统异常");
        }
        return new ResponseEnvelope<>(false, "修改手机号失败");
    }

    @GetMapping("/getUserNickNameANDMobile")
    public Object getUserNickNameANDMobile(String nickName, String mobile) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = sysUserService.getUserNickNameANDMobile(nickName, mobile);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(true, resultMap);
    }


    @RequestMapping(value = "/applyFormal")
    public Object applyFormalAccount(String msgId, HttpServletRequest request) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        //获取平台编码
        String platformCode = request.getHeader("Platform-Code");

        try {
            int row = sysUserService.applyFormalAccount(loginUser.getId(),platformCode);
            if (row > 0) {
                return new ResponseEnvelope<>(true, "申请成功", msgId);
            }
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误", msgId);
        }
        return new ResponseEnvelope<>(true, "申请失败", msgId);
    }

    /**
     * 手机号验证
     *
     * @param str 手机号
     * @return 验证通过返回true
     */
    private static boolean checkMobileFormat(String str) {
        boolean b = false;
        Pattern p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[57])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }

}
