package com.nork.design.controller.web;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.exception.IntelligenceDecorationException;
import com.nork.design.model.BedroomProductMatchDTO;
import com.nork.design.model.BedroomProductMatchDTO.GroupMatchInfoDTO;
import com.nork.design.model.BedroomProductMatchDTO.ProductMatchInfoDTO;
import com.nork.design.model.BedroomProductMatchDTO.StructureMatchInfoDTO;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanOperationLog;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignPlanRes;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.DesignTempletProductResult;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductListByTypeInfo;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.model.constant.PlanVisibleCode;
import com.nork.design.service.DesignPlanOperationLogService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.IntelligenceDecorationService;
import com.nork.design.service.OptimizePlanService;
import com.nork.design.service.TempletPlanOperationHistoryService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.MatchGroupProductResult;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseProductService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.SysDictionaryService;

import net.sf.json.JSONArray;

/**
 * 一键装修
 * 
 * @author Steve.Zheng
 * 
 */
@Controller
@RequestMapping("/{style}/web/design/intelligenceDecoration")
public class IntelligenceDecorationController {
	private static Logger logger = Logger
			.getLogger(IntelligenceDecorationController.class);


	@Autowired
	private IntelligenceDecorationService intelligenceDecorationService;
	@Autowired
	private TempletPlanOperationHistoryService templetPlanOperationHistoryService;
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private ResDesignService resDesignService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private OptimizePlanService optimizePlanService;
	@Autowired
	private DesignPlanOperationLogService designPlanOperationLogService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
	
	// 用于debug(最后要删除) ->start
	public static String posNameDebug = null;
	// 用于debug(最后要删除) ->end
	
	/**
	 * 一键替换获取配置文件
	 * 
	 * @param recommendedPlanId   推荐方案ID
	 * @param designTempletId   样板房ID
	 * @return object
	 */
	@RequestMapping("/findDesignConfig")
	@ResponseBody
	public ResponseEnvelope<Map<String, String>> findDesignConfig(
			Integer recommendedPlanId, Integer designTempletId, String msgId,
			HttpServletRequest request) {
		ResponseEnvelope<Map<String, String>> result = null;
		boolean valid = checkTheParamIsValid(msgId, recommendedPlanId, designTempletId);
		if (!valid) {
			result = new ResponseEnvelope<Map<String, String>>(false,
					"请输入正确的参数！", msgId);
			logger.error("The parameter is invalid!");
			return result;
		}
		return intelligenceDecorationService.findDesignConfig(recommendedPlanId, designTempletId, msgId);
	}

	/**
	 * 一键替换 样板房白膜产品匹配数据
	 * 
	 * @param posNames 白膜多个节点信息
	 * @param recommendedPlanId 推荐方案ID
	 * @return object
	 */
	@RequestMapping("/findByItemCodeInfo")
	@ResponseBody
	public ResponseEnvelope<Map<String, String>> findByItemCodeInfo(
			String posNames, Integer designTempletId, Integer recommendedPlanId,
			String account, String msgId, HttpServletRequest request) {

		ResponseEnvelope<Map<String, String>> result = null;
		
		// 参数验证 ->start
		boolean valid = checkTheParamIsValid(msgId, posNames);
		if (!valid) {
			result = new ResponseEnvelope<Map<String, String>>(false, "请输入正确的参数！", msgId);
			logger.error("The parameter is invalid!");
			return result;
		}
		if(designTempletId == null){
			return new ResponseEnvelope<Map<String, String>>(false,
					"参数designTempletId不能为空", msgId);
		}
		
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if(designTemplet == null){
			logger.info("------designTempletId:" + designTempletId);
			return new ResponseEnvelope<Map<String, String>>(false,
					"该样板房没有找到", msgId);
		}
		
		if(recommendedPlanId == null){
			return new ResponseEnvelope<Map<String, String>>(false,
					"参数recommendedPlanId不能为空", msgId);
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if(designPlanRecommended == null){
			logger.error("recommendedPlanId:" + recommendedPlanId);
		}
		// 参数验证 ->end
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		loginUser.setLoginName(account);

		List<Map<String, String>> returnMapList;
		try {
			returnMapList = intelligenceDecorationService.findByItemCodeInfoV2(posNames,
					designTemplet, designPlanRecommended, loginUser);
		} catch (IntelligenceDecorationException e) {
			return new ResponseEnvelope<>(false, e.getMessage(), msgId);
		}
		return new ResponseEnvelope<Map<String, String>>(Utils.getListTotal(returnMapList), returnMapList, msgId);
	}

	/**
	 * 一键生成设计方案 根据配置生成设计方案及设计方案产品列表
	 * @param recommendedPlanId  推荐方案ID
	 * @param designTempletId 样板房ID
	 * @param context  设计方案配置内容
	 * @param isAllreplace  区分硬装全屋替换（0硬装，1全屋）
	 * @return object
	 */
	@RequestMapping("/findGeneratePlanInfo")
	@ResponseBody
	public ResponseEnvelope<UnityDesignPlan> findGeneratePlanInfo(
			Integer designTempletId, Integer recommendedPlanId, String context,
			String msgId, HttpServletRequest request, Integer isAllreplace,Integer opType ) {
		// test ->start
		/*context = TestCreateByHuangsongbo.context;*/
		// test ->end
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String mediaType = SystemCommonUtil.getMediaType(request);
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<>(false, "参数msgId不能为空！", msgId);
		}
		// 如果没有传opType 默认为用户一键装修
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		ResponseEnvelope<UnityDesignPlan> result = null;
		if(StringUtils.isBlank(context)){
			context = Utils.getValue("test","test");
		}
		boolean valid = checkTheParamIsValid(context, designTempletId, recommendedPlanId);
		if (!valid) {
			result = new ResponseEnvelope<UnityDesignPlan>(false, "请输入正确的参数！",
					msgId);
			logger.error("The parameter is invalid!");
			return result;
		}

		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if (designPlanRecommended != null) {
			//如果是已发布的设计方案被使用就记录下来
			templetPlanOperationHistoryService.saveOrUpdate(recommendedPlanId, designTempletId, loginUser.getId(), isAllreplace);
		}
			logger.info("findGeneratePlanInfo=>opType:"+opType);
		if (opType.intValue() == DesignPlanConstants.AUTO_RENDER.intValue()) {
			return optimizePlanService.findOnekeyAutoRenderPlanInfo(designTempletId, designPlanRecommended, context, msgId, loginUser, mediaType);
//			return designPlanAutoRenderOnekeyService.findGeneratePlanInfo(designTempletId, recommendedPlanId, context, msgId, loginUser, mediaType);
		}else {
			return intelligenceDecorationService.findGeneratePlanInfo(designTempletId, designPlanRecommended, context, msgId, loginUser, mediaType);
		}

	}

	/**
	 * 一键替换结构匹配(多个结构)
	 * created by xiaoxiangcheng
	 * modify by huangsongbo
	 * 
	 * @param designTempletId
	 * @param recommendedPlanId
	 * @param regionMarkAltered 被替换过的结构(前端传,返回的结构数据,让发现有的结构没有被替换过其他结构,就不返回(remove)))
	 * @return
	 */
	@RequestMapping("/matchingStructureData")
	@ResponseBody
	public ResponseEnvelope<SearchStructureProductResult> matchingStructureData(
			Integer designTempletId,
			Integer recommendedPlanId,
			String msgId, 
			String regionMarkAltered,
			HttpServletRequest request) {
		// 验证参数 ->start
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SearchStructureProductResult>(false,
					"参数msgId不能为空", msgId);
		}
		
		if(designTempletId == null){
			return new ResponseEnvelope<SearchStructureProductResult>(false,
					"参数designTempletId不能为空", msgId);
		}
		
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if(designTemplet == null){
			logger.error("------function:matchingStructureData(...)->样板房(DesignTemplet) not found;designTempletId:" + designTempletId);
			return new ResponseEnvelope<SearchStructureProductResult>(false,
					"该样板房没有找到", msgId);
		}
		
		if(recommendedPlanId == null){
			return new ResponseEnvelope<SearchStructureProductResult>(false,
					"参数recommendedPlanId不能为空", msgId);
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if(designPlanRecommended == null){
			logger.error("------function:matchingStructureData(...)->方案推荐(DesignPlanRecommended) not found;recommendedPlanId:" + recommendedPlanId);
			return new ResponseEnvelope<SearchStructureProductResult>(false,
					"该一建生成方案没有找到", msgId);
		}
		// 验证参数 ->end
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		List<String> regionMarkList = Utils.getListFromStr(regionMarkAltered);
		List<SearchStructureProductResult> searchStructureProductResult = intelligenceDecorationService.matchingStructureDataV2(designTemplet, designPlanRecommended, loginUser, regionMarkList);
		return new ResponseEnvelope<>(searchStructureProductResult.size(), searchStructureProductResult, msgId);
	}

	/**
	 * 获取样板房产品结构或组合产品列表
	 * @param designTempletId 样板房ID
	 * @return object
	 */
	@RequestMapping("/findDesignTempletProductList")
	@ResponseBody
	public ResponseEnvelope<DesignTempletProductResult> findDesignTempletProductList(
			Integer designTempletId, Integer groupType, String msgId,
			HttpServletRequest request) {
		ResponseEnvelope<DesignTempletProductResult> result = null;
		boolean valid = checkTheParamIsValid(msgId, designTempletId, groupType);
		if (!valid) {
			result = new ResponseEnvelope<DesignTempletProductResult>(false,
					"请输入正确的参数！", msgId);
			logger.error("The parameter is invalid!");
			return result;
		}
		return intelligenceDecorationService.findDesignTempletProductList(
				designTempletId, groupType, msgId);
	}

	/**
	 * 一键替换组合
	 * @param recommendedPlanId 推荐方案ID
	 * @param designTempletId  样板房ID
	 * @return object
	 */
	@RequestMapping("/getGroupProductData_old")
	@ResponseBody
	public ResponseEnvelope<MatchGroupProductResult> getGroupProductData_old(
			Integer recommendedPlanId, Integer designTempletId, String msgId,
			HttpServletRequest request) {
		ResponseEnvelope<MatchGroupProductResult> result = null;
		boolean valid = checkTheParamIsValid(msgId, designTempletId, recommendedPlanId);
		if (!valid) {
			result = new ResponseEnvelope<MatchGroupProductResult>(false,
					"请输入正确的参数！", msgId);
			logger.error("The parameter is invalid!");
			return result;
		}
		Integer planId = getPlanId(recommendedPlanId);
		if (planId == 0) {
			return new ResponseEnvelope<>(false,"找不到推荐方案！", msgId);
		}
		
		return intelligenceDecorationService.getGroupProductData(planId,designTempletId, msgId);
	}

	@RequestMapping("/getGroupProductData")
	@ResponseBody
	public ResponseEnvelope<MatchGroupProductResult> getGroupProductData(
			Integer recommendedPlanId, Integer designTempletId, String msgId,
			HttpServletRequest request) {
		
		// 验证参数 ->start
		if(designTempletId == null){
			return new ResponseEnvelope<MatchGroupProductResult>(false,
					"参数designTempletId不能为空", msgId);
		}
		
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if(designTemplet == null){
			logger.error("------function:getGroupProductData(...)->样板房(DesignTemplet) not found;designTempletId:" + designTempletId);
			return new ResponseEnvelope<MatchGroupProductResult>(false,
					"该样板房没有找到", msgId);
		}
		
		if(recommendedPlanId == null){
			return new ResponseEnvelope<MatchGroupProductResult>(false,
					"参数recommendedPlanId不能为空", msgId);
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if(designPlanRecommended == null){
			logger.error("------function:getGroupProductData(...)->方案推荐(DesignPlanRecommended) not found;recommendedPlanId:" + recommendedPlanId);
			return new ResponseEnvelope<MatchGroupProductResult>(false,
					"该一建生成方案没有找到", msgId);
		}
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<MatchGroupProductResult>(false,
					"参数msgId不能为空", msgId);
		}
		// 验证参数 ->end

		List<MatchGroupProductResult> result = intelligenceDecorationService.getGroupProductDataV2(designPlanRecommended, designTemplet);
		
		return new ResponseEnvelope<MatchGroupProductResult>(Utils.getListTotal(result), result, msgId);
	}
	
	/**
	 * 获取样板房产品序号配置文件
	 * 
	 * @param templetCode
	 *            样板房code
	 * @return object
	 */
	@RequestMapping("/byConfigSaveProductIndex")
	@ResponseBody
	public ResponseEnvelope byConfigSaveProductIndex(String msgId,
			String templetCode, String context, HttpServletRequest request) {
		//context有特殊字符客户端转码utf-8，需解码
		context = Utils.decodeContext(context,context);
		ResponseEnvelope result = null;
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null) {
			loginUser = new LoginUser();
			loginUser.setLoginName("noLogin");
			loginUser.setId(-1);
		}
		boolean valid = checkTheParamIsValid(msgId, templetCode, context);
		if (!valid) {
			result = new ResponseEnvelope<>(false, "请输入正确的参数！", msgId);
			logger.error("The parameter is invalid!");
			return result;
		}
		return intelligenceDecorationService.byConfigSaveProductIndex(msgId,
				templetCode, context, loginUser);

	}

	/**
	 * 获取样板房产品序号配置文件
	 * 
	 * @param templetCode
	 *            样板房code
	 * @return object
	 */
	@RequestMapping("/getTempletProductIndexConfig")
	@ResponseBody
	public ResponseEnvelope<Object> getTempletProductNumberConfig(String msgId,
			String templetCode, Integer isStandard, String center,
			String regionMark, Integer styleId, String measureCode,
			Integer productIndex) {
		ResponseEnvelope result = null;
		boolean valid = checkTheParamIsValid(msgId, templetCode);
		if (!valid) {
			result = new ResponseEnvelope<>(false, "请输入正确的参数！", msgId);
			logger.error("The parameter is invalid!");
			return result;
		}
		return intelligenceDecorationService.getTempletProductIndexConfig(
				msgId, templetCode);

	}

	// 判断参数是否有效和完整
	private boolean checkTheParamIsValid(String msgId, Integer... numberObjs) {
		boolean valid = true;
		if (StringUtils.isBlank(msgId)) {
			valid = false;
			return valid;
		}
		for (Integer numberV : numberObjs) {
			if (numberV == null) {
				valid = false;
				break;
			}
		}
		return valid;
	}

	private boolean checkTheParamIsValid(String... args) {
		boolean valid = true;
		for (String arg : args) {
			if (StringUtils.isBlank(arg)) {
				valid = false;
				return valid;
			}
		}
		return valid;
	}

	private Integer getPlanId(Integer recommendedPlanId){
		Integer planId = 0;
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if (designPlanRecommended != null) {
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanRecommended.getPlanId());
			if (designPlanRenderScene != null) {
				planId = designPlanRenderScene.getDesignPlanId();
			}
		}
		return planId;
	}
	
    /**
	 * 
	 * 根据配置生成设计方案及设计方案产品列表
	 * 
	 * @param recommendedPlanId
	 *            推荐方案ID
	 * @param designTempletId
	 *            样板房ID
	 * @param isAllreplace
	 *            区分硬装全屋替换（0硬装，1全屋）
	 * @return object
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
	@RequestMapping("/findGeneratePlanInfoOnekey")
	@ResponseBody
	public ResponseEnvelope<UnityDesignPlan> findGeneratePlanInfoOnekey(
			Integer designTempletId, Integer recommendedPlanId,
			String msgId, HttpServletRequest request, Integer isAllreplace,Integer isParent,Integer opType) throws IllegalAccessException, InvocationTargetException {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String mediaType = SystemCommonUtil.getMediaType(request);
		
		if (designTempletId == null ) {
			return new ResponseEnvelope<>(false, "参数designTempletId不能为空", msgId);
		}
		if (recommendedPlanId == null ) {
			return new ResponseEnvelope<>(false, "参数recommendedPlanId不能为空", msgId);
		}
		
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<>(false, "参数msgId不能为空！", msgId);
		}
		logger.warn("开始根据配置生成方案！");
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if (designPlanRecommended != null) {
			//如果是已发布的设计方案被使用就记录下来
			templetPlanOperationHistoryService.saveOrUpdate(recommendedPlanId, designTempletId, loginUser.getId(), isAllreplace);
		}
		return optimizePlanService.getUnityDesignPlan(recommendedPlanId, designTempletId, isParent, isAllreplace, msgId, loginUser, mediaType,opType);

	}
	
	
	/**
	 * 一键生成更新设计方案配置文件
	 *
	 * @param planId
	 * @param msgId
	 * @param context
	 * @return
	 */
	@RequestMapping(value = "/updatePlanConfigOnekey")
	@ResponseBody
	public Object updatePlanConfig(Integer planId, String msgId,
								   String context, Integer planProductId,String bDirtyConfig, HttpServletRequest request,Integer opType) {
		Long startTime = System.currentTimeMillis();
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "planId不能为空！", msgId);
		}
		if (StringUtils.isEmpty(bDirtyConfig)){
			return new ResponseEnvelope<DesignPlan>(false, "bDirtyConfig参数不能为空！",
					msgId);
		}
		if (StringUtils.isEmpty(context)) {//老的校验是isBlank ，不要用isBlank，如果是空字符串就校验通过了，这个不行
			return new ResponseEnvelope<DesignPlan>(false, "context参数不能为空！",
					msgId);
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}

		// TODO:创建设计方案操作日志记录的数据>>start
		DesignPlanOperationLog designPlanOperationLog = new DesignPlanOperationLog();
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		
		designPlanOperationLog.setUserId(loginUser.getId());
		designPlanOperationLog.setDesignPlanId(planId);
		designPlanOperationLog.setStatus(1);
		designPlanOperationLog.setBusinessKey(SystemCommonConstant.QUIT_SAVE_DESIGN_PLAN);
		designPlanOperationLog.setGmtCreate(new Date());
		designPlanOperationLog.setIsDeleted(0);
		designPlanOperationLog.setCreator(loginUser.getLoginName());
		designPlanOperationLog.setSysCode(Utils
				.getCurrentDateTime(Utils.DATETIMESSS)
				+ "_"
				+ Utils.generateRandomDigitString(6));
		designPlanOperationLog.setModifier(loginUser.getLoginName());
		designPlanOperationLog.setGmtModified(new Date());
		designPlanOperationLogService.insertSelective(designPlanOperationLog);
		// 添加数据结束<<end
		if (opType == DesignPlanConstants.USER_RENDER.intValue() ) {
			try {
				DesignPlan designPlan = null;
				if (Utils.enableRedisCache()) {
					designPlan = DesignPlanCacher.getDesignPlan(planId);
				} else {
					designPlan = designPlanService.get(planId);
				}
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setId(planId);
				/* 设置设计方案ischange为1(设计方案被改变) */
				newDesignPlan.setIsChange(new Integer(1));
				if ("true".equals(bDirtyConfig.toLowerCase())){
					newDesignPlan.setSceneModified(Utils.getCurrentTimeMillis());//设计方案的配置文件发生改变要更新时间戳
					newDesignPlan.setVisible(PlanVisibleCode.DESIGN_VISIBLE);
				}
				designPlanService.update(newDesignPlan);
				/* 设置设计方案ischange为1(设计方案被改变)->end */

				// 解除planProductId设计产品的组(解组)->end
				ResDesign resDesign = null;
				if (Utils.enableRedisCache()) {
					resDesign = ResourceCacher.getResDesign(designPlan
							.getConfigFileId());
				} else {
					resDesign = resDesignService.get(designPlan.getConfigFileId());
				}
				if (resDesign != null) {

				}

				/**
				 * 更新配置文件和设计方案产品列表中的挂节点信息
				 * 先更新数据库，再更新配置文件
				 **/
				boolean flag = designPlanService.updatePlanProductByConfig(context, planId,"true".equals(bDirtyConfig.toLowerCase())?true:false);
				
				if (!flag) {
					return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！",msgId);
				}else{
					String filePath = FileUploadUtils.UPLOAD_ROOT
							+ resDesign.getFilePath().replace("/", "\\");
					if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
						filePath = FileUploadUtils.UPLOAD_ROOT
								+ resDesign.getFilePath();
					}
					/**
					 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，
					 * 则上传到web服务器。
					 **/
					Integer ftpUploadMethod = Utils.getIntValue(
							FileUploadUtils.FTP_UPLOAD_METHOD, 1);
					boolean uploadFtpFlag = false;
					// 上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 1 || ftpUploadMethod == 3) {
						// 替换本地
						uploadFtpFlag = Utils.replaceFile(filePath, context);
						if (ftpUploadMethod == 3) {
							// 替换ftp
							uploadFtpFlag = FtpUploadUtils.replaceFile(
									resDesign.getFilePath(), context);
						}
					} else if (ftpUploadMethod == 2) {
						// 替换ftp
						uploadFtpFlag = FtpUploadUtils.replaceFile(
								resDesign.getFilePath(), context);
					}
				}
				//copy 
				DesignPlanRes designPlanRes =optimizePlanService.getDesignPlanRes(planId);
				Long resultId = optimizePlanService.saveAsRenderOnekey(designPlanRes);
				logger.info("findGeneratePlanInfo==>insert to DesignPlan id ==>"+resultId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
			}
			Long endTime = System.currentTimeMillis();
			return new ResponseEnvelope<DesignPlan>(true, "配置文件更新成功！", msgId);
		}else {
			try {
				DesignPlan designPlan = null;
				if (Utils.enableRedisCache()) {
					designPlan = DesignPlanCacher.getDesignPlan2(planId);
				} else {
					designPlan = optimizePlanService.getPlan(planId);
				}
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setId(planId);
				/* 设置设计方案ischange为1(设计方案被改变) */
				newDesignPlan.setIsChange(new Integer(1));
				if ("true".equals(bDirtyConfig.toLowerCase())){
					newDesignPlan.setSceneModified(Utils.getCurrentTimeMillis());//设计方案的配置文件发生改变要更新时间戳
					newDesignPlan.setVisible(PlanVisibleCode.DESIGN_VISIBLE);
				}
				optimizePlanService.update(newDesignPlan);
				/* 设置设计方案ischange为1(设计方案被改变)->end */

				// 解除planProductId设计产品的组(解组)->end
				ResDesign resDesign = null;
				if (Utils.enableRedisCache()) {
					resDesign = ResourceCacher.getResDesign2(designPlan
							.getConfigFileId());
				} else {
					resDesign = optimizePlanService.get(designPlan.getConfigFileId());
				}
				if (resDesign != null) {

				}

				/**
				 * 更新配置文件和设计方案产品列表中的挂节点信息
				 * 先更新数据库，再更新配置文件
				 **/
				boolean flag = optimizePlanService.updatePlanProductByConfig(context, planId);
				if (!flag) {
					return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！",msgId);
				}else{
					String filePath = FileUploadUtils.UPLOAD_ROOT
							+ resDesign.getFilePath().replace("/", "\\");
					if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
						filePath = FileUploadUtils.UPLOAD_ROOT
								+ resDesign.getFilePath();
					}
					/**
					 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，
					 * 则上传到web服务器。
					 **/
					Integer ftpUploadMethod = Utils.getIntValue(
							FileUploadUtils.FTP_UPLOAD_METHOD, 1);
					boolean uploadFtpFlag = false;
					// 上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 1 || ftpUploadMethod == 3) {
						// 替换本地
						uploadFtpFlag = Utils.replaceFile(filePath, context);
						if (ftpUploadMethod == 3) {
							// 替换ftp
							uploadFtpFlag = FtpUploadUtils.replaceFile(
									resDesign.getFilePath(), context);
						}
					} else if (ftpUploadMethod == 2) {
						// 替换ftp
						uploadFtpFlag = FtpUploadUtils.replaceFile(
								resDesign.getFilePath(), context);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
			}
			Long endTime = System.currentTimeMillis();
			return new ResponseEnvelope<DesignPlan>(true, "配置文件更新成功！", msgId);
		}
		
		 

	}
	
	/**
	 * 获取结算列表--接口
	 * 
	 * @param designPlanProduct
	 * @return
	 */
	@RequestMapping(value = "/costListWebAutoRender")
	@ResponseBody
	public Object costListWebAutoRender(DesignPlanProduct designPlanProduct,
			String terminalImei, HttpServletRequest request) {
		/*验证参数*/
		if (designPlanProduct.getPlanId() == null) {
			return new ResponseEnvelope<ProductsCost>(false, "planId不能为空!",designPlanProduct.getMsgId());
		}
		if (StringUtils.isBlank(terminalImei)) {
			return new ResponseEnvelope<ProductsCost>(false,"terminalImei不能为空!", designPlanProduct.getMsgId());
		}
		/*验证参数->end*/
		/*获取登录用户信息*/
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
			return new ResponseEnvelope<ProductsCost>(false, "当前登录用户超时，请重新登录!",designPlanProduct.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			/*传入查询条件(userId)*/
			designPlanProduct.setUserId(loginUser.getId());
		}
		/*获取登录用户信息->end*/
		/*查询用户关联的序列号list*/
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(new Integer(1));
		//authorizedConfig.setTerminalImei(terminalImei);
		List<AuthorizedConfig> list = authorizedConfigService.getList(authorizedConfig);
		/*查询用户关联的序列号list->end*/
		/*add品牌,大类,小类,产品查询条件*/
		List<BaseProduct> baseProductList=new ArrayList<BaseProduct>();
		if(3 == loginUser.getUserType()){
			if(list!=null&&list.size()>0){
				for(AuthorizedConfig authorizedConfigItem:list){
					/*查询条件注入到BaseProduct类中*/
					BaseProduct baseProduct=baseProductService.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
					// 如果授权码查询条件的品牌/大类/小类/productIdList为空,则continue
					if(
							baseProduct.getBrandId() == null && 
							StringUtils.isBlank(baseProduct.getProductTypeValue()) &&
							baseProduct.getProductSmallTypeValue() == null &&
							(baseProduct.getProductIdList() == null || baseProduct.getProductIdList().size() == 0)
							){
						continue;
					}
					baseProductList.add(baseProduct);
				}
				/*设置查询条件(序列号)*/
				designPlanProduct.setBaseProduct(baseProductList);
			}
		}
		/*add品牌,大类,小类,产品查询条件->end*/
//		int total = designPlanProductService.costTypeListCount(designPlanProduct);
		int total = optimizePlanService.costTypeListCount(designPlanProduct);
		List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
		List<ProductsCost> costList = new ArrayList<ProductsCost>();
		if( total > 0 ){
			SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
			/*查询软硬装下面包含的产品小类等信息(带入序列号查询条件)*/
//			costTypeList = designPlanProductService.costTypeList(designPlanProduct);
			costTypeList = optimizePlanService.costTypeList(designPlanProduct);
			for(ProductsCostType costType : costTypeList){
				costType.setPlanId(designPlanProduct.getPlanId());
				costType.setUserId(loginUser.getId());
				//costType.setAuthorizedConfigList(list);
				costType.setBaseProduct(baseProductList);
				/*得到结算汇总清单
				 * 设置查询条件(序列号)*/
//				costList = designPlanProductService.costList(costType); 
				costList = optimizePlanService.costList(costType); 
				List<ProductCostDetail> costDetails = null;
				int productCount = 0 ;
				for (ProductsCost cost : costList) {
					productCount += cost.getProductCount();
					/** 得到结算详情清单 */
					cost.setPlanId(designPlanProduct.getPlanId());
					cost.setUserId(loginUser.getId());
					cost.setAuthorizedConfigList(list);
					cost.setBaseProduct(baseProductList);
					if( dictionary != null ){
						cost.setSalePriceValueName(dictionary.getName());
					}
					if("1".equals(cost.getCostTypeValue())||"2".equals(cost.getCostTypeValue())||"3".equals(cost.getCostTypeValue())){
						BigDecimal salePrice = cost.getTotalPrice();
						BigDecimal totalPrice  = costType.getTotalPrice();
						BigDecimal new_totalPrice  = totalPrice.subtract(salePrice);
						costType.setTotalPrice(new_totalPrice);
						cost.setTotalPrice(null);
					}
//					costDetails = designPlanProductService.costDetail(cost);
					costDetails = optimizePlanService.costDetail(cost);
					if(costDetails!=null&&costDetails.size()>0){
						for (ProductCostDetail productCostDetail : costDetails) {
							BaseProduct baseProduct = baseProductService.get(productCostDetail.getProductId());
							//讲 地面  墙面 天花的 价格 从总价中  去除
							 if("1".equals(baseProduct.getProductTypeValue())||"2".equals(baseProduct.getProductTypeValue())||"3".equals(baseProduct.getProductTypeValue())){
								 if(baseProduct!=null){
//									 if(baseProduct.getSalePrice()!=null&&costType.getTotalPrice()!=null){
//										BigDecimal salePrice = new BigDecimal(baseProduct.getSalePrice());
//										BigDecimal totalPrice  = costType.getTotalPrice();
//										BigDecimal new_totalPrice  = totalPrice.subtract(salePrice);
//										costType.setTotalPrice(new_totalPrice);
//									} 	
									productCostDetail.setTotalPrice(null);
									productCostDetail.setUnitPrice(null);
								} 	
								 
							} 
						}
					}
					// costDetails=DesignPlanProductCacher.costDetail(cost);
					cost.setDetailList(costDetails);
				}
				costType.setDetailList(costList);
				costType.setProductCount(productCount);
				if( dictionary != null ){
					costType.setSalePriceValueName(dictionary.getName());
				}
				costType.setSalePriceValueName(dictionary==null?"":dictionary.getName());
			}
		}
		return new ResponseEnvelope<ProductsCostType>(total, costTypeList,designPlanProduct.getMsgId());
	}
	
	/**
	 * 卧室一建装修产品匹配
	 * 单品:单品/天花/背景墙
	 * 结构
	 * 组合
	 * 
	 * @author huangsongbo
	 * @param designTempletId 样板房id
	 * @param planRecommendedId 推荐方案id
	 * @param msgId
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @param opType 是否是自动渲染
	 * @param spaceCode 空间编码(用于识别房间类型,来决定启用哪种房间类型的一件装修逻辑)
	 * @return
	 */
	@RequestMapping("/bedroomProductMatch")
	@ResponseBody
	public Object bedroomProductMatch(
			Integer designTempletId,
			Integer planRecommendedId,
			String msgId,
			Integer matchType,
			Integer opType,
			String spaceCode,
			HttpServletRequest request
			// 用于debug(最后要删除) ->start
			, String posNameDebug
			// 用于debug(最后要删除) ->end
			) {
		
		// *参数验证 ->start
		if(designTempletId == null) {
			return new ResponseEnvelope<>(false, "参数designTempletId不能为空", msgId);
		}
		if(planRecommendedId == null) {
			return new ResponseEnvelope<>(false, "参数planRecommendedId不能为空", msgId);
		}
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if(designTemplet == null) {
			return new ResponseEnvelope<>(false, "样板房数据不存在!(id = " + designTempletId + ")", msgId);
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
		if(designPlanRecommended == null) {
			return new ResponseEnvelope<>(false, "推荐方案数据不存在!(id = " + planRecommendedId + ")", msgId);
		}
		if(matchType == null) {
			matchType = 0;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 用于debug(最后要删除) ->start
		if(StringUtils.isNotEmpty(posNameDebug)) {
			this.posNameDebug = posNameDebug;
		}
		// 用于debug(最后要删除) ->end
		// *参数验证 ->end
		
		// 返回结果
		BedroomProductMatchDTO bedroomProductMatchDTO = new BedroomProductMatchDTO();
		
		// *整理单品/结构/组合成bean ->start
		// 整理样板房产品表
		List<DesignTempletProduct> designTempletProductList = designTempletProductService.getListByTempletId(designTempletId);
		if(Lists.isEmpty(designTempletProductList)) {
			return new ResponseEnvelope<>(false, "检测到样板房的产品列表为空,designCode:" + designTemplet.getDesignCode(), msgId);
		}
		// 组装样板房中单品/结构/组合数据
		ProductListByTypeInfo productListByTypeInfo = designTempletProductService.getProductListByTypeInfo(designTempletProductList, matchType);
		
		// 整理推荐方案产品表
		List<DesignPlanRecommendedProduct> designPlanRecommendedProductList = designPlanRecommendedProductServiceV2.getListByRecommendedId(planRecommendedId);
		if(Lists.isEmpty(designPlanRecommendedProductList)) {
			return new ResponseEnvelope<>(false, "检测到一建装修方案的产品列表为空", msgId);
		}
		// 组装推荐方案中单品/结构/组合数据
		ProductListByTypeInfo productListByTypeInfoRecommended;
		try {
			productListByTypeInfoRecommended = designPlanRecommendedProductServiceV2.getProductListByTypeInfo(designPlanRecommendedProductList);
		} catch (IntelligenceDecorationException e) {
			return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
		}
		// *整理单品/结构/组合成bean ->end
		
		// *新建设计方案(作为一件装修后生成的设计方案) ->start
		String mediaType = SystemCommonUtil.getMediaType(request);
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		DesignPlan designPlan = new DesignPlan();
		designPlan = designPlanService.createDesignPlan(designPlan, null,designTemplet, mediaType, loginUser, 1, opType);
		/*designPlan.setId(0);*/
		// *新建设计方案(作为一件装修后生成的设计方案) ->end
		
		// 匹配结构
		List<StructureMatchInfoDTO> structureMatchInfo;
		try {
			structureMatchInfo = intelligenceDecorationService.structureListMatch(
					productListByTypeInfo,
					productListByTypeInfoRecommended.getStructureInfo(),
					designPlan.getId(),
					loginUser.getLoginName(),
					opType,
					productListByTypeInfoRecommended.getProductListMap(),
					spaceCode,
					designTemplet
					);
		} catch (IntelligenceDecorationException e) {
			return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
		}
		
		// 匹配组合
		List<GroupMatchInfoDTO> groupMatchInfo = intelligenceDecorationService.groupListMatch(
				productListByTypeInfo.getGroupInfo(),
				productListByTypeInfoRecommended.getGroupInfo(),
				designPlan.getId(),
				loginUser.getLoginName(),
				opType);
		
		// 匹配单品(单品/背景墙/天花)
		List<ProductMatchInfoDTO> productMatchInfo;
		try {
			productMatchInfo = intelligenceDecorationService.productListMatch(
					productListByTypeInfo.getProductList(),
					productListByTypeInfoRecommended.getProductListMap(),
					designPlan.getId(),
					loginUser.getLoginName(),
					matchType,
					opType,
					designTemplet);
		} catch (IntelligenceDecorationException e) {
			return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
		}
		
		// *样板房/推荐方案配置文件 ->start
		Integer designTempletConfigFileId = designTemplet.getConfigFileId();
		String designTempletConfigFileUrl = null;
		if(designTempletConfigFileId != null) {
			ResFile resFile = resFileService.get(designTempletConfigFileId);
			if(resFile != null) {
				designTempletConfigFileUrl = resFile.getFilePath();
			}
		}
		Integer designPlanRecommendedConfigFileId = designPlanRecommended.getConfigFileId();
		String designPlanRecommendedConfigFileUrl = null;
		if(designPlanRecommendedConfigFileId != null) {
			ResDesign resDesign = resDesignService.get(designPlanRecommendedConfigFileId);
			if(resDesign != null) {
				designPlanRecommendedConfigFileUrl = resDesign.getFilePath();
			}
		}
		// *样板房/推荐方案配置文件 ->start
		
		// *组装返回结果 ->start
		bedroomProductMatchDTO.setDesignTempletConfigUrl(designTempletConfigFileUrl);
		bedroomProductMatchDTO.setGroupMatchInfo(groupMatchInfo);
		bedroomProductMatchDTO.setProductMatchInfo(productMatchInfo);
		bedroomProductMatchDTO.setRecommendedPlanConfigUrl(designPlanRecommendedConfigFileUrl);
		bedroomProductMatchDTO.setStructureMatchInfo(structureMatchInfo);
		// *组装返回结果 ->end
		
		return new ResponseEnvelope<BedroomProductMatchDTO>(true, bedroomProductMatchDTO, msgId);
	}
	
	/**
	 * 根据配置文件,生成设计方案,并返回设计方案信息(进入房间)
	 * 
	 * @author huangsongbo
	 * @param designTempletId
	 * @param recommendedPlanId
	 * @param configEncrypt
	 * @param msgId
	 * @param posNameInfo json格式,posName对应designPlanProductId的格式
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/createPlanByConfig")
	@ResponseBody
	public Object createPlanByConfig(
			Integer designTempletId,
			Integer recommendedPlanId,
			String configEncrypt,
			String msgId,
			String posNameInfoJson,
			Integer opType,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		// *参数验证/参数处理 ->start
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<>(false, "参数msgId不能为空", msgId);
		}
		if(StringUtils.isEmpty(posNameInfoJson)) {
			return new ResponseEnvelope<>(false, "参数posNameInfo不能为空", msgId);
		}
		List<PosNameInfo> posNameInfoList = new ArrayList<IntelligenceDecorationController.PosNameInfo>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(posNameInfoJson);
			for (int index = 0; index < jsonArray.size(); index++) {
				/*PosNameInfo posNameInfo = (PosNameInfo) JSONObject.toBean(jsonArray.getJSONObject(index), PosNameInfo.class);*/
				PosNameInfo posNameInfo = new PosNameInfo();
				posNameInfo.setPosName(jsonArray.getJSONObject(index).getString("posName"));
				posNameInfo.setDeignPlanProductId(jsonArray.getJSONObject(index).getInt("deignPlanProductId"));
				posNameInfoList.add(posNameInfo);
			}
		}catch (Exception e) {
			return new ResponseEnvelope<>(false, "参数posNameInfo格式不正确", msgId);
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证/参数处理 ->end
		
		// *附加信息 ->start
		String mediaType = SystemCommonUtil.getMediaType(request);
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		// *附加信息 ->end
		
		Integer planId = designPlanService.createPlanByConfig(designTempletId, recommendedPlanId, configEncrypt, mediaType, loginUser, posNameInfoList, opType);
		
		if(planId == null || planId < 1) {
			return new ResponseEnvelope<>(false, "设计方案创建失败", msgId);
		}
		
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			// *取得设计方案信息,进入房间 ->start
			UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
			@SuppressWarnings("unchecked")
			ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope<UnityDesignPlan>) 
				designPlanService.getDesignPlanInfo(planId, null, null, null, null, null, loginUser, mediaType, Constants.NO_CACHE);
			if(responseEnvelopeInfo.isSuccess()) {
				unityDesignPlan = (UnityDesignPlan)responseEnvelopeInfo.getObj();
			}else {
				return new ResponseEnvelope<>(false, "进入设计方案失败", msgId);
			}
			unityDesignPlan = designPlanService.wrapperData(planId, unityDesignPlan);
			return new  ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);
			/*return webDesignPlanControllerV2.getDesignPlan(0 + "", 0 + "", planId, null, null, msgId, request, null);*/
			// *取得设计方案信息,进入房间 ->end
		}else {
			// 自动渲染
			//获取设计方案信息
			UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
			ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope) optimizePlanService
					.getDesignPlanInfo(planId, null, null, null, null, false, loginUser, mediaType);
			if (responseEnvelopeInfo.isSuccess()) {
				unityDesignPlan = (UnityDesignPlan) responseEnvelopeInfo.getObj();
			} else {
				logger.error(responseEnvelopeInfo.getMessage());
				return new ResponseEnvelope<>(false, "进入设计方案失败！", msgId);
			}
			unityDesignPlan = optimizePlanService.wrapperData(planId, unityDesignPlan);

			return new ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);
		}
		
	}
	
	public class PosNameInfo implements Serializable{
		
		private static final long serialVersionUID = 8839570865694685026L;

		private String posName;
		
		private Integer deignPlanProductId;

		public String getPosName() {
			return posName;
		}

		public void setPosName(String posName) {
			this.posName = posName;
		}

		public Integer getDeignPlanProductId() {
			return deignPlanProductId;
		}

		public void setDeignPlanProductId(Integer deignPlanProductId) {
			this.deignPlanProductId = deignPlanProductId;
		}
		
	}
	
}
