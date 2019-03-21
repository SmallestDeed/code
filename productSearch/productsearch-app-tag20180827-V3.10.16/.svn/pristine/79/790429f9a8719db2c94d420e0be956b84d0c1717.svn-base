package com.sandu.search.controller;

import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.CommonAppSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.base.exception.BizException;
import com.nork.common.constant.util.SystemCommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.service.DesignPlanProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nork.product.model.search.GroupProductSearch;

/**
 * Created by yangz on 2017/12/12.
 */
@Controller
@RequestMapping("/{style}/web/app/search")
public class SearchForCommonApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchForCommonApp.class);
	
	private static final String LOGPREFIX = "[搜索模块]:";
	
    @Autowired
    private CommonAppSearchService commonAppSearchService;

    /**
     * 通过idList查询产品详情. add by yangzhun 2017-12-28 15:16:45
     * @param idArrs
     * @param limit
     * @param start
     * @param userId
     * @param msgId
     * @param isStandard
     * @param regionMark
     * @param styleId
     * @param measureCode
     * @return
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getDetailByIds")
	@ResponseBody
	public Object getDetailByIds(
			@RequestParam(required = false) String idArrs, 
			Integer limit, 
			Integer start, 
			Integer userId, 
			String msgId, 
			HttpServletRequest request,
			Integer isStandard, 
			String regionMark, 
			Integer styleId, 
			String measureCode,
            Integer designPlanId,Integer planProductId,
            // add by huangsongbo 2018.6.15
            // isSpellingFlowerProduct = 0 or null:默认老逻辑
            // isSpellingFlowerProduct = 1:拼花产品,取材质走拼花材质
            Integer isSpellingFlowerProduct
            ) {
		if("".equals(idArrs) || idArrs == null) {
			return new ResponseEnvelope(false,"必要参数不能为空");
		}
		
		// 参数验证/处理 ->start
		if(isSpellingFlowerProduct == null) {
			isSpellingFlowerProduct = 0;
		}
		// 参数验证/处理 ->end
		
        String mediaType = SystemCommonUtil.getMediaType(request);

		List<String> idStringList = Arrays.asList(idArrs.split(","));
		List<Integer> idIntegerList = new ArrayList<>(idStringList.size());
		idStringList.forEach(idStr -> idIntegerList.add(Integer.parseInt(idStr)));
		
		return commonAppSearchService.getDetailByIds(idIntegerList, limit, start, userId, msgId,
				isStandard, regionMark, styleId, measureCode, designPlanId, planProductId, mediaType, isSpellingFlowerProduct);
	}

    /**
     * 查询单品
     * @param style
     * @param productCategoryRel
     * @param request
     * @param houseType
     * @param designPlanId
     * @param planProductId
     * @param spaceCommonId
     * @param templateProductId
     * @param productTypeValue
     * @param smallTypeValue
     * @param queryType
     * @param productModelOrBrandName
     * @param userStatusType
     * @param isStandard
     * @param regionMark
     * @param styleId
     * @param measureCode
     * @param smallpox
     * @return
     */
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
          @RequestParam(value = "productModelNumber", required = false) String productModelOrBrandName,
          @RequestParam(value = "userStatusType", required = false) Integer userStatusType,
          @RequestParam(value = "isStandard", required = false, defaultValue = "0") Integer isStandard,
          @RequestParam(value = "regionMark", required = false) String regionMark,
          @RequestParam(value = "styleId", required = false) Integer styleId,
          @RequestParam(value = "measureCode", required = false) String measureCode,
          @RequestParam(value = "smallpox", required = false) String smallpox
          //@RequestParam(value = "platformCode", required = false) String platformCode
    ) {
        // 获取配置:决定是否走老接口还是新接口
        // 参数验证 ->start
        if (StringUtils.isBlank(productCategoryRel.getCategoryCode()))
            return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", productCategoryRel.getMsgId());
        // 用户信息
        LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
        if (loginUser == null || loginUser.getId() == null || loginUser.getId() < 1) {
            return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", productCategoryRel.getMsgId());
        }
        String mediaType = SystemCommonUtil.getMediaType(request);
        //String platformCode = request.getHeader("Platform-Code");

        return commonAppSearchService.searchSingleProduct(templateProductId, productCategoryRel,
                loginUser, productModelOrBrandName, planProductId, houseType, productTypeValue, spaceCommonId,
                smallTypeValue, mediaType, userStatusType, isStandard, regionMark, styleId, measureCode,
                smallpox, designPlanId, style);
    }

    /**
     * 查询产品组合列表 重构
     * @author louxinhua 菜刀楼
     */
    @RequestMapping(value="/searchProductGroupV2")
    @ResponseBody
    public Object searchProductGroupV2(HttpServletRequest request,
           @ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel,
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
           @RequestParam(value = "userStatusType",required = false)Integer userStatusType){


        //媒介类型.如果没有值，则表示为web前端（2）
        String mediaType = SystemCommonUtil.getMediaType(request);
        // 内部用户可以查询测试中的数据
        LoginUser loginUser2 = SystemCommonUtil.getCurrentLoginUserInfo(request);
        if (loginUser2 == null) {
            return new ResponseEnvelope<>(false, "请重新登录", productCategoryRel.getMsgId());
        }
        Integer userType = loginUser2.getUserType();
        GroupProductSearch groupSearch = this.initGroupProductSearch(request, productCategoryRel, userStatusType,userType);
        // 初始化
        if ( groupSearch == null ) {
        	return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空",productCategoryRel.getMsgId());
        }
        //组合没有对应的平台表所以按之前的逻辑
//        String platformCode = request.getHeader("Platform-Code");

        return commonAppSearchService.searchProductGroupV2( productCategoryRel,  houseType,  designPlanId,
                planProductId, spaceCommonId,templateProductId, productTypeValue,  smallTypeValue,  queryType,
                groupType, type, structureId, designTempletId, userStatusType,  loginUser2,  groupSearch,  mediaType);

    }



    /**
     * 初始化 groupproductsearch 实体
     * @param productCategoryRel
     * @return
     */
	private GroupProductSearch initGroupProductSearch (HttpServletRequest request,
    		ProductCategoryRel productCategoryRel, Integer userStatusType,Integer userType) {

        // 产品组合搜索类
        GroupProductSearch groupSearch = new GroupProductSearch();
        groupSearch.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
        groupSearch.setLimit(productCategoryRel.getLimit());
        groupSearch.setStart(productCategoryRel.getStart());
        // 内部用户可以查询测试中的数据
        if(userStatusType != null) {
            if(userStatusType == 1) {
                groupSearch.setStates(Arrays.asList(1,2,3));
            } else {
                groupSearch.setState(new Integer(3));//外部用户只能看到已发布数据
            }
        }else {
        	if(userType == 1) {
                groupSearch.setStates(Arrays.asList(1,2,3));
            } else {
                groupSearch.setState(new Integer(3));//外部用户只能看到已发布数据
            }
        }
        return groupSearch;
    }



    /**
     * 同类型产品列表接口
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/findSameTypeProductList")
    @ResponseBody
    public Object findSameTypeProductList(Integer productId,Integer designPlanId, 
    		Integer planProductId,Integer spaceCommonId, String msgId, 
    		HttpServletRequest request, HttpServletResponse response) {

        if( StringUtils.isBlank(msgId) ){
            return new ResponseEnvelope<BaseProduct>(false, "msgId为空!", msgId);
        }
        if( productId == null ){
            return new ResponseEnvelope<BaseProduct>(false, "参数缺少产品productId!", msgId);
        }
        LoginUser loginUser = new LoginUser();
        if (SystemCommonUtil.getCurrentLoginUserInfo(request) == null 
        		|| SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
            loginUser.setId(-1);
            loginUser.setLoginName("nologin");
        } else {
            loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
        }

        ResponseEnvelope envelope = (ResponseEnvelope) commonAppSearchService.findSameTypeProductList( productId, designPlanId,  planProductId
                , spaceCommonId, msgId, loginUser);
        
        return envelope;
    }

    /**
     * 查询结构接口
     * @author huangsongbo
     * @param planProductId
     * @param request
     * @return
     */
    @RequestMapping("/searchStructureProduct")
    @ResponseBody
    public Object searchStructureProduct(
            Integer planProductId, Integer spaceCommonId,
            String msgId,Integer start,Integer designPlanId,
            Integer limit, HttpServletRequest request,Integer isStandard,
            Integer styleId,String regionMark,String measureCode){

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser == null) {
            return new ResponseEnvelope<>(false, "请重新登录", msgId);
        }
        String mediaType = SystemCommonUtil.getMediaType(request);
        return commonAppSearchService.searchStructureProduct(planProductId,spaceCommonId,
                designPlanId,msgId,start,limit,loginUser,mediaType,isStandard,styleId,regionMark,measureCode);

    }

    /**
     * 系列列表接口
     * @param msgId
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getSeriesList")
    @ResponseBody
    public Object getSeriesList(String msgId, HttpServletRequest request){

        //参数验证
        if (StringUtils.isEmpty(msgId)) {
            return new ResponseEnvelope(false,"msgId is null",msgId);
        }
        //用户验证
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser == null) {
            return new ResponseEnvelope(false,"user is null",msgId);
        }
//        LoginUser loginUser = new LoginUser();
//        loginUser.setId(48);

        return commonAppSearchService.getSeriesList(msgId,loginUser);

    }

    /**
     * 传入多模型代表产品id,获取该产品对应的产品组适用的模型(根据全铺长度选择最适用模型)的产品信息
     * 备注:
     * 部分代码copy,所以比较乱
     * 
     * @param productId 待替换的产品id(产品组代表产品)
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/getPracticableProductInfo")
	@ResponseBody
    public ResponseEnvelope<CategoryProductResult> getPracticableProductInfo(
    		
    		// 产品详情接口(getDetailByIds方法)参数 ->start
			Integer userId, 
			String msgId, 
			HttpServletRequest request,
			Integer isStandard, 
			String regionMark, 
			Integer styleId, 
			String measureCode,
            Integer designPlanId,Integer planProductId,
            // add by huangsongbo 2018.6.15
            // isSpellingFlowerProduct = 0 or null:默认老逻辑
            // isSpellingFlowerProduct = 1:拼花产品,取材质走拼花材质
            Integer isSpellingFlowerProduct,
            // 产品详情接口参数(getDetailByIds) ->end
            
            Integer productId
    		) {
    	String exceptionLogPrefix = "获取产品信息失败";
    	
    	// 参数验证 ->start
    	if(productId == null) {
			String log = "params error: productId = null";
			LOGGER.error(LOGPREFIX + log);
			return new ResponseEnvelope<>(false, exceptionLogPrefix + "(" + log + ")", msgId);
		}
		if(planProductId == null) {
			String log = "params error: planProductId = null";
			LOGGER.error(LOGPREFIX + log);
			return new ResponseEnvelope<>(false, exceptionLogPrefix + "(" + log + ")", msgId);
		}
		if(isSpellingFlowerProduct == null) {
			isSpellingFlowerProduct = 0;
		}
    	// 参数验证 ->end
    	
    	Integer productIdPracticable;
		try {
			productIdPracticable = commonAppSearchService.getPracticableProductId(productId, planProductId);
		} catch (BizException e) {
			return new ResponseEnvelope<>(false, e.getMessage(), msgId);
		}
    	
    	if(productIdPracticable == null) {
    		/*String log = "非多模型产品; productId = " + productId;
    		LOGGER.error(LOGPREFIX + log);
    		return new ResponseEnvelope<>(false, exceptionLogPrefix + "(" + log + ")", msgId);*/
    		// 修改为,如果没查询出适配产品,则应用原产品
    		productIdPracticable = productId;
    	}
    	
    	String mediaType = SystemCommonUtil.getMediaType(request);
    	
    	List<Integer> idList = new ArrayList<Integer>(1);
    	idList.add(productIdPracticable);
    	
    	return commonAppSearchService.getDetailByIds(idList, 0, 1, userId, msgId,
				isStandard, regionMark, styleId, measureCode, designPlanId, planProductId, mediaType, isSpellingFlowerProduct);
    }

    /**
     *
     * @param productId
     * @param designPlanProductId
     * @return
     */
    @RequestMapping(value = "/getPracticableProductId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope<Integer> getPracticableProductId(Integer productId, Integer designPlanProductId){
        if (productId == null){
            return new ResponseEnvelope<>(false, "产品ID不能为空");
        }
        if (designPlanProductId == null){
            return new ResponseEnvelope<>(false, "方案产品ID不能为空");
        }
        try {
            Integer id = commonAppSearchService.getPracticableProductId(productId, designPlanProductId);
            return new ResponseEnvelope<>(true,id);
        } catch (BizException e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        }
    }
}
