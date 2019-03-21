package com.sandu.common.objectconvert.home;

import java.util.ArrayList;
import java.util.List;

import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.model.vo.BaseBrandVo;
import com.sandu.common.model.PageModel;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.model.vo.SpaceCommonVo;
import com.sandu.product.model.BaseBrand;

/**
 * 转换品牌对象
 *
 * @author weis
 */
public class BaseSpaceCommonObject {

	/**
	 * 转换为BaseArea对象
	 *
	 * @param BaseBrandVo
	 * @return
	 */

	public static List<SpaceCommonVo> parseToSpaceCommonVoList(List<SpaceCommon> spaceCommonList) {
		if (null != spaceCommonList && spaceCommonList.size() > 0) {
			List<SpaceCommonVo> spaceCommonVoList = new ArrayList<>(spaceCommonList.size());
			for (SpaceCommon spaceCommon : spaceCommonList) {
				SpaceCommonVo spaceCommonVo = new SpaceCommonVo();
				spaceCommonVo.setSpaceCommonId(spaceCommon.getId());
				spaceCommonVo.setSpaceCode(spaceCommon.getSpaceCode());
				spaceCommonVo.setSpaceAreas(spaceCommon.getSpaceAreas());
				spaceCommonVo.setSpaceFunctionId(spaceCommon.getSpaceFunctionId());
				spaceCommonVo.setSpaceName(spaceCommon.getSpaceName());
				spaceCommonVo.setSpaceShape(spaceCommon.getSpaceShape());
				spaceCommonVo.setSpaceStatus(spaceCommon.getStatus());
				spaceCommonVo.setSpaceviewPlanPathPic(spaceCommon.getViewPlanPath());
				spaceCommonVo.setSpaceviewPlanSmallPathPic(spaceCommon.getViewPlanSmallPath());
				spaceCommonVo.setTotalUsageAmount(spaceCommon.getTotalUsageAmount());
				spaceCommonVo.setIsStandardSpace(spaceCommon.getIsStandardSpace());
				spaceCommonVoList.add(spaceCommonVo);
			}
			return spaceCommonVoList;
		}
		return null;
	}
}
