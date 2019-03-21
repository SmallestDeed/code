package com.nork.mobile.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.dto.SysDictionaryGetListDTO;
import com.nork.system.service.SysDictionaryService;

@Controller
@RequestMapping("/{style}/mobile/system/sysDictionary")
public class MobileSysDictionaryController {

	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@RequestMapping(value = "/getSelectList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEnvelope<SysDictionaryGetListDTO> getDTOList(String type) {
		// ------参数验证 ->start
		if(StringUtils.isEmpty(type)) {
			return new ResponseEnvelope<>(false, "参数 type 不能为空");
		}
		// ------参数验证 ->end
		
		List<SysDictionaryGetListDTO> returnList = sysDictionaryService.getDTOList(type);
		return new ResponseEnvelope<>(true, returnList);
	}
	
}
