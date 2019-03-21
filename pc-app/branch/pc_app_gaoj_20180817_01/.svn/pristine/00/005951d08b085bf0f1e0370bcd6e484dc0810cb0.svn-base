package com.nork.base.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nork.base.model.FileMD5;
import com.nork.base.service.BaseService;
import com.nork.common.model.LoginMenu;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.NewFileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.home.model.BaseHouse;
import com.nork.home.service.BaseHouseService;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysFunc;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysFuncService;

@Controller
@RequestMapping("/{style}")
public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String EXECUTE_STATUS = "executeStatus";
	public static final int SUCCESS = 0;
	public static final String VALUES = "values";
	public final static String SYSTEM_FORMAT = Utils.getValue(
			"app.system.format", "linux").trim();

	@Autowired
	private BaseService baseService;
	@Autowired
	private SysFuncService sysFuncService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResPicService resPicService;

	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private BaseHouseService baseHouseService;

	/**
	 * 基于ExceptionHandler异常处理
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	// 楼鑫华我先去掉
	// @ExceptionHandler
	// public String exception(HttpServletRequest request,HttpServletResponse
	// response,Exception ex){
	// String errorInfo = "";
	// if(ex instanceof DataAccessException){
	// errorInfo = "数据操作失败";
	// }else if(ex instanceof NullPointerException){
	// errorInfo = "调用了未经初始化的对象或者不存在的对象";
	// }else if(ex instanceof IOException){
	// errorInfo = "IO异常";
	// }else if(ex instanceof ClassNotFoundException){
	// errorInfo = "指定的类不存在";
	// }else if(ex instanceof ArithmeticException){
	// errorInfo = "数据运算异常";
	// }else if(ex instanceof ArrayIndexOutOfBoundsException){
	// errorInfo = "数据下标越界";
	// }else if(ex instanceof IllegalArgumentException){
	// errorInfo = "方法的参数错误";
	// }else if(ex instanceof ClassCastException){
	// errorInfo = "类型强制转换错误";
	// }else if(ex instanceof SecurityException){
	// errorInfo = "违背安全原则异常";
	// }else if(ex instanceof SQLException){
	// errorInfo = "操作数据库异常";
	// }else if(ex.getClass().equals(NoSuchMethodError.class)){
	// errorInfo = "方法未找到异常";
	// }else if(ex.getClass().equals(InternalError.class)){
	// errorInfo = "Java虚拟机发生了内部错误";
	// }else{
	// errorInfo = "程序内部错误,操作失败";
	// }
	// logger.info("异常信息: "+errorInfo,ex);
	// JSONObject json = new JSONObject();
	// json.put("isError", true);
	// json.put("msg", errorInfo);
	// return json.toString();
	// }

	@RequestMapping("/online")
	public Object online(HttpServletRequest request) {
		return "redirect:/pages/online/index.jsp";
	}

	@RequestMapping("/getmenulist")
	public Object getMenu(
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "userId", required = true) Integer userId,
			HttpServletRequest request) {
		List<LoginMenu> menulist = new ArrayList<LoginMenu>();

		// 获取该用户的菜单权限
		List<LoginMenu> datalist = new ArrayList<LoginMenu>();
		List<SysFunc> funcList = sysFuncService.getUserMenus(userId);
		// 转成loginMenu
		if (funcList != null) {
			for (SysFunc func : funcList) {
				datalist.add(func.toLoginMenu());
			}
		}
		// datalist = new LoginMenu().getMenulist();
		// 递归获取全部数据
		for (LoginMenu m : datalist) {
			if (m.getId().equals(id)) {
				menulist.add(m);
				getMenuson(m.getId(), menulist, datalist);
			}
		}
		request.setAttribute("menulist", menulist);
		return "/jsp/common/left";
	}

	private static List<LoginMenu> getMenuson(Integer id,
			List<LoginMenu> menulist, List<LoginMenu> datalist) {
		for (LoginMenu menu : datalist) {
			if (id.equals(menu.getParentId())) {
				menulist.add(menu);
				getMenuson(menu.getId(), menulist, datalist);
			}
		}

		return menulist;
	}

	/**
	 * 通过request获取对象的字段类型
	 */
	@RequestMapping("/getFuncAttType")
	@ResponseBody
	public Object getFuncAttType(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			Map<String, Object> map = Utils.parserToMap(jsonStr);
			String model = (String) map.get("model");
			String func = (String) map.get("func");
			String javaname = Utils.getFirstUpperStr(func);
			String modelname = "com.nork." + model + ".model." + javaname;
			String modelsearchname = "com.nork." + model + ".model.search."
					+ javaname + "Search";
			String modelsmallname = "com.nork." + model + ".model.small."
					+ javaname + "Small";
			obj.putAll(Utils.getJavaType(modelname));
			obj.putAll(Utils.getJavaType(modelsearchname));
			obj.putAll(Utils.getJavaType(modelsmallname));

		}
		return obj;
	}

	@RequestMapping("/uploadTest")
	@ResponseBody
	public boolean uploadTest(
			@RequestParam(value = "filePath", required = false) MultipartFile filePath,
			String msgId, String planId, String level) {
		return true;
	}

	/**
	 * 设计方案渲染图上传
	 * 
	 * @param designPlanSearch
	 * @param request
	 * @return object
	 */
	@RequestMapping("/uploadPlanRender1")
	@ResponseBody
	public boolean uploadPlanRender1(HttpServletRequest request,
			HttpServletResponse response) {

		// 多文件上传使用request
		if (request instanceof MultipartHttpServletRequest) {
			// 获取文件列表并将物理文件保存到服务器中
			// 将HttpServletRequest对象转换为MultipartHttpServletRequest对象
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String fileName = null;

			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				fileName = mf.getOriginalFilename();
				logger.info("文件上传名称：" + fileName);
				response.setContentType("text/html;charset=utf-8");
				// 获取文件列表并将物理文件保存到服务器中

				// filePath设置到model对象中，由model存入数据库中
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(FileUploadUtils.UPLOADPATHTKEY,
						"design.designPlan.render");
				boolean flag = false;
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveFile(map);
				// 生成图片,支持多张,需写到server
				// 插入方案表中
			}
		}
		return true;
	}

	/**
	 * 
	 * @Title: check
	 * @Description: 通过url获取value
	 * @param request
	 * @return: Object
	 * @throws
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Object check(HttpServletRequest request) {
		StringBuffer obj = new StringBuffer();
		try {
			BufferedInputStream in = new BufferedInputStream(
					request.getInputStream());
			int i;
			char c;
			while ((i = in.read()) != -1) {
				c = (char) i;
				obj.append(c);
			}
		} catch (Exception ex) {
			logger.error("通过url获取value失败", ex);
			ex.printStackTrace();
		}
		return new ResponseEnvelope<String>(true);
	}

	/**
	 * 
	 * @Title: download
	 * @Description: 文件下载 </p>
	 * @param request
	 * @param response
	 * @param downloadName
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	public void download(HttpServletRequest request,
			HttpServletResponse response, String downloadName) {
		try {
			String storeName = java.net.URLDecoder
					.decode(downloadName, "UTF-8");
			// 过滤..
			String path = storeName.replaceAll("(\\.\\.)", " ");
			logger.info("path"+path);
			String realNameTemp = storeName.substring(storeName
					.lastIndexOf("/"));

			String realName = realNameTemp.substring(1);
			String contentType = "application/octet-stream";
			if (pathReExp(storeName)) {
				NewFileUploadUtils.download(request, response, path,
						contentType, realName);
			}
		} catch (Exception e) {
			logger.error("文件下载失败", e);
			e.printStackTrace();
		}

	}

	/**
	 * 下载地址判断
	 */
	public Boolean pathReExp(String storeName) {
		// 判断文件夹地址
		if (storeName.endsWith(".zip") == false && storeName.endsWith(".exe") == false&&storeName.endsWith(".ZIP") == false) {
			logger.error("文件类型错误！");
			return false;
		}
		//版本资源上传路径更改,校验更改 (暂时定死.....)
		String names[] =storeName.split("/");
		if(names.length>8) {
			String path =names[7]+"/"+names[8];
			if (!"system/sysVersion".equals(path)) {
		    return false;
			}
		}else {
			return false;
		}
			
		return true;
	}
	

	/**
	 * 
	 * @Title: test
	 * @Description: 测试请求
	 * @param:
	 * @return: void
	 * @throws
	 */
	@RequestMapping(value = "/test")
	public void test() {
		try {
			List<BaseProduct> productList = baseProductService.getList(null);
			if (productList != null && productList.size() > 0) {
				List<BaseProduct> newList = new ArrayList<BaseProduct>();
				for (BaseProduct product : productList) {
					if (StringUtils.isNotBlank(product.getHouseTypeValues())) {
						String[] arr = product.getHouseTypeValues().split(",");
						SysDictionary dictionary = null;
						String ids = "";
						for (int i = 0; i < arr.length; i++) {
							if (StringUtils.isNotBlank(arr[i])) {
								dictionary = sysDictionaryService.get(Integer
										.valueOf(arr[i]));
								ids += dictionary.getValue();
								if ((i + 1) < arr.length) {
									ids += ",";
								}
							}
						}
						product.setHouseTypeValues(ids);
						newList.add(product);
					}
				}
				// 更新
				baseService.updateData(newList);
			}
		} catch (Exception e) {
			logger.error("测试请求 ", e);
			e.printStackTrace();
		}
	}

	/**
	 * 读取某路径下的文件列表
	 * 
	 * @param file
	 * @return
	 */
	public void generateFiles(File file, String path) throws Exception {
		if (file.isDirectory()) {
			List<FileMD5> md5List = new ArrayList<>();
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					generateFiles(fileList[i], path);
				} else {
					if ("filelist.txt".equals(fileList[i].getName())) {
						continue;
					}
					FileMD5 fileMD5 = new FileMD5();
					String filePath = fileList[i].getPath();
					logger.info("filePath===========>" + filePath
							+ ";path==========>" + path);
					String sfilePath = "";
					/*if ("linux".equals(SYSTEM_FORMAT)) {
						sfilePath = filePath.replace(
								Constants.UPLOAD_ROOT, "");
					} else {
						sfilePath = filePath.replace(
						        Constants.UPLOAD_ROOT, "").replace("\\",
								"/");
					}*/
					sfilePath = Utils.getRelativeUrlByAbsolutePath(filePath);
					fileMD5.setPath(sfilePath);
					String md5 = getMD5(fileList[i]);
					fileMD5.setMd5value(md5);
					md5List.add(fileMD5);
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(md5List);
			if (jsonArray.size() > 0) {
				String newPath = file.getPath() + "/filelist.txt";
				logger.info("newPath:old====================>" + newPath);
				if ("linux".equals(SYSTEM_FORMAT)) {
					newPath = newPath.replace("\\", "/");
				} else {
					newPath = newPath.replace("/", "\\");
				}
				logger.info("newPath:new====================>" + newPath);
				File jsonFile = new File(newPath);
				jsonFile.createNewFile();
				FileWriter fw = new FileWriter(jsonFile);
				fw.write(jsonArray.toString());
				fw.close();
			}
		}
	}

	/**
	 * 
	 * @Title: getMD5
	 * @Description: 获取文件MD5 </p>
	 * @param file
	 * @throws Exception
	 * @return: String
	 */
	public String getMD5(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
		IOUtils.closeQuietly(fis);
		return md5;
	}

	/**
	 * 
	 * @Title: updateDBResources
	 * @Description: 修改数据库资源表
	 * @param opType
	 * @return: Object
	 */
	@RequestMapping(value = "/updateDBResources")
	public Object updateDBResources(String opType) {
		if ("pic".equals(opType)) {
			// 户型图
			List<BaseHouse> baseHouseList = baseHouseService
					.getList(new BaseHouse());
			for (BaseHouse baseHouse : baseHouseList) {
				Integer picId = baseHouse.getPicRes1Id();
				if (picId != null) {
					ResPic resPic = resPicService.get(picId);
					if (resPic != null) {
						resPic.setBusinessId(baseHouse.getId());
						resPic.setSysCode(baseHouse.getHouseCode());
						resPicService.update(resPic);
					}
				}
			}
			// 设计方案图片
			List<DesignPlan> designPlanList = designPlanService
					.getList(new DesignPlan());
			// 样板房图片
			List<DesignTemplet> designTempletList = designTempletService
					.getList(new DesignTemplet());
			for (DesignTemplet designTemplet : designTempletList) {
				// 样板房空间布局图
				Integer picId = designTemplet.getPicId();
				if (picId != null) {
					ResPic resPic = resPicService.get(picId);
					if (resPic != null) {
						resPic.setBusinessId(designTemplet.getId());
						resPic.setSysCode(designTemplet.getDesignCode());
						resPicService.update(resPic);
						// 样板房空间布局图-缩略图
						String smallPicInfo = resPic.getSmallPicInfo();
						try {
							JSONObject jsonObject = JSONObject
									.fromObject(smallPicInfo);
							Object smallPicObj = jsonObject.get("ipad");
							if (smallPicObj != null) {
								ResPic smallPic = resPicService.get(Integer
										.valueOf(smallPicObj.toString()));
								if (smallPic != null) {
									smallPic.setBusinessId(designTemplet
											.getId());
									smallPic.setSysCode(designTemplet
											.getDesignCode());
									resPicService.update(smallPic);
								}
							}
						} catch (Exception e) {
							logger.error("修改数据库资源表操作失败", e);
							e.printStackTrace();
							continue;
						}
					}
				}
				// 样板房效果图列表
				String effectPic = designTemplet.getEffectPic();
				if (StringUtils.isNotBlank(effectPic)) {
					String[] arr = effectPic.split(",");
					for (String effectPicId : arr) {
						if (StringUtils.isNotBlank(effectPicId)) {
							ResPic resPic = resPicService.get(Integer
									.valueOf(effectPicId));
							if (resPic != null) {
								resPic.setBusinessId(designTemplet.getId());
								resPic.setSysCode(designTemplet.getDesignCode());
								resPicService.update(resPic);
								// 样板房效果图-缩略图
								String smallPicInfo = resPic.getSmallPicInfo();
								try {
									JSONObject jsonObject = JSONObject
											.fromObject(smallPicInfo);
									Object smallPicObj = jsonObject.get("ipad");
									if (smallPicObj != null) {
										ResPic smallPic = resPicService
												.get(Integer
														.valueOf(smallPicObj
																.toString()));
										if (smallPic != null) {
											smallPic.setBusinessId(designTemplet
													.getId());
											smallPic.setSysCode(designTemplet
													.getDesignCode());
											resPicService.update(smallPic);
										}
									}
								} catch (Exception e) {
									logger.error("修改数据库资源表操作失败", e);
									e.printStackTrace();
									continue;
								}
							}
						}
					}
				}
			}
		} else if ("model".equals(opType)) {

		} else if ("file".equals(opType)) {

		}
		return null;
	}

}
