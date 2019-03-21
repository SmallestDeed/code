package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nork.product.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.product.service.UserProductCollectService;
import com.nork.product.service2.BaseProductServiceV2;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: BaseProductServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
@Service("baseProductService3")
@Transactional
public class BaseProductServiceImplV2 implements BaseProductServiceV2 {
	
	private static Logger logger = Logger.getLogger(BaseProductServiceImplV2.class);
	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	@Autowired
	private BaseProductMapper baseProductMapper;
	@Resource
	private ResPicService resPicService;
	@Resource
	private ResModelService resModelService;
	@Resource
	private ProductAttributeService productAttributeService;
	@Autowired
	private SysDictionaryService sysDictionaryService; 
	@Autowired
	private ResTextureService resTextureService; 
	@Autowired
	private DesignRulesService designRulesService; 
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignTempletService designTempletService;
	
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private UserProductCollectService userProductCollectService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private BaseProductStyleService baseProductStyleService;
	@Autowired
	private ProductCategoryRelService productCategoryRelService;
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
	 * 获取该用户绑定的序列号品牌
	 * 
	 * @param  baseProduct,loginUser,cacheEnable
	 */
	public void getAuthorized(BaseProduct baseProduct,LoginUser loginUser){
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(SystemCommonConstant.STATE);
		List<AuthorizedConfig> authorizedList = new ArrayList<>();
		//判断是否从缓存中取值
		authorizedList = (Utils.enableRedisCache() ? 		
				AuthorizedConfigCacher.getList(authorizedConfig) : authorizedConfigService.getList(authorizedConfig));
		String brandIds = "";
		for(AuthorizedConfig ac : authorizedList){
			if( StringUtils.isNotBlank(ac.getBrandIds()) ){
				brandIds += ac.getBrandIds()+",";
			}
		}
		//品牌是否被绑定状态
		String productBrandId = ","+baseProduct.getBrandId()+",";
		if( StringUtils.isEmpty(brandIds) ){
			baseProduct.setSalePrice(-1.0);
		}
		if( (","+brandIds).indexOf(productBrandId)==-1 ){
			baseProduct.setSalePrice(-1.0);
		}
	}
	
	
	public void productMaterial(BaseProduct baseProduct,Integer id,String planProductId,Integer designPlanId,Integer spaceCommonId,
			LoginUser loginUser,HttpServletRequest request) throws Exception{		
		DesignTemplet designTemplet = new DesignTemplet();
		DesignPlan designPlan=null;
		if (designPlanId != null) {
			designPlan = (Utils.enableRedisCache()) ? DesignCacher.get(designPlanId):designPlanService.get(designPlanId);
			designTemplet = (Utils.enableRedisCache()) ? DesignCacher.getTemplet(designPlan.getDesignTemplateId()):designTempletService.get(designPlan.getDesignTemplateId());
		}
		// 详情逻辑
		productDetailContent(baseProduct,planProductId,designPlan, loginUser,request);

		if (StringUtils.isBlank(baseProduct.getProductTypeMark())) {
			SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType",Utils.getIntValue(baseProduct.getProductTypeValue()));
			if (bigSd != null) {
				if (StringUtils.isNotEmpty(bigSd.getValuekey()) && baseProduct.getProductSmallTypeValue() != null) {
					SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),baseProduct.getProductSmallTypeValue());
					if (smallSd != null) {
						baseProduct.setProductSmallTypeMark(smallSd.getValuekey());
					}
				}
				baseProduct.setProductTypeMark(bigSd.getValuekey());
			}
		}
		//#################产品带上价格单位  satart
		//产品价格单位名称
		Integer spvValue = baseProduct.getSalePriceValue();
		if(spvValue != null){
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice", new Integer(spvValue));
			baseProduct.setSalePriceValueName(dictionary==null?"":dictionary.getName());
		}
		//#################产品带上价格单位  end
		/***在组合产品查询列表 中 增加产品属性****/
		Map<String,String> map=new HashMap<String,String>();
		map=productAttributeService.getPropertyMap(baseProduct.getId());
		baseProduct.setPropertyMap(map);
		
		// 组装产品的规则
		Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
				baseProduct.getProductTypeMark(), baseProduct.getProductSmallTypeMark(), spaceCommonId,
				designTemplet == null ? null : designTemplet.getId(), new DesignRules(),map);
		baseProduct.setRulesMap(rulesMap);
		/*关联查询材质的两个属性(textureAttrValue,laymodes)*/
		if (baseProduct != null && StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
			String materialIds=baseProduct.getMaterialPicIds();
			List<String> idsInfo=Utils.getListFromStr(materialIds);
			if(idsInfo!=null&&idsInfo.size()>0){
				ResTexture resTexture=resTextureService.get(Integer.valueOf(idsInfo.get(0)));
				if(resTexture!=null){
					baseProduct.setTextureAttrValue(resTexture.getTextureAttrValue());
					baseProduct.setLaymodes(resTexture.getLaymodes());
				}
			}
		}
		/*关联查询材质的两个属性(textureAttrValue,laymodes)->end*/
		/*处理拆分材质产品返回默认材质*/
		String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
		Integer isSplit=0;
		List<SplitTextureDTO> splitTextureDTOList=null;
		if(StringUtils.isNotBlank(splitTexturesInfo)){
			Map<String,Object> map1=dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
			isSplit=(Integer) map1.get("isSplit");
			splitTextureDTOList=(List<SplitTextureDTO>) map1.get("splitTexturesChoose");
		}else{
			/*普通产品*/
			String materialIds = baseProduct.getMaterialPicIds();
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
					baseProduct.setSplitTexturesChoose(splitTextureDTOList);
				}
			}
		}
		/*处理拆分材质产品返回默认材质->end*/
		baseProduct.setProductId(id);
		Long endTime=System.currentTimeMillis();
		Map<String,CategoryProductResult> baimoRule = setbaimoRuleMap(spaceCommonId, request, loginUser.getId(), baseProduct.getProductSmallTypeCode(), designPlan, map);
		baseProduct.setBasicModelMap(baimoRule);
	}
	
	
	// 详情逻辑
	public void productDetailContent(BaseProduct baseProduct,String planProductId,DesignPlan designPlan,LoginUser loginUser,HttpServletRequest request) throws Exception {
		//媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setUserId(loginUser.getId());
		userProductCollectSearch.setProductId(baseProduct.getId());
		Integer count = userProductCollectService.getCount(userProductCollectSearch);
		if (count > 0) {
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
		baseProduct.setSplitTexturesInfo(null);
		//获取不同媒介u3d模型
		
		String modelId = getU3dModelId(mediaType,baseProduct);
		
		if (StringUtils.isNotBlank(modelId)) {
			ResModel resModel = resModelService.get(Utils.getIntValue(modelId));
			if( resModel != null ){
				baseProduct.setU3dModelPath(resModel.getModelPath());
				baseProduct.setModelLength(resModel.getLength());
				baseProduct.setModelWidth(resModel.getWidth());
				baseProduct.setModelHeight(resModel.getHeight());
				baseProduct.setModelMinHeight(resModel.getMinHeight());
			}
		}

		Integer brandId = baseProduct.getBrandId();
		if (brandId != null) {
			BaseBrand baseBrand = baseBrandService.get(brandId);
			if (baseBrand != null) {
				baseProduct.setBrandName(baseBrand.getBrandName());
			}
		}

		
		// 风格信息 ->start
		if(StringUtils.isNotBlank(baseProduct.getProductStyleIdInfo())){
			JSONObject styleJson = JSONObject.fromObject(baseProduct.getProductStyleIdInfo());
			List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
			StringBuffer stringBuffer = new StringBuffer("");
			for(String str : styleInfoList){
				stringBuffer.append(str + "  ");
			}
			baseProduct.setProductStyle(stringBuffer.toString());
			baseProduct.setProductStyleInfoList(styleInfoList);
		}
		// 风格信息 ->end
		
		String productType = baseProduct.getProductTypeValue();
		if (!StringUtils.isEmpty(productType)) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType",
					new Integer(productType));
			baseProduct.setProductType(dictionary.getName());
			baseProduct.setProductTypeKey(dictionary.getValuekey());
			baseProduct.setProductTypeCode(dictionary.getValuekey());
			baseProduct.setProductTypeName(dictionary.getName());
		}

		if (baseProduct.getProductSmallTypeValue() != null
				&& StringUtils.isNotBlank(baseProduct.getProductTypeValue())) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue(baseProduct.getProductTypeKey(),
					baseProduct.getProductSmallTypeValue());
			baseProduct.setProductSmallTypeCode(dictionary.getValuekey());
			baseProduct.setProductSmallTypeName(dictionary.getName());
			String rootType = StringUtils.isEmpty(dictionary.getAtt1()) ? "2" : dictionary.getAtt1().trim();
			baseProduct.setRootType(rootType);
			//获取是否是背景墙
			if(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(baseProduct.getProductTypeKey())){
				baseProduct.setBgWall(Utils.getIsBgWall(dictionary==null?"":dictionary.getValuekey()));
			}else{
				baseProduct.setBgWall(0);
			}
		}

		// 产品图片列表
		String smallPicIds = baseProduct.getPicIds();
		List<ResPic> list2=new ArrayList<ResPic>();
		if (StringUtils.isNoneBlank(smallPicIds)) {
			String[] strs = smallPicIds.split(",");
			int j=strs.length;
 			for(int i=(strs.length-1);i>-1;i--){
	 			ResPic resPic = resPicService.get(Utils.getIntValue(strs[i]));
	 			if(resPic!=null)
	 				list2.add(resPic);
			}
		}
		Collections.sort(list2, new Comparator<ResPic>() {
			@Override
			public int compare(ResPic o1, ResPic o2) {
				return (int) (o1.getGmtCreate().getTime()-o2.getGmtCreate().getTime());
			}
		});
		for(ResPic resPic:list2){
			baseProduct.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
		}
		// 产品图片列表
		for (ResPic resPic : list2) {
			if (StringUtils.isNotBlank(resPic.getSmallPicInfo())) {
				Map<String, String> map = Utils.getMapFromStr(resPic.getSmallPicInfo());
				if (StringUtils.isNotBlank(map.get("ipad"))) {
					ResPic ipadPic = resPicService.get(Utils.getIntValue(map.get("ipad")));
					resPic.setIpadThumbnailPath(ipadPic == null ? "" : ipadPic.getPicPath());
				}
				if (StringUtils.isNotBlank(map.get("web"))) {
					ResPic webPic = resPicService.get(Utils.getIntValue(map.get("web")));
					resPic.setWebThumbnailPath(webPic == null ? "" : webPic.getPicPath());
				}
			} else {
				// 如果没有缩略图就显示原图
				resPic.setIpadThumbnailPath(resPic == null ? "" : resPic.getPicPath());
				resPic.setWebThumbnailPath(resPic == null ? "" : resPic.getPicPath());
			}
			baseProduct.getThumbnailList().add(resPic);
		}
		
		Integer colorId = baseProduct.getColorId();
		if (colorId != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId);
			baseProduct.setProductColorKey(dictionary.getAtt1());
		}
		// 材质
		String materialIds = baseProduct.getMaterialPicIds();
		ResTexture resTexture1 = null ;
		if (StringUtils.isNotBlank(materialIds)) {
			resTexture1 = new ResTexture();
			String[] strs = materialIds.split(",");
			baseProduct.setMaterialId(strs[0]);
			resTexture1 = resTextureService.get(Integer.valueOf(strs[0]));
		}
		/*处理材质返回格式(huangsongbo 2017.1.4)*/
		String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
		Integer isSplit=0;
		List<SplitTextureDTO> splitTextureDTOList=null;
		if(StringUtils.isNotBlank(splitTexturesInfo)){
			Map<String,Object> map=dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
			isSplit=(Integer) map.get("isSplit");
			splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
		}else{
			if(resTexture1!=null){
				splitTextureDTOList=new ArrayList<SplitTextureDTO>();
				List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
				SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
				ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture1);
				resTextureDTO.setKey(splitTextureDTO.getKey());
				resTextureDTO.setProductId(baseProduct.getId());
				resTextureDTOList.add(resTextureDTO);
				splitTextureDTO.setList(resTextureDTOList);
				splitTextureDTOList.add(splitTextureDTO);
			}
		}
		baseProduct.setIsSplit(isSplit);
		baseProduct.setSplitTexturesChoose(splitTextureDTOList);
		String mergeIds = getParentIds(baseProduct);
		if (StringUtils.isBlank(mergeIds)) {
			
			// 材质 
			if (StringUtils.isNotBlank(materialIds)) {
				MergeProductResult mergeProductResult = new MergeProductResult();
				mergeProductResult.setState(1);
				String[] strs = materialIds.split(",");
//				ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
				ResTexture resTexture2 = resTextureService.get(Integer.valueOf(strs[0]));
				if (resTexture2 != null) {
					mergeProductResult.setState(2);
					mergeProductResult.setProductId(baseProduct.getId());
					mergeProductResult.setMaterialName(resTexture2.getFileName());
					mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
					mergeProductResult.setMaterialPicPath(resTexture2.getFilePath());
					baseProduct.getMateriallist().add(mergeProductResult);
				}

				// 材质配置文件路径
				Integer materialConfigId = baseProduct.getMaterialFileId();
				if (materialConfigId != null) {
					ResFile resFile = resFileService.get(materialConfigId);
					if (resFile != null) {
						baseProduct.setMaterialConfigPath(resFile.getFilePath());
					}
				}
			}
		}
		List<Integer> proIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(mergeIds)) {
			String arr[] = mergeIds.split(",");
			String colorValue = ".";
			String materialName = ".";
			for (String mergeId : arr) {
				proIdList.add(Integer.parseInt(mergeId));
				BaseProduct bp = get(Utils.getIntValue(mergeId));
				if( bp != null ){
					// 材质
					String materialPicIds = bp.getMaterialPicIds();
					if (StringUtils.isNotBlank(materialPicIds)) {
						MergeProductResult mergeProductResult = new MergeProductResult();
						mergeProductResult.setState(1);
						String[] strs = materialPicIds.split(",");
						ResTexture resTexture = resTextureService.get(Integer.valueOf(strs[0]));
//						ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
						if (resTexture != null) {
							if (materialName.indexOf(("." + resTexture.getFileName() + ".")) == -1) {
								// 页面默认选中材质（状态2）
								if (resTexture1 != null) {
									if (resTexture1.getFileName().equals(resTexture.getFileName())) {
										mergeProductResult.setState(2);
									}
								}
								if (mergeId.equals(baseProduct.getId() + "")) {
									mergeProductResult.setState(2);
								}
								mergeProductResult.setProductId(Utils.getIntValue(mergeId));
								mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
								mergeProductResult.setMaterialName(resTexture.getFileName());
								mergeProductResult.setMaterialPicPath(resTexture.getFilePath());
								baseProduct.getMateriallist().add(mergeProductResult);
							}
							materialName = materialName + resTexture.getFileName() + ".";
						}
					}

					// 材质配置文件路径
					Integer materialConfigId = baseProduct.getMaterialFileId();
					if (materialConfigId != null) {
						ResFile resFile = resFileService.get(materialConfigId);
						if (resFile != null) {
							baseProduct.setMaterialConfigPath(resFile.getFilePath());
						}
					}
				}
			}
		}

		// 产品属性展示
		if (CustomerListUtils.isEmpty(proIdList)) {
			proIdList.add(baseProduct.getId());
		}
		if (CustomerListUtils.isNotEmpty(proIdList)) {
			Map<String, List<ProductAttribute>> map = new HashMap<String, List<ProductAttribute>>();
			List<ProductAttribute> list = new ArrayList<ProductAttribute>();
			ProductAttribute attribute = new ProductAttribute();
			attribute.setProductIdList(proIdList);
			// 查询合并的产品所有属性
			list = productAttributeService.getMergeAttribute(attribute);
			// 过滤同attributeKey的属性
			Set<String> set = new HashSet<String>();
			if (CustomerListUtils.isNotEmpty(list)) {
				for (ProductAttribute pa : list) {
					set.add(pa.getAttributeKey());
				}
			}
			// 匹配产品属性
			if (CustomerListUtils.isNotEmpty(set)) {
				for (String str : set) {
					ProductAttribute attributeKey = new ProductAttribute();
					attributeKey.setProductIdList(proIdList);
					attributeKey.setAttributeKey(str);
					List<ProductAttribute> keyList = productAttributeService.getMergeAttribute(attributeKey);
					List<ProductAttribute> valueKeyList = new ArrayList<ProductAttribute>();
					if (CustomerListUtils.isNotEmpty(keyList)) {
						for (int i = 0; i < keyList.size(); i++) {
							boolean flag = true;
							ProductAttribute pai = keyList.get(i);
							for (int j = 0; j < i; j++) {
								ProductAttribute paj = keyList.get(j);
								if (paj.getAttributeValueKey().equals(pai.getAttributeValueKey())) {
									if (pai.getProductId().intValue() == baseProduct.getId().intValue()) {
										ProductAttribute pak = valueKeyList.get(j);
										pak.setState(2);
									}
									flag = false;
									break;
								}
							}
							if (flag) {
								if (pai.getProductId().intValue() == baseProduct.getId().intValue()) {
									pai.setState(2);
								}
								valueKeyList.add(pai);
							}
						}
						map.put(valueKeyList.get(0).getAttributeName(), valueKeyList);
					}
				}
			}
			baseProduct.setMap(map);
		}
		baseProduct.setParentProductId(baseProduct.getParentId());
		
		//贴图产品获取白模模型
		if(StringUtils.isNotBlank(materialIds)){
			List<String> idsInfo = Utils.getListFromStr(materialIds);
			List<String> materialPathList = new ArrayList<String>();
			for(String idStr:idsInfo){
				ResTexture resTexture=resTextureService.get(Integer.valueOf(idStr));
				if(resTexture!=null && resTexture.getId()!=null){
					materialPathList.add(resTexture.getFilePath());
				}
			}
			if(Lists.isNotEmpty(materialPathList)){
				baseProduct.setMaterialPicPaths((String[])materialPathList.toArray(new String[materialPathList.size()]));
				if(StringUtils.isNotBlank(planProductId)){
					DesignPlanProduct dpp = designPlanProductService.get(Utils.getIntValue(planProductId));
					if( dpp != null ){
						BaseProduct product = get(dpp.getProductId());
						boolean isHard = false;
						if( product != null ){
							isHard = isHard(product);
						}
						if( isHard ){
							BaseProduct baimoProduct = get(dpp.getInitProductId());
							//获取不同媒介u3d模型
							String u3dModelId = getU3dModelId(mediaType,baimoProduct);
							if( StringUtils.isNotBlank(u3dModelId) ){
								ResModel resModel = resModelService.get(Integer.valueOf(u3dModelId));
								if( resModel != null ){
									baseProduct.setU3dModelPath(resModel.getModelPath());
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer isSplit = new Integer(0);
		List<SplitTextureDTO> splitTexturesChooseList = new ArrayList<SplitTextureDTO>();
		List<SplitTextureDTO> splitTexturesInfoList = new ArrayList<SplitTextureDTO>();
		JSONArray jsonArray = JSONArray.fromObject(splitTexturesInfo);
		try {
			@SuppressWarnings({"unchecked", "deprecation"})
			List<SplitTextureInfoDTO> SplitTextureList = JSONArray.toList(jsonArray, SplitTextureInfoDTO.class);
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
						splitTexturesChooseList.add(new SplitTextureDTO(splitTextureInfoDTO.getKey(), splitTextureInfoDTO.getName(), resTextureDTOList));
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
							listNew.remove(null);
							textureList = listNew;
							// 按照textureIdStrList排序 ->end
							for (ResTexture resTexture : textureList) {
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
							
							 /*for(String textureIdStr:textureIdStrList){
								ResTexture resTexture=resTextureService.get(Integer.valueOf(textureIdStr));
								if(resTexture!=null){
									SplitTextureDTO.ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
									resTextureDTO.setKey(splitTextureInfoDTO.getKey());
									resTextureDTO.setProductId(baseProductId);
									resTextureDTOList.add(resTextureDTO);
								}
							}*/
							splitTexturesInfoList.add(new SplitTextureDTO(splitTextureInfoDTO.getKey(), splitTextureInfoDTO.getName(), resTextureDTOList));
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
					logger.warn("------baseProduct的SplitTextureInfo信息格式错误,SplitTextureInfo:" + splitTexturesInfo);
				}
				return map;
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
			logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
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
						StringBuffer conditionStr = new StringBuffer();
						for( Object object : productPropertyArray ){
							productPropertyStr = object.toString();
							baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
							if( StringUtils.isNotBlank(baimoProductPropertyStr) ){
								Integer propValue = map.get(productPropertyStr) != null ? Integer.valueOf(map.get(productPropertyStr)):0;
								conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr + "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode() + "' AND pp.prop_value = " + propValue);
								if( i < productPropertyArray.size() - 1 ){
									conditionStr.append(" union all ");
								}
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
							CategoryProductResult baimoProducts = this.assemblyUnityPanProduct(baimoProductId, spaceCommonId, designPlan, userId, request);
							basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
						}
					}
				}
			}
		}
		return basicModelMap;
	}
	
	@Override
	public CategoryProductResult assemblyUnityPanProduct(Integer productId,Integer spaceCommonId,DesignPlan designPlan,Integer userId,HttpServletRequest request){
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
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
				if(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(productTypeSysDic.getValuekey())){
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
	
	public String getParentIds(BaseProduct baseProduct){
		StringBuffer parentIds = new StringBuffer();
		List<BaseProduct> list = new ArrayList<>();
		if( baseProduct.getParentId() != null && baseProduct.getParentId() > 0 ){
			BaseProduct product = new BaseProduct();
			product.setParentId(baseProduct.getParentId());
			product.setIsDeleted(0);
			list = getList(product);
			if( Lists.isNotEmpty(list) ){
				for(BaseProduct bp : list){
					parentIds.append(bp.getId()).append(",");
				}
			}
		}
		if( StringUtils.isNotBlank(parentIds) ){
			return parentIds.toString().substring(0,parentIds.length()-1);
		}else{
			return parentIds.toString();
		}
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
				if( ("chuangt".equals(valueKey) || "cant".equals(valueKey) || "shaf".equals(valueKey) || "dians".equals(valueKey) || "xingx".equals(valueKey))
						&& ("baimo".equals(xlDic.getAtt3())||StringUtils.isNotBlank(baseProduct.getBmIds())) ){
					flag = false;
				}else {
					flag = true;
				}
			}
			//平吊天花有贴图
			if( "tianh".equals(dlDic.getValuekey()) && "pdtianh".equals(xlDic.getValuekey()) ){	
				flag = true;
			}
		}
		return flag;
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
}