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
import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.model.small.GroupProductCollectSmall;
import com.nork.product.service.GroupProductCollectService;

/**   
 * @Title: GroupProductCollectController.java 
 * @Package com.nork.product.controller
 * @Description:产品模块-组合收藏表Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 19:46:06
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/product/groupProductCollect")
public class GroupProductCollectController {
	private static Logger logger = Logger
			.getLogger(GroupProductCollectController.class);
	private final JsonDataServiceImpl<GroupProductCollect> JsonUtil = new JsonDataServiceImpl<GroupProductCollect>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/product/groupProductCollect";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/groupProductCollect";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/groupProductCollect";
	
	@Autowired
	private GroupProductCollectService groupProductCollectService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    组合收藏表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    组合收藏表 主页面
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
	 * 保存 组合收藏表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("groupProductCollect") GroupProductCollect groupProductCollect
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   groupProductCollect = (GroupProductCollect)JsonUtil.getJsonToBean(jsonStr, GroupProductCollect.class);
		   if(groupProductCollect == null){
			  return new ResponseEnvelope<GroupProductCollect>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(groupProductCollect,request);
			if (groupProductCollect.getId() == null) {
				int id = groupProductCollectService.add(groupProductCollect);
				logger.info("add:id=" + id);
				groupProductCollect.setId(id);
			} else {
				int id = groupProductCollectService.update(groupProductCollect);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String groupProductCollectJson = JsonUtil.getBeanToJsonData(groupProductCollect);
				 GroupProductCollectSmall groupProductCollectSmall= new JsonDataServiceImpl<GroupProductCollectSmall>().getJsonToBean(groupProductCollectJson, GroupProductCollectSmall.class);
				 
				 return new ResponseEnvelope<GroupProductCollectSmall>(groupProductCollectSmall,groupProductCollect.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "数据异常!",groupProductCollect.getMsgId());
		}
		return new ResponseEnvelope<GroupProductCollect>(groupProductCollect,groupProductCollect.getMsgId(),true);
	}
	
	/**
	 *获取 组合收藏表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("groupProductCollect") GroupProductCollect groupProductCollect
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupProductCollect = (GroupProductCollect)JsonUtil.getJsonToBean(jsonStr, GroupProductCollect.class);
			if(groupProductCollect == null){
			   return new ResponseEnvelope<GroupProductCollect>(false,"none", "传参异常!");
			}
			id =  groupProductCollect.getId();
			msgId = groupProductCollect.getMsgId() ;
		}else{
		    id =  groupProductCollect.getId();
		    msgId = groupProductCollect.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<GroupProductCollect>(false, "参数缺少id!",msgId);
		}
		
		try {
			groupProductCollect = groupProductCollectService.get(id);
			
			if("small".equals(style) && groupProductCollect != null ){
				 String groupProductCollectJson = JsonUtil.getBeanToJsonData(groupProductCollect);
				 GroupProductCollectSmall groupProductCollectSmall= new JsonDataServiceImpl<GroupProductCollectSmall>().getJsonToBean(groupProductCollectJson, GroupProductCollectSmall.class);
				 
				 return new ResponseEnvelope<GroupProductCollectSmall>(groupProductCollectSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<GroupProductCollect>(groupProductCollect,msgId,true);
	}
	
   /**
	 * 删除组合收藏表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("groupProductCollect") GroupProductCollect groupProductCollect
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupProductCollect = (GroupProductCollect)JsonUtil.getJsonToBean(jsonStr, GroupProductCollect.class);
			if(groupProductCollect == null){
			   return new ResponseEnvelope<GroupProductCollect>(false, "传参异常!","none");
			}
			ids =  groupProductCollect.getIds();
			msgId = groupProductCollect.getMsgId() ;
		}else{
			ids =  groupProductCollect.getIds();
			msgId = groupProductCollect.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<GroupProductCollect>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = groupProductCollectService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = groupProductCollectService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<GroupProductCollect>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<GroupProductCollect>(true,msgId,true);
	}
	
	/**
	 * 组合收藏表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("groupProductCollectSearch") GroupProductCollectSearch groupProductCollectSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		groupProductCollectSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupProductCollectSearch = (GroupProductCollectSearch)JsonUtil.getJsonToBean(jsonStr, GroupProductCollectSearch.class);
			if(groupProductCollectSearch == null){
			   return new ResponseEnvelope<GroupProductCollect>(false, "传参异常!","none");
			}
		}
		
		List<GroupProductCollect> list = new ArrayList<GroupProductCollect> ();
		int total = 0;
		try {
			total = groupProductCollectService.getCount(groupProductCollectSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = groupProductCollectService.getPaginatedList(
						groupProductCollectSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String groupProductCollectJsonList = JsonUtil.getListToJsonData(list);
				 List<GroupProductCollectSmall> smallList= new JsonDataServiceImpl<GroupProductCollectSmall>().getJsonToBeanList(groupProductCollectJsonList, GroupProductCollectSmall.class);
				 return new ResponseEnvelope<GroupProductCollectSmall>(total,smallList,groupProductCollectSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "数据异常!",groupProductCollectSearch.getMsgId());
		}
		return new ResponseEnvelope<GroupProductCollect>(total, list,groupProductCollectSearch.getMsgId());
	}
	

   /**
	 * 组合收藏表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("groupProductCollectSearch") GroupProductCollectSearch groupProductCollectSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			groupProductCollectSearch = (GroupProductCollectSearch)JsonUtil.getJsonToBean(jsonStr, GroupProductCollectSearch.class);
			if(groupProductCollectSearch == null){
			   return new ResponseEnvelope<GroupProductCollect>(false, "传参异常!","none");
			}
		}
	
    	List<GroupProductCollect> list = new ArrayList<GroupProductCollect> ();
		int total = 0;
		try {
			total = groupProductCollectService.getCount(groupProductCollectSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = groupProductCollectService.getList(groupProductCollectSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String groupProductCollectJsonList = JsonUtil.getListToJsonData(list);
				 List<GroupProductCollectSmall> smallList= new JsonDataServiceImpl<GroupProductCollectSmall>().getJsonToBeanList(groupProductCollectJsonList, GroupProductCollectSmall.class);
				 return new ResponseEnvelope<GroupProductCollectSmall>(total,smallList,groupProductCollectSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "数据异常!",groupProductCollectSearch.getMsgId());
		}
		return new ResponseEnvelope<GroupProductCollect>(total, list,groupProductCollectSearch.getMsgId());
	}
	
   /**
	 *获取 组合收藏表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		GroupProductCollect groupProductCollect = null;
		try {
			groupProductCollect = groupProductCollectService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "数据异常!");
		}
		ResponseEnvelope<GroupProductCollect> res = new ResponseEnvelope<GroupProductCollect>(groupProductCollect);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/groupProductCollect_edit";
		}else{
			url = JSPMAIN + "/groupProductCollect_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 组合收藏表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("groupProductCollectSearch") GroupProductCollectSearch groupProductCollectSearch,HttpServletRequest request, HttpServletResponse response) {

		List<GroupProductCollect> list = new ArrayList<GroupProductCollect> ();
		int total = 0;
		try {
			total = groupProductCollectService.getCount(groupProductCollectSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = groupProductCollectService.getPaginatedList(
						groupProductCollectSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProductCollect>(false, "数据异常!");
		}
		
		ResponseEnvelope<GroupProductCollect> res = new ResponseEnvelope<GroupProductCollect>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", groupProductCollectSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/groupProductCollect_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(GroupProductCollect model,HttpServletRequest request){
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
