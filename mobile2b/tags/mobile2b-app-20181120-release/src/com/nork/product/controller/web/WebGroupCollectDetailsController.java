package com.nork.product.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.product.service.GroupCollectDetailsService;

@Controller
@RequestMapping("/{style}/web/product/groupCollectDetails")
public class WebGroupCollectDetailsController {

	@Autowired
	private GroupCollectDetailsService groupCollectDetailsService;
	
	/**
	 * 删除组合收藏明细接口
	 * @author huangsongbo
	 * @param id 组合收藏明细id
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer id,String msgId){
		if(StringUtils.isBlank(msgId))
			return new ResponseEnvelope<>(false, "参数msgId不能为空", msgId);
		if(id==null)
			return new ResponseEnvelope<>(false, "参数id不能为空", msgId);
		int num=groupCollectDetailsService.delete(id);
		if(num<1)
			return new ResponseEnvelope<>(false, "该组合收藏明细已不存在", msgId);
		return new ResponseEnvelope<>(true, "删除成功", msgId);
	}
	
}
