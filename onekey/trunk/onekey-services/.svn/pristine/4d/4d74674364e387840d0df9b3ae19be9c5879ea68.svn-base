package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanTemplate;
import com.nork.onekeydesign.model.UnityDesignPlan;
import com.nork.onekeydesign.model.search.DesignPlanTemplateSearch;

public interface DesignPlanTemplateService {
	
	/**
	 * 保存副本
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId 一键装修选择的推荐方案id
	 * @param templetId 一键装修选择的样板房id
	 * @param planId 一键装修生成的设计方案id
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @throws IntelligenceDecorationException 
	 */
	void saveTemplate(Integer recommendedPlanId, Integer templetId, Integer planId, Integer matchType) throws IntelligenceDecorationException;

	/**
	 * add DesignPlanTemplate
	 * 
	 * @author huangsongbo
	 * @param designPlanTemplate
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	Integer add(DesignPlanTemplate designPlanTemplate) throws IntelligenceDecorationException;

	/**
	 * update DesignPlanTemplate
	 * 
	 * @author huangsongbo
	 * @param designPlanTemplate
	 * @throws IntelligenceDecorationException 
	 */
	void update(DesignPlanTemplate designPlanTemplate) throws IntelligenceDecorationException;

	/**
	 * bedroomProductMatch接口参数matchType转化成DesignPlanTemplate的字段replaceType
	 * 
	 * @author huangsongbo
	 * @param matchType
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	Integer matchTypeToReplaceType(Integer matchType) throws IntelligenceDecorationException;
	
	/**
	 * 检测有没有对应的一键装修生成方案副本
	 * 
	 * update by huangsongbo 2018.9.1:
	 * 还需要检测该副本的物理文件是否存在,不存在则作废该副本(使其重新生成一个新的副本)
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId 一键装修选择的推荐方案id
	 * @param templetId 一键装修选择的样板房id
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	boolean checkIsStock(Integer recommendedPlanId, Integer templetId, Integer matchType) throws IntelligenceDecorationException;

	/**
	 * designPlanTemplate转化为designPlan,并且获取进入设计方案数据
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId 一键装修选择的推荐方案id
	 * @param templetId 一键装修选择的样板房id
	 * @param matchType 0:全屋替换 ;1:硬装替换
	 * @param opType 渲染机:0/通用版:1
	 * @param loginUser 用户信息
	 * @return
	 */
	UnityDesignPlan getUnityDesignPlan(Integer recommendedPlanId, Integer templetId, Integer matchType, Integer opType, LoginUser loginUser) throws IntelligenceDecorationException;

	/**
	 * 根据搜索条件designPlanTemplateSearch查询符合条件的designPlanTemplate数量
	 * 
	 * @author huangsongbo
	 * @param designPlanTemplateSearch
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	int getTotal(DesignPlanTemplateSearch designPlanTemplateSearch) throws IntelligenceDecorationException;

	/**
	 * DesignPlanTemplate搜索
	 * 
	 * @author huangsongbo
	 * @param designPlanTemplateSearch
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	List<DesignPlanTemplate> getList(DesignPlanTemplateSearch designPlanTemplateSearch) throws IntelligenceDecorationException;
	
}
