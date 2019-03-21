package com.nork.design.controller.web;

import java.util.ArrayList;
import java.util.Date;
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
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanCommentCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanComment;
import com.nork.design.model.UserDesignPlanComment;
import com.nork.design.model.search.DesignPlanCommentSearch;
import com.nork.design.model.small.DesignPlanCommentSmall;
import com.nork.design.service.DesignPlanCommentService;
import com.nork.design.service.DesignPlanService;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.small.UserProductCollectSmall;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResPicService;

@Controller
@RequestMapping("/{style}/web/design/designPlanComment")
public class WebDesignPlanCommentController {
	private static Logger logger = Logger
			.getLogger(WebDesignPlanCommentController.class);
	private final JsonDataServiceImpl<DesignPlanComment> JsonUtil = new JsonDataServiceImpl<DesignPlanComment>();
	private final String STYLE="online";		
	private final String JSPSTYLE="online";
	private final String MAIN = "/"+ STYLE + "/design/designPlanComment";
	private final String BASEMAIN = "/"+ STYLE + "/design/base/designPlanComment";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/design/designPlanComment";
	
	@Autowired
	private DesignPlanCommentService designPlanCommentService;
	@Autowired
	private DesignPlanService designPlanService;
	
	@Autowired
	private ResPicService resPicService;
	/**
	 * 获得评论表列表--接口
	 * @param style
	 * @param designPlanCommentSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCommentList")
	@ResponseBody
	public Object getCommentList(
			@PathVariable String style,@ModelAttribute("designPlanCommentSearch") DesignPlanCommentSearch designPlanCommentSearch,HttpServletRequest request, HttpServletResponse response) {
		List<DesignPlanComment> list = new ArrayList<DesignPlanComment> ();
		//UserDesignPlanComment userDesignPlanComment = new UserDesignPlanComment();
		DesignPlanComment designPlanComment = new DesignPlanComment();
		//userDesignPlanComment.setDesignPlanId(designPlanCommentSearch.getDesignPlanId());
		designPlanComment.setDesignPlanId(designPlanCommentSearch.getDesignPlanId());
		
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designPlanCommentSearch = (DesignPlanCommentSearch)JsonUtil.getJsonToBean(jsonStr, DesignPlanCommentSearch.class);
			if(designPlanCommentSearch == null){
				return new ResponseEnvelope<DesignPlanComment>(false, "传参异常!","none");
			}
		}
		
		String msg = "";
		if(StringUtils.isBlank(designPlanCommentSearch.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanCommentSearch.getMsgId());
		}
		if(designPlanCommentSearch.getDesignPlanId() == null ){
			msg = "参数designPlanId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanCommentSearch.getMsgId());
		}else if(designPlanCommentSearch.getDesignPlanId() != null){
			DesignPlan designPlan=null;
			if(Utils.enableRedisCache()){
				designPlan = DesignCacher.get(designPlanCommentSearch.getDesignPlanId());
			}else{
				designPlan = designPlanService.get(designPlanCommentSearch.getDesignPlanId());
			}
		     
			if(designPlan == null ){
				msg = "该设计方案不存在";
				return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanCommentSearch.getMsgId());
			}
		}
		
		int total = 0;
		try {
			if(Utils.enableRedisCache()){
				total = DesignPlanCommentCacher.getTotalWithParameter(designPlanCommentSearch);
			}else{
				total = designPlanCommentService.getCount(designPlanCommentSearch);
			}
			
			logger.info("total:" + total);
			
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = DesignPlanCommentCacher.getUDPCList(designPlanCommentSearch);
				}else{
					list = designPlanCommentService.getUDPCList(designPlanCommentSearch);
				}
                
				if(list != null && list.size()>0){
					for(DesignPlanComment uudp:list){
						if(uudp.getPic() !=null &&  uudp.getPic() > 0){
							ResPic pic = resPicService.get(uudp.getPic());
							if(pic != null){
							   uudp.setPicPath(Utils.getValue("app.resources.url", "").trim() + pic.getPicPath());
							}else{
							   uudp.setPicPath(Utils.getValue("app.server.url", "").trim() + "/pages/online/images/user/user_default.png");
							}
						}else{
							 uudp.setPicPath(Utils.getValue("app.server.url", "").trim() + "/pages/online/images/user/user_default.png");
						}
					}
				}
			}
			
			if("small".equals(style) && list != null && list.size() > 0){
				String designPlanCommentJsonList = JsonUtil.getListToJsonData(list);
				List<DesignPlanComment> smallList= new JsonDataServiceImpl<DesignPlanComment>().getJsonToBeanList(designPlanCommentJsonList, UserDesignPlanComment.class);
				Long endTime=System.currentTimeMillis();
				//////System.out.println("times1:"+(endTime-startTime));
				return new ResponseEnvelope<DesignPlanComment>(total,smallList,designPlanCommentSearch.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanComment>(false, "数据异常!");
		}
		Long endTime=System.currentTimeMillis();
		//////System.out.println("times:"+(endTime-startTime));
		return new ResponseEnvelope<DesignPlanComment>(total, list ,designPlanCommentSearch.getMsgId());
		
	}
	
	/**
	 * 评论表列表---jsp
	 */
	@RequestMapping(value = "/getUdpcjsplist")
	@ResponseBody
	public Object getUdpcjsplist(
			@PathVariable String style,@ModelAttribute("designPlanCommentSearch") DesignPlanCommentSearch designPlanCommentSearch,HttpServletRequest request, HttpServletResponse response) {
		
		List<DesignPlanComment> list = new ArrayList<DesignPlanComment> ();
		String designPlanId = request.getParameter("designPlanId"); 
		Integer designId = -1;
		if(designPlanId!=null && !"".equals(designPlanId)){
			designId =Integer.parseInt(designPlanId);
		} 
		
		if(designPlanId == null) {
			return new ResponseEnvelope<DesignPlanComment>(false, "数据异常!");
		}
		designPlanCommentSearch.setDesignPlanId(designId);
		int total = 0;
		try {
			//DesignPlanCommentSearch designPlanCommentSearch = new DesignPlanCommentSearch();
			designPlanCommentSearch.setDesignPlanId(designId);
			total = designPlanCommentService.getCount(designPlanCommentSearch);
			if(total >0 ){
				list = designPlanCommentService.getUDPCList(designPlanCommentSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserDesignPlanComment>(false, "数据异常!");
		}
		ResponseEnvelope<DesignPlanComment> res = new ResponseEnvelope<DesignPlanComment>(list.size(), list);
		request.setAttribute("list", list);
		request.setAttribute("total", total);
		request.setAttribute("res", res);
//		String userDesignPlanCommentList = JsonUtil.getListToJsonData(list);
//		if(("").equals(userDesignPlanCommentList)){
//			List<UserDesignPlanComment> smallList = new ArrayList<UserDesignPlanComment>();
//			return new ResponseEnvelope<UserDesignPlanComment>(list.size(),smallList);
//		}
//		 List<UserDesignPlanComment> smallList= new JsonDataServiceImpl<UserDesignPlanComment>().getJsonToBeanList(userDesignPlanCommentList, UserDesignPlanComment.class);
		return new ResponseEnvelope<DesignPlanComment>(total,list);
		
	}
	
	/**
	 * 保存 评论表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("designPlanComment") DesignPlanComment designPlanComment
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		String content = request.getParameter("content");
		String designPlanName = request.getParameter("designPlanName");
		Integer designPlanId = Integer.parseInt(request.getParameter("designPlanId"));
		Integer userID = null;
		if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			designPlanComment.setUserId(null);
		 }else{
			 LoginUser loginUser = new LoginUser();
			 loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			 userID  = loginUser.getId();
			 designPlanComment.setUserId(userID);
		 }
		designPlanComment.setDiscussionprogram(designPlanName);
		designPlanComment.setContent(content);
		designPlanComment.setDesignPlanId(designPlanId);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   designPlanComment = (DesignPlanComment)JsonUtil.getJsonToBean(jsonStr, DesignPlanComment.class);
		   if(designPlanComment == null){
			  return new ResponseEnvelope<DesignPlanComment>(false, "传参异常!","none");
		   }
		}
		
		try {
		    sysSave(designPlanComment,request);
			if (designPlanComment.getId() == null) {
				int id = designPlanCommentService.add(designPlanComment);
				logger.info("add:id=" + id);
				designPlanComment.setId(id);
			} else {
				int id = designPlanCommentService.update(designPlanComment);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String designPlanCommentJson = JsonUtil.getBeanToJsonData(designPlanComment);
				 DesignPlanCommentSmall designPlanCommentSmall= new JsonDataServiceImpl<DesignPlanCommentSmall>().getJsonToBean(designPlanCommentJson, DesignPlanCommentSmall.class);
				 
				 return new ResponseEnvelope<DesignPlanCommentSmall>(designPlanCommentSmall,designPlanComment.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanComment>(false, "数据异常!",designPlanComment.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanComment>(designPlanComment,designPlanComment.getMsgId(),true);
	}
	
	/**
	 * 保存评论----接口
	 * @param style
	 * @param designPlanComment
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveComment")
	@ResponseBody
	public Object saveComment(
			@PathVariable String style,@ModelAttribute("designPlanComment") DesignPlanComment designPlanComment
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designPlanComment = (DesignPlanComment)JsonUtil.getJsonToBean(jsonStr, DesignPlanComment.class);
			if(designPlanComment == null){
				return new ResponseEnvelope<DesignPlanComment>(false, "传参异常!","none");
			}
		}
		
		Integer userId = 0;
		//获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<UserProductCollect>(false, "登录超时，请重新登录!",designPlanComment.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			userId = loginUser.getId();
			designPlanComment.setUserId(userId);
		}
		
		String msg = "";
		if( StringUtils.isBlank(designPlanComment.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanComment.getMsgId());
		}
		if( designPlanComment.getUserId() == null || designPlanComment.getUserId() == 0 ){
			msg = "参数userId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanComment.getMsgId());
		}
		if( designPlanComment.getDesignPlanId() == null ){
			msg = "参数designPlanId不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanComment.getMsgId());
		}else if(designPlanComment.getDesignPlanId() != null){
			DesignPlan dpc = new DesignPlan();
			dpc = designPlanService.get(designPlanComment.getDesignPlanId());
			if(dpc == null){
				msg = "该方案不存在";
				return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanComment.getMsgId());
			}
		}
		if( StringUtils.isBlank(designPlanComment.getContent()) ){
			msg = "评论内容不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false,msg,designPlanComment.getMsgId());
		}
		
		try {
			sysSave(designPlanComment,request);
			if (designPlanComment.getId() == null) {
				int id = designPlanCommentService.add(designPlanComment);
				logger.info("add:id=" + id);
				designPlanComment.setId(id);
			} else {
				int id = designPlanCommentService.update(designPlanComment);
				DesignPlanCommentCacher.remove(designPlanComment.getId());
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				String designPlanCommentJson = JsonUtil.getBeanToJsonData(designPlanComment);
				DesignPlanCommentSmall designPlanCommentSmall= new JsonDataServiceImpl<DesignPlanCommentSmall>().getJsonToBean(designPlanCommentJson, DesignPlanCommentSmall.class);
				return new ResponseEnvelope<DesignPlanCommentSmall>(designPlanCommentSmall,designPlanComment.getMsgId(),true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanComment>(false, "数据异常!",designPlanComment.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanComment>(designPlanComment,designPlanComment.getMsgId(),true);
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanComment model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
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
