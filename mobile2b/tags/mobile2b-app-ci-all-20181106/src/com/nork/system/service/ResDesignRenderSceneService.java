package com.nork.system.service;

import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResDesignRenderScene;

public interface ResDesignRenderSceneService {

	/**
	 * 讲配置文件拷贝一份物理文件,并在res_design_ectype存对应的文件数据
	 * @author huangsongbo
	 * @param resDesign
	 * @param businessId 
	 * @return
	 */
	long copyFromResDesign(ResDesign resDesign, long businessId);

	ResDesignRenderScene get(Integer configFileId);

	long add(ResDesignRenderScene resDesignRenderScene);

	/**
	 * 根据id删除数据
	 * @author huangsongbo
	 * @param resDesignRenderSceneId
	 */
	void delete(Integer resDesignRenderSceneId);

	/**
	 * 将拼花信息拷贝到 效果图中
	 * @param spellingFlowerFileId
	 * @param designPlanRenderScene
	 * @return
	 */
	long copySpellingFlowerFile(Integer spellingFlowerFileId,DesignPlanRenderScene designPlanRenderScene);
	
}
