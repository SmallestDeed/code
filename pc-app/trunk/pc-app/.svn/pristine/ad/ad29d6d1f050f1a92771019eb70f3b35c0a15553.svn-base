package com.nork.design.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.LoginUser;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.RenderPicVO;
import com.nork.design.service.DesignPlanAutoRenderResouceService;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.SaveRenderPicService;
import com.nork.mobile.service.MobileAutoRenderAndOneKeyCopyService;
import com.nork.pay.service.PayOrderService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.service.SysTaskService;
import com.nork.threadpool.RenderJobType;

@Service("saveRenderPicService")
public class SaveRenderPicServiceImpl implements SaveRenderPicService {
	private static Logger logger = Logger.getLogger(SaveRenderPicServiceImpl.class);

	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private SysTaskService sysTaskService;
	@Autowired
	private MobileAutoRenderAndOneKeyCopyService mobileAutoRenderAndOneKeyCopyService;

	@Autowired
	private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;

	@Autowired
	DesignPlanAutoRenderResouceService designPlanAutoRenderResouceService;

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private DesignPlanAutoRenderService designPlanAutoRenderService;
	@Autowired
	private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean saveRenderPicOf720(RenderPicVO renderPic) {

		DesignPlan designPlan = renderPic.getDesignPlan();
		Map<String, MultipartFile> fileMap = renderPic.getFileMap();
		Integer viewPoint = renderPic.getViewPoint();
		Integer scene = renderPic.getScene();
		Integer isTurnOn = renderPic.getIsTurnOn();
		Integer renderingType = renderPic.getRenderingType();
		LoginUser loginUser = renderPic.getLoginUser();
		Integer taskId = renderPic.getTaskId();
		Integer panoLevel = renderPic.getPanoLevel();
		String roamJson = renderPic.getRoamJson();
		String cameraLocation = renderPic.getCameraLocation();
		Integer needShear = renderPic.getNeedShear();
		String jobType = RenderJobType.RENDER_JOB_720;
		if (RenderTypeCode.ROAM_720_LEVEL == renderingType) {
			jobType = RenderJobType.RENDER_JOB_N720;
		}
		renderPic.setJobType(jobType);
		Integer sourcePlanId = renderPic.getSourcePlanId();
		Integer templateId = renderPic.getTemplateId();

		Integer resPicId = 0;
		if (renderPic.getOpType().intValue() == 0) {
			resPicId = designPlanAutoRenderResouceService.saveRenderPicOf720(designPlan, fileMap, viewPoint, scene,
					isTurnOn, renderingType, loginUser, taskId, panoLevel, roamJson, sourcePlanId, templateId, cameraLocation);
		} else {
			resPicId = designPlanService.saveRenderPicOf720(designPlan, fileMap, viewPoint, scene, isTurnOn,
					renderingType, loginUser, taskId, panoLevel, roamJson, sourcePlanId, templateId, cameraLocation, needShear);
		}
		// Integer resPicId =
		// designPlanService.saveRenderPicOf720(designPlan,fileMap,viewPoint,scene,isTurnOn,renderingType,loginUser,taskId);
		// Integer resPicId =
		// designPlanService.saveRenderPicOf720(designPlan,fileMap,viewPoint,scene,isTurnOn,renderingType,loginUser,taskId,panoLevel,roamJson,sourcePlanId,templateId);
		String successMsg = "图片保存状态：保存成功";
		String renderErroMsg = "异步保存720渲染视频过程失败，已退款至余额";
		boolean flag = uploadPicFileCallBack(resPicId, renderPic, successMsg, renderErroMsg);
		if (!flag) {
			throw new RuntimeException("渲染图保存失败!");
		}
		return flag;
	}

	public boolean saveRenderPicOfPhoto(RenderPicVO renderPic) {
		DesignPlan designPlan = renderPic.getDesignPlan();
		Map<String, MultipartFile> fileMap = renderPic.getFileMap();
		Integer viewPoint = renderPic.getViewPoint();
		Integer scene = renderPic.getScene();
		Integer isTurnOn = renderPic.getIsTurnOn();
		Integer renderingType = renderPic.getRenderingType();
		String level = renderPic.getLevel();
		Integer taskId = renderPic.getTaskId();
		LoginUser loginUser = renderPic.getLoginUser();
		Integer sourcePlanId = renderPic.getSourcePlanId();
		Integer templateId = renderPic.getTemplateId();
		Integer picId = 0;
		if (renderPic.getOpType().intValue() == 0) {
			picId = designPlanAutoRenderResouceService.saveRenderPicOfPhoto(designPlan, fileMap, viewPoint, scene,
					isTurnOn, renderingType, level, loginUser, taskId, sourcePlanId, templateId);
		} else {
			picId = designPlanService.saveRenderPicOfPhoto(designPlan, fileMap, viewPoint, scene, isTurnOn,
					renderingType, level, loginUser, taskId, sourcePlanId, templateId);
		}
		// Integer picId =
		// designPlanService.saveRenderPicOfPhoto(designPlan,fileMap,viewPoint,scene,isTurnOn,renderingType,level,loginUser,taskId,sourcePlanId,templateId);

		logger.info("picId=" + picId);
		String successMsg = "图片保存状态：保存成功";
		String renderErroMsg = "异步保存照片级渲染图片过程失败，已退款至余额";
		renderPic.setJobType(RenderJobType.RENDER_JOB_PHOTO);
		boolean flag = uploadPicFileCallBack(picId, renderPic, successMsg, renderErroMsg);
		return flag;
	}

	/**
	 * 保存720渲染视频 add by yangzhun
	 * 
	 * @param renderPic
	 * @return
	 */
	@Override
	public boolean saveRenderPicOfVideo(RenderPicVO renderPic) {
		boolean flag = false;
		DesignPlan designPlan = renderPic.getDesignPlan();
		Map<String, MultipartFile> fileMap = renderPic.getFileMap();
		Integer viewPoint = renderPic.getViewPoint();
		Integer scene = renderPic.getScene();
		Integer isTurnOn = renderPic.getIsTurnOn();
		Integer renderingType = renderPic.getRenderingType();
		LoginUser loginUser = renderPic.getLoginUser();
		Integer taskId = renderPic.getTaskId();
		Integer sourcePlanId = renderPic.getSourcePlanId();
		Integer templateId = renderPic.getTemplateId();

		// Integer resVideoId =
		// designPlanService.saveRenderPicOfVideo(designPlan,fileMap,renderingType,loginUser,taskId,sourcePlanId,templateId);
		Integer resVideoId = 0;
		if (renderPic.getOpType().intValue() == 0) {
			resVideoId = designPlanAutoRenderResouceService.saveRenderPicOfVideo(designPlan, fileMap, renderingType,
					loginUser, taskId, sourcePlanId, templateId);
		} else {
			resVideoId = designPlanService.saveRenderPicOfVideo(designPlan, fileMap, renderingType, loginUser, taskId,
					sourcePlanId, templateId);
		}
		String msg = "渲染视频保存状态：保存成功";
		String renderErroMsg = "异步保存720渲染视频过程失败，已退款至余额";
		// renderPic.setOpType(1);
		renderPic.setJobType(RenderJobType.RENDER_JOB_VIDEO);
		flag = uploadPicFileCallBack(resVideoId, renderPic, msg, renderErroMsg);
		return flag;
	}

	/**
	 * 更新任务状态 add by yanghz
	 */
	private void updateTaskState(SysTask sysTask, String msg, Integer status) {
		if (SysTaskStatus.RENDER_FAILD.equals(sysTask.getState())
				|| SysTaskStatus.END_OF_RENDER.equals(sysTask.getState())) {
			logger.debug("local render task id=" + sysTask.getId() + " state=" + sysTask.getState()
					+ ", forbid to change the task state!");
		} else {
			SysTask task = new SysTask();
			task.setId(sysTask.getId());
			task.setGmtModified(new Date());
			if (org.apache.commons.lang3.StringUtils.isNotBlank(sysTask.getRemark())) {
				task.setRemark(sysTask.getRemark() + "," + msg);
			} else {
				task.setRemark(msg);
			}
			task.setState(status);
			sysTaskService.update(task);
		}
	}

	/**
	 * 产品替换渲染更新任务状态
	 * 
	 */
	private boolean updateTaskStateByTaskId(RenderPicVO renderPic) {
		boolean flag = true;
		Integer taskId = renderPic.getTaskId();
		AutoRenderTask arts = designPlanAutoRenderMapper.getRenderTaskById(taskId);
		SysUser sysUser = sysUserService.get(arts.getOperationUserId());
		LoginUser loginUser = sysUser.toLoginUser();
		logger.error("renderPic.getJobType()  ====>    " + renderPic.getJobType());
		DesignPlan designPlan = renderPic.getDesignPlan();
		Integer planId = designPlan.getId();
		Integer designPlanSceneId = null;
		AutoRenderTaskState taskState = new AutoRenderTaskState();
		taskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
		if (RenderJobType.RENDER_JOB_720.equals(renderPic.getJobType())) {
			taskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
		} else if (RenderJobType.RENDER_JOB_N720.equals(renderPic.getJobType())) {
			taskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
		} else if (RenderJobType.RENDER_JOB_PHOTO.equals(renderPic.getJobType())) {
			taskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
		} else if (RenderJobType.RENDER_JOB_VIDEO.equals(renderPic.getJobType())) {
			taskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
		}
		taskState.setTaskId(taskId);
		if (arts.getTaskType() == 0) {
			designPlanSceneId = mobileAutoRenderAndOneKeyCopyService.transformAndCopyPlan(planId, loginUser);
			logger.error("designPlanSceneId====>" + designPlanSceneId);
			taskState.setBusinessId(designPlanSceneId);
			AutoRenderTaskState autoRenderTaskState = designPlanAutoRenderService.getStateByTaskId(taskId);
			logger.error("autoRenderTaskState  ====>    " + autoRenderTaskState.getId());
			long startTime = autoRenderTaskState.getGmtCreate().getTime();
			long endTime = System.currentTimeMillis();
			long spendTimeLong = endTime -startTime;
			int secondTotal=(int) (spendTimeLong/1000);
			int min=secondTotal/60;
			int second=secondTotal%60;
			taskState.setRenderTimeConsuming(min+"分"+second+"秒");
			logger.error("taskState 11111111111111 ====>    " + taskState.getBusinessId());
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanSceneId);
			taskState.setNewDesignCode(designPlanRenderScene.getPlanCode());
		}
		designPlanAutoRenderMapper.updateTaskStateByTaskId(taskState);
		logger.error("taskState  ====>    " + taskState.getBusinessId());
		return flag;

	}

	private boolean uploadPicFileCallBack(Integer resPicId, RenderPicVO renderPic, String successMsg, String failMsg) {
		boolean flag = true;
		Integer taskId = renderPic.getTaskId();
		logger.error("uploadPicFileCallBack==>" + taskId + "optype=" + renderPic.getOpType().intValue());
		if (renderPic.getIsAuto().intValue() == 1) {//taskId != null && taskId > 0
			flag = updateTaskStateByTaskId(renderPic);
		} else {
			flag = updateTheStateForUserRender(resPicId, renderPic, successMsg, failMsg);
		}

		return flag;
	}

	private boolean updateTheStateForUserRender(Integer resPicId, RenderPicVO renderPic, String successMsg,
			String failMsg) {

		boolean flag = false;
		if (resPicId != null && resPicId > 0) {
			flag = true;
			// 推送保存成功消息
			if (renderPic.getTaskId() != null) {
				SysTask sysTask = sysTaskService.get(renderPic.getTaskId());
				if (sysTask != null) {
					// 更新任务状态为：渲染结束
					updateTaskState(sysTask, successMsg, SysTaskStatus.END_OF_RENDER);
					try {
						Integer messageid = designPlanService.sendRenderMessage(sysTask, 2, resPicId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					logger.debug("taskId=" + sysTask.getId() + "find nothing or already deleted");
				}
			}
		} else {
			flag = false;
			if (renderPic.getTaskId() != null) {
				SysTask sysTask = sysTaskService.get(renderPic.getTaskId());
				if (sysTask != null) {
					// 退款
					payOrderService.renderRefund(sysTask, failMsg);
					// 更新任务状态
					updateTaskState(sysTask, failMsg, SysTaskStatus.RENDER_FAILD);
					// 推送保存失败消息
					try {
						logger.debug("推送保存失败消息");
						Integer messageid = designPlanService.sendRenderMessage(sysTask, 1, null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					logger.debug("taskId=" + sysTask.getId() + "find nothing or already deleted");
				}
			}

		}
		logger.info("flag=" + resPicId);
		return flag;

	}

	//
	private boolean updateTheStateForAutoRender(RenderPicVO renderPic) {
		boolean flag = true;
		Integer planId = renderPic.getDesignPlan().getId();
		Integer templateId = renderPic.getTemplateId();
		Integer sourcePlanId = renderPic.getSourcePlanId();
		AutoRenderTaskState taskState = new AutoRenderTaskState();
		taskState = designPlanAutoRenderMapper.getTaskStateByPlanIdAndTemplateId(sourcePlanId, templateId);
		String jobType = renderPic.getJobType();
		taskState.setBusinessId(planId);
		switch (jobType) {
		case RenderJobType.RENDER_JOB_PHOTO:
			if (2 == taskState.getRenderVideo() && 2 == taskState.getRender720() && 2 == taskState.getRenderN720()) {
				taskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());// 成功
			}
			taskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
			break;
		case RenderJobType.RENDER_JOB_720:
			if (2 == taskState.getRenderVideo() && 2 == taskState.getRenderPic() && 2 == taskState.getRenderN720()) {
				taskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
			}
			taskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
			break;
		case RenderJobType.RENDER_JOB_N720:
			if (2 == taskState.getRenderVideo() && 2 == taskState.getRenderPic() && 2 == taskState.getRender720()) {
				taskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
			}
			taskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
			break;
		case RenderJobType.RENDER_JOB_VIDEO:
			if (2 == taskState.getRenderN720() && 2 == taskState.getRenderPic() && 2 == taskState.getRender720()) {
				taskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
			}
			taskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
			break;
		}
		taskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
		taskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
		taskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
		taskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
		designPlanAutoRenderMapper.updateAutoRenderTaskState(taskState);
		return flag;
	}

	@Override
	public void saveTaskBussinessId(AutoRenderTaskState taskState) {
		// TODO Auto-generated method stub
		designPlanAutoRenderMapper.updateTaskStateByTaskId(taskState);
	}

	@Override
	public AutoRenderTaskState getStateByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		return designPlanAutoRenderMapper.getStateByTaskId(taskId);
	}

	@Override
	public DesignPlanRenderScene getDesignPlanRenderScene(Integer businessId) {
		// TODO Auto-generated method stub
		return designPlanRenderSceneMapper.get(businessId);
	}

}
