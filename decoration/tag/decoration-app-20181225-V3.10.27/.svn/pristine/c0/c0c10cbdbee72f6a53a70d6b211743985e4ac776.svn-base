package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.collections.Lists;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.PlanRecommendedListModel;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.mobile.service.MobileDesignPlanRecommendedService;

@Service("mobileDesignPlanRecommendedService")
public class MobileDesignPlanRececommendedServiceImpl implements MobileDesignPlanRecommendedService {

	private static Logger logger = Logger.getLogger(MobileDesignPlanRececommendedServiceImpl.class);
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;

	/**
	 * 获取设计方案推荐列表
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPlanRecommendedList(/* String all, */String creator, String brandName, String houseType,
			String livingName, String areaValue, String designRecommendedStyleId, String isMainList, String msgId,
			LoginUser loginUser, Integer limit, Integer start, Integer templateId,String planName) {

		PlanRecommendedListModel model = new PlanRecommendedListModel();
		model.setCreator(creator);
		model.setBrandName(brandName);
		model.setHouseType(houseType);
		model.setLivingName(livingName);
		model.setAreaValue(areaValue);
		model.setDesignRecommendedStyleId(designRecommendedStyleId);
		model.setDisplayType(isMainList);
		model.setMsgId(msgId);
		model.setLoginUser(loginUser);
		model.setLimit(limit);
		model.setStart(start);
		model.setTemplateId(templateId);
		model.setPlanName(planName);
		
		return designPlanRecommendedServiceV2.getPlanRecommendedList(model);
		/*// 得到推荐方案的所有数据
		ResponseEnvelope<DesignPlanRecommendedResult> res = 
				(ResponseEnvelope<DesignPlanRecommendedResult>) designPlanRecommendedServiceV2
				.getPlanRecommendedList(model);
		// 根据样板房id 查询 所有自动渲染成功的 任务状态
		List<AutoRenderTaskState> list = new ArrayList<>();
		if (templateId != null && templateId != 0) {
			logger.error("样板房id：templateId  =  " + templateId);
			list = designPlanAutoRenderMapper.getSuccessTaskStateList(templateId);
		}
		// 得到推荐方案的所有数据
		List<DesignPlanRecommendedResult> result = res.getDatalist();
		List<DesignPlanRecommendedResult> result2 = new ArrayList<>();
		// 遍历判断这个推荐方案是否有经过自动渲染并成功
		if (result != null && result.size() > 0 && list != null && list.size() > 0) {
			for (AutoRenderTaskState state : list) {
				for (DesignPlanRecommendedResult recommended : result) {
					if (recommended.getPlanRecommendedId().intValue() == state.getPlanId().intValue()) {
						logger.error("PlanRecommendedId=" + recommended.getPlanRecommendedId()
						+ "=======planId=" + state.getPlanId() + "这个推荐方案已经经过自动渲染了。");
						result2.add(recommended);
					}
				}
			}
			res.setDatalist(result2);
			res.setTotalCount(result2.size());
		}else if(templateId != null && templateId != 0 && Lists.isEmpty(list)){
			return new ResponseEnvelope<>(true,"这个样板房没有经过自动渲染，不能装进我家！");
		}

		return res;*/
	}

}
