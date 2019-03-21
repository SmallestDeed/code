package com.nork.task.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.model.small.SysTaskSmall;
import com.nork.task.service.SysTaskService;

/**   
 * @Title: SysTaskController.java 
 * @Package com.nork.task.controller
 * @Description:任务-系统任务表Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-11-18 10:51:21
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/task/sysTask")
public class SysTaskController {
	private static Logger logger = Logger
			.getLogger(SysTaskController.class);
	private final JsonDataServiceImpl<SysTask> JsonUtil = new JsonDataServiceImpl<SysTask>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/task/sysTask";
	private final String BASEMAIN = "/"+ STYLE + "/task/base/sysTask";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/task/sysTask";
	
	@Autowired
	private SysTaskService sysTaskService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    系统任务表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    系统任务表 主页面
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
	 * 保存 系统任务表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("sysTask") SysTask sysTask
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysTask = (SysTask)JsonUtil.getJsonToBean(jsonStr, SysTask.class);
		   if(sysTask == null){
			  return new ResponseEnvelope<SysTask>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(sysTask,request);
			if (sysTask.getId() == null) {
				int id = sysTaskService.add(sysTask);
				logger.info("add:id=" + id);
				sysTask.setId(id);
			} else {
				int id = sysTaskService.update(sysTask);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String sysTaskJson = JsonUtil.getBeanToJsonData(sysTask);
				 SysTaskSmall sysTaskSmall= new JsonDataServiceImpl<SysTaskSmall>().getJsonToBean(sysTaskJson, SysTaskSmall.class);
				 
				 return new ResponseEnvelope<SysTaskSmall>(sysTaskSmall,sysTask.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常!",sysTask.getMsgId());
		}
		return new ResponseEnvelope<SysTask>(sysTask,sysTask.getMsgId(),true);
	}
	
	/**
	 * 
	 * @Title: checkSysTask   
	 * @Description: 查询设计方案是否有正在进行的渲染任务   
	 * @param request
	 * @param response
	 * @param: @throws Exception      
	 * @return: Object      
	 */
	@RequestMapping(value = "/checkSysTask")
	@ResponseBody
	public Object checkSysTask(HttpServletRequest request, HttpServletResponse response){
		String businessId = request.getParameter("businessId") == null ? "" : request.getParameter("businessId");
		String msgId = request.getParameter("msgId") == null ? "" : request.getParameter("msgId");
		if(StringUtils.isBlank(businessId) || StringUtils.isEmpty(businessId)){
			return new ResponseEnvelope<SysTask>(false, "传参异常!","none");
		}
		try{
			int count = sysTaskService.getSysTaskState(Integer.valueOf(businessId));
			if(count > 0){
				return new ResponseEnvelope<SysTaskSmall>(false,"该设计方案有正在进行的渲染任务  ","none");
			}else{
				return new ResponseEnvelope<SysTaskSmall>(true,msgId,true);
			}
		}catch(Exception e){
			return new ResponseEnvelope<SysTask>(false, "数据异常!",msgId);
		}
	}
	
	/**
	 *获取 系统任务表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysTask") SysTask sysTask
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysTask = (SysTask)JsonUtil.getJsonToBean(jsonStr, SysTask.class);
			if(sysTask == null){
			   return new ResponseEnvelope<SysTask>(false,"none", "传参异常!");
			}
			id =  sysTask.getId();
			msgId = sysTask.getMsgId() ;
		}else{
		    id =  sysTask.getId();
		    msgId = sysTask.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<SysTask>(false, "参数缺少id!",msgId);
		}
		
		try {
			sysTask = sysTaskService.get(id);
			
			if("small".equals(style) && sysTask != null ){
				 String sysTaskJson = JsonUtil.getBeanToJsonData(sysTask);
				 SysTaskSmall sysTaskSmall= new JsonDataServiceImpl<SysTaskSmall>().getJsonToBean(sysTaskJson, SysTaskSmall.class);
				 
				 return new ResponseEnvelope<SysTaskSmall>(sysTaskSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysTask>(sysTask,msgId,true);
	}
	
   /**
	 * 删除系统任务表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("sysTask") SysTask sysTask
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysTask = (SysTask)JsonUtil.getJsonToBean(jsonStr, SysTask.class);
			if(sysTask == null){
			   return new ResponseEnvelope<SysTask>(false, "传参异常!","none");
			}
			ids =  sysTask.getIds();
			msgId = sysTask.getMsgId() ;
		}else{
			ids =  sysTask.getIds();
			msgId = sysTask.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<SysTask>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = sysTaskService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = sysTaskService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<SysTask>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<SysTask>(true,msgId,true);
	}
	
	/**
	 * 系统任务表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysTaskSearch") SysTaskSearch sysTaskSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		sysTaskSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysTaskSearch = (SysTaskSearch)JsonUtil.getJsonToBean(jsonStr, SysTaskSearch.class);
			if(sysTaskSearch == null){
			   return new ResponseEnvelope<SysTask>(false, "传参异常!","none");
			}
		}
		
		List<SysTask> list = new ArrayList<SysTask> ();
		int total = 0;
		try {
			total = sysTaskService.getCount(sysTaskSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysTaskService.getPaginatedList(
						sysTaskSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysTaskJsonList = JsonUtil.getListToJsonData(list);
				 List<SysTaskSmall> smallList= new JsonDataServiceImpl<SysTaskSmall>().getJsonToBeanList(sysTaskJsonList, SysTaskSmall.class);
				 return new ResponseEnvelope<SysTaskSmall>(total,smallList,sysTaskSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常!",sysTaskSearch.getMsgId());
		}
		return new ResponseEnvelope<SysTask>(total, list,sysTaskSearch.getMsgId());
	}
	

   /**
	 * 系统任务表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("sysTaskSearch") SysTaskSearch sysTaskSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysTaskSearch = (SysTaskSearch)JsonUtil.getJsonToBean(jsonStr, SysTaskSearch.class);
			if(sysTaskSearch == null){
			   return new ResponseEnvelope<SysTask>(false, "传参异常!","none");
			}
		}
	
    	List<SysTask> list = new ArrayList<SysTask> ();
		int total = 0;
		try {
			total = sysTaskService.getCount(sysTaskSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysTaskService.getList(sysTaskSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysTaskJsonList = JsonUtil.getListToJsonData(list);
				 List<SysTaskSmall> smallList= new JsonDataServiceImpl<SysTaskSmall>().getJsonToBeanList(sysTaskJsonList, SysTaskSmall.class);
				 return new ResponseEnvelope<SysTaskSmall>(total,smallList,sysTaskSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常!",sysTaskSearch.getMsgId());
		}
		return new ResponseEnvelope<SysTask>(total, list,sysTaskSearch.getMsgId());
	}
	
   /**
	 *获取 系统任务表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		SysTask sysTask = null;
		try {
			sysTask = sysTaskService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常!");
		}
		ResponseEnvelope<SysTask> res = new ResponseEnvelope<SysTask>(sysTask);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/sysTask_edit";
		}else{
			url = JSPMAIN + "/sysTask_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 系统任务表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("sysTaskSearch") SysTaskSearch sysTaskSearch,HttpServletRequest request, HttpServletResponse response) {

		List<SysTask> list = new ArrayList<SysTask> ();
		int total = 0;
		try {
			total = sysTaskService.getCount(sysTaskSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysTaskService.getPaginatedList(
						sysTaskSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常!");
		}
		
		ResponseEnvelope<SysTask> res = new ResponseEnvelope<SysTask>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysTaskSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/sysTask_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysTask model,HttpServletRequest request){
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
	 * 暂停任务
	 * @param response
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/suspendTask")
	@ResponseBody
	public Object suspendTask(HttpServletResponse response,HttpServletRequest request,Integer taskId){
		JSONObject jsonObject = new JSONObject();
		boolean flag = false;
		String message = "";
		if( taskId == null ){
			flag = false;
			message = "taskId不能为空！";
		}else{
			SysTask sysTask = sysTaskService.get(taskId);
			if( sysTask == null ){
				flag = false;
				message = "没有找到taskId为 "+taskId+" 的任务！";
			}else{
				//获取登录用户
				LoginUser loginUser = new LoginUser();
				if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				}else{
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}
				//修改任务状态；记录暂停次数；保存手动暂停日志
				sysTask.setState(SysTaskStatus.SUSPEND);//4为暂停
				sysTask.setNuma1(sysTask.getNuma1() == null ? 1 : sysTask.getNuma1() + 1);//暂停次数+1
				//记录暂停日志
				sysTask.setRemark((StringUtils.isBlank(sysTask.getRemark()) ? "" : sysTask.getRemark() + "\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":由 " + loginUser.getLoginName() + " 在 " + getIpAddr(request) + " 手动暂停任务！]");
				sysTaskService.update(sysTask);
				flag = true;
				message = "暂停成功！";
			}
		}
		jsonObject.accumulate("success",flag);
		jsonObject.accumulate("message", message);
		return jsonObject;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
