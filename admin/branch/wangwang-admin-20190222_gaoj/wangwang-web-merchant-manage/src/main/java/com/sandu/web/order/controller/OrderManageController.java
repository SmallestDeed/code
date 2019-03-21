package com.sandu.web.order.controller;

import com.github.pagehelper.PageInfo;
import com.sandu.api.order.input.OrderManageAdd;
import com.sandu.api.order.input.OrderManageQuery;
import com.sandu.api.order.input.OrderManageUpdate;
import com.sandu.api.order.model.OrderManage;
import com.sandu.api.order.service.OrderManageService;
import com.sandu.api.user.model.User;
import com.sandu.api.user.service.UserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "OrderManage", tags = "orderManage", description = "orderManage")
@RestController
@RequestMapping(value = "/v1/orderManage")
@Slf4j
public class OrderManageController extends BaseController {

    @Autowired
    private OrderManageService orderManageService;

    @Autowired
    private UserService userService;

    @RequiresPermissions({"operation:orderManage:add"})
    @ApiOperation(value = "新建orderManage", response = ReturnData.class)
    @PostMapping
    public ReturnData createOrderManage(@Valid @RequestBody OrderManageAdd input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        int result = orderManageService.addOrder(input);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @RequiresPermissions({"operation:orderManage:edit"})
    @ApiOperation(value = "编辑orderManage", response = ReturnData.class)
    @PutMapping
    public ReturnData updateOrderManage(@Valid @RequestBody OrderManageUpdate input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        int result = orderManageService.update(input);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }

    @RequiresPermissions({"operation:orderManage:view"})
    @ApiOperation(value = "查询orderManage列表", response = OrderManage.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryOrderManageList(@Valid OrderManageQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        PageInfo<OrderManage> results = orderManageService.queryAll(query);
        log.debug("Result: {}", results);
        if (results != null) {
            return ReturnData.builder().code(SUCCESS).data(results.getList()).list(results.getList()).total(results.getTotal());
        }
        return ReturnData.builder().code(SUCCESS).data(NOT_CONTENT).message("没有数据了");
    }

    @RequiresPermissions({"operation:orderManage:view"})
    @ApiOperation(value = "获取OrderManage详情", response = OrderManage.class)
    @GetMapping(value = "/{orderManageId}")
    public ReturnData getOrderManageById(@PathVariable int orderManageId) {
        ReturnData data = ReturnData.builder();
        if (orderManageId <= 0) {
            return ReturnData.builder().code(PARAM_ERROR).message("ID无效");
        }

        OrderManage orderManage = orderManageService.getById(orderManageId);
        if (orderManage == null) {
            return ReturnData.builder().code(SUCCESS).data(NOT_CONTENT).message("不存在");
        }
        return ReturnData.builder().code(SUCCESS).data(orderManage);
    }

    @RequiresPermissions({"operation:orderManage:delete"})
    @ApiOperation(value = "删除orderManage", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteDynamic(Integer orderManageId) {
        ReturnData data = ReturnData.builder();
        int result = orderManageService.deleteOrderById(orderManageId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(SUCCESS).data(result);
    }

    @ApiOperation(value = "查询分类列表", response = User.class)
    @GetMapping(value = "/getCategoryList")
    public ReturnData getCategoryList() {
        ReturnData data = ReturnData.builder();

        List<User> results = userService.queryIntermediary();
        log.debug("Result: {}", results);
        if (results != null && results.size() > 0) {
            return ReturnData.builder().code(SUCCESS).data(results);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }

}
