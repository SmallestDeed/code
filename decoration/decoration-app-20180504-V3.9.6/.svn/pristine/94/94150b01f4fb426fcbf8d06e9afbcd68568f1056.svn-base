package com.nork.system.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.cache.SysMessageRecordCacher;
import com.nork.system.model.SysMessageRecord;
import com.nork.system.model.search.SysMessageRecordSearch;
import com.nork.system.service.SysMessageRecordService;

/**   
 * @Title: SysMessageRecordController.java 
 * @Package com.nork.system.controller
 * @Description:聊天记录-聊天记录表Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-10-28 16:30:54
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/system/sysMessageRecord")
public class WebSysMessageRecordController {
	private static Logger logger = Logger
			.getLogger(WebSysMessageRecordController.class);
	private final JsonDataServiceImpl<SysMessageRecord> JsonUtil = new JsonDataServiceImpl<SysMessageRecord>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/sysMessageRecord";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/sysMessageRecord";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/sysMessageRecord";
	
	@Autowired
	private SysMessageRecordService sysMessageRecordService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 * 获取最近联系人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getRecentContacts")
	@ResponseBody
	public Object getRecentContacts(Integer userId,String msgId,HttpServletRequest request){
		if( userId == null ){
			return new ResponseEnvelope<SysMessageRecord>(false,"userId不能为空！",msgId);
		}
		SysMessageRecordSearch messageRecordSearch = new SysMessageRecordSearch();
		messageRecordSearch.setFromUser(userId);
		List<SysMessageRecord> sysMessageRecords=null;
		if(Utils.enableRedisCache()){
			sysMessageRecords = SysMessageRecordCacher.getRecentContacts(messageRecordSearch);
		}else{
			sysMessageRecords = sysMessageRecordService.selectRecentContacts(messageRecordSearch);
		}
		
		//获取消息数量
		for(SysMessageRecord sysMessageRecord : sysMessageRecords ){
			messageRecordSearch = new SysMessageRecordSearch();
			messageRecordSearch.setTargetUser(userId);
			messageRecordSearch.setFromUser(sysMessageRecord.getUserId());
			messageRecordSearch.setIsRead(1);
			int count=0;
			if(Utils.enableRedisCache()){
				count = SysMessageRecordCacher.getCount(messageRecordSearch);
			}else{
				count = sysMessageRecordService.getCount(messageRecordSearch);
			}
			
			sysMessageRecord.setMessageCount(count);
			String picPath = "";
			if( StringUtils.isBlank(sysMessageRecord.getPicPath()) ){

				if( sysMessageRecord.getSex() == 1 ){//男
					picPath = request.getContextPath() + "/pages/online/images/user/manIcon.jpg";
				}else if( sysMessageRecord.getSex() == 2 ){//女
					picPath = request.getContextPath() + "/pages/online/images/user/womenIcon.jpg";
				}
			}else{
				picPath = Utils.getValue("app.resources.url","http://localhost:89") + sysMessageRecord.getPicPath();
			}
			sysMessageRecord.setPicPath(picPath);
		}
		return new ResponseEnvelope<SysMessageRecord>(sysMessageRecords,msgId);
	}
 
}
