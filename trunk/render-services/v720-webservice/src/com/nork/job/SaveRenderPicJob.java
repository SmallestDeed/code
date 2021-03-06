package com.nork.job;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.nork.common.util.SpringContextHolder;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.RenderPicVO;
import com.nork.design.service.DesignPlanRenderService;
import com.nork.design.service.SaveRenderPicService;
import com.nork.threadpool.RenderJobType;


public class SaveRenderPicJob implements Callable<Boolean> {
	
	private static Logger logger = Logger
			.getLogger(SaveRenderPicJob.class);
	
	private SaveRenderPicService saveRenderPicService;
    
    private String jobType;
    
    private RenderPicVO renderPicVO;
       
	
    
    public SaveRenderPicJob(SaveRenderPicService service, RenderPicVO picModel, String jobType) {
    	this.saveRenderPicService = service;
    	this.renderPicVO = picModel ;
    	this.jobType = jobType;
    }
	@Override
	public Boolean call() throws Exception {
	    DesignPlanRenderService designPlanRenderService = SpringContextHolder.getBean(DesignPlanRenderService.class);
		boolean success = false;
		if(RenderJobType.RENDER_JOB_VIDEO.equals(jobType)){//增加一个渲染类型是否是视频类型的判断
			logger.error("Begin call SaveRenderPicJob_Video");
			success = saveRenderPicService.saveRenderPicOfVideo(renderPicVO);
			logger.error("call SaveRenderPicJob_Video result=" + success);
		}else if(RenderJobType.RENDER_JOB_720.equals(jobType)){
			logger.info("Begin call SaveRenderPicJob_720");
			success = saveRenderPicService.saveRenderPicOf720(renderPicVO);
			logger.info("call SaveRenderPicJob_720 result=" + success);
		}else if(RenderJobType.RENDER_JOB_N720.equals(jobType)){
			logger.info("Begin call RENDER_JOB_N720");
			success = saveRenderPicService.saveRenderPicOf720(renderPicVO);
			logger.info("call RENDER_JOB_N720 result=" + success);
		}else if(RenderJobType.RENDER_JOB_PHOTO.equals(jobType)){
		    if(renderPicVO.getDesignPlan() != null && renderPicVO.getLoginUser() != null){
		        /* Integer designPlanId = renderPicVO.getDesignPlan().getId();
				Integer userId = renderPicVO.getLoginUser().getId();
				boolean allow = designPlanRenderService.allownFreeRender(designPlanId,userId);//免费渲染有次数限制
		        if(!allow){
		            logger.info("planId="+designPlanId+"_has_hreach_hmax_hlimit_htimes");
		            return false;
		        }*/
		        logger.info("Begin call SaveRenderPicJob_Photo");
		        success = saveRenderPicService.saveRenderPicOfPhoto(renderPicVO);
		        logger.info("call SaveRenderPicJob_Photo result=" + success);
		    }
		}else{
			logger.error("call Render Job cant judgment jobType:" + jobType);
		}
		if(success){
			Integer taskId = renderPicVO.getTaskId();
            AutoRenderTaskState taskState = saveRenderPicService.getStateByTaskId(taskId);
            Integer platformId = taskState.getPlatformId();
            renderPicVO.setPlatformId(platformId);
			logger.error("Begin call  getOpType= " + renderPicVO.getOpType());
			if (renderPicVO.getIsAuto().intValue() == 1) {
				logger.error("Begin call  === " + renderPicVO.getTaskId());
				long sceneId = designPlanRenderService.processAfterRender2(renderPicVO);
				taskState.setState(DesignPlanConstants.TASKSTATE.SUCCESS.getValue());
				Integer businessId = Integer.valueOf(String.valueOf(sceneId));
				taskState.setBusinessId(businessId);
				logger.error("businessId  11  =============" + businessId);
				long startTime = taskState.getGmtCreate().getTime();
				long endTime = System.currentTimeMillis();
				long spendTimeLong = endTime -startTime;
				int secondTotal=(int) (spendTimeLong/1000);
				int min=secondTotal/60;
				int second=secondTotal%60;
				taskState.setRenderTimeConsuming(min+"分"+second+"秒");
				DesignPlanRenderScene designPlanRenderScene = saveRenderPicService.getDesignPlanRenderScene(businessId);
				taskState.setNewDesignCode(designPlanRenderScene.getPlanCode());
				saveRenderPicService.saveTaskBussinessId(taskState);
				logger.error("taskState.getTaskId() === " + taskState.getTaskId() + "sceneId==" + sceneId);
			}else {
				designPlanRenderService.processAfterRender(renderPicVO);
			}
		}
		Boolean result = Boolean.valueOf(success);
		return result;
	}

}
