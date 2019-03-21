package com.nork.product.controller.web;

import com.nork.base.controller.BaseController;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.param.ParamCommonVerification;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.common.PlatformConstants;
import com.nork.product.model.*;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.*;
import com.nork.system.model.*;
import com.nork.system.service.*;
import com.nork.user.model.UserTypeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Title: BaseProductController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品库Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/vue/product/baseProduct")
public class VueBaseProductController extends BaseController {
	private static Logger logger = Logger.getLogger(VueBaseProductController.class);
	private final JsonDataServiceImpl<BaseProduct> JsonUtil = new JsonDataServiceImpl<BaseProduct>();
	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	@Autowired
	private BaseProductService baseProductService;
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
	private DesignRulesService designRulesService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductPlatformService productPlatformService;

	/**
	 * 产品详情接口
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/productDetailsWeb")
	@ResponseBody
	public Object productDetailsWeb(@RequestBody BaseProduct baseProduct, HttpServletRequest request, HttpServletResponse response) {

		Long startTime=System.currentTimeMillis();
		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "param error!", "");
		}
		String msgId = baseProduct.getMsgId();
		Integer id = baseProduct.getId();
		boolean valid = ParamCommonVerification.checkTheParamIsValid(msgId,id);
		if (!valid) {
			return new ResponseEnvelope<BaseProduct>(false, "param is empty!", msgId);
		}
		if(Utils.enableRedisCache()){
			baseProduct = BaseProductCacher.get(id);
		}else{
			baseProduct = baseProductService.get(id);
		}
		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, "产品不存在!", msgId);
		}

		Integer designPlanId = baseProduct.getPlanId();
		Integer spaceCommonId = baseProduct.getSpaceComonId();
		Integer planProductId = baseProduct.getDesignPlanProductId();
		Map<Object,Object>paramsMap=new HashMap<Object,Object>();
		paramsMap.put("designPlanId", designPlanId);
		paramsMap.put("spaceCommonId", spaceCommonId);
		paramsMap.put("planProductId", planProductId);
		if(baseProduct!=null){
			paramsMap.put("baseProductId", baseProduct.getId());
		}
		ResponseEnvelope ResponseEnvelope = null;

		try {
			LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
			if (loginUser == null) {
				return new ResponseEnvelope<BaseProduct>(false, "请登录!", msgId);
			}

			/** 获取用户所属品牌 */
			List<BaseBrand> baseBrand = new ArrayList<BaseBrand>();
			if (Utils.enableRedisCache()) {
				baseBrand = BaseBrandCacher.getList(loginUser.getId(),loginUser.getUserType());
			}else{
				if(2==loginUser.getUserType()){//企业用户
					baseBrand = baseBrandService.findBrandIdByUserIdListBase(loginUser.getId());
				}else if(3==loginUser.getUserType()){//经销商
					baseBrand = baseBrandService.findBrandIdByUserIdListB(loginUser.getId());
				}
			}

			/** 品牌是否被绑定状态 */
			String productBrandId = baseProduct.getBrandId().toString();

			if(null != baseBrand){
				String brandIds = "";
				for(BaseBrand ac : baseBrand){
					if(null != ac.getIdStr()){
						if(ac.getIdStr().indexOf(productBrandId)==-1 ){		//这个品牌id不能用indexOf 模糊匹配，应该查出集合 用精准匹配
							baseProduct.setSalePrice(-1.0);
						}
					}
					if( StringUtils.isEmpty(ac.getIdStr()) ){
						baseProduct.setSalePrice(-1.0);
					}
				}
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


			/** 获取产品平台个性化信息 */
			productPlatformService.getBaseProductInfo(baseProduct.getId(), PlatformConstants.PC_2B,baseProduct);

			// 详情逻辑
			productDetailContent(baseProduct,planProductId+"",designPlan, request);

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

			baseProduct.setProductId(id);
			//这个属性在详情里没有用到，而其他接口有用到且是数组类型，设置为null，如果是“”客户端报错
			baseProduct.setSplitTexturesInfo(null);
			Long endTime=System.currentTimeMillis();
			Map<String,CategoryProductResult> baimoRule = baseProductService.setbaimoRuleMap(spaceCommonId, request, loginUser.getId(), baseProduct.getProductSmallTypeCode(), designPlan, map);
			baseProduct.setBasicModelMap(baimoRule);

			/**
			 * add by xiaoxc_20180508
			 * 截面天花信息
			 */
			//状态权限
			List<Integer> putawayStateList = baseProductService.getPutawayList(loginUser);
			baseProductService.getProductCeilingVO(baseProduct,putawayStateList);

			/**
			 * add by xiaoxc_20180305
			 * diy产品删除或下架，状态验证提示 ------start
			 */
			//TODO 产品删除验证
			if (Constants.DATA_DELETED_STATUS_ONE.equals(baseProduct.getIsDeleted())) {
				baseProduct.setProductMessage(ProductConstant.PRODUCT_DELETED_MESSAGE_HINT);
				return new ResponseEnvelope<BaseProduct>(baseProduct, msgId, true);
			}
			//TODO 非内部用户，如果产品不是发布状态，return
			if (!ProductStatuCode.HAS_BEEN_RELEASE.equals(baseProduct.getPutawayState())) {
				if (!UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType())) {
					baseProduct.setProductMessage(ProductConstant.PRODUCT_NOT_SHELVES_MESSAGE_HINT);
				} else {
					//TODO 内部用户产品状态非测试、非上架中、非发布，return
					if (!ProductStatuCode.HAS_BEEN_PUTAWAY.equals(baseProduct.getPutawayState()) ||
							!ProductStatuCode.TESTING.equals(baseProduct.getPutawayState())) {
						baseProduct.setProductMessage(ProductConstant.PRODUCT_NOT_SHELVES_MESSAGE_HINT);
					}
				}
			}
			/** diy产品删除或下架，状态验证提示 ------end **/

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



	@SuppressWarnings("all")
	public void productDetailContent(BaseProduct baseProduct,String planProductId,DesignPlan designPlan, HttpServletRequest request) throws Exception {
		//媒介类型.如果没有值，则表示为web前端（2）
 		String mediaType = SystemCommonUtil.getMediaType(request);
		// 当前登录人是否已经收藏该产品
		LoginUser loginUser = new LoginUser();
		if (SystemCommonUtil.getCurrentLoginUserInfo(request) == null || SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
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

}
