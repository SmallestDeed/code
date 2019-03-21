package com.nork.product.service.impl;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.nork.base.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.objectconvert.baseseries.BaseSeriesObject;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.cache.GroupProductCacher;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.dao.StructureProductMapper;
import com.nork.product.model.*;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.model.vo.BaseCompanyMiniProgramConfig;
import com.nork.product.model.vo.BaseSeriesVO;
import com.nork.product.model.vo.ProductCeilingVO;
import com.nork.product.service.*;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.bo.SysDictionaryBo;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.*;
import com.nork.user.model.UserTypeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangz on 2017/12/12.
 */
@Service("commonAppSearchService")
public class CommonAppSearchServiceImpl implements CommonAppSearchService {


    private static Logger logger = Logger
            .getLogger(CommonAppSearchServiceImpl.class);
    //Json转换类
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[系列模块]:";
    
    private static final String LOGPREFIX = "[产品搜索服务]:";
    
    public static final String PLATFORM_CODE_MINI_PROGRAM= "miniProgram";
    @Autowired
    private ProductCategoryRelService productCategoryRelService;
    /*@Autowired
    private AuthorizedConfigService authorizedConfigService;*/
    @Autowired
    private SysDictionaryService sysDictionaryService; // 系统数据字典组合
    @Autowired
    private GroupProductService groupProductService; // 产品组合 service
    @Autowired
    private GroupProductServiceV2 groupProductServiceV2; // 产品组合 service v2
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
    private StructureProductService structureProductService;
    @Autowired
    private StructureProductMapper structureProductMapper;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private BaseSeriesService baseSeriesService;
    @Autowired
    private DesignPlanService designPlanService;
    @Autowired
    private DesignTempletService designTempletService;
    @Autowired
	private BaseBrandService baseBrandService;
    @Autowired
    private BaseProductMapper baseProductMapper;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private SysUserService sysUserService;
    @Autowired
    private BaseProductCountertopsService baseProductCountertopsService;

    @Autowired
    private ModelAreaRelService modelAreaRelService;
    /**
	 * 通过idList查询产品详情
	 * add by yangz 2017-12-28 15:16:45
	 */
	@Override
	public ResponseEnvelope<CategoryProductResult> getDetailByIds(List<Integer> idList, Integer limit, Integer start, Integer userId,
			String msgId, Integer isStandard, String regionMark, Integer styleId, String measureCode,
            Integer designPlanId, Integer planProductId, String mediaType,
            Integer isSpellingFlowerProduct
			) {
		
		// 参数验证/处理 ->start
		if(isSpellingFlowerProduct == null) {
			isSpellingFlowerProduct = 0;
		}
		// 参数验证/处理 ->end
		
		//定义一个返回数据的list
		List<CategoryProductResult> returnList = new ArrayList<>();
		Map<Integer ,CategoryProductResult> resultMap = new HashMap<>();

        //根据msgId类型不同和通过方案id获取方案信息(非PC端msgId之前作为类型来用)
        DesignPlan designPlan = designPlanService.getDesignPlan(designPlanId,msgId);

        //根据msgId类型不同和通过方案产品id获取方案产品信息(非PC端msgId之前作为类型来用)
        DesignPlanProduct designPlanProduct = designPlanService.getDesignProduct(planProductId, msgId);

		//根据idList查询产品详情
		List<CategoryProductResult> datalist = baseProductMapper.getDetailByIds(idList, limit, start, userId);

		//获取伸缩产品全铺长度
        Integer fullPaveLength = getFullPaveLength(designPlanProduct);

		// 获取需要自动携带白模产品的配置->start
        Map<String, AutoCarryBaimoProducts> autoCarryMap = productCategoryRelService.getAutoCarryMap();
		//遍历详情list
        List<Integer> ceilingProductIdsList = new ArrayList<>();
		for(CategoryProductResult categoryProductResult : datalist){
		    //如果是截面天花存入list集合后续查询用到
            if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(categoryProductResult.getProductTypeCode())) {
                ceilingProductIdsList.add(categoryProductResult.getProductId());
            }
            
			// 处理材质 ->start
            if(ProductConstant.ISSPELLINGFLOWERPRODUCT_TRUE.equals(isSpellingFlowerProduct)) {
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

//            	productCategoryRelService.dealWithTextureInfo(categoryProductResult);

            }
			// 处理材质 ->end

            // 处理产品 橱柜 信息
            baseProductCountertopsService.setCountertopsDetails(categoryProductResult);
			
			// 补充信息->start
			categoryProductResult.setModelProductId(categoryProductResult.getProductId());
			
			// 软硬装:0:软装;1:硬装
			String smallTypeAtt1 = categoryProductResult.getSmallTypeAtt1()==null?"2":categoryProductResult.getSmallTypeAtt1().trim();
			categoryProductResult.setRootType(smallTypeAtt1);
			
			// bgWall:是否是背景墙
			Integer bgWallValue = baseProductService.getBgWallValue(categoryProductResult.getProductTypeCode(),categoryProductResult.getProductSmallTypeCode());
			categoryProductResult.setBgWall(bgWallValue);
			
			// 产品附加属性->start
			Map<String, String> map = new HashMap<String, String>();
			map = productAttributeService.getPropertyMapV2(categoryProductResult.getProductId());
			categoryProductResult.setPropertyMap(map);
			// 产品附加属性->end
			
			//标准、款式、尺寸代码回填
			categoryProductResult.setStyleId(styleId);
			categoryProductResult.setIsStandard(isStandard);
			categoryProductResult.setRegionMark(regionMark);
			categoryProductResult.setMeasureCode(measureCode);

            // 自动携带白膜属性(未优化) ->start
            baseProductService.setBasicModelMap(categoryProductResult, autoCarryMap, map, designPlan, designPlanProduct, mediaType);
            // 自动携带白膜属性(未优化) ->end

            //产品需拉伸缩放的长度值
            Map<String,String> isStretchZoom = baseProductService.getStretchZoomLength(categoryProductResult.getProductSmallTypeCode());
            if (isStretchZoom != null && 0 < isStretchZoom.size()) {
                if (0 == fullPaveLength) {
                    fullPaveLength = Utils.getIntValue(categoryProductResult.getProductLength());
                }
                categoryProductResult.setFullPaveLength(fullPaveLength);
            }
			// 补充信息->end
			
			//将得到的list转换成一个以id为key，数据为value的map
			resultMap.put(categoryProductResult.getProductId(),categoryProductResult);
		}

		//通过遍历idList，将返回数据按照idList的顺序排序
		for(Integer id : idList) {
			returnList.add(resultMap.get(id));
		}

        //新逻辑客户端用到的天花数据存储到单独VO里
        if (null != ceilingProductIdsList && 0 < ceilingProductIdsList.size()) {
            List<ProductCeilingVO> productCeilingVOList = baseProductService.getCeilingInfoByProductIds(ceilingProductIdsList);
            returnList.forEach(categoryProductResult -> {
                productCeilingVOList.forEach(productCeilingVO -> {
                    if (categoryProductResult.getProductId().equals(productCeilingVO.getProductId())) {
                        productCeilingVO.setRegionMark(categoryProductResult.getRegionMark());
                        categoryProductResult.setProductCeilingVO(productCeilingVO);
                    }
                });
            });
        }

		return new ResponseEnvelope<CategoryProductResult>(idList.size(),returnList,msgId);
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
			if (StringUtils.isNotEmpty(baseProduct.getFullPaveLength())) {
				fullPaveLength = Integer.parseInt(baseProduct.getFullPaveLength());
			} else {
				if (StringUtils.isNotEmpty(baseProduct.getProductLength())) {
					fullPaveLength = Integer.parseInt(baseProduct.getProductLength());
				}
			}
		}
        return fullPaveLength;
    }
    
    @Override
    public Object searchSingleProduct(String templateProductId, ProductCategoryRel productCategoryRel
            , LoginUser loginUser, String productModelOrBrandName, Integer planProductId, String houseType
            , Integer productTypeValue, Integer spaceCommonId, Integer smallTypeValue
            , String mediaType, Integer statusType, Integer isStandard, String regionMark, Integer styleId
            , String measureCode, String smallpox,Integer designPlanId, String style) {
        DesignPlan designPlan = new DesignPlan();
        if (designPlanId != null && designPlanId != 0)
            designPlan = designPlanService.get(designPlanId);
        if (designPlan == null) {
            return new ResponseEnvelope<ProductCategoryRel>(false, "找不到该设计方案：" + designPlanId, productCategoryRel.getMsgId());
        }
        ResponseEnvelope<CategoryProductResult> result =
            productCategoryRelService.searchProductV6(templateProductId, productCategoryRel, loginUser, productModelOrBrandName,
                    planProductId, houseType, productTypeValue, spaceCommonId, designPlan, smallTypeValue, mediaType, statusType,
                    isStandard, regionMark, styleId, measureCode, smallpox,null, style,null,null);
        result.setMsgId(productCategoryRel.getMsgId());
        return result;
    }

    @Override
    public Object searchProductGroupV2(ProductCategoryRel productCategoryRel,String houseType,Integer designPlanId,
                                       Integer planProductId,Integer spaceCommonId,String templateProductId,
                                       String  productTypeValue,String  smallTypeValue,String queryType,
                                       String groupType,String type,Integer structureId,Integer designTempletId,
                                       Integer userStatusType,LoginUser loginUser,GroupProductSearch groupSearch,
                                       String mediaType) {

    	Integer usertype = loginUser.getUserType();
    	
    	//修改为非内部用户和设计师不能查询一键装修组合
        if(!(UserTypeCode.USER_TYPE_INNER.equals(usertype) || UserTypeCode.USER_TYPE_DESIGNER.equals(usertype))){
            logger.info("非内部员工或设计师,用户类型为" + usertype);
            groupSearch.setGroupType(0);
        }

        // 拼装系统数据字典
        List<String> productHouseTypeList = this.createHouseTypeList(houseType);
        if ( productHouseTypeList != null && productHouseTypeList.size()>0 ) {
            groupSearch.setHouseTypeList(productHouseTypeList);
        }
        else {}
        DesignPlanProduct designPlanProduct = null;
        //设置主产品属性过滤查询条件
        if(planProductId!=null&&planProductId>0){
            designPlanProduct = designPlanProductService.get(planProductId);
            if (designPlanProduct != null) {
                //设置组合类型查询条件xiaoxc-20170805
                if (StringUtils.isNotEmpty(designPlanProduct.getPlanGroupId())){
                    GroupProduct groupProduct = groupProductService.get(designPlanProduct.getProductGroupId());
                    if (groupProduct != null && groupProduct.getCompositeType() != null && groupProduct.getCompositeType() > 0) {
                        groupSearch.setCompositeType(groupProduct.getCompositeType());
                    }else {
                        //查询该分类code的级别,设置查询条件
                        String categoryCode = productCategoryRel.getCategoryCode();
                        if( StringUtils.isNotBlank(categoryCode) ){
                            categoryCode = categoryCode.trim();
                            groupSearch.setCategoryCode(categoryCode);
                            String[] arr = productCategoryRel.getCategoryCode().split(",");
                            for( String code : arr ){
                                ProCategory proCategory = proCategoryService.findOneByCode(code);
                                if ( proCategory != null ) {
                                    Integer level = proCategory.getLevel();
                                    if (level != null && level.equals(new Integer(0))) {
                                        groupSearch.setFirstStageCode(code);
                                    } else if(level != null && level.equals(new Integer(1))) {
                                        groupSearch.setSecondStageCode(code);
                                    } else if(level != null && level.equals(new Integer(2))) {
                                        groupSearch.setThirdStageCode(code);
                                    } else if(level != null && level.equals(new Integer(3))) {
                                        groupSearch.setFourthStageCode(code);
                                    } else if(level != null && level.equals(new Integer(4))) {
                                        groupSearch.setFifthStageCode(code);
                                    }else{

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
            }
        }
        //设置主产品属性过滤查询条件->end
        SpaceCommon spaceCommon = null;
        if( spaceCommonId != null ){
            spaceCommon = spaceCommonService.get(spaceCommonId);
        }
        if( spaceCommon != null && spaceCommon.getSpaceAreas() !=null ){
            groupSearch.setSpaceCommonArea(Utils.getIntValue(spaceCommon.getSpaceAreas()));
        }

		/*获取该用户绑定的序列号品牌*/
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
//        String [] brandIdList = null;
//        if(StringUtils.isNotBlank(brandIds)){
//            brandIdList=brandIds.split(",");
//        }

		//去掉序列号的逻辑改从用户关联的企业取品牌信息
		String brandIds = "";
		String productVisibilityRange = "";
		List<Integer> brandIdsList = new ArrayList<>();
        List<Integer> miniProgramBrandIdsList = new ArrayList<>();
//		List<SysDictionaryBo> sysDictionaryBoList = new ArrayList<>();
		List<String> visibilityRangeCodeList = new ArrayList<>();
		SysUser sysUser = sysUserService.get(loginUser.getId());
		Integer companyId = sysUser.getBusinessAdministrationId();
        logger.error("Begin getBaseCompanyMiniProgramConfigByAppId");
		if(StringUtils.isNotEmpty(groupSearch.getAppId())) {
            BaseCompanyMiniProgramConfig config = baseCompanyService.getBaseCompanyMiniProgramConfigByAppId(groupSearch.getAppId());
            logger.error("getBaseCompanyMiniProgramConfigByAppId==>" + config.toString());
            if(config != null) {
                companyId = config.getCompanyId();
                String enableBrandIdStrs = config.getEnableBrands();
                if(StringUtils.isNotEmpty(enableBrandIdStrs)) {
                    if (StringUtils.isNotEmpty(enableBrandIdStrs)) {
                        String[] array = enableBrandIdStrs.trim().split(",");
                        for (String idStr : array) {
                            if (StringUtils.isNotEmpty(idStr) && NumberUtils.isDigits(idStr)) {
                                miniProgramBrandIdsList.add(Integer.valueOf(idStr));
                            }
                        }
                    }
                }
            }

        }
		if(null != companyId && companyId > 0) {
			BaseCompany baseCompany = baseCompanyService.get(companyId);
			if(null != baseCompany) {
//				brandIds = baseCompany.getBrandIds();
				if(3 == sysUser.getUserType().intValue()) {//经销商
					brandIds = baseCompany.getBrandIds();
				}else if(2 == sysUser.getUserType()){//厂商
					brandIds = baseBrandService.getBrandIdsStrByCompanyId(companyId);
				}else {//其他的都没有品牌
					
				}
                if(null != brandIds && !"".equals(brandIds)) {
                    brandIdsList = Utils.getIntegerListFromStringList(brandIds);
                }else {
                    logger.error("因为去掉序列号的逻辑，企业表中的“经销商所属品牌ids”是空");
                }
                if (StringUtils.isNotEmpty(groupSearch.getPlatFormCode())
                        && PLATFORM_CODE_MINI_PROGRAM.equalsIgnoreCase(groupSearch.getPlatFormCode())
                        && !"C0000071".equalsIgnoreCase(baseCompany.getCompanyCode())
                        ){
                    logger.error("小程序");
                    if(StringUtils.isNotEmpty(baseCompany.getBrandIds())) {
                        brandIds = baseCompany.getBrandIds();//经销商
                    } else {
                        brandIds = baseBrandService.getBrandIdsStrByCompanyId(companyId);//厂商
                    }
                    if(miniProgramBrandIdsList.size() > 0) {
                        brandIdsList = miniProgramBrandIdsList;
                    }else{
                        if(null != brandIds && !"".equals(brandIds)) {
                            brandIdsList = Utils.getIntegerListFromStringList(brandIds);
                        }
                    }
                    logger.error("searchProductGroupV2==>brandIdsList" + brandIdsList.toString());
                }
				productVisibilityRange = baseCompany.getProductVisibilityRange();

				if(null != productVisibilityRange && !"".equals(productVisibilityRange)) {
					//将可见产品范围转成list
					List<Integer> visibilityRangeIdList = Utils.getIntegerListFromStringList(productVisibilityRange);
					/*//根据可见范围查询到所有可见的分类编码
					visibilityRangeCodeList = proCategoryService.getCodeListByIdList(visibilityRangeIdList);
					//根据所有可见范围的code获得数据字典的大小类list
					sysDictionaryBoList = sysDictionaryService.getListByValueKeys(visibilityRangeCodeList);*/
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
        List<SearchProductGroupResult> resultList = new ArrayList<>();

        boolean isStructure = false;
        if ( structureId != null && structureId > 0 ) {
            isStructure = true;
        }

        try {
        	// 重构by huangsongbo 2017.12.8,加入授权码新逻辑 ->start
			if (isStructure) {
				total = groupProductService.getGroupCountByStructureIdAndStatus(structureId, usertype, null, null, 0);
			}else {
				this.groupProductServiceV2.configSearchFor(groupSearch, productTypeValue, smallTypeValue);
				// 设置授权码查询条件
//				this.setAuthorizedConfigSearchParam(productTypeValue, smallTypeValue, groupSearch, loginUser);
				//去掉序列号逻辑
				this.setProductVisibilityRangeParam(productTypeValue, smallTypeValue, groupSearch
						, usertype, brandIdsList, companyId ,visibilityRangeCodeList);
				total = groupProductService.getGroupCountByProduct(groupSearch);
			}
			// 重构by huangsongbo 2017.12.8,加入授权码新逻辑 ->end

            // 优化第一步
            if (total > 0) { // 获取组基本信息，包括 res_file, res_pic
                // 返回了一些基本产品组信息，包括了是否已经收藏、资源图片
                List<SearchProductGroupResult> groupResultList = null;
                logger.info("查询条件json="+ JSON.toJSONString(groupSearch));
                if (isStructure) {
					/*加入只查询普通组合的逻辑*/
                    groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(), structureId,usertype,null);
                } else {
                    groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(), 0,usertype,null);
                }
                if (groupResultList != null && groupResultList.size() > 0) {
                    // 处理 组装产品组中的产品列表
                    Map<Integer, List<SearchProductGroupDetail>> detailListMap = this.groupProductServiceV2.getGroupProductDetailsByGroupIDList(groupResultList,
                            mediaType, brandIds, spaceCommonId);
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
        }catch (Exception e){
            logger.error("searchProductGroupV2 : " + e );
            e.printStackTrace();
        }

        return new ResponseEnvelope<SearchProductGroupResult>(total, resultList, productCategoryRel.getMsgId());
    }

    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public ResponseEnvelope findSameTypeProductList(Integer productId, Integer designPlanId, Integer planProductId,
                                          Integer spaceCommonId, String msgId,LoginUser loginUser) {
        int total = 0;
        List<BaseProduct> list = new ArrayList<>();
        //缓存开关
        BaseProduct bp = null;
        Integer id = productId;

		/*取id为productId的产品*/
        BaseProduct baseProductNew = baseProductService.get(productId);
        if(baseProductNew==null)
            return new ResponseEnvelope<>(false, "产品未找到[id:"+productId+"]", msgId);

		/*取id为productId的产品->end*/
        DesignPlanProduct planProduct = designPlanProductService.get(planProductId);
        if(planProduct!=null && planProduct.getModelProductId()!=null && planProduct.getModelProductId()>0){
            id = planProduct.getModelProductId();
        }
        bp = baseProductService.get(id);
        if( bp == null )
            return new ResponseEnvelope<BaseProduct>(total, list, msgId);


		/*返回可选材质信息*/
        Integer isSplit = new Integer(0);
        List<SplitTextureDTO> splitTexturesInfo=new ArrayList<SplitTextureDTO>();

        //判断
        if(bp != null){
            if(StringUtils.isNotBlank(baseProductNew.getSplitTexturesInfo())){

                //获取材质信息
                Map<String,Object> dealWithMap =  modelAreaRelService.dealWithSplitTextureInfo(baseProductNew.getId(),null,"info","1");
                isSplit = (Integer)dealWithMap.get("isSplit");
                splitTexturesInfo = (List<SplitTextureDTO>)dealWithMap.get("splitTexturesChooseList");

            }else{

                isSplit=new Integer(0);
                if(baseProductNew.getParentId() != null && !baseProductNew.getParentId().equals(new Integer(0))){
                    //媒介类型.如果没有值，则表示为web前端（2）
                    //String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
                    BaseProduct products = new BaseProduct();
                    //products.setParentId(bp.getParentId());
                    products.setParentId(baseProductNew.getParentId());
                    if(loginUser.getUserType()==1){
                        products.setIsInternalUser("yes");
                    }
                    //如果是平吊天花查询同类型新增和贴图产品
                    SysDictionary bigSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(bp.getProductTypeValue()));
                    SysDictionary smallSysDic = null;
                    if( bigSysDic != null ){
                        smallSysDic = sysDictionaryService.getSysDictionaryByValue(bigSysDic.getValuekey(), Integer.valueOf(bp.getProductSmallTypeValue()));
                    }
                    if( smallSysDic != null && "pdtianh".equals(smallSysDic.getValuekey())){
                        products.setProductTypeValue(bp.getProductTypeValue());
                        products.setProductSmallTypeValue(smallSysDic.getValue());
                    }
                    products.setOrders(" id="+productId+" DESC");
                    total = baseProductService.getSameTypeProductCount(products);
                    if(total > 1){
                        try{
                            list = baseProductService.getSameTypeProductList(products);

                            SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
                            List<SplitTextureDTO.ResTextureDTO> resTextureDTOList=new ArrayList<SplitTextureDTO.ResTextureDTO>();
                            for(BaseProduct baseProduct:list){
                                String materalIds=baseProduct.getMaterialPicIds();
                                List<String> materIdStrList=Utils.getListFromStr(materalIds);
                                if(materIdStrList!=null&&materIdStrList.size()>0){
                                    ResTexture resTexture = null;
                                    //解决已经被删除的材质也能查出来问题 add by yanghz
                                    for(String temId : materIdStrList){
                                        resTexture = resTextureService.get(Integer.valueOf(temId));
                                        if(resTexture != null){
                                            break;
                                        }else{
                                            continue;
                                        }
                                    }
                                    if(resTexture!=null){
                                        SplitTextureDTO.ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
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
                            return new ResponseEnvelope<BaseProduct>(false, "数据异常!",msgId);
                        }
                    }else{
                        total = 0;
                    }
                }
            }
        }


		/**--------返回可选材质信息--------*/
        FindSameTypeProductListDTO result = new FindSameTypeProductListDTO(isSplit, splitTexturesInfo, null);

        return new ResponseEnvelope<FindSameTypeProductListDTO>(result, msgId, true);
    }

    @Override
    public Object searchStructureProduct(Integer planProductId, Integer spaceCommonId, Integer designPlanId, String msgId, Integer start, Integer limit, LoginUser loginUser, String mediaType, Integer isStandard, Integer styleId, String regionMark, String measureCode) {

		/*参数验证*/
        List<SearchStructureProductResult> list=new ArrayList<SearchStructureProductResult>();
        if(planProductId==null || planProductId < 1){
			/*返回空列表*/
            return new ResponseEnvelope<>(new Integer(0), list, msgId);
        }
        if(start==null)
            start=-1;
        if(limit==null)
            limit=-1;
		/*参数验证->end*/
        DesignPlanProduct designPlanProduct=null;
        if(Utils.enableRedisCache())
            designPlanProduct= DesignPlanProductCacher.get(planProductId);
        else
            designPlanProduct = designPlanProductService.get(planProductId);
        if(designPlanProduct==null){
            return new ResponseEnvelope<>(false, "设计方案产品未找到:designPlanProductId:"+planProductId, msgId);
        }
        Integer groupType=designPlanProduct.getGroupType();
        Integer groupId=designPlanProduct.getProductGroupId();
        String structureGroupFlag="";
        Integer templetId=null;
        StructureProductSearch structureSearch = new StructureProductSearch();

        StructureProduct structureProduct = null;
        DesignPlan designPlan = null;
        if(Utils.enableRedisCache()) {
            designPlan = DesignPlanCacher.getDesignPlan(designPlanId);
        } else {
            designPlan = designPlanService.get(designPlanId);
        }

        // 得到点击的结构的信息/设置布局标识查询条件 ->start
        // 如果是主s地面结构,则带入布局标识查询条件(从样板房表中取地面布局标识)
        if(groupType != null && 1 == groupType.intValue()) {
            structureProduct = structureProductService.get(groupId);
            if(structureProduct != null) {
                if(StringUtils.equals("10", designPlanProduct.getRegionMark().trim()) && StringUtils.equals("DJ", structureProduct.getStructureType())) {
                    if(designPlan != null) {
                        Integer designTempletId = designPlan.getDesignTemplateId();
                        if(designTempletId != null) {
                            DesignTemplet designTemplet = designTempletService.get(designTempletId);
                            if(designTemplet != null) {
								/*String structureGroundIdentify = null;*/
								/*if(designTemplet.getGroundIdentify() != null && designTemplet.getGroundIdentify().intValue() != 0) {*/
								/*if(designTemplet.getGroundIdentify() != null && !StringUtils.equals("0", designTemplet.getGroundIdentify())) {
									structureGroundIdentify = designTemplet.getGroundIdentify();
								}
								structureSearch.setStructureGroundIdentify(structureGroundIdentify);*/
                                structureSearch.setIdentifyList(Utils.getIdentifyList(designTemplet.getGroundIdentify()));
                            }
                        }
                    }
                }
            }
        }
        // 得到点击的结构的信息/设置布局标识查询条件 ->end

        if (new Integer(1).equals(isStandard)) {
            structureSearch.setIsDeleted(0);
            structureSearch.setIsCommon(1);
//			structureSearch.setStyleId(styleId);
            structureSearch.setMeasureCode(measureCode);
            structureSearch.setStart(start);
            structureSearch.setLimit(limit);

			/*初始化结构是定制结构，搜索通用结构需带上这个结构*/
            if (designPlan != null && designPlan.getDesignTemplateId() != null && designPlan.getDesignTemplateId() > 0) {
                structureSearch.setTempletId(designPlan.getDesignTemplateId());
            }
            structureSearch.setId(groupId);
        }else if(new Integer(0).equals(groupType)){
			/*识别为组合*/
            GroupProduct groupProduct=null;
            if(Utils.enableRedisCache())
                groupProduct= GroupProductCacher.get(groupId);
            else
                groupProduct=groupProductService.get(groupId);
            if(groupProduct==null){
                logger.info("未找到组数据:groupId:"+groupId);
                return new ResponseEnvelope<>(new Integer(0), list, msgId);
            }
            structureGroupFlag=groupProduct.getGroupFlag();
            Integer structureId=groupProduct.getStructureId();
            if(structureId!=null&&structureId>0){
                structureProduct = structureProductService.get(structureId);
                if(structureProduct==null){
                    logger.info("------结构未找到:id:"+structureId);
                }else{
                    templetId=structureProduct.getTempletId();
                }
            }
        }else if(new Integer(1).equals(groupType)){
			/*识别为结构*/
            if(structureProduct==null){
                logger.info("未找到结构数据:structureId:"+groupId);
                return new ResponseEnvelope<>(new Integer(0), list, msgId);
            }
            structureGroupFlag=structureProduct.getGroupFlag();
            templetId=structureProduct.getTempletId();
        }
        if(new Integer(0).equals(isStandard) && StringUtils.isBlank(structureGroupFlag)){
            logger.info("结构组标记为空值,所以返回空集合");
            return new ResponseEnvelope<>(new Integer(0), list, msgId);
        }
        //更具用户类型查找不同状态的产品结构
        List<Integer> statusList = new ArrayList<Integer>();
        if(loginUser.getUserType() == null ){
            logger.error("用户类型  userType 字段为空！");
        }else{
            if(UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType())){//内部用户
                statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
                statusList.add(StructureProductStatusCode.TESTING);
                statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
            }else if(UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType() || UserTypeCode.USER_TYPE_OUTER_B2C == loginUser.getUserType()){//外部用户
                statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
            }else{
//				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
//				statusList.add(StructureProductStatusCode.TESTING);
                statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
            }
        }
        int total = 0;
        if (new Integer(1).equals(isStandard)) {
            structureSearch.setStatusList(statusList);
            total = structureProductMapper.getCommonStructureCount(structureSearch);
        }else{
            total = structureProductService.getCountByGroupFlag(structureGroupFlag,statusList,start,limit);
        }
        if (total > 0) {
            List<StructureProduct> structureProducts = null;
            if (new Integer(1).equals(isStandard)) {
                structureProducts = structureProductMapper.getCommonStructureList(structureSearch);
            }else{
                structureProducts = structureProductService.findAllByGroupFlag(structureGroupFlag,statusList,templetId,start,limit);
            }
            if (structureProducts != null && structureProducts.size() > 0) {
                for(StructureProduct structureProductItem:structureProducts){
                    SearchStructureProductResult searchStructureProductResult = structureProductService.getSearchResult(structureProductItem, spaceCommonId,mediaType, planProductId);
                    list.add(searchStructureProductResult);
                }
            }
        }

        return new ResponseEnvelope<>(total, list, msgId);
    }

    @Override
    public Object getSeriesList(String msgId, LoginUser loginUser) {
    	
    	//去掉序列号的逻辑改从用户关联的企业取品牌信息
		String brandIds = "";
		String productVisibilityRange = "";
		List<Integer> brandIdsList = new ArrayList<>();
		List<String> visibilityRangeCodeList = new ArrayList<>();
		SysUser sysUser = sysUserService.get(loginUser.getId());
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
		List<BaseSeries> brandSeriesIdList = new ArrayList<>();
		if(visibilityRangeCodeList.contains("cuki") || visibilityRangeCodeList.contains("dgki")) {
			brandSeriesIdList = baseSeriesService.getbrandSeriesIdListByBrandIdList(brandIdsList);
		}

//        logger.info(CLASS_LOG_PREFIX + "获取用户授权码品牌系列userId = " + loginUser.getId());
//        //可能一个用户绑定多个品牌
//        List<BaseSeries> brandSeriesIdList = 
//        		baseSeriesService.getSeriesByUserAuthorizedBrandCode(loginUser.getId());
//        logger.info(CLASS_LOG_PREFIX + "获取用户授权码品牌系列完成brandSeriesIdList = " + gson.toJson(brandSeriesIdList));
        int total = 0;
        List<BaseSeries> seriesList = null;
        //1、序列号绑定橱柜品牌查询该品牌系列 2、序列号绑定非橱柜品牌查询所有品牌系列
        if (Lists.isNotEmpty(brandSeriesIdList)) {
            List<Integer> idsList = new ArrayList<>();
            for (BaseSeries baseSeries : brandSeriesIdList) {
                idsList.add(baseSeries.getId());
            }
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量参数idsList = " + gson.toJson(idsList));
            total = baseSeriesService.getSeriesCount(idsList,null);
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量完成total = " + total);
            if (total > 0) {
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列参数idsList = " + gson.toJson(idsList));
                seriesList = baseSeriesService.getSeriesList(idsList,null);
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量seriesList = " + gson.toJson(seriesList));
            }
        } else {
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列参数level = " + BaseSeries.LEVEL_THREE);
            total = baseSeriesService.getSeriesCount(null,BaseSeries.LEVEL_THREE);
            logger.info(CLASS_LOG_PREFIX + "获取品牌系列数量完成total = " + total);
            if (total > 0) {
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列列表参数level = " + BaseSeries.LEVEL_THREE);
                seriesList = baseSeriesService.getSeriesList(null,BaseSeries.LEVEL_THREE);
                logger.info(CLASS_LOG_PREFIX + "获取品牌系列列表完成seriesList = " + gson.toJson(seriesList));
            }
        }
        List<BaseSeriesVO> resultList = new ArrayList<>();
        for (BaseSeries baseSeries : seriesList) {
            BaseSeriesVO baseSeriesVO = BaseSeriesObject.parseToBaseSeriesVOByBaseServies(baseSeries);
            //处理缩略图[sql不好处理格式(web:id,ipad:id;)]
            if (StringUtils.isNotEmpty(baseSeries.getSmallPicInfo())) {
                Map<String,String> map = Utils.readFileDesc(baseSeries.getSmallPicInfo());
                if (StringUtils.isNotBlank(map.get("web"))) {
                    ResPic resPic = resPicService.get(Integer.parseInt(map.get("web")));
                    baseSeriesVO.setPicSmallPath(null == resPic?"":resPic.getPicPath());
                }
            }
            resultList.add(baseSeriesVO);
        }
        logger.info(CLASS_LOG_PREFIX + "获取品牌系列列表resultList = " + gson.toJson(resultList));

        return new ResponseEnvelope<>(total,resultList,msgId);
    }

    /**
     * 创建数据字典
     * @param houseType
     * @return
     */
    private List<String> createHouseTypeList(String houseType) {

        List<String> productHouseTypeList = null;
        if ( StringUtils.isNotBlank(houseType) ) {
			/*对应出productHouseType的数据字典的value*/
            SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
            sysDictionarySearch.setAtt1(houseType);
            sysDictionarySearch.setType("productHouseType");
            List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
            productHouseTypeList = new ArrayList<String>();//过滤条件

            if ( sysDictionaryList != null && sysDictionaryList.size()>0 ) {
                for (SysDictionary sysDictionary:sysDictionaryList) {
                    productHouseTypeList.add("" + sysDictionary.getValue() );
                }
            }
        }
        return productHouseTypeList;

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
//						this.getSelectCondition(companyId,checkCategory, brandIdsList,sysDictionaryBoList);
				// baseProductService.getSelectConditionByAuthorizedConfigV3(loginUser, sysDictionarySmallType.getType());
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
//				if(null != sysDictionaryBoList && sysDictionaryBoList.size() > 0) {
//		    		for(SysDictionaryBo bo : sysDictionaryBoList) {
//		    			logger.error("bo.getBigTypeValue()="+bo.getBigTypeValue()+",   bo.getSmallTypeValue()"
//		    				+ bo.getSmallTypeValue() +",smallTypeValue = " + smallTypeValue);
//		    			if(Integer.valueOf(smallTypeValue) == bo.getSmallTypeValue()) {
//		    				groupSearch.setBrandIds(brandIdsList);
//		    				continue;
//		    			}
//		    		}
//		    	}
				logger.error("visibilityRangeCodeList = " + visibilityRangeCodeList + ",checkCategory = " +checkCategory);
				logger.error("visibilityRangeCodeList.contains(checkCategory) = " + visibilityRangeCodeList.contains(checkCategory));
                if(visibilityRangeCodeList.contains(checkCategory)) {
                    groupSearch.setBrandIds(brandIdsList);
                }
			}else {
			    logger.error("sysDictionarySmallType是空：" + sysDictionarySmallType);
                groupSearch.setBrandIds(brandIdsList);
            }
		}

	}
    
    private List<BaseProduct> getSelectCondition(Integer companyId,String checkCategory,
    		List<Integer> brandIdsList,List<SysDictionaryBo> sysDictionaryBoList){
    	
    	List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
    	BaseProduct baseProduct = new BaseProduct();
    	if(null != sysDictionaryBoList && sysDictionaryBoList.size() > 0) {
    		for(SysDictionaryBo bo : sysDictionaryBoList) {
    			logger.error("bo.getBigTypeValue()="+bo.getBigTypeValue()+",   bo.getSmallTypeValue()"+bo.getSmallTypeValue());
    		}
    	}
    	for(Integer id : brandIdsList){
    		
    		logger.error("品牌id = "+id);
    	}
    	//根据公司id获取对应过滤大小类配置
    	Map<String, List<Integer>> map = baseCompanyService.getCategoryListV2(companyId);
    	logger.error("checkCategory = "+checkCategory+",companyId = "+companyId+"------->map.get(checkCategory) = " +map.get(checkCategory));
		if(!map.containsKey(checkCategory)) {
			logger.error("根据公司id获取对应授权码过滤大小类配置      获取到的map里面没有"+checkCategory+"这个小分类的数据");
			return baseProductList;
		}
    	
		/*查询条件注入到BaseProduct类中*/
		List<BaseBrand> baseBrandList = baseBrandService.getListByIds(brandIdsList);
		
		if(null != baseBrandList && baseBrandList.size() > 0) {
			for(BaseBrand brand : baseBrandList) {
				baseProduct.setSmallTypeValueListForShowAll(map.get(checkCategory));
				baseProduct.setStatusShowWu(brand.getStatusShowWu());
				baseProduct.setBrandId(brand.getId());
				baseProductList.add(baseProduct);
			}
		}
		
    	return baseProductList;
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
					if(ProductModelConstant.STATUSSHOWWU_NOSHOW.intValue() == baseProduct.getStatusShowWu()
							&& baseProduct.getStatusShowWu() != null) {
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

	@Override
	public Integer getPracticableProductId(Integer productId, Integer designPlanProductId) throws BizException {
		String exceptionLogPrefix = "获取产品信息失败";
		
		// 参数验证 ->start
		if(productId == null) {
			String log = "params error: productId = null";
			logger.error(LOGPREFIX + log);
			throw new BizException(exceptionLogPrefix + "(" + log + ")");
		}
		if(designPlanProductId == null) {
			String log = "params error: designPlanProductId = null";
			logger.error(LOGPREFIX + log);
			throw new BizException(exceptionLogPrefix + "(" + log + ")");
		}
		
		DesignPlanProduct designPlanProduct = designPlanProductService.get(designPlanProductId);
		if(designPlanProduct == null) {
			String log = "DesignPlanProduct not found; designPlanProductId = " + designPlanProductId;
			logger.error(LOGPREFIX + log);
			throw new BizException(exceptionLogPrefix + "(" + log + ")");
		}
		
		Integer baimoProductId = designPlanProduct.getInitProductId();
		BaseProduct baimoBaseProduct = baseProductService.get(baimoProductId);
		if(baimoBaseProduct == null) {
			String log = "BaseProduct not found; baseProductId = " + baimoProductId;
			logger.error(LOGPREFIX + log);
			throw new BizException(exceptionLogPrefix + "(" + log + ")");
		}
		
		String fullPaveLengthStr = baimoBaseProduct.getFullPaveLength();
		if(StringUtils.isEmpty(fullPaveLengthStr)) {
			String log = "fullPaveLengthStr is null; proudctId = " + baimoProductId;
			logger.error(LOGPREFIX + log);
			throw new BizException(exceptionLogPrefix + "(" + log + ")");
		}
		Integer fullPaveLength = null;
		try {
			fullPaveLength = Integer.valueOf(fullPaveLengthStr);
		}catch (Exception e) {
			String log = "String tranfer Integer 错误; str = " + fullPaveLengthStr;
			logger.error(LOGPREFIX + log);
			throw new BizException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		return baseProductService.getPracticableProductInfo(productId, fullPaveLength);
	}
	
	@Override
	public Integer getPracticableProductIdByProductIdAndFullPaveLength(Integer productId, Integer fullPaveLength) throws BizException {
		return baseProductService.getPracticableProductInfo(productId, fullPaveLength);
	}
	
}
