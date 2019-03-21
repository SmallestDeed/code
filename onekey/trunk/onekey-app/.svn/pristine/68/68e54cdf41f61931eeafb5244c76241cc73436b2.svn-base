package com.nork.intelligence.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.BedroomProductMatchDTO.GroupMatchInfoDTO;
import com.nork.onekeydesign.model.BedroomProductMatchDTO.ProductMatchInfoDTO;
import com.nork.onekeydesign.model.BedroomProductMatchDTO.StructureMatchInfoDTO;
import com.nork.onekeydesign.model.DesignPlanRecommended;
import com.nork.onekeydesign.model.DesignTemplet;
import com.nork.onekeydesign.model.DesignTempletProductResult;
import com.nork.onekeydesign.model.ProductListByTypeInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanGroupInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanStructureInfo;
import com.nork.onekeydesign.model.UnityDesignPlan;
import com.nork.product.model.MatchGroupProductResult;

public interface IntelligenceDecorationService {

	ResponseEnvelope<DesignTempletProductResult> findDesignTempletProductList(
			Integer designTempletId, Integer groupType, String msgId);

	ResponseEnvelope<Map<String, String>> findDesignConfig(
			Integer recommendedPlanId, Integer designTempletId, String msgId);

	List<MatchGroupProductResult> getGroupProductData(
			DesignPlanRecommended designPlanRecommended,
			DesignTemplet designTemplet);

	public List<Map<String, String>> findByItemCodeInfoV2(String posNames, DesignTemplet designTemplet,
			DesignPlanRecommended designPlanRecommended, LoginUser loginUser) throws IntelligenceDecorationException;

	ResponseEnvelope<UnityDesignPlan> findGeneratePlanInfo(
			Integer designTempletId,
			DesignPlanRecommended designPlanRecommended, String context,
			String msgId, LoginUser loginUser, String mediaType);

	/**
	 * 匹配结构
	 * 
	 * @author huangsongbo
	 * @param productListByTypeInfo 样板房所有结构信息
	 * @param planStructureInfoRecommended 推荐方案中所有结构信息
	 * @param planId 设计方案id,为了保存一建生成方案的产品列表
	 * @param username 创建人,为了保存一建生成方案的产品列表
	 * @param opType 是否是自动渲染:0:自动渲染
	 * @param productListmap 匹配失败,自行搜索的结构,可能产品明细匹配不上,做的备用方案(当单品匹配)
	 * @param spaceCode 空间编码(用于识别房间类型,来决定启用哪种房间类型的一件装修逻辑)
	 * @param designTemplet 样板房信息
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	public List<StructureMatchInfoDTO> structureListMatch(
			ProductListByTypeInfo productListByTypeInfo,
			PlanStructureInfo planStructureInfoRecommended,
			Integer planId, String username, Integer opType,
			Map<String, List<PlanProductInfo>> productListmap,
			String spaceCode, DesignTemplet designTemplet) throws IntelligenceDecorationException;
	
	/**
	 * 匹配组合
	 * 
	 * @author huangsongbo
	 * @param planGroupInfo 样板房中的所有组合信息
	 * @param planGroupInfoRecommended 推荐方案中的所有组合信息
	 * @param planId 设计方案id,为了保存一建生成方案的产品列表
	 * @param username 创建人,为了保存一建生成方案的产品列表
	 * @param opType 是否是自动渲染:0:自动渲染
	 * @return
	 */
	public List<GroupMatchInfoDTO> groupListMatch(
			PlanGroupInfo planGroupInfo, PlanGroupInfo planGroupInfoRecommended, Integer planId, String username,
			Integer opType
			);
	
	/**
	 * 卧室一建装修匹配单品(单品/天花/背景墙)
	 * 
	 * @param templetProductList 样板房中的单品List
	 * @param map 推荐方案中的单品
	 * @param planId 设计方案id,为了保存一建生成方案的产品列表
	 * @param username 创建人,为了保存一建生成方案的产品列表
	 * @param matchType 0:全屋替换/1:硬装替换
	 * @param opType 是否是自动渲染:0:自动渲染
	 * @param designTemplet 一键装修样板房信息
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	public List<ProductMatchInfoDTO> productListMatch(List<PlanProductInfo> templetProductList,
			Map<String, List<PlanProductInfo>> map, Integer planId, String username, Integer matchType, Integer opType, DesignTemplet designTemplet) throws IntelligenceDecorationException;
	
}