package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.nork.cityunion.model.DesignPlanStoreCatoryModel;
import com.nork.cityunion.model.StorefrontModel;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreCatoryVO;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.objectconvert.cityUnion.UnionDesignPlanStoreCatoryObject;
import com.nork.common.util.StringUtils;

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

import com.nork.cityunion.model.UnionDesignPlanStoreCatory;
import com.nork.cityunion.model.search.UnionDesignPlanStoreCatorySearch;
import com.nork.cityunion.model.small.UnionDesignPlanStoreCatorySmall;
import com.nork.cityunion.service.UnionDesignPlanStoreCatoryService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionDesignPlanStoreCatoryController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟设计方案库类别Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:06
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionDesignPlanStoreCatory")
public class UnionDesignPlanStoreCatoryController {
	private static Logger logger = Logger
			.getLogger(UnionDesignPlanStoreCatoryController.class);
	private final JsonDataServiceImpl<UnionDesignPlanStoreCatory> JsonUtil = new JsonDataServiceImpl<UnionDesignPlanStoreCatory>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionDesignPlanStoreCatory";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionDesignPlanStoreCatory";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionDesignPlanStoreCatory";
	
	@Autowired
	private UnionDesignPlanStoreCatoryService unionDesignPlanStoreCatoryService;

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
	 *    联盟设计方案库类别 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟设计方案库类别 主页面
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
	 * 保存 联盟设计方案库类别
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@RequestBody UnionDesignPlanStoreCatory storeCatory
			, HttpServletRequest request) throws Exception{

		if (storeCatory == null) {
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,storeCatory.getMsgId());
		}
		if (StringUtils.isEmpty(storeCatory.getCatoryName())) {
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.DATA_EXCEPTION,storeCatory.getMsgId());
		} else {
			storeCatory.setCatoryName(storeCatory.getCatoryName().trim());
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			storeCatory.setUserId(loginUser.getId());
		} else {
			if (storeCatory.getUserId() == null) {
				return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY,storeCatory.getMsgId());
			}
		}
		//验证该分类是否已存在发布数据
		int count = unionDesignPlanStoreCatoryService.findStoreCatoryCount(storeCatory.getUserId(),storeCatory.getCatoryName());
		if (count > 0) {
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "已存在该分类名称，请重新创建！",storeCatory.getMsgId());
		}
		UnionDesignPlanStoreCatoryVO storeCatoryVO = null;
		try {
		    sysSave(storeCatory,loginUser);
			if (storeCatory.getId() == null) {
				int id = unionDesignPlanStoreCatoryService.add(storeCatory);
				logger.info("add:id=" + id);
				storeCatory.setId(id);
			} else {
				int id = unionDesignPlanStoreCatoryService.update(storeCatory);
				logger.info("update:id = " + id);
			}
			//转换vo对象
			storeCatoryVO = UnionDesignPlanStoreCatoryObject.paramToStoreCatoryVOByStoreCatory(storeCatory);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.DATA_EXCEPTION,storeCatory.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreCatory>(storeCatoryVO,storeCatory.getMsgId(),true);
	}
	
	/**
	 *获取 联盟设计方案库类别详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionDesignPlanStoreCatory") UnionDesignPlanStoreCatory unionDesignPlanStoreCatory
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreCatory = (UnionDesignPlanStoreCatory)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreCatory.class);
			if(unionDesignPlanStoreCatory == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false,"none", "传参异常!");
			}
			id =  unionDesignPlanStoreCatory.getId();
			msgId = unionDesignPlanStoreCatory.getMsgId() ;
		}else{
		    id =  unionDesignPlanStoreCatory.getId();
		    msgId = unionDesignPlanStoreCatory.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionDesignPlanStoreCatory = unionDesignPlanStoreCatoryService.get(id);
			
			if("small".equals(style) && unionDesignPlanStoreCatory != null ){
				 String unionDesignPlanStoreCatoryJson = JsonUtil.getBeanToJsonData(unionDesignPlanStoreCatory);
				 UnionDesignPlanStoreCatorySmall unionDesignPlanStoreCatorySmall= new JsonDataServiceImpl<UnionDesignPlanStoreCatorySmall>().getJsonToBean(unionDesignPlanStoreCatoryJson, UnionDesignPlanStoreCatorySmall.class);
				 
				 return new ResponseEnvelope<UnionDesignPlanStoreCatorySmall>(unionDesignPlanStoreCatorySmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStoreCatory>(unionDesignPlanStoreCatory,msgId,true);
	}
	
   /**
	 * 删除联盟设计方案库类别,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@RequestBody UnionDesignPlanStoreCatory unionDesignPlanStoreCatory
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreCatory = (UnionDesignPlanStoreCatory)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreCatory.class);
			if(unionDesignPlanStoreCatory == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "传参异常!","none");
			}
			ids =  unionDesignPlanStoreCatory.getIds();
			msgId = unionDesignPlanStoreCatory.getMsgId() ;
		}else{
			ids =  unionDesignPlanStoreCatory.getIds();
			msgId = unionDesignPlanStoreCatory.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "参数ids不能为空!",msgId);
		}
		boolean flag = true;
		try{
			if (ids != null) {
			   if (ids.contains(",")) {
				   String[] strs = ids.split(",");
				   for (String c : strs) {
						Integer id = new Integer(c);
					   flag = unionDesignPlanStoreCatoryService.del(id);
					}
			   } else {
				   Integer id = new Integer(ids);
				   flag = unionDesignPlanStoreCatoryService.del(id);
			    }
			}
			if (!flag) {
				return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionDesignPlanStoreCatory>(true,msgId,true);
	}

	/**
	 * 联盟设计方案库类别列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreCatorySearch") UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionDesignPlanStoreCatorySearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreCatorySearch = (UnionDesignPlanStoreCatorySearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreCatorySearch.class);
			if(unionDesignPlanStoreCatorySearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "传参异常!","none");
			}
		}
		
		List<UnionDesignPlanStoreCatory> list = new ArrayList<UnionDesignPlanStoreCatory> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreCatoryService.getCount(unionDesignPlanStoreCatorySearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreCatoryService.getPaginatedList(
						unionDesignPlanStoreCatorySearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreCatoryJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreCatorySmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreCatorySmall>().getJsonToBeanList(unionDesignPlanStoreCatoryJsonList, UnionDesignPlanStoreCatorySmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreCatorySmall>(total,smallList,unionDesignPlanStoreCatorySearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "数据异常!",unionDesignPlanStoreCatorySearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreCatory>(total, list,unionDesignPlanStoreCatorySearch.getMsgId());
	}
	

   /**
	 * 联盟设计方案库类别全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionDesignPlanStoreCatorySearch") UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionDesignPlanStoreCatorySearch = (UnionDesignPlanStoreCatorySearch)JsonUtil.getJsonToBean(jsonStr, UnionDesignPlanStoreCatorySearch.class);
			if(unionDesignPlanStoreCatorySearch == null){
			   return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "传参异常!","none");
			}
		}
	
    	List<UnionDesignPlanStoreCatory> list = new ArrayList<UnionDesignPlanStoreCatory> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreCatoryService.getCount(unionDesignPlanStoreCatorySearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreCatoryService.getList(unionDesignPlanStoreCatorySearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionDesignPlanStoreCatoryJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionDesignPlanStoreCatorySmall> smallList= new JsonDataServiceImpl<UnionDesignPlanStoreCatorySmall>().getJsonToBeanList(unionDesignPlanStoreCatoryJsonList, UnionDesignPlanStoreCatorySmall.class);
				 return new ResponseEnvelope<UnionDesignPlanStoreCatorySmall>(total,smallList,unionDesignPlanStoreCatorySearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "数据异常!",unionDesignPlanStoreCatorySearch.getMsgId());
		}
		return new ResponseEnvelope<UnionDesignPlanStoreCatory>(total, list,unionDesignPlanStoreCatorySearch.getMsgId());
	}
	
   /**
	 *获取 联盟设计方案库类别详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionDesignPlanStoreCatory unionDesignPlanStoreCatory = null;
		try {
			unionDesignPlanStoreCatory = unionDesignPlanStoreCatoryService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "数据异常!");
		}
		ResponseEnvelope<UnionDesignPlanStoreCatory> res = new ResponseEnvelope<UnionDesignPlanStoreCatory>(unionDesignPlanStoreCatory);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionDesignPlanStoreCatory_edit";
		}else{
			url = JSPMAIN + "/unionDesignPlanStoreCatory_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟设计方案库类别列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionDesignPlanStoreCatorySearch") UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionDesignPlanStoreCatory> list = new ArrayList<UnionDesignPlanStoreCatory> ();
		int total = 0;
		try {
			total = unionDesignPlanStoreCatoryService.getCount(unionDesignPlanStoreCatorySearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionDesignPlanStoreCatoryService.getPaginatedList(
						unionDesignPlanStoreCatorySearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionDesignPlanStoreCatory> res = new ResponseEnvelope<UnionDesignPlanStoreCatory>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionDesignPlanStoreCatorySearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionDesignPlanStoreCatory_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionDesignPlanStoreCatory model,HttpServletRequest request){
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
	private void sysSave(UnionDesignPlanStoreCatory model,LoginUser loginUser){
		if(model != null){
			if (loginUser == null) {
				loginUser = new LoginUser();
				loginUser.setLoginName("nologin");
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
	 * 前端调用联盟设计方案库类别列表
	 */
	@RequestMapping(value = "/storeCatoryList")
	@ResponseBody
	public Object storeCatoryList(@RequestBody DesignPlanStoreCatoryModel model, HttpServletRequest request) {

		Integer userId = null;
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			userId = loginUser.getId();
		} else {
			if (model != null && model.getUserId() != null) {
				userId = model.getUserId();
			} else {
				return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY,model==null?"":model.getMsgId());
			}
		}
		List<UnionDesignPlanStoreCatoryVO> storeCatoryVOList = new ArrayList<UnionDesignPlanStoreCatoryVO> ();
		int total = 0;
		try {
			logger.info("查询参数：userId = "+userId);
			total = unionDesignPlanStoreCatoryService.findStoreCatoryCount(userId,null);
			logger.info("total:" + total);

			if (total > 0) {
				List<UnionDesignPlanStoreCatory> storeCatoryList = unionDesignPlanStoreCatoryService.findStoreCatoryList(userId);
				storeCatoryVOList = UnionDesignPlanStoreCatoryObject.paramToStoreCatoryVOListByStoreCatoryList(storeCatoryList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.DATA_EXCEPTION, model==null?"":model.getMsgId());
		}
		return new ResponseEnvelope(total, storeCatoryVOList, model==null?"":model.getMsgId());
	}

	/**
	 * U3d调用联盟设计方案库类别列表
	 */
	@RequestMapping(value = "/getStoreCatoryList")
	@ResponseBody
	public Object getStoreCatoryList(DesignPlanStoreCatoryModel model, HttpServletRequest request) {

		Integer userId = null;
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			userId = loginUser.getId();
		} else {
			if (model != null && model.getUserId() != null) {
				userId = model.getUserId();
			} else {
				return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY,model==null?"":model.getMsgId());
			}
		}
		List<UnionDesignPlanStoreCatoryVO> storeCatoryVOList = new ArrayList<UnionDesignPlanStoreCatoryVO> ();
		int total = 0;
		try {
			logger.info(" 查询参数：userId = "+userId);
			total = unionDesignPlanStoreCatoryService.findStoreCatoryCount(userId,null);
			logger.info(" total: " + total);

			if (total > 0) {
				List<UnionDesignPlanStoreCatory> storeCatoryList = unionDesignPlanStoreCatoryService.findStoreCatoryList(userId);
				storeCatoryVOList = UnionDesignPlanStoreCatoryObject.paramToStoreCatoryVOListByStoreCatoryList(storeCatoryList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.DATA_EXCEPTION, model==null?"":model.getMsgId());
		}
		return new ResponseEnvelope(total, storeCatoryVOList, model==null?"":model.getMsgId());
	}

	/**
	 * 验证分类是否存在发布方案库
	 */
	@RequestMapping(value = "/isExistCatoryReleasePlan")
	@ResponseBody
	public Object isExistCatoryReleasePlan(@RequestBody DesignPlanStoreCatoryModel model, HttpServletRequest request) {

		if (model == null || model.getCatoryId() == null) {
			return new ResponseEnvelope<UnionDesignPlanStoreCatory>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,model==null?"":model.getMsgId());
		}
		Integer catoryId = model.getCatoryId();
		String msgId = model.getMsgId();

		UnionDesignPlanStoreReleaseSearch storeRelease = new UnionDesignPlanStoreReleaseSearch();
		storeRelease.setCatoryId(catoryId);
		storeRelease.setIsDeleted(0);
		int count = unionDesignPlanStoreReleaseService.getCount(storeRelease);
		if (count > 0) {
			return new ResponseEnvelope(false, "确认删除后联盟方案将移动到默认文件夹！", msgId);
		}
		return new ResponseEnvelope(true, "该分类不存在发布数据，可直接删除。", msgId);
	}
}
