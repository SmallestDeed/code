package com.sandu.web.servicepurchase.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.input.SysDictionaryQuery;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.servicepurchase.input.ServicesBaseInfoAdd;
import com.sandu.api.servicepurchase.input.ServicesBaseInfoUpdate;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.bo.ServicesFuncBO;
import com.sandu.api.servicepurchase.output.ServicesInfoVO;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import com.sandu.api.servicepurchase.serivce.ServicesBaseInfoService;
import com.sandu.api.servicepurchase.serivce.ServicesPriceService;
import com.sandu.api.servicepurchase.serivce.ServicesRoleRelService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: 2019/1/2
 */

@Slf4j
@RestController
@Api(tags = "setMeal", description = "套餐")
@RequestMapping("/inner/setMeal")
public class InnerMealController extends BaseController {

    @Autowired
    private ServicesBaseInfoService servicesBaseInfoService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private ServicesPriceService servicesPriceService;

    @Autowired
    private ServicesRoleRelService servicesRoleRelService;

    @RequiresPermissions({"service.manage.view"})
    @ApiOperation(value = "获取套餐列表", response = ServicesInfoVO.class)
    @GetMapping("/list")
    public ResponseEnvelope chooseServicesList(@Valid ServicesBaseInfoQuery query, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        PageInfo<ServicesBaseInfo> results = servicesBaseInfoService.queryAll(query);
        log.debug("Result: {}", results);
        if (results != null && results.getTotal() > 0) {
            List<ServicesInfoVO> servicesInfoVOS = Lists.newArrayList();
            results.getList().stream().forEach(result -> {
                ServicesInfoVO vo = new ServicesInfoVO();
                BeanUtils.copyProperties(result, vo);
                servicesInfoVOS.add(vo);
            });
            return new ResponseEnvelope<>(true, results.getTotal(), servicesInfoVOS);
        }
        return new ResponseEnvelope<>(false, "暂无数据！");
    }

    @ApiOperation(value = "根据类型获取数据字典信息(type:userType,saleChannel,brandBusinessType)", response = DictionaryTypeListVO.class)
    @GetMapping(value = "/getDictionaryInfo")
    public ResponseEnvelope getDictionaryInfo(SysDictionaryQuery query) {
        if (Strings.isNullOrEmpty(query.getType())) {
            return new ResponseEnvelope<>(false, "输入类型有误");
        }
        log.info("#####查询信息：{}", query);
        List<SysDictionary> dictionaryList = dictionaryService.queryByOption(query);
        List<DictionaryTypeListVO> results = Lists.newArrayList();
        if (dictionaryList != null && dictionaryList.size() > 0) {
            dictionaryList.forEach(result -> {
                DictionaryTypeListVO output = new DictionaryTypeListVO();
                BeanUtils.copyProperties(result, output);
                results.add(output);
            });
            return new ResponseEnvelope<>(true, results);
        }
        return new ResponseEnvelope<>(false, "不存在该数据");
    }

    @ApiOperation(value = "获取企业列表", response = BaseCompany.class)
    @GetMapping(value = "/getCompanyList")
    public ResponseEnvelope getCompanyList() {


        List<BaseCompany> baseCompanyList = baseCompanyService.querySetMealCompany();
        if (baseCompanyList != null && baseCompanyList.size() > 0) {
            return new ResponseEnvelope<>(true, baseCompanyList);
        }
        return new ResponseEnvelope<>(false, "暂无数据");
    }

    @RequiresPermissions({"service.manage.add"})
    @ApiOperation(value = "新建套餐", response = ResponseEnvelope.class)
    @PutMapping("/add")
    public ResponseEnvelope createSetMeal(@Valid @RequestBody ServicesBaseInfoAdd input,BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }

        //新增套餐
        try {
            return servicesBaseInfoService.saveServices(input);
        } catch (RuntimeException e) {
            return new ResponseEnvelope(false, e.getMessage());
        }
    }

    @RequiresPermissions({"service.manage.edit"})
    @ApiOperation(value = "修改套餐", response = ResponseEnvelope.class)
    @PutMapping("/update")
    public ResponseEnvelope updateSetMeal(@Valid @RequestBody ServicesBaseInfoUpdate input,BindingResult validResult) {

        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        //修改套餐
        try {
            return servicesBaseInfoService.updateServices(input);
        } catch (RuntimeException e) {
            return new ResponseEnvelope(false, e.getMessage());
        }
    }

    @RequiresPermissions({"service.manage.view"})
    @ApiOperation(value = "获取套餐详情", response = ServicesInfoVO.class)
    @GetMapping(value = "/getServiceDetail")
    public ResponseEnvelope getServiceDetail(int id) {
        if (id <= 0) {
            return new ResponseEnvelope<>(false, "输入id有误");
        }

        ServicesBaseInfo result = servicesBaseInfoService.getServicesById(id);

        if (result == null) {
            return new ResponseEnvelope<>(false, "不存在该数据");
        }

        ServicesInfoVO output = new ServicesInfoVO();
        BeanUtils.copyProperties(result, output);

        return new ResponseEnvelope<>(true, output);
    }

    @RequiresPermissions({"service.manage.del"})
    @ApiOperation(value = "删除套餐", response = ResponseEnvelope.class)
    @DeleteMapping("/delete")
    public ResponseEnvelope deleteSetMeal(String id, String creator) {

        int result = servicesBaseInfoService.deleteSetMeal(id, creator);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }

        return new ResponseEnvelope<>(false, "失败!");
    }

    @RequiresPermissions({"service.manage.edit"})
    @ApiOperation(value = "修改套餐状态", response = ResponseEnvelope.class)
    @PutMapping("/changeSetMealStatus")
    public ResponseEnvelope changeSetMealStatus(String id, String servicesStatus) {
        //修改状态
        int result = servicesBaseInfoService.openOrCloseServices(id, servicesStatus);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }
        return new ResponseEnvelope<>(false, "失败!");
    }

    @RequiresPermissions({"service.pricesetting.add"})
    @ApiOperation(value = "新建套餐(企业价格)", response = ResponseEnvelope.class)
    @PutMapping("/addCompanyPrice")
    public ResponseEnvelope createSetMealForCompany(@Valid @RequestBody ServicesBaseInfoUpdate input) {

        try {
            return servicesBaseInfoService.addCompanyPrice(input);
        } catch (RuntimeException e) {
            return new ResponseEnvelope(false, e.getMessage());
        }

    }

    @RequiresPermissions({"service.pricesetting.update"})
    @ApiOperation(value = "修改套餐(企业价格)", response = ResponseEnvelope.class)
    @PutMapping("/updateCompanyPrice")
    public ResponseEnvelope updateSetMealForCompany(@Valid @RequestBody ServicesBaseInfoUpdate input) {

        try {
            return servicesBaseInfoService.updateCompanyPrice(input);
        } catch (RuntimeException e) {
            return new ResponseEnvelope(false,e.getMessage());
        }
    }

    @RequiresPermissions({"service.pricesetting.view"})
    @ApiOperation(value = "获取套餐列表(企业价格)", response = ServicesInfoVO.class)
    @GetMapping(value = "/getCompanyPriceList")
    public ResponseEnvelope getCompanyPriceList(ServicesBaseInfoQuery query) {
        Integer totalCount = servicesPriceService.getCount(query);
        Integer totalPages = totalCount % query.getLimit() == 0 ? totalCount / query.getLimit() : totalCount / query.getLimit() + 1;
        if (query.getStart() == null) {
            query.setStart(1);
        }
        if (query.getStart() > totalPages) {
            query.setStart(totalPages);
        }
        if (query.getStart() < 1) {
            query.setStart(1);
        }
        List<ServicesPriceVO> servicesPriceVOs = servicesBaseInfoService.queryServicesPriceInfo(query);
        if(servicesPriceVOs != null && servicesPriceVOs.size()>0){
            return new ResponseEnvelope<>(true, totalCount, servicesPriceVOs);
        }
        return  new ResponseEnvelope<>(false,"暂无数据");
    }

    @RequiresPermissions({"service.pricesetting.del"})
    @ApiOperation(value = "删除套餐(企业价格)", response = ResponseEnvelope.class)
    @DeleteMapping("/deleteCompanyPrice")
    public ResponseEnvelope deleteCompanyPrice(Integer companyId, Integer servicesId) {

        int result = servicesBaseInfoService.deleteCompanyPrice(companyId, servicesId);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }
        return new ResponseEnvelope<>(false, "失败!");
    }

    @RequiresPermissions({"service.pricesetting.view"})
    @ApiOperation(value = "获取套餐详情(企业价格)", response = ServicesInfoVO.class)
    @GetMapping(value = "/getCompanyPriceDetail")
    public ResponseEnvelope getCompanyPriceDetail(Integer companyId, Integer servicesId) {
        ServicesBaseInfo result = servicesBaseInfoService.getCompanyPriceDetail(companyId, servicesId);

        if (result == null) {
            return new ResponseEnvelope<>(false, "不存在该数据");
        }

        ServicesInfoVO output = new ServicesInfoVO();
        BeanUtils.copyProperties(result, output);

        return new ResponseEnvelope<>(true, output);
    }

    @RequiresPermissions({"service.manage.view"})
    @ApiOperation(value = "关联功能列表", response = ServicesFuncBO.class)
    @GetMapping(value = "/getFunctionList")
    public ResponseEnvelope getFunctionList(Integer servicesId) {

        List<ServicesFuncBO> results = servicesBaseInfoService.getFunctionList(servicesId);
        if (results != null && results.size() > 0) {
            return new ResponseEnvelope<>(true, results);
        }
        return new ResponseEnvelope<>(false, "暂无数据");
    }

    @RequiresPermissions({"service.manage.role"})
    @ApiOperation(value = "保存关联功能(servicesId:套餐id、roleIds:关联角色id(type:字符串)、type:(0:添加，1:删除))", response = ResponseEnvelope.class)
    @PostMapping("/saveFunction")
    public ResponseEnvelope saveFunction(Integer servicesId, String roleIds, Integer type) {

        if (Strings.isNullOrEmpty(roleIds)) {
            return new ResponseEnvelope<>(false, "角色id不能为空!");
        }
        //新增角色
        if (type == 0) {
            if (servicesBaseInfoService.saveFunction(servicesId, roleIds) > 0) {
                return new ResponseEnvelope<>(true, "成功!");
            }
            return new ResponseEnvelope<>(false, "失败!");
        } else {
            if (servicesBaseInfoService.removeFunction(servicesId, roleIds) > 0) {
                return new ResponseEnvelope<>(true, "成功!");
            }
            return new ResponseEnvelope<>(false, "失败!");
        }
    }

}
