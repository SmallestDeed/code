package com.nork.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.ProductModelPathService;

/**
 * @Package com.nork.system.controller
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/system/productModelPath")
public class ProductModelPathController {
	@Autowired
	ProductModelPathService modelPathService;
	
	//检查不存在的模型路径，并将它的系统编码和business_id写到服务器的文件中
	@RequestMapping("/checkModelPath")
	public void checkModelPath() throws Exception {
		modelPathService.checkModelPath();
	}
	
}