package com.nork.common.async;

import java.util.concurrent.Callable;

import com.nork.common.util.SpringContextHolder;
import com.nork.onekeydesign.service.DesignPlanService;


/***
 * 准备3d渲染,将3d模型文件解压到渲染目录任务
 * @author xiao.xc
 * @date 2016.06.21
 *
 */
public class UnzipRenderFileTask implements Callable<Result> {
	
	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private UnzipRenderFileParameter parameter;
	
	public UnzipRenderFileTask(UnzipRenderFileParameter parameter){
		this.parameter=parameter;
	}
	@Override
	public Result call() throws Exception {
		// TODO Auto-generated method stub
		designPlanService.startRender(parameter.getPlanId(), parameter.getRootDirName(), parameter.getRenderConfig(), parameter.getType());
	    return null;
	}

}
