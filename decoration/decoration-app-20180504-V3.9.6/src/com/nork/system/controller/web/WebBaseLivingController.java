package com.nork.system.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nork.system.model.BaseLiving;
import com.nork.system.model.search.BaseLivingSearch;
import com.nork.system.model.small.BaseLivingSmall;
import com.nork.system.service.BaseLivingService;

/**   
 * @Title: BaseMessageController.java 
 * @Package com.nork.system.controller
 * @Description:小区服务端Controller
 * @createAuthor qiujun 
 * @CreateDate 2016-05-06
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/system/baseLiving")
public class WebBaseLivingController {
	private static Logger logger = Logger
			.getLogger(WebBaseMessageController.class);
	private final JsonDataServiceImpl<BaseLiving> JsonUtil = new JsonDataServiceImpl<BaseLiving>();
	private final String STYLE="online";		
	private final String JSPSTYLE="online";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/user/baseLiving";
	
	@Autowired
	private BaseLivingService baseLivingService;
 
	/**
	 * 小区列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("baseLivingSearch") BaseLivingSearch baseLivingSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
		if( baseLivingSearch.getLimit() == null ) {
			baseLivingSearch.setLimit(new Integer(20));
		}
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			baseLivingSearch = (BaseLivingSearch)JsonUtil.getJsonToBean(jsonStr, BaseLivingSearch.class);
			if(baseLivingSearch == null){
			   return new ResponseEnvelope<BaseLiving>(false, "传参异常!","none");
			}
		}
		
		List<BaseLiving> list = new ArrayList<BaseLiving> ();
		int total = 0;
		try {
			total = baseLivingService.getCount(baseLivingSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = baseLivingService.getPaginatedList(
						baseLivingSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String baseLivingJsonList = JsonUtil.getListToJsonData(list);
				 List<BaseLivingSmall> smallList= new JsonDataServiceImpl<BaseLivingSmall>().getJsonToBeanList(baseLivingJsonList, BaseLivingSmall.class);
				 return new ResponseEnvelope<BaseLivingSmall>(total,smallList,baseLivingSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseLiving>(false, "数据异常!",baseLivingSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseLiving>(total, list,baseLivingSearch.getMsgId());
	}
}
