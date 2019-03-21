package com.nork.mobile.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.aes.model.AESFileConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Utils;
import com.nork.mobile.model.search.DesignPlanRecommendedModel;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.ResRenderPicService;

/**
 * 移动端推荐设计方案的controller
 * 
 */
@Controller
@RequestMapping("/{style}/mobile/designPlanRecommended")
public class MobileDesignPlanRecommendedController {

	private static Logger logger = Logger.getLogger(MobileDesignPlanRecommendedController.class);
	@Autowired
	private ResRenderPicService resRenderPicService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();

	

	/**
	 * 从推荐页面里获取一个推荐方案的缩略图集
	 */
	@RequestMapping(value = "/getRecommendedPictureList")
	@ResponseBody
	public Object getRecommendedPictureList(@RequestBody DesignPlanRecommendedModel model) {
		String remark = model.getRemark();
		Integer planRecommendedId = model.getPlanRecommendedId();
		if (planRecommendedId == null) {
			return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
		}
		ResRenderPic res = new ResRenderPic();
		List<String> fileKeys = new ArrayList<>();

		res.setPlanRecommendedId(planRecommendedId);
		res.setIsDeleted(0);
		res.setOrder(" gmt_create desc");

		if (remark.equals("photo")) {
			res.setRenderingType(1);
			fileKeys.add("design.designPlanRecommended.render.small.pic");
			res.setFileKeyList(fileKeys);
		} else if (remark.equals("720")) {
			res.setRenderingType(4);
			fileKeys.add("design.designPlanRecommended.render.small.pic");
			res.setFileKeyList(fileKeys);
		} else if (remark.equals("roam")) {
			res.setRenderingType(8);
			res.setPicType("渲染截图");
			fileKeys.add("design.designPlanRecommended.render.pic");
			res.setFileKeyList(fileKeys);
		} else if (remark.equals("video")) {
			res.setRenderingType(6);
			fileKeys.add("design.designPlanRecommended.render.video.cover");
			fileKeys.add("design.designPlanRecommended.render.pic");
			res.setFileKeyList(fileKeys);
		}

		List<ResRenderPic> picList = resRenderPicService.getList(res);

		ResponseEnvelope<ResRenderPic> envelope = new ResponseEnvelope<>();
		envelope.setDatalist(picList);
		envelope.setTotalCount(picList.size());
		return envelope;
	}

}
