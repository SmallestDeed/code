package com.sandu.web.servicepurchase.controller;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.servicepurchase.input.ServicesPurchaseRecordAdd;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.input.query.ServicesPriceQuery;
import com.sandu.api.servicepurchase.input.query.ServicesPurchaseRecordQuery;
import com.sandu.api.servicepurchase.input.query.ServicesRoleRelQuery;
import com.sandu.api.servicepurchase.model.*;
import com.sandu.api.servicepurchase.output.ServicesPurchaseRecordVO;
import com.sandu.api.servicepurchase.output.ServicesUserVO;
import com.sandu.api.servicepurchase.serivce.*;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.systemutil.ExcelUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.Base64Utils.encodeToString;

/**
 * @author Sandu
 */
@Slf4j
@Api("企业代购")
@RestController
@RequestMapping("/service/purchase/inner/buy")
public class SetMealBuyInfoController {

	private final ServicesPurchaseRecordService servicesPurchaseRecordService;
	private final ServicesPriceService servicesPriceService;
	private final ServicesRoleRelService servicesRoleRelService;
	private final SysRoleService sysRoleService;
	private final ServicesBaseInfoService servicesBaseInfoService;
	private final DictionaryService sysDictionaryService;
	private final BaseCompanyService baseCompanyService;
	private final SysUserService sysUserService;
	private final ServicesAccountRelService servicesAccountRelService;
	private final ServicesAccountBuyService servicesAccountBuyService;


	/**
	 * 数据字典中企业对应的用户类型的type
	 */
	private final static String BRAND_BUSINESS_TYPE = "brandBusinessType";
	/**
	 * 套餐状态(0-禁用;1-启用)
	 */
	private final static String SERVICES_STATUS_NO_0 = "0";
	/**
	 * 套餐状态(0-禁用;1-启用)
	 */
	private final static String SERVICES_STATUS_YES_1 = "1";
	/**
	 * 删除标志(0-未删除;1-删除)
	 */
	private final static Integer DELETE_FLAG_0 = 0;
	/**
	 * 通用的companyId
	 */
	private final static Integer COMMON_COMPANY_ID = -1;
	/**
	 * 数据字典用户类型type
	 */
	private final static String USER_TYPE = "userType";
	/**
	 * 三度后台购买的渠道
	 */
	private final static String SANDU_SALE_CHANNEL = "2";
	/**
	 * 套餐购买记录支付成功
	 */
	private final static String PAY_STATUS_SUCCESS = "1";
	/**
	 * 报表数组定义
	 */
	private String[][] content;

	@Autowired
	public SetMealBuyInfoController(ServicesPurchaseRecordService servicesPurchaseRecordService, ServicesPriceService servicesPriceService, ServicesRoleRelService servicesRoleRelService, SysRoleService sysRoleService, ServicesBaseInfoService servicesBaseInfoService, DictionaryService sysDictionaryService, BaseCompanyService baseCompanyService, SysUserService sysUserService, ServicesAccountRelService servicesAccountRelService, ServicesAccountBuyService servicesAccountBuyService) {
		this.servicesPurchaseRecordService = servicesPurchaseRecordService;
		this.servicesPriceService = servicesPriceService;
		this.servicesRoleRelService = servicesRoleRelService;
		this.sysRoleService = sysRoleService;
		this.servicesBaseInfoService = servicesBaseInfoService;
		this.sysDictionaryService = sysDictionaryService;
		this.baseCompanyService = baseCompanyService;
		this.sysUserService = sysUserService;
		this.servicesAccountRelService = servicesAccountRelService;
		this.servicesAccountBuyService = servicesAccountBuyService;
	}

	@GetMapping("companys")
	public Object listCompany() {
		List<SysDictionary> brandBusinessType = sysDictionaryService.listByType("brandBusinessType");

		List<BaseCompany> notAgent = baseCompanyService.queryCompanyByType(
				brandBusinessType.stream().filter(it -> !"agent".equals(it.getValuekey()))
						.map(SysDictionary::getValue)
						.collect(Collectors.toList())
		);
		return notAgent.stream()
				.filter(it -> it.getIsDeleted() != null && it.getIsDeleted() == 0)
				.map(it -> {
					HashMap<String, Object> map = new HashMap<>(1);
					map.put("id", it.getId());
					map.put("companyName", it.getCompanyName());
					return map;
				}).collect(Collectors.toList());
	}



	/**
	 * 获取企业能购买的套餐
	 *
	 * @param companyId
	 * @return
	 */
	@GetMapping(value = "/getServicesList")
	public Object getServicesList(Long companyId) {
		//获取企业可以购买的用户类型
		Set<String> userScopeSet = this.getUserScopeByCompanyId(Math.toIntExact(companyId));
		//查询套餐
		ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
		servicesBaseInfoQuery.setServicesStatus(SERVICES_STATUS_YES_1);
		servicesBaseInfoQuery.setUserScopeSet(userScopeSet);
		servicesBaseInfoQuery.setIsDeleted(DELETE_FLAG_0);
		servicesBaseInfoQuery.setSaleChannel(SANDU_SALE_CHANNEL);
		List<ServicesBaseInfo> setmealList = servicesBaseInfoService.queryServiceBaseInfo(servicesBaseInfoQuery);
		if (CollectionUtils.isEmpty(setmealList)) {
			log.error("未找到可购买套餐");
			return Collections.EMPTY_LIST;
		}
		return setmealList;
	}


	/**
	 * 获取套餐人员
	 *
	 * @param servicesId
	 * @return
	 */
	@GetMapping(value = "/getUserScopes")
	public Object getUserScopes(Long servicesId) {
		List<ServicesUserVO> servicesUserVOList = new ArrayList<>();
		ServicesUserVO servicesUserVO;
		String[] userScopes = servicesBaseInfoService.getById(servicesId).getUserScope().split(",");
		List<SysDictionary> sysDictionaryList = sysDictionaryService.listByType(USER_TYPE);
		for (SysDictionary sysDictionary : sysDictionaryList) {
			for (String s : userScopes) {
				if (Integer.valueOf(s).equals(sysDictionary.getValue())) {
					servicesUserVO = new ServicesUserVO();
					servicesUserVO.setId(sysDictionary.getValue());
					servicesUserVO.setName(sysDictionary.getName());
					servicesUserVOList.add(servicesUserVO);
				}
			}
		}
		return servicesUserVOList;
	}


	/**
	 * 获取时长
	 *
	 * @param
	 * @return
	 */
	@GetMapping(value = "/getDuration")
	public Object getDuration(Long servicesId, Integer companyId) {
		ServicesPriceQuery servicesPriceQuery = new ServicesPriceQuery();
		if (companyId == null) {
			servicesPriceQuery.setCompanyId(COMMON_COMPANY_ID);
		} else {
			servicesPriceQuery.setCompanyId(companyId);
		}

		servicesPriceQuery.setServicesId(servicesId);
		servicesPriceQuery.setIsDeleted(DELETE_FLAG_0);
		List<ServicesPrice> servicesPriceList = servicesPriceService.queryByOption(servicesPriceQuery);
		if (CollectionUtils.isEmpty(servicesPriceList)) {
			servicesPriceQuery.setCompanyId(COMMON_COMPANY_ID);
			servicesPriceList = servicesPriceService.queryByOption(servicesPriceQuery);
			if (CollectionUtils.isEmpty(servicesPriceList)) {
				return Collections.EMPTY_LIST;
			}
		}
		for (ServicesPrice servicesPrice : servicesPriceList) {
			switch (servicesPrice.getPriceUnit()) {
				case "0":
					servicesPrice.setPriceUnit("年");
					break;
				case "1":
					servicesPrice.setPriceUnit("月");
					break;
				case "2":
					servicesPrice.setPriceUnit("日");
					break;
				default:
					break;
			}
			servicesPrice.setPriceUnit(servicesPrice.getDuration() + servicesPrice.getPriceUnit());
		}
		return servicesPriceList;
	}


	/**
	 * 获取列表
	 *
	 */
	//@RequiresPermissions({"service.buy.view"})
	@GetMapping(value = "/getPageList/{page}/{limit}")
	public ResponseEnvelope getPageList(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
		ServicesPurchaseRecordQuery servicesPurchaseRecordQuery = new ServicesPurchaseRecordQuery();
		servicesPurchaseRecordQuery.setStart(page);
		servicesPurchaseRecordQuery.setLimit(limit);
		//返回对象
		List<ServicesPurchaseRecordVO> servicesPurchaseRecordVoList = new ArrayList<>();
		ResponseEnvelope<ServicesPurchaseRecordVO> res = new ResponseEnvelope<>();
		servicesPurchaseRecordQuery.setPurchaseStatus(PAY_STATUS_SUCCESS);

		//查询信息
		Integer total = servicesPurchaseRecordService.getCount(servicesPurchaseRecordQuery);
		if (total < 1) {
			return new ResponseEnvelope(false, "无数据...");
		}
		List<ServicesPurchaseRecord> servicesPurchaseRecordList = servicesPurchaseRecordService.queryByOption(servicesPurchaseRecordQuery);

		//设置回显数据
		setListViewData(servicesPurchaseRecordVoList, servicesPurchaseRecordList);

		return new ResponseEnvelope<>("查询成功", true, total.longValue(), servicesPurchaseRecordVoList);
	}

	private void setListViewData(List<ServicesPurchaseRecordVO> servicesPurchaseRecordVoList, List<ServicesPurchaseRecord> servicesPurchaseRecordList) {
		Set<Integer> companyIdSet = new HashSet<>();
		Set<Long> setmealIdSet = new HashSet<>();
		for (ServicesPurchaseRecord servicesPurchaseRecord : servicesPurchaseRecordList) {
			companyIdSet.add(Integer.valueOf(servicesPurchaseRecord.getCompanyId()));
			setmealIdSet.add(servicesPurchaseRecord.getServicesId());
		}
		// 查询公司信息
		Map<Long, BaseCompany> companyMap = getCompanyMap(companyIdSet);

		//查询公司类型
		Map<Integer, String> companyTypeMap = getCompanyTypeMap();

		// 查询套餐信息
		Map<Long, ServicesBaseInfo> servicesBaseInfoMap = getServicesBaseInfoMap(setmealIdSet);

		//组装返回对象
		ServicesPurchaseRecordVO servicesPurchaseRecordVo;
		for (ServicesPurchaseRecord servicesPurchaseRecord : servicesPurchaseRecordList) {

			BaseCompany company = new BaseCompany();
			if (companyMap != null) {
				company = companyMap.get(Long.valueOf(servicesPurchaseRecord.getCompanyId()));
			}
			ServicesBaseInfo servicesBaseInfo = new ServicesBaseInfo();
			if (servicesBaseInfoMap != null) {
				servicesBaseInfo = servicesBaseInfoMap.get(servicesPurchaseRecord.getServicesId());
			}
			servicesPurchaseRecordVo = new ServicesPurchaseRecordVO();
			servicesPurchaseRecordVo.setId(servicesPurchaseRecord.getId().intValue());
			if (company != null) {
				servicesPurchaseRecordVo.setCompanyCode(company.getCompanyCode());
				servicesPurchaseRecordVo.setCompanyName(company.getCompanyName());
				servicesPurchaseRecordVo.setCompanyType(companyTypeMap.get(company.getBusinessType()));
			}
			if (servicesBaseInfo != null) {
				servicesPurchaseRecordVo.setSetmealName(servicesBaseInfo.getServicesName());
			}
			servicesPurchaseRecordVo.setRemark(servicesPurchaseRecord.getRemark());
//            //设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			servicesPurchaseRecordVo.setGmtCreate(df.format(servicesPurchaseRecord.getGmtCreate()));
			servicesPurchaseRecordVo.setModifier(servicesPurchaseRecord.getModifier());

			servicesPurchaseRecordVo.setSetmealNum(String.valueOf(servicesPurchaseRecord.getPurchaseAmount()));
			servicesPurchaseRecordVo.setSetmealPrice(String.valueOf(servicesPurchaseRecord.getPurchaseMomey()));
			servicesPurchaseRecordVoList.add(servicesPurchaseRecordVo);
		}
	}


	/**
	 * 导出报表
	 *
	 * @return
	 */
	@GetMapping("/account/export/{id}")
	//@RequiresPermissions({"service.buy.export"})
	public void export(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response
	) throws IOException {

		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null) {
			return ;
		}

		if (id == null) {
			return ;
		}

		//获取数据
		ServicesPurchaseRecord servicesPurchaseRecord = servicesPurchaseRecordService.selectByPrimaryKey(Long.valueOf(id));
		if (servicesPurchaseRecord == null) {
			log.error("导出套餐账号-没有找到购买记录");
			return ;
		}
		ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.getById(servicesPurchaseRecord.getServicesId());
		if (servicesBaseInfo == null) {
			log.error("导出套餐账号-没有找到套餐基本");
			return ;
		}
		List<ServicesAccountRel> servicesAccountRelList = servicesAccountRelService.selectByServicesRecordId(id);
		/**
		 * 数据字典中的用户类型key
		 */
		String SYS_DICTIONARY_USER_TYPE = "userType";
		SysDictionary sysDictionary = sysDictionaryService.getByTypeAndValue(SYS_DICTIONARY_USER_TYPE, Integer.valueOf(servicesPurchaseRecord.getUserScope()));

		if (CollectionUtils.isEmpty(servicesAccountRelList)) {
			log.error("导出套餐账号-没有找到账号记录");
			return ;
		}

		Set<String> accountSet = new HashSet<>();
		for (ServicesAccountRel servicesAccountRel : servicesAccountRelList) {
			accountSet.add(servicesAccountRel.getAccount());
		}

		List<SysUser> sysUserList = sysUserService.getUserByNickName(new ArrayList<>(accountSet));
		Map<String, Integer> isLoginMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(sysUserList)) {
			for (SysUser sysUser : sysUserList) {
				isLoginMap.put(sysUser.getNickName(), sysUser.getIsLoginBefore() == null ? 0 : sysUser.getIsLoginBefore());
			}
		}

		//excel标题
		String[] title = {"套餐序号", "套餐名称", "描述", "有效时长", "使用状态", "使用账号", "登录名", "初始密码", "用户类型", "购买时间"};

		//servicesExcelVos
		String fileName = "套餐账号表" + System.currentTimeMillis() + ".xls";

		//sheet名
		String sheetName = "套餐账号表";
		StringBuffer duration;
		content = new String[servicesAccountRelList.size()][title.length];
		for (int i = 0; i < servicesAccountRelList.size(); i++) {
			content[i] = new String[title.length];
			ServicesAccountRel servicesAccountRel = servicesAccountRelList.get(i);
			content[i][0] = servicesBaseInfo.getServicesCode();
			content[i][1] = servicesBaseInfo.getServicesName();
			content[i][2] = servicesBaseInfo.getServiceDesc();
			//有效时长
			duration = new StringBuffer();
			duration.append("购买时长").append(":").append(servicesPurchaseRecord.getDuration() == null ? 0 : servicesPurchaseRecord.getDuration());
			/**
			 * 年
			 */
			String PRICE_UNIT_YEAR = "0";
			if (servicesPurchaseRecord.getPriceUnit().equals(PRICE_UNIT_YEAR)) {
				duration.append("年");
			}
			/**
			 * 月
			 */
			String PRICE_UNIT_MONTH = "1";
			if (servicesPurchaseRecord.getPriceUnit().equals(PRICE_UNIT_MONTH)) {
				duration.append("月");
			}
			/**
			 * 日
			 */
			String PRICE_UNIT_DAY = "2";
			if (servicesPurchaseRecord.getPriceUnit().equals(PRICE_UNIT_DAY)) {
				duration.append("日");
			}
			if (servicesPurchaseRecord.getGiveDuration() > 0) {
				duration.append(",").append("赠送时长").append(":").append(servicesPurchaseRecord.getGiveDuration()).append("天");
			}
			content[i][3] = duration.toString();
			// 使用状态
			if (isLoginMap.containsKey(servicesAccountRel.getAccount())) {
				content[i][4] = isLoginMap.get(servicesAccountRel.getAccount()).equals(0) ? "未使用" : "已使用";
			} else {
				content[i][4] = "账号异常";
			}
			//账号
			content[i][5] = servicesAccountRel.getAccount();
			//登录名
			content[i][6] = servicesAccountRel.getAccount();
			//密码
			content[i][7] = servicesAccountRel.getPassword();
			//用户类型
			if (sysDictionary != null) {
				content[i][8] = sysDictionary.getName();
			}

			//购买时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			content[i][9] = servicesPurchaseRecord.getGmtCreate() != null ? formatter.format(servicesPurchaseRecord.getGmtCreate()) : null;
		}

		//创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

		OutputStream os = null;
		//响应到客户端
		try {
			this.setResponseHeader(response, request, fileName);
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	private void setResponseHeader(
			HttpServletResponse response, HttpServletRequest request, String codedFileName)
			throws UnsupportedEncodingException {
		String agent = request.getHeader("USER-AGENT");
		if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
			codedFileName =
					"=?UTF-8?B?"
							+ (encodeToString(codedFileName.getBytes(StandardCharsets.UTF_8)))
							+ "?=";
		} else {
			codedFileName = URLEncoder.encode(codedFileName, "UTF-8");
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName);
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
	}

	/**
	 * 发送响应流方法
	 */
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 新增
	 *
	 * @param servicesPurchaseRecordAdd
	 * @return
	 */
	//@RequiresPermissions("service.buy.add")
	@PostMapping(value = "/add")
	@Transactional(rollbackFor = {Exception.class})
	public ResponseEnvelope add(@RequestBody ServicesPurchaseRecordAdd servicesPurchaseRecordAdd) {

		//转换流参数
		if (servicesPurchaseRecordAdd == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "传参异常!", "none");
		}

		//校验参数
		ResponseEnvelope responseEnvelope = checkParam(servicesPurchaseRecordAdd);
		if (!responseEnvelope.isSuccess()) {
			return responseEnvelope;
		}


		//查询基本信息
		ServicesBaseInfo servicesBaseInfo = servicesBaseInfoService.getById(servicesPurchaseRecordAdd.getServiceId());
		if (servicesBaseInfo == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "套餐不存在，请刷新后重试!", "none");
		}

		//查询企业可以购买的用户类型
		Integer companyId = servicesPurchaseRecordAdd.getCompanyId();
		Set<String> userScopeSet = this.getUserScopeByCompanyId(companyId);
		//校验用户是否可购买此套餐
		if (!checkUserType(userScopeSet, servicesPurchaseRecordAdd.getUserTypeId())) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "此套餐不适合该用户类型,不可购买!", "none");
		}

		//查询套餐价格信息
		ServicesPrice servicesPrice = servicesPriceService.findPriceById(Long.valueOf(servicesPurchaseRecordAdd.getTimeTypeId()));
		if (servicesPrice == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "未找到套餐价格,不可购买!", "none");
		}

		//查询套餐角色
		ServicesRoleRelQuery servicesRoleRelQuery = new ServicesRoleRelQuery();
		servicesRoleRelQuery.setServicesId(servicesPurchaseRecordAdd.getServiceId());
		List<ServicesRoleRel> servicesRoleRelList = servicesRoleRelService.queryByOption(servicesRoleRelQuery);
		if (CollectionUtils.isEmpty(servicesRoleRelList)) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "此套餐未设置角色,不可购买!", "none");
		}
		Set<Long> roleIds = new HashSet<>();
		servicesRoleRelList.forEach(servicesRoleRel -> {
			roleIds.add(servicesRoleRel.getRoleId().longValue());
		});

		//查询套餐角色平台
		List<SysRole> sysRoleList = sysRoleService.getRolesByRoleIds(roleIds);
		if (CollectionUtils.isEmpty(servicesRoleRelList)) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "此套餐角色未设置平台,不可购买!", "none");
		}
		Map<Long, Long> roleAndPlatfromMap = sysRoleList.stream().collect(Collectors.toMap(SysRole::getId, SysRole::getPlatformId));
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		//生成用户账号
		boolean success = servicesAccountBuyService.addServicesAccount(servicesPurchaseRecordAdd, servicesBaseInfo, servicesPrice, roleAndPlatfromMap, loginUser);
		if (!success) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "操作失败!", "none");
		}
		return new ResponseEnvelope<ServicesPurchaseRecordAdd>(true, "操作成功!", "none");
	}

	/**
	 * 企业类型包含的用户类型集合
	 *
	 * @param companyId
	 * @return
	 */
	private Set<String> getUserScopeByCompanyId(Integer companyId) {
		BaseCompany baseCompany = baseCompanyService.getCompanyById(companyId);
		if (baseCompany == null) {
			log.error(" 没有找到公司，数据异常，请联系管理员，公司id:{}", companyId);
			return Collections.EMPTY_SET;
		}
		//企业类型的Value
		Integer value = baseCompany.getBusinessType();
		//根据企业类型查询用户类型
		SysDictionary sysDictionary = sysDictionaryService.getByTypeAndValue(BRAND_BUSINESS_TYPE, value);
		if (sysDictionary == null) {
			log.error("数据字典没有找到公司类型");
			log.info(BRAND_BUSINESS_TYPE);
			return Collections.EMPTY_SET;
		}
		//用户类型的type
		String type = sysDictionary.getValuekey();
		List<SysDictionary> sysDictionaryList = sysDictionaryService.listByType(type);
		if (org.apache.commons.collections.CollectionUtils.isEmpty(sysDictionaryList)) {
			log.error("数据字典没有找到用户类型的type");
			log.info(type);
			return Collections.EMPTY_SET;
		}
		Set<String> set = new HashSet<>();
		for (SysDictionary sysDict : sysDictionaryList) {
			set.add(sysDict.getAtt1());
		}
		return set;
	}

	/**
	 * 校验参数
	 *
	 * @param servicesPurchaseRecordAdd
	 * @return
	 */
	private ResponseEnvelope checkParam(ServicesPurchaseRecordAdd servicesPurchaseRecordAdd) {
		if (servicesPurchaseRecordAdd.getServiceId() == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "没有选择套餐!", "none");
		}
		if (servicesPurchaseRecordAdd.getCompanyId() == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "没有选择企业!", "none");
		}
		if (servicesPurchaseRecordAdd.getNum() == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "没有选择套餐数量!", "none");
		}
		if (servicesPurchaseRecordAdd.getUserTypeId() == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "没有选择用户类型!", "none");
		}
		if (servicesPurchaseRecordAdd.getTimeTypeId() == null) {
			return new ResponseEnvelope<ServicesPurchaseRecordAdd>(false, "没有选择套餐时长!", "none");
		}
		return new ResponseEnvelope<ServicesPurchaseRecordAdd>(true, "success", "200");
	}

	/**
	 * 校验用户类型
	 *
	 * @param userType
	 * @return
	 */
	private boolean checkUserType(Set<String> userScopes, Integer userType) {
		for (String s : userScopes) {
			if (Integer.valueOf(s).equals(userType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询套餐信息
	 *
	 * @param setmealIdSet
	 * @return
	 */
	private Map<Long, ServicesBaseInfo> getServicesBaseInfoMap(Set<Long> setmealIdSet) {
		Map<Long, ServicesBaseInfo> servicesBaseInfoMap = null;
		if (setmealIdSet != null && setmealIdSet.size() > 0) {
			ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
			servicesBaseInfoQuery.setIds(setmealIdSet);
			List<ServicesBaseInfo> servicesBaseInfoList = servicesBaseInfoService.queryServiceBaseInfo(servicesBaseInfoQuery);
			if (!CollectionUtils.isEmpty(servicesBaseInfoList)) {
				servicesBaseInfoMap = servicesBaseInfoList.stream().collect(Collectors.toMap(ServicesBaseInfo::getId, servicesBaseInfo -> servicesBaseInfo, (k1, k2) -> k1));
			}
		}
		return servicesBaseInfoMap;
	}

	/**
	 * 查询公司类型
	 *
	 * @return
	 */
	private Map<Integer, String> getCompanyTypeMap() {
		/**
		 * 数据字典中的企业类型key
		 */
		String SYS_DICTIONARY_COMPANY_TYPE = "brandBusinessType";
		List<SysDictionary> companyTypeList = sysDictionaryService.listByType(SYS_DICTIONARY_COMPANY_TYPE);
		Map<Integer, String> companyTypeMap = null;
		if (!CollectionUtils.isEmpty(companyTypeList)) {
			companyTypeMap = companyTypeList.stream()
//					.filter(it -> it.getIsDeleted() != null && it.getIsDeleted() != 0)
					.collect(Collectors.toMap(SysDictionary::getValue, SysDictionary::getName));
		}
		return companyTypeMap;
	}

	/**
	 * 查询公司信息
	 *
	 * @param companyIdSet
	 * @return
	 */
	private Map<Long, BaseCompany> getCompanyMap(Set<Integer> companyIdSet) {
		Map<Long, BaseCompany> companyMap = null;
		if (companyIdSet != null && companyIdSet.size() > 0) {
			List<BaseCompany> baseCompanyList = baseCompanyService.listByIds(companyIdSet);
			if (!CollectionUtils.isEmpty(baseCompanyList)) {
				companyMap = baseCompanyList.stream().collect(Collectors.toMap(BaseCompany::getId, baseCompany -> baseCompany, (k1, k2) -> k1));
			}
		}
		return companyMap;
	}


	public static void main(String[] args) {
		int[] a1 = {1};
		int[] a2 = {2};
		System.out.println(findMedianSortedArrays(a1, a2));
	}

	private static double findMedianSortedArrays(int[] a1, int[] a2) {
		int l1 = a1.length;
		int l2 = a2.length;

		int p1 = 0;
		int p2 = 0;

		int total = l1 + l2;

		int[] tmpArray = new int[a1.length + a2.length];
		int currentIndex = 0;
		while (currentIndex <= total / 2) {


			if (l1 == p1 || l2 == p2) {
				currentIndex = -1;
			} else if (a1[p1] > a2[p2]) {
				tmpArray[currentIndex] = a2[p2];
				p2++;
			} else {
				tmpArray[currentIndex] = a1[p1];
				p1++;
			}

			// p1 bound
			if (p1 == l1) {
				while (currentIndex < total / 2) {
					tmpArray[++currentIndex] = a2[p2++];
				}
			}

			// p2 bound
			if (p2 == l2) {
				while (currentIndex < total / 2) {
					tmpArray[++currentIndex] = a1[p1++];
				}
			}


			if (currentIndex >= total / 2) {
				if (total % 2 == 0) {
					//偶数个
					return (tmpArray[currentIndex] + tmpArray[currentIndex - 1]) / 2D;

				} else {
					//奇数个
					return tmpArray[currentIndex];
				}
			}
			++currentIndex;
		}

		return -1D;
	}

}
