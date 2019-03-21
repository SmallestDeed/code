package com.nork.design.controller.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.design.model.*;
import com.nork.design.model.constant.DesignPlanType;
import com.nork.design.model.constant.PlanVisibleCode;
import com.nork.design.model.search.DesignPlanCustomizedProductOrderSearch;
import com.nork.design.service.*;
import com.nork.product.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.nork.design.model.constant.ContextType;

import com.nork.common.constant.util.SystemCommonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.async.AssembleRenderTaskFile;
import com.nork.common.async.AssembleRenderTaskFileParameter;
import com.nork.common.async.Result;
import com.nork.common.async.TaskExecutor;
import com.nork.common.async.UnitUpdateParameter;
import com.nork.common.async.UnitUpdateTask;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.AESUtil;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.ImageUtils;
import com.nork.common.util.ResizeImage;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.cache.DesignPlanCommentCacher;
import com.nork.design.cache.DesignPlanLikeCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.common.RecommendedDecorateState;
import com.nork.design.dao.DesignPlanOperationLogMapper;
import com.nork.design.exception.DesignPlanException;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanComment;
import com.nork.design.model.DesignPlanLike;
import com.nork.design.model.DesignPlanOperationLog;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanResult;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.DesignTempletProductResult;
import com.nork.design.model.PlanMode;
import com.nork.design.model.RenderConfig;
import com.nork.design.model.RenderPicVO;
import com.nork.design.model.ResRenderPicQO;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.model.UnityPlanProduct;
import com.nork.design.model.UnitySpaceCommon;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.search.DesignPlanCommentSearch;
import com.nork.design.model.search.DesignPlanLikeSearch;
import com.nork.design.model.search.DesignPlanSearch;
import com.nork.design.model.small.DesignPlanSmall;
import com.nork.design.service.DesignPlanCommentService;
import com.nork.design.service.DesignPlanLikeService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.SaveRenderPicService;
import com.nork.design.service.TempletPlanOperationHistoryService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.BaseHouseService;
import com.nork.home.service.SpaceCommonService;
import com.nork.job.SaveRenderPicJob;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.GroupProductDetailsCache;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductUsageCountService;
import com.nork.render.model.RenderTask;
import com.nork.render.model.RenderTaskStates;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.model.vo.RenderCheckVo;
import com.nork.render.service.RenderTaskService;
import com.nork.system.cache.DesignerWorksCacher;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.dao.SysDictonaryDao;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResHousePic;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictBigAndSmallTypeInfoModel;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserFans;
import com.nork.system.model.search.ResPicSearch;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.model.small.ResFileSmall;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResHousePicService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserFansService;
import com.nork.system.service.SysUserService;
import com.nork.task.cache.SysTaskCacher;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskResult;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.service.SysTaskService;
import com.nork.threadpool.RenderJobType;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import com.nork.threadpool.ThreadPoolConfig;
import org.springframework.web.multipart.MultipartRequest;

/**
 * @version V1.0
 * @Title: DesignPlanController.java
 * @Package com.nork.design.controller
 * @Description:设计模块-设计方案Controller
 * @createAuthor pandajun
 * @CreateDate 2015-07-03 17:09:51
 */
@Controller
@RequestMapping("/{style}/web/design/designPlan")
public class WebDesignPlanController {

	/*** 获取配置文件 webSocket.properties */
	private final static ResourceBundle webSocket = ResourceBundle.getBundle("config/webSocket");
	private static Logger logger = Logger.getLogger(WebDesignPlanController.class);
	private final JsonDataServiceImpl<SysDictionary> JsonUtil = new JsonDataServiceImpl<SysDictionary>();
	private final JsonDataServiceImpl<DesignPlan> JsonUtilTwo = new JsonDataServiceImpl<DesignPlan>();
	private final JsonDataServiceImpl<DesignPlan> designJsonUtil = new JsonDataServiceImpl<DesignPlan>();
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	private final String SERVERURL = Utils.getValue("app.server.url", "http://localhost:18080/onlineDecorate");
	private final String RESOURCESURL = Utils.getValue("app.resources.url",
			"http://localhost:18080/onlineDecorate/upload");
	public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResHousePicService resHousePicService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private DesignPlanCommentService designPlanCommentService;
	@Autowired
	private DesignPlanLikeService designPlanLikeService;
	@Autowired
	private SysUserFansService sysUserFansService;
	@Autowired
	private SysTaskService sysTaskService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private ProductUsageCountService productUsageCountService;
	@Autowired
	private GroupProductService groupProductService;
	@Resource
	private BaseHouseService baseHouseService;
	@Autowired
	private RenderTaskService renderTaskService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private ResDesignService resDesignService;
	@Autowired
	private SysDictonaryDao sysDictonaryDao;
	@Autowired
	private TempletPlanOperationHistoryService templetPlanOperationHistoryService;
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	@Autowired
	private SaveRenderPicService saveRenderPicService;
	@Autowired
	private DesignPlanOperationLogService designPlanOperationLogService;
	@Autowired
	private ThreadPoolManager uploadFileThreadPoolManager;
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private DesignPlanRenderService designPlanRenderService;
	@Autowired
	private OptimizePlanService optimizePlanService;
	@Autowired
	private DesignPlanCustomizedProductOrderService designPlanCustomizedProductOrderService;
	public final static ResourceBundle render = ResourceBundle.getBundle("render");
	// 任务服务器
	public final static String TASK_SERVER_HOSTS = render.getString("taskServerHosts");
	public static String[] taskServers = null;
	public static Integer taskAllocationIndex = 0;
	// 渲染服务器
	public final static String RENDER_SERVER_HOSTS = render.getString("renderServerHosts");
	public static String[] renderServers = null;
	public static String[] renderStorageType = null;
	public static Integer renderAllocationIndex = 0;
	public static Map<String, RenderConfig> renderServersMap = new HashMap<>();
	public final static boolean JOB_LOG_FLAG = "true".equals(Utils.getValue("jobLog", "false")) ? true : false;

	private void initRender() {
		if (StringUtils.isNotBlank(TASK_SERVER_HOSTS)) {
			taskServers = TASK_SERVER_HOSTS.split(",");
		}
		if (StringUtils.isNotBlank(RENDER_SERVER_HOSTS)) {
			JSONArray jsonArray = JSONArray.fromObject(RENDER_SERVER_HOSTS);
			if (jsonArray != null) {
				renderServers = new String[jsonArray.size()];
				renderStorageType = new String[jsonArray.size()];
				RenderConfig renderConfig = null;
				for (int i = 0; i < jsonArray.size(); i++) {
					renderConfig = (RenderConfig) JSONObject.toBean((JSONObject) jsonArray.get(i), RenderConfig.class);
					renderServersMap.put(renderConfig.getRenderServer() + "_" + renderConfig.getStorageType(),
							renderConfig);
					renderServers[i] = renderConfig.getRenderServer();
					renderStorageType[i] = renderConfig.getStorageType();
				}
			} else {
				logger.error("渲染服务器renderServerHosts配置的格式不正确,请按照配置文件中提供的例子正确配置！");
			}
		} else {
			logger.error("渲染服务器renderServerHosts配置的格式不正确,请按照配置文件中提供的例子正确配置！");
		}
	}

	/**
	 * U3D刷新设计方案
	 * 
	 * @param ：designPlanId
	 * @param request
	 */
	@RequestMapping(value = "/getDesignPlan1")
	public String getDesignPlan(Integer designPlanId, HttpServletRequest request, Integer newFlag) throws Exception {
		request.setAttribute("newFlag", newFlag);
		request.setAttribute("designPlanId", designPlanId);
		return "/online/design/design_main";
	}

	/**
	 * 通过样板房构建设计方案
	 * 
	 * @param
	 * @return String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/createDesign")
	public String createDesign(String templeId, HttpServletRequest request) {
		try {
			if (templeId == null || templeId == null) {
				return "";
			}

			DesignPlan designPlan = new DesignPlan();
			DesignTemplet designTemplet = designTempletService.get(new Integer(templeId));
			if (designTemplet == null) {
				return "";
			}

			Map map = new HashMap();
			map.put("templetId", designTemplet.getId());
			map.put("spaceCommonId", designTemplet.getSpaceCommonId());

			// 校验是否可以存储,间隔时间由配置文件决定
			boolean isNewPlan = isNewPlan(request, map);
			Integer newFlag = 0;
			if (isNewPlan) {
				newFlag = 1;
				// 媒介类型.如果没有值，则表示为web前端（2）
				String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);

				// 从样板房中拷贝数据到设计方案中
				LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				// String DesignName = "";
				designPlan.setMediaType(Utils.getIntValue(mediaType));
				String planCode = designTemplet.getDesignCode() + "_" + Utils.generateRandomDigitString(6);
				designPlan.setPlanCode(planCode);
				designPlan.setPlanName(designTemplet.getDesignName());// 设计方案名称
				designPlan.setUserId(loginUser.getId());// 用户id
				designPlan.setDesignSourceType(7);// 样板房
				designPlan.setDesignId(designTemplet.getId());// 样板房id
				designPlan.setPicId(designTemplet.getPicId());// 设计方案缩略图
				// //设计方案的模型应该从数据库中拷贝一份新的,否则删掉方案后,样本房中的模型将会被删除
				// Integer model3dId =
				// this.copyFile(designTemplet.getModel3dId() == null ? "" :
				// designTemplet.getModel3dId().toString(), "model",
				// "design.designPlan.3dmodel", null, request);
				// designPlan.setModel3dId(model3dId);//设计方案3d模型文件

				// Integer modelU3dId =
				// this.copyFile(designTemplet.getModelU3dId()==null?"":designTemplet.getModelU3dId().toString(),
				// "model", "design.designPlan.u3dmodel", null, request);
				// designPlan.setModelU3dId(modelU3dId);//设计方案u3d模型文件

				// 各媒介模型拷贝
				// if (designTemplet.getIpadModelU3dId() != null && mediaType ==
				// 7) {
				// Integer ipadModelU3dId =
				// this.copyFile(designTemplet.getIpadModelU3dId().toString(),
				// "model", "design.designPlan.u3dmodel.ipad", null, request);
				// designPlan.setModelU3dId(ipadModelU3dId);
				// } else {
				// designPlan.setModelU3dId(0);
				// }
				// if (designTemplet.getIosModelU3dId() != null && mediaType ==
				// 5) {
				// Integer iosModelU3dId =
				// this.copyFile(designTemplet.getIosModelU3dId().toString(),
				// "model", "design.designPlan.u3dmodel.ios", null, request);
				// designPlan.setModelU3dId(iosModelU3dId);
				// } else {
				// designPlan.setModelU3dId(0);
				// }
				// if (designTemplet.getAndroidModelU3dId() != null && mediaType
				// == 6) {
				// Integer androidModelU3dId =
				// this.copyFile(designTemplet.getAndroidModelU3dId().toString(),
				// "model", "design.designPlan.u3dmodel.android", null,
				// request);
				// designPlan.setModelU3dId(androidModelU3dId);
				// } else {
				// designPlan.setModelU3dId(0);
				// }
				// if (designTemplet.getMacBookpcModelU3dId() != null &&
				// mediaType == 4) {
				// Integer macBookPcModelU3dId =
				// this.copyFile(designTemplet.getMacBookpcModelU3dId().toString(),
				// "model", "design.designPlan.u3dmodel.macBookPc", null,
				// request);
				// designPlan.setModelU3dId(macBookPcModelU3dId);
				// } else {
				// designPlan.setModelU3dId(0);
				// }
				// if (designTemplet.getPcModelU3dId() != null && mediaType ==
				// 3) {
				// Integer pcModelU3dId =
				// this.copyFile(designTemplet.getPcModelU3dId().toString(),
				// "model", "design.designPlan.u3dmodel.windowsPc", null,
				// request);
				// designPlan.setModelU3dId(pcModelU3dId);
				// } else {
				// designPlan.setModelU3dId(0);
				// }

				designPlan.setSpaceCommonId(designTemplet.getSpaceCommonId());// 适用的空间ID
				designPlan.setDesignStyleId(null);// 设计风格,后续在更新产品时,需根据产品类型判断
				// 空房模式图形和样板房模式图形拷贝
				// Integer eveningFileId=designTemplet.getEveningFileId();
				// Integer dawnFileId=designTemplet.getDawnFileId();
				// designPlan.setEveningFileId(eveningFileId);
				// designPlan.setDawnFileId(dawnFileId);

				Integer spaceId = designTemplet.getSpaceCommonId();
				String modelU3dId = "-1";
				Integer modelU3dnewId = -1;
				// if (spaceId != null && spaceId > 0) {
				// SpaceCommon spaceCommon = spaceCommonService.get(spaceId);
				// designPlan.setSysCode(spaceCommon.getSpaceCode() + "_" +
				// Utils.generateRandomDigitString(6));// 空间编码(场景编码)
				// // 根据媒介类型赋值模型
				// // U3D空间公共资源模型路径
				// modelU3dId = this.getU3dModelId(mediaType == null ? "2" :
				// mediaType.toString(), spaceCommon);
				// modelU3dnewId = this.copyFile(modelU3dId == null ? "" :
				// modelU3dId.toString(), "model",
				// "design.designPlan.u3dmodel", null, request, planCode);
				//
				// designPlan.setModelU3dId(modelU3dnewId == null ? new
				// Integer(-1) : modelU3dnewId);
				// } else {
				// designPlan.setModelU3dId(-1);
				// }
				// 只需要拷贝配置及产品列表,样板房模型中为固定模型,需要拷贝,公共资源从spacecommon中实时获取也不需要拷贝
				String modelId = this.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), designTemplet);
				if (StringUtils.isNotBlank(modelId)) {
					Integer resFileId = this.copyFile(modelId, "model", "design.designPlan.modelTemplet", null, request,
							planCode);
					designPlan.setModelId(resFileId);
				} else {
					designPlan.setModelId(-1);
				}

				Integer resFileId = this.planCopyFile(
						designTemplet.getConfigFileId() == null ? "" : designTemplet.getConfigFileId().toString(),
						"file", "design.designPlan.u3dconfig", null, request, planCode);
				designPlan.setConfigFileId(resFileId);// // 设计方案配置文件
				// 样板房ID
				designPlan.setDesignTemplateId(designTemplet.getId());
				// 创建新的设计方案信息
				designPlan.setIsOpen(0);
				sysSave(designPlan, request);
				int id = designPlanService.add(designPlan);
				logger.info("add:id=" + id);
				designPlan.setId(id);

				// 回写资源信息
				ResFile rf = new ResFile();
				rf.setId(resFileId);
				rf.setBusinessId(id);
				resFileService.update(rf);
				// 回写资源信息
				// if(StringUtils.isNotEmpty(model3dId)
				// && new Integer(model3dId).intValue() >0){
				// ResModel rm_3d = new ResModel();
				// rm_3d.setId(new Integer(model3dId));
				// rm_3d.setBusinessId(id);
				// resModelService.update(rm_3d);
				// }
				// 回写资源信息
				if (modelU3dnewId != null && modelU3dnewId.intValue() > 0) {
					ResModel rm_u3d = new ResModel();
					rm_u3d.setId(modelU3dnewId);
					rm_u3d.setBusinessId(id);
					resModelService.update(rm_u3d);
				}

				// 公共资源
				if (designPlan.getModelId() != null && designPlan.getModelId().intValue() > 0) {
					ResModel rm_u3d = new ResModel();
					rm_u3d.setId(designPlan.getModelId());
					rm_u3d.setBusinessId(id);
					resModelService.update(rm_u3d);
				}

				// 样板房设计方案的产品列表代入
				DesignTempletProduct templetProduct = new DesignTempletProduct();
				templetProduct.setIsDeleted(0);
				templetProduct.setDesignTempletId(designTemplet.getId());
				List<DesignTempletProduct> temProductList = designTempletProductService.getList(templetProduct);
				for (DesignTempletProduct dtp : temProductList) {
					DesignPlanProduct planProduct = new DesignPlanProduct();
					planProduct.setPlanId(id);
					planProduct.setProductId(dtp.getProductId());
					planProduct.setProductSequence(dtp.getProductSequence());
					String isHide = Utils.getValue("design.designPlan.product.isHide", "true");
					if ("true".equals(isHide.trim())) {
						BaseProduct baseProduct = baseProductService.get(dtp.getProductId());
						planProduct.setIsHide(1);
						if (baseProduct != null) {
							if (StringUtils.isNotBlank(baseProduct.getProductTypeValue())
									&& baseProduct.getProductSmallTypeValue() != null) {
								// 天花msto1，地面flor2,墙面wa3，门的原模默认显示
								if (("1".equals(baseProduct.getProductTypeValue())
										&& baseProduct.getProductSmallTypeValue().intValue() == 3)
										|| ("2".equals(baseProduct.getProductTypeValue())
												&& baseProduct.getProductSmallTypeValue().intValue() == 5)
										|| ("3".equals(baseProduct.getProductTypeValue())
												&& baseProduct.getProductSmallTypeValue().intValue() == 8)
										|| ("4".equals(baseProduct.getProductTypeValue())
												&& baseProduct.getProductSmallTypeValue().intValue() == 1)) {
									planProduct.setIsHide(0);
								}
							}
						}
					} else {
						planProduct.setIsHide(0);
					}
					// 相机位置配置文件
					// Integer locationFileId =
					// this.copyFile(dtp.getLocationFileId()==null?"":dtp.getLocationFileId().toString(),
					// "file",
					// "design.designPlan.locationFile", null, request);
					// planProduct.setLocationFileId(locationFileId);
					planProduct.setLocationFileId(-1);
					/** 样板房产品id **/
					planProduct.setPlanProductId(dtp.getId());
					planProduct.setInitProductId(dtp.getProductId());
					planProduct.setDisplayStatus(0);// 0表示空模，1表示模型
					planProduct.setPosIndexPath(dtp.getPosIndexPath());// 挂节点信息
					planProduct.setPosName(dtp.getPosName());// 挂节点名称
					sysSave(planProduct, request);
					designPlanProductService.add(planProduct);
					// Integer planProductId =

					// 回写资源信息
					// ResFile rf2 = new ResFile();
					// rf2.setId(locationFileId);
					// rf2.setBusinessId(planProductId);
					// resFileService.update(rf2);
				}

			} else {
				// 从数据库中读取最大值的数据,进入
				if (map != null && map.size() > 0) {
					List list = (List) map.get("list");
					if (list != null && list.size() > 0) {
						Map resultMap = (Map) list.get(0);
						Integer spaceCommonId = (Integer) resultMap.get("space_common_id");
						Date gmtModified = Utils.parseDate(
								Utils.formatDate((Date) resultMap.get("max(gmt_modified)"), Utils.DATE_TIME),
								Utils.DATE_TIME);
						Integer userIdDb = (Integer) resultMap.get("user_id");
						DesignPlan lastdesignPlan = new DesignPlan();
						lastdesignPlan.setUserId(userIdDb);
						lastdesignPlan.setGmtModified(gmtModified);
						lastdesignPlan.setSpaceCommonId(spaceCommonId);

						List<DesignPlan> newList = designPlanService.getList(lastdesignPlan);
						designPlan = newList.get(0);
					}
				}
			}
			return "redirect:/online/web/design/designPlan/getDesignPlan.htm?designPlanId=" + designPlan.getId()
					+ "&newFlag=" + newFlag;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/online/errors/error";
	}

	/**
	 * 设计师主页-方案列表接口
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/userPlanList")
	@ResponseBody
	public Object userPlanList(@PathVariable String style, @ModelAttribute("designPlan") DesignPlan designPlan,
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "msgId", required = false) String msgId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (userId == null) {
			msg = "参数userId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}

		designPlan.setUserId(userId);
		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");
		int total = 0;
		List<DesignPlan> list = null;
		if (Utils.enableRedisCache()) {
			list = DesignCacher.getPlanList(designPlan);
		} else {
			list = designPlanService.getPlanList(designPlan);
		}

		try {
			if (Utils.enableRedisCache()) {
				total = DesignCacher.getPlanCount(designPlan);
			} else {
				total = designPlanService.getPlanCount(designPlan);
			}

			for (DesignPlan desPlan : list) {
				SysUser user = null;
				if (Utils.enableRedisCache()) {
					user = SysUserCacher.get(desPlan.getUserId());
				} else {
					user = sysUserService.get(desPlan.getUserId());
				}

				if (user != null) {
					desPlan.setPlanUserName(user.getUserName());
				}
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = null;
					if (Utils.enableRedisCache()) {
						spaceCommon = SpaceCommonCacher.get(desPlan.getSpaceCommonId());
					} else {
						spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					}

					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
					}
				}
				if (desPlan.getPicId() != null) {
					ResPic resPic = null;
					if (Utils.enableRedisCache()) {
						resPic = ResourceCacher.getPic(desPlan.getPicId());
					} else {
						resPic = resPicService.get(desPlan.getPicId());
					}

					if (resPic != null) {
						desPlan.setPicPath(resPic.getPicPath());
					}
				}

				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				desPlan.setCollectState(getDesignCollectState(desPlan.getId(), request));
				// 评论总数
				desPlan.setCommentCount(getCommentCount(desPlan.getId(), request));

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", msgId);
		}
		if ("small".equals(style) && list != null && list.size() > 0) {
			String designPlanJsonList = JsonUtil.getListToJsonData(list);
			List<DesignPlanSmall> smallList = new JsonDataServiceImpl<DesignPlanSmall>()
					.getJsonToBeanList(designPlanJsonList, DesignPlanSmall.class);
			return new ResponseEnvelope<DesignPlanSmall>(total, smallList, msgId);
		}
		return new ResponseEnvelope<DesignPlan>(total, list, msgId);
	}
	
	/**
	 * 获取方案评论数量
	 * 
	 * @param designId
	 * @param request
	 * @return
	 */
	public Integer getCommentCount(Integer designId, HttpServletRequest request) {
		// 获取该方案的评论数量
		DesignPlanCommentSearch search = new DesignPlanCommentSearch();
		search.setIsDeleted(0);
		search.setDesignPlanId(designId);
		List<DesignPlanComment> commentList = null;
		if (Utils.enableRedisCache()) {
			commentList = DesignPlanCommentCacher.getUDPCList(search);
		} else {
			commentList = designPlanCommentService.getUDPCList(search);
			// commentList =
			// designPlanCommentService.getList(designPlanComment);
		}

		if (commentList != null && commentList.size() > 0) {
			return commentList.size();
		} else {
			return 0;
		}
	}

	/**
	 * 获取设计收藏状态
	 * 
	 * @param designId
	 * @param request
	 * @return
	 */
	public Integer getDesignCollectState(Integer designId, HttpServletRequest request) {
		return 0;
	}

	/**
	 * 通过设计方案id查询U3D设计方案对象
	 * 
	 * @param ：designPlanId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/u3dDesignPlan")
	@ResponseBody
	public Object u3dDesignPlan(Integer designPlanId, String msgId, HttpServletRequest request,
			HttpServletResponse response, Integer newFlag) throws Exception {
		if (designPlanId == null) {
			return new ResponseEnvelope<UnityDesignPlan>(false, "数据异常", msgId);
		}
		if (newFlag == null) {
			newFlag = 0;
		}
		// 媒介类型.如果没有值，则表示为web前端（2）
		// Integer mediaType =
		// request.getSession().getAttribute("mediaType")==null?2:(Integer)request.getSession().getAttribute("mediaType");
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);

		// 获取设计方案数据信息
		DesignPlan designPlan = designPlanService.get(designPlanId);

		// 构建unity模型加载时需要的model对象:返回设计方案,空间模型,产品列表及产品配置等信息
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		unityDesignPlan.setNewFlag(newFlag);// 是否是第一次进入
		// 一.获取设计方案信息
		unityDesignPlan.setServiceUrl(SERVERURL);// 访问地址
		// unityDesignPlan.setUploadDir(UPLOADDIR);//文件上传路径,资源类信息需使用访问地址+上传路径+文件路径
		unityDesignPlan.setDesignPlanId(designPlan.getId());// 设计方案id
		unityDesignPlan.setResourcesUrl(RESOURCESURL);// 资源文件访问地址

		Integer modelTemplteFileId = designPlan.getModelId();
		// 将资源id转换为访问路径(模型设计方案公共资源)
		if (modelTemplteFileId != null && modelTemplteFileId.intValue() > 0) {
			ResModel resModel = resModelService.get(modelTemplteFileId);
			String filePath = resModel.getModelPath();
			if (!StringUtils.isEmpty(filePath)) {
				unityDesignPlan.setU3dModelPath(filePath);
			} else {
				unityDesignPlan.setU3dModelPath("");
			}
		} else {
			unityDesignPlan.setU3dModelPath("");
		}

		Integer configFileId = designPlan.getConfigFileId();
		// 将资源id转换为访问路径(模型设计方案总体配置文件)
		if (configFileId != null && configFileId.intValue() > 0) {

			// ResFile resFile =
			// resFileService.get(designPlan.getConfigFileId());
			ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());
			String filePath = resDesign.getFilePath();
			if (!StringUtils.isEmpty(filePath)) {
				designPlan.setFilePath(resDesign.getFilePath());
			}

		}
		// 模型总体配置文件
		unityDesignPlan.setDesignPlanConfigPath(designPlan.getFilePath());
		// U3D界面UI文件
		unityDesignPlan.setDesignPlanUIPath("/pages/online/resource/UI.assetbundle");
		// 场景信息
		/*
		 * if( designPlan.getEveningFileId() != null ){ ResFile resFile =
		 * resFileService.get(designPlan.getEveningFileId()); if( resFile != null ){
		 * unityDesignPlan.setEveningFilePath(resFile.getFilePath()); } } if(
		 * designPlan.getDawnFileId() != null ){ ResFile resFile =
		 * resFileService.get(designPlan.getDawnFileId()); if( resFile != null ){
		 * unityDesignPlan.setDawnFilePath(resFile.getFilePath()); } } if(
		 * designPlan.getNightFileId() != null ){ ResFile resFile =
		 * resFileService.get(designPlan.getNightFileId()); if( resFile != null ){
		 * unityDesignPlan.setNightFilePath(resFile.getFilePath()); } }
		 */
		// 空白图和样本房图
		// Integer emptyModePicId = designPlan.getEveningFileId();
		// Integer templetModePicId = designPlan.getDawnFileId();
		// String emptyModePicPath="";
		// String templetModePicPath="";
		// if(emptyModePicId != null){
		// ResPic respic = resPicService.get(emptyModePicId);
		// if(respic!= null && StringUtils.isEmpty(respic.getPicPath())){
		// emptyModePicPath = respic.getPicPath();
		// }
		// }
		//
		// if(templetModePicId != null){
		// ResPic respic = resPicService.get(templetModePicId);
		// if(respic!= null && StringUtils.isEmpty(respic.getPicPath())){
		// templetModePicPath = respic.getPicPath();
		// }
		// }
		//
		// unityDesignPlan.setEmptyModePicPath(emptyModePicPath);
		// unityDesignPlan.setTempletModePicPath(templetModePicPath);
		//
		// //设计模式
		DesignTemplet designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
		if (designTemplet == null) {
			logger.error("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
					+ designPlan.getId());
		} else {
			if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
				logger.error("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
						+ ",designId=" + designPlan.getId());
			}
		}
		if (!StringUtils.isEmpty(designTemplet.getDesignCode()) && designTemplet.getDesignCode().endsWith("_000")) {
			unityDesignPlan.setDesignMode("0");
		} else {
			unityDesignPlan.setDesignMode("1");
		}
		// 方案初始化模型(从样板房中拷贝而来)
		// Integer modelU3dId = designPlan.getWebModelU3dId();
		// if ( modelU3dId != null && modelU3dId > 0 && mediaType == 2 ) {
		// ResModel resModel = resModelService.get(modelU3dId);
		// if (resModel != null && resModel.getModelPath() != null) {
		// unityDesignPlan.setU3dModelPath(resModel.getModelPath());
		// }
		// } else {
		// unityDesignPlan.setU3dModelPath("");
		// }

		// 各媒介模型拷贝
		// if (designPlan.getIpadModelU3dId() != null && mediaType == 7 ) {
		// ResModel resModel =
		// resModelService.get(designPlan.getIpadModelU3dId());
		// if (resModel != null && resModel.getModelPath() != null) {
		// unityDesignPlan.setU3dModelPath(resModel.getModelPath());
		// }
		// } else {
		// unityDesignPlan.setU3dModelPath("");
		// }
		// if (designPlan.getIosModelU3dId() != null && mediaType == 5 ) {
		// ResModel resModel =
		// resModelService.get(designPlan.getIosModelU3dId());
		// if (resModel != null && resModel.getModelPath() != null) {
		// unityDesignPlan.setU3dModelPath(resModel.getModelPath());
		// }
		// } else {
		// unityDesignPlan.setU3dModelPath("");
		// }
		// if (designPlan.getAndroidModelU3dId() != null && mediaType == 6 ) {
		// ResModel resModel =
		// resModelService.get(designPlan.getAndroidModelU3dId());
		// if (resModel != null && resModel.getModelPath() != null) {
		// unityDesignPlan.setU3dModelPath(resModel.getModelPath());
		// }
		// } else {
		// unityDesignPlan.setU3dModelPath("");
		// }
		// if (designPlan.getMacBookpcModelU3dId() != null && mediaType == 4 ) {
		// ResModel resModel =
		// resModelService.get(designPlan.getMacBookpcModelU3dId());
		// if (resModel != null && resModel.getModelPath() != null) {
		// unityDesignPlan.setU3dModelPath(resModel.getModelPath());
		// }
		// } else {
		// unityDesignPlan.setU3dModelPath("");
		// }
		// if (designPlan.getPcModelU3dId() != null && mediaType == 3 ) {
		// ResModel resModel =
		// resModelService.get(designPlan.getPcModelU3dId());
		// if (resModel != null && resModel.getModelPath() != null) {
		// unityDesignPlan.setU3dModelPath(resModel.getModelPath());
		// }
		// } else {
		// unityDesignPlan.setU3dModelPath("");
		// }

		// 二.获取空间信息
		UnitySpaceCommon unitySpaceCommon = new UnitySpaceCommon();
		// 从
		Integer spaceId = designPlan.getSpaceCommonId();
		if (spaceId != null && spaceId > 0) {
			SpaceCommon spaceCommon = spaceCommonService.get(spaceId);
			String spaceCode = "";
			if (StringUtils.isNotBlank(designPlan.getSysCode())) {
				spaceCode = designPlan.getSysCode().substring(0, designPlan.getSysCode().lastIndexOf("_"));
			}
			unitySpaceCommon.setSpaceCommonId(spaceId);
			unitySpaceCommon.setSpaceCode(spaceCode);// 空间编码(场景编码)
			// U3D空间公共资源模型路径
			String modelU3dId = designPlan.getModelU3dId() == null ? "-1" : designPlan.getModelU3dId().toString();
			Integer spaceModelU3dId = StringUtils.isEmpty(modelU3dId) ? -1 : new Integer(modelU3dId);
			if (spaceModelU3dId > 0) {
				ResModel resModel = resModelService.get(spaceModelU3dId);
				if (resModel != null && resModel.getModelPath() != null) {
					// unitySpaceCommon.setSpaceConfigPath(resModel.getModelPath());
					unitySpaceCommon.setSpaceModelPath(resModel.getModelPath());
				}
			} else {
				// unitySpaceCommon.setSpaceConfigPath("");
				unitySpaceCommon.setSpaceModelPath("");
			}
			// unitySpaceCommon.setSpaceCode(spaceCommon.getSpaceCode());//空间编码(场景编码)
			// //根据媒介类型赋值模型
			// //U3D空间公共资源模型路径
			// String modelU3dId =
			// this.getU3dModelId(mediaType==null?"2":mediaType.toString(),spaceCommon);
			// Integer spaceModelU3dId = StringUtils.isEmpty(modelU3dId)?-1:new
			// Integer(modelU3dId);
			// if(spaceModelU3dId > 0){
			// ResModel resModel = resModelService.get(spaceModelU3dId);
			// if(resModel!=null && resModel.getModelPath()!= null){
			// unitySpaceCommon.setSpaceConfigPath(resModel.getModelPath());
			// unitySpaceCommon.setSpaceModelPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setSpaceConfigPath("");
			// unitySpaceCommon.setSpaceModelPath("");
			// }

			// //U3D空间模型路径
			// Integer spaceModelU3dId =
			// StringUtils.isEmpty(spaceCommon.getModelU3dId()) ? -1 : new
			// Integer(spaceCommon.getModelU3dId());
			// if (spaceModelU3dId > 0 && mediaType == 2 ) {
			// ResModel resModel = resModelService.get(spaceModelU3dId);
			// if (resModel != null && resModel.getModelPath() != null) {
			// unitySpaceCommon.setSpaceConfigPath(resModel.getModelPath());
			// }
			// } else {
			// unitySpaceCommon.setSpaceConfigPath("");
			// }
			// //各媒介模型拷贝
			// Integer spaceIpadU3dModelId =
			// StringUtils.isBlank(spaceCommon.getIpadU3dModelId()) ? -1 :
			// Integer.valueOf(spaceCommon.getIpadU3dModelId());
			// if( spaceIpadU3dModelId > 0 && mediaType == 7 ){
			// ResModel resModel = resModelService.get(spaceIpadU3dModelId);
			// if( resModel != null && resModel.getModelPath() != null ){
			// unitySpaceCommon.setIpadU3dModelPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setIpadU3dModelPath("");
			// }
			// Integer spaceIosU3dModelId =
			// StringUtils.isBlank(spaceCommon.getIosU3dModelId()) ? -1 :
			// Integer.valueOf(spaceCommon.getIosU3dModelId());
			// if( spaceIosU3dModelId > 0 && mediaType == 5 ){
			// ResModel resModel = resModelService.get(spaceIosU3dModelId);
			// if( resModel != null && resModel.getModelPath() != null ){
			// unitySpaceCommon.setIosU3dModelPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setIosU3dModelPath("");
			// }Integer spaceAndroidU3dModelId =
			// StringUtils.isBlank(spaceCommon.getAndroidU3dModelId()) ? -1 :
			// Integer.valueOf(spaceCommon.getAndroidU3dModelId());
			// if( spaceAndroidU3dModelId > 0 && mediaType == 6 ){
			// ResModel resModel = resModelService.get(spaceAndroidU3dModelId);
			// if( resModel != null && resModel.getModelPath() != null ){
			// unitySpaceCommon.setAndroidU3dModelPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setAndroidU3dModelPath("");
			// }Integer spaceMacBookPcU3dModelId =
			// StringUtils.isBlank(spaceCommon.getMacBookPcU3dModelId()) ? -1 :
			// Integer.valueOf(spaceCommon.getMacBookPcU3dModelId());
			// if( spaceMacBookPcU3dModelId > 0 && mediaType == 4 ){
			// ResModel resModel =
			// resModelService.get(spaceMacBookPcU3dModelId);
			// if( resModel != null && resModel.getModelPath() != null ){
			// unitySpaceCommon.setMacBookPcU3dModelPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setMacBookPcU3dModelPath("");
			// }Integer spaceWindowsPcU3dModelId =
			// StringUtils.isBlank(spaceCommon.getWindowsPcU3dModelId()) ? -1 :
			// Integer.valueOf(spaceCommon.getWindowsPcU3dModelId());
			// if( spaceIpadU3dModelId > 0 && mediaType == 3 ){
			// ResModel resModel =
			// resModelService.get(spaceWindowsPcU3dModelId);
			// if( resModel != null && resModel.getModelPath() != null ){
			// unitySpaceCommon.setWindowsPcU3dModelPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setWindowsPcU3dModelPath("");
			// }

			Integer houseTypeValue = spaceCommon.getSpaceFunctionId();
			if (houseTypeValue != null) {
				SysDictionary sd = sysDictionaryService.getSysDictionaryByValue("houseType", houseTypeValue);
				unitySpaceCommon.setHouseTypeValue(sd.getValue());
				unitySpaceCommon.setHouseTypeName(sd.getName());
			} else {
				unitySpaceCommon.setHouseTypeValue(-1);
				unitySpaceCommon.setHouseTypeName("");
			}
			// Integer modelU3dId = designPlan.getModelU3dId();
			// //spaceCommon.getModelU3dId();
			// if(modelU3dId != null){
			// ResModel resModel = resModelService.get(modelU3dId);
			// if(resModel!=null && resModel.getModelPath()!= null){
			// unitySpaceCommon.setSpaceConfigPath(resModel.getModelPath());
			// }
			// }else{
			// unitySpaceCommon.setSpaceConfigPath("");
			// }
		}
		unityDesignPlan.setUnitySpaceCommon(unitySpaceCommon);

		// 三.获取方案中用到的产品列表
		// 产品信息,返回装修顺序及相机参数值
		// 产品级别数据存储
		List<UnityPlanProduct> unityPlanProductList = new ArrayList<UnityPlanProduct>();
		// 最终数据U3DUI界面转换存储
		List<UnityPlanProduct> newUnityPlanProductList = new ArrayList<UnityPlanProduct>();
		// 获取方案产品列表
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setOrder(" product_sequence ");
		designPlanProduct.setOrderNum(" asc ");
		designPlanProduct.setPlanId(designPlan.getId());
		List<DesignPlanProduct> designProductList = designPlanProductService.getList(designPlanProduct);

		if (designProductList != null && designProductList.size() > 0) {
			TreeSet<String> ProductTypeCodeSet = new TreeSet<String>();
			// Map<String, UnityPlanProduct> unityPlanProductMap_p = new
			// HashMap<String, UnityPlanProduct>();
			for (DesignPlanProduct planProduct : designProductList) {

				UnityPlanProduct unityPlanProduct = new UnityPlanProduct();
				unityPlanProduct.setPlanProductId(planProduct.getId());
				unityPlanProduct.setProductSequence(
						StringUtils.isNotBlank(planProduct.getProductSequence()) ? planProduct.getProductSequence()
								: "0");
				unityPlanProduct.setMaterialPicPaths(new String[] {});
				unityPlanProduct.setDecorationModelPath(new String[] {});
				/** 临时字段 **/
				unityPlanProduct.setDisplayStatus(planProduct.getDisplayStatus());
				// unityPlanProduct.setProductSequence("");
				// 每个产品的相机位置
				// if (planProduct.getLocationFileId() != null) {
				// ResFile resFile =
				// resFileService.get(planProduct.getLocationFileId());
				// if (resFile != null) {
				// unityPlanProduct.setProductCameraConfig(resFile.getFilePath());
				// }
				// } else {
				// unityPlanProduct.setProductCameraConfig("");
				// }
				// unityPlanProduct.setProductCameraConfig("");
				// 每个产品的材质信息
				// if (planProduct.getMaterialPicId() != null) {
				// ResPic resPic =
				// resPicService.get(Utils.getIntValue(planProduct.getMaterialPicId()));
				// if (resPic != null) {
				// unityPlanProduct.setMaterialPicPath(resPic.getPicPath());
				// unityPlanProduct.setMaterialPicId(new
				// Integer(planProduct.getMaterialPicId()));
				// } else {
				// unityPlanProduct.setMaterialPicPath("");
				// unityPlanProduct.setMaterialPicId(-1);
				// }
				// } else {
				// unityPlanProduct.setMaterialPicPath("");
				//
				// unityPlanProduct.setMaterialPicId(-1);
				// }
				// 产品的基本信息
				BaseProduct baseProduct = baseProductService.get(planProduct.getProductId());
				unityPlanProduct.setProductId(baseProduct.getId());
				unityPlanProduct.setProductCode(baseProduct.getProductCode());
				unityPlanProduct.setParentProductId(baseProduct.getParentId());
				if (baseProduct.getProductSmallTypeValue() != null && baseProduct.getProductTypeValue() != null) {
					SysDictionary sysdictionary = new SysDictionary();
					sysdictionary.setType("productType");
					sysdictionary.setValue(Integer.parseInt(baseProduct.getProductTypeValue()));
					SysDictionary sd = sysDictionaryService.getNameByType(sysdictionary);

					SysDictionary sysDictionary = new SysDictionary();
					sysDictionary.setType(sd.getValuekey());
					sysDictionary.setValue(baseProduct.getProductSmallTypeValue());
					SysDictionary sDictionary = sysDictionaryService.getNameByType(sysDictionary);
					if (sDictionary != null) {
						unityPlanProduct.setMoveWay(sDictionary.getAtt5());
					}
				}
				// unityPlanProduct.setProLength(baseProduct.getProductLength());
				// unityPlanProduct.setProWidth(baseProduct.getProductWidth());
				// unityPlanProduct.setProHeight(baseProduct.getProductHeight());

				// 产品模型赋值
				String u3dModelId = baseProductService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(),
						baseProduct);
				ResModel resModel = resModelService.get(StringUtils.isEmpty(u3dModelId) ? 0 : new Integer(u3dModelId));
				if (resModel != null) {
					unityPlanProduct.setProductModelPath(resModel.getModelPath());
				} else {
					unityPlanProduct.setProductModelPath("");
				}
				// 产品材质赋值

				if (StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
					String materialPicId = baseProduct.getMaterialPicIds();
					String[] materialPicIds = materialPicId.split(",");

					/*
					 * String materialPicPath = ""; for( int i=0;i<materialPicIds.length;i++ ){ if(
					 * StringUtils.isNotBlank(materialPicIds[i]) ){ ResPic resPic =
					 * resPicService.get(Integer.valueOf(materialPicIds[i])); if( resPic != null ) {
					 * materialPicPath += resPic.getPicPath(); if ((i + 1) < materialPicIds.length)
					 * { materialPicPath += ","; } } } }
					 * unityPlanProduct.setMaterialPicPath(materialPicPath);
					 */
					if (materialPicIds != null && materialPicIds.length > 0) {
						List<String> materialPicList = new ArrayList<String>();
						for (int i = 0; i < materialPicIds.length; i++) {
							if (StringUtils.isNotBlank(materialPicIds[i])) {
								ResPic resPic = resPicService.get(Integer.valueOf(materialPicIds[i]));
								if (resPic != null && StringUtils.isNotBlank(resPic.getPicPath())) {
									materialPicList.add(resPic.getPicPath());
								}
							}
						}
						unityPlanProduct.setMaterialPicPaths(
								(String[]) materialPicList.toArray(new String[materialPicList.size()]));
					}
				}
				// //产品模型信息
				// if ( StringUtils.isNotBlank(baseProduct.getU3dModelId()) &&
				// mediaType == 2 ) {
				// ResModel resModel =
				// resModelService.get(Utils.getIntValue(baseProduct.getU3dModelId()));
				// if (resModel != null) {
				// unityPlanProduct.setProductModelPath(resModel.getModelPath());
				// }
				// }
				//
				// //其他媒介模型信息
				// if( baseProduct.getIpadU3dModelId() != null && mediaType == 7
				// ){
				// ResModel resModel =
				// resModelService.get(baseProduct.getIpadU3dModelId());
				// if( resModel != null ){
				// unityPlanProduct.setIpadU3dModelPath(resModel.getModelPath());
				// }
				// }else{
				// unityPlanProduct.setIpadU3dModelPath("");
				// }
				// if( baseProduct.getIosU3dModelId() != null && mediaType == 5
				// ){
				// ResModel resModel =
				// resModelService.get(baseProduct.getIosU3dModelId());
				// if( resModel != null ){
				// unityPlanProduct.setIosU3dModelPath(resModel.getModelPath());
				// }
				// }else{
				// unityPlanProduct.setIosU3dModelPath("");
				// }
				// if( baseProduct.getAndroidU3dModelId() != null && mediaType
				// == 6 ){
				// ResModel resModel =
				// resModelService.get(baseProduct.getAndroidU3dModelId());
				// if( resModel != null ){
				// unityPlanProduct.setAndroidU3dModelPath(resModel.getModelPath());
				// }
				// }else{
				// unityPlanProduct.setAndroidU3dModelPath("");
				// }
				// if( baseProduct.getMacBookU3dModelId() != null && mediaType
				// == 4 ){
				// ResModel resModel =
				// resModelService.get(baseProduct.getMacBookU3dModelId());
				// if( resModel != null ){
				// unityPlanProduct.setMacBookU3dModelPath(resModel.getModelPath());
				// }
				// }else{
				// unityPlanProduct.setMacBookU3dModelPath("");
				// }
				// if( baseProduct.getWindowsU3dModelId() != null && mediaType
				// == 3 ){
				// ResModel resModel =
				// resModelService.get(baseProduct.getWindowsU3dModelId());
				// if( resModel != null ){
				// unityPlanProduct.setWindowsU3dModelPath(resModel.getModelPath());
				// }
				// }else{
				// unityPlanProduct.setWindowsU3dModelPath("");
				// }

				// 产品子集数量
				unityPlanProduct.setLeafNum(0);
				// 标示产品在界面中的展示类型
				unityPlanProduct.setIsLeaf(new Integer(1));
				// 产品是否隐藏
				unityPlanProduct.setIsHide(planProduct.getIsHide());
				// 产品挂节点路径
				unityPlanProduct.setPosIndexPath(planProduct.getPosIndexPath());
				unityPlanProduct.setPosName(planProduct.getPosName());

				// 获取每个产品的饰品列表
				// FIXME productDecoration无用，已删
				/*
				 * String decorationId = baseProduct.getDecorationId(); if
				 * (StringUtils.isNotBlank(decorationId)) { String[] decorationIds =
				 * decorationId.split(","); if (decorationIds != null && decorationIds.length >
				 * 0) { List<String> decorationU3dModelList = new ArrayList<String>(); for (int
				 * i = 0; i < decorationIds.length; i++) { if
				 * (StringUtils.isNotBlank(decorationIds[i]) && new
				 * Integer(decorationIds[i]).intValue() > 0) { // 获取饰品信息 ProductDecoration
				 * productDecoration = productDecorationService .get(new
				 * Integer(decorationIds[i])); if (productDecoration != null) { String u3dModel
				 * = this.getU3dModelId( mediaType, productDecoration); if
				 * (StringUtils.isNotBlank(u3dModel)) { ResModel pdu3dModel = resModelService
				 * .get(Integer.valueOf(u3dModel)); if (pdu3dModel != null && StringUtils
				 * .isNotBlank(pdu3dModel .getModelPath())) { decorationU3dModelList
				 * .add(pdu3dModel .getModelPath()); } } }
				 * 
				 * } } unityPlanProduct .setDecorationModelPath((String[])
				 * decorationU3dModelList .toArray(new String[decorationU3dModelList .size()]));
				 * } }
				 */
				// 类别获取
				String productTypeValue = baseProduct.getProductTypeValue();
				// UnityPlanProduct unityPlanProduct_p =
				// unityPlanProduct.copy();
				// 产品类别信息
				if (!StringUtils.isEmpty(productTypeValue)) {
					SysDictionary sd = sysDictionaryService.getSysDictionaryByValue("productType",
							new Integer(productTypeValue));
					if (sd != null) {
						// 为保证父节点与子节点的productTypeCode相同，指定如下规则：
						// 子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
						// 父节点时，parentTypeCode存在值(暂时不取)，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值
						unityPlanProduct.setProductTypeValue(sd.getValue());
						unityPlanProduct.setProductTypeCode(sd.getValuekey());
						unityPlanProduct.setProductTypeName(sd.getName());

						// unityPlanProduct_p.setProductTypeValue(sd.getValue());
						// unityPlanProduct_p.setProductTypeCode(sd.getValuekey());
						// unityPlanProduct_p.setProductTypeName(sd.getName());
						// 获取子节点的父节点信息
						unityPlanProduct.setParentTypeCode(sd.getValuekey());
						unityPlanProduct.setParentTypeName(sd.getName());
						unityPlanProduct.setParentTypeValue(sd.getValue());

						// unityPlanProduct_p.setParentTypeValue(-1);
						// unityPlanProduct_p.setParentTypeCode("");
						// unityPlanProduct_p.setParentTypeName("");

						// 获取子节点的节点信息
						Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
						if (productSmallTypeValue != null && new Integer(productSmallTypeValue).intValue() > 0) {
							SysDictionary sdson = sysDictionaryService.getSysDictionaryByValue(sd.getValuekey(),
									productSmallTypeValue);
							if (sdson != null) {
								unityPlanProduct.setSmallTypeValue(sdson.getValue());
								unityPlanProduct.setSmallTypeCode(sdson.getValuekey());
								unityPlanProduct.setSmallTypeName(sdson.getName());
								// 是否是白模
								Integer isBm = 0;
								if ("baimo".equals(sdson.getAtt3())) {
									isBm = 1;
								}
								unityPlanProduct.setIsBaimo(isBm);
								// 软装硬装以下规则处理，同时按最小基本的数据定义-按1硬装2软装,默认软装
								String rootType = StringUtils.isEmpty(sdson.getAtt1()) ? "2" : sdson.getAtt1().trim();
								unityPlanProduct.setRootType(rootType);
								// unityPlanProduct_p.setRootType("");
								//
								// unityPlanProduct_p.setSmallTypeValue(-1);
								// unityPlanProduct_p.setSmallTypeCode("");
								// unityPlanProduct_p.setSmallTypeName("");
							}
						}
					}
				}

				// 存储产品分类集合,便于组装UI界面
				// if
				// (!StringUtils.isEmpty(unityPlanProduct.getProductTypeCode()))
				// {
				// ProductTypeCodeSet.add(unityPlanProduct.getProductTypeCode());
				// 默认使用第一条记录信息做数据代入
				// if
				// (!unityPlanProductMap_p.containsKey(unityPlanProduct.getProductTypeCode()))
				// {
				// unityPlanProductMap_p.put(unityPlanProduct.getProductTypeCode(),
				// unityPlanProduct_p);
				// }
				// }
				// 取样板房中的产品id
				// 样板房产品ID
				unityPlanProduct.setTemplateProductId(
						planProduct.getInitProductId() == null ? "" : planProduct.getInitProductId().toString());
				unityPlanProductList.add(unityPlanProduct);
			}

			// 组装每个产品的分类信息
			// Map<String, List<UnityPlanProduct>> productMap = new
			// HashMap<String, List<UnityPlanProduct>>();
			// if (!ProductTypeCodeSet.isEmpty()) {
			// for (String pproductTypeCode : ProductTypeCodeSet) {
			// List<UnityPlanProduct> productList = new
			// ArrayList<UnityPlanProduct>();
			// for (UnityPlanProduct sunityPlanProduct : unityPlanProductList) {
			// if
			// (pproductTypeCode.equals(sunityPlanProduct.getProductTypeCode()))
			// {
			// productList.add(sunityPlanProduct);
			// }
			// }
			// productMap.put(pproductTypeCode, productList);
			// }
			// }
			//
			// // 四.定制装修导航(在产品列表中该增加大按钮性质,同时,只有一个时,删除小按钮)
			// if (!ProductTypeCodeSet.isEmpty()) {
			// for (String pproductTypeCode : ProductTypeCodeSet) {
			// // 获取父节点信息
			// UnityPlanProduct munityPlanProduct =
			// unityPlanProductMap_p.get(pproductTypeCode);
			// UnityPlanProduct punityPlanProduct = munityPlanProduct.copy();
			// punityPlanProduct.setIsLeaf(0);
			// List<UnityPlanProduct> list = productMap.get(pproductTypeCode);
			// if (list != null && list.size() > 0) {
			// punityPlanProduct.setLeafNum(list.size());
			// }
			// newUnityPlanProductList.add(punityPlanProduct);
			// // 追加下属所有子节点信息(追加所有的小按钮到大按钮中)
			// for (UnityPlanProduct sunityPlanProduct : unityPlanProductList) {
			// if
			// (pproductTypeCode.equals(sunityPlanProduct.getProductTypeCode()))
			// {
			// // if(list.size()>1){
			// newUnityPlanProductList.add(sunityPlanProduct);
			// // }
			// }
			// }
			//
			// }
			// }
		}

		ComparatorT cpmparator = new ComparatorT();
		Collections.sort(newUnityPlanProductList, cpmparator);
		unityDesignPlan.setDatalist(newUnityPlanProductList);

		return new ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);

	}

	// 根据产品顺序排序（升序）
	public class ComparatorT implements Comparator {
		public int compare(Object obj1, Object obj2) {
			UnityPlanProduct unity1 = (UnityPlanProduct) obj1;
			UnityPlanProduct unity2 = (UnityPlanProduct) obj2;
			int flag = (unity1.getProductSequence() == null ? new Integer(0) : new Integer(unity1.getProductSequence()))
					.compareTo(unity2.getProductSequence() == null ? new Integer(0)
							: new Integer(unity2.getProductSequence()));
			if (flag == 0) {
				return (unity1.getProductSequence() == null ? new Integer(0) : new Integer(unity1.getProductSequence()))
						.compareTo(unity2.getProductSequence() == null ? new Integer(0)
								: new Integer(unity2.getProductSequence()));
			} else {
				return flag;
			}
		}
	}

	/* 是否需要创建新的方案 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isNewPlan(HttpServletRequest request, Map map) {
		boolean isNewPlan = false;
		if (map == null) {
			map = new HashMap();
		}
		Integer templetId = (Integer) map.get("templetId");
		Map paraMap = new HashMap();
		paraMap.put("designId", templetId);
		int times = Integer.parseInt(app.getString("design.designPlan.interval.times").trim());
		paraMap.put("times", times);
		LoginUser loginUser = new LoginUser();

		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}

		paraMap.put("userId", loginUser.getId());
		List list = designPlanService.getUserMaxPlan(paraMap);
		if (list != null && list.size() > 0) {
			map.put("list", list);
			isNewPlan = false;
		} else {
			map = new HashMap();
			isNewPlan = true;
		}
		return isNewPlan;
	}

	/**
	 * 更新设计方案
	 * @param designPlanId
	 * @param context
	 * @param planProductJson [{方案产品ID ：产品ID}]
	 * @param materialPicId
	 * @param msgId
	 * @param splitTexturesChoose
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param newFlag
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unityUpdate")
	@ResponseBody
	public Object unityUpdate(Integer designPlanId, String context, String planProductJson,
			String materialPicId, String msgId, String splitTexturesChoose, /* 保存可拆分材质产品选择的材质 */
			@RequestParam(value = "houseId", required = false) String houseId,
			@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "residentialUnitsName", required = false) String residentialUnitsName,
			@RequestParam(value = "newFlag", required = false) Integer newFlag, HttpServletRequest request)
			throws Exception {
		Long startTime = System.currentTimeMillis();
		if (designPlanId == null || StringUtils.isEmpty(context) || StringUtils.isEmpty(planProductJson)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数存在空值", msgId);
		}

		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);

		DesignPlan designPlan = null;
		if (Utils.enableRedisCache()) {
			designPlan = DesignCacher.get(designPlanId);
		} else {
			designPlan = designPlanService.get(designPlanId);
		}
		if (designPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false, "找不到Id=" + designPlanId + "的设计方案！", msgId);
		}
		if (loginUser != null && loginUser.getId() > 0) {
			designPlan.setUserIdTemp(loginUser.getId());
		}

		return designPlanService.unityUpdate(designPlan,designPlanId,planProductJson,materialPicId,context,splitTexturesChoose,request,msgId);
	}

	/**
	 * 设计方案渲染图上传
	 * 
	 * @param request
	 * @return object
	 */
	@RequestMapping("/uploadPlanRender_old")
	@ResponseBody
	public Object uploadPlanRender(String level, String planId, String msgId, Integer viewPoint, Integer scene,
			Integer isTurnOn, String renderingType, HttpServletRequest request, HttpServletResponse response) {
		logger.info("接收渲染图接口....");
		if (StringUtils.isBlank(planId)) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数planId不能为空", msgId);
		}
		if ("4".equals(renderingType)) {// 4表示720度全景图
			if (StringUtils.isBlank(level)) {
				return new ResponseEnvelope<ResFileSmall>(false, "参数level不能为空", msgId);
			}
			if (viewPoint == null) {
				return new ResponseEnvelope<ResFileSmall>(false, "参数viewPoint不能为空", msgId);
			}

			if (scene == null) {
				return new ResponseEnvelope<ResFileSmall>(false, "参数scene不能为空", msgId);
			}
		}
		DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
		String code = "code";
		if (designPlan != null) {
			code = designPlan.getPlanCode();
		}
		// 多文件上传使用request
		if (request instanceof MultipartHttpServletRequest) {
			// 获取文件列表并将物理文件保存到服务器中
			// 将HttpServletRequest对象转换为MultipartHttpServletRequest对象
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String fileName = null;

			List<Map> list = new ArrayList<Map>();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			String fileIds = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				fileName = mf.getOriginalFilename();
				logger.info("文件上传名称：" + fileName);
				response.setContentType("text/html;charset=utf-8");
				// 获取文件列表并将物理文件保存到服务器中

				// filePath设置到model对象中，由model存入数据库中
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY, "design.designPlan.render.upload.path");
				boolean flag = false;
				map.put("code", code);
				map.put(FileUploadUtils.FILE, mf);
				// // //System.out.println("map_--------------size:"+map.size()+"
				// // fileName++++:"+fileName);
				flag = FileUploadUtils.saveFile(map);
				// // //System.out.println("flag+++"+flag);
				String serverFilePath = map.get(FileUploadUtils.SERVER_FILE_PATH).toString();
				// // //System.out.println("serverFilePath+++"+serverFilePath);
				logger.info("追加渲染图水印,serverFilePath=" + serverFilePath);
				// ImageUtils.pressText(
				// Utils.getCurrentDateTime(Utils.DATE_TIME),
				// serverFilePath, ImageUtils.RIGHT_DOWN);
				// ImageUtils.pressText("快速", serverFilePath,
				// ImageUtils.RIGHT_UP);
				try {
					/* 水印图方案一 */
					// StringBuffer str=new StringBuffer();
					// if(new Integer(1).equals(scene)){
					// str.append("白天");
					// }else if(new Integer(2).equals(scene)){
					// str.append("黑夜");
					// }else{
					// str.append("黄昏");
					// }
					// str.append("快速");
					// ImageUtils.watermarking(serverFilePath, str.toString(),
					// new Integer(1).equals(isTurnOn)?1:0);
					/* 水印图方案一->end */
					ImageUtils.watermarking2(serverFilePath, scene, 2, isTurnOn);
				} catch (IOException e1) {
					logger.error(e1.getMessage(),e1);
					e1.printStackTrace();
				}
				// 生成图片,支持多张,需写到server
				if (flag) {
					/**
					 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值， 则上传到web服务器。
					 **/
					Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
					String finalFlieName = (String) map.get("finalFlieName");
					String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
					String ftpFilePath = (String) map.get("filePath");
					// 上传方式为2或者3都需要上传到ftp
					boolean uploadFtpFlag = false;
					if (ftpUploadMethod == 2 || ftpUploadMethod == 3) {
						uploadFtpFlag = FtpUploadUtils.uploadFile(finalFlieName, serverFilePath, ftpFilePath);
					}
					// map.put(FileModel.FILE_SIZE, mf.getSize());
					// 生成缩略图
					try {
						String smallFileName = Utils.generateRandomDigitString(6) + "_"
								+ Utils.getCurrentDateTime(Utils.DATETIMESSS)
								+ fileName.substring(fileName.indexOf("."));
						/*String targetSmallFilePath = Utils.getValue(
								"app.upload.root", "D:\\app").trim()
								+ app.getString((String) map.get(FileUploadUtils.UPLOADPATHTKEY)).replace("[code]", code) + "/small/" + smallFileName;*/
						String relativePath = app.getString((String) map.get(FileUploadUtils.UPLOADPATHTKEY)).replace("[code]", code) + "/small/" + smallFileName;
						String targetSmallFilePath = Utils.getAbsolutePath(relativePath, Utils.getAbsolutePathType.encrypt);
						targetSmallFilePath = Utils.replaceDate(targetSmallFilePath);
						if ("windows".equals(Utils.getValue(
								"app.system.format", "linux").trim())) {
							targetSmallFilePath = targetSmallFilePath.replace(
									"/", "\\");
						}
						ResizeImage.createThumbnail(serverFilePath, targetSmallFilePath, 280, 215);
						File targetSmallFile = new File(targetSmallFilePath);
						Map smallFileMap = FileUploadUtils.getMap(targetSmallFile,
								"design.designPlan.render.upload.path", false);
						map.put("viewPoint", viewPoint);
						map.put("scene", scene);

						smallFileMap.put("original", map);
						/* 传递渲染图的视角和渲染图场景 */
						smallFileMap.put("viewPoint", viewPoint);
						smallFileMap.put("scene", scene);
						list.add(smallFileMap);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 如果上传方式为2，则删除掉临时上传的文件
					if (ftpUploadMethod == 2) {
						if (uploadFtpFlag) {
							// 删除本地
							FileUploadUtils.deleteFile(dbFilePath);
						}
					}
				}
			}
			// 将图片信息记录到数据库中
			// resPicService.saveResDesign(planId, list, level,renderingType);
			resPicService.saveFiles(planId, list, level, renderingType);
		}

		return new ResponseEnvelope<ResFileSmall>(true, "上传成功", msgId);
	}

	/**
	 * 保存客户端渲染的设计方案图(720类型专用)
	 * 
	 * @param planId
	 *            渲染的方案ID
	 * @param templateId
	 *            方案应用到样板房ID
	 * @param sourcePlanId
	 *            源方案ID
	 * @param request
	 * @return object
	 */
	@RequestMapping("/saveDesignRenderPic")
	@ResponseBody
	public Object saveDesignRenderPic(String level, String planId, String msgId, Integer viewPoint, Integer scene,
			Integer isTurnOn, String renderingType, Integer taskId, Integer isChange, HttpServletRequest request,
			HttpServletResponse response, String authorization, Integer panoLevel, String roamJson, Integer opType,
			Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		String msg = "";
		logger.info("接收渲染图接口....");
		if (StringUtils.isBlank(planId)) {
			msg = "参数planId不能为空";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		// 验证登录参数 ->start
		Map<String, String> authorizationMap = Utils.getMapByJson(authorization);
		LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorizationMap.get("token"));
//		LoginUser loginUser = sysUserService.isAllowedThrough(authorizationMap.get("appKey"),
//				authorizationMap.get("token"), authorizationMap.get("deviceId"), authorizationMap.get("mediaType"));
		if (loginUser == null) {
			msg = "请登录系统.";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}

		/*
		 * LoginUser loginUser = (LoginUser)
		 * com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request); if (loginUser == null) { msg
		 * = "请先登录！"; return new ResponseEnvelope<ResFileSmall>(false, msg, msgId); }
		 */
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		if (taskId == null) {
			msg = "参数taskId不能为空";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		if (StringUtils.isBlank(renderingType)) {
			msg = "参数renderingType不能为空";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		if (RenderTypeCode.COMMON_720_LEVEL == Utils.getIntValue(renderingType)
				|| RenderTypeCode.ROAM_720_LEVEL == Utils.getIntValue(renderingType)) {// 4表示720度全景图
			if (StringUtils.isBlank(level)) {
				msg = "参数level不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}
			if (viewPoint == null) {
				msg = "参数viewPoint不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}

			if (scene == null) {
				msg = "参数scene不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}
			if (panoLevel == null) {
				msg = "参数panoLevel不能为空";
				return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
			}
		} else {
			msg = "参数  renderingType 未知图片类型";
			return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		}
		DesignPlan designPlan = null;
		if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
			designPlan = designPlanService.get(Integer.parseInt(planId));
		} else {
			designPlan = optimizePlanService.getPlan(Integer.parseInt(planId));
		}
		/* 获得文件流 */
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
		boolean flag = true;
		String jobType = RenderJobType.RENDER_JOB_720;
		if (RenderTypeCode.ROAM_720_LEVEL == Utils.getIntValue(renderingType)) {
			jobType = RenderJobType.RENDER_JOB_N720;
		}

		RenderPicVO vo = this.createRenderPicVO720(designPlan, fileMap, viewPoint, scene, isTurnOn,
				Utils.getIntValue(renderingType), level, loginUser, taskId, panoLevel, roamJson, opType, templateId,
				sourcePlanId,userRenderType,isAuto);
		vo.setIsChange(isChange);
		ResponseEnvelope syncSaveResult = this.syncSaveRenderPic(saveRenderPicService, vo, jobType);// 异步任务

		// TODO : Check the upload state;

		/*
		 * boolean flag =
		 * designPlanService.saveRenderPic(designPlan,fileMap,viewPoint,scene,
		 * isTurnOn,Utils.getIntValue(renderingType),loginUser);
		 */
		/*
		 * if( !flag){ return new ResponseEnvelope<ResFileSmall>(false, "渲染失败", msgId);
		 * }
		 */

		// return new ResponseEnvelope<ResFileSmall>(syncSaveResult.isSuccess(),
		// syncSaveResult.getMessage(), msgId);
		return new ResponseEnvelope<ResFileSmall>(true, "图片保存中", msgId);
	}

	private String testMethod() {
		String r = "";
		logger.info("test");
		return r;

	}

	@SuppressWarnings("rawtypes")
	private ResponseEnvelope syncSaveRenderPic(SaveRenderPicService service, RenderPicVO picModel, String jobType) {
		logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "进入syncSaveRenderPic方法");
		ResponseEnvelope result = new ResponseEnvelope();
		ThreadPool threadPool = uploadFileThreadPoolManager.getThreadPool();// 取得线程池实列
		logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "创建job任务start");
		// 创建同步保存渲染图的任务
		SaveRenderPicJob job = new SaveRenderPicJob(service, picModel, jobType);
		logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "创建job任务end");
		logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "提交线程任务start");
		Future<Boolean> future = threadPool.submit(job);
		logger.info("设计方案id=" + picModel.getDesignPlan().getId() + "提交线程任务end");
		return result;
	}

	private RenderPicVO createRenderPicVO(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType, String level, LoginUser loginUser, Integer taskId,
			Integer opType, Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		logger.info("updateTheStateForAutoRender planId=" + designPlan.getId() + ",templateId=" + templateId
				+ ",sourcePlanId=" + sourcePlanId);
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		RenderPicVO vo = new RenderPicVO();
		vo.setDesignPlan(designPlan);
		vo.setFileMap(fileMap);
		vo.setViewPoint(viewPoint);
		vo.setScene(scene);
		vo.setIsTurnOn(isTurnOn);
		vo.setRenderingType(renderingType);
		vo.setLoginUser(loginUser);
		vo.setLevel(level);
		vo.setTaskId(taskId);
		vo.setOpType(opType);
		vo.setUserRenderType(userRenderType);
		sourcePlanId = sourcePlanId != null ? sourcePlanId : 0;
		vo.setSourcePlanId(sourcePlanId);
		if (templateId != null) {
			vo.setTemplateId(templateId);
		}
		if(isAuto == null) {
			isAuto = 0;
		}
		vo.setIsAuto(isAuto);
		return vo;
	}

	// TODO:封装720渲染全景图对象
	private RenderPicVO createRenderPicVO720(DesignPlan designPlan, Map<String, MultipartFile> fileMap,
			Integer viewPoint, Integer scene, Integer isTurnOn, Integer renderingType, String level,
			LoginUser loginUser, Integer taskId, Integer panoLevel, String roamJson, Integer opType, Integer templateId,
			Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		RenderPicVO vo = new RenderPicVO();
		vo.setDesignPlan(designPlan);
		vo.setFileMap(fileMap);
		vo.setViewPoint(viewPoint);
		vo.setScene(scene);
		vo.setIsTurnOn(isTurnOn);
		vo.setRenderingType(renderingType);
		vo.setLoginUser(loginUser);
		vo.setLevel(level);
		vo.setTaskId(taskId);
		vo.setPanoLevel(panoLevel);
		vo.setRoamJson(roamJson);
		vo.setOpType(opType);
		vo.setUserRenderType(userRenderType);
		sourcePlanId = sourcePlanId != null ? sourcePlanId : 0;
		vo.setSourcePlanId(sourcePlanId);
		if (templateId != null) {
			vo.setTemplateId(templateId);
		}
		if(isAuto == null) {
			isAuto = 0;
		}
		vo.setIsAuto(isAuto);
		return vo;
	}

	/*
	 * 根据不同的媒介获取不同的模型
	 */
	public String getU3dModelId(String mediaType, DesignTemplet designTemplet) {
		if (designTemplet == null || mediaType == null) {
			return "";
		}
		if ("3".equals(mediaType)) {
			return designTemplet.getPcModelU3dId() == null ? "" : designTemplet.getPcModelU3dId().toString();
		} else if ("4".equals(mediaType)) {
			return designTemplet.getMacBookpcModelU3dId() == null ? ""
					: designTemplet.getMacBookpcModelU3dId().toString();
		} else if ("5".equals(mediaType)) {
			return designTemplet.getIosModelU3dId() == null ? "" : designTemplet.getIosModelU3dId().toString();
		} else if ("6".equals(mediaType)) {
			return designTemplet.getAndroidModelU3dId() == null ? "" : designTemplet.getAndroidModelU3dId().toString();
		} else if ("7".equals(mediaType)) {
			return designTemplet.getIpadModelU3dId() == null ? "" : designTemplet.getIpadModelU3dId().toString();
		} else {
			return designTemplet.getWebModelU3dId() == null ? "" : designTemplet.getWebModelU3dId().toString();
		}
	}

	/*
	 * FIXME productDecoration无用，已删 根据不同的媒介获取不同的模型 public String
	 * getU3dModelId(String mediaType, ProductDecoration productDecoration) { if
	 * (productDecoration == null || mediaType == null) { return ""; } if
	 * ("3".equals(mediaType)) { return productDecoration.getWindowsU3dmodelId() ==
	 * null ? "" : productDecoration.getWindowsU3dmodelId().toString(); } else if
	 * ("4".equals(mediaType)) { return productDecoration.getMacbookU3dmodelId() ==
	 * null ? "" : productDecoration.getMacbookU3dmodelId().toString(); } else if
	 * ("5".equals(mediaType)) { return productDecoration.getIosU3dmodelId() == null
	 * ? "" : productDecoration.getIosU3dmodelId().toString(); } else if
	 * ("6".equals(mediaType)) { return productDecoration.getAndroidU3dmodelId() ==
	 * null ? "" : productDecoration.getAndroidU3dmodelId().toString(); } else if
	 * ("7".equals(mediaType)) { return productDecoration.getIpadU3dmodelId() ==
	 * null ? "" : productDecoration.getIpadU3dmodelId().toString(); } else { return
	 * productDecoration.getU3dModelId() == null ? "" :
	 * productDecoration.getU3dModelId().toString(); } }
	 */

	/**
	 * 修改设计方案渲染图列表
	 * 
	 * @param planId
	 * @return
	 */
	@RequestMapping(value = "/designPlanRenderPicList")
	@ResponseBody
	public Object designPlanRenderPicList(@RequestParam(value = "planId", required = false) Integer planId,
			@RequestParam(value = "msgId", required = false) String msgId) {
		if (planId == null) {
			return new ResponseEnvelope<ResPic>(false, "planId为必填参数", msgId);
		}

		// 缩略图
		List<ResRenderPic> picSmallList = new ArrayList<>();
		ResRenderPicQO resRenderPicQO = new ResRenderPicQO();

		// TODO:创建一个fileKeyList用作查询条件
		List<String> fileKeyList = new ArrayList<>();
		fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
		fileKeyList.add("design.designPlan.render.video.cover");
		resRenderPicQO.setFileKeys(fileKeyList);

		resRenderPicQO.setBusinessId(planId);
		resRenderPicQO.setIsDeleted(0);
		resRenderPicQO.setOrder(" gmt_create desc ");
		picSmallList = resRenderPicService.selectListByFileKeys(resRenderPicQO);

		if (picSmallList.size() < 1) {
			return new ResponseEnvelope<ResRenderPic>(0, picSmallList, msgId);
		}
		for (ResRenderPic tempRenderPic : picSmallList) {
			// 原图
			ResRenderPic resRenderPic = null;
			// 原视频
			ResRenderVideo resRenderVideo = null;
			if (tempRenderPic.getPid() != null) {
				resRenderPic = resRenderPicService.get(tempRenderPic.getPid());
			} else {
				if (tempRenderPic.getId() != null) {
					resRenderVideo = resRenderVideoService.selectBySysTaskPicId(tempRenderPic.getId());
				}
			}

			if (resRenderPic != null) {
				if (resRenderPic.getRenderingType() != null) {
					if (RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType()
							|| RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()) {
						ResRenderPic rp = null;
						// 获取截图
						if (resRenderPic.getSysTaskPicId() == null) {
							logger.info("设计方案planId=" + planId + "获取渲染图片列表接口：sysTaskPicId is null!");
						} else {
							rp = resRenderPicService.get(resRenderPic.getSysTaskPicId());
						}
						if (rp == null) {
							// 截图不存在则取原图
							logger.info("设计方案planId=" + planId + "获取渲染图片列表接口：截图信息为空!");
							tempRenderPic.setPicPath(resRenderPic.getPicPath());
						} else {
							if (rp.getPicPath() == null) {
								logger.info("设计方案planId=" + planId + "获取渲染图片列表接口列表接口：图片id=" + rp.getId() + "图片路径为空！");
							} else {
								// tempRenderPic.setPicPath(rp.getPicPath());
								tempRenderPic.setOriginalPicPath(rp.getPicPath());
							}
						}
						tempRenderPic.setRenderingType(resRenderPic.getRenderingType());
						tempRenderPic.setBaseRenderId(resRenderPic.getId());
					} else {
						tempRenderPic.setOriginalPicPath(resRenderPic.getPicPath());
						tempRenderPic.setRenderingType(resRenderPic.getRenderingType());
						tempRenderPic.setBaseRenderId(resRenderPic.getId());
					}
				} else {
					tempRenderPic.setOriginalPicPath(resRenderPic.getPicPath());
					tempRenderPic.setRenderingType(resRenderPic.getRenderingType());
					tempRenderPic.setBaseRenderId(resRenderPic.getId());
				}
			}
			// 判断漫游视频是否存在
			if (resRenderVideo != null) {
				if (resRenderVideo.getRenderingType() != null) {
					if (RenderTypeCode.COMMON_VIDEO == resRenderVideo.getRenderingType()
							|| RenderTypeCode.HD_VIDEO == resRenderVideo.getRenderingType()) {
						ResRenderVideo rp = null;
						// 获取截图
						if (resRenderVideo.getSysTaskPicId() == null) {
							logger.error("设计方案planId=" + planId + "获取渲染视频列表接口：sysTaskPicId is null!");
						} else {
							rp = resRenderVideoService.get(resRenderVideo.getSysTaskPicId());
						}
						if (rp == null) {
							// // 截图不存在则取原图
							// logger.error("设计方案planId=" + planId
							// + "获取渲染视频列表接口：封面图信息为空!");
							// tempRenderPic.setPicPath(resRenderVideo.getVideoPath());
						} else {
							if (rp.getVideoPath() == null) {
								logger.error("设计方案planId=" + planId + "获取渲染视频列表接口列表接口：视频id=" + rp.getId() + "视频路径为空！");
							} else {
								// tempRenderPic.setPicPath(rp.getPicPath());
								tempRenderPic.setOriginalPicPath(rp.getVideoPath());
							}
						}
						tempRenderPic.setRenderingType(resRenderVideo.getRenderingType());
						tempRenderPic.setOriginalPicPath(resRenderVideo.getVideoPath());
						tempRenderPic.setBaseRenderId(resRenderVideo.getId());
					} else {
						tempRenderPic.setOriginalPicPath(resRenderVideo.getVideoPath());
						tempRenderPic.setRenderingType(resRenderVideo.getRenderingType());
						tempRenderPic.setBaseRenderId(resRenderVideo.getId());
					}
				} else {
					tempRenderPic.setOriginalPicPath(resRenderVideo.getVideoPath());
					tempRenderPic.setRenderingType(resRenderVideo.getRenderingType());
					tempRenderPic.setBaseRenderId(resRenderVideo.getId());
				}
			}
			// }
		}

		return new ResponseEnvelope<ResRenderPic>(picSmallList.size(), picSmallList, msgId);
	}

	/**
	 * 设计方案渲染图列表
	 * 
	 * @param planId
	 * @return
	 */
	@RequestMapping(value = "/designPlanRenderPicList_bak")
	@ResponseBody
	public Object designPlanRenderPicList_BAK(@RequestParam(value = "planId", required = false) Integer planId,
			@RequestParam(value = "msgId", required = false) String msgId,
			@ModelAttribute("search") ResPicSearch search) {
		if (planId == null) {
			return new ResponseEnvelope<ResPic>(false, "planId为必填参数", msgId);
		}

		List<ResPic> list = new ArrayList<>();
		DesignPlan designPlan = null;
		if (Utils.enableRedisCache()) {
			designPlan = DesignCacher.get(planId);
		} else {
			designPlan = designPlanService.get(planId);
		}

		if (designPlan == null) {
			return new ResponseEnvelope<ResPic>(false, "没有找到planId为 " + planId + " 的设计方案", msgId);
		}
		int total = 0;

		return new ResponseEnvelope<ResPic>(total, list, msgId);
	}

	/**
	 * 删除设计方案渲染图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delRenderPicWeb_bak")
	@ResponseBody
	public Object delRenderPic_bak(Integer planId, Integer resPicId, String msgId, HttpServletResponse response) {
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "planId不能为空！", msgId);
		}
		if (resPicId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "resPicId不能为空！", msgId);
		}
		DesignPlan designPlan = null;
		if (Utils.enableRedisCache()) {
			designPlan = DesignCacher.get(planId);
		} else {
			designPlan = designPlanService.get(planId);
		}
		return new ResponseEnvelope<DesignPlan>(true, "删除渲染图成功！", msgId);
	}

	/**
	 * 删除设计方案渲染图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delRenderPicWeb")
	@ResponseBody
	public Object delRenderPic(Integer planId, Integer resPicId, String msgId, HttpServletResponse response) {
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "planId不能为空！", msgId);
		}
		if (resPicId == null) {// 缩略图id
			return new ResponseEnvelope<DesignPlan>(false, "resPicId不能为空！", msgId);
		}

		// 删除缩略图
		ResRenderPic resRenderPic2 = null;
		ResRenderPic resRenderPic = resRenderPicService.get(resPicId);
		if (resRenderPic == null) {
			logger.error("缩略图id=" + resPicId + "没有原图");
		} else {
			resRenderPic2 = resRenderPicService.get(resPicId);
		}
		ResRenderPic delPic = new ResRenderPic();
		delPic.setId(resPicId);
		delPic.setIsDeleted(1);
		resRenderPicService.update(delPic);

		// 删除缩原图
		if (resRenderPic2 != null && resRenderPic2.getPid() != null) {
			ResRenderPic delSmallPic = new ResRenderPic();
			delSmallPic.setId(resRenderPic2.getPid());
			delSmallPic.setIsDeleted(1);
			resRenderPicService.update(delSmallPic);
		}

		return new ResponseEnvelope<DesignPlan>(true, "删除渲染图成功！", msgId);
	}

	/**
	 * 删除设计方案渲染图
	 * 
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/delRenderPicWeb1")
	public void delRenderPic(Integer planId, Integer fileId, HttpServletResponse response) {
		try {

			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=UTF-8");
			if (planId == null) {
				out.print(false);
			}
			if (fileId == null) {
				out.print(false);
			}
			DesignPlan designPlan = null;
			if (Utils.enableRedisCache()) {
				designPlan = DesignPlanCacher.getDesignPlan(planId);
			} else {
				designPlan = designPlanService.get(planId);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取空间类型
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDropValue")
	@ResponseBody
	public Object getDropValue(@ModelAttribute("sysDictionary") SysDictionary sysDictionary, HttpServletRequest request)
			throws Exception {
		// SysDictionary sysDictionary = new SysDictionary();
		sysDictionary.setType("houseType");
		int total = 0;
		SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
		sysDictionarySearch.setType("houseType");

		total = sysDictionaryService.getCount(sysDictionarySearch);
		List<SysDictionary> houseTypelist = sysDictionaryService.getList(sysDictionary);
		// request.setAttribute("houseTypelist", houseTypelist);

		// SysDictionary spaceStyle = new SysDictionary();
		// spaceStyle.setType("productStyle");
		// List<SysDictionary> spaceStyleList =
		// sysDictionaryService.getList(spaceStyle);
		// request.setAttribute("spaceStyleList", spaceStyleList);

		// return "/online/newDesign/design_list";
		String sysDictionaryList = JsonUtil.getListToJsonData(houseTypelist);
		List<SysDictionary> smallList = new JsonDataServiceImpl<SysDictionary>().getJsonToBeanList(sysDictionaryList,
				SysDictionary.class);
		return new ResponseEnvelope<SysDictionary>(total, smallList);
	}

	/**
	 * 获取风格选择
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStyleValue")
	@ResponseBody
	public Object getStyleValue(@ModelAttribute("sysDictionary") SysDictionary sysDictionary,
			HttpServletRequest request) throws Exception {
		// SysDictionary sysDictionary = new SysDictionary();
		sysDictionary.setType("productStyle");
		int total = 0;
		SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
		sysDictionarySearch.setType("productStyle");

		total = sysDictionaryService.getCount(sysDictionarySearch);
		List<SysDictionary> spaceStyleList = sysDictionaryService.getList(sysDictionary);
		// request.setAttribute("houseTypelist", houseTypelist);

		// SysDictionary spaceStyle = new SysDictionary();
		// spaceStyle.setType("productStyle");
		// List<SysDictionary> spaceStyleList =
		// sysDictionaryService.getList(spaceStyle);
		// request.setAttribute("spaceStyleList", spaceStyleList);

		// return "/online/newDesign/design_list";
		String sysDictionaryList = JsonUtil.getListToJsonData(spaceStyleList);
		List<SysDictionary> smallList = new JsonDataServiceImpl<SysDictionary>().getJsonToBeanList(sysDictionaryList,
				SysDictionary.class);
		return new ResponseEnvelope<SysDictionary>(total, smallList);
	}

	/**
	 * 最新设计方案查询
	 * 
	 * @param designPlan
	 * @param request
	 * @return object
	 */
	@RequestMapping(value = "/designPlanAll")
	public Object designPlanAll(@ModelAttribute("designPlan") DesignPlan designPlan, HttpServletRequest request)
			throws Exception {

		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");
		String area = request.getParameter("areaValue") == "" ? "" : request.getParameter("areaValue");
		designPlan.setSpaceAreas(area);

		List<DesignPlan> list = designPlanService.getDesignsList(designPlan);
		if (list != null && list.size() > 0) {
			for (DesignPlan desPlan : list) {
				// 获取用户名称
				SysUser user = sysUserService.get(desPlan.getUserId());
				if (user != null) {
					desPlan.setPlanUserName(user.getNickName());
				}
				// 通过空间ID获取空间编码、名称、内容。
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
					}
				}
				// 获取方案图片路径
				if (desPlan.getPicId() != null) {
					ResPic resPic = resPicService.get(desPlan.getPicId());
					if (resPic != null) {
						desPlan.setPicPath(resPic.getPicPath());
					}
				}
				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setId(-1);
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}

				// 关注用到
				desPlan.setAmount(getDesignUserFans(desPlan.getUserId(), request));
				// 获取该方案的评论数量
				DesignPlanComment designPlanComment = new DesignPlanComment();
				designPlanComment.setIsDeleted(0);
				designPlanComment.setDesignPlanId(desPlan.getId());
				List<DesignPlanComment> commentList = designPlanCommentService.getList(designPlanComment);
				if (commentList != null && commentList.size() > 0) {
					desPlan.setCommentCount(Integer.valueOf(commentList.size()));
				} else {
					desPlan.setCommentCount(0);
				}

				// 获取该方案的点赞数量
				DesignPlanLike designPlanLike = new DesignPlanLike();
				designPlanLike.setIsDeleted(0);
				designPlanLike.setDesignId(desPlan.getId());
				List<DesignPlanLike> likeList = designPlanLikeService.getList(designPlanLike);
				if (likeList != null && likeList.size() > 0) {
					desPlan.setLikeCount(Integer.valueOf(likeList.size()));
				} else {
					desPlan.setLikeCount(0);
				}

				// 获得分享数最多
				try {
					if (desPlan.getShareTotal() != null && desPlan.getShareTotal() > 0) {
						desPlan.setShareCount(desPlan.getShareTotal());
					} else {
						desPlan.setShareCount(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("designSourceType", designPlan.getDesignSourceType());

		request.setAttribute("list", list);
		return "/online/newDesign/designProgram_list";
	}

	/**
	 * 通过空间类型和面积查询设计方案列表
	 * 
	 * @param designPlan
	 * @param request
	 * @return object
	 */
	@RequestMapping(value = "/designListBySpaceTypeArea")
	public Object designListBySpaceTypeArea(@ModelAttribute("designPlan") DesignPlan designPlan,
			HttpServletRequest request) throws Exception {

		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");

		List<DesignPlan> list = null;
		SpaceCommon spacecommon = new SpaceCommon();
		String area = request.getParameter("areaValue") == "" ? "" : request.getParameter("areaValue");
		// Integer spaceFunctionId
		// =Integer.parseInt(request.getParameter("spaceFunctionId") == null ?
		// null :request.getParameter("spaceFunctionId"));
		designPlan.setSpaceAreas(area);
		// / designPlan.setSpaceFunctionId(spaceFunctionId);
		// List<SpaceCommon> spacecommonList =
		// / spaceCommonService.getList(spacecommon);
		// for (SpaceCommon spaceCom : spacecommonList) {
		// designPlan.setSpaceCommonId(spaceCom.getId());
		list = designPlanService.getPlanListByAreas(designPlan);
		if (list != null && list.size() > 0) {
			for (DesignPlan desPlan : list) {
				// 获取用户名称
				SysUser user = sysUserService.get(desPlan.getUserId());
				if (user != null) {
					desPlan.setPlanUserName(user.getUserName());
				}
				// 通过空间ID获取空间编码、名称、内容。
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
					}
				}
				// 获取方案图片路径
				if (desPlan.getPicId() != null) {
					ResPic resPic = resPicService.get(desPlan.getPicId());
					if (resPic != null) {
						desPlan.setPicPath(resPic.getPicPath());
					}
				}
				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setId(-1);
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}
				// 获取该方案的评论数量
				DesignPlanComment designPlanComment = new DesignPlanComment();
				designPlanComment.setIsDeleted(0);
				designPlanComment.setDesignPlanId(desPlan.getId());
				List<DesignPlanComment> commentList = designPlanCommentService.getList(designPlanComment);
				if (commentList != null && commentList.size() > 0) {
					desPlan.setCommentCount(Integer.valueOf(commentList.size()));
				} else {
					desPlan.setCommentCount(0);
				}

				// 获得分享数最多
				try {
					if (desPlan.getShareTotal() != null && desPlan.getShareTotal() > 0) {
						desPlan.setShareCount(desPlan.getShareTotal());
					} else {
						desPlan.setShareCount(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// }

		request.setAttribute("designSourceType", designPlan.getDesignSourceType());
		request.setAttribute("list", list);
		return "/online/newDesign/designProgram_list";
	}

	/**
	 * 通过风格选择设计方案
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/designListByStyle")
	public String designListByStyle(@ModelAttribute("designPlan") DesignPlan designPlan, HttpServletRequest request) {

		designPlan.setOrder("gmt_modified desc");
		// SysDictionary spaceStyle = new SysDictionary();
		// spaceStyle.setType("productStyle");
		// List<SysDictionary> spaceStyleList =
		// sysDictionaryService.getList(spaceStyle);
		List<DesignPlan> list = designPlanService.getDesignsList(designPlan);
		if (list != null && list.size() > 0) {
			for (DesignPlan desPlan : list) {
				// 获取用户名称
				SysUser user = sysUserService.get(desPlan.getUserId());
				if (user != null) {
					desPlan.setPlanUserName(user.getNickName());
				}
				// 通过空间ID获取空间编码、名称、内容。
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
					}
				}
				// 获取方案图片路径
				if (desPlan.getPicId() != null) {
					ResPic resPic = resPicService.get(desPlan.getPicId());
					if (resPic != null) {
						desPlan.setPicPath(resPic.getPicPath());
					}
				}
				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setId(-1);
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}
				// 获取该方案的评论数量
				DesignPlanComment designPlanComment = new DesignPlanComment();
				designPlanComment.setIsDeleted(0);
				designPlanComment.setDesignPlanId(desPlan.getId());
				List<DesignPlanComment> commentList = designPlanCommentService.getList(designPlanComment);
				if (commentList != null && commentList.size() > 0) {
					desPlan.setCommentCount(Integer.valueOf(commentList.size()));
				} else {
					desPlan.setCommentCount(0);
				}

				// 获得分享数最多
				try {
					if (desPlan.getShareTotal() != null && desPlan.getShareTotal() > 0) {
						desPlan.setShareCount(desPlan.getShareTotal());
					} else {
						desPlan.setShareCount(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		request.setAttribute("designSourceType", designPlan.getDesignSourceType());

		request.setAttribute("list", list);

		return "/online/newDesign/designProgram_list";
	}

	/**
	 * 设计方案详情接口
	 * 
	 * @param designPlanId
	 * @param request
	 * @return String
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@RequestMapping(value = "/designPlanDetailWeb")
	@ResponseBody
	public Object designPlanDetailWeb(@PathVariable String style, @RequestParam("msgId") String msgId,
			@RequestParam("designPlanId") Integer designPlanId, @RequestParam("designPlanType") Integer designPlanType,
			HttpServletRequest request) throws Exception {
		Long startTime = System.currentTimeMillis();
		String msg = "";
		if (designPlanId == null || StringUtils.isBlank(msgId) || designPlanType == null) {
			msg = "params error!";
			return new ResponseEnvelope<DesignPlanRenderScene>(false, msg, msgId);
		}

		LoginUser userFromSession = SystemCommonUtil.getLoginUserFromSession(request);
		if (userFromSession == null) {
			msg = "登录超时";
			return new ResponseEnvelope<DesignPlanRenderScene>(false, msg, msgId);
		}
		if (DesignPlanType.DESIGN_PLAN == designPlanType) {// 原方案类型
			// 获取该方案的信息
			DesignPlan designPlan = null;
			designPlan = designPlanService.get(designPlanId);
			if (designPlan == null) {
				msg = "id为" + designPlanId + "的方案不存在!";
				return new ResponseEnvelope<DesignPlanRenderScene>(false, msg, msgId);
			}
			ResponseEnvelope responseEnvelope_ = null;
			Map<Object, Object> paramsMap = new HashMap<Object, Object>();
			paramsMap.put("userId", designPlan.getUserId());
			paramsMap.put("planId", designPlanId);
			paramsMap.put("designPlanType", designPlanType);
			if (Utils.enableRedisCache()) {
				responseEnvelope_ = CommonCacher.getAll(ModuleType.DesignPlan, "designPlanDetailWeb", paramsMap);
			}
			if (responseEnvelope_ != null) {
				return responseEnvelope_;
			}

			try {
				if (designPlan != null) {
					// 媒介类型.如果没有值，则表示为web前端（2）
					String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
					/** 判断媒介类型是否一致 */
					String newMediaType = designPlan.getMediaType() + "";
					SysUser user = null;
					if (Utils.enableRedisCache()) {
						user = SysUserCacher.get(designPlan.getUserId());
					} else {
						user = sysUserService.get(designPlan.getUserId());
					}

					designPlan.setPlanUserName( user == null ? "无" : user.getUserName() == null ? "无" : user.getUserName());
					designPlan.setCreator( user == null ? "无" : user.getUserName() == null ? "无" : user.getUserName());
					if (designPlan.getSpaceCommonId() != null) {
						SpaceCommon spaceCommon = null;
						if (Utils.enableRedisCache()) {
							spaceCommon = SpaceCommonCacher.get(designPlan.getSpaceCommonId());
						} else {
							spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
						}

						if (spaceCommon != null) {
							designPlan.setSpaceCode(spaceCommon.getSpaceCode());
							designPlan.setSpaceName(spaceCommon.getSpaceName());
							designPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
						}
					}

					if (StringUtils.isEmpty(designPlan.getRemark())) {
						designPlan.setRemark("无");
					}

					int DesignPlanState = designPlanService.getDesignPlanState(designPlanId);
					/** 此设计方案的白膜产品多余0 那么就是未装修完成，小于0就是完成 */
					if (DesignPlanState == 0) {
						/** '是否装修完成状态 (1.未装修完成 2.已装修完成)', **/
						designPlan.setDesignPlanState("2");
					} else {
						designPlan.setDesignPlanState("1");
					}

					// 渲染图，无渲染图显示空间布局图
					designPlan = designPlanService.getMyDesignDetailPic(designPlan);
					if (designPlan.getPicType() == null) {
						designPlan.setPicType(0);
					}

					// 得到该方案的产品数量
					int i = designPlanProductRenderSceneService.getProductCount(designPlanId);
					designPlan.setPlanProductCount(i);

				} else {
					return new ResponseEnvelope<DesignPlanRenderScene>(false, "方案id=" + designPlanId + "不存在方案或被删除",
							msgId);
				}
			} catch (Exception e) {
				logger.error(e);
				return new ResponseEnvelope<DesignPlanRenderScene>(false, "数据异常", msgId);
			}

			ResponseEnvelope responseEnvelope = new ResponseEnvelope<DesignPlanRenderScene>(designPlan, msgId, true);
			if (Utils.enableRedisCache()) {
				CommonCacher.addAll(ModuleType.DesignPlan, "designPlanDetailWeb", paramsMap, responseEnvelope);
			}
			return responseEnvelope;
		} else if (DesignPlanType.SCENE_DESGSN_PLAN == designPlanType) {// 副本方案类型
			// 获取该方案的信息
			DesignPlanRenderScene planRenderScene = null;
			planRenderScene = designPlanRenderSceneService.get(designPlanId);
			if (planRenderScene == null) {
				msg = "id为" + designPlanId + "的方案不存在!";
				return new ResponseEnvelope<DesignPlanRenderScene>(false, msg, msgId);
			}
 
			try {
				if (planRenderScene != null) {
					// 媒介类型.如果没有值，则表示为web前端（2）
					String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
					/** 判断媒介类型是否一致 */
					String newMediaType = planRenderScene.getMediaType() + "";
					SysUser user = null;
					if (Utils.enableRedisCache()) {
						user = SysUserCacher.get(planRenderScene.getUserId());
					} else {
						user = sysUserService.get(planRenderScene.getUserId());
					}

					planRenderScene.setPlanUserName( user == null ? "无" : user.getUserName() == null ? "无" : user.getUserName());
					planRenderScene.setCreator( user == null ? "无" : user.getUserName() == null ? "无" : user.getUserName());
					if (planRenderScene.getSpaceCommonId() != null) {
						SpaceCommon spaceCommon = null;
						if (Utils.enableRedisCache()) {
							spaceCommon = SpaceCommonCacher.get(planRenderScene.getSpaceCommonId());
						} else {
							spaceCommon = spaceCommonService.get(planRenderScene.getSpaceCommonId());
						}

						if (spaceCommon != null) {
							planRenderScene.setSpaceCode(spaceCommon.getSpaceCode());
							planRenderScene.setSpaceName(spaceCommon.getSpaceName());
							planRenderScene.setSpaceAreas(spaceCommon.getSpaceAreas());
						}
					}

					if (StringUtils.isEmpty(planRenderScene.getRemark())) {
						planRenderScene.setRemark("无");
					}

					int DesignPlanState = designPlanRenderSceneService.getDesignPlanSceneState(designPlanId);
					/** 此设计方案的白膜产品多余0 那么就是未装修完成，小于0就是完成 */
					if (DesignPlanState == 0) {
						/** '是否装修完成状态 (1.未装修完成 2.已装修完成)', **/
						planRenderScene.setDesignPlanState("2");
					} else {
						planRenderScene.setDesignPlanState("1");
					}

					// 渲染图，无渲染图显示空间布局图
					planRenderScene = designPlanService.getMyDesignSceneDetailPic(planRenderScene);
					if (planRenderScene.getPicType() == null) {
						planRenderScene.setPicType(0);
					}

					// 得到该方案的产品数量
					int i = designPlanProductRenderSceneService.getProductCount(designPlanId);
					planRenderScene.setPlanProductCount(i);

				} else {
					return new ResponseEnvelope<DesignPlanRenderScene>(false, "方案id=" + designPlanId + "不存在方案或被删除",
							msgId);
				}
			} catch (Exception e) {
				logger.error(e);
				return new ResponseEnvelope<DesignPlanRenderScene>(false, "数据异常", msgId);
			}
			ResponseEnvelope responseEnvelope = new ResponseEnvelope<DesignPlanRenderScene>(planRenderScene, msgId,
					true);
			// if (Utils.enableRedisCache()) {
			// CommonCacher.addAll(ModuleType.DesignPlan, "designPlanDetailWeb", paramsMap,
			// responseEnvelope);
			// }
			return responseEnvelope;
		} else {

		}
		return new ResponseEnvelope<DesignPlanRenderScene>(false, msg, msgId);
	}

	/**
	 * 设计方案详情 编辑接口
	 * 
	 * @param designPlanId
	 *            ,plan_name,remark,msgId
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/updateDesignPlanDetailWeb")
	@ResponseBody
	public Object updateDesignPlanDetailWeb(String remark, String plan_name, String msgId, Integer designPlanId,
			String designPlanType) {
		// designPlanType 0代表原方案，1代表副本方案
		try {
			String msg = "";
			if (designPlanId == null) {
				msg = "参数designPlanId不能为空";
				return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
			}

			if (StringUtils.isBlank(msgId)) {
				msg = "参数msgId不能为空";
				return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
			}
			if ("1".equals(designPlanType) && StringUtils.isNotBlank(plan_name)) {// 修改设计方案副本的名称信息
				DesignPlanRenderScene designPlanRenderScene = new DesignPlanRenderScene();
				designPlanRenderScene.setId(designPlanId);
				designPlanRenderScene.setPlanName(plan_name);
				designPlanRenderScene.setRemark(remark);
				designPlanRenderService.updataBakSceneName(designPlanRenderScene);
				return new ResponseEnvelope<String>(true, "修改成功", msgId);
			}

			DesignPlan designPlan = designPlanService.get(designPlanId);
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(designPlan.getId());
			if (designPlan != null) {
				// 处理设计方案名称(huangsongbo) ->start
				if (StringUtils.isBlank(plan_name)) {
					Integer designTempletId = designPlan.getDesignId();
					if (designTempletId != null) {
						DesignTemplet designTemplet = designTempletService.get(designTempletId);
						if (designTemplet != null) {
							plan_name = designTemplet.getDesignName();
						}
					}
				}
				// 处理设计方案名称(huangsongbo) ->end
				newDesignPlan.setId(designPlanId);
				newDesignPlan.setRemark(remark);
				newDesignPlan.setPlanName(plan_name);
				designPlanService.update(newDesignPlan);
				if (Utils.enableRedisCache()) {
					DesignCacher.removePlan(designPlanId);
				}
			} else {
				return new ResponseEnvelope<String>(false, "设计方案未找到", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<String>(false, "修改失败", msgId);
		}
		return new ResponseEnvelope<String>(true, "修改成功", msgId);
	}

	/**
	 * 设计方案详情
	 * 
	 * @param designPlanId
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/designPlanDetail")
	public String designPlanDetail(Integer designPlanId, HttpServletRequest request) throws Exception {
		if (designPlanId == null) {
			return "";
		}

		// 获取该方案的信息
		DesignPlan designPlan = new DesignPlan();
		designPlan.setId(designPlanId);
		designPlan.setIsDeleted(0);
		List<DesignPlanResult> list = designPlanService.getDesignList(designPlan);
		DesignPlanResult planResult = new DesignPlanResult();
		if (list != null && list.size() > 0) {
			planResult = list.get(0);
			if (planResult != null) {
				// 获取该方案的渲染图 路径
				String renderPicIds = planResult.getRenderPicIds();
				if (renderPicIds != null && !"".equals(renderPicIds)) {
					if (renderPicIds.contains(",")) {
						String[] strs = renderPicIds.split(",");
						for (String picId : strs) {
							ResPic resPic = resPicService.get(Utils.getIntValue(picId));
							planResult.getPicList().add(resPic == null ? "" : resPic.getPicPath());
						}
					} else {
						ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
						planResult.getPicList().add(resPic == null ? "" : resPic.getPicPath());
					}
				}
				planResult.setDesignSource("6");
				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				planResult.setCollectState(
						getDesignCollectState(planResult.getDesignId(), planResult.getDesignSource(), request));
				// 判断该方案是否点赞，如果没有收藏返回0，已收藏返回1。
				planResult.setLikeState(getDesignLikeState(planResult.getDesignId(), request));
				// 得到该方案的产品数量
				planResult.setDesignProductCount(getDesignProductCount(planResult.getDesignId(), request));
				// 关注用到
				request.setAttribute("count", getDesignUserFans(planResult.getUserId(), request));
			}
		}

		// 获取评论数量
		DesignPlanCommentSearch designPlanComment = new DesignPlanCommentSearch();
		designPlanComment.setIsDeleted(0);
		designPlanComment.setDesignPlanId(designPlanId);
		int total = designPlanCommentService.getCount(designPlanComment);
		String modelShow = request.getParameter("type") == "" ? "" : request.getParameter("type");
		String commentType = request.getParameter("commentType") == null ? "" : request.getParameter("commentType");
		request.setAttribute("type", modelShow);
		request.setAttribute("commentType", commentType);
		request.setAttribute("designPlan", planResult);
		request.setAttribute("total", total);
		return "/online/newDesign/design_detail";
	}

	/**
	 * 设计方案详情
	 * 
	 * @param designPlanId
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/designDetail")
	public String designDetail(Integer designPlanId, HttpServletRequest request) throws Exception {
		if (designPlanId == null) {
			return "";
		}

		// 获取该方案的信息
		DesignPlan designPlan = new DesignPlan();
		designPlan.setId(designPlanId);
		designPlan.setIsDeleted(0);
		List<DesignPlanResult> list = designPlanService.getDesignList(designPlan);
		DesignPlanResult planResult = new DesignPlanResult();
		if (list != null && list.size() > 0) {
			planResult = list.get(0);
			if (planResult != null) {
				// 获取该方案的渲染图 路径
				String renderPicIds = planResult.getRenderPicIds();
				if (renderPicIds != null && !"".equals(renderPicIds)) {
					if (renderPicIds.contains(",")) {
						String[] strs = renderPicIds.split(",");
						for (String picId : strs) {
							ResPic resPic = resPicService.get(Utils.getIntValue(picId));
							planResult.getPicList().add(resPic == null ? "" : resPic.getPicPath());
						}
					} else {
						ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
						planResult.getPicList().add(resPic == null ? "" : resPic.getPicPath());
					}
				}
				planResult.setDesignSource("6");
				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				planResult.setCollectState(
						getDesignCollectState(planResult.getDesignId(), planResult.getDesignSource(), request));
				// 判断该方案是否点赞，如果没有收藏返回0，已收藏返回1。
				planResult.setLikeState(getDesignLikeState(planResult.getDesignId(), request));
				// 得到该方案的产品数量
				planResult.setDesignProductCount(getDesignProductCount(planResult.getDesignId(), request));
				// 关注用到
				request.setAttribute("count", getDesignUserFans(planResult.getUserId(), request));
			}
		}

		// 获得分享数最多
		try {
			if (planResult.getShareTotal() != null && planResult.getShareTotal() > 0) {
				planResult.setShareCount(planResult.getShareTotal());
			} else {
				planResult.setShareCount(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取评论数量
		DesignPlanCommentSearch designPlanComment = new DesignPlanCommentSearch();
		designPlanComment.setIsDeleted(0);
		designPlanComment.setDesignPlanId(designPlanId);
		int total = designPlanCommentService.getCount(designPlanComment);
		String modelShow = request.getParameter("type") == "" ? "" : request.getParameter("type");
		String commentType = request.getParameter("commentType") == null ? "" : request.getParameter("commentType");
		request.setAttribute("commentType", commentType);
		request.setAttribute("designPlan", planResult);
		request.setAttribute("type", modelShow);
		request.setAttribute("total", total);
		return "/online/newDesign/designDetail";
	}

	/**
	 * 设计方案详情
	 * 
	 * @param designPlanId
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/designDetailPage")
	public String designDetailPage(Integer designPlanId, HttpServletRequest request) throws Exception {
		if (designPlanId == null) {
			return "";
		}

		// 获取该方案的信息
		DesignPlan designPlan = new DesignPlan();
		designPlan.setId(designPlanId);
		designPlan.setIsDeleted(0);
		List<DesignPlanResult> list = designPlanService.getDesignList(designPlan);
		DesignPlanResult planResult = new DesignPlanResult();
		if (list != null && list.size() > 0) {
			planResult = list.get(0);
			if (planResult != null) {
				// 获取该方案的渲染图 路径
				String renderPicIds = planResult.getRenderPicIds();
				if (renderPicIds != null && !"".equals(renderPicIds)) {
					if (renderPicIds.contains(",")) {
						String[] strs = renderPicIds.split(",");
						for (String picId : strs) {
							ResPic resPic = resPicService.get(Utils.getIntValue(picId));
							planResult.getPicList().add(resPic == null ? "" : resPic.getPicPath());
						}
					} else {
						ResPic resPic = resPicService.get(Utils.getIntValue(renderPicIds));
						planResult.getPicList().add(resPic == null ? "" : resPic.getPicPath());
					}
				}
				planResult.setDesignSource("6");
				// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
				planResult.setCollectState(
						getDesignCollectState(planResult.getDesignId(), planResult.getDesignSource(), request));
				// 判断该方案是否点赞，如果没有收藏返回0，已收藏返回1。
				planResult.setLikeState(getDesignLikeState(planResult.getDesignId(), request));
				// 得到该方案的产品数量
				planResult.setDesignProductCount(getDesignProductCount(planResult.getDesignId(), request));
				// 关注用到
				request.setAttribute("count", getDesignUserFans(planResult.getUserId(), request));
			}
		}

		// 获取评论数量
		DesignPlanCommentSearch designPlanComment = new DesignPlanCommentSearch();
		designPlanComment.setIsDeleted(0);
		designPlanComment.setDesignPlanId(designPlanId);
		int total = designPlanCommentService.getCount(designPlanComment);
		request.setAttribute("designPlan", planResult);
		request.setAttribute("total", total);
		return "/online/newDesign/design_detailsPage";
	}

	/**
	 * 保存 设计方案 接口
	 */
	@RequestMapping(value = "/planSave")
	@ResponseBody
	public Object planSave(@PathVariable String style, @RequestParam(value = "templateId") Integer templateId,
			@RequestParam(value = "planSource", required = false) String planSource,
			@RequestParam(value = "msgId") String msgId,
			@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "houseId", required = false) String houseId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long startTime = System.currentTimeMillis();

		logger.info("planSouce+++++++++++++++++++++++++++++++++>" + planSource);
		logger.info("livingId++++++++++" + livingId);

		String requestUri = request.getRequestURI();

		if (requestUri.startsWith("/your-owner-uri/")) {
			request.setAttribute("com.tingyun.agent.APPLICATION_NAME", "CustomApplicationName");
		}

		/* 一：参数验证 */
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (templateId == null) {
			msg = "参数templateId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		/*
		 * if(planSource == null){ msg = "参数planSource不能为空"; return new
		 * ResponseEnvelope<DesignPlan>(false,msg,msgId); }
		 */

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("houseId", houseId);
		paramMap.put("livingId", livingId);

		String unitName = baseHouseService.selectUnitsName(paramMap);

		DesignPlan designPlan = new DesignPlan();
		DesignTemplet designTemplet = null;
		if (Utils.enableRedisCache()) {
			designTemplet = DesignCacher.getTemplet(templateId);
		} else {
			designTemplet = designTempletService.get(new Integer(templateId));
		}

		if (designTemplet == null) {
			return new ResponseEnvelope<DesignTemplet>(false, "数据异常", msgId);
		}

		Map map = new HashMap();
		map.put("templetId", designTemplet.getId());

		// 1.校验是否可以新增设计方案，间隔时间由配置文件决定
		/* 二： 校验是否可以存储,间隔时间由配置文件决定 */
		boolean isNewPlan = isNewPlan(request, map);
		Integer newFlag = 0;
		if (isNewPlan) {
			newFlag = 1;
			/* 媒介类型.如果没有值，则表示为web前端（2） */
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);

			/* 三：从样板房中拷贝数据到设计方案中 */
			LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);

			// 2.复制样板房信息
			// String DesignName = "";
			designPlan.setMediaType(Utils.getIntValue(mediaType));
			designPlan.setPlanCode(designTemplet.getDesignCode() + "_" + Utils.generateRandomDigitString(6));
			designPlan.setPlanName(designTemplet.getDesignName());// 设计方案名称
			designPlan.setUserId(loginUser == null ? -1 : loginUser.getId());// 用户id
			designPlan.setDesignSourceType(7);// 样板房
			designPlan.setDesignId(designTemplet.getId());// 样板房iddesignPlan.setPicId(designTemplet.getPicId());//
															// 设计方案缩略图

			designPlan.setHouseId(Integer.valueOf(houseId));
			designPlan.setLivingId(Integer.valueOf(livingId));
			designPlan.setPlanSource(planSource);
			designPlan.setResidentialUnitsName(unitName);

			Integer spaceId = designTemplet.getSpaceCommonId();
			SpaceCommon spaceCommon = spaceCommonService.get(spaceId);
			designPlan.setSysCode(spaceCommon.getSpaceCode() + "_" + Utils.generateRandomDigitString(6));// 空间编码(场景编码)
			// 根据媒介类型赋值模型
			// U3D空间公共资源模型路径
			// String modelU3dId = this.getU3dModelId(mediaType == null ? "2" :
			// mediaType.toString(), spaceCommon);
			// Integer modelU3dnewId = -1;
			// logger.info("wpc.copyFile.modelU3dnewId=" + modelU3dId);
			// modelU3dnewId = this.copyFile(modelU3dId == null ? "" :
			// modelU3dId.toString(), "model",
			// "design.designPlan.u3dmodel", null, request,
			// designPlan.getPlanCode());
			//
			// logger.info("mediaType=" + mediaType + ";spaceCommon.id="
			// + (spaceCommon == null ? "" : spaceCommon.getId().toString()));
			//
			// designPlan.setModelU3dId(modelU3dnewId == null ? new Integer(-1)
			// : modelU3dnewId);
			// 样板房ID
			designPlan.setDesignTemplateId(designTemplet.getId());

			// 公共空间拷贝//u3d模型直接引用样板房的模型
			String modelId = this.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), designTemplet);
			if (StringUtils.isNotBlank(modelId)) {
				logger.info("wpc.copyFile.modelId=" + modelId);
				designPlan.setModelId(Utils.getIntValue(modelId));
			} else {
				designPlan.setModelId(-1);
			}

			designPlan.setSpaceCommonId(designTemplet.getSpaceCommonId());// 适用的空间ID
			designPlan.setDesignStyleId(null);// 设计风格,后续在更新产品时,需根据产品类型判断

			/* 拷贝了一份 样板房的 u3d模型 到 设计方案中 */
			Integer resFileId = this.planCopyFile(
					designTemplet.getConfigFileId() == null ? "" : designTemplet.getConfigFileId().toString(), "file",
					"design.designPlan.u3dconfig", null, request, designPlan.getPlanCode());
			designPlan.setConfigFileId(resFileId);// //设计方案配置文件
			// 创建新的设计方案信息

			// TODO:创建设计方案操作日志记录的数据>>start
			DesignPlanOperationLog designPlanOperationLog = new DesignPlanOperationLog();
			// LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
			designPlanOperationLog.setUserId(loginUser.getId());
			designPlanOperationLog.setDesignPlanId(designPlan.getId());
			designPlanOperationLog.setStatus(0);
			designPlanOperationLog.setBusinessKey(SystemCommonConstant.CREARE_DESIGN_PLAN);
			designPlanOperationLog.setGmtCreate(new Date());
			designPlanOperationLog.setIsDeleted(0);
			designPlanOperationLog.setCreator(loginUser.getLoginName());
			designPlanOperationLog
					.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			designPlanOperationLog.setModifier(loginUser.getLoginName());
			designPlanOperationLog.setGmtModified(new Date());
			designPlanOperationLogService.insertSelective(designPlanOperationLog);
			// 添加数据结束<<end

			try {
				designPlan.setIsOpen(0);
				sysSave(designPlan, request);
				int id = designPlanService.add(designPlan);
				logger.info("add:id=" + id);
				designPlan.setId(id);
				DesignCacher.removePlan(id);

				// 回写资源信息
				ResFile rf = new ResFile();
				rf.setId(resFileId);
				rf.setBusinessId(id);
				resFileService.update(rf);
				ResourceCacher.removeResFile(rf.getId());
				// 回写资源信息
				// ResModel rm_u3d = new ResModel();
				// if (modelU3dnewId != null && modelU3dnewId.intValue() > 0) {
				// rm_u3d.setId(modelU3dnewId);
				// rm_u3d.setBusinessId(id);
				// resModelService.update(rm_u3d);
				// ResourceCacher.removeResModel(rm_u3d.getId());
				// }

				// 3.复制样板房产品表信息
				/* 拷贝一份 样板房的产品到 到设计方案产品表中 */
				// 样板房设计方案的产品列表代入
				DesignTempletProduct templetProduct = new DesignTempletProduct();
				templetProduct.setIsDeleted(0);
				templetProduct.setDesignTempletId(designTemplet.getId());
				List<DesignTempletProduct> temProductList = null;
				if (Utils.enableRedisCache()) {
					temProductList = DesignCacher.getTempletProductList(templetProduct);
				} else {
					temProductList = designTempletProductService.getList(templetProduct);
				}

				for (DesignTempletProduct dtp : temProductList) {
					DesignPlanProduct planProduct = new DesignPlanProduct();
					planProduct.setPlanId(id);
					// 拷贝挂节点
					planProduct.setPosIndexPath(dtp.getPosIndexPath());
					planProduct.setPosName(dtp.getPosName());
					planProduct.setProductId(dtp.getProductId());
					planProduct.setProductSequence(dtp.getProductSequence());
					// 拷贝样板房中产品组信息
					planProduct.setProductGroupId(dtp.getProductGroupId());
					planProduct.setIsMainProduct(dtp.getIsMainProduct());
					planProduct.setPlanGroupId(dtp.getPlanGroupId());
					planProduct.setGroupType(dtp.getGroupType());
					String isHide = Utils.getValue("design.designPlan.product.isHide", "true");
					if ("true".equals(isHide.trim())) {
						BaseProduct baseProduct = null;
						if (Utils.enableRedisCache()) {
							baseProduct = BaseProductCacher.get(dtp.getProductId());
						} else {
							baseProduct = baseProductService.get(dtp.getProductId());
						}
						planProduct.setIsHide(1);
						if (baseProduct != null) {
							if (StringUtils.isNotBlank(baseProduct.getProductTypeValue())
									&& baseProduct.getProductSmallTypeValue() != null) {
								// 天花，地面,墙面,门的原模默认显示
								if (("1".equals(baseProduct.getProductTypeValue()))
										|| ("2".equals(baseProduct.getProductTypeValue()))
										|| ("3".equals(baseProduct.getProductTypeValue()))
										|| ("4".equals(baseProduct.getProductTypeValue()))) {
									planProduct.setIsHide(0);
								}
							}
						}
					} else {
						planProduct.setIsHide(0);
					}
					planProduct.setPlanProductId(dtp.getId());
					planProduct.setInitProductId(dtp.getProductId());
					// planProduct.setDisplayStatus(0);// 0表示空模，1表示模型
					planProduct.setPosIndexPath(dtp.getPosIndexPath());// 挂节点信息
					planProduct.setPosName(dtp.getPosName());
					planProduct.setBindParentProductId(dtp.getBindParentProductId());
					sysSave(planProduct, request);
					designPlanProductService.add(planProduct);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEnvelope<DesignPlan>(false, "数据异常", msgId);
			}
		} else {
			// 4.如果在配置的时间段内创建, 则查询最新创建的设计方案
			// 从数据库中读取最大值的数据,进入
			if (map != null && map.size() > 0) {
				List list = (List) map.get("list");
				if (list != null && list.size() > 0) {
					Map resultMap = (Map) list.get(0);
					Integer spaceCommonId = (Integer) resultMap.get("space_common_id");
					Date gmtModified = Utils.parseDate(
							Utils.formatDate((Date) resultMap.get("max(gmt_modified)"), Utils.DATE_TIME),
							Utils.DATE_TIME);
					Integer userIdDb = (Integer) resultMap.get("user_id");
					DesignPlan lastdesignPlan = new DesignPlan();
					lastdesignPlan.setUserId(userIdDb);
					lastdesignPlan.setGmtModified(gmtModified);
					lastdesignPlan.setSpaceCommonId(spaceCommonId);

					List<DesignPlan> newList = designPlanService.getList(lastdesignPlan);
					designPlan = newList.get(0);
				}
			}

		}
		Long endTime = System.currentTimeMillis();
		logger.info("复制样板房消耗时间：" + (endTime - startTime));
		return getDesignPlan(livingId, houseId, designPlan.getId(), newFlag, msgId, request);
	}

	/**
	 * 通过设计方案id查询U3D设计方案对象(设计方案-获取设计方案进入模型)
	 * 
	 * @param ：designPlanId
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getDesignPlan", "/getDesignPlanWeb" })
	@ResponseBody
	public Object getDesignPlan(@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "houseId", required = false) String houseId,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId, Integer newFlag,
			@RequestParam(value = "msgId", required = false) String msgId, HttpServletRequest request)
			throws Exception {

		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		Long startTime = System.currentTimeMillis();
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
		}
		if (designPlanId == null) {
			msg = "参数designPlanId不能为空";
			return new ResponseEnvelope<UnityDesignPlan>(false, msg, msgId);
		}

		if (newFlag == null) {
			newFlag = 0;
		}
		// 媒介类型.如果没有值，则表示为web前端（2）
		// Integer mediaType =
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
		// 1.组装设计方案基础信息
		// 获取设计方案数据信息
		DesignPlan  designPlan = designPlanService.get(designPlanId);

		if (designPlan == null) {
			msg = "没有找到designPlanId为" + designPlanId + "的设计方案!";
			return new ResponseEnvelope<UnityDesignPlan>(false, msg, msgId);
		}
		// 构建unity模型加载时需要的model对象:返回设计方案,空间模型,产品列表及产品配置等信息
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		unityDesignPlan.setNewFlag(newFlag);// 是否是第一次进入
		// 一.获取设计方案信息
		unityDesignPlan.setServiceUrl(SERVERURL);// 访问地址
		unityDesignPlan.setDesignPlanId(designPlan.getId());// 设计方案id
		unityDesignPlan.setPlanName(designPlan.getPlanName());// 设计方案名称
		unityDesignPlan.setPlanCode(designPlan.getPlanCode());// 设计方案编码
		unityDesignPlan.setResourcesUrl(RESOURCESURL);// 资源文件访问地址

		unityDesignPlan.setEffectsConfig(designPlan.getEffectsConfig()); // 特效配置数据

		Integer modelTemplteFileId = designPlan.getModelId();
		// 将资源id转换为访问路径(模型设计方案公共资源)
		if (modelTemplteFileId != null && modelTemplteFileId.intValue() > 0) {
			ResModel resModel = null;
			if (Utils.enableRedisCache()) {
				resModel = ResourceCacher.getModel(modelTemplteFileId);
			} else {
				resModel = resModelService.get(modelTemplteFileId);
			}

			String filePath = null;
			if (resModel != null) {
				filePath = resModel.getModelPath();
			}
			if (!StringUtils.isEmpty(filePath)) {
				unityDesignPlan.setU3dModelPath(filePath);
			} else {
				unityDesignPlan.setU3dModelPath("");
			}
		} else {
			unityDesignPlan.setU3dModelPath("");
		}

		Integer configFileId = designPlan.getConfigFileId();
		// 将资源id转换为访问路径(模型设计方案总体配置文件)
		logger.info("=====================获取设计方案配置文件。id=" + configFileId);
		if (configFileId != null && configFileId.intValue() > 0) {
			// ResFile resFile=null;
			ResDesign resDesign = null;
			if (Utils.enableRedisCache()) {
				resDesign = ResourceCacher.getResDesign(designPlan.getConfigFileId());
				logger.info("===============cache:" + resDesign);
			} else {
				resDesign = resDesignService.get(designPlan.getConfigFileId());
			}

			String filePath = null;
			if (resDesign != null) {
				filePath = resDesign.getFilePath();
			}
			logger.info("===============filePath" + filePath);
			if (!StringUtils.isEmpty(filePath)) {
				designPlan.setFilePath(resDesign.getFilePath());
			}
		}
		// 模型总体配置文件
		unityDesignPlan.setDesignPlanConfigPath(designPlan.getFilePath());
		// U3D界面UI文件
		unityDesignPlan.setDesignPlanUIPath("/pages/online/resource/UI.assetbundle");

		/**
		 * 方案定制产品、订单信息获取
		 * add by xiaoxc 20180828
		 */
		Integer customizedProductConfigFileId = designPlan.getCustomizedProductConfigId();
		if (null != customizedProductConfigFileId && 0 < customizedProductConfigFileId) {
			ResDesign resDesign = resDesignService.get(customizedProductConfigFileId);
			unityDesignPlan.setCustomizedProductConfigPath(resDesign == null ? "" : resDesign.getFilePath());
		}
		unityDesignPlan.setCustomizedProductOrderCode(designPlan.getCustomizedProductOrderCode());
		unityDesignPlan.setOrderId(designPlan.getOrderId());

		// 2.组装空间信息
		// 二.获取空间信息
		UnitySpaceCommon unitySpaceCommon = new UnitySpaceCommon();
		Integer spaceId = designPlan.getSpaceCommonId();
		if (spaceId != null && spaceId > 0) {
			SpaceCommon spaceCommon = null;
			if (Utils.enableRedisCache()) {
				spaceCommon = SpaceCommonCacher.get(spaceId);
			} else {
				spaceCommon = spaceCommonService.get(spaceId);
			}

			String spaceCode = "";
			if (StringUtils.isNotBlank(designPlan.getSysCode())) {
				spaceCode = designPlan.getSysCode().substring(0, designPlan.getSysCode().lastIndexOf("_"));
			}

			unitySpaceCommon.setSpaceCommonId(spaceId);
			unitySpaceCommon.setSpaceCode(spaceCode);// 空间编码(场景编码)
			unitySpaceCommon.setSpaceName(spaceCommon.getSpaceName());
			unitySpaceCommon.setHouseId(houseId);
			unitySpaceCommon.setLivingId(livingId);

			unitySpaceCommon.setPlanSource(designPlan.getPlanSource());
			unitySpaceCommon.setResidentialUnitsName(designPlan.getResidentialUnitsName()); // 小区户型名称
			// U3D空间公共资源模型路径
			String modelU3dId = designPlan.getModelU3dId() == null ? "-1" : designPlan.getModelU3dId().toString();
			Integer spaceModelU3dId = StringUtils.isEmpty(modelU3dId) ? -1 : new Integer(modelU3dId);
			if (spaceModelU3dId > 0) {
				ResModel resModel = null;
				if (Utils.enableRedisCache()) {
					resModel = ResourceCacher.getModel(spaceModelU3dId);
				} else {
					resModel = resModelService.get(spaceModelU3dId);
				}

				if (resModel != null && resModel.getModelPath() != null) {
					unitySpaceCommon.setSpaceModelPath(resModel.getModelPath());
				}
			} else {
				unitySpaceCommon.setSpaceModelPath("");
			}
			Integer houseTypeValue = null;
			if (spaceCommon != null) {
				houseTypeValue = spaceCommon.getSpaceFunctionId();
			}
			if (houseTypeValue != null) {
				SysDictionary sd = sysDictionaryService.getSysDictionary("houseType", houseTypeValue);
				unitySpaceCommon.setHouseTypeValue(sd.getValue());
				unitySpaceCommon.setHouseTypeName(sd.getName());
			} else {
				unitySpaceCommon.setHouseTypeValue(-1);
				unitySpaceCommon.setHouseTypeName("");
			}
		}
		unityDesignPlan.setUnitySpaceCommon(unitySpaceCommon);

		// 设计模式
		DesignTemplet designTemplet = null;
		if (Utils.enableRedisCache()) {
			designTemplet = DesignCacher.getTemplet(designPlan.getDesignTemplateId());
		} else {
			designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
		}

		if (designTemplet == null) {
			logger.error("designTemplet is null !designTempletId = " + designPlan.getDesignTemplateId());
			return new ResponseEnvelope<UnityDesignPlan>(false,
					"designTemplet is null !designTempletId = " + designPlan.getDesignTemplateId(), msgId);
		} else {
			if (designTemplet.getDesignCode().endsWith("_000")) {
				unityDesignPlan.setDesignMode("0");
			} else {
				unityDesignPlan.setDesignMode("1");
			}
		}
		// 3.组装设计方案产品列表
		// 三.获取方案中用到的产品列表
		// 产品信息,返回装修顺序及相机参数值
		// 产品级别数据存储
		List<UnityPlanProduct> unityPlanProductList = new ArrayList<UnityPlanProduct>();
		// 最终数据U3DUI界面转换存储
		List<UnityPlanProduct> newUnityPlanProductList = new ArrayList<UnityPlanProduct>();
		// 获取方案产品列表
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setPlanId(designPlan.getId());
		List<DesignPlanProduct> designProductList = null;
		if (Utils.enableRedisCache()) {
			designProductList = DesignPlanProductCacher.getAllList(designPlanProduct);
		} else {
			designProductList = designPlanProductService.getList(designPlanProduct);
		}

		if (designProductList != null && designProductList.size() > 0) {
			TreeSet<String> productTypeCodeSet = new TreeSet<String>();
			Map<String, UnityPlanProduct> unityPlanProductMap_p = new HashMap<String, UnityPlanProduct>();
			for (DesignPlanProduct planProduct : designProductList) {
				int groupId = planProduct.getProductGroupId() == null ? 0 : planProduct.getProductGroupId();
				int isMain = planProduct.getIsMainProduct() == null ? 0 : planProduct.getIsMainProduct();
				UnityPlanProduct unityPlanProduct = new UnityPlanProduct();
				unityPlanProduct.setIsDirty(planProduct.getIsDirty());
				unityPlanProduct.setPlanProductId(planProduct.getId());
				unityPlanProduct.setProductSequence(planProduct.getProductSequence());
				unityPlanProduct.setMaterialPicPaths(new String[] {});
				unityPlanProduct.setDecorationModelPath(new String[] {});
				unityPlanProduct.setPosIndexPath(planProduct.getPosIndexPath());
				unityPlanProduct.setPosName(planProduct.getPosName());
				unityPlanProduct.setBindProductId(planProduct.getBindParentProductId());

				// 4.组合或结构产品逻辑处理
				/* 处理结构返回格式 */
				Integer groupType = planProduct.getGroupType();
				unityPlanProduct.setGroupType(groupType);
				Map<Integer, Integer> memoryMap = new HashMap<Integer, Integer>();
				if (groupType == null || new Integer(0).equals(groupType)) {
					/* 组合 */
					unityPlanProduct.setProductGroupId(groupId);
					unityPlanProduct.setIsMainProduct(isMain);
					unityPlanProduct.setPlanGroupId(planProduct.getPlanGroupId());
					unityPlanProduct.setPlanStructureId("");
					/* 识别是否是结构组合->是->得到对应结构的id */
					/* 先从memoryMap中找(防止重复访问数据库) */
					if (groupId > 0) {
						if (memoryMap.containsKey(groupId)) {
							unityPlanProduct.setProductStructureId(memoryMap.get(groupId));
						} else {
							/* 如果该组合是结构组合,找对应的结构id */
							GroupProduct groupProduct = groupProductService.get(groupId);
							if (groupProduct != null) {
								Integer structureId = groupProduct.getStructureId();
								if (structureId != null && structureId > 0) {
									unityPlanProduct.setProductStructureId(structureId);
									String PlanGroupId = unityPlanProduct.getPlanGroupId();
									if (StringUtils.isNotBlank(PlanGroupId)) {
										String[] strs = PlanGroupId.split("_");
										unityPlanProduct.setPlanStructureId(structureId + "_" + strs[1]);
									}
									memoryMap.put(groupId, structureId);
								}
							}
						}
					}
					/* 识别是否是结构组合->是->得到对应结构的id->end */
					if (unityPlanProduct.getProductStructureId() != null
							&& unityPlanProduct.getProductStructureId() > 0) {

					} else {
						unityPlanProduct.setProductStructureId(new Integer(0));
					}
				} else if (new Integer(1).equals(groupType)) {
					/* 结构 */
					unityPlanProduct.setIsMainProduct(new Integer(0));
					unityPlanProduct.setProductGroupId(new Integer(0));
					unityPlanProduct.setPlanGroupId("");
					unityPlanProduct.setPlanStructureId(planProduct.getPlanGroupId());
					unityPlanProduct.setProductStructureId(groupId);
				}
				/* 处理结构返回格式->end */
				// 如果该产品是主产品，则返回该方案产品组合的方案产品ID数据
				GroupProductDetails groupProductDetails = new GroupProductDetails();
				// if( isMain == 1 || groupId > 0 ){
				if (groupId > 0) {
					DesignPlanProduct planProducts = new DesignPlanProduct();
					planProducts.setProductGroupId(groupId);
					planProducts.setPlanId(designPlan.getId());
					planProducts.setGroupType(groupType);
					planProducts.setIsDeleted(0);
					List<DesignPlanProduct> dppList = null;
					if (Utils.enableRedisCache()) {
						dppList = DesignPlanProductCacher.getAllList(planProducts);
					} else {
						dppList = designPlanProductService.getList(planProducts);
					}

					if (Lists.isNotEmpty(dppList)) {
						Integer arrayIds[] = new Integer[dppList.size()];
						for (int i = 0; i < dppList.size(); i++) {
							DesignPlanProduct dpp = dppList.get(i);
							arrayIds[i] = dpp.getId();
						}
						if (new Integer(1).equals(groupType))
							unityPlanProduct.setPlanProductStructureIds(arrayIds);
						else {
							/* if(new Integer(1).equals(isMain)) */
							unityPlanProduct.setPlanProductGroupIds(arrayIds);
							unityPlanProduct.setPlanProductStructureIds(arrayIds);
						}
					}
				} else {
					// 如果是次产品则去检索它是否有组合过并且是主产品，是则设置成1
					// 白模产品不需要检索
					/* 查询条件设置组合查询的state(根据用户类型,内部用户能查到测试和上架的组合,其他用户只能查到上架的组合) */
					LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
					Integer userType = loginUser.getUserType();
					List<Integer> statusList = new ArrayList<Integer>();
					statusList.add(1);
					if (userType == 1) {
						statusList.add(2);
					}
					groupProductDetails.setStatusList(statusList);
					/*
					 * 查询条件设置组合查询的state(根据用户类型,内部用户能查到测试和上架的组合,其他用户只能查到上架的组合)-> end
					 */
					if (!planProduct.getProductId().equals(planProduct.getInitProductId())) {
						groupProductDetails.setProductId(planProduct.getProductId());
						groupProductDetails.setIsMain(1);
						List<GroupProductDetails> gpdList = null;
						if (Utils.enableRedisCache()) {
							gpdList = GroupProductDetailsCache.getList(groupProductDetails);
						} else {
							gpdList = groupProductDetailsService.getList(groupProductDetails);
						}
						if (gpdList.size() > 0) {
							unityPlanProduct.setIsMainProduct(1);
						}
					}
				}

				// 5.产品基本信息组装
				// 产品的基本信息
				BaseProduct baseProduct = null;
				if (planProduct.getProductId() != null && planProduct.getProductId() > 0) {
					if (Utils.enableRedisCache()) {
						baseProduct = BaseProductCacher.get(planProduct.getProductId());
					} else {
						baseProduct = baseProductService.get(planProduct.getProductId());
					}
				}
				SysDictionary dictionary = new SysDictionary();
				SysDictionary sDictionary = new SysDictionary();
				if (baseProduct != null && baseProduct.getProductSmallTypeValue() != null
						&& baseProduct.getProductTypeValue() != null) {
					SysDictionary sysdictionary = new SysDictionary();
					sysdictionary.setType("productType");
					sysdictionary.setValue(Integer.parseInt(baseProduct.getProductTypeValue()));
					dictionary = sysDictionaryService.getSysDictionary("productType",
							Utils.getIntValue(baseProduct.getProductTypeValue()));
					if (dictionary == null || dictionary.getId() == null) {
						logger.error("baseProduct.getProductTypeValue()=" + baseProduct.getProductTypeValue()
								+ ";baseProduct.getId()=" + baseProduct.getId());
					}
					SysDictionary sysDictionary = new SysDictionary();
					sysDictionary.setType(dictionary.getValuekey());
					sysDictionary.setValue(baseProduct.getProductSmallTypeValue());
					sDictionary = sysDictionaryService.getSysDictionary(dictionary.getValuekey(),
							baseProduct.getProductSmallTypeValue());
					if (sDictionary != null) {
						unityPlanProduct.setMoveWay(sDictionary.getAtt5());
					} else {
						logger.error("sDictionary is null, sd.getValuekey()=" + dictionary.getValuekey()
								+ ";baseProduct.getProductSmallTypeValue()=" + baseProduct.getProductSmallTypeValue()
								+ ";productid=" + baseProduct.getProductId() + ";baseProduct.getProductTypeValue()="
								+ baseProduct.getProductTypeValue());
					}
					if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(dictionary.getValuekey())) {
						unityPlanProduct
								.setBgWall(Utils.getIsBgWall(sDictionary == null ? "" : sDictionary.getValuekey()));
					} else {
						unityPlanProduct.setBgWall(0);
					}
				}
				if (null != baseProduct) {
					unityPlanProduct.setProductId(baseProduct.getId());
					unityPlanProduct.setProductCode(baseProduct.getProductCode());
					unityPlanProduct.setParentProductId(baseProduct.getParentId());
					unityPlanProduct.setProductLength(baseProduct.getProductLength());
					unityPlanProduct.setProductWidth(baseProduct.getProductWidth());
					unityPlanProduct.setProductHeight(baseProduct.getProductHeight());
					unityPlanProduct.setMinHeight(baseProduct.getMinHeight());
					// 如果该墙面有绑定关系，则取绑定产品白模长宽高
					String bindProductid = planProduct.getBindParentProductId();
					if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(dictionary.getValuekey()) && StringUtils.isNotBlank(bindProductid)) {
						String array[] = bindProductid.split(",");
						BaseProduct baiMoProduct = null;
						StringBuffer fullPaveLength = new StringBuffer();
						for (String bindId : array) {
							if (Utils.enableRedisCache()) {
								baiMoProduct = BaseProductCacher.get(Utils.getIntValue(bindId));
							} else {
								baiMoProduct = baseProductService.get(Utils.getIntValue(bindId));
							}
							if (baiMoProduct != null && baiMoProduct.getFullPaveLength() != null) {
								fullPaveLength.append(baiMoProduct.getFullPaveLength() + ",");
							}
						}
						if (baiMoProduct != null) {
							if (baiMoProduct.getProductLength() != null) {
								unityPlanProduct.setInitModelLength(Utils.getIntValue(baiMoProduct.getProductLength()));
							}
							if (baiMoProduct.getProductWidth() != null) {
								unityPlanProduct.setInitModelWidth(Utils.getIntValue(baiMoProduct.getProductWidth()));
							}
							if (baiMoProduct.getProductHeight() != null) {
								unityPlanProduct.setInitModelHeight(Utils.getIntValue(baiMoProduct.getProductHeight()));
							}
							if (baiMoProduct.getFullPaveLength() != null) {
								String fullPave = fullPaveLength.toString();
								unityPlanProduct.setFullPaveLength(
										fullPave != null ? fullPave.substring(0, fullPave.length() - 1) : fullPave);
							}
						}
					}
					// 如果是背景墙则取白模背景墙产品的长宽高
					Integer baiMoId = planProduct.getInitProductId();
					String bjType = Utils.getValue("app.smallProductType.stretch", "");
					if (Utils.isMateProductType(sDictionary == null ? "" : sDictionary.getValuekey(), bjType)
							&& baiMoId != null && baiMoId.intValue() > 0) {
						BaseProduct baiMoProduct = null;
						if (Utils.enableRedisCache()) {
							baiMoProduct = BaseProductCacher.get(baiMoId);
						} else {
							baiMoProduct = baseProductService.get(baiMoId);
						}
						if (baiMoProduct != null) {
							if (baiMoProduct.getProductLength() != null) {
								unityPlanProduct.setInitModelLength(Utils.getIntValue(baiMoProduct.getProductLength()));
							}
							if (baiMoProduct.getProductWidth() != null) {
								unityPlanProduct.setInitModelWidth(Utils.getIntValue(baiMoProduct.getProductWidth()));
							}
							if (baiMoProduct.getProductHeight() != null) {
								unityPlanProduct.setInitModelHeight(Utils.getIntValue(baiMoProduct.getProductHeight()));
							}
							if (baiMoProduct.getFullPaveLength() != null) {
								unityPlanProduct.setFullPaveLength(baiMoProduct.getFullPaveLength());
							}
						}
					}
				} else {
					logger.error("planProduct.getProductId():" + planProduct.getProductId());
					return new ResponseEnvelope<UnityDesignPlan>(false,
							"planProduct.getProductId():" + planProduct.getProductId(), msgId);
				}

				String productTypeValue = null;
				if (baseProduct != null) {
					productTypeValue = baseProduct.getProductTypeValue();
					if (baseProduct.getBmIds() != null) {
						unityPlanProduct.setIsCustomized(1);
					}
				}
				String u3dModelId = baseProductService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(),
						baseProduct);
				ResModel resModel = resModelService.get(StringUtils.isEmpty(u3dModelId) ? 0 : new Integer(u3dModelId));
				if (resModel != null) {
					unityPlanProduct.setProductModelPath(resModel.getModelPath());
					unityPlanProduct.setModelLength(resModel.getLength());
					unityPlanProduct.setModelWidth(resModel.getWidth());
					unityPlanProduct.setModelHeight(resModel.getHeight());
					unityPlanProduct.setModelMinHeight(resModel.getMinHeight());
				} else {
					// unityPlanProduct.setProductModelPath("");
					/* 应对只有材质的硬装产品无模型的情况 */
					if (Utils.enableRedisCache()) {
						baseProduct = BaseProductCacher.get(planProduct.getProductId());
					} else {
						baseProduct = baseProductService.get(planProduct.getProductId());
					}
					boolean isHard = false;
					if (baseProduct != null) {
						isHard = baseProductService.isHard(baseProduct);
					}
					if (isHard) {
						BaseProduct baimoProduct = null;
						Integer currentProductId = null;
						// 换贴图应找当前产品模型
						if (planProduct.getModelProductId() != null && planProduct.getModelProductId() != 0) {
							currentProductId = planProduct.getModelProductId();
							unityPlanProduct.setModelProductId(planProduct.getModelProductId());
						} else {
							currentProductId = planProduct.getInitProductId();
						}
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(currentProductId);
						} else {
							baimoProduct = baseProductService.get(currentProductId);
						}
						// 获取不同媒介u3d模型
						String modelId = baseProductService.getU3dModelId(mediaType, baimoProduct);
						if (StringUtils.isNotBlank(modelId)) {
							ResModel resModel1 = null;
							if (Utils.enableRedisCache()) {
								resModel1 = ResourceCacher.getModel(Integer.valueOf(modelId));
							} else {
								resModel1 = resModelService.get(Integer.valueOf(modelId));
							}
							if (resModel1 != null) {
								unityPlanProduct.setProductModelPath(resModel1.getModelPath());
							}
						}
					}
				}
				if (baseProduct != null && StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
					String materialIds = baseProduct.getMaterialPicIds();
					List<String> idsInfo = Utils.getListFromStr(materialIds);
					List<String> materialPicList = new ArrayList<String>();
					ResTexture resTextureTemp = null;
					for (String idStr : idsInfo) {
						ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));
						if (resTexture == null)
							continue;
						if (resTextureTemp == null) {
							resTextureTemp = resTexture;
							unityPlanProduct.setTextureAttrValue(resTextureTemp.getTextureAttrValue());
							unityPlanProduct.setLaymodes(resTextureTemp.getLaymodes());
						}
						if (resTexture != null && resTexture.getId() != null) {
							materialPicList.add(resTexture.getFilePath());
						}
					}
					unityPlanProduct.setMaterialPicPaths(
							(String[]) materialPicList.toArray(new String[materialPicList.size()]));
				}

				// 产品子集数量
				unityPlanProduct.setLeafNum(0);
				// 标示产品在界面中的展示类型
				unityPlanProduct.setIsLeaf(new Integer(1));
				// 产品是否隐藏
				unityPlanProduct.setIsHide(planProduct.getIsHide());
				// 获取每个产品的饰品列表
				// 6.产品材质组装
				String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
				if (StringUtils.isNotBlank(splitTexturesInfo)) {
					if (StringUtils.isNotBlank(planProduct.getSplitTexturesChooseInfo())) {
						splitTexturesInfo = planProduct.getSplitTexturesChooseInfo();
					}
					Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(baseProduct.getId(),
							splitTexturesInfo, "choose");
					unityPlanProduct.setIsSplit((Integer) map.get("isSplit"));
					unityPlanProduct.setSplitTexturesChoose((List<SplitTextureDTO>) map.get("splitTexturesChoose"));
				} else {
					List<SplitTextureDTO> splitTextureDTOList = new ArrayList<SplitTextureDTO>();
					String materialIds = baseProduct.getMaterialPicIds();
					Integer materialId = 0;
					if (StringUtils.isNotBlank(materialIds)) {
						List<String> materialIdStrList = Utils.getListFromStr(materialIds);
						if (materialIdStrList != null && materialIdStrList.size() > 0) {
							materialId = Integer.valueOf(materialIdStrList.get(0));
						}
					}
					if (materialId != null && materialId > 0) {
						ResTexture resTexture = resTextureService.get(materialId);
						if (resTexture != null) {
							splitTextureDTOList = new ArrayList<SplitTextureDTO>();
							List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
							SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
							ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
							resTextureDTO.setKey(splitTextureDTO.getKey());
							resTextureDTO.setProductId(baseProduct.getId());
							resTextureDTOList.add(resTextureDTO);
							splitTextureDTO.setList(resTextureDTOList);
							splitTextureDTOList.add(splitTextureDTO);
							unityPlanProduct.setSplitTexturesChoose(splitTextureDTOList);
						}
					}
				}
				/* 处理拆分材质产品的默认材质信息->end */
				UnityPlanProduct unityPlanProduct_p = unityPlanProduct.copy();
				// 产品类别信息r
				if (!StringUtils.isEmpty(productTypeValue)) {
					// SysDictionary sd =
					// sysDictionaryService.getSysDictionaryByValue("productType",
					// new Integer(productTypeValue), request);
					SysDictionary sd = sysDictionaryService.getSysDictionary("productType",
							new Integer(productTypeValue));
					if (sd != null) {
						// 为保证父节点与子节点的productTypeCode相同，指定如下规则：
						// 子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
						// 父节点时，parentTypeCode存在值(暂时不取)，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值
						unityPlanProduct.setProductTypeValue(sd.getValue());
						unityPlanProduct.setProductTypeCode(sd.getValuekey());
						unityPlanProduct.setProductTypeName(sd.getName());

						unityPlanProduct_p.setProductTypeValue(sd.getValue());
						unityPlanProduct_p.setProductTypeCode(sd.getValuekey());
						unityPlanProduct_p.setProductTypeName(sd.getName());
						// 获取子节点的父节点信息
						unityPlanProduct.setParentTypeCode(sd.getValuekey());
						unityPlanProduct.setParentTypeName(sd.getName());
						unityPlanProduct.setParentTypeValue(sd.getValue());

						unityPlanProduct_p.setParentTypeValue(-1);
						unityPlanProduct_p.setParentTypeCode("");
						unityPlanProduct_p.setParentTypeName("");

						// 获取子节点的节点信息
						Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
						if (productSmallTypeValue != null && new Integer(productSmallTypeValue).intValue() > 0) {
							// SysDictionary sdson =
							// sysDictionaryService.getSysDictionaryByValue(sd.getValuekey(),
							// productSmallTypeValue, request);
							SysDictionary sdson = sysDictionaryService.getSysDictionary(sd.getValuekey(),
									productSmallTypeValue);
							if (sdson != null) {
								unityPlanProduct.setSmallTypeValue(sdson.getValue());
								unityPlanProduct.setSmallTypeCode(sdson.getValuekey());
								unityPlanProduct.setSmallTypeName(sdson.getName());
								// 是否是白模
								Integer isBm = 0;
								if ("baimo".equals(sdson.getAtt3())) {
									isBm = 1;
								}
								unityPlanProduct.setIsBaimo(isBm);
								unityPlanProduct_p.setIsBaimo(isBm);
								// 软装硬装以下规则处理，同时按最小基本的数据定义-按1硬装2软装,默认软装
								String rootType = StringUtils.isEmpty(sdson.getAtt1()) ? "2" : sdson.getAtt1().trim();
								unityPlanProduct.setRootType(rootType);
								unityPlanProduct_p.setRootType("");
								unityPlanProduct_p.setIsBaimo(isBm);
								unityPlanProduct_p.setSmallTypeValue(-1);
								unityPlanProduct_p.setSmallTypeCode("");
								unityPlanProduct_p.setSmallTypeName("");
							}
						}
					}
				}

				// 存储产品分类集合,便于组装UI界面
				if (!StringUtils.isEmpty(unityPlanProduct.getProductTypeCode())) {
					productTypeCodeSet.add(unityPlanProduct.getProductTypeCode());
					// 默认使用第一条记录信息做数据代入
					if (!unityPlanProductMap_p.containsKey(unityPlanProduct.getProductTypeCode())) {
						unityPlanProductMap_p.put(unityPlanProduct.getProductTypeCode(), unityPlanProduct_p);
					}
				} else {
					logger.error("unityPlanProduct.getProductTypeCode() is null ;unityPlanProduct.getProductTypeCode()="
							+ unityPlanProduct.getProductTypeCode() + ";unityPlanProduct.getProductId="
							+ unityPlanProduct.getProductId() + ";unityPlanProduct.getProductCode="
							+ unityPlanProduct.getProductCode());
				}

				Map<String, String> map = new HashMap<String, String>();
				/**** 将材质的长宽也给 塞到这个list 中取 **/
				// BaseProduct baseProduct=baseProductService.get(productId);
				String aterialPicIds = baseProduct.getMaterialPicIds();
				/** 材质id **/
				if (aterialPicIds != null && !"".equals(aterialPicIds)) {
					if (StringUtils.isNumeric(aterialPicIds)) {
						ResTexture resTexture = resTextureService.get(Integer.parseInt(aterialPicIds));
						if (resTexture != null) {
							unityPlanProduct.setTextureWidth(resTexture.getFileWidth() + "");
							unityPlanProduct.setTextureHeight(resTexture.getFileHeight() + "");
						}
					}
				}
				/*** 在组合产品查询列表 中 增加产品属性 ****/
				map = productAttributeService.getPropertyMap(baseProduct.getId());
				unityPlanProduct.setPropertyMap(map);

				// 样板房产品ID
				unityPlanProduct.setTemplateProductId(
						planProduct.getInitProductId() == null ? "" : planProduct.getInitProductId().toString());

				// 组装产品的规则
				String productTypeCode = unityPlanProduct.getProductTypeCode();// 产品大类
				String productSmallTypeCode = unityPlanProduct.getSmallTypeCode();// 产品小类
				String productId = null;// 产品ID
				if (unityPlanProduct.getProductId() != null) {
					productId = unityPlanProduct.getProductId().toString();// 产品ID
				}
				Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(productId, productTypeCode,
						productSmallTypeCode, spaceId, designTemplet.getId(), new DesignRules(), map);
				// Map<String,String> rulesMap = new HashMap<>();
				// rulesMap.put("moveSign","[{\"moveSignType\":\"productLevel\",\"moveSignKey\":\"baimo_pipe_0001\",\"moveType\":\"vertical\"}]");
				unityPlanProduct.setRulesMap(rulesMap);
				logger.warn("产品Id=" + productId + "spaceId=" + spaceId + ":" + rulesMap);
				unityPlanProductList.add(unityPlanProduct);
			}

			// 组装每个产品的分类信息
			Map<String, List<UnityPlanProduct>> productMap = new HashMap<String, List<UnityPlanProduct>>();
			if (!productTypeCodeSet.isEmpty()) {
				for (String pproductTypeCode : productTypeCodeSet) {
					List<UnityPlanProduct> productList = new ArrayList<UnityPlanProduct>();
					for (UnityPlanProduct sunityPlanProduct : unityPlanProductList) {
						if (pproductTypeCode.equals(sunityPlanProduct.getProductTypeCode())) {
							productList.add(sunityPlanProduct);
						}
					}
					productMap.put(pproductTypeCode, productList);
				}
			}

			// 7.定制装修导航
			// 四.定制装修导航(在产品列表中该增加大按钮性质,同时,只有一个时,删除小按钮)
			// 为保证父节点与子节点的productTypeCode相同，指定如下规则：
			// 子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
			// 父节点时，parentTypeCode存在值，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值
			if (!productTypeCodeSet.isEmpty()) {
				for (String pproductTypeCode : productTypeCodeSet) {
					// 获取父节点信息
					UnityPlanProduct munityPlanProduct = unityPlanProductMap_p.get(pproductTypeCode);
					UnityPlanProduct punityPlanProduct = munityPlanProduct.copy();
					punityPlanProduct.setIsLeaf(0);
					List<UnityPlanProduct> list = productMap.get(pproductTypeCode);
					if (list != null && list.size() > 0) {
						punityPlanProduct.setLeafNum(list.size());
						punityPlanProduct.setProductGroupId(0);
						punityPlanProduct.setIsMainProduct(0);
					}

					// 新建样板房隐藏白膜背景墙
					/*
					 * BaseProduct baseProduct =
					 * baseProductService.get(punityPlanProduct.getProductId());
					 * if(baseProduct!=null){ String smallTypeValue =
					 * baseProduct.getProductSmallTypeValue()+""; String typeValue =
					 * baseProduct.getProductTypeValue(); if("3".equals(typeValue)){
					 * if("16".equals(smallTypeValue)||"17".equals( smallTypeValue)){
					 * punityPlanProduct.setIsHide(1); } } }
					 */

					newUnityPlanProductList.add(punityPlanProduct);
					// 追加下属所有子节点信息
					for (UnityPlanProduct sunityPlanProduct : unityPlanProductList) {
						if (pproductTypeCode.equals(sunityPlanProduct.getProductTypeCode())) {
							// if(list.size()>1){
							newUnityPlanProductList.add(sunityPlanProduct);
							// }
						}
					}
				}
			}
		}

		ComparatorT cpmparator = new ComparatorT();
		Collections.sort(newUnityPlanProductList, cpmparator);

		unityDesignPlan.setDatalist(newUnityPlanProductList);
		Long endTime = System.currentTimeMillis();
		logger.info("创建设计方案消耗时间++++++++++++++++:" + (endTime - startTime));
		return new ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);

	}

	/**
	 * 设计方案改造
	 * 
	 * @param designPlanId
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/planRemould")
	@ResponseBody
	public Object planReform(@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "houseId", required = false) String houseId, @RequestParam("msgId") String msgId,
			@RequestParam("designPlanId") Integer designPlanId, HttpServletRequest request) throws Exception {

		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (designPlanId == null) {
			msg = "参数designPlanId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		DesignPlan desPlan = new DesignPlan();
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null) {
			msg = "请先登录！";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		// 判断是不是自己的方案，如果是则直接设计该方案,不是则拷贝该方案数据
		DesignPlan design = null;
		if (Utils.enableRedisCache()) {
			design = DesignCacher.get(designPlanId);
		} else {
			design = designPlanService.get(designPlanId);
		}

		if (design == null) {
			msg = "没有找到designPlanId为" + designPlanId + "的设计方案!";
			return new ResponseEnvelope<UnityDesignPlan>(false, msg, msgId);
		}

		if (design != null && design.getUserId() == loginUser.getId()) {
			return getDesignPlan(houseId, livingId, designPlanId, 0, msgId, request);
		} else {
			WebDesignPlanController wpc = new WebDesignPlanController();

			DesignPlan plan = designPlanService.get(designPlanId);
			if (plan != null) {
				if (plan.getPlanCode() != null) {
					desPlan.setPlanCode(plan.getPlanCode().substring(0, plan.getPlanCode().length() - 6)
							+ Utils.generateRandomDigitString(6));
				}
				// 媒介类型.如果没有值，则表示为web前端（2）
				String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);

				desPlan.setMediaType(Utils.getIntValue(mediaType));
				desPlan.setPlanName(plan.getPlanName());
				desPlan.setUserId(loginUser.getId());
				desPlan.setDesignSourceType(6);
				desPlan.setDesignId(plan.getId());

				// 设计方案的模型应该从数据库中拷贝一份新的,否则删掉方案后,原设计方案中的模型将会被删除
				Integer picId = null;
				if (plan.getPicId() != null) {
					picId = this.copyFile(plan.getPicId().toString(), "pic", "design.designPlan.pic", null, request,
							desPlan.getPlanCode());
					desPlan.setPicId(plan.getPicId());
				} else {
					desPlan.setPicId(-1);
				}
				Integer modelId = null;

				Integer model3dId = null;
				if (plan.getModel3dId() != null) {
					model3dId = this.copyFile(plan.getModel3dId().toString(), "model", "design.designPlan.3dmodel",
							null, request, desPlan.getPlanCode());
					desPlan.setModel3dId(model3dId);// 设计方案3d模型文件
				} else {
					desPlan.setModel3dId(-1);
				}

				// 拷贝公共资源
				desPlan.setSysCode(plan.getSysCode().substring(0, plan.getSysCode().length() - 6)
						+ Utils.generateRandomDigitString(6));
				Integer modelU3dId = null;
				if (plan.getModelU3dId() != null && plan.getModelU3dId() > 0) {
					modelU3dId = this.copyFile(plan.getModelU3dId().toString(), "model", "design.designPlan.u3dmodel",
							null, request, desPlan.getPlanCode());
					// desPlan.setWebModelU3dId(modelU3dId);//设计方案u3d模型文件
					desPlan.setModelU3dId(modelU3dId);
				} else {
					desPlan.setModelU3dId(-1);
				}

				desPlan.setSpaceCommonId(plan.getSpaceCommonId());// 适用的空间ID
				desPlan.setDesignStyleId(plan.getDesignStyleId());// 设计风格,后续在更新产品时,需根据产品类型判断
				Integer resFileId = null;
				if (plan.getConfigFileId() != null) {
					resFileId = this.planCopyFile(plan.getConfigFileId().toString(), "file",
							"design.designPlan.u3dconfig", null, request, desPlan.getPlanCode());
					desPlan.setConfigFileId(resFileId);// //设计方案配置文件
				} else {
					desPlan.setConfigFileId(-1);// //设计方案配置文件
				}

				// 创建新的设计方案信息
				try {
					sysSave(desPlan, request);
					int id = designPlanService.add(desPlan);
					logger.info("add:id=" + id);
					desPlan.setId(id);

					// 回写资源信息
					if (picId != null) {
						ResPic rp = new ResPic();
						rp.setId(picId);
						rp.setBusinessId(id);
						resPicService.update(rp);
						ResourceCacher.removeResPic(rp.getId());
					}
					// 回写资源信息
					if (resFileId != null) {
						ResFile rf = new ResFile();
						rf.setId(resFileId);
						rf.setBusinessId(id);
						resFileService.update(rf);
						ResourceCacher.removeResFile(rf.getId());
					}
					// 回写资源信息
					if (modelId != null) {
						ResModel rm = new ResModel();
						rm.setId(modelId);
						rm.setBusinessId(id);
						resModelService.update(rm);
						ResourceCacher.removeResModel(rm.getId());
					}
					// 回写资源信息
					// if(model3dId!=null){
					// ResModel rm_3d = new ResModel();
					// rm_3d.setId(model3dId);
					// rm_3d.setBusinessId(id);
					// resModelService.update(rm_3d);
					// }
					// 回写资源信息
					if (modelU3dId != null) {
						ResModel rm_u3d = new ResModel();
						rm_u3d.setId(modelU3dId);
						rm_u3d.setBusinessId(id);
						resModelService.update(rm_u3d);
						ResourceCacher.removeResModel(rm_u3d.getId());
					}
					// 设计方案的产品列表代入
					DesignPlanProduct desPlanProduct = new DesignPlanProduct();
					desPlanProduct.setIsDeleted(0);
					desPlanProduct.setPlanId(plan.getId());
					List<DesignPlanProduct> planProductList = null;
					if (Utils.enableRedisCache()) {
						planProductList = DesignPlanCacher.getDesignPlanProductList(desPlanProduct);
					} else {
						planProductList = designPlanProductService.getList(desPlanProduct);
					}

					for (DesignPlanProduct dpProduct : planProductList) {
						DesignPlanProduct planProduct = new DesignPlanProduct();
						planProduct.setPlanId(id);
						planProduct.setProductId(dpProduct.getProductId());
						planProduct.setProductSequence(dpProduct.getProductSequence());
						planProduct.setIsHide(0);
						/*
						 * String isHide = app.getString("design.designPlan.product.isHide"); if(
						 * "true".equals(isHide.trim()) ){ BaseProduct baseProduct =
						 * baseProductService.get(dpProduct.getProductId()); planProduct.setIsHide(1);
						 * if( baseProduct != null ){ String filterTypeValue = "1,2,3"; if(
						 * filterTypeValue. indexOf(baseProduct.getProductTypeValue()) > -1 ){
						 * planProduct.setIsHide(0); } } }else { planProduct.setIsHide(0); }
						 */
						// 相机位置配置文件
						// Integer locationFileId = null;
						// if(dpProduct.getLocationFileId()!=null){
						// locationFileId =
						// this.copyFile(dpProduct.getLocationFileId().toString(),
						// "file",
						// "design.designPlan.locationFile", null, request);
						// planProduct.setLocationFileId(locationFileId);
						// }else{
						// planProduct.setLocationFileId(-1);
						// }
						sysSave(planProduct, request);
						// Integer planProductId =
						designPlanProductService.add(planProduct);
						// // 回写资源信息
						// if(locationFileId!=null){
						// ResFile rf2 = new ResFile();
						// rf2.setId(locationFileId);
						// rf2.setBusinessId(planProductId);
						// resFileService.update(rf2);
						// }
					}

				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEnvelope<DesignPlan>(false, "数据异常", msgId);
				}
			}

			return getDesignPlan(houseId, livingId, designPlanId, 0, msgId, request);
		}

	}

	/**
	 * 设计方案改造
	 * 
	 * @param designPlanId
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/planReform")
	public String planReform(Integer designPlanId, HttpServletRequest request) throws Exception {

		DesignPlan desPlan = new DesignPlan();
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null) {
			return "";
		}
		Map map = new HashMap();
		map.put("templetId", designPlanId);
		// 校验是否可以存储,间隔时间由配置文件决定
		boolean isNewPlan = isNewPlan(request, map);
		if (isNewPlan) {
			DesignPlan plan = designPlanService.get(designPlanId);
			if (plan != null) {
				if (plan.getPlanCode() != null) {
					desPlan.setPlanCode(plan.getPlanCode().substring(0, plan.getPlanCode().length() - 6)
							+ Utils.generateRandomDigitString(6));
				}
				String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
				if (desPlan.getMediaType() == null) {
					desPlan.setMediaType(Utils.getIntValue(mediaType));
				}
				desPlan.setPlanName(plan.getPlanName());
				desPlan.setUserId(loginUser.getId());
				desPlan.setDesignSourceType(6);
				desPlan.setDesignId(plan.getId());

				// 设计方案的模型应该从数据库中拷贝一份新的,否则删掉方案后,原设计方案中的模型将会被删除
				Integer picId = null;
				if (plan.getPicId() != null) {
					picId = this.copyFile(plan.getPicId().toString(), "pic", "design.designPlan.pic", null, request,
							desPlan.getPlanCode());
					desPlan.setPicId(plan.getPicId());
				} else {
					desPlan.setPicId(-1);
				}
				// Integer modelId = null;
				// if (plan.getModelId() != null) {
				// modelId = this.copyFile(plan.getModelId().toString(),
				// "model", "design.designPlan.model", null, request);
				// desPlan.setModelId(modelId);//设计方案模型文件
				// } else {
				// desPlan.setModelId(-1);
				// }
				// Integer model3dId = null;
				// if (plan.getModel3dId() != null) {
				// model3dId = this.copyFile(plan.getModel3dId().toString(),
				// "model", "design.designPlan.3dmodel", null, request);
				// desPlan.setModel3dId(model3dId);//设计方案3d模型文件
				// } else {
				// desPlan.setModel3dId(-1);
				// }
				// Integer modelU3dId = null;
				// if (plan.getWebModelU3dId() != null) {
				// modelU3dId =
				// this.copyFile(plan.getWebModelU3dId().toString(), "model",
				// "design.designPlan.u3dmodel", null, request);
				// desPlan.setWebModelU3dId(modelU3dId);//设计方案u3d模型文件
				// } else {
				// desPlan.setWebModelU3dId(-1);
				// }

				desPlan.setSysCode(plan.getSysCode().substring(0, plan.getSysCode().length() - 6)
						+ Utils.generateRandomDigitString(6));
				Integer modelU3dId = null;
				if (plan.getModelU3dId() != null && plan.getModelU3dId() > 0) {
					modelU3dId = this.copyFile(plan.getModelU3dId().toString(), "model", "design.designPlan.u3dmodel",
							null, request, desPlan.getPlanCode());
					// desPlan.setWebModelU3dId(modelU3dId);//设计方案u3d模型文件
					desPlan.setModelU3dId(modelU3dId);
				} else {
					desPlan.setModelU3dId(-1);
				}

				desPlan.setSpaceCommonId(plan.getSpaceCommonId());// 适用的空间ID
				desPlan.setDesignStyleId(plan.getDesignStyleId());// 设计风格,后续在更新产品时,需根据产品类型判断
				Integer resFileId = null;
				if (plan.getConfigFileId() != null) {
					resFileId = this.planCopyFile(plan.getConfigFileId().toString(), "file",
							"design.designPlan.locationFile", null, request, desPlan.getPlanCode());
					desPlan.setConfigFileId(resFileId);// // 设计方案配置文件
				} else {
					desPlan.setConfigFileId(-1);// // 设计方案配置文件
				}
				// 样板房ID
				desPlan.setDesignTemplateId(plan.getDesignTemplateId());
				// 场景
				// desPlan.setEveningFileId(plan.getEveningFileId());
				// desPlan.setNightFileId(plan.getNightFileId());
				// desPlan.setDawnFileId(plan.getDawnFileId());
				// 创建新的设计方案信息
				try {
					sysSave(desPlan, request);
					int id = designPlanService.add(desPlan);
					logger.info("add:id=" + id);
					desPlan.setId(id);

					// 回写资源信息
					if (picId != null) {
						ResPic rp = new ResPic();
						rp.setId(picId);
						rp.setBusinessId(id);
						resPicService.update(rp);
					}
					// 回写资源信息
					if (resFileId != null) {
						ResFile rf = new ResFile();
						rf.setId(resFileId);
						rf.setBusinessId(id);
						resFileService.update(rf);
					}
					// 回写资源信息
					// if (modelId != null) {
					// ResModel rm = new ResModel();
					// rm.setId(modelId);
					// rm.setBusinessId(id);
					// resModelService.update(rm);
					// }
					// 回写资源信息
					// if (model3dId != null) {
					// ResModel rm_3d = new ResModel();
					// rm_3d.setId(model3dId);
					// rm_3d.setBusinessId(id);
					// resModelService.update(rm_3d);
					// }
					// 回写资源信息
					if (modelU3dId != null) {
						ResModel rm_u3d = new ResModel();
						rm_u3d.setId(modelU3dId);
						rm_u3d.setBusinessId(id);
						resModelService.update(rm_u3d);
					}
					// 设计方案的产品列表代入
					DesignPlanProduct desPlanProduct = new DesignPlanProduct();
					desPlanProduct.setIsDeleted(0);
					desPlanProduct.setPlanId(plan.getId());
					List<DesignPlanProduct> planProductList = designPlanProductService.getList(desPlanProduct);
					for (DesignPlanProduct dpProduct : planProductList) {
						DesignPlanProduct planProduct = new DesignPlanProduct();
						planProduct.setPlanId(id);
						planProduct.setProductId(dpProduct.getProductId());
						planProduct.setProductSequence(dpProduct.getProductSequence());
						// 相机位置配置文件
						// Integer locationFileId = null;
						// if(dpProduct.getLocationFileId()!=null){
						// locationFileId =
						// this.copyFile(dpProduct.getLocationFileId().toString(),
						// "file",
						// "design.designPlan.locationFile", null, request);
						// planProduct.setLocationFileId(locationFileId);
						// }else{
						// planProduct.setLocationFileId(-1);
						// }
						sysSave(planProduct, request);
						// Integer planProductId =
						designPlanProductService.add(planProduct);
						// // 回写资源信息
						// if(locationFileId!=null){
						// ResFile rf2 = new ResFile();
						// rf2.setId(locationFileId);
						// rf2.setBusinessId(planProductId);
						// resFileService.update(rf2);
						// }
					}

				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}
			}
		} else {
			// 从数据库中读取最大值的数据,进入
			if (map != null && map.size() > 0) {
				List list = (List) map.get("list");
				if (list != null && list.size() > 0) {
					Map resultMap = (Map) list.get(0);
					Integer spaceCommonId = (Integer) resultMap.get("space_common_id");
					Date gmtModified = Utils.parseDate(
							Utils.formatDate((Date) resultMap.get("max(gmt_modified)"), Utils.DATE_TIME),
							Utils.DATE_TIME);
					Integer userIdDb = (Integer) resultMap.get("user_id");
					DesignPlan lastdesignPlan = new DesignPlan();
					lastdesignPlan.setUserId(userIdDb);
					lastdesignPlan.setGmtModified(gmtModified);
					lastdesignPlan.setSpaceCommonId(spaceCommonId);

					List<DesignPlan> newList = designPlanService.getList(lastdesignPlan);
					desPlan = newList.get(0);
				}
			}

		}
		return "redirect:/online/web/design/designPlan/getDesignPlan.htm?designPlanId=" + desPlan.getId();
	}

	/**
	 * 用户中心-我的设计方案列表
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/myDesignPlan")
	public Object myDesignPlan(@ModelAttribute("designPlan") DesignPlan designPlan, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		designPlan.setUserId(loginUser.getId());
		// designPlan.setDesignSourceType(7);
		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");

		List<DesignPlan> list = designPlanService.getPlanList(designPlan);
		try {
			for (DesignPlan desPlan : list) {
				SysUser user = sysUserService.get(desPlan.getUserId());
				if (user != null) {
					desPlan.setPlanUserName(user.getNickName());
				}
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
					}
				}
				if (desPlan.getPicId() != null) {
					ResPic resPic = resPicService.get(desPlan.getPicId());
					if (resPic != null) {
						desPlan.setPicPath(resPic.getPicPath());
					}
				}

				// 评论总数
				DesignPlanComment designPlanComment = new DesignPlanComment();
				designPlanComment.setDesignPlanId(desPlan.getId());
				List<DesignPlanComment> commentList = designPlanCommentService.getList(designPlanComment);
				if (commentList != null && commentList.size() > 0) {
					desPlan.setCommentCount(Integer.valueOf(commentList.size()));
				} else {
					desPlan.setCommentCount(0);
				}
			}
			// 查询用户粉丝列表
			request.setAttribute("fansList", fansList(request));
			// 查询用户关注的列表
			request.setAttribute("attentionList", attentionList(request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", designPlan.getMsgId());
		}
		request.setAttribute("list", list);
		return "/online/user/myDesign";
	}


	/**
	 * 用户中心-我的设计方案接口
	 * 
	 * @return Object
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/myDesignPlanWeb")
	@ResponseBody
	public Object myDesignPlanWeb(@PathVariable String style, @ModelAttribute("designPlan") DesignPlan designPlan,
			HttpServletRequest request) throws Exception {

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<DesignPlan>(false, "请登录！", designPlan.getMsgId());
		}
		String mediaType = SystemCommonUtil.getMediaType(request);
		List<DesignPlan> list = new ArrayList<DesignPlan>();
		int total = 0;
		try {
			total = designPlanService.getTotalFromMyDesignPlan(loginUser, designPlan, mediaType);
			list = designPlanService.getMyDesignPlanList(loginUser, designPlan, total, mediaType);
		} catch (Exception e) {
			logger.error("查询我的方案列表异常exception:" + e.getMessage());
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", designPlan.getMsgId());
		}

		return getResultData(total, list, style, designPlan.getMsgId());
	}

	/**
	 * 根据style封装返回前端的数据类型
	 * 
	 * @param total
	 * @param list
	 * @param style
	 * @param msgId
	 * @return
	 */
	private ResponseEnvelope<?> getResultData(int total, List<DesignPlan> list, String style, String msgId) {
		if ("small".equals(style) && list != null && list.size() > 0) {
			String designPlanJsonList = JsonUtil.getListToJsonData(list);
			List<DesignPlanSmall> smallList = new JsonDataServiceImpl<DesignPlanSmall>()
					.getJsonToBeanList(designPlanJsonList, DesignPlanSmall.class);
			return new ResponseEnvelope<DesignPlanSmall>(total, smallList, msgId);
		}
		logger.warn(JSONObject.fromObject(new ResponseEnvelope<DesignPlan>(total, list, msgId)));
		return new ResponseEnvelope<DesignPlan>(total, list, msgId);
	}

	/** 解析固定格式字符串 */
	private Map<String, String> readFileDesc(String fileDesc) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotBlank(fileDesc)) {
			String[] strs = fileDesc.split(";");
			for (String str : strs) {
				if (str.split(":").length == 2) {
					map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
				}
			}
		}
		return map;
	}

	/**
	 * 用户中心-我的改造方案列表
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/myRemouldPlan")
	public Object myRemouldPlan(@ModelAttribute("designPlan") DesignPlan designPlan, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		designPlan.setUserId(loginUser.getId());
		designPlan.setDesignSourceType(6);
		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");

		List<DesignPlan> list = designPlanService.getPlanList(designPlan);
		try {
			for (DesignPlan desPlan : list) {
				SysUser user = sysUserService.get(desPlan.getUserId());
				if (user != null) {
					desPlan.setPlanUserName(user.getNickName());
					if (desPlan.getDesignSourceType() == 7) {
						DesignTemplet designTemplet = designTempletService.get(desPlan.getDesignId());
						SysUser templetuser = sysUserService.get(designTemplet.getUserId());
						if (templetuser != null) {
							desPlan.setAuthorId(templetuser.getId());
						}
					} else if (desPlan.getDesignSourceType() == 6) {
						DesignPlan desigPlan = designPlanService.get(desPlan.getDesignId());
						SysUser planuser = sysUserService.get(desigPlan.getUserId());
						if (planuser != null) {
							desPlan.setAuthorId(planuser.getId());
						}
					}
				}
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
					}
				}
				if (desPlan.getPicId() != null) {
					ResPic resPic = resPicService.get(desPlan.getPicId());
					if (resPic != null) {
						desPlan.setPicPath(resPic.getPicPath());
					}
				}

				// 评论总数
				DesignPlanComment designPlanComment = new DesignPlanComment();
				designPlanComment.setDesignPlanId(desPlan.getId());
				List<DesignPlanComment> commentList = designPlanCommentService.getList(designPlanComment);
				if (commentList != null && commentList.size() > 0) {
					desPlan.setCommentCount(Integer.valueOf(commentList.size()));
				} else {
					desPlan.setCommentCount(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", designPlan.getMsgId());
		}
		request.setAttribute("list", list);
		return "/online/user/myRemould";
	}

	/**
	 * 设计方案公开
	 * 
	 * @param designPlan
	 *            方案
	 */
	@RequestMapping(value = "/designOpen")
	@ResponseBody
	public Object designPlanOpen(HttpServletRequest request, @ModelAttribute("designPlan") DesignPlan designPlan) {
		LoginUser loginUser = new LoginUser();

		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		DesignPlan plan = designPlanService.get(designPlan.getId());
		/**
		 * DesignPlan plan = DesignPlanCacher.getDesignPlan(designPlan.getId());
		 */
		if (plan != null) {
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(designPlan.getId());
			newDesignPlan.setIsOpen(1);
			newDesignPlan.setGmtCreate(plan.getGmtCreate());
			newDesignPlan.setGmtModified(plan.getGmtModified());
			int i = designPlanService.update(newDesignPlan);
			logger.info("公开的方案id:" + i);
		}
		return new ResponseEnvelope<DesignPlan>(true);
	}

	/**
	 * 设计方案公开或者发布时，选择品牌后，校验
	 * @param brandIds 品牌s
	 * @param designPlanId 方案id
	 * @return 不符合要求的产品数据code
	 */
	@RequestMapping(value = "/designOpenPublishCheck")
	@ResponseBody
	public Object designOpenPublishCheck(String brandIds,String designPlanId,String msgId){
		Set<String> codeSet = new HashSet<String>();
		Integer designId = null;
		//传递参数有值，进行校验
		if(StringUtils.isNotBlank(brandIds)&&StringUtils.isNotBlank(designPlanId)){
			try{
				designId = Integer.parseInt(designPlanId);
			}catch (Exception e){
				logger.error("设计方案id="+designPlanId+"转换Integer出错。");
				return new ResponseEnvelope(false, "参数：设计方案id有误",msgId);
			}
			if(null!=designId){
				codeSet = designPlanService.getProductCodeLogic(brandIds,designId);
			}
		}

		//判断
		if(null!=codeSet&&codeSet.size()>0){
			return new ResponseEnvelope(codeSet, msgId,false);
		}else{
			return new ResponseEnvelope(codeSet, msgId,true);
		}

	}
	/**
	 * 设计方案公开
	 * 
	 * @param designPlan
	 *            方案
	 */
	@RequestMapping(value = "/designClosed")
	@ResponseBody
	public Object designPlanClosed(HttpServletRequest request, @ModelAttribute("designPlan") DesignPlan designPlan) {
		LoginUser loginUser = new LoginUser();

		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}

		DesignPlan plan = designPlanService.get(designPlan.getId());
		/**
		 * DesignPlan plan = DesignPlanCacher.getDesignPlan(designPlan.getId());
		 */

		if (plan != null) {
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(plan.getId());
			newDesignPlan.setIsOpen(0);
			newDesignPlan.setGmtCreate(plan.getGmtCreate());
			newDesignPlan.setGmtModified(plan.getGmtModified());
			int i = designPlanService.update(newDesignPlan);
			logger.info("取消公开的方案id:" + i);
		}
		return new ResponseEnvelope<DesignPlan>(true);
	}

	/**
	 * 删除设计方案
	 * 
	 * @param designPlan
	 *            方案
	 */
	@RequestMapping(value = "/delPlan")
	@ResponseBody
	public Object del(HttpServletRequest request, DesignPlan designPlan) {

		String taskBusinessId = request.getParameter("id");

		if (StringUtils.isEmpty(taskBusinessId) || !StringUtils.isNumeric(taskBusinessId)) {
			return new ResponseEnvelope<>(false, "传参异常", "none");
		}

		if (designPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false, "参数异常!");
		}
		if (StringUtils.isEmpty(designPlan.getMsgId())) {
			return new ResponseEnvelope<DesignPlan>(false, "msgId参数不能为空!", designPlan.getMsgId());
		}
		designPlanService.delete(Integer.valueOf(taskBusinessId), designPlan);

		return new ResponseEnvelope<DesignPlan>(true, "", designPlan.getMsgId());
	}

	/**
	 * 设计页面-我的设计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myDesign")
	public Object myDesign(HttpServletRequest request, String planId) {
		List<DesignPlan> list = new ArrayList<DesignPlan>();
		if (StringUtils.isNotBlank(planId)) {
			DesignPlan designPlan = designPlanService.get(Integer.valueOf(planId));
			DesignPlan designPlanSearch = new DesignPlan();
			// 根据修改时间倒序
			designPlanSearch.setOrder("gmt_modified desc");
			designPlanSearch.setUserId(designPlan.getUserId());
			list = designPlanService.getPlanList(designPlanSearch);
			for (int i = 0; i < list.size(); i++) {
				DesignPlan desPlan = list.get(i);
				if ((desPlan.getId() + "").equals(planId)) {
					list.remove(desPlan);
					i--;
					continue;
				}
				if (desPlan.getSpaceCommonId() != null) {
					SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
						// if
						// (StringUtils.isNotBlank(spaceCommon.getView3dPic()))
						// {
						// ResPic resPic =
						// resPicService.get(Integer.valueOf(spaceCommon.getView3dPic()));
						// desPlan.setView3dPic(resPic.getPicPath());
						// }
					}
				}
				if (desPlan.getPicId() != null) {
					ResPic resPic = resPicService.get(desPlan.getPicId());
					desPlan.setPicPath(resPic.getPicPath());
				}
			}
		}
		request.setAttribute("myDesigns", list);
		return "/online/design/myDesign/myDesignWeb_list";
	}

	/*
	 * 自动存储
	 */
	public Integer copyFile(String resId, String resType, String fileKey, Integer bussniess,
			HttpServletRequest request) {
		logger.info(
				"resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess + ";");
		String resFilePath = "";
		Integer newResId = -1;

		ResFile resFile = new ResFile();
		ResModel resModel = new ResModel();
		ResPic resPic = new ResPic();
		if (!StringUtils.isEmpty(resId)) {
			if ("file".equals(resType)) {
				ResFile file = resFileService.get(new Integer(resId));
				if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
					resFilePath = file.getFilePath();
					resFile = file.copy();
				}
			}
			if ("model".equals(resType)) {
				ResModel model = resModelService.get(new Integer(resId));
				if (model != null && !StringUtils.isEmpty(model.getModelPath())) {
					resFilePath = model.getModelPath();
					resModel = model.copy();
				}

			}
			if ("pic".equals(resType)) {
				ResPic pic = resPicService.get(new Integer(resId));
				if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
					resFilePath = pic.getPicPath();
					resPic = pic.copy();
				}

			}
			if (!StringUtils.isEmpty(resFilePath)) {
				String uploadRoot = "";
				/*if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					uploadRoot = FileUploadUtils.UPLOAD_ROOT;
				} else {
					uploadRoot = FileUploadUtils.FTP_UPLOAD_ROOT;
				}*/
				/*String srcPath = uploadRoot + resFilePath.replace("/", "\\");*/
				String srcPath = Utils.getAbsolutePath(resFilePath, null);
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					srcPath = uploadRoot + resFilePath;
				}

				File resourcesFile = new File(srcPath);
				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
						resFilePath.length());
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
				}

				String newPath = app.getString(fileKey + ".upload.path");
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
				String targetName = newPath + tarName;
				String targetPath = uploadRoot + targetName.replace("/", "\\");
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					targetPath = uploadRoot + targetName;
				}

				File targetFile = new File(targetPath);

				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0,
						resFilePath.lastIndexOf("/") + 1);
				/*String dbFilePath = FileUploadUtils.UPLOAD_ROOT.replace("\\",
						"/") + newPath + tarName;*/
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (resourcesFile.isFile() && resourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(resourcesFile, targetFile);
					}
				} else if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					// 下载到本地
					flag = FtpUploadUtils.downFile(resPath, resourcesName, newPath + tarName);
					if (flag) {
						// 上传ftp服务器
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);
						if (flag) {
							// 删除本地
							FileUploadUtils.deleteFile(newPath + tarName);
						} else {
							return newResId;
						}
					} else {
						return newResId;
					}
				} else {
					// 3 本地和ftp同时上传(默认是本地上传)
					// resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称
					flag = FtpUploadUtils.downFile(resPath, resourcesName, newPath + tarName);// 下载到本地
					if (flag) {
						// tarName:文件名称，dbFilePath:本地文件路径，newPath:ftp服务器存放文件路径
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
						if (!flag) {
							return newResId;
						}
					} else {
						return newResId;
					}
				}

				if (flag) {
					if ("file".equals(resType)) {
						resFile.setSysCode(null);
						resFile.setFilePath(targetName);
						resFile.setFileKey(fileKey);
						resFile.setBusinessId(bussniess);
						sysSave(resFile, request);
						newResId = resFileService.add(resFile);
					}
					if ("model".equals(resType)) {
						resModel.setSysCode(null);
						resModel.setModelPath(targetName);
						resModel.setFileKey(fileKey);
						resModel.setBusinessId(bussniess);
						sysSave(resModel, request);
						newResId = resModelService.add(resModel);
					}
					if ("pic".equals(resType)) {
						resPic.setPicPath(targetName);
						resPic.setFileKey(fileKey);
						resPic.setSysCode(null);
						resPic.setBusinessId(bussniess);
						sysSave(resPic, request);
						newResId = resPicService.add(resPic);
					}
				}
			}
		}

		return newResId;
	}

	/*
	 * 自动存储(加code)
	 */
	public Integer copyFile(String resId, String resType, String fileKey, Integer bussniess, HttpServletRequest request,
			String code) {
		logger.info("resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess
				+ ";code=" + code);
		String resFilePath = "";
		Integer newResId = -1;

		ResFile resFile = new ResFile();
		ResModel resModel = new ResModel();
		ResPic resPic = new ResPic();
		if (!StringUtils.isEmpty(resId)) {
			if ("file".equals(resType)) {
				ResFile file = resFileService.get(new Integer(resId));
				if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
					resFilePath = file.getFilePath();
					resFile = file.copy();
				}
			}
			if ("model".equals(resType)) {
				ResModel model = resModelService.get(new Integer(resId));
				if (model != null && !StringUtils.isEmpty(model.getModelPath())) {
					resFilePath = model.getModelPath();
					resModel = model.copy();
				}

			}
			if ("pic".equals(resType)) {
				ResPic pic = resPicService.get(new Integer(resId));
				if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
					resFilePath = pic.getPicPath();
					resPic = pic.copy();
				}

			}
			if (!StringUtils.isEmpty(resFilePath)) {
				// String uploadRoot = "";
				/*String local_uploadRoot = FileUploadUtils.UPLOAD_ROOT;*/
				String srcPath = Utils.dealWithPath(Utils.getAbsolutePath(resFilePath, null), null);
				/*if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					srcPath = local_uploadRoot + ;
				} else {
					srcPath = local_uploadRoot + resFilePath.replace("/", "\\");
				}*/
				
				
				File srcresourcesFile = new File(srcPath);

				if (!srcresourcesFile.getParentFile().exists()) {
					srcresourcesFile.getParentFile().mkdirs();
				}
				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
						resFilePath.length());
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
				}

				String newPath = app.getString(fileKey + ".upload.path");
				newPath = newPath.replace("[code]", code);
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
				String targetName = newPath + tarName;
				/*String local_targetPath = local_uploadRoot
						+ targetName.replace("/", "\\");
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					local_targetPath = local_uploadRoot + targetName;
				}*/
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);
				
				File local_targetFile = new File(local_targetPath);

				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0,
						resFilePath.lastIndexOf("/") + 1);
				/*String dbFilePath = FileUploadUtils.UPLOAD_ROOT.replace("\\", "/") + newPath + tarName;*/
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
					} else {
						logger.error("srcresourcesFile is not  exists !srcresourcesFile=" + srcresourcesFile);
					}
				} else if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					// 下载到本地
					flag = FtpUploadUtils.downFile(resPath, resourcesName);
					if (FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						if (flag) {
							// 上传ftp服务器
							flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);
							if (flag) {
								// 删除本地
								FileUploadUtils.deleteFile(newPath + tarName);
							} else {
								return newResId;
							}
						} else {
							return newResId;
						}
					} else {
						logger.error("copy file is error");
						return -1;
					}

				} else {
					// 3 本地和ftp同时上传(默认是本地上传)
					// resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (!flag || FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						logger.error("copy file is error");
						return -1;
					}
					if (flag) {
						// tarName:文件名称，dbFilePath:本地文件路径，newPath:ftp服务器存放文件路径
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
						if (!flag) {
							return newResId;
						}
					} else {
						return newResId;
					}
				}

				if (flag) {
					if ("file".equals(resType)) {
						resFile.setSysCode(code);
						resFile.setFilePath(targetName);
						resFile.setFileKey(fileKey);
						resFile.setBusinessId(bussniess);
						resFile.setFileCode(code);
						sysSave(resFile, request);
						newResId = resFileService.add(resFile);
					}
					if ("model".equals(resType)) {
						resModel.setSysCode(code);
						resModel.setModelPath(targetName);
						resModel.setFileKey(fileKey);
						resModel.setBusinessId(bussniess);
						resModel.setModelCode(code);
						sysSave(resModel, request);
						newResId = resModelService.add(resModel);
					}
					if ("pic".equals(resType)) {
						resPic.setPicPath(targetName);
						resPic.setFileKey(fileKey);
						resPic.setSysCode(code);
						resPic.setBusinessId(bussniess);
						resPic.setPicCode(code);
						sysSave(resPic, request);
						newResId = resPicService.add(resPic);
					}
				}
			}
		}

		return newResId;
	}

	/*
	 * 自动存储(加code)
	 */
	public Integer planCopyFile(String resId, String resType, String fileKey, Integer bussniess,
			HttpServletRequest request, String code) {
		logger.info("resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess
				+ ";code=" + code);
		String resFilePath = "";
		Integer newResId = -1;

		ResDesign resDesign = new ResDesign();
		// ResFile resFile = new ResFile();
		ResModel resModel = new ResModel();
		ResPic resPic = new ResPic();
		if (!StringUtils.isEmpty(resId)) {
			if ("file".equals(resType)) {
				ResFile file = resFileService.get(new Integer(resId));
				if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
					resFilePath = file.getFilePath();
					// resFile = file.copy();
					resDesign = file.resDesignCopy();
				}
			}
			if ("model".equals(resType)) {
				ResModel model = resModelService.get(new Integer(resId));
				if (model != null && !StringUtils.isEmpty(model.getModelPath())) {
					resFilePath = model.getModelPath();
					resModel = model.copy();
				}

			}
			if ("pic".equals(resType)) {
				ResPic pic = resPicService.get(new Integer(resId));
				if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
					resFilePath = pic.getPicPath();
					resPic = pic.copy();
				}

			}
			if (!StringUtils.isEmpty(resFilePath)) {
				// String uploadRoot = "";
				/*String local_uploadRoot = FileUploadUtils.UPLOAD_ROOT;*/
				String srcPath = Utils.dealWithPath(Utils.getAbsolutePath(resFilePath, null), null);
				/*if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					srcPath = local_uploadRoot + resFilePath;
				} else {
					srcPath = local_uploadRoot + resFilePath.replace("/", "\\");
				}*/
				

				File srcresourcesFile = new File(srcPath);

				if (!srcresourcesFile.getParentFile().exists()) {
					srcresourcesFile.getParentFile().mkdirs();
				}
				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
						resFilePath.length());
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
				}

				String newPath = app.getString(fileKey + ".upload.path");
				newPath = newPath.replace("[code]", code);
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
				String targetName = newPath + tarName;
				/*String local_targetPath = local_uploadRoot
						+ targetName.replace("/", "\\");
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					local_targetPath = local_uploadRoot + targetName;
				}*/
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);

				File local_targetFile = new File(local_targetPath);

				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0,
						resFilePath.lastIndexOf("/") + 1);
				/*String dbFilePath = FileUploadUtils.UPLOAD_ROOT.replace("\\", "/") + newPath + tarName;*/
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
					} else {
						logger.error("srcresourcesFile is not  exists !srcresourcesFile=" + srcresourcesFile);
					}
				} else if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					// 下载到本地
					flag = FtpUploadUtils.downFile(resPath, resourcesName);
					if (FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						if (flag) {
							// 上传ftp服务器
							flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);
							if (flag) {
								// 删除本地
								FileUploadUtils.deleteFile(newPath + tarName);
							} else {
								return newResId;
							}
						} else {
							return newResId;
						}
					} else {
						logger.error("copy file is error");
						return -1;
					}

				} else {
					// 3 本地和ftp同时上传(默认是本地上传)
					// resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (!flag || FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						logger.error("copy file is error");
						return -1;
					}
					if (flag) {
						// tarName:文件名称，dbFilePath:本地文件路径，newPath:ftp服务器存放文件路径
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
						if (!flag) {
							return newResId;
						}
					} else {
						return newResId;
					}
				}

				if (flag) {
					if ("file".equals(resType)) {
						resDesign.setSysCode(code);
						resDesign.setFilePath(targetName);
						resDesign.setFileKey(fileKey);
						resDesign.setBusinessId(bussniess);
						resDesign.setFileCode(code);
						sysSave(resDesign, request);
						newResId = resDesignService.add(resDesign);
					}
					if ("model".equals(resType)) {
						resModel.setSysCode(code);
						resModel.setModelPath(targetName);
						resModel.setFileKey(fileKey);
						resModel.setBusinessId(bussniess);
						resModel.setModelCode(code);
						sysSave(resModel, request);
						newResId = resModelService.add(resModel);
					}
					if ("pic".equals(resType)) {
						resPic.setPicPath(targetName);
						resPic.setFileKey(fileKey);
						resPic.setSysCode(code);
						resPic.setBusinessId(bussniess);
						resPic.setPicCode(code);
						sysSave(resPic, request);
						newResId = resPicService.add(resPic);
					}
				}
			}
		}

		return newResId;
	}

	/**
	 * DesignPlan自动存储系统字段
	 */
	private void sysSave(ResDesign model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * DesignPlan自动存储系统字段
	 */
	private void sysSave(DesignPlan model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * DesignPlanProduct自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * ResFile自动存储系统字段
	 */
	private void sysSave(ResFile model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * ResModel自动存储系统字段
	 */
	private void sysSave(ResModel model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * ResPic自动存储系统字段
	 */
	private void sysSave(ResPic model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 获取设计收藏状态
	 * 
	 * @param designId
	 * @param srcType
	 * @param request
	 * @return
	 */
	public Integer getDesignCollectState(Integer designId, String srcType, HttpServletRequest request) {
		return 0;
	}

	/**
	 * 获取设计点赞状态
	 * 
	 * @param designId
	 * @param request
	 * @return
	 */
	public Integer getDesignLikeState(Integer designId, HttpServletRequest request) {
		DesignPlanLike designPlanLike = new DesignPlanLike();
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		designPlanLike.setUserId(loginUser.getId());
		designPlanLike.setDesignId(designId);
		List<DesignPlanLike> likeList = designPlanLikeService.getList(designPlanLike);
		if (likeList != null && likeList.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取设计产品数量
	 * 
	 * @param designId
	 * @param request
	 * @return
	 */
	public Integer getDesignProductCount(Integer designId, HttpServletRequest request) {
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setPlanId(designId);
		List<DesignPlanProduct> list = designPlanProductService.getList(designPlanProduct);
		if (list != null && list.size() > 0) {
			return (Integer) list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 增加分享数量
	 * 
	 * @param designId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateShareCount")
	@ResponseBody
	public Object updateShareCount(Integer designId, HttpServletRequest request) {
		DesignPlan designPlan = designPlanService.get(designId);
		int shareT = designPlan.getShareTotal() == null ? 0 : designPlan.getShareTotal();
		int shareSum = shareT + 1;
		DesignPlan dPlan = new DesignPlan();
		dPlan.setId(designId);
		dPlan.setShareTotal(shareSum);
		dPlan.setGmtCreate(designPlan.getGmtCreate());
		int i = designPlanService.update(dPlan);
		logger.info("update:id=" + i);
		return new ResponseEnvelope<DesignPlanProduct>(true);
	}

	/**
	 * 增加分享数量--接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateShareCountWeb")
	@ResponseBody
	public Object updateShareCountWeb(@PathVariable String style, @ModelAttribute("designPlan") DesignPlan designPlan,
			String msgId, HttpServletRequest request) {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlan = (DesignPlan) JsonUtilTwo.getJsonToBean(jsonStr, DesignPlan.class);
		}

		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空!";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (designPlan.getId() == null) {
			msg = "参数id不能为空";
			return new ResponseEnvelope<DesignPlanSmall>(false, msg, msgId);
		}
		DesignPlan dePlan = designPlanService.get(designPlan.getId());
		if (dePlan == null) {
			msg = "该方案设计不存在";
			return new ResponseEnvelope<DesignPlanSmall>(false, msg, msgId);
		}

		DesignPlan designplan = designPlanService.get(designPlan.getId());
		int shareT = designplan.getShareTotal() == null ? 0 : designplan.getShareTotal();
		int shareSum = shareT + 1;
		DesignPlan dPlan = new DesignPlan();
		dPlan.setId(designPlan.getId());
		dPlan.setShareTotal(shareSum);
		int i = designPlanService.update(dPlan);
		/** 添加缓存 */

		logger.info("update:id=" + i);
		return new ResponseEnvelope<DesignPlanProduct>(dPlan, msgId, true);
	}

	/**
	 * 是否被关注 1已关注, 0需关注
	 * 
	 * @param designUserId
	 * @param request
	 * @return
	 */
	public Integer getDesignUserFans(Integer designUserId, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
		sysUserFansSearch.setFansUserId(loginUser.getId());
		sysUserFansSearch.setUserId(designUserId);
		return sysUserFansService.getCount(sysUserFansSearch);
	}

	// 查询用户粉丝列表
	public List<SysUserFans> fansList(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser = sysUserService.get(loginUser.getId());
		}
		SysUserFansSearch sysUserFansSearch = new SysUserFansSearch();
		sysUserFansSearch.setUserId(sysUser.getId());
		List<SysUserFans> fansList = sysUserFansService.getPaginatedList(sysUserFansSearch);
		if (CustomerListUtils.isNotEmpty(fansList)) {
			for (SysUserFans userFans : fansList) {
				SysUser user = sysUserService.get(userFans.getFansUserId());
				userFans.setNickName(user == null ? "" : user.getNickName());
				ResPic resPic = resPicService.get(sysUser.getPicId());
				userFans.setPic(resPic == null ? "" : resPic.getPicPath());
			}
		}
		return fansList;
	}

	// 查询用户关注的列表
	public List<SysUserFans> attentionList(HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = new SysUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			sysUser = sysUserService.get(loginUser.getId());
		}

		SysUserFansSearch userFansSearch = new SysUserFansSearch();
		userFansSearch.setFansUserId(sysUser.getId());
		List<SysUserFans> attentionList = sysUserFansService.getPaginatedList(userFansSearch);
		if (CustomerListUtils.isNotEmpty(attentionList)) {
			for (SysUserFans userFans : attentionList) {
				SysUser user = sysUserService.get(userFans.getUserId());
				userFans.setNickName(user == null ? "" : user.getNickName());
				ResPic resPic = resPicService.get(sysUser.getPicId());
				userFans.setPic(resPic == null ? "" : resPic.getPicPath());
			}
		}
		return attentionList;
	}

	/**
	 * 隐藏产品
	 * 
	 * @param planId
	 *            设计方案ID
	 * @param planProductId
	 *            设计方案关联产品ID
	 * @param response
	 * @return
	 */
	@RequestMapping("/hideProduct")
	public void hideProduct(Integer planId, Integer planProductId, HttpServletResponse response) {
		String flag = "false";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			DesignPlanProduct planProduct = designPlanProductService.get(planProductId);
			DesignPlanProduct newPlanProduct = new DesignPlanProduct();
			newPlanProduct.setId(planProduct.getId());
			newPlanProduct.setIsHide(1);
			designPlanProductService.update(newPlanProduct);
			flag = "true";
			out.write(flag);
		} catch (Exception e) {
			e.printStackTrace();
			out.write(flag);
		}
	}

	/**
	 * 支付渲染任务之前验证
	 * 
	 * @param planId
	 *            设计方案ID
	 * @param params
	 *            3dmax渲染参数
	 * @param request
	 * @param msgId
	 * @param priority
	 *            渲染任务优先级
	 * @return
	 */
	@RequestMapping("/checkRender")
	@ResponseBody
	public Object checkRender(String level, Integer isTurnOn, Integer planId, String params, HttpServletRequest request,
			HttpServletResponse response, String msgId, Integer priority, Integer viewPoint, Integer scene,
			Integer renderingType, String temporaryPic) {
		/* temporaryPic 为高清渲染临时图片 */
		if (planId == null) {
			return new ResponseEnvelope<SysTask>(false, "planId不能为空!", msgId);
		}
		logger.info("点击渲染planId =" + planId + " 验证渲染！");
		try {
			DesignPlan designPlan = designPlanService.get(planId);
			if (designPlan != null) {
				/** 验证样板房、空间和产品是否上架 **/
				String isShelvesValidate = Utils.getPropertyName("render", "app.render.isShelvesValidate", "0");// 验证所属空间和样板房是否上架,默认不验证（0）
				if ("1".equals(isShelvesValidate)) {
					JSONObject jsonObject = shelvesValidate(designPlan);
					logger.error("==================================" + jsonObject.getBoolean("success"));
					if (jsonObject != null && !jsonObject.getBoolean("success")) {
						return new ResponseEnvelope<SysTask>(false, jsonObject.getString("message"), msgId);
					}
				}
			} else {
				logger.info("没有找到planId为" + planId + "的设计方案!");
				return new ResponseEnvelope<SysTask>(false, "没有找到planId为" + planId + "的设计方案!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "系统异常", msgId);
		}
		return new ResponseEnvelope<SysTask>(true, "成功", msgId);
	}

	/**
	 * 保存渲染任务
	 * 
	 * @param planId
	 *            设计方案ID
	 * @param params
	 *            3dmax渲染参数
	 * @param request
	 * @param msgId
	 * @param priority
	 *            渲染任务优先级
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/prepareRender_new")
	@ResponseBody
	public Object prepareRender_new(String level, Integer isTurnOn, Integer planId, String params,
			HttpServletRequest request, HttpServletResponse response, String msgId, Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String orderNo) {

		String paramString = String.format(
				"保存渲染任务App端传来的参数：level:%s, isTurnOn:%d, planId:%s, msgId:%s, priority:%d, viewPoint:%d, scene:%d, renderingType:%d, temporaryPic:%s",
				level, isTurnOn, planId, msgId, priority, viewPoint, scene, renderingType, temporaryPic);
		logger.info(paramString);

		LoginUser user = SystemCommonUtil.getLoginUserFromSession(request);
		if (user==null) {
			return new ResponseEnvelope<SysTask>(false, SystemCommonConstant.PLEASE_LOGIN, msgId);
		}
		int userId = user.getId();

		String taskConfig = "";
		/* 参数验证 */
		/* temporaryPic 为高清渲染临时图片 */
		if (planId == null) {
			return new ResponseEnvelope<SysTask>(false, "planId不能为空!", msgId);
		}
		/* 配置文件信息 产品模型 坐标 等一些渲染需要的 txt 文本 */
		if (StringUtils.isBlank(params)) {
			return new ResponseEnvelope<SysTask>(false, "params不能为空!", msgId);
		} else {
			taskConfig = params;
		}
		/* 优先级，如果等null 那么优先级月底 */
		if (priority == null) {
			priority = 1;
		}
		/* 视角 */
		if (viewPoint == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数viewPoint不能为空", msgId);
		}
		/* 场景 白天黑夜 黄昏？ */
		if (scene == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数scene不能为空", msgId);
		}
		/* 渲染类型 720 照片级？ */
		if (renderingType == null || "".equals(renderingType)) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数renderingType不能为空", msgId);
		}

		/** 保存高清截图信息 */
		Integer picId = null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String filePicName = null;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				filePicName = mf.getOriginalFilename();
				logger.info("文件上传名称：" + filePicName);
				response.setContentType("text/html;charset=utf-8");
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY, "design.designPlan.render.upload.path");
				boolean flag = false;
				map.put("code", "");
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveFile(map);
				if (flag) {
					// ResPic resPic = new ResPic();
					ResRenderPic resPic = new ResRenderPic();
					resPic.setPicSuffix(map.get("FileSuffix") + "");
					resPic.setPicPath(map.get("dbFilePath") + "");
					resPic.setPicFormat(map.get("format") + "");
					resPic.setPicCode(map.get("code") + "");
					resPic.setPicSize(Integer.parseInt(map.get("fileSize") + ""));
					resPic.setPicWeight((Integer) map.get("picWeight"));
					resPic.setFileKey(map.get("fileKey") + "");
					resPic.setPicName(map.get("fileName") + "");
					resPic.setPicHigh((Integer) map.get("picHeight"));
					picId = resRenderPicService.add(resPic);
				}
			}
		}

		try {
			DesignPlan designPlan = designPlanService.get(planId);
			if (designPlan != null) {
				/** 验证样板房、空间和产品是否上架 **/
				String isShelvesValidate = Utils.getPropertyName("render", "app.render.isShelvesValidate", "0");// 验证所属空间和样板房是否上架,默认不验证（0）
				if ("1".equals(isShelvesValidate)) {
					JSONObject jsonObject = shelvesValidate(designPlan);
					if (jsonObject != null && !jsonObject.getBoolean("success")) {
						return new ResponseEnvelope<SysTask>(false, jsonObject.getString("message"), msgId);
					}
				}

				SysTask sysTask = new SysTask();
				JSONObject attribute = new JSONObject();
				attribute.accumulate("viewPoint", viewPoint);
				attribute.accumulate("scene", scene);
				attribute.accumulate("renderingType", renderingType);
				sysTask.setAttribute(attribute.toString());
				sysTask.setBusinessId(planId);
				sysTask.setRenderType(renderingType);
				String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
						+ Utils.generateRandomDigitString(6);
				sysTask.setBusinessCode(rootDirName);
				sysTask.setState(SysTaskStatus.WAIT_EXECUTE);// 待执行

				SysDictionary sysDictionary = null;
				if (RenderTypeCode.COMMON_720_LEVEL == renderingType.intValue()) {// 720度普通
					sysDictionary = sysDictionaryService.findOneByTypeAndValue("panoramaRanderPay", 1);
				} else {
					sysDictionary = sysDictionaryService.findOneByTypeAndValue("renderType", renderingType);
				}
				if (sysDictionary != null) {
					sysTask.setBusinessName(sysDictionary.getName());/* 业务名称 */
				}
				priority = 1000 - renderingType;// 优先级

				LoginUser loginUser = sysSave(sysTask, request);
				// 因为免登录通过session取不到用户
				sysTask.setCreator(designPlan.getCreator());
				sysTask.setModifier(designPlan.getModifier());

				// 识别为该用户是销售,则提高渲染优先级
				if (loginUser != null && loginUser.getUserType() != null
						&& new Integer(5).equals(loginUser.getUserType())) {
					priority -= 10;
				} else {
				}
				sysTask.setPriority(priority);// 任务优先级
				if (picId != null) {
					sysTask.setPicId(picId);
				}
				sysTask.setState(SysTaskStatus.CREATING_TASK);
				logger.error("app端创建渲染任务订单号参数：" + orderNo);
				// ****临时使用****/
				sysTask.setOrderNo(orderNo);
				// ****临时使用end****/
				int i = sysTaskService.add(sysTask);

				if (i > 0) {
					SysTask task = new SysTask();
					task.setBusinessId(planId);
					task.setIsDeleted(0);
					List<SysTask> taskList = sysTaskService.getList(task);
					logger.error("--------------taskListSize:" + taskList.size());
					if (taskList != null && taskList.size() == 1) {
						boolean flag = copyPlan_new(designPlan, sysTask, request);
						if (!flag) {
							return new ResponseEnvelope<SysTask>(false, "渲染时复制设计方案异常！", msgId);
						}
					} else {
						/** 如果该方案变更过则渲染是需要复制一份设计方案并修改之前的变更状态。 */
						if (designPlan.getIsChange() == 1) {
							boolean flag = copyPlan_new(designPlan, sysTask,
									request);/*
												 * 点击渲染之后，新建一份设计方案，保存一份新的 用户当前的场景，防止在渲染中，用户修改了设计方案场景
												 */
							if (flag) {
								DesignPlan newDesignPlan = new DesignPlan();
								newDesignPlan.setId(designPlan.getId());
								newDesignPlan.setIsChange(0);
								designPlanService.update(newDesignPlan);
							} else {
								return new ResponseEnvelope<SysTask>(false, "渲染时复制设计方案异常！", msgId);
							}
						} else {// 如果没有变更则找到上次渲染copy的设计方案ID
							task.setOrders(" id desc");
							List<SysTask> renderPlanList = sysTaskService.getRenderPlanList(task);
							if (renderPlanList != null && renderPlanList.size() > 1) {
								SysTask st = renderPlanList.get(0);
								SysTask newSt = new SysTask();
								newSt.setId(st.getId());
								newSt.setPlanId(renderPlanList.get(1).getPlanId());
								sysTaskService.update(newSt);
							}
						}
					}
				} else {
					logger.info("任务添加失败!");
					return new ResponseEnvelope<SysTask>(false, "渲染任务添加失败!", msgId);
				}
				SysTask task2 = new SysTask();
				task2.setId(i);
				task2.setState(SysTaskStatus.WAIT_EXECUTE);
				task2.setGmtModified(new Date());
				/** 保存新渲染系统需要的任务参数 start */
				// 把最后一个字符“_3dmax”替换成任务id
				taskConfig = taskConfig.trim();
				if (taskConfig.endsWith("_3dmax")) {
					taskConfig = taskConfig.substring(0, taskConfig.lastIndexOf("_3dmax"));
					taskConfig = taskConfig + Integer.toString(i);
				} else {
					logger.error("Id为" + i + "的任务params参数有误,没有以'_3dmax'字符结尾");
				}
				String renderParamUploadPath = designPlanService.saveRenderParams(taskConfig, designPlan);
				task2.setTaskConfig(renderParamUploadPath);
				/** 保存新渲染系统需要的任务参数 end */
				sysTaskService.update(task2);
			} else {
				logger.info("没有找到planId为" + planId + "的设计方案!");
				return new ResponseEnvelope<SysTask>(false, "没有找到planId为" + planId + "的设计方案!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "系统异常", msgId);
		}
		return new ResponseEnvelope<SysTask>(true, "成功", msgId);
	}

	/**
	 * 保存渲染任务
	 * 
	 * @param planId
	 *            设计方案ID
	 * @param params
	 *            3dmax渲染参数
	 * @param request
	 * @param msgId
	 * @param priority
	 *            渲染任务优先级
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/prepareRender")
	@ResponseBody
	public Object prepareRender(String level, Integer isTurnOn, Integer planId, String params,
			HttpServletRequest request, HttpServletResponse response, String msgId, Integer priority, Integer viewPoint,
			Integer scene, Integer renderingType, String temporaryPic, String orderNo) {

		String paramString = String.format(
				"保存渲染任务App端传来的参数：level:%s, isTurnOn:%d, planId:%s, msgId:%s, priority:%d, viewPoint:%d, scene:%d, renderingType:%d, temporaryPic:%s",
				level, isTurnOn, planId, msgId, priority, viewPoint, scene, renderingType, temporaryPic);
		logger.info(paramString);

		String taskConfig = "";
		/* 参数验证 */
		/* temporaryPic 为高清渲染临时图片 */
		if (planId == null) {
			return new ResponseEnvelope<SysTask>(false, "planId不能为空!", msgId);
		}
		/* 配置文件信息 产品模型 坐标 等一些渲染需要的 txt 文本 */
		if (StringUtils.isBlank(params)) {
			return new ResponseEnvelope<SysTask>(false, "params不能为空!", msgId);
		} else {
			taskConfig = params;
		}
		/* 优先级，如果等null 那么优先级月底 */
		if (priority == null) {
			priority = 1;
		}
		/* 视角 */
		if (viewPoint == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数viewPoint不能为空", msgId);
		}
		/* 场景 白天黑夜 黄昏？ */
		if (scene == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数scene不能为空", msgId);
		}
		/* 渲染类型 720 照片级？ */
		if (renderingType == null || "".equals(renderingType)) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数renderingType不能为空", msgId);
		}

		Integer picId = null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String filePicName = null;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				filePicName = mf.getOriginalFilename();
				logger.info("文件上传名称：" + filePicName);
				response.setContentType("text/html;charset=utf-8");
				/* 获取文件列表并将物理文件保存到服务器中 */

				/* filePath设置到model对象中，由model存入数据库中 */
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY, "design.designPlan.render.upload.path");
				boolean flag = false;
				map.put("code", "");
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveFile(map);
				if (flag) {
					ResPic resPic = new ResPic();
					String serverFilePath = map.get(FileUploadUtils.SERVER_FILE_PATH).toString();
					resPic.setPicSuffix(map.get("FileSuffix") + "");
					resPic.setPicPath(map.get("dbFilePath") + "");
					resPic.setPicFormat(map.get("format") + "");
					resPic.setPicCode(map.get("code") + "");
					resPic.setPicSize(Integer.parseInt(map.get("fileSize") + ""));
					resPic.setPicWeight(map.get("picWeight") + "");
					resPic.setFileKey(map.get("fileKey") + "");
					resPic.setPicName(map.get("fileName") + "");
					resPic.setPicHigh(map.get("picHeight") + "");
					// ResRenderPic resRenderPic = new ResRenderPic();
					// String serverFilePath =
					// map.get(FileUploadUtils.SERVER_FILE_PATH).toString();
					// resRenderPic.setPicSuffix(map.get("FileSuffix")+"");
					// resRenderPic.setPicPath(map.get("dbFilePath")+"");
					// resRenderPic.setPicFormat(map.get("format")+"");
					// resRenderPic.setPicCode(map.get("code")+"");
					// resRenderPic.setPicSize(Integer.parseInt(map.get("fileSize")+""));
					// resRenderPic.setPicWeight(Integer.parseInt(map.get("picWeight")+""));
					// resRenderPic.setFileKey(map.get("fileKey")+"");
					// resRenderPic.setPicName( map.get("fileName")+"");
					// resRenderPic.setPicHigh(Integer.parseInt(map.get("picHeight")+""));
					picId = resPicService.add(resPic);
				}
			}
		}

		/** 初始化渲染基本信息 */
		initRender();
		logger.info("点击渲染开始,进行任务创建planId =" + planId);
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			DesignPlan designPlan = designPlanService.get(planId);
			if (designPlan != null) {
				/** 验证样板房、空间和产品是否上架 **/
				String isShelvesValidate = Utils.getPropertyName("render", "app.render.isShelvesValidate", "0");// 验证所属空间和样板房是否上架,默认不验证（0）
				if ("1".equals(isShelvesValidate)) {
					JSONObject jsonObject = shelvesValidate(designPlan);
					logger.info("==================================" + jsonObject.getBoolean("success"));
					if (jsonObject != null && !jsonObject.getBoolean("success")) {
						return new ResponseEnvelope<SysTask>(false, jsonObject.getString("message"), msgId);
					}
				}
				logger.info("渲染服务器：" + renderServers);
				logger.info("任务服务器：" + taskServers);
				if (renderServers != null && taskServers != null) {
					SysTask sysTask = new SysTask();
					JSONObject attribute = new JSONObject();
					attribute.accumulate("viewPoint", viewPoint);
					attribute.accumulate("scene", scene);
					attribute.accumulate("renderingType", renderingType);
					sysTask.setAttribute(attribute.toString());
					sysTask.setBusinessId(planId);
					sysTask.setRenderType(renderingType);
					String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
							+ Utils.generateRandomDigitString(6);
					logger.info("rootDirName=" + rootDirName);
					/** 检查该设计方案是否有高清渲染任务 */
					/**
					 * sysTask.setStateList(Arrays.asList(SysTaskStatus.
					 * WAIT_EXECUTE,SysTaskStatus.EXECUTING)); List <SysTask> taskList =
					 * SysTaskCacher.getList(sysTask); if( taskList != null && taskList.size() > 0
					 * ){ return new ResponseEnvelope<SysTask>(false,"已有高清渲染任务正在执行中！！",msgId); }
					 */

					sysTask.setBusinessCode(rootDirName);
					sysTask.setState(SysTaskStatus.WAIT_EXECUTE);// 待执行
					/*
					 * if("3".equals(renderingType)){ sysTask.setBusinessName("效果图渲染"); }
					 */
					SysDictionary sysDictionary = null;
					if (renderingType.intValue() == 4) {// 720度渲染
						sysDictionary = sysDictionaryService.findOneByTypeAndValue("panoramaRanderPay", 1);
					} else {
						sysDictionary = sysDictionaryService.findOneByTypeAndValue("renderType", renderingType);
					}
					if (sysDictionary != null) {
						sysTask.setBusinessName(sysDictionary.getName());/* 业务名称 */
					}
					priority = 1000 - renderingType;// 优先级
					// sysTask.setBusinessName("效果图渲染");
					// priority=1;
					/** 生成配置文件 */
					String renderRootPath = Utils.getPropertyName("render", "app.render.locRoot", "D:\\\\MaxRender");
					String projectPath = Utils.getPropertyName("render",
							"design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
					String maxScriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
					params = params.replace("_3dmax", rootDirName).replace("renderRootPath",
							renderRootPath + (projectPath + maxScriptPath + rootDirName).replace("/", "\\"));
					/* 处理params字符串 */
					/* 得到空间code */
					/*
					 * String spaceCommonCode="";
					 * if(designPlan.getSpaceCommonId()!=null&&designPlan. getSpaceCommonId()>0){
					 * SpaceCommon spaceCommon=spaceCommonService.get(designPlan.
					 * getSpaceCommonId()); if(spaceCommon!=null)
					 * spaceCommonCode=spaceCommon.getSpaceCode(); }
					 * if(StringUtils.isBlank(spaceCommonCode)) return new
					 * ResponseEnvelope<SysTask>(false,
					 * "渲染失败:planId:"+planId+"对应的空间(id:"+designPlan. getSpaceCommonId()+")数据丢失",
					 * msgId); params=params.replace("[sysTaskCode]", sysTask.getBusinessCode());
					 * params=dealWithParams(params, spaceCommonCode);
					 */
					/* 处理params字符串->end */
					/** 保存配置信息到文件中 */
					// logger.info("params =" + params);
					/** 先把文件保存到本地 */
					Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
					String paramUploadPath = Utils.getPropertyName("render", "params.upload.path",
							"/system/sysTask/param/") + designPlan.getPlanCode() + "/";
					String fileName = rootDirName + ".txt";
					/*String filePath = FileUploadUtils.UPLOAD_ROOT + paramUploadPath + fileName;*/
					String filePath = Utils.getAbsolutePath(paramUploadPath + fileName, null);
					logger.warn("------配置文件data.txt生成路径为:" + filePath);
					boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath, params);
					if (uploadFtpFlag) {
						/** 上传方式为2或者3表示文件在ftp上 */
						if (ftpUploadMethod == 2) {
							uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, paramUploadPath);
							if (uploadFtpFlag) {
								/** 删除本地文件 */
								FileUploadUtils.deleteDir(new File(filePath));
							} else {
								logger.error("生成渲染任务参数文件失败");
							}
						} else if (ftpUploadMethod == 3) {
							uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, paramUploadPath);
							if (!uploadFtpFlag) {
								logger.error("生成渲染任务参数文件失败");
							}
						}
					} else {
						logger.error("生成渲染任务参数文件失败");
					}

					sysTask.setParams(paramUploadPath + fileName);
					LoginUser loginUser = sysSave(sysTask, request);
					// 因为免登录通过session取不到用户
					sysTask.setCreator(designPlan.getCreator());
					sysTask.setModifier(designPlan.getModifier());

					/** 获取被分配的任务和渲染服务器 */
					String renderServerIp = renderServers[renderAllocationIndex];
					String taskServerIp = taskServers[taskAllocationIndex];
					RenderConfig renderConfig = renderServersMap
							.get(renderServerIp + "_" + renderStorageType[renderAllocationIndex]);
					String renderWay = renderConfig.getStorageType();
					sysTask.setRenderServer(renderServerIp);// 渲染服务器IP
					sysTask.setTaskServer(taskServerIp);// 任务服务器IP
					// 识别为该用户是销售,则提高渲染优先级
					if (loginUser != null && loginUser.getUserType() != null
							&& new Integer(5).equals(loginUser.getUserType())) {
						priority -= 10;
					} else {
					}
					sysTask.setPriority(priority);// 任务优先级
					if (picId != null) {
						sysTask.setPicId(picId);
					}
					// 【云渲染】
					if ("ruiyun".equals(renderWay)) {
						sysTask.setRenderWay(renderWay);
						sysTask.setPicDownloadState(0);
					} else {
						sysTask.setRenderWay(renderWay);
						sysTask.setPicDownloadState(1);
					}
					sysTask.setState(SysTaskStatus.CREATING_TASK);
					logger.info("app端创建渲染任务订单号参数：" + orderNo);
					// ****临时使用****/
					sysTask.setOrderNo(orderNo);
					// ****临时使用end****/
					int i = sysTaskService.add(sysTask);

					/**
					 * logger.info("任务添加完成!rootDirName="+rootDirName+
					 * ";sysTaskId="+i+";designPlanId=" + planId);
					 */
					// //
					// //System.out.println("IsChange=0渲染任务创建成功!rootDirName="+rootDirName+";sysTaskId="+i+";designPlanId="
					// // + planId+";"+"\n"+"data="+params+";\n"+";
					// // taskServerIp="+taskServerIp
					// // +";renderServerIp="+renderServerIp);
					if (i > 0) {
						renderAllocationIndex++;
						if (renderServers.length == renderAllocationIndex) {
							renderAllocationIndex = 0;
						}
						taskAllocationIndex++;
						if (taskServers.length == taskAllocationIndex) {
							taskAllocationIndex = 0;
						}
						SysTask task = new SysTask();
						task.setBusinessId(planId);
						task.setIsDeleted(0);
						List<SysTask> taskList = sysTaskService.getList(task);
						logger.error("--------------taskListSize:" + taskList.size());
						if (taskList != null && taskList.size() == 1) {
							// boolean flag =
							// copyPlan(designPlan,sysTask,request);
							boolean flag = copyPlan_new(designPlan, sysTask, request);
							if (!flag) {
								return new ResponseEnvelope<SysTask>(false, "渲染时复制设计方案异常！", msgId);
							}
						} else {
							/** 如果该方案变更过则渲染是需要复制一份设计方案并修改之前的变更状态。 */
							if (designPlan.getIsChange() == 1) {
								boolean flag = copyPlan_new(designPlan, sysTask,
										request);/*
													 * 点击渲染之后，新建一份设计方案，保存一份新的 用户当前的场景，防止在渲染中， 用户修改了设计方案场景
													 */
								if (flag) {
									DesignPlan newDesignPlan = new DesignPlan();
									newDesignPlan.setId(designPlan.getId());
									newDesignPlan.setIsChange(0);
									designPlanService.update(newDesignPlan);
								} else {
									return new ResponseEnvelope<SysTask>(false, "渲染时复制设计方案异常！", msgId);
								}
							} else {// 如果没有变更则找到上次渲染copy的设计方案ID
								task.setOrders(" id desc");
								List<SysTask> renderPlanList = sysTaskService.getRenderPlanList(task);
								if (renderPlanList != null && renderPlanList.size() > 1) {
									SysTask st = renderPlanList.get(0);
									SysTask newSt = new SysTask();
									newSt.setId(st.getId());
									newSt.setPlanId(renderPlanList.get(1).getPlanId());
									sysTaskService.update(newSt);
								}
							}
						}
						if ("ruiyun".equals(renderWay)) {
							// JSONObject jsonObject =
							// designPlanService.assembleConfig(sysTask,renderConfig);
							// if(jsonObject != null &&
							// !jsonObject.getBoolean("success")){
							// return new
							// ResponseEnvelope<JSONObject>(jsonObject,msgId,false);
							// }
							// 异步处理渲染任务组合文件
							AssembleRenderTaskFileParameter parameter = new AssembleRenderTaskFileParameter(sysTask,
									renderConfig);
							AssembleRenderTaskFile rendertask = new AssembleRenderTaskFile(parameter);
							FutureTask<Result> futureTask = new FutureTask<Result>(rendertask);
							TaskExecutor.getInstance().addTask(futureTask);
							logger.info("任务ID：" + sysTask.getId() + "  异步处理完毕！");
						}
					} else {
						logger.info("任务添加失败!");
						return new ResponseEnvelope<SysTask>(false, "渲染任务添加失败!", msgId);
					}
					SysTask task2 = new SysTask();
					task2.setId(i);
					task2.setState(SysTaskStatus.WAIT_EXECUTE);
					task2.setGmtModified(new Date());
					/** 保存新渲染系统需要的任务参数 start */
					// 把最后一个字符“_3dmax”替换成任务id
					taskConfig = taskConfig.trim();
					if (taskConfig.endsWith("_3dmax")) {
						taskConfig = taskConfig.substring(0, taskConfig.lastIndexOf("_3dmax"));
						taskConfig = taskConfig + Integer.toString(i);
					} else {
						logger.error("Id为" + i + "的任务params参数有误,没有以'_3dmax'字符结尾");
					}
					String renderParamUploadPath = designPlanService.saveRenderParams(taskConfig, designPlan);
					task2.setTaskConfig(renderParamUploadPath);
					/** 保存新渲染系统需要的任务参数 end */
					sysTaskService.update(task2);
				} else {
					logger.info("渲染服务器或者任务服务器没有配置！");
					logger.info("任务服务器：" + TASK_SERVER_HOSTS);
					logger.info("渲染服务器：" + RENDER_SERVER_HOSTS);
					return new ResponseEnvelope<SysTask>(false, "渲染服务器或者任务服务器没有配置！", msgId);
				}
			} else {
				logger.info("没有找到planId为" + planId + "的设计方案!");
				return new ResponseEnvelope<SysTask>(false, "没有找到planId为" + planId + "的设计方案!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "系统异常", msgId);
		}
		return new ResponseEnvelope<SysTask>(true, "成功", msgId);
	}

	private String dealWithParams(String params, String spaceCommonCode) {
		/*
		 * 示例 [spaceCommon]\D03_0263_chuangh\D03_0263_chuangh.max
		 * [baseProduct]\baimo_D03_0263_dim_0001\baimo_D03_0263_dim_0001.max
		 * ->F:\chengdu\MaxRender\resources\baseProduct\wu_D04_0004_zaox_0002\
		 * wu_D04_0004_zaox_0002.max
		 */
		String renderPathSuffix = Utils.getPropertyName("render", "render.renderResourcesPath",
				"D:\\MaxRender\\MaxRender\\resources");
		/* 处理产品模型路径 */
		params = params.replace("[baseProduct]", renderPathSuffix + "\\baseProduct");
		/* 处理空间模型路径 */
		params = params.replace("[spaceCommon]", renderPathSuffix + "\\spaceCommon\\" + spaceCommonCode);
		return params;
	}

	/**
	 * 生成渲染任务 缩虐图方法
	 * 
	 * @param level
	 * @param isTurnOn
	 * @param planId
	 * @param params
	 * @param priority
	 * @param viewPoint
	 * @param scene
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/HdRenderingNeedPictures")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer HdRenderingNeedPictures(String level, Integer isTurnOn, String planId, String params,
			Integer priority, Integer viewPoint, Integer scene, String renderingType, HttpServletRequest request,
			HttpServletResponse response) {

		Integer smallPicId = 0;
		if (request instanceof MultipartHttpServletRequest) {

			/**
			 * 获取文件列表并将物理文件保存到服务器中, 将HttpServletRequest对象转换为MultipartHttpServletRequest对象
			 */
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String fileName = null;
			String code = "code";
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			List<Map> list = new ArrayList<Map>();

			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				fileName = mf.getOriginalFilename();
				response.setContentType("text/html;charset=utf-8");

				/** 获取文件列表并将物理文件保存到服务器中 */
				/** filePath设置到model对象中，由model存入数据库中 */
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY, "design.designPlan.render.upload.path");
				boolean flag = false;
				map.put("code", code);
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveFile(map);
				String serverFilePath = map.get(FileUploadUtils.SERVER_FILE_PATH).toString();
				logger.info("追加渲染图水印,serverFilePath=" + serverFilePath);

				try {
					ImageUtils.watermarking2(serverFilePath, scene, 2, isTurnOn);
					/** 水印图方案一->end */
				} catch (IOException e1) {
					logger.error("水印图生成失败");
					e1.printStackTrace();
				}

				/** 生成图片,支持多张,需写到server */
				if (flag) {
					/**
					 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值， 则上传到web服务器。
					 **/
					Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
					String finalFlieName = (String) map.get("finalFlieName");
					String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
					String ftpFilePath = (String) map.get("filePath");
					/** 上传方式为2或者3都需要上传到ftp */
					boolean uploadFtpFlag = false;
					if (ftpUploadMethod == 2 || ftpUploadMethod == 3) {
						uploadFtpFlag = FtpUploadUtils.uploadFile(finalFlieName, serverFilePath, ftpFilePath);
					}

					/** 生成缩略图 */
					try {
						String smallFileName = Utils.generateRandomDigitString(6) + "_"
								+ Utils.getCurrentDateTime(Utils.DATETIMESSS)
								+ fileName.substring(fileName.indexOf("."));
						/*String targetSmallFilePath = Utils.getValue(
								"app.upload.root", "D:\\app").trim()
								+ app.getString(
										(String) map
												.get(FileUploadUtils.UPLOADPATHTKEY))
										.replace("[code]", code)
								+ "/small/"
								+ smallFileName;*/
						String relativePath = app.getString((String) map.get(FileUploadUtils.UPLOADPATHTKEY)).replace("[code]", code) + "/small/" + smallFileName;
						String targetSmallFilePath = Utils.getAbsolutePath(relativePath, Utils.getAbsolutePathType.encrypt);
						targetSmallFilePath = Utils.replaceDate(targetSmallFilePath, null);
						if ("windows".equals(Utils.getValue(
								"app.system.format", "linux").trim())) {
							targetSmallFilePath = targetSmallFilePath.replace(
									"/", "\\");
						}
						ResizeImage.createThumbnail(serverFilePath, targetSmallFilePath, 280, 215);
						File targetSmallFile = new File(targetSmallFilePath);
						Map smallFileMap = FileUploadUtils.getMap(targetSmallFile,
								"design.designPlan.render.upload.path", false);
						map.put("viewPoint", viewPoint);
						map.put("scene", scene);

						smallFileMap.put("original", map);
						/** 传递渲染图的视角和渲染图场景 */
						smallFileMap.put("viewPoint", viewPoint);
						smallFileMap.put("scene", scene);
						list.add(smallFileMap);
					} catch (Exception e) {
						e.printStackTrace();
					}
					/** 如果上传方式为2，则删除掉临时上传的文件 */
					/**
					 * if( ftpUploadMethod == 2 ){ if( uploadFtpFlag ){ /**删除本地
					 */
					/**
					 * FileUploadUtils.deleteFile(dbFilePath); } }
					 */
				}
			}
			/** 将图片信息记录到数据库中 */
			smallPicId = resPicService.saveFiles(planId, list, level, renderingType);
		}
		return smallPicId;
	}

	public boolean copyPlan_new(DesignPlan plan, SysTask sysTask, HttpServletRequest request) {
		Integer designPlanId = plan.getId();
		String creator = plan.getCreator();
		String modifier = plan.getModifier();
		DesignPlan desPlan = plan;
		desPlan.setId(null);
		if (plan.getPlanCode() != null) {
			desPlan.setPlanCode(plan.getPlanCode().substring(0, plan.getPlanCode().length() - 6)
					+ Utils.generateRandomDigitString(6));
		}
		desPlan.setDesignSourceType(9);// 来源渲染copy
		desPlan.setDesignId(designPlanId);
		desPlan.setIsChange(0);
		desPlan.setIsOpen(0);
		desPlan.setSysCode(
				plan.getSysCode().substring(0, plan.getSysCode().length() - 6) + Utils.generateRandomDigitString(6));
		sysSave(desPlan, request);
		desPlan.setCreator(creator);
		desPlan.setModifier(modifier);
		int id = designPlanService.add(desPlan);
		if (id > 0) {
			SysTask sys_task = sysTaskService.get(sysTask.getId());
			if (sys_task != null) {
				SysTask newSysTask = new SysTask();
				newSysTask.setId(sys_task.getId());
				newSysTask.setPlanId(id);
				sysTaskService.update(newSysTask);
			}
		}
		if (designPlanId != null) {
			// 设计方案的产品列表代入
			DesignPlanProduct desPlanProduct = new DesignPlanProduct();
			desPlanProduct.setIsDeleted(0);
			desPlanProduct.setPlanId(designPlanId);
			List<DesignPlanProduct> planProductList = designPlanProductService.getList(desPlanProduct);
			for (DesignPlanProduct dpProduct : planProductList) {
				DesignPlanProduct planProduct = new DesignPlanProduct();
				planProduct = dpProduct;
				planProduct.setId(null);
				planProduct.setPlanId(id);
				sysSave(planProduct, request);
				designPlanProductService.add(planProduct);
			}
		}

		return true;
	}

	public boolean copyPlan(DesignPlan plan, SysTask sysTask, HttpServletRequest request) {
		DesignPlan desPlan = new DesignPlan();
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null) {
			return false;
		}
		if (plan.getPlanCode() != null) {
			desPlan.setPlanCode(plan.getPlanCode().substring(0, plan.getPlanCode().length() - 6)
					+ Utils.generateRandomDigitString(6));
		}
		// 媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);

		desPlan.setMediaType(Utils.getIntValue(mediaType));
		desPlan.setPlanName(plan.getPlanName());
		desPlan.setUserId(loginUser.getId());
		desPlan.setDesignSourceType(6);
		desPlan.setDesignId(plan.getId());
		desPlan.setIsChange(0);
		desPlan.setIsOpen(0);

		// 设计方案的模型应该从数据库中拷贝一份新的,否则删掉方案后,原设计方案中的模型将会被删除
		Integer picId = null;
		if (plan.getPicId() != null) {
			picId = this.copyFile(plan.getPicId().toString(), "pic", "design.designPlan.pic", null, request,
					desPlan.getPlanCode());
			desPlan.setPicId(plan.getPicId());
		} else {
			desPlan.setPicId(-1);
		}
		Integer modelId = null;

		Integer model3dId = null;
		if (plan.getModel3dId() != null) {
			model3dId = this.copyFile(plan.getModel3dId().toString(), "model", "design.designPlan.3dmodel", null,
					request, desPlan.getPlanCode());
			desPlan.setModel3dId(model3dId);// 设计方案3d模型文件
		} else {
			desPlan.setModel3dId(-1);
		}

		// 拷贝公共资源
		desPlan.setSysCode(
				plan.getSysCode().substring(0, plan.getSysCode().length() - 6) + Utils.generateRandomDigitString(6));
		Integer modelU3dId = null;
		if (plan.getModelU3dId() != null && plan.getModelU3dId() > 0) {
			modelU3dId = this.copyFile(plan.getModelU3dId().toString(), "model", "design.designPlan.u3dmodel", null,
					request, desPlan.getPlanCode());
			// desPlan.setWebModelU3dId(modelU3dId);//设计方案u3d模型文件
			desPlan.setModelU3dId(modelU3dId);
		} else {
			desPlan.setModelU3dId(-1);
		}

		desPlan.setSpaceCommonId(plan.getSpaceCommonId());// 适用的空间ID
		desPlan.setDesignStyleId(plan.getDesignStyleId());// 设计风格,后续在更新产品时,需根据产品类型判断
		Integer resFileId = null;
		if (plan.getConfigFileId() != null) {
			resFileId = this.planCopyFile(plan.getConfigFileId().toString(), "file", "design.designPlan.u3dconfig",
					null, request, desPlan.getPlanCode());
			desPlan.setConfigFileId(resFileId);// //设计方案配置文件
		} else {
			desPlan.setConfigFileId(-1);// //设计方案配置文件
		}

		// 创建新的设计方案信息
		try {
			sysSave(desPlan, request);
			int id = designPlanService.add(desPlan);
			logger.info("add:id=" + id);
			desPlan.setId(id);
			if (id > 0) {
				sysTask = sysTaskService.get(sysTask.getId());
				if (sysTask != null) {
					SysTask newSysTask = new SysTask();
					newSysTask.setId(sysTask.getId());
					newSysTask.setPlanId(id);
					sysTaskService.update(newSysTask);
				}
			}
			// 回写资源信息
			if (picId != null) {
				ResPic rp = new ResPic();
				rp.setId(picId);
				rp.setBusinessId(id);
				rp.setPicCode(desPlan.getPlanCode());
				resPicService.update(rp);
			}
			// 回写资源信息
			if (resFileId != null) {
				ResFile rf = new ResFile();
				rf.setId(resFileId);
				rf.setBusinessId(id);
				rf.setFileCode(desPlan.getPlanCode());
				resFileService.update(rf);
			}
			// 回写资源信息
			if (modelId != null) {
				ResModel rm3 = new ResModel();
				rm3.setId(modelId);
				rm3.setBusinessId(id);
				rm3.setModelCode(desPlan.getPlanCode());
				resModelService.update(rm3);
			}

			// 回写资源信息
			if (modelU3dId != null) {
				ResModel rm_u3d = new ResModel();
				rm_u3d.setId(modelU3dId);
				rm_u3d.setBusinessId(id);
				rm_u3d.setModelCode(desPlan.getPlanCode());
				resModelService.update(rm_u3d);
			}
			// 设计方案的产品列表代入
			DesignPlanProduct desPlanProduct = new DesignPlanProduct();
			desPlanProduct.setIsDeleted(0);
			desPlanProduct.setPlanId(plan.getId());
			List<DesignPlanProduct> planProductList = designPlanProductService.getList(desPlanProduct);
			for (DesignPlanProduct dpProduct : planProductList) {
				DesignPlanProduct planProduct = new DesignPlanProduct();
				planProduct = dpProduct;
				planProduct.setId(null);
				planProduct.setPlanId(id);
				/*
				 * planProduct.setProductId(dpProduct.getProductId());
				 * planProduct.setProductSequence(dpProduct.getProductSequence() );
				 */
				planProduct.setIsHide(0);
				sysSave(planProduct, request);
				designPlanProductService.add(planProduct);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 样板展示列表接口
	 */
	@RequestMapping(value = "/getModelShowList")
	@ResponseBody
	public Object getModelShowList(@PathVariable String style, @ModelAttribute("designPlan") DesignPlan designPlan,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlan = (DesignPlan) designJsonUtil.getJsonToBean(jsonStr, DesignPlan.class);
		}

		String msg = "";
		if (StringUtils.isBlank(designPlan.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, designPlan.getMsgId());
		}

		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");
		List<DesignPlan> list = new ArrayList<>();
		int total = 0;
		try {
			// 媒介类型.如果没有值，则表示为web前端（2）
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			// 验证媒介类型是否一致
			designPlan.setMediaType(Integer.valueOf(mediaType));
			if (Utils.enableRedisCache()) {
				total = DesignCacher.getPlanCount(designPlan);
			} else {
				total = designPlanService.getPlanCount(designPlan);

			}

			if (total > 0) {
				if (Utils.enableRedisCache()) {
					list = DesignCacher.getPlanList(designPlan);
				} else {
					list = designPlanService.getDesignsList(designPlan);
				}

				for (DesignPlan desPlan : list) {
					// 获取用户名称
					SysUser user = sysUserService.get(desPlan.getUserId());
					if (user != null) {
						desPlan.setPlanUserName(user.getUserName());
					}
					// 通过空间ID获取空间编码、名称、内容。
					if (desPlan.getSpaceCommonId() != null) {
						SpaceCommon spaceCommon = null;
						if (Utils.enableRedisCache()) {
							spaceCommon = SpaceCommonCacher.get(desPlan.getSpaceCommonId());
						} else {
							spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
						}

						if (spaceCommon != null) {
							desPlan.setSpaceCode(spaceCommon.getSpaceCode());
							desPlan.setSpaceName(spaceCommon.getSpaceName());
							desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
						}
					}
					// 获取方案图片路径
					if (desPlan.getPicId() != null) {
						ResPic resPic = resPicService.get(desPlan.getPicId());
						desPlan.setPicPath(resPic == null ? "" : resPic.getPicPath());
					}
					// 判断该方案是否被收藏，如果没有收藏返回0，已收藏返回1。
					LoginUser loginUser = new LoginUser();
					if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
						loginUser.setId(-1);
						loginUser.setLoginName("nologin");
					} else {
						loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
					}
					DesignPlanCommentSearch commentSearch = new DesignPlanCommentSearch();
					commentSearch.setDesignPlanId(desPlan.getId());
					int count = 0;
					if (Utils.enableRedisCache()) {
						count = DesignPlanCommentCacher.getTotalWithParameter(commentSearch);
					} else {
						count = designPlanCommentService.getCount(commentSearch);
					}
					desPlan.setCommentCount(count);
					// 点赞数量
					// int likeCount = getDesignLikeConut(desPlan.getId(),
					// request);
					DesignPlanLikeSearch search = new DesignPlanLikeSearch();
					search.setDesignId(desPlan.getId());
					int likeCount = 0;
					if (Utils.enableRedisCache()) {
						likeCount = DesignPlanLikeCacher.getTotalWithParameter(search);
					} else {
						likeCount = designPlanLikeService.getCount(search);
					}

					// 收藏数量
					// int fansConut = getSysUserFansConut(request);

					desPlan.setLikeCount(likeCount);
					// desPlan.setFansConut(fansConut);
					// 渲染图
					if (desPlan.getPicId() != null) {
						ResPic resPic = null;
						if (Utils.enableRedisCache()) {
							resPic = ResourceCacher.getPic(desPlan.getPicId());
						} else {
							resPic = resPicService.get(desPlan.getPicId());
						}

						if (resPic != null) {
							desPlan.setPicPath(resPic.getPicPath());
						}
					} else {
						desPlan.setPicPath("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", designPlan.getMsgId());
		}
		if ("small".equals(style) && list != null && list.size() > 0) {
			String designPlanJsonList = JsonUtil.getListToJsonData(list);
			List<DesignPlanSmall> smallList = new JsonDataServiceImpl<DesignPlanSmall>()
					.getJsonToBeanList(designPlanJsonList, DesignPlanSmall.class);
			return new ResponseEnvelope<DesignPlanSmall>(total, smallList, designPlan.getMsgId());
		}
		return new ResponseEnvelope<DesignPlan>(total, list, designPlan.getMsgId());
	}

	/**
	 * 获取设计方案渲染图
	 * 
	 * @param planId
	 * @param request
	 * @return object
	 */
	@RequestMapping("/getPlanRenderPic")
	@ResponseBody
	public boolean getPlanRenderPic(String level, String planId, String renderingType, List<String> files,
			HttpServletRequest request, HttpServletResponse response) {
		// planId不能为空
		if (StringUtils.isNotBlank(planId)) {
			return false;
		}
		DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
		String code = "code";
		if (designPlan != null) {
			code = designPlan.getPlanCode();
		}
		List<Map> list = new ArrayList<Map>();
		if (files != null && files.size() > 0) {
			for (String filePath : files) {
				File file = new File(filePath);
				if (!file.exists()) {
					// 文件不存在
					continue;
				}
				String fileName = file.getName();
				// 复制渲染图
				String targetFilePath = "";
				String targetSmallFilePath = "";
				String smallFileName = Utils.generateRandomDigitString(6) + "_"
						+ Utils.getCurrentDateTime(Utils.DATETIMESSS) + fileName.substring(fileName.indexOf("."));
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					// targetFilePath = FileUploadUtils.UPLOAD_ROOT +
					// app.getString("design.designPlan.render.upload.path").replace("[code]",
					// code);
					String relativePath = Utils.getPropertyName("config/res",
									"design.designPlan.render.upload.path",
									"/design/designPlan/[code]/render/")
									.replace("[code]", code);
					targetFilePath = Utils.replaceDate(targetFilePath, null);
					targetFilePath = Utils.getAbsolutePath(relativePath, null);
					targetSmallFilePath = targetFilePath + "/small" + "/"
							+ smallFileName;
				} else {
					// targetFilePath = (FileUploadUtils.UPLOAD_ROOT+
					// app.getString("design.designPlan.render.upload.path")).replace("/",
					// "\\").replace("[code]", code);
					/*targetFilePath = (FileUploadUtils.UPLOAD_ROOT + Utils
							.getPropertyName("config/res",
									"design.designPlan.render.upload.path",
									"/design/designPlan/[code]/render/"))
							.replace("/", "\\").replace("[code]", code);*/
					String relativePath = (Utils
							.getPropertyName("config/res",
									"design.designPlan.render.upload.path",
									"/design/designPlan/[code]/render/"))
							.replace("/", "\\").replace("[code]", code);
					targetFilePath = Utils.replaceDate(targetFilePath);
					targetFilePath = Utils.getAbsolutePath(relativePath, null);
					targetSmallFilePath = targetFilePath + "\\small" + "\\" + smallFileName;
				}
				File targetFile = new File(targetFilePath);
				FileUploadUtils.fileCopy(file, targetFile);
				// 生成缩略图
				try {
					ResizeImage.createThumbnail(filePath, targetSmallFilePath, 280, 215);
					File targetSmallFile = new File(targetSmallFilePath);
					Map smallFileMap = FileUploadUtils.getMap(targetSmallFile, "design.designPlan.render.upload.path",
							true);
					Map map = FileUploadUtils.getMap(file, "design.designPlan.render.upload.path", true);
					smallFileMap.put("original", map);
					list.add(smallFileMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 将图片信息记录到数据库中
			resPicService.saveFiles(planId, list, level, renderingType);

			// 发邮件通知渲染已经完成
			// DesignPlan designPlan =
			// designPlanService.get(Integer.valueOf(planId));
			// Integer userId = designPlan.getUserId();
		}
		return true;
	}

	/**
	 * 自动存储系统字段
	 * 
	 * @return
	 */
	private LoginUser sysSave(SysTask model, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (model != null) {
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
		return loginUser;
	}

	/**
	 * 设计详情方案推荐（同来源样板房）方案
	 * 
	 * @param designTempletId
	 *            (样板房ID)
	 */
	@RequestMapping(value = "/recommendPlanList")
	public Object recommendPlanList(@PathVariable String style, Integer designTempletId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (designTempletId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "数据异常！");
		}
		DesignPlan designPlan = new DesignPlan();
		designPlan.setDesignId(designTempletId);
		designPlan.setDesignSourceType(7);// 来源样板房
		designPlan.setIsOpen(1);// 公开是1
		// 根据修改时间倒序
		designPlan.setOrder("gmt_modified desc");
		List<DesignPlan> list = new ArrayList<>();
		try {
			// 媒介类型.如果没有值，则表示为web前端（2）
			// String mediaType = (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || request
			// .getSession().getAttribute("mediaType") == null) ? "2"
			// : (String) request.getSession().getAttribute("mediaType");
			// 验证媒介类型是否一致
			// designPlan.setMediaType(Integer.valueOf(mediaType));
			list = designPlanService.getPlanList(designPlan);
			if (CustomerListUtils.isNotEmpty(list)) {
				for (DesignPlan desPlan : list) {
					// 通过空间ID获取空间编码、名称、内容。
					if (desPlan.getSpaceCommonId() != null) {
						SpaceCommon spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
						if (spaceCommon != null) {
							desPlan.setSpaceCode(spaceCommon.getSpaceCode());
							desPlan.setSpaceName(spaceCommon.getSpaceName());
							desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
						}
					}
					ResPic resPic = new ResPic();
					if (desPlan.getPicId() != null) {
						resPic = resPicService.get(desPlan.getPicId());
					}
					// 渲染图

					desPlan.setPicPath(resPic == null ? "" : resPic.getPicPath());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!");
		}
		request.setAttribute("list", list);
		return "/online/newDesign/recommend_plan";
	}

	/**
	 * 测试高清渲染之前的3d模型文件解压（仅供测试使用）
	 * 
	 * @param planId
	 * @param rootDirName
	 * @return
	 */
	@RequestMapping(value = "/testUnzip")
	@ResponseBody
	public Object testUnzip(Integer planId, String rootDirName) {
		JSONObject jsonObject = new JSONObject();
		if (planId == null) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message", "设计方案ID不能为空！");
			return jsonObject;
		}
		if (StringUtils.isBlank(rootDirName)) {
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("message", "生成文件目录不能为空！");
			return jsonObject;
		}
		// UnzipRenderFileParameter parameter=new
		// UnzipRenderFileParameter(planId, rootDirName, null, "test");
		// UnzipRenderFileTask task = new UnzipRenderFileTask(parameter);
		// FutureTask<Result> futureTask = new FutureTask<Result>(task);
		// TaskExecutor.getInstance().addTask(futureTask);
		jsonObject = (JSONObject) designPlanService.startRender(planId, rootDirName, null, "test");
		// jsonObject.accumulate("success",false);
		// jsonObject.accumulate("message", "解压文件失败!");
		return jsonObject;
	}

	/**
	 * 验证用户是否有测试渲染权限
	 * 
	 * @param request
	 * @param msgId
	 * @return
	 */
	@RequestMapping(value = "/hasTestRenderAuthorized")
	@ResponseBody
	public Object hasTestRenderAuthorized(HttpServletRequest request, String msgId) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) != null) {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			SysDictionary sysDictionary = sysDictionaryService.findOneByValueKey(loginUser.getLoginName());
			/**
			 * SysDictionary sysDictionary =
			 * DesignPlanCacher.findOneByValueKey(loginUser.getLoginName(), request);
			 */
			if (sysDictionary != null && "testRender".equals(sysDictionary.getType()) && sysDictionary.getId() != null
					&& StringUtils.isNotBlank(sysDictionary.getAtt1())
					&& StringUtils.isNotBlank(sysDictionary.getAtt2())) {
				return new ResponseEnvelope<DesignPlan>(true, null, msgId);
			}
		}
		return new ResponseEnvelope<DesignPlan>(false, null, msgId);
	}

	/**
	 * 测试高清渲染之前的3d模型文件解压（仅供测试使用）
	 * 
	 * @param planId
	 * @param rootDirName
	 * @return
	 */
	@RequestMapping(value = "/testRender")
	@ResponseBody
	public Object testRender(Integer planId, String rootDirName, String data, String msgId,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "设计方案ID不能为空！", msgId);
		}
		if (StringUtils.isBlank(data)) {
			return new ResponseEnvelope<DesignPlan>(false, "Data配置文本不能为空！", msgId);
		}
		DesignPlan designPlan = designPlanService.get(planId);
		if (designPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false, "没有找到planId为" + planId + "的设计方案！", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) != null) {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			SysDictionary sysDictionary = sysDictionaryService.findOneByValueKey(loginUser.getLoginName());
			if (sysDictionary != null && "testRender".equals(sysDictionary.getType()) && sysDictionary.getId() != null
					&& StringUtils.isNotBlank(sysDictionary.getAtt1())
					&& StringUtils.isNotBlank(sysDictionary.getAtt2())) {
				String fileDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
						+ Utils.generateRandomDigitString(6);
				RenderConfig renderConfig = new RenderConfig();
				renderConfig.setStorageType("local");
				jsonObject = (JSONObject) designPlanService.startRender(planId, sysDictionary.getAtt1() + fileDirName,
						renderConfig, "test");
				jsonObject.accumulate("msgId", msgId);
				// 如果渲染文件解压成功，则生成Data配置文件
				if ((Boolean) jsonObject.get("success")) {
					// Data文件中,渲染文件在设计师本地的路径
					String designerLocalPath = sysDictionary.getAtt2();
					data = data.replaceAll("renderRootPath", designerLocalPath).replaceAll("_3dmax", fileDirName);
					String filePath = sysDictionary.getAtt1() + fileDirName + "/Data.txt";
					File file = new File(filePath);
					if (!file.getParentFile().exists()) {
						file.mkdirs();
					}
					if (file.exists()) {
						file.delete();
					}
					FileWriter fw = null;
					try {
						file.createNewFile();
						fw = new FileWriter(file);
						fw.write(data);
					} catch (IOException e) {
						e.printStackTrace();
						jsonObject.accumulate("success", false);
						jsonObject.accumulate("message", "Data配置文件生成异常！");
					} finally {
						if (fw != null) {
							try {
								fw.close();
							} catch (IOException e) {
								e.printStackTrace();
								jsonObject.accumulate("success", false);
								jsonObject.accumulate("message", "Data配置文件生成异常！");
							}
						}
					}
				}
			}
		}

		return jsonObject;
	}

	/**
	 * 获取HTTP请求输入流的内容
	 * 
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	private String getContentFromRequestInputStream(HttpServletRequest request) throws Exception {
		try {
			return IOUtils.toString(request.getInputStream(), "utf-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("请求参数出错");
		}
	}

	/**
	 * 
	 * @param jsonObj
	 *            JSONObject
	 * @param paramNames
	 *            String[] 需要校验的参数数组
	 * @param emptyCheck
	 *            boolean 是否要校验空值
	 * @throws Exception
	 */
	private void checkParams(JSONObject jsonObj, String[] paramNames, boolean emptyCheck) throws Exception {

		if (jsonObj != null && paramNames != null) {
			for (String paramName : paramNames) {
				Object paramValue = jsonObj.get(paramName);
				if (paramValue == null) {
					logger.info(paramName + " 参数不存在");
					throw new Exception(paramName + " 参数不存在");
				}
				if (emptyCheck && paramValue.toString().length() == 0) {
					logger.info(paramName + "参数的值不能为空");
					throw new Exception(paramName + "参数的值不能为空");
				}
			}

		}

	}

	@RequestMapping(value = "/updateEffectsConfig")
	@ResponseBody
	public Object updateEffectsConfig(HttpServletRequest request) throws Exception {
		ResponseEnvelope<DesignPlan> rs = null;
		String msgId = request.getParameter("msgId");
		String planId = request.getParameter("planId");
		String effectsConfig = request.getParameter("effectsConfig");
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("msgId", msgId);
			jsonObj.put("planId", planId);
			jsonObj.put("effectsConfig", effectsConfig);
			checkParams(jsonObj, new String[] { "planId", "msgId", "effectsConfig" }, true);
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(Integer.parseInt(planId));
			newDesignPlan.setEffectsConfig(effectsConfig);
			designPlanService.update(newDesignPlan);
			rs = new ResponseEnvelope<DesignPlan>(true, "特效配置修改成功", msgId);
		} catch (JSONException e) {
			rs = new ResponseEnvelope<DesignPlan>(false, "特效配置失败");
		} catch (Exception e) {
			rs = new ResponseEnvelope<DesignPlan>(false, e.getMessage());
		}

		return rs;
	}

	/**
	 * 更新设计方案配置文件
	 * 
	 * @param planId
	 * @param msgId
	 * @param context
	 * @return
	 */
	@RequestMapping(value = "/updatePlanConfig_old")
	@ResponseBody
	public Object updatePlanConfig_old(Integer planId, String msgId, String context, Integer planProductId,
			String bDirtyConfig, HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "planId不能为空！", msgId);
		}
		if (StringUtils.isEmpty(bDirtyConfig)) {
			return new ResponseEnvelope<DesignPlan>(false, "bDirtyConfig参数不能为空！", msgId);
		}
		if (StringUtils.isEmpty(context)) {// 老的校验是isBlank ，不要用isBlank，如果是空字符串就校验通过了，这个不行
			return new ResponseEnvelope<DesignPlan>(false, "context参数不能为空！", msgId);
		}

		// TODO:创建设计方案操作日志记录的数据>>start
		DesignPlanOperationLog designPlanOperationLog = new DesignPlanOperationLog();
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		designPlanOperationLog.setUserId(loginUser.getId());
		designPlanOperationLog.setDesignPlanId(planId);
		designPlanOperationLog.setStatus(1);
		designPlanOperationLog.setBusinessKey(SystemCommonConstant.QUIT_SAVE_DESIGN_PLAN);
		designPlanOperationLog.setGmtCreate(new Date());
		designPlanOperationLog.setIsDeleted(0);
		designPlanOperationLog.setCreator(loginUser.getLoginName());
		designPlanOperationLog
				.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		designPlanOperationLog.setModifier(loginUser.getLoginName());
		designPlanOperationLog.setGmtModified(new Date());
		designPlanOperationLogService.insertSelective(designPlanOperationLog);
		// 添加数据结束<<end

		// UpdatePlanConfigParameter parameter = new
		// UpdatePlanConfigParameter(planId,context,planProductId);
		// UpdatePlanConfigTask task = new UpdatePlanConfigTask(parameter);
		// FutureTask<Result> futureTask = new FutureTask<Result>(task);
		// TaskExecutor.getInstance().addTask(futureTask);
		try {
			DesignPlan designPlan = null;
			if (Utils.enableRedisCache()) {
				designPlan = DesignPlanCacher.getDesignPlan(planId);
			} else {
				designPlan = designPlanService.get(planId);
			}
		
			/* 设置设计方案ischange为1(设计方案被改变)->end */
			// 把变动后的配置文件内容写入配置文件
			// 解除planProductId设计产品的组(解组)
			// if(planProductId>0){
			// DesignPlanProduct
			// designPlanProduct=designPlanProductService.get(planProductId);
			// if( designPlanProduct != null ){
			// String planGroupId=designPlanProduct.getPlanGroupId();
			// if( StringUtils.isNotBlank(planGroupId) &&
			// !StringUtils.equals("0", planGroupId) ){
			// designPlanProductService.relieveGroupByPlanIdAndplanGroupId(planId,planGroupId);
			// logger.warn("移动或者旋转产品导致解组:时间:"+Utils.getTimeStr()+";设计方案id:"+planId+";planProductId:"+planProductId);
			// }
			// }
			// }
			// 解除planProductId设计产品的组(解组)->end
			ResDesign resDesign = null;
			// ResFile resFile=null;
			if (Utils.enableRedisCache()) {
				resDesign = ResourceCacher.getResDesign(designPlan.getConfigFileId());
			} else {
				resDesign = resDesignService.get(designPlan.getConfigFileId());
			}
			if (resDesign != null) {

			}
			/*String filePath = FileUploadUtils.UPLOAD_ROOT
					+ resDesign.getFilePath().replace("/", "\\");
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				filePath = FileUploadUtils.UPLOAD_ROOT + resDesign.getFilePath();
			}*/
			String filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);
			
			/**
			 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值， 则上传到web服务器。
			 **/
			Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD, 1);
			boolean uploadFtpFlag = false;
			// 上传方式为2或者3表示文件在ftp上
			// String contextOld=null;
			// Boolean state=false;
			if (ftpUploadMethod == 1 || ftpUploadMethod == 3) {
				// 替换本地
				// contextOld=FileUploadUtils.getFileContext(filePath);
				// if((contextOld.length()-context.length())>5||(contextOld.length()-context.length())>-5){
				// state=true;
				// }
				uploadFtpFlag = Utils.replaceFile(filePath, context);
				if (ftpUploadMethod == 3) {
					// contextOld=FileUploadUtils.getFileContext(filePath);
					// if((contextOld.length()-context.length())>5||(contextOld.length()-context.length())>-5){
					// state=true;
					// }
					// 替换ftp
					uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
				}
			} else if (ftpUploadMethod == 2) {
				// contextOld=FileUploadUtils.getFileContext(filePath);
				// if((contextOld.length()-context.length())>5||(contextOld.length()-context.length())>-5){
				// state=true;
				// }
				// 替换ftp
				uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
			}
			// if(state){
			// designPlan.setGmtModified(new Date());
			// designPlanService.update(designPlan);
			// }
			if (uploadFtpFlag) {
				boolean flag = designPlanService.updatePlanProductByConfig(
						context, planId,"true".equals(bDirtyConfig.toLowerCase())?true:false);
				/**
				 * DesignPlan designPlan_= new DesignPlan(); designPlan_.setId(planId);
				 * designPlan_.setGmtModified(new Date());
				 * designPlanService.update(designPlan_);
				 */
				if (!flag) {
					return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
				}
			} else {
				return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
		}
		Long endTime = System.currentTimeMillis();
		// // //System.out.println("times:"+(endTime-startTime));
		return new ResponseEnvelope<DesignPlan>(true, "配置文件更新成功！", msgId);
	}

	/**
	 * 设计详情方案推荐（同来源样板房）方案接口
	 * 
	 * @param designTempletId
	 *            (样板房ID)
	 */
	@RequestMapping(value = "/recommendPlanListWeb")
	@ResponseBody
	public Object recommendPlanListWeb(@PathVariable String style, Integer designTempletId, String msgId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (designTempletId == null) {
			msg = "参数designTempletId不能为空";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		/**
		 * if (spaceCommonId == null) { msg = "参数spaceCommonId不能为空"; return new
		 * ResponseEnvelope<DesignPlan>(false, msg, msgId); }
		 */
		DesignPlan designPlan = new DesignPlan();
		designPlan.setDesignId(designTempletId);
		DesignTemplet dPlan = null;
		if (Utils.enableRedisCache()) {
			dPlan = DesignPlanCacher.getDesignTemplet(designTempletId);
		} else {
			dPlan = designTempletService.get(designTempletId);
		}

		if (dPlan != null) {
			SpaceCommon sCommon = null;
			if (Utils.enableRedisCache()) {
				sCommon = DesignPlanCacher.getSpaceCommonOne(dPlan.getSpaceCommonId());
			} else {
				sCommon = spaceCommonService.get(dPlan.getSpaceCommonId());
			}

			if (sCommon != null)
				designPlan.setSpaceFunctionId(sCommon.getSpaceFunctionId());
		}
		designPlan.setDesignSourceType(7);
		/** 来源样板房 */
		designPlan.setIsOpen(1);
		/** 公开是1 */
		/** 根据修改时间倒序 */
		designPlan.setOrder("gmt_modified desc");
		List<DesignPlan> list = new ArrayList<>();
		try {
			/** 媒介类型.如果没有值，则表示为web前端（2） */
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			/** 验证媒介类型是否一致 */
			designPlan.setMediaType(Integer.valueOf(mediaType));
			list = designPlanService.getPlanList(designPlan);
			if (CustomerListUtils.isNotEmpty(list)) {
				for (DesignPlan desPlan : list) {
					/** 通过空间ID获取空间编码、名称、内容。 */
					if (desPlan.getSpaceCommonId() != null) {
						SpaceCommon spaceCommon = null;
						if (Utils.enableRedisCache()) {
							spaceCommon = DesignPlanCacher.getSpaceCommonTwo(desPlan.getSpaceCommonId());
						} else {
							spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
						}

						if (spaceCommon != null) {
							desPlan.setSpaceCode(spaceCommon.getSpaceCode());
							desPlan.setSpaceName(spaceCommon.getSpaceName());
							desPlan.setSpaceAreas(spaceCommon.getSpaceAreas());
						}
					}
					/** 获取方案图片路径 */
					ResPic resPic = null;
					if (desPlan.getPicId() != null) {
						if (Utils.enableRedisCache()) {
							resPic = DesignerWorksCacher.getResPic(desPlan.getPicId());
						} else {
							resPic = resPicService.get(desPlan.getPicId());
						}

						desPlan.setPicPath(resPic == null ? "" : resPic.getPicPath());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<DesignPlan>(list, msgId);
	}

	/**
	 * 设计方案公开接口
	 * 
	 * @param designPlan
	 *            方案
	 */
	@RequestMapping(value = "/designPlanOpenWeb")
	@ResponseBody
	public Object designPlanOpenWeb(String msgId, HttpServletRequest request, DesignPlan designPlan) {

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlan = (DesignPlan) JsonUtilTwo.getJsonToBean(jsonStr, DesignPlan.class);
			if (designPlan.getId() == null) {
				return new ResponseEnvelope<DesignPlan>(false, "传参异常!", "none");
			}
		}
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (designPlan.getId() == null) {
			msg = "参数id不能为空！";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		DesignPlan plan = null;
		if (Utils.enableRedisCache()) {
			plan = DesignPlanCacher.getDesignPlan(designPlan.getId());
		} else {
			plan = designPlanService.get(designPlan.getId());
		}

		if (plan == null) {
			msg = "该方案不存在!";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		} else {
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(designPlan.getId());
			newDesignPlan.setIsOpen(1);
			int i = designPlanService.update(newDesignPlan);
			logger.info("公开的方案id:" + i);
		}

		return new ResponseEnvelope<DesignPlan>(designPlan, msgId, true);

	}

	/**
	 * 设计方案公开接口
	 * 
	 * @param designPlan
	 *            方案
	 */
	@RequestMapping(value = "/designPlanClosedWeb")
	@ResponseBody
	public Object designPlanClosedWeb(String msgId, HttpServletRequest request, DesignPlan designPlan) {

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlan = (DesignPlan) JsonUtilTwo.getJsonToBean(jsonStr, DesignPlan.class);
			if (designPlan.getId() == null) {
				return new ResponseEnvelope<DesignPlan>(false, "传参异常!", "none");
			}
		}
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空！";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		if (designPlan.getId() == null) {
			msg = "参数id不能为空！";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		}
		DesignPlan plan = null;
		if (Utils.enableRedisCache()) {
			plan = DesignPlanCacher.getDesignPlan(designPlan.getId());
		} else {
			plan = designPlanService.get(designPlan.getId());
		}

		if (plan == null) {
			msg = "该方案不存在!";
			return new ResponseEnvelope<DesignPlan>(false, msg, msgId);
		} else {
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(designPlan.getId());
			newDesignPlan.setIsOpen(0);
			int i = designPlanService.update(newDesignPlan);
			logger.info("取消公开的方案id:" + i);
		}

		return new ResponseEnvelope<DesignPlan>(designPlan, msgId, true);

	}

	/**
	 * 保存 设计方案 接口
	 */
	@RequestMapping(value = "/saveDesignModeWeb")
	@ResponseBody
	public Object saveDesignModeWeb(@ModelAttribute("designPlan") DesignPlan designPlan, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (StringUtils.isEmpty(designPlan.getMsgId())) {
			return new ResponseEnvelope<DesignPlan>(false, "msgId不能为空!", "none");
		}
		if (designPlan.getDesignPlanId() == null) {
			return new ResponseEnvelope<DesignPlan>(false, "设计方案id不能为空!", designPlan.getMsgId());
		}

		designPlan.setId(designPlan.getDesignPlanId());

		try {
			sysSave(designPlan, request);
			int id = designPlanService.update(designPlan);
			DesignPlanCacher.removeDesignPlan(designPlan);
			/** 清除缓存 */

			logger.info("update:id=" + id);
			/** 如果是空房间模式，逻辑删除产品列表中的软装产品数据 */
			if ("0".equals(designPlan.getDesignMode())) {
				DesignPlanProduct designPlanProduct = new DesignPlanProduct();
				designPlanProduct.setIsDeleted(0);
				designPlanProduct.setPlanId(designPlan.getId());
				List<DesignPlanProduct> list = null;
				if (Utils.enableRedisCache()) {
					list = DesignPlanCacher.getDesignPlanProductList(designPlanProduct);
				} else {
					list = designPlanProductService.getList(designPlanProduct);
				}

				for (DesignPlanProduct dpp : list) {
					BaseProduct baseProduct = null;
					if (Utils.enableRedisCache()) {
						baseProduct = DesignPlanProductCacher.getBaseProduct(dpp.getProductId());
					} else {
						baseProduct = baseProductService.get(dpp.getProductId());
					}

					if (StringUtils.isNotBlank(baseProduct.getProductTypeValue())
							&& baseProduct.getProductSmallTypeValue() != null) {
						SysDictionary dictionary = null;
						if (Utils.enableRedisCache()) {
							dictionary = DesignPlanCacher.getSysDictionaryByValue("productType",
									new Integer(baseProduct.getProductTypeValue()), request);
						} else {
							// dictionary =
							// sysDictionaryService.getSysDictionaryByValue("productType",
							// new
							// Integer(baseProduct.getProductTypeValue()),request);
							dictionary = sysDictionaryService.findOneByTypeAndValue("productType",
									new Integer(baseProduct.getProductTypeValue()));
						}

						if (dictionary != null && StringUtils.isNotEmpty(dictionary.getValuekey())) {
							if (Utils.enableRedisCache()) {
								dictionary = DesignPlanCacher.getSysDictionaryByValue(dictionary.getValuekey(),
										new Integer(baseProduct.getProductSmallTypeValue()), request);
							} else {
								dictionary = sysDictionaryService.findOneByTypeAndValue(dictionary.getValuekey(),
										new Integer(baseProduct.getProductSmallTypeValue()));
							}

							String rootType = dictionary.getAtt1();
							if (StringUtils.isBlank(rootType) || new Integer(rootType).intValue() == 2) {
								designPlanProduct = new DesignPlanProduct();
								designPlanProduct.setId(dpp.getId());
								designPlanProduct.setIsDeleted(1);
								designPlanProductService.update(designPlanProduct);
								DesignPlanCacher.removeDesignPlanProduct(designPlanProduct);
								/** 清除缓存 **/
								DesignPlanCacher.removeObjectDesignPlanNew();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "数据异常!", designPlan.getMsgId());
		}
		return new ResponseEnvelope<DesignPlan>(designPlan, designPlan.getMsgId(), true);
	}

	/**
	 * 在模型中给设计方案新增产品 接口
	 * 
	 * @param msgId
	 * @param context
	 * @param posIndexPath
	 * @param designPlanId
	 * @param productId
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/addDesignPlanProduct")
	 * 
	 * @ResponseBody public Object addDesignPlanProduct(@RequestParam(value =
	 * "msgId", required = false) String msgId,
	 * 
	 * @RequestParam(value = "context", required = false) String context,
	 * 
	 * @RequestParam(value = "posIndexPath", required = false) String posIndexPath,
	 * 
	 * @RequestParam(value = "posName", required = false) String posName,
	 * 
	 * @RequestParam(value = "designPlanId", required = false) Integer designPlanId,
	 * 
	 * @RequestParam(value = "productId", required = false) Integer productId,
	 * HttpServletRequest request) {
	 * 
	 * if (StringUtils.isBlank(msgId)) { return new
	 * ResponseEnvelope<DesignPlan>(false, "msgId不能为空！", msgId); } if
	 * (StringUtils.isBlank(context)) { return new
	 * ResponseEnvelope<DesignPlan>(false, "context不能为空！", msgId); } if
	 * (StringUtils.isBlank(posIndexPath)) { return new
	 * ResponseEnvelope<DesignPlan>(false, "posIndexPath不能为空！", msgId); } if
	 * (StringUtils.isBlank(posName)) { return new
	 * ResponseEnvelope<DesignPlan>(false, "posName不能为空！", msgId); } if
	 * (designPlanId == null) { return new ResponseEnvelope<DesignPlan>(false,
	 * "designPlanId不能为空！", msgId); } if (productId == null) { return new
	 * ResponseEnvelope<DesignPlan>(false, "productId不能为空！", msgId); } Integer
	 * designPlanProductId =
	 * designPlanProductService.updateDesignPlanProduct(designPlanId, posIndexPath,
	 * productId,posName, request);
	 * DesignPlanCacher.removeDesignPlanProduct(designPlanId);
	 * 
	 * if (designPlanProductId == null || designPlanProductId.intValue() < 0) {
	 * return new ResponseEnvelope<DesignPlan>(false, "设计方案产品更新异常！", msgId); }
	 * boolean flag = designPlanService.updateDesignPlanConfig(designPlanId,
	 * context, request);
	 * 
	 * if (!flag) { return new ResponseEnvelope<DesignPlan>(false, "配置文件转换异常！",
	 * msgId); } else { boolean flag1 =
	 * designPlanService.updatePlanProductByConfig(context,designPlanId); if( !flag1
	 * ){ return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId); }
	 * DesignPlan designPlan = designPlanService.get(designPlanId); if( designPlan
	 * != null ){ designPlan.setGmtModified(new Date()); } return new
	 * ResponseEnvelope<DesignPlan>(designPlanProductId, msgId, true); } }
	 */

	/**
	 * 在模型中给设计方案新增产品 接口
	 * 
	 * @param msgId
	 * @param context
	 * @param posIndexPath
	 * @param designPlanId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/addDesignPlanProduct")
	@ResponseBody
	public Object addDesignPlanProduct(@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "context", required = false) String context,
			@RequestParam(value = "posIndexPath", required = false) String posIndexPath,
			@RequestParam(value = "posName", required = false) String posName,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "productId", required = false) Integer productId,
			@RequestParam(value = "houseId", required = false) String houseId,
			@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "residentialUnitsName", required = false) String residentialUnitsName,
			@RequestParam(value = "newFlag", required = false) Integer newFlag,
			@RequestParam(value = "bindProductId", required = false) Integer bindProductId,
			HttpServletRequest request) {
		Long startTime = new Date().getTime();
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, "msgId不能为空！", msgId);
		}
		if (StringUtils.isBlank(context)) {
			return new ResponseEnvelope<DesignPlan>(false, "context不能为空！", msgId);
		}
		if (StringUtils.isBlank(posIndexPath)) {
			return new ResponseEnvelope<DesignPlan>(false, "posIndexPath不能为空！", msgId);
		}
		if (StringUtils.isBlank(posName)) {
			return new ResponseEnvelope<DesignPlan>(false, "posName不能为空！", msgId);
		}
		if (designPlanId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "designPlanId不能为空！", msgId);
		}
		if (productId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "productId不能为空！", msgId);
		}

		return designPlanService.addDesignPlanProduct(designPlanId,posIndexPath,posName,productId,context,bindProductId,loginUser,msgId);
	}

	/**
	 * 渲染任务返回状态，和时间 注：用新渲染系统时次接口暂时停掉
	 * 
	 * @param planId
	 *            任务ID
	 * @param request
	 * @return object
	 */
	@RequestMapping("/getRenderTaskState_old")
	@ResponseBody
	public Object getRenderTaskState(Integer planId, String msgId, HttpServletRequest request,
			HttpServletResponse response) {
		// 参数验证
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SysTask>(false, "参数msgId不能为空！", msgId);
		}
		if (planId == null) {
			return new ResponseEnvelope<SysTask>(false, "参数planId不能为空！", msgId);
		}
		List<SysTaskResult> list = new ArrayList<SysTaskResult>();
		try {
			List<Integer> stateList = new ArrayList<>();

			// 查询状态为正在执行和等待执行的任务 ry解压完成状态 ry渲染中..ry渲染完成 的数据
			SysTaskSearch taskSearch = new SysTaskSearch();
			taskSearch.setBusinessId(planId);
			taskSearch.setStart(-1);
			taskSearch.setLimit(-1);
			taskSearch.setStateList(stateList);
			taskSearch.setOrders(" id asc ");
			stateList.add(SysTaskStatus.EXECUTING);
			stateList.add(SysTaskStatus.READY_RENDER);
			stateList.add(SysTaskStatus.RY_EXECUTING);
			stateList.add(SysTaskStatus.RENDER_END);
			stateList.add(SysTaskStatus.WAIT_EXECUTE);
			taskSearch.setStateList(stateList);
			List<SysTask> taskList = sysTaskService.getPaginatedList(taskSearch);
			int taskTime = 0;
			SysTaskResult taskResult = null;

			for (SysTask task : taskList) {
				Integer renderingType = null;
				if (task.getAttribute() != null && !"".equals(task.getAttribute())) {// {"viewPoint":1,"scene":1}
					JSONObject json = JSONObject.fromObject(task.getAttribute());
					renderingType = json.getInt("renderingType");
				}
				if (renderingType != null) {
					if (renderingType == 2) {
						taskTime = Utils.getIntValue(Utils.getValue("app.render.taskTime", "10"));
					} else if (renderingType == 4) {
						taskTime = Utils.getIntValue(Utils.getValue("app.render720.taskTime", "60"));
					}
				}
				taskResult = new SysTaskResult();
				Date gmtCreate = task.getGmtCreate();
				Date date = new Date();
				Long ms = date.getTime() - gmtCreate.getTime();
				Double spentTime = (double) (ms / 1000) / 60;
				taskResult.setSpentTime(spentTime + "");
				// 正在执行。。。
				if (task.getState() == SysTaskStatus.EXECUTING) {
					taskResult.setTaskId(task.getId());
					taskResult.setTaskState(task.getState());
					taskResult.setTaskNumber(0);
					taskResult.setTaskTime(taskTime);
					taskResult.setTaskNumber(0);
					// 取图
					if (task.getPicId() != null && task.getPicId().intValue() > 0) {
						ResPic resPic = resPicService.get(task.getPicId());
						taskResult.setPicPath(resPic == null ? "" : resPic.getPicPath());
					}
					list.add(taskResult);
					continue;
				} else {
					// 等待执行。。。
					// 查询所有出 720 和 照片级 的任务*
					SysTaskSearch taskSearchRunning = new SysTaskSearch();
					stateList.add(SysTaskStatus.EXECUTING);
					stateList.add(SysTaskStatus.WAIT_EXECUTE);
					stateList.add(SysTaskStatus.READY_RENDER);
					stateList.add(SysTaskStatus.RY_EXECUTING);
					stateList.add(SysTaskStatus.RENDER_END);
					stateList.add(SysTaskStatus.WAIT_EXECUTE);
					// taskSearchRunning.setRenderServer(task.getRenderServer());
					// 不用区分服务器
					taskSearchRunning.setStart(-1);
					taskSearchRunning.setLimit(-1);
					taskSearchRunning.setOrders(" id asc ");
					taskSearchRunning.setRenderType(2);
					taskSearchRunning.setStateList(stateList);

					Integer renderingTimeCount = null; // 所有渲染 所需时间
					Integer rendering720TimeCount = null; // 所有照片级渲染 所需时间

					Integer renderingTime = null; // 每个照片级渲染 所需时间
					Integer rendering720Time = null; // 每个所有720渲染 所需时间
					Integer renderingCount = null; // 所有照片级渲染
					Integer rendering720Count = null; // 所有720渲染

					renderingTime = Utils.getIntValue(Utils.getValue("app.render.taskTime", "10"));
					rendering720Time = Utils.getIntValue(Utils.getValue("app.render720.taskTime", "60"));
					// 照片级渲染任务数量
					renderingCount = sysTaskService.getCount(taskSearchRunning);
					taskSearchRunning.setRenderType(4);
					// 720度渲染任务数量
					rendering720Count = sysTaskService.getCount(taskSearchRunning);
					// 照片级渲染任务所需要的排队时间
					renderingTimeCount = renderingTime * renderingCount;
					// 720度渲染任务所需要的排队时间
					rendering720TimeCount = rendering720Time * rendering720Count;

					SysTaskSearch sysTaskSearch = new SysTaskSearch();
					String renderServerIp = task.getRenderServer();
					sysTaskSearch.setId(task.getId());
					// sysTaskSearch.setRenderServer(renderServerIp); //不用区分服务器
					sysTaskSearch.setState(SysTaskStatus.WAIT_EXECUTE);
					int taskCount = sysTaskService.getCount(sysTaskSearch);
					taskResult.setTaskId(task.getId());
					taskResult.setTaskState(task.getState());
					taskResult.setTaskNumber(taskCount);
					// taskResult.setTaskTime((taskCount * taskTime +
					// taskTime)+"");
					taskResult.setTaskTime((renderingTimeCount + rendering720TimeCount));
					if (task.getPicId() != null && task.getPicId().intValue() > 0) {
						ResPic resPic = resPicService.get(task.getPicId());
						taskResult.setPicPath(resPic == null ? "" : resPic.getPicPath());
					}
					list.add(taskResult);
				}
			}
			DesignPlanCacher.removeObjectDesignPlanNew();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常！", msgId);
		}
		// 按任务ID升序排序
		ComparatorS cpmparator = new ComparatorS();
		Collections.sort(list, cpmparator);
		Long endTime = System.currentTimeMillis();
		// // //System.out.println("times:" + (endTime - startTime));
		return new ResponseEnvelope<SysTask>(list, msgId, true);
	}

	/**
	 * 根据任务Id顺序排序（升序）
	 * 
	 * @author Administrator
	 * 
	 */
	public class ComparatorS implements Comparator {
		public int compare(Object obj1, Object obj2) {
			SysTaskResult unity1 = (SysTaskResult) obj1;
			SysTaskResult unity2 = (SysTaskResult) obj2;
			int flag = (unity1.getTaskId() == null ? new Integer(0) : new Integer(unity1.getTaskId()))
					.compareTo(unity2.getTaskId() == null ? new Integer(0) : new Integer(unity2.getTaskId()));
			if (flag == 0) {
				return (unity1.getTaskId() == null ? new Integer(0) : new Integer(unity1.getTaskId()))
						.compareTo(unity2.getTaskId() == null ? new Integer(0) : new Integer(unity2.getTaskId()));
			} else {
				return flag;
			}
		}
	}

	/**
	 * 是否有渲染任务
	 * 
	 * @param planId
	 * @param request
	 * @return object
	 */
	@RequestMapping("/isRenderTask")
	@ResponseBody
	public Object isRenderTask(Integer planId, String msgId, HttpServletRequest request, HttpServletResponse response) {
		Long startTime = System.currentTimeMillis();
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SysTask>(false, "参数msgId不能为空！", msgId);
		}
		// planId不能为空
		if (planId == null) {
			return new ResponseEnvelope<SysTask>(false, "参数planId不能为空！", msgId);
		}
		List<SysTask> list = new ArrayList<SysTask>();
		SysTask sysTask = new SysTask();
		String flag = "true";
		DesignPlan designPlan = null;
		if (Utils.enableRedisCache()) {
			designPlan = DesignCacher.get(planId);
		} else {
			designPlan = designPlanService.get(planId);
		}

		// 判断该设计方案是否有渲染图
		// 没有渲染图则查询是否有任务
		try {
			sysTask.setBusinessId(planId);
			if (Utils.enableRedisCache()) {
				list = SysTaskCacher.getList(sysTask);
			} else {
				list = sysTaskService.getList(sysTask);
			}

			if (Lists.isNotEmpty(list)) {
				SysTask st = list.get(0);
				if (st.getState() == SysTaskStatus.END_OF_EXCEPTION
						|| st.getState() == SysTaskStatus.TASK_TERMINATION) {
					flag = "false";
				}
			} else {
				flag = "false";
			}
			// DesignPlanCacher.removeObjectDesignPlanNew();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常！", msgId);
		}
		Long endTime = System.currentTimeMillis();
		// // //System.out.println("times:" + (endTime - startTime));
		return new ResponseEnvelope<SysTask>(flag, msgId, true);
	}

	/**
	 * 设计方案更新/新增组合产品接口
	 */
	@RequestMapping(value = "/unityGroupProduct")
	@ResponseBody
	public Object unityGroupProduct(Integer designPlanId, String context, String groupProductJson,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "materialPicId", required = false) String materialPicId, String planGroupId,
			String msgId, Integer isStandard, String center, String regionMark, Integer styleId, String measureCode,
			@RequestParam(value = "houseId", required = false) String houseId,
			@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "residentialUnitsName", required = false) String residentialUnitsName,
			@RequestParam(value = "newFlag", required = false) Integer newFlag,

			HttpServletRequest request) throws Exception {
		if (designPlanId == null || StringUtils.isEmpty(context) || StringUtils.isEmpty(groupProductJson)) {
			return new ResponseEnvelope<DesignPlan>(false, "参数存在空值", msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		// groupProductJson:[{"groupId":34227,"productId":38020,"isMainProduct":0,"posIndexPath":"\"2/16/\"","posName":"tali1","textureProductModelId":0,"groupProductUniqueId":"5166735"},{"groupId":34227,"productId":238974,"isMainProduct":1,"posIndexPath":"\"2/17/\"","posName":"beca1","textureProductModelId":0,"groupProductUniqueId":"5166733"}]
		return designPlanService.unityGroupProduct(designPlanId,groupProductJson,groupId,planProductId,materialPicId,context,planGroupId,loginUser,isStandard,center,regionMark,styleId,measureCode,msgId);
	}

	/*
	 * 校验渲染所需要的 产品 空间 样板房 是否上架
	 * 
	 * @param request
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/renderCheck")
	@ResponseBody
	public Object renderCheck(HttpServletRequest request) {

		DesignPlan designPlan = null;
		RenderCheckVo renderCheck = new RenderCheckVo();

		String planId = request.getParameter("planId");
		String msgId = request.getParameter("msgId");

		if (StringUtils.isBlank(planId)) {
			return new ResponseEnvelope<RenderCheckVo>(false, "缺少参数planId", msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<RenderCheckVo>(false, "缺少参数msgId", msgId);
		}
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if (loginUser == null) {
			return new ResponseEnvelope<RenderCheckVo>(false, "登录超时，请重新登录", msgId);
		}
		try {
			designPlan = designPlanService.get(Integer.parseInt(planId));
			if (designPlan == null) {
				return new ResponseEnvelope<RenderCheckVo>(false, "该设计方案不存在，方案ID：" + planId, msgId);
			}
			JSONObject jsonObject = null;
			jsonObject = designPlanService.shelvesValidate(designPlan, loginUser);
			logger.info("==================================" + jsonObject.getBoolean("success"));
			if (jsonObject != null) {
				Object obj = jsonObject.get("productCodeList");
				List<String> productCodeList = (List<String>) obj;
				String message = jsonObject.getString("message");
				ResponseEnvelope responseEnvelope = null;
				if (StringUtils.isBlank(message)) {
					responseEnvelope = new ResponseEnvelope<>(true, "", msgId);
				} else {
					responseEnvelope = new ResponseEnvelope<>(true, message, msgId);
				}

				responseEnvelope.setDatalist(productCodeList);

				// 判断当前时间是否在免费时间段内
				Boolean isFree = renderTaskService.renderFreeTime();
				if (isFree) {
					// 免费
					renderCheck.setIsFree(RenderTypeCode.RENDER_FREE_TIME_TYPE_KEY);
				} else {
					// 收费
					renderCheck.setIsFree(RenderTypeCode.RENDER_NOT_FREE_TIME_TYPE_KEY);
				}
				responseEnvelope.setObj(renderCheck);
				return responseEnvelope;
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return new ResponseEnvelope<RenderCheckVo>(false, "数据错误", msgId);
	}

	/**
	 * 设计方案的样板房和产品上架验证
	 * 
	 * @return
	 */
	public JSONObject shelvesValidateNew(DesignPlan designPlan) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.accumulate("success", false);
			if (designPlan == null) {
				jsonObject.accumulate("message", "没有在系统中找到设计方案！");
				logger.info("没有在系统中找到设计方案！planId is null");
				return jsonObject;
			}
			// 验证空间是否发布
			Integer spaceCommonId = designPlan.getSpaceCommonId();
			if (spaceCommonId == null) {
				jsonObject.accumulate("message", "找不到设计方案所属的空间！");
				logger.info("找不到设计方案所属得空间！planId = " + designPlan.getId() + "; spaceCommonId is null!");
				return jsonObject;
			}
			SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
			if (spaceCommon == null) {
				jsonObject.accumulate("message", "找不到设计方案所属的空间！");
				logger.info("找不到设计方案所属得空间！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
				return jsonObject;
			}
			if (spaceCommon.getStatus() != 1) {
				jsonObject.accumulate("message", "设计方案所属的空间没有发布！");
				logger.info("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
				return jsonObject;
			}

			// 验证样板房是否上架
			Integer designTempletId = designPlan.getDesignTemplateId();
			if (designTempletId == null) {
				jsonObject.accumulate("message", "找不到设计方案所属的样板房！");
				logger.info("找不到设计方案所属的样板房！planId = " + designPlan.getId() + "; designTempletId is null!");
				return jsonObject;
			}
			DesignTemplet designTemplet = designTempletService.get(designTempletId);
			if (designTemplet == null) {
				jsonObject.accumulate("message", "找不到设计方案所属的样板房！");
				logger.info("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
				return jsonObject;
			}
			if (designTemplet.getPutawayState() != 1 && designTemplet.getPutawayState() != 2) {
				jsonObject.accumulate("message", "设计方案所属的样板房没有上架！");
				logger.info(
						"设计方案所属的样板房没有上架！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
				return jsonObject;
			}

			// 验证设计方案产品是否上架
			DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
			designPlanProductSearch.setPlanId(designPlan.getId());
			designPlanProductSearch.setIsDeleted(0);
			List<DesignPlanProduct> designPlanProducts = designPlanProductService.getList(designPlanProductSearch);
			StringBuffer productMessage = new StringBuffer();
			List<String> productCodeList = new ArrayList<String>();
			if (designPlanProducts != null && designPlanProducts.size() > 0) {

				BaseProduct baseProduct = null;
				for (DesignPlanProduct designPlanProduct : designPlanProducts) {
					Integer productId = designPlanProduct.getProductId();
					if (productId != null) {
						baseProduct = baseProductService.get(productId);
						if (baseProduct != null) {
							if (baseProduct.getPutawayState().intValue() != 1
									&& baseProduct.getPutawayState().intValue() != 2
									&& baseProduct.getPutawayState().intValue() != 3) {
								productMessage.append("产品 [" + baseProduct.getProductCode() + "] 没有上架或没有上架测试或没有发布!");
								productCodeList.add(baseProduct.getProductCode());
								logger.info("产品 [" + baseProduct.getProductCode() + "] 没有上架或没有上架测试或没有发布");
							}
						}
					}
				}
				if (productMessage.length() > 0) {
					logger.info(productMessage);
					jsonObject.accumulate("message", productMessage.toString());
					jsonObject.accumulate("productCodeList", productCodeList);
				} else {
					jsonObject.remove("success");
					jsonObject.accumulate("success", true);
				}
			}

		} catch (Exception e) {
			logger.error("shelvesValidate" + e);
			jsonObject.remove("success");
			jsonObject.accumulate("success", false);
			return jsonObject;

		}
		return jsonObject;
	}

	/**
	 * 
	 * 
	 * /** 获得支付模式及对应价格
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@RequestMapping("/getPlanMode")
	@ResponseBody
	public Object getPlanMode(String msgId, String type) {
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<>(false, "缺少参数msgId", msgId);
		}
		// renderType(渲染类型)
		if (StringUtils.isEmpty(type)) {
			return new ResponseEnvelope<>(false, "缺少参数type", msgId);
		}
		List<PlanMode> list = new ArrayList<PlanMode>();
		List<SysDictionary> sysDictionaryList = sysDictionaryService.findAllByType(type);
		if (sysDictionaryList == null || sysDictionaryList.size() == 0) {
			logger.info("数据字典中未添加type" + type + "的数据");
			return new ResponseEnvelope<>(false, "没有数据,请联系管理员", msgId);
		}
		for (SysDictionary sysDictionary : sysDictionaryList) {
			// 金额以分为单位，app显示以元为单位
			Double money = 0.0;
			if (StringUtils.isNoneBlank(sysDictionary.getAtt1())) {
				money = Double.valueOf(sysDictionary.getAtt1());
			}
			list.add(new PlanMode("" + sysDictionary.getValue(), money, sysDictionary.getName(), type));
		}
		/*
		 * list.add(new PlanMode("1", 5.0)); list.add(new PlanMode("2", 10.0));
		 */
		return new ResponseEnvelope<>(list.size(), list, msgId);
	}

	/**
	 * 设计方案的样板房和产品上架验证
	 * 
	 * @return
	 */
	public JSONObject shelvesValidate(DesignPlan designPlan) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("success", false);
		if (designPlan == null) {
			jsonObject.accumulate("message", "没有在系统中找到设计方案！");
			logger.info("没有在系统中找到设计方案！planId is null");
			return jsonObject;
		}
		// 验证空间是否发布
		Integer spaceCommonId = designPlan.getSpaceCommonId();
		if (spaceCommonId == null) {
			jsonObject.accumulate("message", "找不到设计方案所属的空间！");
			logger.info("找不到设计方案所属得空间！planId = " + designPlan.getId() + "; spaceCommonId is null!");
			return jsonObject;
		}
		SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
		if (spaceCommon == null) {
			jsonObject.accumulate("message", "找不到设计方案所属的空间！");
			logger.info("找不到设计方案所属得空间！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
			return jsonObject;
		}
		if (spaceCommon.getStatus() != 1) {
			jsonObject.accumulate("message", "设计方案所属的空间没有发布！");
			logger.info("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
			return jsonObject;
		}

		// 验证样板房是否上架
		Integer designTempletId = designPlan.getDesignTemplateId();
		if (designTempletId == null) {
			jsonObject.accumulate("message", "找不到设计方案所属的样板房！");
			logger.info("找不到设计方案所属的样板房！planId = " + designPlan.getId() + "; designTempletId is null!");
			return jsonObject;
		}
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designTemplet == null) {
			jsonObject.accumulate("message", "找不到设计方案所属的样板房！");
			logger.info("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
			return jsonObject;
		}
		if (designTemplet.getPutawayState() != 1 && designTemplet.getPutawayState() != 2) {
			jsonObject.accumulate("message", "设计方案所属的样板房没有上架或上架测试！");
			logger.info("设计方案所属的样板房没有上架！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
			return jsonObject;
		}

		// 验证设计方案产品是否上架
		ResModel resModel = null;
		DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
		designPlanProductSearch.setPlanId(designPlan.getId());
		designPlanProductSearch.setIsDeleted(0);
		List<DesignPlanProduct> designPlanProducts = designPlanProductService.getList(designPlanProductSearch);
		StringBuffer productMessage = new StringBuffer();
		if (designPlanProducts != null && designPlanProducts.size() > 0) {
			BaseProduct baseProduct = null;
			for (DesignPlanProduct designPlanProduct : designPlanProducts) {
				Integer productId = designPlanProduct.getProductId();
				if (productId != null) {
					baseProduct = baseProductService.get(productId);
					if (baseProduct.getPutawayState() != 1 && baseProduct.getPutawayState() != 2) {
						productMessage.append("产品 [" + baseProduct.getProductCode() + "] 没有上架或没有上架测试!");
						logger.info("产品 [" + baseProduct.getProductCode() + "] 没有上架或没有上架测试");
					}
					// 检查产品的3dmax模型是否上传
					if (baseProduct.getModelId() != null && baseProduct.getModelId().intValue() > 0) {
						resModel = resModelService.get(baseProduct.getModelId());
						if (resModel != null && StringUtils.isNotBlank(resModel.getModelPath())) {

						} else {
							productMessage.append("产品 [" + baseProduct.getProductCode() + "] 的3DMax模型没有上传!");
							logger.info("产品 [" + baseProduct.getProductCode() + "] 的3DMax模型文件没有上传!");
						}
					} else {
						productMessage.append("产品 [" + baseProduct.getProductCode() + "] 的3DMax模型文件没有上传!");
						logger.info("产品 [" + baseProduct.getProductCode() + "] 的3DMax模型文件没有上传!");
					}
				}
			}
		}
		if (productMessage.length() > 0) {
			logger.info(productMessage);
			jsonObject.accumulate("message", productMessage.toString());
		} else {
			jsonObject.remove("success");
			jsonObject.accumulate("success", true);
		}
		return jsonObject;
	}

	@RequestMapping("/test")
	@ResponseBody
	public void test() throws Exception {
		designPlanService.renderTask();
	}

	@RequestMapping("/updateRenderTaskState")
	public Object updateRenderTaskState(HttpServletRequest request) {
		String num = request.getParameter("num");
		int i = 0;
		int j = 0;
		int x = 0;
		if (num == null || "".equals(num)) {
			return new ResponseEnvelope<>(false, "缺少参数num", "0");
		}
		List<SysTask> list = null;
		SysTask sysTask = new SysTask();
		sysTask.setStart(Integer.parseInt(num));
		sysTask.setLimit(1000);
		sysTask.setIsDeleted(0);
		list = sysTaskService.getList(sysTask);
		if (list != null && list.size() > 0) {
			for (SysTask sysTask_ : list) {
				if (StringUtils.isNotBlank(sysTask_.getAttribute())) {
					JSONObject json = JSONObject.fromObject(sysTask_.getAttribute());
					sysTask_.setRenderType(json.getInt("renderingType"));
					sysTaskService.update(sysTask_);
					i = i + 1;
				} else {
					j = j + 1;
				}
			}

		}
		return new ResponseEnvelope<>(false, "成功" + i + "个,attribute无数据" + j + "个,失败" + x + "个", "0");
	}

	/**
	 * 渲染开始消息发送
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/renderStartMessageSend")
	public @ResponseBody Map<String, String> renderStartMessageSend(HttpServletRequest request) {

		Map<String, String> resMap = new HashMap<String, String>();
		SysTask sysTask = null;
		DesignPlan plan = null;
		Integer planId = null;
		Integer userId = null;
		SysUser sysUser = null;
		Integer messageId = null;

		try {
			// ///////////////////////
			// 这个方法里需要调用 杨焕志的service
			// ///////////////////////

			String taskAesID = request.getParameter("taskID");

			logger.info("receieve taskAesID:" + taskAesID);

			if (StringUtils.isBlank(taskAesID)) {
				resMap.put("state", "2");
				resMap.put("data", "缺少参数 taskAesID");
				logger.error("=========缺少参数 taskAesID");
				return resMap;
			}

			/* 解密 */
			String taskID = "";
			try {
				taskID = AESUtil.decrypt(taskAesID, "");
			} catch (Exception e) {
				logger.error("decode taskAesID : " + taskAesID + " error, ", e);
				resMap.put("state", "3");
				resMap.put("data", "解密失败");
				return resMap;
			}

			logger.info("decode taskID : " + taskID);

			if (!StringUtils.isNumeric(taskID)) {
				resMap.put("state", "3");
				resMap.put("data", "解密失败");
				logger.error("=========解密失败");
				return resMap;

			}

			sysTask = sysTaskService.get(Integer.parseInt(taskID));
			if (sysTask == null) {
				resMap.put("state", "4");
				resMap.put("data", "找不到该任务ID：" + taskID);
				logger.error("=========找不到该任务ID：" + taskID);
				return resMap;

			} else if (SysTaskStatus.END_OF_EXECUTION == sysTask.getState()
					|| SysTaskStatus.END_OF_EXCEPTION == sysTask.getState()
					|| SysTaskStatus.END_OF_EXCEPTION_RENDERFAILD == sysTask.getState()) {
				resMap.put("state", "1");
				resMap.put("data", taskID + "该任务已经是完成或者异常结束状态,state=" + sysTask.getState());
				logger.error(taskID + "该任务已经是完成或者异常结束状态,state=" + sysTask.getState());
				return resMap;
			} else {
				RenderTask byTaskId = renderTaskService.getByTaskId(Integer.parseInt(taskID));
				if (byTaskId == null) {
					resMap.put("state", "5");
					resMap.put("data", "render_task表中找不到该任务ID：" + taskID);
					logger.error("=========render_task表中找不到该任务ID：" + taskID);
					return resMap;
				}
				/* 修改任务状态 为正在执行 */
				sysTask.setId(Integer.parseInt(taskID));
				sysTask.setState(SysTaskStatus.EXECUTING);
				sysTaskService.update(sysTask);

				/* 修改render_task表任务状态 为正在执行 */
				byTaskId.setStatus(RenderTaskStates.RENDERING);
				byTaskId.setGmtRenderStart(new Date());
				renderTaskService.update(byTaskId);

				planId = sysTask.getBusinessId();
				if (planId == null) {
					resMap.put("state", "5");
					resMap.put("data", "找不到该任务ID的设计方案：" + taskID);
					logger.error("=========找不到该任务ID的设计方案：" + taskID);
					return resMap;

				}

				plan = designPlanService.get(planId);
				if (plan == null) {
					resMap.put("state", "6");
					resMap.put("data", "找不到该任务ID的设计方案：" + taskID);
					logger.error("=========找不到该任务ID的设计方案：" + taskID);
					return resMap;
				}

				userId = plan.getUserId();
				if (userId == null) {
					resMap.put("state", "7");
					resMap.put("data", "找不到该任务ID的用户：" + taskID);
					logger.error("=========找不到该任务ID的用户：" + taskID);
					return resMap;

				}
			}
		} catch (Exception e) {
			logger.error("=========消息推送异常", e);
			resMap.put("state", "8");
			resMap.put("data", "消息推送异常");
			return resMap;
		}
		resMap.put("state", "1");
		resMap.put("data", "消息推送成功");
		return resMap;

	}

	/**
	 * 获取 httpClient 提交过来的图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/takeRenderPic")
	public @ResponseBody Map<String, String> takeRenderPic(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> map = null;
		try {
			map = designPlanService.saveRenderPic(request);
			return map;
		} catch (Exception e) {
			logger.error("takeRenderPic error : ", e);
			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put("state", "-1");
			resMap.put("data", "系统异常");
			return resMap;
		}

	}

	/**
	 * 渲染任务返回状态，和剩余时间（新渲染系统）
	 * 
	 * @param planId
	 *            任务ID
	 * @param request
	 * @return object
	 */
	@RequestMapping("/getRenderTaskState")
	@ResponseBody
	public Object getRenderTaskStateNew(Integer planId, String msgId, HttpServletRequest request,
			HttpServletResponse response) {
		/* 参数验证 */
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<SysTask>(false, "参数msgId不能为空！", msgId);
		}
		if (planId == null) {
			return new ResponseEnvelope<SysTask>(false, "参数planId不能为空！", msgId);
		}
		List<SysTaskResult> list = new ArrayList<SysTaskResult>();
		try {
			List<Integer> stateList = new ArrayList<>();

			/* 查询状态为等待执行、执行中和未执行的任务 数据 */
			SysTaskSearch taskSearch = new SysTaskSearch();
			taskSearch.setBusinessId(planId);
			taskSearch.setStart(-1);
			taskSearch.setLimit(-1);
			taskSearch.setOrders(" priority desc, gmt_create desc ");
			stateList.add(SysTaskStatus.EXECUTING);
			stateList.add(SysTaskStatus.WAITING_EXECUTE);
			stateList.add(SysTaskStatus.WAIT_EXECUTE);
			stateList.add(SysTaskStatus.CREATING_TASK);
			taskSearch.setStateList(stateList);
			List<SysTask> taskList = sysTaskService.getPaginatedList(taskSearch);

			/** 计算每条任务的等待时间 **/
			if (taskList.size() > 0) {
				list = sysTaskService.calculateTaskRenderTime(taskList);
			}
			if (Utils.enableRedisCache()) {
				DesignPlanCacher.removeObjectDesignPlanNew();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<SysTask>(false, "数据异常！", msgId);
		}
		// 按任务id排序
		ComparatorS cpmparator = new ComparatorS();
		Collections.sort(list, cpmparator);
		return new ResponseEnvelope<SysTask>(list, msgId, true);
	}

	/**
	 * 修改 designPlan 的资源id
	 */
	@RequestMapping("/resDesignRepair")
	@ResponseBody
	public ResponseEnvelope<DesignPlan> resDesignRepair() {
		// ResponseEnvelope<DesignPlan> responseEnvelope =
		// designPlanService.resDesignRepair();
		// return responseEnvelope;
		long start = System.currentTimeMillis();
		Integer x = 0;
		Integer c = 0;
		long h = 0;
		Integer count = 0;
		Integer num = 0;
		List<DesignPlan> list = new ArrayList<DesignPlan>();

		DesignPlanSearch designPlanSearch = new DesignPlanSearch();
		designPlanSearch.setIsDeleted(0);
		count = designPlanService.getCount(designPlanSearch);

		num = count / 500;
		if (num > 0) {
			num = num + 1;
		}
		if (count > 0) {
			for (int j = 0; j <= num; j++) {
				DesignPlanSearch designPlantSearch_ = new DesignPlanSearch();
				designPlantSearch_.setLimit(500);
				designPlantSearch_.setStart(j * 500);
				designPlantSearch_.setIsDeleted(0);
				list = designPlanService.getPaginatedList(designPlantSearch_);
				for (DesignPlan designPlan_ : list) {
					try {
						boolean flag = false;
						/*
						 * Integer picId = null; picId = designPlan_.getPicId(); if(picId!=null){
						 * List<ResDesign>resDesigOldList = null; List<ResDesign>resDesigList = null;
						 * ResDesign resDesign = new ResDesign(); resDesign.setId(picId);
						 * resDesign.setBusinessId(designPlan_.getId()); resDesign.setSource("res_pic");
						 * resDesigList = resDesignService.getList(resDesign);
						 * if(resDesigList==null||resDesigList.size()<=0){ resDesign.setId(null);
						 * resDesign.setOldId(picId); resDesign.setBusinessId(designPlan_.getId());
						 * resDesigOldList = resDesignService.getList(resDesign);
						 * if(resDesigOldList!=null&&resDesigOldList.size()>0){
						 * designPlan_.setPicId(resDesigOldList.get(0).getId()); } } }
						 */
						Integer configFileId = null;
						configFileId = designPlan_.getConfigFileId();
						if (configFileId != null) {
							List<ResDesign> resDesigOldList = null;
							List<ResDesign> resDesigList = null;
							ResDesign resDesign = new ResDesign();
							resDesign.setId(configFileId);
							// resDesign.setBusinessId(designPlan_.getId());
							resDesign.setSource("res_file");
							resDesigList = resDesignService.getList(resDesign);
							if (resDesigList == null || resDesigList.size() <= 0) {
								resDesign.setId(null);
								resDesign.setOldId(configFileId);
								// resDesign.setBusinessId(designPlan_.getId());
								resDesigOldList = resDesignService.getList(resDesign);
								if (resDesigOldList != null && resDesigOldList.size() > 0) {
									designPlan_.setConfigFileId(resDesigOldList.get(0).getId());
									flag = true;
								}
							}
						}

						if (flag) {
							designPlanService.update(designPlan_);
							c = c + 1;
						}
					} catch (Exception e) {
						x = x + 1;
						continue;

					}
				}
			}
		}
		h = System.currentTimeMillis() - start;
		return new ResponseEnvelope<DesignPlan>(true, "数据共：" + count + "条，成功：" + c + "条，失败：" + x + "条，共耗时：" + h, "0");
	}

	@RequestMapping("addRender")
	public void addRender(HttpServletRequest request) {
		String designPlanId = request.getParameter("designPlanId");
		String resDesignId = request.getParameter("resDesignId");
		DesignPlan designPlan = null;
		designPlan = designPlanService.get(Integer.parseInt(designPlanId));
		if (designPlan != null) {
			JSONArray renderPicArray = new JSONArray();
			JSONObject renderObj = new JSONObject();
			renderObj.accumulate("smallRenderPicId", resDesignId);
			renderObj.accumulate("renderPicId", resDesignId);
			renderPicArray.add(renderObj);
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(designPlan.getId());
			designPlanService.update(newDesignPlan);
		}
	}

	/**
	 * 一键替换
	 * 
	 * @param productId
	 *            产品ID
	 * @param bmProductId
	 *            白膜产品ID
	 * @return object
	 */
	@RequestMapping("/getProductInfo")
	@ResponseBody
	public Object getProductInfo(String templetProductCode, String designTempletProductCode, String msgId,
			HttpServletRequest request) {

		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<>(false, "参数msgId不能为空！", msgId);
		}
		if (StringUtils.isBlank(templetProductCode)) {
			return new ResponseEnvelope<>(false, "参数templetProductCode不能为空！", msgId);
		}
		if (StringUtils.isBlank(designTempletProductCode)) {
			return new ResponseEnvelope<>(false, "参数designTempletProductCode不能为空！", msgId);
		}
		CategoryProductResult productResult = new CategoryProductResult();

		BaseProduct baseProduct = baseProductService.findOneByCode(templetProductCode);
		BaseProduct bmBaseProduct = baseProductService.findOneByCode(designTempletProductCode);

		if (baseProduct == null || bmBaseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false,
					"找不到产品productCode=" + templetProductCode + " 或 " + designTempletProductCode, msgId);
		}
		productResult.setTargetBmProductId(bmBaseProduct.getId());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("type", "productType");
		paramMap.put("bValue", baseProduct.getProductTypeValue());
		paramMap.put("sValue", baseProduct.getProductSmallTypeValue());
		List<SysDictBigAndSmallTypeInfoModel> modelList = sysDictonaryDao.selectBigAndSmallTypeByTypeAndValue(paramMap);
		// BaseProductSearch searchProduct = new BaseProductSearch();
		// if( "1".equals(baseProduct.getProductTypeValue()) ||
		// "3".equals(baseProduct.getProductTypeValue()) ){
		// searchProduct.setBmIds(String.valueOf(bmProductId));
		// productResult.setIsCustomized(1);
		// }
		// searchProduct.setProductTypeValue(baseProduct.getProductTypeValue());
		// searchProduct.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		// searchProduct.setIsDeleted(0);
		// searchProduct.setStart(0);
		// searchProduct.setLimit(1);
		// List<BaseProduct> list =
		// baseProductService.getPaginatedList(searchProduct);
		// if( list != null && list.size() > 0 ){
		// BaseProduct productObj = list.get(0);
		if (StringUtils.isNotBlank(baseProduct.getBmIds())) {
			productResult.setIsCustomized(1);
		} else {
			productResult.setIsCustomized(0);
		}
		if (baseProduct.getWindowsU3dModelId() != null && baseProduct.getWindowsU3dModelId() > 0) {
			ResModel resModel = resModelService.get(baseProduct.getWindowsU3dModelId());
			productResult.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
		}
		productResult.setProductId(baseProduct.getId());
		productResult.setProductTypeValue(
				Utils.getIntValue(baseProduct.getProductTypeValue() == null ? "0" : baseProduct.getProductTypeValue()));
		productResult.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		if (bmBaseProduct != null) {
			productResult.setFullPaveLength(
					Utils.getIntValue(bmBaseProduct.getFullPaveLength() == null ? bmBaseProduct.getProductLength()
							: bmBaseProduct.getFullPaveLength()));
		}
		productResult.setProductLength(baseProduct.getProductLength());
		if (modelList != null && !modelList.isEmpty()) {
			SysDictBigAndSmallTypeInfoModel infoModel = modelList.get(0);
			productResult.setProductTypeCode(infoModel.getBigValueKey());
			productResult.setProductSmallTypeCode(infoModel.getSmallValueKey());
		}
		productResult.setMaterialPicId(baseProduct.getMaterialId());
		baseProductService.setTextureInfo(productResult, baseProduct);

		return new ResponseEnvelope<CategoryProductResult>(productResult, msgId, true);
	}

	/**
	 * 设计方案封面设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateCoverPic")
	@ResponseBody
	public Object updateCoverPic(HttpServletRequest request, HttpServletResponse response) {
		String picId = request.getParameter("picId");
		String planId = request.getParameter("planId");
		String msgId = request.getParameter("msgId");
		String designPlanType = request.getParameter("designPlanType");

		if (StringUtils.isBlank(picId)) {
			return new ResponseEnvelope<DesignPlan>(false, "缺少参数picId", msgId);
		}
		if (StringUtils.isBlank(planId)) {
			return new ResponseEnvelope<DesignPlan>(false, "缺少参数planId", msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, "缺少参数msgId", msgId);
		}
		ResRenderPic resRenderPic = resRenderPicService.get(Integer.parseInt(picId));
		if (resRenderPic == null) {
			return new ResponseEnvelope<DesignPlan>(false, "图片不存在或被删除，请刷新页面", msgId);
		}
		if (resRenderPic.getRenderingType() == null) {
			return new ResponseEnvelope<DesignPlan>(false, "图片类型错误，只允许上传照片级渲染图", msgId);
		}
		if (resRenderPic.getRenderingType().intValue() == RenderTypeCode.COMMON_720_LEVEL
				|| resRenderPic.getRenderingType().intValue() == RenderTypeCode.HD_720_LEVEL) {
			return new ResponseEnvelope<DesignPlan>(false, "720渲染图不允许设为封面", msgId);
		}
		if (resRenderPic.getRenderingType().intValue() == RenderTypeCode.SCREEN_OF_PIC) {
			return new ResponseEnvelope<DesignPlan>(false, "高清渲染不允许设为封面", msgId);
		}
		if (resRenderPic.getRenderingType().intValue() != RenderTypeCode.COMMON_PICTURE_LEVEL
				&& resRenderPic.getRenderingType().intValue() != RenderTypeCode.HD_PICTURE_LEVEL
				&& resRenderPic.getRenderingType().intValue() != RenderTypeCode.ULTRA_HD_PICTURE_LEVEL) {
			return new ResponseEnvelope<DesignPlan>(false, "图片类型错误，只允许上传照片级渲染图", msgId);
		}

		DesignPlan designPlan = null;
		if (StringUtils.isNotEmpty(designPlanType) && StringUtils.equals("1", designPlanType)) {
			// 通过副本id找到对应的设计方案id
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(Integer.parseInt(planId));

			if (designPlanRenderScene != null && designPlanRenderScene.getDesignPlanId() != null) {
				designPlan = designPlanService.get(designPlanRenderScene.getDesignPlanId());
			}

		} else {
			designPlan = designPlanService.get(Integer.parseInt(planId));
		}

		if (designPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false, "该方案不存在或被删除，请刷新页面", msgId);
		}
		try {
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(Integer.parseInt(planId));
			newDesignPlan.setCoverPicId(Integer.parseInt(picId));
			designPlanService.update(newDesignPlan);
			DesignCacher.removePlan(newDesignPlan.getId());
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEnvelope<DesignPlan>(false, "封面设置失败", msgId);
		}

		return new ResponseEnvelope<DesignPlan>(true, "封面设置成功", msgId);

	}

	/**
	 * 一键替换
	 * 
	 * @param templetId
	 *            模板ID
	 * @param designTempletId
	 *            样板房ID
	 * @return object
	 */
	@RequestMapping("/findDesignConfig")
	@ResponseBody
	public Object findDesignConfig(Integer templetId, Integer designTempletId, String msgId,
			HttpServletRequest request) {

		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<>(false, "参数msgId不能为空！", msgId);
		}
		if (templetId == null) {
			return new ResponseEnvelope<>(false, "参数templetId不能为空！", msgId);
		}
		if (designTempletId == null) {
			return new ResponseEnvelope<>(false, "参数designTempletId不能为空！", msgId);
		}
		Map<String, String> map = new HashMap<>();
		DesignPlan designPlan = designPlanService.get(templetId);
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designPlan == null || designTemplet == null) {
			return new ResponseEnvelope<>(false, "找不到模板或样板房！", msgId);
		}
		if (designPlan.getDesignTemplateId() == designTempletId) {
			return new ResponseEnvelope(map, msgId, true);
		}
		if (designPlan.getConfigFileId() != null) {
			ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());
			map.put("templetConfigPath", resDesign == null ? "" : resDesign.getFilePath());
		}
		if (designTemplet.getConfigFileId() != null) {
			ResFile resFile = resFileService.get(designTemplet.getConfigFileId());
			map.put("designConfigPath", resFile == null ? "" : resFile.getFilePath());
		}

		return new ResponseEnvelope(map, msgId, true);
	}

	/**
	 * 一键替换（处理天花）
	 * 
	 * @param templetProductCode
	 *            A空间的产品Code
	 * @param designTempletProductCode
	 *            B空间的白膜产品Code
	 * @return object
	 */
	@RequestMapping("/disposeOneKeyDecorateData")
	@ResponseBody
	public Object disposeOneKeyDecorateData(String templetProductCode, String designTempletProductCode, String msgId,
			HttpServletRequest request) {

		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<>(false, "参数msgId不能为空！", msgId);
		}
		if (StringUtils.isBlank(templetProductCode)) {
			return new ResponseEnvelope<>(false, "参数templetProductCode不能为空！", msgId);
		}
		if (StringUtils.isBlank(designTempletProductCode)) {
			return new ResponseEnvelope<>(false, "参数designTempletProductCode不能为空！", msgId);
		}
		CategoryProductResult productResult = new CategoryProductResult();
		Map<String, String> map = new HashMap<>();

		BaseProduct baseProduct = baseProductService.findOneByCode(templetProductCode);
		BaseProduct bmBaseProduct = baseProductService.findOneByCode(designTempletProductCode);

		if (baseProduct == null || bmBaseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false,
					"找不到产品productCode=" + templetProductCode + " 或 " + designTempletProductCode, msgId);
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("type", "productType");
		paramMap.put("bValue", baseProduct.getProductTypeValue());
		paramMap.put("sValue", baseProduct.getProductSmallTypeValue());
		List<SysDictBigAndSmallTypeInfoModel> modelList = sysDictonaryDao.selectBigAndSmallTypeByTypeAndValue(paramMap);
		SysDictBigAndSmallTypeInfoModel sysDic = null;
		if (modelList != null && modelList.size() > 0) {
			sysDic = modelList.get(0);
		}
		if (sysDic == null) {
			return new ResponseEnvelope<BaseProduct>(false, "产品数据分类找不到！productCode=" + templetProductCode, msgId);
		}
		BaseProductSearch searchProduct = new BaseProductSearch();
		if ("1".equals(baseProduct.getProductTypeValue())) {
			searchProduct.setBmIds(String.valueOf(bmBaseProduct.getId()));
		} else {
			searchProduct.setProductTypeValue(bmBaseProduct.getProductTypeValue());
		}
		searchProduct.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		searchProduct.setProductTypeCode(sysDic.getBigValueKey());
		LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		searchProduct.setPutawayStateList(baseProductService.getPutawayList(loginUser));
		searchProduct.setStyleId(baseProduct.getStyleId());
		searchProduct.setProductStyleIdInfo(baseProduct.getProductStyleIdInfo());
		searchProduct.setColorId(baseProduct.getColorId());
		searchProduct.setStart(0);
		searchProduct.setLimit(1);
		List<BaseProduct> productList = baseProductService.getProductData(searchProduct);
		if (productList != null && productList.size() > 0) {
			BaseProduct productObj = productList.get(0);
			map.put("productCode", productObj.getProductCode());
			map.put("bmProductCode", designTempletProductCode);
		} else {

		}
		return new ResponseEnvelope<>(map, msgId, true);
	}

	/**
	 * 照片级渲染图保存
	 * 
	 * @param request
	 * @return object
	 */
	@RequestMapping("/uploadPlanRender")
	@ResponseBody
	public Object uploadPlanRenderPicOfPhoto(String level, Integer planId, String msgId, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType, Integer taskId, Integer isChange,
			HttpServletRequest request, HttpServletResponse response, String authorization, Integer opType,
			Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
			
		}
		if (planId == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数planId不能为空", msgId);
		}
		if (StringUtils.isBlank(level)) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数level不能为空", msgId);
		}
		if (viewPoint == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数viewPoint不能为空", msgId);
		}

		if (scene == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数scene不能为空", msgId);
		}
		if (renderingType == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数renderingType不能为空", msgId);
		} else {
			if (RenderTypeCode.COMMON_PICTURE_LEVEL != renderingType) {
				return new ResponseEnvelope<ResFileSmall>(false, "未知渲染类型", msgId);
			} else {

			}
		}
		if (taskId == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "参数taskId不能为空", msgId);
		}
		// 验证登录参数 ->start
		Map<String, String> authorizationMap = Utils.getMapByJson(authorization);
		LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorizationMap.get("token"));
//		LoginUser loginUser = sysUserService.isAllowedThrough(authorizationMap.get("appKey"),
//				authorizationMap.get("token"), authorizationMap.get("deviceId"), authorizationMap.get("mediaType"));
		if (loginUser == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "请登录系统.", msgId);
		}
		// 验证登录参数 ->end
		DesignPlan designPlan = null;
		if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
			designPlan = designPlanService.get(planId);
		} else {
			designPlan = optimizePlanService.getPlan(planId);
		}
		String code = "code";
		if (designPlan == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "找不到该id=" + planId + "方案信息", msgId);
		} else {
			code = designPlan.getPlanCode();
		}
		Boolean renderSuccess = true;
		// 多文件上传使用request
		if (request instanceof MultipartHttpServletRequest) {
			// 获取文件列表并将物理文件保存到服务器中
			// 将HttpServletRequest对象转换为MultipartHttpServletRequest对象
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String fileName = null;

			List<Map> list = new ArrayList<Map>();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 照片级保存渲染图
			boolean flag = true;
			RenderPicVO vo = createRenderPicVO(designPlan, fileMap, viewPoint, scene, isTurnOn, renderingType, level,
					loginUser, taskId, opType, templateId, sourcePlanId,userRenderType, isAuto);
			vo.setIsChange(isChange);
			logger.info("设计方案id=" + designPlan.getId() + "异步保存照片级图片start");
			@SuppressWarnings("rawtypes")
			ResponseEnvelope syncSaveResult = syncSaveRenderPic(saveRenderPicService, vo,
					RenderJobType.RENDER_JOB_PHOTO);// 异步任务
			if (syncSaveResult != null && syncSaveResult.isSuccess() == false) {
				return new ResponseEnvelope<ResFileSmall>(false,
						syncSaveResult.getMessage() != null ? syncSaveResult.getMessage() : "", msgId);
			} else {
				return new ResponseEnvelope<ResFileSmall>(true, "保存渲染图中", msgId);
			}
		}
		return new ResponseEnvelope<ResFileSmall>(true, "保存渲染图中", msgId);
	}

	// TODO：添加手动保存设计方案的按钮的接口
	/**
	 * 手动保存设计方案配置文件
	 * 
	 * @param planId
	 * @param msgId
	 * @param context
	 * @return
	 */
	@RequestMapping(value = "/manualSavePlanConfig")
	@ResponseBody
	public Object manualSavePlanConfig(Integer planId, String msgId, String context, Integer planProductId,
			HttpServletRequest request) {
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "planId不能为空！", msgId);
		}
		if (StringUtils.isBlank(context)) {
			return new ResponseEnvelope<DesignPlan>(false, "context参数不能为空！", msgId);
		}

		// TODO:创建设计方案操作日志记录的数据
		DesignPlanOperationLog designPlanOperationLog = new DesignPlanOperationLog();
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		designPlanOperationLog.setUserId(loginUser.getId());
		designPlanOperationLog.setDesignPlanId(planId);
		designPlanOperationLog.setStatus(2);
		designPlanOperationLog.setBusinessKey(SystemCommonConstant.MANUAL_SAVE_DESIGN_PLAN);
		designPlanOperationLog.setGmtCreate(new Date());
		designPlanOperationLog.setIsDeleted(0);
		designPlanOperationLog.setCreator(loginUser.getLoginName());
		designPlanOperationLog
				.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		designPlanOperationLog.setModifier(loginUser.getLoginName());
		designPlanOperationLog.setGmtModified(new Date());
		designPlanOperationLogService.insertSelective(designPlanOperationLog);
		// 添加数据结束

		try {
			DesignPlan designPlan = null;
			if (Utils.enableRedisCache()) {
				designPlan = DesignPlanCacher.getDesignPlan(planId);
			} else {
				designPlan = designPlanService.get(planId);
			}
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(planId);
			// 设置设计方案ischange为1(设计方案被改变)
			newDesignPlan.setIsChange(new Integer(1));
			designPlanService.update(newDesignPlan);
			// 设置设计方案ischange为1(设计方案被改变)->end
			// 把变动后的配置文件内容写入配置文件
			ResDesign resDesign = null;
			if (Utils.enableRedisCache()) {
				resDesign = ResourceCacher.getResDesign(designPlan.getConfigFileId());
			} else {
				resDesign = resDesignService.get(designPlan.getConfigFileId());
			}
			if (resDesign != null) {

			}
			/*String filePath = FileUploadUtils.UPLOAD_ROOT
					+ resDesign.getFilePath().replace("/", "\\");
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				filePath = FileUploadUtils.UPLOAD_ROOT + resDesign.getFilePath();
			}*/
			String filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);
			/**
			 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值， 则上传到web服务器。
			 **/
			Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD, 1);
			boolean uploadFtpFlag = false;
			// 上传方式为2或者3表示文件在ftp上
			if (ftpUploadMethod == 1 || ftpUploadMethod == 3) {
				// 替换本地
				uploadFtpFlag = Utils.replaceFile(filePath, context);
				if (ftpUploadMethod == 3) {
					// 替换ftp
					uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
				}
			} else if (ftpUploadMethod == 2) {
				// 替换ftp
				uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
			}
			if (uploadFtpFlag) {
				boolean flag = designPlanService.updatePlanProductByConfig(
						context, planId,false);
				if (!flag) {
					return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
				}
			} else {
				return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
		}
		return new ResponseEnvelope<DesignPlan>(true, "配置文件更新成功！", msgId);
	}

	@ResponseBody
	@RequestMapping("/uploadPlanRenderVideo")
	public Object uploadPlanRenderVideo(String msgId, Integer planId, String renderingType, String authorization,
			Integer taskId, Integer isChange, HttpServletRequest request, HttpServletResponse response, Integer opType,
			Integer templateId, Integer sourcePlanId,Integer userRenderType,Integer isAuto) {
		
		// 验证登录参数 ->start
		Map<String, String> authorizationMap = Utils.getMapByJson(authorization);
		LoginUser loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorizationMap.get("token"));
//		LoginUser loginUser = sysUserService.isAllowedThrough(authorizationMap.get("appKey"),
//				authorizationMap.get("token"), authorizationMap.get("deviceId"), authorizationMap.get("mediaType"));
		if (loginUser == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "请登录系统.", msgId);
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证
		if (planId == null) {
			return new ResponseEnvelope<ResFileSmall>(false, "planId不能为空", msgId);
		}
		if (StringUtils.isBlank(renderingType)) {
			return new ResponseEnvelope<ResFileSmall>(false, "renderingType不能为空", msgId);
		}
		MultipartRequest multipartRequest = null;
		if (request instanceof MultipartRequest) {
			multipartRequest = (MultipartRequest) request;

		}
		DesignPlan designPlan = null;
		if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
			designPlan = designPlanService.get(planId);
		} else {
			designPlan = optimizePlanService.getPlan(planId);
		}

		// 获得文件流
		// MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)
		// request;
		// Iterator<String> iter = multipartRequest.getFileNames();
		// boolean flag = true;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 6表示普通720度渲染视频
		// if(RenderTypeCode.COMMON_VIDEO == Utils.getIntValue(renderingType)){
		RenderPicVO vo = this.createRenderVideoVO(designPlan, fileMap, Utils.getIntValue(renderingType), loginUser,
				taskId, opType, templateId, sourcePlanId,userRenderType,isAuto);
		vo.setIsChange(isChange);
		ResponseEnvelope syncSaveResult = this.syncSaveRenderPic(saveRenderPicService, vo,
				RenderJobType.RENDER_JOB_VIDEO);
		// }else {
		// msg = "参数 renderingType 未知图片类型";
		// return new ResponseEnvelope<ResFileSmall>(false, msg, msgId);
		// }
		return new ResponseEnvelope<ResFileSmall>(true, "视频保存中", msgId);
	}

	/** 封装720视频renderPicVO对象 **/
	private RenderPicVO createRenderVideoVO(DesignPlan designPlan, Map<String, MultipartFile> fileMap,
			Integer renderingType, LoginUser loginUser, Integer taskId, Integer opType, Integer templateId,
			Integer sourcePlanId,Integer userRenderType, Integer isAuto) {
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		RenderPicVO vo = new RenderPicVO();
		vo.setOpType(opType);
		vo.setDesignPlan(designPlan);
		vo.setFileMap(fileMap);
		vo.setRenderingType(renderingType);
		vo.setLoginUser(loginUser);
		vo.setTaskId(taskId);
		vo.setUserRenderType(userRenderType);
		sourcePlanId = sourcePlanId != null ? sourcePlanId : 0;
		vo.setSourcePlanId(sourcePlanId);
		if(isAuto == null) {
			isAuto = 0;
		}
		if (templateId != null) {
			vo.setTemplateId(templateId);
		}
		vo.setIsAuto(isAuto);

		return vo;
	}

	/**
	 * 更新设计方案配置文件
	 *
	 * @param planId
	 * @param msgId
	 * @param context
	 * @return
	 */
	@RequestMapping(value = "/updatePlanConfig")
	@ResponseBody
	public Object updatePlanConfig(Integer planId, String msgId, String context, Integer planProductId,
			String bDirtyConfig, HttpServletRequest request, Integer opType) {
		if (planId == null) {
			return new ResponseEnvelope<DesignPlan>(false, "planId不能为空！", msgId);
		}
		if (StringUtils.isEmpty(bDirtyConfig)) {
			return new ResponseEnvelope<DesignPlan>(false, "bDirtyConfig参数不能为空！", msgId);
		}
		if (StringUtils.isEmpty(context)) {// 老的校验是isBlank ，不要用isBlank，如果是空字符串就校验通过了，这个不行
			return new ResponseEnvelope<DesignPlan>(false, "context参数不能为空！", msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null ||loginUser.getId() == null) {
			return new ResponseEnvelope<DesignPlan>(false, "登录超时，请重新登录！", msgId);
		}
		// 如果没有传opType 默认为用户一键装修
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}

		return designPlanService.updatePlanConfig(loginUser,planId,opType,context,bDirtyConfig,msgId);
	}
	
	/**
	 * 批量新增产品
	 * 
	 * @author huangsongbo
	 * @param designPlanId 设计方案id
	 * @param newProductsJson 待新增的产品信息json
	 * @param context 配置文件
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/addDesignPlanProducts")
	@ResponseBody
	public Object addDesignPlanProducts(
			Integer designPlanId,
			String newProductsJson,
			String context,
			String msgId,
			HttpServletRequest request
			) {
		// 参数验证 ->start
		if(designPlanId == null) {
			return new ResponseEnvelope<>(false, "参数:designPlanId不能为空", msgId);
		}
		DesignPlan designPlan = designPlanService.get(designPlanId);
		if(designPlan == null) {
			return new ResponseEnvelope<>(false, "设计方案没有找到(designPlanId = " + designPlanId + ")", msgId);
		}
		if(StringUtils.isEmpty(newProductsJson)) {
			return new ResponseEnvelope<>(false, "参数:newProductsJson不能为空", msgId);
		}
		// 不需要更新配置文件,放在退出房间的时候更新
		/*if(StringUtils.isEmpty(context)) {
			return new ResponseEnvelope<>(false, "参数:context不能为空", msgId);
		}*/
		if(StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<>(false, "参数:msgId不能为空", msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser == null) {
			return new ResponseEnvelope<>(false, "未检测到用户登录信息,请重新登录", msgId);
		}
		// 参数验证 ->end
		
		List<AddDesignPlanProductsResultVO> result;
		try {
			result = designPlanService.addDesignPlanProducts(newProductsJson, context, designPlan, loginUser);
		} catch (DesignPlanException e) {
			return new ResponseEnvelope<>(false, e.getMessage(), msgId);
		}
		
		return new ResponseEnvelope<AddDesignPlanProductsResultVO>(result == null ? 0 : result.size(), result, msgId);
	}

}
