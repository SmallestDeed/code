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
import com.nork.system.model.SysConsumingRecords;
import com.nork.system.model.search.SysConsumingRecordsSearch;
import com.nork.system.model.small.SysConsumingRecordsSmall;
import com.nork.system.service.SysConsumingRecordsService;

@Controller
@RequestMapping("/{style}/web/system/sysConsumingRecords")
public class WebSysConsumingRecordsController {
	
	private static Logger logger = Logger.getLogger(WebSysConsumingRecordsController.class);
	private final JsonDataServiceImpl<SysConsumingRecords> JsonUtil = new JsonDataServiceImpl<SysConsumingRecords>();
	@Autowired
	private SysConsumingRecordsService sysConsumingRecordsService;
	
	
	
	/**
	 * 消费记录列表
	 */
	@RequestMapping(value = "/queryConsumingRecords")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("sysConsumingRecordsSearch") SysConsumingRecordsSearch sysConsumingRecordsSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		/**每页不同页码时使用*/
		
		if(sysConsumingRecordsSearch.getMsgId()==null||"".equals(sysConsumingRecordsSearch.getMsgId())){
			return new ResponseEnvelope<SysConsumingRecords>(false, "缺少 msgId 参数!","none");
		}
		if(sysConsumingRecordsSearch.getUserId()==null||"".equals(sysConsumingRecordsSearch.getUserId())){
			return new ResponseEnvelope<SysConsumingRecords>(false, "缺少 userId 参数!","none");
		}
 		sysConsumingRecordsSearch.setLimit(new Integer(20));
 		String limit=request.getParameter("limit");
 		if(limit!=null&&!"".equals(limit)){
 			sysConsumingRecordsSearch.setLimit(Integer.parseInt(limit));
 		}
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
}
