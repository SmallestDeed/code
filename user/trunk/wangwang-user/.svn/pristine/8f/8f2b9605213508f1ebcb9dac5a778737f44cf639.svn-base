package com.sandu.api.user.service.manage;

import com.sandu.api.operatorLog.model.SysUserOperatorLog;
import com.sandu.api.user.input.*;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.output.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/9 16:29
 */
@Component
public interface SysUserManageService {

    /**
    * @description： 批量生成用户
    * @author : WangHaiLin
    * @date : 2018/6/1 16:17
    * @param: [addUsers 批量生成入参]
    * @return: com.sandu.api.user.output.UserBatchVO
    *
    */
   UserBatchVO sysUserBatchInsert(UserBatchAdd addUsers);


    /**
     * @author : WangHaiLin
     * 删除内部账户
     * @param userId 账户Id
     * @return 操作结果
     */
   int deleteInternalUser(Long userId);

    /**
     * @author : WangHaiLin
     * 修改内部账户
     * @param userUpdate 修改内部账户入参
     * @return 修改结果
     */
   int updateInternalUser(InternalUserUpdate userUpdate, LoginUser loginUser);

    /**
     * @author : WangHaiLin
     * 查询账户详情
     * @param userId 账号
     * @return  查询账号详情信息
     */
    InternalUserDetailVO getInternalUserDetail(Long userId);

    /**
     * @author : WangHaiLin
     * 查询内部账号列表
     * @param userQuery 列表查询入参
     * @return list 内部账号集合
     */
   List<InternalUserListVO> getInternalUserList(InternalUserQuery userQuery);

    /**
     * @author : WangHaiLin
     * 查询内部账号数量
     * @param userQuery 数量查询入参
     * @return int 数量
     */
   int getInternalUserCount(InternalUserQuery userQuery);



    /**
     * @author : WangHaiLin
     * 查询经销商用户详情
     * @param userId 账号
     * @return DealersUserDetailVO账号详情输出
     */
    int deleteDealersUser(Long userId);
    /**
     * @author : WangHaiLin
     * 修改经销商用户
     * @param userUpdate 修改入参
     * @return int 修改操作结果
     */
   int updateDealersUser(DealersUserUpdate userUpdate, LoginUser loginUser);

    /**
     * @author : WangHaiLin
     * 查询经销商账号详情
     * @param userId 账号
     * @return DealersUserDetailVO 经销商账号详情输出
     */
   DealersUserDetailVO getDealersUserDetail(Long userId);

    /**
     * @author : WangHaiLin
     * 查询经销商账号数量
     * @param userQuery 查询条件
     * @return int 数量
     */
   int getDealersUserCount(DealersUserQuery userQuery);

    /**
     * @author : WangHaiLin
     * 查询经销商账号列表
     * @param userQuery 列表查询条件
     * @return list 经销商账号列表
     */
   List<DealersUserListVO> getDealersUserList(DealersUserQuery userQuery);


    /**
     * @author : WangHaiLin
     * 查询当前企业所有用户类型
     * @param companyId 企业Id
     * @return list 用户类型集合
     */
    List<Integer> getAllUserType(Long companyId);

    /**
     * 通过用户账号查询用户企业Id
     * @param account 用户账号
     * @return Long 企业Id
     */
    Long getUserCompanyIdByAccount(String account);

    /**
     * 通过用户账号查询用户企业Id
     * @param userId 用户账号ID
     * @return Long 企业Id
     */
    UserCompanyInfoVO getCompanyInfoByUserId(Long userId);

    /**
     * 处理用户头像
     * @param headPic 头像Id
     * @return int 处理结果
     */
    int dealUserHeadPic(DealUserHeadPic headPic);


    /**
	 * 通过账号和密码获取B端用户,如果多个经销商只返回一个
	 * @param account
	 * @param password
	 * @return
	 */
	SysUser get2BUser(String account,String password,Long companyId);


    /**
     * 校验内部用户手机号是否唯一
     * @author chenqiang
     * @param mobile 手机号
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    boolean checkPhone(String mobile);

    /**
     * 校验独立经销商手机号是否唯一
     * @author chenqiang
     * @param mobile 手机号
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    boolean checkPhoneDuLI(String mobile);

    /**
     * 查询B端 经销商手机号 在当前企业下是否唯一
     * @author chenqiang
     * @param mobile 手机号
     * @param companyId 企业id
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    boolean checkFranchiserPhoneOne(String mobile,Integer companyId);

    /**
     * 查询B端 经销商手机号 在非经销商用户是否存在
     * @author chenqiang
     * @param mobile 手机号
     * @return 手机号是否唯一：true：唯一
     * @date 2018/6/21
     */
    boolean checkFranchiserPhoneTwo(String mobile);

    /**
     * 密码md5加密规则
     * @author chenqiang
     * @param password 明文密码
     * @return 加密后的字符串
     */
    String getMd5Password(String password);

    /**
     * 根据手机号，修改相同手机号的 经销商用户 的 密码
     * @author chenqiang
     * @param mobile 手机号
     * @return 是否修改成功：true:成功
     * @date 2018/6/21
     */
    boolean updateSysUserByMobile(String mobile,String password);

    /**
     * 绑定经销商企业
     * @author chenqiang
     * @param userId id
     * @param franchiserId 企业id
     * @return 是否修改成功：true:成功
     * @date 2018/6/21
     */
    boolean bindCompanyFranchiser(Long userId,Long franchiserId);

    /**
     * 删除经销商时，解绑 账号
     * @author chenqiang
     * @param franchiserId 经销商id
     * @param loginName    登录名
     * @return 是否解绑 ：true:成功
     * @date 2018/6/21
     */
    boolean notBindUserToFranchiser(Long franchiserId,String loginName);


    /**
     * 处理套餐升级带来的失效时间和有效时长变更
     * @author wanghl
     * @param userId 用户Id
     * @param failureTime 失效时间
     * @return boolean (true:操作成功；false:操作失败)
     */
    boolean dealUserFailureTimeAndValidTime(Long userId,Date oldAccountFailureTime,Date beginTime,Date failureTime);

    Map<String, Object> checkUserIsFirstLogin(UserLogin userLogin);

    Map<String, Object> getPackageAccountTime(Long id);

    void restoreSysUserData();

 List<SysUserOperatorLog> getOperatorLog(Long id);
}
