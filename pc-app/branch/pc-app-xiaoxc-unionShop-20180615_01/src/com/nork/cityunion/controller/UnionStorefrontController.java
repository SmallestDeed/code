package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.nork.cityunion.model.StorefrontModel;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreVO;
import com.nork.cityunion.model.vo.UnionStorefrontVO;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.objectconvert.cityUnion.UnionStorefrontObject;
import com.nork.common.param.ParamCommonVerification;
import com.nork.common.util.Constants;
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

import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.search.UnionStorefrontSearch;
import com.nork.cityunion.model.small.UnionStorefrontSmall;
import com.nork.cityunion.service.UnionStorefrontService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionStorefrontController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟店面信息表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:22:46
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionStorefront")
public class UnionStorefrontController {
	private static Logger logger = Logger
			.getLogger(UnionStorefrontController.class);
	private final JsonDataServiceImpl<UnionStorefront> JsonUtil = new JsonDataServiceImpl<UnionStorefront>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionStorefront";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionStorefront";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionStorefront";
	
	@Autowired
	private UnionStorefrontService unionStorefrontService;

	@Autowired
	private UnionDesignPlanStoreReleaseService unionDesignPlanStoreReleaseService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    联盟店面信息表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟店面信息表 主页面
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
	 * 保存 联盟店面信息表
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/save")
	@ResponseBody
	public Object save(@RequestBody UnionStorefront unionStorefront
			, HttpServletRequest request) throws Exception{

		if (unionStorefront == null) {
			return new ResponseEnvelope<UnionStorefrontVO>(false, "The param is null!", unionStorefront.getMsgId());
		}
		if (!ParamCommonVerification.checkTheParamIsValid(unionStorefront.getName(),
				unionStorefront.getContact(),unionStorefront.getPhone(),unionStorefront.getAddress())) {
			return new ResponseEnvelope<UnionStorefrontVO>(false, "The param is null!", unionStorefront.getMsgId());
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (unionStorefront.getUserId() == null) {
			if (loginUser == null) {
				return new ResponseEnvelope<UnionStorefrontVO>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, unionStorefront.getMsgId());
			}
			unionStorefront.setUserId(loginUser.getId());
		}
		UnionStorefrontVO storefrontVO = null;
		try {
		    sysSave(unionStorefront,loginUser);
			if (unionStorefront.getId() == null) {
				int id = unionStorefrontService.add(unionStorefront);
				logger.info("add:id=" + id);
				unionStorefront.setId(id);
			} else {
				int id = unionStorefrontService.update(unionStorefront);
				logger.info("update:id=" + id);
			}
			//转换vo对象
			storefrontVO = UnionStorefrontObject.parseToStorefrontVOByStorefront(unionStorefront);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!",unionStorefront.getMsgId());
		}
		return new ResponseEnvelope<UnionStorefrontVO>(storefrontVO,unionStorefront.getMsgId(),true);
	}
	
	/**
	 *获取 联盟店面信息表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionStorefront") UnionStorefront unionStorefront
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionStorefront = (UnionStorefront)JsonUtil.getJsonToBean(jsonStr, UnionStorefront.class);
			if(unionStorefront == null){
			   return new ResponseEnvelope<UnionStorefront>(false,"none", "传参异常!");
			}
			id =  unionStorefront.getId();
			msgId = unionStorefront.getMsgId() ;
		}else{
		    id =  unionStorefront.getId();
		    msgId = unionStorefront.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionStorefront>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionStorefront = unionStorefrontService.get(id);
			
			if("small".equals(style) && unionStorefront != null ){
				 String unionStorefrontJson = JsonUtil.getBeanToJsonData(unionStorefront);
				 UnionStorefrontSmall unionStorefrontSmall= new JsonDataServiceImpl<UnionStorefrontSmall>().getJsonToBean(unionStorefrontJson, UnionStorefrontSmall.class);
				 
				 return new ResponseEnvelope<UnionStorefrontSmall>(unionStorefrontSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionStorefront>(unionStorefront,msgId,true);
	}
	
   /**
	 * 删除联盟店面信息表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@RequestBody UnionStorefront unionStorefront
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionStorefront = (UnionStorefront)JsonUtil.getJsonToBean(jsonStr, UnionStorefront.class);
			if(unionStorefront == null){
			   return new ResponseEnvelope<UnionStorefront>(false, "传参异常!","none");
			}
			ids =  unionStorefront.getIds();
			msgId = unionStorefront.getMsgId() ;
		}else{
			ids =  unionStorefront.getIds();
			msgId = unionStorefront.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionStorefront>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = unionStorefrontService.delete(id);
						logger.info("delete:id=" + id);
						//TODO 更新已发布方案关联信息
						UnionDesignPlanStoreRelease storeRelease = new UnionDesignPlanStoreRelease();
						storeRelease.setStorefrontId(id);
						unionDesignPlanStoreReleaseService.updateByStoreRelease(storeRelease);
					}
			   }else{
					Integer id = new Integer(ids);
					i = unionStorefrontService.delete(id);
					logger.info(" delete:id= " + id);
					//TODO 更新已发布方案关联信息
				   UnionDesignPlanStoreRelease storeRelease = new UnionDesignPlanStoreRelease();
				   storeRelease.setStorefrontId(id);
				   unionDesignPlanStoreReleaseService.updateByStoreRelease(storeRelease);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<UnionStorefront>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionStorefront>(true,msgId,true);
	}
	
	/**
	 * 联盟店面信息表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionStorefrontSearch") UnionStorefrontSearch unionStorefrontSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionStorefrontSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionStorefrontSearch = (UnionStorefrontSearch)JsonUtil.getJsonToBean(jsonStr, UnionStorefrontSearch.class);
			if(unionStorefrontSearch == null){
			   return new ResponseEnvelope<UnionStorefront>(false, "传参异常!","none");
			}
		}
		
		List<UnionStorefront> list = new ArrayList<UnionStorefront> ();
		int total = 0;
		try {
			total = unionStorefrontService.getCount(unionStorefrontSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionStorefrontService.getPaginatedList(
						unionStorefrontSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionStorefrontJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionStorefrontSmall> smallList= new JsonDataServiceImpl<UnionStorefrontSmall>().getJsonToBeanList(unionStorefrontJsonList, UnionStorefrontSmall.class);
				 return new ResponseEnvelope<UnionStorefrontSmall>(total,smallList,unionStorefrontSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!",unionStorefrontSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionStorefront>(total, list,unionStorefrontSearch.getMsgId());
	}
	

   /**
	 * 联盟店面信息表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionStorefrontSearch") UnionStorefrontSearch unionStorefrontSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionStorefrontSearch = (UnionStorefrontSearch)JsonUtil.getJsonToBean(jsonStr, UnionStorefrontSearch.class);
			if(unionStorefrontSearch == null){
			   return new ResponseEnvelope<UnionStorefront>(false, "传参异常!","none");
			}
		}
	
    	List<UnionStorefront> list = new ArrayList<UnionStorefront> ();
		int total = 0;
		try {
			total = unionStorefrontService.getCount(unionStorefrontSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionStorefrontService.getList(unionStorefrontSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionStorefrontJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionStorefrontSmall> smallList= new JsonDataServiceImpl<UnionStorefrontSmall>().getJsonToBeanList(unionStorefrontJsonList, UnionStorefrontSmall.class);
				 return new ResponseEnvelope<UnionStorefrontSmall>(total,smallList,unionStorefrontSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!",unionStorefrontSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionStorefront>(total, list,unionStorefrontSearch.getMsgId());
	}
	
   /**
	 *获取 联盟店面信息表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionStorefront unionStorefront = null;
		try {
			unionStorefront = unionStorefrontService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!");
		}
		ResponseEnvelope<UnionStorefront> res = new ResponseEnvelope<UnionStorefront>(unionStorefront);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionStorefront_edit";
		}else{
			url = JSPMAIN + "/unionStorefront_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟店面信息表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionStorefrontSearch") UnionStorefrontSearch unionStorefrontSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionStorefront> list = new ArrayList<UnionStorefront> ();
		int total = 0;
		try {
			total = unionStorefrontService.getCount(unionStorefrontSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionStorefrontService.getPaginatedList(
						unionStorefrontSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionStorefront> res = new ResponseEnvelope<UnionStorefront>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionStorefrontSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionStorefront_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionStorefront model,HttpServletRequest request){
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
	private void sysSave(UnionStorefront model,LoginUser loginUser){

		if (loginUser == null) {
			loginUser = new LoginUser();
			loginUser.setLoginName("noLogin");
		}
		if(model != null){
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
	 * 联盟店面信息表列表
	 */
	@RequestMapping(value = "/storefrontlist")
	@ResponseBody
	public Object storefrontlist(@RequestBody StorefrontModel model, HttpServletRequest request) {

		if (model == null) {
			return new ResponseEnvelope<>(false,SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		Integer userId = model.getUserId();
		String msgId = model.getMsgId();

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			userId = loginUser.getId();
		} else {
			if (userId == null) {
				return new ResponseEnvelope<UnionStorefrontVO>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, msgId);
			}
		}
		List<UnionStorefrontVO> storefrontVOList = new ArrayList<> ();
		UnionStorefrontSearch unionStorefrontSearch = new UnionStorefrontSearch();
		unionStorefrontSearch.setUserId(userId);
		unionStorefrontSearch.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
		unionStorefrontSearch.setStart(model.getStart());
		unionStorefrontSearch.setLimit(model.getLimit());
		int total = 0;
		try {
			logger.info("查询参数：userId="+userId);
			total = unionStorefrontService.getCount(unionStorefrontSearch);
			logger.info("total:" + total);
			if (total > 0) {
				List<UnionStorefront> storefrontList = unionStorefrontService.getPaginatedList(unionStorefrontSearch);
				//转换volist集合
				storefrontVOList = UnionStorefrontObject.parseToStorefrontVOListByStorefrontList(storefrontList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope(total, storefrontVOList, msgId);
	}

	/**
	 *获取 联盟店面信息表详情
	 */
	@RequestMapping(value = "/getStorefront")
	@ResponseBody
	public Object getStorefront(@RequestBody StorefrontModel model, HttpServletRequest request) {

		if (model == null) {
			return new ResponseEnvelope<UnionStorefront>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,model.getMsgId());
		}
		Integer storefrontId = model.getStorefrontId();
		String msgId = model.getMsgId();

		UnionStorefrontVO storefrontVO = null;
		try {
			UnionStorefront unionStorefront = unionStorefrontService.get(storefrontId);
			//转换vo对象
			storefrontVO = UnionStorefrontObject.parseToStorefrontVOByStorefront(unionStorefront);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionStorefront>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionStorefrontVO>(storefrontVO,msgId,true);
	}

}
