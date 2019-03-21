package com.nork.product.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductRenderScene;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.model.search.MobileSearchProductModel;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProCategoryCacher;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.FindSameTypeProductListDTO;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.ProductModelConstant;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.CompanyUnionService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.GroupProductServiceV2;
import com.nork.product.service.MobileWebSearchService;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * Created by yangz on 2017/12/12.
 */
@Service("mobileWebSearchService")
public class MobileWebSearchServiceImpl implements MobileWebSearchService {

    private static Logger logger =
            Logger.getLogger(MobileWebSearchServiceImpl.class);

    private static final String TYPE_NOT_NULL = "参数type不能为空";
    private static final String TYPE_IS = "参数type为‘";
    private static final String MUST_BE_INTRODUCTION_CID = "’时，必须传入cid参数";
    private static final String ELSE = "其他";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DesignPlanService designPlanService;
    @Autowired
    private ProCategoryService proCategoryService;
    @Autowired
    private ProductCategoryRelService productCategoryRelService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private AuthorizedConfigService authorizedConfigService;
    @Autowired
    private GroupProductService groupProductService; // 产品组合 service
    @Autowired
    private GroupProductServiceV2 groupProductServiceV2; // 产品组合 service v2
    @Autowired
    private DesignPlanProductService designPlanProductService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private ResTextureService resTextureService;
    @Autowired
    private BaseBrandService baseBrandService;
    @Autowired
    private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
    @Autowired
    private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
    @Autowired
    private CompanyUnionService companyUnionService;
    @Autowired
    private BaseCompanyService baseCompanyService;

    @Override
    public Object searchProduct(MobileSearchProductModel model, String mediaType,String style) {

        String templateProductId = null;
        String productModelOrBrandName = null;
        String smallpox = null;

        Integer planProductId = model.getPlanProductId();
        Integer productId = model.getProductId();
        String houseType = model.getHouseType();
        Integer productTypeValue = model.getProductTypeValue();
        Integer smallTypeValue = model.getSmallTypeValue();
        Integer spaceCommonId = model.getSpaceCommonId();
        Integer designPlanId = model.getDesignPlanId();
        Integer isStandard = model.getIsStandard();
        String regionMark = model.getRegionMark();
        Integer styleId = model.getStyleId();
        String measureCode = model.getMeasureCode();
        Integer userId = model.getUserId();
        Integer limit = model.getLimit();
        Integer start = model.getStart();
        String msgId = model.getMsgId();
        String categoryCode = model.getCategoryCode();
        String platformCode = model.getPlatformCode();
        // 获取配置:决定是否走老接口还是新接口
        // 用户信息
        SysUser sysUser = sysUserService.get(userId);
        LoginUser loginUser = sysUser.toLoginUser();

        Integer userStatusType = loginUser.getUserType();
        DesignPlan designPlan = designPlanService.getDesignPlan(designPlanId,msgId);

        if (StringUtils.isBlank(categoryCode)) {
            return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", model.getMsgId());
        }
        ProductCategoryRel productCategoryRel = new ProductCategoryRel();
        productCategoryRel.setCategoryCode(categoryCode);
        productCategoryRel.setLimit(limit);
        productCategoryRel.setStart(start);
        productCategoryRel.setMsgId(msgId);

        logger.debug("param:designPlanId=" + designPlanId + ";productId=" + productId + ";spaceCommonId=" + spaceCommonId
                + ";houseType=" + houseType + ";productTypeValue=" + productTypeValue + ";smallTypeValue="
                + smallTypeValue + ";categoryCode=" + categoryCode + ";styleId=" + styleId);

        if (loginUser == null || loginUser.getId() == null || loginUser.getId() < 1) {
            return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", msgId);
        } // 参数验证 ->end

        ResponseEnvelope<CategoryProductResult> result = productCategoryRelService.searchProductV6(templateProductId,
                productCategoryRel, loginUser, productModelOrBrandName, planProductId, houseType, productTypeValue,
                spaceCommonId, designPlan, smallTypeValue, mediaType, userStatusType, isStandard, regionMark, styleId,
                measureCode, smallpox,productId,style,platformCode
                /*加一个msgId来判断从那张表里去方案产品的数据*/
                ,msgId);//style==online时  移动端、运营网站使用正式数据，预处理
        return result;
    }

    @Override
    public Object searchProCategory(String type, Integer cid, String msgId) {
        long startTime = System.currentTimeMillis();
        String msg = "";
//        List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
        if (StringUtils.isNotBlank(type)) {
            ProCategory category = null;
            int total = 0;
            // 查询所有类目
            if ("A".equals(type)) {
                if (Utils.enableRedisCache()) {
                    category = (ProCategory) ProCategoryCacher.getAllList();
                } else {
                    category = proCategoryService.getAllCategory();
                }
            }
            if (category != null) {
                return resultList(msgId, startTime, category, total);
            }
        } else {
            msg = TYPE_NOT_NULL;
            return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
        }
        return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
    }

    @SuppressWarnings("unchecked")
	private Object resultList(String msgId, long startTime, ProCategory category, int total) {
        SearchProCategorySmall categorySmall;
        categorySmall = new SearchProCategorySmall();
        categorySmall.setGg(category.getNameFirstLetter());
        categorySmall.setAa(category.getId());
        categorySmall.setBb(category.getCode());
        categorySmall.setCc(category.getPid());
        categorySmall.setDd(category.getName());
        categorySmall.setFf(category.getChildrenNodes() == null ? new ArrayList<SearchProCategorySmall>() : category.getChildrenNodes());
        categorySmall.setPicPath(category.getPicPath());
        List<SearchProCategorySmall> resultList = new ArrayList<SearchProCategorySmall>();
        resultList.add(categorySmall);
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        logger.info("searchProCategory executeTime : " + executeTime + "ms");
        MyCompartor compartor = new MyCompartor();
        Collections.sort(resultList, compartor);
        return new ResponseEnvelope<SearchProCategorySmall>(total, resultList, msgId);
    }

    @SuppressWarnings("rawtypes")
	// 1、排序升序 2、name拼音首字排序
    class MyCompartor implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            ProCategory sdto1 = (ProCategory) o1;
            ProCategory sdto2 = (ProCategory) o2;
            int flag = (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
                    .compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
            if (flag != 0) {
                return (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
                        .compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
            } else {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(sdto1.getName(), sdto2.getName());
            }
        }
    }

    @Override
    public Object searchProductGroup(MobileSearchProductModel model, String mediaType) {

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
        String platformCode = model.getPlatformCode();

        SysUser sysUser = sysUserService.get(userId);
        Integer userStatusType = sysUser.getUserType();

        // 初始化
        GroupProductSearch groupSearch = this.initGroupProductSearch(productCategoryRel, userStatusType);
        if (groupSearch == null) {
            return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", model.getMsgId());
        }

        LoginUser loginUser = sysUser.toLoginUser();
        if (loginUser == null) {
            return new ResponseEnvelope<>(false, "请重新登录", model.getMsgId());
        }
        //添加异业联盟逻辑
//        if("brand2c".equals(platformCode) || "mobile2c".equals(platformCode)){
//            groupSearch.setBrandIds(this.getBrandIdsByProductId(model.getProductId()));
////            groupSearch.setBrandIds(this.adjustBrandIds(model.getProductId()));
//        }

        // 设置非内部员工不能查询一建装修组合
// 		if(!(new Integer(1).equals(loginUser.getUserType()))){
// 		    logger.info("非内部员工="+loginUser.getUserType());
// 			groupSearch.setGroupType(0);
// 		}
        //修改为都不能看到一键装修组合 2018.03.04 gaojun
        groupSearch.setGroupType(0);

        // 1为外网 2 为内网 默认为外网
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
                if (scene != null) {
                    designPlanProduct = scene.designPlanProductCopy();
                } else {
                	DesignPlanRecommendedProduct recommendedProduct = designPlanRecommendedProductServiceV2.get(planProductId);
                	if(recommendedProduct == null) {
                		return new ResponseEnvelope<>(false, "数据错误!", model.getMsgId());
                	}
                	designPlanProduct = recommendedProduct.recommendedProductCopy();
                }
            }

            if (designPlanProduct != null) {
                // 设置组合类型查询条件xiaoxc-20170805
                if (StringUtils.isNotEmpty(designPlanProduct.getPlanGroupId())) {
                    GroupProduct groupProduct = groupProductService.get(designPlanProduct.getProductGroupId());
                    logger.debug("designPlanProduct.getPlanGroupId()="+designPlanProduct.getPlanGroupId() + "designPlanProduct.getProductGroupId()=" + designPlanProduct.getProductGroupId());
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
//        AuthorizedConfig authorizedConfig = new AuthorizedConfig();
//        authorizedConfig.setUserId(loginUser.getId());
//        authorizedConfig.setState(1);
//        List<AuthorizedConfig> authorizedList = new ArrayList<>();
//        authorizedList = authorizedConfigService.getList(authorizedConfig);
//        String brandIds = "";
//        for (AuthorizedConfig ac : authorizedList) {
//            if (StringUtils.isNotBlank(ac.getBrandIds())) {
//                brandIds += ac.getBrandIds() + ",";
//            }
//        }
//        String[] brandIdList = null;
//        if (StringUtils.isNotBlank(brandIds)) {
//            brandIdList = brandIds.split(",");
//        }

		//去掉序列号的逻辑改从用户关联的企业取品牌信息
		String brandIds = "";
		String productVisibilityRange = "";
		List<Integer> brandIdsList = new ArrayList<>();
		List<String> visibilityRangeCodeList = new ArrayList<>();
		Integer companyId = sysUser.getBusinessAdministrationId();
		if(null != companyId && companyId > 0) {
			BaseCompany baseCompany = baseCompanyService.get(companyId);
			if(null != baseCompany) {
				if(3 == sysUser.getUserType().intValue()) {//经销商
					brandIds = baseCompany.getBrandIds();
				}else if(2 == sysUser.getUserType()){//厂商
					brandIds = baseBrandService.getBrandIdsStrByCompanyId(companyId);
				}else {//其他的都没有品牌
					
				}
				productVisibilityRange = baseCompany.getProductVisibilityRange();
				if(null != brandIds && !"".equals(brandIds)) {
					brandIdsList = Utils.getIntegerListFromStringList(brandIds);
				}else {
					logger.error("因为去掉序列号的逻辑，企业表中的“经销商所属品牌ids”是空");
				}
				if(null != productVisibilityRange && !"".equals(productVisibilityRange)) {
					//将可见产品范围转成list
					List<Integer> visibilityRangeIdList = Utils.getIntegerListFromStringList(productVisibilityRange);
					//根据所有可见范围的类目id获得其父级类目的codeList
					visibilityRangeCodeList = proCategoryService.getParentCodeListByIdList(visibilityRangeIdList);
				}else {
					logger.error("因为去掉序列号的逻辑，该企业配置的“产品可见范围”是空");
				}
			}else {
				logger.error("因为去掉序列号的逻辑，用企业/经销商id查询到的企业信息是空");
			}
		}else {
			logger.error("因为去掉序列号的逻辑，通过用户id查到的用户的企业/经销商id是空");
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
        	// 重构by huangsongbo 2017.12.8,加入授权码新逻辑 ->start
			if (isStructure) {
				total = groupProductService.getGroupCountByStructureIdAndStatus(structureId, usertype, null, null, 0);
			}else {
				this.groupProductServiceV2.configSearchFor(groupSearch, productTypeValue, smallTypeValue);
				// 设置授权码查询条件
//				this.setAuthorizedConfigSearchParam(productTypeValue, smallTypeValue, groupSearch, loginUser);
				if(!"brand2c".equals(platformCode) && !"mobile2c".equals(platformCode)) {
					//去掉序列号逻辑
					this.setProductVisibilityRangeParam(productTypeValue, smallTypeValue, groupSearch
							, usertype, brandIdsList, companyId ,visibilityRangeCodeList);
				}
				byBrandIdsTotal = groupProductService.getGroupCountByProduct(groupSearch);
			}
			// 重构by huangsongbo 2017.12.8,加入授权码新逻辑 ->end
            // 优化第一步
            if (total > 0 || byBrandIdsTotal > 0) { // 获取组基本信息，包括 res_file, res_pic
                // 返回了一些基本产品组信息，包括了是否已经收藏、资源图片
                List<SearchProductGroupResult> groupResultList = null;
                if (isStructure) {
					/* 加入只查询普通组合的逻辑 */
                    groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(),
                            structureId, usertype, null);
                } else {
                    groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(),
                            0, usertype, null);
                }

                if (groupResultList != null && groupResultList.size() > 0) {
                    // 处理 组装产品组中的产品列表
                    Map<Integer, List<SearchProductGroupDetail>> detailListMap = this.groupProductServiceV2
                            .getGroupProductDetailsByGroupIDList(groupResultList, mediaType, brandIds, spaceCommonId);
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
        if (isStructure) {
			
		}else {
			total = byBrandIdsTotal;
		}
        return new ResponseEnvelope<SearchProductGroupResult>(total, resultList, productCategoryRel.getMsgId());

    }
    
    /**
     * 设置产品大小类、产品可见范围、无品牌等条件
     * @param productTypeValue
     * @param smallTypeValue
     * @param groupSearch
     */
    private void setProductVisibilityRangeParam(String productTypeValue, String smallTypeValue, GroupProductSearch groupSearch
			, Integer userType,List<Integer> brandIdsList, Integer companyId
			,List<String> visibilityRangeCodeList) {

		if (Utils.isExternalUser(userType)) {
			// 获取用户点击的产品类型
			SysDictionary sysDictionarySmallType = null;
			try {
				sysDictionarySmallType = sysDictionaryService.findOneByTypeAndValueAndValue("productType",
						Integer.valueOf(productTypeValue), Integer.valueOf(smallTypeValue));
			} catch (Exception e) {
				// 防止数字类型转换错误
			}
			if (sysDictionarySmallType != null) {
				String checkCategory = sysDictionarySmallType.getType();
				List<BaseProduct> baseProductList = new ArrayList<>();
				groupSearch.setBaseProductList(baseProductList);
				// 是否显示无品牌 ->start
				// copy from 产品搜索
				boolean isShowWuBoolean = true;
				if(null != baseProductList && baseProductList.size() > 0) {
					for (BaseProduct baseProduct : baseProductList) {
						if (ProductModelConstant.STATUSSHOWWU_NOSHOW.intValue() == baseProduct.getStatusShowWu()
								&& baseProduct.getStatusShowWu() != null) {
							isShowWuBoolean = false;
							break;
						}
					}
				}
				String isShowWu = Utils.getValue("app.product.searchProduct.isShowWu", "1");
				if (StringUtils.equals("1", isShowWu) && isShowWuBoolean) {
					groupSearch.setShowWu(true);
				}
				BaseBrand baseBrand = baseBrandService.findOneByBrandReferred(ProductModelConstant.BRAND_REFERRED_WU);
				Integer brandIdWuPinPai = 0;
				if (baseBrand != null) {
					brandIdWuPinPai = baseBrand.getId();
				}
				groupSearch.setBrandIdWuPinPai(brandIdWuPinPai);
				// 是否显示无品牌 ->end

				logger.error("visibilityRangeCodeList = " + visibilityRangeCodeList + ",checkCategory = " +checkCategory);
				logger.error("visibilityRangeCodeList.contains(checkCategory) = " + visibilityRangeCodeList.contains(checkCategory));
				if(visibilityRangeCodeList.contains(checkCategory)) {
					groupSearch.setBrandIds(brandIdsList);
				}
			}
		}

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
        groupSearch.setState(3);// 只能看到已发布数据
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
     * 递归查询分类
     *
     * @param category
     * @return
     */
    private List<SearchProCategorySmall> recursionCategory(ProCategory category) {
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        List<ProCategory> list = null;

        if (Utils.enableRedisCache()) {
            ProCategory pc = new ProCategory();
            pc.setPid(category.getId());
            pc.setLongCode(category.getLongCode());
            list = ProCategoryCacher.getAllList(pc);
        } else {
            ProCategorySearch search = new ProCategorySearch();
            search.setPid(category.getId());
            search.setLongCode(category.getLongCode());
            list = proCategoryService.getList(search);
        }

        if (list != null && list.size() > 0) {
            if (childrenNodes == null) {
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
            SearchProCategorySmall newCategory = null;
            SearchProCategorySmall newCategory_ = null;
            for (ProCategory childrenNode : list) {
                if (ELSE.equals(childrenNode.getName())) {
                    newCategory_ = new SearchProCategorySmall();
                    newCategory_.setAa(childrenNode.getId());
                    newCategory_.setCc(childrenNode.getPid());
                    newCategory_.setBb(childrenNode.getCode());
                    newCategory_.setDd(childrenNode.getName());
                    newCategory_.setFf(recursionCategory(childrenNode));
                    newCategory_.setPicPath(childrenNode.getPicPath());
                } else {
                    newCategory = new SearchProCategorySmall();
                    newCategory.setAa(childrenNode.getId());
                    newCategory.setCc(childrenNode.getPid());
                    newCategory.setBb(childrenNode.getCode());
                    newCategory.setDd(childrenNode.getName());
                    newCategory.setFf(recursionCategory(childrenNode));
                    newCategory.setPicPath(childrenNode.getPicPath());
                    childrenNodes.add(newCategory);
                }
            }
            if (newCategory_ != null) {
                childrenNodes.add(newCategory_);
            }
            category.setChildrenNodes(childrenNodes);
        }
        return childrenNodes;
    }

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
                    products.setIsInternalUser("yes");
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
                            List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
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
                                        SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
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

    @Override
    public Object selectTextureById(Integer id) {
        ResTexture resTexture = resTextureService.get(id);

        if (resTexture.getBrandId() != null && resTexture.getBrandId() > 0) {
            BaseBrand baseBrand = baseBrandService.get(resTexture.getBrandId());
            resTexture.setBrandName(baseBrand.getBrandName());
        }

        return new ResponseEnvelope<>(true, resTexture);
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
    /**
     * 获取所有联盟品牌ID
     * @param productId
     * @return
     */
	private List<Integer> getBrandIdsByProductId(Integer productId){
    	List<Integer> brandIds = baseBrandService.getBrandIdsByProductId(productId);
        if (brandIds.size() == 0) {
            brandIds = baseBrandService.getProductOwnBrandIds(productId);
        }
		return brandIds;
    	
    }
    
    /**
	 * 设置授权码查询条件
	 * @param productTypeValue
	 * @param smallTypeValue
	 * @param groupSearch
	 * @param loginUser
	 */
	private void setAuthorizedConfigSearchParam(String productTypeValue, String smallTypeValue, GroupProductSearch groupSearch,
			LoginUser loginUser) {
		if(Utils.isExternalUser(loginUser.getUserType())) {
			// 获取用户点击的产品类型
			SysDictionary sysDictionarySmallType = null;
			try {
				sysDictionarySmallType = sysDictionaryService.findOneByTypeAndValueAndValue("productType", Integer.valueOf(productTypeValue), Integer.valueOf(smallTypeValue));
			}catch (Exception e) {
				// 防止数字类型转换错误
			}
			if(sysDictionarySmallType != null) {
				List<BaseProduct> baseProductList = 
						baseProductService.getSelectConditionByAuthorizedConfigV3(loginUser, sysDictionarySmallType.getType());
				groupSearch.setBaseProductList(baseProductList);
				// 是否显示无品牌 ->start
				// copy from 产品搜索
				boolean isShowWuBoolean = true;
				for(BaseProduct baseProduct : baseProductList) {
					if(
							baseProduct.getStatusShowWu() != null 
							&& ProductModelConstant.STATUSSHOWWU_NOSHOW.intValue() == baseProduct.getStatusShowWu()) {
						isShowWuBoolean = false;
						break;
					}
				}
				String isShowWu = Utils.getValue("app.product.searchProduct.isShowWu", "1");
				if(StringUtils.equals("1", isShowWu) && isShowWuBoolean){
					groupSearch.setShowWu(true);
				}
				BaseBrand baseBrand = baseBrandService.findOneByBrandReferred(ProductModelConstant.BRAND_REFERRED_WU);
				Integer brandIdWuPinPai = 0;
				if(baseBrand != null){
					brandIdWuPinPai = baseBrand.getId();
				}
				groupSearch.setBrandIdWuPinPai(brandIdWuPinPai);
				// 是否显示无品牌 ->end
			}
		}
	}
}
