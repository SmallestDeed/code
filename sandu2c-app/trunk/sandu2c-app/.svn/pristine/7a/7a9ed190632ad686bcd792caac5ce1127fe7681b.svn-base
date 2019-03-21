package com.sandu.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.common.util.GsonUtil;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designplan.service.AutoRenderTaskService;
import com.sandu.render.model.vo.RenderStateVo;
import com.sandu.system.model.BaseHousePicFullHousePlanRelSearch;
import com.sandu.system.model.RenderTaskState;
import com.sandu.system.model.RenderTaskStateSearch;
import com.sandu.system.service.ResRenderPicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.system.dao.BaseHousePicFullHousePlanRelMapper;
import com.sandu.system.model.BaseHousePicFullHousePlanRel;
import com.sandu.system.service.BaseHousePicFullHousePlanRelService;

@Slf4j
@Service("baseHousePicFullHousePlanRelService")
public class BaseHousePicFullHousePlanRelServiceImpl implements BaseHousePicFullHousePlanRelService {

	@Autowired
	private BaseHousePicFullHousePlanRelMapper baseHousePicFullHousePlanRelMapper;
	@Autowired
	private AutoRenderTaskService autoRenderTaskService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	
	@Override
	public Map<Integer, Integer> getRenderStatusMap(Integer fullHousePlanId) {
		Map<Integer, Integer> returnMap = new HashMap<>();
		// 参数验证 ->start
		if(fullHousePlanId == null) {
			return returnMap;
		}
		// 参数验证 ->end
		Map<Integer, BaseHousePicFullHousePlanRel> map = this.getRenderMap(fullHousePlanId);
		map.forEach((key, value) -> returnMap.put(key, value.getState() == null ? null : value.getState().intValue()));
		return returnMap;
	}

	@Override
	public Map<Integer, BaseHousePicFullHousePlanRel> getRenderMap(Integer fullHousePlanId) {
		Map<Integer, BaseHousePicFullHousePlanRel> returnMap = new HashMap<>();

		// 参数验证 ->start
		if(fullHousePlanId == null) {
			return returnMap;
		}
		// 参数验证 ->end

		List<BaseHousePicFullHousePlanRel> baseHousePicFullHousePlanRelList = this.getListByFullPlanId(fullHousePlanId);
		if(baseHousePicFullHousePlanRelList != null && baseHousePicFullHousePlanRelList.size() > 0) {
			baseHousePicFullHousePlanRelList.forEach(item -> {
				RenderStateVo taskState = autoRenderTaskService.getTaskStatus(item.getTaskId().intValue());
				if (taskState != null && taskState.getBusinessId() != null) {
					ResRenderPic resRenderPic = new ResRenderPic();
					resRenderPic.setFileKey("design.designPlan.render.small.pic");
					resRenderPic.setRenderingType(4);
					resRenderPic.setDesignSceneId(taskState.getBusinessId());
					resRenderPic.setIsDeleted(0);
					List<ResRenderPic> picList = resRenderPicService.getList(resRenderPic);
					if (picList != null && picList.size() > 0) {
						item.setPlanPic(picList.get(0).getPicPath());
					}
				}
				returnMap.put(
						item.getDesignTempletId() == null ? null : item.getDesignTempletId().intValue(),
						item
				);
			});
		}
		return returnMap;
	}

	@Override
	public List<BaseHousePicFullHousePlanRel> getListByFullPlanId(Integer fullHousePlanId) {
		// 参数验证 ->start
		if(fullHousePlanId == null) {
			return null;
		}
		// 参数验证 ->end
		
		return baseHousePicFullHousePlanRelMapper.getListByFullPlanId(fullHousePlanId);
	}

	@Override
	public List <RenderTaskState> getRenderTaskStateList(RenderTaskStateSearch search) {
		return baseHousePicFullHousePlanRelMapper.getTaskStateList(search);
	}

	@Override
	public List <BaseHousePicFullHousePlanRel> getBaseHousePicFullHousePlanRelList(BaseHousePicFullHousePlanRelSearch search) {
		return baseHousePicFullHousePlanRelMapper.getBaseHousePicFullHousePlanRelList(search);
	}

	@Override
	public List<RenderTaskState> getRenderTaskListByFullHouseIdAndTemplateIds(RenderTaskStateSearch search) {
		return baseHousePicFullHousePlanRelMapper.getRenderTaskListByFullHouseIdAndTemplateIds(search);
	}

	@Override
	public BaseHousePicFullHousePlanRel getBaseHousePicFullHousePlanRelByTemplateId(RenderTaskStateSearch search) {
		return baseHousePicFullHousePlanRelMapper.getBaseHousePicFullHousePlanRelByTemplateId(search);
	}
}
