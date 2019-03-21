package com.nork.system.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.cache.SysDictionaryCacher;
import com.nork.system.model.SysDicitonaryOptimize;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.model.small.SysDictionarySmall;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: BaseMessageController.java
 * @Package com.nork.system.controller
 * @Description:系统字典服务端Controller
 * @createAuthor qiujun
 * @CreateDate 2016-05-06
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/system/sysDictionary")
public class WebSysDictionaryController {
	private final JsonDataServiceImpl<SysDictionary> JsonUtil = new JsonDataServiceImpl<SysDictionary>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user/sysDictionary";
	ResourceBundle app = ResourceBundle.getBundle("app");
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	
	/**
	 * 数据字典全部列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@ModelAttribute("sysDictionarySearch") SysDictionarySearch sysDictionarySearch, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long startTime=System.currentTimeMillis();
		String jsonStr = Utils.getJsonStr(request); 
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			try {
				sysDictionarySearch = (SysDictionarySearch) JsonUtil.getJsonToBean(jsonStr, SysDictionarySearch.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (sysDictionarySearch == null) {
				return new ResponseEnvelope<SysDictionary>(false, "传参异常!", "none");
			}
		}
		List<SysDicitonaryOptimize> list = new ArrayList<SysDicitonaryOptimize>();
		int total = 0;
		try {

			
//			if(Utils.enableRedisCache()){
//				list=SysDictionaryCacher.getListOptimize();
//			}else{
				sysDictionarySearch.setOrder("ordering");
				sysDictionarySearch.setOrderNum("asc");
				sysDictionarySearch.setType("spaceShape");
				list = sysDictionaryService.getListOptimize(sysDictionarySearch);
//			}
			if( list != null && list.size() > 0 ){
				//TODO  APP端为了生成导航，需要产品小类来排序
				// 先获取所有大类
				List<SysDictionary> productTypeList = sysDictionaryService.findAllByType("productType");
				if( productTypeList != null && productTypeList.size() > 0 ){
					// 获取大类下的所有小类
					List<SysDicitonaryOptimize> productSmallTypeList = null;
					SysDictionarySearch sysDicSearch = null;
					for( SysDictionary sysDictionary : productTypeList ){
						String productType = sysDictionary.getValuekey();
						if( StringUtils.isNotBlank(productType) ){
							sysDicSearch = new SysDictionarySearch();
							sysDicSearch.setType(productType);
							productSmallTypeList = sysDictionaryService.findAll(sysDicSearch);
							list.addAll(productSmallTypeList);
						}
					}
				}
				if ("small".equals(style) ) {
					String sysDictionaryJsonList = JsonUtil.getListToJsonData(list);
					List<SysDictionarySmall> smallList = new JsonDataServiceImpl<SysDictionarySmall>()
							.getJsonToBeanList(sysDictionaryJsonList, SysDictionarySmall.class);
					return new ResponseEnvelope<SysDictionarySmall>(total, smallList, sysDictionarySearch.getMsgId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysDictionary>(false, "数据异常!", sysDictionarySearch.getMsgId());
		}
		Long endTime=System.currentTimeMillis();
		//////System.out.println("times:"+(endTime-startTime));
		
		SysDicitonaryOptimize SysDicitonaryOptimize=new SysDicitonaryOptimize();
		SysDicitonaryOptimize.setTimeConsuming("-------------方法耗时----------"+(endTime-startTime)+"");
		list.add(SysDicitonaryOptimize);
		return new ResponseEnvelope<SysDicitonaryOptimize>(total, list, sysDictionarySearch.getMsgId());
	}
	
	



}
