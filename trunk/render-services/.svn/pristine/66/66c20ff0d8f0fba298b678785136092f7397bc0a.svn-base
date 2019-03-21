package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.service.OptimizePlanService;
import com.nork.mobile.dao.AutoRenderResRenderPicMapper;
import com.nork.mobile.dao.AutoRenderResRenderVideoMapper;
import com.nork.mobile.model.search.AutoRenderResRenderPic;
import com.nork.mobile.model.search.AutoRenderResRenderVideo;
import com.nork.mobile.service.MobileResRenderPicService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.impl.ResRenderPicServiceImpl;

@Service("mobileResRenderPicService")
public class MobileResRenderPicServiceImpl implements MobileResRenderPicService {

	private static Logger logger = Logger.getLogger(ResRenderPicServiceImpl.class);
	@Autowired
	private ResRenderPicMapper resRenderPicMapper;
	@Autowired
	private OptimizePlanService optimizeDesingPlanService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private AutoRenderResRenderPicMapper autoRenderResRenderPicMapper;
	@Autowired
	private AutoRenderResRenderVideoMapper autoRenderResRenderVideoMapper;

	/**
	 * 从推荐页面获取720全景图
	 */
	@Override
	public String getRecommendedPicturePath(ResRenderPicSearch resRenderPicSearch) {
		List<ResRenderPic> list = resRenderPicMapper.selectList(resRenderPicSearch);
		String picPath = "";
		if (CustomerListUtils.isNotEmpty(list)) {
			for (ResRenderPic res : list) {
				String code = res.getSysCode();
				if (code != null && res.getFileKey().equals("design.designPlan.render.pic")) {
					if (RenderTypeCode.COMMON_720_LEVEL == res.getRenderingType()
							|| RenderTypeCode.HD_720_LEVEL == res.getRenderingType()) {
						// 拼接 路径+sysCode
						picPath = Utils.getPropertyName("app", "app.render.server.url", "")
								+ Utils.getPropertyName("app", "app.server.siteName", "")
								+ "pages/vr720/vr720MobileSingle.htm?code=" + code;
						break;
					}
				}
			}
		}
		return picPath;
	}

	/**
	 * 获取720全景图
	 */
	@Override
	public String getPanoPicturePath(Integer id) {
		List<ResRenderPic> list = resRenderPicMapper.getResRenderPicsByBusinessId(id, 4);
		String picPath = "";
		if (CustomerListUtils.isNotEmpty(list)) {
			String code = list.get(0).getSysCode();
			// 拼接 路径+sysCode
			picPath = Utils.getPropertyName("app", "app.render.server.url", "")
					+ Utils.getPropertyName("app", "app.server.siteName", "")
					+ "pages/vr720/vr720MobileSingle.htm?code=" + code;

			return picPath;
		} else {
			return picPath;
		}
	}

	/**
	 * 通过sourcePlanId和templateId获取720全景图的sys_code的接口
	 * 
	 * @param resRenderPic
	 * @return
	 */
	@Override
	public Map<String, String> getResRenderPic(Integer sourcePlanId, Integer templateId, Integer renderingType,Integer taskId) {
		String picPath = "";
		Map<String, String> map = optimizeDesingPlanService.get720SysCode(sourcePlanId, templateId,renderingType,taskId);
		if(renderingType.intValue() == 4 || renderingType.intValue() == 8) {
			if(renderingType.intValue() == 4 && map.get("code") != null && map.get("code") != "") {
				if(templateId.intValue() == -1 || templateId.intValue() == 0) {// 替换渲染
					picPath = Utils.getPropertyName("app", "app.render.server.url", "")
							+ Utils.getPropertyName("app", "app.server.siteName", "")
							+ "pages/vr720/vr720MobileSingle.htm?code=" + map.get("code");
				} else {
					picPath = Utils.getPropertyName("app", "app.render.server.url", "")
							+ Utils.getPropertyName("app", "app.server.siteName", "")
							+ "pages/vr720/vr720MobileSingleForAuto.htm?code=" + map.get("code");
				}
			}else if(renderingType.intValue() == 8 && map.get("code") != null && map.get("code") != "") {
				if(templateId.intValue() == -1 || templateId.intValue() == 0) {// 替换渲染
					picPath = Utils.getPropertyName("app", "app.render.server.url", "")
							+ Utils.getPropertyName("app", "app.server.siteName", "")
							+ "pages/vr720/vr720RoamOfMobile.htm?code=" + map.get("code");
				} else {
					picPath = Utils.getPropertyName("app", "app.render.server.url", "")
							+ Utils.getPropertyName("app", "app.server.siteName", "")
							+ "pages/vr720/vr720RoamOfMobile.htm?code=" + map.get("code");
				}
			}
			map.put("url", picPath);
		}
		return map;
	}

	/**
	 * 从我的设计页面获取 缩略图集
	 */
	public Object getPictureList(Integer designPlanId, String remark) {
		ResRenderPic res = new ResRenderPic();
		List<String> fileKeys = new ArrayList<>();

//		res.setBusinessId(designPlanId);
		res.setDesignSceneId(designPlanId);
		res.setIsDeleted(0);
		res.setOrder(" gmt_create desc");

		// 根据不同的类型设置不同的筛选条件
		if (remark.equals("photo")) {
			res.setRenderingType(1);
			fileKeys.add("design.designPlan.render.small.pic");
			res.setFileKeyList(fileKeys);
		} else if (remark.equals("720")) {
			res.setRenderingType(4);
			fileKeys.add("design.designPlan.render.small.pic");
			res.setFileKeyList(fileKeys);
		} else if (remark.equals("video")) {
			res.setRenderingType(6);
			fileKeys.add("design.designPlan.render.video.cover");
			res.setFileKeyList(fileKeys);
		} else if (remark.equals("roam")) {
			res.setRenderingType(8);
			fileKeys.add("design.designPlan.render.pic");
			res.setFileKeyList(fileKeys);
		}

		// 调用PC端 原有的方法
		List<ResRenderPic> picList = resRenderPicService.getList(res);

		ResponseEnvelope<ResRenderPic> envelope = new ResponseEnvelope<>();
		envelope.setDatalist(picList);
		envelope.setTotalCount(picList.size());
		return envelope;
	}


	@Override
	public Object getQRCodePicUrl(Integer picId, String msgId, LoginUser loginUser,String remark) {
		/* 网页地址(扫描二维码进入的地址) */
		ResRenderPic resPic = null;
		String picPath = null;
		if("0".equals(remark)) {
			resPic = resRenderPicService.get(picId);
			if (resPic == null) {
				return new ResponseEnvelope<>(false, "未找到该图片：picId=" + picId, msgId);
			} else {
				picPath = resRenderPicService.getQRpicPath(resPic, loginUser);
			}
		}else if("1".equals(remark)){
			AutoRenderResRenderPic pic = autoRenderResRenderPicMapper.selectByPrimaryKey(picId);
			if (pic == null) {
				return new ResponseEnvelope<>(false, "未找到该图片：picId=" + picId, msgId);
			} else {
				picPath = getQRpicPath(pic, loginUser);
			}
		}
		// 生成二维码图片->end
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("url", picPath);
//		returnMap.put("contentUrl", picPath);
		return new ResponseEnvelope<>(returnMap, msgId, true);
	}
	
	/**
	 * 获取可以生成 不同的二维码的路径
	 * 
	 * @param resPic
	 * @param loginUser
	 * @return
	 */
	private String getQRpicPath(AutoRenderResRenderPic resPic, LoginUser loginUser) {
		int renderingType = resPic.getRenderingType().intValue();
		String picPath = getQRCodeInfo(resPic, loginUser);
		if (resPic.getRenderingType() != null) {
			//根据渲染类型的不同    拼接不同的路径  获取资源
			if (RenderTypeCode.COMMON_720_LEVEL == renderingType || RenderTypeCode.HD_720_LEVEL == renderingType) {// 普通单图720
				picPath = Utils.getPropertyName("app", "app.render.server.url", "")
						+ Utils.getPropertyName("app", "app.server.siteName", "") + picPath;
			} else if (RenderTypeCode.ROAM_720_LEVEL == renderingType) {// 720漫游
				picPath = Utils.getPropertyName("app", "app.render.server.url", "")
						+ Utils.getPropertyName("app", "app.server.siteName", "") + picPath;
			} else if (RenderTypeCode.COMMON_VIDEO == renderingType) {// 普通视频
				picPath = Utils.getPropertyName("app", "app.resources.url", "") + picPath;
			} else if (RenderTypeCode.HD_VIDEO == renderingType) {// 高清视频
				picPath = Utils.getPropertyName("app", "app.resources.url", "") + picPath;
			} else {
				picPath = Utils.getValue("app.resources.url", "") + picPath;
			}
		} else {
			/* 取普通渲染图路径 */
			picPath = Utils.getValue("app.resources.url", "") + picPath;
		}
		return picPath;
	}

	/**
	 * 获取二维码信息
	 * 
	 * @param resPic
	 * @param loginUser
	 * @return
	 */
	private String getQRCodeInfo(AutoRenderResRenderPic resPic, LoginUser loginUser) {

		String picPath = "";
		String code = "";
		if (resPic.getRenderingType() != null) {
			if (RenderTypeCode.COMMON_720_LEVEL == resPic.getRenderingType()
					|| RenderTypeCode.HD_720_LEVEL == resPic.getRenderingType()) {// 普通单图720
				AutoRenderResRenderPic singleCode = autoRenderResRenderPicMapper.get720SingleCode(resPic);// 得到720单点分享需要的code
				if (singleCode != null && StringUtils.isNotEmpty(singleCode.getSysCode())) {
					code = singleCode.getSysCode();
				} else {
					logger.error("ResRenderPic is null ,res_render_pic_id=" + resPic.getId());
					return null;
				}
				picPath = "pages/vr720/vr720MobileOrginal.htm?code=" + code;
			} else if (RenderTypeCode.ROAM_720_LEVEL == resPic.getRenderingType()) {// 720漫游
				// 获取720漫游组信息，传过来的pid问截图ID。渲染图列表接口返回了缩略图列表和对应的截图ID
				DesignRenderRoam romanCode = autoRenderResRenderPicMapper.get720RomanCode(resPic);
				if (romanCode != null && StringUtils.isNotEmpty(romanCode.getUuid())) {
					code = romanCode.getUuid();
					picPath = "pages/vr720/vr720Roam.htm?code=" + code;
				} else {
					logger.error("DesignRenderRoam is null ,res_render_pic_id=" + resPic.getId());
					return null;
				}
			} else if (RenderTypeCode.COMMON_VIDEO == resPic.getRenderingType()
					|| RenderTypeCode.HD_VIDEO == resPic.getRenderingType()) {// 普通视频
				AutoRenderResRenderVideo resRenderVideo = new AutoRenderResRenderVideo();
				resRenderVideo.setIsDeleted(0);
				resRenderVideo.setSysTaskPicId(resPic.getId());
				List<AutoRenderResRenderVideo> videoList = autoRenderResRenderVideoMapper.selectList(resRenderVideo);
				if (videoList == null || videoList.size() <= 0) {
					return null;
				} else if (videoList != null && videoList.size() == 1) {
					picPath = videoList.get(0).getVideoPath();
				} else {
					return null;
				}
			} else {
				picPath = resPic.getPicPath();
			}
		} else {
			/* 取普通渲染图路径 */
			picPath = resPic.getPicPath();
		}
		return picPath;
	}
}
