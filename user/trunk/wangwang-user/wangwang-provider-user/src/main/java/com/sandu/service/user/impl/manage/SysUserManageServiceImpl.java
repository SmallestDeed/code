package com.sandu.service.user.impl.manage;

import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.service.PayAccountService;
import com.sandu.api.operatorLog.model.SysUserOperatorLog;
import com.sandu.api.operatorLog.service.SysUserOperatorLogService;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.servicepurchase.model.ServicesAccountRel;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import com.sandu.api.servicepurchase.serivce.ServicesAccountRelService;
import com.sandu.api.servicepurchase.serivce.ServicesPriceService;
import com.sandu.api.servicepurchase.serivce.biz.ServicesPurchaseBizService;
import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.ResPicService;

import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.user.constant.UserTypeConstant;
import com.sandu.api.user.input.*;
import com.sandu.api.user.model.*;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.*;

import com.sandu.api.user.service.ServicesAccountRefService;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.UserJurisdictionService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.common.Md5;
import com.sandu.common.PageHelper;
import com.sandu.common.Validator;
import com.sandu.common.sms.Utils;
import com.sandu.service.user.dao.SysUserDao;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("sysUserManageService")
public class SysUserManageServiceImpl implements SysUserManageService {

	private Logger logger = LoggerFactory.getLogger(SysUserManageServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserJurisdictionService userJurisdictionService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
	private SysUserDao sysUserDao;

    @Autowired
    private SysUserOperatorLogService sysUserOperatorLogService;

    @Autowired
    private ServicesPurchaseBizService servicesPurchaseBizService;

    @Autowired
    private PayAccountService payAccountService;

    @Autowired
    private ServicesAccountRelService servicesAccountRelService;

    @Autowired
    private ServicesPriceService servicesPriceService;

    @Autowired
    private SysDictionaryService sysDictionaryService;


    @Override
    public  UserBatchVO sysUserBatchInsert(UserBatchAdd addUsers) {
        logger.info("购买套餐入参：{}",addUsers);
        if (addUsers!=null&&addUsers.getAccount()!=0){
            Map<Long,String> map=new HashMap<Long,String>();
            try{
                for (int i=0;i<addUsers.getAccount();i++){
                    SysUser sysUser=new SysUser();
                    //存储系统字段
                    sysUser.setPlatformType(2);//平台类型:1:C端用户,2:B端用户
                    sysUser.setServicesFlag(1);//套餐类型：0为普通生成账号,1为套餐购买生成的账号
                    sysUser.setGroupId(0);//组织Id为0(必填字段)
                    sysUser.setGmtCreate(new Date());
                    sysUser.setCreator("adminApi");
                    sysUser.setIsDeleted(0);
                    sysUser.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    //生成uuid
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    sysUser.setUuid(uuid);

                    //生成账号(用于登录)和给company_id字段赋值(update by wanghl)
                    if (3==addUsers.getUserType()){//经销商账号
                        //生成经销商用户的nick_name
                        sysUser.setNickName(this.createFranchiseUserNickName(addUsers.getCompanyId()));
                        BaseCompany baseCompany = baseCompanyService.queryById(addUsers.getCompanyId());
                        if (baseCompany.getBusinessType()==2){//经销商购买经销商账号
                            sysUser.setCompanyId(baseCompany.getPid()==null?addUsers.getCompanyId():baseCompany.getPid());
                            sysUser.setBusinessAdministrationId(addUsers.getCompanyId());
                        }else if(baseCompany.getBusinessType()==1){//厂商购买经销商账号
                            sysUser.setCompanyId(addUsers.getCompanyId());
                        }
                    }else{//非经销商账号
                        //生成非经销商用户的nick_name
                        sysUser.setNickName(this.createCompanyInUserNickName(addUsers.getCompanyId()));
                        //非经销商账号company_id等于business_administration_id
                        sysUser.setCompanyId(addUsers.getCompanyId());
                        sysUser.setBusinessAdministrationId(addUsers.getCompanyId());
                    }
                    //update by wanghl
                    sysUser.setUserType(addUsers.getUserType());

                    Md5 md5=new Md5();
                    String password = md5.getMd5Password("123456");
                    sysUser.setPassword(password);
                    sysUser.setMobile(addUsers.getMobile());
                    sysUser.setUseType(addUsers.getUseType());
                    sysUser.setUserSource(addUsers.getUserSource());
                    sysUser.setPasswordUpdateFlag(1);
                    logger.info("套餐购买生成账号入参：{}",sysUser);
                    //向sys_user表添加数据生成账号
                    Long userId = sysUserService.insertUser(sysUser);
                    logger.info("套餐购买生成账号结果：{}",userId);
                    //为生成的账号添加平台权限
                    List<Integer> roleLists1 = addUsers.getRoleLists();
                    Set<Long> roles=new HashSet<>();
                    for (Integer role:roleLists1) {
                        roles.add(role.longValue());
                    }
                    List<SysRole> sysRoles = sysRoleService.getRolesByRoleIds(roles);
                    if (sysRoles!=null&&sysRoles.size()>0){
                        Set<Long> platformIds=new HashSet<>();
                        for (SysRole role:sysRoles) {
                            platformIds.add(role.getPlatformId());
                        }
                        for (Long platformId:platformIds) {
                            saveUserJurisdiction(userId,platformId,sysUser.getCreator());
                        }
                    }
                    //为生成的账号添加角色
                    List<Integer> roleLists = addUsers.getRoleLists();
                    if (roleLists!=null&&roleLists.size()>0){
                        for (Integer roleId:roleLists) {

                            saveUserRole(roleId.longValue(),userId,sysUser.getCreator());
                        }
                    }
                    map.put(userId,sysUser.getNickName());
                }
                UserBatchVO userBatchVO=new UserBatchVO();
                userBatchVO.setMap(map);
                userBatchVO.setPurchaseRecordId(addUsers.getPurchaseRecordId());
                userBatchVO.setCompanyId(addUsers.getCompanyId());
                userBatchVO.setServiceId(addUsers.getServiceId());
                return  userBatchVO;
            } catch (Exception e) {
                logger.error("{}",e);
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除内部账号
     * @param userId 账户Id
     * @return
     */
    @Override
    public int deleteInternalUser(Long userId) {
        if(userId!=null){
            return sysUserService.deleteUser(userId);
        }
        return 0;
    }

    /**
     * 修改内部账号
     * @param userUpdate 修改内部账户入参
     * @param loginUser
     * @return
     */
    @Override
    public int updateInternalUser(InternalUserUpdate userUpdate,LoginUser loginUser) {
        //数据转换
        if(userUpdate!=null&&userUpdate.getUserId()!=null){
            SysUser sysUser=new SysUser();
            sysUser.setId(userUpdate.getUserId());
            if(StringUtils.isNotEmpty(userUpdate.getPassword())){
                //密码加密处理
                Md5 md5=new Md5();
                String password = md5.getMd5Password(userUpdate.getPassword());
                sysUser.setPassword(password);
            }
            sysUser.setUserName(userUpdate.getNickName());
            sysUser.setEmail(userUpdate.getEMail());
            sysUser.setMobile(userUpdate.getMobile());
            sysUser.setUserType(userUpdate.getUserType());
            sysUser.setPicId(userUpdate.getHeadPicId());
            /**用户新增地址 modifier wanghl**/
            sysUser.setFirstLoginTime(userUpdate.getFirstLoginTime());
            sysUser.setProvinceCode(userUpdate.getProvinceCode());
            sysUser.setCityCode(userUpdate.getCityCode());
            sysUser.setAreaCode(userUpdate.getAreaCode());
            sysUser.setStreetCode(userUpdate.getStreetCode());
            sysUser.setAddress(userUpdate.getAddress());
            /**end**/
            saveSysInfo(sysUser,loginUser);
            return sysUserService.updateUser(sysUser);
        }
        return 0;
    }

    /**
     * 获取内部账号详情
     * @param userId 账号
     * @return
     */
    @Override
    public InternalUserDetailVO getInternalUserDetail(Long userId) {
        if (userId==null){
            return null;
        }
        SysUser sysUser = sysUserService.selectUserByAccount(userId);
        logger.info("内部用户详情查询数据库返回结果sysUser:{}",sysUser);
        if (sysUser!=null){
            InternalUserDetailVO userDetail=new InternalUserDetailVO();
            userDetail.setUserId(userId);
            userDetail.setAccount(sysUser.getNickName());
            userDetail.setNickName(sysUser.getUserName());
            userDetail.setMobile(sysUser.getMobile());
            userDetail.setEMail(sysUser.getEmail());
            userDetail.setUserType(sysUser.getUserType());
            /**用户新增地址 modifier wanghl**/
            userDetail.setProvinceCode(sysUser.getProvinceCode());
            userDetail.setCityCode(sysUser.getCityCode());
            userDetail.setAreaCode(sysUser.getAreaCode());
            userDetail.setStreetCode(sysUser.getStreetCode());
            userDetail.setAddress(sysUser.getAddress());
            if (null!=sysUser.getFirstLoginTime()){
                userDetail.setFirstLoginTime(sysUser.getFirstLoginTime());
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = format.format(sysUser.getFirstLoginTime());
                userDetail.setFirstLoginTimeStr(dateStr);
            }
            /**end**/
            if (sysUser.getPicId()!=null){
                userDetail.setHeadPicId(sysUser.getPicId().toString());
                ResPic resPic = resPicService.get(sysUser.getPicId());
                if(null != resPic)
                    userDetail.setHeadPicPath(resPic.getPicPath());
            }
            return  userDetail;
        }
        return null;
    }

    /**
     * 查询内部账号列表
     * @param userQuery 列表查询入参
     * @return
     */
    @Override
    public List<InternalUserListVO> getInternalUserList(InternalUserQuery userQuery) {
        InternalUserQueryExtends queryExtends=new InternalUserQueryExtends();
        //对参数进行去空格操作
        if(userQuery.getCompanyId()!=null){
            queryExtends.setCompanyId(Long.valueOf(userQuery.getCompanyId().toString().trim()));
        }if(userQuery.getAccount()!=null){
            queryExtends.setAccount(userQuery.getAccount().trim());
        }if(userQuery.getMobile()!=null){
            queryExtends.setMobile(userQuery.getMobile().trim());
        }if(userQuery.getUserName()!=null){
            queryExtends.setUserName(userQuery.getUserName().trim());
        }
        BaseCompany baseCompany = baseCompanyService.queryById(userQuery.getCompanyId());
        //当企业类型为"厂商"，则内部账号不包括“经销商”
        if(1==baseCompany.getBusinessType()){
            queryExtends.setUserType(3);
        }
        //分页处理
        Integer totalCount = sysUserService.getInternalUserCount(queryExtends);
        PageHelper page = PageHelper.getPage(totalCount, userQuery.getLimit(), userQuery.getPage());
        queryExtends.setLimit(page.getStart());
        queryExtends.setPage(page.getPageSize());
        //进行查询
        List<InternalUserListVO> userList = sysUserService.selectInternalUserList(queryExtends);
        if(userList!=null){
            return userList;
        }
        return null;
    }

    /**
     * 查询内部账号数量
     * @param userQuery 数量查询入参
     * @return
     */
    @Override
    public int getInternalUserCount(InternalUserQuery userQuery) {
        InternalUserQueryExtends queryExtends=new InternalUserQueryExtends();
        //对参数进行去空格操作
        if(userQuery.getCompanyId()!=null){
            queryExtends.setCompanyId(Long.valueOf(userQuery.getCompanyId().toString().trim()));
        }if(userQuery.getAccount()!=null){
            queryExtends.setAccount(userQuery.getAccount().trim());
        }if(userQuery.getMobile()!=null){
            queryExtends.setMobile(userQuery.getMobile().trim());
        }if(userQuery.getUserName()!=null){
            queryExtends.setUserName(userQuery.getUserName().trim());
        }
        BaseCompany baseCompany = baseCompanyService.queryById(userQuery.getCompanyId());
        //当企业类型为"厂商"，则内部账号不包括“经销商”
        if(1==baseCompany.getBusinessType()){
            queryExtends.setUserType(3);
        }
        return sysUserService.getInternalUserCount(queryExtends);
    }


    /**
     * 删除经销商账号
     * @param userId 账号
     * @return
     */
    @Override
    public int deleteDealersUser(Long userId) {
        if (userId!=null){
            return sysUserService.deleteUser(userId);
        }
        return 0;

    }

    /**
     * 修改经销商账号
     * @param userUpdate 修改入参
     * @param loginUser
     * @return
     */
    @Override
    public int updateDealersUser(DealersUserUpdate userUpdate,LoginUser loginUser) {
        if(userUpdate!=null&&userUpdate.getUserId()!=null) {
            SysUser sysUser = new SysUser();
            sysUser.setId(userUpdate.getUserId());
            if(StringUtils.isNotEmpty(userUpdate.getPassword())){
                //密码加密处理
                Md5 md5=new Md5();
                String password = md5.getMd5Password(userUpdate.getPassword());
                sysUser.setPassword(password);
            }
            sysUser.setUserName(userUpdate.getNickName());
            sysUser.setEmail(userUpdate.getEMail());
            sysUser.setMobile(userUpdate.getMobile());
            sysUser.setUserType(userUpdate.getUserType());
            sysUser.setPicId(userUpdate.getHeadPicId());
            sysUser.setCompanyId(userUpdate.getCompanyId());
            /**用户新增地址 modifier wanghl**/
            sysUser.setFirstLoginTime(userUpdate.getFirstLoginTime());
            sysUser.setProvinceCode(userUpdate.getProvinceCode());
            sysUser.setCityCode(userUpdate.getCityCode());
            sysUser.setAreaCode(userUpdate.getAreaCode());
            sysUser.setStreetCode(userUpdate.getStreetCode());
            sysUser.setAddress(userUpdate.getAddress());
            /**end**/
            saveSysInfo(sysUser, loginUser);
            return  sysUserService.updateUser(sysUser);
        }
            return 0;
    }

    /**
     * 获取经销商账号详情
     * @param userId 账号
     * @return
     */
    @Override
    public DealersUserDetailVO getDealersUserDetail(Long userId) {
        SysUser sysUser = sysUserService.selectUserByAccount(userId);
        if (sysUser!=null){
            DealersUserDetailVO userDetail=new DealersUserDetailVO();
            userDetail.setUserId(userId);
            userDetail.setAccount(sysUser.getNickName());
            userDetail.setNickName(sysUser.getUserName());
            userDetail.setMobile(sysUser.getMobile());
            userDetail.setEMail(sysUser.getEmail());
            userDetail.setUserType(sysUser.getUserType());
            /**用户新增地址 modifier wanghl**/
            userDetail.setProvinceCode(sysUser.getProvinceCode());
            userDetail.setCityCode(sysUser.getCityCode());
            userDetail.setAreaCode(sysUser.getAreaCode());
            userDetail.setStreetCode(sysUser.getStreetCode());
            userDetail.setAddress(sysUser.getAddress());
            if (null!=sysUser.getFirstLoginTime()){
                userDetail.setFirstLoginTime(sysUser.getFirstLoginTime());
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = format.format(sysUser.getFirstLoginTime());
                userDetail.setFirstLoginTimeStr(dateStr);
            }

            /**end**/
            if(null != sysUser.getBusinessAdministrationId()){
                userDetail.setFranchiserId(sysUser.getBusinessAdministrationId().toString());
                BaseCompany baseCompany = baseCompanyService.queryById(sysUser.getBusinessAdministrationId());
                if(null != baseCompany)
                    userDetail.setFranchiserName(baseCompany.getCompanyName());
            }
            if (sysUser.getPicId()!=null){
                userDetail.setHeadPicId(sysUser.getPicId().toString());
                ResPic resPic = resPicService.get(sysUser.getPicId());
                if(null != resPic)
                    userDetail.setHeadPicPath(resPic.getPicPath());
            }
            return  userDetail;
        }
        return null;
    }

    /**
     * 查询经销商账号数量
     * @param userQuery 查询条件
     * @return
     */
    @Override
    public int getDealersUserCount(DealersUserQuery userQuery) {
        if (userQuery.getCompanyId()==null){
            return 0;
        }
        DealersUserQueryPO userQueryPo=new DealersUserQueryPO();
        //对参数进行去空格操作
        if(userQuery.getAccount()!=null){
            userQueryPo.setAccount(userQuery.getAccount().trim());
        }if(userQuery.getDealersCompanyId()!=null){
            userQueryPo.setDealersCompanyId(userQuery.getDealersCompanyId());
        }if(userQuery.getMobile()!=null){
            userQueryPo.setMobile(userQuery.getMobile().trim());
        }if(userQuery.getUserName()!=null){
            userQueryPo.setUserName(userQuery.getUserName().trim());
        }if(userQuery.getCompanyId()!=null){
            userQueryPo.setCompanyId(Long.valueOf(userQuery.getCompanyId().toString().trim()));
        }

        return sysUserService.getDealersUserCount(userQueryPo);
    }

    /**
     * 查询经销商账号列表
     * @param userQuery 列表查询条件
     * @return
     */
    @Override
    public List<DealersUserListVO> getDealersUserList(DealersUserQuery userQuery) {
        if (userQuery.getCompanyId()==null){
            return null;
        }
        DealersUserQueryPO userQueryPo=new DealersUserQueryPO();
        if(userQuery.getAccount()!=null){
            userQueryPo.setAccount(userQuery.getAccount().trim());
        }if(userQuery.getDealersCompanyId()!=null){
            userQueryPo.setDealersCompanyId(userQuery.getDealersCompanyId());
        }if(userQuery.getMobile()!=null){
            userQueryPo.setMobile(userQuery.getMobile().trim());
        }if(userQuery.getUserName()!=null){
            userQueryPo.setUserName(userQuery.getUserName().trim());
        }if(userQuery.getCompanyId()!=null){
            userQueryPo.setCompanyId(Long.valueOf(userQuery.getCompanyId().toString().trim()));
        }

        int totalCount = sysUserService.getDealersUserCount(userQueryPo);
        PageHelper page = PageHelper.getPage(totalCount, userQuery.getLimit(), userQuery.getPage());
        userQueryPo.setLimit(page.getStart());
        userQueryPo.setPage(page.getPageSize());
        return sysUserService.selectDealersUserList(userQueryPo);
    }


    @Override
    public List<Integer> getAllUserType(Long companyId) {
        return sysUserService.getAllUserType(companyId);
    }

    @Override
    public Long getUserCompanyIdByAccount(String account) {
        if(account!=null){
            return sysUserService.getUserCompanyIdByAccount(account);
        }
        return null;
    }

    @Override
    public UserCompanyInfoVO getCompanyInfoByUserId(Long userId) {
        return sysUserService.getCompanyInfoByUserId(userId);
    }

    @Override
    public int dealUserHeadPic(DealUserHeadPic headPic) {
        if(headPic==null||headPic.getModifier()==null||headPic.getUserId()==null){
            return 0;
        }
        return sysUserService.dealUserHeadPic(headPic);
    }


    /**
     * 为账号添加角色
     * @param roleId 角色Id
     * @param userId 账号Id
     * @param operator 操作者
     */
    private void saveUserRole(Long roleId, Long userId,String operator) {
        Date date = new Date();
        SysUserRole sr = new SysUserRole();
        sr.setUserId(userId);
        sr.setRoleId(roleId);
        sr.setCreator(operator);
        sr.setGmtCreate(date);
        sr.setModifier(operator);
        sr.setGmtModified(date);
        sr.setIsDeleted(0);
        sysRoleService.saveUserRole(sr);
    }

    /**
     *  为账号添加平台权限
     * @param userId 用户Id
     * @param platformId  平台Id
     * @param operator 操作人员
     */
    private void saveUserJurisdiction(Long userId, Long platformId, String operator) {
        //构建UserJurisdiction
        Date date = new Date();
        UserJurisdiction userJurisdiction = new UserJurisdiction();
        userJurisdiction.setIsDeleted(0);
        userJurisdiction.setCreator(operator);
        userJurisdiction.setGmtCreate(date);
        userJurisdiction.setModifier(operator);
        userJurisdiction.setGmtModified(date);
        userJurisdiction.setPlatformId(platformId);
        userJurisdiction.setUserId(userId);
        userJurisdiction.setJurisdictionStatus(1);
        userJurisdiction.setModifierUserId(userId);
        userJurisdictionService.save(userJurisdiction);
    }



    /**
     * 存储系统字段
     * @param sysUser  账号对象
     * @param loginUser 当前登录人员
     */
   private static void saveSysInfo(SysUser sysUser,LoginUser loginUser){
       if(sysUser != null){
           if(sysUser.getId() == null){
               sysUser.setGmtCreate(new Date());
               sysUser.setCreator(loginUser.getLoginName());
               sysUser.setIsDeleted(0);
               if(sysUser.getSysCode()==null || "".equals(sysUser.getSysCode())){
                   sysUser.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
               }
           }
           sysUser.setGmtModified(new Date());
           sysUser.setModifier(loginUser.getLoginName());
       }
   }

   @Override
   public SysUser get2BUser(String account, String password,Long companyId) {
       logger.info("检查用户名账号:"+account);
       if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
       	return null;
       }
       
       List<UserInfoBO> list = null;
       //账号密码登录
       UserQuery userQuery = new UserQuery();
       userQuery.setIsDeleted(0);
       userQuery.setPassword(password);
       userQuery.setPlatformType(2);
       if(companyId!=null) {
    	   userQuery.setCompanyId(companyId);
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
           userQuery.setNickName(account);
           list = sysUserDao.selectList(userQuery);
           //用户名登录的验证
           if (list == null || list.size() <= 0) {
               userQuery.setUserName(account);
               list = sysUserDao.selectList(userQuery);
           }
       }
       
       if (list != null && list.size() > 0) {
           return list.get(0);
       }
		return null;
	}

    /**
     * 经销商默认可见产品范围与品牌
     * @author chenqiang
     * @param mobile 手机号
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    public boolean checkPhone(String mobile){
        boolean bool = true;

        int count = sysUserDao.checkPhone(mobile);
        if(count > 0)
            bool = false;

        return bool;
    }

    /**
     * 校验独立经销商手机号是否唯一
     * @author chenqiang
     * @param mobile 手机号
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    public boolean checkPhoneDuLI(String mobile){
        boolean bool = true;
        int count = 0;

        List<Integer> userTypeList = new ArrayList<>();
        userTypeList.add(UserTypeConstant.USER_TYPE_DEALERS);
        userTypeList.add(UserTypeConstant.USER_TYPE_DEALERS_D);

        //判断在所有(除去经销商用户、独立经销商用户)用户中是否存在
        count = sysUserDao.selectUserByUserTypeMobile(null, userTypeList, mobile);

        if (count > 0) {
            bool = false;
        } else {

            userTypeList = new ArrayList<>();
            userTypeList.add(UserTypeConstant.USER_TYPE_DEALERS_D);

            //判断在（独立经销商用户)用户中是否存在
            count = sysUserDao.selectUserByUserTypeMobile(userTypeList, null, mobile);

            if (count > 0) {
                bool = false;
            } else {
                bool = true;
            }
        }

        return bool;
    }

    /**
     * 查询B端 经销商手机号 在当前企业下是否唯一
     * @author chenqiang
     * @param mobile 手机号
     * @param companyId 企业id
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    public boolean checkFranchiserPhoneOne(String mobile,Integer companyId){
        boolean bool = true;

        int count = sysUserDao.checkFranchiserPhoneOne(mobile,companyId);
        if(count > 0)
            bool = false;

        return bool;
    }

    /**
     * 查询B端 经销商手机号 在非经销商用户是否存在
     * @author chenqiang
     * @param mobile 手机号
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    public boolean checkFranchiserPhoneTwo(String mobile){
        boolean bool = true;

        int count = sysUserDao.checkFranchiserPhoneTwo(mobile);
        if(count > 0)
            bool = false;

        return bool;
    }

    /**
     * 密码md5加密规则
     * @author chenqiang
     * @param password 明文密码
     * @return 加密后的字符串
     */
    public String getMd5Password(String password){
        return md5(md5("WeB"+password));
    }

    /**
     * 根据手机号，修改相同手机号的 经销商用户 的 密码
     * @author chenqiang
     * @param mobile 手机号
     * @return 是否修改成功：true:成功
     * @date 2018/6/21
     */
    public boolean updateSysUserByMobile(String mobile,String password){
        boolean bool = true;

        int count = sysUserDao.updateSysUserByMobile(mobile,password);
        if(count <= 0)
            bool = false;

        return bool;
    }

    /**
     * 绑定经销商企业
     * @author chenqiang
     * @param userId id
     * @param franchiserId 企业id
     * @return 是否修改成功：true:成功
     * @date 2018/6/21
     */
    public boolean bindCompanyFranchiser(Long userId,Long franchiserId){
        return sysUserService.addUserFranchiser(userId, franchiserId);
    }

    /**
     * 删除经销商时，解绑 账号
     * @author chenqiang
     * @param franchiserId 经销商id
     * @param loginName    登录名
     * @return 是否解绑 ：true:成功
     * @date 2018/6/21
     */
    public boolean notBindUserToFranchiser(Long franchiserId,String loginName){
        boolean bool = true;

        try {
            sysUserDao.notBindUserToFranchiser(franchiserId,loginName);
        }catch (Exception e){
            logger.error("解绑账号出错",e);
            bool = false;
        }

        return bool;
    }


    /**
     * 处理套餐升级带来的失效时间和有效时长变更
     * @author wanghl
     * @param userId 用户Id
     * @param failureTime 失效时间
     * @return boolean (true:操作成功；false:操作失败)
     */
    @Override
    public boolean dealUserFailureTimeAndValidTime(Long userId, Date oldAccountFailureTime,Date beginTime,Date failureTime) {
        try{
            if (null!=userId&&null!=failureTime){
                SysUser sysUser = sysUserService.getUserById(userId);
                //初次登录，同步账号失效时间和套餐失效时间(按照逻辑，此判断不会起作用，以防万一保留)
                if (null!=sysUser&&null==sysUser.getFirstLoginTime()){
                    int result = sysUserService.modifyServiceUserFirstLogin(new Date(), userId, failureTime);
                    if (result>0){
                        return true;
                    }
                    logger.error("初次登录同步账号到期时间失败");
                    return false;
                }


                logger.info("套餐升级修改用户失效时间和有效时长:通过Id查询用户结果：{}",sysUser);
                if (null!=sysUser&&null!=sysUser.getFirstLoginTime()&&null==sysUser.getFailureTime()){
                    logger.info("套餐升级修改用户失效时间和有效时长:用户没有失效时间处理...开始");
                    Date firstLoginTime = sysUser.getFirstLoginTime();
                    String validTime = this.getYearMonthDayFromDateToDate(firstLoginTime, failureTime);
                    SysUserOperatorLog operatorLog=new SysUserOperatorLog();
                    operatorLog.setUserId(sysUser.getId());
                    operatorLog.setEventCode(100);
                    operatorLog.setIsDeleted(0);
                    operatorLog.setValue(validTime);
                    operatorLog.setOperatorTime(new Date());
                    operatorLog.setOperator(sysUser.getNickName());
                    operatorLog.setRemark("套餐购买账号套餐升级记录有效时间");
                    int result = sysUserOperatorLogService.addLog(operatorLog);
                    if (result<=0){
                        logger.error("向sys_user_operator_log表添加数据失败");
                        return false;
                    }
                    int result2 = sysUserService.updateUserFailureTime(failureTime, sysUser.getNickName(), sysUser.getId());
                    if (result2>0){
                        return true;
                    }
                    logger.error("修改失效时间失败失败");
                    logger.info("套餐升级修改用户失效时间和有效时长:用户没有失效时间处理...结束");
                    return false;
                }
                if (null!=sysUser&&null!=sysUser.getFirstLoginTime()&&null!=sysUser.getFailureTime()){
                    logger.info("套餐升级修改用户失效时间和有效时长:用户有失效时间处理...开始");
//                    Date formerFailureTime = sysUser.getFailureTime();
                    String oldUsedTime = null;
                    if (Objects.nonNull(oldAccountFailureTime)){
                        //用户上个套餐使用的时长
                        oldUsedTime = this.getYearMonthDayFromDateToDate(sysUser.getFirstLoginTime(), oldAccountFailureTime);
                    }
                    //得到新失效时间到原失效时间之间的差值：xxYxxMxxD
                    //String validTime = this.getYearMonthDayFromDateToDate(formerFailureTime, totalVaildTime);
                    //计算更换套餐使用时长
                    String validTime = this.getYearMonthDayFromDateToDate(beginTime,failureTime);
                    //将上个套餐使用的时长和新套餐的时长累加起来
                    String finalVaildTime =  sumOldUsedTimeAndCurVaildTime(oldUsedTime,validTime);
                    if (StringUtils.isNotEmpty(finalVaildTime)){
                        //删除旧的有效时间
                        int update = sysUserOperatorLogService.updateByUserId(sysUser.getId());
                    }
                    //构造新增operatorLog表入参
                    SysUserOperatorLog operatorLog=new SysUserOperatorLog();
                    operatorLog.setUserId(sysUser.getId());
                    operatorLog.setEventCode(100);
                    operatorLog.setIsDeleted(0);
                    operatorLog.setValue(finalVaildTime);
                    operatorLog.setOperatorTime(new Date());
                    operatorLog.setOperator(sysUser.getNickName());
                    operatorLog.setRemark("套餐升级");
                    int result = sysUserOperatorLogService.addLog(operatorLog);
                    if (result<=0){
                        logger.error("向sys_user_operator_log表添加数据失败");
                        return false;
                    }
                    int result2 = sysUserService.updateUserFailureTime(failureTime, sysUser.getNickName(), sysUser.getId());
                    if (result2>0){
                        return true;
                    }
                    logger.error("修改失效时间失败失败");
                    logger.info("套餐升级修改用户失效时间和有效时长:用户有失效时间处理...结束");
                    return false;
                }
                logger.error("该用户不满足套餐升级");
                return false;
            }
            logger.error("参数异常userId:{},failureTime:{}",userId,failureTime);
            return false;
        }catch (Exception e){
            logger.error("处理套餐升级失效时间处理失败",e);
            return false;
        }
    }

    private String sumOldUsedTimeAndCurVaildTime(String oldUsedTime, String validTime) {
        if (StringUtils.isEmpty(oldUsedTime)){
            return validTime;
        }

        StringBuilder sb = new StringBuilder();

        //计算年限
        this.totalYear(sb,oldUsedTime,validTime);

        //计算月份
        this.totalMonth(sb,oldUsedTime,validTime);

        //计算天数
        this.totalDate(sb,oldUsedTime,validTime);

        return sb.toString();
    }

    private void totalDate(StringBuilder sb, String oldUsedTime, String validTime) {
        this.getVaildTime(sb,oldUsedTime,validTime,"D","M");

    }

    private void getVaildTime(StringBuilder sb, String oldUsedTime, String validTime, String type,String beforeType) {
        int finalD = 0;
        if (oldUsedTime.indexOf(type) > 0){
            int year = this.getAgeLimit(oldUsedTime,type,beforeType);
            int vY = 0;
            if (validTime.indexOf(type) > 0){
                vY = this.getAgeLimit(validTime,type,beforeType);
            }
            finalD = year + vY;
        }else{
            if (validTime.indexOf(type) > 0){
                int vY = this.getAgeLimit(validTime,type,beforeType);
                finalD = vY;
            }
        }
        if (finalD > 0){
            sb.append(finalD);
            sb.append(type);
        }
    }

    private void totalMonth(StringBuilder sb, String oldUsedTime, String validTime) {
        this.getVaildTime(sb,oldUsedTime,validTime,"M","Y");
//        int finalM = 0;
//        if (oldUsedTime.indexOf("M") > 0){
//            int year = this.getAgeLimit(oldUsedTime,"M");
//            if (validTime.indexOf("M") > 0){
//                int vY = this.getAgeLimit(validTime,"M");
//                finalM = year + vY;
//            }
//        }else{
//            if (validTime.indexOf("M") > 0){
//                int vY = this.getAgeLimit(validTime,"M");
//                finalM = vY;
//            }
//        }
//        if (finalM > 0){
//            sb.append(finalM);
//            sb.append("M");
//        }
    }

    private void totalYear(StringBuilder sb, String oldUsedTime, String validTime) {
        this.getVaildTime(sb,oldUsedTime,validTime,"Y",null);
    }

    private int getAgeLimit(String oldUsedTime,String type,String beforeType) {
        int yIndex = oldUsedTime.indexOf(type);
        int y = Integer.parseInt(oldUsedTime.substring(StringUtils.isEmpty(beforeType) ? 0 : oldUsedTime.indexOf(beforeType)+1, yIndex));
        return y;
    }


    /**
     * 计算两个时间点之间相隔xx年xx月xx天
     * @param fromDate 前一个时间点
     * @param toDate 后一个时间点
     * @return xx年xx月xx天
     */
    private static String getYearMonthDayFromDateToDate(Date fromDate,Date toDate){
        StringBuilder validTimeStr=new StringBuilder();
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear-fromYear;
        int month = toMonth-fromMonth;
        int day = toDay-fromDay;
        if (day<0){
            month=month-1;
            if (toMonth==1||toMonth==3||toMonth==5||toMonth==7||toMonth==8||toMonth==11){//Calendar的月从0开始
                day=day+31;
            }else if(toMonth==4||toMonth==6||toMonth==9||toMonth==10||toMonth==12){
                day=day+30;
            }else{
                day=day+28;
            }
        }
        if (month<0){
            year=year-1;
            month=month+12;
        }
        if (year>0){
            validTimeStr.append(year);
            validTimeStr.append("Y");
        }if (month>0){
            validTimeStr.append(month);
            validTimeStr.append("M");
        }if (day>0){
            validTimeStr.append(day);
            validTimeStr.append("D");
        }
        return validTimeStr.toString();
    }

    /**
     * md5加密
     * @author chenqiang
     * @param plainText
     * @return
     */
    private String md5(String plainText) {
        String str="";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str=buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }



    /**
     * add by wanghl
     * 生成经销商用户登录名
     * 生成规则：
     * 经销商所属厂商企业编码 + F0000A + 5位数 例：（DTB0000001F0000A00001）
     *
     */
    public synchronized String createFranchiseUserNickName(Long companyId) {
        String nickName = null;
        try{
            StringBuffer nameCodePrefix = new StringBuffer();//前缀
            StringBuffer nameCodeSuffix = new StringBuffer();//后缀

            BaseCompany baseCompany = baseCompanyService.queryById(companyId);
            logger.info("生成经销商账号登录名：经销商企业：{}",baseCompany);
            nameCodePrefix.append(baseCompany.getCompanyCode()+"F0000");
            nameCodePrefix.append("A");

            String nameCodePrefixS = nameCodePrefix.toString()+"%";
            String nameCodeMax = sysUserService.getMaxFranchiserAccountCode(nameCodePrefixS,companyId==null?0:companyId);  /**获取当前前缀的最大序列号**/
            logger.info("生成经销商账号登录名：登录名后5位最大的账号：{}",nameCodeMax);
            Integer nameCode = 0;
            if (StringUtils.isNotEmpty(nameCodeMax)) {
                nameCode = Integer.parseInt(nameCodeMax.substring(nameCodePrefixS.length()-1,nameCodeMax.length()));
                nameCode ++;
                nameCodeSuffix.append(nameCode);
                logger.info("生成经销商账号登录名：登录名后5位：{}",nameCodeSuffix);
            } else {
                nameCodeSuffix.append("1");
                logger.info("生成经销商账号登录名：登录名后缀数字：{}",nameCodeSuffix);
            }

            int k = nameCodeSuffix.length();
            for (int i = 0 ; i < 5 - k ; i++) {
                nameCodeSuffix.insert(0,"0");
            }
            nickName= nameCodePrefix+""+nameCodeSuffix;
            logger.info("生成经销商账号登录名：登录名后：{}",nickName);
            return nickName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * add by wanghl
     * 生成企业内部用户登录名
     * 生成规则：
     * 企业编码 + EIU + 5位数 例：（DTB0000001EIU00001）
     *
     */
    public synchronized String createCompanyInUserNickName(Long companyId) {
        BaseCompany baseCompany = new BaseCompany();
        String nickName = null;
        try {
            StringBuffer nickNameCodePrefix = new StringBuffer();//前缀
            StringBuffer nickNameCodeSuffix = new StringBuffer();//后缀

            baseCompany = baseCompanyService.queryById(companyId);
            nickNameCodePrefix.append(baseCompany.getCompanyCode());
            nickNameCodePrefix.append("EIU");

            String commanyCodePrefixS = nickNameCodePrefix.toString() + "%";
            String userNameCodeMax = "";
            userNameCodeMax = sysUserService.getMaxCompanyInUserNameCode(commanyCodePrefixS, companyId==null?0:companyId);  /**获取当前前缀的最大序列号**/
            logger.info("生成账号登录名：登录名后5位最大的账号：{}",userNameCodeMax);
            Integer nameCode = 0;
            if (StringUtils.isNotEmpty(userNameCodeMax)) {
                nameCode = Integer.parseInt(userNameCodeMax.substring(commanyCodePrefixS.length()-1, userNameCodeMax.length()));
                nameCode++;
                nickNameCodeSuffix.append(nameCode);
                logger.info("生成内部账号登录名：登录名后缀数字：{}",nickNameCodeSuffix);
            } else {
                nickNameCodeSuffix.append("1");
            }

            int k = nickNameCodeSuffix.length();
            for (int i = 0; i < 4 - k; i++) {
                nickNameCodeSuffix.insert(0, "0");
            }

            nickName = (nickNameCodePrefix + "" + nickNameCodeSuffix);
            return nickName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> checkUserIsFirstLogin(UserLogin userLogin) {
        //获取用户信息
        //SysUser sysUser = sysUserDao.selectByPrimaryKey(userId.intValue());
        SysUser sysUser = this.get2BUser(userLogin.getAccount(), userLogin.getPassword(), userLogin.getLoginCompanyId());
        Map<String, Object> resultMap = null;
        if (sysUser != null && sysUser.getIsLoginBefore().intValue() == 0) {
            //用户首次登录 =>1.获取赠送渲染月份 2.获取赠送度币
            resultMap = new HashMap<>();
            Map<String, Object> map = servicesPurchaseBizService.getFreeRenderDurationByUserid(sysUser.getId());
            if(!map.isEmpty()){
                resultMap.put("mobile2bGiveRender", map.get("freeRenderDuration"));
                resultMap.put("giveSanduCurrency",map.get("sanduCurrency"));
            }
        }
        resultMap.put("isLoginBefore",sysUser.getIsLoginBefore());
        return resultMap;
    }

    @Override
    public Map<String, Object> getPackageAccountTime(Long id) {
        Map<String,Object> resultMap = new HashMap<>();
        ServicesAccountRel account = servicesAccountRelService.getAccountByUserId(id.intValue());
        if (Objects.nonNull(account)){
            //获取套餐时长信息
            List<ServicesPrice> lists = servicesPriceService.findServicesPriceList(account.getServicesId());
            if (Objects.nonNull(lists)){
                ServicesPrice servicesPrice = lists.get(0);
                //计算套餐用户时长
                countPackageUserTime(servicesPrice,resultMap);
                //账号有效时间
                resultMap.put("failureTime",account.getEffectiveEnd());
                return resultMap;
            }
        }
        return resultMap;
    }

    @Override
    public void restoreSysUserData() {
        List<SysUser> sysUsers = sysUserService.getRestoreUserData();
        logger.info("获取到总的用户数量: =>{}" + sysUsers.size());
        for (int i = 0; i < sysUsers.size(); i++) {
            SysUser user = sysUsers.get(i);
            //获取用户的公司信息
            BaseCompany company = baseCompanyService.queryById(user.getCompanyId());
            if(Objects.nonNull(company)){
                //获取该企业可以创建的用户类型
                SysDictionary type = sysDictionaryService.getSysDictionary("brandBusinessType", company.getBusinessType());
                //获取可以创建的用户类型
                List<SysDictionary> listTypes = sysDictionaryService.getListByType(type.getValuekey());
                if (!CollectionUtils.isEmpty(listTypes)){
                    //判断当前用户类型是否为该公司下的可创建用户类型
                    if (!listTypes.stream().map(SysDictionary::getAtt1).anyMatch(val -> Objects.equals(val,user.getUserType().toString()))){
                        Integer userType = null;
                        switch (company.getBusinessType()){
                            case 9:
                                //独立经销商
                                userType = 14;
                                break;
                            case 4:
                                userType = 5;
                                break;
                            case 5:
                                userType = 5;
                                break;
                            case 6:
                                userType = 5;
                                break;
                            case 7:
                                userType = 13;
                                break;
                        }

                        if (Objects.nonNull(userType)){
                            logger.info("即将更新的数据:"+"{id} =>{}" + user.getId()+"原{userType} =>{}"+user.getUserType()+"即将更新的userType =>{}"+userType);
                            SysUser update = new SysUser();
                            update.setUserType(userType);
                            update.setId(user.getId());
                            sysUserDao.updateUser(update);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<SysUserOperatorLog> getOperatorLog(Long userId) {
        return sysUserDao.getOperatorLog(userId);
    }

    private Map<String, Object> countPackageUserTime(ServicesPrice servicesPrice, Map<String, Object> resultMap) {
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(servicesPrice.getDuration())) {
            sb.append(servicesPrice.getDuration());
        }
        if (Objects.isNull(servicesPrice.getPriceUnit())) {
            sb.append("未知时长");
        } else {
            switch (Integer.valueOf(servicesPrice.getPriceUnit())) {
                case 0:
                    //年
                    sb.append("年");
                    break;
                case 1:
                    //月
                    sb.append("月");
                    break;
                case 2:
                    //日
                    sb.append("日");
                    break;
                default:
                    sb.append("未知时长");
                    break;
            }
        }
        resultMap.put("vaildTime",sb.toString());
        return resultMap;
    }

    public static void main(String[] args) {
        List<String> lists = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        if (!lists.stream().anyMatch(it ->Objects.equals("7",it))){
            System.out.println("哈哈");
        }
    }
}
