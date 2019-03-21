package com.nork.product.controller.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

/**
 * @version V1.0
 * @Title: ProductCategoryRelController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品与产品类目关联Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Controller
@RequestMapping("/{style}/web/product/productCategorySearch")
public class WebProductCategorySearchController {
    private static Logger logger = Logger
            .getLogger(WebProductCategorySearchController.class);
    private final JsonDataServiceImpl<ProductCategoryRel> JsonUtil = new JsonDataServiceImpl<ProductCategoryRel>();
    private final String STYLE = "online";
    private final String JSPSTYLE = "online";
    private final String MAIN = "/" + STYLE + "/product/proCategory";
    private final String BASEMAIN = "/" + STYLE + "/product/proCategory";
    private final String JSPMAIN = "/" + JSPSTYLE + "/product/proCategory";

    @Autowired
    private ProductCategoryRelService productCategoryRelService;

    @Autowired
    private BaseProductService baseProductService;

    @Autowired
    private BaseBrandService baseBrandService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private ProCategoryService categoryService;
    
    @Autowired
    private ResPicService resPicService;

    @Autowired
    private DesignPlanService designPlanService;

    @Autowired
    private SpaceCommonService spaceCommonService;

    @Autowired
    private DesignTempletProductService designTempletProductService;

    @Autowired
    private DesignPlanProductService designPlanProductService;

    @Autowired
    private ResModelService resModelService;

    @Autowired
    private DesignTempletService designTempletService;

    @Autowired
    private ResFileService resFileService;
    
	@Autowired
	private AuthorizedConfigService authorizedConfigService; 

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 根据分类查询产品
     *
     * @param request
     * @param productCategoryRel
     * @return
     */
    @RequestMapping(value = "/searchProduct")
    public Object searchProduct(HttpServletRequest request, @ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel,
                                @RequestParam(value = "houseType",required = false)String houseType,
                                @RequestParam(value = "designPlanId",required = false)Integer designPlanId,
                                @RequestParam(value = "planProductId",required = false)Integer planProductId,
                                @RequestParam(value = "spaceCommonId",required = false)Integer spaceCommonId,
                                @RequestParam(value = "templateProductId",required = false)String templateProductId,
                                @RequestParam(value = "productTypeValue",required = false)String  productTypeValue,
                                @RequestParam(value = "smallTypeValue",required = false)String  smallTypeValue,
                                @RequestParam(value = "queryType",required = false)String queryType) {
    	logger.info("分类查找产品列表。。。");
        if( StringUtils.isNotBlank(houseType) ){
            productCategoryRel.setHouseTypeValues(houseType);
        }
        if( StringUtils.isNotBlank(houseType) ) {
            productCategoryRel.setHouseTypeValues(houseType);
        }
        int total = 0;
        List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
        //查询方式
        if( StringUtils.isNotBlank(productCategoryRel.getCategoryCode()) && "group".equals(queryType) ){
            String[] arr = productCategoryRel.getCategoryCode().split(",");
            productCategoryRel.setCategoryIdList(Arrays.asList(arr));
        }

        LoginUser loginUser = new LoginUser();
        if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
            loginUser.setId(-1);
            loginUser.setLoginName("nologin");
        } else {
            loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
        }
        productCategoryRel.setUserId(loginUser.getId());
        productCategoryRel.setSpaceCommonId(spaceCommonId);
        if( StringUtils.isNotBlank(templateProductId) ){
			String arrayProId[] = templateProductId.split(",");
			productCategoryRel.setTemplateProductId(Arrays.asList(arrayProId));
		}
        DesignPlan designPlan = new DesignPlan();
        //是否为空房模式
        boolean emptyModel = false;
        if( designPlanId != null && designPlanId != 0 && planProductId != null && planProductId != 0 ) {
            designPlan = designPlanService.get(designPlanId);
            DesignPlanProduct designPlanProduct = designPlanProductService.get(planProductId);
            if( designPlan != null && designPlanProduct != null ) {
                productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
                productCategoryRel.setDesignProductId(designPlanProduct.getPlanProductId());
                productCategoryRel.setProductId(designPlanProduct.getProductId());

                DesignTemplet designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
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

                logger.info("房间类型为houseType=" + houseType + ";" +"\n"
    					+ "通用空间主键spaceCommonId=" + spaceCommonId + ";"+"\n"
    					+ "设计方案主键designPlanId=" + designPlanId + ";"+"\n"
    					+ "当前选中的产品在设计方案产品列表中的数据主键designPlanProduct_ProductId=" + planProductId + ";"+"\n"
    					+ "当前选中的产品在样本房列表中的数据主键templateProductId=" + designPlanProduct.getPlanProductId() + ";"+"\n"
    					+ "当前选中的产品在样本房列表中的产品主键templateProduct_ProductId=" + templateProductId + ";"+"\n"
    					+ "当前选中产品的大类productTypeValue=" + productTypeValue + ";"+"\n"
    					+ "当前选中产品的小类smallTypeValue=" + smallTypeValue + ";"+"\n"
    					+ "查询类型queryType=" + queryType + ";"
    					);
            }
        }

        //如果是厂商，则只能查询这个厂商品牌下的产品
        if( 3 == loginUser.getUserType() ){
            if( StringUtils.isNotBlank(loginUser.getBrandIds()) ){
                String[] brandIds = loginUser.getBrandIds().split(",");
                productCategoryRel.setBrandIds(Arrays.asList(brandIds));
            }
        }

      //是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；空房模式，无推荐，显示推荐+同类型数据+排除推荐数据)
		String onlyShowRecommend = Utils.getValue("onlyShowRecommend","false");
		String exceptRecommend = Utils.getValue("exceptRecommend","false");
		
		if(StringUtils.isNotEmpty(productTypeValue) && StringUtils.isNotEmpty(smallTypeValue)
				&& new Integer(productTypeValue).intValue() >0 &&  new Integer(smallTypeValue).intValue() >0){
			//是否为硬装产品
			SysDictionary sysDictionary = new SysDictionary();
			sysDictionary.setValue(Integer.valueOf(productTypeValue));
			sysDictionary.setSmallValue(Integer.valueOf(smallTypeValue));
			sysDictionary =  sysDictionaryService.checkType(sysDictionary);
			//att4等于1表示为硬装产品
			if( "1".equals(sysDictionary.getAtt4()) || emptyModel ){
				onlyShowRecommend = "false";
			}

			//&& StringUtils.isNotBlank(productBaimoIds) && spaceCommonId != null
			//只显示推荐产品时，不需要大小类过滤
			if( "true".equals(onlyShowRecommend) ){
				productCategoryRel.setOnlyShowRecommend(true);
			}else{
				productCategoryRel.setOnlyShowRecommend(false);
				productCategoryRel.setProductTypeValue(productTypeValue);
				productCategoryRel.setProductSmallTypeValue(StringUtils.isEmpty(smallTypeValue)?-1:new Integer(smallTypeValue).intValue());
			}
			//排除推荐数据时
			if( "true".equals(exceptRecommend) ){
				productCategoryRel.setExceptRecommend(true);
			}else{
				productCategoryRel.setExceptRecommend(false);
			}
		}else{
			productCategoryRel.setOnlyShowRecommend(false);
			productCategoryRel.setExceptRecommend(true);
			productCategoryRel.setProductTypeValue(null);
			productCategoryRel.setProductSmallTypeValue(null);
		}
		/*序列号配置过滤*/
		Map<String,List<String>> map=authorizedConfigService.getConfigParams(loginUser.getId());
		productCategoryRel.setConfigBrandIdList(map.get("brandIdList"));
		productCategoryRel.setConfigProductIdList(map.get("productIdList"));
		productCategoryRel.setConfigSmallTypeIdList(map.get("smallTypeIdList"));
		productCategoryRel.setConfigTypeValueList(map.get("typeValueList"));
		/*序列号配置过滤END*/
        if( "group".equals(queryType) ){
            total = productCategoryRelService.findProductByCategoryCodeCount(productCategoryRel);//findProductByCategoryCode
            if( total > 0 ) {
                list = productCategoryRelService.findProductByCategoryCode(productCategoryRel);
            }
        }else{
            total = productCategoryRelService.findCategoryProductResultByLongCodeCount(productCategoryRel);
            if( total > 0 ) {
                list = productCategoryRelService.findCategoryProductResultByLongCode(productCategoryRel);
            }
        }

        //媒介类型.如果没有值，则表示为web前端（2）
        String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
        for( CategoryProductResult productResult : list ){
            BaseProduct baseProduct = baseProductService.get(productResult.getProductId());
            String modelId = baseProductService.getU3dModelId(mediaType, baseProduct);
            if(StringUtils.isNotEmpty(modelId)){
                ResModel resModel = resModelService.get(Integer.valueOf(modelId));
                if( resModel != null ){
                    productResult.setProductModelPath(resModel.getModelPath());
                }
            }
            String productTypeValue2 = baseProduct.getProductTypeValue();
            if( StringUtils.isNotBlank(productTypeValue2) ){
                SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue2));
                productResult.setProductTypeValue(productTypeSysDic.getValue());
                productResult.setProductTypeCode(productTypeSysDic.getValuekey());
                productResult.setProductTypeName(productTypeSysDic.getName());
                Integer productSmallTypeValue2 = baseProduct.getProductSmallTypeValue();
                if (productTypeSysDic.getValue() != null && productSmallTypeValue2 != null) {
                    SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue2);
                    productResult.setProductSmallTypeCode(productSmallTypeSysDic.getValuekey());
                    productResult.setProductSmallTypeName(productSmallTypeSysDic.getName());
                    productResult.setProductSmallTypeValue(productSmallTypeSysDic.getValue());

                    String rootType = StringUtils.isEmpty(productSmallTypeSysDic.getAtt1()) ? "2" : productSmallTypeSysDic.getAtt1().trim();
                    productResult.setRootType(rootType);
                }
            }
            //空间ID
            productResult.setSpaceCommonId(baseProduct.getSpaceComonId());

            //产品材质路径
            String materialIds = productResult.getMaterialPicId();
            if(StringUtils.isNotBlank(materialIds)){
                String[] mIds = materialIds.split(",");
                if( mIds != null ){
                    ResPic resPic = new ResPic();
                    resPic.setFileKey("product.baseProduct.material");
                    resPic.setBusinessId(baseProduct.getId());
                    resPic.setOrders(" sequence asc ");
                    List<ResPic> materialPics = resPicService.getList(resPic);
                    if( materialPics != null && materialPics.size() > 0 ){
                        String[] materialPaths = new String[materialPics.size()];
                        for( int i=0;i<materialPics.size();i++ ){
                            resPic = materialPics.get(i);
                            if( resPic != null ) {
                                materialPaths[i] = resPic.getPicPath();
                            }
                        }
                        productResult.setMaterialPicPaths(materialPaths);
                    }
                }
            }
            //材质配置文件路径
            Integer materialConfigId = baseProduct.getMaterialFileId();
            if( materialConfigId != null ){
                ResFile resFile = resFileService.get(materialConfigId);
                if( resFile != null ) {
                    productResult.setMaterialConfigPath(resFile.getFilePath());
                }
            }

            productResult.setParentProductId(baseProduct.getParentId());
        }

        request.setAttribute("list", list);
        return Utils.getPageUrl(request, JSPMAIN + "/product_list");
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @RequestMapping(value = "/design")
    public Object design(HttpServletRequest request,HttpServletResponse response) {
         HttpSession session = request.getSession();
        if( session.getAttribute("CATEGORY_JSON") != null ){

        }else{
            ProCategory category = categoryService.get(1);
            List<SearchProCategorySmall> categoryList = recursionCategory(category);
            category.setChildrenNodes(categoryList);
            SearchProCategorySmall categorySmall = new SearchProCategorySmall();
            categorySmall.setAa(category.getId());
            categorySmall.setBb(category.getCode());
            categorySmall.setCc(category.getPid());
            categorySmall.setDd(category.getName());
            categorySmall.setFf(category.getChildrenNodes());
            JSONObject jsonObject = JSONObject.fromObject(categorySmall);
            session.setAttribute("CATEGORY", categorySmall);
            try {
				session.setAttribute("CATEGORY_JSON_DATA",URLEncoder.encode(jsonObject.toString(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
        return Utils.getPageUrl(request, JSPSTYLE + "/decorate/design");
    }

    @RequestMapping(value = "/searchCategory1")
    public Object searchCategory1(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("分类查询。。。");
        HttpSession session = request.getSession();
        if( session.getAttribute("CATEGORY") == null ){
            ProCategory category = categoryService.get(1);
            List<SearchProCategorySmall> categoryList = recursionCategory(category);
            category.setChildrenNodes(categoryList);
            SearchProCategorySmall categorySmall = new SearchProCategorySmall();
            categorySmall.setAa(category.getId());
            categorySmall.setBb(category.getCode());
            categorySmall.setCc(category.getPid());
            categorySmall.setDd(category.getName());
            categorySmall.setFf(category.getChildrenNodes());
            JSONObject jsonObject = JSONObject.fromObject(categorySmall);
            session.setAttribute("CATEGORY", categorySmall);
            try {
                session.setAttribute("CATEGORY_JSON_DATA",URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return Utils.getPageUrl(request, JSPSTYLE + "/product/proCategory/categoryDetail");
    }

    @RequestMapping(value = "/searchCategory")
    public Object searchCategory(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("分类查询。。。");
        HttpSession session = request.getSession();
        if( session.getAttribute("CATEGORY") == null ){
            ProCategory category = null; //categoryService.get(1);
            ProCategory pc = new ProCategory();
            pc.setOrder(" long_code");
            List<ProCategory> categoryAllList = categoryService.getList(pc);
            if(categoryAllList!= null && categoryAllList.size()>=0){
            	for(ProCategory ppc :categoryAllList){
            		if(ppc.getId().intValue() == 1){
            			category = ppc;
            			break;
            		}
            	}
            }
            List<SearchProCategorySmall> categoryList = recursionCategory2(category,categoryAllList);
            category.setChildrenNodes(categoryList);
            SearchProCategorySmall categorySmall = new SearchProCategorySmall();
            categorySmall.setAa(category.getId());
            categorySmall.setBb(category.getCode());
            categorySmall.setCc(category.getPid());
            categorySmall.setDd(category.getName());
            categorySmall.setFf(category.getChildrenNodes());
            JSONObject jsonObject = JSONObject.fromObject(categorySmall);
            session.setAttribute("CATEGORY", categorySmall);
            try {
                session.setAttribute("CATEGORY_JSON_DATA",URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return Utils.getPageUrl(request, JSPSTYLE + "/product/proCategory/categoryDetail");
    }
    
    @RequestMapping(value = "/getCategory")
    @ResponseBody
    public Object getCategory(HttpServletRequest request,HttpServletResponse response,String msgId) {
	    logger.info("获得分类查询。。。");
        ProCategory category = null;//categoryService.get(1);
        
        ProCategory pc = new ProCategory();
        pc.setOrder(" long_code ");
        List<ProCategory> categoryAllList = categoryService.getList(pc);
        if(categoryAllList!= null && categoryAllList.size()>=0){
        	for(ProCategory ppc :categoryAllList){
        		if(ppc.getId().intValue() == 1){
        			category = ppc;
        			break;
        		}
        	}
        }
        List<SearchProCategorySmall> categoryList = recursionCategory2(category,categoryAllList);
        category.setChildrenNodes(categoryList);
        SearchProCategorySmall categorySmall = new SearchProCategorySmall();
        categorySmall.setAa(category.getId());
        categorySmall.setBb(category.getCode());
        categorySmall.setCc(category.getPid());
        categorySmall.setDd(category.getName());
        categorySmall.setFf(category.getChildrenNodes());
        String obj = null;
  /*    JSONObject jsonObject = JSONObject.fromObject(categorySmall);
       try{
        	obj = URLEncoder.encode(jsonObject.toString(),"UTF-8");
        }catch(Exception e){
        	e.printStackTrace();
        }*/
        obj = new JsonDataServiceImpl<SearchProCategorySmall>().getBeanToJsonData(categorySmall);
        logger.info("getCategory=" + obj);
        return new ResponseEnvelope<SearchProCategorySmall>(categorySmall,msgId,true);
    }
    
    /**
     * 递归查询分类
     * @param category
     * @return
     */
    public List<SearchProCategorySmall> recursionCategory(ProCategory category){
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        ProCategorySearch search = new ProCategorySearch();
        search.setPid(category.getId());
        search.setLongCode(category.getLongCode());
        List<ProCategory> list = categoryService.getList(search);
        if( list != null && list.size() > 0 ){
            if( childrenNodes == null ){
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
            SearchProCategorySmall newCategory = null;
            for( ProCategory childrenNode : list ){
                newCategory = new SearchProCategorySmall();
                newCategory.setAa(childrenNode.getId());
                newCategory.setCc(childrenNode.getPid());
                newCategory.setDd(childrenNode.getName());
                newCategory.setBb(childrenNode.getCode());
                newCategory.setFf(recursionCategory(childrenNode));
                childrenNodes.add(newCategory);
            }
            category.setChildrenNodes(childrenNodes);
        }
        return childrenNodes;
    }
    

    /**
     * 递归查询分类
     * @param category
     * @return
     */
    public List<SearchProCategorySmall> recursionCategory2(ProCategory category,List<ProCategory> categoryAllList){
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        ProCategorySearch search = new ProCategorySearch();
        search.setPid(category.getId());
        search.setLongCode(category.getLongCode());
        List<ProCategory> list = new ArrayList<ProCategory>();
        if(categoryAllList!= null && categoryAllList.size()>0){
        	for(ProCategory pc:categoryAllList){
        		if(pc.getPid().intValue()==search.getPid().intValue() && pc.getLongCode().indexOf(category.getLongCode()) != -1){
        			list.add(pc);
        		}
        	}
        }else{
	        list = categoryService.getList(search);
	    }
        if( list != null && list.size() > 0 ){
            if( childrenNodes == null ){
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
        }
        SearchProCategorySmall newCategory = null;
        for( ProCategory childrenNode : list ){
            newCategory = new SearchProCategorySmall();
            newCategory.setAa(childrenNode.getId());
            newCategory.setCc(childrenNode.getPid());
            newCategory.setDd(childrenNode.getName());
            newCategory.setBb(childrenNode.getCode());
            newCategory.setFf(recursionCategory2(childrenNode,categoryAllList));
            childrenNodes.add(newCategory);
        }
        category.setChildrenNodes(childrenNodes);
        
        return childrenNodes;
    }
    
    /**
     * 自动存储系统字段
     */
    private void sysSave(ProductCategoryRel model, HttpServletRequest request) {
        if (model != null) {
            LoginUser loginUser = new LoginUser();
            if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
                loginUser.setLoginName("nologin");
            } else {
                loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
            }

            if (model.getId() == null) {
                model.setGmtCreate(new Date());
                model.setCreator(loginUser.getLoginName());
                model.setIsDeleted(0);
                if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                    model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }

            model.setGmtModified(new Date());
            model.setModifier(loginUser.getLoginName());
        }
    }

}
