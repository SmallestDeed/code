package com.sandu.common.objectconvert.home;

import java.util.ArrayList;
import java.util.List;

import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.model.vo.BaseBrandVo;
import com.sandu.common.model.PageModel;
import com.sandu.design.model.DesignTemplet;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.model.vo.DesignTempletVo;
import com.sandu.home.model.vo.SpaceCommonVo;
import com.sandu.product.model.BaseBrand;

/**
 * 转换品牌对象
 *
 * @author weis
 */
public class DesignTempletObject {

	/**
	 * 转换为BaseArea对象
	 *
	 * @param BaseBrandVo
	 * @return
	 */

	public static List<DesignTempletVo> parseToSpaceCommonVoList(List<DesignTemplet> list) {
		if (null != list && list.size() > 0) {
			List<DesignTempletVo> designTempletVoList = new ArrayList<>(list.size());
			for (DesignTemplet designTemplet : list) {
				DesignTempletVo designTempletVo = new DesignTempletVo();
				designTempletVo.setDesignTempletId(designTemplet.getId());
				designTempletVo.setDesignCode(designTemplet.getDesignCode());
				designTempletVo.setDesignName(designTemplet.getDesignName());
				designTempletVo.setDesigneffectPlanSmallUrlPic(designTemplet.getEffectPlanSmallUrl());
				designTempletVo.setDesignEffectPlanUrlPic(designTemplet.getEffectPlanUrl());
				designTempletVo.setSpaceAreas(designTemplet.getSpaceAreas());
				designTempletVo.setSpaceCommonId(designTemplet.getSpaceCommonId());
				designTempletVo.setSpaceFunctionId(designTemplet.getSpaceFunctionId());
				designTempletVo.setSpaceShape(designTemplet.getSpaceShape());				
				designTempletVoList.add(designTempletVo);
			}
			return designTempletVoList;
		}
		return null;
	}
}
