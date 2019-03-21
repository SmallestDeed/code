package com.nork.product.controller.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignPlan;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.system.dao.SysRoleMapper;
import com.nork.system.dao.SysUserRoleMapper;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserRole;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserEquipmentService;
import com.nork.system.service.SysUserService;
import com.nork.system.sms.httpclient.SmsClient;

@Controller
@RequestMapping("/{style}/web/product/authorizedConfig")
public class WebAuthorizedConfigController {
	private static Logger logger = Logger.getLogger(WebAuthorizedConfigController.class);
	@Autowired
	private AuthorizedConfigService authorizedConfigService;

	@Autowired
	private BaseBrandService baseBrandService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private ResPicService resPicService;

	@Autowired
	private  SysUserEquipmentService sysUserEquipmentService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	/**
	 * 授权品牌列表接口
	 */
	@RequestMapping(value = "/authorizedBrandList")
	@ResponseBody
	public Object authorizedBrandList(
			@ModelAttribute("authorizedConfigSearch") AuthorizedConfigSearch authorizedConfigSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int validity = 0;
		/* 20160607需求变更为序列号与设备没有关系 */
		authorizedConfigSearch.setTerminalImei(null);
		String msgId = authorizedConfigSearch.getMsgId();
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数msgId不能为空！", msgId);
		}
		/* 获取当前用户 */
		LoginUser loginUser = new LoginUser();
		if (SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
			return new ResponseEnvelope<DesignPlan>(false, "请先登录！", msgId);
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		authorizedConfigSearch.setUserId(loginUser.getId());/* 当前厂商 */
		authorizedConfigSearch.setState(1);/* 已使用 */
		List<AuthorizedConfig> list = new ArrayList<AuthorizedConfig>();
		int total = 0;
		int totalState = 0;
		try {
			/* 每次登录验证未过期的序列号是否过期，过期则修改序列号状态 */
			authorizedConfigSearch.setValidState(1);
			total = authorizedConfigService.getCount(authorizedConfigSearch);
			if (total > 0) {
				list = authorizedConfigService.getPaginatedList(authorizedConfigSearch);
				for (AuthorizedConfig authorizedConfig : list) {
					Date validTime = authorizedConfig.getValidTime();
					Date currentTime = new Date();
					if (validTime != null) {
						if (!validTime.after(currentTime)) {/* 有效时间在当前时间之前，说明已经过期 */
							authorizedConfig.setValidState(0);
							authorizedConfigService.update(authorizedConfig);
						}
					}
					if (validTime == null) {
						/* 有效时间在当前时间之前，说明已经过期 */
						authorizedConfig.setValidState(0);
						authorizedConfigService.update(authorizedConfig);
					}
				}

			}
			/* 进入添加品牌列表,能看到 所有过期和未过期的 序列号 */
			authorizedConfigSearch.setValidState(3);
			total = authorizedConfigService.getCount(authorizedConfigSearch);
			authorizedConfigSearch.setValidState(3);/* 重复 也误删，impl修改了该字段 */
			list = authorizedConfigService.getPaginatedList(authorizedConfigSearch);
			for (AuthorizedConfig authorizedConfig : list) {
				if (StringUtils.isNotBlank(authorizedConfig.getBrandIds())) {
					String brandArr[] = authorizedConfig.getBrandIds().split(",");
					List<BaseBrand> blist = new ArrayList<BaseBrand>();
					for (String bid : brandArr) {
						BaseBrand baseBrand = null;
						if (Utils.enableRedisCache()) {
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
								baseBrand.setBrandLogoPath(resPic.getPicPath());
							}
							blist.add(baseBrand);
						}
					}
					authorizedConfig.setList(blist);
				}
				String success = "";
				/* 至于使用可开通才能进 活动判断 */
				if (authorizedConfig.getAuthorizedCodeType() != null
						&& authorizedConfig.getAuthorizedCodeType().intValue() == 1
						&& authorizedConfig.getIsOpen() != null && authorizedConfig.getIsOpen().intValue() == 1) {
					/* 判断当前时间是否在 活动时间内 ,条件成立返回价格 并填充价格。 */
					Map<String, String> checkActivityMap = checkActivity(authorizedConfig);
					if (checkActivityMap == null || checkActivityMap.size() <= 0) {
						success = "false";
					} else {
						success = checkActivityMap.get("success");
						if (StringUtils.isNotBlank(checkActivityMap.get("unitPrice"))) {
							BigDecimal bigDecimal = new BigDecimal(checkActivityMap.get("unitPrice"));
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
					success = "false";
				}
				if ("false".equals(success)) {
					if (authorizedConfig.getUnitPrice() != null) {
						BigDecimal bigDecimal = new BigDecimal(Double.toString(authorizedConfig.getUnitPrice()));
						BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
						BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
						authorizedConfig.setUnitPrice(bigDecimalMultiply.doubleValue());
						authorizedConfig.setActivityIds(null);
					}
				}

			}
			/* 验证完之后，再次查询未过期的序列号 */
			authorizedConfigSearch.setValidState(1);
			totalState = authorizedConfigService.getCount(authorizedConfigSearch);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常！", msgId);
		}
		if (totalState <= 0) {
			validity = 0;
			ResponseEnvelope<AuthorizedConfig> RES = new ResponseEnvelope<AuthorizedConfig>(total, list,
					authorizedConfigSearch.getMsgId());
			RES.setObj(validity);
			return RES;
		} else {
			validity = 1;
			ResponseEnvelope<AuthorizedConfig> RES = new ResponseEnvelope<AuthorizedConfig>(total, list,
					authorizedConfigSearch.getMsgId());
			RES.setObj(validity);
			return RES;
		}
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
		try {
			Date date = new Date();
			Date startTime = authorizedConfig.getStartTime(); /* 激活时间 */
			String unitPrice = null;
			if (StringUtils.isNotBlank(authorizedConfig.getActivityIds())) {

				SysDictionary sysDictionary = sysDictionaryService
						.get(Integer.parseInt(authorizedConfig.getActivityIds()));
				if (sysDictionary != null) {
					if (StringUtils.isNotBlank(sysDictionary.getAtt1())
							&& StringUtils.isNotBlank(sysDictionary.getAtt1Info())) {
						/* 数据校验 -- 正常数据类型 为：att1 = 1-7 att1Info = 600 */
						Map<String, String> map = checkParam(sysDictionary.getAtt1(), sysDictionary.getAtt1Info());
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
								/* -- 活动详情 赋值 */
								activityDetailsAssignment(sysDictionary, authorizedConfig);
								unitPrice = map.get("price");
								// authorizedConfig.setUnitPrice(Double.parseDouble(unitPrice));
								resMap.put("success", "true");
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
					if (StringUtils.isNotBlank(sysDictionary.getAtt2())
							&& StringUtils.isNotBlank(sysDictionary.getAtt2Info())) {
						Map<String, String> map = checkParam(sysDictionary.getAtt2(), sysDictionary.getAtt2Info());
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
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
					if (StringUtils.isNotBlank(sysDictionary.getAtt3())
							&& StringUtils.isNotBlank(sysDictionary.getAtt3Info())) {
						Map<String, String> map = checkParam(sysDictionary.getAtt3(), sysDictionary.getAtt3Info());
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
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
					if (StringUtils.isNotBlank(sysDictionary.getAtt4())
							&& StringUtils.isNotBlank(sysDictionary.getAtt4Info())) {
						Map<String, String> map = checkParam(sysDictionary.getAtt4(), sysDictionary.getAtt4Info());
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
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
					if (StringUtils.isNotBlank(sysDictionary.getAtt5())
							&& StringUtils.isNotBlank(sysDictionary.getAtt5Info())) {
						Map<String, String> map = checkParam(sysDictionary.getAtt5(), sysDictionary.getAtt5Info());
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
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
					if (StringUtils.isNotBlank(sysDictionary.getAtt6())
							&& StringUtils.isNotBlank(sysDictionary.getAtt6Info())) {
						Map<String, String> map = checkParam(sysDictionary.getAtt6(), sysDictionary.getAtt6Info());
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
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
					if (StringUtils.isNotBlank(sysDictionary.getAtt7())
							&& StringUtils.isNotBlank(sysDictionary.getAtt7Info())) {
						Map<String, String> map = checkParam(sysDictionary.getAtt7(), sysDictionary.getAtt7Info());
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
								resMap.put("unitPrice", unitPrice);
								return resMap;
							}
						}
					}
				} else {
					resMap.put("success", "false");
					return resMap;
				}

			} else {
				resMap.put("success", "false");
				return resMap;
			}

		} catch (Exception e) {
			resMap.put("success", "false");
			logger.error("checkActivity" + e);
			return resMap;
		}

		resMap.put("success", "false");
		return resMap;
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

	/**
	 * 数据校验 正常数据类型 为：att1 = 1-7 att1Info = 600
	 * 
	 * @param att1
	 * @param att1Info
	 * @return
	 */
	public Map<String, String> checkParam(String att1, String att1Info) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (StringUtils.isBlank(att1) && StringUtils.isBlank(att1Info)) {
			resMap.put("success", "false");
			return resMap;
		}
		if (att1.indexOf("-") <= -1) {
			resMap.put("success", "false");
			return resMap;
		}
		String startTimeNum = att1.substring(0, att1.indexOf("-"));
		String endTimeNum = att1.split("-")[1];

		try {
			Integer.parseInt(startTimeNum);
			Integer.parseInt(endTimeNum);
			Integer.parseInt(att1Info);

			resMap.put("success", "true");
			resMap.put("startTimeNum", startTimeNum);
			resMap.put("endTimeNum", endTimeNum);
			resMap.put("price", att1Info);
			return resMap;
		} catch (Exception e) {
			resMap.put("success", "false");
			return resMap;
		}
	}

	/**
	 * 修改序列号状态(绑定用户/解绑)
	 * 
	 * @author huangsongbo
	 * @param msgId
	 * @param terminalImei
	 *            设备IMEI
	 * @param authorizedCode
	 *            序列号
	 * @param password
	 *            密码
	 * @param status
	 *            状态值(0:取消授权;1:绑定授权)
	 * @return
	 */
	@RequestMapping(value = "/updateAuthorizedConfigStatus")
	@ResponseBody
	public Object updateAuthorizedConfigStatus(String msgId, String terminalImei, String authorizedCode,
			String password, Integer status, HttpServletRequest request, HttpServletResponse response) {
		/* 得到userId */
		Integer userId = null;
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (loginUser != null) {
			userId = loginUser.getId();
		}
		/* 得到userIdEnd */
		Map<String, String> map = authorizedConfigService.VerifyParams(msgId, userId, terminalImei, authorizedCode,
				password, status);
		if (StringUtils.equals("false", map.get("success"))) {
			/* 参数不符合规范 */
			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
		}
		/* 根据参数验证序列号是否正确 */
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setValidState(3);/* 等于3则查询 所有状态 */
		authorizedConfigSearch.setLimit(1);
		authorizedConfigSearch.setStart(0);
		authorizedConfigSearch.setAuthorizedCode(authorizedCode);
		List<AuthorizedConfig> authorizedConfigs = null;
		authorizedConfigs = authorizedConfigService.getPaginatedList(authorizedConfigSearch);

		if (authorizedConfigs == null || authorizedConfigs.size() < 1) {
		  if(status.equals(new Integer(0))){
		    return new ResponseEnvelope<>(false, "该序列号不存在,请输入其它序列号或联系客服", msgId);
          }else {
            //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
            return new ResponseEnvelope<>(true, "该序列号不存在,请输入其它序列号或联系客服", msgId);
          }
		    
		}
		
		AuthorizedConfig authorizedConfig = authorizedConfigs.get(0);
		//做个标记  判断该序列号是否超过最晚激活时间
		if(authorizedConfig != null) {
			if(authorizedConfig.getActivationTimeType().intValue() == 1) {//到期序列号作废
				Date date = new Date();
				Date date1 = authorizedConfig.getLatestActivationDate();
				if(date1.before(date)) {
					return new ResponseEnvelope<>(false, "该序列号已过期！", msgId);
				}
			}
		}
		/* 用户想添加 自己删除过的授权码 */
		if (authorizedConfig.getUserId() != null) {
			if (authorizedConfig.getState().intValue() == 0
					&& loginUser.getId().intValue() == authorizedConfig.getUserId().intValue()) {
				AuthorizedConfig authorizedConfig_new = new AuthorizedConfig();
				authorizedConfig_new.setId(authorizedConfig.getId());
				authorizedConfig_new.setState(1);
				authorizedConfigService.update(authorizedConfig_new);
				//FIXME：绑定序列号时，给用户配置相应的角色
				Integer userType = this.setRoleToUser(authorizedConfig.getType().intValue(), userId);
				return new ResponseEnvelope<>(true,"添加成功！", msgId ,true);
			}
		}
		Date startTime = authorizedConfig.getStartTime();
		if (startTime != null) {
			if (startTime.after(new Date())) {
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				String startTimeStr = sim.format(startTime);
				return new ResponseEnvelope<>(false,
						"序列号" + authorizedConfig.getAuthorizedCode() + ",开始生效时间为：" + startTimeStr + ",绑定失败！", msgId);
			}
		}
		/* get经销商付费sysDictionary */
		SysDictionary sysDictionaryDealersPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
		if (sysDictionaryDealersPay == null)
			return new RuntimeException("请管理员添加数据字典:type:payModel;valueKey:dealersPay;name:经销商付费");
		if(status.equals(new Integer(1))&&sysDictionaryDealersPay.getValue().equals(authorizedConfig.getPayModelValue())){
			/*判定为经销商付费的序列号*/
			/*检测是否已经绑定了同公司的序列号*/
			List<AuthorizedConfig> list=authorizedConfigService.findAllByUserIdAndCompanyId(userId,authorizedConfig.getCompanyId());
			if(list!=null&&list.size()>0){
				/*验证是否被使用*/
				if (authorizedConfig.getUserId() != null && authorizedConfig.getUserId() > 0
						&& !(authorizedConfig.getUserId().equals(userId)))
				    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
					return new ResponseEnvelope<>(true , "序列号已使用，请输入其他序列号！", msgId);
				/* 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定 */
				String companyIdStr = authorizedConfig.getCompanyId();
				String brandIdsStr = authorizedConfig.getBrandIds();
				for(AuthorizedConfig authorizedConfig2:list){
					if(StringUtils.equals(companyIdStr, authorizedConfig2.getCompanyId())&&StringUtils.equals(brandIdsStr, authorizedConfig2.getBrandIds()))
					  //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
					  return new ResponseEnvelope<>(true , "已添加该品牌,无需重复添加", msgId);		
				}
				/*
				 * 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定*->end/
				 */
				/* 用户绑定设备号->end */
				Map<String, String> resMap = null;
				resMap = authorizedConfigActivation(authorizedConfig, loginUser, "no");
				if (resMap != null) {
					if ("true".equals(resMap.get("success"))) {
						authorizedConfig.setUserId(userId);
						authorizedConfig.setState(1);
						authorizedConfig.setTerminalImei(terminalImei);
						authorizedConfigService.update(authorizedConfig);
						//添加授权码时判断是否需要绑定设备
						sysUserEquipmentService.authorizedConfigBindingEquipment(userId,terminalImei);
						AuthorizedConfigCacher.remove(authorizedConfig.getId());
					} else {
						return new ResponseEnvelope<>(false, resMap.get("data"), msgId);
					}
				} else {
					return new ResponseEnvelope<>(false, "绑定失败", msgId);
				}
				/* 允许直接绑定 */
				authorizedConfig.setUserId(userId);
				authorizedConfig.setState(1);
				authorizedConfig.setTerminalImei(terminalImei);
				authorizedConfigService.update(authorizedConfig);
				//添加授权码时判断是否需要绑定设备
				sysUserEquipmentService.authorizedConfigBindingEquipment(userId,terminalImei);
				AuthorizedConfigCacher.remove(authorizedConfig.getId());
				//FIXME：绑定序列号时，给用户配置相应的角色
				Integer userType = this.setRoleToUser(authorizedConfig.getType().intValue(), userId);
				return new ResponseEnvelope<>(true, "绑定成功", msgId,true);

			}
		}

		if(!StringUtils.equals(password, authorizedConfig.getPassword())) {
		  if(status.equals(new Integer(0))){
		      return new ResponseEnvelope<>(false , "密码错误！", msgId);
		  }else {
		      //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
	          return new ResponseEnvelope<>(true , "密码错误！", msgId);
		  }
		}
		if (status.equals(new Integer(0))) {
			/* 取消绑定 */
			if (authorizedConfig.getUserId() == null || !userId.equals(authorizedConfig.getUserId())) {
				return new ResponseEnvelope<>(false, "该序列号不归该用户所有,操作失败", msgId);
			}
			/* 识别终端号 */
			// if(!StringUtils.equals(terminalImei, authorizedConfig.getTerminalImei()))
			// return new ResponseEnvelope<>(false, "设备不能识别,操作失败", msgId);
			/* 识别该用户有多少条序列号->只剩最后一条->解绑失败 */
			AuthorizedConfigSearch authorizedConfigSearch2 = new AuthorizedConfigSearch();
			authorizedConfigSearch2.setUserId(userId);
			authorizedConfigSearch2.setState(1);
			authorizedConfigSearch2.setStart(0);
			authorizedConfigSearch2.setLimit(2);
			// authorizedConfigSearch2.setTerminalImei(terminalImei);
			List<AuthorizedConfig> authorizedConfigs2 = authorizedConfigService
					.getPaginatedList(authorizedConfigSearch2);
			if (authorizedConfigs2 != null && authorizedConfigs2.size() == 1) {
				if (authorizedConfigs2.get(0).getAuthorizedCode().equals(authorizedCode)) {
					return new ResponseEnvelope<>(false, "必须保留至少一条授权品牌,删除失败", msgId);
				}
			}
			/* 识别该用户有多少条序列号->只剩最后一条->解绑失败->end */
			// authorizedConfig.setUserId(0);
			authorizedConfig.setState(0);
			// authorizedConfig.setTerminalImei("");
			authorizedConfigService.update(authorizedConfig);//FIXME：这里是解绑序列号，是否需要把用户u3d菜单权限删除
			AuthorizedConfigCacher.remove(authorizedConfig.getId());

		}else{
			/*绑定序列号*/
			/*识别该序列号是否已经绑定*/
			if(new Integer(1).equals(authorizedConfig.getState()))
			    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				//return new ResponseEnvelope<>(false, "该序列号已被使用,不能重复绑定", msgId);
				return new ResponseEnvelope<>(true , "序列号已使用！请输入其他序列号！", msgId,false);
			/* state不为1且userId还保存->识别是不是该用户以前删除的序列号 */
			if (authorizedConfig.getUserId() != null && authorizedConfig.getUserId() > 0
					&& !(authorizedConfig.getUserId().equals(userId)))
			    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				return new ResponseEnvelope<>(true , "序列号已使用！请输入其他序列号！", msgId,false);
			/* 用户绑定设备号 */
			SysUser sysUser2 = sysUserService.get(loginUser.getId());
			if (sysUser2 == null || sysUser2.getId() == null)
				return new RuntimeException("未找到该用户");
			/* 识别是否支付 */
			SysDictionary sysDictionaryPayStatusFinish = sysDictionaryService.findOneByTypeAndValueKey("payStatus",
					"payStatus_finish");
			if (sysDictionaryPayStatusFinish == null)
				return new RuntimeException("请管理员添加数据字典:type:payStatus;valueKey:payStatus_finish;name:已付款");
			if (authorizedConfig.getPayStatusValue() != sysDictionaryPayStatusFinish.getValue())
			//success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				return new ResponseEnvelope<>(true, "该序列号未支付，请点击购买开通！", msgId);
			/* 识别是否支付->end */
			/* 判定添加第2条以上的序列号,不能为企业支付类型的序列号 */
			List<AuthorizedConfig> list = authorizedConfigService.findAllByUserId(userId);
			/*
			 * if(list!=null&&list.size()>0){ 非第一次绑定 get企业支付的sysDictionary SysDictionary
			 * sysDictionarycompanyPay=sysDictionaryService.findOneByTypeAndValueKey(
			 * "payModel", "companyPay"); if(sysDictionarycompanyPay==null){ return new
			 * RuntimeException("请管理员添加数据字典:type:payModel;valueKey:companyPay;name:合同付费"); }
			 * if(sysDictionarycompanyPay.getValue().equals(authorizedConfig.
			 * getPayModelValue())){ SysDictionary contactInformation
			 * =sysDictionaryService.findOneByTypeAndValueKey("contactInformation",
			 * "telephone"); String telephone = null; if(contactInformation!=null){
			 * telephone = contactInformation.getAtt1(); }
			 * if(contactInformation==null||telephone==null||"".equals(telephone)){
			 * telephone = "0755—23895307"; } return new ResponseEnvelope<>(false,
			 * "该序列号已经通过合同付费，不能添加。有疑问，请联系客服电话："+telephone, msgId); } }else{ 第一次绑定 }
			 */
			/* 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定 */
			String companyIdStr = authorizedConfig.getCompanyId();
			String brandIdsStr = authorizedConfig.getBrandIds();
			for(AuthorizedConfig authorizedConfig2:list){
				if(StringUtils.equals(companyIdStr, authorizedConfig2.getCompanyId())&&StringUtils.equals(brandIdsStr, authorizedConfig2.getBrandIds()))
				  //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				  return new ResponseEnvelope<>(true, "已添加该品牌,无需重复添加", msgId);		
			}
			/*
			 * 识别是否有重复品牌,如果有重复公司+品牌则提示无需重复绑定*->end/ /*判定添加第2条以上的序列号,不能为企业支付类型的序列号->end
			 */
			/* 用户绑定设备号->end */
			Map<String, String> resMap = null;
			resMap = authorizedConfigActivation(authorizedConfig, loginUser, "no");
			if (resMap != null) {
				if ("true".equals(resMap.get("success"))) {
					authorizedConfig.setUserId(userId);
					authorizedConfig.setState(1);
					authorizedConfig.setTerminalImei(terminalImei);
					authorizedConfigService.update(authorizedConfig);
					//添加授权码时判断是否需要绑定设备
					sysUserEquipmentService.authorizedConfigBindingEquipment(userId,terminalImei);
					AuthorizedConfigCacher.remove(authorizedConfig.getId());
					//FIXME：绑定序列号时，给用户配置相应的角色  将方法返回值改为序列号类型
					Integer userType = this.setRoleToUser(authorizedConfig.getType().intValue(), userId);
					if(userType != null) {
						sysUser2.setUserType(userType);
					}
					logger.error("authorizedConfig.getType().intValue()     =====" + authorizedConfig.getType().intValue());
					logger.error("userType3333333333333333333333333333333333333333333333333333" + userType);
				} else {
					return new ResponseEnvelope<>(false, resMap.get("data"), msgId);
				}
			} else {
				return new ResponseEnvelope<>(false, "绑定失败", msgId);
			}
			if (StringUtils.isNotBlank(sysUser2.getUserImei())) {
				if (!StringUtils.equals(terminalImei, sysUser2.getUserImei()))
					// return new RuntimeException("序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据");
					return new ResponseEnvelope<>(false, "序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据", msgId);
			} else {
				/* 绑定设备号 */
				sysUser2.setUserImei(terminalImei);
				sysUser2.setBalanceAmount(null);
				sysUserService.update(sysUser2);
			}
		}

		return new ResponseEnvelope<>(true, status.equals(new Integer(0))?"解绑成功":"绑定成功", msgId,true);
	}

	
	
	
	
	/**
	 * 序列号购买之前的验证
	 * 
	 * @author huangsongbo
	 * @param authorizedCode
	 * @return
	 */
	@RequestMapping("/verify")
	@ResponseBody
	public Object verify(String authorizedCode, String msgId, HttpServletRequest request) {
		/* 得到userId */
		Integer userId = null;
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (loginUser != null) {
			userId = loginUser.getId();
		}
		/* 得到userId->end */
		AuthorizedConfig authorizedConfig = authorizedConfigService.findOneByAuthorizedCode(authorizedCode);
		if (authorizedConfig == null)
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<>(true , "该序列号不存在,请输入其它序列号或联系客服", msgId,false);
		//校验序列号是否过期
		if(authorizedConfig.getValidState() == 0 ) {
		  if(authorizedConfig.getIsOpen() == null || authorizedConfig.getIsOpen() == 0) {
		    //不可开通
            return new ResponseEnvelope<>(true, "该序列号已过期,请购买其它序列号", msgId);
		  }else if(authorizedConfig.getIsOpen() == 1) {
		    //可开通
            return new ResponseEnvelope<>(true, "该序列号已过期,如需继续使用，请开通", msgId);
		  }
         
        }
		/*识别该用户码是否已被使用*/
		if(authorizedConfig.getState()==1){
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<>(true, "该序列号已被使用,请购买其他序列号", msgId,false);
		}
		/* 识别该用户吗是否已被使用->end */
		/* state不为1且userId还保存->识别是不是该用户以前删除的序列号 */
		if (authorizedConfig.getUserId() != null && authorizedConfig.getUserId() > 0
				&& authorizedConfig.getUserId() != userId)
		  //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
          return new ResponseEnvelope<>(true, "该序列号已被使用,请购买其他序列号", msgId,false);
		/* 检查是否为企业支付->是->不允许添加 */
		SysDictionary sysDictionarycompanyPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "companyPay");
		if (sysDictionarycompanyPay == null)
			return new RuntimeException("请管理员添加数据字典:type:payModel;valueKey:companyPay;name:企业付费");
		SysDictionary sysDictionaryDealersPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
		if (sysDictionaryDealersPay == null)
			return new RuntimeException("请管理员添加数据字典:type:payModel;valueKey:dealersPay;name:经销商付费");
		if (sysDictionarycompanyPay.getValue().equals(authorizedConfig.getPayModelValue())) {
			return new ResponseEnvelope<>(true, "该序列号已经通过企业付费，请直接输入密码开通", msgId);
		} else if (sysDictionaryDealersPay.getValue().equals(authorizedConfig.getPayModelValue())) {
			/* 检查是否已绑定同公司的序列号->true,则无需重复购买 */
			List<AuthorizedConfig> list = authorizedConfigService.findAllByUserIdAndCompanyId(userId,
					authorizedConfig.getCompanyId());
			if (list != null && list.size() > 0)
				return new ResponseEnvelope<>(true, "您已经开通了该公司的其他品牌，所以无需再次支付。", msgId);
		}
		/* 检查是否为企业支付->是->不允许添加->end */
		authorizedConfig.setPassword("");
		/* 设置返回类型 */
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("authorizedCode", authorizedConfig.getAuthorizedCode());
		returnMap.put("authorizedId", authorizedConfig.getId());

		String success = "";
        /*至于使用可开通才能进 活动判断*/
        if(authorizedConfig.getAuthorizedCodeType()!=null&&authorizedConfig.getAuthorizedCodeType().intValue()==1&&
            authorizedConfig.getIsOpen()!=null&&authorizedConfig.getIsOpen().intValue()==1){
            /*判断当前时间是否在 活动时间内 ,条件成立返回价格 并填充价格。*/
            Map<String,String>checkActivityMap = checkActivity(authorizedConfig); 
            if(checkActivityMap==null||checkActivityMap.size()<=0){
                success = "false";
            }else{
                success = checkActivityMap.get("success");
                if( StringUtils.isNotBlank(checkActivityMap.get("unitPrice"))){
                    BigDecimal bigDecimal = new BigDecimal(checkActivityMap.get("unitPrice"));
                    BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
                    BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
                    authorizedConfig.setUnitPrice(bigDecimalMultiply.doubleValue());
                    double unitPrice = bigDecimalMultiply.doubleValue();
                    returnMap.put("unitPrice", unitPrice);
                }
            }
        }else{
            success = "false";
        }
        if("false".equals(success)){
            BigDecimal bigDecimal = new BigDecimal(Double.toString(authorizedConfig.getUnitPrice()));
            BigDecimal bigDecimal2 = new BigDecimal(Double.toString(100));
            BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
            double unitPrice = bigDecimalMultiply.doubleValue();
            returnMap.put("unitPrice", unitPrice);
        }
        /*设置返回类型->end*/
        return new ResponseEnvelope<>(returnMap, msgId, true);
	}

	/**
	 * 购买序列号
	 * 
	 * @param msgId
	 * @param terminalImei
	 * @param authorizedCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/binding")
	@ResponseBody
	public Object binding(String msgId, String terminalImei, String authorizedCode, HttpServletRequest request) {
		if (StringUtils.isBlank(terminalImei))
			return new ResponseEnvelope<>(false, "参数terminalImei不能为空", msgId);
		if (StringUtils.isBlank(authorizedCode))
			return new ResponseEnvelope<>(false, "参数authorizedCode不能为空", msgId);
		/* 得到userId */
		Integer userId = null;
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (loginUser != null) {
			userId = loginUser.getId();
		}
		/* 得到userIdEnd */
		AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
		authorizedConfigSearch.setLimit(1);
		authorizedConfigSearch.setStart(0);
		authorizedConfigSearch.setAuthorizedCode(authorizedCode);
		List<AuthorizedConfig> authorizedConfigs = null;
		authorizedConfigs = authorizedConfigService.getPaginatedList(authorizedConfigSearch);
		if (authorizedConfigs == null || authorizedConfigs.size() < 1)
			return new ResponseEnvelope<>(false, "序列号不存在", msgId);
		AuthorizedConfig authorizedConfig = authorizedConfigs.get(0);
		/* get经销商付费sysDictionary */
		SysDictionary sysDictionaryDealersPay = sysDictionaryService.findOneByTypeAndValueKey("payModel", "dealersPay");
		if (sysDictionaryDealersPay == null)
			return new RuntimeException("请管理员添加数据字典:type:payModel;valueKey:dealersPay;name:经销商付费");
		if (sysDictionaryDealersPay.getValue().equals(authorizedConfig.getPayModelValue())) {
			/* 判定为经销商付费的序列号 */
			/* 识别是否支付 */
			SysDictionary sysDictionaryPayStatusFinish = sysDictionaryService.findOneByTypeAndValueKey("payStatus",
					"payStatus_finish");
			if (sysDictionaryPayStatusFinish == null)
				return new RuntimeException("请管理员添加数据字典:type:payStatus;valueKey:payStatus_finish;name:已付款");
			if (!authorizedConfig.getPayStatusValue().equals(sysDictionaryPayStatusFinish.getValue()))
				return new ResponseEnvelope<>(false, "该序列号还没有支付，请点击购买开通！", msgId);
			/* 识别是否支付->end */
			/* 验证是否被使用 */
			if (new Integer(1).equals(authorizedConfig.getState()))
				return new ResponseEnvelope<>(false, "序列号已使用！请输入其他序列号！", msgId);

			/* 账户与设备绑定 */
			SysUser sysUser2 = sysUserService.get(loginUser.getId());
			if (sysUser2 == null || sysUser2.getId() == null)
				return new RuntimeException("未找到该用户");
			if (StringUtils.isNotBlank(sysUser2.getUserImei())) {
				if (!StringUtils.equals(terminalImei, sysUser2.getUserImei()))
					// return new RuntimeException("序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据");
					return new ResponseEnvelope<>(false, "序列号数据出错,该用户在其他设备上绑定了序列号,该情况不予出现,请检测序列号数据", msgId);
			} else {
				/* 绑定设备号 */
				sysUser2.setUserImei(terminalImei);
				sysUserService.update(sysUser2);
			}
			/* 账户与设备绑定->end */
			/* 允许绑定 */
			authorizedConfig.setUserId(userId);
			authorizedConfig.setState(1);
			authorizedConfig.setTerminalImei(terminalImei);

			Map<String, String> resMap = authorizedConfigActivation(authorizedConfig, loginUser, "yes");
			if ("true".equals(resMap.get("success"))) {
				authorizedConfigService.update(authorizedConfig);
				//添加授权码时判断是否需要绑定设备
				sysUserEquipmentService.authorizedConfigBindingEquipment(userId,terminalImei);
				AuthorizedConfigCacher.remove(authorizedConfig.getId());
				return new ResponseEnvelope<>(true, "绑定成功", msgId);
			} else {
				return new ResponseEnvelope<>(false, resMap.get("data"), msgId);
			}
		} else {
			return new ResponseEnvelope<>(false, "只能绑定经销商付费的序列号", msgId);
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
		/* 激活授权码 - 关于新需求 购买时激活 */
		try {
			if (authorizedConfig.getStartTime() != null) {
				resMap.put("success", "true");
				return resMap;
			}
			Date date = new Date();
			Date latestTime = authorizedConfig.getLatestActivationDate();
			//如果设置了序列号到期自动激活的，就把激活时间设置为创建序列号时设置的时间
			if(authorizedConfig.getActivationTimeType().intValue() == 2) {
				//如果超过了最晚激活时间，就把最晚激活时间设置为当前时间
				if(latestTime.before(date)) {
					date = latestTime;
				}
			}
			authorizedConfig.setStartTime(date);

			/* 能使用的天数 */
			Integer validDay = authorizedConfig.getValidDay();
			if (validDay == null || validDay.intValue() <= 0) {
				resMap.put("data", "序列号数据出错,有效天数缺少值");
				resMap.put("success", "false");
				return resMap;
			}
			/* 判断是否存在销售延期缩短有效期的策略 */
			if ("yes".equals(purchase)) {
				String validStrategy = authorizedConfig.getValidStrategy();
				if (StringUtils.isNotBlank(validStrategy)) {
					if (validStrategy.indexOf("-") > -1) {
						validStrategy = validStrategy.replace("-", "");
						Integer dayNum = Integer.parseInt(validStrategy);
						validDay = validDay.intValue() - dayNum.intValue();
						Date validTime = Utils.getDateAfter(date, validDay);
						authorizedConfig.setValidTime(validTime);
					} else {
						Integer dayNum = Integer.parseInt(validStrategy);
						validDay = validDay.intValue() + dayNum.intValue();
						Date validTime = Utils.getDateAfter(date, validDay);
						authorizedConfig.setValidTime(validTime);
					}
				} else {
					Date validTime = Utils.getDateAfter(date, validDay);
					authorizedConfig.setValidTime(validTime);
				}
			} else {
				Date validTime = Utils.getDateAfter(date, validDay);
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
						Double priceStrategy = null;
						if (balanceAmount != null) {
							priceStrategy = (Double.parseDouble(priceStrategyStr) * 100) + balanceAmount;
						} else {
							priceStrategy = (Double.parseDouble(priceStrategyStr) * 100);
						}
						sysUser = new SysUser();
						sysUser.setBalanceAmount(priceStrategy);
						sysUser.setId(loginUser.getId());
						sysUserService.updateBalanceAmountByUserId(sysUser);
					} else {
						resMap.put("data", "用户不存在");
						resMap.put("success", "false");
						return resMap;
					}
				}
			}
		} catch (Exception e) {
			logger.error("authorizedConfigActivation:" + e);
			resMap.put("data", "authorizedConfigActivation:" + e);
			resMap.put("success", "false");
			return resMap;
		}
		resMap.put("success", "true");
		return resMap;
	}

	/**
	 * 序列号续费方法
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/continueCost")
	@ResponseBody
	public Object continueCost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/* 获取当前用户 */
		String msgId = request.getParameter("msgId");
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
			return new ResponseEnvelope<DesignPlan>(false, "请先登录！", msgId);
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		Date validTime = null;
		Date currentTime = null;
		AuthorizedConfig authorizedConfig = null;

		String authorizedConfigId = request.getParameter("id");

		String type = request.getParameter("type");
		if (authorizedConfigId == null || "".equals(authorizedConfigId)) {
			return new ResponseEnvelope<>(true, "缺少参数：id", msgId);
		}
		if (msgId == null || "".equals(msgId)) {
			return new ResponseEnvelope<>(true, "缺少参数：msgId", msgId);
		}

		try {
			authorizedConfig = authorizedConfigService.get(Integer.parseInt(authorizedConfigId));
			if (authorizedConfig != null) {
				validTime = authorizedConfig.getValidTime();
				currentTime = new Date();
			} else {
				return new ResponseEnvelope<>(true, "序列号不存在", msgId);
			}

			Integer dayNum = null;
			SysDictionary sysDictionary = new SysDictionary();
			sysDictionary.setValuekey("formalValidTime");
			List<SysDictionary> validTimeList = sysDictionaryService.getList(sysDictionary);
			if (validTimeList != null && validTimeList.size() > 0) {
				dayNum = validTimeList.get(0).getValue();
			}
			if (dayNum == null || dayNum.intValue() < 0) {
				return new ResponseEnvelope<>(true, "授权码操作失败，请联系客服", msgId);
			}

			if (validTime == null) {
				Calendar calendar = Calendar.getInstance();
				Date date = new Date(System.currentTimeMillis());
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +1);
				date = calendar.getTime();
				authorizedConfig.setValidTime(date);
			}
			if ("1".equals(type)) {
				/* 判断是否存在销售延期缩短有效期的策略 */
				String validStrategy = authorizedConfig.getValidStrategy();
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
				if (validTime != null) {
					if (!validTime.after(currentTime)) {/* 有效时间在当前时间之前，说明已经过期，从当前时间上开始续时间 */
						Date date = Utils.getDateAfter(new Date(), dayNum);
						authorizedConfig.setValidTime(date);
					}
					if (validTime.after(currentTime)) {/* 有效时间在当前时间之前，说明没过期,从原本时间上 加长时间 */
						Date date = Utils.getDateAfter(validTime, dayNum);
						authorizedConfig.setValidTime(date);
					}
				}
			} else {
				if (validTime != null) {
					if (!validTime.after(currentTime)) {/* 有效时间在当前时间之前，说明已经过期，从当前时间上开始续时间 */
						Date date = Utils.getDateAfter(new Date(), dayNum);
						authorizedConfig.setValidTime(date);
					}
					if (validTime.after(currentTime)) {/* 有效时间在当前时间之前，说明没过期,从原本时间上 加长时间 */
						Date date = Utils.getDateAfter(validTime, dayNum);
						authorizedConfig.setValidTime(date);
					}
				}
			}
			authorizedConfig.setAuthorizedCodeType(0);
			authorizedConfig.setId(Integer.parseInt(authorizedConfigId));
			authorizedConfig.setValidState(1);
			authorizedConfigService.update(authorizedConfig);
			if ("1".equals(type)) {
				return new ResponseEnvelope<>(true, "开通成功", msgId);
			} else {
				return new ResponseEnvelope<>(true, "续费成功", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if ("1".equals(type)) {
				return new ResponseEnvelope<>(true, "开通失败", msgId);
			} else {
				return new ResponseEnvelope<>(true, "续费失败", msgId);
			}
		}
	}

	@RequestMapping("/clearCacheUser")
	@ResponseBody
	public Object clearCacheUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		authorizedConfigService.delAuthorizedPastDueUserCacheJob();
		return new ResponseEnvelope<>(true, "清除成功", "1111");

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

		/* 获得所有正式的授权码 遇到只剩30天的的给于就提醒 */
		AuthorizedConfig authorizedConfig30 = new AuthorizedConfig();
		authorizedConfig30.setIsDeleted(0);
		authorizedConfig30.setAuthorizedCodeType(0);
		String validTimeBegin30 = getDateAfter(new Date(), 30) + " 00:00:00";
		String validTimeEnd30 = getDateAfter(new Date(), 30) + " 23:59:59";
		authorizedConfig30.setValidTimeBegin(validTimeBegin30);
		authorizedConfig30.setValidTimeEnd(validTimeEnd30);

		day30List = authorizedConfigService.getList(authorizedConfig30);
		for (AuthorizedConfig authorizedConfig : day30List) {
			SysUser sysUser = null;
			String mobile = null;
			sysUser = sysUserService.get(authorizedConfig.getUserId());
			if (sysUser != null) {
				mobile = sysUser.getMobile();
			}
			if (StringUtils.isNotBlank(mobile)) {
				String message = null;
				message = MessageFormat.format(SmsClient.AUTHORIZED_CONFIG_VALID_TIME30,
						authorizedConfig.getAuthorizedCode());
				// message= "您的授权码 :"+authorizedConfig.getAuthorizedCode()+"将会在30天后过期，请及时续费";
				message = URLEncoder.encode(message, "UTF-8");
				String params = "phone=" + mobile + "&message=" + message;
				result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
				if ("0".equals(result)) {
					logger.info(message + "发送成功！");

				} else {
					logger.error(message + "发送验证短信异常！");

				}
			}
		}

		/* 获得所有正式的授权码 遇到只剩7天的的给于就提醒 */
		AuthorizedConfig authorizedConfig7 = new AuthorizedConfig();
		authorizedConfig7.setIsDeleted(0);
		authorizedConfig7.setAuthorizedCodeType(0);
		String validTimeBegin7 = getDateAfter(new Date(), 7) + " 00:00:00";
		String validTimeEnd7 = getDateAfter(new Date(), 7) + " 23:59:59";
		authorizedConfig7.setValidTimeBegin(validTimeBegin7);
		authorizedConfig7.setValidTimeEnd(validTimeEnd7);
		day7List = authorizedConfigService.getList(authorizedConfig7);
		for (AuthorizedConfig authorizedConfig : day7List) {
			SysUser sysUser = null;
			String mobile = null;
			sysUser = sysUserService.get(authorizedConfig.getUserId());
			if (sysUser != null) {
				mobile = sysUser.getMobile();
			}
			if (StringUtils.isNotBlank(mobile)) {
				String message = null;
				// message =
				// MessageFormat.format(SmsClient.app.getString("registerContext"),"123",
				// SmsClient.VALID_TIME/60000,SmsClient.SERVICE_PHONE);
				message = MessageFormat.format(SmsClient.AUTHORIZED_CONFIG_VALID_TIME7,
						authorizedConfig.getAuthorizedCode());
				// message = MessageFormat.format(SmsClient.AUTHORIZED_CONFIG_VALID_TIME7,123);
				// message= "您的授权码 :"+authorizedConfig.getAuthorizedCode()+"将会在7天后过期，请及时续费";
				message = URLEncoder.encode(message, "UTF-8");
				String params = "phone=" + mobile + "&addserial=&message=" + message;
				result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
				if ("0".equals(result)) {
					logger.info(message + "发送成功！");

				} else {
					logger.error(message + "发送验证短信异常！");

				}
			}
		}
	}
	
	/**
	 * 给相应的 用户配置相应的角色
	 * @param type
	 * @param userId
	 */
	private Integer setRoleToUser(int type,Integer userId) {
		SysUser sysUser = sysUserService.get(userId);
		int sellerId = sysRoleMapper.getRoleByCode("U3D_seller");//经销商角色id
		int firmId = sysRoleMapper.getRoleByCode("U3D_firm");//厂商角色id
		List<AuthorizedConfig> authorizedConfigList = authorizedConfigService.findAllByUserId(userId);
		if(Lists.isNotEmpty(authorizedConfigList)) {
			if(authorizedConfigList.size() == 1) {
				if(type == 3) {
					//判断该用户是否有经销商角色
					SysUserRole sysUserRole = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(sellerId));
					if(sysUserRole == null) {
						this.addSysUserRole(userId, sellerId);
					}
				}else if(type == 2) {
					SysUserRole sysUserRole = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(sellerId));
					if(sysUserRole != null) {//删除经销商权限
						sysUserRoleMapper.deleteByUserIdAndRoleId(userId, new Integer(sellerId));
					}
					//判断该用户是否有厂商角色
					SysUserRole sysUserRole2 = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(firmId));
					if(sysUserRole2 == null) {
						this.addSysUserRole(userId, firmId);
					}
				}else {
					SysUserRole sysUserRole = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(sellerId));
					if(sysUserRole != null) {//删除经销商权限
						sysUserRoleMapper.deleteByUserIdAndRoleId(userId, new Integer(sellerId));
					}
					SysUserRole sysUserRole2 = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(sellerId));
					if(sysUserRole2 != null) {//删除厂商权限
						sysUserRoleMapper.deleteByUserIdAndRoleId(userId, new Integer(sellerId));
					}
				}
				return new Integer(type);
			}else {
				if(type == 3) {//经销商
					//如果用户不是厂商，就把用户类型修改为经销商，并配置相应角色，如果是就跳过
					if(sysUser.getUserType().intValue() != 2) {
						//判断该用户是否有经销商角色
						SysUserRole sysUserRole = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(sellerId));
						if(sysUserRole == null) {
							this.addSysUserRole(userId, sellerId);
						}
						sysUser.setUserType(new Integer(3));
						logger.error("sysUser.toString()---------经销商------"+sysUser.toString());
						int id = sysUserService.update(sysUser);
						if(id > 0) {
							logger.error("userId ============================" + id);
							return new Integer(3);
						}
					}
				}else if(type == 2) {//厂商
					//判断该用户是否有经销商角色
					SysUserRole sysUserRole = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(firmId));
					if(sysUserRole == null) {
						this.addSysUserRole(userId, firmId);
					}
					SysUserRole sysUserRole2 = sysUserRoleMapper.getByUserIdAndRoleId(userId, new Integer(sellerId));
					if(sysUserRole2 != null) {//删除经销商权限
						sysUserRoleMapper.deleteByUserIdAndRoleId(userId, new Integer(sellerId));
					}
					//把用户类型修改为厂商
					sysUser.setUserType(new Integer(2));
					logger.error("sysUser.toString()------厂商---------"+sysUser.toString());
					int id = sysUserService.update(sysUser);
					if(id > 0) {
						return new Integer(2);
					}
				}else if(type != 2 && type != 3) {
					if(sysUser.getUserType().intValue() != 2 
							&& sysUser.getUserType().intValue() != 3) {
						sysUser.setUserType(new Integer(type));
						logger.error("sysUser.toString()---------其他------"+sysUser.toString());
						int id = sysUserService.update(sysUser);
						if(id > 0) {
							return new Integer(type);
						}
					}
				}
				return null;
			}
		}
		return null;
	}

	/**
	 * 给用户添加角色
	 * @param userId
	 * @param roleId
	 */
	private void addSysUserRole(Integer userId,Integer roleId) {
		SysUserRole s = new SysUserRole();
		s.setUserId(userId);
		s.setRoleId(roleId);
		s.setCreator("nologin");
		s.setGmtCreate(new Date());
		s.setModifier("nologin");
		s.setGmtModified(new Date());
		s.setIsDeleted(0);
		s.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)
				+ "_" + Utils.generateRandomDigitString(6));
		
		sysUserRoleMapper.insertSelective(s);
	}
}
