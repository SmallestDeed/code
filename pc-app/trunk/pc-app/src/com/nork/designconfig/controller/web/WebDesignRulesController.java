package com.nork.designconfig.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlanProduct;
import com.nork.designconfig.model.ProductInfo;
import com.nork.designconfig.model.ProductReplaceInfo;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.*;
import com.nork.product.model.vo.ProductCeilingVO;
import com.nork.product.service.BaseProductCountertopsService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.system.service.ModelAreaRelService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: DesignRulesController.java 
 * @Package com.nork.designconfig.controller
 * @Description:设计配置-设计规则Controller
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 19:56:47
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/designconfig/designRules")
public class WebDesignRulesController {
	private static Logger logger = LoggerFactory.getLogger(WebDesignRulesController.class);
	private final JsonDataServiceImpl<DesignRules> JsonUtil = new JsonDataServiceImpl<DesignRules>();
	
	@Autowired
	private DesignRulesService designRulesService;
	
	@Autowired
	private DesignPlanService  designPlanService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private ProductAttributeService productAttributeService;

	@Autowired
	private BaseProductMapper baseProductMapper;

	@Autowired
	private ProductCategoryRelService productCategoryRelService;

	@Autowired
	private BaseProductCountertopsService baseProductCountertopsService;

	@Autowired
	private ModelAreaRelService modelAreaRelService;

	@RequestMapping("/getProductReplaceInfo")
	@ResponseBody
	public Object getProductReplaceInfo(HttpServletRequest request,
										@RequestParam(value="productId", required = false) Integer productId,
										@RequestParam(value="designPlanId", required = false) Integer designPlanId,
										@RequestParam(value="msgId", required = false) String msgId,
										Integer isStandard, String regionMark, Integer styleId, String measureCode,
										Integer planProductId,
										// isSpellingFlowerProduct = 0 or null:默认老逻辑
										// isSpellingFlowerProduct = 1:拼花产品,取材质走拼花材质
										Integer isSpellingFlowerProduct){

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (null == loginUser)
			return new ResponseEnvelope<ProductReplaceInfo>(false, "请先登录！", msgId);

		ProductReplaceInfo productReplaceInfo = new ProductReplaceInfo();
		try {

			//规则信息
			this.setRulesSecondary(productId,designPlanId,productReplaceInfo);

			//产品详情信息
			this.setProductDetails(designPlanId,planProductId,productId,loginUser,isStandard,regionMark,styleId,measureCode,isSpellingFlowerProduct,msgId,productReplaceInfo);

		}catch (BizException e){
			return new ResponseEnvelope<ProductReplaceInfo>(false, productReplaceInfo,e.getMessage(), msgId);
		}catch (Exception e){
			logger.error("获取产品替换详情系统错误",e);
			return new ResponseEnvelope<ProductReplaceInfo>(false, productReplaceInfo,"系统错误", msgId);
		}

		return new ResponseEnvelope<ProductReplaceInfo>(true, productReplaceInfo,"获取产品替换详情成功", msgId);
	}


	private void setRulesSecondary(Integer productId,Integer designPlanId,ProductReplaceInfo productReplaceInfo) throws BizException{

		//缓存参数
		ResponseEnvelope responseEnvelope=null;
		Map<String,String> rulesMap = null;
		Map<Object,Object> paramsMap = new HashMap<>();
		paramsMap.put("productId", productId);
		paramsMap.put("designPlanId", designPlanId);

		//查看是否具有缓存
		if(Utils.enableRedisCache()){
			responseEnvelope = CommonCacher.getAll(ModuleType.DesignRules, "getRulesSecondaryList", paramsMap);
			if(null != responseEnvelope && null != responseEnvelope.getObj())
				rulesMap =  (Map<String,String>)responseEnvelope.getObj();
		}

		if(null == rulesMap){

			DesignPlan designPlan = null;
			Integer spaceCommonId = null;
			Integer designTempletId = null;

			try{

				ProductInfo productInfo = baseProductService.getProdutctInfoById(productId);
				if(null == productInfo)
					throw new BizException("找不到该产品ID");
				if(StringUtils.isBlank(productInfo.getValuekey()))
					throw new BizException("找不到该产品大类");
				if(StringUtils.isBlank(productInfo.getSmallValueKey()))
					throw new BizException("找不到该产品小类");

				designPlan=designPlanService.get(designPlanId);
				if(designPlan != null){
					spaceCommonId=designPlan.getSpaceCommonId();
					designTempletId=designPlan.getDesignTemplateId();
				}
				Map<String,String> map = new HashMap<>();
				map = productAttributeService.getPropertyMap(productId);

				rulesMap = designRulesService.getRulesSecondaryList(productId+"",
						productInfo.getValuekey(),productInfo.getSmallValueKey(),spaceCommonId,designTempletId,new DesignRules(),map);
				rulesMap.put("putawayState",productInfo.getPutawayState() == null ? null : productInfo.getPutawayState().toString());

				/*加入缓存处理*/
				if(Utils.enableRedisCache()){
					CommonCacher.addAll(ModuleType.DesignRules, "getRulesSecondaryList", paramsMap, responseEnvelope);
				}

			}catch(Exception e){
				logger.error("获取产品规则信息系统错误",e);
				throw new BizException("获取产品规则信息系统错误");
			}
		}

		productReplaceInfo.setRulesSecondary(rulesMap);
	}

	private void setProductDetails(Integer designPlanId,Integer planProductId,Integer productId,LoginUser loginUser,
								   Integer isStandard, String regionMark, Integer styleId, String measureCode,
								   Integer isSpellingFlowerProduct,String msgId,ProductReplaceInfo productReplaceInfo) throws BizException{

		CategoryProductResult categoryProductResult = baseProductMapper.getDetailById(productId, loginUser.getId());

		if(null == categoryProductResult)
			throw new BizException("获取产品详情：产品不存在");

		//设值产品：多维材质标识
		if(StringUtils.isNotBlank(categoryProductResult.getSplitTexturesInfoStr()))
			productReplaceInfo.setIsMultidimensional(1);

		// 处理材质返回格式
//		productCategoryRelService.dealWithTextureInfo(categoryProductResult);
		if (ProductConstant.ISSPELLINGFLOWERPRODUCT_TRUE.equals(isSpellingFlowerProduct)) {

			categoryProductResult.setSplitTexturesChoose(
					productCategoryRelService.getSplitTexturesChooseForSpellingflower(categoryProductResult.getParquetTextureIds(), categoryProductResult.getProductId())
			);

		}else {

			//获取材质信息
			Map<String,Object> dealWithMap =  modelAreaRelService.dealWithSplitTextureInfo(categoryProductResult.getProductId(),null,"choose","1");
			Integer isSplit = (Integer)dealWithMap.get("isSplit");
			List<SplitTextureDTO> splitTexturesChooseList = (List<SplitTextureDTO>)dealWithMap.get("splitTexturesChooseList");
			categoryProductResult.setIsSplit(isSplit);
			categoryProductResult.setSplitTexturesChoose(splitTexturesChooseList);

		}

		/** 处理产品 橱柜 信息*/
		baseProductCountertopsService.setCountertopsDetails(categoryProductResult);

		// 当前模型产品ID
		categoryProductResult.setModelProductId(categoryProductResult.getProductId());

		// 软硬装:0:软装;1:硬装
		String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1()==null?"2":categoryProductResult.getSmallTypeAtt1().trim();
		categoryProductResult.setRootType(smallTypeAtt1);

		// 是否是背景墙
		Integer bgWallValue = this.getBgWallValue(categoryProductResult.getProductTypeCode(),categoryProductResult.getProductSmallTypeCode());
		categoryProductResult.setBgWall(bgWallValue);

		// 产品附加属性
		Map<String, String> map = new HashMap<>();
		map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
		categoryProductResult.setPropertyMap(map);

		//标准、款式、尺寸代码回填
		categoryProductResult.setStyleId(styleId);
		categoryProductResult.setIsStandard(isStandard);
		categoryProductResult.setRegionMark(regionMark);
		categoryProductResult.setMeasureCode(measureCode);


		//获取方案
		DesignPlan designPlan = designPlanService.getDesignPlan(designPlanId, msgId);
		//获取方案产品
		DesignPlanProduct designPlanProduct = designPlanService.getDesignProduct(planProductId, msgId);
		//获取伸缩产品全铺长度
		Integer fullPaveLength = getFullPaveLength(designPlanProduct);

		// 获取需要自动携带白模产品的配置
		Map<String, AutoCarryBaimoProducts> autoCarryMap = productCategoryRelService.getAutoCarryMap();
		// 自动携带白膜属性:未优化
		baseProductService.setBasicModelMap(categoryProductResult, autoCarryMap, map, designPlan, designPlanProduct, loginUser.getMediaType());

		//产品需拉伸缩放的长度值
		Map<String,String> isStretchZoom = baseProductService.getStretchZoomLength(categoryProductResult.getProductSmallTypeCode());
		if (isStretchZoom != null && 0 < isStretchZoom.size()) {

			if (0 == fullPaveLength)
				fullPaveLength = Utils.getIntValue(categoryProductResult.getProductLength());

			categoryProductResult.setFullPaveLength(fullPaveLength);
		}

		//判断：如果是截面天花-天花数据存储到单独VO
		if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(categoryProductResult.getProductTypeCode())){

			ProductCeilingVO productCeilingVO = baseProductService.getCeilingInfoByProductId(categoryProductResult.getProductId());

			if(null != productCeilingVO){

				productCeilingVO.setRegionMark(categoryProductResult.getRegionMark());
				categoryProductResult.setProductCeilingVO(productCeilingVO);

			}
		}

		productReplaceInfo.setProductDetails(categoryProductResult);

	}

	/**
	 * 获取全铺长度值
	 * @param designPlanProduct
	 * @return
	 */
	private Integer getFullPaveLength(DesignPlanProduct designPlanProduct) {
		if (null == designPlanProduct) {
			return new Integer(0);
		}
		Integer productId = null;
		if (designPlanProduct.getInitProductId() != null && designPlanProduct.getInitProductId() > 0 ) {
			productId = designPlanProduct.getInitProductId();
		} else {
			productId = designPlanProduct.getProductId();
		}
		BaseProduct baseProduct = null;
		if (null != productId) {
			baseProduct = baseProductService.get(productId);
		} else {
			return new Integer(0);
		}
		Integer fullPaveLength = new Integer(0);
		if (null != baseProduct) {
			try {
				if (StringUtils.isNotEmpty(baseProduct.getFullPaveLength())) {
					fullPaveLength = Integer.valueOf(baseProduct.getFullPaveLength());
				} else {
					if (StringUtils.isNotEmpty(baseProduct.getProductLength())) {
						fullPaveLength = Integer.valueOf(baseProduct.getProductLength());
					}
				}
			} catch (NumberFormatException e) {
				//容错处理：四舍五入
				if (StringUtils.isNotEmpty(baseProduct.getFullPaveLength())) {
					fullPaveLength = Integer.valueOf((int) Math.round(Double.valueOf(baseProduct.getFullPaveLength())));
				} else {
					if (StringUtils.isNotEmpty(baseProduct.getProductLength())) {
						fullPaveLength = Integer.valueOf((int) Math.round(Double.valueOf(baseProduct.getProductLength())));
					}
				}
			}
		}
		return fullPaveLength;
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
	 * 获取规则
	 * getRulesSecondaryList
	 * @param request
	 * @param productId
	 * @param designPlanId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/getRulesSecondaryList")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Object getRulesSecondaryList(HttpServletRequest request,
			@RequestParam(value="productId", required = false) Integer productId,
			@RequestParam(value="designPlanId", required = false) Integer designPlanId,
			@RequestParam(value="msgId", required = false) String msgId){
			/*缓存处理*/
			 ResponseEnvelope responseEnvelope=null;
			Map<Object,Object>paramsMap =new HashMap<Object,Object>();
			paramsMap.put("productId", productId);
			paramsMap.put("designPlanId", designPlanId);
			if(Utils.enableRedisCache()){
				responseEnvelope=CommonCacher.getAll(ModuleType.DesignRules, "getRulesSecondaryList", paramsMap);
			}
			if(responseEnvelope!=null){
				responseEnvelope.setMsgId(msgId);
				return responseEnvelope;
			}
			BaseProduct baseProduct= null;
			String bigValue=null;
			String smallValue=null;
			String productTypeCode=null;
			String productSmallTypeCode=null;
			DesignPlan designPlan=null;
			Integer spaceCommonId=null;
			Integer designTempletId=null;
		
			try{
				
				if(productId==null||productId.intValue()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "缺少参数productId", msgId);
				}
				
				baseProduct=baseProductService.get(productId);
				if(baseProduct==null){
					return new ResponseEnvelope<BaseProduct>(false, "找不到该产品ID!"+productId, msgId);
				}
				bigValue=baseProduct.getProductTypeValue();
				smallValue=baseProduct.getProductSmallTypeValue()+"";
				
				SysDictionary sysDictionary = new SysDictionary();
				sysDictionary.setIsDeleted(0);
				sysDictionary.setType("productType");
				List<SysDictionary>bgList=null;
				List<SysDictionary>smallList=null;
				if(bigValue!=null&&!"".equals(bigValue)){
					sysDictionary.setValue(Integer.parseInt(bigValue));
					bgList=sysDictionaryService.getList(sysDictionary);
				}
				if(bgList==null||bgList.size()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "找不到该产品大类!", msgId);
				}
				if(bgList!=null&&bgList.size()!=1){
					return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
				}
				if(bgList!=null&&bgList.size()==1){
					productTypeCode=bgList.get(0).getValuekey();
				}
				if(bigValue!=null&&smallValue!=null&&!"".equals(smallValue)){
					sysDictionary.setType(productTypeCode);
					sysDictionary.setValue(Integer.parseInt(smallValue));
					smallList=sysDictionaryService.getList(sysDictionary);
				}
				if(smallList==null||smallList.size()<=0){
					return new ResponseEnvelope<BaseProduct>(false, "找不到该产品小类!", msgId);
				}
				if(smallList!=null&&smallList.size()!=1){
					return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
				}
				if(smallList!=null&&smallList.size()==1){
					productSmallTypeCode=smallList.get(0).getValuekey();
				}
				
				 
				designPlan=designPlanService.get(designPlanId);
				if(designPlan!=null){
					spaceCommonId=designPlan.getSpaceCommonId();
					designTempletId=designPlan.getDesignTemplateId();
				}
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyMap(productId);
		 
				
				responseEnvelope=new ResponseEnvelope();
 
				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(productId+"",
						productTypeCode,productSmallTypeCode,spaceCommonId,designTempletId,new DesignRules(),map);
				rulesMap.put("putawayState",baseProduct.getPutawayState()==null?null:baseProduct.getPutawayState().toString());
				responseEnvelope.setMsgId(msgId);
				responseEnvelope.setObj(rulesMap);
				/*if(rulesMap!=null&&rulesMap.size()>0){
					JSONObject jsonObject = JSONObject.fromObject(rulesMap);  
					String res=jsonObject.toString();
					if(res!=null&&!"".equals(res)){
						res=res.replace("\"","") ;
						res=res.replace("\\","");
						responseEnvelope.setObj(res);
					}
				}*/
				/*加入缓存处理*/
				if(Utils.enableRedisCache()){
					CommonCacher.addAll(ModuleType.DesignRules, "getRulesSecondaryList", paramsMap, responseEnvelope);
				}
				return responseEnvelope;
					
			}catch(Exception e){
				e.printStackTrace();
				return new ResponseEnvelope<BaseProduct>(false, "数据错误!", msgId);
			}
	}
	
}
