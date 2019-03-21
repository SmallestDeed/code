package com.nork.design.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommended;

public interface DesignPlanRecommendedService {
	/**
	 * 判断是否拥有设计方案拷贝 和 发布的权限
	 * @param loginUser
	 * @return
	 */
	public boolean isPermissions(LoginUser loginUser);
	
	/**
	 * 判断是否拥有审核 权限
	 * @param loginUser
	 * @return
	 */
	public boolean isDesignPlanCheck(LoginUser loginUser,Integer userId);
	
	/**
	 * 发布逻辑
	 * @param designPlan
	 * @param brandIds
	 * @return
	 */
	public boolean planIsRelease(DesignPlan designPlan, Integer isRelease,List<String> brandIds,List<String> checkIdList,HttpServletRequest request);
	/**
	 * 取消发布逻辑
	 * @param designPlan
	 * @return
	 */
	public boolean planNoRelease(DesignPlan designPlan);
	
	/**
	 * 方案推荐 《发布》   需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染 
	 * @param designPlan
	 * @param brandIdList
	 * @return
	 */
	public Map<String, String> planIsReleaseCheck(DesignPlan designPlan, List<String> brandIdList);
	
	/**
	 * 方案推荐 《测试发布》   需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染
	 * @param designPlan
	 * @return
	 */
	public Map<String, String> planIsTestCheck(DesignPlan designPlan, List<String> brandIdList);

    DesignPlanRecommended get(Integer planRecommendedId);

    List<DesignPlanRecommended> getListByIds(List<Integer> recommendIdList);

    void updateDesignPlanUseCount(Integer recommendedPlanId);
}
