package com.nork.design.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.exception.IntelligenceDecorationException;
import com.nork.design.model.BedroomProductMatchDTO.GroupMatchInfoDTO;
import com.nork.design.model.BedroomProductMatchDTO.ProductMatchInfoDTO;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProductResult;
import com.nork.design.model.ProductListByTypeInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanGroupInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanStructureInfo;
import com.nork.design.model.UnityDesignPlan;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.MatchGroupProductResult;
import com.nork.product.model.result.SearchStructureProductResult;

public interface IntelligenceDecorationService {
	
	public ResponseEnvelope<Map<String,String>> findDesignConfig(Integer templetId,Integer designTempletId,String msgId);
	
	public ResponseEnvelope<BaseProduct> disposeOneKeyDecorateData(String templetProductCode,String designTempletProductCode,String msgId, LoginUser loginUser);
	
	public ResponseEnvelope<UnityDesignPlan> findGeneratePlanInfo(Integer designTempletId,DesignPlanRecommended designPlanRecommended,String context,String msgId, LoginUser loginUser, String mediaType);
	
	public ResponseEnvelope<SearchStructureProductResult> matchingStructureData(String structureCodes, Integer designTempletId, Integer designPlanId, String msgId, LoginUser loginUser);
		
	public ResponseEnvelope<DesignTempletProductResult> findDesignTempletProductList(Integer designTempletId, Integer groupType, String msgId);
	
	public ResponseEnvelope<Object> getTempletProductIndexConfig(String msgId,String templetCode);

	public ResponseEnvelope byConfigSaveProductIndex(String msgId,String templetCode,String context,LoginUser loginUser);

	/**
	 * 匹配样板房和推荐设计方案中的结构信息,
	 * 返回结构替换信息(哪些结构删除,哪些结构替换进来)
	 * 
	 * 特殊逻辑:
	 * 假设地面没有换成别的结构,则不反悔该地面的结构替换信息
	 * 
	 * 改造:by huangsongbo
	 * 原作者:xiaoxiangcheng
	 * @param designTemplet
	 * @param designPlanRecommended
	 * @param loginUser
	 * @param regionMarkList 保留的该区域编码的结构(没有在这个list的结构编码删除)
	 * @return
	 */
	public List<SearchStructureProductResult> matchingStructureDataV2(DesignTemplet designTemplet, DesignPlanRecommended designPlanRecommended, LoginUser loginUser, List<String> regionMarkList);

	public List<Map<String, String>> findByItemCodeInfoV2(String posNames, DesignTemplet designTemplet,
			DesignPlanRecommended designPlanRecommended, LoginUser loginUser) throws IntelligenceDecorationException;

	/**
	 * 样板房白膜组合匹配推荐方案组合
	 * 
	 * @author huangsongbo
	 * @param designPlanRecommended
	 * @param designTemplet
	 * @return
	 */
	public List<MatchGroupProductResult> getGroupProductData(DesignPlanRecommended designPlanRecommended,
			DesignTemplet designTemplet);

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
	public Map<String, Object> structureListMatch(
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
	 * 匹配单品验证更多的属性(比如产品的长宽高)
	 * 
	 * @author huangsongbo
	 * @param planProductInfo 待匹配的产品信息
	 * @param planProductInfoCheck 待匹配的属性(产品长宽高等)
	 * @return
	 */
	boolean productMatchCheckMoreInfo(PlanProductInfo planProductInfo, PlanProductInfo planProductInfoCheck);

	/**
	 * 一键生成更新设计方案配置文件
	 * @param planId
	 * @param context
	 * @param planProductId
	 * @param bDirtyConfig
	 * @param opType
	 * @return
	 */
	Object updatePlanConfig(Integer planId,String context, Integer planProductId,String bDirtyConfig, Integer opType,LoginUser loginUser);
}
