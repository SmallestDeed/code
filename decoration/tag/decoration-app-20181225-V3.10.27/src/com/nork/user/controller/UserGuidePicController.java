package com.nork.user.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.ResPicResponse;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.ResPic;
import com.nork.system.model.search.ResPicSearch;
import com.nork.system.service.ResPicService;

/**
 * @Title: UserGuidePicController.java
 * @Package com.nork.user.controller
 * @Description:用户指南-用户指南图片列表Controller
 * @createAuthor pandajun
 * @CreateDate 2016-12-07 14:42:28
 * @version V1.0
 */
@Controller
@RequestMapping("/online/{style}/user/userGuidePic")
public class UserGuidePicController {
	private static Logger logger = Logger.getLogger(UserGuidePicController.class);
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "jsp";
	private final String MAIN = "/" + STYLE + "/user/userGuidePic";
	private final String BASEMAIN = "/" + STYLE + "/user/base/userGuidePic";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user/userGuidePic";

	@Resource
	private ResPicService resPicService;

	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 用户指南图片列表 基础主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 * 用户指南图片列表 主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return MAIN;
	}

	/**
	 * 访问主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN + "/list";
	}

	/**
	 * 获取用户指南标题图片列表
	 */
	@RequestMapping(value = "/getUserGuideTitePiclList")
	@ResponseBody
	public Object getUserGuideTitePiclList(@ModelAttribute("resPicSearch") ResPicSearch resPicSearch,
			HttpServletRequest request, HttpServletResponse response, String uId) {

		ResPicResponse picResponse = null;

		resPicSearch.setIsDeleted(0);
		resPicSearch.setPicType("10");
		/* 按照sequence排序 */
        resPicSearch.setOrder(" sequence ");
        resPicSearch.setOrderNum(" desc ");
		List<ResPicResponse> list = new ArrayList<ResPicResponse>();

		if (uId.equals("")) {
			return new ResponseEnvelope<ResPic>(false, "数据异常!", resPicSearch.getMsgId());
		}

		String ugId = request.getParameter("uId");
		resPicSearch.setBusinessId(Integer.valueOf(ugId));

		int total = 0;
		try {
			total = resPicService.selectCountGuide(resPicSearch);
			logger.info("total:" + total);
			if (total > 0) {
				List<ResPic> picList = resPicService.selectPaginatedListGuide(resPicSearch);
				for (ResPic pic : picList) {
					picResponse = new ResPicResponse();
					picResponse.setId(pic.getId());
					picResponse.setPicPath(pic.getPicPath());
					list.add(picResponse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResPic>(false, "数据异常!", resPicSearch.getMsgId());
		}
		return new ResponseEnvelope<ResPicResponse>(total, list, resPicSearch.getMsgId());
	}

}
