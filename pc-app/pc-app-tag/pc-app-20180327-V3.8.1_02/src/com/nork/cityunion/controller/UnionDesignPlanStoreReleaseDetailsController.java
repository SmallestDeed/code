package com.nork.cityunion.controller;

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

import com.nork.cityunion.model.UnionDesignPlanStoreReleaseDetails;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseDetailsSearch;
import com.nork.cityunion.model.small.UnionDesignPlanStoreReleaseDetailsSmall;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseDetailsService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionDesignPlanStoreReleaseDetailsController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟素材发布明细表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:26:23
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionDesignPlanStoreReleaseDetails")
public class UnionDesignPlanStoreReleaseDetailsController {
	private static Logger logger = Logger
			.getLogger(UnionDesignPlanStoreReleaseDetailsController.class);
	private final JsonDataServiceImpl<UnionDesignPlanStoreReleaseDetails> JsonUtil = new JsonDataServiceImpl<UnionDesignPlanStoreReleaseDetails>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionDesignPlanStoreReleaseDetails";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionDesignPlanStoreReleaseDetails";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionDesignPlanStoreReleaseDetails";
	
	@Autowired
	private UnionDesignPlanStoreReleaseDetailsService unionDesignPlanStoreReleaseDetailsService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    联盟素材发布明细表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟素材发布明细表 主页面
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
	 * 保存 联盟素材发布明细表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseDetails") UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   unionDesignPlanStoreReleaseDetails = (UnionDesignPlanStoreReleaseDetails)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseDetails.class);
		   if(unionDesignPlanStoreReleaseDetails == null){
			  return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(unionDesignPlanStoreReleaseDetails,request);
			if (unionDesignPlanStoreReleaseDetails.getId() == null) {
				int id = unionDesignPlanStoreReleaseDetailsService.add(unionDesignPlanStoreReleaseDetails);
				logger.info("add:id=" + id);
				unionDesignPlanStoreReleaseDetails.setId(id);
			} else {
				int id = unionDesignPlanStoreReleaseDetailsService.update(unionDesignPlanStoreReleaseDetails);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String unionDesignPlanStoreReleaseDetailsJson = JsonUtil.getBeanToJsonData(unionDesignPlanStoreReleaseDetails);
				 UnionDesignPlanStoreReleaseDetailsSmall unionDesignPlanStoreReleaseDetailsSmall= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseDetailsSmall>().getJsonToBean(unionDesignPlanStoreReleaseDetailsJson, UnionDesignPlanStoreReleaseDetailsSmall.class);
				 
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetailsSmall>(unionDesignPlanStoreReleaseDetailsSmall,unionDesignPlanStoreReleaseDetails.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "数据异常!",unionDesignPlanStoreReleaseDetails.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(unionDesignPlanStoreReleaseDetails,unionDesignPlanStoreReleaseDetails.getMsgId(),true);
	}
	
	/**
	 *获取 联盟素材发布明细表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseDetails") UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreReleaseDetails = (UnionDesignPlanStoreReleaseDetails)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseDetails.class);
			if(unionDesignPlanStoreReleaseDetails == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false,"none", "传参异常!");
			}
			id =  unionDesignPlanStoreReleaseDetails.getId();
			msgId = unionDesignPlanStoreReleaseDetails.getMsgId() ;
		}else{
		    id =  unionDesignPlanStoreReleaseDetails.getId();
		    msgId = unionDesignPlanStoreReleaseDetails.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionDesignPlanStoreReleaseDetails = unionDesignPlanStoreReleaseDetailsService.get(id);
			
			if("small".equals(style) && unionDesignPlanStoreReleaseDetails != null ){
				 String unionDesignPlanStoreReleaseDetailsJson = JsonUtil.getBeanToJsonData(unionDesignPlanStoreReleaseDetails);
				 UnionDesignPlanStoreReleaseDetailsSmall unionDesignPlanStoreReleaseDetailsSmall= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseDetailsSmall>().getJsonToBean(unionDesignPlanStoreReleaseDetailsJson, UnionDesignPlanStoreReleaseDetailsSmall.class);
				 
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetailsSmall>(unionDesignPlanStoreReleaseDetailsSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(unionDesignPlanStoreReleaseDetails,msgId,true);
	}
	
   /**
	 * 删除联盟素材发布明细表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseDetails") UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreReleaseDetails = (UnionDesignPlanStoreReleaseDetails)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseDetails.class);
			if(unionDesignPlanStoreReleaseDetails == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "传参异常!","none");
			}
			ids =  unionDesignPlanStoreReleaseDetails.getIds();
			msgId = unionDesignPlanStoreReleaseDetails.getMsgId() ;
		}else{
			ids =  unionDesignPlanStoreReleaseDetails.getIds();
			msgId = unionDesignPlanStoreReleaseDetails.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = unionDesignPlanStoreReleaseDetailsService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = unionDesignPlanStoreReleaseDetailsService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(true,msgId,true);
	}
	
	/**
	 * 联盟素材发布明细表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseDetailsSearch") UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionDesignPlanStoreReleaseDetailsSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreReleaseDetailsSearch = (UnionDesignPlanStoreReleaseDetailsSearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseDetailsSearch.class);
			if(unionDesignPlanStoreReleaseDetailsSearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "传参异常!","none");
			}
		}
		
		List<UnionDesignPlanStoreReleaseDetails> list = new ArrayList<UnionDesignPlanStoreReleaseDetails> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreReleaseDetailsService.getCount(unionDesignPlanStoreReleaseDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreReleaseDetailsService.getPaginatedList(
						unionDesignPlanStoreReleaseDetailsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreReleaseDetailsJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreReleaseDetailsSmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseDetailsSmall>().getJsonToBeanList(unionDesignPlanStoreReleaseDetailsJsonList, UnionDesignPlanStoreReleaseDetailsSmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetailsSmall>(total,smallList,unionDesignPlanStoreReleaseDetailsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "数据异常!",unionDesignPlanStoreReleaseDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(total, list,unionDesignPlanStoreReleaseDetailsSearch.getMsgId());
	}
	

   /**
	 * 联盟素材发布明细表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseDetailsSearch") UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreReleaseDetailsSearch = (UnionDesignPlanStoreReleaseDetailsSearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseDetailsSearch.class);
			if(unionDesignPlanStoreReleaseDetailsSearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "传参异常!","none");
			}
		}
	
    	List<UnionDesignPlanStoreReleaseDetails> list = new ArrayList<UnionDesignPlanStoreReleaseDetails> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreReleaseDetailsService.getCount(unionDesignPlanStoreReleaseDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreReleaseDetailsService.getList(unionDesignPlanStoreReleaseDetailsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreReleaseDetailsJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreReleaseDetailsSmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseDetailsSmall>().getJsonToBeanList(unionDesignPlanStoreReleaseDetailsJsonList, UnionDesignPlanStoreReleaseDetailsSmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetailsSmall>(total,smallList,unionDesignPlanStoreReleaseDetailsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "数据异常!",unionDesignPlanStoreReleaseDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(total, list,unionDesignPlanStoreReleaseDetailsSearch.getMsgId());
	}
	
   /**
	 *获取 联盟素材发布明细表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails = null;
		try {
			unionDesignPlanStoreReleaseDetails = unionDesignPlanStoreReleaseDetailsService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "数据异常!");
		}
		ResponseEnvelope<UnionDesignPlanStoreReleaseDetails> res = new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(unionDesignPlanStoreReleaseDetails);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionDesignPlanStoreReleaseDetails_edit";
		}else{
			url = JSPMAIN + "/unionDesignPlanStoreReleaseDetails_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟素材发布明细表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionDesignPlanStoreReleaseDetailsSearch") UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionDesignPlanStoreReleaseDetails> list = new ArrayList<UnionDesignPlanStoreReleaseDetails> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreReleaseDetailsService.getCount(unionDesignPlanStoreReleaseDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreReleaseDetailsService.getPaginatedList(
						unionDesignPlanStoreReleaseDetailsSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionDesignPlanStoreReleaseDetails> res = new ResponseEnvelope<UnionDesignPlanStoreReleaseDetails>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionDesignPlanStoreReleaseDetailsSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionDesignPlanStoreReleaseDetails_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionDesignPlanStoreReleaseDetails model,HttpServletRequest request){
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
