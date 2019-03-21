package com.sandu.web.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sandu.api.product.bo.GroupProductBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.house.input.GroupProductQuery;
import com.sandu.api.product.service.GroupProductService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.house.ProductPutawayState;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018年1月21日
 */

@Api(tags = "组合产品")
@RestController
@RequestMapping("/v1/groupProduct")
public class GroupProductController extends BaseController {

    private static final Integer UNLIMIT = -1;

    @Autowired
    private GroupProductService groupProductService;

    @ApiOperation(value = "获取组的白膜产品列表", response = GroupProductBO.class)
    @PostMapping("/list")
    public ReturnData<?> listGroupProduct(HttpServletRequest request, GroupProductQuery query) {
        ReturnData response = setMsgId(request);

        // if (query.getPageSize() != UNLIMIT) {
        // query.setPageSize(super.getLimit(query.getPageSize()));
        // query.setPageNum(super.getPage(query.getPageNum(), query.getPageSize()));
        // }
        query.setPutawayState(new Integer[]{ProductPutawayState.GroupProduct.SHELVE.getValue(), ProductPutawayState.GroupProduct.RELEASE.getValue()});

        // TODO 这个前端和产品的要求，我也...(^-^)```懵了 ?
        query.setPageNum(0);
        query.setPageSize(50);

        Map<String, Object> map = groupProductService.listGroupProduct(query, true);
        Object list = map.get("list");
        List<GroupProductBO> data = list == null ? new ArrayList<>() : (List<GroupProductBO>) list;

        return response.list(data);
    }
}
