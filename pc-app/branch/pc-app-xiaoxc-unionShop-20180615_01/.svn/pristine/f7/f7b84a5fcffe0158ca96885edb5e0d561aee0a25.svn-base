package com.nork.product.service2.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.AuthorizedVO;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service2.AuthorizedConfigService;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.system.sms.httpclient.SmsClient;

@Service("authorizedConfigService2")
public class AuthorizedConfigServiceImpl implements AuthorizedConfigService {
	private static Logger logger = Logger.getLogger(AuthorizedConfigServiceImpl.class);

	// TODO:用有意义的声明
	private static final Integer VALIDSTATE_EXPIRED = 0;
	private static final Integer VALIDSTATE_1 = 1;
	private static final Integer VALIDSTATE_2 = 2;
	private static final Integer VALIDSTATE_3 = 3;
	private static final Integer STATE_USED = 1;
	private static final String CACH_ENABLE = "1";
	private static final String CACH_DISENABLE = "0";
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String NO_NUM = "序列号不存在";
	private static final String ADD_SUCCESS = "添加成功";
	private static final String NUM = "序列号";
	private static final String START_TIME = ",开始生效时间为：";
	private static final String BIND_FAIL = "绑定失败！";
	private static final String PLEASE_ADD_DATADIC_TO_ADMIN_1 = "请管理员添加数据字典:type:payModel;valueKey:dealersPay;name:经销商付费";
	private static final String PLEASE_ADD_DATADIC_TO_ADMIN_2 = "请管理员添加数据字典:type:payStatus;valueKey:payStatus_finish;name:已付款";
	private static final String PLEASE_ADD_DATADIC_TO_ADMIN_3 = "请管理员添加数据字典:type:payModel;valueKey:companyPay;name:企业付费";
	private static final String NUM_USE_IMPORT_OTHER_NUM = "序列号已使用！请输入其他序列号！";
	private static final String BRAND_ADD_OK_NOT_REPETITION = "已添加该品牌,无需重复添加";
	private static final String NUM_PWD_EORR = "序列号密码错误";
	private static final String NUM_NO_BELONG_USER_OPERATION_FAIL = "该序列号不归该用户所有,操作失败";
	private static final String MUST_ONE_BRAND_DEL_FAIL = "必须保留至少一条授权品牌,删除失败";
	private static final String NOT_FIND_USER = "未找到该用户!";
	private static final String NUM_NO_PAID_CLICK_DREDGE = "该序列号还没有支付，请点击购买开通！";
	private static final String NUM_EORR_PLEASE_CHECK_NUM = "序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据";
	private static final String USER_NOT_IN = "用户不存在";
	private static final String NUMDATA_EORR_LACK_VALUE = "序列号数据出错,有效天数缺少值";
	private static final String MSGID_NOT_NULL = "参数msgId不能为空";
	private static final String USERID_NOT_NULL = "参数userId不能为空";
	private static final String TERMINALIMEI_NOT_NULL = "参数terminalImei不能为空";
	private static final String AUTHORIZEDCODE_NOT_NULL = "参数authorizedCode不能为空";
	private static final String PWD_NOT_NULL = "参数password不能为空";
	private static final String STATUS_NOT_NULL = "参数status不能为空";
	private static final String STATUS_ONLY_ZERO_OR_ONE = "参数status只能填0或1";
	private static final String FALSE = "false";
	private static final String TRUE = "true";
	private static final String MSG = "msg";
	private static final String LACK_ID = "缺少参数：id";
	private static final String LACK_MSGID = "缺少参数：msgId";
	private static final String NUM_OPERATE_FAIL_CALL_SERVICE = "授权码操作失败，请联系客服";
	private static final String DREDGE_SUCCESS = "开通成功";
	private static final String DREDGE_FAIL = "开通失败";
	private static final String RENEW_SUCCESS = "续费成功";
	private static final String RENEW_FAIL = "续费失败";
	private static final String NUM_THEN_USE = "该序列号已被使用";
	private static final String ONLY_BIND_DEALER_PAID_NUM = "只能绑定经销商付费的序列号";
	private static final String U_DREDGE_COMPANY_BRAND_SO_NOT_PAID = "您已经开通了该公司的其他品牌，所以无需再次支付。";
	private static final String NUM_HAS_PAID_IMPORT_PWD_DREDGE = "该序列号已经通过企业付费，请直接输入密码开通！";
	private static final String CHECK_DIC_NO_DESIGN_COMPANY_PLEASE_ADD = "------检测到数据字典没有\"设计公司\"这条数据字典,type=brandBusinessType,valuekey=designCompany,请管理员添加";
	private static final String SEND_CHECKMSG_ERROR = "发送验证短信异常！";
	private static final String SEND_SUCCESS = "发送成功！";
	private static final String UNITPRICE = "unitPrice";
	@Autowired
	private AuthorizedConfigMapper authorizedConfigMapper;

	@Autowired
	private BaseBrandService baseBrandService;

	@Autowired
	private ResPicService resPicService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public ResponseEnvelope<AuthorizedConfig> authorizedBrandList(AuthorizedConfigSearch authorizedConfigSearch,
			LoginUser loginUser) {
		int validity = 0;
		List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
		List<Integer> updateIds = new ArrayList<Integer>();
		authorizedConfigSearch.setTerminalImei(null);
		authorizedConfigSearch.setUserId(loginUser.getId());// 当前厂商
		authorizedConfigSearch.setState(STATE_USED);// 已使用
		list = getPaginatedList(authorizedConfigSearch);
		for (AuthorizedConfig authorizedConfig : list) {
			Date validTime = authorizedConfig.getValidTime();
			Date currentTime = new Date();
			if (validTime != null) {
				if (!validTime.after(currentTime)) {// 有效时间在当前时间之前，说明已经过期
					authorizedConfig.setValidState(VALIDSTATE_EXPIRED);
					updateIds.add(authorizedConfig.getId());
				}
			} else {
				// 有效时间在当前时间之前，说明已经过期
				authorizedConfig.setValidState(VALIDSTATE_EXPIRED);
				updateIds.add(authorizedConfig.getId());
			}
		}
		// 把需要update 的数据一次性update
		if (updateIds.size() > 0) {
			Integer validState = 0;
			authorizedConfigMapper.updateValidStateByIds(validState, updateIds);// 批量设置为已过期
		}

		// 进入添加品牌列表,能看到 所有过期和未过期的 序列号
		// TODO:判断我们是否每次需要先得到总数？
		authorizedConfigSearch.setValidState(VALIDSTATE_3);
		int totalValidSN = getCount(authorizedConfigSearch);
		List<AuthorizedConfig> validList = getPaginatedList(authorizedConfigSearch);
		validList = resolveValidateList(validList);
		// 验证完之后，再次查询未过期的序列号
		authorizedConfigSearch.setValidState(VALIDSTATE_1);
		int totalState = getCount(authorizedConfigSearch);
		ResponseEnvelope<AuthorizedConfig> RES = new ResponseEnvelope<AuthorizedConfig>(totalValidSN, validList,
				authorizedConfigSearch.getMsgId());
		if (totalState > 0) {
			validity = 1;
		}
		RES.setObj(validity);
		return RES;
	}

	private List<AuthorizedConfig> resolveValidateList(List<AuthorizedConfig> validList) {
		String cacheEnableV = Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE, CACH_DISENABLE);
		for (AuthorizedConfig authorizedConfig : validList) {
			if (StringUtils.isNotBlank(authorizedConfig.getBrandIds())) {
				String brandArr[] = authorizedConfig.getBrandIds().split(",");
				List<BaseBrand> blist = new ArrayList<BaseBrand>();
				for (String bid : brandArr) {
					BaseBrand baseBrand = null;
					if (StringUtils.equals(CACH_ENABLE, cacheEnableV)) {
						baseBrand = BaseBrandCacher.get(Utils.getIntValue(bid));
					} else {
						baseBrand = baseBrandService.get(Utils.getIntValue(bid));
					}
					if (baseBrand != null) {
						ResPic resPic = null;
						if (baseBrand.getBrandLogo() != null && StringUtils.isNumeric(baseBrand.getBrandLogo())) {
							resPic = resPicService.get(Integer.parseInt(baseBrand.getBrandLogo()));
						}
						if (resPic != null) {
							baseBrand.setBrandLogo(resPic.getPicPath());
						}
						blist.add(baseBrand);
					}
				}
				authorizedConfig.setList(blist);
			}
			String successFlag = SUCCESS;
			// 至于使用可开通才能进 活动判断
			if (authorizedConfig.getAuthorizedCodeType() != null
					&& authorizedConfig.getAuthorizedCodeType().intValue() == 1 && authorizedConfig.getIsOpen() != null
					&& authorizedConfig.getIsOpen().intValue() == 1) {
				// 判断当前时间是否在 活动时间内 ,条件成立返回价格 并填充价格。
				Map<String, String> checkActivityMap = checkActivity(authorizedConfig);
				if (checkActivityMap == null || checkActivityMap.size() <= 0) {
					successFlag = FAIL;
				} else {
					successFlag = checkActivityMap.get("success");
					if (StringUtils.isNotBlank(checkActivityMap.get(UNITPRICE))) {
						BigDecimal bigDecimal = new BigDecimal(checkActivityMap.get(UNITPRICE));
						BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
						BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
						BigDecimal bigDecimal_ = new BigDecimal(authorizedConfig.getUnitPrice() + "");
						BigDecimal bigDecimal2_ = new BigDecimal(Double.toString(100));
						BigDecimal bigDecimalMultiply_ = bigDecimal_.multiply(bigDecimal2_);
						authorizedConfig.setOriginalPrice(bigDecimalMultiply_.doubleValue());
						authorizedConfig.setUnitPrice(bigDecimalMultiply.doubleValue());
					}
				}
			} else {
				successFlag = FAIL;
			}
			if (FAIL.equals(successFlag)) {
				if (authorizedConfig.getUnitPrice() != null) {
					BigDecimal bigDecimal = new BigDecimal(Double.toString(authorizedConfig.getUnitPrice()));
					BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
					BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
					authorizedConfig.setUnitPrice(bigDecimalMultiply.doubleValue());
					authorizedConfig.setActivityIds(null);
				}
			}
		}
		return validList;
	}

	/**
	 * 所有数据
	 * 
	 * @param authorizedConfig
	 * @return List<AuthorizedConfig>
	 */
	@Override
	public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig) {
		if (authorizedConfig.getValidState() == null) {
			authorizedConfig.setValidState(1);
		}
		if (authorizedConfig.getValidState() != null && authorizedConfig.getValidState() == 3) {// 等于3则查询所有
			authorizedConfig.setValidState(null);
		}
		return authorizedConfigMapper.selectList(authorizedConfig);
	}

	/**
	 * 所有数据(mobile) add by xiaozp
	 * 
	 * @param authorizedConfig
	 * @return List<AuthorizedConfig>
	 */
	@Override
	public List<AuthorizedConfig> getListByMobile(AuthorizedConfig authorizedConfig) {
		if (authorizedConfig.getValidState() == null) {
			authorizedConfig.setValidState(1);
		}
		if (authorizedConfig.getValidState() != null && authorizedConfig.getValidState() == 3) {// 等于3则查询所有
			authorizedConfig.setValidState(null);
		}
		return authorizedConfigMapper.selectListByMobile(authorizedConfig);
	}

	/**
	 * 获取数据数量
	 *
	 * @param authorizedConfigSearch
	 * @return int
	 */
	@Override
	public int getCount(AuthorizedConfigSearch authorizedConfigSearch) {
		if (authorizedConfigSearch.getValidState() == null) {
			authorizedConfigSearch.setValidState(1);
		}
		if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 3) {// 等于3则查询所有
			authorizedConfigSearch.setValidState(null);
		}
		return authorizedConfigMapper.selectCount(authorizedConfigSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param authorizedConfigSearch
	 * @return List<AuthorizedConfig>
	 */
	@Override
	public List<AuthorizedConfig> getPaginatedList(AuthorizedConfigSearch authorizedConfigSearch) {
		if (authorizedConfigSearch.getValidState() == null) {
			authorizedConfigSearch.setValidState(1);
		}
		if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 3) {// 等于3则查询所有
			authorizedConfigSearch.setValidState(null);
		}
		if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 4) {// 等于4则查询
																											// 试用的和未过期的
			authorizedConfigSearch.setValidState(4);
		}
		return authorizedConfigMapper.selectPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 验证修改序列号状态接口的参数
	 * 
	 * @author huangsongbo
	 * @param msgId
	 *            msgId
	 * @param userId
	 *            用户id
	 * @param terminalImei
	 *            设备IMEI
	 * @param authorizedCode
	 *            序列号
	 * @param password
	 *            密码
	 * @param status
	 *            状态值(只能填0,1)
	 * @return
	 */
	public Map<String, String> VerifyParams(String msgId, Integer userId, String terminalImei, String authorizedCode,
			String password, Integer status) {
		String success = FALSE;
		String msg = "";
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(msgId)) {
			msg = MSGID_NOT_NULL;
		} else if (userId == null) {
			msg = USERID_NOT_NULL;
		} else if (StringUtils.isBlank(terminalImei)) {
			msg = TERMINALIMEI_NOT_NULL;
		} else if (StringUtils.isBlank(authorizedCode)) {
			msg = AUTHORIZEDCODE_NOT_NULL;
		} else if (StringUtils.isBlank(password)) {
			msg = PWD_NOT_NULL;
		} else if (status == null) {
			msg = STATUS_NOT_NULL;
		} else if (!(0 == status || 1 == status)) {
			msg = STATUS_ONLY_ZERO_OR_ONE;
		} else {
			success = TRUE;
		}
		map.put(SUCCESS, success);
		map.put(MSG, msg);
		return map;
	}

	/**
	 * 更新数据
	 *
	 * @param authorizedConfig
	 * @return int
	 */
	@Override
	public int update(AuthorizedConfig authorizedConfig) {
		return authorizedConfigMapper.updateByPrimaryKeySelective(authorizedConfig);
	}

	/**
	 * 新增数据
	 *
	 * @param authorizedConfig
	 * @return int
	 */
	@Override
	public int add(AuthorizedConfig authorizedConfig) {
		authorizedConfigMapper.insertSelective(authorizedConfig);
		return authorizedConfig.getId();
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return authorizedConfigMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return AuthorizedConfig
	 */
	@Override
	public AuthorizedConfig getAuthorizedConfig(Integer id) {
		return authorizedConfigMapper.selectByPrimaryKey(id);
	}

	/**
	 * 通过userId和companyId查找AuthorizedConfig
	 * 
	 * @param userId
	 * @param companyId
	 */

	@Override
	public List<AuthorizedConfig> findAllByUserIdAndCompanyId(Integer userId, String companyId) {
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		authorizedConfigSearch.setCompanyId(companyId);
		return getPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 查找该用户绑定的序列号
	 * 
	 * @param userId
	 * @return
	 */
	public List<AuthorizedConfig> findAllByUserId(Integer userId) {
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		return getPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 时间校验 判断当前时间是否在 活动时间内 ,条件成立返回价格 并填充价格。
	 * 
	 * @param att1
	 * @param att1Info
	 * @return
	 */
	public Map<String, String> checkActivity(AuthorizedConfig authorizedConfig) {
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put(SUCCESS, FALSE);
		try {
			Date date = new Date();
			Date startTime = authorizedConfig.getStartTime(); // 激活时间
			String unitPrice = null;
			if (StringUtils.isNotBlank(authorizedConfig.getActivityIds())) {
				SysDictionary sysDictionary = sysDictionaryService
						.get(Integer.parseInt(authorizedConfig.getActivityIds()));
				String att = "";
				String attInfo = "";
				if (sysDictionary != null) {
					if (StringUtils.isNotBlank(sysDictionary.getAtt1())
							&& StringUtils.isNotBlank(sysDictionary.getAtt1Info())) {
						att = sysDictionary.getAtt1();
						attInfo = sysDictionary.getAtt1Info();
					} else if (StringUtils.isNotBlank(sysDictionary.getAtt2())
							&& StringUtils.isNotBlank(sysDictionary.getAtt2Info())) {
						att = sysDictionary.getAtt2();
						attInfo = sysDictionary.getAtt2Info();
					} else if (StringUtils.isNotBlank(sysDictionary.getAtt3())
							&& StringUtils.isNotBlank(sysDictionary.getAtt3Info())) {
						att = sysDictionary.getAtt3();
						attInfo = sysDictionary.getAtt3Info();
					} else if (StringUtils.isNotBlank(sysDictionary.getAtt4())
							&& StringUtils.isNotBlank(sysDictionary.getAtt4Info())) {
						att = sysDictionary.getAtt4();
						attInfo = sysDictionary.getAtt4Info();
					} else if (StringUtils.isNotBlank(sysDictionary.getAtt5())
							&& StringUtils.isNotBlank(sysDictionary.getAtt5Info())) {
						att = sysDictionary.getAtt5();
						attInfo = sysDictionary.getAtt5Info();
					} else if (StringUtils.isNotBlank(sysDictionary.getAtt6())
							&& StringUtils.isNotBlank(sysDictionary.getAtt6Info())) {
						att = sysDictionary.getAtt6();
						attInfo = sysDictionary.getAtt6Info();
					} else if (StringUtils.isNotBlank(sysDictionary.getAtt7())
							&& StringUtils.isNotBlank(sysDictionary.getAtt7Info())) {
						att = sysDictionary.getAtt7();
						attInfo = sysDictionary.getAtt7Info();
					}

					if (StringUtils.isNotBlank(att) && StringUtils.isNotBlank(attInfo)) {
						// 数据校验--正常数据类型为:att1 = 1-7,att1Info = 600;
						Map<String, String> map = checkParam(att, attInfo);
						if ("true".equals(map.get("success"))) {
							Integer startTimeNum = null;
							Integer endTimeNum = null;
							Date activityStartTime = null;
							Date activityEndTime = null;
							if ("1".equals(map.get("startTimeNum"))) {
								activityStartTime = startTime;
							} else {
								startTimeNum = Integer.parseInt(map.get("startTimeNum"));
								activityStartTime = Utils.getDateAfter(startTime, startTimeNum);
							}
							if ("1".equals(map.get("endTimeNum"))) {
								activityEndTime = startTime;
							} else {
								endTimeNum = Integer.parseInt(map.get("endTimeNum"));
								activityEndTime = Utils.getDateAfter(startTime, endTimeNum + 1);
							}
							if (!activityStartTime.after(date) && activityEndTime.after(date)) {
								activityDetailsAssignment(sysDictionary, authorizedConfig);
								unitPrice = map.get("price");
								// authorizedConfig.setUnitPrice(Double.parseDouble(unitPrice));
								resMap.put("success", "true");
								resMap.put(UNITPRICE, unitPrice);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			resMap.put(SUCCESS, FALSE);
		}
		return resMap;
	}

	public Map<String, String> checkParam(String att1, String att1Info) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (StringUtils.isBlank(att1) && StringUtils.isBlank(att1Info)) {
			resMap.put(SUCCESS, FALSE);
			return resMap;
		}
		if (att1.indexOf("-") <= -1) { // 如果是-1，也不在活动时间内
			resMap.put(SUCCESS, FALSE);
			return resMap;
		}
		String startTimeNum = att1.substring(0, att1.indexOf("-"));
		String endTimeNum = att1.split("-")[1];

		try {
			Integer.parseInt(startTimeNum);
			Integer.parseInt(endTimeNum);
			Integer.parseInt(att1Info);
			resMap.put(SUCCESS, TRUE);
			resMap.put("startTimeNum", startTimeNum);
			resMap.put("endTimeNum", endTimeNum);
			resMap.put("price", att1Info);
			return resMap;
		} catch (Exception e) {
			resMap.put(SUCCESS, FALSE);
			return resMap;
		}
	}

	/**
	 * 活动详情 赋值
	 * 
	 * @param sysDictionary
	 * @param authorizedConfig
	 */
	public void activityDetailsAssignment(SysDictionary sysDictionary, AuthorizedConfig authorizedConfig) {
		StringBuffer activityDetails = new StringBuffer("");
		if (StringUtils.isNotBlank(sysDictionary.getAtt1()) && StringUtils.isNotBlank(sysDictionary.getAtt1Info())) {
			activityDetails.append(sysDictionary.getAtt1() + "天内" + sysDictionary.getAtt1Info() + "元，");
		}
		if (StringUtils.isNotBlank(sysDictionary.getAtt2()) && StringUtils.isNotBlank(sysDictionary.getAtt2Info())) {
			activityDetails.append(sysDictionary.getAtt2() + "天内" + sysDictionary.getAtt2Info() + "元，");
		}
		if (StringUtils.isNotBlank(sysDictionary.getAtt3()) && StringUtils.isNotBlank(sysDictionary.getAtt3Info())) {
			activityDetails.append(sysDictionary.getAtt3() + "天内" + sysDictionary.getAtt3Info() + "元，");
		}
		if (StringUtils.isNotBlank(sysDictionary.getAtt4()) && StringUtils.isNotBlank(sysDictionary.getAtt4Info())) {
			activityDetails.append(sysDictionary.getAtt4() + "天内" + sysDictionary.getAtt4Info() + "元,");
		}
		if (StringUtils.isNotBlank(sysDictionary.getAtt5()) && StringUtils.isNotBlank(sysDictionary.getAtt5Info())) {
			activityDetails.append(sysDictionary.getAtt5() + "天内" + sysDictionary.getAtt5Info() + "元，");
		}
		if (StringUtils.isNotBlank(sysDictionary.getAtt6()) && StringUtils.isNotBlank(sysDictionary.getAtt6Info())) {
			activityDetails.append(sysDictionary.getAtt6() + "天内" + sysDictionary.getAtt6Info() + "元，");
		}
		if (StringUtils.isNotBlank(sysDictionary.getAtt7()) && StringUtils.isNotBlank(sysDictionary.getAtt7Info())) {
			activityDetails.append(sysDictionary.getAtt7() + "天内" + sysDictionary.getAtt7Info() + "元，");
		}
		authorizedConfig.setActivityDetails(activityDetails.toString());
	}

	/***
	 * 验证序列号状态
	 * 
	 * @param authorizedConfigs
	 * @param loginUser
	 * @param authorizedVO
	 * @return
	 */
	public Map<String, String> checkAuthorizedConfigStatus(List<AuthorizedConfig> authorizedConfigs,
			LoginUser loginUser, AuthorizedVO authorizedVO) {
		Map<String, String> reslutMap = new HashMap<String, String>();
		reslutMap.put(SUCCESS, TRUE);
		reslutMap.put(MSG, ADD_SUCCESS);
		reslutMap = this.VerifyParams(authorizedVO.getMsgId(), loginUser.getId(), authorizedVO.getTerminalImei(),
				authorizedVO.getAuthorizedCode(), authorizedVO.getPassword(), authorizedVO.getStatus());
		if (StringUtils.equals(FALSE, reslutMap.get(SUCCESS))) {
			// 参数不符合规范
			return reslutMap;
		}
		if (authorizedConfigs == null || authorizedConfigs.size() < 1) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NO_NUM);
			return reslutMap;
		}
		AuthorizedConfig authorizedConfig = authorizedConfigs.get(0);
		// 用户想添加 自己删除过的授权码
		if (authorizedConfig.getUserId() != null) {
			if (authorizedConfig.getState().intValue() == 0
					&& loginUser.getId().intValue() == authorizedConfig.getUserId().intValue()) {
				AuthorizedConfig authorizedConfig_new = new AuthorizedConfig();
				authorizedConfig_new.setId(authorizedConfig.getId());
				authorizedConfig_new.setState(1);
				this.update(authorizedConfig_new);
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, ADD_SUCCESS);
				return reslutMap;
			}
		}
		Date startTime = authorizedConfig.getStartTime();
		if (startTime != null) {
			if (startTime.after(new Date())) {
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				String startTimeStr = sim.format(startTime);
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, NUM + authorizedConfig.getAuthorizedCode() + START_TIME + startTimeStr + BIND_FAIL);
				return reslutMap;
			}
		}
		return reslutMap;
	}

	/***
	 * 添加序列号
	 * 
	 * @param authorizedConfig
	 * @param loginUser
	 * @param terminalImei
	 * @return
	 */
	public Map<String, String> addAuthorizedConfigNum(AuthorizedConfig authorizedConfig, LoginUser loginUser,
			String terminalImei) {
		Integer userId = loginUser.getId();
		Map<String, String> reslutMap = new HashMap<String, String>();
		reslutMap.put(SUCCESS, TRUE);
		reslutMap.put(MSG, ADD_SUCCESS);
		// 判定为经销商付费的序列号
		// 检测是否已经绑定了同公司的序列号
		List<AuthorizedConfig> list = this.findAllByUserIdAndCompanyId(userId, authorizedConfig.getCompanyId());
		if (list != null && list.size() > 0) {
			// 验证是否被使用
			if ("1".equals(authorizedConfig.getState())) {
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, NUM_USE_IMPORT_OTHER_NUM);
				return reslutMap;
			}
			if (authorizedConfig.getUserId() != null && authorizedConfig.getUserId() > 0
					&& !(authorizedConfig.getUserId().equals(userId))) {
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, NUM_USE_IMPORT_OTHER_NUM);
				return reslutMap;
			}
			// 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定
			String companyIdStr = authorizedConfig.getCompanyId();
			String brandIdsStr = authorizedConfig.getBrandIds();
			for (AuthorizedConfig authorizedConfig2 : list) {
				if (StringUtils.equals(companyIdStr, authorizedConfig2.getCompanyId())
						&& StringUtils.equals(brandIdsStr, authorizedConfig2.getBrandIds()))
					reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, BRAND_ADD_OK_NOT_REPETITION);
				return reslutMap;
			}
			// 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定end
			// 用户绑定设备号->end
			Map<String, String> resMap = null;
			resMap = authorizedConfigActivation(authorizedConfig, loginUser, "no");
			if (resMap != null) {
				if (TRUE.equals(resMap.get(SUCCESS))) {
					authorizedConfig.setUserId(userId);
					authorizedConfig.setState(1);
					authorizedConfig.setTerminalImei(terminalImei);
					this.update(authorizedConfig);
					AuthorizedConfigCacher.remove(authorizedConfig.getId());
				} else {
					reslutMap.put(SUCCESS, FALSE);
					reslutMap.put(MSG, resMap.get("data"));
					return reslutMap;
				}
			} else {
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, BIND_FAIL);
				return reslutMap;
			}
			// 允许直接绑定
			authorizedConfig.setUserId(userId);
			authorizedConfig.setState(1);
			authorizedConfig.setTerminalImei(terminalImei);
			this.update(authorizedConfig);
			AuthorizedConfigCacher.remove(authorizedConfig.getId());
			reslutMap.put(SUCCESS, TRUE);
			reslutMap.put(MSG, SystemCommonConstant.BIND_SUCCESS);
			return reslutMap;
		}
		return reslutMap;
	}

	/***
	 * 解绑
	 * 
	 * @param userId
	 * @param authorizedConfig
	 * @return
	 */
	public Map<String, String> unbindNum(Integer userId, String authorizedCode, AuthorizedConfig authorizedConfig) {
		Map<String, String> reslutMap = new HashMap<String, String>();
		reslutMap.put(SUCCESS, TRUE);
		reslutMap.put(MSG, SystemCommonConstant.RELIEVE_SUCCESS);
		// 取消绑定
		if (authorizedConfig.getUserId() == null || !userId.equals(authorizedConfig.getUserId())) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NUM_NO_BELONG_USER_OPERATION_FAIL);
			return reslutMap;
		}
		// 识别该用户有多少条序列号->只剩最后一条->解绑失败
		AuthorizedConfigSearch authorizedConfigSearch2 = new AuthorizedConfigSearch();
		authorizedConfigSearch2.setUserId(userId);
		authorizedConfigSearch2.setState(1);
		authorizedConfigSearch2.setStart(0);
		authorizedConfigSearch2.setLimit(2);
		List<AuthorizedConfig> authorizedConfigs2 = this.getPaginatedList(authorizedConfigSearch2);
		if (authorizedConfigs2 != null && authorizedConfigs2.size() == 1) {
			if (authorizedConfigs2.get(0).getAuthorizedCode().equals(authorizedCode)) {
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, MUST_ONE_BRAND_DEL_FAIL);
				return reslutMap;
			}
		}
		// 识别该用户有多少条序列号->只剩最后一条->解绑失败->end
		authorizedConfig.setState(0);
		// authorizedConfig.setTerminalImei("");
		this.update(authorizedConfig);
		AuthorizedConfigCacher.remove(authorizedConfig.getId());
		return reslutMap;
	}

	/***
	 * 绑定
	 * 
	 * @param userId
	 * @param authorizedConfig
	 * @return
	 */
	public Map<String, String> bindNum(AuthorizedConfig authorizedConfig, LoginUser loginUser, String terminalImei)
			throws Exception {
		Map<String, String> reslutMap = new HashMap<String, String>();
		reslutMap.put(SUCCESS, TRUE);
		reslutMap.put(MSG, SystemCommonConstant.BIND_SUCCESS);
		Integer userId = loginUser.getId();
		// 绑定序列号
		// 识别该序列号是否已经绑定
		if ("1".equals(authorizedConfig.getState())) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NUM_USE_IMPORT_OTHER_NUM);
			return reslutMap;
		}
		// state不为1且userId还保存->识别是不是该用户以前删除的序列号
		if (authorizedConfig.getUserId() != null && authorizedConfig.getUserId() > 0
				&& !(authorizedConfig.getUserId().equals(userId))) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NUM_USE_IMPORT_OTHER_NUM);
			return reslutMap;
		}
		// 用户绑定设备号
		SysUser sysUser2 = sysUserService.get(loginUser.getId());
		if (sysUser2 == null || sysUser2.getId() == null) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NOT_FIND_USER);
			throw new RuntimeException(NOT_FIND_USER);
			// return reslutMap;
		}
		// 识别是否支付
		SysDictionary sysDictionaryPayStatusFinish = sysDictionaryService.findOneByTypeAndValueKey("payStatus",
				"payStatus_finish");
		if (sysDictionaryPayStatusFinish == null) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, PLEASE_ADD_DATADIC_TO_ADMIN_2);// 已付款
			throw new RuntimeException(PLEASE_ADD_DATADIC_TO_ADMIN_2);
		}
		if (authorizedConfig.getPayStatusValue() != sysDictionaryPayStatusFinish.getValue()) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NUM_NO_PAID_CLICK_DREDGE);
			return reslutMap;
		}
		// 识别是否支付->end
		// 判定添加第2条以上的序列号,不能为企业支付类型的序列号
		List<AuthorizedConfig> list = this.findAllByUserId(userId);
		// 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定
		String companyIdStr = authorizedConfig.getCompanyId();
		String brandIdsStr = authorizedConfig.getBrandIds();
		for (AuthorizedConfig authorizedConfig2 : list) {
			if (StringUtils.equals(companyIdStr, authorizedConfig2.getCompanyId())
					&& StringUtils.equals(brandIdsStr, authorizedConfig2.getBrandIds()))
				reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, BRAND_ADD_OK_NOT_REPETITION);
			return reslutMap;
		}
		// 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定->end
		// 判定添加第2条以上的序列号,不能为企业支付类型的序列号->end
		// 用户绑定设备号->end
		Map<String, String> resMap = null;
		resMap = authorizedConfigActivation(authorizedConfig, loginUser, "no");
		if (resMap != null) {
			if (TRUE.equals(resMap.get(SUCCESS))) {
				authorizedConfig.setUserId(userId);
				authorizedConfig.setState(1);
				authorizedConfig.setTerminalImei(terminalImei);
				this.update(authorizedConfig);
				AuthorizedConfigCacher.remove(authorizedConfig.getId());
			} else {
				reslutMap.put(SUCCESS, FALSE);
				reslutMap.put(MSG, resMap.get("data"));
				return reslutMap;
			}
		} else {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, BIND_FAIL);
			return reslutMap;
		}
		if (StringUtils.isNotBlank(sysUser2.getUserImei())) {
			if (!StringUtils.equals(terminalImei, sysUser2.getUserImei()))
				// return new
				// RuntimeException("序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据");
				reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NUM_EORR_PLEASE_CHECK_NUM);
			return reslutMap;
		} else {
			// 绑定设备号
			sysUser2.setUserImei(terminalImei);
			sysUserService.update(sysUser2);
		}

		return reslutMap;
	}

	/***
	 * 增加 、绑定、解绑
	 * 
	 * @param loginUser
	 * @param authorizedVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> updateAuthorizedConfigStatus(LoginUser loginUser, AuthorizedVO authorizedVO)
			throws Exception {
		// 根据参数验证序列号是否正确
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setValidState(3);// 等于3则查询 所有状态
		authorizedConfigSearch.setLimit(1);
		authorizedConfigSearch.setStart(0);
		authorizedConfigSearch.setAuthorizedCode(authorizedVO.getAuthorizedCode());
		List<AuthorizedConfig> authorizedConfigs = null;
		authorizedConfigs = this.getPaginatedList(authorizedConfigSearch);
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		if (authorizedConfigs.size() > 0) {
			authorizedConfig = authorizedConfigs.get(0);
		}
		Map<String, String> reslutMap = checkAuthorizedConfigStatus(authorizedConfigs, loginUser, authorizedVO);
		if (StringUtils.equals(FALSE, reslutMap.get(SUCCESS))) {
			// 参数不符合规范
			return reslutMap;
		}
		// get经销商付费sysDictionary
		SysDictionary sysDictionaryDealersPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
		if (sysDictionaryDealersPay == null)
			reslutMap.put(SUCCESS, FALSE);
		reslutMap.put(MSG, PLEASE_ADD_DATADIC_TO_ADMIN_1);// 经销商付费
		if (1 == authorizedVO.getStatus()
				&& sysDictionaryDealersPay.getValue().equals(authorizedConfigs.get(0).getPayModelValue())) {
			return addAuthorizedConfigNum(authorizedConfig, loginUser, authorizedVO.getTerminalImei());
		}
		if (!StringUtils.equals(authorizedVO.getPassword(), authorizedConfig.getPassword())) {
			reslutMap.put(SUCCESS, FALSE);
			reslutMap.put(MSG, NUM_PWD_EORR);
			return reslutMap;
		}
		if (0 == authorizedVO.getStatus()) {
			return unbindNum(loginUser.getId(), authorizedVO.getAuthorizedCode(), authorizedConfig);
		} else {
			return bindNum(authorizedConfig, loginUser, authorizedVO.getTerminalImei());
		}
	}

	/**
	 * 授权码激活
	 * 
	 * @param authorizedConfig
	 * @param loginUser
	 * @return
	 */
	public Map<String, String> authorizedConfigActivation(AuthorizedConfig authorizedConfig, LoginUser loginUser,
			String purchase) {
		Map<String, String> resMap = new HashMap<String, String>();
		// 激活授权码 - 关于新需求 购买时激活
		try {
			Date startTime = new Date();
			authorizedConfig.setStartTime(startTime);
			// 能使用的天数
			Integer validDay = authorizedConfig.getValidDay();
			if (validDay == null || validDay.intValue() <= 0) {
				resMap.put("data", NUMDATA_EORR_LACK_VALUE);
				resMap.put(SUCCESS, FALSE);
				return resMap;
			}
			// 判断是否存在销售延期缩短有效期的策略
			if ("yes".equals(purchase)) {
				String validStrategy = authorizedConfig.getValidStrategy();
				if (StringUtils.isNotBlank(validStrategy)) {
					if (validStrategy.indexOf("-") > -1) {
						validStrategy = validStrategy.replace("-", "");
						Integer dayNum = Integer.parseInt(validStrategy);
						validDay = validDay.intValue() - dayNum.intValue();
						Date validTime = Utils.getDateAfter(new Date(), validDay);
						authorizedConfig.setValidTime(validTime);
					} else {
						Integer dayNum = Integer.parseInt(validStrategy);
						validDay = validDay.intValue() + dayNum.intValue();
						Date validTime = Utils.getDateAfter(new Date(), validDay);
						authorizedConfig.setValidTime(validTime);
					}
				} else {
					Date validTime = Utils.getDateAfter(new Date(), validDay);
					authorizedConfig.setValidTime(validTime);
				}
			} else {
				Date validTime = Utils.getDateAfter(new Date(), validDay);
				authorizedConfig.setValidTime(validTime);
			}
			/*
			 * 第1次绑定赠送 只有在未使用状态 进行绑定的时候赠送预充值 活动预充值
			 */
			if (authorizedConfig.getState() != null && authorizedConfig.getState().intValue() == 0) {
				String priceStrategyStr = authorizedConfig.getPriceStrategy();
				if (priceStrategyStr != null && !"".equals(priceStrategyStr)) {
					SysUser sysUser = sysUserService.get(loginUser.getId());
					if (sysUser != null) {
						Double balanceAmount = sysUser.getBalanceAmount();
						Double priceStrategy = (Double.parseDouble(priceStrategyStr) * 100) + balanceAmount;
						sysUser.setBalanceAmount(priceStrategy);
						sysUser.setId(sysUser.getId());
						sysUserService.update(sysUser);
					} else {
						resMap.put("data", USER_NOT_IN);
						resMap.put(SUCCESS, FALSE);
						return resMap;
					}
				}
			}
		} catch (Exception e) {
			logger.error("authorizedConfigActivation:" + e);
			resMap.put("data", "authorizedConfigActivation:" + e);
			resMap.put(SUCCESS, FALSE);
			return resMap;
		}
		resMap.put(SUCCESS, TRUE);
		return resMap;
	}

	/**
	 * 将List添加到Set中
	 * 
	 * @author huangsongbo
	 * @param brandIdList
	 * @param brandIds
	 */
	private void setAddList(Set<String> brandIdList, List<String> brandIds) {
		brandIdList.addAll(brandIds);
	}

	/**
	 * 特殊格式String(200,100,850)转化为List
	 * 
	 * @author huangsongbo
	 * @param str
	 * @return
	 */
	private List<String> stringToList(String str) {
		if (StringUtils.isBlank(str))
			return new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		String[] strs = str.split(",");
		for (String value : strs) {
			list.add(value);
		}
		return list;
	}

	/**
	 * 购买序列号
	 * 
	 * @param msgId
	 * @param terminalImei
	 * @param authorizedCode
	 * @param
	 * @return
	 */
	public Object binding(String msgId, String terminalImei, String authorizedCode, LoginUser loginUser) {
		Integer userId = loginUser.getId();
		if (StringUtils.isBlank(terminalImei))
			return new ResponseEnvelope<>(false, TERMINALIMEI_NOT_NULL, msgId);
		if (StringUtils.isBlank(authorizedCode))
			return new ResponseEnvelope<>(false, AUTHORIZEDCODE_NOT_NULL, msgId);
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setLimit(1);
		authorizedConfigSearch.setStart(0);
		authorizedConfigSearch.setAuthorizedCode(authorizedCode);
		List<AuthorizedConfig> authorizedConfigs = this.getPaginatedList(authorizedConfigSearch);
		if (authorizedConfigs == null || authorizedConfigs.size() < 1)
			return new ResponseEnvelope<>(false, NO_NUM, msgId);
		AuthorizedConfig authorizedConfig = authorizedConfigs.get(0);
		// get经销商付费sysDictionary
		SysDictionary sysDictionaryDealersPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
		if (sysDictionaryDealersPay == null)
			return new RuntimeException(PLEASE_ADD_DATADIC_TO_ADMIN_1);// 经销商付费
		if (sysDictionaryDealersPay.getValue().equals(authorizedConfig.getPayModelValue())) {
			// 判定为经销商付费的序列号
			// 识别是否支付
			SysDictionary sysDictionaryPayStatusFinish = sysDictionaryService.findOneByTypeAndValueKey("payStatus",
					"payStatus_finish");
			if (sysDictionaryPayStatusFinish == null)
				return new RuntimeException(PLEASE_ADD_DATADIC_TO_ADMIN_2);// 已付款
			if (!authorizedConfig.getPayStatusValue().equals(sysDictionaryPayStatusFinish.getValue()))
				return new ResponseEnvelope<>(false, NUM_NO_PAID_CLICK_DREDGE, msgId);// 未支付
			// 识别是否支付->end
			// 验证是否被使用
			if (1 == (authorizedConfig.getState()))
				return new ResponseEnvelope<>(false, NUM_USE_IMPORT_OTHER_NUM, msgId);
			// 账户与设备绑定
			SysUser sysUser2 = sysUserService.get(loginUser.getId());
			if (sysUser2 == null || sysUser2.getId() == null)
				return new RuntimeException(NOT_FIND_USER);
			if (StringUtils.isNotBlank(sysUser2.getUserImei())) {
				if (!StringUtils.equals(terminalImei, sysUser2.getUserImei()))
					// return new
					// RuntimeException("序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据");
					return new ResponseEnvelope<>(false, NUM_EORR_PLEASE_CHECK_NUM, msgId);
			} else {
				// 绑定设备号
				sysUser2.setUserImei(terminalImei);
				sysUserService.update(sysUser2);
			}
			// 账户与设备绑定->end
			// 允许绑定
			authorizedConfig.setUserId(userId);
			authorizedConfig.setState(1);
			authorizedConfig.setTerminalImei(terminalImei);

			Map<String, String> resMap = authorizedConfigActivation(authorizedConfig, loginUser, "yes");
			if (TRUE.equals(resMap.get(SUCCESS))) {
				this.update(authorizedConfig);
				AuthorizedConfigCacher.remove(authorizedConfig.getId());
				return new ResponseEnvelope<>(true, SystemCommonConstant.BIND_SUCCESS, msgId);
			} else {
				return new ResponseEnvelope<>(false, resMap.get("data"), msgId);
			}
		} else {
			return new ResponseEnvelope<>(false, ONLY_BIND_DEALER_PAID_NUM, msgId);
		}
	}

	/**
	 * 查询序列号表获得一个参数的集合(品牌id,大类value,小类value,产品id)
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	public Map<String, List<String>> getConfigParams(Integer id, LoginUser loginUser) {
		Integer userId = loginUser.getId();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Set<String> brandIdSet = new HashSet<String>();
		Set<String> typeValueSet = new HashSet<String>();
		Set<String> smallTypeIdSet = new HashSet<String>();
		Set<String> productIdSet = new HashSet<String>();
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setStart(-1);
		List<AuthorizedConfig> authorizedConfigs = getPaginatedList(authorizedConfigSearch);
		for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
			// brand // list转set 去重？
			List<String> brandIds = stringToList(authorizedConfig.getBrandIds());
			setAddList(brandIdSet, brandIds);
			// typeValue
			List<String> typeValues = stringToList(authorizedConfig.getBigType());
			setAddList(typeValueSet, typeValues);
			// smallTypeId
			List<String> smallTypeIds = stringToList(authorizedConfig.getSmallTypeIds());
			setAddList(smallTypeIdSet, smallTypeIds);
			// productIds
			List<String> productIds = stringToList(authorizedConfig.getProductIds());
			setAddList(productIdSet, productIds);
		}
		// set 又转list
		List<String> brandIdList = new ArrayList<String>(brandIdSet);
		List<String> typeValueList = new ArrayList<String>(typeValueSet);
		List<String> smallTypeIdList = new ArrayList<String>(smallTypeIdSet);
		List<String> productIdList = new ArrayList<String>(productIdSet);
		map.put("brandIdList", brandIdList);
		map.put("typeValueList", typeValueList);
		map.put("smallTypeIdList", smallTypeIdList);
		map.put("productIdList", productIdList);
		return map;
	}

	/**
	 * 获取该序列号的最大序号
	 * 
	 * @param authorizedConfig
	 * @return List<AuthorizedConfig>
	 */
	@Override
	public List<AuthorizedConfig> getBigNum(AuthorizedConfig authorizedConfig) {
		return authorizedConfigMapper.selectBigNum(authorizedConfig);
	}

	/**
	 * 返回按照品牌归类的AuthorizedConfig
	 * 
	 * @author huangsongbo
	 * @param userId
	 *            用户id
	 */
	@Override
	public Map<Integer, List<AuthorizedConfig>> getEffectualauthorizedConfigs(Integer userId, String terminalImei) {
		Map<Integer, List<AuthorizedConfig>> map = new HashMap<Integer, List<AuthorizedConfig>>();
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		if (StringUtils.isNotBlank(terminalImei))
			authorizedConfigSearch.setTerminalImei(terminalImei);
		List<AuthorizedConfig> authorizedConfigs = getPaginatedList(authorizedConfigSearch);
		for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
			String brandIdsStr = authorizedConfig.getBrandIds();
			for (String str : brandIdsStr.split(",")) {
				if (StringUtils.isNotEmpty(str)) {
					if (map.containsKey(Integer.valueOf(str))) {
						map.get(Integer.valueOf(str)).add(authorizedConfig);
					} else {
						List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
						list.add(authorizedConfig);
						map.put(Integer.valueOf(str), list);
					}
				} else {
				}
			}
		}
		return map;
	}

	/**
	 * 返回按照大类归类的AuthorizedConfig
	 * 
	 * @author huangsongbo
	 * @param userId
	 *            用户id
	 */
	@Override
	public Map<String, List<AuthorizedConfig>> getEffectualauthorizedConfigs2(Integer userId, String terminalImei) {
		Map<String, List<AuthorizedConfig>> map = new HashMap<String, List<AuthorizedConfig>>();
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setState(1);
		if (StringUtils.isNotBlank(terminalImei))
			authorizedConfigSearch.setTerminalImei(terminalImei);
		List<AuthorizedConfig> authorizedConfigs = null;
		if (Utils.enableRedisCache()) {
			authorizedConfigs = AuthorizedConfigCacher.getPageList(authorizedConfigSearch);
		} else {
			authorizedConfigs = getPaginatedList(authorizedConfigSearch);
		}
		for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
			String bigTypesStr = authorizedConfig.getBigType();
			if (StringUtils.isBlank(bigTypesStr)) {
				String str = "all";
				if (map.containsKey(str)) {
					map.get(str).add(authorizedConfig);
				} else {
					List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
					list.add(authorizedConfig);
					map.put(str, list);
				}
			} else {
				for (String str : bigTypesStr.split(",")) {
					if (map.containsKey(str)) {
						map.get(str).add(authorizedConfig);
					} else {
						List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
						list.add(authorizedConfig);
						map.put(str, list);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 通过authorizedCode查找AuthorizedConfig
	 * 
	 * @author huangsongbo
	 * @param authorizedCode
	 * @return
	 */
	@Override
	public AuthorizedConfig findOneByAuthorizedCode(String authorizedCode) {
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(0);
		authorizedConfigSearch.setLimit(1);
		authorizedConfigSearch.setIsDeleted(0);
		authorizedConfigSearch.setAuthorizedCode(authorizedCode);
		List<AuthorizedConfig> list = getPaginatedList(authorizedConfigSearch);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过userId和payModelValue查找AuthorizedConfig
	 * 
	 * @param userId
	 * @param payModelValue
	 * @return
	 */
	@Override
	public List<AuthorizedConfig> findAllByUserIdAndPayModelValue(Integer userId, Integer payModelValue) {
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setStart(-1);
		authorizedConfigSearch.setLimit(-1);
		authorizedConfigSearch.setUserId(userId);
		authorizedConfigSearch.setPayModelValue(payModelValue);
		authorizedConfigSearch.setState(1);
		return getPaginatedList(authorizedConfigSearch);
	}

	/**
	 * 去重复(品牌,大类,小类)
	 * 
	 * @author huangsongbo
	 * @param authorizedConfig
	 */
	@Override
	public void dealWithRepeat(AuthorizedConfig authorizedConfig) {
		String brandIds = authorizedConfig.getBrandIds();
		List<String> brandIdsList = Utils.getListFromStr(brandIds);
		if (brandIdsList != null && brandIdsList.size() > 0)
			authorizedConfig.setBrandIds(brandIdsList.get(0));
		String bigType = authorizedConfig.getBigType();
		List<String> bigTypeList = Utils.getListFromStr(bigType);
		if (bigTypeList != null && bigTypeList.size() > 0)
			authorizedConfig.setBigType(bigTypeList.get(0));
		String smallTypeIds = authorizedConfig.getSmallTypeIds();
		List<String> smallTypeIdList = Utils.getListFromStr(smallTypeIds);
		if (smallTypeIdList != null && smallTypeIdList.size() > 0)
			authorizedConfig.setSmallTypeIds(smallTypeIdList.get(0));
	}

	/**
	 * T除序列号到期的登录用户
	 * 
	 * @author xiaoxc
	 */
	@Override
	public Object delAuthorizedPastDueUserCacheJob() {

		try {// 查询此前一天的到期授权码
			List<AuthorizedConfig> list = authorizedConfigMapper.selectPastDueAuthorizedLists();
			if (Lists.isNotEmpty(list)) {
				for (AuthorizedConfig authorizedConfig : list) {
					logger.warn("notOutCount:" + authorizedConfig.getNotOutCount());
					if (authorizedConfig.getNotOutCount() == 0) {
						if (authorizedConfig.getUserId() != null) {
							// 清除用户缓存
							SysUserCacher.remove(authorizedConfig.getUserId());
							// 清除用户超时信息
							SysUserCacher.removeTimeOutUser(authorizedConfig.getUserId());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		}
		return null;
	}

	/**
	 * 得到该用户关联的设计公司授权码的数量
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public int findCountWhereCompanyTypeIsDesignByUserId(Integer id, LoginUser loginUser) {
		Integer userId = loginUser.getId();
		// 找到设计公司数据字典
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey("brandBusinessType",
				"designCompany");
		if (sysDictionary == null) {
			logger.error(CHECK_DIC_NO_DESIGN_COMPANY_PLEASE_ADD);
			return 0;
		}
		return authorizedConfigMapper.findCountByCompanyTypeAndUserId(sysDictionary.getValue(), userId);
	}

	/**
	 * 获得几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = now.getTime();
		String dateS = sdf.format(date);
		return dateS;
	}

	/**
	 * 获得所有正式的授权码 遇到只剩30天的 7天的的给于就提醒
	 * 
	 * @throws Exception
	 */
	public void authorizationCode_SMS_Alerts() throws Exception {
		String result = "0";
		List<AuthorizedConfig> day30List = null;
		List<AuthorizedConfig> day7List = null;
		AuthorizedConfig authorizedConfig30 = new AuthorizedConfig();
		authorizedConfig30.setIsDeleted(0);
		authorizedConfig30.setAuthorizedCodeType(0);
		String validTimeBegin30 = getDateAfter(new Date(), 30) + " 00:00:00";
		String validTimeEnd30 = getDateAfter(new Date(), 30) + " 23:59:59";
		authorizedConfig30.setValidTimeBegin(validTimeBegin30);
		authorizedConfig30.setValidTimeEnd(validTimeEnd30);
		day30List = this.getListByMobile(authorizedConfig30);
		for (AuthorizedConfig authorizedConfig : day30List) {
			String mobile = authorizedConfig.getMobile();
			if (StringUtils.isNotBlank(mobile)) {
				String message = null;
				message = MessageFormat.format(SmsClient.AUTHORIZED_CONFIG_VALID_TIME30,
						authorizedConfig.getAuthorizedCode());
				// message= "您的授权码
				// :"+authorizedConfig.getAuthorizedCode()+"将会在30天后过期，请及时续费";
				message = URLEncoder.encode(message, "UTF-8");
				String params = "phone=" + mobile + "&message=" + message;
				result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
				if ("0".equals(result)) {
					logger.info(message + SEND_SUCCESS);
				} else {
					logger.error(message + SEND_CHECKMSG_ERROR);
				}
			}
		}
		// 获得所有正式的授权码 遇到只剩7天的的给于就提醒
		AuthorizedConfig authorizedConfig7 = new AuthorizedConfig();
		authorizedConfig7.setIsDeleted(0);
		authorizedConfig7.setAuthorizedCodeType(0);
		String validTimeBegin7 = getDateAfter(new Date(), 7) + " 00:00:00";
		String validTimeEnd7 = getDateAfter(new Date(), 7) + " 23:59:59";
		authorizedConfig7.setValidTimeBegin(validTimeBegin7);
		authorizedConfig7.setValidTimeEnd(validTimeEnd7);
		day7List = this.getListByMobile(authorizedConfig7);
		for (AuthorizedConfig authorizedConfig : day7List) {
			String mobile = authorizedConfig.getMobile();
			if (StringUtils.isNotBlank(mobile)) {
				String message = null;
				message = MessageFormat.format(SmsClient.AUTHORIZED_CONFIG_VALID_TIME7,
						authorizedConfig.getAuthorizedCode());
				message = URLEncoder.encode(message, "UTF-8");
				String params = "phone=" + mobile + "&addserial=&message=" + message;
				result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
				if ("0".equals(result)) {
					logger.info(message + SEND_SUCCESS);
				} else {
					logger.error(message + SEND_CHECKMSG_ERROR);
				}
			}
		}
	}

	/**
	 * 序列号续费
	 */
	@Override
	public Object continueCost(AuthorizedVO authorizedVO) {
		String authorizedConfigId = authorizedVO.getId();
		String type = authorizedVO.getType();
		String msgId = authorizedVO.getMsgId();
		Date currentTime = new Date();// 当前时间
		Date validTime = null; // 有效时间
		AuthorizedConfig authorizedConfig = null;
		if (authorizedConfigId == null || "".equals(authorizedConfigId)) {
			return new ResponseEnvelope<>(true, LACK_ID, msgId);
		}
		if (msgId == null || "".equals(msgId)) {
			return new ResponseEnvelope<>(true, LACK_MSGID, msgId);
		}
		try {
			authorizedConfig = this.getAuthorizedConfig(Integer.parseInt(authorizedConfigId));
			if (authorizedConfig != null) {
				validTime = authorizedConfig.getValidTime();
			} else {
				return new ResponseEnvelope<>(true, NO_NUM, msgId);
			}
			Integer dayNum = null;
			SysDictionary sysDictionary = new SysDictionary();
			sysDictionary.setValuekey("formalValidTime");// 设置唯一标识
			List<SysDictionary> validTimeList = sysDictionaryService.getList(sysDictionary);
			if (validTimeList != null && validTimeList.size() > 0) {
				dayNum = validTimeList.get(0).getValue();
			}
			if (dayNum == null || dayNum.intValue() < 0) {
				return new ResponseEnvelope<>(true, NUM_OPERATE_FAIL_CALL_SERVICE, msgId);
			}
			if (validTime == null) {
				authorizedConfig.setValidTime(Utils.getYearBefore(new Date(System.currentTimeMillis()), 1));
			}
			// 类型为开通的时候，计算daynum
			if ("1".equals(type)) {
				// 判断是否存在销售延期缩短有效期的策略 (有效时长策略)
				String validStrategy = authorizedConfig.getValidStrategy();
				// //System.out.println("validStrategy"+validStrategy);
				if (StringUtils.isNotBlank(validStrategy)) {
					if (validStrategy.indexOf("-") > -1) {
						validStrategy = validStrategy.replace("-", "");
						Integer activityDayNum = Integer.parseInt(validStrategy);
						dayNum = dayNum.intValue() - activityDayNum.intValue();
					} else {
						Integer activityDayNum = Integer.parseInt(validStrategy);
						dayNum = dayNum.intValue() + activityDayNum.intValue();
					}
				}
			}
			if (validTime != null) {
				if (!validTime.after(currentTime)) {// 有效时间在当前时间之前，说明已经过期，从当前时间上开始续时间
					Date date = Utils.getDateAfter(new Date(), dayNum);
					authorizedConfig.setValidTime(date);
				}
				if (validTime.after(currentTime)) {// 有效时间在当前时间之前，说明没过期,从原本时间上
													// 加长时间
					Date date = Utils.getDateAfter(validTime, dayNum);
					authorizedConfig.setValidTime(date);
				}
			}
			authorizedConfig.setAuthorizedCodeType(0); // 序列号类型
			authorizedConfig.setId(Integer.parseInt(authorizedConfigId));
			authorizedConfig.setValidState(1); // 有效状态
			this.update(authorizedConfig);
			if ("1".equals(type)) {
				return new ResponseEnvelope<>(true, DREDGE_SUCCESS, msgId);
			} else {
				return new ResponseEnvelope<>(true, RENEW_SUCCESS, msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if ("1".equals(type)) { // 区分是绑定 还是开通
				return new ResponseEnvelope<>(true, DREDGE_FAIL, msgId);
			} else {
				return new ResponseEnvelope<>(true, RENEW_FAIL, msgId);
			}
		}
	}

	/**
	 * 序列号购买之前的验证
	 * 
	 * @author
	 * @param authorizedCode
	 * @return
	 */
	public Object verify(String authorizedCode, String msgId, Integer userId) {
		AuthorizedConfig authorizedConfig = this.findOneByAuthorizedCode(authorizedCode);
		if (authorizedConfig == null)
			return new ResponseEnvelope<>(false, NO_NUM, msgId);
		// 识别该用户码是否已被使用
		if (authorizedConfig.getState() == 1) {
			return new ResponseEnvelope<>(false, NUM_THEN_USE, msgId);
		}
		// 识别该序列号是否已被使用->end
		// state不为1且userId还保存->识别是不是该用户以前删除的序列号
		if (authorizedConfig.getUserId() != null && authorizedConfig.getUserId() > 0
				&& authorizedConfig.getUserId() != userId)
			return new ResponseEnvelope<>(false, NUM_USE_IMPORT_OTHER_NUM, msgId);
		// 检查是否为企业支付->是->不允许添加
		SysDictionary sysDictionarycompanyPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "companyPay");
		if (sysDictionarycompanyPay == null)
			return new RuntimeException(PLEASE_ADD_DATADIC_TO_ADMIN_3);// 企业付费
		SysDictionary sysDictionaryDealersPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
		if (sysDictionaryDealersPay == null)
			return new RuntimeException(PLEASE_ADD_DATADIC_TO_ADMIN_1);// 经销商付费
		if (sysDictionarycompanyPay.getValue().equals(authorizedConfig.getPayModelValue())) {
			return new ResponseEnvelope<>(false, NUM_HAS_PAID_IMPORT_PWD_DREDGE, msgId);
		} else if (sysDictionaryDealersPay.getValue().equals(authorizedConfig.getPayModelValue())) {
			// 检查是否已绑定同公司的序列号->true,则无需重复购买
			List<AuthorizedConfig> list = this.findAllByUserIdAndCompanyId(userId, authorizedConfig.getCompanyId());
			if (list != null && list.size() > 0)
				return new ResponseEnvelope<>(false, U_DREDGE_COMPANY_BRAND_SO_NOT_PAID, msgId);
		}
		// 检查是否为企业支付->是->不允许添加->end
		authorizedConfig.setPassword("");
		// 设置返回类型
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("authorizedCode", authorizedConfig.getAuthorizedCode());
		returnMap.put("authorizedId", authorizedConfig.getId());
		String success = "false";
		// 至于使用可开通才能进 活动判断
		if (authorizedConfig.getAuthorizedCodeType() != null && authorizedConfig.getAuthorizedCodeType().intValue() == 1
				&& authorizedConfig.getIsOpen() != null && authorizedConfig.getIsOpen().intValue() == 1) {
			// 判断当前时间是否在 活动时间内 ,条件成立返回价格 并填充价格。
			Map<String, String> checkActivityMap = checkActivity(authorizedConfig);
			if (checkActivityMap != null && checkActivityMap.size() > 0) {
				success = checkActivityMap.get("success");
				if (StringUtils.isNotBlank(checkActivityMap.get(UNITPRICE))) {
					BigDecimal bigDecimal = new BigDecimal(checkActivityMap.get(UNITPRICE));
					BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
					BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
					authorizedConfig.setUnitPrice(bigDecimalMultiply.doubleValue());
					double unitPrice = bigDecimalMultiply.doubleValue();
					returnMap.put(UNITPRICE, unitPrice);
				}
			}
		}
		if ("false".equals(success)) {
			BigDecimal bigDecimal = new BigDecimal(Double.toString(authorizedConfig.getUnitPrice()));
			BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
			BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
			double unitPrice = bigDecimalMultiply.doubleValue();
			returnMap.put(UNITPRICE, unitPrice);
		}
		// 设置返回类型->end
		return new ResponseEnvelope<>(returnMap, msgId, true);
	}
}
