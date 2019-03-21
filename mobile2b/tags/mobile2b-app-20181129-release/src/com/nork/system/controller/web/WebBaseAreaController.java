package com.nork.system.controller.web;

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
import com.nork.system.cache.BaseAreaCacher;
import com.nork.system.model.BaseArea;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.small.BaseAreaSmall;
import com.nork.system.service.BaseAreaService;

/**
 * @Title: BaseMessageController.java
 * @Package com.nork.system.controller
 * @Description:区域服务端Controller
 * @createAuthor qiujun
 * @CreateDate 2016-05-06
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/system/baseArea")
public class WebBaseAreaController {
	private static Logger logger = Logger.getLogger(WebBaseMessageController.class);
	private final JsonDataServiceImpl<BaseArea> JsonUtil = new JsonDataServiceImpl<BaseArea>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user/baseArea";

	@Autowired
	private BaseAreaService baseAreaService;

	/**
	 * 行政区域列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style, @ModelAttribute("baseAreaSearch") BaseAreaSearch baseAreaSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		if (baseAreaSearch == null) {
			baseAreaSearch.setLimit(new Integer(20));
		}

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseAreaSearch = (BaseAreaSearch) JsonUtil.getJsonToBean(jsonStr, BaseAreaSearch.class);
			if (baseAreaSearch == null) {
				return new ResponseEnvelope<BaseArea>(false, "传参异常!", "none");
			}
		}

		List<BaseArea> list = new ArrayList<BaseArea>();
		int total = 0;
		try {
			total = baseAreaService.getCount(baseAreaSearch);

			if (total > 0) {
				list = baseAreaService.getPaginatedList(baseAreaSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String baseAreaJsonList = JsonUtil.getListToJsonData(list);
				List<BaseAreaSmall> smallList = new JsonDataServiceImpl<BaseAreaSmall>()
						.getJsonToBeanList(baseAreaJsonList, BaseAreaSmall.class);
				return new ResponseEnvelope<BaseAreaSmall>(total, smallList, baseAreaSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseArea>(false, "数据异常!", baseAreaSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseArea>(total, list, baseAreaSearch.getMsgId());
	}

	/**
	 * 行政区域全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style, @ModelAttribute("baseAreaSearch") BaseAreaSearch baseAreaSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long startTime = System.currentTimeMillis();
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseAreaSearch = (BaseAreaSearch) JsonUtil.getJsonToBean(jsonStr, BaseAreaSearch.class);
			if (baseAreaSearch == null) {
				return new ResponseEnvelope<BaseArea>(false, "传参异常!", "none");
			}
		}
		//baseAreaSearch.setOrders(" ordering desc");
		baseAreaSearch.setIsDeleted(0);
		List<BaseArea> list = new ArrayList<BaseArea>();
		List<BaseArea> reslist = new ArrayList<BaseArea>();
		int total = 0;
		try {
			if(Utils.enableRedisCache()){
				list = BaseAreaCacher.getAllList();
			}else{
				list = baseAreaService.getList(new BaseArea());
			}
		
			
			if(baseAreaSearch.getLevelId()!=null){
				for(BaseArea ba:list){
					if(baseAreaSearch.getLevelId().equals(ba.getLevelId())){
						reslist.add(ba);
					}
				}
			}
			if(baseAreaSearch.getPid()!=null){
				for(BaseArea ba:list){
					if(baseAreaSearch.getPid().equals(ba.getPid())){
						reslist.add(ba);
					}
				}
			}
			
			total= reslist.size();
			if ("small".equals(style) && reslist != null && reslist.size() > 0) {
				String baseAreaJsonList = JsonUtil.getListToJsonData(reslist);
				List<BaseAreaSmall> smallList = new JsonDataServiceImpl<BaseAreaSmall>()
						.getJsonToBeanList(baseAreaJsonList, BaseAreaSmall.class);
				return new ResponseEnvelope<BaseAreaSmall>(total, smallList, baseAreaSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseArea>(false, "数据异常!", baseAreaSearch.getMsgId());
		}
		Long endTime = System.currentTimeMillis();
		//////System.out.println("times:" + (endTime - startTime));
		return new ResponseEnvelope<BaseArea>(total, reslist, baseAreaSearch.getMsgId());
	}
}
