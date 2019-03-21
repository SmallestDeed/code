package com.nork.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRes;
import com.nork.design.model.DesignPlanResRenderScene;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanRenderService;
import com.nork.design.service.DesignPlanService;

@Controller
@RequestMapping("/online/testController")
public class TestControllerCreatedByHuangsongbo {
	
	@Autowired
	private DesignPlanRenderService designPlanRenderService;

	@Autowired
	private DesignPlanService designPlanService;
	
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	
	@RequestMapping("/test001")
	@ResponseBody
	public void test001(){
		DesignPlanRes designPlanRes = designPlanRenderService.getDesignPlanRes(58074);
		designPlanRenderService.saveAsRenderBakScene(designPlanRes);
		//System.out.println();
	}
	
	@RequestMapping("/test002")
	@ResponseBody
	public void test002(){
		DesignPlanResRenderScene designPlanResRenderScene = designPlanRenderSceneService.getDesignPlanResRenderSceneById(58074);
		DesignPlanRes designPlanRes = designPlanRenderService.getDesignPlanResByDesignPlanResRenderScene(designPlanResRenderScene);
		long id = designPlanRenderService.saveAsTempDesignPaln4RenderBakScene(designPlanRes, 34);
		//System.out.println(id);
	}
	
	@RequestMapping("/test003")
	@ResponseBody
	public void test003(Integer id){
		designPlanRenderService.deleteTempDesignPaln4RenderBakScene(id, 279);
	}
	
	@RequestMapping("/test004")
	@ResponseBody
	public void test004(Integer id){
		designPlanRenderService.changeTempDesignPalnVisible(id);
	}
	
	@RequestMapping("/test005")
	@ResponseBody
	public void test005(){
		DesignPlan designPlan = designPlanService.get(17524);
    	Integer renderSceneId = designPlan.getDesignSceneId();
    	if(renderSceneId != null && renderSceneId > 0){
    		//System.out.println(true);
    	}else{
    		//System.out.println(false);
    	}
	}
	
}
