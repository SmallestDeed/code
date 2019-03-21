package com.nork.product.controller.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.properties.ResProperties;
import com.nork.product.model.*;
import com.nork.user.model.UserTypeCode;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductResult;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.GroupProductDetailsCache;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.product.service.GroupProductCollectService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.productprops.service.ProductPropsService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

@Controller
@RequestMapping("/{style}/web/product/groupProduct")
public class WebGroupProductController {
	@Autowired
	private ProductPropsService productPropsService;
	@Autowired
	private GroupProductService groupProductService;
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
	
	@Autowired
	private AuthorizedConfigService authorizedConfigService;

	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	private BaseProductStyleService baseProductStyleService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private DesignPlanProductService designPlanProductService;

	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	private static Logger logger = Logger
			.getLogger(WebProductCategoryRelController.class);
	
	/**
	 * 产品组合详情接口
	 * 
	 * @param groupId 组合产品Id
	 * 
	 */
	@RequestMapping(value = "/getGroupProductDetails")
	@ResponseBody
	public Object getGroupProductDetails(String msgId,Integer groupId,
			 HttpServletRequest request, HttpServletResponse response) {
		
		/*数据验证*/
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<GroupProduct>(false, "参数msgId不能为空!", msgId);
		}
		if(groupId==null){
			return new ResponseEnvelope<GroupProduct>(false, "参数组合groupId不能为空!", msgId);
		}
		GroupProduct groupProduct = groupProductService.getMoreInfoById(groupId);
		if( groupProduct==null ){
			return new ResponseEnvelope<GroupProduct>(false, "找不到该组合产品:"+groupId, msgId);
		}
		/*媒介类型.如果没有值，则表示为web前端（2）*/
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
								 
		SearchProductGroupResult groupProductResult = new SearchProductGroupResult();
		SearchProductGroupDetail groupDetail = null;
		List<SearchProductGroupDetail> details = new ArrayList<>();
		/*填充位置信息没因为老数据是 文本，新数据是id 这里 支持新老数据*/
		if(groupProduct.getLocation()!=null&&!"".equals(groupProduct.getLocation())){
			ResFile resFile=null;
			String txt=null;
			if(StringUtils.isNumeric(groupProduct.getLocation())){/*判断里面是不是数字*/
				resFile	=resFileService.get(Integer.parseInt(groupProduct.getLocation()));
				if(resFile!=null){
					String url=Tools.getRootPath(resFile.getFilePath(), "")+resFile.getFilePath();
					txt=FileUploadUtils.getFileContext(url);
				}
				groupProduct.setLocation(txt);	 
			}
			
		}
		// 风格信息 ->start
				if(StringUtils.isNotBlank(groupProduct.getProductStyleIdInfo())){
					JSONObject styleJson = JSONObject.fromObject(groupProduct.getProductStyleIdInfo());
					List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
					StringBuffer stringBuffer = new StringBuffer("");
					for(String str : styleInfoList){
						stringBuffer.append(str + "  ");
					}
					groupProductResult.setProductStyle(stringBuffer.toString());
					groupProductResult.setProductStyleInfoList(styleInfoList);
				}
				// 风格信息 ->end
		groupProductResult.setGroupConfig(groupProduct.getLocation());
		groupProductResult.setGroupId(groupProduct.getId());
		groupProductResult.setGroupCode(groupProduct.getGroupCode());
		groupProductResult.setGroupName(groupProduct.getGroupName());
		//组合品牌,风格,描述信息,封面url->start
		groupProductResult.setBrandName(groupProduct.getBrandName());
		groupProductResult.setStyleName(groupProduct.getStyleName());
		groupProductResult.setRemark(groupProduct.getRemark());
		groupProductResult.setPicPath(groupProduct.getPicPath());
		if(groupProduct.getProductType() == null || "".equals(groupProduct.getProductType())){
			groupProductResult.setProductType("无");
		}else{
			groupProductResult.setProductType(groupProduct.getProductType());
		}
		//组合品牌,风格,描述信息,封面url->end
		
		/* 当前登录人是否已经收藏该产品组合*/
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
		groupProductCollectSearch.setUserId(loginUser.getId());
		groupProductCollectSearch.setGroupId(groupProduct.getId());
		Integer count = groupProductCollectService.getCount(groupProductCollectSearch);
		if (count > 0) {
			groupProductResult.setCollectState(1);
		} else {
			groupProductResult.setCollectState(0);
		}
		Double totalPrice = 0.00;
		int brandState=0;
		try {
			
			/*查询 组合产品明细列表*/
			GroupProductDetails groupProductDetails = new GroupProductDetails();
			groupProductDetails.setGroupId(groupProduct.getId());
			groupProductDetails.setOrder(" sorting ");
			List<GroupProductDetails> gpdList = GroupProductDetailsCache.getList(groupProductDetails);
			
			/*获取该用户绑定的序列号品牌，如果用户没有 产品品牌，则不显示价格*/
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
			
			for(GroupProductDetails detailEntity : gpdList){
				BaseProduct baseProduct=null;
				if(Utils.enableRedisCache()){
					baseProduct = BaseProductCacher.getBaseProductServiceById(detailEntity.getProductId());
				}else{
					baseProduct = baseProductService.get(detailEntity.getProductId());
				}
				groupDetail = new SearchProductGroupDetail();
				
				/*在组合产品查询列表 中 填充产品属性*/
				if(detailEntity.getProductId()>0){
					Map<String,String>map=new HashMap<String,String>();
					map=productAttributeService.getPropertyMap(detailEntity.getProductId());
					groupDetail.setPropertyMap(map);
				}
				
				
				if( baseProduct != null ){
					groupDetail.setProductId(detailEntity.getProductId());
					int isMain = detailEntity.getIsMain()==null?0:detailEntity.getIsMain();
					groupDetail.setIsMainProduct(isMain);
					Double salePrice = baseProduct.getSalePrice()==null?0:baseProduct.getSalePrice();
					/*totalPrice  组合总的价格*/
					totalPrice = totalPrice + salePrice;
					if( baseProduct.getPicId() != null && baseProduct.getPicId() > 0 ){
						ResPic resPic=null;
						if(Utils.enableRedisCache()){
							resPic = ResourceCacher.getPic(baseProduct.getPicId());
						}else{
							resPic = resPicService.get(baseProduct.getPicId());
						}
						
						groupDetail.setPicPath(resPic==null?"":resPic.getPicPath());
					}
					
					if(brandState==0){						
						/*判断该品牌是否被绑定状态*/
						String productBrandId = ","+baseProduct.getBrandId()+",";
						if( StringUtils.isEmpty(brandIds) ){
							brandState=brandState+1;
						}
						if( (","+brandIds).indexOf(productBrandId)==-1 ){
							brandState=brandState+1;
						}
					}
					/*获取不同媒介u3d模型*/
//					String planVersion = null;
//					if(designPlan!=null){
//						planVersion = designPlan.getPlanVersion();
//					}
//					String modelId = baseProductService.getU3dModelId(mediaType,baseProduct,planVersion);
//					groupDetail.setModelVersionState(baseProductService.getModelVersionState(StringUtils.isEmpty(modelId) ? 0 : new Integer(modelId),baseProduct));
					String modelId = baseProductService.getU3dModelId(mediaType,baseProduct);
					if( StringUtils.isNotBlank(modelId) ){
						ResModel resModel = resModelService.get(Integer.valueOf(modelId));
						if( resModel != null ){
							File modelFile = new File(resModel.getModelPath());
							if( modelFile.exists() ){
								groupDetail.setU3dModelPath(resModel.getModelPath());
							}
						}
					}
					
				}
				details.add(groupDetail);
			}
			groupProductResult.setGroupProductDetails(details);
			Double groupPrice = groupProduct.getGroupPrice()==null?0:groupProduct.getGroupPrice();
			if( groupPrice == 0 ){
				groupProductResult.setGroupPrice(totalPrice);
			}else{
				groupProductResult.setGroupPrice(groupPrice);
			}
			if(brandState>0){
				groupProductResult.setGroupPrice(0.0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProduct>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<GroupProduct>(groupProductResult, msgId, true);
	}
	
	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryGroupByBrand")
	@ResponseBody
	public Object queryGroupByBrand(@ModelAttribute("@baseBrandSearch")BaseBrandSearch baseBrandSearch,String msgId,HttpServletRequest request) throws UnsupportedEncodingException{

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		//验证用户
		if (loginUser == null) {
			return new ResponseEnvelope<GroupProduct>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, msgId);
		}
//		LoginUser loginUser = new LoginUser();
//		loginUser.setUserType(3);
//		loginUser.setId(48);
		logger.error("userId="+loginUser.getId()+";userType="+loginUser.getUserType());
		baseBrandSearch.setUserType(loginUser.getUserType());  /**1是内部*/
		baseBrandSearch.setUserId(loginUser.getId());
		String versionType = Utils.getPropertyName("app", ResProperties.SYS_VERSION_TYPE, "1").trim();/*1为外网  2  为内网    默认为外网*/
		baseBrandSearch.setVersionType(versionType);


		//从缓存里获取
		Map<Object,Object>paramsMap=new HashMap<Object,Object>();
		paramsMap.put("brandName", baseBrandSearch.getSch_BrandName_());
		paramsMap.put("userId", loginUser.getId());
		paramsMap.put("limit", baseBrandSearch.getLimit());
		paramsMap.put("start", baseBrandSearch.getStart());
		ResponseEnvelope ResponseEnvelope = null;
		if(Utils.enableRedisCache()){
			ResponseEnvelope=CommonCacher.getAll(ModuleType.GroupProduct,"queryGroupByBrand",paramsMap);
		}
		if(ResponseEnvelope!=null){
			return  ResponseEnvelope;
		}

		if (UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType()) {
			/**获取该用户绑定的序列号品牌*/
			String brandIds = authorizedConfigService.getAuthorizedBrandIdsByUserId(loginUser.getId());
			List<Integer> brandIdsList = new ArrayList<>();
			if (StringUtils.isNotEmpty(brandIds)) {
				String[] array = brandIds.split(",");
				for (String str : array) {
					brandIdsList.add(Utils.getIntValue(str));
				}
			}
			baseBrandSearch.setBrandIds(brandIdsList);
		}

		//如果是经销商需过滤同行业品牌组合
//		if (UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType()) {
//			//设置品牌行业值，用户过滤同行业品牌产品
//			List<BrandIndustryModel> brandIndustryModelList = baseBrandService.getAuthorizedBrandFilterCondition(loginUser.getId());
//			baseBrandSearch.setBrandIndustryModelList(brandIndustryModelList);
//		}

		List<SearchProductGroupResult>resultList = new ArrayList<SearchProductGroupResult>();
		int total = groupProductService.getBuildingHomeGroupCount(baseBrandSearch);
		if (total > 0) {
			List<GroupProductDetails> list = groupProductService.getBuildingHomeGroupList(baseBrandSearch);
			for (GroupProductDetails groupProductDetails : list) {
				SearchProductGroupResult searchProductGroupResult=new SearchProductGroupResult();
				searchProductGroupResult.setGroupId(groupProductDetails.getGroupId());
				searchProductGroupResult.setGroupCode(groupProductDetails.getGroupCode());
				searchProductGroupResult.setGroupName(groupProductDetails.getGroupName());
				searchProductGroupResult.setBrandName(groupProductDetails.getBrandName());
				searchProductGroupResult.setPicPath(groupProductDetails.getGroupPicPath());
				searchProductGroupResult.setCollectState(groupProductDetails.getCollectState());
				/*补充信息*/
				searchProductGroupResult.setProductName(searchProductGroupResult.getGroupName());
				Double salePrice = groupProductService.getSalePrice(searchProductGroupResult.getGroupId());
				searchProductGroupResult.setSalePrice(salePrice);
				resultList.add(searchProductGroupResult);
			}   
		}
		
		ResponseEnvelope responseEnvelope_=new ResponseEnvelope<>(total, resultList, baseBrandSearch.getMsgId());
		if(Utils.enableRedisCache()){
			CommonCacher.addAll(ModuleType.GroupProduct,"queryGroupByBrand",paramsMap,responseEnvelope_);
		}
		return responseEnvelope_;
	}


	/**
	 * 一键替换组合
	 *
	 * @param templetId 模板ID
	 * @param designTempletId 样板房ID
	 * @return object
	 */
	@RequestMapping("/getGroupProductData")
	@ResponseBody
	public Object getGroupProductData(Integer templetId,Integer designTempletId,String msgId, HttpServletRequest request){

		if( StringUtils.isBlank(msgId) ){
			return new ResponseEnvelope<>(false, "参数msgId不能为空！", msgId);
		}
		if( templetId == null ){
			return new ResponseEnvelope<>(false, "参数templetId不能为空！", msgId);
		}
		if( designTempletId == null ){
			return new ResponseEnvelope<>(false, "参数designTempletId不能为空！", msgId);
		}
		List<MatchGroupProductResult> list = new ArrayList();
		DesignPlan designPlan = designPlanService.get(templetId);
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if( designPlan== null || designTemplet == null){
			return new ResponseEnvelope<>(false, "找不到模板或样板房！", msgId);
		}
		//查询样板房主组合
		DesignTempletProduct designTempletProduct = new DesignTempletProduct();
		designTempletProduct.setDesignTempletId(designTempletId);
		designTempletProduct.setGroupType(0);
		designTempletProduct.setIsDeleted(0);
		designTempletProduct.setIsMainProduct(1);
		List<DesignTempletProduct> templetList = designTempletProductService.getByTempletIdMainProduct(designTempletProduct);
		if( Lists.isEmpty(templetList) ){
			return new ResponseEnvelope<>(0,list, msgId);
		}
		//模板组合主产品
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setPlanId(templetId);
		designPlanProduct.setGroupType(0);
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setIsMainProduct(1);
		List<DesignPlanProductResult> planList = designPlanProductService.getByPlanIdGroupMainProduct(designPlanProduct);
		if( Lists.isEmpty(templetList) ){
			return new ResponseEnvelope<MatchGroupProductResult>(0,list, msgId);
		}
		MatchGroupProductResult matchGroupProductResult = null ;
		for ( DesignTempletProduct templetProduct : templetList){
			matchGroupProductResult = new MatchGroupProductResult();
			int temp = 0 ;
			for (DesignPlanProductResult planProduct : planList ) {
				String productTypeValue = planProduct.getProductTypeValue();
				Integer productSmallTypeValue = planProduct.getProductSmallTypeValue();
				//匹配同分类组合产品
				if( productTypeValue != null && Utils.getIntValue(productTypeValue) == templetProduct.getProductTypeValue().intValue() ){
					//柜子按小类过滤组合，临时解决方案，后续加组合类型过滤
					if( "10".equals(productTypeValue) ){
						if( productSmallTypeValue == 1 && templetProduct.getProductSmallTypeValue() == 14 ){

						}else if( productSmallTypeValue == 2 && templetProduct.getProductSmallTypeValue() == 15){

						}else{
							continue;
						}
					}
					temp ++ ;
					matchGroupProductResult.setTempletMianProductCode(planProduct.getProductCode());
					// 目前数据库 location字段， 分为 txt 文件路径、json 串。
					String filePath = planProduct.getFilePath();
					if ( filePath != null && !filePath.trim().isEmpty() ) {
						String url = Tools.getRootPath(productTypeValue, "") + filePath;
						String text = FileUploadUtils.getFileContext(url);
						matchGroupProductResult.setTempletGroupConfig(text);
					}else{
						matchGroupProductResult.setTempletGroupConfig(planProduct.getLocation());
					}
					/*designPlanProduct.setProductGroupId(planProduct.getGroupId());
					designPlanProduct.setIsMainProduct(null);
					List<DesignPlanProductResult> planGroupList = designPlanProductService.getByPlanIdGroupMainProduct(designPlanProduct);
					List<Map<String,String>> templetGroupProductList = null;
					Map<String,String> map = null;
					if( planGroupList != null && planGroupList.size() > 0 ){
						for( DesignPlanProductResult planGroupProduct : planList ){
							templetGroupProductList = new ArrayList<>();
							map = new HashMap<>();
							map.put("templet_planProductId",planGroupProduct.getPlanProductId()+"");
							map.put("templet_posIndexPaht",planGroupProduct.getPosIndexPath());
							templetGroupProductList.add(map);
						}
					}else{
						map.put("templet_planProductId","");
						map.put("templet_posIndexPaht","");
					}
					matchGroupProductResult.setTempletGroupProductList(templetGroupProductList);*/
					break;
				}else{
					continue;
				}
			}
			if(temp > 0){
				matchGroupProductResult.setBmMianProductCode(templetProduct.getProductCode());
				List<GroupProductDetails> groupProductCodeList = groupProductDetailsService.getByGroupIdProductCodeList(templetProduct.getProductGroupId());
				List<Map<String,String>> bmGroupProductList = new ArrayList<>();
				Map<String,String> map = null;
				if( Lists.isNotEmpty(groupProductCodeList) ){
					for( GroupProductDetails productDetails : groupProductCodeList ){
						map = new HashMap<>();
						map.put("productCode",productDetails.getProductCode());
						map.put("posIndexPath",productDetails.getPosIndexPath());
						bmGroupProductList.add(map);
					}
				}
				matchGroupProductResult.setBmGroupProductList(bmGroupProductList);
				list.add(matchGroupProductResult);
			}
		}
		int total = Utils.getListTotal(list) ;

		return new ResponseEnvelope<MatchGroupProductResult>(total,list, msgId);
	}
}
