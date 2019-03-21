package com.nork.common.async;

import com.nork.common.util.MessageUtil;
import com.nork.design.common.SyncFullSearchRecommendedPlan;
import com.nork.design.service.SyncRecommendedPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class SyncRecommendedPlanTask implements Callable<Result> {

	Logger logger = LoggerFactory.getLogger(SyncRecommendedPlanTask.class);
	private final static String CLASS_LOG_PREFIX = "[方案同步任务]推荐方案信息同步任务:";

	private String planIds;
	private int actionType;
	private SyncRecommendedPlanService syncRecommendedPlanService;

	public SyncRecommendedPlanTask(String planIds, int actionType, SyncRecommendedPlanService syncRecommendedPlanService) {
		this.planIds = planIds;
		this.actionType = actionType;
		this.syncRecommendedPlanService = syncRecommendedPlanService;
	}

	@Override
	public Result call() throws Exception {
		Long startTime = System.currentTimeMillis();
		//MessageUtil messageUtil = SyncFullSearchRecommendedPlan.syncRecommendedPlanByPlanId(planIds);
		MessageUtil messageUtil = syncRecommendedPlanService.syncRecommendedPlanByPlanIds(planIds, actionType);
		if (!messageUtil.isStauts()) {
			logger.error(CLASS_LOG_PREFIX + messageUtil.getMessage());
		}
		logger.error(CLASS_LOG_PREFIX + "方案同步耗时 {}", System.currentTimeMillis() - startTime);
		return null;
	}

}
