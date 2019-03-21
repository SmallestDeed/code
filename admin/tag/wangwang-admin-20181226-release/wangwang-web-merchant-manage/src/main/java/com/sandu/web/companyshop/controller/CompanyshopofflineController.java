package com.sandu.web.companyshop.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.companyshop.input.CompanyShopOfflineClaim;
import com.sandu.api.companyshop.input.CompanyshopofflineAdd;
import com.sandu.api.companyshop.input.CompanyshopofflineQuery;
import com.sandu.api.companyshop.input.CompanyshopofflineUpdate;
import com.sandu.api.companyshop.model.Companyshopoffline;
import com.sandu.api.companyshop.output.CompanyshopofflineVO;
import com.sandu.api.companyshop.service.biz.CompanyshopofflineBizService;
import com.sandu.api.shop.service.CompanyShopService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
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
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 13:54
 * @since 1.8
 */
@Api(value = "Companyshopoffline", tags = "companyshopoffline", description = "Companyshopoffline")
@RestController
@RequestMapping(value = "/v1/companyshopoffline")
@Slf4j
public class CompanyshopofflineController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CompanyshopofflineBizService companyshopofflineBizService;

    @Autowired
    private CompanyShopService companyShopService;

    @RequiresPermissions({"shopOffline:dealer:view"})
    @GetMapping("/business/list")
    public ReturnData listShopOffline(@Valid CompanyshopofflineQuery query, BindingResult validResult) {
        query.setPage(query.getPage() == null ? 0 : query.getPage());
        query.setLimit(query.getLimit() == null ? 10 : query.getLimit());
//
//        PageInfo<Companyshopoffline> pageInfo = companyshopofflineBizService.listShopOffline(query);
//        if (pageInfo == null || pageInfo.getTotal() <= 0) {
//            return ReturnData.builder().success(false).code(NOT_CONTENT).message(NOT_CONTENT.getRemark());
//        }
//
//        List<CompanyshopofflineVO> listVO = new ArrayList<>();
//        pageInfo.getList().forEach(shopOffline -> {
//            CompanyshopofflineVO vo = new CompanyshopofflineVO();
//            BeanUtils.copyProperties(shopOffline, vo);
//
//            listVO.add(vo);
//        });
//
//        return ReturnData.builder().code(SUCCESS).data(listVO).total(pageInfo.getTotal());
        // 未认领的店铺
        query.setClaimStatus(Companyshopoffline.ClaimStatus.UNCLAIMED.getStatus());

        // 厂商ID
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return ReturnData.builder().code(UNAUTHORIZED).success(false).message(UNAUTHORIZED.getRemark());
        }
        SysUser sysUser = sysUserService.getUserById(loginUser.getId().longValue());
        if (sysUser == null || sysUser.getCompanyId() == null) {
            log.error("用户 userId => {} 没有关联厂商信息", loginUser.getId());
            return ReturnData.builder().code(ERROR).success(false).message(ERROR.getRemark());
        }
        query.setCompanyId(sysUser.getCompanyId().intValue());

        return this.queryCompanyshopofflineList(query, validResult);
    }

    @RequiresPermissions({"shopOffline:dealer:view"})
    @GetMapping("/get/{shopOfflineId}")
    public ReturnData getDetail(@PathVariable Integer shopOfflineId) {
        return getByCompanyshopofflineId(shopOfflineId);
    }

    @RequiresPermissions({"shopOffline:dealer:receive"})
    @PostMapping("/claimShop/{shopOfflineId}")
    public ReturnData claimShop(@PathVariable Long shopOfflineId) {
        if (shopOfflineId == null || shopOfflineId <= 0) {
            log.error("参数异常：shopOfflineId => {} 不能为空", shopOfflineId);
            return ReturnData.builder().code(PARAM_ERROR).success(false).message("参数异常");
        }

        CompanyShopOfflineClaim claim = new CompanyShopOfflineClaim();
        claim.setShopOfflineId(shopOfflineId);

        // 认领人的信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return ReturnData.builder().code(UNAUTHORIZED).success(false).message(UNAUTHORIZED.getRemark());
        }
        claim.setUserId(loginUser.getId());
        claim.setName(loginUser.getName());
        claim.setLoginId(loginUser.getLoginId());
        claim.setLoginName(loginUser.getLoginName());
        claim.setLoginPhone(loginUser.getLoginPhone());


        return companyshopofflineBizService.claimShop(claim);

//        Integer updateCount = companyshopofflineBizService.claimShop(claim);
//        if (updateCount > 0) {
//            return ReturnData.builder().code(SUCCESS).success(true).message("认领成功");
//        }
//
//        return ReturnData.builder().code(ERROR).message("系统错误，请稍后再试！");
    }

    @RequiresPermissions({"shopOffline:add"})
    @ApiOperation(value = "新建companyshopoffline", response = ReturnData.class)
    @PostMapping
    public ReturnData createCompanyshopoffline(@Valid @RequestBody CompanyshopofflineAdd input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        Companyshopoffline companyshopoffline = companyshopofflineBizService.checkShopName(input.getShopName(), input.getCompanyId());
        if (companyshopoffline != null) {
            return ReturnData.builder().code(ERROR).message("该门店名已存在,请重新输入");
        }
        Integer count = companyShopService.checkShopName(input.getShopName(), null);
        if (count != null && 0 < count.intValue()) {
            return ReturnData.builder().code(ERROR).message("该门店名已存在,请重新输入");
        }

        int companyshopofflineId = companyshopofflineBizService.create(input);
        if (companyshopofflineId > 0) {
            return ReturnData.builder().code(SUCCESS).data(companyshopofflineId);
        }

        return ReturnData.builder().code(ERROR).message("输入数据有误");
    }

    @RequiresPermissions({"shopOffline:edit"})
    @ApiOperation(value = "编辑companyshopoffline", response = ReturnData.class)
    @PutMapping
    public ReturnData updateCompanyshopoffline(@Valid @RequestBody CompanyshopofflineUpdate input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        Companyshopoffline companyshopoffline = companyshopofflineBizService.checkShopName(input.getShopName(), input.getCompanyId());
        if (companyshopoffline != null && !companyshopoffline.getId().equals(input.getId())
                && companyshopoffline.getShopName().equals(input.getShopName())) {
            return ReturnData.builder().code(ERROR).message("该门店名已存在,请重新输入");
        }
        Integer count = companyShopService.checkShopName(input.getShopName(), null);
        if (count != null && 0 < count.intValue()) {
            return ReturnData.builder().code(ERROR).message("该门店名已存在,请重新输入");
        }

        int result = companyshopofflineBizService.update(input);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).message("输入数据有误");
    }

    @RequiresPermissions({"shopOffline:delete"})
    @ApiOperation(value = "删除companyshopoffline", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteCompanyshopoffline(String companyshopofflineId) {
        ReturnData data = ReturnData.builder();

        int result = companyshopofflineBizService.delete(companyshopofflineId);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).message("不存在");
    }

    @RequiresPermissions({"shopOffline:view"})
    @ApiOperation(value = "获取companyshopoffline详情", response = CompanyshopofflineVO.class)
    @GetMapping(value = "/{companyshopofflineId}")
    public ReturnData getByCompanyshopofflineId(@PathVariable int companyshopofflineId) {
        ReturnData data = ReturnData.builder();
        if (companyshopofflineId <= 0) {
            return ReturnData.builder().code(ERROR).message("id无效");
        }

        Companyshopoffline companyshopoffline = companyshopofflineBizService.getById(companyshopofflineId);
        if (companyshopoffline == null) {
            return ReturnData.builder().code(NOT_CONTENT).message("companyshopoffline不存在");
        }

        CompanyshopofflineVO output = new CompanyshopofflineVO();
        BeanUtils.copyProperties(companyshopoffline, output);
        //原字段ID转模块ID
//        output.setCompanyshopofflineId(companyshopoffline.getId());

        return ReturnData.builder().code(SUCCESS).data(output);
    }

    @RequiresPermissions({"shopOffline:view"})
    @ApiOperation(value = "查询companyshopoffline列表", response = CompanyshopofflineVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryCompanyshopofflineList(@Valid CompanyshopofflineQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        final PageInfo<Companyshopoffline> results = companyshopofflineBizService.query(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            final List<CompanyshopofflineVO> companyshopofflines = Lists.newArrayList();
            results.getList().stream().forEach(companyshopoffline -> {
                CompanyshopofflineVO output = new CompanyshopofflineVO();
                BeanUtils.copyProperties(companyshopoffline, output);
                //原字段ID转模块ID
//                output.setCompanyshopofflineId(companyshopoffline.getId());

                companyshopofflines.add(output);
            });

            return ReturnData.builder().code(SUCCESS).list(companyshopofflines).total(results.getTotal());
        }

        return ReturnData.builder().code(NOT_CONTENT).message("暂无数据");
    }

    @RequiresPermissions({"shopOffline:release"})
    @ApiOperation(value = "发布", response = ReturnData.class)
    @PutMapping("/toRelease")
    public ReturnData companyshopofflineToRelease(String id, String isRelease) {
        ReturnData data = ReturnData.builder();
        int result = companyshopofflineBizService.toRelease(id, isRelease);
        if (result > 0) {
            return ReturnData.builder().code(SUCCESS).data(result);
        }

        return ReturnData.builder().code(ERROR).data(result);
    }
}
