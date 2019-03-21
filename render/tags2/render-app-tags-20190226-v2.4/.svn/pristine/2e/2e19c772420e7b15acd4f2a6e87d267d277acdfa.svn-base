package com.nork.design.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.Utils;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.RenderPicVO;
import com.nork.design.model.ResRenderPicQO;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.OptimizePlanService;
import com.nork.design.service.SaveRenderPicService;
import com.nork.job.SaveRenderPicJob;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.small.ResFileSmall;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.nork.threadpool.RenderJobType;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import com.sandu.common.LoginContext;

/**
 * @version V1.0
 * @Title: DesignPlanController.java
 * @Package com.nork.design.controller
 * @Description:设计模块-设计方案Controller
 * @createAuthor pandajun
 * @CreateDate 2015-07-03 17:09:51
 */
@Controller
@RequestMapping("/{style}/web/design/designPlan")
public class WebDesignPlanController {

	/*** 获取配置文件 webSocket.properties */
	private final static ResourceBundle webSocket = ResourceBundle.getBundle("config/webSocket");
	private static Logger logger = Logger.getLogger(WebDesignPlanController.class);
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private OptimizePlanService optimizePlanService;
	@Autowired
	private SaveRenderPicService saveRenderPicService;
	@Autowired
	private ThreadPoolManager uploadFileThreadPoolManager;
	

	/**
	 * 修改设计方案渲染图列表
	 * 
	 * @param planId
	 * @return
	 */
	@RequestMapping(value = "/designPlanRenderPicList")
	@ResponseBody
	public Object designPlanRenderPicList(@RequestParam(value = "planId", required = false) Integer planId,
			@RequestParam(value = "msgId", required = false) String msgId) {
		if (planId == null) {
			return new ResponseEnvelope<ResPic>(false, "planId为必填参数", msgId);
		}

		// 缩略图
		List<ResRenderPic> picSmallList = new ArrayList<>();
		ResRenderPicQO resRenderPicQO = new ResRenderPicQO();

		// TODO:创建一个fileKeyList用作查询条件
		List<String> fileKeyList = new ArrayList<>();
		fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
		fileKeyList.add("design.designPlan.render.video.cover");
		resRenderPicQO.setFileKeys(fileKeyList);

		resRenderPicQO.setBusinessId(planId);
		resRenderPicQO.setIsDeleted(0);
		resRenderPicQO.setOrder(" gmt_create desc ");
		picSmallList = resRenderPicService.selectListByFileKeys(resRenderPicQO);

		if (picSmallList.size() < 1) {
			return new ResponseEnvelope<ResRenderPic>(0, picSmallList, msgId);
		}
		for (ResRenderPic tempRenderPic : picSmallList) {
			// 原图
			ResRenderPic resRenderPic = null;
			// 原视频
			ResRenderVideo resRenderVideo = null;
			if (tempRenderPic.getPid() != null) {
				resRenderPic = resRenderPicService.get(tempRenderPic.getPid());
			} else {
				if (tempRenderPic.getId() != null) {
					resRenderVideo = resRenderVideoService.selectBySysTaskPicId(tempRenderPic.getId());
				}
			}

			if (resRenderPic != null) {
				if (resRenderPic.getRenderingType() != null) {
					if (RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType()
							|| RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()) {
						ResRenderPic rp = null;
						// 获取截图
						if (resRenderPic.getSysTaskPicId() == null) {
							logger.info("设计方案planId=" + planId + "获取渲染图片列表接口：sysTaskPicId is null!");
						} else {
							rp = resRenderPicService.get(resRenderPic.getSysTaskPicId());
						}
						if (rp == null) {
							// 截图不存在则取原图
							logger.info("设计方案planId=" + planId + "获取渲染图片列表接口：截图信息为空!");
							tempRenderPic.setPicPath(resRenderPic.getPicPath());
						} else {
							if (rp.getPicPath() == null) {
								logger.info("设计方案planId=" + planId + "获取渲染图片列表接口列表接口：图片id=" + rp.getId() + "图片路径为空！");
							} else {
								// tempRenderPic.setPicPath(rp.getPicPath());
								tempRenderPic.setOriginalPicPath(rp.getPicPath());
							}
						}
						tempRenderPic.setRenderingType(resRenderPic.getRenderingType());
						tempRenderPic.setBaseRenderId(resRenderPic.getId());
					} else {
						tempRenderPic.setOriginalPicPath(resRenderPic.getPicPath());
						tempRenderPic.setRenderingType(resRenderPic.getRenderingType());
						tempRenderPic.setBaseRenderId(resRenderPic.getId());
					}
				} else {
					tempRenderPic.setOriginalPicPath(resRenderPic.getPicPath());
					tempRenderPic.setRenderingType(resRenderPic.getRenderingType());
					tempRenderPic.setBaseRenderId(resRenderPic.getId());
				}
			}
			// 判断漫游视频是否存在
			if (resRenderVideo != null) {
				if (resRenderVideo.getRenderingType() != null) {
					if (RenderTypeCode.COMMON_VIDEO == resRenderVideo.getRenderingType()
							|| RenderTypeCode.HD_VIDEO == resRenderVideo.getRenderingType()) {
						ResRenderVideo rp = null;
						// 获取截图
						if (resRenderVideo.getSysTaskPicId() == null) {
							logger.error("设计方案planId=" + planId + "获取渲染视频列表接口：sysTaskPicId is null!");
						} else {
							rp = resRenderVideoService.get(resRenderVideo.getSysTaskPicId());
						}
						if (rp == null) {
							// // 截图不存在则取原图
							// logger.error("设计方案planId=" + planId
							// + "获取渲染视频列表接口：封面图信息为空!");
							// tempRenderPic.setPicPath(resRenderVideo.getVideoPath());
						} else {
							if (rp.getVideoPath() == null) {
								logger.error("设计方案planId=" + planId + "获取渲染视频列表接口列表接口：视频id=" + rp.getId() + "视频路径为空！");
							} else {
								// tempRenderPic.setPicPath(rp.getPicPath());
								tempRenderPic.setOriginalPicPath(rp.getVideoPath());
							}
						}
						tempRenderPic.setRenderingType(resRenderVideo.getRenderingType());
						tempRenderPic.setOriginalPicPath(resRenderVideo.getVideoPath());
						tempRenderPic.setBaseRenderId(resRenderVideo.getId());
					} else {
						tempRenderPic.setOriginalPicPath(resRenderVideo.getVideoPath());
						tempRenderPic.setRenderingType(resRenderVideo.getRenderingType());
						tempRenderPic.setBaseRenderId(resRenderVideo.getId());
					}
				} else {
					tempRenderPic.setOriginalPicPath(resRenderVideo.getVideoPath());
					tempRenderPic.setRenderingType(resRenderVideo.getRenderingType());
					tempRenderPic.setBaseRenderId(resRenderVideo.getId());
				}
			}
			// }
		}

		return new ResponseEnvelope<ResRenderPic>(picSmallList.size(), picSmallList, msgId);
	}


	/**
	 * 保存客户端渲染的设计方案图(720类型专用)
	 * 
	 * @param planId
	 *            渲染的方案ID
	 * @param templateId
	 *            方案应用到样板房ID
	 * @param sourcePlanId
	 *            源方案ID
	 * @param request
	 * @return object
	 */
	@RequestMapping("/saveDesignRenderPic")
	@ResponseBody
	public Object saveDesignRenderPic(String level, String planId, String msgId, Integer viewPoint, Integer scene,
			Integer isTurnOn, String renderingType, Integer taskId, Integer isChange, HttpServletRequest request,
			HttpServletResponse response, String token, Integer panoLevel, String roamJson, Integer opType,
			Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		String msg = "";
		logger.info("接收渲染图接口....");
		if (StringUtils.isBlank(planId)) {
			msg = "参数planId不能为空";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		// 验证登录参数 ->start
		logger.error("saveDesignRenderPic   token==" + token);
		LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(token);
		if (loginUser == null) {
			msg = "请登录系统.";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}

		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		if (taskId == null) {
			msg = "参数taskId不能为空";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		if (StringUtils.isBlank(renderingType)) {
			msg = "参数renderingType不能为空";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		if (RenderTypeCode.COMMON_720_LEVEL == Utils.getIntValue(renderingType)
				|| RenderTypeCode.ROAM_720_LEVEL == Utils.getIntValue(renderingType)) {// 4表示720度全景图
			if (StringUtils.isBlank(level)) {
				msg = "参数level不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}
			if (viewPoint == null) {
				msg = "参数viewPoint不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}

			if (scene == null) {
				msg = "参数scene不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}
			if (panoLevel == null) {
				msg = "参数panoLevel不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}
		} else {
			msg = "参数  renderingType 未知图片类型";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		DesignPlan designPlan = null;
		if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
			designPlan = designPlanService.get(Integer.parseInt(planId));
		} else {
			designPlan = optimizePlanService.getPlan(Integer.parseInt(planId));
		}
		/* 获得文件流 */
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
		boolean flag = true;
		String jobType = RenderJobType.RENDER_JOB_720;
		if (RenderTypeCode.ROAM_720_LEVEL == Utils.getIntValue(renderingType)) {
			jobType = RenderJobType.RENDER_JOB_N720;
		}

		RenderPicVO vo = this.createRenderPicVO720(designPlan, fileMap, viewPoint, scene, isTurnOn,
				Utils.getIntValue(renderingType), level, loginUser, taskId, panoLevel, roamJson, opType, templateId,
				sourcePlanId,userRenderType,isAuto);
		vo.setIsChange(isChange);
		ResponseEnvelope syncSaveResult = this.syncSaveRenderPic(saveRenderPicService, vo, jobType);// 异步任务

		// TODO : Check the upload state;

		/*
		 * boolean flag =
		 * designPlanService.saveRenderPic(designPlan,fileMap,viewPoint,scene,
		 * isTurnOn,Utils.getIntValue(renderingType),loginUser);
		 */
		/*
		 * if( !flag){ return new ResponseEnvelope<ResFileSmall>(false, "渲染失败", msgId);
		 * }
		 */

		// return new ResponseEnvelope<ResFileSmall>(syncSaveResult.isSuccess(),
		// syncSaveResult.getMessage(), msgId);
		return new ResponseEnvelope<ResFileSmall>(true, "图片保存中", msgId);
	}

	
	/**
	 * 照片级渲染图保存
	 * 
	 * @param request
	 * @return object
	 */
	@RequestMapping("/uploadPlanRender")
	@ResponseBody
	public Object uploadPlanRenderPicOfPhoto(String level, Integer planId, String msgId, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType, Integer taskId, Integer isChange,
			HttpServletRequest request, HttpServletResponse response, String authorization, Integer opType,
			Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
			
		}
		if (planId == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数planId不能为空", msgId);
		}
		if (StringUtils.isBlank(level)) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数level不能为空", msgId);
		}
		if (viewPoint == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数viewPoint不能为空", msgId);
		}

		if (scene == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数scene不能为空", msgId);
		}
		if (renderingType == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数renderingType不能为空", msgId);
		} else {
			if (RenderTypeCode.COMMON_PICTURE_LEVEL != renderingType) {
				return new ResponseEnvelope<ResFileSmall>(false, "未知渲染类型", msgId);
			} else {

			}
		}
		if (taskId == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数taskId不能为空", msgId);
		}
		// 验证登录参数 ->start
		LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorization);
		if (loginUser == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "请登录系统.", msgId);
		}
		// 验证登录参数 ->end
		DesignPlan designPlan = null;
		if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
			designPlan = designPlanService.get(planId);
		} else {
			designPlan = optimizePlanService.getPlan(planId);
		}
		String code = "code";
		if (designPlan == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "找不到该id=" + planId + "方案信息", msgId);
		} else {
			code = designPlan.getPlanCode();
		}
		Boolean renderSuccess = true;
		// 多文件上传使用request
		if (request instanceof MultipartHttpServletRequest) {
			// 获取文件列表并将物理文件保存到服务器中
			// 将HttpServletRequest对象转换为MultipartHttpServletRequest对象
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String fileName = null;

			List<Map> list = new ArrayList<Map>();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 照片级保存渲染图
			boolean flag = true;
			RenderPicVO vo = createRenderPicVO(designPlan, fileMap, viewPoint, scene, isTurnOn, renderingType, level,
					loginUser, taskId, opType, templateId, sourcePlanId,userRenderType, isAuto);
			vo.setIsChange(isChange);
			logger.info("设计方案id=" + designPlan.getId() + "异步保存照片级图片start");
			@SuppressWarnings("rawtypes")
			ResponseEnvelope syncSaveResult = syncSaveRenderPic(saveRenderPicService, vo,
					RenderJobType.RENDER_JOB_PHOTO);// 异步任务
			if (syncSaveResult != null && syncSaveResult.isSuccess() == false) {
				return new ResponseEnvelope<ResFileSmall>(false,
						syncSaveResult.getMessage() != null ? syncSaveResult.getMessage() : "", msgId);
			} else {
				return new ResponseEnvelope<ResFileSmall>(true, "保存渲染图中", msgId);
			}
		}
		return new ResponseEnvelope<ResFileSmall>(true, "保存渲染图中", msgId);
	}
	
	@ResponseBody
	@RequestMapping("/uploadPlanRenderVideo")
	public Object uploadPlanRenderVideo(String msgId, Integer planId, String renderingType, String authorization,
			Integer taskId, Integer isChange, HttpServletRequest request, HttpServletResponse response, Integer opType,
			Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		
		// 验证登录参数 ->start
		LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorization);
		if (loginUser == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "请登录系统.", msgId);
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证
		if (planId == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "planId不能为空", msgId);
		}
		if (StringUtils.isBlank(renderingType)) {
			return new ResponseEnvelope<ResFileSmall>(false, "renderingType不能为空", msgId);
		}
		MultipartRequest multipartRequest = null;
		if (request instanceof MultipartRequest) {
			multipartRequest = (MultipartRequest) request;

		}
		DesignPlan designPlan = null;
		if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
			designPlan = designPlanService.get(planId);
		} else {
			designPlan = optimizePlanService.getPlan(planId);
		}

		// 获得文件流
		// MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)
		// request;
		// Iterator<String> iter = multipartRequest.getFileNames();
		// boolean flag = true;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 6表示普通720度渲染视频
		// if(RenderTypeCode.COMMON_VIDEO == Utils.getIntValue(renderingType)){
		RenderPicVO vo = this.createRenderVideoVO(designPlan, fileMap, Utils.getIntValue(renderingType), loginUser,
				taskId, opType, templateId, sourcePlanId,userRenderType,isAuto);
		vo.setIsChange(isChange);
		ResponseEnvelope syncSaveResult = this.syncSaveRenderPic(saveRenderPicService, vo,
				RenderJobType.RENDER_JOB_VIDEO);
		// }else {
		// msg = "参数 renderingType 未知图片类型";
		// return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		// }
		return new ResponseEnvelope<ResFileSmall>(true, "视频保存中", msgId);
	}

	/** 封装720视频renderPicVO对象 **/
	private RenderPicVO createRenderVideoVO(DesignPlan designPlan, Map<String, MultipartFile> fileMap,
			Integer renderingType, LoginUser loginUser, Integer taskId, Integer opType, Integer templateId,
			Integer sourcePlanId,Integer userRenderType, Integer isAuto) {
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		RenderPicVO vo = new RenderPicVO();
		vo.setOpType(opType);
		vo.setDesignPlan(designPlan);
		vo.setFileMap(fileMap);
		vo.setRenderingType(renderingType);
		vo.setLoginUser(loginUser);
		vo.setTaskId(taskId);
		vo.setUserRenderType(userRenderType);
		sourcePlanId = sourcePlanId != null ? sourcePlanId : 0;
		vo.setSourcePlanId(sourcePlanId);
		if(isAuto == null) {
			isAuto = 0;
		}
		if (templateId != null) {
			vo.setTemplateId(templateId);
		}
		vo.setIsAuto(isAuto);

		return vo;
	}
	// TODO:封装720渲染全景图对象
		private RenderPicVO createRenderPicVO720(DesignPlan designPlan, Map<String, MultipartFile> fileMap,
				Integer viewPoint, Integer scene, Integer isTurnOn, Integer renderingType, String level,
				LoginUser loginUser, Integer taskId, Integer panoLevel, String roamJson, Integer opType, Integer templateId,
				Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
			if (opType == null) {
				opType = DesignPlanConstants.USER_RENDER;
			}
			RenderPicVO vo = new RenderPicVO();
			vo.setDesignPlan(designPlan);
			vo.setFileMap(fileMap);
			vo.setViewPoint(viewPoint);
			vo.setScene(scene);
			vo.setIsTurnOn(isTurnOn);
			vo.setRenderingType(renderingType);
			vo.setLoginUser(loginUser);
			vo.setLevel(level);
			vo.setTaskId(taskId);
			vo.setPanoLevel(panoLevel);
			vo.setRoamJson(roamJson);
			vo.setOpType(opType);
			vo.setUserRenderType(userRenderType);
			sourcePlanId = sourcePlanId != null ? sourcePlanId : 0;
			vo.setSourcePlanId(sourcePlanId);
			if (templateId != null) {
				vo.setTemplateId(templateId);
			}
			if(isAuto == null) {
				isAuto = 0;
			}
			vo.setIsAuto(isAuto);
			return vo;
		}
		

		private RenderPicVO createRenderPicVO(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
				Integer scene, Integer isTurnOn, Integer renderingType, String level, LoginUser loginUser, Integer taskId,
				Integer opType, Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
			logger.info("updateTheStateForAutoRender planId=" + designPlan.getId() + ",templateId=" + templateId
					+ ",sourcePlanId=" + sourcePlanId);
			if (opType == null) {
				opType = DesignPlanConstants.USER_RENDER;
			}
			RenderPicVO vo = new RenderPicVO();
			vo.setDesignPlan(designPlan);
			vo.setFileMap(fileMap);
			vo.setViewPoint(viewPoint);
			vo.setScene(scene);
			vo.setIsTurnOn(isTurnOn);
			vo.setRenderingType(renderingType);
			vo.setLoginUser(loginUser);
			vo.setLevel(level);
			vo.setTaskId(taskId);
			vo.setOpType(opType);
			vo.setUserRenderType(userRenderType);
			sourcePlanId = sourcePlanId != null ? sourcePlanId : 0;
			vo.setSourcePlanId(sourcePlanId);
			if (templateId != null) {
				vo.setTemplateId(templateId);
			}
			if(isAuto == null) {
				isAuto = 0;
			}
			vo.setIsAuto(isAuto);
			return vo;
		}
		@SuppressWarnings("rawtypes")
		private ResponseEnvelope syncSaveRenderPic(SaveRenderPicService service, RenderPicVO picModel, String jobType) {
			logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "进入syncSaveRenderPic方法");
			ResponseEnvelope result = new ResponseEnvelope();
			ThreadPool threadPool = uploadFileThreadPoolManager.getThreadPool();// 取得线程池实列
			logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "创建job任务start");
			// 创建同步保存渲染图的任务
			SaveRenderPicJob job = new SaveRenderPicJob(service, picModel, jobType);
			logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "创建job任务end");
			logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "提交线程任务start");
			Future<Boolean> future = threadPool.submit(job);
			logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "提交线程任务end");
			return result;
		}
		
	
}
