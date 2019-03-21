package com.sandu.system.service;

import java.util.List;
import java.util.Map;

import com.sandu.system.model.BaseHousePicFullHousePlanRel;
import com.sandu.system.model.BaseHousePicFullHousePlanRelSearch;
import com.sandu.system.model.RenderTaskState;
import com.sandu.system.model.RenderTaskStateSearch;
import org.springframework.stereotype.Component;

@Component
public interface BaseHousePicFullHousePlanRelService {

	/**
	 * 获取一个渲染状态的map
	 * key = 样板房id
	 * value = 渲染状态
	 * 
	 * @param fullHousePlanId
	 * @return
	 */
	Map<Integer, Integer> getRenderStatusMap(Integer fullHousePlanId);

	/**
	 * 获取一个渲染状态的map
	 * key = 样板房id
	 * value = 渲染状态
	 *
	 * @param fullHousePlanId
	 * @return
	 */
	Map<Integer, BaseHousePicFullHousePlanRel> getRenderMap(Integer fullHousePlanId);

	/**
	 * 
	 * @author huangsongbo
	 * @param fullHousePlanId
	 * @return
	 */
	List<BaseHousePicFullHousePlanRel> getListByFullPlanId(Integer fullHousePlanId);

	List<RenderTaskState> getRenderTaskStateList(RenderTaskStateSearch search);

	List<BaseHousePicFullHousePlanRel> getBaseHousePicFullHousePlanRelList(BaseHousePicFullHousePlanRelSearch search);

    List<RenderTaskState> getRenderTaskListByFullHouseIdAndTemplateIds(RenderTaskStateSearch search);

    BaseHousePicFullHousePlanRel getBaseHousePicFullHousePlanRelByTemplateId(RenderTaskStateSearch search);
}
