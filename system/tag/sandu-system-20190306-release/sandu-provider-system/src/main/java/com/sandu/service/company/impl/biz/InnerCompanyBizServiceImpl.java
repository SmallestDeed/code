package com.sandu.service.company.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.company.CompanyBizException;
import com.sandu.api.company.input.InnerCompanyInput;
import com.sandu.api.company.input.InnerCompanyQuery;
import com.sandu.api.company.input.SysDictionaryInfo;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.CustomerAlotZone;
import com.sandu.api.company.model.UserLevelCfg;
import com.sandu.api.company.model.bo.InnerCompanyBO;
import com.sandu.api.company.output.InnerCompanyDetails;
import com.sandu.api.company.service.*;
import com.sandu.api.company.service.biz.InnerCompanyBizService;
import com.sandu.api.constance.BusinessTypeConstant;
import com.sandu.api.constance.SysDictionaryConstant;
import com.sandu.api.constance.UserTypeConstant;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.util.Constants;
import com.sandu.constant.Punctuation;
import com.sandu.service.company.dao.SysResLevelCfgMapper;
import com.sandu.system.model.ResPic;
import com.sandu.user.model.SysUserLevelPrice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sandu.api.constance.UserTypeConstant.COMPANY_CODE;
import static com.sandu.api.constance.UserTypeConstant.DISTRIBUTOR_CODE;
import static com.sandu.systemutil.SystemCommonUtil.getMd5Password;

/**
 * @author Sandu
 * @ClassName InnerCompanyBizServiceImpl
 * @date 2018/11/6
 */

@Service
@Slf4j
public class InnerCompanyBizServiceImpl implements InnerCompanyBizService {


	@Resource(name = "commonThreadPool")
	private ThreadPoolExecutor executor;

	@Autowired
	private BaseCompanyService baseCompanyService;

	@Autowired
	private SysResLevelCfgMapper sysResLevelCfgMapper;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserLevelPriceService sysUserLevelPriceService;

	@Autowired
	private SysUserLevelConfigService sysUserLevelConfigService;

	@Autowired
	private ProCategoryService proCategoryService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private CompanyCategoryRelService companyCategoryRelService;


	@Autowired
	private CustomerAlotZoneService customerAlotZoneService;

	@Autowired
	private SysRoleService  sysRoleService;

	@Autowired
	private BaseAreaService baseAreaService;


	@Autowired
	private ResPicService resPicService;


	/**
	 * 新增企业
	 *
	 * @param companyInput
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer addCompany(InnerCompanyInput companyInput) {
		BaseCompany baseCompany = new BaseCompany();
		BeanUtils.copyProperties(companyInput, baseCompany);
		if (companyInput.getId() != null) {
			baseCompany.setId(companyInput.getId().longValue());
		}

		//处理行业
		Collection<Integer> industries = fetchIndustryByCategorys(companyInput.getProductVisibilityRange(), this.fetchIndustryFormat()).keySet();
		baseCompany.setCompanyIndustrys(Joiner.on(Punctuation.COMMA).join(industries));
		baseCompany.setLongAreaCode(Strings.nullToEmpty(baseCompany.getProvinceCode()) + "."
				+ Strings.nullToEmpty(baseCompany.getCityCode()) + "."
				+ Strings.nullToEmpty(baseCompany.getAreaCode()) + "."
				+ Strings.nullToEmpty(baseCompany.getStreetCode()));

		if (companyInput.getId() == null && StringUtils.isEmpty(companyInput.getCompanyCode())) {
			validCompanyName(companyInput.getCompanyName(), null);
			//新建企业
			String companyCode = this.fetchCompanyCode();
			if (StringUtils.isEmpty(companyCode)) {
				throw new RuntimeException("创建企业编码失败!");
			}
			baseCompany.setCompanyCode(companyCode);
			baseCompanyService.insertSelective(baseCompany);
		} else {
			//更新
			validCompanyName(companyInput.getCompanyName(), companyInput.getId());
			baseCompanyService.updateByPrimaryKeySelective(baseCompany);
		}

		//处理企业经营范围
		if (baseCompany.getId() > 0) {
			companyCategoryRelService.batchSave(baseCompany.getId().intValue(), baseCompany.getCategoryIds(), companyInput.getLoginUser());
		}

		//小程序管理
		if (companyInput.getWithDistributor() != null && companyInput.getWithDistributor()) {
			managerMiniPro(baseCompany, companyInput.getIsManage(), companyInput.getLoginUser());
		}


		return baseCompany.getId().intValue();
	}

	private void validCompanyName(String companyName, Long id) {
		int i = baseCompanyService.checkCompanyName(companyName, id);
		if (i > 0) {
			throw new CompanyBizException("此公司名称已存在...");
		}
	}



	private void managerMiniPro(BaseCompany baseCompany, Integer isManage, LoginUser loginUser) {
		// 查询厂商下面所有的账号信息
		List<SysUser> users = sysUserService.getUsersByCompanyId(baseCompany.getId());
		//查询厂商客户管理角色权限
		SysRole companyRole = sysRoleService.getRoleByCode(COMPANY_CODE);
		//查询经销商客户管理角色权限
		SysRole distributorRole = sysRoleService.getRoleByCode(DISTRIBUTOR_CODE);
		Integer sysRole;
		if (isManage == 0) {
			//查询有地址的经销商 distributor
			List<BaseCompany> distributors = baseCompanyService.queryDistributor(baseCompany.getId());
			if (!distributors.isEmpty()) {
				executor.execute(() -> {
					for (BaseCompany distributor : distributors) {
						CustomerAlotZone customerAlotZone = new CustomerAlotZone();
						//设置区域长编码
						customerAlotZone.setLongCode(distributor.getProvinceCode() + "-" + distributor.getCityCode() + "-" + distributor.getAreaCode());
						//设置为自动生成类型
						customerAlotZone.setSourceType(0);

						List<CustomerAlotZone> oldCustomer = customerAlotZoneService.queryCustomer(distributor.getId().intValue(), 2);
						if (oldCustomer != null && oldCustomer.size() > 0) {
							for (CustomerAlotZone cus : oldCustomer) {
								customerAlotZoneService.updateCustomerZone(cus.getId());
							}
						} else {
							//根据经销商id，和来源类型 查询该经销商是否已经自动生成过分配规则
							BaseCompany oldCompany = customerAlotZoneService.getDistributorByType(0, distributor.getId().intValue());
							if (oldCompany != null) {
								//如果有，则不再自动生成分配规则
								continue;
							}
							//如果没有，根据经销商id,区域code查询分配规则表
							List<BaseCompany> companies = customerAlotZoneService.queryDistributorByInfo(distributor);

							//如果有， 则代表已手动添加过该区域规则
							if (companies != null && companies.size() > 0) {
								//获取该手动添加区域
								customerAlotZone.setId(companies.get(0).getId().intValue());
								//不再自动生成分配规则 直接更新该条数据的longCode，type
								customerAlotZone.setModifier("1");
								customerAlotZone.setGmtModified(new Date());
								customerAlotZoneService.updateByPrimaryKeySelective(customerAlotZone);
							} else {
								//按经销商查找有没有分配规则
								List<CustomerAlotZone> customerAlotZones =
										customerAlotZoneService.queryDistributorByCompanyId(distributor.getId().intValue());
								//如果有，则代表已手动添加过该经销商的其他分配规则
								if (customerAlotZones.size() > 0) {
									//获取第一条， 更新该条数据的longCode,Type
									customerAlotZone.setId(customerAlotZones.get(0).getId());
									customerAlotZone.setModifier("1");
									customerAlotZone.setGmtModified(new Date());
									customerAlotZoneService.updateByPrimaryKeySelective(customerAlotZone);
								} else {
									//如果该经销商没有分配过任何区域，自动分配一条按经销商地址的分配规则
									customerAlotZone.setId(null);
									customerAlotZone.setCompanyId(distributor.getPid());
									customerAlotZone.setChannelCompanyId(distributor.getId().intValue());
									customerAlotZone.setProvinceCode(distributor.getProvinceCode());
									customerAlotZone.setCityCode(distributor.getCityCode());
									customerAlotZone.setAreaCode(distributor.getAreaCode());
									customerAlotZone.setCreator("1");
									customerAlotZone.setGmtCreated(new Date());
									customerAlotZone.setIsDeleted(0);
									customerAlotZoneService.insertSelective(customerAlotZone);
								}
							}
						}
					}
				});
			}


			//开通厂商下面账号的客户管理权限
			for (SysUser user : users) {
				if (baseCompany.getId().equals(user.getBusinessAdministrationId())) {
					sysRole = companyRole.getId().intValue();
				} else {
					sysRole = distributorRole.getId().intValue();
				}
				SysUserRole userRole = new SysUserRole();
				userRole.setRoleId(sysRole.longValue());
				userRole.setUserId(user.getId());
				userRole.setCreator(loginUser.getLoginName());
				userRole.setGmtCreate(new Date());
				userRole.setModifier(loginUser.getLoginName());
				userRole.setGmtModified(new Date());
				List<SysUserRole> selectRoles = customerAlotZoneService.selectByPrimaryUserRole(userRole);
				if (selectRoles != null && selectRoles.size() > 0) {
					customerAlotZoneService.updateStatusById(selectRoles.get(0).getId(), 0,
							userRole.getModifier(), userRole.getGmtModified());
				} else {
					userRole.setIsDeleted(0);
					customerAlotZoneService.insertUserRoleSelective(userRole);
				}
//				redisLoginService.del(UserConstant.RBAC_USER_ROLE_PREFIX + user.getId());
			}

		} else {
			// 删除权限
			for (SysUser user : users) {
				//清除redis
//				redisLoginService.del(UserConstant.RBAC_USER_ROLE_PREFIX + user.getId());
				if (baseCompany.getId().equals(user.getBusinessAdministrationId())) {
					sysRole = companyRole.getId().intValue();
				} else {
					sysRole = distributorRole.getId().intValue();
				}
				SysUserRole userRole = new SysUserRole();
				userRole.setRoleId(sysRole.longValue());
				userRole.setUserId(user.getId());
				userRole.setModifier(loginUser.getLoginName());
				userRole.setGmtModified(new Date());
				List<SysUserRole> selectRoles = customerAlotZoneService.selectByPrimaryUserRole(userRole);
				if (selectRoles != null && selectRoles.size() > 0) {
					customerAlotZoneService.updateStatusById(selectRoles.get(0).getId(), 1,
							userRole.getModifier(), userRole.getGmtModified());
				}
			}
			Integer distributorIds = baseCompany.getId().intValue();
			executor.execute(() -> customerAlotZoneService.removeCustomerAlotZone(distributorIds));
		}

	}

	/**
	 * 根据企业ID删除企业
	 *
	 * @param companyIds ids
	 * @param userId     操作人ID
	 * @return
	 */
	@Override
	public Integer deleteByIds(List<Integer> companyIds, Integer userId) {
		//删除企业
		SysUser user = sysUserService.getUserById(userId.longValue());
		if (user == null) {
			throw new RuntimeException("获取用户失败");
		}
		return baseCompanyService.deleteByIds(companyIds, user.getUserName());
	}

	/**
	 * 更新企业信息
	 *
	 * @param companyInput
	 * @return
	 */
	@Override
	public Integer updateCompany(InnerCompanyInput companyInput) {

		return this.addCompany(companyInput);
	}

	/**
	 * 查询企业列表
	 *
	 * @return
	 * @param query
	 */
	@Override
	public PageInfo<InnerCompanyBO> listCompany(InnerCompanyQuery query) {
		//set query param
		setInnerCompanyQuery(query);

		//do query
		PageInfo<InnerCompanyBO> innerCompanyBOPageInfo = baseCompanyService.queryInnerCompany(query);


		setListViewData(innerCompanyBOPageInfo.getList());

		return innerCompanyBOPageInfo;
	}

	private void setListViewData(List<InnerCompanyBO> list) {
		//设置行业信息
		List<SysDictionary> industry = fetchIndustryFormat();

		List<SysDictionary> companyType = dictionaryService.listByType(SysDictionaryConstant.DICTIONARY_COMPANY_TYPE);


		List<String> areaCodes = list.stream().map(InnerCompanyBO::getLongAreaCode).filter(StringUtils::isNotEmpty)
				.map(it -> Splitter.on(Punctuation.DOT).omitEmptyStrings().trimResults().splitToList(it))
				.reduce(new LinkedList<>(), (o1, o2) -> {
					if (!o2.isEmpty()) {
						o1.addAll(o2);
					}
					return o1;
				});

		Map<String, String> code2Name = baseAreaService.code2Name(new HashSet<>(areaCodes));

		list.forEach(it -> {
			//处理企业类型
			companyType.stream().filter(type -> type.getValue().equals(it.getBusinessType()))
					.findFirst()
					.ifPresent(type -> it.setCompanyType(type.getName()));

			//处理企业地区信息
			if (it.getProvinceCode() != null) {
				it.setProvinceCode(code2Name.get(it.getProvinceCode()));
			}
			if (it.getAreaCode() != null) {
				it.setAreaCode(code2Name.get(it.getAreaCode()));
			}
			if (it.getCityCode() != null) {
				it.setCityCode(code2Name.get(it.getCityCode()));
			}
			if (it.getStreetCode() != null) {
				it.setStreetCode(code2Name.get(it.getStreetCode()));
			}

			//处理行业信息
			if (StringUtils.isEmpty(it.getProductVisibilityRange())) {
				return;
			}

			Map<Integer, String> industryValue2Name = fetchIndustryByCategorys(it.getProductVisibilityRange(), industry);

			List<SysDictionaryInfo> dictionaryInfo = industryValue2Name.entrySet().stream()
					.map(en -> SysDictionaryInfo.builder()
							.name(en.getValue())
							.value(en.getKey())
							.build())
					.collect(Collectors.toList());
			it.setIndustryInfo(dictionaryInfo);
		});

	}

	/**
	 * 获取行业字典,替换att2中的分类编码为分类ＩＤ
	 *
	 * @return
	 */
	private List<SysDictionary> fetchIndustryFormat() {
		List<SysDictionary> industry = dictionaryService.listByType(SysDictionaryConstant.DICTIONARY_INDUSTRY);
		List<String> reduce = industry.stream()
				.filter(it -> StringUtils.isNotEmpty(it.getAtt2()))
				.map(it -> Arrays.asList(it.getAtt2().split(Punctuation.COMMA)))
				.reduce(new ArrayList<>(), (o1, o2) -> {
					o1.addAll(o2);
					return o1;
				});
		if (reduce.isEmpty()) {
			return industry;
		}
		List<CategoryListVO> categorys = proCategoryService.getListByCodeList(reduce);
		Map<String, Integer> code2Id = categorys.stream().collect(Collectors.toMap(CategoryListVO::getCategoryCode, CategoryListVO::getCategoryId));
		for (SysDictionary in : industry) {
			if (Strings.isNullOrEmpty(in.getAtt2())) {
				continue;
			}
			for (Map.Entry<String, Integer> entry : code2Id.entrySet()) {
				if (in.getAtt2().contains(entry.getKey())) {
					String replace = in.getAtt2().replace(entry.getKey(), entry.getValue() + "");
					in.setAtt2(replace);
				}
			}

		}
		return industry;
	}

	private Map<Integer, String> fetchIndustryByCategorys(String productVisibilityRange, List<SysDictionary> sysDictionaries) {
		if (StringUtils.isEmpty(productVisibilityRange) || sysDictionaries.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<Integer, String> id2Name = new HashMap<>(sysDictionaries.size());
		List<String> currentCategoryList = Arrays.asList(productVisibilityRange.split(Punctuation.COMMA));
		for (SysDictionary dictionary : sysDictionaries) {
			List<String> list = Arrays.asList(Strings.nullToEmpty(dictionary.getAtt2()).split(Punctuation.COMMA));
			if (Collections.disjoint(list, currentCategoryList)){
				continue;
			}
			id2Name.put(dictionary.getValue(), dictionary.getName());
		}

		return id2Name;

	}

	private void setInnerCompanyQuery(InnerCompanyQuery query) {
		if (StringUtils.isEmpty(query.getIndustryValueStr())) {
			return;

		}
		query.setIndustryValue(Arrays.stream(query.getIndustryValueStr().split(Punctuation.COMMA)).map(Integer::new).collect(Collectors.toList()));

		List<SysDictionary> sysDictionaries = dictionaryService.listByType(SysDictionaryConstant.DICTIONARY_INDUSTRY);

		List<String> categoryCodes = sysDictionaries.stream().filter(it -> query.getIndustryValue().contains(it.getValue()))
				.map(SysDictionary::getAtt2)
				.map(it -> Arrays.asList(Strings.nullToEmpty(it).split(Punctuation.COMMA)))
				.reduce(new LinkedList<>(), (o1, o2) -> {
					o1.addAll(o2);
					return o1;
				});
		query.setCategoryCodeList(categoryCodes);

	}

	/**
	 * 获取企业详情
	 *
	 * @param companyId
	 * @return
	 */
	@Override
	public InnerCompanyDetails details(Long companyId) {
		BaseCompany company = baseCompanyService.selectByPrimaryKey(companyId);
//		List<SysDictionary> industrys = dictionaryService.listByType(SysDictionaryConstant.DICTIONARY_INDUSTRY);
//		List<String> ins = new LinkedList<>();
//		Splitter.on(Punctuation.COMMA).trimResults().omitEmptyStrings().splitToList(company.getCompanyIndustrys())
//				.forEach(it -> {
//					industrys.stream().filter(in -> in.getValue().equals(it))
//							.findFirst()
//							.ifPresent(in -> {
//								ins.add(in.getName());
//							});
//				});

		List<Integer> categoryIds = Stream.of(Strings.nullToEmpty(company.getProductVisibilityRange()).split(Punctuation.COMMA))
				.filter(StringUtils::isNotBlank)
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		List<CategoryListVO> listByIdList = proCategoryService.getListByIdList(categoryIds);


		InnerCompanyDetails details = new InnerCompanyDetails();
		BeanUtils.copyProperties(company, details);
		details.setCategoryListVOList(listByIdList);


		if (details.getCompanyLogo() != null) {
			ResPic pic = resPicService.get(details.getCompanyLogo());
			if (pic != null) {
				details.setCompanyLogoUrl(pic.getPicPath());
			}
		}


		//set name
		Map<String, String> code2Name = baseAreaService.code2Name(new HashSet<>(Splitter.on(Punctuation.DOT).trimResults().splitToList(Strings.nullToEmpty(company.getLongAreaCode()))));
		details.setAreaCodeName(code2Name.get(details.getAreaCode()));
		details.setCityCodeName(code2Name.get(details.getCityCode()));
		details.setProvinceCodeName(code2Name.get(details.getProvinceCode()));
		details.setStreetCodeName(code2Name.get(details.getStreetCode()));


		SysDictionary bussinessType = dictionaryService.getByTypeAndValue(SysDictionaryConstant.DICTIONARY_COMPANY_TYPE, details.getBusinessType());
		if (bussinessType != null) {
			details.setBusinessTypeName(bussinessType.getName());
		}



		return details;
	}

	@Override
	public int checkCompanyName(String name) {
		return baseCompanyService.checkCompanyName(name, null);
	}


	private String fetchCompanyCode() {
		/**
		 * 生成企业code
		 * 生成规则：
		 * 产品可见范围  + 7位数序列  例：（ABC0000001）
		 *
		 */
		try {
			List<Integer> businessTypeList = new ArrayList<Integer>();
			businessTypeList.add(BusinessTypeConstant.BUSINESS_TYPE_FRANCHISER);
			//前缀
			StringBuilder companyCodePrefix = new StringBuilder();
			//后缀
			StringBuilder companyCodeSuffix = new StringBuilder();

			companyCodePrefix.append("C");

			String companyCodeMax = "";
			companyCodeMax = baseCompanyService.createCompanyCode(companyCodePrefix.toString() + "%", null, businessTypeList);

			int companyCode;
			if (StringUtils.isNotEmpty(companyCodeMax)) {
				companyCode = Integer.parseInt(companyCodeMax.substring(1, companyCodeMax.length()));
				companyCode++;
				companyCodeSuffix.append(companyCode);
			} else {
				companyCodeSuffix.append("1");
			}

			int k = companyCodeSuffix.length();
			for (int i = 0; i < 7 - k; i++) {
				companyCodeSuffix.insert(0, "0");
			}

			return companyCodePrefix + "" + companyCodeSuffix;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 创建企业超级管理员
	 *
	 * @param sysUser
	 * @param loginUser
	 * @return
	 */
	private Integer createAdminUser(SysUser sysUser, LoginUser loginUser) {
		Date now = new Date();
		String areaLongCode = "";
		//用户区域Id，用于在用户列表显示用户区域
		Integer areaId = 0;

		sysUser.setMediaType(1);
		sysUser.setPlatformType(2);
		sysUser.setAreaLongCode(areaLongCode);
		sysUser.setAreaId(areaId);
		if (sysUser.getGroupId() == null) {
			sysUser.setGroupId(0);
		}
		sysUser.setPassword(getMd5Password(sysUser.getPassword()));
		sysUser.setUserType(UserTypeConstant.USER_TYPE_COMPANY);
		//是否需要更新密码标识
		sysUser.setPasswordUpdateFlag(1);
		sysUser.setGmtCreate(now);
		sysUser.setGmtModified(now);
		sysUser.setIsDeleted(0);
		sysUser.setModifier(loginUser.getId() + "");
		sysUser.setCreator(loginUser.getId() + "");
		//有效时长
		sysUser.setUseType(1);
		sysUser.setValidTime(12);

		SysDictionary mediaType = dictionaryService.getByTypeAndValue("mediaType", sysUser.getMediaType());
		if (mediaType != null) {
			sysUser.setAppKey(mediaType.getAtt1());
		}

		sysUserService.insertUser(sysUser);
		Long id = sysUser.getId();

		//初始化用户等级
		UserLevelCfg cfg = new UserLevelCfg();
		cfg.setUserId(id.intValue());
		cfg.setPcLimitKey(Constants.USER_LEVEL_PC_LIMIT_1);
		cfg.setMobileLimitKey(Constants.USER_LEVEL_MOBILE_LIMIT_1);
		cfg.setUserGroupType(sysUser.getUserType());
		cfg.setUserLevel(Constants.USER_LEVEL_INIT);
		cfg.setVersion(Constants.USER_LEVEL_USER_PAY_TYPE_BASE);
		this.addUserLevelLimit(cfg);
		log.info("add:id=" + id);
		sysUser.setId((long) id);
		SysUserLevelPrice levelPrice = sysUserLevelPriceService.getIdByUserType(sysUser.getUserType());
		if (levelPrice != null && levelPrice.getId() > 0) {
			sysUserLevelConfigService.initUserLevelByLevelPriceId(id, levelPrice.getId());
		}


		return id.intValue();
	}


	private boolean addUserLevelLimit(UserLevelCfg cfg) {
		if(cfg==null){
			return false;
		}

		if(cfg.getUserId()<=0 || cfg.getUserLevel()<=0){
			return false;
		}

		if(cfg.getUserGroupType()<0 || cfg.getVersion()<0){
			return false;
		}

		if(StringUtils.isEmpty(cfg.getMobileLimitKey()) || StringUtils.isEmpty(cfg.getPcLimitKey())){
			return false;
		}

		sysResLevelCfgMapper.addUserResLevel(cfg);
		sysResLevelCfgMapper.addUserDeviceLimit(cfg);
		return true;
	}






}
