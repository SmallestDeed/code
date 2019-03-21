package com.sandu.web.customer.controller;


import static com.sandu.common.ReturnData.builder;
import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.PARAM_ERROR;
import static com.sandu.constant.ResponseEnum.SUCCESS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.output.CompanyVO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.customer.constant.CustomerBaseInfoConstant;
import com.sandu.api.customer.input.CustomerAlotZoneAdd;
import com.sandu.api.customer.input.CustomerBaseInfoVO;
import com.sandu.api.customer.input.FollowVO;
import com.sandu.api.customer.input.ReallocateVO;
import com.sandu.api.customer.input.query.CustomerAlotZoneQuery;
import com.sandu.api.customer.input.query.CustomerBaseInfoQuery;
import com.sandu.api.customer.input.query.CustomerFollowLogQuery;
import com.sandu.api.customer.model.AllCode;
import com.sandu.api.customer.model.CustomerAlotZone;
import com.sandu.api.customer.model.CustomerBaseInfo;
import com.sandu.api.customer.model.CustomerFollowLog;
import com.sandu.api.customer.output.CompanyUserVO;
import com.sandu.api.customer.output.CustomerAlotZoneVO;
import com.sandu.api.customer.output.CustomerVO;
import com.sandu.api.customer.output.FollowUpVO;
import com.sandu.api.customer.output.PageVO;
import com.sandu.api.customer.service.CustomerAlotZoneService;
import com.sandu.api.customer.service.CustomerBaseInfoService;
import com.sandu.api.customer.service.CustomerFollowLogService;
import com.sandu.api.user.model.User;
import com.sandu.api.user.model.bo.UserBo;
import com.sandu.api.user.service.UserService;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
import com.sandu.common.ReturnData;
import com.sandu.constant.ResponseEnum;
import com.sandu.exception.ServiceException;
import com.sandu.util.CopyUtil;
import com.sandu.util.excel.ExportExcel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sandu-lipeiyuan
 */
@Api(description = "业主管理", tags = "customer")
@RestController
@RequestMapping("/v1/customer")
@Slf4j
public class CustomerController extends BaseController {

    @Resource
    private CustomerBaseInfoService  customerBaseInfoService;
    @Resource
    private UserService              userService;
    @Resource
    private CompanyService           companyService;
    @Resource
    private BaseCompanyService       baseCompanyService;
    @Resource
    private CustomerFollowLogService customerFollowLogService;
    @Autowired
    private CustomerAlotZoneService  customerAlotZoneService;

    /**
     * 业主管理列表
     *
     * @param customerBaseInfoVO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "业主管理-列表", response = CompanyVO.class)
    public ReturnData list(CustomerBaseInfoVO customerBaseInfoVO) {

        log.info("业主管理列表-入参,customerBaseInfoVO:{}", customerBaseInfoVO);
        //返回参数
        ReturnData data = ReturnData.builder();
        //处理页码
        if (customerBaseInfoVO == null) {
            return data.code(ResponseEnum.PARAM_ERROR).success(false).message("请传入页码");
        }
        //处理页码
        if (customerBaseInfoVO.getStart() == null || customerBaseInfoVO.getStart() <= 1) {
            customerBaseInfoVO.setStart(1);
        }
        if (customerBaseInfoVO.getLimit() == null || customerBaseInfoVO.getLimit() <= 0) {
            customerBaseInfoVO.setLimit(20);
        }

        //获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("未登录");
            return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录");
        }
        log.info("业主管理列表-厂商-userId:{}", loginUser.getId());

        UserBo userBo = userService.getUserById(loginUser.getId());
        if (userBo == null) {
            log.error("未找到用户,userId:{}", loginUser.getId());
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("未找到用户");
        }
        log.info("业主管理列表-厂商-businessAdministrationId:{}", userBo.getBusinessAdministrationId());

        if (userBo.getBusinessAdministrationId() == null) {
            log.error("未找到用户企业,userId:{}", loginUser.getId());
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("未找到用户企业");
        }
        //判断是厂商还是经销商
        BaseCompany baseCompany = baseCompanyService.getCompanyById(userBo.getBusinessAdministrationId());
        if (baseCompany == null) {
            log.error("未找到用户企业,companyId:{}", userBo.getBusinessAdministrationId());
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("未找到用户企业");
        }
        CustomerBaseInfoQuery customerBaseInfoQuery = new CustomerBaseInfoQuery();
        customerBaseInfoQuery = (CustomerBaseInfoQuery) CopyUtil.copyProperties(customerBaseInfoQuery, customerBaseInfoVO);
        customerBaseInfoQuery.setIsDeleted(0);

        //厂商
        if (baseCompany.getBusinessType().equals(CustomerBaseInfoConstant.BUSINESS_TYPE_MANUFACTURER)) {
            customerBaseInfoQuery.setCompanyId(Math.toIntExact(baseCompany.getId()));
        }
        //经销商
        if (baseCompany.getBusinessType().equals(CustomerBaseInfoConstant.BUSINESS_TYPE_DEALER)) {
            customerBaseInfoQuery.setChannelCompanyId(Math.toIntExact(baseCompany.getId()));
        }
        
        if(customerBaseInfoQuery.getCompanyId()== null && customerBaseInfoQuery.getChannelCompanyId() == null) {
            log.error("非厂商、经销商、独立经销商用户,不能查看业主数据！");
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("非厂商、经销商用户,不能查看业主数据！");
        }
        
        return data.data(getCustomerVOPageVO(customerBaseInfoQuery, customerBaseInfoVO.getStart(), customerBaseInfoVO.getLimit())).code(ResponseEnum.SUCCESS).success(true);
    }

    /**
     * 我的业主-经销商列表
     *
     * @param customerBaseInfoVO
     * @return
     */
    @GetMapping("/distributor/list")
    @ApiOperation(value = "业主管理-列表", response = CompanyVO.class)
    public ReturnData distributorList(CustomerBaseInfoVO customerBaseInfoVO) {

        log.info("业主管理列表-入参,customerBaseInfoVO:{}", customerBaseInfoVO);
        //返回参数
        ReturnData data = ReturnData.builder();
        //处理页码
        if (customerBaseInfoVO == null) {
            return data.code(ResponseEnum.PARAM_ERROR).success(false).message("请传入页码");
        }
        //处理页码
        if (customerBaseInfoVO.getStart() == null || customerBaseInfoVO.getStart() <= 1) {
            customerBaseInfoVO.setStart(1);
        }
        if (customerBaseInfoVO.getLimit() == null || customerBaseInfoVO.getLimit() <= 0) {
            customerBaseInfoVO.setLimit(20);
        }

        //获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("未登录");
            return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录");
        }
        log.info("业主管理列表-厂商-userId:{}", loginUser.getId());

        UserBo userBo = userService.getUserById(loginUser.getId());
        if (userBo == null) {
            log.error("未找到用户,userId:{}", loginUser.getId());
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("未找到用户");
        }
        log.info("业主管理列表-厂商-businessAdministrationId:{}", userBo.getBusinessAdministrationId());

        if (userBo.getBusinessAdministrationId() == null) {
            log.error("未找到用户企业,userId:{}", loginUser.getId());
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("未找到用户企业");
        }

        CustomerBaseInfoQuery customerBaseInfoQuery = new CustomerBaseInfoQuery();
        customerBaseInfoQuery = (CustomerBaseInfoQuery) CopyUtil.copyProperties(customerBaseInfoQuery, customerBaseInfoVO);
        customerBaseInfoQuery.setIsDeleted(0);
        customerBaseInfoQuery.setChannelCompanyId(Math.toIntExact(userBo.getBusinessAdministrationId()));
        return data.data(getCustomerVOPageVO(customerBaseInfoQuery, customerBaseInfoVO.getStart(), customerBaseInfoVO.getLimit())).code(ResponseEnum.SUCCESS).success(true);

    }


    /**
     * 请求列表信息
     *
     * @param customerBaseInfoQuery
     * @return
     */
    private PageVO<CustomerVO> getCustomerVOPageVO(CustomerBaseInfoQuery customerBaseInfoQuery, Integer pageNo, Integer pageSize) {
        PageVO<CustomerVO> customerVOPageVO = new PageVO<>();
        //请求分页列表数据
        PageInfo<CustomerBaseInfo> customerBaseInfoPageInfo = customerBaseInfoService.pageSelectByOption(customerBaseInfoQuery
                , pageNo, pageSize);
        List<CustomerBaseInfo> customerBaseInfoList = customerBaseInfoPageInfo.getList();
        if (CollectionUtils.isEmpty(customerBaseInfoList)) {
            log.info("没有找到列表记录");
            return customerVOPageVO;
        }

        Set<Integer> userIdSet = new HashSet<>();
        Set<Integer> followUpUserIdSet = new HashSet<>();
        List<Integer> channelCompanyIds = new ArrayList<>();
        for (CustomerBaseInfo customerBaseInfo : customerBaseInfoList) {
            userIdSet.add(customerBaseInfo.getUserId());
            followUpUserIdSet.add(customerBaseInfo.getFollowUpUserId());
            if (customerBaseInfo.getChannelCompanyId() != null && !channelCompanyIds.contains(customerBaseInfo.getChannelCompanyId())) {
                channelCompanyIds.add(customerBaseInfo.getChannelCompanyId());
            }
        }

        //获取用户信息
        Map<Integer, User> userInfoMap = userService.selectByIdsToMap(userIdSet);
        if (MapUtils.isEmpty(userInfoMap)) {
            log.info("没有找到列表用户信息");
            return customerVOPageVO;
        }

        //获取跟进人名称
        Map<Integer, User> followUpUserIdMap = userService.selectByIdsToMap(followUpUserIdSet);

        //获取公司名称信息
        Map<Long, String> companyNameMap = companyService.idAndNameMap(channelCompanyIds);

        //组装返回对象
        List<CustomerVO> customerBaseInfoVOList = getCustomerVOS(customerBaseInfoList, userInfoMap, followUpUserIdMap, companyNameMap, null);
        customerVOPageVO.setList(customerBaseInfoVOList);
        customerVOPageVO.setTotal(customerBaseInfoPageInfo.getTotal());
        customerVOPageVO.setPageSize(customerBaseInfoPageInfo.getPageSize());
        customerVOPageVO.setPageNo(customerBaseInfoPageInfo.getPageNum());
        return customerVOPageVO;
    }


    /**
     * 业主详情
     *
     * @return
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "业主管理-厂商列表-业主详情", response = CompanyVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "业主详情id(列表返回的id)", paramType = "path", dataType = "Integer", required = true)
    })
    public ReturnData info(@PathVariable Integer id) {

        log.info("业主管理列表-查看详情-入参,id:{}", id);
        //返回参数
        ReturnData data = ReturnData.builder();
        CustomerVO customerVO = new CustomerVO();

        //请求数据
        CustomerBaseInfoQuery customerBaseInfoQuery = new CustomerBaseInfoQuery();
        customerBaseInfoQuery.setId(Long.valueOf(id));
        List<CustomerBaseInfo> customerBaseInfoList = customerBaseInfoService.selectByOption(customerBaseInfoQuery);
        if (CollectionUtils.isEmpty(customerBaseInfoList)) {
            return data.data(customerVO).code(ResponseEnum.SUCCESS).success(true);
        }

        //获取用户信息
        Set<Integer> userIdSet = customerBaseInfoList.stream().map(CustomerBaseInfo::getUserId).collect(Collectors.toSet());
        Map<Integer, User> userInfoMap = userService.selectByIdsToMap(userIdSet);
        if (MapUtils.isEmpty(userInfoMap)) {
            log.info("业主管理-厂商列表-业主详情,没有找到用户");
            return data.data(customerVO).code(ResponseEnum.SUCCESS).success(true);
        }


        //获取公司名称信息
        List<Integer> companyIds = customerBaseInfoList.stream().map(CustomerBaseInfo::getChannelCompanyId).distinct().collect(Collectors.toList());
        Map<Long, String> companyNameMap = companyService.idAndNameMap(companyIds);

        //查询跟进结果
        CustomerFollowLogQuery customerFollowLogQuery = new CustomerFollowLogQuery();
        customerFollowLogQuery.setUserIds(userIdSet);
        List<CustomerFollowLog> customerFollowLogList = customerFollowLogService.selectByOption(customerFollowLogQuery);

        Map<Integer, String> resultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(customerBaseInfoList)) {
            for (CustomerFollowLog customerFollowLog : customerFollowLogList) {
                resultMap.put(customerFollowLog.getUserId(), customerFollowLog.getFollowUpResult());
            }
        }

        //获取分配人名称
        Set<Integer> followUpUserIdSet = customerBaseInfoList.stream().map(CustomerBaseInfo::getFollowUpUserId).collect(Collectors.toSet());
        //获取跟进人名称
        Map<Integer, User> followUpUserIdMap = userService.selectByIdsToMap(followUpUserIdSet);

        //组装返回对象
        List<CustomerVO> customerBaseInfoVOList = getCustomerVOS(customerBaseInfoList, userInfoMap, followUpUserIdMap, companyNameMap, resultMap);

        return data.data(customerBaseInfoVOList.get(0)).code(ResponseEnum.SUCCESS).success(true);
    }

    /**
     * 重新分配提交
     *
     * @return
     */
    @PostMapping("/reallocate")
    @ApiOperation(value = "业主管理-厂商列表-重新分配提交", response = CompanyVO.class)
    public ReturnData reallocate(@RequestBody ReallocateVO reallocateVO) {
        log.info("业主管理列表-重新分配提交-入参,ReallocateVO:{}", reallocateVO);
        //返回参数
        ReturnData data = ReturnData.builder();
        //获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("未登录");
            return data.code(ResponseEnum.ERROR).success(false).message("用户未登录");
        }
        if (reallocateVO == null || reallocateVO.getId() == null || reallocateVO.getCompanyId() == null) {
            return data.code(ResponseEnum.PARAM_ERROR).success(false).message("参数错误！");
        }
        //权限控制,厂商才能分配，如果是厂商返回当前用户companyId
        Integer opCompanyId = this.getCompanyId(CustomerBaseInfoConstant.BUSINESS_TYPE_MANUFACTURER, loginUser);
        log.info("业主管理列表-厂商-CompanyId:{}", opCompanyId);
        if (opCompanyId == null) {
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("未查询到业主所属的厂商信息");
        }
        //校验经销商是否是这个厂商的
        List<Company> companyList = companyService.queryDealerByPid(opCompanyId);
        if (CollectionUtils.isEmpty(companyList)) {
            return data.code(ResponseEnum.FORBIDDEN).success(false).message("该厂商下没有经销商记录，不能分配");
        }
        Set<Long> companyIdSet = companyList.stream().map(Company::getId).collect(Collectors.toSet());
        //判断经销商是否是当前厂商下的
        Long loginUserCompanyId = Long.valueOf(reallocateVO.getCompanyId());
        if (!companyIdSet.contains(loginUserCompanyId)) {
            return data.code(ResponseEnum.FORBIDDEN).success(false).message("选中业主跟当前登录用户厂商信息不一致,不能重新分配!");
        }

        //校验是否可以重新分配
        CustomerBaseInfo customerBaseInfo = customerBaseInfoService.selectByPrimaryKey(reallocateVO.getId());
        if (customerBaseInfo == null) {
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("没有找到这条记录，请刷新后重试！");
        }

        //只有已分配和跟进中的业主可以重新分配
        if (!customerBaseInfo.getAlotStatus().equals(CustomerBaseInfoConstant.ALOT_STATUS_ALLOCATED) &&
                !customerBaseInfo.getAlotStatus().equals(CustomerBaseInfoConstant.ALOT_STATUS_FOLLOW)) {
            return data.code(ResponseEnum.FORBIDDEN).success(false).message("只有已分配和跟进中的业主才能进行重新分配！");
        }

        //更新
        customerBaseInfo.setChannelCompanyId(reallocateVO.getCompanyId());
        customerBaseInfo.setAlotTime(LocalDateTime.now());
        customerBaseInfo.setAlotType(CustomerBaseInfoConstant.ALOT_TYPE_HAND);
        customerBaseInfo.setAlotUserId(loginUser.getId());
        int update = customerBaseInfoService.updateByPrimaryKeySelective(customerBaseInfo);
        if (update > 0) {
            return data.code(ResponseEnum.SUCCESS).success(true);
        }

        return data.code(ResponseEnum.ERROR).success(false);
    }


    /**
     * 重新分配-厂商所属的经销商列表下拉框
     *
     * @return
     */
    @GetMapping("/reallocateList")
    @ApiOperation(value = "业主管理-厂商列表-重新分配-厂商所属的经销商列表下拉框", response = CompanyVO.class)
    public ReturnData reallocateList() {

        //返回参数
        ReturnData data = ReturnData.builder();
        List<CompanyUserVO> companyUserVOList = new ArrayList<>();
        //获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("未登录");
            return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录");
        }
        User user = userService.selectById(Long.valueOf(loginUser.getId()));
        if (user == null) {
            return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("登录用户不存在");
        }

        List<Company> companyList = companyService.queryDealerByPid(user.getBusinessAdministrationId());
        if (CollectionUtils.isEmpty(companyList)) {
            log.info("重新分配-厂商所属的经销商列表下拉框,没有找到数据");
            return data.data(companyUserVOList).code(ResponseEnum.SUCCESS).success(true);
        }

        CompanyUserVO companyUserVO;
        for (Company company : companyList) {
            companyUserVO = new CompanyUserVO();
            companyUserVO.setId(Math.toIntExact(company.getId()));
            companyUserVO.setName(company.getCompanyName());
            companyUserVOList.add(companyUserVO);
        }
        return data.data(companyUserVOList).code(ResponseEnum.SUCCESS).success(true);
    }


    /**
     * 跟进提交(需求是对一条记录进行编辑）（包括失效和跟进完成）
     *
     * @return
     */
    @PostMapping("/followIn/")
    @ApiOperation(value = "业主管理-经销商列表-跟进提交", response = CompanyVO.class)
    public ReturnData followIn(@Valid @RequestBody FollowVO followVO) {

        log.info("业主管理-经销商列表-跟进提交入参,followVO:{}", followVO);
        ReturnData data = ReturnData.builder();
        // 校验参数
        if (checkParam(followVO)) {
            return data.code(ResponseEnum.PARAM_ERROR).success(false).message("参数错误！");
        }

        //校验状态参数
        if (!followVO.getAlotStatus().equals(CustomerBaseInfoConstant.ALOT_STATUS_FOLLOW) &&
                !followVO.getAlotStatus().equals(CustomerBaseInfoConstant.ALOT_STATUS_COMPLETE) &&
                !followVO.getAlotStatus().equals(CustomerBaseInfoConstant.ALOT_STATUS_INVAIN)) {
            return data.code(ResponseEnum.PARAM_ERROR).success(false).message("跟进状态错误！");
        }

        //获取用户信息(跟进人)
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("未登录");
            return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录！");
        }

        //校验业主状态是否能跟进
        CustomerBaseInfoQuery customerBaseInfoQuery = new CustomerBaseInfoQuery();
        customerBaseInfoQuery.setUserId(followVO.getFollowUserId());
        List<CustomerBaseInfo> customerBaseInfoList = customerBaseInfoService.selectByOption(customerBaseInfoQuery);
        if (CollectionUtils.isEmpty(customerBaseInfoList)) {
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("没找到该用户！");
        }
        Integer alotStatus = customerBaseInfoList.get(0).getAlotStatus();
        if (!alotStatus.equals(CustomerBaseInfoConstant.ALOT_STATUS_ALLOCATED) &&
                !alotStatus.equals(CustomerBaseInfoConstant.ALOT_STATUS_FOLLOW)) {
            return data.code(ResponseEnum.FORBIDDEN).success(false).message("该业主状态无法跟进！");
        }
        //校验是否已有人跟进
        customerBaseInfoQuery.setFollowUpUserId(customerBaseInfoList.get(0).getFollowUpUserId());
        if (customerBaseInfoQuery.getFollowUpUserId() != null && !loginUser.getId().equals(customerBaseInfoQuery.getFollowUpUserId())) {
            return data.code(ResponseEnum.FORBIDDEN).success(false).message("该业主已有人跟进！");
        }

        //跟进记录（需要回写customerBaseInfo跟进状态）
        CustomerFollowLog customerFollowLog = customerFollowLogService.selectByPrimaryKey(followVO.getId());
        if (customerFollowLog == null) {
            customerFollowLog = getCustomerFollowLog(followVO, loginUser);
            int insert = customerFollowLogService.complete(customerFollowLog, followVO.getAlotStatus(), loginUser.getId());
            if (insert > 0) {
                return data.code(ResponseEnum.SUCCESS).success(true).message("操作成功！");
            }
            return data.code(ResponseEnum.ERROR).success(false).message("操作失败！");
        }
        //修改跟进人
        customerFollowLog.setFollowUpResult(followVO.getFollowUpResult());
        customerFollowLog.setModifier(loginUser.getLoginName());
        customerFollowLog.setGmtModified(LocalDateTime.now());
        int update = customerFollowLogService.updateByPrimaryKeySelective(customerFollowLog, loginUser.getId(), followVO.getAlotStatus());
        if (update > 0) {
            return data.code(ResponseEnum.SUCCESS).success(true).message("操作成功！");
        }
        return data.code(ResponseEnum.ERROR).success(false).message("操作失败！");
    }

    /**
     * 跟进记录
     *
     * @param followVO
     * @param loginUser
     * @return
     */
    private CustomerFollowLog getCustomerFollowLog(FollowVO followVO, LoginUser loginUser) {
        CustomerFollowLog customerFollowLog = new CustomerFollowLog();
        customerFollowLog.setUserId(followVO.getFollowUserId());
        customerFollowLog.setFollowUpResult(followVO.getFollowUpResult());
        customerFollowLog.setCompanyId(followVO.getCompanyId());
        customerFollowLog.setFollowUpUserId(loginUser.getId());
        customerFollowLog.setCreator(loginUser.getLoginName());
        customerFollowLog.setGmtCreate(LocalDateTime.now());
        customerFollowLog.setModifier(loginUser.getLoginName());
        customerFollowLog.setGmtModified(LocalDateTime.now());
        customerFollowLog.setIsDeleted(0);
        return customerFollowLog;
    }

    /**
     * 参数校验
     *
     * @param followVO
     * @return
     */
    private boolean checkParam(@Valid @RequestBody FollowVO followVO) {
        if (followVO == null) {
            return true;
        }
        if (followVO.getCompanyId() == null) {
            return true;
        }
        if (followVO.getId() == null) {
            return true;
        }
        if (followVO.getFollowUserId() == null) {
            return true;
        }
        if (followVO.getAlotStatus() == null) {
            return true;
        }
        return false;
    }


    /**
     * 跟进详情(需求是对一条记录进行编辑）
     *
     * @return
     */
    @GetMapping("/followUp/{id}")
    @ApiOperation(value = "业主管理-经销商列表-跟进详情", response = CompanyVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "业主详情id(列表返回的id)", paramType = "path", dataType = "Integer", required = true)

    })
    public ReturnData followUp(@PathVariable Integer id) {

        log.info("业主管理列表-跟进详情-入参,id:{}", id);
        //返回参数
        ReturnData data = ReturnData.builder();
        FollowUpVO followUpVO = new FollowUpVO();
        //获取业主状态信息
        CustomerBaseInfo customerBaseInfo = customerBaseInfoService.selectByPrimaryKey(Long.valueOf(id));
        if (customerBaseInfo == null) {
            return data.code(ResponseEnum.NOT_FOUND).success(false).message("没有找到此业主，请刷新后重试！");
        }
        followUpVO.setAlotStatus(customerBaseInfo.getAlotStatus());
        followUpVO.setAlotStatusName(getAlotStatusName(followUpVO.getAlotStatus()));

        //有手机号取手机号没有手机号取微信号
        String userInfo = customerBaseInfo.getMobile();
        followUpVO.setUserInfo(userInfo);
        if (customerBaseInfo.getMobile() == null) {
            //获取用户信息
            User user = userService.selectById(Long.valueOf(customerBaseInfo.getUserId()));
            if (user == null) {
                return data.code(ResponseEnum.NOT_FOUND).success(false).message("用户没有绑定手机号或微信号，不能跟进！");
            }
            userInfo = user.getOpenId();
            followUpVO.setUserInfo(userInfo);
        }

        //跟进记录
        CustomerFollowLogQuery customerFollowLogQuery = new CustomerFollowLogQuery();
        customerFollowLogQuery.setUserId(customerBaseInfo.getUserId());
        List<CustomerFollowLog> customerFollowLogList = customerFollowLogService.selectByOption(customerFollowLogQuery);
        if (CollectionUtils.isEmpty(customerFollowLogList)) {
            // 第一次跟进
            followUpVO.setId(Long.valueOf(0));
            return data.data(followUpVO).code(ResponseEnum.SUCCESS).success(true);
        }
        CustomerFollowLog customerFollowLog = customerFollowLogList.get(0);
        followUpVO.setId(customerFollowLog.getId());
        followUpVO.setFollowUpResult(customerFollowLog.getFollowUpResult());
        return data.data(followUpVO).code(ResponseEnum.SUCCESS).success(true);
    }

    /**
     * 跟进状态获取状态名称
     *
     * @param alotStatus
     * @return
     */
    private String getAlotStatusName(Integer alotStatus) {
        switch (alotStatus) {
            case 0:
                return "未分配";
            case 1:
                return "已分配";
            case 2:
                return "跟进中";
            case 3:
                return "跟进完成";
            case 4:
                return "无效";
            default:
                break;
        }
        return null;
    }


    /**
     * 获取企业内部用户
     *
     * @return
     */
    @GetMapping("/companyUser/list")
    @ApiOperation(value = "业主管理-获取企业内部用户", response = CompanyVO.class)
    public ReturnData dealerUserList() {
        ReturnData data = ReturnData.builder();
        log.info("业主管理列表-获取企业内部用户");
        //获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("未登录");
            return data.success(false).code(ResponseEnum.UNAUTHORIZED).message("未登录");
        }
        log.info("业主管理列表-厂商-userId:{}", loginUser.getId());

        UserBo userBo = userService.getUserById(loginUser.getId());
        if (userBo == null) {
            log.error("未找到用户,userId:{}", loginUser.getId());
            return data.success(false).code(ResponseEnum.NOT_FOUND).message("未找到用户");
        }
        log.info("业主管理列表-厂商-businessAdministrationId:{}", userBo.getBusinessAdministrationId());

        if (userBo.getBusinessAdministrationId() == null) {
            log.error("未找到用户企业,userId:{}", loginUser.getId());
            return data.success(false).code(ResponseEnum.NOT_FOUND).message("未找到用户企业");
        }

        List<User> userList = userService.selectByBusinessAdministrationId(userBo.getBusinessAdministrationId());

        if (CollectionUtils.isEmpty(userList)) {
            return data.data(Collections.EMPTY_LIST).success(true).code(ResponseEnum.NOT_FOUND).message("未找到用户企业");
        }
        List<CompanyUserVO> companyUserVOList = new ArrayList<>();
        for (User user : userList) {
            CompanyUserVO companyUserVO = new CompanyUserVO();
            companyUserVO.setId(user.getId());
            StringBuffer nameBuffer = new StringBuffer();
            if (StringUtils.isNoneBlank(user.getUserName())) {
                nameBuffer.append(user.getUserName());
                nameBuffer.append("/");
            }
            if (StringUtils.isNoneBlank(user.getNickName())) {
                nameBuffer.append(user.getNickName());
            }
            companyUserVO.setName(nameBuffer.toString());
            companyUserVOList.add(companyUserVO);
        }
        return data.data(companyUserVOList).code(ResponseEnum.SUCCESS).success(true);
    }


    /**
     * 组装返回对象
     *
     * @param customerBaseInfoList 业主信息list
     * @param userInfoMap          用户基本信息map
     * @param FollowUpUserMap      跟进人名称
     * @param companyNameMap       公司名称
     * @return
     */
    private List<CustomerVO> getCustomerVOS(List<CustomerBaseInfo> customerBaseInfoList, Map<Integer, User> userInfoMap,
                                            Map<Integer, User> FollowUpUserMap, Map<Long, String> companyNameMap,
                                            Map<Integer, String> resultMap) {
        List<CustomerVO> customerBaseInfoVOList = new ArrayList<>();
        CustomerVO customerVO;
        for (CustomerBaseInfo customerBaseInfo : customerBaseInfoList) {

            customerVO = new CustomerVO();
            //id
            customerVO.setId(customerBaseInfo.getId());

            //分配状态
            if (customerBaseInfo.getAlotStatus() != null) {
                customerVO.setAlotStatus(customerBaseInfo.getAlotStatus());
                customerVO.setAlotStatusName(getAlotStatusName(customerVO.getAlotStatus()));
            }

            if (MapUtils.isNotEmpty(FollowUpUserMap)) {
                //跟进人
                if (customerBaseInfo.getFollowUpUserId() != null) {
                    customerVO.setFollowUpUserId(customerBaseInfo.getFollowUpUserId());
                    User FollowUpUser = FollowUpUserMap.get(customerBaseInfo.getFollowUpUserId());
                    if (FollowUpUser != null) {
                        String name = FollowUpUser.getUserName();
                        if (name == null || "".equals(name)) {
                            log.info("跟进人nickName，name:{}",FollowUpUser.getNickName());
                            customerVO.setFollowUpUserName(FollowUpUser.getNickName());
                        } else {
                            log.info("跟进人userName，name:{}",FollowUpUser.getUserName());
                            customerVO.setFollowUpUserName(FollowUpUser.getUserName());
                        }
                    }
                    log.info("跟进人为空，user:{},FollowUpUser:{}",customerBaseInfo.getFollowUpUserId(),FollowUpUser);

                }
            }
            //分配经销商
            if (customerBaseInfo.getChannelCompanyId() != null && MapUtils.isNotEmpty(companyNameMap)) {
                customerVO.setChannelCompanyId(customerBaseInfo.getChannelCompanyId());
                Long channelCompanyId = Long.valueOf(customerBaseInfo.getChannelCompanyId());
                customerVO.setChannelCompanyName(companyNameMap.get(channelCompanyId));
            }
            //业主等级
            customerVO.setLevel(customerBaseInfo.getLevel());
            customerVO.setScore(customerBaseInfo.getScore());
            User user = userInfoMap.get(customerBaseInfo.getUserId());
            if(user!=null) {
            	customerVO.setUserId(Math.toIntExact(user.getId()));

            	// modified by songjianming@sanduspace.cn on 2018/11/16
                // 修复 NullPoniterException 问题
                //昵称
                customerVO.setNickName(user.getNickName());
                //手机号
                customerVO.setMobile(user.getMobile());
                //微信号
                customerVO.setOpenId(user.getOpenId());
                //跟进结果
                if (MapUtils.isNotEmpty(resultMap)) {
                    customerVO.setFollowUpResult(resultMap.get(user.getId()));
                }
            }
            //业主地址
            customerVO.setAddress(customerBaseInfo.getAddress());

            customerBaseInfoVOList.add(customerVO);
        }
        return customerBaseInfoVOList;
    }

    /**
     * 从登录信息中判断用户是否是businessType用户
     * 获取有权限的企业id
     *
     * @return 企业id
     */
    private Integer getCompanyId(Integer businessType, LoginUser loginUser) {
        log.info("业主管理列表-厂商-userId:{}", loginUser.getId());

        UserBo userBo = userService.getUserById(loginUser.getId());
        if (userBo == null) {
            log.error("未找到用户,userId:{}", loginUser.getId());
            return null;
        }
        log.info("业主管理列表-厂商-businessAdministrationId:{}", userBo.getBusinessAdministrationId());

        if (userBo.getBusinessAdministrationId() == null) {
            log.error("未找到用户企业,userId:{}", loginUser.getId());
            return null;
        }

        //判断用户类型是否正确
        BaseCompany baseCompany = baseCompanyService.getCompanyById(userBo.getBusinessAdministrationId());
        if (baseCompany == null) {
            log.error("未找到用户企业,companyId:{}", userBo.getBusinessAdministrationId());
            return null;
        }

        if (!baseCompany.getBusinessType().equals(businessType)) {
            log.error("用户企业不是厂商,companyId:{}", userBo.getBusinessAdministrationId());
            return null;
        }

        return userBo.getBusinessAdministrationId();

    }

    @GetMapping("/{companyId}")
    @ApiOperation(value = "根据经销商id获取分配规则", response = CustomerAlotZoneVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "经销商id", paramType = "path", required = true, dataType = "Long")
    })
    public ReturnData getCustomerAlotZoneByCompanyId(@PathVariable Long companyId) {

        Map<String, String> baseAreaMap = customerAlotZoneService.queryAreaMap();
        List<CustomerAlotZone> results = customerAlotZoneService.queryAlotZoneByCompany(companyId);
        if (results != null && results.size() > 0) {
            CustomerAlotZoneVO vo = new CustomerAlotZoneVO();
            List<AllCode> allCodes = new ArrayList<>();
            for (CustomerAlotZone result : results) {
                AllCode allCode = new AllCode();
                allCode.setId(result.getId().intValue());
                allCode.setProvinceCode(result.getProvinceCode());
                allCode.setProvinceName(baseAreaMap.get(result.getProvinceCode()));
                allCode.setCityCode(result.getCityCode());
                allCode.setCityName(baseAreaMap.get(result.getCityCode()));
                allCode.setAreaCode(result.getAreaCode());
                allCode.setAreaCodeName(baseAreaMap.get(result.getAreaCode()));
                allCodes.add(allCode);
            }
            vo.setChannelCompanyId(companyId.intValue());
            vo.setAllCode(allCodes);
            return builder().data(vo).code(SUCCESS);
        }
        return builder().code(NOT_CONTENT).message("没有数据");
    }

    @PostMapping("/company")
    @ApiOperation(value = "新增经销商分配规则")
    public ReturnData saveAlotZone(@Valid @RequestBody CustomerAlotZoneAdd add, BindingResult br) {
        ReturnData data = builder();
        if (br.hasErrors()) {
            return processValidError(br, data);
        }
        if (add.getChannelCompanyId() == null) {
            return builder().code(PARAM_ERROR).message("新增失败,请选择要分配的经销商...");
        }
        for (int i = 0, length = add.getAllCode().size(); i < length; i++) {
            if (Strings.isNullOrEmpty(add.getAllCode().get(i).getProvinceCode())) {
                return builder().code(PARAM_ERROR).message("新增失败,至少选择省区域...");
            }
            for (int j = i + 1; j < length; j++) {
                boolean existed = add.getAllCode().get(i).getProvinceCode().equals(add.getAllCode().get(j).getProvinceCode()) &&
                        add.getAllCode().get(i).getCityCode().equals(add.getAllCode().get(j).getCityCode()) &&
                        add.getAllCode().get(i).getAreaCode().equals(add.getAllCode().get(j).getAreaCode());
                if (existed) {
                    return builder().code(PARAM_ERROR).message("新增失败,已有相同分配规则...");
                }
            }
        }
        log.info("新增经销商参数信息：{}", add);
        int result = customerAlotZoneService.addGeneraRule(add);
        if (result > 0) {
            return builder().code(SUCCESS).message("新增成功");
        }
        return builder().code(ERROR).message("新增失败...");
    }

    @DeleteMapping
    @ApiOperation("取消经销商分配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "经销商id", paramType = "query", dataType = "Integer", required = true),
    })
    public ReturnData deleteAlotZone(Integer companyId) {
        log.info("经销商id:{}", companyId);
        int result = customerAlotZoneService.deleteByCompanyId(companyId);
        if (result > 0) {
            return builder().code(SUCCESS).message("取消分配成功");
        }
        return builder().code(ERROR).message("取消分配失败");
    }

    @ApiOperation(value = "分配规则列表", response = CustomerAlotZoneVO.class)
    @GetMapping("/AlotZoneList/allotRule")
    public ReturnData listCommonDesignPlan(CustomerAlotZoneQuery query) {
        if (query.getStart() == null) {
            query.setStart(1);
        }
        if (query.getLimit() == null) {
            query.setLimit(10);
        }
        query.setIsDeleted(0);
        int total = customerAlotZoneService.countByOption(query);
        if (query.getStart() > total) {
            query.setStart(total);
        }
        if (query.getStart() < 1) {
            query.setStart(1);
        }
        PageVO<CustomerAlotZoneVO> datas = new PageVO<>();
        datas.setList(customerAlotZoneService.queryAlotZoneList(query));
        datas.setTotal(total);
        datas.setPageNo(query.getStart());
        datas.setPageSize(query.getLimit());
        if (datas.getList() == null || datas.getList().size() < 1) {
            return builder().code(NOT_CONTENT).message("没有数据了");
        }
        return builder().data(datas).code(SUCCESS).message("成功");
    }

    @ApiOperation(value = "根据厂商id查询经销商列表", response = BaseCompany.class)
    @GetMapping("/companyList/factory/{companyId}")
    public ReturnData queryCompanyList(@PathVariable Long companyId) {

        List<Company> results = customerAlotZoneService.queryCompanyList(companyId.intValue());
        if (results.size() > 0) {
            return ReturnData.builder().data(results).code(SUCCESS);
        }
        return ReturnData.builder().code(NOT_CONTENT).message("没有数据了");
    }

    @ApiOperation(value = "查询经销商是否已被分配", response = BaseCompany.class)
    @GetMapping("/companyList/{distributorId}")
    public ReturnData queryCompanyList(@PathVariable Integer distributorId) {
        if(distributorId == null) {
            return ReturnData.builder().code(NOT_CONTENT).message("请选择经销商");
        }
        List<CustomerAlotZone> results = customerAlotZoneService.queryAlotZoneByDistributorId(Integer.valueOf(distributorId));
        if (results.size() > 0) {
            return ReturnData.builder().code(NOT_CONTENT).message("该经销商已设置分配规则");
        }
        return ReturnData.builder().code(SUCCESS).message("成功");
    }
    
    /**
     * 业主管理导出
     * @param customerBaseInfoVO
     * @return
     * @throws Exception 
     */
    @GetMapping("/exportCheck")
    @ApiOperation(value = "业主管理导出", response = ReturnData.class)
    public ReturnData exportCheck(HttpServletResponse response,CustomerBaseInfoVO customerBaseInfoVO) throws Exception {
        log.info("业主管理列表-入参,customerBaseInfoVO:{}", customerBaseInfoVO);
        //数据校验
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
    	BaseCompany baseCompany = null;
        Map<String,Object> resultMap  = validateData(loginUser);
        if(resultMap!=null && resultMap.containsKey("fail")) {
        	 return ReturnData.builder().code(NOT_CONTENT).message((String) resultMap.get("fail"));
        }else {
        	baseCompany = (BaseCompany) resultMap.get("success");
        }
        return ReturnData.builder().code(SUCCESS).message("成功");
    }
    
    /**
     * 业主管理导出
     * @param customerBaseInfoVO
     * @return
     * @throws Exception 
     */
    @GetMapping("/export")
    @ApiOperation(value = "业主管理导出", response = ReturnData.class)
    public void export(HttpServletResponse response,CustomerBaseInfoVO customerBaseInfoVO) throws Exception {
        log.info("业主管理列表-入参,customerBaseInfoVO:{}", customerBaseInfoVO);
        //数据校验
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
    	BaseCompany baseCompany = null;
        Map<String,Object> resultMap  = validateData(loginUser);
        if(resultMap!=null && resultMap.containsKey("success")) {
        	baseCompany = (BaseCompany) resultMap.get("success");
        }
        //查询导出的数据
		List<CustomerVO> datas = new ArrayList<CustomerVO>();
		CustomerBaseInfoQuery customerBaseInfoQuery = new CustomerBaseInfoQuery();
        customerBaseInfoQuery = (CustomerBaseInfoQuery) CopyUtil.copyProperties(customerBaseInfoQuery, customerBaseInfoVO);
        customerBaseInfoQuery.setIsDeleted(0);
       //导出类型:exportType:0-业主管理导出;1-我的业主导出
        if(customerBaseInfoVO!=null) {
        	if(customerBaseInfoVO.getExportType() ==0) {
	        	//厂商
	            if (baseCompany.getBusinessType().equals(CustomerBaseInfoConstant.BUSINESS_TYPE_MANUFACTURER)) {
	                customerBaseInfoQuery.setCompanyId(Math.toIntExact(baseCompany.getId()));
	            }
	            //经销商
	            if (baseCompany.getBusinessType().equals(CustomerBaseInfoConstant.BUSINESS_TYPE_DEALER)) {
	                customerBaseInfoQuery.setChannelCompanyId(Math.toIntExact(baseCompany.getId()));
	            }
        	}else {
	        	customerBaseInfoQuery.setChannelCompanyId(Math.toIntExact(loginUser.getBusinessAdministrationId()));
        	}
        }
		datas = customerBaseInfoService.queryExportData(customerBaseInfoQuery);
		if(datas == null ||datas.isEmpty()) {
			return;
		}
		try {
			List<Map<String, Object>> infoLs = this.createExcelRecord(datas);
			// 导出表格名称
			String filename = "业主信息导出";
			String columns[] = {"序号","昵称","手机号码", "业主地址", "业主等级", "状态", "所属经销商企业", "跟进人"};
			String keys[] = {
					"id",
					"nickName",
					"phone",
					"address",
					"levelName",
					"alotStatusName",
					"channelCompanyName",
					"followUpUserName"
			};
			Workbook wookbook = ExportExcel.createWorkBook(infoLs, keys, columns);
	        try {
	        	response.setContentType("application/vnd.ms-excel");
//	        	response.setContentType("application/octet-stream");
	            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename+".xls", "utf-8"));
		        OutputStream outputStream = response.getOutputStream();
//		        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\excle_test\\测试.xls"));
		        wookbook.write(outputStream);
//		        wookbook.write(fileOutputStream);
		        outputStream.flush();
		        outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
//		ExcelUtils.exportExcel(datas, "业主管理资料导出", "业主管理列表", response, false);
    }
    
    private List<Map<String, Object>> createExcelRecord(List<CustomerVO> list) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		CustomerVO project = null;
		int index = 1;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int j = 0; j < list.size(); j++) {
			project = list.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("id", index++);
			mapValue.put("nickName", project.getNickName()); // 户型编码
			mapValue.put("phone", project.getMobile());
			mapValue.put("address", project.getAddress());
			mapValue.put("levelName", project.getLevelName());
			mapValue.put("alotStatusName", project.getAlotStatusName());
			mapValue.put("channelCompanyName", project.getChannelCompanyName());
			mapValue.put("followUpUserName", project.getFollowUpUserName());
			listmap.add(mapValue);
		}
		return listmap;
	}
    
    /**
     * 数据校验
     * @param loginUser
     */
    private Map<String,Object> validateData(LoginUser loginUser) {
    	 Map<String,Object> resultMap = new HashMap<String,Object>();
    	 BaseCompany baseCompany = null;
         if (loginUser == null) {
             log.error("未登录");
             resultMap.put("fail", "未登录");
             return resultMap;
 			 //throw new ServiceException(ResponseEnum.UNAUTHORIZED, "未登录");
         }
	     log.info("业主管理列表-厂商-userId:{}", loginUser.getId());
	     UserBo userBo = userService.getUserById(loginUser.getId());
	     if (userBo == null) {
	         log.error("未找到用户,userId:{}", loginUser.getId());
//			throw new ServiceException(ResponseEnum.NOT_FOUND, "未找到用户");
			resultMap.put("fail", "未找到用户");
            return resultMap;
	     }
	     log.info("业主管理列表-厂商-businessAdministrationId:{}", userBo.getBusinessAdministrationId());
	     if (userBo.getBusinessAdministrationId() == null) {
	         log.error("未找到用户企业,userId:{}", loginUser.getId());
	         resultMap.put("fail", "未找到用户企业");
	         return resultMap;
			 //throw new ServiceException(ResponseEnum.NOT_FOUND, "未找到用户企业");
	     }
	     baseCompany = baseCompanyService.getCompanyById(userBo.getBusinessAdministrationId());
	     if (baseCompany == null) {
	         log.error("未找到用户企业,companyId:{}", userBo.getBusinessAdministrationId());
	         resultMap.put("fail", "未找到用户企业");
	         return resultMap;
	         //throw new ServiceException(ResponseEnum.NOT_FOUND, "未找到用户企业");
	     }
	     if (!(baseCompany.getBusinessType().equals(CustomerBaseInfoConstant.BUSINESS_TYPE_MANUFACTURER)||
	    		baseCompany.getBusinessType().equals(CustomerBaseInfoConstant.BUSINESS_TYPE_DEALER))) {
	    	 resultMap.put("fail", "非厂商、经销商用户不能导出");
	         return resultMap;	
	    	 //throw new ServiceException(ResponseEnum.UNAUTHORIZED, "非厂商和独立经销商用户不能导出");
	     }
	     resultMap.put("success",baseCompany);
	     return resultMap;
    }

}
