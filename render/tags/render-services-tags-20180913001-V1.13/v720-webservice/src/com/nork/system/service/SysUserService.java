package com.nork.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysUserSearch;

/**   
 * @Title: SysUserService.java 
 * @Package com.nork.system.service
 * @Description:系统-用户Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0   
 */
public interface SysUserService {
	/**
	 * 新增数据
	 *
	 * @param sysUser
	 * @return  int 
	 */
	public int add(SysUser sysUser);

	public int add(SysUser sysUser,HttpServletRequest request);

	
	/**
	 *    更新数据
	 *
	 * @param sysUser
	 * @return  int 
	 */
	public int update(SysUser sysUser);

	public boolean updateFinance(SysUser user);
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	public SysUser findWithAccount(String account);
	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUser 
	 */
	public SysUser get(Integer id);

	/***
	 * 获取系统用户
	 * @return
	 */
	List<SysUser> getSysList();
	/**
	 * 所有数据
	 * 
	 * @param  sysUser
	 * @return   List<SysUser>
	 */
	public List<SysUser> getList(SysUser sysUser);

	/**
	 *    获取数据数量
	 *
	 * @param  sysUser
	 * @return   int
	 */
	public int getCount(SysUserSearch sysUserSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysUser
	 * @return   List<SysUser>
	 */
	public List<SysUser> getPaginatedList(SysUserSearch sysUsertSearch);

	/**
	 * 验证用户名和密码
	 * @param account
	 * @param password
	 * @return
	 */
	public ResponseEnvelope<SysUser> checkUserAccount(SysUser user);

	/**
	 * 用户登录验证
	 * @param account
	 * @param password
	 * @return
	 */
	public SysUser checkUserAccount(String account, String password);
	
	/**
	 * 用户登录只验证账号
	 * @param account
	 * @param password
	 * @return
	 */
	public SysUser checkOnlyUserAccount(String account, String password);


	/**
	 * 修改用户密码
	 * @param user
	 * @param oldPassword
	 * @param checkOldPassword
	 * @return
	 */
	public boolean updateUserPwd(SysUser user, String oldPassword,
			boolean checkOldPassword);

	/**
	 * 分页查询用户详细信息
	 * @param sysUserSearch
	 * @return
	 */
	List<SysUser> userDetailPaginatedList(SysUserSearch sysUserSearch);
	
	public void processregister(String email);
	
	
	public void processActivate(String email , String validateCode)throws ServiceException;

	/**通过loginName查找用户*/
	public List<SysUser> findOneByLoginName(String loginName);

	/*得到有gmt_create字段的所有表名*/
	public List<String> getTableNames();
	
	/**
	 * 密码md5加密规则
	 * @author huangsongbo
	 * @param password 明文密码
	 * @return 加密后的字符串
	 */
	public String getMd5Password(String password);
	/**
	 * 判断需不需要给用户解绑设备号->满足条件则解绑
	 * @author huangsongbo
	 * @param userId
	 */
	public void unwrapImei(Integer userId);

	/**
	 * @author huangsongbo
	 * 检测用户是否已登录
	 * @param id
	 * @return true:已登录,不允许再登录;false:允许登录
	 */
	//public boolean checkisLoginIn(Integer id);

	/**
	 * 更新该用户的在缓存中的信息(登录有效时长信息:validTime)
	 * @author huangsongbo
	 * @param userId
	 */
	//public void updateValidTime(Integer userId);

	/**
	 * 检测用户是否已登录(检测缓存信息是否存在)
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public boolean checkisLoginIn2(Integer id, String terminalImei);

	/**
	 * LoginUser转化为SysUser
	 * @author huangsongbo
	 * @param loginUser
	 * @return
	 */
	public SysUser getSysUserByLoginUser(LoginUser loginUser);

	/**
	 * 通过角色CODE查询用户
	 * @param roleCode
	 * @return
	 */
	public List<SysUser> getUserByRoleCode(String roleCode);

	/**
	 * 通过登录四参数验证该用户是否异常
	 * @author huangsongbo
	 * @param appKey
	 * @param token
	 * @param deviceId
	 * @param mediaType
	 * @return
	 */
	public LoginUser isAllowedThrough(String appKey, String token, String deviceId, String mediaType);

	/**
	 * 获取session登录信息
	 * @author xiaoxc
	 * @param request
	 * @return LoginUser
	 */
	public LoginUser getSessionLoginUser(HttpServletRequest request);

	/**
	 * 用token来加密app端需要的key
	 * @param user
	 * @return
	 */
    SysUser EencryptKey(SysUser user);
    public void updateBalanceAmountByUserId(SysUser user);
   
    /**
     * 获取用户头像地址
     * @param sysUser
     * @return
     */
    public SysUser getUserPic(SysUser sysUser);
    
    /**
	 * 用户登录验证
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean checkWhiteList(String terminalImei);
    
}
