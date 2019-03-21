package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.cityunion.model.UnionGroupModel;
import com.nork.cityunion.model.vo.UnionGroupDetailsVO;
import com.nork.cityunion.model.vo.UnionSpecialOfferVO;
import com.nork.common.constant.SystemCommonConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
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
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.search.UnionGroupDetailsSearch;
import com.nork.cityunion.model.small.UnionGroupDetailsSmall;
import com.nork.cityunion.service.UnionGroupDetailsService;
import com.nork.cityunion.service.UnionGroupService;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import sun.rmi.runtime.Log;

/**   
 * @Title: UnionGroupDetailsController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟组明细表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:02
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionGroupDetails")
public class UnionGroupDetailsController {
	private static Logger logger = Logger
			.getLogger(UnionGroupDetailsController.class);
	private final JsonDataServiceImpl<UnionGroupDetails> JsonUtil = new JsonDataServiceImpl<UnionGroupDetails>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionGroupDetails";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionGroupDetails";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionGroupDetails";
	
	@Autowired
	private UnionGroupDetailsService unionGroupDetailsService;
	@Autowired
	private UnionGroupService unionGroupService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    联盟组明细表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟组明细表 主页面
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
	 * 保存 联盟组明细表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@RequestBody UnionGroupDetails unionGroupDetails
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   unionGroupDetails = (UnionGroupDetails)JsonUtil.getJsonToBean(jsonStr, UnionGroupDetails.class);
		   if(unionGroupDetails == null){
			  return new ResponseEnvelope<UnionGroupDetails>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(unionGroupDetails,request);
			if (unionGroupDetails.getId() == null) {
				UnionGroup unionGroup = new UnionGroup();
				unionGroup.setGroupName(unionGroupDetails.getGroupName());
				unionGroup.setUserId(unionGroupDetails.getUserId());
				sysSaveUnion(unionGroup,request);
				Integer unionGroupId = unionGroupService.add(unionGroup);
				List<UnionGroupDetails>list = unionGroupDetails.getDetailsList();
				for (UnionGroupDetails unionDetails : list) {
					unionDetails.setGroupId(unionGroupId);
				}
				int id = unionGroupDetailsService.batchInsertDataList(list);
				logger.info("添加了" + id +"条记录！");
//				int id = unionGroupDetailsService.add(unionGroupDetails);
//				logger.info("add:id=" + id);
			} else {
				if (StringUtils.isNotBlank(unionGroupDetails.getGroupName()) ) {
					UnionGroup union = new UnionGroup();
					union.setId(unionGroupDetails.getGroupId());
					union.setGroupName(unionGroupDetails.getGroupName());
					unionGroupService.update(union);
				}
				List<UnionGroupDetails> groupList = unionGroupDetails.getDetailsList();
				for (UnionGroupDetails groupDetails : groupList) {
					int id = unionGroupDetailsService.update(groupDetails);
					logger.info("update:id=" + id);
				}
			}
			
			if("small".equals(style)){
				 String unionGroupDetailsJson = JsonUtil.getBeanToJsonData(unionGroupDetails);
				 UnionGroupDetailsSmall unionGroupDetailsSmall= new JsonDataServiceImpl<UnionGroupDetailsSmall>().getJsonToBean(unionGroupDetailsJson, UnionGroupDetailsSmall.class);
				 
				 return new ResponseEnvelope<UnionGroupDetailsSmall>(unionGroupDetailsSmall,unionGroupDetails.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "数据异常!",unionGroupDetails.getMsgId());
		}
		return new ResponseEnvelope<UnionGroupDetails>(unionGroupDetails,unionGroupDetails.getMsgId(),true);
	}

	/**
	 * 保存 联盟组明细表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveUnionGroupInfo")
	@ResponseBody
	public Object saveUnionGroupInfo(@RequestBody UnionGroupModel model
							,HttpServletRequest request) throws Exception{

		if (model == null) {
			return new ResponseEnvelope<>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		String groupName = model.getGroupName();
		Integer groupId = model.getGroupId();
		Integer userId = model.getUserId();
		String msgId = model.getMsgId();
		List<UnionGroupDetailsVO> groupDetailsList = model.getUnionGroupDetails();
		if (StringUtils.isEmpty(groupName) && (groupId == null && groupId.intValue() == 0)) {
			return new ResponseEnvelope<>(false,SystemCommonConstant.METHOD_PARAM_IS_EMPTY,msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			userId = loginUser.getId();
		} else {
			if (userId == null) {
				return new ResponseEnvelope<>(false,SystemCommonConstant.LOGIN_USER_IS_EMPTY,msgId);
			}
			loginUser = new LoginUser();
			loginUser.setId(userId);
			loginUser.setLoginName("nologin");
		}
		//事物处理 保存联盟组信息
		UnionGroupDetailsVO unionGroupDetailsVO = unionGroupDetailsService.saveUnionGroupInfo(groupDetailsList,groupId,groupName,loginUser);

		if (unionGroupDetailsVO == null) {
			return new ResponseEnvelope<>(false,SystemCommonConstant.DATA_EXCEPTION,msgId);
		}
		return new ResponseEnvelope<>(unionGroupDetailsVO,msgId,true);
	}

	
	/**
	 *获取 联盟组
	 */
	@RequestMapping(value = "/getUnionGroupList")
	@ResponseBody
	public Object getUnionGroupList(String msgId, HttpServletRequest request) {

		String strUserId = request.getParameter("userId");
		Integer userId = null;
		if (StringUtils.isNotEmpty(strUserId)) {
			userId = Utils.getIntValue(strUserId);
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (userId == null) {
			if (loginUser == null) {
				return new ResponseEnvelope<>(false,SystemCommonConstant.LOGIN_USER_IS_EMPTY,msgId);
			} else {
				userId = loginUser.getId();
			}
		}
		String groupName = null;
		String shopName = null;
		List<UnionGroupDetails> resultList = new ArrayList<UnionGroupDetails>();
		List<UnionGroup> list = unionGroupService.getListByUserId(userId);
		UnionGroupDetails unionDetails = null;
		if (list != null & list.size() > 0) {
			for (UnionGroup unionGroup : list) {
				Integer groupId = unionGroup.getId();
				groupName = unionGroup.getGroupName();
				List<UnionGroupDetails> detailsList = unionGroupDetailsService.getListByGroupId(groupId);
				List<String> namesList = new ArrayList<>();
				if (detailsList != null && detailsList.size() > 0) {
					for (UnionGroupDetails unionGroupDetails : detailsList) {
						unionDetails =  new UnionGroupDetails();
						shopName = unionGroupDetails.getName();
						namesList.add(shopName);
					}
					unionDetails.setGroupId(groupId);
					unionDetails.setGroupName(groupName);
					unionDetails.setDetailNames(namesList);
					resultList.add(unionDetails);
				}
			}
		}else {
			new ResponseEnvelope<UnionGroupDetails>(false,"您的联盟组信息为空！",msgId);
		}
		return new ResponseEnvelope<UnionGroupDetails>(true,msgId,list.size(),resultList);
	}
	/**
	 *获取 联盟组明细表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionGroupDetails") UnionGroupDetails unionGroupDetails
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroupDetails = (UnionGroupDetails)JsonUtil.getJsonToBean(jsonStr, UnionGroupDetails.class);
			if(unionGroupDetails == null){
			   return new ResponseEnvelope<UnionGroupDetails>(false,"none", "传参异常!");
			}
			id =  unionGroupDetails.getId();
			msgId = unionGroupDetails.getMsgId() ;
		}else{
		    id =  unionGroupDetails.getId();
		    msgId = unionGroupDetails.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionGroupDetails>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionGroupDetails = unionGroupDetailsService.get(id);
			
			if("small".equals(style) && unionGroupDetails != null ){
				 String unionGroupDetailsJson = JsonUtil.getBeanToJsonData(unionGroupDetails);
				 UnionGroupDetailsSmall unionGroupDetailsSmall= new JsonDataServiceImpl<UnionGroupDetailsSmall>().getJsonToBean(unionGroupDetailsJson, UnionGroupDetailsSmall.class);
				 
				 return new ResponseEnvelope<UnionGroupDetailsSmall>(unionGroupDetailsSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionGroupDetails>(unionGroupDetails,msgId,true);
	}
	
   /**
	 * 删除联盟组明细表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@RequestBody UnionGroupDetails unionGroupDetails
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroupDetails = (UnionGroupDetails)JsonUtil.getJsonToBean(jsonStr, UnionGroupDetails.class);
			if(unionGroupDetails == null){
			   return new ResponseEnvelope<UnionGroupDetails>(false, "传参异常!","none");
			}
			ids =  unionGroupDetails.getIds();
			msgId = unionGroupDetails.getMsgId() ;
		}else{
			ids =  unionGroupDetails.getIds();
			msgId = unionGroupDetails.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionGroupDetails>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
//						i = unionGroupDetailsService.delete(id);
						i = unionGroupDetailsService.deleteById(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
//					i = unionGroupDetailsService.delete(id);
					i = unionGroupDetailsService.deleteById(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<UnionGroupDetails>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionGroupDetails>(true,msgId,true);
	}
	
	/**
	 * 联盟组明细表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionGroupDetailsSearch") UnionGroupDetailsSearch unionGroupDetailsSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionGroupDetailsSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroupDetailsSearch = (UnionGroupDetailsSearch)JsonUtil.getJsonToBean(jsonStr, UnionGroupDetailsSearch.class);
			if(unionGroupDetailsSearch == null){
			   return new ResponseEnvelope<UnionGroupDetails>(false, "传参异常!","none");
			}
		}
		
		List<UnionGroupDetails> list = new ArrayList<UnionGroupDetails> ();
		int total = 0;
		try {
			total = unionGroupDetailsService.getCount(unionGroupDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionGroupDetailsService.getPaginatedList(
						unionGroupDetailsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionGroupDetailsJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionGroupDetailsSmall> smallList= new JsonDataServiceImpl<UnionGroupDetailsSmall>().getJsonToBeanList(unionGroupDetailsJsonList, UnionGroupDetailsSmall.class);
				 return new ResponseEnvelope<UnionGroupDetailsSmall>(total,smallList,unionGroupDetailsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "数据异常!",unionGroupDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionGroupDetails>(total, list,unionGroupDetailsSearch.getMsgId());
	}
	

   /**
	 * 联盟组明细表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionGroupDetailsSearch") UnionGroupDetailsSearch unionGroupDetailsSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroupDetailsSearch = (UnionGroupDetailsSearch)JsonUtil.getJsonToBean(jsonStr, UnionGroupDetailsSearch.class);
			if(unionGroupDetailsSearch == null){
			   return new ResponseEnvelope<UnionGroupDetails>(false, "传参异常!","none");
			}
		}
	
    	List<UnionGroupDetails> list = new ArrayList<UnionGroupDetails> ();
		int total = 0;
		try {
			total = unionGroupDetailsService.getCount(unionGroupDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionGroupDetailsService.getList(unionGroupDetailsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionGroupDetailsJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionGroupDetailsSmall> smallList= new JsonDataServiceImpl<UnionGroupDetailsSmall>().getJsonToBeanList(unionGroupDetailsJsonList, UnionGroupDetailsSmall.class);
				 return new ResponseEnvelope<UnionGroupDetailsSmall>(total,smallList,unionGroupDetailsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "数据异常!",unionGroupDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionGroupDetails>(total, list,unionGroupDetailsSearch.getMsgId());
	}
	
   /**
	 *获取 联盟组明细表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionGroupDetails unionGroupDetails = null;
		try {
			unionGroupDetails = unionGroupDetailsService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "数据异常!");
		}
		ResponseEnvelope<UnionGroupDetails> res = new ResponseEnvelope<UnionGroupDetails>(unionGroupDetails);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionGroupDetails_edit";
		}else{
			url = JSPMAIN + "/unionGroupDetails_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟组明细表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionGroupDetailsSearch") UnionGroupDetailsSearch unionGroupDetailsSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionGroupDetails> list = new ArrayList<UnionGroupDetails> ();
		int total = 0;
		try {
			total = unionGroupDetailsService.getCount(unionGroupDetailsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionGroupDetailsService.getPaginatedList(
						unionGroupDetailsSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroupDetails>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionGroupDetails> res = new ResponseEnvelope<UnionGroupDetails>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionGroupDetailsSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionGroupDetails_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionGroupDetails model,HttpServletRequest request){
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
	private void sysSaveUnion(UnionGroup model,HttpServletRequest request){
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
}
