package com.sandu.designplan.service.impl;

import com.google.gson.Gson;
import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.dao.DesignPlanProductMapper;
import com.sandu.designplan.dao.DesignPlanProductRenderSceneMapper;
import com.sandu.designplan.dao.DesignPlanRecommendedProductMapper;
import com.sandu.designplan.service.DesignPlanProductRenderSceneService;
import com.sandu.designplan.service.DesignPlanProductService;
import com.sandu.designplan.service.DesignPlanRecommendedProductService;
import com.sandu.designplan.service.DesignPlanProductService.costListEnum;
import com.sandu.optimizeplan.service.OptimizePlanService;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.model.ProductCostDetail;
import com.sandu.product.model.search.UserProductCollectSearch;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.product.service.BaseProductService;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.UserSO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title: DesignPlanProductServiceImpl.java
 * @Package com.sandu.design.service.impl
 * @Description:设计方案-设计方案产品库ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-26 11:26:11
 */
@Service("designPlanProductService")
public class DesignPlanProductServiceImpl implements DesignPlanProductService {
	private final static Gson GSON = new Gson();
	private final static Logger logger = LoggerFactory.getLogger(DesignPlanProductServiceImpl.class);
	private final static String CLASS_LOG_PREFIX = "[720场景产品清单列表服务]";
	@Autowired
	private DesignPlanProductMapper designPlanProductMapper;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private DesignPlanRecommendedProductService designPlanRecommendedProductService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private OptimizePlanService optimizePlanService;


	/**
	 * 新增数据
	 *
	 * @param designPlanProduct
	 * @return int
	 */
	@Override
	public int add(DesignPlanProduct designPlanProduct) {
		designPlanProductMapper.insertSelective(designPlanProduct);
		return designPlanProduct.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param designPlanProduct
	 * @return int
	 */
	@Override
	public int update(DesignPlanProduct designPlanProduct) {
		/* 删除 进入该样板房的缓存 */
		return designPlanProductMapper.updateByPrimaryKeySelective(designPlanProduct);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return designPlanProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return DesignPlanProduct
	 */
	@Override
	public DesignPlanProduct get(Integer id) {
		return designPlanProductMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProductsCostType> costList(DesignPlanProduct designPlanProduct, costListEnum type, UserSO userSo) {
		List<ProductsCostType> costTypeList = this.costTypeList(designPlanProduct, type);
		if (null == costTypeList || costTypeList.size() == 0) {
			return null;
		}
		String costCodes = null;
		for (ProductsCostType productsCostType : costTypeList) {
			if (StringUtils.isNotBlank(productsCostType.getCostCodes())) {
				if (productsCostType.getCostCodes().endsWith(",")) {
					costCodes += productsCostType.getCostCodes();
				} else {
					costCodes = costCodes + "," + productsCostType.getCostCodes();
				}

			}
		}
		ProductsCostType costType = new ProductsCostType();
		costType.setCostCodes(costCodes);
		costType.setPlanId(designPlanProduct.getPlanId());
		if (designPlanProduct.getBrandList() != null && designPlanProduct.getBrandList().size() > 0) {
			costType.setBrandList(designPlanProduct.getBrandList());
		}

		if (designPlanProduct.getPlatformId() != null && designPlanProduct.getPlatformId() > 0) {
			costType.setPlatformId(designPlanProduct.getPlatformId());
		}
		List<ProductsCost> costList = this.costList(costType, type);
		if (null == costList || costList.size() == 0) {
			return costTypeList;
		}

		ProductsCost cost = new ProductsCost();
		List<String> costTypeCodeList = new ArrayList<String>();
		for (ProductsCost productsCost : costList) {
			if (StringUtils.isNotBlank(productsCost.getCostTypeCode())) {
				costTypeCodeList.add(productsCost.getCostTypeCode());
			}
		}
		cost.setCostTypeCodeList(costTypeCodeList);
		SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
		logger.info(CLASS_LOG_PREFIX + "查询数据字典:costList:{}.", null == dictionary ? null : dictionary.toString());
		cost.setPlanId(designPlanProduct.getPlanId());
		if (designPlanProduct.getBrandList() != null && designPlanProduct.getBrandList().size() > 0) {
			cost.setBrandList(designPlanProduct.getBrandList());
		}
		
		if (designPlanProduct.getPlatformId() != null && designPlanProduct.getPlatformId() > 0) {
			cost.setPlatformId(designPlanProduct.getPlatformId());
		}

		if (dictionary != null) {
			cost.setSalePriceValueName(dictionary.getName());
		}
		
		if (userSo != null) {
			cost.setUserId(userSo.getUserId());
		} else {
			cost.setUserId(0);
		}

		cost.setSysDictionaryBOList(designPlanProduct.getSysDictionaryBOList());
		cost.setOwnBrandIdList(designPlanProduct.getOwnBrandIdList());
		List<ProductCostDetail> costDetails = this.costDetail(cost, type);
		
		//排除联盟公司未公开的产品(0不保密，1保密)
		if(costDetails != null && costDetails.size() > 0) {
			List<ProductCostDetail> unPublicProductList = new ArrayList<>();
			for (ProductCostDetail productCostDetail : costDetails) {
				if(productCostDetail.getSecrecy()!=null&&productCostDetail.getSecrecy()==1&&
						productCostDetail.getCompanyId().intValue()!=designPlanProduct.getCompanyId().intValue()) {
					unPublicProductList.add(productCostDetail);
				}
			}
			if(!unPublicProductList.isEmpty()) {
				costDetails.removeAll(unPublicProductList);
			}
			
		}
		

		// 组装参数
		for (ProductsCost productsCost : costList) {
			if (costDetails != null && costDetails.size() > 0) {
				List<ProductCostDetail> costDetailList = new ArrayList<ProductCostDetail>();
				for (ProductCostDetail productCostDetail : costDetails) {
					if (productsCost.getCostTypeCode().equals(productCostDetail.getCostTypeCode())) {
						costDetailList.add(productCostDetail);

					}
				}
				productsCost.setDetailList(costDetailList);
			}
		}
		for (ProductsCostType productsCostType : costTypeList) {
			List<ProductsCost> costDetailList = new ArrayList<ProductsCost>();
			for (ProductsCost productsCost : costList) {
				if(productsCostType.getCostCodes().contains(productsCost.getCostTypeCode())) {
					costDetailList.add(productsCost);
				}
			}
			productsCostType.setDetailList(costDetailList);
		}

		//处理产品价格参数
		for(ProductsCostType costType1 : costTypeList){
			Double costTypeTotalPrice = 0d ;
			for(ProductsCost cost1 :costType1.getDetailList()){
				Double cost1TotalPrice = 0d ;
				for(ProductCostDetail costDetail1:cost1.getDetailList()){
					cost1TotalPrice+= costDetail1.getTotalPrice().doubleValue();
				}
				cost1.setTotalPrice(new BigDecimal(cost1TotalPrice));
				costTypeTotalPrice += cost1.getTotalPrice().doubleValue();
			}
			costType1.setTotalPrice(new BigDecimal(costTypeTotalPrice));
		}




		logger.info(CLASS_LOG_PREFIX + "720场景清单列表：costList:{}", GSON.toJson(costTypeList));
		return costTypeList;
	}

	public int costTypeListCount(DesignPlanProduct designPlanProduct, costListEnum type) {

		// 参数验证/处理 ->start
		if (designPlanProduct == null) {
			return 0;
		}
		if (type == null) {
			type = costListEnum.designPlan;
		}
		// 参数验证/处理 ->end

		if (type == costListEnum.designPlan) {
			return designPlanProductMapper.costTypeListCount(designPlanProduct);
		} else if (type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costTypeListCount(designPlanProduct);
		} else if (type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductService.costTypeListCount(designPlanProduct);
		} else if (type == costListEnum.oneKeyDesignPlan) {
			return optimizePlanService.costTypeListCount(designPlanProduct);
		} else {

		}

		return 0;
	}

	private List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct, costListEnum type) {
		// 参数验证/处理 ->start
		if (designPlanProduct == null) {
			return null;
		}
		if (type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costTypeList(designPlanProduct);
		} else if (type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductService.costTypeList(designPlanProduct);
		}

		return null;
	}

	private List<ProductsCost> costList(ProductsCostType productsCostType, costListEnum type) {
		// 参数验证/参数处理 ->start
		if (productsCostType == null) {
			return null;
		}

		if (type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costList(productsCostType);
		} else if (type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductService.costList(productsCostType);
		}

		return null;
	}

	private List<ProductCostDetail> costDetail(ProductsCost cost, costListEnum type) {
		// 参数验证/参数处理 ->start
		if (cost == null) {
			return null;
		}
		if (type == costListEnum.designPlanRenderScene) {
			return designPlanProductRenderSceneService.costDetail(cost);
		} else if (type == costListEnum.designPlanRecommended) {
			return designPlanRecommendedProductService.costDetail(cost);
		}

		return null;
	}

	/**
	 * 所有数据
	 * 
	 * @param designPlanProduct
	 * @return List<DesignPlanProduct>
	 */
	@Override
	public List<DesignPlanProduct> getList(DesignPlanProduct designPlanProduct) {
		return designPlanProductMapper.selectList(designPlanProduct);
	}
}
