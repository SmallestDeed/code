package com.nork.design.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.cityunion.service.UnionDesignPlanStoreService;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.FileModel;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.*;
import com.nork.design.model.DesignRenderGroup;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.threadpool.RenderJobType;
import com.sandu.common.LoginContext;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.product.model.BaseBrand;
import com.nork.system.service.ResRenderPicService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设计方案副本controller
 * @author huangsongbo
 *
 */
@Controller
@RequestMapping("/{style}/web/design/designPlanRenderScene")
public class WebDesignPlanRenderSceneController {

	private Logger logger = LoggerFactory.getLogger(WebDesignPlanRenderSceneController.class);
	
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	
	@Autowired
	private ResRenderPicService resRenderPicService;

	@Autowired
	private UnionDesignPlanStoreService unionDesignPlanStoreService;
	
	/**
	 * 通过副本id删除副本数据及关联的渲染图(逻辑删除,is_deleted = 1)
	 * @author huangsongbo
	 * @param planId
	 * @param msgId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public Object deleteById(
			Integer planId,
			String msgId,
			HttpServletRequest request,
			HttpServletResponse response
			){
		
		if(planId == null){
			return new ResponseEnvelope<>(false, "参数planId不能为空", msgId);
		}
		
		DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(planId);
		
		if(designPlanRenderScene == null){
			logger.error("------副本数据不存在,id:" + planId);
			return new ResponseEnvelope<>(false, "找到对应副本", msgId);
		}
		
		// 检测该副本是不是该登录用户的副本,如果不是,不允许删除
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		
		if(loginUser == null || loginUser.getId() == null || loginUser.getId() < 1){
			return new ResponseEnvelope<>(false, "未检测到登录信息,请重新登录", msgId);
		}
		
		if(!loginUser.getId().equals(designPlanRenderScene.getUserId())){
			return new ResponseEnvelope<>(false, "删除失败,不能删除别人的设计方案", msgId);
		}

		//判断该方案是否组合
		if(null != designPlanRenderScene.getGroupPrimaryId() && designPlanRenderScene.getGroupPrimaryId() > 0){

			//查询组合
			List<DesignPlanRenderScene> groupList = designPlanRenderSceneService.getGroupList(designPlanRenderScene.getGroupPrimaryId(),0);
			//循环删除
			for (DesignPlanRenderScene renderScene : groupList) {

				planId = renderScene.getId();
				/**
				 * add by : xiaoxc-20180910
				 * 需求：COMMON-1415
				 * 注释下面代码，增加逻辑删除效果图方案
				 */
				// 删除副本及关联数据(物理删除)
				//designPlanRenderSceneService.deleteAllDataById(planId);

				// 删除副本对应的渲染图(逻辑删除)
				//resRenderPicService.deletePicBySceneId(planId);

				//逻辑删除效果图方案
				designPlanRenderSceneService.delete(planId);
				/**
				 * 删除同城联盟方案素材库及发布素材库相关联的数据（逻辑删除）
				 * add by xiaoxc_20180124
				 */
				unionDesignPlanStoreService.deleteCityUnionPlanStoreByDesignPlanId(renderScene.getId());
			}

		}else{
			/**
			 * add by : xiaoxc-20180910
			 * 需求：COMMON-1415
			 * 注释下面代码，增加逻辑删除效果图方案
			 */
			// 删除副本及关联数据(物理删除)
			//designPlanRenderSceneService.deleteAllDataById(planId);

			// 删除副本对应的渲染图(逻辑删除)
			//resRenderPicService.deletePicBySceneId(planId);

			//逻辑删除效果图方案
			designPlanRenderSceneService.delete(planId);

			/**
			 * 删除同城联盟方案素材库及发布素材库相关联的数据（逻辑删除）
			 * add by xiaoxc_20180124
			 */
			unionDesignPlanStoreService.deleteCityUnionPlanStoreByDesignPlanId(planId);
		}

		return new ResponseEnvelope<>(true, "删除成功", msgId);
	}
	
	
	/**
	 * 通过厂商看到 经销商下 所有用到 该厂商品牌的设计方案
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping( value="/vendorPlanList")
    @ResponseBody
	public ResponseEnvelope<DesignPlanRenderScene> vendorPlanList(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String msgId = request.getParameter("msgId");
		String brandId = request.getParameter("brandId");
		String planName = request.getParameter("planName");
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<DesignPlanRenderScene>(false," parame not found!",msgId);
		}
        if (loginUser==null) {
            return new ResponseEnvelope<DesignPlanRenderScene>(false,SystemCommonConstant.PLEASE_LOGIN,msgId);
        }
        return designPlanRenderSceneService.vendorPlanList(msgId,brandId,planName,loginUser,limit,start);
	}
	
	
	
	/**
	 * 得到该厂商的所有品牌
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping( value="/manufacturerBrandList")
    @ResponseBody
	public ResponseEnvelope<BaseBrand>  manufacturerBrandList(HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String msgId = request.getParameter("msgId");
		if(StringUtils.isEmpty(msgId)){
			return new ResponseEnvelope<BaseBrand>(false," parame not found!",msgId);
		}
        if (loginUser.getId() < 0) {
            return new ResponseEnvelope<BaseBrand>(false,SystemCommonConstant.PLEASE_LOGIN,msgId);
        }
        return designPlanRenderSceneService.manufacturerBrandList(msgId,loginUser);
	}

	/**
	 * 效果图方案打组校验接口
	 * @author chenqiang
	 * @param msgId
	 * @param ids			方案id（id,id,....）
	 * @param spaceAreas	使用面积（面积,面积.....）
	 * @param checkType		校验类型（1：方案空间类型、2：方案适用面积）
	 * @param request
	 * @param response
	 * @return
	 * @date 2018/8/13 0013 16:22
	 */
	@RequestMapping( value="/addGroupListCheck")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRenderScene>  addGroupList(String msgId,String ids,String spaceAreas,String checkType,
																 HttpServletRequest request,HttpServletResponse response){
		//参数校验
		if(StringUtils.isBlank(checkType))
			return new ResponseEnvelope<>(false, "校验类型不能为空", msgId);

		//校验方案空间类型
		if(checkType.equals("1")){
			//参数校验
			if(StringUtils.isBlank(ids))
				return new ResponseEnvelope<>(false, "方案ids不能为空", msgId);

			List<Integer> idList = Arrays.asList(ids.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

			List<String> spaceList = designPlanRenderSceneService.selectSpaceListByIdList(idList);
			if(null == spaceList || spaceList.size() == 0)
				return new ResponseEnvelope<>(false, "选择的效果图方案功能空间为空", msgId);
			if(spaceList.size() > 1)
				return new ResponseEnvelope<>(false, "选择的效果图方案功能空间不一样", msgId);

			return new ResponseEnvelope<>(true, "选择的效果图方案功能空间一样", msgId);


		//校验方案使用面积
		}else if(checkType.equals("2")){

			//参数校验
			if(StringUtils.isBlank(spaceAreas))
				return new ResponseEnvelope<>(false, "适用面积参数不能为空", msgId);

			//切割获取list集合
			List<String> strList = Arrays.asList(spaceAreas.split(","));

			//list集合去重
			Set<String> set = new HashSet<String>(strList);		//去重

			//判断是否去重前与去重后是否相等
			if(strList.size() != set.size()){
				return new ResponseEnvelope<>(false, "选择的效果图方案适用面积重复", msgId);
			}else{
				return new ResponseEnvelope<>(true, "选择的效果图方案适用面积正确", msgId);
			}

		}else{
			return new ResponseEnvelope<>(false, "校验类型参数有误", msgId);
		}
	}


	/**
	 * 效果图方案打组接口
	 * @author chenqiang
	 * @param msgId
	 * @param groupListJson	方案组合参数
	 * @param request
	 * @param response
	 * @return
	 * @date 2018/8/13 0013 17:47
	 */
	@RequestMapping( value="/addGroupList")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRenderScene>  addGroupList(String msgId,String groupListJson,
			HttpServletRequest request,HttpServletResponse response){

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		List<DesignRenderGroup> groupList = null;
		try {
			groupList = new Gson().fromJson(groupListJson,new TypeToken<List<DesignRenderGroup>>(){}.getType());
		}catch (Exception e){
			logger.error("转换打组JSON参数错误{}",e);
			return new ResponseEnvelope<>(false, "转换打组JSON参数错误", msgId);
		}

		//参数校验
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "请重新登录", msgId);
		if(null == groupList || groupList.size() == 0)
			return new ResponseEnvelope<>(false, "效果图方案打组参数有误", msgId);

		//打组
		boolean bool = designPlanRenderSceneService.addGroupList(groupList,loginUser);

		//返回
		if(bool)
			return new ResponseEnvelope<>(true, "效果图方案打组成功", msgId);
		else
			return new ResponseEnvelope<>(false, "效果图方案打组出错", msgId);

	}

	/**
	 * 效果图方案解组接口
	 * @author chenqiang
	 * @param msgId
	 * @param designRenderId	方案id
	 * @param request
	 * @param response
	 * @return
	 * @date 2018/8/13 0013 16:26
	 */
	@RequestMapping( value="/deleGroupList")
	@ResponseBody
	public ResponseEnvelope<DesignPlanRenderScene>  deleGroupList(String msgId,Integer designRenderId,
																 HttpServletRequest request,HttpServletResponse response){
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(null == designRenderId)
			return new ResponseEnvelope<>(false, "方案参数为空", msgId);
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "请重新登录", msgId);

		int count = designPlanRenderSceneService.delGroupList(designRenderId,loginUser);

		if(count > 0)
			return new ResponseEnvelope<>(true, "效果图方案解组成功", msgId);
		else
			return new ResponseEnvelope<>(false, "效果图方案解组出错", msgId);

	}

	/**
	 * 效果图方案上传图片
	 * @author chenqiang
	 * @param msgId
	 * @param designPlanRederId		效果图方案id
	 * @return
	 * @date 2018/8/14 0014 10:20
	 */
	@RequestMapping( value="/img/upload")
	@ResponseBody
	public ResponseEnvelope uploadImg(@RequestParam("msgId") String msgId,@RequestParam("designPlanRenderId")Integer designPlanRederId,String authorization,
														 HttpServletRequest request,HttpServletResponse response) {
		//获取登录用户
		Map<String, Object> authorizationMap = Jwt.validToken(authorization);
		request.setAttribute("AuthorizationData", authorizationMap.get("data"));
		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);

		try {

			//参数验证
			if(null == designPlanRederId)
				return new ResponseEnvelope(false,"效果图方案参数不能为空",msgId);
			if(null == loginUser)
				return new ResponseEnvelope<>(false, "请重新登录", msgId);
			//获取效果图方案
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanRederId);
			if(null == designPlanRenderScene)
				return new ResponseEnvelope(false,"效果图方案不存在",msgId);

			if (request instanceof MultipartHttpServletRequest) {

				//将HttpServletRequest对象转换为MultipartHttpServletRequest对象
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
				Collection<MultipartFile> values = fileMap.values();
				Iterator<MultipartFile> fileList = values.iterator();

				while (fileList.hasNext()){
					MultipartFile file = fileList.next();

					//文件流验证
					if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename()))
						return new ResponseEnvelope(false, "上传文件为空",msgId);


					/** 获取上传图片信息 */
					//上传文件名
					String oldName = file.getOriginalFilename();
					//后缀
					String suffix = StringUtils.substringAfterLast(oldName, ".");
					//大小
					Long size = file.getSize();

					/** 校验 */
					if (!("jpg，png".contains(suffix)||"JPG，PNG".contains(suffix))) {
						return new ResponseEnvelope(false, "仅支持图片格式jpg、png",msgId);
					}
					logger.error("上传图片大小="+size);
					if (size > 15*1024*1024) {
						return new ResponseEnvelope(false, "仅支持上传图片大小15M",msgId);
					}

					//原图路径变量
					String filePathPrefix = "";		//路径前缀
					String filePath = "";			//完整上传路径

					//构建上传路径前缀
					String fileKey = "design.designPlan.render.upload.path";
					String defaultPath = "/AA/d_userdesign/[YYYY]/[MM]/[dd]/[HH]/design/designPlan/render/";
					filePathPrefix = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
					filePathPrefix = Utils.replaceDate(filePathPrefix);
					filePathPrefix = Utils.getAbsolutePath(filePathPrefix, null);//这句话获取全部资源路径：在内部获取存储前缀（filePathPrefix）
					if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) )
						filePathPrefix = filePathPrefix.replace("\\", "/");
					File fileCheck = new File(filePathPrefix);//构建文件流文件夹
					if(!fileCheck.exists())
						fileCheck.mkdirs();

					//生成新的文件名称
					String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6) + "." + suffix;

					//完整路径
					filePath = filePathPrefix + fileName;

					//上传图片
					File picFile = new File(filePath);
					file.transferTo(picFile);

					//非jpg图片,转换为JPG
					File sFile = null;
					if(!suffix.toLowerCase().equals("jpg")){
						String thumbnailPicName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6) + ".jpg";
						String thumbnailPicPath = filePathPrefix + thumbnailPicName;

						//压缩
						Thumbnails.of(filePath).scale(1f).outputFormat("jpg").toFile(thumbnailPicPath);

						//删除原未压缩数据
						if (picFile.exists()) {
							logger.error("原未压缩文件路径filePath="+filePath);
							picFile.delete();
						} else {
							logger.error("文件不存在filePath：{}" + filePath);
						}

						sFile = new File(thumbnailPicPath);
					}else{
						sFile = new File(filePath);
					}


					//获取上传文件信息
					Map<String, String> renderPicMap = FileUploadUtils.getMap(sFile,"design.designPlan.render.upload.path",false);

					//新增照片级原图数据信息
					int resId = savePlanRenderPicOfPhot(renderPicMap,designPlanRenderScene);
					if(resId < 1)
						return new ResponseEnvelope(false, "新增图片信息错误",msgId);

					//生成缩略图
					String smallFileName = Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS) + ".jpg";// + suffix;=强制缩略图为jpg
					String storePath = Utils.getPropertyName("config/res",fileKey,defaultPath).trim() + "small/" + smallFileName;
					storePath = Utils.replaceDate(storePath);
					String targetSmallFilePath = Utils.getAbsolutePath(storePath, null);
					if ("windows".equals(Utils.getValue("app.system.format", "linux").trim()))
						targetSmallFilePath = targetSmallFilePath.replace("/", "\\");
					if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) )
						targetSmallFilePath = targetSmallFilePath.replace("\\", "/");

					//根据原图生成缩略图
					ThumbnailUtil.createThumbnail(filePath,targetSmallFilePath,ThumbnailUtil.PIC_WIDTH,ThumbnailUtil.PIC_HIGHT);
					File targetSmallFile = new File(targetSmallFilePath);
					Map smallRenderPicMap = FileUploadUtils.getMap(targetSmallFile, "design.designPlan.render.upload.path", false);

					//新增照片级缩略图数据信息
					int smallId = savePlanRenderSamllPicOfPhot(smallRenderPicMap,resId,designPlanRenderScene);
					if(smallId < 1)
						return new ResponseEnvelope(false, "新增图片缩略图信息错误",msgId);

				}

				return new ResponseEnvelope(true, "上传成功",msgId);
			}

			return new ResponseEnvelope(false, "上传参数错误",msgId);

		}catch(Exception e){
			logger.error("图片上传失败",e);
			return new ResponseEnvelope(false, "系统错误",msgId);
		}

	}

	/**
	 * 保存原图
	 * @author chenqiang
	 * @param renderPicMap				原图文件流Map
	 * @param designPlanRenderScene		效果图方案对象
	 * @return
	 * @date 2018/8/14 0014 11:49
	 */
	private int savePlanRenderPicOfPhot(Map renderPicMap,DesignPlanRenderScene designPlanRenderScene){

		//保存渲染图原图
		ResRenderPic renderPic = assembleResPic(renderPicMap);
		renderPic.setIsDeleted(0);
		renderPic.setCreator(designPlanRenderScene.getCreator());
		renderPic.setModifier(designPlanRenderScene.getCreator());
		renderPic.setDesignPlanSceneId(designPlanRenderScene.getId());
		renderPic.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
		renderPic.setPicType("照片级原图");
		renderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
		renderPic.setTaskCreateTime(new Date());
		renderPic.setSourcePlanId(0);
		renderPic.setTemplateId(0);
		logger.info("addrenderPic="+designPlanRenderScene.getId());
		return  resRenderPicService.addDesignRender(renderPic);

	}

	/**
	 * 保存缩略图
	 * @author chenqiang
	 * @param smallRenderPicMap			缩略图文件流Map
	 * @param pid						原图id
	 * @param designPlanRenderScene		效果图方案对象
	 * @return
	 * @date 2018/8/14 0014 11:49
	 */
	private int savePlanRenderSamllPicOfPhot(Map smallRenderPicMap,Integer pid,DesignPlanRenderScene designPlanRenderScene){

		//保存缩略图
		ResRenderPic smallRenderPic = assembleResPic(smallRenderPicMap);
		smallRenderPic.setPid(pid);
		smallRenderPic.setCreator(designPlanRenderScene.getCreator());
		smallRenderPic.setIsDeleted(0);
		smallRenderPic.setModifier(designPlanRenderScene.getCreator());
		smallRenderPic.setDesignPlanSceneId(designPlanRenderScene.getId());
		smallRenderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
		smallRenderPic.setPicType("照片级缩略图");
		smallRenderPic.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
		smallRenderPic.setTaskCreateTime(new Date());
		smallRenderPic.setSourcePlanId(0);
		smallRenderPic.setTemplateId(0);
		return resRenderPicService.addDesignRender(smallRenderPic);

	}

	/**
	 * 根据文件流得到的Map组装resPic
	 * @param map
	 * @return
	 */
	private ResRenderPic assembleResPic(Map map){

		ResRenderPic resPic = new ResRenderPic();
		String dbFilePath = Utils.dealWithPath(map.get(FileUploadUtils.DB_FILE_PATH).toString(), "linux");
		resPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		resPic.setPicCode(resPic.getSysCode());
		resPic.setGmtCreate(new Date());
		resPic.setGmtModified(new Date());
		resPic.setIsDeleted(0);
		resPic.setPicName(map.get(FileModel.FILE_ORIGINAL_NAME)==null?"":map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		resPic.setPicSize(map.get(FileModel.FILE_SIZE)==null?-1:Integer.valueOf(map.get(FileModel.FILE_SIZE).toString()));
		resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		resPic.setPicFileName(dbFilePath.substring(dbFilePath.lastIndexOf("/") + 1, dbFilePath.lastIndexOf(".")));
		resPic.setPicPath(dbFilePath);
		resPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
		resPic.setPicType("无");
		resPic.setViewPoint(null != map.get("viewPoint") ? Integer.parseInt(map.get("viewPoint").toString()) : null);
		resPic.setScene(null != map.get("scene") ? Integer.parseInt(map.get("scene").toString()) : null);
		return resPic;

	}

}
