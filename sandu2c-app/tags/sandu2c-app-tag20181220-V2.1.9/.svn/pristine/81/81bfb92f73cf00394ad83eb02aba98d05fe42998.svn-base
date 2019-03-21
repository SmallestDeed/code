package com.sandu.common.objectconvert.base;

import java.util.ArrayList;
import java.util.List;

import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.model.vo.BaseBrandVo;
import com.sandu.common.model.PageModel;
import com.sandu.product.model.BaseBrand;

/**
 * 转换品牌对象
 *
 * @author weis
 */
public class BaseBrandObject {

	/**
	 * 转换为BaseArea对象
	 *
	 * @param BaseBrandVo
	 * @return
	 */

	public static List<BaseBrandVo> parseToBrandVoList(List<BaseBrand> brandList) {
		if (null != brandList && brandList.size() > 0) {
			List<BaseBrandVo> brandVolist = new ArrayList<>(brandList.size());
			for (BaseBrand baseBrand : brandList) {
				BaseBrandVo baseBrandVo = new BaseBrandVo();
				baseBrandVo.setBrandId(baseBrand.getId());
				baseBrandVo.setBrandName(baseBrand.getBrandName());
				baseBrandVo.setBrandLogo(baseBrand.getBrandLogo());
				baseBrandVo.setBrandDesc(baseBrand.getBrandDesc());
				brandVolist.add(baseBrandVo);
			}
			return brandVolist;
		}
		return null;
	}
}
