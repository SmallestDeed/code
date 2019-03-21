package com.sandu.web.companyshop.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.companyshop.input.ShopidentificationAdd;
import com.sandu.api.companyshop.input.ShopidentificationQuery;
import com.sandu.api.companyshop.input.ShopidentificationUpdate;
import com.sandu.api.companyshop.model.Shopidentification;
import com.sandu.api.companyshop.output.ShopidentificationVO;
import com.sandu.api.companyshop.service.biz.ShopidentificationBizService;
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

import javax.validation.Valid;
import java.util.List;

import static com.sandu.constant.ResponseEnum.*;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-22 11:50
 */
@Api(value = "Shopidentification", tags = "shopidentification", description = "Shopidentification")
@RestController
@RequestMapping(value = "/v1/shopidentification")
@Slf4j
public class ShopidentificationController extends BaseController {

    @Autowired
    private ShopidentificationBizService shopidentificationBizService;

    @ApiOperation(value = "新建shopidentification", response = ReturnData.class)
    @PostMapping
    public ReturnData createShopidentification(@Valid @RequestBody ShopidentificationAdd input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        int shopidentificationId = shopidentificationBizService.create(input);
        if (shopidentificationId > 0) {
            return ReturnData.builder().code(SUCCESS).data(shopidentificationId);
        }

        return ReturnData.builder().code(ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "编辑shopidentification", response = ReturnData.class)
    @PutMapping
    public ReturnData updateShopidentification(@Valid @RequestBody ShopidentificationUpdate input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        int result = shopidentificationBizService.update(input);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "删除shopidentification", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteShopidentification(String shopidentificationId) {
        ReturnData data = ReturnData.builder();

        int result = shopidentificationBizService.delete(shopidentificationId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).message("不存在");
    }

    @ApiOperation(value = "根据ShopId获取详情", response = ShopidentificationVO.class)
    @GetMapping(value = "/getInfoByShopId/{shopId}")
    public ReturnData getInfoByShopId(@PathVariable int shopId) {
        if (shopId <= 0) {
            return ReturnData.builder().code(ERROR).message("店铺Id无效");
        }

        Shopidentification shopidentification = shopidentificationBizService.getByShopId(shopId);
        if (shopidentification == null) {
            return ReturnData.builder().code(NOT_CONTENT).message("shopidentification不存在");
        }

        ShopidentificationVO output = new ShopidentificationVO();
        BeanUtils.copyProperties(shopidentification, output);
        //原字段ID转模块ID
//        output.setShopidentificationId(shopidentification.getId());

        return ReturnData.builder().code(SUCCESS).data(output);
    }

    @ApiOperation(value = "获取shopidentification详情", response = ShopidentificationVO.class)
    @GetMapping(value = "/{shopidentificationId}")
    public ReturnData getByShopidentificationId(@PathVariable int shopidentificationId) {
        ReturnData data = ReturnData.builder();
        if (shopidentificationId <= 0) {
            return ReturnData.builder().code(ERROR).message("ID无效");
        }

        Shopidentification shopidentification = shopidentificationBizService.getById(shopidentificationId);
        if (shopidentification == null) {
            return ReturnData.builder().code(NOT_CONTENT).message("shopidentification不存在");
        }

        ShopidentificationVO output = new ShopidentificationVO();
        BeanUtils.copyProperties(shopidentification, output);
        //原字段ID转模块ID
//        output.setShopidentificationId(shopidentification.getId());

        return ReturnData.builder().code(SUCCESS).data(output);
    }

    @ApiOperation(value = "查询shopidentification列表", response = ShopidentificationVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryShopidentificationList(@Valid ShopidentificationQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        final PageInfo<Shopidentification> results = shopidentificationBizService.query(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<ShopidentificationVO> shopidentifications = Lists.newArrayList();
            results.getList().stream().forEach(shopidentification -> {
                ShopidentificationVO output = new ShopidentificationVO();
                BeanUtils.copyProperties(shopidentification, output);
                //原字段ID转模块ID
//                output.setShopidentificationId(shopidentification.getId());

                shopidentifications.add(output);
            });

            return ReturnData.builder().code(SUCCESS).list(shopidentifications).total(results.getTotal());
        }

        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }


}
