package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductRenderScene;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.controller.MobileSearchProductGroupController;
import com.nork.mobile.model.search.MobileSearchProductModel;
import com.nork.mobile.service.MobileSearchProductGroupService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.GroupProductServiceV2;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductAttributeService;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * 
 * @author yangzhun
 * @createTime 2017年10月27日16:16:04
 */
@Service("mobileSearchProductGroupService")
public class MobileSearchProductGroupServiceImpl implements MobileSearchProductGroupService {

	private static Logger logger = Logger.getLogger(MobileSearchProductGroupController.class);
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private GroupProductService groupProductService; // 产品组合 service
	@Autowired
	private GroupProductServiceV2 groupProductServiceV2; // 产品组合 service v2
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private ProCategoryService proCategoryService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;

	/**
	 * 移动端搜索组合产品的方法
	 */
	@Override
	public Object searchProductGroup(MobileSearchProductModel model) {

		// FIXME: 参数不一定能获取到，要去看看
		ProductCategoryRel productCategoryRel = new ProductCategoryRel();
		Integer limit = model.getLimit();
		Integer start = model.getStart();
		String categoryCode_ = model.getCategoryCode();
		productCategoryRel.setLimit(limit);
		productCategoryRel.setStart(start);
		productCategoryRel.setCategoryCode(categoryCode_);
//		Integer userStatusType = model.getUserStatusType();
		Integer userId = model.getUserId();
		String houseType = model.getHouseType();
		Integer planProductId = model.getPlanProductId();
		Integer structureId = model.getStructureId();
		Integer spaceCommonId = model.getSpaceCommonId();
		String productTypeValue = model.getProductTypeValue().toString();
		String smallTypeValue = model.getSmallTypeValue().toString();

		SysUser sysUser = sysUserService.get(userId);
		Integer userStatusType = sysUser.getUserType();
		
		// 初始化
		GroupProductSearch groupSearch = this.initGroupProductSearch(productCategoryRel, userStatusType);
		if (groupSearch == null) {
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", model.getMsgId());
		}
		// 设置不查询一建装修组合
		groupSearch.setGroupType(0);

		// String mediaType = SystemCommonUtil.getMediaType(request);
		String mediaType = "MOBILE";
		// 内部用户可以查询测试中的数据
//		SysUser sysUser = sysUserService.get(userId);
		LoginUser loginUser = sysUser.toLoginUser();
		if (loginUser == null) {
			return new ResponseEnvelope<>(false, "请重新登录", model.getMsgId());
		}
		// 1为外网 2 为内网 默认为外网
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();
		Integer usertype = loginUser.getUserType();

		// 拼装系统数据字典
		List<String> productHouseTypeList = this.createHouseTypeList(houseType);
		if (productHouseTypeList != null && productHouseTypeList.size() > 0) {
			groupSearch.setHouseTypeList(productHouseTypeList);
		}
		DesignPlanProduct designPlanProduct = null;
		// 设置主产品属性过滤查询条件
		if (planProductId != null && planProductId > 0) {
			designPlanProduct = designPlanProductService.get(planProductId);
			
			//当用户从移动端的推荐方案页面里搜索组合传进来的planProductId是副本的
			if(designPlanProduct == null) {
				DesignPlanProductRenderScene scene = designPlanProductRenderSceneService.get(planProductId);
				designPlanProduct = scene.designPlanProductCopy();
			}
			
			if (designPlanProduct != null) {
				// 设置组合类型查询条件xiaoxc-20170805
				if (StringUtils.isNotEmpty(designPlanProduct.getPlanGroupId())) {
					GroupProduct groupProduct = groupProductService.get(designPlanProduct.getProductGroupId());
					logger.error("designPlanProduct.getPlanGroupId()="+designPlanProduct.getPlanGroupId() + "designPlanProduct.getProductGroupId()=" + designPlanProduct.getProductGroupId());
					if (groupProduct != null && groupProduct.getCompositeType() != null
							&& groupProduct.getCompositeType() > 0) {
						groupSearch.setCompositeType(groupProduct.getCompositeType());/** getCompositeType 组合类型 **/
					} else {
						// 查询该分类code的级别,设置查询条件
						String categoryCode = productCategoryRel.getCategoryCode();
						if (StringUtils.isNotBlank(categoryCode)) {
							categoryCode = categoryCode.trim();
							groupSearch.setCategoryCode(categoryCode);
							String[] arr = productCategoryRel.getCategoryCode().split(",");
							for (String code : arr) {
								ProCategory proCategory = proCategoryService.findOneByCode(code);
								if (proCategory != null) {
									Integer level = proCategory.getLevel();
									if (level != null && level.equals(new Integer(0))) {
										groupSearch.setFirstStageCode(code);
									} else if (level != null && level.equals(new Integer(1))) {
										groupSearch.setSecondStageCode(code);
									} else if (level != null && level.equals(new Integer(2))) {
										groupSearch.setThirdStageCode(code);
									} else if (level != null && level.equals(new Integer(3))) {
										groupSearch.setFourthStageCode(code);
									} else if (level != null && level.equals(new Integer(4))) {
										groupSearch.setFifthStageCode(code);
									} else {

									}
								}
							}
						}
					}
				}
				/*productAttributeService.setProductAttributeQueryCondition(groupSearch,designPlanProduct.getProductId());*/
				// update by huangsongbo 2017.11.18
				// 以前的设置过滤属性查询条件有问题,不能根据product_attribute这张表取属性的code(因为code可能更新)
				groupSearch.setProductFilterPropList(baseProductService.getProductFilterPropList(designPlanProduct.getInitProductId()));
			} else {
				logger.error("designPlanProduct   is  null ");
			}
		}
		// 设置主产品属性过滤查询条件->end
		SpaceCommon spaceCommon = null;
		if (spaceCommonId != null) {
			spaceCommon = spaceCommonService.get(spaceCommonId);
		}
		if (spaceCommon != null && spaceCommon.getSpaceAreas() != null) {
			groupSearch.setSpaceCommonArea(Utils.getIntValue(spaceCommon.getSpaceAreas()));
		}

		/* 获取该用户绑定的序列号品牌 */
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
		String[] brandIdList = null;
		if (StringUtils.isNotBlank(brandIds)) {
			brandIdList = brandIds.split(",");
		}

		int total = 0;
		int byBrandIdsTotal = 0;
		List<SearchProductGroupResult> resultList = new ArrayList<>();

		boolean isStructure = false;
		if (structureId != null && structureId > 0) {
			isStructure = true;
		}

		try {
			/* brandIds 新增逻辑， 先通过授权码里的品牌查询有没有组合，如果没有再查所有组合 */
			// 结构的概念， 2016-12-28 added
			if (3 == usertype) {
				if (isStructure) {
					byBrandIdsTotal = groupProductService.getGroupCountByStructureIdAndStatus(structureId, usertype,
							brandIdList, versionType, 0);
				} else {
					this.groupProductServiceV2.configSearchFor(groupSearch, productTypeValue, smallTypeValue);
					groupSearch.setBrandIdList(brandIdList);
					byBrandIdsTotal = groupProductService.getGroupCountByProduct(groupSearch);
				}
			}
			if (byBrandIdsTotal <= 0) {
				groupSearch.setBrandIdList(null);
				if (isStructure) {
					total = groupProductService.getGroupCountByStructureIdAndStatus(structureId, usertype, null,
							versionType, 0);
				} else {
					this.groupProductServiceV2.configSearchFor(groupSearch, productTypeValue, smallTypeValue);
					groupSearch.setGroupType(0);
					total = groupProductService.getGroupCountByProduct(groupSearch);
				}
			}

			// 优化第一步
			if (total > 0 || byBrandIdsTotal > 0) { // 获取组基本信息，包括 res_file, res_pic
				// 返回了一些基本产品组信息，包括了是否已经收藏、资源图片
				List<SearchProductGroupResult> groupResultList = null;
				if (isStructure) {
					/* 加入只查询普通组合的逻辑 */
					groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(),
							structureId, usertype, versionType);
				} else {
					groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(),
							0, usertype, versionType);
				}

				if (groupResultList != null && groupResultList.size() > 0) {
					// 处理 组装产品组中的产品列表
					Map<Integer, List<SearchProductGroupDetail>> detailListMap = this.groupProductServiceV2
							.getGroupProductDetailsByGroupIDList(groupResultList, mediaType, brandIds, spaceCommonId);
					// test->start
					/*
					 * List<SearchProductGroupDetail> searchProductGroupDetailList =
					 * detailListMap.get(new Integer(1791)); List<SearchProductGroupDetail>
					 * searchProductGroupDetailList2 = detailListMap.get(new Integer(1959));
					 */
					// test->end
					if (detailListMap != null) {
						for (SearchProductGroupResult searchProductGroupResult : groupResultList) {
							Integer groupID = searchProductGroupResult.getGroupId();

							if (detailListMap.get(groupID) != null) {
								if (isStructure) {
									searchProductGroupResult.setStructureProductList(detailListMap.get(groupID));
								} else {
									searchProductGroupResult.setGroupProductDetails(detailListMap.get(groupID));
								}

							}
						}
						resultList = groupResultList;
					}
				}
			}
		} catch (Exception e) {
			logger.error("searchProductGroupV2 : " + e);
		}

		return new ResponseEnvelope<SearchProductGroupResult>(total, resultList, productCategoryRel.getMsgId());

	}

	/**
	 * 初始化 groupproductsearch 实体
	 * 
	 * @param productCategoryRel
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	private GroupProductSearch initGroupProductSearch(ProductCategoryRel productCategoryRel, Integer userStatusType) {
		// 产品组合搜索类
		GroupProductSearch groupSearch = new GroupProductSearch();
		groupSearch.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		groupSearch.setLimit(productCategoryRel.getLimit());
		groupSearch.setStart(productCategoryRel.getStart());
		groupSearch.setState(3);
		return groupSearch;
	}

	/**
	 * 创建数据字典
	 * 
	 * @param houseType
	 * @return
	 */
	private List<String> createHouseTypeList(String houseType) {
		List<String> productHouseTypeList = null;
		if (StringUtils.isNotBlank(houseType)) {
			/* 对应出productHouseType的数据字典的value */
			SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
			sysDictionarySearch.setAtt1(houseType);
			sysDictionarySearch.setType("productHouseType");
			List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
			productHouseTypeList = new ArrayList<String>();// 过滤条件
			// 将List<Integer> 装成 List<String>
			if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
				for (SysDictionary sysDictionary : sysDictionaryList) {
					productHouseTypeList.add("" + sysDictionary.getValue());
				}
			}
		}
		return productHouseTypeList;

	}

	/**
	 * 查询同类产品的材质
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object findTextureOfSameTypeProduct(Integer productId, String msgId, Integer planProductId, Integer userId) {
		SysUser sysUser = sysUserService.get(userId);
		LoginUser loginUser = new LoginUser();
		if (sysUser != null) {
			loginUser = sysUser.toLoginUser();
		} else {
			return new ResponseEnvelope<>(false, "该用户不存在！", msgId);
		}

		int total = 0;
		List<BaseProduct> list = new ArrayList<>();
		// 缓存开关
		BaseProduct bp = null;
		Integer id = productId;
		/* 取id为productId的产品 */
		BaseProduct baseProductNew = null;
		if (Utils.enableRedisCache()) {
			baseProductNew = BaseProductCacher.get(productId);
		} else {
			baseProductNew = baseProductService.get(productId);
		}
		if (baseProductNew == null)
			return new ResponseEnvelope<>(false, "产品未找到[id:" + productId + "]", msgId);
		/* 取id为productId的产品->end */
		DesignPlanProduct planProduct = designPlanProductService.get(planProductId);
		if (planProduct != null && planProduct.getModelProductId() != null && planProduct.getModelProductId() > 0) {
			id = planProduct.getModelProductId();
		}
		if (Utils.enableRedisCache()) {
			bp = BaseProductCacher.get(id);
		} else {
			bp = baseProductService.get(id);
		}
		if (bp == null) {
			return new ResponseEnvelope<BaseProduct>(total, list, msgId);
		}
		/* 返回可选材质信息 */
		Integer isSplit = new Integer(0);
		List<SplitTextureDTO> splitTexturesInfo = new ArrayList<SplitTextureDTO>();
		if (bp != null) {
			if (StringUtils.isNotBlank(baseProductNew.getSplitTexturesInfo())) {
				Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(baseProductNew.getId(),
						baseProductNew.getSplitTexturesInfo(), "info");
				isSplit = (Integer) map.get("isSplit");
				splitTexturesInfo = (List<SplitTextureDTO>) map.get("splitTexturesInfo");
			} else {
				isSplit = new Integer(0);
				if (baseProductNew.getParentId() != null && !baseProductNew.getParentId().equals(new Integer(0))) {
					// 媒介类型.如果没有值，则表示为web前端（2）
					BaseProduct products = new BaseProduct();
					products.setParentId(baseProductNew.getParentId());
					products.setIsInternalUser("false");
					// 如果是平吊天花查询同类型新增和贴图产品
					SysDictionary bigSysDic = sysDictionaryService.getSysDictionaryByValue("productType",
							Integer.valueOf(bp.getProductTypeValue()));
					SysDictionary smallSysDic = null;
					if (bigSysDic != null) {
						smallSysDic = sysDictionaryService.getSysDictionaryByValue(bigSysDic.getValuekey(),
								Integer.valueOf(bp.getProductSmallTypeValue()));
					}
					if (smallSysDic != null && "pdtianh".equals(smallSysDic.getValuekey())) {
						products.setProductTypeValue(bp.getProductTypeValue());
						products.setProductSmallTypeValue(smallSysDic.getValue());
					}
					products.setOrders(" id=" + productId + " DESC");
					total = baseProductService.getSameTypeProductCount(products);
					if (total > 1) {
						try {
							list = baseProductService.getSameTypeProductList(products);
							SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
							List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
							for (BaseProduct baseProduct : list) {
								String materalIds = baseProduct.getMaterialPicIds();
								List<String> materIdStrList = Utils.getListFromStr(materalIds);
								if (materIdStrList != null && materIdStrList.size() > 0) {
									ResTexture resTexture = null;
									// 解决已经被删除的材质也能查出来问题 add by yanghz
									for (String temId : materIdStrList) {
										resTexture = resTextureService.get(Integer.valueOf(temId));
										if (resTexture != null) {
											break;
										} else {
											continue;
										}
									}
									if (resTexture != null) {
										ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
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
							return new ResponseEnvelope<BaseProduct>(false, "数据异常!", msgId);
						}
					} else {
						total = 0;
					}
				}
			}
		}
		/* 返回可选材质信息->end */
		FindSameTypeProductListDTO result = new FindSameTypeProductListDTO(isSplit, splitTexturesInfo, null);
		return new ResponseEnvelope<>(result, msgId, true);
	}

	/**
	 * findSameTypeProductList接口返回格式类
	 * @author huangsongbo
	 *
	 */
	public class FindSameTypeProductListDTO {
		private Integer isSplit;
		private List<SplitTextureDTO> splitTexturesInfo;
		private List<BaseProduct> productList;

		public FindSameTypeProductListDTO() {
			super();
		}

		public FindSameTypeProductListDTO(Integer isSplit, List<SplitTextureDTO> splitTexturesInfo) {
			super();
			this.isSplit = isSplit;
			this.splitTexturesInfo = splitTexturesInfo;
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
	 * 根据id查询产品
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object selectProductById(Integer id, boolean onlyDefault) {
		BaseProduct baseProduct = baseProductService.get(id);
		String textuerInfo = baseProduct.getSplitTexturesInfo();
		if (textuerInfo != null && textuerInfo != "") {
			if(!onlyDefault) {
				//如果不仅仅需要默认材质，就按原方法处理拆分材质信息
				Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(id, textuerInfo, "info");
				Integer isSplit = (Integer) map.get("isSplit");
				List<SplitTextureDTO> splitTexturesInfo = (List<SplitTextureDTO>) map.get("splitTexturesInfo");
				FindSameTypeProductListDTO result = new FindSameTypeProductListDTO(isSplit, splitTexturesInfo);
				return new ResponseEnvelope<>(true,result);
			}
			List<String> textuerIds = new ArrayList<>();
			// 将产品的多维材质转成list
			List textuerInfoList = Utils.getJSONObjectByString(textuerInfo);
			if (textuerInfoList.size() > 0) {
				for (Object object : textuerInfoList) {
					// 将list转成map
					Map<String, String> splitTexturesInfo = Utils.getMapByJson(object.toString());
					// 获取默认材质
					String textureId = splitTexturesInfo.get("defaultId");
					textuerIds.add(textureId);
				}
			}
			ResTexture resTexture_ = new ResTexture();
			resTexture_.setResTextureIds(textuerIds);
			
			List list = resTextureService.getBatchGet(resTexture_);
			return new ResponseEnvelope(list.size(), list);
		}
		return new ResponseEnvelope(true, "此产品无材质");
	}

	/**
	 * 根据id查询材质
	 */
	@Override
	public Object selectTextureById(Integer id) {
		ResTexture resTexture = resTextureService.get(id);

		if (resTexture.getBrandId() != null && resTexture.getBrandId() > 0) {
			BaseBrand baseBrand = baseBrandService.get(resTexture.getBrandId());
			resTexture.setBrandName(baseBrand.getBrandName());
		}

		return new ResponseEnvelope<>(true, resTexture);
	}

}
