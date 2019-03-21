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
import com.nork.product.model.ProductUsageCount;
import com.nork.product.model.search.ProductUsageCountSearch;
import com.nork.product.model.small.ProductUsageCountSmall;
import com.nork.product.service.ProductUsageCountService;

/**   
 * @Title: ProductUsageCountController.java 
 * @Package com.nork.product.controller
 * @Description:产品模块-使用次数记录表Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:18:44
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/product/productUsageCount")
public class ProductUsageCountController {
	private static Logger logger = Logger
			.getLogger(ProductUsageCountController.class);
	private final JsonDataServiceImpl<ProductUsageCount> JsonUtil = new JsonDataServiceImpl<ProductUsageCount>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/product/productUsageCount";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/productUsageCount";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/productUsageCount";
	
	@Autowired
	private ProductUsageCountService productUsageCountService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    使用次数记录表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    使用次数记录表 主页面
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
	 * 保存 使用次数记录表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("productUsageCount") ProductUsageCount productUsageCount
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   productUsageCount = (ProductUsageCount)JsonUtil.getJsonToBean(jsonStr, ProductUsageCount.class);
		   if(productUsageCount == null){
			  return new ResponseEnvelope<ProductUsageCount>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(productUsageCount,request);
			if (productUsageCount.getId() == null) {
				int id = productUsageCountService.add(productUsageCount);
				logger.info("add:id=" + id);
				productUsageCount.setId(id);
			} else {
				int id = productUsageCountService.update(productUsageCount);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String productUsageCountJson = JsonUtil.getBeanToJsonData(productUsageCount);
				 ProductUsageCountSmall productUsageCountSmall= new JsonDataServiceImpl<ProductUsageCountSmall>().getJsonToBean(productUsageCountJson, ProductUsageCountSmall.class);
				 
				 return new ResponseEnvelope<ProductUsageCountSmall>(productUsageCountSmall,productUsageCount.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "数据异常!",productUsageCount.getMsgId());
		}
		return new ResponseEnvelope<ProductUsageCount>(productUsageCount,productUsageCount.getMsgId(),true);
	}
	
	/**
	 *获取 使用次数记录表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("productUsageCount") ProductUsageCount productUsageCount
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productUsageCount = (ProductUsageCount)JsonUtil.getJsonToBean(jsonStr, ProductUsageCount.class);
			if(productUsageCount == null){
			   return new ResponseEnvelope<ProductUsageCount>(false,"none", "传参异常!");
			}
			id =  productUsageCount.getId();
			msgId = productUsageCount.getMsgId() ;
		}else{
		    id =  productUsageCount.getId();
		    msgId = productUsageCount.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<ProductUsageCount>(false, "参数缺少id!",msgId);
		}
		
		try {
			productUsageCount = productUsageCountService.get(id);
			
			if("small".equals(style) && productUsageCount != null ){
				 String productUsageCountJson = JsonUtil.getBeanToJsonData(productUsageCount);
				 ProductUsageCountSmall productUsageCountSmall= new JsonDataServiceImpl<ProductUsageCountSmall>().getJsonToBean(productUsageCountJson, ProductUsageCountSmall.class);
				 
				 return new ResponseEnvelope<ProductUsageCountSmall>(productUsageCountSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<ProductUsageCount>(productUsageCount,msgId,true);
	}
	
   /**
	 * 删除使用次数记录表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("productUsageCount") ProductUsageCount productUsageCount
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productUsageCount = (ProductUsageCount)JsonUtil.getJsonToBean(jsonStr, ProductUsageCount.class);
			if(productUsageCount == null){
			   return new ResponseEnvelope<ProductUsageCount>(false, "传参异常!","none");
			}
			ids =  productUsageCount.getIds();
			msgId = productUsageCount.getMsgId() ;
		}else{
			ids =  productUsageCount.getIds();
			msgId = productUsageCount.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<ProductUsageCount>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = productUsageCountService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = productUsageCountService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<ProductUsageCount>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<ProductUsageCount>(true,msgId,true);
	}
	
	/**
	 * 使用次数记录表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("productUsageCountSearch") ProductUsageCountSearch productUsageCountSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		productUsageCountSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productUsageCountSearch = (ProductUsageCountSearch)JsonUtil.getJsonToBean(jsonStr, ProductUsageCountSearch.class);
			if(productUsageCountSearch == null){
			   return new ResponseEnvelope<ProductUsageCount>(false, "传参异常!","none");
			}
		}
		
		List<ProductUsageCount> list = new ArrayList<ProductUsageCount> ();
		int total = 0;
		try {
			total = productUsageCountService.getCount(productUsageCountSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = productUsageCountService.getPaginatedList(
						productUsageCountSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String productUsageCountJsonList = JsonUtil.getListToJsonData(list);
				 List<ProductUsageCountSmall> smallList= new JsonDataServiceImpl<ProductUsageCountSmall>().getJsonToBeanList(productUsageCountJsonList, ProductUsageCountSmall.class);
				 return new ResponseEnvelope<ProductUsageCountSmall>(total,smallList,productUsageCountSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "数据异常!",productUsageCountSearch.getMsgId());
		}
		return new ResponseEnvelope<ProductUsageCount>(total, list,productUsageCountSearch.getMsgId());
	}
	

   /**
	 * 使用次数记录表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("productUsageCountSearch") ProductUsageCountSearch productUsageCountSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productUsageCountSearch = (ProductUsageCountSearch)JsonUtil.getJsonToBean(jsonStr, ProductUsageCountSearch.class);
			if(productUsageCountSearch == null){
			   return new ResponseEnvelope<ProductUsageCount>(false, "传参异常!","none");
			}
		}
	
    	List<ProductUsageCount> list = new ArrayList<ProductUsageCount> ();
		int total = 0;
		try {
			total = productUsageCountService.getCount(productUsageCountSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = productUsageCountService.getList(productUsageCountSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String productUsageCountJsonList = JsonUtil.getListToJsonData(list);
				 List<ProductUsageCountSmall> smallList= new JsonDataServiceImpl<ProductUsageCountSmall>().getJsonToBeanList(productUsageCountJsonList, ProductUsageCountSmall.class);
				 return new ResponseEnvelope<ProductUsageCountSmall>(total,smallList,productUsageCountSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "数据异常!",productUsageCountSearch.getMsgId());
		}
		return new ResponseEnvelope<ProductUsageCount>(total, list,productUsageCountSearch.getMsgId());
	}
	
   /**
	 *获取 使用次数记录表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		ProductUsageCount productUsageCount = null;
		try {
			productUsageCount = productUsageCountService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "数据异常!");
		}
		ResponseEnvelope<ProductUsageCount> res = new ResponseEnvelope<ProductUsageCount>(productUsageCount);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/productUsageCount_edit";
		}else{
			url = JSPMAIN + "/productUsageCount_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 使用次数记录表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("productUsageCountSearch") ProductUsageCountSearch productUsageCountSearch,HttpServletRequest request, HttpServletResponse response) {

		List<ProductUsageCount> list = new ArrayList<ProductUsageCount> ();
		int total = 0;
		try {
			total = productUsageCountService.getCount(productUsageCountSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = productUsageCountService.getPaginatedList(
						productUsageCountSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductUsageCount>(false, "数据异常!");
		}
		
		ResponseEnvelope<ProductUsageCount> res = new ResponseEnvelope<ProductUsageCount>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", productUsageCountSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/productUsageCount_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ProductUsageCount model,HttpServletRequest request){
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
