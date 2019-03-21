package com.sandu.api.user.service;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.github.pagehelper.PageInfo;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.user.input.*;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.ResponseBo;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.UserManageDTO;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.DealersUserListVO;
import com.sandu.api.user.output.InternalUserListVO;
import com.sandu.api.user.output.UserCompanyInfoVO;
import com.sandu.api.user.output.UserWillExpireVO;
import com.sandu.commons.MessageUtil;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserService {
	//SysUser get(Integer id);
	int update(SysUser record);

	/**
	 * 校验手机号(已注册true/未注册false)
	 *
	 * @param phone
	 * @return
	 */
	boolean checkPhone(String phone);

	ResponseBo checkPhone(String phone, String code, Long userId);

	ResponseBo checkPhone(String phone, String code);

	ResponseBo checkImgCode(String sysCode, String userId, String imgCode);

	void saveCode(String sysCode, String userId, String verifyCode);

	void delCode(String sysCode, String userId);

	String getCode(String sysCode, String userId);

	Boolean sendMessage(String mobile);

	SysUser get(Integer id);

	public List<CompanyInfoBO> getFranchiserCompanyList(String account, String password, String platformCode, int isForLogin);

	public String getOpenid(String appid, String code);

	int selectMobileByMobile(String mobile);

	int updateByPhone(SysUser mobile);

	int updateMobileByUserId(SysUser sysUser);

	int selectMobileByUserInfo(String mobile);

	List<CompanyInfoBO> selectCompanySumByMobile(String mobile);

	List<CompanyInfoBO> selectCompanyUserSumByMobile(String mobile);

	List<CompanyInfoBO> selectCompanyMobileByMobile(String mobile);

	int selectCompanyMobileCountByMobile(String mobile);

	SysUser selectMobileById(Long id);

	SysUser selectMobileInfoByMobile(String mobile);

	List<CompanyInfoBO> selectCompanyIdAndMobileById(Long id);

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

	int updateMobileById(String mobile, Long id);

	/*ResponseBo updateMobileById(UserUpdateMobile userUpdateMobile,Long id);*/
	int modifyUserMobile(UserUpdateMobile userUpdateMobile, Long id, Integer userType);

	boolean selectMultipleFranchiserAccount(Long id);

	String selectDealerMobileById(Long id);

	int selectEnterpriseCountByMobile(String mobile);

	String selectPwdById(Long id);

	String selectPwdByMobile(String mobile);

	/**
	 * 微信小程序插入nickName
	 *
	 * @param openId
	 * @param nickName
	 * @param resPicId
	 * @return
	 */
	int updateMinProUserInfo(String openId, String nickName, String headPicId);

	/**
	 * 保存用户失效时间
	 *
	 * @param date
	 * @param id
	 * @param timeInMillis
	 */
	void updateFailureTimeByPrimaryKey(Date date, Long id, Date timeInMillis);

	void updateFirstLoginTimeByPrimaryKey(Date date, Long id);

	int save(UserRegister userRegister);

	int editPassword(String mobile, String newPassword, String authCode);

	int check2BMobileIsExist(String mobile);

	int modifyUserMobile2C(Integer data, String mobile, String authCode);


	/**
	 * @description：通过昵称查用户
	 * @author : WangHaiLin
	 * @date : 2018/6/1 15:11
	 * @param: nickNames 昵称集合
	 * @return: java.util.List<com.sandu.api.user.model.SysUser> 用户集合
	 */
	List<SysUser> getUserByNickName(List<String> nickNames);

	/**
	 * 新增用户(商家后台购买套餐)
	 *
	 * @author : WangHaiLin
	 * @date : 2018/6/1 17:58
	 * @param: [sysUser ]
	 * @return: Long 新增用户Id
	 */
	Long insertUser(SysUser sysUser);

	/**
	 * @param userId 用户Id
	 * @return 操作结果
	 * @author : WangHaiLin
	 * 删除账户
	 */
	int deleteUser(Long userId);

	/**
	 * @param userUpdate 修改账户入参
	 * @return 修改结果
	 * @author : WangHaiLin
	 * 修改账户
	 */
	int updateUser(SysUser userUpdate);

	/**
	 * @param userId 用户Id
	 * @return SysUser 查询账号信息
	 * @author : WangHaiLin
	 * 查询账户详情
	 */
	SysUser selectUserByAccount(Long userId);

	/**
	 * @param userQuery 列表查询入参
	 * @return list 内部账号集合
	 * @author : WangHaiLin
	 * 查询内部账号列表
	 */
	List<InternalUserListVO> selectInternalUserList(InternalUserQueryExtends userQuery);

	/**
	 * @param userQueryExtends 查询条件
	 * @return Integer 数据数量
	 * @author : WangHaiLin
	 * 查询满足条件内部账号的数量
	 */
	int getInternalUserCount(InternalUserQueryExtends userQueryExtends);

	/**
	 * @param userQuery 查询条件
	 * @return list 经销商账号列表
	 * @author : WangHaiLin
	 * 查询经销商账号列表
	 */
	List<DealersUserListVO> selectDealersUserList(DealersUserQueryPO userQuery);

	/**
	 * @param userQuery 查询条件
	 * @return int 数据数量
	 * @author : WangHaiLin
	 * 查询经销商账号数量
	 */
	int getDealersUserCount(DealersUserQueryPO userQuery);


	/**
	 * @param companyId 企业Id
	 * @return list 用户类型集合
	 * @author : WangHaiLin
	 * 查询当前企业所有用户类型
	 */
	List<Integer> getAllUserType(Long companyId);


	/**
	 * 通过用户账号查询用户企业Id
	 *
	 * @param account 用户账号
	 * @return Long 企业Id
	 */
	Long getUserCompanyIdByAccount(String account);

	/**
	 * 通过用户账号查询用户企业Id
	 *
	 * @param userId 用户账号ID
	 * @return UserCompanyInfoVO
	 */
	UserCompanyInfoVO getCompanyInfoByUserId(Long userId);


	/**
	 * 处理用户头像
	 *
	 * @param headPic 头像Id
	 * @return int 处理结果
	 */
	int dealUserHeadPic(DealUserHeadPic headPic);

	/**
	 * 修改B端用户手机号码
	 *
	 * @param userUpdateMobile
	 * @param id
	 * @return
	 */
	int modify2BUserMobile(UserUpdateMobile userUpdateMobile, LoginUser loginUser);

	/**
	 * 修改B端用户绑定手机号及密码
	 *
	 * @param userParam
	 */
	int modify2BUserMobileAndPassword(UserUpdatePasswordAndMobile userParam, Integer userId);

	/**
	 * 是否是多经销商账号
	 *
	 * @param id
	 * @return
	 */
	boolean isMultipleFranchiserAccount(Long id);

	/**
	 * 经销商用户绑定企业下面的某个经销商
	 *
	 * @param userId
	 * @param businessAdministrationId
	 * @return
	 */
	boolean addUserFranchiser(Long userId, Long businessAdministrationId);

	int editUserInfo(Long id, UserInfoEdit userInfoEdit);

	int modifiPhone(Long userId, String mobile, String authCode);

	boolean addGiftAmount(List<Long> userIds, Integer amount, String giveDesc);


	public Boolean sendMessage(String mobile, String message);

	String setPicPath(SysUser sysUser);

	List<SysUser> getUserBuMobile(String mobile);

	boolean checkUserIsExistVirtualCompany(Integer userId);

	public List<CompanyInfoBO> getFranchiserCompanyList(String account, String password, String platformCode);

	/**
	 * 查询企业内部用户登录名符合规则的最大编码
	 *
	 * @return
	 */
	String getMaxCompanyInUserNameCode(String companyCodePrefixS, Long companyId);

	/**
	 * 查询经销商用户登录名符合规则的最大编码
	 *
	 * @return
	 */
	String getMaxFranchiserAccountCode(String companyCodePrefixS, Long companyId);


	/**
	 * 套餐升级角色转换
	 *
	 * @param userId
	 * @param oldRoleIds
	 * @param currentRoleIds
	 */
	void updateUserRoles(Integer userId, List<Integer> oldRoleIds, List<Integer> currentRoleIds);

	UserInfoBO getUserInfoByNickName(String account);

	SysUser getUserById(Long id);

    int updatePasswordByMerchantManage(Long id, String newPassword, String oldPassword);

	/**
	 * wangHL
	 * 套餐升级修改用户失效时间
	 * @param failureTime 新失效时间
	 * @param nickName 用户昵称（修改人）
	 * @param userId  用户Id
	 * @return int 修改操作结果
	 */
    int updateUserFailureTime(Date failureTime,String nickName,Long userId);


	/**
	 * 获取失效时间在当前时间和当前时间后days天的B端用户电话
	 * @param days 天数
	 * @param platformType 平台类型 （C端是1,B端是2）
	 * @return  list--用户电话集合
	 */
	List<UserWillExpireVO> getUserWillExpire(Integer days, Integer platformType);

    String getMarkedMessage(Long userId, String platformCode);

	/**
	 * 校验邀请码
	 * @param invitationCode 邀请码
	 * @return true：邀请码存在；false：邀请码不存在
	 */
	boolean checkInvitation(String invitationCode);

    int modifyPassword(Long id, String newPassword, String oldPassword);

    boolean checkUserSecondRender(Long userId);

    Map<String,Object> getUserNickNameANDMobile(String nickName, String mobile);

    List<SysUser> getUsersByCompanyId(Long companyId);

    int modifyUserInfo(MinProUserInfo minProUserInfo,Long userId) throws Exception;
    
    /**
     * 获取用户信息
     * @param openid
     */
	SysUser getMiniUser(String openid);

    /**
     * 保存小程序用户信息:主要保存openId
     * @param appid
     * @param openid
     */
	void saveMiniUserInfo(String appid, String openid);

	/**
	 * 小程序用户是否存在
	 * @param openId
	 * @return
	 */
	boolean isMiniUserExist(String openId);

    int applyFormalAccount(Long id,String platformCode);

    Map<String, Object> getUserPackInfo(Long id);

	String getPackType(Integer servicesId);

	/**
	 * 同步套餐用户初次登录时套餐和账号的有效期
	 * @param firstLoginTime 初次登录时间
	 * @param userId 用户Id
	 * @param failureTime 失效时间
	 * @return int
	 */
    int modifyServiceUserFirstLogin(Date firstLoginTime,Long userId,Date failureTime);


	PageInfo<UserManageDTO> selectUserList(UserManageSearch search);

	boolean handlerUserDel(String ids,Long loginUserId);

    Map getPackServicesName(Long id);

    List<BasePlatform> getLoginUserAdminPlatform(Long userId);

    List<SysUser> getRestoreUserData();


	/**
	 * 校验平台手机号(已注册true/未注册false)
	 * @param phone
	 * @param platformCode
	 * @param appId
	 * @return
	 */
	boolean checkPhone(String phone, String platformCode, String appId);

	/**
	 * 验证发送短信信息
	 * @author xiaoxc
	 * @param mobile	手机号
	 * @param clietIp	客户端标识
	 * @date 2019-01-17
	 */
	MessageUtil verifySmsInfo(String mobile, String clietIp);

	/**
	 * 根据功能类型发送不同的短信内容
	 * @author xiaoxc
	 * @param mobile		手机号
	 * @param clietIp		客户端标识
	 * @param functionType	功能类型
	 * @param map			客户端发送的数量和手机接收的数量
	 * @date 2019-01-17
	 */
	Boolean sendMessage(String mobile, String clietIp, Integer functionType, Map<String, Integer> map, String platformCode);

}
