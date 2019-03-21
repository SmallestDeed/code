package com.sandu.web.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.house.bo.SystemDictionaryBO;
import com.sandu.api.house.input.SystemDictionarySearch;
import com.sandu.api.house.model.SystemDictionary;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.DictionaryTypeEnum;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BusinessException;
import com.sandu.util.Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月17日
 */

@RestController
@RequestMapping("/v1/dict")
@Api(tags = "字典")
@SuppressWarnings({"rawtypes", "unchecked"})
public class SysDictionaryController extends BaseController {

	@Autowired
	private SystemDictionaryService systemDictionaryService;

	/**
	 * 获取空间户型返回了空间对应cad的图形编码
	 * </p>
	 * http://localhost:9898/v1/draw/house/spaceShape
	 * 
	 * @return
	 */
	@GetMapping("/get/space/shape")
	@ApiOperation(value = "获取空间形状", response = SystemDictionary.class)
	public ReturnData getSpaceCommonShape(HttpServletRequest request) {
		ReturnData response = ReturnData.builder();
		List<SystemDictionary> list = new ArrayList<>();
		try {
			setMsgId(request);
			SystemDictionarySearch systemDictionarySearch = new SystemDictionarySearch();
			systemDictionarySearch.setType(DictionaryTypeEnum.SPACE_SHAPE.getType());
			list = systemDictionaryService.listSysDictionary(systemDictionarySearch);
		} catch (BusinessException e) {
			logger.error("获取空间形状异常 ==> {}", e.getMessage(), e);
			response.status(e.isFlag()).message(e.getResponseEnum().getMessage()).code(e.getResponseEnum());
		} catch (Exception e) {
			logger.error("获取空间形状异常 ==> {}", e.getMessage(), e);
			response.status(false).code(ResponseEnum.ERROR);
		}
		
		return response.list(list);
	}

	@GetMapping("/get/hardbaimo/type")
	@ApiOperation(value = "获取硬装白膜分类", response = SystemDictionary.class)
	public ReturnData getHardBaimoType(HttpServletRequest request) {
		ReturnData response = ReturnData.builder();
		List<SystemDictionary> listProductType = new ArrayList<>();
		setMsgId(request);
		SystemDictionarySearch search = new SystemDictionarySearch();
		search.setAtt3("baimo");
		search.setAtt4("1");
		search.setType(DictionaryTypeEnum.PRODUCT_TYPE.getType());
		listProductType = systemDictionaryService.listHardBaimoType(search);
		return response.list(listProductType);
	}

	@PostMapping("/get/hardbaimosamll/type")
	@ApiOperation(value = "获取硬装白膜小分类", response = SystemDictionary.class)
	public ReturnData getHardBaimoSamllType(HttpServletRequest request, String productValue) {
		ReturnData response = ReturnData.builder();
		List<SystemDictionary> listProductType = new ArrayList<>();
		setMsgId(request);
		SystemDictionarySearch search = new SystemDictionarySearch();
		search.setAtt3("baimo");
		search.setValue(productValue);
		search.setType(DictionaryTypeEnum.PRODUCT_TYPE.getType());
		listProductType = systemDictionaryService.listHardBaimoSamllType(search);
		return response.list(listProductType);
	}

	@PostMapping("/get/softbaimo/type")
	@ApiOperation(value = "获取软装白膜分类", response = SystemDictionary.class)
	public ReturnData getSoftBaimoSamllType(HttpServletRequest request) {
		ReturnData response = ReturnData.builder();
		List<SystemDictionaryBO> listProductType = new ArrayList<>();
		response = setMsgId(request);
		SystemDictionarySearch search = new SystemDictionarySearch();
		search.setAtt4("2");
		search.setAtt3("baimo");
		search.setType(DictionaryTypeEnum.PRODUCT_TYPE.getType());
		listProductType = systemDictionaryService.listSoftBiamoType(search);
		return response.list(listProductType);
	}

	@PostMapping("/get/groupType")
	@ApiOperation(value = "获取组合产品白膜分类", response = SystemDictionary.class)
	public ReturnData listGroupType(HttpServletRequest request) {
		ReturnData response = ReturnData.builder();
		List<SystemDictionaryBO> groupTypeVO;
		response = setMsgId(request);
		SystemDictionarySearch search = new SystemDictionarySearch();
		search.setType(DictionaryTypeEnum.GROUP_PRODUCT_TYPE.getType());
		List<SystemDictionary> listSysDictionary = systemDictionaryService.listSysDictionary(search);
		groupTypeVO = getGroupTypeVO(listSysDictionary);
		return response.list(groupTypeVO);
	}
	
	private static final Integer GROUP_NAME_SPLIT_SUFFIX_INDEX = 3;

	private List<SystemDictionaryBO> getGroupTypeVO(List<SystemDictionary> dict) {
		List<SystemDictionaryBO> list = new ArrayList<>();
		if (dict == null || dict.isEmpty()) {
			return list;
		}
		for (SystemDictionary dic : dict) {
			SystemDictionaryBO bo = new SystemDictionaryBO();
			bo.setId(dic.getId());
			// 组合名 => 前三个字符
			bo.setName(StringUtils.isNotBlank(dic.getName()) ? dic.getName().substring(0, GROUP_NAME_SPLIT_SUFFIX_INDEX) : dic.getName());
			bo.setType(dic.getType());
			bo.setValue(Objects.toString(dic.getValue(), Utils.VOID_VALUE));
			bo.setValuekey(dic.getValuekey());
			list.add(bo);
		}
		return list;
	}
}
