package com.nork.product.controller.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.properties.ResProperties;
import com.nork.product.model.*;
import com.nork.product.service.*;
import com.nork.user.model.UserTypeCode;

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
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.service.ProductPropsService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * @Title: ProductCategoryRelController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品与产品类目关联Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/productCategoryRel")
public class WebProductCategoryRelController {
	private static Logger logger = Logger
			.getLogger(WebProductCategoryRelController.class);
	private final JsonDataServiceImpl<ProductCategoryRel> JsonUtil = new JsonDataServiceImpl<ProductCategoryRel>();
	private final String STYLE="jsp";
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/product/productCategoryRel";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/productCategoryRel";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/productCategoryRel";
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
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
	private ResModelService resModelService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ProCategoryService proCategoryService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private GroupProductCollectService groupProductCollectService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private ProductPropsService productPropsService;
	@Autowired
	private ProductColorsService productColorsService;
	@Autowired
	private SpaceCommonService spaceCommonService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 * 根据分类查询产品接口
	 *	主逻辑放在service
	 *@author huangsongbo
	 * @param request
	 * @param productCategoryRel
	 * @return Object
	 */
	@RequestMapping(value = "/searchProduct_old")
	@ResponseBody
	public Object searchProductV6_old(@PathVariable String style,
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
		// 获取配置:决定是否走老接口还是新接口
		String oldOrNew = Utils.getValue("app.product.searchProduct.oldOrNew", "new");
		if(StringUtils.equals("new", oldOrNew)){
			// 参数验证 ->start
			if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) 
				return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", productCategoryRel.getMsgId());
			// 用户信息
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			if(loginUser == null || loginUser.getId() == null || loginUser.getId() < 1) {
				return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", productCategoryRel.getMsgId());
			}
			DesignPlan designPlan = new DesignPlan();
			if (designPlanId != null && designPlanId != 0)
				designPlan = designPlanService.get(designPlanId);
			if (designPlan == null)
				return new ResponseEnvelope<ProductCategoryRel>(false, "找不到该设计方案：" + designPlanId, productCategoryRel.getMsgId());
			
		// 参数验证 ->end

//			ResponseEnvelope<CategoryProductResult> result =
//					productCategoryRelService.searchProductV6(templateProductId, productCategoryRel, loginUser, productModelOrBrandName, planProductId, houseType, productTypeValue, spaceCommonId, designPlan, smallTypeValue, request, null);
			return null;
		}else{
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
			 
			// 基本信息->end
			ResponseEnvelope<CategoryProductResult> result = new  ResponseEnvelope<CategoryProductResult>();
			if (Utils.enableRedisCache()) {
				result = ProductCategoryRelCacher.searchProduct(productCategoryRel, request, houseType, designPlanId,
						planProductId, spaceCommonId, templateProductId, "" + productTypeValue, "" + smallTypeValue, queryType,
						productModelOrBrandName, loginUser, designPlan);
			}else{
				result = productCategoryRelService.searchProduct(productCategoryRel, request, houseType, designPlanId,
						planProductId, spaceCommonId, templateProductId, "" + productTypeValue, "" + smallTypeValue,
						queryType, productModelOrBrandName, loginUser, designPlan);
			}
			Long startDateEnd = System.currentTimeMillis();
			logger.info("------查询接口总耗时:" + (startDateEnd - startDate));
			return result;
		}
	}
	
	/**
	 * 根据分类查询产品接口
	 *	主逻辑放在service
	 *@author huangsongbo
	 * @param request
	 * @param productCategoryRel
	 * @return Object
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
		    @RequestParam(value = "isStandard", required = false, defaultValue ="0") Integer isStandard,
		    @RequestParam(value = "regionMark", required = false) String regionMark,
		    @RequestParam(value = "styleId", required = false) Integer styleId,
		    @RequestParam(value = "measureCode", required = false) String measureCode,
		    @RequestParam(value = "smallpox", required = false) String smallpox
			) {
		// 获取配置:决定是否走老接口还是新接口
		// 参数验证 ->start
		if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) 
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", productCategoryRel.getMsgId());
		// 用户信息
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId() == null || loginUser.getId() < 1) {
			return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", productCategoryRel.getMsgId());
//			loginUser = new LoginUser();
//			loginUser.setId(48);
//			loginUser.setUserType(1);
		}// 参数验证 ->end
		
		DesignPlan designPlan = new DesignPlan();
		if (designPlanId != null && designPlanId != 0)
			designPlan = designPlanService.get(designPlanId);
		if (designPlan == null)
			return new ResponseEnvelope<ProductCategoryRel>(false, "找不到该设计方案：" + designPlanId, productCategoryRel.getMsgId());
		
		

		ResponseEnvelope<CategoryProductResult> result = 
				productCategoryRelService.searchProductV6(templateProductId, productCategoryRel, loginUser, productModelOrBrandName, 
						planProductId, houseType, productTypeValue, spaceCommonId, designPlan, smallTypeValue, request, userStatusType,
						isStandard,regionMark,styleId,measureCode,smallpox);
		result.setMsgId(productCategoryRel.getMsgId());
		
		logger.error("result======================"+ JSONObject.fromObject(result).toString());
		return result;
	}
	
    //按搜索产品属性匹配度排序
    public List<CategoryProductResult> getProductList(List<CategoryProductResult> list,ProductCategoryRel productCategoryRel){
    	List<ProductProps> propsOrderList = productCategoryRel.getPropsOrderList();
		if (Lists.isNotEmpty(propsOrderList)) {
			  //搜索产品的排序属性排序
			ComparatorP cpmparator = new ComparatorP();
	 		Collections.sort(propsOrderList, cpmparator);
	 		//结果集产品
			for(CategoryProductResult productResult : list){
				BaseProduct baseProduct=null;
				if(Utils.enableRedisCache()){
					baseProduct = BaseProductCacher.get(productResult.getProductId());
				}else{
					baseProduct = baseProductService.get(productResult.getProductId());
				}
				if(baseProduct==null){
					logger.error("baseProduct is null;productId="+productResult.getProductId()+";");
					continue;
				}
				Map<String,String> map = new HashMap<String,String>();
				//所有属性值的map
				map = productAttributeService.getPropertyOrderMap(baseProduct.getId());
				if( map.size()==0 ){
					productResult.setProductOrdering(99999999);
					continue;
				}
				//
				StringBuffer orderNumber = new StringBuffer();
				for (ProductProps props : propsOrderList) {
					//选中产品显示第一个
					if( productResult.getProductId().intValue() == props.getProductId().intValue() ){
						productResult.setProductOrdering(1);
						continue; 
					}
					int tmp = 0;
					for (Map.Entry<String, String> entry : map.entrySet()) {
						//属性类型匹配
						if( entry.getKey().equals(props.getParentCode()) ){
							if(StringUtils.isNotBlank(map.get(props.getParentCode()))){
								//获取属性份额里
								ProductProps productProps = productPropsService.get(Utils.getIntValue(map.get(props.getParentCode())));
								if(productProps != null){
									if( productProps.getPropValue().equals(props.getPropValue()) ){
										//1、value相等且属性数量一样2、value相等且属性数量不一样 
										if(propsOrderList.size() == map.size()){
											orderNumber.append(2);
										}else{
											orderNumber.append(3);
										}
										tmp ++ ; break;
									}else{
										//3、value不等、同类型且属性数量一样 4、value不等、同类型且属性数量不一样
										if(propsOrderList.size() == map.size()){
											orderNumber.append(4);
										}else{
											orderNumber.append(5);
										}
										tmp ++ ; break;
									}
								}
							}
						}
					}
					//5、无匹配的属性
					if(tmp == 0){
						orderNumber.append(7);
					}
				}
				productResult.setProductOrdering(Utils.getIntValue(orderNumber.toString()));
			}
		}
		//如果该产品关联了色系则关联色系排序
		if(list.size() > 0){
			BaseProduct baseProduct = new BaseProduct();
			ProductColors productColors = new ProductColors();
			ProductColors productColors_=null;
			for (CategoryProductResult cpr : list) {
				Integer productId = cpr.getProductId();
				if(productId != null){
					if(Utils.enableRedisCache()){
						baseProduct = BaseProductCacher.get(cpr.getProductId());
					}else{
						baseProduct = baseProductService.get(cpr.getProductId());
					}
					String colorsLongCode = baseProduct.getColorsLongCode();
					String pCode = "";
					String cCode = "";
					if(StringUtils.isNotBlank(colorsLongCode)){
						String[] colorsCode=colorsLongCode.split("\\.");
						//获取父色系排序
						pCode = colorsCode[1];
						productColors.setColorsCode(pCode);
						productColors_ = productColorsService.getByCode(productColors);
						String orderingF="";
						String orderingC="";
						if(productColors_!=null){
							if(productColors_.getOrdering() != null){
								orderingF = productColors_.getOrdering()+"";
								if(orderingF.length() ==1 ){
									orderingF = 0+orderingF;
								}
							}
						}
						//截取子编码
						cCode = colorsCode[2];
						productColors.setColorsCode(cCode);
						productColors_ = productColorsService.getByCode(productColors);
						if(productColors_!=null){
							if(productColors_.getOrdering() != null){
								orderingC = productColors_.getOrdering() + "";
								if(orderingC.length() ==1 ){
									orderingC = 0+orderingC;
								}
							}
						}
						//父编码排序+子编码排序
						if(StringUtils.isNotBlank(orderingF) && StringUtils.isNotBlank(orderingC)){
							cpr.setColorsOrdering(Integer.parseInt((orderingF+orderingC)));
						}
					}
				}
			}
		}
		/**列表排序,推荐、小类、同属性匹配度、使用量排序*/
		ComparatorO cpmparator = new ComparatorO();
 		Collections.sort(list, cpmparator);
 		
 		return list;
    }
    
	/**
	 * 根据产品顺序排序（升序）
	 * @author Administrator
	 *
	 */
	public class ComparatorT implements Comparator {
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
	public class ComparatorO implements Comparator {
		public int compare(Object obj1, Object obj2) {
			CategoryProductResult unity1 = (CategoryProductResult) obj1;
			CategoryProductResult unity2 = (CategoryProductResult) obj2;
			Integer productOrder1 = unity1.getProductOrdering() == null ? new Integer(0) : new Integer(unity1.getProductOrdering());
			Integer productOrder2 = unity2.getProductOrdering() == null ? new Integer(0) : new Integer(unity2.getProductOrdering());
			Integer colorsOrder1 = unity1.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE) : new Integer(unity1.getColorsOrdering());
			Integer colorsOrder2 = unity2.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE) : new Integer(unity2.getColorsOrdering());
			if( unity1.getOrderType().compareTo(unity2.getOrderType()) != 0 ){ //定制、推荐、小类
				return unity1.getOrderType().compareTo(unity2.getOrderType());
			}else if( productOrder1.compareTo(productOrder2) != 0 ){//属性匹配度
				return productOrder1.compareTo(productOrder2);
			}else if(colorsOrder1.compareTo(colorsOrder2) != 0){//色系排序
				return colorsOrder1.compareTo(colorsOrder2);
			}else{ //使用量
				Integer count1 = unity1.getProductCount() == null ? 0 : unity1.getProductCount();
				Integer count2 = unity2.getProductCount() == null ? 0 : unity2.getProductCount();
				/*return unity2.getProductCount().compareTo(unity1.getProductCount());*/
				return count2.compareTo(count1);
			}
		}
	}
	
	/**
	 * 属性长序号排序（升序）
	 * @author Administrator
	 *
	 */
	public class ComparatorP implements Comparator {
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
	
	public List<CategoryProductResult> getList(List<CategoryProductResult> list,ProductCategoryRel productCategoryRel){
		
		return list;
	}
	
	
	
	/**
     * 根据分类查询产品接口
     *
     * @param request
     * @param productCategoryRel
     * @return Object
     */
    @RequestMapping(value = "/searchBuildingHome")
    @ResponseBody
    public Object searchBuildingHome(@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel
			,HttpServletRequest request, HttpServletResponse response) {
    	Long startTime = System.currentTimeMillis();
    	Long endTime = 0L;
    	String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productCategoryRel = (ProductCategoryRelSearch)JsonUtil.getJsonToBean(jsonStr, ProductCategoryRelSearch.class);
		}
		String msg = "";
		if(StringUtils.isBlank(productCategoryRel.getCategoryCode())){
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg,productCategoryRel.getMsgId());
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		//验证用户
		if (loginUser == null) {
			return new ResponseEnvelope<GroupProduct>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, productCategoryRel.getMsgId());
		}
//		LoginUser loginUser = new LoginUser();
//		loginUser.setUserType(3);
//		loginUser.setId(48);
        String versionType = Utils.getPropertyName("app", ResProperties.SYS_VERSION_TYPE, "1").trim();/*1为外网  2  为内网    默认为外网*/
        if( loginUser.getUserType()==1 && "2".equals(versionType)){
        	productCategoryRel.setIsInternalUser("yes");
        }
        productCategoryRel.setUserId(loginUser.getId());
        //建材家居搜索多个类型查询方式
  		if( productCategoryRel.getCategoryCode().contains(",") ){
  			String[] arr = productCategoryRel.getCategoryCode().split(",");
  			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
  		}else{
  			String categoryCode = productCategoryRel.getCategoryCode();
			ProCategory proCategory = null;
			if(StringUtils.isNotBlank(categoryCode)){
				proCategory = proCategoryService.findOneByCode(categoryCode);
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
						productCategoryRel.setCategoryCode("." + categoryCode + ".");
					}
				}
			}
  		}
    	/*获取黑名单配置信息*/
    	/*获得黑名单配置开关*/
        Integer categoryBlacklistEnable=0;
    	//Integer categoryBlacklistEnable=Integer.valueOf(Utils.getValue("categoryBlacklistEnable", "0"));
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "categoryBlacklistEnable");
        if(sysDictionary1!=null){
        	try{
        		categoryBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
        	}catch(Exception e){
        		throw new RuntimeException("配置格式错误,type=configuration,valueKey=categoryBlacklistEnable,att1="+sysDictionary1.getAtt1());
        	}
        }
    	Set<String> blacklist=new HashSet<String>();
    	if(categoryBlacklistEnable==1){
    		/*获取黑名单配置*/
    		//String categoryBlacklist=Utils.getValue("categoryBlacklist", "");//brand_appliances:1;brand_furniture:1,2,3;brand_appliances:xingx
    		String categoryBlacklist="";
    		SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "categoryBlacklist");
    		if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
    			categoryBlacklist=sysDictionary2.getAtt1();
    		Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(categoryBlacklist);//{CW_jj=[dddd, xxxx, !xingx]}
    		/*处理类似于"!xingx"的值*/
    		categoryBlacklistMap=baseProductService.dealWithMap(categoryBlacklistMap);
    		/*得到用户所属类型:序列号->公司->小类*/
    		Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
    		/*获取产品类型黑名单(取交集)*/
    		boolean flag=false;
    		for(String typeStr:typeSet){
    			if(categoryBlacklistMap.containsKey(typeStr))
    				if(!flag){
    					flag=true;
    					blacklist.addAll(categoryBlacklistMap.get(typeStr));
    				}else{
    					blacklist.retainAll(categoryBlacklistMap.get(typeStr));
    				}
    		}
    		/*productCategoryRel过滤条件中添加黑名单*/
    		productCategoryRel.setBlacklistSet(blacklist);//[xingx, 1]
    	}
    	
    	//获取该用户绑定的序列号品牌
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(1);
		List<AuthorizedConfig> authorizedList = new ArrayList<>();
		if( Utils.enableRedisCache() ){
			authorizedList = AuthorizedConfigCacher.getList(authorizedConfig);
		}else{
			authorizedList = authorizedConfigService.getList(authorizedConfig);
		}
		String brandIds = "";
		for(AuthorizedConfig ac : authorizedList){
			if( StringUtils.isNotBlank(ac.getBrandIds()) ){
				brandIds += ac.getBrandIds()+",";
			}
		}
		/**
		 *设置品牌是否过滤无品牌条件、授权码条件、企业细分行业条件
		 * @author xiaoxc_20171120
		 */
		if(UserTypeCode.USER_TYPE_OUTER_B2B == loginUser.getUserType()){
			//设置品牌行业值，用户过滤同行业品牌产品
			List<BrandIndustryModel> brandIndustryModelList = baseBrandService.getAuthorizedBrandFilterCondition(loginUser.getId());
			productCategoryRel.setBrandIndustryModelList(brandIndustryModelList);
		}
		int total = 0;
 		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
 		try{
 			endTime = System.currentTimeMillis();
		    logger.warn("分类搜索查询结果之前消耗时间times:"+(endTime-startTime));
		    if( Utils.enableRedisCache() ){
		    	total = ProductCategoryRelCacher.findBuildingHomeProductCount(productCategoryRel);
		    }else{
		    	total = productCategoryRelService.findBuildingHomeProductCount(productCategoryRel);
		    }
		    if(total > 0){
			    if( Utils.enableRedisCache() ){
				    list = ProductCategoryRelCacher.findBuildingHomeProductResult(productCategoryRel);
			    }else{
			    	list = productCategoryRelService.findBuildingHomeProductResult(productCategoryRel);
			    }
		    }
		    logger.warn("分类搜索查询结果之后消耗时间times:"+(System.currentTimeMillis()-startTime));
		    BaseProduct baseProduct = new BaseProduct();
			ProductColors productColors = new ProductColors();
			for(CategoryProductResult productResult : list){
//				if( 3 == loginUser.getUserType() ){  授权码 不判断用户类型，  所有用户类型没绑授权码都显示***
					//品牌是否被绑定
					String productBrandId = ","+productResult.getBrandId()+",";
					if( StringUtils.isEmpty(brandIds) ){
						productResult.setSalePrice("-1.0");
					}
					if( (","+brandIds).indexOf(productBrandId)==-1 ){
						productResult.setSalePrice("-1.0");
					}
//				}
				// 带上销售价格单位
				Integer productId = productResult.getProductId();
				if (productId != null) {
					BaseProduct basePro = baseProductService.get(productId);
					Integer spvValue = basePro.getSalePriceValue();
					if (spvValue != null) {
						SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice",spvValue);
						productResult.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
					}
				}
			}
 		}catch(Exception e){
 			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!",productCategoryRel.getMsgId());
 		}
        //////System.out.println("消耗时间times:"+((System.currentTimeMillis())-startTime));
	    return new ResponseEnvelope<CategoryProductResult>(total,list,productCategoryRel.getMsgId());
    }
	

	/**
	 * 查询产品组合列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProductGroup")
	@ResponseBody
	public Object searchProductGroup(HttpServletRequest request, @ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel,
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
									 @RequestParam(value = "designTempletId",required = false)Integer designTempletId
			){
		
		// 楼鑫华 注释掉的，旧的废弃任务
//		return null;
		
		
		//structureId=2;
		logger.info("分类查找产品组合列表。。。");
		GroupProductSearch groupSearch = new GroupProductSearch();
		if(StringUtils.isBlank(productCategoryRel.getCategoryCode())){
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空",productCategoryRel.getMsgId());
		}
		groupSearch.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		//媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		// 内部用户可以查询测试中的数据
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if( new Integer(1).equals(loginUser.getUserType()) ){
			groupSearch.setStates(Arrays.asList(1,2));
		}else{
			groupSearch.setState(1);
		}
		//获取设计方案产品u3d模型版本用到
		DesignPlan designPlan = new DesignPlan();
		if(Utils.enableRedisCache()){
			designPlan = DesignPlanCacher.getDesignPlan(designPlanId);
		}else{
			designPlan = designPlanService.get(designPlanId);
		}
		/*if( StringUtils.isNotBlank(houseType) ){
			groupSearch.setHouseTypeValues(houseType);
		}*/
		//设置产品空间类型查询条件
		if(StringUtils.isNotBlank(houseType)){
			/*对应出productHouseType的数据字典的value*/
			SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
			sysDictionarySearch.setAtt1(houseType);
			sysDictionarySearch.setType("productHouseType");
			//sysDictionarySearch.setType("houseType");
			List<SysDictionary> sysDictionaryList=sysDictionaryService.getPaginatedList(sysDictionarySearch);
			List<String> productHouseTypeList=new ArrayList<String>();//过滤条件
			if(sysDictionaryList!=null&&sysDictionaryList.size()>0){
				for(SysDictionary sysDictionary:sysDictionaryList){
					productHouseTypeList.add(""+sysDictionary.getValue());
				}
			}
			if(productHouseTypeList.size()>0){
				groupSearch.setHouseTypeList(productHouseTypeList);
			}
		}
		//设置产品空间类型查询条件->end
		//设置主产品属性过滤查询条件
		if(planProductId!=null&&planProductId>0){
			DesignPlanProduct designPlanProduct=designPlanProductService.get(planProductId);
			if(designPlanProduct!=null){
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
		List<SearchProductGroupResult> resultList = new ArrayList<>();
		
		 
		int total=0;
		if(structureId!=null&&structureId.intValue()>0){
			/*if(designTempletId==null||designTempletId.intValue()<=0){
				return new ResponseEnvelope<ProductCategoryRel>(false, "参数designTempletId不能为空",productCategoryRel.getMsgId());
			}
			groupSearch.setStructureId(structureId);
			groupSearch.setDesignTempletId(designTempletId);
			total = groupProductService.getGroupCountByStructureId(groupSearch);*/
			total = groupProductService.getGroupCountByStructureId(structureId);
		}
		if(structureId==null||structureId.intValue()<=0){
				groupSearch.setStructureState(2);	/*结构组合没有数据就查询普通组合*/
				if(productTypeValue!=null&&!"".equals(productTypeValue)&&!"0".equals(productTypeValue)){
					groupSearch.setProductTypeValue(productTypeValue);
				}
				if(productTypeValue!=null&&!"".equals(productTypeValue)&&!"0".equals(productTypeValue)&&smallTypeValue!=null&&!"".equals(smallTypeValue)&&!"0".equals(smallTypeValue)){
					SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType",Utils.getIntValue(productTypeValue));
					SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(), Integer.valueOf(smallTypeValue));
					String smallKey = "";
					if("2".equals(smallSd.getAtt4()) && "baimo".equals(smallSd.getAtt3())){
						smallKey = Utils.getTypeValueKey(smallSd.getValuekey());
					}else{
						smallKey = smallSd.getValuekey();
					}
					SysDictionary sd  = sysDictionaryService.findOneByValueKeyInCache(smallKey);
					groupSearch.setSmallTypeValue(sd.getValue().toString());
				} 
				total = groupProductService.getGroupCountByProduct(groupSearch);
		}
 		if( total > 0 ){
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
			
			List<SearchProductGroupDetail> details = null;
			SearchProductGroupResult groupProduct = null;
			SearchProductGroupDetail groupDetail = null;
			GroupProduct groupProduct_ = null;
			List<GroupProduct> groupProducts=null;
			if(structureId!=null&&structureId.intValue()>0){
				
				/*groupSearch.setStructureId(structureId);
				groupSearch.setDesignTempletId(designTempletId);
				groupProducts = groupProductService.getGroupListByStructureId(groupSearch);*/
				groupProducts = groupProductService.getGroupListByStructureId(structureId);
			}
			if(structureId==null||structureId.intValue()<=0){
				groupProducts = groupProductService.getGroupListByProduct(groupSearch);
			}
			List<GroupProductDetails> groupProductDetails = new ArrayList<>();
			if( groupProducts != null && groupProducts.size() > 0 ){
				for( GroupProduct entity : groupProducts ){
					int brandState=0;
					if(entity!=null){
						if(entity.getLocation()!=null&&!"".equals(entity.getLocation())){
							ResFile resFile=null;		
							if(StringUtils.isNumeric(entity.getLocation())){/**判断里面是不是数字*/
								resFile	=resFileService.get(Integer.parseInt(entity.getLocation()));
								String txt = "";
								if(resFile!=null){
									String url=Tools.getRootPath(resFile.getFilePath(), "")+resFile.getFilePath();
									txt = FileUploadUtils.getFileContext(url);
									entity.setLocation(txt);	
									/*entity.setStructureConfig(txt);*/
								}
								entity.setLocation(txt);
							}
						}
	 
						groupProduct = new SearchProductGroupResult();
						groupProduct.setGroupConfig(entity.getLocation());
						groupProduct.setGroupId(entity.getId());
						groupProduct.setGroupCode(entity.getGroupCode());
						groupProduct.setGroupName(entity.getGroupName());
						// 组装产品组封面图片
						Integer picId = entity.getPicId();
						if( picId != null && picId > 0 ){
							ResPic pic = resPicService.get(picId);
							if( pic != null ){
								groupProduct.setPicPath(pic.getPicPath());
							}
						}
						// 如果没有图片则显示默认图片
						if( StringUtils.isBlank(groupProduct.getPicPath()) ){
	
						}
						// 是否收藏
						GroupProductCollectSearch groupCollectSearch = new GroupProductCollectSearch();
						groupCollectSearch.setUserId(loginUser.getId());
						groupCollectSearch.setGroupId(entity.getId());
						int count = groupProductCollectService.getCount(groupCollectSearch);
						if( count > 0 ){
							groupProduct.setCollectState(1);
						}
						if(structureId!=null&&structureId.intValue()>0) {
							groupProduct_ = groupProductService.getStructureByGroupId(groupProduct.getGroupId());
							if( groupProduct_ != null){
								groupProduct.setStructureCode(groupProduct_.getStructureCode());
								groupProduct.setStructureName(groupProduct_.getStructureName());
								groupProduct.setProductStructureId(groupProduct_.getProductStructureId());
								
								if(groupProduct_.getLocation()!=null&&!"".equals(groupProduct_.getLocation())){
									ResFile resFile=null;
									String txt=null;
									if(StringUtils.isNumeric(groupProduct_.getLocation())){/**判断里面是不是数字*/
										resFile	=resFileService.get(Integer.parseInt(groupProduct_.getLocation()));
									}
									if(resFile!=null){
										String url=Tools.getRootPath(resFile.getFilePath(), "")+resFile.getFilePath();
										txt=FileUploadUtils.getFileContext(url);
									}
									if(txt!=null&&!"".equals(txt)){
										groupProduct.setStructureConfig(txt);
									}
//							else{
//								groupProduct.setStructureConfig(groupProduct_.getLocation());
//							}
								}
							}
						}
						
						/*组装产品组中的产品列表*/
						GroupProductDetails groupProductDetailSearch = new GroupProductDetails();
						 groupProductDetailSearch.setGroupId(entity.getId());
						groupProductDetails = groupProductDetailsService.getList(groupProductDetailSearch);
						if( groupProductDetails != null && groupProductDetails.size() > 0 ){
							details = new ArrayList<>();
							for( GroupProductDetails detailEntity : groupProductDetails ){
								groupDetail = new SearchProductGroupDetail();
								
								if(detailEntity.getChartletProductModelId()!=null&&detailEntity.getChartletProductModelId().intValue()>0){
									BaseProduct baseProduct=null;
									baseProduct=baseProductService.get(detailEntity.getChartletProductModelId());
									if(baseProduct!=null){
										ResModel resModel= null;
										resModel=resModelService.get(baseProduct.getWindowsU3dModelId());
										if(resModel!=null){
											groupDetail.setU3dModelPath(resModel.getModelPath());
											groupDetail.setTextureProductModelId(detailEntity.getChartletProductModelId());  /**贴图产品的模型id*/
											groupDetail.setModelMinHeight(resModel.getMinHeight());
										}
									}
								}
								if(detailEntity.getChartletProductModelId()==null||detailEntity.getChartletProductModelId().intValue()<=0){
									BaseProduct baseProduct_=baseProductService.get(detailEntity.getProductId());
									if(baseProduct_!=null){
										ResModel resModel= null;
										resModel=resModelService.get(baseProduct_.getWindowsU3dModelId());
										if(resModel!=null){
											groupDetail.setU3dModelPath(resModel.getModelPath());
											groupDetail.setModelMinHeight(resModel.getMinHeight());
										}
									}
								}
 								
								groupDetail.setProductId(detailEntity.getProductId());
								groupDetail.setProductGroupId(groupProduct.getGroupId());
								int isMain = detailEntity.getIsMain()==null?0:detailEntity.getIsMain();
								groupDetail.setIsMainProduct(isMain);
								if( isMain == 1 ) {
									groupProduct.setProductId(detailEntity.getProductId());
								}
								logger.warn("组合产品详情++++++++产品ID："+detailEntity.getProductId());
								if( detailEntity.getProductId() != null && detailEntity.getProductId() > 0 ){
									BaseProduct baseProduct = baseProductService.get(detailEntity.getProductId());
									if( baseProduct != null ){
										if(brandState==0){
												/*品牌是否被绑定状态*/
												String productBrandId = ","+baseProduct.getBrandId()+",";
												if( StringUtils.isEmpty(brandIds) ){
													brandState=brandState+1;	
												}
												if( (","+brandIds).indexOf(productBrandId)==-1 ){
													brandState=brandState+1;
												}
										}
										groupDetail.setProductCode(baseProduct.getProductCode());
										// 组装产品模型地址
										String modelId = baseProductService.getU3dModelId(mediaType, baseProduct);
										if( StringUtils.isNotBlank(modelId) ){
											ResModel resModel = resModelService.get(Integer.valueOf(modelId));
											if( resModel != null ){
												File modelFile = new File(Tools.getRootPath(resModel.getModelPath(),"")+resModel.getModelPath());
												if( modelFile.exists() ){
													groupDetail.setU3dModelPath(resModel.getModelPath());
												}
											}
										}
	
										// 产品大类信息
										String typeValue = baseProduct.getProductTypeValue();
										if( StringUtils.isNotBlank(typeValue) ){
											SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
											groupDetail.setProductTypeValue(dlDic.getValue());
											groupDetail.setProductTypeCode(dlDic.getValuekey());
											groupDetail.setProductTypeName(dlDic.getName());
	
											// 产品小类
											Integer smallType = baseProduct.getProductSmallTypeValue();
											if( smallType != null && smallType.intValue() > 0 ){
												SysDictionary xlDic = sysDictionaryService.findOneByTypeAndValue(dlDic.getValuekey(), smallType);
												groupDetail.setProductSmallTypeValue(xlDic.getValue().toString());
												groupDetail.setProductSmallTypeCode(xlDic.getValuekey());
												groupDetail.setProductSmallTypeName(xlDic.getName());
	
												String rootType = StringUtils.isEmpty(xlDic.getAtt1()) ? "2" : xlDic.getAtt1().trim();
												groupDetail.setRootType(rootType);
											}
										}
										groupDetail.setParentProductId(baseProduct.getParentId());
									
										// 长宽高
										groupDetail.setProductWidth(baseProduct.getProductWidth());
										groupDetail.setProductHeight(baseProduct.getProductHeight());
										groupDetail.setProductLength(baseProduct.getProductLength());
	
										// 父类产品分类信息
										if( baseProduct.getParentId() != null && baseProduct.getParentId().intValue() > 0 ){
											BaseProduct parentProduct = baseProductService.get(baseProduct.getParentId());
											if( parentProduct != null ){
												String parentTypeValue = parentProduct.getProductTypeValue();
												if( StringUtils.isNotBlank(parentTypeValue) ){
													SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(parentTypeValue));
													groupDetail.setParentTypeValue(dlDic.getValue());
													groupDetail.setParentTypeCode(dlDic.getValuekey());
													groupDetail.setParentTypeName(dlDic.getName());
												}
											}
											groupDetail.setParentProductId(baseProduct.getParentId());
										}
	
										// 长宽高
										groupDetail.setProductWidth(baseProduct.getProductWidth());
										groupDetail.setProductHeight(baseProduct.getProductHeight());
										groupDetail.setProductLength(baseProduct.getProductLength());

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
													splitTextureDTOList=new ArrayList<SplitTextureDTO>();
													List<ResTextureDTO> resTextureDTOList=new ArrayList<ResTextureDTO>();
													SplitTextureDTO splitTextureDTO=new SplitTextureDTO("1", "", null);
													ResTextureDTO resTextureDTO=resTextureService.fromResTexture(resTexture);
													groupDetail.setMaterialPicPaths(new String[]{resTextureDTO.getPath()});
													resTextureDTO.setKey(splitTextureDTO.getKey());
													resTextureDTO.setProductId(baseProduct.getId());
													resTextureDTOList.add(resTextureDTO);
													splitTextureDTO.setList(resTextureDTOList);
													splitTextureDTOList.add(splitTextureDTO);
												}
											}
										}
										groupDetail.setIsSplit(isSplit);
										groupDetail.setSplitTexturesChoose(splitTextureDTOList);
										/*处理拆分材质产品返回默认材质->end*/
										 /***在组合产品查询列表 中 增加产品属性****/
										Map<String,String> map = new HashMap<String,String>();
										map = productAttributeService.getPropertyMap(baseProduct.getId());
										baseProduct.setPropertyMap(map);
										
										// 产品规则
										Map<String,String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
												baseProduct.getProductTypeMark(),baseProduct.getProductSmallTypeMark(),spaceCommonId,null,new DesignRules(),map);
										groupDetail.setRulesMap(rulesMap);
										if( groupProduct_ != null ) {
											groupDetail.setProductStructureId(groupProduct_.getProductStructureId());
										}
									}
									details.add(groupDetail);
								}
							}

							
							if(structureId!=null&&structureId.intValue()>0){
								groupProduct.setStructureProductList(details);
							}
							if(structureId==null||structureId.intValue()<=0){
								groupProduct.setGroupProductDetails(details);
							}
							/*补充信息*/
							groupProduct.setProductName(groupProduct.getGroupName());
							Double salePrice=groupProductService.getSalePrice(groupProduct.getGroupId());
							groupProduct.setSalePrice(salePrice);
							/*识别是否是结构组合->是->得到对应结构的id*/
							/*先从memoryMap中找(防止重复访问数据库)*/
							Map<Integer,Integer> memoryMap=new HashMap<Integer, Integer>();
							if(memoryMap.containsKey(entity.getId())){
								groupProduct.setProductStructureId(memoryMap.get(entity.getId()));
							}else{
								/*如果该组合是结构组合,找对应的结构id*/
								if(groupProduct!=null){
									Integer structureId_=entity.getStructureId();
									if(structureId_!=null&&structureId_>0){
										groupProduct.setProductStructureId(structureId_);
										memoryMap.put(entity.getId(), structureId_);
									}
								}
							}
							/*识别是否是结构组合->是->得到对应结构的id->end*/
							/*补充品名品名*/
							GroupProductDetailsSearch groupProductDetailsSearch=new GroupProductDetailsSearch();
							groupProductDetailsSearch.setStart(0);
							groupProductDetailsSearch.setLimit(1);
							groupProductDetailsSearch.setGroupId(groupProduct.getGroupId());
							groupProductDetailsSearch.setIsMain(new Integer(1));
							List<GroupProductDetails> groupProductDetailsMain=groupProductDetailsService.getPaginatedList(groupProductDetailsSearch);
							if(groupProductDetailsMain!=null&&groupProductDetailsMain.size()>0){
								Integer productId=groupProductDetailsMain.get(0).getProductId();
								BaseProduct baseProduct=baseProductService.get(productId);
								Integer brandId=baseProduct.getBrandId();
								if(brandId!=null){
									BaseBrand baseBrand=baseBrandService.get(brandId);
									if(baseBrand!=null&&baseBrand.getId()>0)
										groupProduct.setBrandName(baseBrand.getBrandName());
								}
							}
							/*补充信息->end*/
														
							/**有一条品牌 没被绑定，整个组合价格 设为0*/
							if(brandState>0){
								groupProduct.setSalePrice(-1.0);
							}
							
							resultList.add(groupProduct);
						}
					}
				}
			}
		}
		return new ResponseEnvelope<SearchProductGroupResult>(total,resultList,productCategoryRel.getMsgId());
	}
	
}
