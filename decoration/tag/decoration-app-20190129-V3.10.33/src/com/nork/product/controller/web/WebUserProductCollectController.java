package com.nork.product.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.properties.AppProperties;
import com.nork.common.properties.ResProperties;
import com.nork.product.model.*;
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
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.UserProductPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.cache.UserProductCollectCacher;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.model.small.UserProductCollectSmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.UserProductCollectService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

/**
 * Created by lizf on 2015/7/14.
 */
@Controller
@RequestMapping("/{style}/web/product/userProductCollect")
public class WebUserProductCollectController {
	private static Logger logger = Logger.getLogger(WebUserProductCollectController.class);
	private final JsonDataServiceImpl<UserProductCollect> JsonUtil = new JsonDataServiceImpl<UserProductCollect>();
	private final String STYLE="online";
	private final String JSPSTYLE="online";
	private final String MAIN = "/"+ STYLE + "/product/userProductCollect";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/userProductCollect";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/userProductCollect";

	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private UserProductCollectService userProductCollectService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductAttributeService productAttributeService;

	@RequestMapping(value = "/jsplist")
	@ResponseBody
	public Object jsplist(
	            @ModelAttribute("userProductCollectSearch") UserProductCollectSearch userProductCollectSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UserProductCollect> list = new ArrayList<UserProductCollect> ();
		int total = 0;
		try {
			total = userProductCollectService.getCount(userProductCollectSearch);
            logger.info("total:" + total);

			if (total > 0) {
				list = userProductCollectService.getPaginatedList(
						userProductCollectSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductCollect>(false, "数据异常!");
		}

		UserProductsConllect upc = new UserProductsConllect();
		//upc.setUserName(userProductCollectSearch.getUserName());
		//upc =userProductCollectService.getuserProductsConllect(upc);

		String productTypeValue = request.getParameter("productTypeValue");
		productTypeValue = (productTypeValue==null||"undefined".equals(productTypeValue))?"":productTypeValue;
		String brandIdStr = (request.getParameter("brandId")==null||"undefined".equals(request.getParameter("brandId")))?"":request.getParameter("brandId");
		if( StringUtils.isNotBlank(brandIdStr)){
			Integer brandId = Integer.parseInt(brandIdStr);
			upc.setBrandId(brandId);
		}
		upc.setProductTypeValue(productTypeValue);

		LoginUser loginUser = new LoginUser();

		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			upc.setUserId(loginUser.getId());
		}


		List<UserProductsConllect> upcList = new ArrayList<UserProductsConllect>();
		upcList = userProductCollectService.getUserProductsConllectList(upc);
		for (UserProductsConllect userProductsConllect : upcList ) {
			BaseProduct baseProduct = baseProductService.get(Integer.valueOf(userProductsConllect.getProductId()));
			if( StringUtils.isNotBlank(baseProduct.getProductTypeValue()) && baseProduct.getProductSmallTypeValue() != null ){
				SysDictionary productTypeDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(baseProduct.getProductTypeValue()));
				userProductsConllect.setProductTypeKey(productTypeDic.getValuekey());
				userProductsConllect.setProductTypeName(productTypeDic.getName());
				userProductsConllect.setProductTypeValue(productTypeDic.getValue().toString());
				SysDictionary productSmallTypeDic = sysDictionaryService.getSysDictionaryByValue(productTypeDic.getValuekey(),baseProduct.getProductSmallTypeValue());
				userProductsConllect.setProductSmallTypeName(productSmallTypeDic.getName());
				userProductsConllect.setProductSmallTypeValue(productSmallTypeDic.getValue());
				userProductsConllect.setProductSmallTypeCode(productSmallTypeDic.getValuekey());
			}
			//u3dmodelpath
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			String u3dModelPath = baseProductService.getU3dModelId(mediaType,baseProduct);
			if( StringUtils.isNotBlank(u3dModelPath) ){
				ResModel resModel = resModelService.get(Integer.valueOf(u3dModelPath));
				if( resModel != null ) {
					userProductsConllect.setU3dModelPath(resModel.getModelPath());
				}
			}
		}

		String userProductsConllectList = JsonUtil.getListToJsonData(upcList);
		if(("").equals(userProductsConllectList)){
			List<UserProductPlan> smallList = new ArrayList<UserProductPlan>();
			return new ResponseEnvelope<UserProductPlan>(total,smallList);
		}
		 List<UserProductsConllect> smallList= new JsonDataServiceImpl<UserProductsConllect>().getJsonToBeanList(userProductsConllectList, UserProductsConllect.class);
		//List<UserProductsConllect> userList = userProductCollectService.selectUserNamelist(upc);

		ResponseEnvelope<UserProductCollect> res = new ResponseEnvelope<UserProductCollect>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", userProductCollectSearch);

		return new ResponseEnvelope<UserProductsConllect>(total,smallList);
	}

	/**
	 * 通过品牌类型查询产品---list
	 */
	@RequestMapping(value = "/getProductListByBrandType")
	public Object getProductListByBrandType(
			@ModelAttribute("baseBrandSearch") BaseBrandSearch baseBrandSearch,HttpServletRequest request, HttpServletResponse response) {

		List<BaseBrand> list = new ArrayList<BaseBrand> ();
		List<BaseProduct> baseProductlist = new ArrayList<BaseProduct> ();
		List<UserProductsConllect> upcList = new ArrayList<UserProductsConllect>();
		UserProductsConllect userProductsConllect = new UserProductsConllect();
		BaseProductSearch baseProducttSearch = new BaseProductSearch();

		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<BaseBrandSearch>(false, "登录超时，请重新登录!",baseBrandSearch.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userProductsConllect.setUserId(loginUser.getId());
		}



		int total = 0;
		try {
			total = baseBrandService.getCount(baseBrandSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = baseBrandService.getPaginatedList(baseBrandSearch);
				if(CustomerListUtils.isNotEmpty(list)){
					for(int i=0;i<list.size();i++){
						baseProducttSearch.setBrandId(list.get(i).getId());
						baseProductlist = baseProductService.getPaginatedList(baseProducttSearch);
						for (BaseProduct baseProduct : baseProductlist) {
							userProductsConllect.setProductId(baseProduct.getId().toString());
							userProductsConllect.setPicId(baseProduct.getPicId());
							userProductsConllect.setBrandId(baseProduct.getBrandId());
							//userProductCollectService.getUserProductConllect(userProductsConllect);
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseBrand>(false, "数据异常!");
		}

		ResponseEnvelope<BaseBrand> res = new ResponseEnvelope<BaseBrand>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("totalSize", total);
		request.setAttribute("search", baseBrandSearch);

		return  Utils.getPageUrl(request, JSPMAIN + "/baseBrand_list");
	}


	@RequestMapping(value = "/getNamelist")
	@ResponseBody
	public Object getNamelist(
			@ModelAttribute("userProductCollectSearch") UserProductCollectSearch userProductCollectSearch,HttpServletRequest request, HttpServletResponse response) {

		List<BaseBrand> brandNameList = new ArrayList<BaseBrand>();
		brandNameList = baseBrandService.getList(new BaseBrand());
		long total =0;

		request.setAttribute("search", userProductCollectSearch);
		String baseBrandList = JsonUtil.getListToJsonData(brandNameList);
		 List<BaseBrand> smallList= new JsonDataServiceImpl<BaseBrand>().getJsonToBeanList(baseBrandList, BaseBrand.class);
		 return new ResponseEnvelope<BaseBrand>(total,smallList);
	}

	 /**
		 * 删除我的产品收藏,支持批量删除，传递ids=1,2,3格式即可
		 */
		@RequestMapping(value = "/del")
		@ResponseBody
		public Object del(@PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
		                 ,HttpServletRequest request, HttpServletResponse response) {
			String jsonStr = Utils.getJsonStr(request);
			String msgId = "";
			String ids   = "";
			if (jsonStr != null && jsonStr.trim().length() > 0){
				userProductCollect = (UserProductCollect)JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
				if(userProductCollect == null){
				   return new ResponseEnvelope<UserProductCollect>(false, "传参异常!","none");
				}
				ids =  userProductCollect.getIds();
				msgId = userProductCollect.getMsgId() ;
			}else{
				ids =  userProductCollect.getIds();
				msgId = userProductCollect.getMsgId() ;
			}

			if (ids == null) {
			    return new ResponseEnvelope<UserProductCollect>(false, "参数ids不能为空!",msgId);
			}
			int i = 0;
			try{
				if (ids != null ) {
				   if(ids.contains(",")){
						String[] strs = ids.split(",");
						for (String c : strs) {
							Integer id = new Integer(c);
							i = userProductCollectService.delete(id);
							logger.info("delete:id=" + id);
						}
				   }else{
						Integer id = new Integer(ids);
						i = userProductCollectService.delete(id);
						logger.info("delete:id=" + id);
				    }
				}

				if( i == 0){
					return new ResponseEnvelope<UserProductCollect>(false, "记录不存在!",msgId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEnvelope<UserProductCollect>(false, "删除失败!",msgId);
			}
			return new ResponseEnvelope<UserProductCollect>(true,msgId,true);
		}

		/**
		 * 产品收藏删除-----接口
		 * @param style
		 * @param usedProducts
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/delUserProduct")
		@ResponseBody
		public Object delUserProduct(@PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
	            ,HttpServletRequest request) {
			Long startTime = System.currentTimeMillis();
			String jsonStr = Utils.getJsonStr(request);
			if (jsonStr != null && jsonStr.trim().length() > 0) {
				userProductCollect = (UserProductCollect) JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
				if (userProductCollect.getId() == null) {
					return new ResponseEnvelope<UserProductCollect>(false, "传参异常!", "none");
				}
			}
			String msg = "";
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				msg = "登录超时，请重新登录!";
				return new ResponseEnvelope<UserProductCollect>(false, msg, userProductCollect.getMsgId());
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				userProductCollect.setUserId(loginUser.getId());
			}
			if (userProductCollect.getProductId() == null) {
				msg = "参数productId不能为空！";
				return new ResponseEnvelope<UserProductCollect>(false, msg, userProductCollect.getMsgId());
			}
			List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
			if(list !=null && list.size() > 0){
				int flag = userProductCollectService.delete(list.get(0).getId());
				if(flag == 1 ){
					UserProductCollectCacher.remove(list.get(0).getId());
					ProductCategoryRelCacher.remove(1);
					Long endTime = System.currentTimeMillis();
					//////System.out.println("times:" + (endTime - startTime));
					return new ResponseEnvelope<UserProductCollect>(true,userProductCollect.getMsgId(),true);
				}else{
					return new ResponseEnvelope<UserProductCollect>(false, "数据删除失败!",userProductCollect.getMsgId());
				}
			}else{
				return new ResponseEnvelope<UserProductCollect>(false, "该记录不存在或已被删除!",userProductCollect.getMsgId());
			}
		}

		/**
		 * 保存 我的产品收藏
		 */
		@RequestMapping(value = "/saveProducts")
		@ResponseBody
		public Object saveProducts(
				@PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
				,HttpServletRequest request, HttpServletResponse response) throws Exception{

			String jsonStr = Utils.getJsonStr(request);
			if (jsonStr != null && jsonStr.trim().length() > 0){
			   userProductCollect = (UserProductCollect)JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
			   if(userProductCollect == null){
				  return new ResponseEnvelope<UserProductCollect>(false, "传参异常!","none");
			   }
			}
			//获取登录用户
			LoginUser loginUser = new LoginUser();
			if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
				return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",userProductCollect.getMsgId());
			}else{
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				userProductCollect.setUserId(loginUser.getId());
			}
			if(userProductCollect.getProductId() == null){
				return new ResponseEnvelope<UserProductCollect>(false, "传参异常!","none");
			}
			try {
				//验证用户是否已收藏过该商品
				List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
				if( list != null && list.size() > 0 ){
					return new ResponseEnvelope<UserProductCollect>(false, "已经收藏该商品！",userProductCollect.getMsgId());
				}
				sysSave(userProductCollect,request);
				if (userProductCollect.getId() == null) {
					int id = userProductCollectService.add(userProductCollect);
					logger.info("add:id=" + id);
					userProductCollect.setId(id);
				} else {
					int id = userProductCollectService.update(userProductCollect);
					logger.info("update:id=" + id);
				}

				if("small".equals(style)){

					 String userProductCollectJson = JsonUtil.getBeanToJsonData(userProductCollect);
					 UserProductCollectSmall userProductCollectSmall= new JsonDataServiceImpl<UserProductCollectSmall>().getJsonToBean(userProductCollectJson, UserProductCollectSmall.class);

					 return new ResponseEnvelope<UserProductCollectSmall>(userProductCollectSmall,userProductCollect.getMsgId(),true);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEnvelope<UserProductCollect>(false, "数据异常!",userProductCollect.getMsgId());
			}
			return new ResponseEnvelope<UserProductCollect>(true, "收藏成功!",userProductCollect.getMsgId());
		}

		/**
		 * 自动存储系统字段
		 */
		private void sysSave(UserProductCollect model,HttpServletRequest request){
			if(model != null){
					 LoginUser loginUser = new LoginUser();
					 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
						loginUser.setLoginName("nologin");
					 }else{
					    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
					 }

					if(model.getId() == null){
						model.setGmtCreate(new Date());
						model.setCreator(loginUser.getLoginName());
						model.setIsDeleted(0);
					    if(model.getSysCode()==null || "".equals(model.getSysCode())){
						   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
					   }
					}

					model.setGmtModified(new Date());
					model.setModifier(loginUser.getLoginName());
			}
		}

	/**
	 * 品牌馆--我的收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myCollectProduct")
	public Object myCollectProduct(@ModelAttribute(value="userProductsCollect")UserProductsConllect userProductsCollect,HttpServletRequest request){
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",null);
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		userProductsCollect.setUserId(loginUser.getId());
		List<UserProductsConllect> list = userProductCollectService.getUserProductsConllectList(userProductsCollect);
		request.setAttribute("list", list);

		SysDictionary sysDic = new SysDictionary();
		sysDic.setType("productType");
		List<SysDictionary> productTypeList = sysDictionaryService.getList(sysDic);
		request.setAttribute("productTypeList", productTypeList);

		if( StringUtils.isNotBlank(userProductsCollect.getProductTypeValue()) ){
			SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(userProductsCollect.getProductTypeValue()));
			sysDic.setType(sysDictionary.getValuekey());
			List<SysDictionary> productSmallTypeList = sysDictionaryService.getList(sysDic);
			request.setAttribute("productSmallTypeList", productSmallTypeList);
		}

		request.setAttribute("userProductsCollect", userProductsCollect);
		List<BaseBrand> brandList = baseBrandService.getList(new BaseBrand());
		request.setAttribute("brandList", brandList);
		return Utils.getPageUrl(request, "/online/product/baseBrand/myCollect");
	}



	/**
	 * 用户中心--产品收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myCollects")
	public Object myCollects(@ModelAttribute(value="userProductsCollect")UserProductsConllect userProductsCollect,HttpServletRequest request){
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",null);
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		userProductsCollect.setUserId(loginUser.getId());
		List<UserProductsConllect> list = userProductCollectService.getAllList(userProductsCollect);
		String modelShow = "pDetails" ;
		String value = request.getParameter("value") == null ? "" :request.getParameter("value");
        request.setAttribute("value", value);
        request.setAttribute("type", modelShow);
		request.setAttribute("list", list);

		return Utils.getPageUrl(request, "/online/user/myCollections");
	}
	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectionValue")
	public Object selectionValue(@ModelAttribute(value="userProductsCollect")UserProductsConllect userProductsCollect,HttpServletRequest request){
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",null);
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		String value = request.getParameter("value") == "" ? null :  request.getParameter("value");
		userProductsCollect.setUserId(loginUser.getId());
		userProductsCollect.setProductTypeValue(value);
		List<UserProductsConllect> list = userProductCollectService.getAllList(userProductsCollect);
		request.setAttribute("list", list);

		return Utils.getPageUrl(request, "/online/user/myCollections");
	}

	/**
	 * 用户中心--产品收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myProductCollects")
	public Object myProductCollects(@ModelAttribute(value="userProductsCollect")UserProductsConllect userProductsCollect,HttpServletRequest request){
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",null);
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		List<UserProductsConllect> list = new ArrayList<UserProductsConllect>();
		int total = 0;
		userProductsCollect.setUserId(loginUser.getId());
		total = userProductCollectService.getUserProductsConllectCount(userProductsCollect);
		if(total>0){
			list = userProductCollectService.getUserProductsConllectList(userProductsCollect);
		}
		request.setAttribute("list", list);

		return Utils.getPageUrl(request, "/online/user/myProductCollections");
	}

	/**
	 * 产品收藏列表----接口
	 * @param request
	 * @return
	 */
	@SuppressWarnings({"unused", "unchecked" })
	@RequestMapping(value = "/collectionList")
	@ResponseBody
	public Object collectionList(@PathVariable String style,@ModelAttribute(value="userProductsCollect")UserProductsConllect userProductsCollect
			,Integer designPlanId,Integer spaceCommonId,HttpServletRequest request){
		long start =System.currentTimeMillis();
		int total = 0 ;
		List<UserProductsConllect> list = null;
		try {
			UserProductCollect userProductCollect = new UserProductCollect();
			String jsonStr = Utils.getJsonStr(request);
			if (jsonStr != null && jsonStr.trim().length() > 0) {
				userProductCollect = (UserProductCollect) JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
				if (userProductsCollect.getId() == null) {
					return new ResponseEnvelope<UserProductsConllect>(false, "传参异常!", userProductsCollect.getMsgId());
				}
			}

			String msg = "";
			if( StringUtils.isBlank(userProductsCollect.getMsgId()) ){
				msg = "参数msgId不能为空";
				return new ResponseEnvelope<UserProductsConllect>(false,msg,userProductsCollect.getMsgId());
			}

			/**获取登录用户*/
			LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
			//验证用户
			if (loginUser == null) {
				return new ResponseEnvelope<GroupProduct>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, userProductsCollect.getMsgId());
			}
//			loginUser.setId(48);
//			loginUser.setUserType(1);

			/**获取该用户绑定的序列号品牌*/
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			authorizedList = authorizedConfigService.getList(authorizedConfig);
			String brandIds = "";
			for(AuthorizedConfig ac : authorizedList){
				if( StringUtils.isNotBlank(ac.getBrandIds()) ){
					brandIds += ac.getBrandIds()+",";
				}
			}

			SysDictionary sysDictionary = new SysDictionary();
			String ProductTypeValue=userProductsCollect.getProductTypeValue();
			String smallValueKeyS = null;
			StringBuffer smallValueKeyS_ = new StringBuffer("");
			String versionType = Utils.getPropertyName("app", ResProperties.SYS_VERSION_TYPE, "1").trim();/*1为外网  2  为内网    默认为外网*/
			if( loginUser.getUserType()==1 && "2".equals(versionType)){
				userProductsCollect.setIsInternalUser("yes");
	        }
			
			if(ProductTypeValue!=null&&!"".equals(ProductTypeValue)){
				
				sysDictionary.setValue(Integer.parseInt(userProductsCollect.getProductTypeValue()));
				sysDictionary.setType("classifiedCollect");
				List<SysDictionary>sysDictionaryList= sysDictionaryService.getList(sysDictionary);
				if(sysDictionaryList!=null&&sysDictionaryList.size()>0){
					smallValueKeyS=sysDictionaryList.get(0).getAtt1(); /**2.通过收藏分类ID 获取 下面的产品小分类*/
					if(smallValueKeyS==null||"".equals(smallValueKeyS)){
						smallValueKeyS="";
					}
				}
				userProductsCollect.setSmallValueKeyS(smallValueKeyS);
				userProductsCollect.setUserId(loginUser.getId());
				total = userProductCollectService.getUserProductsConllectCount(userProductsCollect);
				logger.info("total:" + total);
					list = userProductCollectService.getAllList(userProductsCollect);
					if( list != null && list.size() > 0 ) {
						for (UserProductsConllect productCollect : list) {
							int brandState=0;
							int proId=productCollect.getProductId()!=null?Integer.valueOf(productCollect.getProductId()):-1;
							BaseProduct baseProduct=null;
							if(Utils.enableRedisCache()){
								baseProduct = BaseProductCacher.get(proId);
							}else{
								baseProduct = baseProductService.get(proId);
							}
							if( baseProduct != null ){
								if(brandState==0){
									/**品牌是否被绑定状态*/
									String productBrandId = ","+baseProduct.getBrandId()+",";
									if( StringUtils.isEmpty(brandIds) ){
										brandState=brandState+1;
									}
									if( (","+brandIds).indexOf(productBrandId)==-1 ){
										brandState=brandState+1;
									}
								}
							}
							if(brandState>0){
								productCollect.setSalePrice(-1.0);	
							}else{
								// 带上销售价格单位
								if(productCollect.getProductId().length() > 0){
									Integer productId =Integer.parseInt(productCollect.getProductId());
									if (productId != null) {
										BaseProduct basePro = baseProductService.get(productId);
										Integer spvValue = basePro.getSalePriceValue();
										if (spvValue != null) {
											SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice",spvValue);
											productCollect.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
										}
									}
								}
							}
							
						}
					}
			}
			if(ProductTypeValue==null||"".equals(ProductTypeValue)){
				userProductsCollect.setUserId(loginUser.getId());
				total = userProductCollectService.getUserProductsConllectCount_All(userProductsCollect);
				logger.info("total:" + total);
				list = userProductCollectService.getAllList_All(userProductsCollect);
				if( list != null && list.size() > 0 ) {
					for (UserProductsConllect productCollect : list) {
						int brandState=0;
						int proId=productCollect.getProductId()!=null?Integer.valueOf(productCollect.getProductId()):-1;
						BaseProduct baseProduct=null;
						if(Utils.enableRedisCache()){
							baseProduct = BaseProductCacher.get(proId);
						}else{
							baseProduct = baseProductService.get(proId);
						}
						if( baseProduct != null ){
							if(brandState==0){
								/**品牌是否被绑定状态*/
								String productBrandId = ","+baseProduct.getBrandId()+",";
								if( StringUtils.isEmpty(brandIds) ){
									brandState=brandState+1;
								}
								if( (","+brandIds).indexOf(productBrandId)==-1 ){
									brandState=brandState+1;
								}
							}
						}
						if(brandState>0){
							productCollect.setSalePrice(-1.0);	
						}else{
							// 带上销售价格单位
							if(StringUtils.isNotEmpty(productCollect.getProductId())){
//							if(productCollect.getProductId().length() > 0){
								Integer productId =Integer.parseInt(productCollect.getProductId());
								if (productId != null) {
									BaseProduct basePro = baseProductService.get(productId);
									Integer spvValue = basePro.getSalePriceValue();
									if (spvValue != null) {
										SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice",spvValue);
										productCollect.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
									}
								}
							}
						}
						
					}
				}
			}

 
			//媒介类型.如果没有值，则表示为web前端（2）
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			if( list != null && list.size() > 0 ) {
				for (UserProductsConllect productCollect : list) {
					int brandState=0;
					int proId=productCollect.getProductId()!=null?Integer.valueOf(productCollect.getProductId()):-1;
					BaseProduct baseProduct=null;
					if(Utils.enableRedisCache()){
						baseProduct = BaseProductCacher.get(proId);
					}else{
						baseProduct = baseProductService.get(proId);
					}
					
					if( baseProduct != null ){
						
						if(brandState==0){
							/**品牌是否被绑定状态*/
							String productBrandId = ","+baseProduct.getBrandId()+",";
							if( StringUtils.isEmpty(brandIds) ){
								brandState=brandState+1;
							}
							if( (","+brandIds).indexOf(productBrandId)==-1 ){
								brandState=brandState+1;
							}
						}
						if(brandState>0){
							productCollect.setSalePrice(-1.0);	
						}
						//产品大类
						String productType = baseProduct.getProductTypeValue();
						SysDictionary dictionary = new SysDictionary();
	                    if( StringUtils.isNotEmpty(productType) ){
	                    	dictionary = sysDictionaryService.getSysDictionaryByValue("productType", new Integer(productType));
	                        productCollect.setProductType(dictionary.getName());
	                        productCollect.setProductTypeKey(dictionary.getValuekey());
	                        productCollect.setProductTypeCode(dictionary.getValuekey());
	                        productCollect.setProductTypeName(dictionary.getName());
	                    }
	                    if( StringUtils.isNotBlank(productType) && baseProduct.getProductSmallTypeValue() != null ){
	                        SysDictionary sd = sysDictionaryService.getSysDictionaryByValue(dictionary.getValuekey(),baseProduct.getProductSmallTypeValue());
	                        productCollect.setProductSmallTypeCode(sd.getValuekey());
	                        productCollect.setProductSmallTypeName(sd.getName());
	                        productCollect.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
	                        String rootType = StringUtils.isEmpty(sd.getAtt1()) ? "2" : sd.getAtt1().trim();
	                        productCollect.setRootType(rootType);
	                    }
	                    String materialPicIds = baseProduct.getMaterialPicIds();
	                    if(StringUtils.isNotBlank(materialPicIds)){
							String[] mIds = materialPicIds.split(",");
							if( mIds != null ){
								ResPic resPic = new ResPic();
								resPic.setFileKey("product.baseProduct.material");
								resPic.setBusinessId(baseProduct.getId());
								resPic.setOrders(" sequence asc ");
								List<ResPic> materialPics=null;
								if(Utils.enableRedisCache()){
									materialPics =ResourceCacher.getAllPicList(resPic);
								}else{
									materialPics = resPicService.getList(resPic);
								}
								
								if( materialPics != null && materialPics.size() > 0 ){
									String[] materialPaths = new String[materialPics.size()];
									for( int j=0;j<materialPics.size();j++ ){
										resPic = materialPics.get(j);
										if( resPic != null ) {
											materialPaths[j] = resPic.getPicPath();
										}
									}
									productCollect.setMaterialPicPaths(materialPaths);
								}
							}
						}
						DesignPlan designPlan=null;
						DesignTemplet designTemplet = new DesignTemplet();
						if(designPlanId != null){
							//DesignPlan designPlan = designPlanService.get(designPlanId);
							//designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
							if(Utils.enableRedisCache()){
								designPlan = DesignCacher.get(designPlanId);
							}else{
								designPlan = designPlanService.get(designPlanId);
							}

							if(designPlan!=null){
								if(Utils.enableRedisCache()){
									designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
								}else{
									designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
								}

							}
						}

						//材质配置文件路径
						Integer materialConfigId = baseProduct.getMaterialFileId();
						if( materialConfigId != null ){
							ResFile resFile=null;
							if(Utils.enableRedisCache()){
								resFile = ResourceCacher.getFile(materialConfigId);
							}else{
								resFile = resFileService.get(materialConfigId);
							}
							
							if(resFile != null ) {
								productCollect.setMaterialConfigPath(resFile.getFilePath());
							}
						}
						productCollect.setProductLength(baseProduct.getProductLength());
						productCollect.setProductWidth(baseProduct.getProductWidth());
						productCollect.setProductHeight(baseProduct.getProductHeight());
						productCollect.setParentProductId(baseProduct.getParentId());
	                    //u3dModelPath
	                    String u3dModelId = baseProductService.getU3dModelId(mediaType, baseProduct);
	                     if(StringUtils.isNotBlank(u3dModelId) ){
	                    	ResModel resModel=null;
	                    	if(Utils.enableRedisCache()){
	                    		resModel = ResourceCacher.getModel(Integer.valueOf(u3dModelId));
	                    	}else{
	                    		resModel = resModelService.get(Integer.valueOf(u3dModelId));
	                    	}
	                        if( resModel != null ){
	                        	productCollect.setU3dModelPath(resModel.getModelPath());
	                        }
	                    }
	                    
	    				/***在组合产品查询列表 中 增加产品属性****/
	    				Map<String,String> map = new HashMap<String,String>();
	    				map = productAttributeService.getPropertyMap(baseProduct.getId());
	    				baseProduct.setPropertyMap(map);
	    				
	    				//组装产品的规则
	    				Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
	    						productCollect.getProductTypeCode(),productCollect.getProductSmallTypeCode(),spaceCommonId,designTemplet==null?null:designTemplet.getId(),new DesignRules(),map);
	    				productCollect.setRulesMap(rulesMap);
					}
					/*关联查询材质的两个属性(textureAttrValue,laymodes)*/
					/*if (baseProduct != null && StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
						String materialIds=baseProduct.getMaterialPicIds();
						List<String> idsInfo=Utils.getListFromStr(materialIds);
						if(idsInfo!=null&&idsInfo.size()>0){
							ResTexture resTexture=resTextureService.get(Integer.valueOf(idsInfo.get(0)));
							if(resTexture!=null){
								productCollect.setTextureAttrValue(resTexture.getTextureAttrValue());
								productCollect.setLaymodes(resTexture.getLaymodes());
							}
						}
					}*/
					/*关联查询材质的两个属性(textureAttrValue,laymodes)->end*/
					/*处理拆分材质产品返回默认材质*/
					String splitTexturesInfo=baseProduct.getSplitTexturesInfo();
					Integer isSplit=0;
					List<SplitTextureDTO> splitTextureDTOList=null;
					if(StringUtils.isNotBlank(splitTexturesInfo)){
						Map<String,Object> map=baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo,"choose");
						isSplit=(Integer) map.get("isSplit");
						splitTextureDTOList=(List<SplitTextureDTO>) map.get("splitTexturesChoose");
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
								productCollect.setTextureWidth(resTexture.getFileWidth()+"");
								productCollect.setTextureHeight(resTexture.getFileHeight()+"");
								productCollect.setTextureAttrValue(resTexture.getTextureAttrValue());
								productCollect.setLaymodes(resTexture.getLaymodes());
								String[] materialPathList=new String[]{resTextureDTO.getPath()};
								productCollect.setMaterialPicPaths(materialPathList);
								/*同时存一份数据在老的数据结构上->end*/
							}
						}
					}
					productCollect.setIsSplit(isSplit);
					productCollect.setSplitTexturesChoose(splitTextureDTOList);
				}
			}
			if("small".equals(style) && list != null && list.size() > 0){
				 String userProductsConllectJsonList = JsonUtil.getListToJsonData(list);
				 List<UserProductsConllect> smallList= new JsonDataServiceImpl<UserProductsConllect>().getJsonToBeanList(userProductsConllectJsonList, UserProductsConllect.class);
				 return new ResponseEnvelope<UserProductsConllect>(total,smallList,userProductsCollect.getMsgId());
			 }
 
			/****************************************/
			//////System.out.println("times = " + (System.currentTimeMillis() -start));
			/****************************************/
			
		}  catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductsConllect>(false, "数据异常!",userProductsCollect.getMsgId());
		}
		return new ResponseEnvelope<UserProductsConllect>(total, list,userProductsCollect.getMsgId());
	}
	
	/**
	 * 取消产品收藏
	 */
	@RequestMapping(value = "/cancelCollect")
	@ResponseBody
	public Object cancelCollect(@PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
	                 ,HttpServletRequest request, HttpServletResponse response) {
		
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",null);
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userProductCollect.setUserId(loginUser.getId());
			userProductCollectSearch.setUserId(loginUser.getId());
		}
		int total = 0;
		try{
			total = userProductCollectService.getCount(userProductCollectSearch);
			if (userProductCollect != null && userProductCollect.getUserId() != null && userProductCollect.getProductId() != null) {
				Integer i =  userProductCollectService.cancelConllect(userProductCollect);
				if(i!=null && new Integer(i).intValue()>0){
					userProductCollectService.delete(i);
				}
				logger.info("delete:id=" +i);
			}
			if( total == 0){
				return new ResponseEnvelope<UserProductCollect>(false, "记录不存在!",userProductCollect.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductCollect>(false, "删除失败!",userProductCollect.getMsgId());
			}
		return new ResponseEnvelope<UserProductCollect>(true,userProductCollect.getMsgId(),true);
	}
	
	/**
	 * 保存我的产品收藏
	 * @param style
	 * @param userProductCollect
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveProduct")
	@ResponseBody
	public Object saveProduct(
			@PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long startTime = System.currentTimeMillis();
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			userProductCollect = (UserProductCollect)JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
			if(userProductCollect == null){
				return new ResponseEnvelope<UserProductCollect>(false, "传参异常!","none");
			}
		}
		Integer userId = 0;
		LoginUser loginUser = new LoginUser();
		if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",userProductCollect.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userId = loginUser.getId();
			userProductCollect.setUserId(userId);
		}
		
		String msg = "";
		if( StringUtils.isBlank(userProductCollect.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		if( userProductCollect.getUserId() == null || userProductCollect.getUserId() == 0 ){
			msg = "参数userId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		if( userProductCollect.getProductId() == null ){
			msg = "参数productId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}else if(userProductCollect.getProductId() != null){
			BaseProduct bp = new BaseProduct();
			bp = baseProductService.get(userProductCollect.getProductId());
			if(bp ==null){
				msg = "该产品不存在";
				return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
			}
		}
		userProductCollect.setUserId(userId);
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if(CustomerListUtils.isNotEmpty(list)){
			msg = "该产品已收藏，请查看收藏夹！";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,userProductCollect.getMsgId());
		}
		
		try {
			sysSave(userProductCollect,request);
			if (userProductCollect.getId() == null) {
				int id = userProductCollectService.add(userProductCollect);
				logger.info("add:id=" + id);
				userProductCollect.setId(id);
			} else {
				int id = userProductCollectService.update(userProductCollect);
				logger.info("update:id=" + id);
			}
			UserProductCollectCacher.remove(userProductCollect.getId());
			if("small".equals(style)){
				//String userProductCollectJson = JsonUtil.getBeanToJsonData(userProductCollect);
				//UserProductCollectSmall userProductCollectSmall= new JsonDataServiceImpl<UserProductCollectSmall>().getJsonToBean(userProductCollectJson, UserProductCollectSmall.class);
				String message = "ok";
				return new ResponseEnvelope<UserProductCollect>(true,message,userProductCollect.getMsgId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductCollect>(false, "数据异常!",userProductCollect.getMsgId());
		}
		Long endTime = System.currentTimeMillis();
		//////System.out.println("times:" + (endTime - startTime));
		return new ResponseEnvelope<UserProductCollect>(userProductCollect,userProductCollect.getMsgId(),true);
	}
	
	 /**
		 * 我的产品收藏全部列表
		 */
		@RequestMapping(value = "/userProductList")
		@ResponseBody
		public Object userProductList(
		            @PathVariable String style,@ModelAttribute("userProductCollect") UserProductCollect userProductCollect
					,HttpServletRequest request, HttpServletResponse response) throws Exception  {
			String jsonStr = Utils.getJsonStr(request);
			if (jsonStr != null && jsonStr.trim().length() > 0){
				userProductCollect = (UserProductCollect)JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
				if(userProductCollect == null){
				   return new ResponseEnvelope<UserProductCollect>(false, "传参异常!","none");
				}
			}
			List<BaseProduct> userProductList = new ArrayList<BaseProduct>();
	    	List<UserProductCollect> list = new ArrayList<UserProductCollect> ();
			list = userProductCollectService.getList(userProductCollect);
			if(list.size()>0){
				for(UserProductCollect userProduct:list){
					BaseProduct product = baseProductService.get(userProduct.getProductId());
					if(product!=null){
						product.setBrandName(product.getBrandId()==null?"":baseBrandService.get(product.getBrandId())==null?"":baseBrandService.get(product.getBrandId()).getBrandName());
						product.setProductStyle(product.getProStyleId()==null?"":sysDictionaryService.getSysDictionaryByValue("productStyle", product.getProStyleId()).getName());
						product.setProductType(StringUtils.isEmpty(product.getProductTypeValue())?"":sysDictionaryService.getSysDictionaryByValue("productType", new Integer(product.getProductTypeValue())).getName());
						product.setProductPicPath(product.getPicId()==0?"":resPicService.get(product.getPicId())==null?"":resPicService.get(product.getPicId()).getPicPath());
						String houseTypeName = "";
						if( StringUtils.isNotBlank(product.getHouseTypeValues()) ){
							String[] arr = product.getHouseTypeValues().split(",");
							for( int j=0;j<arr.length;j++ ){
								if( StringUtils.isNotBlank(arr[j]) ){
									houseTypeName += sysDictionaryService.getSysDictionaryByValue("houseType",Integer.valueOf(arr[j])).getName();
									if( (j+1) < arr.length ){
										houseTypeName += ",";
									}
								}
							}
						}
						product.setHouseType(houseTypeName);
					}
					userProductList.add(product);
				}
				
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String userProductCollectJsonList = JsonUtil.getListToJsonData(list);
				 List<UserProductCollectSmall> smallList= new JsonDataServiceImpl<UserProductCollectSmall>().getJsonToBeanList(userProductCollectJsonList, UserProductCollectSmall.class);
				 return new ResponseEnvelope<UserProductCollectSmall>(smallList,userProductCollect.getMsgId());
			 }
			 
		
			return new ResponseEnvelope<BaseProduct>(userProductList,userProductCollect.getMsgId());
		}
}
