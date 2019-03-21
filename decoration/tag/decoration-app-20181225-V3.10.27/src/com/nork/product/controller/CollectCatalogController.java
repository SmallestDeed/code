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
import com.nork.product.model.CollectCatalog;
import com.nork.product.model.search.CollectCatalogSearch;
import com.nork.product.model.small.CollectCatalogSmall;
import com.nork.product.service.CollectCatalogService;

/**   
 * @Title: CollectCatalogController.java 
 * @Package com.nork.product.controller
 * @Description:产品管理-收藏目录表Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-07-01 10:46:26
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/product/collectCatalog")
public class CollectCatalogController {
	private static Logger logger = Logger
			.getLogger(CollectCatalogController.class);
	private final JsonDataServiceImpl<CollectCatalog> JsonUtil = new JsonDataServiceImpl<CollectCatalog>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/product/collectCatalog";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/collectCatalog";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/collectCatalog";
	
	@Autowired
	private CollectCatalogService collectCatalogService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    收藏目录表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    收藏目录表 主页面
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
	 * 保存 收藏目录表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("collectCatalog") CollectCatalog collectCatalog
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   collectCatalog = (CollectCatalog)JsonUtil.getJsonToBean(jsonStr, CollectCatalog.class);
		   if(collectCatalog == null){
			  return new ResponseEnvelope<CollectCatalog>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(collectCatalog,request);
			if (collectCatalog.getId() == null) {
				int id = collectCatalogService.add(collectCatalog);
				logger.info("add:id=" + id);
				collectCatalog.setId(id);
			} else {
				int id = collectCatalogService.update(collectCatalog);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String collectCatalogJson = JsonUtil.getBeanToJsonData(collectCatalog);
				 CollectCatalogSmall collectCatalogSmall= new JsonDataServiceImpl<CollectCatalogSmall>().getJsonToBean(collectCatalogJson, CollectCatalogSmall.class);
				 
				 return new ResponseEnvelope<CollectCatalogSmall>(collectCatalogSmall,collectCatalog.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "数据异常!",collectCatalog.getMsgId());
		}
		return new ResponseEnvelope<CollectCatalog>(collectCatalog,collectCatalog.getMsgId(),true);
	}
	
	/**
	 *获取 收藏目录表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("collectCatalog") CollectCatalog collectCatalog
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			collectCatalog = (CollectCatalog)JsonUtil.getJsonToBean(jsonStr, CollectCatalog.class);
			if(collectCatalog == null){
			   return new ResponseEnvelope<CollectCatalog>(false,"none", "传参异常!");
			}
			id =  collectCatalog.getId();
			msgId = collectCatalog.getMsgId() ;
		}else{
		    id =  collectCatalog.getId();
		    msgId = collectCatalog.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<CollectCatalog>(false, "参数缺少id!",msgId);
		}
		
		try {
			collectCatalog = collectCatalogService.get(id);
			
			if("small".equals(style) && collectCatalog != null ){
				 String collectCatalogJson = JsonUtil.getBeanToJsonData(collectCatalog);
				 CollectCatalogSmall collectCatalogSmall= new JsonDataServiceImpl<CollectCatalogSmall>().getJsonToBean(collectCatalogJson, CollectCatalogSmall.class);
				 
				 return new ResponseEnvelope<CollectCatalogSmall>(collectCatalogSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<CollectCatalog>(collectCatalog,msgId,true);
	}
	
   /**
	 * 删除收藏目录表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("collectCatalog") CollectCatalog collectCatalog
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			collectCatalog = (CollectCatalog)JsonUtil.getJsonToBean(jsonStr, CollectCatalog.class);
			if(collectCatalog == null){
			   return new ResponseEnvelope<CollectCatalog>(false, "传参异常!","none");
			}
			ids =  collectCatalog.getIds();
			msgId = collectCatalog.getMsgId() ;
		}else{
			ids =  collectCatalog.getIds();
			msgId = collectCatalog.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<CollectCatalog>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = collectCatalogService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = collectCatalogService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<CollectCatalog>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<CollectCatalog>(true,msgId,true);
	}
	
	/**
	 * 收藏目录表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("collectCatalogSearch") CollectCatalogSearch collectCatalogSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		collectCatalogSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			collectCatalogSearch = (CollectCatalogSearch)JsonUtil.getJsonToBean(jsonStr, CollectCatalogSearch.class);
			if(collectCatalogSearch == null){
			   return new ResponseEnvelope<CollectCatalog>(false, "传参异常!","none");
			}
		}
		
		List<CollectCatalog> list = new ArrayList<CollectCatalog> ();
		int total = 0;
		try {
			total = collectCatalogService.getCount(collectCatalogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = collectCatalogService.getPaginatedList(
						collectCatalogSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String collectCatalogJsonList = JsonUtil.getListToJsonData(list);
				 List<CollectCatalogSmall> smallList= new JsonDataServiceImpl<CollectCatalogSmall>().getJsonToBeanList(collectCatalogJsonList, CollectCatalogSmall.class);
				 return new ResponseEnvelope<CollectCatalogSmall>(total,smallList,collectCatalogSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "数据异常!",collectCatalogSearch.getMsgId());
		}
		return new ResponseEnvelope<CollectCatalog>(total, list,collectCatalogSearch.getMsgId());
	}
	

   /**
	 * 收藏目录表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("collectCatalogSearch") CollectCatalogSearch collectCatalogSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			collectCatalogSearch = (CollectCatalogSearch)JsonUtil.getJsonToBean(jsonStr, CollectCatalogSearch.class);
			if(collectCatalogSearch == null){
			   return new ResponseEnvelope<CollectCatalog>(false, "传参异常!","none");
			}
		}
	
    	List<CollectCatalog> list = new ArrayList<CollectCatalog> ();
		int total = 0;
		try {
			total = collectCatalogService.getCount(collectCatalogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = collectCatalogService.getList(collectCatalogSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String collectCatalogJsonList = JsonUtil.getListToJsonData(list);
				 List<CollectCatalogSmall> smallList= new JsonDataServiceImpl<CollectCatalogSmall>().getJsonToBeanList(collectCatalogJsonList, CollectCatalogSmall.class);
				 return new ResponseEnvelope<CollectCatalogSmall>(total,smallList,collectCatalogSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "数据异常!",collectCatalogSearch.getMsgId());
		}
		return new ResponseEnvelope<CollectCatalog>(total, list,collectCatalogSearch.getMsgId());
	}
	
   /**
	 *获取 收藏目录表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		CollectCatalog collectCatalog = null;
		try {
			collectCatalog = collectCatalogService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "数据异常!");
		}
		ResponseEnvelope<CollectCatalog> res = new ResponseEnvelope<CollectCatalog>(collectCatalog);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/collectCatalog_edit";
		}else{
			url = JSPMAIN + "/collectCatalog_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 收藏目录表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("collectCatalogSearch") CollectCatalogSearch collectCatalogSearch,HttpServletRequest request, HttpServletResponse response) {

		List<CollectCatalog> list = new ArrayList<CollectCatalog> ();
		int total = 0;
		try {
			total = collectCatalogService.getCount(collectCatalogSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = collectCatalogService.getPaginatedList(
						collectCatalogSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CollectCatalog>(false, "数据异常!");
		}
		
		ResponseEnvelope<CollectCatalog> res = new ResponseEnvelope<CollectCatalog>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", collectCatalogSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/collectCatalog_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(CollectCatalog model,HttpServletRequest request){
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
