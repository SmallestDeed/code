package com.nork.design.service.impl;

import com.google.gson.Gson;
import com.nork.common.constant.AutoRenderTaskConstant;
import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.DesignPlanRecommendedMapperV2;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.dao.FullHouseDesignPlanMapper;
import com.nork.design.model.*;
import com.nork.design.service.*;
import com.nork.mobile.service.MobileAutoRenderAndOneKeyCopyService;
import com.nork.pay.service.PayOrderService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.common.SysUserMessageConstants;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.SysUserMessageService;
import com.nork.system.service.SysUserService;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.service.SysTaskService;
import com.nork.threadpool.RenderJobType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service("saveRenderPicService")
public class SaveRenderPicServiceImpl implements SaveRenderPicService {
	private static Logger logger = Logger.getLogger(SaveRenderPicServiceImpl.class);
	public static final String SYSTEM_DOMAIN_NAME = Utils.getPropertyName("app","app.system.url","https://system.sanduspace.com");
	public static final String CORE_DOMAIN_NAME = Utils.getPropertyName("app","app.core.url","https://core.sanduspace.com");
	private final Gson GSON = new Gson();
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
	@Autowired
	private SysUserMessageService sysUserMessageService;
	@Autowired
    private DesignPlanRecommendedMapperV2 designPlanRecommendedMapperV2;
	@Autowired
	private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;

	@Autowired
	private FullHouseDesignPlanService fullHouseDesignPlanService;



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
		Integer needShear = renderPic.getNeedShear();
		String jobType = RenderJobType.RENDER_JOB_720;
		if (RenderTypeCode.ROAM_720_LEVEL == renderingType) {
			jobType = RenderJobType.RENDER_JOB_N720;
		}
		renderPic.setJobType(jobType);
		Integer sourcePlanId = renderPic.getSourcePlanId();
		Integer templateId = renderPic.getTemplateId();
		String cameraLocation = renderPic.getCameraLocation();

		Integer resPicId = 0;
		if (renderPic.getOpType().intValue() == 0) {
			resPicId = designPlanAutoRenderResouceService.saveRenderPicOf720(designPlan, fileMap, viewPoint, scene,
					isTurnOn, renderingType, loginUser, taskId, panoLevel, roamJson, sourcePlanId, templateId, needShear,cameraLocation);
		} else {
			resPicId = designPlanService.saveRenderPicOf720(designPlan, fileMap, viewPoint, scene, isTurnOn,
					renderingType, loginUser, taskId, panoLevel, roamJson, sourcePlanId, templateId, needShear,cameraLocation);
		}
		// Integer resPicId =
		// designPlanService.saveRenderPicOf720(designPlan,fileMap,viewPoint,scene,isTurnOn,renderingType,loginUser,taskId);
		// Integer resPicId =
		// designPlanService.saveRenderPicOf720(designPlan,fileMap,viewPoint,scene,isTurnOn,renderingType,loginUser,taskId,panoLevel,roamJson,sourcePlanId,templateId);
		String successMsg = "图片保存状态：保存成功";
		String renderErroMsg = "异步保存720渲染视频过程失败，已退款至余额";
		boolean flag = uploadPicFileCallBack(resPicId, renderPic, successMsg, renderErroMsg);
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
	 * 渲染更新任务状态
	 * 
	 */
	private boolean updateTaskStateByTaskId(RenderPicVO renderPic) {
		boolean flag = true;
		try {

			Integer taskId = renderPic.getTaskId();
			AutoRenderTask art = designPlanAutoRenderMapper.getRenderTaskById(taskId);
			Integer platformId = art.getPlatformId();
			SysUser sysUser = sysUserService.get(art.getOperationUserId());
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
			if (art.getTaskType() == 0) {
				designPlanSceneId = mobileAutoRenderAndOneKeyCopyService.transformAndCopyPlan(planId, loginUser,platformId,renderPic);
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

		} catch (Exception e) {
			logger.error("SaveRenderPicServiceImpl  updateTaskStateByTaskId =====> exception:"+e);
			flag = false;
		}

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

	@Override
	public void handleSuccessfulRender(Integer taskId) {


			/*修改全屋户型渲染状态信息表start*/
		int baseHousePicFullHousePlanRelId;
		try {
			baseHousePicFullHousePlanRelId = designPlanAutoRenderService.updateBaseHousePicFullHousePlanRelSuccessByTaskId(taskId);
			if(0 == baseHousePicFullHousePlanRelId){
				logger.warn("修改全屋户型渲染状态信息失败"+taskId);
			}
			logger.info("修改全屋户型渲染状态信息成功"+baseHousePicFullHousePlanRelId);
		}catch (Exception e){
			logger.error("修改全屋户型渲染状态信息数据异常"+e);

		}
			/*修改全屋户型渲染状态信息表end*/

		//获取渲染成功的任务
		AutoRenderTaskState taskState = designPlanAutoRenderMapper.getStateByTaskId(taskId);

		if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE.equals(taskState.getPlanHouseType())) {
			//如果是全屋方案，判断子任务数量：
			// 		子任务不为最后一个则不生成消息，改变主任务中的子任务数量
			// 		子任务为最有一个，则改变主任务状态为完成，插入渲染成功的消息，并回调生成主方案及子方案
			this.handleFullHousePlanTask(taskState);
			//改变taskState为主任务
//			taskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());

		} else if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE_NEW.equals(taskState.getPlanHouseType())) {
			//处理新的720逻辑
			this.handleFullHousePlanNewTask(taskState);

		} else {
			//如果是单空间方案，就生成消息
			this.createRenderSuccessMessage(taskState);

			//渲染成功后小程序发送消息模板
			try {
				miniprogramMessageTemplate(taskState);
			} catch (Exception e) {
				logger.error("小程序发送模板消息 ===>exception：",e);
			}
		}

	}

	/**
	 * 处理新720渲染成功逻辑
	 * 分三种情况1：第一次创建全屋方案，2：修改全屋方案（新增或替换子方案），3：替换单个子方案生成新的全屋方案
	 * @param taskState
	 */
	private void handleFullHousePlanNewTask(AutoRenderTaskState taskState) {
		//获取全屋制作的动作
		Integer fullHousePlanAction = taskState.getFullHousePlanAction();

		if (AutoRenderTaskConstant.TASK_TYPE_REPLACE.equals(taskState.getTaskType())) {
			if (AutoRenderTaskConstant.FULL_HOUSE_PLAN_ACTION_UPDATE.equals(fullHousePlanAction)){
				//处理修改全屋方案的逻辑
				this.handleFullHouseActionUpdate(taskState);
			}else if (AutoRenderTaskConstant.FULL_HOUSE_PLAN_ACTION_UPDATE_NEW.equals(fullHousePlanAction)) {
				//替换渲染生成新的全屋方案，与替换单个子方案生成新的全屋方案的逻辑应该一致
				this.handleFullHouseActionUpdateNew(taskState);
			}else {
				logger.error("处理新720渲染成功逻辑 ====> 类型无法识别 fullHousePlanAction=" + fullHousePlanAction,
						new GeneratePanoramaException("fullHousePlanAction类型无法识别"));
				return;
			}
		} else {
			//全屋装进我家


			if (AutoRenderTaskConstant.FULL_HOUSE_PLAN_ACTION_CREATE.equals(fullHousePlanAction)) {
				//处理第一次创建全屋的逻辑
				this.handleFullHouseActionCreate(taskState);

			} else if (AutoRenderTaskConstant.FULL_HOUSE_PLAN_ACTION_UPDATE.equals(fullHousePlanAction)) {
				//处理修改全屋方案的逻辑
				this.handleFullHouseActionUpdate(taskState);

			} else if (AutoRenderTaskConstant.FULL_HOUSE_PLAN_ACTION_UPDATE_NEW.equals(fullHousePlanAction)) {
				//处理替换单个子方案生成新的全屋方案的逻辑
				this.handleFullHouseActionUpdateNew(taskState);

			} else {
				logger.error("处理新720渲染成功逻辑 ====> 类型无法识别 fullHousePlanAction=" + fullHousePlanAction,
						new GeneratePanoramaException("fullHousePlanAction类型无法识别"));
				return;
			}
		}
	}


	/**
	 * 处理单个子方案替换生成新的全屋方案的逻辑
	 * 子方案渲染完成后获取到原来全屋方案的其他子方案的效果图方案id，然后调用insertFullHousePlan的方法生成全屋方案，
	 * 然后更新主方案的全屋方案信息及渲染状态等
	 * @param taskState
	 */
	private void handleFullHouseActionUpdateNew(AutoRenderTaskState taskState) {
		//调用生成全屋方案
		//String resultStr = createFullHousePlanBaseOnOldPlan(taskState);
		AutoRenderTaskState tempTaskState = new AutoRenderTaskState();
		BeanUtils.copyProperties(taskState, tempTaskState);
		String uuid = updateFullHousePlan(tempTaskState);
		//logger.error("处理全屋方案任务 --------- resultStr="+resultStr);
		logger.error("处理全屋方案任务 --------- uuid="+uuid);
		AutoRenderTaskState autoRenderTaskState = new AutoRenderTaskState();
		autoRenderTaskState.setTaskId(taskState.getMainTaskId());
		autoRenderTaskState.setGmtCreate(taskState.getGmtCreate());
		autoRenderTaskState.setNewFullHousePlanId(taskState.getNewFullHousePlanId());
		autoRenderTaskState.setFullHousePlanId(taskState.getFullHousePlanId());
		String renderTypesStr = taskState.getRenderTypesStr();

		if (StringUtils.isNotBlank(uuid)) {
			//远程调用生成的结果不为空，截取，添加到state表中
			/*String[] split = resultStr.split(",");
			if (split.length > 1) {
				Integer newFullHousePlanId = Integer.parseInt(split[0]);
				//把新的全屋方案id和UUID存起来
				autoRenderTaskState.setNewFullHousePlanId(newFullHousePlanId);
				autoRenderTaskState.setFullHousePlanUUID(split[1]);
			}*/
			autoRenderTaskState.setFullHousePlanUUID(uuid);
			//改变主任务的状态为渲染成功，
			autoRenderTaskState.setBusinessId(taskState.getBusinessId());
			autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
			//改变主任务的状态为渲染成功，
			if ("1".equals(renderTypesStr)) {
				autoRenderTaskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
			} else if ("2".equals(renderTypesStr)) {
				autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
			} else if ("3".equals(renderTypesStr)) {
				autoRenderTaskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
			} else if ("4".equals(renderTypesStr)){
				autoRenderTaskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
			}
		} else {
			//生成全屋方案返回的结果为空的时候，设置失败的状态，远程调用没有问题的话不会走这里
			autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
			if ("1".equals(renderTypesStr)) {
				autoRenderTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
			} else if ("2".equals(renderTypesStr)) {
				autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
			} else if ("3".equals(renderTypesStr)) {
				autoRenderTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
			} else if ("4".equals(renderTypesStr)){
				autoRenderTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
			}
			autoRenderTaskState.setRemark("制作全屋方案，调用core更新生成方案失败，result is empty");

		}
		//公共方案添加渲染时间及更新主任务状态
		this.updateTaskStateTimeConsum(autoRenderTaskState);
		//插入消息及小程序发送模板消息（现在插入的是子任务渲染完成的消息，小程序发送消息参数是替换之前方案的id，

		// 如果有变化发送消息模板的逻辑也要修改）
		insertAndSendMessage(taskState);
	}

	/**
	 * 根据旧的全屋方案创建新的全屋方案
	 * @param taskState
	 * @return
	 */
	private String createFullHousePlanBaseOnOldPlan(AutoRenderTaskState taskState) {
		//获取替换子方案的全屋方案id
		Integer oldFullHousePlanId = taskState.getFullHousePlanId();
		if (oldFullHousePlanId == null || oldFullHousePlanId <= 0) {
			logger.error("根据旧的全屋方案创建新的全屋方案 =====> oldFullHousePlanId="+oldFullHousePlanId);
			return null;
		}

		//查询主任务state
		AutoRenderTaskState mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
		//构造insert的对象
		FullHouseDesignPlanAdd fullHouseDesignPlanAdd = new FullHouseDesignPlanAdd();
		fullHouseDesignPlanAdd.setDesignPlanName(mainTaskState.getDesignName());
		fullHouseDesignPlanAdd.setDesignPlanStyleId(0);
		fullHouseDesignPlanAdd.setUserId(mainTaskState.getOperationUserId());
		fullHouseDesignPlanAdd.setFullHousePlanSourceId(mainTaskState.getFullHousePlanId());
		//找出原全屋方案的所有子方案
		List<FullHouseDesignPlanDetail> detailList = fullHouseDesignPlanMapper.getFullHousePlanDetailsList(oldFullHousePlanId);
		//找出原全屋子方案除了要替换的其他方案数据
		List<FullHouseDesignPlanDetail> detailListNew = detailList.stream().filter(detail ->
				!taskState.getPreRenderSceneId().equals(detail.getRenderScenePlanId()))
				.collect(Collectors.toList());

		//构造效果图方案id集合
		List<Integer> livingDiningRoom = new ArrayList<>();
		List<Integer> bedroom = new ArrayList<>();
		List<Integer> kitchen = new ArrayList<>();
		List<Integer> toilet = new ArrayList<>();
		List<Integer> schoolroom = new ArrayList<>();
		//把当前方案设置到参数中
		switch (taskState.getSpaceFunctionId()) {
			case AutoRenderTaskConstant.SPACE_TYPE_LIVING_DINING_ROOM :
				livingDiningRoom.add(taskState.getBusinessId());
				break;
			case AutoRenderTaskConstant.SPACE_TYPE_BEDROOM :
				bedroom.add(taskState.getBusinessId());
				break;
			case AutoRenderTaskConstant.SPACE_TYPE_KITCHEN :
				kitchen.add(taskState.getBusinessId());
				break;
			case AutoRenderTaskConstant.SPACE_TYPE_TOILET :
				toilet.add(taskState.getBusinessId());
				break;
			case AutoRenderTaskConstant.SPACE_TYPE_SCHOOLROOM :
				schoolroom.add(taskState.getBusinessId());
				break;
		}
		//把旧的方案设置到参数中
		for (FullHouseDesignPlanDetail fullHouseDesignPlanDetail : detailListNew) {
			//封装全屋方案insert对象所需的各种户型对应的效果图id
			switch (fullHouseDesignPlanDetail.getSpaceType()) {
				case AutoRenderTaskConstant.SPACE_TYPE_LIVING_DINING_ROOM :
					livingDiningRoom.add(fullHouseDesignPlanDetail.getRenderScenePlanId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_BEDROOM :
					bedroom.add(fullHouseDesignPlanDetail.getRenderScenePlanId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_KITCHEN :
					kitchen.add(fullHouseDesignPlanDetail.getRenderScenePlanId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_TOILET :
					toilet.add(fullHouseDesignPlanDetail.getRenderScenePlanId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_SCHOOLROOM :
					schoolroom.add(fullHouseDesignPlanDetail.getRenderScenePlanId());
					break;
			}
		}
		fullHouseDesignPlanAdd.setLivingDiningRoom(livingDiningRoom);
		fullHouseDesignPlanAdd.setBedroom(bedroom);
		fullHouseDesignPlanAdd.setKitchen(kitchen);
		fullHouseDesignPlanAdd.setToilet(toilet);
		fullHouseDesignPlanAdd.setSchoolroom(schoolroom);

		String url = CORE_DOMAIN_NAME+"/v1/core/fullHouse/addFullHouseDesignPlan";
		logger.error("远程调用生成全屋方案 ---------- url="+url);
		logger.error("远程调用生成全屋方案 ---------- fullHouseDesignPlanAdd="+fullHouseDesignPlanAdd);
		String result = Utils.doPost(url, fullHouseDesignPlanAdd);
		logger.error("远程调用生成全屋方案 ---------- result="+result);
		if (StringUtils.isBlank(result)) {
			return null;
		}
		ResponseEnvelope responseEnvelope = GSON.fromJson(result, ResponseEnvelope.class);
		logger.error("远程调用生成全屋方案 ---------- responseEnvelope="+responseEnvelope);
		if (responseEnvelope.getObj() != null) {
			return responseEnvelope.getObj().toString();
		}
		return null;
	}

	/**
	 * 处理修改全屋方案的逻辑
	 * 主方案已经存在，更新完主方案把uuid回填到主方案即可
	 * @param taskState
	 */
	private void handleFullHouseActionUpdate(AutoRenderTaskState taskState) {
		//创建主任务修改对象
		AutoRenderTaskState mainTaskState = new AutoRenderTaskState();
		mainTaskState.setTaskId(taskState.getMainTaskId());
		mainTaskState.setGmtCreate(taskState.getGmtCreate());
		//调用update全屋方案的接口
		String uuid = this.updateFullHousePlan(taskState);
		if (StringUtils.isNotBlank(uuid)) {
			mainTaskState.setFullHousePlanUUID(uuid);
		} else {
			mainTaskState.setRemark("调用core更新全屋方案失败，uuid is empty");
		}
		mainTaskState.setNewFullHousePlanId(taskState.getNewFullHousePlanId());
		mainTaskState.setFullHousePlanId(taskState.getFullHousePlanId());
		//生成时间及更新主任务state表
		this.updateTaskStateTimeConsum(mainTaskState);
		//生成子任务渲染成功的消息，子任务渲染完成小程序发送消息模板
		this.insertAndSendMessage(taskState);
	}

	/**
	 * 处理第一次创建全屋方案的逻辑
	 * 调用core的update全屋方案接口，并改变主任务渲染状态，子任务在之前已经处理过
	 * @param taskState
	 */
	private void handleFullHouseActionCreate(AutoRenderTaskState taskState) {
		// 获取主任务状态
		AutoRenderTaskState mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
		//调用update全屋方案的接口
		String uuid = this.updateFullHousePlan(taskState);

		Integer subtaskCount = mainTaskState.getSubtaskCount();
		if (subtaskCount == null){
			mainTaskState.setBusinessId(taskState.getBusinessId());
			String renderTypesStr = taskState.getRenderTypesStr();
			if (StringUtils.isNotBlank(uuid)) {
				mainTaskState.setFullHousePlanUUID(uuid);
				//改变主任务的状态为渲染成功，
				mainTaskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
				if ("1".equals(renderTypesStr)) {
					mainTaskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
				} else if ("2".equals(renderTypesStr)) {
					mainTaskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
				} else if ("3".equals(renderTypesStr)) {
					mainTaskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
				} else if ("4".equals(renderTypesStr)){
					mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
				}

			} else {
				//更新全屋方案返回的结果为空的时候，设置失败的状态，远程调用没有问题的话不会走这里
				logger.error("远程调用更新全屋方案有误！！！");
				mainTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
				if ("1".equals(renderTypesStr)) {
					mainTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
				} else if ("2".equals(renderTypesStr)) {
					mainTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
				} else if ("3".equals(renderTypesStr)) {
					mainTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
				} else if ("4".equals(renderTypesStr)){
					mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
				}
				mainTaskState.setRemark("调用core更新全屋方案失败，uuid is empty");
			}
			//生成时间及更新主任务state表
			this.updateTaskStateTimeConsum(mainTaskState);
			//生成子任务渲染成功的消息，子任务渲染完成小程序发送消息模板
			this.insertAndSendMessage(taskState);
		} else if (subtaskCount == 1){
			List<AutoRenderTaskState> subtaskList = designPlanAutoRenderMapper.getSubTaskListByMainTaskId(taskState.getMainTaskId());
			int successed = DesignPlanConstants.TASKSTATE.FAILUE.getValue();
			for (AutoRenderTaskState autoRenderTaskState : subtaskList) {
				if (autoRenderTaskState.getState() == DesignPlanConstants.TASKSTATE.SUCCESS.getValue()){
					successed = DesignPlanConstants.TASKSTATE.SUCCESS.getValue();
				}
			}
			mainTaskState.setState(successed);
			mainTaskState.setBusinessId(taskState.getBusinessId());
			if (StringUtils.isNotBlank(uuid)) {
				mainTaskState.setFullHousePlanUUID(uuid);
			}
			String renderTypesStr = taskState.getRenderTypesStr();
			if (successed == DesignPlanConstants.TASKSTATE.SUCCESS.getValue()) {
				//改变主任务的状态为渲染成功，
				if ("1".equals(renderTypesStr)) {
					mainTaskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
				} else if ("2".equals(renderTypesStr)) {
					mainTaskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
				} else if ("3".equals(renderTypesStr)) {
					mainTaskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
				} else if ("4".equals(renderTypesStr)){
					mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
				}

			} else {
				//更新全屋方案返回的结果为空的时候，设置失败的状态，远程调用没有问题的话不会走这里
				if ("1".equals(renderTypesStr)) {
					mainTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
				} else if ("2".equals(renderTypesStr)) {
					mainTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
				} else if ("3".equals(renderTypesStr)) {
					mainTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
				} else if ("4".equals(renderTypesStr)){
					mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
				}
				mainTaskState.setRemark("所有子任务均渲染失败!");
			}
			//生成时间及更新主任务state表
			this.updateTaskStateTimeConsum(mainTaskState);
			//生成子任务渲染成功的消息，子任务渲染完成小程序发送消息模板
			this.insertAndSendMessage(taskState);
		} else if (subtaskCount > 1){
			synchronized (SaveRenderPicServiceImpl.class){
				mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
				mainTaskState.setSubtaskCount(mainTaskState.getSubtaskCount() - 1);
				if (StringUtils.isNotBlank(uuid)) {
					mainTaskState.setFullHousePlanUUID(uuid);
				}
				designPlanAutoRenderMapper.updateTaskStateByTaskId(mainTaskState);
			}
		}
	}

	/**
	 * 抽取公共方法，确定渲染时间最后更新state
	 * @param mainTaskState
	 */
	private void updateTaskStateTimeConsum(AutoRenderTaskState mainTaskState) {
		long startTime = mainTaskState.getGmtCreate().getTime();
		long endTime = System.currentTimeMillis();
		long spendTimeLong = endTime - startTime;
		int secondTotal = (int)spendTimeLong/1000;
		int minute = secondTotal / 60;
		int second = secondTotal % 60;
		mainTaskState.setRenderTimeConsuming(minute+"分"+second+"秒");
		logger.error("处理全屋方案任务 ---- 回填主任务相关信息：autoRenderTaskState="+mainTaskState);
		designPlanAutoRenderMapper.updateTaskStateByTaskId(mainTaskState);
		FullHouseDesignPlan fullHouseDesignPlan = fullHouseDesignPlanMapper.getFullHousePlanById(mainTaskState.getNewFullHousePlanId());
//		FullHouseDesignPlan fullHouseDesignPlan = fullHouseDesignPlanService.getgetByUUID(mainTaskState.getFullHousePlanUUID());
		logger.error("处理全屋方案任务 ---- 回填主任务相关信息：autoRenderTaskState="+fullHouseDesignPlan);
		if(fullHouseDesignPlan != null) {
			Integer newFullHousePlanId = fullHouseDesignPlan.getId();
			Integer currentNumberOfRendingTask = fullHouseDesignPlanService.getNumberOfRendingTaskByFullHousePlanId(newFullHousePlanId);
			if(currentNumberOfRendingTask.intValue() == 0) {
				fullHouseDesignPlanService.updateFullHousePlanState(newFullHousePlanId,1);
			}
		}else{
			logger.error("Can't get the full house plan info.");
		}

	}

	/**
	 * 远程调用core的update全屋方案接口
	 * @param taskState
	 * @return
	 */
	private String updateFullHousePlan(AutoRenderTaskState taskState) {
		String url = CORE_DOMAIN_NAME+"/v1/core/fullHouse/updateFullHouseDesignPlanScene";

		FullHouseDesignPlanSceneUpdate update = new FullHouseDesignPlanSceneUpdate();
		update.setFullHouseId(taskState.getNewFullHousePlanId());
		update.setNewPlanId(taskState.getBusinessId());
		update.setSourcePlanId(taskState.getPreRenderSceneId()==null?0:taskState.getPreRenderSceneId());
		update.setSpaceFunctionId(taskState.getSpaceFunctionId());
		update.setUserId(taskState.getOperationUserId());
		update.setHouseId(taskState.getHouseId());

		String result = Utils.doPost(url, update);
		logger.error("远程调用更新全屋方案 ------ result="+result);
		if (StringUtils.isBlank(result)) {
			return null;
		}
		ResponseEnvelope responseEnvelope = GSON.fromJson(result, ResponseEnvelope.class);
		logger.error("远程调用更新全屋方案 ------ responseEnvelope="+responseEnvelope);
		if (responseEnvelope.getObj() != null) {
			return responseEnvelope.getObj().toString();
		}
		return null;

	}

	/**
	 * 处理全屋方案任务
	 * @param taskState
	 */
	private synchronized void handleFullHousePlanTask(AutoRenderTaskState taskState) {
		//通过子任务获取主任务，判断主任务中的子任务数量字段:
		// 		子任务不为最后一个则不生成消息，改变主任务中的子任务数量
		// 		子任务为最后一个则改变主任务状态为完成，插入渲染成功的消息，并回调生成主方案及子方案
		logger.error("处理全屋方案任务 --------- taskState.getMainTaskId()="+taskState.getMainTaskId());
		AutoRenderTaskState mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
		logger.error("处理全屋方案任务 --------- mainTaskState="+mainTaskState);
		Integer subtaskCount = mainTaskState.getSubtaskCount();
		logger.error("处理全屋方案任务 --------- subtaskCount="+subtaskCount);
		Integer mainTaskId = mainTaskState.getTaskId();
		logger.error("处理全屋方案任务 --------- mainTaskId="+mainTaskId);
		//创建update对象
		AutoRenderTaskState autoRenderTaskState = new AutoRenderTaskState();
		autoRenderTaskState.setTaskId(mainTaskId);
		autoRenderTaskState.setNewFullHousePlanId(mainTaskState.getNewFullHousePlanId());
		if (subtaskCount > 1) {
			//子任务不为最后一个，只改变主任务的子任务数量字段
			logger.error("处理全屋方案任务 --------- subtaskCount-1:"+(subtaskCount-1));
			autoRenderTaskState.setSubtaskCount(subtaskCount-1);
			logger.error("处理全屋方案任务 --------- 子任务不是最后一个，改变子任务数量为："+autoRenderTaskState.getSubtaskCount());
			designPlanAutoRenderMapper.updateTaskStateByTaskId(autoRenderTaskState);
		} else {
			//子任务为最后一个，rest调用生成全屋主方案及子方案
			String resultStr = this.createFullHousePlan(mainTaskState);
			logger.error("处理全屋方案任务 --------- resultStr="+resultStr);

			if (StringUtils.isNotBlank(resultStr)) {
				//远程调用生成的结果不为空，截取，添加到state表中
				String[] split = resultStr.split(",");
				if (split.length > 1) {
					Integer newFullHousePlanId = Integer.parseInt(split[0]);
					//把新的全屋方案id和UUID存起来
					autoRenderTaskState.setNewFullHousePlanId(newFullHousePlanId);
					autoRenderTaskState.setFullHousePlanUUID(split[1]);
				}
				//改变主任务的状态为渲染成功，
				autoRenderTaskState.setBusinessId(mainTaskState.getBusinessId());
				autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
				autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
				autoRenderTaskState.setSubtaskCount(subtaskCount-1);
			} else {
				//生成全屋方案返回的结果为空的时候，设置失败的状态，远程调用没有问题的话不会走这里
				autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
				autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);

			}
			//公共方案添加渲染时间及更新主任务状态
			mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(mainTaskId);
			logger.error("处理全屋方案任务 --------- 最终的主任务 mainTaskState："+mainTaskState);
			this.updateTaskStateTimeConsum(mainTaskState);
			insertAndSendMessage(mainTaskState);
		}

	}

	/**
	 * 插入渲染成功的消息，小程序发送消息模板
	 * @param mainTaskState
	 */
	private void insertAndSendMessage(AutoRenderTaskState mainTaskState) {
		//插入主任务渲染成功的消息
		this.createRenderSuccessMessage(mainTaskState);

		//渲染成功后小程序发送消息模板
		try {
            miniprogramMessageTemplate(mainTaskState);
        } catch (Exception e) {
            logger.error("小程序发送模板消息 ===>exception："+e);
        }
	}

	/**
	 * 调用生成全屋方案
	 * @param mainTaskState
	 * @return
	 */
	private String createFullHousePlan(AutoRenderTaskState mainTaskState) {
		//构造insert的对象
		FullHouseDesignPlanAdd fullHouseDesignPlanAdd = new FullHouseDesignPlanAdd();
		fullHouseDesignPlanAdd.setDesignPlanName(mainTaskState.getDesignName());
		fullHouseDesignPlanAdd.setDesignPlanStyleId(0);
		fullHouseDesignPlanAdd.setUserId(mainTaskState.getOperationUserId());
		fullHouseDesignPlanAdd.setFullHousePlanSourceId(mainTaskState.getFullHousePlanId());
		fullHouseDesignPlanAdd.setHouseId(mainTaskState.getHouseId());
		//通过主任务id获取所有的子任务集合
		List<AutoRenderTaskState> subTaskStateList =
				designPlanAutoRenderMapper.getSubTaskListByMainTaskId(mainTaskState.getTaskId());
		//在主任务回填第一个子任务的businessId，便于获取封面图之类的
		mainTaskState.setBusinessId(subTaskStateList.get(0).getBusinessId());
		//构造效果图方案id集合
		List<Integer> livingDiningRoom = new ArrayList<>();
		List<Integer> bedroom = new ArrayList<>();
		List<Integer> kitchen = new ArrayList<>();
		List<Integer> toilet = new ArrayList<>();
		List<Integer> schoolroom = new ArrayList<>();

		for (AutoRenderTaskState subTaskState : subTaskStateList) {
			//封装全屋方案insert对象所需的各种户型对应的效果图id
			switch (subTaskState.getSpaceFunctionId()) {
				case AutoRenderTaskConstant.SPACE_TYPE_LIVING_DINING_ROOM :
					livingDiningRoom.add(subTaskState.getBusinessId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_BEDROOM :
					bedroom.add(subTaskState.getBusinessId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_KITCHEN :
					kitchen.add(subTaskState.getBusinessId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_TOILET :
					toilet.add(subTaskState.getBusinessId());
					break;
				case AutoRenderTaskConstant.SPACE_TYPE_SCHOOLROOM :
					schoolroom.add(subTaskState.getBusinessId());
					break;
			}
		}
		fullHouseDesignPlanAdd.setLivingDiningRoom(livingDiningRoom);
		fullHouseDesignPlanAdd.setBedroom(bedroom);
		fullHouseDesignPlanAdd.setKitchen(kitchen);
		fullHouseDesignPlanAdd.setToilet(toilet);
		fullHouseDesignPlanAdd.setSchoolroom(schoolroom);

		String url = CORE_DOMAIN_NAME+"/v1/core/fullHouse/addFullHouseDesignPlan";
		logger.error("远程调用生成全屋方案 ---------- url="+url);
		logger.error("远程调用生成全屋方案 ---------- fullHouseDesignPlanAdd="+fullHouseDesignPlanAdd);
		String result = Utils.doPost(url, fullHouseDesignPlanAdd);
		logger.error("远程调用生成全屋方案 ---------- result="+result);
		if (StringUtils.isBlank(result)) {
			return null;
		}
		ResponseEnvelope responseEnvelope = GSON.fromJson(result, ResponseEnvelope.class);
		logger.error("远程调用生成全屋方案 ---------- responseEnvelope="+responseEnvelope);
		if (responseEnvelope.getObj() != null) {
			return responseEnvelope.getObj().toString();
		}
		return null;
	}


	/**
	 * 生成渲染成功的消息
	 * @param taskState
	 */
	private void createRenderSuccessMessage(AutoRenderTaskState taskState) {
		//生成一条渲染成功的消息记录-----start
		logger.error("生成我的消息开始-----------------------");
		SysUserMessage sysUserMessage = new SysUserMessage();
		if ("1".equals(taskState.getRenderTypesStr())) {
			sysUserMessage.setTitle("照片级渲染成功");
		} else if ("2".equals(taskState.getRenderTypesStr())) {
			sysUserMessage.setTitle("720°渲染成功");
		} else if ("3".equals(taskState.getRenderTypesStr())) {
			sysUserMessage.setTitle("720°多点渲染成功");
		} else if ("4".equals(taskState.getRenderTypesStr())) {
			sysUserMessage.setTitle("漫游视频渲染成功");
		}
		sysUserMessage.setTaskId(taskState.getTaskId());
		if (0 == taskState.getTaskType().intValue()) {
			sysUserMessage.setContent("装进我家 | " + taskState.getDesignName());
		} else if (1 == taskState.getTaskType().intValue()) {
			sysUserMessage.setContent("替换渲染 | " + taskState.getDesignName());
		}
		sysUserMessage.setCreator(taskState.getCreator());
		sysUserMessage.setModifier(taskState.getModifier());
		sysUserMessage.setMessageType(SysUserMessageConstants.RENDER_TASK_NEWS);
		sysUserMessage.setStatus(SysUserMessageConstants.SUCCESS);
		sysUserMessage.setUserId(taskState.getOperationUserId());
		sysUserMessage.setPlatformId(taskState.getPlatformId());
		//生成一条渲染成功的消息记录--end
		//插入消息记录到我的消息表
		int id = sysUserMessageService.add(sysUserMessage);
		logger.error("insert a success taskMessage into sys_user_message---->id：" + id);
	}

	/**
	 * 小程序发送消息模板调用
	 * @param taskState
	 */
	private void miniprogramMessageTemplate( AutoRenderTaskState taskState) {
		logger.error("发送消息模板调用====>taskState="+taskState);
		Integer miniProgramId = getPlatformIdByCode("miniProgram");
		logger.error("发送消息模板调用====>miniProgramId="+miniProgramId);
		if (miniProgramId != null && miniProgramId.equals(taskState.getPlatformId())) {
			String url = SYSTEM_DOMAIN_NAME + "/v1/notify/wx/sendRenderTemplateMsg";
			Map<String, String> map = new HashMap<>();
			logger.error("发送消息模板调用====>taskState.getOperationUserId()="+taskState.getOperationUserId());
			map.put("userId", taskState.getOperationUserId().toString());
			Integer planId = taskState.getPlanId();
			if (taskState.getGroupPrimaryId() != null && taskState.getGroupPrimaryId() != 0) {
				//如果打组推荐方案主方案不为0则返回主方案id
				planId = taskState.getGroupPrimaryId();
			}
			logger.error("发送消息模板调用====>planId="+planId);
			map.put("designPlanId", planId.toString());
			logger.error("发送消息模板调用====>taskState.getPlanHouseType()="+taskState.getPlanHouseType());
			if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE.equals(taskState.getPlanHouseType())) {
				//如果是全屋方案
				FullHouseDesignPlan fullHousePlan = fullHouseDesignPlanMapper.getFullHousePlanById(taskState.getNewFullHousePlanId());
				map.put("designPlanName", fullHousePlan.getPlanName());
			} else {
				//单空间方案找对应方案的名称
				switch (taskState.getOperationSource()) {
					case 0:
						DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.get(taskState.getPlanId());
						logger.error("发送消息模板调用====>designPlanRenderScene.getPlanName=" + designPlanRenderScene.getPlanName());
						map.put("designPlanName", designPlanRenderScene.getPlanName());
						break;
					case 1:
						DesignPlanRecommended designPlanRecommended = designPlanRecommendedMapperV2.selectByPrimaryKey(taskState.getPlanId());
						map.put("designPlanName", designPlanRecommended.getPlanName());
						break;
				}
			}
			map.put("renderResult", "渲染成功");
			logger.error("发送消息模板调用====>taskState.getTaskType()="+taskState.getTaskType());
			map.put("renderType", taskState.getTaskType().toString());
			logger.error("发送消息模板调用====>taskState.getRenderTypesStr()="+taskState.getRenderTypesStr());
			switch (taskState.getRenderTypesStr()) {
				case "1" :
					map.put("renderLevel", "1");
					break;
				case "2" :
					map.put("renderLevel", "4");
					break;
				case "3" :
					map.put("renderLevel", "8");
					break;
				case "4" :
					map.put("renderLevel", "6");
					break;
			}
			logger.error("小程序发送模板消息 ===> url="+url+" ,map="+map);
			String result = Utils.doPostMethodForm(url,map);
			logger.error("小程序发送模板消息 ===> result : "+result);
		}

	}

	/**
	 * 根据平台编码获取平台id
	 * @param platformCode
	 * @return
	 */
	public Integer getPlatformIdByCode(String platformCode) {
		return designPlanRenderSceneMapper.getPlatformIdByCode(platformCode);
	}

}
