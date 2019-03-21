package com.nork.mobile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.CacheManager;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.controller.web.WebDesignPlanController;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.impl.DesignPlanAutoRenderServiceImpl;
import com.nork.mobile.dao.MobileRenderRecordMapper;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.TextureReplaceTaskDetail;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.mobile.service.MobileDesignPlanService;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.mobile.service.MobileSearchDesignPlanProductService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.nork.system.service.SysDictionaryService;

@Controller
@RequestMapping("/{style}/mobile/design/designPlan")
public class MobileDesignPlanController {
	private static Logger logger = Logger
			.getLogger(WebDesignPlanController.class);

	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
    private ResRenderPicService resRenderPicService;
	@Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
    private ResRenderPicMapper resRenderPicMapper;
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	@Autowired
    private DesignPlanAutoRenderServiceImpl designPlanAutoRenderServiceImpl;
	@Autowired
	private MobilePayRenderService mobilePayRenderService;
	@Autowired
	private MobileSearchDesignPlanProductService mobileSearchDesignPlanProductService;
	@Autowired
	private MobileRenderRecordMapper mobileRenderRecordMapper;
	@Autowired
	private MobileDesignPlanService mobileDesignPlanService;

	/**
	 * 我的设计   不再获取设计方案   改为   获取720效果图
	 * 
	 * @param designPlan
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/myDesignPlanMobile")
	@ResponseBody
	public ResponseEnvelope getThumbList(
			@RequestBody MobileRenderingModel thumbData) {
		ResponseEnvelope envelope = new ResponseEnvelope();

		String msgId = thumbData.getMsgId();
		Integer start = thumbData.getStart();//第几条数据
		Integer limit = thumbData.getLimit();//每页显示多少条，最大不能超过20页
		Integer userId = thumbData.getUserId();
		String name = thumbData.getName();//查询名称
		Integer spaceFunctionId = thumbData.getSpaceFunctionId();
		envelope.setMsgId(msgId);
		
		if (StringUtils.isEmpty(msgId)) {
			envelope.setSuccess(false);
			envelope.setMessage("params error");
			return envelope;
		}
		if (userId < 0) {
			envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
			return envelope;
		}

        DesignPlanRenderScene designPlanRenderScene = new DesignPlanRenderScene();
        designPlanRenderScene.setUserId(userId);
        designPlanRenderScene.setPlanName(name);
        designPlanRenderScene.setStart(start);
        designPlanRenderScene.setLimit(limit);
        designPlanRenderScene.setSpaceFunctionId(spaceFunctionId);
        envelope = designPlanAutoRenderServiceImpl.getrenderPicByPageV2(designPlanRenderScene);
		envelope.setMsgId(msgId);
		envelope.setMessage("success");
		return envelope;

	}

	/**
	 * 获取720图片  ，，没有按钮的
	 * 
	 * @param designPlan
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/getPanoPicture")
	@ResponseBody
	public Object getPanoPicture(@RequestBody MobileRenderingModel designPlan, HttpServletRequest request) {
		if (designPlan.getThumbId() == null) {
			return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
		}
		
		ResRenderPic res = resRenderPicService.get(designPlan.getThumbId());
		//获取用户信息
		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
		String appKey = map.get("appKey");
		String cacheKey = "user_H5Token:" + appKey;
		LoginUser loginUser = (LoginUser) CacheManager.getInstance().getCacher().getObject(cacheKey);
		//获取二维码路径   没有按钮的
		String panoPath =  this.getQRpicPath(res,loginUser);
		Map<String,String> returnMap=new HashMap<String, String>();
		returnMap.put("url", panoPath);
		returnMap.put("contentUrl",res.getId().toString());
//		return new ResponseEnvelope<ResRenderPic>(panoPath);
		return new ResponseEnvelope<>(returnMap);
	}

	/**
	 * 获取空间类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSpace")
	@ResponseBody
	public Object getSpace() {
		List<SysDictionary> list = sysDictionaryService.findAllByType("houseType");
		return new ResponseEnvelope<SysDictionary>(list.size(), list);
	}

	/**
	 * 我的设计   效果图    详情查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/detailsSee")
	@ResponseBody
	public ResponseEnvelope<ResRenderPic> detailsSee(@RequestBody MobileRenderingModel model, HttpServletRequest request){
		String detailsSeeType = model.getDetailsSeeType();
		String picId = model.getSmallPicId();
		String msgId = model.getMsgId();
		//获取登录用户
		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
		String appKey = map.get("appKey");
		String cacheKey = "user_H5Token:" + appKey;
		LoginUser loginUser = (LoginUser) CacheManager.getInstance().getCacher().getObject(cacheKey);
		
		boolean valid = this.validPlanReleaseParam(msgId,picId);
		if(!valid) {
			return new ResponseEnvelope<ResRenderPic>(false,"参数不完整",msgId);
		}
		if(StringUtils.isEmpty(detailsSeeType)){
			detailsSeeType = "";
		}
		return designPlanRecommendedServiceV2.detailsSee(msgId,picId,detailsSeeType,loginUser);
	}
	/**
	 * 参数完整性判断
	 * @param args
	 * @return
	 */
	private boolean validPlanReleaseParam(String... args) {
		boolean result = true;
		for(String arg :args) {
			if(StringUtils.isEmpty(arg)) {
				result = false;
				return result;
			}
		}
		return result;
	}
	/**
	 * 获取该方案的产品清单
	 * @param planId
	 * @param userId
	 * @param userType
	 * @return
	 */
	@RequestMapping("/getDesignPlanProductList")
	@ResponseBody
	public Object getDesignPlanProductList(
			@RequestBody MobileRenderingModel model, HttpServletRequest request) {
		
		return mobileSearchDesignPlanProductService.getDesignPlanProductList(model);
	}

	/**
	 * 生成二维码图的url
	 * @param resPic
	 * @param loginUser
	 * @return
	 */
	public String getQRpicPath(ResRenderPic resPic, LoginUser loginUser){
		int renderingType = resPic.getRenderingType().intValue();
		String picPath = this.getQRCodeInfo(resPic, loginUser);
		if(resPic.getRenderingType() != null){
			if( RenderTypeCode.COMMON_720_LEVEL == renderingType || RenderTypeCode.HD_720_LEVEL == renderingType ){// 普通单图720
				picPath = Utils.getPropertyName("app","app.server.url","")
						+ Utils.getPropertyName("app","app.server.siteName","") + picPath;
			}else if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){// 720漫游
					picPath = Utils.getPropertyName("app","app.server.url","")
							+ Utils.getPropertyName("app","app.server.siteName","") + picPath;
			} else if(RenderTypeCode.COMMON_VIDEO == renderingType){//普通视频
				picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			}else if(RenderTypeCode.HD_VIDEO == renderingType){//高清视频
				picPath = Utils.getPropertyName("app","app.resources.url","")  + picPath;
			}else{
				picPath=Utils.getValue("app.resources.url", "")+picPath;
			}
		}else{
			/*取普通渲染图路径*/
			picPath=Utils.getValue("app.resources.url", "")+picPath;
		}
		return picPath;
	}
	/**
	 * 二维码的信息
	 * @param resPic
	 * @param loginUser
	 * @return
	 */
	public String getQRCodeInfo(ResRenderPic resPic, LoginUser loginUser) {

		String picPath="";
		String code = "";
		if(resPic.getRenderingType() != null){
			if( RenderTypeCode.COMMON_720_LEVEL == resPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resPic.getRenderingType() ){// 普通单图720
				ResRenderPic singleCode = resRenderPicMapper.get720SingleCode(resPic);//得到720单点分享需要的code
				if(singleCode != null && StringUtils.isNotEmpty(singleCode.getSysCode())){
					code = singleCode.getSysCode();
				}else{
					logger.error("ResRenderPic is null ,res_render_pic_id="+resPic.getId());
					return null;
				}
				picPath = "pages/vr720/vr720MobileSingle.htm?code="+code;
			}else if( RenderTypeCode.ROAM_720_LEVEL == resPic.getRenderingType() ){// 720漫游
				// 获取720漫游组信息，传过来的pid问截图ID。渲染图列表接口返回了缩略图列表和对应的截图ID
				logger.error("id = "+ resPic.getId());
				DesignRenderRoam romanCode = resRenderPicMapper.get720RomanCode(resPic);
				logger.error("romanCode  == " + romanCode.getUuid());
				if(romanCode != null && StringUtils.isNotEmpty(romanCode.getUuid())){
					code = romanCode.getUuid();
					picPath = "pages/vr720/vr720RoamOfMobile.htm?code="+code;
				}else{
					logger.error("DesignRenderRoam is null ,res_render_pic_id="+resPic.getId());
					return null;
				}
			} else if(RenderTypeCode.COMMON_VIDEO == resPic.getRenderingType() || RenderTypeCode.HD_VIDEO == resPic.getRenderingType()){//普通视频
				ResRenderVideo resRenderVideo = new ResRenderVideo();
				resRenderVideo.setIsDeleted(0);
				resRenderVideo.setSysTaskPicId(resPic.getId());
				List<ResRenderVideo> videoList = resRenderVideoService.getList(resRenderVideo);
				if(videoList == null || videoList.size()<=0 ){
					return null;
				}else if(videoList != null && videoList.size() == 1 ){
					picPath = videoList.get(0).getVideoPath();
				}else{
					return null;
				}
			}else{
				picPath=resPic.getPicPath();
			}
		}else{
			/*取普通渲染图路径*/
			picPath=resPic.getPicPath();
		}
		return picPath;
	}
	/**
	 * 产品替换
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/productReplaceList")
	@ResponseBody
	public ResponseEnvelope<ProductReplaceTaskDetail>  productReplaceList(Integer taskId,String msgId) {
		List<ProductReplaceTaskDetail> replaceProductList = mobilePayRenderService.selectListByTaskId(taskId,msgId);
		ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(replaceProductList,msgId);
		return  result;
	}
	/**
	 * 产品移除
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/productDelList")
	@ResponseBody
	public ResponseEnvelope<ProductReplaceTaskDetail>  productDelList(Integer taskId,String msgId) {
		List<ProductReplaceTaskDetail> replaceProductList =  mobilePayRenderService.selectDelListByTaskId(taskId,msgId);
		ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(replaceProductList,msgId);
		return  result;
	}
	/**
	 * 组合替换
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/groupProductReplaceList")
	@ResponseBody
	public ResponseEnvelope<ProductGroupReplaceTaskDetail>  groupProductReplaceList(Integer taskId,String msgId) {
		List<ProductGroupReplaceTaskDetail> groupReplaceList = mobilePayRenderService.selectGroupReplaceListByTaskId(taskId, msgId);
		ResponseEnvelope<ProductGroupReplaceTaskDetail> result = new ResponseEnvelope<ProductGroupReplaceTaskDetail>(groupReplaceList,msgId);
		return result; 
	}
	/**
	 * 材质替换
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/textureReplaceList")
	@ResponseBody
	public ResponseEnvelope<ProductReplaceTaskDetail>  textureReplaceList(Integer taskId,String msgId) {
		List<ProductReplaceTaskDetail> selectTextureReplaceList =mobilePayRenderService.selectTextureReplaceListByTaskId(taskId, msgId);
		ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(selectTextureReplaceList,msgId);
		return  result;
	}
	
	/**
	 * 逻辑删除我的设计、我的任务、我的消息
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteMyDesignPlanAndTask")
	@ResponseBody
	public Object deleteMyDesignPlanAndTask(@RequestBody MobileRenderingModel model) {
		Integer planId = model.getPlanId();
		return mobileDesignPlanService.deleteMyDesignPlanAndTask(planId);
	}
}
