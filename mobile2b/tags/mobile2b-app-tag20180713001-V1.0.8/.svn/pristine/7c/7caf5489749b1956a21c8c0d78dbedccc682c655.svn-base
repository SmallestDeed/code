package com.nork.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.business.model.HouseSpace;
import com.nork.business.service.HouseSpaceService;
import com.nork.common.constant.PicType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.home.model.BaseHouse;
import com.nork.home.service.BaseHouseService;
import com.nork.resource.model.ResPicHouse;
import com.nork.resource.model.search.ResPicHouseSearch;
import com.nork.resource.service.ResPicHouseService;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResPicService;

/**   
 * @Title: ResPicController.java 
 * @Package com.nork.system.controller
 * @Description:系统-图片资源库Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/system/resPicHouse")
public class ResPicHouseController {
	private static Logger logger = Logger.getLogger(ResPicHouseController.class);
	private final JsonDataServiceImpl<ResPicHouse> JsonUtil = new JsonDataServiceImpl<ResPicHouse>();
	private final String STYLE="jsp";		
	private final String JSPSTYLE="jsp";
	private final String MAIN = "/"+ STYLE + "/system/resPicHouse";
	private final String BASEMAIN = "/"+ STYLE + "/system/base/resPicHouse";
	private final String JSPMAIN = "/"+STYLE+"/system/resPicHouse";
	
	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	@Autowired
	private ResPicHouseService resPicHouseService;
	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private HouseSpaceService houseSpaceService;
	@Autowired
	private ResPicService resPicService;
	
	
	   /**
		 * 图片资源库列表(需整理的房型图片信息)
		 */
		@RequestMapping(value = "/jsplist")
		public Object jsplist(@ModelAttribute("resPicHouseSearch") ResPicHouseSearch resPicHouseSearch,HttpServletRequest request, HttpServletResponse response) {

			List<ResPicHouse> list = new ArrayList<ResPicHouse> ();
			int total = 0;
			try {
				total = resPicHouseService.getCount(resPicHouseSearch);
	            logger.info("total:" + total);
	            
				if (total > 0) {
					list = resPicHouseService.getPaginatedList(resPicHouseSearch);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ResponseEnvelope<ResPicHouse> res = new ResponseEnvelope<ResPicHouse>(total, list);
			request.setAttribute("list", list);
			request.setAttribute("res", res);
			request.setAttribute("total", total);
			request.setAttribute("search", resPicHouseSearch);
			 
			return  JSPMAIN + "/resPicHouse_list";
		}
		
		
			
		
		 /**
		 * 图片资源库编辑(需整理的房型图片信息)
		 */
		@RequestMapping(value = "/edit")
		public Object edit(@ModelAttribute("resPicHouse") ResPicHouse resPicHouse,HttpServletRequest request, HttpServletResponse response) {
			String jsonStr = Utils.getJsonStr(request);
			Integer id = 0;
			if (jsonStr != null && jsonStr.trim().length() > 0){
				resPicHouse = (ResPicHouse)JsonUtil.getJsonToBean(jsonStr, ResPicHouse.class);
				if(resPicHouse != null){
					id =  resPicHouse.getId();
				}
				
			}else{
			    id =  resPicHouse.getId();
			}
			
			try {
				//根据id查询的房型图片对象
				resPicHouse = resPicHouseService.get(id);
				//获取上一条房型图片对象
				ResPicHouse prevResPicHouse = resPicHouseService.getPrevResPicHouseId(id);
				//获取下一条房型图片对象
				ResPicHouse nextResPicHouse = resPicHouseService.getNextResPicHouseId(id);
				
				request.setAttribute("res", resPicHouse);
				request.setAttribute("prevId", prevResPicHouse==null?"0":prevResPicHouse.getId());
				request.setAttribute("nextId", nextResPicHouse==null?"0":nextResPicHouse.getId());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return  JSPMAIN + "/resPicHouse_edit";
		}
		
		
		
		
		 /**
		 * 保存房型图片有关信息到对应的表
		 */
		@RequestMapping(value = "/saveResPicHousInfo")
		public void saveResPicHousInfo(int resPicHouseId,@ModelAttribute("baseHouse") BaseHouse baseHouse,HttpServletRequest request, HttpServletResponse response) {
			
			try {
				   int id = 0;
				   sysSave(baseHouse,request);
					if (baseHouse.getId() == null) {
					    id = baseHouseService.add(baseHouse);
						baseHouse.setId(id);
					} else {
					    id = baseHouseService.update(baseHouse);
					}
					
					//客厅
					String[] livingRoomsLength = request.getParameterValues("livingRoomLength");
					String[] livingRoomsWidth = request.getParameterValues("livingRoomWidth");
					if(livingRoomsLength.length>0){
						  for(int i=0;i<livingRoomsLength.length;i++){
						       String livingRoomLength = livingRoomsLength[i];
						       String livingRoomWidth = livingRoomsWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("客厅_"+(i+1));            
						       houseSpace.setMixLength(livingRoomLength); //主体长度
						       houseSpace.setMixWidth(livingRoomWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
					
					//餐厅
					String[] diningRoomsLength = request.getParameterValues("diningRoomLength");
					String[] diningRoomsWidth = request.getParameterValues("diningRoomWidth");
					if(diningRoomsLength.length>0){
						  for(int i=0;i<diningRoomsLength.length;i++){
						       String diningRoomLength = diningRoomsLength[i];
						       String diningRoomWidth = diningRoomsWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("餐厅_"+(i+1));            
						       houseSpace.setMixLength(diningRoomLength); //主体长度
						       houseSpace.setMixWidth(diningRoomWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
					
					//客餐厅
					String[] livingDiningRoomsLength = request.getParameterValues("livingDiningRoomLength");
					String[] livingDiningRoomsWidth = request.getParameterValues("livingDiningRoomWidth");
					if(livingDiningRoomsLength.length>0){
						  for(int i=0;i<livingDiningRoomsLength.length;i++){
						       String livingDiningRoomLength = livingDiningRoomsLength[i];
						       String livingDiningRoomWidth = livingDiningRoomsWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("客餐厅_"+(i+1));            
						       houseSpace.setMixLength(livingDiningRoomLength); //主体长度
						       houseSpace.setMixWidth(livingDiningRoomWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
					
					//卧室
					String[] bedRoomsLength = request.getParameterValues("bedRoomLength");
					String[] bedRoomsWidth = request.getParameterValues("bedRoomWidth");
					if(bedRoomsLength.length>0){
						  for(int i=0;i<bedRoomsLength.length;i++){
						       String bedRoomLength = bedRoomsLength[i];
						       String bedRoomWidth = bedRoomsWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("卧室_"+(i+1));            
						       houseSpace.setMixLength(bedRoomLength); //主体长度
						       houseSpace.setMixWidth(bedRoomWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
					
					//卫生间
					String[] bathRoomsLength = request.getParameterValues("bathRoomLength");
					String[] bathRoomsWidth = request.getParameterValues("bathRoomWidth");
					if(bathRoomsLength.length>0){
						  for(int i=0;i<bathRoomsLength.length;i++){
						       String bathRoomLength = bathRoomsLength[i];
						       String bathRoomWidth = bathRoomsWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("卫生间_"+(i+1));            
						       houseSpace.setMixLength(bathRoomLength); //主体长度
						       houseSpace.setMixWidth(bathRoomWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
					
					//厨房
					String[] cookRoomsLength = request.getParameterValues("cookRoomLength");
					String[] cookRoomsWidth = request.getParameterValues("cookRoomWidth");
					if(cookRoomsLength.length>0){
						  for(int i=0;i<cookRoomsLength.length;i++){
						       String cookRoomLength = cookRoomsLength[i];
						       String cookRoomWidth = cookRoomsWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("厨房_"+(i+1));            
						       houseSpace.setMixLength(cookRoomLength); //主体长度
						       houseSpace.setMixWidth(cookRoomWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
					
					//阳台
					String[] balconysLength = request.getParameterValues("balconyLength");
					String[] balconysWidth = request.getParameterValues("balconyWidth");
					if(balconysLength.length>0){
						  for(int i=0;i<balconysLength.length;i++){
						       String balconyLength = balconysLength[i];
						       String balconyWidth = balconysWidth[i];
						       
						       HouseSpace houseSpace = new HouseSpace();
						       houseSpace.setHouseId(id);
						       houseSpace.setSpaceName("阳台_"+(i+1));            
						       houseSpace.setMixLength(balconyLength); //主体长度
						       houseSpace.setMixWidth(balconyWidth);   //主体宽度
						       houseSpaceService.add(houseSpace); 
						       
						  }
					}
				
				    //数据保存后,数据状态变为已处理
					ResPicHouse resPicHouse = resPicHouseService.get(resPicHouseId);
					resPicHouse.setNumAtt1(1);
					resPicHouseService.update(resPicHouse);
				
//					List<BaseHouse> baseHouseList = baseHouseService.getList(new BaseHouse());
//					request.getSession().setAttribute("baseHouseList", baseHouseList);
//					
//					List<HouseSpace> houseSpaceList = houseSpaceService.getList(new HouseSpace());
//					request.getSession().setAttribute("houseSpaceList", houseSpaceList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	
	
		/**
		 * 图片上传
		 * 
		 * @param file
		 * @param request
		 * @param model
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/upload")
		public void upload(@RequestParam MultipartFile resPicHouseFile,HttpServletRequest request, HttpServletResponse response) throws IOException {
			
			//String picPath= "E:\\workspace\\onlineDecorate\\WebContent\\upload";
		
			
			//上传文件的原名(即上传前的文件名字)
			String imageName = null;
			String suffix = null;

			String originalFilename = resPicHouseFile.getOriginalFilename();
			//获得当前时间
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			//转换为字符串
			String formatDate = format.format(new Date());
			//随机生成文件编号
			int random = new Random().nextInt(10000);
			imageName = new StringBuffer().append(formatDate).append(random).toString();
			suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
			try {
				//这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
				FileUtils.copyInputStreamToFile(resPicHouseFile.getInputStream(),new File(Tools.getRootPath("", ""), imageName + suffix));
			} catch (IOException e) {    
				e.printStackTrace();
			}
			String basePath = app.getString("app.server.url");
			
			//filePath设置到model对象中，由model存入数据库中
			Map<String, Object> map = new HashMap<String, Object>();
			String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);//什么方法，我表示看不懂， 上面才new的，下面取个什么鬼
			
			
			ResPic resPic = new ResPic();
			resPic.setPicPath(imageName + suffix);
			resPic.setPicName(imageName);
			resPic.setPicPath(dbFilePath);
			resPic.setPicSuffix(suffix);
			resPic.setPicType(PicType.HOUSE);
			sysSave(resPic,request);
			int res_id = resPicService.add(resPic);
			
			Map<String, Object> picInfoMap = new HashMap<String, Object>();
			picInfoMap.put("picUrl", basePath+"/upload/"+imageName + suffix);
			picInfoMap.put("resId", res_id);
			JSONObject json = JSONObject.fromObject(picInfoMap);
			response.getWriter().print(json);
		}  
	  
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	
    /**
	 *    访问主页面
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/main")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN+"/resPic_add";
	}
    

	/**
	 * 保存 图片资源库(处理整理的数据资料)
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<ResPicHouse> houselist = new ArrayList<ResPicHouse>();
		
		File f=new File("E:\\housedata");     
		File[] list=f.listFiles();        /* 此处获取文件夹下的所有文件 */
		String cityName = "";
		String areaName = "";
		String yearStr = "";
		String liveName = "";
		String picName = "";
		String path = "";
		String areaPath = "";
		String yearPath = "";
		String livePath = "";
		String housePath = "";
		for(int i=0;i<list.length;i++)
			
		    path = list[i].getAbsolutePath();
				
			cityName = path.replace("E:\\housedata\\", "");
			
			File areaFile=new File(path);   
			File[] areaList=areaFile.listFiles();  
			for(int a=0;a<areaList.length;a++){
				
				areaPath = areaList[a].getAbsolutePath();
				
				areaName = areaPath.replace(path+"\\", "");
				
				File yearFile=new File(areaPath);   
				File[] yearList=yearFile.listFiles();  
				for(int b=0;b<yearList.length;b++){
					
					yearPath = yearList[b].getAbsolutePath();
					
					yearStr = yearPath.replace(areaPath+"\\", "");
					
					if(!yearPath.endsWith(".db")){
					
					File liveFile=new File(yearPath);   
					File[] liveList=liveFile.listFiles();  
					for(int c=0;c<liveList.length;c++){
						
						livePath = liveList[c].getAbsolutePath(); 
						
						liveName = livePath.replace(yearPath+"\\", "");
						
						File houseFile=new File(livePath);   
						File[] houseList=houseFile.listFiles(); 
						for(int d=0;d<houseList.length;d++){
							
							housePath = houseList[d].getAbsolutePath();
							
							String picNameStr =  housePath.replace(livePath+"\\", "");
							picName = picNameStr.substring(0, picNameStr.length()-4);
							
							//////System.out.println(cityName+"--"+areaName+"--"+yearStr+"--"+liveName+"--"+picName+"--"+housePath);
							
							// 获得当前时间
							DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
							// 转换为字符串
							String formatDate = format.format(new Date());
							// 随机生成文件编号
							int random = new Random().nextInt(10000);
							
							String imageName = new StringBuffer().append(formatDate).append(random).toString();
					        
					       // String picPath=request.getSession().getServletContext().getRealPath("images");
							
							String picPath= "E:\\workspace\\onlineDecorate\\WebContent\\upload";
					        
					        File file = new File(housePath);
					        
					        if(!file.exists())
					            throw new RuntimeException("文件不存在..");
					        
					        FileInputStream fis = new FileInputStream(file);
					        byte[] bt = new byte[1024];
					        int len = 0;
					        
					        String fileName=file.getName();
					        String prefix=fileName.substring(fileName.lastIndexOf("."));
					        
					        if(prefix.endsWith("jpg")||prefix.endsWith("png")){
					        	 FileOutputStream fos = new FileOutputStream(picPath+"//"+imageName+prefix);
							        while((len=fis.read(bt))!=-1){
							            fos.write(bt,0,len);
							        }
							        fos.close();
							        fis.close();
							        ResPicHouse resPicHouse = new ResPicHouse();
							        resPicHouse.setPicName(picName);
							        
							        resPicHouse.setPicPath(imageName+prefix);
								       
							        resPicHouse.setAtt1(cityName+areaName);
							        resPicHouse.setAtt2(yearStr);
							        resPicHouse.setAtt3(liveName);
									
							        sysSave(resPicHouse,request);
									if (resPicHouse.getId() == null) {
										int id = resPicHouseService.add(resPicHouse);
										logger.info("add:id=" + id);
										resPicHouse.setId(id);
									} else {
										int id = resPicHouseService.update(resPicHouse);
										logger.info("update:id=" + id);
									}
					        }
					        
						}
						
					 }
			
			      }
			   }
			}
			
			return new ResponseEnvelope<ResPicHouse>(5, houselist,"0");
			
   }
	

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResPicHouse model,HttpServletRequest request){
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
	 * 自动存储系统字段
	 */
	private void sysSave(BaseHouse model,HttpServletRequest request){
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
