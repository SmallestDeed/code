package com.nork.product.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.MergeProductResult;
import com.nork.product.model.ProductAttribute;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductCollectService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.ProductAttributeService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

@Controller
@RequestMapping("/{style}/web/product/groupProductDetails")
public class WebGroupProductDetailsController {
	
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private GroupProductCollectService groupProductCollectService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ProductAttributeService productAttributeService;
	
	
	/**
	 * 组合产品详情选择属性
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/selectProductAttribute")
	@ResponseBody
	public Object selectProductAttribute(String msgId,Integer colorId,String materialId,String onclickType,String mergeProductIds,
			Integer productId,String attributeKey,String attributeValueKey,Integer groupId,HttpServletRequest request, HttpServletResponse response) {
		
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
		if(groupId == null){
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少groupId!",msgId);
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
			productDetailContent(baseProduct,groupId,request);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<BaseProduct>(baseProduct,msgId,true);
	}
	
	public void productDetailContent(BaseProduct baseProduct, Integer groupId,HttpServletRequest request) throws Exception {
		// 当前登录人是否已经收藏该产品组合
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
		groupProductCollectSearch.setUserId(loginUser.getId());
//		groupProductCollectSearch.setGroupId(groupId);
		Integer count = groupProductCollectService.getCount(groupProductCollectSearch);
		if (count > 0) {
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
		//组合产品列表
		GroupProductDetails groupProductDetails = new GroupProductDetails();
		groupProductDetails.setGroupId(groupId);
		List<GroupProductDetails> gpdList = groupProductDetailsService.getList(groupProductDetails);
		List<BaseProduct> bpList = new ArrayList<BaseProduct>();
		for(GroupProductDetails gpd : gpdList){
			BaseProduct bp = baseProductService.get(gpd.getProductId());
			if( bp != null && bp.getPicId() != null & bp.getPicId() > 0 ){
				ResPic resPic = resPicService.get(bp.getPicId());
				bp.setPicPath(resPic==null?"":resPic.getPicPath());
			}
			bpList.add(bp);
		}
//		baseProduct.setGroupProductList(bpList);
		baseProduct.setGroupId(groupId);//组合产品ID

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
		}

		// 产品图片列表
		String smallPicIds = baseProduct.getPicIds();
		if (StringUtils.isNoneBlank(smallPicIds)) {
			String[] strs = smallPicIds.split(",");
			for (String smallPic : strs) {
				ResPic resPic = resPicService.get(Utils.getIntValue(smallPic));
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
			String[] strs = materialIds.split(",");
			baseProduct.setMaterialId(strs[0]);
			resp = resPicService.get(Utils.getIntValue(strs[0]));
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
				mergeProductResult.setProductId(baseProduct.getId());
				mergeProductResult.setColorId(colorId2);
				mergeProductResult.setColorName(dictionary.getName());
				baseProduct.getColorlist().add(mergeProductResult);
			}
			/* 材质 */
			if (StringUtils.isNotBlank(materialIds)) {
				MergeProductResult mergeProductResult = new MergeProductResult();
				mergeProductResult.setState(1);
				String[] strs = materialIds.split(",");
				ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
				if (resPic != null) {
					mergeProductResult.setState(2);
					mergeProductResult.setProductId(baseProduct.getId());
					mergeProductResult.setMaterialName(resPic.getPicName());
					mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
					mergeProductResult.setMaterialPicPath(resPic == null ? "" : resPic.getPicPath());
					baseProduct.getMateriallist().add(mergeProductResult);
				}

				// 产品材质路径
				if (StringUtils.isNotBlank(materialIds)) {
					String[] mIds = materialIds.split(",");
					if (mIds != null) {
						resPic = new ResPic();
						resPic.setFileKey("product.baseProduct.material");
						resPic.setBusinessId(baseProduct.getId());
						resPic.setOrders(" sequence asc ");
						List<ResPic> materialPics = resPicService.getList(resPic);
						if (materialPics != null && materialPics.size() > 0) {
							String[] materialPaths = new String[materialPics.size()];
							for (int i = 0; i < materialPics.size(); i++) {
								resPic = materialPics.get(i);
								if (resPic != null) {
									materialPaths[i] = resPic.getPicPath();
								}
							}
							baseProduct.setMaterialPicPaths(materialPaths);
						}
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
		List<Integer> proIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(mergeIds) && mergeIds.contains(",")) {
			String arr[] = mergeIds.split(",");
			String colorValue = ".";
			String materialName = ".";
			//TODO
			for (String mergeId : arr) {
				proIdList.add(Integer.parseInt(mergeId));
				BaseProduct bp = baseProductService.get(Utils.getIntValue(mergeId));
				// 颜色
				if (bp.getColorId() != null) {
					if (colorValue.indexOf(("." + bp.getColorId() + ".")) == -1) {
						MergeProductResult mergeProductResult = new MergeProductResult();
						SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor",
								bp.getColorId());
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
				}

				// 材质
				String materialPicIds = bp.getMaterialPicIds();
				if (StringUtils.isNotBlank(materialPicIds)) {
					MergeProductResult mergeProductResult = new MergeProductResult();
					mergeProductResult.setState(1);
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
				}

				if (StringUtils.isNotBlank(materialPicIds)) {
					String[] mIds = materialPicIds.split(",");
					if (mIds != null) {
						ResPic resPic = new ResPic();
						resPic.setFileKey("product.baseProduct.material");
						resPic.setBusinessId(baseProduct.getId());
						resPic.setOrders(" sequence asc ");
						List<ResPic> materialPics = resPicService.getList(resPic);
						if (materialPics != null && materialPics.size() > 0) {
							String[] materialPaths = new String[materialPics.size()];
							for (int i = 0; i < materialPics.size(); i++) {
								resPic = materialPics.get(i);
								if (resPic != null) {
									materialPaths[i] = resPic.getPicPath();
								}
							}
							baseProduct.setMaterialPicPaths(materialPaths);
						}
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

		// 媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		Integer u3dModel = 0;
		if ("3".equals(mediaType)) {
			u3dModel = baseProduct.getWindowsU3dModelId();
			if (u3dModel != null && u3dModel > 0) {
				ResModel resModel = resModelService.get(u3dModel);
				baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
			}
			baseProduct.setIpadU3dModelId(0);
		} else if ("4".equals(mediaType)) {
			u3dModel = baseProduct.getMacBookU3dModelId();
			if (u3dModel != null && u3dModel > 0) {
				ResModel resModel = resModelService.get(u3dModel);
				baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
			}
		} else if ("5".equals(mediaType)) {
			u3dModel = baseProduct.getIosU3dModelId();
			if (u3dModel != null && u3dModel > 0) {
				ResModel resModel = resModelService.get(u3dModel);
				baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
			}
		} else if ("6".equals(mediaType)) {
			u3dModel = baseProduct.getAndroidU3dModelId();
			if (u3dModel != null && u3dModel > 0) {
				ResModel resModel = resModelService.get(u3dModel);
				baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
			}
		} else if ("7".equals(mediaType)) {
			u3dModel = baseProduct.getIpadU3dModelId();
			if (u3dModel != null && u3dModel > 0) {
				ResModel resModel = resModelService.get(u3dModel);
				baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
			}
		} else {
			u3dModel = Utils.getIntValue(baseProduct.getU3dModelId());
			if (u3dModel != null && u3dModel > 0) {
				ResModel resModel = resModelService.get(u3dModel);
				baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
			}
		}
		baseProduct.setParentProductId(baseProduct.getParentId());
	}

}
