package com.nork.customerservice.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.customerservice.cache.SysFaqCacher;
import com.nork.customerservice.model.SysFaq;
import com.nork.customerservice.model.search.SysFaqSearch;
import com.nork.customerservice.model.small.SysFaqSmall;
import com.nork.customerservice.service.SysFaqService;

@Controller
@RequestMapping("/{style}/web/customerservice/sysFaq")
public class WebSysFaqController {

	private final JsonDataServiceImpl<SysFaq> JsonUtil = new JsonDataServiceImpl<SysFaq>();
	
	private static Logger logger = Logger.getLogger(WebSysFaqController.class);
	
	@Autowired
	private SysFaqService sysFaqService;
	
	/**
	 * 常见问题列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style,@ModelAttribute("sysFaqSearch") SysFaqSearch sysFaqSearch
				,HttpServletRequest request, HttpServletResponse response) {
		
		/**************************************/
		long start =System.currentTimeMillis();
		/**************************************/
		
 		/**每页不同页码时使用*/
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysFaqSearch = (SysFaqSearch)JsonUtil.getJsonToBean(jsonStr, SysFaqSearch.class);
			if(sysFaqSearch == null){
			   return new ResponseEnvelope<SysFaq>(false, "传参异常!","none");
			}
		}
		
		List<SysFaq> list = new ArrayList<SysFaq> ();
		int total = 0;
		try {
			sysFaqSearch.setSort(1);
			if(Utils.enableRedisCache()){
				total = SysFaqCacher.getSysFaqCount(sysFaqSearch);
			}else{
				total = sysFaqService.getCount(sysFaqSearch);
			}
			
            logger.info("total:" + total);
            
			if (total > 0) {
				 list = sysFaqService.getPaginatedList(sysFaqSearch); 
				//list = SysFaqCacher.getSysFaqList(sysFaqSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String sysFaqJsonList = JsonUtil.getListToJsonData(list);
				 List<SysFaqSmall> smallList= new JsonDataServiceImpl<SysFaqSmall>().getJsonToBeanList(sysFaqJsonList, SysFaqSmall.class);
				 return new ResponseEnvelope<SysFaqSmall>(total,smallList,sysFaqSearch.getMsgId());
			 }
			 
				/*******************************************/
				//////System.out.println("times = " + (System.currentTimeMillis() -start));
				/*******************************************/
				
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysFaq>(false, "数据异常!",sysFaqSearch.getMsgId());
		}
		return new ResponseEnvelope<SysFaq>(total, list,sysFaqSearch.getMsgId());
	}
	
	/**
	 *获取 常见问题详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("sysFaq") SysFaq sysFaq
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			sysFaq = (SysFaq)JsonUtil.getJsonToBean(jsonStr, SysFaq.class);
			if(sysFaq == null){
			   return new ResponseEnvelope<SysFaq>(false,"none", "传参异常!");
			}
			id =  sysFaq.getId();
			msgId = sysFaq.getMsgId() ;
		}else{
		    id =  sysFaq.getId();
		    msgId = sysFaq.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<SysFaq>(false, "参数缺少id!",msgId);
		}
		
		try {
			if(Utils.enableRedisCache()){
				sysFaq = SysFaqCacher.getSysFaqById(id);
			}else{
				sysFaq = sysFaqService.get(id);
			}
			
			if("small".equals(style) && sysFaq != null ){
				 String sysFaqJson = JsonUtil.getBeanToJsonData(sysFaq);
				 SysFaqSmall sysFaqSmall= new JsonDataServiceImpl<SysFaqSmall>().getJsonToBean(sysFaqJson, SysFaqSmall.class);
				 
				 return new ResponseEnvelope<SysFaqSmall>(sysFaqSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysFaq>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<SysFaq>(sysFaq,msgId,true);
	}
	
}
