package com.sandu.service.user.impl.manage;


import com.github.pagehelper.PageInfo;
import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.service.PayAccountService;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.servicepurchase.constant.ServicesPurchaseConstant;
import com.sandu.api.servicepurchase.model.ServicesAccountRel;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.servicepurchase.model.ServicesRoleRel;
import com.sandu.api.servicepurchase.serivce.ServicesAccountRelService;
import com.sandu.api.servicepurchase.serivce.ServicesBaseInfoService;
import com.sandu.api.servicepurchase.serivce.ServicesPriceService;
import com.sandu.api.servicepurchase.serivce.ServicesRoleRelService;
import com.sandu.api.servicepurchase.serivce.biz.ServicesPurchaseBizService;
import com.sandu.api.user.input.UserAdd;
import com.sandu.api.user.input.UserEdit;
import com.sandu.api.user.input.UserManageSearch;
import com.sandu.api.user.model.*;
import com.sandu.api.user.service.SysRoleGroupService;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.UserJurisdictionService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.common.exception.BizException;
import com.sandu.common.sms.Utils;
import com.sandu.common.util.SysUserUtil;
import com.sandu.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.servicepurchase.constant.ServicesPurchaseConstant.PRICE_UNIT_YEAR;

@Slf4j
public abstract class BaseUserManageServiceImpl {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ServicesPurchaseBizService servicesPurchaseBizService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    //是否需要更新密码标识
    private static final Integer PASSWORD_NEED_UPDATE = 1;

    //b端用户标识
    private static final Integer PLATFORM_2b_VALUE = 2;

    @Autowired
    private UserJurisdictionService userJurisdictionService;

    @Autowired
    private PayAccountService payAccountService;

    @Autowired
    private ServicesAccountRelService servicesAccountRelService;

    @Autowired
    private ServicesRoleRelService servicesRoleRelService;

    @Autowired
    private ServicesBaseInfoService servicesBaseInfoService;

    @Autowired
    private ServicesPriceService servicesPriceService;

    @Autowired
    private SysRoleGroupService sysRoleGroupService;

    @Autowired
    private SysUserManageService sysUserManageService;

    /**
     * 查看用户列表
     * @param search
     * @return
     */
    public PageInfo<UserManageDTO> getUserList(UserManageSearch search){
        //获取用户列表
        PageInfo<UserManageDTO> pageInfo = selectUserList(search);
        return pageInfo;
    }

    /**
     * 厂商、经销商新增用户
     * @param userAdd
     * @param userId
     * @return
     */
    public boolean addUser(UserAdd userAdd,Long userId){

        //构建用户信息
        SysUser sysUser = transformSysUer(userAdd);

        //创建用户的登录编码
        String nickName = createNickName(sysUser.getCompanyId());
        sysUser.setNickName(nickName);

        //设置用户经销商id
        setBusinessAdministrationId(sysUser,userAdd.getFranchiserId());

        //存储系统字段
        saveSystemField(sysUser,userId,userAdd.getServicesId());

        //获取套餐类型
        getPackageType(sysUser,userAdd.getServicesId());

        checkUserTypeIsSameToPackageType(userAdd.getServicesId(),sysUser.getUserType());

        //保存用户信息
        Long addUserId = sysUserService.insertUser(sysUser);

        //处理套餐信息
        handlerPackageInfo(userAdd.getServicesId(),userAdd.getPriceId(),addUserId,sysUser);

        //开通度币账号
        openPayAccount(sysUser.getId(), sysUser.getMobile(), 0d);

        return addUserId > 0;
    }

    private void getPackageType(SysUser sysUser, Long servicesId) {
        if (Objects.nonNull(servicesId)){
            String packType = sysUserService.getPackType(servicesId.intValue());
            sysUser.setUseType(Objects.equals(Integer.parseInt(packType),0) ? 1 : 0);
        }
    }

    /**
     * 修改用户信息
     * @param userEdit
     * @param userId
     */
    public void editUser(UserEdit userEdit, Long userId){

        //获取当前修改用户信息
        SysUser operater = sysUserService.get(userId.intValue());

        //要修改的用户信息
        SysUser changeUserInfo = sysUserService.get(Integer.valueOf(userEdit.getId() + ""));

        //构建用户信息
        SysUser sysUser = transformSysUer(userEdit);

        sysUser.setModifier(SysUserUtil.getUserName(operater));

        setBusinessAdministrationId(sysUser,userEdit.getFranchiserId());

        if (Objects.nonNull(userEdit.getServicesId())){
            sysUser.setServicesFlag(1);
            sysUser.setValidTime(0);
        }

        //获取用户的套餐信息
        ServicesAccountRel account = servicesAccountRelService.getAccountByUserId(userEdit.getId().intValue());

        //校验用户类型是否变更
        checkUserTypeChange(userEdit.getServicesId(),userEdit.getId(),userEdit.getUserType(),account);

        //更新用户信息
        int update = sysUserService.update(sysUser);
        log.info("更新用户成功 =>{userId}"+userEdit.getId());

        if (update > 0 && Objects.nonNull(userEdit.getServicesId())){
            log.info("开始处理用户更换套餐逻辑 =>{servicesId}" + userEdit.getServicesId());
            if (Objects.nonNull(account) && Objects.equals(userEdit.getServicesId(),account.getServicesId())){
                //套餐没有变更
                log.info("套餐没有变更");
                return;
            }else{
                //TODO 删除用户套餐相关权限、平台权限
                delUserOldRole(account,changeUserInfo);
            }
            //插入套餐信息
            SysUser editUserInfo = sysUserService.get(sysUser.getId().intValue());
            handlerPackageInfo(userEdit.getServicesId(),userEdit.getPriceId(),editUserInfo.getId(),editUserInfo);
            syncAccountInfo(editUserInfo,userEdit.getServicesId());


            if (Objects.nonNull(account) && Objects.equals(Integer.parseInt(account.getStatus()),1)){
                //套餐已使用 =>{} 激活套餐时间
                activatePackageTime(userEdit,changeUserInfo,account);
            }

            if (Objects.equals(changeUserInfo.getServicesFlag(),0)){
                //旧用户转套餐用户,立即启用
                if (Objects.nonNull(changeUserInfo.getFailureTime())){
                    //已经登录过了 =>{}激活套餐时间
                    activatePackageTime(userEdit,changeUserInfo,account);
                }
            }
        }
    }

    private void activatePackageTime(UserEdit userEdit, SysUser changeUserInfo, ServicesAccountRel account) {
        List<ServicesPrice> servicesPrices = servicesPriceService.findServicesPriceList(userEdit.getServicesId());
        if (!CollectionUtils.isEmpty(servicesPrices)){
            ServicesPrice servicesPrice = servicesPrices.get(0);
            Date now = new Date();
            Date result = fetchDate(now, servicesPrice.getDuration(), Integer.valueOf(servicesPrice.getPriceUnit()));
            ServicesAccountRel accountRel = servicesAccountRelService.getAccountByUserId(changeUserInfo.getId().intValue());
            ServicesAccountRel updateAccountRel = new ServicesAccountRel();
            updateAccountRel.setId(accountRel.getId());
            updateAccountRel.setEffectiveBegin(now);
            updateAccountRel.setEffectiveEnd(result);
            updateAccountRel.setStatus("1");
            servicesAccountRelService.updateByPrimaryKeySelective(updateAccountRel);
            //计算用户使用了多少时长
            //同步SysUser表中失效时间
            sysUserManageService.dealUserFailureTimeAndValidTime(accountRel.getUserId(),new Date().compareTo(changeUserInfo.getFailureTime()) >= 0 ? changeUserInfo.getFailureTime() : new Date(),now,result);
        }
    }

    private Date totalUserUsedTime(Date failureTime, Date firstLoginTime) {
        if (new Date().compareTo(failureTime) >= 0){
            //时长已过期
            return null;
        }
        return new Date();
    }

    private long totalOldUserUseTime(SysUser changeUserInfo) {
        if (new Date().compareTo(changeUserInfo.getFailureTime()) >= 0){
            //套餐过期了
            return 0;
        }
        return changeUserInfo.getFailureTime().getTime() - changeUserInfo.getFirstLoginTime().getTime();
    }

    private long computeTime(ServicesAccountRel account) {
        Date now = new Date();
        if (Objects.equals(account.getStatus(),"1")){
            //判断账号是否过期
            if (now.compareTo(account.getEffectiveEnd()) >= 0){
                //账号过期
                return 0;
            }
            return account.getEffectiveEnd().getTime() - now.getTime();
        }
        return 0;
    }

    private Date fetchDate(Date currentDate, int mount, int timeUnit) {
        Date result = currentDate;
        switch (timeUnit + "") {
            case PRICE_UNIT_YEAR:
                result = DateUtils.addYears(currentDate, mount);
                break;
            case ServicesPurchaseConstant.PRICE_UNIT_MONTH:
                result = DateUtils.addMonths(currentDate, mount);
                break;
            case ServicesPurchaseConstant.PRICE_UNIT_DAY:
                result = DateUtils.addDays(currentDate, mount);
                break;
            default:
                break;
        }
        return result;
    }

    private void syncAccountInfo(SysUser editUserInfo, Long servicesId) {
        ServicesBaseInfo baseInfo = servicesBaseInfoService.getById(servicesId);
        Integer useType = baseInfo.getServicesType() == 0 ? 1 : 0;

        if (!Objects.equals(editUserInfo.getUseType(),useType)){
            SysUser update = new SysUser();
            update.setUseType(useType);
            update.setId(editUserInfo.getId());
            sysUserService.update(update);
        }
    }

    private void checkUserTypeChange(Long changeServicesId,Long userId, Integer userType,ServicesAccountRel account) {

        //校验用户用户类型和套餐类型是否匹配
        checkUserTypeIsSameToPackageType(changeServicesId,userType);

//        SysUser sysUser = sysUserService.get(userId.intValue());
//        if (!Objects.equals(sysUser.getUserType(),userType) && Objects.nonNull(account)){
//            if (Objects.equals(changeServicesId,account.getServicesId())){
//                //套餐没有变更
//                delUserOldRole(account,sysUser);
//            }
//        }
    }

    private void checkUserTypeIsSameToPackageType(Long changeServicesId, Integer userType) {
        if (Objects.nonNull(changeServicesId)){
            ServicesBaseInfo baseInfo = servicesBaseInfoService.getById(changeServicesId);

            if (Objects.isNull(baseInfo)){
                throw new BizException("套餐不存在");
            }

            if (StringUtils.isEmpty(baseInfo.getUserScope())){
                throw new BizException("没有配置套餐适用用户类型");
            }
            String[] split = baseInfo.getUserScope().split(",");
            if (!Arrays.asList(split).stream().map(Integer::parseInt).anyMatch(item -> Objects.equals(item,userType))){
                throw new BizException("套餐与用户类型不匹配");
            }
        }
    }

    /**
     * 用户更换套餐时,需要删除用户的旧套餐的所有权限
     */
    private void delUserOldRole(ServicesAccountRel account,SysUser user) {

        if (Objects.equals(user.getServicesFlag(),0)){
            handlerOldAccountRole(user);
        }else{
            handlerPackageAccountRole(account);
        }
    }

    private void handlerPackageAccountRole(ServicesAccountRel account) {
        if (Objects.nonNull(account)){
            log.info("开始删除用户旧的套餐权限 =>{}" + account.getServicesId());
            //获取用户原来的套餐权限
            List<ServicesRoleRel> oldRoles = servicesRoleRelService.getByServiceId(account.getServicesId());

            if (!CollectionUtils.isEmpty(oldRoles)){
                //提取用户角色id
                List<Integer> oldRoleIds = oldRoles.stream().map(ServicesRoleRel::getRoleId).collect(Collectors.toList());
                //根据角色id获取旧套餐所配置的平台id
                Set<Long> oldPlatformIds = sysRoleService.getRolePlatformId(oldRoleIds);

                //删除用户的旧的权限
                sysRoleService.delUserRole(account.getUserId().intValue(),oldRoleIds);

                //删除用户的平台权限
                userJurisdictionService.batchDelUserJurisdiction(account.getUserId().intValue(),oldPlatformIds);

                //删除套餐用户关联的信息
                ServicesAccountRel servicesAccountRel = new ServicesAccountRel();
                servicesAccountRel.setIsDeleted(1);
                servicesAccountRel.setId(account.getId());
                servicesAccountRelService.updateByPrimaryKeySelective(servicesAccountRel);
            }
        }
    }

    private void handlerOldAccountRole(SysUser user) {
        log.info("开始删除老账号权限 =>{}" + user.getId());
        //获取用户的角色权限
        Set<SysUserRole> userRoles = sysRoleService.getUserRolesByUserId(user.getId());
        //获取用户的角色组权限
        Set<SysUserRoleGroup> userGroupRole = sysRoleGroupService.getUserRoleGroupByUserId(user.getId());
        //获取用户的平台权限
        List<UserJurisdiction> userJurisdictions = userJurisdictionService.queryByUserId(user.getId());
        //开始删除权限
        if (!CollectionUtils.isEmpty(userRoles)){
            List<Integer> roleIds = userRoles.stream().map(id -> Integer.valueOf(id.getId() + "")).collect(Collectors.toList());
            sysRoleService.delUserRole(user.getId().intValue(),roleIds);
        }

        if (!CollectionUtils.isEmpty(userGroupRole)){
            List<Integer> roleGroupIds = userGroupRole.stream().map(id -> Integer.valueOf(id.getId() + "")).collect(Collectors.toList());
            sysRoleGroupService.batchDel(user.getId(),roleGroupIds);
        }

        if (!CollectionUtils.isEmpty(userJurisdictions)){
            Set<Long> oldPlatformIds = userJurisdictions.stream().map(UserJurisdiction::getPlatformId).collect(Collectors.toSet());
            //删除用户的平台权限
            userJurisdictionService.batchDelUserJurisdiction(user.getId().intValue(),oldPlatformIds);
        }
    }

    private void handlerPackageInfo(Long servicesId,Integer priceId,Long addUserId,SysUser sysUser) {
        if (Objects.nonNull(servicesId)){
            //用户新增时有设置套餐
            sysUser.setId(addUserId);
            List<ServicesRoleRel> servicesRoleRels = null;
            servicesRoleRels = servicesPurchaseBizService.handlerCompanyUserPackageInfo(servicesId, sysUser,priceId);
            if (!CollectionUtils.isEmpty(servicesRoleRels)){
                //插入用户角色
                this.handlerPackageUserRoles(sysUser.getId(), servicesRoleRels);
                //插入平台权限
                this.handlerPackageUserJurisdiction(sysUser.getId(), sysUser.getMobile(), servicesRoleRels);
            }
        }
    }

    private PayAccount openPayAccount(Long userId, String mobile, Double balanceAmount) {
        PayAccount payAccount = new PayAccount();
        Date date = new Date();
        payAccount.setUserId(userId);
        payAccount.setCreator(mobile);
        payAccount.setGmtCreate(date);
        payAccount.setModifier(mobile);
        payAccount.setGmtModified(date);
        payAccount.setIsDeleted(0);
        payAccount.setBalanceAmount(balanceAmount * 10);
        payAccount.setConsumeAmount(0.0);
        payAccount.setPlatformBusinessType("2b");

        payAccountService.insertPayAccount(payAccount);
        return payAccount;
    }

    private void handlerPackageUserJurisdiction(Long userId, String mobile, List<ServicesRoleRel> servicesRoleRels) {
        List<Integer> roleids = servicesRoleRels.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        if (Objects.nonNull(roleids)){
            Set<Long> platformIds = sysRoleService.getRolePlatformId(roleids);
            log.info("需要开通的平台权限 =>{}" + (platformIds == null ? 0 : platformIds.toString()));

            if (platformIds != null) {
                //构建UserJurisdiction
                List<UserJurisdiction> userJurisdictions = platformIds.stream()
                        .map(platformId -> {
                            return buildUserJurisdiction(userId, platformId, mobile);
                        })
                        .collect(Collectors.toList());

                userJurisdictionService.batchUserJurisdictions(userJurisdictions);
            }
        }
    }

    private UserJurisdiction buildUserJurisdiction(Long userId, Long id, String mobile) {
        //构建UserJurisdiction
        Date date = new Date();
        UserJurisdiction userJurisdiction = new UserJurisdiction();
        userJurisdiction.setIsDeleted(0);
        userJurisdiction.setCreator(mobile);
        userJurisdiction.setGmtCreate(date);
        userJurisdiction.setModifier(mobile);
        userJurisdiction.setGmtModified(date);
        userJurisdiction.setPlatformId(id);
        userJurisdiction.setUserId(userId);
        userJurisdiction.setJurisdictionStatus(1);
        userJurisdiction.setModifierUserId(userId);

        return userJurisdiction;
    }

    private void handlerPackageUserRoles(Long userId, List<ServicesRoleRel> servicesRoleRels) {

        List<SysUserRole> userRoles = servicesRoleRels.stream().map(m -> {
            Date now = new Date();
            SysUserRole u = new SysUserRole();
            u.setUserId(userId);
            u.setGmtCreate(now);
            u.setGmtModified(now);
            u.setIsDeleted(0);
            u.setRoleId(new Long(m.getRoleId()));
            u.setCreator("system");
            u.setModifier("system");
            u.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            return u;
        }).collect(Collectors.toList());
        //批量插入用户角色权限
        if (userRoles != null && !userRoles.isEmpty()) {
            sysRoleService.batchUserRole(userRoles);
        }

    }

    private void saveSystemField(SysUser sysUser, Long userId, Long servicesId) {
        SysUser currentUser = sysUserService.get(userId.intValue());
        Date now = new Date();
        sysUser.setGmtCreate(now);
        sysUser.setGmtModified(now);
        sysUser.setCreator(StringUtils.isEmpty(currentUser.getUserName()) ? StringUtils.isEmpty(currentUser.getMobile()) ? currentUser.getNickName() : currentUser.getMobile() : currentUser.getUserName());

        //是否需要更新密码标识
        sysUser.setPasswordUpdateFlag(StringUtils.isEmpty(sysUser.getMobile()) ? PASSWORD_NEED_UPDATE : 0);
        sysUser.setPlatformType(PLATFORM_2b_VALUE);
        sysUser.setGroupId(0); //防止报错

        //套餐用户标识
        sysUser.setUuid(UUIDUtil.getUUID());
        sysUser.setServicesFlag(1);
        sysUser.setUserSource(2);
    }

    protected abstract void setBusinessAdministrationId(SysUser sysUser, Long franchiserId);

    protected abstract String createNickName(Long companyId);

    private SysUser transformSysUer(Object user) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user,sysUser);
        return sysUser;
    }

    protected PageInfo<UserManageDTO> selectUserList(UserManageSearch search){
        //做这一步是为了兼容经销列表
        setCompanyId(search);
        setUserType(search);
        return sysUserService.selectUserList(search);
    }

    protected abstract void setUserType(UserManageSearch search);

    protected abstract void setCompanyId(UserManageSearch search);

    protected String creatVendorANDCompanyUserCode(Long companyId){
        BaseCompany baseCompany;
        String userName = "";
        /**
         * 生成企业内部用户登录名
         * 生成规则：
         * 企业编码 + EIU + 4位数 例：（DTB0000001EIU0001）
         *
         */
        try {
            StringBuffer commanyCodePrefix = new StringBuffer();//前缀
            StringBuffer userNameCodeSuffix = new StringBuffer();//后缀

            baseCompany = baseCompanyService.queryById(companyId);
            commanyCodePrefix.append(baseCompany.getCompanyCode());
            commanyCodePrefix.append("EIU");

            String commanyCodePrefixS = commanyCodePrefix.toString() + "%";
            String userNameCodeMax = "";
            userNameCodeMax = this.getMaxCompanyInUserNameCode(commanyCodePrefixS, companyId);  /**获取当前前缀的最大序列号**/

            Integer nameCode = 0;
            if (StringUtils.isNotEmpty(userNameCodeMax)) {
                nameCode = Integer.parseInt(userNameCodeMax.substring(commanyCodePrefixS.length()-1, userNameCodeMax.length()));
                nameCode++;
                userNameCodeSuffix.append(nameCode);
            } else {
                userNameCodeSuffix.append("1");
            }

            int k = userNameCodeSuffix.length();
            for (int i = 0; i < 4 - k; i++) {
                userNameCodeSuffix.insert(0, "0");
            }

            userName = (commanyCodePrefix + "" + userNameCodeSuffix);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userName;
    }

    /**
     * 获取企业内部用户名最大编码
     *
     * @return
     */
    private String getMaxCompanyInUserNameCode(String commanyCodePrefixS, Long companyId) {
        String userCode = null;
        userCode = sysUserService.getMaxCompanyInUserNameCode(commanyCodePrefixS, companyId);
        return userCode;
    }
}
