package com.sandu.web.base.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sandu.base.model.BaseArea;
import com.sandu.base.model.query.BaseCompanyQuery;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.model.vo.BaseCompanyVo;
import com.sandu.base.service.BaseAreaService;
import com.sandu.base.service.BaseCompanyService;
import com.sandu.matadata.Page;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.resource.service.ResPicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "企业信息", tags = "BaseCompany")
@RestController
@RequestMapping(value = "/v1/sandu/mini/basecompany")
public class BaseCompanyController {
	private static final Logger logger = LoggerFactory.getLogger(BaseCompanyController.class.getName());
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	
	@RequestMapping(value = {"/detail"},method=RequestMethod.GET)
	@ApiOperation(value = "获取企业详情", response = ResultMessage.class)
	public ResultMessage detail(long companyId) {
		ResultMessage message=new ResultMessage();
		BaseCompanyVo vo = baseCompanyService.get(companyId);
		if(vo!=null){
			vo.setLstResPic(resPicService.getCompanyRecommendPlan(vo.getId()));
			message.setData(vo);
			message.setCode(ResultCode.Success);
		}
		return message;
	}
	
	@RequestMapping(value = {"/list"},method=RequestMethod.GET)
	@ApiOperation(value = "分页获取企业信息", response = ResultMessage.class)
	public ResultMessage list(BaseCompanyQuery query) {
		ResultMessage message=new ResultMessage();
		Page<BaseCompanyVo> page=baseCompanyService.getList(query);
		if(page!=null && page.getList()!=null && page.getList().size()>0) {
			for(BaseCompanyVo vo:page.getList()) {
				//补充企业推荐方案的图片资源信息
				vo.setLstResPic(resPicService.getCompanyRecommendPlan(vo.getId()));
			}
			message.setData(page);
			message.setCode(ResultCode.Success);
		}
		return message;
	}
	
}
