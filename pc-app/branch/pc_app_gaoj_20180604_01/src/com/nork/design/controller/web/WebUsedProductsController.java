package com.nork.design.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.product.common.PlatformConstants;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductPlatformService;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.cache.UsedProductsCacher;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.UsedProducts;
import com.nork.design.model.UserProductPlan;
import com.nork.design.model.search.UsedProductsSearch;
import com.nork.design.model.small.UsedProductsSmall;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.UsedProductsService;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.small.UserProductCollectSmall;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: UsedProductsController.java
 * @Package com.nork.design.controller
 * @Description:设计方案-已使用产品表Controller
 * @createAuthor pandajun
 * @CreateDate 2015-07-10 16:23:04
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/design/usedProducts")
public class WebUsedProductsController {
	private static Logger logger = Logger.getLogger(WebUsedProductsController.class);
	private final JsonDataServiceImpl<UsedProducts> JsonUtil = new JsonDataServiceImpl<UsedProducts>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/design/usedProducts";
	private final String BASEMAIN = "/" + STYLE + "/design/base/usedProducts";
	private final String JSPMAIN = "/" + JSPSTYLE + "/design/usedProducts";

	@Autowired
	private UsedProductsService usedProductsService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignPlanProductService  designPlanProductService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private ProductPlatformService productPlatformService;
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UsedProducts model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 删除已使用产品表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style, @ModelAttribute("usedProducts") UsedProducts usedProducts,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids = "";
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			usedProducts = (UsedProducts) JsonUtil.getJsonToBean(jsonStr, UsedProducts.class);
			if (usedProducts == null) {
				return new ResponseEnvelope<UsedProducts>(false, "传参异常!", "none");
			}
			ids = usedProducts.getIds();
			msgId = usedProducts.getMsgId();
		} else {
			ids = usedProducts.getIds();
			msgId = usedProducts.getMsgId();
		}

		if (ids == null) {
			return new ResponseEnvelope<UsedProducts>(false, "参数ids不能为空!", msgId);
		}
		int i = 0;
		try {
			if (ids != null) {
				if (ids.contains(",")) {
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = usedProductsService.delete(id);
						logger.info("delete:id=" + id);
					}
				} else {
					Integer id = new Integer(ids);
					i = usedProductsService.delete(id);
					logger.info("delete:id=" + id);
				}
			}

			if (i == 0) {
				return new ResponseEnvelope<UsedProducts>(false, "记录不存在!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UsedProducts>(false, "删除失败!", msgId);
		}
		return new ResponseEnvelope<UsedProducts>(true, msgId, true);
	}

	/**
	 * 已使用删除-----接口
	 *
	 * @param style
	 * @param usedProducts
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delUsedProducts")
	@ResponseBody
	public Object delUsedProducts(@PathVariable String style, @ModelAttribute("usedProducts") UsedProducts usedProducts,
			HttpServletRequest request) {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			usedProducts = (UsedProducts) JsonUtil.getJsonToBean(jsonStr, UsedProducts.class);
			if (usedProducts.getId() == null) {
				return new ResponseEnvelope<UsedProducts>(false, "传参异常!", "none");
			}
		}
		String msg = "";
		if (StringUtils.isBlank(usedProducts.getMsgId())) {
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<UsedProducts>(false, msg, usedProducts.getMsgId());
		}
		if (usedProducts.getId() == null) {
			msg = "参数id不能为空！";
			return new ResponseEnvelope<UsedProducts>(false, msg, usedProducts.getMsgId());
		}
		UsedProducts usedProduct=null;
		if(Utils.enableRedisCache()){
			usedProduct = UsedProductsCacher.get(usedProducts.getId());
		}else{
			usedProduct = usedProductsService.get(usedProducts.getId());
		}
		
		if (usedProduct == null) {
			return new ResponseEnvelope<UsedProducts>(false, "被删除的数据不存在!", usedProducts.getMsgId());
		} else {
			int flag = usedProductsService.delete(usedProduct.getId());
			if (flag == 1) {
				UsedProductsCacher.remove(usedProducts.getId());
				return new ResponseEnvelope<UsedProducts>(true, usedProducts.getMsgId(), true);
			} else {
				return new ResponseEnvelope<UsedProducts>(false, "数据删除失败!", usedProducts.getMsgId());
			}
		}
	}

	/**
	 * 保存 已使用产品表---接口
	 *
	 * @param style
	 * @param usedProducts
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveUsedProduct")
	@ResponseBody
	public Object saveUsedProduct(@PathVariable String style, @ModelAttribute("usedProducts") UsedProducts usedProducts,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			usedProducts = (UsedProducts) JsonUtil.getJsonToBean(jsonStr, UsedProducts.class);
			if (usedProducts == null) {
				return new ResponseEnvelope<UsedProducts>(false, "传参异常!", "none");
			}
		}
		// 获取登录用户
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!", usedProducts.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			usedProducts.setUserId(loginUser.getId());
		}

		String msg = "";
		if(usedProducts.getDesignPlanProductId()==null||usedProducts.getDesignPlanProductId()<=0){
			msg = "参数designPlanProductId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
		}
		if (StringUtils.isBlank(usedProducts.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
		}
		if (usedProducts.getUserId() == null || usedProducts.getUserId().intValue() <= 0) {
			msg = "参数userId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
		}
		if (usedProducts.getProductId() == null) {
			msg = "参数productId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
		} else if (usedProducts.getProductId() != null) {
			BaseProduct bp = new BaseProduct();
			if(Utils.enableRedisCache()){
				bp = BaseProductCacher.get(usedProducts.getProductId());
			}else{
				bp = baseProductService.get(usedProducts.getProductId());
			}
			
			if (bp == null) {
				msg = "该产品不存在";
				return new ResponseEnvelope<UserProductCollectSmall>(false, msg, usedProducts.getMsgId());
			}
		}
		if (usedProducts.getDesignId() == null) {
			msg = "参数designId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
		} else if (usedProducts.getDesignId() != null) {
			DesignPlan dpc = new DesignPlan();
			if(Utils.enableRedisCache()){
				dpc = DesignCacher.get(usedProducts.getDesignId());
			}else{
				dpc = designPlanService.get(usedProducts.getDesignId());
			}
			
			if (dpc == null) {
				msg = "该方案不存在";
				return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
			} else if (dpc != null) {
				if (usedProducts.getUserId().intValue() != dpc.getUserId().intValue()) {
					msg = "该用户无此方案设计";
					return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
				}
			}

			DesignPlanProduct designPlanProduct=designPlanProductService.get(usedProducts.getDesignPlanProductId());
			if(designPlanProduct==null){
				msg = "该用户无此产品";
				return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProducts.getMsgId());
			}
			usedProducts.setPlanProductId(designPlanProduct.getProductId());
			usedProducts.setPosIndexPath(designPlanProduct.getPosIndexPath());
			
		}


		try {
			sysSave(usedProducts, request);
			if (usedProducts.getId() == null) {
				int id = usedProductsService.add(usedProducts);
				logger.info("add:id=" + id);
				usedProducts.setId(id);
			} else {
				int id = usedProductsService.update(usedProducts);
				logger.info("update:id=" + id);
			}
			UsedProductsCacher.remove(usedProducts.getId());
			if ("small".equals(style)) {
				// String usedProductsJson =
				// JsonUtil.getBeanToJsonData(usedProducts);
				// UsedProductsSmall usedProductsSmall= new
				// JsonDataServiceImpl<UsedProductsSmall>().getJsonToBean(usedProductsJson,
				// UsedProductsSmall.class);
				String message = "ok";
				return new ResponseEnvelope<UsedProductsSmall>(message, usedProducts.getMsgId(), true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UsedProducts>(false, "数据异常!", usedProducts.getMsgId());
		}
		return new ResponseEnvelope<UsedProducts>(usedProducts, usedProducts.getMsgId(), true);
	}

	/**
	 * 已使用产品表列表---接口
	 *
	 * @param style
	 * @param usedProductsSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/usedProductList")
	@ResponseBody
	public Object usedProductList(@PathVariable String style,
			@ModelAttribute("usedProductsSearch") UsedProductsSearch usedProductsSearch, Integer designPlanId,
			Integer spaceCommonId,Integer planProductId, HttpServletRequest request, HttpServletResponse response) {
		Long startTime = System.currentTimeMillis();
		UserProductPlan userProductPlan = new UserProductPlan();
		/** 获取登录用户 */
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!", usedProductsSearch.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userProductPlan.setUserId(loginUser.getId());
		}

		/** 参数验证 */
		String msg = "";
		if (usedProductsSearch.getDesignId() == null) {
			msg = "参数designId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProductsSearch.getMsgId());
		} else if (usedProductsSearch.getDesignId() != null) {
			DesignPlan designPlan = new DesignPlan();
			if(Utils.enableRedisCache()){
				designPlan = DesignCacher.get(usedProductsSearch.getDesignId());
			}else{
				designPlan = designPlanService.get(usedProductsSearch.getDesignId());
			}
			
			if (designPlan == null) {
				msg = "该方案不存在";
				return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProductsSearch.getMsgId());
			} else if (designPlan != null) {
				if (userProductPlan.getUserId().intValue() != designPlan.getUserId().intValue()) {
					msg = "该用户无此方案设计";
					return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProductsSearch.getMsgId());
				}
			}
		}

		DesignPlan designPlan=null;
		if (designPlanId != null) {
			if(Utils.enableRedisCache()){
				designPlan = DesignCacher.get(designPlanId);
			}else{
				designPlan = designPlanService.get(designPlanId);
			}
		}
		DesignPlanProduct designPlanProduct = null;
		if( planProductId != null && planProductId.intValue() > 0 ){
			if(Utils.enableRedisCache()){
				designPlanProduct = DesignPlanProductCacher.get(planProductId);
			}else{
				designPlanProduct = designPlanProductService.get(planProductId);
			}
		}

		List<UserProductPlan> list = new ArrayList<UserProductPlan>();
		List<CategoryProductResult> productResultList = new ArrayList<CategoryProductResult>();
		int total = 0;
		try {
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if( loginUser.getUserType()==1 && "2".equals(versionType)){
				usedProductsSearch.setIsInternalUser("yes");
	        }
			if(Utils.enableRedisCache()){
				total = UsedProductsCacher.getCount(usedProductsSearch);
			}else{
				total = usedProductsService.getUsedProductPlanCount(usedProductsSearch);
			}
			logger.info("total:" + total);
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = UsedProductsCacher.getList(usedProductsSearch);
				}else{
					list = usedProductsService.getUsedProductPlanList(usedProductsSearch);
				}
				for (UserProductPlan usedProduct : list) {
					CategoryProductResult productResult = new CategoryProductResult();
					if(new Integer(1).equals(usedProduct.getStatus())){
						/*该产品已删除,另作处理*/
						String noProductImageUrl="/system/noProductImage.png";
						productResult.setPicPath(noProductImageUrl);
						continue;
					}
					BaseProduct baseProduct=new BaseProduct();
					int proId=usedProduct.getProductId() != null ? Integer.valueOf(usedProduct.getProductId()) : -1;
					if(Utils.enableRedisCache()){
						baseProduct = BaseProductCacher.get(proId);
					}else{
						baseProduct = baseProductService.get(proId);
					}
					productResult.setProductId(proId);
					productResult.setProductCode(usedProduct.getProductCode());
					productResult.setProductName(usedProduct.getProductName());
					productResult.setCollectState(usedProduct.getCollectState());
					productResult.setMaterialPicId(baseProduct==null?"-1":baseProduct.getMaterialPicIds());
					productResult.setGmtCreate(usedProduct.getGmtCreate());

					/** 获取平台个性化信息 */
					productPlatformService.getUseCategoryProductResultInfo(proId, PlatformConstants.PC_2B,productResult);

					//获取需要自动携带白模产品的配置
					Map<String,AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
					String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app","design.designPlan.autoCarryBaimoProducts","");
					if( StringUtils.isNotBlank(autoCarryBaimoProducrsConfig) ){
						JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
						try {
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
					productResult = baseProductService.decorationProductInfo(productResult,baseProduct, designPlan, designPlanProduct, autoCarryMap, request);
					productResultList.add(productResult);
				}
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String usedProductsJsonList = JsonUtil.getListToJsonData(list);
				List<UserProductPlan> smallList = new JsonDataServiceImpl<UserProductPlan>()
						.getJsonToBeanList(usedProductsJsonList, UserProductPlan.class);
				return new ResponseEnvelope<UserProductPlan>(total, smallList, usedProductsSearch.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductPlan>(false, "数据异常!", usedProductsSearch.getMsgId());
		}
		Long endTime = System.currentTimeMillis();
		//////System.out.println("times:"+(endTime-startTime));
		return new ResponseEnvelope<CategoryProductResult>(total, productResultList, usedProductsSearch.getMsgId());
	}

	/**
	 * 已使用产品组合列表---接口
	 *
	 * @param style
	 * @param usedProductsSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/usedGroupList")
	@ResponseBody
	public Object usedGroupList(@PathVariable String style,
								  @ModelAttribute("usedProductsSearch") UsedProductsSearch usedProductsSearch, Integer designPlanId,
								  Integer spaceCommonId,Integer planProductId, HttpServletRequest request, HttpServletResponse response) {
		Long startTime = System.currentTimeMillis();
		String cacheEnable=Utils.getValue("redisCacheEnable", "0");
		UserProductPlan userProductPlan = new UserProductPlan();
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser.getId() < 0) {
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!", usedProductsSearch.getMsgId());
		}
		userProductPlan.setUserId(loginUser.getId());
		String msg = "";
		if (usedProductsSearch.getDesignId() == null) {
			msg = "参数designId不能为空";
			return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProductsSearch.getMsgId());
		} else if (usedProductsSearch.getDesignId() != null) {
			DesignPlan designPlan = new DesignPlan();
			if(StringUtils.equals("1", cacheEnable)){
				designPlan = DesignCacher.get(usedProductsSearch.getDesignId());
			}else{
				designPlan = designPlanService.get(usedProductsSearch.getDesignId());
			}

			if (designPlan == null) {
				msg = "该方案不存在";
				return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProductsSearch.getMsgId());
			} else if (designPlan != null) {
				if (userProductPlan.getUserId().intValue() != designPlan.getUserId().intValue()) {
					msg = "该用户无此方案设计";
					return new ResponseEnvelope<UsedProductsSmall>(false, msg, usedProductsSearch.getMsgId());
				}
			}
		}
		DesignPlan designPlan=null;
		if (designPlanId != null) {
			if(StringUtils.equals("1", cacheEnable)){
				designPlan = DesignCacher.get(designPlanId);
			}else{
				designPlan = designPlanService.get(designPlanId);
			}
		}
		DesignPlanProduct designPlanProduct = null;
		if( planProductId != null && planProductId.intValue() > 0 ){
			if(StringUtils.equals("1", cacheEnable)){
				designPlanProduct = DesignPlanProductCacher.get(planProductId);
			}else{
				designPlanProduct = designPlanProductService.get(planProductId);
			}
		}
		List<UserProductPlan> list = new ArrayList<UserProductPlan>();
		List<CategoryProductResult> productResultList = new ArrayList<CategoryProductResult>();
		int total = 0;
		try {
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if( loginUser.getUserType()==1 && "2".equals(versionType)){
				usedProductsSearch.setIsInternalUser("yes");
			}
			if(StringUtils.equals("1", cacheEnable)){
				total = UsedProductsCacher.getCount(usedProductsSearch);
			}else{
				total = usedProductsService.getUsedProductPlanCount(usedProductsSearch);
			}
			logger.info("total:" + total);
			if (total > 0) {
				if(StringUtils.equals("1", cacheEnable)){
					list = UsedProductsCacher.getList(usedProductsSearch);
				}else{
					list = usedProductsService.getUsedProductPlanList(usedProductsSearch);
				}
				for (UserProductPlan usedProduct : list) {
					CategoryProductResult productResult = new CategoryProductResult();
					if(new Integer(1).equals(usedProduct.getStatus())){
						/*该产品已删除,另作处理*/
						String noProductImageUrl="/system/noProductImage.png";
						productResult.setPicPath(noProductImageUrl);
						continue;
					}
					BaseProduct baseProduct=new BaseProduct();
					int proId=usedProduct.getProductId() != null ? Integer.valueOf(usedProduct.getProductId()) : -1;
					if(StringUtils.equals("1", cacheEnable)){
						baseProduct = BaseProductCacher.get(proId);
					}else{
						baseProduct = baseProductService.get(proId);
					}
					productResult.setProductId(proId);
					productResult.setProductCode(usedProduct.getProductCode());
					productResult.setProductName(usedProduct.getProductName());
					productResult.setPicPath(usedProduct.getProductPath());
					productResult.setCollectState(usedProduct.getCollectState());
					productResult.setMaterialPicId(baseProduct==null?"-1":baseProduct.getMaterialPicIds());
					productResult.setGmtCreate(usedProduct.getGmtCreate());
					//获取需要自动携带白模产品的配置
					Map<String,AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
					String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app","design.designPlan.autoCarryBaimoProducts","");
					if( StringUtils.isNotBlank(autoCarryBaimoProducrsConfig) ){
						JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
						try {
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
					productResult = baseProductService.decorationProductInfo(productResult,baseProduct, designPlan, designPlanProduct, autoCarryMap, request);
					productResultList.add(productResult);
				}
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String usedProductsJsonList = JsonUtil.getListToJsonData(list);
				List<UserProductPlan> smallList = new JsonDataServiceImpl<UserProductPlan>()
						.getJsonToBeanList(usedProductsJsonList, UserProductPlan.class);
				return new ResponseEnvelope<UserProductPlan>(total, smallList, usedProductsSearch.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductPlan>(false, "数据异常!", usedProductsSearch.getMsgId());
		}
		Long endTime = System.currentTimeMillis();
		////System.out.println("times:"+(endTime-startTime));
		return new ResponseEnvelope<CategoryProductResult>(total, productResultList, usedProductsSearch.getMsgId());
	}


	/**
	 * 方案已使用组合列表
	 * @autroh xiaoxc
	 * @param designPlanId
	 * @param start
	 * @param limit
	 * @param msgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPlanGroupList")
	@ResponseBody
	public ResponseEnvelope getPlanGroupList(Integer designPlanId,Integer start,Integer limit,
							Integer spaceCommonId,Integer groupId, String msgId, HttpServletRequest request){

		if(StringUtils.isBlank(msgId)){
			return new ResponseEnvelope<>(false,"缺少参数msgId",msgId);
		}
		if(designPlanId == null || designPlanId <= 0){
			return new ResponseEnvelope<>(false,"缺少参数designPlanId",msgId);
		}
		if (start == null || start <= 0) {
			start = 0;
		}
		if (limit == null || limit <= 0) {
			limit = 20;
		}
		String mediaType = SystemCommonUtil.getMediaType(request);
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return groupProductService.getPlanGroupList(designPlanId,start,limit,loginUser,mediaType,msgId,spaceCommonId,groupId);
	}

}
