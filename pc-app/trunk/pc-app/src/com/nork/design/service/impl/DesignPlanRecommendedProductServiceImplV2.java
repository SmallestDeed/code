package com.nork.design.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.design.model.*;
import com.nork.design.model.output.UnityPlanProductRecommended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.StringUtils;
import com.nork.design.dao.DesignPlanRecommendedProductMapperV2;
import com.nork.design.exception.IntelligenceDecorationException;
import com.nork.design.model.ProductListByTypeInfo.PlanGroupInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanStructureInfo;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignTempletProductService;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.ProductModelConstant;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.StructureProductService;
import com.nork.productprops.service.ProductPropsService;

@Service("designPlanRecommendedProductServiceV2")
@Transactional
public class DesignPlanRecommendedProductServiceImplV2 implements DesignPlanRecommendedProductServiceV2{

	Logger logger = LoggerFactory.getLogger(DesignPlanRecommendedProductServiceImplV2.class);
	
	DesignPlanRecommendedProductMapperV2 designPlanRecommendedProductMapperV2;
	
	@Autowired
	private GroupProductService groupProductService;
	
	@Autowired
	private DesignTempletProductService designTempletProductService;
	
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	
	@Autowired
	private StructureProductService structureProductService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	public void setDesignPlanRecommendedProductMapperV2(
			DesignPlanRecommendedProductMapperV2 designPlanRecommendedProductMapperV2) {
		this.designPlanRecommendedProductMapperV2 = designPlanRecommendedProductMapperV2;
	}

	@Override
	public int add(DesignPlanRecommendedProduct DesignPlanRecommendedProduct) {
		return designPlanRecommendedProductMapperV2.insertSelective(DesignPlanRecommendedProduct);
	}

	@Override
	public int update(DesignPlanRecommendedProduct DesignPlanRecommendedProduct) {
		return designPlanRecommendedProductMapperV2.updateByPrimaryKeySelective(DesignPlanRecommendedProduct);
	}

	@Override
	public int delete(Integer id) {
		return designPlanRecommendedProductMapperV2.deleteByPrimaryKey(id);
	}

	@Override
	public DesignPlanRecommendedProduct get(Integer id) {
		return designPlanRecommendedProductMapperV2.selectByPrimaryKey(id);
	}

	@Override
	public List<DesignPlanRecommendedProduct> getList(DesignPlanRecommendedProduct DesignPlanRecommendedProduct) {
		return designPlanRecommendedProductMapperV2.selectList(DesignPlanRecommendedProduct);
	}

	public List<UnityPlanProductRecommended> getUnityPlanProductRecommendedList(Integer recommendedId){
		return designPlanRecommendedProductMapperV2.selectUnityPlanProductRecommendedList(recommendedId);
	}

	@Override
	public int getCount(DesignPlanRecommendedProduct DesignPlanRecommendedProduct) {
		return designPlanRecommendedProductMapperV2.selectCount(DesignPlanRecommendedProduct);
	}

	@Override
	public void batchAdd(List<DesignPlanRecommendedProduct> recommendedProductlist) {
		designPlanRecommendedProductMapperV2.batchAdd(recommendedProductlist);
	}

	@Override
	public List<DesignPlanRecommendedProductResult> getListLeftProduct(DesignPlanRecommendedProduct dprp) {
		return designPlanRecommendedProductMapperV2.getListLeftProduct(dprp);
	}

	@Override
	public int getListLeftProductCount(DesignPlanRecommendedProduct dprp) {
		return designPlanRecommendedProductMapperV2.getListLeftProductCount(dprp);
	}
	/**
	 * 通过推荐id 删除产品
	 * @param planRecommendedId
	 */
	@Override
	public void deletedByPlanRecommendedId(int planRecommendedId) {
		designPlanRecommendedProductMapperV2.deletedByPlanRecommendedId(planRecommendedId);
		
	}

	@Override
	public List<DesignPlanRecommendedProduct> getMainProductListByPlanRecommendedId(Integer id) {
		return designPlanRecommendedProductMapperV2.getMainProductListByPlanRecommendedId(id);
	}

	@Override
	public List<DesignPlanRecommendedProduct> getListByPlanIdAndGroupId(Integer planRecommendedId,
			Integer productGroupId) {
		return designPlanRecommendedProductMapperV2.getListByPlanIdAndGroupId(planRecommendedId, productGroupId);
	}
	
	@Override
	public List<DesignProductResult> getPlanProductList(Integer recommendedPlanId) {
		return designPlanRecommendedProductMapperV2.getPlanProductList(recommendedPlanId);
	}

	@Override
	public List<DesignPlanRecommendedProduct> getListByRecommendedId(Integer planRecommendedId) {
		// *参数验证 ->start
		if(planRecommendedId == null) {
			return null;
		}
		// *参数验证 ->end
		return designPlanRecommendedProductMapperV2.getListByRecommendedId(planRecommendedId);
	}

	@Override
	public ProductListByTypeInfo getProductListByTypeInfo(
			List<DesignPlanRecommendedProduct> designPlanRecommendedProductList) throws IntelligenceDecorationException {
		
		// *参数验证 ->start
		if(designPlanRecommendedProductList == null || designPlanRecommendedProductList.size() == 0) {
			logger.error("------function:getProductListByTypeInfo(List<DesignPlanRecommendedProduct> designPlanRecommendedProductList)->(designPlanRecommendedProductList == null || designPlanRecommendedProductList.size() == 0) = true");
			return null;
		}
		// *参数验证 ->end
		
		List<ProductListByTypeInfo.PlanProductInfo> productList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
		
		// *结构信息 ->start
		PlanStructureInfo structureInfo = new ProductListByTypeInfo().new PlanStructureInfo();
		Map<String, PlanProductInfo> mainProudctMap = new HashMap<String, PlanProductInfo>();
		Map<String, List<PlanProductInfo>> structureProudctListMap = new HashMap<String, List<PlanProductInfo>>();
		Map<Integer, StructureProduct> structureProudctListMapCache = new HashMap<Integer, StructureProduct>();
		// *结构信息 ->end
		
		// *组合信息 ->start
		PlanGroupInfo groupInfo = new ProductListByTypeInfo().new PlanGroupInfo();
		Map<String, PlanProductInfo> groupMainProudctMap = new HashMap<String, PlanProductInfo>();
		Map<String, List<PlanProductInfo>> groupProudctListMap = new HashMap<String, List<PlanProductInfo>>();
		// *组合信息 ->end
		Map<String, List<PlanProductInfo>> productListMap = new HashMap<String, List<PlanProductInfo>>();
		
		/*// 组合id->组合类型的map
		Map<Integer, Integer> groupTypeMap = new HashMap<Integer, Integer>();*/
		// 组合id->组合信息的map
		Map<Integer, GroupProduct> groupInfoMap = new HashMap<Integer, GroupProduct>();
		
		// wallType优先级,优先级排序:由高到低:J,K,L
		List<String> wallTypeScoreList = Arrays.asList(new String[] {"null", "L", "K", "J"});
		
		for(DesignPlanRecommendedProduct designPlanRecommendedProduct : designPlanRecommendedProductList) {
			// 去掉白膜
			if(designPlanRecommendedProduct != null && designPlanRecommendedProduct.getProductCode() != null && designPlanRecommendedProduct.getProductCode().startsWith("baimo_")) {
				continue;
			}
			
			PlanProductInfo planProductInfo = this.getPlanProductInfoFromDesignPlanRecommendedProduct(designPlanRecommendedProduct);
			/*if(1 == designPlanRecommendedProduct.getGroupType().intValue() && StringUtils.equals("dim", designPlanRecommendedProduct.getBigTypeValuekey())) {*/
			if(1 == designPlanRecommendedProduct.getGroupType().intValue() && 
					/*(StringUtils.equals("dim", designTempletProduct.getBigTypeValuekey()))*/
					/*排除结构背景墙*/
					DesignTempletProductServiceImpl.beijingValuekeyList.indexOf(designPlanRecommendedProduct.getSmallTypeValuekey()) == -1
					) {
				// 判断为结构(只要dim结构)
				String planGroupId = designPlanRecommendedProduct.getPlanGroupId();
				
				if(StringUtils.isEmpty(planGroupId) || StringUtils.equals("0", planGroupId.trim())){
					logger.error("------function:getProductListByTypeInfo(...)->\n"
							+ "存在group_type = 1 但是plan_group_id = null or plan_group_id = 0的数据,这种数据不允许出现");
				}else {
					this.setStructureProductInfo(planProductInfo, structureProudctListMapCache);
					if(mainProudctMap.containsKey(planGroupId)) {
						structureProudctListMap.get(planGroupId).add(planProductInfo);
					}else {
						mainProudctMap.put(planGroupId, planProductInfo);
						List<PlanProductInfo> planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
						planProductInfoList.add(planProductInfo);
						structureProudctListMap.put(planGroupId, planProductInfoList);
					}
				}
				// *也添加到单品中 ->start
				PlanProductInfo planProductInfoCopy = planProductInfo.clone();
				planProductInfoCopy.setPlanGroupId(null);
				planProductInfoCopy.setGroupType(null);
				productList.add(planProductInfoCopy);
				this.dealWithProductListMap(productListMap, planProductInfoCopy);
				// *也添加到单品中 ->end
			}else if(StringUtils.isNotEmpty(designPlanRecommendedProduct.getPlanGroupId()) 
					&& !StringUtils.equals("0", designPlanRecommendedProduct.getPlanGroupId())
					&& designPlanRecommendedProduct.getGroupType() != null && 0 == designPlanRecommendedProduct.getGroupType().intValue()) {
				// 判断为组合
				String planGroupId = designPlanRecommendedProduct.getPlanGroupId();
				
				// 获取组合类型
				/*Integer groupType = groupProductService.getGroupType(groupTypeMap, designPlanRecommendedProduct.getProductGroupId());
				planProductInfo.setGroupType(groupType);*/
				// 获取组合信息
				GroupProduct groupProduct = groupProductService.getGroupProductByCache(groupInfoMap, designPlanRecommendedProduct.getProductGroupId());
				if(groupProduct != null) {
					planProductInfo.setGroupType(groupProduct.getCompositeType());
					planProductInfo.setProductFilterPropList(groupProduct.getProductFilterPropList());
				}else{
					throw new IntelligenceDecorationException("推荐方案中某个组合被删除了,一键装修失败");
				}
				
				if(groupProudctListMap.containsKey(planGroupId)) {
					groupProudctListMap.get(planGroupId).add(planProductInfo);
				}else {
					List<PlanProductInfo> planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
					planProductInfoList.add(planProductInfo);
					groupProudctListMap.put(planGroupId, planProductInfoList);
				}
				// *也添加到单品中 ->start
				PlanProductInfo planProductInfoCopy = planProductInfo.clone();
				planProductInfoCopy.setPlanGroupId(null);
				planProductInfoCopy.setGroupType(null);
				String smallTypeValuekeyInit = planProductInfo.getSmallTypeValuekeyInit();
				if(StringUtils.isNotBlank(smallTypeValuekeyInit)) {
					smallTypeValuekeyInit = "basic_" + smallTypeValuekeyInit.replace("basic_", "");
					planProductInfoCopy.setSmallTypeValuekeyInit(smallTypeValuekeyInit);
				}
				productList.add(planProductInfoCopy);
				this.dealWithProductListMap(productListMap, planProductInfoCopy);
				// *也添加到单品中 ->end
			}else {
				// 判断为单品
				ProductListByTypeInfo.PlanProductInfo productListByTypeInfo = this.getPlanProductInfoFromDesignPlanRecommendedProduct(designPlanRecommendedProduct);
				// 配置背景墙匹配优先级
				String wallType = productListByTypeInfo.getWallType();
				if(StringUtils.isNotEmpty(wallType)) {
					productListByTypeInfo.setWallTypeScore(wallTypeScoreList.indexOf(wallType));
				}else {
					productListByTypeInfo.setWallTypeScore(-1);
				}
				productList.add(productListByTypeInfo);
				// 拼装productListMap
				this.dealWithProductListMap(productListMap, productListByTypeInfo);
			}
		}
		
		// *组装结构信息 ->start
		structureInfo.setMainProudctMap(mainProudctMap);
		structureInfo.setStructureProudctListMap(structureProudctListMap);
		// *组装结构信息 ->end
				
		// *组装组合信息 ->start
		// 组装groupMainProudctMap
		for(String key : groupProudctListMap.keySet()) {
			List<PlanProductInfo> planProductInfoList = groupProudctListMap.get(key);
			// 取出主产品
			PlanProductInfo planProductInfoMainProduct = null;
			for(PlanProductInfo planProductInfo : planProductInfoList) {
				if(planProductInfo.getIsMainProduct() != null && 1 == planProductInfo.getIsMainProduct().intValue()) {
					planProductInfoMainProduct = planProductInfo;
					break;
				}
			}
			if(planProductInfoMainProduct != null) {
				// 取出主产品对应的初始化白膜产品的过滤/需要参加匹配条件的属性(暂定为床头柜组合)
				if(StringUtils.equals("ca", planProductInfoMainProduct.getBigTypeValuekey()) 
						&& (StringUtils.equals("basic_beca", planProductInfoMainProduct.getSmallTypeValuekey()) || StringUtils.equals("beca", planProductInfoMainProduct.getSmallTypeValuekey()))) {
					planProductInfoMainProduct.setProductFilterPropList(
							baseProductService.getProductPropsSimpleByProductId(planProductInfoMainProduct.getInitProductId(), ProductModelConstant.PRODUCTATTRCODE_CHAOXIANG)
							);
				}
				groupMainProudctMap.put(key, planProductInfoMainProduct);
			}else {
				// 主产品都不见了...数据有错误,全部当单品处理
				logger.error("------function:getProductListByTypeInfo(....)->\n"
						+ "识别到样板房里的一个组合,主产品不见了,plan_product_id = " + key);
				for(PlanProductInfo planProductInfo : planProductInfoList) {
					productList.add(planProductInfo);
				}
			}
		}
		groupInfo.setGroupProudctListMap(groupProudctListMap);
		groupInfo.setMainProudctMap(groupMainProudctMap);
		// *组装组合信息 ->end
		
		ProductListByTypeInfo productListByTypeInfo = new ProductListByTypeInfo();
		productListByTypeInfo.setGroupInfo(groupInfo);
		productListByTypeInfo.setProductList(productList);
		productListByTypeInfo.setProductListMap(productListMap);
		productListByTypeInfo.setStructureInfo(structureInfo);
		
		return productListByTypeInfo;
	}

	/**
	 * 设置结构信息 to planProductInfo(结构布局标识)
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param structureProudctListMapCache
	 */
	private void setStructureProductInfo(PlanProductInfo planProductInfo,
			Map<Integer, StructureProduct> structureProudctListMapCache) {
		// 参数验证 ->start
		if(planProductInfo == null) {
			return;
		}
		if(structureProudctListMapCache == null) {
			return;
		}
		// 参数验证 ->end
		
		/*Integer structureId = planProductInfo.getGroupOrStructureId();*/
		Integer structureId = null;
		String planStructureId = planProductInfo.getPlanGroupId();
		if(StringUtils.isNotEmpty(planStructureId)) {
			try {
				structureId = Integer.valueOf(planStructureId.split("_")[0]);
			}catch (Exception e) {
				
			}
		}
		if(structureId != null) {
			if(structureProudctListMapCache.containsKey(structureId)) {
				StructureProduct structureProduct = structureProudctListMapCache.get(structureId);
				if(structureProduct != null) {
					planProductInfo.setStructureProductSmallpoxIdentify(structureProduct.getStructureGroundIdentify());
				}
			}else {
				StructureProduct structureProduct = structureProductService.get(structureId);
				if(structureProduct != null) {
					planProductInfo.setStructureProductSmallpoxIdentify(structureProduct.getStructureGroundIdentify());
					structureProudctListMapCache.put(structureId, structureProduct);
				}
			}
		}
		
	}

	private PlanProductInfo getPlanProductInfoFromDesignPlanRecommendedProduct(
			DesignPlanRecommendedProduct designPlanRecommendedProduct) {
		PlanProductInfo planProductInfo = new PlanProductInfo();
		planProductInfo.setProductId(designPlanRecommendedProduct.getProductId());
		planProductInfo.setBigTypeValuekey(designPlanRecommendedProduct.getBigTypeValuekey());
		planProductInfo.setSmallTypeValuekey(designPlanRecommendedProduct.getSmallTypeValuekey());
		planProductInfo.setPosName(designPlanRecommendedProduct.getPosName());
		planProductInfo.setBigTypeValuekeyInit(designPlanRecommendedProduct.getBigTypeValuekeyInit());
		planProductInfo.setSmallTypeValuekeyInit(designPlanRecommendedProduct.getSmallTypeValuekeyInit());
		planProductInfo.setProductCode(designPlanRecommendedProduct.getProductCode());
		planProductInfo.setProductLength(designPlanRecommendedProduct.getProductLength());
		planProductInfo.setProductWidth(designPlanRecommendedProduct.getProductWidth());
		planProductInfo.setProductHeight(designPlanRecommendedProduct.getProductHeight());
		planProductInfo.setRegionMark(designPlanRecommendedProduct.getRegionMark());
		planProductInfo.setWallOrientation(designPlanRecommendedProduct.getWallOrientation());
		planProductInfo.setWallType(designPlanRecommendedProduct.getWallType());
		planProductInfo.setIsMainProduct(designPlanRecommendedProduct.getIsMainProduct());
		planProductInfo.setPlanGroupId(designPlanRecommendedProduct.getPlanGroupId());
		planProductInfo.setMeasureCode(designPlanRecommendedProduct.getMeasureCode());
		planProductInfo.setBindParentProductId(designPlanRecommendedProduct.getBindParentProductId());
		planProductInfo.setSplitTexturesChooseInfo(designPlanRecommendedProduct.getSplitTexturesChooseInfo());
		planProductInfo.setIsStandard(designPlanRecommendedProduct.getIsStandard());
		planProductInfo.setStyleId(designPlanRecommendedProduct.getStyleId());
		planProductInfo.setProductIndex(designPlanRecommendedProduct.getProductIndex());
		planProductInfo.setIsMainStructureProduct(designPlanRecommendedProduct.getIsMainStructureProduct());
		planProductInfo.setIsGroupReplaceWay(designPlanRecommendedProduct.getIsGroupReplaceWay());
		planProductInfo.setInitProductId(designPlanRecommendedProduct.getInitProductId());
		planProductInfo.setGroupProductUniqueId(designPlanRecommendedProduct.getGroupProductUniqueId());
		planProductInfo.setProductSmallpoxIdentify(designPlanRecommendedProduct.getProductSmallpoxIdentify());
		planProductInfo.setBrandId(designPlanRecommendedProduct.getBrandId());
		planProductInfo.setProductModelNumber(designPlanRecommendedProduct.getProductModelNumber());
		planProductInfo.setSeriesId(designPlanRecommendedProduct.getSeriesId());
		return planProductInfo;
	}

	/**
	 * 组装Map<String, List<PlanProductInfo>> productListMap
	 * productListMap格式:Map<大类valuekey, 产品list>
	 * 
	 * @author huangsongbo
	 * @param productListMap
	 * @param productListByTypeInfo
	 */
	private void dealWithProductListMap(Map<String, List<PlanProductInfo>> productListMap,
			PlanProductInfo productListByTypeInfo) {
		String bigTypeValuekey = productListByTypeInfo.getBigTypeValuekey();
		if(productListMap.containsKey(bigTypeValuekey)) {
			List<PlanProductInfo> planProductInfoList = productListMap.get(bigTypeValuekey);
			planProductInfoList.add(productListByTypeInfo);
		}else {
			List<ProductListByTypeInfo.PlanProductInfo> planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
			planProductInfoList.add(productListByTypeInfo);
			productListMap.put(bigTypeValuekey, planProductInfoList);
		}
	}

	@Override
	public int costTypeListCount(DesignPlanProduct designPlanProduct) {
		return designPlanRecommendedProductMapperV2.costTypeListCount(designPlanProduct);
	}

	@Override
	public List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct) {
		return designPlanRecommendedProductMapperV2.costTypeList(designPlanProduct);
	}

	@Override
	public List<ProductsCost> costList(ProductsCostType productsCostType) {
		return designPlanRecommendedProductMapperV2.costList(productsCostType);
	}

	@Override
	public List<ProductCostDetail> costDetail(ProductsCost cost) {
		return designPlanRecommendedProductMapperV2.costDetail(cost);
	}

	@Override
	public List<String> getDeleteWallOrientationList(Integer planRecommendedId) {
		
		// 参数验证 ->start
		if(planRecommendedId == null) {
			logger.error("------function:getDeleteWallOrientationList(Integer planRecommendedId)->\n"
					+ "planRecommendedId = null");
			return null;
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
		if(designPlanRecommended == null) {
			logger.error("------function:getDeleteWallOrientationList(Integer planRecommendedId)->\n"
					+ "designPlanRecommended = null(planRecommendedId = " + planRecommendedId + ")");
			return null;
		}
		// 参数验证 ->end
		
		// 推荐方案对应的样板房id
		Integer templetId = designPlanRecommended.getDesignTemplateId();
		
		List<String> wallOrientationListTemplet = designTempletProductService.getWallOrientationList(templetId);
		List<String> wallOrientationListRecommended = this.getWallOrientationList(designPlanRecommended.getId());
		
		// 取差集
		wallOrientationListTemplet.removeAll(wallOrientationListRecommended);
		
		// 去空元素
		for (int index = 0; index < wallOrientationListTemplet.size(); index++) {
			String str = wallOrientationListTemplet.get(index);
			if(StringUtils.isBlank(str)) {
				wallOrientationListTemplet.remove(str);
				index --;
			}
		}
		
		return wallOrientationListTemplet;
	}

	@Override
	public List<String> getWallOrientationList(Integer id) {
		if(id == null) {
			return new ArrayList<String>();
		}
		return designPlanRecommendedProductMapperV2.getWallOrientationList(id);
	}

	@Override
	public List<DesignPlanRecommendedProduct> getBeijingProductInfo(Integer recommendedPlanId) {
		if(recommendedPlanId == null) {
			return null;
		}
		return designPlanRecommendedProductMapperV2.getBeijingProductInfo(recommendedPlanId);
	}

	/**
	 * 根据推荐方案ID获取推荐方案产品分类keys
	 * @param recommendedPlanId
	 * @return
	 */
	@Override
	public List<String> findListByRecommendedPlanId(Integer recommendedPlanId) {
		return designPlanRecommendedProductMapperV2.findListByRecommendedPlanId(recommendedPlanId);
	}

	/**
	 * 查询没有更新组合唯一标识的推荐方案
	 * @return
	 */
	@Override
	public List<DesignPlanRecommendedProduct> findNoGroupProductUniqueIdList() {
		return designPlanRecommendedProductMapperV2.findNoGroupProductUniqueIdList();
	}

	/**
	 * 根据推荐方案ID查询方案组合产品
	 * @param recommendedPlanId
	 * @return
	 */
	@Override
	public List<DesignPlanRecommendedProduct> findRecommendedGroupProductByPlanRecommendedId(Integer recommendedPlanId) {
		return designPlanRecommendedProductMapperV2.findRecommendedGroupProductByPlanRecommendedId(recommendedPlanId);
	}

}
