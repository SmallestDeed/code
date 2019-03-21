package com.nork.onekeydesign.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.properties.AppProperties;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.onekeydesign.dao.DesignTempletProductMapper;
import com.nork.onekeydesign.model.DesignTempletProduct;
import com.nork.onekeydesign.model.DesignTempletProductResult;
import com.nork.onekeydesign.model.ProductListByTypeInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanGroupInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanStructureInfo;
import com.nork.onekeydesign.model.search.DesignTempletProductSearch;
import com.nork.onekeydesign.service.DesignPlanRecommendedService;
import com.nork.onekeydesign.service.DesignTempletProductService;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.ProductModelConstant;
import com.nork.product.model.StructureProduct;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.StructureProductService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: DesignTempletProductServiceImpl.java 
 * @Package com.nork.onekeydesign.service.impl
 * @Description:设计模块-方案产品表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-07 11:04:44
 * @version V1.0   
 */
@Service("designTempletProductService")
@Transactional
public class DesignTempletProductServiceImpl implements DesignTempletProductService {
    @Autowired
	private DesignTempletProductMapper designTempletProductMapper;

	@Autowired
	private GroupProductService groupProductService;
	
	@Autowired
	private StructureProductService structureProductService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	private Logger logger = LoggerFactory.getLogger(DesignTempletProductServiceImpl.class);

	// 白膜背景墙valukeyList
	static List<String> beijingValuekeyList = Utils.getListFromStr(
			Utils.getValueByFileKey(AppProperties.APP, AppProperties.SMALLPRODUCTTYPE_BEIJINGWALL_FILEKEY, "basic_dians,basic_shaf,basic_cant,basic_chuangt,basic_xingx,basic_beij")
			);
	
	/**
	 * 新增数据
	 *
	 * @param designTempletProduct
	 * @return  int 
	 */
	@Override
	public int add(DesignTempletProduct designTempletProduct) {
		return designTempletProduct.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designTempletProduct
	 * @return  int 
	 */
	@Override
	public int update(DesignTempletProduct designTempletProduct) {
		return designTempletProductMapper
				.updateByPrimaryKeySelective(designTempletProduct);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designTempletProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignTempletProduct 
	 */
	@Override
	public DesignTempletProduct get(Integer id) {
		return designTempletProductMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designTempletProduct
	 * @return   List<DesignTempletProduct>
	 */
	@Override
	public List<DesignTempletProduct> getList(DesignTempletProduct designTempletProduct) {
	    return designTempletProductMapper.selectList(designTempletProduct);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designTempletProductSearch
	 * @return   int
	 */
	@Override
	public int getCount(DesignTempletProductSearch designTempletProductSearch){
		return  designTempletProductMapper.selectCount(designTempletProductSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designTempletProductSearch
	 * @return   List<DesignTempletProduct>
	 */
	@Override
	public List<DesignTempletProduct> getPaginatedList(
			DesignTempletProductSearch designTempletProductSearch) {
		return designTempletProductMapper.selectPaginatedList(designTempletProductSearch);
	}


	/**
	 *  删除数据
	 *
	 * @param designCode
	 * @return  int 
	 */
	@Override

    public int deleteByDesignTempletCode(String designCode) {
		return designTempletProductMapper.deleteByDesignTempletCode(designCode);
	}

	@Override
	public int batchSave(Integer designTempletId,List<DesignTempletProduct> designProducts){
		//先删除样板房中的产品
//		designTempletProductMapper.deleteByDesignTempletId(designTempletId);
		
		//保存新的关联产品信息
		if( designProducts != null ){
			DesignTempletProduct designTempletProduct = null;
			for( int i=0;i<designProducts.size();i++ ){
				designTempletProduct = designProducts.get(i);
				designTempletProductMapper.insertSelective(designTempletProduct);
			}
		}
		return 0;
	}

	@Override
	public DesignTempletProduct sysSave(DesignTempletProduct designTempletProduct,LoginUser loginUser){
		designTempletProduct.setCreator(loginUser.getLoginName());
		designTempletProduct.setModifier(loginUser.getLoginName());
		designTempletProduct.setGmtCreate(new Date());
		designTempletProduct.setGmtModified(new Date());
		return designTempletProduct;
	}

	/**
	 * 得到design_templet_product表中的产品idList(去重复)
	 * @author huangsongbo
	 * @return
	 */
	public List<Integer> getDistinctProductIdList() {
		List<Integer> idList=designTempletProductMapper.getDistinctProductIdList();
		return idList;
	}

	public List<DesignTempletProductResult> getTempletIdProductList(DesignTempletProduct designTempletProduct){
		return designTempletProductMapper.byTempletIdProductList(designTempletProduct);
	}
	public List<DesignTempletProduct> getByTempletIdMainProduct(DesignTempletProduct designTempletProduct){
		return designTempletProductMapper.byTempletIdMainProduct(designTempletProduct);
	}

	/**
	 * 更新产品序号
	 * @param designTempletProduct
	 * @return  int
	 */
	@Override
	public int updateSameProductTypeIndex(DesignTempletProduct designTempletProduct) {
		return designTempletProductMapper.updateSameProductTypeIndex(designTempletProduct);
	}

	/**
	 * 根据样板房ID搜索结构产品
	 * @author xiaoxc
	 * @return
	 */
	public List<DesignTempletProduct> getStructureProductByDesignId(Integer designTempletId){
		return designTempletProductMapper.getStructureProductByDesignId(designTempletId);
	}

	@Override
	public List<DesignTempletProduct> getMainProductListByTempletId(Integer id) {
		DesignTempletProduct designTempletProduct = new DesignTempletProduct();
		designTempletProduct.setDesignTempletId(id);
		designTempletProduct.setGroupType(0);
		designTempletProduct.setIsDeleted(0);
		designTempletProduct.setIsMainProduct(1);
		// 样板房中有哪些白膜组合(取每个白膜组合的主产品)
		return this.getByTempletIdMainProduct(designTempletProduct);
	}

	@Override
	public List<DesignTempletProduct> getListByTempletId(Integer designTempletId) {
		if(designTempletId == null) {
			return null;
		}
		return designTempletProductMapper.getListByTempletId(designTempletId);
	}

	@Override
	public ProductListByTypeInfo getProductListByTypeInfo(
			List<DesignTempletProduct> designTempletProductList, 
			Integer matchType,
			Integer planRecommendedId
			) {
		
		// *参数验证 ->start
		if(designTempletProductList == null || designTempletProductList.size() == 0) {
			logger.error("------function:getProductListByTypeInfo(List<DesignTempletProduct> designTempletProductList)->(designTempletProductList == null || designTempletProductList.size() == 0) = true");
			return null;
		}
		if(matchType == null) {
			return null;
		}
		if(planRecommendedId == null){
			return null;
		}
		// *参数验证 ->end
		
		List<ProductListByTypeInfo.PlanProductInfo> productList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
		
		// *结构信息 ->start
		PlanStructureInfo structureInfo = new ProductListByTypeInfo().new PlanStructureInfo();
		Map<String, PlanProductInfo> structureMainProudctMap = new HashMap<String, PlanProductInfo>();
		Map<String, List<PlanProductInfo>> structureProudctListMap = new HashMap<String, List<PlanProductInfo>>();
		Map<Integer, StructureProduct> structureProudctListMapCache = new HashMap<Integer, StructureProduct>();
		// *结构信息 ->end
		
		// *组合信息 ->start
		PlanGroupInfo groupInfo = new ProductListByTypeInfo().new PlanGroupInfo();
		Map<String, PlanProductInfo> groupMainProudctMap = new HashMap<String, PlanProductInfo>();
		Map<String, List<PlanProductInfo>> groupProudctListMap = new HashMap<String, List<PlanProductInfo>>();
		// *组合信息 ->end
		
		/*// 组合id->组合类型的map
		Map<Integer, Integer> groupTypeMap = new HashMap<Integer, Integer>();*/
		// 组合id->组合信息的map
		Map<Integer, GroupProduct> groupInfoMap = new HashMap<Integer, GroupProduct>();
		
		// 检测推荐方案中有没有定制浴室柜,如果有的话,会进行特殊逻辑处理:样板房的浴室柜组合变成单品匹配逻辑 ->start
		boolean isRemmendedPlanHasDyba = designPlanRecommendedService.getIsRemmendedPlanHasDyba(planRecommendedId);
		// 检测推荐方案中有没有定制浴室柜,如果有的话,会进行特殊逻辑处理:样板房的浴室柜组合变成单品匹配逻辑 ->end
		
		for(DesignTempletProduct designTempletProduct : designTempletProductList) {
			
			logger.debug("posName:" + designTempletProduct.getPosName());
			
			PlanProductInfo planProductInfo = this.getPlanProductInfoFromDesignTempletProduct(designTempletProduct);
			logger.debug("posName:" + planProductInfo.getPosName());
			if(1 == designTempletProduct.getGroupType().intValue() && 
					/*(StringUtils.equals("dim", designTempletProduct.getBigTypeValuekey()))*/
					/*排除结构背景墙*/
					beijingValuekeyList.indexOf(designTempletProduct.getSmallTypeValuekey()) == -1
					// 户型绘制功能上线之后,地面结构匹配不走结构匹配逻辑 update by huangsongbo 2018.3.16
					&& !(StringUtils.equals("dim", designTempletProduct.getBigTypeValuekey()))
					) {
				// 判断为结构(只要dim结构)
				String planGroupId = designTempletProduct.getPlanGroupId();
				if(StringUtils.isEmpty(planGroupId) || StringUtils.equals("0", planGroupId.trim())){
					logger.error("------function:getProductListByTypeInfo(List<DesignTempletProduct> designTempletProductList)->\n"
							+ "存在group_type = 1 但是plan_group_id = null or plan_group_id = 0的数据,这种数据不允许出现");
				}else {
					// 设置结构信息(布局标识)
					this.setStructureProductInfo(planProductInfo, structureProudctListMapCache);
					if(structureMainProudctMap.containsKey(planGroupId)) {
						structureProudctListMap.get(planGroupId).add(planProductInfo);
					}else {
						structureMainProudctMap.put(planGroupId, planProductInfo);
						List<PlanProductInfo> planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
						planProductInfoList.add(planProductInfo);
						structureProudctListMap.put(planGroupId, planProductInfoList);
					}
				}
			}else if(StringUtils.isNotEmpty(designTempletProduct.getPlanGroupId()) 
					&& !StringUtils.equals("0", designTempletProduct.getPlanGroupId())
					&& designTempletProduct.getGroupType() != null && 0 == designTempletProduct.getGroupType().intValue()) {
				// 判断为组合
				// 区别与硬装替换和全屋替换
				if(0 == matchType.intValue()) {
					// 全屋替换
					String planGroupId = designTempletProduct.getPlanGroupId();
					
					/*// 获取组合类型
					Integer groupType = groupProductService.getGroupType(groupTypeMap, designTempletProduct.getProductGroupId());
					planProductInfo.setGroupType(groupType);*/
					// 获取组合信息
					GroupProduct groupProduct = groupProductService.getGroupProductByCache(groupInfoMap, designTempletProduct.getProductGroupId());
					if(groupProduct != null) {
						planProductInfo.setGroupType(groupProduct.getCompositeType());
						/*planProductInfo.setProductFilterPropList(groupProduct.getProductFilterPropList());*/
						// 浴室柜组合特殊处理:如果推荐方案中有定制浴室柜,把样板房中的浴室柜组合当作单品处理 ->start
						// 浴室柜组合value = 9
						if(isRemmendedPlanHasDyba && groupProduct.getCompositeType() != null && Constants.groupTypeValueYushigui.intValue() == groupProduct.getCompositeType().intValue()) {
							productList.add(planProductInfo);
							continue;
						}
						// 浴室柜组合特殊处理:如果推荐方案中有定制浴室柜,把样板房中的浴室柜组合当作单品处理 ->end
					}
					if(groupProudctListMap.containsKey(planGroupId)) {
						groupProudctListMap.get(planGroupId).add(planProductInfo);
					}else {
						List<PlanProductInfo> planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
						planProductInfoList.add(planProductInfo);
						groupProudctListMap.put(planGroupId, planProductInfoList);
					}
				}else {
					// 硬装替换,组合放在单品匹配列表里面
					productList.add(planProductInfo);
				}
			}else {
				// 判断为单品
				// 获取系列标识
				String seriesSign = baseProductService.getSeriesSign(planProductInfo.getSmallTypeValuekey());
				planProductInfo.setSeriesSign(seriesSign);
				productList.add(planProductInfo);
			}
		}
		
		// *组装结构信息 ->start
		structureInfo.setMainProudctMap(structureMainProudctMap);
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
		
		Integer structureId = planProductInfo.getGroupOrStructureId();
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

	private PlanProductInfo getPlanProductInfoFromDesignTempletProduct(DesignTempletProduct designTempletProduct) {
		PlanProductInfo planProductInfo = new PlanProductInfo();
		planProductInfo.setProductId(designTempletProduct.getProductId());
		planProductInfo.setBigTypeValuekey(designTempletProduct.getBigTypeValuekey());
		planProductInfo.setSmallTypeValuekey(designTempletProduct.getSmallTypeValuekey());
		planProductInfo.setPosName(designTempletProduct.getPosName());
		planProductInfo.setBigTypeValuekeyInit(designTempletProduct.getBigTypeValuekey());
		planProductInfo.setSmallTypeValuekeyInit(designTempletProduct.getSmallTypeValuekey());
		planProductInfo.setProductCode(designTempletProduct.getProductCode());
		planProductInfo.setProductLength(designTempletProduct.getProductLength());
		planProductInfo.setProductWidth(designTempletProduct.getProductWidth());
		planProductInfo.setProductHeight(designTempletProduct.getProductHeight());
		planProductInfo.setRegionMark(designTempletProduct.getRegionMark());
		planProductInfo.setFullPaveLength(designTempletProduct.getFullPaveLength());
		planProductInfo.setWallOrientation(designTempletProduct.getWallOrientation());
		planProductInfo.setWallType(designTempletProduct.getWallType());
		planProductInfo.setIsMainProduct(designTempletProduct.getIsMainProduct());
		planProductInfo.setPlanGroupId(designTempletProduct.getPlanGroupId());
		planProductInfo.setCenter(designTempletProduct.getCenter());
		planProductInfo.setMeasureCode(designTempletProduct.getMeasureCode());
		planProductInfo.setPlanProductId(designTempletProduct.getId());
		planProductInfo.setInitProductId(designTempletProduct.getProductId());
		planProductInfo.setBindParentProductId(designTempletProduct.getBindParentProductId());
		planProductInfo.setCenter(designTempletProduct.getCenter());
		planProductInfo.setGroupOrStructureId(designTempletProduct.getProductGroupId());
		planProductInfo.setGroupType(designTempletProduct.getGroupType());
		planProductInfo.setIsMainStructureProduct(designTempletProduct.getIsMainStructureProduct());
		planProductInfo.setIsGroupReplaceWay(designTempletProduct.getIsGroupReplaceWay());
		planProductInfo.setProductIndex(designTempletProduct.getProductIndex());
		planProductInfo.setProductSmallpoxIdentify(designTempletProduct.getProductSmallpoxIdentify());
		planProductInfo.setPosIndexPath(designTempletProduct.getPosIndexPath());
		return planProductInfo;
	}

	@Override
	public int updateDesignTemplateProduct(DesignTempletProduct designTempletProduct) {
		// TODO Auto-generated method stub
		return designTempletProductMapper.updateDesignTemplateProduct(designTempletProduct);
	}

	@Override
	public List<String> getWallOrientationList(Integer templetId) {
		if(templetId == null) {
			return new ArrayList<String>();
		}
		return designTempletProductMapper.getWallOrientationList(templetId);
	}

	@Override
	public List<DesignTempletProduct> getBeijingProductInfo(Integer designTempletId) {
		if(designTempletId == null) {
			return null;
		}
		return designTempletProductMapper.getBeijingProductInfo(designTempletId);
	}

	@Override
	public int getCountByTempletIdAndProductType(Integer designTempletId,
			String smallTypeValuekey) {
		// 参数验证 ->start
		if(designTempletId == null){
			return 0;
		}
		if(StringUtils.isEmpty(smallTypeValuekey)){
			return 0;
		}
		// 参数验证 ->end
		
		SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByValueKey(smallTypeValuekey);
		if(sysDictionarySmallType == null){
			return 0;
		}
		SysDictionary sysDictionaryBigType = sysDictionaryService.findOneByValueKey(sysDictionarySmallType.getType());
		if(sysDictionaryBigType == null){
			return 0;
		}
		return designTempletProductMapper.getCountByPlanIdAndProductTypeValue(
				designTempletId, sysDictionaryBigType.getValue(), sysDictionarySmallType.getValue());
	}

	@Override
	public int getCountByTempletIdAndProductTypeValueList(
			Integer designTempletId, Integer bigTypeValue,
			List<Integer> smallTypeValueList) {
		// 参数验证 ->start
		if(designTempletId == null){
			return 0;
		}
		if(bigTypeValue == null){
			return 0;
		}
		if(Lists.isEmpty(smallTypeValueList)){
			return 0;
		}
		// 参数验证 ->end
		
		return designTempletProductMapper.getCountByTempletIdAndProductTypeValueList(designTempletId, bigTypeValue, smallTypeValueList);
	}
	
}
