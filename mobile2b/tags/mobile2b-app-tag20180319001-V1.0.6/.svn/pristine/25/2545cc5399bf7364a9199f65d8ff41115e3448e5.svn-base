package com.nork.system.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import org.springframework.web.multipart.MultipartFile;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.system.cache.DesignerWorksCacher;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.DesignerWorks;
import com.nork.system.model.DesignerWorksUser;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResPicResult;
import com.nork.system.model.search.DesignerWorksSearch;
import com.nork.system.model.search.ResPicSearch;
import com.nork.system.model.small.DesignerWorksSmall;
import com.nork.system.service.DesignerWorksService;
import com.nork.system.service.ResPicService;

@Controller
@RequestMapping("/{style}/web/system/designerWorks")
public class WebDesignerWorksController {
	private static Logger logger = Logger
			.getLogger(WebDesignerWorksController.class);
	private final JsonDataServiceImpl<DesignerWorks> JsonUtil = new JsonDataServiceImpl<DesignerWorks>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="online";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/user";
	private final String UPLOAD_PATH = "system.sysUser.designerWorks.piclist.path";
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	@Autowired
	private DesignerWorksService designerWorksService;
	@Autowired
	private ResPicService resPicService;
	
	/**
	 * 设计师作品列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("designerWorks") DesignerWorks designerWorks,HttpServletRequest request, HttpServletResponse response) {
		
		DesignerWorksSearch designerWorksSearch =new DesignerWorksSearch();
		 LoginUser loginUser = new LoginUser();
		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		 }else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		    /**designerWorksUser.setUserId(loginUser.getId());*/
		    designerWorksSearch.setUserId(loginUser.getId());
		 }
		 designerWorksSearch.setTitle(designerWorks.getTitle());
		List<DesignerWorks> list = new ArrayList<DesignerWorks> ();
		int total = 0;
		try {
			total = designerWorksService.getCount(designerWorksSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = designerWorksService.getPaginatedList(designerWorksSearch);
				for (DesignerWorks designerWork : list) {
					ResPicSearch resPictSearch =new ResPicSearch();
					if(!"".equals(designerWork.getPicId()) && designerWork.getPicId() != null){
						
						resPictSearch.setId(Integer.parseInt(designerWork.getPicId()));
						List<ResPic> resPic = resPicService.getPaginatedList(resPictSearch);
						if(resPic!=null&&resPic.size()>0){
							designerWork.setPicPath(resPic.get(0).getPicPath());
						}else{
							designerWork.setPicPath(null);
						}
					}else{
						designerWork.setPicPath(null);
					}
				}
				/**list = designerWorksService.getdesignerWorkDetail(designerWorksUser);*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorks>(false, "数据异常!");
		}
		
		ResponseEnvelope<DesignerWorks> res = new ResponseEnvelope<DesignerWorks>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", designerWorksSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/designerWorks/designerWorks");
	}
	

	/**
	 * 修改设计师作品
	 */
	@RequestMapping(value = "/updateDesignerWork")
	@ResponseBody
	public Object updateDesignerWork(
			@ModelAttribute("designerWorksUser") DesignerWorksUser designerWorksUser,HttpServletRequest request, HttpServletResponse response) {
		try {
				designerWorksUser = designerWorksService.getdesignerWorkRendered(designerWorksUser);
				String pic = designerWorksUser.getPicIds();
				if(!"".equals(pic) && pic != null){
					String pics []  = pic.split(",");
					if(pics != null && pics.length > 0){
						for (int i = 0; i < pics.length; i++) {
							ResPicResult resPicResult = new ResPicResult();
							ResPic respic = resPicService.get(Integer.parseInt(pics[i]));
							if(respic!=null){
								resPicResult.setPicId(respic.getId());
								resPicResult.setPicPath(respic.getPicPath());
							}
							designerWorksUser.getPicList().add(resPicResult);
						}
						
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorksUser>(false, "数据异常!");
		}
//		
//		request.setAttribute("val", designerWorksUser);
//		
//		return  Utils.getPageUrl(request, JSPMAIN + "/designerWorks/addDesignerWork");
		return new ResponseEnvelope<DesignerWorksUser>(designerWorksUser,designerWorksUser.getMsgId(),true);
	}
	
	/**
	 * 删除图片
	 */
	@RequestMapping(value = "/deleteImages")
	@ResponseBody
	public Object deleteImages(
			@ModelAttribute("designerWorksUser") DesignerWorksUser designerWorksUser,HttpServletRequest request, HttpServletResponse response) {
		DesignerWorks designerWorks = new DesignerWorks();
		try {
			 designerWorks  = designerWorksService.get(designerWorksUser.getId());
			
			String picS =  designerWorks.getPicIds();
			String detect = picS;
			String pic_s = designerWorksUser.getPicIds();
			//picS = (picS).replace(","+pic_s+",", "");
			if(picS.equals(pic_s)){
				 pic_s = pic_s;
			}else{
				pic_s = pic_s+",";
			}
			picS = (","+picS+",").replace(pic_s, "");
			if(picS.startsWith(",")){
				picS = picS.substring(1,picS.length());	
			}
			if(picS.endsWith(",")){
				picS = picS.substring(0,picS.length()-1);	
			}
			if(detect.equals(pic_s)){
				designerWorks.setPicIds(picS);
				designerWorks.setPicId(picS);
			}else{
				designerWorks.setPicIds(picS);
			}
			designerWorks.setTitle(designerWorksUser.getTitle());
			designerWorks.setArea(designerWorksUser.getArea());
			designerWorks.setProjectTime(designerWorksUser.getProjectTime());
			designerWorks.setWorksDescription(designerWorksUser.getWorksDescription());
			int j = designerWorksService.update(designerWorks);
			logger.info("update j =:" + j);
			//删除res_pic表的数据
			String pic = designerWorksUser.getPicIds();
			if(pic.equals(picS)){
				if(pic != null){
					int i  = resPicService.delete(Integer.parseInt(pic));
					logger.info("delete i =:" + i);
				}
			}
		} 	catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorksUser>(false, "数据异常!",designerWorksUser.getMsgId());
		}
		
		String pic = designerWorks.getPicIds();
		if(!"".equals(pic) && pic != null){
			if(pic.contains(",")){
				String pics []  = pic.split(",");
				if(pics != null && pics.length > 0){
					for (int i = 0; i < pics.length; i++) {
						ResPicResult resPicResult = new ResPicResult();
						ResPic respic = resPicService.get(Integer.parseInt(pics[i]));
						if(respic!=null){
							resPicResult.setPicId(respic.getId());
							resPicResult.setPicPath(respic.getPicPath());
						}
						designerWorks.getPicList().add(resPicResult);
					}
					
				}
			}else{
				ResPicResult resPicResult = new ResPicResult();
				ResPic respic = resPicService.get(Integer.parseInt(pic));
				if(respic!=null){
					resPicResult.setPicId(respic.getId());
					resPicResult.setPicPath(respic.getPicPath());
				}
				designerWorks.getPicList().add(resPicResult);
			}
		}
		return new ResponseEnvelope<DesignerWorks>(designerWorks,designerWorks.getMsgId(),true);
	}
	
	/**
	 * 删除设计师作品---接口
	 */
	@RequestMapping(value = "/delWorks")
	@ResponseBody
	public Object delWorks(@ModelAttribute("designerWorks") DesignerWorks designerWorks
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String msg = "";	
		String ids   = "";
		
		
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designerWorks = (DesignerWorks)JsonUtil.getJsonToBean(jsonStr, DesignerWorks.class);
			if(designerWorks == null){
			   return new ResponseEnvelope<DesignerWorks>(false, "传参异常!","none");
			}
			ids =  designerWorks.getIds();
			msgId = designerWorks.getMsgId() ;
		}else{
			ids =  designerWorks.getIds();
			msgId = designerWorks.getMsgId() ;
		}
		
		if( StringUtils.isBlank(designerWorks.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignerWorks>(false,msg,designerWorks.getMsgId());
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<DesignerWorks>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = designerWorksService.delete(id);
						DesignerWorksCacher.removeDesignerWorkById(id);/**清除缓存*/
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = designerWorksService.delete(id);
					DesignerWorksCacher.removeDesignerWorkById(id);/**清除缓存*/
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<DesignerWorks>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorks>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<DesignerWorks>(true,msgId,true);
	}
	
	/**
	 * 设计师作品列表---jsp
	 */
	@RequestMapping(value = "/designerWorkslist")
	public Object designerWorkslist(
			@ModelAttribute("designerWorksUser") DesignerWorksUser designerWorksUser,HttpServletRequest request, HttpServletResponse response) {
		
		LoginUser loginUser = new LoginUser();
		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		 }else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		    designerWorksUser.setUserId(loginUser.getId());
		 }
		String ids = request.getParameter("ids");
		String id [] = ids.split("_");
		String idvalue = id[1];
		List<DesignerWorksUser> list = new ArrayList<DesignerWorksUser> ();
		int total = 0;
		DesignerWorksSearch designerWorksSearch =new DesignerWorksSearch();
		try {
			designerWorksSearch.setUserId(designerWorksUser.getUserId());
			total = designerWorksService.getCount(designerWorksSearch);
			logger.info("total:" + total);
			
			if (total > 0) {
				//list = designerWorksService.getPaginatedList(designerWorksSearch);
				list = designerWorksService.getdesignerWorkDetail(designerWorksUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorksUser>(false, "数据异常!");
		}
		
		ResponseEnvelope<DesignerWorksUser> res = new ResponseEnvelope<DesignerWorksUser>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("idval", Integer.parseInt(idvalue));
		request.setAttribute("search", designerWorksSearch);
		
		return  Utils.getPageUrl(request, JSPMAIN + "/designerWorks/designerWorksList");
	}
	
	/**
	 * 设计师作品列表---jsp
	 */
	@RequestMapping(value = "/designerWorksListWeb")
	@ResponseBody
	public Object designerWorksList(@PathVariable String style,
	            @ModelAttribute("designerWorksUser") DesignerWorksUser designerWorksUser,HttpServletRequest request, HttpServletResponse response) {
		DesignerWorks designerWorks = new DesignerWorks();
		
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designerWorks = (DesignerWorks) JsonUtil.getJsonToBean(jsonStr, DesignerWorks.class);
			if (designerWorksUser.getId() == null) {
				return new ResponseEnvelope<DesignerWorksUser>(false, "传参异常!", "none");
			}
		} 
		String msg = "";
		if( StringUtils.isBlank(designerWorksUser.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignerWorksUser>(false,msg,designerWorksUser.getMsgId());
		} 
		
		if( designerWorksUser.getUserId() == null ){
			msg = "参数userId不能为空";
			return new ResponseEnvelope<DesignerWorksUser>(false,msg,designerWorksUser.getMsgId());
		} 
		
/**		 LoginUser loginUser = new LoginUser();
 		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
 			loginUser.setLoginName("nologin");
 		 }else{
 		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
 		 }
	 	 designerWorksUser.setUserId(designerWorksUser);*/
		
		List<DesignerWorksUser> list = new ArrayList<DesignerWorksUser> ();
		DesignerWorksSearch designerWorksSearch =new DesignerWorksSearch();
		int total = 0;
		try {
			designerWorksSearch.setUserId(designerWorksUser.getUserId());
			if(Utils.enableRedisCache()){
				total = DesignerWorksCacher.getDesignerWorksCount(designerWorksSearch);
			}else{
				total = designerWorksService.getCount(designerWorksSearch);
			}
			
            logger.info("total:" + total);
            
			if (total > 0) {
				/**list = designerWorksService.getPaginatedList(designerWorksSearch);*/
				/**list = designerWorksService.getdesignerWorkDetail(designerWorksUser);*/
				if(Utils.enableRedisCache()){
					list = DesignerWorksCacher.getdesignerWorkDetail(designerWorksUser);
				}else{
					list = designerWorksService.getdesignerWorkDetail(designerWorksUser);
				}
				
			}
			
			if("small".equals(style) && list != null && list.size() > 0){
				 String designerWorksUserJsonList = JsonUtil.getListToJsonData(list);
				 List<DesignerWorksUser> smallList= new JsonDataServiceImpl<DesignerWorksUser>().getJsonToBeanList(designerWorksUserJsonList, DesignerWorksUser.class);
				 return new ResponseEnvelope<DesignerWorksUser>(total,smallList,designerWorksUser.getMsgId());
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorksUser>(false, "数据异常!",designerWorksUser.getMsgId());
		}
		return new ResponseEnvelope<DesignerWorksUser>(total, list,designerWorksUser.getMsgId());
	}
	
	
	
	/**
	 * 设计师作品渲染图
	 */
	@RequestMapping(value = "/designerWorksRendered")
	public Object designerWorksRendered(
			@ModelAttribute("designerWorksUser") DesignerWorksUser designerWorksUser,HttpServletRequest request, HttpServletResponse response) {
		DesignerWorksUser designerWorksUserDb  = null;
		try {
			List<DesignerWorksUser> designerWorksUserList = designerWorksService.getdesignerWorkDetail(designerWorksUser);
			String renderPicIds = "";
			Integer designerId = null;
			if(designerWorksUserList != null && designerWorksUserList.size() > 0 ){
				designerId = designerWorksUserList.get(0).getId();
			}
			designerWorksUser.setId(designerId);
			designerWorksUserDb = designerWorksService.getdesignerWorkRendered(designerWorksUser);
			if(designerWorksUserDb != null ){
				renderPicIds = designerWorksUserDb.getPicIds();
				if(renderPicIds !=null && !"".equals(renderPicIds)){
					if(renderPicIds.contains(",")){
						String[] strs = renderPicIds.split(",");
						
						for(String picId : strs){
							ResPic resPic = resPicService.get(Utils.getIntValue(picId));
							designerWorksUserDb.getRenderPicList().add(resPic==null?"":resPic.getPicPath());
						}
					}else{
						ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
						designerWorksUserDb.getRenderPicList().add(resPic==null?"":resPic.getPicPath());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorksUser>(false, "数据异常!");
		}
		
		request.setAttribute("designerWork", designerWorksUserDb);
		
		return  Utils.getPageUrl(request, JSPMAIN + "/designerWorks/designerWorksDetail");
	}
	
	/**
	 * 修改设计师作品---接口
	 */
	@RequestMapping(value = "/modifyDesignerWork")
	@ResponseBody
	public Object modifyDesignerWork(
			@ModelAttribute("designerWorksUser") DesignerWorksUser designerWorksUser,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		DesignerWorks designerWorks =new DesignerWorks();
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designerWorks = (DesignerWorks)JsonUtil.getJsonToBean(jsonStr, DesignerWorks.class);
			if(designerWorks == null){
				return new ResponseEnvelope<DesignerWorks>(false, "传参异常!","none");
			}
		}
 		/**获取登录用户
		LoginUser loginUser = new LoginUser();
		if( com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null ){
			return new ResponseEnvelope<DesignerWorksUser>(false, "登录超时，请重新登录!",designerWorksUser.getMsgId());
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			designerWorksUser.setUserId(loginUser.getId());
     	}*/
		
		String msg = "";
		if( StringUtils.isBlank(designerWorksUser.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignerWorksUser>(false,msg,designerWorksUser.getMsgId());
		}
		DesignerWorksUser designerWorksUserDb = null;
		/**DesignerWorksUser designerWorksUser = new DesignerWorksUser();
		designerWorksUser.setId(designerWorks.getId());**/
		try {
			if(Utils.enableRedisCache()){
				designerWorksUserDb = DesignerWorksCacher.getdesignerWorkRendered(designerWorksUser);
			}else{
				designerWorksUserDb = designerWorksService.getdesignerWorkRendered(designerWorksUser);
			}
				
				String pic = designerWorksUserDb.getPicIds();
				if(!"".equals(pic) && pic != null){
					String pics []  = pic.split(",");
					if(pics != null && pics.length > 0){
						for (int i = 0; i < pics.length; i++) {
							ResPicResult resPicResult = new ResPicResult();
							ResPic respic=null;
							if(Utils.enableRedisCache()){
								respic = ResourceCacher.getPic(Integer.parseInt(pics[i]));
							}else{
								respic = resPicService.get(Integer.parseInt(pics[i]));
							}
							
							if(respic!=null){
								resPicResult.setPicId(respic.getId());
								resPicResult.setPicPath(respic.getPicPath());
							}
							designerWorksUserDb.getPicList().add(resPicResult);
						}
						
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorksUser>(false,designerWorksUser.getMsgId(),"数据异常!");
		}
		return new ResponseEnvelope<DesignerWorksUser>(designerWorksUserDb,designerWorksUser.getMsgId(),true);
	}
	
	/**
	 * 保存 设计师作品
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("designerWorks") DesignerWorks designerWorks
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		LoginUser loginUser = new LoginUser();
		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		 }else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		    designerWorks.setUserId(loginUser.getId());
		 }
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   designerWorks = (DesignerWorks)JsonUtil.getJsonToBean(jsonStr, DesignerWorks.class);
		   if(designerWorks == null){
			  return new ResponseEnvelope<DesignerWorks>(false, "传参异常!","none");
		   }
		}
		
		try {
			String pTime = request.getParameter("projectTime") == "" ? "" : request.getParameter("projectTime");
			designerWorks.setProjectTime(pTime);
		    sysSave(designerWorks,request);
			if (designerWorks.getId() == null) {
				int id = designerWorksService.add(designerWorks);
				logger.info("add:id=" + id);
				designerWorks.setId(id);
			} else {
				int id = designerWorksService.update(designerWorks);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String designerWorksJson = JsonUtil.getBeanToJsonData(designerWorks);
				 DesignerWorksSmall designerWorksSmall= new JsonDataServiceImpl<DesignerWorksSmall>().getJsonToBean(designerWorksJson, DesignerWorksSmall.class);
				 
				 return new ResponseEnvelope<DesignerWorksSmall>(designerWorksSmall,designerWorks.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorks>(false, "数据异常!",designerWorks.getMsgId());
		}
		return new ResponseEnvelope<DesignerWorks>(designerWorks,designerWorks.getMsgId(),true);
	}
	
	/**
	 *新增设计师作品---接口
	 */
	@RequestMapping(value = "/saveDesignerWorks")
	@ResponseBody
	public Object saveDesignerWorks(
			@PathVariable String style,@ModelAttribute("designerWorks") DesignerWorks designerWorks
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		LoginUser loginUser = new LoginUser();
		 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		 }else{
		    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		    designerWorks.setUserId(loginUser.getId());
		 }
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   designerWorks = (DesignerWorks)JsonUtil.getJsonToBean(jsonStr, DesignerWorks.class);
		   if(designerWorks == null){
			  return new ResponseEnvelope<DesignerWorks>(false, "传参异常!","none");
		   }
		}
		String msg = "";
		if( StringUtils.isBlank(designerWorks.getMsgId()) ){
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignerWorks>(false,msg,designerWorks.getMsgId());
		}
		try {
			String pTime = request.getParameter("projectTime") == "" ? "" : request.getParameter("projectTime");
			designerWorks.setProjectTime(pTime);
		    sysSave(designerWorks,request);
			if (designerWorks.getId() == null) {
				int id = designerWorksService.add(designerWorks); 
				logger.info("add:id=" + id);
				designerWorks.setId(id);
			} else {
				int id = designerWorksService.update(designerWorks);
				DesignerWorksCacher.removeDesignerWork(designerWorks);/**清除缓存*/
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String designerWorksJson = JsonUtil.getBeanToJsonData(designerWorks);
				 DesignerWorksSmall designerWorksSmall= new JsonDataServiceImpl<DesignerWorksSmall>().getJsonToBean(designerWorksJson, DesignerWorksSmall.class);
				 
				 return new ResponseEnvelope<DesignerWorksSmall>(designerWorksSmall,designerWorks.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorks>(false, "数据异常!",designerWorks.getMsgId());
		}
		return new ResponseEnvelope<DesignerWorks>(designerWorks,designerWorks.getMsgId(),true);
	}
	
	@RequestMapping(value = "/saveCallBack")
	@ResponseBody
	public Object saveCallBack(
			@PathVariable String style,@ModelAttribute("designerWorks") DesignerWorks designerWorks
			,HttpServletRequest request, HttpServletResponse response
			,@RequestParam(value = "filePath", required = false) MultipartFile filePath
			) throws Exception{
		
		LoginUser loginUser = new LoginUser();
		if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
			loginUser.setLoginName("nologin");
		}else{
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			designerWorks.setUserId(loginUser.getId());
		}
		ResPic resPic = new ResPic();
		Integer newid = null;	
	    String  imgPaths = "";
	    String pic = null;
	    String picids = null;
		int res_id = -1; 
		if(filePath != null && filePath.getSize() > 0){
			response.setContentType("text/html;charset=utf-8");
			//获取文件列表并将物理文件保存到服务器中
		    //filePath设置到model对象中，由model存入数据库中
			Map map = new HashMap();
			//配置文件中的key定义
			map.put(FileUploadUtils.UPLOADPATHTKEY, UPLOAD_PATH);
			//文件
			map.put(FileUploadUtils.FILE, filePath);
			//返回的需要存储在数据库中的path
			map.put(FileUploadUtils.DB_FILE_PATH, "");
			
			boolean flag = false;
			String dbFilePath = null;
			flag = FileUploadUtils.saveFile(map);
			if (flag) {
				dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
				resPic.setPicPath(dbFilePath);
				sysSave(resPic,request);
				resPic.setPicCode(resPic.getSysCode());
				String picName=resPic.getPicPath().substring(resPic.getPicPath().lastIndexOf("/")+1,resPic.getPicPath().lastIndexOf("."));
				resPic.setPicName(picName);
				resPic.setPicFileName(picName);
				resPic.setPicType("用户设计师作品");
				resPic.setPicSize((int)(filePath.getSize()));
				String picSuffix=resPic.getPicPath().substring(resPic.getPicPath().lastIndexOf("."),resPic.getPicPath().length());
				resPic.setPicSuffix(picSuffix);
				resPic.setPicFormat(picSuffix.substring(1,picSuffix.length()));
				resPic.setFileKey("system.sysUser.designerWorks.piclist");
				res_id = resPicService.add(resPic);
		    }
			if(res_id < 0){
				return "<script>window.parent.saveCallBackFailed('数据上传失败!');</script>";
			}
			pic = new Integer(res_id).toString();
			
		}
		
		try {
		    sysSave(designerWorks,request);
			if (designerWorks.getId() == null ) {
				designerWorks.setPicId(pic);
				designerWorks.setPicIds(pic);
				newid = designerWorksService.add(designerWorks);
				logger.info("add:id=" + newid);
				designerWorks.setId(newid);
				picids = pic;
			} else {
				DesignerWorks designerWorksdb =  designerWorksService.get(designerWorks.getId());
				String pics = designerWorksdb.getPicIds();
				if(pic != null && !"".equals(pic) && pics != null && !"".equals(pics)){
					picids = pics+","+ pic;
				}else{
					picids = pic;
					designerWorks.setPicId(picids);
				}
				designerWorks.setPicIds(picids);
				int id = designerWorksService.update(designerWorks);
				newid = designerWorks.getId();
				logger.info("update:id=" + newid);
			}
			if(resPic.getId()!=null){
				resPic.setBusinessId(designerWorks.getId());
				resPicService.update(resPic);
				/*生成缩略图*/
				resPicService.createThumbnail(resPic, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "<script>window.parent.saveCallBackFailed('数据异常!');</script>";
		}
		
		//通过主键查询列表id
		//通过资源表获取理解path并且加上http路径app.resources.url，组装成图片字符串imgPaths="http../xxx.png,http.../xxxx.png"
		DesignerWorksUser designerWorksUser = new DesignerWorksUser();
		
		//designerWorks = designerWorksService.get(newid);
		String str [] =null;
		String str1 = designerWorks.getPicIds();
		String basePath = app.getString("app.resources.url");
		ResPic respic =new ResPic();
		if(!"".equals(str1) && str1 != null && str1.contains(",")){
			str = str1.split(",");
			for (int i = 0; i < str.length; i++) {
				respic.setId(Integer.parseInt(str[i]));
				respic  = resPicService.get(respic.getId());
				imgPaths += basePath + respic.getPicPath()+",";
			}
		}else if (!"".equals(str1) && str1 != null){
			respic.setId(Integer.parseInt(str1));
			respic  = resPicService.get(respic.getId());
			imgPaths += basePath + respic.getPicPath();
		}
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		if(type == null || "".equals(type)){
			return "<script>window.parent.savedSuccessfully("+newid+",'"+picids+"','"+imgPaths+"');</script>";
		}else{
			return "<script>window.parent.saveCallBackSuccessed("+newid+",'"+picids+"','"+imgPaths+"');</script>";
		}
	}
	
	/**
	 * 删除设计师作品,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("designerWorks") DesignerWorks designerWorks
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			designerWorks = (DesignerWorks)JsonUtil.getJsonToBean(jsonStr, DesignerWorks.class);
			if(designerWorks == null){
			   return new ResponseEnvelope<DesignerWorks>(false, "传参异常!","none");
			}
			ids =  designerWorks.getIds();
			msgId = designerWorks.getMsgId() ;
		}else{
			ids =  designerWorks.getIds();
			msgId = designerWorks.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<DesignerWorks>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = designerWorksService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = designerWorksService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<DesignerWorks>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignerWorks>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<DesignerWorks>(true,msgId,true);
	}
	
//	/**
//	 * 检验上传图片的数量
//	 */
//	@RequestMapping(value = "/uploadImgCount")
//	public void uploadImgCount(@ModelAttribute("designerWorks") DesignerWorks designerWorks,String imgPath,HttpServletRequest request, HttpServletResponse response) throws IOException {
//		
//		String flag = "false";
//	
//		//String  imgTypeStr= imgPath.substring(imgPath.lastIndexOf("."));
//		String [] imgTypeStr = imgPath.split(",");
//		
//		if(imgTypeStr.length > 9 && imgTypeStr.length > 0 ){
//			flag = "false";
//		}else if(imgTypeStr.length > 0 && imgTypeStr.length < 10){
//			flag = "true";
//		}
//		
//		
//		response.getWriter().write(flag);
//		
//	}	
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignerWorks model,HttpServletRequest request){
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
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResPic model,HttpServletRequest request){
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
