package com.nork.design.controller.web;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.param.ParamCommonVerification;

import com.nork.pay.alipay.model.hb.Product;
import com.nork.product.common.PlatformConstants;
import com.nork.product.model.*;
import com.nork.product.service.*;
import com.nork.user.model.UserTypeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.async.ProductSaveParameter;
import com.nork.common.async.ProductSaveTask;
import com.nork.common.async.Result;
import com.nork.common.async.TaskExecutor;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.ClassReflectionUtils;
import com.nork.common.util.Utils;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.cache.UsedProductsCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductRenderScene;
import com.nork.design.model.DesignPlanProductResult;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.design.model.UsedProducts;
import com.nork.design.model.constant.DesignPlanType;
import com.nork.design.model.search.DesignPlanProductSearch;
import com.nork.design.model.small.DesignPlanProductSmall;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.UsedProductsService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.cache.UserProductCollectCacher;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Title: WebDesignPlanProductController.java
 * @Package com.nork.design.controller.web
 * @Description:设计方案-设计方案产品库Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-26 11:26:11
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanProduct")
public class WebDesignPlanProductController {
	private static Logger logger = Logger
			.getLogger(WebDesignPlanProductController.class);
	private final JsonDataServiceImpl<DesignPlanProduct> JsonUtil = new JsonDataServiceImpl<DesignPlanProduct>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/design/designPlanProduct";
	private final String BASEMAIN = "/" + STYLE
			+ "/design/base/designPlanProduct";
	private final String JSPMAIN = "/" + JSPSTYLE + "/design/designPlanProduct";
	@Resource
	private SysUserService sysUserService;
	@Resource
	private UsedProductsService usedProductsService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private UserProductCollectService userProductCollectService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private UserProductConfigService userProductConfigService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private ProductUsageCountService productUsageCountService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	@Autowired
	private ResDesignService resDesignService;
	@Autowired
	private ProductPlatformService productPlatformService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 设计方案产品库列表
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getPlanProductList")
	public Object getPlanProductList(
			@ModelAttribute("designPlanProduct") DesignPlanProduct designPlanProduct,
			HttpServletRequest request) {
		if (designPlanProduct == null) {
			return new ResponseEnvelope<DesignPlanProduct>(false, "参数异常!");
		}
		int total = 0;
		List<DesignPlanProductResult> planProductlist = new ArrayList<DesignPlanProductResult>();
		try {
			total = designPlanProductService
					.planProductCount(designPlanProduct);
			planProductlist = designPlanProductService
					.planProductList(designPlanProduct);
			if (planProductlist != null && planProductlist.size() > 0) {
				for (DesignPlanProductResult planProduct : planProductlist) {
					// 产品收藏状态
					UserProductCollect productCollect = new UserProductCollect();
					LoginUser loginUser = new LoginUser();
					if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
							|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
						loginUser.setId(-1);
						loginUser.setLoginName("nologin");
					} else {
						loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
					}
					productCollect.setProductId(planProduct.getProductId());
					productCollect.setUserId(loginUser.getId());
					List<UserProductCollect> collectList = userProductCollectService
							.getList(productCollect);
					if (collectList != null && collectList.size() > 0) {
						planProduct.setCollectState(1);
					} else {
						planProduct.setCollectState(0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanProductResult>(false, "数据异常!");
		}

		request.setAttribute("total", total);
		request.setAttribute("proudctList", planProductlist);
		return "/online/newDesign/design_product";
		// return new
		// ResponseEnvelope<DesignPlanProductResult>(total,planProductlist);
	}

	/**
	 * 获取结算列表
	 * 
	 * @param designPlanProduct
	 * @return
	 */
	@RequestMapping(value = "/costList")
	public Object costList(DesignPlanProduct designPlanProduct,
			HttpServletRequest request) {
		if (designPlanProduct.getPlanId() == null) {
			return new ResponseEnvelope<ProductsCost>(false, "planId不能为空!",
					designPlanProduct.getMsgId());
		}
		// 获取当前登录用户信息
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
			return new ResponseEnvelope<ProductsCost>(false, "当前登录用户超时，请重新登录!",
					designPlanProduct.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			designPlanProduct.setUserId(loginUser.getId());
		}
		int total = designPlanProductService.costListCount(designPlanProduct);
		List<ProductsCost> costList = new ArrayList<ProductsCost>();
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		BigDecimal totalAmount = new BigDecimal(0);
		if (total > 0) {
			// 得到结算汇总清单
//			costList = designPlanProductService.costList(designPlanProduct);
			List<ProductCostDetail> costDetails = null;
			for (ProductsCost cost : costList) {
				// 得到结算详情清单
				cost.setPlanId(designPlanProduct.getPlanId());
				costDetails = designPlanProductService.costDetail(cost);
				cost.setDetailList(costDetails);
				totalAmount = totalAmount.add(cost.getTotalPrice());
			}
		}
		request.setAttribute("costList", costList);
		request.setAttribute("totalAmount", numberFormat.format(totalAmount));
		return Utils.getPageUrl(request,
				"/online/design/designPlanCost/designPlanCost_list");
	}

	/**
	 * 获取结算列表--接口
	 * 
	 * @param designPlanProduct
	 * @return
	 */
	@RequestMapping(value = "/costListWeb")
	@ResponseBody
	public Object costListWeb(DesignPlanProduct designPlanProduct,
			String terminalImei, HttpServletRequest request) {
		/** 验证参数 */
		if (designPlanProduct.getPlanId() == null) {
			return new ResponseEnvelope<ProductsCost>(false, "planId不能为空!",designPlanProduct.getMsgId());
		}
		if (StringUtils.isBlank(terminalImei)) {
			return new ResponseEnvelope<ProductsCost>(false,"terminalImei不能为空!", designPlanProduct.getMsgId());
		}

		/** 获取登录用户信息 */
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
			return new ResponseEnvelope<ProductsCost>(false, "当前登录用户超时，请重新登录!",designPlanProduct.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			//传入查询条件(userId)
			designPlanProduct.setUserId(loginUser.getId());
		}
		
		
		/** 获取用户所属品牌 */
		BaseBrand baseBrand = null;
		String beandIds = "";
        if(2==loginUser.getUserType()){//企业用户
        	baseBrand = baseBrandService.findBrandIdByUserIdBase(loginUser);
        }else if (3==loginUser.getUserType()){
        	baseBrand = baseBrandService.findBrandIdByUserIdAndUserType(loginUser);
        }


        /** 品牌逻辑处理 */
		boolean flag = false;
		List list= null;
		if(null != baseBrand){
			if(StringUtils.isNotBlank(baseBrand.getBrandStr())){
			
				beandIds = baseBrand.getBrandStr();
				String[] strs = beandIds.split(",");
				//将字符串数组转换成集合list
				list=Arrays.asList(strs);
				BaseBrand baseBrandInfo = baseBrandService.findBrandInfoByUserId(baseBrand.getBrandStr());
				if(null != baseBrandInfo){
				   if(null != baseBrandInfo.getBrandReferred()){
					   String[] ids = baseBrandInfo.getBrandReferred().split(",");
					   for(int i=0;i<ids.length;i++){
						   if(Utils.isSanduUser(ids[i])){//判断是否是三度品牌
								flag = true;
							}
					   }
				   }
				}
			}
		}

		//只过滤2 和3 的用户
		if(1 == loginUser.getUserType() || 4 <= loginUser.getUserType()){//判断是否为内部用户与其他用户（非厂商与经销商）也不做过滤
			flag = true;
		}

		List<String> li = null;
		if(!flag){
			designPlanProduct.setStatus("Y");
			if(null != baseBrand){
				designPlanProduct.setBrandsStr(baseBrand.getBrandStr());//按照品牌过滤
			}
			li = designPlanProductService.scopeProductByuserId(loginUser.getId());
		}

		/** 判断用户具有的产品状态权限 并 赋值 */
		List<Integer> putawayState = new ArrayList<>(3);
		putawayState.add(ProductStatuCode.HAS_BEEN_RELEASE);
		if (UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType())) {
			putawayState.add(ProductStatuCode.HAS_BEEN_PUTAWAY);
			putawayState.add(ProductStatuCode.TESTING);
		}
		designPlanProduct.setProductPutawayStateList(putawayState);
		designPlanProduct.setUserId(loginUser.getId());


		/** 创建价格变量 */
		BigDecimal totalPriceOne = new BigDecimal(0);	//第一层结算类型清单总价
		BigDecimal totalPriceTwo = new BigDecimal(0);	//第二层结算类型清单总价

		/** 判断是否进行品牌过滤 */
		if(flag){
			List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
			List<ProductsCost> costList = new ArrayList<ProductsCost>();

			/** 获取方案产品的结算类型数量 */
			int total = designPlanProductService.costTypeListCount(designPlanProduct);
			if( total > 0 ){

				//获取数字字典配置的价格单位
				SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");

				/** 获取方案产品的结算类型列表 */
				costTypeList = designPlanProductService.costTypeList(designPlanProduct);

				for(ProductsCostType costType : costTypeList){
					totalPriceOne = new BigDecimal(0);	//第一层结算类型清单总价
					List<ProductCostDetail> costDetails = null;
					int productCount = 0 ;
					List<String> matchingList = new ArrayList<String>();

					/** 获取该结算类型下的结算类型清单 */
					costType.setPlanId(designPlanProduct.getPlanId());
					costType.setUserId(loginUser.getId());
					costType.setProductPutawayState(putawayState);
					costList = designPlanProductService.costList(costType);

					for (ProductsCost cost : costList) {
						totalPriceTwo = new BigDecimal(0);	//第二层结算类型清单总价
						productCount += cost.getProductCount();

						cost.setPlanId(designPlanProduct.getPlanId());
						cost.setUserId(loginUser.getId());
						cost.setAuthorizedConfigList(list);
						if( dictionary != null ){
							cost.setSalePriceValueName(dictionary.getName());
						}
						cost.setProductPutawayState(putawayState);
						cost.setUserId(loginUser.getId());//用户id

						/** 获取该结算类型下的产品清单 */
						costDetails = designPlanProductService.costDetail(cost);
						if(costDetails!=null&&costDetails.size()>0){

							//保密产品，只对所属企业用户可见
							if(loginUser.getUserType() != 1){
								List<ProductCostDetail> costDetailsTemp = new ArrayList<ProductCostDetail>();
								for (ProductCostDetail costDetail : costDetails) {
									if(costDetail.getSecrecyFlag() == 1){	//保密
										if(StringUtils.isNotEmpty(beandIds)){
											if(beandIds.contains(costDetail.getBrandId()+"")){	//员工所属企业品牌是否包含产品品牌
												costDetailsTemp.add(costDetail);
											}else{
												productCount -= 1;
											}
										}
									}else{
										costDetailsTemp.add(costDetail);
									}
								}
								costDetails = costDetailsTemp;
							}

							for (ProductCostDetail productCostDetail : costDetails) {
								/** 获取产品平台个性化信息 */
								productPlatformService.getProductCostDetailInfo(productCostDetail.getProductId(),PlatformConstants.PC_2B,productCostDetail);
								totalPriceTwo = totalPriceTwo.add(null != productCostDetail.getTotalPrice() ? productCostDetail.getTotalPrice() : new BigDecimal(0));	//计算第二层类型总价
							}

						}else{
							productCount -= 1;
							matchingList.add(cost.getCostTypeCode());
						}

						//第二层类型清单 赋值 产品列表、总价
						cost.setDetailList(costDetails);
						cost.setTotalPrice(totalPriceTwo);

						//计算第一层总价
						totalPriceOne = totalPriceOne.add(cost.getTotalPrice());
						//地面 墙面 天花的 价格 从总价中 去除 并 当前类型清单 总价为null
						if("1".equals(cost.getCostTypeValue())||"2".equals(cost.getCostTypeValue())||"3".equals(cost.getCostTypeValue())){
							totalPriceOne = totalPriceOne.subtract(cost.getTotalPrice());
							cost.setTotalPrice(null);
						}

					}

					//如果三级菜单为空，那么就删除二级菜单
					LinkedList<ProductsCost> linkedList = new LinkedList<ProductsCost>(costList);
					for(ProductsCost lis : linkedList){
					 for(String ls :matchingList){
							if(lis.getCostTypeCode().equals(ls)){
								costList.remove(lis);
							}
						}
					}

					//第一层类型清单 赋值 第二层类型清单、第一层总价、产品数量、价格单位名称
					costType.setDetailList(costList);
					costType.setTotalPrice(totalPriceOne);
					costType.setProductCount(productCount);
					costType.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());

				}
			}
			return new ResponseEnvelope<ProductsCostType>(total, costTypeList,designPlanProduct.getMsgId());

		}else if(null != li && li.size() > 0){

			List<String> matchingStairList = new ArrayList<String>();//如果二级菜单为空那么就删除一级级菜单
			List<String> matchingStairTwoList = new ArrayList<String>();//如果三级菜单为空那么就删除二级菜单
			List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
			List<ProductsCost> costList = new ArrayList<ProductsCost>();

			/** 赋值参数进行过滤 */
			designPlanProduct.setAtt2List(li);
			designPlanProduct.setUserId(loginUser.getId());
			/** 获取方案产品的结算类型数量 */
			int total = designPlanProductService.costTypeListCount(designPlanProduct);

			if( total > 0 ){
				//获取数字字典配置的价格单位
				SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");

				/** 获取方案产品的结算类型列表 */
				costTypeList = designPlanProductService.costTypeList(designPlanProduct);

				for(ProductsCostType costType : costTypeList){
					totalPriceOne = new BigDecimal(0);	//第一层结算类型清单总价

					/** 获取结算类型列表 中的 结算类型列表 */
					costType.setPlanId(designPlanProduct.getPlanId());
					costType.setUserId(loginUser.getId());
					costType.setProductPutawayState(putawayState);
					if(!flag){
						if(null != baseBrand){
							costType.setBrandsStr(baseBrand.getBrandStr());//按照品牌过滤
						}
					}
					costList = designPlanProductService.costList(costType);

					//如果二级级菜单为空，那么就删除一级菜单
					if(costList.size() < 1){
						matchingStairList.add(costType.getCostCodes());
					}

					int productCount = 0 ;
					List<ProductCostDetail> costDetails = null;
					List<String> matchingList = new ArrayList<String>();//

					for (ProductsCost cost : costList) {
						totalPriceTwo = new BigDecimal(0);	//第二层结算类型清单总价
						productCount += cost.getProductCount();

						/** 获取第三层产品里列表清单 */
						cost.setPlanId(designPlanProduct.getPlanId());
						cost.setUserId(loginUser.getId());
						//如果绑定三度或者是内部则产品清单不做过滤
						if(!flag){
							cost.setStatus("Y");
							if(null != baseBrand){
								cost.setBrandsStr(baseBrand.getBrandStr());//按照品牌过滤
							}
						}
						cost.setAuthorizedConfigList(list);
						if( dictionary != null ){
							cost.setSalePriceValueName(dictionary.getName());
						}
						cost.setProductPutawayState(putawayState);
						cost.setUserId(loginUser.getId());//用户id
						costDetails = designPlanProductService.costDetail(cost);

						if(costDetails!=null&&costDetails.size()>0){

							//保密产品，只对所属企业用户可见
							if(loginUser.getUserType() != 1){
								List<ProductCostDetail> costDetailsTemp = new ArrayList<ProductCostDetail>();
								for (ProductCostDetail costDetail : costDetails) {
									if(costDetail.getSecrecyFlag() == 1){	//保密
										if(StringUtils.isNotEmpty(beandIds)){
											if(beandIds.contains(costDetail.getBrandId()+"")){	//员工所属企业品牌是否包含产品品牌
												costDetailsTemp.add(costDetail);
											}else{
												productCount -= 1;
											}
										}
									}else{
										costDetailsTemp.add(costDetail);
									}
								}
								costDetails = costDetailsTemp;
							}


							for (ProductCostDetail productCostDetail : costDetails) {
								/** 获取产品平台个性化信息 */
								productPlatformService.getProductCostDetailInfo(productCostDetail.getProductId(),PlatformConstants.PC_2B,productCostDetail);
								totalPriceTwo = totalPriceTwo.add(null != productCostDetail.getTotalPrice() ? productCostDetail.getTotalPrice() : new BigDecimal(0));	//计算第二层类型总价
							}

						}else{
							//如果三级菜单为空，那么就删除二级菜单
							productCount -= 1;
							matchingList.add(cost.getCostTypeCode());
						}

						//第二层类型清单 赋值 产品列表、总价
						if(costDetails.size()>0){
							cost.setDetailList(costDetails);
						}
						cost.setTotalPrice(totalPriceTwo);

						//计算第一层总价
						totalPriceOne = totalPriceOne.add(cost.getTotalPrice());
						//地面 墙面 天花的 价格 从总价中 去除 并 当前类型清单 总价为null
						if("1".equals(cost.getCostTypeValue())||"2".equals(cost.getCostTypeValue())||"3".equals(cost.getCostTypeValue())){
							totalPriceOne = totalPriceOne.subtract(cost.getTotalPrice());
							cost.setTotalPrice(null);
						}

					}

					//如果三级菜单为空，那么就删除二级菜单
					 LinkedList<ProductsCost> linkedList = new LinkedList<ProductsCost>(costList);
					 for(ProductsCost lis : linkedList){
						 for(String ls :matchingList){
								if(lis.getCostTypeCode().equals(ls)){
									costList.remove(lis);
								}
							}
					 }


					//第一层类型清单 赋值 第二层类型清单、第一层总价、产品数量、价格单位名称
					costType.setDetailList(costList);
					costType.setTotalPrice(totalPriceOne);
					costType.setProductCount(productCount);
					costType.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
					
				}
			}


			//如果二级菜单为空，那么就删除一级菜单
			if(costList.size() < 1){
				LinkedList<ProductsCostType> linkedLists = new LinkedList<ProductsCostType>(costTypeList);
 				for(ProductsCostType lis : linkedLists){
	 				for(String ls : matchingStairList){
		 				if(lis.getCostCodes().equals(ls)){
			 				costTypeList.remove(lis);
						}
					}
 				}
			}

			return new ResponseEnvelope<ProductsCostType>(total, costTypeList,designPlanProduct.getMsgId());

		}else{

			return new ResponseEnvelope<ProductsCostType>(0, null,designPlanProduct.getMsgId());

		}
		
	}

	/**
	 * 设计方案已删除产品列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletedList")
	@ResponseBody
	public Object deletedList(
			@PathVariable String style,
			@ModelAttribute("designPlanProductSearch") DesignPlanProductSearch designPlanProductSearch,
			Integer spaceCommonId, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(designPlanProductSearch.getMsgId())) {
			return new ResponseEnvelope<DesignPlanProduct>(false, "msgId不能为空!", designPlanProductSearch.getMsgId());
		}
		if (designPlanProductSearch.getDesignPlanId() == null
				|| designPlanProductSearch.getDesignPlanId().intValue() < 0) {
			return new ResponseEnvelope<DesignPlanProduct>(false, "designPlanId不能为空!", designPlanProductSearch.getMsgId());
		}
		/** 登录用户信息 */
		LoginUser loginUser = new LoginUser();
        if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
            loginUser.setId(-1);
            loginUser.setLoginName("nologin");
        } else {
            loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
        }
		/* 此处需要修改，将数据库中planId修改成designPlanId后,删除该条赋值 */
		designPlanProductSearch.setPlanId(designPlanProductSearch.getDesignPlanId());

		/* 每页不同页码时使用 */
		if (designPlanProductSearch.getLimit() == null) {
			designPlanProductSearch.setLimit(new Integer(20));
		}
		designPlanProductSearch.setIsDeleted(1);

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanProductSearch = (DesignPlanProductSearch) JsonUtil
					.getJsonToBean(jsonStr, DesignPlanProductSearch.class);
			if (designPlanProductSearch == null) {
				return new ResponseEnvelope<DesignPlanProduct>(false, "传参异常!", "none");
			}
		}
		DesignPlan designPlan=null;
		if(Utils.enableRedisCache()){
			designPlan = DesignPlanCacher.getDesignPlan(designPlanProductSearch.getDesignPlanId());
		}else{
			designPlan = designPlanService.get(designPlanProductSearch.getDesignPlanId());
		}
		if( designPlan==null ){
			return new ResponseEnvelope<DesignPlanProduct>(false, "找不到设计方案 ！PlanID="+designPlanProductSearch.getDesignPlanId(), designPlanProductSearch.getMsgId());
		}
		/** 媒介类型.如果没有值，则表示为web前端（2） */
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);


		List<DesignPlanProduct> list = new ArrayList<DesignPlanProduct>();
		int total = 0;
		try {
			/**根据修改时间排序*/
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if( loginUser.getUserType()==1 && "2".equals(versionType)){
				designPlanProductSearch.setIsInternalUser("yes");
	        }
			total =designPlanProductService.getCount(designPlanProductSearch);			

			logger.info("total:" + total);
			if (total > 0) {
				/**  默认修改时间排序 **/
				list = designPlanProductService.getPaginatedList(designPlanProductSearch);
			}
			if (list != null && list.size() > 0) {
				for (DesignPlanProduct dpp : list) {
					Integer productId = dpp.getProductId();
					BaseProduct baseProduct=null;
					if(Utils.enableRedisCache()){
						baseProduct = DesignPlanProductCacher.getBaseProduct(productId);
					}else{
						baseProduct = baseProductService.get(productId);
					}
					Map<String,String> map = new HashMap<String,String>();
					if (baseProduct != null) {

						/** 获取产品平台个性化信息 */
						productPlatformService.getDeleteBaseProductInfo(baseProduct.getId(),PlatformConstants.PC_2B,baseProduct);

						/*填充基础数据*/
						dpp.setProductCode(baseProduct.getProductCode());
						dpp.setProductName(baseProduct.getProductName());
						dpp.setProductSmallPicPath(baseProduct.getPicPath());

						/* 产品类型  */
						Integer productType = baseProduct.getProductTypeValue() == null ? null : Integer.valueOf(baseProduct.getProductTypeValue());
						SysDictionary sysDictionary = new SysDictionary();
						if (productType != null) {
							dpp.setProductType(productType);
							sysDictionary = sysDictionaryService.getSysDictionaryByValue("productType",productType);
							dpp.setProductTypeName(sysDictionary.getName());
							dpp.setProductTypeCode(sysDictionary.getValuekey());
						}
						SysDictionary dictionary = null;
						if (productType != null&& baseProduct.getProductSmallTypeValue() != null) {
							dictionary = sysDictionaryService.getSysDictionaryByValue(sysDictionary.getValuekey(), baseProduct.getProductSmallTypeValue());
							if( dictionary != null ){
								dpp.setProductSmallTypeCode(dictionary.getValuekey());
								dpp.setProductSmallTypeName(dictionary.getName());
								dpp.setProductSmallTypeValue(dictionary.getValue());
								String rootType = StringUtils.isEmpty(dictionary.getAtt1()) ? "2" : dictionary.getAtt1().trim();
								dpp.setRootType(rootType);
							}
						}
						dpp.setParentProductId(baseProduct.getParentId());
						dpp.setProductLength(baseProduct.getProductLength());
						dpp.setProductWidth(baseProduct.getProductWidth());
						dpp.setProductHeight(baseProduct.getProductHeight());
						// bgWall:是否是背景墙
						Integer bgWallValue = baseProductService.getBgWallValue(sysDictionary==null?"":sysDictionary.getValuekey(),dictionary==null?"":dictionary.getValuekey());
						dpp.setBgWall(bgWallValue);
						/*填充材质信息*/
						/*处理拆分材质产品返回默认材质*/
						String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
						Integer isSplit=0;
						List<SplitTextureDTO> splitTextureDTOList=null;
						if(StringUtils.isNotBlank(splitTexturesInfo)){
							Map<String,Object> map1=baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
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
									/*productResult.setTextureAttrValue(resTexture.getTextureAttrValue());
									productResult.setLaymodes(resTexture.getLaymodes());
									productResult.setTextureWidth(resTexture.getFileWidth()+"");
									productResult.setTextureHeight(resTexture.getFileHeight()+"");*/
									splitTextureDTOList=new ArrayList<SplitTextureDTO>();
									List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
									SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
									ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
									resTextureDTO.setKey(splitTextureDTO.getKey());
									resTextureDTO.setProductId(baseProduct.getId());
									resTextureDTOList.add(resTextureDTO);
									splitTextureDTO.setList(resTextureDTOList);
									splitTextureDTOList.add(splitTextureDTO);
									/*同时存一份数据在老的数据结构上*/
									String[] materialPathList=new String[]{resTextureDTO.getPath()};
									dpp.setMaterialPicPaths(materialPathList);
									/*同时存一份数据在老的数据结构上->end*/
								}
							}
						}
						dpp.setSplitTexturesChoose(splitTextureDTOList);
						dpp.setIsSplit(isSplit);
						/*填充材质信息->end*/
						/*if (StringUtils.isNotBlank(materialPicIds)) {
							String[] mIds = materialPicIds.split(",");
							if (mIds != null) {
								ResPic resPic = new ResPic();
								resPic.setFileKey("product.baseProduct.material");
								resPic.setBusinessId(baseProduct.getId());
								resPic.setOrders(" sequence asc ");
								List<ResPic> materialPics=null;
								if(StringUtils.equals("1", cacheEnable)){
									materialPics = ResourceCacher.getAllPicList(resPic);
								}else{
									materialPics = resPicService.getList(resPic);
								}
								
								if (materialPics != null
										&& materialPics.size() > 0) {
									String[] materialPaths = new String[materialPics
											.size()];
									for (int i = 0; i < materialPics.size(); i++) {
										resPic = materialPics.get(i);
										if (resPic != null) {
											materialPaths[i] = resPic
													.getPicPath();
										}
									}
									baseProduct.setMaterialPicPaths(materialPaths);
								}
							}
						}*/
						// 材质配置文件路径
						Integer materialConfigId = baseProduct.getMaterialFileId();
						if (materialConfigId != null) {
							ResFile resFile=null;
							if(Utils.enableRedisCache()){
								resFile = ResourceCacher.getFile(materialConfigId);
							}else{
								resFile = resFileService.get(materialConfigId);
							}
							
							if (resFile != null) {
								baseProduct.setMaterialConfigPath(resFile.getFilePath());
							}
						}
						// u3d模型路径
						String u3dModelId = baseProductService.getU3dModelId(mediaType, baseProduct);
						ResModel resModel=null;
						if(Utils.enableRedisCache()){
							resModel = ResourceCacher.getModel(StringUtils.isBlank(u3dModelId) ? -1 : Integer.valueOf(u3dModelId));
						}else{
							resModel =resModelService.get(StringUtils.isBlank(u3dModelId)?-1:Integer.valueOf(u3dModelId));
						}
						if (resModel != null) {
							dpp.setU3dModelPath(resModel.getModelPath());
						}
						// 产品品牌
						Integer brandId = baseProduct.getBrandId();
						if (brandId != null) {
							BaseBrand baseBrand=null;
							if(Utils.enableRedisCache()){
								baseBrand = BaseBrandCacher.get(brandId);
							}else{
								baseBrand = baseBrandService.get(brandId);
							}
							if (baseBrand != null) {
								dpp.setBrandName(baseBrand.getBrandName());
							}
						}
						// 产品价格
						dpp.setSalePrice(baseProduct.getSalePrice());
						
						 /***在组合产品查询列表 中 增加产品属性****/
						map = productAttributeService.getPropertyMap(baseProduct.getId());
						baseProduct.setPropertyMap(map);
						dpp.setPropertyMap(map);
					}

					Integer planProductId = dpp.getPlanProductId();
					if (planProductId != null && planProductId.intValue() > 0) {
						DesignTempletProduct designTempletProduct=null;
						if(Utils.enableRedisCache()){
							designTempletProduct = DesignPlanProductCacher.getDesignTempletProductService(planProductId);
						}else{
							designTempletProduct = designTempletProductService.get(planProductId);
						}
						
						dpp.setPosIndexPath(designTempletProduct.getPosIndexPath());
						dpp.setPosName(designTempletProduct.getPosName());
					}
					dpp.setDesignPlanProductId(dpp.getId());

					DesignTemplet designTemplet = new DesignTemplet();
					if (designPlan != null) {
						if(Utils.enableRedisCache()){
							designTemplet = DesignPlanCacher.getDesignTemplet(designPlan.getDesignTemplateId());
						}else{
							designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
						}
						
					}
					/** 组装产品的规则 */
					Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(), dpp.getProductTypeCode(), dpp
									.getProductSmallTypeCode(), spaceCommonId,
									designTemplet == null ? null
											: designTemplet.getId(),
									new DesignRules(),map);
					dpp.setRulesMap(rulesMap);
					// 白膜属性 ->start
					/*Map<String,CategoryProductResult> baimoRule = baseProductService.setbaimoRuleMap(spaceCommonId, request, loginUser.getId(), dpp.getProductSmallTypeCode(), map, designPlan);*/
					Map<String,CategoryProductResult> baimoRule = baseProductService.setbaimoRuleMap(spaceCommonId, request, loginUser.getId(), dpp.getProductSmallTypeCode(), designPlan, map);
					dpp.setBasicModelMap(baimoRule);
					// 白膜属性 ->end
				}
				
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String designPlanProductJsonList = JsonUtil.getListToJsonData(list);
				List<DesignPlanProductSmall> smallList = new JsonDataServiceImpl<DesignPlanProductSmall>().getJsonToBeanList(designPlanProductJsonList, DesignPlanProductSmall.class);
				return new ResponseEnvelope<DesignPlanProductSmall>(total, smallList, designPlanProductSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanProduct>(false, "数据异常!", designPlanProductSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanProduct>(total, list, designPlanProductSearch.getMsgId());
	}

	/**
	 * 设计方案产品库列表接口
	 */
	@RequestMapping(value = "/designPlanProductList_old")
	@ResponseBody
	public Object designPlanProductList_old(
			@PathVariable String style,
			@ModelAttribute("designPlanProduct") DesignPlanProduct designPlanProduct,
			HttpServletRequest request, HttpServletResponse response) {
		Long startTime = System.currentTimeMillis();
		List<DesignPlanProductResult> planProductlist = new ArrayList<DesignPlanProductResult>();
		int total = 0;
		/** 同类合并，装载用的list */
		List<DesignPlanProductResult> planProductlistNew = new ArrayList<DesignPlanProductResult>();

		String msg = "";
		if (StringUtils.isBlank(designPlanProduct.getMsgId())) {
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<DesignPlanProduct>(false, msg,
					designPlanProduct.getMsgId());
		}
		if (designPlanProduct.getPlanId() == null) {
			msg = "参数planId不能为空！";
			return new ResponseEnvelope<DesignPlanProduct>(false, msg,
					designPlanProduct.getMsgId());
		}
		
		
		try {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setId(-1);
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}
			
			StringBuffer sb= new StringBuffer("");
			/**包含着 品牌id的数组*/
			String  arr[] = null;    
			/**该产品关联的品牌id*/
			List<AuthorizedConfig> AuthorizedConfigList = new ArrayList<AuthorizedConfig>();
			/**通过userId 查询 该用户通过序列号 添加的所有产品*/
			AuthorizedConfigSearch authorizedConfigtSearch= new AuthorizedConfigSearch();
			authorizedConfigtSearch.setUserId(loginUser.getId());
			AuthorizedConfigList=authorizedConfigService.getPaginatedList(authorizedConfigtSearch);
			
			/**将品牌的id 全部拼接成一个StringBuffer*/
			if(AuthorizedConfigList!=null&&AuthorizedConfigList.size()>0){
				for(int i=0;i<AuthorizedConfigList.size();i++){
					String brandIds=AuthorizedConfigList.get(i).getBrandIds();
					if(brandIds.contains(",")){
						if(i==AuthorizedConfigList.size()-1){
							sb.append(brandIds);
						}
						if(i!=AuthorizedConfigList.size()-1){
							sb.append(brandIds+",");
						}
					}
					if(!brandIds.contains(",")){
						if(i==AuthorizedConfigList.size()-1){
							sb.append(brandIds);
						}
						if(i!=AuthorizedConfigList.size()-1){
							sb.append(brandIds+",");
						}
					}
				}
			}
			if(sb!=null&&!"".equals(sb)){
				arr=sb.toString().split(",");
			}
			
			
			designPlanProduct.setIsDeleted(0);
			if(Utils.enableRedisCache()){
				total = DesignPlanProductCacher.getPlanProductCount(designPlanProduct);
				planProductlist = DesignPlanProductCacher.getPlanProductList(designPlanProduct);
			}else{
				total = designPlanProductService.planProductCount(designPlanProduct);
				planProductlist = designPlanProductService.planProductList(designPlanProduct);
			}

			if (planProductlist != null && planProductlist.size() > 0) {
				for (DesignPlanProductResult planProduct : planProductlist) {
					/**给产品带上价格单位*/
					Integer productId = planProduct.getProductId();
					if(productId != null){
						BaseProduct baseProduct = baseProductService.get(productId);
						Integer salePriceValue = baseProduct.getSalePriceValue();
						if(salePriceValue != null){
							SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice", new Integer(salePriceValue));
							planProduct.setSalePriceValueName(dictionary==null?"":dictionary.getName());
						}
					}
					/**品牌是否被绑定状态*/
					int priceState=0;
					Integer ProductBrandId=planProduct.getBrandId();
					if(arr==null){
						planProduct.setSalePrice("-1.0");
					}
					if(arr!=null){
						for (String brandId : arr) {
							if(brandId.equals(ProductBrandId+"")){
								priceState=priceState+1;
								break;
							}
						}
					}
					if(priceState<=0){
						planProduct.setSalePrice("-1.0");
					}
					
					/** 产品收藏状态 */
					UserProductCollect productCollect = new UserProductCollect();
					
					productCollect.setProductId(planProduct.getProductId());
					productCollect.setUserId(loginUser.getId());
					List<UserProductCollect> collectList=null;
					if(Utils.enableRedisCache()){
						collectList = UserProductCollectCacher.getList(productCollect);
					}else{
						collectList = userProductCollectService.getList(productCollect);
					}

					if (collectList != null && collectList.size() > 0) {
						planProduct.setCollectState(1);
					} else {
						planProduct.setCollectState(0);
					}
					
				}
				
				/***********************************/
				/** 判断是否有重复类型产品，如果超过两件数量加1 */
				/***********************************/
				for (DesignPlanProductResult planProductOne : planProductlist) {

					String ProductCode = planProductOne.getProductCode();
					if (ProductCode.indexOf("baimo") == 0) {
						continue;
					}
					Integer ProductId = planProductOne.getProductId();
					/** 取得商品的id */
					int ProductNum = 0;
					/** 商品数量 */

					for (DesignPlanProductResult planProductTwo : planProductlist) {
						/** 获得同样类型商品数量 */
						int ProductIdOne = planProductTwo.getProductId();
						if (ProductId == ProductIdOne) {
							ProductNum = ProductNum + 1;
							/** 再次循环此列表，id相同说明产品相同， 进入一次就加1 */
						}
					}

					planProductOne.setCount(ProductNum);
					/** 加入商品数量 */

					int num = 0;
					for (DesignPlanProductResult planProductThree : planProductlistNew) {
						/** 去除重复产品 */
						int ProductIdTwo = planProductThree.getProductId();
						if (ProductId == ProductIdTwo) {
							/** 查询planProductlistNew 里有没有相同的id，如果有那么产品相同，不添加 */
							num = num + 1;
						}
					}

					if (num <= 0) {
						planProductlistNew.add(planProductOne);
						/** 没有相同的id，则此产品不重复，添加 */
					}
				}
				/***********************************/
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!",
					designPlanProduct.getMsgId());
		}

		//System.out.println("times:" + (System.currentTimeMillis() - startTime));
		return new ResponseEnvelope<DesignPlanProductResult>(total,
				planProductlistNew, designPlanProduct.getMsgId());
	}
	
	
	
	
	
	/**
	 * 设计方案产品库列表接口
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/designPlanProductList")
	@ResponseBody
	public Object designPlanProductList(
			@PathVariable String style, @ModelAttribute("designPlanProduct") DesignPlanProduct designPlanProduct,
			Integer designPlanType, HttpServletRequest request, HttpServletResponse response) {

		List<DesignPlanProductResult> planProductlist = new ArrayList<DesignPlanProductResult>();
		if (StringUtils.isBlank(designPlanProduct.getMsgId())) {
			return new ResponseEnvelope<DesignPlanProduct>(false, "参数msgId不能为空！", designPlanProduct.getMsgId());
		}
		if (designPlanProduct.getPlanId() == null) {
			return new ResponseEnvelope<DesignPlanProduct>(false, "参数planId不能为空！", designPlanProduct.getMsgId());
		}
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null) {
            return new ResponseEnvelope<DesignPlanProduct>(false, SystemCommonConstant.PLEASE_LOGIN, designPlanProduct.getMsgId());
		}
		try {
			String  brandIdList[] = this.getBrandIds(loginUser.getId(),loginUser.getUserType());
			StringBuffer brandIdBuffer = null;
			for (String s : brandIdList) {
				brandIdBuffer = new StringBuffer();
				brandIdBuffer.append(s+",");
			}


			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if("2".equals(versionType) && loginUser.getUserType()==1){
				designPlanProduct.setIsInternalUser("yes");
			}
			designPlanProduct.setIsDeleted(0);
			if(designPlanProduct.getGroupType()==null)
			    designPlanProduct.setGroupType(0);//查单品
			logger.info(designPlanProduct.getStart() +" -- "+designPlanProduct.getLimit()+""+designPlanProduct.getMsgId());
			if(DesignPlanType.SCENE_DESGSN_PLAN == designPlanType){
				// 走设计方案产品副本表
				DesignPlanProductRenderScene designPlanProductRenderScene = new DesignPlanProductRenderScene();
				ClassReflectionUtils.reflectionAttr(designPlanProduct, designPlanProductRenderScene);
				logger.info(designPlanProductRenderScene.getStart() +" -- "+designPlanProductRenderScene.getLimit()+""+designPlanProductRenderScene.getMsgId());
				planProductlist = designPlanProductRenderSceneService.getDesignPlanProductList(designPlanProductRenderScene);
			}else{
				planProductlist = designPlanProductService.getDesignPlanProductList(designPlanProduct);// 走设计方案产品表
			}
			if (planProductlist != null && planProductlist.size() > 0) {
				for (DesignPlanProductResult planProduct : planProductlist) {

					/** 获取产品平台个性化信息 */
					productPlatformService.getDesignPlanProductResultInfo(planProduct.getProductId(), PlatformConstants.PC_2B,planProduct);

					/*品牌是否被绑定状态*/
					this.isBinding(brandIdList,planProduct);

					/*产品收藏状态 */
					UserProductCollectSearch productCollect = new UserProductCollectSearch();
					productCollect.setProductId(planProduct.getProductId());
					productCollect.setUserId(loginUser.getId());
					Integer  collectCount = null;
					collectCount = userProductCollectService.getCount(productCollect);
					if (collectCount != null && collectCount.intValue() > 0) {
						planProduct.setCollectState(1);
					} else {
						planProduct.setCollectState(0);
					}
				}

				//保密产品，只对所属企业用户可见
				if(loginUser.getUserType() != 1){
					List<DesignPlanProductResult> listTemp = new ArrayList<DesignPlanProductResult>();
					for (DesignPlanProductResult designPlanProductResult : planProductlist) {
						if(designPlanProductResult.getSecrecyFlag() == 1){	//保密

							if(null != brandIdBuffer){
								if(brandIdBuffer.toString().contains(designPlanProductResult.getBrandId()+"")){	//员工所属企业品牌是否包含产品品牌
									listTemp.add(designPlanProductResult);
								}
							}

						}else{
							listTemp.add(designPlanProductResult);
						}
					}
					planProductlist = listTemp;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!",
					designPlanProduct.getMsgId());
		}
		int total = 0;
		if(planProductlist!=null && planProductlist.size()>0)
		    total =planProductlist.size();
		
		ResponseEnvelope responseEnvelope = new ResponseEnvelope<DesignPlanProductResult>(total,
				this.productQuantity(planProductlist), designPlanProduct.getMsgId());//total 
		return responseEnvelope;
	}
	
	
	
	/**
	 * 获取用户授权码的品牌ids
	 * @param userId
	 * @return
	 */
	public String [] getBrandIds(Integer userId,Integer type) {//2018-3-21 wutehua
		StringBuffer stringBuffer= new StringBuffer("");
		String  brandIdList[] = null;  //包含着 品牌id的数组
		//List<AuthorizedConfig> AuthorizedConfigList = new ArrayList<AuthorizedConfig>();//该产品关联的品牌id
		//AuthorizedConfigSearch authorizedConfigtSearch= new AuthorizedConfigSearch();//通过userId 查询 该用户通过序列号 添加的所有产品
		//authorizedConfigtSearch.setUserId(userId);
		//AuthorizedConfigList = authorizedConfigService.getPaginatedList(authorizedConfigtSearch);
		List<BaseBrand> baseBrand =null;
		if(2==type){//企业用户
			baseBrand = baseBrandService.findBrandIdByUserIdListBase(userId);
		}else if(3==type){//经销商
			baseBrand = baseBrandService.findBrandIdByUserIdListB(userId);
		}
		if(baseBrand!=null&&baseBrand.size()>0){//将品牌的id 全部拼接成一个StringBuffer
			for(int i=0;i<baseBrand.size();i++){
				String brandIds = baseBrand.get(i).getIdStr();
				if(i==baseBrand.size()-1){
					stringBuffer.append(brandIds);
				}else {
					stringBuffer.append(brandIds+",");
				}
			}
		}
		/*if(AuthorizedConfigList!=null&&AuthorizedConfigList.size()>0){//将品牌的id 全部拼接成一个StringBuffer
			for(int i=0;i<AuthorizedConfigList.size();i++){
				String brandIds = AuthorizedConfigList.get(i).getBrandIds();
				if(i==AuthorizedConfigList.size()-1){
					stringBuffer.append(brandIds);
				}else {
					stringBuffer.append(brandIds+",");
				}
			}
		}*/
		if(stringBuffer !=null && !"".equals(stringBuffer)){
			brandIdList = stringBuffer.toString().split(",");
		} 
		return brandIdList;
	}
	
	
	/**
	 * 品牌是否被绑定状态
	 * @param arr
	 * @param planProduct
	 */
	public void isBinding(String [] arr,DesignPlanProductResult result){
		Boolean isBinding = false;
		if(result == null) {
			return;
		}
		Integer i = result.getBrandId();
		if(arr != null && i != null) {
			String productBrandId = i.toString();
			if(StringUtils.isNotEmpty(productBrandId)) {
				for (String brandId : arr) {
					if(brandId.equals(productBrandId)){
						isBinding = true;
						break;
					}
				}
			}
		}
		if(!isBinding) {
			result.setSalePrice("-1.0");
		}
	}
	
	
	/**
	 * 计算每个产品的数量
	 * @param planProductlist
	 * @return
	 */
	public List<DesignPlanProductResult> productQuantity(List<DesignPlanProductResult> planProductlist) {
		List<DesignPlanProductResult> result = new ArrayList<DesignPlanProductResult>();
		if (planProductlist == null || planProductlist.size() <= 0) {
			return result;
		}
		for (DesignPlanProductResult planProductOne : planProductlist) {
			String ProductCode = planProductOne.getProductCode();
			if (ProductCode.indexOf("baimo") == 0) {
				continue;
			}
			Integer productId = planProductOne.getProductId();/* 取得商品的id */
			int productNum = 0;
			for (DesignPlanProductResult planProductTwo : planProductlist) {/* 商品数量 */
				int productIdOne = planProductTwo.getProductId();/* 获得同样类型商品数量 */
				if (productId == productIdOne) {
					productNum = productNum + 1;/* 再次循环此列表，id相同说明产品相同， 进入一次就加1 */
				}
			}
			planProductOne.setCount(productNum);/* 加入商品数量 */
			int num = 0;
			for (DesignPlanProductResult planProductThree : result) {
				int productIdTwo = planProductThree.getProductId();/* 去除重复产品 */
				if (productId == productIdTwo) {
					num = num + 1;/* 查询planProductlistNew 里有没有相同的id，如果有那么产品相同，不添加 */
				}
			}
			if (num <= 0) {
				result.add(planProductOne);/* 没有相同的id，则此产品不重复，添加 */ 
			}
		}
		return result;
	}
	
 
	/**
	 * 保存 设计方案产品库
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@ModelAttribute("designPlanProduct") DesignPlanProduct designPlanProduct,
			@RequestParam(value = "houseId",required = false) String houseId,
			@RequestParam(value = "livingId",required = false) String livingId,
			@RequestParam(value = "residentialUnitsName",required = false) String residentialUnitsName,
			@RequestParam(value = "newFlag",required = false) Integer newFlag,
			@RequestParam(value = "designPlanId",required = false) Integer designPlanId,
			@RequestParam(value = "context",required = false)String context,
			@RequestParam(value = "reUploadConfig",required = false) Integer reUploadConfig,
			HttpServletRequest request, HttpServletResponse response) {
		if( StringUtils.isBlank(designPlanProduct.getIds()) ){
			return new ResponseEnvelope<DesignPlanProduct>(designPlanProduct,
					designPlanProduct.getMsgId(), true);
		}
		String msgId = designPlanProduct.getMsgId();
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null) {
			return new ResponseEnvelope<DesignPlan>(false, "登录超时，请重新登录！", msgId);
		}
		ProductSaveParameter parameter=new ProductSaveParameter(designPlanProduct,designPlanId,context,reUploadConfig,request);
		return designPlanProductService.delProductAndSynConfigFile(parameter, loginUser);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 设置isDirty接口
	 * 
	 * @param id
	 *            要设置的designPlanProductId
	 * @param isDirty
	 *            isDirty值
	 * @return
	 */
	@RequestMapping(value = "/setIsDirty")
	@ResponseBody
	public Object setIsDirty(String id, String isDirty, String msgId) {
		DesignPlanProduct designPlanProduct = null;
		// if(StringUtils.isBlank(id)) return new
		// ResponseEnvelope<DesignPlanProduct>(false, "参数id不能为空",msgId);
		// if(StringUtils.isBlank(isDirty)) return new
		// ResponseEnvelope<DesignPlanProduct>(false, "参数isDirty不能为空",msgId);
		// if(StringUtils.isBlank(msgId)) return new
		// ResponseEnvelope<DesignPlanProduct>(false, "参数msgId不能为空",msgId);
		try {
			designPlanProduct = designPlanProductService.get(Integer
					.parseInt(id));
			if (designPlanProduct == null)
				return new ResponseEnvelope<DesignPlanProduct>(false, "id:"
						+ id + ",designPlanProduct未找到,设置失败", msgId);
			DesignPlanProduct newPlanProduct = new DesignPlanProduct();
			newPlanProduct.setId(designPlanProduct.getId());
			newPlanProduct.setIsDirty(Integer.parseInt(isDirty));
			designPlanProductService.update(newPlanProduct);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isBlank(msgId))
				return new ResponseEnvelope<DesignPlanProduct>(false,
						"参数格式不正确", msgId);
		}
		return new ResponseEnvelope<ProductsCost>(designPlanProduct, msgId,
				true);
	}
	
	/**
	 * 一个产品更新多个方案产品 接口
	 */
	@RequestMapping(value = "/updatePlanProducts")
	@ResponseBody
	public Object updatePlanProducts(Integer productId,String planProductIds,String msgId,
			 @RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			 @RequestParam(value = "houseId",required = false) String houseId,
			 @RequestParam(value = "livingId",required = false) String livingId,
			 @RequestParam(value = "residentialUnitsName",required = false) String residentialUnitsName,
			 @RequestParam(value = "newFlag",required = false) Integer newFlag,
			 HttpServletRequest request,
			 HttpServletResponse response,String splitTexturesChoose) throws Exception {
		String msg = "";
		if( StringUtils.isBlank(msgId) ){
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<>(false, msg, msgId);
		}
		if( productId == null || productId < 1 ){
			msg = "参数productId不能为空！";
			return new ResponseEnvelope<>(false, msg, msgId);
		}
		if( StringUtils.isBlank(planProductIds) ){
			msg = "参数planProductIds不能为空！";
			return new ResponseEnvelope<>(false, msg, msgId);
		}
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null||loginUser.getId()==null && loginUser.getUserType()!=null){
			return new ResponseEnvelope<>(false, "请重新登录!", msgId);
		}
		
		try {
			String[] array = planProductIds.split(",");
			for(String id : array){
				DesignPlanProduct planProduct = designPlanProductService.get(Utils.getIntValue(id));
				if( planProduct == null ){
					msg = "找不到该设计方案产品："+id;
					return new ResponseEnvelope<>(false, msg, msgId);
				}
				DesignPlanProduct newPlanProduct = new DesignPlanProduct();
				newPlanProduct.setId(planProduct.getId());
				BaseProduct baseProduct = baseProductService.get(productId);
				//更新多材质信息
				if( baseProduct != null && StringUtils.isNotBlank(baseProduct.getSplitTexturesInfo()) ){
					newPlanProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
				}else{
					newPlanProduct.setSplitTexturesChooseInfo("");
				}
				newPlanProduct.setProductId(productId);
				newPlanProduct.setIsHide(0);
				designPlanProductService.update(newPlanProduct);
				if(Utils.enableRedisCache()){
					DesignPlanProductCacher.remove(Utils.getIntValue(id)); 
				}
			    UsedProducts usedProducts = new UsedProducts();
			    SysUser sysUser = sysUserService.get(loginUser.getId());
			    usedProducts.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+"_"+Utils.generateRandomDigitString(6));
				usedProducts.setCreator(sysUser.getNickName());
				usedProducts.setGmtCreate(new Date());
				usedProducts.setGmtModified(new Date());
				usedProducts.setModifier(loginUser.getName());
				usedProducts.setIsDeleted(0);
				usedProducts.setUserId(loginUser.getId());
				usedProducts.setDesignId(planProduct.getPlanId());
				usedProducts.setProductId(productId);
				/**usedProducts.setNuma1(Utils.getIntValue(materialPicId));*/
				/**sysSave(usedProducts, request);*/
				usedProducts.setPlanProductId(planProduct.getProductId());
				usedProducts.setPosIndexPath(planProduct.getPosIndexPath());
				int id_ = usedProductsService.add(usedProducts);
				if(Utils.enableRedisCache()){
					UsedProductsCacher.remove(id_);
				}
			}
			/*更新产品使用次数表*/
			/*获取用户信息*/
			productUsageCountService.update(loginUser.getId(),productId,array.length);
			/*更新产品使用次数表->end*/

			//异步更新进入设计方案缓存
			/* 设计方案产品列表不走缓存。2017-07-31
			designPlanService.updateDesignPlanCache(designPlanId,newFlag,houseId,livingId,residentialUnitsName,request);*/

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<>(true, "更新数据成功！", msgId);
	}


	/**
	 * 结构背景墙替换保存
	 * @author xiaoxc
	 * @param productJson
	 * @param planProductIds
	 * @return ResponseEnvelope
	 */
	@RequestMapping(value = "/updateStructureBjWallProducts")
	@ResponseBody
	public Object updateStructureBjWallProducts(String productJson,String planProductIds,String msgId,
									 @RequestParam(value = "designPlanId", required = false) Integer designPlanId,
									 @RequestParam(value = "houseId",required = false) String houseId,
									 @RequestParam(value = "livingId",required = false) String livingId,
									 @RequestParam(value = "residentialUnitsName",required = false) String residentialUnitsName,
									 @RequestParam(value = "newFlag",required = false) Integer newFlag,HttpServletRequest request
									 ) throws Exception {
		String msg = "";
		if( StringUtils.isBlank(msgId) ){
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<>(false, msg, msgId);
		}
		if( StringUtils.isBlank(productJson) ){
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<>(false, msg, msgId);
		}
		if( StringUtils.isBlank(planProductIds) ){
			msg = "参数planProductIds不能为空！";
			return new ResponseEnvelope<>(false, msg, msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		ResponseEnvelope responseEnvelope = designPlanProductService.updateStructureBjWallProducts(productJson,planProductIds,msgId,loginUser);
		/* 设计方案产品列表不走缓存。2017-07-31
		if (responseEnvelope.isSuccess() == true) {
			//异步更新进入设计方案缓存
			designPlanService.updateDesignPlanCache(designPlanId,newFlag,houseId,livingId,residentialUnitsName,request);
		}*/
		return responseEnvelope;
	}

	/**
	 * 替换一个产品时，自动携带其他产品（可配置）
	 * @param request
	 * @param response
	 * @param productId 产品ID
	 * @param productSmallTypeCode 产品小类
	 * @return
	 */
	@RequestMapping(value = "/autoCarryProduct")
	@ResponseBody
	public Object autoCarryProduct(HttpServletRequest request, HttpServletResponse response,
								   String productSmallTypeCode, Integer productId, Integer planId, String msgId){
		if( planId == null ){
			return new ResponseEnvelope<CategoryProductResult>(msgId,"planId不能为空",false);
		}
		if( productId == null ){
			return new ResponseEnvelope<CategoryProductResult>(msgId,"productId不能为空",false);
		}
		if( StringUtils.isBlank(productSmallTypeCode) ){
			return new ResponseEnvelope<CategoryProductResult>(msgId,"productSmallTypeCode不能为空",false);
		}
		Map<String,CategoryProductResult> categoryProductResultMap = baseProductService.autoCarryProduct(productId, productSmallTypeCode, planId, request);
		if( categoryProductResultMap != null ){
			return new ResponseEnvelope<CategoryProductResult>(true,categoryProductResultMap,msgId);
		}else{
			return new ResponseEnvelope<CategoryProductResult>(false,null,msgId);
		}
	}
	
	/**
	 * 还原已删除的背景墙、门框、窗帘
	 * @param designPlanId
	 * @return
	 */
	@RequestMapping(value = "/reductionProduct")
	@ResponseBody
	public Object reductionProduct(String msgId,Integer designPlanId,HttpServletRequest request) {
		String mediaType = SystemCommonUtil.getMediaType(request);
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		return designPlanProductService.reductionProduct(msgId,designPlanId,mediaType,loginUser);
	}
	
	/**
	 * 更新 执行还原操作之后的设计方案 的配置文件
	 * @return
	 */
	@RequestMapping(value = "/updateAfterRestore")
	@ResponseBody
	public Object UpdateAfterRestore(Integer planId,String posIndexMap,String msgId) {
		
		List<Integer> ids = new ArrayList<>();
		JSONArray json = JSONArray.fromObject(posIndexMap); // 首先把字符串转成 JSONArray  对象
		if(json.size()>0){
			for(int i=0;i<json.size();i++){
				JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
			    // 得到 每个对象中的属性值
				String designPlanId = (String) job.get("id");
			    String posIndexPath = (String) job.get("posIndexPath");
			    DesignPlanProduct designPlanProduct = designPlanProductService.get(Integer.valueOf(designPlanId));
				
				designPlanProduct.setIsDeleted(0);
				designPlanProduct.setProductId(designPlanProduct.getInitProductId());
//				designPlanProduct.setIsHide(1);
				designPlanProduct.setPosIndexPath(posIndexPath);
				Integer id = designPlanProductService.update(designPlanProduct);
				ids.add(id);
				continue;
			}
		}
		ResponseEnvelope envelope = new ResponseEnvelope<>();
		envelope.setDatalist(ids);
		envelope.setMsgId(msgId);
		return envelope;
	}


	/**
	 * 同时更新方案多个产品
	 * @param planProductJson [{方案产品ID ：产品ID}]
	 * @param planId 方案ID
	 * @param needDelPlanProductIds 需删除方案产品IDs
	 * @param msgId
	 * @param request
	 * @author xiaoxc
	 * @return
	 */
	@RequestMapping(value = "/updatePlanProductIds")
	@ResponseBody
	public Object updatePlanProductIds(String planProductJson, Integer planId, String needDelPlanProductIds, String msgId, HttpServletRequest request) {

		boolean falg = ParamCommonVerification.checkTheParamIsValid(planProductJson,msgId,String.valueOf(planId));
		if (!falg) {
			return new ResponseEnvelope<>(false, "The param is null", msgId);
		}

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser == null) {
			return new ResponseEnvelope<>(false, "user is null", msgId);
		}
		boolean isSuccess = designPlanProductService.updatePlanProducts(planProductJson,planId,needDelPlanProductIds,loginUser);
		String msg = "";
		if (isSuccess) {
			msg = "更新成功！";
		}else{
			msg = "更新失败！";
		}
		return new ResponseEnvelope(isSuccess,msg,msgId);
	}
	
}
