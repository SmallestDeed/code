package com.nork.system.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import com.nork.common.properties.AesProperties;
import com.nork.common.util.AESUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nork.common.errorkeys.ErrorKeys;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.model.WhiteDeviceList;
import com.nork.common.properties.AesProperties;
import com.nork.common.util.AESUtil2;
import com.nork.common.util.SendEmail;
import com.nork.common.util.Utils;
import com.nork.common.util.Validator;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.mobile.dao.MobileRenderRecordMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysUserSearch;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * @Title: SysUserServiceImpl.java
 * @Package com.nork.system.service.impl
 * @Description:系统-用户ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0
 */
@Service("sysUserService")
@Transactional
public class SysUserServiceImpl implements SysUserService {

	private SysUserMapper sysUserMapper;

	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	
	@Autowired
	private ResPicService resPicServicer;
	
	@Autowired
	private MobileRenderRecordMapper mobileRenderRecordMapper;
	
	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}
	private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
	// 密匙
	private static String key=Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
	/**
	 * 新增数据
	 *
	 * @param sysUser
	
	 * @return int
	 */
	@Override
	public int add(SysUser sysUser) {
		sysUserMapper.insertSelective(sysUser);
		return sysUser.getId();
	}

	/**
	 * 新增数据
	 *
	 * @param sysUser
	
	 * @return int
	 */
	@Override
	
	public int add(SysUser sysUser, HttpServletRequest request) {
		setAppKey(sysUser, request);
		sysUserMapper.insertSelective(sysUser);
		return sysUser.getId();
	}
	

	/** 设置sysUser的mediaType属性 */
	private void setAppKey(SysUser sysUser, HttpServletRequest request) {
		Integer mediaType = sysUser.getMediaType();
		if (mediaType != null) {
			/*
			 * SysDictionarySearch sysDictionarySearch=new
			 * SysDictionarySearch(); sysDictionarySearch.setType("mediaType");
			 * sysDictionarySearch.setValue(mediaType); List<SysDictionary>
			 * sysDictionaries=sysDictionaryMapper.selectPaginatedList(
			 * sysDictionarySearch);
			 * if(sysDictionaries!=null&&sysDictionaries.size()>0){
			 * sysUser.setAppKey(sysDictionaries.get(0).getAtt1());
			 * sysUser.setMediaType(mediaType); }
			 */
			SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("mediaType", mediaType);
			if (sysDictionary != null) {
				sysUser.setAppKey(sysDictionary.getAtt1());
				sysUser.setMediaType(mediaType);
			}
		}
	}

	public List<SysUser> getSysList(){
		return sysUserMapper.getSysList();
	}
	/**
	
	 * 更新数据
	 *
	 * @param sysUser

	 * @return int
	 */
	@Override
	public int update(SysUser sysUser) {
		sysUserMapper.updateByPrimaryKeySelective(sysUser);
		// setAppKey(sysUser);
		return sysUser.getId();
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	/**

	 * 获取数据详情
	 *
	 * @param id

	 * @return SysUser
	 */
	@Override
	public SysUser get(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 

	 * @param sysUser
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> getList(SysUser sysUser) {
	    sysUser.setIsDeleted(0);
		return sysUserMapper.selectList(sysUser);
	}

	/**

	 * 获取数据数量
	 *

	 * @param sysUser
	 * @return int
	 */
	@Override


	public int getCount(SysUserSearch sysUserSearch) {
	    sysUserSearch.setIsDeleted(0);
		return sysUserMapper.selectCount(sysUserSearch);
	}

	/**

	 * 分页获取数据
	 *

	 * @param sysUser
	 * @return List<SysUser>
	 */
	@Override

	public List<SysUser> getPaginatedList(SysUserSearch sysUserSearch) {
	    sysUserSearch.setIsDeleted(0);
		return sysUserMapper.selectPaginatedList(sysUserSearch);
	}

	public SysUser findWithAccount(String account){
		SysUser user=null;
		SysUser search=new SysUser();
		search.setAccount(account);
		List<SysUser> lst=sysUserMapper.selectWithAccount(search);
		if (CustomerListUtils.isNotEmpty(lst)){
			user=lst.get(0);
		}
		return user;
	}
	
	/**
	 * 查询用户
	 * 
	 */

	public ResponseEnvelope<SysUser> checkUserAccount(SysUser user) {

		String account = user.getAccount();
		String password = user.getPassword();
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
			return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!", user.getMsgId());
		}
		user.setIsDeleted(0);

		List<SysUser> list = null;
		// 手机登录验证
		if (Validator.MOBILE.validate(account)) {
			user.setMobile(account);

			list = sysUserMapper.selectList(user);
		}

		// 邮箱登录验证
		else if (Validator.EAMIL.validate(account)) {
			user.setEmail(account);
			list = sysUserMapper.selectList(user);

		}
		// 用户名登录验证
		else {
			user.setNickName(account);
			list = sysUserMapper.selectList(user);
		}


		if (CustomerListUtils.isNotEmpty(list)) {
			user.setPassword(password);
			list = sysUserMapper.selectList(user);

			if (CustomerListUtils.isNotEmpty(list)) {
				return new ResponseEnvelope<SysUser>(user, user.getMsgId(), true);
			} else {
				// 密码不正确
				return new ResponseEnvelope<SysUser>(false, ErrorKeys.INVALID_PASSWORD, user.getMsgId());
			}

		} else {
			// 用户不存在
			return new ResponseEnvelope<SysUser>(false, ErrorKeys.INVALID_LOGIN_ACCOUNT, user.getMsgId());
		}
	}


	/**
	 * 用户登录验证
	 * 
	 */

	public SysUser checkUserAccount(String account, String password) {
		SysUser user = new SysUser();
		user.setIsDeleted(0);
		user.setPassword(password);

		List<SysUser> list = null;
		// 手机登录验证
		if (Validator.MOBILE.validate(account)) {
			user.setMobile(account);

			list = sysUserMapper.selectList(user);
		}

		// 邮箱登录验证
		else if (Validator.EAMIL.validate(account)) {
			user.setEmail(account);
			list = sysUserMapper.selectList(user);

		}
		// 用户名登录验证
		else {

			if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password))
				return null;

			user.setNickName(account);
			list = sysUserMapper.selectList(user);
			//modify by qiu.jun 增加对用户名登录的支持
			if (CustomerListUtils.isEmpty(list)) {
				user.setUserName(account);
				list = sysUserMapper.selectList(user);
			}
		}


		if (CustomerListUtils.isNotEmpty(list)) {
			user = list.get(0);
			return user;
		} else {
			return null;
		}
	}

	/**
	 * 用户登录验证
	 * 
	 */

	public SysUser checkOnlyUserAccount(String account, String password) {
		SysUser user = new SysUser();
		user.setIsDeleted(0);
		user.setPassword(password);

		List<SysUser> list = null;
		// 手机登录验证
		if (Validator.MOBILE.validate(account)) {
			user.setMobile(account);

			list = sysUserMapper.selectList(user);
		}

		// 邮箱登录验证
		else if (Validator.EAMIL.validate(account)) {
			user.setEmail(account);
			list = sysUserMapper.selectList(user);

		}
		// 用户名登录验证
		else {

			if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password))
				return null;

			user.setNickName(account);
			list = sysUserMapper.selectList(user);
			//modify by qiu.jun 增加对用户名登录的支持
			if (CustomerListUtils.isEmpty(list)) {
				user.setUserName(account);
				list = sysUserMapper.selectList(user);
			}
		}


		if (CustomerListUtils.isNotEmpty(list)) {
			user = list.get(0);
			return user;
		} else {
			return null;
		}
	}
	
	public boolean updateFinance(SysUser user){
		int result=sysUserMapper.updateFinanceById(user);
		return result>0 ? true : false;
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @param user
	 * @param oldPassword
	 * @param checkOldPassword
	 * @return
	 */
	
	public boolean updateUserPwd(SysUser user, String oldPassword, boolean checkOldPassword) {

		int count = sysUserMapper.updateByPrimaryKeySelective(user);


		return count > 0 ? true : false;
	}

	/**
	 * 分页查询用户详细信息
	 * 
	 * @param sysUserSearch
	 * @return
	 */
	@Override

	public List<SysUser> userDetailPaginatedList(SysUserSearch sysUserSearch) {
	    sysUserSearch.setIsDeleted(0); //过滤被逻辑删除用户
		return sysUserMapper.userDetailPaginatedList(sysUserSearch);
	}
	
	public void processregister(String email) {
		SysUser user = new SysUser();

       
		/// 邮件的内容
		StringBuffer sb = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
		sb.append("<a href=\"http://localhost:8080/springmvc/user/register?action=activate&email=");
		sb.append(email);
		sb.append("&validateCode=");
		sb.append(user.getAtt3());
		sb.append("\">http://localhost:8080/springmvc/user/register?action=activate&email=");
		sb.append(email);
		sb.append("&validateCode=");
		sb.append(user.getAtt3());
		sb.append("</a>");


		// 发送邮件
		SendEmail.send(email, sb.toString());
		//////System.out.println("发送邮件");

	}

	/**
	 * 密码md5加密规则
	 * @author huangsongbo
	 * @param password 明文密码
	 * @return 加密后的字符串
	 */
	public String getMd5Password(String password){
		return md5(md5("WeB"+password));
		//
	}
	
	/**
	 * md5加密(copy)
	 * @author huangsongbo
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
			////////System.out.println("result: " + buf.toString());// 32位的加密
			////////System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
			str=buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 判断需不需要给用户解绑设备号->满足条件则解绑
	 * @author huangsongbo
	 * @param userId
	 */
	public void unwrapImei(Integer userId) {
		AuthorizedConfigSearch authorizedConfigSearch2=new AuthorizedConfigSearch();
		authorizedConfigSearch2.setStart(0);
		authorizedConfigSearch2.setLimit(1);
		authorizedConfigSearch2.setUserId(userId);
		authorizedConfigSearch2.setState(1);
		List<AuthorizedConfig> authorizedConfigs2=authorizedConfigService.getPaginatedList(authorizedConfigSearch2);
		if(authorizedConfigs2==null||authorizedConfigs2.size()==0){
			/*解绑user和设备号的关联*/
			SysUser sysUser=get(userId);
			sysUser.setUserImei("");
			update(sysUser);
		}
	}
	
	/**
	 * 处理激活
	 * 
	 * @throws ParseException
	 */
	/// 传递激活码和email过来
	public void processActivate(String email, String validateCode) throws ServiceException {
		// 数据访问层，通过email获取用户信息
		SysUser user = null;

		// 验证用户是否存在
		// 激活成功， //并更新用户的激活状态，为已激活
		user.setEmail(email);
		sysUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<SysUser> findOneByLoginName(String loginName) {
		return sysUserMapper.findOneByLoginName(loginName);
	}

	@Override
	public List<String> getTableNames() {
		// TODO Auto-generated method stub
		return sysUserMapper.getTableNames();
	}

	/**
	 * @author huangsongbo
	 * 检测用户是否已登录
	 * @param id
	 * @return true:已登录,不允许再登录;false:允许登录
	 */
//	public boolean checkisLoginIn(Integer id) {
//		SysUser sysUser=SysUserCacher.checkTimeOutUser(id);
//		if(sysUser!=null){
//			/*缓存信息存在->检测信息中的有效时长*/
//			if(sysUser.getValidTime()==null){
//				return false;
//			}else{
//				long now=new Date().getTime();
//				if(now>sysUser.getValidTime())
//					/*用户已退出登录(没有通过心跳接口更新登录有效时长)*/
//					return false;
//				/*该用户还在登录状态中*/
//				return true;
//			}
//		}else{
//			return false;
//		}
//	}

	/**
	 * 更新该用户的在缓存中的信息(登录有效时长信息:validTime)
	 * @author huangsongbo
	 * @param userId
	 */
//	public void updateValidTime(Integer userId) {
//		SysUser sysUser=SysUserCacher.checkTimeOutUser(userId);
//		if(sysUser==null){
//			logger.error("------更新用户登录有效时长接口中,缓存找不到对应用户信息:这种情况不允许出现");
//			return;
//		}
//		SysUserCacher.updateTimeOutUser(sysUser);
//	}

	/**
	 * 检测用户是否已登录(检测缓存信息是否存在)
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public boolean checkisLoginIn2(Integer id, String terminalImei) {
		boolean flag=false;
		SysUser sysUser=get(id);
		logger.info("======id : " + id + "; terminalImei : " + terminalImei + "; sysUser.getToken() : " + sysUser.getToken());
		SysUser sysUserFromCache=SysUserCacher.checkTimeOutUserByToken(sysUser.getToken());
		logger.info("======id : " + id + "; sysUserFromCache : " + sysUserFromCache);
		if(sysUserFromCache==null||sysUserFromCache.getId().equals(new Integer(0))){
			/*缓存信息不存在,帐号没有被登录*/
			flag=false;
		}else{
			logger.info("======id : " + id + "; sysUserFromCache.getTerminalImei() : " + sysUserFromCache.getTerminalImei());
			if(StringUtils.equals(terminalImei, sysUserFromCache.getTerminalImei())){
				flag=false;
			}else{
				flag=true;
			}
		}
		return flag;
	}

	/**
	 * LoginUser转化为SysUser
	 * @author huangsongbo
	 * @param loginUser
	 * @return
	 */
	public SysUser getSysUserByLoginUser(LoginUser loginUser) {
		SysUser sysUser = null;
		if(loginUser!=null){
			sysUser=new SysUser();
			sysUser.setId(loginUser.getId());
			sysUser.setUserType(loginUser.getUserType());
			sysUser.setSysCode(loginUser.getLoginId());
			sysUser.setNickName(loginUser.getLoginName());
			sysUser.setMobile(loginUser.getLoginPhone());
			sysUser.setEmail(loginUser.getLoginEmail());
			sysUser.setUserName(loginUser.getName());
			sysUser.setAppKey(loginUser.getAppKey());
			sysUser.setToken(loginUser.getToken());
			sysUser.setMediaType(org.apache.commons.lang3.StringUtils.isBlank(loginUser.getDeviceId())?null:Integer.valueOf(loginUser.getDeviceId()));
			sysUser.setMsgId(loginUser.getMsgId());
			sysUser.setGroupId(loginUser.getGroupId());
			sysUser.setSex(loginUser.getSex());
			sysUser.setPicPath(loginUser.getPicPath());
			sysUser.setBrandIds(loginUser.getBrandIds());
		}
		return sysUser;
	}

	/**
	 * 通过角色CODE查询用户
	 * @param roleCode
	 * @return
	 */
	@Override
	public List<SysUser> getUserByRoleCode(String roleCode){
		if( org.apache.commons.lang3.StringUtils.isNotBlank(roleCode) ){
			return sysUserMapper.getUserByRoleCode(roleCode);
		}
		return null;
	}

	/**
	 * 通过登录四参数验证该用户是否异常
	 * @author huangsongbo
	 * @param appKey
	 * @param token
	 * @param deviceId
	 * @param mediaType
	 * @return
	 */
	public LoginUser isAllowedThrough(String appKey, String token, String deviceId, String mediaType) {
		// 参数验证 ->start
		if(
				org.apache.commons.lang3.StringUtils.isBlank(appKey)
				|| org.apache.commons.lang3.StringUtils.isBlank(token)
				|| org.apache.commons.lang3.StringUtils.isBlank(deviceId)
			){
			return null;
		}
		// 参数验证 ->end
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		sysUser.setIsDeleted(0);
		sysUser.setToken(token);
		sysUser.setAppKey(appKey);
		sysUser.setMediaType(new Integer(deviceId));
		List<SysUser> list = this.getList(sysUser);
		if (list != null && list.size() == 1) {
			SysUser user = list.get(0);
			if(user != null){
				loginUser.setId(user.getId());
				loginUser.setLoginId(user.getSysCode());
				loginUser.setLoginName(user.getNickName());
				loginUser.setLoginEmail(user.getEmail());
				loginUser.setName(user.getUserName());
				loginUser.setUserType(user.getUserType());
				loginUser.setGroupId(user.getGroupId());
				loginUser.setSex(user.getSex());
			}else{
				logger.info("user is null.....");
				return null;
			}
		}
		return loginUser;
	}

	/**
	 * 获取session登录信息
	 * @author xiaoxc
	 * @param request
	 * @return LoginUser
	 */
	@Override
	public LoginUser getSessionLoginUser(HttpServletRequest request){
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		return loginUser;
	}

	/**
	 * 用token对加密文件的key进行加密
	 */
    @Override
    public SysUser EencryptKey(SysUser user) {
		String token = "";
        if(org.apache.commons.lang3.StringUtils.isNotBlank(user.getToken())){
        	//截取token的前八位
			if(user.getToken().length() < 8){
				//字符长度不够8位则在后面补0
				token = user.getToken()+String.format("%1$0"+(8-user.getToken().length())+"d",0);
			}else{
				token = user.getToken().substring(0,8);
			}
			String encrypt = AESUtil2.encryptFile(key, token);
			user.setCryptKey(encrypt);
			return user;
		}else{
        	logger.error("用户id="+user.getId()+"返回token信息为null！");
		}
		return user;
    }

	@Override
	public void updateBalanceAmountByUserId(SysUser user) {
		sysUserMapper.updateBalanceAmountByUserId(user);
		
	}

	/**
	 * 获得用户头像
	 */
	@Override
	public SysUser getUserPic(SysUser sysUser) {
		if(sysUser ==null || sysUser.getId()==null) {
			return sysUser;
		}
		if (StringUtils.isEmpty(sysUser.getPicPath())) {
			Integer sexValue=sysUser.getSex();
			if(sexValue == null ) {
				sexValue = 1;
			}
			//查找默认头像地址
			SysDictionary dictionary=new SysDictionary();
			dictionary=sysDictionaryService.getSysDictionary("userDefaultPicture", sexValue);
			if(dictionary !=null && dictionary.getPicId()!=null) {
				ResPic userPic=resPicServicer.get(dictionary.getPicId());
				if(userPic != null && userPic.getPicPath() !=null) {
					sysUser.setUserPic(userPic.getPicPath());
				}
			}
			
		}
		return sysUser;
	}

	@Override
	public boolean checkWhiteList(String terminalImei) {
		// TODO Auto-generated method stub
		WhiteDeviceList whiteDeviceList = new WhiteDeviceList();
		List<WhiteDeviceList> list = mobileRenderRecordMapper.checkWhiteList(whiteDeviceList);
		for (WhiteDeviceList whiteDeviceList2 : list) {
			if (terminalImei.equals(whiteDeviceList2.getDeviceKey())) {
				return true;
			}else {
				continue;
			}
		}
		return false;
	}
	
	/**
	 * 移动端续费
	 * @param sysUser
	 * @return
	 */
	@Override
	public void renew(SysUser sysUser) {
		sysUserMapper.renew(sysUser);
	}

}
