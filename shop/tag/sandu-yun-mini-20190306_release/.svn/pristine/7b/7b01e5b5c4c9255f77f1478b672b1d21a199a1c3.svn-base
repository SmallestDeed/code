package com.sandu.web.company.controller;

import java.math.BigInteger;
import java.util.List;

import com.sandu.company.model.query.CompanyDesignerQuery;
import com.sandu.designplan.model.query.DesignPlanQuery;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.resource.model.ResPic;
import com.sandu.sys.model.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.common.constant.SysDictionaryConstant;
import com.sandu.common.utils.StringUtils;
import com.sandu.common.utils.SysDictionaryUtils;
import com.sandu.company.model.vo.CompanyDesignerVo;
import com.sandu.company.service.CompanyDesignerService;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import com.sandu.sys.model.vo.SysDictionaryVo;
import com.sandu.sys.service.SysDictionaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "企业设计师", tags = "CompanyDesigner")
@RestController
@RequestMapping(value = "/v1/sandu/mini/CompanyDesigner")
public class CompanyDesignerController {
	@Autowired
	private CompanyDesignerService companyDesignerService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	
	@ApiOperation(value = "查询企业设计团队", response = ResultMessage.class)
    @RequestMapping(value="/designerList",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public ResultMessage detail(CompanyDesignerQuery input) {
    	ResultMessage result=new ResultMessage();
    	if (null==input.getCompanyId()||null==input.getPlatformValue()){
			result.setCode(ResultCode.ParameterError);
			result.setMessage("参数不能为空");
			return  result;
		}
		try{
			List<CompanyDesignerVo> lstVo= companyDesignerService.getTopDesigner(input);
			if(lstVo!=null && lstVo.size()>0) {
				String type=SysDictionaryConstant.DESIGNER_STYLES;
				//List<SysDictionaryVo> lstDictionary=sysDictionaryService.getListWithType(type);
				for(CompanyDesignerVo vo : lstVo) {
				/*if(StringUtils.isNotEmpty(vo.getStyleids())) {
					vo.setStyleNames(SysDictionaryUtils.getNames(lstDictionary, vo.getStyleids()));
				}*/
					//作品数
					DesignPlanQuery query = new DesignPlanQuery();
					query.setUserId(BigInteger.valueOf(vo.getUserId()));
					vo.setWorksCount(designPlanRecommendedService.findRecommendedPlanCount(query));
					//头像为空处理
				/*if (StringUtils.isEmpty(vo.getPicPath())) {
					Integer sexValue=vo.getSex();
					if(sexValue == null ) {
						sexValue = 1;
					}
					//查找默认头像地址
					SysDictionaryVo dictionary=sysDictionaryService.getDictionoryByTypeOrValue("userDefaultPicture", sexValue);
					vo.setPicPath(dictionary == null ? "" : dictionary.getPicPath());
				}*/
				}
				result.setCode(ResultCode.Success);
				result.setData(lstVo);
				result.setMessage("查询成功");
				return result;
			}
			result.setCode(ResultCode.NoDateReturn);
			result.setMessage("查询成功，无设计师数据");
			return result;
		}catch (Exception e){
    		result.setCode(ResultCode.Fail);
    		result.setMessage("系统异常");
    		return result;
		}

	}
	
}
