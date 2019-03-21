package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.nork.cityunion.model.*;
import com.nork.cityunion.model.vo.*;
import com.nork.cityunion.service.*;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.objectconvert.cityUnion.UnionGroupDetailsObject;
import com.nork.common.objectconvert.cityUnion.UnionGroupObject;
import com.nork.common.objectconvert.cityUnion.UnionSpecialOfferObject;
import com.nork.common.objectconvert.cityUnion.UnionStorefrontObject;
import com.nork.common.param.ParamCommonVerification;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.base.service.impl.JsonDataServiceImpl;

import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.model.small.UnionDesignPlanStoreReleaseSmall;
import com.nork.common.model.LoginUser;
import sun.rmi.runtime.Log;

/**   
 * @Title: UnionDesignPlanStoreReleaseController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟素材发布表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionDesignPlanStoreRelease")
public class UnionDesignPlanStoreReleaseController {
	private static Logger logger = Logger
			.getLogger(UnionDesignPlanStoreReleaseController.class);
	private final JsonDataServiceImpl<UnionDesignPlanStoreRelease> JsonUtil = new JsonDataServiceImpl<UnionDesignPlanStoreRelease>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionDesignPlanStoreRelease";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionDesignPlanStoreRelease";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionDesignPlanStoreRelease";
	
	@Autowired
	private UnionDesignPlanStoreReleaseService unionDesignPlanStoreReleaseService;

	@Autowired
	private UnionDesignPlanStoreReleaseDetailsService unionDesignPlanStoreReleaseDetailsService;

	@Autowired
	private UnionDesignPlanStoreService unionDesignPlanStoreService;

	@Autowired
	private UnionStorefrontService unionStorefrontService;

	@Autowired
	private UnionGroupService unionGroupService;

	@Autowired
	private UnionSpecialOfferService unionSpecialOfferService;

	@Autowired
	private UnionGroupDetailsService unionGroupDetailsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    联盟素材发布表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟素材发布表 主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return MAIN;
	}

    /**
	 *    访问主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN+"/list";
	}
	
	/**
	 * 保存 联盟素材发布表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@RequestBody UnionDesignPlanStoreRelease unionDesignPlanStoreRelease
			, HttpServletRequest request) throws Exception{

		if (unionDesignPlanStoreRelease == null) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,unionDesignPlanStoreRelease.getMsgId());
		}
		if (unionDesignPlanStoreRelease.getCatoryId() == null) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.PARAM_EXCEPTION,unionDesignPlanStoreRelease.getMsgId());
		}
		if (unionDesignPlanStoreRelease.getStoreReleaseModelList() == null || unionDesignPlanStoreRelease.getStoreReleaseModelList().size() == 0) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.DATA_DELETE_FAILED + "!",unionDesignPlanStoreRelease.getMsgId());
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (unionDesignPlanStoreRelease.getUserId() == null) {
			if (loginUser == null) {
				return new ResponseEnvelope<>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY,unionDesignPlanStoreRelease.getMsgId());
			} else {
				unionDesignPlanStoreRelease.setUserId(loginUser.getId());
			}
		}
		sysSave(unionDesignPlanStoreRelease,loginUser);
		//保存发布数据，返回发布ids,用于获取二维码地址
		Map<String,String> map = new HashMap<String,String>();
		String releaseIds = unionDesignPlanStoreReleaseService.save(unionDesignPlanStoreRelease, loginUser);
		if (releaseIds == null) {
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false,SystemCommonConstant.DATA_EXCEPTION,unionDesignPlanStoreRelease.getMsgId());
		}
		map.put("releaseIds",releaseIds);
		return new ResponseEnvelope<>(true,map,unionDesignPlanStoreRelease.getMsgId());
	}
	
	/**
	 *获取 联盟素材发布表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionDesignPlanStoreRelease") UnionDesignPlanStoreRelease unionDesignPlanStoreRelease
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreRelease = (UnionDesignPlanStoreRelease)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreRelease.class);
			if(unionDesignPlanStoreRelease == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false,"none", "传参异常!");
			}
			id =  unionDesignPlanStoreRelease.getId();
			msgId = unionDesignPlanStoreRelease.getMsgId() ;
		}else{
		    id =  unionDesignPlanStoreRelease.getId();
		    msgId = unionDesignPlanStoreRelease.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionDesignPlanStoreRelease = unionDesignPlanStoreReleaseService.get(id);
			
			if("small".equals(style) && unionDesignPlanStoreRelease != null ){
				 String unionDesignPlanStoreReleaseJson = JsonUtil.getBeanToJsonData(unionDesignPlanStoreRelease);
				 UnionDesignPlanStoreReleaseSmall unionDesignPlanStoreReleaseSmall= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseSmall>().getJsonToBean(unionDesignPlanStoreReleaseJson, UnionDesignPlanStoreReleaseSmall.class);
				 
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseSmall>(unionDesignPlanStoreReleaseSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStoreRelease>(unionDesignPlanStoreRelease,msgId,true);
	}
	
   /**
	 * 删除联盟素材发布表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@ModelAttribute("unionDesignPlanStoreRelease") UnionDesignPlanStoreRelease storeRelease
	                 ,HttpServletRequest request) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			storeRelease = (UnionDesignPlanStoreRelease)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreRelease.class);
			if(storeRelease == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, SystemCommonConstant.DATA_EXCEPTION,"none");
			}
			ids =  storeRelease.getIds();
			msgId = storeRelease.getMsgId() ;
		}else{
			ids =  storeRelease.getIds();
			msgId = storeRelease.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = unionDesignPlanStoreReleaseService.delete(id);
						logger.info("delete:id=" + id);
						UnionDesignPlanStoreReleaseDetails releaseDetails = new UnionDesignPlanStoreReleaseDetails();
						releaseDetails.setUnionDesignPlanStoreReleaseId(id);
						releaseDetails.setIsDeleted(Constants.DATA_DELETED_STATUS_ONE);
						releaseDetails.setGmtModified(new Date());
						unionDesignPlanStoreReleaseDetailsService.updateByReleaseId(releaseDetails);
					}
			   } else {
				   Integer id = new Integer(ids);
				   i = unionDesignPlanStoreReleaseService.delete(id);
				   logger.info("delete:id=" + id);
				   UnionDesignPlanStoreReleaseDetails releaseDetails = new UnionDesignPlanStoreReleaseDetails();
				   releaseDetails.setUnionDesignPlanStoreReleaseId(id);
				   releaseDetails.setIsDeleted(Constants.DATA_DELETED_STATUS_ONE);
				   releaseDetails.setGmtModified(new Date());
				   unionDesignPlanStoreReleaseDetailsService.updateByReleaseId(releaseDetails);
			    }
			}
			if( i == 0){
				return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStoreRelease>(true,msgId,true);
	}
	
	/**
	 * 联盟素材发布表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseSearch") UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionDesignPlanStoreReleaseSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreReleaseSearch = (UnionDesignPlanStoreReleaseSearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseSearch.class);
			if(unionDesignPlanStoreReleaseSearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "传参异常!","none");
			}
		}
		
		List<UnionDesignPlanStoreRelease> list = new ArrayList<UnionDesignPlanStoreRelease> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreReleaseService.getCount(unionDesignPlanStoreReleaseSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreReleaseService.getPaginatedList(
						unionDesignPlanStoreReleaseSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreReleaseJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreReleaseSmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseSmall>().getJsonToBeanList(unionDesignPlanStoreReleaseJsonList, UnionDesignPlanStoreReleaseSmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseSmall>(total,smallList,unionDesignPlanStoreReleaseSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "数据异常!",unionDesignPlanStoreReleaseSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreRelease>(total, list,unionDesignPlanStoreReleaseSearch.getMsgId());
	}
	

   /**
	 * 联盟素材发布表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreReleaseSearch") UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreReleaseSearch = (UnionDesignPlanStoreReleaseSearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreReleaseSearch.class);
			if(unionDesignPlanStoreReleaseSearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "传参异常!","none");
			}
		}
	
    	List<UnionDesignPlanStoreRelease> list = new ArrayList<UnionDesignPlanStoreRelease> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreReleaseService.getCount(unionDesignPlanStoreReleaseSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreReleaseService.getList(unionDesignPlanStoreReleaseSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreReleaseJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreReleaseSmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreReleaseSmall>().getJsonToBeanList(unionDesignPlanStoreReleaseJsonList, UnionDesignPlanStoreReleaseSmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreReleaseSmall>(total,smallList,unionDesignPlanStoreReleaseSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "数据异常!",unionDesignPlanStoreReleaseSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreRelease>(total, list,unionDesignPlanStoreReleaseSearch.getMsgId());
	}
	
   /**
	 *获取 联盟素材发布表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionDesignPlanStoreRelease unionDesignPlanStoreRelease = null;
		try {
			unionDesignPlanStoreRelease = unionDesignPlanStoreReleaseService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "数据异常!");
		}
		ResponseEnvelope<UnionDesignPlanStoreRelease> res = new ResponseEnvelope<UnionDesignPlanStoreRelease>(unionDesignPlanStoreRelease);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionDesignPlanStoreRelease_edit";
		}else{
			url = JSPMAIN + "/unionDesignPlanStoreRelease_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟素材发布表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionDesignPlanStoreReleaseSearch") UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionDesignPlanStoreRelease> list = new ArrayList<UnionDesignPlanStoreRelease> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreReleaseService.getCount(unionDesignPlanStoreReleaseSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreReleaseService.getPaginatedList(
						unionDesignPlanStoreReleaseSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionDesignPlanStoreRelease> res = new ResponseEnvelope<UnionDesignPlanStoreRelease>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionDesignPlanStoreReleaseSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionDesignPlanStoreRelease_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionDesignPlanStoreRelease model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(request.getSession()==null||request.getSession().getAttribute("loginUser")==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
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
	 * 自动存储系统字段
	 */
	private void sysSave(UnionDesignPlanStoreRelease model, LoginUser loginUser){
		if (model != null) {
			if (loginUser == null) {
				loginUser = new LoginUser();
				loginUser.setLoginName("nologin");
			}
			if (model.getId() == null) {
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
	 * 联盟素材发布表列表
	 */
	@RequestMapping(value = "/storeReleaseList")
	@ResponseBody
	public Object storeReleaseList(Integer catoryId, String releaseName
			,Integer userId, Integer start, Integer limit, String msgId, HttpServletRequest request) {

		if (catoryId == null) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY, msgId);
		}
		if (userId == null || userId == 0) {
			LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
			if (loginUser == null) {
				return new ResponseEnvelope<>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, msgId);
			} else {
				userId = loginUser.getId();
			}
		}
		if (start == null) {
			start = 0;
		}
		if (limit == null || limit == 0) {
			limit = SystemCommonConstant.SYSTEM_DISPLAY_LIST_LIMIT;
		}
		List<UnionDesignPlanStoreReleaseVO> storeReleaseVOList = new ArrayList<> ();
		int total = 0;
		try {
			logger.info("查询参数：catoryId="+catoryId+",releaseName="+releaseName+",userId="+userId+",start="+start+",limit="+limit);
			total = unionDesignPlanStoreReleaseService.findStoreReleaseCount(catoryId,releaseName,userId,start,limit);
			logger.info("total:" + total);

			if (total > 0) {
				storeReleaseVOList = unionDesignPlanStoreReleaseService.findStoreReleaseList(catoryId,releaseName,userId,start,limit);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionDesignPlanStoreRelease>(false, SystemCommonConstant.DATA_EXCEPTION,msgId);
		}
		return new ResponseEnvelope(total, storeReleaseVOList,msgId);
	}

	/**
	 * 前端获取同盟发布二维码地址
	 */
	@RequestMapping(value = "/getStoreReleaseQRcodeUrl")
	@ResponseBody
	public Object getStoreReleaseQRcodeUrl(@RequestBody DesignPlanStoreReleaseModel model, HttpServletRequest request) {
		if (model == null || StringUtils.isEmpty(model.getReleaseIds())) {
			return new ResponseEnvelope(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		String releaseIds = model.getReleaseIds();
		String msgId = model.getMsgId();
		List<String> list = this.getQcCodeUrl(releaseIds);
		return new ResponseEnvelope(true, list,msgId);
	}


	/**
	 * 前端获取同盟发布二维码地址
	 */
	@RequestMapping(value = "/getReleaseQRcodeUrl")
	@ResponseBody
	public Object getReleaseQRcodeUrl(DesignPlanStoreReleaseModel model, HttpServletRequest request) {
		if (model == null || StringUtils.isEmpty(model.getReleaseIds())) {
			return new ResponseEnvelope(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		String releaseIds = model.getReleaseIds();
		String msgId = model.getMsgId();
		List<String> list = this.getQcCodeUrl(releaseIds);
		return new ResponseEnvelope(true, list,msgId);
	}

	/**
	 * 获取二维码
	 * @param releaseIds
	 * @return
	 */
	private List<String> getQcCodeUrl(String releaseIds){
		List<String> list = new ArrayList<>();
		String[] arrayIds = releaseIds.split(",");
		for (String releaseId : arrayIds) {
			String qcCodeUrl = Utils.getPropertyName("app","render.unionStoreSingle.qrCode.picUrl",
					"render.unionStoreSingle.qrCode.picUrl = http://v720.dev.sanduspace.cn/#/?releaseId=")+releaseId;
			list.add(qcCodeUrl);
		}
		return list;
	}

	/**
	 * 编辑方案素材发布信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editStoreReleaseInfo")
	@ResponseBody
	public Object editStoreReleaseInfo(@RequestBody DesignPlanStoreReleaseModel model
			, HttpServletRequest request) {

		if (model == null || model.getReleaseId() == null || model.getReleaseId() == 0) {
			return new ResponseEnvelope(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		Integer releaseId = model.getReleaseId();
		String msgId = model.getMsgId();

		EditUnionDesignPlanStoreReleaseVO storeReleaseVO = new EditUnionDesignPlanStoreReleaseVO();
		UnionDesignPlanStoreRelease storeRelease = unionDesignPlanStoreReleaseService.get(releaseId);
		if (storeRelease == null) {
			return new ResponseEnvelope<>(false,SystemCommonConstant.DATA_EXCEPTION,msgId);
		}
		storeReleaseVO.setReleaseId(releaseId);
		storeReleaseVO.setReleaseName(storeRelease.getReleaseName());
		try {
			//TODO 获取门店信息
			UnionStorefront unionStorefront = unionStorefrontService.get(storeRelease.getStorefrontId());
			//转换vo对象
			UnionStorefrontVO storefrontVO = UnionStorefrontObject.parseToStorefrontVOByStorefront(unionStorefront);
			storeReleaseVO.setUnionStorefrontVO(storefrontVO);
			//TODO 获取联盟组信息
			UnionGroup unionGroup = unionGroupService.get(storeRelease.getUnionGroupId());
			//转换vo对象
			UnionGroupVO unionGroupVO = UnionGroupObject.parseToGroupVOByGroup(unionGroup);
			//获取联盟组明细
			List<UnionGroupDetails> groupDetailsList = unionGroupDetailsService.getListByGroupId(storeRelease.getUnionGroupId());
			//转换联盟组明细volist
			List<UnionGroupDetailsVO> groupDetailsVOList = UnionGroupDetailsObject.parseToGroupDetailsVOListByGroupDetailsList(groupDetailsList);
			unionGroupVO.setGroupDetailsList(groupDetailsVOList);
			storeReleaseVO.setUnionGroupVO(unionGroupVO);

			//TODO 获取优惠活动信息
			UnionSpecialOffer specialOffer = unionSpecialOfferService.get(storeRelease.getSpecialOfferId());
			//转换vo对象
			UnionSpecialOfferVO specialOfferVO = UnionSpecialOfferObject.paramToSpecialOfferVOBySpecialOffer(specialOffer);
			storeReleaseVO.setUnionSpecialOfferVO(specialOfferVO);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, SystemCommonConstant.DATA_EXCEPTION,msgId);
		}
		return new ResponseEnvelope(true, storeReleaseVO,msgId);
	}
}
