package com.nork.product.controller.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.properties.AppProperties;
import com.nork.common.properties.ResProperties;
import com.nork.product.model.*;
import com.nork.user.model.UserTypeCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.GroupingSetExpr;
import com.nork.base.controller.BaseController;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.ZipUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanTypeConstant;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.DesignTempletPutawayState;
import com.nork.design.model.constant.DesignPlanType;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.product.service.StructureProductDetailsService;
import com.nork.product.service.StructureProductService;
import com.nork.product.service.UserProductCollectService;
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
 * @Title: BaseProductController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品库Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/baseProduct")
public class WebBaseProductController extends BaseController {
	private static Logger logger = Logger.getLogger(WebBaseProductController.class);
	private final JsonDataServiceImpl<BaseProduct> JsonUtil = new JsonDataServiceImpl<BaseProduct>();
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/product/baseProduct";
	private final String BASEMAIN = "/" + STYLE + "/product/base/baseProduct";
	private final String JSPMAIN = "/" + JSPSTYLE + "/product/baseProduct";
	
	@Autowired
	private StructureProductService structureProductService;
	@Autowired
	private ProductCategoryRelService productCategoryRelService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private UserProductCollectService userProductCollectService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private StructureProductDetailsService structureProductDetailsService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseProductStyleService baseProductStyleService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	
	/**
	 * 产品详情
	 */
	@RequestMapping(value = "/productDetails")
	public Object productDetails(@ModelAttribute("baseProduct") BaseProduct baseProduct, HttpServletRequest request,
			HttpServletResponse response) {

		baseProduct = baseProductService.get(baseProduct.getId());

		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!");
		}
		try {
			// 产品详情逻辑
			productDetailContent(baseProduct,null,new DesignPlan(), request);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", baseProduct.getMsgId());
		}
		ResponseEnvelope<BaseProduct> res = new ResponseEnvelope<BaseProduct>(baseProduct);
		request.setAttribute("res", res);
		request.setAttribute("product", baseProduct);
		return "/online/product/proCategory/product_details";
	}

	/**
	 * 返回模型地址接口
	 * */
	@RequestMapping(value = "/getModelFilePath")
	@ResponseBody 
	public Object getModelFilePath(Integer id,String modelType,String msgId,HttpServletRequest request, HttpServletResponse response) {
		String urlPath = "";
		BaseProduct baseProduct = new BaseProduct();
		if (id == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少id!", msgId);
		}
		if(Utils.enableRedisCache()){
			baseProduct = BaseProductCacher.get(id);
		}else{
			baseProduct = baseProductService.get(id);
		}
		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "产品不存在!", msgId);
		}
		if(baseProduct.getWindowsU3dModelId() == null){
			return new ResponseEnvelope<BaseProduct>(false, "产品模型路径为空!", msgId);
		}
		try {
			ResModel resModel = resModelService.get(baseProduct.getWindowsU3dModelId());
			if(resModel != null){
				urlPath = resModel.getModelPath();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", msgId);
		}
		return urlPath;
	}
	/**
	 * 产品详情接口
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/productDetailsWeb")
	@ResponseBody 
	public Object productDetailsWeb(@PathVariable String style, @ModelAttribute("baseProduct") BaseProduct baseProduct,
			Integer designPlanId, Integer spaceCommonId,String planProductId,@RequestParam(value = "modelType",required=false) String modelType, HttpServletRequest request, HttpServletResponse response) {
		Map<Object,Object>paramsMap=new HashMap<Object,Object>();
		paramsMap.put("designPlanId", designPlanId);
		paramsMap.put("spaceCommonId", spaceCommonId);
		paramsMap.put("planProductId", planProductId);
		if(baseProduct!=null){
			paramsMap.put("baseProductId", baseProduct.getId());
		}
		ResponseEnvelope ResponseEnvelope = null;
		if(Utils.enableRedisCache()){
			ResponseEnvelope = CommonCacher.getAll(ModuleType.BaseProduct,"productDetailsWeb",paramsMap);
		}
		
		if(ResponseEnvelope!=null){
			return  ResponseEnvelope;
		}
		Long startTime=System.currentTimeMillis();
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;	
		
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseProduct = (BaseProduct) JsonUtil.getJsonToBean(jsonStr, BaseProduct.class);
			if (baseProduct == null) {
				return new ResponseEnvelope<BaseProduct>(false, "none", "传参异常!");
			}
			id = baseProduct.getId();
			msgId = baseProduct.getMsgId();
		} else {
			id = baseProduct.getId();
			msgId = baseProduct.getMsgId();
		}

		if (id == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少id!", msgId);
		}
		if(Utils.enableRedisCache()){
			baseProduct = BaseProductCacher.get(id);
		}else{
			baseProduct = baseProductService.get(id);
		}
		
		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "产品不存在!", msgId);
		}
		try {
			
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setId(-1);
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}
			//获取该用户绑定的序列号品牌
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			if( Utils.enableRedisCache() ){
				authorizedList = AuthorizedConfigCacher.getList(authorizedConfig);
			}else{
				authorizedList = authorizedConfigService.getList(authorizedConfig);
			}
			String brandIds = "";
			for(AuthorizedConfig ac : authorizedList){
				if( StringUtils.isNotBlank(ac.getBrandIds()) ){
					brandIds += ac.getBrandIds()+",";
				}
			}
			/*品牌是否被绑定状态*/
			String productBrandId = ","+baseProduct.getBrandId()+",";
			if( StringUtils.isEmpty(brandIds) ){
				baseProduct.setSalePrice(-1.0);
			}
			if( (","+brandIds).indexOf(productBrandId)==-1 ){
				baseProduct.setSalePrice(-1.0);
			}
			DesignTemplet designTemplet = new DesignTemplet();
			DesignPlan designPlan=null;
			if (designPlanId != null) {
				if(Utils.enableRedisCache()){
					designPlan = DesignCacher.get(designPlanId);
					designTemplet = null!=designPlan?DesignCacher.getTemplet(designPlan.getDesignTemplateId()):null;
				}else{
					designPlan = designPlanService.get(designPlanId);
					designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
				}
			}

			// 详情逻辑
			productDetailContent(baseProduct,planProductId,modelType,designPlan, request);

			if (StringUtils.isBlank(baseProduct.getProductTypeMark())) { //获取大类
				SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Utils.getIntValue(baseProduct.getProductTypeValue()));
				if (bigSd != null) {																					
					if (StringUtils.isNotEmpty(bigSd.getValuekey()) && baseProduct.getProductSmallTypeValue() != null) {//获取小类
						SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(), baseProduct.getProductSmallTypeValue());
						if (smallSd != null) {
							baseProduct.setProductSmallTypeMark(smallSd.getValuekey());
						}
					}
					baseProduct.setProductTypeMark(bigSd.getValuekey());
				}
			}
			if (baseProduct.getProductTypeMark() != null) { //获取大类
				SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Utils.getIntValue(baseProduct.getProductTypeValue()));
				if (bigSd != null) {																					
					if (StringUtils.isNotEmpty(bigSd.getValuekey()) && baseProduct.getProductSmallTypeValue() != null) {//获取小类
						SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(), baseProduct.getProductSmallTypeValue());
						baseProduct.setShowU3dModel(smallSd.getShowU3dModel());
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
			Map<String,String>map=new HashMap<String,String>();
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
			/*String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
			Integer isSplit=0;
			List<SplitTextureDTO> splitTextureDTOList=null;
			if(StringUtils.isNotBlank(splitTexturesInfo)){
				Map<String,Object> map1=baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
				isSplit=(Integer) map1.get("isSplit");
				splitTextureDTOList=(List<SplitTextureDTO>) map1.get("splitTexturesChoose");
			}else{
				*//*普通产品*//*
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
			}*/
			/*处理拆分材质产品返回默认材质->end*/
			baseProduct.setProductId(id);
			//这个属性在详情里没有用到，而其他接口有用到且是数组类型，设置为null，如果是“”客户端报错
			baseProduct.setSplitTexturesInfo(null);
			Long endTime=System.currentTimeMillis();
			Map<String,CategoryProductResult> baimoRule = baseProductService.setbaimoRuleMap(spaceCommonId, request, loginUser.getId(), baseProduct.getProductSmallTypeCode(), designPlan, map);
			baseProduct.setBasicModelMap(baimoRule);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", msgId);
		}
		ResponseEnvelope responseEnvelope_=new ResponseEnvelope<BaseProduct>(baseProduct, msgId, true);
		if(Utils.enableRedisCache()){
			CommonCacher.addAll(ModuleType.BaseProduct,"productDetailsWeb",paramsMap,responseEnvelope_);
		}
		return responseEnvelope_;
	}

	/**
	 * 产品详情
	 */
	@RequestMapping(value = "/proDetails")
	public Object proDetails(@ModelAttribute("baseProduct") BaseProduct baseProduct, HttpServletRequest request,
			HttpServletResponse response) {

		baseProduct = baseProductService.get(baseProduct.getId());

		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!");
		}
		// logger.info("---"+baseProduct.getColorlist().size()+"------");
		// 由于加了缓存需清空上一次的记录
		baseProduct.getColorlist().clear();
		baseProduct.getMateriallist().clear();
		baseProduct.getSmallPiclist().clear();
		try {
			Integer brandId = baseProduct.getBrandId();
			if (brandId != null) {
				BaseBrand baseBrand = baseBrandService.get(brandId);
				if (baseBrand != null) {
					baseProduct.setBrandName(baseBrand.getBrandName());
				}
			}
			// 风格
			Integer productStyleId = baseProduct.getProStyleId();
			if (productStyleId != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productStyle", productStyleId);
				baseProduct.setProductStyle(dictionary.getName());
			}
			
			String productType = baseProduct.getProductTypeValue();
			if (!StringUtils.isEmpty(productType)) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType",
						new Integer(productType));
				baseProduct.setProductType(dictionary.getName());
				baseProduct.setProductTypeName(dictionary.getName());
				baseProduct.setProductTypeKey(dictionary.getValuekey());
			}
			// 产品图片列表
			String smallPicIds = baseProduct.getPicIds();
			if (smallPicIds != null && !"".equals(smallPicIds)) {
				String[] strs = smallPicIds.split(",");
				for (String smallPic : strs) {
					ResPic resPic = resPicService.get(Utils.getIntValue(smallPic));
					if( StringUtils.isNotBlank(resPic.getSmallPicInfo()) ){
						Map<String,String> map = Utils.getMapFromStr(resPic.getSmallPicInfo());
						if( StringUtils.isNotBlank(map.get("ipad")) ){
							ResPic ipadPic = resPicService.get(Utils.getIntValue(map.get("ipad")));
							resPic.setIpadThumbnailPath(ipadPic==null?"":ipadPic.getPicPath());
						}
						if( StringUtils.isNotBlank(map.get("web")) ){
							ResPic webPic = resPicService.get(Utils.getIntValue(map.get("web")));
							resPic.setWebThumbnailPath(webPic==null?"":webPic.getPicPath());
						}
					}
					baseProduct.getSmallPiclist().add(resPic);
				}
			}
			// 产品颜色标识
			Integer colorId = baseProduct.getColorId();
			if (colorId != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId);
				baseProduct.setProductColorKey(dictionary.getAtt1());
			}
			// 材质
			String materialIds = baseProduct.getMaterialPicIds();
			ResPic resp = new ResPic();
			if (StringUtils.isNotBlank(materialIds)) {
				if (materialIds.contains(",")) {
					String[] strs = materialIds.split(",");
					resp = resPicService.get(Utils.getIntValue(strs[0]));
					request.setAttribute("materialId", strs[0]);
					request.setAttribute("materialPath", resp == null ? "" : resp.getPicPath());
				} else {
					resp = resPicService.get(Utils.getIntValue(materialIds));
					request.setAttribute("materialId", materialIds);
					request.setAttribute("materialPath", resp == null ? "" : resp.getPicPath());
				}
			}

			// 合并的产品Id
			String mergeIds = baseProduct.getMergeProductIds();

			if (StringUtils.isBlank(mergeIds)) {
				Integer colorId2 = baseProduct.getColorId();
				/* 颜色 */
				if (colorId2 != null) {
					SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId2);
					MergeProductResult mergeProductResult = new MergeProductResult();
					mergeProductResult.setState(2);
					mergeProductResult.setColorId(colorId2);
					mergeProductResult.setColorName(dictionary.getName());
					baseProduct.getColorlist().add(mergeProductResult);
				}
				/* 材质 */
				if (StringUtils.isNotBlank(materialIds)) {
					MergeProductResult mergeProductResult = new MergeProductResult();
					mergeProductResult.setState(1);
					if (materialIds.contains(",")) {
						String[] strs = materialIds.split(",");
						ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
						if (resPic != null) {
							// 页面默认选中材质（状态2）
							if (resp != null) {
								if (resp.getPicName().equals(resPic.getPicName())) {
									mergeProductResult.setState(2);
								}
							}
							mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
							mergeProductResult.setMaterialPicPath(resPic == null ? "" : resPic.getPicPath());
							baseProduct.getMateriallist().add(mergeProductResult);
						}
					} else {
						ResPic resPic = resPicService.get(Utils.getIntValue(materialIds));
						if (resPic != null) {
							// 页面默认选中材质（状态2）
							if (resp != null) {
								if (resp.getPicName().equals(resPic.getPicName())) {
									mergeProductResult.setState(2);
								}
							}
							mergeProductResult.setMaterialPicId(Utils.getIntValue(materialIds));
							mergeProductResult.setMaterialPicPath(resPic == null ? "" : resPic.getPicPath());
							baseProduct.getMateriallist().add(mergeProductResult);
						}
					}
				}
			}

			if (StringUtils.isNotBlank(mergeIds) && mergeIds.contains(",")) {
				String arr[] = mergeIds.split(",");
				String colorValue = ".";
				String materialName = ".";
				for (String mergeId : arr) {
					BaseProduct bp = baseProductService.get(Utils.getIntValue(mergeId));
					// 颜色
					if (bp.getColorId() != null) {
						if (colorValue.indexOf(("." + bp.getColorId() + ".")) == -1) {
							MergeProductResult mergeProductResult = new MergeProductResult();
							SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor",
									bp.getColorId());
							// 是该本产品 页面默认选中颜色（状态2）
							mergeProductResult.setState(1);
							if (bp.getColorId() == baseProduct.getColorId()) {
								mergeProductResult.setState(2);
							}
							mergeProductResult.setColorId(bp.getColorId());
							mergeProductResult.setProductId(Utils.getIntValue(mergeId));
							mergeProductResult.setColorName(dictionary.getName());
							baseProduct.getColorlist().add(mergeProductResult);
						}
						colorValue = colorValue + bp.getColorId() + ".";
					}

					// 材质
					String materialPicIds = bp.getMaterialPicIds();
					if (StringUtils.isNotBlank(materialPicIds)) {
						MergeProductResult mergeProductResult = new MergeProductResult();
						mergeProductResult.setState(1);
						if (materialPicIds.contains(",")) {
							String[] strs = materialPicIds.split(",");
							ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
							if (resPic != null) {
								if (materialName.indexOf(("." + resPic.getPicName() + ".")) == -1) {
									// 页面默认选中材质（状态2）
									if (resp != null) {
										if (resp.getPicName().equals(resPic.getPicName())) {
											mergeProductResult.setState(2);
										}
									}
									if (mergeId.equals(baseProduct.getId() + "")) {
										mergeProductResult.setState(2);
									}
									mergeProductResult.setProductId(Utils.getIntValue(mergeId));
									mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
									mergeProductResult.setMaterialName(resPic.getPicName());
									mergeProductResult.setMaterialPicPath(resPic.getPicPath());
									baseProduct.getMateriallist().add(mergeProductResult);
								}
								materialName = materialName + resPic.getPicName() + ".";
							}
						} else {
							ResPic resPic = resPicService.get(Utils.getIntValue(materialPicIds));
							if (resPic != null) {
								if (materialName.indexOf(("." + resPic.getPicName() + ".")) == -1) {
									// 页面默认选中材质（状态2）
									if (resp != null) {
										if (resp.getPicName().equals(resPic.getPicName())) {
											mergeProductResult.setState(2);
										}
									}
									if (mergeId.equals(baseProduct.getId() + "")) {
										mergeProductResult.setState(2);
									}
									mergeProductResult.setProductId(Utils.getIntValue(mergeId));
									mergeProductResult.setMaterialPicId(Utils.getIntValue(materialPicIds));
									mergeProductResult.setMaterialName(resPic.getPicName());
									mergeProductResult.setMaterialPicPath(resPic.getPicPath());
									baseProduct.getMateriallist().add(mergeProductResult);
								}
								materialName = materialName + resPic.getPicName() + ".";
							}
						}
					}
				}
			}
			if (StringUtils.isNotBlank(baseProduct.getU3dModelId())) {
				ResModel resModel = resModelService.get(Integer.valueOf(baseProduct.getU3dModelId()));
				if (resModel != null)
					baseProduct.setU3dModelPath(resModel.getModelPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", baseProduct.getMsgId());
		}
		ResponseEnvelope<BaseProduct> res = new ResponseEnvelope<BaseProduct>(baseProduct);
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setProductId(baseProduct.getId());
		int count = userProductCollectService.getCount(userProductCollectSearch);
		if (count > 0) {
			/* 被收藏 */
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
		request.setAttribute("res", res);
		request.setAttribute("product", baseProduct);

		return "/online/product/productDetails";
	}

	/**
	 * 产品详情
	 */
	@RequestMapping(value = "/categoryProductList")
	public Object categoryProductList(@ModelAttribute("baseProduct") BaseProduct baseProduct,
			HttpServletRequest request, HttpServletResponse response) {

		BaseProduct product = baseProductService.get(baseProduct.getId());
		if (product == null) {
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", baseProduct.getMsgId());
		}
		try {
			product.setBrandName(product.getBrandId() == null ? ""
					: baseBrandService.get(product.getBrandId()) == null ? ""
							: baseBrandService.get(product.getBrandId()).getBrandName());
			product.setProductColor(product.getColorId() == null ? ""
					: sysDictionaryService.getSysDictionaryByValue("productColor", product.getColorId())
							.getName());
			product.setProductType(StringUtils.isEmpty(product.getProductTypeValue()) ? ""
					: sysDictionaryService
							.getSysDictionaryByValue("productType", new Integer(product.getProductTypeValue()))
							.getName());
			String smallPicIds = product.getPicIds();
			if (smallPicIds != null && !"".equals(smallPicIds)) {
				if (smallPicIds.contains(",")) {
					String[] strs = smallPicIds.split(",");
					for (String smallPic : strs) {
						ResPic resPic = resPicService.get(Utils.getIntValue(smallPic));
						product.getSmallPiclist().add(resPic.getPicPath());
					}
				} else {
					ResPic resPic = resPicService.get(Utils.getIntValue(smallPicIds));
					product.getSmallPiclist().add(resPic.getPicPath());
				}
			}
			String materialPicIds = product.getMaterialPicIds();
			if (materialPicIds != null && !"".equals(materialPicIds)) {
				if (smallPicIds.contains(",")) {
					String[] strs = materialPicIds.split(",");
					for (String material : strs) {
						ResPic resPic = resPicService.get(Utils.getIntValue(material));
						product.getMateriallist().add(resPic.getPicPath());
					}
				} else {
					ResPic resPic = resPicService.get(Utils.getIntValue(materialPicIds));
					product.getMateriallist().add(resPic.getPicPath());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", baseProduct.getMsgId());
		}

		ResponseEnvelope<BaseProduct> res = new ResponseEnvelope<BaseProduct>(product);
		request.setAttribute("res", res);
		request.setAttribute("product", product);

		return "/online/brand/show";
	}

	/**
	 * 产品详情
	 */
	@RequestMapping(value = "/showDetails")
	public Object showDetails(@ModelAttribute("baseProduct") BaseProduct baseProduct, HttpServletRequest request,
			HttpServletResponse response) {

		baseProduct = baseProductService.get(baseProduct.getId());

		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!");
		}
		logger.info("---" + baseProduct.getColorlist().size() + "------");
		// 由于加了缓存需清空上一次的记录
		baseProduct.getColorlist().clear();
		baseProduct.getMateriallist().clear();
		baseProduct.getSmallPiclist().clear();
		try {
			Integer brandId = baseProduct.getBrandId();
			if (brandId != null) {
				BaseBrand baseBrand = baseBrandService.get(brandId);
				if (baseBrand != null) {
					baseProduct.setBrandName(baseBrand.getBrandName());
				}
			}
			// 风格
			Integer productStyleId = baseProduct.getProStyleId();
			if (productStyleId != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productStyle", productStyleId);
				baseProduct.setProductStyle(dictionary.getName());
			}
			String productType = baseProduct.getProductTypeValue();
			if (!StringUtils.isEmpty(productType)) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType",
						new Integer(productType));
				baseProduct.setProductType(dictionary.getName());
			}
			// 产品图片列表
			String smallPicIds = baseProduct.getPicIds();
			if (smallPicIds != null && !"".equals(smallPicIds)) {
				if (smallPicIds.contains(",")) {
					String[] strs = smallPicIds.split(",");
					for (String smallPic : strs) {
						ResPic resPic = resPicService.get(Utils.getIntValue(smallPic));
						baseProduct.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
					}
				} else {
					ResPic resPic = resPicService.get(Utils.getIntValue(smallPicIds));
					baseProduct.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
				}
			}

			Integer colorId = baseProduct.getColorId();
			if (colorId != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId);
				baseProduct.setProductColorKey(dictionary.getAtt1());
			}
			// 材质
			String materialIds = baseProduct.getMaterialPicIds();
			ResPic resp = new ResPic();
			if (StringUtils.isNotBlank(materialIds)) {
				if (materialIds.contains(",")) {
					String[] strs = materialIds.split(",");
					resp = resPicService.get(Utils.getIntValue(strs[0]));
					request.setAttribute("materialId", strs[0]);
					request.setAttribute("materialPath", resp == null ? "" : resp.getPicPath());
				} else {
					resp = resPicService.get(Utils.getIntValue(materialIds));
					request.setAttribute("materialId", materialIds);
					request.setAttribute("materialPath", resp == null ? "" : resp.getPicPath());
				}
			}
			// 合并的产品Id
			String mergeIds = baseProduct.getMergeProductIds();
			if (StringUtils.isNotBlank(mergeIds) && mergeIds.contains(",")) {
				String arr[] = mergeIds.split(",");
				String colorValue = ".";
				String materialName = ".";
				for (String mergeId : arr) {
					BaseProduct bp = baseProductService.get(Utils.getIntValue(mergeId));
					// 颜色
					if (bp.getColorId() != null) {
						if (colorValue.indexOf(("." + bp.getColorId() + ".")) == -1) {
							MergeProductResult mergeProductResult = new MergeProductResult();
							SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor",
									bp.getColorId());
							// 是该本产品 页面默认选中颜色（状态2）
							mergeProductResult.setState(1);
							if (bp.getColorId() == baseProduct.getColorId()) {
								mergeProductResult.setState(2);
							}
							mergeProductResult.setColorId(bp.getColorId());
							mergeProductResult.setProductId(Utils.getIntValue(mergeId));
							mergeProductResult.setColorName(dictionary.getName());

							baseProduct.getColorlist().add(mergeProductResult);
						}
						colorValue = colorValue + bp.getColorId() + ".";
					}

					// 材质
					String materialPicIds = bp.getMaterialPicIds();
					if (StringUtils.isNotBlank(materialPicIds)) {
						MergeProductResult mergeProductResult = new MergeProductResult();
						mergeProductResult.setState(1);
						if (materialPicIds.contains(",")) {
							String[] strs = materialPicIds.split(",");
							ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
							if (resPic != null) {
								if (materialName.indexOf(("." + resPic.getPicName() + ".")) == -1) {
									// 页面默认选中材质（状态2）
									if (resp != null) {
										if (resp.getPicName().equals(resPic.getPicName())) {
											mergeProductResult.setState(2);
										}
									}
									if (mergeId.equals(baseProduct.getId() + "")) {
										mergeProductResult.setState(2);
									}
									mergeProductResult.setProductId(Utils.getIntValue(mergeId));
									mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
									mergeProductResult.setMaterialName(resPic.getPicName());
									mergeProductResult.setMaterialPicPath(resPic.getPicPath());
									baseProduct.getMateriallist().add(mergeProductResult);
								}
								materialName = materialName + resPic.getPicName() + ".";
							}
						} else {
							ResPic resPic = resPicService.get(Utils.getIntValue(materialPicIds));
							if (resPic != null) {
								if (materialName.indexOf(("." + resPic.getPicName() + ".")) == -1) {
									// 页面默认选中材质（状态2）
									if (resp != null) {
										if (resp.getPicName().equals(resPic.getPicName())) {
											mergeProductResult.setState(2);
										}
									}
									if (mergeId.equals(baseProduct.getId() + "")) {
										mergeProductResult.setState(2);
									}
									mergeProductResult.setProductId(Utils.getIntValue(mergeId));
									mergeProductResult.setMaterialPicId(Utils.getIntValue(materialPicIds));
									mergeProductResult.setMaterialName(resPic.getPicName());
									mergeProductResult.setMaterialPicPath(resPic.getPicPath());
									baseProduct.getMateriallist().add(mergeProductResult);
								}
								materialName = materialName + resPic.getPicName() + ".";
							}
						}
					}
				}
			}
			if (StringUtils.isNotBlank(baseProduct.getU3dModelId())) {
				ResModel resModel = resModelService.get(Integer.valueOf(baseProduct.getU3dModelId()));
				if (resModel != null)
					baseProduct.setU3dModelPath(resModel.getModelPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!", baseProduct.getMsgId());
		}

		ResponseEnvelope<BaseProduct> res = new ResponseEnvelope<BaseProduct>(baseProduct);
		request.setAttribute("res", res);
		request.setAttribute("product", baseProduct);

		return "/online/product/showDetails";
	}

	/**
	 * 产品详情选择颜色和材质
	 */
	@RequestMapping(value = "/getDetailProductId")
	@ResponseBody
	public Object getDetailProductId(Integer colorId, String materialId, String onclickType, String mergeProductIds,
			HttpServletRequest request, HttpServletResponse response) {

		Map map = new HashMap();
		map.put("colorId", colorId);
		map.put("materialId", materialId);
		map.put("onclickType", onclickType);// color , material
		map.put("mergeProductIds", mergeProductIds);//
		Integer id = baseProductService.getDetailProduct(map);
		BaseProduct baseProduct = baseProductService.get(id);
		if (baseProduct == null) {
			baseProduct = new BaseProduct();
		}
		return new ResponseEnvelope<BaseProduct>(baseProduct, baseProduct.getMsgId(), true);
	}

	/**
	 * 产品库列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("baseProductSearch") BaseProduct baseProduct
				,HttpServletRequest request, HttpServletResponse response) {

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (null == loginUser) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY,baseProduct.getMsgId());
		}
		baseProduct.setUserId(loginUser.getId());
		String versionType = Utils.getPropertyName("app", ResProperties.SYS_VERSION_TYPE, "1").trim();/*1为外网  2  为内网    默认为外网*/
		if( UserTypeCode.USER_TYPE_INNER == loginUser.getUserType() && "2".equals(versionType)){
			baseProduct.setIsInternalUser("yes");
        }

		List<CategoryProductResult> list = new ArrayList<CategoryProductResult> ();
		int total = 0;
		try {
			//授权码绑定的品牌
			String brandIds = authorizedConfigService.getAuthorizedBrandIdsByUserId(loginUser.getId());

			//如果是厂商，则只能查询这个厂商品牌下的产品及行业之外的产品
			if( UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType() ){
				//设置品牌行业值，用户过滤同行业品牌产品
				List<BrandIndustryModel> brandIndustryModelList = baseBrandService.getAuthorizedBrandFilterCondition(loginUser.getId());
				baseProduct.setBrandIndustryModelList(brandIndustryModelList);
			}
			if( Utils.enableRedisCache() ){
				total = BaseProductCacher.getBrandProductCount(baseProduct);
			}else{
				total = baseProductService.getBrandProductsCount(baseProduct);
			}
			if (total > 0) {
				if( Utils.enableRedisCache() ){
					list = BaseProductCacher.getBrandProductList(baseProduct);
				}else{
					list = baseProductService.getBrandProductsList(baseProduct);
				}
			}
			for(CategoryProductResult cpr : list){
				/**品牌是否被绑定状态*/
				String productBrandId = ","+cpr.getBrandId()+",";
				if( StringUtils.isEmpty(brandIds) ){
					cpr.setSalePrice("-1.0");
				}
				if( (","+brandIds).indexOf(productBrandId)==-1 ){
					cpr.setSalePrice("-1.0");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, "数据异常!",baseProduct.getMsgId());
		}
		return new ResponseEnvelope<CategoryProductResult>(total, list,baseProduct.getMsgId());
	}

	/**
	 * 产品详情选择属性
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getDetailAttributeProductId")
	@ResponseBody
	public Object getDetailAttributeProductId(Integer colorId, String materialId, String onclickType,
			String mergeProductIds, Integer productId, String attributeKey, String attributeValueKey,
			String msgId,HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(onclickType)) {
			onclickType = "material";
		}
		Map map = new HashMap();
		map.put("colorId", colorId);
		map.put("materialId", materialId);
		map.put("onclickType", onclickType);// color , material
		map.put("mergeProductIds", mergeProductIds);//
		Integer id = baseProductService.getDetailProduct(map);
		if (StringUtils.isNotBlank(attributeKey) && StringUtils.isNotBlank(attributeValueKey)) {

			//得到之前产品的属性
			List<ProductAttribute> currentList = new ArrayList<ProductAttribute>();
			ProductAttribute currentPa = new ProductAttribute();
			currentPa.setProductId(id);
			if(Utils.enableRedisCache()){
				currentList = BaseProductCacher.getProductAttributeList(currentPa);
			}else{
				currentList = productAttributeService.getList(currentPa);
			}
			
			// 需要匹配的属性
			List<ProductAttribute> newList = new ArrayList<ProductAttribute>();
			ProductAttribute newPa = new ProductAttribute();
			newPa.setAttributeKey(attributeKey);
			newPa.setAttributeValueKey(attributeValueKey);
			List<Integer> pidList = new ArrayList<Integer>();
			if (StringUtils.isNoneBlank(mergeProductIds)) {
				String mergeArr[] = mergeProductIds.split(",");
				for (String mergeIds : mergeArr) {
					pidList.add(Utils.getIntValue(mergeIds));
				}
				newPa.setProductIdList(pidList);
			}
			if(Utils.enableRedisCache()){
				newList = BaseProductCacher.getMergeAttribute(newPa);
			}else{
				newList = productAttributeService.getMergeAttribute(newPa);
			}
			
			Set<ProductAttribute> currentSet = new HashSet<ProductAttribute>();
			if (CustomerListUtils.isNotEmpty(currentList)) {
				for (ProductAttribute pa : currentList) {
					if (!pa.getAttributeKey().equals(attributeKey)) {
						currentSet.add(pa);
					}
				}
			}
			if (CustomerListUtils.isEmpty(currentSet)) {
				BaseProduct baseProduct=null;
				if(Utils.enableRedisCache()){
					baseProduct = BaseProductCacher.getBaseProductServiceById(productId);
				}else{
					baseProduct = baseProductService.get(productId);
				}
				
				if (baseProduct == null) {
					baseProduct = new BaseProduct();
					return new ResponseEnvelope<BaseProduct>(baseProduct, baseProduct.getMsgId(), true);
				}
			}
			// 匹配属性，获取产品ＩＤ
			if (CustomerListUtils.isNotEmpty(newList)) {
				Integer arr[] = new Integer[newList.size()];
				int index = 0;
				int temp = 0;
				for (int n = 0; n < newList.size(); n++) {
					int tempNum = 0;
					ProductAttribute pa = newList.get(n);
					ProductAttribute attribute = new ProductAttribute();
					attribute.setProductId(pa.getProductId());
					List<ProductAttribute> list=null;
					if(Utils.enableRedisCache()){
						list = BaseProductCacher.getProductAttributeList(attribute);
					}else{
						list = productAttributeService.getList(attribute);
					}
					
					if (CustomerListUtils.isNotEmpty(list)) {
						arr[n] = list.size();
						for (ProductAttribute pai : list) {
							if (pai.getAttributeKey().equals(attributeKey)) {
								continue;
							}
							Iterator<ProductAttribute> iterator = currentSet.iterator();
							while (iterator.hasNext()) {
								ProductAttribute iteratorPa = iterator.next();
								if (iteratorPa.getAttributeKey().equals(pai.getAttributeKey())
										&& iteratorPa.getAttributeValueKey().equals(pai.getAttributeValueKey())) {
									productId = pai.getProductId();
									tempNum = 1;
									break;
								}
							}
							if (tempNum == 1)
								break;
						}
						if (tempNum == 1) {
							temp = 1;
						} else {
							if (temp == 0) {
								if (arr[index] > arr[n]) {
									index = n;
								}
								productId = newList.get(index).getProductId();
							}
						}
					} else {
						productId = pa.getProductId();
						break;
					}
				}
			}
		}
		BaseProduct baseProduct=null;
		if(Utils.enableRedisCache()){
			baseProduct = BaseProductCacher.getBaseProductServiceById(productId);
		}else{
			baseProduct = baseProductService.get(productId);
		}
		
		if (baseProduct == null) {
			baseProduct = new BaseProduct();
		}
		baseProduct.setProductId(baseProduct.getId());
		return new ResponseEnvelope<BaseProduct>(baseProduct, msgId, true);
	}

	@SuppressWarnings("unchecked")
	public void productDetailContent(BaseProduct baseProduct,String planProductId,DesignPlan designPlan, HttpServletRequest request) throws Exception {
		//媒介类型.如果没有值，则表示为web前端（2）
 		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		// 当前登录人是否已经收藏该产品
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setUserId(loginUser.getId());
		userProductCollectSearch.setProductId(baseProduct.getId());
		Integer count = userProductCollectService.getCount(userProductCollectSearch);
		if (count > 0) {
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
//		baseProduct.setSplitTexturesInfo(null);
		//获取不同媒介u3d模型

//		String planVersion = null;
//		if(designPlan!=null){
//			planVersion = designPlan.getPlanVersion();
//		}
//		String modelId = baseProductService.getU3dModelId(mediaType,baseProduct,planVersion);
//		
		//String modelId = baseProductService.getU3dModelId(mediaType,baseProduct,designPlan.getPlanVersion());
		

		String modelId = baseProductService.getU3dModelId(mediaType,baseProduct);

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
		// logger.info("---"+baseProduct.getColorlist().size()+"------");
		// 由于加了缓存需清空上一次的记录
//		baseProduct.getColorlist().clear();
//		baseProduct.getMateriallist().clear();
//		baseProduct.getSmallPiclist().clear();

		Integer brandId = baseProduct.getBrandId();
		if (brandId != null) {
			BaseBrand baseBrand = baseBrandService.get(brandId);
			if (baseBrand != null) {
				baseProduct.setBrandName(baseBrand.getBrandName());
			}
		}

		// 风格
		/*Integer productStyleId = baseProduct.getProStyleId();
		if (productStyleId != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productStyle", productStyleId,
					request);
			baseProduct.setProductStyle(dictionary.getName());
		}*/
		
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
	 			//baseProduct.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
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
//		ResPic resp = new ResPic();
		ResTexture resTexture1 = null ;
		if (StringUtils.isNotBlank(materialIds)) {
			resTexture1 = new ResTexture();
			String[] strs = materialIds.split(",");
			baseProduct.setMaterialId(strs[0]);
			resTexture1 = resTextureService.get(Integer.valueOf(strs[0]));
//			resp = resPicService.get(Utils.getIntValue(strs[0]));
		}
		/*处理材质返回格式(huangsongbo 2017.1.4)*/
		String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
		Integer isSplit=0;
		List<SplitTextureDTO> splitTextureDTOList=null;
		List<SplitTextureDTO> splitTextureDTOInfoList=null;
		if(StringUtils.isNotBlank(splitTexturesInfo)){
			Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
			Map<String,Object> mapInfo=baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"info");
			isSplit=(Integer) map.get("isSplit");
			splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
			splitTextureDTOInfoList = (List<SplitTextureDTO>) mapInfo.get("splitTexturesInfo");
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
		baseProduct.setSplitTexturesInfoList(splitTextureDTOInfoList);
		/*处理材质返回格式(huangsongbo 2017.1.4)->end*/
		// 合并的产品Id
//		String mergeIds = baseProduct.getMergeProductIds();
		String mergeIds = getParentIds(baseProduct);
		if (StringUtils.isBlank(mergeIds)) {
			/*Integer colorId2 = baseProduct.getColorId();
			// 颜色
			if (colorId2 != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId2,request);
				MergeProductResult mergeProductResult = new MergeProductResult();
				mergeProductResult.setState(2);
				mergeProductResult.setProductId(baseProduct.getId());
				mergeProductResult.setColorId(colorId2);
				mergeProductResult.setColorName(dictionary.getName());
				baseProduct.getColorlist().add(mergeProductResult);
			} */
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
				BaseProduct bp = baseProductService.get(Utils.getIntValue(mergeId));
				if( bp != null ){
					// 颜色
					/*if (bp.getColorId() != null) {
						if (colorValue.indexOf(("." + bp.getColorId() + ".")) == -1) {
							MergeProductResult mergeProductResult = new MergeProductResult();
							SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor",
									bp.getColorId(), request);
							// 是该本产品 页面默认选中颜色（状态2）
							mergeProductResult.setState(1);
							if (bp.getColorId().intValue() == baseProduct.getColorId().intValue()) {
								mergeProductResult.setState(2);
							}
							mergeProductResult.setColorId(bp.getColorId());
							mergeProductResult.setProductId(Utils.getIntValue(mergeId));
							mergeProductResult.setColorName(dictionary.getName());
							baseProduct.getColorlist().add(mergeProductResult);
						}
						colorValue = colorValue + bp.getColorId() + ".";
					}*/

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
						BaseProduct product = baseProductService.get(dpp.getProductId());
						boolean isHard = false;
						if( product != null ){
							isHard = baseProductService.isHard(product);
						}
						if( isHard ){
							BaseProduct baimoProduct = baseProductService.get(dpp.getInitProductId());
							//获取不同媒介u3d模型
							String u3dModelId = baseProductService.getU3dModelId(mediaType,baimoProduct);
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

	public void productDetailContent(BaseProduct baseProduct,String planProductId,String modelType,DesignPlan designPlan, HttpServletRequest request) throws Exception {
		//媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		// 当前登录人是否已经收藏该产品
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setUserId(loginUser.getId());
		userProductCollectSearch.setProductId(baseProduct.getId());
		Integer count = userProductCollectService.getCount(userProductCollectSearch);
		if (count > 0) {
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
//		baseProduct.setSplitTexturesInfo(null);
		//获取不同媒介u3d模型

//		String planVersion = null;
//		if(designPlan!=null){
//			planVersion = designPlan.getPlanVersion();
//		}
//		String modelId = baseProductService.getU3dModelId(mediaType,baseProduct,planVersion);
//
		//String modelId = baseProductService.getU3dModelId(mediaType,baseProduct,designPlan.getPlanVersion());


		String modelId = baseProductService.getU3dModelId(mediaType,baseProduct);

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
		// logger.info("---"+baseProduct.getColorlist().size()+"------");
		// 由于加了缓存需清空上一次的记录
//		baseProduct.getColorlist().clear();
//		baseProduct.getMateriallist().clear();
//		baseProduct.getSmallPiclist().clear();

		Integer brandId = baseProduct.getBrandId();
		if (brandId != null) {
			BaseBrand baseBrand = baseBrandService.get(brandId);
			if (baseBrand != null) {
				baseProduct.setBrandName(baseBrand.getBrandName());
			}
		}

		// 风格
		/*Integer productStyleId = baseProduct.getProStyleId();
		if (productStyleId != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productStyle", productStyleId,
					request);
			baseProduct.setProductStyle(dictionary.getName());
		}*/

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
				//baseProduct.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
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
//		ResPic resp = new ResPic();
		ResTexture resTexture1 = null ;
		if (StringUtils.isNotBlank(materialIds)) {
			resTexture1 = new ResTexture();
			String[] strs = materialIds.split(",");
			baseProduct.setMaterialId(strs[0]);
			resTexture1 = resTextureService.get(Integer.valueOf(strs[0]));
//			resp = resPicService.get(Utils.getIntValue(strs[0]));
		}
		/*处理材质返回格式(huangsongbo 2017.1.4)*/
		String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
		Integer isSplit=0;
		List<SplitTextureDTO> splitTextureDTOList=null;
		List<SplitTextureDTO> splitTextureDTOInfoList=null;
		if(StringUtils.isNotBlank(splitTexturesInfo)){
			Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(loginUser,modelType,baseProduct.getId(), splitTexturesInfo,"choose");
			Map<String,Object> mapInfo=baseProductService.dealWithSplitTextureInfo(loginUser,modelType,baseProduct.getId(), splitTexturesInfo,"info");
			isSplit=(Integer) map.get("isSplit");
			splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
			splitTextureDTOInfoList = (List<SplitTextureDTO>) mapInfo.get("splitTexturesInfo");
		}else{
			if(resTexture1!=null){
				splitTextureDTOList=new ArrayList<SplitTextureDTO>();
				List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
				SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
				ResTextureDTO resTextureDTO=resTextureService.fromResTexture(loginUser,resTexture1,modelType);
				resTextureDTO.setKey(splitTextureDTO.getKey());
				resTextureDTO.setProductId(baseProduct.getId());
				resTextureDTOList.add(resTextureDTO);
				splitTextureDTO.setList(resTextureDTOList);
				splitTextureDTOList.add(splitTextureDTO);
			}
		}
		baseProduct.setIsSplit(isSplit);
		baseProduct.setSplitTexturesChoose(splitTextureDTOList);
		baseProduct.setSplitTexturesInfoList(splitTextureDTOInfoList);
		/*处理材质返回格式(huangsongbo 2017.1.4)->end*/
		// 合并的产品Id
//		String mergeIds = baseProduct.getMergeProductIds();
		String mergeIds = getParentIds(baseProduct);
		if (StringUtils.isBlank(mergeIds)) {
			/*Integer colorId2 = baseProduct.getColorId();
			// 颜色
			if (colorId2 != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId2,request);
				MergeProductResult mergeProductResult = new MergeProductResult();
				mergeProductResult.setState(2);
				mergeProductResult.setProductId(baseProduct.getId());
				mergeProductResult.setColorId(colorId2);
				mergeProductResult.setColorName(dictionary.getName());
				baseProduct.getColorlist().add(mergeProductResult);
			} */
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
				BaseProduct bp = baseProductService.get(Utils.getIntValue(mergeId));
				if( bp != null ){
					// 颜色
					/*if (bp.getColorId() != null) {
						if (colorValue.indexOf(("." + bp.getColorId() + ".")) == -1) {
							MergeProductResult mergeProductResult = new MergeProductResult();
							SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor",
									bp.getColorId(), request);
							// 是该本产品 页面默认选中颜色（状态2）
							mergeProductResult.setState(1);
							if (bp.getColorId().intValue() == baseProduct.getColorId().intValue()) {
								mergeProductResult.setState(2);
							}
							mergeProductResult.setColorId(bp.getColorId());
							mergeProductResult.setProductId(Utils.getIntValue(mergeId));
							mergeProductResult.setColorName(dictionary.getName());
							baseProduct.getColorlist().add(mergeProductResult);
						}
						colorValue = colorValue + bp.getColorId() + ".";
					}*/

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
						BaseProduct product = baseProductService.get(dpp.getProductId());
						boolean isHard = false;
						if( product != null ){
							isHard = baseProductService.isHard(product);
						}
						if( isHard ){
							BaseProduct baimoProduct = baseProductService.get(dpp.getInitProductId());
							//获取不同媒介u3d模型
							String u3dModelId = baseProductService.getU3dModelId(mediaType,baimoProduct);
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

	/**
	 * 根据图片顺序排序（降序）
	 * @author Administrator
	 *
	 */
	public class ComparatorT implements Comparator {
		public int compare(Object obj1, Object obj2) {
			ResPic unity1 = (ResPic) obj1;
			ResPic unity2 = (ResPic) obj2;
			int flag = (unity2.getSequence() == null ? new Integer(0) : new Integer(unity2.getSequence())) 
					.compareTo(unity1.getSequence() == null ? new Integer(0)
							: new Integer(unity1.getSequence()));
			if (flag == 0) {
				return (unity2.getSequence() == null ? new Integer(0) : new Integer(unity2.getSequence()))
						.compareTo(unity1.getSequence() == null ? new Integer(0)
								: new Integer(unity1.getSequence()));
			} else {
				return flag;
			}
		}
	}
	
	public String getParentIds(BaseProduct baseProduct){
		StringBuffer parentIds = new StringBuffer();
		List<BaseProduct> list = new ArrayList<>();
		if( baseProduct.getParentId() != null && baseProduct.getParentId() > 0 ){
			BaseProduct product = new BaseProduct();
			product.setParentId(baseProduct.getParentId());
			product.setIsDeleted(0);
			list = baseProductService.getList(product);
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
	 * 产品详情选择颜色和材质 接口
	 */
	@RequestMapping(value = "/getDetailProductId")
	@ResponseBody
	public Object getDetailProductId(String msgId, Integer colorId, String materialId, String onclickType,
			String mergeProductIds, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<BaseProduct>(false, "msgId为空!", msgId);
		}
		if (colorId == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少colorId!", msgId);
		}
		if (StringUtils.isBlank(materialId)) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少materialId!", msgId);
		}
		if (StringUtils.isBlank(onclickType)) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少onclickType!", msgId);
		}
		if (StringUtils.isBlank(mergeProductIds)) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少mergeProductIds!", msgId);
		}
		Map map = new HashMap();
		map.put("colorId", colorId);
		map.put("materialId", materialId);
		map.put("onclickType", onclickType);// color , material
		map.put("mergeProductIds", mergeProductIds);//
		Integer id = baseProductService.getDetailProduct(map);
		BaseProduct baseProduct = baseProductService.get(id);

		return new ResponseEnvelope<BaseProduct>(baseProduct, baseProduct.getMsgId(), true);
	}

	/**
	 * 产品详情选择属性
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getDetailAttributeProductIdWeb")
	@ResponseBody
	public Object getDetailAttributeProductIdWeb(String msgId,Integer colorId,String materialId,String onclickType,String mergeProductIds,
			Integer productId,String attributeKey,String attributeValueKey,String planProductId,HttpServletRequest request, HttpServletResponse response) {
		
		if(StringUtils.isBlank(msgId)){
			return new ResponseEnvelope<BaseProduct>(false, "msgId为空!",msgId);
		}
		if(colorId == null ){
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少colorId!",msgId);
		}
		if(productId == null ){
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少productId!",msgId);
		}
		if(StringUtils.isBlank(materialId)){
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少materialId!",msgId);
		}
		if(StringUtils.isBlank(onclickType)){
			onclickType = "material";
		}
		Integer id = -1 ;
		if( StringUtils.isNotEmpty(mergeProductIds) ){
			Map map  = new HashMap();
			map.put("colorId", colorId);
			map.put("materialId", materialId);
			map.put("onclickType", onclickType);/**color , material*/
			map.put("mergeProductIds", mergeProductIds);
			if(Utils.enableRedisCache()){
				id= BaseProductCacher.getDetailProduct(map);
			}else{
				id = baseProductService.getDetailProduct(map);
			}
			
		}else{
			id = productId;
		}
		if( StringUtils.isNotBlank(attributeKey) && StringUtils.isNotBlank(attributeValueKey) ){
			/**得到当前产品的属性*/
			List<ProductAttribute> currentList = new ArrayList<ProductAttribute>(); 
			ProductAttribute currentPa = new ProductAttribute();
			currentPa.setProductId(id);
			if(Utils.enableRedisCache()){
				currentList=BaseProductCacher.getProductAttributeList(currentPa);
			}else{
				currentList = productAttributeService.getList(currentPa);
			}
			
			/**需要匹配的属性*/
			List<ProductAttribute> newList = new ArrayList<ProductAttribute>(); 
			ProductAttribute newPa = new ProductAttribute();
			newPa.setAttributeKey(attributeKey);
			newPa.setAttributeValueKey(attributeValueKey);
			List<Integer> pidList = new ArrayList<Integer>();
			if( StringUtils.isNoneBlank(mergeProductIds) ){
				String mergeArr[] = mergeProductIds.split(",");
				for(String mergeIds : mergeArr){
					pidList.add(Utils.getIntValue(mergeIds));
				}
				newPa.setProductIdList(pidList);
			}
			if(Utils.enableRedisCache()){
				newList=BaseProductCacher.getMergeAttribute(newPa);
			}else{
				newList = productAttributeService.getMergeAttribute(newPa);
			}
			
			Set<ProductAttribute> currentSet = new HashSet<ProductAttribute>();
			/**过滤当前的attributeKey*/
			if( CustomerListUtils.isNotEmpty(currentList) ){
				for(ProductAttribute pa : currentList){
					if( !pa.getAttributeKey().equals(attributeKey) ){
						currentSet.add(pa);
					}
				}
			}
			/**如果当前没有其它属性，则默认显示传过来的ID产品*/
			if(CustomerListUtils.isEmpty(currentSet) ){
				id = productId;
			}
			/**匹配属性，获取产品ID*/
			if( CustomerListUtils.isNotEmpty(newList) ){
				Integer arr[] = new Integer[newList.size()];
				int index = 0;
				int temp = 0;
				for(int n=0;n<newList.size();n++){
					int tempNum = 0;
					ProductAttribute pa = newList.get(n);
					ProductAttribute attribute = new ProductAttribute();
					attribute.setProductId(pa.getProductId());
					List<ProductAttribute> list=null;
					if(Utils.enableRedisCache()){
						list =BaseProductCacher.getProductAttributeList(attribute);
					}else{
						list = productAttributeService.getList(attribute);
					}
					
					if( CustomerListUtils.isNotEmpty(list) ){
						arr[n] = list.size();
						for(ProductAttribute pai : list){
							if( pai.getAttributeKey().equals(attributeKey) ){
								continue;
							}
							Iterator<ProductAttribute> iterator = currentSet.iterator();
							while(iterator.hasNext()){
								ProductAttribute iteratorPa = iterator.next();
								if( iteratorPa.getAttributeKey().equals(pai.getAttributeKey()) && iteratorPa.getAttributeValueKey().equals(pai.getAttributeValueKey())){
									id = pai.getProductId();
									tempNum = 1;
									break;
								}
							}
							if(tempNum == 1)
								break;
						}
						/**tmpNum为1已匹配到产品ID，没有则判断属性最少的产品*/
						if(tempNum == 1)
							temp = 1;
						else{
							if(temp==0){
								if(arr[index] > arr[n]){
									index = n;
								}
								id = newList.get(index).getProductId();
							}
						}
					}else{
						id = pa.getProductId();
						break;
					}
				}
			}
		}
		BaseProduct baseProduct=null;
		if(Utils.enableRedisCache()){
			baseProduct =BaseProductCacher.getBaseProductServiceById(id);
		}else{
			baseProduct = baseProductService.get(id);
		}
		
		if(baseProduct==null){
			return new ResponseEnvelope<BaseProduct>(false, "产品不存在!",msgId);
		}
		try{
	        //详情逻辑
			baseProduct.setProductId(baseProduct.getId());
			productDetailContent(baseProduct,planProductId,new DesignPlan(),request);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<BaseProduct>(baseProduct,msgId,true);
	}
	
	/**
	 * 完善产品组，保存白膜组合产品
	 * 
	 * @param isBmGroup save白膜组合类型：1，save结构组合类型：2
	 * @param groupType 组合类型 0:普通组合;1:一件装修组合
	 * @return
	 */
	@RequestMapping(value = "/updateBmGroupLocation")
	@ResponseBody
	public Object updateBmGroupLocation(String groupCode, String config, Integer isBmGroup, String msgId,
			HttpServletRequest request, Integer groupType, String designCode) {
		if (groupType.intValue() != GroupProductTypeConstant.TYPE_COMMON
				&& groupType.intValue() != GroupProductTypeConstant.TYPE_INTELLIGENCE) {
			return new ResponseEnvelope<BaseProduct>(false, "参数groupType不正确!现只支持0(普通组合)或1(一建装修组合)", msgId);
		}
		try {
			LoginUser loginUser = new LoginUser();
			if (request.getSession() == null || request.getSession().getAttribute("loginUser") == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
			}
			// groupType处理 ->end
			DesignTemplet designTemplet = designTempletService.selectByTemplateCode(designCode);
			if(designTemplet == null) {
				return new ResponseEnvelope<>(false, "没有找到指定样板房,code:"+designCode, msgId);
			}
			SpaceCommon spaceCommon = spaceCommonService.get(designTemplet.getSpaceCommonId());
			Integer spaceCommonType = spaceCommon.getSpaceFunctionId();
			if (isBmGroup == null) {
				return new ResponseEnvelope<BaseProduct>(false, "是否是白模组合参数isBmGroup不能为空!", msgId);
			}
			if (StringUtils.isBlank(groupCode)) {
				return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
			}
			if (StringUtils.isBlank(config)) {
				return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
			}
			JSONObject json = JSONObject.fromObject(config);
			JSONArray jsonArray;
			if (isBmGroup != 2) {
				jsonArray = JSONArray.fromObject(json.get("productList"));
			} else {
				jsonArray = JSONArray.fromObject(json.get("ProductStructList"));
			}

			GroupProduct groupProduct = new GroupProduct();
			groupProduct.setGroupType(groupType);
			groupProduct.setGroupCode(groupCode);
			List<GroupProduct> groupProducts = groupProductService.getList(groupProduct);
			if (Lists.isNotEmpty(groupProducts)) {
				groupProduct = groupProducts.get(0);
				GroupProductDetailsSearch groupDetauls = new GroupProductDetailsSearch();// 比较位置配置信息中的产品数量是否和数据库产品组中产品数量相同
				groupDetauls.setGroupId(groupProduct.getId());
				int count = groupProductDetailsService.getCount(groupDetauls);
				if (isBmGroup != 2) {
					if (jsonArray != null && count == jsonArray.size()) {
						// 保存产品位置信息
						groupProductService.update(groupProduct);
					} else {
						return new ResponseEnvelope<BaseProduct>(false, "位置信息中产品与数据库不匹配!", msgId);
					}
				}
				return new ResponseEnvelope<BaseProduct>(false, "编码已存在!", msgId);
			} else {
				/** 保存白模组合产品 **/
				if (isBmGroup == 1) {
					Integer fileId = locationTxt(config, loginUser, groupProduct);
					/* 讲文件ID保存至组合位置字段 */
					groupProduct.setLocation(fileId + "");
					groupProduct.setType(spaceCommonType);
					groupProduct.setGroupName(groupCode);
					groupProduct.setStart(0);
					groupProduct.setSorting(0);
					groupProduct.setPicId(0);
					groupProduct.setDesignTempletId(designTemplet.getId());
					/* 保存组合产品 */
					sysSave(groupProduct, request);
					int groupId = groupProductService.add(groupProduct);
					if (groupId < 1) {
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					/* 回填 */
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);
					/* 保存组合明细表信息 */
					BaseProduct baseProduct = null;
					GroupProductDetails gpd = null;
					String planGroupId = groupId + "_"+Utils.getCurrentDateTime(Utils.DATETIMESSS);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject object = (JSONObject) jsonArray.get(i);
						baseProduct = new BaseProduct();
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);

						/* 第一条数据默认为主产品 */
						if (i == 0) {
							gpd.setIsMain(1);
						}
						String productCode = object.getString("productName");
						baseProduct.setProductCode(productCode);
						baseProduct.setIsDeleted(0);
						// gpd.setPosIndexPath(dpp.getPosIndexPath());
						List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
						if (baseProducts != null && baseProducts.size() >= 1) {
							baseProduct = baseProducts.get(0);
							if (baseProduct != null) {
								gpd.setProductId(baseProduct.getId());
							}
						} else {
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!,产品没找到,code:" + productCode,
									msgId);
						}
						// 设置产品视角和远景视角->start
						setCameraInfo(gpd, object, null);
						// 设置产品视角和远景视角->end
						sysSave(gpd, request);
						int gpdId = groupProductDetailsService.add(gpd);
						if (gpdId < 1) {
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
						}
						String posName = object.getString("posName");
						DesignTempletProduct designTempletProduct = new DesignTempletProduct();
						designTempletProduct.setPosName(posName);
						designTempletProduct.setDesignTempletId(designTemplet.getId());
						designTempletProduct.setIsMainProduct(gpd.getIsMain());
						designTempletProduct.setPlanGroupId(planGroupId);
						designTempletProduct.setProductGroupId(groupId);
						designTempletProduct.setGroupType(0);
						int resultId = designTempletProductService.updateDesignTemplateProduct(designTempletProduct);
						if (resultId < 1) {
							return new ResponseEnvelope<BaseProduct>(false, "更新方案样板房产品数据失败!", msgId);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
		}
		return new ResponseEnvelope<BaseProduct>(true, msgId, true);
	}
		
	
	
	/**
	 * 完善产品组，更新产品组合中的产品位置信息
	 * 
	 * @param isBmGroup save白膜组合类型：1，save结构组合类型：2
	 * @param groupType 组合类型 0:普通组合;1:一件装修组合
	 * @return
	 */
	@RequestMapping(value = "/updateProductGroupLocation")
	@ResponseBody
	public Object updateProductGroupLocation(String groupCode,Integer designPlanId,Integer spaceCommonType,
			String config,Integer isBmGroup,String msgId,HttpServletRequest request,
			String structureCode,String designPlanCode,
			String spaceCommonTypeCode,
			String designTempletCode,
			Integer groupType
			){
		
		// groupType处理 ->start
		if(groupType == null){
			groupType = 0;
		}else{
			if(groupType.intValue() != GroupProductTypeConstant.TYPE_COMMON && groupType.intValue() != GroupProductTypeConstant.TYPE_INTELLIGENCE){
				return new ResponseEnvelope<BaseProduct>(false, "参数groupType不正确!现只支持0(普通组合)或1(一建装修组合)", msgId);
			}
		}
		// groupType处理 ->end
		
		try {
			 LoginUser loginUser = new LoginUser();
			if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
				loginUser.setLoginName("nologin");
			}else{
			    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}
			if( isBmGroup == null ){
				return new ResponseEnvelope<BaseProduct>(false, "是否是白模组合参数isBmGroup不能为空!", msgId);
			}
			List<SysDictionary>sysList=null;
			Integer spacValue=null;
			if(isBmGroup==2){
				/*判断空间类型是否存在*/
				SysDictionary sysDictionary= new SysDictionary();
//				sysDictionary.setType("spaceType");
				//sysDictionary.setType("productTypeValue");
				sysDictionary.setType("houseType");
				sysDictionary.setValuekey(spaceCommonTypeCode);
				sysList=sysDictionaryService.getList(sysDictionary);
				if(sysList==null||sysList.size()<=0){
					if( spaceCommonType == null ){
						return new ResponseEnvelope<BaseProduct>(false, "参数spaceCommonType不存在!", msgId);
					}else{
						spacValue=spaceCommonType;
					}
				}else{
					spacValue=sysList.get(0).getValue();
				}
				if (StringUtils.isBlank(groupCode)) {
					return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
				}
				if (StringUtils.isBlank(config)) {
					return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
				}
				if(StringUtils.isBlank(structureCode)){
					return new ResponseEnvelope<BaseProduct>(false, "structureCode不能为空!", msgId);
				}
				if(StringUtils.isBlank(designTempletCode)){
					return new ResponseEnvelope<BaseProduct>(false, "designTempletCode不能为空!", msgId);
				}
			}else{
				if (StringUtils.isBlank(groupCode)) {
					return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
				}
				if (StringUtils.isBlank(config)) {
					return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
				}
				if( spaceCommonType == null ){
					return new ResponseEnvelope<BaseProduct>(false, "参数spaceCommonType不能为空!", msgId);
				}
			} 
			
			if(Utils.hasEmptyOrSpecialCharacter(groupCode)) {
	          return new ResponseEnvelope<>(false, "请检查组合编码中是否包含空格！", msgId);
	        }
	        //去除编码中的空格,换行符之类特殊字符
	        structureCode = Utils.replaceSpecialCharacter(groupCode);
	        
			/*GroupProductSearch groupProductSearch = new GroupProductSearch();
			groupProductSearch.setGroupCode(groupCode);
			Integer num=null;
			num=groupProductService.getCount(groupProductSearch);
			if(num!=null&&num.intValue()>0){
				return new ResponseEnvelope<BaseProduct>(false, "该组合编码:"+groupCode+"已经存在！！", msgId);
			}*/
			DesignPlan designPlan = null;
			if(designPlanId != null){
				designPlan = designPlanService.get(designPlanId);
			}
			
			JSONObject json = JSONObject.fromObject(config);
			JSONArray jsonArray;
			if(isBmGroup!=2){
				 jsonArray = JSONArray.fromObject(json.get("productList"));
			}else{
				 jsonArray = JSONArray.fromObject(json.get("ProductStructList"));
			}
 
			GroupProduct groupProduct = new GroupProduct();
			groupProduct.setGroupType(groupType);
			groupProduct.setGroupCode(groupCode);
			List<GroupProduct> groupProducts = groupProductService.getList(groupProduct);
			if(Lists.isNotEmpty(groupProducts)){
				groupProduct = groupProducts.get(0);
				GroupProductDetailsSearch groupDetauls = new GroupProductDetailsSearch();//比较位置配置信息中的产品数量是否和数据库产品组中产品数量相同
				groupDetauls.setGroupId(groupProduct.getId());
				int count = groupProductDetailsService.getCount(groupDetauls);
				if(isBmGroup!=2){
					if (jsonArray != null && count == jsonArray.size()){
						//保存产品位置信息 
						// groupProduct没有做任何修改就update,这是什么操作??
						groupProductService.update(groupProduct);
					}else{
						return new ResponseEnvelope<BaseProduct>(false, "位置信息中产品与数据库不匹配!", msgId);
					}
				}
				return new ResponseEnvelope<BaseProduct>(false, "编码已存在!", msgId);
			}else{
				/**保存白模组合产品**/
				if(isBmGroup == 1){
					if( designPlanId != null ){
						if( designPlan != null ){
							groupProduct.setDesignTempletId(designPlan.getDesignTemplateId());
						}else
							return new ResponseEnvelope<BaseProduct>(false, "找不到该设计方案Id："+designPlanId+"!", msgId);
					}else{
						return new ResponseEnvelope<BaseProduct>(false, "参数designPlanId不能为空!", msgId);
					}
					Integer fileId=locationTxt(config,loginUser,groupProduct);
			        /*讲文件ID保存至组合位置字段*/
					groupProduct.setLocation(fileId+"");
					groupProduct.setType(spaceCommonType);
					groupProduct.setGroupName(groupCode);
					groupProduct.setStart(0);
					groupProduct.setSorting(0);
					groupProduct.setPicId(0);
					/*保存组合产品*/
					sysSave(groupProduct,request);
					int groupId = groupProductService.add(groupProduct);
					if( groupId < 1 ){
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					/*回填*/
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);
					/*保存组合明细表信息*/
					BaseProduct baseProduct = null;
					GroupProductDetails gpd = null;
					for(int i=0;i<jsonArray.size();i++){
						JSONObject object = (JSONObject)jsonArray.get(i);
						baseProduct = new BaseProduct();
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);
						 
						/*第一条数据默认为主产品*/
						if( i==0 ){
							gpd.setIsMain(1);
						}
						Integer planProductId = (Integer)object.get("planProductId");
						DesignPlanProduct dpp = new DesignPlanProduct();
						if( planProductId != null ){
							dpp = designPlanProductService.get(planProductId);
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "位置信息中planProductId属性为空!", msgId);
						}
						if( dpp != null ){
							gpd.setAtt1(dpp==null?"":dpp.getPlanProductId()+"");
							//gpd.setAtt1(dpp==null?"":dpp.getId()+"");
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "找不到该设计方案产品ID:"+planProductId+"!", msgId);
						}
						String productCode = object.getString("productName");
						baseProduct.setProductCode(productCode);
						baseProduct.setIsDeleted(0);
						gpd.setPosIndexPath(dpp.getPosIndexPath());
						List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
						if( baseProducts!=null && baseProducts.size() >= 1 ){
							baseProduct = baseProducts.get(0);
							if( baseProduct != null ){
								gpd.setProductId(baseProduct.getId());
							}
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!,产品没找到,code:" + productCode, msgId);
						}
						// 设置产品视角和远景视角->start
						setCameraInfo(gpd, object, null);
						// 设置产品视角和远景视角->end
						sysSave(gpd,request);
						int gpdId = groupProductDetailsService.add(gpd);
						if( gpdId < 1 ){
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
						}
					}
				/**保存结构组合**/
				}else if(isBmGroup == 2 ){
					
					//sgroupProductService.addGroupStructure(designTempletCode,structureCode,jsonArray,msgId);
					/*List<DesignPlan>designPlanList=null;
					DesignPlan designPlan = new DesignPlan();
					designPlan.setPlanCode(designPlanCode);
					designPlanList=designPlanService.getList(designPlan);
					if(designPlanList==null||designPlanList.size()<=0){
						return new ResponseEnvelope<BaseProduct>(false, "该设计方案："+designPlanCode+"不存在!", msgId);
					}*/
					List<DesignTemplet>designList=null;
					DesignTemplet designTemplet = new DesignTemplet();
					designTemplet.setDesignCode(designTempletCode);
					designTemplet.setIsDeleted(0);
					designList=designTempletService.getList(designTemplet);
					if(designList==null||designList.size()<=0){
						return new ResponseEnvelope<BaseProduct>(false, "该样板房编码："+designTempletCode+"不存在!", msgId);
					}
					
					List<StructureProduct>structureList=null;
					StructureProduct structureProduct = new StructureProduct();
					structureProduct.setStructureCode(structureCode);
					structureProduct.setIsDeleted(0);
					structureList=structureProductService.getList(structureProduct);
					if(structureList==null||structureList.size()<=0){
						return new ResponseEnvelope<BaseProduct>(false, "该结构编码："+structureCode+"不存在!", msgId);
					}
					StructureProduct structureProduct_=null;
					String groupFlag=null;
					structureProduct_=structureProductService.get(structureList.get(0).getId());
					if(structureProduct_!=null){
						groupFlag=structureProduct_.getGroupFlag();
						if(groupFlag!=null){
							groupProduct.setGroupFlag(groupFlag);
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "该结构编码："+structureCode+"不存在结构标记groupFlag!", msgId);
						}
					}
					groupProduct.setStructureId(structureList.get(0).getId());
					groupProduct.setDesignTempletId(designList.get(0).getId());
					Integer fileId=locationTxt(config,loginUser,groupProduct);
			        /*讲文件ID保存至组合位置字段*/
					groupProduct.setLocation(fileId+"");
					groupProduct.setType(spacValue);
					groupProduct.setGroupName(groupCode);
					/* 保存组合产品*/
					sysSave(groupProduct,request);
					int groupId = groupProductService.add(groupProduct);
					if( groupId < 1 ){
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					/*回填*/
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);
					/*保存组合明细表信息*/
					BaseProduct baseProduct = null;
					
					// 取得所有的产品code(huangsongbo)->start
					/*List<String> productCodeList = new ArrayList<String>();
					for(int i=0;i<jsonArray.size();i++){
						JSONObject jsonObject = (JSONObject)jsonArray.get(i);
						if(jsonObject.containsKey("ProductCode")){
							String productCode = jsonObject.getString("ProductCode");
							if(StringUtils.isNotBlank(productCode))
								productCodeList.add(productCode);
						}
					}*/
					// 取得所有的产品code(huangsongbo)->end
					// 获取modeCode:productCode的map(为了自动匹配cameraLook和cameraView属性)(huangsongbo)
					Map<String, StructureProductDetails> map = structureProductDetailsService.getStructureProductDetailsInfoMap(structureProduct_.getId());
					GroupProductDetails gpd = null;
					for(int i=0;i<jsonArray.size();i++){
						JSONObject object = (JSONObject)jsonArray.get(i);
						baseProduct = new BaseProduct();
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);
						/*第一条数据默认为主产品*/
						if( i==0 ){
							gpd.setIsMain(1);
						}
						String productCode = object.getString("ProductCode");
						baseProduct.setProductCode(productCode);
						baseProduct.setIsDeleted(0);
						List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
						if( baseProducts!=null && baseProducts.size() >= 1 ){
							baseProduct = baseProducts.get(0);
							if( baseProduct != null ){
								gpd.setProductId(baseProduct.getId());
							}
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!,产品没找到,code:" + productCode, msgId);
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
								baseProductlist=baseProductService.getList(baseProduct_);
								if(baseProductlist!=null&&baseProductlist.size()>0){
									gpd.setChartletProductModelId(baseProductlist.get(0).getId());
								}else{
									return new ResponseEnvelope<BaseProduct>(false, "模型编码："+ModelCode+"  不存在，或者已删除!，保存组合明细数据失败!", msgId);
								}
							}else{
								return new ResponseEnvelope<BaseProduct>(false, "模型编码："+ModelCode+"  不存在，或者已删除!，保存组合明细数据失败!", msgId);
							}
						}
						// 设置产品视角和远景视角->start
						setCameraInfo(gpd, object, (map.containsKey(ModelCode))?map.get(ModelCode):null);
						// 设置产品视角和远景视角->end
						sysSave(gpd,request);
						int gpdId = groupProductDetailsService.add(gpd);
						if( gpdId < 1 ){
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
						}
					}
				/**保存普通组合**/
				}else{
					Integer fileId=locationTxt(config,loginUser,groupProduct);
			        /*讲文件ID保存至组合位置字段*/
					groupProduct.setLocation(fileId+"");
					groupProduct.setType(spaceCommonType);
					groupProduct.setGroupName(groupCode);
					/* 保存组合产品*/
					sysSave(groupProduct,request);
					int groupId = groupProductService.add(groupProduct);
					if( groupId < 1 ){
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					/*回填*/
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);
					/*保存组合明细表信息*/
					BaseProduct baseProduct = null;
					GroupProductDetails gpd = null;
					String planGroupId = groupId+"_"+System.currentTimeMillis();
					for(int i=0;i<jsonArray.size();i++){
						// eg:{"productName":"kangl_bcca_0001","planProductId":8309929,"localPosX":0,"localPosY":-0.36601305,"localPosZ":-0.002499938,"localRotX":9.334668E-6,"localRotY":0,"localRotZ":0}
						JSONObject object = (JSONObject)jsonArray.get(i);
						baseProduct = new BaseProduct();
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);
						/*第一条数据默认为主产品*/
						if( i==0 ){
							gpd.setIsMain(1);
						}
						String productCode = object.getString("productName");
						baseProduct.setProductCode(productCode);
						baseProduct.setIsDeleted(0);
						List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
						if( baseProducts!=null && baseProducts.size()>=1 ){
							baseProduct = baseProducts.get(0);
							if( baseProduct != null ){
								gpd.setProductId(baseProduct.getId());
							}
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!,产品没找到,code:" + productCode, msgId);
						}
						// 设置产品视角和远景视角->start
						setCameraInfo(gpd, object, null);
						// 设置产品视角和远景视角->end
						
						// 组合明细中设置当前选择的多材质信息 ->start
						this.setGroupSplitTexturesChooseInfo(gpd, (Integer)object.get("planProductId"));
						// 组合明细中设置当前选择的多材质信息 ->end
						
						sysSave(gpd,request);
						int gpdId = groupProductDetailsService.add(gpd);
						if( gpdId < 1 ){
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
						}else{
							//如果是一件装修组合,则把该组合更新该方案组合
							if(groupType.intValue() == GroupProductTypeConstant.TYPE_INTELLIGENCE.intValue()){
								Integer planProductId = (Integer)object.get("planProductId");
								DesignPlanProduct designPlanProduct = new DesignPlanProduct();
								designPlanProduct.setId(planProductId);
								designPlanProduct.setProductGroupId(groupId);   //组合id
								designPlanProduct.setPlanGroupId(planGroupId);  //时间搓
								designPlanProduct.setIsMainProduct(gpd.getIsMain());
								designPlanProductService.update(designPlanProduct);
							}
						}
					}
				}
			}
			
			// 如果是一件装修组合,标记该设计方案为一建装修方案
			if(designPlan != null){
				if(groupType.intValue() == GroupProductTypeConstant.TYPE_INTELLIGENCE.intValue()){
					DesignPlan newDesignPlan = new DesignPlan();
					newDesignPlan.setId(designPlan.getId());
					newDesignPlan.setPlanType(DesignPlanTypeConstant.TYPE_INTELLIGENCE);
					designPlanService.update(newDesignPlan);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
		}
		return new ResponseEnvelope<BaseProduct>(true, msgId, true);
	}

	/**
	 * 设置组合明细多材质信息(保存新建组合的时候选择的多材质信息)
	 * 
	 * @author huangsongbo
	 * @param gpd
	 * @param object
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
		}
		// 参数验证 ->end
		
		gpd.setSplitTexturesChooseInfo(designPlanProduct.getSplitTexturesChooseInfo());
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
 * 通过编辑器定义3中组合
 * @name  updateEditorProductGroupLocation
 * @param groupCode
 * @param config
 * @param msgId
 * @param request
 * @param structureCode
 * @param designPlanCode
 * @param spaceCommonTypeCode
 * @param designTempletCode
 * @return
 */
	@RequestMapping(value = "/updateEditorProductGroupLocation")
	@ResponseBody
	public Object updateEditorProductGroupLocation(String groupCode,String config,String msgId,HttpServletRequest request,
			String structureCode,String designPlanCode,String spaceCommonTypeCode,String designTempletCode){
		try{
			/*条件验证*/
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			/*结构组合判断条件*/
			if(structureCode!=null&&!"".equals(structureCode)){
				if(spaceCommonTypeCode==null||"".equals(spaceCommonTypeCode)){
					return new ResponseEnvelope<BaseProduct>(false, "参数spaceCommonType不存在!", msgId);
				}
				if (StringUtils.isBlank(groupCode)) {
					return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
				}
				if (StringUtils.isBlank(config)) {
					return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
				}
				if(StringUtils.isBlank(structureCode)){
					return new ResponseEnvelope<BaseProduct>(false, "structureCode不能为空!", msgId);
				}
				if(StringUtils.isBlank(designTempletCode)){
					return new ResponseEnvelope<BaseProduct>(false, "designTempletCode不能为空!", msgId);
				}
			}
			/*普通组合和白膜判断条件*/
			if(structureCode==null||"".equals(structureCode)){
				if (StringUtils.isBlank(groupCode)) {
					return new ResponseEnvelope<BaseProduct>(false, "产品组groupCode不能为空!", msgId);
				}
				if (StringUtils.isBlank(config)) {
					return new ResponseEnvelope<BaseProduct>(false, "位置关系参数不能为空!", msgId);
				}
			}
			/*if(Utils.hasEmptyOrSpecialCharacter(groupCode)) {
			  return new ResponseEnvelope<BaseProduct>(false, "请检查结构编码中是否包含空格!", msgId);
			}*/
			//去除结构编码中的空格和换行符之类特殊符号
			groupCode = Utils.replaceSpecialCharacter(groupCode);
			
			/*获取空间类型，用作查询组合条件，在sql里是一个 foreach  begin*/
			List<SysDictionary>sysList=null;
			Integer spacValue=null;
			if(structureCode==null||"".equals(structureCode)){
				SysDictionary sysDictionary= new SysDictionary();
				//sysDictionary.setType("productTypeValue");
				sysDictionary.setType("houseType");
				sysDictionary.setValuekey(spaceCommonTypeCode);
				sysList=sysDictionaryService.getList(sysDictionary);
				if(sysList==null||sysList.size()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "参数spaceCommonType不存在!", msgId);
				}
				if(sysList!=null&&sysList.size()>0){
					spacValue=sysList.get(0).getValue();
				}
			}
			/*获取空间类型，用作查询组合条件，在sql里是一个 foreach  end*/
			
			
			
			
			JSONObject json = JSONObject.fromObject(config);
			JSONArray jsonArray;
			jsonArray = JSONArray.fromObject(json.get("productList"));


			
			GroupProduct groupProduct = new GroupProduct();
			groupProduct.setGroupCode(groupCode);
			List<GroupProduct> groupProducts = groupProductService.getList(groupProduct);
			if(Lists.isNotEmpty(groupProducts)){
				groupProduct = groupProducts.get(0);
				GroupProductDetailsSearch groupDetauls = new GroupProductDetailsSearch();/*比较位置配置信息中的产品数量是否和数据库产品组中产品数量相同*/
				groupDetauls.setGroupId(groupProduct.getId());
				int count = groupProductDetailsService.getCount(groupDetauls);
				if(structureCode==null||"".equals(structureCode)){
					if (jsonArray != null && count == jsonArray.size()){
						groupProductService.update(groupProduct);/*保存产品位置信息*/
					}
					if (jsonArray == null || count != jsonArray.size()){
						return new ResponseEnvelope<BaseProduct>(false, "位置信息中产品与数据库不匹配!", msgId);
					}
				}
			}
			/**保存白模组合产品**/
			if(!Lists.isNotEmpty(groupProducts)){
				if((structureCode==null||"".equals(structureCode))&&(designTempletCode!=null&&!"".equals(designTempletCode))){
					
					/*查询样板房是否存在*/
					List<DesignTemplet>templetList=null;
					DesignTemplet designTemplet = new DesignTemplet();
					designTemplet.setDesignCode(designTempletCode);
					/*designTemplet.setPutawayState(1);*/
					designTemplet.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());
					designTemplet.setIsDeleted(0);
					templetList=designTempletService.getList(designTemplet);
					if(templetList==null||templetList.size()!=1){
						return new ResponseEnvelope<BaseProduct>(false, "样板房编码"+designTempletCode+"不存在或者未上架", msgId);
					}
					designTemplet=templetList.get(0);
 
					Integer fileId=locationTxt(config,loginUser,groupProduct);/*讲文件ID保存至组合位置字段*/
					groupProduct.setDesignTempletId(designTemplet.getId());
					groupProduct.setLocation(fileId+"");
					groupProduct.setType(spacValue);
					groupProduct.setGroupName(groupCode);
					groupProduct.setStart(0);
					groupProduct.setSorting(0);
					groupProduct.setPicId(0);
					sysSave(groupProduct,request);/*保存组合产品*/
					int groupId = groupProductService.add(groupProduct);
					if( groupId < 1 ){
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);
					/*保存组合明细表信息*/
					for(int i=0;i<jsonArray.size();i++){
						BaseProduct baseProduct = null;
						GroupProductDetails gpd = null;
						List<DesignTempletProduct>templetProductlist=null;
						List<BaseProduct> baseProducts=null;
						DesignTempletProduct dtp = null;
						
						JSONObject object = (JSONObject)jsonArray.get(i);
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);
						if( i==0 ){					/*第一条数据默认为主产品*/
							gpd.setIsMain(1);
						}
						/*
						 * 优先通过产品编码和设计方案编码， 查询设计方案产品id
						 * 如果改变吗在设计方案产品表中存在多个，增加posName为查询条件
						 */
						/*查询产品是否存在*/
						String productCode =(String) object.getString("productName");
						String posName=	(String)object.get("posName");
						baseProduct=new BaseProduct();
						baseProduct.setProductCode(productCode);
						baseProduct.setIsDeleted(0);
						baseProducts = baseProductService.getList(baseProduct);
						if( baseProducts==null || baseProducts.size()<=0 ){
							return new ResponseEnvelope<BaseProduct>(false, "产品编码"+productCode+"不存在", msgId);
						}
						if( baseProducts!=null && baseProducts.size()==1 ){
							baseProduct=baseProducts.get(0);
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "产品编码"+productCode+"在baseProduct 出现多个", msgId);
						}
						/*查询设计方案产品id是否存在*/
						if( baseProduct != null ){
							dtp=new DesignTempletProduct();
							dtp.setProductId(baseProduct.getId());
							dtp.setDesignTempletId(designTemplet.getId());
							dtp.setIsDeleted(0);
							templetProductlist = designTempletProductService.getList(dtp);
						}
						if(templetProductlist==null||templetProductlist.size()<=0){
							return new ResponseEnvelope<BaseProduct>(false, "样板房产品编码"+productCode+"不存在", msgId);
						}
						/*是否存在多个*/
						if(templetProductlist!=null&&templetProductlist.size()>1){
							if(posName == null ||"".equals(posName)){
								return new ResponseEnvelope<BaseProduct>(false, "位置信息中posName属性不能为空!", msgId);
							}
							dtp.setPosName(posName);
							templetProductlist = designTempletProductService.getList(dtp);
						}
						if(templetProductlist==null||templetProductlist.size()<=0){
							return new ResponseEnvelope<BaseProduct>(false, "产品编码"+productCode+"不存在", msgId);
						}
						if(templetProductlist!=null&&templetProductlist.size()<1){
							return new ResponseEnvelope<BaseProduct>(false, "产品编码"+productCode+"位置错误", msgId);
						}
						if(templetProductlist!=null&&templetProductlist.size()==1){
							gpd.setAtt1(templetProductlist.get(0).getId()+"");
							gpd.setProductId(baseProduct.getId());
							// 设置产品视角和远景视角->start
							setCameraInfo(gpd, object, null);
							// 设置产品视角和远景视角->end
							sysSave(gpd,request);
							int gpdId = groupProductDetailsService.add(gpd);
							if( gpdId < 1 ){
								return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
							}
						}
					}
				}
				
				/**保存普通组合产品**/
				if((structureCode==null||"".equals(structureCode))&&(designTempletCode==null||"".equals(designTempletCode))){
					Integer fileId=locationTxt(config,loginUser,groupProduct); /*讲文件ID保存至组合位置字段*/
					groupProduct.setLocation(fileId+"");
					groupProduct.setType(spacValue);
					if( spacValue.intValue() == 10 ){ //如果是客卧房就存卧室value
						groupProduct.setSpaceFunctionValue(4);
					}else{
						groupProduct.setSpaceFunctionValue(spacValue);
					}
					groupProduct.setGroupName(groupCode);
					/* 保存组合产品*/
					sysSave(groupProduct,request);
					int groupId = groupProductService.add(groupProduct);
					if( groupId < 1 ){
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);/*保存组合明细表信息*/
					BaseProduct baseProduct = null;
					GroupProductDetails gpd = null;
					for(int i=0;i<jsonArray.size();i++){
						JSONObject object = (JSONObject)jsonArray.get(i);
						baseProduct = new BaseProduct();
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);
						if( i==0 ){/*第一条数据默认为主产品*/
							gpd.setIsMain(1);
						}
						String productCode = object.getString("productName");
						if(productCode!=null&&"".equals(productCode)){
							baseProduct.setProductCode(productCode);
							baseProduct.setIsDeleted(0);
							/*baseProduct.setPutawayState(1);*/
							baseProduct.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());
							List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
							if( baseProducts!=null && baseProducts.size()==1 ){
								baseProduct = baseProducts.get(0);
								if( baseProduct != null ){
									gpd.setProductId(baseProduct.getId());
								}
								if( baseProduct == null ){
									return new ResponseEnvelope<BaseProduct>(false, productCode+"产品编码不存在或者未上架", msgId);
								}
								// 设置产品视角和远景视角->start
								setCameraInfo(gpd, object, null);
								// 设置产品视角和远景视角->end
								sysSave(gpd,request);
								int gpdId = groupProductDetailsService.add(gpd);
								if( gpdId < 1 ){
									return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
								}
							}
							if( baseProducts!=null && baseProducts.size()!=1 ){
								return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
							}
							if( baseProducts==null || baseProducts.size()<=0 ){
								return new ResponseEnvelope<BaseProduct>(false, productCode+"产品编码不存在或者未上架", msgId);
							}
							
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
						}

					}
				}
				
				/***保存结构组合***/
				if((structureCode!=null&&!"".equals(structureCode))&&(designTempletCode!=null&&!"".equals(designTempletCode))){
					List<DesignTemplet>designList=null;
					DesignTemplet designTemplet = new DesignTemplet();
					designTemplet.setDesignCode(designTempletCode);
					designTemplet.setIsDeleted(0);
					designList=designTempletService.getList(designTemplet);
					if(designList==null||designList.size()<=0){
						return new ResponseEnvelope<BaseProduct>(false, "该样板房编码："+designTempletCode+"不存在!", msgId);
					}
					
					List<StructureProduct>structureList=null;
					StructureProduct structureProduct = new StructureProduct();
					structureProduct.setStructureCode(structureCode);
					structureProduct.setIsDeleted(0);
					structureList=structureProductService.getList(structureProduct);
					if(structureList==null||structureList.size()<=0){
						return new ResponseEnvelope<BaseProduct>(false, "该结构编码："+structureCode+"不存在!", msgId);
					}
					StructureProduct structureProduct_=null;
					String groupFlag=null;
					structureProduct_=structureProductService.get(structureList.get(0).getId());
					if(structureProduct_!=null){
						groupFlag=structureProduct_.getGroupFlag();
						if(groupFlag!=null){
							groupProduct.setGroupFlag(groupFlag);
						}else{
							return new ResponseEnvelope<BaseProduct>(false, "该结构编码："+structureCode+"不存在结构标记groupFlag!", msgId);
						}
					}
					groupProduct.setStructureId(structureList.get(0).getId());
					groupProduct.setDesignTempletId(designList.get(0).getId());
					Integer fileId=locationTxt(config,loginUser,groupProduct);
			        /*讲文件ID保存至组合位置字段*/
					groupProduct.setLocation(fileId+"");
					groupProduct.setType(spacValue);
					groupProduct.setGroupName(groupCode);
					/* 保存组合产品*/
					sysSave(groupProduct,request);
					int groupId = groupProductService.add(groupProduct);
					if( groupId < 1 ){
						return new ResponseEnvelope<BaseProduct>(false, "保存组合数据失败!", msgId);
					}
					ResFile resFile = new ResFile();
					resFile.setBusinessId(groupId);
					resFile.setId(fileId);
					resFileService.update(resFile);
					/*保存组合明细表信息*/
					BaseProduct baseProduct = null;
					GroupProductDetails gpd = null;
					Map<String, StructureProductDetails> map = structureProductDetailsService.getStructureProductDetailsInfoMap(structureProduct_.getId());
					for(int i=0;i<jsonArray.size();i++){
						JSONObject object = (JSONObject)jsonArray.get(i);
						baseProduct = new BaseProduct();
						gpd = new GroupProductDetails();
						gpd.setGroupId(groupId);
						gpd.setIsMain(0);
						if( i==0 ){/*第一条数据默认为主产品*/
							gpd.setIsMain(1);
						}
						String productCode = object.getString("ProductCode");
						baseProduct.setProductCode(productCode);
						List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
						if( baseProducts!=null && baseProducts.size()==1 ){
							baseProduct = baseProducts.get(0);
							if( baseProduct != null ){
								gpd.setProductId(baseProduct.getId());
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
								baseProductlist=baseProductService.getList(baseProduct_);
								if(baseProductlist!=null&&baseProductlist.size()>0){
									gpd.setChartletProductModelId(baseProductlist.get(0).getId());
								}else{
									return new ResponseEnvelope<BaseProduct>(false, "模型编码："+ModelCode+"  不存在，或者已删除!，保存组合明细数据失败!", msgId);
								}
							}else{
								return new ResponseEnvelope<BaseProduct>(false, "模型编码："+ModelCode+"  不存在，或者已删除!，保存组合明细数据失败!", msgId);
							}
						}
						// 设置产品视角和远景视角->start
						setCameraInfo(gpd, object, (map.containsKey(ModelCode))?map.get(ModelCode):null);
						// 设置产品视角和远景视角->end
						sysSave(gpd,request);
						int gpdId = groupProductDetailsService.add(gpd);
						if( gpdId < 1 ){
							return new ResponseEnvelope<BaseProduct>(false, "保存组合明细数据失败!", msgId);
						}
					}
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
		}
		return new ResponseEnvelope<BaseProduct>(true, msgId, true);
	}
	
	
  
	
	/**
	 * 将String 类型 location转为txt文本
	 * 传进来一个String  返回一个文本id
	 * @return
	 * @throws IOException 
	 */
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
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(GroupProduct model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
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
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
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
	private void sysSave(DesignTempletProduct model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
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
	 * 同类型产品列表接口
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findSameTypeProductList")
	@ResponseBody
	public Object findSameTypeProductList(Integer productId, Integer designPlanId, Integer planProductId, Integer spaceCommonId,
										  String msgId, @RequestParam(value = "modelType",required=false) String modelType, HttpServletRequest request, HttpServletResponse response) {
		
		if( StringUtils.isBlank(msgId) ){
			return new ResponseEnvelope<BaseProduct>(false, "msgId为空!", msgId);
		}
		if( productId == null ){
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少产品productId!", msgId);
		}
		LoginUser loginUser = new LoginUser();
        if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
            loginUser.setId(-1);
            loginUser.setLoginName("nologin");
        } else {
            loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
        }
        int total = 0;
        List<BaseProduct> list = new ArrayList<>();
		//缓存开关
		BaseProduct bp = null;
		Integer id = productId;
		/*取id为productId的产品*/
		BaseProduct baseProductNew=null;
		if( Utils.enableRedisCache() ){
			baseProductNew = BaseProductCacher.get(productId);
		}else{
			baseProductNew = baseProductService.get(productId);	
		}
		if(baseProductNew==null)
			return new ResponseEnvelope<>(false, "产品未找到[id:"+productId+"]", msgId);
		/*取id为productId的产品->end*/
		DesignPlanProduct planProduct = designPlanProductService.get(planProductId);
		if(planProduct!=null && planProduct.getModelProductId()!=null && planProduct.getModelProductId()>0){
			id = planProduct.getModelProductId();
		}
		if( Utils.enableRedisCache() ){
			bp = BaseProductCacher.get(id);
		}else{
			bp = baseProductService.get(id);	
		}	
		if( bp == null ){
			return new ResponseEnvelope<BaseProduct>(total, list, msgId);
		}
		/*返回可选材质信息*/
		Integer isSplit=new Integer(0);
		List<SplitTextureDTO> splitTexturesInfo=new ArrayList<SplitTextureDTO>();
		if(bp!=null){
			if(StringUtils.isNotBlank(baseProductNew.getSplitTexturesInfo())){
				Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(loginUser,modelType,baseProductNew.getId(), baseProductNew.getSplitTexturesInfo(),"info");
				isSplit=(Integer) map.get("isSplit");
				splitTexturesInfo=(List<SplitTextureDTO>) map.get("splitTexturesInfo");
			}else{
				isSplit=new Integer(0);
				if(baseProductNew.getParentId() != null && !baseProductNew.getParentId().equals(new Integer(0))){
					//媒介类型.如果没有值，则表示为web前端（2）
					//String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
					BaseProduct products = new BaseProduct();
					//products.setParentId(bp.getParentId());
					products.setParentId(baseProductNew.getParentId());
					String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
					if(loginUser.getUserType()==1 && "2".equals(versionType)){
						products.setIsInternalUser("yes");
			        }
					//如果是平吊天花查询同类型新增和贴图产品
					SysDictionary bigSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(bp.getProductTypeValue()));
					SysDictionary smallSysDic = null;
					if( bigSysDic != null ){
						smallSysDic = sysDictionaryService.getSysDictionaryByValue(bigSysDic.getValuekey(), Integer.valueOf(bp.getProductSmallTypeValue()));
					}
					if( smallSysDic != null && "pdtianh".equals(smallSysDic.getValuekey())){
						products.setProductTypeValue(bp.getProductTypeValue());
						products.setProductSmallTypeValue(smallSysDic.getValue());
					}
					products.setOrders(" id="+productId+" DESC");
					total = baseProductService.getSameTypeProductCount(products);
					if(total > 1){
						try{
							list = baseProductService.getSameTypeProductList(products);
							//SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", resTextureDTOList);
							SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
							List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
							for(BaseProduct baseProduct:list){
								String materalIds=baseProduct.getMaterialPicIds();
								List<String> materIdStrList=Utils.getListFromStr(materalIds);
								if(materIdStrList!=null&&materIdStrList.size()>0){
									ResTexture resTexture = null;
									//解决已经被删除的材质也能查出来问题 add by yanghz
									for(String temId : materIdStrList){
										resTexture = resTextureService.get(Integer.valueOf(temId));
										if(resTexture != null){
											break;
										}else{
											continue;
										}
									}
									if(resTexture!=null){
										ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
										resTextureDTO.setKey(splitTextureDTO.getKey());
										resTextureDTO.setProductId(baseProduct.getId());
										resTextureDTOList.add(resTextureDTO);
									}
								}
							}
							splitTextureDTO.setList(resTextureDTOList);
							splitTexturesInfo.add(splitTextureDTO);
						} catch (Exception e) {
							e.printStackTrace();
							return new ResponseEnvelope<BaseProduct>(false, "数据异常!",msgId);
						}
					}else{
						total = 0;
					}
				}
			}
		}
		/*返回可选材质信息->end*/
		FindSameTypeProductListDTO result=new FindSameTypeProductListDTO(isSplit, splitTexturesInfo, null);
		return new ResponseEnvelope<>(result, msgId, true);
	}
	
/*	@RequestMapping("/testUnZip")
	@ResponseBody
	public String testUnZip(String code1,String code2,HttpServletRequest request) throws IOException{
		//////System.out.println(code1);
		byte[] bs=code1.getBytes("UTF-8");
		//////System.out.println("------"+bs.length);
		byte[] bs2=ZipUtils.unZip(bs);
		test
		//////System.out.println("------"+code.length);
		byte[] bs2=ZipUtils.unZip(code);
		test->end
		//////System.out.println("------"+bs2.length);
		String str=new String(bs2,"UTF-8");
		//////System.out.println("------"+str);
		return str;
	}*/
	
	@RequestMapping("/testUnZip")
	@ResponseBody
	public String testUnZip(HttpServletRequest request) throws IOException{
		//BufferedInputStream in = new BufferedInputStream(request.getInputStream());
		//ServletInputStream in = request.getInputStream();
        DataInputStream in = new DataInputStream(request.getInputStream());
        byte[] media = new byte[1024];
        @SuppressWarnings("unused")
		int length = in.read(media, 0, 1024);
		byte[] bs1=ZipUtils.toByteArray(in);
		/*int totalbytes = request.getContentLength();
		byte[] dataOrigin = new byte[totalbytes];
		String str2=request.getContextPath();
		DataInputStream in = new DataInputStream(request.getInputStream());
        in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中
        in.close(); // 关闭数据流
        String reqcontent = new String(dataOrigin);*/
		//byte[] bs1=getRequestPostBytes(request);
		byte[] bytes = ZipUtils.unZip(bs1);
		String str=new String(bytes, "GBK");
		//////System.out.println(str);
		return str;
	}
	
	/*@RequestMapping(value="/testUnZip",produces = "application/json")
	@ResponseBody
	public String testUnZip(@RequestBody byte[] bytes){
		//////System.out.println(bytes);
		return "";
	}*/
	
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();  
        /*当无请求参数时，request.getContentLength()返回-1 */  
        if(contentLength<0){  
            return null;  
        }  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
  
            int readlen = request.getInputStream().read(buffer, i,  
                    contentLength - i);  
            if (readlen == -1) {  
                break;  
            }  
            i += readlen;  
        }  
        return buffer;  
    }
	
	/*public String testUnZip(HttpExchange httpExchange) throws IOException{
		InputStream in = httpExchange.getRequestBody();
		byte[] bs1=ZipUtils.input2byte(in);
		byte[] bytes = ZipUtils.unZip(bs1);
		String str=new String(bytes, "GBK");
		//////System.out.println(str);
		return str;
		
	}*/
	
	@RequestMapping("/testUnZip2")
	@ResponseBody
	public void testUnZip2(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String strZip=(String) request.getAttribute("zip_param");
		String str="heheda";
		byte[] bytes=str.getBytes();
		byte[] returnBytes=ZipUtils.zip(bytes);
		response.getOutputStream().write(returnBytes);
	}

	@RequestMapping("/testDecompressModel")
	@ResponseBody
	public String test(Integer proudctId){
		if(proudctId == null)
			return "proudctId不能为空";
		BaseProduct baseProduct = baseProductService.get(proudctId);
		if(baseProduct == null)
			return "productId为" + proudctId + "的产品找不到";
		Integer modelId = baseProduct.getModelId();
		return "" + resModelService.decompressModel(modelId, baseProduct.getProductCode());
	}
	
	/**
	 * findSameTypeProductList接口返回格式类
	 * @author huangsongbo
	 *
	 */
	public class FindSameTypeProductListDTO{
		private Integer isSplit;
		private List<SplitTextureDTO> splitTexturesInfo;
		private List<BaseProduct> productList;
		
		public FindSameTypeProductListDTO() {
			super();
		}
		
		public FindSameTypeProductListDTO(Integer isSplit, List<SplitTextureDTO> splitTexturesInfo,
				List<BaseProduct> productList) {
			super();
			this.isSplit = isSplit;
			this.splitTexturesInfo = splitTexturesInfo;
			this.productList = productList;
		}
		
		public Integer getIsSplit() {
			return isSplit;
		}
		public void setIsSplit(Integer isSplit) {
			this.isSplit = isSplit;
		}
		public List<SplitTextureDTO> getSplitTexturesInfo() {
			return splitTexturesInfo;
		}
		public void setSplitTexturesInfo(List<SplitTextureDTO> splitTexturesInfo) {
			this.splitTexturesInfo = splitTexturesInfo;
		}
		public List<BaseProduct> getProductList() {
			return productList;
		}
		public void setProductList(List<BaseProduct> productList) {
			this.productList = productList;
		}
		
	}
	
	
	/**
	 * 单品替换组合方法（适用于天花地板 功能）
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/productSelectGroupReplaceV2")
	@ResponseBody
	public ResponseEnvelope productSelectGroupReplaceV2(HttpServletRequest request ,HttpServletResponse response,Integer styleId){
		List<SearchProductGroupDetail>resultList = null;
		Integer userType = null;
		DesignPlan designPlan = null;
		List<Integer>designPlanProductIds = null;
		
		String planProductStructureIds = request.getParameter("planProductStructureIds"); 
		String planId = request.getParameter("planId");  // 序号
		String productId = request.getParameter("productId");   //产品id
		String productIndex = request.getParameter("productIndex");  // 序号
		String spaceCommonId = request.getParameter("spaceCommonId");  // 空间

		String msgId = request.getParameter("msgId");
		if(StringUtils.isBlank(productId)){
			return new ResponseEnvelope<>(false,"缺少参数productId",msgId);
		}
		if(StringUtils.isBlank(productIndex)){
			return new ResponseEnvelope<>(false,"缺少参数productIndex",msgId);
		}
		if(StringUtils.isBlank(msgId)){
			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
		}
		if(StringUtils.isBlank(planProductStructureIds)){
			return new ResponseEnvelope<>(false,"缺少参数planProductStructureIds",msgId);
		}
		if(StringUtils.isBlank(planId)){
			return new ResponseEnvelope<>(false,"缺少参数planId",msgId);
		}
		if(StringUtils.isBlank(spaceCommonId)){
			return new ResponseEnvelope<>(false,"缺少参数spaceCommonId",msgId);
		}
		if( styleId == null ){
			return new ResponseEnvelope<>(false,"缺少参数styleId",msgId);
		}
		designPlan = designPlanService.get(Integer.parseInt(planId));
		if(designPlan == null){
			return new ResponseEnvelope<>(false,"id 为"+planId+"的方案 不存在",msgId);
		}
		BaseProduct baseProduct = baseProductService.get(Integer.parseInt(productId));
		if(baseProduct == null){
			return new ResponseEnvelope<>(false,"id 为"+productId+"的产品 不存在",msgId);
		}
//		String mediaType = SystemCommonUtil.getMediaType(request);
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String mediaType = "3";
		if (loginUser!=null && loginUser.getId() > 0) {
			userType = loginUser.getUserType();
		}else {
			userType = 1;
		}
		String [] planProductStructureIds_ = planProductStructureIds.split(",");
		designPlanProductIds = new ArrayList<Integer>();
		for (String id : planProductStructureIds_) {
			designPlanProductIds.add(Integer.parseInt(id));
		}
		resultList = baseProductService.productSelectGroupReplaceV3( baseProduct,designPlan,designPlanProductIds,Integer.parseInt(productIndex),userType,Integer.parseInt(spaceCommonId), mediaType,styleId);
		if(resultList == null || resultList.size()<= 0){
			return new ResponseEnvelope<>(false,"找不到匹配的产品",msgId);
		}
		return new ResponseEnvelope<SearchProductGroupDetail>(resultList.size(), resultList, msgId);
	}
	
	/**
	 * 定制搜索接口
	 * 专未替换浴缸/面盆,要把水龙头带上的功能定制的搜索(水龙头)接口
	 * 
	 * @author huangsongbo
	 * @param smallType 小类valuekey
	 * @return
	 */
	@RequestMapping("/searchProductForReplaceTub")
	@ResponseBody
	public Object searchProductForReplaceTub(String smallType, String msgId, HttpServletRequest request) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(smallType)) {
			return new ResponseEnvelope<>(false, "参数smallType不能为空", msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (null == loginUser) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, msgId);
		}
		// 参数验证 ->end
		
		List<CategoryProductResult> resultList = baseProductService.searchProductForReplaceTub(smallType, loginUser);
		if(resultList == null) {
			return new ResponseEnvelope<>(false, "未搜索到对应水龙头白膜", msgId);
		}
		return new ResponseEnvelope<>(1, resultList, msgId);
	}
	
	/**
	 * 通过产品编码得到产品详情
	 * 
	 * @author huangsongbo
	 * @param productCodes
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/getInfoByProductCode")
	@ResponseBody
	public ResponseEnvelope<CategoryProductResult> getInfoByProductCode(
			String productCodes,
			String msgId
			){
		// 参数验证 ->start
		if(StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<>(false, "参数msgId不能为空", msgId);
		}
		if(StringUtils.isEmpty(productCodes)) {
			return new ResponseEnvelope<>(false, "参数productCodes不能为空", msgId);
		}
		// 参数验证 ->end
		
		List<String> productCodeList = Utils.getListFromStr(productCodes);
		
		List<CategoryProductResult> results = baseProductService.getInfoByProductCode(productCodeList);
		return new ResponseEnvelope<>(results == null ? 0 : results.size(), results, msgId);
	}
	
}
