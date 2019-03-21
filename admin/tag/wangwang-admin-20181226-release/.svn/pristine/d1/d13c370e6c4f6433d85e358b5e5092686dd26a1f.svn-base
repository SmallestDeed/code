package com.sandu.web.dynamic.controller;

import com.sandu.api.basesupplydemand.service.biz.BaseSupplydemandBizService;
import com.sandu.api.companyshop.service.biz.CompanyshopBizService;
import com.sandu.api.dynamic.input.DynamicQuery;
import com.sandu.api.dynamic.model.Dynamic;
import com.sandu.api.dynamic.service.DynamicService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * store_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-22 16:51
 */
@Api(value = "Dynamic", tags = "dynamic", description = "Dynamic")
@RestController
@RequestMapping(value = "/v1/dynamic")
@Slf4j
public class DynamicController extends BaseController {

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private CompanyshopBizService companyshopBizService;

    @Autowired
    private BaseSupplydemandBizService basesupplydemandBizService;

    @RequiresPermissions({"operation:dynamic:view"})
    @ApiOperation(value = "查询companyshop列表", response = Dynamic.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryCompanyshopList(@Valid DynamicQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        List<Dynamic> results = dynamicService.queryAll(query);
        log.debug("Result: {}", results);
        if (results != null) {
            return ReturnData.builder().code(SUCCESS).data(results).list(results).total(results.size());
        }
        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }

    @RequiresPermissions({"operation:dynamic:delete"})
    @ApiOperation(value = "删除dynamic", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteDynamic(String id, String type) {
        ReturnData data = ReturnData.builder();
        int result = 0;
        if(type.equals("供需")) {
            result = basesupplydemandBizService.delete(id);
        }
        if(type.equals("店铺")) {
            result = companyshopBizService.delete(id);
        }
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }
        return ReturnData.builder().code(ERROR).data(result);
    }

    @ApiOperation(value = "刷新", response = ReturnData.class)
    @PutMapping("/toRefresh")
    public ReturnData dynamicToRefresh(String id, String topId, String type) {
        ReturnData data = ReturnData.builder();
        int result = 0;
        if("供需".equals(type)) {
            result = basesupplydemandBizService.baseSupplyToRefresh(id,topId);
        }
        if("店铺".equals(type)) {
            result = companyshopBizService.companyshopToRefresh(id,topId);
        }
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @RequiresPermissions({"operation:dynamic:top"})
    @ApiOperation(value = "动态置顶", response = ReturnData.class)
    @PutMapping("/toTop")
    public ReturnData companyshopToTop(String id, String topId, String type) {
        ReturnData data = ReturnData.builder();
        int result = 0;
        if(type.equals("供需")) {
            result = basesupplydemandBizService.baseSupplyToTop(id,topId);
        }
        if(type.equals("店铺")) {
            result = companyshopBizService.companyshopToTop(id,topId);
        }
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }






}
