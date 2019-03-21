package com.sandu.web.companyshop.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.companyshop.input.CompanyshopQuery;
import com.sandu.api.companyshop.model.Companyshop;
import com.sandu.api.companyshop.output.CompanyshopVO;
import com.sandu.api.companyshop.service.biz.CompanyshopBizService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sandu.constant.ResponseEnum.*;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * store_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-22 16:51
 */
@Api(value = "Companyshop", tags = "companyshop", description = "Companyshop")
@RestController
@RequestMapping(value = "/v1/companyshop")
@Slf4j
public class CompanyshopController extends BaseController {

    @Autowired
    private CompanyshopBizService companyshopBizService;

    @Autowired
    private DictionaryService dictionaryService;

    @RequiresPermissions({"operation:storedelete:delete"})
    @ApiOperation(value = "删除companyshop", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteCompanyshop(String companyshopId) {
        ReturnData data = ReturnData.builder();

        int result = companyshopBizService.delete(companyshopId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }
        return ReturnData.builder().code(ERROR).data(result);
    }

    @RequiresPermissions({"operation:storeManage:view"})
    @ApiOperation(value = "获取companyshop详情", response = CompanyshopVO.class)
    @GetMapping(value = "/{companyshopId}")
    public ReturnData getByCompanyshopId(@PathVariable int companyshopId) {
        ReturnData data = ReturnData.builder();
        if (companyshopId <= 0) {
            return ReturnData.builder().code(PARAM_ERROR).message("ID无效");
        }

        Companyshop companyshop = companyshopBizService.getById(companyshopId);
        if (companyshop == null) {
            return ReturnData.builder().code(SUCCESS).data(NOT_CONTENT).message("basesupplydemand不存在");
        }

        CompanyshopVO output = new CompanyshopVO();
        BeanUtils.copyProperties(companyshop, output);
        //原字段ID转模块ID
        output.setId(companyshop.getId());

        return ReturnData.builder().code(SUCCESS).data(output);
    }

    @RequiresPermissions({"operation:storeManage:view"})
    @ApiOperation(value = "查询companyshop列表", response = CompanyshopVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryCompanyshopList(@Valid CompanyshopQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        final PageInfo<Companyshop> results = companyshopBizService.query(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<CompanyshopVO> companyshops = Lists.newArrayList();
            results.getList().stream().forEach(companyshop -> {
                CompanyshopVO output = new CompanyshopVO();
                BeanUtils.copyProperties(companyshop, output);
                //原字段ID转模块ID
                output.setId(companyshop.getId());

                companyshops.add(output);
            });

            return ReturnData.builder().code(SUCCESS).data(companyshops).list(companyshops).total(results.getTotal());
        }

        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }


    @RequiresPermissions({"operation:storeManage:top"})
    @ApiOperation(value = "动态置顶", response = ReturnData.class)
    @PutMapping("/toTop")
    public ReturnData companyshopToTop(String companyshopId, String topId) {
        ReturnData data = ReturnData.builder();
        int result = companyshopBizService.companyshopToTop(companyshopId, topId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @ApiOperation(value = "刷新", response = ReturnData.class)
    @PutMapping("/toRefresh")
    public ReturnData companyshopToRefresh(String companyshopId, String topId) {
        ReturnData data = ReturnData.builder();
        int result = companyshopBizService.companyshopToRefresh(companyshopId, topId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @ApiOperation(value = "查询店铺类型", response = CompanyshopVO.class)
    @GetMapping(value = "/getShopList")
    public ReturnData getCategoryList() {
        ReturnData data = ReturnData.builder();

        List<Dictionary> results = dictionaryService.listByType("shopType");
        log.debug("Result: {}", results);
        if (results != null && results.size() > 0) {
            return ReturnData.builder().code(SUCCESS).data(results);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }


}
