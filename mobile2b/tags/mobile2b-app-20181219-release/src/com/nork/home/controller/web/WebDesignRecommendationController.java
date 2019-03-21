package com.nork.home.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanResult;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletResult;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.home.cache.DesignRecommendationCacher;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.model.DesignRecommendation;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.search.DesignRecommendationSearch;
import com.nork.home.model.small.DesignRecommendationSmall;
import com.nork.home.service.DesignRecommendationService;
import com.nork.home.service.SpaceCommonService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.cache.SysDictionaryCacher;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserFansService;
import com.nork.system.service.SysUserService;

/**
 * Created by zhangwj on 2015/7/9.
 */
@Controller
@RequestMapping("/{style}/web/home/designRecommendation")
public class WebDesignRecommendationController {

    private static Logger logger = Logger
            .getLogger(WebDesignRecommendationController.class);
    private final JsonDataServiceImpl<DesignRecommendation> JsonUtil = new JsonDataServiceImpl<DesignRecommendation>();
    private final String STYLE="online";
    private final String JSPSTYLE="online";
    private final String MAIN = "/"+ STYLE + "/home/designRecommendation";
    private final String BASEMAIN = "/"+ STYLE + "/home/base/designRecommendation";
    private final String JSPMAIN = "/"+ JSPSTYLE + "/home/designRecommendation";
    
    @Autowired
	private DesignRecommendationService designRecommendationService;
    @Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private SpaceCommonService spaceCommonService;
    @Autowired
	private ResPicService resPicService;
    @Autowired
	private SysDictionaryService sysDictionaryService;
    @Autowired
	private SysUserService sysUserService;
    @Autowired
	private DesignPlanProductService designPlanProductService;
    @Autowired
    private SysUserFansService sysUserFansService;

    /**
     *    访问主页面
     *
     * @param
     * @return   String
     */
	@RequestMapping(value = "/list")
	public Object index(@ModelAttribute("designRecommendationSearch") DesignRecommendationSearch designRecommendationSearch,  HttpServletRequest request, HttpServletResponse response) {

		SysDictionary sysDictionary = new SysDictionary();
    	sysDictionary.setType("houseType");
    	
    	List<SysDictionary> houseTypelist = sysDictionaryService.getList(sysDictionary);
    	request.setAttribute("houseTypelist", houseTypelist);
		
		List<DesignRecommendation> list = new ArrayList<DesignRecommendation> ();
		int total = 0;
		try {
			total = designRecommendationService.getCount(designRecommendationSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designRecommendationService.getPaginatedList(designRecommendationSearch);
				for(DesignRecommendation design : list){
					if("7".equals(design.getSrcType())){//样板间
						DesignTemplet desTemplet = new DesignTemplet();
						desTemplet.setSysCode(design.getSrcCode());
						List<DesignTemplet> designTemplet = designTempletService.getList(desTemplet);
						if(designTemplet.size()>0){
							if(designTemplet.get(0)!=null){
								SpaceCommon spaceCommon = spaceCommonService.get(designTemplet.get(0).getSpaceCommonId());
								design.setSpaceName(spaceCommon.getSpaceName());
							}
						}
						
					}else if ("6".equals(design.getSrcType())){//设计方案
						DesignPlan desPlan = new DesignPlan();
						desPlan.setSysCode(design.getSrcCode());
						List<DesignPlan> designPlan = designPlanService.getList(desPlan);
						if(designPlan.size()>0){
							if(designPlan.get(0)!=null){
								SpaceCommon spaceCommon = spaceCommonService.get(designPlan.get(0).getSpaceCommonId());
								design.setSpaceName(spaceCommon.getSpaceName());
							}
						}
						
					}
					if(design.getPicId()!=null && !"".equals(design.getPicId())){
						if(design.getPicId().contains(",")){
							String picIds[] = design.getPicId().split(",");
							ResPic resPic = resPicService.get(Utils.getIntValue(picIds[picIds.length-1]));
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}else{
							ResPic resPic = resPicService.get(Utils.getIntValue(design.getPicId()));
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRecommendation>(false, "数据异常!");
		}
		
		ResponseEnvelope<DesignRecommendation> res = new ResponseEnvelope<DesignRecommendation>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", designRecommendationSearch);
		return  Utils.getPageUrl(request, JSPMAIN + "/designRecommendation_list");
	}
    
	  /**
     *    访问主页面
     *
     * @param
     * @return   String
     */
	@RequestMapping(value = "/designRecommendationList")
	public Object designRecommendationList(@ModelAttribute("designRecommendationSearch") DesignRecommendationSearch designRecommendationSearch,  HttpServletRequest request, HttpServletResponse response) {

		SysDictionary sysDictionary = new SysDictionary();
    	sysDictionary.setType("houseType");
    	
    	List<SysDictionary> houseTypelist = sysDictionaryService.getList(sysDictionary);
    	request.setAttribute("houseTypelist", houseTypelist);
		
		List<DesignRecommendation> list = new ArrayList<DesignRecommendation> ();
		//首页是否显示  1显示，0不显示。
		designRecommendationSearch.setHomeIsDisplayed(1);
		int total = 0;
		try {
			total = designRecommendationService.getCount(designRecommendationSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designRecommendationService.getPaginatedList(designRecommendationSearch);
				for(DesignRecommendation design : list){
					if("7".equals(design.getSrcType())){//样板间
						DesignTemplet desTemplet = new DesignTemplet();
						desTemplet.setSysCode(design.getSrcCode());   																																			
						List<DesignTemplet> designTemplet = designTempletService.getList(desTemplet);
						if(CustomerListUtils.isNotEmpty(designTemplet)){
							if(designTemplet.get(0)!=null){
								SpaceCommon spaceCommon = spaceCommonService.get(designTemplet.get(0).getSpaceCommonId());
								design.setSpaceName(spaceCommon.getSpaceName());
								design.setDesignName(designTemplet.get(0).getDesignName());
							}
						}
						
					}else if ("6".equals(design.getSrcType())){//设计方案
						DesignPlan desPlan = new DesignPlan();
						desPlan.setSysCode(design.getSrcCode());
						List<DesignPlan> designPlan = designPlanService.getList(desPlan);
						if(CustomerListUtils.isNotEmpty(designPlan)){
							if(designPlan.get(0)!=null){
								SpaceCommon spaceCommon = spaceCommonService.get(designPlan.get(0).getSpaceCommonId());
								if(spaceCommon!= null){
									design.setSpaceName(spaceCommon.getSpaceName());
								}
								design.setDesignName(designPlan.get(0).getPlanName());
							}
						}
						
					}
					if(design.getPicId()!=null && !"".equals(design.getPicId())){
						if(design.getPicId().contains(",")){
							String picIds[] = design.getPicId().split(",");
							ResPic resPic = resPicService.get(Utils.getIntValue(picIds[picIds.length-1]));
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}else{
							ResPic resPic = resPicService.get(Utils.getIntValue(design.getPicId()));
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRecommendation>(false, "数据异常!");
		}
		
		ResponseEnvelope<DesignRecommendation> res = new ResponseEnvelope<DesignRecommendation>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", designRecommendationSearch);
		return  "/online/design/space_search";
	}
	
	/**
     * 推荐热门方案列表接口
     *
     * @param 
     * @return   String
     */
	@RequestMapping(value = "/designRecommendationListWeb")
	@ResponseBody
	public Object designRecommendationList(@PathVariable String style,
			@ModelAttribute("designRecommendationSearch") DesignRecommendationSearch designRecommendationSearch,
			HttpServletRequest request, HttpServletResponse response) {
		@SuppressWarnings("unused")
		long start =System.currentTimeMillis();
		/*String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designRecommendationSearch = (DesignRecommendationSearch)JsonUtil.getJsonToBean(jsonStr, DesignRecommendationSearch.class);
			if(designRecommendationSearch == null){
			   return new ResponseEnvelope<DesignRecommendation>(false, "传参异常!","none");
			}
		}*/
		/*if(StringUtils.isBlank(designRecommendationSearch.getMsgId())){
			return new ResponseEnvelope<DesignRecommendation>(false, "参数msgId为空",designRecommendationSearch.getMsgId());
		}*/
		//空间下拉
		SysDictionary sysDictionary = new SysDictionary();
    	sysDictionary.setType("houseType"); 
    	List<SysDictionary> houseTypelist=null;
    	if(Utils.enableRedisCache()){
    		houseTypelist =SysDictionaryCacher.getAllListWithType("houseType");
    	}else{
    		houseTypelist = sysDictionaryService.getList(sysDictionary);
    	}
    	
    	request.setAttribute("houseTypelist", houseTypelist);
		
		List<DesignRecommendation> list = new ArrayList<DesignRecommendation>();
		int total = 0;
		try {
			//媒介类型.如果没有值，则表示为web前端（2）
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			if(Utils.enableRedisCache()){
				total=DesignRecommendationCacher.getTotal();
			}else{
				total = designRecommendationService.getCount(designRecommendationSearch);
			}
			
            logger.info("total:" + total);
            
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list=DesignRecommendationCacher.getPageList(designRecommendationSearch);
				}else{
					list = designRecommendationService.getPaginatedList(designRecommendationSearch);
				}
				
				for(DesignRecommendation design : list){
					design.setMsgId(designRecommendationSearch.getMsgId());
					if("7".equals(design.getSrcType())){//样板间
						DesignTemplet desTemplet = new DesignTemplet();
						desTemplet.setSysCode(design.getSrcCode());   			
						List<DesignTemplet> designTemplet=null;
						if(Utils.enableRedisCache()){
							designTemplet = DesignTempletCacher.getAllTempletListWithSysCode(design.getSrcCode());
						}else{
							designTemplet = designTempletService.getList(desTemplet);
						}
						
						if(designTemplet.size()>0){
							if(designTemplet.get(0)!=null){
								SpaceCommon spaceCommon=null;
								if(Utils.enableRedisCache()){
									spaceCommon = SpaceCommonCacher.get(designTemplet.get(0).getSpaceCommonId());
								}else{
									spaceCommon = spaceCommonService.get(designTemplet.get(0).getSpaceCommonId());
								}
								
								design.setSpaceName(spaceCommon.getSpaceName());
							}
						} 
					}else if ("6".equals(design.getSrcType())){//设计方案
						DesignPlan desPlan = new DesignPlan();
						desPlan.setSysCode(design.getSrcCode());
						List<DesignPlan> designPlan=null;
						if(Utils.enableRedisCache()){
							designPlan = DesignCacher.getAllPlantListWithSysCode(design.getSrcCode());
						}else{
							designPlan = designPlanService.getList(desPlan);
						}
						
						if(designPlan.size()>0){
							if(designPlan.get(0)!=null){
								SpaceCommon spaceCommon=null;
								if(Utils.enableRedisCache()){
									spaceCommon = SpaceCommonCacher.get(designPlan.get(0).getSpaceCommonId());
								}else{
									spaceCommon = spaceCommonService.get(designPlan.get(0).getSpaceCommonId());
								}
								
								design.setSpaceName(spaceCommon.getSpaceName());
							}
						}
						
					}
					if(design.getPicId()!=null && !"".equals(design.getPicId())){
						if(design.getPicId().contains(",")){
							String picIds[] = design.getPicId().split(",");
							ResPic resPic=null;
							if(Utils.enableRedisCache()){
								resPic = ResourceCacher.getPic(Utils.getIntValue(picIds[picIds.length-1]));
							}else{
								resPic = resPicService.get(Utils.getIntValue(picIds[picIds.length-1]));
							}
							
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}else{
							ResPic resPic=null;
							if(Utils.enableRedisCache()){
								resPic = ResourceCacher.getPic(Utils.getIntValue(design.getPicId()));
							}else{
								resPic = resPicService.get(Utils.getIntValue(design.getPicId()));
							}
							
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}
					}
                    //设计方案
                    if("6".equals(design.getSrcType())){
                    	List<DesignPlan> ld=null;
                    	if(Utils.enableRedisCache()){
                    		ld = DesignCacher.getAllPlantListWithSysCode(design.getSrcCode());
                    	}else{
                    		DesignPlan desPlan = new DesignPlan();
            				desPlan.setSysCode(design.getSrcCode());
            				ld = designPlanService.getList(desPlan);
                    	}
                        
                        design.setDesignId((ld==null||ld.size()==0)?0:ld.get(0).getId());
                    }
                    //样板房7
                    if("7".equals(design.getSrcType())){
                    	List<DesignTemplet> lt=null;
                    	if(Utils.enableRedisCache()){
                    		lt = DesignTempletCacher.getAllTempletListWithSysCode(design.getSrcCode());
                    	}else{
                    		DesignTemplet desTemplet = new DesignTemplet();
            				desTemplet.setSysCode(design.getSrcCode());   
            				lt=designTempletService.getList(desTemplet);
                    	}
                        
                        design.setDesignId((lt==null||lt.size()==0)?0:lt.get(0).getId());
                    }
				}
			}

			if("small".equals(style) && list != null && list.size() > 0){
				 String designRecommendationJsonList = JsonUtil.getListToJsonData(list);
				 List<DesignRecommendationSmall> smallList= new JsonDataServiceImpl<DesignRecommendationSmall>().getJsonToBeanList(designRecommendationJsonList, DesignRecommendationSmall.class);
				 return new ResponseEnvelope<DesignRecommendationSmall>(total,smallList,designRecommendationSearch.getMsgId());
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRecommendation>(false, "数据异常!",designRecommendationSearch.getMsgId());
		}
		long end = System.currentTimeMillis();
		//////System.out.println("times = " + (end -start));
		return new ResponseEnvelope<DesignRecommendation>(total,list,designRecommendationSearch.getMsgId());
	}
	
  
	
	/**
     *    前台首页 热门设计
     *
     * @param
     * @return  Object
     */
	@RequestMapping(value = "/hotDesignList")
	public Object hotDesignList(@ModelAttribute("designRecommendationSearch") DesignRecommendationSearch designRecommendationSearch,HttpServletRequest request) {

		 
		List<DesignRecommendation> list = new ArrayList<DesignRecommendation> ();
		//首页是否显示  1显示，0不显示。
		designRecommendationSearch.setHomeIsDisplayed(1);
		int total = 0;
		try {
			total = designRecommendationService.getCount(designRecommendationSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designRecommendationService.getPaginatedList(designRecommendationSearch);
				for(DesignRecommendation design : list){
					if("7".equals(design.getSrcType())){//样板间
						DesignTemplet desTemplet = new DesignTemplet();
						desTemplet.setSysCode(design.getSrcCode());   																																			
						List<DesignTemplet> designTemplet = designTempletService.getList(desTemplet);
						if(designTemplet.size()>0){
							if(designTemplet.get(0)!=null){
								SpaceCommon spaceCommon = spaceCommonService.get(designTemplet.get(0).getSpaceCommonId());
								design.setSpaceName(spaceCommon.getSpaceName());
							}
						}
						
					}else if ("6".equals(design.getSrcType())){//设计方案
						DesignPlan desPlan = new DesignPlan();
						desPlan.setSysCode(design.getSrcCode());
						List<DesignPlan> designPlan = designPlanService.getList(desPlan);
						if(designPlan.size()>0){
							if(designPlan.get(0)!=null){
								SpaceCommon spaceCommon = spaceCommonService.get(designPlan.get(0).getSpaceCommonId());
								design.setSpaceName(spaceCommon.getSpaceName());
							}
						}
						
					}
					if(design.getPicId()!=null && !"".equals(design.getPicId())){
						if(design.getPicId().contains(",")){
							String picIds[] = design.getPicId().split(",");
							ResPic resPic = resPicService.get(Utils.getIntValue(picIds[picIds.length-1]));
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}else{
							ResPic resPic = resPicService.get(Utils.getIntValue(design.getPicId()));
							if(resPic!=null){
								design.setPicPath(resPic.getPicPath());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignRecommendation>(false, "数据异常!");
		}
		request.setAttribute("list", list);
		return  "/online/index";
	}
	
	/**
	 * 首页列表详情
	 * 
	 * @param designPlanId
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/detail")
	public String detail(String srcCode,String srcType,HttpServletRequest request)
			throws Exception {
		if(srcCode==null || srcType==null){
			return "";
		}
		//样板间
		if("7".equals(srcType)){
			DesignTempletResult templetResult = new DesignTempletResult();
			DesignTemplet desTemplet = new DesignTemplet();
			desTemplet.setSysCode(srcCode);
			List<DesignTemplet> templetList = designTempletService.getList(desTemplet);
			if(templetList!=null && templetList.size()>0){
				DesignTemplet designTemplet = templetList.get(0);
				if(designTemplet != null && designTemplet.getId() != null){
					templetResult = designTempletService.getDesignList(designTemplet.getId());
					if(templetResult != null){
						String renderPicIds = templetResult.getRenderPicIds();
						if (renderPicIds!=null && !"".equals(renderPicIds)) {
							if(renderPicIds.contains(",")){
								String[] strs = renderPicIds.split(",");
								for(String picId : strs){
									ResPic resPic = resPicService.get(Utils.getIntValue(picId));
									if (resPic != null) {
										templetResult.getPicList().add(resPic.getPicPath());
									} 
								}
							}else{
								ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
								if (resPic != null) {
									templetResult.getPicList().add(resPic.getPicPath());
								} 
							}
						}
						templetResult.setDesignSource(srcType);
						//判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
//						templetResult.setCollectState(getDesignCollectState(templetResult.getDesignId(),srcType,request));
						//得到该方案的产品数量
						templetResult.setDesignProductCount(getDesignProductCount(templetResult.getDesignId(),request));
						//关注用到
						request.setAttribute("count", getDesignUserFans(templetResult.getUserId(),request));
					}
				}
			}
			request.setAttribute("designPlan", templetResult);
		}
		
		//设计方案
		if ("6".equals(srcType)){
			List<DesignPlanResult> list = new ArrayList<DesignPlanResult>();
			DesignPlanResult designResult = new DesignPlanResult();
			DesignPlan desPlan = new DesignPlan();
			desPlan.setSysCode(srcCode);
			List<DesignPlan> planList = designPlanService.getList(desPlan);
			if(planList != null && planList.size()>0){
				DesignPlan designPlan = planList.get(0);
				 
				if(designPlan!=null){
					list = designPlanService.getDesignList(designPlan);
					if(list!=null && list.size()>0){
						designResult = list.get(0);
					}
					if(designResult != null){
						//获取该方案的渲染图 路径
						String renderPicIds = designResult.getRenderPicIds();
						if (renderPicIds!=null && !"".equals(renderPicIds)) {
							if(renderPicIds.contains(",")){
								String[] strs = renderPicIds.split(",");
								for(String picId : strs){
									ResPic resPic = resPicService.get(Utils.getIntValue(picId));
									if (resPic != null) {
										designResult.getPicList().add(resPic.getPicPath());
									} 
								}
							}else{
								ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
								if (resPic != null) {
									designResult.getPicList().add(resPic.getPicPath());
								} 
							}
						}
						designResult.setDesignSource(srcType);
						//判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
//						designResult.setCollectState(getDesignCollectState(designResult.getDesignId(),srcType,request));
						//得到该方案的产品数量
						designResult.setDesignProductCount(getDesignProductCount(designResult.getDesignId(),request)); 
						//关注用到
						request.setAttribute("count", getDesignUserFans(designResult.getUserId(),request));
					} 
				}
			}
			request.setAttribute("designPlan", designResult);
		} 
		
		return "/online/newDesign/design_detail";
	}
	
	/**
	 * 获取设计产品数量
	 * @param designId
	 * @param request
	 * @return
	 */
	public Integer getDesignProductCount(Integer designId,HttpServletRequest request){
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setPlanId(designId);
		List<DesignPlanProduct> list = designPlanProductService.getList(designPlanProduct);
		if(list!=null && list.size()>0){
			return (Integer)list.size();
		}else{
			return 0;
		}
	}
	/**
	 * 是否被关注 1已关注, 0需关注
	 * @param designUserId
	 * @param request
	 * @return 
	 */
	public Integer getDesignUserFans(Integer designUserId,HttpServletRequest request){
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin"); 
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
		sysUserFansSearch.setFansUserId(loginUser.getId());
		sysUserFansSearch.setUserId(designUserId);
		return sysUserFansService.getCount(sysUserFansSearch);
	}
}
