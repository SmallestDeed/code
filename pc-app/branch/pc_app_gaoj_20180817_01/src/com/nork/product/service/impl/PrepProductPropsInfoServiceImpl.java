package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.product.dao.BaseProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.product.dao.PrepProductPropsInfoMapper;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.PrepProductPropsInfo;
import com.nork.product.model.ProductStatuCode;
import com.nork.product.model.search.PrepProductPropsInfoSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.PrepProductPropsInfoService;
import com.nork.product.service.impl.BaseProductServiceImpl.getBaseProductPropListMapEnum;
import com.nork.productprops.model.BaseProductProps;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: PrepProductPropsInfoServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-预处理表(属性匹配)ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-02-25 14:55:01
 * @version V1.0   
 */
@Service("prepProductPropsInfoService")
public class PrepProductPropsInfoServiceImpl implements PrepProductPropsInfoService {

	private static Logger logger = Logger.getLogger(PrepProductPropsInfoServiceImpl.class);
	
	@Autowired
	private PrepProductPropsInfoMapper prepProductPropsInfoMapper;
	
	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private BaseProductMapper baseProductMapper;

	/**
	 * 新增数据
	 *
	 * @param prepProductPropsInfo
	 * @return  int
	 */
	@Override
	public int add(PrepProductPropsInfo prepProductPropsInfo) {
		prepProductPropsInfoMapper.insertSelective(prepProductPropsInfo);
		return prepProductPropsInfo.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param prepProductPropsInfo
	 * @return  int 
	 */
	@Override
	public int update(PrepProductPropsInfo prepProductPropsInfo) {
		return prepProductPropsInfoMapper
				.updateByPrimaryKeySelective(prepProductPropsInfo);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return prepProductPropsInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  PrepProductPropsInfo 
	 */
	@Override
	public PrepProductPropsInfo get(Integer id) {
		return prepProductPropsInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  prepProductPropsInfo
	 * @return   List<PrepProductPropsInfo>
	 */
	@Override
	public List<PrepProductPropsInfo> getList(PrepProductPropsInfo prepProductPropsInfo) {
	    return prepProductPropsInfoMapper.selectList(prepProductPropsInfo);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  prepProductPropsInfoSearch
	 * @return   int
	 */
	@Override
	public int getCount(PrepProductPropsInfoSearch prepProductPropsInfoSearch){
		return  prepProductPropsInfoMapper.selectCount(prepProductPropsInfoSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  prepProductPropsInfoSearch
	 * @return   List<PrepProductPropsInfo>
	 */
	@Override
	public List<PrepProductPropsInfo> getPaginatedList(
			PrepProductPropsInfoSearch prepProductPropsInfoSearch) {
		return prepProductPropsInfoMapper.selectPaginatedList(prepProductPropsInfoSearch);
	}

	/**
	 * 更新属性(根据productIdList)
	 * @author huangsongbo
	 * @param updatePropsProductIdList
	 */
	public void updateByProductIdList(Set<Integer> updatePropsProductIdList) {
		if(updatePropsProductIdList == null || updatePropsProductIdList.size() == 0){
			return;
		}
		String isSaveAllStr = Utils.getValue("product.prep.props.isSaveAll", "1");
		/**是否保存排序属性数据*/
		boolean isSaveSort = false;
		if(StringUtils.equals("1", isSaveAllStr)){
			isSaveSort = true;
		}
		// 应该改成查询发布中/已发布的产品??
		/*String productStatusStr = Utils.getValue("product.prep.productStatus", "1");
		List<Integer> productStatusList = Utils.getIntegerListFromStringList(productStatusStr);*/
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.HAS_BEEN_RELEASE);
		productStatusList.add(ProductStatuCode.RELEASEING);
		
		// 修改成批量取属性
		for(Integer productIdItem : updatePropsProductIdList){
			BaseProductProps baseProductProps = this.getBaseProductPropsByProductId(productIdItem);
			if(!isSaveSort){
				// 不保存排序属性数据的话.没有过滤属性的产品跳过
				if(baseProductProps.getProductFilterPropList() == null || baseProductProps.getProductFilterPropList().size() == 0){
					continue;
				}
			}
			
			// 删除productId为updatePropsProductIdList中的数据
			this.deleteByProductId(productIdItem);
			// 删除matchProductId = 该产品id的数据
			this.deleteByMatchProductId(productIdItem);
			// 得到该产品的第三级分类
			// 判断是否是白膜
			BaseProduct baseProduct = baseProductService.get(productIdItem);
			if(baseProduct == null){
				continue;
			}
			if(baseProduct.getProductCode().startsWith("baimo_")){
				// 处理白膜
				SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(baseProduct.getProductTypeValue()));
				if(sysDictionary == null){
					continue;
				}
				String categoryCode = sysDictionary.getValuekey();
				List<Integer> productIdList = baseProductService.getPreprocessProductIdByCategoryCode(categoryCode, productStatusList);
				// 产品属性装配(每个产品的属性装配好)->start
				List<BaseProductProps> baseProductPropsList = new ArrayList<BaseProductProps>();
				for(Integer productId : productIdList){
					BaseProductProps baseProductPropsMatch = this.getBaseProductPropsByProductId(productId);
					if(!isSaveSort){
						// 不保存排序属性数据的话.过滤属性为0的产品匹配数据
						if(baseProductPropsMatch.getProductFilterPropList() != null && baseProductPropsMatch.getProductFilterPropList().size() == 0){
							continue;
						}
					}
					baseProductPropsList.add(baseProductPropsMatch);
				}
				// 产品属性装配(每个产品的属性装配好) ->end
				List<BaseProductProps> baseProductPropsListBaimo = new ArrayList<BaseProductProps>();
				baseProductPropsListBaimo.add(baseProductProps);
				List<PrepProductPropsInfo> baimoPrepProductPropsInfoList = 
						this.getPrepProductPropsInfo(baseProductPropsListBaimo, baseProductPropsList, categoryCode, true);
				// 白膜产品->该产品的匹配数据 ->end
				this.batchSave(baimoPrepProductPropsInfoList);
			}else{
				// 处理普通产品
				String categoryCode = prepProductPropsInfoMapper.getThirdStageCodeByProductId(productIdItem);
				// 找到该产品对应分类下的所有产品
				// 获得该分类下所有产品(必须有关联属性)
				List<Integer> productIdList = baseProductService.getPreprocessProductIdByCategoryCode(categoryCode, productStatusList);
				// 产品属性装配(每个产品的属性装配好)->start
				List<BaseProductProps> baseProductPropsList = new ArrayList<BaseProductProps>();
				for(Integer productId : productIdList){
					BaseProductProps baseProductPropsMatch = getBaseProductPropsByProductId(productId);
					if(!isSaveSort){
						// 不保存排序属性数据的话.过滤属性为0的产品匹配数据
						if(baseProductPropsMatch.getProductFilterPropList() != null && baseProductPropsMatch.getProductFilterPropList().size() == 0){
							continue;
						}
					}
					baseProductPropsList.add(baseProductPropsMatch);
				}
				// 产品属性装配(每个产品的属性装配好) ->end
				
				// 硬装isSaveAll = false,软装isSaveAll = true(为了防止新增产品,找不到经过过滤属性过滤的产品)
				String[] categoryCodes = new String[]{"dim","qiangm","tianh","meng"};
				List<String> categoryCodeList = Arrays.asList(categoryCodes);/*new ArrayList<String>();*/
				boolean isSaveAll = false;
				if(categoryCodeList.indexOf(categoryCode) != -1){
					//硬装(不可新增)
					
				}else{
					isSaveAll = true;
				}
				
				List<BaseProductProps> baseProductPropsList2 = new ArrayList<BaseProductProps>();
				baseProductPropsList2.add(baseProductProps);
				
				// 属性匹配度计算+过滤 ->start
				List<PrepProductPropsInfo> prepProductPropsInfoList = this.getPrepProductPropsInfo(
						baseProductPropsList2, baseProductPropsList, categoryCode, isSaveAll);
				// 属性匹配度计算+过滤 ->end
				
				// 匹配产品->该产品的匹配数据 ->start
				List<PrepProductPropsInfo> prepProductPropsInfoListMatch = this.getPrepProductPropsInfo(
						baseProductPropsList, baseProductPropsList2, categoryCode, isSaveAll);
				prepProductPropsInfoList.addAll(prepProductPropsInfoListMatch);
				// 匹配产品->该产品的匹配数据 ->end
				
				// 白膜产品->该产品的匹配数据 ->start
				List<BaseProductProps> baimoBaseProductPropsList = new ArrayList<BaseProductProps>();
				// 取该分类下的白膜
				List<Integer> baimoProductIdList = baseProductService.getPreprocessBaimoProductIdByCategoryCode(categoryCode);
				for(Integer productId : baimoProductIdList){
					BaseProductProps baseProductPropsbaimo = this.getBaseProductPropsByProductId(productId);
					if(!isSaveSort){
						// 不保存排序属性数据的话.过滤属性为0的产品匹配数据
						if(baseProductPropsbaimo.getProductFilterPropList() != null && baseProductPropsbaimo.getProductFilterPropList().size() == 0){
							continue;
						}
					}
					baimoBaseProductPropsList.add(baseProductPropsbaimo);
				}
				List<PrepProductPropsInfo> baimoPrepProductPropsInfoList = 
						this.getPrepProductPropsInfo(baimoBaseProductPropsList, baseProductPropsList2, categoryCode, true);
				prepProductPropsInfoList.addAll(baimoPrepProductPropsInfoList);
			
				// 白膜产品->该产品的匹配数据 ->end
				this.batchSave(prepProductPropsInfoList);
			}
		}
	}

	private BaseProductProps getBaseProductPropsByProductId(Integer productId) {
		List<ProductPropsSimple> productPropsSimpleList = baseProductService.getProductPropsSimpleByProductId(productId);
		List<ProductPropsSimple> productOrderPropList = new ArrayList<ProductPropsSimple>();
		List<ProductPropsSimple> productFilterPropList = new ArrayList<ProductPropsSimple>();
		for(ProductPropsSimple productPropsSimple : productPropsSimpleList){
			if(productPropsSimple.getIsSort() == 0){
				// 排序属性
				productOrderPropList.add(productPropsSimple);
			}else{
				// 过滤属性
				productFilterPropList.add(productPropsSimple);
			}
		}
		BaseProductProps baseProductProps = new BaseProductProps(
				productId, false, productOrderPropList, productFilterPropList);
		return baseProductProps;
	}
	
	@Override
	public void deleteByProductId(Integer productIdItem) {
		prepProductPropsInfoMapper.deleteByProductId(productIdItem);
	}
	
	private void deleteByMatchProductId(Integer productId) {
		prepProductPropsInfoMapper.deleteByMatchProductId(productId);
	}
	
	/**
	 * 匹配产品获得匹配度信息
	 * @author huangsongbo
	 * @param baseProductPropsList 选中的产品
	 * @param isSaveAll 是否保存匹配度为0的数据
	 * @param baseProductPropsListBeMatched 需要被匹配的产品
	 * @return
	 */
	public List<PrepProductPropsInfo> getPrepProductPropsInfo(List<BaseProductProps> baseProductPropsList,
			List<BaseProductProps> baseProductPropsListBeMatched, String categoryCode, boolean isSaveAll) {
		List<PrepProductPropsInfo> list = new ArrayList<PrepProductPropsInfo>();
		int i = 0;
		for(BaseProductProps baseProductProps : baseProductPropsList){
			for(BaseProductProps baseProductPropsBeMatched : baseProductPropsListBeMatched){
				i ++;
				PrepProductPropsInfo prepProductPropsInfo = null;
				// 取出过滤属性
				List<ProductPropsSimple> productFilterPropList = baseProductProps.getProductFilterPropList();
				if(productFilterPropList == null || productFilterPropList.size() == 0){
					// 无需匹配过滤属性
					Integer matchValue = this.getMatchValue(
							baseProductProps.getProductOrderPropList(), baseProductPropsBeMatched.getProductOrderPropList());
					if(matchValue.equals(new Integer(0))&&!isSaveAll){
						
					}else{
						prepProductPropsInfo = new PrepProductPropsInfo(
								baseProductProps.getProductId(), baseProductPropsBeMatched.getProductId(), matchValue, categoryCode);
					}
				}else{
					// 匹配过滤属性
					boolean isMatched = this.isMatched(
							baseProductProps.getProductFilterPropList(), baseProductPropsBeMatched.getProductFilterPropList());
					if(isMatched){
						Integer matchValue = this.getMatchValue(
								baseProductProps.getProductOrderPropList(), baseProductPropsBeMatched.getProductOrderPropList());
						/*if(matchValue.equals(new Integer(0))&&!isSaveAll){
							
						}else{*/
							prepProductPropsInfo = new PrepProductPropsInfo(
									baseProductProps.getProductId(), baseProductPropsBeMatched.getProductId(), matchValue, categoryCode);
						/*}*/
					}
				}
				if(prepProductPropsInfo != null){
					list.add(prepProductPropsInfo);
					if(i % 10000 == 0){
						logger.warn("------已处理完" + i + "条属性");
					}
					if(i % 1000000 == 0){
						this.batchSave(list);
						list.clear();
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public void batchSave(List<PrepProductPropsInfo> totalList) {
		if(totalList == null || totalList.size() == 0){
			return;
		}
		Integer size = totalList.size();
		Integer start = 0;
		Integer limit = 10000;
		List<PrepProductPropsInfo> list = new ArrayList<PrepProductPropsInfo>();
		do{
			list = totalList.subList(start, (start += limit) > size ? size : start);
			prepProductPropsInfoMapper.batchSave(list);
			list = new ArrayList<PrepProductPropsInfo>();
			logger.warn("------已插入" + start + "条数据");
			logger.warn("------共" + totalList.size() + "条数据");
		}while(start < size);
	}
	
	private Integer getMatchValue(List<ProductPropsSimple> productOrderPropList,
			List<ProductPropsSimple> productOrderPropList2) {
		Integer matchValue = new Integer(0);
		// 得到分数map(key+"/"+value:分数)
		Map<String, Integer> scoreMap = this.getScoreMap(productOrderPropList);
		if(scoreMap == null || scoreMap.size() == 0){
			return new Integer(0);
		}
		for(ProductPropsSimple productPropsSimple : productOrderPropList2){
			String mapKey = productPropsSimple.getKey() + "/" + productPropsSimple.getValue();
			if(scoreMap.containsKey(mapKey)){
				matchValue += scoreMap.get(mapKey);
			}
		}
		return matchValue;
	}
	
	/**
	 * 判断是否符合过滤属性规则
	 * @param productFilterPropList
	 * @param productFilterPropList2
	 * @return
	 */
	private boolean isMatched(List<ProductPropsSimple> productFilterPropList,
			List<ProductPropsSimple> productFilterPropList2) {
		boolean flag = true;
		Map<String, Integer> map = this.getScoreMap(productFilterPropList);
		Map<String, Integer> map2 = this.getScoreMap(productFilterPropList2);
		for(String mapKey : map.keySet()){
			if(!map2.containsKey(mapKey)){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 得到分数map(key+"/"+value:分数)
	 * @author huangsongbo
	 * @param productOrderPropList
	 * @return
	 */
	private Map<String, Integer> getScoreMap(List<ProductPropsSimple> productOrderPropList) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 排序(根据sortValue)
		Comparator<ProductPropsSimple> comparator = new Comparator<ProductPropsSimple>() {
			public int compare(ProductPropsSimple productPropsSimple1, ProductPropsSimple productPropsSimple2) {
				return productPropsSimple2.getSortValue() - productPropsSimple1.getSortValue();
			}
		};
		Collections.sort(productOrderPropList, comparator);
		Integer i = new Integer(1);
		for(ProductPropsSimple productPropsSimple : productOrderPropList){
			map.put(productPropsSimple.getKey() + "/" + productPropsSimple.getValue(), i);
			i *= 10;
		}
		return map;
	}
	

	@Override
	public void noTranUpdateByProductStatusList(List<Integer> productStatusList) {
		
		if(productStatusList == null || productStatusList.size() == 0){
			return;
		}
		
		// 分批处理(根据分类)(同时查出属性) ->start
		
		// 删除预处理属性表中对应数据:productId = 要处理的产品id 或者 match_product_id = 要处理的产品id ->start
		logger.warn("------正在删除需要更新的产品对应的旧的预处理数据");
		List<Integer> productIdList = baseProductService.findIdListByStatus(productStatusList,0);
		if(productIdList == null || productIdList.size() == 0){
			return;
		}
		this.deleteByProductIdList(productIdList);
		this.deleteByMatchProductIdList(productIdList);
		/*int i = 1;
		for(Integer productId : productIdList){
			logger.warn("------删除产品:" + i);
			this.deleteByProductId(productId);
			this.deleteByMatchProductId(productId);
			i ++;
		}*/
		logger.warn("------删除完毕");
		// 删除预处理属性表中对应数据:productId = 要处理的产品id 或者 match_product_id = 要处理的产品id ->end
		
		// 得到要处理数据的distinct分类(大类value) ->start
		List<Integer> productTypeValueList = baseProductService.getProductTypeValueByStatus(productStatusList);
		// 得到要处理数据的distinct分类(大类value) ->end
		
		// 遍历分类分批处理预处理属性数据 并保存 ->start
		logger.warn("------正在增量预处理属性匹配数据");
		
		for(Integer productTypeValue : productTypeValueList){
			
			// 得到数据字典信息
			SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("productType", productTypeValue);
			if(sysDictionary == null){
				logger.error("------数据字典未找到,type = productType" + "value = " + productTypeValue);
				continue;
			}
			
			// isSaveMatchIsZero:是否储存匹配度为0的产品
			// 硬装isSaveAll = false,软装isSaveAll = true(为了防止新增产品,找不到经过过滤属性过滤的产品)
			String[] categoryCodes = new String[]{"dim","qiangm","tianh","meng"};
			List<String> categoryCodeList = Arrays.asList(categoryCodes);
			boolean isSaveMatchIsZero = false;
			if(categoryCodeList.indexOf(sysDictionary.getValuekey()) != -1){
				//硬装(不可新增)
				
			}else{
				isSaveMatchIsZero = true;
			}
			
			if(productTypeValue == null){
				continue;
			}
			logger.warn("------开始处理productTypeValue = " + productTypeValue + "的产品数据");
			
			logger.warn("------正在取出待处理产品的属性信息:productTypeValue = " + productTypeValue);
			
			// 得到要处理的产品属性数据(根据大类value) ->start
			Map<getBaseProductPropListMapEnum, List<BaseProductProps>> baseProductPropListMap = 
					baseProductService.getBaseProductPropListByProudctStatusListAndProudctTypeValue(productStatusList, productTypeValue);
			
			List<BaseProductProps> baseProductPropList = baseProductPropListMap.get(getBaseProductPropListMapEnum.baseProductPropList);
			List<BaseProductProps> baimoBaseProductPropList = baseProductPropListMap.get(getBaseProductPropListMapEnum.baimoBaseProductPropList);
			
			if((baseProductPropList == null || baseProductPropList.size() == 0) 
					&& (baimoBaseProductPropList == null || baimoBaseProductPropList.size() == 0)){
				continue;
			}
			// 得到要处理的产品属性数据(根据大类value) ->end
			
			// 得到需要匹配的产品属性数据(大类value下所有的产品) ->start
			List<Integer> matchProductStatusList = new ArrayList<Integer>();
			matchProductStatusList.addAll(productStatusList);
			matchProductStatusList.remove(ProductStatuCode.RELEASEING);
			matchProductStatusList.add(ProductStatuCode.RELEASEING);
			matchProductStatusList.remove(ProductStatuCode.HAS_BEEN_RELEASE);
			matchProductStatusList.add(ProductStatuCode.HAS_BEEN_RELEASE);
			Map<getBaseProductPropListMapEnum, List<BaseProductProps>> matchBaseProductPropListMap = 
					baseProductService.getBaseProductPropListByProudctStatusListAndProudctTypeValue(matchProductStatusList, productTypeValue);
			
			List<BaseProductProps> allBaseProductPropList = matchBaseProductPropListMap.get(getBaseProductPropListMapEnum.baseProductPropList);
			List<BaseProductProps> allBaimoBaseProductPropList = matchBaseProductPropListMap.get(getBaseProductPropListMapEnum.baimoBaseProductPropList);
			// 得到需要匹配的产品属性数据(大类value下所有的产品) ->end
			
			logger.warn("------待处理产品的属性信息整理完毕:productTypeValue = " + productTypeValue);
			
			// 匹配数据并保存 ->start
			logger.warn("------正在匹配属性/保存属性匹配度");
			
			// 处理白膜 ->start
			if(baimoBaseProductPropList != null && baimoBaseProductPropList.size() > 0){
				// 白膜的话,只要白膜去匹配每个普通产品
				List<PrepProductPropsInfo> prepProductPropsInfoList = 
						this.getPrepProductPropsInfo(baimoBaseProductPropList, allBaseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				this.batchSave(prepProductPropsInfoList);
			}
			// 处理白膜 ->end
			
			// 处理普通产品 ->start
			if(baseProductPropList != null && baseProductPropList.size() > 0){
				
				// 普通产品,需要匹配全部产品,然后被全部产品匹配一次,最后被所有白膜匹配一次 ->start
				List<PrepProductPropsInfo> prepProductPropsInfoList = 
						this.getPrepProductPropsInfo(baseProductPropList, allBaseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				
				this.batchSave(prepProductPropsInfoList);
				
				// 为了不重复匹配,allBaseProductPropList与baseProductPropList取差集
				allBaseProductPropList.removeAll(baseProductPropList);
				
				List<PrepProductPropsInfo> prepProductPropsInfoList2 = 
						this.getPrepProductPropsInfo(allBaseProductPropList, baseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				
				this.batchSave(prepProductPropsInfoList2);
				
				List<PrepProductPropsInfo> prepProductPropsInfoList3 = 
						this.getPrepProductPropsInfo(allBaimoBaseProductPropList, baseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				
				this.batchSave(prepProductPropsInfoList3);
				// 普通产品,需要匹配全部产品,然后被全部产品匹配一次,最后被白膜匹配一次 ->end
				
			}
			// 处理普通产品 ->end
			
			logger.warn("------匹配属性/保存属性匹配度完毕");
			// 匹配数据并保存 ->end
			
			logger.warn("------productTypeValue = " + productTypeValue + "的产品数据匹配保存完毕");
			
		}
		logger.warn("------增量预处理属性匹配数据完毕");
		// 遍历分类分批处理预处理属性数据 并保存 ->end
		
		// 分批处理(根据分类)(同时查出属性) ->end
		
	}

	@Override
	public void deleteByMatchProductIdList(List<Integer> productIdList) {
		Integer size = productIdList.size();
		Integer start = 0;
		Integer limit = 100;
		List<Integer> list = new ArrayList<Integer>();
		do{
			logger.warn("------正在删除matchProductId参数符合条件的数据:" + ((start * 100)/size) + "%");
			list = productIdList.subList(start, (start += limit) > size ? size : start);
			/*prepProductPropsInfoMapper.deleteByMatchProductIdList(list);*/
			List<Integer> idList = prepProductPropsInfoMapper.getIdListByMatchProductIdList(list);
			this.deleteByIdListBatch(idList);
		}while(start < size);
		logger.warn("------正在删除matchProductId参数符合条件的数据:100%");
	}

	@Override
	public void deleteByProductIdList(List<Integer> productIdList) {
		Integer size = productIdList.size();
		Integer start = 0;
		Integer limit = 100;
		List<Integer> list = new ArrayList<Integer>();
		do{
			logger.warn("------正在删除productId参数符合条件的数据:" + ((start * 100)/size) + "%");
			list = productIdList.subList(start, (start += limit) > size ? size : start);
			/*prepProductPropsInfoMapper.deleteByProductIdList(list);*/
			List<Integer> idList = prepProductPropsInfoMapper.getIdListByProductIdList(list);
			this.deleteByIdListBatch(idList);
		}while(start < size);
		logger.warn("------正在删除productId参数符合条件的数据:100%");
	}

	/**
	 * 根据idList批量删除(100条100条删除)
	 * @param idList
	 */
	private void deleteByIdListBatch(List<Integer> idList) {
		Integer size = idList.size();
		Integer start = 0;
		Integer limit = 100;
		List<Integer> list = new ArrayList<Integer>();
		do{
			list = idList.subList(start, (start += limit) > size ? size : start);
			this.deleteByIdList(list);
		}while(start < size);
	}

	private void deleteByIdList(List<Integer> idList) {
		if(idList == null || idList.size() == 0){
			return;
		}
		prepProductPropsInfoMapper.deleteByIdList(idList);
	}
	
	public List<PrepProductPropsInfo> getAllPrepProductPropsList(List<Integer> productStatusList) {
		List<PrepProductPropsInfo> allPrepProductPropsList = new ArrayList<PrepProductPropsInfo>();
		if(productStatusList == null || productStatusList.size() == 0){
			return allPrepProductPropsList;
		}

		List<Integer> productIdList = baseProductMapper.getProductListByStatus(ProductStatuCode.RELEASEING);
		if(productIdList == null || productIdList.size() == 0){
			return allPrepProductPropsList;
		}
		
		this.deleteByProductIdList(productIdList);
		this.deleteByMatchProductIdList(productIdList);
		
		logger.error("------删除完毕");
		// 删除预处理属性表中对应数据:productId = 要处理的产品id 或者 match_product_id = 要处理的产品id ->end
		
		// 得到要处理数据的distinct分类(大类value) ->start
		List<Integer> productTypeValueList = baseProductService.getProductTypeValueByStatus(productStatusList);
		// 得到要处理数据的distinct分类(大类value) ->end
		
		// 遍历分类分批处理预处理属性数据 并保存 ->start
		logger.error("------正在增量预处理属性匹配数据");
		
		for(Integer productTypeValue : productTypeValueList){
			// 得到数据字典信息
			SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("productType", productTypeValue);
			if(sysDictionary == null){
				logger.error("------数据字典未找到,type = productType" + "value = " + productTypeValue);
				continue;
			}
			String[] categoryCodes = new String[]{"dim","qiangm","tianh","meng"};
			List<String> categoryCodeList = Arrays.asList(categoryCodes);
			boolean isSaveMatchIsZero = false;
			if(categoryCodeList.indexOf(sysDictionary.getValuekey()) != -1){
				//硬装(不可新增)
				
			}else{
				isSaveMatchIsZero = true;
			}
			
			if(productTypeValue == null){
				continue;
			}
			// 得到要处理的产品属性数据(根据大类value) ->start
			Map<getBaseProductPropListMapEnum, List<BaseProductProps>> baseProductPropListMap = 
					baseProductService.getBaseProductPropListByProudctStatusListAndProudctTypeValue(productStatusList, productTypeValue);
			
			List<BaseProductProps> baseProductPropList = baseProductPropListMap.get(getBaseProductPropListMapEnum.baseProductPropList);
			List<BaseProductProps> baimoBaseProductPropList = baseProductPropListMap.get(getBaseProductPropListMapEnum.baimoBaseProductPropList);
			
			if((baseProductPropList == null || baseProductPropList.size() == 0) 
					&& (baimoBaseProductPropList == null || baimoBaseProductPropList.size() == 0)){
				continue;
			}
			// 得到要处理的产品属性数据(根据大类value) ->end
			
			// 得到需要匹配的产品属性数据(大类value下所有的产品) ->start
			List<Integer> matchProductStatusList = new ArrayList<Integer>();
			matchProductStatusList.addAll(productStatusList);
			matchProductStatusList.remove(ProductStatuCode.RELEASEING);
			matchProductStatusList.add(ProductStatuCode.RELEASEING);
			matchProductStatusList.remove(ProductStatuCode.HAS_BEEN_RELEASE);
			matchProductStatusList.add(ProductStatuCode.HAS_BEEN_RELEASE);
			Map<getBaseProductPropListMapEnum, List<BaseProductProps>> matchBaseProductPropListMap = 
					baseProductService.getBaseProductPropListByProudctStatusListAndProudctTypeValue(matchProductStatusList, productTypeValue);
			
			List<BaseProductProps> allBaseProductPropList = matchBaseProductPropListMap.get(getBaseProductPropListMapEnum.baseProductPropList);
			List<BaseProductProps> allBaimoBaseProductPropList = matchBaseProductPropListMap.get(getBaseProductPropListMapEnum.baimoBaseProductPropList);
			// 得到需要匹配的产品属性数据(大类value下所有的产品) ->end
			// 处理白膜 ->start
			if(baimoBaseProductPropList != null && baimoBaseProductPropList.size() > 0){
				// 白膜的话,只要白膜去匹配每个普通产品
				List<PrepProductPropsInfo> prepProductPropsInfoList = 
						this.getPrepProductPropsInfo(baimoBaseProductPropList, allBaseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				allPrepProductPropsList.addAll(prepProductPropsInfoList);
			}
			// 处理白膜 ->end
			
			// 处理普通产品 ->start
			if(baseProductPropList != null && baseProductPropList.size() > 0){
				// 普通产品,需要匹配全部产品,然后被全部产品匹配一次,最后被所有白膜匹配一次 ->start
				List<PrepProductPropsInfo> prepProductPropsInfoList = 
						this.getPrepProductPropsInfo(baseProductPropList, allBaseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				allPrepProductPropsList.addAll(prepProductPropsInfoList);
				
				
				List<PrepProductPropsInfo> prepProductPropsInfoList2 = 
						this.getPrepProductPropsInfo(allBaseProductPropList, baseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				allPrepProductPropsList.addAll(prepProductPropsInfoList2);
				
				List<PrepProductPropsInfo> prepProductPropsInfoList3 = 
						this.getPrepProductPropsInfo(allBaimoBaseProductPropList, baseProductPropList, sysDictionary.getValuekey(), isSaveMatchIsZero);
				allPrepProductPropsList.addAll(prepProductPropsInfoList3);
				
			}
		}
		return allPrepProductPropsList;
	}
	
}
