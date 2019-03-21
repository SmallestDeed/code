/**
 * 
 */
package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.service.DesignPlanService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.controller.web.WebGroupProductControllerV2;
import com.nork.product.dao.GroupProductMapper;
import com.nork.product.dao.ProductGroupDao;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProductAllTypeModel;
import com.nork.product.model.ProductGroupBaseInfoModel;
import com.nork.product.model.ProductInfoInGroupModel;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductServiceV2;
import com.nork.product.service.ProductAttributeService;
import com.nork.system.dao.SysDictonaryDao;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictBigAndSmallTypeInfoModel;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;


/**
 * 
 * GroupProductServiceV2 实现类, 主要用于特定业务逻辑的重构
 * @author louxinhua
 *
 */
public class GroupProductServiceV2Impl implements GroupProductServiceV2 {

	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * map 的 key
	 */
	private static final String BSP_TYPE_VALUE_MAP_KEY = "dictValueList";
	
	/**
	 * 组合收藏状态
	 * @author louxinhua
	 *
	 */
	private enum GROUP_COLLECTED_STATUS {
		not_collect,  	// 没有收藏
		collected		// 已经收藏	
	}
	
	/**
	 * 主产品的标示枚举
	 * @author louxinhua
	 *
	 */
	private enum GROUP_MAIN_PRODUCT_FLAG {
		not_main, // 不是主产品
		main	// 是主产品
	}
	
	
	private static final String PRODUCT_CODE_AND_MARK_MAP_KEY_typemark =  "typemark";
	private static final String PRODUCT_CODE_AND_MARK_MAP_KEY_small_typeark =  "small_typeark";
	private static final String PRODUCT_CODE_AND_MARK_MAP_KEY_productcode =  "productcode";
	private static final String PRODUCT_CODE_AND_MARK_MAP_KEY_material_pic_ids = "material_pic_ids";
	private static final String PRODUCT_CODE_AND_MARK_MAP_KEY_split_textures_info = "split_textures_info";
	
	
	
	@Autowired
	private GroupProductMapper groupProductMapper; // 产品组 mapper
	
	@Autowired
	private ProductGroupDao productGroupDao; // 产品组 dao， 里面只有联合查询，没有增、删、改。
	
	@Autowired
	private BaseProductService baseProductService;
	
	
	@Autowired
	private DesignRulesService designRulesService;
	
	@Autowired
	private SysDictonaryDao sysDictonaryDao;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private DesignPlanService designPlanService;

	
	
	
	/* (non-Javadoc)
	 * @see com.nork.product.service.GroupProductServiceV2#getGroupCountByProduct(com.nork.product.model.search.GroupProductSearch)
	 */
	@Override
	public Integer getGroupCountByProduct(GroupProductSearch search) {
		Integer count = groupProductMapper.getGroupCountByProduct(search);
		return count;
	}

	/**
	 * 根据条件配置GroupProductSearch
	 * @param groupSearch
	 * @param productTypeValue
	 * @param smallTypeValue
	 * @return
	 */
	public GroupProductSearch configSearchFor(GroupProductSearch groupSearch, String  productTypeValue, String  smallTypeValue) {

		groupSearch.setStructureState(WebGroupProductControllerV2.GROUP_SEARCH_TYPE.normal_group.ordinal());	/*结构组合没有数据就查询普通组合*/
		if(productTypeValue!=null&&!"".equals(productTypeValue)&&!"0".equals(productTypeValue)){
			groupSearch.setProductTypeValue(productTypeValue);
			if( smallTypeValue!=null&&!"".equals(smallTypeValue)&&!"0".equals(smallTypeValue) ){

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("type", "productType");
				paramMap.put("bValue", Utils.getIntValue(productTypeValue));
				paramMap.put("sValue", Integer.valueOf(smallTypeValue));
				List<SysDictBigAndSmallTypeInfoModel> modelList = sysDictonaryDao.selectBigAndSmallTypeByTypeAndValue(paramMap);
				
				if ( modelList != null && !modelList.isEmpty() ) {
					SysDictBigAndSmallTypeInfoModel infoModel = modelList.get(0);
					String smallKey = "";
					if("2".equals(infoModel.getSmallAtt4()) && "baimo".equals(infoModel.getSmallAtt3())){
						smallKey = Utils.getTypeValueKey(infoModel.getSmallValueKey());
					}else{
						smallKey = infoModel.getSmallValueKey();
					}

					// FIXME:这里是否可以优化？
					try{
						SysDictionary sd  = sysDictionaryService.findOneByValueKeyInCache(smallKey);
						logger.info("sd.getValue()" + sd.getValue());
						groupSearch.setSmallTypeValue(sd.getValue().toString());
					}catch(Exception e){
						e.printStackTrace();
					}

				}
				else {}
			}
			else {}
		}
		else {}
		
		
		
		return groupSearch;
		
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.nork.product.service.GroupProductServiceV2#getGroupListByProduct(com.nork.product.model.search.GroupProductSearch)
	 */
	@Override
	public List<SearchProductGroupResult> getGroupListByProduct(GroupProductSearch search, Integer userID, Integer structureId,Integer usertype,String versionType) {
		
		// 首先获取列表，包含了 res_pic, res_file 的值
		List<ProductGroupBaseInfoModel> groupList = null;
		boolean isStructure = false;
		if ( structureId != null && structureId > 0 ) {
			isStructure = true;
			//groupList = productGroupDao.getGroupListByStructureIdV2(structureId);
			groupList = productGroupDao.getGroupListByStructureIdAndStatus(structureId, usertype, search.getStart(), search.getLimit(), search.getBrandIdList(), versionType, search.getGroupType());
		}
		else {
			isStructure = false;
			groupList = productGroupDao.getGroupListByProductV3(search);
		}
		
		List<SearchProductGroupResult> resultList = new ArrayList<>();
		
		if ( groupList != null && groupList.size() > 0 ) {
			
			List<Integer> groupIDSList = new ArrayList<>();
			
			// 遍历组列表
			for (ProductGroupBaseInfoModel baseInfo : groupList) {
				
				groupIDSList.add(baseInfo.getGroupID());
				
				SearchProductGroupResult productGroup = new SearchProductGroupResult();
				
				// 目前数据库 location字段， 分为 txt 文件路径、json 串。
				String filePath = baseInfo.getFilePath();
				if ( filePath != null && !filePath.trim().isEmpty() ) { // 为什么不用 StringUtils？因为 Utils 太多了，没有注释
					// 说明 file_path 有值
					String url =Tools.getRootPath(filePath,  "") + filePath;
					String text = FileUploadUtils.getFileContext(url);
					productGroup.setGroupConfig(text);
					if (isStructure) {
						productGroup.setStructureConfig(text);
					}
					else {}
					
				}
				else {
					// 直接用 location json 串
					productGroup.setGroupConfig(baseInfo.getLocation());
					if (isStructure) {
						productGroup.setStructureConfig(baseInfo.getLocation());
					}
					else {}
				}
				productGroup.setGroupPrice(baseInfo.getGroupPrice());
				productGroup.setSalePrice(baseInfo.getGroupPrice());
				productGroup.setGroupId(baseInfo.getGroupID());
				productGroup.setGroupCode(baseInfo.getGroupCode());
				productGroup.setGroupName(baseInfo.getGroupName());
				productGroup.setProductName(baseInfo.getGroupName());
				
				// 如果是 结构 。 2016-12-29 added
				if ( isStructure ) {
					productGroup.setStructureCode(baseInfo.getStructureCode());
					productGroup.setStructureName(baseInfo.getStructureName());
					if ( baseInfo.getProductStructureId() != null ) {
						productGroup.setProductStructureId(baseInfo.getProductStructureId().intValue());
					}
					else {
						productGroup.setProductStructureId(0);
					}
					
				}
				else {}
				
				
				// 组装产品组封面图片
				Integer picId = baseInfo.getPicID();
				if( picId != null && picId > 0 ){
					productGroup.setPicPath(baseInfo.getPicPath());
				}
				else {
					// FIXME:如果没有图片则显示默认图片
				}
				
				// 放到 resultList 里面
				resultList.add(productGroup);
			}
			
			
			
			// 根据 userid 和 productgroupid 获取收藏
			Map<String, Object> params = new HashMap<>();
			params.put("userID", userID);
			params.put("groupIDList", groupIDSList);
//			List<Map<String, Integer>> collectedGroupList = this.productGroupDao.getProductGroupUserCollectedUsingUnion(params);
			List<Map<String, Integer>> collectedGroupList = this.getUserCollectedProductGroupByIDS(userID, groupIDSList);
			
			if ( collectedGroupList != null && collectedGroupList.size() > 0 ) {
				// 说明用户对产品组有收藏，进行遍历分析
				// 首先把collectedGroupList变成一个 map, (实际上 set 也可以)
				Map<Integer, String> tempMap = new HashMap<>();
				for (Map<String, Integer> collectedGroup : collectedGroupList) {
					tempMap.put( (Integer)collectedGroup.get("groupID") , "");
				}
				
				// 遍历 resultList ，设置用户是否已经收藏
				for (SearchProductGroupResult groupResult : resultList) {
					Integer groupID = groupResult.getGroupId();
					if ( tempMap.get(groupID) != null ) {
						// 收藏了
						groupResult.setCollectState(GROUP_COLLECTED_STATUS.collected.ordinal());
					}
					else {
						// 木有收藏
						groupResult.setCollectState(GROUP_COLLECTED_STATUS.not_collect.ordinal());						
					}
				}
			}
			else {}
			
		}
		else {}
		
		
		
		return resultList;
	}
	
	
	
	/**
	 * 查询用户在groupIDList中收藏了哪些产品组
	 * @param userID
	 * @param groupIDList
	 * @return map list. map 里面两个 key， 一个 userID， 一个 groupID
	 */
	public List<Map<String, Integer>> getUserCollectedProductGroupByIDS(Integer userID, List<Integer> groupIDList) {
		
		
		if ( userID < 1 || ( groupIDList == null || groupIDList.size() < 1 ) ) {
			return null;
		}
		else {}
		
		Map<String, Object> params = new HashMap<>();
		params.put("userID", userID);
		params.put("groupIDList", groupIDList);
		List<Map<String, Integer>> collectedGroupList = this.productGroupDao.getProductGroupUserCollectedUsingUnion(params);
		
		
		return collectedGroupList;
	}
	
	
	/**
	 * 把记录分成几次查询的底数
	 */
	private static final int LOOP_LIMIT_COUNT = 10;
	
	/**
	 * 把产品属性分成几次查询的底数
	 */
	private static final int LOOP_LIMIT_PRODUCT_ATTR_COUNT = 5;
	
	/**
	 * 返回产品组里面的产品相信信息，比如长宽高、产品大小类型等等
	 * @return
	 */
	public Map<Integer, List<SearchProductGroupDetail>> getGroupProductDetailsByGroupIDList(List<SearchProductGroupResult> groupList,
			String mediaType, String brandIds, Integer spaceCommonId) {
		
		
//		this.productGroupDao.getProductInfoInTheGroupByGroupIDAndModelIDType(map)
		
		List<ProductInfoInGroupModel> allProductInfoList = new ArrayList<>();
		Map<Integer, Map<String, String>> productCodeAndMarkMap = new HashMap<>(); // 用来缓存1、材质，是可以替换的还是不可以替换的。2、product code 3、typemark 4、small_typemark
		
		// 进行10条、10条的查询
		int groupCount = groupList.size();
		int bigCount = groupCount/LOOP_LIMIT_COUNT;
		int sCount = groupCount%LOOP_LIMIT_COUNT;
		for (int index = 0; index < bigCount; index++) {
			
			List<Map<String, Object>> queryParamsList = new ArrayList<>();
			int limitCount = (index+1)*LOOP_LIMIT_COUNT; // 10、20、30
			int sIndex = index*LOOP_LIMIT_COUNT; // 0、10、20
			for (; sIndex < limitCount; sIndex++ ) {
				SearchProductGroupResult spgr = groupList.get(sIndex);
				Map<String, Object> map = new HashMap<>();
				map.put("groupID", spgr.getGroupId());
				String modelColumnName = this.getU3dModelColumnName(mediaType);
				map.put("idColumnName", modelColumnName);
				if ( spgr.getProductStructureId() != null ) {
					map.put("productStructureId", spgr.getProductStructureId());
				}
				else {
					map.put("productStructureId", 0);
				}
				queryParamsList.add(map);
			}
			
			// FIXME:调试需要 delete
			// 获取10个产品组中每个产品的信息
			List<ProductInfoInGroupModel> infoModelList = this.productGroupDao.getProductInfoInTheGroupByGroupIDAndModelIDType(queryParamsList);
			allProductInfoList.addAll(infoModelList);
		}
		
		
		// 来，这是剩下的
		if ( sCount > 0 ) {
			List<Map<String, Object>> queryParamsList = new ArrayList<>();
			for (int index = 0; index < sCount; index++) {
				int remainIndex = index + bigCount*LOOP_LIMIT_COUNT;
				SearchProductGroupResult spgr = groupList.get(remainIndex);
				Map<String, Object> map = new HashMap<>();
				map.put("groupID", spgr.getGroupId());
				String modelColumnName = this.getU3dModelColumnName(mediaType);
				map.put("idColumnName", modelColumnName);
				if ( spgr.getProductStructureId() != null ) {
					map.put("productStructureId", spgr.getProductStructureId());
				}
				else {
					map.put("productStructureId", 0);
				}
				queryParamsList.add(map);
			}
			
			// 获取剩下产品组中每个产品的信息
			List<ProductInfoInGroupModel> infoModelList = this.productGroupDao.getProductInfoInTheGroupByGroupIDAndModelIDType(queryParamsList);
			allProductInfoList.addAll(infoModelList);
		}
		else {}
		
		// 遍历 allProductInfoList 进行 details 初始化
		Map<Integer, List<SearchProductGroupDetail>> listMap = new HashMap<>(); // 用来放每个 groupid 对应到的 detail list
		Map<Integer, Integer> groupMainProductMap = new HashMap<>(); // 用来缓存每个 group 的 main product id
		Map<Integer, List<Integer>> bandIDListMap = new HashMap<>(); // 用来缓存每个 group 的 对应到的每个产品 list 的每个 bandID
		Map<Integer, Map<String, List<Map<String, Object>>>> bspTypeValueMap = new HashMap<>(); // 用来缓存每个产品组里面每个产品的一些值，用于后面查询
		// 由于这个常常用到，单独拿出来
		Map<Integer, List<Integer>> groupProductIDListMap = new HashMap<>(); // 每个产品组合的product id list 
		
		// 用 groupid 来获取 产品组合 中主产品的 brandName(品牌名)
		Map<Integer, String> groupMainProductBrandNameMap = new HashMap<>();
		// 用来缓存产品组里面每个产品价格的总和
		Map<Integer, Double> groupPriceMap = new HashMap<>();

		
		for (ProductInfoInGroupModel productInfoInGroupModel : allProductInfoList) {
			
			Map<String, String> codeAndMarkMap = new HashMap<>();
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_productcode, productInfoInGroupModel.getProductCode());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_typemark, productInfoInGroupModel.getProductTypeMark());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_small_typeark, productInfoInGroupModel.getProductSmallTypeMark());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_split_textures_info, productInfoInGroupModel.getSplitTexturesInfo());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_material_pic_ids, productInfoInGroupModel.getMaterialPicIds());
			productCodeAndMarkMap.put(productInfoInGroupModel.getProductID(), codeAndMarkMap);
			
			Integer groupID = productInfoInGroupModel.getGroupID();
			List<SearchProductGroupDetail> detailList = null;
			List<Integer> bandIDList = null;
			List<Integer> productIDList = null;
			Map<String, List<Map<String, Object>>> bspTypeValueListMap = null;
			
			// 初始化判断
			if ( null == listMap.get(groupID)  ) {
				detailList = new ArrayList<>();
				listMap.put(groupID, detailList);
			}
			else {
				detailList = listMap.get(groupID);
			}
			
			if ( null == bandIDListMap.get(groupID)  ) {
				bandIDList = new ArrayList<>();
				bandIDListMap.put(groupID, bandIDList);
			}
			else {
				bandIDList = bandIDListMap.get(groupID);
			}
			
			if ( null == bspTypeValueMap.get(groupID) ) {
				bspTypeValueListMap = new HashMap<String, List<Map<String, Object>>>();
				List<Map<String, Object>> tempList = new ArrayList<>();
				bspTypeValueListMap.put(BSP_TYPE_VALUE_MAP_KEY, tempList);
				bspTypeValueMap.put(groupID, bspTypeValueListMap);
			}
			else {
				bspTypeValueListMap = bspTypeValueMap.get(groupID);
			}
			
			if ( groupProductIDListMap.get(groupID) == null ) {
				productIDList = new ArrayList<>();
				groupProductIDListMap.put(groupID, productIDList);
			}
			else {
				productIDList = groupProductIDListMap.get(groupID);
			}
			
			// 价格
			if ( groupPriceMap.get(groupID) == null ) {
				groupPriceMap.put(groupID, 0.0);
			}
			else {}
			groupPriceMap.put(groupID, groupPriceMap.get(groupID) + productInfoInGroupModel.getSalePrice()); // 价格加上去
			
			productIDList.add(productInfoInGroupModel.getProductID()); // 把产品 id 放到 list 里面
			
			// 这个用在后面获取产品组中各个产品的大类、小类、父类
			List<Map<String, Object>> typeValueMapList = bspTypeValueListMap.get(BSP_TYPE_VALUE_MAP_KEY);
			Map<String, Object> typeValueMap = new HashMap<>();
			typeValueMap.put("bValue", StringUtils.trimToEmpty(productInfoInGroupModel.getProductTypeValue()));
			typeValueMap.put("sValue", StringUtils.trimToEmpty(productInfoInGroupModel.getProductSmallTypeValue()));
			typeValueMap.put("pValue", StringUtils.trimToEmpty(productInfoInGroupModel.getParentProductTypeValue()));
			typeValueMap.put("productID", productInfoInGroupModel.getProductID());
			typeValueMapList.add(typeValueMap);
			
			SearchProductGroupDetail detail = new SearchProductGroupDetail();
			detail.setProductId(productInfoInGroupModel.getProductID());
			detail.setProductGroupId(productInfoInGroupModel.getGroupID());
			detail.setProductCode(StringUtils.trimToEmpty(productInfoInGroupModel.getProductCode()));
			int isMain = productInfoInGroupModel.getIsMain() == null ? 0 : productInfoInGroupModel.getIsMain();
			detail.setIsMainProduct(isMain);
			// 设置主产品的品牌名字到groupMainProductBrandNameMap
			if ( isMain == GROUP_MAIN_PRODUCT_FLAG.main.ordinal() ) {
				groupMainProductBrandNameMap.put(groupID, StringUtils.trimToEmpty(productInfoInGroupModel.getBrandName()));
			}
			else {}
			// 暂存每个产品组的主产品 id
			if ( GROUP_MAIN_PRODUCT_FLAG.main.ordinal() == isMain  ) {
				groupMainProductMap.put(groupID, productInfoInGroupModel.getProductID());
			}
			else {}
			
			// 对每个 产品 detail 进行模型地址的赋值
			if ( null == productInfoInGroupModel.getModelPath() ) {
				detail.setU3dModelPath("");
			}
			else {
				detail.setU3dModelPath( productInfoInGroupModel.getModelPath() );
			}
			BaseProduct baseProduct = baseProductService.get(productInfoInGroupModel.getProductID());
			// 长、高、宽
			detail.setProductWidth(productInfoInGroupModel.getProductWidth());
			detail.setProductHeight(productInfoInGroupModel.getProductHeight());
			detail.setProductLength(productInfoInGroupModel.getProductLength());
			// 最小高度
			detail.setModelMinHeight(productInfoInGroupModel.getMinHeight() == null ? 0: productInfoInGroupModel.getMinHeight());
			// 相机位置和远景相机位置
			detail.setCameraLook(productInfoInGroupModel.getCameraLook());
			detail.setCameraView(productInfoInGroupModel.getCameraView());
			// 结构相关
			if ( productInfoInGroupModel.getProductStructureId() != null ) {
				detail.setProductStructureId(productInfoInGroupModel.getProductStructureId().intValue());
			}
			else {
				detail.setProductStructureId(0);
			}
			
			if ( productInfoInGroupModel.getChartletProductModelId() != null ) {
				detail.setTextureProductModelId(productInfoInGroupModel.getChartletProductModelId());
			}
			else {
				detail.setTextureProductModelId(0);
			}
			
			// 添加品牌id
			Integer tempBandID = productInfoInGroupModel.getBrandID();
			if ( tempBandID != null && tempBandID > 0  ) {
				bandIDList.add(productInfoInGroupModel.getBrandID());
			}
			else {}
			
			
			// parent product id
			detail.setParentProductId(productInfoInGroupModel.getParentID());
			
			detailList.add(detail);
			
			listMap.put(groupID, detailList);
			
			bandIDListMap.put(groupID, bandIDList);
		}
		
		// :从 groupMainProductMap 中，对groupList的每个主产品的 id 进行赋值
		// :对 listMap 进行遍历，处理 “品牌是否被绑定状态” brandState 的逻辑
		for (SearchProductGroupResult groupResult : groupList) {
			Integer groupID = groupResult.getGroupId();
			Integer mainProductID = groupMainProductMap.get(groupID);
			if ( mainProductID != null && mainProductID > 0 ) {
				groupResult.setProductId(mainProductID);
			}
			else {}
			
			int brandState = 0; // 用来判断最后的价格是否为0
			// 进行 brandState 的处理
			List<Integer> list = bandIDListMap.get(groupID); // groupid 对应到的 bandid list
			if ( list != null && !list.isEmpty() ) {
				
				for (Integer integer : list) {
					if ( brandState == 0 ) {
						/*品牌是否被绑定状态*/
						String productBrandId = "," + integer + ",";
						if( StringUtils.isEmpty(brandIds) ){
							brandState=brandState+1;
						}
						else {}
						
						if( (","+brandIds).indexOf(productBrandId)==-1 ){
							brandState=brandState+1;
						}
					}
					else {}
				}
				
			}
			else {}
			
			if ( brandState > 0 ) {
				groupResult.setSalePrice(-1.0); // 为什么设置成 -1.0， 因为后面价格计算要用到
			}
			else {}
		}

		// :对产品大类、小类、父类进行处理, 写到这里我差不多要晕了已经
		for (SearchProductGroupResult groupResult : groupList) {
			
			// 逻辑是对每个 group 中的所有产品进行一次 sql 查询，用 union 做
			Integer groupID = groupResult.getGroupId();
			Map<String, List<Map<String, Object>>> map = bspTypeValueMap.get(groupID);
			List<Map<String, Object>> list = map.get(GroupProductServiceV2Impl.BSP_TYPE_VALUE_MAP_KEY);
			if (  list != null && !list.isEmpty()  ) {
				 List<GroupProductAllTypeModel> typeModelList = this.productGroupDao.getProductBSPTypeValueUsingUnion(list);
				 if ( typeModelList != null && !typeModelList.isEmpty() ) {
					 // list 转成 map
					 Map<Integer, GroupProductAllTypeModel> tempProductTypeMap = new HashMap<>();
					 for (GroupProductAllTypeModel groupProductAllTypeModel : typeModelList) {
						 tempProductTypeMap.put(groupProductAllTypeModel.getProductID(), groupProductAllTypeModel);
					 }
					 List<SearchProductGroupDetail> detailList = listMap.get(groupID);
					 for (SearchProductGroupDetail searchProductGroupDetail : detailList) {
						Integer productID = searchProductGroupDetail.getProductId();
						GroupProductAllTypeModel model = tempProductTypeMap.get(productID);
						if ( model != null ) {
							searchProductGroupDetail.setProductTypeValue( model.getbValue() );
							searchProductGroupDetail.setProductTypeCode( StringUtils.trimToEmpty(model.getbValueKey()) );
							searchProductGroupDetail.setProductTypeName( StringUtils.trimToEmpty(model.getbName()) );
							
							searchProductGroupDetail.setProductSmallTypeCode( StringUtils.trimToEmpty(model.getsValueKey()) );
							searchProductGroupDetail.setProductSmallTypeName( StringUtils.trimToEmpty(model.getsName()) );
							searchProductGroupDetail.setProductSmallTypeValue( model.getsValue() == null ? "" : String.valueOf(model.getsValue()) );
							
							
							String rootType = StringUtils.isEmpty(model.getsAtt1()) ? "2" : StringUtils.trim(model.getsAtt1());
							searchProductGroupDetail.setRootType(rootType);
							
						}
						else {}
					}
				 }
				 else {
					 
				 }
			}
			else {}
			
		}

		// FIXME:需要 黄宋博 进行重构
		// 产品材质重写，需要加入贴图材质逻辑
		for (SearchProductGroupResult groupResult : groupList) {
			Integer groupID = groupResult.getGroupId();
			List<SearchProductGroupDetail> list = listMap.get(groupID); // 改产品组的产品列表
			if ( list != null && !list.isEmpty() ) {
				
				// 普通产品 和 拆分材质产品 分开
				for (SearchProductGroupDetail searchProductGroupDetail : list) {
					Integer productID = searchProductGroupDetail.getProductId();
					Map<String, String> caizhiMap = productCodeAndMarkMap.get(productID);
					
					/*处理拆分材质产品返回默认材质*/
					String splitTexturesInfo = caizhiMap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_split_textures_info);

					
					Integer isSplit=0;
					List<SplitTextureDTO> splitTextureDTOList = null;
					if ( StringUtils.isNotBlank(splitTexturesInfo)) {
						Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(productID, splitTexturesInfo,"choose");
						isSplit=(Integer) map.get("isSplit");
						splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
					}else{
						/*普通产品*/
						String materialIds = caizhiMap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_material_pic_ids);
						Integer materialId=0;
						if(StringUtils.isNotBlank(materialIds)){
							List<String> materialIdStrList=Utils.getListFromStr(materialIds);
							if(materialIdStrList!=null&&materialIdStrList.size()>0){
								materialId=Integer.valueOf(materialIdStrList.get(0));
							}
						}
						if ( materialId!=null && materialId>0 ) {
							ResTexture resTexture = resTextureService.get(materialId);
							if ( resTexture!=null ) {
								splitTextureDTOList = new ArrayList<SplitTextureDTO>();
								List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
								SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
								ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
								searchProductGroupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
								resTextureDTO.setKey(splitTextureDTO.getKey());
								resTextureDTO.setProductId(productID);
								resTextureDTOList.add(resTextureDTO);
								splitTextureDTO.setList(resTextureDTOList);
								splitTextureDTOList.add(splitTextureDTO);
							}
						}
					}
					searchProductGroupDetail.setIsSplit(isSplit);
					searchProductGroupDetail.setSplitTexturesChoose(splitTextureDTOList);
					/*处理拆分材质产品返回默认材质->end*/
				}
				
			}
			else {}
		}
		
		
		// 写到这里我已经快奔溃了。。。
		// TODO:获取每个产品的属性，这里要用奇怪一点的方式了估计。。。因为原有的数据结构不能动太多
		// 把产品属性放到一个 map 里面，如果已经有了，则不去 select 了。
		// 每个产品组中，产品的个数不一定，每个产品属性数量也不一样，小心选择用5
		// FIXME:以后产品属性还要放到缓存里面去
		Map<Integer, Map<String, String>> productAttrMap = new HashMap<>();
		// 先每个产品组里面的所有产品查一下这么来优化
		Collection<List<Integer>> idListCO = groupProductIDListMap.values();  
		for(List<Integer> value:idListCO){  
		    
			// 实际上每个产品的属性都是一样的，如果 productAttrMap 里面有了属性，实际上可以不去查询
			List<Integer> tempList = new ArrayList<>();
			for (Integer productID : value) {
				if ( productAttrMap.get(productID) == null ) {
					tempList.add(productID);
				}
				else {}
			}
			
			// 如果tempList size 为0，说明productAttrMap里面已经有属性了，都不用去查，节省一次sql 查询
			if ( !tempList.isEmpty() ) {
				List<Map<String, Object>> attriList = this.productGroupDao.getProductesAttrisUsingUnion(tempList);
				for (Map<String, Object> map : attriList) {
					Integer productID = (Integer)map.get("productID");
					String attributeKey = (String)map.get("attributeKey");
					if ( !StringUtils.isEmpty(attributeKey) ) {
//						String attributeValueId = (String)map.get("attributeValueId"); // 现在不需要
						String propValue = (String)map.get("propValue");
						Map<String, String> tempMap = new HashMap<>();
						tempMap.put(attributeKey, StringUtils.trimToEmpty(propValue));
						productAttrMap.put(productID, tempMap);
					}
					else {}
				}
			}
			else {}
		}
		
		
		// 已经拿到产品属性了，现在用各种条件查询, 这种效率高一点点
		for ( List<SearchProductGroupDetail> detailsInEveryGroup : listMap.values()) {  
			for (SearchProductGroupDetail searchProductGroupDetail : detailsInEveryGroup) {
				Integer productID = searchProductGroupDetail.getProductId();
				/***在组合产品查询列表 中 增加产品属性****/
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(productID);
				searchProductGroupDetail.setPropertyMap(map);
				// 进行 规则 service 的调用
				Map<String, String> codeAndMarkmap = productCodeAndMarkMap.get(productID);
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(String.valueOf(productID), codeAndMarkmap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_productcode), 
						codeAndMarkmap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_typemark), codeAndMarkmap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_small_typeark), 
						spaceCommonId, null, new DesignRules() , productAttrMap.get(productID));
				searchProductGroupDetail.setRulesMap(rulesMap);
			}
		}
		
		
		
		
		// : 补充品名 和 价格
		for (SearchProductGroupResult group : groupList) {
			Integer groupID = group.getGroupId();
			// 品牌名
			if ( groupMainProductBrandNameMap.get(groupID) != null ) {
				group.setBrandName(groupMainProductBrandNameMap.get(groupID));
			}
			else {}
			
			// 价格
			if ( group.getSalePrice() != null ) {
				if ( group.getSalePrice() <= -1.0 ) { // 说明 品牌 没被绑定，整个组合价格 设为0
					group.setSalePrice(-1.0);
				}
				else {
					if (group.getSalePrice() > -1.0) { // 以 品牌价格 为准，不去统计每个产品的总价格
						
					}
					else {
						Double price = groupPriceMap.get(groupID);
						group.setSalePrice(price);
					}
				}
			}
			else {
				group.setSalePrice(-1.0);
			}
			
			
		}
		return listMap;
	}
	
	@Override
	public Map<Integer, List<SearchProductGroupDetail>> getGroupProductDetailsByGroupIDReplaceList(List<SearchProductGroupResult> groupList,
			String mediaType,  Integer spaceCommonId) {
		
		
//		this.productGroupDao.getProductInfoInTheGroupByGroupIDAndModelIDType(map)
		
		List<ProductInfoInGroupModel> allProductInfoList = new ArrayList<>();
		Map<Integer, Map<String, String>> productCodeAndMarkMap = new HashMap<>(); // 用来缓存1、材质，是可以替换的还是不可以替换的。2、product code 3、typemark 4、small_typemark
		
		// 进行10条、10条的查询
		int groupCount = groupList.size();
		int bigCount = groupCount/LOOP_LIMIT_COUNT;
		int sCount = groupCount%LOOP_LIMIT_COUNT;
		for (int index = 0; index < bigCount; index++) {
			
			List<Map<String, Object>> queryParamsList = new ArrayList<>();
			int limitCount = (index+1)*LOOP_LIMIT_COUNT; // 10、20、30
			int sIndex = index*LOOP_LIMIT_COUNT; // 0、10、20
			for (; sIndex < limitCount; sIndex++ ) {
				SearchProductGroupResult spgr = groupList.get(sIndex);
				Map<String, Object> map = new HashMap<>();
				map.put("groupID", spgr.getGroupId());
				String modelColumnName = this.getU3dModelColumnName(mediaType);
				map.put("idColumnName", modelColumnName);
				if ( spgr.getProductStructureId() != null ) {
					map.put("productStructureId", spgr.getProductStructureId());
				}
				else {
					map.put("productStructureId", 0);
				}
				queryParamsList.add(map);
			}
			
			// FIXME:调试需要 delete
			// 获取10个产品组中每个产品的信息
			List<ProductInfoInGroupModel> infoModelList = this.productGroupDao.getProductInfoInTheGroupByGroupIDAndModelIDType(queryParamsList);
			allProductInfoList.addAll(infoModelList);
		}
		
		
		// 来，这是剩下的
		if ( sCount > 0 ) {
			List<Map<String, Object>> queryParamsList = new ArrayList<>();
			for (int index = 0; index < sCount; index++) {
				int remainIndex = index + bigCount*LOOP_LIMIT_COUNT;
				SearchProductGroupResult spgr = groupList.get(remainIndex);
				Map<String, Object> map = new HashMap<>();
				map.put("groupID", spgr.getGroupId());
				String modelColumnName = this.getU3dModelColumnName(mediaType);
				map.put("idColumnName", modelColumnName);
				if ( spgr.getProductStructureId() != null ) {
					map.put("productStructureId", spgr.getProductStructureId());
				}
				else {
					map.put("productStructureId", 0);
				}
				queryParamsList.add(map);
			}
			
			// 获取剩下产品组中每个产品的信息
			List<ProductInfoInGroupModel> infoModelList = this.productGroupDao.getProductInfoInTheGroupByGroupIDAndModelIDType(queryParamsList);
			allProductInfoList.addAll(infoModelList);
		}
		else {}
		
		// 遍历 allProductInfoList 进行 details 初始化
		Map<Integer, List<SearchProductGroupDetail>> listMap = new HashMap<>(); // 用来放每个 groupid 对应到的 detail list
		Map<Integer, Integer> groupMainProductMap = new HashMap<>(); // 用来缓存每个 group 的 main product id
		Map<Integer, List<Integer>> bandIDListMap = new HashMap<>(); // 用来缓存每个 group 的 对应到的每个产品 list 的每个 bandID
		Map<Integer, Map<String, List<Map<String, Object>>>> bspTypeValueMap = new HashMap<>(); // 用来缓存每个产品组里面每个产品的一些值，用于后面查询
		// 由于这个常常用到，单独拿出来
		Map<Integer, List<Integer>> groupProductIDListMap = new HashMap<>(); // 每个产品组合的product id list 
		
		// 用 groupid 来获取 产品组合 中主产品的 brandName(品牌名)
		Map<Integer, String> groupMainProductBrandNameMap = new HashMap<>();
		// 用来缓存产品组里面每个产品价格的总和
		Map<Integer, Double> groupPriceMap = new HashMap<>();

		
		for (ProductInfoInGroupModel productInfoInGroupModel : allProductInfoList) {
			
			Map<String, String> codeAndMarkMap = new HashMap<>();
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_productcode, productInfoInGroupModel.getProductCode());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_typemark, productInfoInGroupModel.getProductTypeMark());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_small_typeark, productInfoInGroupModel.getProductSmallTypeMark());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_split_textures_info, productInfoInGroupModel.getSplitTexturesInfo());
			codeAndMarkMap.put(PRODUCT_CODE_AND_MARK_MAP_KEY_material_pic_ids, productInfoInGroupModel.getMaterialPicIds());
			productCodeAndMarkMap.put(productInfoInGroupModel.getProductID(), codeAndMarkMap);
			
			Integer groupID = productInfoInGroupModel.getGroupID();
			List<SearchProductGroupDetail> detailList = null;
			List<Integer> bandIDList = null;
			List<Integer> productIDList = null;
			Map<String, List<Map<String, Object>>> bspTypeValueListMap = null;
			
			// 初始化判断
			if ( null == listMap.get(groupID)  ) {
				detailList = new ArrayList<>();
				listMap.put(groupID, detailList);
			}
			else {
				detailList = listMap.get(groupID);
			}
			
			if ( null == bandIDListMap.get(groupID)  ) {
				bandIDList = new ArrayList<>();
				bandIDListMap.put(groupID, bandIDList);
			}
			else {
				bandIDList = bandIDListMap.get(groupID);
			}
			
			if ( null == bspTypeValueMap.get(groupID) ) {
				bspTypeValueListMap = new HashMap<String, List<Map<String, Object>>>();
				List<Map<String, Object>> tempList = new ArrayList<>();
				bspTypeValueListMap.put(BSP_TYPE_VALUE_MAP_KEY, tempList);
				bspTypeValueMap.put(groupID, bspTypeValueListMap);
			}
			else {
				bspTypeValueListMap = bspTypeValueMap.get(groupID);
			}
			
			if ( groupProductIDListMap.get(groupID) == null ) {
				productIDList = new ArrayList<>();
				groupProductIDListMap.put(groupID, productIDList);
			}
			else {
				productIDList = groupProductIDListMap.get(groupID);
			}
			
			// 价格
			if ( groupPriceMap.get(groupID) == null ) {
				groupPriceMap.put(groupID, 0.0);
			}
			else {}
			groupPriceMap.put(groupID, groupPriceMap.get(groupID) + productInfoInGroupModel.getSalePrice()); // 价格加上去
			
			productIDList.add(productInfoInGroupModel.getProductID()); // 把产品 id 放到 list 里面
			
			// 这个用在后面获取产品组中各个产品的大类、小类、父类
			List<Map<String, Object>> typeValueMapList = bspTypeValueListMap.get(BSP_TYPE_VALUE_MAP_KEY);
			Map<String, Object> typeValueMap = new HashMap<>();
			typeValueMap.put("bValue", StringUtils.trimToEmpty(productInfoInGroupModel.getProductTypeValue()));
			typeValueMap.put("sValue", StringUtils.trimToEmpty(productInfoInGroupModel.getProductSmallTypeValue()));
			typeValueMap.put("pValue", StringUtils.trimToEmpty(productInfoInGroupModel.getParentProductTypeValue()));
			typeValueMap.put("productID", productInfoInGroupModel.getProductID());
			typeValueMapList.add(typeValueMap);
			
			SearchProductGroupDetail detail = new SearchProductGroupDetail();
			detail.setProductId(productInfoInGroupModel.getProductID());
			detail.setProductGroupId(productInfoInGroupModel.getGroupID());
			detail.setProductCode(StringUtils.trimToEmpty(productInfoInGroupModel.getProductCode()));
			int isMain = productInfoInGroupModel.getIsMain() == null ? 0 : productInfoInGroupModel.getIsMain();
			detail.setIsMainProduct(isMain);
			// 设置主产品的品牌名字到groupMainProductBrandNameMap
			if ( isMain == GROUP_MAIN_PRODUCT_FLAG.main.ordinal() ) {
				groupMainProductBrandNameMap.put(groupID, StringUtils.trimToEmpty(productInfoInGroupModel.getBrandName()));
			}
			else {}
			// 暂存每个产品组的主产品 id
			if ( GROUP_MAIN_PRODUCT_FLAG.main.ordinal() == isMain  ) {
				groupMainProductMap.put(groupID, productInfoInGroupModel.getProductID());
			}
			else {}
			
			// 对每个 产品 detail 进行模型地址的赋值
			if ( null == productInfoInGroupModel.getModelPath() ) {
				detail.setU3dModelPath("");
			}
			else {
				detail.setU3dModelPath( productInfoInGroupModel.getModelPath() );
			}
			BaseProduct baseProduct = baseProductService.get(productInfoInGroupModel.getProductID());
			// 长、高、宽
			detail.setProductWidth(productInfoInGroupModel.getProductWidth());
			detail.setProductHeight(productInfoInGroupModel.getProductHeight());
			detail.setProductLength(productInfoInGroupModel.getProductLength());
			// 最小高度
			detail.setModelMinHeight(productInfoInGroupModel.getMinHeight() == null ? 0: productInfoInGroupModel.getMinHeight());
			// 相机位置和远景相机位置
			detail.setCameraLook(productInfoInGroupModel.getCameraLook());
			detail.setCameraView(productInfoInGroupModel.getCameraView());
			// 结构相关
			if ( productInfoInGroupModel.getProductStructureId() != null ) {
				detail.setProductStructureId(productInfoInGroupModel.getProductStructureId().intValue());
			}
			else {
				detail.setProductStructureId(0);
			}
			
			if ( productInfoInGroupModel.getChartletProductModelId() != null ) {
				detail.setTextureProductModelId(productInfoInGroupModel.getChartletProductModelId());
			}
			else {
				detail.setTextureProductModelId(0);
			}
			
			// 添加品牌id
			Integer tempBandID = productInfoInGroupModel.getBrandID();
			if ( tempBandID != null && tempBandID > 0  ) {
				bandIDList.add(productInfoInGroupModel.getBrandID());
			}
			else {}
			
			
			// parent product id
			detail.setParentProductId(productInfoInGroupModel.getParentID());
			detail.setProductIndex(productInfoInGroupModel.getProductIndex());
			detailList.add(detail);
			
			listMap.put(groupID, detailList);
			
			bandIDListMap.put(groupID, bandIDList);
		}
		
		// :从 groupMainProductMap 中，对groupList的每个主产品的 id 进行赋值
		// :对 listMap 进行遍历，处理 “品牌是否被绑定状态” brandState 的逻辑
		for (SearchProductGroupResult groupResult : groupList) {
			Integer groupID = groupResult.getGroupId();
			Integer mainProductID = groupMainProductMap.get(groupID);
			if ( mainProductID != null && mainProductID > 0 ) {
				groupResult.setProductId(mainProductID);
			}
		
		}

		// :对产品大类、小类、父类进行处理, 写到这里我差不多要晕了已经
		for (SearchProductGroupResult groupResult : groupList) {
			
			// 逻辑是对每个 group 中的所有产品进行一次 sql 查询，用 union 做
			Integer groupID = groupResult.getGroupId();
			Map<String, List<Map<String, Object>>> map = bspTypeValueMap.get(groupID);
			List<Map<String, Object>> list = map.get(GroupProductServiceV2Impl.BSP_TYPE_VALUE_MAP_KEY);
			if (  list != null && !list.isEmpty()  ) {
				 List<GroupProductAllTypeModel> typeModelList = this.productGroupDao.getProductBSPTypeValueUsingUnion(list);
				 if ( typeModelList != null && !typeModelList.isEmpty() ) {
					 // list 转成 map
					 Map<Integer, GroupProductAllTypeModel> tempProductTypeMap = new HashMap<>();
					 for (GroupProductAllTypeModel groupProductAllTypeModel : typeModelList) {
						 tempProductTypeMap.put(groupProductAllTypeModel.getProductID(), groupProductAllTypeModel);
					 }
					 List<SearchProductGroupDetail> detailList = listMap.get(groupID);
					 for (SearchProductGroupDetail searchProductGroupDetail : detailList) {
						Integer productID = searchProductGroupDetail.getProductId();
						GroupProductAllTypeModel model = tempProductTypeMap.get(productID);
						if ( model != null ) {
							searchProductGroupDetail.setProductTypeValue( model.getbValue() );
							searchProductGroupDetail.setProductTypeCode( StringUtils.trimToEmpty(model.getbValueKey()) );
							searchProductGroupDetail.setProductTypeName( StringUtils.trimToEmpty(model.getbName()) );
							
							searchProductGroupDetail.setProductSmallTypeCode( StringUtils.trimToEmpty(model.getsValueKey()) );
							searchProductGroupDetail.setProductSmallTypeName( StringUtils.trimToEmpty(model.getsName()) );
							searchProductGroupDetail.setProductSmallTypeValue( model.getsValue() == null ? "" : String.valueOf(model.getsValue()) );
							searchProductGroupDetail.setBgWall(Utils.getIsBgWall(StringUtils.trimToEmpty(model.getsValueKey())));
							
							String rootType = StringUtils.isEmpty(model.getsAtt1()) ? "2" : StringUtils.trim(model.getsAtt1());
							searchProductGroupDetail.setRootType(rootType);
							
						}
						else {}
					}
				 }
				 else {
					 
				 }
			}
			else {}
			
		}

		// FIXME:需要 黄宋博 进行重构
		// 产品材质重写，需要加入贴图材质逻辑
		for (SearchProductGroupResult groupResult : groupList) {
			Integer groupID = groupResult.getGroupId();
			List<SearchProductGroupDetail> list = listMap.get(groupID); // 改产品组的产品列表
			if ( list != null && !list.isEmpty() ) {
				
				// 普通产品 和 拆分材质产品 分开
				for (SearchProductGroupDetail searchProductGroupDetail : list) {
					Integer productID = searchProductGroupDetail.getProductId();
					Map<String, String> caizhiMap = productCodeAndMarkMap.get(productID);
					
					/*处理拆分材质产品返回默认材质*/
					String splitTexturesInfo = caizhiMap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_split_textures_info);

					
					Integer isSplit=0;
					List<SplitTextureDTO> splitTextureDTOList = null;
					if ( StringUtils.isNotBlank(splitTexturesInfo)) {
						Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(productID, splitTexturesInfo,"choose");
						isSplit=(Integer) map.get("isSplit");
						splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
					}else{
						/*普通产品*/
						String materialIds = caizhiMap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_material_pic_ids);
						Integer materialId=0;
						if(StringUtils.isNotBlank(materialIds)){
							List<String> materialIdStrList=Utils.getListFromStr(materialIds);
							if(materialIdStrList!=null&&materialIdStrList.size()>0){
								materialId=Integer.valueOf(materialIdStrList.get(0));
							}
						}
						if ( materialId!=null && materialId>0 ) {
							ResTexture resTexture = resTextureService.get(materialId);
							if ( resTexture!=null ) {
								splitTextureDTOList = new ArrayList<SplitTextureDTO>();
								List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
								SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
								ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
								searchProductGroupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
								resTextureDTO.setKey(splitTextureDTO.getKey());
								resTextureDTO.setProductId(productID);
								resTextureDTOList.add(resTextureDTO);
								splitTextureDTO.setList(resTextureDTOList);
								splitTextureDTOList.add(splitTextureDTO);
							}
						}
					}
					searchProductGroupDetail.setIsSplit(isSplit);
					searchProductGroupDetail.setSplitTexturesChoose(splitTextureDTOList);
					/*处理拆分材质产品返回默认材质->end*/
				}
				
			}
			else {}
		}
		
		
		// 写到这里我已经快奔溃了。。。
		// TODO:获取每个产品的属性，这里要用奇怪一点的方式了估计。。。因为原有的数据结构不能动太多
		// 把产品属性放到一个 map 里面，如果已经有了，则不去 select 了。
		// 每个产品组中，产品的个数不一定，每个产品属性数量也不一样，小心选择用5
		// FIXME:以后产品属性还要放到缓存里面去
		Map<Integer, Map<String, String>> productAttrMap = new HashMap<>();
		// 先每个产品组里面的所有产品查一下这么来优化
		Collection<List<Integer>> idListCO = groupProductIDListMap.values();  
		for(List<Integer> value:idListCO){  
		    
			// 实际上每个产品的属性都是一样的，如果 productAttrMap 里面有了属性，实际上可以不去查询
			List<Integer> tempList = new ArrayList<>();
			for (Integer productID : value) {
				if ( productAttrMap.get(productID) == null ) {
					tempList.add(productID);
				}
				else {}
			}
			
			// 如果tempList size 为0，说明productAttrMap里面已经有属性了，都不用去查，节省一次sql 查询
			if ( !tempList.isEmpty() ) {
				List<Map<String, Object>> attriList = this.productGroupDao.getProductesAttrisUsingUnion(tempList);
				for (Map<String, Object> map : attriList) {
					Integer productID = (Integer)map.get("productID");
					String attributeKey = (String)map.get("attributeKey");
					if ( !StringUtils.isEmpty(attributeKey) ) {
//						String attributeValueId = (String)map.get("attributeValueId"); // 现在不需要
						String propValue = (String)map.get("propValue");
						Map<String, String> tempMap = new HashMap<>();
						tempMap.put(attributeKey, StringUtils.trimToEmpty(propValue));
						productAttrMap.put(productID, tempMap);
					}
					else {}
				}
			}
			else {}
		}
		
		
		// 已经拿到产品属性了，现在用各种条件查询, 这种效率高一点点
		for ( List<SearchProductGroupDetail> detailsInEveryGroup : listMap.values()) {  
			for (SearchProductGroupDetail searchProductGroupDetail : detailsInEveryGroup) {
				Integer productID = searchProductGroupDetail.getProductId();
				/***在组合产品查询列表 中 增加产品属性****/
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(productID);
				searchProductGroupDetail.setPropertyMap(map);
				// 进行 规则 service 的调用
				Map<String, String> codeAndMarkmap = productCodeAndMarkMap.get(productID);
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(String.valueOf(productID), codeAndMarkmap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_productcode), 
						codeAndMarkmap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_typemark), codeAndMarkmap.get(PRODUCT_CODE_AND_MARK_MAP_KEY_small_typeark), 
						spaceCommonId, null, new DesignRules() , productAttrMap.get(productID));
				searchProductGroupDetail.setRulesMap(rulesMap);
			}
		}
		
		
		
		
		// : 补充品名 和 价格
		for (SearchProductGroupResult group : groupList) {
			Integer groupID = group.getGroupId();
			// 品牌名
			if ( groupMainProductBrandNameMap.get(groupID) != null ) {
				group.setBrandName(groupMainProductBrandNameMap.get(groupID));
			}
			else {}
			
			// 价格
			if ( group.getSalePrice() != null ) {
				if ( group.getSalePrice() <= -1.0 ) { // 说明 品牌 没被绑定，整个组合价格 设为0
					group.setSalePrice(-1.0);
				}
				else {
					if (group.getSalePrice() > -1.0) { // 以 品牌价格 为准，不去统计每个产品的总价格
						
					}
					else {
						Double price = groupPriceMap.get(groupID);
						group.setSalePrice(price);
					}
				}
			}
			else {
				group.setSalePrice(-1.0);
			}
			
			
		}
		return listMap;
	}
	
	/**
	 * 根据 mediaType 获取 model 字段名
	 * @param mediaType
	 * @return
	 */
	private String getU3dModelColumnName(String mediaType){
		
		if ( mediaType==null ) {
			return "";
		}
		else {}
		if ("3".equals(mediaType)) {
			return "windows_u3dmodel_id";
		} else if("4".equals(mediaType)) {
			return "macbook_u3dmodel_id";
		} else if("5".equals(mediaType)) {
			return "ios_u3dmodel_id";
		} else if("6".equals(mediaType)) {
			return "android_u3dmodel_id";
		} else if("7".equals(mediaType)) {
			return "ipad_u3dmodel_id";
		} else {
			return "windows_u3dmodel_id";
		}
	}

}
