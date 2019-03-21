package com.sandu.web.servicepurchase.controller;

import static java.math.BigDecimal.ROUND_DOWN;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sandu.api.servicepurchase.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.message.BasicHeader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.servicepurchase.constant.ServicesPurchaseConstant;
import com.sandu.api.servicepurchase.input.InnerServiceRenew;
import com.sandu.api.servicepurchase.input.ServicesOfficialTrial;
import com.sandu.api.servicepurchase.input.ServicesPurchaseRenewals;
import com.sandu.api.servicepurchase.input.query.ServiceInnerUserQuery;
import com.sandu.api.servicepurchase.input.query.ServiceQuery;
import com.sandu.api.servicepurchase.model.bo.ServiceRecordInitBO;
import com.sandu.api.servicepurchase.model.bo.ServicesFuncBO;
import com.sandu.api.servicepurchase.model.bo.ServicesListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseLogListBO;
import com.sandu.api.servicepurchase.model.vo.OfficalServicesListVO;
import com.sandu.api.servicepurchase.model.vo.ServiceInnerUserListVO;
import com.sandu.api.servicepurchase.model.vo.ServicesFuncVO;
import com.sandu.api.servicepurchase.model.vo.ServicesListVO;
import com.sandu.api.servicepurchase.model.vo.ServicesPurchaseListVO;
import com.sandu.api.servicepurchase.model.vo.ServicesPurchaseLogListVO;
import com.sandu.api.servicepurchase.output.PayResponseVO;
import com.sandu.api.servicepurchase.output.ServicesBaseInfoVO;
import com.sandu.api.servicepurchase.output.ServicesInfoVO;
import com.sandu.api.servicepurchase.output.ServicesPriceContentVO;
import com.sandu.api.servicepurchase.output.ServicesRoleVO;
import com.sandu.api.servicepurchase.output.ServicesUpdateInfoVO;
import com.sandu.api.servicepurchase.serivce.ServicesAccountRelService;
import com.sandu.api.servicepurchase.serivce.ServicesBaseInfoService;
import com.sandu.api.servicepurchase.serivce.ServicesPriceService;
import com.sandu.api.servicepurchase.serivce.ServicesPurchaseRecordService;
import com.sandu.api.servicepurchase.serivce.ServicesRoleRelService;
import com.sandu.api.servicepurchase.serivce.biz.ServicesPurchaseBizService;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.ReturnData;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.util.CopyPropretyUtil;
import com.sandu.web.BaseController;
import com.sandu.web.servicepurchase.ExcelExportUtils;
import com.sandu.web.servicepurchase.HttpResult;
import com.sandu.web.servicepurchase.HttpService;
import com.sandu.web.servicepurchase.ServicesExportBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(description = " 服务购买")
@RestController
@RequestMapping("/v1/services")
@Slf4j
public class ServicesPurchaseController extends BaseController {


    private final static String PAY_METHOD_WX = "wxScanCodePay";
    private final static String PAY_METHOD_ZF = "aliScanCodePay";
    @Resource
    private ServicesPurchaseBizService servicesPurchaseBizService;
    @Resource
    private ServicesBaseInfoService servicesBaseInfoService;
    @Resource
    private ServicesPriceService servicesPriceService;
    @Resource
    private ServicesAccountRelService servicesAccountRelService;
    @Resource
    private ServicesPurchaseRecordService servicesPurchaseRecordService;
    @Resource
    private ServicesRoleRelService servicesRoleRelService;
    @Resource
    private DictionaryService sysDictionaryService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    BaseCompanyService baseCompanyService;
    @Autowired
    private HttpService httpService;
    @Resource
    private SysUserService sysUserService;
    @Value("${service.pay.notifyUrl}")
    private String notifyUrl;
    @Value("${service.pay.url}")
    private String payUrl;
    @Value("${wxpay.ip}")
    private String wxPayIp;

//    @Autowired
//	private SimpMessagingTemplate messagingTemplate;
//    @Autowired
//    private WebSocketServer webSocketServer ;


    @ApiOperation(value = "获取可购买服务列表.", response = ServicesListVO.class)
    @GetMapping("/choose/list")
    public ResponseEnvelope chooseServicesList(@Valid ServiceQuery query, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        PageInfo<ServicesListBO> services = servicesPurchaseBizService.getServicesByScope(query);
        List<ServicesListVO> vos = services.getList().stream().map(bo -> {
            ServicesListVO vo = ServicesListVO.builder().build();
            BeanUtils.copyProperties(bo, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ResponseEnvelope<>(true, services.getTotal(), vos);
    }

    @ApiOperation(value = "已购套餐.", response = ServicesPurchaseListVO.class)
    @GetMapping("/purchase/list")
    public ResponseEnvelope bePurchase(@Valid ServiceQuery query, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        PageInfo<ServicesPurchaseListBO> bos = servicesPurchaseBizService.getBePurchasedServices(query);
        log.info("bos:{}", bos.getList());
        List<ServicesPurchaseListVO> vos = bos.getList().stream().map(bo -> {
            ServicesPurchaseListVO vo = ServicesPurchaseListVO.builder().build();
            BeanUtils.copyProperties(bo, vo);
            return vo;
        }).collect(Collectors.toList());
        log.info("vos:{}", vos);
        return new ResponseEnvelope<>(true, bos.getTotal(), vos);
    }

    @ApiOperation(value = "购买记录.", response = ServicesPurchaseLogListVO.class)
    @GetMapping("/paylog/list")
    public ResponseEnvelope getPurchaseLogs(@Valid ServiceQuery query, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        PageInfo<ServicesPurchaseLogListBO> bos = servicesPurchaseBizService.getPurchasedServicesLogs(query);
        List<ServicesPurchaseLogListVO> vos = bos.getList().stream().map(bo -> {
            ServicesPurchaseLogListVO vo = ServicesPurchaseLogListVO.builder().build();
            BeanUtils.copyProperties(bo, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ResponseEnvelope<>(true, bos.getTotal(), vos);
    }

    @ApiOperation(value = "功能详情.", response = ServicesListVO.class)
    @GetMapping("/func/details/{serviceId}")
    public ResponseEnvelope<ServicesFuncVO> getFuncs(@PathVariable Integer serviceId) {
        ServiceQuery serviceQuery = new ServiceQuery();
        serviceQuery.setServiceId(serviceId);
        List<ServicesFuncBO> funcs = servicesPurchaseBizService.getServiceFuncsByServiceId(serviceId);
        List<ServicesFuncVO> vos = funcs.stream().map(bo -> {
            ServicesFuncVO vo = ServicesFuncVO.builder().build();
            BeanUtils.copyProperties(bo, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ResponseEnvelope<>(true, vos.size(), vos);
    }

    //    @ApiOperation(value = "导出已购账号.", response = ServicesListVO.class)
//    @GetMapping("/file/{companyId}")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer companyId) {
        ServiceQuery serviceQuery = new ServiceQuery();
        serviceQuery.setCompanyId(companyId);
        PageInfo<ServicesListBO> services = servicesPurchaseBizService.getServicesByScope(serviceQuery);
        ServicesListBO servicesListBO = new ServicesListBO();
        servicesListBO.setServicesName("-----");
        servicesListBO.setServicesCode("-----");
        servicesListBO.setPrice(BigDecimal.valueOf(55456));
        services.getList().add(servicesListBO);
        List<ServicesExportBean> list = services.getList().stream().map(item -> {
            ServicesExportBean servicesExportBean = new ServicesExportBean();
            BeanUtils.copyProperties(item, servicesExportBean);
            servicesExportBean.setGmtCreate(new Date());
            return servicesExportBean;
        }).collect(Collectors.toList());
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("测试.xls", "utf-8"));
//            response.setHeader("Content-disposition", "attachment;filename=" +"测试.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("统计表");
        ExcelExportUtils.createTitle(workbook, sheet, ServicesExportBean.class);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\excle_test\\测试.xls"));
            ExcelExportUtils.setData(workbook, sheet, list, fileOutputStream);
            ExcelExportUtils.setData(workbook, sheet, list, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "商家后台点击购买弹出详情", response = ServicesBaseInfoVO.class)
    @GetMapping(value = "/{servicesId}/info")
    public ResponseEnvelope getServicesPriceById(@ApiParam(value = "套餐ID", required = true) @PathVariable Long servicesId) {
        ServicesBaseInfoVO bo = null;
        bo = servicesPurchaseBizService.findServicesPriceById(servicesId);
        if (bo == null) {
            return new ResponseEnvelope<>(false, "未查询到相关数据...");
        }
        return new ResponseEnvelope<>(true, bo);
    }

    @ApiOperation(value = "计算总价", response = ReturnData.class)
    @GetMapping(value = "/{servicesId}/{priceId}/{purchaseAmount}/info")
    public ResponseEnvelope getProductInfoById(@ApiParam(value = "套餐ID", required = true) @PathVariable Long servicesId,
                                               @ApiParam(value = "时长选择", required = true) @PathVariable Long priceId,
                                               @ApiParam(value = "购买总数量", required = true) @PathVariable Long purchaseAmount) {
        BigDecimal payAmount = servicesPurchaseBizService.getServicesPayAmount(servicesId, priceId, purchaseAmount);
        return new ResponseEnvelope<>(true, payAmount);
    }

    @ApiOperation(value = "手机号码校验", response = ServicesBaseInfoVO.class)
    @GetMapping(value = "/{telephone}/check")
    public ResponseEnvelope checkTelephoneExists(@ApiParam(value = "手机号码", required = true) @PathVariable String telephone) {
        int count = servicesPurchaseBizService.checkTelephoneExists(telephone);
        if (count > 0) {
            return new ResponseEnvelope<>(false, "手机号码已注册试用,不能重复试用!");
        }
        return new ResponseEnvelope<>(true, "");
    }

    @ApiOperation(value = "官网试用功能")
    @PostMapping("/officialTrial")
    public ResponseEnvelope officialTrial(@Valid @RequestBody ServicesOfficialTrial trial, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, new ResponseEnvelope());
        }
        Gson gson = new Gson();
        log.info("官网试用传入参数:{}", gson.toJson(trial));
        ServicesPurchaseRecord record = new ServicesPurchaseRecord();
        record.setPurchaseSource(ServicesPurchaseConstant.PURCHASE_SOURCE_1);   //购买来源为官网
        record.setBusinessType(ServicesPurchaseConstant.BUSSINESS_TYPE_2);     //业务类型为试用
        record.setUserScope(ServicesPurchaseConstant.USER_TYPE_5);         //试用用户类型为设计师
        record.setPurchaseAmount(new BigDecimal(1));
        record.setTelephone(trial.getTelephone());
        record.setTelephoneCode(trial.getTelephoneCode());
        Map<String, Object> resultMap = servicesPurchaseBizService.saveBuy(record);
        if (resultMap != null && resultMap.containsKey("error")) {
            return new ResponseEnvelope<>(false, resultMap.get("error"));
        } else {
            return new ResponseEnvelope<>(true, "恭喜你试用成功!");
        }
    }

    @ApiOperation(value = "官网套餐展示", response = OfficalServicesListVO.class)
    @GetMapping("/offical/{channelId}")
    public ResponseEnvelope officialServicesList(@PathVariable Integer channelId) {
        OfficalServicesListVO vo = servicesPurchaseBizService.getOfficalServicesInfo(channelId);
        return new ResponseEnvelope<>(true, "查询成功!", vo);
    }

    @ApiOperation(value = "套餐续费查询", response = ServicesInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "saleChannel", value = "套餐销售渠道(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)", paramType = "path", required = true, dataType = "String")
    })
    @RequestMapping(value = "/renewals/{userId}/{saleChannel}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope renewals(@PathVariable Integer userId, @PathVariable String saleChannel) {

        log.info("套餐_查询续费套餐_入参,userId:{},saleChannel:{}", userId, saleChannel);

        ServicesInfoVO servicesInfoVo = new ServicesInfoVO();

        //用户可以购买套餐的用户类型集合
        Long companyId = this.getUserCompanyIdByUserId(userId);
        if (companyId == null) {
            log.error("用户信息错误，请联系管理员！userId:{}", userId);
            return new ResponseEnvelope(false, "用户信息错误，请联系管理员！", servicesInfoVo);
        }
        Set<String> userScopeSet = this.getUserScopeByCompanyId(companyId);

        //根据用户id查询用户当前套餐
        List<ServicesAccountRel> servicesAccountRelList = servicesAccountRelService.queryByUserId(userId, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesAccountRelList)) {
            log.error("没有找到当前用户套餐，数据异常，请联系管理员！userId:{}", userId);
            return new ResponseEnvelope(false, "没有找到当前用户套餐，数据异常，请联系管理员！", servicesInfoVo);
        }
        if (servicesAccountRelList.size() > 1) {
            log.error("用户当前有多个套餐，数据异常，请联系管理员！userId:{}", userId);
            return new ResponseEnvelope(false, "用户当前有多个套餐，数据异常，请联系管理员！", servicesInfoVo);
        }
        ServicesAccountRel servicesAccountRel = servicesAccountRelList.get(0);

        //查询套餐是否可续费
        ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.queryReBuyServices(servicesAccountRel.getServicesId(),
                userScopeSet, saleChannel, ServicesPurchaseConstant.SERVICES_STATUS_1
                , ServicesPurchaseConstant.SERVICES_TYPE_FORMAL, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (servicesBaseInfo == null) {
            log.info("用户不能升级此套餐，请重新选择，serviceId:{}，userScopeSet:{}，saleChannel:{}", servicesAccountRel.getServicesId(), userScopeSet, saleChannel);
            servicesInfoVo.setServicesStatus(String.valueOf(ServicesPurchaseConstant.SERVICES_RENEW_STATUS_0));
            return new ResponseEnvelope(false, "当前套餐不可购买，请选择升级其他套餐！", servicesInfoVo);
        }

        //套餐基本信息
        this.getServiceInfoVO(servicesInfoVo, servicesBaseInfo);
        //查询套餐价格
        List<ServicesPrice> servicesPriceList = servicesPriceService.queryByServicesIdAndCompanyId(servicesAccountRel.getServicesId(),
                Math.toIntExact(companyId), ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesPriceList)) {
            servicesPriceList = servicesPriceService.queryByServicesIdAndCompanyId(servicesAccountRel.getServicesId(),
                    ServicesPurchaseConstant.COMMON_COMPANY_ID, ServicesPurchaseConstant.DELETE_FLAG_0);
            if (CollectionUtils.isEmpty(servicesPriceList)) {
                log.error("套餐未设置价格，不可购买！servicesId:{}", servicesAccountRel.getServicesId());
                return new ResponseEnvelope(false, "没有找到该套餐价格,不可购买！", servicesInfoVo);
            }
        }

        //组装数据
        this.getServicesInfoVO(servicesInfoVo, null, servicesPriceList);

        return new ResponseEnvelope(true, "success", servicesInfoVo);
    }

    @ApiOperation(value = "升级套餐列表(分页查询可升级套餐)", response = ServicesUpdateInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "saleChannel", value = "套餐销售渠道(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示多少行", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "showRole", value = "是否显示套餐角色（显示：true，不显示：false）（默认不显示）", paramType = "query", required = true, dataType = "Boolean")
    })
    @RequestMapping(value = "/chooseList/{userId}/{saleChannel}/{pageNo}/{pageSize}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope<ServicesUpdateInfoVO> chooseList(@PathVariable Integer userId, @PathVariable String saleChannel, @PathVariable Integer pageNo, @PathVariable Integer pageSize, Boolean showRole) {

        log.info("套餐_查询升级套餐_入参,userId:{},saleChannel:{},pageNo:{},pageSize:{}},showRole:{}", userId, saleChannel, pageNo, pageSize, showRole);

        long totalCount;//套餐列表总记录数
        //是否展示角色，默认不展示
        if (showRole == null) {
            showRole = false;
        }

        //页码信息校验
        List<ServicesUpdateInfoVO> servicesUpdateInfoVOList = new ArrayList<>();
        if (pageNo <= 0 || pageSize <= 0) {
            return new ResponseEnvelope<>(false, "页码错误！", servicesUpdateInfoVOList);
        }

        //查询用户可购买的套餐
        ResponseEnvelope<ServicesBaseInfo> responseEnvelope = this.findUserServices(userId, saleChannel, pageNo, pageSize);
        if (!responseEnvelope.isSuccess()) {
            return new ResponseEnvelope<>(false, responseEnvelope.getMessage(), servicesUpdateInfoVOList);
        }
        List<ServicesBaseInfo> servicesBaseInfoList = responseEnvelope.getDatalist();
        totalCount = responseEnvelope.getTotalCount();

        //查询套餐角色services_role_rel
        Set<Long> servicesIds = servicesBaseInfoList.stream().map(ServicesBaseInfo::getId).collect(Collectors.toSet());
        List<ServicesRoleRel> servicesRoleRelList = servicesRoleRelService.queryByIds(servicesIds, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesRoleRelList)) {
            log.error("套餐未设置角色，套餐ids:{}", servicesIds);
            return new ResponseEnvelope<>(false, "套餐未设置角色，请联系管理员！", servicesUpdateInfoVOList);
        }

        // 查询角色名称
        Set<Long> roleIds = new HashSet<>();
        servicesRoleRelList.forEach(servicesRoleRel -> roleIds.add(Long.valueOf(servicesRoleRel.getRoleId())));
        if(roleIds == null || roleIds.size() == 0) {
            log.error("角色ids:{}", roleIds);
            return new ResponseEnvelope<>(false, "套餐设置的角色不存在,请联系管理员！");
        }
        List<SysRole> sysRoleList = sysRoleService.getRolesByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(sysRoleList)) {
            log.error("套餐设置的角色不存在，角色ids:{}", roleIds);
            return new ResponseEnvelope<>(false, "套餐设置的角色不存在,请联系管理员！", servicesUpdateInfoVOList);
        }
        Map<Long, String> roleNameMap = sysRoleList.stream().collect(Collectors.toMap(SysRole::getId, SysRole::getName));

        // 组装角色数据
        Map<Long, List<ServicesRoleVO>> servicesToRoleIdMap = this.getServicesRoleMap(servicesRoleRelList, roleNameMap);

        //组装返回对象
        this.getServicesUpdateInfoVOList(showRole, servicesUpdateInfoVOList, servicesBaseInfoList, servicesToRoleIdMap);

        return new ResponseEnvelope<>(true, totalCount, servicesUpdateInfoVOList, "200");
    }

    @ApiOperation(value = "查询选择的升级套餐详细信息", response = ServicesInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "serviceId", value = "升级套餐id", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "saleChannel", value = "套餐销售渠道(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)", paramType = "path", required = true, dataType = "String")
    })
    @RequestMapping(value = "/updagrade/{userId}/{saleChannel}/{serviceId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope updagrade(@PathVariable Integer userId, @PathVariable Long serviceId, @PathVariable String saleChannel) {

        log.info("套餐_查询升级的套餐_入参，userId:{},servicesId:{},saleChannel:{}", userId, serviceId, saleChannel);

        ServicesInfoVO servicesInfoVO = new ServicesInfoVO();

        //用户可以购买套餐的用户类型集合
        Long companyId = this.getUserCompanyIdByUserId(userId);
        Set<String> userScopeSet = this.getUserScopeByCompanyId(companyId);

        //查询用户当前套餐，计算套餐残值
        List<ServicesAccountRel> servicesAccountRelList = servicesAccountRelService.queryByUserId(userId, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesAccountRelList)) {
            log.error("没有找到当前用户套餐，数据异常，请联系管理员！userId:{}", userId);
            return new ResponseEnvelope(false, "没有找到当前用户套餐，数据异常，请联系管理员！", servicesInfoVO);
        }
        if (servicesAccountRelList.size() > 1) {
            log.error("用户当前有多个套餐，数据异常，请联系管理员！userId:{}", userId);
            return new ResponseEnvelope(false, "用户当前有多个套餐，数据异常，请联系管理员！", servicesInfoVO);
        }
        ServicesAccountRel servicesAccountRel = servicesAccountRelList.get(0);

        //获取当前套餐价格
        ServicesPurchaseRecord servicesPurchaseRecord = servicesPurchaseRecordService.selectByPrimaryKey(servicesAccountRel.getPurchaseRecordId());
        if (servicesPurchaseRecord == null) {
            log.error("没有找到用户当前套餐购买记录，数据异常，请联系管理员！userId:{},servicesPurchaseRecordId:{}", userId,
                    servicesAccountRel.getPurchaseRecordId());
            return new ResponseEnvelope(false, "没有找到用户当前套餐购买记录，数据异常，请联系管理员！",
                    servicesInfoVO);
        }

        //计算套餐残值，原有套餐残值=原套餐价钱*（1-已使用时长/原套餐总时长）。
        BigDecimal surplusValue = getSurplusValue(servicesAccountRel.getEffectiveEnd(), servicesAccountRel.getEffectiveBegin(),
                servicesPurchaseRecord.getUnitPrice());

        // 校验套餐是否可用
        ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.queryReBuyServices(serviceId,
                userScopeSet, saleChannel, ServicesPurchaseConstant.SERVICES_STATUS_1,
                ServicesPurchaseConstant.SERVICES_TYPE_FORMAL, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (servicesBaseInfo == null) {
            log.info("用户不能升级此套餐，请重新选择，serviceId:{}，userScopeSet:{}，saleChannel:{}", serviceId, userScopeSet, saleChannel);
            return new ResponseEnvelope(false, "用户不能升级此套餐，请重新选择！", servicesInfoVO);
        }

        //套餐基本信息
        this.getServiceInfoVO(servicesInfoVO, servicesBaseInfo);
        //查询套餐价格
        List<ServicesPrice> servicesPriceList = getServicesPriceList(serviceId, Math.toIntExact(companyId));
        if (CollectionUtils.isEmpty(servicesPriceList)) {
            log.info("没有找到升级套餐价格！不可购买！serviceId:{},companyId:{}", serviceId, companyId);
            return new ResponseEnvelope(false, "没有找到升级套餐价格！不可购买！", servicesInfoVO);
        }

        //组装数据
        this.getServicesInfoVO(servicesInfoVO, surplusValue, servicesPriceList);

        //套餐用户类型
        List<SysDictionary> sysDictionarieList = sysDictionaryService.listByType("userType");
        Map<Integer, String> userScoMap;
        if (CollectionUtils.isNotEmpty(sysDictionarieList)) {
            Integer scope = Integer.valueOf(servicesInfoVO.getUserScope());
            userScoMap = sysDictionarieList.stream().collect(Collectors.toMap(SysDictionary::getValue, SysDictionary::getName, (key1, key2) -> key2));
            servicesInfoVO.setUserScopeName(userScoMap.get(scope));
        }

        return new ResponseEnvelope(true, "success", servicesInfoVO);
    }


    @ApiOperation(value = "查询套餐功能", response = ServicesUpdateInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "servicesId", value = "升级套餐id", paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "saleChannel", value = "套餐销售渠道(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "showRole", value = "是否显示套餐角色（显示：true，不显示：false）（默认不显示）", paramType = "query", required = true, dataType = "Boolean")
    })
    @RequestMapping(value = "/funcList/{servicesId}/{userId}/{saleChannel}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope funcList(@PathVariable Long servicesId, @PathVariable Integer userId, @PathVariable String saleChannel,
                                     Boolean showRole) {

        log.info("套餐_查询套餐功能_入参，userId:{},servicesId:{},saleChannel:{},showRole:{}", userId, servicesId, saleChannel, showRole);

        if (showRole == null) {
            showRole = false;
        }
        ServicesUpdateInfoVO servicesUpdateInfoVO = new ServicesUpdateInfoVO();

        //用户可以购买套餐的用户类型集合
        Long companyId = this.getUserCompanyIdByUserId(userId);
        Set<String> userScopeSet = this.getUserScopeByCompanyId(companyId);
        //查询套餐信息
        ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.queryReBuyServices(servicesId, userScopeSet, saleChannel,
                ServicesPurchaseConstant.SERVICES_STATUS_1, ServicesPurchaseConstant.SERVICES_TYPE_FORMAL, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (servicesBaseInfo == null) {
            log.error("套餐对当前用户不可用，不能查看！saleChannel:{},servicesId:{},userScopeSet:{}", saleChannel, servicesId, userScopeSet);
            return new ResponseEnvelope(false, "套餐对当前用户不可用，不能查看！", servicesUpdateInfoVO);
        }

        //查询套餐角色
        List<ServicesRoleRel> servicesRoleRelList = servicesRoleRelService.queryByServiceId(servicesId, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesRoleRelList)) {
            log.error("套餐没有配置角色,servicesId:{}", servicesId);
            return new ResponseEnvelope(false, "套餐没有配置角色，不能查看！", servicesUpdateInfoVO);
        }
        // 查询角色名称
        Set<Long> roleIds = new HashSet<>();
        servicesRoleRelList.forEach(servicesRoleRel -> roleIds.add(Long.valueOf(servicesRoleRel.getRoleId())));
        if(roleIds == null || roleIds.size() == 0) {
            log.error("角色ids:{}", roleIds);
            return new ResponseEnvelope<>(false, "套餐设置的角色不存在,请联系管理员！");
        }
        List<SysRole> sysRoleList = sysRoleService.getRolesByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(sysRoleList)) {
            log.error(" 套餐设置的角色不存在，角色ids:{}", roleIds);
            return new ResponseEnvelope(false, "套餐设置的角色不存在,请联系管理员！", servicesUpdateInfoVO);
        }
        Map<Long, String> roleNameMap = sysRoleList.stream().collect(Collectors.toMap(SysRole::getId, SysRole::getName));


        //组装角色对象
        Map<Long, List<ServicesRoleVO>> servicesToRoleIdMap = this.getServicesRoleMap(servicesRoleRelList, roleNameMap);
        //套餐基本信息
        getServicesUpdateInfoVO(showRole, servicesUpdateInfoVO, servicesBaseInfo, servicesToRoleIdMap);
        return new ResponseEnvelope(true, "success", servicesUpdateInfoVO);
    }

    @ApiOperation(value = "查询购买套餐详情", response = ServicesInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "升级套餐id", paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "saleChannel", value = "套餐销售渠道(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)", paramType = "path", required = true, dataType = "String"),
    })
    @GetMapping("/buyInfo/{userId}/{saleChannel}/{serviceId}")
    public ResponseEnvelope buyInfo(@PathVariable Integer userId, @PathVariable Long serviceId, @PathVariable String saleChannel) {

        log.info("套餐_查询升级的套餐_入参，userId:{},servicesId:{},saleChannel:{}", userId, serviceId, saleChannel);

        ServicesInfoVO servicesInfoVO = new ServicesInfoVO();
        //用户可以购买套餐的用户类型集合(企业类型可以购买的用户类型，如厂商可以购买厂商和经销商，关系在sys_dictionary中配置)
        Long companyId = this.getUserCompanyIdByUserId(userId);
        Set<String> userScopeSet = this.getUserScopeByCompanyId(companyId);
        // 校验套餐是否可用(2018-07-23  需求要求可以购买正式和试用套餐，所以servicesType为null)
        ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.queryReBuyServices(serviceId,
                userScopeSet, saleChannel, ServicesPurchaseConstant.SERVICES_STATUS_1,
                null, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (servicesBaseInfo == null) {
            log.info("用户不能购买此套餐，这个用户的用户类型或者购买渠道错误，或者是这个套餐是禁用或删除状态，serviceId:{},userScopeSet:{},saleChannel:{}", serviceId, userScopeSet, saleChannel);
            return new ResponseEnvelope(false, "用户不能购买此套餐，请重新选择！", servicesInfoVO);
        }

        //套餐基本信息
        this.getServiceInfoVO(servicesInfoVO, servicesBaseInfo);
        //查询套餐价格
        if (companyId == null) {
            companyId = Long.valueOf(ServicesPurchaseConstant.COMMON_COMPANY_ID);
        }
        List<ServicesPrice> servicesPriceList = servicesPriceService.queryByServicesIdAndCompanyId(serviceId,
                Math.toIntExact(companyId), ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesPriceList)) {
            servicesPriceList = servicesPriceService.queryByServicesIdAndCompanyId(serviceId,
                    ServicesPurchaseConstant.COMMON_COMPANY_ID, ServicesPurchaseConstant.DELETE_FLAG_0);
            if (CollectionUtils.isEmpty(servicesPriceList)) {
                log.info("没有找到升级套餐价格！不可购买！serviceId:{}", serviceId);
                return new ResponseEnvelope(false, "没有找到升级套餐价格！不可购买！", servicesInfoVO);
            }
        }

        //组装数据（不需要计算残值，所以surplusValue = null）
        this.getServicesInfoVO(servicesInfoVO, null, servicesPriceList);
        return new ResponseEnvelope(true, "success", servicesInfoVO);
    }


    @ApiOperation(value = "查询用户当前套餐信息", response = ServicesInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "msgId", value = "u3d回调id", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/baseInfoFroU3d", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope baseInfoFroU3d(Integer userId, String msgId) {
        log.info("套餐_查询用户当前套餐信息_入参，userId:{},msgId:{}", userId, msgId);
        if (userId == null) {
            return new ResponseEnvelope(false, null, msgId, "没有找到当前用户，数据异常，请联系管理员！");
        }
        //u3d回调id（u3d要求必传）
        ResponseEnvelope responseEnvelope = this.baseInfo(userId);
        responseEnvelope.setMsgId(msgId);

        return responseEnvelope;
    }


    @ApiOperation(value = "查询用户当前套餐信息", response = ServicesInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "String")
    })
    @RequestMapping(value = "/baseInfo/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope baseInfo(@PathVariable Integer userId) {

        log.info("套餐_查询用户当前套餐信息_入参，userId:{}", userId);
        ServicesInfoVO servicesInfoVO = new ServicesInfoVO();

        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            log.error(" 没有找到当前用户，用户id:{}", userId);
            return new ResponseEnvelope(false, servicesInfoVO, "500", "没有找到当前用户，数据异常，请联系管理员！");
        }
        //计算套餐剩余时间
        if (sysUser.getFailureTime() == null) {
            servicesInfoVO.setRemainingTime(0);
        } else {
            int days = (int) ((sysUser.getFailureTime().getTime() - System.currentTimeMillis()) / (1000 * 3600 * 24));
            servicesInfoVO.setRemainingTime(days < 0 ? 0 : days);
        }

        //查询用户当前套餐
        List<ServicesAccountRel> servicesAccountRelList = servicesAccountRelService.queryByUserId(userId, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesAccountRelList)) {
            log.error(" 没有找到当前用户套餐，用户id:{}", userId);
            return new ResponseEnvelope(false, servicesInfoVO, "500", "没有找到当前用户套餐，数据异常，请联系管理员！");
        }
        if (servicesAccountRelList.size() > 1) {
            log.error(" 用户当前有多个套餐，用户id:{}", userId);
            return new ResponseEnvelope(false, servicesInfoVO, "500", "用户当前有多个套餐，数据异常，请联系管理员！");
        }
        ServicesAccountRel servicesAccountRel = servicesAccountRelList.get(0);

        ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.getById(servicesAccountRel.getServicesId());
        if (servicesBaseInfo == null) {
            log.error(" 当前用户套餐不存在，servicesId:{}", userId);
            return new ResponseEnvelope(false, servicesInfoVO, "500", "当前用户套餐不存在！");
        }
        this.getServiceInfoVO(servicesInfoVO, servicesBaseInfo);

        return new ResponseEnvelope(true, servicesInfoVO, "200", "success");
    }

    @RequiresPermissions({"manufacturer.user.service.upgrade", "dealer.user.service.upgrade", "company.user.service.upgrade","user.manage.service.upgrade"})
    @ApiOperation(value = "三度用户修改套餐")
    @PostMapping("inner/edit")
    public ResponseEnvelope innerEditService(HttpServletRequest request, @RequestBody @Valid ServicesPurchaseRenewals renewals,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, new ResponseEnvelope());
        }
        if (!renewals.isInnerFlag()) {
            return new ResponseEnvelope<>(false, "系统错误");
        }
        List<ServicesFuncBO> platformList = servicesBaseInfoService.getFunctionList(renewals.getServiceId());
        if (platformList == null || platformList.size() == 0) {
            return new ResponseEnvelope<>(false, "编辑失败，当前套餐暂未关联功能！");
        }
        ServiceRecordInitBO bo = new ServiceRecordInitBO();
        BeanUtils.copyProperties(renewals, bo);
        bo.setPriceId(Long.valueOf(renewals.getPriceId()));
        bo.setAccountUserId(renewals.getAccountUserId());
        try {
            //生成套餐订单
            ServicesPurchaseRecord record = servicesPurchaseBizService.initServiceOrder(bo);
            //同步账号信息
            servicesPurchaseBizService.syncServiceAccount(record.getOrderNo(), ServicesPurchaseRecord.RECORD_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope<>(false, e.getMessage());
        }
        return new ResponseEnvelope<>(true, "修改成功 ");
    }

    @ApiOperation(value = "服务续费/购买支付接口")
    @PostMapping("pay/confirm")
    public ResponseEnvelope initServicePayOrder(HttpServletRequest request, @RequestBody @Valid ServicesPurchaseRenewals renewals,
                                                BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, new ResponseEnvelope());
        }
        ServiceRecordInitBO bo = new ServiceRecordInitBO();
        BeanUtils.copyProperties(renewals, bo);
        bo.setPriceId(Long.valueOf(renewals.getPriceId()));
        bo.setAccountUserId(renewals.getAccountUserId());
        HttpResult httpResult;
        //生成套餐订单
        try {
            ServicesPurchaseRecord record = servicesPurchaseBizService.initServiceOrder(bo);
            //生成支付订单,获取支付二维码
            httpResult = initPayOrder(request, renewals, record);
        } catch (Exception e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        }
        log.info("获取支付二维码,httpResult:{}", httpResult);
        if (Objects.equals(httpResult.getCode(), 200)) {
            return new ResponseEnvelope<>(true, (Object) httpResult.getBody());
        }
        return new ResponseEnvelope<>(false, "系统异常，获取支付信息失败");
    }

    private String initPayOrderWithRPC(ServicesPurchaseRenewals renewals, ServicesPurchaseRecord record) {
        PayParam payParam = new PayParam();
        payParam.setIntenalTradeNo(record.getOrderNo());
        payParam.setTradeDesc("套餐续费/升级");
        payParam.setTotalFee(1L);
        payParam.setPayMethod(record.getPayType().equals("0") ? PAY_METHOD_ZF : PAY_METHOD_WX);
        payParam.setIp(wxPayIp);
        payParam.setNotifyUrl(notifyUrl);
        payParam.setOperator(Long.valueOf(record.getUserid()));
        payParam.setPlatformCode(renewals.getPlatformCode());
        payParam.setSource(Integer.valueOf(renewals.getPurchaseSource()));
        PayService payService = SpringContextHolder.getBean(payParam.getPayMethod());
        return payService.doPay(payParam);
    }

    @ApiOperation(value = "获取订单的支付状态", response = PayResponseVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payTradeNo", value = "交易订单号", paramType = "path", required = true, dataType = "String")
    })
    @RequestMapping(value = "/payResponse/{payTradeNo}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEnvelope payResponse(@PathVariable String payTradeNo) throws Exception {
        String status = servicesPurchaseBizService.getTradeStatus(payTradeNo);
        PayResponseVO response = new PayResponseVO();
        response.setPayTradeNo(payTradeNo);
        response.setStatus(status); //支付状态:0-未支付;1-支付成功;2-支付失败
        return new ResponseEnvelope<>(true, response);
    }

    @ApiOperation(value = "支付完成回调接口")
    @PostMapping("pay/callback")
    public void payCallbackFun(HttpServletRequest request) {
        log.info("支付完成，接口回调。");
        log.info("request param:{}", request.getParameterMap());
        String recordStatus;
        String payTradeNo = "";
        try {
            if ("SUCCESS".equals(request.getParameter("resultCode"))) {
                recordStatus = ServicesPurchaseRecord.RECORD_STATUS_SUCCESS;
            } else {
                recordStatus = ServicesPurchaseRecord.RECORD_STATUS_FAIL;
            }
            payTradeNo = request.getParameter("payTradeNo");
            //更新订单状态
            servicesPurchaseBizService.updateRedisStatus(payTradeNo, recordStatus);
            servicesPurchaseBizService.syncServiceAccount(request.getParameter("intenalTradeNo"), recordStatus);
        } catch (Exception e) {
            log.error("{}", e);
            e.printStackTrace();
        }
    }


    private HttpResult initPayOrder(HttpServletRequest request, ServicesPurchaseRenewals renewals, ServicesPurchaseRecord record) {
        try {
            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("intenalTradeNo", record.getOrderNo());
            requestParam.put("tradeDesc", "套餐续费/升级");
            log.info("支付金额:{}", record.getPurchaseMomey());
            requestParam.put("totalFee", record.getPurchaseMomey().multiply(new BigDecimal(100)).longValue() + "");
			requestParam.put("payMethod", "0".equals(record.getPayType()) ? PAY_METHOD_ZF : PAY_METHOD_WX);
            requestParam.put("ip", wxPayIp);
            requestParam.put("notifyUrl", notifyUrl);
            requestParam.put("operator", renewals.getUserId().toString());
            requestParam.put("source", renewals.getPurchaseSource());
            log.info("支付参数:{}", requestParam);
            List<BasicHeader> basicHeaders = Arrays.asList(new BasicHeader("Platform-Code", renewals.getPlatformCode()),
                    new BasicHeader("Authorization", request.getHeader("Authorization")));
            HttpResult httpResult = httpService.doPost(payUrl, requestParam, basicHeaders);
            log.info("rest pay result:{}", httpResult);
            return httpResult;
        } catch (Exception e) {
            log.error("exception:{}", e);
            e.printStackTrace();
        }
        return new HttpResult();
    }

    /**
     * 组装续费套餐返回对象
     *
     * @param servicesUpdateInfoVOList 续费套餐返回对象List
     * @param servicesBaseInfoList     套餐列表
     * @param servicesToRoleIdMap      套餐与角色名称列表对应的map
     */
    private void getServicesUpdateInfoVOList(boolean showRole, List<ServicesUpdateInfoVO> servicesUpdateInfoVOList, List<ServicesBaseInfo> servicesBaseInfoList, Map<Long, List<ServicesRoleVO>> servicesToRoleIdMap) {
        ServicesUpdateInfoVO servicesUpdateInfoVO;
        for (ServicesBaseInfo servicesBaseInfo : servicesBaseInfoList) {
            servicesUpdateInfoVO = new ServicesUpdateInfoVO();
            getServicesUpdateInfoVO(showRole, servicesUpdateInfoVO, servicesBaseInfo, servicesToRoleIdMap);
            servicesUpdateInfoVOList.add(servicesUpdateInfoVO);
        }
    }

    /**
     * 组装角色对象
     *
     * @param servicesRoleRelList 套餐角色列表
     * @param roleNameMap         套餐角色月名称对应的map
     * @return 套餐与角色名称列表对应的map
     */
    private Map<Long, List<ServicesRoleVO>> getServicesRoleMap(List<ServicesRoleRel> servicesRoleRelList, Map<Long, String> roleNameMap) {
        // 组装角色数据
        Map<Long, List<ServicesRoleVO>> servicesToRoleIdMap = new HashMap<>(16);
        servicesRoleRelList.forEach(servicesRoleRel -> {
            List<ServicesRoleVO> list = new ArrayList<>();
            ServicesRoleVO servicesRoleVO = new ServicesRoleVO();
            servicesRoleVO.setRoleId(Long.valueOf(servicesRoleRel.getRoleId()));
            servicesRoleVO.setRoleName(roleNameMap.get(servicesRoleRel.getRoleId().longValue()));
            if (servicesToRoleIdMap.containsKey(servicesRoleRel.getServicesId())) {
                list = servicesToRoleIdMap.get(servicesRoleRel.getServicesId());
                list.add(servicesRoleVO);
            } else {
                list.add(servicesRoleVO);
                servicesToRoleIdMap.put(servicesRoleRel.getServicesId(), list);
            }
        });
        return servicesToRoleIdMap;
    }

    /**
     * 组装升级套餐数据
     *
     * @param servicesInfoVO    需要返回的组装对象
     * @param surplusValue      套餐残值
     * @param servicesPriceList 套餐对应的价格列表
     */
    private void getServicesInfoVO(ServicesInfoVO servicesInfoVO, BigDecimal surplusValue, List<ServicesPrice> servicesPriceList) {
        ServicesPriceContentVO servicesPriceContentVO;
        List<ServicesPriceContentVO> servicesPriceContentVOList = new ArrayList<>();
        for (ServicesPrice servicesPrice : servicesPriceList) {
            servicesPriceContentVO = new ServicesPriceContentVO();
            CopyPropretyUtil.copyProperties(servicesPriceContentVO, servicesPrice);
            //时长
            servicesPriceContentVO.setTerm(getTerm(servicesPrice.getDuration(), servicesPrice.getPriceUnit()));
            //计算套餐价格差值
            if (surplusValue != null) {
                //减去差价后的价格
                BigDecimal differPrice = servicesPrice.getPrice().subtract(surplusValue);
                if (differPrice.compareTo(BigDecimal.valueOf(0)) == 1) {
                    servicesPriceContentVO.setDifferPrice(differPrice);
                } else {
                    servicesPriceContentVO.setDifferPrice(BigDecimal.valueOf(0));
                }
            }
            servicesPriceContentVOList.add(servicesPriceContentVO);
        }
        servicesInfoVO.setServicesPriceVoList(servicesPriceContentVOList);

    }


    /**
     * 获取时长
     *
     * @param duration  时长数值
     * @param priceUnit 时长单位
     * @return 时长
     */
    private String getTerm(Integer duration, String priceUnit) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(duration);
        switch (priceUnit) {
            case ServicesPurchaseConstant.PRICE_UNIT_MONTH:
                stringBuilder.append("个月");
                break;
            case ServicesPurchaseConstant.PRICE_UNIT_DAY:
                stringBuilder.append("天");
                break;
            default:
                stringBuilder.append("年");
                break;
        }
        return stringBuilder.toString();
    }

    /**
     * 获取套餐价格
     *
     * @param serviceId 套餐id
     * @param companyId 用户公司id
     * @return 用户购买该套餐的价格列表
     */
    private List<ServicesPrice> getServicesPriceList(Long serviceId, Integer companyId) {
        List<ServicesPrice> servicesPriceList = servicesPriceService.queryByServicesIdAndCompanyId(serviceId,
                companyId, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesPriceList)) {
            servicesPriceList = servicesPriceService.queryByServicesIdAndCompanyId(serviceId,
                    ServicesPurchaseConstant.COMMON_COMPANY_ID, ServicesPurchaseConstant.DELETE_FLAG_0);
            return servicesPriceList;
        }
        return servicesPriceList;
    }

    /**
     * 计算套餐残值
     *
     * @param endDate   套餐结束时间
     * @param startDate 套餐开始时间
     * @param unitPrice 原套餐价格
     * @return 原套餐残值
     */
    private BigDecimal getSurplusValue(Date endDate, Date startDate, BigDecimal unitPrice) {
        //原有套餐残值=原套餐价钱*（1-已使用时长/原套餐总时长）。
        //未使用的账号套餐时间为空,残值为购买价
        BigDecimal surplusValue = BigDecimal.valueOf(0);
        if (endDate == null || startDate == null) {
            surplusValue = unitPrice;
        } else {
            if (endDate.getTime() - startDate.getTime() == 0) {
                return surplusValue;
            }
            surplusValue = unitPrice.multiply(BigDecimal.valueOf(1 - (endDate.getTime() - System.currentTimeMillis()) / (endDate.getTime() - startDate.getTime())));
        }
        return surplusValue;
    }

    /**
     * 查询用户可购买的套餐
     *
     * @param userId      用户id
     * @param saleChannel 套餐购买渠道
     * @return 用户可购买的套餐列表
     */
    private ResponseEnvelope<ServicesBaseInfo> findUserServices(Integer userId, String saleChannel, Integer start, Integer limit) {

        //定义常量
        Long serviceId;//套餐id
        String userScope;//用户类型
        //查询用户类型
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            log.error(" 没有找到当前用户，数据异常，请联系管理员，用户id:{}", userId);
            return new ResponseEnvelope<>(false, "没有找到当前用户，数据异常，请联系管理员！", Collections.EMPTY_LIST);
        }
        //用户类型
        userScope = String.valueOf(sysUser.getUserType());

        //查询用户当前套餐
        List<ServicesAccountRel> servicesAccountRelList = servicesAccountRelService.queryByUserId(userId, ServicesPurchaseConstant.DELETE_FLAG_0);
        if (CollectionUtils.isEmpty(servicesAccountRelList)) {
            log.error(" 没有找到当前用户套餐，用户id:{}", userId);
            return new ResponseEnvelope<>(false, "没有找到当前用户套餐，数据异常，请联系管理员！", Collections.EMPTY_LIST);
        }
        if (servicesAccountRelList.size() > 1) {
            log.error(" 用户当前有多个套餐，用户id:{}", userId);
            return new ResponseEnvelope<>(false, "用户当前有多个套餐，数据异常，请联系管理员！", Collections.EMPTY_LIST);
        }
        ServicesAccountRel servicesAccountRel = servicesAccountRelList.get(0);
        serviceId = servicesAccountRel.getServicesId();

        //根据当前用户分页查询可购买的套餐
        int totalCount = servicesBaseInfoService.countQueryBuyServices(serviceId, userScope, saleChannel, ServicesPurchaseConstant.SERVICES_STATUS_1
                , ServicesPurchaseConstant.SERVICES_TYPE_FORMAL, ServicesPurchaseConstant.DELETE_FLAG_0);

        if (totalCount < 1) {
            log.info("没有找到可购买的套餐 ,serviceId:{},userScope:{},saleChannel:{}", serviceId, userScope, saleChannel);
            return new ResponseEnvelope<>(false, "没有找到可购买的套餐", Collections.EMPTY_LIST);
        }

        List<ServicesBaseInfo> servicesBaseInfoList = servicesBaseInfoService.queryBuyServices(serviceId, userScope, saleChannel, ServicesPurchaseConstant.SERVICES_STATUS_1
                , ServicesPurchaseConstant.SERVICES_TYPE_FORMAL, ServicesPurchaseConstant.DELETE_FLAG_0, start, limit);
        if (CollectionUtils.isEmpty(servicesBaseInfoList)) {
            log.info("没有找到可购买的套餐 ,serviceId:{},userScope:{},saleChannel:{}", serviceId, userScope, saleChannel);
            return new ResponseEnvelope<>(false, "没有找到可购买的套餐", Collections.EMPTY_LIST);
        }

        return new ResponseEnvelope<>(true, totalCount, servicesBaseInfoList, "200");

    }

    /**
     * 套餐基本信息
     *
     * @param servicesInfoVO   出参对象
     * @param servicesBaseInfo 套餐基本信息
     */
    private void getServiceInfoVO(ServicesInfoVO servicesInfoVO, ServicesBaseInfo servicesBaseInfo) {
        servicesInfoVO.setId(servicesBaseInfo.getId());
        servicesInfoVO.setServicesName(servicesBaseInfo.getServicesName());
        servicesInfoVO.setServicesStatus(servicesBaseInfo.getServicesStatus());
        servicesInfoVO.setServiceDesc(servicesBaseInfo.getServiceDesc());
        servicesInfoVO.setUserScope(servicesBaseInfo.getUserScope());
        servicesInfoVO.setServicesType(servicesBaseInfo.getServicesType());
    }

    /**
     * 可升级套餐基本信息
     *
     * @param servicesUpdateInfoVO 出参对象
     * @param servicesBaseInfo     套餐基本信息
     * @param servicesToRoleIdMap  套餐对应的角色
     */
    private void getServicesUpdateInfoVO(boolean showRole, ServicesUpdateInfoVO servicesUpdateInfoVO, ServicesBaseInfo servicesBaseInfo, Map<Long, List<ServicesRoleVO>> servicesToRoleIdMap) {
        servicesUpdateInfoVO.setId(servicesBaseInfo.getId());
        servicesUpdateInfoVO.setServicesName(servicesBaseInfo.getServicesName());
        servicesUpdateInfoVO.setServiceDesc(servicesBaseInfo.getServiceDesc());
        if (showRole) {
            servicesUpdateInfoVO.setServicesRole(servicesToRoleIdMap.get(servicesBaseInfo.getId()));
        }
    }

    /**
     * 企业类型包含的用户类型集合
     *
     * @param userId
     * @return
     */
    private Long getUserCompanyIdByUserId(Integer userId) {

        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            log.error("用户不存在，userId:{}", userId);
            return null;
        }
        //用户的公司id
        return sysUser.getCompanyId();
    }

    /**
     * 企业类型包含的用户类型集合
     *
     * @param companyId
     * @return
     */
    private Set<String> getUserScopeByCompanyId(Long companyId) {
        BaseCompany baseCompany = baseCompanyService.getCompanyById(companyId);
        if (baseCompany == null) {
            log.error(" 没有找到公司，数据异常，请联系管理员，公司id:{}", companyId);
            return Collections.EMPTY_SET;
        }
        //企业类型的Value
        Integer value = baseCompany.getBusinessType();
        //根据企业类型查询用户类型
        SysDictionary sysDictionary = sysDictionaryService.getByTypeAndValue(ServicesPurchaseConstant.BRAND_BUSSINESS_TYPE, value);
        if (sysDictionary == null) {
            log.error("数据字典没有找到公司类型，数据异常，请联系管理员，公司类型Value:{},type:{}", value, ServicesPurchaseConstant.BRAND_BUSSINESS_TYPE);
            return Collections.EMPTY_SET;
        }
        //用户类型的type
        String type = sysDictionary.getValuekey();
        List<SysDictionary> sysDictionaryList = sysDictionaryService.queryByType(type);
        if (CollectionUtils.isEmpty(sysDictionaryList)) {
            log.error("数据字典没有找到用户类型的type，type:{}", type);
            return Collections.EMPTY_SET;
        }
        Set<String> set = new HashSet<>();
        for (SysDictionary sysDict : sysDictionaryList) {
            set.add(sysDict.getAtt1());
        }
        return set;
    }


    @ApiOperation(value = "无价格套餐购买接口")
    @PostMapping("/pay/confirm/test")
    public ResponseEnvelope initServicePayOrderWithTest(@RequestBody @Valid ServicesPurchaseRenewals renewals,
                                                        BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, new ResponseEnvelope());
        }
        ServiceRecordInitBO bo = new ServiceRecordInitBO();
        BeanUtils.copyProperties(renewals, bo);
        //手动set
        bo.setPriceId(Long.valueOf(renewals.getPriceId()));
        bo.setAccountUserId(renewals.getAccountUserId());
        try {
            ServicesPurchaseRecord record = servicesPurchaseBizService.initServiceOrder(bo);

            if (record.getPurchaseMomey().setScale(0, ROUND_DOWN).doubleValue() > 0) {
                log.info("无法通过试用接口购买价格大于0的套餐!");
                return new ResponseEnvelope<>(true, "套餐数据异常,不能购买此套餐...");
            }
            servicesPurchaseBizService.syncServiceAccount(record.getOrderNo(), ServicesPurchaseRecord.RECORD_STATUS_SUCCESS);
        } catch (Exception e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        }
        return new ResponseEnvelope<>(true, "购买成功");
    }

    @RequiresPermissions({"manufacturer.user.service.renewal", "dealer.user.service.renewal", "company.user.service.renewal","user.manage.service.renewal"})
    @ApiOperation("三度--账号续费")
    @PostMapping("inner/renew")
    public ResponseEnvelope renewServiceForInner(@Valid @RequestBody InnerServiceRenew renew) {
        ServicesAccountRel servicesAccountRel;
        try {
            servicesAccountRel = servicesPurchaseBizService.renewServiceForInner(renew);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope<>(false, e.getMessage());
        }
        return new ResponseEnvelope<>(true, "购买成功", servicesAccountRel.getEffectiveEnd());
    }


    @RequiresPermissions({"service.manage.account"})
    @ApiOperation("获取套餐内所有用户,成员账号")
    @GetMapping("/user/list")
    public ResponseEnvelope<ServiceInnerUserListVO> listUserWithServiceId(ServiceInnerUserQuery query
            , BindingResult validResult) {
        ResponseEnvelope<ServiceInnerUserListVO> result = new ResponseEnvelope<>();
        if (validResult.hasErrors()) {
            super.processValidError(validResult, result);
        }

        PageInfo<ServicesAccountRel> page = servicesAccountRelService.queryByOptionForPage(query);
        if (page.getList().isEmpty()) {
            result.setDatalist(Collections.emptyList());
            result.setTotalCount(page.getTotal());
            result.setMessage("没有数据了");
            return result;
        }

        List<ServiceInnerUserListVO> list = setViewListVO(page);

        result.setDatalist(list);
        result.setTotalCount(page.getTotal());
        return result;
    }


    private <S, P, R, K, V> Map<K, V> getMap(List<S> list,
                                             Function<S, P> filedFun,
                                             Collector<P, ?, List<P>> collectType,
                                             Function<List<P>, List<R>> transFun,
                                             Function<? super R, ? extends K> keyMapper,
                                             Function<? super R, ? extends V> valueMapper) {
        return list.stream()
                .map(filedFun)
                .collect(Collectors.collectingAndThen(collectType, transFun))
                .stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    private List<ServiceInnerUserListVO> setViewListVO(PageInfo<ServicesAccountRel> page) {


        Map<String, SysUser> nickName2User = page.getList().stream()
                .map(ServicesAccountRel::getAccount)
                .collect(Collectors.collectingAndThen(Collectors.toList(), sysUserService::getUserByNickName))
                .stream()
                .collect(Collectors.toMap(SysUser::getNickName, o -> o));

        Map<Long, String> companyId2Name = page.getList().stream()
                .map(ServicesAccountRel::getCompanyId)
                .map(Integer::valueOf)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), baseCompanyService::listByIds))
                .stream()
                .collect(Collectors.toMap(BaseCompany::getId, BaseCompany::getCompanyName));

        return page.getList()
                .stream()
                .map(it -> {
                    ServiceInnerUserListVO vo = ServiceInnerUserListVO.builder()
                            .user(nickName2User.get(it.getAccount()))
                            .loginName(it.getAccount())
                            .expireTime(it.getEffectiveEnd())
                            .companyName(companyId2Name.get(Long.valueOf(it.getCompanyId())))
                            .build();
					if (vo.getUser() != null) {
						vo.setMobile(vo.getUser().getMobile());
						vo.setNickName(vo.getUser().getUserName());
					}
                    return vo;
                }).collect(Collectors.toList());
    }
}
