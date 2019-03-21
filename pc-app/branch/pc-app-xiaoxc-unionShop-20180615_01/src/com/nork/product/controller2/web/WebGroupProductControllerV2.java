/**
 * 
 */
package com.nork.product.controller2.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.controller.BaseController;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.GroupProductServiceV2;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductAttributeService;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.SysDictionaryService;

/**
 * 产品组 另一个 controller
 * @author louxinhua
 *
 */
@Controller
/*@RequestMapping("/{style}/web/product/groupProductV2")*/
public class WebGroupProductControllerV2 extends BaseController {

	private final JsonDataServiceImpl<ProductCategoryRel> JsonUtil = new JsonDataServiceImpl<ProductCategoryRel>();
	private final String STYLE="jsp";
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/product/productCategoryRel";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/productCategoryRel";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/productCategoryRel";
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private SysDictionaryService sysDictionaryService; // 系统数据字典组合
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

	/**
	 * 组合收藏状态
	 * @author louxinhua
	 *
	 */
	public enum GROUP_SEARCH_TYPE {
		default_0,  		// 默认
		structure_group,	// 结构的组合	
		normal_group		// 普通的产品组合
	}
	
	
	/**
	 * 查询产品组合列表 重构
	 * @author louxinhua 菜刀楼
	 */
	@RequestMapping(value="/searchProductGroupV2")
	@ResponseBody
	public Object searchProductGroupV2(HttpServletRequest request, @ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel,
									 @RequestParam(value = "houseType",required = false)String houseType,
									 @RequestParam(value = "designPlanId",required = false)Integer designPlanId,
									 @RequestParam(value = "planProductId",required = false)Integer planProductId,
									 @RequestParam(value = "spaceCommonId",required = false)Integer spaceCommonId,
									 @RequestParam(value = "templateProductId",required = false)String templateProductId,
									 @RequestParam(value = "productTypeValue",required = false)String  productTypeValue,
									 @RequestParam(value = "smallTypeValue",required = false)String  smallTypeValue,
									 @RequestParam(value = "queryType",required = false)String queryType,
									 @RequestParam(value = "groupType",required = false)String groupType,
									 @RequestParam(value = "type",required = false)String type,
									 @RequestParam(value = "structureId",required = false)Integer structureId,
									 @RequestParam(value = "designTempletId",required = false)Integer designTempletId,
									 @RequestParam(value = "userStatusType",required = false)Integer userStatusType
			){
		// 初始化
		GroupProductSearch groupSearch = this.initGroupProductSearch(request, productCategoryRel, userStatusType);
		if ( groupSearch == null ) {
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空",productCategoryRel.getMsgId());
		}
		else {}

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

		//媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		// 内部用户可以查询测试中的数据
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
//			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//			loginUser.setUserType(3);
//			loginUser.setMediaType("3");
		}
		if(loginUser==null){
			return new ResponseEnvelope<>(false, "请重新登录", productCategoryRel.getMsgId());
		}
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
		Integer usertype = loginUser.getUserType();
//		Integer status = 1; //如果usertype == 1 那么该用户为内部用户， 那么可以看到测试数据和上架数据，status=3  则能看到
//		if(1==usertype){
//			status=3;
//		}
		
		// 拼装系统数据字典
		List<String> productHouseTypeList = this.createHouseTypeList(houseType);
		if ( productHouseTypeList != null && productHouseTypeList.size()>0 ) {
			groupSearch.setHouseTypeList(productHouseTypeList);
		}
		else {}
		
		//设置主产品属性过滤查询条件
		if(planProductId!=null&&planProductId>0){
			DesignPlanProduct designPlanProduct = designPlanProductService.get(planProductId);
			if ( designPlanProduct!=null ) {
				productAttributeService.setProductAttributeQueryCondition(groupSearch,designPlanProduct.getProductId());
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
		String [] brandIdList = null;
		if(StringUtils.isNotBlank(brandIds)){
			brandIdList=brandIds.split(",");
		}
		
		int total = 0;
		int byBrandIdsTotal = 0;
		List<SearchProductGroupResult> resultList = new ArrayList<>();
		
		boolean isStructure = false;
		if ( structureId != null && structureId > 0 ) {
			isStructure = true;
		}
		
		try {
			/*brandIds 新增逻辑，  先通过授权码里的品牌查询有没有组合，如果没有再查所有组合*/
			// 结构的概念， 2016-12-28 added
			if(3==usertype){
				if (isStructure) {
					byBrandIdsTotal = groupProductService.getGroupCountByStructureIdAndStatus(structureId, usertype, brandIdList, versionType, 0);
				}else{
					this.groupProductServiceV2.configSearchFor(groupSearch, productTypeValue, smallTypeValue);
					groupSearch.setBrandIdList(brandIdList);
					byBrandIdsTotal = groupProductService.getGroupCountByProduct(groupSearch);
				}
			}
			if(byBrandIdsTotal<=0){
				groupSearch.setBrandIdList(null);
				if (isStructure) {
					total =   groupProductService.getGroupCountByStructureIdAndStatus(structureId, usertype, null, versionType, 0);
				} else {
					this.groupProductServiceV2.configSearchFor(groupSearch, productTypeValue, smallTypeValue);
					total = groupProductService.getGroupCountByProduct(groupSearch);
				}
			}

			// 优化第一步
			if (total > 0||byBrandIdsTotal>0) { // 获取组基本信息，包括 res_file, res_pic

 

				// 返回了一些基本产品组信息，包括了是否已经收藏、资源图片
				List<SearchProductGroupResult> groupResultList = null;

				
				
				
				
				if (isStructure) {
					groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(), structureId,usertype,versionType);
				} else {
					groupResultList = this.groupProductServiceV2.getGroupListByProduct(groupSearch, loginUser.getId(), 0,usertype,versionType);
				}


				if (groupResultList != null && groupResultList.size() > 0) {
					// 处理 组装产品组中的产品列表
					Map<Integer, List<SearchProductGroupDetail>> detailListMap = this.groupProductServiceV2.getGroupProductDetailsByGroupIDList(groupResultList,
							mediaType, brandIds, spaceCommonId);
					// test->start
					/*List<SearchProductGroupDetail> searchProductGroupDetailList = detailListMap.get(new Integer(1791));
					List<SearchProductGroupDetail> searchProductGroupDetailList2 = detailListMap.get(new Integer(1959));*/
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

							} else {
							}
						}
						resultList = groupResultList;
					} else {
					}

				} else {

				}

			} else {
			}
		}catch (Exception e){
			logger.error("searchProductGroupV2 : " + e );
		}
		
		return new ResponseEnvelope<SearchProductGroupResult>(total, resultList, productCategoryRel.getMsgId());
	}
	
	
	/**
	 * 初始化 groupproductsearch 实体
	 * @param productCategoryRel
	 * @return
	 */
	private GroupProductSearch initGroupProductSearch (HttpServletRequest request, ProductCategoryRel productCategoryRel,
			Integer userStatusType) {
		
		// 产品组合搜索类
		GroupProductSearch groupSearch = new GroupProductSearch();
		
		if ( StringUtils.isBlank(productCategoryRel.getCategoryCode()) ) {
			
		}
		else {}
		groupSearch.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		groupSearch.setLimit(productCategoryRel.getLimit());
		groupSearch.setStart(productCategoryRel.getStart());
		// 内部用户可以查询测试中的数据
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
		if( new Integer(1).equals(loginUser.getUserType()) && "2".equals(versionType)){
			groupSearch.setStates(Arrays.asList(1,2,3));//state=3  add by yanghz  内部用户可以看到测试中，已上架，已发布状态数据
		}else{
			groupSearch.setState(3);//外部用户只能看到已发布数据
		}
		
		
		if(userStatusType != null || "".equals(userStatusType)) {
			if(userStatusType == 1) {
				groupSearch.setStates(Arrays.asList(1,2,3));
			} else {
				groupSearch.setState(3);//外部用户只能看到已发布数据
			}
		}
		
		return groupSearch;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
}
