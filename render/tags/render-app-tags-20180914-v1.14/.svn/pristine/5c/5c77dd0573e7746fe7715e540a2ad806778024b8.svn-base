package com.nork.product.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.sandu.common.LoginContext;

@Controller
@RequestMapping("/{style}/web/product/authorizedConfig")
public class WebAuthorizedConfigController {
	private static Logger logger = Logger.getLogger(WebAuthorizedConfigController.class);
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysDictionaryService sysDictionaryService;

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
		Map map =LoginContext.getTokenData();
		if (null == map) {
			return new ResponseEnvelope<>(false,"请重新登录！");
		}
		LoginUser loginUser= new LoginUser();
		String appKey = map.get("appKey").toString();
		String mediaType = request.getHeader("MediaType");
		Long uTypeValue = (Long)map.get("utype");
		String loginName = (String) map.get("uname");
		int uType = uTypeValue.intValue();
		long id =(long) map.get("uid");
		loginUser.setId(new Long(id).intValue());
		loginUser.setLoginName(loginName);
		loginUser.setUserType(Integer.valueOf(uType));
		loginUser.setAppKey(appKey);
		loginUser.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
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
//						if (Utils.enableRedisCache()) {
//							baseBrand = BaseBrandCacher.get(Utils.getIntValue(bid));
//						} else {
							baseBrand = baseBrandService.get(Utils.getIntValue(bid));
//						}

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

}
