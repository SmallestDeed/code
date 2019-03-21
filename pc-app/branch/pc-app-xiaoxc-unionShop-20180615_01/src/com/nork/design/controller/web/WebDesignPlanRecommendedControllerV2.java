package com.nork.design.controller.web;

import java.util.*;

import javax.mail.search.SearchTerm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.nork.base.model.BasePlatform;
import com.nork.base.service.BasePlatformService;
import com.nork.common.constant.SysDictionaryConstant;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.IgnoreJsonPropertyFilter;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.*;
import com.nork.design.model.constant.DesignPlanRecommendedConstant;
import com.nork.design.model.constant.PlanSourceEnum;
import com.nork.design.model.constant.PlatformTypeEnum;
import com.nork.design.model.constant.ShelfStatusEnum;
import com.nork.design.model.input.FranchiserPlanListDTO;
import com.nork.design.model.input.PlanRecommendedListForFranchiserDTO;
import com.nork.design.model.output.FranchiserPlanListVO;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.product.model.*;
import com.nork.product.service.GroupProductService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.design.cache.DesignCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.common.RecommendedDecorateState;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.nork.system.service.SysUserService;


/**
 * 设计方案推荐
 * 2017-0710 zhaobl
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanRecommendedV2")
public class WebDesignPlanRecommendedControllerV2 {
	private static Logger logger = Logger.getLogger(WebDesignTempletController.class);
	@Autowired
	ResRenderPicService resRenderPicService;
	@Autowired
	ResRenderVideoService resRenderVideoService;
	@Autowired
	BaseBrandService baseBrandService;
	@Autowired
	DesignPlanRecommendedServiceV2  designPlanRecommendedServiceV2;
	@Autowired
	DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
	@Autowired
	GroupProductService groupProductService;

	@Autowired
	private BasePlatformService basePlatformService;
	
	@Autowired
	private BaseCompanyService baseCompanyService;
	
	@Autowired
	private SysUserService sysUserService;
	
	private static String logPrefixClass = "function:WebDesignPlanRecommendedControllerV2.";
	
	/**
	 * 方案推荐 列表 数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/planRecommendedList")
	@ResponseBody
	public Object planRecommendedList(HttpServletRequest request,Integer start, Integer limit, HttpServletResponse response){

		String msgId = request.getParameter("msgId");
		String houseType = request.getParameter("houseType");
		String livingName = request.getParameter("livingName");
		String areaValue = request.getParameter("areaValue");
		String designRecommendedStyleId = request.getParameter("designRecommendedStyleId");
		//如果这个有值，默认用这个风格将数据置顶，只用于一件装修页面
		String designRecommendedStyleIdTop = request.getParameter("designRecommendedStyleIdTop");
		String displayType = request.getParameter("displayType");
		String planName = request.getParameter("planName");
		String creator = request.getParameter("creator");		//搜索条件：创建者
		String brandName = request.getParameter("brandName");	//搜索条件：品牌
		String planCheckState = request.getParameter("planCheckState"); //推荐方案审核状态
		String spaceLayoutType = request.getParameter("spaceLayoutType");//空间布局类型
		
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
		boolean valid = this.validPlanReleaseParam(msgId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		
		if(StringUtils.isEmpty(designRecommendedStyleId)) {
			designRecommendedStyleId = null;
		}
		if("0".equals(designRecommendedStyleId)){
			designRecommendedStyleId = null;
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null || loginUser.getId() <= 0 ){
//			loginUser = new LoginUser();
//			loginUser.setId(13080);
//			loginUser.setUserType(4);
//			loginUser.setBusinessAdministrationId((long)2960);
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"请重新登录！",msgId);
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
	 * 公开方案
	 * 
	 * @author huangsongbo
	 * @date 2018.3.27
	 * @param dto
	 * @return
	 */
	@RequestMapping("/planRecommendedListForFranchiser")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedResult> planRecommendedListForFranchiser(
			@Valid PlanRecommendedListForFranchiserDTO dto,
			HttpServletRequest request
			) {
		String logPrefixFunction = logPrefixClass + "planRecommendedListForFranchiser -> ";
		
		// 参数验证 ->start
		if(dto == null) {
			return new ResponseEnvelope<>(false, "dto = null", null);
		}
		// 参数验证 ->end
		
		// 设置查询条件 ->start
		
		// 获取用户信息 ->start
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "请重新登录", null);
		}
		// 获取用户信息 ->end
		
		// 处理平台code->平台id ->start
		if(StringUtils.isEmpty(dto.getPlatformCode())) {
			dto.setPlatformCode("pc2b");
		}
		dto.setPlatformId(designPlanRecommendedServiceV2.getPlatformIdByPlatformCode(dto.getPlatformCode()));
		// 处理平台code->平台id ->start
		
		dto.setUserId(loginUser.getId());
		// 设置查询条件 ->end
		
		dto.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
		dto.setRecommendedType(DesignPlanRecommendedConstant.RECOMMENDEDTYPE_NOOPSYCHE);
		if(loginUser.getBusinessAdministrationId() != null) {
			// 2018-04-16 added by zhangwj 如果是经销商，则使用企业的ID。（原因：公开方案不会和经销商ID做关联，关联的都是企业ID）后续考虑直接在sso中处理，直接储存企业ID
			BaseCompany baseCompany = baseCompanyService.get(loginUser.getBusinessAdministrationId().intValue());
			dto.setCompanyId(Integer.valueOf(loginUser.getBusinessAdministrationId() + ""));
			if( baseCompany != null && baseCompany.getBusinessType() != null && baseCompany.getBusinessType().intValue() == 2 ){
				dto.setCompanyId(baseCompany.getPid());
			}
		}
		
		int total = designPlanRecommendedServiceV2.getPlanRecommendedListSizeByDTO(dto);
		
		List<DesignPlanRecommendedResult> designPlanRecommendedResultList = null;
		if(total > 0) {
			designPlanRecommendedResultList = designPlanRecommendedServiceV2.getPlanRecommendedListByDTO(dto);
		}
		
		return new ResponseEnvelope<DesignPlanRecommendedResult>(total, designPlanRecommendedResultList, dto.getMsgId());
	}
	
	/**
	 * 《发布界面》
	 * 方案发布回显功能
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/releaseDetails")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommended> releaseDetails (HttpServletRequest request, HttpServletResponse response){
		DesignPlanRecommended designPlanRecommended = null;
		String msgId = request.getParameter("msgId");
		String thumbId = request.getParameter("thumbId");
		String recommendedType = request.getParameter("recommendedType");
		 
		boolean valid = this.validPlanReleaseParam(msgId,thumbId,recommendedType);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		Integer sceneId = designPlanRenderSceneService.getIdByThumbId(Integer.parseInt(thumbId));
		DesignPlanRenderScene designPlanRenderScene = null;
		if(sceneId!=null && sceneId.intValue()>0){
			designPlanRenderScene = designPlanRenderSceneService.get(sceneId);
			if(designPlanRenderScene == null){
				return new ResponseEnvelope<DesignPlanRecommended>(false,"方案副本不存在,或者已经被删除",msgId);
			}
		}else{
			return new ResponseEnvelope<DesignPlanRecommended>(false,"方案副本不存在,或者已经被删除",msgId);
		}
		DesignPlanRecommended designPlanRecommendedSearch = new DesignPlanRecommended();
		designPlanRecommendedSearch.setPlanId(sceneId);
		designPlanRecommendedSearch.setRecommendedType(Integer.parseInt(recommendedType));
		designPlanRecommendedSearch.setIsDeleted(0);
		List<DesignPlanRecommended> resList = designPlanRecommendedServiceV2.getList(designPlanRecommendedSearch);
		if(resList!=null && resList.size()==1){
			designPlanRecommended = resList.get(0);
		}else{
			return new ResponseEnvelope<DesignPlanRecommended>(false,"数据错误",msgId);
		}
		return new ResponseEnvelope<DesignPlanRecommended>(designPlanRecommended,msgId,true);
		 
	}
	
	/**
	 * 《发布界面》
	 * zhaobl 已经选择 的品牌列表 接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/myBrandList")
	@ResponseBody
	public ResponseEnvelope<BaseBrand> myBrandList(HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String thumbId = request.getParameter("thumbId");
		boolean valid = this.validPlanReleaseParam(msgId,thumbId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		return designPlanRecommendedServiceV2.myBrandList(msgId,thumbId);
	}
	
	
	/**
	 * 已经选择 的品牌列表 删除接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/deletedBrand")
	@ResponseBody
	public ResponseEnvelope<DesignPlanBrand> deletedBrand(HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String designPlanBrandId = request.getParameter("designPlanBrandId");
		boolean valid = this.validPlanReleaseParam(msgId,designPlanBrandId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.deletedBrand(msgId,designPlanBrandId,loginUser);
		
 
	}
	
	/**
	 * 《发布界面》
	 * 发布用的品牌列表 接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/brandList")
	@ResponseBody
	public ResponseEnvelope<BaseBrand> brandList(@ModelAttribute("baseBrandSearch")BaseBrandSearch baseBrandSearch,
			HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		if(StringUtils.isBlank(msgId)){
			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
		}
		return designPlanRecommendedServiceV2.myBrandList(msgId,baseBrandSearch);
	}
	
	
	
	
	


	/**
	 * 发布
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/planRelease")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedResult> planRelease(HttpServletRequest request, HttpServletResponse response){
		String logPrefixFunction = logPrefixClass + "planRelease -> ";
		
		String msgId = request.getParameter("msgId");
		String brandIds = request.getParameter("brandIds");/*品牌ids*/
		String styleId = request.getParameter("styleId"); /*风格id*/
		String thumbId = request.getParameter("thumbId");	/*副本缩略图id*/
		String isRelease = request.getParameter("isRelease");/* 发布 状态*/
		String planNumber = request.getParameter("planNumber");/*方案编号*/
		String recommendedTypeStrParam = request.getParameter("recommendedType");/*方案类型*/
		
		if(StringUtils.isEmpty(styleId) || (Integer.parseInt(styleId) < 0)) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "方案风格不能为空", msgId);
		}
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser ==null || loginUser.getId() <= 0) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"登录超时,请重新登录",msgId);
		}
		
		/*logger.error(logPrefixFunction + loginUser.getRoles());*/
		
		// 参数recommendedType验证 ->start
		if(StringUtils.isEmpty(recommendedTypeStrParam)) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "方案类型不能为空", msgId);
		}
		
		List<String> recommendedTypeStrList = Utils.getListFromStr(recommendedTypeStrParam);
		List<Integer> recommendedTypeList = new ArrayList<Integer>();
		for(String recommendedTypeStr : recommendedTypeStrList) {
			Integer recommendedType = null;
			try {
				recommendedType = Integer.valueOf(recommendedTypeStr);
			}catch (Exception e) {
				logger.error(logPrefixFunction + "String(recommendedTypeStr) -> Integer failed; recommendedTypeStr = " + recommendedTypeStr);
				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "参数recommendedType错误", msgId);
			}
			
			if(recommendedType == null || 
					(
							DesignPlanRecommendedConstant.RECOMMENDEDTYPE_COMMON.intValue() != recommendedType.intValue()
							&& DesignPlanRecommendedConstant.RECOMMENDEDTYPE_NOOPSYCHE.intValue() != recommendedType.intValue()
							)) {
				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "参数recommendedType错误", msgId);
			}
			
			// 权限校验 ->start
			/*boolean hasPermission = designPlanRecommendedServiceV2.hasReleasePermissionParamRecommendedType(loginUser.getRoles(), recommendedType);*/
			String hasPermissionButton = Utils.getValue("app.hasPermissionButton", "true");
			if(StringUtils.equals(hasPermissionButton, "true")) {
				boolean hasPermission = designPlanRecommendedServiceV2.hasReleasePermissionParamRecommendedType(recommendedType);
				if(!hasPermission) {
					return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"no permission",msgId);
				}
			}
			// 权限校验 ->end
			
			recommendedTypeList.add(recommendedType);
		}
		
		
		// 参数recommendedType验证 ->end
		
		// 验证参数shelfStatus(智能/普通方案) ->start
		// update by huangsongbo 2018.4.8 添加方案类型(智能方案,普通方案)
		// 注释原因,普通方案/智能方案,由recommendedType控制
		/*String shelfStatus = request.getParameter("shelfStatus");
		if(StringUtils.isEmpty(shelfStatus)) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数shelfStatus不能为空",msgId);
		}
		List<String> shelfStatusList = Utils.getListFromStr(shelfStatus);
		for(String shelfStatusItem : shelfStatusList) {
			if(!StringUtils.equals(shelfStatusItem, ShelfStatusEnum.ONEKEY.getCode()) && !StringUtils.equals(shelfStatusItem, ShelfStatusEnum.DEFAULT.getCode())) {
				return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数shelfStatus只能为" + ShelfStatusEnum.ONEKEY.getCode() + "/" + ShelfStatusEnum.DEFAULT.getCode(),msgId);
			}
		}*/
		// 验证参数shelfStatus(智能/普通方案) ->end
		
		// 处理shelfStatus(DEFAULT -> "") ->start
		// 注释原因,普通方案/智能方案,由recommendedType控制
		/*if(StringUtils.equals(shelfStatus, ShelfStatusEnum.DEFAULT.getCode())) {
			shelfStatus = "";
		}*/
		// 处理shelfStatus(DEFAULT -> "") ->end
		
		boolean valid = this.validPlanReleaseParam(styleId,msgId,thumbId,isRelease);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数不完整",msgId);
		}
		/*装有所有参数的model*/
		ReleaseDesignPlanModel model = new ReleaseDesignPlanModel();
		model.setStyleId(styleId);
		model.setIsRelease(isRelease);
		model.setThumbId(thumbId);
		
		model.setBrandIds(brandIds);
		model.setPlanNumber(planNumber);
		/*model.setShelfStatus(shelfStatus);*/
		
		ResponseEnvelope<DesignPlanRecommendedResult> result = null;
		for(Integer recommendedType : recommendedTypeList) {
			model.setRecommendedType(recommendedType + "");
			result = designPlanRecommendedServiceV2.recommendDesignPlan(model, msgId, loginUser);
			if(result.isSuccess()) {
				
			}else {
				return result;
			}
		}
		return result;
	}
	
	
	/**
	 * 获取设计方案风格 接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getDesignStyleList")
	@ResponseBody
	public ResponseEnvelope<BaseProductStyle> getDesignStyleList(HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String planRecommendedId = request.getParameter("planRecommendedId");
		String thumbId = request.getParameter("thumbId");
		String houseType = request.getParameter("houseType");
		boolean valid = this.validPlanReleaseParam(msgId);
		if(!valid) {
			return new ResponseEnvelope<>(false,"参数不完整",msgId);
		}
		return designPlanRecommendedServiceV2.getDesignStyleList(msgId,thumbId,planRecommendedId,houseType);
	}
	
	
	
	
	/**
	 * 获取设计方案风格 接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getDesignStyleListNew")
	@ResponseBody
	public ResponseEnvelope<BaseProductStyle> getDesignStyleListNew(HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String planRecommendedId = request.getParameter("planRecommendedId");
		String thumbId = request.getParameter("thumbId");
		String houseType = request.getParameter("houseType");
		boolean valid = this.validPlanReleaseParam(msgId);
		if(!valid) {
			return new ResponseEnvelope<BaseProductStyle>(false,"参数不完整",msgId);
		}
		return designPlanRecommendedServiceV2.getDesignStyleListNew(msgId,thumbId,planRecommendedId,houseType);
	}
	
	
	
 
	/**
	 * 审核列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/designPlanRecommendedCheckList")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedResult> designPlanRecommendedCheckList(HttpServletRequest request, HttpServletResponse response){
		String houseType = request.getParameter("houseType");
		String areaValue = request.getParameter("areaValue");
		String msgId =  request.getParameter("msgId");
		String planName =  request.getParameter("planName");
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String livingName = request.getParameter("livingName");
		String creator = request.getParameter("creator");		//搜索条件：创建者
		String brandName = request.getParameter("brandName");	//搜索条件：品牌
		String designRecommendedStyleId = request.getParameter("designRecommendedStyleId");
		boolean valid = this.validPlanReleaseParam(msgId);
		if(StringUtils.isNotEmpty(livingName)) {
			livingName = livingName.trim();
			creator = livingName;
			brandName = livingName;
			planName = livingName;
		}
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		int limit_int = -1;
		int start_int = -1;
		if(StringUtils.isNotEmpty(limit)){
			limit_int = Integer.parseInt(limit);
		}
		if(StringUtils.isNotEmpty(start)){
			start_int = Integer.parseInt(start);
		}
		PlanRecommendedListModel model = new PlanRecommendedListModel();
		model.setDesignRecommendedStyleId(designRecommendedStyleId);
		model.setHouseType(houseType);
		model.setAreaValue(areaValue);
		model.setMsgId(msgId);
		model.setPlanName(planName);
		model.setLimit(limit_int);
		model.setStart(start_int);
		model.setLivingName(livingName);
		model.setCreator(creator);
		model.setBrandName(brandName);
		model.setLoginUser(loginUser);
		return designPlanRecommendedServiceV2.designPlanRecommendedCheckList(model);
	}
	
	
	/**
	 * 审核接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/designPlanRecommendedCheck")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommended> designPlanRecommendedCheck(HttpServletRequest request, HttpServletResponse response){
		String msgId =  request.getParameter("msgId");
		String planRecommendedId =  request.getParameter("planRecommendedId");
		String isReleaseState =  request.getParameter("isReleaseState");
		String failCause =  request.getParameter("failCause");
		boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId,isReleaseState);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.designPlanRecommendedCheck(msgId,planRecommendedId,loginUser,isReleaseState,failCause);
	}
 
 
	/**
	 * 方案推荐详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/designPlanRecommendedDetails")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommended> designPlanRecommendedDetails(HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String planRecommendedId = request.getParameter("planRecommendedId");
		boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.designPlanRecommendedDetails(msgId,planRecommendedId,loginUser);
	}
	
	
	/**
	 * 方案推荐详情 产品列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getDesignPlanRecommendedProductList")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedProductResult> getDesignPlanRecommendedProductList(HttpServletRequest request, HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String planRecommendedId = request.getParameter("planRecommendedId");
		boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommendedProductResult>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.getDesignPlanRecommendedProductList(msgId,planRecommendedId,loginUser);
	}
	
	/**
	 * 取消发布接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cancelRelease")
	@ResponseBody
	 public ResponseEnvelope<DesignPlanRecommended> cancelRelease(HttpServletRequest request, HttpServletResponse response){
		 String msgId = request.getParameter("msgId");
		 String planRecommendedId = request.getParameter("planRecommendedId");
		 boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId);
		 if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		 }
		 LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		 return designPlanRecommendedServiceV2.cancelRelease(msgId,loginUser,planRecommendedId);	
	 }
	

	/**
	 * 提交审核
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/submitCheck")
	@ResponseBody
	public  ResponseEnvelope<DesignPlanRecommended>  submitCheck(HttpServletRequest request, HttpServletResponse response){
		String logPrefixFunction = logPrefixClass + "submitCheck -> ";
		
		 String msgId = request.getParameter("msgId");
		 String planRecommendedId = request.getParameter("planRecommendedId");
		 String spaceAreas = request.getParameter("spaceAreas");
		 
		 // 添加可编辑信息(recommendedType,styleId,brandIds) ->start
		 // update by huangsongbo 2018.4.8
		 // 品牌id
		 String brandIds = request.getParameter("brandIds");
		 // 设计方案风格
		 String styleId = request.getParameter("styleId");
		 
		 /*String recommendedTypeStr = request.getParameter("recommendedType");*/
		 /*方案类型*/
			
		// 参数recommendedType验证 ->start
		/*Integer recommendedType = null;
		if(StringUtils.isEmpty(recommendedTypeStr)) {
			return new ResponseEnvelope<DesignPlanRecommended>(false, "参数recommendedType不能为空", msgId);
		}else {
			try {
				recommendedType = Integer.valueOf(recommendedTypeStr);
			}catch (Exception e) {
				logger.error(logPrefixFunction + "String(recommendedTypeStr) -> Integer failed; recommendedTypeStr = " + recommendedTypeStr);
				return new ResponseEnvelope<DesignPlanRecommended>(false, "参数recommendedType错误", msgId);
			}
			
			if(recommendedType == null || 
					(
							DesignPlanRecommendedConstant.RECOMMENDEDTYPE_COMMON.intValue() != recommendedType.intValue()
							&& DesignPlanRecommendedConstant.RECOMMENDEDTYPE_NOOPSYCHE.intValue() != recommendedType.intValue()
							)) {
				return new ResponseEnvelope<DesignPlanRecommended>(false, "参数recommendedType错误", msgId);
			}
		}*/
		// 参数recommendedType验证 ->end
		 
		 // 添加可编辑信息(shelfStatus,styleId,brandIds) ->end
		 
		 boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId,spaceAreas);
		 if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		 }
		 LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		 return designPlanRecommendedServiceV2.submitCheck(msgId, loginUser, planRecommendedId, spaceAreas, brandIds, styleId
				 /*, recommendedType*/
				 );
	}
	
	
	
	/**
	 * 设计方案封面设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateCoverPic")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommended> updateCoverPic(HttpServletRequest request,
			HttpServletResponse response) {
		String picId = request.getParameter("picId");
		String planRecommendedId = request.getParameter("planRecommendedId");
		String msgId = request.getParameter("msgId");
		boolean valid = this.validPlanReleaseParam(msgId,picId,planRecommendedId);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.updateCoverPic(msgId,loginUser,planRecommendedId,picId);
	}
	
	
	
	
	/**
	 * 修改设计方案
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updatePlanRecommended")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommended> updatePlanRecommended(HttpServletRequest request,
			HttpServletResponse response){
		String remark = request.getParameter("remark");
		String planName = request.getParameter("planName");
		if(StringUtils.isEmpty(planName)){
			planName = request.getParameter("plan_name");
		}
		String planRecommendedId = request.getParameter("planRecommendedId");
		String msgId = request.getParameter("msgId");
		boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.updatePlanRecommended(msgId,loginUser,planRecommendedId,remark,planName);
	}
	
	
	/**
	 * 参数完整性判断
	 * @param args
	 * @return
	 */
	private boolean validPlanReleaseParam(String... args) {
		boolean result = true;
		for(String arg :args) {
			if(StringUtils.isEmpty(arg)) {
				result = false;
				return result;
			}
		}
		return result;
	}
	
 
	
	
 
	/**
	 * 详情查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/detailsSee")
	@ResponseBody
	public ResponseEnvelope<ResRenderPic> detailsSee(HttpServletRequest request,
			HttpServletResponse response){
		String detailsSeeType = request.getParameter("detailsSeeType");
		String picId = request.getParameter("smallPicId");
		String msgId = request.getParameter("msgId");
		boolean valid = this.validPlanReleaseParam(msgId,picId);
		if(!valid) {
			return new ResponseEnvelope<ResRenderPic>(false,"参数不完整",msgId);
		}
		if(StringUtils.isEmpty(detailsSeeType)){
			detailsSeeType = "";
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanRecommendedServiceV2.detailsSee(msgId,picId,detailsSeeType,loginUser);
	}

	/**
	 * 获取户型空间面积列表（提交审核时弹出该列表供设计师选择）
	 * @author xiaoxc
	 * @param spaceFunctionId
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSpaceAreaList")
	@ResponseBody
	public ResponseEnvelope getSpaceAreaList(Integer spaceFunctionId,String msgId, HttpServletRequest request){
		boolean valid = this.validPlanReleaseParam(msgId,spaceFunctionId+"");
		if(!valid) {
			return new ResponseEnvelope(false,"参数不完整",msgId);
		}
		return designPlanRecommendedServiceV2.getSpaceAreaList(spaceFunctionId,msgId);
	}
	
 
	@RequestMapping("/planFormalIsReleaseCheck")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRecommendedResult> planFormalIsReleaseCheck (HttpServletRequest request){
		String msgId = request.getParameter("msgId");
		String thumbId = request.getParameter("thumbId");
		String recommendedType = request.getParameter("recommendedType");
		boolean valid = this.validPlanReleaseParam(msgId,thumbId);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(true,"参数不完整",msgId);  //应app 要求这里改成true
		}
		return designPlanRecommendedServiceV2.planFormalIsReleaseCheck(msgId,thumbId,recommendedType);
	}
	
	/**
	 * 推荐方案列表 获得推荐方案审核状态
	 * @param request
	 * @param msgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getRecommendedPlanCheckState")
	@ResponseBody
	public ResponseEnvelope getRecommendedPlanCheckState(HttpServletRequest request, String msgId){
	  return designPlanRecommendedServiceV2.getRecommendedPlanCheckState(msgId);
	}

	/**
	 * 更新所有推荐卫生间方案的空间布局类型
	 * author xiaoxc
	 * date 20171220
	 * @return
	 */
	@RequestMapping("/updateRecommendedPlanSpaceLayoutType")
	@ResponseBody
	public ResponseEnvelope updateRecommendedPlanSpaceLayoutType(){
		logger.error("开始更新推荐方案空间布局类型。。。。");
		StringBuilder builder1 = new StringBuilder("[");
		StringBuilder builder2 = new StringBuilder("[");
		int count = 0;
		int temp = 0;
		//TODO 获取所有推荐卫生间方案id
		List<Integer> idList = designPlanRecommendedServiceV2.findRecommendedPlanIdListBySpaceFunctionId(SysDictionaryConstant.house_Type_toilet_value);
		if (Lists.isNotEmpty(idList)) {
			logger.error("卫生间方案共size = "+idList.size());
			for (Integer id : idList) {
				logger.error("开始更新第"+temp+++"条");
				//TODO 获取推荐方案产品小分类keys
				List<String> productKeylist = designPlanRecommendedProductServiceV2.findListByRecommendedPlanId(id);
				if (Lists.isNotEmpty(productKeylist)) {
					//TODO 获取空间布局类型
					String spaceLayoutType = designPlanRecommendedServiceV2.getLayoutType(productKeylist,DesignPlanConstants.EnumDesignType.PLAN.toString());
					if (StringUtils.isNotEmpty(spaceLayoutType)) {
						DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();
						designPlanRecommended.setId(id);
						designPlanRecommended.setSpaceLayoutType(spaceLayoutType);
						//TODO 更新推荐方案空间布局类型
						designPlanRecommendedServiceV2.update(designPlanRecommended);
						builder1.append(id+",");
						count ++;
						continue;
					}
				}
				builder2.append(id+",");
			}
		}
		builder1.append("]").append(":"+count+"个推荐方案已更新\\n");
		builder2.append("]").append(":未更新");
		String message = "共"+idList.size()+"条\\n "+builder1.toString() + builder2.toString();
		return new ResponseEnvelope(true,message);
	}


	/**
	 * 更新推荐方案组合标识
	 * @return
	 */
	@RequestMapping("/updateGroupProductUniqueId")
	@ResponseBody
	public ResponseEnvelope updateRecommendedPlanProductGroupProductUniqueId() {
		logger.error("开始更新---------");
		List<DesignPlanRecommendedProduct> recommendedProductList = designPlanRecommendedProductServiceV2.findNoGroupProductUniqueIdList();
		if (recommendedProductList != null && recommendedProductList.size() > 0) {
			logger.error("recommendedProductList.size() = " + recommendedProductList.size());
		} else {
			return new ResponseEnvelope(true,"无需更新的推荐方案");
		}
		for (DesignPlanRecommendedProduct recommendedProduct : recommendedProductList) {
			List<DesignPlanRecommendedProduct> recommendedGroupProductList =
					designPlanRecommendedProductServiceV2.findRecommendedGroupProductByPlanRecommendedId(recommendedProduct.getPlanRecommendedId());
			if (recommendedGroupProductList != null && recommendedGroupProductList.size() > 0) {
				//TODO 组合去重复
				Set<RecommendedProductModel> set = new HashSet();
				for (DesignPlanRecommendedProduct recommendedGroupProduct : recommendedGroupProductList) {
					RecommendedProductModel recommended = new RecommendedProductModel();
					recommended.setPlanGroupId(recommendedGroupProduct.getPlanGroupId());
					recommended.setGroupId(recommendedGroupProduct.getProductGroupId());
					set.add(recommended);
				}
				if (set != null && set.size() > 0) {
					for (RecommendedProductModel recommendedProduct1 : set) {
						Integer groupId = recommendedProduct1.getGroupId();
						String planGroupId = recommendedProduct1.getPlanGroupId();
						String filePath = groupProductService.getGroupConfigByGroupId(groupId);
						if (StringUtils.isEmpty(filePath)) {
							logger.error("组合配置文件路径为空，组合ID："+groupId);
							continue;
						}
						//读取配置文件中的内容
						String fileContext = FileUploadUtils.getFileContext(Utils.getValue("app.upload.root","D\\:\\\\nork\\\\resources")+filePath);
						if (StringUtils.isEmpty(fileContext)) {
							logger.error("推荐方案产品ID="+recommendedProduct.getId()+"组合配置文件内容为空!");
							logger.error("组合配置文件路径："+filePath);
							continue;
						}
						//将配置文件中的内容转换成json格式
						JSONObject jsonObject = JSONObject.fromObject(fileContext);
						JSONArray jsonArray = (JSONArray) jsonObject.get("productList");
						JsonConfig jsonConfig = getJsonConfig();
						List<GroupProductJsonConfig> productList = (List<GroupProductJsonConfig>) JSONArray.toCollection(jsonArray, jsonConfig);
						if (productList != null && productList.size() > 0) {
							for (GroupProductJsonConfig groupConfig : productList) {
								for (DesignPlanRecommendedProduct recommendedGroupProduct : recommendedGroupProductList) {
									//TODO 同组合同方案组合标识一致
									if (groupId.equals(recommendedGroupProduct.getProductGroupId())
											&& planGroupId.equals(recommendedGroupProduct.getPlanGroupId())) {
										//TODO 组合配置文件产品编码和推荐方案产品编码一致则更新
										if (groupConfig.getProductName().equals(recommendedGroupProduct.getProductCode())) {
											DesignPlanRecommendedProduct dprp = designPlanRecommendedProductServiceV2.get(recommendedGroupProduct.getId());
											if (StringUtils.isNotEmpty(dprp.getGroupProductUniqueId())) {
												continue;
											}
											DesignPlanRecommendedProduct reProduct = new DesignPlanRecommendedProduct();
											reProduct.setId(recommendedGroupProduct.getId());
											reProduct.setGroupProductUniqueId(groupConfig.getPlanProductId()+"");
											reProduct.setRemark("xiaoxc_updateUniqueId");
											designPlanRecommendedProductServiceV2.update(reProduct);
											break;
										}
									}
								}
							}
						}
					}
				}
			}

		}
		return null;
	}


	public static JsonConfig getJsonConfig(){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(GroupProductJsonConfig.class);

		//处理json中key的首字母大写情况
		jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String s) {
				char[] chars = s.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		//过滤在json中Entity没有的属性
		jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
		return jsonConfig;
	}

	/**
	 * 内部方案/外部方案/共享方案列表接口
	 * 
	 * @author huangsongbo
	 * @date 2018.3.28
	 * @param dto
	 * @return
	 */
	@RequestMapping("/franchiserPlanList")
	@ResponseBody
	public ResponseEnvelope<FranchiserPlanListVO> franchiserPlanList(
			FranchiserPlanListDTO dto,
			HttpServletRequest request
			) {
		String logPrefixFunction = logPrefixClass + "franchiserPlanList -> ";
		
		// 参数验证 ->start
		if(dto == null) {
			return new ResponseEnvelope<FranchiserPlanListVO>(false, "dto = null", null);
		}
		if(PlanSourceEnum.valueOf(dto.getPlanSource()) == null) {
			return new ResponseEnvelope<FranchiserPlanListVO>(false, "planSource不支持类型", null);
		}
		// 参数验证 ->end
		
		// 获取用户信息 ->start
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null) {
			return new ResponseEnvelope<FranchiserPlanListVO>(false, "请重新登录", null);
		}
		// 获取用户信息 ->end
		
		// 设置查询条件 ->start
		SysUser sysUser = sysUserService.get(loginUser.getId());
		if(sysUser == null) {
			logger.error(logPrefixFunction + "sysUser = null;sysUserId = " + loginUser.getId());
			return new ResponseEnvelope<FranchiserPlanListVO>(false, "请重新登录", null);
		}

		// 2018-04-16 added by zhangwj 如果是经销商，则使用企业的ID。（原因：推荐方案表中的数据不会和经销商ID做关联，关联的都是企业ID）后续考虑直接在sso中处理，直接储存企业ID
		Integer companyId = sysUser.getBusinessAdministrationId();
		if( sysUser.getBusinessAdministrationId() != null ){
			BaseCompany baseCompany = baseCompanyService.get(sysUser.getBusinessAdministrationId());
			if( baseCompany != null && baseCompany.getBusinessType() != null && baseCompany.getBusinessType().intValue() == 2 ){
				companyId = baseCompany.getPid();
			}
		}

		dto = this.dealWithParamForFunctionFranchiserPlanList(dto, companyId);
		// 设置查询条件 ->end
		
		int total = designPlanRecommendedServiceV2.getFranchiserPlanListSizeByDTO(dto);
		List<FranchiserPlanListVO> franchiserPlanListVOList = null;
		if(total > 0) {
			franchiserPlanListVOList = designPlanRecommendedServiceV2.getFranchiserPlanListByDTO(dto);
		}
		return new ResponseEnvelope<FranchiserPlanListVO>(total, franchiserPlanListVOList, dto.getMsgId());
	}

	private FranchiserPlanListDTO dealWithParamForFunctionFranchiserPlanList(FranchiserPlanListDTO dto, Integer companyId) {
		String logPrefixFunction = logPrefixClass + "dealWithParamForFunctionFranchiserPlanList -> ";
		
		// 参数验证 ->start
		if(dto == null) {
			logger.error(logPrefixFunction + "dto = null");
			return new FranchiserPlanListDTO();
		}
		// 参数验证 ->end
		
		dto.setCompanyId(companyId);
		//2018.04.23 add by chenm  按照方案发布顺序逆序排序
        dto.setOrder("release_time");
        dto.setOrderNum("desc");
        
		// 处理共享方案查询条件 ->start
		// 特殊逻辑:首先,是共享方案,其次,非同行公司
		/*if(StringUtils.equals(PlanSourceEnum.share.getCode(), dto.getPlanSource())) {
			List<Integer> baseCompanyIdList = baseCompanyService.getCompanyIdListDifferentIndustry(companyId);
			logger.debug(logPrefixFunction + "baseCompanyIdList.size:" + (baseCompanyIdList == null ? 0 : baseCompanyIdList.size()));
			dto.setCompanyId(null);
			dto.setBaseCompanyIdList(baseCompanyIdList);
		}*/
		// 处理共享方案查询条件 ->end
		
		// 处理查询条件shelfStatus(智能方案/普通方案) ->start
		String shelfStatus = dto.getShelfStatus();
		if(StringUtils.isNotEmpty(shelfStatus)) {
			if(shelfStatus.startsWith("!")) {
				shelfStatus = shelfStatus.substring(1, shelfStatus.length());
				dto.setShelfStatusNotLike(shelfStatus);
			}else {
				dto.setShelfStatusLike(shelfStatus);
			}
		}
		// 处理查询条件shelfStatus(智能方案/普通方案) ->end
		
		// 处理查询条件platformType ->start
		List<String> platformTypeList = null;
		if(StringUtils.isNotEmpty(dto.getPlatformType())) {
			platformTypeList = Utils.getListFromStr(dto.getPlatformType());
			if(Lists.isNotEmpty(platformTypeList)) {
				dto.setPlatformTypeList(platformTypeList);
			}
		}
		
		switch (PlatformTypeEnum.getEnumByCode(dto.getPlatformType())) {
		case toB:
			dto.setPlatformTypeButton(true);
			break;
		case toC:
			dto.setPlatformTypeButton(true);
			break;
		case toBAndToC:
			dto.setPlatformTypeButton(true);
			break;
		case undistributed:
			dto.setPlatformTypeList(Arrays.asList(new String[] {PlatformTypeEnum.toB.getCode(), PlatformTypeEnum.toC.getCode()}));
			dto.setPlatformTypeButton(false);
			break;
		default:
			break;
		}
		// 处理查询条件platformType ->end
		
		// 处理平台code->平台id ->start
		/*if(StringUtils.isEmpty(dto.getPlatformCode())) {
			dto.setPlatformCode("pc2b");
		}
		dto.setPlatformId(designPlanRecommendedServiceV2.getPlatformIdByPlatformCode(dto.getPlatformCode()));*/
		// 处理平台code->平台id ->start
		
		dto.setIsRelease(RecommendedDecorateState.IS_RELEASEING);
		
		return dto;
	}
	
}