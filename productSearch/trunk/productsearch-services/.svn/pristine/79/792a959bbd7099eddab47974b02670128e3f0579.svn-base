package com.nork.mobile.service.impl;

import java.util.*;

import com.nork.design.model.DesignPlanProductRenderScene;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.product.model.*;
import com.nork.product.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.model.search.MobileSearchProductModel;
import com.nork.mobile.service.MobileSearchProductGroupService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

import javax.annotation.Resource;

/**
 * 
 * @author yangzhun
 * @createTime 2017年10月27日16:16:04
 */
@Service("mobileSearchProductGroupService")
public class MobileSearchProductGroupServiceImpl implements MobileSearchProductGroupService {

	private static Logger logger = Logger.getLogger(MobileSearchProductGroupServiceImpl.class);
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
	@Resource
	private CompanyUnionService companyUnionService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	@Autowired
    private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;

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
		//添加异业联盟逻辑
		groupSearch.setBrandIds(this.adjustBrandIds(model.getProductId()));

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
                if(scene == null) {
                	DesignPlanRecommendedProduct recommendedProduct = designPlanRecommendedProductServiceV2.get(planProductId);
                	if(recommendedProduct == null) {
                		return new ResponseEnvelope<>(false, "数据错误!", model.getMsgId());
                	}
                	designPlanProduct = recommendedProduct.recommendedProductCopy();
                }
                designPlanProduct = scene.designPlanProductCopy();
            }
			if (designPlanProduct != null) {
				// 设置组合类型查询条件xiaoxc-20170805
				if (StringUtils.isNotEmpty(designPlanProduct.getPlanGroupId())) {
					GroupProduct groupProduct = groupProductService.get(designPlanProduct.getProductGroupId());
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
	private GroupProductSearch initGroupProductSearch(ProductCategoryRel productCategoryRel, Integer userStatusType) {
		// 产品组合搜索类
		GroupProductSearch groupSearch = new GroupProductSearch();
		groupSearch.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		groupSearch.setLimit(productCategoryRel.getLimit());
		groupSearch.setStart(productCategoryRel.getStart());
		groupSearch.setState(3);// 外部用户只能看到已发布数据
		return groupSearch;
	}

	/**
	 * 创建数据字典
	 * k
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
					String versionType = Utils.getPropertyName("app", "sys.version.type", "1")
							.trim();/* 1为外网 2 为内网 默认为外网 */
					if (loginUser.getUserType() == 1 && "2".equals(versionType)) {
						products.setIsInternalUser("yes");
					}
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


	//获取联盟品牌id
	private List<Integer> adjustBrandIds(Integer productId) {
		Integer companyId = baseProductService.getProductCompanyId(productId);
		List<Integer> companyIds = companyUnionService.getAllCompanyId(companyId);
		if (companyIds == null || companyIds.size() <= 0) {
			companyIds = Collections.singletonList(companyId);
		}
		List<Integer> brandIds = new ArrayList<>();
		for (Integer id : companyIds) {
			//查询公司品牌
			brandIds.addAll(baseBrandService.queryBrandListByCompanyId(id));
		}
		return brandIds;
	}
}
