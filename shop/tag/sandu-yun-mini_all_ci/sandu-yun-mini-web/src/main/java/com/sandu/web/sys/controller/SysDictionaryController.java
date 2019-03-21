package com.sandu.web.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.common.constant.SysDictionaryConstant;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.sys.model.vo.SysDictionaryVo;
import com.sandu.sys.service.SysDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "数据字典服务", tags = "SysDictionary")
@RestController
@RequestMapping(value = "/v1/sandu/mini/sysdictionary")
public class SysDictionaryController {

	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@RequestMapping(value = {"/designstyles"},method=RequestMethod.GET)
	@ApiOperation(value = "获取设计师风格", response = ResultMessage.class)
	public ResultMessage list() {
		ResultMessage message=new ResultMessage();
		String type=SysDictionaryConstant.DESIGNER_STYLES;
		List<SysDictionaryVo> lstDic=sysDictionaryService.getListWithType(type);
		if(lstDic!=null &&lstDic.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstDic);
		}
		return message;
	}
	
	@RequestMapping(value = {"/list"},method=RequestMethod.GET)
	@ApiOperation(value = "根据类型获取数据字典项", response = ResultMessage.class)
	public ResultMessage list(String type) {
		ResultMessage message=new ResultMessage();
		if(StringUtils.isEmpty(type)) {
			message.setMessage("字典类型不能为空.");
			return message;
		}
		List<SysDictionaryVo> lstDic=sysDictionaryService.getListWithType(type);
		if(lstDic!=null &&lstDic.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstDic);
		}
		return message;
	}
	
	@RequestMapping(value = {"/listwithbusinessType"},method=RequestMethod.GET)
	@ApiOperation(value = "根据类型和业务类型获取数据字典项", response = ResultMessage.class)
	public ResultMessage list(String type, String businessType) {
		ResultMessage message=new ResultMessage();
		if(StringUtils.isEmpty(type)) {
			message.setMessage("字典类型不能为空.");
			return message;
		}
		List<SysDictionaryVo> lstDic=sysDictionaryService.getListWithType(type);
		if(lstDic!=null &&lstDic.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstDic);
			message.setBusinessType(businessType);
		}
		return message;
	}
}
