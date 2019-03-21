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
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.search.ResRenderVideoSearch;
import com.nork.system.model.small.ResRenderVideoSmall;
import com.nork.system.service.ResRenderVideoService;

/**   
 * @Title: ResRenderVideoController.java 
 * @Package com.nork.system.controller
 * @Description:渲染视频资源库-渲染视频资源表Controller
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 21:03:50
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/resRenderVideo")
public class ResRenderVideoController {
	private static Logger logger = Logger
			.getLogger(ResRenderVideoController.class);
	private final JsonDataServiceImpl<ResRenderVideo> JsonUtil = new JsonDataServiceImpl<ResRenderVideo>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/resRenderVideo";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/resRenderVideo";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/resRenderVideo";
	
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    渲染视频资源表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    渲染视频资源表 主页面
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
	 * 保存 渲染视频资源表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("resRenderVideo") ResRenderVideo resRenderVideo
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   resRenderVideo = (ResRenderVideo)JsonUtil.getJsonToBean(jsonStr, ResRenderVideo.class);
		   if(resRenderVideo == null){
			  return new ResponseEnvelope<ResRenderVideo>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(resRenderVideo,request);
			if (resRenderVideo.getId() == null) {
				int id = resRenderVideoService.add(resRenderVideo);
				logger.info("add:id=" + id);
				resRenderVideo.setId(id);
			} else {
				int id = resRenderVideoService.update(resRenderVideo);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String resRenderVideoJson = JsonUtil.getBeanToJsonData(resRenderVideo);
				 ResRenderVideoSmall resRenderVideoSmall= new JsonDataServiceImpl<ResRenderVideoSmall>().getJsonToBean(resRenderVideoJson, ResRenderVideoSmall.class);
				 
				 return new ResponseEnvelope<ResRenderVideoSmall>(resRenderVideoSmall,resRenderVideo.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "数据异常!",resRenderVideo.getMsgId());
		}
		return new ResponseEnvelope<ResRenderVideo>(resRenderVideo,resRenderVideo.getMsgId(),true);
	}
	
	/**
	 *获取 渲染视频资源表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("resRenderVideo") ResRenderVideo resRenderVideo
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderVideo = (ResRenderVideo)JsonUtil.getJsonToBean(jsonStr, ResRenderVideo.class);
			if(resRenderVideo == null){
			   return new ResponseEnvelope<ResRenderVideo>(false,"none", "传参异常!");
			}
			id =  resRenderVideo.getId();
			msgId = resRenderVideo.getMsgId() ;
		}else{
		    id =  resRenderVideo.getId();
		    msgId = resRenderVideo.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<ResRenderVideo>(false, "参数缺少id!",msgId);
		}
		
		try {
			resRenderVideo = resRenderVideoService.get(id);
			
			if("small".equals(style) && resRenderVideo != null ){
				 String resRenderVideoJson = JsonUtil.getBeanToJsonData(resRenderVideo);
				 ResRenderVideoSmall resRenderVideoSmall= new JsonDataServiceImpl<ResRenderVideoSmall>().getJsonToBean(resRenderVideoJson, ResRenderVideoSmall.class);
				 
				 return new ResponseEnvelope<ResRenderVideoSmall>(resRenderVideoSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<ResRenderVideo>(resRenderVideo,msgId,true);
	}
	
   /**
	 * 删除渲染视频资源表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("resRenderVideo") ResRenderVideo resRenderVideo
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderVideo = (ResRenderVideo)JsonUtil.getJsonToBean(jsonStr, ResRenderVideo.class);
			if(resRenderVideo == null){
			   return new ResponseEnvelope<ResRenderVideo>(false, "传参异常!","none");
			}
			ids =  resRenderVideo.getIds();
			msgId = resRenderVideo.getMsgId() ;
		}else{
			ids =  resRenderVideo.getIds();
			msgId = resRenderVideo.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<ResRenderVideo>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = resRenderVideoService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = resRenderVideoService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<ResRenderVideo>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<ResRenderVideo>(true,msgId,true);
	}
	
	/**
	 * 渲染视频资源表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("resRenderVideoSearch") ResRenderVideoSearch resRenderVideoSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		resRenderVideoSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderVideoSearch = (ResRenderVideoSearch)JsonUtil.getJsonToBean(jsonStr, ResRenderVideoSearch.class);
			if(resRenderVideoSearch == null){
			   return new ResponseEnvelope<ResRenderVideo>(false, "传参异常!","none");
			}
		}
		
		List<ResRenderVideo> list = new ArrayList<ResRenderVideo> ();
		int total = 0;
		try {
			total = resRenderVideoService.getCount(resRenderVideoSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resRenderVideoService.getPaginatedList(
						resRenderVideoSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String resRenderVideoJsonList = JsonUtil.getListToJsonData(list);
				 List<ResRenderVideoSmall> smallList= new JsonDataServiceImpl<ResRenderVideoSmall>().getJsonToBeanList(resRenderVideoJsonList, ResRenderVideoSmall.class);
				 return new ResponseEnvelope<ResRenderVideoSmall>(total,smallList,resRenderVideoSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "数据异常!",resRenderVideoSearch.getMsgId());
		}
		return new ResponseEnvelope<ResRenderVideo>(total, list,resRenderVideoSearch.getMsgId());
	}
	

   /**
	 * 渲染视频资源表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("resRenderVideoSearch") ResRenderVideoSearch resRenderVideoSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			resRenderVideoSearch = (ResRenderVideoSearch)JsonUtil.getJsonToBean(jsonStr, ResRenderVideoSearch.class);
			if(resRenderVideoSearch == null){
			   return new ResponseEnvelope<ResRenderVideo>(false, "传参异常!","none");
			}
		}
	
    	List<ResRenderVideo> list = new ArrayList<ResRenderVideo> ();
		int total = 0;
		try {
			total = resRenderVideoService.getCount(resRenderVideoSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resRenderVideoService.getList(resRenderVideoSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String resRenderVideoJsonList = JsonUtil.getListToJsonData(list);
				 List<ResRenderVideoSmall> smallList= new JsonDataServiceImpl<ResRenderVideoSmall>().getJsonToBeanList(resRenderVideoJsonList, ResRenderVideoSmall.class);
				 return new ResponseEnvelope<ResRenderVideoSmall>(total,smallList,resRenderVideoSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "数据异常!",resRenderVideoSearch.getMsgId());
		}
		return new ResponseEnvelope<ResRenderVideo>(total, list,resRenderVideoSearch.getMsgId());
	}
	
   /**
	 *获取 渲染视频资源表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		ResRenderVideo resRenderVideo = null;
		try {
			resRenderVideo = resRenderVideoService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "数据异常!");
		}
		ResponseEnvelope<ResRenderVideo> res = new ResponseEnvelope<ResRenderVideo>(resRenderVideo);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/resRenderVideo_edit";
		}else{
			url = JSPMAIN + "/resRenderVideo_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 渲染视频资源表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("resRenderVideoSearch") ResRenderVideoSearch resRenderVideoSearch,HttpServletRequest request, HttpServletResponse response) {

		List<ResRenderVideo> list = new ArrayList<ResRenderVideo> ();
		int total = 0;
		try {
			total = resRenderVideoService.getCount(resRenderVideoSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = resRenderVideoService.getPaginatedList(
						resRenderVideoSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResRenderVideo>(false, "数据异常!");
		}
		
		ResponseEnvelope<ResRenderVideo> res = new ResponseEnvelope<ResRenderVideo>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", resRenderVideoSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/resRenderVideo_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResRenderVideo model,HttpServletRequest request){
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
