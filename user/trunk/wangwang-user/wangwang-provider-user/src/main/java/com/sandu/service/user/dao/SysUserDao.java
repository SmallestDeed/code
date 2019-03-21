package com.sandu.service.user.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sandu.api.operatorLog.model.SysUserOperatorLog;
import com.sandu.api.user.input.*;
import com.sandu.api.user.model.SysUserMessage;
import com.sandu.api.user.model.UserManageDTO;
import com.sandu.api.user.output.UserCompanyInfoVO;
import com.sandu.api.user.output.UserWillExpireVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.model.bo.UserCompanyInfoBO;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.DealersUserListVO;
import com.sandu.api.user.output.InternalUserListVO;

@Repository
public interface SysUserDao {

    List<UserInfoBO> selectList(UserQuery query);
    
    List<UserCompanyInfoBO> select2bUserCompanyInfo(@Param("mobile") String mobile);
    
    int update2bUserPasswordByMobile(@Param("mobile") String mobile,@Param("password") String password);
    
    int update2bUserPasswordAndMobileByUserId(@Param("userId")Long userId, @Param("mobile") String mobile, @Param("password") String password);
    
    int addMiniProgramUser(@Param("openId") String openId , @Param("miniProgramCompanyId") Long miniProgramCompanyId,
						   @Param("usableHouseNumber")Integer usableHouseNumber,@Param("appId")String appId,@Param("uuid")String uuid);

	void updatePasswordUpdateFlagByUserId(@Param("userId")  Long userId);
	
	void updatePasswordUpdateFlagByMobile(@Param("mobile")  String mobile);
	
	void updateLastLoginCompanyIdByMobile(@Param("mobile") String mobile,@Param("lastLoginCompanyId") Long lastLoginCompanyId);

	UserInfoBO selectCompanyFranchiserInfo(@Param("mobile")String mobile,@Param("password")String password,@Param("companyId")Long companyId);
	
	List<UserInfoBO> selectCompanyFranchiserUsers(@Param("mobile")String mobile,@Param("password")String password);
	
	
    SysUser selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKeySelective(SysUser record);
	
	int updateByPhone(SysUser mobile);
   
	int selectMobileByMobile(String mobile);
	
	int updateMobileByUserId(SysUser sysUser);
	
	int selectMobileByUserInfo(String mobile);

	void updateIsLoginBeforeByUserId(@Param("userId") Long userId);
	
	List<CompanyInfoBO> selectCompanySumByMobile(String mobile);
	
	List<CompanyInfoBO> selectCompanyUserSumByMobile(String mobile);
	
	List<CompanyInfoBO> selectCompanyMobileByMobile(String mobile);
	
	int selectCompanyMobileCountByMobile(String mobile);
	
	
	
	SysUser selectMobileById(Long id);
	
	SysUser selectMobileInfoByMobile(String mobile);
	
	//2.根据用户id获取企业id
		List<CompanyInfoBO>selectCompanyIdAndMobileById(Long id);
		
	//1.根据用户id去查询用户类型
	SysUser selectUserTypeById(Long id);
	
	int selectCountById(CompanyInfoBO bo);
	
	CompanyInfoBO selectCompanyIdByMobielAndId(CompanyInfoBO bo);
	List<CompanyInfoBO> selectCountByIdAndMobile(CompanyInfoBO bo);
	int selectCountsByIdAndMobile(CompanyInfoBO bo);
	int selectCountsByUserIdAndMobile(CompanyInfoBO bo);
	List<SysUser> selectUserTypeByMobile(String mobile);
	int selectCountMobileByMobile(String mobile);
	int selectMobileAndUserTypeByUserInfo(String mobile);
	Long selectCompanyIdByMobile(String mobile);
	int updatePwdByMobile(SysUser sysUser);
	
	int updateMobileById(@Param("mobile") String mobile,@Param("id")Long id);
	int updateMobileByMobile(@Param("mobile") String mobile,@Param("newMobile")String newMobile);
	int updatePwdByMobiles(@Param("mobile") String mobile,@Param("password")String pwd,@Param("lastLoginCompanyId")Long companyId);
	int updatePwdById(@Param("id") Long id,@Param("password")String pwd,@Param("lastLoginCompanyId")Long companyId);
	int updateFranchiserByNewMobile(@Param("oldMobile")String mobile,@Param("password")String pwd,@Param("lastLoginCompanyId")Long companyId,@Param("passwordUpdateFlag")Integer passwordUpdateFlag,@Param("newMobile")String newMobile);
	int updateByUserId(@Param("id") Long id,@Param("mobile")String mobile,@Param("password")String password,@Param("lastLoginCompanyId")Long companyId,@Param("passwordUpdateFlag")Integer passwordUpdateFlag);
	int updateFranchiserMobile(@Param("newMobile")String newMobile,@Param("oldMobile")String mobile);
	int selectMultipleFranchiserAccount(Long id);
	String selectDealerMobileById(Long id);
	int selectEnterpriseCountByMobile(String mobile);
	String selectPwdById(Long id);
	String selectPwdByMobile(String mobile);
    int updateMinProUserInfo(@Param("openId") String openId,@Param("nickName") String nickName,@Param("headPic")String headPic);


    void updateFailureTimeByPrimaryKey(@Param("firstLoginTime")Date date,@Param("id") Long id,@Param("failureTime") Date failureTime);
    void updateFirstLoginTimeByPrimaryKey(@Param("firstLoginTime")Date date,@Param("id") Long id);

    int selectCountDealerByNewMobile(@Param("newMobile")String mobile,@Param("id") Long id);
    SysUser getFranchiserByMobile(@Param("newMobile")String mobile);
    int selectExistMobileByBindingMobile(String mobile);
    String selectCompanyIdByNewMobile(@Param("mobile") String mobile,@Param("id") Long id);
    int selectCompanyCountByCompanyId(@Param("newMobile")String mobile,@Param("companyIds") String companyIds);
    String selectMobileByUserId(@Param("id") Long id);

	int save(SysUser sysUser);

    int count2BMobileIsExist(@Param("mobile") String mobile);

    int updateUserMobile2C(@Param("userId") Integer userId, @Param("mobile") String mobile);

    int updatePassword(SysUser sysUser);


    SysUser getUserById(@Param("userId") Long userId);

	/**
	 * @description： 通过昵称查询用户
	 * @author : WangHaiLin
	 * @date : 2018/6/1 15:06
	 * @param: [nickName]
	 * @return: com.sandu.api.user.model.SysUser
	 *
	 */
	List<SysUser> getUserByNickName(@Param("nickNames") List<String> nickNames);

	SysUser getUserByNickName(@Param("nickName") String nickName);

    /**
    * @description： 新增用户(商家后台购买套餐)
    * @author : WangHaiLin
    * @date : 2018/6/1 18:01
    * @param: [sysUser]
    * @return: int  新增操作结果
    *
    */
    int insertUser(SysUser sysUser);

	/**
	 * @author : WangHaiLin
	 * 删除账户
	 * @param userId id
	 * @return 操作结果
	 */
	int deleteUser(@Param("userId") Long userId);

	/**
	 * @author : WangHaiLin
	 * 修改账户
	 * @param userUpdate 修改账户入参
	 * @return 修改结果
	 */
	int updateUser(SysUser userUpdate);


	/**
	 * @author : WangHaiLin
	 * 查询内部账号列表
	 * @param userQuery 列表查询入参
	 * @return list 内部账号集合
	 */
	List<InternalUserListVO> selectInternalUserList(InternalUserQueryExtends userQuery);

	/**
	 * @author : WangHaiLin
	 * 查询内部账号数量
	 * @param userQuery 查询条件
	 * @return 返回结果
	 */
	int selectInternalUserCount(InternalUserQueryExtends userQuery);

	/**
	 * @author : WangHaiLin
	 * 查询经销商账号列表
	 * @param userQuery 查询条件
	 * @return list 经销商账号列表
	 */
	List<DealersUserListVO> selectDealersUserList(DealersUserQueryPO userQuery);

	/**
	 * @author : WangHaiLin
	 * 查询经销商账号数量
	 * @param userQuery 查询条件
	 * @return int 数据数量
	 */
	int  getDealersUserCount(DealersUserQueryPO userQuery);

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
	Long getUserCompanyIdByAccount(@Param("account") String account);

	/**
	 * 通过用户账号查询用户企业Id
	 * @param userId 用户账号ID
	 * @return UserCompanyInfoVO
	 */
	UserCompanyInfoVO getCompanyInfoByUserId(Long userId);

	/**
	 * 处理用户头像
	 * @param headPic 头像Id
	 * @return int 处理结果
	 */
	int dealUserHeadPic(DealUserHeadPic headPic);



	 /**
     * 查询用户列表
     * @param user
     * @return
     */
	List<SysUser> selectUserList(SysUser user);

	/**
	 * 根据用户id修改用户信息
	 * @param id
	 * @param mobile
	 * @param password
	 * @param companyId
	 * @param passwordUpdateFlag
	 * @return
	 */
	int updateUserInfoById(@Param("id") Long id,@Param("mobile")String mobile,@Param("password")String password,@Param("lastLoginCompanyId")Long companyId,@Param("passwordUpdateFlag")Integer passwordUpdateFlag);

	/**
	 * 根据通过电话修改B端用户
	 * @param id
	 * @param mobile
	 * @param password
	 * @param companyId
	 * @param passwordUpdateFlag
	 * @return
	 */
	int update2BUserInfoByMobile(@Param("mobile")String mobile,@Param("password")String password,@Param("lastLoginCompanyId")Long companyId,@Param("oldMobile")String oldMobile);
	
	/**
	 * 根据用户id修改用户电话和密码
	 * @param mobile
	 * @param password
	 * @param id
	 * @return
	 */
	int update2BUserInfoById(@Param("mobile") String mobile,@Param("password") String password,@Param("lastLoginCompanyId")Long companyId,@Param("id")Long id);
	
	
	/**
	 * 根据用户id修改用户电话
	 * @param mobile
	 * @param id
	 * @return
	 */
	int updateUserMobileById(@Param("mobile") String mobile,@Param("id")Long id);
	
	/**
	 * 根据手机号,平台类型,用户类型 更新原有手机号信息
	 * @param newMobile
	 * @param oldMobile
	 * @param platformType
	 * @param userType
	 * @return
	 */
	int updateFranchiserMobileByOldMobile(@Param("newMobile")String newMobile,@Param("oldMobile")String oldMobile);

	/**
	 * 根据电话查询经销商id列表
	 * @param mobile
	 * @return
	 */
	Set<Long> selectFranchiserCompanyIdsByMobile(@Param("mobile") String mobile);

	/**
	 * 根据用户id修改经销商id
	 * @param businessAdministrationId
	 * @param userId
	 * @return
	 */
	int updateBusinessAdministrationIdByUserId( @Param("businessAdministrationId")Long businessAdministrationId, @Param("userId")Long userId);


	/**
	 * 查询B端手机号是否唯一
	 * @author chenqiang
	 * @param mobile 手机号
	 * @return count 数量
	 * @date 2018/6/21
	 */
	int checkPhone(@Param("mobile")String mobile);

	/**
	 * 根据条件 查询B端手机号是否唯一
	 * @author chenqiang
	 * @param mobile 手机号
	 * @return count 数量
	 * @date 2018/6/21
	 */
	int selectUserByUserTypeMobile(@Param("userTypeList") List<Integer> userTypeList,@Param("userTypenNotList") List<Integer> userTypenNotList,@Param("mobile") String mobile);

	/**
	 * 查询B端 经销商 手机号是否唯一
	 * @author chenqiang
	 * @param mobile 手机号
	 * @return count 数量
	 * @date 2018/6/21
	 */
	int checkFranchiserPhoneOne(@Param("mobile")String mobile,@Param("companyId") Integer companyId);

	/**
	 * 查询B端 经销商 手机号是否唯一
	 * @author chenqiang
	 * @param mobile 手机号
	 * @return count 数量
	 * @date 2018/6/21
	 */
	int checkFranchiserPhoneTwo(@Param("mobile")String mobile);

	/**
	 * 根据手机号，修改相同手机号的 经销商用户 的 密码
	 * @author chenqiang
	 * @param mobile 手机号
	 * @param password 密码
	 * @return count 数量
	 * @date 2018/6/21
	 */
	int updateSysUserByMobile(@Param("mobile")String mobile,@Param("password")String password);

	/**
	 * 删除经销商时，解绑 账号
	 * @author chenqiang
	 * @param franchiserId 经销商id
	 * @param loginName    登录名
	 * @return count 数量
	 * @date 2018/6/21
	 */
	int notBindUserToFranchiser(@Param("franchiserId") Long franchiserId,@Param("loginName") String loginName);

    void updateBusinessAdministrationId(@Param("userId")Long userId,@Param("companyId") Long companyId);

    List<SysUser> selectUser(@Param("mobile") String mobile);

	/**add by wanghl start**/
	/**
	 * 查询企业内部用户登录名符合规则的最大编码
	 * @return
	 */
	String getMaxCompanyInUserNameCode(@Param("companyCodePrefixS") String companyCodePrefixS,@Param("companyId")Long companyId);

	/**
	 * 查询经销商用户登录名符合规则的最大编码
	 * @return
	 */
	String getMaxFranchiserAccountCode(@Param("companyCodePrefixS") String companyCodePrefixS,@Param("companyId")Long companyId);
	/**add by wanghl end**/

    UserInfoBO getUserInfoByNickName(String account);

	SysUser selectUserById(@Param("id")Long id);


	/**
	 * wangHL
	 * 套餐升级修改用户失效时间
	 * @param failureTime 新失效时间
	 * @param nickName 用户昵称（修改人）
	 * @param userId  用户Id
	 * @return int 修改操作结果
	 */
	int updateUserFailureTime(@Param("failureTime") Date failureTime,@Param("nickName") String nickName,
							  @Param("userId") Long userId);

	void updateServiceFlag(@Param("id")Long id, @Param("serviceFlag")int serviceFlag);

	void updateCustomerBaseInfo(@Param("userId")Integer userId, @Param("mobile")String mobile);

    void delByPrimaryKey(Long id);

	/**
	 * WangHaiLin
	 * 查询还剩5天就要过期的账号的--电话号码
	 * @param nowTime 当前时间
	 * @param timeAfterDays 当前时间XX天后的时间
	 * @param platformType 平台类型
	 * @return list
	 */
	List<UserWillExpireVO> getUserWillExpire(@Param("nowTime") Date nowTime, @Param("timeAfterDays") Date timeAfterDays,
											 @Param("platformType")Integer platformType);

    int insertSysUserMessage(SysUserMessage message);

    //int getSysUserMessage(Long userId);
	/***
	 * 通过邀请码查询用户
	 * @param invitationCode 邀请码
	 * @return sysUser
	 */
	SysUser getUserByInvitationCode(@Param("invitationCode") String invitationCode);

    int checkUserSecondRender(Long userId);
    Map<String,Object> getUserNickNameANDMobile(@Param("nickName") String nickName, @Param("mobile")String mobile);

    List<SysUser> getUsersByCompanyId(Long companyId);
	List<UserInfoBO> selectUserByNickNameAndCompanyId(@Param("nickName")String nickName, @Param("companyId")Long companyId);

	List<SysUser> select2bUserByMobile(SysUser userQuery);

    Map<String, Object> getUserPackInfo(Long userId);

	String getPackType(Integer servicesId);

	/**
	 * 同步套餐用户初次登录的套餐时间和账号时间
	 * @param firstLoginTime 初次登录时间
	 * @param userId 用户Id
	 * @param failureTime 账号失效时间
	 * @return 操作结果
	 */
	int updateServiceUserFirstLogin(@Param("firstLoginTime") Date firstLoginTime,
									@Param("userId") Long userId,
									@Param("failureTime") Date failureTime);

    List<UserManageDTO> selectUserListResult(UserManageSearch search);

	void batchDelByPrimaryKey(@Param("userIds")Set<Long> userIds,@Param("modifier") String modifier);

    List<SysUser> getRestoreData();

	List<SysUserOperatorLog> getOperatorLog(Long userId);
}
