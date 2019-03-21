package com.nork.design.controller.web;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.param.ParamCommonVerification;
import com.nork.design.model.*;
import com.nork.design.model.input.DesignPlanRecommendedQuery;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.product.model.BaseCompany;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 前端vue推荐方案列表
 *
 * @author xiaoxc
 * @data 2018-06-05
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanRecommended")
public class VueDesignPlanRecommendedController {

	@Autowired
	DesignPlanRecommendedServiceV2  designPlanRecommendedServiceV2;

	@Autowired
	private BaseCompanyService baseCompanyService;
	
	@Autowired
	private SysUserService sysUserService;
	

	/**
	 * 方案推荐 列表 数据
	 * @param request
	 * @param query
	 * @return
	 */
	@RequestMapping("/planRecommendedList")
	@ResponseBody
	public Object planRecommendedList(@RequestBody DesignPlanRecommendedQuery query, HttpServletRequest request){

		String msgId = query.getMsgId();
		String houseType = query.getHouseType();
		String livingName = query.getLivingName();
		String areaValue = query.getAreaValue();
		String designRecommendedStyleId = query.getDesignRecommendedStyleId();
		//如果这个有值，默认用这个风格将数据置顶，只用于一件装修页面
		String designRecommendedStyleIdTop = query.getDesignRecommendedStyleIdTop();
		String displayType = query.getDisplayType();
		String planName = query.getPlanName();
		String creator = query.getCreator();		//搜索条件：创建者
		String brandName = query.getBrandName();	//搜索条件：品牌
		String planCheckState = query.getPlanCheckState(); //推荐方案审核状态
		String spaceLayoutType = query.getSpaceLayoutType();//空间布局类型
		Integer start = query.getStart();
		Integer limit = query.getLimit();
		Long companyId = query.getCompanyId();
		
		// update by huangsongbo 2018.4.14 加入平台查询条件 -> start
		/*Platform-Code: pc2b*/
		String platformCode = request.getHeader("Platform-Code");
		if(StringUtils.isEmpty(platformCode)) {
			platformCode = "pc2b";
		}
		// update by huangsongbo 2018.4.14 加入平台查询条件 -> end
		
		if(StringUtils.isNotEmpty(livingName)) {
			livingName = livingName.trim();
			creator = livingName;
			brandName = livingName;
			planName = livingName;
		}

		if(StringUtils.isEmpty(designRecommendedStyleId)) {
			designRecommendedStyleId = null;
		}
		if("0".equals(designRecommendedStyleId)){
			designRecommendedStyleId = null;
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null || loginUser.getId() <= 0 ){
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"请重新登录！",msgId);
		}
		// vue店铺企业
		if (companyId != null) {
			loginUser.setBusinessAdministrationId(companyId);
		}

		if( loginUser.getBusinessAdministrationId() == null ){
			SysUser sysUser = sysUserService.get(loginUser.getId());
			if( sysUser != null ){
				if(sysUser.getBusinessAdministrationId() != null) {
					loginUser.setBusinessAdministrationId(Long.valueOf(sysUser.getBusinessAdministrationId() + ""));
				}
			}
		}

		// 2018-04-16 added by zhangwj 如果当前用户为经销商用户，则使用厂商的企业ID。（原因：经销商ID关联design_plan_brand表的企业ID无法查出数据。）
		BaseCompany baseCompany = null;
		if(loginUser.getBusinessAdministrationId() != null) {
			baseCompany = baseCompanyService.get(loginUser.getBusinessAdministrationId().intValue());
		}
		if( baseCompany != null && baseCompany.getBusinessType() != null && baseCompany.getBusinessType().intValue() == 2 ){
			loginUser.setBusinessAdministrationId(Long.valueOf(baseCompany.getPid() + ""));
		}

		if(limit == null || limit.intValue() <= 0){
			limit = -1;
		}
		if(start == null){
			start = -1;
		}
		
		PlanRecommendedListModel model = new PlanRecommendedListModel();
		model.setPlanName(planName);
		model.setLimit(limit);
		model.setStart(start);
		model.setCreator(creator);
		model.setBrandName(brandName);
		model.setHouseType(houseType);
		model.setLivingName(livingName);
		model.setAreaValue(areaValue);
		model.setDesignRecommendedStyleIdTop(designRecommendedStyleIdTop);
		model.setDesignRecommendedStyleId(designRecommendedStyleId);
		model.setDisplayType(displayType);
		model.setMsgId(msgId);
		model.setLoginUser(loginUser);
		model.setPlanCheckState(planCheckState == null ?planCheckState : planCheckState.trim());
		model.setSpaceLayoutType(spaceLayoutType);
		model.setPlatformCode(platformCode);
		model.setCompanyId(loginUser.getBusinessAdministrationId());
		
		return designPlanRecommendedServiceV2.getPlanRecommendedList(model);
	}

	/**
	 * 方案推荐详情
	 * @param request
	 * @param query
	 * @return
	 */
	@RequestMapping("/designPlanRecommendedDetails")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommended> designPlanRecommendedDetails(
			@RequestBody DesignPlanRecommendedQuery query, HttpServletRequest request){
		String msgId = query.getMsgId();
		String planRecommendedId = query.getPlanRecommendedId();
		boolean valid = ParamCommonVerification.checkTheParamIsValid(msgId,planRecommendedId);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.designPlanRecommendedDetails(msgId,planRecommendedId,loginUser);
	}

	/**
	 * 方案推荐详情 产品列表
	 * @param request
	 * @param query
	 * @return
	 */
	@RequestMapping("/getDesignPlanRecommendedProductList")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedProductResult> getDesignPlanRecommendedProductList(
			@RequestBody DesignPlanRecommendedQuery query, HttpServletRequest request){
		String msgId = query.getMsgId();
		String planRecommendedId = query.getPlanRecommendedId();
		boolean valid = ParamCommonVerification.checkTheParamIsValid(msgId,planRecommendedId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.getDesignPlanRecommendedProductList(msgId,planRecommendedId,loginUser);
	}

	/**
	 * 详情查看
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/detailsSee")
	@ResponseBody
	public ResponseEnvelope<ResRenderPic> detailsSee(@RequestBody MobileRenderingModel model, HttpServletRequest request){
		String detailsSeeType = model.getDetailsSeeType();
		String picId = model.getSmallPicId();
		String msgId = model.getMsgId();
		boolean valid = ParamCommonVerification.checkTheParamIsValid(msgId,picId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		if(StringUtils.isEmpty(detailsSeeType)){
			detailsSeeType = "";
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.detailsSee(msgId,picId,detailsSeeType,loginUser);
	}

}