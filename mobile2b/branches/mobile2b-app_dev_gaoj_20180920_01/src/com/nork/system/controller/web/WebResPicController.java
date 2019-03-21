package com.nork.system.controller.web;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.model.DesignRenderRoam;
import com.nork.design.service.DesignRenderRoamService;
import com.nork.job.DelQRPicScheul;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.common.util.QRCode.MatrixToImageWriter;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;

@Controller
@RequestMapping("/{style}/web/system/resPic")
public class WebResPicController {

	Logger logger = Logger.getLogger(WebResPicController.class);

	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private ResRenderVideoService resRenderVideoService;

	
	
	
	
	@RequestMapping("/qRCodePicUrl")
	@ResponseBody
	public Object qRCodePicUrl(Integer id,String QRCodeType,String msgId, HttpServletRequest request){
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser == null || loginUser.getId().intValue() < 1 ){
			return new ResponseEnvelope<>(false, "登录超时，请重新登录", msgId);
		}
		String qRCodePicUrl = null;
		
		if("resRenderPic".equals(QRCodeType)){
			qRCodePicUrl = this.getRenderPicPath(id,loginUser);
		}else if("resRenderVideo".equals(QRCodeType)){
			qRCodePicUrl = this.getVideoPath(id);
		}
		return qRCodePicUrl;
	}
	
	
	
	
	public String getRenderPicPath(Integer picId,LoginUser loginUser){
		if(picId == null || picId.intValue() < 1){
			 return null;
		}
		if(loginUser == null || loginUser.getId().intValue() < 1){
			return null;
		}
		ResRenderPic resPic = resRenderPicService.get(picId);
		if (resPic == null){
			 return null;
		}
		String picUrl = resRenderPicService.getQRpicPath(resPic,loginUser);
		return picUrl;
	}
	
	
	
	public String getVideoPath(Integer videoId){
		String videoPath = null;
		if(videoId == null || videoId.intValue()<=0){
			 return null;
		}
		ResRenderVideo resRenderVideo = resRenderVideoService.get(videoId);
		if (resRenderVideo == null){
			 return null;
		}
		if(StringUtils.isEmpty(resRenderVideo.getVideoPath())){
			return null;
		}
		videoPath = resRenderVideo.getVideoPath();
		return videoPath;
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("/getQRCodePicUrl")
	@ResponseBody
	public Object getQRCodePicUrl(Integer picId, Integer renderingType, String msgId, HttpServletRequest request){
		
		long start =System.currentTimeMillis();
		/*参数验证*/
		if(picId==null||picId<1){
			return new ResponseEnvelope<>(false, "参数picId不能为空并且不能小于1", msgId);
		}

		// 获取登录用户信息
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser == null){
			return new ResponseEnvelope<>(false, "登录超时，请重新登录", msgId);
		}
		/*参数验证->end*/

		/*网页地址(扫描二维码进入的地址)*/
		ResRenderPic resPic = resRenderPicService.get(picId);
		if(resPic==null)
			return new ResponseEnvelope<>(false, "未找到该图片:picId:"+picId, msgId);

		String picPath =  resRenderPicService.getQRpicPath(resPic,loginUser);
		


		//生成二维码图片->end
		Map<String,String> returnMap=new HashMap<String, String>();
		returnMap.put("url", picPath);
		returnMap.put("contentUrl",picPath);
		System.out.println("times = " + (System.currentTimeMillis() -start));
		return new ResponseEnvelope<>(returnMap, msgId, true);
	}

	public static void main(String arg[]){}
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
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<>(false, "请先登录!", msgId);
		}

		/* 参数验证->end */
		/* 网页地址(扫描二维码进入的地址) */
		// ResPic resPic=resPicService.get(picId);
		// ResRenderPic resPic = resRenderPicService.get(picId);
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
				// 取漫游视频路径
//				ResourceBundle render = ResourceBundle.getBundle("render");
				// 视频地址
				if (StringUtils.isBlank(resRenderVideo.getVideoPath())) {
					return new ResponseEnvelope<>(false, "视频加载中，请稍等片刻!", msgId);
				}
//				videoPath = render.getString("app.resources.url")
//						+ resRenderVideo.getVideoPath();
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
