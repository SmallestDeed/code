package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.nork.cityunion.model.UnionGroupModel;
import com.nork.cityunion.model.vo.UnionGroupDetailsVO;
import com.nork.cityunion.model.vo.UnionGroupVO;
import com.nork.cityunion.model.vo.UnionStorefrontVO;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.objectconvert.cityUnion.UnionGroupDetailsObject;
import com.nork.common.objectconvert.cityUnion.UnionGroupObject;
import com.nork.common.util.Constants;
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
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.base.service.impl.JsonDataServiceImpl;

import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.search.UnionGroupSearch;
import com.nork.cityunion.model.small.UnionGroupSmall;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.cityunion.service.UnionGroupDetailsService;
import com.nork.cityunion.service.UnionGroupService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionGroupController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟分组表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionGroup")
public class UnionGroupController {
	private static Logger logger = Logger
			.getLogger(UnionGroupController.class);
	private final JsonDataServiceImpl<UnionGroup> JsonUtil = new JsonDataServiceImpl<UnionGroup>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionGroup";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionGroup";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionGroup";
	
	@Autowired
	private UnionGroupService unionGroupService;
	@Autowired
	private UnionGroupDetailsService unionGroupDetailsService;
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
	 *    联盟分组表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟分组表 主页面
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
	 * 保存 联盟分组表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@RequestBody UnionGroup unionGroup
			,HttpServletRequest request) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   unionGroup = (UnionGroup)JsonUtil.getJsonToBean(jsonStr, UnionGroup.class);
		   if(unionGroup == null){
			  return new ResponseEnvelope<UnionGroup>(false, "传参异常!","none");
		   }
		}

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			unionGroup.setUserId(loginUser.getId());
		} else {
			if (unionGroup.getUserId() == null) {
				return new ResponseEnvelope<>(false,SystemCommonConstant.LOGIN_USER_IS_EMPTY,unionGroup.getMsgId());
			}
			loginUser = new LoginUser();
			loginUser.setId(unionGroup.getUserId());
			loginUser.setLoginName("nologin");
		}
		UnionGroupVO groupVO = null;
		try {
		    sysSave(unionGroup,request);
			if (unionGroup.getId() == null) {
				int id = unionGroupService.add(unionGroup);
				logger.info("add:id=" + id);
				unionGroup.setId(id);
			} else {
				int id = unionGroupService.update(unionGroup);
				logger.info( " update:id= " + id);
			}
			//转换vo对象
			groupVO = UnionGroupObject.parseToGroupVOByGroup(unionGroup);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!",unionGroup.getMsgId());
		}
		return new ResponseEnvelope(groupVO,unionGroup.getMsgId(),true);
	}
	
	/**
	 *获取 联盟分组表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("unionGroup") UnionGroup unionGroup
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroup = (UnionGroup)JsonUtil.getJsonToBean(jsonStr, UnionGroup.class);
			if(unionGroup == null){
			   return new ResponseEnvelope<UnionGroup>(false,"none", "传参异常!");
			}
			id =  unionGroup.getId();
			msgId = unionGroup.getMsgId() ;
		}else{
		    id =  unionGroup.getId();
		    msgId = unionGroup.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<UnionGroup>(false, "参数缺少id!",msgId);
		}
		
		try {
			unionGroup = unionGroupService.get(id);
			
			if("small".equals(style) && unionGroup != null ){
				 String unionGroupJson = JsonUtil.getBeanToJsonData(unionGroup);
				 UnionGroupSmall unionGroupSmall= new JsonDataServiceImpl<UnionGroupSmall>().getJsonToBean(unionGroupJson, UnionGroupSmall.class);
				 
				 return new ResponseEnvelope<UnionGroupSmall>(unionGroupSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionGroup>(unionGroup,msgId,true);
	}
	
   /**
	 * 删除联盟分组表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@RequestBody UnionGroup unionGroup
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroup = (UnionGroup)JsonUtil.getJsonToBean(jsonStr, UnionGroup.class);
			if(unionGroup == null){
			   return new ResponseEnvelope<UnionGroup>(false, "传参异常!","none");
			}
			ids =  unionGroup.getIds();
			msgId = unionGroup.getMsgId() ;
		}else{
			ids =  unionGroup.getIds();
			msgId = unionGroup.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionGroup>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = unionGroupService.deleteById(id);
						logger.info("delete:id=" + id);
						//删除联盟详情表
						unionGroupDetailsService.deleteByGroupId(id);
						//更新素材发布的联盟组信息
						unionDesignPlanStoreReleaseService.deleteByGroupId(id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = unionGroupService.deleteById(id);
					logger.info("delete:id=" + id);
					//删除联盟详情表
					unionGroupDetailsService.deleteByGroupId(id);
					//更新素材发布的联盟组信息
					unionDesignPlanStoreReleaseService.deleteByGroupId(id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<UnionGroup>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionGroup>(true,"删除成功！",msgId,true);
	}
	
	/**
	 * 品牌列表接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/brandList")
	@ResponseBody
	public ResponseEnvelope<BaseBrand> brandList(@RequestBody  BaseBrandSearch baseBrandSearch,
			HttpServletRequest request){
		String msgId = baseBrandSearch.getMsgId();
		return unionGroupService.myBrandList(msgId,baseBrandSearch);
	}

	/**
	 * 联盟分组表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionGroupSearch") UnionGroupSearch unionGroupSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionGroupSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroupSearch = (UnionGroupSearch)JsonUtil.getJsonToBean(jsonStr, UnionGroupSearch.class);
			if(unionGroupSearch == null){
			   return new ResponseEnvelope<UnionGroup>(false, "传参异常!","none");
			}
		}
		
		List<UnionGroup> list = new ArrayList<UnionGroup> ();
		int total = 0;
		try {
			total = unionGroupService.getCount(unionGroupSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionGroupService.getPaginatedList(
						unionGroupSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionGroupJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionGroupSmall> smallList= new JsonDataServiceImpl<UnionGroupSmall>().getJsonToBeanList(unionGroupJsonList, UnionGroupSmall.class);
				 return new ResponseEnvelope<UnionGroupSmall>(total,smallList,unionGroupSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!",unionGroupSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionGroup>(total, list,unionGroupSearch.getMsgId());
	}
	

   /**
	 * 联盟分组表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionGroupSearch") UnionGroupSearch unionGroupSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionGroupSearch = (UnionGroupSearch)JsonUtil.getJsonToBean(jsonStr, UnionGroupSearch.class);
			if(unionGroupSearch == null){
			   return new ResponseEnvelope<UnionGroup>(false, "传参异常!","none");
			}
		}
	
    	List<UnionGroup> list = new ArrayList<UnionGroup> ();
		int total = 0;
		try {
			total = unionGroupService.getCount(unionGroupSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionGroupService.getList(unionGroupSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionGroupJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionGroupSmall> smallList= new JsonDataServiceImpl<UnionGroupSmall>().getJsonToBeanList(unionGroupJsonList, UnionGroupSmall.class);
				 return new ResponseEnvelope<UnionGroupSmall>(total,smallList,unionGroupSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!",unionGroupSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionGroup>(total, list,unionGroupSearch.getMsgId());
	}
	
   /**
	 *获取 联盟分组表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionGroup unionGroup = null;
		try {
			unionGroup = unionGroupService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!");
		}
		ResponseEnvelope<UnionGroup> res = new ResponseEnvelope<UnionGroup>(unionGroup);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionGroup_edit";
		}else{
			url = JSPMAIN + "/unionGroup_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟分组表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionGroupSearch") UnionGroupSearch unionGroupSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionGroup> list = new ArrayList<UnionGroup> ();
		int total = 0;
		try {
			total = unionGroupService.getCount(unionGroupSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionGroupService.getPaginatedList(
						unionGroupSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionGroup> res = new ResponseEnvelope<UnionGroup>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionGroupSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionGroup_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionGroup model,HttpServletRequest request){
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
	 * 联盟分组表列表
	 */
	@RequestMapping(value = "/unionGroupList")
	@ResponseBody
	public Object unionGroupList(@RequestBody UnionGroupSearch unionGroupSearch
					,HttpServletRequest request) {

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			unionGroupSearch.setUserId(loginUser.getId());
		}else{
			if (unionGroupSearch.getUserId() == null) {
				return new ResponseEnvelope<UnionStorefrontVO>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, unionGroupSearch.getMsgId());
			}
		}
		if (unionGroupSearch.getStart() == null) {
			unionGroupSearch.setStart(0);
		}
		if (unionGroupSearch.getLimit() == null) {
			unionGroupSearch.setLimit(SystemCommonConstant.SYSTEM_DISPLAY_LIST_LIMIT);
		}
		unionGroupSearch.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
		List<UnionGroupVO> groupVOList = new ArrayList<UnionGroupVO> ();
		int total = 0;
		try {
			logger.info("查询参数：userId="+unionGroupSearch.getUserId()+",limit="+unionGroupSearch.getLimit());
			total = unionGroupService.getCount(unionGroupSearch);
			logger.info("total:" + total);

			if (total > 0) {
				List<UnionGroup> list = unionGroupService.getPaginatedList(unionGroupSearch);
				//转换联盟组voList
				groupVOList = UnionGroupObject.parseToGroupVOListByGroupList(list);
				for (UnionGroupVO groupVo : groupVOList) {
					List<UnionGroupDetails> groupDetailsList = unionGroupDetailsService.getListByGroupId(groupVo.getGroupId());
					//转换联盟组详情volist
					List<UnionGroupDetailsVO> groupDetailsVOList = UnionGroupDetailsObject.parseToGroupDetailsVOListByGroupDetailsList(groupDetailsList);
					groupVo.setGroupDetailsList(groupDetailsVOList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!",unionGroupSearch.getMsgId());
		}
		return new ResponseEnvelope<>(total, groupVOList,unionGroupSearch.getMsgId());
	}


	/**
	 *获取 联盟分组表详情
	 */
	@RequestMapping(value = "/getUnionGroup")
	@ResponseBody
	public Object getUnionGroup(@RequestBody UnionGroupModel model, HttpServletRequest request) {

		if (model == null) {
			return new ResponseEnvelope<UnionGroup>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		Integer groupId = model.getGroupId();
		String msgId = model.getMsgId();

		UnionGroupVO unionGroupVO = null;
		try {
			UnionGroup unionGroup = unionGroupService.get(groupId);
			//转换vo对象
			unionGroupVO = UnionGroupObject.parseToGroupVOByGroup(unionGroup);
			//获取联盟组明细
			List<UnionGroupDetails> groupDetailsList = unionGroupDetailsService.getListByGroupId(groupId);
			//转换联盟组明细volist
			List<UnionGroupDetailsVO> groupDetailsVOList = UnionGroupDetailsObject.parseToGroupDetailsVOListByGroupDetailsList(groupDetailsList);
			unionGroupVO.setGroupDetailsList(groupDetailsVOList);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionGroup>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionGroupVO>(unionGroupVO,msgId,true);
	}


}
