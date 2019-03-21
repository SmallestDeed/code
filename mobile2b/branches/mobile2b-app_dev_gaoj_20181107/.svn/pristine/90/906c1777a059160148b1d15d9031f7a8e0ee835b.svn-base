package com.nork.system.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.model.SysUserLastLoginLog;
import com.nork.system.model.search.SysUserLastLoginLogSearch;
import com.nork.system.model.small.SysUserLastLoginLogSmall;
import com.nork.system.service.SysUserLastLoginLogService;
/**   
 * @Title: SysUserLastLoginLogController.java 
 * @Package com.nork.系统模块.controller
 * @Description:system-用户最后登录时间Controller
 * @createAuthor pandajun 
 * @CreateDate 2017-07-04 10:03:13
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/系统模块/sysUserLastLoginLog")
public class SysUserLastLoginLogController {
	private static Logger logger = Logger
			.getLogger(SysUserLastLoginLogController.class);
	private final JsonDataServiceImpl<SysUserLastLoginLog> JsonUtil = new JsonDataServiceImpl<SysUserLastLoginLog>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/系统模块/sysUserLastLoginLog";
	private final String BASEMAIN = "/"+ STYLE + "/系统模块/base/sysUserLastLoginLog";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/系统模块/sysUserLastLoginLog";
	
	@Autowired
	private SysUserLastLoginLogService sysUserLastLoginLogService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    用户最后登录时间 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    用户最后登录时间 主页面
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
	 * 保存 用户最后登录时间
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("sysUserLastLoginLog") SysUserLastLoginLog sysUserLastLoginLog
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysUserLastLoginLog = (SysUserLastLoginLog)JsonUtil.getJsonToBean(jsonStr, SysUserLastLoginLog.class);
		   if(sysUserLastLoginLog == null){
			  return new ResponseEnvelope<SysUserLastLoginLog>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(sysUserLastLoginLog,request);
			if (sysUserLastLoginLog.getId() == null) {
				int id = sysUserLastLoginLogService.add(sysUserLastLoginLog);
				logger.info("add:id=" + id);
				sysUserLastLoginLog.setId(id);
			} else {
				int id = sysUserLastLoginLogService.update(sysUserLastLoginLog);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String sysUserLastLoginLogJson = JsonUtil.getBeanToJsonData(sysUserLastLoginLog);
				 SysUserLastLoginLogSmall sysUserLastLoginLogSmall= new JsonDataServiceImpl<SysUserLastLoginLogSmall>().getJsonToBean(sysUserLastLoginLogJson, SysUserLastLoginLogSmall.class);
				 
				 return new ResponseEnvelope<SysUserLastLoginLogSmall>(sysUserLastLoginLogSmall,sysUserLastLoginLog.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "数据异常!",sysUserLastLoginLog.getMsgId());
		}
		return new ResponseEnvelope<SysUserLastLoginLog>(sysUserLastLoginLog,sysUserLastLoginLog.getMsgId(),true);
	}
	
	/**
	 *获取 用户最后登录时间详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysUserLastLoginLog") SysUserLastLoginLog sysUserLastLoginLog
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserLastLoginLog = (SysUserLastLoginLog)JsonUtil.getJsonToBean(jsonStr, SysUserLastLoginLog.class);
			if(sysUserLastLoginLog == null){
			   return new ResponseEnvelope<SysUserLastLoginLog>(false,"none", "传参异常!");
			}
			id =  sysUserLastLoginLog.getId();
			msgId = sysUserLastLoginLog.getMsgId() ;
		}else{
		    id =  sysUserLastLoginLog.getId();
		    msgId = sysUserLastLoginLog.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<SysUserLastLoginLog>(false, "参数缺少id!",msgId);
		}
		
		try {
			sysUserLastLoginLog = sysUserLastLoginLogService.get(id);
			
			if("small".equals(style) && sysUserLastLoginLog != null ){
				 String sysUserLastLoginLogJson = JsonUtil.getBeanToJsonData(sysUserLastLoginLog);
				 SysUserLastLoginLogSmall sysUserLastLoginLogSmall= new JsonDataServiceImpl<SysUserLastLoginLogSmall>().getJsonToBean(sysUserLastLoginLogJson, SysUserLastLoginLogSmall.class);
				 
				 return new ResponseEnvelope<SysUserLastLoginLogSmall>(sysUserLastLoginLogSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysUserLastLoginLog>(sysUserLastLoginLog,msgId,true);
	}
	
   /**
	 * 删除用户最后登录时间,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("sysUserLastLoginLog") SysUserLastLoginLog sysUserLastLoginLog
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserLastLoginLog = (SysUserLastLoginLog)JsonUtil.getJsonToBean(jsonStr, SysUserLastLoginLog.class);
			if(sysUserLastLoginLog == null){
			   return new ResponseEnvelope<SysUserLastLoginLog>(false, "传参异常!","none");
			}
			ids =  sysUserLastLoginLog.getIds();
			msgId = sysUserLastLoginLog.getMsgId() ;
		}else{
			ids =  sysUserLastLoginLog.getIds();
			msgId = sysUserLastLoginLog.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<SysUserLastLoginLog>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = sysUserLastLoginLogService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = sysUserLastLoginLogService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<SysUserLastLoginLog>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<SysUserLastLoginLog>(true,msgId,true);
	}
	
	/**
	 * 用户最后登录时间列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysUserLastLoginLogSearch") SysUserLastLoginLogSearch sysUserLastLoginLogSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		sysUserLastLoginLogSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserLastLoginLogSearch = (SysUserLastLoginLogSearch)JsonUtil.getJsonToBean(jsonStr, SysUserLastLoginLogSearch.class);
			if(sysUserLastLoginLogSearch == null){
			   return new ResponseEnvelope<SysUserLastLoginLog>(false, "传参异常!","none");
			}
		}
		
		List<SysUserLastLoginLog> list = new ArrayList<SysUserLastLoginLog> ();
		int total = 0;
		try {
			total = sysUserLastLoginLogService.getCount(sysUserLastLoginLogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserLastLoginLogService.getPaginatedList(
						sysUserLastLoginLogSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserLastLoginLogJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserLastLoginLogSmall> smallList= new JsonDataServiceImpl<SysUserLastLoginLogSmall>().getJsonToBeanList(sysUserLastLoginLogJsonList, SysUserLastLoginLogSmall.class);
				 return new ResponseEnvelope<SysUserLastLoginLogSmall>(total,smallList,sysUserLastLoginLogSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "数据异常!",sysUserLastLoginLogSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUserLastLoginLog>(total, list,sysUserLastLoginLogSearch.getMsgId());
	}
	

   /**
	 * 用户最后登录时间全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("sysUserLastLoginLogSearch") SysUserLastLoginLogSearch sysUserLastLoginLogSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserLastLoginLogSearch = (SysUserLastLoginLogSearch)JsonUtil.getJsonToBean(jsonStr, SysUserLastLoginLogSearch.class);
			if(sysUserLastLoginLogSearch == null){
			   return new ResponseEnvelope<SysUserLastLoginLog>(false, "传参异常!","none");
			}
		}
	
    	List<SysUserLastLoginLog> list = new ArrayList<SysUserLastLoginLog> ();
		int total = 0;
		try {
			total = sysUserLastLoginLogService.getCount(sysUserLastLoginLogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserLastLoginLogService.getList(sysUserLastLoginLogSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserLastLoginLogJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserLastLoginLogSmall> smallList= new JsonDataServiceImpl<SysUserLastLoginLogSmall>().getJsonToBeanList(sysUserLastLoginLogJsonList, SysUserLastLoginLogSmall.class);
				 return new ResponseEnvelope<SysUserLastLoginLogSmall>(total,smallList,sysUserLastLoginLogSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "数据异常!",sysUserLastLoginLogSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUserLastLoginLog>(total, list,sysUserLastLoginLogSearch.getMsgId());
	}
	
   /**
	 *获取 用户最后登录时间详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		SysUserLastLoginLog sysUserLastLoginLog = null;
		try {
			sysUserLastLoginLog = sysUserLastLoginLogService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "数据异常!");
		}
		ResponseEnvelope<SysUserLastLoginLog> res = new ResponseEnvelope<SysUserLastLoginLog>(sysUserLastLoginLog);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/sysUserLastLoginLog_edit";
		}else{
			url = JSPMAIN + "/sysUserLastLoginLog_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 用户最后登录时间列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("sysUserLastLoginLogSearch") SysUserLastLoginLogSearch sysUserLastLoginLogSearch,HttpServletRequest request, HttpServletResponse response) {

		List<SysUserLastLoginLog> list = new ArrayList<SysUserLastLoginLog> ();
		int total = 0;
		try {
			total = sysUserLastLoginLogService.getCount(sysUserLastLoginLogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserLastLoginLogService.getPaginatedList(
						sysUserLastLoginLogSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserLastLoginLog>(false, "数据异常!");
		}
		
		ResponseEnvelope<SysUserLastLoginLog> res = new ResponseEnvelope<SysUserLastLoginLog>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysUserLastLoginLogSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/sysUserLastLoginLog_list");
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
}
