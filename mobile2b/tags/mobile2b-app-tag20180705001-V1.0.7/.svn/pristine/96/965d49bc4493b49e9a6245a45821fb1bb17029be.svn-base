package com.nork.product.controller;

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
import com.nork.product.model.GroupCollectDetails;
import com.nork.product.model.search.GroupCollectDetailsSearch;
import com.nork.product.model.small.GroupCollectDetailsSmall;
import com.nork.product.service.GroupCollectDetailsService;

/**   
 * @Title: GroupCollectDetailsController.java 
 * @Package com.nork.product.controller
 * @Description:产品模块-组合收藏明细表Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:41:47
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/product/groupCollectDetails")
public class GroupCollectDetailsController {
	private static Logger logger = Logger
			.getLogger(GroupCollectDetailsController.class);
	private final JsonDataServiceImpl<GroupCollectDetails> JsonUtil = new JsonDataServiceImpl<GroupCollectDetails>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/product/groupCollectDetails";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/groupCollectDetails";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/groupCollectDetails";
	
	@Autowired
	private GroupCollectDetailsService groupCollectDetailsService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    组合收藏明细表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    组合收藏明细表 主页面
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
	 * 保存 组合收藏明细表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("groupCollectDetails") GroupCollectDetails groupCollectDetails
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   groupCollectDetails = (GroupCollectDetails)JsonUtil.getJsonToBean(jsonStr, GroupCollectDetails.class);
		   if(groupCollectDetails == null){
			  return new ResponseEnvelope<GroupCollectDetails>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(groupCollectDetails,request);
			if (groupCollectDetails.getId() == null) {
				int id = groupCollectDetailsService.add(groupCollectDetails);
				logger.info("add:id=" + id);
				groupCollectDetails.setId(id);
			} else {
				int id = groupCollectDetailsService.update(groupCollectDetails);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String groupCollectDetailsJson = JsonUtil.getBeanToJsonData(groupCollectDetails);
				 GroupCollectDetailsSmall groupCollectDetailsSmall= new JsonDataServiceImpl<GroupCollectDetailsSmall>().getJsonToBean(groupCollectDetailsJson, GroupCollectDetailsSmall.class);
				 
				 return new ResponseEnvelope<GroupCollectDetailsSmall>(groupCollectDetailsSmall,groupCollectDetails.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "数据异常!",groupCollectDetails.getMsgId());
		}
		return new ResponseEnvelope<GroupCollectDetails>(groupCollectDetails,groupCollectDetails.getMsgId(),true);
	}
	
	/**
	 *获取 组合收藏明细表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("groupCollectDetails") GroupCollectDetails groupCollectDetails
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupCollectDetails = (GroupCollectDetails)JsonUtil.getJsonToBean(jsonStr, GroupCollectDetails.class);
			if(groupCollectDetails == null){
			   return new ResponseEnvelope<GroupCollectDetails>(false,"none", "传参异常!");
			}
			id =  groupCollectDetails.getId();
			msgId = groupCollectDetails.getMsgId() ;
		}else{
		    id =  groupCollectDetails.getId();
		    msgId = groupCollectDetails.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<GroupCollectDetails>(false, "参数缺少id!",msgId);
		}
		
		try {
			groupCollectDetails = groupCollectDetailsService.get(id);
			
			if("small".equals(style) && groupCollectDetails != null ){
				 String groupCollectDetailsJson = JsonUtil.getBeanToJsonData(groupCollectDetails);
				 GroupCollectDetailsSmall groupCollectDetailsSmall= new JsonDataServiceImpl<GroupCollectDetailsSmall>().getJsonToBean(groupCollectDetailsJson, GroupCollectDetailsSmall.class);
				 
				 return new ResponseEnvelope<GroupCollectDetailsSmall>(groupCollectDetailsSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<GroupCollectDetails>(groupCollectDetails,msgId,true);
	}
	
   /**
	 * 删除组合收藏明细表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("groupCollectDetails") GroupCollectDetails groupCollectDetails
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupCollectDetails = (GroupCollectDetails)JsonUtil.getJsonToBean(jsonStr, GroupCollectDetails.class);
			if(groupCollectDetails == null){
			   return new ResponseEnvelope<GroupCollectDetails>(false, "传参异常!","none");
			}
			ids =  groupCollectDetails.getIds();
			msgId = groupCollectDetails.getMsgId() ;
		}else{
			ids =  groupCollectDetails.getIds();
			msgId = groupCollectDetails.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<GroupCollectDetails>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = groupCollectDetailsService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = groupCollectDetailsService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<GroupCollectDetails>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<GroupCollectDetails>(true,msgId,true);
	}
	
	/**
	 * 组合收藏明细表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("groupCollectDetailsSearch") GroupCollectDetailsSearch groupCollectDetailsSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		groupCollectDetailsSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupCollectDetailsSearch = (GroupCollectDetailsSearch)JsonUtil.getJsonToBean(jsonStr, GroupCollectDetailsSearch.class);
			if(groupCollectDetailsSearch == null){
			   return new ResponseEnvelope<GroupCollectDetails>(false, "传参异常!","none");
			}
		}
		
		List<GroupCollectDetails> list = new ArrayList<GroupCollectDetails> ();
		int total = 0;
		try {
			total = groupCollectDetailsService.getCount(groupCollectDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = groupCollectDetailsService.getPaginatedList(
						groupCollectDetailsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String groupCollectDetailsJsonList = JsonUtil.getListToJsonData(list);
				 List<GroupCollectDetailsSmall> smallList= new JsonDataServiceImpl<GroupCollectDetailsSmall>().getJsonToBeanList(groupCollectDetailsJsonList, GroupCollectDetailsSmall.class);
				 return new ResponseEnvelope<GroupCollectDetailsSmall>(total,smallList,groupCollectDetailsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "数据异常!",groupCollectDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<GroupCollectDetails>(total, list,groupCollectDetailsSearch.getMsgId());
	}
	

   /**
	 * 组合收藏明细表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("groupCollectDetailsSearch") GroupCollectDetailsSearch groupCollectDetailsSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupCollectDetailsSearch = (GroupCollectDetailsSearch)JsonUtil.getJsonToBean(jsonStr, GroupCollectDetailsSearch.class);
			if(groupCollectDetailsSearch == null){
			   return new ResponseEnvelope<GroupCollectDetails>(false, "传参异常!","none");
			}
		}
	
    	List<GroupCollectDetails> list = new ArrayList<GroupCollectDetails> ();
		int total = 0;
		try {
			total = groupCollectDetailsService.getCount(groupCollectDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = groupCollectDetailsService.getList(groupCollectDetailsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String groupCollectDetailsJsonList = JsonUtil.getListToJsonData(list);
				 List<GroupCollectDetailsSmall> smallList= new JsonDataServiceImpl<GroupCollectDetailsSmall>().getJsonToBeanList(groupCollectDetailsJsonList, GroupCollectDetailsSmall.class);
				 return new ResponseEnvelope<GroupCollectDetailsSmall>(total,smallList,groupCollectDetailsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "数据异常!",groupCollectDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<GroupCollectDetails>(total, list,groupCollectDetailsSearch.getMsgId());
	}
	
   /**
	 *获取 组合收藏明细表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		GroupCollectDetails groupCollectDetails = null;
		try {
			groupCollectDetails = groupCollectDetailsService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "数据异常!");
		}
		ResponseEnvelope<GroupCollectDetails> res = new ResponseEnvelope<GroupCollectDetails>(groupCollectDetails);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/groupCollectDetails_edit";
		}else{
			url = JSPMAIN + "/groupCollectDetails_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 组合收藏明细表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("groupCollectDetailsSearch") GroupCollectDetailsSearch groupCollectDetailsSearch,HttpServletRequest request, HttpServletResponse response) {

		List<GroupCollectDetails> list = new ArrayList<GroupCollectDetails> ();
		int total = 0;
		try {
			total = groupCollectDetailsService.getCount(groupCollectDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = groupCollectDetailsService.getPaginatedList(
						groupCollectDetailsSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupCollectDetails>(false, "数据异常!");
		}
		
		ResponseEnvelope<GroupCollectDetails> res = new ResponseEnvelope<GroupCollectDetails>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", groupCollectDetailsSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/groupCollectDetails_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(GroupCollectDetails model,HttpServletRequest request){
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
