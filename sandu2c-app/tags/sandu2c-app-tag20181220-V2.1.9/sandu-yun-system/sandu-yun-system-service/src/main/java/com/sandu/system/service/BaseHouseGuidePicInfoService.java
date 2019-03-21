package com.sandu.system.service;

import java.util.List;
import java.util.Map;

import com.sandu.design.model.DesignTemplet;
import com.sandu.home.model.dto.GuideInfoDTO;
import com.sandu.system.model.BaseHouseGuidePicInfo;
import com.sandu.system.model.search.BaseHouseGuidePicInfoSearch;

public interface BaseHouseGuidePicInfoService {

	/**
	 * 获取2d户型导航图得坐标配置/渲染状态
	 * find by picId
	 * 
	 * @author huangsongbo
     * @param picId 户型2d导航图id
     * @param fullHousePlanId 全屋方案id 用来查询当前渲染状态
     * @param renderStatusMap 同fullHousePlanId也是用提供渲染状态信息,只不过提供了已经查好的渲染状态map,此参数优先级更大,
	 * @param designTempletMap key = 样板房id, value = 样板房信息, add by huangsongbo 2018.10.12 为了填充样板房的其他信息(该样板房匹配到了哪个推荐方案)
	 * @return
	 */
	List<GuideInfoDTO> getGuideInfoDTOListByPicId(Integer picId, Integer fullHousePlanId, Map<Integer, Integer> renderStatusMap, Map<Integer, DesignTemplet> designTempletMap);


	List<GuideInfoDTO> getGuideInfoDTOListByTemplatedIds(Integer picId, List<Integer> templateIds);

	/**
	 * 
	 * @author huangsongbo
	 * @param baseHouseGuidePicInfoSearch
	 * @return
	 */
	List<BaseHouseGuidePicInfo> getListBySearch(BaseHouseGuidePicInfoSearch baseHouseGuidePicInfoSearch);

}
