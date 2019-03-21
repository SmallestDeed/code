package com.nork.designconfig.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.model.search.DesignRulesSearch;
import com.nork.designconfig.model.small.DesignRulesSmall;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: DesignRulesController.java 
 * @Package com.nork.designconfig.controller
 * @Description:设计配置-设计规则Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 19:56:47
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/designconfig/designRules")
public class DesignRulesController {
	private static Logger logger = Logger
			.getLogger(DesignRulesController.class);
	private final JsonDataServiceImpl<DesignRules> JsonUtil = new JsonDataServiceImpl<DesignRules>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/designconfig/designRules";
	private final String BASEMAIN = "/"+ STYLE + "/designconfig/base/designRules";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/designconfig/designRules";
	
	@Autowired
	private DesignRulesService designRulesService;
	

	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    设计规则 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    设计规则 主页面
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
	 * 保存 设计规则
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("designRules") DesignRules designRules
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   designRules = (DesignRules)JsonUtil.getJsonToBean(jsonStr, DesignRules.class);
		   if(designRules == null){
			  return new ResponseEnvelope<DesignRules>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(designRules,request);
			if (designRules.getId() == null) {
				int id = designRulesService.add(designRules);
				logger.info("add:id=" + id);
				designRules.setId(id);
			} else {
				int id = designRulesService.update(designRules);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String designRulesJson = JsonUtil.getBeanToJsonData(designRules);
				 DesignRulesSmall designRulesSmall= new JsonDataServiceImpl<DesignRulesSmall>().getJsonToBean(designRulesJson, DesignRulesSmall.class);
				 
				 return new ResponseEnvelope<DesignRulesSmall>(designRulesSmall,designRules.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "数据异常!",designRules.getMsgId());
		}
		return new ResponseEnvelope<DesignRules>(designRules,designRules.getMsgId(),true);
	}
	
	/**
	 *获取 设计规则详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("designRules") DesignRules designRules
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designRules = (DesignRules)JsonUtil.getJsonToBean(jsonStr, DesignRules.class);
			if(designRules == null){
			   return new ResponseEnvelope<DesignRules>(false,"none", "传参异常!");
			}
			id =  designRules.getId();
			msgId = designRules.getMsgId() ;
		}else{
		    id =  designRules.getId();
		    msgId = designRules.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<DesignRules>(false, "参数缺少id!",msgId);
		}
		
		try {
			designRules = designRulesService.get(id);
			
			if("small".equals(style) && designRules != null ){
				 String designRulesJson = JsonUtil.getBeanToJsonData(designRules);
				 DesignRulesSmall designRulesSmall= new JsonDataServiceImpl<DesignRulesSmall>().getJsonToBean(designRulesJson, DesignRulesSmall.class);
				 
				 return new ResponseEnvelope<DesignRulesSmall>(designRulesSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<DesignRules>(designRules,msgId,true);
	}
	
   /**
	 * 删除设计规则,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("designRules") DesignRules designRules
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designRules = (DesignRules)JsonUtil.getJsonToBean(jsonStr, DesignRules.class);
			if(designRules == null){
			   return new ResponseEnvelope<DesignRules>(false, "传参异常!","none");
			}
			ids =  designRules.getIds();
			msgId = designRules.getMsgId() ;
		}else{
			ids =  designRules.getIds();
			msgId = designRules.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<DesignRules>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = designRulesService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = designRulesService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<DesignRules>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<DesignRules>(true,msgId,true);
	}
	
	/**
	 * 设计规则列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("designRulesSearch") DesignRulesSearch designRulesSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		designRulesSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designRulesSearch = (DesignRulesSearch)JsonUtil.getJsonToBean(jsonStr, DesignRulesSearch.class);
			if(designRulesSearch == null){
			   return new ResponseEnvelope<DesignRules>(false, "传参异常!","none");
			}
		}
		
		List<DesignRules> list = new ArrayList<DesignRules> ();
		int total = 0;
		try {
			total = designRulesService.getCount(designRulesSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designRulesService.getPaginatedList(
						designRulesSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String designRulesJsonList = JsonUtil.getListToJsonData(list);
				 List<DesignRulesSmall> smallList= new JsonDataServiceImpl<DesignRulesSmall>().getJsonToBeanList(designRulesJsonList, DesignRulesSmall.class);
				 return new ResponseEnvelope<DesignRulesSmall>(total,smallList,designRulesSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "数据异常!",designRulesSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignRules>(total, list,designRulesSearch.getMsgId());
	}
	

   /**
	 * 设计规则全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("designRulesSearch") DesignRulesSearch designRulesSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designRulesSearch = (DesignRulesSearch)JsonUtil.getJsonToBean(jsonStr, DesignRulesSearch.class);
			if(designRulesSearch == null){
			   return new ResponseEnvelope<DesignRules>(false, "传参异常!","none");
			}
		}
	
    	List<DesignRules> list = new ArrayList<DesignRules> ();
		int total = 0;
		try {
			total = designRulesService.getCount(designRulesSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designRulesService.getList(designRulesSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String designRulesJsonList = JsonUtil.getListToJsonData(list);
				 List<DesignRulesSmall> smallList= new JsonDataServiceImpl<DesignRulesSmall>().getJsonToBeanList(designRulesJsonList, DesignRulesSmall.class);
				 return new ResponseEnvelope<DesignRulesSmall>(total,smallList,designRulesSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "数据异常!",designRulesSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignRules>(total, list,designRulesSearch.getMsgId());
	}
	
   /**
	 *获取 设计规则详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		DesignRules designRules = null;
		try {
			designRules = designRulesService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "数据异常!");
		}
		ResponseEnvelope<DesignRules> res = new ResponseEnvelope<DesignRules>(designRules);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/designRules_edit";
		}else{
			url = JSPMAIN + "/designRules_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 设计规则列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("designRulesSearch") DesignRulesSearch designRulesSearch,HttpServletRequest request, HttpServletResponse response) {

		List<DesignRules> list = new ArrayList<DesignRules> ();
		int total = 0;
		try {
			total = designRulesService.getCount(designRulesSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designRulesService.getPaginatedList(designRulesSearch);	
				for (int i = 0; i < list.size(); i++) {
					DesignRules designRule =  list.get(i);
					String rulesType = designRule.getRulesType();
					if(!"".equals(rulesType) && rulesType!=null){
//						SysDictionary sysDictionary = new SysDictionary();
//						sysDictionary.setType("rulesType");
//						sysDictionary.setValuekey(rulesType);
						SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("rulesType",rulesType);
						designRule.setRulesTypeName(dictionary.getName());
					}
					//规则标识
					String rulesSign = designRule.getRulesSign();
					if(!"".equals(rulesSign) && rulesSign!=null){
//						SysDictionary sysDictionary = new SysDictionary();
//						sysDictionary.setType("rulesSign");
//						sysDictionary.setValuekey(rulesSign);
						SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("rulesSign",rulesSign);
						designRule.setRulesSignName(dictionary.getName());
					}
					//规则级别
					String rulesLevel = designRule.getRulesLevel();
					if(!"".equals(rulesLevel) && rulesLevel!=null){
//						SysDictionary sysDictionary = new SysDictionary();
//						sysDictionary.setType("rulesLevel");
//						sysDictionary.setValuekey(rulesLevel);
						SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("rulesLevel",rulesLevel);
						designRule.setRulesLevelName(dictionary.getName());
					}
					//表达式
					String ext2 = designRule.getExt2();
					if(!"".equals(ext2) && ext2!=null){
						SysDictionary sysDictionary = new SysDictionary();
						sysDictionary.setType("rulesExpression");
						sysDictionary.setValuekey(ext2);
						SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("rulesExpression",ext2);
						designRule.setExtName(dictionary.getName());
					}
					//规则模式
					String ext1 = designRule.getExt1();
					if(!"".equals(ext1) && ext1!=null){
//						SysDictionary sysDictionary = new SysDictionary();
//						sysDictionary.setType("rulesModel");
//						sysDictionary.setValuekey(ext1);
						SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("rulesModel",ext1);
						designRule.setRulesModelName(dictionary.getName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRules>(false, "数据异常!");
		}
		
		ResponseEnvelope<DesignRules> res = new ResponseEnvelope<DesignRules>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", designRulesSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/designRules_list");
	}
	
	

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignRules model,HttpServletRequest request){
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
	
	   /**
		 * 单个产品的产品设计规则(通过选中的产品和规则标识获取标识处理串)
		 */
		@RequestMapping(value = "/productRule")
		@ResponseBody
		public Object productRule(
				     Integer productId,String bigType,String smallType  //选择产品的大类和小类，当规则为多个产品时，可以为空
		            ,Integer spaceCommonId,Integer templateId           //当前空间和样板房id
		            ,String  rulesSign       //规则标识
		            ,String  rulesModel      //规则模式，可以为空
		            ,String  msgId
					,HttpServletRequest request
					,HttpServletResponse response) throws Exception  {
			if(productId ==null ||StringUtils.isEmpty(bigType)|| StringUtils.isEmpty(smallType)||
				spaceCommonId==null|| templateId == null 
			   || StringUtils.isEmpty(rulesSign)
			   || StringUtils.isEmpty(rulesSign)
			   ||StringUtils.isEmpty(msgId)){
				return new ResponseEnvelope<DesignRules>(false, "参数异常!");
			}
			
			String rulejson="";
			DesignRules designRules = new DesignRules();
			//先判断样板房规则，有则返回，无则继续
			designRules.setRulesSign(rulesSign);
			designRules.setRulesBusiness(templateId.toString());
			designRules.setRulesType("templetRulesType");
			designRules.setAtt1(rulesModel);
			List<DesignRules> list  = designRulesService.getList(designRules);

			if(list != null && list.size()>0){
				for(DesignRules d:list){
					//如果规则中配置的是产品，判断选中的产品是否相等
					if("productLevel".equals(d.getRulesLevel())){
						if(productId.toString().equals(d.getRulesMainValue())){
							rulejson =  d.getRulesSecondaryValues();
							return new ResponseEnvelope<String>(rulejson,msgId,true);
						}
					}
					//如果规则中配置的是小类，判断选中的产品是否在包含在小类中
					if("smallLevel".equals(d.getRulesLevel())){
						if(smallType.equals(d.getRulesMainValue())){
							rulejson =  d.getRulesSecondaryValues();
							return new ResponseEnvelope<String>(rulejson,msgId,true);
						}
					}
					//如果规则中配置的是大类，判断选中的产品是否在包含在大类中
					if("bigLevel".equals(d.getRulesLevel())){
						if(bigType.equals(d.getRulesMainValue())){
							rulejson =  d.getRulesSecondaryValues();
							return new ResponseEnvelope<String>(rulejson,msgId,true);
						}
					}
				}
			}
			//再判断通用空间规则，有则返回，无则继续
			designRules.setRulesSign(rulesSign);
			designRules.setRulesBusiness(spaceCommonId.toString());
			designRules.setRulesType("spaceRulesType");
			designRules.setAtt1(rulesModel);
			list  = designRulesService.getList(designRules);
			if(list != null && list.size()>0){
				for(DesignRules d:list){
					//如果规则中配置的是产品，判断选中的产品是否相等
					if("productLevel".equals(d.getRulesLevel())){
						if(productId.toString().equals(d.getRulesMainValue())){
							rulejson =  d.getRulesSecondaryValues();
							return new ResponseEnvelope<String>(rulejson,msgId,true);
						}
					}
					//如果规则中配置的是小类，判断选中的产品是否在包含在小类中
					if("smallLevel".equals(d.getRulesLevel())){
						if(smallType.equals(d.getRulesMainValue())){
							rulejson =  d.getRulesSecondaryValues();
							return new ResponseEnvelope<String>(rulejson,msgId,true);
						}
					}
					//如果规则中配置的是大类，判断选中的产品是否在包含在大类中
					if("bigLevel".equals(d.getRulesLevel())){
						if(bigType.equals(d.getRulesMainValue())){
							rulejson =  d.getRulesSecondaryValues();
							return new ResponseEnvelope<String>(rulejson,msgId,true);
						}
					}
				}
			}
				
			  //最后判断通用规则，有则返回
		      designRules.setRulesSign(rulesSign);
		      designRules.setRulesBusiness(null);
			  designRules.setRulesType("commonRulesType");
			  designRules.setAtt1(rulesModel);
			  list  = designRulesService.getList(designRules);
			  if(list != null && list.size()>0){
					for(DesignRules d:list){
						//如果规则中配置的是产品，判断选中的产品是否相等
						if("productLevel".equals(d.getRulesLevel())){
							if(productId.toString().equals(d.getRulesMainValue())){
								rulejson =  d.getRulesSecondaryValues();
								return new ResponseEnvelope<String>(rulejson,msgId,true);
							}
						}
						//如果规则中配置的是小类，判断选中的产品是否在包含在小类中
						if("smallLevel".equals(d.getRulesLevel())){
							if(smallType.equals(d.getRulesMainValue())){
								rulejson =  d.getRulesSecondaryValues();
								return new ResponseEnvelope<String>(rulejson,msgId,true);
							}
						}
						//如果规则中配置的是大类，判断选中的产品是否在包含在大类中
						if("bigLevel".equals(d.getRulesLevel())){
							if(bigType.equals(d.getRulesMainValue())){
								rulejson =  d.getRulesSecondaryValues();
								return new ResponseEnvelope<String>(rulejson,msgId,true);
							}
						}
					}
			  }
			return new ResponseEnvelope<String>(rulejson,msgId,true); 
		}
		
		 /**
		 * 单个产品的产品设计规则(通过选中的产品和规则标识获取标识处理串)
		 */
		@RequestMapping(value = "/productsRule")
		@ResponseBody
		public Object productsRule(
					String   productIds,
		            Integer  spaceCommonId,Integer templateId    //当前空间和样板房id
		            ,String  rulesSign       //规则标识
		            ,String  rulesModel      //规则模式，可以为空
		            ,String  msgId
					,HttpServletRequest request
					,HttpServletResponse response) throws Exception  {
			if(StringUtils.isEmpty(productIds)
			   || spaceCommonId==null || templateId == null 
			   || StringUtils.isEmpty(rulesSign)
			   || StringUtils.isEmpty(rulesSign)
			   ||StringUtils.isEmpty(msgId)){
				return new ResponseEnvelope<DesignRules>(false, "参数异常!",msgId);
			}
			
			String rulejson="";
			DesignRules designRules = new DesignRules();
				//先判断样板房规则，有则返回，无则继续
				designRules.setRulesSign(rulesSign);
				designRules.setRulesBusiness(templateId.toString());
				designRules.setRulesType("templetRulesType");
				designRules.setAtt1(rulesModel);
				List<DesignRules> list  = designRulesService.getList(designRules);
				if(list != null && list.size()>0){
					for(DesignRules d:list){
						//如果规则中配置的是大类，判断选中的产品是否在包含在大类中
						if("bigLevel".equals(d.getRulesLevel())){
							String[] productIdss = productIds.split(",");
							String cats = "";
							for(String productId :productIdss){
								BaseProduct b =  baseProductService.get(new Integer(productId));
								SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(b.getProductTypeValue()));
								String cat = sysDictionary==null?"":sysDictionary.getValuekey();//获取产品的大类
								cats = cats +","+ cat;
							}
							if(StringUtils.isNotEmpty(cats) && StringUtils.isNotEmpty(d.getRulesMainObj()) && cats.equals(d.getRulesMainObj())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
						//如果规则中配置的是小类，判断选中的产品是否在包含在小类中
						if("smallLevel".equals(d.getRulesLevel())){
							String[] productIdss = productIds.split(",");
							String cats = "";
							for(String productId :productIdss){
								BaseProduct b =  baseProductService.get(new Integer(productId));
								SysDictionary sysDictionary1 = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(b.getProductTypeValue()));
								SysDictionary sysDictionary2 = sysDictionaryService.getSysDictionaryByValue(sysDictionary1.getValuekey(),new Integer(b.getProductSmallTypeValue()));
								String cat = sysDictionary2==null?"":sysDictionary2.getValuekey();//获取产品的小类
								cats = cats +","+ cat;
							}
							
							if(StringUtils.isNotEmpty(cats) && StringUtils.isNotEmpty(d.getRulesMainObj()) && cats.equals(d.getRulesMainObj())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
						//如果规则中配置的是产品，判断选中的产品是否相等
						if("productLevel".equals(d.getRulesLevel())){
							if(productIds.equals(d.getRulesMainObj())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
					}
				}
			    //再判断通用空间规则，有则返回，无则继续
				designRules.setRulesSign(rulesSign);
				designRules.setRulesBusiness(spaceCommonId.toString());
				designRules.setRulesType("spaceRulesType");
				designRules.setAtt1(rulesModel);
				list  = designRulesService.getList(designRules);
				if(list != null && list.size()>0){
					for(DesignRules d:list){
						//如果规则中配置的是大类，判断选中的产品是否在包含在大类中
						if("bigLevel".equals(d.getRulesLevel())){
							String[] productIdss = productIds.split(",");
							String cats = "";
							List<String> t1 = new ArrayList<String>();
							List<String> t2 = new ArrayList<String>();
							for(String productId :productIdss){
								BaseProduct b =  baseProductService.get(new Integer(productId));
								SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(b.getProductTypeValue()));
								String cat = sysDictionary==null?"":sysDictionary.getValuekey();//获取产品的大类
								cats = cats +","+ cat;
								t1.add(cat);
							}
							Collections.sort(t1);
							
							if(StringUtils.isNotEmpty(d.getRulesMainObj())){
								String[] objs = d.getRulesMainObj().split(",");
								for(String obj:objs){
									t2.add(obj);
								}
							}
							Collections.sort(t2);
							
							if(StringUtils.isNotEmpty(cats) && StringUtils.isNotEmpty(d.getRulesMainObj()) && t1.toString().equals(t2.toString())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
						//如果规则中配置的是小类，判断选中的产品是否在包含在小类中
						if("smallLevel".equals(d.getRulesLevel())){
							String[] productIdss = productIds.split(",");
							String cats = "";
							List<String> t1 = new ArrayList<String>();
							List<String> t2 = new ArrayList<String>();
							for(String productId :productIdss){
								BaseProduct b =  baseProductService.get(new Integer(productId));
								SysDictionary sysDictionary1 = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(b.getProductTypeValue()));
								SysDictionary sysDictionary2 = sysDictionaryService.getSysDictionaryByValue(sysDictionary1.getValuekey(),new Integer(b.getProductSmallTypeValue()));
								String cat = sysDictionary2==null?"":sysDictionary2.getValuekey();//获取产品的小类
								cats = cats +","+ cat;
								t1.add(cat);
							}
							
							Collections.sort(t1);
							
							if(StringUtils.isNotEmpty(d.getRulesMainObj())){
								String[] objs = d.getRulesMainObj().split(",");
								for(String obj:objs){
									t2.add(obj);
								}
							}
							Collections.sort(t2);
							
							if(StringUtils.isNotEmpty(cats) && StringUtils.isNotEmpty(d.getRulesMainObj()) && t1.toString().equals(t2.toString())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
						//如果规则中配置的是产品，判断选中的产品是否相等
						if("productLevel".equals(d.getRulesLevel())){
							if(productIds.equals(d.getRulesMainObj())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
					}
				}
				
			  //最后判断通用规则，有则返回
		      designRules.setRulesSign(rulesSign);
		      designRules.setRulesBusiness(null);
			  designRules.setRulesType("commonRulesType");
			  designRules.setAtt1(rulesModel);
			  list  = designRulesService.getList(designRules);
			  if(list != null && list.size()>0){
					for(DesignRules d:list){
						//如果规则中配置的是大类，判断选中的产品是否在包含在大类中
						if("bigLevel".equals(d.getRulesLevel())){
							String[] productIdss = productIds.split(",");
							String cats = "";
							List<String> t1 = new ArrayList<String>();
							List<String> t2 = new ArrayList<String>();
							for(String productId :productIdss){
								BaseProduct b =  baseProductService.get(new Integer(productId));
								SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(b.getProductTypeValue()));
								String cat = sysDictionary==null?"":sysDictionary.getValuekey();//获取产品的大类
								cats = cats +","+ cat;
								t1.add(cat);
							}
							
							Collections.sort(t1);
							
							if(StringUtils.isNotEmpty(d.getRulesMainObj())){
								String[] objs = d.getRulesMainObj().split(",");
								for(String obj:objs){
									t2.add(obj);
								}
							}
							Collections.sort(t2);
							if(StringUtils.isNotEmpty(cats) && StringUtils.isNotEmpty(d.getRulesMainObj()) && t1.toString().equals(t2.toString())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
						//如果规则中配置的是小类，判断选中的产品是否在包含在小类中
						if("smallLevel".equals(d.getRulesLevel())){
							String[] productIdss = productIds.split(",");
							String cats = "";
							List<String> t1 = new ArrayList<String>();
							List<String> t2 = new ArrayList<String>();
							for(String productId :productIdss){
								BaseProduct b =  baseProductService.get(new Integer(productId));
								SysDictionary sysDictionary1 = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(b.getProductTypeValue()));
								SysDictionary sysDictionary2 = sysDictionaryService.getSysDictionaryByValue(sysDictionary1.getValuekey(),new Integer(b.getProductSmallTypeValue()));
								String cat = sysDictionary2==null?"":sysDictionary2.getValuekey();//获取产品的小类
								cats = cats +","+ cat;
								t1.add(cat);
							}
							
							Collections.sort(t1);
							
							if(StringUtils.isNotEmpty(d.getRulesMainObj())){
								String[] objs = d.getRulesMainObj().split(",");
								for(String obj:objs){
									t2.add(obj);
								}
							}
							Collections.sort(t2);
							
							if(StringUtils.isNotEmpty(cats) && StringUtils.isNotEmpty(d.getRulesMainObj()) && t1.toString().equals(t2.toString())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
						//如果规则中配置的是产品，判断选中的产品是否相等
						if("productLevel".equals(d.getRulesLevel())){
							if(StringUtils.isNotEmpty(d.getRulesMainObj()) && productIds.equals(d.getRulesMainObj())){
							   rulejson =  d.getRulesSecondaryValues();
							   return new ResponseEnvelope<String>(rulejson,msgId,true); 
							}
						}
					}
			  }
			return new ResponseEnvelope<String>(rulejson,msgId,true); 
		}
}
