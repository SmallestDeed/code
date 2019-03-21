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

import com.nork.system.model.ResVersion;
import com.nork.system.model.search.ResVersionSearch;
import com.nork.system.model.small.ResVersionSmall;
import com.nork.system.service.ResVersionService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: ResVersionController.java 
 * @Package com.nork.system.controller
 * @Description:系统版本-系统版本资源表Controller
 * @createAuthor pandajun 
 * @CreateDate 2017-08-17 11:41:05
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/resVersion")
public class ResVersionController {
	private static Logger logger = Logger
			.getLogger(ResVersionController.class);
	private final JsonDataServiceImpl<ResVersion> JsonUtil = new JsonDataServiceImpl<ResVersion>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/resVersion";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/resVersion";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/resVersion";
	
	@Autowired
	private ResVersionService resVersionService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    系统版本资源表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    系统版本资源表 主页面
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
	 * 保存 系统版本资源表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("resVersion") ResVersion resVersion
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   resVersion = (ResVersion)JsonUtil.getJsonToBean(jsonStr, ResVersion.class);
		   if(resVersion == null){
			  return new ResponseEnvelope<ResVersion>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(resVersion,request);
			if (resVersion.getId() == null) {
				int id = resVersionService.add(resVersion);
				logger.info("add:id=" + id);
				resVersion.setId(id);
			} else {
				int id = resVersionService.update(resVersion);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String resVersionJson = JsonUtil.getBeanToJsonData(resVersion);
				 ResVersionSmall resVersionSmall= new JsonDataServiceImpl<ResVersionSmall>().getJsonToBean(resVersionJson, ResVersionSmall.class);
				 
				 return new ResponseEnvelope<ResVersionSmall>(resVersionSmall,resVersion.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "数据异常!",resVersion.getMsgId());
		}
		return new ResponseEnvelope<ResVersion>(resVersion,resVersion.getMsgId(),true);
	}
	
	/**
	 *获取 系统版本资源表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("resVersion") ResVersion resVersion
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resVersion = (ResVersion)JsonUtil.getJsonToBean(jsonStr, ResVersion.class);
			if(resVersion == null){
			   return new ResponseEnvelope<ResVersion>(false,"none", "传参异常!");
			}
			id =  resVersion.getId();
			msgId = resVersion.getMsgId() ;
		}else{
		    id =  resVersion.getId();
		    msgId = resVersion.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<ResVersion>(false, "参数缺少id!",msgId);
		}
		
		try {
			resVersion = resVersionService.get(id);
			
			if("small".equals(style) && resVersion != null ){
				 String resVersionJson = JsonUtil.getBeanToJsonData(resVersion);
				 ResVersionSmall resVersionSmall= new JsonDataServiceImpl<ResVersionSmall>().getJsonToBean(resVersionJson, ResVersionSmall.class);
				 
				 return new ResponseEnvelope<ResVersionSmall>(resVersionSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<ResVersion>(resVersion,msgId,true);
	}
	
   /**
	 * 删除系统版本资源表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("resVersion") ResVersion resVersion
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resVersion = (ResVersion)JsonUtil.getJsonToBean(jsonStr, ResVersion.class);
			if(resVersion == null){
			   return new ResponseEnvelope<ResVersion>(false, "传参异常!","none");
			}
			ids =  resVersion.getIds();
			msgId = resVersion.getMsgId() ;
		}else{
			ids =  resVersion.getIds();
			msgId = resVersion.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<ResVersion>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = resVersionService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = resVersionService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<ResVersion>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<ResVersion>(true,msgId,true);
	}
	
	/**
	 * 系统版本资源表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("resVersionSearch") ResVersionSearch resVersionSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		resVersionSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resVersionSearch = (ResVersionSearch)JsonUtil.getJsonToBean(jsonStr, ResVersionSearch.class);
			if(resVersionSearch == null){
			   return new ResponseEnvelope<ResVersion>(false, "传参异常!","none");
			}
		}
		
		List<ResVersion> list = new ArrayList<ResVersion> ();
		int total = 0;
		try {
			total = resVersionService.getCount(resVersionSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resVersionService.getPaginatedList(
						resVersionSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String resVersionJsonList = JsonUtil.getListToJsonData(list);
				 List<ResVersionSmall> smallList= new JsonDataServiceImpl<ResVersionSmall>().getJsonToBeanList(resVersionJsonList, ResVersionSmall.class);
				 return new ResponseEnvelope<ResVersionSmall>(total,smallList,resVersionSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "数据异常!",resVersionSearch.getMsgId());
		}
		return new ResponseEnvelope<ResVersion>(total, list,resVersionSearch.getMsgId());
	}
	

   /**
	 * 系统版本资源表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("resVersionSearch") ResVersionSearch resVersionSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resVersionSearch = (ResVersionSearch)JsonUtil.getJsonToBean(jsonStr, ResVersionSearch.class);
			if(resVersionSearch == null){
			   return new ResponseEnvelope<ResVersion>(false, "传参异常!","none");
			}
		}
	
    	List<ResVersion> list = new ArrayList<ResVersion> ();
		int total = 0;
		try {
			total = resVersionService.getCount(resVersionSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resVersionService.getList(resVersionSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String resVersionJsonList = JsonUtil.getListToJsonData(list);
				 List<ResVersionSmall> smallList= new JsonDataServiceImpl<ResVersionSmall>().getJsonToBeanList(resVersionJsonList, ResVersionSmall.class);
				 return new ResponseEnvelope<ResVersionSmall>(total,smallList,resVersionSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "数据异常!",resVersionSearch.getMsgId());
		}
		return new ResponseEnvelope<ResVersion>(total, list,resVersionSearch.getMsgId());
	}
	
   /**
	 *获取 系统版本资源表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		ResVersion resVersion = null;
		try {
			resVersion = resVersionService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "数据异常!");
		}
		ResponseEnvelope<ResVersion> res = new ResponseEnvelope<ResVersion>(resVersion);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/resVersion_edit";
		}else{
			url = JSPMAIN + "/resVersion_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 系统版本资源表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("resVersionSearch") ResVersionSearch resVersionSearch,HttpServletRequest request, HttpServletResponse response) {

		List<ResVersion> list = new ArrayList<ResVersion> ();
		int total = 0;
		try {
			total = resVersionService.getCount(resVersionSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resVersionService.getPaginatedList(
						resVersionSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResVersion>(false, "数据异常!");
		}
		
		ResponseEnvelope<ResVersion> res = new ResponseEnvelope<ResVersion>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", resVersionSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/resVersion_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResVersion model,HttpServletRequest request){
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
