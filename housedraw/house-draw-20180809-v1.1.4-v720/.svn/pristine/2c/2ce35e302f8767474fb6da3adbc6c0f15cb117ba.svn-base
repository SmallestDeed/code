package com.sandu.web.company.controller;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.PlatformTypeBO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "公司")
@RestController
@RequestMapping("/v1/company")
public class BaseCompanyController extends BaseController {

    @Autowired
    private BaseCompanyService baseCompanyService;

    @PostMapping("/list")
    @ApiOperation(value = "获取公司列表", response = BaseCompany.class)
    public ReturnData listCompany(HttpServletRequest request) {
        return setMsgId(request).list(baseCompanyService.listCompany());
    }

    @PostMapping("/platform")
    @ApiOperation(value = "获取平台列表", response = PlatformTypeBO.class)
    public ReturnData listPlatform(HttpServletRequest request) {
        return setMsgId(request).list(baseCompanyService.listPlatform());
    }
}
