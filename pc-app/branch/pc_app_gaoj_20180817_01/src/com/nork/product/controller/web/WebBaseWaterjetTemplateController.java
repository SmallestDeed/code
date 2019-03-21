package com.nork.product.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.collections.Lists;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.dto.BaseWaterjetTemplateDTO;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;
import com.nork.product.service.BaseWaterjetTemplateService;

@Controller
@RequestMapping("/{style}/web/product/baseWaterjetTemplate")
public class WebBaseWaterjetTemplateController {

	private static final Logger logger = LoggerFactory.getLogger(WebBaseWaterjetTemplateController.class);
	
	private String logPrefix = "custom:";
	
	@Autowired
	private BaseWaterjetTemplateService baseWaterjetTemplateService;
	
	/**
	 * 获取水刀模板列表
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public Object findAll(BaseWaterjetTemplateSearch search) {
		// 参数验证 ->start
		if(search == null) {
			logger.error(logPrefix + "search = null");
			return new ResponseEnvelope<>(false, "参数错误", null);
		}
		// 参数验证 ->end
		
		// 查询条件设置 ->start
		search.setIsDeleted(0);
		// 查询条件设置 ->end
		
		int total = baseWaterjetTemplateService.getCountBySearch(search);
		List<BaseWaterjetTemplateDTO> returnList = null;
		List<BaseWaterjetTemplate> list = null;
		if(total > 0) {
			list = baseWaterjetTemplateService.findAllBySearch(search);
		}
		
		list = baseWaterjetTemplateService.setMoreInfoForfindAll(list);
		
		returnList = this.convertToDTO(list);
		
		return new ResponseEnvelope<>(total, returnList, search.getMsgId());
	}

	private List<BaseWaterjetTemplateDTO> convertToDTO(List<BaseWaterjetTemplate> list) {
		if(Lists.isEmpty(list)) {
			return null;
		}
		List<BaseWaterjetTemplateDTO> baseWaterjetTemplateDTOList = new ArrayList<BaseWaterjetTemplateDTO>();
		list.forEach(item -> {
			BaseWaterjetTemplateDTO baseWaterjetTemplateDTO = this.convertToDTO(item);
			baseWaterjetTemplateDTOList.add(baseWaterjetTemplateDTO);
		});
		return baseWaterjetTemplateDTOList;
	}

	private BaseWaterjetTemplateDTO convertToDTO(BaseWaterjetTemplate item) {
		// 参数验证 ->start
		if(item == null) {
			return null;
		}
		// 参数验证 ->end
		
		BaseWaterjetTemplateDTO baseWaterjetTemplateDTO = new BaseWaterjetTemplateDTO();
		baseWaterjetTemplateDTO.setId(item.getId());
		baseWaterjetTemplateDTO.setTemplateFileUrl(item.getTemplateFileUrl());
		baseWaterjetTemplateDTO.setTemplateLength(item.getTemplateLength());
		baseWaterjetTemplateDTO.setTemplateName(item.getTemplateName());
		baseWaterjetTemplateDTO.setTemplatePicUrl(item.getTemplatePicUrl());
		baseWaterjetTemplateDTO.setTemplateWidth(item.getTemplateWidth());
		return baseWaterjetTemplateDTO;
	}
	
}
