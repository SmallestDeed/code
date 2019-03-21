package com.sandu.service.servicepurchase.impl.biz;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.platform.service.BasePlatformService;
import com.sandu.api.servicepurchase.ServicePurchaseBizException;
import com.sandu.api.servicepurchase.constant.ServicesPurchaseConstant;
import com.sandu.api.servicepurchase.input.InnerServiceRenew;
import com.sandu.api.servicepurchase.input.query.ServiceQuery;
import com.sandu.api.servicepurchase.model.*;
import com.sandu.api.servicepurchase.model.bo.*;
import com.sandu.api.servicepurchase.model.vo.OfficalServicesListVO;
import com.sandu.api.servicepurchase.output.ServicesBaseInfoVO;
import com.sandu.api.servicepurchase.output.ServicesPriceListVO;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import com.sandu.api.servicepurchase.output.UserScopeVO;
import com.sandu.api.servicepurchase.serivce.*;
import com.sandu.api.servicepurchase.serivce.biz.ServicesPurchaseBizService;
import com.sandu.api.user.input.UserBatchAdd;
import com.sandu.api.user.model.ResponseBo;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.output.UserBatchVO;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import com.sandu.constant.Punctuation;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.service.redis.RedisService;
import com.sandu.service.servicepurchase.dao.ServiceActionDetailsLogMapper;
import com.sandu.service.servicepurchase.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sandu.api.servicepurchase.constant.ServicesPurchaseConstant.*;
import static com.sandu.service.servicepurchase.util.DateUtil.millionSecondsOfDay;
import static java.math.BigDecimal.ROUND_DOWN;

/**
 * @author Sandu
 */
@Service("servicesPurchaseBizService")
@Slf4j
@Transactional
public class ServicesPurchaseBizServiceImpl implements ServicesPurchaseBizService {

	private final BaseCompanyService companyService;
	private final ServicesBaseInfoService servicesBaseInfoService;
	private final DictionaryService sysDictionaryService;
	private final ServicesRoleRelService servicesRoleRelService;
	private final ServicesAccountRelService servicesAccountRelService;
	private final SysUserService sysUserService;
	private final ServicesPriceService servicesPriceService;
	private final ServicesPurchaseRecordService servicesPurchaseRecordService;
	private final SysUserManageService sysUserManageService;
	private final BaseCompanyService baseCompanyService;
	private final PayModelGroupRefService payModelGroupRefService;
	private final SysRoleService sysRoleService;
	private final BasePlatformService basePlatformService;
	private final RedisService redisService;
	private final ServiceActionDetailsLogMapper serviceActionDetailsLogMapper;

	@Value("${app.user.scope.company.values}")
	private String companyUserScopes;
	@Value("${app.user.scope.designer.values}")
	private String designerUserScopes;
	@Value("${app.user.scope.decoration.values}")
	private String decorationUserScopes;
	@Value("${msg.template}")
	private String msgTemplate;
	@Value("${msg.services.renewals.new.template}")
	private String msgRenewalsNewTemplate;
	@Value("${msg.services.renewals.template}")
	private String msgRenewalsTemplate;
	@Value("${msg.services.renewals.try.template}")
	private String msgRenewalsTryTemplate;
	@Value("${service.kechang.qianggongzhang.companyIds}")
	private String kqCompanyIds;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	public ServicesPurchaseBizServiceImpl(DictionaryService sysDictionaryService, BaseCompanyService companyService, ServicesBaseInfoService servicesBaseInfoService, ServicesRoleRelService servicesRoleRelService, ServicesAccountRelService servicesAccountRelService, SysUserService sysUserService, ServicesPriceService servicesPriceService, ServicesPurchaseRecordService servicesPurchaseRecordService, SysUserManageService sysUserManageService, BaseCompanyService baseCompanyService, PayModelGroupRefService payModelGroupRefService, SysRoleService sysRoleService, BasePlatformService basePlatformService, RedisService redisService, ServiceActionDetailsLogMapper serviceActionDetailsLogMapper) {
		this.sysDictionaryService = sysDictionaryService;
		this.companyService = companyService;
		this.servicesBaseInfoService = servicesBaseInfoService;
		this.servicesRoleRelService = servicesRoleRelService;
		this.servicesAccountRelService = servicesAccountRelService;
		this.sysUserService = sysUserService;
		this.servicesPriceService = servicesPriceService;
		this.servicesPurchaseRecordService = servicesPurchaseRecordService;
		this.sysUserManageService = sysUserManageService;
		this.baseCompanyService = baseCompanyService;
		this.payModelGroupRefService = payModelGroupRefService;
		this.sysRoleService = sysRoleService;
		this.basePlatformService = basePlatformService;
		this.redisService = redisService;
		this.serviceActionDetailsLogMapper = serviceActionDetailsLogMapper;
	}

	@Override
	public PageInfo<ServicesListBO> getServicesByScope(ServiceQuery query) {
		if (query.getCompanyId() == null) {
			throw new RuntimeException("企业ID有误");
		}
		BaseCompany baseCompany = companyService.getCompanyById(query.getCompanyId());
		if(baseCompany == null) {
			throw new RuntimeException("没有找到该企业");
		}
		//处理经销商用户UserScope
		SysUser sysUser = sysUserService.get(query.getUserId());
		if (Objects.nonNull(sysUser)
				&& Integer.valueOf(3).equals(sysUser.getUserType())
				&& Objects.nonNull(sysUser.getBusinessAdministrationId())) {
			query.setCompanyId(sysUser.getBusinessAdministrationId().intValue());
		}
		final List<Integer> userTypes = new LinkedList<>();
		Integer businessType = baseCompany.getBusinessType();
		//设置用户类型
		List<SysDictionary> companyUserType = sysDictionaryService.getByTypeAndValues("brandBusinessType", businessType);
		if (!companyUserType.isEmpty()) {
			companyUserType = sysDictionaryService.listByType(companyUserType.get(0).getValuekey());
		}
		userTypes.addAll(
				companyUserType.stream().map(SysDictionary::getAtt1).filter(org.apache.commons.lang3.StringUtils::isNoneBlank)
						.map(Integer::valueOf).distinct().collect(Collectors.toList())
		);
		if (userTypes.isEmpty()) {
			return new PageInfo<>(Collections.emptyList());
		}
		if (query.getUserScope() != null) {
			if (!userTypes.contains(query.getUserScope())) {
				return new PageInfo<>(Collections.emptyList());
			} else {
				userTypes.clear();
				userTypes.add(query.getUserScope());
			}
		}
		PageInfo<ServicesListBO> bos = servicesBaseInfoService.getServicesListByUserScope(userTypes, query.getSaleChannel(), query.getPage(), query.getLimit());
		List<SysDictionary> userType = sysDictionaryService.listByType("userType");
		//设置用户类型对应名称
		bos.getList().forEach(bo -> {
			bo.setScopeNames(new ArrayList<>());
			Splitter.on(Punctuation.COMMA).trimResults().splitToList(bo.getUserScope()).forEach(scopeValue -> {
				userType.stream().filter(item -> item.getValue().equals(Integer.valueOf(scopeValue)))
						.forEach(item -> bo.getScopeNames().add(item.getName()));
			});
			//处理价格
			List<ServicesPrice> priceList = new ArrayList<>();
			//获取当前公司的当前套餐的价格
			priceList = servicesPriceService.queryByServicesIdAndCompanyId(Long.parseLong(bo.getServicesId() + ""), query.getCompanyId(), ServicesPurchaseConstant.DELETE_FLAG_0);
			if (Objects.isNull(priceList) || priceList.isEmpty()) {
				//取当前套餐的通用价格
				priceList = servicesPriceService.queryByServicesIdAndCompanyId(Long.parseLong(bo.getServicesId() + ""), ServicesPurchaseConstant.COMMON_COMPANY_ID, ServicesPurchaseConstant.DELETE_FLAG_0);
			}
			List<ServicesPriceListVO> prices = priceList.stream().map(price -> {
				ServicesPriceListVO vo = ServicesPriceListVO.builder().build();
				BeanUtils.copyProperties(price, vo);
				return vo;
			}).collect(Collectors.toList());
			bo.setPriceList(prices);
		});
		return bos;
	}

	@Override
	public List<ServicesFuncBO> getServiceFuncsByServiceId(Integer serviceId) {
		if (serviceId == null) {
			throw new RuntimeException("套餐ID有误");
		}
		List<ServicesRoleRel> rel = servicesRoleRelService.getByServiceId(serviceId.longValue());
		if (Objects.isNull(rel)) {
			return Collections.emptyList();
		}
		//取服务权限
		Set<Long> roleIds = rel.stream().map(ServicesRoleRel::getRoleId).map(Integer::longValue).collect(Collectors.toSet());
		if(roleIds == null || roleIds.size() == 0) {
			throw new RuntimeException("角色id有误");
		}
		List<SysRole> roles = sysRoleService.getRolesByRoleIds(roleIds);
		if (roles == null) {
			log.error("获取服务权限失败,套餐ID:" + serviceId);
			throw new RuntimeException("获取服务权限失败");
		}
		//取平台信息
		Map<Integer, String> allPlatformIdAndName = basePlatformService.getAllPlatformIdAndName();
		return roles.stream().map(role -> {
			ServicesFuncBO bo = new ServicesFuncBO();
			bo.setFuncNames(Splitter.on(Punctuation.COMMA).trimResults().splitToList(Strings.nullToEmpty(role.getName())));
			bo.setPlatformName(allPlatformIdAndName.get(role.getPlatformId().intValue()));
			return bo;
		}).collect(Collectors.toList());
	}

	@Override
	public PageInfo<ServicesPurchaseListBO> getBePurchasedServices(ServiceQuery query) {
		if (query.getCompanyId() == null) {
			throw new RuntimeException("企业ID有误");
		}
		PageInfo<ServicesPurchaseListBO> bos = servicesRoleRelService.getBePurchasedServices(query.getCompanyId(), query.getPage(), query.getLimit());
		List<String> nickNames = bos.getList().stream().map(ServicesPurchaseListBO::getAccount).collect(Collectors.toList());
		//获取用户类型
		List<SysUser> users = sysUserService.getUserByNickName(nickNames);
		List<SysDictionary> userTypeNames = sysDictionaryService.listByType("userType");
		if (users != null) {
			bos.getList().forEach(bo ->
					users.forEach(user -> {
						if (bo.getAccount().equalsIgnoreCase(user.getNickName())) {
							bo.setUserType(user.getUserType() + "");
							userTypeNames.stream().filter(item -> item.getValue().toString().equals(bo.getUserType())).findFirst().ifPresent(
									item -> bo.setUserTypeName(item.getName())
							);
						}
					}));
		}
		bos.getList().forEach(item -> item.setDuration(setDurationTimeUnitString(item.getDuration(), item.getPriceUnit())));
		return bos;
	}

	@Override
	public PageInfo<ServicesPurchaseLogListBO> getPurchasedServicesLogs(ServiceQuery query) {
		if (query.getCompanyId() == null) {
			throw new RuntimeException("企业ID有误");
		}
		PageInfo<ServicesPurchaseLogListBO> pageInfo = servicesBaseInfoService.getPurchasedServicesLogsByCompanyId(query.getCompanyId(), query.getPage(), query.getLimit());
		List<String> names = pageInfo.getList().stream()
				.map(ServicesPurchaseLogListBO::getCreator)
				.filter(item -> !Strings.isNullOrEmpty(item))
				.distinct().collect(Collectors.toList());
		List<SysUser> users = sysUserService.getUserByNickName(names);
		pageInfo.getList().forEach(item -> {
			item.setPurchaseUseTime(setDurationTimeUnitString(item.getDuration() + "", item.getPriceUnit()));
			users.stream().filter(user -> user.getNickName().equals(item.getCreator())).findFirst().ifPresent(
					user -> {
						StringBuilder creator = new StringBuilder();
						if (org.apache.commons.lang3.StringUtils.isNoneBlank(user.getUserName())) {
							creator.append(user.getUserName());
						}
						if (org.apache.commons.lang3.StringUtils.isNoneBlank(user.getMobile())) {
							if (creator.length() > 0) {
								creator.append("(").append(user.getMobile()).append(")");
							} else {
								creator.append(user.getMobile());
							}
						}
						item.setCreator(creator.toString());
					}
			);
		});
		return pageInfo;
	}

	private String setDurationTimeUnitString(String duration, String priceUnit) {
		StringBuilder useTime = new StringBuilder();
		useTime.append(duration);
		switch (priceUnit) {
			case PRICE_UNIT_YEAR:
				useTime.append("年");
				break;
			case PRICE_UNIT_MONTH:
				useTime.append("月");
				break;
			case PRICE_UNIT_DAY:
				useTime.append("日");
				break;
			default:
				break;
		}
		return useTime.toString();
	}

	/**
	 * 点击购买加载套餐相关信息
	 */
	@Override
	public ServicesBaseInfoVO findServicesPriceById(Long servicesId) {
		ServicesBaseInfo info = servicesBaseInfoService.getById(servicesId);
		if (info == null) {
			return null;
		}
		ServicesBaseInfoVO servicesBaseInfoVO = new ServicesBaseInfoVO();
		servicesBaseInfoVO.setId(info.getId());
		servicesBaseInfoVO.setServicesName(info.getServicesName());          //套餐名称
		List<UserScopeVO> scopeLists = new ArrayList<UserScopeVO>();
		if (info.getUserScope() != null) {
			List<SysDictionary> userDictionary = sysDictionaryService.listByType("userType");
			String[] userScopeList = info.getUserScope().split(Punctuation.COMMA);
			for (String anUserScopeList : userScopeList) {
				for (SysDictionary dic : userDictionary) {
					if (dic.getValue().equals(Integer.valueOf(anUserScopeList))) {
						UserScopeVO userScopeVO = new UserScopeVO();
						userScopeVO.setUserTypeId(anUserScopeList);
						userScopeVO.setUserTypeName(dic.getName());
						scopeLists.add(userScopeVO);
						break;
					}
				}
			}
		}
		if (!scopeLists.isEmpty()) {
			servicesBaseInfoVO.setUserScope(scopeLists);
		}
		//获取价格策略
		List<ServicesPriceVO> priceList = servicesPriceService.findServicesPriceById(info.getId());
		if (priceList != null) {
			servicesBaseInfoVO.setPriceList(priceList);
		}
		return servicesBaseInfoVO;
	}

	/**
	 * 计算套餐的总价
	 * 单价*折扣*总数
	 */
	@Override
	public BigDecimal getServicesPayAmount(Long servicesId, Long priceId, Long purchaseAmount) {
		BigDecimal payAmount = null;
		//取套餐信息
		ServicesBaseInfo info = servicesBaseInfoService.getById(servicesId);
		if (info == null) {
			return new BigDecimal(0);
		}
		ServicesPrice servicesPrice = servicesPriceService.findPriceById(priceId);
		if (servicesPrice == null) {
			return new BigDecimal(0);
		}
		//取单价
		BigDecimal unitPrice = servicesPrice.getPrice();
		payAmount = unitPrice.multiply(new BigDecimal(purchaseAmount));
		return payAmount;
	}

	/**
	 * 官网试用逻辑
	 * 1.校验手机验证码
	 * 2.写入套餐购买记录
	 * 3.调用userCenter生成账号
	 * 4.写入套餐账户关系
	 * 5.调用赠送度币服务
	 * 6.账号生成成功短信通知
	 *
	 * @param add
	 * @return resultMap
	 * @date
	 */
	@Override
	public Map<String, Object> saveBuy(ServicesPurchaseRecord add) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			ServicesBaseInfo info = null;
			ServicesPrice servicesPrice = null;
			List<ServicesRoleRel> roleList = null;
			//校验手机短信验证码
			ResponseBo bo = sysUserService.checkPhone(add.getTelephone(), add.getTelephoneCode());
			if (!bo.getStatus()) {
				resultMap.put("error", bo.getMsg());
				return resultMap;
			}
			//获取试用套餐、指定渠道的基本信息
			List<ServicesBaseInfo> infoLists = servicesBaseInfoService.getServicesByType(SERVICES_TYPE_TRY + "", ServicesPurchaseConstant.PURCHASE_SOURCE_1);
			if (infoLists == null || infoLists.size() == 0) {
				resultMap.put("error", "请配置试用套餐信息!");
				return resultMap;
			}
			info = infoLists.get(0);
			add.setServicesId(info.getId());
			List<ServicesPrice> servicesPriceList = servicesPriceService.findServicesPriceList(info.getId());
			if (servicesPriceList == null || servicesPriceList.size() == 0) {
				resultMap.put("error", "请配置试用套餐价格!");
				return resultMap;
			}
			servicesPrice = servicesPriceList.get(0);
			add.setServicesPriceId(servicesPrice.getId().intValue());
			roleList = servicesRoleRelService.getByServiceId(add.getServicesId());
			if (roleList == null || roleList.size() == 0) {
				resultMap.put("error", "请配置试用套餐功能!");
				return resultMap;
			}
			UserBatchAdd userBatchAdd = new UserBatchAdd();
			userBatchAdd.setServiceId(add.getServicesId());
			userBatchAdd.setAccount(add.getPurchaseAmount().intValue());
			userBatchAdd.setMobile(add.getTelephone());
			String businessType = add.getBusinessType();
			boolean trialFlag = false;
			if (ServicesPurchaseConstant.BUSSINESS_TYPE_2.equals(businessType) && ServicesPurchaseConstant.PURCHASE_SOURCE_1.equals(add.getPurchaseSource())) {
				trialFlag = true;
				userBatchAdd.setAccount(1);
			}
			//组装数据
			ServicesPurchaseRecord record = assemblePurchaseData(add, servicesPrice);
			int recordId = servicesPurchaseRecordService.addServicesPurchaseRecord(record);
			if (recordId > 0) {
				userBatchAdd.setPurchaseRecordId(record.getId());
				List<Integer> roles = roleList.stream().map(roleId -> Integer.parseInt(roleId.getRoleId().toString())).collect(Collectors.toList());
				userBatchAdd.setRoleLists(roles);
				userBatchAdd.setCompanyId(Long.parseLong(record.getCompanyId() + ""));
				userBatchAdd.setUserType(Integer.parseInt(add.getUserScope()));
				userBatchAdd.setUseType(0);
				Gson gson = new Gson();
				//调用用户中心新增用户接口
				log.info("调用用户中心传入接口参数{}", gson.toJson(userBatchAdd));
				UserBatchVO userBatchVO = sysUserManageService.sysUserBatchInsert(userBatchAdd);
				log.info("调用用户中心输出接口参数{}", gson.toJson(userBatchVO));
				String userAccount = "";
				if (userBatchVO == null) {
					resultMap.put("error", "调用户中心接口异常");
					return resultMap;
				}
				Map<Long, String> userMap = userBatchVO.getMap();
				Set<Long> idList = userMap.keySet(); //获取返回的用户账号信息
				List<ServicesAccountRel> accountList = new ArrayList<ServicesAccountRel>();
				Iterator<Map.Entry<Long, String>> entries = userMap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<Long, String> entry = entries.next();
					userAccount = entry.getValue();
					//拼装用户的基本信息
					ServicesAccountRel account = assembleAccountData(entry.getKey(), entry.getValue(), userBatchVO, add.getBusinessType(), record.getCreator());
					accountList.add(account);
				}
				//写入套餐和用户关联关系表
				servicesAccountRelService.batchInsertServiceAccount(accountList);
				//写入用户赠送度币
				if (servicesPrice != null && servicesPrice.getSanduCurrency() != null && servicesPrice.getSanduCurrency() > 0) {
					List<Long> ids = new ArrayList<Long>();
					ids.addAll(idList);
					log.info("赠送度币输如参数:用户id{},赠送度币{}", ids, servicesPrice.getSanduCurrency());
					boolean flag = sysUserService.addGiftAmount(ids, servicesPrice.getSanduCurrency(), "官网试用赠送度币");
					log.info("赠送度币输出参数{}", flag);
				}
				//如果是试用用户,则推送密码到指定手机
				if (trialFlag) {
					String unitStr = "";
					switch (servicesPrice.getPriceUnit()) {
						case PRICE_UNIT_YEAR:
							unitStr = "年";
							break;
						case ServicesPurchaseConstant.PRICE_UNIT_MONTH:
							unitStr = "个月";
							break;
						case ServicesPurchaseConstant.PRICE_UNIT_DAY:
							unitStr = "日";
							break;
						default:
							unitStr = "";
							break;
					}
					String sendMsg = MessageFormat.format(msgTemplate, info.getServicesName(), userAccount, ServicesPurchaseConstant.DEFAULT_PWD, servicesPrice.getDuration(), unitStr, servicesPrice.getSanduCurrency() == null ? 0 : servicesPrice.getSanduCurrency());
					sendMsg = URLEncoder.encode(sendMsg, "UTF-8");
					log.info("发送用户账号信息开始,mobile:{},msg:{}", add.getTelephone(), sendMsg);
					boolean success = sysUserService.sendMessage(add.getTelephone(), sendMsg);
					log.info("发送用户账号信息结束,返回{}", success);
				}
			} else {
				resultMap.put("error", "写入套餐购买记录失败!");
			}
			resultMap.put("success", "操作成功!");
		} catch (Exception e) {
			resultMap.put("error", e.getMessage());
			log.error("套餐购买失败,失败信息{}", e);
		}
		return resultMap;
	}

	/**
	 * 拼装ServicesAccountRel
	 *
	 * @param userId
	 * @param userAccount
	 * @param userBatchVO
	 * @param businessType
	 * @param creator
	 * @return
	 */
	private ServicesAccountRel assembleAccountData(Long userId, String userAccount, UserBatchVO userBatchVO, String businessType, String creator) {
		ServicesAccountRel account = new ServicesAccountRel();
		account.setUserId(userId);
		account.setAccount(userAccount);
		account.setBusinessType(businessType);
		account.setPassword(ServicesPurchaseConstant.DEFAULT_PWD);
		account.setCompanyId(userBatchVO.getCompanyId() + "");
		account.setPurchaseRecordId(userBatchVO.getPurchaseRecordId());
		account.setIsDeleted(ServicesPurchaseConstant.DELETE_FLAG_0);
		account.setGmtCreate(new Date());
		account.setCreator(creator);
		account.setModifier(creator);
		account.setGmtModified(new Date());
		account.setServicesId(userBatchVO.getServiceId());
		account.setStatus(ServicesPurchaseConstant.STATUS_0);
		return account;
	}

	/**
	 * 组装购买记录数据
	 *
	 * @param add
	 * @param servicesPrice
	 * @return
	 */
	private ServicesPurchaseRecord assemblePurchaseData(ServicesPurchaseRecord add, ServicesPrice servicesPrice) {
		ServicesPurchaseRecord record = new ServicesPurchaseRecord();
		record.setCompanyId(add.getCompanyId() + "");
		record.setPurchaseStatus(ServicesPurchaseConstant.PAY_STATUS_0);
		record.setPayType(add.getPayType());
		record.setCreator(add.getCreator());
		record.setModifier(add.getModifier());
		BigDecimal unitPrice = servicesPrice.getPrice();                    //取单价
		BigDecimal payAmount = unitPrice.multiply(add.getPurchaseAmount()); //计算总价
		record.setPurchaseMomey(payAmount);                                //计算总金额
		//类型为试用
		if (ServicesPurchaseConstant.BUSSINESS_TYPE_2.equals(add.getBusinessType())) {
			int companyId = createCompany(add.getTelephone());
			record.setCompanyId(companyId + "");
			record.setTelephone(add.getTelephone());       //手机号码
			record.setPurchaseStatus(ServicesPurchaseConstant.PAY_STATUS_0);//试用的默认为支付成功
			record.setPurchaseMomey(new BigDecimal(0));    //支付金额写入0
			record.setPayType(ServicesPurchaseConstant.PAY_TYPE_2);   //支付类型写其他
			record.setCreator(add.getTelephone());           //创建人
			record.setModifier(add.getTelephone());        //修改人
			record.setPurchaseTime(new Date());
		}
		record.setBusinessType(add.getBusinessType());
		record.setServicesId(add.getServicesId());
		record.setServicesPriceId(add.getServicesPriceId());
		record.setUserid(add.getUserid() + "");
		record.setUserScope(add.getUserScope());
		record.setGmtCreate(new Date());
		record.setGmtModified(new Date());
		record.setIsDeleted(ServicesPurchaseConstant.DELETE_FLAG_0);
		record.setOrderNo(IdGenerator.generateNo());
		record.setUnitPrice(servicesPrice.getPrice());                     //单价
		record.setPriceUnit(servicesPrice.getPriceUnit());                   //时长单元(0-年;1-月;2-日)
		record.setPurchaseSource(add.getPurchaseSource() + "");                 //购买渠道来源
		record.setDuration(servicesPrice.getDuration());                     //获取购买时长
		record.setGiveDuration(servicesPrice.getGiveDuration());             //获取赠送套餐
		record.setFreeRenderDuration(servicesPrice.getFreeRenderDuration()); //免费渲染时长
		record.setSanduCurrency(servicesPrice.getSanduCurrency());           //赠送度币
		record.setPurchaseAmount(add.getPurchaseAmount());          //计算总数量
		return record;
	}

	/**
	 * 组装购买记录数据
	 *
	 * @param add
	 * @param servicesPrice
	 * @return
	 */
	private ServicesPurchaseRecord mobileAssemblePurchaseData(ServicesPurchaseRecord add, ServicesPrice servicesPrice, Long companyId) {
		ServicesPurchaseRecord record = new ServicesPurchaseRecord();
		record.setCompanyId(add.getCompanyId() + "");
		record.setPurchaseStatus(ServicesPurchaseConstant.PAY_STATUS_0);
		record.setPayType(add.getPayType());
		record.setCreator(add.getCreator());
		record.setModifier(add.getModifier());
		BigDecimal unitPrice = servicesPrice.getPrice();                    //取单价
		BigDecimal payAmount = unitPrice.multiply(add.getPurchaseAmount()); //计算总价
		record.setPurchaseMomey(payAmount);                                //计算总金额
		//类型为试用
		if (ServicesPurchaseConstant.BUSSINESS_TYPE_2.equals(add.getBusinessType())) {
			//int companyId = createCompany(add.getTelephone());
			record.setCompanyId(companyId + "");
			record.setTelephone(add.getTelephone());       //手机号码
			record.setPurchaseStatus(ServicesPurchaseConstant.PAY_STATUS_0);//试用的默认为支付成功
			record.setPurchaseMomey(new BigDecimal(0));    //支付金额写入0
			record.setPayType(ServicesPurchaseConstant.PAY_TYPE_2);   //支付类型写其他
			record.setCreator(add.getTelephone());           //创建人
			record.setModifier(add.getTelephone());        //修改人
			record.setPurchaseTime(new Date());
		}
		record.setBusinessType(add.getBusinessType());
		record.setServicesId(add.getServicesId());
		record.setServicesPriceId(add.getServicesPriceId());
		record.setUserid(add.getUserid() + "");
		record.setUserScope(add.getUserScope());
		record.setGmtCreate(new Date());
		record.setGmtModified(new Date());
		record.setIsDeleted(ServicesPurchaseConstant.DELETE_FLAG_0);
		record.setOrderNo(IdGenerator.generateNo());
		record.setUnitPrice(servicesPrice.getPrice());                     //单价
		record.setPriceUnit(servicesPrice.getPriceUnit());                   //时长单元(0-年;1-月;2-日)
		record.setPurchaseSource(add.getPurchaseSource() + "");                 //购买渠道来源
		record.setDuration(servicesPrice.getDuration());                     //获取购买时长
		record.setGiveDuration(servicesPrice.getGiveDuration());             //获取赠送套餐
		record.setFreeRenderDuration(servicesPrice.getFreeRenderDuration()); //免费渲染时长
		record.setSanduCurrency(servicesPrice.getSanduCurrency());           //赠送度币
		record.setPurchaseAmount(add.getPurchaseAmount());          //计算总数量
		record.setPayType("2");
		return record;
	}

	/**
	 * 创建设计师虚拟公司
	 *
	 * @param telephone
	 * @return 企业id
	 * @date
	 */
	public int createCompany(String telephone) {
		try {
			String companyCode = createCompanyCode();
			BaseCompany baseCompany = new BaseCompany();
			baseCompany.setCompanyCode(companyCode);
			baseCompany.setCompanyName("SD".concat(genetorCompanyName()));
			baseCompany.setCreator(telephone);
			baseCompany.setPhone(telephone);
			baseCompany.setGmtCreate(new Date());
			baseCompany.setModifier(telephone);
			baseCompany.setGmtModified(new Date());
			baseCompany.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			baseCompany.setIsDeleted(0);
			baseCompany.setAtt1("1");//是否虚拟公司;0-否;1-是
			baseCompany.setBusinessType(BusinessTypeConstant.BUSINESSTYPE_DESIGNER);
			int id = baseCompanyService.insertSelective(baseCompany);
			return id;
		} catch (Exception e) {
			log.error("创建公司账号失败,失败信息:{}", e);
		}
		return 0;
	}

	/**
	 * 虚拟公司规则：平台（字母表示）+6位随机数；如ST31y0M9 ST=通用版PC端    SG=移动B端   SD=三度官网
	 *
	 * @return
	 */
	public static String genetorCompanyName() {
		String randomcode = "";
		// 用字符数组的方式随机
		String model = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] m = model.toCharArray();
		for (int j = 0; j < 6; j++) {
			char c = m[(int) (Math.random() * 36)];
			// 保证六位随机数之间没有重复的
			if (randomcode.contains(String.valueOf(c))) {
				j--;
				continue;
			}
			randomcode = randomcode + c;
		}
		return randomcode;
	}

	/**
	 * 创建设计师企业编码
	 * 生成企业code
	 * 生成规则：C+7位数序列
	 *
	 * @return 企业编码
	 * @date
	 */
	private String createCompanyCode() throws Exception {
		List<Integer> businessTypeList = null;
		List<Integer> businessTypeNotList = null;
		StringBuffer companyCodePrefix = new StringBuffer();//前缀
		StringBuffer commanyCodeSuffix = new StringBuffer();//后缀
		int count = 0;
		businessTypeNotList = new ArrayList<Integer>();
		businessTypeNotList.add(BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);
		companyCodePrefix.append("C");
		count = 7;
		//获取当前最大序列
		String companyCodeMax = "";
		companyCodeMax = baseCompanyService.getMaxCompanyCode(companyCodePrefix.toString() + "%", businessTypeList, businessTypeNotList);  /**获取当前前缀的最大序列号**/
		//序列
		Integer commanyCode = 0;
		//获取最大序列
		if (!StringUtils.isEmpty(companyCodeMax)) {
			commanyCode = Integer.parseInt(companyCodeMax.substring(companyCodePrefix.length(), companyCodeMax.length()));
		}
		//增加1
		commanyCode++;
		//赋值后缀
		commanyCodeSuffix.append(commanyCode);
		//循环补齐
		int k = commanyCodeSuffix.length();
		for (int i = 0; i < count - k; i++) {
			commanyCodeSuffix.insert(0, "0");
		}
		return companyCodePrefix + "" + commanyCodeSuffix;
	}

	/**
	 * 获取用户未过期的角色列表信息
	 *
	 * @param account 用户账号, roleIds：用户角色
	 * @return ServicesRoleInfoBO 对象
	 * @date
	 */
	@Override
	public ServicesRoleInfoBO getValidRoles(String account, Long userId, Set<Long> roleIds) {
		log.info("用户账号：account:{},userId :{},用户角色:roleIds:{}", account, userId, roleIds);
		ServicesRoleInfoBO result = new ServicesRoleInfoBO();
		if (StringUtils.isEmpty(account) || userId == null) {
			result.setRoleIds(roleIds);
			log.info("result :{}", result);
			return result;
		}
		//判断账号表里面是否存在,若不存在,则直接返回roleIds
		ServicesAccountRel accountByUserId = servicesAccountRelService.getAccountByUserId(userId.intValue());
		if (accountByUserId == null) {
			result.setRoleIds(roleIds);
			result.setOldServiceFlag(true);
			log.info("result :{}", result);
			return result;
		}
		Optional<ServicesAccountRel> first = Optional.of(accountByUserId);

		ServicesBaseInfo baseInfo = servicesBaseInfoService.getById(first.get().getServicesId());
		Integer serviceType = SERVICES_TYPE_FORMAL;
		if (SERVICES_TYPE_TRY.equals(baseInfo.getServicesType())) {
			serviceType = SERVICES_TYPE_TRY;
		}
		result.setServiceType(serviceType);
		result.setRoleIds(roleIds);
		result.setServiceName(baseInfo.getServicesName());
		ServicesAccountRel servicesAccountRel = first.get();
		//激活
		if (Integer.parseInt(servicesAccountRel.getStatus()) == 0) {
			this.activeAccount(account, servicesAccountRel);
		}
		//处理科创/抢工长 套餐用户逻辑
		if (Arrays.asList(kqCompanyIds.split(Punctuation.COMMA)).contains(servicesAccountRel.getCompanyId())) {
			BaseCompany company = companyService.getCompanyById(Long.valueOf(servicesAccountRel.getCompanyId()));
			result.setCompanyName(company.getCompanyName());
		}
		if (new Date().compareTo(servicesAccountRel.getEffectiveEnd()) <= 0) {
			//未到期
			//获取套餐对应的角色id集合
			List<ServicesRoleRel> roleList = servicesRoleRelService.getByServiceId(servicesAccountRel.getServicesId());
			Set<Long> roles = roleList.stream().map(roleId -> Long.parseLong(roleId.getRoleId().toString())).collect(Collectors.toSet());
			result.setRoleIds(roles);
			//套餐临期
			long mills = DateUtils.addDays(new Date(), ServicesPurchaseConstant.TIPSDAY).getTime() - servicesAccountRel.getEffectiveEnd().getTime();
			if (mills > 0) {
				result.setTipsFlag(true);
				mills = servicesAccountRel.getEffectiveEnd().getTime() - System.currentTimeMillis();
				result.setRemainingDays((int) Math.ceil((double) mills / millionSecondsOfDay));
			}
		} else {
			//到期
			result.setMaturityFlag(true);
		}
		log.info("result :{}", result);
		return result;
	}

	private void activeAccount(String account, ServicesAccountRel servicesAccountRel) {
		Long purchaseRecordId = servicesAccountRel.getPurchaseRecordId();
		ServicesPurchaseRecord record = servicesPurchaseRecordService.selectByPrimaryKey(purchaseRecordId);
		if (record == null) {
			return;
		}
		Integer duration = record.getDuration();            //获取套餐购买时长
		Integer giveDuration = record.getGiveDuration();    //获取赠送时长
		ServicesAccountRel temRel = new ServicesAccountRel();
		temRel.setStatus(ServicesPurchaseConstant.STATUS_1);
		temRel.setId(servicesAccountRel.getId());
		temRel.setModifier(account);
		temRel.setEffectiveBegin(new Date());
		Date endDate;
		switch (record.getPriceUnit()) {
			case PRICE_UNIT_YEAR://年
				endDate = DateUtils.addYears(new Date(), duration);
				temRel.setEffectiveEnd(endDate);
				break;
			case ServicesPurchaseConstant.PRICE_UNIT_MONTH://月
				endDate = DateUtils.addMonths(new Date(), duration);
				temRel.setEffectiveEnd(endDate);
				break;
			case ServicesPurchaseConstant.PRICE_UNIT_DAY://日
				endDate = DateUtils.addDays(new Date(), duration);
				temRel.setEffectiveEnd(endDate);
				break;
			default:
				break;
		}
		temRel.setEffectiveEnd(DateUtils.addDays(temRel.getEffectiveEnd(), giveDuration == null ? 0 : giveDuration)); //设置到期时间
		temRel.setGmtModified(new Date());
		servicesAccountRel.setEffectiveBegin(temRel.getEffectiveBegin());
		servicesAccountRel.setEffectiveEnd(temRel.getEffectiveEnd());
		servicesAccountRelService.updateByPrimaryKeySelective(temRel);
		//同步用户到期时间
		sysUserManageService.dealUserFailureTimeAndValidTime(servicesAccountRel.getUserId(), temRel.getEffectiveEnd());
	}

	/**
	 * 校验手机号码是否参与试用套餐
	 *
	 * @param telephone
	 * @return count 不存在则返回0;存在则返回大于0
	 * @date
	 */
	@Override
	public int checkTelephoneExists(String telephone) {
		int count = 0;
		count = servicesPurchaseRecordService.checkTelephoneExists(telephone);
		log.info("校验手机号:{},返回count:{}", telephone, count);
		if (count <= 0) {
			count = sysUserService.getUserBuMobile(telephone) == null ? 0 : sysUserService.getUserBuMobile(telephone).size();
			log.info("sys校验手机号:{},返回count:{}", telephone, count);
		}
		return count;
	}

	/**
	 * 获取官网套餐信息
	 *
	 * @param channelId (销售渠道)
	 * @return OfficalServicesListVO
	 * @date
	 */
	@Override
	public OfficalServicesListVO getOfficalServicesInfo(Integer channelId) {
		//获取试用套餐、指定渠道的基本信息
		List<ServicesBaseInfo> infoLists = servicesBaseInfoService.getServicesByType(SERVICES_TYPE_TRY + "", channelId + "");
		OfficalServicesListVO officalServicesListVO = new OfficalServicesListVO();
		if (infoLists != null && infoLists.size() > 0) {
			ServicesBaseInfo servicesBaseInfo = infoLists.get(0);
			officalServicesListVO.setId(servicesBaseInfo.getId());
			officalServicesListVO.setServicesName(servicesBaseInfo.getServicesName());
			List<ServicesPrice> servicesPriceList = servicesPriceService.findServicesPriceList(servicesBaseInfo.getId());
			if (servicesPriceList != null && servicesPriceList.size() > 0) {
				ServicesPrice servicesPrice = servicesPriceList.get(0);
				officalServicesListVO.setPrice(servicesPrice.getPrice());
				officalServicesListVO.setSanduCurrency(servicesPrice.getSanduCurrency());
				officalServicesListVO.setDuration(servicesPrice.getDuration());
			}
		}
		return officalServicesListVO;
	}

	@Override
	public ServicesPurchaseRecord initServiceOrder(ServiceRecordInitBO bo) {
		log.info("套餐购买入参，bo:{}", bo);
		if (BUSSINESS_TYPE_0.equals(bo.getBusinessType())) {
			//购买套餐
			return buyServicesFirst(bo);
		} else if ((BUSSINESS_TYPE_1.equals(bo.getBusinessType())) || BUSSINESS_TYPE_4.equals(bo.getBusinessType())) {
			//升级续费
			ServicesAccountRel account = servicesAccountRelService.getAccountByUserId(bo.getAccountUserId());
			if (account == null) {
				throw new RuntimeException("此用户未开通过任何套餐...");
			}
			if (account.getEffectiveBegin() == null) {
				throw new RuntimeException("此账号尚未激活,请先激活再进行续费/升级操作...");
			}
			//获取旧订单
			ServicesPurchaseRecord oldRecord = servicesPurchaseRecordService.selectByPrimaryKey(account.getPurchaseRecordId());
			//获取套餐具体价格信息
			ServicesPrice priceInfo = servicesPriceService.findPriceById(bo.getPriceId());
			//获取套餐基础信息
			ServicesBaseInfo servicesInfo = servicesBaseInfoService.getById(priceInfo.getServicesId());
			//补差价生成新订单
			ServicesPurchaseRecord record = this.initRecord(bo, servicesInfo, priceInfo, oldRecord, account);
			servicesPurchaseRecordService.addServicesPurchaseRecord(record);

			if (record.getFreeRenderDuration() > 0) {
				log.info("start sync user FreeRenderDuration: userId:{},duration:{}", account.getUserId(), record.getFreeRenderDuration());
				payModelGroupRefService.updatePackageGiveRender(account.getUserId(), record.getFreeRenderDuration());
				log.info("end sync user FreeRenderDuration");
			}
			return record;
		}
		return null;
	}

	private ServicesPurchaseRecord buyServicesFirst(ServiceRecordInitBO bo) {
		ServicesPrice servicesPrice = servicesPriceService.findPriceById(bo.getPriceId());
		if (Objects.isNull(servicesPrice)) {
			throw new RuntimeException("获取套餐价格信息失败");
		}
		ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.getById(servicesPrice.getServicesId());
		if (Objects.isNull(servicesBaseInfo)) {
			throw new RuntimeException("获取套餐基本信息失败");
		}
		//三度升级/ 续费套餐不处理度币和渲染时长
		if (Objects.equals(bo.getPurchaseSource(), PURCHASE_SOURCE_2)) {
			servicesPrice.setSanduCurrency(0);
			servicesPrice.setFreeRenderDuration(0);
		}
		SysUser sysUser = sysUserService.get(bo.getUserId());
		log.info("获取用户信息:{}", sysUser);
		ServicesPurchaseRecord record = new ServicesPurchaseRecord();
		record.setUserid(String.valueOf(bo.getAccountUserId()));
		record.setPayType(bo.getPayType());
		record.setCompanyId(bo.getCompanyId().toString());
		Optional.of(sysUser).ifPresent(user -> {
			record.setCreator(user.getNickName());
			record.setModifier(user.getNickName());
		});
		record.setBusinessType(bo.getBusinessType());
		record.setServicesPriceId(bo.getPriceId().intValue());
		record.setServicesId(servicesPrice.getServicesId());
		record.setUserScope(servicesBaseInfo.getUserScope());
		record.setPurchaseSource(bo.getPurchaseSource() + "");                 //购买渠道来源
		record.setPurchaseAmount(BigDecimal.valueOf(bo.getPurchaseAmount()));          //计算总数量
		ServicesPurchaseRecord recordInsert = this.assemblePurchaseData(record, servicesPrice);
		servicesPurchaseRecordService.addServicesPurchaseRecord(recordInsert);
		return recordInsert;
	}


	@Override
	public boolean syncServiceAccount(String orderNum, String recordStatus) {
		if (Strings.isNullOrEmpty(orderNum) || Strings.isNullOrEmpty(recordStatus)) {
			log.debug("同步用户套餐,参数异常...");
			return false;
		}

		//改变套餐记录支付状态
		ServicesPurchaseRecord record = servicesPurchaseRecordService.getRecordByOrderNum(orderNum);
		if (record == null) {
			log.debug("获取购买记录失败,订单号:", orderNum);
			return false;
		}
		record.setPurchaseStatus(recordStatus);
		record.setPurchaseTime(new Date());
		servicesPurchaseRecordService.updateByPrimaryKeySelective(record);

		ServicesPrice priceInfo = servicesPriceService.findPriceById(record.getServicesPriceId().longValue());
		//三度购买,不处理度币/渲染时长
		if (Objects.equals(record.getPurchaseSource(), PURCHASE_SOURCE_2)) {
			priceInfo.setSanduCurrency(0);
			priceInfo.setFreeRenderDuration(0);
		}

		//生成购买账号
		ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.getById(record.getServicesId());
		if (BUSSINESS_TYPE_0.equals(record.getBusinessType())) {
			//生成账号
			insertUserAccount(record, servicesBaseInfo, priceInfo);
		}

		//套餐升级续费
		if (BUSSINESS_TYPE_1.equals(record.getBusinessType()) || BUSSINESS_TYPE_4.equals(record.getBusinessType())) {
			ServicesAccountRel oldAccount = servicesAccountRelService.getAccountByUserId(Integer.valueOf(record.getUserid()));
			if (Objects.isNull(oldAccount)) {
				log.debug("套餐升级续费获取账户异常");
				return false;
			}
			//更新用户套餐
			ServicesAccountRel curAccount = updateUserAccount(orderNum, record, oldAccount, priceInfo);
			log.info("同步套餐到期时间到用户表-----Start : userId:{},EffectiveEnd:{}", oldAccount.getUserId(), curAccount.getEffectiveEnd());
			boolean result = sysUserManageService.dealUserFailureTimeAndValidTime(oldAccount.getUserId(), curAccount.getEffectiveEnd());
			log.info("同步套餐到期时间到用户表-----END,reslut:{}", result);
		}
		return true;
	}

	private ServicesAccountRel updateUserAccount(String orderNum, ServicesPurchaseRecord record, ServicesAccountRel oldAccount, ServicesPrice priceInfo) {
		log.info("套餐续费/升级,record编号:{}", orderNum);
		//软删除旧的账号记录
		oldAccount.setIsDeleted(ServicesPurchaseConstant.DELETE_FLAG_1);
		servicesAccountRelService.updateByPrimaryKeySelective(oldAccount);
		//修改账号套餐信息
		ServicesAccountRel curAccount = insertUserAccountForAlter(record, oldAccount);
		if (priceInfo != null && priceInfo.getSanduCurrency() != null && priceInfo.getSanduCurrency() > 0) {
			this.giveSanduCurrency(priceInfo, Collections.singletonList(curAccount.getUserId()));
		}
		//短信通知
		if (!Objects.equals(record.getPurchaseSource(), PURCHASE_SOURCE_2)) {
			//三度后台修改套餐
			sendMassage(oldAccount, curAccount);
		}
		return curAccount;
	}

	/**
	 * 逻辑删除旧用户,生成新用户
	 *
	 * @param record     新套餐购买记录
	 * @param oldAccount 旧套餐用户
	 * @return
	 */
	private ServicesAccountRel insertUserAccountForAlter(ServicesPurchaseRecord record, ServicesAccountRel oldAccount) {
		ServicesAccountRel curAccount = new ServicesAccountRel();
		BeanUtils.copyProperties(oldAccount, curAccount);
		curAccount.setId(null);
		curAccount.setPurchaseRecordId(record.getId());
		curAccount.setServicesId(record.getServicesId());
		if (!record.getServicesId().equals(oldAccount.getServicesId())) {
			//升级套餐,更新权限
			log.debug("升级套餐:之前套餐id:{},当前套餐id:{}", oldAccount.getServicesId(), record.getServicesId());
			List<Integer> curRoles = servicesRoleRelService.getByServiceId(record.getServicesId())
					.stream().map(roleId -> Integer.parseInt(roleId.getRoleId().toString())).collect(Collectors.toList());
			List<Integer> oldRoles = servicesRoleRelService.getByServiceId(oldAccount.getServicesId())
					.stream().map(roleId -> Integer.parseInt(roleId.getRoleId().toString())).collect(Collectors.toList());
			log.debug("升级套餐更新权限Start:升级用户ID:{},之前套餐权限:{},当前套餐权限:{}", oldAccount.getUserId(), oldRoles, curRoles);
			sysUserService.updateUserRoles(oldAccount.getUserId().intValue(), oldRoles, curRoles);
			log.debug(" 更新权限End");
		}
		curAccount.setModifier(record.getCreator());
		//更新作用时间,不然再次更换套餐有问题
		Date dateTmp = new Date();
		if (BUSSINESS_TYPE_1.equals(record.getBusinessType())) {
			//续费
			dateTmp = oldAccount.getEffectiveEnd();
		} else if (BUSSINESS_TYPE_4.equals(record.getBusinessType())) {
			//升级
			curAccount.setEffectiveBegin(dateTmp);
		}
		//设置到期时间
		Date endDate;
		switch (record.getPriceUnit().trim()) {
			case PRICE_UNIT_YEAR:
				endDate = DateUtils.addYears(dateTmp, record.getDuration());
				curAccount.setEffectiveEnd(endDate);
				break;
			case ServicesPurchaseConstant.PRICE_UNIT_MONTH:
				endDate = DateUtils.addMonths(dateTmp, record.getDuration());
				curAccount.setEffectiveEnd(endDate);
				break;
			case ServicesPurchaseConstant.PRICE_UNIT_DAY:
				endDate = DateUtils.addDays(dateTmp, record.getDuration());
				curAccount.setEffectiveEnd(endDate);
				break;
			default:
				break;
		}
		curAccount.setEffectiveEnd(DateUtils.addDays(curAccount.getEffectiveEnd(), record.getGiveDuration()));
		curAccount.setBusinessType(record.getBusinessType());
		curAccount.setCompanyId(record.getCompanyId());
		curAccount.setGmtCreate(new Date());
		curAccount.setGmtModified(new Date());
		curAccount.setIsDeleted(ServicesPurchaseConstant.DELETE_FLAG_0);
		//插入新的账号记录
		servicesAccountRelService.batchInsertServiceAccount(Collections.singletonList(curAccount));

		if (!Objects.equals(record.getPurchaseSource(), PURCHASE_SOURCE_2) && BUSSINESS_TYPE_4.equals(record.getBusinessType())) {
			//三度后台 升级套餐
			initBuyServiceLog(oldAccount, curAccount, String.format("账户%s 由 %s(套餐ID) 升级 至 %s(套餐ID)", curAccount.getAccount(), oldAccount.getServicesId(), curAccount.getServicesId()));
		}
		return curAccount;
	}

	/**
	 * 购买套餐生成账号
	 *
	 * @param record
	 * @param servicesBaseInfo
	 */
	private void insertUserAccount(ServicesPurchaseRecord record, ServicesBaseInfo servicesBaseInfo, ServicesPrice priceInfo) {
		//生成账号
		UserBatchAdd userBatchAdd = new UserBatchAdd();
		userBatchAdd.setServiceId(record.getServicesId());
		userBatchAdd.setAccount(record.getPurchaseAmount().intValue());
		userBatchAdd.setPurchaseRecordId(record.getId());
		List<Integer> roles = servicesRoleRelService.getByServiceId(record.getServicesId())
				.stream().map(roleId -> Integer.parseInt(roleId.getRoleId().toString())).collect(Collectors.toList());
		userBatchAdd.setRoleLists(roles);
		userBatchAdd.setCompanyId(Long.parseLong(record.getCompanyId() + ""));
		userBatchAdd.setUserType(Integer.parseInt(servicesBaseInfo.getUserScope()));
		if (servicesBaseInfo.getServicesType() != null) {
			Integer useType = null;
			if (servicesBaseInfo.getServicesType() == 1) {
				useType = 0;
			} else if (servicesBaseInfo.getServicesType() == 0) {
				useType = 1;
			}
			userBatchAdd.setUseType(useType);
		}
		if (!StringUtils.isEmpty(record.getPurchaseSource())) {
			userBatchAdd.setUserSource(Integer.parseInt(record.getPurchaseSource()));
		}
		Gson gson = new Gson();
		//调用用户中心新增用户接口
		log.info("调用用户中心传入接口参数{}", gson.toJson(userBatchAdd));
		UserBatchVO userBatchVO = sysUserManageService.sysUserBatchInsert(userBatchAdd);
		log.info("调用用户中心输出接口参数{}", gson.toJson(userBatchVO));
		List<ServicesAccountRel> accountRelList = userBatchVO.getMap().entrySet()
				.stream()
				.map(entrySet ->
						this.assembleAccountData(entrySet.getKey(), entrySet.getValue(), userBatchVO, servicesBaseInfo.getUserScope(), record.getCreator())
				).collect(Collectors.toList());
		//赠送度币

		//内部购买不处理度币
		if (priceInfo != null && priceInfo.getSanduCurrency() != null && priceInfo.getSanduCurrency() > 0) {
			this.giveSanduCurrency(priceInfo, new ArrayList<>(userBatchVO.getMap().keySet()));
		}
		//写入套餐和用户关联关系表
//		log.info("新增套餐账户:{}", accountRelList);
		servicesAccountRelService.batchInsertServiceAccount(accountRelList);
		log.info("新增套餐账户：{}",accountRelList);
		//save log
		for (ServicesAccountRel account : accountRelList) {
			initBuyServiceLog(null, account, String.format("账户%s 新增套餐, 有效期为 :%s　－　 %s", account.getAccount(), account.getEffectiveBegin(), account.getEffectiveEnd()));
		}
	}

	private void giveSanduCurrency(ServicesPrice priceInfo, List<Long> ids) {
		log.info("赠送度币输如参数:用户id{},赠送度币{}", ids, priceInfo.getSanduCurrency());
		boolean flag = sysUserService.addGiftAmount(ids, priceInfo.getSanduCurrency(), "购买套餐赠送度币");
		log.info("赠送度币输出参数{}", flag);
	}

	private void sendMassage(ServicesAccountRel oldAccount, ServicesAccountRel curAccount) {
		//根据账户获取套餐详情
		Map<Long, List<ServicesBaseInfo>> id2ServiceBaseInfo = Stream.of(oldAccount.getServicesId(), curAccount.getServicesId()).distinct()
				.map(servicesBaseInfoService::getById)
				.collect(Collectors.groupingBy(ServicesBaseInfo::getId, Collectors.toList()));

		SysUser sysUser = sysUserService.get(oldAccount.getUserId().intValue());
		if (org.apache.commons.lang3.StringUtils.isBlank(sysUser.getMobile())) {
			log.info("获取用户手机号失败,用户:{},account UserId:{}", sysUser, oldAccount.getUserId());
			return;
		}
		String msg;
		String oldServiceName = id2ServiceBaseInfo.get(oldAccount.getServicesId()).get(0).getServicesName();
		String curServiceName = id2ServiceBaseInfo.get(curAccount.getServicesId()).get(0).getServicesName();
		String endDateString = new SimpleDateFormat("yyyy-MM-dd").format(curAccount.getEffectiveEnd().getTime());
		if (id2ServiceBaseInfo.get(oldAccount.getServicesId()).get(0).getServicesType().equals(1)) {
			//试用套餐升级
			msg = MessageFormat.format(msgRenewalsTryTemplate, curServiceName, endDateString);
		} else if (oldAccount.getServicesId().equals(curAccount.getServicesId())) {
			//续费
			msg = MessageFormat.format(msgRenewalsTemplate, oldServiceName, endDateString);
		} else {
			//升级套餐
			msg = MessageFormat.format(msgRenewalsNewTemplate,
					oldServiceName,
					curServiceName,
					endDateString);
		}
//        try {
//            msg = URLEncoder.encode(msg, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            log.error("format message template error");
//            e.printStackTrace();
//        }
		//短信通知
		log.info("短信提示Start===>:用户手机号:{},msg:{}", sysUser.getMobile(), msg);
		sysUserService.sendMessage(sysUser.getMobile(), msg);
		log.info("短信提示End");
	}

	private ServicesPurchaseRecord initRecord(ServiceRecordInitBO bo, ServicesBaseInfo servicesInfo,
											  ServicesPrice priceInfo, ServicesPurchaseRecord oldRecord,
											  ServicesAccountRel account) {
		if (Objects.isNull(servicesInfo) || Objects.isNull(priceInfo) || Objects.isNull(oldRecord)) {
			log.error("获取套餐信息失败,serviceInfo:{},priceInfo:{},oldRecord:{}", servicesInfo, priceInfo, oldRecord);
			throw new RuntimeException("获取套餐信息失败...");
		}
		SysUser sysUser = sysUserService.get(bo.getUserId());
		if (Objects.isNull(sysUser)) {
			throw new RuntimeException("获取用户信息异常...");
		}

		ServicesPurchaseRecord record = new ServicesPurchaseRecord();
		record.setCompanyId(bo.getCompanyId() + "");
		record.setOrderNo(IdGenerator.generateNo());
		record.setServicesId(servicesInfo.getId());
		record.setServicesPriceId(priceInfo.getId().intValue());
		record.setUserScope(servicesInfo.getUserScope());
		record.setPurchaseSource(bo.getPurchaseSource());
		record.setBusinessType(bo.getBusinessType());
		record.setUnitPrice(priceInfo.getPrice());
		record.setPriceUnit(priceInfo.getPriceUnit());
		record.setPurchaseAmount(BigDecimal.valueOf(bo.getPurchaseAmount() == null ? 1 : bo.getPurchaseAmount()));
		record.setDuration(priceInfo.getDuration());
		record.setGiveDuration(priceInfo.getGiveDuration());
		record.setFreeRenderDuration(priceInfo.getFreeRenderDuration());
		record.setSanduCurrency(priceInfo.getSanduCurrency());
		record.setPurchaseMomey(priceInfo.getPrice().multiply(record.getPurchaseAmount()));
		record.setPayType(bo.getPayType());
		record.setPurchaseStatus(ServicesPurchaseConstant.PAY_STATUS_0);
		record.setGmtCreate(new Date());
		record.setGmtModified(new Date());
		record.setCreator(sysUser.getNickName());
		record.setModifier(sysUser.getNickName());
		record.setUserid(String.valueOf(bo.getAccountUserId()));
		record.setIsDeleted(ServicesPurchaseConstant.DELETE_FLAG_0);

		//三度升级/ 续费套餐不处理度币和渲染时长
		if (Objects.equals(bo.getPurchaseSource(), PURCHASE_SOURCE_2) && BUSSINESS_TYPE_4.equals(bo.getBusinessType())) {
			record.setSanduCurrency(0);
			record.setFreeRenderDuration(0);
			record.setRemark("内部升级 套餐");
			return record;
		}

		if (BUSSINESS_TYPE_1.equals(bo.getBusinessType())) {
			return record;
		}
		log.debug("pre差价----{}", record.getPurchaseMomey());
		//取差价
		BigDecimal reimburse = this.reimburseAccount(account, oldRecord).setScale(2, ROUND_DOWN);
		//设置订单最终价格
		log.debug("差价----{}", reimburse);
		record.setPurchaseMomey(record.getPurchaseMomey().subtract(reimburse).setScale(2, ROUND_DOWN));
		if (record.getPurchaseMomey().compareTo(BigDecimal.ZERO) <= 0) {
			record.setPurchaseMomey(BigDecimal.valueOf(0));
		}
		log.debug("record--------: {}", record.getPurchaseMomey());
		return record;

	}

	private BigDecimal reimburseAccount(ServicesAccountRel account, ServicesPurchaseRecord oldRecord) {
		Date now = new Date();
		//到期判断
		if (now.compareTo(account.getEffectiveEnd()) <= 0) {
			Long unUserMillis = DateUtil.getMills(now, account.getEffectiveEnd());
			Long allMillis = DateUtil.getMills(account.getEffectiveBegin(), account.getEffectiveEnd());
			//计算属于时长比
			double m1 = unUserMillis.doubleValue() / allMillis.doubleValue();
			return oldRecord.getPurchaseMomey().divide(oldRecord.getPurchaseAmount(), 2).multiply(BigDecimal.valueOf(m1));
		}
		return BigDecimal.valueOf(0);
	}

	/**
	 * 获取指定用户的免费渲染时长(月)
	 *
	 * @param userId 用户id
	 * @return 免费渲染月数
	 * @date
	 */
	@Override
	public Map<String, Object> getFreeRenderDurationByUserid(Long userId) {
		ServicesAccountRel accountRel = servicesAccountRelService.getAccountByUserId(userId.intValue());
		ServicesPurchaseRecord record = servicesPurchaseRecordService.selectByPrimaryKey(accountRel == null ? null : accountRel.getPurchaseRecordId());
		ServicesPrice servicesPrice = servicesPriceService.findPriceById(record == null ? null : record.getServicesPriceId().longValue());
		if (servicesPrice == null) {
			return Collections.EMPTY_MAP;
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("freeRenderDuration", servicesPrice.getFreeRenderDuration() == null ? 0 : servicesPrice.getFreeRenderDuration());
		resultMap.put("sanduCurrency", servicesPrice.getSanduCurrency() == null ? 0 : servicesPrice.getSanduCurrency());
		return resultMap;
	}

	@Override
	public boolean updateRedisStatus(String payTradeNo, String recordStatus) {
		log.info("payTradeNo:{},recordStatus:{}", payTradeNo, recordStatus);
		boolean flag = redisService.set(payTradeNo, recordStatus, 30L); //30s以后超时
		log.info("更新订单redis:{},flag{},", payTradeNo, flag);
		return flag;
	}

	@Override
	public String getTradeStatus(String payTradeNo) {
		log.info("payTradeNo:{}获取订单状态:", payTradeNo);
		String status = "";
		status = (String) redisService.get(payTradeNo);
		if (StringUtils.isEmpty(status)) {
			status = "0";
		}
		log.info("payTradeNo:{}获取订单状态返回：{}", payTradeNo, status);
		return status;
	}

	@Override
	public Object mobile2bRegisterUsePackage(SysUser sysUser) {
		ServicesPurchaseRecord add = new ServicesPurchaseRecord();
		Map<String, Object> returnMap = new HashMap<>();
		try {
			ServicesBaseInfo info = null;
			ServicesPrice servicesPrice = null;
			List<ServicesRoleRel> roleList = null;

			//获取试用套餐、指定渠道的基本信息
			List<ServicesBaseInfo> infoLists = servicesBaseInfoService.getServicesListByUserScopeAndTye(SERVICES_TYPE_TRY + "", ServicesPurchaseConstant.PURCHASE_SOURCE_6, sysUser.getUserType() + "");
			if (infoLists == null || infoLists.size() == 0) {
				throw new RuntimeException("没有配置试用套餐信息!");
			}
			info = infoLists.get(0);
			add.setServicesId(info.getId());
			List<ServicesPrice> servicesPriceList = servicesPriceService.findServicesPriceList(info.getId());
			if (servicesPriceList == null || servicesPriceList.size() == 0) {
				log.error("没有配置试用套餐价格!");
				return null;
			}
			servicesPrice = servicesPriceList.get(0);
			add.setServicesPriceId(servicesPrice.getId().intValue());
			roleList = servicesRoleRelService.getByServiceId(info.getId());
			if (roleList == null || roleList.size() == 0) {
				throw new RuntimeException("没有配置用套餐功能!!");
			}
			returnMap.put("roleList", roleList);
			add.setPurchaseSource(ServicesPurchaseConstant.PURCHASE_SOURCE_6);   //购买来源为官网
			add.setBusinessType(ServicesPurchaseConstant.BUSSINESS_TYPE_2);     //业务类型为试用
			add.setUserScope(sysUser.getUserType() + "");         //试用用户类型为设计师
			add.setPurchaseAmount(new BigDecimal(1));
			add.setTelephone(sysUser.getMobile());
			//组装数据
			ServicesPurchaseRecord record = mobileAssemblePurchaseData(add, servicesPrice, sysUser.getCompanyId());
			int recordId = servicesPurchaseRecordService.addServicesPurchaseRecord(record);
			if (recordId > 0) {
				UserBatchVO userBatchVO = new UserBatchVO();
				userBatchVO.setCompanyId(sysUser.getCompanyId());
				userBatchVO.setServiceId(add.getServicesId());
				userBatchVO.setPurchaseRecordId(record.getId());
				ServicesAccountRel account = assembleAccountData(sysUser.getId(), sysUser.getNickName(), userBatchVO, add.getBusinessType(), record.getCreator());
				//写入套餐和用户关联关系表
				List<ServicesAccountRel> accountList = new ArrayList<>();
				accountList.add(account);
				servicesAccountRelService.batchInsertServiceAccount(accountList);
				//写入用户赠送度币
				if (servicesPrice != null && servicesPrice.getSanduCurrency() != null && servicesPrice.getSanduCurrency() > 0) {
					returnMap.put("sanduCurrency", servicesPrice.getSanduCurrency());
				}
			} else {
				throw new RuntimeException("写入套餐记录失败!!");
			}
			return returnMap;
		} catch (Exception e) {
			log.error("同城联盟注册获取套餐信息异常,失败信息{}", e);
		}
		return returnMap;
	}

	@Override
	public List<ServicesRoleRel> handlerCompanyUserPackageInfo(Long servicesId, SysUser sysUser,Integer priceId) {
		ServicesPurchaseRecord add = new ServicesPurchaseRecord();
		try {
			ServicesPrice servicesPrice = null;
			List<ServicesRoleRel> roleList = null;

			//获取套餐信息
			ServicesBaseInfo info = servicesBaseInfoService.getById(servicesId);
			if (Objects.isNull(info)) {
				throw new RuntimeException("没有配置试用套餐信息!");
			}
			add.setServicesId(info.getId());
			if (Objects.nonNull(priceId)){ ;
				servicesPrice = servicesPriceService.findPriceById(Long.parseLong(priceId+""));
				if (Objects.isNull(servicesPrice)){
					log.error("没有配置试用套餐价格!");
					return null;
				}
			}else{
				List<ServicesPrice> servicesPriceList = servicesPriceService.findServicesPriceList(info.getId());
				if (servicesPriceList == null || servicesPriceList.size() == 0) {
					log.error("没有配置试用套餐价格!");
					return null;
				}
				servicesPrice = servicesPriceList.get(0);
			}
			add.setServicesPriceId(servicesPrice.getId().intValue());
			roleList = servicesRoleRelService.getByServiceId(info.getId());
			if (roleList == null || roleList.size() == 0) {
				throw new RuntimeException("没有配置用套餐功能!!");
			}
			add.setPurchaseSource(ServicesPurchaseConstant.PURCHASE_SOURCE_7);   //三度后台创建用户
			add.setBusinessType(Objects.equals(info.getServicesType(),0) ? "0" : "3");     //业务类型为试用
			add.setUserScope(sysUser.getUserType() + "");
			add.setPurchaseAmount(new BigDecimal(1));
			add.setTelephone(sysUser.getMobile());
			//组装数据
			ServicesPurchaseRecord record = mobileAssemblePurchaseData(add, servicesPrice, sysUser.getCompanyId());
			int recordId = servicesPurchaseRecordService.addServicesPurchaseRecord(record);
			if (recordId > 0) {
				UserBatchVO userBatchVO = new UserBatchVO();
				userBatchVO.setCompanyId(sysUser.getCompanyId());
				userBatchVO.setServiceId(add.getServicesId());
				userBatchVO.setPurchaseRecordId(record.getId());
				ServicesAccountRel account = assembleAccountData(sysUser.getId(), sysUser.getNickName(), userBatchVO, add.getBusinessType(), record.getCreator());
				//写入套餐和用户关联关系表
				List<ServicesAccountRel> accountList = new ArrayList<>();
				accountList.add(account);
				servicesAccountRelService.batchInsertServiceAccount(accountList);
			} else {
				throw new RuntimeException("写入套餐记录失败!!");
			}
			return roleList;
		} catch (Exception e) {
			log.error("同城联盟注册获取套餐信息异常,失败信息{}", e);
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public ServicesAccountRel renewServiceForInner(InnerServiceRenew renew) {
		ServicesAccountRel oldAccount = servicesAccountRelService.getAccountByUserId(renew.getUserId());
		if (oldAccount == null) {
			throw new ServicePurchaseBizException("获取套餐用户失败");
		}

		ServicesBaseInfo service = servicesBaseInfoService.getById(oldAccount.getServicesId());
		if (service == null) {
			throw new ServicePurchaseBizException("获取套餐失败");
		}

		if(oldAccount.getEffectiveEnd() == null) {
			throw new ServicePurchaseBizException("请先登录激活该套餐");
		}

		Date now = new Date();
		ServicesAccountRel account = new ServicesAccountRel();
		oldAccount.setGmtModified(now);
		oldAccount.setModifier(renew.getLoginUser().getLoginName());
		BeanUtils.copyProperties(oldAccount, account);


		//删除旧记录
		oldAccount.setIsDeleted(1);
		servicesAccountRelService.updateByPrimaryKeySelective(oldAccount);

		//延长时间,插入
		account.setEffectiveEnd(fetchDate(account.getEffectiveEnd(), renew.getRenewMount(), renew.getRenewTimeUnit()));
		account.setId(null);
		servicesAccountRelService.batchInsertServiceAccount(Collections.singletonList(account));
		account = servicesAccountRelService.getAccountByUserId(account.getUserId().intValue());

		//同步用户套餐 时长
		log.info("同步套餐到期时间到用户表-----Start : userId:{},EffectiveEnd:{}", oldAccount.getUserId(), account.getEffectiveEnd());
		boolean result = sysUserManageService.dealUserFailureTimeAndValidTime(oldAccount.getUserId(), account.getEffectiveEnd());
		log.info("同步套餐到期时间到用户表-----END,reslut:{}", result);

		//插入续费日志
		this.initBuyServiceLog(oldAccount, account, String.format("账户%s 由 %s 续费至 %s", account.getAccount(), oldAccount.getEffectiveEnd(), account.getEffectiveEnd()));
		return account;

	}

	private void initBuyServiceLog(ServicesAccountRel oldAccount, @NotNull ServicesAccountRel account, String remark) {
		ServiceActionDetailsLog log = new ServiceActionDetailsLog();
		log.setActionType(2);
		log.setPreAccountId(oldAccount == null ? 0 : oldAccount.getId().intValue());
		log.setCurAccountId(account.getId().intValue());
		log.setRemark(remark);
		log.setCreator(account.getModifier());
		log.setGmtCreate(account.getGmtModified());
		serviceActionDetailsLogMapper.insert(log);
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
}
