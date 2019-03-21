package com.nork.render.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nork.base.controller.BaseController;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.AESUtil;
import com.nork.render.service.RenderHostService;

/**   
 * @Title: RenderHostController.java 
 * @Package com.nork.render.controller
 * @Description:渲染-渲染主机Controller
 * @createAuthor yanghz 
 * @CreateDate 2017-01-15 17:45:34
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/render/renderHost")
public class WebRenderHostController extends BaseController{
	
	@Autowired
	private RenderHostService renderHostService;
	
	/**
	 *  接收来自渲染主机的心跳（检测主机是否正常）
	 *
	 * @param
	 * @return   String 
	 */
	@RequestMapping(value = "/taskHostState")
	public Object receiveTaskHostState(String taskID,String password,String msgId){
		//解密
		AESUtil.decrypt(taskID, password);
		
		return new ResponseEnvelope<>(true, "成功");
	}
}
