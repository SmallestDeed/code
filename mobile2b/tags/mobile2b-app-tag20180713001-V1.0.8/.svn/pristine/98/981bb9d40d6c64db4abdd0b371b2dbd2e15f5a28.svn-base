package com.nork.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.nork.demo.model.Exp;
import com.nork.demo.model.search.ExpSearch;
import com.nork.demo.model.small.ExpSmall;
import com.nork.demo.service.ExpService;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResPicService;

/**   
 * @Title: ExpController.java 
 * @Package com.nork.demo.controller
 * @Description:演示模块-参考例子Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-05-17 20:11:49
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/demo/exp")
public class ExpController {
	private static Logger logger = Logger
			.getLogger(ExpController.class);
	private final JsonDataServiceImpl<Exp> JsonUtil = new JsonDataServiceImpl<Exp>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/demo/exp";
	private final String BASEMAIN = "/"+ STYLE + "/demo/base/exp";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/demo/exp";
	private final String UPLOAD_PATH = "demo.exp.att1.upload.path";
	
	@Autowired
	private ExpService expService;
	
	@Autowired
	private ResPicService resPicService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	@RequestMapping("/exp")
	public String exp(){
		return JSPMAIN+"/exp";
	}
	
	/**
	 *    参考例子 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    参考例子 主页面
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
		return JSPMAIN+"/exp_list";
	}
	
	/**
	 * 保存 参考例子
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("exp") Exp exp
			,String resIds
			,HttpServletRequest request, HttpServletResponse response
			,@RequestParam(value = "filePath", required = false) MultipartFile filePath
			) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   exp = (Exp)JsonUtil.getJsonToBean(jsonStr, Exp.class);
		   if(exp == null){
			  return new ResponseEnvelope<Exp>(false, "传参异常!","none");
		   }
		}
		

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
				ResPic resPic = new ResPic();
				dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
				resPic.setPicPath(dbFilePath);
				sysSave(resPic,request);
				res_id = resPicService.add(resPic);
			}
		}else{
			res_id = 0;
		}
		
		if(res_id < 0){
			return new ResponseEnvelope<Exp>(false, "数据异常!",exp.getMsgId());
		}
	
		try {
		    sysSave(exp,request);
			if (exp.getId() == null) {
				int id = expService.add(exp);
				logger.info("add:id=" + id);
				exp.setId(id);
			} else {
				int id = expService.update(exp);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String expJson = JsonUtil.getBeanToJsonData(exp);
				 ExpSmall expSmall= new JsonDataServiceImpl<ExpSmall>().getJsonToBean(expJson, ExpSmall.class);
				 
				 return new ResponseEnvelope<ExpSmall>(expSmall,exp.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "数据异常!",exp.getMsgId());
		}
		return new ResponseEnvelope<Exp>(exp,exp.getMsgId(),true);
	}
	
	/**
	 * 保存 参考例子
	 */
	@RequestMapping(value = "/saveCallBack")
	@ResponseBody
	public Object saveCallBack(
			@PathVariable String style,@ModelAttribute("exp") Exp exp
			,HttpServletRequest request, HttpServletResponse response
			,@RequestParam(value = "filePath", required = false) MultipartFile filePath
			) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   exp = (Exp)JsonUtil.getJsonToBean(jsonStr, Exp.class);
		   if(exp == null){
			   return "<script>window.parent.saveCallBackFailed('数据传参异常!');</script>";
		   }
		}
		
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
				ResPic resPic = new ResPic();
				dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
				resPic.setPicPath(dbFilePath);
				sysSave(resPic,request);
				res_id = resPicService.add(resPic);
				
		    }
		}

		if(res_id < 0){
			return "<script>window.parent.saveCallBackFailed('数据上传失败!');</script>";
		}
		
		exp.setAtt1(new Integer(res_id).toString());
		
		try {
		    sysSave(exp,request);
			if (exp.getId() == null) {
				int id = expService.add(exp);
				logger.info("add:id=" + id);
				exp.setId(id);
			} else {
				int id = expService.update(exp);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String expJson = JsonUtil.getBeanToJsonData(exp);
				 ExpSmall expSmall= new JsonDataServiceImpl<ExpSmall>().getJsonToBean(expJson, ExpSmall.class);
				 
				 return "<script>window.parent.saveCallBackSuccessed('true');</script>";
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return "<script>window.parent.saveCallBackFailed('数据异常!');</script>";
		}
		return "<script>window.parent.saveCallBackSuccessed('true');</script>";
	}
	
	/**
	 *获取 参考例子详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("exp") Exp exp
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			exp = (Exp)JsonUtil.getJsonToBean(jsonStr, Exp.class);
			if(exp == null){
			   return new ResponseEnvelope<Exp>(false,"none", "传参异常!");
			}
			id =  exp.getId();
			msgId = exp.getMsgId() ;
		}else{
		    id =  exp.getId();
		    msgId = exp.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<Exp>(false, "参数缺少id!",msgId);
		}
		
		try {
			exp = expService.get(id);
			
			if("small".equals(style) && exp != null ){
				 String expJson = JsonUtil.getBeanToJsonData(exp);
				 ExpSmall expSmall= new JsonDataServiceImpl<ExpSmall>().getJsonToBean(expJson, ExpSmall.class);
				 
				 return new ResponseEnvelope<ExpSmall>(expSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<Exp>(exp,msgId,true);
	}
	
   /**
	 * 删除参考例子,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("exp") Exp exp
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			exp = (Exp)JsonUtil.getJsonToBean(jsonStr, Exp.class);
			if(exp == null){
			   return new ResponseEnvelope<Exp>(false, "传参异常!","none");
			}
			ids =  exp.getIds();
			msgId = exp.getMsgId() ;
		}else{
			ids =  exp.getIds();
			msgId = exp.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<Exp>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = expService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = expService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<Exp>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<Exp>(true,msgId,true);
	}
	
	/**
	 * 参考例子列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("expSearch") ExpSearch expSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
		if( expSearch == null ) {
			expSearch.setLimit(new Integer(20));
		}
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			expSearch = (ExpSearch)JsonUtil.getJsonToBean(jsonStr, ExpSearch.class);
			if(expSearch == null){
			   return new ResponseEnvelope<Exp>(false, "传参异常!","none");
			}
		}
		
		List<Exp> list = new ArrayList<Exp> ();
		int total = 0;
		try {
			total = expService.getCount(expSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = expService.getPaginatedList(
						expSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String expJsonList = JsonUtil.getListToJsonData(list);
				 List<ExpSmall> smallList= new JsonDataServiceImpl<ExpSmall>().getJsonToBeanList(expJsonList, ExpSmall.class);
				 return new ResponseEnvelope<ExpSmall>(total,smallList,expSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "数据异常!",expSearch.getMsgId());
		}
		return new ResponseEnvelope<Exp>(total, list,expSearch.getMsgId());
	}
	

   /**
	 * 参考例子全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("expSearch") ExpSearch expSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			expSearch = (ExpSearch)JsonUtil.getJsonToBean(jsonStr, ExpSearch.class);
			if(expSearch == null){
			   return new ResponseEnvelope<Exp>(false, "传参异常!","none");
			}
		}
	
    	List<Exp> list = new ArrayList<Exp> ();
		int total = 0;
		try {
			total = expService.getCount(expSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = expService.getList(expSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String expJsonList = JsonUtil.getListToJsonData(list);
				 List<ExpSmall> smallList= new JsonDataServiceImpl<ExpSmall>().getJsonToBeanList(expJsonList, ExpSmall.class);
				 return new ResponseEnvelope<ExpSmall>(total,smallList,expSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "数据异常!",expSearch.getMsgId());
		}
		return new ResponseEnvelope<Exp>(total, list,expSearch.getMsgId());
	}
	
   /**
	 *获取 参考例子详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		Exp exp = null;
		try {
			exp = expService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "数据异常!");
		}
		ResponseEnvelope<Exp> res = new ResponseEnvelope<Exp>(exp);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/exp_edit";
		}else{
			url = JSPMAIN + "/exp_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 参考例子列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("expSearch") ExpSearch expSearch,HttpServletRequest request, HttpServletResponse response) {

		List<Exp> list = new ArrayList<Exp> ();
		int total = 0;
		try {
			total = expService.getCount(expSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = expService.getPaginatedList(
						expSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<Exp>(false, "数据异常!");
		}
		
		ResponseEnvelope<Exp> res = new ResponseEnvelope<Exp>(total,list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", expSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/exp_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(Exp model,HttpServletRequest request){
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
