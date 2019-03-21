package com.nork.product.service2.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.SystemCommonConstant;
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
import com.nork.product.dao2.BaseBrandMapper;
import com.nork.product.dao2.GroupProductCollectMapper;
import com.nork.product.dao2.GroupProductMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.GroupProductResult;
import com.nork.product.model.MatchGroupProductResult;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service2.GroupProductService2;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;

/**
 * @Title: GroupProductServiceImpl2.java
 * @Package com.nork.product.service2.impl
 * @Description:产品模块-产品组合主表ServiceImpl
 * @createAuthor yangzhun
 * @CreateDate 2017-06-21 20:52:57
 */
@Service("groupProductService2")
public class GroupProductServiceImpl2 implements GroupProductService2 {

	@Autowired
	private GroupProductDetailsService groupProductDetailsService;

	@Autowired
	private GroupProductMapper groupProductMapper2;
	@Autowired
	private BaseBrandMapper baseBrandMapper2;
	@Autowired
	private GroupProductCollectMapper groupProductCollectMapper2;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private BaseProductStyleService baseProductStyleService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private DesignPlanProductService designPlanProductService;

	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	/**
	 * 通过产品查询产品组信息
	 * 
	 * @param groupSearch
	 * @return
	 */
	@Override
	public int getGroupCountByProduct(GroupProductSearch groupSearch) {
		return groupProductMapper2.getGroupCountByProduct(groupSearch);
	}

	/**
	 * 通过产品查询产品组信息
	 * 
	 * @param productSearch
	 * @return
	 */
	@Override
	public List<GroupProduct> getGroupListByProduct(
			GroupProductSearch productSearch) {
		return groupProductMapper2.getGroupListByProduct(productSearch);
	}

	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 * 
	 * @param brandName
	 * @return GroupProduct
	 */
	@Override
	public List<GroupProductDetails> queryBrandGroupList(
			BaseBrandSearch ResBrand) {
		return groupProductMapper2.queryBrandGroupList(ResBrand);
	}

	@Override
	public int queryBrandGroupCount(BaseBrandSearch ResBrand) {
		return groupProductMapper2.queryBrandGroupCount(ResBrand);
	}

	/**
	 * 得到组合的价格->组合表有价格信息就取价格信息,没有的话就把该组合的产品价格相加
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	public Double getSalePrice(Integer groupId) {
		Double salePrice = 0.0;
		GroupProduct groupProduct = groupProductMapper2
				.selectByPrimaryKey(groupId);
		if (groupProduct == null || groupProduct.getId() == null
				|| groupProduct.getId() <= 0) {
			return 0.0;
		}
		if (groupProduct.getGroupPrice() != null
				&& groupProduct.getGroupPrice() > 0) {
			salePrice = groupProduct.getGroupPrice();
		} else {
			salePrice = groupProductMapper2.getPriceFromDetails(groupId);
		}
		return salePrice;
	}

	// @Override
	// public int getGroupCountByStructureId(GroupProductSearch groupSearch) {
	// return groupProductMapper.getGroupCountByStructureId(groupSearch);
	// }
	//
	// @Override
	// public List<GroupProduct> getGroupListByStructureId(GroupProductSearch
	// groupSearch) {
	// return groupProductMapper.getGroupListByStructureId(groupSearch);
	// }

	@Override
	public int getGroupCountByStructureId(Integer structureId) {
		return groupProductMapper2.getGroupCountByStructureId(structureId);
	}

	@Override
	public List<GroupProduct> getGroupListByStructureId(Integer structureId) {
		return groupProductMapper2.getGroupListByStructureId(structureId);
	}

	@Override
	public GroupProduct getStructureByGroupId(Integer groupId) {
		return groupProductMapper2.getStructureByGroupId(groupId);
	}

	@Override
	public List<GroupProductResult> selectCommonList(GroupProduct groupProduct)
			throws Exception {
		List<GroupProductResult> dataList = new ArrayList<GroupProductResult>();
		dataList = groupProductMapper2.selectCommonList(groupProduct);
		return dataList;
	}

	/**
	 * 判断该产品是不是否是主产品
	 * 
	 * @param cacheEnable
	 * @param productId
	 *            产品id
	 * @param cacheEnable
	 *            是否开启缓存
	 * @return
	 */
	public Integer getIsMainProduct(Integer productId, String cacheEnable) {
		Integer isMain = 0;
		GroupProductDetails groupProductDetails = new GroupProductDetails();
		groupProductDetails.setProductId(productId);
		groupProductDetails.setIsMain(1);
		groupProductDetails.setIsDeleted(0);
		groupProductDetails.setStart(0);
		groupProductDetails.setLimit(1);
		List<GroupProductDetails> groupList = new ArrayList<>();
		if (StringUtils.equals("1", cacheEnable)) {
			groupList = GroupProductDetailsCache.getList(groupProductDetails);
		} else {
			groupList = groupProductDetailsService.getList(groupProductDetails);
		}
		if (groupList != null && groupList.size() > 0) {
			isMain = 1;
		}
		return isMain;
	}

	/**
	 * 验证该产品是否是主产品 0:非主产品 1:是某个组合的主产品
	 * 
	 * @author huangsongbo
	 * @param productId
	 *            产品id
	 */
	public Integer getIsMainProductV2(Integer productId) {
		Integer groupId = groupProductMapper2.getIsMainProductV2(productId);
		return (groupId != null && groupId > 0) ? 1 : 0;
	}

	/**
	 * 根据结构id和组合状态查找结构组合数量
	 * 
	 * @author huangsongbo
	 * @param structureId
	 * @return
	 */
	public int getGroupCountByStructureIdAndStatus(Integer structureId,
			Integer usertype, String[] brandIdList, String versionType) {
		return groupProductMapper2.getGroupCountByStructureIdAndStatus(
				structureId, usertype, brandIdList, versionType);
	}

	/**
	 * 通过id查找组合(关联查找更多信息,比如:品牌名,风格名)
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	public GroupProduct getMoreInfoById(Integer groupId) {
		return groupProductMapper2.getMoreInfoById(groupId);
	}

	@Override
	public void updateStatus(Integer oldStatus, Integer newStatus) {
		groupProductMapper2.updateStatus(oldStatus, newStatus);
	}

	/**
	 * 产品组合详情接口
	 * 
	 */
	@Override
	public Object getGroupProductDetails(String msgId, Integer groupId,
			LoginUser loginUser, String mediaType) {

		String cacheEnable = Utils.getValue(
				SystemCommonConstant.REDIS_CACHE_ENABLE,
				SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE);
		GroupProduct groupProduct = groupProductMapper2
				.getMoreInfoById(groupId);
		if (groupProduct == null) {
			return new ResponseEnvelope<GroupProduct>(false, "找不到该组合产品:"
					+ groupId, msgId);
		}
		// 媒介类型.如果没有值，则表示为web前端（2）

		SearchProductGroupResult groupProductResult = new SearchProductGroupResult();
		SearchProductGroupDetail groupDetail = null;
		List<SearchProductGroupDetail> details = new ArrayList<>();
		// 填充位置信息没因为老数据是 文本，新数据是id 这里 支持新老数据
		if (groupProduct.getLocation() != null
				&& !"".equals(groupProduct.getLocation())) {
			ResFile resFile = null;
			String txt = null;
			if (StringUtils.isNumeric(groupProduct.getLocation())) {// 判断里面是不是数字
				resFile = resFileService.get(Integer.parseInt(groupProduct
						.getLocation()));
				if (resFile != null) {
					String url = Tools.getRootPath(resFile.getFilePath(), "")
							+ resFile.getFilePath();
					txt = FileUploadUtils.getFileContext(url);
				}
				groupProduct.setLocation(txt);
			}

		}
		// 风格信息 ->start
		if (StringUtils.isNotBlank(groupProduct.getProductStyleIdInfo())) {
			JSONObject styleJson = JSONObject.fromObject(groupProduct
					.getProductStyleIdInfo());
			List<String> styleInfoList = baseProductStyleService
					.getProductStyleInfo(styleJson);
			StringBuffer stringBuffer = new StringBuffer("");
			for (String str : styleInfoList) {
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
		// 组合品牌,风格,描述信息,封面url->start
		groupProductResult.setBrandName(groupProduct.getBrandName());
		groupProductResult.setStyleName(groupProduct.getStyleName());
		groupProductResult.setRemark(groupProduct.getRemark());
		groupProductResult.setPicPath(groupProduct.getPicPath());
		if (groupProduct.getProductType() == null
				|| "".equals(groupProduct.getProductType())) {
			groupProductResult.setProductType("无");
		} else {
			groupProductResult.setProductType(groupProduct.getProductType());
		}
		// 组合品牌,风格,描述信息,封面url->end

		GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
		groupProductCollectSearch.setUserId(loginUser.getId());
		groupProductCollectSearch.setGroupId(groupProduct.getId());
		Integer count = groupProductCollectMapper2
				.selectCount(groupProductCollectSearch);
		if (count > 0) {
			groupProductResult.setCollectState(1);
		} else {
			groupProductResult.setCollectState(0);
		}
		Double totalPrice = 0.00;
		int brandState = 0;
		try {

			// 查询 组合产品明细列表
			GroupProductDetails groupProductDetails = new GroupProductDetails();
			groupProductDetails.setGroupId(groupProduct.getId());
			groupProductDetails.setOrder(" sorting ");
			List<GroupProductDetails> gpdList = GroupProductDetailsCache
					.getList(groupProductDetails);

			// 获取该用户绑定的序列号品牌，如果用户没有 产品品牌，则不显示价格
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			if (StringUtils.equals(
					SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE, cacheEnable)) {
				authorizedList = AuthorizedConfigCacher
						.getList(authorizedConfig);
			} else {
				authorizedList = authorizedConfigService
						.getList(authorizedConfig);
			}
			String brandIds = "";
			for (AuthorizedConfig ac : authorizedList) {
				if (StringUtils.isNotBlank(ac.getBrandIds())) {
					brandIds += ac.getBrandIds() + ",";
				}
			}
			
			
			for (GroupProductDetails detailEntity : gpdList) {
				BaseProduct baseProduct = null;
				if (StringUtils.equals(
						SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE,
						cacheEnable)) {
					baseProduct = BaseProductCacher
							.getBaseProductServiceById(detailEntity
									.getProductId());
				} else {
					baseProduct = baseProductService.get(detailEntity.getProductId());
				}
				groupDetail = new SearchProductGroupDetail();

				// 在组合产品查询列表 中 填充产品属性
				if (detailEntity.getProductId() > 0) {
					Map<String, String> map = new HashMap<String, String>();
					map = productAttributeService.getPropertyMap(detailEntity
							.getProductId());
					groupDetail.setPropertyMap(map);
				}

				if (baseProduct != null) {
					groupDetail.setProductId(detailEntity.getProductId());
					int isMain = detailEntity.getIsMain() == null ? 0
							: detailEntity.getIsMain();
					groupDetail.setIsMainProduct(isMain);
					Double salePrice = baseProduct.getSalePrice() == null ? 0
							: baseProduct.getSalePrice();
					// totalPrice 组合总的价格
					totalPrice = totalPrice + salePrice;
					if (baseProduct.getPicId() != null
							&& baseProduct.getPicId() > 0) {
						ResPic resPic = null;
						if (StringUtils.equals(
								SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE,
								cacheEnable)) {
							resPic = ResourceCacher.getPic(baseProduct
									.getPicId());
						} else {
							resPic = resPicService.get(baseProduct.getPicId());
						}

						groupDetail.setPicPath(resPic == null ? "" : resPic
								.getPicPath());
					}

					if (brandState == 0) {
						// 判断该品牌是否被绑定状态
						String productBrandId = "," + baseProduct.getBrandId()
								+ ",";
						if (StringUtils.isEmpty(brandIds)) {
							brandState = brandState + 1;
						}
						if (("," + brandIds).indexOf(productBrandId) == -1) {
							brandState = brandState + 1;
						}
					}
					// 获取不同媒介u3d模型
					String modelId = baseProductService.getU3dModelId(
							mediaType, baseProduct);
					if (StringUtils.isNotBlank(modelId)) {
						ResModel resModel = resModelService.get(Integer.valueOf(modelId));
						if (resModel != null) {
							File modelFile = new File(resModel.getModelPath());
							if (modelFile.exists()) {
								groupDetail.setU3dModelPath(resModel
										.getModelPath());
							}
						}
					}

				}
				details.add(groupDetail);
			}
			groupProductResult.setGroupProductDetails(details);
			Double groupPrice = groupProduct.getGroupPrice() == null ? 0
					: groupProduct.getGroupPrice();
			if (groupPrice == 0) {
				groupProductResult.setGroupPrice(totalPrice);
			} else {
				groupProductResult.setGroupPrice(groupPrice);
			}
			if (brandState > 0) {
				groupProductResult.setGroupPrice(0.0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<GroupProduct>(false,
					SystemCommonConstant.DATA_EXCEPTION, msgId);
		}
		return new ResponseEnvelope<GroupProduct>(groupProductResult, msgId, true);

	}

	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 * 
	 * @param baseBrandSearch
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	@Override
	public Object queryGroupByBrand(BaseBrandSearch baseBrandSearch,
			String msgId, LoginUser loginUser) {

		String cacheEnable = Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE, SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE);
		long start = System.currentTimeMillis();
		baseBrandSearch.setUserType(loginUser.getUserType()); // 1是内部
		String versionType = Utils.getPropertyName("app", "sys.version.type",
				"1").trim();// 1为外网 2 为内网 默认为外网
		baseBrandSearch.setVersionType(versionType);
		
		Map<Object, Object> paramsMap = new HashMap<Object, Object>();
		paramsMap.put("brandName", baseBrandSearch.getSch_BrandName_());
		paramsMap.put("userId", loginUser.getId());
		paramsMap.put("limit", baseBrandSearch.getLimit());
		paramsMap.put("start", baseBrandSearch.getStart());
		ResponseEnvelope ResponseEnvelope = null;
		if (StringUtils.equals(SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE, cacheEnable)) {
			ResponseEnvelope = CommonCacher.getAll(ModuleType.GroupProduct,
					"queryGroupByBrand", paramsMap);
		}

		if (ResponseEnvelope != null) {
			return ResponseEnvelope;
		}

		// baseBrandSearch.setUserType(1);
		List<SearchProductGroupResult> resultList = new ArrayList<SearchProductGroupResult>();
		int total = groupProductMapper2.queryBrandGroupCount(baseBrandSearch);
		if (total > 0) {
			List<GroupProductDetails> list = groupProductMapper2
					.queryBrandGroupList(baseBrandSearch);

			// 获取该用户绑定的序列号品牌
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			authorizedList = authorizedConfigService.getList(authorizedConfig);
			String brandIds = "";
			for (AuthorizedConfig ac : authorizedList) {
				if (StringUtils.isNotBlank(ac.getBrandIds())) {
					brandIds += ac.getBrandIds() + ",";
				}
			}

			for (GroupProductDetails groupProductDetails : list) {
				int brandState = 0;
				SearchProductGroupResult searchProductGroupResult = new SearchProductGroupResult();
				searchProductGroupResult.setGroupId(groupProductDetails
						.getGroupId());
				searchProductGroupResult.setGroupCode(groupProductDetails
						.getGroupCode());
				searchProductGroupResult.setGroupName(groupProductDetails
						.getGroupName());
				searchProductGroupResult.setBrandName(groupProductDetails
						.getBrandName());
				Integer picId = groupProductDetails.getGroupPicId();
				if (picId != null && picId > 0) {
					ResPic resPic = resPicService.get(picId);
					if (resPic != null) {
						searchProductGroupResult
								.setPicPath(resPic.getPicPath());
					}
				}
				GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
				groupProductCollectSearch.setUserId(loginUser.getId());
				groupProductCollectSearch.setGroupId(groupProductDetails
						.getGroupId());
				List<GroupProductCollect> GroupProductCollectList = groupProductCollectMapper2
						.selectPaginatedList(groupProductCollectSearch);
				if (GroupProductCollectList != null
						&& GroupProductCollectList.size() > 0) {
					searchProductGroupResult.setCollectState(1);
				}
				if (GroupProductCollectList == null
						|| GroupProductCollectList.size() <= 0) {
					searchProductGroupResult.setCollectState(0);
				}
				// 补充信息
				searchProductGroupResult
						.setProductName(searchProductGroupResult.getGroupName());
				Double salePrice = this.getSalePrice(searchProductGroupResult
						.getGroupId());
				searchProductGroupResult.setSalePrice(salePrice);
				// 补充品名品名
				GroupProductDetailsSearch groupProductDetailsSearch = new GroupProductDetailsSearch();
				groupProductDetailsSearch.setGroupId(searchProductGroupResult
						.getGroupId());
				List<GroupProductDetails> groupProductDetailsMain = groupProductDetailsService
						.getPaginatedList(groupProductDetailsSearch);
				if (groupProductDetailsMain != null
						&& groupProductDetailsMain.size() > 0) {
					for (GroupProductDetails groupProductDetails_ : groupProductDetailsMain) {
						Integer isMain = groupProductDetails_.getIsMain();
						if (isMain != null && isMain == 1) {
							Integer productId = groupProductDetails_
									.getProductId();
							BaseProduct baseProduct = baseProductService.get(productId);
							// 品牌是否被绑定状态
							String productBrandId = ","
									+ baseProduct.getBrandId() + ",";
							if (StringUtils.isEmpty(brandIds)) {
								brandState = brandState + 1;
							}
							if (("," + brandIds).indexOf(productBrandId) == -1) {
								brandState = brandState + 1;
							}
							Integer brandId = baseProduct.getBrandId();
							if (brandId != null) {
								BaseBrand baseBrand = baseBrandMapper2.selectByPrimaryKey(brandId);
								if (baseBrand != null && baseBrand.getId() > 0)
									searchProductGroupResult.setBrandName(baseBrand.getBrandName());
							}
						}
						if (brandState == 0) {
							if (isMain == null || isMain != 1) {
								Integer productId = groupProductDetails_
										.getProductId();
								BaseProduct baseProduct = baseProductService.get(productId);
								// 品牌是否被绑定状态
								String productBrandId = ","
										+ baseProduct.getBrandId() + ",";
								if (StringUtils.isEmpty(brandIds)) {
									brandState = brandState + 1;
								}
								if (("," + brandIds).indexOf(productBrandId) == -1) {
									brandState = brandState + 1;
								}
							}
						}
					}
				}
				// 有一条品牌 没被绑定，整个组合价格 设为0
				if (brandState > 0) {
					searchProductGroupResult.setSalePrice(-1.0);
				}

				// 补充信息->end
				resultList.add(searchProductGroupResult);
			}
		}

		ResponseEnvelope responseEnvelope_ = new ResponseEnvelope<>(total,
				resultList, baseBrandSearch.getMsgId());
		if (StringUtils.equals("1", cacheEnable)) {
			CommonCacher.addAll(ModuleType.GroupProduct, "queryGroupByBrand",
					paramsMap, responseEnvelope_);
		}
		return responseEnvelope_;

	}

	/**
	 * 一键替换组合
	 * 
	 * @param templetId
	 * @param designTempletId
	 * @param msgId
	 * @return
	 */
	@Override
	public Object getGroupProductData(Integer templetId,
			Integer designTempletId, String msgId) {
		List<MatchGroupProductResult> list = new ArrayList();
		DesignPlan designPlan = designPlanService.get(templetId);
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designPlan == null || designTemplet == null) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.TEMPLATE_CANNOT_FIND, msgId);
		}
		// 查询样板房主组合
		DesignTempletProduct designTempletProduct = new DesignTempletProduct();
		designTempletProduct.setDesignTempletId(designTempletId);
		designTempletProduct.setGroupType(0);
		designTempletProduct.setIsDeleted(0);
		designTempletProduct.setIsMainProduct(1);
		List<DesignTempletProduct> templetList = designTempletProductService
				.getByTempletIdMainProduct(designTempletProduct);
		if (Lists.isEmpty(templetList)) {
			return new ResponseEnvelope<>(0, list, msgId);
		}
		// 模板组合主产品
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setPlanId(templetId);
		designPlanProduct.setGroupType(0);
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setIsMainProduct(1);
		List<DesignPlanProductResult> planList = designPlanProductService
				.getByPlanIdGroupMainProduct(designPlanProduct);
		if (Lists.isEmpty(templetList)) {
			return new ResponseEnvelope<MatchGroupProductResult>(0, list, msgId);
		}
		MatchGroupProductResult matchGroupProductResult = null;
		for (DesignTempletProduct templetProduct : templetList) {
			matchGroupProductResult = new MatchGroupProductResult();
			int temp = 0;
			for (DesignPlanProductResult planProduct : planList) {
				String productTypeValue = planProduct.getProductTypeValue();
				Integer productSmallTypeValue = planProduct
						.getProductSmallTypeValue();
				// 匹配同分类组合产品
				if (productTypeValue != null
						&& Utils.getIntValue(productTypeValue) == templetProduct
								.getProductTypeValue().intValue()) {
					// 柜子按小类过滤组合，临时解决方案，后续加组合类型过滤
					if ("10".equals(productTypeValue)) {
						if (productSmallTypeValue == 1 && templetProduct.getProductSmallTypeValue() == 14) {

						} else if (productSmallTypeValue == 2 && templetProduct.getProductSmallTypeValue() == 15) {

						} else {
							continue;
						}
					}
					temp++;
					matchGroupProductResult
							.setTempletMianProductCode(planProduct
									.getProductCode());
					// 目前数据库 location字段， 分为 txt 文件路径、json 串。
					String filePath = planProduct.getFilePath();
					if (filePath != null && !filePath.trim().isEmpty()) {
						String url = Tools.getRootPath(filePath, "")
								+ filePath;
						String text = FileUploadUtils.getFileContext(url);
						matchGroupProductResult.setTempletGroupConfig(text);
					} else {
						matchGroupProductResult
								.setTempletGroupConfig(planProduct
										.getLocation());
					}
					break;
				} else {
					continue;
				}
			}
			if (temp > 0) {
				matchGroupProductResult.setBmMianProductCode(templetProduct
						.getProductCode());
				List<GroupProductDetails> groupProductCodeList = groupProductDetailsService
						.getByGroupIdProductCodeList(templetProduct
								.getProductGroupId());
				List<Map<String, String>> bmGroupProductList = new ArrayList<>();
				Map<String, String> map = null;
				if (Lists.isNotEmpty(groupProductCodeList)) {
					for (GroupProductDetails productDetails : groupProductCodeList) {
						map = new HashMap<>();
						map.put("productCode", productDetails.getProductCode());
						map.put("posIndexPath",
								productDetails.getPosIndexPath());
						bmGroupProductList.add(map);
					}
				}
				matchGroupProductResult
						.setBmGroupProductList(bmGroupProductList);
				list.add(matchGroupProductResult);
			}
		}
		int total = Utils.getListTotal(list);

		return new ResponseEnvelope<MatchGroupProductResult>(total, list, msgId);
	}
}
