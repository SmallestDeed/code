package com.nork.system.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.base.service.impl.JsonDataServiceImpl;

import com.nork.system.model.SysUserSystemOperationLog;
import com.nork.system.model.search.SysUserSystemOperationLogSearch;
import com.nork.system.model.small.SysUserSystemOperationLogSmall;
import com.nork.system.service.SysUserSystemOperationLogService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: SysUserSystemOperationLogController.java 
 * @Package com.nork.system.controller
 * @Description:系统模块-用户系统操作记录Controller
 * @createAuthor pandajun 
 * @CreateDate 2017-07-05 19:43:31
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/sysUserSystemOperationLog")
public class SysUserSystemOperationLogController {
	private static Logger logger = Logger
			.getLogger(SysUserSystemOperationLogController.class);
	private final JsonDataServiceImpl<SysUserSystemOperationLog> JsonUtil = new JsonDataServiceImpl<SysUserSystemOperationLog>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/sysUserSystemOperationLog";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/sysUserSystemOperationLog";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/sysUserSystemOperationLog";
	
	@Autowired
	private SysUserSystemOperationLogService sysUserSystemOperationLogService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    用户系统操作记录 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    用户系统操作记录 主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return MAIN;
	}

    /**
	 *    访问主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN+"/list";
	}
	
	/**
	 * 保存 用户系统操作记录
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("sysUserSystemOperationLog") SysUserSystemOperationLog sysUserSystemOperationLog
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysUserSystemOperationLog = (SysUserSystemOperationLog)JsonUtil.getJsonToBean(jsonStr, SysUserSystemOperationLog.class);
		   if(sysUserSystemOperationLog == null){
			  return new ResponseEnvelope<SysUserSystemOperationLog>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(sysUserSystemOperationLog,request);
			if (sysUserSystemOperationLog.getId() == null) {
				int id = sysUserSystemOperationLogService.add(sysUserSystemOperationLog);
				logger.info("add:id=" + id);
				sysUserSystemOperationLog.setId(id);
			} else {
				int id = sysUserSystemOperationLogService.update(sysUserSystemOperationLog);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String sysUserSystemOperationLogJson = JsonUtil.getBeanToJsonData(sysUserSystemOperationLog);
				 SysUserSystemOperationLogSmall sysUserSystemOperationLogSmall= new JsonDataServiceImpl<SysUserSystemOperationLogSmall>().getJsonToBean(sysUserSystemOperationLogJson, SysUserSystemOperationLogSmall.class);
				 
				 return new ResponseEnvelope<SysUserSystemOperationLogSmall>(sysUserSystemOperationLogSmall,sysUserSystemOperationLog.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "数据异常!",sysUserSystemOperationLog.getMsgId());
		}
		return new ResponseEnvelope<SysUserSystemOperationLog>(sysUserSystemOperationLog,sysUserSystemOperationLog.getMsgId(),true);
	}
	
	/**
	 *获取 用户系统操作记录详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysUserSystemOperationLog") SysUserSystemOperationLog sysUserSystemOperationLog
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserSystemOperationLog = (SysUserSystemOperationLog)JsonUtil.getJsonToBean(jsonStr, SysUserSystemOperationLog.class);
			if(sysUserSystemOperationLog == null){
			   return new ResponseEnvelope<SysUserSystemOperationLog>(false,"none", "传参异常!");
			}
			id =  sysUserSystemOperationLog.getId();
			msgId = sysUserSystemOperationLog.getMsgId() ;
		}else{
		    id =  sysUserSystemOperationLog.getId();
		    msgId = sysUserSystemOperationLog.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<SysUserSystemOperationLog>(false, "参数缺少id!",msgId);
		}
		
		try {
			sysUserSystemOperationLog = sysUserSystemOperationLogService.get(id);
			
			if("small".equals(style) && sysUserSystemOperationLog != null ){
				 String sysUserSystemOperationLogJson = JsonUtil.getBeanToJsonData(sysUserSystemOperationLog);
				 SysUserSystemOperationLogSmall sysUserSystemOperationLogSmall= new JsonDataServiceImpl<SysUserSystemOperationLogSmall>().getJsonToBean(sysUserSystemOperationLogJson, SysUserSystemOperationLogSmall.class);
				 
				 return new ResponseEnvelope<SysUserSystemOperationLogSmall>(sysUserSystemOperationLogSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysUserSystemOperationLog>(sysUserSystemOperationLog,msgId,true);
	}
	
   /**
	 * 删除用户系统操作记录,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("sysUserSystemOperationLog") SysUserSystemOperationLog sysUserSystemOperationLog
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserSystemOperationLog = (SysUserSystemOperationLog)JsonUtil.getJsonToBean(jsonStr, SysUserSystemOperationLog.class);
			if(sysUserSystemOperationLog == null){
			   return new ResponseEnvelope<SysUserSystemOperationLog>(false, "传参异常!","none");
			}
			ids =  sysUserSystemOperationLog.getIds();
			msgId = sysUserSystemOperationLog.getMsgId() ;
		}else{
			ids =  sysUserSystemOperationLog.getIds();
			msgId = sysUserSystemOperationLog.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<SysUserSystemOperationLog>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = sysUserSystemOperationLogService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = sysUserSystemOperationLogService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<SysUserSystemOperationLog>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<SysUserSystemOperationLog>(true,msgId,true);
	}
	
	/**
	 * 用户系统操作记录列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysUserSystemOperationLogSearch") SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		sysUserSystemOperationLogSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserSystemOperationLogSearch = (SysUserSystemOperationLogSearch)JsonUtil.getJsonToBean(jsonStr, SysUserSystemOperationLogSearch.class);
			if(sysUserSystemOperationLogSearch == null){
			   return new ResponseEnvelope<SysUserSystemOperationLog>(false, "传参异常!","none");
			}
		}
		
		List<SysUserSystemOperationLog> list = new ArrayList<SysUserSystemOperationLog> ();
		int total = 0;
		try {
			total = sysUserSystemOperationLogService.getCount(sysUserSystemOperationLogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserSystemOperationLogService.getPaginatedList(
						sysUserSystemOperationLogSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserSystemOperationLogJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserSystemOperationLogSmall> smallList= new JsonDataServiceImpl<SysUserSystemOperationLogSmall>().getJsonToBeanList(sysUserSystemOperationLogJsonList, SysUserSystemOperationLogSmall.class);
				 return new ResponseEnvelope<SysUserSystemOperationLogSmall>(total,smallList,sysUserSystemOperationLogSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "数据异常!",sysUserSystemOperationLogSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUserSystemOperationLog>(total, list,sysUserSystemOperationLogSearch.getMsgId());
	}
	

   /**
	 * 用户系统操作记录全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("sysUserSystemOperationLogSearch") SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserSystemOperationLogSearch = (SysUserSystemOperationLogSearch)JsonUtil.getJsonToBean(jsonStr, SysUserSystemOperationLogSearch.class);
			if(sysUserSystemOperationLogSearch == null){
			   return new ResponseEnvelope<SysUserSystemOperationLog>(false, "传参异常!","none");
			}
		}
	
    	List<SysUserSystemOperationLog> list = new ArrayList<SysUserSystemOperationLog> ();
		int total = 0;
		try {
			total = sysUserSystemOperationLogService.getCount(sysUserSystemOperationLogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserSystemOperationLogService.getList(sysUserSystemOperationLogSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserSystemOperationLogJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserSystemOperationLogSmall> smallList= new JsonDataServiceImpl<SysUserSystemOperationLogSmall>().getJsonToBeanList(sysUserSystemOperationLogJsonList, SysUserSystemOperationLogSmall.class);
				 return new ResponseEnvelope<SysUserSystemOperationLogSmall>(total,smallList,sysUserSystemOperationLogSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "数据异常!",sysUserSystemOperationLogSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUserSystemOperationLog>(total, list,sysUserSystemOperationLogSearch.getMsgId());
	}
	
   /**
	 *获取 用户系统操作记录详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		SysUserSystemOperationLog sysUserSystemOperationLog = null;
		try {
			sysUserSystemOperationLog = sysUserSystemOperationLogService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "数据异常!");
		}
		ResponseEnvelope<SysUserSystemOperationLog> res = new ResponseEnvelope<SysUserSystemOperationLog>(sysUserSystemOperationLog);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/sysUserSystemOperationLog_edit";
		}else{
			url = JSPMAIN + "/sysUserSystemOperationLog_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 用户系统操作记录列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("sysUserSystemOperationLogSearch") SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch,HttpServletRequest request, HttpServletResponse response) {

		List<SysUserSystemOperationLog> list = new ArrayList<SysUserSystemOperationLog> ();
		int total = 0;
		try {
			total = sysUserSystemOperationLogService.getCount(sysUserSystemOperationLogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserSystemOperationLogService.getPaginatedList(
						sysUserSystemOperationLogSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserSystemOperationLog>(false, "数据异常!");
		}
		
		ResponseEnvelope<SysUserSystemOperationLog> res = new ResponseEnvelope<SysUserSystemOperationLog>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysUserSystemOperationLogSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/sysUserSystemOperationLog_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysUserSystemOperationLog model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(request.getSession()==null||request.getSession().getAttribute("loginUser")==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
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
}
