package com.nork.intelligence.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.onekeydesign.model.vo.DimMatchInfoVO;
import com.nork.onekeydesign.service.*;
import com.nork.intelligence.constant.IntelligenceDecorationConstant;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.collections.Lists;
import com.nork.onekeydesign.common.DesignPlanConstants;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.BedroomProductMatchDTO;
import com.nork.onekeydesign.model.BedroomProductMatchDTO.GroupMatchInfoDTO;
import com.nork.onekeydesign.model.BedroomProductMatchDTO.ProductMatchInfoDTO;
import com.nork.onekeydesign.model.BedroomProductMatchDTO.StructureMatchInfoDTO;
import com.nork.onekeydesign.model.DesignPlan;
import com.nork.onekeydesign.model.DesignPlanRecommended;
import com.nork.onekeydesign.model.DesignPlanRecommendedProduct;
import com.nork.onekeydesign.model.DesignTemplet;
import com.nork.onekeydesign.model.DesignTempletProduct;
import com.nork.onekeydesign.model.PosNameInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo;
import com.nork.onekeydesign.model.UnityDesignPlan;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.model.bo.SysDictionaryBo;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.sandu.search.exception.ElasticSearchException;

/**
 * 一件装修controller copy from 通用版本
 * 
 * @author huangsongbo
 * 2017.11.28
 */
@Controller
@RequestMapping("/{style}/web/design/intelligenceDecoration")
public class IntelligenceDecorationController {

	private static Logger logger = LogManager.getLogger(IntelligenceDecorationController.class);
	
	private String logPrefixClass = "function:IntelligenceDecorationController.";
	
	@Autowired
	private IntelligenceDecorationService intelligenceDecorationService;
	
	@Autowired
	private DesignTempletService designTempletService;
	
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	
	@Autowired
	private OptimizePlanService optimizePlanService;
	
	@Autowired
	private DesignTempletProductService designTempletProductService;
	
	@Autowired
	private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
	
	@Autowired
	private DesignPlanService designPlanService;
	
	@Autowired
	private ResFileService resFileService;
	
	@Autowired
	private ResDesignService resDesignService;
	
	@Autowired
	private DesignPlanTemplateService designPlanTemplateService;
	
	/**
	 * 匹配结构返回结果
	 * 
	 * @author huangsongbo
	 * 
	 */
	/*public enum structureListMatchResultMapEnum{
		structureMatchInfo, productListByTypeInfo
	}*/
	
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/bedroomProductMatch")
	@ResponseBody
	public Object bedroomProductMatch(
			Integer designTempletId,
			Integer planRecommendedId,
			String msgId,
			Integer matchType,
			Integer opType,
			String spaceCode,
			HttpServletRequest request,
			// add by huangsongbo 2018.1.9, 前端要这个参数,决定是从空间入口装修(选择空间类型+面积)的方案还是从户型入口(选择小区->户型)装修的方案
			String planSource,
			// add by huangsongbo 2018.7.17; 识别是否需要给出备用推荐方案的id(小面积推荐方案的id)
			// 1:需要; other(0,null...):不需要
			Integer returnParamType
			// 用于debug(最后要删除) ->start
			,String posNameDebug
			// 用于debug(最后要删除) ->end
			,Integer supplyDemandId) {

		// *参数验证 ->start
		if(designTempletId == null) {
			return new ResponseEnvelope<>(false, "参数designTempletId不能为空", msgId);
		}
		if(planRecommendedId == null) {
			return new ResponseEnvelope<>(false, "参数planRecommendedId不能为空", msgId);
		}
		DesignTemplet designTemplet = designTempletService.getForOneKey(designTempletId);
		if(designTemplet == null) {
			return new ResponseEnvelope<>(false, "样板房数据不存在!(id = " + designTempletId + ")", msgId);
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(planRecommendedId);
		if(designPlanRecommended == null) {
			return new ResponseEnvelope<>(false, "推荐方案数据不存在!(id = " + planRecommendedId + ")", msgId);
		}

		//如果是主方案，匹配适用于该样板空间的子方案 add by : xiaoxc20180911
		if (designPlanRecommended.getGroupPrimaryId().equals(planRecommendedId)) {
			planRecommendedId = designPlanRecommendedService.getBestMatchInPlanGroup(designTempletId, planRecommendedId);
			designPlanRecommended = designPlanRecommendedService.get(planRecommendedId);
			if(designPlanRecommended == null) {
				return new ResponseEnvelope<>(false, "推荐方案数据不存在!(bestMatchPlanId = " + planRecommendedId + ")", msgId);
			}
		}

		if(matchType == null) {
			matchType = 0;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromRequest(request);
		if(loginUser == null){
			return new ResponseEnvelope<UnityDesignPlan>(false, "未检测到登录信息,请登录", msgId);
		}
		// 防止没有userName的账号 update by huangsongbo 2018.6.12
		if(StringUtils.isEmpty(loginUser.getLoginName())) {
			loginUser.setLoginName("noUsername");
		}
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
		ProductListByTypeInfo productListByTypeInfo = designTempletProductService.getProductListByTypeInfo(designTempletProductList, matchType, planRecommendedId);

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
		/*String mediaType = SystemCommonUtil.getMediaType(request);*/
		String mediaType = null!=loginUser?loginUser.getMediaType():null;

		DesignPlan designPlan = new DesignPlan();
		designPlan.setPlanSource(planSource);
		//add by weisheng,通过信息详情页面的户型进入一键装修生成草图方案时需要保存这个字段
		if(null !=supplyDemandId && supplyDemandId >0){
			designPlan.setSupplyDemandId(supplyDemandId);
		}
		// designPlan = designPlanService.createDesignPlanForOneKey(designPlan, null,designTemplet, mediaType, loginUser, 1, opType);
		// /*designPlan.setId(0);*/
        //
		// // 设置新设计方案拼花配置文件 ->start
		// long resFileId = resDesignService.copySpellingFlowerFile(designPlanRecommended.getSpellingFlowerFileId(), designPlan.getId(), designPlan.getPlanCode());
		// designPlan.setSpellingFlowerFileId(Integer.valueOf(resFileId + ""));
		// designPlan.setSpellingFlowerProduct(designPlanRecommended.getSpellingFlowerProduct());
		// designPlanService.update(designPlan);
		// 设置新设计方案拼花配置文件 ->end
		designPlan.setId(437626);

		// *新建设计方案(作为一件装修后生成的设计方案) ->end

		// 匹配结构
		Map<String, Object> structureListMatchResultMap = null;
		try {
			structureListMatchResultMap = intelligenceDecorationService.structureListMatch(
					productListByTypeInfo,
					productListByTypeInfoRecommended.getStructureInfo(),
					designPlan.getId(),
					loginUser.getLoginName(),
					opType,
					productListByTypeInfoRecommended.getProductListMap(),
					spaceCode,
					designTemplet,
					null
			);
		} catch (IntelligenceDecorationException e) {
			return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
		}

		List<StructureMatchInfoDTO> structureMatchInfo = null;
		if(structureListMatchResultMap != null) {
			structureMatchInfo = (List<StructureMatchInfoDTO>) structureListMatchResultMap.get(Constants.STRUCTURELISTMATCHRESULTMAPENUM_STRUCTUREMATCHINFO);
			productListByTypeInfo = (ProductListByTypeInfo) structureListMatchResultMap.get(Constants.STRUCTURELISTMATCHRESULTMAPENUM_PRODUCTLISTBYTYPEINFO);
		}

		// 匹配组合
		/*List<GroupMatchInfoDTO> groupMatchInfo = intelligenceDecorationService.groupListMatch(
				productListByTypeInfo.getGroupInfo(),
				productListByTypeInfoRecommended.getGroupInfo(),
				designPlan.getId(),
				loginUser.getLoginName(),
				opType);*/
		Map<String, Object> groupMatchResultMap = intelligenceDecorationService.groupListMatch(
				productListByTypeInfo,
				productListByTypeInfoRecommended.getGroupInfo(),
				designPlan.getId(),
				loginUser.getLoginName(),
				opType,
				null);

		List<GroupMatchInfoDTO> groupMatchInfo = null;
		if(groupMatchResultMap != null) {
			groupMatchInfo = (List<GroupMatchInfoDTO>) groupMatchResultMap.get(IntelligenceDecorationConstant.GROUPMATCHRESULTMAP_GROUPMATCHINFO);
			productListByTypeInfo = (ProductListByTypeInfo) groupMatchResultMap.get(IntelligenceDecorationConstant.GROUPMATCHRESULTMAP_PRODUCTLISTBYTYPEINFO);
		}

		// 匹配单品(单品/背景墙/天花)
		Map<String, Object> productListMatchResultMap = null;
		try {
			productListMatchResultMap = intelligenceDecorationService.productListMatch(
					productListByTypeInfo.getProductList(),
					productListByTypeInfoRecommended.getProductListMap(),
					// 户型绘制需求,导致地面/天花逻辑完全变化 update by huangsongbo 2018.3.16 ->start
					productListByTypeInfoRecommended.getStructureInfo(),
					// 户型绘制需求,导致地面/天花逻辑完全变化 update by huangsongbo 2018.3.16 ->end
					designPlan.getId(),
					loginUser.getLoginName(),
					matchType,
					opType,
					designTemplet,
					planRecommendedId,
					null);
		} catch (IntelligenceDecorationException e) {
			return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
		} catch (ElasticSearchException e) {
			return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
		}

		List<ProductMatchInfoDTO> productMatchInfo = null;
		List<DimMatchInfoVO> dimMatchInfo = null;
		if(productListMatchResultMap != null) {
			productMatchInfo = (List<ProductMatchInfoDTO>) productListMatchResultMap.get(IntelligenceDecorationConstant.PRODUCTLISTMATCHRESULTMAP_PRODUCTMATCHINFO);
			dimMatchInfo = (List<DimMatchInfoVO>) productListMatchResultMap.get(IntelligenceDecorationConstant.PRODUCTLISTMATCHRESULTMAP_DIMMATCHINFOVO);
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
		bedroomProductMatchDTO.setDimMatchInfo(dimMatchInfo);
		Integer dataType = 1;
		if(designTemplet.getOrigin() != null && 1 == designTemplet.getOrigin().intValue()) {
			dataType = 2;
		}else {

		}
		bedroomProductMatchDTO.setDataType(dataType);
		// *组装返回结果 ->end

		// 返回小面积推荐方案的id ->start
		// add by huangsongbo 2018.7.17
		if(returnParamType != null && 1 == returnParamType.intValue()) {
			Integer standbyRecommendedId = null;
			try {
				standbyRecommendedId = designPlanRecommendedService.getStandbyRecommendedId(planRecommendedId);
			}catch (IntelligenceDecorationException e) {
				return new ResponseEnvelope<BedroomProductMatchDTO>(false, e.getMessage());
			}
			if(standbyRecommendedId != null) {
				bedroomProductMatchDTO.setStandbyRecommendedId(standbyRecommendedId);
			}
		}
		// 返回小面积推荐方案的id ->end

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
	 * @param posNameInfoJson json格式,posName对应designPlanProductId的格式
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
		String logPrefixFunction = logPrefixClass + "createPlanByConfig -> ";
		
		// *参数验证/参数处理 ->start
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<>(false, "参数msgId不能为空", msgId);
		}
		if(StringUtils.isEmpty(posNameInfoJson)) {
			return new ResponseEnvelope<>(false, "参数posNameInfo不能为空", msgId);
		}
		List<PosNameInfo> posNameInfoList = new ArrayList<PosNameInfo>();
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
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromRequest(request);
		if(loginUser == null){
			return new ResponseEnvelope<UnityDesignPlan>(false, "未检测到登录信息,请登录", msgId);
		}
		// 防止没有userName的账号 update by huangsongbo 2018.6.12
		if(StringUtils.isEmpty(loginUser.getLoginName())) {
			loginUser.setLoginName("noUsername");
		}
		logger.error("------createPlanByConfig -> loginUser:" + loginUser);
		
		DesignTemplet designTemplet = designTempletService.getForOneKey(designTempletId);
		if(designTemplet == null) {
			logger.error(logPrefixFunction + "designTemplet = null;designTempletId = " + designTempletId);
			return new ResponseEnvelope<>(false, "样板房未找到,一件装修失败", msgId);
		}
		
		// *参数验证/参数处理 ->end
		
		// *附加信息 ->start
		/*String mediaType = SystemCommonUtil.getMediaType(request);*/
		String mediaType = null!=loginUser?loginUser.getMediaType():null;
		logger.error("------createPlanByConfig -> mediaType:" + mediaType);
		// *附加信息 ->end
		
		Integer planId = designPlanService.createPlanByConfig(designTempletId, recommendedPlanId, configEncrypt, mediaType, loginUser, posNameInfoList, opType);
		
		if(planId == null || planId < 1) {
			return new ResponseEnvelope<>(false, "设计方案创建失败", msgId);
		}
		
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			// *取得设计方案信息,进入房间 ->start
			@SuppressWarnings("unchecked")
			ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope<UnityDesignPlan>) 
				designPlanService.getDesignPlanInfoForOneKey(planId, null, null, null, null, null, loginUser, mediaType, Constants.NO_CACHE);
			if(responseEnvelopeInfo.isSuccess()) {
				unityDesignPlan = (UnityDesignPlan)responseEnvelopeInfo.getObj();
			}else {
				return new ResponseEnvelope<>(false, "进入设计方案失败", msgId);
			}
			unityDesignPlan = designPlanService.wrapperData(planId, unityDesignPlan);
			unityDesignPlan.setDesignTempletId(designTempletId);
			// *取得设计方案信息,进入房间 ->end
		}else {
			// 自动渲染
			//获取设计方案信息
			
			@SuppressWarnings("unchecked")
			ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope<UnityDesignPlan>) optimizePlanService
					.getDesignPlanInfoForOneKey(planId, null, null, null, null, false, loginUser, mediaType);
			if (responseEnvelopeInfo.isSuccess()) {
				unityDesignPlan = (UnityDesignPlan) responseEnvelopeInfo.getObj();
			} else {
				logger.error(responseEnvelopeInfo.getMessage());
				return new ResponseEnvelope<>(false, "进入设计方案失败！", msgId);
			}
			unityDesignPlan = optimizePlanService.wrapperData(planId, unityDesignPlan);
			unityDesignPlan.setDesignTempletId(designTempletId);
		}
		
		Integer dataType = 1;
		if(designTemplet.getOrigin() != null && 1 == designTemplet.getOrigin().intValue()) {
			dataType = 2;
		}else {
			
		}
		unityDesignPlan.setDataType(dataType);
		return new ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);
		
	}


	/***
	 * 获取一键装修资源路径
	 * @param designTempletId 样板房id
	 * @param planRecommendedId 推荐方案id
	 * @param msgId
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @param opType 是否是自动渲染
	 * @param spaceCode 空间编码(用于识别房间类型,来决定启用哪种房间类型的一件装修逻辑)
	 * @return
	 */
	@RequestMapping("getDesignRecommendedInfo")
	@ResponseBody
	public Object getDesignRecommendedInfo(
			Integer designTempletId,
			Integer planRecommendedId,
			String msgId,
			Integer matchType,
			Integer opType,
			String spaceCode,
			HttpServletRequest request,
			String planSource,
			Integer returnParamType){

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromRequest(request);
		if(null==loginUser)
			return new ResponseEnvelope<DesignPlan>(false, "登录超时，请重新登录", msgId);
		if(planRecommendedId == null)
			return new ResponseEnvelope<>(false, "参数planRecommendedId不能为空", msgId);

		try {

			//判断样板房id参数是否传递
			if(null == designTempletId){

				return designPlanService.getDesignRecommendedInfo(planRecommendedId.toString(),planRecommendedId.toString(),"",loginUser,msgId);

			}else{

				DesignTemplet designTemplet = designTempletService.getForOneKey(designTempletId);
				if(designTemplet == null) {
					return new ResponseEnvelope<>(false, "样板房数据不存在!(id = " + designTempletId + ")", msgId);
				}
				DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(planRecommendedId);
				if(designPlanRecommended == null) {
					return new ResponseEnvelope<>(false, "推荐方案数据不存在!(id = " + planRecommendedId + ")", msgId);
				}

				//如果是主方案，匹配适用于该样板空间的子方案
				if (designPlanRecommended.getGroupPrimaryId().equals(planRecommendedId)) {
					planRecommendedId = designPlanRecommendedService.getBestMatchInPlanGroup(designTempletId, planRecommendedId);
					designPlanRecommended = designPlanRecommendedService.get(planRecommendedId);
					if(designPlanRecommended == null) {
						return new ResponseEnvelope<>(false, "推荐方案数据不存在!(bestMatchPlanId = " + planRecommendedId + ")", msgId);
					}
				}

				if(matchType == null) {
					matchType = 0;
				}
				if (opType == null) {
					opType = DesignPlanConstants.USER_RENDER;
				}
				// *参数验证 ->end

				// *整理单品/结构/组合成bean ->start
				// 整理样板房产品表
				List<DesignTempletProduct> designTempletProductList = designTempletProductService.getListByTempletId(designTempletId);
				if(Lists.isEmpty(designTempletProductList)) {
					return new ResponseEnvelope<>(false, "检测到样板房的产品列表为空,designCode:" + designTemplet.getDesignCode(), msgId);
				}
				// 组装样板房中单品/结构/组合数据
				ProductListByTypeInfo productListByTypeInfo = designTempletProductService.getProductListByTypeInfo(designTempletProductList, matchType, planRecommendedId);

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

				return intelligenceDecorationService.downloadProductMatch(
						designTemplet,
						designPlanRecommended,
						msgId,
						matchType,
						opType,
						spaceCode,
						loginUser,
						planSource,
						returnParamType,
						productListByTypeInfo,
						productListByTypeInfoRecommended);
			}

		}catch (Exception e){

			logger.error("getDesignRecommendedInfo系统错误",e);
			return new ResponseEnvelope<DesignPlan>(false, "系统错误", msgId);
		}

	}


	/**
	 * 获取产品资源详情信息
	 * @author chenqiang
	 * @param ids 产品ids eg:id,id,id,id
	 * @param msgId
	 * @param request
	 * @return
	 * @date 2018/12/14 0014 18:15
	 */
	@RequestMapping("/getProductSourceInfo")
	@ResponseBody
	public Object getProductSourceInfo(String ids,String msgId,HttpServletRequest request){

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromRequest(request);
		if(null==loginUser)
			return new ResponseEnvelope<DesignPlan>(false, "登录超时，请重新登录", msgId);

		if(StringUtils.isBlank(ids))
			return new ResponseEnvelope<DesignPlan>(false, "产品不能为空", msgId);

		try {

			List<Integer> idList = Arrays.stream(ids.split(",")).filter(m -> null != m && !",".equals(m)).map(id -> Integer.parseInt(id)).collect(Collectors.toList());

			return designPlanService.getProductSourceInfo(idList,loginUser,msgId);

		}catch (Exception e){

			logger.error("getProductSourceInfo系统错误",e);
			return new ResponseEnvelope<DesignPlan>(false, "系统错误", msgId);
		}

	}


	/**
	 * 查询样板房在推荐方案组中最匹配的方案ID
	 * @param designTemplateId	样板房ID
	 * @param recommendedPlanId	打组推荐方案的主ID
	 * @return
	 */
	@RequestMapping("/getBestMatchInPlanGroup")
	@ResponseBody
	public Object getBestMatchInPlanGroup(Integer designTemplateId, Integer recommendedPlanId){
		if( designTemplateId == null || designTemplateId.intValue() < 1 ){
			return new ResponseEnvelope<>(false, "样板房错误", null);
		}
		if( recommendedPlanId == null || recommendedPlanId.intValue() < 1 ){
			return new ResponseEnvelope<>(false, "推荐方案错误", null);
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(recommendedPlanId);
		if( designPlanRecommended == null
				|| (designPlanRecommended.getGroupPrimaryId().intValue() == 0 || designPlanRecommended.getGroupPrimaryId().intValue() != designPlanRecommended.getId()) ){
			return new ResponseEnvelope<>(false, "推荐方案错误", null);
		}
		Integer bestMatchPlanId = designPlanRecommendedService.getBestMatchInPlanGroup(designTemplateId, recommendedPlanId);
		return new ResponseEnvelope<>(true, bestMatchPlanId, null);
	}
	
	/**
	 * 保存一键装修生成方案副本
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId 一键装修选择的推荐方案id
	 * @param templetId 一键装修选择的样板房id
	 * @param planId 一键装修生成的设计方案id
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/saveOnekeytemplate")
	@ResponseBody
	public Object saveOnekeytemplate(
			Integer recommendedPlanId,
			Integer templetId,
			Integer planId,
			Integer matchType,
			String msgId
			) {
		// 参数验证 ->start
		if(recommendedPlanId == null) {
			return new ResponseEnvelope<>(false, "参数recommendedPlanId不能为空", msgId);
		}
		if(templetId == null) {
			return new ResponseEnvelope<>(false, "参数templetId不能为空", msgId);
		}
		if(planId == null) {
			return new ResponseEnvelope<>(false, "参数planId不能为空", msgId);
		}
		if(matchType == null) {
			return new ResponseEnvelope<>(false, "参数matchType不能为空", msgId);
		}
		// 参数验证 ->end
		
		// 检测副本开关,并做对应处理 add by huangsongbo 2018.9.5 ->start
		boolean button = intelligenceDecorationService.getTemplateButton();
		if(!button) {
			return new ResponseEnvelope<>(true, "副本功能处于停用状态", msgId);
		}
		// 检测副本开关,并做对应处理 add by huangsongbo 2018.9.5 ->end
		
		// 如果已存在副本,则不保存 ->start
		// 不能避免并发现象
		try {
			boolean isStock = designPlanTemplateService.checkIsStock(recommendedPlanId, templetId, matchType);
			if(isStock) {
				return new ResponseEnvelope<>(true, "已存在对应副本,无需重复创建", msgId);
			}
		} catch (IntelligenceDecorationException e) {
			logger.error(logPrefixClass + e.getMessage());
			return new ResponseEnvelope<>(false, e.getMessage(), msgId);
		}
		
		// 如果已存在副本,则不保存 ->end
		
		// 注释 by huangsongbo,改为异步调用
		/*try {
			designPlanTemplateService.saveTemplate(recommendedPlanId, templetId, planId, matchType);
		} catch (IntelligenceDecorationException e) {
			logger.error(logPrefixClass + e.getMessage());
			return new ResponseEnvelope<>(false, e.getMessage(), msgId);
		}*/
		
		new Thread() {
			
			@Override
			public void run() {
				try {
					designPlanTemplateService.saveTemplate(recommendedPlanId, templetId, planId, matchType);
				} catch (IntelligenceDecorationException e) {
					e.printStackTrace();
				}
				super.run();
			}
			
		}.start();
		
		return new ResponseEnvelope<>(true, "success", msgId);
	}
	
	/**
	 * 检测是否有对应的一键装修生成方案副本,如果有,则直接copy该副本转为designPlan,并进入房间
	 * update by huangsongbo 2018.7.28
	 * 如果是自动渲染,则copy到onekey_decoration_design_plan
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId 一键装修选择的推荐方案id
	 * @param templetId 一键装修选择的样板房id
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @param opType 渲染机:0/通用版:1
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/checkTemplate")
	@ResponseBody
	public Object checkTemplate(
			Integer recommendedPlanId,
			Integer templetId,
			Integer matchType,
			Integer opType,
			String msgId,
			HttpServletRequest request
			) {
		// 参数验证 ->start
		if(recommendedPlanId == null) {
			return new ResponseEnvelope<>(false, "参数recommendedPlanId不能为空", msgId);
		}
		if(templetId == null) {
			return new ResponseEnvelope<>(false, "参数templetId不能为空", msgId);
		}
		if(matchType == null) {
			return new ResponseEnvelope<>(false, "参数matchType不能为空", msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromRequest(request);
		if(loginUser == null){
			return new ResponseEnvelope<UnityDesignPlan>(false, "未检测到登录信息,请登录", msgId);
		}
		// 参数验证 ->end
		
		// 检测副本开关,并做对应处理 add by huangsongbo 2018.9.5 ->start
		boolean button = intelligenceDecorationService.getTemplateButton();
		if(!button) {
			return new ResponseEnvelope<>(true, "副本功能处于停用状态", msgId, false);
		}
		// 检测副本开关,并做对应处理 add by huangsongbo 2018.9.5 ->end
		
		try {
			// check 有没有对应的副本
			boolean isStock = designPlanTemplateService.checkIsStock(recommendedPlanId, templetId, matchType);
			if(isStock) {
				UnityDesignPlan unityDesignPlan = designPlanTemplateService.getUnityDesignPlan(recommendedPlanId, templetId, matchType, opType, loginUser);
				ResponseEnvelope<UnityDesignPlan> result = new ResponseEnvelope<>(true, unityDesignPlan, msgId);
				result.setFlag(true);
				return result;
			}else {
				// update by huangsongbo 2018.8.1 ->start
				// 由于前端,success = false, 会强制弹框,所以改为用flag来判断是否存在对应副本
				/*return new ResponseEnvelope<>(false, "没有找到对应副本", msgId);*/
				return new ResponseEnvelope<>(true, "没有找到对应/可正常使用的副本", msgId, false);
				// update by huangsongbo 2018.8.1 ->end
			}
		}catch (Exception e) {
			logger.error(logPrefixClass + e.getMessage());
			return new ResponseEnvelope<>(false, e.getMessage(), msgId);
		}
		
	}

}