package com.nork.system.controller.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.sandu.common.LoginContext;

@Controller
@RequestMapping("/{style}/web/system/resPic")
public class WebResPicController {

	Logger logger = Logger.getLogger(WebResPicController.class);

	@Autowired
	private ResRenderPicService resRenderPicService;
	
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	/**
	 * 获取二维码接口
	 * @param picId
	 * @param renderingType
	 * @param msgId
	 * @param request
	 * @return
	 */
	@RequestMapping("/getQRCodePicUrl")
	@ResponseBody
	public Object getQRCodePicUrl(Integer picId, Integer renderingType, String msgId, HttpServletRequest request){
		/*参数验证*/
		if(picId==null||picId<1){
			return new ResponseEnvelope<>(false, "参数picId不能为空并且不能小于1", msgId);
		}

		// 获取登录用户信息
		Map map =LoginContext.getTokenData();
		if (null == map) {
			return new ResponseEnvelope<>(false,"请重新登录！");
		}
		LoginUser loginUser= new LoginUser();
		String appKey = map.get("appKey").toString();
		String mediaType = request.getHeader("MediaType");
		Long uTypeValue = (Long)map.get("utype");
		String loginName = (String) map.get("uname");
		int uType = uTypeValue.intValue();
		long id =(long) map.get("uid");
		loginUser.setId(new Long(id).intValue());
		loginUser.setLoginName(loginName);
		loginUser.setUserType(Integer.valueOf(uType));
		loginUser.setAppKey(appKey);
		loginUser.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		//LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		/*参数验证->end*/

		/*网页地址(扫描二维码进入的地址)*/
		ResRenderPic resPic = resRenderPicService.get(picId);
		if(resPic==null)
			return new ResponseEnvelope<>(false, "未找到该图片:picId:"+picId, msgId);

		String picPath =  resRenderPicService.getQRpicPath(resPic);
		


		//生成二维码图片->end
		Map<String,String> returnMap=new HashMap<String, String>();
		returnMap.put("url", picPath);
		returnMap.put("contentUrl",picPath);
		return new ResponseEnvelope<>(returnMap, msgId, true);
	}

	/**
	 * 获取视频路径的二维码的接口
	 * 
	 * @param videoId
	 * @param renderingType
	 * @param msgId
	 * @param request
	 * @return
	 */
	@RequestMapping("/getQRCodeVideoUrl")
	@ResponseBody
	public Object getQRCodeVideoUrl(Integer videoId, Integer renderingType,
			String msgId, HttpServletRequest request) {
		/* 参数验证 */
		if (videoId == null || videoId < 1) {
			return new ResponseEnvelope<>(false, "参数picId不能为空并且不能小于1", msgId);
		}

		// 获取登录用户信息
		Map map =LoginContext.getTokenData();
		if (null == map) {
			return new ResponseEnvelope<>(false,"请重新登录！");
		}
		LoginUser loginUser= new LoginUser();
		String appKey = map.get("appKey").toString();
		String mediaType = request.getHeader("MediaType");
		Long uTypeValue = (Long)map.get("utype");
		String loginName = (String) map.get("uname");
		int uType = uTypeValue.intValue();
		long id =(long) map.get("uid");
		loginUser.setId(new Long(id).intValue());
		loginUser.setLoginName(loginName);
		loginUser.setUserType(Integer.valueOf(uType));
		loginUser.setAppKey(appKey);
		loginUser.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
//		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		//LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		ResRenderVideo resRenderVideo = resRenderVideoService.get(videoId);
		if (resRenderVideo == null)
			return new ResponseEnvelope<>(false, "未找到该图片:videoId:" + videoId,
					msgId);
		String videoPath = "";

		if (resRenderVideo.getRenderingType() != null) {
			if (RenderTypeCode.COMMON_VIDEO == resRenderVideo
					.getRenderingType()
					|| RenderTypeCode.HD_VIDEO == resRenderVideo
							.getRenderingType()) {
				// 视频地址
				if (StringUtils.isBlank(resRenderVideo.getVideoPath())) {
					return new ResponseEnvelope<>(false, "视频加载中，请稍等片刻!", msgId);
				}
				videoPath = Utils.getValue("app.resources.url", "")
						+ resRenderVideo.getVideoPath();
				logger.info("=====================================videoPath="
						+ videoPath);
			} else {
				/* 取普通渲染图路径 */
				videoPath = Utils.getValue("app.resources.url", "")
						+ resRenderVideo.getVideoPath();
			}
		} else {
			/* 取普通渲染图路径 */
			videoPath = Utils.getValue("app.resources.url", "")
					+ resRenderVideo.getVideoPath();
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("url", videoPath);
		return new ResponseEnvelope<>(returnMap, msgId, true);
	}

}
