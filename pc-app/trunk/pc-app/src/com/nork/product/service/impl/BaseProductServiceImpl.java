package com.nork.product.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.common.util.ThumbnailUtil;
import com.nork.design.model.output.UnityPlanProductresTexture;
import com.nork.designconfig.model.ProductInfo;
import com.nork.product.model.*;
import com.nork.product.model.result.CeilingCrossDataResult;
import com.nork.product.model.vo.CeilingCrossDataVO;
import com.nork.product.model.vo.ProductCeilingVO;
import com.nork.product.service.*;
import com.nork.system.model.*;
import com.nork.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.nork.client.controller.BaseProductController;
import com.nork.common.constant.SysDictionaryConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.exception.BizException;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.AppProperties;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanTypeConstant;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletPutawayState;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.GroupProductDetailsCache;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.exception.BaseProductException;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchStructureProductDetailResult;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.productprops.model.BaseProductProps;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.sync.model.ProductEntity;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.constant.UserTypeConstant;
import com.nork.user.model.UserTypeCode;
import com.sandu.search.entity.elasticsearch.index.ProductIndexMappingData;
import com.sandu.search.entity.elasticsearch.search.product.ProductSearchForOneKeyDTO;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.service.product.dubbo.ProductSearchOpenService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**   
 * @Title: BaseProductServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
@Service("baseProductService")
@Transactional
public class BaseProductServiceImpl implements BaseProductService {
	
	private static Logger logger = LoggerFactory.getLogger(BaseProductServiceImpl.class);
	
	private static final String LOGPREFIX = "[产品模块]:";
	
	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	private static List<String> beijingValuekeyList = Utils.getListFromStr(
			Utils.getValueByFileKey(AppProperties.APP, AppProperties.SMALLPRODUCTTYPE_BEIJINGWALL_FILEKEY, "basic_dians,basic_shaf,basic_cant,basic_chuangt,basic_xingx,basic_beij")
			);
	
	private static List<SeriesConfig> seriesConfigList = getSeriesConfigList();
	
	private static List<ReplaceTubProperties> replaceTubPropertiesList = getReplaceTubProperties();
	
	@Autowired
	private BaseProductMapper baseProductMapper;
	@Resource
	private ResPicService resPicService;
	@Resource
	private ResModelService resModelService;
	@Resource
	private ProductAttributeService productAttributeService;
	@Autowired
	private ResFileService resFileService; 
	@Autowired
	private SysDictionaryService sysDictionaryService; 
	@Autowired
	private ResTextureService resTextureService; 
	@Autowired
	private DesignRulesService designRulesService; 
	@Autowired
	private ProductCategoryRelService productCategoryRelService; 
	@Autowired
	private GroupProductDetailsService groupProductDetailsService; 
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private ProductColorsService productColorsService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private SysResLevelCfgService sysResLevelCfgService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private StructureProductService structureProductService;
	@Autowired
	private StructureProductDetailsService structureProductDetailsService;
	@Autowired
	private BaseProductCountertopsService baseProductCountertopsService;
	@Autowired
	private ModelAreaRelService modelAreaRelService;

	@Autowired
	private ProductSearchOpenService productSearchOpenService;
	
	/**
	 * 新增数据
	 *
	 * @param baseProduct
	 * @return  int 
	 */
	@Override
	public int add(BaseProduct baseProduct) {
		baseProductMapper.insertSelective(baseProduct);
		return baseProduct.getId();
	}

	/**
	 * 获取/拼装app.product.searchProductForReplaceTub.replaceTubProperties配置
	 * 
	 * @author huangsongbo
	 * @return
	 */
	private static List<com.nork.product.service.impl.BaseProductServiceImpl.ReplaceTubProperties> getReplaceTubProperties() {
		String replaceTubPropertiesStr = Utils.getValueByFileKey(AppProperties.APP, AppProperties.PRODUCT_SEARCHPRODUCTFORREPLACETUB_REPLACETUBPROPERTIES, "[{\"replacedProductSmallType\":\"mpba\",\"affiliateProductSmallType\":\"basic_ltbp\",\"productPropsList\":[{\"key\":\"TapPlace\",\"valueListStr\":\"1,2\"}]},{\"replacedProductSmallType\":\"baba\",\"affiliateProductSmallType\":\"basic_lybp\",\"productPropsList\":[{\"key\":\"TapPlace\",\"valueListStr\":\"1\"}]},{\"replacedProductSmallType\":\"edba\",\"affiliateProductSmallType\":\"basic_lybp\",\"productPropsList\":[{\"key\":\"TapPlace\",\"valueListStr\":\"1\"}]}]");
		JSONArray jsonArray = JSONArray.fromObject(replaceTubPropertiesStr);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("productPropsList", ProductPropsSimple.class);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<ReplaceTubProperties> replaceTubPropertiesList = JSONArray.toList(jsonArray, ReplaceTubProperties.class, classMap);
		// 再处理一波 ->start
		for (int index = 0; index < replaceTubPropertiesList.size(); index++) {
			ReplaceTubProperties replaceTubProperties = replaceTubPropertiesList.get(index);
			if(replaceTubProperties == null) {
				replaceTubPropertiesList.remove(index);
				index --;
			}
			List<ProductPropsSimple> productPropsSimpleList = replaceTubProperties.getProductPropsList();
			if(Lists.isEmpty(productPropsSimpleList)) {
				continue;
			}
			List<ProductPropsSimple> productPropsSimpleListResult = new ArrayList<>();
			for(ProductPropsSimple productPropsSimple : productPropsSimpleList) {
				// 验证配置正确性 ->start
				if(productPropsSimple == null) {
					continue;
				}
				if(StringUtils.isEmpty(productPropsSimple.getKey())) {
					continue;
				}
				if(StringUtils.isEmpty(productPropsSimple.getValueListStr())) {
					continue;
				}
				// 验证配置正确性 ->end
				List<Integer> valueList = Utils.getIntegerListFromStringList(productPropsSimple.getValueListStr());
				if(Lists.isEmpty(valueList)) {
					continue;
				}
				for(Integer value : valueList) {
					ProductPropsSimple productPropsSimpleNew = new ProductPropsSimple();
					productPropsSimpleNew.setKey(productPropsSimple.getKey());
					productPropsSimpleNew.setValue(value);
					productPropsSimpleListResult.add(productPropsSimpleNew);
				}
			}
			replaceTubProperties.setProductPropsList(productPropsSimpleListResult);
		}
		// 再处理一波 ->end
		return replaceTubPropertiesList;
	}

	private static List<SeriesConfig> getSeriesConfigList() {
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("smallTypeValuekeyInfo", SeriesConfigItem.class);
		JSONArray jsonArray = JSONArray.fromObject(ProductModelConstant.SERIESCONFIG);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<SeriesConfig> seriesConfigList = JSONArray.toList(jsonArray, SeriesConfig.class, classMap);
		
		// 处理小类,String->list等等 ->start
		if(Lists.isNotEmpty(seriesConfigList)) {
			for(SeriesConfig seriesConfig : seriesConfigList) {
				List<SeriesConfigItem> seriesConfigItemList = seriesConfig.getSmallTypeValuekeyInfo();
				List<String> valuekeyList = new ArrayList<>();
				if(Lists.isNotEmpty(seriesConfigItemList)) {
					for(SeriesConfigItem seriesConfigItem : seriesConfigItemList) {
						String seriesProductType = seriesConfigItem.getSeriesProductType();
						List<String> seriesProductTypeList = Utils.getListFromStr(seriesProductType);
						seriesConfigItem.setValuekeyList(seriesProductTypeList);
						valuekeyList.addAll(seriesProductTypeList);
					}
				}
				seriesConfig.setValuekeyList(valuekeyList);
			}
		}
		// 处理小类,String->list等等 ->end
		return seriesConfigList;
	}

	/**
	 *    更新数据
	 *
	 * @param baseProduct
	 * @return  int 
	 */
	@Override
	public int update(BaseProduct baseProduct) {
		return baseProductMapper
				.updateByPrimaryKeySelective(baseProduct);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseProduct 
	 */
	@Override
	public BaseProduct get(Integer id) {
		return baseProductMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	@Override
	public List<BaseProduct> getList(BaseProduct baseProduct) {
	    return baseProductMapper.selectList(baseProduct);
	}
	/**
	 * 所有数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	@Override
	public List<BaseProduct> getList2(BaseProduct baseProduct) {
	    return baseProductMapper.selectList2(baseProduct);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @return   int
	 */
	@Override
	public int getCount(BaseProductSearch baseProductSearch){
		return  baseProductMapper.selectCount(baseProductSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @return   List<BaseProduct>
	 */
	@Override
	public List<BaseProduct> getPaginatedList(
			BaseProductSearch baseProductSearch) {
		return baseProductMapper.selectPaginatedList(baseProductSearch);
	}
	
	/**
	 *    根据产品风格得出设计方案风格
	 *
	 * @param  planId
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getProductStyle(Integer planId){
		return baseProductMapper.selestProductStyle(planId);
	}

	/**
	 * 其他
	 * 
	 */
	/**
	 * 查询同类型数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	@Override
	public List<BaseProduct> getSameTypeProductList(BaseProduct baseProduct) {
	    return baseProductMapper.sameTypeProductList(baseProduct);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseProduct
	 * @return   int
	 */
	@Override
	public int getSameTypeProductCount(BaseProduct baseProduct){
		return  baseProductMapper.sameTypeProductCount(baseProduct);	
    }

	/**
	 * 根据产品名称和品牌ID查询产品
	 * @param baseProduct
	 * @return
	 */
	@Override
	public List<CategoryProductResult> selectProductByNameAndBrandId(BaseProduct baseProduct){
		return baseProductMapper.selectProductByNameAndBrandId(baseProduct);
	}

	@Override
	public Integer selectProductByNameAndBrandCount(BaseProduct baseProduct){
		return baseProductMapper.selectProductByNameAndBrandCount(baseProduct);
	}

	@Override
	public Integer getDetailProduct(Map map) {
		Integer proId = -1;
		if (map == null || map.size() == 0) {
			return -1;
		}
		Integer colorId = (Integer) map.get("colorId");
		String materialId = (String) map.get("materialId");
		String onclickType = (String) map.get("onclickType");
		String mergeProductIds = (String) map.get("mergeProductIds");
		if (StringUtils.isBlank(materialId) || colorId == null
				|| colorId < 0 || StringUtils.isEmpty(onclickType)
				|| StringUtils.isEmpty(mergeProductIds)) {
			return proId;
		}
		try{
		ResPic resMaterail = resPicService.get(Integer.parseInt(materialId));	
		//选中类型是colorId，materialId
	    //如果存在，则返回id，如果不存在，通过选中的匹配到另外一个存在的产品返回上去
		String[] mergeProducts = mergeProductIds.split(",");  
		if( mergeProducts!= null && mergeProducts.length>0){
			for(String productId:mergeProducts){
				if(StringUtils.isNotBlank(productId) && new Integer(productId)>0){
					BaseProduct bp = this.get(new Integer(productId));
					Integer bpColorId = bp.getColorId() ;
					String bpMaterialId = bp.getMaterialPicIds();
					if(bp != null){
						ResPic resPic = null;
						String materialList = bp.getMaterialPicIds();
						if(materialList!= null && materialList.contains(",")){
							String[] materials = materialList.split(",");
							if(materials!= null && materials.length >0){
								bpMaterialId = materials[0];
								resPic = resPicService.get(Integer.parseInt(bpMaterialId));
							}else{
								continue;
							}
						}else{
							if(materialList!= null && !materialList.contains(",")){
								bpMaterialId = bp.getMaterialPicIds();
								resPic = resPicService.get(Integer.parseInt(bpMaterialId));
							}else{
								continue;
							}
						}
						//颜色和材质相同的产品返回
//						if(colorId == bpColorId && materialId.equals(bpMaterialId)){
						if(colorId == bpColorId && resMaterail.getPicName().equals(resPic.getPicName())){
							proId =  bp.getId();
							break;
						}
					}
				}
			}
			//如果找不到则以选择的类型为中心，寻找对应的产品默认信息
			if(proId == -1){
				for(String productId:mergeProducts){
					if(StringUtils.isNotBlank(productId) && new Integer(productId)>0){
						BaseProduct bp = this.get(new Integer(productId));
						Integer bpColorId = bp.getColorId() ;
						String bpMaterialId = bp.getMaterialPicIds();
						if(bp != null){
							String materialList = bp.getMaterialPicIds();
							if(materialList!= null && materialList.contains(",")){
								String[] materials = materialList.split(",");
								if(materials!= null && materials.length >0){
									bpMaterialId = materials[0];
								}else{
									continue;
								}
							}else{
								if(materialList!= null && !materialList.contains(",")){
									bpMaterialId = bp.getMaterialPicIds();
								}else{
									continue;
								}
							}
							//颜色和材质相同的产品返回
							if("color".equals(onclickType)){
								if(colorId == bpColorId){
									proId =  bp.getId();
									break;
								}
							}
							if("material".equals(onclickType)){
								if(materialId.equals(bpMaterialId)){
									proId =  bp.getId();
									break;
								}
							}
						}
					}
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return proId;
	}
	
	/**
	 * 产品新增
	 */
	public boolean add(BaseProduct baseProduct,String proAttIds,HttpServletRequest request){
		try{
			if(baseProduct.getSalePrice()==null)
				baseProduct.setSalePrice(0.0);
			if (baseProduct.getId() == null || baseProduct.getId() <= 0 ) {
				/*baseProduct.setPutawayState(0);*/
				baseProduct.setPutawayState(DesignTempletPutawayState.NO_UP.intValue());
				int id = this.add(baseProduct);
				logger.info("add:id=" +id);
				baseProduct.setId(id);
				//回填方法
				backfill(baseProduct); 
				//产品属性 回填产品ID
				if(StringUtils.isNotBlank(proAttIds)){
					String attIds[] = proAttIds.split(","); 
					for(String attId:attIds){ 
						ProductAttribute pa = new ProductAttribute();
						pa = productAttributeService.get(Utils.getIntValue(attId));
						if( pa != null ) {
							pa.setProductId(id);
							productAttributeService.update(pa);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace(); 
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
	
	public void copyResources(BaseProduct oldProduct,BaseProduct newProduct,HttpServletRequest request){
		/*pic(缩略图)*/
		if( oldProduct.getPicId() != null && oldProduct.getPicId() > 0 ){
			Integer picId = resPicService.copyPic(oldProduct.getPicId(), request);
			newProduct.setPicId(picId);
		}
		/*图片列表*/
		if( StringUtils.isNotBlank(oldProduct.getPicIds()) ){
			String pic[] = oldProduct.getPicIds().split(",");
			StringBuffer sb = new StringBuffer();
			for( String id : pic ){
				Integer picId = resPicService.copyPic(Utils.getIntValue(id), request);
				sb.append(picId).append(",");
			}
			String ids = sb.toString();
			if( ids.length() > 0 ){
				ids = ids.substring(0,ids.length()-1);
			}
			newProduct.setPicIds(ids);
		}
		/*材质*/
		if( StringUtils.isNotBlank(oldProduct.getMaterialPicIds()) ){
			String pic[] = oldProduct.getMaterialPicIds().split(",");
			StringBuffer sb = new StringBuffer();
			for( String id : pic ){
				Integer picId = resPicService.copyPic(Utils.getIntValue(id), request);
				sb.append(picId).append(",");
			}
			String ids = sb.toString();
			if( ids.length() > 0 ){
				ids = ids.substring(0,ids.length()-1);
			}
			newProduct.setMaterialPicIds(ids);
		}
		/*材质球文件*/
		if( oldProduct.getMaterialFileId() != null && oldProduct.getMaterialFileId() > 0 ){
			Integer fileId = resFileService.copyFile(oldProduct.getModelId(),request);
			newProduct.setMaterialFileId(fileId);
		}
		
		
		/*modelId(3dmax模型)*/
		if( oldProduct.getModelId() != null ){
			Integer modelId = resModelService.copyModel(oldProduct.getModelId(),request);
			newProduct.setModelId(modelId);
		}
		/*u3dModelId*/
		if( StringUtils.isNotBlank(oldProduct.getU3dModelId()) ){
			Integer u3dModelId = resModelService.copyModel(Integer.parseInt(oldProduct.getU3dModelId()), request);
			newProduct.setU3dModelId(""+u3dModelId);
		}
		/*ipadU3dModelId*/
		if( oldProduct.getIpadU3dModelId() != null ){
			Integer ipadU3dModelId = resModelService.copyModel(oldProduct.getIpadU3dModelId(), request);
			newProduct.setIpadU3dModelId(ipadU3dModelId);
		}
		/*macBookU3dModelId*/
		if( oldProduct.getMacBookU3dModelId() != null ){
			Integer macBookU3dModelId = resModelService.copyModel(oldProduct.getMacBookU3dModelId(), request);
			newProduct.setMacBookU3dModelId(macBookU3dModelId);
		}
		/*windowsU3dModelId*/
		if( oldProduct.getWindowsU3dModelId() != null && oldProduct.getWindowsU3dModelId() > 0 ){
			Integer windowsU3dModelId = resModelService.copyModel(oldProduct.getWindowsU3dModelId(), request);
			newProduct.setWindowsU3dModelId(windowsU3dModelId);
		}
		/*androidU3dModelId*/
		if( oldProduct.getAndroidU3dModelId() != null && oldProduct.getAndroidU3dModelId() > 0 ){
			Integer androidU3dModelId = resModelService.copyModel(oldProduct.getAndroidU3dModelId(), request);
			newProduct.setAndroidU3dModelId(androidU3dModelId);
		}
		/*iosU3dModelId*/
		if( oldProduct.getIosU3dModelId() != null && oldProduct.getIosU3dModelId() >0 ){
			Integer iosU3dModelId = resModelService.copyModel(oldProduct.getIosU3dModelId(), request);
			newProduct.setIosU3dModelId(iosU3dModelId);
		}
	}
	
	
	/**
	 * 产品新增、修改需回填的信息表
	 * @param baseProduct
	 */
	public void backfill(BaseProduct baseProduct){
		Integer productId = baseProduct.getId();
		//回填缩略图businessId
		if( baseProduct.getPicId() != null ) {
			String picKey = "product.baseProduct.pic" ;
			resPicService.backFillResPic(productId,Integer.valueOf(baseProduct.getPicId()),picKey,baseProduct.getProductCode());
		}
		//回填web使用u3d模型businessId
		if( StringUtils.isNotBlank(baseProduct.getU3dModelId()) ){
			String modelKey = "product.baseProduct.u3dmodel.web";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getU3dModelId()), modelKey);
		}
		//回填ipad使用u3d模型businessId
		if( baseProduct.getIpadU3dModelId() != null && baseProduct.getIpadU3dModelId() > 0 ){
			String modelKey = "product.baseProduct.u3dmodel.ipad";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getIpadU3dModelId()), modelKey);
		}
		//回填ios使用u3d模型businessId
		if( baseProduct.getIosU3dModelId() != null && baseProduct.getIosU3dModelId() > 0 ){
			String modelKey = "product.baseProduct.u3dmodel.ios";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getIosU3dModelId()), modelKey);
		}
		//回填android使用u3d模型businessId
		if( baseProduct.getAndroidU3dModelId() != null && baseProduct.getAndroidU3dModelId() > 0 ){
			String modelKey = "product.baseProduct.u3dmodel.android";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getAndroidU3dModelId()), modelKey);
		}
		//回填macBook使用u3d模型businessId
		if( baseProduct.getMacBookU3dModelId() != null && baseProduct.getMacBookU3dModelId() > 0 ){
			String modelKey = "product.baseProduct.u3dmodel.macBookPc";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getMacBookU3dModelId()), modelKey);
		}
		//回填windows使用u3d模型businessId
		if( baseProduct.getWindowsU3dModelId() != null && baseProduct.getWindowsU3dModelId() > 0 ){
			String modelKey = "product.baseProduct.u3dmodel.windowsPc";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getWindowsU3dModelId()), modelKey);
		}
		/*回填材质球配置文件的businessId*/
		if(baseProduct.getMaterialFileId()!=null&&baseProduct.getMaterialFileId()>0){
			resFileService.setBusinessId(baseProduct.getMaterialFileId(), baseProduct.getId());
		}
		//回填材质模型businessId
		if( StringUtils.isNotBlank(baseProduct.getMaterialPicIds()) ){
			String[] arr = baseProduct.getMaterialPicIds().split(",");
			for( int i=0;i<arr.length;i++ ){
				String picKey = "product.baseProduct.material" ;
				resPicService.backFillResPic(productId,Integer.valueOf(arr[i]),picKey,baseProduct.getProductCode());
			}
		}
		//回填3dmax材质模型businessId
		if( StringUtils.isNotBlank(baseProduct.getMaterial3dPicIds()) ){
			String[] arr = baseProduct.getMaterial3dPicIds().split(",");
			for( int i=0;i<arr.length;i++ ){
				String picKey = "product.baseProduct.material3d" ;
				resPicService.backFillResPic(productId,Integer.valueOf(arr[i]),picKey,baseProduct.getProductCode());
			}
		}
		//回填3dmax模型businessId
		if( baseProduct.getModelId() != null && baseProduct.getModelId() > 0 ){
			String modelKey = "product.baseProduct.model";
			resModelService.backFillResModel(productId, Integer.valueOf(baseProduct.getModelId()), modelKey);
		}
		//回填产品列表businessId
		if( StringUtils.isNotBlank(baseProduct.getPicIds()) ){
			String[] arr = baseProduct.getPicIds().split(",");
			for( int i=0;i<arr.length;i++ ){
				String picKey = "product.baseProduct.piclist" ;
				resPicService.backFillResPic(productId,Integer.valueOf(arr[i]),picKey,baseProduct.getProductCode());
			}
		}
	}
	
	/**
	 * 产品修改先清除资源回填信息
	 * @param baseProduct
	 */
	public void clearBackfill(BaseProduct baseProduct){
		
		Integer productId = baseProduct.getId();
		//回填缩略图businessId
		if( baseProduct.getPicId() != null ) {
			resPicService.clearBackfillResPic(productId, baseProduct.getPicId());
		}
		
		//回填web使用u3d模型businessId
		if( StringUtils.isNotBlank(baseProduct.getU3dModelId()) ){
			resModelService.clearBackfillResModel(productId, Integer.valueOf(baseProduct.getU3dModelId()));
		}
		//回填ipad使用u3d模型businessId
		if( baseProduct.getIpadU3dModelId() != null && baseProduct.getIpadU3dModelId() > 0 ){
			resModelService.clearBackfillResModel(productId, baseProduct.getIpadU3dModelId());
		}
		//回填ios使用u3d模型businessId
		if( baseProduct.getIosU3dModelId() != null && baseProduct.getIosU3dModelId() > 0 ){
			resModelService.clearBackfillResModel(productId, baseProduct.getIosU3dModelId());
		}
		//回填android使用u3d模型businessId
		if( baseProduct.getAndroidU3dModelId() != null && baseProduct.getAndroidU3dModelId() > 0 ){
			resModelService.clearBackfillResModel(productId, baseProduct.getAndroidU3dModelId());
		}
		//回填macBook使用u3d模型businessId
		if( baseProduct.getMacBookU3dModelId() != null && baseProduct.getMacBookU3dModelId() > 0 ){
			resModelService.clearBackfillResModel(productId, baseProduct.getMacBookU3dModelId());
		}
		//回填macBook使用u3d模型businessId
		if( baseProduct.getWindowsU3dModelId() != null && baseProduct.getWindowsU3dModelId() > 0 ){
			resModelService.clearBackfillResModel(productId, baseProduct.getWindowsU3dModelId());
		}
		//回填材质模型businessId
		if( StringUtils.isNotBlank(baseProduct.getMaterialPicIds()) ){
			String[] arr = baseProduct.getMaterialPicIds().split(",");
			for( int i=0;i<arr.length;i++ ){
				resPicService.clearBackfillResPic(productId, Utils.getIntValue(arr[i]));
			}
		}
		//回填3dmax材质模型businessId
		if( StringUtils.isNotBlank(baseProduct.getMaterial3dPicIds()) ){
			String[] arr = baseProduct.getMaterial3dPicIds().split(",");
			for( int i=0;i<arr.length;i++ ){
				resPicService.clearBackfillResPic(productId, Utils.getIntValue(arr[i]));
			}
		}
		//回填3dmax模型businessId
		if( baseProduct.getModelId() != null && baseProduct.getModelId() > 0 ){
			resModelService.clearBackfillResModel(productId, baseProduct.getModelId());
		}
		//回填产品列表businessId
		if( StringUtils.isNotBlank(baseProduct.getPicIds()) ){
			String[] arr = baseProduct.getPicIds().split(",");
			for( int i=0;i<arr.length;i++ ){
				resPicService.clearBackfillResPic(productId, Utils.getIntValue(arr[i]));
			}
		}
	}

	@Override
	public List<String> findAllName() {
		return baseProductMapper.findAllName();
	}

	@Override
	public String getU3dModelId(String mediaType,BaseProduct baseProduct){
		if(baseProduct == null || mediaType==null){
			return "";
		}
		if("3".equals(mediaType)){
			return baseProduct.getWindowsU3dModelId()==null?"":baseProduct.getWindowsU3dModelId().toString();
		}else if("4".equals(mediaType)){
			return baseProduct.getMacBookU3dModelId()==null?"":baseProduct.getMacBookU3dModelId().toString();
		}else if("5".equals(mediaType)){
			return baseProduct.getIosU3dModelId()==null?"":baseProduct.getIosU3dModelId().toString();
		}else if("6".equals(mediaType)){
			return baseProduct.getAndroidU3dModelId()==null?"":baseProduct.getAndroidU3dModelId().toString();
		}else if("7".equals(mediaType)){
			return baseProduct.getIpadU3dModelId()==null?"":baseProduct.getIpadU3dModelId().toString();
		}else{
			return baseProduct.getWindowsU3dModelId()==null?"":baseProduct.getWindowsU3dModelId().toString();
		}
	}

	/**
	 * 产品组工作周报表
	 * @param baseProductSearch
	 * @return
	 */
	@Override
	public List<ProductWeekReport> productExecutionReport(BaseProductSearch baseProductSearch){
		/*取时间段,按周分组,每组取数据*/
		List<ProductWeekReport> productWeekReports=new ArrayList<ProductWeekReport>();
		/*得到startDate对应周的星期一和endDate对应周的星期天*/
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf.parse(baseProductSearch.getStartDate());
			endDate = sdf.parse(baseProductSearch.getEndDate());
		} catch (ParseException e) {
			logger.info("------productExecutionReport:String转化Date失败");
			e.printStackTrace();
		}
		/*取startDate的周一*/
		startDate=convertWeekByDate(startDate);
		/*循环取一周的数据*/
		do{
			String oneWeekInfo="";
			baseProductSearch.setStartDateD(startDate);//星期一
			oneWeekInfo+=dateFormatString(startDate)+"到";
			//取startDate一周之后(星期天)
			startDate=convertWeekByDate2(startDate, 0);
			oneWeekInfo+=dateFormatString(startDate);
			//startDate(星期天)+一天(星期一)
			startDate=convertWeekByDate2(startDate, 1);
			baseProductSearch.setEndDateD(startDate);
			List<ProductWeekReport> productWeekReport=baseProductMapper.productExecutionReport(baseProductSearch);
			if(productWeekReport!=null&&productWeekReport.size()>0){
				productWeekReport.get(0).setOneWeekInfo(oneWeekInfo);
				productWeekReports.add(productWeekReport.get(0));
			}
		}while(startDate.before(endDate));
		return productWeekReports;
	}

	@Override
	public String findAllCode(BaseProductSearch baseProductSearch) {
		return baseProductMapper.findAllCode(baseProductSearch);
	}
	
	/**得到指定日期的周一*/
	private Date convertWeekByDate(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		return cal.getTime();
	}
	
	/**得到指定日期的周日addDay:再往后addDay天*/
	private Date convertWeekByDate2(Date time,Integer addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, 6+addDay);
		return cal.getTime();
	}
	
	/*Date转化为String*/
	private String dateFormatString(Date date){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String str=simpleDateFormat.format(date);
		return str;
	}

	@Override
	public String findHardMountedProductCode(BaseProductSearch baseProductSearch) {
		return baseProductMapper.findHardMountedProductCode(baseProductSearch);
	}

	@Override
	public String findHardMountedWhiteMoldCoding(
			BaseProductSearch baseProductSearch) {
		return baseProductMapper.findHardMountedWhiteMoldCoding(baseProductSearch);
	}

	@Override
	public String findSoftloadingProductCode(BaseProductSearch baseProductSearch) {
		return baseProductMapper.findSoftloadingProductCode(baseProductSearch);
	}

	@Override
	public String findSoftFittedWhiteMoldCoding(
			BaseProductSearch baseProductSearch) {
		return baseProductMapper.findSoftFittedWhiteMoldCoding(baseProductSearch);
	}

	@Override
	public List<BaseProduct> findAllSpaceStuffProductList() {
		
		return baseProductMapper.findAllSpaceStuffProductList();
	}
	
	/**
	 * 更新产品所有附件的code
	 * @param baseProduct
	 */
	public void updateCode(BaseProduct baseProduct){
		String code=baseProduct.getProductCode();
		/*picId*/
		if(baseProduct.getPicId()!=null&&baseProduct.getPicId()>0){
			resPicService.updateCode(baseProduct.getPicId(), code);
		}
		/*modelId*/
		if(baseProduct.getModelId()!=null&&baseProduct.getModelId()>0){
			resModelService.updateCode(baseProduct.getModelId(), code);
		}
		/*materialPicIds:多个*/
		if(StringUtils.isNotBlank(baseProduct.getMaterialPicIds())){
			if(baseProduct.getMaterialPicIds().indexOf(",")!=-1){
				/*多个*/
				String[] ids=baseProduct.getMaterialPicIds().split(",");
				for(String id:ids){
					resPicService.updateCode(Integer.parseInt(id), code);
				}
			}else{
				resPicService.updateCode(Integer.parseInt(baseProduct.getMaterialPicIds()), code);
			}
		}
		/*smallPicIds:多个*/
		if(StringUtils.isNotBlank(baseProduct.getPicIds())){
			if(baseProduct.getPicIds().indexOf(",")!=-1){
				/*多个*/
				String[] ids=baseProduct.getPicIds().split(",");
				for(String id:ids){
					resPicService.updateCode(Integer.parseInt(id), code);
				}
			}else{
				resPicService.updateCode(Integer.parseInt(baseProduct.getPicIds()), code);
			}
		}
		/*u3dModelId*/
		if(StringUtils.isNotBlank(baseProduct.getU3dModelId())){
			if(baseProduct.getU3dModelId().indexOf(",")!=-1){
				/*多个*/
				String[] ids=baseProduct.getU3dModelId().split(",");
				for(String id:ids){
					resModelService.updateCode(Integer.parseInt(id), code);
				}
			}else{
				resModelService.updateCode(Integer.parseInt(baseProduct.getU3dModelId()), code);
			}
		}
		/*ipadU3dModelId*/
		if(baseProduct.getIpadU3dModelId()!=null&&baseProduct.getIpadU3dModelId()>0){
			resModelService.updateCode(baseProduct.getIpadU3dModelId(), code);
		}
		/*macBookU3dModelId*/
		if(baseProduct.getMacBookU3dModelId()!=null&&baseProduct.getMacBookU3dModelId()>0){
			resModelService.updateCode(baseProduct.getMacBookU3dModelId(), code);
		}
		/*windowsU3dModelId*/
		if(baseProduct.getWindowsU3dModelId()!=null&&baseProduct.getWindowsU3dModelId()>0){
			resModelService.updateCode(baseProduct.getWindowsU3dModelId(), code);
		}
		/*androidU3dModelId*/
		if(baseProduct.getAndroidU3dModelId()!=null&&baseProduct.getAndroidU3dModelId()>0){
			resModelService.updateCode(baseProduct.getAndroidU3dModelId(), code);
		}
		/*iosU3dModelId*/
		if(baseProduct.getIosU3dModelId()!=null&&baseProduct.getIosU3dModelId()>0){
			resModelService.updateCode(baseProduct.getIosU3dModelId(), code);
		}
	}

	@Override
	public String findTempletHardMountedProductCode(
			BaseProductSearch baseProductSearch) {
		return baseProductMapper.findTempletHardMountedProductCode(baseProductSearch);
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(BaseProduct model,HttpServletRequest request){
        		if(model != null){
        		  LoginUser loginUser = new LoginUser();
                  loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
                  
                  if(loginUser==null || loginUser.getId() == null){
                    //使用另一种读取用户的方式，因为PC端有些接口token信息作为参数传递
                    loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
                    if(loginUser == null || loginUser.getId() == null) {
                      loginUser = new LoginUser();
                      loginUser.setLoginName("nologin");
                    }
                }
				 
				if(model.getId() == null || model.getId()==-1){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public int authorizedProductCount(BaseProductSearch baseProductSearch) {
		return baseProductMapper.authorizedProductCount(baseProductSearch);
	}

	@Override
	public List<BaseProduct> authorizedProductList(
			BaseProductSearch baseProductSearch) {
		return baseProductMapper.authorizedProductList(baseProductSearch);
	}

	/**
	 * 获取产品列表
	 * @param baseProduct
	 * @return
	 */
	@Override
	public List<CorrectProduct> getProductList(BaseProduct baseProduct){
		return baseProductMapper.getProductList(baseProduct);
	}
	
	@Override
	public ProductEntity selectProductEntity(Integer id){
		return baseProductMapper.selectProductEntity(id);
	}

	/**
	 * 通过编码删除
	 * @param productCode
	 * @return
	 */
	@Override
	public int deleteByCode(String productCode){
		// 删除资源文件

		// 删除数据
		return baseProductMapper.deleteByCode(productCode);
	}

	/**
	 * 新增ProductEntity
	 * @return
	 */
	@Override
	public int insertEntity(ProductEntity productEntity){
		baseProductMapper.insertEntity(productEntity);
		return productEntity.getId();
	}

	/**
	 * 更新ProductEntity
	 * @param productEntity
	 * @return
	 */
	@Override
	public int updateEntity(ProductEntity productEntity){
		return baseProductMapper.updateEntity(productEntity);
	}

	@Override
	public ProductEntity sysSave(ProductEntity baseProduct,LoginUser loginUser){
		baseProduct.setCreator(loginUser.getLoginName());
		baseProduct.setModifier(loginUser.getLoginName());
		baseProduct.setGmtCreate(new Date());
		baseProduct.setGmtModified(new Date());
		return baseProduct;
	}

	/**
	 * 获得产品的缩略图url
	 * @return
	 */
	public String getPicUrlFromProductId(Integer productId) {
		String url="";
		BaseProduct baseProduct=get(productId);
		if(baseProduct==null)
			return url;
		if(baseProduct.getPicId()==null)
			return url;
		ResPic resPic=resPicService.get(baseProduct.getPicId());
		if(resPic==null)
			return url;
		if(StringUtils.isBlank(url))
			url=resPic.getPicPath();
		return url;
	}
	
	/**
	 * 判断是否为硬装产品
	 * @param baseProduct
	 */
	public boolean isHard(BaseProduct baseProduct){
		logger.info("------baseProductCode:"+baseProduct.getProductCode());
		boolean flag = false;
		String typeValue = baseProduct.getProductTypeValue();
		if( StringUtils.isNotBlank(typeValue) ){
			SysDictionary dlDic = sysDictionaryService.getSysDictionary("productType", Integer.valueOf(typeValue));
			Integer smallType = baseProduct.getProductSmallTypeValue();
			SysDictionary xlDic = null;
			if( smallType != null && smallType.intValue() > 0 ){
				xlDic = sysDictionaryService.getSysDictionary(dlDic.getValuekey(), smallType);
			}
			if( "qiangm,dim".indexOf(dlDic.getValuekey()) > -1 ){
				String valueKey=Utils.getTypeValueKey(xlDic.getValuekey());
				/*if( ("chuangt".equals(xlDic.getValuekey()) || "cant".equals(xlDic.getValuekey()) || "shaf".equals(xlDic.getValuekey()) || "dians".equals(xlDic.getValuekey()) || "xingx".equals(xlDic.getValuekey()))
						&& StringUtils.isNotBlank(baseProduct.getBmIds()) ){*/
				if( ("chuangt".equals(valueKey) || "cant".equals(valueKey) || "shaf".equals(valueKey) || "dians".equals(valueKey) ||
						"xingx".equals(valueKey) || "beijing".equals(valueKey) || "chuangk".equals(valueKey) || "mengk".equals(valueKey))
					//xiaoxc 6-21注释，原因背景墙详情替换模型不对
					//&& ("baimo".equals(xlDic.getAtt3())||StringUtils.isNotBlank(baseProduct.getBmIds()))
						){
					flag = false;
				}else {
					flag = true;
				}
			}
			//平吊天花有贴图/截面天花无模型
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(dlDic.getValuekey())) {
				if (ProductTypeConstant.PRODUCT_SMALL_TYPE_PDTIANH.equals(xlDic.getValuekey())
						|| ProductTypeConstant.PRODUCT_SMALL_TYPE_JIEM.equals(xlDic.getValuekey())) {
					flag = true;
				}
			}
		}
		return flag;
	}

	@Override
	public List<CategoryProductResult> getBrandProductsList(BaseProduct baseProduct) {
		return baseProductMapper.selectBrandProductsList(baseProduct);
	}

	@Override
	public int getBrandProductsCount(BaseProduct baseProduct) {
		return baseProductMapper.selectBrandProductsCount(baseProduct);
	}

	/**
	 * 处理类似于"!xingx"的值
	 * @author huangsongbo
	 * @param categoryBlacklistMap
	 * @return
	 */
	public Map<String, Set<String>> dealWithMap(Map<String, Set<String>> categoryBlacklistMap) {
		/*{CW_jj=[dddd, xxxx, (xingx,xingy)]}*/
		List<String> smallTypeList=sysDictionaryService.findAllSmallTypeValueKey();
		for(Map.Entry<String, Set<String>> entry:categoryBlacklistMap.entrySet()){
			Set<String> typeSet=entry.getValue();//[dddd, xxxx, (xingx,xingy)]
			for(String typeStr:typeSet){
				if(typeStr.startsWith("(")&&typeStr.endsWith("")){
					typeSet.remove(typeStr);
					List<String> smallTypeListCopy=new ArrayList<String>();
					smallTypeListCopy.addAll(smallTypeList);
					List<String> strList=Utils.getListFromStr(typeStr.replace("(", "").replace(")", ""));
					for(String str:strList){
						smallTypeListCopy.remove(str);
					}
					typeSet.addAll(smallTypeListCopy);
					entry.setValue(typeSet);
				}
			}
			/*test*/
			////////System.out.println(typeSet.contains("zaox"));
			////////System.out.println(typeSet.contains("koub"));
			/*test->end*/
		}
		return categoryBlacklistMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CategoryProductResult decorationProductInfo(CategoryProductResult productResult,BaseProduct baseProduct,DesignPlan designPlan,
			DesignPlanProduct designPlanProduct,Map<String,AutoCarryBaimoProducts> autoCarryMap, LoginUser loginUser) {
		
		//1代表是定制产品，0否
		if( baseProduct.getBmIds() != null ){
			productResult.setIsCustomized(1);
		}
		//是否是主产品
		GroupProductDetails groupProductDetails = new GroupProductDetails();
		groupProductDetails.setProductId(baseProduct.getId());
		groupProductDetails.setIsMain(1);
		groupProductDetails.setIsDeleted(0);
		List<GroupProductDetails> groupList = new ArrayList<>();
		if( Utils.enableRedisCache() ){
			groupList = GroupProductDetailsCache.getList(groupProductDetails);
		}else{
			groupList = groupProductDetailsService.getList(groupProductDetails);
		}
		if( Lists.isNotEmpty(groupList) && groupList.size() > 0 ){
			productResult.setIsMainProduct(1);
		}
		String modelId = getU3dModelId(loginUser.getMediaType(), baseProduct);
		if(StringUtils.isNotEmpty(modelId)){
			ResModel resModel=null;
			if( Utils.enableRedisCache() ){
				resModel = ResourceCacher.getModel(Integer.valueOf(modelId));
			}else{
				resModel = resModelService.get(Integer.valueOf(modelId));
			}
			if(resModel != null){
				productResult.setU3dModelPath(resModel.getModelPath());
				productResult.setProductModelPath(resModel.getModelPath());
				productResult.setModelLength(resModel.getLength());
				productResult.setModelWidth(resModel.getWidth());
				productResult.setModelHeight(resModel.getHeight());
				productResult.setModelMinHeight(resModel.getMinHeight());
				productResult.setModelProductId(baseProduct.getId());
			}else{
			}
		}else{
		}
		String productTypeValue2 = baseProduct.getProductTypeValue();
		if(StringUtils.isNotBlank(productTypeValue2)){
			SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue2));
			productResult.setProductTypeValue(productTypeSysDic.getValue());
			productResult.setProductTypeCode(productTypeSysDic.getValuekey());
			productResult.setProductTypeName(productTypeSysDic.getName());
			Integer productSmallTypeValue2 = baseProduct.getProductSmallTypeValue();
			if (productTypeSysDic.getValue() != null && productSmallTypeValue2 != null) {
				SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue2);
				productResult.setProductSmallTypeCode(productSmallTypeSysDic.getValuekey());
				productResult.setProductSmallTypeName(productSmallTypeSysDic.getName());
				productResult.setProductSmallTypeValue(productSmallTypeSysDic.getValue());
				String rootType = StringUtils.isEmpty(productSmallTypeSysDic.getAtt1()) ? "2" : productSmallTypeSysDic.getAtt1().trim();
				productResult.setRootType(rootType);
				//获取是否是背景墙
				if( ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(productTypeSysDic.getValuekey()) ){
					productResult.setBgWall(Utils.getIsBgWall(productSmallTypeSysDic==null?"":productSmallTypeSysDic.getValuekey()));
				}else{
					productResult.setBgWall(0);
				}
			}
		}
		//空间ID
		productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
		//产品长宽高
		productResult.setProductLength(baseProduct.getProductLength());
		productResult.setProductWidth(baseProduct.getProductWidth());
		productResult.setProductHeight(baseProduct.getProductHeight());
		productResult.setMinHeight(baseProduct.getMinHeight());
		/*处理拆分材质产品返回默认材质*/
		String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
		Integer isSplit=0;
		List<SplitTextureDTO> splitTextureDTOList=null;

		if(StringUtils.isNotBlank(splitTexturesInfo)){
			//获取材质信息
			Map<String,Object> dealWithMap =  modelAreaRelService.dealWithSplitTextureInfo(baseProduct.getId(),StringUtils.isNotBlank(modelId) ? Integer.parseInt(modelId) : null,"choose","1");
			isSplit = (Integer)dealWithMap.get("isSplit");
			splitTextureDTOList = (List<SplitTextureDTO>)dealWithMap.get("splitTexturesChooseList");

		}else{
			/*普通产品*/
			String materialIds = productResult.getMaterialPicId();
			Integer materialId=0;
			if(StringUtils.isNotBlank(materialIds)){
				List<String> materialIdStrList=Utils.getListFromStr(materialIds);
				if(materialIdStrList!=null&&materialIdStrList.size()>0){
					materialId=Integer.valueOf(materialIdStrList.get(0));
				}
			}
			if(materialId!=null&&materialId>0){
				ResTexture resTexture=resTextureService.get(materialId);
				if(resTexture!=null){
					splitTextureDTOList=new ArrayList<SplitTextureDTO>();
					List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
					SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
					ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
					resTextureDTO.setKey(splitTextureDTO.getKey());
					resTextureDTO.setProductId(baseProduct.getId());
					resTextureDTOList.add(resTextureDTO);
					splitTextureDTO.setList(resTextureDTOList);
					splitTextureDTOList.add(splitTextureDTO);
					productResult.setSplitTexturesChoose(splitTextureDTOList);
					/*同时存一份数据在老的数据结构上*/
					productResult.setTextureAttrValue(resTexture.getTextureAttrValue());
					productResult.setLaymodes(resTexture.getLaymodes());
					productResult.setTextureWidth(resTexture.getFileWidth()+"");
					productResult.setTextureHeight(resTexture.getFileHeight()+"");
					String[] materialPathList=new String[]{resTextureDTO.getPath()};
					productResult.setMaterialPicPaths(materialPathList);
					/*同时存一份数据在老的数据结构上->end*/
				}
			}
		}
		productResult.setIsSplit(isSplit);
		productResult.setSplitTexturesChoose(splitTextureDTOList);
		if(splitTextureDTOList!=null&&splitTextureDTOList.size()>0
				&&splitTextureDTOList.get(0).getList()!=null&&splitTextureDTOList.get(0).getList().size()>0){
			if( designPlanProduct != null ){
				BaseProduct product = new BaseProduct();
				logger.warn("designPlanProduct.getProductId()-----------------"+designPlanProduct.getProductId());
				if(designPlanProduct.getProductId() != null && designPlanProduct.getProductId()>0){
					if(Utils.enableRedisCache()){
						product = BaseProductCacher.get(designPlanProduct.getProductId());
					}else{
						product = baseProductMapper.selectByPrimaryKey(designPlanProduct.getProductId());
					}
				}
				boolean isHard = false ;
				if( product != null ){
					isHard = isHard(product);
				}
				//平吊天花造型
				if( ("1".equals(product.getProductTypeValue()) && product.getProductSmallTypeValue() == 5)
						|| ("3".equals(product.getProductTypeValue()) && (product.getProductSmallTypeValue() == 5)) ){
					isHard = false;
				}

				// 如果产品的模型不为空，则表示该产品不是贴图产品或者
				if( StringUtils.isNotBlank(modelId) && StringUtils.isBlank(baseProduct.getBmIds()) ){
					isHard = false;
				}

				if( isHard ){
					BaseProduct baimoProduct = new BaseProduct();
					if(Utils.enableRedisCache()){
						baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
					}else{
						baimoProduct = baseProductMapper.selectByPrimaryKey(designPlanProduct.getInitProductId());
					}
					//获取不同媒介u3d模型
					String u3dModelId = getU3dModelId(loginUser.getMediaType(), baimoProduct);
					if( StringUtils.isNotBlank(u3dModelId) ){
						ResModel resModel = null;
						if(Utils.enableRedisCache())
							resModel = ResourceCacher.getModel(Integer.valueOf(u3dModelId));
						else
							resModel = resModelService.get(Integer.valueOf(u3dModelId));
						if( resModel != null ){
							productResult.setU3dModelPath(resModel.getModelPath());
							productResult.setModelLength(resModel.getLength());
							productResult.setModelWidth(resModel.getWidth());
							productResult.setModelHeight(resModel.getHeight());
							productResult.setModelMinHeight(resModel.getMinHeight());
						}
					}
				}
			}
		}
		/*产品材质路径->end*/
		//材质配置文件路径
		Integer materialConfigId = baseProduct.getMaterialFileId();
		if( materialConfigId != null ){
			ResFile resFile=null;
			if(Utils.enableRedisCache()){
				resFile = ResourceCacher.getFile(materialConfigId);
			}else{
				resFile = resFileService.get(materialConfigId);
			}
			
			if(resFile != null) {
				productResult.setMaterialConfigPath(resFile.getFilePath());
			}
		}

		productResult.setParentProductId(baseProduct.getParentId());
		
		/***在组合产品查询列表 中 增加产品属性****/
		Map<String,String> map = new HashMap<String,String>();
		map = productAttributeService.getPropertyMap(productResult.getProductId());
		productResult.setPropertyMap(map);
		Integer spaceCommonId = designPlan==null?null:designPlan.getSpaceCommonId();

//		Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
//				productResult.getProductTypeCode(),productResult.getProductSmallTypeCode(),spaceCommonId,designTempletId,new DesignRules(),map,request);
//		productResult.setRulesMap(rulesMap); 

		// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
		if( autoCarryMap != null && autoCarryMap.containsKey(productResult.getProductSmallTypeCode()) ){
			logger.info("当前产品分类："+productResult.getProductSmallTypeCode()+"需要自动携带白模信息。");
			// 获取当前产品得配置
			AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productResult.getProductSmallTypeCode());
			logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
			if( autoCarryBaimoProducts != null ){
				// 获取需要携带得白模有哪些
				JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
				List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(baimoProductsConfigArray,AutoCarryBaimoProducts.class);
				// 根据产品的属性，查询同属性的白模产品
				if( baimoProductsConfigs != null && baimoProductsConfigs.size() > 0 ){
					ProductAttribute pa1 = null;
					List<ProductAttribute> tempList = null;
					Map<String,CategoryProductResult> basicModelMap = new HashMap<>();
					// 产品属性(需要对比的属性)
					JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
					// 白模产品属性（需要对比的属性）
					JSONArray baimoProductPropertyArray = null;
					Map<String,String> baimoPropertyMap = new HashMap<>();
					for( AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs ){
						// 白模产品的属性
						baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
						if( baimoProductPropertyArray == null ){
							continue;
						}
						if( baimoProductPropertyArray.size() != productPropertyArray.size() ){
							continue;
						}
						for( Object object : baimoProductPropertyArray ){
							String[] objStr = object.toString().split("#");
							baimoPropertyMap.put(objStr[0],objStr[1]);
						}
						// 对比产品和白模产品的每个属性
						String productPropertyStr = "";
						String baimoProductPropertyStr = "";
						int i = 0;
						Map<String,Object> conditionMap = new HashMap<>();
						List<String> conditionList = new ArrayList<>();
						for( Object object : productPropertyArray ){
							StringBuffer conditionStr = new StringBuffer();
							productPropertyStr = object.toString();
							baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
							if( StringUtils.isNotBlank(baimoProductPropertyStr) ){
								Integer propValue = map.get(productPropertyStr) != null ? Integer.valueOf(map.get(productPropertyStr)):0;
								conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr + "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode() + "' AND pp.prop_value = " + propValue);
								// 2018.2.28有个bug union all 是多余的,因为在mapper.xml里面已经有个这个关键字 update by huangsongbo
								/*if( i < productPropertyArray.size() - 1 ){
									conditionStr.append(" union all ");
								}*/
							}
							i++;
							conditionList.add(conditionStr.toString());
						}
						conditionMap.put("conditionCount",conditionList.size());
						conditionMap.put("conditionList",conditionList);

						tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
						if( tempList != null && tempList.size() > 0 ) {
							logger.info("共查到"+tempList.size()+"条满足要求的白模数据！！！");
							Integer baimoProductId = tempList.get(0).getProductId();
							CategoryProductResult baimoProducts = assemblyUnityPanProduct(baimoProductId, spaceCommonId, designPlan, loginUser.getId(), loginUser.getMediaType());
							basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
						}
					}
					productResult.setBasicModelMap(basicModelMap);
				}
			}
		}
		
		return productResult;
	}
	
	@Override
	public CategoryProductResult assemblyUnityPanProduct(Integer productId,Integer spaceCommonId,DesignPlan designPlan,Integer userId, String mediaType){
		ProductCategoryRel productCategoryRel = new ProductCategoryRel();
		productCategoryRel.setProductId(productId);
		productCategoryRel.setUserId(userId);
		CategoryProductResult productResult = productCategoryRelService.getCategoryProductResultByProductId(productCategoryRel);
		if( productResult == null ){
			return productResult;
		}
		BaseProduct baseProduct=null;
		if(Utils.enableRedisCache()){
			baseProduct = BaseProductCacher.get(productId);
		}else{
			baseProduct = baseProductMapper.selectByPrimaryKey(productId);
		}
		if( baseProduct == null ){
			return productResult;
		}
		String modelId = getU3dModelId(mediaType, baseProduct);
		if(StringUtils.isNotEmpty(modelId)){
			ResModel resModel=null;
			if(Utils.enableRedisCache()){
				resModel = ResourceCacher.getModel(Integer.valueOf(modelId));
			}else{
				resModel = resModelService.get(Integer.valueOf(modelId));
			}
			if(resModel != null){
				productResult.setU3dModelPath(resModel.getModelPath());
				productResult.setProductModelPath(resModel.getModelPath());
				productResult.setModelLength(resModel.getLength());
				productResult.setModelWidth(resModel.getWidth());
				productResult.setModelHeight(resModel.getHeight());
				productResult.setModelMinHeight(resModel.getMinHeight());
			}else{
			}
		}else{
		}
		String productTypeValue2 = baseProduct.getProductTypeValue();
		if(StringUtils.isNotBlank(productTypeValue2)){
			SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue2));
			productResult.setProductTypeValue(productTypeSysDic.getValue());
			productResult.setProductTypeCode(productTypeSysDic.getValuekey());
			productResult.setProductTypeName(productTypeSysDic.getName());
			Integer productSmallTypeValue2 = baseProduct.getProductSmallTypeValue();
			if (productTypeSysDic.getValue() != null && productSmallTypeValue2 != null) {
				SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue2);
				productResult.setProductSmallTypeCode(productSmallTypeSysDic.getValuekey());
				productResult.setProductSmallTypeName(productSmallTypeSysDic.getName());
				productResult.setProductSmallTypeValue(productSmallTypeSysDic.getValue());
				String rootType = StringUtils.isEmpty(productSmallTypeSysDic.getAtt1()) ? "2" : productSmallTypeSysDic.getAtt1().trim();
				productResult.setRootType(rootType);
				//获取是否是背景墙
				if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(productTypeSysDic.getValuekey())) {
					productResult.setBgWall(Utils.getIsBgWall(productSmallTypeSysDic==null?"":productSmallTypeSysDic.getValuekey()));
				}
			}
		}
		//空间ID
		productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
		//产品长宽高
		productResult.setProductLength(baseProduct.getProductLength());
		productResult.setProductWidth(baseProduct.getProductWidth());
		productResult.setProductHeight(baseProduct.getProductHeight());
				/*产品材质路径*/
		String materialIds = productResult.getMaterialPicId();
		if(StringUtils.isNotBlank(materialIds)){
			List<String> idsInfo=Utils.getListFromStr(materialIds);
			List<String> materialPathList=new ArrayList<String>();
			ResTexture resTextureTemp=null;
			for(String idStr:idsInfo){
				ResTexture resTexture=resTextureService.get(Integer.valueOf(idStr));
				if(resTexture != null && resTextureTemp==null){
					resTextureTemp=resTexture;
					productResult.setTextureAttrValue(resTextureTemp.getTextureAttrValue());
					productResult.setLaymodes(resTextureTemp.getLaymodes());
					productResult.setTextureWidth(resTexture.getFileWidth()+"");
					productResult.setTextureHeight(resTexture.getFileHeight()+"");
				}
				if(resTexture!=null&&resTexture.getId()!=null){
					materialPathList.add(resTexture.getFilePath());
				}
			}
			if(Lists.isNotEmpty(materialPathList)){
				productResult.setMaterialPicPaths((String[]) materialPathList.toArray(new String[materialPathList.size()]));
			}
		}

		/*产品材质路径->end*/
		//材质配置文件路径
		Integer materialConfigId = baseProduct.getMaterialFileId();
		if( materialConfigId != null ){
			ResFile resFile=null;
			if(Utils.enableRedisCache()){
				resFile = ResourceCacher.getFile(materialConfigId);
			}else{
				resFile = resFileService.get(materialConfigId);
			}

			if(resFile != null) {
				productResult.setMaterialConfigPath(resFile.getFilePath());
			}
		}

		productResult.setParentProductId(baseProduct.getParentId());

		//组装产品的规则
		if( StringUtils.isBlank(baseProduct.getProductTypeMark()) || StringUtils.isBlank(baseProduct.getProductSmallType())){
			SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Utils.getIntValue(baseProduct.getProductTypeValue()));
			if( bigSd != null ){
				if(StringUtils.isNotEmpty(bigSd.getValuekey()) && baseProduct.getProductSmallTypeValue()!=null){
					SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),baseProduct.getProductSmallTypeValue());
					if( smallSd != null ){
						baseProduct.setProductSmallTypeMark(smallSd.getValuekey());
					}
				}
				baseProduct.setProductTypeMark(bigSd.getValuekey());
			}
		}

		/***在组合产品查询列表 中 增加产品属性****/
		Map<String,String> map = new HashMap<String,String>();
		map = productAttributeService.getPropertyMap(productResult.getProductId());
		productResult.setPropertyMap(map);

		Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
				baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,designPlan==null?null:designPlan.getDesignTemplateId(),new DesignRules(),map);
		productResult.setRulesMap(rulesMap);
		return productResult;
	}

	@Override
	public Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer isSplit = new Integer(0);
		List<SplitTextureDTO> splitTexturesChooseList = new ArrayList<SplitTextureDTO>();
		List<SplitTextureDTO> splitTexturesInfoList = new ArrayList<SplitTextureDTO>();
		try {
			Gson gson2 = new Gson();
			List<SplitTextureInfoDTO> SplitTextureList = gson2.fromJson(splitTexturesInfo, new TypeToken<List<SplitTextureInfoDTO>>() {}.getType());
			if (SplitTextureList != null && SplitTextureList.size() >= 1) {
				for (SplitTextureInfoDTO splitTextureInfoDTO : SplitTextureList) {
					/*处理默认材质*/
					if (StringUtils.equals("choose", type) || StringUtils.equals("all", type)) {
						Integer defaultId = splitTextureInfoDTO.getDefaultId();
						List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
						if (defaultId != null && defaultId > 0) {
							ResTexture resTexture = resTextureService.get(defaultId);
							if (resTexture != null && resTexture.getId() != null && resTexture.getId() > 0) {
								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
								resTextureDTO.setKey(splitTextureInfoDTO.getKey());
								resTextureDTO.setProductId(baseProductId);
								if( resTextureDTO.getTextureWidth() == 0 || resTextureDTO.getTextureWidth() == null ){
									resTextureDTO.setTextureWidth(80); //徐扬确认。如果材质长度为空/0则给默认80
								}
								if( resTextureDTO.getTextureHeight() == 0 || resTextureDTO.getTextureHeight() == null ){
									resTextureDTO.setTextureHeight(80); //徐扬确认。如果材质长度为空/0则给默认80
								}
								resTextureDTOList.add(resTextureDTO);
							}
						}
						splitTexturesChooseList.add(new SplitTextureDTO(splitTextureInfoDTO.getKey(), splitTextureInfoDTO.getName(),splitTextureInfoDTO.getTextureRegionName(), resTextureDTOList));
					}
					/*处理默认材质->end*/
					/*处理可选材质*/
					if (StringUtils.equals("info", type) || StringUtils.equals("all", type)) {
						String textureIdsStr = splitTextureInfoDTO.getTextureIds();
						List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
						List<String> textureIdStrList = Utils.getListFromStr(textureIdsStr);
						if (textureIdStrList != null && textureIdStrList.size() > 0) {
							List<ResTexture> textureList = null;
							/*优化后*/
							ResTexture resTexture_ = new ResTexture();
							resTexture_.setResTextureIds(textureIdStrList);
							textureList = resTextureService.getBatchGet(resTexture_);
							// 按照textureIdStrList排序 ->start
							List<ResTexture> listNew = new ArrayList<ResTexture>();
							// textureIdStrList:[406, 407, 409, 410]
							// 将默认材质和textureIdStrList第一个元素互换位置 ->start
							Integer defaultId = splitTextureInfoDTO.getDefaultId();
							if(defaultId != null){
								int index = textureIdStrList.indexOf(defaultId + "");
								if(index != -1){
									String item = textureIdStrList.get(0);
									textureIdStrList.set(0, defaultId + "");
									textureIdStrList.set(index, item);
								}
							}
							// 将默认材质和textureIdStrList第一个元素互换位置 ->end
							if(textureIdStrList != null && textureIdStrList.size() > 0){
								for(int i = 0; i < textureIdStrList.size(); i ++){
									listNew.add(null);
								}
							}
							if(textureList != null && textureList.size() > 0){
								for(ResTexture resTexture : textureList){
									if(textureIdStrList.indexOf("" + resTexture.getId()) != -1){
										listNew.set(textureIdStrList.indexOf("" + resTexture.getId()), resTexture);
									}
								}
							}
							/*listNew.remove(null);*/
							textureList = listNew;
							// 按照textureIdStrList排序 ->end
							for (ResTexture resTexture : textureList) {
								if(resTexture == null){
									continue;
								}
								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
								resTextureDTO.setKey(splitTextureInfoDTO.getKey());
								resTextureDTO.setProductId(baseProductId);
								if(resTextureDTO.getTextureWidth() == null || resTextureDTO.getTextureWidth() == 0){
									resTextureDTO.setTextureWidth(80); //徐扬确认。如果材质长度为空/0则给默认80
								}
								if(resTextureDTO.getTextureHeight() == null || resTextureDTO.getTextureHeight() == 0){
									resTextureDTO.setTextureHeight(80); //徐扬确认。如果材质长度为空/0则给默认80
								}
								resTextureDTOList.add(resTextureDTO);
							}
							
							 /*for(String textureIdStr:textureIdStrList){
								ResTexture resTexture=resTextureService.get(Integer.valueOf(textureIdStr));
								if(resTexture!=null){
									SplitTextureDTO.ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
									resTextureDTO.setKey(splitTextureInfoDTO.getKey());
									resTextureDTO.setProductId(baseProductId);
									resTextureDTOList.add(resTextureDTO);
								}
							}*/
							splitTexturesInfoList.add(new SplitTextureDTO(splitTextureInfoDTO.getKey(), splitTextureInfoDTO.getName(),splitTextureInfoDTO.getTextureRegionName(), resTextureDTOList));
						}
					}

					/*处理可选材质->end*/
						}
						isSplit = new Integer(1);
						map.put("isSplit", isSplit);
						map.put("splitTexturesChoose", splitTexturesChooseList);
						map.put("splitTexturesInfo", splitTexturesInfoList);
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.warn("------baseProduct的SplitTextureInfo信息格式错误,SplitTextureInfo:" + splitTexturesInfo);
					return map;
				}
				return map;
			}

			/**
			 * 通过产品code查找产品
			 * @author huangsongbo
			 * @param productCode
			 * @return
			 */
		public BaseProduct findOneByCode (String productCode){
			BaseProductSearch baseProductSearch = new BaseProductSearch();
			baseProductSearch.setStart(new Integer(0));
			baseProductSearch.setLimit(new Integer(1));
			baseProductSearch.setIsDeleted(0);
			baseProductSearch.setProductCode(productCode);
			List<BaseProduct> list = this.getPaginatedList(baseProductSearch);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		}

		/**
		 * BaseProduct->StructureProductDetailsSearch
		 * @author huangsongbo
		 * @param baseProduct
		 * @return
		 */
		public SearchStructureProductDetailResult getStructureDetailsSearch (BaseProduct baseProduct, String mediaType){
			SearchStructureProductDetailResult searchStructureProductDetailResult = new SearchStructureProductDetailResult();
			searchStructureProductDetailResult.setProductId(baseProduct.getId());
			searchStructureProductDetailResult.setProductCode(baseProduct.getProductCode());
			searchStructureProductDetailResult.setProductWidth(baseProduct.getProductWidth());
			searchStructureProductDetailResult.setProductLength(baseProduct.getProductLength());
			searchStructureProductDetailResult.setProductHeight(baseProduct.getProductHeight());
			/*u3dModelPath*/
			String modelId = this.getU3dModelId(mediaType, baseProduct);
			if (StringUtils.isNotBlank(modelId)) {
				ResModel resModel = resModelService.get(Integer.valueOf(modelId));
				if (resModel != null) {
					/*File modelFile = new File(Utils.getValue("app.upload.root", "") + resModel.getModelPath());*/
					File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
					if (modelFile.exists()) {
						searchStructureProductDetailResult.setU3dModelPath(resModel.getModelPath());
						searchStructureProductDetailResult.setModelMinHeight(resModel.getMinHeight());
					}
				}
			}
		/*u3dModelPath->end*/
		/*大类value,code,name*/
			String typeValue = baseProduct.getProductTypeValue();
			SysDictionary bigTypeDic = null;
			if (StringUtils.isNotBlank(typeValue)) {
				bigTypeDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
				searchStructureProductDetailResult.setProductTypeValue(bigTypeDic.getValue());
				searchStructureProductDetailResult.setProductTypeCode(bigTypeDic.getValuekey());
				searchStructureProductDetailResult.setProductTypeName(bigTypeDic.getName());
			}
		/*大类value,code,name->end*/
		/*rootType,小类value,code,name*/
			Integer smallType = baseProduct.getProductSmallTypeValue();
			if (smallType != null && smallType.intValue() > 0) {
				if (bigTypeDic != null) {
					SysDictionary smallTypeDic = sysDictionaryService.findOneByTypeAndValue(bigTypeDic.getValuekey(), smallType);
					searchStructureProductDetailResult.setProductSmallTypeValue(smallTypeDic.getValue().toString());
					searchStructureProductDetailResult.setProductSmallTypeCode(smallTypeDic.getValuekey());
					searchStructureProductDetailResult.setProductSmallTypeName(smallTypeDic.getName());
					String rootType = StringUtils.isEmpty(smallTypeDic.getAtt1()) ? "2" : smallTypeDic.getAtt1().trim();
					searchStructureProductDetailResult.setRootType(rootType);
				}
			}
		/*rootType,小类value,code,name->end*/
		/*封面图片路径*/
			Integer picId = baseProduct.getPicId();
			if (picId != null && picId > 0) {
				ResPic resPic = resPicService.get(picId);
				if (resPic != null) {
					searchStructureProductDetailResult.setPicPath(resPic.getPicPath());
				}
			}
		/*封面图片路径->end*/
			return searchStructureProductDetailResult;
		}


		@Override
		public BaseProduct getBaseProductFromAuthorizedConfig (AuthorizedConfig authorizedConfig){
			BaseProduct baseProduct = new BaseProduct();
			/*去重复,序列号只能绑定一个品牌,一个大类,一个小类,因对于以前的错误数据*/
			authorizedConfigService.dealWithRepeat(authorizedConfig);
			/*品牌*/
			String brandId = authorizedConfig.getBrandIds();
			Integer brandIdInt = null;
			try {
				brandIdInt = Integer.valueOf(brandId);
			}catch (Exception e) {
				
			}
			if(brandIdInt != null) {
				BaseBrand baseBrand = baseBrandService.get(brandIdInt);
				if(baseBrand != null) {
					baseProduct.setStatusShowWu(baseBrand.getStatusShowWu());
				}
			}
			if (StringUtils.isNotBlank(brandId))
				baseProduct.setBrandId(Integer.parseInt(brandId));
			/*品牌->end*/
			/*大类*/
			String bigType = authorizedConfig.getBigType();
			if (StringUtils.isNotBlank(bigType)) {
				SysDictionary sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey("productType", bigType);
				if (sysDictionaryBigType == null) {
					logger.info("------数据字典未找到:(type:productType,valuekey:" + bigType + ")");
				} else {
					baseProduct.setProductTypeValue("" + sysDictionaryBigType.getValue());
				}
			}
			/*大类->end*/
			/*小类*/
			String smallType = authorizedConfig.getSmallTypeIds();
			if (StringUtils.isNotBlank(smallType)) {
				SysDictionary sysDictionarySmallType = sysDictionaryService.get(Integer.parseInt(smallType));
				if (sysDictionarySmallType == null) {
					logger.info("------数据字典未找到:(id:" + smallType + ")");
				} else {
					baseProduct.setProductSmallTypeValue(sysDictionarySmallType.getValue());
				}
			}
			/*小类->end*/
			/*产品ids*/
			String productIds = authorizedConfig.getProductIds();
			List<Integer> productIdList = new ArrayList<Integer>();
			if (StringUtils.isNotBlank(productIds)) {
				List<String> productIdStrList = Utils.getListFromStr(productIds);
				if (productIdStrList != null && productIdStrList.size() > 0) {
					for (String productIdStr : productIdStrList) {
						productIdList.add(Integer.parseInt(productIdStr));
					}
				}
			}
			baseProduct.setProductIdList(productIdList);
			/*产品ids->end*/
			return baseProduct;
		}


	/**
	 * 设置查询条件,从序列号信息中获取
	 * @param bigType
	 * @param smallType
	 * @param productIds
	 * @return
	 */
	@Override
	public BrandIndustryModel getBrandIndustryModelbyAuthorizedInfo (String bigType, String smallType, String productIds){
		BrandIndustryModel brandIndustryModel = new BrandIndustryModel();

		if (StringUtils.isNotBlank(bigType)) {
			SysDictionary sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.SYS_DICTIONARY_PRODUCT_TYPE, bigType);
			if (sysDictionaryBigType == null) {
				logger.info("------数据字典未找到:(type:productType,valuekey:" + bigType + ")");
			} else {
				brandIndustryModel.setAuthorizedProductTypeValue("" + sysDictionaryBigType.getValue());
			}
		}
		if (StringUtils.isNotBlank(smallType)) {
			SysDictionary sysDictionarySmallType = sysDictionaryService.get(Integer.parseInt(smallType));
			if (sysDictionarySmallType == null) {
				logger.info("------数据字典未找到:(id:" + smallType + ")");
			} else {
				brandIndustryModel.setAuthorizedProductSmallTypeValue(sysDictionarySmallType.getValue()+"");
			}
		}

		List<Integer> productIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(productIds)) {
			List<String> productIdStrList = Utils.getListFromStr(productIds);
			if (productIdStrList != null && productIdStrList.size() > 0) {
				for (String productIdStr : productIdStrList) {
					productIdList.add(Integer.parseInt(productIdStr));
				}
			}
		}
		brandIndustryModel.setAuthorizedProductIdList(productIdList);
		return brandIndustryModel;
	}

		@Override
		public int selectBaseCount (BaseProductSearch baseProductSearch){
			int result = baseProductMapper.selectBaseCount(baseProductSearch);
			return result;
		}

		@Override
		public BaseProduct selectByProductCode (String code){
			BaseProduct baseProduct = baseProductMapper.selectByProductCode(code);
			return baseProduct;
		}

		@Override
		public int selectProCount (BaseProductSearch baseProductSearch){
			int result = baseProductMapper.selectProCount(baseProductSearch);
			return result;
		}

		@Override
		public List<BaseProduct> selectProPaginatedList (BaseProductSearch baseProductSearch){
			List<BaseProduct> list = baseProductMapper.selectProPaginatedList(baseProductSearch);
			return list;
		}

		/**
		 * 得到黑名单配置
		 * @author huangsongbo
		 * @return
		 */
		public Set<String> getBlacklist (Integer userId){
			/*获得黑名单配置开关*/
			Integer categoryBlacklistEnable = 0;
			SysDictionary sysDictionary1 = sysDictionaryService.findOneByTypeAndValueKey("configuration", "categoryBlacklistEnable");
			if (sysDictionary1 != null) {
				try {
					categoryBlacklistEnable = Integer.valueOf(sysDictionary1.getAtt1());
				} catch (Exception e) {
					throw new RuntimeException("配置格式错误,type=configuration,valueKey=categoryBlacklistEnable,att1=" + sysDictionary1.getAtt1());
				}
			}
			Set<String> blacklist = new HashSet<String>();
			if (categoryBlacklistEnable == 1) {
				/*获取黑名单配置*/
				String categoryBlacklist = "";
				SysDictionary sysDictionary2 = sysDictionaryService.findOneByTypeAndValueKey("configuration", "categoryBlacklist");
				if (sysDictionary2 != null && sysDictionary2.getAtt1() != null)
					categoryBlacklist = sysDictionary2.getAtt1();
				Map<String, Set<String>> categoryBlacklistMap = Utils.getCategoryBlacklistMap(categoryBlacklist);//{CW_jj=[dddd, xxxx, !xingx]}
				/*处理类似于"!xingx"的值*/
				categoryBlacklistMap = this.dealWithMap(categoryBlacklistMap);
				/*得到用户所属类型:序列号->公司->小类*/
				Set<String> typeSet = baseCompanyService.getTypeSetByUserId(userId);
				/*获取产品类型黑名单(取交集)*/
				boolean flag = false;
				for (String typeStr : typeSet) {
					if (categoryBlacklistMap.containsKey(typeStr))
						if (!flag) {
							flag = true;
							blacklist.addAll(categoryBlacklistMap.get(typeStr));
						} else {
							blacklist.retainAll(categoryBlacklistMap.get(typeStr));
						}
				}
			}
			return blacklist;
		}

		/**
		 * 从序列号获取查询条件
		 * @author huangsongbo
		 * @return
		 */
		public List<BaseProduct> getSelectConditionByAuthorizedConfig (LoginUser loginUser){
			//获取该用户绑定的序列号品牌
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			if (Utils.enableRedisCache()) {
				authorizedList = AuthorizedConfigCacher.getList(authorizedConfig);
			} else {
				authorizedList = authorizedConfigService.getList(authorizedConfig);
			}
			//获取该用户绑定的序列号品牌->end
			
			//add品牌,大类,小类,产品查询条件
			List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
			if (3 == loginUser.getUserType()) {
				if (authorizedList != null && authorizedList.size() > 0) {
					for (AuthorizedConfig authorizedConfigItem : authorizedList) {
					/*查询条件注入到BaseProduct类中*/
						BaseProduct baseProduct = this.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
						baseProductList.add(baseProduct);
					}
				}
			}
			//add品牌,大类,小类,产品查询条件->end
			return baseProductList;
		}

		/**
		 * 从序列号获取查询条件V2
		 * @author huangsongbo
		 * @return
		 */
		public List<BaseProduct> getSelectConditionByAuthorizedConfigV2(LoginUser loginUser){
			//获取该用户绑定的序列号品牌
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			authorizedList = authorizedConfigService.getList(authorizedConfig);
			//获取该用户绑定的序列号品牌->end
			
			//add品牌,大类,小类,产品查询条件
			List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
			if (authorizedList != null && authorizedList.size() > 0) {
				for (AuthorizedConfig authorizedConfigItem : authorizedList) {
					/*查询条件注入到BaseProduct类中*/
					BaseProduct baseProduct = this.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
					
					// 排除小类查询条件处理,设置 ->start
					// 比如授权码关联的东鹏公司是做墙面瓷砖的,则点击墙面搜索,瓷砖小类只显示东鹏的瓷砖+无品牌瓷砖,其他小类则显示全部
					// test ->start
					List<Integer> smallTypeValueListForShowAll = new ArrayList<Integer>();
					smallTypeValueListForShowAll.add(1);
					// test ->end
					// 排除小类查询条件处理,设置 ->end
					baseProduct.setSmallTypeValueListForShowAll(smallTypeValueListForShowAll);
					baseProductList.add(baseProduct);
				}
			}
			//add品牌,大类,小类,产品查询条件->end
			return baseProductList;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public CategoryProductResult decorationProductInfoV2 (CategoryProductResult productResult, BaseProduct
		baseProduct, DesignPlan designPlan,
				DesignPlanProduct designPlanProduct, Map < String, AutoCarryBaimoProducts > autoCarryMap, LoginUser
		loginUser, HttpServletRequest request){
		/*String key = "decorationProductInfoV2_productId_"+baseProduct.getId()+"_designTempletId_"+designPlan.getDesignTemplateId()
			+"_InitProductId_"+designPlanProduct.getInitProductId()+"_autoCarryMap_"+autoCarryMap.toString();
		CategoryProductResult productResultCache = (CategoryProductResult) CacheManager.getInstance().getCacher().getObject(key);
		if(productResultCache==null){*/
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			//一次性查出产品大小类信息,材质配置文件,模型,是否是组合

			//定制产品信息;1:是;0:否->start
			if (baseProduct.getBmIds() != null) {
				productResult.setIsCustomized(1);
			}
			//定制产品信息;1:是;0:否->end

			Long startTime = new Date().getTime();
			//是否是主产品信息->start
			/*Integer isMainProduct=groupProductService.getIsMainProduct(baseProduct.getId(), cacheEnable);*/
			Integer isMainProduct = groupProductService.getIsMainProductV2(baseProduct.getId());
			productResult.setIsMainProduct(isMainProduct);
			//是否是主产品信息->end
			Long endTime = new Date().getTime();
			logger.warn("------是否是主产品:" + (endTime - startTime));

			startTime = new Date().getTime();
			//大类,小类,软硬装->start
			String bigTypeValueStr = baseProduct.getProductTypeValue();
			if (StringUtils.isNotBlank(bigTypeValueStr)) {
				productResult.setProductTypeValue(Integer.valueOf(bigTypeValueStr));
			}
			this.setTypeMsg(productResult, request);
			//大类,小类,软硬装->end
			endTime = new Date().getTime();
			logger.warn("------大小类信息:" + (endTime - startTime));

			//是否是背景墙->start
			Integer bgWallValue = this.getBgWallValue(productResult.getProductTypeCode(),productResult.getProductSmallTypeCode());
			productResult.setBgWall(bgWallValue);
			//是否是背景墙->end

			//空间ID->start
			productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
			//空间ID->end

			//产品长宽高->start
			productResult.setProductLength(baseProduct.getProductLength());
			productResult.setProductWidth(baseProduct.getProductWidth());
			productResult.setProductHeight(baseProduct.getProductHeight());
			productResult.setMinHeight(baseProduct.getMinHeight());
			//产品长宽高->end

			startTime = new Date().getTime();
			//材质信息->start
			this.setTextureInfo(productResult, baseProduct);
			//材质信息->end
			endTime = new Date().getTime();
			logger.warn("------材质信息:" + (endTime - startTime));

			startTime = new Date().getTime();
			//u3d模型相关信息->start
			ResModel resModel = this.setmodelInfo(baseProduct, designPlanProduct.getInitProductId(), mediaType);
			if (resModel != null) {
				productResult.setU3dModelPath(resModel.getModelPath());
				productResult.setProductModelPath(resModel.getModelPath());
				productResult.setModelLength(resModel.getLength());
				productResult.setModelWidth(resModel.getWidth());
				productResult.setModelHeight(resModel.getHeight());
				productResult.setModelMinHeight(resModel.getMinHeight());
				productResult.setModelProductId(baseProduct.getId());
			} else {
				logger.info("resModel is null;productId=" + baseProduct.getId() + ";");
			}
			//u3d模型相关信息->end
			endTime = new Date().getTime();
			logger.info("------u3d模型信息:" + (endTime - startTime));

			//材质配置文件路径->start
			Integer materialConfigId = baseProduct.getMaterialFileId();
			if (materialConfigId != null) {
				ResFile resFile = null;
				if (Utils.enableRedisCache()) {
					resFile = ResourceCacher.getFile(materialConfigId);
				} else {
					resFile = resFileService.get(materialConfigId);
				}

				if (resFile != null) {
					productResult.setMaterialConfigPath(resFile.getFilePath());
				}
			}
			//材质配置文件路径->end

			//同类型新增产品设置主产品id->start
			productResult.setParentProductId(baseProduct.getParentId());
			//同类型新增产品设置主产品id->end

			startTime = new Date().getTime();
			//产品附加属性->start
			Map<String, String> map = new HashMap<String, String>();
			map = productAttributeService.getPropertyMap(productResult.getProductId());
			productResult.setPropertyMap(map);
			//产品附加属性->end
			endTime = new Date().getTime();
			logger.warn("------产品附加属性:" + (endTime - startTime));

			//设计规则->start
			startTime = new Date().getTime();
			Integer spaceCommonId = designPlan == null ? null : designPlan.getSpaceCommonId();
			Integer designTempletId = designPlan == null ? null : designPlan.getDesignTemplateId();
			Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
					productResult.getProductTypeCode(), productResult.getProductSmallTypeCode(), spaceCommonId, designTempletId, new DesignRules(), map);
			productResult.setRulesMap(rulesMap);
			/*CacheManager.getInstance().getCacher().setObject(key, productResult, 0);*/
			endTime = new Date().getTime();
			logger.info("------规则:" + (endTime - startTime));
			//设计规则->end
		/*}else{
			productResult = productResultCache;
		}*/


			startTime = new Date().getTime();
		/*Integer spaceCommonId = designPlan==null?null:designPlan.getSpaceCommonId();
		Map<String,String> map=productResult.getPropertyMap();*/
			// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
			if (autoCarryMap != null && autoCarryMap.containsKey(productResult.getProductSmallTypeCode())) {
				logger.info("当前产品分类：" + productResult.getProductSmallTypeCode() + "需要自动携带白模信息。");
				// 获取当前产品得配置
				AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productResult.getProductSmallTypeCode());
				logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
				if (autoCarryBaimoProducts != null) {
					// 获取需要携带得白模有哪些
					JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
					List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(baimoProductsConfigArray, AutoCarryBaimoProducts.class);
					// 根据产品的属性，查询同属性的白模产品
					if (baimoProductsConfigs != null && baimoProductsConfigs.size() > 0) {
						ProductAttribute pa1 = null;
						List<ProductAttribute> tempList = null;
						Map<String, CategoryProductResult> basicModelMap = new HashMap<>();
						// 产品属性(需要对比的属性)
						JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
						// 白模产品属性（需要对比的属性）
						JSONArray baimoProductPropertyArray = null;
						Map<String, String> baimoPropertyMap = new HashMap<>();
						for (AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs) {
							// 白模产品的属性
							baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
							if (baimoProductPropertyArray == null) {
								continue;
							}
							if (baimoProductPropertyArray.size() != productPropertyArray.size()) {
								continue;
							}
							for (Object object : baimoProductPropertyArray) {
								String[] objStr = object.toString().split("#");
								baimoPropertyMap.put(objStr[0], objStr[1]);
							}
							// 对比产品和白模产品的每个属性
							String productPropertyStr = "";
							String baimoProductPropertyStr = "";
							int i = 0;
							Map<String, Object> conditionMap = new HashMap<>();
							List<String> conditionList = new ArrayList<>();
							StringBuffer conditionStr = new StringBuffer();
							for (Object object : productPropertyArray) {
								productPropertyStr = object.toString();
								baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
								if (StringUtils.isNotBlank(baimoProductPropertyStr)) {
									Integer propValue = map.get(productPropertyStr) != null ? Integer.valueOf(map.get(productPropertyStr)) : 0;
									conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr + "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode() + "' AND pp.prop_value = " + propValue);
									if (i < productPropertyArray.size() - 1) {
										conditionStr.append(" union all ");
									}
								}
								i++;
								conditionList.add(conditionStr.toString());
							}
							conditionMap.put("conditionCount", conditionList.size());
							conditionMap.put("conditionList", conditionList);

							tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
							if (tempList != null && tempList.size() > 0) {
								logger.info("共查到" + tempList.size() + "条满足要求的白模数据！！！");
								Integer baimoProductId = tempList.get(0).getProductId();
								CategoryProductResult baimoProducts = assemblyUnityPanProduct(baimoProductId, spaceCommonId, designPlan, loginUser.getId(), loginUser.getMediaType());
								basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
							}
						}
						productResult.setBasicModelMap(basicModelMap);
					}
				}
			}
			endTime = new Date().getTime();
			logger.info("------other:" + (endTime - startTime));
			return productResult;
		}

	public CategoryProductResult decorationProductInfoV3 (CategoryProductResult productResult,
														  BaseProduct baseProduct, DesignPlan designPlan, DesignPlanProduct designPlanProduct,
														  Map < String, AutoCarryBaimoProducts > autoCarryMap, String mediaType){
		//FIXME: 写死移动端的  设备类型 设置为PC端一致
		if("MOBILE".equals(mediaType)) {
			mediaType = "3";
		}
		// 定制产品信息;1:是;0:否->start
		if (baseProduct.getBmIds() != null) {
			productResult.setIsCustomized(1);
		}
		// 定制产品信息;1:是;0:否->end

		// 是否是主产品信息->start
		Integer groupCount = baseProduct.getGroupCount();
		productResult.setIsMainProduct((groupCount != null && groupCount > 0) ? 1 : 0);
		// 是否是主产品信息->end

		String bigTypeValueStr = baseProduct.getProductTypeValue();
		Integer bigTypeValue = null;
		if (StringUtils.isNotBlank(bigTypeValueStr)) {
			bigTypeValue = Integer.valueOf(bigTypeValueStr);
		}
		productResult.setProductTypeValue(bigTypeValue);
		productResult.setProductTypeCode(baseProduct.getBigTypeValueKey());
		productResult.setProductTypeName(baseProduct.getBigTypeName());
		productResult.setProductSmallTypeCode(baseProduct.getSmallTypeValueKey());
		productResult.setProductSmallTypeName(baseProduct.getSmallTypeName());
		productResult.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		String smallTypeAtt1 = baseProduct.getSmallTypeAtt1().trim();
		String rootType = StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1;
		productResult.setRootType(rootType);
		// 收藏状态
		productResult.setCollectState(baseProduct.getCollectState());
		// 大类,小类,软硬装->end

		// 是否是背景墙->start
		Integer bgWallValue = this.getBgWallValue(productResult.getProductTypeCode(),productResult.getProductSmallTypeCode());
		productResult.setBgWall(bgWallValue);
		// 是否是背景墙->end

		// 空间ID->start
		productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
		// 空间ID->end

		// 产品长宽高->start
		productResult.setProductLength(baseProduct.getProductLength());
		productResult.setProductWidth(baseProduct.getProductWidth());
		productResult.setProductHeight(baseProduct.getProductHeight());
		productResult.setMinHeight(baseProduct.getMinHeight());
		// 产品长宽高->end

		// 材质信息->start
		this.setTextureInfo(productResult, baseProduct);
		// 材质信息->end

		// u3d模型相关信息->start
		ResModel resModel = this.setmodelInfo(baseProduct, designPlanProduct==null?null:designPlanProduct.getInitProductId(), mediaType);
		if (resModel != null) {
			productResult.setU3dModelPath(resModel.getModelPath());
			productResult.setProductModelPath(resModel.getModelPath());
			productResult.setModelLength(resModel.getLength());
			productResult.setModelWidth(resModel.getWidth());
			productResult.setModelHeight(resModel.getHeight());
			productResult.setModelMinHeight(resModel.getMinHeight());
			productResult.setModelProductId(baseProduct.getId());
		} else {
			logger.info("resModel is null;productId=" + baseProduct.getId() + ";");
		}
		// u3d模型相关信息->end

		// 同类型新增产品设置主产品id->start
		productResult.setParentProductId(baseProduct.getParentId());
		// 同类型新增产品设置主产品id->end

		// 产品附加属性->start
		Map<String, String> map = new HashMap<String, String>();
		map = productAttributeService.getPropertyMap(productResult.getProductId());
		productResult.setPropertyMap(map);
		// 产品附加属性->end

		// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
		// 自动携带白膜属性(未优化) ->start
		this.setBasicModelMap(productResult, autoCarryMap, map, designPlan, designPlanProduct, mediaType);
		// 自动携带白膜属性(未优化) ->end

		this.removeNull(productResult);
		return productResult;
	}


	public CategoryProductResult decorationProductInfoV3 (CategoryProductResult productResult, BaseProduct
		baseProduct, DesignPlan designPlan,
				DesignPlanProduct designPlanProduct, Map < String, AutoCarryBaimoProducts > autoCarryMap,
				/*LoginUserloginUser,*/
				HttpServletRequest request){
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			//FIXME: 写死移动端的  设备类型 设置为PC端一致
			if("MOBILE".equals(mediaType)) {
				mediaType = "3";
			}
			// 一次性查出产品大小类信息,材质配置文件,模型,是否是组合

			// 定制产品信息;1:是;0:否->start
			if (baseProduct.getBmIds() != null) {
				productResult.setIsCustomized(1);
			}
			// 定制产品信息;1:是;0:否->end

			Long startTime = new Date().getTime();
			// 是否是主产品信息->start
		/*Integer isMainProduct=groupProductService.getIsMainProductV2(baseProduct.getId());
		productResult.setIsMainProduct(isMainProduct);*/
			Integer groupCount = baseProduct.getGroupCount();
			productResult.setIsMainProduct((groupCount != null && groupCount > 0) ? 1 : 0);
			// 是否是主产品信息->end
			Long endTime = new Date().getTime();
		/*logger.warn("------是否是主产品:" + (endTime - startTime));*/

			startTime = new Date().getTime();
			// 大类,小类,软硬装->start
		/*String bigTypeValueStr = baseProduct.getProductTypeValue();
		if (StringUtils.isNotBlank(bigTypeValueStr)) {
			productResult.setProductTypeValue(Integer.valueOf(bigTypeValueStr));
		}
		this.setTypeMsg(productResult, request);*/
			String bigTypeValueStr = baseProduct.getProductTypeValue();
			Integer bigTypeValue = null;
			if (StringUtils.isNotBlank(bigTypeValueStr)) {
				bigTypeValue = Integer.valueOf(bigTypeValueStr);
			}
			productResult.setProductTypeValue(bigTypeValue);
			productResult.setProductTypeCode(baseProduct.getBigTypeValueKey());
			productResult.setProductTypeName(baseProduct.getBigTypeName());
			productResult.setProductSmallTypeCode(baseProduct.getSmallTypeValueKey());
			productResult.setProductSmallTypeName(baseProduct.getSmallTypeName());
			productResult.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
			String smallTypeAtt1 = baseProduct.getSmallTypeAtt1().trim();
			String rootType = StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1;
			productResult.setRootType(rootType);
			// 收藏状态
			productResult.setCollectState(baseProduct.getCollectState());
			// 大类,小类,软硬装->end
			endTime = new Date().getTime();
		/*logger.warn("------大小类信息:" + (endTime - startTime));*/

			// 是否是背景墙->start
			Integer bgWallValue = this.getBgWallValue(productResult.getProductTypeCode(),productResult.getProductSmallTypeCode());
			productResult.setBgWall(bgWallValue);
			// 是否是背景墙->end

			// 空间ID->start
			productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
			// 空间ID->end

			// 产品长宽高->start
			productResult.setProductLength(baseProduct.getProductLength());
			productResult.setProductWidth(baseProduct.getProductWidth());
			productResult.setProductHeight(baseProduct.getProductHeight());
			productResult.setMinHeight(baseProduct.getMinHeight());
			// 产品长宽高->end

			startTime = new Date().getTime();
			// 材质信息->start
			this.setTextureInfo(productResult, baseProduct);
			// 材质信息->end
			endTime = new Date().getTime();
		/*logger.warn("------材质信息:" + (endTime - startTime));*/

			startTime = new Date().getTime();
			// u3d模型相关信息->start
			ResModel resModel = this.setmodelInfo(baseProduct, designPlanProduct==null?null:designPlanProduct.getInitProductId(), mediaType);
			if (resModel != null) {
				productResult.setU3dModelPath(resModel.getModelPath());
				productResult.setProductModelPath(resModel.getModelPath());
				productResult.setModelLength(resModel.getLength());
				productResult.setModelWidth(resModel.getWidth());
				productResult.setModelHeight(resModel.getHeight());
				productResult.setModelMinHeight(resModel.getMinHeight());
				productResult.setModelProductId(baseProduct.getId());
			} else {
				logger.info("resModel is null;productId=" + baseProduct.getId() + ";");
			}
			// u3d模型相关信息->end
			endTime = new Date().getTime();
		/*logger.warn("------u3d模型信息:" + (endTime - startTime));*/

			// 同类型新增产品设置主产品id->start
			productResult.setParentProductId(baseProduct.getParentId());
			// 同类型新增产品设置主产品id->end

			startTime = new Date().getTime();
			// 产品附加属性->start
			Map<String, String> map = new HashMap<String, String>();
			map = productAttributeService.getPropertyMap(productResult.getProductId());
			productResult.setPropertyMap(map);
			// 产品附加属性->end
			endTime = new Date().getTime();
		/*logger.warn("------产品附加属性:" + (endTime - startTime));*/

		// 设计规则->start
		/*startTime = new Date().getTime();
		Integer spaceCommonId = designPlan == null ? null : designPlan.getSpaceCommonId();
		Integer designTempletId = designPlan == null ? null : designPlan.getDesignTemplateId();
		Map<String, String> rulesMap = designRulesService.getRulesSecondaryListV3(baseProduct,spaceCommonId,designTempletId, new DesignRules(), map);*/
//		Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
//		productResult.getProductTypeCode(),productResult.getProductSmallTypeCode(),spaceCommonId,designTempletId,new DesignRules(),map,request);
		/*productResult.setRulesMap(rulesMap);
		endTime = new Date().getTime();
		logger.warn("------规则:" + (endTime - startTime));*/
		// 设计规则->end

			startTime = new Date().getTime();
			// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
			if (autoCarryMap != null && autoCarryMap.containsKey(productResult.getProductSmallTypeCode())) {
				logger.info("当前产品分类：" + productResult.getProductSmallTypeCode() + "需要自动携带白模信息。");
				// 获取当前产品得配置
				AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productResult.getProductSmallTypeCode());
				logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
				if (autoCarryBaimoProducts != null) {
					// 获取需要携带得白模有哪些
					JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
					List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
							.toCollection(baimoProductsConfigArray, AutoCarryBaimoProducts.class);
					// 根据产品的属性，查询同属性的白模产品
					if (baimoProductsConfigs != null && baimoProductsConfigs.size() > 0) {
						List<ProductAttribute> tempList = null;
						Map<String, CategoryProductResult> basicModelMap = new HashMap<>();
						// 产品属性(需要对比的属性)
						JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
						// 白模产品属性（需要对比的属性）
						JSONArray baimoProductPropertyArray = null;
						Map<String, String> baimoPropertyMap = new HashMap<>();
						for (AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs) {
							// 白模产品的属性
							baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
							if (baimoProductPropertyArray == null) {
								continue;
							}
							if (baimoProductPropertyArray.size() != productPropertyArray.size()) {
								continue;
							}
							for (Object object : baimoProductPropertyArray) {
								String[] objStr = object.toString().split("#");
								baimoPropertyMap.put(objStr[0], objStr[1]);
							}
							// 对比产品和白模产品的每个属性
							String productPropertyStr = "";
							String baimoProductPropertyStr = "";
							int i = 0;
							Map<String, Object> conditionMap = new HashMap<>();
							List<String> conditionList = new ArrayList<>();
							StringBuffer conditionStr = new StringBuffer();
							for (Object object : productPropertyArray) {
								productPropertyStr = object.toString();
								baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
								if (StringUtils.isNotBlank(baimoProductPropertyStr)) {
									Integer propValue = map.get(productPropertyStr) != null
											? Integer.valueOf(map.get(productPropertyStr)) : 0;
									conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr
											+ "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode()
											+ "' AND pp.prop_value = " + propValue);
									if (i < productPropertyArray.size() - 1) {
										conditionStr.append(" union all ");
									}
								}
								i++;
								conditionList.add(conditionStr.toString());
							}
							conditionMap.put("conditionCount", conditionList.size());
							conditionMap.put("conditionList", conditionList);

							tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
							if (tempList != null && tempList.size() > 0) {
								logger.info("共查到" + tempList.size() + "条满足要求的白模数据！！！");
								Integer baimoProductId = tempList.get(0).getProductId();
								/*CategoryProductResult baimoProducts = assemblyUnityPanProduct(baimoProductId, spaceCommonId,
									designPlan, loginUser.getId(), request);*/
								ProductCategoryRel productCategoryRel = new ProductCategoryRel();
								productCategoryRel.setProductId(baimoProductId);
								/*productCategoryRel.setUserId(loginUser.getId());*/
								CategoryProductResult productResultBaimo = productCategoryRelService.getCategoryProductResultByProductId(productCategoryRel);
								Map<String,Integer> InfoByIdMap = new HashMap<>();
								InfoByIdMap.put("id",productResultBaimo.getProductId());
								InfoByIdMap.put("designTempletId",designPlan.getDesignTemplateId());
								BaseProduct baseProductBaimo = this.getInfoById(InfoByIdMap);
								CategoryProductResult baimoProducts = decorationProductInfoV3(productResultBaimo, baseProductBaimo, designPlan, designPlanProduct, autoCarryMap,
										/*loginUser,*/
										request);
								basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
							}
						}
						productResult.setBasicModelMap(basicModelMap);
					}
				}
			}
			endTime = new Date().getTime();
			/*logger.warn("------other:" + (endTime - startTime));*/
			this.removeNull(productResult);
			return productResult;
		}

		public CategoryProductResult decorationProductInfoV4 (CategoryProductResult productResult,
				BaseProduct baseProduct, DesignPlan designPlan,
				DesignPlanProduct designPlanProduct, Map < String, AutoCarryBaimoProducts > autoCarryMap,
			HttpServletRequest request) {
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		// 一次性查出产品大小类信息,材质配置文件,模型,是否是组合

		// 定制产品信息;1:是;0:否->start
		if (baseProduct.getBmIds() != null) {
			productResult.setIsCustomized(1);
		}
		// 定制产品信息;1:是;0:否->end

		// 是否是主产品信息->start
		Integer groupCount = baseProduct.getGroupCount();
		productResult.setIsMainProduct((groupCount != null && groupCount > 0) ? 1 : 0);
		// 是否是主产品信息->end
		/* logger.warn("------是否是主产品:" + (endTime - startTime)); */

		// 大类,小类,软硬装->start
		String bigTypeValueStr = baseProduct.getProductTypeValue();
		Integer bigTypeValue = null;
		if (StringUtils.isNotBlank(bigTypeValueStr)) {
			bigTypeValue = Integer.valueOf(bigTypeValueStr);
		}
		productResult.setProductTypeValue(bigTypeValue);
		productResult.setProductTypeCode(baseProduct.getBigTypeValueKey());
		productResult.setProductTypeName(baseProduct.getBigTypeName());
		productResult.setProductSmallTypeCode(baseProduct.getSmallTypeValueKey());
		productResult.setProductSmallTypeName(baseProduct.getSmallTypeName());
		productResult.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		String smallTypeAtt1 = baseProduct.getSmallTypeAtt1().trim();
		String rootType = StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1;
		productResult.setRootType(rootType);
		// 大类,小类,软硬装->end

		// 是否是背景墙->start
		Integer bgWallValue = this.getBgWallValue(productResult.getProductTypeCode(),
				productResult.getProductSmallTypeCode());
		productResult.setBgWall(bgWallValue);
		// 是否是背景墙->end

		// 空间ID->start
		productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
		// 空间ID->end

		// 产品长宽高->start
		productResult.setProductLength(baseProduct.getProductLength());
		productResult.setProductWidth(baseProduct.getProductWidth());
		productResult.setProductHeight(baseProduct.getProductHeight());
		productResult.setMinHeight(baseProduct.getMinHeight());
		// 产品长宽高->end

		// 材质信息->start
		this.setTextureInfo(productResult, baseProduct);
		// 材质信息->end

		// u3d模型相关信息->start
		ResModel resModel = this.setmodelInfo(baseProduct,
				designPlanProduct == null ? null : designPlanProduct.getInitProductId(), mediaType);
		if (resModel != null) {
			productResult.setU3dModelPath(resModel.getModelPath());
			productResult.setProductModelPath(resModel.getModelPath());
			productResult.setModelLength(resModel.getLength());
			productResult.setModelWidth(resModel.getWidth());
			productResult.setModelHeight(resModel.getHeight());
			productResult.setModelMinHeight(resModel.getMinHeight());
			productResult.setModelProductId(baseProduct.getId());
		} else {
			logger.info("resModel is null;productId=" + baseProduct.getId() + ";");
		}
		// u3d模型相关信息->end

		// 同类型新增产品设置主产品id->start
		productResult.setParentProductId(baseProduct.getParentId());
		// 同类型新增产品设置主产品id->end

		// 产品附加属性->start
		Map<String, String> map = new HashMap<String, String>();
		map = productAttributeService.getPropertyMap(productResult.getProductId());
		productResult.setPropertyMap(map);
		// 产品附加属性->end

		// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
		if (autoCarryMap != null && autoCarryMap.containsKey(productResult.getProductSmallTypeCode())) {
			logger.info("当前产品分类：" + productResult.getProductSmallTypeCode() + "需要自动携带白模信息。");
			// 获取当前产品得配置
			AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productResult.getProductSmallTypeCode());
			logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
			if (autoCarryBaimoProducts != null) {
				// 获取需要携带得白模有哪些
				JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
				@SuppressWarnings("unchecked")
				List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
						.toCollection(baimoProductsConfigArray, AutoCarryBaimoProducts.class);
				// 根据产品的属性，查询同属性的白模产品
				if (baimoProductsConfigs != null && baimoProductsConfigs.size() > 0) {
					List<ProductAttribute> tempList = null;
					Map<String, CategoryProductResult> basicModelMap = new HashMap<>();
					// 产品属性(需要对比的属性)
					JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
					// 白模产品属性（需要对比的属性）
					JSONArray baimoProductPropertyArray = null;
					Map<String, String> baimoPropertyMap = new HashMap<>();
					for (AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs) {
						// 白模产品的属性
						baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
						if (baimoProductPropertyArray == null) {
							continue;
						}
						if (baimoProductPropertyArray.size() != productPropertyArray.size()) {
							continue;
						}
						for (Object object : baimoProductPropertyArray) {
							String[] objStr = object.toString().split("#");
							baimoPropertyMap.put(objStr[0], objStr[1]);
						}
						// 对比产品和白模产品的每个属性
						String productPropertyStr = "";
						String baimoProductPropertyStr = "";
						int i = 0;
						Map<String, Object> conditionMap = new HashMap<>();
						List<String> conditionList = new ArrayList<>();
						StringBuffer conditionStr = new StringBuffer();
						for (Object object : productPropertyArray) {
							productPropertyStr = object.toString();
							baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
							if (StringUtils.isNotBlank(baimoProductPropertyStr)) {
								Integer propValue = map.get(productPropertyStr) != null
										? Integer.valueOf(map.get(productPropertyStr)) : 0;
								conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr
										+ "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode()
										+ "' AND pp.prop_value = " + propValue);
								if (i < productPropertyArray.size() - 1) {
									conditionStr.append(" union all ");
								}
							}
							i++;
							conditionList.add(conditionStr.toString());
						}
						conditionMap.put("conditionCount", conditionList.size());
						conditionMap.put("conditionList", conditionList);

						tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
						if (tempList != null && tempList.size() > 0) {
							logger.info("共查到" + tempList.size() + "条满足要求的白模数据！！！");
							Integer baimoProductId = tempList.get(0).getProductId();
							ProductCategoryRel productCategoryRel = new ProductCategoryRel();
							productCategoryRel.setProductId(baimoProductId);
							CategoryProductResult productResultBaimo = productCategoryRelService
									.getCategoryProductResultByProductId(productCategoryRel);
							Map<String, Integer> InfoByIdMap = new HashMap<>();
							InfoByIdMap.put("id", productResultBaimo.getProductId());
							InfoByIdMap.put("designTempletId", designPlan.getDesignTemplateId());
							BaseProduct baseProductBaimo = this.getInfoById(InfoByIdMap);
							CategoryProductResult baimoProducts = decorationProductInfoV3(productResultBaimo,
									baseProductBaimo, designPlan, designPlanProduct, autoCarryMap,
									request);
							basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
						}
					}
					productResult.setBasicModelMap(basicModelMap);
				}
			}
		}
		return productResult;
	}
		
	/**
		 * 获取产品对应的u3d模型信息
		 * @author huangsongbo
		 * @param baseProduct 产品
		 * @param initProductId 产品对应的白膜id
		 * @param mediaType 媒介值
		 * @return
		 */
		public ResModel setmodelInfo (BaseProduct baseProduct, Integer initProductId, String mediaType){
			String modelId = getU3dModelId(mediaType, baseProduct);
			ResModel resModel = null;
			if (StringUtils.isNotEmpty(modelId)) {
				//产品有自己的模型
				if (Utils.enableRedisCache()) {
					resModel = ResourceCacher.getModel(Integer.valueOf(modelId));
				} else {
					resModel = resModelService.get(Integer.valueOf(modelId));
				}
			} else {
				//产品没有自己的模型->
				boolean isHard = false;
				if (baseProduct != null) {
					isHard = isHard(baseProduct);
				}
				//平吊天花造型
				if (("1".equals(baseProduct.getProductTypeValue()) && baseProduct.getProductSmallTypeValue() == 5)
						|| ("3".equals(baseProduct.getProductTypeValue()) && (baseProduct.getProductSmallTypeValue() == 5))) {
					isHard = false;
				}
				// 如果产品的模型不为空，则表示该产品不是贴图产品或者
				if (StringUtils.isNotBlank(modelId) && StringUtils.isBlank(baseProduct.getBmIds())) {
					isHard = false;
				}
				if (isHard) {
					if (initProductId != null && initProductId > 0) {
						//取白膜数据->start
						BaseProduct baimoProduct = new BaseProduct();
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(initProductId);
						} else {
							baimoProduct = baseProductMapper.selectByPrimaryKey(initProductId);
						}
						//取白膜数据->end

						//获取不同媒介u3d模型
						String u3dModelId = getU3dModelId(mediaType, baimoProduct);
						if (StringUtils.isNotBlank(u3dModelId)) {
							if (Utils.enableRedisCache())
								resModel = ResourceCacher.getModel(Integer.valueOf(u3dModelId));
							else
								resModel = resModelService.get(Integer.valueOf(u3dModelId));
						}
					}
				}
			}
			return resModel;
		}

		/**
		 * 设置材质信息
		 * @author huangsongbo
		 * @param productResult
		 * @param baseProduct
		 */
		@SuppressWarnings("unchecked")
		public void setTextureInfo (CategoryProductResult productResult, BaseProduct baseProduct){
			//处理拆分材质产品返回默认材质
			String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
			Integer isSplit = 0;
			List<SplitTextureDTO> splitTextureDTOList = null;
			if (StringUtils.isNotBlank(splitTexturesInfo)) {
				Map<String, Object> map = this.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo, "choose");
				isSplit = (Integer) map.get("isSplit");
				splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
			} else {
				//普通产品
				String materialIds = productResult.getMaterialPicId();
				Integer materialId = 0;
				if (StringUtils.isNotBlank(materialIds)) {
					List<String> materialIdStrList = Utils.getListFromStr(materialIds);
					if (materialIdStrList != null && materialIdStrList.size() > 0) {
						materialId = Integer.valueOf(materialIdStrList.get(0));
					}
				}
				if (materialId != null && materialId > 0) {
					ResTexture resTexture = resTextureService.get(materialId);
					if (resTexture != null) {
						splitTextureDTOList = new ArrayList<SplitTextureDTO>();
						List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
						SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
						ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
						resTextureDTO.setKey(splitTextureDTO.getKey());
						resTextureDTO.setProductId(baseProduct.getId());
						resTextureDTOList.add(resTextureDTO);
						splitTextureDTO.setList(resTextureDTOList);
						splitTextureDTOList.add(splitTextureDTO);
						productResult.setSplitTexturesChoose(splitTextureDTOList);
						//同时存一份数据在老的数据结构上
						productResult.setTextureAttrValue(resTexture.getTextureAttrValue());
						productResult.setLaymodes(resTexture.getLaymodes());
						productResult.setTextureWidth(resTexture.getFileWidth() + "");
						productResult.setTextureHeight(resTexture.getFileHeight() + "");
						String[] materialPathList = new String[]{resTextureDTO.getPath()};
						productResult.setMaterialPicPaths(materialPathList);
						//同时存一份数据在老的数据结构上->end
					}
				}
			}
			productResult.setIsSplit(isSplit);
			productResult.setSplitTexturesChoose(splitTextureDTOList);
		}

		/**
		 * 识别是否是背景墙
		 * 0:不是
		 * 1:是
		 * @param productTypeCode
		 * @param productSmallTypeCode
		 * @return
		 */
		public Integer getBgWallValue (String productTypeCode,String productSmallTypeCode){
			Integer bgWallValue = 0;
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(productTypeCode)) {
				bgWallValue = Utils.getIsBgWall(productSmallTypeCode);
			} else {
			}
			return bgWallValue;
		}

		/**
		 * 设置大小类信息
		 * @param productResult
		 */
		public void setTypeMsg (CategoryProductResult productResult, HttpServletRequest request){
			Integer bigTypeValue = productResult.getProductTypeValue();
			if (bigTypeValue != null && bigTypeValue > 0) {
				SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", bigTypeValue);
				productResult.setProductTypeCode(productTypeSysDic.getValuekey());
				productResult.setProductTypeName(productTypeSysDic.getName());
				Integer smallTypeValue = productResult.getProductSmallTypeValue();
				if (smallTypeValue != null && smallTypeValue > 0) {
					SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(), smallTypeValue);
					productResult.setProductSmallTypeCode(productSmallTypeSysDic.getValuekey());
					productResult.setProductSmallTypeName(productSmallTypeSysDic.getName());
					String rootType = StringUtils.isEmpty(productSmallTypeSysDic.getAtt1()) ? "2" : productSmallTypeSysDic.getAtt1().trim();
					productResult.setRootType(rootType);
				}
			}
		}

		/**
		 * 获得产品信息(并且join多表查询更多的属性)
		 * @author huangsongbo
		 */
		public BaseProduct getInfoById (Map<String,Integer> map){
			return baseProductMapper.getInfoById(map);
		}

		/**
		 * 设置产品色系排序条件
		 * @param list
		 */
		public void productColorsSort (List < CategoryProductResult > list ){
			Long startDate2_1 = new Date().getTime();
			if (list.size() > 0) {
				BaseProduct baseProduct = new BaseProduct();
				ProductColors productColors = new ProductColors();
				ProductColors productColors_ = null;
				for (CategoryProductResult cpr : list) {
					Integer productId = cpr.getProductId();
					if (productId != null) {
						if (Utils.enableRedisCache()) {
							baseProduct = BaseProductCacher.get(cpr.getProductId());
						} else {
							baseProduct = this.get(cpr.getProductId());
						}
						String colorsLongCode = baseProduct.getColorsLongCode();
						String pCode = "";
						String cCode = "";
						if (StringUtils.isNotBlank(colorsLongCode)) {
							String[] colorsCode = colorsLongCode.split("\\.");
							//获取父色系排序
							pCode = colorsCode[1];
							productColors.setColorsCode(pCode);
							productColors_ = productColorsService.getByCode(productColors);
							String orderingF = "";
							String orderingC = "";
							if (productColors_ != null) {
								if (productColors_.getOrdering() != null) {
									orderingF = productColors_.getOrdering() + "";
									if (orderingF.length() == 1) {
										orderingF = 0 + orderingF;
									}
								}
							}
							//截取子编码
							cCode = colorsCode[2];
							productColors.setColorsCode(cCode);
							productColors_ = productColorsService.getByCode(productColors);
							if (productColors_ != null) {
								if (productColors_.getOrdering() != null) {
									orderingC = productColors_.getOrdering() + "";
									if (orderingC.length() == 1) {
										orderingC = 0 + orderingC;
									}
								}
							}
							//父编码排序+子编码排序
							if (StringUtils.isNotBlank(orderingF) && StringUtils.isNotBlank(orderingC)) {
								cpr.setColorsOrdering(Integer.parseInt((orderingF + orderingC)));
							}
						}
					}
				}
			}
			Long startDate2_2 = new Date().getTime();
			logger.warn("------色系排序:" + (startDate2_2 - startDate2_1));
		}

		public void productColorsSortV2 (List < CategoryProductResult > list){
			if (list.size() > 0) {
				ProductColors productColors = new ProductColors();
				ProductColors productColors_ = null;
				for (CategoryProductResult cpr : list) {
				/*String colorsLongCode = baseProduct.getColorsLongCode();*/
					String colorsLongCode = cpr.getColorsLongCode();
					String pCode = "";
					String cCode = "";
					if (StringUtils.isNotBlank(colorsLongCode)) {
						String[] colorsCode = colorsLongCode.split("\\.");
						//获取父色系排序
						pCode = colorsCode[1];
						productColors.setColorsCode(pCode);
						productColors_ = productColorsService.getByCode(productColors);
						String orderingF = "";
						String orderingC = "";
						if (productColors_ != null) {
							if (productColors_.getOrdering() != null) {
								orderingF = productColors_.getOrdering() + "";
								if (orderingF.length() == 1) {
									orderingF = 0 + orderingF;
								}
							}
						}
						//截取子编码
						cCode = colorsCode[2];
						productColors.setColorsCode(cCode);
						productColors_ = productColorsService.getByCode(productColors);
						if (productColors_ != null) {
							if (productColors_.getOrdering() != null) {
								orderingC = productColors_.getOrdering() + "";
								if (orderingC.length() == 1) {
									orderingC = 0 + orderingC;
								}
							}
						}
						//父编码排序+子编码排序
						if (StringUtils.isNotBlank(orderingF) && StringUtils.isNotBlank(orderingC)) {
							cpr.setColorsOrdering(Integer.parseInt((orderingF + orderingC)));
						}
					}
				}
			}
		}

		@Override
		public BaseProduct getDataAndModel (BaseProduct baseProduct){
			return baseProductMapper.getDataAndModel(baseProduct);
		}

		@Override
		public List<BaseProduct> getInfoByIdList(List<Integer> idList, Integer designTemplateId, Integer userId) {
			return baseProductMapper.getInfoByIdList(idList, designTemplateId, userId);
		}

		public Map<String,CategoryProductResult> setbaimoRuleMap(Integer spaceCommonId, HttpServletRequest request, Integer userId,
				String productSmallTypeCode, DesignPlan designPlan, Map<String, String> map) {
			Map<String,CategoryProductResult> basicModelMap = new HashMap<>();
			//获取需要自动携带白模产品的配置
			Map<String,AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
			String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app","design.designPlan.autoCarryBaimoProducts","");
			if( StringUtils.isNotBlank(autoCarryBaimoProducrsConfig) ){
				JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
				try {
					@SuppressWarnings("unchecked")
					List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
					if( autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0 ) {
						for( AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs ){
							autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(),autoCarryBaimoProductsConfig);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.error("获取自动携带白模产品配置异常！");
				}
			}
			// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
			if( autoCarryMap != null && autoCarryMap.containsKey(productSmallTypeCode) ){
				logger.info("当前产品分类："+productSmallTypeCode+"需要自动携带白模信息。");
				// 获取当前产品得配置
				AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productSmallTypeCode);
				if( autoCarryBaimoProducts != null ){
					// 获取需要携带得白模有哪些
					JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
					List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(baimoProductsConfigArray,AutoCarryBaimoProducts.class);
					// 根据产品的属性，查询同属性的白模产品
					if( baimoProductsConfigs != null && baimoProductsConfigs.size() > 0 ){
						ProductAttribute pa1 = null;
						List<ProductAttribute> tempList = null;
						// 产品属性(需要对比的属性)
						JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
						// 白模产品属性（需要对比的属性）
						JSONArray baimoProductPropertyArray = null;
						Map<String,String> baimoPropertyMap = new HashMap<>();
						for( AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs ){
							// 白模产品的属性
							baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
							if( baimoProductPropertyArray == null ){
								continue;
							}
							if( baimoProductPropertyArray.size() != productPropertyArray.size() ){
								continue;
							}
							for( Object object : baimoProductPropertyArray ){
								String[] objStr = object.toString().split("#");
								baimoPropertyMap.put(objStr[0],objStr[1]);
							}
							// 对比产品和白模产品的每个属性
							String productPropertyStr = "";
							String baimoProductPropertyStr = "";
							int i = 0;
							Map<String,Object> conditionMap = new HashMap<>();
							List<String> conditionList = new ArrayList<>();
							for( Object object : productPropertyArray ){
								StringBuffer conditionStr = new StringBuffer();
								productPropertyStr = object.toString();
								baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
								if( StringUtils.isNotBlank(baimoProductPropertyStr) ){
									Integer propValue = map.get(productPropertyStr) != null ? Integer.valueOf(map.get(productPropertyStr)):0;
									conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr + "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode() + "' AND pp.prop_value = " + propValue);
									// 2018.2.28有个bug union all 是多余的,因为在mapper.xml里面已经有个这个关键字 update by huangsongbo
									/*if( i < productPropertyArray.size() - 1 ){
										conditionStr.append(" union all ");
									}*/
								}
								i++;
								conditionList.add(conditionStr.toString());
							}
							conditionMap.put("conditionCount",conditionList.size());
							conditionMap.put("conditionList",conditionList);

							tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
							if( tempList != null && tempList.size() > 0 ) {
								logger.info("共查到"+tempList.size()+"条满足要求的白模数据！！！");
								Integer baimoProductId = tempList.get(0).getProductId();
								CategoryProductResult baimoProducts = this.assemblyUnityPanProduct(baimoProductId, spaceCommonId, designPlan, userId, UserTypeConstant.mediaType);
								basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
							}
						}
					}
				}
			}
			return basicModelMap;
		}

		
	public List<ProductPropsSimple> getProductPropsSimpleByProductId(Integer productId) {
		return baseProductMapper.getProductPropsSimpleByProductId(productId);
	}

	/**
	 * 匹配模板房间产品数据
	 * @author xiaoxc
	 * @param baseProductSearch
	 * @return list
	 */
	public List<BaseProduct> getTempletProductData(BaseProductSearch baseProductSearch){
		return baseProductMapper.getTempletProductData(baseProductSearch);
	}

	/**
	 * 匹配同类型产品数据
	 * @author xiaoxc
	 * @param baseProductSearch
	 * @return list
	 */
	public List<BaseProduct> getProductData(BaseProductSearch baseProductSearch){
		return baseProductMapper.getProductData(baseProductSearch);
	}

	@Override
	public void setBasicModelMap(CategoryProductResult categoryProductResult,
			Map<String, AutoCarryBaimoProducts> autoCarryMap, Map<String, String> map, DesignPlan designPlan, DesignPlanProduct designPlanProduct, String mediaType) {
		// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
		if (autoCarryMap != null && autoCarryMap.containsKey(categoryProductResult.getProductSmallTypeCode())) {
			logger.info("当前产品分类：" + categoryProductResult.getProductSmallTypeCode() + "需要自动携带白模信息。");
			// 获取当前产品得配置s
			AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(categoryProductResult.getProductSmallTypeCode());
			logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
			if (autoCarryBaimoProducts != null) {
				// 获取需要携带得白模有哪些
				JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
				@SuppressWarnings("unchecked")
				List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
						.toCollection(baimoProductsConfigArray, AutoCarryBaimoProducts.class);
				// 根据产品的属性，查询同属性的白模产品
				if (baimoProductsConfigs != null && baimoProductsConfigs.size() > 0) {
					List<ProductAttribute> tempList = null;
					Map<String, CategoryProductResult> basicModelMap = new HashMap<>();
					// 产品属性(需要对比的属性)
					JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
					// 白模产品属性（需要对比的属性）
					JSONArray baimoProductPropertyArray = null;
					Map<String, String> baimoPropertyMap = new HashMap<>();
					for (AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs) {
						// 白模产品的属性
						baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
						if (baimoProductPropertyArray == null) {
							continue;
						}
						if (baimoProductPropertyArray.size() != productPropertyArray.size()) {
							continue;
						}
						for (Object object : baimoProductPropertyArray) {
							String[] objStr = object.toString().split("#");
							baimoPropertyMap.put(objStr[0], objStr[1]);
						}
						// 对比产品和白模产品的每个属性
						String productPropertyStr = "";
						String baimoProductPropertyStr = "";
						int i = 0;
						Map<String, Object> conditionMap = new HashMap<>();
						List<String> conditionList = new ArrayList<>();
						StringBuffer conditionStr = new StringBuffer();
						for (Object object : productPropertyArray) {
							productPropertyStr = object.toString();
							baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
							if (StringUtils.isNotBlank(baimoProductPropertyStr)) {
								Integer propValue = map.get(productPropertyStr) != null
										? Integer.valueOf(map.get(productPropertyStr)) : 0;
								conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr
										+ "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode()
										+ "' AND pp.prop_value = " + propValue);
								if (i < productPropertyArray.size() - 1) {
									conditionStr.append(" union all ");
								}
							}
							i++;
							conditionList.add(conditionStr.toString());
						}
						conditionMap.put("conditionCount", conditionList.size());
						conditionMap.put("conditionList", conditionList);
						tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
						if (tempList != null && tempList.size() > 0) {
							logger.info("共查到" + tempList.size() + "条满足要求的白模数据！！！");
							Integer baimoProductId = tempList.get(0).getProductId();
							ProductCategoryRel productCategoryRel = new ProductCategoryRel();
							productCategoryRel.setProductId(baimoProductId);
							CategoryProductResult productResultBaimo = productCategoryRelService.getCategoryProductResultByProductId(productCategoryRel);
							Map<String,Integer> InfoByIdMap = new HashMap<>();
							InfoByIdMap.put("id",productResultBaimo.getProductId());
							InfoByIdMap.put("designTempletId",designPlan.getDesignTemplateId());
							BaseProduct baseProductBaimo = this.getInfoById(InfoByIdMap);
							CategoryProductResult baimoProducts = decorationProductInfoV3(productResultBaimo, baseProductBaimo, designPlan, designPlanProduct, autoCarryMap,
									mediaType);
							basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
						}
					}
					categoryProductResult.setBasicModelMap(basicModelMap);
				}
			}
		}
	}

	@Override
		public List<BaseProduct> getSelectConditionByAuthorizedConfigV3(LoginUser loginUser, String checkCategory) {
			//获取该用户绑定的序列号品牌
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			authorizedList = authorizedConfigService.getList(authorizedConfig);
			//获取该用户绑定的序列号品牌->end

			//add品牌,大类,小类,产品查询条件
			List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
			if (authorizedList != null && authorizedList.size() > 0) {
				for (AuthorizedConfig authorizedConfigItem : authorizedList) {
					if(StringUtils.isNotBlank(authorizedConfigItem.getCompanyId())){
						// 查看授权码对应的分类,如果和当前查询分类不一致,continue
						/*Map<String, Object> map = baseCompanyService.getCategoryList(Integer.valueOf(authorizedConfigItem.getCompanyId()));*/
						Map<String, List<Integer>> map = baseCompanyService.getCategoryListV2(Integer.valueOf(authorizedConfigItem.getCompanyId()));
						/*@SuppressWarnings("unchecked")
						List<String> authorizedConfigCategoryList = (List<String>) map.get("bigTypeValueKeyList");
						if(authorizedConfigCategoryList.indexOf(checkCategory) == -1){
							continue;
						}*/
						if(map == null) {
							continue;
						}
						if(!map.containsKey(checkCategory)) {
							continue;
						}
						/*查询条件注入到BaseProduct类中*/
						BaseProduct baseProduct = this.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
						
						// 排除小类查询条件处理,设置 ->start
						// 比如授权码关联的东鹏公司是做墙面瓷砖的,则点击墙面搜索,瓷砖小类只显示东鹏的瓷砖+无品牌瓷砖,其他小类则显示全部
						// test ->start
						/*List<Integer> smallTypeValueListForShowAll = new ArrayList<Integer>();
						smallTypeValueListForShowAll.add(1);*/
						// 获取需要过滤的小类的value值List
						/*@SuppressWarnings("unchecked")
						List<Integer> smallTypeValueListForShowAll = (List<Integer>) map.get("smallTypeValueList");*/
						// test ->end
						// 排除小类查询条件处理,设置 ->end
						baseProduct.setSmallTypeValueListForShowAll(map.get(checkCategory));
						baseProductList.add(baseProduct);
					}
				}
			}
			//add品牌,大类,小类,产品查询条件->end
			return baseProductList;
		}


	/**
	 * 根据用户类型获取不同状态数据
	 * @author xiaoxc
	 * @param loginUser
	 * @return list
	 */
	@Override
	public List<Integer> getPutawayList(LoginUser loginUser){
		List putawayList = new ArrayList();
		if( loginUser.getUserType() == null ){
			return putawayList;
		}
		if( UserTypeCode.USER_TYPE_OUTER_B2C == loginUser.getUserType()
				|| UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType()) {
			putawayList.add(BaseProductPutawayState.IS_RELEASE);
		}else{
			putawayList.add(BaseProductPutawayState.IS_UP);
			putawayList.add(BaseProductPutawayState.IS_TEST);
			putawayList.add(BaseProductPutawayState.IS_RELEASE);
		}
		return  putawayList;
	}

		@Override
		public List<Integer> findIdListByStatus(List<Integer> productStatusList,int limit) {
			return baseProductMapper.findIdListByStatus(productStatusList,limit);
		}


		@Override
		public void updateStatus(int oldStatus, int newStatus) {
			baseProductMapper.updateStatus(oldStatus, newStatus);
		}

		@Override
		public void updateBmProductStatus(int oldStatus, int newStatus) {
			baseProductMapper.updateBmProductStatus(oldStatus, newStatus);
		}

		@Override
		public void updateProductStatus(int oldStatus, int newStatus,List<Integer> productIdList) {
			baseProductMapper.updateProductStatus(oldStatus, newStatus, productIdList);
		}

		/**
		 * 取出所有有属性的产品的id组成list
		 * @author huangsongbo
		 * @param categoryCode
		 * @return
		 */
		@Override
		public List<Integer> getPreprocessProductIdByCategoryCode(String categoryCode, List<Integer> productStatusList) {
			return baseProductMapper.getPreprocessProductIdByCategoryCode(categoryCode, productStatusList);
		}

		@Override
		public List<Integer> getPreprocessBaimoProductIdByCategoryCode(String categoryCode) {
			return baseProductMapper.getPreprocessBaimoProductIdByCategoryCode(categoryCode);
		}


		@Override
		public BaseProductSearch getProductInfoFilter(BaseProductSearch searchProduct,DesignProductResult baimoProduct) {
			if (baimoProduct != null) {
				return searchProduct;
			}
			Map<String,String> stretchZoomMap = getStretchZoomLength(baimoProduct.getSmallTypeKey());
			if (stretchZoomMap != null && stretchZoomMap.size() > 0) {
				if ("3".equals(baimoProduct.getBigTypeValue())) {
					searchProduct.setBeijing(true);
				} else {
					searchProduct.setStretch(true);
				}
				int stretchZoomLength = Utils.getIntValue(stretchZoomMap.get(ProductModelConstant.STRETCH_LENGTH));
				String isHeightFilter = stretchZoomMap.get(ProductModelConstant.IS_HEIGHT_FILTER);
				String productHeight = null;
				//是否需要高度过滤，是则设置高度值
				if (ProductModelConstant.HEIGHT_FILTER_VALUE_YES.equals(isHeightFilter)) {
                    searchProduct.setStretchHeight(baimoProduct.getProductHeight());
				}
				String fullPaveLength = baimoProduct.getFullPaveLength();
	//				if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) < 1) {
	//					fullPaveLength = baimoProduct.getProductLength();
	//				}
				if( StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
						&& StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0 ){
						searchProduct.setStartLength((Utils.getIntValue(fullPaveLength) - stretchZoomLength)+1);
						searchProduct.setEndLength(Utils.getIntValue(fullPaveLength) + stretchZoomLength);
				}else{
					searchProduct.setStretchHeight("0");
					searchProduct.setEndLength(-1);
					searchProduct.setStartLength(-1);
				}
			}
			//过滤产品长、 高信息
			String productSmallTypes_LH = Utils.getValue("filter.productLH.productSmallType","");
			if( Utils.isMateProductType(baimoProduct.getSmallTypeKey(),productSmallTypes_LH) ){
				if( baimoProduct != null ){
					if( baimoProduct.getProductLength() != null && baimoProduct.getProductHeight() != null ){
						searchProduct.setProductLength(baimoProduct.getProductLength());
						searchProduct.setProductHeight(baimoProduct.getProductHeight());
					}else{
						searchProduct.setProductLength("-1");
						searchProduct.setProductHeight("-1");
					}
				}
				searchProduct.setProductSmallTypeValue(null);
			}
			//过滤产品长、宽信息
			String productSmallTypes_LW = Utils.getValue("filter.productLW.productSmallType","");
			if( Utils.isMateProductType(baimoProduct.getSmallTypeKey(),productSmallTypes_LW) ){
				if( baimoProduct != null ) {
					searchProduct.setProductLength(baimoProduct.getProductLength());
					searchProduct.setProductWidth(baimoProduct.getProductWidth());
				}else{
					searchProduct.setProductLength("-1");
					searchProduct.setProductWidth("-1");
				}
			}
			//过滤产品属性
			productAttributeService.getOnekeyDecorateProductAttributeFilterCondition(searchProduct,baimoProduct.getProductId());

			return searchProduct;

		}

		@Override
		public int getcountByStatusList(List<Integer> productStatusList) {
			BaseProductSearch baseProductSearch = new BaseProductSearch();
			baseProductSearch.setStart(-1);
			baseProductSearch.setLimit(-1);
			baseProductSearch.setPutawayStateList(productStatusList);
			baseProductSearch.setIsDeleted(0);
			return this.getCount(baseProductSearch);
		}

		@Override
		public List<Integer> getProductTypeValueByStatus(List<Integer> productStatusList) {
			return baseProductMapper.getProductTypeValueByStatus(productStatusList);
		}

		@Override
		public Map<getBaseProductPropListMapEnum, List<BaseProductProps>> getBaseProductPropListByProudctStatusListAndProudctTypeValue(
				List<Integer> productStatusList, Integer productTypeValue) {
			
			// 获取配置(是否保存排序属性) ->start
			// 如果不需要保存排序属性,则整理出只有过滤属性的list
			String isSaveAllStr = Utils.getValue("product.prep.props.isSaveAll", "1");
			boolean isSaveSort = false;
			if(StringUtils.equals("1", isSaveAllStr)){
				isSaveSort = true;
			}
			// 获取配置(是否保存排序属性) ->end
			
			Map<getBaseProductPropListMapEnum, List<BaseProductProps>> returnMap = new HashMap<getBaseProductPropListMapEnum, List<BaseProductProps>>();
			
			List<BaseProduct> baseProductList = this.getPropInfoByStatusListAndProductTypeValue(productStatusList, productTypeValue);
			List<BaseProductProps> baseProductPropList = new ArrayList<>();
			List<BaseProductProps> baimoBaseProductPropList = new ArrayList<>();
			
			for(BaseProduct baseProduct : baseProductList){
				BaseProductProps baseProductProps = new BaseProductProps();
				baseProductProps.setProductId(baseProduct.getId());
				String productCode = baseProduct.getProductCode();
				if(StringUtils.isNotBlank(productCode) && productCode.startsWith("baimo_")){
					baseProductProps.setBaimo(true);
				}else{
					baseProductProps.setBaimo(false);
				}
				// 设置BaseProductProps的属性(过滤/排序)
				String propInfo = baseProduct.getPropInfo();
				if(StringUtils.isNotBlank(propInfo)){
					List<ProductPropsSimple> productOrderPropList= new ArrayList<ProductPropsSimple>();
					List<ProductPropsSimple> productFilterPropList = new ArrayList<ProductPropsSimple>();
					List<String> propInfoItemList = Utils.getListFromStr(propInfo, ";");
					for(String propInfoItem : propInfoItemList){
						String[] strs = propInfoItem.split(",");
						ProductPropsSimple productPropsSimple = null;
						try{
							productPropsSimple = new ProductPropsSimple(Integer.parseInt(strs[1]), strs[0], Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
						}catch(Exception e){
							logger.error("------propInfo转化为ProductPropsSimple失败,propInfoItem:" + propInfoItem + "baseProduct=>getProductId=" + baseProduct.getProductId());
							continue;
						}
						if(productPropsSimple.getIsSort() == 0){
							// 排序属性
							productOrderPropList.add(productPropsSimple);
						}else{
							// 过滤属性
							productFilterPropList.add(productPropsSimple);
						}
					}
					baseProductProps.setProductFilterPropList(productFilterPropList);
					baseProductProps.setProductOrderPropList(productOrderPropList);
				}
				
				if(baseProductProps.isBaimo()){
					if(!isSaveSort){
						if(baseProductProps.getProductFilterPropList() != null && baseProductProps.getProductFilterPropList().size() > 0){
							baimoBaseProductPropList.add(baseProductProps);
						}
					}else{
						baimoBaseProductPropList.add(baseProductProps);
					}
				}else{
					if(!isSaveSort){
						if(baseProductProps.getProductFilterPropList() != null && baseProductProps.getProductFilterPropList().size() > 0){
							baseProductPropList.add(baseProductProps);
						}
					}else{
						baseProductPropList.add(baseProductProps);
					}
				}
				
			}
			
			returnMap.put(getBaseProductPropListMapEnum.baimoBaseProductPropList, baimoBaseProductPropList);
			returnMap.put(getBaseProductPropListMapEnum.baseProductPropList, baseProductPropList);
			
			return returnMap;
		}

		@Override
		public List<BaseProduct> getPropInfoByStatusListAndProductTypeValue(List<Integer> productStatusList,
				Integer productTypeValue) {
			return baseProductMapper.getPropInfoByStatusListAndProductTypeValue(productStatusList, productTypeValue);
		}

		/**
		 * getBaseProductPropListByProudctStatusListAndProudctTypeValue方法返回Map的key枚举
		 * @author huangsongbo
		 *
		 */
		public enum getBaseProductPropListMapEnum{
			baimoBaseProductPropList, baseProductPropList
		}

	@Override
	public List<BaseProduct> getByPlanIdProductList(Integer planId,String productTypeValue,Integer productSmallTypeValue) {
		return baseProductMapper.findByPlanIdProductList(planId, productTypeValue,productSmallTypeValue);
	}

	@Override
	public int getCountRealTime(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, categoryCode);//如果是空间，他返回的是百分比，需要乘总数
    	int valNum = Integer.valueOf(val);
        prepProductSearchInfo.setLevelLimitCount(valNum);
        return baseProductMapper.getCountRealTime(prepProductSearchInfo);
	}

	@Override
	public List<CategoryProductResult> getProductIdListV2RealTime(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId,categoryCode);//如果是空间，他返回的是百分比，需要乘总数
        int valNum = Integer.valueOf(val);
        prepProductSearchInfo.setLevelLimitCount(valNum);
		return baseProductMapper.getProductIdListV2RealTime(prepProductSearchInfo);
	}

	@Override
	public BaseProduct findDataByCode(String productCode){
		return  baseProductMapper.findDataByCode(productCode);
	}

	@Override
	public List<BaseProduct> getDicList(BaseProduct baseProduct) {
		    return baseProductMapper.selectDicList(baseProduct);
	}

	public List<DesignProductResult> getPlanProductList(Integer planId){
		return baseProductMapper.getPlanProductList(planId);
	}

	/**
	 * 单品替换组合方法（适用于天花地板 功能）
	 * @return
	 */
	@Override
	public List<SearchProductGroupDetail> productSelectGroupReplaceV2(BaseProduct baseProduct, DesignPlan designPlan, 
			List<Integer> designPlanProductIds,int productIndex, Integer userType,Integer spaceCommonId,String mediaType) {
		List<SearchProductGroupDetail>resProductList = new ArrayList<SearchProductGroupDetail>();
		String filterLength = Utils.getValue("app.filter.stretch.length", "10");
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("productType", Integer.parseInt(baseProduct.getProductTypeValue()));
		if(sysDictionary == null){
			return null;
		}
		if(!ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getValuekey()) ){
			return null;
		}
		
		baseProduct.setProductTypeCode(sysDictionary.getValuekey());
		SysDictionary sysDictionary_ = sysDictionaryService.findOneByTypeAndValue(sysDictionary.getValuekey(), baseProduct.getProductSmallTypeValue());
		if(sysDictionary_ == null){
			return null;
		}
		String rootType = StringUtils.isEmpty(sysDictionary_.getAtt1()) ? "2" : sysDictionary_.getAtt1().trim();
		baseProduct.setRootType(rootType);
		baseProduct.setBgWall(Utils.getIsBgWall(sysDictionary_.getValuekey()));
		baseProduct.setProductSmallTypeCode(sysDictionary_.getValuekey());
		baseProduct.setProductIndex(productIndex);
		/*
		 * 通过样板房产品ids  查询序号 好 产品详细
		 */
		List<BaseProduct>productList = baseProductMapper.getProductListByDesignPlanProductids(designPlanProductIds);
		for (BaseProduct baseProduct_1 : productList) {
			BaseProduct resBaseProduct = null;
			if(baseProduct_1.getProductIndex()!=null && baseProduct_1.getProductIndex().intValue() == productIndex){
				resBaseProduct = baseProduct;
			}else{
				//需要通过 大小类  、款式 、长宽 、状态 、白膜 过滤
				if( "3".equals(baseProduct_1.getProductTypeValue()) ){
					BaseProduct baseProduct_2 = new BaseProduct();
					String productTypeCode = null;
					Integer styleId = null;
					String fullPaveLength = null;
					Integer startLength = null;
					Integer endLength = null;
					String productHeight = null;
					String productTypeValue = null;
					Integer productSmallTypeValue = null;
					if(userType.intValue() == 1){
						baseProduct_2.setIsInternalUser("yes");
					}
					productTypeCode = baseProduct_1.getProductTypeCode();
					styleId = baseProduct.getStyleId();
					productTypeValue = baseProduct.getProductTypeValue();
					productSmallTypeValue = baseProduct.getProductSmallTypeValue();
					
					baseProduct_2.setProductTypeCode(productTypeCode); //大小类
					baseProduct_2.setStyleId(styleId);  //款式
					baseProduct_2.setBeijing(true);
					baseProduct_2.setProductTypeValue(productTypeValue);
					baseProduct_2.setProductSmallTypeValue(productSmallTypeValue);
					fullPaveLength = baseProduct_1.getFullPaveLength();//长宽
					if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
						fullPaveLength = baseProduct_1.getProductLength();
					}
					productHeight = baseProduct_1.getProductHeight();
					if( StringUtils.isNotBlank(fullPaveLength)  && Utils.getIntValue(fullPaveLength) > 0  && StringUtils.isNotBlank(productHeight)  && Utils.getIntValue(productHeight) > 0 ){
						startLength = (Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength))+1;
						endLength = Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength);
						baseProduct_2.setStartLength(startLength);
						baseProduct_2.setEndLength(endLength);
						baseProduct_2.setStretchHeight(productHeight);
					}
					if(productTypeCode == null || "".equals(productTypeCode) || styleId == null || styleId.intValue()<=0 || startLength == null || startLength.intValue()<=0
							|| endLength == null || endLength.intValue()<=0 || productHeight == null || "".equals(productHeight) || fullPaveLength == null || "".equals(fullPaveLength)
									|| productTypeValue == null || "".equals(productTypeValue)|| productSmallTypeValue == null || productSmallTypeValue.intValue()<=0  ){
						return null;
					}
					// 需要通过 大小类  、款式 、长宽 、状态 、白膜 过滤  得到的结果
					List<BaseProduct>list = baseProductMapper.matchingProductList(baseProduct_2);
					if(list!=null && list.size()>0){
						resBaseProduct = list.get(0);
						resBaseProduct.setProductIndex(baseProduct_1.getProductIndex());
						resBaseProduct.setBgWall(Utils.getIsBgWall(resBaseProduct.getProductSmallTypeCode()));
						String rootType_ = StringUtils.isEmpty(resBaseProduct.getAtt1()) ? "2" : resBaseProduct.getAtt1().trim();
						resBaseProduct.setRootType(rootType_);
					}
				}
			}
 
			if(resBaseProduct!=null){
				SearchProductGroupDetail searchProductGroupDetail = new SearchProductGroupDetail();
				searchProductGroupDetail.setRootType(resBaseProduct.getRootType());
				searchProductGroupDetail.setBgWall(resBaseProduct.getBgWall());
				searchProductGroupDetail.setProductIndex(resBaseProduct.getProductIndex());
				searchProductGroupDetail.setParentProductId(resBaseProduct.getParentProductId());
				searchProductGroupDetail.setProductSmallTypeCode(resBaseProduct.getProductSmallTypeCode());
				searchProductGroupDetail.setProductTypeCode(resBaseProduct.getProductTypeCode());
				searchProductGroupDetail.setProductLength(resBaseProduct.getProductLength());
				searchProductGroupDetail.setProductWidth(resBaseProduct.getProductWidth());
				searchProductGroupDetail.setProductHeight(resBaseProduct.getProductHeight());
				searchProductGroupDetail.setProductId(resBaseProduct.getId());
				searchProductGroupDetail.setProductCode(resBaseProduct.getProductCode());
				searchProductGroupDetail.setProductSmallTypeValue(resBaseProduct.getProductSmallTypeValue()+"");
				searchProductGroupDetail.setProductIndex(resBaseProduct.getProductIndex());
				searchProductGroupDetail.setProductTypeName(sysDictionary.getName());
				searchProductGroupDetail.setProductSmallTypeName(sysDictionary_.getName());
				searchProductGroupDetail.setProductTypeValue(sysDictionary.getValue());
				String modelId = this.getU3dModelId(mediaType, resBaseProduct);
				if( StringUtils.isNotBlank(modelId) ){
					ResModel resModel = resModelService.get(Integer.valueOf(modelId));
					if( resModel != null ){
						/*File modelFile = new File(Utils.getValue("app.upload.root","")+resModel.getModelPath());*/
						File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
						if( modelFile.exists() ){
							searchProductGroupDetail.setU3dModelPath(resModel.getModelPath());
						}
					}
				}
				/*处理拆分材质产品返回默认材质*/
				String splitTexturesInfo = resBaseProduct.getSplitTexturesInfo();
				Integer isSplit=0;
				List<SplitTextureDTO> splitTextureDTOList=null;
				//多材质
				if(StringUtils.isNotBlank(splitTexturesInfo)){
					Map<String,Object> map=this.dealWithSplitTextureInfo(resBaseProduct.getId(), splitTexturesInfo,"choose");
					isSplit=(Integer) map.get("isSplit");
					splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
				} else {
					/*普通产品*/
					String materialIds = resBaseProduct.getMaterialPicIds();
					Integer materialId=0;
					if(StringUtils.isNotBlank(materialIds)){
						List<String> materialIdStrList=Utils.getListFromStr(materialIds);
						if(materialIdStrList!=null&&materialIdStrList.size()>0){
							materialId=Integer.valueOf(materialIdStrList.get(0));
						}
					}
					if(materialId!=null && materialId>0){
						ResTexture resTexture = resTextureService.get(materialId);
						if(resTexture!=null){
							splitTextureDTOList=new ArrayList<SplitTextureDTO>();
							List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
							SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
							ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
							searchProductGroupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
							resTextureDTO.setKey(splitTextureDTO.getKey());
							resTextureDTO.setProductId(baseProduct.getId());
							resTextureDTOList.add(resTextureDTO);
							splitTextureDTO.setList(resTextureDTOList);
							splitTextureDTOList.add(splitTextureDTO);
						}
					}
				}
				searchProductGroupDetail.setIsSplit(isSplit);
				searchProductGroupDetail.setSplitTexturesChoose(splitTextureDTOList);
				/*处理拆分材质产品返回默认材质->end*/
				//在组合产品查询列表 中 增加产品属性
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(resBaseProduct.getId());
				searchProductGroupDetail.setPropertyMap(map);
				
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(), baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
				searchProductGroupDetail.setRulesMap(rulesMap);
				resProductList.add(searchProductGroupDetail);
			}else{
				return null;
			}
		} 
		return resProductList;
	}


	/**
	 * 通过白模背景墙主体，匹配出对应背景墙产品
	 * @param baseProduct
	 * @param designPlan
	 * @param designPlanProductIds 设计方案产品ID
	 * @param productIndex
	 * @param userType
	 * @param spaceCommonId
	 * @param mediaType
	 * @param styleId 主产品款式
	 * @return
	 * @throws ElasticSearchException 
	 * @throws BizException 
	 */
	@Override
 	public List<SearchProductGroupDetail> productSelectGroupReplaceV3(BaseProduct baseProduct, DesignPlan designPlan,
						List<Integer> designPlanProductIds,int productIndex, Integer userType,Integer spaceCommonId,String mediaType,Integer styleId) throws BizException, ElasticSearchException{
		List<SearchProductGroupDetail> resProductList = new ArrayList<SearchProductGroupDetail>();
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("productType", Integer.parseInt(baseProduct.getProductTypeValue()));
		if(sysDictionary == null){
			return null;
		}
		if(!ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getValuekey()) ){
			return null;
		}
		baseProduct.setProductTypeCode(sysDictionary.getValuekey());
		SysDictionary sysDictionary_ = sysDictionaryService.findOneByTypeAndValue(sysDictionary.getValuekey(), baseProduct.getProductSmallTypeValue());
		if(sysDictionary_ == null){
			return null;
		}
		String rootType = StringUtils.isEmpty(sysDictionary_.getAtt1()) ? "2" : sysDictionary_.getAtt1().trim();
		baseProduct.setRootType(rootType);
		baseProduct.setBgWall(Utils.getIsBgWall(sysDictionary_.getValuekey()));
		baseProduct.setProductSmallTypeCode(sysDictionary_.getValuekey());
		baseProduct.setProductIndex(productIndex);

		/** 通过对应白模产品的全铺长度、款式找到合适的产品。
		 *  如果两个条件过滤不出产品，则直接用全铺长度过滤。
		 *  全铺长度过滤不出数据则返回空
		 **/
		String filterLength = Utils.getValue("app.filter.stretch.length", "10");// 全铺长度可浮动范围±10
		BaseProduct searchProduct = new BaseProduct();
		searchProduct.setProductTypeValue(baseProduct.getProductTypeValue());
		searchProduct.setProductTypeCode(baseProduct.getProductTypeMark());
		searchProduct.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		searchProduct.setProductSmallTypeCode(baseProduct.getProductSmallTypeMark());
		if(userType.intValue() == 1){
			searchProduct.setIsInternalUser("yes");
		}
		// 款式
		// update by huangsongbo 2017.12.18 ->start
		/*searchProduct.setStyleId(baseProduct.getStyleId());
		searchProduct.setProductModelNumber(baseProduct.getProductModelNumber());*/
		searchProduct.setOrderStyleId(baseProduct.getStyleId());
		searchProduct.setOrderProductModelNumber(baseProduct.getProductModelNumber());
		// update by huangsongbo 2017.12.18 ->end
		
		searchProduct.setBeijing(true);
		DesignPlanProduct designPlanProduct = null;
		BaseProduct baseProduct1 = null;
		SearchProductGroupDetail searchProductGroupDetail = null;
		SearchProductGroupDetail searchProductGroupDetailClone = null;
		for( Integer designPlanProductId : designPlanProductIds ){
			// 根据每一个设计方案产品ID，查出与自己全铺长度
			designPlanProduct = designPlanProductService.get(designPlanProductId);
			if( designPlanProduct == null ){
				return null;
			}
			if( designPlanProduct.getProductIndex() != null && designPlanProduct.getProductIndex().intValue() == productIndex ){
				searchProductGroupDetail = new SearchProductGroupDetail();
				searchProductGroupDetailClone = searchProductGroupDetail.clone();
				searchProductGroupDetailClone.setRootType(rootType);
				searchProductGroupDetailClone.setBgWall(Utils.getIsBgWall(baseProduct.getProductSmallTypeCode()));
				searchProductGroupDetailClone.setProductIndex(designPlanProduct.getProductIndex());
				searchProductGroupDetailClone.setParentProductId(baseProduct.getParentId());
				searchProductGroupDetailClone.setProductTypeValue(Integer.valueOf(searchProduct.getProductTypeValue()));
				searchProductGroupDetailClone.setProductTypeCode(baseProduct.getProductTypeMark());
				searchProductGroupDetailClone.setProductTypeName(sysDictionary.getName());
				searchProductGroupDetailClone.setProductSmallTypeCode(baseProduct.getProductSmallTypeCode());
				searchProductGroupDetailClone.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue().toString());
				searchProductGroupDetailClone.setProductSmallTypeName(sysDictionary_.getName());
				searchProductGroupDetailClone.setProductHeight(baseProduct.getProductHeight());
				searchProductGroupDetailClone.setProductWidth(baseProduct.getProductWidth());
				searchProductGroupDetailClone.setProductLength(baseProduct.getProductLength());
				searchProductGroupDetailClone.setProductId(baseProduct.getId());
				searchProductGroupDetailClone.setProductCode(baseProduct.getProductCode());
				searchProductGroupDetailClone.setIsMainProduct(designPlanProduct.getIsMainProduct());
				searchProductGroupDetailClone.setSplitTexturesInfo(baseProduct.getSplitTexturesInfo());
				String modelId = this.getU3dModelId(mediaType, baseProduct);
				if( StringUtils.isNotBlank(modelId) ){
					ResModel resModel = resModelService.get(Integer.valueOf(modelId));
					if( resModel != null ){
						/*File modelFile = new File(Utils.getValue("app.upload.root","")+resModel.getModelPath());*/
						File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
						if( modelFile.exists() ){
							searchProductGroupDetailClone.setU3dModelPath(resModel.getModelPath());
						}
					}
				}
			}else{
				List<SearchProductGroupDetail> searchProductGroupDetailList = new ArrayList<>();
				searchProductGroupDetail = new SearchProductGroupDetail();
//				Integer productId = designPlanProduct.getProductId();
//				baseProduct1 = baseProductMapper.selectByPrimaryKey(productId);
//				if( baseProduct1 != null && !baseProduct1.getProductCode().startsWith("baimo") ){
//					baseProduct1 = baseProductMapper.selectByPrimaryKey(designPlanProduct.getInitProductId());
//				}
				//xiaoxc_20170821_edit
				if (designPlanProduct.getInitProductId() != null && designPlanProduct.getInitProductId().intValue() > 0) {
					baseProduct1 = baseProductMapper.selectByPrimaryKey(designPlanProduct.getInitProductId());
				}else{
					return null;
				}
				// 全铺长度过滤条件
				Integer fullPaveLength = 0;
				if( StringUtils.isNotBlank(baseProduct1.getFullPaveLength()) ) {
					fullPaveLength = Integer.valueOf(baseProduct1.getFullPaveLength());
					searchProduct.setFullPaveLength(baseProduct1.getFullPaveLength());
					// 全铺长度范围
					searchProduct.setStartLength(fullPaveLength - Integer.valueOf(filterLength));
					searchProduct.setEndLength(fullPaveLength + Integer.valueOf(filterLength));
					searchProduct.setProductTypeValue(baseProduct.getProductTypeValue());
					searchProduct.setProductTypeCode(baseProduct.getProductTypeMark());
					
					// update by huangsongbo 2017.12.18 ->start
					/*searchProduct.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());*/
					searchProduct.setOrderProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
					searchProduct.setProductSmallTypeValue(null);
					searchProduct.setSmallTypeValueListForShowAll(sysDictionaryService.getValueByTypeAndValueKeylist(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, beijingValuekeyList));
					// update by huangsongbo 2017.12.18 ->start
					
					searchProduct.setStretchHeight(baseProduct1.getProductHeight());
					// 排序,暂时没有
					
					// 改造该方法,顺便添加逻辑:本小类排前面,不然可以选择所有背景墙小类,update by huangsongbo2017.12.18 ->start
					/*searchProductGroupDetailList = baseProductMapper.getBgWallByFullPaveLengthAndStyle(searchProduct);
					// 如果本背景墙小类搜不出产品,则搜全小类背景墙产品 ->start
					if(Lists.isEmpty(searchProductGroupDetailList)) {
						searchProduct.setProductSmallTypeValue(null);
						searchProduct.setSmallTypeValueListForShowAll(sysDictionaryService.getValueByTypeAndValueKeylist(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, beijingValuekeyList));
						searchProductGroupDetailList = baseProductMapper.getBgWallByFullPaveLengthAndStyle(searchProduct);
					}
					// 如果本背景墙小类搜不出产品,则搜全小类背景墙产品 ->end*/
					searchProduct.setStart(0);
					searchProduct.setLimit(1);
					
					// 2018.8.8 备注 by huangsongbo slow,所以要改造:从es库中搜索产品
					searchProductGroupDetailList = this.getBgWallByFullPaveLengthAndStyleV2(searchProduct);
					
					// 改造该方法,顺便添加逻辑:本小类排前面,不然可以选择所有背景墙小类,update by huangsongbo2017.12.18 ->start
					
					// 上述方法重构(update by huangsongbo 2017.12.18) ->start
					
					// 上述方法重构(update by huangsongbo 2017.12.18) ->end
					
					// 如果用全铺长度和款式过滤不出产品，则直接用全铺长度过滤
					/*if( searchProductGroupDetailList == null || searchProductGroupDetailList.size() == 0 ){
						searchProduct.setStyleId(null);
						searchProductGroupDetailList = baseProductMapper.getBgWallByFullPaveLengthAndStyle(searchProduct);
					}*/
					if( searchProductGroupDetailList != null && searchProductGroupDetailList.size() > 0 ){
						searchProductGroupDetail = searchProductGroupDetailList.get(0);
						searchProductGroupDetailClone = searchProductGroupDetail.clone();
						searchProductGroupDetailClone.setRootType(rootType);
						searchProductGroupDetailClone.setBgWall(Utils.getIsBgWall(baseProduct.getProductSmallTypeCode()));
						searchProductGroupDetailClone.setProductIndex(designPlanProduct.getProductIndex());
						searchProductGroupDetailClone.setParentProductId(baseProduct1.getParentId());
						searchProductGroupDetailClone.setProductTypeValue(Integer.valueOf(searchProduct.getProductTypeValue()));
						searchProductGroupDetailClone.setProductTypeCode(baseProduct.getProductTypeMark());
						searchProductGroupDetailClone.setProductTypeName(sysDictionary.getName());
						searchProductGroupDetailClone.setProductSmallTypeCode(baseProduct.getProductSmallTypeCode());
						searchProductGroupDetailClone.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue().toString());
						searchProductGroupDetailClone.setProductSmallTypeName(sysDictionary_.getName());
						searchProductGroupDetailClone.setIsMainProduct(designPlanProduct.getIsMainProduct());
						baseProduct1.setWindowsU3dModelId(searchProductGroupDetailClone.getU3dModelId());
						String modelId = this.getU3dModelId(mediaType, baseProduct1);
						if( StringUtils.isNotBlank(modelId) ){
							ResModel resModel = resModelService.get(Integer.valueOf(modelId));
							if( resModel != null ){
								/*File modelFile = new File(Utils.getValue("app.upload.root","")+resModel.getModelPath());*/
								File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
								if( modelFile.exists() ){
									searchProductGroupDetailClone.setU3dModelPath(resModel.getModelPath());
								}
							}
						}
					}else{
						return null;
					}
				}else{
					return null;
				}
			}
			if( searchProductGroupDetailClone != null ){
				/* 处理拆分材质产品返回默认材质 */
				processProductMaterial(searchProductGroupDetailClone);
						/*处理拆分材质产品返回默认材质->end*/
				//在组合产品查询列表 中 增加产品属性
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(searchProductGroupDetailClone.getProductId());
				searchProductGroupDetailClone.setPropertyMap(map);

				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(searchProductGroupDetailClone.getProductId().toString(), searchProductGroupDetailClone.getProductTypeCode(),searchProductGroupDetailClone.getProductSmallTypeCode(),spaceCommonId,null,new DesignRules(),map);
				searchProductGroupDetailClone.setRulesMap(rulesMap);
				resProductList.add(searchProductGroupDetailClone);
			}
		}
		return resProductList;
	}

	/**
	 * 结构背景墙匹配搜索逻辑
	 * copy+改造
	 *
	 *@author huangsongbo
	 * @param searchProduct
	 * @return
	 * @throws ElasticSearchException 
	 * @throws BizException 
	 */
	private List<SearchProductGroupDetail> getBgWallByFullPaveLengthAndStyleV2(BaseProduct searchProduct) throws BizException, ElasticSearchException {
		// 参数验证 ->start
		if(searchProduct == null) {
			return null;
		}
		// 参数验证 ->end
		
		// update by huangsongbo 2018.8.8:优化:单品搜索由从数据库改为从es库 ->start
		/*return baseProductMapper.getBgWallByFullPaveLengthAndStyleV2(searchProduct);*/
		List<SearchProductGroupDetail> searchProductGroupDetailList = this.selectFromEs(searchProduct);
		return searchProductGroupDetailList;
		// update by huangsongbo 2018.8.8:优化:单品搜索由从数据库改为从es库 ->start
	}

	@Override
	public List<SearchProductGroupDetail> selectFromEs(BaseProduct searchProduct) throws BizException, ElasticSearchException {
		String throwLogPrefix = "查询产品异常";
		
		// 参数验证 ->start
		if(searchProduct == null) {
			String log = "params error: searchProduct = null";
			logger.error(LOGPREFIX + log);
			throw new BizException(throwLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		ProductSearchForOneKeyDTO productSearchForOneKeyDTO = this.transferToProductSearchForOneKeyDTO(searchProduct);
		List<ProductIndexMappingData> productIndexMappingDataList = productSearchOpenService.searchProduct(productSearchForOneKeyDTO);
		List<Integer> productIdList = productIndexMappingDataList.stream().map(item -> item.getId()).collect(Collectors.toList());
		
		/*return this.transferToSearchProductGroupDetailList(productIndexMappingDataList);*/
		return this.getSearchProductGroupDetailListByProductIdList(productIdList);
	}

	/**
	 * List<ProductIndexMappingData> -> List<SearchProductGroupDetail>
	 * 
	 * @author huangsongbo
	 * @param productIndexMappingDataList
	 * @return
	 */
	/*private List<SearchProductGroupDetail> transferToSearchProductGroupDetailList(
			List<ProductIndexMappingData> productIndexMappingDataList) {
		sss;
		return null;
	}*/

	@Override
	public List<SearchProductGroupDetail> getSearchProductGroupDetailListByProductIdList(List<Integer> productIdList) {
		// 参数验证 ->start
		if(Lists.isEmpty(productIdList)) {
			String log = "params error: Lists.isEmpty(productIdList) = true";
			logger.error(LOGPREFIX + log);
			return null;
		}
		// 参数验证 ->end
		
		return baseProductMapper.getSearchProductGroupDetailListByProductIdList(productIdList);
	}

	@Override
	public List<UnityPlanProductresTexture> getCountertopsTextureList(Integer productId){
		return baseProductMapper.selectCountertopsTextureList(productId);
	}

	public ProductInfo getProdutctInfoById(Integer id){
		return baseProductMapper.selectProdutctInfoById(id);
	}

	/**
	 * 获取天花信息
	 * @param productIdsList
	 * @return
	 */
	@Override
	public List<ProductCeilingVO> getCeilingInfoByProductIds(List<Integer> productIdsList) {
		return  baseProductMapper.getCeilingInfoByProductIds(productIdsList);
	}
	@Override
	public ProductCeilingVO getCeilingInfoByProductId(Integer id){
		return baseProductMapper.getCeilingInfoByProductId(id);
	}

	/**
	 * BaseProduct -> ProductSearchForOneKeyDTO
	 * 
	 * @author huangsongbo
	 * @param searchProduct
	 * @return
	 * @throws BizException 
	 */
	private ProductSearchForOneKeyDTO transferToProductSearchForOneKeyDTO(BaseProduct searchProduct) throws BizException {
		String throwLogPrefix = "数据转换异常";
		
		// 参数验证 ->start
		if(searchProduct == null) {
			String log = "params error: searchProduct = null";
			logger.error(LOGPREFIX + log);
			throw new BizException(throwLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		// 查询条件设置 ->start
		ProductSearchForOneKeyDTO productSearchForOneKeyDTO = new ProductSearchForOneKeyDTO();
		// 产品状态
		if(StringUtils.equals("yes", searchProduct.getIsInternalUser())) {
			productSearchForOneKeyDTO.setPutawayStateList(Arrays.asList(new Integer[] {1, 2, 3, 5}));
		}else {
			productSearchForOneKeyDTO.setPutawayStateList(Arrays.asList(new Integer[] {3}));
		}
		// 产品大类
		productSearchForOneKeyDTO.setProductTypeValue(searchProduct.getProductTypeValue());
		// 产品小类
		productSearchForOneKeyDTO.setProductSmallTypeValue(searchProduct.getProductSmallTypeValue());
		productSearchForOneKeyDTO.setSmallTypeValueListForShowAll(searchProduct.getSmallTypeValueListForShowAll());
		productSearchForOneKeyDTO.setOrderStyleId(searchProduct.getOrderStyleId());
		productSearchForOneKeyDTO.setOrderProductModelNumber(searchProduct.getOrderProductModelNumber());
		productSearchForOneKeyDTO.setOrderSmallTypeValue(searchProduct.getOrderProductSmallTypeValue());
		// 分页
		productSearchForOneKeyDTO.setStart(searchProduct.getStart());
		productSearchForOneKeyDTO.setLimit(searchProduct.getLimit());
		// 产品长高
		if(searchProduct.isBeijing()) {
			if(searchProduct.getStartLength() != null && searchProduct.getEndLength() != null) {
				productSearchForOneKeyDTO.setProductLengthStartInteger(searchProduct.getStartLength());
				productSearchForOneKeyDTO.setProductLengthEndInteger(searchProduct.getEndLength());
			}
			if(StringUtils.isNotEmpty(searchProduct.getStretchHeight())) {
				this.setProductLengthParams(productSearchForOneKeyDTO, Integer.valueOf(searchProduct.getStretchHeight()));
			}
		}
		// 查询条件设置 ->end
		
		return productSearchForOneKeyDTO;
	}

	private void setProductLengthParams(ProductSearchForOneKeyDTO productSearchForOneKeyDTO, Integer productHeight) throws BizException {
		String throwLogPrefix = "设置查询条件失败";
		
		// 参数验证 ->start
		if(productSearchForOneKeyDTO == null) {
			String log = "params error: productSearchForOneKeyDTO = null";
			logger.error(LOGPREFIX + log);
			throw new BizException(throwLogPrefix + "(" + log + ")");
		}
		if(productHeight == null) {
			String log = "params error: productHeight = null";
			logger.error(LOGPREFIX + log);
			throw new BizException(throwLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		Integer productHeightStart = null;
		Integer productHeightEnd = null;
		if(productHeight >= 0 && productHeight <= 40) {
			productHeightStart = 0;
			productHeightEnd = 40;
		}else if(productHeight > 40 && productHeight <= 52) {
			productHeightStart = 41;
			productHeightEnd = 52;
		}else {
			productHeightStart = 53;
		}
		productSearchForOneKeyDTO.setProductHeightStart(productHeightStart);
		productSearchForOneKeyDTO.setProductHeightEnd(productHeightEnd);
	}

	/**
	 * 处理产品材质
	 * @param searchProductGroupDetail
	 * @return
	 */
	public SearchProductGroupDetail processProductMaterial(SearchProductGroupDetail searchProductGroupDetail){
		/* 处理拆分材质产品返回默认材质 */
		String splitTexturesInfo = searchProductGroupDetail.getSplitTexturesInfo();
		Integer isSplit=0;
		List<SplitTextureDTO> splitTextureDTOList=null;
		//多材质
		if(StringUtils.isNotBlank(splitTexturesInfo)){
			//设值产品多维标识
			searchProductGroupDetail.setIsMultidimensional(1);
			Map<String,Object> map=this.dealWithSplitTextureInfo(searchProductGroupDetail.getProductId(), splitTexturesInfo,"choose");
			isSplit=(Integer) map.get("isSplit");
			splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
		} else {
			/*普通产品*/
			String materialIds = searchProductGroupDetail.getMaterialPicIds();
			Integer materialId=0;
			if(StringUtils.isNotBlank(materialIds)){
				List<String> materialIdStrList=Utils.getListFromStr(materialIds);
				if(materialIdStrList!=null&&materialIdStrList.size()>0){
					materialId=Integer.valueOf(materialIdStrList.get(0));
				}
			}
			if(materialId!=null && materialId>0){
				ResTexture resTexture = resTextureService.get(materialId);
				if(resTexture!=null){
					splitTextureDTOList=new ArrayList<SplitTextureDTO>();
					List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
					SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
					ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
					searchProductGroupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
					resTextureDTO.setKey(splitTextureDTO.getKey());
					resTextureDTO.setProductId(searchProductGroupDetail.getProductId());
					resTextureDTOList.add(resTextureDTO);
					splitTextureDTO.setList(resTextureDTOList);
					splitTextureDTOList.add(splitTextureDTO);
				}
			}
		}
		searchProductGroupDetail.setIsSplit(isSplit);
		searchProductGroupDetail.setSplitTexturesChoose(splitTextureDTOList);
		return searchProductGroupDetail;
	}

	@Override
	public Integer dealWithBaimoSmallTypeValue(Integer bigTypeValue, Integer smallTypeValue) {
		
		// 参数验证 ->start
		if(smallTypeValue == null){
			return -1;
		}
		if(bigTypeValue == null){
			return -1;
		}
		// 参数验证 ->end
		
		// 如果点击的产品是白膜,处理小类:basic_mengk变成mengk
		Integer smallTypeValueTemp = smallTypeValue;
		// 特殊处理背景墙(basic_beij->beijing)
		if(new Integer(3).equals(bigTypeValue) && new Integer(16).equals(smallTypeValue)){
			smallTypeValueTemp = 17;
		}else{
			smallTypeValueTemp = sysDictionaryService.getProductValueByBaimoValue(bigTypeValue, smallTypeValue) == null ?
					smallTypeValueTemp : sysDictionaryService.getProductValueByBaimoValue(bigTypeValue, smallTypeValue);
		}
		return smallTypeValueTemp;
	}

	/**
	 *	查询出替换产品时，哪些产品需要一起被带出
	 * @param productId	产品ID
	 * @param productSmallTypeCode	产品小类code
	 * @param planId	设计方案ID
	 * @param request	设置这个参数很无奈，decorationProductInfoV3这个方法要传，没办法。本来想改decorationProductInfoV3，无奈用到的点太多，不敢随便改。后面相关开发改了之后会把这个参数去掉。
	 * @return
	 */
	@Override
	public Map<String,CategoryProductResult> autoCarryProduct(Integer productId,String productSmallTypeCode,Integer planId,HttpServletRequest request){
		if(  planId == null || productId == null || StringUtils.isBlank(productSmallTypeCode) ){
			return null;
		}
		DesignPlan designPlan = designPlanService.get(planId);
		if( designPlan == null ){
			return null;
		}
		BaseProduct baseProduct = baseProductMapper.selectByPrimaryKey(productId);
		if( baseProduct == null ){
			return null;
		}
		Map<String,CategoryProductResult> carryProductMap = new HashMap<>();
		Map<String, AutoCarryBaimoProducts> autoCarryMap = this.getAutoCarryMap();
		if( autoCarryMap != null && autoCarryMap.get(productSmallTypeCode) != null ){
			logger.info("当前产品: "+baseProduct.getProductCode()+",小分类：" + productSmallTypeCode+ " 需要自动携带白模信息。");
			// 获取当前产品的属性
			Map<String,String> productAttributeMap = productAttributeService.getPropertyMapV2(productId);
			// 获取当前产品的配置
			AutoCarryBaimoProducts productConfig = autoCarryMap.get(productSmallTypeCode);
			logger.info("当前产品要对比的属性：" + productConfig.getContrastAttributeKey());
			// 获取需要携带哪些产品
			if (productConfig != null) {
				JSONArray productAttributeArray = productConfig.getContrastAttributeKey();// 产品需要比对的属性
				JSONArray carryProductAttributeArray = null;// 携带产品需要对比的属性
				JSONArray carryProductArray = productConfig.getBaimoProducts();// 产品需要携带的产品列表
				Map<String,String> carryProductAttributeMap = new HashMap<>();// key：产品的比对属性；value：携带产品的比对属性
				List<AutoCarryBaimoProducts> carryProducts = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(carryProductArray, AutoCarryBaimoProducts.class);
				// 根据产品的属性，查询同属性的白模产品
				if( Lists.isNotEmpty(carryProducts) ){
					List<ProductAttribute> tempList = null;
					for( AutoCarryBaimoProducts carryProduct : carryProducts ){
						carryProductAttributeArray = carryProduct.getContrastAttributeKey();
						if( carryProductAttributeArray == null ){
							continue;
						}
						// 产品与携带产品属性数量不一致(配置错误)
						if( carryProductAttributeArray.size() != productAttributeArray.size() ){
							logger.info(Utils.getLineNumber() + "产品比对属性与携带产品的比对属性数量不一致（配置错误）");
							continue;
						}
						for( Object object : carryProductAttributeArray ){
							String[] objStr = object.toString().split("#");
							carryProductAttributeMap.put(objStr[0],objStr[1]);
						}
						// 对比产品和需要携带的产品属性
						String productAttribute = "";
						String carryProductAttribute = "";// 产品属性所对应携带产品中的哪个属性
						Map<String, Object> conditionMap = new HashMap<>();
						List<String> conditionList = new ArrayList<>();
						for( int i=0;i<productAttributeArray.size();i++ ){
							StringBuffer conditionStr = new StringBuffer();
							Object object = productAttributeArray.get(i);
							productAttribute = object.toString();
							carryProductAttribute = carryProductAttributeMap.get(productAttribute);
							if( StringUtils.isNotBlank(carryProductAttribute) ){
								Integer productAttributeValue = productAttributeMap.get(productAttribute) == null ? 0 : Integer.valueOf(productAttributeMap.get(productAttribute));
								conditionStr.append("pa.attribute_key = '" + carryProductAttribute
										+ "' AND pa.attribute_type_value = '" + carryProduct.getSmallTypeCode()
										+ "' AND pp.prop_value = " + productAttributeValue);
								/*if (i < productAttributeArray.size() - 1) {
									conditionStr.append(" union all ");
								}*/
								conditionList.add(conditionStr.toString());
							}
						}
						conditionMap.put("conditionCount", conditionList.size());
						conditionMap.put("conditionList", conditionList);

						// 查询属性匹配的产品
						tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
						if( Lists.isNotEmpty(tempList) ){
							logger.info("共查到" + tempList.size() + "条满足要求的白模数据！！！");
							Integer carryProductId = tempList.get(0).getProductId();
							ProductCategoryRel productCategoryRel = new ProductCategoryRel();
							productCategoryRel.setProductId(carryProductId);
							CategoryProductResult categoryProductResult = productCategoryRelService.getCategoryProductResultByProductId(productCategoryRel);
							Map<String,Integer> InfoByIdMap = new HashMap<>();
							InfoByIdMap.put("id",categoryProductResult.getProductId());
							InfoByIdMap.put("designTempletId",designPlan.getDesignTemplateId());
							BaseProduct baseProductBaimo = this.getInfoById(InfoByIdMap);
							decorationProductInfoV3(categoryProductResult, baseProductBaimo, designPlan, null, autoCarryMap,
										/*loginUser,*/
									request);
							// 携带产品的属性
							Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProductBaimo.getId()+"",
									baseProductBaimo.getBigTypeValueKey(),baseProductBaimo.getSmallTypeValueKey(),designPlan.getSpaceCommonId(),designPlan.getDesignTemplateId(),new DesignRules(),categoryProductResult.getPropertyMap());
							categoryProductResult.setRulesMap(rulesMap);
							carryProductMap.put(carryProduct.getSmallTypeCode(),categoryProductResult);
						}
					}
				}
			}
		}
		return carryProductMap;
	}

	private Map<String, AutoCarryBaimoProducts> getAutoCarryMap() {
		Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
		String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
				"design.designPlan.autoCarryBaimoProducts", "");
		if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
			JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
			try {
				@SuppressWarnings("unchecked")
				List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
						.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
				if (autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0) {
					for (AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs) {
						autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(),
								autoCarryBaimoProductsConfig);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取自动携带白模产品配置异常！");
			}
		}
		return autoCarryMap;
	}

	@Override
	public BaseProductSearch getBaseProductSearchByPlanProductInfo(PlanProductInfo planProductInfo) {
		
		// *参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		// *参数验证 ->end
		
		StringBuffer matchInfo = new StringBuffer("搜索条件:");
		BaseProductSearch baseProductSearch = new BaseProductSearch();
		
		String bigTypeValueKey = null;
		String smallTypeValuekey = null;
		if(StringUtils.isNotEmpty(planProductInfo.getBigTypeValuekey())) {
			bigTypeValueKey = planProductInfo.getBigTypeValuekey();
		}else {
			if(StringUtils.isNotEmpty(planProductInfo.getBigTypeValuekeyInit())) {
				bigTypeValueKey = planProductInfo.getBigTypeValuekeyInit();
			}
		}
		
		if(StringUtils.isNotEmpty(planProductInfo.getSmallTypeValuekey())) {
			smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		}else {
			if(StringUtils.isNotEmpty(planProductInfo.getSmallTypeValuekeyInit())) {
				smallTypeValuekey = planProductInfo.getSmallTypeValuekeyInit();
			}
		}
		
		// 产品大类
		if(StringUtils.isNotEmpty(bigTypeValueKey)) {
			SysDictionary sysDictionary = sysDictionaryService.findOneByValueKey(bigTypeValueKey);
			if(sysDictionary == null) {
				logger.error("function:BaseProductServiceImpl.getBaseProductSearchByPlanProductInfo->"
						+ "数据字典没有找到;valuekey:" + bigTypeValueKey);
				return null;
			}else {
				baseProductSearch.setProductTypeValue(sysDictionary.getValue() + "");
				matchInfo.append("ProductTypeValue:" + baseProductSearch.getProductTypeValue() + ";");
			}
		}
		// 产品小类
		if(StringUtils.isNotEmpty(smallTypeValuekey)) {
			smallTypeValuekey = smallTypeValuekey.replace("basic_", "");
			SysDictionary sysDictionary = sysDictionaryService.findOneByValueKey(smallTypeValuekey);
			if(sysDictionary == null) {
				logger.error("function:BaseProductServiceImpl.getBaseProductSearchByPlanProductInfo->"
						+ "数据字典没有找到;valuekey:" + planProductInfo.getBigTypeValuekey());
				return null;
			}else {
				baseProductSearch.setProductSmallTypeValue(sysDictionary.getValue());
				matchInfo.append("ProductSmallTypeValue:" + baseProductSearch.getProductSmallTypeValue() + ";");
			}
		}
		// 产品小类排序
		if(StringUtils.isNotBlank(planProductInfo.getOrderSmallTypeValueKey())) {
			SysDictionary sysDictionary = sysDictionaryService.findOneByValueKey(planProductInfo.getOrderSmallTypeValueKey().replace("basic_", ""));
			if(sysDictionary == null) {
				logger.error("function:BaseProductServiceImpl.getBaseProductSearchByPlanProductInfo->"
						+ "数据字典没有找到;valuekey:" + planProductInfo.getBigTypeValuekey());
				return null;
			}else {
				baseProductSearch.setOrderSmallTypeValue(sysDictionary.getValue());
				matchInfo.append("OrderSmallTypeValue:" + baseProductSearch.getProductSmallTypeValue() + ";");
			}
		}
		// 产品长度
		if(StringUtils.isNotEmpty(planProductInfo.getProductLength())) {
			baseProductSearch.setProductLength(planProductInfo.getProductLength());
			matchInfo.append("ProductLength:" + baseProductSearch.getProductLength() + ";");
		}
		// 产品宽度
		if(StringUtils.isNotEmpty(planProductInfo.getProductWidth())) {
			baseProductSearch.setProductWidth(planProductInfo.getProductWidth());
			matchInfo.append("ProductWidth:" + baseProductSearch.getProductWidth() + ";");
		}
		// 产品高度
		if(StringUtils.isNotEmpty(planProductInfo.getProductHeight())) {
			baseProductSearch.setProductHeight(planProductInfo.getProductHeight());
			matchInfo.append("ProductHeight:" + baseProductSearch.getProductHeight() + ";");
		}
		// 产品长度范围start
		if(planProductInfo.getProductLengthStart() != null) {
			try {
				baseProductSearch.setProductLengthStartInteger(planProductInfo.getProductLengthStart());
				matchInfo.append("ProductLengthStart:" + planProductInfo.getProductLengthStart() + ";");
			}catch (Exception e) {
				
			}
		}
		// 产品长度范围end
		if(planProductInfo.getProductLengthEnd() != null) {
			try {
				baseProductSearch.setProductLengthEndInteger(planProductInfo.getProductLengthEnd());
				matchInfo.append("ProductLengthEnd:" + planProductInfo.getProductLengthEnd() + ";");
			}catch (Exception e) {
				
			}
		}
		// 尺寸代码
		if(StringUtils.isNotEmpty(planProductInfo.getMeasureCode())) {
			baseProductSearch.setMeasureCode(planProductInfo.getMeasureCode());
			matchInfo.append("MeasureCode:" + baseProductSearch.getMeasureCode() + ";");
		}
		// 款式
		if(planProductInfo.getStyleId() != null) {
			baseProductSearch.setStyleId(planProductInfo.getStyleId());
			matchInfo.append("StyleId:" + baseProductSearch.getStyleId() + ";");
		}
		// 款式排序
		if(planProductInfo.getOrderStyleId() != null) {
			baseProductSearch.setOrderStyleId(planProductInfo.getOrderStyleId());
			matchInfo.append("OrderStyleId:" + baseProductSearch.getStyleId() + ";");
		}
		// 白膜id(定制产品对应的白膜id)
		if(StringUtils.isNotEmpty(planProductInfo.getBmIds())) {
			baseProductSearch.setBmIds(planProductInfo.getBmIds());
			matchInfo.append("BmIds:" + baseProductSearch.getBmIds() + ";");
		}
		if(StringUtils.isNotEmpty(planProductInfo.getWallType())) {
			baseProductSearch.setWallType(planProductInfo.getWallType());
			matchInfo.append("WallType:" + baseProductSearch.getWallType() + ";");
		}
		// 布局标识
		/*if(planProductInfo.getProductSmallpoxIdentify() != null && 0 != planProductInfo.getProductSmallpoxIdentify().intValue()) {*/
		if(planProductInfo.getProductSmallpoxIdentify() != null && !StringUtils.equals("0", planProductInfo.getProductSmallpoxIdentify())) {
			baseProductSearch.setProductSmallpoxIdentify(planProductInfo.getProductSmallpoxIdentify());
			matchInfo.append("ProductSmallpoxIdentify:" + baseProductSearch.getProductSmallpoxIdentify() + ";");
		}
		// 布局标识list
		if(Lists.isNotEmpty(planProductInfo.getProductSmallpoxIdentifyList())) {
			baseProductSearch.setIdentifyList(planProductInfo.getProductSmallpoxIdentifyList());
		}
		// 产品过滤属性
		if(Lists.isNotEmpty(planProductInfo.getProductFilterPropList())) {
			baseProductSearch.setProductFilterPropList(planProductInfo.getProductFilterPropList());
		}
		// 是否是背景墙
		if(planProductInfo.isBeijing()) {
			baseProductSearch.setBeijing(planProductInfo.isBeijing());
		}
		// 产品品牌排序
		if(planProductInfo.getOrderBrandId() == null) {
			baseProductSearch.setOrderBrandId(planProductInfo.getOrderBrandId());
		}
		// 产品型号排序
		if(StringUtils.isNotEmpty(planProductInfo.getOrderProductModelNumber())) {
			baseProductSearch.setOrderProductModelNumber(planProductInfo.getOrderProductModelNumber());
		}
		// 产品系列
		if(planProductInfo.getSeriesId() != null && 0 != planProductInfo.getSeriesId().intValue()) {
			baseProductSearch.setSeriesId(planProductInfo.getSeriesId());
		}
		// 产品小类list(排除)
		if(Lists.isNotEmpty(planProductInfo.getExcludeSmallTypeValueList())) {
			baseProductSearch.setExcludeSmallTypeValueList(planProductInfo.getExcludeSmallTypeValueList());
		}
		// 小类list
		if(Lists.isNotEmpty(planProductInfo.getSmallTypeValueList())) {
			baseProductSearch.setSmallTypeValueListForShowAll(planProductInfo.getSmallTypeValueList());
			baseProductSearch.setProductSmallTypeValue(null);
			baseProductSearch.setExcludeSmallTypeValueList(null);
			matchInfo.append("SmallTypeValuekeyList:" + baseProductSearch.getSmallTypeValueListForShowAll() + ";");
		}
		// 布局标识排序
		if(StringUtils.isNotEmpty(planProductInfo.getOrderProductSmallpoxIdentify())) {
			baseProductSearch.setOrderProductSmallpoxIdentify(planProductInfo.getOrderProductSmallpoxIdentify());
		}
		planProductInfo.setMatchInfo(matchInfo.toString());
		return baseProductSearch;
	}

	@Override
	public List<BaseProduct> selectProductEasy(BaseProductSearch baseProductSearch) {
		// *参数验证 ->start
		if(baseProductSearch == null) {
			return null;
		}
		if(StringUtils.isEmpty(baseProductSearch.getProductTypeValue())) {
			return null;
		}
		// *参数验证 ->end
		return baseProductMapper.selectProductEasy(baseProductSearch);
	}
	
	public void removeNull(CategoryProductResult productResult) {
		if(productResult == null) {
			return;
		}
		if(productResult.getId() == null) { productResult.setId(0); }
		if(productResult.getProductId() == null) { productResult.setProductId(0); }
		if(productResult.getProductTypeValue() == null) { productResult.setProductTypeValue(0); }
		if(productResult.getProductSmallTypeValue() == null) { productResult.setProductSmallTypeValue(0); }
		if(productResult.getCollectState() == null) { productResult.setCollectState(0); }
			
		if(productResult.getProductCount() == null) { productResult.setProductCount(0); }
		if(productResult.getOrderType() == null) { productResult.setOrderType(0); }
		if(productResult.getModelProductId() == null) { productResult.setModelProductId(0); }
		if(productResult.getBgWall() == null) { productResult.setBgWall(0); }
		if(productResult.getSpaceCommonId() == null) { productResult.setSpaceCommonId(0); }
		if(productResult.getParentId() == null) { productResult.setParentId(0); }
 
		if(productResult.getParentProductId() == null) { productResult.setParentProductId(0); }
		if(productResult.getBrandId() == null) { productResult.setBrandId(0); }
		if(productResult.getProductOrdering() == null) { productResult.setProductOrdering(0); }
		if(productResult.getIsCustomized() == null) { productResult.setIsCustomized(0); }
		if(productResult.getFullPaveLength() == null) { productResult.setFullPaveLength(0); }
		if(productResult.getTargetBmProductId() == null) { productResult.setTargetBmProductId(0); }
		if(productResult.getTextureAttrValue() == null) { productResult.setTextureAttrValue(0); }
		if(productResult.getColorsOrdering() == null) { productResult.setColorsOrdering(0); }
		if(productResult.getTargetBmProductId() == null) { productResult.setTargetBmProductId(0); }
			 
		if(productResult.getIsStandard() == null) { productResult.setIsStandard(0); }
		if(productResult.getStyleId() == null) { productResult.setStyleId(0); }
		if(productResult.getProductSmallpoxIdentify() == null) { productResult.setProductSmallpoxIdentify(0); }
 
	}

	@Override
	public String getPropFilterInfo(Integer productId) {
		// 参数验证/处理 ->start
		if(productId == null) {
			return null;
		}
		// 参数验证/处理 ->end
		
		return baseProductMapper.getPropFilterInfo(productId);
	}

	@Override
	public List<ProductPropsSimple> getProductFilterPropList(Integer productId) {
		// 参数验证 ->start
		if(productId == null) {
			return null;
		}
		// 参数验证 ->end
		
		// 获取主产品的过滤属性
		List<ProductPropsSimple> productFilterPropList = null;
		if(productId != null) {
			String propInfo = this.getPropFilterInfo(productId);
			// 组装产品属性信息
			if(StringUtils.isNotBlank(propInfo)){
				productFilterPropList = new ArrayList<ProductPropsSimple>();
				List<String> propInfoItemList = Utils.getListFromStr(propInfo, ";");
				for(String propInfoItem : propInfoItemList){
					String[] strs = propInfoItem.split(",");
					ProductPropsSimple productPropsSimple = null;
					try{
						productPropsSimple = new ProductPropsSimple(Integer.parseInt(strs[1]), strs[0], Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
					}catch(Exception e){
						logger.error("------propInfo转化为ProductPropsSimple失败,propInfoItem:" + propInfoItem + "baseProduct=>getProductId=" + productId);
						continue;
					}
					productFilterPropList.add(productPropsSimple);
				}
			}
		}
		return productFilterPropList;
	}

	@Override
	public List<ProductPropsSimple> getProductPropsSimpleByProductId(Integer productId,
			String productAttrCode) {
		// 参数验证/处理 ->start
		if(productId == null) {
			return null;
		}
		if(StringUtils.isEmpty(productAttrCode)) {
			return null;
		}
		// 参数验证/处理 ->end
		
		return baseProductMapper.getProductPropsSimpleByProductIdAndAttrCode(productId, productAttrCode);
	}

	/**
	 * 通过结构id 获取结构里的产品 详情	和 windows模型地址	 
	 * @param structureProductId
	 * @return
	 */
	@Override
	public List<ProductSimpleModel> getProductListByStructureId(Integer structureProductId) {
		return baseProductMapper.getProductListByStructureId(structureProductId);
	}

	@Override
	public List<String> getProductIdentification(Integer productId) {
		return baseProductMapper.getProductIdentification(productId);
	}

	/**
	 * 获取系列产品匹配数据
	 * @param planProduct
	 * @param loginUser
	 * @param seriesId
	 * @author xiaoxc
	 * @return
	 */
	@Override
	public CategoryProductResult getSeriesProductData(DesignProductResult planProduct,LoginUser loginUser,Integer seriesId){
		CategoryProductResult matchProductData = null;
		Integer productSmallTypeValue = planProduct.getSmallTypeValue();
		//如果点击的产品是白膜,处理小类:basic_mengk变成mengk
		if (planProduct.getProductCode().startsWith("baimo_")) {
			if (!StringUtils.isEmpty(planProduct.getBigTypeValue())) {
				Integer smallValue = sysDictionaryService.getProductValueByBaimoValue(Utils.getIntValue(planProduct.getBigTypeValue()), planProduct.getBmSmallTypeValue());
				productSmallTypeValue = smallValue == null?productSmallTypeValue:smallValue;
			}
		}

		BaseProductSearch searchProduct = new BaseProductSearch();

		//获取小类key
		String smallTypeValueKey  = planProduct.getSmallTypeKey().replace("basic_", "");

		//判断为非橱柜全台面产品
		if(!StringUtils.equals(smallTypeValueKey,ProductTypeConstant.PRODUCT_SMALL_TYPE_CTQKI)){
			List<Integer> smallValueList = this.getSmallValueList(planProduct.getBigTypeKey(),smallTypeValueKey);
			if (smallValueList != null && smallValueList.size() > 0) {
				searchProduct.setProductSmallValueList(smallValueList);
			}
			//设置全铺长度过滤和属性过滤
			this.setProductFullLengthFilterRange(searchProduct,planProduct);
		}

		searchProduct.setProductTypeValue(planProduct.getBigTypeValue());
		searchProduct.setProductSmallTypeValue(productSmallTypeValue);

		searchProduct.setProductTypeCode(planProduct.getBigTypeKey());
		searchProduct.setPutawayStateList(this.getPutawayList(loginUser));
		searchProduct.setSeriesId(seriesId);
		searchProduct.setStart(0);
		searchProduct.setLimit(1);
		List<CategoryProductResult> productList = baseProductMapper.getSeriesProductData(searchProduct);
		if (productList != null && productList.size() > 0) {
			matchProductData = productList.get(0);
			return matchProductData;
		}else{
			return null;
		}
	}

	/**
	 * 获取小类可以匹配其它小分类
	 * @param smallType
	 * @param smallValueKey
	 * @author xiaoxc
	 * @return
	 */
	private List<Integer> getSmallValueList(String smallType, String smallValueKey){
		if (StringUtils.isEmpty(smallType) || StringUtils.isEmpty(smallValueKey)) {
			return null;
		}
		List<Integer> smallTypeValueList = new ArrayList<>();
		String showMoreSmallTypeValue = ProductModelConstant.PRODUCT_SEARCHPRODUCT_SHOWMORESMALLTYPE_VALUE;
		if (StringUtils.isNotEmpty(showMoreSmallTypeValue)) {
			JSONArray jsonArray = JSONArray.fromObject(showMoreSmallTypeValue);
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					List<String> checkTypeList = Utils.getListFromStr(jsonObj.getString("checkType"));
					List<String> showTypeList = Utils.getListFromStr(jsonObj.getString("showType"));
					if (checkTypeList.indexOf(smallValueKey) != -1) {
						smallTypeValueList = sysDictionaryService.getValueByTypeAndValueKeylist(smallType, showTypeList);
						break;
					}
				}
			}
		}
		return smallTypeValueList;
	}

	/**
	 * 设置全铺长度过滤范围
	 * @param searchProduct
	 * @param baimoProduct
	 * @author xiaoxc
	 * @return
	 */
	private BaseProductSearch setProductFullLengthFilterRange(BaseProductSearch searchProduct,DesignProductResult baimoProduct) {
		if (baimoProduct == null) {
			return searchProduct;
		}
		Map<String,String> stretchZoomMap = getStretchZoomLength(baimoProduct.getSmallTypeKey());
		if (stretchZoomMap != null && stretchZoomMap.size() > 0) {
			searchProduct.setStretch(true);
			int stretchZoomLength = Utils.getIntValue(stretchZoomMap.get(ProductModelConstant.STRETCH_LENGTH));
			String isHeightFilter = stretchZoomMap.get(ProductModelConstant.IS_HEIGHT_FILTER);
			//是否需要高度过滤，是则设置高度值
			if (ProductModelConstant.HEIGHT_FILTER_VALUE_YES.equals(isHeightFilter)) {
				searchProduct.setStretchHeight(baimoProduct.getProductHeight());
			}
			String fullPaveLength = baimoProduct.getFullPaveLength();
			if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0) {
				searchProduct.setStartLength(Utils.getIntValue(fullPaveLength) - stretchZoomLength + 1);
				searchProduct.setEndLength(Utils.getIntValue(fullPaveLength) + stretchZoomLength);
			}else{
				searchProduct.setStretchHeight("0");
				searchProduct.setEndLength(-1);
				searchProduct.setStartLength(-1);
			}
		}
		//过滤产品属性
		productAttributeService.getOnekeyDecorateProductAttributeFilterCondition(searchProduct,baimoProduct.getProductId());

		return searchProduct;
	}


	/**
	 * 获取拉伸缩放长度
	 * @param smallTypeKey
	 * @author xiaoxc
	 * @return Map<String,String>
	 */
	@Override
	public Map<String,String> getStretchZoomLength(String smallTypeKey){
		if (StringUtils.isEmpty(smallTypeKey)) {
			return null;
		}
		Map<String,String> map = new HashMap<>();
		String stretchProductTypeArray = ProductModelConstant.PRODUCT_SEARCH_STRETCH_CONFIG;
		if (StringUtils.isNotEmpty(stretchProductTypeArray)) {
			JSONArray jsonArray = JSONArray.fromObject(stretchProductTypeArray);
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					String stretchProductTypes = ","+jsonObj.getString("productTypeKey")+",";
					boolean falg = Utils.isMateProductType(smallTypeKey,stretchProductTypes);
					if (falg) {
						map.put(ProductModelConstant.STRETCH_LENGTH, jsonObj.getString(ProductModelConstant.STRETCH_LENGTH));
						map.put(ProductModelConstant.IS_HEIGHT_FILTER ,jsonObj.getString(ProductModelConstant.IS_HEIGHT_FILTER));
						break;
					}
				}
			}
		}
		return map;
	}

	/**
	 * 获取拉伸缩放长度
	 * @param productTypevlaue
	 * @param productSmallTypeValue
	 * @author xiaoxc
	 * @return Map<String,String>
	 */
	@Override
	public Map<String,String> getStretchZoomLength(Integer productTypevlaue, Integer productSmallTypeValue){
		if (productTypevlaue == null || productSmallTypeValue == null) {
			return null;
		}
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue(ProductTypeConstant.SYS_DICTIONARY_PRODUCT_TYPE,productTypevlaue,productSmallTypeValue);
		if (sysDictionary == null) {
			return null;
		}
		Map<String,String> map = new HashMap<>();
		String stretchProductTypeArray = ProductModelConstant.PRODUCT_SEARCH_STRETCH_CONFIG;
		if (StringUtils.isNotEmpty(stretchProductTypeArray)) {
			JSONArray jsonArray = JSONArray.fromObject(stretchProductTypeArray);
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					String stretchProductTypes = ","+jsonObj.getString("productTypeKey")+",";
					boolean falg = Utils.isMateProductType(sysDictionary.getValuekey(),stretchProductTypes);
					if (falg) {
						map.put(ProductModelConstant.STRETCH_LENGTH, jsonObj.getString(ProductModelConstant.STRETCH_LENGTH));
						map.put(ProductModelConstant.IS_HEIGHT_FILTER ,jsonObj.getString(ProductModelConstant.IS_HEIGHT_FILTER));
						break;
					}
				}
			}
		}
		return map;
	}

	@Override
	public String getSeriesSign(String valuekey) {
		// 参数验证/处理 ->start
		if(StringUtils.isBlank(valuekey)) {
			return null;
		}
		valuekey = "basic_" + valuekey.replace("basic_", "");
		// 参数验证/处理 ->end
		// 拼系列标识
		StringBuffer returnString = new StringBuffer("");
		if(Lists.isNotEmpty(seriesConfigList)) {
			for(SeriesConfig seriesConfig : seriesConfigList) {
				List<String> valuekeyList =  seriesConfig.getValuekeyList();
				if(valuekeyList.indexOf(valuekey) != -1) {
					returnString.append(seriesConfig.getKey() + "_");
					List<SeriesConfigItem> seriesConfigItemList = seriesConfig.getSmallTypeValuekeyInfo();
					if(Lists.isNotEmpty(seriesConfigItemList)) {
						for(SeriesConfigItem seriesConfigItem : seriesConfigItemList) {
							List<String> valuekeyListItem = seriesConfigItem.getValuekeyList();
							if(Lists.isNotEmpty(valuekeyListItem)) {
								if(valuekeyListItem.indexOf(valuekey) != -1) {
									returnString.append(seriesConfigItem.getSeriesSign());
									break;
								}
							}
						}
					}
					break;
				}
			}
		}
		return returnString.toString();
	}

	@Override
	public String getSeriesSign(Integer bigTypeValue, Integer smallTypeValue) {
		// 参数验证/处理 ->start
		if(bigTypeValue == null) {
			return null;
		}
		if(smallTypeValue == null) {
			return null;
		}
		// 参数验证/处理 ->end
		
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue("productType", bigTypeValue, smallTypeValue);
		if(sysDictionary == null) {
			return null;
		}
		return this.getSeriesSign(sysDictionary.getValuekey());
	}
	
	/**
	 * 读取系列相关配置json转化为bean
	 * 
	 * @author huangsongbo
	 *
	 */
	public static class SeriesConfig implements Serializable{
		
		private static final long serialVersionUID = 7279763391313482088L;

		private List<SeriesConfigItem> smallTypeValuekeyInfo;
		
		private String key;

		/**
		 * 整合所有小类valuekey
		 */
		private List<String> valuekeyList;
		
		public List<String> getValuekeyList() {
			return valuekeyList;
		}

		public void setValuekeyList(List<String> valuekeyList) {
			this.valuekeyList = valuekeyList;
		}

		public List<SeriesConfigItem> getSmallTypeValuekeyInfo() {
			return smallTypeValuekeyInfo;
		}

		public void setSmallTypeValuekeyInfo(List<SeriesConfigItem> smallTypeValuekeyInfo) {
			this.smallTypeValuekeyInfo = smallTypeValuekeyInfo;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
	}
	
	public static class SeriesConfigItem{
		
		private String seriesProductType;
		
		private String seriesSign;

		private List<String> valuekeyList;
		
		public List<String> getValuekeyList() {
			return valuekeyList;
		}

		public void setValuekeyList(List<String> valuekeyList) {
			this.valuekeyList = valuekeyList;
		}

		public String getSeriesProductType() {
			return seriesProductType;
		}

		public void setSeriesProductType(String seriesProductType) {
			this.seriesProductType = seriesProductType;
		}

		public String getSeriesSign() {
			return seriesSign;
		}

		public void setSeriesSign(String seriesSign) {
			this.seriesSign = seriesSign;
		}
		
	}
	
	public static enum getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum{
		in, notIn
	}
	
	@Override
	public Map<getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum, List<Integer>> getsmallTypeValueListBySmallTypeValueKey(String smallTypeValueKey) {
		// 参数验证/处理 ->start
		if(StringUtils.isEmpty(smallTypeValueKey)) {
			return null;
		}
		smallTypeValueKey = smallTypeValueKey.replace("basic_", "");
		// 参数验证/处理 ->end
		
		Map<getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum, List<Integer>> returnMap = new HashMap<BaseProductServiceImpl.getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum, List<Integer>>();
		
		List<String> smallTypeValueKeyList = null;
		List<String> smallTypeValueKeyListNotIn = null;
		// 优先先处理product.searchProduct.showMoreSmallType配置
		Map<String, List<String>> showMoreSmallTypeInfoMap = (Map<String, List<String>>) ProductCategoryRelServiceImpl.showMoreSmallTypeMap.get("showMoreSmallTypeInfoMap");
		if(showMoreSmallTypeInfoMap.containsKey(smallTypeValueKey)) {
			smallTypeValueKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey);
		}else {
			// 再看special.productType配置
			Map<String, List<String>> specialProductTypeMap = ProductCategoryRelServiceImpl.specialProductTypeMap;
			SysDictionary sysDictionary = sysDictionaryService.findOneByValueKey(smallTypeValueKey);
			if(sysDictionary == null) {
				logger.error("sysDictionary not found:valuekey = " + sysDictionary.getValuekey());
				return null;
			}
			String bigTypeValueKey = sysDictionary.getType();
			if(specialProductTypeMap == null) {
				return null;
			}
			if(specialProductTypeMap.containsKey(bigTypeValueKey)) {
				List<String> specialProductTypeMapSmallTypeValueKeyList = specialProductTypeMap.get(bigTypeValueKey);
				if(Lists.isEmpty(specialProductTypeMapSmallTypeValueKeyList)) {
					return null;
				}
				if(specialProductTypeMapSmallTypeValueKeyList.indexOf(smallTypeValueKey) != -1) {
					// in
					smallTypeValueKeyList = specialProductTypeMapSmallTypeValueKeyList;
				}else {
					// notIn
					smallTypeValueKeyListNotIn = specialProductTypeMapSmallTypeValueKeyList;
				}
			}
		}
		
		// 处理smallTypeValueKeyList成smallTypeValueList
		returnMap.put(getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum.in, sysDictionaryService.getValueListBySmallTypeValueKeyList(smallTypeValueKeyList));
		returnMap.put(getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum.notIn, sysDictionaryService.getValueListBySmallTypeValueKeyList(smallTypeValueKeyListNotIn));
		return returnMap;
	}

	@Override
	public List<CategoryProductResult> searchProductForReplaceTub(
			String smallType,
			LoginUser loginUser
			) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(smallType)) {
			return null;
		}
		smallType = smallType.replace("basic_", "");
		// 参数验证 ->end
		
		// 获取搜索配置ReplaceTubProperties
		// 检测smallType是否包含在配置内
		if(Lists.isEmpty(replaceTubPropertiesList)) {
			return null;
		}
		ReplaceTubProperties replaceTubProperties = null;
		for(ReplaceTubProperties replaceTubPropertiesItem : replaceTubPropertiesList) {
			if(StringUtils.equals(smallType, replaceTubPropertiesItem.getReplacedProductSmallType())) {
				replaceTubProperties = replaceTubPropertiesItem;
				break;
			}
		}
		if(replaceTubProperties == null) {
			logger.error("function:searchProductForReplaceTub->参数smallType没有找到对应配置");
			return null;
		}
		List<CategoryProductResult> result = null;
		
		// 设置查询条件 ->start
		BaseProductSearch baseProductSearch = this.setSearchPropForFunctionSearchProductForReplaceTub(replaceTubProperties.getAffiliateProductSmallType(), loginUser);
		if(baseProductSearch == null) {
			return null;
		}
		baseProductSearch.setStart(0);
		baseProductSearch.setLimit(1);
		// 设置查询条件 ->end
		
		if(Lists.isEmpty(replaceTubProperties.getProductPropsList())) {
			
		}else {
			for(ProductPropsSimple productPropsSimple : replaceTubProperties.getProductPropsList()) {
				List<ProductPropsSimple> productPropsSimpleList = new ArrayList<ProductPropsSimple>();
				productPropsSimpleList.add(productPropsSimple);
				baseProductSearch.setProductFilterPropList(productPropsSimpleList);
				result = this.selectProductEasyV2(baseProductSearch);
				if(Lists.isNotEmpty(result)) {
					break;
				}
			}
		}
		// 补充产品信息 ->start
		this.getMoreProductInfo(result);
		// 补充产品信息 ->end
		return result;
	}
	
	private void getMoreProductInfo(List<CategoryProductResult> result) {
		if(Lists.isEmpty(result)) {
			return;
		}
		// copy ->start
		for(CategoryProductResult categoryProductResult : result){
			categoryProductResult.setModelProductId(categoryProductResult.getProductId());
			// 软硬装:0:软装;1:硬装
			String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1().trim();
			categoryProductResult.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1);
			// bgWall:是否是背景墙
			Integer bgWallValue = this.getBgWallValue(categoryProductResult.getProductTypeCode(),categoryProductResult.getProductSmallTypeCode());
			categoryProductResult.setBgWall(bgWallValue);
			
			// 产品附加属性->start
			Map<String, String> map = new HashMap<String, String>();
			map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
			categoryProductResult.setPropertyMap(map);
			// 产品附加属性->end
		}
		// copy ->end
	}

	private List<CategoryProductResult> selectProductEasyV2(BaseProductSearch baseProductSearch) {
		// *参数验证 ->start
		if(baseProductSearch == null) {
			return null;
		}
		if(StringUtils.isEmpty(baseProductSearch.getProductTypeValue())) {
			return null;
		}
		// *参数验证 ->end
		return baseProductMapper.selectProductEasyV2(baseProductSearch);
	}

	/**
	 * 设置查询条件
	 * 专用于searchProductForReplaceTub方法
	 * 
	 * @author huangsongbo
	 * @param smallType
	 * @param loginUser 
	 * @param baseProductSearch
	 * @return 
	 */
	private BaseProductSearch setSearchPropForFunctionSearchProductForReplaceTub(String smallType, LoginUser loginUser) {
		SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByValueKey(smallType);
		if(sysDictionarySmallType == null) {
			logger.error("function:searchProductForReplaceTub->sysDictionary not found(valuekey = " + smallType + ")");
			return null;
		}
		SysDictionary sysDictionaryBigType = sysDictionaryService.findOneByValueKey(sysDictionarySmallType.getType());
		if(sysDictionaryBigType == null) {
			logger.error("function:searchProductForReplaceTub->sysDictionary not found(valuekey = " + sysDictionarySmallType.getType() + ")");
			return null;
		}
		BaseProductSearch baseProductSearch = new BaseProductSearch();
		baseProductSearch.setProductTypeValue(sysDictionaryBigType.getValue() + "");
		baseProductSearch.setProductSmallTypeValue(sysDictionarySmallType.getValue());
		// 设置产品状态查询条件 ->start
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();
		if(loginUser != null && loginUser.getUserType() == 1 && "2".equals(versionType)) {
			baseProductSearch.setIsInternalUser("true");
		}
		// 设置产品状态查询条件 ->end
		return baseProductSearch;
	}

	/**
	 * 面碰/浴缸替换带入水龙头配置
	 * 
	 * @author huangsongbo
	 *
	 */
	public static class ReplaceTubProperties implements Serializable{
		
		private static final long serialVersionUID = -3586795374300283073L;

		/**
		 * 被替换的产品小类(面盆/浴缸)
		 */
		private String replacedProductSmallType;
		
		/**
		 * 附属产品小类(替换面盆,搜索水龙头信息,水龙头为附属小类)
		 */
		private String affiliateProductSmallType;
		
		/**
		 * 搜索水龙头,要满足的属性条件(并集,满足属性List其一就好)
		 */
		private List<ProductPropsSimple> productPropsList;

		public String getReplacedProductSmallType() {
			return replacedProductSmallType;
		}

		public void setReplacedProductSmallType(String replacedProductSmallType) {
			this.replacedProductSmallType = replacedProductSmallType;
		}

		public String getAffiliateProductSmallType() {
			return affiliateProductSmallType;
		}

		public void setAffiliateProductSmallType(String affiliateProductSmallType) {
			this.affiliateProductSmallType = affiliateProductSmallType;
		}

		public List<ProductPropsSimple> getProductPropsList() {
			return productPropsList;
		}

		public void setProductPropsList(List<ProductPropsSimple> productPropsList) {
			this.productPropsList = productPropsList;
		}
		
	}

	/**
	 * 匹配把手产品数据
	 * @author xiaoxc
	 * @param putawayStateList 产品状态
	 * @param seriesId  系列ID
	 * @param attributeType 属性分类
	 * @param attributeValueKey 属性key
	 * @return
	 */
	@Override
	public List<CategoryProductResult> getHandleProductData(List<Integer> putawayStateList,Integer seriesId, String attributeType, String attributeValueKey){
		return baseProductMapper.getHandleProductData(putawayStateList,seriesId,attributeType,attributeValueKey);
	}

	@Override
	public List<CategoryProductResult> getInfoByProductCode(List<String> productCodeList) {
		// 参数验证 ->start
		if(Lists.isEmpty(productCodeList)) {
			return null;
		}
		// 参数验证 ->end
		
		List<CategoryProductResult> result = this.getSimpleInfoByProductCode(productCodeList);
		if(Lists.isNotEmpty(result)) {
			for(CategoryProductResult categoryProductResult : result) {
				// copy from WebBaseSeriesController.matchinghandleProduct->start
	            //设置产品多维材质
	            productCategoryRelService.dealWithTextureInfo(categoryProductResult);
	            // 软硬装:0:软装;1:硬装
	            String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1();
	            categoryProductResult.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1.trim());
	            Map<String, String> map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
	            categoryProductResult.setPropertyMap(map);
				// copy from WebBaseSeriesController.matchinghandleProduct ->end
			}
		}
		return result;
	}

	@Override
	public List<CategoryProductResult> getSimpleInfoByProductCode(List<String> productCodeList) {
		// 参数验证 ->start
		if(Lists.isEmpty(productCodeList)) {
			return null;
		}
		// 参数验证 ->end
		
		return baseProductMapper.getSimpleInfoByProductCode(productCodeList);
	}
	
	/**
	 * 通过idList查询产品详情
	 * add by yangzhun 2017-12-28 15:16:45
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ResponseEnvelope getDetailByIds(List<Integer> idList, Integer limit, Integer start, Integer userId,
			String msgId, Integer isStandard, String regionMark, Integer styleId, String measureCode) {
		//定义一个返回数据的list
		List<CategoryProductResult> returnList = new ArrayList<>();
		Map<Integer ,CategoryProductResult> resultMap = new HashMap<>();
		
		List<CategoryProductResult> datalist = baseProductMapper.getDetailByIds(idList, limit, start, userId);
		// 获取需要自动携带白模产品的配置->start
//		Map<String, AutoCarryBaimoProducts> autoCarryMap = this.getAutoCarryMap();
		// 获取需要自动携带白模产品的配置->end
		for(CategoryProductResult categoryProductResult : datalist){
			// 处理材质返回格式
			productCategoryRelService.dealWithTextureInfo(categoryProductResult);

			/** 处理产品 橱柜 信息*/
			baseProductCountertopsService.setCountertopsDetails(categoryProductResult);

//			categoryProductResult.setSearchLength(String.valueOf(stretchZoomLength));
			// 补充信息->start
			categoryProductResult.setModelProductId(categoryProductResult.getProductId());
			// 软硬装:0:软装;1:硬装
			String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1().trim();
			categoryProductResult.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1);
			// bgWall:是否是背景墙
			Integer bgWallValue = this.getBgWallValue(categoryProductResult.getProductTypeCode(),categoryProductResult.getProductSmallTypeCode());
			categoryProductResult.setBgWall(bgWallValue);
			// 产品附加属性->start
			Map<String, String> map = new HashMap<String, String>();
			map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
			categoryProductResult.setPropertyMap(map);
			// 产品附加属性->end
			// 自动携带白膜属性(未优化) ->start
//			this.setBasicModelMap(categoryProductResult, autoCarryMap, map, designPlan, designPlanProduct, mediaType);
			// 自动携带白膜属性(未优化) ->end
			//标准、款式、尺寸代码回填
			categoryProductResult.setStyleId(styleId);
			categoryProductResult.setIsStandard(isStandard);
			categoryProductResult.setRegionMark(regionMark);
			categoryProductResult.setMeasureCode(measureCode);
			// 补充信息->end
			//将得到的list转换成一个以id为key，数据为value的map
			resultMap.put(categoryProductResult.getProductId(),categoryProductResult);
		}
		//通过遍历idList，将返回数据按照idList的顺序排序
		for(Integer id : idList) {
//			if(null != resultMap.get(id)) {
				returnList.add(resultMap.get(id));
//			}
		}
		
		return new ResponseEnvelope<>(idList.size(),returnList,msgId);
	}

	@Override
	public SearchStructureProductDetailResult getStructureDetailsSearch(BaseProduct baseProduct, Integer mediaType) {
		SearchStructureProductDetailResult searchStructureProductDetailResult = new SearchStructureProductDetailResult();
		searchStructureProductDetailResult.setProductId(baseProduct.getId());
		searchStructureProductDetailResult.setProductCode(baseProduct.getProductCode());
		searchStructureProductDetailResult.setProductWidth(baseProduct.getProductWidth());
		searchStructureProductDetailResult.setProductLength(baseProduct.getProductLength());
		searchStructureProductDetailResult.setProductHeight(baseProduct.getProductHeight());
		/* u3dModelPath */
		String modelId = this.getU3dModelId(mediaType.toString(), baseProduct);
		if (StringUtils.isNotBlank(modelId)) {
			ResModel resModel = resModelService.get(Integer.valueOf(modelId));
			if (resModel != null) {
				/*
				 * File modelFile = new File(Utils.getValue("app.upload.root", "") +
				 * resModel.getModelPath());
				 */
				File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt));
				if (modelFile.exists()) {
					searchStructureProductDetailResult.setU3dModelPath(resModel.getModelPath());
					searchStructureProductDetailResult.setModelMinHeight(resModel.getMinHeight());
				}
			}
		}
		/* u3dModelPath->end */
		/* 大类value,code,name */
		String typeValue = baseProduct.getProductTypeValue();
		SysDictionary bigTypeDic = null;
		if (StringUtils.isNotBlank(typeValue)) {
			bigTypeDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
			searchStructureProductDetailResult.setProductTypeValue(bigTypeDic.getValue());
			searchStructureProductDetailResult.setProductTypeCode(bigTypeDic.getValuekey());
			searchStructureProductDetailResult.setProductTypeName(bigTypeDic.getName());
		}
		/* 大类value,code,name->end */
		/* rootType,小类value,code,name */
		Integer smallType = baseProduct.getProductSmallTypeValue();
		if (smallType != null && smallType.intValue() > 0) {
			if (bigTypeDic != null) {
				SysDictionary smallTypeDic = sysDictionaryService.findOneByTypeAndValue(bigTypeDic.getValuekey(), smallType);
				searchStructureProductDetailResult.setProductSmallTypeValue(smallTypeDic.getValue().toString());
				searchStructureProductDetailResult.setProductSmallTypeCode(smallTypeDic.getValuekey());
				searchStructureProductDetailResult.setProductSmallTypeName(smallTypeDic.getName());
				String rootType = StringUtils.isEmpty(smallTypeDic.getAtt1()) ? "2" : smallTypeDic.getAtt1().trim();
				searchStructureProductDetailResult.setRootType(rootType);
			}
		}
		/* rootType,小类value,code,name->end */
		/* 封面图片路径 */
		Integer picId = baseProduct.getPicId();
		if (picId != null && picId > 0) {
			ResPic resPic = resPicService.get(picId);
			if (resPic != null) {
				searchStructureProductDetailResult.setPicPath(resPic.getPicPath());
			}
		}
		/* 封面图片路径->end */
		return searchStructureProductDetailResult;
	}

	/**
	 * 获取截面天花数据
	 * @param baseProduct
	 * @param putawayStateList
	 */
	@Override
	public void getProductCeilingVO(BaseProduct baseProduct, List<Integer> putawayStateList) {
		if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(baseProduct.getProductTypeCode())) {
			ProductCeilingVO productCeilingVO = new ProductCeilingVO();
			//TODO 是截面天花直接获取本身产品信息，否则通过款式匹配，匹配不到给默认截面信息和款式
			if (ProductTypeConstant.PRODUCT_SMALL_TYPE_JIEM.equals(baseProduct.getProductSmallTypeCode())) {
				List<ProductCeilingVO> productCeilingVOList = this.getCeilingInfoByProductCode(baseProduct.getProductCode());
				if (null != productCeilingVOList && 0 < productCeilingVOList.size()) {
					productCeilingVO = productCeilingVOList.get(0);
				}
			} else {
				//通过款式匹配
				List<ProductCeilingVO> productCeilingVOList = this.getCeilingCrossSectionDataByStyleId(putawayStateList, baseProduct.getStyleId());
				if (null != productCeilingVOList && 0 < productCeilingVOList.size()) {
					productCeilingVO = productCeilingVOList.get(0);
				} else {
					//查询默认的款式截面天花
					productCeilingVOList = this.getCeilingInfoByProductCode(ProductModelConstant.DEFAULT_JIEM_TIANH_PRODUCT_CODE);
					if (null != productCeilingVOList && 0 < productCeilingVOList.size()) {
						productCeilingVO = productCeilingVOList.get(0);
					}
				}
			}
			productCeilingVO.setRegionMark(baseProduct.getRegionMark());
			baseProduct.setProductCeilingVO(productCeilingVO);
		}
	}

	/**
	 * 通过款式匹配天花截面数据
	 * @author xiaoxc
	 * @param putawayStateList
	 * @param styleId
	 * @return
	 */
	@Override
	public List<ProductCeilingVO> getCeilingCrossSectionDataByStyleId(List<Integer> putawayStateList, Integer styleId) {
		return baseProductMapper.getCeilingCrossSectionDataByStyleId(putawayStateList, styleId);
	}

	/**
	 * 通过产品Id获取产品款式
	 * @author xiaoxc
	 * @param productCode
	 * @return
	 */
	@Override
	public List<ProductCeilingVO> getCeilingInfoByProductCode(String productCode) {
		return baseProductMapper.getCeilingInfoByProductCode(productCode);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductGroup(String groupCode, Integer designPlanId, Integer spaceCommonType,
        String config, Integer isBmGroup, String msgId, HttpServletRequest request,
        String structureCode, String spaceCommonTypeCode, String designTempletCode, Integer groupType,
        Integer isMainProductId, Integer compositeTypeValue,MultipartFile coverPicFile,String authorization) throws BaseProductException,Exception {
		/**
		 * 参数校验 begin
		 */
		if (groupType == null) {
			groupType = 0;
		} else {
			if (groupType.intValue() != GroupProductTypeConstant.TYPE_COMMON && groupType.intValue() != GroupProductTypeConstant.TYPE_INTELLIGENCE) {
				return new ResponseEnvelope<BaseProduct>(false, "参数groupType不正确!现只支持0(普通组合)或1(一建装修组合)", msgId);
			}
		}

//           LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		//更改用户信息读取方式
		Map<String, Object> authorizationMap = Jwt.validToken(authorization);
		request.setAttribute("AuthorizationData", authorizationMap.get("data"));
		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		if (loginUser == null || loginUser.getId() <= 0) {
			logger.error("updateProductGroup用户为空！！authorization：" + authorization);
			return new ResponseEnvelope<BaseProduct>(false, "请重新登录！", msgId);
		}

		if (isBmGroup == null) {
			return new ResponseEnvelope<BaseProduct>(false, "是否是白模组合参数isBmGroup不能为空!", msgId);
		}
		List<SysDictionary> sysList = null;
		Integer spacValue = null;


		if (isBmGroup == 2) {
		  /*判断空间类型是否存在*/
			SysDictionary sysDictionary = new SysDictionary();
//            sysDictionary.setType("spaceType");
			//sysDictionary.setType("productTypeValue");
			sysDictionary.setType("houseType");
			sysDictionary.setValuekey(spaceCommonTypeCode);
			sysList = sysDictionaryService.getList(sysDictionary);
			if (sysList == null || sysList.size() <= 0) {
				if (spaceCommonType == null) {
					return new ResponseEnvelope<BaseProduct>(false, "参数spaceCommonType不存在!", msgId);
				} else {
					spacValue = spaceCommonType;
				}
			} else {
				spacValue = sysList.get(0).getValue();
			}
			if (StringUtils.isBlank(groupCode)) {
				return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
			}
			if (StringUtils.isBlank(config)) {
				return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
			}
			if (StringUtils.isBlank(structureCode)) {
				return new ResponseEnvelope<BaseProduct>(false, "structureCode不能为空!", msgId);
			}
			if (StringUtils.isBlank(designTempletCode)) {
				return new ResponseEnvelope<BaseProduct>(false, "designTempletCode不能为空!", msgId);
			}
		} else {
			spacValue = spaceCommonType;
			if (StringUtils.isBlank(groupCode)) {
				return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
			}
			if (StringUtils.isBlank(config)) {
				return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
			}
			if (spaceCommonType == null) {
				return new ResponseEnvelope<BaseProduct>(false, "参数spaceCommonType不能为空!", msgId);
			}
		}

		if (Utils.hasEmptyOrSpecialCharacter(groupCode)) {
			return new ResponseEnvelope<>(false, "请检查组合编码中是否包含空格！", msgId);
		}
		//去除编码中的空格,换行符之类特殊字符
	  /*structureCode = Utils.replaceSpecialCharacter(groupCode);*/  //？？？不知道这个逻辑是不是对的
		groupCode = Utils.replaceSpecialCharacter(groupCode);

		if (isMainProductId == null || isMainProductId < 1) {
			return new ResponseEnvelope<BaseProduct>(false, "缺失参数isMainProductId!", msgId);
		}
		if (compositeTypeValue == null || compositeTypeValue == 0) {
			return new ResponseEnvelope<BaseProduct>(false, "请选择组合类型!", msgId);
		}
		if (coverPicFile == null || coverPicFile.getSize() == 0) {
			return new ResponseEnvelope<BaseProduct>(false, "缺失封面图文件coverPicFile!", msgId);
		}
		//组合类型是否正确
		SysDictionary groupProductType = new SysDictionary();
		groupProductType = sysDictionaryService.findOneByTypeAndValue(SysDictionaryConstant.SYS_DICTIONARY_GROUP_TYPE, compositeTypeValue);
		if (groupProductType == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数compositeTypeValue错误!", msgId);
		}
//        }
		if (designPlanId == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数designPlanId不能为空!", msgId);
		}

		DesignPlan designPlan = null;
		designPlan = designPlanService.get(designPlanId);
		if (designPlan == null) {
			throw new BaseProductException("找不到该设计方案!designPlanId :" + designPlanId);
		}
		Integer spaceCommonId = designPlan.getSpaceCommonId();
		if (spaceCommonId == null) {
			return new ResponseEnvelope<BaseProduct>(false, "该设计方案缺失关联空间！", msgId);
		}
		SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
		if (spaceCommon == null) {
			return new ResponseEnvelope<BaseProduct>(false, "该设计方案缺失关联空间！", msgId);
		}
		/**
		 *  参数校验完成 end
		 */

		JSONObject json = JSONObject.fromObject(config);
		JSONArray jsonArray;
		if (isBmGroup != 2) {
			jsonArray = JSONArray.fromObject(json.get("productList"));
		} else {
			//结构组合
			jsonArray = JSONArray.fromObject(json.get("ProductStructList"));
		}
		GroupProduct groupProduct = new GroupProduct();

		try {
			/**
			 * 填充组合相关字段
			 *
			 */
			//自动生成组合编码
			groupProduct.setGroupType(groupType);
			groupProduct.setCompositeType(compositeTypeValue);
			//组合名称
			groupProduct.setGroupName(groupCode);
			//自动生成组合编码
			String newCode = groupProductService.createNewGroupProductCode();
			if (newCode != null) {
				groupProduct.setGroupCode(newCode);
			}
			//房型类别
			groupProduct.setType(spacValue);
			// 保存组合产品时保存空间相关信息
			if (spaceCommon.getSpaceFunctionId() != null) {
				groupProduct.setSpaceFunctionValue(spaceCommon.getSpaceFunctionId());
			}

			groupProduct.setUserId(loginUser.getId());
		    /* 记录操作人信息*/
			sysSave(groupProduct, request);
			//保存配置文件
			Integer fileId = locationTxt(config, loginUser, groupProduct);
			//将文件ID保存至组合位置字段
			groupProduct.setLocation(fileId + "");
			//保存组合封面图
			Integer picId = uploadGroupProductCoverPicFile(coverPicFile, loginUser, groupProduct);
			groupProduct.setPicId(picId);

		  /* 保存不同类型组合产品及详细信息*/
			if (isBmGroup == 1) {
				/**保存白模组合产品**/
				groupProduct = saveIsbaimoGroupProduct(request, isMainProductId, jsonArray, groupProduct,designPlan);
			} else if (isBmGroup == 2) {
				/**保存结构组合**/
				//保存结构组合的逻辑   没有保存结构组合的入口了 --2018/11/07
				//sgroupProductService.addGroupStructure(designTempletCode,structureCode,jsonArray,msgId);
				groupProduct = saveStructureGroupProduct(request, structureCode, isMainProductId, jsonArray, groupProduct);
			} else {
				/**保存普通组合**/
				groupProduct = saveAnotherTypeGroupProduct(request, isMainProductId, jsonArray, groupProduct,loginUser);
			}

			// 如果是一键装修组合,标记该设计方案为一键装修方案
			if (groupType.intValue() == GroupProductTypeConstant.TYPE_INTELLIGENCE.intValue()) {
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setId(designPlan.getId());
				newDesignPlan.setPlanType(DesignPlanTypeConstant.TYPE_INTELLIGENCE);
				designPlanService.update(newDesignPlan);
				if(isBmGroup != 1 && isBmGroup !=2){
					//如果为一键装修的产品组合，则不在商家后台显示
					groupProduct.setIsDisplay(1);
				}
			}
			//给组合自动上架
			groupProduct.setState(GroupProductStateCode.HAS_BEEN_RELEASE);
			groupProductService.update(groupProduct);

			//回填文件信息
			ResFile resFile = new ResFile();
			resFile.setBusinessId(groupProduct.getId());
			resFile.setId(fileId);
			resFileService.update(resFile);
			//回填封面图信息
			ResPic pic = new ResPic();
			pic.setBusinessId(groupProduct.getId());
			pic.setId(picId);
			resPicService.update(pic);

		} catch (BaseProductException e) {
			throw new BaseProductException(e.getMessage());
		} catch (Exception e) {
			logger.error("出现异常",e);
			throw new RuntimeException("出现异常！");
		}
		return new ResponseEnvelope<BaseProduct>(true, msgId, true);

	}

	private GroupProduct saveAnotherTypeGroupProduct(HttpServletRequest request, Integer isMainProductId, JSONArray jsonArray, GroupProduct groupProduct,LoginUser loginUser) throws IOException, BaseProductException {

	    /* 保存组合产品*/
		int groupId = groupProductService.add(groupProduct);
		if( groupId < 1 ){
          throw new BaseProductException("保存组合数据失败!");
        }

        /*保存组合明细表信息*/
		BaseProduct baseProduct = null;
		GroupProductDetails gpd = null;
		String planGroupId = groupId+"_"+System.currentTimeMillis();
		//主产品
		BaseProduct mainProduct = null;
		for(int i=0;i<jsonArray.size();i++){
            // eg:{"productName":"kangl_bcca_0001","planProductId":8309929,"localPosX":0,"localPosY":-0.36601305,"localPosZ":-0.002499938,"localRotX":9.334668E-6,"localRotY":0,"localRotZ":0}
            JSONObject object = (JSONObject)jsonArray.get(i);
            baseProduct = new BaseProduct();
            gpd = new GroupProductDetails();
            gpd.setGroupId(groupId);
            gpd.setIsMain(0);

            String productCode = object.getString("productName");
            baseProduct.setProductCode(productCode);
            baseProduct.setIsDeleted(0);
            List<BaseProduct> baseProducts = this.getList(baseProduct);
            if( baseProducts!=null && baseProducts.size()>=1 ){
                baseProduct = baseProducts.get(0);
                if( baseProduct != null ){
                    gpd.setProductId(baseProduct.getId());
                }
            }else{
                throw new BaseProductException("保存组合明细数据失败!,产品没找到,code:" + productCode);
            }
            //设置主产品
			if(isMainProductId != null && isMainProductId > 0){
				if(baseProduct.getId().equals(isMainProductId)) {
					gpd.setIsMain(1);
					mainProduct = baseProduct;
				}
			} else{
			    /*如果没有设置主产品，则第一条数据默认为主产品*/
				if( i==0 ){
					gpd.setIsMain(1);
					mainProduct = baseProduct;
				}
			}

            // 设置产品视角和远景视角->start
            setCameraInfo(gpd, object, null);
            // 设置产品视角和远景视角->end

            // 组合明细中设置当前选择的多材质信息 ->start
            this.setGroupSplitTexturesChooseInfo(gpd, (Integer)object.get("planProductId"));
            // 组合明细中设置当前选择的多材质信息 ->end

            sysSave(gpd,request);
            int gpdId = groupProductDetailsService.add(gpd);
            if (gpdId < 1) {
                throw new BaseProductException("保存组合明细数据失败!");
            } else {
                //如果是一件装修组合,则把该组合更新该方案组合
                //20180103注释该逻辑，原因：1、做组合不能破坏原有方案产品里的组合，
                // 2、如果原有组合产品没有全部做到一键生成组合里来，替换组合再次进入导致空间错乱
//                        if(groupType.intValue() == GroupProductTypeConstant.TYPE_INTELLIGENCE.intValue()){
//                            Integer planProductId = (Integer)object.get("planProductId");
//                            DesignPlanProduct designPlanProduct = new DesignPlanProduct();
//                            designPlanProduct.setId(planProductId);
//                            designPlanProduct.setProductGroupId(groupId);   //组合id
//                            designPlanProduct.setPlanGroupId(planGroupId);  //时间搓
//                            designPlanProduct.setIsMainProduct(gpd.getIsMain());
//                            designPlanProductService.update(designPlanProduct);
//                        }
            }
        }
		/**
		 * 设置组合的品牌和企业(读取主产品的品牌和企业)
		 */
		if(mainProduct != null){
			Integer brandId = mainProduct.getBrandId();
			groupProduct.setBrandId(brandId);
			if(mainProduct.getCompanyId() != null && brandId >0){
				groupProduct.setCompanyId(mainProduct.getCompanyId());
			}else{
				if(brandId != null && brandId > 0 ){
					BaseBrand baseBrand = baseBrandService.get(brandId);
					if(baseBrand != null){
						groupProduct.setCompanyId(baseBrand.getCompanyId());
					}
				}
			}
			/**
			 * 如果是非内部用户，校验主产品的品牌是否属于用户所属企业的品牌
			 * 如果不是，则不能在商家后台显示
			 */
			if(!Objects.equals(loginUser.getUserType(),UserTypeConstant.USER_TYPE_INTERNAL)){
				if(loginUser.getBusinessAdministrationId() != null){
					Integer companyId = loginUser.getBusinessAdministrationId().intValue();
					//经销商则找厂商公司id
					if(Objects.equals(loginUser.getUserType(),UserTypeConstant.USER_TYPE_DEALERS)){
						BaseCompany company = baseCompanyService.get(companyId);
						if(company != null){
							companyId = company.getPid();
						}
					}
					if(!companyId.equals(mainProduct.getCompanyId())){
						groupProduct.setIsDisplay(1);
					}
				}
			}

		}
		return groupProduct;
	}

	private GroupProduct saveStructureGroupProduct( HttpServletRequest request, String structureCode, Integer isMainProductId,JSONArray jsonArray, GroupProduct groupProduct) throws IOException, BaseProductException {

		List<StructureProduct>structureList=null;
		StructureProduct structureProduct = new StructureProduct();
		structureProduct.setStructureCode(structureCode);
		structureProduct.setIsDeleted(0);
		structureList=structureProductService.getList(structureProduct);
		if(structureList==null||structureList.size()<=0){
			throw new BaseProductException("该结构编码："+structureCode+"不存在!");
        }
		StructureProduct structureProduct_=null;
		String groupFlag=null;
		structureProduct_=structureProductService.get(structureList.get(0).getId());
		if(structureProduct_!=null){
            groupFlag=structureProduct_.getGroupFlag();
            if(groupFlag!=null){
                groupProduct.setGroupFlag(groupFlag);
            }else{
				throw new BaseProductException("该结构编码："+structureCode+"不存在结构标记groupFlag!");
            }
        }
		//保存组合信息
		groupProduct.setStructureId(structureList.get(0).getId());
		/*groupProduct.setType(spacValue);*/
		int groupId = groupProductService.add(groupProduct);
		if( groupId < 1 ){
            throw new BaseProductException("保存组合数据失败!");
        }
		/*保存组合明细表信息*/
		BaseProduct baseProduct = null;

		// 获取modeCode:productCode的map(为了自动匹配cameraLook和cameraView属性)(huangsongbo)
		Map<String, StructureProductDetails> map = structureProductDetailsService.getStructureProductDetailsInfoMap(structureProduct_.getId());
		GroupProductDetails gpd = null;
		for(int i=0;i<jsonArray.size();i++){
            JSONObject object = (JSONObject)jsonArray.get(i);
            baseProduct = new BaseProduct();
            gpd = new GroupProductDetails();
            gpd.setGroupId(groupId);
            gpd.setIsMain(0);
            String productCode = object.getString("ProductCode");
            baseProduct.setProductCode(productCode);
            baseProduct.setIsDeleted(0);
            List<BaseProduct> baseProducts = this.getList(baseProduct);
            if( baseProducts!=null && baseProducts.size() >= 1 ){
                baseProduct = baseProducts.get(0);
                if( baseProduct != null ){
                    gpd.setProductId(baseProduct.getId());
                }
            }else{
				throw new BaseProductException("保存组合明细数据失败!,产品没找到,code:" + productCode);
            }
            if(isMainProductId != null && isMainProductId >0){
				if(baseProduct.getId().equals(isMainProductId)) {
					gpd.setIsMain(1);
				}
			}else{
            	/*如果没设置主产品则第一条数据默认为主产品*/
				if( i==0 ){
					gpd.setIsMain(1);
				}
			}

            String ModelCode = object.getString("ModelCode");
            if(ModelCode!=null&&!"".equals(ModelCode)){
                List<ResModel>modelList=null;
                ResModel resModel = new ResModel();
                resModel.setModelCode(ModelCode);
                resModel.setFileKey("product.baseProduct.u3dmodel.windowsPc");
                resModel.setIsDeleted(0);
                modelList=resModelService.getList(resModel);
                if(modelList!=null&&modelList.size()>0){
                    List<BaseProduct>baseProductlist=null;
                    BaseProduct baseProduct_ = new  BaseProduct();
                    baseProduct_.setIsDeleted(0);
                    baseProduct_.setWindowsU3dModelId(modelList.get(0).getId());
                    baseProductlist=this.getList(baseProduct_);
                    if(baseProductlist!=null&&baseProductlist.size()>0){
                        gpd.setChartletProductModelId(baseProductlist.get(0).getId());
                    }else{
						throw new BaseProductException("模型编码："+ModelCode+"  不存在，或者已删除!，保存组合明细数据失败!");
                    }
                }else{
					throw new BaseProductException("模型编码："+ModelCode+"  不存在，或者已删除!，保存组合明细数据失败!");
                }
            }
            // 设置产品视角和远景视角->start
            setCameraInfo(gpd, object, (map.containsKey(ModelCode))?map.get(ModelCode):null);
            // 设置产品视角和远景视角->end
            sysSave(gpd,request);
            int gpdId = groupProductDetailsService.add(gpd);
            if( gpdId < 1 ){
                throw new BaseProductException("保存组合明细数据失败!");
            }
        }
		return groupProduct;
	}

	private GroupProduct saveIsbaimoGroupProduct(HttpServletRequest request, Integer isMainProductId, JSONArray jsonArray, GroupProduct groupProduct,DesignPlan designPlan) throws IOException, BaseProductException {

		groupProduct.setDesignTempletId(designPlan.getDesignTemplateId());
		groupProduct.setStart(0);
		groupProduct.setSorting(0);
        /*保存组合产品*/
		int groupId = groupProductService.add(groupProduct);
		if( groupId < 1 ){
            throw new BaseProductException("保存组合数据失败!");
        }
		/*保存组合明细表信息*/
		BaseProduct baseProduct = null;
		GroupProductDetails gpd = null;
		for(int i=0;i<jsonArray.size();i++){
            JSONObject object = (JSONObject)jsonArray.get(i);
            baseProduct = new BaseProduct();
            gpd = new GroupProductDetails();
            gpd.setGroupId(groupId);
            gpd.setIsMain(0);

            Integer planProductId = (Integer)object.get("planProductId");
            DesignPlanProduct dpp = new DesignPlanProduct();
            if( planProductId != null ){
                dpp = designPlanProductService.get(planProductId);
            }else{
                throw new BaseProductException("位置信息中planProductId属性为空!");
            }
            if( dpp != null ){
                gpd.setAtt1(dpp==null?"":dpp.getPlanProductId()+"");
            }else{
                throw new BaseProductException("找不到该设计方案产品ID:"+planProductId+"!");
            }
            if(dpp.getProductId() == null ) {
                throw new BaseProductException("该设计方案产品关联缺失产品Id!planProductId:"+planProductId+"!");
            }
            if(isMainProductId != null && isMainProductId > 0){
				if(dpp.getProductId().equals(isMainProductId)) {
					//指定主产品
					gpd.setIsMain(1);
				}
			}else{
            	 /*如果没有指定主产品,则第一条数据默认为主产品*/
				if( i==0 ){
					gpd.setIsMain(1);
				}
			}

            String productCode = object.getString("productName");
            baseProduct.setProductCode(productCode);
            baseProduct.setIsDeleted(0);
            gpd.setPosIndexPath(dpp.getPosIndexPath());
            List<BaseProduct> baseProducts = this.getList(baseProduct);
            if( baseProducts!=null && baseProducts.size() >= 1 ){
                baseProduct = baseProducts.get(0);
                if( baseProduct != null ){
                    gpd.setProductId(baseProduct.getId());
                }
            }else{
                throw new BaseProductException("保存组合明细数据失败!,产品没找到,code:" + productCode);
            }
            // 设置产品视角和远景视角->start
            setCameraInfo(gpd, object, null);
            // 设置产品视角和远景视角->end
            sysSave(gpd,request);
            int gpdId = groupProductDetailsService.add(gpd);
            if( gpdId < 1 ){
              throw new BaseProductException("保存组合明细数据失败!");
            }
        }
		return groupProduct;
	}


	/**
     * 将String 类型 location转为txt文本
     * 传进来一个String  返回一个文本id
     * @return
     * @throws IOException 
     */
    @Override
    public Integer locationTxt(String config,LoginUser loginUser,GroupProduct groupProduct) throws IOException{
        
        
        if(config!=null&&!"".equals(config)){
            JSONObject json=JSONObject.fromObject(config);
            Object boxSizeX=json.get("boxSizeX");
            Object boxSizeY=json.get("boxSizeY");
            Object boxSizeZ=json.get("boxSizeZ");
            if(boxSizeX!=null&&!"".equals(boxSizeX)){
                groupProduct.setGroupWidth(boxSizeX.toString());
            }
            if(boxSizeY!=null&&!"".equals(boxSizeY)){
                groupProduct.setGroupLength(boxSizeY.toString());
            }
            if(boxSizeZ!=null&&!"".equals(boxSizeZ)){
                groupProduct.setGroupHigh(boxSizeZ.toString());
            }
        }
        
        /*生成的 txt*/
        ResFile resFile= new ResFile();
        String filePath=null;
        String fileName=null;
        Integer fileId=null;
        String url=null;
        String serverType=null;
        String filePathUrl=null;
        //filePath=app.getString("product.groupProduct.file.location.upload.path");
        filePath = Utils.getPropertyName("config/res", "product.groupProduct.file.location.upload.path", "/AA/c_basedesign/[yyyy]/[MM]/[dd]/[HH]/product/groupProduct/file/location/");
        filePath = Utils.replaceDate(filePath);
        url=Tools.getRootPath(filePath, "");
        serverType = app.getString("app.system.format");
        if(filePath==null||"".equals(filePath)){
            throw new RuntimeException("not found: product.groupProduct.file.location.upload.path");
        }
        if(url==null||"".equals(url)){
            throw new RuntimeException("not found: app.upload.root");
        }
        if(serverType==null||"".equals(serverType)){
            throw new RuntimeException("not found: app.system.format");
        }
        filePathUrl=url+filePath;
        if("windows".equals(serverType)){
            filePathUrl=filePathUrl.replace("/", "\\");
        }
        File file = new File(filePathUrl);
        if (!file.exists()) {
            file.mkdirs();
        }
        File txtFile = File.createTempFile("groupLocation_", ".txt", file);
        PrintStream ps = new PrintStream(new FileOutputStream(txtFile));
        
        /*将位置保存至 txt*/
        ps.println(config);
        ps.close();
        fileName=txtFile.getName();
        
        /*讲文件信息保存至数据库*/
        resFile.setFileCode(System.currentTimeMillis()+"_"+Utils.generateRandomDigitString(6));
        resFile.setFileName(fileName.substring(0,fileName .lastIndexOf(".")));
        resFile.setFileOriginalName(fileName.substring(0,fileName .lastIndexOf(".")));
        resFile.setFileType("无");
        resFile.setFileSize(txtFile.length()+"");
        resFile.setFilePath(filePath+fileName);
        resFile.setFileKey("product.groupProduct.file.location");
        resFile.setIsDeleted(0);
        
        resFile.setCreator(StringUtils.isEmpty(loginUser.getName())?"":loginUser.getLoginName());
        resFile.setModifier(StringUtils.isEmpty(loginUser.getName())?"":loginUser.getLoginName());
        resFile.setGmtCreate(new Date());
        resFile.setGmtModified(new Date());
        resFile.setFileSuffix(".txt");
        
        fileId = resFileService.add(resFile);
        if(fileId==null||fileId<=0){
            throw new RuntimeException("error:  1727 line , resFileService.add(resFile)");
        }

        return fileId;
    }

	@Override
	public List<CeilingCrossDataResult> getCeilingCrossDataByStyleId(List<String> styles) {
		return baseProductMapper.selectCeilingCrossData(styles);
	}

	/**
     * 上传组合的封面图
     * @param file
     * @param loginUser
     * @param product
     * @return
     * @throws BaseProductException 
     */
    public Integer uploadGroupProductCoverPicFile(MultipartFile file,LoginUser loginUser,GroupProduct product) throws BaseProductException {
      
		ResPic pic = new ResPic();
		Integer id =null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FileUploadUtils.UPLOADPATHTKEY, ResProperties.PRODUCT_GROUPPRODUCT_PIC);
		map.put(FileUploadUtils.FILE, file);
		// 获取文件大小
		Long fileSize = file.getSize();
		String originalFilename = file.getOriginalFilename();
		// 获取物理文件名称
		String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
		String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'),originalFilename.length()).toLowerCase();
		if(!".jpg".equals(suffix) && !".png".equals(suffix) && ".jpeg".equals(suffix)) {
			throw new BaseProductException("请上传.jpg/.png/.jpeg 后缀的图片!");
		}
		if (fileSize > 15*1024*1024) {
			throw new BaseProductException("仅支持上传图片大小15M!");
		}

		try {
			boolean flag = FileUploadUtils.saveFile(map);
			if(flag) {

				pic.setPicCode(product.getGroupCode());
				pic.setPicName(filename);
				pic.setPicType("--");
				pic.setSysCode(product.getGroupCode());
				pic.setFileKey((String)map.get(FileModel.FILE_KEY));
				pic.setPicPath((String)map.get("dbFilePath"));
				pic.setPicFileName((String)map.get("FileOriginalName"));
				pic.setPicSize(Integer.parseInt((String)map.get(FileModel.FILE_SIZE)));
				pic.setPicWeight((String)map.get(FileModel.PIC_WEIGHT));
				pic.setPicFormat((String)map.get(FileModel.FORMAT));
				pic.setPicHigh((String)map.get(FileModel.PIC_HEIGHT));
				pic.setPicSuffix((String)map.get(FileModel.FILE_SUFFIX));
				pic.setPicFormat((String)map.get(FileModel.FORMAT));
				pic.setIsDeleted(0);
				pic.setCreator(loginUser.getLoginName());
				pic.setGmtCreate(new Date());
				pic.setModifier(loginUser.getLoginName());
				pic.setGmtModified(new Date());
				id = resPicService.add(pic);

				boolean bool = groupProductThumbnailCoverPic(id,pic.getPicPath());
				if(!bool)
					throw new BaseProductException("图片压缩错误!");
			}
		} catch (Exception e) {
			logger.error("上传图片出错",e);
			throw new RuntimeException("上传图片错误");
		}

		return id;
    }


	public boolean groupProductThumbnailCoverPic(Integer resId,String picPath) throws IOException {
    	if(null == resId || StringUtils.isBlank(picPath))
    		return false;

		//原图绝对路径
		String picFilePath = Utils.getAbsolutePath(picPath, null);
		picFilePath = Utils.dealWithPath(picFilePath, Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
		logger.error("原产品组合封面图路径picFilePath="+picFilePath);


		//构建新上传路径
		String filePathPrefix = "";		//路径前缀
		String filePath = "";			//完整上传路径
		String fileKey = "product.groupProduct.pic.upload.path";
		String defaultPath = "/AA/c_basedesign/[yyyy]/[MM]/[dd]/[HH]/product/groupProduct/pic/";
		filePathPrefix = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
		filePathPrefix = Utils.replaceDate(filePathPrefix);
		filePathPrefix = Utils.getAbsolutePath(filePathPrefix, null);//这句话获取全部资源路径：在内部获取存储前缀（filePathPrefix）
		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) )
			filePathPrefix = filePathPrefix.replace("\\", "/");
		File fileCheck = new File(filePathPrefix);//构建文件流文件夹
		if(!fileCheck.exists())
			fileCheck.mkdirs();
		//生成新的文件名称
		String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6) + ".jpg";
		//完整路径
		filePath = filePathPrefix + fileName;


		boolean flag = ThumbnailUtil.createThumbnail(picFilePath, filePath, 512, 512);
		if (!flag) {
			logger.error("资源resId：{}压缩失败！", resId);
			return false;
		}
		File targetFile = new File(filePath);
		Map map = FileUploadUtils.getMap(targetFile,"product.groupProduct.pic.upload.path", false);


		ResPic pic = new ResPic();
		pic.setId(resId);
		pic.setPicName(fileName);
		pic.setPicType("产品组合封面图");
		pic.setFileKey((String)map.get(FileModel.FILE_KEY));
		pic.setPicPath((String)map.get("dbFilePath"));
		pic.setPicFileName((String)map.get("FileOriginalName"));
		pic.setPicSize(Integer.parseInt((String)map.get(FileModel.FILE_SIZE)));
		pic.setPicWeight((String)map.get(FileModel.PIC_WEIGHT));
		pic.setPicFormat((String)map.get(FileModel.FORMAT));
		pic.setPicHigh((String)map.get(FileModel.PIC_HEIGHT));
		pic.setPicSuffix((String)map.get(FileModel.FILE_SUFFIX));
		pic.setPicFormat((String)map.get(FileModel.FORMAT));
		pic.setNewOrginalPicPath(picPath);
		int count = resPicService.update(pic);

		if (count > 0) {

//			File delFile = new File(picFilePath);
//			if (delFile.exists()) {
//				delFile.delete();
//			} else {
//				logger.error("文件不存在picFilePath：{}" + picFilePath);
//			}

			return true;
		} else {
			return  false;
		}
	}



	/**
     * 自动存储系统字段
     */
    private void sysSave(GroupProduct model,HttpServletRequest request){
        if(model != null){
                 LoginUser loginUser = new LoginUser();
                 loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
                 
                 if(loginUser==null || loginUser.getId() == null){
                   //使用另一种读取用户的方式，因为PC端有些接口token信息作为参数传递
                   loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
                   if(loginUser == null || loginUser.getId() == null) {
                     loginUser = new LoginUser();
                     loginUser.setLoginName("nologin");
                   }
                 }
                 
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if(model.getSysCode()==null || "".equals(model.getSysCode())){
                       model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                   }
                }
                
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());
        }
    }
    
    /**
     * 自动存储系统字段
     */
    private void sysSave(GroupProductDetails model,HttpServletRequest request){
                if(model != null){
                LoginUser loginUser = new LoginUser();
                loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
                
                if(loginUser==null || loginUser.getId() == null){
                  //使用另一种读取用户的方式，因为PC端有些接口token信息作为参数传递
                  loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
                  if(loginUser == null || loginUser.getId() == null) {
                    loginUser = new LoginUser();
                    loginUser.setLoginName("nologin");
                  }
                }
                 
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if(model.getSysCode()==null || "".equals(model.getSysCode())){
                       model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                   }
                }
                
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());
        }
    }
    
    /**
     * 设置相机属性
     * @author huangsongbo
     * @param groupProductDetails
     * @param jsonObject
     * @param structureProductDetails 
     */
    private void setCameraInfo(GroupProductDetails groupProductDetails, JSONObject jsonObject, StructureProductDetails structureProductDetails) {
        String cameraLook = null;
        String cameraView = null;
        if(jsonObject.containsKey("CameraLook"))
            cameraLook = jsonObject.getString("CameraLook");
        if(jsonObject.containsKey("CameraView"))
            cameraView = jsonObject.getString("CameraView");
        // 更新cameraLook->start
        if(StringUtils.isNotBlank(cameraLook)){
            groupProductDetails.setCameraLook((cameraLook==null||"null".equals(cameraLook))?"":cameraLook);
        }else{
            // 根据对应结构对应的产品更新相机属性
            if(structureProductDetails !=null){
                groupProductDetails.setCameraLook(structureProductDetails.getCameraLook());
            }
        }
        // 更新cameraLook->end
        // 更新cameraView->start
        if(StringUtils.isNotBlank(cameraView)){
            groupProductDetails.setCameraView((cameraView==null||"null".equals(cameraView))?"":cameraView);
        }else{
            // 根据对应结构对应的产品更新相机属性
            if(structureProductDetails !=null){
                groupProductDetails.setCameraView(structureProductDetails.getCameraView());
            }
        }
        // 更新cameraView->end
    }
    
    /**
     * 设置组合明细多材质信息(保存新建组合的时候选择的多材质信息)
     * 
     * @author huangsongbo
     * @param gpd
     * @param designPlanProductId
     */
    private void setGroupSplitTexturesChooseInfo(GroupProductDetails gpd, Integer designPlanProductId) {
        // 参数验证 ->start
        if(gpd == null) {
            return;
        }
        if(designPlanProductId == null) {
            return;
        }
        DesignPlanProduct designPlanProduct = designPlanProductService.get(designPlanProductId);
        if(designPlanProduct == null) {
            logger.error("designPlanProduct not found->designPlanProductId:" + designPlanProductId);
            return;
        }
        // 参数验证 ->end
        
        gpd.setSplitTexturesChooseInfo(designPlanProduct.getSplitTexturesChooseInfo());
    }
}