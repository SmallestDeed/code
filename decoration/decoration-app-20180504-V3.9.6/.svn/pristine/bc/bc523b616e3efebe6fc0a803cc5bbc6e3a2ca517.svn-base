package com.nork.design.controller.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.SysDictionaryConstant;
import com.nork.common.util.collections.Lists;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.IgnoreJsonPropertyFilter;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.design.model.search.DesignPlanSearch;
import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.home.cache.DesignRecommendationCacher;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.model.DesignProgramDiy;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.DesignRecommendationService;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.BaseProductStyle;
import com.nork.product.model.JsonProduct;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductStyleService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * @Title: DesignTempletController.java
 * @Package com.nork.design.controller
 * @Description:设计模块-设计方案样板房表Controller
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/design/designTemplet")
public class WebDesignTempletController {
	private static Logger logger = Logger.getLogger(WebDesignTempletController.class);
	private final JsonDataServiceImpl<DesignTemplet> JsonUtil = new JsonDataServiceImpl<DesignTemplet>();
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/design/designTemplet";

	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private DesignRecommendationService designRecommendationService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private BaseProductStyleService productStyleService;
	/**
	 *  通过解析配置文件中的内容，解析json，并自动生成产品列表
	 * @param designTempletId
	 * @param filePath
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean analysisJson(Integer designTempletId,String filePath,HttpServletRequest request) throws Exception{
		
		try{
			//读取配置文件中的内容
			String fileContext = FileUploadUtils.getFileContext(filePath);
			//将配置文件中的内容转换成json格式
			JSONObject jsonObject = JSONObject.fromObject(fileContext);
			JSONArray jsonArray = (JSONArray)jsonObject.get("RoomConfig");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setRootClass(JsonProduct.class);
			//处理json中key的首字母大写情况
			jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
				@Override
				public String transformToJavaIdentifier(String s) {
					char[] chars = s.toCharArray();
					chars[0] = Character.toLowerCase(chars[0]);
					return new String(chars);
				}
			});
			//过滤在json中Entity没有的属性
			jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
			List<JsonProduct> productCodes = (List<JsonProduct>) JSONArray.toCollection(jsonArray, jsonConfig);

			//组装需要保存的数据
			List<DesignTempletProduct> designProductList = new ArrayList<>();
			BaseProduct baseProduct = null;
			DesignTempletProduct designProduct = null;
			for( JsonProduct jsonProduct : productCodes ){
				baseProduct = new BaseProduct();
				designProduct = new DesignTempletProduct();
				baseProduct.setProductCode(jsonProduct.getItemCode());
				List<BaseProduct> list = baseProductService.getList(baseProduct);
				if( list != null && list.size() > 0 ) {
					baseProduct = list.get(0);
					sysSave(designProduct, request);
					designProduct.setProductCode(baseProduct.getProductCode());
					designProduct.setProductId(baseProduct.getId());
					designProduct.setProductName(baseProduct.getProductName());
					designProduct.setDesignTempletId(designTempletId);
					
					//获取大类序号
					String productTypeSeq = "";
					String productTypeKey = "";
					if (StringUtils.isNotBlank(baseProduct.getProductTypeValue())) {
						SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType", new Integer(baseProduct.getProductTypeValue()));
						productTypeSeq = (dictionary==null ||dictionary.getOrdering()==null||dictionary.getOrdering()==0)?"00":dictionary.getOrdering().toString();
						if(dictionary==null ||StringUtils.isEmpty(dictionary.getValuekey())){
							continue;
						}
						productTypeKey = dictionary.getValuekey();
						if(productTypeSeq.length()==1){
							productTypeSeq = "0" + productTypeSeq;
						}
					} else {
						productTypeSeq = "00";
					}
					//获取小类序号
					String productSmallTypeSeq = "";
					if ( baseProduct.getProductSmallTypeValue() != null ) {
						SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue(productTypeKey, new Integer(baseProduct.getProductSmallTypeValue()));
						productSmallTypeSeq = (dictionary==null ||dictionary.getOrdering()==null||dictionary.getOrdering()==0)?"00":dictionary.getOrdering().toString();
						if(productSmallTypeSeq.length()==1){
							productSmallTypeSeq = "0" + productSmallTypeSeq;
						}
					}else{
						productSmallTypeSeq = "00";
					}
					
					designProduct.setProductSequence(productTypeSeq +""+productSmallTypeSeq+"");
					designProduct.setPosIndexPath(jsonProduct.getPosIndexPath());
					designProduct.setPosName(jsonProduct.getPosName());
					designProductList.add(designProduct);
				}
			}
			
			//同一类型增加序号
			
			
			//保存到样板间产品表
			if( designProductList != null && designProductList.size() > 0 ) {
				DesignTempletProductSearch designTempletProductSearch = new DesignTempletProductSearch();
				designTempletProductSearch.setDesignTempletId(designTempletId);
				designTempletProductSearch.setIsDeleted(0);
				int num = designTempletProductService.getCount(designTempletProductSearch);
				if(num == 0){
					try{
						designTempletProductService.batchSave(designTempletId, designProductList);
					}catch(Exception e){
						e.printStackTrace();
					    return false;
					}
					return true;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignTempletProduct model,HttpServletRequest request){
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
	 * 空间布局列表
	 * 
	 * @param designTempletSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/spaceSearch")
	public Object spaceSearch(@ModelAttribute("designTempletSearch") DesignTempletSearch designTempletSearch,
			HttpServletRequest request, HttpServletResponse response) {
	    
	    LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            return "/online/design/space_layout_list";
        }
        
		List<DesignTemplet> list = new ArrayList<DesignTemplet>();
		int total = 0;
		// 空间类型
		String spaceFunctionId = request.getParameter("spaceFunctionId");
		// 面积
		String areaValue = request.getParameter("areaValue");
		// 门的位置
		String doorLocationIdText = request.getParameter("doorLocationIdText");
		String doorLocationId = request.getParameter("doorLocationId");
		try {

			SpaceCommon spaceCommon = new SpaceCommon();
			if (spaceFunctionId != null && spaceFunctionId.length() > 0) {

				spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));
				spaceCommon.setSpaceAreas(areaValue);
				// 根据选择空间传空间名称到页面
				SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("houseType",
						Integer.valueOf(spaceFunctionId));
				request.setAttribute("spaceName", sysDictionary == null ? "" : sysDictionary.getName());
			}
			if (doorLocationId != null && doorLocationId.length() > 0) {
				spaceCommon.setDoorLocationId(Integer.valueOf(doorLocationId));
			}
			List<SpaceCommon> spacelist = spaceCommonService.getList(spaceCommon);
			List<Integer> spaceIdList = new ArrayList<Integer>();
			if (CustomerListUtils.isNotEmpty(spacelist)) {
				for (int i = 0; i < spacelist.size(); i++) {
					Integer spaceId = spacelist.get(i).getId();
					spaceIdList.add(spaceId);
					designTempletSearch.setSpaceIdList(spaceIdList);
				}
			} else {
				spaceIdList.add(0);
				designTempletSearch.setSpaceIdList(spaceIdList);
			}
			// 显示推荐的 1为推荐
			designTempletSearch.setIsRecommend(1);
			total = designTempletService.getCount(designTempletSearch,loginUser.getId());

			if (total > 0) {
				list = designTempletService.getPaginatedList(designTempletSearch,loginUser.getId());
				for (DesignTemplet designTemplet : list) {
					Integer styleId = designTemplet.getDesignStyleId();

					SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("planStyle", styleId);
							

					designTemplet.setDesignStyleName(sysDictionary == null ? "" : sysDictionary.getName());

					ResPic resPic = resPicService.get(designTemplet.getPicId());

					designTemplet.setPicPath(resPic == null ? "" : resPic.getPicPath());

					Integer spaceCommonId = designTemplet.getSpaceCommonId();

					SpaceCommon spacecommon = spaceCommonService.get(spaceCommonId);

					designTemplet.setSpaceCommonName(spacecommon == null ? "" : spacecommon.getSpaceName());

					designTemplet.setSpaceAreas(spacecommon == null ? "" : spacecommon.getSpaceAreas());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEnvelope<DesignTemplet> res = new ResponseEnvelope<DesignTemplet>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("spaceFunctionId", spaceFunctionId);
		request.setAttribute("areaValue", areaValue);
		request.setAttribute("doorLocationIdText", doorLocationIdText);
		request.setAttribute("doorLocationId", doorLocationId);

		return "/online/design/space_layout_list";
	}

	/**
     * 空间样板房列表
     * @param designTemplet
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/spaceDesign1")
	public Object spaceDesign(@ModelAttribute("designTemplet") DesignTemplet designTemplet,HttpServletRequest request, HttpServletResponse response) {
		List<DesignTemplet> list = new ArrayList<DesignTemplet> ();
		//空间类型
		String spaceFunctionId = request.getParameter("spaceFunctionId");
		//根据选择空间传空间名称到页面
		SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("houseType", Integer.valueOf(spaceFunctionId));
		request.setAttribute("spaceName", sysDictionary==null?"":sysDictionary.getName());
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		
		//上架条件
		/*designTemplet.setPutawayState(1);*/
		//发布条件
		designTemplet.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());
		try {
			list = designTempletService.getList(designTemplet);
			if(CustomerListUtils.isNotEmpty(list)){
				for(DesignTemplet templet:list){
	                ResPic resPic = resPicService.get(templet.getPicId());
	                templet.setPicPath(resPic==null?"":Utils.getSmallPath(resPic.getPicPath(),mediaType));
	                templet.setSmallpicPath(resPic==null?"":Utils.getSmallPath(resPic.getPicPath(),mediaType));
	                Integer spaceCommonId = templet.getSpaceCommonId();
					SpaceCommon spacecommon = spaceCommonService.get(spaceCommonId);
					templet.setSpaceCommonName(spacecommon==null?"":spacecommon.getSpaceName());
					templet.setSpaceAreas(spacecommon==null?"":spacecommon.getSpaceAreas());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("list", list);
		
		return  "/online/design/space_design_list";
	}
	
	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 * 
	 * @param spaceCommonIdText
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@RequestMapping(value = {"/spaceDesign","/spaceDesignWeb"})
	@ResponseBody
	public Object spaceDesign(@RequestParam(value = "spaceCommonId", required = false) String spaceCommonIdText,
			HttpServletRequest request, @RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) {
		Long startTime=System.currentTimeMillis();
		String msg = "";
		/*if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignTemplet>(false, msg, msgId);
		}*/
		if (StringUtils.isBlank(spaceCommonIdText)) {
			msg = "参数spaceCommonId不能为空";
			return new ResponseEnvelope<DesignTemplet>(false, msg, msgId);
		}
		int total = 0;
		List<DesignTemplet> list = new ArrayList<DesignTemplet>();
		DesignTempletSearch designTempletSearch = new DesignTempletSearch();
		try {
			// 上架条件
			/*designTempletSearch.setPutawayState(1);*/
			// 发布条件
			designTempletSearch.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());
			designTempletSearch.setIsDeleted(0);
			designTempletSearch.setSpaceCommonId(Integer.parseInt(spaceCommonIdText));
			if (StringUtils.isNotBlank(start)) {
				designTempletSearch.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				designTempletSearch.setLimit(Integer.parseInt(limit));
			}
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState*/
			LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			Integer userType = null;
			if(loginUser==null){
				logger.info("------查询样板房接口->无法识别登录用户");
			}else{
				String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
				userType =loginUser.getUserType();
				if(userType != null && userType.intValue() ==1 && "2".equals(versionType)){
					designTempletSearch.setPutawayState(null);
					Integer[] integers=new Integer[]{1,2,3};/*1上架  2测试  3为增加 的发布*/
					designTempletSearch.setPutawayStates(Arrays.asList(integers));
				}
			}
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end*/

			if(Utils.enableRedisCache()){
				if(userType != null && userType.intValue() ==1){
				    total=DesignTempletCacher.getTotalWithParameter2(designTempletSearch,userType,loginUser.getId());
				}else{
					total=DesignTempletCacher.getTotalWithParameter(designTempletSearch,loginUser.getId());
				}
			}else{
				total = designTempletService.getCount(designTempletSearch,loginUser.getId());
			}
			
			if (total > 0) {
				if(Utils.enableRedisCache()){
					if(userType != null && userType.intValue() ==1){
						list=DesignTempletCacher.getPageWithParameter2(designTempletSearch,userType,loginUser.getId());
					}else{
						list=DesignTempletCacher.getPageWithParameter(designTempletSearch,loginUser.getId());
					}
				}else{
					list = designTempletService.getPaginatedList(designTempletSearch,loginUser.getId());
				}
				
			}
			/*String mediaType = (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || request.getSession().getAttribute("mediaType") == null)
					? "2" : (String) request.getSession().getAttribute("mediaType");*/

			/* 关联查询spaceName和spaceAreas */
			if (CustomerListUtils.isNotEmpty(list)) {
				for (DesignTemplet templet : list) {
					/* 空间布局缩略图 */
					ResPic resPic2 = null;
					if( templet.getPicId() != null  ){
						if(Utils.enableRedisCache()){
							resPic2 = ResourceCacher.getPic(templet.getPicId());
						}else{
							resPic2 = resPicService.get(templet.getPicId());
						}
					}
					if (resPic2 != null) {
						templet.setPicPath(resPic2.getPicPath());
						ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic2, "ipad"));
						//templet.setSmallpicPath(resPic2 == null ? "" : Utils.getSmallPath(resPic2.getPicPath(), mediaType));
						templet.setSmallpicPath(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
					}
					// 平面图
					/*
					 * ResPic resPic = resPicService.get(templet.getPicId());
					 * templet.setPicPath(resPic==null?"":(StringUtils.isEmpty(
					 * resPic.getPicPath())?"":resPic.getPicPath()));
					 * templet.setSmallpicPath(resPic==null?"":Utils.
					 * getSmallPath(resPic.getPicPath(),mediaType));
					 */
					// 效果图列表
					String effectPicId = templet.getEffectPic();
					if (!StringUtils.isEmpty(effectPicId)) {
						List<String> effectlist = new ArrayList();
						if (effectPicId.contains(",")) {
							String[] strs = effectPicId.split(",");
							for (String s : strs) {
								if (!StringUtils.isEmpty(s)) {
									ResPic resPic=null;
									if(Utils.enableRedisCache()){
										resPic = ResourceCacher.getPic(Integer.parseInt(s));
									}else{
										resPic = resPicService.get(Integer.parseInt(s));
									}
									
									if (resPic != null && StringUtils.isNotBlank(resPic.getPicPath())) {
										effectlist.add(resPic.getPicPath());
									}
								}
							}
							templet.setEffectPicListPath(effectlist.toArray(new String[effectlist.size()]));
							ResPic resPic=null;
							if(Utils.enableRedisCache()){
								resPic = ResourceCacher.getPic(Integer.parseInt(strs[0]));
							}else{
								resPic = resPicService.get(Integer.parseInt(strs[0]));
							}
							ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
							//templet.setEffectSmallPicPath(resPic == null ? "" : Utils.getSmallPath(resPic.getPicPath(), mediaType));
							templet.setEffectSmallPicPath(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
							
							//客户端取值错误，临时修改为对应的值todo
							templet.setPicPath(templet.getEffectPicListPath()[0]);
							templet.setSmallpicPath(templet.getEffectSmallPicPath());
						} else {
							ResPic resPic=null;
							if(Utils.enableRedisCache()){
								resPic = ResourceCacher.getPic(Integer.parseInt(effectPicId));
							}else{
								resPic = resPicService.get(Integer.parseInt(effectPicId));
							}
							
							String[] s = new String[1];
							s[0] = resPic == null ? ""
									: (Utils.isEmpty(resPic.getPicPath()) ? "" : resPic.getPicPath());
							templet.setEffectPicListPath(s);
							ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
							//templet.setEffectSmallPicPath(resPic == null ? "" : Utils.getSmallPath(resPic.getPicPath(), mediaType));
							templet.setEffectSmallPicPath(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
							
						    //客户端取值错误，临时修改为对应的值todo
							templet.setPicPath(templet.getEffectPicListPath()[0]);
							templet.setSmallpicPath(templet.getEffectSmallPicPath());
						}
					}
	                /*关联查询平面效果图url及对应缩略图url*/
	            	String effectPlanIds = templet.getEffectPlanIds();
	            	if(StringUtils.isNotBlank(effectPlanIds)){
	            		Integer effectPlanId=0;
	            		if(effectPlanIds.split(",").length>1){
	            			effectPlanId=Integer.valueOf(effectPlanIds.split(",")[0]);
	            		}else{
	            			effectPlanId=Integer.valueOf(effectPlanIds);
	            		}
		            	ResPic resPic = resPicService.get(effectPlanId);
						if(resPic!= null && StringUtils.isNotBlank(resPic.getPicPath())){
							templet.setEffectPlanUrl(resPic.getPicPath());
							ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
							//templet.setEffectPlanSmallUrl(Utils.getSmallPath(resPic.getPicPath(),mediaType));
							templet.setEffectPlanSmallUrl(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
						}
	            	}
		
					Integer spaceCommonId = templet.getSpaceCommonId();
					SpaceCommon spacecommon=null;
					if(Utils.enableRedisCache()){
						spacecommon = SpaceCommonCacher.get(spaceCommonId);
					}else{
						spacecommon = spaceCommonService.get(spaceCommonId);
					}
					
					templet.setSpaceCommonName(spacecommon == null ? "" : spacecommon.getSpaceName());
					templet.setSpaceAreas(spacecommon == null ? "" : spacecommon.getSpaceAreas());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignTemplet>(false, "数据异常", msgId);
		}
		Long endTime=System.currentTimeMillis();
		//////System.out.println("times:"+(endTime-startTime));
		return new ResponseEnvelope<DesignTemplet>(total, list, msgId);
	}

	/**
     * 获得空间类型对应风格
     * @param spaceFunctionId 空间类型Id
     * @param msgId
     */
	@RequestMapping(value = {"/queryStyleByHouseType"})
	@ResponseBody
	public Object queryStyleByHouseType(@RequestParam(value = "spaceFunctionId", required = false)String spaceFunctionId, String msgId){
		if (!StringUtils.isNotBlank(spaceFunctionId)) {
			return new ResponseEnvelope<BaseProductStyle>(false, "缺少参数spaceFunctionId", msgId);
		}
		/*查询空间类型对应风格分类*/
		List<BaseProductStyle> styleLs= new ArrayList<BaseProductStyle>();
		Integer functionId=Integer.parseInt(spaceFunctionId);
		try {
			styleLs=productStyleService.getStyleByHouseType(functionId);
			BaseProductStyle baseProductStyle = new BaseProductStyle();
			baseProductStyle.setName("全部");
			styleLs.add(0, baseProductStyle);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProductStyle>(false, "数据异常", msgId);
		}
		return new ResponseEnvelope<BaseProductStyle>(styleLs.size(), styleLs, msgId);
	}
	
	
	 /* 根据HouseTypeValue查询样板房
	 * queryDesignTempletByHouseTypeValue
	 * @param request
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = {"/queryDesignTemplet"})
	@ResponseBody
	public Object queryDesignTemplet(HttpServletRequest request,
			@RequestParam(value = "spaceFunctionId", required = false) String spaceFunctionId,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start){
		
		List<DesignTemplet> list = new ArrayList<DesignTemplet>();
		int total=0;
		DesignTempletSearch designTempletSearch = new DesignTempletSearch();
		try {
			/*designTempletSearch.setPutawayState(1);*/
			designTempletSearch.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());
			if (StringUtils.isNotBlank(start)) {
				designTempletSearch.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				designTempletSearch.setLimit(Integer.parseInt(limit));
			}
			if (!StringUtils.isNotBlank(spaceFunctionId)) {
				return new ResponseEnvelope<DesignTemplet>(false, "缺少参数spaceFunctionId", msgId);
			}
			if (!StringUtils.isNotBlank(msgId)) {
				return new ResponseEnvelope<DesignTemplet>(false, "缺少参数msgId", msgId);
			}
			LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);/**根据登录用户类型(内部用户,普通用户)决定查询条件putawayState*/
			Integer userType = null;
			if(loginUser==null){
				logger.info("------查询样板房接口->无法识别登录用户");
			}
			if(loginUser!=null){
				userType =loginUser.getUserType();
			}
			if(userType != null && userType.intValue() ==1){
				designTempletSearch.setPutawayState(null);
				Integer[] integers=new Integer[]{1,2,3}; /*1上架  2测试  3为增加 的发布*/
				designTempletSearch.setPutawayStates(Arrays.asList(integers));
			}
			designTempletSearch.setSpaceFunctionId(Integer.parseInt(spaceFunctionId));
			
			total = designTempletService.getCount(designTempletSearch,loginUser.getId());/**根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end*/
			if (total > 0) {
				list = designTempletService.getPaginatedList(designTempletSearch,loginUser.getId());
			}
			if (CustomerListUtils.isNotEmpty(list)) {
				for (DesignTemplet templet : list) {
					/**空间布局缩略图 */
					ResPic resPic2 = null;
					if( templet.getPicId() != null  ){
						if(Utils.enableRedisCache()){
							resPic2 = ResourceCacher.getPic(templet.getPicId());
						}else{
							resPic2 = resPicService.get(templet.getPicId());
						}
					}
					if (resPic2 != null) {
						templet.setPicPath(resPic2.getPicPath());
						ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic2, "ipad"));
						templet.setSmallpicPath(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
					}
					/** 效果图列表*/
					String effectPicId = templet.getEffectPic();
					if (!StringUtils.isEmpty(effectPicId)) {
						List<String> effectlist = new ArrayList();
						if (effectPicId.contains(",")) {
							String[] strs = effectPicId.split(",");
							for (String s : strs) {
								if (!StringUtils.isEmpty(s)) {
									ResPic resPic=null;
									if(Utils.enableRedisCache()){
										resPic = ResourceCacher.getPic(Integer.parseInt(s));
									}else{
										resPic = resPicService.get(Integer.parseInt(s));
									}
									if (resPic != null && StringUtils.isNotBlank(resPic.getPicPath())) {
										effectlist.add(resPic.getPicPath());
									}
								}
							}
							templet.setEffectPicListPath(effectlist.toArray(new String[effectlist.size()]));
							ResPic resPic=null;
							if(Utils.enableRedisCache()){
								resPic = ResourceCacher.getPic(Integer.parseInt(strs[0]));
							}else{
								resPic = resPicService.get(Integer.parseInt(strs[0]));
							}
							ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
							templet.setEffectSmallPicPath(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
							
							/**客户端取值错误，临时修改为对应的值todo*/
							templet.setPicPath(templet.getEffectPicListPath()[0]);
							templet.setSmallpicPath(templet.getEffectSmallPicPath());
						} else {
							ResPic resPic=null;
							if(Utils.enableRedisCache()){
								resPic = ResourceCacher.getPic(Integer.parseInt(effectPicId));
							}else{
								resPic = resPicService.get(Integer.parseInt(effectPicId));
							}
							
							String[] s = new String[1];
							s[0] = resPic == null ? ""
									: (Utils.isEmpty(resPic.getPicPath()) ? "" : resPic.getPicPath());
							templet.setEffectPicListPath(s);
							ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
							templet.setEffectSmallPicPath(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
							
						    /**客户端取值错误，临时修改为对应的值todo*/
							templet.setPicPath(templet.getEffectPicListPath()[0]);
							templet.setSmallpicPath(templet.getEffectSmallPicPath());
						}
					}
	                /**关联查询平面效果图url及对应缩略图url*/
	            	String effectPlanIds = templet.getEffectPlanIds();
	            	if(StringUtils.isNotBlank(effectPlanIds)){
	            		Integer effectPlanId=0;
	            		if(effectPlanIds.split(",").length>1){
	            			effectPlanId=Integer.valueOf(effectPlanIds.split(",")[0]);
	            		}else{
	            			effectPlanId=Integer.valueOf(effectPlanIds);
	            		}
		            	ResPic resPic = resPicService.get(effectPlanId);
						if(resPic!= null && StringUtils.isNotBlank(resPic.getPicPath())){
							templet.setEffectPlanUrl(resPic.getPicPath());
							ResPic smallResPic=resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
							templet.setEffectPlanSmallUrl(smallResPic==null?"":(Utils.isBlank(smallResPic.getPicPath())?"":smallResPic.getPicPath()));
						}
	            	}
		
					Integer spaceCommonId = templet.getSpaceCommonId();
					SpaceCommon spacecommon=null;
					if(Utils.enableRedisCache()){
						spacecommon = SpaceCommonCacher.get(spaceCommonId);
					}else{
						spacecommon = spaceCommonService.get(spaceCommonId);
					}
					
					templet.setSpaceCommonName(spacecommon == null ? "" : spacecommon.getSpaceName());
					templet.setSpaceAreas(spacecommon == null ? "" : spacecommon.getSpaceAreas());
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEnvelope<DesignTemplet>(false, "数据异常", msgId);
	}
	return new ResponseEnvelope<DesignTemplet>(total, list, msgId);
}
	
	
	
	
	
	
	/**
	 * 判断是否创建过该样板房的方案
	 * 
	 * @param planId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/isExistsPlan")
	@ResponseBody
	public Object isExistsPlan(Integer planId, HttpServletRequest request, String msgId) {
		JSONObject jsonObject = new JSONObject();
		if (planId == null) {
			return new ResponseEnvelope<DesignTemplet>(false, "参数planId不能为空", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (loginUser==null) {
			return jsonObject;
		}
		DesignPlanSearch designPlan = new DesignPlanSearch();
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);;

		designPlan.setCreator(loginUser.getLoginName());
		designPlan.setDesignId(planId);
		designPlan.setDesignSourceType(7);
		designPlan.setOrders("gmt_modified DESC");
		designPlan.setMediaType(new Integer(mediaType));
		designPlan.setLimit(1);
		List<DesignPlan> list=null;
		if(Utils.enableRedisCache()){
			list = DesignCacher.getPaginatedList(designPlan);
		}else{
			list = designPlanService.getPaginatedList(designPlan);
		}
		
		if (list != null && list.size() > 0) {
			ResponseEnvelope<DesignPlan> re = new ResponseEnvelope<DesignPlan>(true,
					"exists designPlan!last designPlan.id=" + list.get(0).getId(), msgId);
			re.setObj(list.get(0).getId());
			return re;
		} else {
			return new ResponseEnvelope<DesignTemplet>(true, "not exists designPlan!", msgId);
		}
	}

	/**
	 * 通过配置文件内容再重新再生成样板房产品列表（存在时，不删除，需手工删除）
	 * 
	 * @param designId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reloadDate")
	@ResponseBody
	public boolean reloadDate(Integer designId, HttpServletRequest request) {
		if (designId == null || new Integer(designId) < 0) {
			return false;
		}

		// 通过样板房修改
		DesignTemplet designTemplet = designTempletService.get(designId);
		if (designTemplet != null) {
			// 解析配置文件中产品信息，并更新
			Integer configFileId = designTemplet.getConfigFileId();
			if (configFileId != null && configFileId > 0) {
				ResFile resFile = resFileService.get(configFileId);
				if (resFile != null) {
					String filePath =Tools.getRootPath(resFile.getFilePath(),"" )+ resFile.getFilePath();
					File f = new File(filePath);
					if (!f.exists()) {
						return false;
					}
					try {
						analysisJson(designTemplet.getId(), filePath, request);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 空间布局列表(移动端引用)
	 */
	@RequestMapping(value = "/app/spaceSearch")
	@ResponseBody
	public Object list(@ModelAttribute("designTempletSearch") DesignTempletSearch designTempletSearch,
			String spaceFunctionId, String areaValue, String doorLocationIdText, String doorLocationId,
			HttpServletRequest request, HttpServletResponse response) {
	    
	    
	    LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            ResponseEnvelope envelope = new ResponseEnvelope<DesignTemplet>();
            envelope.setSuccess(false);
            envelope.setMsgId( designTempletSearch.getMsgId());
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
		// 每页不同页码时使用
		if (designTempletSearch.getLimit() == null) {
			designTempletSearch.setLimit(new Integer(20));
		}
		List<DesignTemplet> list = new ArrayList<DesignTemplet>();
		int total = 0;
		// 空间类型
		// String spaceFunctionId = request.getParameter("spaceFunctionId");
		// 面积
		// String areaValue = request.getParameter("areaValue");
		// 门的位置
		// String doorLocationIdText =
		// request.getParameter("doorLocationIdText");
		// String doorLocationId = request.getParameter("doorLocationId");
		try {

			SpaceCommon spaceCommon = new SpaceCommon();
			if (spaceFunctionId != null && spaceFunctionId.length() > 0) {

				spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));
				spaceCommon.setSpaceAreas(areaValue);

			}
			if (doorLocationId != null && doorLocationId.length() > 0) {
				spaceCommon.setDoorLocationId(Integer.valueOf(doorLocationId));
			}
			List<SpaceCommon> spacelist = spaceCommonService.getList(spaceCommon);
			List<Integer> spaceIdList = new ArrayList<Integer>();
			if (CustomerListUtils.isNotEmpty(spacelist)) {
				for (int i = 0; i < spacelist.size(); i++) {
					Integer spaceId = spacelist.get(i).getId();
					spaceIdList.add(spaceId);
					designTempletSearch.setSpaceIdList(spaceIdList);
				}
			} else {
				spaceIdList.add(0);
				designTempletSearch.setSpaceIdList(spaceIdList);
			}
			// 显示推荐的 1为推荐
			designTempletSearch.setIsRecommend(1);
			total = designTempletService.getCount(designTempletSearch,loginUser.getId());

			if (total > 0) {
				list = designTempletService.getPaginatedList(designTempletSearch,loginUser.getId());
				for (DesignTemplet designTemplet : list) {
					Integer styleId = designTemplet.getDesignStyleId();

					SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("planStyle", styleId);
							

					designTemplet.setDesignStyleName(sysDictionary == null ? "" : sysDictionary.getName());

					ResPic resPic = resPicService.get(designTemplet.getPicId());

					designTemplet.setPicPath(resPic == null ? "" : resPic.getPicPath());

					Integer spaceCommonId = designTemplet.getSpaceCommonId();

					SpaceCommon spacecommon = spaceCommonService.get(spaceCommonId);

					designTemplet.setSpaceCommonName(spacecommon == null ? "" : spacecommon.getSpaceName());

					designTemplet.setSpaceAreas(spacecommon == null ? "" : spacecommon.getSpaceAreas());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEnvelope<DesignTemplet>(total, list, designTempletSearch.getMsgId());
	}

	/**
	 * 获取样板间——下拉框值
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/spaceList")
	public String spaceList(HttpServletRequest request) {

		// 获取空间类型
		SysDictionary spaceType = new SysDictionary();
		spaceType.setType("houseType");
		List<SysDictionary> houseTypelist = sysDictionaryService.getList(spaceType);
		request.setAttribute("houseTypelist", houseTypelist);
		// 获取空间风格
		SysDictionary spaceStyle = new SysDictionary();
		spaceStyle.setType("productStyle");
		List<SysDictionary> spaceStyleList = sysDictionaryService.getList(spaceStyle);
		request.setAttribute("spaceStyleList", spaceStyleList);

		// return "/online/space/space_list";
		return "/online/design/designTrends/designTrends";
	}

	/**
	 * 获取空间（样板间）列表
	 * 
	 * @param designTemplet
	 * @param request
	 */
	@RequestMapping(value = "/templetList")
	public String templetList(@ModelAttribute("designProgramDiy") DesignProgramDiy designProgramDiy,
			HttpServletRequest request) {

		designProgramDiy.setOrder(" gmt_modified desc ");
		List<DesignProgramDiy> list = designRecommendationService.getDropDownBoxList(designProgramDiy);

		request.setAttribute("houseTypeId", designProgramDiy.getHouseTypeId());
		request.setAttribute("designStyleId", designProgramDiy.getDesignStyleId());
		request.setAttribute("list", list);

		return "/online/space/templet_list";
	}

	/**
	 * 获取空间（样板间）渲染图
	 * 
	 * @param templateId
	 * @param request
	 */
	@RequestMapping(value = "/spaceRenderPic")
	public String spaceRenderPic(Integer templateId, HttpServletRequest request) {

		// 默认显示第一条
		DesignProgramDiy designProgramDiy = new DesignProgramDiy();
		if (templateId == null) {
			designProgramDiy.setOrder("gmt_modified desc");
			// List<DesignSpaceResult> list =
			// designTempletService.getSpaceList(designTemplet);
			List<DesignProgramDiy> list = designRecommendationService.getDropDownBoxList(designProgramDiy);
			if (list != null && list.size() > 0) {
				templateId = list.get(0).getId();
			}
		}

		RecommendationSpaceResult recommendationSpaceResult = designRecommendationService.getSpaceRender(templateId);
		if (recommendationSpaceResult != null) {
			String renderPicIds = recommendationSpaceResult.getRenderPicIds();

			if (renderPicIds != null && !"".equals(renderPicIds)) {
				if (renderPicIds.contains(",")) {
					String[] strs = recommendationSpaceResult.getRenderPicIds().split(",");

					for (String picId : strs) {
						ResPic resPic = resPicService.get(Utils.getIntValue(picId));
						recommendationSpaceResult.getRenderPicList().add(resPic == null ? "" : resPic.getPicPath());
					}
				} else {
					ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
					recommendationSpaceResult.getRenderPicList().add(resPic == null ? "" : resPic.getPicPath());
				}
			}
		}
		request.setAttribute("recommendationSpaceResult", recommendationSpaceResult);

		return "/online/space/space_render";
	}

	/**
	 * 设计潮流-图片列表接口
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/designTrends")
	@ResponseBody
	public Object designTrends(@PathVariable String style,
			@ModelAttribute("designProgramDiy") DesignProgramDiy designProgramDiy, HttpServletRequest request)
					throws Exception {
		String msg = "";
		// DesignProgramDiy designProgramDiy = new DesignProgramDiy();
		if (StringUtils.isBlank(designProgramDiy.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignProgramDiy>(false, msg, designProgramDiy.getMsgId());
		}

		Integer templateId = null;
		List<DesignProgramDiy> list = null;
		// 默认显示第一条
		designProgramDiy.setOrder("gmt_modified desc");
		int total = 0;
		try {
			if(Utils.enableRedisCache()){
				total=DesignRecommendationCacher.getTotalDiy(designProgramDiy);
			}else{
				total = designRecommendationService.getTotal(designProgramDiy);
			}
			
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = DesignRecommendationCacher.getDropDownList(designProgramDiy);
				}else{
					list = designRecommendationService.getDropDownBoxList(designProgramDiy);
				}
				
				for (DesignProgramDiy designProgramdiy : list) {
					List<String> renderPicList = new ArrayList<String>();
					templateId = designProgramdiy.getId();
					RecommendationSpaceResult recommendationSpaceResult = designRecommendationService.getSpaceRender(templateId);
					if (recommendationSpaceResult != null) {
						String renderPicIds = recommendationSpaceResult.getRenderPicIds();
						if (renderPicIds != null && !"".equals(renderPicIds)) {
							if (renderPicIds.contains(",")) {
								String[] strs = recommendationSpaceResult.getRenderPicIds().split(",");
								for (String picId : strs) {
									ResPic resPic = resPicService.get(Utils.getIntValue(picId));
									renderPicList.add(resPic == null ? "" : resPic.getPicPath());
								}
							} else {
								ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
								renderPicList.add(resPic == null ? "" : resPic.getPicPath());
							}
						}
					}
					designProgramdiy.setRenderPicList(renderPicList);
					designProgramdiy.setSpaceAreas(recommendationSpaceResult.getSpaceArea());
					designProgramdiy.setDesignName(recommendationSpaceResult.getDesignName());
					designProgramdiy.setDescription(recommendationSpaceResult.getDescription());
					designProgramdiy.setPicPath(recommendationSpaceResult.getPicPath());
				}

				if ("small".equals(style) && list != null && list.size() > 0) {
					String designPlanJsonList = JsonUtil.getListToJsonData(list);
					List<DesignProgramDiy> smallList = new JsonDataServiceImpl<DesignProgramDiy>()
							.getJsonToBeanList(designPlanJsonList, DesignProgramDiy.class);
					return new ResponseEnvelope<DesignProgramDiy>(total, smallList, designProgramDiy.getMsgId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignProgramDiy>(false, "数据异常!");
		}

		return new ResponseEnvelope<DesignProgramDiy>(total, list, designProgramDiy.getMsgId());
	}

	/**
	 * 判断是否创建过该样板房的方案
	 * 
	 * @param planId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/existsCreate")
	@ResponseBody
	public Object existsCreate(Integer planId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (loginUser==null) {
			return jsonObject;
		}
		DesignPlan designPlan = new DesignPlan();
		designPlan.setCreator(loginUser.getLoginName());
		designPlan.setDesignId(planId);
		designPlan.setOrders(" gmt_create desc ");
		List<DesignPlan> list = designPlanService.getList(designPlan);
		if (list != null && list.size() > 0) {
			jsonObject.accumulate("success", true);
			jsonObject.accumulate("planId", list.get(0).getId());
		} else {
			jsonObject.accumulate("success", false);
		}
		return jsonObject;
	}


	/**
	 * 更新所有卫生间样板房的空间布局类型
	 * author xiaoxc
	 * date 20171220
	 * @return
	 */
	@RequestMapping("/updateDesignTempletSpaceLayoutType")
	@ResponseBody
	public ResponseEnvelope updateDesignTempletSpaceLayoutType(){
		logger.error("开始功能样板房空间布局类型。。。。");
		StringBuilder builder1 = new StringBuilder("[");
		StringBuilder builder2 = new StringBuilder("[");
		int count = 0;
		int temp = 0;
		//TODO 获取所有卫生间样板房的ID
		List<Integer> idList = designTempletService.findIdListBySpaceCommonId(SysDictionaryConstant.house_Type_toilet_value);
		if (Lists.isNotEmpty(idList)) {
			logger.error("卫生间样板房共size = "+idList.size());
			for (Integer id : idList) {
				logger.error("开始更新第"+temp+++"条");
				//TODO 获取样板房产品小分类keys
				List<String> productKeylist = designTempletService.findListByDesignTempletId(id);
				if (Lists.isNotEmpty(productKeylist)) {
					//TODO 获取空间布局类型
					String spaceLayoutType = designPlanRecommendedServiceV2.getLayoutType(productKeylist, DesignPlanConstants.EnumDesignType.TEMPLET.toString());
					if (StringUtils.isNotEmpty(spaceLayoutType)) {
						DesignTemplet designTemplet = new DesignTemplet();
						designTemplet.setId(id);
						designTemplet.setSpaceLayoutType(spaceLayoutType);
						//TODO 更新样板房空间布局类型
						designTempletService.update(designTemplet);
						builder1.append(id+",");
						count ++;
						continue;
					}
				}
				builder2.append(id+",");
			}
		}
		builder1.append("]").append(":"+count+"个推荐方案已更新\n");
		builder2.append("]").append(":未更新");
		String message = "共"+idList.size()+"条\n"+builder1.toString() + builder2.toString();
		return new ResponseEnvelope(true,message);
	}
	
}
