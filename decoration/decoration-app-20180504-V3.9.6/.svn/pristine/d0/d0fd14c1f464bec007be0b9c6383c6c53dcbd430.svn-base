package com.nork.productprops.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
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
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.search.ProductPropsSearch;
import com.nork.productprops.model.small.ProductPropsSmall;
import com.nork.productprops.service.ProductPropsService;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResPicService;

/**   
 * @Title: ProductPropsController.java 
 * @Package com.nork.productprops.controller
 * @Description:产品属性-产品属性表Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 10:40:03
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/productprops/productProps")
public class ProductPropsController {
	private static Logger logger = Logger
			.getLogger(ProductPropsController.class);
	private final JsonDataServiceImpl<ProductProps> JsonUtil = new JsonDataServiceImpl<ProductProps>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/productprops/productProps";
	private final String BASEMAIN = "/"+ STYLE + "/productprops/base/productProps";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/productprops/productProps";

	private final String PIC_UPLOAD_PATH = "product.productProps.pic.upload.path";
	
	@Autowired
	private ProductPropsService productPropsService;

	@Autowired
	private ResPicService resPicService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
    
	/**
	 *    产品属性表 基础主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}
	
	/**
	 *    产品属性表 主页面
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
		return JSPMAIN+"/main";
	}
	
	/**
	 * 保存 产品属性表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(
			@PathVariable String style,@ModelAttribute("productProps") ProductProps productProps,
			@RequestParam(value = "picFilePath", required = false) MultipartFile picFilePath,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
		   productProps = (ProductProps)JsonUtil.getJsonToBean(jsonStr, ProductProps.class);
		   if(productProps == null){
			  return new ResponseEnvelope<ProductProps>(false, "传参异常!","none");
		   }
		}

		ResPic resPic = new ResPic();
		int res_id = -1;
		if(picFilePath != null && picFilePath.getSize() > 0){
			response.setContentType("text/html;charset=utf-8");
			//获取文件列表并将物理文件保存到服务器中

			//filePath设置到model对象中，由model存入数据库中
			Map<String, Object> map = new HashMap<String, Object>();
			//配置文件中的key定义
			map.put(FileUploadUtils.UPLOADPATHTKEY, PIC_UPLOAD_PATH);
			//文件
			map.put(FileUploadUtils.FILE, picFilePath);
			//返回的需要存储在数据库中的path
			map.put(FileUploadUtils.DB_FILE_PATH, "");

			boolean flag = false;
			String dbFilePath = null;
			flag = FileUploadUtils.saveFile(map);
			if (flag) {
				String originalFilename = picFilePath.getOriginalFilename();
				//获取文件后缀
				String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
				//获取物理文件名称
				String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
				dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
				resPic.setPicName(filename);
				resPic.setPicPath(dbFilePath);
				resPic.setFileKey("design.designTemplet.pic");
				resPic.setPicName(filename);//业务名称
				resPic.setPicFileName(filename);//物理文件名称
				resPic.setPicSize(new Long(picFilePath.getSize()).intValue());
				resPic.setPicSuffix(suffix);
				resPic.setPicType("样板图");
				try{
					String realPath =(String) map.get(FileUploadUtils.SERVER_FILE_PATH);
					BufferedImage bufferedImage = ImageIO.read(new File(realPath));
					int width = bufferedImage.getWidth();  //图片宽度
					int height = bufferedImage.getHeight();//图片高度
					String format = suffix.replace(".", "");
					resPic.setPicWeight(new Integer(width).toString());
					resPic.setPicHigh(new Integer(height).toString());
					resPic.setPicFormat(format);//图片格式
				}catch(Exception e){
					e.printStackTrace();
				}
				resPic.setPicLevel("0");//图片级别
				resPic.setPicOrdering("0");//图片排序
				sysSave(resPic,request);
				res_id = resPicService.add(resPic);

			}
			if(res_id < 0){
				return "<script>window.parent.saveCallBackFailed('数据上传失败!');</script>";
			}
			productProps.setPicPath(new Integer(res_id));

		}else {
			if(productProps.getPicPath()==null){
				productProps.setPicPath(0);
			}
		}

		try {
		    sysSave(productProps, request);
			if (productProps.getId() == null) {
				ProductProps parentProductProps = productPropsService.get(productProps.getPid());
				productProps.setLevel(productProps.getLevel()+1);
				productProps.setIsLeaf(0);
				productProps.setLongCode(parentProductProps.getLongCode()+productProps.getCode()+".");
				int id = productPropsService.add(productProps);
				//设置父级isLeaf为1
				parentProductProps.setIsLeaf(1);
				productPropsService.update(parentProductProps);
				logger.info("add:id=" + id);
				productProps.setId(id);
			} else {
				String longCode = productProps.getLongCode();
				longCode = longCode.substring(0,longCode.length()-1);
				productProps.setLongCode(longCode.substring(0,longCode.lastIndexOf(".")+1)+productProps.getCode()+".");
				int id = productPropsService.update(productProps);
				logger.info("update:id=" + id);
			}
			
			if("small".equals(style)){
				 String productPropsJson = JsonUtil.getBeanToJsonData(productProps);
				 ProductPropsSmall productPropsSmall= new JsonDataServiceImpl<ProductPropsSmall>().getJsonToBean(productPropsJson, ProductPropsSmall.class);
				 
				 return new ResponseEnvelope<ProductPropsSmall>(productPropsSmall,productProps.getMsgId(),true);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!",productProps.getMsgId());
		}
		return "<script>window.parent.saveCallBackSuccessed('true');</script>";
	}
	
	/**
	 *获取 产品属性表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,@ModelAttribute("productProps") ProductProps productProps
	                  ,HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";	
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productProps = (ProductProps)JsonUtil.getJsonToBean(jsonStr, ProductProps.class);
			if(productProps == null){
			   return new ResponseEnvelope<ProductProps>(false,"none", "传参异常!");
			}
			id =  productProps.getId();
			msgId = productProps.getMsgId() ;
		}else{
		    id =  productProps.getId();
		    msgId = productProps.getMsgId() ;
		}
		
		if(id == null ){
		   return new ResponseEnvelope<ProductProps>(false, "参数缺少id!",msgId);
		}
		
		try {
			productProps = productPropsService.get(id);
			
			if("small".equals(style) && productProps != null ){
				 String productPropsJson = JsonUtil.getBeanToJsonData(productProps);
				 ProductPropsSmall productPropsSmall= new JsonDataServiceImpl<ProductPropsSmall>().getJsonToBean(productPropsJson, ProductPropsSmall.class);
				 
				 return new ResponseEnvelope<ProductPropsSmall>(productPropsSmall,msgId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!",msgId);
		}
		return new ResponseEnvelope<ProductProps>(productProps,msgId,true);
	}
	
   /**
	 * 删除产品属性表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,@ModelAttribute("productProps") ProductProps productProps
	                 ,HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";	
		String ids   = "";
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productProps = (ProductProps)JsonUtil.getJsonToBean(jsonStr, ProductProps.class);
			if(productProps == null){
			   return new ResponseEnvelope<ProductProps>(false, "传参异常!","none");
			}
			ids =  productProps.getIds();
			msgId = productProps.getMsgId() ;
		}else{
			ids =  productProps.getIds();
			msgId = productProps.getMsgId() ;
		}
		
		if (ids == null) {
		    return new ResponseEnvelope<ProductProps>(false, "参数ids不能为空!",msgId);
		}
		int i = 0;
		try{
			if (ids != null ) {
			   if(ids.contains(",")){
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = productPropsService.delete(id);
						logger.info("delete:id=" + id);
					}
			   }else{
					Integer id = new Integer(ids);
					i = productPropsService.delete(id);
					logger.info("delete:id=" + id);
			    }
			}
			
			if( i == 0){
				return new ResponseEnvelope<ProductProps>(false, "记录不存在!",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "删除失败!",msgId);
		}
		return new ResponseEnvelope<ProductProps>(true,msgId,true);
	}
	
	/**
	 * 产品属性表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(
	            @PathVariable String style,@ModelAttribute("productPropsSearch") ProductPropsSearch productPropsSearch
				,HttpServletRequest request, HttpServletResponse response) {
 		//每页不同页码时使用
		if( productPropsSearch.getLimit() == null ) {
			productPropsSearch.setLimit(new Integer(20));
		}
		
 		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productPropsSearch = (ProductPropsSearch)JsonUtil.getJsonToBean(jsonStr, ProductPropsSearch.class);
			if(productPropsSearch == null){
			   return new ResponseEnvelope<ProductProps>(false, "传参异常!","none");
			}
		}
		
		List<ProductProps> list = new ArrayList<ProductProps> ();
		int total = 0;
		try {
			total = productPropsService.getCount(productPropsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = productPropsService.getPaginatedList(
						productPropsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String productPropsJsonList = JsonUtil.getListToJsonData(list);
				 List<ProductPropsSmall> smallList= new JsonDataServiceImpl<ProductPropsSmall>().getJsonToBeanList(productPropsJsonList, ProductPropsSmall.class);
				 return new ResponseEnvelope<ProductPropsSmall>(total,smallList,productPropsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!",productPropsSearch.getMsgId());
		}
		return new ResponseEnvelope<ProductProps>(total, list,productPropsSearch.getMsgId());
	}
	

   /**
	 * 产品属性表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(
	            @PathVariable String style,@ModelAttribute("productPropsSearch") ProductPropsSearch productPropsSearch
				,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0){
			productPropsSearch = (ProductPropsSearch)JsonUtil.getJsonToBean(jsonStr, ProductPropsSearch.class);
			if(productPropsSearch == null){
			   return new ResponseEnvelope<ProductProps>(false, "传参异常!","none");
			}
		}
	
    	List<ProductProps> list = new ArrayList<ProductProps> ();
		int total = 0;
		try {
			total = productPropsService.getCount(productPropsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = productPropsService.getList(productPropsSearch);
			}
			
			 if("small".equals(style) && list != null && list.size() > 0){
				 String productPropsJsonList = JsonUtil.getListToJsonData(list);
				 List<ProductPropsSmall> smallList= new JsonDataServiceImpl<ProductPropsSmall>().getJsonToBeanList(productPropsJsonList, ProductPropsSmall.class);
				 return new ResponseEnvelope<ProductPropsSmall>(total,smallList,productPropsSearch.getMsgId());
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!",productPropsSearch.getMsgId());
		}
		return new ResponseEnvelope<ProductProps>(total, list,productPropsSearch.getMsgId());
	}
	
   /**
	 *获取 产品属性表详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id,HttpServletRequest request, HttpServletResponse response) {
		ProductProps productProps = null;
		try {
			productProps = productPropsService.get(id);
			productProps.setParentProps(productPropsService.get(productProps.getPid()));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!");
		}
		ResponseEnvelope<ProductProps> res = new ResponseEnvelope<ProductProps>(productProps);
		request.setAttribute("res", res);
		
		String url ="";
		String type = (String)request.getParameter("pageType");
		if("edit".equals(type)){
			url = JSPMAIN + "/productProps_edit";
		}else{
			url = JSPMAIN + "/productProps_view";
		}
		return  Utils.getPageUrl(request, url);
	}
	
   /**
	 * 产品属性表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
	            @ModelAttribute("productPropsSearch") ProductPropsSearch productPropsSearch,HttpServletRequest request, HttpServletResponse response) {

		List<ProductProps> list = new ArrayList<ProductProps> ();
		int total = 0;
		try {
			total = productPropsService.getCount(productPropsSearch);
            logger.info("total:" + total);
            
			if (total > 0) {
				list = productPropsService.getPaginatedList(
						productPropsSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!");
		}
		
		ResponseEnvelope<ProductProps> res = new ResponseEnvelope<ProductProps>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", productPropsSearch);
		 
		return  Utils.getPageUrl(request, JSPMAIN + "/productProps_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ProductProps model,HttpServletRequest request){
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
	 * 组装产品属性树形结构数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/productProps")
	public void productProps(HttpServletRequest request, HttpServletResponse response){
		try{
			String id = request.getParameter("id");
			if(StringUtils.isBlank(id) ){
				id = "0";
			}
			List<String> lstTree = new ArrayList<>();
			List<ProductProps> productPropsList = productPropsService.asyncLoadTree(Integer.valueOf(id));
			if( productPropsList != null && productPropsList.size() > 0 ){
				for( ProductProps productProp : productPropsList ){
					String isParent = "false";
					if( productProp.getIsLeaf() == 1 ){
						isParent = "true";
					}
					String str = "{id:"+productProp.getId()+", pId:"+productProp.getPid()+", name:\""+productProp.getName().trim()+"\", isParent:"+isParent+"}";
					lstTree.add(str.toString());
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(lstTree);
			response.getWriter().print(jsonArray.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据分类属性查询下级列表
	 * @param productPropsSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rightProductProps")
	public Object rightProductProps(
			@ModelAttribute("productPropsSearch") ProductPropsSearch productPropsSearch,HttpServletRequest request, HttpServletResponse response){
		List<ProductProps> list = new ArrayList<ProductProps> ();
		ProductProps productProps = productPropsService.get(productPropsSearch.getPid());
		int total = 0;
		try {
			total = productPropsService.getCount(productPropsSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = productPropsService.getPaginatedList(
						productPropsSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!");
		}

		ResponseEnvelope<ProductProps> res = new ResponseEnvelope<ProductProps>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", productPropsSearch);
		request.setAttribute("productProps", productProps);

		return  Utils.getPageUrl(request, JSPMAIN + "/productProps_right");
	}

	/**
	 * 查询属性下的属性值列表
	 * @param productPropsSearch
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rightProductPropVals")
	public Object rightProductPropVals(
			@ModelAttribute("productPropsSearch") ProductPropsSearch productPropsSearch,HttpServletRequest request, HttpServletResponse response){
		List<ProductProps> list = new ArrayList<ProductProps> ();
		ProductProps productProps = productPropsService.get(productPropsSearch.getPid());
		int total = 0;
		try {
			total = productPropsService.getCount(productPropsSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = productPropsService.getPaginatedPropValsList(
						productPropsSearch);
				for( ProductProps props : list ){
					ResPic resPic = resPicService.get(props.getPicPath());
					if( resPic != null ){
						props.setPicPathVal(resPic.getPicPath());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ProductProps>(false, "数据异常!");
		}

		ResponseEnvelope<ProductProps> res = new ResponseEnvelope<ProductProps>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", productPropsSearch);
		request.setAttribute("productProps", productProps);

		return  Utils.getPageUrl(request, JSPMAIN + "/productProps_right");
	}

	/**
	 * 验证编码是否重复
	 * @param productPropsSearch
	 * @param opType
	 * @param response
	 */
	@RequestMapping(value = "/checkCode")
	@ResponseBody
	public void checkCode(@ModelAttribute("productPropsSearch")ProductPropsSearch productPropsSearch,String opType,HttpServletResponse response){
		try{
			PrintWriter out = response.getWriter();
			String msg = "false";
			int count = productPropsService.getCount(productPropsSearch);
			if( count == 0 ){
				msg = "true";
			}else if( count == 1 && "edit".equals(opType) ){
				ProductProps productProps = productPropsService.get(productPropsSearch.getId());
				if( productProps.getCode().equals(productPropsSearch.getCode()) ){
					msg = "true";
				}else{
					msg = "false";
				}
			}else{
				msg = "false";
			}
			out.print(msg);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Object add(HttpServletRequest request){
		String id = request.getParameter("id");
		String level = request.getParameter("level");
		if( StringUtils.isNotBlank(id) ){
			ProductProps productProps = productPropsService.get(Integer.valueOf(id));
			request.setAttribute("productProps",productProps);
		}
		if( StringUtils.isNotBlank(level) ) {
			if ( Integer.valueOf(level) < 3 ) {
				return Utils.getPageUrl(request, JSPMAIN + "/productProps_add");
			} else {
				return Utils.getPageUrl(request, JSPMAIN + "/productProps_addPropVal");
			}
		}
		return null;
	}

	/**
	 * ResPic自动存储系统字段
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

	/**
	 * 组装类目树
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productPropsTree")
	public Object productPropsTree(HttpServletRequest request, HttpServletResponse response){
		try {
			//得到所有类目
			List<ProductProps> productPropses = productPropsService.getList(new ProductProps());
			if( productPropses == null ){
				return null;
			}

			List<String> list = new ArrayList<String>();
			for( ProductProps productProps : productPropses ){
				String flag = "false";
				if( productProps.getLevel() == 0 || productProps.getLevel() == 1 ){
					flag = "true";
				}
				String jsonStr = "{id:" + productProps.getId() +",name:\""+  productProps.getName() +"\",pId:"+ productProps.getPid() +",open:"+flag+"}";
				list.add(jsonStr);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ztree", list);
			JSONObject json = JSONObject.fromObject(map);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 产品批量录入左菜单列表
	 */
	@RequestMapping(value = "/leftProductProps") 
	public String leftProductProps(String productTypeCode,
				HttpServletRequest request, HttpServletResponse response) throws Exception  {
			request.setAttribute("productPropsCode", productTypeCode);
		    return  Utils.getPageUrl(request, "/jsp/product/productAttribute/proAttribute_left");
		 
	}
	
	/**
	 * 根据产品分类编码查询出子节点
	 * @param productProsCode
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/productProsList") 
	public void productProsList(String productPropsCode,HttpServletRequest request, HttpServletResponse response) throws Exception  {
		 
    	List<ProductProps> list = new ArrayList<ProductProps> ();
    	List<String> lstTree = new ArrayList<String>();
    	ProductProps productProps = new ProductProps();
    	productProps.setLongCode("."+productPropsCode+".");
		try {
//			String id = request.getParameter("id");
//			if( StringUtils.isBlank(id) ){
//				id = "0";
//			}
			lstTree.add("{id:1,name:\"NORK产品属性根节点\",pId:0,nocheck:true,open:true}");
			list = productPropsService.getList(productProps);
			for(ProductProps props : list){
				String isNocheck = "true";
				if( props.getLevel() == 4 ){
					isNocheck = "false";
				}
				String str = "{id:"+props.getId()+", pId:"+props.getPid()+", name:\""+props.getName().trim()+"\",open:true,nocheck:"+isNocheck+"}";
				lstTree.add(str.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = JSONArray.fromObject(lstTree);
		response.getWriter().print(jsonArray.toString());
	}
	
	 
	/**
	 * 产品批量 组合生成
	 * @param colorIds
	 * @param materialIds
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchGenerate") 
	public String batchGenerate(String colorIds,String materialIds,
				HttpServletRequest request, HttpServletResponse response) throws Exception  {
		List<ProductProps> list = new ArrayList<ProductProps>();
		
		String colorArr[] = {};
		String materialArr[] = {};
		if(StringUtils.isNotBlank(colorIds)){
			if(colorIds.contains(",")){
				colorArr = colorIds.split(",");
			}
		}
		if(StringUtils.isNotBlank(materialIds)){
			if(materialIds.contains(",")){
				materialArr = materialIds.split(",");
			}
		}
		for(int i=0;i<colorArr.length;i++){
			ProductProps productProps = new ProductProps();
			ProductProps colorProps = productPropsService.get(Utils.getIntValue(colorArr[i]));
			for(int j=0;j<materialArr.length;j++){
				ProductProps materialProps = productPropsService.get(Utils.getIntValue(materialArr[j]));
				productProps.setColorName(colorProps.getName());
				productProps.setMaterialName(materialProps.getName());
				list.add(productProps);
			}
		}
		request.setAttribute("list", list);
		return  Utils.getPageUrl(request, "/jsp/product/productAttribute/proAttribute_batchAdd");
		 
	}
}
