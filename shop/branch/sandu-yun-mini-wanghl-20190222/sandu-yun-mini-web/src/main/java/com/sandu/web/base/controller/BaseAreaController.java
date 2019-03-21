package com.sandu.web.base.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.service.BaseAreaService;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "区域信息", tags = "BaseArea")
@RestController
@RequestMapping(value = "/v1/sandu/mini/basearea")
public class BaseAreaController {
	private static final Logger logger = LoggerFactory.getLogger(BaseAreaController.class.getName());
	@Autowired
	private BaseAreaService baseAreaService;
	
	@RequestMapping(value = {"/list"},method=RequestMethod.GET)
	@ApiOperation(value = "获取所有城市", response = ResultMessage.class)
	public ResultMessage list(BaseArea area) {
		ResultMessage message=new ResultMessage();
		List<BaseAreaVo> lstArea=baseAreaService.getCityList();
		if(lstArea!=null &&lstArea.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstArea);
		}
		return message;
	}
	
	@RequestMapping(value = {"/listOpenService"},method=RequestMethod.GET)
	@ApiOperation(value = "获取开通选装服务的城市", response = ResultMessage.class)
	public ResultMessage listOpenService(BaseArea area) {
		ResultMessage message=new ResultMessage();
		List<BaseAreaVo> lstArea=baseAreaService.getOpenServiceCityList();
		if(lstArea!=null &&lstArea.size()>0) {
			message.setCode(ResultCode.Success);
			message.setData(lstArea);
		}
		return message;
	}
	
}
