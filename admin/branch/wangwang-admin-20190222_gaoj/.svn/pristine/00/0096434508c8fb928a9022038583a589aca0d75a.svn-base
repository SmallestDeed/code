package com.sandu.web.proprietorinfo.controller;

import com.sandu.api.proprietorinfo.input.ProprietorInfoListQuery;
import com.sandu.api.proprietorinfo.input.ProprietorInfoUpdate;
import com.sandu.api.proprietorinfo.output.CustomerTypeVo;
import com.sandu.api.proprietorinfo.output.ProprietorInfoDetailVo;
import com.sandu.api.proprietorinfo.output.ProprietorInfoEditVo;
import com.sandu.api.proprietorinfo.output.ProvinceVo;
import com.sandu.api.proprietorinfo.service.biz.ProprietorInfoBizService;
import com.sandu.api.user.model.SysUser;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
import com.sandu.common.ReturnData;
import com.sandu.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(description = "业主信息管理", tags = "proprietorInfo")
@RestController
@RequestMapping("/v1/proprietor/info")
@Slf4j
public class ProprietorInfoController extends BaseController {
    private final ProprietorInfoBizService proprietorInfoBizService;


    @Autowired
    public ProprietorInfoController(ProprietorInfoBizService proprietorInfoBizService) {
        this.proprietorInfoBizService = proprietorInfoBizService;
    }

    @RequiresPermissions({"decorate:ownerInfo:view"})
    @RequestMapping("/list")
    @ApiOperation(value = "查询业主基础信息列表")
    public ReturnData getList(@Validated @RequestBody ProprietorInfoListQuery proprietorInfoListQuery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return processValidError(bindingResult, ReturnData.builder());
        }
        Integer total = proprietorInfoBizService.getVoListCount(proprietorInfoListQuery);
        if (total == null || total <= 0){
            return ReturnData.builder().success(false).message("查询业主基础信息结果为空");
        }
        List<ProprietorInfoDetailVo> list = proprietorInfoBizService.getVoList(proprietorInfoListQuery);
        return ReturnData.builder().success(true).list(list).message("查询业主基础信息成功").total(total);
    }

    @RequiresPermissions({"decorate:ownerInfo:view"})
    @GetMapping("/info")
    @ApiOperation(value = "查询业主基础信息详情")
    public ReturnData getInfo(Integer id) {
        if (id == null || id <= 0) {
            return ReturnData.builder().success(false).message("参数不正确");
        }
        ProprietorInfoEditVo vo = proprietorInfoBizService.getVoById(id);
        if (vo == null) {
            return ReturnData.builder().success(false).message("查询业主基础信息详情结果为空");
        }
        return ReturnData.builder().success(true).data(vo).message("查询成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除业主")
    public ReturnData delete(@PathVariable("id") Integer id) {
        Integer count = proprietorInfoBizService.deleteByIds(Collections.singletonList(id));
        return ReturnData.builder().success(count > 0).message("操作成功");
    }

    @RequiresPermissions({"decorate:ownerInfo:edit"})
    @PostMapping("/update")
    @ApiOperation(value = "更新业主基础信息")
    public ReturnData update(@Validated @RequestBody ProprietorInfoUpdate proprietorInfoUpdate, BindingResult bindingResult) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null){
            return ReturnData.builder().success(false).message("用户未登录");
        }
        if (bindingResult.hasErrors()) {
            return processValidError(bindingResult, ReturnData.builder());
        }
        if (proprietorInfoUpdate.getCustomerTypeValue() != null){
            if (proprietorInfoUpdate.getCustomerTypeValue() == 1 || proprietorInfoUpdate.getCustomerTypeValue() == 2){
                StringBuilder sb = new StringBuilder();
                boolean exit = false;
                if (StringUtils.isBlank(proprietorInfoUpdate.getHouseAcreage())) {
                    sb.append("1、2类客户装修面积不能为空；");
                    exit = true;
                }
                if (StringUtils.isBlank(proprietorInfoUpdate.getHouseEstate())){
                    sb.append("1、2类客户小区名称不能为空；");
                    exit = true;
                }
                if (StringUtils.isBlank(proprietorInfoUpdate.getAddress())){
                    sb.append("1、2类客户详细地址不能为空；");
                    exit = true;
                }
                if (proprietorInfoUpdate.getBedroomNum() == null ||
                        proprietorInfoUpdate.getLivingRoomNum() == null ||
                        proprietorInfoUpdate.getToiletNum() == null){
                    sb.append("1、2类客户户型信息不完整；");
                    exit = true;
                }
                if (proprietorInfoUpdate.getDecorateTypeValue() == null){
                    sb.append("1、2类客户装修方式不能为空；");
                    exit = true;
                }
                if (proprietorInfoUpdate.getDecorateBudgetValue() == null){
                    sb.append("1、2类客户装修预算不能为空；");
                    exit = true;
                }
                if (proprietorInfoUpdate.getGoodStyleValue() == null){
                    sb.append("1、2类客户倾向风格不能为空；");
                    exit = true;
                }
                if (proprietorInfoUpdate.getDecorateHouseTypeKey() == null){
                    sb.append("1、2类客户装修类型不能为空；");
                    exit = true;
                }
                if (exit){
                    return ReturnData.builder().success(false).message(sb.toString());
                }
            }
        }

        SysUser user = proprietorInfoBizService.getSysUserById(loginUser.getId());
        if (user == null){
            return ReturnData.builder().success(false).message("找不到用户信息");
        }
        proprietorInfoUpdate.setModifier(user.getUserName());
        proprietorInfoUpdate.setGmtModified(new Date());
        int successNum = proprietorInfoBizService.updateProprietorInfo(proprietorInfoUpdate);
        if (successNum == 0) {
            return ReturnData.builder().success(false).message("更新失败");
        }
        return ReturnData.builder().success(true).message("更新成功");
    }

    @GetMapping("/getRequirementType")
    @ApiOperation(value = "获取需求类型")
    public ReturnData getRequirementType() {
        // 类型(0:0元设计;1:装修报价;3-店铺预约)
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> mapOne = new HashMap<>(2);
        Map<String, String> mapTwo = new HashMap<>(2);
        Map<String, String> mapThree = new HashMap<>(2);

        mapOne.put("type", "0");
        mapOne.put("typeName", "0元设计");
        list.add(mapOne);
        mapTwo.put("type", "1");
        mapTwo.put("typeName", "装修报价");
        list.add(mapTwo);
        mapThree.put("type", "3");
        mapThree.put("typeName", "店铺预约");
        list.add(mapThree);

        return ReturnData.builder().success(true).list(list);
    }

    @GetMapping("/getCityList")
    @ApiOperation(value = "获取省市列表")
    public ReturnData getCityList() {
        List<ProvinceVo> list = proprietorInfoBizService.getProvinceVoList();
        if (list == null || list.size() <= 0) {
            return ReturnData.builder().success(false).message("查询省市列表失败");
        }
        return ReturnData.builder().success(true).list(list).message("查询省市列表成功");
    }

    @GetMapping("/getCustomerType")
    @ApiOperation(value = "获取用户类型列表")
    public ReturnData getCustomerType() {
        List<CustomerTypeVo> list = proprietorInfoBizService.getCustomerTypeList();
        if (list == null || list.size() <= 0) {
            return ReturnData.builder().success(false).message("查询用户类型列表失败");
        }
        return ReturnData.builder().success(true).list(list).message("查询用户类型列表成功");
    }
}
