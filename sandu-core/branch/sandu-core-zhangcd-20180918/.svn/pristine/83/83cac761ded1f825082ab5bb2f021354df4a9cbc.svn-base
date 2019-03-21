package com.sandu.web.designplan.controller;

import com.google.gson.Gson;
import com.sandu.api.base.common.BaseController;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.MessageResponse;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.designplan.input.PlanRecommendedQuery;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.designplan.service.DesignPlanRecommendedService;
import com.sandu.common.LoginContext;
import com.sandu.api.base.common.ResponseEnvelope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xiaoxc on 2018/5/31 0031.
 */
@Api(tags = "recommendedDesignPlan", description = "推荐方案")
@RestController
@RequestMapping("/v1/core/designplan")
public class DesignPlanRecommendedController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(DesignPlanRecommendedController.class);
    private final static String CLASS_LOG_PREFIX = "[推荐方案服务]:";
    private final static Gson GSON = new Gson();
    @Value("${sanducloudhome.company.code}")
    private String companyCode;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private HttpServletRequest request;


    /**
     * 获取推荐方案列表数据
     *
     * @param query
     * @return
     */
    @ApiOperation(value = "推荐方案列表", response = ResponseEnvelope.class)
    @RequestMapping(value = "/designplanrecommendedlist",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope designPlanRecommendedList(@ModelAttribute PlanRecommendedQuery query) {
        //用户及参数校验获取
        String platformCode = request.getHeader("Platform-Code");
        if (StringUtils.isEmpty(platformCode)) {
            return new ResponseEnvelope(false, "Param is empty!");
        }
        MessageResponse messageResponse = paramVerify(query, platformCode);
        if (!messageResponse.isStauts()) {
            return new ResponseEnvelope(false, messageResponse.getMessage());
        }

        // 获取企业信息
        BaseCompany baseCompany = null;
        Long companyId = Long.valueOf(query.getCompanyId());
        if (companyId == null) {
            companyId = query.getLoginUser().getBusinessAdministrationId();
        }
        if (companyId != null) {
            baseCompany = baseCompanyService.get(companyId.intValue());
        }

        // 获取推荐列表数据
        logger.info(CLASS_LOG_PREFIX + "获取方案推荐列表数据,获取列表参数数据->query:{}", query.toString());
        return designPlanRecommendedService.getPlanRecommendedList(query,companyCode,baseCompany);
    }

    /**
     * 根据不同条件获取方案数量
     *
     * @param query
     * @return
     */
    @ApiOperation(value = "推荐方案数量", response = ResponseEnvelope.class)
    @RequestMapping(value = "/recommendedPlanCount",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope recommendedPlanCount(@ModelAttribute PlanRecommendedQuery query) {

        //用户及参数校验获取
        String platformCode = request.getHeader("Platform-Code");
        if (StringUtils.isEmpty(platformCode)) {
            return new ResponseEnvelope(false, "Param is empty!");
        }
        MessageResponse messageResponse = paramVerify(query, platformCode);
        if (!messageResponse.isStauts()) {
            return new ResponseEnvelope(false, messageResponse.getMessage());
        }
        // 获取企业信息
        BaseCompany baseCompany = null;
        if (query.getCompanyId() != null) {
            baseCompany = baseCompanyService.get(query.getCompanyId());
        }
        // 获取推荐列表数据
        logger.info(CLASS_LOG_PREFIX + "获取方案数量参数->query:{}", query.toString());
        Integer count = designPlanRecommendedService.recommendedPlanCount(query, companyCode, baseCompany);

        return new ResponseEnvelope(true, count);
    }

    /**
     * 1、登录用户校验
     * 2、平台ID获取
     * @param query
     * @param platformCode
     * @return
     */
    private MessageResponse paramVerify(PlanRecommendedQuery query, String platformCode) {
        // 获取用户登录信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new MessageResponse(false,CLASS_LOG_PREFIX + "paramVerify：{} 请登录!");
        } else {
            query.setLoginUser(loginUser);
        }

        // 获取当前平台
        BasePlatform basePlatform = basePlatformService.getBasePlatform(platformCode);
        if (basePlatform == null) {
            return new MessageResponse(false, "未知的平台");
        }
        query.setPlatformId(basePlatform.getId());

        return new MessageResponse(true);
    }

}
