package com.nork.product.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.SpecialProductTypeInfo;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.PrepProductSearchInfoService;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.productprops.service.ProductPropsService;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: ProductCategoryRelController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品与产品类目关联Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/high/product/productCategoryRel")
public class WebProductCategoryRelControllerCreateByHuangsongbo {
	private static Logger logger = Logger.getLogger(WebProductCategoryRelControllerCreateByHuangsongbo.class);
	private final JsonDataServiceImpl<ProductCategoryRel> JsonUtil = new JsonDataServiceImpl<ProductCategoryRel>();

	@Autowired
	private ProductCategoryRelService productCategoryRelService;

	@Autowired
	private ProductAttributeService productAttributeService;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	private DesignPlanProductService designPlanProductService;

	@Autowired
	private DesignTempletService designTempletService;

	@Autowired
	private ProductPropsService productPropsService;

	@Autowired
	private ProCategoryService proCategoryService;
	
	@Autowired
	private PrepProductSearchInfoService prepProductSearchInfoService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 根据分类查询产品接口
	 *
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProductV1_")
	@ResponseBody
	public Object searchProduct(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelNumber) {
		if("0".equals(templateProductId)){
			templateProductId="";
		}
		Long startDate = new Date().getTime();
		// 参数验证->start
		String msg = "";
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		// 参数验证->end

		// 基本信息->start
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			productCategoryRel = (ProductCategoryRelSearch) JsonUtil.getJsonToBean(jsonStr,
					ProductCategoryRelSearch.class);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// 基本信息->end

		Long startDate1 = new Date().getTime();
		// 获取黑名单配置信息(huangsongbo 2017.1.11)
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		productCategoryRel.setBlacklistSet(blacklist);
		// 获取黑名单配置信息(huangsongbo 2017.1.11)->end
		Long startDate2 = new Date().getTime();
		logger.warn("------获取黑名单配置:" + (startDate2 - startDate1));

		if (productCategoryRel.getCategoryCode().contains(",")) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		} else {
			productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		}

		if (StringUtils.isNotBlank(productModelNumber)) {
			productCategoryRel.setProductModelNumber(productModelNumber.trim());
		}

		productCategoryRel.setSpaceCommonId(spaceCommonId);
		productCategoryRel.setProductTypeValue(productTypeValue);

		Long startDate3 = new Date().getTime();
		// 大小类查询条件->start
		SysDictionary bigSd = null;
		SysDictionary bmSmallSd = null;
		SysDictionary smallSd = null;
		if (StringUtils.isNotBlank(productTypeValue)) {
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue)
					);
		}
		if (bigSd != null && StringUtils.isNotBlank(smallTypeValue)) {
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(smallTypeValue));
			smallSd = sysDictionaryService.dealWithInconformity(bigSd, bmSmallSd);
			if (smallSd != null) {
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			}
		}
		// 大小类查询条件->end
		Long startDate4 = new Date().getTime();
		logger.warn("------大小类信息查询条件:" + (startDate4 - startDate3));

		int total = 0;
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		try {
			if (loginUser.getUserType() == 1) {
				productCategoryRel.setIsInternalUser("yes");
			}
			// 用户Id
			productCategoryRel.setUserId(loginUser.getId());
			DesignPlan designPlan = new DesignPlan();
			DesignPlanProduct designPlanProduct = null;
			if (designPlanId != null && designPlanId != 0) {
				if (Utils.enableRedisCache()) {
					designPlan = DesignCacher.get(designPlanId);
				} else {
					designPlan = designPlanService.get(designPlanId);
				}
			}
			if (designPlan == null) {
				msg = "找不到该设计方案：" + designPlanId;
				return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
			}
			Integer templatePlanProductId = -1;
			BaseProduct productSelected = null;
			// 是否为空房模式
			boolean emptyModel = false;

			Long startDate5 = new Date().getTime();
			designPlanProduct = sethouseTypeSearchCondition(productCategoryRel, houseType, planProductId);
			// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->end
			Long startDate6 = new Date().getTime();
			logger.warn("------产品空间类型搜索条件:" + (startDate6 - startDate5));

			Long startDate7 = new Date().getTime();
			if (planProductId != null && planProductId != 0) {
				/*
				 * logger.warn(
				 * "designPlanProduct.getProductId()-----------------"+
				 * designPlanProduct.getProductId());
				 */
				if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0) {
					if (Utils.enableRedisCache()) {
						productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
					} else {
						productSelected = baseProductService.get(designPlanProduct.getProductId());
					}
					// 获取查询属性产品的条件
					productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
							designPlanProduct.getProductId());

				}
				productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
				productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
				templatePlanProductId = designPlanProduct.getPlanProductId();
				productCategoryRel.setDesignProductId(templatePlanProductId);
				if (StringUtils.isBlank(templateProductId)) {
					templateProductId = productSelected.getBmIds();
				}
				if (StringUtils.isNotBlank(templateProductId)) {
					String[] arraytemplateProductId = templateProductId.split(",");
					productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
				}

				DesignTemplet designTemplet = null;
				if (Utils.enableRedisCache()) {
					designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
				} else {
					designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
				}
				if (designTemplet == null) {
					logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
							+ designPlan.getId());
				} else {
					if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
						logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
								+ ",designId=" + designPlan.getId());
					}
				}
				if (!StringUtils.isEmpty(designTemplet.getDesignCode())
						&& designTemplet.getDesignCode().endsWith("_000")) {
					emptyModel = true;
				} else {
					emptyModel = false;
				}
			}
			Long startDate8 = new Date().getTime();
			logger.warn("------planProductId不为空的逻辑:" + (startDate8 - startDate7));

			// 白模产品信息
			BaseProduct baimoProduct = new BaseProduct();
			if (designPlanProduct != null) {
				if (designPlanProduct.getInitProductId() != null
						&& designPlanProduct.getInitProductId().intValue() > 0) {
					if (Utils.enableRedisCache()) {
						baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
					} else {
						baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
					}
				} else {
					if (Utils.enableRedisCache()) {
						baimoProduct = BaseProductCacher.get(Utils.getIntValue(templateProductId));
					} else {
						baimoProduct = baseProductService.get(Utils.getIntValue(templateProductId));
					}
				}
			}

			// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
			// 空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
			String onlyShowRecommend = Utils.getValue("onlyShowRecommend", "false");
			String exceptRecommend = Utils.getValue("exceptRecommend", "false");
			boolean flag = false;// 只显示推荐产品
			if ("false".equals(exceptRecommend) && "true".equals(onlyShowRecommend)) {
				flag = true;
			}
			String specialProductTypes = Utils.getValue("special.productType", "");
			String filterLength = Utils.getValue("app.filter.stretch.length", "0");
			if (StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
					&& new Integer(productTypeValue).intValue() > 0 && new Integer(smallTypeValue).intValue() > 0) {
				// 是否为硬装产品
				SysDictionary sysDictionary = new SysDictionary();
				sysDictionary.setValue(Integer.valueOf(productTypeValue));
				sysDictionary.setSmallValue(Integer.valueOf(smallTypeValue));
				sysDictionary = sysDictionaryService.checkType(sysDictionary);// 需调整
				// att4等于1表示为硬装产品
				if ("1".equals(sysDictionary.getAtt4()) || emptyModel) {
					onlyShowRecommend = "false";
				} else if (StringUtils.equals("1", sysDictionary.getAtt6())) {// 样板房定制产品
					onlyShowRecommend = "false";
				}
				// 判断是否是背景墙
				String bjType = Utils.getValue("app.smallProductType.stretch", "");
				boolean isShowbgWall = Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), bjType);
				if (isShowbgWall) {
					// 只显示背景墙exceptRecommend设为true
					exceptRecommend = "true";
					productCategoryRel.setShowBgWall(true);
					String fullPaveLength = baimoProduct.getFullPaveLength();
					if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
						fullPaveLength = baimoProduct.getProductLength();
					}
					String productHeight = baimoProduct.getProductHeight();
					if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
							&& StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0) {
						productCategoryRel
								.setStartLength(Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength));
						productCategoryRel
								.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength));
						productCategoryRel.setBgWallHeight(productHeight);
					} else {
						productCategoryRel.setValue(true);
					}
				} else {
					productCategoryRel.setShowBgWall(false);
				}
				// 判断是否是特殊分类
				if (StringUtils.isNotBlank(specialProductTypes)) {
					JSONArray productTypeArray = JSONArray.fromObject(specialProductTypes);
					if (productTypeArray != null && productTypeArray.size() > 0) {
						for (int i = 0; i < productTypeArray.size(); i++) {
							JSONObject jsonObj = (JSONObject) productTypeArray.get(i);
							String bigTypeKey = jsonObj.getString("productTypeKey");
							String smallTypeKeys = "," + jsonObj.getString("productSmallTypeKeys") + ",";
							if (bigTypeKey.equals(bigSd == null ? "" : bigSd.getValuekey())) {
								productCategoryRel.setSpecialProductType(smallTypeKeys);
								String smallKey = "";
								if (smallSd != null) {
									if ("baimo".equals(smallSd.getAtt3())) {
										smallKey = Utils.getTypeValueKey(smallSd.getValuekey());
									} else {
										smallKey = smallSd.getValuekey();
									}
									// 背景墙特殊处理，（app必须配置special.productType背景墙类型）
									if (smallTypeKeys.indexOf(smallKey) != -1 && !isShowbgWall) {
										productCategoryRel.setProductSmallTypeKey(smallKey);
										break;
									}
								}
							}
						}
					}
				}
				// 过滤产品长、 高信息
				String productSmallTypes_LH = Utils.getValue("filter.productLH.productSmallType", "");
				if (Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), productSmallTypes_LH)) {
					if (baimoProduct != null) {
						productCategoryRel.setProductLength(baimoProduct.getProductLength());
						productCategoryRel.setProductHeight(baimoProduct.getProductHeight());
					} else {
						productCategoryRel.setProductLength(productSelected.getProductLength());
						productCategoryRel.setProductHeight(productSelected.getProductHeight());
					}
				}
				// 过滤产品长、宽信息
				String productSmallTypes_LW = Utils.getValue("filter.productLW.productSmallType", "");
				if (Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), productSmallTypes_LW)) {
					if (baimoProduct != null) {
						productCategoryRel.setProductLength(baimoProduct.getProductLength());
						productCategoryRel.setProductWidth(baimoProduct.getProductWidth());
					} else {
						productCategoryRel.setProductLength(productSelected.getProductLength());
						productCategoryRel.setProductWidth(productSelected.getProductWidth());
					}
				}
			}
			// ture只显示推荐，false显示所有分类相关数据
			if ("true".equals(onlyShowRecommend)) {
				productCategoryRel.setOnlyShowRecommend(true);
			} else {
				productCategoryRel.setOnlyShowRecommend(false);
			}
			// true排除推荐，false不排除
			if ("true".equals(exceptRecommend)) {
				productCategoryRel.setExceptRecommend(true);
			} else {
				productCategoryRel.setExceptRecommend(false);
			}

			// 设置序列号查询条件(huangsongbo 2017.1.11)
			List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
			productCategoryRel.setBaseProduct(baseProductList);
			// 设置序列号查询条件(huangsongbo 2017.1.11)->end

			List<CategoryProductResult> customizeList = new ArrayList<>();// 定制
			List<CategoryProductResult> recommendList = new ArrayList<>();// 推荐和分类
			int customizeCount = 0;
			int recommendCount = 0;
			int limit = productCategoryRel.getLimit();
			int start = productCategoryRel.getStart();
			// 查询定制产品
			if ("false".equals(onlyShowRecommend) && spaceCommonId != null) {
				if ("qiangm".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					// 绑定点有多个
					String parentProductId = designPlanProduct.getBindParentProductId();
					List<String> parentProIds = new ArrayList<String>();
					if (StringUtils.isNotBlank(parentProductId)) {
						String arrayParentProId[] = parentProductId.split(",");
						DesignPlanProduct dpp = null;
						// 如果该绑定产品存在，就不需查询绑定产品的分类
						for (String arrId : arrayParentProId) {
							dpp = new DesignPlanProduct();
							dpp.setIsDeleted(0);
							dpp.setPlanId(designPlanProduct.getPlanId());
							dpp.setInitProductId(Utils.getIntValue(arrId));
							List<DesignPlanProduct> ls = designPlanProductService.getList(dpp);
							// 如果背景墙存在空间则不显示背景墙产品
							if (Lists.isEmpty(ls) && ls.size() == 0) {
								parentProIds.add(arrId);
							}
						}
						// 判断是否是新增背景墙,是则过滤背景墙长高
						if (Lists.isNotEmpty(parentProIds) && parentProIds.size() > 0) {
							productCategoryRel.setTemplateProductId(parentProIds);
							productCategoryRel.setShowBgWall(true);
							// 白模背景墙产品信息
							BaseProduct product = new BaseProduct();
							if (Utils.enableRedisCache()) {
								product = BaseProductCacher.get(Utils.getIntValue(arrayParentProId[0]));
							} else {
								product = baseProductService.get(Utils.getIntValue(arrayParentProId[0]));
							}
							// 过滤背景墙长高
							String fullPaveLength = product.getFullPaveLength();
							if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
								fullPaveLength = product.getProductLength();
							}
							String productHeight = product.getProductHeight();
							if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
									&& StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0) {

								productCategoryRel.setStartLength(
										Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength));
								productCategoryRel.setEndLength(
										Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength));
								productCategoryRel.setBgWallHeight(productHeight);

								SysDictionary bmSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
										product.getProductSmallTypeValue());
								SysDictionary sdic = null;
								if (bmSd != null && "baimo".equals(bmSd.getAtt3())) {
									sdic = sysDictionaryService.findOneByTypeAndValueKey(bigSd.getValuekey(),
											Utils.getTypeValueKey(bmSd.getValuekey()));
								}
								productCategoryRel.setProductSmallTypeValue(sdic == null ? 0 : sdic.getValue());
							} else {
								productCategoryRel.setValue(true);
							}
						}
					}
				} else if ("tianh".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					// 如果查天花只显示定制产品
					exceptRecommend = "true";
				}

				Long startDate9 = new Date().getTime();
				// count查询->start
				// 非定制只查询推荐产品
				if (flag) {
					if (Utils.enableRedisCache()) {
						recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
					}
				} else {
					if (!"true".equals(exceptRecommend)) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService
									.findRecommendCategoryProductResultCount(productCategoryRel);
						}
					}
					if (Utils.enableRedisCache()) {
						customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						customizeCount = productCategoryRelService
								.findCustomizedCategoryProductResultCount(productCategoryRel);
					}
				}
				// 如果是厂商，则只能查询这个厂商品牌下的产品,没有则查询除其外所有品牌的产品
				if (3 == loginUser.getUserType() && customizeCount + recommendCount == 0) {
					/* add品牌,大类,小类,产品 */
					/* 针对搜索条件(大类同,小类异的情况->返回空列表) */
					boolean falg2 = false;
					String productTypeValue2 = "0";
					if (StringUtils.equals(productTypeValue.trim(), "0")) {
						SysDictionary sysDictionaryBigType = sysDictionaryService
								.findOneByValueKeyInCache(productCategoryRel.getCategoryCode().replace(".", ""));
						if (!StringUtils.equals("productType", sysDictionaryBigType.getType())) {
							sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey("productType",
									sysDictionaryBigType.getType());
						}
						if (sysDictionaryBigType != null)
							productTypeValue2 = sysDictionaryBigType.getValue() + "";
					} else {
						productTypeValue2 = productTypeValue.trim();
					}
					for (BaseProduct baseProduct : baseProductList) {
						/* 识别序列号有没有和productTypeValue相同的大类 */
						logger.info("------productTypeValue2:" + productTypeValue2
								+ ";baseProduct.getProductTypeValue():" + baseProduct.getProductTypeValue());
						if (StringUtils.equals(productTypeValue2, baseProduct.getProductTypeValue())) {
							falg2 = true;
							break;
						}
					}
					if (!falg2) {
						productCategoryRel.setBaseProduct(null);
					}
					/* add品牌,大类,小类,产品->end */
					if (flag) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
						}
					} else {
						if (!"true".equals(exceptRecommend)) {
							if (Utils.enableRedisCache()) {
								recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
							} else {
								/* 新过滤方案(done) */
								recommendCount = productCategoryRelService
										.findRecommendCategoryProductResultCount(productCategoryRel);
							}
						}
						if (Utils.enableRedisCache()) {
							customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							customizeCount = productCategoryRelService
									.findCustomizedCategoryProductResultCount(productCategoryRel);
						}
					}
				}
				// count查询->end
				Long startDate10 = new Date().getTime();
				logger.warn("------count查询:" + (startDate10 - startDate9));

				Long startDate11 = new Date().getTime();
				// 定制产品list查询->start
				if (!flag) {
					if (customizeCount > 0) {
						if (Utils.enableRedisCache()) {
							customizeList = ProductCategoryRelCacher.getListCustomizedCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							customizeList = productCategoryRelService
									.findCustomizedCategoryProductResult(productCategoryRel);
						}
					}
				}
				// 定制产品list查询->end
				Long startDate12 = new Date().getTime();
				logger.warn("------定制产品list查询:" + (startDate12 - startDate11));

			} else {

				Long startDate13 = new Date().getTime();
				// 非定制产品list->start
				if (flag) {
					if (Utils.enableRedisCache()) {
						recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
					}
				} else {
					if (Utils.enableRedisCache()) {
						recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						recommendCount = productCategoryRelService
								.findRecommendCategoryProductResultCount(productCategoryRel);
					}
				}
				// 如果厂商没有搜索到该品牌的产品，则搜索产品分类下所有的产品
				if (/* 3 == loginUser.getUserType() && */recommendCount == 0) {
					productCategoryRel.setBrandIds(null);
					if (flag) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
						}
					} else {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService
									.findRecommendCategoryProductResultCount(productCategoryRel);
						}
					}
				}
				// 非定制产品list->start
				Long startDate14 = new Date().getTime();
				logger.warn("------非定制产品list" + (startDate14 - startDate13));

			}
			if ("true".equals(exceptRecommend)) {
				list.addAll(customizeList);
			} else {

				Long startDate15 = new Date().getTime();
				// 推荐产品list->start
				if (flag) {// 只查推荐吧
					if (Lists.isNotEmpty(customizeList) && customizeList.size() > 0) {
						list.addAll(customizeList);
						if (Utils.enableRedisCache()) {
							recommendList = ProductCategoryRelCacher.getRecommendList(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendList = productCategoryRelService.getRecommendResult(productCategoryRel);
						}
						list.addAll(recommendList);
					} else {
						if (Utils.enableRedisCache()) {
							list = ProductCategoryRelCacher.getRecommendList(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							list = productCategoryRelService.getRecommendResult(productCategoryRel);
						}
					}
				} else {
					// 查询推荐和分类
					if (Lists.isNotEmpty(customizeList) && customizeList.size() > 0) {
						list.addAll(customizeList);
						if (Utils.enableRedisCache()) {
							recommendList = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendList = productCategoryRelService
									.findRecommendCategoryProductResult(productCategoryRel);
						}
						list.addAll(recommendList);
					} else {
						if (Utils.enableRedisCache()) {
							list = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							list = productCategoryRelService.findRecommendCategoryProductResult(productCategoryRel);
						}
					}
				}
				// 推荐产品list->end
				Long startDate16 = new Date().getTime();
				logger.warn("------推荐产品list:" + (startDate16 - startDate15));

			}
			// list查询->end

			Long startDate19 = new Date().getTime();
			// 排序 推荐 小类 匹配度 使用量 色系->start
			list = getProductList(list, productCategoryRel);
			// 排序 推荐 小类 匹配度 使用量 色系->end
			Long startDate20 = new Date().getTime();
			logger.warn("------排序总时间:" + (startDate20 - startDate19));

			total = recommendCount + customizeCount;
			/* 分页 */
			list = Utils.paging(list, start, limit);
			// 获取需要自动携带白模产品的配置
			Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
			String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
					"design.designPlan.autoCarryBaimoProducts", "");
			if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
				JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
				try {
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

			Long startDate17 = new Date().getTime();
			// 附加属性->start
			for (CategoryProductResult productResult : list) {
				BaseProduct baseProduct = null;
				/*
				 * if(Utils.enableRedisCache()){ baseProduct =
				 * BaseProductCacher.get(productResult.getProductId()); }else{
				 * baseProduct =
				 * baseProductService.get(productResult.getProductId()); }
				 */
				// join 查询大小类信息,是否是硬装,是否是组合
				Map<String,Integer> InfoByIdMap = new HashMap<>();
				InfoByIdMap.put("id",productResult.getProductId());
				InfoByIdMap.put("designTempletId",designPlan.getDesignTemplateId());
				baseProduct = baseProductService.getInfoById(InfoByIdMap);

				if (baseProduct == null) {
					logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
					continue;
				}
				productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
						designPlanProduct, autoCarryMap, /*loginUser,*/ request);
				/*
				 * logger.warn("------decorationProductInfoV3耗时:"+(new
				 * Date().getTime()-startTime));
				 */
			}
			// 附加属性->end
			Long startDate18 = new Date().getTime();
			logger.info("------附加属性:" + (startDate18 - startDate17));

			/** 列表排序,同小类的排在前面，同大类其次，剩余的排最后 */
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!", productCategoryRel.getMsgId());
		}
		Long startDateEnd = new Date().getTime();
		logger.warn("------查询接口总耗时:" + (startDateEnd - startDate));
		return new ResponseEnvelope<CategoryProductResult>(total, list, productCategoryRel.getMsgId());
	}

	/**
	 * 根据分类查询产品接口
	 *
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProductV2_old_")
	@ResponseBody
	public Object searchProductV2_old(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelNumber) {
		if("0".equals(templateProductId)){
			templateProductId="";
		}
		Long startDate = new Date().getTime();

		// 参数验证->start
		String msg = "";
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		DesignPlan designPlan = null;
		if (designPlanId != null && designPlanId != 0) {
			designPlan = designPlanService.get(designPlanId);
		}
		if (designPlan == null) {
			msg = "找不到该设计方案：" + designPlanId;
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		// 参数验证->end

		// 基本信息->start
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			productCategoryRel = (ProductCategoryRelSearch) JsonUtil.getJsonToBean(jsonStr,
					ProductCategoryRelSearch.class);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// 基本信息->end

		// 获取黑名单配置信息(huangsongbo 2017.1.11)
		Long startDate1 = new Date().getTime();
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		productCategoryRel.setBlacklistSet(blacklist);
		Long startDate2 = new Date().getTime();
		logger.warn("------获取黑名单配置:" + (startDate2 - startDate1));
		// 获取黑名单配置信息(huangsongbo 2017.1.11)->end

		// 大小类,产品分类查询条件->start
		Long startDate3 = new Date().getTime();
		// 加入产品编码或型号搜索条件
		if (productCategoryRel.getCategoryCode().contains(",")) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		} else {
			productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		}
		if (StringUtils.isNotBlank(productModelNumber)) {
			productCategoryRel.setProductModelNumber(productModelNumber.trim());
		}
		productCategoryRel.setSpaceCommonId(spaceCommonId);
		productCategoryRel.setProductTypeValue(productTypeValue);
		// 获取当前产品的大类和小类,可以删除
		// 获取产品大小类信息(配置写法去掉.修复数据字典中小类和白模_小类的编码一致.eg:basic_beij->beij)。排序时使用
		SysDictionary bigSd = null;
		SysDictionary bmSmallSd = null;
		SysDictionary smallSd = null;
		if (StringUtils.isNotBlank(productTypeValue)) {
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue)
					);
		}
		if (bigSd != null && StringUtils.isNotBlank(smallTypeValue)) {
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(smallTypeValue));
			// 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
			smallSd = sysDictionaryService.dealWithInconformity(bigSd, bmSmallSd);
			if (smallSd != null) {
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			}
		}
		Long startDate4 = new Date().getTime();
		logger.warn("------大小类信息查询条件:" + (startDate4 - startDate3));
		// 大小类,产品分类查询条件->end

		int total = 0;
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		try {
			if (loginUser.getUserType() == 1) {
				productCategoryRel.setIsInternalUser("yes");
			}
			// 用户Id
			productCategoryRel.setUserId(loginUser.getId());
			Integer templatePlanProductId = -1;
			BaseProduct productSelected = null;

			// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->start
			Long startDate5 = new Date().getTime();
			DesignPlanProduct designPlanProduct = sethouseTypeSearchCondition(productCategoryRel, houseType,
					planProductId);
			Long startDate6 = new Date().getTime();
			logger.warn("------产品空间类型搜索条件:" + (startDate6 - startDate5));
			// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->end

			Long startDate7 = new Date().getTime();
			if (planProductId != null && planProductId != 0) {
				if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0) {
					if (Utils.enableRedisCache()) {
						productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
					} else {
						productSelected = baseProductService.get(designPlanProduct.getProductId());
					}
					// 获取查询属性产品的条件
					productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
							designPlanProduct.getProductId());

				}
				productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
				productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
				templatePlanProductId = designPlanProduct.getPlanProductId();
				productCategoryRel.setDesignProductId(templatePlanProductId);
				if (StringUtils.isBlank(templateProductId)) {
					templateProductId = productSelected.getBmIds();
				}
				if (StringUtils.isNotBlank(templateProductId)) {
					String[] arraytemplateProductId = templateProductId.split(",");
					productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
				}

				DesignTemplet designTemplet = null;
				if (Utils.enableRedisCache()) {
					designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
				} else {
					designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
				}
				if (designTemplet == null) {
					logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
							+ designPlan.getId());
				} else {
					if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
						logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
								+ ",designId=" + designPlan.getId());
					}
				}
			}
			Long startDate8 = new Date().getTime();
			logger.info("------planProductId不为空的逻辑:" + (startDate8 - startDate7));

			// 产品对应白膜信息->start
			BaseProduct baimoProduct = new BaseProduct();
			if (designPlanProduct != null) {
				if (designPlanProduct.getInitProductId() != null
						&& designPlanProduct.getInitProductId().intValue() > 0) {
					if (Utils.enableRedisCache()) {
						baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
					} else {
						baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
					}
				} else {
					if (Utils.enableRedisCache()) {
						baimoProduct = BaseProductCacher.get(Utils.getIntValue(templateProductId));
					} else {
						baimoProduct = baseProductService.get(Utils.getIntValue(templateProductId));
					}
				}
			}
			// 产品对应白膜信息->end

			// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
			// 空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
			String onlyShowRecommend = Utils.getValue("onlyShowRecommend", "false");
			String exceptRecommend = Utils.getValue("exceptRecommend", "false");
			boolean flag = false;// 只显示推荐产品
			if ("false".equals(exceptRecommend) && "true".equals(onlyShowRecommend)) {
				flag = true;
			}
			String onlyShowCustomization = null;
			if (StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
					&& new Integer(productTypeValue).intValue() > 0 && new Integer(smallTypeValue).intValue() > 0) {
				String bjType = Utils.getValue("app.smallProductType.stretch", "");
				boolean isShowbgWall = Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), bjType);
				if (isShowbgWall) {
					onlyShowCustomization = "true";
				}
				productCategoryRelService.bgWallAndSpecialTypeInfo(productCategoryRel, isShowbgWall, bigSd, smallSd,
						baimoProduct, productSelected);
			}

			// ture只显示推荐，false显示所有分类相关数据
			if ("true".equals(onlyShowRecommend)) {
				productCategoryRel.setOnlyShowRecommend(true);
			} else {
				productCategoryRel.setOnlyShowRecommend(false);
			}
			// true排除推荐，false不排除
			if ("true".equals(exceptRecommend)) {
				productCategoryRel.setExceptRecommend(true);
			} else {
				productCategoryRel.setExceptRecommend(false);
			}

			// 设置序列号查询条件(huangsongbo 2017.1.11)
			Long startDate8_1 = new Date().getTime();
			List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
			productCategoryRel.setBaseProduct(baseProductList);
			Long startDate8_2 = new Date().getTime();
			logger.warn("------设置序列号查询条件:" + (startDate8_2 - startDate8_1));
			// 设置序列号查询条件(huangsongbo 2017.1.11)->end

			List<CategoryProductResult> customizeList = new ArrayList<>();// 定制
			List<CategoryProductResult> recommendList = new ArrayList<>();// 推荐和分类
			int customizeCount = 0;
			int recommendCount = 0;
			int limit = productCategoryRel.getLimit();
			int start = productCategoryRel.getStart();
			// 查询定制产品
			if ("false".equals(onlyShowRecommend) && spaceCommonId != null) {
				if ("qiangm".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					productCategoryRelService.getWallTypeLogic(productCategoryRel, designPlanProduct, bigSd,
							 request);
				} else if ("tianh".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					// 如果查天花只显示定制产品
					onlyShowCustomization = "true";
				}

				// count查询->start
				Long startDate9 = new Date().getTime();
				// 非定制只查询推荐产品
				if (flag) {
					if (Utils.enableRedisCache()) {
						recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
					}
				} else {
					if (!"true".equals(onlyShowCustomization)) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService
									.findRecommendCategoryProductResultCount(productCategoryRel);
						}
					}
					// 定制类产品的数据获取
					if (Utils.enableRedisCache()) {
						customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						customizeCount = productCategoryRelService
								.findCustomizedCategoryProductResultCount(productCategoryRel);
					}
				}
				// 如果是厂商，则只能查询这个厂商品牌下的产品,没有则查询除其外所有品牌的产品
				if (3 == loginUser.getUserType() && customizeCount + recommendCount == 0) {
					/* add品牌,大类,小类,产品 */
					/* 针对搜索条件(大类同,小类异的情况->返回空列表) */
					boolean falg2 = false;
					String productTypeValue2 = "0";
					if (StringUtils.equals(productTypeValue.trim(), "0")) {
						SysDictionary sysDictionaryBigType = sysDictionaryService
								.findOneByValueKeyInCache(productCategoryRel.getCategoryCode().replace(".", ""));
						if (!StringUtils.equals("productType", sysDictionaryBigType.getType())) {
							sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey("productType",
									sysDictionaryBigType.getType());
						}
						if (sysDictionaryBigType != null)
							productTypeValue2 = sysDictionaryBigType.getValue() + "";
					} else {
						productTypeValue2 = productTypeValue.trim();
					}
					// 通过序列号过滤数据（当序列号存在该大类、小类产品或品牌时，只显示该品牌或该大类或小类的产品，如果不存在则查询所有数据）
					for (BaseProduct baseProduct : baseProductList) {
						/* 识别序列号有没有和productTypeValue相同的大类 */
						logger.info("------productTypeValue2:" + productTypeValue2
								+ ";baseProduct.getProductTypeValue():" + baseProduct.getProductTypeValue());
						if (StringUtils.equals(productTypeValue2, baseProduct.getProductTypeValue())) {
							falg2 = true;
							break;
						}
					}
					if (!falg2) {
						productCategoryRel.setBaseProduct(null);
					}
					/* add品牌,大类,小类,产品->end */
					if (flag) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
						}
					} else {
						if (!"true".equals(onlyShowCustomization)) {
							if (Utils.enableRedisCache()) {
								recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
							} else {
								/* 新过滤方案(done) */
								recommendCount = productCategoryRelService
										.findRecommendCategoryProductResultCount(productCategoryRel);
							}
						}
						if (Utils.enableRedisCache()) {
							customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							customizeCount = productCategoryRelService
									.findCustomizedCategoryProductResultCount(productCategoryRel);
						}
					}
				}
				Long startDate10 = new Date().getTime();
				logger.warn("------count查询:" + (startDate10 - startDate9));
				// count查询->end

				// 定制产品list查询->start
				Long startDate11 = new Date().getTime();
				if (!flag) {
					if (customizeCount > 0) {
						if (Utils.enableRedisCache()) {
							customizeList = ProductCategoryRelCacher.getListCustomizedCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							customizeList = productCategoryRelService
									.findCustomizedCategoryProductResult(productCategoryRel);
						}
					}
				}
				Long startDate12 = new Date().getTime();
				logger.warn("------定制产品list查询:" + (startDate12 - startDate11));
				// 定制产品list查询->end

			} else {

				// 非定制产品list->start
				Long startDate13 = new Date().getTime();
				if (flag) {
					if (Utils.enableRedisCache()) {
						recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
					}
				} else {
					if (Utils.enableRedisCache()) {
						recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						recommendCount = productCategoryRelService
								.findRecommendCategoryProductResultCount(productCategoryRel);
					}
				}
				// 如果厂商没有搜索到该品牌的产品，则搜索产品分类下所有的产品
				if (/* 3 == loginUser.getUserType() && */recommendCount == 0) {
					productCategoryRel.setBrandIds(null);
					if (flag) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
						}
					} else {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService
									.findRecommendCategoryProductResultCount(productCategoryRel);
						}
					}
				}
				Long startDate14 = new Date().getTime();
				logger.warn("------非定制产品list" + (startDate14 - startDate13));
				// 非定制产品list->start

			}
			if ("true".equals(onlyShowCustomization)) {
				list.addAll(customizeList);
			} else {

				// 推荐产品list->start
				Long startDate15 = new Date().getTime();
				if (flag) {// 只查推荐吧
					if (Lists.isNotEmpty(customizeList) && customizeList.size() > 0) {
						list.addAll(customizeList);
						if (Utils.enableRedisCache()) {
							recommendList = ProductCategoryRelCacher.getRecommendList(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendList = productCategoryRelService.getRecommendResult(productCategoryRel);
						}
						list.addAll(recommendList);
					} else {
						if (Utils.enableRedisCache()) {
							list = ProductCategoryRelCacher.getRecommendList(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							list = productCategoryRelService.getRecommendResult(productCategoryRel);
						}
					}
				} else {
					// 查询推荐和分类
					if (Lists.isNotEmpty(customizeList) && customizeList.size() > 0) {
						list.addAll(customizeList);
						if (Utils.enableRedisCache()) {
							recommendList = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendList = productCategoryRelService
									.findRecommendCategoryProductResult(productCategoryRel);
						}
						list.addAll(recommendList);
					} else {
						if (Utils.enableRedisCache()) {
							list = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							list = productCategoryRelService.findRecommendCategoryProductResult(productCategoryRel);
						}
					}
				}
				Long startDate16 = new Date().getTime();
				logger.warn("------推荐产品list:" + (startDate16 - startDate15));
				// 推荐产品list->end

			}
			// list查询->end

			// 排序 推荐 小类 匹配度 使用量 色系->start
			Long startDate19 = new Date().getTime();
			list = getProductList(list, productCategoryRel);
			Long startDate20 = new Date().getTime();
			logger.warn("------排序总时间:" + (startDate20 - startDate19));
			// 排序 推荐 小类 匹配度 使用量 色系->end

			total = recommendCount + customizeCount;

			// 分页->start
			Long startDate20_1 = new Date().getTime();
			list = Utils.paging(list, start, limit);
			Long startDate20_2 = new Date().getTime();
			logger.warn("------分页:" + (startDate20_2 - startDate20_1));
			// 分页->end

			// 获取需要自动携带白模产品的配置->start
			Long startDate20_3 = new Date().getTime();
			Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
			String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
					"design.designPlan.autoCarryBaimoProducts", "");
			if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
				JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
				try {
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
			Long startDate20_4 = new Date().getTime();
			logger.info("------获取需要自动携带白模产品的配置:" + (startDate20_4 - startDate20_3));
			// 获取需要自动携带白模产品的配置->end

			// 附加属性->start
			Long startDate17 = new Date().getTime();
			for (CategoryProductResult productResult : list) {
				BaseProduct baseProduct = null;
				// join 查询大小类信息,是否是硬装,是否是组合
				Map<String,Integer> InfoByIdMap = new HashMap<>();
				InfoByIdMap.put("id",productResult.getProductId());
				InfoByIdMap.put("designTempletId",designPlan.getDesignTemplateId());
				baseProduct = baseProductService.getInfoById(InfoByIdMap);

				if (baseProduct == null) {
					logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
					continue;
				}
				productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
						designPlanProduct, autoCarryMap, /*loginUser,*/ request);
				/*
				 * logger.warn("------decorationProductInfoV3耗时:"+(new
				 * Date().getTime()-startTime));
				 */
			}
			Long startDate18 = new Date().getTime();
			logger.info("------附加属性:" + (startDate18 - startDate17));
			// 附加属性->end

			/** 列表排序,同小类的排在前面，同大类其次，剩余的排最后 */
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!", productCategoryRel.getMsgId());
		}
		Long startDateEnd = new Date().getTime();
		logger.warn("------查询接口总耗时:" + (startDateEnd - startDate));
		return new ResponseEnvelope<CategoryProductResult>(total, list, productCategoryRel.getMsgId());
	}

	/**
	 * 根据分类查询产品接口
	 *
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProduct_old_")
	@ResponseBody
	public Object searchProductV2(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelNumber) {
	/*	?queryType
		?productName= 
		productModelNumber
		categoryCode
		
		loginUser.getId()
		    spaceCommonId=1577     -
		    templateProductId=1785 -
            productTypeValue=3 -
			smallTypeValue=8 -
			categoryCode=qiangm
			planProductId=19140 -
			designPlanId=608 -
			houseType=3 -适合什么房间
			
			start=0 -
			limit=30 -
			msgId=10*/
		if("0".equals(templateProductId)){
			templateProductId="";
		}
		Long startDate = System.currentTimeMillis();

		// 参数验证->start
		String msg = "";
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0) {
			designPlan = designPlanService.get(designPlanId);
		}
		if (designPlan == null) {
			msg = "找不到该设计方案：" + designPlanId;
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		// 参数验证->end

		int limit = productCategoryRel.getLimit();
		int start = productCategoryRel.getStart();
		long total = 0;
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		DesignPlanProduct designPlanProduct = sethouseTypeSearchCondition(productCategoryRel, houseType,planProductId);
		
		// 基本信息->start
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			productCategoryRel = (ProductCategoryRelSearch) JsonUtil.getJsonToBean(jsonStr,
					ProductCategoryRelSearch.class);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// 基本信息->end

		// 获取黑名单配置信息(huangsongbo 2017.1.11)->start
		Long startDate1 = System.currentTimeMillis();
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		productCategoryRel.setBlacklistSet(blacklist);
		Long startDate2 = System.currentTimeMillis();
		logger.error("------获取黑名单配置:" + (startDate2 - startDate1));
		// 获取黑名单配置信息(huangsongbo 2017.1.11)->end

		// 大小类,产品分类查询条件->start
		Long startDate3 = System.currentTimeMillis();
		// 加入产品编码或型号搜索条件
		if (productCategoryRel.getCategoryCode().contains(",")) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		} else {
			productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		}
		if (StringUtils.isNotBlank(productModelNumber)) {
			productCategoryRel.setProductModelNumber(productModelNumber.trim());
		}
		productCategoryRel.setSpaceCommonId(spaceCommonId);
		productCategoryRel.setProductTypeValue(productTypeValue);
		// 获取当前产品的大类和小类,可以删除
		// 获取产品大小类信息(配置写法去掉.修复数据字典中小类和白模_小类的编码一致.eg:basic_beij->beij)。排序时使用
		SysDictionary bigSd = null;
		SysDictionary bmSmallSd = null;
		SysDictionary smallSd = null;
		if (StringUtils.isNotBlank(productTypeValue)) {
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType",
					Integer.valueOf(productTypeValue));
		}
		if (bigSd != null && StringUtils.isNotBlank(smallTypeValue)) {
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(smallTypeValue));
			// 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
			smallSd = sysDictionaryService.dealWithInconformity(bigSd, bmSmallSd);
			if (smallSd != null) {
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			}else{
				smallSd = bmSmallSd;
			}
		}

		if (loginUser.getUserType() == 1) {
			productCategoryRel.setIsInternalUser("yes");
		}
		// 用户Id
		productCategoryRel.setUserId(loginUser.getId());
		Integer templatePlanProductId = -1;
		BaseProduct productSelected = null;

		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->start
		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->end
		Long startDate7 = System.currentTimeMillis();
		if (planProductId != null && planProductId != 0) {
			if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0) {
				if (Utils.enableRedisCache()) {
					productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
				} else {
					productSelected = baseProductService.get(designPlanProduct.getProductId());
				}
				// 获取查询属性产品的条件
				productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
						designPlanProduct.getProductId());

			}
			productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
			productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
			templatePlanProductId = designPlanProduct.getPlanProductId();
			productCategoryRel.setDesignProductId(templatePlanProductId);
			if (StringUtils.isBlank(templateProductId)) {
				templateProductId = productSelected.getBmIds();
			}
			if (StringUtils.isNotBlank(templateProductId)) {
				String[] arraytemplateProductId = templateProductId.split(",");
				productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
			}

			DesignTemplet designTemplet = null;
			if (Utils.enableRedisCache()) {
				designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
			} else {
				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
			}
			if (designTemplet == null) {
				logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId()
						+ ",designId=" + designPlan.getId());
			} else {
				if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
					logger.info("designTemplet.designCode is null ... templeId="
							+ designPlan.getDesignTemplateId() + ",designId=" + designPlan.getId());
				}
			}
		}
		Long startDate8 = System.currentTimeMillis();
		logger.info("------planProductId不为空的逻辑:" + (startDate8 - startDate7));

		// 加入大缓存
		// 生成key
		Map<Object, Object> paramsMap = new HashMap<Object, Object>();
//		paramsMap.put("spaceCommonId", spaceCommonId);
		paramsMap.put("productTypeValue", productTypeValue);
		paramsMap.put("smallTypeValue", smallSd.getValue());
		//白膜不同搜索产品列表不同
		paramsMap.put("templateProductId", templateProductId);
		paramsMap.put("categoryCode", productCategoryRel.getCategoryCode());
		//属性过滤缓存
		if( productCategoryRel.getAttributeConditionList() != null && productCategoryRel.getAttributeConditionList().size() > 0 ){
			String str = "";
			for(String condition : productCategoryRel.getAttributeConditionList()){
				str += condition+",";
			}
			paramsMap.put("attributeConditionList", str);
		}
//		if( productCategoryRel.getCategoryCode().equals("meng") || productCategoryRel.getCategoryCode().equals("qiangm")){
//		paramsMap.put("planProductId", planProductId);
//		paramsMap.put("designPlanId", designPlanId);
		paramsMap.put("houseType", houseType);
		paramsMap.put("productModelNumber", productCategoryRel.getProductModelNumber());
		/*paramsMap.put("start",productCategoryRel.getStart());
		paramsMap.put("limit",productCategoryRel.getLimit());*/
		//CommonCacher.removeAll(ModuleType.BaseProduct,"searchProduct", paramsMap);
		ResponseEnvelope<CategoryProductResult> result = null;
		if (Utils.enableRedisCache()) {
			result = CommonCacher.getAll(ModuleType.BaseProduct,
					"searchProduct", paramsMap);
		}
		
		try {
			if (result != null) {
				list = result.getDatalist();
				total = result.getTotalCount();
			} else {
				// 产品对应白膜信息->start
				BaseProduct baimoProduct = new BaseProduct();
				if (designPlanProduct != null) {
					if (designPlanProduct.getInitProductId() != null
							&& designPlanProduct.getInitProductId().intValue() > 0) {
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
						} else {
							baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
						}
					} else {
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(Utils.getIntValue(templateProductId));
						} else {
							baimoProduct = baseProductService.get(Utils.getIntValue(templateProductId));
						}
					}
				}
				// 产品对应白膜信息->end

				// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
				// 空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
				String onlyShowCustomization = null;
				if (StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
						&& new Integer(productTypeValue).intValue() > 0 && new Integer(smallTypeValue).intValue() > 0) {

					//是否为硬装产品
					SysDictionary sysDictionary = new SysDictionary();
					sysDictionary.setValue(Integer.valueOf(productTypeValue));
					sysDictionary.setSmallValue(Integer.valueOf(smallTypeValue));
					sysDictionary =  sysDictionaryService.checkType(sysDictionary);//需调整
					//att4等于1表示为硬装产品
					if( "1".equals(sysDictionary.getAtt4()) ){
						onlyShowCustomization = "false";
					}else if(StringUtils.equals("1", sysDictionary.getAtt6())){//样板房定制产品
						onlyShowCustomization = "false";
					}

					String bjType = Utils.getValue("app.smallProductType.stretch", "");
					boolean isShowbgWall = Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(),
							bjType);
					if (isShowbgWall) {
						onlyShowCustomization = "true";
					}
					productCategoryRel = productCategoryRelService.bgWallAndSpecialTypeInfo(productCategoryRel, isShowbgWall, bigSd, smallSd,
							baimoProduct, productSelected);
				}

				// 显示所有分类相关数据
				productCategoryRel.setOnlyShowRecommend(false);
				productCategoryRel.setExceptRecommend(false);

				// 设置序列号查询条件(huangsongbo 2017.1.11)
				Long startDate8_1 = System.currentTimeMillis();
				List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
				productCategoryRel.setBaseProduct(baseProductList);
				Long startDate8_2 = System.currentTimeMillis();
				logger.info("------设置序列号查询条件:" + (startDate8_2 - startDate8_1));
				// 设置序列号查询条件(huangsongbo 2017.1.11)->end

				List<CategoryProductResult> customizeList = new ArrayList<>();// 定制
				List<CategoryProductResult> recommendList = new ArrayList<>();// 推荐和分类
				int customizeCount = 0;
				int recommendCount = 0;
				// 查询定制产品
				if (spaceCommonId != null) {
					if ("qiangm".equals(bigSd == null ? "" : bigSd.getValuekey()) && designPlanProduct != null) {
						productCategoryRel = productCategoryRelService.getWallTypeLogic(productCategoryRel, designPlanProduct, bigSd,
								 request);
					} else if ("tianh".equals(bigSd == null ? "" : bigSd.getValuekey())) {
						// 如果查天花只显示定制产品
						onlyShowCustomization = "true";
					}

					// count查询->start
					Long startDate9 = System.currentTimeMillis();
					// 非定制只查询推荐产品
					if (!"true".equals(onlyShowCustomization)) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							recommendCount = productCategoryRelService
									.findRecommendCategoryProductResultCount(productCategoryRel);
						}
					}
					// 定制类产品的数据获取
					if (Utils.enableRedisCache()) {
						customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
					} else {
						/* 新过滤方案(done) */
						customizeCount = productCategoryRelService
								.findCustomizedCategoryProductResultCount(productCategoryRel);
					}
					// 如果是厂商，则只能查询这个厂商品牌下的产品,没有则查询除其外所有品牌的产品
					if (3 == loginUser.getUserType() && customizeCount + recommendCount == 0) {

						/* add品牌,大类,小类,产品 */
						/* 针对搜索条件(大类同,小类异的情况->返回空列表) */
						boolean falg2 = false;
						String productTypeValue2 = "0";
						if (StringUtils.equals(productTypeValue.trim(), "0")) {
							SysDictionary sysDictionaryBigType = sysDictionaryService
									.findOneByValueKeyInCache(productCategoryRel.getCategoryCode().replace(".", ""));
							if (!StringUtils.equals("productType", sysDictionaryBigType.getType())) {
								sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey("productType",
										sysDictionaryBigType.getType());
							}
							if (sysDictionaryBigType != null)
								productTypeValue2 = sysDictionaryBigType.getValue() + "";
						} else {
							productTypeValue2 = productTypeValue.trim();
						}
						// 通过序列号过滤数据（当序列号存在该大类、小类产品或品牌时，只显示该品牌或该大类或小类的产品，如果不存在则查询所有数据）
						for (BaseProduct baseProduct : baseProductList) {
							/* 识别序列号有没有和productTypeValue相同的大类 */
							if (StringUtils.equals(productTypeValue2, baseProduct.getProductTypeValue())) {
								falg2 = true;
								break;
							}
						}
						if (!falg2) {
							productCategoryRel.setBaseProduct(null);
						}
						/* add品牌,大类,小类,产品->end */

						if (!"true".equals(onlyShowCustomization)) {
							if (Utils.enableRedisCache()) {
								recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
							} else {
								/* 新过滤方案(done) */
								recommendCount = productCategoryRelService.findRecommendCategoryProductResultCount(productCategoryRel);
							}
						}
						if (Utils.enableRedisCache()) {
							customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							customizeCount = productCategoryRelService.findCustomizedCategoryProductResultCount(productCategoryRel);
						}
					}
					Long startDate10 = System.currentTimeMillis();
					logger.info("------count查询:" + (startDate10 - startDate9));
					// count查询->end

					// 定制产品list查询->start
					Long startDate11 = System.currentTimeMillis();
					if (customizeCount > 0) {
						if (Utils.enableRedisCache()) {
							customizeList = ProductCategoryRelCacher.getListCustomizedCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							customizeList = productCategoryRelService
									.findCustomizedCategoryProductResult(productCategoryRel);
						}
					}
					Long startDate12 = System.currentTimeMillis();
					logger.info("------定制产品list查询:" + (startDate12 - startDate11));
					// 定制产品list查询->end
				}

				if ("true".equals(onlyShowCustomization)) {
					list.addAll(customizeList);
				} else {
					// 推荐产品list->start
					Long startDate15 = System.currentTimeMillis();
					// 查询推荐和分类
					if (Lists.isNotEmpty(customizeList) && customizeList.size() > 0) {
						list.addAll(customizeList);
						if (Utils.enableRedisCache()) {
							recommendList = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
						} else {
							/* 新过滤方案(done) */
							/*recommendList = productCategoryRelService.findRecommendCategoryProductResult(productCategoryRel);*/
							recommendList = productCategoryRelService.findRecommendCategoryProductResultV2(productCategoryRel);
						}
						list.addAll(recommendList);
					} else {
						if (Utils.enableRedisCache()) {
							list = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
						} else {
							// old function
							/*list = productCategoryRelService.findRecommendCategoryProductResult(productCategoryRel);*/
							// new function
							list = productCategoryRelService.findRecommendCategoryProductResultV2(productCategoryRel);
						}
					}
					// 推荐产品list->end

				}
				// list查询->end

				// 排序 推荐 小类 匹配度 使用量 色系->start
				list = getProductList(list, productCategoryRel);
				// 排序 推荐 小类 匹配度 使用量 色系->end

				total = recommendCount + customizeCount;
				
				// 存入缓存
				if(Utils.enableRedisCache()){
					CommonCacher.addAll(ModuleType.BaseProduct, "searchProduct",
							paramsMap, new ResponseEnvelope<>(total, list, productCategoryRel.getMsgId()));
				}
			}
			
			// 分页->start
			list = Utils.paging(list, start, limit);
			// 分页->end

			// 获取需要自动携带白模产品的配置->start
			Long startDate20_3 = System.currentTimeMillis();
			Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
			String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
					"design.designPlan.autoCarryBaimoProducts", "");
			if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
				JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
				try {
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
			// 获取需要自动携带白模产品的配置->end

			// 附加属性->start
			Long startDate17 = System.currentTimeMillis();
			/*for (CategoryProductResult productResult : list) {*/
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				CategoryProductResult productResult = list.get(i);
				// 缓存key
				Map<Object, Object> keyMap = new HashMap<Object, Object>();
				keyMap.put("productId", productResult.getProductId());
				keyMap.put("designPlanId", designPlan.getId());
				if(designPlanProduct != null){
					keyMap.put("designPlanProductId", designPlanProduct.getId());
				}
				// 缓存取数据
				ResponseEnvelope<CategoryProductResult> responseEnvelopeProductResult = null;
				if(Utils.enableRedisCache()){
					responseEnvelopeProductResult = CommonCacher.getAll(ModuleType.BaseProduct, "decorationProductInfoV3", keyMap);
				}
				if(responseEnvelopeProductResult == null){
					BaseProduct baseProduct = null;
					// join 查询大小类信息,是否是硬装,是否是组合
					Map<String,Integer> InfoByIdMap = new HashMap<>();
					InfoByIdMap.put("id",productResult.getProductId());
					InfoByIdMap.put("designTempletId",designPlan.getDesignTemplateId());
					baseProduct = baseProductService.getInfoById(InfoByIdMap);

					if (baseProduct == null) {
						logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
						continue;
					}
					/*productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
							designPlanProduct, autoCarryMap, loginUser, request);*/
					productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
							designPlanProduct, autoCarryMap, request);
					if(Utils.enableRedisCache()){
						CommonCacher.addAll(ModuleType.BaseProduct, "decorationProductInfoV3", keyMap, new ResponseEnvelope<CategoryProductResult>(productResult));
					}
				}else{
					CategoryProductResult productResultFromCache = (CategoryProductResult) responseEnvelopeProductResult.getObj();
					list.remove(i);
					list.add(i, productResultFromCache);
				}
				/*
				 * logger.warn("------decorationProductInfoV3耗时:"+(new
				 * Date().getTime()-startTime));
				 */
			}
			// 附加属性->end

			/** 列表排序,同小类的排在前面，同大类其次，剩余的排最后 */
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!", productCategoryRel.getMsgId());
		}
		return new ResponseEnvelope<CategoryProductResult>(total, list, productCategoryRel.getMsgId());
	}
	
	/**
	 * 根据分类查询产品接口
	 *
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	/*@RequestMapping(value = "/searchProduct")*/
	@RequestMapping(value = "/searchProductV2_")
	@ResponseBody
	public Object searchProductV2_(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelNumber) {
	/*	?queryType
		?productName= 
		productModelNumber
	
		
		    loginUser.getId()
		    spaceCommonId=1577     - 空间
		    templateProductId=1785 -样板房的产品id
            productTypeValue=3     -选中产品的大类
			smallTypeValue=8     ---选中产品的小类
			categoryCode=qiangm --选中的节点
			planProductId=19140 -- 选中的产品
			productModelNumber  --模型编码
			houseType=3         -适合什么房间
			start=0 -  分页1
			limit=30 - 分页2
			msgId=10 - 标识
			
			designPlanId=608    --设计方案id
			
			*/
	
		logger.info("开始搜索产品......");
		logger.info("分类搜索url："+request.getRequestURI());
		Long startDate = System.currentTimeMillis();
		// 参数验证->start
		String msg = "";
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0) {
			designPlan = designPlanService.get(designPlanId);
		}
		if (designPlan == null) {
			msg = "找不到该设计方案：" + designPlanId;
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		 

		/*  loginUser.getId()
		    spaceCommonId=1577     - 空间
		    templateProductId=1785 -样板房的产品id
            productTypeValue=3     -选中产品的大类
			smallTypeValue=8     ---选中产品的小类
			categoryCode=qiangm --选中的节点
			planProductId=19140 -- 选中的产品
			productModelNumber  --模型编码
			houseType=3         -适合什么房间
			start=0 -  分页1
			limit=30 - 分页2
			msgId=10 - 标识*/
		// 基本信息->end
		ResponseEnvelope<CategoryProductResult> result = new  ResponseEnvelope<CategoryProductResult>();
		if (Utils.enableRedisCache()) {
			result = ProductCategoryRelCacher.searchProduct(productCategoryRel, request, houseType, designPlanId, planProductId, spaceCommonId, templateProductId, productTypeValue, smallTypeValue, queryType, productModelNumber, loginUser, designPlan);
		}else{
			result = productCategoryRelService.searchProduct(productCategoryRel, request, houseType, designPlanId
					, planProductId, spaceCommonId
					, templateProductId, productTypeValue, smallTypeValue, queryType, productModelNumber
					, loginUser, designPlan);
		}
		return result;
	}
	
	/**
	 * 根据分类查询产品接口
	 * 1.逻辑修改(count+list逻辑修改为只查list)
	 * 2.收藏状态放在分页后查询
	 * 3.sql优化
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProductV3_")
	@ResponseBody
	public Object searchProductV3(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelNumber) {

		if ("0".equals(templateProductId)) {
			templateProductId = "";
		}
		Long startDate = new Date().getTime();

		// 参数验证->start
		String msg = "";
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0) {
			designPlan = designPlanService.get(designPlanId);
		}
		if (designPlan == null) {
			msg = "找不到该设计方案：" + designPlanId;
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		// 参数验证->end

		int limit = productCategoryRel.getLimit();
		int start = productCategoryRel.getStart();
		long total = 0;
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)
		DesignPlanProduct designPlanProduct = sethouseTypeSearchCondition(productCategoryRel, houseType, planProductId);

		// 基本信息->start
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			productCategoryRel = (ProductCategoryRelSearch) JsonUtil.getJsonToBean(jsonStr,
					ProductCategoryRelSearch.class);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// 基本信息->end

		// 获取黑名单配置信息(huangsongbo 2017.1.11)->start
		Long startDate1 = new Date().getTime();
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		productCategoryRel.setBlacklistSet(blacklist);
		Long startDate2 = new Date().getTime();
		logger.warn("------获取黑名单配置:" + (startDate2 - startDate1));
		// 获取黑名单配置信息(huangsongbo 2017.1.11)->end

		// 大小类,产品分类查询条件->start
		Long startDate3 = new Date().getTime();
		// 加入产品编码或型号搜索条件
		if (productCategoryRel.getCategoryCode().contains(",")) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		} else {
			productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		}
		if (StringUtils.isNotBlank(productModelNumber)) {
			productCategoryRel.setProductModelNumber(productModelNumber.trim());
		}
		productCategoryRel.setSpaceCommonId(spaceCommonId);
		productCategoryRel.setProductTypeValue(productTypeValue);
		
		// 获取当前产品的大类和小类,可以删除
		// 获取产品大小类信息(配置写法去掉.修复数据字典中小类和白模_小类的编码一致.eg:basic_beij->beij)。排序时使用
		SysDictionary bigSd = null;
		SysDictionary bmSmallSd = null;
		SysDictionary smallSd = null;
		if (StringUtils.isNotBlank(productTypeValue)) {
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue));
		}
		if (bigSd != null && StringUtils.isNotBlank(smallTypeValue)) {
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(smallTypeValue));
			// 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
			smallSd = sysDictionaryService.dealWithInconformity(bigSd, bmSmallSd);
			if (smallSd != null) {
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			} else {
				smallSd = bmSmallSd;
			}
		}
		Long startDate4 = new Date().getTime();
		logger.warn("------大小类信息查询条件:" + (startDate4 - startDate3));
		// 大小类,产品分类查询条件->end

		// 决定查询的产品的状态(userType = 1的用户可以查看到上架+测试中的产品))
		Integer userType = loginUser.getUserType() == null ? 0 : loginUser.getUserType();
		if (userType == 1) {
			productCategoryRel.setIsInternalUser("yes");
		}

		// 用户Id
		productCategoryRel.setUserId(loginUser.getId());
		Integer templatePlanProductId = -1;
		BaseProduct productSelected = null;

		Long startDate7 = new Date().getTime();
		if (planProductId != null && planProductId != 0) {
			if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0) {
				if (Utils.enableRedisCache()) {
					productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
				} else {
					productSelected = baseProductService.get(designPlanProduct.getProductId());
				}
				// 获取查询属性产品的条件
				productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
						designPlanProduct.getProductId());

			}
			productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
			productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
			templatePlanProductId = designPlanProduct.getPlanProductId();
			productCategoryRel.setDesignProductId(templatePlanProductId);
			if (StringUtils.isBlank(templateProductId)) {
				templateProductId = productSelected.getBmIds();
			}
			if (StringUtils.isNotBlank(templateProductId)) {
				String[] arraytemplateProductId = templateProductId.split(",");
				productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
			}

			DesignTemplet designTemplet = null;
			if (Utils.enableRedisCache()) {
				designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
			} else {
				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
			}
			if (designTemplet == null) {
				logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
						+ designPlan.getId());
			} else {
				if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
					logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
							+ ",designId=" + designPlan.getId());
				}
			}
		}
		Long startDate8 = new Date().getTime();
		logger.warn("------planProductId不为空的逻辑:" + (startDate8 - startDate7));

		// 加入大缓存(内部)
		// 生成key
		Map<Object, Object> paramsMapInside = new HashMap<Object, Object>();
		paramsMapInside.put("productTypeValue", productTypeValue);
		paramsMapInside.put("smallTypeValue", smallSd.getValue());
		// 白膜不同搜索产品列表不同
		paramsMapInside.put("templateProductId", templateProductId);
		paramsMapInside.put("categoryCode", productCategoryRel.getCategoryCode());
		// 属性过滤缓存
		if (productCategoryRel.getAttributeConditionList() != null
				&& productCategoryRel.getAttributeConditionList().size() > 0) {
			String str = "";
			for (String condition : productCategoryRel.getAttributeConditionList()) {
				str += condition + ",";
			}
			paramsMapInside.put("attributeConditionList", str);
		}
		paramsMapInside.put("houseType", houseType);
		paramsMapInside.put("productModelNumber", productModelNumber);
		Map<Object, Object> paramsMapOutside = new HashMap<Object, Object>();
		paramsMapOutside.putAll(paramsMapInside);
		paramsMapOutside.put("start", start);
		paramsMapOutside.put("limit", limit);

		try {
			//最外层缓存(直接出结果集)
			if (Utils.enableRedisCache()) {
				ResponseEnvelope<CategoryProductResult> responseEnvelope = CommonCacher.getAll(ModuleType.BaseProduct, "searchProduct", paramsMapOutside);
				if(responseEnvelope != null){
					return new ResponseEnvelope<CategoryProductResult>(responseEnvelope.getTotalCount(), responseEnvelope.getDatalist(), productCategoryRel.getMsgId());
				}
			}
			
			//第二层缓存(list,未分页)
			ResponseEnvelope<CategoryProductResult> result = null;
			if (Utils.enableRedisCache()) {
				result = CommonCacher.getAll(ModuleType.BaseProduct, "searchProduct", paramsMapInside);
			}
			
			if (result != null) {
				list = result.getDatalist();
				total = result.getTotalCount();
			} else {

				Long startDate7_1 = new Date().getTime();
				// 产品对应白膜信息->start
				BaseProduct baimoProduct = new BaseProduct();
				if (designPlanProduct != null) {
					if (designPlanProduct.getInitProductId() != null
							&& designPlanProduct.getInitProductId().intValue() > 0) {
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
						} else {
							baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
						}
					} else {
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(Utils.getIntValue(templateProductId));
						} else {
							baimoProduct = baseProductService.get(Utils.getIntValue(templateProductId));
						}
					}
				}
				// 产品对应白膜信息->end

				// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
				// 空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
				String onlyShowCustomization = null;
				if (StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
						&& new Integer(productTypeValue).intValue() > 0 && new Integer(smallTypeValue).intValue() > 0) {
					String bjType = Utils.getValue("app.smallProductType.stretch", "");
					boolean isShowbgWall = Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(),
							bjType);
					if (isShowbgWall) {
						onlyShowCustomization = "true";
					}
					productCategoryRelService.bgWallAndSpecialTypeInfo(productCategoryRel, isShowbgWall, bigSd, smallSd,
							baimoProduct, productSelected);
				}
				Long startDate7_2 = new Date().getTime();
				logger.warn("------other查询条件:" + (startDate7_2 - startDate7_1));

				// 显示所有分类相关数据
				productCategoryRel.setOnlyShowRecommend(false);
				productCategoryRel.setExceptRecommend(false);

				// 设置序列号查询条件(huangsongbo 2017.1.11)
				if(userType == 3){
					Long startDate8_1 = new Date().getTime();
					List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
					productCategoryRel.setBaseProduct(baseProductList);
					Long startDate8_2 = new Date().getTime();
					logger.warn("------设置序列号查询条件:" + (startDate8_2 - startDate8_1));
				}
				// 设置序列号查询条件(huangsongbo 2017.1.11)->end

				List<CategoryProductResult> customizeList = new ArrayList<>();// 定制
				List<CategoryProductResult> recommendList = new ArrayList<>();// 推荐和分类
				/*int customizeCount = 0;
				int recommendCount = 0;*/

				// 查询定制产品
				if ("qiangm".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					productCategoryRelService.getWallTypeLogic(productCategoryRel, designPlanProduct, bigSd,  request);
				} else if ("tianh".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					// 如果查天花只显示定制产品
					onlyShowCustomization = "true";
				}

				// list+count查询->start
				Long startDate9 = new Date().getTime();
				if(userType == 3){
					// 考虑序列号过滤
					customizeList = productCategoryRelService.findCustomizedCategoryProductResultV2(productCategoryRel);
					list.addAll(customizeList);
					if(StringUtils.equals("true", onlyShowCustomization)){
						if(list.size() > 0){
							
						}else{
							// 去掉序列号过滤条件
							productCategoryRel.setBaseProduct(null);
							customizeList = productCategoryRelService.findCustomizedCategoryProductResultV2(productCategoryRel);
							list.addAll(customizeList);
						}
					}else{
						// 推荐+分类
						recommendList = productCategoryRelService.findRecommendCategoryProductResultV2(productCategoryRel);
						if(customizeList.size() + recommendList.size() != 0){
							list.addAll(recommendList);
						}else{
							// 去掉序列号过滤条件
							productCategoryRel.setBaseProduct(null);
							customizeList = productCategoryRelService.findCustomizedCategoryProductResultV2(productCategoryRel);
							recommendList = productCategoryRelService.findRecommendCategoryProductResultV2(productCategoryRel);
							list.addAll(customizeList);
							list.addAll(recommendList);
						}
					}
				}else{
					// 不考虑序列号过滤
					// 查询定制
					customizeList = productCategoryRelService.findCustomizedCategoryProductResultV2(productCategoryRel);
					list.addAll(customizeList);
					if(StringUtils.equals("true", onlyShowCustomization)){
						
					}else{
						// 推荐+分类
						recommendList = productCategoryRelService.findRecommendCategoryProductResultV2(productCategoryRel);
						list.addAll(recommendList);
					}
				}
				total = list.size();
				Long startDate10 = new Date().getTime();
				logger.warn("------list+count查询:" + (startDate10 - startDate9));
				// list+count查询->end

				// 排序 推荐 小类 匹配度 使用量 色系->start
				Long startDate19 = new Date().getTime();
				list = getProductList(list, productCategoryRel);
				Long startDate20 = new Date().getTime();
				logger.warn("------排序总时间:" + (startDate20 - startDate19));
				// 排序 推荐 小类 匹配度 使用量 色系->end

				// 存入缓存
				if(Utils.enableRedisCache()){
					CommonCacher.addAll(ModuleType.BaseProduct, "searchProduct", paramsMapInside,
							new ResponseEnvelope<>(total, list, productCategoryRel.getMsgId()));
				}
			}
				// 分页->start
				Long startDate20_1 = new Date().getTime();
				list = Utils.paging(list, start, limit);
				Long startDate20_2 = new Date().getTime();
				logger.warn("------分页:" + (startDate20_2 - startDate20_1));
				// 分页->end

				// 获取需要自动携带白模产品的配置->start
				Long startDate20_3 = new Date().getTime();
				Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
				String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
						"design.designPlan.autoCarryBaimoProducts", "");
				if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
					JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
					try {
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
				Long startDate20_4 = new Date().getTime();
				logger.warn("------获取需要自动携带白模产品的配置:" + (startDate20_4 - startDate20_3));
				// 获取需要自动携带白模产品的配置->end

				// 附加属性->start
				Long startDate17 = new Date().getTime();
				int listSize = list.size();
				
				// 得到产品idList(为了一次性查出数据)
				List<Integer> idList = new ArrayList<Integer>();
				for(CategoryProductResult categoryProductResult : list){
					idList.add(categoryProductResult.getProductId());
				}
				// 一次性查出数据(查询大小类信息,是否是硬装,是否是组合)
				List<BaseProduct> baseProductInfoList = baseProductService.getInfoByIdList(idList, designPlan.getDesignTemplateId(), loginUser.getId());
				// 整理数据成map:(productId:baseProduct)
				Map<Integer, BaseProduct> baseProductInfoMap = new HashMap<>();
				for(BaseProduct baseProduct : baseProductInfoList){
					baseProductInfoMap.put(baseProduct.getId(), baseProduct);
				}
				
				for (int i = 0; i < listSize; i++) {
					CategoryProductResult productResult = list.get(i);
					// join 查询大小类信息,是否是硬装,是否是组合
					/*Map<String, Integer> InfoByIdMap = new HashMap<>();
					InfoByIdMap.put("id", productResult.getProductId());
					InfoByIdMap.put("designTempletId", designPlan.getDesignTemplateId());
					baseProduct = baseProductService.getInfoById(InfoByIdMap);*/
					BaseProduct baseProduct = baseProductInfoMap.get(productResult.getProductId());
					if (baseProduct == null) {
						logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
						continue;
					}
					productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
							designPlanProduct, autoCarryMap, request);
				}
				Long startDate18 = new Date().getTime();
				logger.warn("------附加属性:" + (startDate18 - startDate17));
				// 附加属性->end
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!", productCategoryRel.getMsgId());
		}
		Long startDateEnd = new Date().getTime();
		logger.warn("------查询接口总耗时:" + (startDateEnd - startDate));
		if(Utils.enableRedisCache()){
			CommonCacher.addAll(ModuleType.BaseProduct, "searchProduct", paramsMapOutside,
					new ResponseEnvelope<>(total, list, productCategoryRel.getMsgId()));
		}
		return new ResponseEnvelope<CategoryProductResult>(total, list, productCategoryRel.getMsgId());
	}

	/**
	 * 根据分类查询产品接口
	 *	1.pro_category分类long_code拆分(为了不用like查询,提高sql执行效率)
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProductV4_")
	@ResponseBody
	public Object searchProductV4(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelNumber) {

		if ("0".equals(templateProductId)) {
			templateProductId = "";
		}
		Long startDate = new Date().getTime();

		// 参数验证->start
		String msg = "";
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0) {
			designPlan = designPlanService.get(designPlanId);
		}
		if (designPlan == null) {
			msg = "找不到该设计方案：" + designPlanId;
			return new ResponseEnvelope<ProductCategoryRel>(false, msg, productCategoryRel.getMsgId());
		}
		// 参数验证->end

		int limit = productCategoryRel.getLimit();
		int start = productCategoryRel.getStart();
		long total = 0;
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)
		DesignPlanProduct designPlanProduct = sethouseTypeSearchCondition(productCategoryRel, houseType, planProductId);

		// 基本信息->start
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			productCategoryRel = (ProductCategoryRelSearch) JsonUtil.getJsonToBean(jsonStr,
					ProductCategoryRelSearch.class);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		// 基本信息->end

		// 获取黑名单配置信息(huangsongbo 2017.1.11)->start
		Long startDate1 = new Date().getTime();
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		productCategoryRel.setBlacklistSet(blacklist);
		Long startDate2 = new Date().getTime();
		logger.warn("------获取黑名单配置:" + (startDate2 - startDate1));
		// 获取黑名单配置信息(huangsongbo 2017.1.11)->end

		// 大小类,产品分类查询条件->start
		Long startDate3 = new Date().getTime();
		// 加入产品编码或型号搜索条件
		if (productCategoryRel.getCategoryCode().contains(",")) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		} else {
			productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		}
		if (StringUtils.isNotBlank(productModelNumber)) {
			productCategoryRel.setProductModelNumber(productModelNumber.trim());
		}
		productCategoryRel.setSpaceCommonId(spaceCommonId);
		productCategoryRel.setProductTypeValue(productTypeValue);
		
		// 获取当前产品的大类和小类,可以删除
		// 获取产品大小类信息(配置写法去掉.修复数据字典中小类和白模_小类的编码一致.eg:basic_beij->beij)。排序时使用
		SysDictionary bigSd = null;
		SysDictionary bmSmallSd = null;
		SysDictionary smallSd = null;
		if (StringUtils.isNotBlank(productTypeValue)) {
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue));
		}
		if (bigSd != null && StringUtils.isNotBlank(smallTypeValue)) {
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(smallTypeValue));
			// 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
			smallSd = sysDictionaryService.dealWithInconformity(bigSd, bmSmallSd);
			if (smallSd != null) {
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			} else {
				smallSd = bmSmallSd;
			}
		}
		Long startDate4 = new Date().getTime();
		logger.warn("------大小类信息查询条件:" + (startDate4 - startDate3));
		// 大小类,产品分类查询条件->end

		// 决定查询的产品的状态(userType = 1的用户可以查看到上架+测试中的产品))
		Integer userType = loginUser.getUserType() == null ? 0 : loginUser.getUserType();
		if (userType == 1) {
			productCategoryRel.setIsInternalUser("yes");
		}

		// 用户Id
		productCategoryRel.setUserId(loginUser.getId());
		Integer templatePlanProductId = -1;
		BaseProduct productSelected = null;

		Long startDate7 = new Date().getTime();
		if (planProductId != null && planProductId != 0) {
			if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0) {
				if (Utils.enableRedisCache()) {
					productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
				} else {
					productSelected = baseProductService.get(designPlanProduct.getProductId());
				}
				// 获取查询属性产品的条件
				productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
						designPlanProduct.getProductId());

			}
			productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
			productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
			templatePlanProductId = designPlanProduct.getPlanProductId();
			productCategoryRel.setDesignProductId(templatePlanProductId);
			if (StringUtils.isBlank(templateProductId)) {
				templateProductId = productSelected.getBmIds();
			}
			if (StringUtils.isNotBlank(templateProductId)) {
				String[] arraytemplateProductId = templateProductId.split(",");
				productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
			}

			DesignTemplet designTemplet = null;
			if (Utils.enableRedisCache()) {
				designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
			} else {
				designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
			}
			if (designTemplet == null) {
				logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
						+ designPlan.getId());
			} else {
				if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
					logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
							+ ",designId=" + designPlan.getId());
				}
			}
		}
		Long startDate8 = new Date().getTime();
		logger.warn("------planProductId不为空的逻辑:" + (startDate8 - startDate7));

		// 加入大缓存(内部)
		// 生成key
		Map<Object, Object> paramsMapInside = new HashMap<Object, Object>();
		paramsMapInside.put("productTypeValue", productTypeValue);
		paramsMapInside.put("smallTypeValue", smallSd.getValue());
		// 白膜不同搜索产品列表不同
		paramsMapInside.put("templateProductId", templateProductId);
		paramsMapInside.put("categoryCode", productCategoryRel.getCategoryCode());
		// 属性过滤缓存
		if (productCategoryRel.getAttributeConditionList() != null
				&& productCategoryRel.getAttributeConditionList().size() > 0) {
			String str = "";
			for (String condition : productCategoryRel.getAttributeConditionList()) {
				str += condition + ",";
			}
			paramsMapInside.put("attributeConditionList", str);
		}
		paramsMapInside.put("houseType", houseType);
		paramsMapInside.put("productModelNumber", productModelNumber);
		Map<Object, Object> paramsMapOutside = new HashMap<Object, Object>();
		paramsMapOutside.putAll(paramsMapInside);
		paramsMapOutside.put("start", start);
		paramsMapOutside.put("limit", limit);

		try {
			//最外层缓存(直接出结果集)
			if (Utils.enableRedisCache()) {
				ResponseEnvelope<CategoryProductResult> responseEnvelope = CommonCacher.getAll(ModuleType.BaseProduct, "searchProduct", paramsMapOutside);
				if(responseEnvelope != null){
					return new ResponseEnvelope<CategoryProductResult>(responseEnvelope.getTotalCount(), responseEnvelope.getDatalist(), productCategoryRel.getMsgId());
				}
			}
			
			//第二层缓存(list,未分页)
			ResponseEnvelope<CategoryProductResult> result = null;
			if (Utils.enableRedisCache()) {
				result = CommonCacher.getAll(ModuleType.BaseProduct, "searchProduct", paramsMapInside);
			}
			
			if (result != null) {
				list = result.getDatalist();
				total = result.getTotalCount();
			} else {
				//查询该分类code的级别,设置查询条件
				String categoryCode = productCategoryRel.getCategoryCode().replace(".", "");
				if(StringUtils.isNotBlank(categoryCode)){
					ProCategory proCategory = proCategoryService.findOneByCode(categoryCode);
					if(proCategory != null){
						Integer level = proCategory.getLevel();
						if(level != null && level.equals(new Integer(0))){
							productCategoryRel.setFirstStageCode(categoryCode);
						}else if(level != null && level.equals(new Integer(1))){
							productCategoryRel.setSecondStageCode(categoryCode);
						}else if(level != null && level.equals(new Integer(2))){
							productCategoryRel.setThirdStageCode(categoryCode);
						}else if(level != null && level.equals(new Integer(3))){
							productCategoryRel.setFourthStageCode(categoryCode);
						}else if(level != null && level.equals(new Integer(4))){
							productCategoryRel.setFifthStageCode(categoryCode);
						}else{
							
						}
					}
				}
				
				Long startDate7_1 = new Date().getTime();
				// 产品对应白膜信息->start
				BaseProduct baimoProduct = new BaseProduct();
				if (designPlanProduct != null) {
					if (designPlanProduct.getInitProductId() != null
							&& designPlanProduct.getInitProductId().intValue() > 0) {
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
						} else {
							baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
						}
					} else {
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(Utils.getIntValue(templateProductId));
						} else {
							baimoProduct = baseProductService.get(Utils.getIntValue(templateProductId));
						}
					}
				}
				// 产品对应白膜信息->end

				// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
				// 空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
				String onlyShowCustomization = null;
				if (StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
						&& new Integer(productTypeValue).intValue() > 0 && new Integer(smallTypeValue).intValue() > 0) {
					String bjType = Utils.getValue("app.smallProductType.stretch", "");
					boolean isShowbgWall = Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(),
							bjType);
					if (isShowbgWall) {
						onlyShowCustomization = "true";
					}
					productCategoryRelService.bgWallAndSpecialTypeInfo(productCategoryRel, isShowbgWall, bigSd, smallSd,
							baimoProduct, productSelected);
				}
				Long startDate7_2 = new Date().getTime();
				logger.warn("------other查询条件:" + (startDate7_2 - startDate7_1));

				// 显示所有分类相关数据
				productCategoryRel.setOnlyShowRecommend(false);
				productCategoryRel.setExceptRecommend(false);

				// 设置序列号查询条件(huangsongbo 2017.1.11)
				if(userType == 3){
					Long startDate8_1 = new Date().getTime();
					List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
					productCategoryRel.setBaseProduct(baseProductList);
					Long startDate8_2 = new Date().getTime();
					logger.warn("------设置序列号查询条件:" + (startDate8_2 - startDate8_1));
				}
				// 设置序列号查询条件(huangsongbo 2017.1.11)->end

				List<CategoryProductResult> customizeList = new ArrayList<>();// 定制
				List<CategoryProductResult> recommendList = new ArrayList<>();// 推荐和分类
				/*int customizeCount = 0;
				int recommendCount = 0;*/

				// 查询定制产品
				if ("qiangm".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					productCategoryRelService.getWallTypeLogic(productCategoryRel, designPlanProduct, bigSd,
							 request);
				} else if ("tianh".equals(bigSd == null ? "" : bigSd.getValuekey())) {
					// 如果查天花只显示定制产品
					onlyShowCustomization = "true";
				}

				// list+count查询->start
				Long startDate9 = new Date().getTime();
				if(userType == 3){
					// 考虑序列号过滤
					customizeList = productCategoryRelService.findCustomizedCategoryProductResultV3(productCategoryRel);
					list.addAll(customizeList);
					if(StringUtils.equals("true", onlyShowCustomization)){
						if(list.size() > 0){
							
						}else{
							// 去掉序列号过滤条件
							productCategoryRel.setBaseProduct(null);
							customizeList = productCategoryRelService.findCustomizedCategoryProductResultV3(productCategoryRel);
							list.addAll(customizeList);
						}
					}else{
						// 推荐+分类
						recommendList = productCategoryRelService.findRecommendCategoryProductResultV3(productCategoryRel);
						if(customizeList.size() + recommendList.size() != 0){
							list.addAll(recommendList);
						}else{
							// 去掉序列号过滤条件
							productCategoryRel.setBaseProduct(null);
							customizeList = productCategoryRelService.findCustomizedCategoryProductResultV3(productCategoryRel);
							recommendList = productCategoryRelService.findRecommendCategoryProductResultV3(productCategoryRel);
							list.addAll(customizeList);
							list.addAll(recommendList);
						}
					}
				}else{
					// 不考虑序列号过滤
					// 查询定制
					customizeList = productCategoryRelService.findCustomizedCategoryProductResultV3(productCategoryRel);
					list.addAll(customizeList);
					if(StringUtils.equals("true", onlyShowCustomization)){
						
					}else{
						// 推荐+分类
						recommendList = productCategoryRelService.findRecommendCategoryProductResultV3(productCategoryRel);
						list.addAll(recommendList);
					}
				}
				total = list.size();
				Long startDate10 = new Date().getTime();
				logger.warn("------list+count查询:" + (startDate10 - startDate9));
				// list+count查询->end

				// 排序 推荐 小类 匹配度 使用量 色系->start
				Long startDate19 = new Date().getTime();
				list = getProductList(list, productCategoryRel);
				Long startDate20 = new Date().getTime();
				logger.warn("------排序总时间:" + (startDate20 - startDate19));
				// 排序 推荐 小类 匹配度 使用量 色系->end

				// 存入缓存
				if(Utils.enableRedisCache()){
					CommonCacher.addAll(ModuleType.BaseProduct, "searchProduct", paramsMapInside,
							new ResponseEnvelope<>(total, list, productCategoryRel.getMsgId()));
				}
			}
				// 分页->start
				Long startDate20_1 = new Date().getTime();
				list = Utils.paging(list, start, limit);
				Long startDate20_2 = new Date().getTime();
				logger.warn("------分页:" + (startDate20_2 - startDate20_1));
				// 分页->end

				// 获取需要自动携带白模产品的配置->start
				Long startDate20_3 = new Date().getTime();
				Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
				String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
						"design.designPlan.autoCarryBaimoProducts", "");
				if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
					JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
					try {
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
				Long startDate20_4 = new Date().getTime();
				logger.warn("------获取需要自动携带白模产品的配置:" + (startDate20_4 - startDate20_3));
				// 获取需要自动携带白模产品的配置->end

				// 附加属性->start
				Long startDate17 = new Date().getTime();
				int listSize = list.size();
				
				// 得到产品idList(为了一次性查出数据)
				List<Integer> idList = new ArrayList<Integer>();
				for(CategoryProductResult categoryProductResult : list){
					idList.add(categoryProductResult.getProductId());
				}
				// 一次性查出数据(查询大小类信息,是否是硬装,是否是组合)
				List<BaseProduct> baseProductInfoList = new ArrayList<BaseProduct>();
				if(idList.size() > 0){
					baseProductInfoList = baseProductService.getInfoByIdList(idList, designPlan.getDesignTemplateId(), loginUser.getId());
				}
				// 整理数据成map:(productId:baseProduct)
				Map<Integer, BaseProduct> baseProductInfoMap = new HashMap<>();
				for(BaseProduct baseProduct : baseProductInfoList){
					baseProductInfoMap.put(baseProduct.getId(), baseProduct);
				}
				
				for (int i = 0; i < listSize; i++) {
					CategoryProductResult productResult = list.get(i);
					// join 查询大小类信息,是否是硬装,是否是组合
					/*Map<String, Integer> InfoByIdMap = new HashMap<>();
					InfoByIdMap.put("id", productResult.getProductId());
					InfoByIdMap.put("designTempletId", designPlan.getDesignTemplateId());
					baseProduct = baseProductService.getInfoById(InfoByIdMap);*/
					BaseProduct baseProduct = baseProductInfoMap.get(productResult.getProductId());
					if (baseProduct == null) {
						logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
						continue;
					}
					productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
							designPlanProduct, autoCarryMap, request);
				}
				Long startDate18 = new Date().getTime();
				logger.warn("------附加属性:" + (startDate18 - startDate17));
				// 附加属性->end
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!", productCategoryRel.getMsgId());
		}
		Long startDateEnd = new Date().getTime();
		logger.warn("------查询接口总耗时:" + (startDateEnd - startDate));
		if(Utils.enableRedisCache()){
			CommonCacher.addAll(ModuleType.BaseProduct, "searchProduct", paramsMapOutside,
					new ResponseEnvelope<>(total, list, productCategoryRel.getMsgId()));
		}
		return new ResponseEnvelope<CategoryProductResult>(total, list, productCategoryRel.getMsgId());
	}
	
	/**
	 * 根据分类查询产品接口
	 *	查询逻辑完全改造,建立预处理表,单表过滤查询
	 *@author huangsongbo
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/searchProductV5_old_")
	public Object searchProductV5(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			// 相当于样板房初始化该产品对应的白膜的样板房产品信息
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) Integer productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) Integer smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelOrBrandName) {
		// 参数验证 ->start
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) 
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", productCategoryRel.getMsgId());
		
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0)
			designPlan = designPlanService.get(designPlanId);
		if (designPlan == null)
			return new ResponseEnvelope<ProductCategoryRel>(false, "找不到该设计方案：" + designPlanId, productCategoryRel.getMsgId());
		
		// 用户信息
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId() == null || loginUser.getId() < 1)
			return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", productCategoryRel.getMsgId());
		// 参数验证 ->end

		if ("0".equals(templateProductId)) {
			templateProductId = "";
		}
		
		Integer limit = productCategoryRel.getLimit();
		Integer start = productCategoryRel.getStart();
		
		// 设置查询条件 ->start
		Long startTime = new Date().getTime();
		PrepProductSearchInfo prepProductSearchInfo  = new PrepProductSearchInfo();
		
		// 用户类型=1,可以查询测试+上架的产品
		if(!new Integer(1).equals(loginUser.getUserType()))
			prepProductSearchInfo.setProductStatus(new Integer(1));
		// 型号查询条件(型号/品牌名) ->start
		if(StringUtils.isNotBlank(productModelOrBrandName))
			prepProductSearchInfo.setProductModelOrBrandName(productModelOrBrandName);
		// 型号查询条件(型号/品牌名) ->end
		
		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)
		BaseProduct baimoBaseProductInit = null;
		DesignPlanProduct designPlanProduct = null;
		if(planProductId != null && planProductId > 0){
			// 设计方案产品
			designPlanProduct = designPlanProductService.get(planProductId);
			// 点击的产品初始白膜
			Integer initProductId = designPlanProduct.getInitProductId();
			if(initProductId != null && initProductId > 0){
				baimoBaseProductInit = baseProductService.get(initProductId);
			}/*else{
				// 如果没有对应的白膜(说明这个产品可能是新增出来的,则baimoBaseProductInit设置为自己)
				baimoBaseProductInit = baseProductService.get(designPlanProduct.getProductId());
			}*/
		}
		// 得到空间类型搜索条件
		List<String> HouseTypeList = getHouseTypeSearchCondition(houseType, baimoBaseProductInit);
		prepProductSearchInfo.setHouseTypeList(HouseTypeList);
		
		// 获取黑名单配置信息 ->start
		/*Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		prepProductSearchInfoSearchSimple.setBlacklistSet(blacklist);*/
		// 获取黑名单配置信息 ->end
		
		// 分类code,大类,小类 ->start
		//注:暂时只处理只有一个分类
		Integer productTypeValueInt = Integer.valueOf(productTypeValue);
		if(productTypeValueInt == null || new Integer(0).equals(productTypeValueInt)){
			
		}else{
			prepProductSearchInfo.setBigTypeValue(productTypeValueInt);
		}
		/*prepProductSearchInfo.setSmallTypeValue(Integer.valueOf(smallTypeValue));*/
		String categoryCode = productCategoryRel.getCategoryCode();
		if(StringUtils.isNotBlank(categoryCode)){
			ProCategory proCategory = proCategoryService.findOneByCode(categoryCode);
			if(proCategory != null){
				Integer level = proCategory.getLevel();
				if(level != null && level.equals(new Integer(0))){
					prepProductSearchInfo.setFirstStageCode(categoryCode);
				}else if(level != null && level.equals(new Integer(1))){
					prepProductSearchInfo.setSecondStageCode(categoryCode);
				}else if(level != null && level.equals(new Integer(2))){
					prepProductSearchInfo.setThirdStageCode(categoryCode);
				}else if(level != null && level.equals(new Integer(3))){
					prepProductSearchInfo.setFourthStageCode(categoryCode);
				}else if(level != null && level.equals(new Integer(4))){
					prepProductSearchInfo.setFifthStageCode(categoryCode);
				}else{
					prepProductSearchInfo.setCategoryLongCodeLike("." + categoryCode + ".");
				}
			}
		}
		// 分类code,大类,小类 ->end
		
		// 设置空间id,样板房id,init白膜id(判断是否是定制)查询条件 ->start
		prepProductSearchInfo.setSpaceCommonId(spaceCommonId);
		prepProductSearchInfo.setDesignTempletId(designPlan.getDesignId());
		if(baimoBaseProductInit != null){
			prepProductSearchInfo.setBaimoId(baimoBaseProductInit.getId());
		}
		// 设置空间id,样板房id,init白膜id(判断是否是定制)查询条件 ->end
		
		// 属性查询条件 ->start
		// 查询该产品的所有属性
		// 取属性
		BaseProduct baseProductProp = null;
		if(designPlanProduct != null){
			if(baimoBaseProductInit == null){
				baseProductProp = baseProductService.get(designPlanProduct.getProductId());
			}else{
				baseProductProp = baimoBaseProductInit;
			}
			// 过滤属性是依据该产品对应的初始化白膜的过滤属性
			if(baseProductProp != null){
				List<ProductPropsSimple> productPropsSimpleListBaimo = baseProductService.getProductPropsSimpleByProductId(baseProductProp.getId());
				if(productPropsSimpleListBaimo != null && productPropsSimpleListBaimo.size() > 0){
					// 取出排序属性+过滤属性
					List<ProductPropsSimple> productOrderPropList = new ArrayList<ProductPropsSimple>();
					List<ProductPropsSimple> productFilterPropList = new ArrayList<ProductPropsSimple>();
					for(ProductPropsSimple productPropsSimple : productPropsSimpleListBaimo){
						if(productPropsSimple.getIsSort() == 1){
							// 过滤属性
							productFilterPropList.add(productPropsSimple);
						}
					}
					// 处理过滤属性 ->start
					if(productFilterPropList.size() > 0){
						prepProductSearchInfo.setHasFilterProps(true);
						prepProductSearchInfo.setBaimoProductId(baseProductProp.getId());
					}
					// 处理过滤属性 ->end
					// 处理排序属性 ->start
					// 排序属性是按照点击的产品的排序属性决定匹配度
					List<ProductPropsSimple> productPropsSimpleList = baseProductService.getProductPropsSimpleByProductId(designPlanProduct.getProductId());
					if(productPropsSimpleList != null && productPropsSimpleList.size() > 0){
						// 取出排序属性+过滤属性
						for(ProductPropsSimple productPropsSimple : productPropsSimpleList){
							if(productPropsSimple.getIsSort() == 0){
								// 排序属性
								productOrderPropList.add(productPropsSimple);
							}
						}
					}
					if(productOrderPropList.size() > 0){
						prepProductSearchInfo.setHasOrderProps(true);
						prepProductSearchInfo.setProductId(designPlanProduct.getProductId());
					}
					// 处理排序属性 ->end
				}
			}
		}else{
			// 未点击产品搜索(通过分类栏直接搜索)
		}
		
		// 解析配置special.productType并设置对应的查询条件 ->start
		// 如果点击的产品是白膜,处理小类:basic_mengk变成mengk
		Integer smallTypeValueTemp = smallTypeValue;
		if(designPlanProduct != null && designPlanProduct.getProductId().equals(designPlanProduct.getInitProductId())){
			// 点击的是白膜
			// 特殊处理背景墙(basic_beij->beijing)
			if(new Integer(3).equals(productTypeValue) && new Integer(16).equals(smallTypeValue)){
				smallTypeValueTemp = 17;
			}else{
				smallTypeValueTemp = sysDictionaryService.getProductValueByBaimoValue(productTypeValue, smallTypeValue);
			}
		}
		// in/notIn
		Map<String, Object> specialProductTypeMap = getSpecialProductTypeMap(productTypeValue, smallTypeValueTemp);
		if(specialProductTypeMap.containsKey("notIn")){
			prepProductSearchInfo.setExcludeSmallTypeValue((List<Integer>)specialProductTypeMap.get("notIn"));
		}
		if(specialProductTypeMap.containsKey("in")){
			prepProductSearchInfo.setSmallTypeValue(smallTypeValueTemp);
		}
		// 解析配置special.productType并设置对应的查询条件 ->end
		
		// 解析配置filter.productLH.productSmallType并设置对应的查询条件 ->start
		String filterProductLH =Utils.getValue("filter.productLH.productSmallType", "");
		// 得到小类数据字典
		SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByTypeAndValueAndValue("productType", productTypeValue, smallTypeValue);
		if(StringUtils.isNotBlank(filterProductLH)){
			List<String> filterProductLHSmallTypeList = Utils.getListFromStr(filterProductLH);
			if(sysDictionarySmallType != null){
				String smallTypeCode = sysDictionarySmallType.getValuekey();
				if(filterProductLHSmallTypeList.indexOf(smallTypeCode) != -1){
					// 设置长,高过滤条件
					if(baimoBaseProductInit != null){
						Integer productLength = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductLength())?baimoBaseProductInit.getProductLength():"0");
						prepProductSearchInfo.setProductLength(productLength);
						Integer productHeight = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductHeight())?baimoBaseProductInit.getProductHeight():"0");
						prepProductSearchInfo.setProductHeight(productHeight);
					}
				}
			}
		}
		// 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->end

		// 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->start
		String filterProductLW =Utils.getValue("filter.productLW.productSmallType", "");
		if(StringUtils.isNotBlank(filterProductLW)){
			List<String> filterProductLWSmallTypeList = Utils.getListFromStr(filterProductLW);
			if(sysDictionarySmallType != null){
				String smallTypeCode = sysDictionarySmallType.getValuekey();
				if(filterProductLWSmallTypeList.indexOf(smallTypeCode) != -1){
					// 设置长,宽过滤条件(小于)
					if(baimoBaseProductInit != null){
						Integer productLength = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductLength())?baimoBaseProductInit.getProductLength():"0");
						prepProductSearchInfo.setLessProductLength(productLength);
						Integer productWidth = Integer.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductWidth())?baimoBaseProductInit.getProductWidth():"0");
						prepProductSearchInfo.setLessProductWidth(productWidth);
					}
				}
			}
		}
		// 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->end
		
		// 序列号查询条件 ->start
		Integer userType = loginUser.getUserType();
		if(userType == 3){
			List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
			prepProductSearchInfo.setBaseProductList(baseProductList);
		}
		// 序列号查询条件 ->end
		
		// 点击普通墙面是否要显示背景墙(当背景墙删除的时候) ->start
		// 识别是否要显示背景墙
		this.setBgWallCondition(prepProductSearchInfo, designPlanProduct);
		// 点击普通墙面是否要显示背景墙(当背景墙删除的时候) ->start
		
		// 普通背景墙长高过滤 ->start
		String isBeijingStr = Utils.getValue("app.smallProductType.stretch", "");
		List<String> isBeijingTypeList = Utils.getListFromStr(isBeijingStr);
		if(sysDictionarySmallType != null){
			if(isBeijingTypeList.indexOf(sysDictionarySmallType.getValuekey()) != -1){
				// 是背景墙(限制普通背景墙的长高)
				/*//System.out.println();*/
				prepProductSearchInfo.setBeijing(true);
				// 设置长高查询条件
				baseProductProp.getProductLength();
				String beijingHeightStr = baseProductProp.getProductHeight();
				String beijingLengthStr = baseProductProp.getProductLength();
				prepProductSearchInfo.setBeijingHeight(StringUtils.isNotBlank(beijingHeightStr)?Integer.valueOf(beijingHeightStr):null);
				Integer beijingLengthStart = 0;
				Integer beijingLengthEnd = 0;
				if(StringUtils.isNotBlank(baseProductProp.getFullPaveLength())){
					Integer fullPaveLength = Integer.valueOf(baseProductProp.getFullPaveLength());
					Integer beijingRange = Integer.valueOf(Utils.getValue("app.filter.stretch.length", "20"));
					beijingLengthStart = fullPaveLength - beijingRange;
					beijingLengthEnd = fullPaveLength + beijingRange;
				}else{
					beijingLengthStart = 0;
					beijingLengthEnd = StringUtils.isNotBlank(beijingLengthStr)?Integer.valueOf(beijingLengthStr):null;
				}
				prepProductSearchInfo.setBeijingLengthStart(beijingLengthStart);
				prepProductSearchInfo.setBeijingLengthEnd(beijingLengthEnd);
			}
		}
		// 普通背景墙长高过滤 ->end
		
		// 是否是天花,天花只显示定制产品 ->start
		if(sysDictionarySmallType != null){
			if("pdtianh".equals(sysDictionarySmallType.getValuekey())){
				prepProductSearchInfo.setTianh(true);
			}
		}
		// 是否是天花,天花只显示定制产品 ->start
		
		// 设置授权码查询条件 ->start
		Long startTime2 = new Date().getTime();
		if(userType == 3){
			List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfigV2(loginUser);
			prepProductSearchInfo.setBaseProductList(baseProductList);
		}
		Long endTime2 = new Date().getTime();
		logger.warn("------设置授权码查询条件耗时:" + (endTime2 - startTime2));
		// 设置授权码查询条件 ->end
		
		// 其他查询条件 ->start
		prepProductSearchInfo.setStart(start);
		prepProductSearchInfo.setLimit(limit);
		// 其他查询条件 ->end
		Long endTime = new Date().getTime();
		logger.warn("------设置查询条件耗时:" + (endTime - startTime));
		// 设置查询条件 ->end
		
		// 查询 ->start
		Integer count = 0;
		List<CategoryProductResult> returnList = new ArrayList<CategoryProductResult>();
		startTime = new Date().getTime();
		if(userType == 3){
			// 考虑序列号过滤
			count = prepProductSearchInfoService.getCount(prepProductSearchInfo,loginUser.getId(),categoryCode);
			if(count > 0){
				returnList = prepProductSearchInfoService.getProductIdListV2(prepProductSearchInfo,loginUser.getId(),categoryCode);
			}else{
				// 如果没搜出产品,则去掉授权码再查询
				prepProductSearchInfo.setBaseProductList(null);
				count = prepProductSearchInfoService.getCount(prepProductSearchInfo,loginUser.getId(),categoryCode);
				if(count > 0){
					returnList = prepProductSearchInfoService.getProductIdListV2(prepProductSearchInfo,loginUser.getId(),categoryCode);
				}
			}
		}else{
			// 不经过序列号过滤
			count = prepProductSearchInfoService.getCount(prepProductSearchInfo,loginUser.getId(),categoryCode);
			if(count > 0){
				returnList = prepProductSearchInfoService.getProductIdListV2(prepProductSearchInfo,loginUser.getId(),categoryCode);
			}
		}
		endTime = new Date().getTime();
		logger.warn("------查询耗时:" + (endTime - startTime));
		startTime = new Date().getTime();
		
		// 获取需要自动携带白模产品的配置->start
		Map<String, AutoCarryBaimoProducts> autoCarryMap = this.getAutoCarryMap();
		// 获取需要自动携带白模产品的配置->end
		
		for(CategoryProductResult categoryProductResult : returnList){
			// 处理材质返回格式
			dealWithTextureInfo(categoryProductResult);
			// 补充信息->start
			categoryProductResult.setModelProductId(categoryProductResult.getProductId());
			// 软硬装:0:软装;1:硬装
			String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1().trim();
			categoryProductResult.setRootType(StringUtils.isEmpty(smallTypeAtt1) ? "2" : smallTypeAtt1);
			// bgWall:是否是背景墙
			Integer bgWallValue = baseProductService.getBgWallValue(categoryProductResult.getProductTypeCode(),categoryProductResult.getProductSmallTypeCode());
			categoryProductResult.setBgWall(bgWallValue);
			// 产品附加属性->start
			Map<String, String> map = new HashMap<String, String>();
			map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
			categoryProductResult.setPropertyMap(map);
			// 产品附加属性->end
			// 自动携带白膜属性(未优化) ->start
			//baseProductService.setBasicModelMap(categoryProductResult, autoCarryMap, map, designPlan, designPlanProduct, request);
			// 自动携带白膜属性(未优化) ->end
			/*categoryProductResult.setIsMainProduct(new Integer(0));
			categoryProductResult.setRootType("1");
			categoryProductResult.setBgWall(new Integer(0));
			categoryProductResult.setMinHeight("");
			categoryProductResult.setOrderType(new Integer(0));
			categoryProductResult.setProductCount(new Integer(0));
			categoryProductResult.setPropertyMap(new HashMap<String, String>());
			categoryProductResult.setMaterialPicPaths(new String[]{});*/
			// 补充信息->end
		}
		endTime = new Date().getTime();
		logger.warn("------拉取详细信息耗时:" + (endTime - startTime));
		// 查询 ->end
		return new ResponseEnvelope<CategoryProductResult>(count, returnList, productCategoryRel.getMsgId());
	}
	
	/**
	 * 根据分类查询产品接口
	 *	主逻辑放在service
	 *@author huangsongbo
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	//@RequestMapping(value = "/searchProduct_")
	@RequestMapping(value = "/searchProduct")
	@ResponseBody
	public Object searchProductV6(@PathVariable String style,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel, HttpServletRequest request,
			@RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			// 相当于样板房初始化该产品对应的白膜的样板房产品信息
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) Integer productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) Integer smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "productModelNumber", required = false) String productModelOrBrandName) {
		// 参数验证 ->start
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) 
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", productCategoryRel.getMsgId());
		
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0)
			designPlan = designPlanService.get(designPlanId);
		if (designPlan == null)
			return new ResponseEnvelope<ProductCategoryRel>(false, "找不到该设计方案：" + designPlanId, productCategoryRel.getMsgId());
		
		// 用户信息
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId() == null || loginUser.getId() < 1)
			return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", productCategoryRel.getMsgId());
		// 参数验证 ->end

//		ResponseEnvelope<CategoryProductResult> result =
//				productCategoryRelService.searchProductV6(templateProductId, productCategoryRel, loginUser, productModelOrBrandName, planProductId, houseType, productTypeValue, spaceCommonId, designPlan, smallTypeValue, request,null);
//		return result;
		return null;
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

	private void setBgWallCondition(PrepProductSearchInfo prepProductSearchInfo, DesignPlanProduct designPlanProduct) {
		if(designPlanProduct == null)
			return;
		String BindParentProductIdsStr = designPlanProduct.getBindParentProductId();
		List<Integer> bindParentProductIdList = Utils.getIntegerListFromStringList(BindParentProductIdsStr);
		if(bindParentProductIdList == null || bindParentProductIdList.size() == 0)
			return;
		// 检测绑定点上的产品是否还在
		List<Integer> idList = new ArrayList<Integer>();
		for(Integer id : bindParentProductIdList){
			DesignPlanProduct designPlanProductBind = designPlanProductService.findIdByInitProductIdAndPlanId(id, designPlanProduct.getPlanId());
			if(designPlanProductBind == null)
				idList.add(id);
		}
		if(idList.size() == 0)
			return;
		List<Integer> smallTypeValueList = new ArrayList<Integer>();
		for(Integer productId : idList){
			BaseProduct baseProductBaimo = baseProductService.get(productId);
			if(baseProductBaimo == null)
				continue;
			Integer smallTypeValue = sysDictionaryService.getProductValueByBaimoValue
					(Integer.valueOf(baseProductBaimo.getProductTypeValue()), baseProductBaimo.getProductSmallTypeValue());
			smallTypeValueList.add(smallTypeValue);
		}
		List<Integer> excludeSmallTypeValueList = prepProductSearchInfo.getExcludeSmallTypeValue();
		if(excludeSmallTypeValueList != null && excludeSmallTypeValueList.size() > 0){
			excludeSmallTypeValueList.removeAll(smallTypeValueList);
			prepProductSearchInfo.setExcludeSmallTypeValue(excludeSmallTypeValueList);
		}
		List<Integer> baimoIdList = new ArrayList<>();
		if(prepProductSearchInfo.getBaimoId() != null)
			baimoIdList.add(prepProductSearchInfo.getBaimoId());
		baimoIdList.addAll(idList);
		prepProductSearchInfo.setBaimoIdList(baimoIdList);
	}

	private Map<String, Object> getSpecialProductTypeMap(Integer bigTypeValue, Integer smallTypeValue) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String specialProductTypeStr = Utils.getValue("special.productType", "");
		if(StringUtils.isNoneBlank(specialProductTypeStr)){
			JSONArray jsonArray=JSONArray.fromObject(specialProductTypeStr);
			@SuppressWarnings({ "deprecation", "unchecked" })
			List<SpecialProductTypeInfo> specialProductTypeInfoList = JSONArray.toList(jsonArray, SpecialProductTypeInfo.class);
			// 取出所有大类查询其对应的value
			List <String> bigTypeList = new ArrayList<String>();
			for(SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList){
				bigTypeList.add(specialProductTypeInfo.getProductTypeKey());
			}
			// 转换成bigType:value的map
			Map<Integer, String> bigTypeMap = sysDictionaryService.getValueValueKeyMapByTypeAndValueKey("productType", bigTypeList);
			if(bigTypeMap.containsKey(bigTypeValue)){
				// 找到对应大类配置
				String bigType = bigTypeMap.get(bigTypeValue);
				for(SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList){
					if(StringUtils.equals(bigType, specialProductTypeInfo.getProductTypeKey())){
						String smallTypeStr = specialProductTypeInfo.getProductSmallTypeKeys();
						List<String> smallTypeList = Utils.getListFromStr(smallTypeStr);
						List<Integer> smallTypeValueList = sysDictionaryService.getValueByTypeAndValueKeylist(bigType, smallTypeList);
						if(smallTypeValueList.indexOf(smallTypeValue) == -1){
							// 确定为不包含这些小类
							returnMap.put("notIn", smallTypeValueList);
						}else{
							// 确定为只要改小类
							returnMap.put("in", smallTypeValue);
						}
						break;
					}
				}
			}else{
				// 在配置范围外,不做处理
				
			}
		}
		return returnMap;
	}

	/**
	 * 处理材质信息,填装材质信息(默认材质)
	 * @param categoryProductResult
	 */
	private void dealWithTextureInfo(CategoryProductResult categoryProductResult) {
		List<SplitTextureDTO> SplitTextureDTOList = new ArrayList<>();
		if(StringUtils.isNotBlank(categoryProductResult.getSplitTexturesInfoStr())){
			// 多材质产品
		}else{
			if(StringUtils.isNotBlank(categoryProductResult.getMaterialPicId())){
				// 单材质产品
				SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
				SplitTextureDTO.ResTextureDTO resTextureDTO 
					= splitTextureDTO.new ResTextureDTO(
							Integer.valueOf(categoryProductResult.getMaterialPicId()),
							categoryProductResult.getResTexturePath(),
							categoryProductResult.getTextureAttrValue(),
							StringUtils.isNotBlank(categoryProductResult.getTextureWidth()) ? Integer.valueOf(categoryProductResult.getTextureWidth()) : null,
							StringUtils.isNotBlank(categoryProductResult.getTextureHeight()) ? Integer.valueOf( categoryProductResult.getTextureHeight()) : null,
							categoryProductResult.getLaymodes(),null,null,null);
				resTextureDTO.setKey("1");
				resTextureDTO.setProductId(categoryProductResult.getProductId());
				List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
				resTextureDTOList.add(resTextureDTO);
				splitTextureDTO.setList(resTextureDTOList);
				SplitTextureDTOList.add(splitTextureDTO);
			}
		}
		categoryProductResult.setSplitTexturesChoose(SplitTextureDTOList);
	}

	/**
	 * 得到空间类型搜索条件
	 * @author huangsongbo
	 * @param houseType
	 * @param baimoBaseProductInit
	 * @return
	 */
	private List<String> getHouseTypeSearchCondition(String houseType, BaseProduct baimoBaseProductInit) {
		if (StringUtils.isNotBlank(houseType)) {
			//找到houseType对应的productHouseType
			SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
			sysDictionarySearch.setAtt1(houseType);
			sysDictionarySearch.setType("productHouseType");
			List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
			List<String> productHouseTypeList = new ArrayList<String>();
			List<String> productHouseTypeList3 = new ArrayList<String>();
			if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
				for (SysDictionary sysDictionary : sysDictionaryList) {
					productHouseTypeList.add("" + sysDictionary.getValue());
					productHouseTypeList3.add("" + sysDictionary.getValue());
				}
			}
			if (baimoBaseProductInit != null) {
				//和对应初始化白膜的houseTypeValues取交集
				//eg:进入客餐厅,如果点击的灯的初始化白膜是客厅,则只能搜出客厅的等
				String productHouseTypesStr = baimoBaseProductInit.getHouseTypeValues();
				if (StringUtils.isNotBlank(productHouseTypesStr)) {
					List<String> productHouseTypeList2 = Utils.getListFromStr(productHouseTypesStr);
					productHouseTypeList.retainAll(productHouseTypeList2);
				}
			}
			if (productHouseTypeList.size() > 0) {
				 return productHouseTypeList;
			} else {
				return productHouseTypeList3;
			}
		}
		return null;
	}

	private DesignPlanProduct sethouseTypeSearchCondition(ProductCategoryRel productCategoryRel, String houseType,
			Integer planProductId) {
		/* String msg; */
		DesignPlanProduct designPlanProduct = null;
		if (StringUtils.isNotBlank(houseType)) {
			/* 对应出productHouseType的数据字典的value */
			SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
			sysDictionarySearch.setAtt1(houseType);
			sysDictionarySearch.setType("productHouseType");
			List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
			List<String> productHouseTypeList = new ArrayList<String>();// 过滤条件
			List<String> productHouseTypeList3 = new ArrayList<String>();
			if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
				for (SysDictionary sysDictionary : sysDictionaryList) {
					productHouseTypeList.add("" + sysDictionary.getValue());
					productHouseTypeList3.add("" + sysDictionary.getValue());
				}
			}
			/* 得到产品空间类型valueList */
			// String productHouseTypesStr=productSelected.getHouseType();
			/* 取白膜的productHouseType */
			if (planProductId != null && planProductId != 0) {
				designPlanProduct = designPlanProductService.get(planProductId);
				if (designPlanProduct == null) {
					/*
					 * msg = "找不到该设计方案产品：" + planProductId; return new
					 * ResponseEnvelope<ProductCategoryRel>(false, msg,
					 * productCategoryRel.getMsgId());
					 */
					return designPlanProduct;
				}
				Integer baimoProductId = designPlanProduct.getInitProductId();
				BaseProduct baimoProduct = baseProductService.get(baimoProductId);
				if (baimoProduct != null) {
					String productHouseTypesStr = baimoProduct.getHouseTypeValues();
					if (StringUtils.isNotBlank(productHouseTypesStr)) {
						List<String> productHouseTypeList2 = Utils.getListFromStr(productHouseTypesStr);
						productHouseTypeList.retainAll(productHouseTypeList2);
					}
				}
			}
			if (productHouseTypeList.size() > 0) {
				productCategoryRel.setHouseTypeList(productHouseTypeList);
			} else {
				productCategoryRel.setHouseTypeList(productHouseTypeList3);
			}
		}
		return designPlanProduct;
	}

	// 按搜索产品属性匹配度排序
	public List<CategoryProductResult> getProductList(List<CategoryProductResult> list,
			ProductCategoryRel productCategoryRel) {

		List<ProductProps> propsOrderList = productCategoryRel.getPropsOrderList();

		// 搜索产品的排序属性排序->start
		Long startDate1_1 = new Date().getTime();
		if (Lists.isNotEmpty(propsOrderList)) {
			ComparatorP cpmparator = new ComparatorP();
			Collections.sort(propsOrderList, cpmparator);
			for (CategoryProductResult productResult : list) {
				BaseProduct baseProduct = null;
				if (Utils.enableRedisCache()) {
					baseProduct = BaseProductCacher.get(productResult.getProductId());
				} else {
					baseProduct = baseProductService.get(productResult.getProductId());
				}
				if (baseProduct == null) {
					logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map = productAttributeService.getPropertyOrderMap(baseProduct.getId());
				if (map.size() == 0) {
					productResult.setProductOrdering(9999999);
					continue;
				}
				StringBuffer orderNumber = new StringBuffer();
				for (ProductProps props : propsOrderList) {
					// 选中产品显示第一个
					if (productResult.getProductId().intValue() == props.getProductId().intValue()) {
						productResult.setProductOrdering(1);
						continue;
					}
					int tmp = 0;
					for (Map.Entry<String, String> entry : map.entrySet()) {
						// 属性类型匹配
						if (entry.getKey().equals(props.getParentCode())) {
							if (StringUtils.isNotBlank(map.get(props.getParentCode()))) {
								ProductProps productProps = productPropsService
										.get(Utils.getIntValue(map.get(props.getParentCode())));
								if (productProps != null) {
									if (productProps.getPropValue().equals(props.getPropValue())) {
										// 1、value相等且属性数量一样2、value相等且属性数量不一样
										if (propsOrderList.size() == map.size()) {
											orderNumber.append(2);
										} else {
											orderNumber.append(3);
										}
										tmp++;
										break;
									} else {
										// 3、value不等、同类型且属性数量一样
										// 4、value不等、同类型且属性数量不一样
										if (propsOrderList.size() == map.size()) {
											orderNumber.append(4);
										} else {
											orderNumber.append(5);
										}
										tmp++;
										break;
									}
								}
							}
						}
					}
					// 5、无匹配的属性
					if (tmp == 0) {
						orderNumber.append(7);
					}
				}
				productResult.setProductOrdering(Utils.getIntValue(orderNumber.toString()));
			}
		}
		Long startDate1_2 = new Date().getTime();
		logger.info("------搜索产品的排序属性排序:" + (startDate1_2 - startDate1_1));
		// 搜索产品的排序属性排序->end

		// 如果该产品关联了色系则关联色系排序->start
		Long startDate2_1 = new Date().getTime();
		baseProductService.productColorsSortV2(list);
		Long startDate2_2 = new Date().getTime();
		logger.info("------色系排序:" + (startDate2_2 - startDate2_1));
		// 如果该产品关联了色系则关联色系排序->end

		// 列表排序,推荐,小类,同属性匹配度,使用量,色系排序->start
		Long startDate3_1 = new Date().getTime();
		ComparatorO cpmparator = new ComparatorO();
		Collections.sort(list, cpmparator);
		Long startDate3_2 = new Date().getTime();
		logger.info("------列表排序,推荐,小类,同属性匹配度,使用量,色系排序:" + (startDate3_2 - startDate3_1));
		// 列表排序,推荐,小类,同属性匹配度,使用量,色系排序-->end

		return list;
	}

	/**
	 * 根据产品顺序排序（升序）
	 * 
	 * @author Administrator
	 *
	 */
	public class ComparatorT implements Comparator<Object> {
		public int compare(Object obj1, Object obj2) {
			CategoryProductResult unity1 = (CategoryProductResult) obj1;
			CategoryProductResult unity2 = (CategoryProductResult) obj2;
			int flag = (unity1.getProductOrdering() == null ? new Integer(0) : new Integer(unity1.getProductOrdering()))
					.compareTo(unity2.getProductOrdering() == null ? new Integer(0)
							: new Integer(unity2.getProductOrdering()));
			if (flag == 0) {
				return (unity1.getProductOrdering() == null ? new Integer(0) : new Integer(unity1.getProductOrdering()))
						.compareTo(unity2.getProductOrdering() == null ? new Integer(0)
								: new Integer(unity2.getProductOrdering()));
			} else {
				return flag;
			}
		}
	}

	public class ComparatorO implements Comparator<Object> {
		public int compare(Object obj1, Object obj2) {
			CategoryProductResult unity1 = (CategoryProductResult) obj1;
			CategoryProductResult unity2 = (CategoryProductResult) obj2;
			Integer productOrder1 = unity1.getProductOrdering() == null ? new Integer(0)
					: new Integer(unity1.getProductOrdering());
			Integer productOrder2 = unity2.getProductOrdering() == null ? new Integer(0)
					: new Integer(unity2.getProductOrdering());
			Integer colorsOrder1 = unity1.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE)
					: new Integer(unity1.getColorsOrdering());
			Integer colorsOrder2 = unity2.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE)
					: new Integer(unity2.getColorsOrdering());
			if (unity1.getOrderType().compareTo(unity2.getOrderType()) != 0) { // 定制、推荐、小类
				return unity1.getOrderType().compareTo(unity2.getOrderType());
			} else if (productOrder1.compareTo(productOrder2) != 0) {// 属性匹配度
				return productOrder1.compareTo(productOrder2);
			} else if (colorsOrder1.compareTo(colorsOrder2) != 0) {// 色系排序
				return colorsOrder1.compareTo(colorsOrder2);
			} else { // 使用量
				return (unity2.getProductCount() == null ? new Integer(0) : unity2.getProductCount())
						.compareTo(unity1.getProductCount() == null ? new Integer(0) : unity1.getProductCount());
			}
		}
	}

	/**
	 * 属性长序号排序（升序）
	 * 
	 * @author Administrator
	 *
	 */
	public class ComparatorP implements Comparator<Object> {
		public int compare(Object obj1, Object obj2) {
			ProductProps unity1 = (ProductProps) obj1;
			ProductProps unity2 = (ProductProps) obj2;
			int flag = (unity1.getSequenceNumber() == null ? new Integer(0) : new Integer(unity1.getSequenceNumber()))
					.compareTo(unity2.getSequenceNumber() == null ? new Integer(0)
							: new Integer(unity2.getSequenceNumber()));
			if (flag == 0) {
				return (unity1.getSequenceNumber() == null ? new Integer(0) : new Integer(unity1.getSequenceNumber()))
						.compareTo(unity2.getSequenceNumber() == null ? new Integer(0)
								: new Integer(unity2.getSequenceNumber()));
			} else {
				return flag;
			}
		}
	}

	public List<CategoryProductResult> getList(List<CategoryProductResult> list,
			ProductCategoryRel productCategoryRel) {
		return list;
	}

}
