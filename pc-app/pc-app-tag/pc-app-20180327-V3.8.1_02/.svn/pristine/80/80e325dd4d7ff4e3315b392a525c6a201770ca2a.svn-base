package com.nork.home.controller.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignTempletPutawayState;
import com.nork.home.cache.BaseHouseCacher;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.BaseHouseResult;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.home.model.search.BaseHouseSearch;
import com.nork.home.model.small.BaseHouseSmall;
import com.nork.home.service.BaseHouseService;
import com.nork.product.model.small.BaseBrandSmall;
import com.nork.system.model.BaseArea;
import com.nork.system.model.BaseLiving;
import com.nork.system.model.ResHousePic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.BaseLivingService;
import com.nork.system.service.ResHousePicService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;


/**   
 * @Title: WebBaseHouseController.java 
 * @Package com.nork.home.controller.web
 * @Description:前台业务-户型Controller
 * @createAuthor xiaoxc 
 * @CreateDate 2015-09-17 16:32:51
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/home/baseHouse")
public class WebBaseHouseController {
	private static Logger logger = Logger.getLogger(WebBaseHouseController.class);
	private final JsonDataServiceImpl<BaseHouse> JsonUtil = new JsonDataServiceImpl<BaseHouse>();
	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private BaseLivingService baseLivingService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ResHousePicService resHousePicService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	/**
	 * 小区户型首页
	 */
	/**
	 * @param province
	 * @param city
	 * @param livingName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/livingHouse") 
	public String leftProductProps(
			@RequestParam(value="province", required = false)String province,
			@RequestParam(value="city", required = false)String city,
			@RequestParam(value="livingName", required = false)String livingName,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
			request.setAttribute("province",province);
			request.setAttribute("city",city);
			request.setAttribute("livingName",livingName);
			if(StringUtils.isNotBlank(province)){
				BaseArea baseArea = new BaseArea();
				baseArea.setAreaCode(province);
				baseArea.setLevelId(1);
				List<BaseArea> list =  baseAreaService.getList(baseArea);
				if(CustomerListUtils.isNotEmpty(list)){
					baseArea = list.get(0);
					request.setAttribute("provinceName", baseArea.getAreaName());
				}
			}
			if(StringUtils.isNotBlank(city)){
				BaseArea baseArea = new BaseArea();
				baseArea.setAreaCode(city);
				baseArea.setLevelId(2);
				List<BaseArea> list =  baseAreaService.getList(baseArea);
				if(CustomerListUtils.isNotEmpty(list)){
					baseArea = list.get(0);
					request.setAttribute("cityName", baseArea.getAreaName());
				}
			}
			
		    return  Utils.getPageUrl(request, "/online/home/house_search");
		 
	}
	
	/**
     * 户型搜索列表
     * @param baseHouseResult
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/houseSearch")
	public Object houseSearch(@ModelAttribute("BaseHouseResult") BaseHouseResult baseHouseResult, HttpServletRequest request, HttpServletResponse response) {
		
		List<BaseHouseResult> list = new ArrayList<BaseHouseResult> ();
//		baseHouseResult.setProvinceCode("110000");
//		baseHouseResult.setCityCode("110101");
//		baseHouseResult.setLivingName("3");
		if(baseHouseResult==null){
			return new ResponseEnvelope<BaseHouseResult>(false,"参数不能为空!");
		}
		String areaCode_p = baseHouseResult.getProvinceCode();
		String areaCode_c = baseHouseResult.getCityCode() ;
		StringBuffer areaLongCode = new StringBuffer(); 
		if(StringUtils.isNotBlank(areaCode_p)){
			areaLongCode.append("."+areaCode_p);
		}
		if(StringUtils.isNotBlank(areaCode_c)){
			areaLongCode.append("."+areaCode_c);
		}
		if(StringUtils.isNotBlank(areaLongCode.toString())){
			areaLongCode.append(".");
		}
		baseHouseResult.setAreaLongCode(areaLongCode.toString()); 
		Integer userType =null;
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser==null||loginUser.getId()==null && loginUser.getUserType()!=null){
			userType =loginUser.getUserType();
			baseHouseResult.setUserType(userType);
		}
		try{
			list = baseHouseService.getHouseList(baseHouseResult);
			
			if(CustomerListUtils.isNotEmpty(list)){
				for(BaseHouseResult houseResult : list){
					StringBuffer areaName = new StringBuffer();
					String areaCode = houseResult.getAreaLongCode();
					if(!StringUtils.isEmpty(areaCode)){
						if(areaCode.contains(".")){
							String area [] = areaCode.split("\\.");
							for(String acode : area){
								BaseArea baseArea = new BaseArea();
								baseArea.setAreaCode(acode);
								List<BaseArea> areaList = baseAreaService.getList(baseArea);
								if(CustomerListUtils.isNotEmpty(areaList)){
									areaName.append(areaList.get(0).getAreaName());
								}
							}
						}else{
							BaseArea baseArea = new BaseArea();
							baseArea.setAreaCode(areaCode);
							List<BaseArea> areaList = baseAreaService.getList(baseArea);
							if(CustomerListUtils.isNotEmpty(areaList)){
								areaName.append(areaList.get(0).getAreaName());
							}
						}
						houseResult.setAreaName(areaName.toString());
					}else{
						houseResult.setAreaName("不明");
					}  
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouseResult>(false,"数据异常!");
		}
		 
		request.setAttribute("list", list); 
		
		return  "/online/home/living_house_list"; 
		
	}


	/**
	 * 户型搜索列表接口
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @param livingName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/houseSearchWeb")
	@ResponseBody
	public Object houseSearch(
			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="cityCode",required=false) String cityCode,
			@RequestParam(value="districtCode",required=false) String districtCode,
			@RequestParam(value="livingName",required=false) String livingName,
			@RequestParam(value = "msgId",required = false) String msgId,
			@RequestParam(value = "limit",required = false) String limit,
			@RequestParam(value = "start",required = false) String start,HttpServletRequest request) {
		if( StringUtils.isBlank(msgId)){
			return new ResponseEnvelope<BaseHouseResult>(false,"参数msgId不能为空",msgId);
		}
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null || loginUser.getId() < 1){
			return new ResponseEnvelope<BaseHouse>(false,"请重新登录",msgId);
		}
		BaseHouseResult baseHouseResult=new BaseHouseResult();
		baseHouseResult.setProvinceCode(provinceCode);
		baseHouseResult.setCityCode(cityCode);
		baseHouseResult.setLivingName(livingName);
		if(StringUtils.isNotBlank(start)){
			baseHouseResult.setStart(Integer.parseInt(start));
		}
		if(StringUtils.isNotBlank(limit)){
			baseHouseResult.setLimit(Integer.parseInt(limit));
		}
		int total=0;
		List<BaseHouseResult> list = new ArrayList<BaseHouseResult> ();
		StringBuffer areaLongCode = new StringBuffer();
		//添加省份
		if(StringUtils.isNotBlank(provinceCode)){
			areaLongCode.append("."+provinceCode);
		}
		//添加市
		if(StringUtils.isNotBlank(cityCode)){
			areaLongCode.append("."+cityCode);
		}
		//添加市行政区
		if(StringUtils.isNotBlank(districtCode)){
			areaLongCode.append("."+districtCode);
		}
		if(StringUtils.isNotBlank(areaLongCode.toString())){
			areaLongCode.append(".");
		}
		baseHouseResult.setAreaLongCode(areaLongCode.toString());
		try{
			baseHouseResult.setStart(-1);
			baseHouseResult.setLimit(-1);
			this.internalPermissions(loginUser,baseHouseResult);//判断该用户该环境 拥有 看到  哪些状态的空间和样板房，并且赋值
			if(Utils.enableRedisCache()){
			    list = BaseHouseCacher.getPageList(baseHouseResult,loginUser.getId());
			}else{
			    list = baseHouseService.getHouseList(baseHouseResult);
			}
			total=list.size();
			if(StringUtils.isNotBlank(start)&&StringUtils.isNotBlank(limit)) {
				list = (List<BaseHouseResult>) Utils.paging2(list, Integer.parseInt(start), Integer.parseInt(limit));
			}

			//循环获取小区所属区域名称
			if(CustomerListUtils.isNotEmpty(list)){
				for(BaseHouseResult houseResult : list){
					//获取小区所属区域code
					String areaCode = houseResult.getAreaLongCode().trim();
					if(StringUtils.isNotBlank(areaCode)){
						String[] areaCodes = areaCode.split("\\.");
						String areaName = null;
						if(areaCodes.length>3){
							areaName = baseAreaService.getAreaNameByCode(areaCodes);
						}
						//判断是否存在
						if(StringUtils.isNotBlank(areaName)){
							houseResult.setAreaName(areaName);
						}else{
							logger.info("小区所属区域名称不明，查询不到，小区信息="+ JSON.toJSONString(houseResult));
							houseResult.setAreaName("不明");
						}
					}else{
						logger.info("小区所属区域名称不明areaCode is null,小区信息="+JSON.toJSONString(houseResult));
						houseResult.setAreaName("不明");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouseResult>(false,"数据异常!",msgId);
		}
		return new ResponseEnvelope<BaseHouseResult>(total,list,msgId);
	}
	
	/**
     * 户型类型搜索
     * @param baseHouse
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/houseList")
	public Object houseList(@ModelAttribute("BaseHouse") BaseHouse baseHouse, HttpServletRequest request, HttpServletResponse response) {
		
		List<BaseHouse> list = new ArrayList<BaseHouse> ();
		if(baseHouse==null){
			return new ResponseEnvelope<BaseHouseResult>(false,"参数不能为空!");
		}
		if(baseHouse.getLivingId()!=null){
			BaseLiving bl = baseLivingService.get(baseHouse.getLivingId());
			request.setAttribute("livingName", bl==null?"":bl.getLivingName());
		}
		try{
			list = baseHouseService.getList(baseHouse);
			for(BaseHouse house : list){
				if( house!=null && StringUtils.isNotBlank(house.getHouseCommonCode()) && house.getHouseCommonCode().length() > 6 ){
					house.setHouseType(house.getHouseCommonCode().substring(0, 6));
				}else{
					house.setHouseType(house.getHouseCommonCode());
				}
			}
			request.setAttribute("houseCount", list==null?"0":list.size());
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouseResult>(false,"数据异常!");
		}
		request.setAttribute("province", baseHouse.getProvince());
		request.setAttribute("city", baseHouse.getCity());
		request.setAttribute("livingName", baseHouse.getLivingName());
		request.setAttribute("list", list);
		
		return  "/online/home/house_list"; 
		
	}

	/**
	 * 户型类型搜索接口.
	 * 
	 * <pre>
	 * v3.9.4（draw-v1.0.2）需求：
	 * 	1、户型列表中显示所有有样板房的户型，包括三度平台提供官方数据和用户自己绘制的数据（ok）；
	 * 	2、三度提供的官方户型在户型图上标记为【推荐】字样，用户绘制户型标记为【普通】字样（ok）；
	 * 	3、对于有户型图的户型，此处展示户型图，若无户型图的（比如用户自由绘制的）则展示绘制工具的全屋截图（ok）；
	 * 	4、此列表优先展示官方推荐的户型，其次再展示用户的户型（ok）（ORDER BY data_type ASC）；
	 * </pre>
	 * 
	 * @param request
	 * @param request
	 * @param livingId
	 * @return
	 */
	@RequestMapping(value = "/houseListWeb")
	@ResponseBody
	public Object houseList(@PathVariable String style, 
			@RequestParam(value = "livingId", required = false) String livingId, 
			@RequestParam(value = "msgId", required = false) String msgId, 
			@RequestParam(value = "limit", required = false) String limit, 
			@RequestParam(value = "start", required = false) String start, 
			HttpServletRequest request) {
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null || loginUser.getId() < 1) {
			return new ResponseEnvelope<BaseHouse>(false, "请重新登录", msgId);
		}
		if (StringUtils.isBlank(livingId)) {
			return new ResponseEnvelope<BaseHouse>(false, "参数livingId不能为空", msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<BaseHouse>(false, "参数msgId不能为空", msgId);
		}
		BaseHouseSearch baseHouseSearch = new BaseHouseSearch();
		List<BaseHouse> list = new ArrayList<BaseHouse>();
		int total = 0;
		try {
			baseHouseSearch.setLivingId(Integer.parseInt(livingId));
			if (StringUtils.isNotBlank(start)) {
				baseHouseSearch.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				baseHouseSearch.setLimit(Integer.parseInt(limit));
			}
			this.internalPermissions(loginUser, baseHouseSearch);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
			total = baseHouseService.getHaveSpaceCount(baseHouseSearch);
			if (total > 0) {
				list = baseHouseService.getHaveSpaceList(baseHouseSearch);
				for (BaseHouse baseHouse : list) {
					if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() != 0) {
						ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
						if (null != resHousePic) {
							baseHouse.setThumbnailPath(resHousePic.getThumbnailPath());
							baseHouse.setLargeThumbnailPath(resHousePic.getLargeThumbnailPath());
						}
					} else if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
						// 截图图片处理 v3.9.4(draw-v1.0.2)
						ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
						baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
						baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
					}
				}
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String baseHouseJsonList = JsonUtil.getListToJsonData(list);
				List<BaseHouseSmall> smallList = new JsonDataServiceImpl<BaseHouseSmall>().getJsonToBeanList(baseHouseJsonList, BaseBrandSmall.class);
				return new ResponseEnvelope<BaseHouseSmall>(total, smallList, msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouse>(false, "数据异常!", msgId);
		}

		return new ResponseEnvelope<BaseHouse>(total, list, msgId);
	}
	
	/**
	 * 判断该用户该环境 拥有 看到  哪些状态的空间和样板房，并且赋值
	 * @param loginUser
	 * @param baseHouseResult
	 */
	public void internalPermissions(LoginUser loginUser,BaseHouseResult baseHouseResult) {
		if(loginUser == null || loginUser.getId() < 1 || baseHouseResult == null){
			return;
		}
		Integer spaceCommonStatusList[] = null;//存放空间状态的list  用于in 查询
		Integer designTempletPutawayStateList[] = null; //存放样板房状态的list  用于in 查询
		
		  
		
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();//1为正式2为内部,默认为正式
		
		if(loginUser.getUserType()!=null && 1 == loginUser.getUserType().intValue() && "2".equals(versionType)) {//内部环境并且内部用户才能看到测试中上架中发布中的
			spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE,SpaceCommonStatus.IS_UP,SpaceCommonStatus.IS_TEST};
			designTempletPutawayStateList= new Integer[]{DesignTempletPutawayState.IS_RELEASE,DesignTempletPutawayState.IS_UP,DesignTempletPutawayState.IS_TEST};
		}else {
			spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE};
			designTempletPutawayStateList= new Integer[]{DesignTempletPutawayState.IS_RELEASE};

		}
		baseHouseResult.setSpaceCommonStatusList(spaceCommonStatusList);
		baseHouseResult.setDesignTempletPutawayStateList(designTempletPutawayStateList);
	}
	
	/**
	 * 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
	 * 
	 * @param loginUser
	 * @param baseHouseSearch
	 */
	public void internalPermissions(LoginUser loginUser, BaseHouseSearch baseHouseSearch) {
		if (loginUser == null || loginUser.getId() < 1 || baseHouseSearch == null) {
			return;
		}
		Integer spaceCommonStatusList[] = null;// 存放空间状态的list 用于in 查询
		Integer designTempletPutawayStateList[] = null; // 存放样板房状态的list 用于in 查询

		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();// 1为正式2为内部,默认为正式

		if (loginUser.getUserType() != null && 1 == loginUser.getUserType().intValue() && "2".equals(versionType)) {// 内部环境并且内部用户才能看到测试中上架中发布中的
			spaceCommonStatusList = new Integer[] { SpaceCommonStatus.IS_RELEASE, SpaceCommonStatus.IS_UP, SpaceCommonStatus.IS_TEST };
			designTempletPutawayStateList = new Integer[] { DesignTempletPutawayState.IS_RELEASE, DesignTempletPutawayState.IS_UP, DesignTempletPutawayState.IS_TEST };
		} else {
			spaceCommonStatusList = new Integer[] { SpaceCommonStatus.IS_RELEASE };
			designTempletPutawayStateList = new Integer[] { DesignTempletPutawayState.IS_RELEASE };

		}
		baseHouseSearch.setSpaceCommonStatusList(spaceCommonStatusList);
		baseHouseSearch.setDesignTempletPutawayStateList(designTempletPutawayStateList);
	}
	
	@RequestMapping(value="/baseHouseNameList")
	@ResponseBody
	public Object findAllBaseHouseName(){
		List<String> baseHouseNames=new ArrayList<String>();
		baseHouseNames=baseHouseService.findAllName();
		Set<String> set=new HashSet<>();
		for(String str:baseHouseNames){
			if(StringUtils.isNotBlank(str)){
				set.add(str);
			}
		}
		return set;
	}
	
	/**
     * 搜索某户型下的房间
     * @param request
     * @return
     */
	@RequestMapping(value = "/houseType")
	@ResponseBody
	public Object houseType(Integer id,String msgId, HttpServletRequest request) {
		String msg="";
		if(id == null || id == 0){
			msg = "户型Id不能为空";
			return new ResponseEnvelope<BaseHouse>(false,msg,msgId);
		}
		List<SysDictionary> sdList = new ArrayList<SysDictionary>();
		try {
			List<String> list = baseHouseService.getPaginatedListMoreInfo(id);
			sdList = sysDictionaryService.getListByValueType("houseType",list);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEnvelope<BaseHouse>(false,"数据异常!",msgId);
		}
		
		return new ResponseEnvelope<SysDictionary>(sdList.size(), sdList,msgId);
		
	}

}
