package com.nork.product.controller.web;

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

import net.sf.json.JSONArray;

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
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.cache.AuthorizedConfigCacher;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.ProductColors;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductCollectService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.product.service.ProductColorsService;
import com.nork.product.service.ProductRecommendationService;
import com.nork.product.service.StructureProductService;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.service.ProductPropsService;
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
@RequestMapping("/{style}/web/product/productCategoryRel_xiao")
public class WebProductCategoryRelController_Xiao {
	private static Logger logger = Logger
			.getLogger(WebProductCategoryRelController_Xiao.class);
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
	private DesignTempletService designTempletService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
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
	private ProductRecommendationService productRecommendationService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private ProductPropsService productPropsService;
	@Autowired
	private StructureProductService structureProductService;
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
     *
     * @param request
     * @param productCategoryRel
     * @return Object
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchProduct")
    @ResponseBody
    public Object searchProduct(@PathVariable String style,@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel
			,HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "houseType",required = false)String houseType,
								@RequestParam(value = "designPlanId",required = false)Integer designPlanId,
								@RequestParam(value = "planProductId",required = false)Integer planProductId,
								@RequestParam(value = "spaceCommonId",required = false)Integer spaceCommonId,
								@RequestParam(value = "templateProductId",required = false)String templateProductId,
								@RequestParam(value = "productTypeValue",required = false)String  productTypeValue,
								@RequestParam(value = "smallTypeValue",required = false)String  smallTypeValue,
								@RequestParam(value = "queryType",required = false)String queryType,
								@RequestParam(value = "productModelNumber",required = false)String productModelNumber) {
		if("0".equals(templateProductId)){
			templateProductId="";
		}
    	Long startTime=System.currentTimeMillis();
    	Long startTime2 = 0L;
    	String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productCategoryRel = (ProductCategoryRelSearch)JsonUtil.getJsonToBean(jsonStr, ProductCategoryRelSearch.class);
		}
		String msg = "";
		if(StringUtils.isBlank(productCategoryRel.getCategoryCode())){
			msg = "参数categoryCode不能为空";
			return new ResponseEnvelope<ProductCategoryRel>(false, msg,productCategoryRel.getMsgId());
		}
		DesignPlan designPlan = new DesignPlan();
		if( designPlanId != null && designPlanId != 0 ) {
			if(Utils.enableRedisCache()){
				designPlan = DesignCacher.get(designPlanId);
			}else{
				designPlan = designPlanService.get(designPlanId);
			}
		}
		if( designPlan == null ){
			msg = "找不到该设计方案："+designPlanId;
			return new ResponseEnvelope<ProductCategoryRel>(false, msg,productCategoryRel.getMsgId());
		}
        LoginUser loginUser = new LoginUser();
        if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
            loginUser.setId(-1);
            loginUser.setLoginName("nologin");
        } else {
            loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
        }


        //1.通过序列号过滤产品数据（1.通过公司关联业务明细，寻找业务需要过滤的小类；2.去掉该小类的产品数据）
    	/*获取黑名单配置信息*/
    	/*获得黑名单配置开关*/
        Integer categoryBlacklistEnable=0;
    	//Integer categoryBlacklistEnable=Integer.valueOf(Utils.getValue("categoryBlacklistEnable", "0"));
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "categoryBlacklistEnable");
		//
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
    		//存储去掉的小类信息
    		productCategoryRel.setBlacklistSet(blacklist);//[xingx, 1]
    	}
    	/*获取黑名单配置信息->end*/



		//建材家居搜索多个类型查询方式
		if( productCategoryRel.getCategoryCode().contains(",") ){
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		}else{
			productCategoryRel.setCategoryCode("."+productCategoryRel.getCategoryCode()+".");
		}

		//加入产品编码或型号搜索条件
		if( StringUtils.isNotBlank(productModelNumber) ){
			productCategoryRel.setProductModelNumber(productModelNumber.trim());
		}
		productCategoryRel.setSpaceCommonId(spaceCommonId);
		productCategoryRel.setProductTypeValue(productTypeValue);

		//获取当前产品的大类和小类,可以删除
		// 获取产品大小类信息(配置写法去掉.修复数据字典中小类和白模_小类的编码一致.beij，basic_beij)。排序时使用
		SysDictionary bigSd = null ;
		SysDictionary bmSmallSd = null ;
		SysDictionary smallSd = null ;
		if( StringUtils.isNotBlank(productTypeValue) ){
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue));
		}
		if( bigSd != null && StringUtils.isNotBlank(smallTypeValue)){
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(), Integer.valueOf(smallTypeValue));
			//白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
			String productType = Utils.getValue("app.product.typeInconformity", "");
			boolean isInconformity = Utils.isMateProductType(bmSmallSd==null?"":bmSmallSd.getValuekey(),productType);
			if( bmSmallSd != null && "baimo".equals(bmSmallSd.getAtt3()) && !isInconformity){
				smallSd = sysDictionaryService.findOneByTypeAndValueKey(bigSd.getValuekey(), Utils.getTypeValueKey(bmSmallSd.getValuekey()));
				if( smallSd == null ){
					smallSd = bmSmallSd;
				}
			}else{
				smallSd = bmSmallSd;
			}
			if(smallSd != null){
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			}
		}

		//2.获取当前房间类型,方便后续判断产品是否在当前房间合适,
		int total = 0;
 		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
 		try{
            if(loginUser.getUserType()==1 ){
            	productCategoryRel.setIsInternalUser("yes");
            }
            //用户Id
            productCategoryRel.setUserId(loginUser.getId());
            DesignPlanProduct designPlanProduct = null;
			Integer templatePlanProductId = -1;
			Integer productId = -1;
			BaseProduct productSelected = null;
 			/*设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)hsb*/
			if(StringUtils.isNotBlank(houseType)){
				/*对应出productHouseType的数据字典的value*/
				SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
				sysDictionarySearch.setAtt1(houseType);
				sysDictionarySearch.setType("productHouseType");
				List<SysDictionary> sysDictionaryList=sysDictionaryService.getPaginatedList(sysDictionarySearch);
				List<String> productHouseTypeList=new ArrayList<String>();//过滤条件
				List<String> productHouseTypeList3=new ArrayList<String>();
				if(sysDictionaryList!=null&&sysDictionaryList.size()>0){
					for(SysDictionary sysDictionary:sysDictionaryList){
						productHouseTypeList.add(""+sysDictionary.getValue());
						productHouseTypeList3.add(""+sysDictionary.getValue());
					}
				}
				/*得到产品空间类型valueList*/
				/*取白膜的productHouseType*/
				if( planProductId != null && planProductId != 0 ) {
					designPlanProduct = designPlanProductService.get(planProductId);
					if( designPlanProduct == null ){
						msg = "找不到该设计方案产品："+planProductId;
		            	return new ResponseEnvelope<ProductCategoryRel>(false, msg,productCategoryRel.getMsgId());
					}
					Integer baimoProductId=designPlanProduct.getInitProductId();
					BaseProduct baimoProduct=baseProductService.get(baimoProductId);
					if(baimoProduct!=null){
						String productHouseTypesStr=baimoProduct.getHouseTypeValues();
						if(StringUtils.isNotBlank(productHouseTypesStr)){
							List<String> productHouseTypeList2=Utils.getListFromStr(productHouseTypesStr);
							productHouseTypeList.retainAll(productHouseTypeList2);
						}
					}
				}
				if(productHouseTypeList.size()>0){
					productCategoryRel.setHouseTypeList(productHouseTypeList);
				}else{
					productCategoryRel.setHouseTypeList(productHouseTypeList3);
				}
			}
 			/*设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->end*/

            //是否为空房模式
            boolean emptyModel = false;
 			//获取设计方案中的样板房中的产品信息
			if( planProductId != null && planProductId != 0 ) {
				logger.warn("designPlanProduct.getProductId()-----------------"+designPlanProduct.getProductId());
				if(designPlanProduct.getProductId() != null && designPlanProduct.getProductId()>0){
					productId = designPlanProduct.getProductId();
					if(Utils.enableRedisCache()){
						productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
					}else{
						productSelected = baseProductService.get(designPlanProduct.getProductId());
					}
					//获取查询属性产品的条件
					productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,designPlanProduct.getProductId());
				}
				productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
				productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
				templatePlanProductId = designPlanProduct.getPlanProductId();
				productCategoryRel.setDesignProductId(templatePlanProductId);
//				productCategoryRel.setTemplateProductId(String.valueOf(designPlanProduct.getInitProductId()));
				if( StringUtils.isBlank(templateProductId) ){
					templateProductId = productSelected.getBmIds();
				}
				if( StringUtils.isNotBlank(templateProductId) ){
					String[] arraytemplateProductId = templateProductId.split(",");
					productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
				}
				
				DesignTemplet designTemplet = null;
				if(Utils.enableRedisCache()){
					designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
				}else{
					designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
				}
				if(designTemplet ==null){
					logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId()+",designId=" + designPlan.getId());
				}else{
					if(StringUtils.isEmpty(designTemplet.getDesignCode())){
						logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()+",designId=" + designPlan.getId());
					}
				}
				if(!StringUtils.isEmpty(designTemplet.getDesignCode()) && designTemplet.getDesignCode().endsWith("_000")){
					emptyModel = true;
				}else{
					emptyModel = false;
				}
			}
			logger.warn("房间类型为houseType=" + houseType + ";" + "\n"
							+ "通用空间主键spaceCommonId=" + spaceCommonId + ";" + "\n"
							+ "设计方案主键designPlanId=" + designPlanId + ";" + "\n"
							+ "当前选中的产品在设计方案产品列表中的数据主键designPlanProduct_ProductId=" + planProductId + ";" + "\n"
							+ "当前选中的产品的数据主键ProductId=" + productId + ";" + "\n"
							+ "当前选中的产品在样本房列表中的数据主键templateProductId=" + templatePlanProductId + ";" + "\n"
							+ "当前选中的产品在样本房列表中的产品主键templateProduct_ProductId=" + templateProductId + ";" + "\n"
							+ "当前选中产品的大类productTypeValue=" + productTypeValue + ";" + "\n"
							+ "当前选中产品的小类smallTypeValue=" + smallTypeValue + ";" + "\n"
							+ "查询类型queryType=" + queryType + ";"
			);
			
			//设计方案对应的样板房白模产品信息
			BaseProduct baimoProduct = new BaseProduct();
			if( designPlanProduct != null ){
				if( designPlanProduct.getInitProductId() != null && designPlanProduct.getInitProductId().intValue() > 0 ){
					if(Utils.enableRedisCache()){
						baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
					}else{
						baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
					}
				}else{
					if(Utils.enableRedisCache()){
						baimoProduct = BaseProductCacher.get(Utils.getIntValue(templateProductId));
					}else{
						baimoProduct = baseProductService.get(Utils.getIntValue(templateProductId));
					}
				}
			}
			//是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
			//空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
			String onlyShowRecommend = Utils.getValue("onlyShowRecommend","false");
			String exceptRecommend = Utils.getValue("exceptRecommend","false");
			boolean flag = false;//只显示推荐产品
			if( "false".equals(exceptRecommend) && "true".equals(onlyShowRecommend) ){
				flag = true;
			}
			//三 1.判断是否是背景墙，根据长宽推荐合适的产品，在上下10cm内的背景墙都查出来,2 、判断是否是特殊小分类, 3、特殊分类过滤长宽高
			String onlyShowCustomization = null;
			if(StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
					&& new Integer(productTypeValue).intValue() >0 &&  new Integer(smallTypeValue).intValue() >0){
				String bjType = Utils.getValue("app.smallProductType.stretch", "");
				boolean isShowbgWall = Utils.isMateProductType(smallSd==null?"":smallSd.getValuekey(),bjType);
				if( isShowbgWall ){
					onlyShowCustomization = "true";
				}
				productCategoryRelService.bgWallAndSpecialTypeInfo(productCategoryRel,isShowbgWall,bigSd,smallSd,baimoProduct,productSelected);
			}

			/*//ture只显示推荐，false显示所有分类相关数据
			if( "true".equals(onlyShowRecommend) ){
				productCategoryRel.setOnlyShowRecommend(true);
			}else{
				productCategoryRel.setOnlyShowRecommend(false);
			}
			//true排除推荐，false不排除
			if( "true".equals(exceptRecommend) ){
				productCategoryRel.setExceptRecommend(true);
			}else{
				productCategoryRel.setExceptRecommend(false);
			}*/

			//4.获取该用户绑定的序列号品牌，通过上面获取的参数，将不符合条件的数据排除（可能是大类、小类或者品牌对应的产品列表）
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			if( Utils.enableRedisCache() ){
				authorizedList = AuthorizedConfigCacher.getList(authorizedConfig);
			}else{
				authorizedList = authorizedConfigService.getList(authorizedConfig);
			}
//			String brandIds = "";
//			for(AuthorizedConfig ac : authorizedList){
//				if( StringUtils.isNotBlank(ac.getBrandIds()) ){
//					brandIds += ac.getBrandIds()+",";
//				}
//			}
//			//如果是厂商，则只能查询这个厂商品牌下的产品
//			if( 3 == loginUser.getUserType() ){
//				if(StringUtils.isNotBlank(brandIds)){
//					String[] arrayBrandId = brandIds.split(",");
//					productCategoryRel.setBrandIds(Arrays.asList(arrayBrandId));
//				}
//			}
			/*add品牌,大类,小类,产品查询条件*/
			List<BaseProduct> baseProductList=new ArrayList<BaseProduct>();
			if(3 == loginUser.getUserType()){
				if(authorizedList!=null&&authorizedList.size()>0){
					for(AuthorizedConfig authorizedConfigItem:authorizedList){
						/*查询条件注入到BaseProduct类中*/
						BaseProduct baseProduct=baseProductService.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
						baseProductList.add(baseProduct);
					}
					productCategoryRel.setBaseProduct(baseProductList);
				}
			}
			/*add品牌,大类,小类,产品查询条件->end*/
			Long endTime = System.currentTimeMillis();
		    logger.warn("分类搜索查询结果之前消耗时间times:"+(endTime-startTime));
			List<CategoryProductResult> customizeList = new ArrayList<>();//定制
		    List<CategoryProductResult> recommendList = new ArrayList<>();//推荐和分类
		    int customizeCount = 0;
	    	int recommendCount = 0 ;
	    	int limit = productCategoryRel.getLimit();
	    	int start = productCategoryRel.getStart();
		    //查询定制产品
		    if("false".equals(onlyShowRecommend) && spaceCommonId != null ){
				//墙面在搜索中的逻辑
		    	if("qiangm".equals(bigSd==null?"":bigSd.getValuekey())){
					productCategoryRelService.getWallTypeLogic(productCategoryRel,designPlanProduct,bigSd,request);
		    	}else if("tianh".equals(bigSd==null?"":bigSd.getValuekey())){
		    		//如果查天花只显示定制产品
					onlyShowCustomization = "true";
		    	}
		    	//非定制只查询推荐产品
		    	if( flag ){
					//推荐逻辑可以删除
		    		if(Utils.enableRedisCache()){
			    		recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
			    	}else{
			    		/*新过滤方案(done)*/
						recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
			    	}
		    	}else{
		    		 if( !"true".equals(onlyShowCustomization) ){
				    	 if(Utils.enableRedisCache()){
				    		 recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
				    	 }else{
				    		 /*新过滤方案(done)*/
							 recommendCount = productCategoryRelService.findRecommendCategoryProductResultCount(productCategoryRel);
				    	 }
		    		 }
					//定制类产品的数据获取
			    	if( Utils.enableRedisCache() ){
			    		customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
					}else{
						/*新过滤方案(done)*/
						customizeCount = productCategoryRelService.findCustomizedCategoryProductResultCount(productCategoryRel);
					}
		    	}
		    	//如果是厂商，则只能查询这个厂商品牌下的产品,没有则查询除其外所有品牌的产品
		    	if( 3 == loginUser.getUserType() && customizeCount + recommendCount == 0 ){
		    	/*if(customizeCount + recommendCount == 0 ){*/
//		    		productCategoryRel.setBrandIds(null);
		    		/*add品牌,大类,小类,产品*/
		    		/*针对搜索条件(大类同,小类异的情况->返回空列表)*/
		    		boolean falg2=false;
		    		String productTypeValue2="0";
	    			if(StringUtils.equals(productTypeValue.trim(), "0")){
	    				SysDictionary sysDictionaryBigType=sysDictionaryService.findOneByValueKeyInCache(productCategoryRel.getCategoryCode().replace(".", ""));
	    				if(!StringUtils.equals("productType", sysDictionaryBigType.getType())){
	    					sysDictionaryBigType=sysDictionaryService.findOneByTypeAndValueKey("productType", sysDictionaryBigType.getType());
	    				}
	    				if(sysDictionaryBigType!=null)
	    					productTypeValue2=sysDictionaryBigType.getValue()+"";
	    			}else{
	    				productTypeValue2=productTypeValue.trim();
	    			}
	    			//通过序列号过滤数据（当序列号存在该大类、小类产品或品牌时，只显示该品牌或该大类或小类的产品，如果不存在则查询所有数据）？？
		    		for(BaseProduct baseProduct:baseProductList){
		    			/*识别序列号有没有和productTypeValue相同的大类*/
		    			logger.info("------productTypeValue2:"+productTypeValue2+";baseProduct.getProductTypeValue():"+baseProduct.getProductTypeValue());
		    			if(StringUtils.equals(productTypeValue2, baseProduct.getProductTypeValue())){
		    				falg2=true;
		    				break;
		    			}
		    		}
		    		if(!falg2){
		    			productCategoryRel.setBaseProduct(null);
		    		}
		    		/*add品牌,大类,小类,产品->end*/
		    		if( flag ){
		    			if(Utils.enableRedisCache()){
				    		recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
				    	}else{
				    		/*新过滤方案(done)*/
							recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
				    	}
			    	}else {
			    		if( !"true".equals(onlyShowCustomization) ){
				    		if(Utils.enableRedisCache()){
					    		recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
				    		}else{
				    			/*新过滤方案(done)*/
								recommendCount = productCategoryRelService.findRecommendCategoryProductResultCount(productCategoryRel);
				    		}
			    		}
			    		if( Utils.enableRedisCache() ){
			    			customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
			    		}else{
			    			/*新过滤方案(done)*/
			    			customizeCount = productCategoryRelService.findCustomizedCategoryProductResultCount(productCategoryRel);
			    		}
		    		}
		    	}
		    	if(!flag){
		    		if( customizeCount > 0 ){
		    			if( Utils.enableRedisCache() ){
		    				customizeList = ProductCategoryRelCacher.getListCustomizedCategoryCode(productCategoryRel);
		    			}else{
		    				/*新过滤方案(done)*/
		    				customizeList = productCategoryRelService.findCustomizedCategoryProductResult(productCategoryRel);
		    			}
		    		}
		    	}
		    }else{
		    	if( flag ){
	    			if(Utils.enableRedisCache()){
			    		 recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
			    	}else{
			    		/*新过滤方案(done)*/
						 recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
			    	}
		    	}else{
		    		if(Utils.enableRedisCache()){
		    			recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
		    		}else{
		    			/*新过滤方案(done)*/
		    			recommendCount = productCategoryRelService.findRecommendCategoryProductResultCount(productCategoryRel);
		    		}
		    	}
				//如果厂商没有搜索到该品牌的产品，则搜索产品分类下所有的产品
	    		if(/* 3 == loginUser.getUserType() && */recommendCount == 0 ){
	    			productCategoryRel.setBrandIds(null);
	    			if( flag ){
		    			if(Utils.enableRedisCache()){
				    		 recommendCount = ProductCategoryRelCacher.getRecommendCount(productCategoryRel);
				    	}else{
				    		/*新过滤方案(done)*/
							 recommendCount = productCategoryRelService.getRecommendResultCount(productCategoryRel);
				    	}
			    	}else{
		    			if( Utils.enableRedisCache() ){
				    		recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
		    			}else{
		    				/*新过滤方案(done)*/
							recommendCount = productCategoryRelService.findRecommendCategoryProductResultCount(productCategoryRel);
		    			}
			    	}
	    		}
		    }


		    if( "true".equals(onlyShowCustomization) ){
		    	list.addAll(customizeList);
		    }else{
			    if( flag ){//只查推荐吧
			    	 if( Lists.isNotEmpty(customizeList) && customizeList.size() > 0 ){
				    	list.addAll(customizeList);
			    		if( Utils.enableRedisCache() ){
			    			recommendList = ProductCategoryRelCacher.getRecommendList(productCategoryRel);
			    		}else{
			    			/*新过滤方案(done)*/
			    			recommendList = productCategoryRelService.getRecommendResult(productCategoryRel);
			    		}
			    		list.addAll(recommendList);
				    }else{
				    	if( Utils.enableRedisCache() ){
				    		list = ProductCategoryRelCacher.getRecommendList(productCategoryRel);
				    	}else{
				    		/*新过滤方案(done)*/
				    		list = productCategoryRelService.getRecommendResult(productCategoryRel);
				    	}
				    }
			    }else{
			    	//查询推荐和分类
				    if( Lists.isNotEmpty(customizeList) && customizeList.size() > 0 ){
				    	list.addAll(customizeList);
		//		    	if( customizeCount < limit + start ){
		//		    		productCategoryRel.setStart(0);
		//		    		productCategoryRel.setLimit(limit - (customizeCount % limit));
				    		if( Utils.enableRedisCache() ){
				    			recommendList = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
				    		}else{
				    			/*新过滤方案(done)*/
				    			recommendList = productCategoryRelService.findRecommendCategoryProductResult(productCategoryRel);
				    		}
				    		list.addAll(recommendList);
		//		    	}
				    }else{
	//			    	if( customizeCount != 0 && customizeCount < limit + start ){
	//			    		int starts = start+(limit-(customizeCount%limit))-limit;
	//			    		productCategoryRel.setStart(starts<0?0:starts);
	//			    	}
				    	if( Utils.enableRedisCache() ){
				    		list = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
				    	}else{
				    		/*新过滤方案(done)*/
				    		list = productCategoryRelService.findRecommendCategoryProductResult(productCategoryRel);
				    	}
				    }
			    }
		    }


		    //排序 推荐 小类 匹配度 使用量 色系
	    	list = getProductList(list,productCategoryRel);
/*		    if( Utils.enableRedisCache() ){
		    	total = ProductCategoryRelCacher.getCategoryProductCount(productCategoryRel);
		    }else{
		    	total = productCategoryRelService.getCategoryProductCount(productCategoryRel);
		    }
		    //如果厂商没有搜索到该品牌的产品，则搜索产品分类下所有的产品
    		if( 3 == loginUser.getUserType() && total == 0){
    			productCategoryRel.setBrandIds(null);
    			if( Utils.enableRedisCache() ){
    				total = ProductCategoryRelCacher.getCategoryProductCount(productCategoryRel);
    			}else{
					total = productCategoryRelService.getCategoryProductCount(productCategoryRel);
    			}
	    	}
		    if( Utils.enableRedisCache() ){
			    list = ProductCategoryRelCacher.getCategoryProductResult(productCategoryRel);
		    }else{
		    	list = productCategoryRelService.getCategoryProductResult(productCategoryRel);
		    }
		    if( 3 == loginUser.getUserType() && total == 0){
    			productCategoryRel.setBrandIds(null);
    			if( Utils.enableRedisCache() ){
    			    list = ProductCategoryRelCacher.getCategoryProductResult(productCategoryRel);
    		    }else{
    		    	list = productCategoryRelService.getCategoryProductResult(productCategoryRel);
    		    }
	    	}
		    */
		    
		    total = recommendCount+customizeCount;
	        /*分页*/
	        list = Utils.paging(list,start,limit);
		    endTime = System.currentTimeMillis();
		    logger.warn("分类搜索查询结果之后消耗时间times:"+(endTime-startTime));
		    startTime2 = System.currentTimeMillis();

			//床通过床架获取床垫和床品的白模，通过配置确定床还是其他物品
			//获取需要自动携带白模产品的配置
			Map<String,AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
			String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app","design.designPlan.autoCarryBaimoProducts","");
			if( StringUtils.isNotBlank(autoCarryBaimoProducrsConfig) ){
				JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
				try {
					List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
					if( autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0 ) {
						for( AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs ){
							autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(),autoCarryBaimoProductsConfig);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.error("获取自动携带白模产品配置异常！");
				}
			}

			for(CategoryProductResult productResult : list){
				BaseProduct baseProduct=null;
				if(Utils.enableRedisCache()){
					baseProduct = BaseProductCacher.get(productResult.getProductId());
				}else{
					baseProduct = baseProductService.get(productResult.getProductId());
				}
				if(baseProduct==null){
					logger.info("baseProduct is null;productId="+productResult.getProductId()+";");
					continue;
				}
				//7.获取产品的一些客户端特殊信息传输过去，可以考虑独立以后给客户端调用
				productResult = baseProductService.decorationProductInfo(productResult,baseProduct, designPlan, designPlanProduct, autoCarryMap, request);
			}
			/**列表排序,同小类的排在前面，同大类其次，剩余的排最后*/
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductCategoryRel>(false, "数据异常!",productCategoryRel.getMsgId());
		}
 		
        Long endTime2=System.currentTimeMillis();
	    return new ResponseEnvelope<CategoryProductResult>(total,list,productCategoryRel.getMsgId());
    } 
    
    //按搜索产品属性匹配度排序
    public List<CategoryProductResult> getProductList(List<CategoryProductResult> list,ProductCategoryRel productCategoryRel){
    	List<ProductProps> propsOrderList = productCategoryRel.getPropsOrderList();
		if (Lists.isNotEmpty(propsOrderList)) {
			//搜索产品的排序属性排序
			ComparatorP cpmparator = new ComparatorP();
	 		Collections.sort(propsOrderList, cpmparator);
			for(CategoryProductResult productResult : list){
				BaseProduct baseProduct=null;
				if(Utils.enableRedisCache()){
					baseProduct = BaseProductCacher.get(productResult.getProductId());
				}else{
					baseProduct = baseProductService.get(productResult.getProductId());
				}
				if(baseProduct==null){
					logger.info("baseProduct is null;productId="+productResult.getProductId()+";");
					continue;
				}
				Map<String,String> map = new HashMap<String,String>();
				map = productAttributeService.getPropertyOrderMap(baseProduct.getId());
				if( map.size()==0 ){
					productResult.setProductOrdering(99999999);
					continue;
				}
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
				return unity2.getProductCount().compareTo(unity1.getProductCount());	
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
	

}
