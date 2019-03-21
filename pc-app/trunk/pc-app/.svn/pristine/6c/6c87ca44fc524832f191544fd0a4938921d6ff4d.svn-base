package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.nork.cityunion.model.DesignPlanStoreModel;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreVO;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.objectconvert.cityUnion.UnionDesignPlanStoreObject;
import com.nork.common.param.ParamCommonVerification;
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

import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.search.UnionDesignPlanStoreSearch;
import com.nork.cityunion.model.small.UnionDesignPlanStoreSmall;
import com.nork.cityunion.service.UnionDesignPlanStoreService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionDesignPlanStoreController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟设计方案素材库表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:21:19
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionDesignPlanStore")
public class UnionDesignPlanStoreController {
	private static Logger logger = Logger
			.getLogger(UnionDesignPlanStoreController.class);
	private final JsonDataServiceImpl<UnionDesignPlanStore> JsonUtil = new JsonDataServiceImpl<UnionDesignPlanStore>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionDesignPlanStore";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionDesignPlanStore";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionDesignPlanStore";
	
	@Autowired
	private UnionDesignPlanStoreService unionDesignPlanStoreService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    联盟设计方案素材库表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟设计方案素材库表 主页面
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
	 * 保存 联盟设计方案素材库表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("unionDesignPlanStore") UnionDesignPlanStore unionDesignPlanStore
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   unionDesignPlanStore = (UnionDesignPlanStore)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStore.class);
		   if(unionDesignPlanStore == null){
			  return new ResponseEnvelope<UnionDesignPlanStore>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(unionDesignPlanStore,request);
			if (unionDesignPlanStore.getId() == null) {
				int id = unionDesignPlanStoreService.add(unionDesignPlanStore);
				logger.info("add:id=" + id);
				unionDesignPlanStore.setId(id);
			} else {
				int id = unionDesignPlanStoreService.update(unionDesignPlanStore);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String unionDesignPlanStoreJson = JsonUtil.getBeanToJsonData(unionDesignPlanStore);
				 UnionDesignPlanStoreSmall unionDesignPlanStoreSmall= new JsonDataServiceImpl<UnionDesignPlanStoreSmall>().getJsonToBean(unionDesignPlanStoreJson, UnionDesignPlanStoreSmall.class);
				 
				 return new ResponseEnvelope<UnionDesignPlanStoreSmall>(unionDesignPlanStoreSmall,unionDesignPlanStore.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "数据异常!",unionDesignPlanStore.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStore>(unionDesignPlanStore,unionDesignPlanStore.getMsgId(),true);
	}
	
	/**
	 *获取 联盟设计方案素材库表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionDesignPlanStore") UnionDesignPlanStore unionDesignPlanStore
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStore = (UnionDesignPlanStore)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStore.class);
			if(unionDesignPlanStore == null){
			   return new ResponseEnvelope<UnionDesignPlanStore>(false,"none", "传参异常!");
			}
			id =  unionDesignPlanStore.getId();
			msgId = unionDesignPlanStore.getMsgId() ;
		}else{
		    id =  unionDesignPlanStore.getId();
		    msgId = unionDesignPlanStore.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionDesignPlanStore>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionDesignPlanStore = unionDesignPlanStoreService.get(id);
			
			if("small".equals(style) && unionDesignPlanStore != null ){
				 String unionDesignPlanStoreJson = JsonUtil.getBeanToJsonData(unionDesignPlanStore);
				 UnionDesignPlanStoreSmall unionDesignPlanStoreSmall= new JsonDataServiceImpl<UnionDesignPlanStoreSmall>().getJsonToBean(unionDesignPlanStoreJson, UnionDesignPlanStoreSmall.class);
				 
				 return new ResponseEnvelope<UnionDesignPlanStoreSmall>(unionDesignPlanStoreSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStore>(unionDesignPlanStore,msgId,true);
	}
	
   /**
	 * 删除联盟设计方案素材库表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@ModelAttribute("unionDesignPlanStore") UnionDesignPlanStore unionDesignPlanStore
	                 ,HttpServletRequest request) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStore = (UnionDesignPlanStore)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStore.class);
			if(unionDesignPlanStore == null){
			   return new ResponseEnvelope<UnionDesignPlanStore>(false, "传参异常!","none");
			}
			ids =  unionDesignPlanStore.getIds();
			msgId = unionDesignPlanStore.getMsgId() ;
		}else{
			ids =  unionDesignPlanStore.getIds();
			msgId = unionDesignPlanStore.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionDesignPlanStore>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = unionDesignPlanStoreService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = unionDesignPlanStoreService.delete(id);
					logger.info(" delete:id= " + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<UnionDesignPlanStore>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStore>(true,msgId,true);
	}
	

	/**
	 * 联盟设计方案素材库表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
			@PathVariable String style,@ModelAttribute("unionDesignPlanStoreSearch") UnionDesignPlanStoreSearch unionDesignPlanStoreSearch
			,HttpServletRequest request, HttpServletResponse response) {
		//每页不同页码时使用
		unionDesignPlanStoreSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreSearch = (UnionDesignPlanStoreSearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreSearch.class);
			if(unionDesignPlanStoreSearch == null){
				return new ResponseEnvelope<UnionDesignPlanStore>(false, "传参异常!","none");
			}
		}

		List<UnionDesignPlanStore> list = new ArrayList<UnionDesignPlanStore> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreService.getCount(unionDesignPlanStoreSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = unionDesignPlanStoreService.getPaginatedList(
						unionDesignPlanStoreSearch);
			}

			if("small".equals(style) && list != null && list.size() > 0){
				String unionDesignPlanStoreJsonList = JsonUtil.getListToJsonData(list);
				List<UnionDesignPlanStoreSmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreSmall>().getJsonToBeanList(unionDesignPlanStoreJsonList, UnionDesignPlanStoreSmall.class);
				return new ResponseEnvelope<UnionDesignPlanStoreSmall>(total,smallList,unionDesignPlanStoreSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "数据异常!",unionDesignPlanStoreSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStore>(total, list,unionDesignPlanStoreSearch.getMsgId());
	}

   /**
	 * 联盟设计方案素材库表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreSearch") UnionDesignPlanStoreSearch unionDesignPlanStoreSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreSearch = (UnionDesignPlanStoreSearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreSearch.class);
			if(unionDesignPlanStoreSearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStore>(false, "传参异常!","none");
			}
		}
	
    	List<UnionDesignPlanStore> list = new ArrayList<UnionDesignPlanStore> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreService.getCount(unionDesignPlanStoreSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreService.getList(unionDesignPlanStoreSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreSmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreSmall>().getJsonToBeanList(unionDesignPlanStoreJsonList, UnionDesignPlanStoreSmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreSmall>(total,smallList,unionDesignPlanStoreSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "数据异常!",unionDesignPlanStoreSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStore>(total, list,unionDesignPlanStoreSearch.getMsgId());
	}
	
   /**
	 *获取 联盟设计方案素材库表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionDesignPlanStore unionDesignPlanStore = null;
		try {
			unionDesignPlanStore = unionDesignPlanStoreService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "数据异常!");
		}
		ResponseEnvelope<UnionDesignPlanStore> res = new ResponseEnvelope<UnionDesignPlanStore>(unionDesignPlanStore);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionDesignPlanStore_edit";
		}else{
			url = JSPMAIN + "/unionDesignPlanStore_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟设计方案素材库表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionDesignPlanStoreSearch") UnionDesignPlanStoreSearch unionDesignPlanStoreSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionDesignPlanStore> list = new ArrayList<UnionDesignPlanStore> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreService.getCount(unionDesignPlanStoreSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreService.getPaginatedList(
						unionDesignPlanStoreSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStore>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionDesignPlanStore> res = new ResponseEnvelope<UnionDesignPlanStore>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionDesignPlanStoreSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionDesignPlanStore_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionDesignPlanStore model,HttpServletRequest request){
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
	 * 联盟设计方案素材库表列表
	 */
	@RequestMapping(value = "/designPlanStoreList")
	@ResponseBody
	public Object designPlanStoreList(String designPlanName, Integer userId, Integer start,
								Integer limit, String msgId, HttpServletRequest request) {

//		if (StringUtils.isEmpty(msgId)) {
//			return new ResponseEnvelope<UnionDesignPlanStoreVO>(false, "The param is null", msgId);
//		}
		if (userId == null || userId == 0) {
			LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
			if (loginUser == null) {
				return new ResponseEnvelope<UnionDesignPlanStoreVO>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, msgId);
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
		List<UnionDesignPlanStoreVO> planStoreVoList = new ArrayList<> ();
		int total = 0;
		try {
			logger.info("查询参数：designPlanName="+designPlanName+",userId="+userId+",start="+start+",limit="+limit);
			total = unionDesignPlanStoreService.findMyDesignPlanStoreCount(designPlanName,userId,start,limit);
			logger.info("total:" + total);

			if (total > 0) {
				List<UnionDesignPlanStore> list = unionDesignPlanStoreService.findMyDesignPlanStoreList(designPlanName,userId,start,limit);
				//转换voList对象
				planStoreVoList = UnionDesignPlanStoreObject.parseToPlanStoreVOListByPlanStoreList(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionDesignPlanStoreVO>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope(total, planStoreVoList, msgId);

	}


	/**
	 * 同城联盟单独发布获取方案素材库图片
	 */
	@RequestMapping(value = "/getStorePicList")
	@ResponseBody
	public Object getStorePicList(@RequestBody DesignPlanStoreModel model, HttpServletRequest request) {

		if (model == null || StringUtils.isEmpty(model.getStoreIds())) {
			return new ResponseEnvelope<>(false,SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		String msgId = model.getMsgId();
		String storeIds = model.getStoreIds();

		List<Integer> storeIdList = Utils.getIntegerListFromStringList(storeIds);
		List<Map<String,String>> list = new ArrayList<>();
		try {
			List<UnionDesignPlanStore> storeList = unionDesignPlanStoreService.getStorePicByStoreIds(storeIdList);
			if (storeIdList != null && storeIdList.size() > 0) {
				for (UnionDesignPlanStore planStore : storeList) {
					Map<String,String> map = new HashMap<String,String>();
					map.put("storeId",String.valueOf(planStore.getId()));
					map.put("planStoreName",planStore.getDesignPlanName());
					map.put("storeSmallPicPath",planStore.getPicSmallPath());
					list.add(map);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<>(false, SystemCommonConstant.DATA_EXCEPTION, msgId);
		}
		return new ResponseEnvelope(true, list, msgId);
	}
}
