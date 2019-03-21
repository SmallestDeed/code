package com.nork.system.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.cache.BaseMessageRecieveCacher;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageRecieveSearch;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.SysUserService;

/**   
 * @Title: BaseMessageRecieveController.java 
 * @Package com.nork.system.controller
 * @Description:系统模块-消息接收表Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:08:13
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/system/baseMessageRecieve")
public class WebBaseMessageRecieveController {
	private static Logger logger = Logger
			.getLogger(WebBaseMessageRecieveController.class);
	private final JsonDataServiceImpl<BaseMessageRecieve> JsonUtil = new JsonDataServiceImpl<BaseMessageRecieve>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/baseMessageRecieve";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/baseMessageRecieve";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/baseMessageRecieve";
	
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private BaseMessageService baseMessageService;
	@Autowired
	private SysUserService sysUserService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
   
	/**
	 * 私信列表---接口
	 */
	@RequestMapping(value = "/messagelist")
	@ResponseBody
	public Object messagelist(@PathVariable String style,
			@ModelAttribute("baseMessageRecieveSearch") BaseMessageRecieveSearch baseMessageRecieveSearch,HttpServletRequest request, HttpServletResponse response) {
		List<BaseMessageRecieve> list = new ArrayList<BaseMessageRecieve> ();
		List<UserMessageDesignPlan> messageList = new ArrayList<UserMessageDesignPlan>();
		UserMessageDesignPlan userMessageDesignPlan = new UserMessageDesignPlan();
		List<UserMessageDesignPlan> umdplist = new ArrayList<UserMessageDesignPlan>();
		userMessageDesignPlan.setMessageType(1);
		userMessageDesignPlan.setBusinessTypeId(4);
		
		int total = 0;
		try{
			logger.info("total:" + total);
			if(Utils.enableRedisCache()){
				total = BaseMessageRecieveCacher.getCount(baseMessageRecieveSearch);
			}else{
				total = baseMessageRecieveService.getCount(baseMessageRecieveSearch);
			}
			
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = BaseMessageRecieveCacher.getPageList(baseMessageRecieveSearch);
				}else{
					list = baseMessageRecieveService.getPaginatedList(baseMessageRecieveSearch);
				}
				
			}
			for (int i = 0; i < list.size(); i++) {
				//userMessageDesignPlan.setId(list.get(i).getId());
				userMessageDesignPlan.setMessageId(list.get(i).getMessageId());
				if(Utils.enableRedisCache()){
					umdplist = BaseMessageRecieveCacher.getList(userMessageDesignPlan);
				}else{
					umdplist = baseMessageRecieveService.getMessageList(userMessageDesignPlan);
				}
				
				for (UserMessageDesignPlan userMessageDesignPlan2 : umdplist) {
					messageList.add(userMessageDesignPlan2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseMessageRecieve>(false, "数据异常!",baseMessageRecieveSearch.getMsgId());
		}
		return new ResponseEnvelope<UserMessageDesignPlan>(total,messageList,baseMessageRecieveSearch.getMsgId());
	}
  
}
