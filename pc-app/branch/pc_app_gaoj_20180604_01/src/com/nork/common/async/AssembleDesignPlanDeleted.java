package com.nork.common.async;

import java.util.concurrent.Callable;

import com.nork.common.util.SpringContextHolder;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;

public class AssembleDesignPlanDeleted implements Callable<Result>{

	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private DesignPlan plan;
	
	public AssembleDesignPlanDeleted(DesignPlan plan){
	    this.plan=plan;
	}
	
	@Override
	public Result call() throws Exception {
		designPlanService.delPlan(this.plan);
		return null;
	}
}
