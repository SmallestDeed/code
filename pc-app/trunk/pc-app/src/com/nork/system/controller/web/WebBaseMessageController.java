package com.nork.system.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.util.SystemCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.cache.BaseMessageCacher;
import com.nork.system.cache.SysUserFansCacher;
import com.nork.system.model.BaseMessage;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserFans;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageRecieveSearch;
import com.nork.system.model.search.BaseMessageSearch;
import com.nork.system.model.search.SysMessageRecordSearch;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.model.small.BaseMessageSmall;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysMessageRecordService;
import com.nork.system.service.SysUserFansService;
import com.nork.system.service.SysUserService;

/**
 * @Title: BaseMessageController.java
 * @Package com.nork.system.controller
 * @Description:系统模块-消息表Controller
 * @createAuthor pandajun
 * @CreateDate 2015-08-13 14:30:45
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/system/baseMessage")
public class WebBaseMessageController {
	private static Logger logger = Logger.getLogger(WebBaseMessageController.class);
	private final JsonDataServiceImpl<BaseMessage> JsonUtil = new JsonDataServiceImpl<BaseMessage>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user/baseMessage";
	private final Integer READ_MESSAGE = 1;//已读消息
	private final Integer UNREAD_MESSAGE = 0;//未读消息

	@Autowired
	private BaseMessageService baseMessageService;
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private SysUserFansService sysUserFansService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMessageRecordService sysMessageRecordService;
	@Autowired
	private ResRenderPicService resRenderPicService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 保存 消息表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@PathVariable String style, @ModelAttribute("baseMessage") BaseMessage baseMessage,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessage = (BaseMessage) JsonUtil.getJsonToBean(jsonStr, BaseMessage.class);
			if (baseMessage == null) {
				return new ResponseEnvelope<BaseMessage>(false, "传参异常!", "none");
			}
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			baseMessage.setUserId(loginUser.getId());
		}
		try {
			String userId = request.getParameter("userId") == "" ? null : request.getParameter("userId");
			sysSave(baseMessage, request);
			if (baseMessage.getId() == null) {
				if (baseMessage.getMessageType() == 0 && baseMessage.getBusinessTypeId() == 1) {
					SysUserFans sysUserFans = new SysUserFans();
					sysUserFans.setUserId(Integer.parseInt(userId));
					sysUserFans.setFansUserId(loginUser.getId());
					/*sysUserFans = sysUserFansService.getObject(sysUserFans);
					if(sysUserFans!=null){
						   baseMessage.setBusinessObjId(sysUserFans.getId());
					}*/
					List<SysUserFans> lstFans=null;
					if(Utils.enableRedisCache()){
						lstFans=SysUserFansCacher.getList(sysUserFans);
					}else{
						lstFans = sysUserFansService.getList(sysUserFans);
					}
					
					if(CustomerListUtils.isNotEmpty(lstFans)){
						baseMessage.setBusinessObjId(lstFans.get(0).getId());
					}
				}
				int id = baseMessageService.add(baseMessage);
				if (baseMessage.getMessageType() == 1 && baseMessage.getBusinessTypeId() == 3) {
					baseMessage.setBusinessObjId(id);
					baseMessageService.update(baseMessage);
				}
				BaseMessageRecieve baseMessageRecieve = new BaseMessageRecieve();
				baseMessageRecieve.setMessageId(id);
				if (!"".equals(userId) && userId != null) {
					baseMessageRecieve.setUserId(Integer.parseInt(userId));
					baseMessageRecieve.setIsReaded(0);
					sysSave(baseMessageRecieve, request);
					if (baseMessageRecieve.getId() == null) {
						baseMessageRecieveService.add(baseMessageRecieve);
					}
				}
				logger.info("add:id=" + id);
				baseMessage.setId(id);
			} else {
				int id = baseMessageService.update(baseMessage);
				BaseMessageCacher.remove(id);
				logger.info("update:id=" + id);
			}

			if ("small".equals(style)) {
				String baseMessageJson = JsonUtil.getBeanToJsonData(baseMessage);
				BaseMessageSmall baseMessageSmall = new JsonDataServiceImpl<BaseMessageSmall>()
						.getJsonToBean(baseMessageJson, BaseMessageSmall.class);

				return new ResponseEnvelope<BaseMessageSmall>(baseMessageSmall, baseMessage.getMsgId(), true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessage.getMsgId());
		}
		return new ResponseEnvelope<BaseMessage>(true, baseMessage.getMsgId(), true);
	}

	/**
	 * 通知原作者接口
	 */
	@RequestMapping(value = "/notifyAuthor")
	@ResponseBody
	public Object notifyAuthor(@PathVariable String style, @ModelAttribute("baseMessage") BaseMessage baseMessage,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// BaseMessage baseMessage = new BaseMessage ();
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessage = (BaseMessage) JsonUtil.getJsonToBean(jsonStr, BaseMessage.class);
			if (baseMessage == null) {
				return new ResponseEnvelope<BaseMessageSmall>(false, "传参异常!", "none");
			}
		}
		Integer userId = 0;
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userId = loginUser.getId();
			baseMessage.setUserId(userId);
		}

		String msg = "";
		String receiverId = request.getParameter("receiverId") == "" ? null : request.getParameter("receiverId");
		if (StringUtils.isBlank(baseMessage.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<BaseMessageSmall>(false, msg, baseMessage.getMsgId());
		}
		if (StringUtils.isBlank(receiverId)) {
			msg = "参数receiverId不能为空";
			return new ResponseEnvelope<BaseMessageSmall>(false, msg, baseMessage.getMsgId());
		}
		if (baseMessage.getBusinessObjId() == null) {
			msg = "参数businessObjId不能为空";
			return new ResponseEnvelope<BaseMessageSmall>(false, msg, baseMessage.getMsgId());
		} else if (baseMessage.getBusinessObjId() != null) {
			DesignPlan dpc = new DesignPlan();
			dpc = designPlanService.get(baseMessage.getBusinessObjId());
			if (dpc == null) {
				msg = "该方案不存在";
				return new ResponseEnvelope<BaseMessageSmall>(false, msg, baseMessage.getMsgId());
			}
		}

		try {
			baseMessage.setBusinessObjType("design_plan");
			baseMessage.setBusinessTypeId(2);
			baseMessage.setMessageType(0);
			baseMessage.setStatus(1);
			baseMessage.setContent("XXX已修改您的作品");
			sysSave(baseMessage, request);
			if (baseMessage.getId() == null) {
				int id = baseMessageService.add(baseMessage);
				BaseMessageRecieve baseMessageRecieve = new BaseMessageRecieve();
				baseMessageRecieve.setMessageId(id);
				if (!"".equals(receiverId) && receiverId != null) {
					baseMessageRecieve.setUserId(Integer.parseInt(receiverId));
					baseMessageRecieve.setIsReaded(1);
					sysSave(baseMessageRecieve, request);
					if (baseMessageRecieve.getId() == null) {
						baseMessageRecieveService.add(baseMessageRecieve);
					}
				}
				logger.info("add:id=" + id);
				baseMessage.setId(id);
			} else {
				int id = baseMessageService.update(baseMessage);
				BaseMessageCacher.remove(id);
				logger.info("update:id=" + id);
			}

			if ("small".equals(style)) {
				String baseMessageJson = JsonUtil.getBeanToJsonData(baseMessage);
				BaseMessageSmall baseMessageSmall = new JsonDataServiceImpl<BaseMessageSmall>()
						.getJsonToBean(baseMessageJson, BaseMessageSmall.class);

				return new ResponseEnvelope<BaseMessageSmall>(baseMessageSmall, baseMessage.getMsgId(), true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessageSmall>(false, "数据异常!", baseMessage.getMsgId());
		}
		String baseMessageJson = JsonUtil.getBeanToJsonData(baseMessage);
		BaseMessageSmall baseMessageSmall = new JsonDataServiceImpl<BaseMessageSmall>().getJsonToBean(baseMessageJson,
				BaseMessageSmall.class);

		return new ResponseEnvelope<BaseMessageSmall>(baseMessageSmall, baseMessage.getMsgId(), true);
	}

	/**
	 * 新增消息表,支持批量新增，传递userId=1,2,3格式即可
	 */
	@RequestMapping(value = "/batchSave")
	@ResponseBody
	public Object batchSave(@PathVariable String style, @ModelAttribute("baseMessage") BaseMessage baseMessage,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessage = (BaseMessage) JsonUtil.getJsonToBean(jsonStr, BaseMessage.class);
			if (baseMessage == null) {
				return new ResponseEnvelope<BaseMessage>(false, "传参异常!", "none");
			}
		}

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			baseMessage.setUserId(loginUser.getId());
		}
		// 验证参数
		if (baseMessage.getMessageType() == null) {
			return new ResponseEnvelope<BaseMessage>(false, "messageType参数不能为空!", baseMessage.getMsgId());
		}
		if (baseMessage.getBusinessTypeId() == null) {
			return new ResponseEnvelope<BaseMessage>(false, "businessTypeId参数不能为空!", baseMessage.getMsgId());
		}
		if (StringUtils.isBlank(baseMessage.getBusinessObjType())) {
			return new ResponseEnvelope<BaseMessage>(false, "businessObjType参数不能为空!", baseMessage.getMsgId());
		}
		if (baseMessage.getStatus() == null) {
			return new ResponseEnvelope<BaseMessage>(false, "status参数不能为空!", baseMessage.getMsgId());
		}
		if (StringUtils.isBlank(baseMessage.getContent())) {
			return new ResponseEnvelope<BaseMessage>(false, "content参数不能为空!", baseMessage.getMsgId());
		}
		if (baseMessage.getBusinessObjId() == null) {
			return new ResponseEnvelope<BaseMessage>(false, "businessObjId参数不能为空!", baseMessage.getMsgId());
		}
		String str = request.getParameter("userIds");
		if (StringUtils.isBlank(str)) {
			return new ResponseEnvelope<BaseMessage>(false, "userIds参数不能为空!", baseMessage.getMsgId());
		}
		try {
			sysSave(baseMessage, request);
			baseMessage.setContent(baseMessage.getContent().replaceAll("XXX", loginUser.getName()));
			int id = baseMessageService.add(baseMessage);
			logger.info("add:id=" + id);

			BaseMessageRecieve baseMessageRecieve = null;
			if (str.contains(",")) {
				String[] userId = str.split(",");
				if (!"".equals(userId) && userId != null) {
					for (String userIds : userId) {
						baseMessageRecieve = new BaseMessageRecieve();
						baseMessageRecieve.setMessageId(id);
						baseMessageRecieve.setUserId(Integer.parseInt(userIds));
						baseMessageRecieve.setIsReaded(0);
						sysSave(baseMessageRecieve, request);
						if (baseMessageRecieve.getId() == null) {
							baseMessageRecieveService.add(baseMessageRecieve);
						}
					}
				}
			} else {
				sysSave(baseMessage, request);
				String userId = "";
				if (baseMessage.getId() == null) {
					int i = baseMessageService.add(baseMessage);
					BaseMessageRecieve baseMessageRecieveu = new BaseMessageRecieve();
					baseMessageRecieveu.setMessageId(i);
					userId = request.getParameter("userId");
					if (!"".equals(userId) && userId != null) {
						baseMessageRecieveu.setUserId(Integer.parseInt(userId));
						baseMessageRecieveu.setIsReaded(0);
						sysSave(baseMessageRecieveu, request);
						if (baseMessageRecieveu.getId() == null) {
							baseMessageRecieveService.add(baseMessageRecieveu);
						}
					}
					logger.info("add:id=" + i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessage.getMsgId());
		}
		return new ResponseEnvelope<BaseMessage>(true, baseMessage.getMsgId(), true);
	}

	/**
	 * 新增消息表,支持批量新增，传递userId=1,2,3格式即可
	 */
	@RequestMapping(value = "/inviteDesigner")
	@ResponseBody
	public Object inviteDesigner(@PathVariable String style, @ModelAttribute("baseMessage") BaseMessage baseMessage,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessage = (BaseMessage) JsonUtil.getJsonToBean(jsonStr, BaseMessage.class);
			if (baseMessage == null) {
				return new ResponseEnvelope<BaseMessage>(false, "传参异常!", "none");
			}
		}

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			baseMessage.setUserId(loginUser.getId());
		}
		String str = request.getParameter("userIds");

		try {
			sysSave(baseMessage, request);
			baseMessage.setContent(baseMessage.getContent().replaceAll("XXX", loginUser.getName()));
			int id = baseMessageService.add(baseMessage);
			logger.info("add:id=" + id);

			BaseMessageRecieve baseMessageRecieve = null;
			if (str.contains(",")) {
				String[] userId = str.split(",");
				if (!"".equals(userId) && userId != null) {
					for (String userIds : userId) {
						baseMessageRecieve = new BaseMessageRecieve();
						baseMessageRecieve.setMessageId(id);
						baseMessageRecieve.setUserId(Integer.parseInt(userIds));
						baseMessageRecieve.setIsReaded(0);
						sysSave(baseMessageRecieve, request);
						if (baseMessageRecieve.getId() == null) {
							baseMessageRecieveService.add(baseMessageRecieve);
						}
					}
				}
			} else {
				sysSave(baseMessage, request);
				String userId = "";
				if (baseMessage.getId() == null) {
					int i = baseMessageService.add(baseMessage);
					BaseMessageRecieve baseMessageRecieveu = new BaseMessageRecieve();
					baseMessageRecieveu.setMessageId(i);
					userId = request.getParameter("userId");
					if (!"".equals(userId) && userId != null) {
						baseMessageRecieveu.setUserId(Integer.parseInt(userId));
						baseMessageRecieveu.setIsReaded(0);
						sysSave(baseMessageRecieveu, request);
						if (baseMessageRecieveu.getId() == null) {
							baseMessageRecieveService.add(baseMessageRecieveu);
						}
					}
					logger.info("add:id=" + i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessage.getMsgId());
		}
		return new ResponseEnvelope<BaseMessage>(true, baseMessage.getMsgId(), true);
	}

	/**
	 * 系统消息列表
	 * 
	 * @param baseMessageSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/messageList")
	public Object messageList(@ModelAttribute("baseMessageSearch") BaseMessageSearch baseMessageSearch,
			HttpServletRequest request, HttpServletResponse response) {

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			baseMessageSearch.setUserId(loginUser.getId());
		}

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessageSearch = (BaseMessageSearch) JsonUtil.getJsonToBean(jsonStr, BaseMessageSearch.class);
			if (baseMessageSearch == null) {
				return new ResponseEnvelope<BaseMessage>(false, "传参异常!", "none");
			}
		}

		List<BaseMessage> list = new ArrayList<BaseMessage>();
		int total = 0;
		try {
			total = baseMessageService.getCount(baseMessageSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = baseMessageService.getPaginatedList(baseMessageSearch);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessageSearch.getMsgId());
		}
		ResponseEnvelope<BaseMessage> res = new ResponseEnvelope<BaseMessage>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		// return new ResponseEnvelope<BaseMessage>(total,
		// list,baseMessageSearch.getMsgId());
		return Utils.getPageUrl(request, JSPMAIN + "/baseMessageWeb_list");
	}

	/**
	 * 消息表全部列表
	 */
	@RequestMapping(value = "/messagelistWeb")
	@ResponseBody
	public Object messagelist(@PathVariable String style,
			@ModelAttribute("baseMessageSearch") BaseMessageSearch baseMessageSearch, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessageSearch = (BaseMessageSearch) JsonUtil.getJsonToBean(jsonStr, BaseMessageSearch.class);
			if (baseMessageSearch == null) {
				//return new ResponseEnvelope<BaseMessage>(false, "传参异常!", "none");
			}
		}
		if (baseMessageSearch == null){
			baseMessageSearch=new BaseMessageSearch();
		}
		List<BaseMessage> list = new ArrayList<BaseMessage>();
		int total = 0;
		try {
			if(Utils.enableRedisCache()){
				total = BaseMessageCacher.getCount(baseMessageSearch);
			}else{
				total = baseMessageService.getCount(baseMessageSearch);
			}
			
			logger.info("total:" + total);

			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = BaseMessageCacher.getList(baseMessageSearch);
				}else{
					list = baseMessageService.getList(baseMessageSearch);
				}
				
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String baseMessageJsonList = JsonUtil.getListToJsonData(list);
				List<BaseMessageSmall> smallList = new JsonDataServiceImpl<BaseMessageSmall>()
						.getJsonToBeanList(baseMessageJsonList, BaseMessageSmall.class);
				return new ResponseEnvelope<BaseMessageSmall>(total, smallList, baseMessageSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessageSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseMessage>(total, list, baseMessageSearch.getMsgId());
	}

	/**
	 * 消息表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@ModelAttribute("baseMessageSearch") BaseMessageSearch baseMessageSearch, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseMessageSearch = (BaseMessageSearch) JsonUtil.getJsonToBean(jsonStr, BaseMessageSearch.class);
			if (baseMessageSearch == null) {
				return new ResponseEnvelope<BaseMessage>(false, "传参异常!", "none");
			}
		}

		List<BaseMessage> list = new ArrayList<BaseMessage>();
		int total = 0;
		try {
			total = baseMessageService.getCount(baseMessageSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = baseMessageService.getList(baseMessageSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String baseMessageJsonList = JsonUtil.getListToJsonData(list);
				List<BaseMessageSmall> smallList = new JsonDataServiceImpl<BaseMessageSmall>()
						.getJsonToBeanList(baseMessageJsonList, BaseMessageSmall.class);
				return new ResponseEnvelope<BaseMessageSmall>(total, smallList, baseMessageSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessageSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseMessage>(total, list, baseMessageSearch.getMsgId());
	}

	/**
	 * 私信收信列表
	 */
	@RequestMapping(value = "/receiverlist")
	@ResponseBody
	public Object receiverlist(@ModelAttribute("baseMessageSearch") BaseMessageSearch baseMessageSearch,
			HttpServletRequest request, HttpServletResponse response) {

		List<BaseMessage> list = new ArrayList<BaseMessage>();
		List<BaseMessageRecieve> recievelist = new ArrayList<BaseMessageRecieve>();
		List<UserMessageDesignPlan> receiverList = new ArrayList<UserMessageDesignPlan>();
		List<UserMessageDesignPlan> userMessageDesignPlanList = new ArrayList<UserMessageDesignPlan>();
		BaseMessageRecieveSearch baseMessageRecieveSearch = new BaseMessageRecieveSearch();
		UserMessageDesignPlan userMessageDesignPlan = new UserMessageDesignPlan();
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			baseMessageSearch.setUserId(loginUser.getId());
			// userMessageDesignPlan.setUserId(loginUser.getId());
			baseMessageRecieveSearch.setUserId(loginUser.getId());
		}
		int total = 0;
		try {
			BaseMessageRecieve baseMessageRecieve = new BaseMessageRecieve();
			BaseMessage baseMessage = new BaseMessage();
			total = baseMessageRecieveService.getCount(baseMessageRecieveSearch);
			// logger.info("total:" + total);
			// 分页
			// recievelist =
			// baseMessageRecieveService.getPaginatedList(baseMessageRecieveSearch);
			// 不分页
			recievelist = baseMessageRecieveService.getList(baseMessageRecieveSearch);

			for (int i = 0; i < recievelist.size(); i++) {
				userMessageDesignPlan.setMessageId(recievelist.get(i).getMessageId());
				baseMessage = baseMessageService.get(recievelist.get(i).getMessageId());
				userMessageDesignPlan.setMessageType(1);
				userMessageDesignPlan.setBusinessTypeId(3);
				userMessageDesignPlan.setUserId(baseMessage.getUserId());
				// 查询站内消息列表
				userMessageDesignPlanList = baseMessageService.getWebLettersList(userMessageDesignPlan);
				for (UserMessageDesignPlan userMessageDesignPlan2 : userMessageDesignPlanList) {
					receiverList.add(userMessageDesignPlan2);
					for (int j = 0; j < receiverList.size(); j++) {
						baseMessageRecieve.setId(receiverList.get(j).getId());
						baseMessageRecieve.setIsReaded(1);
						int id = baseMessageRecieveService.update(baseMessageRecieve);
						logger.info("update:id=" + id);
					}
				}
			}

			/*
			 * if (total > 0) { list =
			 * baseMessageService.getPaginatedList(baseMessageSearch); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!", baseMessageSearch.getMsgId());
		}

		// ResponseEnvelope<BaseMessage> res = new
		// ResponseEnvelope<BaseMessage>(total, list);
		// request.setAttribute("list", list);
		// request.setAttribute("res", res);
		// request.setAttribute("search", baseMessageSearch);
		//
		// return Utils.getPageUrl(request, JSPMAIN + "/baseMessage_list");
		return new ResponseEnvelope<UserMessageDesignPlan>(total, receiverList, baseMessageSearch.getMsgId());
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(BaseMessage model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

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
	 * 自动存储系统字段
	 */
	private void sysSave(BaseMessageRecieve model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

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
	 * 私信首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/letterHome")
	public Object letterHome(HttpServletRequest request) {
		// 获取当前登录用户的关注列表
		request.setAttribute("attentionList", attentionList(request));
		// 获取未读消息总数
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		SysMessageRecordSearch messageRecordSearch = new SysMessageRecordSearch();
		messageRecordSearch.setTargetUser(loginUser.getId());
		messageRecordSearch.setIsRead(1);
		int count = sysMessageRecordService.getCount(messageRecordSearch);
		request.setAttribute("unReadMessageCount", count);
		// //获取最近联系人列表
		// LoginUser loginUser = new LoginUser();
		// if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
		// loginUser.setId(-1);
		// loginUser.setLoginName("nologin");
		// }else{
		// loginUser =
		// com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		// }
		// SysMessageRecordSearch messageRecordSearch = new
		// SysMessageRecordSearch();
		// messageRecordSearch.setFromUser(loginUser.getId());
		// List<SysMessageRecord> sysMessageRecords =
		// sysMessageRecordService.selectRecentContacts(messageRecordSearch);
		// //获取消息数量
		// for( SysMessageRecord sysMessageRecord : sysMessageRecords ){
		// messageRecordSearch = new SysMessageRecordSearch();
		// messageRecordSearch.setTargetUser(loginUser.getId());
		// messageRecordSearch.setFromUser(sysMessageRecord.getUserId());
		// messageRecordSearch.setIsRead(1);
		// int count = sysMessageRecordService.getCount(messageRecordSearch);
		// sysMessageRecord.setMessageCount(count);
		// if(
		// org.apache.commons.lang.StringUtils.isBlank(sysMessageRecord.getPicPath())
		// ){
		// String picPath = "";
		// if( sysMessageRecord.getSex() == 1 ){//男
		// picPath = request.getContextPath() +
		// "/pages/online/images/user/manIcon.jpg";
		// }else if( sysMessageRecord.getSex() == 2 ){//女
		// picPath = request.getContextPath() +
		// "/pages/online/images/user/womenIcon.jpg";
		// }
		// sysMessageRecord.setPicPath(picPath);
		// }
		// }
		// request.setAttribute("recentContacts",sysMessageRecords);
		return JSPMAIN + "/letterHome";
	}

	// 查询用户关注的列表
	public List<SysUserFans> attentionList(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser = sysUserService.get(loginUser.getId());
		}

		SysUserFansSearch userFansSearch = new SysUserFansSearch();
		userFansSearch.setFansUserId(sysUser.getId());
		List<SysUserFans> attentionList = sysUserFansService.getPaginatedList(userFansSearch);
		if (CustomerListUtils.isNotEmpty(attentionList)) {
			for (SysUserFans userFans : attentionList) {
				SysUser user = sysUserService.get(userFans.getUserId());
				if (user != null) {
					userFans.setNickName(user == null ? "" : user.getUserName());
					ResPic resPic = null;
					if (user.getPicId() != null && user.getPicId() != 0) {
						resPic = resPicService.get(Integer.valueOf(user.getPicId()));
					}
					String picPath = "";
					if (resPic != null) {
						picPath = Utils.getValue("app.resources.url", "http://localhost:89") + resPic.getPicPath();
					} else {
						if (user.getSex() == null || user.getSex() == 1) {// 男
							picPath = request.getContextPath() + "/pages/online/images/user/manIcon.jpg";
						} else if (user.getSex() == 2) {// 女
							picPath = request.getContextPath() + "/pages/online/images/user/womenIcon.jpg";
						}
					}
					userFans.setPic(picPath);
				}
			}
		}
		return attentionList;
	}
	
	/**
	 * 消息列表
	 * @param baseMessageSearch
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@ModelAttribute("baseMessageSearch") BaseMessageSearch baseMessageSearch
				,HttpServletRequest request) {
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (null == loginUser) {
			 return new ResponseEnvelope<BaseMessage>(false, "请登录!",baseMessageSearch.getMsgId());
		 } else {
		    baseMessageSearch.setUserId(loginUser.getId());
		 }
 		//每页不同页码时使用
		if (baseMessageSearch.getLimit() == null) {
			baseMessageSearch.setLimit(new Integer(20));
		}

		List<BaseMessage> list = new ArrayList<BaseMessage> ();
		int total = 0;
		try {
			total = baseMessageService.getCountAllMessage(baseMessageSearch);
            logger.info("total:" + total);
			if (total > 0) {
				list = baseMessageService.getAllMessage(baseMessageSearch);
			}
			// 获取高清渲染图的路径
			ResRenderPic renderPic = null;
			for (BaseMessage message : list) {
				// 如果是高清渲染消息
				if (message.getNuma1() != null && message.getNuma1() > 0) {
					renderPic = resRenderPicService.get(message.getNuma1());
				}
				if (message.getBusinessTypeId() == RenderTypeCode.COMMON_720_LEVEL) {
					if (renderPic != null && renderPic.getRenderingType() != null) {
						//720度不显示原图，直接显示截图
						if (RenderTypeCode.COMMON_720_LEVEL == renderPic.getRenderingType()
								|| RenderTypeCode.ROAM_720_LEVEL == renderPic.getRenderingType()) {
							if (renderPic.getSysTaskPicId() != null) {
								ResRenderPic rp = resRenderPicService.get(renderPic.getSysTaskPicId());
								if (rp == null) {
									//如果截图信息为空就显示原图
									message.setFilePath(renderPic.getPicPath());
								}else{
									if(rp.getPicPath() != null){
										message.setFilePath(rp.getPicPath());
									}
								}
							}else{
								logger.error("获取用户的消息列表接口："+"720度图片中没有存储截图id！老数据没有正常现象！");
							}
						} else {
							message.setFilePath(renderPic.getPicPath());
						}
					}
				} else {
					if (null != renderPic && !StringUtils.isEmpty(renderPic.getPicPath())) {
						message.setFilePath(renderPic.getPicPath());
					}
				}
				// 读取信息后，去掉未读标记.不直接update所有数据是因为可能会有多页，所以只把当前查看页的消息置为已读。
				if (null != message.getRecieveId() && (null == message.getIsReaded() || 0 == message.getIsReaded())) {
					BaseMessageRecieve baseMessageRecieve = new BaseMessageRecieve();
					baseMessageRecieve.setId(message.getRecieveId());
					baseMessageRecieve.setIsReaded(1);
					baseMessageRecieveService.update(baseMessageRecieve);
				}
			}
		} catch (Exception e) {
			logger.error("获取消息列表异常,exception：" + e);
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!",baseMessageSearch.getMsgId());
		}
		return new ResponseEnvelope<>(total, list, baseMessageSearch.getMsgId());
	}
	
	/**
	 * 未读消息总数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/unReadCount")
	@ResponseBody
	public Object unReadCount(String msgId,HttpServletRequest request, HttpServletResponse response) {
		if( StringUtils.isEmpty(msgId) ){
			return new ResponseEnvelope<BaseMessage>(false, "参数msgId为空!",msgId);
		}
//		BaseMessage baseMessage = new BaseMessage();
		BaseMessageSearch baseMessage = new BaseMessageSearch();
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null ){
			loginUser.setLoginName("nologin");
		}else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		    baseMessage.setUserId(loginUser.getId());
		}
		int total = 0;
		try {
			total = baseMessageService.getCountUnreaded(baseMessage);
//			total = baseMessageService.getUnReadMessageCount(baseMessage);
            logger.info("total:" + total);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessage>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<BaseMessage>(total, null, msgId);
	}
	
	
}
