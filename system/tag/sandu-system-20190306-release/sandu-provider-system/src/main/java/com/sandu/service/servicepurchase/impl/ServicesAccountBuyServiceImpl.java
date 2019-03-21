package com.sandu.service.servicepurchase.impl;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.UserLevelCfg;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.company.service.SysUserLevelConfigService;
import com.sandu.api.company.service.SysUserLevelPriceService;
import com.sandu.api.constance.EntityClassTypeConstant;
import com.sandu.api.constance.PlatformConstant;
import com.sandu.api.constance.UserConstant;
import com.sandu.api.servicepurchase.input.ServicesPurchaseRecordAdd;
import com.sandu.api.servicepurchase.model.*;
import com.sandu.api.servicepurchase.serivce.ServicesAccountBuyService;
import com.sandu.api.servicepurchase.serivce.ServicesAccountRelService;
import com.sandu.api.servicepurchase.serivce.ServicesPurchaseRecordService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.UserJurisdiction;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.util.Constants;
import com.sandu.commons.Utils;
import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.service.company.dao.SysResLevelCfgMapper;
import com.sandu.service.servicepurchase.dao.SysUserRoleMapper;
import com.sandu.service.servicepurchase.dao.UserJurisdictionMapper;
import com.sandu.systemutil.SystemCommonUtil;
import com.sandu.user.model.SysUserLevelPrice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.systemutil.SystemCommonUtil.getMd5Password;

@Service
public class ServicesAccountBuyServiceImpl implements ServicesAccountBuyService {

	private final BaseCompanyService baseCompanyService;
	private final SysUserService sysUserService;
	private final ServicesAccountRelService servicesAccountRelService;
	private final SysUserRoleMapper sysUserRoleMapper;
	private final PayAccountService payAccountService;
	private final SysResLevelCfgMapper sysResLevelCfgMapper;
	private final SysUserLevelPriceService sysUserLevelPriceService;
	private final SysUserLevelConfigService sysUserLevelConfigService;
	private final ServicesPurchaseRecordService servicesPurchaseRecordService;
	private final UserJurisdictionMapper userJurisdictionMapper;

	/**
	 * 支付方式(0-支付宝;1-微信;2-其他)'
	 */
	private final static String PAY_TYPE = "2";
	/**
	 * 支付状态 0-待支付;1-支付成功;2-支付失败
	 */
	private final static String PAY_STATUS = "1";
	/**
	 * 删除标志（0：正常，1：删除）
	 */
	private final static Integer DELETE_FLAG_0 = 0;

	/**
	 * 经销商
	 */
	private final static Integer AGENCY = 3;

	/**
	 * 单位：年
	 */
	private final static String PRICE_UNIT_YEAR = "0";
	/**
	 * 单位：月
	 */
	private final static String PRICE_UNIT_MONTH = "1";
	/**
	 * 单位：年
	 */
	private final static String PRICE_UNIT_DAY = "2";
	/**
	 * 试用套餐
	 */
	private final static Integer SERVICES_TYPE_TRY = 1;
	/**
	 * 正式套餐
	 */
	private final static Integer SERVICES_TYPE_FORMAL = 0;
	/**
	 * 代购
	 */
	private final static Integer BUSSINESS_TYPE_PURCHASE = 3;
	/**
	 * 购买来源(1-三度官网;2-三度后台代购;3-商家后台;4-科创;5-抢工长)
	 */
	private final static String PURCHASE_SOURCE_SANDU_PURCHASE = "2";

	/**
	 * 默认生成的密码长度
	 */
	private final static Integer PWD_LENGTH = 6;

	@Autowired
	public ServicesAccountBuyServiceImpl(BaseCompanyService baseCompanyService, SysUserService sysUserService, ServicesAccountRelService servicesAccountRelService, SysUserRoleMapper sysUserRoleMapper, PayAccountService payAccountService, SysResLevelCfgMapper sysResLevelCfgMapper, SysUserLevelPriceService sysUserLevelPriceService, SysUserLevelConfigService sysUserLevelConfigService, ServicesPurchaseRecordService servicesPurchaseRecordService, UserJurisdictionMapper userJurisdictionMapper) {
		this.baseCompanyService = baseCompanyService;
		this.sysUserService = sysUserService;
		this.servicesAccountRelService = servicesAccountRelService;
		this.sysUserRoleMapper = sysUserRoleMapper;
		this.payAccountService = payAccountService;
		this.sysResLevelCfgMapper = sysResLevelCfgMapper;
		this.sysUserLevelPriceService = sysUserLevelPriceService;
		this.sysUserLevelConfigService = sysUserLevelConfigService;
		this.servicesPurchaseRecordService = servicesPurchaseRecordService;
		this.userJurisdictionMapper = userJurisdictionMapper;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public boolean addServicesAccount(ServicesPurchaseRecordAdd servicesPurchaseRecordAdd, ServicesBaseInfo servicesBaseInfo,
									  ServicesPrice servicesPrice, Map<Long, Long> roleAndPlatfromMapSource, LoginUser loginUser) {
		Map<Integer, Long> roleAndPlatfromMap = roleAndPlatfromMapSource.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().intValue(), Map.Entry::getValue));
		try {
			//插入购买记录
			long servicesRecordId = insertServicePurchaseRecord(servicesPurchaseRecordAdd, loginUser, servicesPrice);
			if (servicesRecordId > 0) {
				//账号默认登录生效
				boolean effective = false;
				Date endDate = null;
				Integer days = 0;
				Calendar d = Calendar.getInstance();
				//有效时长判断
				//试用套餐 存天数
				if (servicesBaseInfo.getServicesType().equals(SERVICES_TYPE_TRY)) {
					if (PRICE_UNIT_YEAR.equals(servicesPrice.getPriceUnit())) {
						days = d.getActualMaximum(Calendar.DAY_OF_YEAR) * servicesPrice.getDuration() + servicesPrice.getGiveDuration();
					}
					if (PRICE_UNIT_MONTH.equals(servicesPrice.getPriceUnit())) {
						days = d.getActualMaximum(Calendar.DAY_OF_MONTH) * servicesPrice.getDuration() + servicesPrice.getGiveDuration();
					}
					if (PRICE_UNIT_DAY.equals(servicesPrice.getPriceUnit())) {
						days = servicesPrice.getDuration() + servicesPrice.getGiveDuration();
					}

				}
				//正式套餐 存月份
				if (servicesBaseInfo.getServicesType().equals(SERVICES_TYPE_FORMAL)) {
					if (PRICE_UNIT_YEAR.equals(servicesPrice.getPriceUnit())) {
						days = servicesPrice.getDuration() * 12;
					}
					if (PRICE_UNIT_MONTH.equals(servicesPrice.getPriceUnit())) {
						days = servicesPrice.getDuration();
					}
					if (PRICE_UNIT_DAY.equals(servicesPrice.getPriceUnit())) {
						if (servicesPrice.getDuration() != null && servicesPrice.getDuration() != 0) {
							days = servicesPrice.getDuration() / 30;
						}
					}
					if (servicesPrice.getGiveDuration() != null && servicesPrice.getGiveDuration() != 0) {
						days += servicesPrice.getGiveDuration() / 30;
					}
				}
				if (effective) {
					//失效时间
					Calendar ca = Calendar.getInstance();
					ca.add(Calendar.DATE, days);
					endDate = ca.getTime();
				}

				//开通账户平台
				String platFromBusinessType = "2b";
				// 生成账号
				saveUser(servicesBaseInfo.getServicesType() == 0 ? 1 : 0, platFromBusinessType, endDate, effective,
						Math.toIntExact(servicesPurchaseRecordAdd.getServiceId()), roleAndPlatfromMap, servicesRecordId,
						servicesPurchaseRecordAdd.getCompanyId(), servicesPurchaseRecordAdd.getUserTypeId(),
//						days, servicesBaseInfo.getServicesStatus(), servicesPurchaseRecordAdd.getNum(),
						days, "0", servicesPurchaseRecordAdd.getNum(),
						loginUser, servicesPrice.getSanduCurrency() == null ? 0 : servicesPrice.getSanduCurrency());
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}


	/**
	 * 插入购买记录
	 *
	 * @param servicesPurchaseRecordAdd
	 * @param loginUser
	 * @param servicesPrice
	 * @return
	 */
	private long insertServicePurchaseRecord(ServicesPurchaseRecordAdd servicesPurchaseRecordAdd, com.sandu.api.user.model.LoginUser loginUser, ServicesPrice servicesPrice) {

		ServicesPurchaseRecord servicesPurchaseRecord = new ServicesPurchaseRecord();
		//生成订单编号
		String orderNo = IdGenerator.generateNo();
		servicesPurchaseRecord.setOrderNo(orderNo);
		servicesPurchaseRecord.setServicesId(servicesPurchaseRecordAdd.getServiceId());
		servicesPurchaseRecord.setCompanyId(String.valueOf(servicesPurchaseRecordAdd.getCompanyId()));
		servicesPurchaseRecord.setUserScope(String.valueOf(servicesPurchaseRecordAdd.getUserTypeId()));
		servicesPurchaseRecord.setRemark(servicesPurchaseRecordAdd.getRemark());
		servicesPurchaseRecord.setUnitPrice(servicesPrice.getPrice());
		servicesPurchaseRecord.setPurchaseAmount(BigDecimal.valueOf(servicesPurchaseRecordAdd.getNum()));
		servicesPurchaseRecord.setPurchaseMomey(servicesPrice.getPrice().multiply(BigDecimal.valueOf(servicesPurchaseRecordAdd.getNum())));

		servicesPurchaseRecord.setSanduCurrency(servicesPrice.getSanduCurrency());
		servicesPurchaseRecord.setFreeRenderDuration(servicesPrice.getFreeRenderDuration());
		servicesPurchaseRecord.setGiveDuration(servicesPrice.getGiveDuration());
		//支付状态默认成功
		servicesPurchaseRecord.setPurchaseStatus(PAY_STATUS);
		//支付方式默认为其他
		servicesPurchaseRecord.setPayType(PAY_TYPE);
		//支付时间默认为创建时间
		servicesPurchaseRecord.setPurchaseTime(new Date());
		//操作人信息
		servicesPurchaseRecord.setCreator(loginUser.getLoginName());
		servicesPurchaseRecord.setModifier(loginUser.getLoginName());
		servicesPurchaseRecord.setGmtCreate(new Date());
		servicesPurchaseRecord.setGmtModified(new Date());
		//删除标志
		servicesPurchaseRecord.setIsDeleted(DELETE_FLAG_0);
		servicesPurchaseRecord.setPurchaseSource(PURCHASE_SOURCE_SANDU_PURCHASE);
		servicesPurchaseRecord.setDuration(servicesPrice.getDuration());
		servicesPurchaseRecord.setPriceUnit(servicesPrice.getPriceUnit());
		servicesPurchaseRecord.setServicesPriceId(Math.toIntExact(servicesPrice.getId()));
		servicesPurchaseRecord.setUserid(loginUser.getLoginId());
		servicesPurchaseRecord.setBusinessType(BUSSINESS_TYPE_PURCHASE + "");
		servicesPurchaseRecordService.addServicesPurchaseRecord(servicesPurchaseRecord);
		return servicesPurchaseRecord.getId();
	}

	/**
	 * 生成账号
	 *
	 * @param endDate                  套餐结束时间
	 * @param effective                是否立即生效（默认否）
	 * @param servicesId               套餐id
	 * @param servicesPurchaseRecordId 套餐记录id
	 * @param companyId                企业
	 * @param userTypeId               用户类型
	 * @param status                   账号状态
	 * @param num                      购买数量
	 * @param loginUser                登录信息
	 * @param amount                   赠送的度币
	 * @throws Exception
	 */
	private void saveUser(Integer servicesType, String platFromBusinessType, Date endDate, boolean effective,
						  Integer servicesId, Map<Integer, Long> roleAndPlatfromMap, long servicesPurchaseRecordId,
						  Integer companyId, Integer userTypeId, Integer days, String status, Integer num,
						  com.sandu.api.user.model.LoginUser loginUser, int amount) throws Exception {
		//账号
		String usernameCode = getUserNameMaxCode(companyId, userTypeId);

		SysUser sysUser;
		//套餐对象
		List<ServicesAccountRel> servicesAccountRelList = new ArrayList<>();
		Set<String> nickNameSet = new HashSet<>();
		for (int i = 1; i <= num; i++) {
			//生成随机密码
//            String password = getStringRandom(PWD_LENGTH);
			String password = "123456";
			//组合账号
			sysUser = getSysUser(effective, servicesType, companyId, userTypeId, days, password, loginUser.getLoginName(), usernameCode, nickNameSet, i);
			//套装账号记录
			getServicesAccountRel(endDate, effective, servicesPurchaseRecordId, companyId, servicesId, status, sysUser, servicesAccountRelList, password);
		}

		//保存账号记录
		int serviceNum = servicesAccountRelService.batchInsertServiceAccount(servicesAccountRelList);
		if (serviceNum == servicesAccountRelList.size()) {
			//配置账号角色
			insertUserRole(roleAndPlatfromMap, platFromBusinessType, loginUser, nickNameSet, amount);
		} else {
			throw new Exception("新增账号失败,请重新添加");
		}

	}

	/**
	 * 初始化用户等级
	 *
	 * @param userId
	 * @param useType
	 */
	private void initUserLevel(Integer userId, Integer useType) {
		UserLevelCfg cfg = new UserLevelCfg();
		cfg.setUserId(userId);
		cfg.setPcLimitKey(Constants.USER_LEVEL_PC_LIMIT_1);
		cfg.setMobileLimitKey(Constants.USER_LEVEL_MOBILE_LIMIT_1);
		cfg.setUserGroupType(useType);
		cfg.setUserLevel(Constants.USER_LEVEL_INIT);
		cfg.setVersion(Constants.USER_LEVEL_USER_PAY_TYPE_BASE);
		this.addUserLevelLimit(cfg);
		SysUserLevelPrice levelPrice = sysUserLevelPriceService.getIdByUserType(useType);
		if (levelPrice != null && levelPrice.getId() > 0) {
			sysUserLevelConfigService.initUserLevelByLevelPriceId(userId, levelPrice.getId());
		}
	}

	private boolean addUserLevelLimit(UserLevelCfg cfg) {
		if (cfg == null) {
			return false;
		}

		if (cfg.getUserId() <= 0 || cfg.getUserLevel() <= 0) {
			return false;
		}

		if (cfg.getUserGroupType() < 0 || cfg.getVersion() < 0) {
			return false;
		}

		if (StringUtils.isEmpty(cfg.getMobileLimitKey()) || StringUtils.isEmpty(cfg.getPcLimitKey())) {
			return false;
		}

		sysResLevelCfgMapper.addUserResLevel(cfg);
		sysResLevelCfgMapper.addUserDeviceLimit(cfg);
		return true;
	}

	/**
	 * 开通用户积分
	 *
	 * @param userId
	 */
	private void insertPayAccount(Integer userId, String platFromBusinessType, LoginUser loginUser, int amount) {
		PayAccount payAccount = new PayAccount();
		payAccount.setUserId(userId);
		payAccount.setPlatformBussinessType(platFromBusinessType);
		payAccount.setIsDeleted(DELETE_FLAG_0);
		payAccount.setConsumAmount(0D);
		payAccount.setBalanceAmount((double) (amount * 10));
		SystemCommonUtil.sysSave(payAccount, loginUser, EntityClassTypeConstant.ENTITY_CLASS_TYPE_PAYACCOUNT);
		payAccountService.add(payAccount);
	}

	/**
	 * 配置账号角色
	 *
	 * @param nickNameSet
	 */
	private void insertUserRole(Map<Integer, Long> roleAndPlatFromMap, String platFromBusinessType, com.sandu.api.user.model.LoginUser loginUser, Set<String> nickNameSet, int amount) {
		//查询插入的账号，并配置角色
		List<SysUser> userList = sysUserService.getUserByNickName(new ArrayList<>(nickNameSet));

		if (!CollectionUtils.isEmpty(userList)) {
			List<SysUserRole> sysUserRoleList = new ArrayList<>();
			List<UserJurisdiction> userJurisdictionList = new ArrayList<>();
			SysUserRole sysUserRole;
			UserJurisdiction userJurisdiction;
			for (SysUser user : userList) {
				//套餐平台ids
				Set<Long> platFromIds = new HashSet<>();
				for (Map.Entry map : roleAndPlatFromMap.entrySet()) {
					//用户角色关联
					sysUserRole = new SysUserRole();
					sysUserRole.setUserId(user.getId());
					sysUserRole.setRoleId(Long.valueOf(map.getKey() + ""));
					sysUserRole.setCreator(loginUser.getLoginName());
					sysUserRole.setGmtCreate(new Date());
					sysUserRole.setModifier(loginUser.getLoginName());
					sysUserRole.setGmtModified(new Date());
					sysUserRole.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
					sysUserRole.setIsDeleted(DELETE_FLAG_0);
					sysUserRoleList.add(sysUserRole);
					//套餐平台ids
					platFromIds.add((Long) map.getValue());
				}
				//用户平台关联
				for (Long platFromId : platFromIds) {
					userJurisdiction = new UserJurisdiction();
					userJurisdiction.setUserId(user.getId());
					userJurisdiction.setPlatformId((long) Math.toIntExact(platFromId));
					userJurisdiction.setJurisdictionStatus(1);
					userJurisdiction.setModifierUserId(loginUser.getId());
					SystemCommonUtil.sysSave(userJurisdiction, loginUser, EntityClassTypeConstant.ENTITY_CLASS_TYPE_USERJURISDICTION);
					userJurisdictionList.add(userJurisdiction);
				}

				//开通用户平台积分
				insertPayAccount(user.getId().intValue(), platFromBusinessType, loginUser, amount);

				//初始化用户等级
				initUserLevel(user.getId().intValue(), user.getUseType());
			}
			//用户角色
			sysUserRoleMapper.insertUserRoleList(sysUserRoleList);
			//用户平台
			userJurisdictionMapper.insertUserPlatformList(userJurisdictionList);
		}
	}


	/**
	 * 套餐账号记录
	 *
	 * @param endDate                  结束时间
	 * @param effective                是否立即生效 默认false
	 * @param servicesPurchaseRecordId 套餐购买记录id
	 * @param companyId                公司id
	 * @param servicesId               套餐id
	 * @param status                   套餐使用状态(0-未使用;1-使用)
	 * @param sysUser                  用户信息
	 * @param servicesAccountRelList   需要组装的对象list
	 * @param password                 明文密码
	 */
	private void getServicesAccountRel(Date endDate, boolean effective, long servicesPurchaseRecordId, Integer companyId, Integer servicesId, String status, SysUser sysUser, List<ServicesAccountRel> servicesAccountRelList, String password) {


		ServicesAccountRel servicesAccountRel = new ServicesAccountRel();
		servicesAccountRel.setAccount(sysUser.getNickName());
		//这里要求存放明文初始密码
		servicesAccountRel.setPassword(password);
		servicesAccountRel.setCreator(sysUser.getCreator());
		servicesAccountRel.setGmtCreate(sysUser.getGmtCreate());
		servicesAccountRel.setCompanyId(String.valueOf(companyId));
		servicesAccountRel.setServicesId(Long.valueOf(servicesId));
		servicesAccountRel.setGmtModified(sysUser.getGmtModified());
		servicesAccountRel.setBusinessType(String.valueOf(BUSSINESS_TYPE_PURCHASE));
		servicesAccountRel.setUserId(sysUser.getId());
		if (effective) {
			servicesAccountRel.setEffectiveBegin(new Date());
			servicesAccountRel.setEffectiveEnd(endDate);
		}
		servicesAccountRel.setModifier(sysUser.getModifier());
		servicesAccountRel.setPurchaseRecordId(servicesPurchaseRecordId);
		servicesAccountRel.setStatus(status);
		servicesAccountRel.setIsDeleted(0);
		servicesAccountRelList.add(servicesAccountRel);
	}

	/**
	 * 用户账号
	 *
	 * @param companyId
	 * @param userTypeId
	 * @param userName
	 * @param usernameCode
	 * @param nickNameSet
	 * @param i
	 * @return
	 */
	private SysUser getSysUser(boolean effective, Integer servicesType, Integer companyId, Integer userTypeId, Integer days, String password, String userName, String usernameCode, Set<String> nickNameSet, int i) {
		SysUser sysUser;
		//拼接用户名
		usernameCode = usernameCode.replace(" ", "");
		Integer code = Integer.parseInt(usernameCode.substring(usernameCode.length() - 4)) + i;
		String str;
		if (userTypeId.equals(AGENCY)) {
			//经销商后面是5位小数，其他是4位
			str = String.format("%05d", code);
		} else {
			str = String.format("%04d", code);
		}
		String prefix = usernameCode.substring(0, usernameCode.length() - 4);
		String nickName = prefix + str;
		nickNameSet.add(nickName);

		sysUser = new SysUser();
		sysUser.setNickName(nickName);
		//MD5加密密码
		sysUser.setPassword(getMd5Password(password));
		//是否需要更新密码标识
		sysUser.setPasswordUpdateFlag(UserConstant.PASSWORD_NEED_UPDATE);
		//默认为B端用户
		sysUser.setPlatformType(PlatformConstant.PC_2B_VALUE);
		sysUser.setUseType(servicesType);
		sysUser.setValidTime(days);
		sysUser.setUserType(userTypeId);
		//不知道是什么，给个0就好了
		sysUser.setGroupId(0);
		if (effective) {
			//失效时间
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DATE, days);
			Date date = ca.getTime();
			sysUser.setFailureTime(date);
		}
		sysUser.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		sysUser.setCreator(userName);
		sysUser.setGmtCreate(new Date());
		sysUser.setGmtModified(new Date());
		sysUser.setModifier(userName);
		sysUser.setIsDeleted(DELETE_FLAG_0);
		sysUser.setBusinessAdministrationId(companyId.longValue());
		sysUser.setIsLoginBefore(0);
		sysUser.setCompanyId(companyId.longValue());
		sysUser.setServicesFlag(1);
		if (userTypeId.equals(AGENCY)) {
			sysUser.setBusinessAdministrationId(null);
		}
		Long userId = sysUserService.insertUser(sysUser);
		sysUser.setId(userId);
		return sysUser;
	}


	/**
	 * 生成随机数字和字母,
	 *
	 * @param length 生成字符串长度
	 * @return
	 */
	public String getStringRandom(int length) {

		String val = "";
		Random random = new Random();
		//length为几位密码
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 获取最大code
	 *
	 * @param companyId
	 * @param userType
	 * @return
	 */
	private String getUserNameMaxCode(Integer companyId, Integer userType) {
		// 前缀
		StringBuilder commanyCodePrefix = new StringBuilder();
		//后缀
		StringBuilder userNameCodeSuffix = new StringBuilder();
		BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(companyId.longValue());
		commanyCodePrefix.append(baseCompany.getCompanyCode());
		if (userType.equals(AGENCY)) {
			commanyCodePrefix.append("F0000A");
		} else {
			commanyCodePrefix.append("EIU");
		}

		//模糊前缀条件
		String commanyCodePrefixS = commanyCodePrefix.toString() + "%";

		//查询最大序列
		String userNameCodeMax = sysUserService.getMaxCompanyInUserNameCode(commanyCodePrefixS, companyId.longValue());

		Integer nameCode = 0;
		if (StringUtils.isNotEmpty(userNameCodeMax)) {
			nameCode = Integer.parseInt(userNameCodeMax.substring(commanyCodePrefixS.length() - 1, userNameCodeMax.length()));
			nameCode++;
			userNameCodeSuffix.append(nameCode);
		} else {
			userNameCodeSuffix.append("1");
		}

		int k = userNameCodeSuffix.length();
		for (int i = 0; i < 4 - k; i++) {
			userNameCodeSuffix.insert(0, "0");
		}

		return commanyCodePrefix.append(userNameCodeSuffix).toString();
	}


}
