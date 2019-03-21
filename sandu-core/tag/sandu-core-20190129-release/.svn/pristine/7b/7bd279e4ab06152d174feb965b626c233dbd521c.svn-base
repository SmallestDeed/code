package com.sandu.web.product.controller;

import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.model.BaseProductCategorySearch;
import com.sandu.api.base.model.BaseProductCategory;
import com.sandu.api.base.service.BaseProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/19
 * @since : sandu_yun_1.0
 */

@Log4j2(topic = "[ProductCategoryController]")
@RestController
@RequestMapping("/v1/core/productCategory")
@Api(tags = "ProductCategoryController")
public class ProductCategoryController {

    @Autowired
    private  BaseProductCategoryService baseProductCategoryService;

//    @ApiOperation(value = "Get the product category info.", response = ResponseEnvelope.class)
    @RequestMapping(value="/getProductCategory",method = RequestMethod.GET)
    public ResponseEnvelope getProductCategory(@RequestParam("currentLevel") Integer currentLevel,
                                               @RequestParam("currentId")Integer currentId,HttpServletRequest request) {
        String referStr = StringUtils.isNotEmpty(request.getHeader("Referer"))?request.getHeader("Referer"):
                "https://servicewechat.com/wx0d37f598e1028825/devtools/page-frame.html";
        BaseProductCategorySearch search = new BaseProductCategorySearch();
        search.setReferStr(referStr);
        search.setCurrentLevel(currentLevel);
        search.setCurrentId(currentId);
        List<BaseProductCategory> productCategoryList = baseProductCategoryService.getBaseProductCategoryList(search);
        return new ResponseEnvelope(true, productCategoryList);
    }

    @ApiOperation(value = "Get the fourth and fifth level category.", response = ResponseEnvelope.class)
    @GetMapping("/getProductCategoryFilters")
    public ResponseEnvelope getProductCategoryFilters(@RequestParam("currentLevel") Integer currentLevel,
                                                      @RequestParam("currentId")Integer currentId,HttpServletRequest request) {
        String referStr = StringUtils.isNotEmpty(request.getHeader("Referer"))?request.getHeader("Referer"):
                "https://servicewechat.com/wx0d37f598e1028825/devtools/page-frame.html";
        BaseProductCategorySearch search = new BaseProductCategorySearch();
        search.setReferStr(referStr);
        search.setCurrentLevel(currentLevel);
        search.setCurrentId(currentId);
        List<List<BaseProductCategory>> productCategoryList = baseProductCategoryService.getBaseProductCategoryFilterList(search);
        return new ResponseEnvelope(true, productCategoryList);
    }


    @ApiOperation(value = "Refresh the cache.", response = ResponseEnvelope.class)
    @GetMapping("/refreshCache")
    public ResponseEnvelope refreshCache() {
        baseProductCategoryService.refreshCache();
        return new ResponseEnvelope(true,"ok");
    }
}
