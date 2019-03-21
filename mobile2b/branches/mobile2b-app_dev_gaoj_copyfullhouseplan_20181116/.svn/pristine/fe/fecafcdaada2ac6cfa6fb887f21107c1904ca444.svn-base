/**
 * 
 */
package com.nork.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.BaseBrandResponse;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service.BaseBrandService;

/**
 * @author LENOVO
 * 
 */
@Controller
@RequestMapping("/{style}/client/brand")
public class BrandController {

	private static final Logger logger = LoggerFactory
			.getLogger(BrandController.class);

	@Resource
	private BaseBrandService baseBrandService;

	/**
	 * 获取品牌列表接口 </p>
	 * 
	 * @param resTextureSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getBrandList")
	@ResponseBody
	public Object getBrandList(
			@ModelAttribute("baseBrandSearch") BaseBrandSearch baseBrandSearch,
			HttpServletRequest request, HttpServletResponse response) {

		BaseBrandResponse baseBrandResponse = null;

		baseBrandSearch.setIsDeleted(0);
		List<BaseBrandResponse> list = new ArrayList<BaseBrandResponse>();
		int total = 0;
		try {

			total = baseBrandService.getCount(baseBrandSearch);

			if (total > 0) {

				List<BaseBrand> baseList = baseBrandService
						.getPaginatedList(baseBrandSearch);

				for (BaseBrand brand : baseList) {
					baseBrandResponse = new BaseBrandResponse();
					baseBrandResponse.setId(brand.getId());
					baseBrandResponse.setBrandName(brand.getBrandName());
					baseBrandResponse.setSysCode(brand.getSysCode());
					list.add(baseBrandResponse);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseBrand>(false, "数据异常!",
					baseBrandSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseBrandResponse>(total, list,
				baseBrandSearch.getMsgId());
	}

}
