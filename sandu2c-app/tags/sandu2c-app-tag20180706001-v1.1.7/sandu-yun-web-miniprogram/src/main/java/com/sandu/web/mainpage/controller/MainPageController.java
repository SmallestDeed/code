package com.sandu.web.mainpage.controller;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.goods.output.CompanyIntroduceVO;
import com.sandu.product.service.BaseCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "MainPageController", description = "小程序首页")
@Log4j2
@RestController
@RequestMapping("/v1/miniprogram/mainpage")
public class MainPageController {

    @Autowired
    private BaseCompanyService baseCompanyService;

    /**
     * @Author: zhangchengda
     * @Description: 小程序首页企业信息
     * @Date: 13:48 2018/7/2
     */
    @ApiOperation(value = "品牌介绍", response = ResponseEnvelope.class)
    @GetMapping("/company")
    public ResponseEnvelope getCompanyIntroduce(Integer companyId)
    {
        log.info("getCompanyIntroduce(Integer companyId);companyId={}",companyId);
        CompanyIntroduceVO introduce = baseCompanyService.getIntroduce(companyId);
        log.info("getCompanyIntroduce(Integer companyId);result={}",introduce);
        return new ResponseEnvelope(true,"success",introduce);
    }
}
