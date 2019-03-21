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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.UserGuideResponse;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.service.SysUserService;
import com.nork.user.model.UserGuide;
import com.nork.user.model.search.UserGuideSearch;
import com.nork.user.service.UserGuideService;

/**
 * @Title: UserGuideController.java
 * @Package com.nork.user.controller
 * @Description:用户指南-用户指南Controller
 * @createAuthor pandajun
 * @CreateDate 2016-11-21 20:22:33
 * @version V1.0
 */
@Controller
@RequestMapping("/online/{style}/user/userGuide")
public class UserGuideController {
	private static Logger logger = Logger.getLogger(UserGuideController.class);
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "jsp";
	private final String MAIN = "/" + STYLE + "/user/userGuide";
	private final String BASEMAIN = "/" + STYLE + "/user/base/userGuide";
	private final String JSPMAIN = "/" + JSPSTYLE + "/user/userGuide";

	@Autowired
	private UserGuideService userGuideService;

	@Resource
	private SysUserService sysUserService;

	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 用户指南 基础主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 * 用户指南 主页面
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
	 * 获取用户指南标题列表
	 */
	@RequestMapping(value = "/getUserGuideTitelList")
	@ResponseBody
	public Object getUserGuideTitelList(@ModelAttribute("userGuideSearch") UserGuideSearch userGuideSearch,
			HttpServletRequest request, HttpServletResponse response) {

		UserGuideResponse dictionaryResponse = null;

		userGuideSearch.setIsDeleted(0);
		List<UserGuideResponse> list = new ArrayList<UserGuideResponse>();

		int total = 0;
		try {
			total = userGuideService.getCount(userGuideSearch);
			logger.info("total:" + total);

			if (total > 0) {

				List<UserGuide> guideList = userGuideService.getPaginatedList(userGuideSearch);

				for (UserGuide guide : guideList) {
					dictionaryResponse = new UserGuideResponse();
					dictionaryResponse.setId(guide.getId());
					dictionaryResponse.setName(guide.getName());
					list.add(dictionaryResponse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserGuide>(false, "数据异常!", userGuideSearch.getMsgId());
		}
		return new ResponseEnvelope<UserGuideResponse>(total, list, userGuideSearch.getMsgId());
	}

}