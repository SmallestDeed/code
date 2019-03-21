package com.sandu.web.companyshop.controller;

import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.PARAM_ERROR;
import static com.sandu.constant.ResponseEnum.SUCCESS;
import static com.sandu.constant.ResponseEnum.UNAUTHORIZED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.area.output.BaseAreaBo;
import com.sandu.api.area.service.BaseAreaService;
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
import com.sandu.constant.Punctuation;
import com.sandu.util.excel.CompanyShopImportBean;
import com.sandu.util.excel.ExcelUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
    
    @Resource
    private BaseAreaService baseAreaService;
    
    @Value("${file.storage.path}")
    private String basePath;

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
    
    @ApiOperation("excel批量导入线下门店")
    @PostMapping("/import")
//    @RequiresPermissions({"texture:add"})
    public ReturnData batchImportTextures(@RequestPart MultipartFile file) {
        if (file == null ||!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(Punctuation.DOT) + 1).equalsIgnoreCase("XLSX")) {
            return ReturnData.builder().success(false).code(PARAM_ERROR).message("请上传xlsx格式文件");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        log.info("user info,{}", user);
        try {
            ExcelUtils<CompanyShopImportBean> utils = new ExcelUtils(CompanyShopImportBean.class);
            List<CompanyShopImportBean> beans = utils.readExcelToBean(file.getInputStream(), 0, basePath, "/AA/c_companyshop");
            List<CompanyshopofflineAdd> adds  = new ArrayList<CompanyshopofflineAdd>();
            for (int i = 0; i < beans.size(); i++) {
            	CompanyShopImportBean bean = beans.get(i);
            	CompanyshopofflineAdd add = new CompanyshopofflineAdd();
            	add.setCreator(user.getId()+"");
            	BeanUtils.copyProperties(bean, add);
            	add.setCompanyId(user.getBusinessAdministrationId().intValue());
            	//检查店铺名称是否重复
            	Companyshopoffline companyshopoffline = companyshopofflineBizService.checkShopName(add.getShopName(), add.getCompanyId());
                if (companyshopoffline != null) {
                    throw new RuntimeException(String.format("请检查第%s行,该门店名已存在,请重新输入!", (i+1)));
                }
                Integer count = companyShopService.checkShopName(add.getShopName(), null);
                if (count != null && 0 < count.intValue()) {
                    throw new RuntimeException(String.format("请检查第%s行,该门店名已存在,请重新输入!", (i+1)));
                }
            	BaseAreaBo bo =  baseAreaService.getAreaCodeByName(bean.getProvinceName(), bean.getCityName(), bean.getAreaName(), bean.getStreetName());
            	if(bo == null) {
                    throw new RuntimeException(String.format("请检查第%s行,请检查省市区街道输入是否正确!", (i+1)));
            	}else {
            		add.setProvinceCode(bo.getProvinceCode());
            		add.setCityCode(bo.getCityCode());
            		add.setAreaCode(bo.getAreaCode());
            		add.setStreetCode(bo.getStreetCode());
            	}
            	adds.add(add);
			}
            log.info("start insert shops, time:{}", LocalDateTime.now());
            companyshopofflineBizService.importCompanyShopFromExcel(adds);
            log.info("end insert shops, time:{}", LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.builder().success(false).code(PARAM_ERROR).message(e.getMessage());
        }
        return ReturnData.builder().success(true).code(SUCCESS);
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
