package com.sandu.web.basesupplydemand.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.basesupplydemand.input.BasesupplydemandQuery;
import com.sandu.api.basesupplydemand.input.BasesupplydemandUpdate;
import com.sandu.api.basesupplydemand.model.Basesupplydemand;
import com.sandu.api.basesupplydemand.model.SupplyDemandCategory;
import com.sandu.api.basesupplydemand.output.BasesupplydemandVO;
import com.sandu.api.basesupplydemand.output.SupplyCategoryListVO;
import com.sandu.api.basesupplydemand.service.SupplyDemandCategoryService;
import com.sandu.api.basesupplydemand.service.biz.BaseSupplydemandBizService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.sandu.constant.ResponseEnum.*;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */
@Api(value = "Basesupplydemand", tags = "basesupplydemand", description = "Basesupplydemand")
@RestController
@RequestMapping(value = "/v1/basesupplydemand")
@Slf4j
public class BasesupplydemandController extends BaseController {

    @Autowired
    private BaseSupplydemandBizService baseSupplydemandBizService;

    @Resource
    private SupplyDemandCategoryService supplyDemandCategoryService;


    @RequiresPermissions({"operation:basesupplydemand:edit"})
    @ApiOperation(value = "编辑basesupplydemand", response = ReturnData.class)
    @PutMapping
    public ReturnData updateBasesupplydemand(@Valid @RequestBody BasesupplydemandUpdate input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        int result = baseSupplydemandBizService.update(input);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }
        return ReturnData.builder().code(ERROR).data(result);
    }

    @RequiresPermissions({"operation:basesupplydemand:delete"})
    @ApiOperation(value = "删除basesupplydemand", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteBasesupplydemand(String basesupplydemandId) {
        ReturnData data = ReturnData.builder();

        int result = baseSupplydemandBizService.delete(basesupplydemandId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @RequiresPermissions({"operation:basesupplydemand:view"})
    @ApiOperation(value = "获取basesupplydemand详情", response = BasesupplydemandVO.class)
    @GetMapping(value = "/{basesupplydemandId}")
    public ReturnData getByBasesupplydemandId(@PathVariable int basesupplydemandId) {
        ReturnData data = ReturnData.builder();
        if (basesupplydemandId <= 0) {
            return ReturnData.builder().code(PARAM_ERROR).message("ID无效");
        }

        Basesupplydemand basesupplydemand = baseSupplydemandBizService.getById(basesupplydemandId);
        if (basesupplydemand == null) {
            return ReturnData.builder().code(SUCCESS).data(NOT_CONTENT).message("basesupplydemand不存在");
        }

        BasesupplydemandVO output = new BasesupplydemandVO();
        BeanUtils.copyProperties(basesupplydemand, output);
        //原字段ID转模块ID
        output.setId(basesupplydemand.getId());

        return ReturnData.builder().code(SUCCESS).data(output);
    }

    @RequiresPermissions({"operation:basesupplydemand:view"})
    @ApiOperation(value = "查询basesupplydemand列表", response = BasesupplydemandVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryBasesupplydemandList(@Valid BasesupplydemandQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        final PageInfo<Basesupplydemand> results = baseSupplydemandBizService.query(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<BasesupplydemandVO> basesupplydemands = Lists.newArrayList();
            results.getList().stream().forEach(basesupplydemand -> {
                BasesupplydemandVO output = new BasesupplydemandVO();
                BeanUtils.copyProperties(basesupplydemand, output);
                //原字段ID转模块ID
                output.setId(basesupplydemand.getId());

                basesupplydemands.add(output);
            });
            return ReturnData.builder().code(SUCCESS).data(basesupplydemands).list(basesupplydemands).total(results.getTotal());
        }

        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }

    @RequiresPermissions({"operation:basesupplydemand:top"})
    @ApiOperation(value = "动态置顶", response = ReturnData.class)
    @PutMapping("/toTop")
    public ReturnData baseSupplydemandToTop(String basesupplydemandId, String topId) {
        ReturnData data = ReturnData.builder();
        int result = baseSupplydemandBizService.baseSupplyToTop(basesupplydemandId, topId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @ApiOperation(value = "刷新", response = ReturnData.class)
    @PutMapping("/toRefresh")
    public ReturnData baseSupplyToRefresh(String basesupplydemandId, String topId) {
        ReturnData data = ReturnData.builder();
        int result = baseSupplydemandBizService.baseSupplyToRefresh(basesupplydemandId, topId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }


    @ApiOperation(value = "查询分类列表", response = BasesupplydemandVO.class)
    @GetMapping(value = "/getCategoryList")
    public ReturnData getCategoryList() {
        ReturnData data = ReturnData.builder();

        List<SupplyDemandCategory> results = supplyDemandCategoryService.querySupplyDemandCategory();
        log.debug("Result: {}", results);
        if (results != null && results.size() > 0) {
            return ReturnData.builder().code(SUCCESS).data(results);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }

    @ApiOperation(value = "查询级联分类列表", response = SupplyCategoryListVO.class)
    @GetMapping(value = "/queryAllCategoryList")
    public ReturnData queryAllCategoryList() {
        ReturnData data = ReturnData.builder();

        List<SupplyDemandCategory> results = supplyDemandCategoryService.querySupplyDemandCategory();
        List<SupplyCategoryListVO> vos = new ArrayList<>();
        for (SupplyDemandCategory result : results) {
            if (result.getLevel() == 1) {
                SupplyCategoryListVO vo = new SupplyCategoryListVO();
                vo.setValue(result.getId());
                vo.setLabel(result.getName());
                List<SupplyCategoryListVO> childs = new ArrayList<>();
                for (SupplyDemandCategory childResult : results) {
                    if (result.getId().equals(childResult.getPid())) {
                        SupplyCategoryListVO child = new SupplyCategoryListVO();
                        child.setLabel(childResult.getName());
                        child.setValue(childResult.getId());
                        childs.add(child);
                    }
                }
                vo.setChildren(childs);
                vos.add(vo);
            }
        }
        log.debug("Result: {}", vos);
        if (vos != null && vos.size() > 0) {
            return ReturnData.builder().code(SUCCESS).data(vos);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }


}
