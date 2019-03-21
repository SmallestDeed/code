package com.nork.designconfig.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductAttributeService;
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
@RequestMapping("/{style}/web/designconfig/designRules")
public class WebDesignRulesController {
	private static Logger logger = Logger
			.getLogger(WebDesignRulesController.class);
	private final JsonDataServiceImpl<DesignRules> JsonUtil = new JsonDataServiceImpl<DesignRules>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/designconfig/designRules";
	private final String BASEMAIN = "/"+ STYLE + "/designconfig/base/designRules";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/designconfig/designRules";
	
	@Autowired
	private DesignRulesService designRulesService;
	
	@Autowired
	private DesignPlanService  designPlanService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private ProductAttributeService productAttributeService;
 
	
	
	/**
	 * 获取规则
	 * getRulesSecondaryList
	 * @param request
	 * @param productId
	 * @param designPlanId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/getRulesSecondaryList")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Object getRulesSecondaryList(HttpServletRequest request,
			@RequestParam(value="productId", required = false) Integer productId,
			@RequestParam(value="designPlanId", required = false) Integer designPlanId,
			@RequestParam(value="msgId", required = false) String msgId){
			/*缓存处理*/
			 ResponseEnvelope responseEnvelope=null;
			Map<Object,Object>paramsMap =new HashMap<Object,Object>();
			paramsMap.put("productId", productId);
			paramsMap.put("designPlanId", designPlanId);
			if(Utils.enableRedisCache()){
				responseEnvelope=CommonCacher.getAll(ModuleType.DesignRules, "getRulesSecondaryList", paramsMap);
			}
			if(responseEnvelope!=null){
				responseEnvelope.setMsgId(msgId);
				return responseEnvelope;
			}
			BaseProduct baseProduct= null;
			String bigValue=null;
			String smallValue=null;
			String productTypeCode=null;
			String productSmallTypeCode=null;
			DesignPlan designPlan=null;
			Integer spaceCommonId=null;
			Integer designTempletId=null;
		
			try{
				
				if(productId==null||productId.intValue()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "缺少参数productId", msgId);
				}
				
				baseProduct=baseProductService.get(productId);
				if(baseProduct==null){
					return new ResponseEnvelope<BaseProduct>(false, "找不到该产品ID!"+productId, msgId);
				}
				bigValue=baseProduct.getProductTypeValue();
				smallValue=baseProduct.getProductSmallTypeValue()+"";
				
				SysDictionary sysDictionary = new SysDictionary();
				sysDictionary.setIsDeleted(0);
				sysDictionary.setType("productType");
				List<SysDictionary>bgList=null;
				List<SysDictionary>smallList=null;
				if(bigValue!=null&&!"".equals(bigValue)){
					sysDictionary.setValue(Integer.parseInt(bigValue));
					bgList=sysDictionaryService.getList(sysDictionary);
				}
				if(bgList==null||bgList.size()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "找不到该产品大类!", msgId);
				}
				if(bgList!=null&&bgList.size()!=1){
					return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
				}
				if(bgList!=null&&bgList.size()==1){
					productTypeCode=bgList.get(0).getValuekey();
				}
				if(bigValue!=null&&smallValue!=null&&!"".equals(smallValue)){
					sysDictionary.setType(productTypeCode);
					sysDictionary.setValue(Integer.parseInt(smallValue));
					smallList=sysDictionaryService.getList(sysDictionary);
				}
				if(smallList==null||smallList.size()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "找不到该产品小类!", msgId);
				}
				if(smallList!=null&&smallList.size()!=1){
					return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
				}
				if(smallList!=null&&smallList.size()==1){
					productSmallTypeCode=smallList.get(0).getValuekey();
				}
				
				 
				designPlan=designPlanService.get(designPlanId);
				if(designPlan!=null){
					spaceCommonId=designPlan.getSpaceCommonId();
					designTempletId=designPlan.getDesignTemplateId();
				}
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(productId);
		 
				
				responseEnvelope=new ResponseEnvelope();
 
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(productId+"",
						productTypeCode,productSmallTypeCode,spaceCommonId,designTempletId,new DesignRules(),map);
				rulesMap.put("putawayState",baseProduct.getPutawayState()==null?null:baseProduct.getPutawayState().toString());
				responseEnvelope.setMsgId(msgId);
				responseEnvelope.setObj(rulesMap);
				/*if(rulesMap!=null&&rulesMap.size()>0){
					JSONObject jsonObject = JSONObject.fromObject(rulesMap);  
					String res=jsonObject.toString();
					if(res!=null&&!"".equals(res)){
						res=res.replace("\"","") ;
						res=res.replace("\\","");
						responseEnvelope.setObj(res);
					}
				}*/
				/*加入缓存处理*/
				if(Utils.enableRedisCache()){
					CommonCacher.addAll(ModuleType.DesignRules, "getRulesSecondaryList", paramsMap, responseEnvelope);
				}
				return responseEnvelope;
					
			}catch(Exception e){
				e.printStackTrace();
				return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
			}
	}
	
}
