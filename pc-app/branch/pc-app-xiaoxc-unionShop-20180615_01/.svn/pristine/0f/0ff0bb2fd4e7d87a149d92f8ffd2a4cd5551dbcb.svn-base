package com.nork.product.controller2.web;
 
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.GroupProductVO;
import com.nork.common.model.LoginUser;
import com.nork.product.service2.GroupProductDetailsService;
@Controller
@RequestMapping("/{style}/web/product/groupProductDetails2")
public class WebGroupProductDetailsController {
	private static Logger logger = Logger.getLogger(WebGroupProductDetailsController.class);
	@Autowired
	private GroupProductDetailsService groupProductDetailsService2;
	
	/*
	 * 组合产品详情选择属性
	 */
	@RequestMapping(value = "/selectProductAttribute")
	@ResponseBody
	public Object selectProductAttribute(GroupProductVO groupProductVO,HttpServletRequest request ) {
				LoginUser loginUser= SystemCommonUtil.getLoginUserFromSession(request);
				String mediaType = SystemCommonUtil.getMediaType(request);
				
		return groupProductDetailsService2.selectProductAttribute(groupProductVO,mediaType,loginUser); 
	}
	
}
