package com.nork.home.controller.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.util.WebUtils;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.client.model.UploadFileResponse;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.HouseSpaceResult;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.home.service.BaseHouseService;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: WebSpaceCommonController.java
 * @Package com.nork.home.web.controller
 * @Description:户型房型-通用空间表Controller
 * @createAuthor xiaoxc
 * @CreateDate 2015-09-17 19:48:39
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/home/spaceCommon")
public class WebSpaceCommonController {
	private static Logger logger = Logger.getLogger(WebSpaceCommonController.class);

	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	
	/**
	 * 通过户型过滤空间布局图
	 * 
	 * @param houseId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/houseSpaceList")
	public Object houseSpaceList(Integer houseId, HttpServletRequest request, HttpServletResponse response) {
		List<HouseSpaceResult> list = new ArrayList<HouseSpaceResult>();
		if (houseId == null) {
			return new ResponseEnvelope<SpaceCommon>(false, "传参异常!", "none");
		}
		BaseHouse baseHouse = baseHouseService.get(houseId);
		request.setAttribute("houseType", baseHouse != null ? baseHouse.getHouseCommonCode() : "");
		try {
			list = spaceCommonService.getHouseSpaceList(houseId);

			if (CustomerListUtils.isNotEmpty(list)) {
				for (HouseSpaceResult houseSpace : list) {
					String view3dPic = houseSpace.getView3dPicId();
					if (!StringUtils.isEmpty(view3dPic)) {
						if (view3dPic.contains(",")) {
							String str[] = view3dPic.split(",");
							ResPic resPic = resPicService.get(Integer.parseInt(str[0]));
							houseSpace.setViewPicPath(resPic == null ? "" : resPic.getPicPath());
						} else {
							ResPic resPic = resPicService.get(Integer.parseInt(view3dPic));
							houseSpace.setViewPicPath(resPic == null ? "" : resPic.getPicPath());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SpaceCommon>(false, "数据异常!", "none");
		}
		String livingId = (String) request.getParameter("livingId");
		String province = (String) request.getParameter("province");
		String city = (String) request.getParameter("city");
		String livingName = (String) request.getParameter("livingName");
		request.setAttribute("livingId", livingId);
		request.setAttribute("province", province);
		request.setAttribute("city", city);
		request.setAttribute("livingName", livingName);
		request.setAttribute("list", list);

		return "/online/home/house_space_list";

	}

	/**
	 * 通过户型过滤空间布局图
	 * 
	 * @param houseId
	 * @param limit
	 * @param start
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/houseSpaceListWeb")
	@ResponseBody
	public Object houseSpaceList(@RequestParam(value = "houseId", required = false) String houseId,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start,
			HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		String msg = "";
		if (StringUtils.isBlank(houseId)) {
			msg = "参数houseId不能为空!";
			return new ResponseEnvelope<HouseSpaceResult>(false, msg, msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空!";
			return new ResponseEnvelope<HouseSpaceResult>(false, msg, msgId);
		}
		
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
    	/*获取黑名单配置信息*/
    	/*获得黑名单配置开关*/
    	//Integer spaceCommonBlacklistEnable=Integer.valueOf(Utils.getValue("spaceCommonBlacklistEnable", "0"));
		Integer spaceCommonBlacklistEnable=0;
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklistEnable");
        if(sysDictionary1!=null){
        	try{
        		spaceCommonBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
        	}catch(Exception e){
        		throw new RuntimeException("配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="+sysDictionary1.getAtt1());
        	}
        }
    	Set<String> blacklist=new HashSet<String>();
    	if(spaceCommonBlacklistEnable==1){
    		/*获取黑名单配置*/
    		//String spaceCommonBlacklist=Utils.getValue("spaceCommonBlacklist", "");
    		String spaceCommonBlacklist="";
    		SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklist");
    		if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
    			spaceCommonBlacklist=sysDictionary2.getAtt2();
    		Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(spaceCommonBlacklist);
    		/*处理类似于"!xingx"的值*/
    		categoryBlacklistMap=spaceCommonService.dealWithMap(categoryBlacklistMap);
    		/*得到用户所属类型:序列号->公司->小类*/
    		Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
    		/*获取产品类型黑名单*/
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
    	}
    	/*获取黑名单配置信息->end*/
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件*/
    	Set<Integer> spaceFunctionIdblackList=new HashSet<Integer>();
    	for(String blackValueKey:blacklist){
    		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("houseType", blackValueKey);
    		if(sysDictionary!=null)
    			spaceFunctionIdblackList.add(sysDictionary.getValue());
    	}
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件->end*/
		int userType = loginUser==null?-1:(loginUser.getUserType()==null?-1:loginUser.getUserType());
    	SpaceCommon spaceCommonTemporary=new SpaceCommon();
    	spaceCommonTemporary.setHouseId(Integer.parseInt(houseId));
    	spaceCommonTemporary.setUserType(userType);
    	spaceCommonTemporary.setBlackList(spaceFunctionIdblackList);
    	String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
    	spaceCommonTemporary.setVersionType(versionType);
		int total = 0;
		List<HouseSpaceResult> list = new ArrayList<HouseSpaceResult>();
		try {
			if(Utils.enableRedisCache()){
				if(userType ==1){
					//total = SpaceCommonCacher.getHouseSpaceListCount2(Integer.parseInt(houseId),userType);
					total = SpaceCommonCacher.getHouseSpaceListCount2(spaceCommonTemporary,loginUser.getId());
				}else{
					//total = SpaceCommonCacher.getHouseSpaceListCount(Integer.parseInt(houseId));
					total = SpaceCommonCacher.getHouseSpaceListCount(spaceCommonTemporary,loginUser.getId());
				}
			}else{
				if(userType ==1){
					//total=spaceCommonService.getHouseSpaceListCount2(Integer.parseInt(houseId));
					total=spaceCommonService.getHouseSpaceListCount2(spaceCommonTemporary,loginUser.getId());
				}else{
					//total=spaceCommonService.getHouseSpaceListCount(Integer.parseInt(houseId));
					total=spaceCommonService.getHouseSpaceListCount(spaceCommonTemporary,loginUser.getId());
				}
			}
			
			/*设置分页查询条件*/
			spaceCommonTemporary.setStart(StringUtils.isBlank(start) ? -1 : Integer.parseInt(start));
			spaceCommonTemporary.setLimit(StringUtils.isBlank(limit) ? -1 : Integer.parseInt(limit));
			/*设置分页查询条件->end*/
			logger.info("total:" + total);
			if (total > 0) {
				if(Utils.enableRedisCache()){
					if(userType ==1){
						/*list =  SpaceCommonCacher.getPaginatedHouseSpaceList2(Integer.parseInt(houseId),
										StringUtils.isBlank(start) ? -1 : Integer.parseInt(start),
										StringUtils.isBlank(limit) ? -1 : Integer.parseInt(limit),userType);*/
						list =  SpaceCommonCacher.getPaginatedHouseSpaceList2(spaceCommonTemporary,loginUser.getId());
					}else{
						/*list =  SpaceCommonCacher.getPaginatedHouseSpaceList(Integer.parseInt(houseId),
								StringUtils.isBlank(start) ? -1 : Integer.parseInt(start),
								StringUtils.isBlank(limit) ? -1 : Integer.parseInt(limit));*/
						list =  SpaceCommonCacher.getPaginatedHouseSpaceList(spaceCommonTemporary,loginUser.getId());
					}
					
				}else{
					if(userType==1){
						 /*list = spaceCommonService.getPaginatedHouseSpaceList2(Integer.
								 parseInt(houseId),StringUtils.isBlank(start)?-1:Integer.
								 parseInt(start),StringUtils.isBlank(limit)?-1:Integer.
								 parseInt(limit),userType);*/
						list = spaceCommonService.getPaginatedHouseSpaceList2(spaceCommonTemporary,loginUser.getId());
					}else{
						 /*list =	 spaceCommonService.getPaginatedHouseSpaceList(Integer.
								 parseInt(houseId),StringUtils.isBlank(start)?-1:Integer.
								 parseInt(start),StringUtils.isBlank(limit)?-1:Integer.
								 parseInt(limit));*/
						list =	 spaceCommonService.getPaginatedHouseSpaceList(spaceCommonTemporary,loginUser.getId());
					}
				}

			}
			if (CustomerListUtils.isNotEmpty(list)) {
				for (HouseSpaceResult houseSpace : list) {
					SpaceCommon spaceCommon=new SpaceCommon();
					spaceCommon.setId(houseSpace.getSpaceId());
					spaceCommonService.setPicParams(spaceCommon);
					SysDictionary sysDictionary = new SysDictionary();
					sysDictionary.setType("houseType");
					sysDictionary.setValue(houseSpace.getSpaceFunctionId());
					List<SysDictionary> sdList = sysDictionaryService.getList(sysDictionary);
					if(sdList.size()>0) {
						houseSpace.setFunctionAreas(sdList.get(0).getName()+houseSpace.getSpaceAreas()+"平");
					}
					/*首页图*/
					houseSpace.setViewPicPath(spaceCommon.getView3dPicPath());
					houseSpace.setViewPicSmallPath(spaceCommon.getView3dSmallPicPath());
					/*平面图*/
					houseSpace.setViewPlanPath(spaceCommon.getViewPlanPath());
					houseSpace.setViewPlanSmallPath(spaceCommon.getViewPlanSmallPath());
				}
//				String mediaType = (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
//						|| request.getSession().getAttribute("mediaType") == null) ? "2"
//								: (String) request.getSession().getAttribute("mediaType");
//				for (HouseSpaceResult houseSpace : list) {
//					String view3dPic = houseSpace.getView3dPicId();
//					if (!StringUtils.isEmpty(view3dPic)) {
//						if (view3dPic.contains(",")) {
//							String str[] = view3dPic.split(",");
//							ResHousePic resPic=null;
//							if(Utils.enableRedisCache()){
//								resPic =  ResourceCacher.getHousePic(Integer.parseInt(str[0]));
//							}else{
//								resPic =  resHousePicService.get(Integer.parseInt(str[0]));
//							}
////							resPic=resHousePicService.get(Integer.parseInt(str[0]));
//							ResHousePic smallResHousePic=resHousePicService.get(Utils.getSmallPicId(resPic, "ipad"));
//							//houseSpace.setViewPicPath(resPic==null?"":Utils.getSmallPath(resPic.getPicPath(),mediaType));
//							houseSpace.setViewPicPath(resPic == null ? "" : (Utils.isEmpty(resPic.getPicPath())?"":resPic.getPicPath()));
//							houseSpace.setViewPicSmallPath(smallResHousePic==null?"":(Utils.isBlank(smallResHousePic.getPicPath())?"":smallResHousePic.getPicPath()));
//						} else {
//							ResHousePic resPic=null;
//							if(Utils.enableRedisCache()){
//								resPic = ResourceCacher.getHousePic(Integer.parseInt(view3dPic));
//							}else{
//								resPic =  resHousePicService.get(Integer.parseInt(view3dPic));
//							}
//							/*ResHousePic resPic=null;
//							resPic=resHousePicService.get(Integer.parseInt(view3dPic));*/
//							houseSpace.setViewPicPath(resPic == null ? "" : (Utils.isEmpty(resPic.getPicPath())?"":resPic.getPicPath()));
//							ResHousePic smallResHousePic=resHousePicService.get(Utils.getSmallPicId(resPic, "ipad"));
//							//houseSpace.setViewPicPath(resPic==null?"":Utils.getSmallPath(resPic.getPicPath(),mediaType));
//							houseSpace.setViewPicSmallPath(smallResHousePic==null?"":(Utils.isBlank(smallResHousePic.getPicPath())?"":smallResHousePic.getPicPath()));
//						}
//					}
//					/*关联查询出空间俯视平面图缩略图url*/
//					String viewPlanIds = houseSpace.getViewPlanIds();
//					if (!StringUtils.isEmpty(viewPlanIds)) {
//						if (viewPlanIds.contains(",")) {
//							String str[] = viewPlanIds.split(",");
//							//ResPic resPic = resPicService.get(Integer.parseInt(str[0]));
//							ResHousePic resPic = null;
//							if(Utils.enableRedisCache()){
//								resPic = ResourceCacher.getHousePic(Integer.parseInt(str[0]));
//							}else{
//								resPic =   resHousePicService.get(Integer.parseInt(str[0]));
//							}
//							houseSpace.setViewPlanPath(resPic == null ? "" : (Utils.isEmpty(resPic.getPicPath())?"":resPic.getPicPath()));
//							ResHousePic smallResHousePic=resHousePicService.get(Utils.getSmallPicId(resPic, "ipad"));
//							//houseSpace.setViewPlanSmallPath(resPic==null?"":Utils.getSmallPath(resPic.getPicPath(),mediaType));
//							houseSpace.setViewPlanSmallPath(smallResHousePic==null?"":(Utils.isBlank(smallResHousePic.getPicPath())?"":smallResHousePic.getPicPath()));
//						} else {
//							//ResPic resPic = resPicService.get(Integer.parseInt(viewPlanIds));
//							ResHousePic resPic = null; //resHousePicService.get(Integer.parseInt(viewPlanIds));
//							
//							if(Utils.enableRedisCache()){
//								resPic = ResourceCacher.getHousePic(Integer.parseInt(viewPlanIds));
//							}else{
//								resPic =   resHousePicService.get(Integer.parseInt(viewPlanIds));
//							}
//							houseSpace.setViewPlanPath(resPic == null ? "" : (Utils.isEmpty(resPic.getPicPath())?"":resPic.getPicPath()));
//							ResHousePic smallResHousePic=resHousePicService.get(Utils.getSmallPicId(resPic, "ipad"));
//							//houseSpace.setViewPlanSmallPath(resPic==null?"":Utils.getSmallPath(resPic.getPicPath(),mediaType));
//							houseSpace.setViewPlanSmallPath(smallResHousePic==null?"":(Utils.isBlank(smallResHousePic.getPicPath())?"":smallResHousePic.getPicPath()));
//						}
//					}
//				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<HouseSpaceResult>(false, "数据异常!", msgId);
		}
		Long endTime = System.currentTimeMillis();
		//////System.out.println("times:" + (endTime - startTime));
		return new ResponseEnvelope<HouseSpaceResult>(total, list, msgId);
	}

	/* 查找所有空间名 */
	@RequestMapping(value = "/spaceCommonNameList")
	@ResponseBody
	public Object spaceCommonNameList() {
		List<String> spaceCommonNames = new ArrayList<String>();
		spaceCommonNames = spaceCommonService.findAllName();
		Set<String> set = new HashSet<String>();
		for (String str : spaceCommonNames) {
			if (StringUtils.isNotBlank(str)) {
				set.add(str);
			}
		}
		return set;
	}

	/**
	 * 空间布局列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/spaceSearch")
	public Object spaceSearch(HttpServletRequest request, HttpServletResponse response) {
		List<SpaceCommon> list = new ArrayList<SpaceCommon>();

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
          return  "/online/design/space_layout_list";
        }
	        
		// 空间类型
		String spaceFunctionId = request.getParameter("spaceFunctionId");
		// 面积
		String areaValue = request.getParameter("areaValue");
		String spaceShape = request.getParameter("spaceShape");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int tatol = 0;
		try {
			SpaceCommon spaceCommon = new SpaceCommon();
			// SpaceCommonSearch spaceCommon = new SpaceCommonSearch();
			if (spaceFunctionId != null && spaceFunctionId.length() > 0) {
				if (StringUtils.isNotBlank(start)) {
					spaceCommon.setStart(Integer.parseInt(start));
				}
				if (StringUtils.isNotBlank(limit)) {
					spaceCommon.setLimit(Integer.parseInt(limit));
				}
				if (StringUtils.isNotBlank(spaceShape)) {
					spaceCommon.setSpaceShape(spaceShape);
				}
				spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));// 空间功能类型
				spaceCommon.setSpaceAreas(areaValue);// 空间面积
				// 只查询标准空间
				spaceCommon.setPid(0);// 缩略图
				spaceCommon.setIsStandardSpace(1);// 是否是标准空间
				// 根据选择空间传空间名称到页面
				SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("houseType",
						Integer.valueOf(spaceFunctionId));
				request.setAttribute("spaceName", sysDictionary == null ? "" : sysDictionary.getName());
				tatol = spaceCommonService.getSpaceSearchCount(spaceCommon,loginUser.getId());
				if (tatol > 0) {
					list = spaceCommonService.getSpaceSearchList(spaceCommon,loginUser.getId());
				}
				// list=spaceCommonService.getPaginatedList(spaceCommon);
				String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
				if (CustomerListUtils.isNotEmpty(list)) {
					for (SpaceCommon space : list) {
						String view3dPic = space.getView3dPic();
						if (!StringUtils.isEmpty(view3dPic)) {
							if (view3dPic.contains(",")) {
								String str[] = view3dPic.split(",");
								ResPic resPic = resPicService.get(Integer.parseInt(str[0]));
								space.setPicPath(
										resPic == null ? "" : Utils.getSmallPath(resPic.getPicPath(), mediaType));
								space.setView3dSmallPicPath(
										resPic == null ? "" : Utils.getSmallPath(resPic.getPicPath(), mediaType));
							} else {
								ResPic resPic = resPicService.get(Integer.parseInt(view3dPic));
								space.setPicPath(
										resPic == null ? "" : Utils.getSmallPath(resPic.getPicPath(), mediaType));
								space.setView3dSmallPicPath(
										resPic == null ? "" : Utils.getSmallPath(resPic.getPicPath(), mediaType));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEnvelope<SpaceCommon> res = new ResponseEnvelope<SpaceCommon>(tatol, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("doorLocationIdText", "");
		request.setAttribute("doorLocationId", "");
		/* 绑定上一个接口的data信息 */
		request.setAttribute("spaceFunctionId", spaceFunctionId);
		// 面积
		request.setAttribute("areaValue", areaValue);
		request.setAttribute("start", start);
		request.setAttribute("limit", limit);
		return "/online/design/space_layout_list";

	}

	/**
	 * 空间布局列表查询接口:根据空间类型、面积查询出空间列表
	 * 
	 * @param spaceFunctionId
	 * @param sch_SpaceCode 空间编码
	 * @param areaValue
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@RequestMapping(value = "/spaceSearchWeb")
	@ResponseBody
	public Object spaceSearch(/*
								 * @ModelAttribute("spaceCommonSearch")
								 * SpaceCommonSearch spaceCommonSearch,
								 */
			@RequestParam(value = "spaceFunctionId", required = false) String spaceFunctionId/* 空间类型 */,
			@RequestParam(value = "areaValue", required = false) String areaValue/* 面积 */, HttpServletRequest request,
			@RequestParam(value = "spaceShape", required = false) String spaceShape,
			@RequestParam(value=  "sch_SpaceCode", required=false)String sch_SpaceCode,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) {
		Long startTime = System.currentTimeMillis();
		String msg = "";
		if (StringUtils.isBlank(areaValue)) {
			msg = "参数areaValue不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		if (StringUtils.isBlank(spaceFunctionId)) {
			msg = "参数spaceFunctionId不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		//loginUser.setUserType(3);
		if(loginUser==null){
			logger.info("------查询样板房接口->无法识别登录用户");
			throw new RuntimeException("------查询样板房接口->无法识别登录用户");
		}
    	/*获取黑名单配置信息*/
    	/*获得黑名单配置开关*/
    	//Integer spaceCommonBlacklistEnable=Integer.valueOf(Utils.getValue("spaceCommonBlacklistEnable", "0"));
		Integer spaceCommonBlacklistEnable=0;
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklistEnable");
        if(sysDictionary1!=null){
        	try{
        		spaceCommonBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
        	}catch(Exception e){
        		throw new RuntimeException("配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="+sysDictionary1.getAtt1());
        	}
        }
    	Set<String> blacklist=new HashSet<String>();
    	if(spaceCommonBlacklistEnable==1){
    		/*获取黑名单配置*/
    		//String spaceCommonBlacklist=Utils.getValue("spaceCommonBlacklist", "");
    		String spaceCommonBlacklist="";
    		SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklist");
    		if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
    			spaceCommonBlacklist=sysDictionary2.getAtt1();
    		Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(spaceCommonBlacklist);
    		/*处理类似于"!xingx"的值*/
    		categoryBlacklistMap=spaceCommonService.dealWithMap(categoryBlacklistMap);
    		/*得到用户所属类型:序列号->公司->小类*/
    		Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
    		/*获取产品类型黑名单*/
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
    	}
    	/*获取黑名单配置信息->end*/
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件*/
    	Set<Integer> spaceFunctionIdblackList=new HashSet<Integer>();
    	for(String blackValueKey:blacklist){
    		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("houseType", blackValueKey);
    		if(sysDictionary!=null)
    			spaceFunctionIdblackList.add(sysDictionary.getValue());
    	}
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件->end*/
		SpaceCommon spaceCommon = new SpaceCommon();
		spaceCommon.setIsDeleted(0);
		spaceCommon.setSch_spaceCode(sch_SpaceCode);
		if(spaceCommonBlacklistEnable==1){
			spaceCommon.setBlackList(spaceFunctionIdblackList);
		}
		int total = 0;
		List<SpaceCommon> list = new ArrayList<SpaceCommon>();
		try {
			spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));// 空间功能类型
			spaceCommon.setSpaceAreas(areaValue);// 空间面积
			if (StringUtils.isNotBlank(spaceShape)) {
				spaceCommon.setSpaceShape(spaceShape);
			}
			// 只查询标准空间(下面两个条件为标准空间)
			spaceCommon.setPid(0);// 缩略图
			spaceCommon.setIsStandardSpace(1);// 是否是标准空间
			if (StringUtils.isNotBlank(start)) {
				spaceCommon.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				spaceCommon.setLimit(Integer.parseInt(limit));
			}
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState*/
			Integer spaceIntegers[] = {};
			Integer designTempletIntegers[] = {};
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if(loginUser.getUserType()==1 && "2".equals(versionType)){
				spaceIntegers=new Integer[]{1,3,5}; /*1上架  5测试  3为增加 的发布*/
				designTempletIntegers = new Integer[]{1,2,3}; /*1上架  2测试  3为增加 的发布*/
			}else{
				spaceCommon.setStatus(SpaceCommonStatus.IS_RELEASE.intValue());
			}
			//spaceCommon.setPutawayStates(Arrays.asList(integers));
			spaceCommon.setStatusList(Arrays.asList(spaceIntegers));
			spaceCommon.setPutawayStates(Arrays.asList(designTempletIntegers));
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end*/
			total=spaceCommonService.getSpaceSearchCount(spaceCommon,loginUser.getId());	 
			if (total > 0) {
				list = spaceCommonService.getSpaceSearchList(spaceCommon,loginUser.getId());	  
			}
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			//String type = Utils.getMdeiaType(mediaType) ;
			for(SpaceCommon spaceCommonSingle:list){
				/*查询view3dPicPath,view3dSmallPicPath,viewPlanPath,viewPlanSmallPath*/
				spaceCommonService.setPicParams(spaceCommonSingle);
			}
//
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SpaceCommon>(false, "数据异常", msgId);
		}
		Long endTime = System.currentTimeMillis();
		//////System.out.println("times:" + String.valueOf(endTime - startTime));
		return new ResponseEnvelope<SpaceCommon>(total, list, msgId);
	}
	
	
	/**
	 * 空间列表查询接口:根据空间类型查询出空间列表
	 * 
	 * @param spaceFunctionId 空间类型ID
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@RequestMapping(value = "/spaceSearchList")
	@ResponseBody
	public Object spaceSearchList(
			@RequestParam(value = "spaceFunctionId", required = false) String spaceFunctionId,
			HttpServletRequest request,
			@RequestParam(value = "spaceShape", required = false) String spaceShape,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) {
		Long startTime = System.currentTimeMillis();
		String msg = "";
		if (StringUtils.isBlank(spaceFunctionId)) {
			msg = "参数spaceFunctionId不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null){
			logger.info("------查询样板房接口->无法识别登录用户");
			throw new RuntimeException("------查询样板房接口->无法识别登录用户");
		}
    	/*获取黑名单配置信息*/
		Integer spaceCommonBlacklistEnable=0;
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklistEnable");
        if(sysDictionary1!=null){
        	try{
        		spaceCommonBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
        	}catch(Exception e){
        		throw new RuntimeException("配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="+sysDictionary1.getAtt1());
        	}
        }
    	Set<String> blacklist=new HashSet<String>();
    	if(spaceCommonBlacklistEnable==1){
    		String spaceCommonBlacklist="";
    		SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklist");
    		if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
    			spaceCommonBlacklist=sysDictionary2.getAtt1();
    		Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(spaceCommonBlacklist);
    		/*处理类似于"!xingx"的值*/
    		categoryBlacklistMap=spaceCommonService.dealWithMap(categoryBlacklistMap);
    		/*得到用户所属类型:序列号->公司->小类*/
    		Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
    		/*获取产品类型黑名单*/
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
    	}
    	/*获取黑名单配置信息->end*/
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件*/
    	Set<Integer> spaceFunctionIdblackList=new HashSet<Integer>();
    	for(String blackValueKey:blacklist){
    		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("houseType", blackValueKey);
    		if(sysDictionary!=null)
    			spaceFunctionIdblackList.add(sysDictionary.getValue());
    	}
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件->end*/
		SpaceCommon spaceCommon = new SpaceCommon();
		spaceCommon.setIsDeleted(0);
		if(spaceCommonBlacklistEnable==1){
			spaceCommon.setBlackList(spaceFunctionIdblackList);
		}
		int total = 0;
		List<SpaceCommon> list = new ArrayList<SpaceCommon>();
		try {
			spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));// 空间功能类型
			if (StringUtils.isNotBlank(spaceShape)) {
				spaceCommon.setSpaceShape(spaceShape);
			}
			// 只查询标准空间(下面两个条件为标准空间)
			spaceCommon.setPid(0);
			spaceCommon.setIsStandardSpace(1);
			if (StringUtils.isNotBlank(start)) {
				spaceCommon.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				spaceCommon.setLimit(Integer.parseInt(limit));
			}
			Integer[] integers = {};
			if( loginUser.getUserType()!=1 ){
				/*spaceCommon.setStatus(1);*/
				spaceCommon.setStatus(SpaceCommonStatus.IS_RELEASE.intValue());
			}else{
				integers=new Integer[]{1,3,5};/*1上架  3发布 5测试 为增加 的发布*/
			}

			spaceCommon.setPutawayStates(Arrays.asList(integers));
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end*/
			if(Utils.enableRedisCache()){
				total = SpaceCommonCacher.getTotalWithParameter(spaceCommon,loginUser.getId());
			}else{
				total=spaceCommonService.getSpaceSearchCount(spaceCommon,loginUser.getId());
			}
			 
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = SpaceCommonCacher.getPageWithParameter(spaceCommon,loginUser.getId());
				}else{
					list = spaceCommonService.getSpaceSearchList(spaceCommon,loginUser.getId());
				}
			}
			for(SpaceCommon spaceCommonSingle:list){
				spaceCommonService.setPicParams(spaceCommonSingle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SpaceCommon>(false, "数据异常", msgId);
		}
		Long endTime = System.currentTimeMillis();
		//System.out.println("times:" + String.valueOf(endTime - startTime));
		return new ResponseEnvelope<SpaceCommon>(total, list, msgId);
	}


	/**
	 * 推荐样板房空间列表：条件（标准，发布，公开）
	 *
	 * @param spaceFunctionId 空间类型ID
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@RequestMapping(value = "/recommendSpaceList")
	@ResponseBody
	public Object recommendSpaceList(
			@RequestParam(value = "spaceFunctionId", required = false, defaultValue = "1") String spaceFunctionId,
			HttpServletRequest request,
			@RequestParam(value = "spaceShape", required = false) String spaceShape,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) {
		Long startTime = System.currentTimeMillis();
		String msg = "";
		if (StringUtils.isBlank(spaceFunctionId)) {
			msg = "参数spaceFunctionId不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null){
			logger.info("------查询样板房接口->无法识别登录用户");
			throw new RuntimeException("------查询推荐样板房接口->无法识别登录用户");
		}
    	/*获取黑名单配置信息*/
//		Integer spaceCommonBlacklistEnable=0;
//		SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklistEnable");
//		if(sysDictionary1!=null){
//			try{
//				spaceCommonBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
//			}catch(Exception e){
//				throw new RuntimeException("配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="+sysDictionary1.getAtt1());
//			}
//		}

		//黑名单的意思是如：做马桶的公司只能看到卫生间
//		Set<String> blacklist=new HashSet<String>();
//		if(spaceCommonBlacklistEnable==1){
//			String spaceCommonBlacklist="";
//			SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklist");
//			if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
//				spaceCommonBlacklist=sysDictionary2.getAtt1();
//			Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(spaceCommonBlacklist);
//    		/*处理类似于"!xingx"的值*/
//			categoryBlacklistMap=spaceCommonService.dealWithMap(categoryBlacklistMap);
//    		/*得到用户所属类型:序列号->公司->小类*/
//			Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
//    		/*获取产品类型黑名单*/
//			boolean flag=false;
//			for(String typeStr:typeSet){
//				if(categoryBlacklistMap.containsKey(typeStr))
//					if(!flag){
//						flag=true;
//						blacklist.addAll(categoryBlacklistMap.get(typeStr));
//					}else{
//						blacklist.retainAll(categoryBlacklistMap.get(typeStr));
//					}
//			}
//		}
//    	/*获取黑名单配置信息->end*/
//    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件*/
//		Set<Integer> spaceFunctionIdblackList=new HashSet<Integer>();
//		for(String blackValueKey:blacklist){
//			SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("houseType", blackValueKey);
//			if(sysDictionary!=null)
//				spaceFunctionIdblackList.add(sysDictionary.getValue());
//		}
//    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件->end*/
		SpaceCommon spaceCommon = new SpaceCommon();
//		spaceCommon.setIsDeleted(0);
//		if(spaceCommonBlacklistEnable==1){
//			spaceCommon.setBlackList(spaceFunctionIdblackList);
//		}
		int total = 0;
		List<SpaceCommon> list = new ArrayList<SpaceCommon>();
		try {
			spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));// 空间功能类型
			if (StringUtils.isNotBlank(spaceShape)) {
				spaceCommon.setSpaceShape(spaceShape);
			}
			// 只查询标准空间(下面两个条件为标准空间)
			spaceCommon.setPid(0);
			spaceCommon.setIsStandardSpace(1);// 是否是标准空间
			spaceCommon.setOpenStatus(1);//公开
			if (StringUtils.isNotBlank(start)) {
				spaceCommon.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				spaceCommon.setLimit(Integer.parseInt(limit));
			}
			if( loginUser.getUserType() != 1 ){
				/*spaceCommon.setStatus(1);*/
				spaceCommon.setStatus(SpaceCommonStatus.IS_RELEASE.intValue());
			}
//			Integer[] integers=new Integer[]{1,2};
//			spaceCommon.setPutawayStates(Arrays.asList(integers));
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end*/
			if(Utils.enableRedisCache()){
				total = SpaceCommonCacher.getTotalWithParameter(spaceCommon,loginUser.getId());
			}else{
				total=spaceCommonService.getSpaceSearchCount(spaceCommon,loginUser.getId());
			}
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = SpaceCommonCacher.getPageWithParameter(spaceCommon,loginUser.getId());
				}else{
					list = spaceCommonService.getSpaceSearchList(spaceCommon,loginUser.getId());
				}
			}
			for(SpaceCommon spaceCommonSingle:list){
				spaceCommonService.setPicParams(spaceCommonSingle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SpaceCommon>(false, "数据异常", msgId);
		}
		Long endTime = System.currentTimeMillis();
		//System.out.println("times:" + String.valueOf(endTime - startTime));
		return new ResponseEnvelope<SpaceCommon>(total, list, msgId);
	}
	
	
	
	/**
	 * U3D空间智能搜索
	 * 
	 * @param spaceFunctionId
	 * @param areaValue
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@RequestMapping(value = "/spaceCapacity")
	@ResponseBody
	public Object spaceCapacity(
			HttpServletRequest request,
			@RequestParam(value = "spaceAnalyzeCode", required = false) String spaceAnalyzeCode, /*空间编码*/
			@RequestParam(value = "spaceShape", required = false) String spaceShape,    /*形状*/
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) {
		String cacheEnable=Utils.getValue("redisCacheEnable", "0");
		String msg = "";
		if (StringUtils.isBlank(spaceAnalyzeCode)) {
			msg = "参数spaceAnalyzeCode不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		if (StringUtils.isBlank(spaceShape)) {
			msg = "参数spaceShape不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null){
			logger.info("------查询样板房接口->无法识别登录用户");
			throw new RuntimeException("------查询推荐样板房接口->无法识别登录用户");
		}
    	/*获取黑名单配置信息*/
    	/*获得黑名单配置开关*/
		Integer spaceCommonBlacklistEnable=0;
        SysDictionary sysDictionary1=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklistEnable");
        if(sysDictionary1!=null){
        	try{
        		spaceCommonBlacklistEnable=Integer.valueOf(sysDictionary1.getAtt1());
        	}catch(Exception e){
        		throw new RuntimeException("配置格式错误,type=configuration,valueKey=spaceCommonBlacklistEnable,att1="+sysDictionary1.getAtt1());
        	}
        }
    	Set<String> blacklist=new HashSet<String>();
    	if(spaceCommonBlacklistEnable==1){
    		/*获取黑名单配置*/
    		String spaceCommonBlacklist="";
    		SysDictionary sysDictionary2=sysDictionaryService.findOneByTypeAndValueKey("configuration", "spaceCommonBlacklist");
    		if(sysDictionary2!=null&&sysDictionary2.getAtt1()!=null)
    			spaceCommonBlacklist=sysDictionary2.getAtt1();
    		Map<String,Set<String>> categoryBlacklistMap=Utils.getCategoryBlacklistMap(spaceCommonBlacklist);
    		/*处理类似于"!xingx"的值*/
    		categoryBlacklistMap=spaceCommonService.dealWithMap(categoryBlacklistMap);
    		/*得到用户所属类型:序列号->公司->小类*/
    		Set<String> typeSet=baseCompanyService.getTypeSetByUserId(loginUser.getId());
    		/*获取产品类型黑名单*/
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
    	}
    	/*获取黑名单配置信息->end*/
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件*/
    	Set<Integer> spaceFunctionIdblackList=new HashSet<Integer>();
    	for(String blackValueKey:blacklist){
    		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("houseType", blackValueKey);
    		if(sysDictionary!=null)
    			spaceFunctionIdblackList.add(sysDictionary.getValue());
    	}
    	/*处理黑名单,根据valueKey查询value值,并加入到查询条件->end*/
		SpaceCommon spaceCommon = new SpaceCommon();
		spaceCommon.setIsDeleted(0);
		spaceCommon.setSpaceU3dCode(spaceAnalyzeCode);	//空间编码
		spaceCommon.setSpaceShapeInt(Integer.parseInt(spaceShape));              //形状
		if(spaceCommonBlacklistEnable==1){
			spaceCommon.setBlackList(spaceFunctionIdblackList);
		}
		int total = 0;
		List<SpaceCommon> list = new ArrayList<SpaceCommon>();
		try {
			// 只查询标准空间(下面两个条件为标准空间)
			spaceCommon.setPid(0);// 缩略图
			spaceCommon.setIsStandardSpace(1);// 是否是标准空间
			if (StringUtils.isNotBlank(start)) {
				spaceCommon.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				spaceCommon.setLimit(Integer.parseInt(limit));
			}
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState*/
			Integer spaceIntegers[] = {};
			Integer designTempletIntegers[] = {};
			String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/*1为外网  2  为内网    默认为外网*/
			if(loginUser.getUserType()==1 && "2".equals(versionType)){
				spaceIntegers=new Integer[]{1,3,5}; /*1上架  5测试  3为增加 的发布*/
				designTempletIntegers = new Integer[]{1,2,3}; /*1上架  2测试  3为增加 的发布*/
			}else{
				spaceCommon.setStatus(SpaceCommonStatus.IS_RELEASE.intValue());
			}
			spaceCommon.setStatusList(Arrays.asList(spaceIntegers));
			spaceCommon.setPutawayStates(Arrays.asList(designTempletIntegers));
			/*根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end*/
              
			if(StringUtils.equals("1", cacheEnable)){
				total = SpaceCommonCacher.getTotalParameter(spaceCommon);
			}else{
				total=spaceCommonService.spaceCapacityCount(spaceCommon);
			}
			 
			if (total > 0) {
				if(StringUtils.equals("1", cacheEnable)){
					list = SpaceCommonCacher.getCapacityParameter(spaceCommon);
				}else{
					list = spaceCommonService.spaceCapacityList(spaceCommon);
				}
			}
			String mediaType = (request.getSession() == null || request.getSession().getAttribute("mediaType") == null)
					? "2" : (String) request.getSession().getAttribute("mediaType");
			for(SpaceCommon spaceCommonSingle:list){
				spaceCommonService.setPicParams(spaceCommonSingle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SpaceCommon>(false, "数据异常", msgId);
		}
		Long endTime = System.currentTimeMillis();
		return new ResponseEnvelope<SpaceCommon>(total, list, msgId);
	}

	
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam(value = "resPicFile",required = false) MultipartFile resPicFile,String msgId,HttpServletRequest request) throws IOException {
	    if (resPicFile != null && !resPicFile.isEmpty()) {
	        // 获取图片的文件名
	        String fileName = resPicFile.getOriginalFilename();
	        //保存  
	        try {  
	        	String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.appSystemOperationLog.upload.path",""),null);
	        	File picFileName = new File(path+fileName);
	        	 if(!picFileName.exists()){  
	        		 picFileName.mkdirs();  
	 	        } 
	        	 resPicFile.transferTo(picFileName);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            new  ResponseEnvelope<LoginUser>(false, "传输文件失败");
	        } 
	    }  
	    return new  ResponseEnvelope<LoginUser>(true, "传输文件成功",msgId);
	}

}
