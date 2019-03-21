package com.nork.common.async;

import java.util.concurrent.Callable;

import net.sf.json.JSONObject;

import com.nork.common.util.SpringContextHolder;
import com.nork.design.model.RenderConfig;
import com.nork.design.service.DesignPlanService;
import com.nork.task.model.SysTask;

/***
 * 组装渲染任务文件
 * @author xiaoxc
 * @date 2016.12.26
 *
 */
public class AssembleRenderTaskFile implements Callable<Result>{
	
	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private AssembleRenderTaskFileParameter parameter ; 
	
	public AssembleRenderTaskFile(AssembleRenderTaskFileParameter parameter){
		this.parameter = parameter;
	}

	@Override
	public Result call() throws Exception {

		SysTask renderTask = parameter.getSysTask();
		RenderConfig renderConfig = parameter.getRenderConfig();
		JSONObject jsonObject = designPlanService.assembleRenderConfig(renderTask,renderConfig);
//		if(jsonObject != null && !jsonObject.getBoolean("success")){
//			return null;
//		}
		return null;
	}
}
