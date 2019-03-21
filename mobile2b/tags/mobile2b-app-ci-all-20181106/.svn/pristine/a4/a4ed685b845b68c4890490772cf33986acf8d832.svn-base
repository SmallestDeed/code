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
import com.nork.system.model.SysConsumingRecords;
import com.nork.system.model.search.SysConsumingRecordsSearch;
import com.nork.system.model.small.SysConsumingRecordsSmall;
import com.nork.system.service.SysConsumingRecordsService;

/**   
 * @Title: SysConsumingRecordsController.java 
 * @Package com.nork.system.controller
 * @Description:系统模块-消费记录Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-07-18 16:49:19
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/sysConsumingRecords")
public class SysConsumingRecordsController {
	private static Logger logger = Logger
			.getLogger(SysConsumingRecordsController.class);
	private final JsonDataServiceImpl<SysConsumingRecords> JsonUtil = new JsonDataServiceImpl<SysConsumingRecords>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/sysConsumingRecords";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/sysConsumingRecords";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/system/sysConsumingRecords";
	
	@Autowired
	private SysConsumingRecordsService sysConsumingRecordsService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    消费记录 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    消费记录 主页面
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
	 * 保存 消费记录
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("sysConsumingRecords") SysConsumingRecords sysConsumingRecords
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   sysConsumingRecords = (SysConsumingRecords)JsonUtil.getJsonToBean(jsonStr, SysConsumingRecords.class);
		   if(sysConsumingRecords == null){
			  return new ResponseEnvelope<SysConsumingRecords>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(sysConsumingRecords,request);
			if (sysConsumingRecords.getId() == null) {
				int id = sysConsumingRecordsService.add(sysConsumingRecords);
				logger.info("add:id=" + id);
				sysConsumingRecords.setId(id);
			} else {
				int id = sysConsumingRecordsService.update(sysConsumingRecords);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String sysConsumingRecordsJson = JsonUtil.getBeanToJsonData(sysConsumingRecords);
				 SysConsumingRecordsSmall sysConsumingRecordsSmall= new JsonDataServiceImpl<SysConsumingRecordsSmall>().getJsonToBean(sysConsumingRecordsJson, SysConsumingRecordsSmall.class);
				 
				 return new ResponseEnvelope<SysConsumingRecordsSmall>(sysConsumingRecordsSmall,sysConsumingRecords.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "数据异常!",sysConsumingRecords.getMsgId());
		}
		return new ResponseEnvelope<SysConsumingRecords>(sysConsumingRecords,sysConsumingRecords.getMsgId(),true);
	}
	
	/**
	 *获取 消费记录详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysConsumingRecords") SysConsumingRecords sysConsumingRecords
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysConsumingRecords = (SysConsumingRecords)JsonUtil.getJsonToBean(jsonStr, SysConsumingRecords.class);
			if(sysConsumingRecords == null){
			   return new ResponseEnvelope<SysConsumingRecords>(false,"none", "传参异常!");
			}
			id =  sysConsumingRecords.getId();
			msgId = sysConsumingRecords.getMsgId() ;
		}else{
		    id =  sysConsumingRecords.getId();
		    msgId = sysConsumingRecords.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<SysConsumingRecords>(false, "参数缺少id!",msgId);
		}
		
		try {
			sysConsumingRecords = sysConsumingRecordsService.get(id);
			
			if("small".equals(style) && sysConsumingRecords != null ){
				 String sysConsumingRecordsJson = JsonUtil.getBeanToJsonData(sysConsumingRecords);
				 SysConsumingRecordsSmall sysConsumingRecordsSmall= new JsonDataServiceImpl<SysConsumingRecordsSmall>().getJsonToBean(sysConsumingRecordsJson, SysConsumingRecordsSmall.class);
				 
				 return new ResponseEnvelope<SysConsumingRecordsSmall>(sysConsumingRecordsSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysConsumingRecords>(sysConsumingRecords,msgId,true);
	}
	
   /**
	 * 删除消费记录,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("sysConsumingRecords") SysConsumingRecords sysConsumingRecords
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysConsumingRecords = (SysConsumingRecords)JsonUtil.getJsonToBean(jsonStr, SysConsumingRecords.class);
			if(sysConsumingRecords == null){
			   return new ResponseEnvelope<SysConsumingRecords>(false, "传参异常!","none");
			}
			ids =  sysConsumingRecords.getIds();
			msgId = sysConsumingRecords.getMsgId() ;
		}else{
			ids =  sysConsumingRecords.getIds();
			msgId = sysConsumingRecords.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<SysConsumingRecords>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = sysConsumingRecordsService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = sysConsumingRecordsService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<SysConsumingRecords>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<SysConsumingRecords>(true,msgId,true);
	}
	
	/**
	 * 消费记录列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysConsumingRecordsSearch") SysConsumingRecordsSearch sysConsumingRecordsSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		sysConsumingRecordsSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysConsumingRecordsSearch = (SysConsumingRecordsSearch)JsonUtil.getJsonToBean(jsonStr, SysConsumingRecordsSearch.class);
			if(sysConsumingRecordsSearch == null){
			   return new ResponseEnvelope<SysConsumingRecords>(false, "传参异常!","none");
			}
		}
		
		List<SysConsumingRecords> list = new ArrayList<SysConsumingRecords> ();
		int total = 0;
		try {
			total = sysConsumingRecordsService.getCount(sysConsumingRecordsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysConsumingRecordsService.getPaginatedList(
						sysConsumingRecordsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysConsumingRecordsJsonList = JsonUtil.getListToJsonData(list);
				 List<SysConsumingRecordsSmall> smallList= new JsonDataServiceImpl<SysConsumingRecordsSmall>().getJsonToBeanList(sysConsumingRecordsJsonList, SysConsumingRecordsSmall.class);
				 return new ResponseEnvelope<SysConsumingRecordsSmall>(total,smallList,sysConsumingRecordsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "数据异常!",sysConsumingRecordsSearch.getMsgId());
		}
		return new ResponseEnvelope<SysConsumingRecords>(total, list,sysConsumingRecordsSearch.getMsgId());
	}
	

   /**
	 * 消费记录全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("sysConsumingRecordsSearch") SysConsumingRecordsSearch sysConsumingRecordsSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysConsumingRecordsSearch = (SysConsumingRecordsSearch)JsonUtil.getJsonToBean(jsonStr, SysConsumingRecordsSearch.class);
			if(sysConsumingRecordsSearch == null){
			   return new ResponseEnvelope<SysConsumingRecords>(false, "传参异常!","none");
			}
		}
	
    	List<SysConsumingRecords> list = new ArrayList<SysConsumingRecords> ();
		int total = 0;
		try {
			total = sysConsumingRecordsService.getCount(sysConsumingRecordsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysConsumingRecordsService.getList(sysConsumingRecordsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysConsumingRecordsJsonList = JsonUtil.getListToJsonData(list);
				 List<SysConsumingRecordsSmall> smallList= new JsonDataServiceImpl<SysConsumingRecordsSmall>().getJsonToBeanList(sysConsumingRecordsJsonList, SysConsumingRecordsSmall.class);
				 return new ResponseEnvelope<SysConsumingRecordsSmall>(total,smallList,sysConsumingRecordsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "数据异常!",sysConsumingRecordsSearch.getMsgId());
		}
		return new ResponseEnvelope<SysConsumingRecords>(total, list,sysConsumingRecordsSearch.getMsgId());
	}
	
   /**
	 *获取 消费记录详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		SysConsumingRecords sysConsumingRecords = null;
		try {
			sysConsumingRecords = sysConsumingRecordsService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "数据异常!");
		}
		ResponseEnvelope<SysConsumingRecords> res = new ResponseEnvelope<SysConsumingRecords>(sysConsumingRecords);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/sysConsumingRecords_edit";
		}else{
			url = JSPMAIN + "/sysConsumingRecords_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 消费记录列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("sysConsumingRecordsSearch") SysConsumingRecordsSearch sysConsumingRecordsSearch,HttpServletRequest request, HttpServletResponse response) {

		List<SysConsumingRecords> list = new ArrayList<SysConsumingRecords> ();
		int total = 0;
		try {
			total = sysConsumingRecordsService.getCount(sysConsumingRecordsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = sysConsumingRecordsService.getPaginatedList(
						sysConsumingRecordsSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysConsumingRecords>(false, "数据异常!");
		}
		
		ResponseEnvelope<SysConsumingRecords> res = new ResponseEnvelope<SysConsumingRecords>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", sysConsumingRecordsSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/sysConsumingRecords_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(SysConsumingRecords model,HttpServletRequest request){
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
