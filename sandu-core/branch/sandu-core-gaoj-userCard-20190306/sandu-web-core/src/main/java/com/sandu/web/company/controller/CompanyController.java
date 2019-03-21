package com.sandu.web.company.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.base.input.GroupPurchaseActivitySearch;
import com.sandu.api.base.model.*;
import com.sandu.api.base.output.BaseCompanyVo;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.base.service.SysUserService;
import com.sandu.common.LoginContext;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/23
 * @since : sandu_yun_1.0
 */
@Log4j2(topic = "[CompanyController]")
@RestController
@RequestMapping("/v1/core/company")
@Api(tags = "CompanyController")
public class CompanyController {

    private final Gson gson = new Gson();

    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/dashboardconfig/{appId}")
    public ResponseEnvelope getMiniPorgramDashboardConfig(@PathVariable String appId) {
        MiniProgramDashboard data = baseCompanyService.getMiniProgramDashboardByAppId(appId);
        if (data == null || StringUtils.isEmpty(data.getConfig())) {
            return new ResponseEnvelope(true, "您还没有配置小程序首页，请前往商家后台配置!", data);
        }
        String configJson = data.getConfig();
        Map<String, MiniProgramDashboardConfig> mapConfig = gson.fromJson(configJson, new TypeToken<Map<String, MiniProgramDashboardConfig>>() {
        }.getType());
        data.setConfigMap(mapConfig);
        data.setConfig(null);
        return new ResponseEnvelope(true, "ok", data);
    }

    @GetMapping("/branddesc/{appId}/{getRichContext}")
    public ResponseEnvelope getBrandDesc(@PathVariable String appId, @PathVariable String getRichContext) {
        CompanyBrandDesc data = baseCompanyService.getCompanyBrandDesc(appId, getRichContext);
        return new ResponseEnvelope(true, "ok", data);
    }

    @GetMapping("/actbargain/{appId}")
    public ResponseEnvelope getActBargain(@PathVariable String appId) {
        List<MiniProgramActBargain> dataList = baseCompanyService.getMiniProgramActBargainListByAppId(appId);
        return new ResponseEnvelope(true, dataList.size(), dataList);
    }


    @RequestMapping(value = "/groupPurchaseActivity", method = RequestMethod.POST)
    public ResponseEnvelope getGroupPurchaseActivity(@RequestBody GroupPurchaseActivitySearch search) {
        //用户及参数校验获取
        if (StringUtils.isEmpty(search.getAppId())) {
            return new ResponseEnvelope(false, "Param is empty!");
        }
        List<GroupPurchaseActivity> dataList = baseCompanyService.getMiniProgramGroupPurchaseActivityListByAppId(search);
        return new ResponseEnvelope(true, dataList.size(), dataList);
    }

    @GetMapping("/getAllCompany")
    public ResponseEnvelope getAllCompany() {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            return new ResponseEnvelope(false, "请登录");
        }

        SysUser sysUser = sysUserService.selectByPrimaryKey(loginUser.getId().longValue());
        if (null == sysUser) {
            return new ResponseEnvelope(false, "请重新登录");
        }
        try {
            List<BaseCompanyVo> companyList = baseCompanyService.getAllCompany(sysUser);
            if (CollectionUtils.isEmpty(companyList)) {
                return new ResponseEnvelope(false, "获取企业信息失败");
            }
            return new ResponseEnvelope(true, "获取企业信息成功", companyList.size(), companyList);
        } catch (BizException e) {
            log.warn("获取用户所属企业的所有公司异常，BizException={}", e);
            return new ResponseEnvelope(false, e.getMessage());
        } catch (Exception e) {
            log.error("获取用户所属企业的所有公司异常，exception={}", e);
            return new ResponseEnvelope(false, "系统异常");
        }
    }
}
