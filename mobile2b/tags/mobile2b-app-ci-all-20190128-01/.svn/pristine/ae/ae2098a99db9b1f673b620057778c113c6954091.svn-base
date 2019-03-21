//package com.nork.design.controller.web;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import DesignPlan.DesignPlanRelease;
//
//import com.nork.common.constant.util.SystemCommonUtil;
//import com.nork.common.model.LoginUser;
//import com.nork.common.model.ResponseEnvelope;
//import com.nork.common.properties.ResProperties;
//import com.nork.common.util.Tools;
//import com.nork.common.util.Utils;
//import com.nork.common.util.QRCode.MatrixToImageWriter;
//import com.nork.design.dao.DesignPlanAutoRenderMapper;
//import com.nork.design.model.DesignPlan;
//import com.nork.design.model.DesignPlanBrand;
//import com.nork.design.model.DesignPlanCheck;
//import com.nork.design.model.DesignPlanRecommendedResult;
//import com.nork.design.model.DesignTemplet;
//import com.nork.design.model.ReleaseDesignPlanModel;
//import com.nork.design.model.RenderPicInfo;
//import com.nork.design.service.DesignPlanAutoRenderService;
//import com.nork.design.service.DesignPlanBrandService;
//import com.nork.design.service.DesignPlanCheckService;
//import com.nork.design.service.DesignPlanProductService;
//import com.nork.design.service.DesignPlanRecommendedService;
//import com.nork.design.service.DesignPlanService;
//import com.nork.design.service.DesignTempletService;
//import com.nork.design.service.impl.NewDesignPlanRecommendServiceImpl;
//import com.nork.home.model.SpaceCommon;
//import com.nork.home.service.SpaceCommonService;
//import com.nork.product.model.BaseBrand;
//import com.nork.product.model.BaseProductStyle;
//import com.nork.product.model.search.BaseBrandSearch;
//import com.nork.product.model.search.BaseProductStyleSearch;
//import com.nork.product.service.AuthorizedConfigService;
//import com.nork.product.service.BaseBrandService;
//import com.nork.product.service.BaseProductService;
//import com.nork.product.service.BaseProductStyleService;
//import com.nork.render.model.RenderTypeCode;
//import com.nork.system.model.ResRenderPic;
//import com.nork.system.model.SysDictionary;
//import com.nork.system.model.SysUser;
//import com.nork.system.model.SysUserPlanRecommended;
//import com.nork.system.model.search.ResRenderPicSearch;
//import com.nork.system.service.ResRenderPicService;
//import com.nork.system.service.SysDictionaryService;
//import com.nork.system.service.SysRoleService;
//import com.nork.system.service.SysUserRoleService;
//import com.nork.system.service.SysUserService;
// 
// 
//
// /**
//  * 设计方案推荐
//  * 2017-05-03 zhaobl
//  * @author Administrator
//  *
//  */
//@Controller
//@RequestMapping("/{style}/web/design/designPlanRecommended")
//public class WebDesignPlanRecommendedController {
//	/*** 获取配置文件 tmg.properties */
//	private static Logger logger = Logger.getLogger(WebDesignPlanRecommendedController.class);
//	
//	@Autowired
//	DesignPlanAutoRenderService designPlanAutoRenderServiceImpl;
//	
//	@Autowired
//	DesignPlanBrandService designPlanBrandService;
//	
//	@Autowired
//	AuthorizedConfigService authorizedConfigService;
//	
//	@Autowired
//	DesignPlanService designPlanService;
//	
//	@Autowired
//	SysDictionaryService sysDictionaryService;
//	
//	@Autowired
//	ResRenderPicService resRenderPicService;
//	
//	@Autowired
//	BaseBrandService baseBrandService;
//	
//	@Autowired
//	BaseProductStyleService baseProductStyleService;
//	
//	@Autowired
//	DesignTempletService designTempletService;
//	
//	@Autowired
//	SpaceCommonService spaceCommonService;
//	
//	@Autowired
//	DesignPlanProductService designPlanProductService;
//	
//	@Autowired
//	BaseProductService baseProductService;
//	
//	@Autowired
//	DesignPlanCheckService designPlanCheckService;
//	
//	@Autowired
//	SysUserService sysUserService;
//	
//	@Autowired
//	SysRoleService sysRoleService;
//	
//	@Autowired
//	SysUserRoleService sysUserRoleService;
//	
//	@Autowired
//	DesignPlanRecommendedService designPlanRecommendedService;
//	/////////////////////////
//	@Autowired
//	NewDesignPlanRecommendServiceImpl newDesignPlanRecommendServiceImpl;
//	
//	
//	/**
//	 * 方案推荐 列表 数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/planRecommendedList")
//	@ResponseBody
//	public Object planRecommendedList(HttpServletRequest request, HttpServletResponse response){
//		String msgId = request.getParameter("msgId");
//		String houseType = request.getParameter("houseType");
//		String livingName = request.getParameter("livingName");
//		String areaValue = request.getParameter("areaValue");
//		String designRecommendedStyleId = request.getParameter("designRecommendedStyleId");
//		String isMainList = request.getParameter("isMainList");  
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"缺少参数msgId",msgId);
//		}
//		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
//		return newDesignPlanRecommendServiceImpl.getPlanRecommendedList(houseType, livingName, areaValue, designRecommendedStyleId, isMainList, msgId, loginUser);
//	}
//	
//	private boolean validPlanReleaseParam(String... args) {
//		boolean result = true;
//		for(String arg :args) {
//			if(StringUtils.isBlank(arg)) {
//				result = false;
//				return result;
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * Update by Steve on 2017-06-28
//	 * @name 方案发布
//	 *  发布规则：
//	 * 	1.断是否拥有设计方案拷贝 和 发布的权限
//	 * 	2.发布需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/planRelease")
//	@ResponseBody
//	public ResponseEnvelope<DesignPlanRecommendedResult> planRelease(HttpServletRequest request, HttpServletResponse response){
//		String msgId = request.getParameter("msgId");
//		String brandIds = request.getParameter("brandIds");  /*品牌ids*/
//		String checkIds = request.getParameter("checkIds");  /*审核人员ids*/
//		String styleId = request.getParameter("styleId"); /*风格id*/
//		String planId = request.getParameter("planId");/*方案id*/
//		String isRelease = request.getParameter("isRelease");/*设计方案是否发布 0 否 1是*/
//		String isRecommended = request.getParameter("isRecommended");/*是否默认推荐  1 默认 0 非默认*/
//		String isDefaultDecorate = request.getParameter("isDefaultDecorate");/*是否支持一键装修  1 支持  0不支持*/
//		String planNumber = request.getParameter("planNumber");/*方案编号*/
//		
//		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
//		if(loginUser==null) {
//			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"登录超时,请重新登录",msgId);
//		}
//		boolean valid = validPlanReleaseParam(msgId,planId,isRelease);
//		if(!valid) {
//			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数不完整",msgId);
//		}
//		ReleaseDesignPlanModel model = new ReleaseDesignPlanModel();
//		model.setBrandIds(brandIds);
//		model.setCheckIds(checkIds);
//		model.setIsDefaultDecorate(isDefaultDecorate);
//		model.setIsRecommended(isRecommended);
//		model.setIsRelease(isRelease);
//		model.setPlanId(planId);
//		model.setPlanNumber(planNumber);
//		model.setStyleId(styleId);
//		if(StringUtils.isNoneBlank(planId)){
//			DesignPlan plan = designPlanService.get(Integer.valueOf(planId));
//			designPlanAutoRenderServiceImpl.createTaskListByDesignPlan(plan);
//		}
//		
//		return newDesignPlanRecommendServiceImpl.recommendDesignPlan(model, msgId, loginUser);
//	}
//	
//	
//	
//	/**
//	 * 发布用的品牌列表 接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/brandList")
//	@ResponseBody
//	public Object brandList(@ModelAttribute("baseBrandSearch")BaseBrandSearch baseBrandSearch,HttpServletRequest request, HttpServletResponse response){
//		
//		Integer total = 0;
//		List <BaseBrand> brandList = null;
//		List <BaseBrand> list = new ArrayList<BaseBrand>();
//		String msgId = request.getParameter("msgId");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
//		}
//		/*在里面新增一个选择所有*/
//		BaseBrand baseBrand = new BaseBrand();
//		baseBrand.setBrandName("所有品牌");
//		baseBrand.setId(-1);
//		list.add(baseBrand);
//		try{
//			baseBrandSearch.setIsDeleted(0);
//			total = baseBrandService.getCount(baseBrandSearch);	
//			baseBrandSearch.setLimit(total);
//			if(total!=null&&total.intValue()>0){
//				brandList= baseBrandService.getPaginatedList(baseBrandSearch);
//				list.addAll(brandList);
//				total = total + 1;
//			}
//		}catch (Exception e) {
//			logger.error("brandList  methods the error  :" + e);
//			return new ResponseEnvelope<>(false,"数据错误",msgId);
//		}
//		return new ResponseEnvelope<>(total,list,msgId);
//	}
//	
//	
//	/**
//	 * 已经选择 的品牌列表 接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/myBrandList")
//	@ResponseBody
//	public Object myBrandList(HttpServletRequest request, HttpServletResponse response){
//		List<BaseBrand>list = null;
//		String msgId = request.getParameter("msgId");
//		String planId = request.getParameter("planId");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
//		}
//		if(StringUtils.isBlank(planId)){
//			return new ResponseEnvelope<>(false,"缺少参数planId",msgId);
//		}
//		try{
//			DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
//			if(designPlan==null){
//				return new ResponseEnvelope<>(false,"该设计方案不存在或被删除planId："+planId,msgId);
//			}
//			//删除产品将会把设计方案推荐  改成未发布
//			/*if(designPlan.getIsRelease()!=null&&designPlan.getIsRelease().intValue() == 1){
//				DesignPlan newDesignPlan = new DesignPlan();
//				newDesignPlan.setId(designPlan.getId());
//				newDesignPlan.setIsRelease(DesignPlanRelease.stateless);
//				designPlanService.update(newDesignPlan);
//			}*/
//			list = designPlanBrandService.getListByPlanId(Integer.parseInt(planId));
//			if(list!=null&&list.size()>0){
//				for (BaseBrand baseBrand : list) {
//					Integer brandAssociatedId = baseBrand.getBrandAssociatedId();
//					if(brandAssociatedId!=null&&brandAssociatedId.intValue()==-1){
//						baseBrand.setBrandName("所有品牌");
//					}
//				}
//			}
//		}catch (Exception e) {
//			logger.error("myBrandList  methods the error  :" + e);
//			return new ResponseEnvelope<>(false,"数据错误",msgId);
//		}
//		return new ResponseEnvelope<>(list.size(),list,msgId);
//	}
//	
//	
//	
//	/**
//	 * 已经选择 的品牌列表 删除接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/deletedBrand")
//	@ResponseBody
//	public Object deletedBrand(HttpServletRequest request, HttpServletResponse response){
//		String msgId = request.getParameter("msgId");
//		String designPlanBrandId = request.getParameter("designPlanBrandId");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
//		}
//		if(StringUtils.isBlank(designPlanBrandId)){
//			return new ResponseEnvelope<>(false,"缺少参数designPlanBrandId",msgId);
//		}
//		try{
//			DesignPlanBrand designPlanBrand = designPlanBrandService.get(Integer.parseInt(designPlanBrandId));
//			if(designPlanBrand==null){
//				return new ResponseEnvelope<>(false,"designPlanBrandId:" + designPlanBrandId + "不存在",msgId);
//			}
//			Integer planId = designPlanBrand.getPlanId();
//			if(planId == null||planId.intValue()<=0){
//				return new ResponseEnvelope<>(false,"数据错误",msgId);
//			}
//			DesignPlan designPlan = null;
//			designPlan = designPlanService.get(planId);
//			if(designPlan == null){
//				return new ResponseEnvelope<>(false,"该设计方案已经被删除，请刷新我的设计页面",msgId);
//			}
//			if(designPlan.getIsRelease()!=null&&designPlan.getIsRelease().intValue()==1){
//				DesignPlan designPlan_ = new DesignPlan();
//				designPlan_.setId(designPlan.getId());
//				designPlan_.setIsRelease(DesignPlanRelease.stateless);
//				designPlanService.update(designPlan_);	
//			}
//			designPlanBrandService.delete(Integer.parseInt(designPlanBrandId));
//		}catch (Exception e) {
//			logger.error("deletedBrand  methods the error  :" + e);
//			return new ResponseEnvelope<>(false,"删除失败",msgId);
//		}
//		return new ResponseEnvelope<>(true,"删除成功",msgId);
//	}
//	
//	
//	
//	
//	/**
//	 * 方案推荐详情  接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/designPlanRecommendedDetails")
//	@ResponseBody
//	public Object designPlanRecommendedDetails(HttpServletRequest request, HttpServletResponse response){
//		
//		
//		
//		// 获取登录用户信息
//		LoginUser loginUser = new LoginUser();
//		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
//			loginUser.setId(-1);
//			loginUser.setLoginName("nologin");
//		} else {
//			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//		}
//				
//		String msgId = request.getParameter("msgId");
//		String planId = request.getParameter("planId");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
//		}
//		if(StringUtils.isBlank(planId)){
//			return new ResponseEnvelope<>(false,"缺少参数planId",msgId);
//		}
//		DesignPlan designPlan = null;
//		try{
//			designPlan = designPlanService.get(Integer.parseInt(planId));
//			if(designPlan==null){
//				return new ResponseEnvelope<>(false,"方案不存在planId："+planId,msgId);
//			}
//			
//			/*
//			 * 生成720渲染图二维码，
//			 * 720的二维码 取第1个720渲染图片的2维码，照片级二维码取每一个的，
//			 * 优先获取720高清渲染图，其次获取720普通渲染图，
//			 **/
//			String picPath = null;
//			Integer x = null;
//			Integer i = null;
//			List<ResRenderPic> render720List = null; 
//			ResRenderPicSearch renderPic720 = new ResRenderPicSearch();
//			renderPic720.setBusinessId(designPlan.getId());
//			renderPic720.setIsDeleted(0);
//			renderPic720.setLimit(-1);
//			renderPic720.setOrder(" gmt_create desc ");
//			renderPic720.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);/*取渲染原图*/
//			renderPic720.setRenderingType(RenderTypeCode.HD_720_LEVEL);/*优先获取720高清渲染图，其次获取720普通渲染图*/
//			x = resRenderPicService.getCount(renderPic720);
//			if(x == null || x.intValue()<=0){
//				renderPic720.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
//				i = resRenderPicService.getCount(renderPic720);
//				if(i != null && i.intValue()>0){
//					render720List = resRenderPicService.getList(renderPic720 );
//				}
//			}else{
//				render720List = resRenderPicService.getList(renderPic720 );
//			}
//			if(render720List!=null&&render720List.size()>0){
//				//picPath = render720List.get(0).getPicPath();
//				picPath = render720List.get(0).getPanoPath();
//			}
//			if(picPath!=null&&!"".equals(picPath)){
//				String qrCodeUrl = qrCodeCreate(picPath,"is720"); /*二维码生成方法*/
//				designPlan.setQrCode720Path(qrCodeUrl);
//			} 
// 
//			List<RenderPicInfo> renderPicList = new ArrayList<RenderPicInfo>();
//			
//			//查询该设计方案的全部渲染原图列表
//			List<ResRenderPic> picList = new ArrayList<>(); 
//			ResRenderPic resRenderPicS = new ResRenderPic();
//			resRenderPicS.setBusinessId(designPlan.getId());
//			resRenderPicS.setIsDeleted(0);
//			resRenderPicS.setLimit(-1);
//			resRenderPicS.setOrder(" gmt_create desc ");
//			resRenderPicS.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
//			picList = resRenderPicService.getList(resRenderPicS );
//			
//			/*封面摆在图片列表的第一位*/
//			Integer coverPicId = designPlan.getCoverPicId();
//			if(coverPicId!=null&&coverPicId.intValue()>0){
//				ResRenderPic coverPic = resRenderPicService.get(coverPicId);
//				if(coverPic!=null){
//					String qrCodeUrl = qrCodeCreate(coverPic.getPicPath(),"no720");
//					renderPicList.add(new RenderPicInfo(coverPic.getPicPath(), coverPic.getRenderingType(),coverPic.getId(),qrCodeUrl));
//				}
//			}
//			if(picList != null && picList.size() > 0){/*取渲染原图*/
//				for(ResRenderPic resRenderPic : picList){
//					if(coverPicId!=null&&coverPicId.intValue()>0){/*封面摆在图片列表的第一位，所以下面不需要重复  设置*/
//						if(resRenderPic.getId().intValue() == coverPicId.intValue()){
//							continue;
//						}
//					}
//					ResRenderPic rp = null;
//					if(resRenderPic.getSysTaskPicId() == null){
//						logger.error("设计方案planId="+planId+"获取渲染图片列表接口：sysTaskPicId is null!");
//					}
//					if(resRenderPic.getSysTaskPicId() != null){
//						rp = resRenderPicService.get(resRenderPic.getSysTaskPicId());
//					}
//					if(resRenderPic.getRenderingType() != null){
//						if(RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()){
//							if(rp == null){
//								logger.error("设计方案planId="+planId+"获取渲染图片列表接口：截图信息为空!");
//							}else{
//								if(StringUtils.isBlank(rp.getPicPath())){
//									logger.error("设计方案planId="+planId+"获取渲染图片列表接口列表接口：图片id="+rp.getId()+"图片路径为空！");
//								}
//								if(StringUtils.isNotBlank(rp.getPicPath())){
//									String qrCodeUrl = qrCodeCreate(resRenderPic.getPicPath(),"no720");
//									designPlan.setPicType(1);
//									renderPicList.add(new RenderPicInfo(rp.getPicPath(), resRenderPic.getRenderingType(),resRenderPic.getId(),qrCodeUrl));
//								}
//							}
//						}else{
//							String qrCodeUrl = qrCodeCreate(resRenderPic.getPicPath(),"no720");
//							designPlan.setPicType(1);
//							renderPicList.add(new RenderPicInfo(resRenderPic.getPicPath(), resRenderPic.getRenderingType(),resRenderPic.getId(),qrCodeUrl));
//						}
//					}else{
//						String qrCodeUrl = qrCodeCreate(resRenderPic.getPicPath(),"no720");
//						designPlan.setPicType(1);
//						renderPicList.add(new RenderPicInfo(resRenderPic.getPicPath(), resRenderPic.getRenderingType(),resRenderPic.getId(),qrCodeUrl));
//					}
//				}
//				designPlan.setPicList(renderPicList);
//			}
//		}catch (Exception e) {
//			logger.error("designPlanRecommendedDetails  methods the error  :" + e);
//			return new ResponseEnvelope<>(false,"数据错误",msgId);
//		}
//		return new ResponseEnvelope<DesignPlan>(designPlan, msgId, true);
//	}
//	
// 
//	/**
//	 * 2维码生成
//	 * @param picPath
//	 */
//	public String qrCodeCreate(String picPath,String type){
//		String qrCodeUrl = null;
//		try{
//			String rendPicUrl = null;
//			//渲染图路径
//			if(StringUtils.isBlank(type)){
//				return null;
//			}
//			if("no720".equals(type)){
//				rendPicUrl =  Utils.getValue("app.resources.url", "") + picPath;
//			}else if ("is720".equals(type)){
//				ResourceBundle render = ResourceBundle.getBundle("render");
//				rendPicUrl =  render.getString("app.pano.url") + picPath;
//			}else{
//				return null;
//			}
//			 
//			//二维码存放路径
//			String resUrl = Utils.getPropertyName("config/res","system.QRCode.pic.upload.path", "/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/system/QRCode/pic/");
//			resUrl = Utils.replaceDate(resUrl);
//			String qrCodePath =Tools.getRootPath(rendPicUrl, "") + resUrl;
//			 
//			//二维码名字
//			String rendPicUrlName = rendPicUrl.substring(rendPicUrl.lastIndexOf("/")+1);
//			if(rendPicUrlName.indexOf(".html")>-1){
//				rendPicUrlName = rendPicUrlName.substring(0,rendPicUrlName.lastIndexOf(".html")) + ".jpg";
//			}else{
//				rendPicUrlName = rendPicUrlName.substring(0,rendPicUrlName.lastIndexOf(".")) + ".jpg";
//			}
//			qrCodePath=Utils.dealWithPath(qrCodePath, "linux");
//			MatrixToImageWriter.createQRCodeImage(rendPicUrl,qrCodePath,rendPicUrlName);
//			qrCodeUrl = resUrl + rendPicUrlName;
//		}catch (Exception e) {
//			logger.error("qrCodeCreate  methods the error  :" + e);
//			return qrCodeUrl;
//		}
//		return qrCodeUrl;
//	}
//	
//	
//	
//	
//
//	
//	
//	
//	
//	
//	/**
//	 * 获取设计方案风格 接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/getDesignStyleList")
//	@ResponseBody
//	public Object getDesignStyleList(HttpServletRequest request, HttpServletResponse response){
//		List<BaseProductStyle>list = null;
//		Integer count = 0;
//		String msgId = request.getParameter("msgId");
//		String planId = request.getParameter("planId");
//		String houseType = request.getParameter("houseType");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
//		}
//		BaseProductStyleSearch baseProductStyleSearch = new BaseProductStyleSearch();
//		
//		//    用于发布页面       风格按设计方案的空间类型显示，不在显示全部
//		if(StringUtils.isBlank(planId)&&StringUtils.isBlank(houseType)){
//			return new ResponseEnvelope<>(false,"planId  和   houseType  不能同时为空",msgId);
//		}
//		DesignPlan designPlan = null;
//		if(planId!=null&&!"".equals(planId)){
//			 
//			DesignTemplet designTemplet = null;
//			SpaceCommon spaceCommon = null;
//			String houseName = null;
//			designPlan = designPlanService.get(Integer.parseInt(planId));
//			if(designPlan != null){
//				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
//			}else{
//				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案已被删除！",msgId);
//			}
//
//			if(designTemplet != null ){
//				spaceCommon = spaceCommonService.get(designTemplet.getSpaceCommonId());
//			}else{
//				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 样板房已被删除！",msgId);
//			}
//			
//			if(spaceCommon != null ){
//				List<SysDictionary>sysList= null;
//				SysDictionary sysDictionary = new SysDictionary();
//				sysDictionary.setType("houseType");
//				sysDictionary.setValue(spaceCommon.getSpaceFunctionId());
//				sysList = sysDictionaryService.getList(sysDictionary);
//				if(sysList!=null && sysList.size()>0){
//					houseName = sysList.get(0).getName();
//				}else{
//					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
//					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
//				}
//			}else{
//				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 空间已被删除！",msgId);
//			}
//			
//			if(houseName!=null && !"".equals(houseName)){
//				List<BaseProductStyle>styleList = null;
//				BaseProductStyle baseProductStyle = new BaseProductStyle();
//				baseProductStyle.setName(houseName);
//				baseProductStyle.setIsDeleted(0);
//				baseProductStyle.setLevel(1);
//				styleList = baseProductStyleService.getList(baseProductStyle);
//				if(styleList!=null && styleList.size()>0){
//					String name = styleList.get(0).getName();
//					if(houseName.equals(name)){
//						baseProductStyleSearch.setPid(styleList.get(0).getId());
//					}else{
//						return new ResponseEnvelope<BaseProductStyle>(false,"数据异常！",msgId);
//					}
//				}else{
//					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
//					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
//				}
//			}
//		}
//		
//		
//		//用于  方案推荐 风格过滤
//		if(houseType!=null && !"".equals(houseType)){
//			List<SysDictionary>sysList = null;
//			String houseName = null;
//			SysDictionary sysDictionary = new SysDictionary();
//			sysDictionary.setIsDeleted(0);
//			sysDictionary.setType("houseType");
//			sysDictionary.setValue(Integer.parseInt(houseType));
//			sysList = sysDictionaryService.getList(sysDictionary);
//			 
//			if(sysList!=null && sysList.size()>0){
//				houseName = sysList.get(0).getName();
//			}
//			if(houseName!=null && !"".equals(houseName)){
//				List<BaseProductStyle>styleList = null;
//				BaseProductStyle baseProductStyle = new BaseProductStyle();
//				baseProductStyle.setName(houseName);
//				baseProductStyle.setIsDeleted(0);
//				baseProductStyle.setLevel(1);
//				styleList = baseProductStyleService.getList(baseProductStyle);
//				if(styleList!=null && styleList.size()>0){
//					String name = styleList.get(0).getName();
//					if(houseName.equals(name)){
//						baseProductStyleSearch.setPid(styleList.get(0).getId());
//					}else{
//						return new ResponseEnvelope<BaseProductStyle>(false,"数据异常！",msgId);
//					}
//				}else{
//					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
//					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
//				}
//			}
//		}
//
//		try{
//			baseProductStyleSearch.setLevel(2);
//			baseProductStyleSearch.setIsDeleted(0);
//			baseProductStyleSearch.setSch_LongCode_(".root2.");
//			count = baseProductStyleService.getCount(baseProductStyleSearch);
//			baseProductStyleSearch.setLimit(count);
//			if(count!=null&&count.intValue()>0){
//				list = baseProductStyleService.getPaginatedList(baseProductStyleSearch);
//			}
//			if(list!=null && list.size()>0 && designPlan!=null){
//				Integer designRecommendedStyleId = designPlan.getDesignRecommendedStyleId();
//				if(designRecommendedStyleId!=null){
//					for (BaseProductStyle baseProductStyle : list) {
//						if(baseProductStyle.getId().intValue() == designRecommendedStyleId.intValue()){
//							baseProductStyle.setDesignPlanRecommendedBeSelected(1);
//						}
//					}
//				}
// 
//			}
//		}catch (Exception e) {
//			logger.error("getDesignStyleList  methods the error  :" + e);
//			return new ResponseEnvelope<BaseProductStyle>(false,"数据错误",msgId);
//		}
//		return new ResponseEnvelope<BaseProductStyle>(count,list,msgId);
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * 获取设计方案风格 接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/getDesignStyleListNew")
//	@ResponseBody
//	public Object getDesignStyleListNew(HttpServletRequest request, HttpServletResponse response){
//		List<BaseProductStyle>list = null;
//		Integer count = 0;
//		String msgId = request.getParameter("msgId");
//		String planId = request.getParameter("planId");
//		String houseType = request.getParameter("houseType");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
//		}
//		BaseProductStyleSearch baseProductStyleSearch = new BaseProductStyleSearch();
//		
//		//    用于发布页面       风格按设计方案的空间类型显示，不在显示全部
//		if(StringUtils.isBlank(planId)&&StringUtils.isBlank(houseType)){
//			return new ResponseEnvelope<>(false,"planId  和   houseType  不能同时为空",msgId);
//		}
//		DesignPlan designPlan = null;
//		if(planId!=null&&!"".equals(planId)){
//			DesignTemplet designTemplet = null;
//			SpaceCommon spaceCommon = null;
//			Integer value = null;
//			designPlan = designPlanService.get(Integer.parseInt(planId));
//			if(designPlan != null){
//				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
//			}else{
//				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案已被删除！",msgId);
//			}
//
//			if(designTemplet != null ){
//				spaceCommon = spaceCommonService.get(designTemplet.getSpaceCommonId());
//			}else{
//				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 样板房已被删除！",msgId);
//			}
//			
//			if(spaceCommon != null ){
//				List<SysDictionary>sysList= null;
//				SysDictionary sysDictionary = new SysDictionary();
//				sysDictionary.setType("houseType");
//				sysDictionary.setValue(spaceCommon.getSpaceFunctionId());
//				sysList = sysDictionaryService.getList(sysDictionary);
//				if(sysList!=null && sysList.size()>0){
//					value = sysList.get(0).getValue();
//				}else{
//					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
//					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
//				}
//			}else{
//				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 空间已被删除！",msgId);
//			}
//			
//			if(value!=null && value.intValue()>0){
//				List<BaseProductStyle>styleList = null;
//				BaseProductStyle baseProductStyle = new BaseProductStyle();
//				baseProductStyle.setNuma2(value);
//				baseProductStyle.setIsDeleted(0);
//				baseProductStyle.setLevel(1);
//				styleList = baseProductStyleService.getList(baseProductStyle);
//				if(styleList!=null && styleList.size()>0){
//					baseProductStyleSearch.setPid(styleList.get(0).getId());
//				}else{
//					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
//					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
//				}
//			}
//		}
//		//用于  方案推荐 风格过滤
//		if(houseType!=null && !"".equals(houseType)){
//				List<BaseProductStyle>styleList = null;
//				BaseProductStyle baseProductStyle = new BaseProductStyle();
//				baseProductStyle.setNuma2(Integer.parseInt(houseType));
//				baseProductStyle.setIsDeleted(0);
//				baseProductStyle.setLevel(1);
//				styleList = baseProductStyleService.getList(baseProductStyle);
//				if(styleList!=null && styleList.size()>0){
//					baseProductStyleSearch.setPid(styleList.get(0).getId());
//				}else{
//					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
//					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
//				}
//			}
//		try{
//			baseProductStyleSearch.setLevel(2);
//			baseProductStyleSearch.setIsDeleted(0);
//			baseProductStyleSearch.setSch_LongCode_(".root2.");
//			count = baseProductStyleService.getCount(baseProductStyleSearch);
//			baseProductStyleSearch.setLimit(count);
//			if(count!=null&&count.intValue()>0){
//				list = baseProductStyleService.getPaginatedList(baseProductStyleSearch);
//			}
//			if(list!=null && list.size()>0 && designPlan!=null){
//				Integer designRecommendedStyleId = designPlan.getDesignRecommendedStyleId();
//				for (BaseProductStyle baseProductStyle : list) {
//					if(baseProductStyle.getId().intValue() == designRecommendedStyleId.intValue()){
//						baseProductStyle.setDesignPlanRecommendedBeSelected(1);
//					}
//				}
//			}
//		}catch (Exception e) {
//			logger.error("getDesignStyleList  methods the error  :" + e);
//			return new ResponseEnvelope<BaseProductStyle>(false,"数据错误",msgId);
//		}
//		return new ResponseEnvelope<BaseProductStyle>(count,list,msgId);
//	}
//	
//	
//	/**
//	 * 取得该设计方案的  审核人员列表
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/planCheckUserList")
//	@ResponseBody
//	public Object planCheckUserList(HttpServletRequest request, HttpServletResponse response){
//		List<SysUserPlanRecommended>list = null;
//		DesignPlan designPlan = null;
//		String msgId = request.getParameter("msgId");
//		String planId = request.getParameter("planId");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数msgId",msgId);
//		}
//		if(StringUtils.isBlank(planId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数planId",msgId);
//		}
//		designPlan = designPlanService.get(Integer.parseInt(planId));
//		if(designPlan == null){
//			return new ResponseEnvelope<BaseProductStyle>(false,"方案id:"+planId+"不存在，可能已被删除",msgId);
//		}
//		//取得该设计方案的  审核人员列表
//		list = designPlanCheckService.planCheckUserList(designPlan);
//		return new ResponseEnvelope<SysUserPlanRecommended>(list.size(),list,msgId);
//	}
//	
//	
//	/**
//	 * 删除 审核人员
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/delPlanCheckUser")
//	@ResponseBody
//	public Object delPlanCheckUser(HttpServletRequest request, HttpServletResponse response){
//		String msgId = request.getParameter("msgId");
//		String designPlanCheckId = request.getParameter("designPlanCheckId");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数msgId",msgId);
//		}
//		if(StringUtils.isBlank(designPlanCheckId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数designPlanCheckId",msgId);
//		}
//		try{
//			designPlanCheckService.delete(Integer.parseInt(designPlanCheckId));
//		}catch (Exception e) {
//			logger.error("delPlanCheckUser  methods the error  :" + e);
//			return new ResponseEnvelope<BaseProductStyle>(false,"删除失败，数据错误",msgId);
//		}
//		return new ResponseEnvelope<BaseProductStyle>(true,"删除成功",msgId);
//	}
//	
//	
//	
//	/**
//	 * 方案推荐审核接口
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/designPlanRecommendedCheck")
//	@ResponseBody
//	public Object designPlanRecommendedCheck(HttpServletRequest request, HttpServletResponse response){
//		String msgId =  request.getParameter("msgId");
//		String planId =  request.getParameter("planId");
//		String userId =  request.getParameter("userId");
//		String isRelease =  request.getParameter("isRelease");
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数msgId",msgId);
//		}
//		if(StringUtils.isBlank(planId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数planId",msgId);
//		}
//		if(StringUtils.isBlank(userId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数userId",msgId);
//		}
//		if(StringUtils.isBlank(isRelease)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数isRelease",msgId);
//		}
//		int isReleaseInt = Integer.parseInt(isRelease);
//		if(isReleaseInt != DesignPlanRelease.is_release && isReleaseInt != DesignPlanRelease.failure_check){
//			return new ResponseEnvelope<BaseProductStyle>(false,"参数错误isRelease",msgId);
//		}
//		DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
//		if(designPlan == null){
//			return new ResponseEnvelope<BaseProductStyle>(false,"不存在该方案，方案id为："+planId,msgId);
//		}
//		SysUser sysUser = sysUserService.get(Integer.parseInt(userId));
//		if(sysUser == null){
//			return new ResponseEnvelope<BaseProductStyle>(false,"不存在该用户，id为："+userId,msgId);
//		}
//		
//		
//		try{
//			//判断是否有审核权限
//			boolean permissions = false;
//			permissions =  designPlanRecommendedService.isDesignPlanCheck(null, Integer.parseInt(userId));
//			//审核通过逻辑
//			if(designPlan.getIsRelease().intValue() == DesignPlanRelease.is_release
//					|| designPlan.getIsRelease().intValue() == DesignPlanRelease.failure_check){
//				return new ResponseEnvelope<DesignPlan>(true,"请勿重复操作！",msgId);
//			}
//			if(isReleaseInt == DesignPlanRelease.is_release){
//				if(permissions){
//					Map<String,String>resMap = designPlanRecommendedService.planIsReleaseCheck(designPlan,null);
//					if("false".equals(resMap.get("success"))){
//						return new ResponseEnvelope<DesignPlanRecommendedResult>(false,resMap.get("data"),msgId);
//					}
//					if(!"true".equals(resMap.get("success"))){
//						return new ResponseEnvelope<DesignPlanRecommendedResult>(false,resMap.get("data"),msgId);
//					}
//					//判断是产品 是否都是发布中
//					/*DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
//					designPlanProductSearch.setPlanId(designPlan.getId());
//					designPlanProductSearch.setIsDeleted(0);
//					List<DesignPlanProduct> designPlanProducts = designPlanProductService.getList(designPlanProductSearch);
//					if( designPlanProducts != null && designPlanProducts.size() > 0 ){
//						for( DesignPlanProduct designPlanProduct : designPlanProducts ){
//							Integer productId = designPlanProduct.getProductId();
//							if( productId != null ){
//								BaseProduct baseProduct = baseProductService.get(productId);
//								if( baseProduct != null ){
//									if(baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_RELEASE.intValue()&&
//											baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_UP.intValue()&&
//													baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_TEST.intValue()
//											){
//										return new ResponseEnvelope<BaseProductStyle>(false,"产品 ["+baseProduct.getProductCode()+"] 未发布,请替换或删除该产品,再进行发布!",msgId);
//									}
//								}
//							}
//						}
//					}*/
//					//修改设计方案状态
//					DesignPlan newDesignPlan = new DesignPlan();
//					newDesignPlan.setId(Integer.parseInt(planId));
//					newDesignPlan.setIsRelease(DesignPlanRelease.is_release);
//					designPlanService.update(newDesignPlan);
//				}
//			//审核失败逻辑
//			}else if(isReleaseInt == DesignPlanRelease.failure_check){
//				DesignPlan newDesignPlan = new DesignPlan();
//				newDesignPlan.setId(Integer.parseInt(planId));
//				newDesignPlan.setIsRelease(DesignPlanRelease.failure_check);
//				designPlanService.update(newDesignPlan);
//			}
//		}catch (Exception e) {
//			logger.error("designPlanRecommendedCheck  methods the error  :" + e);
//			return new ResponseEnvelope<BaseProductStyle>(false,"审核失败，数据错误",msgId);
//		}
//		return new ResponseEnvelope<DesignPlan>(true,"操作成功",msgId);
//	}
//	
//	
//	/**
//	 * 审核列表
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/designPlanCheckList")
//	@ResponseBody
//	public Object designPlanCheckList(HttpServletRequest request, HttpServletResponse response){
//		List<DesignPlanRecommendedResult>list = null;
//		int total = 0;
//		String msgId =  request.getParameter("msgId");
//		String userId =  request.getParameter("userId");
//		String spaceFunctionId =  request.getParameter("spaceFunctionId");
//		/*String planNumber =  request.getParameter("planNumber");*/
//		String planName =  request.getParameter("planName");
//		/*String brandName =  request.getParameter("brandName");*/
//		if(StringUtils.isBlank(userId)){
//			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数userId",msgId);
//		}
//		DesignPlanCheck designPlanCheck = new DesignPlanCheck();
//		if(StringUtils.isNotBlank(spaceFunctionId) && !"-1".equals(spaceFunctionId)){
//			
//			designPlanCheck.setSpaceFunctionId(Integer.parseInt(spaceFunctionId));
//		}
//		if(StringUtils.isNotBlank(planName)){
//			if(planName.startsWith("@") && planName.endsWith("@")){
//				planName = planName.replace("@", "");
//				designPlanCheck.setBrandName(planName);
//			}else if(planName.startsWith("*") && planName.endsWith("*")){
//				planName = planName.replace("*", "");
//				designPlanCheck.setPlanNumber(planName);
//			}else{ 
//				designPlanCheck.setPlanName(planName);
//			}
//		}
//		/*if(StringUtils.isNotBlank(planNumber)){
//			designPlanCheck.setPlanNumber(planNumber);	
//		}
// 
//		if(StringUtils.isNotBlank(brandName)){
//			designPlanCheck.setBrandName(brandName);
//		}*/
//		try{
//			 
//			designPlanCheck.setUserId(Integer.parseInt(userId));
//			total = designPlanCheckService.getDesignPlanCheckCount(designPlanCheck);
//			if(total>0){
//				list = designPlanCheckService.getDesignPlanCheckList(designPlanCheck);
//			}
//		}catch (Exception e) {
//			logger.error("designPlanCheckList  methods the error  :" + e);
//			return new ResponseEnvelope<BaseProductStyle>(false,"数据错误",msgId);
//		}
//		return new ResponseEnvelope<DesignPlanRecommendedResult>(total,list,msgId);
//	}
//	
//	/**
//	 * 方案发布回显功能
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/releaseDetails")
//	@ResponseBody
//	public Object releaseDetails (HttpServletRequest request, HttpServletResponse response){
//		DesignPlan designPlan = null;
//		String planId = request.getParameter("planId");
//		String msgId = request.getParameter("msgId");
//		if(StringUtils.isBlank(planId)){
//			return new ResponseEnvelope<DesignPlan>(false,"缺少参数planId",msgId);
//		}
//		if(StringUtils.isBlank(msgId)){
//			return new ResponseEnvelope<DesignPlan>(false,"缺少参数msgId",msgId);
//		}
//		designPlan = designPlanService.get(Integer.parseInt(planId));
//		if(designPlan == null){
//			return new ResponseEnvelope<DesignPlan>(false,"id为"+planId+"的方案不存在!",msgId);
//		}
//		return new ResponseEnvelope<DesignPlan>(designPlan,msgId,true);
//	}
//}=======
////package com.nork.design.controller.web;
////import java.util.ArrayList;
////import java.util.Arrays;
////import java.util.List;
////import java.util.Map;
////import java.util.ResourceBundle;
////
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////import org.apache.commons.lang3.StringUtils;
////import org.apache.log4j.Logger;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.ModelAttribute;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.ResponseBody;
////
////import com.nork.common.constant.util.SystemCommonUtil;
////import com.nork.common.model.LoginUser;
////import com.nork.common.model.ResponseEnvelope;
////import com.nork.common.properties.ResProperties;
////import com.nork.common.util.Utils;
////import com.nork.common.util.QRCode.MatrixToImageWriter;
////import com.nork.design.model.DesignPlan;
////import com.nork.design.model.DesignPlanBrand;
////import com.nork.design.model.DesignPlanCheck;
////import com.nork.design.model.DesignPlanProduct;
////import com.nork.design.model.DesignPlanRecommendedResult;
////import com.nork.design.model.DesignTemplet;
////import com.nork.design.model.ReleaseDesignPlanModel;
////import com.nork.design.model.RenderPicInfo;
////import com.nork.design.service.DesignPlanBrandService;
////import com.nork.design.service.DesignPlanCheckService;
////import com.nork.design.service.DesignPlanProductService;
////import com.nork.design.service.DesignPlanService;
////import com.nork.design.service.DesignPlanRecommendedService;
////import com.nork.design.service.DesignTempletService;
////import com.nork.design.service.impl.NewDesignPlanRecommendServiceImpl;
////import com.nork.home.model.SpaceCommon;
////import com.nork.home.service.SpaceCommonService;
////import com.nork.product.model.AuthorizedConfig;
////import com.nork.product.model.BaseBrand;
////import com.nork.product.model.BaseProduct;
////import com.nork.product.model.BaseProductPutawayState;
////import com.nork.product.model.BaseProductStyle;
////import com.nork.product.model.search.BaseBrandSearch;
////import com.nork.product.model.search.BaseProductStyleSearch;
////import com.nork.product.service.AuthorizedConfigService;
////import com.nork.product.service.BaseBrandService;
////import com.nork.product.service.BaseProductService;
////import com.nork.product.service.BaseProductStyleService;
////import com.nork.render.model.RenderTypeCode;
////import com.nork.system.model.ResRenderPic;
////import com.nork.system.model.SysDictionary;
////import com.nork.system.model.SysUser;
////import com.nork.system.model.SysUserPlanRecommended;
////import com.nork.system.model.search.ResRenderPicSearch;
////import com.nork.system.service.ResRenderPicService;
////import com.nork.system.service.SysDictionaryService;
////import com.nork.system.service.SysRoleService;
////import com.nork.system.service.SysUserRoleService;
////import com.nork.system.service.SysUserService;
////
////import DesignPlan.DesignPlanRelease;
//// 
//// 
////
//// /**
////  * 设计方案推荐
////  * 2017-05-03 zhaobl
////  * @author Administrator
////  *
////  */
////@Controller
////@RequestMapping("/{style}/web/design/designPlanRecommended")
////public class WebDesignPlanRecommendedController {
////	/*** 获取配置文件 tmg.properties */
////	private static Logger logger = Logger.getLogger(WebDesignPlanRecommendedController.class);
////
////	@Autowired
////	DesignPlanBrandService designPlanBrandService;
////	
////	@Autowired
////	AuthorizedConfigService authorizedConfigService;
////	
////	@Autowired
////	DesignPlanService designPlanService;
////	
////	@Autowired
////	SysDictionaryService sysDictionaryService;
////	
////	@Autowired
////	ResRenderPicService resRenderPicService;
////	
////	@Autowired
////	BaseBrandService baseBrandService;
////	
////	@Autowired
////	BaseProductStyleService baseProductStyleService;
////	
////	@Autowired
////	DesignTempletService designTempletService;
////	
////	@Autowired
////	SpaceCommonService spaceCommonService;
////	
////	@Autowired
////	DesignPlanProductService designPlanProductService;
////	
////	@Autowired
////	BaseProductService baseProductService;
////	
////	@Autowired
////	DesignPlanCheckService designPlanCheckService;
////	
////	@Autowired
////	SysUserService sysUserService;
////	
////	@Autowired
////	SysRoleService sysRoleService;
////	
////	@Autowired
////	SysUserRoleService sysUserRoleService;
////	
////	@Autowired
////	DesignPlanRecommendedService designPlanRecommendedService;
////	/////////////////////////
////	@Autowired
////	NewDesignPlanRecommendServiceImpl newDesignPlanRecommendServiceImpl;
////	
////	
////	/**
////	 * 方案推荐 列表 数据
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/planRecommendedList")
////	@ResponseBody
////	public Object planRecommendedList(HttpServletRequest request, HttpServletResponse response){
////		String msgId = request.getParameter("msgId");
////		String houseType = request.getParameter("houseType");
////		String livingName = request.getParameter("livingName");
////		String areaValue = request.getParameter("areaValue");
////		String designRecommendedStyleId = request.getParameter("designRecommendedStyleId");
////		String isMainList = request.getParameter("isMainList");  
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"缺少参数msgId",msgId);
////		}
////		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
////		return newDesignPlanRecommendServiceImpl.getPlanRecommendedList(houseType, livingName, areaValue, designRecommendedStyleId, isMainList, msgId, loginUser);
////	}
////	
////	private boolean validPlanReleaseParam(String... args) {
////		boolean result = true;
////		for(String arg :args) {
////			if(StringUtils.isBlank(arg)) {
////				result = false;
////				return result;
////			}
////		}
////		return result;
////	}
////	
////	/**
////	 * Update by Steve on 2017-06-28
////	 * @name 方案发布
////	 *  发布规则：
////	 * 	1.断是否拥有设计方案拷贝 和 发布的权限
////	 * 	2.发布需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/planRelease")
////	@ResponseBody
////	public ResponseEnvelope<DesignPlanRecommendedResult> planRelease(HttpServletRequest request, HttpServletResponse response){
////		String msgId = request.getParameter("msgId");
////		String brandIds = request.getParameter("brandIds");  /*品牌ids*/
////		String checkIds = request.getParameter("checkIds");  /*审核人员ids*/
////		String styleId = request.getParameter("styleId"); /*风格id*/
////		String planId = request.getParameter("planId");/*方案id*/
////		String isRelease = request.getParameter("isRelease");/*设计方案是否发布 0 否 1是*/
////		String isRecommended = request.getParameter("isRecommended");/*是否默认推荐  1 默认 0 非默认*/
////		String isDefaultDecorate = request.getParameter("isDefaultDecorate");/*是否支持一键装修  1 支持  0不支持*/
////		String planNumber = request.getParameter("planNumber");/*方案编号*/
////		
////		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
////		if(loginUser==null) {
////			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"登录超时,请重新登录",msgId);
////		}
////		boolean valid = validPlanReleaseParam(msgId,planId,isRelease);
////		if(!valid) {
////			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"参数不完整",msgId);
////		}
////		ReleaseDesignPlanModel model = new ReleaseDesignPlanModel();
////		model.setBrandIds(brandIds);
////		model.setCheckIds(checkIds);
////		model.setIsDefaultDecorate(isDefaultDecorate);
////		model.setIsRecommended(isRecommended);
////		model.setIsRelease(isRelease);
////		model.setPlanId(planId);
////		model.setPlanNumber(planNumber);
////		model.setStyleId(styleId);
////		
////		return newDesignPlanRecommendServiceImpl.recommendDesignPlan(model, msgId, loginUser);
////	}
////	
////	
////	
////	/**
////	 * 发布用的品牌列表 接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/brandList")
////	@ResponseBody
////	public Object brandList(@ModelAttribute("baseBrandSearch")BaseBrandSearch baseBrandSearch,HttpServletRequest request, HttpServletResponse response){
////		
////		Integer total = 0;
////		List <BaseBrand> brandList = null;
////		List <BaseBrand> list = new ArrayList<BaseBrand>();
////		String msgId = request.getParameter("msgId");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
////		}
////		/*在里面新增一个选择所有*/
////		BaseBrand baseBrand = new BaseBrand();
////		baseBrand.setBrandName("所有品牌");
////		baseBrand.setId(-1);
////		list.add(baseBrand);
////		try{
////			baseBrandSearch.setIsDeleted(0);
////			total = baseBrandService.getCount(baseBrandSearch);	
////			baseBrandSearch.setLimit(total);
////			if(total!=null&&total.intValue()>0){
////				brandList= baseBrandService.getPaginatedList(baseBrandSearch);
////				list.addAll(brandList);
////				total = total + 1;
////			}
////		}catch (Exception e) {
////			logger.error("brandList  methods the error  :" + e);
////			return new ResponseEnvelope<>(false,"数据错误",msgId);
////		}
////		return new ResponseEnvelope<>(total,list,msgId);
////	}
////	
////	
////	/**
////	 * 已经选择 的品牌列表 接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/myBrandList")
////	@ResponseBody
////	public Object myBrandList(HttpServletRequest request, HttpServletResponse response){
////		List<BaseBrand>list = null;
////		String msgId = request.getParameter("msgId");
////		String planId = request.getParameter("planId");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
////		}
////		if(StringUtils.isBlank(planId)){
////			return new ResponseEnvelope<>(false,"缺少参数planId",msgId);
////		}
////		try{
////			DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
////			if(designPlan==null){
////				return new ResponseEnvelope<>(false,"该设计方案不存在或被删除planId："+planId,msgId);
////			}
////			//删除产品将会把设计方案推荐  改成未发布
////			/*if(designPlan.getIsRelease()!=null&&designPlan.getIsRelease().intValue() == 1){
////				DesignPlan newDesignPlan = new DesignPlan();
////				newDesignPlan.setId(designPlan.getId());
////				newDesignPlan.setIsRelease(DesignPlanRelease.stateless);
////				designPlanService.update(newDesignPlan);
////			}*/
////			list = designPlanBrandService.getListByPlanId(Integer.parseInt(planId));
////			if(list!=null&&list.size()>0){
////				for (BaseBrand baseBrand : list) {
////					Integer brandAssociatedId = baseBrand.getBrandAssociatedId();
////					if(brandAssociatedId!=null&&brandAssociatedId.intValue()==-1){
////						baseBrand.setBrandName("所有品牌");
////					}
////				}
////			}
////		}catch (Exception e) {
////			logger.error("myBrandList  methods the error  :" + e);
////			return new ResponseEnvelope<>(false,"数据错误",msgId);
////		}
////		return new ResponseEnvelope<>(list.size(),list,msgId);
////	}
////	
////	
////	
////	/**
////	 * 已经选择 的品牌列表 删除接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/deletedBrand")
////	@ResponseBody
////	public Object deletedBrand(HttpServletRequest request, HttpServletResponse response){
////		String msgId = request.getParameter("msgId");
////		String designPlanBrandId = request.getParameter("designPlanBrandId");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
////		}
////		if(StringUtils.isBlank(designPlanBrandId)){
////			return new ResponseEnvelope<>(false,"缺少参数designPlanBrandId",msgId);
////		}
////		try{
////			DesignPlanBrand designPlanBrand = designPlanBrandService.get(Integer.parseInt(designPlanBrandId));
////			if(designPlanBrand==null){
////				return new ResponseEnvelope<>(false,"designPlanBrandId:" + designPlanBrandId + "不存在",msgId);
////			}
////			Integer planId = designPlanBrand.getPlanId();
////			if(planId == null||planId.intValue()<=0){
////				return new ResponseEnvelope<>(false,"数据错误",msgId);
////			}
////			DesignPlan designPlan = null;
////			designPlan = designPlanService.get(planId);
////			if(designPlan == null){
////				return new ResponseEnvelope<>(false,"该设计方案已经被删除，请刷新我的设计页面",msgId);
////			}
////			if(designPlan.getIsRelease()!=null&&designPlan.getIsRelease().intValue()==1){
////				DesignPlan designPlan_ = new DesignPlan();
////				designPlan_.setId(designPlan.getId());
////				designPlan_.setIsRelease(DesignPlanRelease.stateless);
////				designPlanService.update(designPlan_);	
////			}
////			designPlanBrandService.delete(Integer.parseInt(designPlanBrandId));
////		}catch (Exception e) {
////			logger.error("deletedBrand  methods the error  :" + e);
////			return new ResponseEnvelope<>(false,"删除失败",msgId);
////		}
////		return new ResponseEnvelope<>(true,"删除成功",msgId);
////	}
////	
////	
////	
////	
////	/**
////	 * 方案推荐详情  接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/designPlanRecommendedDetails")
////	@ResponseBody
////	public Object designPlanRecommendedDetails(HttpServletRequest request, HttpServletResponse response){
////		
////		
////		
////		// 获取登录用户信息
////		LoginUser loginUser = new LoginUser();
////		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
////			loginUser.setId(-1);
////			loginUser.setLoginName("nologin");
////		} else {
////			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
////		}
////				
////		String msgId = request.getParameter("msgId");
////		String planId = request.getParameter("planId");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
////		}
////		if(StringUtils.isBlank(planId)){
////			return new ResponseEnvelope<>(false,"缺少参数planId",msgId);
////		}
////		DesignPlan designPlan = null;
////		try{
////			designPlan = designPlanService.get(Integer.parseInt(planId));
////			if(designPlan==null){
////				return new ResponseEnvelope<>(false,"方案不存在planId："+planId,msgId);
////			}
////			
////			/*
////			 * 生成720渲染图二维码，
////			 * 720的二维码 取第1个720渲染图片的2维码，照片级二维码取每一个的，
////			 * 优先获取720高清渲染图，其次获取720普通渲染图，
////			 **/
////			String picPath = null;
////			Integer x = null;
////			Integer i = null;
////			List<ResRenderPic> render720List = null; 
////			ResRenderPicSearch renderPic720 = new ResRenderPicSearch();
////			renderPic720.setBusinessId(designPlan.getId());
////			renderPic720.setIsDeleted(0);
////			renderPic720.setLimit(-1);
////			renderPic720.setOrder(" gmt_create desc ");
////			renderPic720.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);/*取渲染原图*/
////			renderPic720.setRenderingType(RenderTypeCode.HD_720_LEVEL);/*优先获取720高清渲染图，其次获取720普通渲染图*/
////			x = resRenderPicService.getCount(renderPic720);
////			if(x == null || x.intValue()<=0){
////				renderPic720.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
////				i = resRenderPicService.getCount(renderPic720);
////				if(i != null && i.intValue()>0){
////					render720List = resRenderPicService.getList(renderPic720 );
////				}
////			}else{
////				render720List = resRenderPicService.getList(renderPic720 );
////			}
////			if(render720List!=null&&render720List.size()>0){
////				//picPath = render720List.get(0).getPicPath();
////				picPath = render720List.get(0).getPanoPath();
////			}
////			if(picPath!=null&&!"".equals(picPath)){
////				String qrCodeUrl = qrCodeCreate(picPath,"is720"); /*二维码生成方法*/
////				designPlan.setQrCode720Path(qrCodeUrl);
////			} 
//// 
////			List<RenderPicInfo> renderPicList = new ArrayList<RenderPicInfo>();
////			
////			//查询该设计方案的全部渲染原图列表
////			List<ResRenderPic> picList = new ArrayList<>(); 
////			ResRenderPic resRenderPicS = new ResRenderPic();
////			resRenderPicS.setBusinessId(designPlan.getId());
////			resRenderPicS.setIsDeleted(0);
////			resRenderPicS.setLimit(-1);
////			resRenderPicS.setOrder(" gmt_create desc ");
////			resRenderPicS.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
////			picList = resRenderPicService.getList(resRenderPicS );
////			
////			/*封面摆在图片列表的第一位*/
////			Integer coverPicId = designPlan.getCoverPicId();
////			if(coverPicId!=null&&coverPicId.intValue()>0){
////				ResRenderPic coverPic = resRenderPicService.get(coverPicId);
////				if(coverPic!=null){
////					String qrCodeUrl = qrCodeCreate(coverPic.getPicPath(),"no720");
////					renderPicList.add(new RenderPicInfo(coverPic.getPicPath(), coverPic.getRenderingType(),coverPic.getId(),qrCodeUrl));
////				}
////			}
////			if(picList != null && picList.size() > 0){/*取渲染原图*/
////				for(ResRenderPic resRenderPic : picList){
////					if(coverPicId!=null&&coverPicId.intValue()>0){/*封面摆在图片列表的第一位，所以下面不需要重复  设置*/
////						if(resRenderPic.getId().intValue() == coverPicId.intValue()){
////							continue;
////						}
////					}
////					ResRenderPic rp = null;
////					if(resRenderPic.getSysTaskPicId() == null){
////						logger.error("设计方案planId="+planId+"获取渲染图片列表接口：sysTaskPicId is null!");
////					}
////					if(resRenderPic.getSysTaskPicId() != null){
////						rp = resRenderPicService.get(resRenderPic.getSysTaskPicId());
////					}
////					if(resRenderPic.getRenderingType() != null){
////						if(RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()){
////							if(rp == null){
////								logger.error("设计方案planId="+planId+"获取渲染图片列表接口：截图信息为空!");
////							}else{
////								if(StringUtils.isBlank(rp.getPicPath())){
////									logger.error("设计方案planId="+planId+"获取渲染图片列表接口列表接口：图片id="+rp.getId()+"图片路径为空！");
////								}
////								if(StringUtils.isNotBlank(rp.getPicPath())){
////									String qrCodeUrl = qrCodeCreate(resRenderPic.getPicPath(),"no720");
////									designPlan.setPicType(1);
////									renderPicList.add(new RenderPicInfo(rp.getPicPath(), resRenderPic.getRenderingType(),resRenderPic.getId(),qrCodeUrl));
////								}
////							}
////						}else{
////							String qrCodeUrl = qrCodeCreate(resRenderPic.getPicPath(),"no720");
////							designPlan.setPicType(1);
////							renderPicList.add(new RenderPicInfo(resRenderPic.getPicPath(), resRenderPic.getRenderingType(),resRenderPic.getId(),qrCodeUrl));
////						}
////					}else{
////						String qrCodeUrl = qrCodeCreate(resRenderPic.getPicPath(),"no720");
////						designPlan.setPicType(1);
////						renderPicList.add(new RenderPicInfo(resRenderPic.getPicPath(), resRenderPic.getRenderingType(),resRenderPic.getId(),qrCodeUrl));
////					}
////				}
////				designPlan.setPicList(renderPicList);
////			}
////		}catch (Exception e) {
////			logger.error("designPlanRecommendedDetails  methods the error  :" + e);
////			return new ResponseEnvelope<>(false,"数据错误",msgId);
////		}
////		return new ResponseEnvelope<DesignPlan>(designPlan, msgId, true);
////	}
////	
//// 
////	/**
////	 * 2维码生成
////	 * @param picPath
////	 */
////	public String qrCodeCreate(String picPath,String type){
////		String qrCodeUrl = null;
////		try{
////			String rendPicUrl = null;
////			//渲染图路径
////			if(StringUtils.isBlank(type)){
////				return null;
////			}
////			if("no720".equals(type)){
////				rendPicUrl =  Utils.getValue("app.resources.url", "") + picPath;
////			}else if ("is720".equals(type)){
////				ResourceBundle render = ResourceBundle.getBundle("render");
////				rendPicUrl =  render.getString("app.pano.url") + picPath;
////			}else{
////				return null;
////			}
////			 
////			//二维码存放路径
////			String resUrl = Utils.getPropertyName("config/res","system.QRCode.pic.upload.path", "/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/system/QRCode/pic/");
////			resUrl = Utils.replaceDate(resUrl);
////			String qrCodePath = Utils.getValue("app.upload.root", "") + resUrl;
////			 
////			//二维码名字
////			String rendPicUrlName = rendPicUrl.substring(rendPicUrl.lastIndexOf("/")+1);
////			if(rendPicUrlName.indexOf(".html")>-1){
////				rendPicUrlName = rendPicUrlName.substring(0,rendPicUrlName.lastIndexOf(".html")) + ".jpg";
////			}else{
////				rendPicUrlName = rendPicUrlName.substring(0,rendPicUrlName.lastIndexOf(".")) + ".jpg";
////			}
////			qrCodePath=Utils.dealWithPath(qrCodePath, "linux");
////			MatrixToImageWriter.createQRCodeImage(rendPicUrl,qrCodePath,rendPicUrlName);
////			qrCodeUrl = resUrl + rendPicUrlName;
////		}catch (Exception e) {
////			logger.error("qrCodeCreate  methods the error  :" + e);
////			return qrCodeUrl;
////		}
////		return qrCodeUrl;
////	}
////	
////	
////	
////	
////
////	
////	
////	
////	
////	
////	/**
////	 * 获取设计方案风格 接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/getDesignStyleList")
////	@ResponseBody
////	public Object getDesignStyleList(HttpServletRequest request, HttpServletResponse response){
////		List<BaseProductStyle>list = null;
////		Integer count = 0;
////		String msgId = request.getParameter("msgId");
////		String planId = request.getParameter("planId");
////		String houseType = request.getParameter("houseType");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
////		}
////		BaseProductStyleSearch baseProductStyleSearch = new BaseProductStyleSearch();
////		
////		//    用于发布页面       风格按设计方案的空间类型显示，不在显示全部
////		if(StringUtils.isBlank(planId)&&StringUtils.isBlank(houseType)){
////			return new ResponseEnvelope<>(false,"planId  和   houseType  不能同时为空",msgId);
////		}
////		DesignPlan designPlan = null;
////		if(planId!=null&&!"".equals(planId)){
////			 
////			DesignTemplet designTemplet = null;
////			SpaceCommon spaceCommon = null;
////			String houseName = null;
////			designPlan = designPlanService.get(Integer.parseInt(planId));
////			if(designPlan != null){
////				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
////			}else{
////				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案已被删除！",msgId);
////			}
////
////			if(designTemplet != null ){
////				spaceCommon = spaceCommonService.get(designTemplet.getSpaceCommonId());
////			}else{
////				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 样板房已被删除！",msgId);
////			}
////			
////			if(spaceCommon != null ){
////				List<SysDictionary>sysList= null;
////				SysDictionary sysDictionary = new SysDictionary();
////				sysDictionary.setType("houseType");
////				sysDictionary.setValue(spaceCommon.getSpaceFunctionId());
////				sysList = sysDictionaryService.getList(sysDictionary);
////				if(sysList!=null && sysList.size()>0){
////					houseName = sysList.get(0).getName();
////				}else{
////					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
////					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
////				}
////			}else{
////				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 空间已被删除！",msgId);
////			}
////			
////			if(houseName!=null && !"".equals(houseName)){
////				List<BaseProductStyle>styleList = null;
////				BaseProductStyle baseProductStyle = new BaseProductStyle();
////				baseProductStyle.setName(houseName);
////				baseProductStyle.setIsDeleted(0);
////				baseProductStyle.setLevel(1);
////				styleList = baseProductStyleService.getList(baseProductStyle);
////				if(styleList!=null && styleList.size()>0){
////					String name = styleList.get(0).getName();
////					if(houseName.equals(name)){
////						baseProductStyleSearch.setPid(styleList.get(0).getId());
////					}else{
////						return new ResponseEnvelope<BaseProductStyle>(false,"数据异常！",msgId);
////					}
////				}else{
////					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
////					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
////				}
////			}
////		}
////		
////		
////		//用于  方案推荐 风格过滤
////		if(houseType!=null && !"".equals(houseType)){
////			List<SysDictionary>sysList = null;
////			String houseName = null;
////			SysDictionary sysDictionary = new SysDictionary();
////			sysDictionary.setIsDeleted(0);
////			sysDictionary.setType("houseType");
////			sysDictionary.setValue(Integer.parseInt(houseType));
////			sysList = sysDictionaryService.getList(sysDictionary);
////			 
////			if(sysList!=null && sysList.size()>0){
////				houseName = sysList.get(0).getName();
////			}
////			if(houseName!=null && !"".equals(houseName)){
////				List<BaseProductStyle>styleList = null;
////				BaseProductStyle baseProductStyle = new BaseProductStyle();
////				baseProductStyle.setName(houseName);
////				baseProductStyle.setIsDeleted(0);
////				baseProductStyle.setLevel(1);
////				styleList = baseProductStyleService.getList(baseProductStyle);
////				if(styleList!=null && styleList.size()>0){
////					String name = styleList.get(0).getName();
////					if(houseName.equals(name)){
////						baseProductStyleSearch.setPid(styleList.get(0).getId());
////					}else{
////						return new ResponseEnvelope<BaseProductStyle>(false,"数据异常！",msgId);
////					}
////				}else{
////					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
////					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
////				}
////			}
////		}
////
////		try{
////			baseProductStyleSearch.setLevel(2);
////			baseProductStyleSearch.setIsDeleted(0);
////			baseProductStyleSearch.setSch_LongCode_(".root2.");
////			count = baseProductStyleService.getCount(baseProductStyleSearch);
////			baseProductStyleSearch.setLimit(count);
////			if(count!=null&&count.intValue()>0){
////				list = baseProductStyleService.getPaginatedList(baseProductStyleSearch);
////			}
////			if(list!=null && list.size()>0 && designPlan!=null){
////				Integer designRecommendedStyleId = designPlan.getDesignRecommendedStyleId();
////				if(designRecommendedStyleId!=null){
////					for (BaseProductStyle baseProductStyle : list) {
////						if(baseProductStyle.getId().intValue() == designRecommendedStyleId.intValue()){
////							baseProductStyle.setDesignPlanRecommendedBeSelected(1);
////						}
////					}
////				}
//// 
////			}
////		}catch (Exception e) {
////			logger.error("getDesignStyleList  methods the error  :" + e);
////			return new ResponseEnvelope<BaseProductStyle>(false,"数据错误",msgId);
////		}
////		return new ResponseEnvelope<BaseProductStyle>(count,list,msgId);
////	}
////	
////	
////	
////	
////	
////	
////	
////	
////	
////	/**
////	 * 获取设计方案风格 接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/getDesignStyleListNew")
////	@ResponseBody
////	public Object getDesignStyleListNew(HttpServletRequest request, HttpServletResponse response){
////		List<BaseProductStyle>list = null;
////		Integer count = 0;
////		String msgId = request.getParameter("msgId");
////		String planId = request.getParameter("planId");
////		String houseType = request.getParameter("houseType");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
////		}
////		BaseProductStyleSearch baseProductStyleSearch = new BaseProductStyleSearch();
////		
////		//    用于发布页面       风格按设计方案的空间类型显示，不在显示全部
////		if(StringUtils.isBlank(planId)&&StringUtils.isBlank(houseType)){
////			return new ResponseEnvelope<>(false,"planId  和   houseType  不能同时为空",msgId);
////		}
////		DesignPlan designPlan = null;
////		if(planId!=null&&!"".equals(planId)){
////			DesignTemplet designTemplet = null;
////			SpaceCommon spaceCommon = null;
////			Integer value = null;
////			designPlan = designPlanService.get(Integer.parseInt(planId));
////			if(designPlan != null){
////				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
////			}else{
////				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案已被删除！",msgId);
////			}
////
////			if(designTemplet != null ){
////				spaceCommon = spaceCommonService.get(designTemplet.getSpaceCommonId());
////			}else{
////				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 样板房已被删除！",msgId);
////			}
////			
////			if(spaceCommon != null ){
////				List<SysDictionary>sysList= null;
////				SysDictionary sysDictionary = new SysDictionary();
////				sysDictionary.setType("houseType");
////				sysDictionary.setValue(spaceCommon.getSpaceFunctionId());
////				sysList = sysDictionaryService.getList(sysDictionary);
////				if(sysList!=null && sysList.size()>0){
////					value = sysList.get(0).getValue();
////				}else{
////					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
////					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
////				}
////			}else{
////				return new ResponseEnvelope<BaseProductStyle>(false,"该设计方案 空间已被删除！",msgId);
////			}
////			
////			if(value!=null && value.intValue()>0){
////				List<BaseProductStyle>styleList = null;
////				BaseProductStyle baseProductStyle = new BaseProductStyle();
////				baseProductStyle.setNuma2(value);
////				baseProductStyle.setIsDeleted(0);
////				baseProductStyle.setLevel(1);
////				styleList = baseProductStyleService.getList(baseProductStyle);
////				if(styleList!=null && styleList.size()>0){
////					baseProductStyleSearch.setPid(styleList.get(0).getId());
////				}else{
////					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
////					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
////				}
////			}
////		}
////		//用于  方案推荐 风格过滤
////		if(houseType!=null && !"".equals(houseType)){
////				List<BaseProductStyle>styleList = null;
////				BaseProductStyle baseProductStyle = new BaseProductStyle();
////				baseProductStyle.setNuma2(Integer.parseInt(houseType));
////				baseProductStyle.setIsDeleted(0);
////				baseProductStyle.setLevel(1);
////				styleList = baseProductStyleService.getList(baseProductStyle);
////				if(styleList!=null && styleList.size()>0){
////					baseProductStyleSearch.setPid(styleList.get(0).getId());
////				}else{
////					//return new ResponseEnvelope<BaseProductStyle>(false,"找不到 设计方案 的相关风格！",msgId);
////					return new ResponseEnvelope<BaseProductStyle>(0,null,msgId);
////				}
////			}
////		try{
////			baseProductStyleSearch.setLevel(2);
////			baseProductStyleSearch.setIsDeleted(0);
////			baseProductStyleSearch.setSch_LongCode_(".root2.");
////			count = baseProductStyleService.getCount(baseProductStyleSearch);
////			baseProductStyleSearch.setLimit(count);
////			if(count!=null&&count.intValue()>0){
////				list = baseProductStyleService.getPaginatedList(baseProductStyleSearch);
////			}
////			if(list!=null && list.size()>0 && designPlan!=null){
////				Integer designRecommendedStyleId = designPlan.getDesignRecommendedStyleId();
////				for (BaseProductStyle baseProductStyle : list) {
////					if(baseProductStyle.getId().intValue() == designRecommendedStyleId.intValue()){
////						baseProductStyle.setDesignPlanRecommendedBeSelected(1);
////					}
////				}
////			}
////		}catch (Exception e) {
////			logger.error("getDesignStyleList  methods the error  :" + e);
////			return new ResponseEnvelope<BaseProductStyle>(false,"数据错误",msgId);
////		}
////		return new ResponseEnvelope<BaseProductStyle>(count,list,msgId);
////	}
////	
////	
////	/**
////	 * 取得该设计方案的  审核人员列表
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/planCheckUserList")
////	@ResponseBody
////	public Object planCheckUserList(HttpServletRequest request, HttpServletResponse response){
////		List<SysUserPlanRecommended>list = null;
////		DesignPlan designPlan = null;
////		String msgId = request.getParameter("msgId");
////		String planId = request.getParameter("planId");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数msgId",msgId);
////		}
////		if(StringUtils.isBlank(planId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数planId",msgId);
////		}
////		designPlan = designPlanService.get(Integer.parseInt(planId));
////		if(designPlan == null){
////			return new ResponseEnvelope<BaseProductStyle>(false,"方案id:"+planId+"不存在，可能已被删除",msgId);
////		}
////		//取得该设计方案的  审核人员列表
////		list = designPlanCheckService.planCheckUserList(designPlan);
////		return new ResponseEnvelope<SysUserPlanRecommended>(list.size(),list,msgId);
////	}
////	
////	
////	/**
////	 * 删除 审核人员
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/delPlanCheckUser")
////	@ResponseBody
////	public Object delPlanCheckUser(HttpServletRequest request, HttpServletResponse response){
////		String msgId = request.getParameter("msgId");
////		String designPlanCheckId = request.getParameter("designPlanCheckId");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数msgId",msgId);
////		}
////		if(StringUtils.isBlank(designPlanCheckId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数designPlanCheckId",msgId);
////		}
////		try{
////			designPlanCheckService.delete(Integer.parseInt(designPlanCheckId));
////		}catch (Exception e) {
////			logger.error("delPlanCheckUser  methods the error  :" + e);
////			return new ResponseEnvelope<BaseProductStyle>(false,"删除失败，数据错误",msgId);
////		}
////		return new ResponseEnvelope<BaseProductStyle>(true,"删除成功",msgId);
////	}
////	
////	
////	
////	/**
////	 * 方案推荐审核接口
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/designPlanRecommendedCheck")
////	@ResponseBody
////	public Object designPlanRecommendedCheck(HttpServletRequest request, HttpServletResponse response){
////		String msgId =  request.getParameter("msgId");
////		String planId =  request.getParameter("planId");
////		String userId =  request.getParameter("userId");
////		String isRelease =  request.getParameter("isRelease");
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数msgId",msgId);
////		}
////		if(StringUtils.isBlank(planId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数planId",msgId);
////		}
////		if(StringUtils.isBlank(userId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数userId",msgId);
////		}
////		if(StringUtils.isBlank(isRelease)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数isRelease",msgId);
////		}
////		int isReleaseInt = Integer.parseInt(isRelease);
////		if(isReleaseInt != DesignPlanRelease.is_release && isReleaseInt != DesignPlanRelease.failure_check){
////			return new ResponseEnvelope<BaseProductStyle>(false,"参数错误isRelease",msgId);
////		}
////		DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
////		if(designPlan == null){
////			return new ResponseEnvelope<BaseProductStyle>(false,"不存在该方案，方案id为："+planId,msgId);
////		}
////		SysUser sysUser = sysUserService.get(Integer.parseInt(userId));
////		if(sysUser == null){
////			return new ResponseEnvelope<BaseProductStyle>(false,"不存在该用户，id为："+userId,msgId);
////		}
////		
////		
////		try{
////			//判断是否有审核权限
////			boolean permissions = false;
////			permissions =  designPlanRecommendedService.isDesignPlanCheck(null, Integer.parseInt(userId));
////			//审核通过逻辑
////			if(designPlan.getIsRelease().intValue() == DesignPlanRelease.is_release
////					|| designPlan.getIsRelease().intValue() == DesignPlanRelease.failure_check){
////				return new ResponseEnvelope<DesignPlan>(true,"请勿重复操作！",msgId);
////			}
////			if(isReleaseInt == DesignPlanRelease.is_release){
////				if(permissions){
////					Map<String,String>resMap = designPlanRecommendedService.planIsReleaseCheck(designPlan,null);
////					if("false".equals(resMap.get("success"))){
////						return new ResponseEnvelope<DesignPlanRecommendedResult>(false,resMap.get("data"),msgId);
////					}
////					if(!"true".equals(resMap.get("success"))){
////						return new ResponseEnvelope<DesignPlanRecommendedResult>(false,resMap.get("data"),msgId);
////					}
////					//判断是产品 是否都是发布中
////					/*DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
////					designPlanProductSearch.setPlanId(designPlan.getId());
////					designPlanProductSearch.setIsDeleted(0);
////					List<DesignPlanProduct> designPlanProducts = designPlanProductService.getList(designPlanProductSearch);
////					if( designPlanProducts != null && designPlanProducts.size() > 0 ){
////						for( DesignPlanProduct designPlanProduct : designPlanProducts ){
////							Integer productId = designPlanProduct.getProductId();
////							if( productId != null ){
////								BaseProduct baseProduct = baseProductService.get(productId);
////								if( baseProduct != null ){
////									if(baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_RELEASE.intValue()&&
////											baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_UP.intValue()&&
////													baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_TEST.intValue()
////											){
////										return new ResponseEnvelope<BaseProductStyle>(false,"产品 ["+baseProduct.getProductCode()+"] 未发布,请替换或删除该产品,再进行发布!",msgId);
////									}
////								}
////							}
////						}
////					}*/
////					//修改设计方案状态
////					DesignPlan newDesignPlan = new DesignPlan();
////					newDesignPlan.setId(Integer.parseInt(planId));
////					newDesignPlan.setIsRelease(DesignPlanRelease.is_release);
////					designPlanService.update(newDesignPlan);
////				}
////			//审核失败逻辑
////			}else if(isReleaseInt == DesignPlanRelease.failure_check){
////				DesignPlan newDesignPlan = new DesignPlan();
////				newDesignPlan.setId(Integer.parseInt(planId));
////				newDesignPlan.setIsRelease(DesignPlanRelease.failure_check);
////				designPlanService.update(newDesignPlan);
////			}
////		}catch (Exception e) {
////			logger.error("designPlanRecommendedCheck  methods the error  :" + e);
////			return new ResponseEnvelope<BaseProductStyle>(false,"审核失败，数据错误",msgId);
////		}
////		return new ResponseEnvelope<DesignPlan>(true,"操作成功",msgId);
////	}
////	
////	
////	/**
////	 * 审核列表
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/designPlanCheckList")
////	@ResponseBody
////	public Object designPlanCheckList(HttpServletRequest request, HttpServletResponse response){
////		List<DesignPlanRecommendedResult>list = null;
////		int total = 0;
////		String msgId =  request.getParameter("msgId");
////		String userId =  request.getParameter("userId");
////		String spaceFunctionId =  request.getParameter("spaceFunctionId");
////		/*String planNumber =  request.getParameter("planNumber");*/
////		String planName =  request.getParameter("planName");
////		/*String brandName =  request.getParameter("brandName");*/
////		if(StringUtils.isBlank(userId)){
////			return new ResponseEnvelope<BaseProductStyle>(false,"缺少参数userId",msgId);
////		}
////		DesignPlanCheck designPlanCheck = new DesignPlanCheck();
////		if(StringUtils.isNotBlank(spaceFunctionId) && !"-1".equals(spaceFunctionId)){
////			
////			designPlanCheck.setSpaceFunctionId(Integer.parseInt(spaceFunctionId));
////		}
////		if(StringUtils.isNotBlank(planName)){
////			if(planName.startsWith("@") && planName.endsWith("@")){
////				planName = planName.replace("@", "");
////				designPlanCheck.setBrandName(planName);
////			}else if(planName.startsWith("*") && planName.endsWith("*")){
////				planName = planName.replace("*", "");
////				designPlanCheck.setPlanNumber(planName);
////			}else{ 
////				designPlanCheck.setPlanName(planName);
////			}
////		}
////		/*if(StringUtils.isNotBlank(planNumber)){
////			designPlanCheck.setPlanNumber(planNumber);	
////		}
//// 
////		if(StringUtils.isNotBlank(brandName)){
////			designPlanCheck.setBrandName(brandName);
////		}*/
////		try{
////			 
////			designPlanCheck.setUserId(Integer.parseInt(userId));
////			total = designPlanCheckService.getDesignPlanCheckCount(designPlanCheck);
////			if(total>0){
////				list = designPlanCheckService.getDesignPlanCheckList(designPlanCheck);
////			}
////		}catch (Exception e) {
////			logger.error("designPlanCheckList  methods the error  :" + e);
////			return new ResponseEnvelope<BaseProductStyle>(false,"数据错误",msgId);
////		}
////		return new ResponseEnvelope<DesignPlanRecommendedResult>(total,list,msgId);
////	}
////	
////	/**
////	 * 方案发布回显功能
////	 * @param request
////	 * @param response
////	 * @return
////	 */
////	@RequestMapping("/releaseDetails")
////	@ResponseBody
////	public Object releaseDetails (HttpServletRequest request, HttpServletResponse response){
////		DesignPlan designPlan = null;
////		String planId = request.getParameter("planId");
////		String msgId = request.getParameter("msgId");
////		if(StringUtils.isBlank(planId)){
////			return new ResponseEnvelope<DesignPlan>(false,"缺少参数planId",msgId);
////		}
////		if(StringUtils.isBlank(msgId)){
////			return new ResponseEnvelope<DesignPlan>(false,"缺少参数msgId",msgId);
////		}
////		designPlan = designPlanService.get(Integer.parseInt(planId));
////		if(designPlan == null){
////			return new ResponseEnvelope<DesignPlan>(false,"id为"+planId+"的方案不存在!",msgId);
////		}
////		return new ResponseEnvelope<DesignPlan>(designPlan,msgId,true);
////	}
////}>>>>>>> .merge-right.r19885
