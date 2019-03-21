package com.nork.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.SysDictionaryResponse;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.SysDictionaryService;

@Controller
@RequestMapping("/{style}/client/sysDictionary")
public class SysDictionaryController {

	private static final Logger logger  = org.slf4j.LoggerFactory.getLogger(SysDictionaryController.class);
	
	@Resource
	private SysDictionaryService sysDictionaryService;
	
	/**
	 * 材质配置列表
	 */
	@RequestMapping(value = "/getSysDictionaryList")
	@ResponseBody
	public Object getMaterialList( @ModelAttribute("sysDictionarySearch") SysDictionarySearch sysDictionarySearch,String type,HttpServletRequest request, HttpServletResponse response) {
		
		SysDictionaryResponse dictionaryResponse = null;
		
		//查询材质配置文件列表(type)标识
		sysDictionarySearch.setIsDeleted(0);
		sysDictionarySearch.setType(type);

		List<SysDictionaryResponse> list = new ArrayList<SysDictionaryResponse>();
		
		int total = 0;
		try {
			
			total = sysDictionaryService.getCount(sysDictionarySearch);
            
			if (total > 0) {
				List<SysDictionary> dicList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
				
				for(SysDictionary dic : dicList){
					dictionaryResponse = new SysDictionaryResponse();
					dictionaryResponse.setId(dic.getId());
					dictionaryResponse.setName(dic.getName());
					dictionaryResponse.setType(dic.getType());
					dictionaryResponse.setValuekey(dic.getValuekey());
					dictionaryResponse.setValue(dic.getValue());
					list.add(dictionaryResponse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysDictionary>(false, "数据异常!",sysDictionarySearch.getMsgId());
		}
		return new ResponseEnvelope<SysDictionaryResponse>(total, list, sysDictionarySearch.getMsgId());
	}
	
}
