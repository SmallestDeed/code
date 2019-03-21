package com.sandu.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.system.model.BaseHousePicFullHousePlanRelSearch;
import com.sandu.system.model.RenderTaskState;
import com.sandu.system.model.RenderTaskStateSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.system.dao.BaseHousePicFullHousePlanRelMapper;
import com.sandu.system.model.BaseHousePicFullHousePlanRel;
import com.sandu.system.service.BaseHousePicFullHousePlanRelService;

@Service("baseHousePicFullHousePlanRelService")
public class BaseHousePicFullHousePlanRelServiceImpl implements BaseHousePicFullHousePlanRelService {

	@Autowired
	private BaseHousePicFullHousePlanRelMapper baseHousePicFullHousePlanRelMapper;
	
	@Override
	public Map<Integer, Integer> getRenderStatusMap(Integer fullHousePlanId) {
		Map<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		
		// 参数验证 ->start
		if(fullHousePlanId == null) {
			return returnMap;
		}
		// 参数验证 ->end
		
		List<BaseHousePicFullHousePlanRel> baseHousePicFullHousePlanRelList = this.getListByFullPlanId(fullHousePlanId);
		if(baseHousePicFullHousePlanRelList != null && baseHousePicFullHousePlanRelList.size() > 0) {
			baseHousePicFullHousePlanRelList.forEach(item -> 
			returnMap.put(
					item.getDesignTempletId() == null ? null : item.getDesignTempletId().intValue(), 
					item.getState() == null ? null : item.getState().intValue()
					)
			);
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
