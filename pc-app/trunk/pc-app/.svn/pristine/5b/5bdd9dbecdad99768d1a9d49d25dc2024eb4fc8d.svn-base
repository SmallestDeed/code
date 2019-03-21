package com.nork.cityunion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.nork.cityunion.model.SpecialOfferModel;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.vo.UnionSpecialOfferVO;
import com.nork.cityunion.model.vo.UnionStorefrontVO;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.objectconvert.cityUnion.UnionSpecialOfferObject;
import com.nork.common.param.ParamCommonVerification;
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

import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.search.UnionSpecialOfferSearch;
import com.nork.cityunion.model.small.UnionSpecialOfferSmall;
import com.nork.cityunion.service.UnionSpecialOfferService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionSpecialOfferController.java 
 * @Package com.nork.cityunion.controller
 * @Description:同城联盟-联盟优惠活动表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/cityunion/unionSpecialOffer")
public class UnionSpecialOfferController {
	private static Logger logger = Logger
			.getLogger(UnionSpecialOfferController.class);
	private final JsonDataServiceImpl<UnionSpecialOffer> JsonUtil = new JsonDataServiceImpl<UnionSpecialOffer>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/cityunion/unionSpecialOffer";
	private final String BASEMAIN = "/"+ STYLE + "/cityunion/base/unionSpecialOffer";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/cityunion/unionSpecialOffer";
	
	@Autowired
	private UnionSpecialOfferService unionSpecialOfferService;

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
	 *    联盟优惠活动表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    联盟优惠活动表 主页面
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
	 * 保存 联盟优惠活动表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@RequestBody UnionSpecialOffer unionSpecialOffer
			,HttpServletRequest request) throws Exception{

		if (unionSpecialOffer == null) {
			return new ResponseEnvelope<UnionSpecialOffer>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,unionSpecialOffer.getMsgId());
		}
		if (!ParamCommonVerification.checkTheParamIsValid(unionSpecialOffer.getSpecialOfferName(),unionSpecialOffer.getSpecialOfferContent())) {
			return new ResponseEnvelope<UnionSpecialOffer>(false, SystemCommonConstant.PARAM_EXCEPTION,unionSpecialOffer.getMsgId());
		}
		if (unionSpecialOffer.getUserId() == null) {
			LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
			if (loginUser == null) {
				return new ResponseEnvelope<UnionSpecialOffer>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY,unionSpecialOffer.getMsgId());
			} else {
				unionSpecialOffer.setUserId(loginUser.getId());
			}
		}
		UnionSpecialOfferVO specialOfferVO = null;
		try {
		    sysSave(unionSpecialOffer,request);
		    //处理优惠活动内容存储格式
			if(!unionSpecialOffer.isTransformContent()){
				unionSpecialOffer = unionSpecialOfferService.transformSpecialOfferContentToJson(unionSpecialOffer);
			}
			if (unionSpecialOffer.getId() == null || unionSpecialOffer.getId() == 0) {
				int id = unionSpecialOfferService.add(unionSpecialOffer);
				logger.info("add:id=" + id);
				unionSpecialOffer.setId(id);
			} else {
				int id = unionSpecialOfferService.update(unionSpecialOffer);
				logger.info(" update:id = " + id);
			}
			//处理活动内容显示格式
			if(!unionSpecialOffer.isTransformContent()){
				unionSpecialOffer = unionSpecialOfferService.transformSpecialOfferWithContent(unionSpecialOffer);
			}
			//转换vo对象
			specialOfferVO = UnionSpecialOfferObject.paramToSpecialOfferVOBySpecialOffer(unionSpecialOffer);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionSpecialOffer>(false, SystemCommonConstant.DATA_EXCEPTION,unionSpecialOffer.getMsgId());
		}
		return new ResponseEnvelope<UnionSpecialOfferVO>(specialOfferVO,unionSpecialOffer.getMsgId(),true);
	}
	
	/**
	 *获取 联盟优惠活动表详情
	 */
	@RequestMapping(value = "/getSpecialOffer")
	@ResponseBody
	public Object getSpecialOffer(@RequestBody SpecialOfferModel model, HttpServletRequest request) {

		if (model == null) {
			return new ResponseEnvelope<UnionSpecialOffer>(false, SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		Integer specialOfferId = model.getSpecialOfferId();
		String msgId = model.getMsgId();

		UnionSpecialOfferVO specialOfferVO = null;
		UnionSpecialOffer unionSpecialOffer = new UnionSpecialOffer();
		try {
			if(model.isTransformContent()){
				//不需要处理活动内容格式，可直接查询
				unionSpecialOffer = unionSpecialOfferService.get(specialOfferId);
				specialOfferVO = UnionSpecialOfferObject.paramToSpecialOfferVOBySpecialOffer(unionSpecialOffer);
			}else{
				//需要转换活动内容显示格式
				specialOfferVO = unionSpecialOfferService.getUnionSpecialOfferVOById(specialOfferId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
			return new ResponseEnvelope<UnionSpecialOffer>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<UnionSpecialOfferVO>(specialOfferVO,msgId,true);
	}
	
   /**
	 * 删除联盟优惠活动表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@RequestBody UnionSpecialOffer unionSpecialOffer
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionSpecialOffer = (UnionSpecialOffer)JsonUtil.getJsonToBean(jsonStr, UnionSpecialOffer.class);
			if(unionSpecialOffer == null){
			   return new ResponseEnvelope<UnionSpecialOffer>(false, "传参异常!","none");
			}
			ids =  unionSpecialOffer.getIds();
			msgId = unionSpecialOffer.getMsgId() ;
		}else{
			ids =  unionSpecialOffer.getIds();
			msgId = unionSpecialOffer.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<UnionSpecialOffer>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = unionSpecialOfferService.delete(id);
						logger.info("delete:id=" + id);
						//TODO 更新已发布方案关联信息
						UnionDesignPlanStoreRelease storeRelease = new UnionDesignPlanStoreRelease();
						storeRelease.setSpecialOfferId(id);
						unionDesignPlanStoreReleaseService.updateByStoreRelease(storeRelease);
					}
			   }else{
					Integer id = new Integer(ids);
					i = unionSpecialOfferService.delete(id);
					logger.info(" delete:id= " + id);
				   //TODO 更新已发布方案关联信息
				   UnionDesignPlanStoreRelease storeRelease = new UnionDesignPlanStoreRelease();
				   storeRelease.setSpecialOfferId(id);
				   unionDesignPlanStoreReleaseService.updateByStoreRelease(storeRelease);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<UnionSpecialOffer>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionSpecialOffer>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<UnionSpecialOffer>(true,msgId,true);
	}
	
	/**
	 * 联盟优惠活动表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("unionSpecialOfferSearch") UnionSpecialOfferSearch unionSpecialOfferSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
 		unionSpecialOfferSearch.setLimit(new Integer(20));
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionSpecialOfferSearch = (UnionSpecialOfferSearch)JsonUtil.getJsonToBean(jsonStr, UnionSpecialOfferSearch.class);
			if(unionSpecialOfferSearch == null){
			   return new ResponseEnvelope<UnionSpecialOffer>(false, "传参异常!","none");
			}
		}
		
		List<UnionSpecialOffer> list = new ArrayList<UnionSpecialOffer> ();
		int total = 0;
		try {
			total = unionSpecialOfferService.getCount(unionSpecialOfferSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionSpecialOfferService.getPaginatedList(
						unionSpecialOfferSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionSpecialOfferJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionSpecialOfferSmall> smallList= new JsonDataServiceImpl<UnionSpecialOfferSmall>().getJsonToBeanList(unionSpecialOfferJsonList, UnionSpecialOfferSmall.class);
				 return new ResponseEnvelope<UnionSpecialOfferSmall>(total,smallList,unionSpecialOfferSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionSpecialOffer>(false, "数据异常!",unionSpecialOfferSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionSpecialOffer>(total, list,unionSpecialOfferSearch.getMsgId());
	}
	

   /**
	 * 联盟优惠活动表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("unionSpecialOfferSearch") UnionSpecialOfferSearch unionSpecialOfferSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			unionSpecialOfferSearch = (UnionSpecialOfferSearch)JsonUtil.getJsonToBean(jsonStr, UnionSpecialOfferSearch.class);
			if(unionSpecialOfferSearch == null){
			   return new ResponseEnvelope<UnionSpecialOffer>(false, "传参异常!","none");
			}
		}
	
    	List<UnionSpecialOffer> list = new ArrayList<UnionSpecialOffer> ();
		int total = 0;
		try {
			total = unionSpecialOfferService.getCount(unionSpecialOfferSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionSpecialOfferService.getList(unionSpecialOfferSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String unionSpecialOfferJsonList = JsonUtil.getListToJsonData(list);
				 List<UnionSpecialOfferSmall> smallList= new JsonDataServiceImpl<UnionSpecialOfferSmall>().getJsonToBeanList(unionSpecialOfferJsonList, UnionSpecialOfferSmall.class);
				 return new ResponseEnvelope<UnionSpecialOfferSmall>(total,smallList,unionSpecialOfferSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionSpecialOffer>(false, "数据异常!",unionSpecialOfferSearch.getMsgId());
		}
		return new ResponseEnvelope<UnionSpecialOffer>(total, list,unionSpecialOfferSearch.getMsgId());
	}
	
   /**
	 *获取 联盟优惠活动表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		UnionSpecialOffer unionSpecialOffer = null;
		try {
			unionSpecialOffer = unionSpecialOfferService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionSpecialOffer>(false, "数据异常!");
		}
		ResponseEnvelope<UnionSpecialOffer> res = new ResponseEnvelope<UnionSpecialOffer>(unionSpecialOffer);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/unionSpecialOffer_edit";
		}else{
			url = JSPMAIN + "/unionSpecialOffer_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 联盟优惠活动表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("unionSpecialOfferSearch") UnionSpecialOfferSearch unionSpecialOfferSearch,HttpServletRequest request, HttpServletResponse response) {

		List<UnionSpecialOffer> list = new ArrayList<UnionSpecialOffer> ();
		int total = 0;
		try {
			total = unionSpecialOfferService.getCount(unionSpecialOfferSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = unionSpecialOfferService.getPaginatedList(
						unionSpecialOfferSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UnionSpecialOffer>(false, "数据异常!");
		}
		
		ResponseEnvelope<UnionSpecialOffer> res = new ResponseEnvelope<UnionSpecialOffer>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", unionSpecialOfferSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/unionSpecialOffer_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UnionSpecialOffer model,HttpServletRequest request){
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
	 * 联盟优惠活动表列表
	 */
	@RequestMapping(value = "/specialOfferList")
	@ResponseBody
	public Object specialOfferList(@RequestBody SpecialOfferModel model, HttpServletRequest request) {

		if (model == null) {
			return  new ResponseEnvelope<>(false,SystemCommonConstant.METHOD_PARAM_IS_EMPTY,"");
		}
		String msgId = model.getMsgId();
		Integer userId = model.getUserId();
		Integer start = model.getStart();
		Integer limit = model.getLimit();

		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser != null) {
			userId = loginUser.getId();
			model.setUserId(userId);
		} else {
			if (userId == null) {
				return new ResponseEnvelope<UnionSpecialOfferVO>(false, SystemCommonConstant.LOGIN_USER_IS_EMPTY, msgId);
			}
		}
		List<UnionSpecialOfferVO> specialOfferVOList = new ArrayList<UnionSpecialOfferVO> ();
		int total = 0;
		try {
			logger.info("查询参数：userId="+userId+",start="+start+",limit="+limit);
			total = unionSpecialOfferService.findSpecialOfferCount(userId,start,limit);
			logger.info("total:" + total);

			if (total > 0) {
				List<UnionSpecialOffer> specialOfferList = unionSpecialOfferService.findSpecialOfferList(model);
				//转换vo list集合
				specialOfferVOList = UnionSpecialOfferObject.paramToSpecialOfferVOListBySpecialOfferList(specialOfferList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<UnionSpecialOffer>(false, SystemCommonConstant.DATA_EXCEPTION,msgId);
		}
		return new ResponseEnvelope(total, specialOfferVOList,msgId);
	}

}
