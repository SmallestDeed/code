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
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.model.small.ResRenderPicSmall;
import com.nork.system.service.ResRenderPicService;

/**   
 * @Title: ResRenderPicController.java 
 * @Package com.nork.system.controller
 * @Description:渲染图片资源库-渲染图片资源库Controller
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/resRenderPic")
public class ResRenderPicController {
	private static Logger logger = Logger
			.getLogger(ResRenderPicController.class);
	private final JsonDataServiceImpl<ResRenderPic> JsonUtil = new JsonDataServiceImpl<ResRenderPic>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/resRenderPic";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/resRenderPic";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/resRenderPic";
	
	@Autowired
	private ResRenderPicService resRenderPicService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    渲染图片资源库 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    渲染图片资源库 主页面
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
	 * 保存 渲染图片资源库
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("resRenderPic") ResRenderPic resRenderPic
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   resRenderPic = (ResRenderPic)JsonUtil.getJsonToBean(jsonStr, ResRenderPic.class);
		   if(resRenderPic == null){
			  return new ResponseEnvelope<ResRenderPic>(false, "传参异常!","none");
		   }
		}
		
	  LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser.getId()<0) {
            return new ResponseEnvelope<ResRenderPic>(false,SystemCommonConstant.PLEASE_LOGIN);
        }
		try {
		    sysSave(resRenderPic,request);
			if (resRenderPic.getId() == null) {
				int id = resRenderPicService.add(resRenderPic);
				logger.info("add:id=" + id);
				resRenderPic.setId(id);
			} else {
				int id = resRenderPicService.update(resRenderPic);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String resRenderPicJson = JsonUtil.getBeanToJsonData(resRenderPic);
				 ResRenderPicSmall resRenderPicSmall= new JsonDataServiceImpl<ResRenderPicSmall>().getJsonToBean(resRenderPicJson, ResRenderPicSmall.class);
				 
				 return new ResponseEnvelope<ResRenderPicSmall>(resRenderPicSmall,resRenderPic.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "数据异常!",resRenderPic.getMsgId());
		}
		return new ResponseEnvelope<ResRenderPic>(resRenderPic,resRenderPic.getMsgId(),true);
	}
	
	/**
	 *获取 渲染图片资源库详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("resRenderPic") ResRenderPic resRenderPic
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderPic = (ResRenderPic)JsonUtil.getJsonToBean(jsonStr, ResRenderPic.class);
			if(resRenderPic == null){
			   return new ResponseEnvelope<ResRenderPic>(false,"none", "传参异常!");
			}
			id =  resRenderPic.getId();
			msgId = resRenderPic.getMsgId() ;
		}else{
		    id =  resRenderPic.getId();
		    msgId = resRenderPic.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<ResRenderPic>(false, "参数缺少id!",msgId);
		}
		
		try {
			resRenderPic = resRenderPicService.get(id);
			
			if("small".equals(style) && resRenderPic != null ){
				 String resRenderPicJson = JsonUtil.getBeanToJsonData(resRenderPic);
				 ResRenderPicSmall resRenderPicSmall= new JsonDataServiceImpl<ResRenderPicSmall>().getJsonToBean(resRenderPicJson, ResRenderPicSmall.class);
				 
				 return new ResponseEnvelope<ResRenderPicSmall>(resRenderPicSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<ResRenderPic>(resRenderPic,msgId,true);
	}
	
   /**
	 * 删除渲染图片资源库,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("resRenderPic") ResRenderPic resRenderPic
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderPic = (ResRenderPic)JsonUtil.getJsonToBean(jsonStr, ResRenderPic.class);
			if(resRenderPic == null){
			   return new ResponseEnvelope<ResRenderPic>(false, "传参异常!","none");
			}
			ids =  resRenderPic.getIds();
			msgId = resRenderPic.getMsgId() ;
		}else{
			ids =  resRenderPic.getIds();
			msgId = resRenderPic.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<ResRenderPic>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = resRenderPicService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = resRenderPicService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<ResRenderPic>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<ResRenderPic>(true,msgId,true);
	}
	
	/**
	 * 渲染图片资源库列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("resRenderPicSearch") ResRenderPicSearch resRenderPicSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		resRenderPicSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderPicSearch = (ResRenderPicSearch)JsonUtil.getJsonToBean(jsonStr, ResRenderPicSearch.class);
			if(resRenderPicSearch == null){
			   return new ResponseEnvelope<ResRenderPic>(false, "传参异常!","none");
			}
		}
		
		List<ResRenderPic> list = new ArrayList<ResRenderPic> ();
		int total = 0;
		try {
			total = resRenderPicService.getCount(resRenderPicSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resRenderPicService.getPaginatedList(
						resRenderPicSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String resRenderPicJsonList = JsonUtil.getListToJsonData(list);
				 List<ResRenderPicSmall> smallList= new JsonDataServiceImpl<ResRenderPicSmall>().getJsonToBeanList(resRenderPicJsonList, ResRenderPicSmall.class);
				 return new ResponseEnvelope<ResRenderPicSmall>(total,smallList,resRenderPicSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "数据异常!",resRenderPicSearch.getMsgId());
		}
		return new ResponseEnvelope<ResRenderPic>(total, list,resRenderPicSearch.getMsgId());
	}
	

   /**
	 * 渲染图片资源库全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("resRenderPicSearch") ResRenderPicSearch resRenderPicSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderPicSearch = (ResRenderPicSearch)JsonUtil.getJsonToBean(jsonStr, ResRenderPicSearch.class);
			if(resRenderPicSearch == null){
			   return new ResponseEnvelope<ResRenderPic>(false, "传参异常!","none");
			}
		}
	
    	List<ResRenderPic> list = new ArrayList<ResRenderPic> ();
		int total = 0;
		try {
			total = resRenderPicService.getCount(resRenderPicSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resRenderPicService.getList(resRenderPicSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String resRenderPicJsonList = JsonUtil.getListToJsonData(list);
				 List<ResRenderPicSmall> smallList= new JsonDataServiceImpl<ResRenderPicSmall>().getJsonToBeanList(resRenderPicJsonList, ResRenderPicSmall.class);
				 return new ResponseEnvelope<ResRenderPicSmall>(total,smallList,resRenderPicSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "数据异常!",resRenderPicSearch.getMsgId());
		}
		return new ResponseEnvelope<ResRenderPic>(total, list,resRenderPicSearch.getMsgId());
	}
	
   /**
	 *获取 渲染图片资源库详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		ResRenderPic resRenderPic = null;
		try {
			resRenderPic = resRenderPicService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "数据异常!");
		}
		ResponseEnvelope<ResRenderPic> res = new ResponseEnvelope<ResRenderPic>(resRenderPic);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/resRenderPic_edit";
		}else{
			url = JSPMAIN + "/resRenderPic_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 渲染图片资源库列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("resRenderPicSearch") ResRenderPicSearch resRenderPicSearch,HttpServletRequest request, HttpServletResponse response) {

		List<ResRenderPic> list = new ArrayList<ResRenderPic> ();
		int total = 0;
		try {
			total = resRenderPicService.getCount(resRenderPicSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resRenderPicService.getPaginatedList(
						resRenderPicSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderPic>(false, "数据异常!");
		}
		
		ResponseEnvelope<ResRenderPic> res = new ResponseEnvelope<ResRenderPic>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", resRenderPicSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/resRenderPic_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResRenderPic model,HttpServletRequest request){
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
