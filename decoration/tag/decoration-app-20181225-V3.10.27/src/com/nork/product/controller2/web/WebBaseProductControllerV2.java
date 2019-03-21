package com.nork.product.controller2.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.product.model.*;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.controller.BaseController;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.UserProductCollectService;
import com.nork.product.service2.BaseProductServiceV2;
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
@RequestMapping("/{style}/web/product/baseProduct2")
public class WebBaseProductControllerV2 extends BaseController {
	private static Logger logger = Logger.getLogger(WebBaseProductControllerV2.class);
	private final JsonDataServiceImpl<BaseProduct> JsonUtil = new JsonDataServiceImpl<BaseProduct>();
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/product/baseProduct";
	private final String BASEMAIN = "/" + STYLE + "/product/base/baseProduct";
	private final String JSPMAIN = "/" + JSPSTYLE + "/product/baseProduct";
	
	@Autowired
	private BaseProductServiceV2 baseProductService;
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
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private BaseProductStyleService baseProductStyleService;
	
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
	 * 产品详情接口
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/productDetailsWeb")
	@ResponseBody 
	public Object productDetailsWeb(@PathVariable String style, @ModelAttribute("baseProduct") BaseProduct baseProduct,
			Integer designPlanId, Integer spaceCommonId,String planProductId, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = null;
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ) {
			return new ResponseEnvelope<DesignPlan>(false, "请先登录！", "none");
		}else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}	
		
		Map<Object,Object>paramsMap=new HashMap<Object,Object>();
		paramsMap.put("designPlanId", designPlanId);
		paramsMap.put("spaceCommonId", spaceCommonId);
		paramsMap.put("planProductId", planProductId);
		if(baseProduct != null){
			paramsMap.put("baseProductId", baseProduct.getId());
		}
		ResponseEnvelope ResponseEnvelope = null;
		if(Utils.enableRedisCache()){
			ResponseEnvelope = CommonCacher.getAll(ModuleType.BaseProduct,"productDetailsWeb",paramsMap);
		}
		if(ResponseEnvelope != null){
			return  ResponseEnvelope;
		}
		
//		Long startTime=System.currentTimeMillis();
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			baseProduct = (BaseProduct) JsonUtil.getJsonToBean(jsonStr, BaseProduct.class);
			if (baseProduct == null) {
				return new ResponseEnvelope<BaseProduct>(false, "none", "传参异常!");
			}
		}
		Integer id =  baseProduct.getId();
		String msgId = baseProduct.getMsgId();
		if (id == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少id!", msgId);
		}
		baseProduct = (Utils.enableRedisCache()? BaseProductCacher.get(id) : baseProductService.get(id));
		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "产品不存在!", msgId);
		}
		
		try {
			//获取该用户绑定的序列号品牌
			baseProductService.getAuthorized(baseProduct,loginUser);
			//产品材质处理
			baseProductService.productMaterial(baseProduct, id, planProductId, designPlanId, spaceCommonId,  loginUser, request);
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
		baseProduct.setSplitTexturesInfo(null);
		//获取不同媒介u3d模型

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
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(baseProduct.getProductTypeKey())) {
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
			Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
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
				BaseProduct bp = baseProductService.get(Utils.getIntValue(mergeId));
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
	
}
