package com.nork.design.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
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
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedCheck;
import com.nork.design.model.DesignPlanRecommendedProductResult;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.PlanRecommendedListModel;
import com.nork.design.model.ReleaseDesignPlanModel;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;


/**
 * 设计方案推荐
 * 2017-0710 zhaobl
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanRecommendedV2")
public class WebDesignPlanRecommendedControllerV2 {
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
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"请重新登录！",msgId);
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
		
		return designPlanRecommendedServiceV2.getPlanRecommendedList(model);
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
		String msgId = request.getParameter("msgId");
		String brandIds = request.getParameter("brandIds");/*品牌ids*/
		String styleId = request.getParameter("styleId"); /*风格id*/
		String thumbId = request.getParameter("thumbId");	/*副本缩略图id*/
		String isRelease = request.getParameter("isRelease");/* 发布 状态*/
		String planNumber = request.getParameter("planNumber");/*方案编号*/
		String recommendedType = request.getParameter("recommendedType");/*方案类型*/
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser ==null || loginUser.getId() <= 0) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"登录超时,请重新登录",msgId);
		}
		boolean valid = this.validPlanReleaseParam(styleId,msgId,thumbId,isRelease,recommendedType);
		if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数不完整",msgId);
		}
		/*装有所有参数的model*/
		ReleaseDesignPlanModel model = new ReleaseDesignPlanModel();
		model.setStyleId(styleId);
		model.setIsRelease(isRelease);
		model.setThumbId(thumbId);
		model.setRecommendedType(recommendedType);
		model.setBrandIds(brandIds);
		model.setPlanNumber(planNumber);
		return designPlanRecommendedServiceV2.recommendDesignPlan(model, msgId, loginUser);
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
		 String msgId = request.getParameter("msgId");
		 String planRecommendedId = request.getParameter("planRecommendedId");
		 String spaceAreas = request.getParameter("spaceAreas");
		 boolean valid = this.validPlanReleaseParam(msgId,planRecommendedId,spaceAreas);
		 if(!valid) {
			return new ResponseEnvelope<DesignPlanRecommended>(false,"参数不完整",msgId);
		 }
		 LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		 return designPlanRecommendedServiceV2.submitCheck(msgId,loginUser,planRecommendedId,spaceAreas);
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
}
