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
import com.nork.common.cache.CacheManager;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanService;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.model.search.DesignPlanRecommendedModel;
import com.nork.mobile.model.search.HouseSearchModel;
import com.nork.mobile.service.MobileDesignPlanRecommendedService;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysUserService;

/**
 * 移动端推荐设计方案的controller
 * 
 * @author yangzhun
 * 
 */
@Controller
@RequestMapping("/{style}/mobile/designPlanRecommended")
public class MobileDesignPlanRecommendedController {

	private static Logger logger = Logger.getLogger(MobileDesignPlanRecommendedController.class);

	@Autowired
	private MobileDesignPlanRecommendedService mobileDesignPlanRecommendedService;
	@Autowired
	private ResRenderPicService resRenderPicService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ResModelService resModelService;
	@Resource
	private ResPicService resPicService;
	@Resource
	private ResDesignService resDesignService;
	@Resource
	private SpaceCommonService spaceCommonService;
	@Resource
	private DesignPlanRecommendedService designPlanRecommendedService;
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;

	/**
	 * 方案推荐 列表 数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/planRecommendedList")
	@ResponseBody
	public Object planRecommendedList(@RequestBody DesignPlanRecommendedModel designPlanRecommendedModel,
			HttpServletRequest request, HttpServletResponse response) {

		String msgId = designPlanRecommendedModel.getMsgId();
		String houseType = designPlanRecommendedModel.getHouseType();
		String livingName = designPlanRecommendedModel.getLivingName();
		String areaValue = designPlanRecommendedModel.getAreaValue();
		String designRecommendedStyleId = designPlanRecommendedModel.getDesignRecommendedStyleId();
		String isMainList = designPlanRecommendedModel.getIsMainList();
		String creator = designPlanRecommendedModel.getCreator();
		String brandName = designPlanRecommendedModel.getBrandName();
		Integer templateId = designPlanRecommendedModel.getTemplateId();
		String planName = designPlanRecommendedModel.getPlanName();
		// String all = designPlanRecommendedModel.getAll();
		/** 移动端获取推荐方案，默认值为mobile */
		String displayType = "mobile";

		Integer start = designPlanRecommendedModel.getStart();
		Integer limit = designPlanRecommendedModel.getLimit();
		// 获取登录用户的信息
		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
		logger.error("获取appkey");
		String appKey = map.get("appKey");
		logger.error("appkey===>"+appKey);
		String cacheKey = "user_H5Token:" + appKey;
		LoginUser loginUser = (LoginUser) CacheManager.getInstance().getCacher().getObject(cacheKey);

		boolean valid = this.validPlanReleaseParam(msgId);
		if (!valid) {
			return new ResponseEnvelope<>(false, "参数不完整", msgId);
		}
		return mobileDesignPlanRecommendedService.getPlanRecommendedList(creator, brandName, houseType, livingName,
				areaValue, designRecommendedStyleId, displayType, msgId, loginUser, limit, start,templateId,planName);
	}

	/**
	 * 参数完整性判断
	 * 
	 * @param args
	 * @return
	 */
	private boolean validPlanReleaseParam(String... args) {
		boolean result = true;
		for (String arg : args) {
			if (StringUtils.isEmpty(arg)) {
				result = false;
				return result;
			}
		}
		return result;
	}

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
			fileKeys.add("design.designPlanRecommended.render.small.pic");
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
