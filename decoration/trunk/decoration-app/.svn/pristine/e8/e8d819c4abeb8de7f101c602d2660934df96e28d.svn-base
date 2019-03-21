package com.nork.product.controller2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.product.service2.GroupCollectDetailsService2;

@Controller
@RequestMapping("/{style}/web/product/groupCollectDetails2")
public class WebGroupCollectDetailsController2 {

	@Autowired
	private GroupCollectDetailsService2 groupCollectDetailsService2;
	
	/**
	 * 删除组合收藏明细接口
	 * @author huangsongbo
	 * @param id 组合收藏明细id
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseEnvelope delete(Integer id,String msgId){
		return groupCollectDetailsService2.delete(id, msgId);
	}
}
