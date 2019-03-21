package com.nork.user.controller;

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

import com.nork.user.model.SysUserRegisterInfo;
import com.nork.user.model.search.SysUserRegisterInfoSearch;
import com.nork.user.model.small.SysUserRegisterInfoSmall;
import com.nork.user.service.SysUserRegisterInfoService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: SysUserRegisterInfoController.java 
 * @Package com.nork.user.controller
 * @Description:用户注册信息-用户注册信息Controller
 * @createAuthor yanghuanzhi
 * @CreateDate 2017-08-07 16:53:22
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/user/sysUserRegisterInfo")
public class SysUserRegisterInfoController {
	private static Logger logger = Logger
			.getLogger(SysUserRegisterInfoController.class);
	private final JsonDataServiceImpl<SysUserRegisterInfo> JsonUtil = new JsonDataServiceImpl<SysUserRegisterInfo>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/user/sysUserRegisterInfo";
	private final String BASEMAIN = "/"+ STYLE + "/user/base/sysUserRegisterInfo";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/user/sysUserRegisterInfo";
	
	@Autowired
	private SysUserRegisterInfoService sysUserRegisterInfoService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    用户注册信息 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    用户注册信息 主页面
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
	 * 保存 用户注册信息
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("sysUserRegisterInfo") SysUserRegisterInfo sysUserRegisterInfo
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysUserRegisterInfo = (SysUserRegisterInfo)JsonUtil.getJsonToBean(jsonStr, SysUserRegisterInfo.class);
		   if(sysUserRegisterInfo == null){
			  return new ResponseEnvelope<SysUserRegisterInfo>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(sysUserRegisterInfo,request);
			if (sysUserRegisterInfo.getId() == null) {
				int id = sysUserRegisterInfoService.add(sysUserRegisterInfo);
				logger.info("add:id=" + id);
				sysUserRegisterInfo.setId(id);
			} else {
				int id = sysUserRegisterInfoService.update(sysUserRegisterInfo);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String sysUserRegisterInfoJson = JsonUtil.getBeanToJsonData(sysUserRegisterInfo);
				 SysUserRegisterInfoSmall sysUserRegisterInfoSmall= new JsonDataServiceImpl<SysUserRegisterInfoSmall>().getJsonToBean(sysUserRegisterInfoJson, SysUserRegisterInfoSmall.class);
				 
				 return new ResponseEnvelope<SysUserRegisterInfoSmall>(sysUserRegisterInfoSmall,sysUserRegisterInfo.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "数据异常!",sysUserRegisterInfo.getMsgId());
		}
		return new ResponseEnvelope<SysUserRegisterInfo>(sysUserRegisterInfo,sysUserRegisterInfo.getMsgId(),true);
	}
	
	/**
	 *获取 用户注册信息详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysUserRegisterInfo") SysUserRegisterInfo sysUserRegisterInfo
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserRegisterInfo = (SysUserRegisterInfo)JsonUtil.getJsonToBean(jsonStr, SysUserRegisterInfo.class);
			if(sysUserRegisterInfo == null){
			   return new ResponseEnvelope<SysUserRegisterInfo>(false,"none", "传参异常!");
			}
			id =  sysUserRegisterInfo.getId();
			msgId = sysUserRegisterInfo.getMsgId() ;
		}else{
		    id =  sysUserRegisterInfo.getId();
		    msgId = sysUserRegisterInfo.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<SysUserRegisterInfo>(false, "参数缺少id!",msgId);
		}
		
		try {
			sysUserRegisterInfo = sysUserRegisterInfoService.get(id);
			
			if("small".equals(style) && sysUserRegisterInfo != null ){
				 String sysUserRegisterInfoJson = JsonUtil.getBeanToJsonData(sysUserRegisterInfo);
				 SysUserRegisterInfoSmall sysUserRegisterInfoSmall= new JsonDataServiceImpl<SysUserRegisterInfoSmall>().getJsonToBean(sysUserRegisterInfoJson, SysUserRegisterInfoSmall.class);
				 
				 return new ResponseEnvelope<SysUserRegisterInfoSmall>(sysUserRegisterInfoSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysUserRegisterInfo>(sysUserRegisterInfo,msgId,true);
	}
	
   /**
	 * 删除用户注册信息,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("sysUserRegisterInfo") SysUserRegisterInfo sysUserRegisterInfo
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserRegisterInfo = (SysUserRegisterInfo)JsonUtil.getJsonToBean(jsonStr, SysUserRegisterInfo.class);
			if(sysUserRegisterInfo == null){
			   return new ResponseEnvelope<SysUserRegisterInfo>(false, "传参异常!","none");
			}
			ids =  sysUserRegisterInfo.getIds();
			msgId = sysUserRegisterInfo.getMsgId() ;
		}else{
			ids =  sysUserRegisterInfo.getIds();
			msgId = sysUserRegisterInfo.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<SysUserRegisterInfo>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = sysUserRegisterInfoService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = sysUserRegisterInfoService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<SysUserRegisterInfo>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<SysUserRegisterInfo>(true,msgId,true);
	}
	
	/**
	 * 用户注册信息列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysUserRegisterInfoSearch") SysUserRegisterInfoSearch sysUserRegisterInfoSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		sysUserRegisterInfoSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserRegisterInfoSearch = (SysUserRegisterInfoSearch)JsonUtil.getJsonToBean(jsonStr, SysUserRegisterInfoSearch.class);
			if(sysUserRegisterInfoSearch == null){
			   return new ResponseEnvelope<SysUserRegisterInfo>(false, "传参异常!","none");
			}
		}
		
		List<SysUserRegisterInfo> list = new ArrayList<SysUserRegisterInfo> ();
		int total = 0;
		try {
			total = sysUserRegisterInfoService.getCount(sysUserRegisterInfoSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserRegisterInfoService.getPaginatedList(
						sysUserRegisterInfoSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserRegisterInfoJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserRegisterInfoSmall> smallList= new JsonDataServiceImpl<SysUserRegisterInfoSmall>().getJsonToBeanList(sysUserRegisterInfoJsonList, SysUserRegisterInfoSmall.class);
				 return new ResponseEnvelope<SysUserRegisterInfoSmall>(total,smallList,sysUserRegisterInfoSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "数据异常!",sysUserRegisterInfoSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUserRegisterInfo>(total, list,sysUserRegisterInfoSearch.getMsgId());
	}
	

   /**
	 * 用户注册信息全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("sysUserRegisterInfoSearch") SysUserRegisterInfoSearch sysUserRegisterInfoSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysUserRegisterInfoSearch = (SysUserRegisterInfoSearch)JsonUtil.getJsonToBean(jsonStr, SysUserRegisterInfoSearch.class);
			if(sysUserRegisterInfoSearch == null){
			   return new ResponseEnvelope<SysUserRegisterInfo>(false, "传参异常!","none");
			}
		}
	
    	List<SysUserRegisterInfo> list = new ArrayList<SysUserRegisterInfo> ();
		int total = 0;
		try {
			total = sysUserRegisterInfoService.getCount(sysUserRegisterInfoSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserRegisterInfoService.getList(sysUserRegisterInfoSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysUserRegisterInfoJsonList = JsonUtil.getListToJsonData(list);
				 List<SysUserRegisterInfoSmall> smallList= new JsonDataServiceImpl<SysUserRegisterInfoSmall>().getJsonToBeanList(sysUserRegisterInfoJsonList, SysUserRegisterInfoSmall.class);
				 return new ResponseEnvelope<SysUserRegisterInfoSmall>(total,smallList,sysUserRegisterInfoSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "数据异常!",sysUserRegisterInfoSearch.getMsgId());
		}
		return new ResponseEnvelope<SysUserRegisterInfo>(total, list,sysUserRegisterInfoSearch.getMsgId());
	}
	
   /**
	 *获取 用户注册信息详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		SysUserRegisterInfo sysUserRegisterInfo = null;
		try {
			sysUserRegisterInfo = sysUserRegisterInfoService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "数据异常!");
		}
		ResponseEnvelope<SysUserRegisterInfo> res = new ResponseEnvelope<SysUserRegisterInfo>(sysUserRegisterInfo);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/sysUserRegisterInfo_edit";
		}else{
			url = JSPMAIN + "/sysUserRegisterInfo_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 用户注册信息列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("sysUserRegisterInfoSearch") SysUserRegisterInfoSearch sysUserRegisterInfoSearch,HttpServletRequest request, HttpServletResponse response) {

		List<SysUserRegisterInfo> list = new ArrayList<SysUserRegisterInfo> ();
		int total = 0;
		try {
			total = sysUserRegisterInfoService.getCount(sysUserRegisterInfoSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysUserRegisterInfoService.getPaginatedList(
						sysUserRegisterInfoSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysUserRegisterInfo>(false, "数据异常!");
		}
		
		ResponseEnvelope<SysUserRegisterInfo> res = new ResponseEnvelope<SysUserRegisterInfo>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysUserRegisterInfoSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/sysUserRegisterInfo_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysUserRegisterInfo model,HttpServletRequest request){
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
