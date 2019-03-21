package com.sandu.web.user.controller;

import com.sandu.api.account.service.PayAccountService;
import com.sandu.api.income.model.CompanyDesignPlanIncome;
import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;
import com.sandu.api.income.model.CompanyDesignPlanIncomeTransferRecord;
import com.sandu.api.income.model.CompanyDesignPlanIncomeWithdrawRecord;
import com.sandu.api.income.service.CompanyDesignPlanIncomeAggregatedService;
import com.sandu.api.income.service.CompanyDesignPlanIncomeService;
import com.sandu.api.income.service.CompanyDesignPlanIncomeTransferRecordService;
import com.sandu.api.income.service.CompanyDesignPlanIncomeWithdrawRecordService;
import com.sandu.api.system.output.IsAllowDrawingsResultDTO;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.service.SysRoleGroupService;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.common.util.ExcelUtil;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.Utils;
import com.sandu.config.SystemConfig;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1/income")
public class MyIncomeComtroller {

    private static final Logger logger = LoggerFactory.getLogger(MyIncomeComtroller.class);

    @Autowired
    private SysRoleGroupService sysRoleGroupService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private CompanyDesignPlanIncomeAggregatedService companyDesignPlanIncomeAggregatedService;

    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;

    @Autowired
    private PayAccountService payAccountService;

    @Autowired
    private CompanyDesignPlanIncomeTransferRecordService companyDesignPlanIncomeTransferRecordService;

    @Autowired
    private CompanyDesignPlanIncomeWithdrawRecordService companyDesignPlanIncomeWithdrawRecordService;

    @Value(value = "${company.income.role.dictionary.type}")
    private String ROLEDICTIONARYTYPE;

    @ApiOperation(value = "获取公司收益情况")
    @GetMapping(value = "/getCompanyAggregated")
    public Object getCompanyIncomeAggregated(Long companyId) {

        /*List<SysDictionary> listByType = sysDictionaryService.getListByType(ROLEDICTIONARYTYPE);

        if (Objects.isNull(listByType) || listByType.isEmpty()) {
            return new ResponseEnvelope<>(false, "请配置公司收益角色权限");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //校验权限
        Boolean vaildRole = this.validRole(loginUser.getId(), listByType.stream().findFirst().get().getValuekey());

        if (!vaildRole) {
            return new ResponseEnvelope<>(false, "您的权限不足,请联系管理员");
        }*/
        try {
            List<CompanyDesignPlanIncomeAggregated> lists = companyDesignPlanIncomeAggregatedService.getAggrgatedByCompanyId(companyId);
            return new ResponseEnvelope<>(true, lists);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
    }

    @ApiOperation(value = "我的收益列表")
    @PostMapping(value = "/companyIncomeList")
    public Object companyIncomeList(@RequestParam(name = "companyId", required = true) Long companyId, Long platformId, String planCreator,String planCode, Integer start, Integer limit) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (Objects.isNull(start) && Objects.isNull(limit)) {
            start = 0;
            limit = 10;
        } else {
            start = (start - 1) * 10;
        }

       /* List<SysDictionary> listByType = sysDictionaryService.getListByType(ROLEDICTIONARYTYPE);

        //校验权限
        Boolean vaildRole = this.validRole(loginUser.getId(), listByType.stream().findFirst().get().getValuekey());

        if (!vaildRole) {
            return new ResponseEnvelope<>(false, "您的权限不足,请联系管理员");
        }*/

        try {
            //统计我的收益列表
            int count = companyDesignPlanIncomeService.countCompanyIncome(companyId, platformId, planCode,planCreator);
            List<CompanyDesignPlanIncome> incomes = new ArrayList<>();
            if (count > 0) {
                incomes = companyDesignPlanIncomeService.selectCompanyIncomeList(companyId, platformId, planCode,planCreator, start, limit);
            }
            return new ResponseEnvelope<>(true, count, incomes);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
    }

    @ApiOperation(value = "转出度币")
    @PostMapping("/transferDubi")
    public Object transferDubi(@RequestParam(value = "userId", required = true) Long userId,
                               @RequestParam(value = "dubi", required = true) Double dubi,
                               @RequestParam(value = "companyId", required = true) Long companyId
    ) {
        if (Objects.isNull(userId)) {
            return new ResponseEnvelope<>(false, "parameter userId is null");
        }

        if (Objects.isNull(dubi)) {
            return new ResponseEnvelope<>(false, "parameter dubi is null");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        /*Boolean vaildRole = validRole(loginUser.getId(), "");

        if (!vaildRole) {
            return new ResponseEnvelope<>(false, "您没有转出度币的权限,请联系管理员");
        }*/

        try {

            //校验用户是否有足够的度币转出
            boolean enough =  companyDesignPlanIncomeAggregatedService.checkCurrentDubiEnough(dubi,companyId);

            if (!enough){
                return new ResponseEnvelope<>(false,"您的当前余额不足");
            }

            //插入转出记录
            companyDesignPlanIncomeTransferRecordService.addTransferRecord(loginUser.getId(), userId, dubi);

            //更新公司收益信息
            companyDesignPlanIncomeAggregatedService.updateTransferDubi(companyId, dubi);

            //用户增加度币
            payAccountService.handlerUserDubiInfo(userId, dubi);

        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(true, "转出成功");
    }

    @ApiOperation("提现度币")
    @PostMapping(value = "/withdrawDubi")
    public Object withdrawDubi(@RequestParam(value = "companyId", required = true) Long companyId,
                               @RequestParam(value = "withdrawType", required = true) Integer withdrawType,
                               @RequestParam(value = "dubi", required = true) Double dubi,
                               @RequestParam(value = "bankcardInfoId", required = true) Long bankcardInfoId,
                               @RequestParam(value = "mobile", required = true) String mobile
    ) {

        if (Objects.isNull(companyId)) {
            return new ResponseEnvelope<>(false, "parameter companyId is null");
        }

        if (Objects.isNull(withdrawType)) {
            return new ResponseEnvelope<>(false, "parameter withdrawType is null");
        }

        if (Objects.isNull(dubi)) {
            return new ResponseEnvelope<>(false, "parameter dubi is null");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        /*Boolean vaildRole = validRole(loginUser.getId(), "");

        if (!vaildRole) {
            return new ResponseEnvelope<>(false, "您没有提现度币的权限,请联系管理员");
        }*/
        try {

        	// 检验该用户是否还有提现次数 add by huangsongbo 2018.12.15
        	IsAllowDrawingsResultDTO resultDTO = companyDesignPlanIncomeWithdrawRecordService.getIsAllowDrawingsResultDTO(loginUser.getId());
        	if(resultDTO != null && !resultDTO.isAllow()) {
        		return new ResponseEnvelope<>(false, resultDTO.getMessage());
        	}
        	
            //校验用户是否有足够的度币转出
            boolean enough =  companyDesignPlanIncomeAggregatedService.checkCurrentDubiEnough(dubi,companyId);

            if (!enough){
                return new ResponseEnvelope<>(false,"您的当前余额不足");
            }

            //插入提交申请记录
            companyDesignPlanIncomeWithdrawRecordService.addWithdrawRecord(companyId, loginUser.getId(), withdrawType, dubi, bankcardInfoId, mobile);

            //更新公司收益信息
            companyDesignPlanIncomeAggregatedService.updateWithdrawDubi(companyId, dubi);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(true, "提现成功");
    }

    @ApiOperation("用户提现记录列表")
    @PostMapping(value = "/withdrawRecordList")
    public Object companyWithdrawRecordList(@RequestParam(value = "companyId", required = true) Long companyId,
                                                    Integer withdrawType, String applyUser, Integer withdrawStatus,
                                                    Integer start, Integer limit
    ) {
        if (Objects.isNull(companyId)) {
            return new ResponseEnvelope<>(false, "parameter companyId is null");
        }

        if (Objects.isNull(start) && Objects.isNull(limit)) {
            start = 0;
            limit = 10;
        } else {
            start = (start - 1) * 10;
        }

        /*LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        Boolean vaildRole = validRole(loginUser.getId(), "");

        if (!vaildRole){
            return new ResponseEnvelope<>(false,"您没有查看提现记录的权限");
        }*/

        try {
            List<CompanyDesignPlanIncomeWithdrawRecord> withdrawRecords = new ArrayList<>();
            int total = companyDesignPlanIncomeWithdrawRecordService.countWithdrawList(companyId, withdrawType, applyUser, withdrawStatus);
            if (total > 0) {
                withdrawRecords = companyDesignPlanIncomeWithdrawRecordService.selectWithdrawList(companyId, withdrawType, applyUser, withdrawStatus, start, limit);
            }
            return new ResponseEnvelope<>(true, total, withdrawRecords);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
    }

    @ApiOperation("公司度币转出列表")
    @PostMapping(value = "/transferRecordList")
    public Object companyTransferRecordList(@RequestParam(value = "companyId", required = true) Long companyId,String transferUserName,Integer start,Integer limit){

        if (Objects.isNull(start) && Objects.isNull(limit)) {
            start = 0;
            limit = 10;
        } else {
            start = (start - 1) * 10;
        }

        /*LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        Boolean vaildRole = validRole(loginUser.getId(), "");

        if (!vaildRole){
            return new ResponseEnvelope<>(false,"您没有查看提现记录的权限");
        }*/

        try {
            int total = companyDesignPlanIncomeTransferRecordService.countTransferRecordList(companyId,transferUserName);
            List<CompanyDesignPlanIncomeTransferRecord> transferRecords = new ArrayList<>();
            if (total > 0){
               transferRecords =  companyDesignPlanIncomeTransferRecordService.selectTransferRecordList(companyId,transferUserName,start,limit);
            }
            return new ResponseEnvelope<>(true,total,transferRecords);
        } catch (Exception e) {
            logger.error("系统错误",e);
            return new ResponseEnvelope<>(false,"系统错误");
        }
    }

    public boolean checkUserRole(Set<Long> roleIds, String roleCode) {
        if (Objects.nonNull(roleIds) && !roleIds.isEmpty()) {
            //获取角色组下的角色
            List<SysRole> sysRoles = sysRoleService.getRolesByRoleIds(roleIds);
            Set<String> collect = sysRoles.stream().map(SysRole::getCode).collect(Collectors.toSet());
            if (collect.contains(roleCode)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 是否允许提现
     * 
     * @return
     */
    @GetMapping("/isAllowDrawings")
    public Object isAllowDrawings() {
		// ------参数/权限验证 ->start
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null && SystemConfig.DEBUGMODEL) {
        	loginUser = Utils.getDebugUser(request);
		}
		if(loginUser == null || loginUser.getId() == null) {
			return new ResponseEnvelope<>(false, "未检测到登录信息, 请重新登录");
		}
		// ------参数/权限验证 ->end
		
		IsAllowDrawingsResultDTO resultDTO = companyDesignPlanIncomeWithdrawRecordService.getIsAllowDrawingsResultDTO(loginUser.getId());
		return new ResponseEnvelope<>(true, resultDTO);
    }

    @GetMapping("/export")
    public void exportExcel(@RequestParam Map<String, String> map, HttpServletResponse response) {
        if (Objects.isNull(map.get("companyId")) || !StringUtils.isNumeric(map.get("companyId"))) {
            logger.warn("导出EXCEL失败，失败原因：companyId is null");
            return;
        }
        if (map.get("exportType") == null) {
            logger.warn("导出EXCEL失败，失败原因：exportType is null");
            return;
        }
        long companyId = Long.parseLong(map.get("companyId"));
        HSSFWorkbook excel = this.getExcel(map, companyId);
        if (excel != null) {
            try {
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String("excel.xls".getBytes(), "iso-8859-1"));
                OutputStream out = response.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(out);
                excel.write(bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                logger.error("导出EXCEL失败，失败原因：IOException", e);
                return;
            }
        }
    }
    
    private Boolean validRole(Long userId, String value) {
        //获取用户的角色组
        Set<SysUserRoleGroup> userRoleGroup = sysRoleGroupService.getUserRoleGroupByUserId(userId);
        if (Objects.nonNull(userRoleGroup) && !userRoleGroup.isEmpty()) {
            //获取用户的角色组
            Set<Long> roleGroupIds = userRoleGroup.stream().map(SysUserRoleGroup::getRoleGroupId).collect(Collectors.toSet());
            Set<Long> roleIds = sysRoleService.batchRoleByRoleRroupIds(roleGroupIds);
            boolean roleVaild = checkUserRole(roleIds, value);
            //false时先不返回
            if (roleVaild) {
                return roleVaild;
            }
        }
        //用户角色关联校验
        Set<SysUserRole> userRoles = sysRoleService.getUserRolesByUserId(userId);
        Set<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        return checkUserRole(roleIds, value);
    }

    private HSSFWorkbook getExcel(Map<String, String> map, long companyId) {
        switch (map.get("exportType")) {
            case "companyIncomeList":
                int count = companyDesignPlanIncomeService.countCompanyIncome(companyId,
                        Objects.isNull(map.get("platformId")) || !StringUtils.isNumeric(map.get("platformId"))? null : Long.parseLong(map.get("platformId")),
                        map.get("planCode"),
                        map.get("planCreator"));
                List<CompanyDesignPlanIncome> incomes = companyDesignPlanIncomeService.selectCompanyIncomeList(companyId,
                        Objects.isNull(map.get("platformId")) || !StringUtils.isNumeric(map.get("platformId"))? null : Long.parseLong(map.get("platformId")),
                        map.get("planCode"),
                        map.get("planCreator"),
                        0,
                        count);
                return ExcelUtil.createExcel(incomes);
            case "withdrawRecordList":
                count = companyDesignPlanIncomeWithdrawRecordService.countWithdrawList(companyId,
                        Objects.isNull(map.get("withdrawType")) || !StringUtils.isNumeric(map.get("withdrawType"))? null : Integer.parseInt(map.get("withdrawType")),
                        map.get("applyUser"),
                        Objects.isNull(map.get("withdrawStatus")) || !StringUtils.isNumeric(map.get("withdrawStatus"))? null : Integer.parseInt(map.get("withdrawStatus")));
                List<CompanyDesignPlanIncomeWithdrawRecord> withdrawRecords = companyDesignPlanIncomeWithdrawRecordService.selectWithdrawList(companyId,
                        Objects.isNull(map.get("withdrawType")) || !StringUtils.isNumeric(map.get("withdrawType"))? null : Integer.parseInt(map.get("withdrawType")),
                        map.get("applyUser"),
                        Objects.isNull(map.get("withdrawStatus")) || !StringUtils.isNumeric(map.get("withdrawStatus"))? null : Integer.parseInt(map.get("withdrawStatus")),
                        0,
                        count);
                return ExcelUtil.createExcel(withdrawRecords);
            case "transferRecordList":
                count = companyDesignPlanIncomeTransferRecordService.countTransferRecordList(companyId, map.get("transferUserName"));
                List<CompanyDesignPlanIncomeTransferRecord> transferRecords = companyDesignPlanIncomeTransferRecordService.selectTransferRecordList(companyId,
                        map.get("transferUserName"),
                        0,
                        count);
                return ExcelUtil.createExcel(transferRecords);
            default:
                return null;
        }
    }
}
