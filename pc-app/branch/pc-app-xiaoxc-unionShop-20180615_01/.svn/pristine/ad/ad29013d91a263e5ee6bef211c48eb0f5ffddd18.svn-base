package com.nork.system.controller.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nork.common.cache.CacheManager;
import com.nork.common.properties.AppProperties;
import com.nork.pay.model.PayAccount;
import com.nork.pay.service.PayAccountService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.PicType;
import com.nork.common.jwt.Jwt;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginMenu;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.ResDistributeUtils;
import com.nork.common.util.SendEmail;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.service.DesignPlanService;
import com.nork.pay.alipay.util.UtilDate;
import com.nork.product.model.ProCategory;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.ProCategoryService;
import com.nork.system.cache.BaseAreaCacher;
import com.nork.system.cache.DesignerWorksCacher;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.cache.SysUserFansCacher;
import com.nork.system.dao.SysRoleMapper;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.dao.SysUserRoleMapper;
import com.nork.system.common.EquipmentConstants;
import com.nork.system.model.BaseArea;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysFunc;
import com.nork.system.model.SysRole;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUser.ResourcesUrl;
import com.nork.system.model.SysUserFans;
import com.nork.system.model.SysUserLastLoginLog;
import com.nork.system.model.SysUserLoginLog;
import com.nork.system.model.SysUserRole;
import com.nork.system.model.UserAccess;
import com.nork.system.model.UserLevelCfg;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.search.BaseMessageRecieveSearch;
import com.nork.system.model.search.DesignerWorksSearch;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.model.search.SysUserRoleSearch;
import com.nork.system.model.search.SysUserSearch;
import com.nork.system.model.search.UserAccessSearch;
import com.nork.system.model.small.SysUserSmall;
import com.nork.system.model.web.WBaseArea;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.DesignerWorksService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysFuncService;
import com.nork.system.service.SysResLevelCfgService;
import com.nork.system.service.SysRoleService;
import com.nork.system.service.SysUserEquipmentService;
import com.nork.system.service.SysUserFansService;
import com.nork.system.service.SysUserLastLoginLogService;
import com.nork.system.service.SysUserLoginLogService;
import com.nork.system.service.SysUserRoleService;
import com.nork.system.service.SysUserService;
import com.nork.system.service.SysUserSystemOperationLogService;
import com.nork.system.service.UserAccessService;
import com.nork.system.sms.httpclient.SmsClient;
import com.nork.user.model.bo.UserRegisterInfoBo;
import com.nork.user.service.SysUserRegisterInfoService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.service.BaseBrandService;

/**
 * @Title: SysUserController.java
 * @Package com.nork.system.controller
 * @Description:系统-用户Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/system/sysUser")
public class WebSysUserController {

	String cacheEnable = Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE, "0");
	private static Logger logger = Logger.getLogger(WebSysUserController.class);
	private final JsonDataServiceImpl<SysUser> JsonUtil = new JsonDataServiceImpl<SysUser>();
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user";

	//private final String PIC_UPLOAD_PATH = "system.sysUser.pic.upload.path";

	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	/*** 获取配置文件 webSocket.properties */
	private final static ResourceBundle webSocket = ResourceBundle
			.getBundle("config/webSocket");

	private final String SERVERURL = app.getString("app.server.url");
	private final String SSOURL = app.getString("app.sso.url");
	private final String RESOURCESURL = app.getString("app.resources.url");
	private final String USERURL = app.getString("app.user.url");
	private final String SITENAME = Utils.getValue("app.server.siteName",
			"nork");
	private final String SITEKEY = Utils.getValue("app.server.siteKey",
			"online");
	//服务化地址集合
	private static final String APP_SERVITIZATION_URLS = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_SERVITIZATION_URLS,"");
	//服务化地址key
	private static final String SERVER_KEY = "serverKey";
	//服务化地址url
	private static final String SERVER_URL = "serverUrl";

	private static List<ResourcesUrl> resourcesUrls = getResourcesUrls();
	
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private SysUserEquipmentService sysUserEquipmentService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysFuncService sysFuncService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysUserFansService sysUserFansService;
	@Autowired
	private BaseMessageService baseMessageService;
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignerWorksService designerWorksService;
	@Autowired
	private UserAccessService userAccessService;
	@Autowired
	private ProCategoryService categoryService;
	@Autowired
	private SysUserLoginLogService sysUserLoginLogService;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysUserLastLoginLogService sysUserLastLoginLogService;
	@Autowired
	private SysUserRegisterInfoService sysUserRegisterInfoService;
	@Autowired
	private SysResLevelCfgService sysResLevelCfgService;
	@Autowired
	private SysUserSystemOperationLogService sysUserSystemOperationLogService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private PayAccountService payAccountService;

	/**
	 * 用户中心-消息
	 * 
	 * @param sysUserSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userCenter")
	public Object userCenter(
			@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			sysUser = sysUserService.get(loginUser.getId());
			ResPic resPic = resPicService.get(sysUser.getPicId());
			request.setAttribute("picPath",
					resPic == null ? "" : resPic.getPicPath());
		}

		// 查询用户粉丝列表
		request.setAttribute("fansList", fansList(request));
		// 查询用户关注的列表
		request.setAttribute("attentionList", attentionList(request));
		// 查询消息列表
		// BaseMessageSearch baseMessageSearch = new BaseMessageSearch();
		BaseMessageRecieveSearch baseMessageRecieveSearch = new BaseMessageRecieveSearch();
		// baseMessageSearch.setUserId(sysUser.getId());
		// List<UserMessageDesignPlan> list = new
		// ArrayList<UserMessageDesignPlan> ();
		List<BaseMessageRecieve> recievelist = new ArrayList<BaseMessageRecieve>();
		List<UserMessageDesignPlan> umdplist = new ArrayList<UserMessageDesignPlan>();
		List<UserMessageDesignPlan> messageList = new ArrayList<UserMessageDesignPlan>();
		// List<UserMessageDesignPlan> designList =new
		// ArrayList<UserMessageDesignPlan>();
		UserMessageDesignPlan userMessageDesignPlan = new UserMessageDesignPlan();
		// recievelist =
		// baseMessageRecieveService.getPaginatedList(baseMessageRecieveSearch);
		baseMessageRecieveSearch.setOrder("gmt_create");
		baseMessageRecieveSearch.setOrderNum("desc");
		recievelist = baseMessageRecieveService
				.getList(baseMessageRecieveSearch);
		for (int i = 0; i < recievelist.size(); i++) {
			if (loginUser.getId() == recievelist.get(i).getUserId()) {
				userMessageDesignPlan.setReceiverId(recievelist.get(i)
						.getUserId());
			} else {
				// userMessageDesignPlan.setUserId(loginUser.getId());
				userMessageDesignPlan.setReceiverId(loginUser.getId());
			}
			userMessageDesignPlan.setId(recievelist.get(i).getId());
			userMessageDesignPlan.setMessageId(recievelist.get(i)
					.getMessageId());
			umdplist = baseMessageService
					.getWebLettersList(userMessageDesignPlan);
			for (UserMessageDesignPlan userMessageDesignPlan2 : umdplist) {
				DesignPlan designPlan = new DesignPlan();
				designPlan.setId(userMessageDesignPlan2.getBusinessObjId());
				List<DesignPlan> designPlanlist = designPlanService
						.getList(designPlan);
				if (designPlanlist.size() > 0) {
					for (DesignPlan designPlan2 : designPlanlist) {
						userMessageDesignPlan2.setPlanName(designPlan2
								.getPlanName());
						messageList.add(userMessageDesignPlan2);
					}
				} else {
					messageList.add(userMessageDesignPlan2);
				}

			}
		}
		request.setAttribute("messageList", messageList);
		return Utils.getPageUrl(request, JSPMAIN + "/myCenter");
	}

	// 查询用户粉丝列表
	public List<SysUserFans> fansList(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser = sysUserService.get(loginUser.getId());
		}
		SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
		sysUserFansSearch.setUserId(sysUser.getId());
		List<SysUserFans> fansList = sysUserFansService
				.getPaginatedList(sysUserFansSearch);
		if (CustomerListUtils.isNotEmpty(fansList)) {
			for (SysUserFans userFans : fansList) {
				SysUser user = sysUserService.get(userFans.getFansUserId());
				userFans.setNickName(user == null ? "" : user.getNickName());
				ResPic resPic = resPicService.get(sysUser.getPicId());
				userFans.setPic(resPic == null ? "" : resPic.getPicPath());
			}
		}
		return fansList;
	}

	// 查询用户关注的列表
	public List<SysUserFans> attentionList(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser = sysUserService.get(loginUser.getId());
		}

		SysUserFansSearch userFansSearch = new SysUserFansSearch();
		userFansSearch.setFansUserId(sysUser.getId());
		List<SysUserFans> attentionList = sysUserFansService
				.getPaginatedList(userFansSearch);
		if (CustomerListUtils.isNotEmpty(attentionList)) {
			for (SysUserFans userFans : attentionList) {
				SysUser user = sysUserService.get(userFans.getUserId());
				userFans.setNickName(user == null ? "" : user.getNickName());
				ResPic resPic = resPicService.get(sysUser.getPicId());
				userFans.setPic(resPic == null ? "" : resPic.getPicPath());
			}
		}
		return attentionList;
	}

	/**
	 * 用户中心-设置
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userSetting")
	public Object userSetting(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			sysUser = sysUserService.get(loginUser.getId());

			String areaLongCode = sysUser.getAreaLongCode();
			if (areaLongCode != null && !"".equals(areaLongCode)) {
				String[] str = areaLongCode.split("\\.");
				if (str != null && str.length > 0 && str.length <= 2) {
					request.setAttribute("proId", str[1]);
				} else if (str != null && str.length > 2 && str.length <= 3) {
					request.setAttribute("proId", str[1]);
					request.setAttribute("cityId", str[2]);
				} else if (str != null && str.length > 3 && str.length <= 4) {
					request.setAttribute("proId", str[1]);
					request.setAttribute("cityId", str[2]);
					request.setAttribute("cityCode", str[3]);
				}
			}
		}

		if (sysUser.getPicId() != null) {
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser
					.getPicId()));
			request.setAttribute("picPath",
					resPic == null ? "" : resPic.getPicPath());
		}

		// 查询用户粉丝列表
		request.setAttribute("fansList", fansList(request));
		// 查询用户关注的列表
		request.setAttribute("attentionList", attentionList(request));

		request.setAttribute("sysUser", sysUser);

		return Utils.getPageUrl(request, JSPMAIN + "/mySetting");
	}

	/**
	 * 访问主页面
	 * 
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		// return JSPMAIN + "/designerCertification/designerCertification";
		return Utils.getPageUrl(request, JSPMAIN
				+ "/designerCertification/designerCertification");
	}

	/**
	 * 设计师--重新认证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recertification")
	public Object recertification(@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		// SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			sysUser.setId(loginUser.getId());
			int i = sysUserService.update(sysUser);
			logger.info("i:" + i);
			sysUser = sysUserService.get(loginUser.getId());
		}

		if (sysUser.getPicId() != null) {
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser
					.getPicId()));
			request.setAttribute("picPath",
					resPic == null ? "" : resPic.getPicPath());
		}

		request.setAttribute("sysUser", sysUser);

		return Utils.getPageUrl(request, JSPMAIN
				+ "/designerCertification/designerCertification");
		// return "redirect:" + Utils.getPageUrl(request,
		// "/online/user/designerCertification/designerCertification.jsp");
	}

	/**
	 * 设计师--设计师认证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/designerCertification")
	public Object designerCertification(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			sysUser = sysUserService.get(loginUser.getId());
		}

		if (sysUser.getPicId() != null) {
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser
					.getPicId()));
			request.setAttribute("picPath",
					resPic == null ? "" : resPic.getPicPath());
		}

		request.setAttribute("sysUser", sysUser);

		return Utils.getPageUrl(request, JSPMAIN
				+ "/designerCertification/certificationInformation");
	}

	/**
	 * 设计师--设计师认证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/designerAuthenticate")
	public Object designerAuthenticate(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			sysUser = sysUserService.get(loginUser.getId());
		}

		if (sysUser.getPicId() != null) {
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser
					.getPicId()));
			request.setAttribute("picPath",
					resPic == null ? "" : resPic.getPicPath());
		}

		request.setAttribute("sysUser", sysUser);

		// return new
		// ResponseEnvelope<SysUser>(sysUser,sysUser.getMsgId(),true);
		return Utils.getPageUrl(request, JSPMAIN
				+ "/designerCertification/certificationInformation");
	}

	/**
	 * 设计师--驳回认证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rejectAuthenticate")
	public Object rejectAuthenticate(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			sysUser = sysUserService.get(loginUser.getId());
		}

		if (sysUser.getPicId() != null) {
			ResPic resPic = resPicService.get(Integer.valueOf(sysUser
					.getPicId()));
			request.setAttribute("picPath",
					resPic == null ? "" : resPic.getPicPath());
		}

		request.setAttribute("sysUser", sysUser);

		// return new
		// ResponseEnvelope<SysUser>(sysUser,sysUser.getMsgId(),true);
		return Utils.getPageUrl(request, JSPMAIN
				+ "/designerCertification/certificationNotPass");
	}

	/**
	 * 个人特长
	 */
	@RequestMapping(value = "/specialtylist")
	@ResponseBody
	public Object specialtylist(
			@PathVariable String style,
			@ModelAttribute("sysDictionarySearch") SysDictionarySearch sysDictionarySearch,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<SysDictionary> list = new ArrayList<SysDictionary>();
		int total = 0;
		try {
			total = sysDictionaryService.getCount(sysDictionarySearch);
			logger.info("total:" + total);

			if (total > 0) {
				sysDictionarySearch.setType("designstyles");
				list = sysDictionaryService.getList(sysDictionarySearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysDictionary>(false, "数据异常!",
					sysDictionarySearch.getMsgId());
		}
		return new ResponseEnvelope<SysDictionary>(total, list,
				sysDictionarySearch.getMsgId());
	}

	/**
	 * 查询已获取的个人特长
	 */
	@RequestMapping(value = "/acquiredSpecialtylist")
	@ResponseBody
	public Object acquiredSpecialtylist(@PathVariable String style,
			@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// List<SysDictionary> list = new ArrayList<SysDictionary> ();
		// List<SysUser> sysUserlist = new ArrayList<SysUser> ();
		// SysUser sysUser = new SysUser();
		SysUserSearch sysUserSearch = new SysUserSearch();

		// 获取登录用户
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<UserProductCollect>(false,
					"登录超时，请重新登录!", sysUser.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser.setId(loginUser.getId());
		}

		int total = 0;
		try {
			total = sysUserService.getCount(sysUserSearch);
			logger.info("total:" + total);

			if (total > 0) {
				// sysDictionarySearch.setType("designstyles");
				sysUser = sysUserService.get(sysUser.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",
					sysUser.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(sysUser, sysUser.getMsgId(), true);
	}

	/**
	 * 验证原密码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkOldPassword")
	public void checkCompanyCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String oldPwd = request.getParameter("oldPassword");

		String flag = "false";
		// 得到登录用户
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		SysUser sysUser = sysUserService.get(loginUser.getId());
		if (sysUser.getPassword().equals(oldPwd)) {
			flag = "true";
		} else {
			flag = "false";
		}
		response.getWriter().write(flag);
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/updatePassword")
	public void updatePassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String newPassword = request.getParameter("newPassword");

		String flag = "false";
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		SysUser sysUser = sysUserService.get(loginUser.getId());
		sysUser.setPassword(newPassword);
		sysUserService.update(sysUser);
		List<SysUser> list = null;
		
		if (Utils.enableRedisCache()) {
			list = SysUserCacher.getSysList();
		} else {
			list = sysUserService.getSysList();
		}

//		//request.getSession().setAttribute("sysUserList", list);
		response.getWriter().write(flag);
	}


	/**
	 * 获取 websocket IP接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("webSocketIP")
	@ResponseBody
	public Object webSocketIP(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String msgId = request.getParameter("msgId");
			if (msgId == null || "".equals(msgId)) {
				return new ResponseEnvelope<>(false, "缺少参数msgId");
			}
			String siteUrl = app.getString("ws.server.site.url");
			String siteName = Utils.getPropertyName("app",
					"app.server.siteName", "app");
			String webSocketServer = webSocket
					.getString("app.webSocket.server");
			String payCallBackServer = webSocket
					.getString("app.PayCallBack.Server");
			String serverUrl = app.getString("app.server.url");
			/*
			 * String siteName= app.getString("app.server.siteName"); String
			 * siteKey=app.getString("app.server.siteKey");
			 */

			/* String serverUrl=url+siteName+siteKey; */
			if (webSocketServer == null || "".equals(webSocketServer)) {
				throw new RuntimeException("not found  : app.webSocket.server");
			}
			if (payCallBackServer == null || "".equals(payCallBackServer)) {
				throw new RuntimeException("not found  : app.webSocket.server");
			}
			if (siteUrl == null || "".equals(siteUrl)) {
				throw new RuntimeException("not found  : ws.server.site.url");
			}

			webSocketServer = webSocketServer.replace("[IP]", siteUrl
					+ siteName);
			payCallBackServer = payCallBackServer.replace("[IP]", siteUrl
					+ siteName);

			Map<String, String> map = new HashMap<String, String>();
			map.put("webSocketServer", webSocketServer);
			map.put("webSocketServer", webSocketServer);
			map.put("payCallBackServer", payCallBackServer);
			map.put("serverUrl", serverUrl);

			ResponseEnvelope RES = new ResponseEnvelope();
			RES.setObj(map);
			RES.setSuccess(true);
			RES.setMsgId(msgId);
			logger.info("=========================="
					+ JSONObject.fromObject(RES).toString());
			return RES;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEnvelope<>(false, "数据异常!");
	}

	/**
	 * 递归查询分类
	 * 
	 * @param category
	 * @return
	 */
	public List<SearchProCategorySmall> recursionCategory2(
			ProCategory category, List<ProCategory> categoryAllList) {
		List<SearchProCategorySmall> childrenNodes = category
				.getChildrenNodes();
		ProCategorySearch search = new ProCategorySearch();
		search.setPid(category.getId());
		search.setLongCode(category.getLongCode());
		List<ProCategory> list = new ArrayList<ProCategory>();
		if (categoryAllList != null && categoryAllList.size() > 0) {
			for (ProCategory pc : categoryAllList) {
				if (pc.getPid().intValue() == search.getPid().intValue()
						&& pc.getLongCode().indexOf(category.getLongCode()) != -1) {
					list.add(pc);
				}
			}
		} else {
			list = categoryService.getList(search);
		}
		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
		}
		SearchProCategorySmall newCategory = null;
		for (ProCategory childrenNode : list) {
			newCategory = new SearchProCategorySmall();
			newCategory.setAa(childrenNode.getId());
			newCategory.setCc(childrenNode.getPid());
			newCategory.setDd(childrenNode.getName());
			newCategory.setBb(childrenNode.getCode());
			newCategory
					.setFf(recursionCategory2(childrenNode, categoryAllList));
			childrenNodes.add(newCategory);
		}
		category.setChildrenNodes(childrenNodes);

		return childrenNodes;
	}

	/**
	 * 递归查询分类
	 * 
	 * @param category
	 * @return
	 */
	public List<SearchProCategorySmall> recursionCategory(ProCategory category) {
		List<SearchProCategorySmall> childrenNodes = category
				.getChildrenNodes();
		ProCategorySearch search = new ProCategorySearch();
		search.setPid(category.getId());
		search.setLongCode(category.getLongCode());
		List<ProCategory> list = categoryService.getList(search);
		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
			SearchProCategorySmall newCategory = null;
			for (ProCategory childrenNode : list) {
				newCategory = new SearchProCategorySmall();
				newCategory.setAa(childrenNode.getId());
				newCategory.setCc(childrenNode.getPid());
				newCategory.setDd(childrenNode.getName());
				newCategory.setBb(childrenNode.getCode());
				newCategory.setFf(recursionCategory(childrenNode));
				childrenNodes.add(newCategory);
			}
			category.setChildrenNodes(childrenNodes);
		}
		return childrenNodes;
	}

	/**
	 * 用户退出
	 */
	@RequestMapping(value = "/loginout")
	public Object loginout(SysUser sysUser, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 清除session
		request.getSession().removeAttribute("loginUser");
		request.getSession().removeAttribute("loginUserToken");
		return "redirect:"
				+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/register")
	public Object register(HttpServletRequest request,
			HttpServletResponse response) {

		// 获取省份列表
		BaseArea baseArea = new BaseArea();
		baseArea.setLevelId(1);
		List<BaseArea> provincelist = baseAreaService.getList(baseArea);
		request.setAttribute("provincelist", provincelist);

		return Utils.getPageUrl(request, JSPMAIN + "/register");
	}

	@RequestMapping(value = "/registerSave")
	public void registerSave(@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String flag = "false";

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
		}
		try {
			sysUser.setGroupId(0);
			// 昵称和用户名为空则默认给手机号
			sysUser.setUserName(sysUser.getMobile());
			sysUser.setNickName(sysUser.getMobile());
			sysSave(sysUser, request);
			if (sysUser.getId() == null) {
				int id = sysUserService.add(sysUser, request);
				logger.info("add:id=" + id);
			} else {
				int id = sysUserService.update(sysUser);
				logger.info("update:id=" + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<SysUser> list = null;
		
		if (Utils.enableRedisCache()) {
			list = SysUserCacher.getSysList();
		} else {
			list = sysUserService.getSysList();
		}
//		//request.getSession().setAttribute("sysUserList", list);

		response.getWriter().write(flag);
	}

	@RequestMapping(value = "/save")
	public void save(@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String flag = "false";

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
		}
		try {
			String areaLongCode = "." + sysUser.getProvinceCode() + "."
					+ sysUser.getAreaId() + ".";
			sysUser.setAreaLongCode(areaLongCode);
			sysUser.setGroupId(0);
			sysUser.setLevel(1);
			sysSave(sysUser, request);
			if (sysUser.getId() == null) {
				int id = sysUserService.add(sysUser);
				logger.info("add:id=" + id);
			} else {
				int id = sysUserService.update(sysUser);
				logger.info("update:id=" + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<SysUser> list = null;
		
		if (Utils.enableRedisCache()) {
			list = SysUserCacher.getSysList();
		} else {
			list = sysUserService.getSysList();
		}
//		//request.getSession().setAttribute("sysUserList", list);
		response.getWriter().write(flag);
	}

	@RequestMapping(value = "/updateMobile")
	public void updateMobile(@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String flag = "false";

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
		}
		try {
			sysUserService.update(sysUser);

			request.getSession().removeAttribute("code");

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SysUser> list = null;
		
		if (Utils.enableRedisCache()) {
			list = SysUserCacher.getSysList();
		} else {
			list = sysUserService.getSysList();
		}
		//request.getSession().setAttribute("sysUserList", list);

		response.getWriter().write(flag);
	}

	@RequestMapping(value = "/getCityList")
	public void getCityList(String areaCode, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			// 获取省份下的城市列表
			BaseArea baseArea = new BaseArea();
			baseArea.setPid(areaCode);
			List<BaseArea> provincelist = baseAreaService.getList(baseArea);
			String json = JSONArray.fromObject(provincelist).toString();
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询设计师列表
	 * 
	 * @param sysUserSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDesignersList")
	public Object getDesignersList(
			@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,
			HttpServletRequest request, HttpServletResponse response) {
		List<SysUser> list = new ArrayList<SysUser>();
		StringBuffer areaCode = new StringBuffer();
		String provinceCode = sysUserSearch.getProvinceCode();
		String cityCode = sysUserSearch.getCityCode();
		if (StringUtils.isNotBlank(provinceCode)) {
			areaCode.append("." + provinceCode);
		}
		if (StringUtils.isNotBlank(cityCode)) {
			areaCode.append("." + cityCode);
		}
		if (StringUtils.isNotBlank(areaCode.toString())) {
			areaCode.append(".");
		}
		sysUserSearch.setAreaLongCode(areaCode.toString());
		int total = 0;
		try {
			sysUserSearch.setUserType(2);
			total = sysUserService.getCount(sysUserSearch);
			logger.info("total=" + total);
			if (total > 0) {
				sysUserSearch.setOrder("level desc");
				// list = sysUserService.getPaginatedList(sysUserSearch);
				list = sysUserService.getList(sysUserSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}

		ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(total,
				list);
		String designIds = request.getParameter("designIds");
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("designIds", designIds);
		request.setAttribute("search", sysUserSearch);

		return Utils.getPageUrl(request, JSPMAIN + "/designers_list");
		// return new
		// ResponseEnvelope<SysUser>(sysUserSearch,sysUserSearch.getMsgId(),true);
	}

	/**
	 * 获取 用户详情
	 */
	@RequestMapping(value = "/getUser")
	public Object getUser(HttpServletRequest request,
			HttpServletResponse response) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		SysUser sysUser = new SysUser();
		try {
			sysUser = sysUserService.get(loginUser.getId());
			if (sysUser != null && sysUser.getPicId() != null) {
				ResPic resPic = resPicService.get(sysUser.getPicId());
				sysUser.setPicPath(resPic == null ? "" : resPic.getPicPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}
		request.setAttribute("user", sysUser);
		return Utils.getPageUrl(request, JSPMAIN + "/userInfo");
	}

	/**
	 * 用户中心--热门设计师列表
	 * 
	 * @param sysUserSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTopDesignerList")
	public Object getTopDesignerList(
			@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,
			HttpServletRequest request, HttpServletResponse response) {
		List<SysUser> list = new ArrayList<SysUser>();
		int total = 0;
		// 省市查询条件 给areaLongCode赋值
		StringBuffer areaCode = new StringBuffer();
		String provinceCode = sysUserSearch.getProvinceCode();
		String cityCode = sysUserSearch.getCityCode();
		if (StringUtils.isNotBlank(provinceCode)) {
			areaCode.append("." + provinceCode);
		}
		if (StringUtils.isNotBlank(cityCode)) {
			areaCode.append("." + cityCode);
		}
		if (StringUtils.isNotBlank(areaCode.toString())) {
			areaCode.append(".");
		}
		sysUserSearch.setAreaLongCode(areaCode.toString());
		// sysUserSearch.setLevel(1);
		try {
			sysUserSearch.setUserType(2);
			total = sysUserService.getCount(sysUserSearch);
			logger.info("total=" + total);
			if (total > 0) {
				sysUserSearch.setOrder("level desc");
				list = sysUserService.getPaginatedList(sysUserSearch);
				for (SysUser user : list) {
					user.setFansCount(fansCount(user.getId()));
					user.setAttentionCount(attentionCount(user.getId()));
					user.setWorksCount(worksCount(user.getId()));
					user.setAccessCount(accessCount(user.getId()));
					// 获取设计师头像路径
					if (user.getPicId() != null && user.getPicId() > 0) {
						ResPic resPic = resPicService.get(user.getPicId());
						user.setPicPath(resPic == null ? "" : resPic
								.getPicPath());
					}
					// 获取设计师 地址
					String areaLongCode = user.getAreaLongCode();
					StringBuffer areaName = new StringBuffer();
					if (StringUtils.isNotBlank(areaLongCode)) {
						if (areaLongCode.contains(".")) {
							String area[] = areaLongCode.split("\\.");
							for (String code : area) {
								BaseArea baseArea = new BaseArea();
								baseArea.setAreaCode(code);
								List<BaseArea> areaList = baseAreaService
										.getList(baseArea);
								if (CustomerListUtils.isNotEmpty(areaList)) {
									areaName.append(areaList.get(0)
											.getAreaName());
								}
							}
						}
					} else {
						areaName.append("未知");
					}
					user.setAreaName(areaName.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}

		ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(total,
				list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysUserSearch);

		return Utils.getPageUrl(request, JSPMAIN + "/designerList");
		// return new
		// ResponseEnvelope<SysUser>(sysUserSearch,sysUserSearch.getMsgId(),true);
	}

	// 获取粉丝数量
	public int fansCount(Integer userId) {
		SysUserFans userFans = new SysUserFans();
		userFans.setUserId(userId);
		userFans.setIsDeleted(0);
		int fansCount = sysUserFansService.getFansCount(userFans);
		return fansCount;
	}

	// 获取关注数量
	public int attentionCount(Integer userId) {
		SysUserFans userFans = new SysUserFans();
		userFans.setFansUserId(userId);
		userFans.setIsDeleted(0);
		int attentionCount = sysUserFansService.getFansCount(userFans);
		return attentionCount;
	}

	// 获取作品数量
	public int worksCount(Integer userId) {
		DesignerWorksSearch designerWorksSearch = new DesignerWorksSearch();
		designerWorksSearch.setUserId(userId);
		int worksCount = designerWorksService.getCount(designerWorksSearch);
		return worksCount;
	}

	// 获取访问数量
	public int accessCount(Integer userId) {
		UserAccessSearch userAccessSearch = new UserAccessSearch();
		userAccessSearch.setBeAccessedId(userId);
		return userAccessService.getCount(userAccessSearch);
	}

	/**
	 * 设计师认证--接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewCertification")
	@ResponseBody
	public Object viewCertification(@PathVariable String style, String msgId,
			@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request) {
		String jsonStr = Utils.getJsonStr(request);
		
		// DesignerWorksCacher.getResPic(12);

		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if (sysUser == null) {
				return new ResponseEnvelope<SysUser>(false, "传参异常!", "none");
			}
		}

		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUser>(false, msg, msgId);
		}

		LoginUser loginUser = new LoginUser();
		// SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		}

		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if ("anonymous".equals(loginUser.getId())) {
			return "redirect:"
					+ Utils.getPageUrl(request, "/pages/online/user/login.jsp");
		} else {
			if (Utils.enableRedisCache()) {
				sysUser = SysUserCacher.get(loginUser.getId());
			} else {
				sysUser = sysUserService.get(loginUser.getId());
			}

		}
		if(sysUser==null)
		{
			sysUser = new SysUser();
			sysUser.setId(-1);
			sysUser.setId(-1);
		}
		if (sysUser.getSex() == null || sysUser.getSex().intValue() == 1) {
			if (StringUtils.isEmpty(sysUser.getPicPath())) {
				sysUser.setPicPath("/system/sysUser/pic/userImageMan.png");
				sysUser.setUserPic("/system/sysUser/pic/userImageMan.png");
			}
		} else {
			if (StringUtils.isEmpty(sysUser.getPicPath())) {
				sysUser.setPicPath("/system/sysUser/pic/userImageGirl.png");
				sysUser.setUserPic("/system/sysUser/pic/userImageGirl.png");
			}
		}

		return new ResponseEnvelope<SysUser>(sysUser, msgId, true);
	}

	/**
	 * 设计师粉丝列表
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/fansList")
	public Object fansList(Integer userId, HttpServletRequest request) {
		if (userId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数异常!");
		}
		SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
		sysUserFansSearch.setUserId(userId);
		SysUser sysUser = sysUserService.get(userId);
		request.setAttribute("userName",
				sysUser == null ? "" : sysUser.getUserName());
		List<SysUserFans> list = new ArrayList<SysUserFans>();
		int total = 0;
		try {
			total = sysUserFansService.getCount(sysUserFansSearch);
			if (total > 0) {
				list = sysUserFansService.getPaginatedList(sysUserFansSearch);
				if (CustomerListUtils.isNotEmpty(list)) {
					for (SysUserFans userFans : list) {
						SysUser user = sysUserService.get(userFans
								.getFansUserId());
						userFans.setNickName(user == null ? "" : user
								.getNickName());
						userFans.setUserName(user == null ? "" : user
								.getUserName());
						if (user != null) {
							ResPic resPic = resPicService.get(user.getPicId());
							userFans.setPic(resPic == null ? "" : resPic
									.getPicPath());
						}
						// 是否关注 1.关注 0未关注
						SysUserFansSearch userFansSearch = new SysUserFansSearch();
						userFansSearch.setFansUserId(userId);
						userFansSearch.setUserId(userFans.getFansUserId());
						int count = sysUserFansService.getCount(userFansSearch);
						userFans.setCount(count);
						// request.setAttribute("count", count);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}

		ResponseEnvelope<SysUserFans> res = new ResponseEnvelope<SysUserFans>(
				total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysUserFansSearch);
		request.setAttribute("userId", userId);
		String pageType = (String) request.getParameter("pageType");
		// String jumpType = (String)request.getParameter("jumpType");
		request.setAttribute("pageType", pageType);
		request.setAttribute("jumpType", "fans");
		String url = "";
		String type = (String) request.getParameter("type");
		if (type.equals("me")) {
			url = JSPMAIN + "/myFans_list";
		} else {
			url = JSPMAIN + "/designFans_list";
		}
		return Utils.getPageUrl(request, url);
	}

	/**
	 * 设计师关注的列表
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/attentionList")
	public Object attentionList(Integer userId, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (userId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数异常!");
		}
		SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
		sysUserFansSearch.setFansUserId(userId);
		SysUser sysUser = sysUserService.get(userId);
		request.setAttribute("userName",
				sysUser == null ? "" : sysUser.getUserName());
		List<SysUserFans> list = new ArrayList<SysUserFans>();
		int total = 0;
		try {
			total = sysUserFansService.getCount(sysUserFansSearch);
			if (total > 0) {
				list = sysUserFansService.getPaginatedList(sysUserFansSearch);
				if (CustomerListUtils.isNotEmpty(list)) {
					for (SysUserFans userFans : list) {
						SysUser user = sysUserService.get(userFans.getUserId());
						userFans.setNickName(user == null ? "" : user
								.getNickName());
						userFans.setUserName(user == null ? "" : user
								.getUserName());
						ResPic resPic = resPicService.get(user.getPicId());
						userFans.setPic(resPic == null ? "" : resPic
								.getPicPath());
						// 是否关注 1.关注 0未关注
						// SysUserFansSearch userFansSearch = new
						// SysUserFansSearch();
						// userFansSearch.setFansUserId(loginUser.getId());
						// userFansSearch.setUserId(userFans.getFansUserId());
						// int count =
						// sysUserFansService.getCount(userFansSearch);
						// request.setAttribute("count", count);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!");
		}

		ResponseEnvelope<SysUserFans> res = new ResponseEnvelope<SysUserFans>(
				total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysUserFansSearch);
		request.setAttribute("userId", userId);
		String pageType = (String) request.getParameter("pageType");
		// String jumpType = (String)request.getParameter("jumpType");
		request.setAttribute("pageType", pageType);
		request.setAttribute("jumpType", "attention");

		String url = "";
		String type = (String) request.getParameter("type");
		if (type.equals("me")) {
			url = JSPMAIN + "/myAttention_list";
		} else {
			url = JSPMAIN + "/designAttention_list";
		}
		return Utils.getPageUrl(request, url);
	}

	/**
	 * 用户注册(移动端引用)
	 */
	@RequestMapping(value = "/app/register")
	@ResponseBody
	public Object register(String terminalImei, String mobile,
						   String password, String code, String msgId, String address,
						   HttpServletRequest request,
						   HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");//个人(企业)名称(对应数据字典中的name)
//		final String userType = request.getParameter("userType");//用户类型:对应数据字典中的value
		String areaCode = request.getParameter("areaCode");//区代码
		String cityCode = request.getParameter("cityCode");//市代码
		String provinceCode = request.getParameter("provinceCode");//省代码

		logger.info("_register：name："+name+",areaCode:"+areaCode+",cityCode:"+cityCode+",provinceCode:"+provinceCode);
		if (StringUtils.isEmpty(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "手机号不能为空", msgId);
		}
		/*if (StringUtils.isEmpty(userType)){
			return new ResponseEnvelope<SysUser>(false, "请选择用户类型!", msgId);
		}*/
		
		if (StringUtils.isEmpty(password)) {
			return new ResponseEnvelope<SysUser>(false, "密码不能为空", msgId);
		}
		if (password.length() < 6) {
			return new ResponseEnvelope<SysUser>(false, "密码不能小于6个字符！", msgId);
		}
		if (StringUtils.isEmpty(code)) {
			return new ResponseEnvelope<SysUser>(false, "请输入验证码！", msgId);
		}
		/*if (Constants.USER_LEVEL_END_USER_GROUP_NAME_DECORATE_USER != Integer.parseInt(userType)){*///普通用户不需要填写地址和名称
		if (StringUtils.isEmpty(provinceCode) || StringUtils.isEmpty(cityCode) || StringUtils.isEmpty(areaCode)) {
			return new ResponseEnvelope<SysUser>(false, "请输入地址!", msgId);
		}
		if (StringUtils.isEmpty(name)) {
			return new ResponseEnvelope<SysUser>(false, "请输入名称!", msgId);
		}
		/*}*/
		if (!isMobile(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！", msgId);
		}
		SysUser user = new SysUser();
		user.setMobile(mobile);
		user.setIsDeleted(0);
		List<SysUser> list = sysUserService.getList(user);
		if (CustomerListUtils.isNotEmpty(list)) {
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true, "该手机号已被注册，请输入其他手机号！",
					msgId);
		}

		/** 获取session中存放的手机短信验证码 */
		long currentTime = System.currentTimeMillis();
		long sendCodeTime = 0;
		String yzm = "";
		int num = 0;
		//允许输入验证码次数
		String verify=SmsClient.app.getString("verifyCount");
		int verifyCount=3;
		if(verify!=null){
			verifyCount=Integer.parseInt(verify);	
		}
		if ("1".equals(cacheEnable)) {
			Object codeObject = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_" + mobile);
			Object timeoutObject = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_TIMEOUT_" + mobile);
			if (codeObject != null) {
				yzm = codeObject.toString();
			}
			if (timeoutObject != null) {
				sendCodeTime = Long.parseLong(timeoutObject.toString());
			}
			// 验证次数
			Object count = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_Count" + mobile);
			if (count != null) {
				num = Integer.parseInt(count.toString());
			}
		} else {
			//防止空指针
			Object timeoutObject = request.getServletContext().getAttribute(
					"REGISTER_CODE_TIMEOUT_" + mobile);;
			Object codeObject = request.getServletContext().getAttribute(
					"REGISTER_CODE_" + mobile);
			Object count =  request.getServletContext().getAttribute(
					"REGISTER_CODE_Count" + mobile);
			if (timeoutObject != null) {
				sendCodeTime =Long.parseLong(timeoutObject.toString());
			}
			if (codeObject != null) {
				yzm = codeObject.toString();
			}
			if (count != null) {
				num = Integer.parseInt(count.toString());
			}
			
		}

		if (StringUtils.isBlank(yzm) || sendCodeTime <= 0) {
		//success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true , "验证码已失效，请重新获取！", msgId);
		}

		// 一分钟验证码失效
		if ((currentTime - sendCodeTime) > SmsClient.VALID_TIME) {
		//success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true , "验证码已超时，请重新获取！", msgId);
		}
		if (num < verifyCount) {
			if (!code.equals(yzm)) {
				num++;
				if ("1".equals(cacheEnable)) {
					CacheManager.getInstance().getCacher()
							.set("REGISTER_CODE_Count" + mobile, num + "", 0);
				} else {
					request.getServletContext().setAttribute(
							"REGISTER_CODE_Count" + mobile, num + "");
				}
				//success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				return new ResponseEnvelope<SysUser>(true, "验证码错误！",
						msgId);
			}
		} else {
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true, "验证码已失效，请重新获取！", msgId,false);
		}
		try {
			SysUser sysUser = new SysUser();
			sysUser.setUserName(name);
			// 昵称默认手机号
			sysUser.setMobile(mobile);
			/*if(Constants.USER_LEVEL_END_USER_GROUP_NAME_DECORATE_USER != Integer.parseInt(userType)){*/
			sysUser.setAreaId(Integer.parseInt(areaCode));
			sysUser.setAreaLongCode("." + provinceCode + "." + cityCode + "." + areaCode);
			/*}*/
			sysUser.setAddress(address);
			sysUser.setNickName(mobile);
			sysUser.setPassword(password);
			sysUser.setAppKey(Utils.generateRandomDigitString(32));
			sysUser.setGroupId(0);
			sysUser.setJob("-1");
			/* 保存设备号 */
			/*2017-12-08 del by zhangwenjian 注册是不需要绑定设备码
			sysUser.setUserImei(terminalImei);
			*/
			//将所有新注册的用户的userType(用户类型)默认为0
			sysUser.setUserType(new Integer(0));
			sysSave(sysUser, request);
			final int userId = sysUserService.add(sysUser, request);

			//pay_account插入两条记录
			PayAccount payAccount = new PayAccount();
			payAccount.setConsumAmount(0d);
			payAccount.setBalanceAmount(3000d);
			payAccount.setUserId(userId);
			payAccount.setIsDeleted(0);
			payAccount.setPlatformBussinessType("2c");
			payAccountService.add(payAccount);

			payAccount.setConsumAmount(0d);
			payAccount.setBalanceAmount(0d);
			payAccount.setUserId(userId);
			payAccount.setIsDeleted(0);
			payAccount.setPlatformBussinessType("2b");
			payAccountService.add(payAccount);

			sysUserService.addRoleGroup(userId);

			//用户注册信息收集
			UserRegisterInfoBo registerInfoBo = new UserRegisterInfoBo();
			registerInfoBo.setUserId(userId);

			/*if (Constants.USER_LEVEL_END_USER_GROUP_NAME_DECORATE_USER != Integer.parseInt(userType)){*/
			registerInfoBo.setName(name);
			/*}*/
			/*registerInfoBo.setUserType(Integer.parseInt(userType));*/
			//收集用户注册信息、分配默认角色
			int id = sysUserRegisterInfoService.addRegisterInfo(registerInfoBo);
			Tools.fixExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    UserLevelCfg cfg = new UserLevelCfg();
                    cfg.setUserId(userId);
                    cfg.setPcLimitKey(Constants.USER_LEVEL_PC_LIMIT_1);
                    cfg.setMobileLimitKey(Constants.USER_LEVEL_MOBILE_LIMIT_1);

                   /* cfg.setUserGroupType(Integer.parseInt(userType));*/
                    cfg.setUserLevel(Constants.USER_LEVEL_INIT);
                    cfg.setVersion(Constants.USER_LEVEL_USER_PAY_TYPE_BASE);
                    sysResLevelCfgService.addUserLevelLimit(cfg);
                }
            });
			if (id < 1){
				return new ResponseEnvelope<SysUser>(false, "注册失败!", msgId, false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!", msgId);
		}
		List<SysUser> list1 = null;
		if (Utils.enableRedisCache()) {
			list1 = SysUserCacher.getSysList();
		} else {
			list1 = sysUserService.getSysList();
		}
		request.getSession().setAttribute("sysUserList", list1);
		try {
            //注册成功，使验证码失效
            if ("1".equals(cacheEnable)) {
              CacheManager.getInstance().getCacher().set("REGISTER_CODE_TIMEOUT_" + mobile,0+"",0);
            }else {
              request.getServletContext().setAttribute("REGISTER_CODE_TIMEOUT_" + mobile , 0);
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
		return new ResponseEnvelope<SysUser>(true,"注册成功!", msgId, true);
		
	}
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern
				.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[57])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	@RequestMapping(value = "/get/{areaCode}")
	@ResponseBody
	public WBaseArea getArea(@PathVariable("areaCode") String areaCode) {
		WBaseArea wBaseArea = null;
		
		if (Utils.enableRedisCache()) {
			wBaseArea = BaseAreaCacher.getAreaByCode(areaCode);
		} else {
			BaseAreaSearch baseAreaSearch = new BaseAreaSearch();
			baseAreaSearch.setAreaCode(areaCode);
			wBaseArea = baseAreaService.getByCode(baseAreaSearch);
		}
		return wBaseArea;
	}

	/**
	 * 用户列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style,
			@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		
		if (sysUserSearch.getLimit() == null) {
			sysUserSearch.setLimit(new Integer(20));
		}

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUserSearch = (SysUserSearch) JsonUtil.getJsonToBean(jsonStr,
					SysUserSearch.class);
			if (sysUserSearch == null) {
				return new ResponseEnvelope<SysUser>(false, "传参异常!", "none");
			}
		}

		List<SysUser> list = new ArrayList<SysUser>();
		int total = 0;
		try {
			if (Utils.enableRedisCache()) {
				total = SysUserCacher.getCount(sysUserSearch);
			} else {
				total = sysUserService.getCount(sysUserSearch);
			}

			logger.info("total:" + total);

			if (total > 0) {
				if (Utils.enableRedisCache()) {
					list = SysUserCacher.getPageList(sysUserSearch);
				} else {
					list = sysUserService.getPaginatedList(sysUserSearch);
				}
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String sysUserJsonList = JsonUtil.getListToJsonData(list);
				List<SysUserSmall> smallList = new JsonDataServiceImpl<SysUserSmall>()
						.getJsonToBeanList(sysUserJsonList, SysUserSmall.class);
				return new ResponseEnvelope<SysUserSmall>(total, smallList,
						sysUserSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",
					sysUserSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(total, list,
				sysUserSearch.getMsgId());
	}

	
	
	
	@RequestMapping(value = "/app/login")
	@ResponseBody
	public ResponseEnvelope <SysUser> loginForApp(SysUser sysUser, SysUserLoginLog sysUserLoginLog, String terminalImei, String mediaType, String usbTerminalImei,
												  HttpServletRequest request, HttpServletResponse response,
												  Integer forceLogin) throws SocketException {
		ResponseEnvelope <SysUser>res = null;
		//TODO : should use the post to send request, the request data should  use the JSON of data, Spring MVC will auto mapping this data
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if (sysUser == null) {
				return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!","login");
			}
		}
		
		Boolean valid = validParam(sysUser.getMsgId(), sysUser.getAccount(), sysUser.getPassword());
		if(!valid) {
			return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!","login");
		}
		//校验用户手机号是否存在 
        SysUser userInfo = new SysUser();
        userInfo.setMobile(sysUser.getAccount());
        List<SysUser> list = sysUserService.getList(userInfo);
        if (CustomerListUtils.isEmpty(list)) {
            //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
            return new ResponseEnvelope<SysUser>(true, "找不到该用户!",sysUser.getMsgId());
        } 
		SysUser user = sysUserService.checkUserAccount(sysUser.getAccount(),sysUser.getPassword());
		if(StringUtils.isEmpty(mediaType)) {
			mediaType = "3";
		}
		if (user != null) {
			String appWebSocketMessage = webSocket.getString("app.webSocket.message");
			String appWebSocketPayOrder = webSocket.getString("app.webSocket.payOrder");
			user.setWebSocketMessage(appWebSocketMessage);
			user.setPayCallBackServer(appWebSocketPayOrder);
			user.setIsLoginIn(0);
			/* 检测设备号 */
			Map<String,String> equipmentMap = sysUserEquipmentService.equipmentCheck(user.getId(), user.getDeviceRestrict(), terminalImei, usbTerminalImei, EquipmentConstants.PC_EQUIPMENT);
			if(equipmentMap == null ||equipmentMap.size()<=0){
				return new ResponseEnvelope<SysUser>(false, "数据异常", sysUser.getMsgId());
			}else if (!"true".equals(equipmentMap.get("success"))) {
				return new ResponseEnvelope<SysUser>(false, equipmentMap.get("data"), sysUser.getMsgId());
			}
			AuthorizedConfigSearch authorizedConfig = new AuthorizedConfigSearch();
			authorizedConfig.setUserId(user.getId());
			authorizedConfig.setIsDeleted(0);
			int count = authorizedConfigService.getCount(authorizedConfig);
			if(count <= 0) {
				sysUserEquipmentService.deleteByUserId(user.getId(),EquipmentConstants.MOBILE_EQUIPMENT);
				sysUserEquipmentService.deleteByUserId(user.getId(),EquipmentConstants.PC_EQUIPMENT);
			}else {
				//查询品牌“三度空间”的id
				int sanduId = baseBrandService.getIdByBrandName("三度空间");
				//将用户绑定的公司id返回
				List<AuthorizedConfig> authorizedConfigList = authorizedConfigService.getList(authorizedConfig);
				if(Lists.isNotEmpty(authorizedConfigList)) {
					StringBuffer brandIds = new StringBuffer();
					for(AuthorizedConfig config : authorizedConfigList) {
						Integer brandId = Integer.valueOf(config.getBrandIds());
						//判断用户绑定的品牌是否有三度空间
						if(sanduId == brandId.intValue()) {
							brandIds.append("0");
							break;
						}else {
							brandIds.append(brandId);
						}
						brandIds.append(",");
					}
					//去掉最后一个逗号
					StringBuffer ids = brandIds.deleteCharAt(brandIds.length() - 1);
					user.setBrandIds(ids.toString());
				}
			}
			//获取该用户的菜单权限
			List<LoginMenu> datalist= new ArrayList<LoginMenu>();
			List<SysFunc> funcList = sysFuncService.getUserU3DMenus(user.getId());
			//转成loginMenu
			if(funcList != null){
				for(SysFunc func:funcList){
					datalist.add(func.toLoginMenu());
				}
			}
			user.setAppMenuList(datalist);
			user.setTerminalImei(terminalImei);
		
			/* 保存心跳间隔信息 */
			Integer heartbeatTime = Integer.valueOf(Utils.getValue("heartbeatTime", "120"));
			user.setHeartbeatTime(heartbeatTime);
			user.setServerUrl(SERVERURL);
			user.setResourcesUrl(RESOURCESURL);
			user.setSitekey(SITEKEY);
			user.setSiteName(SITENAME);

			// 获取数据字典资源路径
			Map<String, String> map = new HashMap<String, String>();
			SysDictionary sysDictionary = new SysDictionary();
			sysDictionary.setType("resourceDirs");
			sysDictionary.setIsDeleted(0);
			List<SysDictionary> resList = sysDictionaryService
					.getList(sysDictionary);
			for (SysDictionary dictionary : resList) {
				map.put(dictionary.getValuekey(), dictionary.getAtt1());
			}
			user.setResourceMap(map);
			/** 查询角色 **/
			SysUserRoleSearch sysUserRoleSearch = new SysUserRoleSearch();
			int userId = user.getId();
			sysUserRoleSearch.setUserId(userId);
//			List<SysUserRole> list = sysUserRoleService.getPaginatedList(sysUserRoleSearch);TODO : Check this code?
			List<SysRole> roleList = sysRoleService.getListByUserId(userId);
			user.setRoleList(roleList);
			
			//用token加密app用来解密的key
		
			// 设置分布式域名配置
			user.setResourcesUrls(resourcesUrls);
			//获取服务化集合配置
			user.setServitizationList(this.getServitizationUrls());
			String userKey = Utils.getUUID();
			
			Map<String , Object> payload=new HashMap<String, Object>();
			Date date=new Date();
			payload.put("uid", user.getId());//用户ID
			payload.put("uname", user.getNickName());//
			payload.put("mtype", mediaType);//mediatype
			payload.put("uphone", user.getMobile());//
			payload.put("appKey", userKey);
			payload.put("ukey", userKey);
			payload.put("utype", user.getUserType());
			payload.put("signflat",SystemCommonConstant.LOGIN_FROM_PC);
			payload.put("iat", date.getTime());//生成时间
			payload.put("ext",date.getTime()+1000*60*60*SystemCommonConstant.USER_TIME_OUT_HOUR);//过期时间6小时
			String token=Jwt.createToken(payload);
			user.setToken(token);

			user = sysUserService.EencryptKey(user); //TODO Check this code
			
			LoginUser loginUser = user.toLoginUser();
			loginUser.setMediaType(mediaType);
			loginUser.setUserKey(userKey);
			//Cache 用户登录信息
			if (Utils.enableRedisCache()) {
				if(StringUtils.isNoneBlank(user.getMobile()) && SystemCommonConstant.AUTO_RENDER_USER_ACCOUNTS.indexOf(user.getMobile()) == -1) {
					saveLogOfSysUser(user, sysUserLoginLog, loginUser);
	            }
				SysUserCacher.cacheTheLoginUserInfo(loginUser, SystemCommonConstant.USER_TIME_OUT_HOUR*60*60); //单位为秒
			}
			//获得用户头像
			user=sysUserService.getUserPic(user);

			res = new ResponseEnvelope<SysUser>(user, sysUser.getMsgId(), true);
		}else {
			res = new ResponseEnvelope<SysUser>(true, "用户名或密码错误",sysUser.getMsgId(),false);
		}
		
		return res;
	}

	//获取服务化地址
	private List<Map<String,String>> getServitizationUrls(){
		List<Map<String,String>> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(APP_SERVITIZATION_URLS)) {
			Map<String,String> map = null;
			JSONArray jsonArray = JSONArray.fromObject(APP_SERVITIZATION_URLS);
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					map = new HashMap<>();
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					map.put(SERVER_KEY, jsonObj.getString(SERVER_KEY));
					map.put(SERVER_URL,jsonObj.getString(SERVER_URL));
					list.add(map);
				}
			}
		}
		return list;
	}

	private void saveLogOfSysUser(SysUser user, SysUserLoginLog sysUserLoginLog, LoginUser loginUser) throws SocketException {
		//用户数据采集
		sysUserLoginLog.setUserId(user.getId());        	//取得登录的用户
		sysUserLoginLog.setLoginTime(new Date());   		//登录时间
		sysUserLoginLog.setLoginType(1);					//登录类型(1:登录)
		sysUserLoginLog.setServerIp(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP());	//服务器IP
		sysUserLoginLog.setUserMobile(user.getMobile());	//用户手机(拿过去命名)
		
		//记录用户最后一次登录信息
		SysUserLastLoginLog sysUserEndLog = new SysUserLastLoginLog();
		sysUserEndLog.setUserId(user.getId());
		sysUserEndLog.setLastLoginTime(new Date());
		sysUserEndLog.setClientIp(sysUserLoginLog.getClientIp());
		sysUserEndLog.setServerIp(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP());
		sysUserEndLog.setLoginDevice(sysUserLoginLog.getLoginDevice());
		sysUserEndLog.setSystemModel(sysUserLoginLog.getSystemModel());
		SysUserLastLoginLog sue = sysUserLastLoginLogService.get(user.getId());	//查询该用户是否已存在最后登录记录
		
		if(sue == null){      //如果没有->add
            sysUserEndLog.setLoginCount(1); //用户登录总次数
            sysUserLastLoginLogService.add(sysUserEndLog);          
        }else{                  //否则update最后登录数据
            sysUserEndLog.setLoginCount(sue.getLoginCount() == null ? 1: (sue.getLoginCount()+1)); //用户登录总次数
            sysUserLastLoginLogService.update(sysUserEndLog);
        }
		
		int storageTime = 60 * 60 * 24 * 365;
		CacheManager.getInstance().getCacher().set("userIdLog", user.getId().toString(), storageTime);
		CacheManager.getInstance().getCacher().set("loginTimeLog", UtilDate.ConverToString(new Date()), storageTime);
		CacheManager.getInstance().getCacher().set("mobileLog", user.getMobile(), storageTime);
		CacheManager.getInstance().getCacher().set("clientIp", sysUserLoginLog.getClientIp(), storageTime);
		CacheManager.getInstance().getCacher().set("operationLoginDevice", sysUserLoginLog.getLoginDevice(), storageTime);
		CacheManager.getInstance().getCacher().set("operationSystemModel", sysUserLoginLog.getSystemModel(), storageTime);
		sysUserLoginLogService.txtSaveUserLog(sysUserLoginLog); //执行写数据到文件方法
		
	}
	
	private Boolean validParam (String ...params) {
		Boolean result = true;
		for(String param : params) {
			if(StringUtils.isBlank(param)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	private void newSysSave(SysUserLastLoginLog model, LoginUser loginUser) {
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	
	/**
	 * 用户登录(移动端引用)
	 * 
	 * @param forceLogin
	 *            是否强制登录 1:强制登录
	 * @throws IOException
	 */
//	@RequestMapping(value = "/app/login")
//	@ResponseBody
	public Object login(SysUser sysUser,SysUserLoginLog sysUserLoginLog, String terminalImei,
			HttpServletRequest request, HttpServletResponse response,
			Integer forceLogin) throws IOException {
		
		ResponseEnvelope res = null;
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if (sysUser == null) {
				return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!",
						"login");
			}
		}
		String msg = "";
		if (sysUser.getMsgId() == null) {
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<SysUser>(false, msg, sysUser.getMsgId());
		}
		if (sysUser.getAccount() == null) {
			msg = "登录名不能为空！";
			return new ResponseEnvelope<SysUser>(false, msg, sysUser.getMsgId());
		}
		if (sysUser.getPassword() == null) {
			msg = "密码不能为空！";
			return new ResponseEnvelope<SysUser>(false, msg, sysUser.getMsgId());
		}

		SysUser user = sysUserService.checkUserAccount(sysUser.getAccount(),sysUser.getPassword());

		if (user != null) {
			String appWebSocketMessage = webSocket
					.getString("app.webSocket.message");
			String appWebSocketPayOrder = webSocket
					.getString("app.webSocket.payOrder");

			user.setWebSocketMessage(appWebSocketMessage);
			user.setPayCallBackServer(appWebSocketPayOrder);
			logger.info("============app 客户端机 terminalImei：" + terminalImei
					+ "; user IMEI ： " + user == null ? "" : user.getUserImei());
			user.setIsLoginIn(0);
			
			
			/* 检测设备号 */
			Map<String,String>equipmentMap = sysUserEquipmentService.equipmentCheck(user.getId(), 0, terminalImei, "", EquipmentConstants.PC_EQUIPMENT);
			if(equipmentMap == null ||equipmentMap.size()<=0){
				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "数据异常", sysUser.getMsgId());
			}else if (!"true".equals(equipmentMap.get("success"))) {
				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, equipmentMap.get("data"), sysUser.getMsgId());
			}
			
			//if (StringUtils.isNotBlank(user.getUserImei())) {
				//if (!StringUtils.equals(terminalImei, user.getUserImei()))// 7824AFBD501E
					//return new ResponseEnvelope<SysUser>(false,
						//	"该帐号已绑定其他设备,只能使用初始设备登录.", sysUser.getMsgId());
			//}
			/* 检测设备号->end */
			/* 检测该用户是否已被登录并做出相应逻辑(根据心跳接口) */
			// boolean flag=sysUserService.checkisLoginIn(user.getId());
			// if(flag)
			// return new ResponseEnvelope<SysUser>(false,
			// "该帐号在其他设备登录,登录失败",sysUser.getMsgId());
			/* 检测该用户是否已被登录并做出相应逻辑(根据心跳接口)->end */
			if (forceLogin != null && forceLogin == 1) {
				/* 强制登录 */
			} else {
				/* 检测该用户是否被其他设备登录 */
				boolean flag = false;
				if (Utils.enableRedisCache()) {
					flag = sysUserService.checkisLoginIn2(user.getId(),
							terminalImei);
				} else {
					LoginUser loginUser = (LoginUser) request.getSession()
							.getAttribute("loginUser");
					if (loginUser != null) {
						if (StringUtils.equals(user.getToken(),
								loginUser.getToken())) {
							flag = true;
						}
					}
				}
				sysUser.setIsLoginIn(flag ? 1 : 0);
				if (flag) {
					return new ResponseEnvelope<SysUser>(sysUser,
							sysUser.getMsgId(), true);
				}
				/* 检测该用户是否被其他设备登录->end */
			}
			// 生成session-key
			SysUser newuser = new SysUser();
			newuser.setId(user.getId());
			newuser.setToken(Utils.generateRandomDigitString(32));
			int c = sysUserService.update(newuser);
			if (c == 1)
				user.setToken(newuser.getToken());
			BaseArea baseArea = null;
			String areaName = "";
			if (user.getAreaLongCode() != null) {
				String areaLongCode = user.getAreaLongCode();
				String[] codeArr = areaLongCode.split("\\.");
				BaseAreaSearch baseAreaSearch = new BaseAreaSearch();
				if (codeArr != null && codeArr.length > 0) {
					for (int i = 0; i < codeArr.length - 1; i++) {
						baseAreaSearch.setAreaCode(codeArr[i + 1]);
						baseArea = baseAreaService
								.selectCityName(baseAreaSearch);
						if (baseArea != null && StringUtils.isNotEmpty(baseArea.getAreaName())){
							areaName += baseArea.getAreaName() + ",";
						}
					}
				}
			}
			//获取该用户的菜单权限
			List<LoginMenu> datalist= new ArrayList<LoginMenu>();
			List<SysFunc> funcList = sysFuncService.getUserU3DMenus(user.getId());
			//转成loginMenu
			if(funcList != null){
				for(SysFunc func:funcList){
					datalist.add(func.toLoginMenu());
				}
			}
			user.setAppMenuList(datalist);
			/* 用户信息存入redis缓存中(用于实现超时功能) */
			user.setTerminalImei(terminalImei);
			if (Utils.enableRedisCache()) {
				SysUserCacher.updateTimeOutUser(user);
			}
			/* 用户信息存入redis缓存中(用于实现超时功能)->end */
			/* 保存心跳间隔信息 */
			Integer heartbeatTime = Integer.valueOf(Utils.getValue(
					"heartbeatTime", "120"));
			user.setHeartbeatTime(heartbeatTime);
			user.setAreaName(areaName);
			// user.setServiceUrl(SERVERURL);
			user.setServerUrl(SERVERURL);
			user.setResourcesUrl(RESOURCESURL);
			user.setSitekey(SITEKEY);
			user.setSiteName(SITENAME);
			if (user.getSex() == null || user.getSex().intValue() == 1) {
				if (StringUtils.isEmpty(user.getPicPath())) {
					user.setPicPath("/system/sysUser/pic/userImageMan.png");
					user.setUserPic("/system/sysUser/pic/userImageMan.png");
				}
			} else {
				if (StringUtils.isEmpty(user.getPicPath())) {
					user.setPicPath("/system/sysUser/pic/userImageGirl.png");
					user.setUserPic("/system/sysUser/pic/userImageGirl.png");
				}
			}

			// 获取数据字典资源路径
			Map<String, String> map = new HashMap<String, String>();
			SysDictionary sysDictionary = new SysDictionary();
			sysDictionary.setType("resourceDirs");
			sysDictionary.setIsDeleted(0);
			List<SysDictionary> resList = sysDictionaryService
					.getList(sysDictionary);
			for (SysDictionary dictionary : resList) {
				map.put(dictionary.getValuekey(), dictionary.getAtt1());
			}
			user.setResourceMap(map);
			/** 查询角色 **/
			SysUserRoleSearch sysUserRoleSearch = new SysUserRoleSearch();
			String roleIds = "";
			int userId = user.getId();
			sysUserRoleSearch.setUserId(userId);
			List<SysUserRole> list = sysUserRoleService.getPaginatedList(sysUserRoleSearch);
			 
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					int roleId = list.get(i).getRoleId();
					if (i == list.size() - 1) {
						roleIds = roleIds + roleId + "";
					}
					if (i != list.size() - 1) {
						roleIds = roleIds + roleId + ",";
					}
				}
			}

			List<SysRole> roleList = sysRoleService.getListByUserId(userId);
			user.setRoleList(roleList);
			//用户数据采集
			sysUserLoginLog.setUserId(user.getId());        	//取得登录的用户
			sysUserLoginLog.setLoginTime(new Date());   		//登录时间
			sysUserLoginLog.setLoginType(1);					//登录类型(1:登录)
			sysUserLoginLog.setServerIp(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP());	//服务器IP
			sysUserLoginLog.setUserMobile(user.getMobile());	//用户手机(拿过去命名)
			//记录用户最后一次登录信息
			SysUserLastLoginLog sysUserEndLog = new SysUserLastLoginLog();
			sysUserEndLog.setUserId(user.getId());
			sysUserEndLog.setLastLoginTime(new Date());
			sysUserEndLog.setClientIp(sysUserLoginLog.getClientIp());
			sysUserEndLog.setServerIp(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP());
			sysUserEndLog.setLoginDevice(sysUserLoginLog.getLoginDevice());
			sysUserEndLog.setSystemModel(sysUserLoginLog.getSystemModel());
			SysUserLastLoginLog sue = sysUserLastLoginLogService.get(user.getId());	//查询该用户是否已存在最后登录记录
			sysSave(sysUserEndLog, request);
			if(sue == null){		//如果没有->add
				sysUserLastLoginLogService.add(sysUserEndLog);			
			}else{					//否则update最后登录数据
				sysUserLastLoginLogService.update(sysUserEndLog);
			}
			/*request.getSession().setAttribute("userIdLog", user.getId());
			request.getSession().setAttribute("loginTimeLog", UtilDate.ConverToString(new Date()));
			request.getSession().setAttribute("mobileLog",  user.getMobile());
			request.getSession().setAttribute("clientIp", sysUserLoginLog.getClientIp());
			request.getSession().setAttribute("operationLoginDevice", sysUserLoginLog.getLoginDevice());
			request.getSession().setAttribute("operationSystemModel", sysUserLoginLog.getSystemModel());*/
			int storageTime = 60 * 60 * 24 * 365;
			CacheManager.getInstance().getCacher().set("userIdLog", user.getId().toString(), storageTime);
			CacheManager.getInstance().getCacher().set("loginTimeLog", UtilDate.ConverToString(new Date()), storageTime);
			CacheManager.getInstance().getCacher().set("mobileLog", user.getMobile(), storageTime);
			CacheManager.getInstance().getCacher().set("clientIp", sysUserLoginLog.getClientIp(), storageTime);
			CacheManager.getInstance().getCacher().set("operationLoginDevice", sysUserLoginLog.getLoginDevice(), storageTime);
			CacheManager.getInstance().getCacher().set("operationSystemModel", sysUserLoginLog.getSystemModel(), storageTime);
			sysUserLoginLogService.txtSaveUserLog(sysUserLoginLog); //执行写数据到文件方法
			//用token加密app用来解密的key
			user = sysUserService.EencryptKey(user);

			
			// 设置分布式域名配置
			user.setResourcesUrls(resourcesUrls);
			
			/* 识别是否绑定设备->没绑定设备,则在登录动作中绑定设备->end */
			ResponseEnvelope envelope =	new ResponseEnvelope<SysUser>(user, sysUser.getMsgId(), true);
			/*envelope.getHeader().put("resDomain", Constants.RES_DOMAIN_CFG);*///返回资源的请求域名，不同资源，域名不一致
			return envelope;

		} else {
			return new ResponseEnvelope<SysUser>(false, "用户名或密码错误",
					sysUser.getMsgId());
			// res=new ResponseEnvelope<SysUser>(false,
			// "用户名或密码错误",sysUser.getMsgId());
			// return ZipUtils.compressionZip(res);
		}

	}

	/**
	 * 得到分布式资源存储对应的域名配置
	 * 
	 * @author huangsongbo
	 * @return
	 */
	private static List<ResourcesUrl> getResourcesUrls() {
		Map<String, String> map = ResDistributeUtils.urlDistributeMap;
		Map<String, List<String>> mapTemp = new HashMap<String, List<String>>();
		for(String modelName : map.keySet()) {
			String domain = map.get(modelName);
			if(mapTemp.containsKey(domain)) {
				List<String> keyList = mapTemp.get(domain);
				keyList.remove(modelName);
				keyList.add(modelName);
				mapTemp.put(domain, keyList);
			}else {
				List<String> keyList = new ArrayList<String>();
				keyList.add(modelName);
				mapTemp.put(domain, keyList);
			}
		}
		
		// *组装List<ResourcesUrl> ->start
		List<ResourcesUrl> resourcesUrlList = new ArrayList<SysUser.ResourcesUrl>();
		for(String domain : mapTemp.keySet()) {
			ResourcesUrl resourcesUrl = new SysUser().new ResourcesUrl();
			resourcesUrl.setDomain(domain);
			resourcesUrl.setModelName(mapTemp.get(domain));
			resourcesUrlList.add(resourcesUrl);
		}
		// *组装List<ResourcesUrl> ->end
		
		return resourcesUrlList;
	}

	/**
	 * 用户中心--热门设计师列表
	 * 
	 * @param sysUserSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/topDesignerList")
	@ResponseBody
	public Object topDesignerList(@PathVariable String style,
			@ModelAttribute("sysUserSearch") SysUserSearch sysUserSearch,
			HttpServletRequest request, HttpServletResponse response) {
		
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUserSearch = (SysUserSearch) JsonUtil.getJsonToBean(jsonStr,
					SysUserSearch.class);
			if (sysUserSearch.getId() == null) {
				return new ResponseEnvelope<SysUserSearch>(false, "传参异常!",
						"none");
			}
		}
		String msg = "";
		if (StringUtils.isBlank(sysUserSearch.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUserSearch>(false, msg,
					sysUserSearch.getMsgId());
		}

		List<SysUser> list = new ArrayList<SysUser>();
		int total = 0;
		// 省市查询条件 给areaLongCode赋值
		StringBuffer areaCode = new StringBuffer();
		String provinceCode = sysUserSearch.getProvinceCode();
		String cityCode = sysUserSearch.getCityCode();
		if (StringUtils.isNotBlank(provinceCode)) {
			areaCode.append("." + provinceCode);
		}
		if (StringUtils.isNotBlank(cityCode)) {
			areaCode.append("." + cityCode);
		}
		if (StringUtils.isNotBlank(areaCode.toString())) {
			areaCode.append(".");
		}
		sysUserSearch.setAreaLongCode(areaCode.toString());
		try {
			sysUserSearch.setUserType(2);
			if (Utils.enableRedisCache()) {
				total = SysUserCacher.getSysUserCount(sysUserSearch);
			} else {
				total = sysUserService.getCount(sysUserSearch);
			}

			logger.info("total=" + total);
			if (total > 0) {
				if (Utils.enableRedisCache()) {
					list = SysUserCacher.getPaginatedList(sysUserSearch);
				} else {
					list = sysUserService.getPaginatedList(sysUserSearch);
				}

				for (SysUser user : list) {
					user.setFansCount(fansCount(user.getId()));
					user.setAttentionCount(attentionCount(user.getId()));
					user.setWorksCount(worksCount(user.getId()));
					user.setAccessCount(accessCount(user.getId()));
					// 获取设计师头像路径
					if (user.getPicId() != null && user.getPicId() > 0) {
						ResPic resPic = null;
						if (Utils.enableRedisCache()) {
							resPic = DesignerWorksCacher.getResPic(user
									.getPicId());
						} else {
							resPic = resPicService.get(user.getPicId());
						}

						user.setPicPath(resPic == null ? "" : resPic
								.getPicPath());
					}
					// 获取设计师 地址
					String areaLongCode = user.getAreaLongCode();
					StringBuffer areaName = new StringBuffer();
					if (StringUtils.isNotBlank(areaLongCode)) {
						if (areaLongCode.contains(".")) {
							String area[] = areaLongCode.split("\\.");
							for (String code : area) {
								BaseArea baseArea = new BaseArea();
								baseArea.setAreaCode(code);
								List<BaseArea> areaList = null;
								if (Utils.enableRedisCache()) {
									areaList = SysUserCacher
											.getbaseAreaList(baseArea);
								} else {
									areaList = baseAreaService
											.getList(baseArea);
								}

								if (CustomerListUtils.isNotEmpty(areaList)) {
									areaName.append(areaList.get(0)
											.getAreaName());
								}
							}
						}
					} else {
						areaName.append("未知");
					}
					user.setAreaName(areaName.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!",
					sysUserSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUser>(total, list,
				sysUserSearch.getMsgId());
	}

	/**
	 * 申请设计师认证---接口
	 */
	@RequestMapping(value = "/designerAuthenticationIF")
	@ResponseBody
	public Object designerAuthenticationIF(@PathVariable String style,
			@ModelAttribute("sysUser") SysUser sysUser, String msgId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int res_id = -1;
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if (sysUser == null) {
				return new ResponseEnvelope<SysUser>(false, "传参异常!", "none");
			}
		}
		// 获取登录用户
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<SysUser>(false, "登录超时，请重新登录!",
					sysUser.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser.setId(loginUser.getId());
		}

		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUser>(false, msg, msgId);
		}
		try {
			String areaLongCode = "." + sysUser.getProvinceCode() + "."
					+ sysUser.getAreaId() + ".";
			sysUser.setAreaLongCode(areaLongCode);
			sysUser.setGroupId(0);
			// sysUser.setLevel(1);
			sysSave(sysUser, request);
			if (sysUser.getId() == null) {
				int id = sysUserService.add(sysUser, request);
				logger.info("add:id=" + id);
				sysUser.setId(id);
			} else {
				if (res_id != -1) {
					sysUser.setPicId(res_id);
				}
				int id = sysUserService.update(sysUser);
				logger.info("update:id=" + id);
			}

			if ("small".equals(style)) {
				String sysUserJson = JsonUtil.getBeanToJsonData(sysUser);
				SysUserSmall sysUserSmall = new JsonDataServiceImpl<SysUserSmall>()
						.getJsonToBean(sysUserJson, SysUserSmall.class);

				return new ResponseEnvelope<SysUserSmall>(sysUserSmall, msgId,
						true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!", msgId);
		}

		if (Utils.enableRedisCache()) {
			sysUser = SysUserCacher.get(sysUser.getId());
		} else {
			sysUser = sysUserService.get(sysUser.getId());
		}

		if (sysUser.getPicId() != null) {
			ResPic resPic = null;
			if (Utils.enableRedisCache()) {
				resPic = DesignerWorksCacher.getResPic(Integer.valueOf(sysUser
						.getPicId()));
			} else {
				resPic = resPicService.get(Integer.valueOf(sysUser.getPicId()));
			}

			// request.setAttribute("picPath",
			// resPic==null?"":resPic.getPicPath());
			sysUser.setPicPath(resPic.getPicPath());
		}

		return new ResponseEnvelope<SysUser>(sysUser, msgId, true);
	}

	/**
	 * 修改个人资料、修改头像(移动端引用)
	 */
	@RequestMapping(value = "/app/update")
	@ResponseBody
	public Object update(@PathVariable String style,
			@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cityCode = request.getParameter("cityCode");//市code
		String areaCode = request.getParameter("areaCode");//区code
		if (sysUser.getMsgId() == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！！",
					sysUser.getMsgId());
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		try {
			String areaLongCode = "." + sysUser.getProvinceCode() + "."
					+ cityCode + "." + areaCode;
			sysUser.setAreaLongCode(areaLongCode);
			sysUser.setId(loginUser.getId());
			sysUser.setGroupId(0);
			sysSave(sysUser, request);
			sysUserService.update(sysUser);
			SysUserCacher.remove(sysUser.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "保存失败！",
					sysUser.getMsgId());
		}

		return new ResponseEnvelope<SysUser>(true, sysUser.getMsgId(), true);
	}

	/**
	 * 上传头像图片接口
	 * 
	 * @param request
	 * @param msgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/app/uploadHeadPortrait")
	@ResponseBody
	public Object uploadHeadPortrait(@RequestParam MultipartFile picFile,
			String msgId, HttpServletRequest request) throws IOException {

		if (msgId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！！", msgId);
		}
		if (picFile == null) {
			return new ResponseEnvelope<SysUser>(false, "上传文件picFile不能为空！！",
					msgId);
		}
		// 上传文件的原名(即上传前的文件名字)
		String imageName = null;
		String suffix = null;

		String originalFilename = picFile.getOriginalFilename();
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 转换为字符串
		String formatDate = format.format(new Date());
		// 随机生成文件编号
		int random = new Random().nextInt(10000);
		imageName = new StringBuffer().append(formatDate).append(random)
				.toString();
		suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

		// 获取配置的业务文件目录
		//String filePath = app.getString("system.sysUser.pic.upload.path");
		String filePath = Utils.getPropertyName("config/res", "system.sysUser.pic.upload.path", "/system/sysUser/pic/");
		filePath = Utils.replaceDate(filePath);
		// 数据库存储目录
		String dbFilePath = filePath + imageName + suffix;
		// 文件服务器的存储路径
		String realPath = Tools.getRootPath(filePath.replace("/", "\\"),"") + filePath;
		if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
			realPath = Tools.getRootPath(filePath,"") + filePath;
		}
		try {
			// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
			FileUtils.copyInputStreamToFile(picFile.getInputStream(), new File(
					realPath, imageName + suffix));
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常！", msgId);
		}
		String fpath = realPath + imageName + suffix;
		File f = new File(fpath);
		Map map = FileUploadUtils.getMap(f, Tools.getRootPath(fpath, ""), false);

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}

		// String basePath = app.getString("app.server.url");
		// ResPic resPic = new ResPic();
		// resPic.setPicName(imageName);
		// resPic.setPicPath(dbFilePath);
		// resPic.setPicSuffix(suffix);
		// if( map.get("PicWeight") != null ){
		// resPic.setPicWeight(map.get("PicWeight").toString());
		// }
		// if( map.get("PicHeight") != null ){
		// resPic.setPicHigh(map.get("PicHeight").toString());
		// }
		// resPic.setPicType(PicType.HOUSE);
		// sysSave(resPic,request);
		// int res_id = resPicService.add(resPic);

		String basePath = app.getString("app.server.url");
		ResPic resPic = new ResPic();
		resPic.setPicName(imageName);
		resPic.setPicPath(dbFilePath);
		resPic.setPicCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_"
				+ Utils.generateRandomDigitString(6));
		resPic.setPicFileName(imageName);
		if (map.get("PicWeight") != null) {
			resPic.setPicWeight(map.get("PicWeight").toString());
		}
		if (map.get("PicHeight") != null) {
			resPic.setPicHigh(map.get("PicHeight").toString());
		}
		// if( map.get("FileSize") != null ){
		// resPic.setPicSize(Integer.valueOf(map.get("FileSize").toString()));
		// }
		resPic.setPicLevel("0");
		resPic.setPicFormat(suffix.substring(1, suffix.length()));
		resPic.setFileKey("system.sysUser.pic.upload.path");
		resPic.setBusinessId(loginUser.getId());
		resPic.setPicSuffix(suffix);
		resPic.setPicType(PicType.USER);
		sysSave(resPic, request);
		int res_id = resPicService.add(resPic);
		/* ftp上传 */
		try {
			boolean flag = false;
			// 仅支持ftp服务器上传
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
				logger.info("------用户头像上传ftp服务器删除本地文件");
				flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath
						+ imageName + suffix, filePath);
				if (flag) {
					// 删除本地
					FileUploadUtils.deleteFile(dbFilePath);
				} else {
					logger.info("---------仅支持ftp服务器文件上传异常！");
				}
			}
			// 3 本地和ftp同时上传(默认是本地上传)
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
				logger.info("------用户头像上传ftp服务器不删除本地文件");
				flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath
						+ imageName + suffix, filePath);
				if (!flag) {
					logger.info("---------本地和ftp服务器同时文件上传异常！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("----ftp上传异常：" + e.getMessage());
		}
		Map<String, Object> picMap = new HashMap<String, Object>();
		picMap.put("picUrl", basePath + "/upload/" + dbFilePath);
		picMap.put("resId", res_id);
		// JSONObject json = JSONObject.fromObject(picMap);
		// response.getWriter().print(json);
		return new ResponseEnvelope<SysUser>(picMap, msgId, true);
	}

	/**
	 * 修改绑定手机号(移动端引用)
	 */
	@RequestMapping(value = "/app/updateMobile")
	@ResponseBody
	public Object updateMobile(@PathVariable String style,
			@ModelAttribute("sysUser") SysUser sysUser, String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (sysUser.getMsgId() == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！！",
					sysUser.getMsgId());
		}
		if (sysUser.getMobile() == null) {
			return new ResponseEnvelope<SysUser>(false, "新手机号不能为空！",
					sysUser.getMsgId());
		}
		if (!isMobile(sysUser.getMobile())) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！",
					sysUser.getMsgId());
		}
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		if (StringUtils.isNotBlank(sysUser.getMobile())) {
			sysUser.setMediaType(Utils.getIntValue(mediaType));
			List<SysUser> list = sysUserService.getList(sysUser);
			if (Lists.isNotEmpty(list)) {
				return new ResponseEnvelope<SysUser>(false, "该手机号已存在，请重新输入！",
						sysUser.getMsgId());
			}
		}
		if (StringUtils.isEmpty(code)) {
			return new ResponseEnvelope<SysUser>(false, "请输入验证码！",
					sysUser.getMsgId());
		}
		/** 获取session中存放的手机短信验证码 */
		if (request.getServletContext().getAttribute(
				"REGISTER_CODE_TIMEOUT_" + sysUser.getMobile()) == null
				|| request.getServletContext().getAttribute(
						"REGISTER_CODE_" + sysUser.getMobile()) == null) {
			return new ResponseEnvelope<SysUser>(false, "验证码已失效，请重新获取！",
					sysUser.getMsgId());
		}
		
		long currentTime = System.currentTimeMillis();
		long sendCodeTime = (long) request.getServletContext().getAttribute(
				"REGISTER_CODE_TIMEOUT_" + sysUser.getMobile());
		String yzm = (String) request.getServletContext().getAttribute(
				"REGISTER_CODE_" + sysUser.getMobile());
				
		// 一分钟验证码失效
		if ((currentTime - sendCodeTime) > SmsClient.VALID_TIME) {
			return new ResponseEnvelope<SysUser>(false, "验证码已失效，请重新获取！",
					sysUser.getMsgId());
		}
		// 验证次数限制
		String count = (String) request.getServletContext().getAttribute(
				"REGISTER_CODE_Count" + sysUser.getMobile());
		int num = Integer.parseInt(count);
		logger.info("num:"+num);
		if (num < 3) {
			if (!code.equals(yzm)) {
				num++;
				request.getServletContext().setAttribute(
						"REGISTER_CODE_Count" + sysUser.getMobile(), num + "");
				return new ResponseEnvelope<SysUser>(false, "验证码不对，请重新输入！",
						sysUser.getMsgId());
			}
		}else{
			return new ResponseEnvelope<SysUser>(false, "验证码已失效，请重新获取！",
				sysUser.getMsgId());
		}

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser .setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		try {
			sysUser.setId(loginUser.getId());

			sysUser.setNickName(sysUser.getMobile());
			// 媒介类型.如果没有值，则表示为web前端（2）
			sysUser.setGroupId(0);
			sysSave(sysUser, request);
			sysUserService.update(sysUser);
			SysUserCacher.remove(loginUser.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "保存失败！",
					sysUser.getMsgId());
		}

		List<SysUser> list = null;
		
		if (Utils.enableRedisCache()) {
			list = SysUserCacher.getSysList();
		} else {
			list = sysUserService.getSysList();
		}
		//request.getSession().setAttribute("sysUserList", list);

		// return new
		// ResponseEnvelope<SysUser>(sysUser,sysUser.getMsgId(),true);

		return new ResponseEnvelope<SysUser>(true, sysUser.getMsgId(), true);

	}

	/**
	 * 修改Email(移动端引用)
	 */
	@RequestMapping(value = "/app/updateEmail")
	@ResponseBody
	public Object updateEmail(@PathVariable String style,
			@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (sysUser.getMsgId() == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！！",
					sysUser.getMsgId());
		}
		if (sysUser.getEmail() == null) {
			return new ResponseEnvelope<SysUser>(false, "Email地址不能为空！",
					sysUser.getMsgId());
		}
		if (!emailFormat(sysUser.getEmail())) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的邮箱地址！",
					sysUser.getMsgId());
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		try {
			sysUser.setId(loginUser.getId());

			sysUser.setGroupId(0);
			sysSave(sysUser, request);
			sysUserService.update(sysUser);
			SysUserCacher.remove(sysUser.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "保存失败！",
					sysUser.getMsgId());
		}

		return new ResponseEnvelope<SysUser>(true, sysUser.getMsgId(), true);
	}
	
	/**
	 * 修改密码 (app端)
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @param msgId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/updatePassword")
	@ResponseBody
	public Object updatePassword(String id, String oldPassword,
			String newPassword, String msgId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数msgId不能为空", msgId);
		}
		if (StringUtils.isEmpty(id)) {
			return new ResponseEnvelope<SysUser>(false, "用户id不能为空", msgId);
		}
		if (StringUtils.isEmpty(oldPassword)) {
			return new ResponseEnvelope<SysUser>(false, "原密码不能为空", msgId);
		}
		if (StringUtils.isEmpty(newPassword)) {
			return new ResponseEnvelope<SysUser>(false, "新密码不能为空", msgId);
		}

		SysUser sysUser = sysUserService.get(Utils.getIntValue(id));
		if (sysUser == null) {
			return new ResponseEnvelope<SysUser>(false , "找不到该用户", msgId);
		}

		if (sysUser.getPassword().equals(oldPassword)) {
			sysUser.setPassword(newPassword);
			sysUser.setPasswordUpdateFlag(0);
			
			//在修改密码前,先根据用户的手机号码去判断是否存在相同的用户信息，如果存在就一并修改
			List<SysUser> userInfo = sysUserService.selectByMobile(sysUser.getMobile());
			if(null != userInfo){
				if(userInfo.size()>1){
					List<Integer> li = new ArrayList<Integer>();
					for(SysUser info : userInfo){
						li.add(info.getId());
					}
					sysUser.setUserId(li);
					sysUser.setGmtModified(new Date());
					sysUser.setModifier(sysUser.getMobile());
					sysUserService.updateByMobileInfo(sysUser);
				}else{
					sysUserService.update(sysUser);
				}
			}
			
			List<SysUser> list = null;
			
			if (Utils.enableRedisCache()) {
				list = SysUserCacher.getSysList();
			} else {
				list = sysUserService.getSysList();
			}
			//request.getSession().setAttribute("sysUserList", list);

			// return new ResponseEnvelope<SysUser>(sysUser,msgId,true);

			return new ResponseEnvelope<SysUser>(true, msgId, true);

		} else {
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true , "旧密码不正确！", msgId);
		}
	}

	/**
	 * 获取 用户详情
	 */
	@RequestMapping(value = "/app/get")
	@ResponseBody
	public Object get(String msgId, HttpServletRequest request,
			HttpServletResponse response) {
		if (msgId == null) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser =com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		SysUser sysUser = new SysUser();
		try {
			sysUser = sysUserService.get(loginUser.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<SysUser>(sysUser, msgId, true);
	}

	/**
	 * 获取 用户详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,
			@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response) {
		
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			sysUser = (SysUser) JsonUtil.getJsonToBean(jsonStr, SysUser.class);
			if (sysUser == null) {
				return new ResponseEnvelope<SysUser>(false, "none", "传参异常!");
			}
			id = sysUser.getId();
			msgId = sysUser.getMsgId();
		} else {
			id = sysUser.getId();
			msgId = sysUser.getMsgId();
		}

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}

		if (id == null) {
			return new ResponseEnvelope<SysUser>(false, "参数缺少id!", msgId);
		}

		// 记录访问
		UserAccess userAccess = new UserAccess();
		// 访问id
		userAccess.setAccessId(loginUser.getId());
		// 被访问id
		userAccess.setBeAccessedId(sysUser.getId());
		if (loginUser.getId() != sysUser.getId()) {
			sysSave(userAccess, request);
			if (userAccess != null && userAccess.getAccessId() != null
					&& userAccess.getBeAccessedId() != null) {
				userAccessService.add(userAccess);
			}
		}

		try {
			if (Utils.enableRedisCache()) {
				sysUser = SysUserCacher.get(id);
			} else {
				sysUser = sysUserService.get(id);
			}

			if (sysUser == null) {
				return new ResponseEnvelope<SysUser>(false, "没有找到用户!", msgId);
			}
			// 当前登录人是否已经关注过该用户
			// LoginUser loginUser = new LoginUser();
			// if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ||
			// com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			// loginUser.setId(-1);
			// loginUser.setLoginName("nologin");
			// } else {
			// loginUser = (LoginUser)
			// com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			// }
			SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
			sysUserFansSearch.setUserId(id);
			sysUserFansSearch.setFansUserId(loginUser.getId());
			Integer count = 0;
			if (Utils.enableRedisCache()) {
				count = SysUserFansCacher.getCount(sysUserFansSearch);
			} else {
				count = sysUserFansService.getCount(sysUserFansSearch);
			}

			if (count > 0) {
				sysUser.setAttentionState(1);
			} else {
				sysUser.setAttentionState(0);
			}
			// 查询所在地
			BaseArea baseArea = new BaseArea();
			baseArea.setAreaCode(sysUser.getAreaId() + "");
			List<BaseArea> list = null;
			if (Utils.enableRedisCache()) {
				list = BaseAreaCacher.getList(baseArea);
			} else {
				list = baseAreaService.getList(baseArea);
			}

			String areaName = "";
			String pid = "";
			if (list != null && list.size() > 0) {
				baseArea = list.get(0);
				areaName = baseArea.getAreaName();
				pid = baseArea.getPid();
				baseArea = new BaseArea();
				baseArea.setAreaCode(pid);
				list = baseAreaService.getList(baseArea);
				if (list != null && list.size() > 0) {
					baseArea = list.get(0);
					areaName = baseArea.getAreaName() + areaName;
				}
				sysUser.setAreaName(areaName);
			}
			ResPic resPic = null;
			if (Utils.enableRedisCache()) {
				resPic = ResourceCacher.getPic(sysUser.getPicId());
			} else {
				resPic = resPicService.get(sysUser.getPicId());
			}

			sysUser.setPicPath(resPic == null ? "" : resPic.getPicPath());
			sysUser.setFansCount(fansCount(sysUser.getId()));
			sysUser.setAttentionCount(attentionCount(sysUser.getId()));
			sysUser.setWorksCount(worksCount(sysUser.getId()));
			sysUser.setAccessCount(accessCount(sysUser.getId()));
			String speciality = sysUser.getSpecialityValue();
			StringBuffer specialityName = new StringBuffer();
			if (StringUtils.isNotBlank(speciality)) {
				if (speciality.contains(",")) {
					String str[] = speciality.split(",");
					for (String value : str) {
						SysDictionary dictionary = sysDictionaryService
								.getSysDictionaryByValue("designstyles",
										Integer.parseInt(value));
						specialityName.append(dictionary == null ? ""
								: dictionary.getName() + ",");
					}
					String name = specialityName.toString();
					sysUser.setSpecialityName(name.substring(0,
							name.length() - 1));
				} else {
					SysDictionary dictionary = sysDictionaryService
							.getSysDictionaryByValue("designstyles",
									Integer.parseInt(speciality));
					specialityName.append(dictionary == null ? "" : dictionary
							.getName());
					sysUser.setSpecialityName(specialityName.toString());
				}
			}

			// 岗位
			SysDictionary sysDic = sysDictionaryService
					.getSysDictionaryByValue("userType",
							StringUtils.isBlank(sysUser.getJob()) ? -1
									: Integer.valueOf(sysUser.getJob()));
			if (sysDic != null) {
				sysUser.setJobName(sysDic.getName());
			}
			if ("small".equals(style) && sysUser != null) {
				String sysUserJson = JsonUtil.getBeanToJsonData(sysUser);
				SysUserSmall sysUserSmall = new JsonDataServiceImpl<SysUserSmall>()
						.getJsonToBean(sysUserJson, SysUserSmall.class);

				return new ResponseEnvelope<SysUserSmall>(sysUserSmall, msgId,
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<SysUser>(sysUser, msgId, true);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UserAccess model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 验证手机是否存在
	 * 
	 * @param sysUser
	 * @param type
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkMobile")
	public void checkMobile(@ModelAttribute("sysUser") SysUser sysUser,
			String type, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String flag = "false";

		if ("edit".equals(type)) {
			SysUser user = sysUserService.get(sysUser.getId());
			if (user != null && sysUser.getMobile() != null
					&& sysUser.getMobile().equals(user.getMobile())) {
				flag = "true";
				response.getWriter().write(flag);
				return;
			}
		}

		List<SysUser> list = sysUserService.getList(sysUser);
		if (CustomerListUtils.isNotEmpty(list)) {
			flag = "false";
		} else {
			flag = "true";
		}

		response.getWriter().print(flag);
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload")
	public void upload(@RequestParam MultipartFile picFile,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// String picPath= "E:\\workspace\\onlineDecorate\\WebContent\\upload";
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser =com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// 上传文件的原名(即上传前的文件名字)
		String imageName = null;
		String suffix = null;

		String originalFilename = picFile.getOriginalFilename();
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 转换为字符串
		String formatDate = format.format(new Date());
		// 随机生成文件编号
		int random = new Random().nextInt(10000);
		imageName = new StringBuffer().append(formatDate).append(random)
				.toString()
				+ Utils.getCurrentDateTime(Utils.DATETIMESSS);
		suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

		// 获取配置的业务文件目录
		//String filePath = app.getString("system.sysUser.pic.upload.path");
		String filePath =   Utils.getPropertyName("config/res", "system.sysUser.pic.upload.path", "/system/sysUser/pic/");
		filePath = Utils.replaceDate(filePath);
		// 数据库存储目录
		String dbFilePath = filePath + imageName + suffix;
		// 文件服务器的存储路径
		String realPath = Tools.getRootPath(filePath,"") + filePath.replace("/", "\\");
		if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
			realPath = Tools.getRootPath(filePath, "")  + filePath;
		}
		try {
			// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
			FileUtils.copyInputStreamToFile(picFile.getInputStream(), new File(
					realPath, imageName + suffix));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fpath = realPath + imageName + suffix;
		File f = new File(fpath);
		Map map = FileUploadUtils.getMap(f, Tools.getRootPath(fpath, ""), false);

		String basePath = app.getString("app.server.url");
		ResPic resPic = new ResPic();
		resPic.setPicName(imageName);
		resPic.setPicPath(dbFilePath);
		resPic.setPicCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_"
				+ Utils.generateRandomDigitString(6));
		resPic.setPicFileName(imageName);
		if (map.get("PicWeight") != null) {
			resPic.setPicWeight(map.get("PicWeight").toString());
		}
		if (map.get("PicHeight") != null) {
			resPic.setPicHigh(map.get("PicHeight").toString());
		}
		// if( map.get("FileSize") != null ){
		// resPic.setPicSize(Integer.valueOf(map.get("FileSize").toString()));
		// }
		resPic.setPicLevel("0");
		resPic.setPicFormat(suffix.substring(1, suffix.length()));
		resPic.setFileKey("system.sysUser.pic.upload.path");
		resPic.setBusinessId(loginUser.getId());
		resPic.setPicSuffix(suffix);
		resPic.setPicType(PicType.USER);
		sysSave(resPic, request);
		int res_id = resPicService.add(resPic);
		/* 生成头像缩略图 */
		// system.sysUser.pic
		resPicService.createThumbnail(resPic, request);
		/* ftp上传 */
		try {
			boolean flag = false;
			// 仅支持ftp服务器上传
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
				logger.info("------用户头像上传ftp服务器删除本地文件");
				flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath
						+ imageName + suffix, filePath);
				if (flag) {
					// 删除本地
					FileUploadUtils.deleteFile(dbFilePath);
				} else {
					logger.info("---------仅支持ftp服务器文件上传异常！");
				}
			}
			// 3 本地和ftp同时上传(默认是本地上传)
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
				logger.info("------用户头像上传ftp服务器不删除本地文件");
				flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath
						+ imageName + suffix, filePath);
				if (!flag) {
					logger.info("---------本地和ftp服务器同时文件上传异常！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("----ftp上传异常：" + e.getMessage());
		}

		Map<String, Object> picMap = new HashMap<String, Object>();
		picMap.put("picUrl", basePath + "/upload" + dbFilePath);
		picMap.put("resId", res_id);
		JSONObject json = JSONObject.fromObject(picMap);
		response.getWriter().print(json);
	}

	/**
	 * 验证手机短信是否发送成功
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/sms")
	public void sms(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "0";
		/** 手机号码 */
		String mobile = request.getParameter("mobile");
		/** 短信验证码 */
		String code = request.getParameter("code");
		/** 短信验证码存入session(session的默认失效时间30分钟) */
		request.getSession().setAttribute("code", code);
		/** 如何初始化失败返回 */
		if (!initClient()) {
			return;
		}
		/** 单个手机号发送短信的方法的参数准备 */
		// 手机号码
		String mobilephone = mobile;
		// 短信内容+随机生成的6位短信验证码
		String content = "根据中国证监会举报中心委托，特向您发送此条短信。您的注册验证码为:" + code;
		// 操作用户的ID
		// Integer operId =
		// Integer.parseInt(Env.getInstance().getProperty("operId"));
		// 定时发送的的发送时间(缺省为空，如果即时发送，填空)
		String tosend_time = "";
		// 应用系统的短信ID，用户查询该短信的状态报告(缺省为0，即不需查询短信的状态报告)
		int sms_id = 0;
		// 黑名单过滤(0：不需要黑名单过滤，1：需要黑名单过滤，缺省为0)
		short backlist_filter = 0;
		// 禁止语过滤(0：不需要禁止语过滤，1：需要禁止语过滤，缺省为0)
		short fbdword_filter = 0;
		// 优先级(值越大优先级越高，0：普通，1,：优先，2：最高，缺省为0)
		short priority = 0;
		// 短信有效时间(格式为：YYYY-MM-DD HH:mm:ss目前为空)
		String valid_time = "";
		/** 发送短信之前先统计一个已经发送的短信条数 */
		// int messageCount = countService.findAllRecord(mobilephone);
		// log.info("已发短信条数为：" +messageCount);
		/*
		 * if(messageCount < 5){
		 *//** 单个手机号发送短信 */
		/*
		 * if (!sendMessage(mobilephone, content, operId, tosend_time, sms_id,
		 * backlist_filter, fbdword_filter, priority, valid_time)) { result =
		 * "0";// 失败 } else { result = "1";// 成功
		 *//** 发送一条短信，记录一条短信记录，为了方便之后的统计短信发送次数 */
		/*
		 * count.setPhone(mobilephone);/ / 手机号码 count.setCaptcha(code);// 短信验证码
		 * count.setSendTime(CommonUtil. getNowDate());// 短信发送时间 if(count !=
		 * null){ countService.saveEntity(count ); log.info("短信验证码发送记录保存成功!"); }
		 * } }else{ result = "2";//一个手机号码最多发送5条短信验证码 log.info("该手机号码今天发送验证码过多");
		 * }
		 */
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(result.toString());
	}

	/**
	 * 验证短信验证码是否正确
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkCode")
	public void checkCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String flag = "false";
		// 如果发送短信接口为关闭状态，则直接返回true
		if ("false".equals(SmsClient.ALLOWED_TO_SEND)) {
			flag = "true";
		} else {
			// 获取发送短信的手机号
			String mobile = request.getParameter("mobile");
			/** 获取手动输入的手机短信验证码 */
			String smsCheckCode = request.getParameter("smsCheckCode");
			/** 获取session中存放的手机短信验证码 */
			String code = (String) request.getSession().getAttribute(
					"REGISTER_CODE_" + mobile);
			// 获取发送验证码的时间
			long currentTime = System.currentTimeMillis();
			long sendCodeTime = (long) request.getSession().getAttribute(
					"REGISTER_CODE_TIMEOUT_" + mobile);
			try {
				if ((currentTime - sendCodeTime) < SmsClient.VALID_TIME) {
					if (smsCheckCode != code && !smsCheckCode.equals(code)) {

						flag = "false";
					} else {
						flag = "true";
					}
				} else {
					flag = "false";
				}
			} catch (Exception e) {
				throw new RuntimeException("短信验证失败", e);
			}
		}

		response.getWriter().print(flag);
	}

	/**
	 * WebService客户端初始化
	 * 
	 */
	public static boolean initClient() {
		/**
		 * 判断客户端是否已经初始化
		 */
		// if (!SmsWebClient.enable()) {
		int ret = 0;
		try {
			/*
			 * ret = SmsWebClient.init(url, userName, passWord); if (ret == -1
			 * || !SmsWebClient.enable()) { log.info("短信平台接口初始化失败！"); return
			 * false; } log.info("短信平台接口初始化成功！" + ret + "-----");
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
			// log.info("短信平台接口初始化过程中异常！");
		}
		// }
		return true;
	}

	/**
	 * 邮件发送
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sendemail")
	public void sendemail(HttpServletRequest request,
			HttpServletResponse response) {

		String email = request.getParameter("email");
		String id = request.getParameter("id");

		String url = app.getString("app.server.url");
		// /邮件的内容
		StringBuffer sb = new StringBuffer(
				"点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
		sb.append("<a href=\"" + url
				+ "/online/web/system/sysUser/updateEmail.htm?email=");
		sb.append(email);
		sb.append("&id=");
		sb.append(id);
		sb.append("\">" + url
				+ "/online/web/system/sysUser/updateEmail.htm?email=");
		sb.append(email);
		sb.append("</a>");
		// 发送邮件
		SendEmail.send(email, sb.toString());
	}

	/**
	 * 邮箱修改
	 * 
	 * @param sysUser
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEmail")
	public Object updateEmail(@ModelAttribute("sysUser") SysUser sysUser,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			sysUserService.update(sysUser);
			SysUserCacher.remove(sysUser.getId());
			List<SysUser> list = null;
			
			if (Utils.enableRedisCache()) {
				list = SysUserCacher.getSysList();
			} else {
				list = sysUserService.getSysList();
			}
			//request.getSession().setAttribute("sysUserList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Utils.getPageUrl(request, JSPMAIN + "/success");
	}

	/**
	 * ResPic自动存储系统字段
	 */
	private void sysSave(ResPic model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysUser model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				String mediaType = (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || request
						.getSession().getAttribute("mediaType") == null) ? "2"
						: (String) request.getSession().getAttribute(
								"mediaType");
				model.setMediaType(Utils.getIntValue(mediaType));
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 手机验证码接口
	 */
	@RequestMapping(value = "/app/sms")
	@ResponseBody
	public Object sms(String mobile, String type, String msgId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！！", msgId);
		}
		if (StringUtils.isEmpty(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "手机号码不能为空！", msgId);
		}
		if (!isMobile(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！", msgId);
		}
		SysUser sysUser = new SysUser();
		sysUser.setMobile(mobile);
		sysUser.setIsDeleted(0);
		List<SysUser> list = sysUserService.getList(sysUser);
		if (StringUtils.isNotEmpty(type)) {
			if ("register".equals(type)) {
				if (Lists.isNotEmpty(list)) {
					//success设置为true便于app关闭计时
					return new ResponseEnvelope<SysUser>(true,
							"该用户已被注册，请重新输入手机号！", msgId,false);
				}
			} else if ("updateMobile".equals(type)) {
				if (Lists.isNotEmpty(list)) {
				    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
					return new ResponseEnvelope<SysUser>(true,
							"该手机已被绑定，请重新输入手机号！", msgId,false);
				}
			} else if (Lists.isEmpty(list)) {
			    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				return new ResponseEnvelope<SysUser>(true , "找不到该用户!", msgId);
			}
		}else  if (Lists.isEmpty(list)) {
          //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
          return new ResponseEnvelope<SysUser>(true , "找不到该用户!", msgId);
		}
		String result = "0";
		/** 短信验证码 */
		String code = Utils.generateRandomDigitString(6);
		if ("1".equals(cacheEnable)) {
			CacheManager.getInstance().getCacher().remove("REGISTER_CODE_" + mobile);
			CacheManager.getInstance().getCacher().set("REGISTER_CODE_" + mobile, code, 0);
			CacheManager.getInstance().getCacher().remove("REGISTER_CODE_TIMEOUT_" + mobile);
			CacheManager.getInstance().getCacher().set("REGISTER_CODE_TIMEOUT_" + mobile,System.currentTimeMillis()+"",0);
			CacheManager.getInstance().getCacher().remove("REGISTER_CODE_Count" + mobile);
			CacheManager.getInstance().getCacher().set("REGISTER_CODE_Count" + mobile, "0", 0);

		} else {
			request.getServletContext().setAttribute("REGISTER_CODE_" + mobile,	code);
			request.getServletContext().setAttribute("REGISTER_CODE_TIMEOUT_" + mobile,System.currentTimeMillis());
			request.getServletContext().setAttribute("REGISTER_CODE_Count" + mobile, "0");
		}
		// ////System.out.println("验证码------------------："+code);

		/** 发送短信的方法的参数准备 */
		// 短信内容+随机生成的6位短信验证码
		String message = "";
		if ("register".equals(type)) {
			message=MessageFormat.format(
					SmsClient.app.getString("registerContext"), code,
					SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
		}else{
			message=MessageFormat.format(
					SmsClient.app.getString("updateMobileContext"), code,
					SmsClient.VALID_TIME / 60000, SmsClient.SERVICE_PHONE);
		}
		message = URLEncoder.encode(message, "UTF-8");
		// 短信接口参数
		// mobile = "13906513994,18358100817,15816860922";
		long seqId = System.currentTimeMillis();
		String params = "phone=" + mobile + "&message=" + message
				+ "&addserial=&seqid=" + seqId;
		result = SmsClient.sendSMS(SmsClient.SEND_MESSAGE, params);
	
		if ("0".equals(result)) {
			return new ResponseEnvelope<String>(true, "发送成功！", msgId,true);
		} else {
			return new ResponseEnvelope<String>(false, "发送验证短信异常！", msgId);
		}
	}

	/*
	 * 找回密码接口
	 */
	@RequestMapping(value = "/app/findPassword")
	@ResponseBody
	public Object findPassword(String mobile, String newPassword, String code,
			String msgId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数msgId不能为空", msgId);
		}
		if (StringUtils.isEmpty(newPassword)) {
			return new ResponseEnvelope<SysUser>(false, "新密码不能为空", msgId);
		}
		if (StringUtils.isEmpty(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "手机号不能为空", msgId);
		}
		if (!isMobile(mobile)) {
			return new ResponseEnvelope<SysUser>(false, "请输入正确的手机号！", msgId);
		}
		if (StringUtils.isEmpty(code)) {
			return new ResponseEnvelope<SysUser>(false, "请输入验证码！", msgId);
		}
		
		/** 获取session中存放的手机短信验证码 */
		long currentTime = System.currentTimeMillis();
		long sendCodeTime = 0L;
		String yzm = "";
		int num = 0;
		//允许输入验证码次数
		String verify=SmsClient.app.getString("verifyCount");
	    int verifyCount=3;
		if(verify!=null){
			verifyCount=Integer.parseInt(verify);	
		}
		if ("1".equals(cacheEnable)) {
			Object codeObject = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_" + mobile);
			Object timeoutObject = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_TIMEOUT_" + mobile);
			if (codeObject != null) {
				yzm = codeObject.toString();
			}
			if (timeoutObject != null) {
				sendCodeTime = Long.parseLong(timeoutObject.toString());
			}
			Object count = CacheManager.getInstance().getCacher()
					.get("REGISTER_CODE_Count" + mobile);
			if (count != null) {
				num = Integer.parseInt(count.toString());
			}
		} else {
			if( request.getServletContext().getAttribute("REGISTER_CODE_TIMEOUT_" + mobile) == null 
				|| request.getServletContext().getAttribute("REGISTER_CODE_" + mobile) == null
				|| request.getServletContext().getAttribute("REGISTER_CODE_Count" + mobile) == null ){
				return new ResponseEnvelope<SysUser>(false , "验证码已失效，请重新获取！", msgId);
			}
			Object time = request.getServletContext().getAttribute("REGISTER_CODE_TIMEOUT_" + mobile);
			sendCodeTime = Long.parseLong(time.toString());
			   
			yzm = (String) request.getServletContext().getAttribute(
					"REGISTER_CODE_" + mobile);
			String count = (String) request.getServletContext().getAttribute(
					"REGISTER_CODE_Count" + mobile);
			if (count != null) {
				num = Integer.parseInt(count);
			}
		}
		
		if (StringUtils.isBlank(yzm) || sendCodeTime <= 0) {
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(false , "验证码已失效，请重新获取！", msgId);
		}
		// 一分钟验证码失效
		if ((currentTime - sendCodeTime) > SmsClient.VALID_TIME) {
		  //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(false , "验证码已超时，请重新获取！", msgId);
		}
		
		if (num < verifyCount) {
			if (!code.equals(yzm)) {
				num++;
				if ("1".equals(cacheEnable)) {
					CacheManager.getInstance().getCacher()
							.set("REGISTER_CODE_Count" + mobile, num + "", 0);
				} else {
					request.getServletContext().setAttribute(
							"REGISTER_CODE_Count" + mobile, num + "");
				}
				 //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
				return new ResponseEnvelope<SysUser>(true , "验证码错误！",
						msgId);
			}
		} else {
		  //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true , "验证码已失效，请重新获取！", msgId,false);
		}
		SysUser sysUser = new SysUser();
		sysUser.setMobile(mobile);
		List<SysUser> list = sysUserService.getList(sysUser);
		/** List<SysUser> list = SysUserCacher.getAllList(sysUser); */
		if (CustomerListUtils.isEmpty(list)) {
		  //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<SysUser>(true , "找不到该用户", msgId);
		} else {
			SysUser user = list.get(0);
			user.setPassword(newPassword);
			user.setPasswordUpdateFlag(0);
			/** sysUserService.update(sysUser); */
			
			//在修改密码前,先根据用户的手机号码去判断是否存在相同的用户信息，如果存在就一并修改
			List<SysUser> userInfo = sysUserService.selectByMobile(mobile);
			if(null != userInfo){
				if(userInfo.size()>1){
					List<Integer> li = new ArrayList<Integer>();
					for(SysUser info : userInfo){
						li.add(info.getId());
					}
					user.setUserId(li);
					user.setGmtModified(new Date());
					user.setModifier(mobile);
					sysUserService.updateByMobileInfo(user);
				}else{
					sysUserService.update(user);
				}
			}
			
			List<SysUser> list1 = null;
			if (Utils.enableRedisCache()) {
				list1 = SysUserCacher.getSysList();
			} else {
				list1 = sysUserService.getSysList();
			}
//			request.getSession().setAttribute("sysUserList", list1);

			if ("1".equals(cacheEnable)) {
				CacheManager.getInstance().getCacher().remove("REGISTER_CODE_Count" + mobile);
				CacheManager.getInstance().getCacher().set("REGISTER_CODE_Count" + mobile, "4", 0);

			} else {
				request.getServletContext().setAttribute("REGISTER_CODE_Count" + mobile, "4");
			}
			
			return new ResponseEnvelope<SysUser>(true, msgId, true);
		}
	}

	/**
	 * 邮件发送
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/app/sendEmail")
	@ResponseBody
	public Object sendEmail(String msgId, String email,
			HttpServletRequest request, HttpServletResponse response) {

		// String email = request.getParameter("email");
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SysUser>(false, "参数msgId不能为空！", msgId);
		}
		if (StringUtils.isEmpty(email)) {
			return new ResponseEnvelope<SysUser>(false, "参数email不能为空！", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// String id = loginUser.getId()+"";
		// String url = app.getString("app.server.url");
		/* 生成邮箱验证码 */
		String emailCode = Utils.generateRandomDigitString(6);
		request.getServletContext().setAttribute("emailCode",
				email + ":" + emailCode + ":" + loginUser.getLoginName());
		// /邮件的内容
		// StringBuffer sb=new
		// StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
		// sb.append("<a href=\""+url+"/online/web/system/sysUser/updateEmail.htm?email=");
		// sb.append(email);
		// sb.append("&id=");
		// sb.append(id);
		// sb.append("\">"+url+"/online/web/system/sysUser/updateEmail.htm?email=");
		// sb.append(email);
		// sb.append("</a>");
		StringBuffer stringBuffer = new StringBuffer("<h4>亲爱的用户:</h4><br><br>");
		stringBuffer.append("您好！感谢您使用三度云享家，您正在进行邮箱验证，本次请求的验证码为：<br>");
		stringBuffer.append("<font color=\"red\"><strong>" + emailCode
				+ "</strong></font>");
		try {
			// 发送邮件
			SendEmail.send(email, stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUser>(false, "邮件发送异常！", msgId);
		}
		return new ResponseEnvelope<SysUser>(true, "验证码已发送到您的邮箱,请登录查看收件箱或垃圾箱！",
				msgId,true);
	}

	/**
	 * 验证验证码并且修改邮箱
	 * 
	 * @author huangsongbo
	 * @param code
	 *            验证码
	 * @param msgId
	 *            msgId
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/checkEmailCodeAndSaveInfo")
	@ResponseBody
	public Object checkEmailCodeAndSaveInfo(String code, String msgId,
			HttpServletRequest request) {
		if (StringUtils.isBlank(msgId))
			return new ResponseEnvelope<>(false, "参数msgId不能为空", msgId);
		if (StringUtils.isBlank(code))
			return new ResponseEnvelope<>(false, "参数code不能为空", msgId);
		String emailCodeInfo = (String) request.getServletContext()
				.getAttribute("emailCode");
		if (StringUtils.isBlank(emailCodeInfo))
			return new ResponseEnvelope<>(false, "没有生成验证码", msgId);
		String email = emailCodeInfo.split(":")[0];
		String emailCode = emailCodeInfo.split(":")[1];
		String username = emailCodeInfo.split(":")[2];
		/* 获得用户信息 */
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser =com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (loginUser.getId() == null)
			return new ResponseEnvelope<>(false, "未获得登录信息,请重新登录", msgId);
		if (!StringUtils.equals(username, loginUser.getLoginName()))
			/* 生成验证码的用户和登录用户不一致 */
			return new ResponseEnvelope<>(false, "没有生成验证码", msgId);
		if (!StringUtils.equals(emailCode, code))
		    //success 设为true,配合前端设计（使提示由弹框提示变为文字提示）
			return new ResponseEnvelope<>(true , "验证码错误！", msgId);
		SysUser sysUser = sysUserService.get(loginUser.getId());
		sysUser.setEmail(email);
		sysUserService.update(sysUser);
		/* 修改成功后去除session中emailCode信息(要修改邮箱需再次获得验证码) */
		request.getServletContext().setAttribute("emailCode", "");
		return new ResponseEnvelope<>(true,"修改成功！", msgId ,true);
	}

	/**
	 * 用户退出接口(删除缓存信息)
	 * @author huangsongbo
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 *  如需修改该接口名，请修改Myfilter中对退出接口放行的校验
	 */
	@RequestMapping("/sysUserQuit")
	@ResponseBody
	public Object sysUserQuit(SysUserLoginLog sysUserLoginLog,HttpServletRequest request, String msgId) throws UnknownHostException, SocketException {
		
		LoginUser loginUser =  com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(CacheManager.getInstance().getCacher().get("userIdLog") != null) {
			//用户数据采集
			sysUserLoginLog.setUserId(Integer.parseInt(CacheManager.getInstance().getCacher().get("userIdLog")));        	//取得登录的用户
			sysUserLoginLog.setLoginTime(new Date());   			//登出时间
			sysUserLoginLog.setLoginType(2);						//登录类型(2:登出)
			sysUserLoginLog.setServerIp(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP());		//服务器IP
			sysUserLoginLogService.txtSaveUserLog(sysUserLoginLog);
		}
		logger.info("loginUser" + loginUser);
		if (loginUser != null && loginUser.getId() != null && loginUser.getId() >0){
			if (Utils.enableRedisCache()) {
				SysUserCacher.removeCacheUserByUserkey(loginUser.getUserKey());
			}
		}
		CacheManager.getInstance().getCacher().remove("userIdLog");
		CacheManager.getInstance().getCacher().remove("loginTimeLog");
		CacheManager.getInstance().getCacher().remove("mobileLog");
		CacheManager.getInstance().getCacher().remove("clientIp");
		CacheManager.getInstance().getCacher().remove("operationLoginDevice");
		CacheManager.getInstance().getCacher().remove("operationSystemModel");
		return new ResponseEnvelope<>(true, "success", msgId);
	}

	/**
	 * 心跳接口 更新该用户的在缓存中的信息(登录有效时长信息:validTime)
	 * 
	 * @author huangsongbo
	 * @param request
	 * @return
	 */
	// @RequestMapping("/updateValidTime")
	// @ResponseBody
	// public Object updateValidTime(String msgId,HttpServletRequest request){
	// LoginUser loginUser = new LoginUser();
	// if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ||
	// com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
	// loginUser.setLoginName("nologin");
	// } else {
	// loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
	// }
	// if(loginUser.getId()==null||loginUser.getId()<1)
	// throw new RuntimeException("------心跳接口->未找到登录用户信息");
	// sysUserService.updateValidTime(loginUser.getId());
	// return new ResponseEnvelope<>(true, "success", msgId);
	// }

	/**
	 * 获取用户余额
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUserBalance")
	@ResponseBody
	public Object getUserBalance(
			@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId,
			String msgId, HttpServletRequest request,
			HttpServletResponse response) {
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SysUser>(false, msg, "");
		}
		if (userId == 0) {
			userId = getCurrentUserId(request);
		}
		SysUser sysUser = sysUserService.get(userId);
		if (sysUser == null) {
			msg = "没有找到该用户数据！";
			return new ResponseEnvelope<SysUser>(false, msg, msgId);
		} else {
			sysUser.setBalanceAmount(sysUser.getBalanceAmount());
		}

		return new ResponseEnvelope<SysUser>(sysUser, msgId, true);
	}

	private int getCurrentUserId(HttpServletRequest request) {
		int userId = 0;
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) != null) {
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userId = loginUser.getId();
		}
		return userId;
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysUserLastLoginLog model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}
	/**
	 * 获取用户余额
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getFiles")
	@ResponseBody
	public Object getFiles(HttpServletRequest request,
			HttpServletResponse response) {
		sysUserSystemOperationLogService.userOperationLog();
		return "OK";
	}
	/**
	 * 修复旧数据：给所有用户添加U3D默认权限
	 */
	@RequestMapping(value = "/addU3dMenu")
	public Object addU3dMenu(){
		//查询所有用户Id
		List<Integer> ids = sysUserMapper.getAllId();
		//查询U3d默认权限对应的角色id
		Integer roleId = sysRoleMapper.getIdByCode();
		SysUserRole sysUserRole = new SysUserRole();
		if(ids != null && roleId > 0) {
			for(Integer userId:ids) {
				//根据用户id和角色id判断该用户是否配置了改角色
				sysUserRole = sysUserRoleMapper.getByUserIdAndRoleId(userId, roleId);
				if(sysUserRole!=null) {
//					ids.remove(userId);
				}else {
					SysUserRole s = new SysUserRole();
					s.setUserId(userId);
					s.setRoleId(roleId);
					s.setCreator("yangzhun");
					s.setGmtCreate(new Date());
					s.setModifier("yangzhun");
					s.setGmtModified(new Date());
					s.setIsDeleted(0);
					s.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_" + Utils.generateRandomDigitString(6));
					
					Integer id = sysUserRoleMapper.insertSelective(s);
				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/app/loginForAuto")
	@ResponseBody
	public ResponseEnvelope <SysUser> loginForAuto(SysUser sysUser, String terminalImei,String mediaType,
			HttpServletRequest request, HttpServletResponse response,
			Integer forceLogin,Integer userId) throws SocketException {
		ResponseEnvelope <SysUser>res = null;
		//验证白名单
		boolean flag = sysUserService.checkWhiteList(terminalImei);
		if (!flag ) {
			return new ResponseEnvelope<SysUser>(false,"数据异常",sysUser.getMsgId());
		}
		SysUser user = sysUserService.get(userId);
		//拿到UserId 查用户信息。
		if(StringUtils.isEmpty(mediaType)) {
			mediaType = "3";
		}
		if (user != null) {
			String appWebSocketMessage = webSocket.getString("app.webSocket.message");
			String appWebSocketPayOrder = webSocket.getString("app.webSocket.payOrder");
			user.setWebSocketMessage(appWebSocketMessage);
			user.setPayCallBackServer(appWebSocketPayOrder);
		
			/* 保存心跳间隔信息 */
			Integer heartbeatTime = Integer.valueOf(Utils.getValue("heartbeatTime", "120"));
			user.setHeartbeatTime(heartbeatTime);
			user.setServerUrl(SERVERURL);
			user.setResourcesUrl(RESOURCESURL);
			user.setSitekey(SITEKEY);
			user.setSiteName(SITENAME);
			//用token加密app用来解密的key
		
			// 设置分布式域名配置
			user.setResourcesUrls(resourcesUrls);
			
			String userKey = UUID.randomUUID().toString();
			Map<String , Object> payload=new HashMap<String, Object>();
			Date date=new Date();
			payload.put("uid", user.getId());//用户ID
			payload.put("uname", user.getNickName());//
			payload.put("mtype", mediaType);//mediatype
			payload.put("uphone", user.getMobile());//
			payload.put("appkey", userKey);
			payload.put("ukey", userKey);
			payload.put("utype", user.getUserType());
			payload.put("iat", date.getTime());//生成时间
			
			payload.put("ext",date.getTime()+1000*60*60*SystemCommonConstant.USER_TIME_OUT_HOUR);//过期时间6小时
			payload.put("rsource","auto"); //请求来源 （自动渲染）
			
			String token=Jwt.createToken(payload);
			user.setToken(token);
			
			user = sysUserService.EencryptKey(user); //TODO Check this code
			
			LoginUser loginUser = user.toLoginUser();
			loginUser.setMediaType(mediaType);
			loginUser.setUserKey(userKey);
			//Cache 用户登录信息
			if (Utils.enableRedisCache()) {
			      SysUserCacher.cacheTheAutoLoginUserInfo(loginUser, SystemCommonConstant.USER_TIME_OUT_HOUR*60*60);
				/*SysUserCacher.cacheTheLoginUserInfo(loginUser, SystemCommonConstant.USER_TIME_OUT_HOUR*60*60);*/ //单位为秒
			}
			
			res = new ResponseEnvelope<SysUser>(user, sysUser.getMsgId(), true);
		}
		
		return res;
	}

	@RequestMapping(value = "/getAppUrl")
	@ResponseBody
	public ResponseEnvelope getAppUrl(String  msgId){
		HashMap<String, String> map = new HashMap<>();
		map.put("normalUrl",SERVERURL);
		map.put("ssoUrl",SSOURL);
		map.put("resourcesUrl",RESOURCESURL);
		map.put("userUrl",USERURL);
		return new ResponseEnvelope(true,map,msgId);
	}
}
