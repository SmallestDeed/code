package com.sandu.service.user.impl.biz;

import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.service.PayAccountService;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.servicepurchase.model.bo.ServicesRoleInfoBO;
import com.sandu.api.servicepurchase.serivce.biz.ServicesPurchaseBizService;
import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.model.SysUserLoginAggregated;
import com.sandu.api.system.model.SysUserLoginLog;
import com.sandu.api.system.service.ResPicService;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.system.service.SysUserLoginAggregatedService;
import com.sandu.api.system.service.SysUserLoginLogService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.input.UserQuery;
import com.sandu.api.user.model.*;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.model.bo.UserMenuBO;
import com.sandu.api.user.model.bo.UserMenuTreeBO;
import com.sandu.api.user.service.*;
import com.sandu.common.NetworkUtil;
import com.sandu.common.Validator;
import com.sandu.common.constant.PlatFormConstant;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.http.HttpClientUtil;
import com.sandu.common.sms.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.config.ResourceConfig;
import com.sandu.pay.order.model.PayModelGroupRef;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.service.user.dao.SysUserDao;
import com.sandu.service.user.impl.SysUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;


abstract class BaseUserLoginServiceImpl {

    private Logger logger = LoggerFactory.getLogger(BaseUserLoginServiceImpl.class);
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private UserJurisdictionService userJurisdictionService;

    @Autowired
    private SysRoleGroupService sysRoleGroupService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysFuncService sysFuncService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserLoginLogService sysUserLoginLogService;

    @Autowired
    private SysUserLoginAggregatedService sysUserLoginAggregatedService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private PayModelGroupRefService payModelGroupRefService;

    @Autowired
    private ServicesPurchaseBizService servicesPurchaseBizService;

    @Autowired
    private PayAccountService payAccountService;

    public Object login(UserLogin userLogin) {
        Long startTime = System.currentTimeMillis();
        if (userLogin != null) {
            logger.info("serverTypeServer:" + userLogin.getServerType());
        }
        //定制企业使用pc2b相同登录逻辑(除了账号验证方式)
        if (userLogin.getPlatformCode().equals("pc2bCustom")) {
            userLogin.setPlatformCode("pc2b");
        }

        BasePlatform platform = basePlatformService.queryByPlatformCode(userLogin.getPlatformCode());
        if (platform == null) {
            throw new BizException(ExceptionCodes.CODE_10010001, "平台类型不正确");
        }
        userLogin.setPlatformId(platform.getId());

        UserInfoBO userInfoBO = checkUserAccount(userLogin);
        if (userInfoBO == null) {
            throw new BizException(ExceptionCodes.CODE_10010014, "用户不存在或者用户名密码有误");
        }

        //户型绘制工具检查当前登录用户使用的工具版本号
        this.checkPcHouseDrawVersion(userLogin);

        //只检查B端用户
        if (userLogin.getPlatformType() == SysUser.PLATFORM_TYPE_B) {
            //是否开通平台权限,合同是否到期,一个经销商多个企业等
            List<CompanyInfoBO> companyList = this.checkCompanyUserInfo(userInfoBO, platform, userLogin.getLoginCompanyId());
            if (companyList != null) {
                userInfoBO.setCompanyList(companyList);
                return userInfoBO;
            }
            //返回当前企业登录信息
            if (userInfoBO.getCompanyId() != null) {
                BaseCompany baseCompany = baseCompanyService.queryById(userInfoBO.getCompanyId());
                userInfoBO.setCurrentLoginCompanyName(baseCompany.getCompanyName());
                userInfoBO.setIsVirtual(baseCompany.getIsVirtual());
                userInfoBO.setIsExamine(baseCompany.getIsExamine());
                Integer logoResId = baseCompany.getCompanyLogo();
                if (logoResId != null) {
                    ResPic pic = resPicService.get(logoResId.longValue());
                    if (pic != null) {
                        userInfoBO.setCompanyLogo(pic.getPicPath());
                    }
                }

                // add by huangsongbo 2018.12.17 查询母公司的公司类型, 暂时的作用: 决定移动2b端要不要显示抢单的功能 ->start
                userInfoBO.setCompanyType(baseCompany.getBusinessType());
                if(baseCompany != null && baseCompany.getPid() != null) {
                    BaseCompany baseCompanyParent = baseCompanyService.queryById(baseCompany.getPid());
                    if(baseCompanyParent != null) {
                        userInfoBO.setCompanyType(baseCompanyParent.getBusinessType());
                    }
                }
                // add by huangsongbo 2018.12.17 查询母公司的公司类型, 暂时的作用: 决定移动2b端要不要显示抢单的功能 ->end
            }
            logger.info("B端用户数据检查累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());
        }

        userInfoBO.setMediaType(userLogin.getMediaType());

        //检测设备
        this.checkEquipment(userInfoBO, userLogin);
        logger.info("检测设备累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());

        //设置用户权限(菜单、功能、字段).
        setUserPermissions(userInfoBO, platform.getId(), platform.getPlatformCode());

        logger.info("设置用户权限累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());

        //生成菜单树
        buildMenuTree(userInfoBO);

        logger.info("生成菜单树累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());

        //JWT生成token
        this.createUserJwtToken(userInfoBO);

        //设置配置信息
        this.setServerConfigInfo(userInfoBO, userLogin);

        logger.info("设置配置信息累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());

        //用户首次登录PC端、移动端是否要更新手机号和密码
        promptUpdatePassword(userInfoBO);

        //缓存用户数据(功能,字段权限,jwtToken...)
        userInfoBO.setPlatformId(userLogin.getPlatformId());
        Object user = this.cacheUserInfoToRedis(userInfoBO);

        logger.info("缓存用户数据累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());
        //赠送B端用户三个月免费渲染
        if (userLogin.getPlatformType() == SysUser.PLATFORM_TYPE_B) {
            if (Objects.equals(userInfoBO.getIsLoginBefore(), 0)) {
                updateIsLoginBeforeFlag(userInfoBO.getId());
            }
            //firstLoginHandler(userInfoBO, platform);
        }

        //保存登录日志
        this.saveLoginLog(userInfoBO, userLogin);
        logger.info("登录累计耗时-->" + (System.currentTimeMillis() - startTime) + "ms,userId=" + userInfoBO.getId());

        return user;
    }

    protected void promptUpdatePassword(UserInfoBO userInfoBO) {
        if (Objects.equals(userInfoBO.getPasswordUpdateFlag(), 1)) {
            //用户需要更新手机号
            if (StringUtils.isEmpty(userInfoBO.getMobile())) {
                userInfoBO.setPromptUpdatePassword(0);
            }else{
                //用户已经绑定手机号，需要提示用户是否更新密码
                sysUserDao.updatePasswordUpdateFlagByUserId(userInfoBO.getId());
                userInfoBO.setPasswordUpdateFlag(0);
                userInfoBO.setPromptUpdatePassword(1);
            }
        }
    }

    /**
     * 是否当天首次登录 create by WangHaiLin (在子类中调用)
     *
     * @param userId     账号Id
     * @param platformId 登录平台
     * @return Boolean true:是当天首次登录；false:不是当天首次登录
     */
    protected boolean isFirstLoginToday(Long userId, Long platformId) {
        SysUserLoginLog lastLoginInfo = sysUserLoginLogService.getLastLoginInfo(userId, platformId);
        //没有该平台登录记录，则说明必然是首次登录
        if (null == lastLoginInfo) {
            return true;
        } else {
            //获取上次登录时间的毫秒数
            Date loginTime = lastLoginInfo.getLoginTime();
            long loginMillis = loginTime.getTime();
            //获取当天0点的时间毫秒数
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
            cal.set(Calendar.MINUTE, 0);//控制分
            cal.set(Calendar.SECOND, 0);//控制秒
            long todayMillis = cal.getTimeInMillis();//当天0点的时间
            if (todayMillis < loginMillis) {
                return false;
            } else {
                return true;
            }
        }
    }

    private void check2bCompanyGiveRenderPackage(UserInfoBO userInfoBO) {
        logger.info("校验B端用户是否有赠送包年套餐start");
        if (userInfoBO.getIsLoginBefore().intValue() == 0) {
            //用户首次登录 => 查询用户所在企业是否具有赠送包年套餐
            Long companyId = userInfoBO.getBusinessAdministrationId();
            if (userInfoBO.getUserType().intValue() == 3) {//判断用户是否是经销商
                //获取经销商的公司id
                BaseCompany baseCompany = baseCompanyService.queryById(userInfoBO.getBusinessAdministrationId());
                if (baseCompany != null) {
                    companyId = baseCompany.getPid();
                }
            }
            PayModelGroupRef p = payModelGroupRefService.checkCompanyGivePackage(companyId);
            if (null != p && userInfoBO.getIsLoginBefore().intValue() == 0) {
                //企业赠送包年
                logger.info("调用支付插入用户包年套餐 =>{} start");
                payModelGroupRefService.add2bCompanyGivePackage(userInfoBO.getId(), p);
            }
        }
    }

    public void checkPcHouseDrawVersion(UserLogin userLogin) {
    }

    public void firstLoginHandler(UserInfoBO userInfoBO, BasePlatform platform) {
        //用户首次登录赠送3个月免费渲染
        if (userInfoBO.getServicesFlag().intValue() == 0) {
            addPackage(userInfoBO, platform);
        } else if (userInfoBO.getIsLoginBefore().intValue() == 0 && userInfoBO.getServicesFlag().intValue() == 1) {
            //套餐用户,赠送渲染
            int giveRenderMonth = packageUserGiveRender(userInfoBO);
        }
        //检验B端企业是具有赠送免费渲染
        try {
            this.check2bCompanyGiveRenderPackage(userInfoBO);
        } catch (Exception e) {
            logger.error("检验B端企业赠送免费渲染失败userId =>{}", userInfoBO.getId());
        }
        updateIsLoginBeforeFlag(userInfoBO.getId());
    }

    private int packageUserGiveRender(UserInfoBO userInfoBO) {
        //获取用户套餐赠送渲染月份
        logger.info("调用系统服务获取用户套餐");
        try {
            Map<String, Object> map = servicesPurchaseBizService.getFreeRenderDurationByUserid(userInfoBO.getId());
            int month = (Integer) map.get("freeRenderDuration");
            if (month > 0) {
                int i = payModelGroupRefService.add2bUserPackageGiveRender(userInfoBO.getId(), month);
                if (i > 0) {
                    logger.info("套餐用户首次登录赠送免费渲染SUCCESS");
                }
                return month;
            }
        } catch (Exception e) {
            logger.error("调用系统服务获取用户套餐失败");
        }
        return 0;
    }

    private void addPackage(UserInfoBO userInfoBO, BasePlatform platform) {
        //pc端、移动端用户赠送免费三个月渲染
        if (PlatFormConstant.PC_2B.equals(platform.getPlatformCode()) ||
                PlatFormConstant.MOBILE_2B.equals(platform.getPlatformCode())) {
            //判断用户是否首次登录 ==> 根据登录日志
            SysUserLoginAggregated sl = sysUserLoginAggregatedService.queryUserAggregatedInfo(userInfoBO.getId(), platform.getId());

            if (sl == null) {
                //当前登录为移动端 ==> 需要判断是否已经登录过pc端
                if (PlatFormConstant.MOBILE_2B.equals(platform.getPlatformCode())) {
                    BasePlatform pc2b = basePlatformService.queryByPlatformCode(PlatFormConstant.PC_2B);
                    SysUserLoginAggregated pc2bSl = sysUserLoginAggregatedService.queryUserAggregatedInfo(userInfoBO.getId(), pc2b.getId());
                    if (pc2bSl != null) return;
                }
                //启用支付模块赠送免费三个月渲染
                logger.info("开始调用支付模块,赠送免费三个月渲染.");
                HttpClientUtil.doPostMethod(ResourceConfig.PAY_PACKAGE_ADD_URL, null, userInfoBO.getToken());
            }
        }

    }

    protected void updateIsLoginBeforeFlag(Long userId) {
        sysUserDao.updateIsLoginBeforeByUserId(userId);
    }


    //是否启用,合同是否到期等
    public List<CompanyInfoBO> checkCompanyUserInfo(UserInfoBO userInfoBO, BasePlatform platform, Long currentLoginCompanyId) {

        if (userInfoBO.getUserType() != null) {
            //厂商,企业内部用户
			/*if(SysUser.USER_TYPE_COMPANY == userInfoBO.getUserType() ||SysUser.USER_TYPE_COMPANY_INTERNAL_USER == userInfoBO.getUserType()) {
				checkCompanyContact(userInfoBO.getBusinessAdministrationId());
			}*///经销商
            /* else*/
            if (SysUser.USER_TYPE_FRANCHISER == userInfoBO.getUserType() || SysUser.USER_TYPE_INDEPENDENT_FRANCHISER == userInfoBO.getUserType()) {
                if (userInfoBO.getLastLoginCompanyId() == null && currentLoginCompanyId == null) {
                    //如果一个经销商多个企业,则返回登录企业列表
                    //List<CompanyInfoBO> companyList = sysUserService.getFranchiserCompanyList(userInfoBO.getMobile(), userInfoBO.getPassword(), platform.getPlatformCode(), 0);
                    List<CompanyInfoBO> companyList = sysUserService.getFranchiserCompanyList(userInfoBO.getMobile(), userInfoBO.getPassword(), platform.getPlatformCode());
                    //获取时间最晚创建的公司
                    if (companyList != null && companyList.size() > 1) {
                        List<CompanyInfoBO> lastCreatCompany = companyList.stream().sorted(Comparator.comparing(CompanyInfoBO::getGmtCreate)).collect(Collectors.toList());
                        currentLoginCompanyId = lastCreatCompany.get(0).getId();
                    }

                }//选择登录企业登录
                //针对一个经销商多个企业的情况:如果最近已经有选择登录企业,则下次登录时不需要再选择了.
                Long loginCompanyId = currentLoginCompanyId == null ? userInfoBO.getLastLoginCompanyId() : currentLoginCompanyId;
                List<UserInfoBO> franchiserUserList = sysUserDao.selectCompanyFranchiserUsers(userInfoBO.getMobile(), userInfoBO.getPassword());
                UserInfoBO companyUserInfoBO = null;
                if (franchiserUserList != null && loginCompanyId != null) {
                    //循环判断是否包含当前登录用户
                    List<UserInfoBO> collect = franchiserUserList.stream().filter(f -> {
                        return f.getId().intValue() == userInfoBO.getId();
                    }).collect(Collectors.toList());

                    if (collect != null && !collect.isEmpty()) {
                        //如果获取包含当前用户,不用选择
                    } else {
                        for (UserInfoBO user : franchiserUserList) {
                            if (user.getCompanyId() != null && user.getCompanyId().intValue() == loginCompanyId.intValue()) {
                                companyUserInfoBO = user;
                            }
                        }
                    }
                }

                if (companyUserInfoBO != null) {
                    /**
                     * u1有A企业(开通pc,mobile2b)和B企业(开通pc)
                     * u1在B企业登录pc
                     * 然后退出
                     * u1在A企业登录mobile2b,提示没权限,但其实是有.
                     * 因为上一次登录记住了B企业,所以在mobile2b会提示没权限,故加此判断.
                     * **/
                    UserJurisdiction userJurisdiction = userJurisdictionService.queryByUserIdAndPlatformId(companyUserInfoBO.getId(), platform.getId());
                    if (userJurisdiction != null) {
                        BeanUtils.copyProperties(companyUserInfoBO, userInfoBO);
                    }
                }//此经销商原来关联2个企业,后面删除一个企业,刚好这个企业又是上次登录的,此时查出来的用户信息为空
                else {
                    userInfoBO.setLastLoginCompanyId(null);
                }

                //当前登录企业与上一次不同,则需要更新记录
                if (userInfoBO.getLastLoginCompanyId() == null
                        || (userInfoBO.getLastLoginCompanyId() != null && currentLoginCompanyId != null && userInfoBO.getLastLoginCompanyId().longValue() != currentLoginCompanyId.longValue())) {
                    sysUserDao.updateLastLoginCompanyIdByMobile(userInfoBO.getMobile(), currentLoginCompanyId);
                }
                //一个经销商一个企业
                Long companyId = getFranchiserCompanyId(userInfoBO);
                if (companyId == null) {
                    //throw new BizException(ExceptionCodes.CODE_10010021, "未找到该经销商对应企业");
                    if (BasePlatform.PLATFORM_CODE_MOBILE_2B.equals(platform.getPlatformCode())
                            || BasePlatform.PLATFORM_CODE_PC_2B.equals(platform.getPlatformCode())) {
                        //检查用户是否绑定经销商企业
                        if (null == userInfoBO.getBusinessAdministrationId() ||
                                0 == userInfoBO.getBusinessAdministrationId().intValue()) {
                            int cid = biuldVirtualCompany(userInfoBO, platform);
                            if (cid < 1) {
                                throw new BizException(ExceptionCodes.CODE_10010021, "创建经销商企业失败!");
                            }
                        }
                    }
                } else {
                    BaseCompany baseCompany = baseCompanyService.queryById(companyId);
                    //设置经销商当前登录企业名称
                    if (baseCompany != null) {
                        userInfoBO.setCurrentLoginCompanyName(baseCompany.getCompanyName());
                    }
                    if (userInfoBO.getFranchiserAccountType() != null) {
                        //检查主账号,子账号,散号
                        //checkCompanyContact(companyId);
                        //checkPlatformJurisdiction(userInfoBO.getId(),platform.getId());
                        //如果是子账号,需要检查主账号
                        if (userInfoBO.getFranchiserAccountType() == 2) {
                            UserInfoBO mainUserInfoBO = this.getMainAccount(userInfoBO.getFranchiserGroupId());
                            if (mainUserInfoBO != null) {
                                //checkCompanyContact(mainUserInfoBO.getBusinessAdministrationId(),"主账号:");//主账号和子账号同属一家企业,子账号已验证过,此处不需要再验证
                                //检查主账号是否被禁用
                                checkAccountJurisdictionStatus(mainUserInfoBO.getId(), UserJurisdiction.JURISDICTION_STATUS_OPEN);
                            } else {
                                throw new BizException(ExceptionCodes.CODE_10010025, "主账号不存在,无法登录");
                            }
                        }
                    }
                }
            }
        }
        //检查是否开通平台权限
        checkPlatformJurisdiction(userInfoBO.getId(), platform.getId());
        //检验用户有效期限
        validUserEffectivityTime(userInfoBO);
        return null;
    }

    private String createFranchiseCode(Integer companyId) {

        /**
         * 生成经销商code
         * 生成规则：
         * 产品可见范围  + 7位数序列  例：（ABC0000001）
         *
         */
        try {
            List<Integer> businessTypeList = new ArrayList<Integer>();
            businessTypeList.add(2);
            StringBuffer commanyCodePrefix = new StringBuffer();//前缀
            StringBuffer commanyCodeSuffix = new StringBuffer();//后缀

            BaseCompany baseCompany = baseCompanyService.queryById(new Long(companyId));
            commanyCodePrefix.append(baseCompany.getCompanyCode());
            commanyCodePrefix.append("F");

            String commanyCodePrefixS = commanyCodePrefix.toString() + "%";
            String companyCodeMax = "";
            companyCodeMax = baseCompanyService.createCompanyCode(commanyCodePrefixS, businessTypeList, null);  /**获取当前前缀的最大序列号**/

            Integer commanyCode = 0;
            if (StringUtils.isNotEmpty(companyCodeMax)) {
                commanyCode = Integer.parseInt(companyCodeMax.substring(commanyCodePrefixS.length() - 1, companyCodeMax.length()));
                commanyCode++;
                commanyCodeSuffix.append(commanyCode);
            } else {
                commanyCodeSuffix.append("1");
            }

            int k = commanyCodeSuffix.length();
            for (int i = 0; i < 4 - k; i++) {
                commanyCodeSuffix.insert(0, "0");
            }

            return commanyCodePrefix + "" + commanyCodeSuffix;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private int biuldVirtualCompany(UserInfoBO userInfoBO, BasePlatform platform) {
        String prefixName = null;
        if (BasePlatform.PLATFORM_CODE_PC_2B.equals(platform.getPlatformCode())) {
            prefixName = "ST";
        } else if (BasePlatform.PLATFORM_CODE_MOBILE_2B.equals(platform.getPlatformCode())) {
            prefixName = "SG";
        } else {
            prefixName = "SD";
        }
        Date now = new Date();
        BaseCompany b = new BaseCompany();
        b.setCompanyName(prefixName + SysUserServiceImpl.getStringRandom(6));
        b.setBusinessType(2);
        b.setCompanyCode(createFranchiseCode(userInfoBO.getCompanyId().intValue()));
        b.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        b.setCreator(userInfoBO.getMobile());
        b.setModifier(userInfoBO.getMobile());
        b.setGmtCreate(now);
        b.setGmtModified(now);
        b.setIsVirtual(1);
        b.setIsDeleted(0);

        int resultId = baseCompanyService.save(b);
        Long companyId = b.getId();

        if (companyId != null) {
            //更新用户企业
            SysUser sysUser = new SysUser();
            sysUser.setId(userInfoBO.getId());
            sysUser.setBusinessAdministrationId(companyId);
            sysUserService.update(sysUser);

            //更新经销商对应厂商所绑定的企业
            BaseCompany baseCompany = baseCompanyService.queryById(userInfoBO.getCompanyId());
            if (baseCompany != null) {
                int updateRow = baseCompanyService.updatePid(companyId, baseCompany.getId());
            }
            return resultId;
        }
        return 0;
    }

    /**
     * 校验B端用户有效时长
     *
     * @param userInfoBO
     */
    public void validUserEffectivityTime(UserInfoBO userInfoBO) {

        logger.info("进入校验用户有效期begin ==>" + userInfoBO.getId());

        //获取当前时间
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date); //表示从当前时间开始算

        logger.info("用户是否首次登录 failureTime ==> {}" + (userInfoBO.getFailureTime() == null ? -1 : userInfoBO.getFailureTime()));

        //判断用户失效是时长是否为空?首次登录:不是首次登录
        if (userInfoBO.getValidTime() != null && userInfoBO.getFailureTime() == null) {
            //首次登录,计算用户失效时长 ==> 根据使用类型useType
            if (userInfoBO.getUseType() != null && userInfoBO.getUseType().intValue() == 0) {
                //试用用户 ==> 按天计算时间
                calendar.add(Calendar.DATE, userInfoBO.getValidTime());
            } else {
                //正式用户 ==> 按月计算
                calendar.add(Calendar.MONTH, userInfoBO.getValidTime());
            }
            userInfoBO.setFailureTime(calendar.getTime());
            sysUserService.updateFailureTimeByPrimaryKey(date, userInfoBO.getId(), calendar.getTime());
        } else if (userInfoBO.getFailureTime() != null && userInfoBO.getServicesFlag().intValue() == 0) {  //套餐用户不走此逻辑
            //检验用户是否过期
            if (userInfoBO.getFailureTime() != null && new Date().compareTo(userInfoBO.getFailureTime()) > 0) {
                throw new BizException(ExceptionCodes.CODE_10010040, "您的账号已过期，请联系客服!");
            }
        }

        //账号未过期,统计账号剩余天数
        if (userInfoBO.getFailureTime() != null && userInfoBO.getServicesFlag().intValue() == 0) {
            int leftTime = transformDay(userInfoBO.getFailureTime());
            userInfoBO.setLeftTime(leftTime);
        }

        if (userInfoBO.getIsLoginBefore() == 0) {
            sysUserService.updateFirstLoginTimeByPrimaryKey(date, userInfoBO.getId());
        }

    }

    private static int transformDay(Date expiryTime) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cal.set(Calendar.MINUTE, 0);//控制分
        cal.set(Calendar.SECOND, 0);//控制秒
        //cal.setTime(new Date());
        long time1 = cal.getTimeInMillis();//当天0点的时间
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(expiryTime);
        long time2 = ca2.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
       /* long lost = (time2 - time1) % (1000 * 3600 * 24);
        if (0!=lost){
            betweenDays++;
        }*/
        return Integer.parseInt(String.valueOf(betweenDays));
    }


    /**
     * 获取经销商企业id
     *
     * @param userInfoBO
     * @return
     */
    private Long getFranchiserCompanyId(UserInfoBO userInfoBO) {
        Long companyId = null;
        //查询经销商id
        Long franchiserId = userInfoBO.getBusinessAdministrationId();
        if (franchiserId != null) {
            BaseCompany franchiser = baseCompanyService.queryById(franchiserId);
            if (franchiser != null) {
                companyId = franchiser.getPid();
            }
        }
        return companyId;
    }

    private UserInfoBO getMainAccount(Integer franchiserGroupId) {
        UserQuery userQuery = new UserQuery();
        userQuery.setFranchiserAccountType(SysUser.FRANCHISER_ACCOUNT_TYPE_MAIN);
        userQuery.setFranchiserGroupId(franchiserGroupId);
        userQuery.setIsDeleted(0);
        List<UserInfoBO> list = sysUserDao.selectList(userQuery);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private void checkAccountJurisdictionStatus(Long userId, int jurisdictionStatus) {
        int count = userJurisdictionService.countByUserIdAndJurisdictionStatus(userId, jurisdictionStatus);
        if (count <= 0) {
            throw new BizException(ExceptionCodes.CODE_10010026, "主账号平台权限未开通或被禁用!");
        }
    }

    /**
     * 检查企业合同
     *
     * @param companyId
     * @param checkTipsPrefix
     */
    private void checkCompanyContact(Long companyId) {
        if (companyId == null) {
            throw new BizException(ExceptionCodes.CODE_10010015, "所属企业不存在!");
        }
        Date now = new Date();
        BaseCompany baseCompany = baseCompanyService.queryById(companyId);
        if (baseCompany == null) {
            throw new BizException(ExceptionCodes.CODE_10010015, "所属企业不存在!");
        } else {
            if (baseCompany.getContractEffectiveTime() != null && now.compareTo(baseCompany.getContractEffectiveTime()) < 0) {
                throw new BizException(ExceptionCodes.CODE_10010016, "企业合同未生效,请联系客服.");
            }
            if (baseCompany.getContractFailureTime() != null && now.compareTo(baseCompany.getContractFailureTime()) > 0) {
                throw new BizException(ExceptionCodes.CODE_10010017, "企业合同已到期,请联系客服.");
            }
        }
    }

    /**
     * 检查平台权限
     *
     * @param userId
     * @param platformId
     */
    public void checkPlatformJurisdiction(Long userId, Long platformId) {
        Date now = new Date();
        UserJurisdiction userJurisdiction = userJurisdictionService.queryByUserIdAndPlatformId(userId, platformId);
        if (userJurisdiction == null) {
            // throw new BizException(ExceptionCodes.CODE_10010018, "未开通此平台权限!");
            throw new BizException(userId.intValue(), "未开通此平台权限,请联系客服.");
        } else {
            if (userJurisdiction.getJurisdictionStatus() != null && userJurisdiction.getJurisdictionStatus().intValue() == UserJurisdiction.JURISDICTION_STATUS_FORBID) {
                //throw new BizException(ExceptionCodes.CODE_10010022, "平台权限已禁用!");
                throw new BizException(userId.intValue(), "平台权限已禁用,请联系客服.");
            }
            if (userJurisdiction.getJurisdictionStatus() != null && userJurisdiction.getJurisdictionStatus().intValue() == UserJurisdiction.JURISDICTION_STATUS_CLOSE) {
                //throw new BizException(ExceptionCodes.CODE_10010023, "平台权限已结束!");
                throw new BizException(userId.intValue(), "平台权限已结束,请联系客服.");
            }

            if (userJurisdiction.getJurisdictionEffectiveTime() != null && now.compareTo(userJurisdiction.getJurisdictionEffectiveTime()) < 0) {
                //throw new BizException(ExceptionCodes.CODE_10010019, "平台权限未生效,请联系客服.");
                throw new BizException(userId.intValue(), "平台权限未生效,请联系客服.");
            }

            if (userJurisdiction.getJurisdictionFailureTime() != null && now.compareTo(userJurisdiction.getJurisdictionFailureTime()) > 0) {
                //throw new BizException(ExceptionCodes.CODE_10010020, "平台权限已到期,请联系客服.");
                throw new BizException(userId.intValue(), "平台权限已到期,请续费开通!");
            }
        }
    }


    /**
     * 菜单树调整(深度优先遍历)
     *
     * @param menuList
     * @param nodeIndex
     */
    public void adjustMenuStructure(UserInfoBO userInfoBO) {
        if (userInfoBO.getMenuList() != null && userInfoBO.getMenuList().size() > 0) {
            List<UserMenuBO> resultMenuList = new ArrayList<UserMenuBO>();
            recursiveTree(resultMenuList, userInfoBO.getMenuList(), 0);
            userInfoBO.setMenuList(resultMenuList);
        }
    }

    private void recursiveTree(List<UserMenuBO> resultmenuList, List<UserMenuBO> menuList, int nodeIndex) {
        //System.out.println(menuList.get(nodeIndex).getId()+"-"+menuList.get(nodeIndex).getName());
        resultmenuList.add(menuList.get(nodeIndex));
        for (int i = nodeIndex; i < menuList.size(); i++) {
            UserMenuBO menu = menuList.get(i);
            if (menuList.get(nodeIndex).getId().longValue() == menu.getParentid().longValue()) {
                recursiveTree(resultmenuList, menuList, i);
            }
        }
    }

    /**
     * 生成菜单树
     *
     * @param menuList
     * @param nodeIndex
     */
    public void buildMenuTree(UserInfoBO userInfoBO) {
        if (userInfoBO.getMenuList() != null && userInfoBO.getMenuList().size() > 0) {
            UserMenuBO menu = userInfoBO.getMenuList().get(0);
            UserMenuTreeBO rootMenu = new UserMenuTreeBO(menu.getId(), menu.getCode(), menu.getName(), menu.getUrl());
            recursiveTree2(rootMenu, userInfoBO.getMenuList(), 0);
            userInfoBO.setMenuTree(rootMenu);
        }
    }

    private void recursiveTree2(UserMenuTreeBO rootMenu, List<UserMenuBO> menuList, int index) {
        for (int i = index; i < menuList.size(); i++) {
            UserMenuBO menu = menuList.get(i);
            if (rootMenu.getId().longValue() == menu.getParentid().longValue()) {
                UserMenuTreeBO subMenu = new UserMenuTreeBO(menu.getId(), menu.getCode(), menu.getName(), menu.getUrl());
                rootMenu.getSubMenuList().add(subMenu);
                recursiveTree2(subMenu, menuList, i);
            }
        }
    }


    public abstract Object cacheUserInfoToRedis(UserInfoBO userInfoBO);

    /**
     * 检查用户设备
     *
     * @param userInfoBO 用户信息
     * @param userLogin  登录参数
     */
    public abstract void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin);

    /**
     * 记录各平台用户登录日志
     *
     * @param userInfoBO
     * @param userLogin
     */
    public void saveLoginLog(UserInfoBO userInfoBO, UserLogin userLogin) {

        logger.info("save用户登录日志begin ==>{}" + userInfoBO.getId());

        //保存用户登录统计

        int result = sysUserLoginAggregatedService.increaseLoginCount(userInfoBO.getId(), userLogin.getPlatformId());

        if (result < 1) {

            SysUserLoginAggregated sl = buildUserLoginAggregated(userInfoBO, userLogin);

            sysUserLoginAggregatedService.addUserLoginAggregated(sl);
        }

        //保存用户登录明细
        SysUserLoginLog sysUserLoginLog = buildSysUserLoginLog(userInfoBO, userLogin);

        sysUserLoginLogService.insertSysUserLoginLog(sysUserLoginLog);
    }


    /**
     * 创建SysUserLoginLog对象
     *
     * @param userInfoBO
     * @param userLogin
     * @return
     */
    SysUserLoginLog buildSysUserLoginLog(UserInfoBO userInfoBO, UserLogin userLogin) {

        SysUserLoginLog sysUserLoginLog = new SysUserLoginLog();

        Date date = new Date();

        sysUserLoginLog.setUserId(userInfoBO.getId());
        sysUserLoginLog.setLoginTime(date);
        sysUserLoginLog.setCreator(userInfoBO.getMobile());
        sysUserLoginLog.setGmtCreate(date);
        sysUserLoginLog.setClientIp(userLogin.getClientIp());
        sysUserLoginLog.setServerIp(NetworkUtil.getLinuxLocalIp() + "/" + NetworkUtil.getLocalIP());
        sysUserLoginLog.setLoginDevice(userLogin.getLoginDevice());
        sysUserLoginLog.setSystemModel(userLogin.getSystemModel());
        sysUserLoginLog.setIsDeleted(0);
        sysUserLoginLog.setModifier(userInfoBO.getMobile());
        sysUserLoginLog.setGmtModified(date);
        sysUserLoginLog.setLoginType(1);
        sysUserLoginLog.setSysCode(date.getTime() + "_" + userInfoBO.getId());
        sysUserLoginLog.setPlatformId(userLogin.getPlatformId());

        return sysUserLoginLog;
    }


    /**
     * 组装统计登录用户登录
     *
     * @param userInfoBO
     * @param userLogin
     * @return
     */
    private SysUserLoginAggregated buildUserLoginAggregated(UserInfoBO userInfoBO, UserLogin userLogin) {

        SysUserLoginAggregated sl = new SysUserLoginAggregated();

        Date date = new Date();

        sl.setUserId(userInfoBO.getId());
        sl.setSysCode(date.getTime() + "_" + userInfoBO.getId());
        sl.setCreator(userInfoBO.getMobile());
        sl.setGmtCreate(date);
        sl.setModifier(userInfoBO.getMobile());
        sl.setGmtModified(date);
        sl.setIsDeleted(0);
        sl.setLoginCount(1);
        sl.setPlatformId(userLogin.getPlatformId());

        return sl;
    }

    /**
     * 设置用户其他信息
     *
     * @param userInfoBO
     */
    public abstract void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin);

    /**
     * 根据用户信息生成JWT Token
     *
     * @param userInfoBO 当前用户信息
     */
    public abstract void createUserJwtToken(UserInfoBO userInfoBO);

    /**
     * 设置用户权限
     *
     * @param userInfoBO 最终返回给前端的用户数据
     * @param platformId 平台id
     */
    public void setUserPermissions(UserInfoBO userInfoBO, Long platformId, String platformCode) {
        //      logger.info("设置用户权限begin");
        //1.获取用户角色组
        Set<SysUserRoleGroup> userRoleGroupSet = sysRoleGroupService.getUserRoleGroupByUserId(userInfoBO.getId());
        Set<Long> userRoleGroupIds = new HashSet<Long>();
        if (userRoleGroupSet != null && userRoleGroupSet.size() > 0) {
            for (SysUserRoleGroup userRoleGroup : userRoleGroupSet) {
                userRoleGroupIds.add(userRoleGroup.getRoleGroupId());
            }
        }
        //      logger.info("获取用户角色组:{}", userRoleGroupIds);

        //2.获取用户角色组对应角色
        Set<SysRoleGroupRef> roleGroupRefsSet = sysRoleService.getRoleGroupRefsByRoleGroupIds(userRoleGroupIds);

        //3.获取用户角色
        Set<SysUserRole> userRoleSet = sysRoleService.getUserRolesByUserId(userInfoBO.getId());

        //4.用户角色id合并去重
        Set<Long> userRoleIds = new HashSet<Long>();
        if (roleGroupRefsSet != null && roleGroupRefsSet.size() > 0) {
            for (SysRoleGroupRef roleGroupRef : roleGroupRefsSet) {
                userRoleIds.add(roleGroupRef.getRoleId());
            }
        }
        if (userRoleSet != null && userRoleSet.size() > 0) {
            for (SysUserRole userRole : userRoleSet) {
                userRoleIds.add(userRole.getRoleId());
            }
        }
//        logger.info("用户角色id合并去重:{}", userRoleIds);

        //5.1.获取角色权限关联数据
        List<String> roleCodeList = new ArrayList<>();

//        logger.info("调用商家后台过滤过期角色");
        ServicesRoleInfoBO infoBo = null;
        try {
            infoBo = servicesPurchaseBizService.getValidRoles(userInfoBO.getNickName(), userInfoBO.getId(), userRoleIds);
        } catch (Exception e) {
            logger.error("调用商家后台过滤过期角色失败userId=>{}" + userInfoBO.getId());
        }
        if (infoBo != null) {
            userInfoBO.setOldServiceFlag(infoBo.getOldServiceFlag());
            if (!infoBo.getOldServiceFlag()) {
                if (infoBo.getMaturityFlag() && StringUtils.isNotEmpty(infoBo.getCompanyName())) {
                    throw new BizException(ExceptionCodes.CODE_10010303, "您购买的套餐已到期，请联系" + infoBo.getCompanyName() + "进行续费");
                }
                userInfoBO.setServiceType(infoBo.getServiceType());
                userInfoBO.setMaturityFlag(infoBo.getMaturityFlag());
                userInfoBO.setRemainingDays(infoBo.getRemainingDays());
                userInfoBO.setServiceName(infoBo.getServiceName());
                if (infoBo.getTipsFlag()) {
                    userInfoBO.setTipsFlag(infoBo.getTipsFlag());
                }
            }
        }
        //       logger.info("调用商家后台过滤过期角色结束{}", infoBo);

        List<SysRole> roleList = sysRoleService.getRolesByRoleIds(infoBo == null ? userRoleIds : infoBo.getRoleIds());
        if (roleList != null && roleList.size() > 0) {
            List<SysRole> list = new ArrayList<SysRole>();
            for (SysRole r : roleList) {
                if (r.getPlatformId() != null && r.getPlatformId().longValue() == platformId.longValue()) {
                    list.add(r);
                    roleCodeList.add(r.getCode());
                }
            }
            userInfoBO.setRoleList(list);
            userInfoBO.setRoleCodeList(roleCodeList);
        }

        if ((userInfoBO.getRoleList() == null || userInfoBO.getRoleList().size() == 0)
                && null != platformCode && platformCode.equals(BasePlatform.PLATFORM_CODE_PC_HOUSE_DRAW)) {
            //户型绘制用户配置默认角色
            this.configureDefaultRoles(userInfoBO);
        }
        //5.2.获取角色权限关联数据
        Set<SysRoleFunc> roleFuncSet = sysRoleService.getRoleFuncByRoleIds(Objects.equals(userInfoBO.getServicesFlag(),1) ? infoBo == null ? null : infoBo.getRoleIds() : userRoleIds);

        //6.抽取权限id列表及字段权限
        List<Long> funcIdList = new ArrayList<Long>();
        Map<String, Set<String>> queryFieldsMap = new HashMap<String, Set<String>>();
        extractFuncIdArrayAndFieldsPermission(roleFuncSet, funcIdList, queryFieldsMap);

        //7.获取用户权限
        List<SysFunc> userFuncList = sysFuncService.getUserFuncs(funcIdList);

        //8.根据平台id设置用户权限(包括字段权限)
        setUserPlatformFuncs(userInfoBO, userFuncList, queryFieldsMap, platformId);
    }

    private void configureDefaultRoles(UserInfoBO userInfoBO) {
        //获取默认角色
        SysRole s = sysRoleService.getRoleByCode("COMMON_HOUSE_DRAW");
        List<SysRole> l = new ArrayList<>();
        if (s != null) {
            l.add(s);
        }
        userInfoBO.setRoleList(l);
    }

    private void setUserPlatformFuncs(UserInfoBO userInfoBO, List<SysFunc> userFuncList, Map<String, Set<String>> queryFieldsMap, Long platformId) {
        //       logger.info("根据平台id对用户权限数据进行过滤:{}", platformId);
        if (userFuncList != null && userFuncList.size() > 0) {
            List<UserMenuBO> menuList = new ArrayList<UserMenuBO>();
            Set<String> funcList = new HashSet<String>();
            Map<String, Set<String>> fieldsMap = new HashMap<String, Set<String>>();
            for (int i = 0; i < userFuncList.size(); i++) {
                SysFunc func = userFuncList.get(i);
                if (func.getPlatFormId() != null && func.getPlatFormId().longValue() == platformId.longValue()) {
                    UserMenuBO userPermissionBO = new UserMenuBO(func.getId(), func.getParentid(),
                            func.getName(), func.getUrl(), func.getKeyword(), func.getType(), func.getLevelid(), func.getCode(), func.getSequence());
                    //菜单
                    if (SysFunc.TYPE_MENU.equals(func.getType())) {
                        menuList.add(userPermissionBO);
                    }
                    //功能按钮
                    if (SysFunc.TYPE_FUNC.equals(func.getType())) {
                        funcList.add(userPermissionBO.getKeyword());
                    }
                    //字段
                    if (SysFunc.TYPE_FIELD.equals(func.getType())) {
                        Set<String> queryFieldsSet = queryFieldsMap.get(func.getId().toString());
						/*if(queryFieldsSet!=null) {
							userPermissionBO.setQueryfields(GsonUtil.toJson(queryFieldsSet));
						}*/
                        fieldsMap.put(func.getKeyword(), queryFieldsSet);
                    }
                }
            }
            userInfoBO.setMenuList(menuList);
            userInfoBO.setPermissions(funcList);
            userInfoBO.setQueryFields(fieldsMap);
        }
    }

    private void extractFuncIdArrayAndFieldsPermission(Set<SysRoleFunc> roleFuncSet, List<Long> funcIdList, Map<String, Set<String>> queryFieldsMap) {
        //获取用户所有角色对某个列表字段权限的并集
        if (roleFuncSet != null && roleFuncSet.size() > 0) {
            for (SysRoleFunc roleFunc : roleFuncSet) {
                String queryFields = roleFunc.getQueryFields();
                if (StringUtils.isNotBlank(queryFields)) {
                    String funcIdKey = roleFunc.getFuncId().toString();
                    if (queryFieldsMap.containsKey(funcIdKey)) {
                        Set<String> fieldsSet = queryFieldsMap.get(funcIdKey);
                        fieldsSet.addAll(GsonUtil.fromJson(queryFields, Set.class));
                    } else {
                        Set<String> unionQueryFiledsSet = new HashSet<String>();
                        unionQueryFiledsSet.addAll(GsonUtil.fromJson(queryFields, Set.class));
                        queryFieldsMap.put(funcIdKey, unionQueryFiledsSet);
                    }
                }
                funcIdList.add(roleFunc.getFuncId());
            }
        }
        //       logger.info("当前用户所有的字段权限:{}", queryFieldsMap);
    }

    /**
     * 检查用户账号
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    public UserInfoBO checkUserAccount(UserLogin userLogin) {
        String account = userLogin.getAccount();
        String password = userLogin.getPassword();
        logger.info("检查用户名账号:" + account);
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return null;
        }

        List<UserInfoBO> list = null;
        //账号密码登录
        UserQuery userQuery = new UserQuery();
        userQuery.setIsDeleted(0);
        userQuery.setPassword(password);
        userQuery.setPlatformType(userLogin.getPlatformType());
        if (userLogin.getLoginCompanyId() != null) {
            userQuery.setCompanyId(userLogin.getLoginCompanyId());
        }

        // 手机登录验证
        if (Validator.MOBILE.validate(account)) {
            userQuery.setMobile(account);
            list = sysUserDao.selectList(userQuery);
        }
        // 邮箱登录验证
        else if (Validator.EAMIL.validate(account)) {
            userQuery.setEmail(account);
            list = sysUserDao.selectList(userQuery);
        }
        // 用户昵称登录验证
        else {
            if (userQuery.getCompanyId() != null) {
                UserInfoBO userInfoBO = sysUserService.getUserInfoByNickName(account);
                if (userInfoBO.getUserType() == 3) {
                    //经销商用户切换企业,nick_name登录需要转换成手机号
                    userQuery.setMobile(userInfoBO.getMobile());
                }
            } else {
                userQuery.setNickName(account);
            }
            list = sysUserDao.selectList(userQuery);
            //用户名登录的验证
            if (list == null || list.size() <= 0) {
                //userQuery.setUserName(account);
                list = sysUserDao.selectList(userQuery);
            }
        }
        //经销商如果有多个账号,则需要取一个有开通平台权限的账号,否则有可能取到没有开通平台权限的账号,导致正常账号无法登录
        if (userLogin.getPlatformType() == SysUser.PLATFORM_TYPE_B) {
            if (list != null && list.size() > 1) {
                for (int i = 0; i < list.size(); i++) {
                    UserInfoBO user = list.get(i);
                    UserJurisdiction userJurisdiction = userJurisdictionService.queryByUserIdAndPlatformId(user.getId(), userLogin.getPlatformId());
                    if (userJurisdiction != null) {
                        return user;
                    }
                }
            }
        }


        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    public void setUserPic(UserInfoBO userInfoBO) {

        if (userInfoBO.getPicId() != null) {
            ResPic resPic = resPicService.get(userInfoBO.getPicId());
            if (resPic != null) {
                userInfoBO.setUserPic(resPic.getPicPath());
                userInfoBO.setPicPath(resPic.getPicPath());
            }
        }

        //获取用户头像
        if (StringUtils.isEmpty(userInfoBO.getPicPath())) {
            Integer sexValue = userInfoBO.getSex();
            if (sexValue == null) {
                sexValue = 1;
            }
            SysDictionary dictSearch = new SysDictionary();
            dictSearch.setIsDeleted(0);
            dictSearch.setType("userDefaultPicture");
            dictSearch.setValue(sexValue);
            SysDictionary dict = sysDictionaryService.getSysDictionary("userDefaultPicture", sexValue);
            if (dict != null && dict.getPicId() != null) {
                if (dict != null && dict.getPicId() != null) {
                    ResPic userPic = resPicService.get(dict.getPicId());
                    if (userPic != null && userPic.getPicPath() != null) {
                        userInfoBO.setUserPic(userPic.getPicPath());
                    }
                }
            }
        }
    }
}
