package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.onekeydesign.model.DesignPlan;
import com.nork.onekeydesign.model.DesignPlanCheck;
import com.nork.onekeydesign.model.DesignPlanRecommendedResult;
import com.nork.system.model.SysUserPlanRecommended;
 

public interface DesignPlanCheckService {

	public int add(DesignPlanCheck designPlanCheck);
	
	public int update(DesignPlanCheck designPlanCheck);
	
	public int delete(Integer id);
	
	public DesignPlanCheck get(Integer id);
	
	public List<DesignPlanCheck> getList(DesignPlanCheck designPlanCheck);

	public int getCount(DesignPlanCheck designPlanCheck);

	/**
	 * 取得该设计方案的  审核人员列表
	 * @param designPlan
	 * @return
	 */
	public List<SysUserPlanRecommended> planCheckUserList(DesignPlan designPlan);
	/**
	 * 方案推荐审核列表
	 * @param designPlanCheck
	 * @return
	 */
	public List<DesignPlanRecommendedResult> getDesignPlanCheckList(DesignPlanCheck designPlanCheck);

	public int getDesignPlanCheckCount(DesignPlanCheck designPlanCheck);
 
}
