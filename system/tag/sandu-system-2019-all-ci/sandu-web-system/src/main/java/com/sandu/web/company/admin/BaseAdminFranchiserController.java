package com.sandu.web.company.admin;

import com.google.common.base.Splitter;
import com.sandu.api.company.input.BaseFranchiserNew;
import com.sandu.api.company.input.BaseFranchiserQuery;
import com.sandu.api.company.input.BaseFranchiserUpdate;
import com.sandu.api.company.model.bo.ItemVO;
import com.sandu.api.company.output.AdminFranchiserDetailsVO;
import com.sandu.api.company.output.AdminFranchiserVO;
import com.sandu.api.company.service.BaseAdminFranchiserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@Api(tags = "baseAdminFranchiser", description = "新三度后台企业经销商管理")
@RestController
@RequestMapping("/v1/base/admin/franchiser")
public class BaseAdminFranchiserController extends BaseController {

    @Resource
    BaseAdminFranchiserService baseAdminFranchiserService;

    /**
     * 经销商列表
     *
     * @param query
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("dealer.manage.view")
    @ApiOperation(value = "经销商企业列表", response = AdminFranchiserVO.class)
    public ResponseEnvelope<AdminFranchiserVO> listFranchiser(BaseFranchiserQuery query) {
        if (query.getPid() == null || query.getPid() <= 0) {
            log.error("经销商列表：参数错误：pid不能为空！query => {}", query);
            return new ResponseEnvelope<>(false, "参数错误：pid不能为空！");
        }

        // 行业类型
        if (!StringUtils.isEmpty(query.getIndustry())) {
            query.setCompanyIndustries(Splitter.on(",").trimResults().splitToList(query.getIndustry()));
        }

        query.setPage(query.getPage() == null ? 0 : query.getPage());
        query.setLimit(query.getLimit() == null ? 10 : query.getLimit());

        return baseAdminFranchiserService.listFranchiser(query);
    }

    /**
     * 获取经销商详情
     *
     * @param companyId
     * @return
     */
    @GetMapping("/get")
    @RequiresPermissions("dealer.manage.view")
    @ApiOperation(value = "经销商企业详情", response = AdminFranchiserDetailsVO.class)
    public ResponseEnvelope<AdminFranchiserDetailsVO> getFranchiser(Long companyId) {
        if (companyId == null) {
            log.error("获取经销商：参数错误：companyId不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：companyId 不能为空！");
        }

        return baseAdminFranchiserService.getFranchiser(companyId);
    }

    /**
     * 添加经销商
     *
     * @param franchiserNew
     * @param validResult
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("dealer.manage.add")
    @ApiOperation(value = "添加经销商企业")
    public ResponseEnvelope addFranchiser(@Valid @RequestBody BaseFranchiserNew franchiserNew,
                                          BindingResult validResult) {
        if (validResult.hasErrors()) {
            log.error("添加经销商：未通过参数验证，franchiserNew => {}, errors = > {}", franchiserNew, validResult.getAllErrors());
            return processValidError(validResult, new ResponseEnvelope());
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("添加经销商：未登录！！！");
            return new ResponseEnvelope<>(false, "未登录，请先登录后再操作！");
        }

        return baseAdminFranchiserService.addFranchiser(franchiserNew, loginUser);
    }

    /**
     * 更新经销商
     *
     * @param update
     * @param validResult
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("dealer.manage.update")
    @ApiOperation(value = "更新经销商企业")
    public ResponseEnvelope updateFranchiser(@Valid @RequestBody BaseFranchiserUpdate update, BindingResult validResult) {
        //1.数据校验
        if (validResult.hasErrors()) {
            log.error("更新经销商：未通过参数验证，update => {}, errors = > {}", update, validResult.getAllErrors());
            return processValidError(validResult, new ResponseEnvelope());
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("更新经销商：未登录！！！");
            return new ResponseEnvelope<>(false, "未登录，请先登录后再操作！");
        }

        return baseAdminFranchiserService.updateFranchiser(update, loginUser);
    }

    /**
     * 删除经销商
     *
     * @param companyId
     * @return
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("dealer.manage.del")
    @ApiOperation(value = "删除经销商企业")
    public ResponseEnvelope deleteFranchiser(Long companyId) {
        if (companyId == null) {
            log.error("删除经销商：参数错误：companyId不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：companyId 不能为空！");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("删除经销商：未登录！！！");
            return new ResponseEnvelope<>(false, "未登录，请先登录后再操作！");
        }

        try {
            return baseAdminFranchiserService.deleteFranchiser(companyId, loginUser);
        } catch (Exception e) {
            log.error("删除经销商：删除异常", e);                    
        }

        return new ResponseEnvelope<>(false, "系统错误，请稍后再试！") ;
    }

    /**
     * 厂商的行业列表
     *
     * @param pid
     * @return
     */
    @GetMapping("/industry/list")
    @ApiOperation(value = "厂商企业行业列表")
    public ResponseEnvelope<ItemVO> listIndustry(Long pid) {
        if (pid == null) {
            log.error("行业类别：参数错误：pid不能为空！");
            return new ResponseEnvelope<>(false, "参数错误：pid 不能为空！");
        }

        return baseAdminFranchiserService.listIndustry(pid);
    }
}
