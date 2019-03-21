package com.nork.design.service.impl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.ClientEndpoint;

import com.nork.common.util.*;
import com.nork.design.model.*;
import com.nork.design.service.*;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.product.service.*;
import com.nork.system.model.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.aes.model.AESFileConstant;
import com.nork.common.async.AssembleDesignPlanDeleted;
import com.nork.common.async.Result;
import com.nork.common.async.TaskExecutor;
import com.nork.common.async.UnitUpdateParameter;
import com.nork.common.async.UnitUpdateTask;
import com.nork.common.async.UpdateDesignPlanCacheParameter;
import com.nork.common.async.UpdateDesignPlanCacheTask;
import com.nork.common.cache.CommonCacher;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.pano.GeneratePanoramaExecutor;
import com.nork.common.pano.GeneratePanoramaTask;
import com.nork.common.properties.AesProperties;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanCacher;
import com.nork.design.cache.DesignPlanCommentCacher;
import com.nork.design.cache.DesignPlanLikeCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.cache.UsedProductsCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.common.RecommendedDecorateState;
import com.nork.design.controller.web.IntelligenceDecorationController.PosNameInfo;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.dao.DesignPlanProductMapper;
import com.nork.design.exception.DesignPlanException;
import com.nork.design.model.constant.ContextType;
import com.nork.design.model.constant.DesignPlanBusinessTypeConstant;
import com.nork.design.model.constant.PlanVisibleCode;
import com.nork.design.model.search.DesignPlanCommentSearch;
import com.nork.design.model.search.DesignPlanLikeSearch;
import com.nork.design.model.search.DesignPlanSearch;
import com.nork.design.model.unity.BindPointDataEx;
import com.nork.design.model.unity.ItemExt;
import com.nork.design.model.unity.RoomConfig;
import com.nork.design.model.unity.RoomExt;
import com.nork.design.model.unity.Root;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.cache.SpaceCommonCacher;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.dao.MobileRenderRecordMapper;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.pay.metadata.PayType;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.GroupProductDetailsCache;
import com.nork.product.dao.StructureProductMapper;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.BaseProductPutawayState;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.ProductConstant;
import com.nork.product.model.ProductModelConstant;
import com.nork.product.model.ProductTypeConstant;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.SplitTextureInfoDTO;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.StructureProductDetails;
import com.nork.product.model.dto.WaterJetInfoDTO;
import com.nork.product.model.dto.WaterJetTextureInfoDTO;
import com.nork.product.model.dto.WaterjetInfoReturnDTO;
import com.nork.product.model.result.SearchStructureProductDetailResult;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.vo.ProductCeilingVO;
import com.nork.render.model.RenderTask;
import com.nork.render.model.RenderTaskStates;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.service.RenderTaskService;
import com.nork.resource.model.ResUsedProducts;
import com.nork.resource.service.ResUsedProductsService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.model.search.ResPicSearch;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.ResDesignRenderSceneService;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.ResRenderVideoService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.system.websocket.obj.MessageResponse;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskRecord;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.service.SysTaskRecordService;
import com.nork.task.service.SysTaskService;
import com.nork.user.model.UserTypeCode;

import app.nork.task.render.model.RenderTaskStatus;
import app.nork.task.render.model.RequestHeadEntity;
import app.nork.task.render.model.RequestQueryTaskEntity;
import app.nork.task.render.model.TaskResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

/**
 * @Title: DesignPlanServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计模块-设计方案ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 17:09:51
 * @version V1.0   
 */
@Service("designPlanService")
@Transactional
@ClientEndpoint
public class DesignPlanServiceImpl implements DesignPlanService {
 
	private static Logger logger = Logger.getLogger(DesignPlanServiceImpl.class);
	
	/*** 获取配置文件 webSocket.properties */
	private final static ResourceBundle webSocket = ResourceBundle.getBundle("config/webSocket");
	private final String SERVERURL = Utils.getValue("app.server.url", "http://localhost:18080/onlineDecorate");
	private final String RESOURCESURL = Utils.getValue("app.resources.url",
			"http://localhost:18080/onlineDecorate/upload");
	
	private String logPrefix = "customLog:";
	
	@Autowired
	private DesignPlanRenderService designPlanRenderService;
	
	@Autowired
	private DesignPlanRecommendedService designPlanRecommendedService;
	
	@Autowired
	private DesignPlanCommentService designPlanCommentService;
	
	@Autowired
	private DesignPlanLikeService designPlanLikeService;

	@Autowired
    private MobileRenderRecordMapper mobileRenderRecordMapper;
	
	@Autowired
	private ResDesignRenderSceneService resDesignRenderSceneService;
 
	private static String PASSWORD_CRYPT_KEY= Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Resource
	private DesignTempletProductService designTempletProductService;
	@Resource
	private DesignPlanMapper designPlanMapper;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private DesignPlanProductMapper designPlanProductMapper;
	@Resource
	private ResDesignService resDesignService;
	@Resource
	private DesignTempletService designTempletService;
	@Resource
	private DesignPlanService designPlanService;
	@Resource
	private BaseProductService baseProductService;
	@Resource
	private UsedProductsService usedProductsService;
	@Resource
	private ResPicService resPicService;
	@Resource
	private ResTextureService resTextureService;
	@Resource
	private ResModelService resModelService;
	@Resource
	private ResFileService resFileService;
	@Resource
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private BaseMessageService baseMessageService;
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private SysTaskService sysTaskService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private SysTaskRecordService sysTaskRecordService;
	@Autowired
	private ProductUsageCountService productUsageCountService;
	@Autowired
	private ResUsedProductsService resUsedProductsService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private RenderTaskService renderTaskService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	@Autowired
	private DesignRenderRoamService designRenderRoamService;
	@Autowired
	private OptimizePlanService optimizePlanService;
	@Autowired
	private DesignPlanOperationLogService designPlanOperationLogService;
 
	@Autowired
	private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
	
	@Autowired
	private BaseProductServiceV2 baseProductServiceV2;

	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	
	@Autowired
    private StructureProductMapper structureProductMapper;
	
	@Autowired
	private StructureProductDetailsService structureProductDetailsService;

	@Autowired
	private MobilePayRenderService mobilePayRenderService;

	@Autowired
	private BaseWaterjetTemplateService baseWaterjetTemplateService;

	@Autowired
	private BaseProductCountertopsService baseProductCountertopsService;

	@Autowired
	private DesignPlanCustomizedProductOrderService designPlanCustomizedProductOrderService;

	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	//private final String APP_RESOURCES_URL="app.resources.url";
	
	@Autowired
	public void setDesignPlanMapper(
			DesignPlanMapper designPlanMapper) {
		this.designPlanMapper = designPlanMapper;
	}

	@Autowired
	public void setDesignPlanProductMapper(
			DesignPlanProductMapper designPlanProductMapper) {
		this.designPlanProductMapper = designPlanProductMapper;
	}

	public final static String APP_OPLOAD_METHOD="app.upload.method";
	
	private final static ResourceBundle render = ResourceBundle.getBundle("render");
	//任务服务器
	private final static String TASK_SERVER_HOSTS = render.getString("taskServerHosts");
	private static String[] taskServers = null;
	private static Integer taskAllocationIndex = 0;
	//渲染服务器
	private final static String RENDER_SERVER_HOSTS = render.getString("renderServerHosts");
	private static String[] renderServers = null;
	private static Integer renderAllocationIndex = 0;
	private static Map<String,RenderConfig> renderServersMap = new HashMap<>();
	private final static boolean JOB_LOG_FLAG = "true".equals(Utils.getValue("jobLog","false"))?true:false;

	private void initRender(){
		if( StringUtils.isNotBlank(TASK_SERVER_HOSTS) ){
			taskServers = TASK_SERVER_HOSTS.split(",");
		}
		if( StringUtils.isNotBlank(RENDER_SERVER_HOSTS) ) {
			JSONArray jsonArray = JSONArray.fromObject(RENDER_SERVER_HOSTS);
			if(jsonArray != null ){
				renderServers = new String[jsonArray.size()];
				RenderConfig renderConfig = null;
				for( int i=0;i<jsonArray.size();i++ ){
					renderConfig = (RenderConfig) JSONObject.toBean((JSONObject) jsonArray.get(i), RenderConfig.class);
					renderServersMap.put(renderConfig.getRenderServer(),renderConfig);
					renderServers[i] = renderConfig.getRenderServer();
				}
			}else{
				logger.debug("渲染服务器renderServerHosts配置的格式不正确,请按照配置文件中提供的例子正确配置！");
			}
		}else{
			logger.debug("渲染服务器renderServerHosts配置的格式不正确,请按照配置文件中提供的例子正确配置！");
		}
	}


	/**
	 * 新增数据
	 *
	 * @param designPlan
	 * @return  int 
	 */
	@Override
	public int add(DesignPlan designPlan) {
		designPlanMapper.insertSelective(designPlan);
		return designPlan.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designPlan
	 * @return  int 
	 */
	@Override
	public int update(DesignPlan designPlan) {
		/*删除 进入该样板房的缓存*/
		/*Map<Object,Object>	paramsMap=new HashMap<>();
		paramsMap.put("designPlanId", designPlan.getId());
		CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);*/
		return designPlanMapper.updateByPrimaryKeySelective(designPlan); 
	}
	
	@Override
	public void delete(Integer id){
		designPlanMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public ResponseEnvelope delete(int taskBusinessId,DesignPlan designPlan) {
	    
	    int count = sysTaskService.getSysTaskState(taskBusinessId);
        if (count > 0) {
            return new ResponseEnvelope<>(false, "该设计方案有正在进行的渲染任务  ", "none");
        }
        
        DesignPlan plan = designPlanService.get(designPlan.getId());
        if (plan == null) {
            return new ResponseEnvelope<DesignPlan>(false, "数据异常!",
                    designPlan.getMsgId());
        }
        
        // .测试发布中 、发布中 、待审核的方案不能删除、修改,点击删除提示用户"请先取消发布!"
        if (plan.getIsRelease() != null) {
            if (plan.getIsRelease().intValue() == RecommendedDecorateState.IS_RELEASEING
                    || plan.getIsRelease().intValue() == RecommendedDecorateState.IS_TEST_RELEASE
                    || plan.getIsRelease().intValue() == RecommendedDecorateState.WAITING_CHECK_RELEASE) {
                return new ResponseEnvelope<>(false, "请取消发布后编辑!  ", "none");
            }
        }
        /* 删除设计方案，然后异步删除模型 图片等其他操作 */
        designPlanMapper.deleteByPrimaryKey(designPlan.getId());
        
        try {
            AssembleDesignPlanDeleted designPlanDeleted = new AssembleDesignPlanDeleted(plan);
            FutureTask<Result> futureTask = new FutureTask<Result>(
                    designPlanDeleted);
            TaskExecutor.getInstance().addTask(futureTask);
            logger.error("任务ID：" + designPlan.getId() + "  异步处理完毕！");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope<DesignPlan>(false, "数据异常!",
                    designPlan.getMsgId());
        }
        return new ResponseEnvelope<>(true, "操作成功", "none");
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	@Override
	public DesignPlan get(Integer id) {
		return designPlanMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designPlan
	 * @return   List<DesignPlan>
	 */
	@Override
	public List<DesignPlan> getList(DesignPlan designPlan) {
	    return designPlanMapper.selectList(designPlan);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designPlanSearch
	 * @return   int
	 */
	@Override
	public int getCount(DesignPlanSearch designPlanSearch){
		return  designPlanMapper.selectCount(designPlanSearch);	
    }
	/**
	 *    获取样板房产品Id
	 *
	 * @param  map
	 * @return   int
	 */
	@Override
	public int getTemplateProductId(Map map){
		return  designPlanMapper.getTemplateProductId(map);	
	}
	

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanSearch
	 * @return   List<DesignPlan>
	 */
	@Override
	public List<DesignPlan> getPaginatedList(
			DesignPlanSearch designPlanSearch) {
		return designPlanMapper.selectPaginatedList(designPlanSearch);
	}

	@Override
	public List<DesignPlan> getPlanList(DesignPlan designPlan) {
		return designPlanMapper.getPlanList(designPlan);
	}
	
	@Override
	public int getPlanCount(DesignPlan designPlan) {
		return designPlanMapper.getPlanCount(designPlan);
	}

	@Override
	public List<Map> getUserMaxPlan(Map map) {
		return designPlanMapper.getUserMaxPlan(map);
	}

	/**
	 * 其他
	 * 
	 */
	@Override
	public List<DesignPlanResult> getDesignList(DesignPlan designPlan) {
		return designPlanMapper.designList(designPlan);
	}

	/**
	 * 操作设计方案
	 * @param operationType 操作类型。1、新增设计方案。从样板房中拷贝。2、改造设计方案，从样板房中拷贝。
	 * @param sourceId 数据来源ID
	 * @param sourceType 数据来源类型
	 * @return
	 */
	public Object operationDesignPlan(String operationType,String sourceType,Integer sourceId) throws InvocationTargetException, IllegalAccessException {
		JSONObject operationResult = new JSONObject();

		//判断参数是否符合要求
		if( StringUtils.isNotBlank(operationType) || StringUtils.isNotBlank(sourceType) || sourceId == null ){
			assembly(operationResult,false,"参数不能为空");
			return operationResult;
		}

		//判断来源类型
		DesignPlan newDesignPlan = new DesignPlan();
		if( "T".equals(sourceType) ){
			DesignTemplet designTemplet = designTempletService.get(sourceId);
			if( designTemplet == null ){
				assembly(operationResult, false, "没有找到id为 " + sourceId + " 的样板间");
				return operationResult;
			}
			BeanUtils.copyProperties(newDesignPlan,designTemplet);
		}else if( "P".equals(sourceType) ){
			DesignPlan designPlan = designPlanService.get(sourceId);
			if( designPlan == null ){
				assembly(operationResult,false,"没有找到id为 "+sourceId+" 的设计方案");
				return operationResult;
			}
			BeanUtils.copyProperties(newDesignPlan,designPlan);
		}
		return operationResult;
	}

	public void assembly(JSONObject jsonObject,boolean flag,String message){
		jsonObject.accumulate("success",flag);
		jsonObject.accumulate("message",message);
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model,HttpServletRequest request){
		if(model != null){
				LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
		    /*Map<String,Object> map1=(Map<String, Object>) request.getAttribute("AuthorizationData");
	                String uid=map1.get("uid").toString();
	                String signflat=request.getParameter("signflat");
	                String key=signflat+uid;
	                LoginUser loginUser =(LoginUser) JedisUserUtils.getObject(key);*/
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResDesign model, LoginUser loginUser){
		if(model != null){
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model, LoginUser loginUser){
		if(model != null){
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 更新设计方案
	 * @param desPlan 方案对象 
	 * @param designPlanId 方案ID
	 * @param planProductJson  [{方案产品ID ：产品ID}]
	 * @param materialPicId 材质
	 * @return boolean
	 */
	public boolean updateDesignPlan(DesignPlan desPlan,Integer designPlanId,String planProductJson,
		String materialPicId,String context,HttpServletRequest request, String splitTexturesChoose) throws InvocationTargetException, IllegalAccessException {
		//替换方案产品列表中的数据
		Gson gson2 = new Gson();
		List<PlanProductJsonModel> modelList = gson2.fromJson(planProductJson, new TypeToken<List<PlanProductJsonModel>>() {}.getType());
		if (null == modelList || modelList.size() == 0) {
			return false;
		}
		for (PlanProductJsonModel planProductJsonModel : modelList) {
			Integer planProductId = planProductJsonModel.getPlanProductId();
			Integer productId = planProductJsonModel.getProductId();
			DesignPlanProduct designPlanProduct = designPlanProductService.get(planProductId);
			Integer oldProductId = designPlanProduct.getProductId();
			BaseProduct newProduct = baseProductService.get(productId);
			try {
				/*如果产品一样*/
				if (oldProductId.equals(productId)) {
					/*保存时,当oldProductId和productId相等,则识别为:可拆分材质产品保存材质*/

					if (StringUtils.isNotBlank(newProduct.getSplitTexturesInfo())) {
						/*识别为该产品是可拆分材质产品*/
						if (StringUtils.isBlank(splitTexturesChoose)) {
							/*识别为该产品是替换操作,并没有选择材质->赋予默认材质*/
							designPlanProduct.setSplitTexturesChooseInfo(newProduct.getSplitTexturesInfo());
						} else {
							/*保存用户选择的材质*/
							//JSONArray jsonArray=JSONArray.fromObject(designPlanProduct)
							String oldSplitTexturesChooseInfo = "";
							if (StringUtils.isBlank(designPlanProduct.getSplitTexturesChooseInfo())) {
								/*从产品表中查找默认材质信息*/
								oldSplitTexturesChooseInfo = newProduct.getSplitTexturesInfo();
							} else {
								oldSplitTexturesChooseInfo = designPlanProduct.getSplitTexturesChooseInfo();
							}
							JSONArray jsonArray = JSONArray.fromObject(oldSplitTexturesChooseInfo);
							@SuppressWarnings({"unchecked", "deprecation"})
							List<SplitTextureInfoDTO> splitTextureDTOList = (List<SplitTextureInfoDTO>) JSONArray.toList(jsonArray, SplitTextureInfoDTO.class);
							List<String> list = Utils.getListFromStr2(splitTexturesChoose, ";");
							/*循环修改 key 相等的  默认材质id*/
							for (String str : list) {
								//更新方案产品列表相同产品的多材质信息
								/*boolean flag = updateSameProductTextureInfo(designPlanId,planProductId,newProduct,str);
								if(flag){
									splitTextureDTOList=this.updateSplitTexture(splitTextureDTOList,str);
								}*/
								splitTextureDTOList = this.updateSplitTexture(splitTextureDTOList, str);
							}
							JSONArray jsonArray2 = JSONArray.fromObject(splitTextureDTOList);
							designPlanProduct.setSplitTexturesChooseInfo(jsonArray2.toString());
						}
					}
				} else {
					designPlanProduct.setSplitTexturesChooseInfo(newProduct.getSplitTexturesInfo() == null ? "" : newProduct.getSplitTexturesInfo());
				}
				designPlanProduct.setProductId(productId);
				designPlanProduct.setMaterialPicId(materialPicId);
				String productSequence = com.nork.common.util.StringUtils.replaceString(designPlanProduct.getPosIndexPath(), "/");
				/*顺序*/
				designPlanProduct.setProductSequence(productSequence);
				//替换时，产品显示
				designPlanProduct.setIsHide(0);
				//媒介类型.如果没有值，则表示为web前端（2）
				String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
				/*识别要替换的产品有没有模型,有->替换modelProductId为要替换的产品id;没有->识别modelProductId是不是为空,为空则为原产品id,有则不变*/
				String modelId = baseProductService.getU3dModelId(mediaType, newProduct);
				if (newProduct != null && StringUtils.isNotBlank(modelId) && Utils.getIntValue(modelId) > 0) {
					/*模型不为空->modelProductId储存新的产品id*/
					// 如果该产品是白膜,则同时更新initProductId字段
					if (StringUtils.isNotBlank(newProduct.getProductCode()) && newProduct.getProductCode().startsWith("baimo_")) {
						designPlanProduct.setInitProductId(newProduct.getId());
					}
					designPlanProduct.setModelProductId(newProduct.getId());
				}
				/*识别要替换的产品有没有模型,有->替换modelProductId为要替换的产品id;没有->识别modelProductId是不是为空,为空则为原产品id,有则不变->end*/
				/*如果是可拆分材质产品,则保存选择的材质信息*/


				/*如果是可拆分材质产品,则保存选择的材质信息->end*/
				sysSave(designPlanProduct, request);
				designPlanProductMapper.updateByPrimaryKeySelective(designPlanProduct);
				if (Utils.enableRedisCache()) {
					DesignPlanProductCacher.remove(designPlanProduct.getId());
				}
				/*更新产品使用次数表*/
				Integer userId = desPlan.getUserIdTemp();
				if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0 && userId != null && userId > 0)
					productUsageCountService.update(userId, designPlanProduct.getProductId(), 1);
				/*更新产品使用次数表->end*/

				//根据产品列表更新装修风格
				List<BaseProduct> productStyleList = baseProductService.getProductStyle(designPlanId);

				if (CustomerListUtils.isNotEmpty(productStyleList)) {
					DesignPlan designPlan = new DesignPlan();
					designPlan.setId(designPlanId);
					if (productStyleList.size() == 1) {
						Integer style = Utils.getnotNullInt(productStyleList.get(0));
						designPlan.setDesignStyleId(style);
					} else {
						designPlan.setDesignStyleId(8);
					}
					//更新设计方案设置是否变更为1，是否装修过1，是否公开为0，
					designPlan.setIsChange(1);
					designPlan.setIsDecorated(1);
					designPlan.setIsOpen(0);
					designPlan.setSceneModified(Utils.getCurrentTimeMillis());//设计方案的配置文件发生改变要更新时间戳
					sysSave(designPlan, request);
					designPlanMapper.updateByPrimaryKeySelective(designPlan);
					if (Utils.enableRedisCache()) {
						DesignCacher.removePlan(designPlan.getId());
					}
				}

				/*新增一条用户使用产品记录*/
				// 白膜产品不记录在已使用列表中
				if (newProduct.getProductCode() != null && !newProduct.getProductCode().startsWith("baimo_")) {
					SysUser sysUser = sysUserService.get(desPlan.getUserId());
					UsedProducts usedProducts = new UsedProducts();
					usedProducts.setCreator(sysUser.getNickName());
					usedProducts.setGmtCreate(new Date());
					usedProducts.setGmtModified(new Date());
					usedProducts.setModifier(sysUser.getNickName());
					usedProducts.setIsDeleted(0);
					usedProducts.setUserId(desPlan.getUserId());
					usedProducts.setDesignId(designPlanId);
					usedProducts.setProductId(productId);
					usedProducts.setNuma1(Utils.getIntValue(materialPicId));
					usedProducts.setPlanProductId(planProductId);
					usedProducts.setPosIndexPath(designPlanProduct.getPosIndexPath());
					//usedProducts.setAtt1(context);
					/**sysSave(usedProducts, request);*/
					usedProducts.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
					int id = usedProductsService.add(usedProducts);
					if (Utils.enableRedisCache()) {
						UsedProductsCacher.remove(id);
					}

					/*将该样板房的 应使用产品位置 坐标信息保存下到本地，上传到自定路径。，并且保存*/
					//配置内容存储到文件中
					/**TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。**/
					Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
					boolean uploadFtpFlag = false;
					//已使用配置内容存储路径
					String usedConfigPath = Utils.getValueByFileKey(ResProperties.RES, ResProperties.DESIGNPLAN_USEDCONFIG_FILEKEY, "/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/usedConfig/");
					usedConfigPath = Utils.replaceDate(usedConfigPath);
					//文件名称
					String fileName = id + ".txt";
					//先把文件保存到本地
					String filePath = Utils.getAbsolutePath(usedConfigPath + fileName, Utils.getAbsolutePathType.encrypt);
					uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath, context);
					//获取文件大小
					Map map = FileUploadUtils.getMap(new File(filePath), "design.designPlan.usedConfig.upload.path", false);
					String fileSize = map.get(FileModel.FILE_SIZE).toString();
					if (uploadFtpFlag) {
						//上传方式为2或者3表示文件在ftp上
						if (ftpUploadMethod == 2) {
							uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
						} else if (ftpUploadMethod == 3) {
							uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
							//删除本地文件
						}
					}
					/*已使用产品资源记录    start*/
					ResUsedProducts resUsedProducts = new ResUsedProducts();
					resUsedProducts.setFileCode(designPlanProduct.getSysCode());
					resUsedProducts.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
					resUsedProducts.setFileOriginalName(fileName);
					//resUsedProducts.setFileType(fileType);
					resUsedProducts.setFileSize(fileSize);
					resUsedProducts.setFileSuffix(fileName.substring(fileName.lastIndexOf(".")));
					//resUsedProducts.setFileLevel();
					resUsedProducts.setFilePath(usedConfigPath + fileName);
					//resUsedProducts.setFileDesc(fileDesc);
					//resUsedProducts.setFileOrdering(fileOrdering);
					resUsedProducts.setFileKey("design.designPlan.usedConfig");
					resUsedProducts.setBusinessId(id);//已使用产品id
					resUsedProducts.setGmtModified(new Date());
					//resUsedProducts.setRemark(remark);
					sysSave(resUsedProducts, request);
					int resId = resUsedProductsService.add(resUsedProducts);
					//回填fileId
					UsedProducts products = usedProductsService.get(id);
					products.setResUsedId(resId);
					usedProductsService.update(products);
				}
				/*已使用产品资源记录    end*/
				/*解除planProductId设计产品的组(解组)*/
				/*String planGroupId=designPlanProduct.getPlanGroupId();
				if(StringUtils.isNotBlank(planGroupId)&&!StringUtils.equals("0", planGroupId)){
					designPlanProductService.relieveGroupByPlanIdAndplanGroupId(designPlanId,planGroupId);
					logger.warn("替换产品操作导致解组:时间:"+Utils.getTimeStr()+";设计方案id:"+designPlanId+";planProductId:"+planProductId+";替换为:"+productId);
				}*/
				/*解除planProductId设计产品的组(解组)->end*/
			} catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 更新splitTextureInfo信息
	 * @author huangsongbo
	 * @param splitTextureDTOList
	 * @param str 格式:key:textureId
	 * @return
	 */
	private List<SplitTextureInfoDTO> updateSplitTexture(List<SplitTextureInfoDTO> splitTextureDTOList, String str) {
		if(str.indexOf(":")==-1)
			return splitTextureDTOList;
		String[] strs=str.split(":");
		if(strs.length<2)
			return splitTextureDTOList;
		String key=strs[0];
		String value=strs[1];
		for(SplitTextureInfoDTO splitTextureDTO:splitTextureDTOList){
			if(StringUtils.equals(key, splitTextureDTO.getKey())){
				splitTextureDTO.setDefaultId(Integer.valueOf(value));
				break;
			}
		}
		return splitTextureDTOList;
	}

	public boolean updateSameProductTextureInfo(Integer designPlanId,Integer planProductId,BaseProduct newProduct,String str){
		try{
			DesignPlanProduct designPlanProduct  = new DesignPlanProduct();
			designPlanProduct.setIsDeleted(0);
			designPlanProduct.setProductId(newProduct.getId());
			designPlanProduct.setPlanId(designPlanId);
			List<DesignPlanProduct> list = designPlanProductMapper.selectList(designPlanProduct);
			if(Lists.isNotEmpty(list)){
				for(DesignPlanProduct designProduct : list ){
					if(designProduct.getId().intValue() == planProductId.intValue()){
						continue;
					}
					String oldSplitTexturesChooseInfo="";
					if(StringUtils.isBlank(designPlanProduct.getSplitTexturesChooseInfo())){
						/*从产品表中查找默认材质信息*/
						oldSplitTexturesChooseInfo = newProduct.getSplitTexturesInfo();
					}else{
						oldSplitTexturesChooseInfo=designPlanProduct.getSplitTexturesChooseInfo();
					}
					DesignPlanProduct planProduct = new DesignPlanProduct();
					planProduct.setId(designProduct.getId());
					JSONArray jsonArray=JSONArray.fromObject(oldSplitTexturesChooseInfo);
					@SuppressWarnings({ "unchecked", "deprecation" })
					List<SplitTextureInfoDTO> splitTextureDTOList=(List<SplitTextureInfoDTO>) JSONArray.toList(jsonArray, SplitTextureInfoDTO.class);
					splitTextureDTOList = this.updateSplitTexture(splitTextureDTOList,str);
					JSONArray jsonArray2=JSONArray.fromObject(splitTextureDTOList);
					planProduct.setSplitTexturesChooseInfo(jsonArray2.toString());
					planProduct.setGmtModified(new Date());
					designPlanProductMapper.updateByPrimaryKeySelective(planProduct);
				}
			}
		}catch (Exception e){
			logger.debug(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 新增设计方案产品
	 * @param desPlan 方案对象 
	 * @param designPlanId 方案ID
	 * @param groupProductJson 组合产品json
	 * @param groupId 组合产品Id
	 * @param planProductId 方案产品Id
	 * @param planGroupId 方案组合唯一标识
	 * @return boolean
	 */
	@Override
	public List<UnityPlanProduct> batchSaveDesignPlan(DesignPlan desPlan,Integer designPlanId,String groupProductJson,Integer groupId,
			Integer planProductId, String materialPicId,String context,String planGroupId,LoginUser loginUser, Integer groupType,Integer isStandard,
			String center,String regionMark,Integer styleId,String measureCode)throws InvocationTargetException, IllegalAccessException {
		List<UnityPlanProduct> list = new ArrayList<>();
		// 记录主产品的initProductId(初始化白膜产品id)
		Integer mainProductInitProductId = null;
		try{
			//组合替换组合更新方案产品列表，先删（逻辑删除）在增
			if (StringUtils.isNotEmpty(planGroupId) && planProductId == null || StringUtils.isNotEmpty(planGroupId) && planProductId == 0) {
				DesignPlanProduct designPlanProduct = new DesignPlanProduct();
				designPlanProduct.setPlanId(designPlanId);
//				designPlanProduct.setProductGroupId(groupId);
				designPlanProduct.setPlanGroupId(planGroupId);
				/*应对替换结构而做的修改20161210*/
				if(groupType!=null&&groupType>1){
					designPlanProduct.setGroupType(groupType);
				}
				/*应对替换结构而做的修改20161210->end*/
				designPlanProduct.setIsDeleted(0);
				List<DesignPlanProduct> dppList = designPlanProductService.getList(designPlanProduct);
				if( Lists.isNotEmpty(dppList) ){
					for(DesignPlanProduct planProduct : dppList){
//						planProduct.setIsDeleted(1);
						// 记录主产品的initProductId(初始化白膜产品id) ->start
						if(planProduct.getIsMainProduct() != null && 1 == planProduct.getIsMainProduct().intValue()) {
							mainProductInitProductId = planProduct.getInitProductId();
						}
						// 记录主产品的initProductId(初始化白膜产品id) ->end
						designPlanProductService.delete(planProduct.getId());
						DesignPlanProductCacher.remove(planProduct.getId());
					}
				}
				//保存组合产品到设计方案产品列表中
				list = batchSavePlanProducts(desPlan,groupProductJson,designPlanId,context,loginUser,groupType,isStandard,center,regionMark,styleId,measureCode, mainProductInitProductId);
			}
			//组合替换单品
			if( planProductId != null && planProductId > 0 &&  StringUtils.isEmpty(planGroupId) || planProductId != null && planProductId > 0 && StringUtils.isEmpty(planGroupId) ){
				DesignPlanProduct planProduct = designPlanProductService.get(planProductId);
				if( planProduct != null ){
//					planProduct.setIsDeleted(1);
					designPlanProductService.delete(planProduct.getId());
					DesignPlanProductCacher.remove(planProduct.getId());
				}
				//保存组合产品到设计方案产品列表中
				list = batchSavePlanProducts(desPlan,groupProductJson,designPlanId,context,loginUser,groupType,isStandard,center,regionMark,styleId,measureCode, mainProductInitProductId);
			}
			//该条件都为空，则视为新增组合产品
			if(StringUtils.isEmpty(planGroupId) && planProductId == null){
				//保存组合产品到设计方案产品列表中
				list = batchSavePlanProducts(desPlan,groupProductJson,designPlanId,context,loginUser,groupType,isStandard,center,regionMark,styleId,measureCode, mainProductInitProductId);
			}
			//根据产品列表更新方案装修风格
			List<BaseProduct> productStyleList = baseProductService.getProductStyle(designPlanId);
			if( CustomerListUtils.isNotEmpty(productStyleList) ){
				DesignPlan designPlan = new DesignPlan();
				designPlan.setId(designPlanId);
				if( productStyleList.size()==1 ){
					Integer style= Utils.getnotNullInt(productStyleList.get(0));
					designPlan.setDesignStyleId(style);
			    }else{
					designPlan.setDesignStyleId(8);
				}
				//更新设计方案设置是否变更为1，是否装修过1，是否公开为0，
				designPlan.setIsChange(1);
				designPlan.setIsDecorated(1);
				designPlan.setIsOpen(0);
				designPlan.setSceneModified(Utils.getCurrentTimeMillis());//设计方案的配置文件发生改变要更新时间戳
				sysSave(designPlan,loginUser);
				designPlanMapper.updateByPrimaryKeySelective(designPlan);
				if(Utils.enableRedisCache()){
					DesignCacher.removePlan(designPlan.getId());
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
		return list;
	}
	
	/**
	 * 保存新的结构/组合
	 * @param desPlan
	 * @param groupProductJson
	 * @param designPlanId
	 * @param context
	 * @param groupType
	 * @param mainProductInitProductId 替换前的组合的主产品的initProductId add by huangsongbo 2017.11.14
	 * @return
	 */
	private List<UnityPlanProduct> batchSavePlanProducts(DesignPlan desPlan,String groupProductJson,
			Integer designPlanId,String context,LoginUser loginUser, Integer groupType,Integer isStandard,
			String center,String regionMark,Integer styleId,String measureCode, Integer mainProductInitProductId){
		groupType=groupType==null?0:groupType;
		List<UnityPlanProduct> unityPlanProductList = new ArrayList<UnityPlanProduct>();
		UnityPlanProduct unityPlanProduct = null;
		//将字符串转换成jsonObject对象 //需新增的产品信息
	   JSONArray groupProductArray = JSONArray.fromObject(groupProductJson);
	   if( groupProductArray != null && groupProductArray.size() > 0 ){
		   String dateTime = Utils.getCurrentDateTime(Utils.DATETIMESSS);
		   Integer arrayIds[] = new Integer[groupProductArray.size()];
		   for(int i=0;i<groupProductArray.size();i++){
			   JSONObject jsonObj = (JSONObject)groupProductArray.get(i);
			   Integer gpId = (Integer)jsonObj.get("groupId");
			   Integer productId = (Integer)jsonObj.get("productId");
			   Integer isMainProduct = (Integer)jsonObj.get("isMainProduct");
			   String posIndexPath = (String)jsonObj.get("posIndexPath");
			   String posName = (String)jsonObj.get("posName");
			   Integer chartletProductModelId = jsonObj.getInt("textureProductModelId");/**贴图产品的模型id*/
			   Integer  productIndex =  (Integer)jsonObj.get("productIndex");
			   String splitTexturesChooseInfo = null;
			   if(jsonObj.containsKey("splitTexturesChooseInfo")) {
				   splitTexturesChooseInfo = jsonObj.getString("splitTexturesChooseInfo");
				   if(StringUtils.equals("null", splitTexturesChooseInfo.trim())) {
					   splitTexturesChooseInfo = "";
				   }
			   }
			   DesignPlanProduct planProduct = new DesignPlanProduct();
			   planProduct.setGroupType(groupType);
			   // 组合产品的唯一标识(eg:区分床头柜是左柜子还是右柜子)
			   if(jsonObj.containsKey("groupProductUniqueId")) {
				   String groupProductUniqueId = jsonObj.getString("groupProductUniqueId");
				   planProduct.setGroupProductUniqueId(groupProductUniqueId);
			   }
			   if( chartletProductModelId != null ){
				   //替换结构组合
				   BaseProduct baseProduct = baseProductService.get(productId);
				   if( baseProduct != null && StringUtils.isNotBlank(baseProduct.getBmIds()) ){
					   planProduct.setInitProductId(Utils.getIntValue(baseProduct.getBmIds()));
				   }else{
					   planProduct.setInitProductId(chartletProductModelId);
				   }
			   }
			   if(new Integer(1).equals(groupType)){
			   	   BaseProduct baseProduct = baseProductService.get(productId);
			   	   if( baseProduct != null && StringUtils.isNotBlank(baseProduct.getBmIds()) ){
					   planProduct.setInitProductId(Utils.getIntValue(baseProduct.getBmIds()));
				   }else{
					   planProduct.setInitProductId(productId);
				   }
				   //结构保存中心点、区域标识、款式、尺寸代码、是否是标准
				   planProduct.setIsStandard(isStandard);
				   planProduct.setCenter(center);
				   planProduct.setMeasureCode(measureCode);
				   planProduct.setRegionMark(regionMark);
				   planProduct.setStyleId(styleId);
			   }
			   if (planProduct.getInitProductId() == null || planProduct.getInitProductId() < 1) {
					planProduct.setInitProductId(productId);
			   }
			   planProduct.setProductIndex(productIndex);
			   planProduct.setIsDeleted(0);
			   planProduct.setPlanId(designPlanId);
			   planProduct.setPosIndexPath(posIndexPath);
			   planProduct.setPosName(posName);
			   planProduct.setProductId(productId);
			   planProduct.setPlanProductId(0);
			   planProduct.setIsHide(0);
			   planProduct.setDisplayStatus(0);
			   planProduct.setProductGroupId(gpId);
			   planProduct.setIsMainProduct(isMainProduct);
			   planProduct.setPlanGroupId(gpId+"_"+dateTime);
			   String productSequence = com.nork.common.util.StringUtils.replaceString(planProduct.getPosIndexPath(),"/");
			   planProduct.setProductSequence(productSequence);
			   
			   // 保存组合默认选择的多材质信息 add by huangsongbo 2017.12.7
			   planProduct.setSplitTexturesChooseInfo(splitTexturesChooseInfo);
			   
			   sysSave(planProduct, loginUser);
			   // 设置主产品的initProductId
			   if(planProduct.getIsMainProduct() != null && 1 == planProduct.getIsMainProduct() && mainProductInitProductId != null) {
				   planProduct.setInitProductId(mainProductInitProductId);
			   }
			   int designPlanProductId = designPlanProductService.add(planProduct);
			   arrayIds[i] = designPlanProductId;//把新增的方案产品Id存到里面。
			   DesignPlanProductCacher.remove(designPlanProductId);
			   unityPlanProduct = new UnityPlanProduct();
			   unityPlanProduct.setPlanProductId(designPlanProductId);
			   /*unityPlanProduct.setProductGroupId(gpId);
			   unityPlanProduct.setIsMainProduct(isMainProduct);
			   unityPlanProduct.setPlanGroupId(gpId+"_"+dateTime);*/
			   unityPlanProduct.setProductId(productId);
			   unityPlanProduct.setPosIndexPath(posIndexPath);
			   unityPlanProduct.setPosName(posName);
			   unityPlanProduct.setTemplateProductId(String.valueOf(productId));
			   /*设置结构数据*/
				/*处理结构返回格式*/
				unityPlanProduct.setGroupType(groupType);
			   unityPlanProduct.setCenter(center);
				Map<Integer,Integer> memoryMap=new HashMap<Integer, Integer>();
				if (isStandard != null && new Integer(1).equals(isStandard)) {
					/*通用结构*/
					unityPlanProduct.setIsMainProduct(new Integer(0));
					unityPlanProduct.setProductGroupId(new Integer(0));
					unityPlanProduct.setPlanGroupId("");
					unityPlanProduct.setPlanStructureId(planProduct.getPlanGroupId());
					unityPlanProduct.setProductStructureId(gpId);
					unityPlanProduct.setStyleId(styleId);
					unityPlanProduct.setMeasureCode(measureCode);
					unityPlanProduct.setRegionMark(regionMark);
					unityPlanProduct.setIsStandard(isStandard);
				}else if(groupType==null||new Integer(0).equals(groupType)){
					/*组合*/
					unityPlanProduct.setProductGroupId(gpId);
					unityPlanProduct.setIsMainProduct(isMainProduct);
					unityPlanProduct.setPlanGroupId(planProduct.getPlanGroupId());
					unityPlanProduct.setPlanStructureId("");
					/*识别是否是结构组合->是->得到对应结构的id*/
					/*先从memoryMap中找(防止重复访问数据库)*/
					if(gpId>0){
						if(memoryMap.containsKey(gpId)){
							unityPlanProduct.setProductStructureId(memoryMap.get(gpId));
						}else{
							/*如果该组合是结构组合,找对应的结构id*/
							GroupProduct groupProduct=groupProductService.get(gpId);
							if(groupProduct!=null){
								Integer structureId=groupProduct.getStructureId();
								if(structureId!=null&&structureId>0){
									String PlanGroupId=unityPlanProduct.getPlanGroupId();
									if(StringUtils.isNotBlank(PlanGroupId)){
										String[] strs=PlanGroupId.split("_");
										unityPlanProduct.setPlanStructureId(structureId+"_"+strs[1]);
									}
									unityPlanProduct.setProductStructureId(structureId);
									memoryMap.put(gpId, structureId);
								}
							}
						}
					}
					/*识别是否是结构组合->是->得到对应结构的id->end*/
					if(unityPlanProduct.getProductStructureId()!=null&&unityPlanProduct.getProductStructureId()>0){
						
					}else{
						unityPlanProduct.setProductStructureId(new Integer(0));
					}
				}else if(new Integer(1).equals(groupType)){
					/*结构*/
					unityPlanProduct.setIsMainProduct(new Integer(0));
					unityPlanProduct.setProductGroupId(new Integer(0));
					unityPlanProduct.setPlanGroupId("");
					unityPlanProduct.setPlanStructureId(planProduct.getPlanGroupId());
					unityPlanProduct.setProductStructureId(gpId);
				}
				/*处理结构返回格式->end*/
			   /*设置结构数据->end*/
			   unityPlanProductList.add(unityPlanProduct);
			   
			   //存储产品到已使用列表
			   if (groupType != 1) {
				   UsedProducts usedProducts = new UsedProducts();
				   usedProducts.setCreator(desPlan.getCreator());
				   usedProducts.setUserId(desPlan.getUserId());
				   usedProducts.setDesignId(designPlanId);
				   usedProducts.setProductId(productId);
				   usedProducts.setGroupId(gpId);
				   sysSave(usedProducts, loginUser);
				   int id = usedProductsService.add(usedProducts);
				   UsedProductsCacher.remove(id);

				   //配置内容存储到文件中
				   /**TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。**/
				   Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
				   boolean uploadFtpFlag = false;

				//已使用配置内容存储路径
//				String usedConfigPath = Utils.getValue("design.designPlan.usedConfig.upload.path","/design/designPlan/[code]/usedConfig/");
//				usedConfigPath = usedConfigPath.replace("[code]",desPlan.getPlanCode());
				/*String usedConfigPath = Utils.getValue("design.designPlan.usedConfig.upload.path","/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/usedConfig/");*/
				String usedConfigPath = Utils.getValueByFileKey(ResProperties.RES, ResProperties.DESIGNPLAN_USEDCONFIG_FILEKEY, "/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/usedConfig/");
				usedConfigPath = Utils.replaceDate(usedConfigPath);
				//文件名称
				String fileName = id+".txt";
				//先把文件保存到本地
				/*String filePath = Constants.UPLOAD_ROOT + usedConfigPath + fileName;*/
				String filePath = Utils.getAbsolutePath(usedConfigPath + fileName, Utils.getAbsolutePathType.encrypt);
				uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,context);
				if( uploadFtpFlag ) {
					//上传方式为2或者3表示文件在ftp上

					if (ftpUploadMethod == 2) {
						uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
					} else if (ftpUploadMethod == 3) {

						uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
						//删除本地文件
						/*FileUploadUtils.deleteDir(new File(Constants.UPLOAD_ROOT + usedConfigPath).getParentFile());*/

					   }
				   }
			   }
		   }
		   if( Lists.isNotEmpty(unityPlanProductList) ){
			   for(UnityPlanProduct upp : unityPlanProductList){
				   if(new Integer(1).equals(upp.getGroupType()))
					   upp.setPlanProductStructureIds(arrayIds);
				   else{
					   upp.setPlanProductGroupIds(arrayIds);
					   upp.setPlanProductStructureIds(arrayIds);
				   }
			   }
		   }
	   }
	   return unityPlanProductList;
	}
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResUsedProducts model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlan model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}
		       /* Map<String,Object> map1=(Map<String, Object>) request.getAttribute("AuthorizationData");
	                String uid=map1.get("uid").toString();
	                String signflat=request.getParameter("signflat");
	                String key=signflat+uid;
	                LoginUser loginUser =(LoginUser) JedisUserUtils.getObject(key);*/
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	private void sysSave(DesignPlan model, LoginUser loginUser) {
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UsedProducts model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}
	private void sysSave(UsedProducts model,LoginUser loginUser){
		if(model != null){
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	/**
	 * 删除设计方案
	 * @param designPlan
	 * @return boolean
	 */
	public boolean delPlan(DesignPlan designPlan) {
		 try{
			if (designPlan.getModelU3dId() != null) {
				ResModel resModel = resModelService.get(designPlan.getModelU3dId());
				if (resModel != null) {
					/**查询该数据是否有共享一个模型 ，如果有只删除数据库数据，不删除物理文件 **/
					int picPathCount = resModelService.modelPathCount(resModel.getModelPath());
					if( picPathCount > 1 ){
						resModelService.delete(resModel.getId());
						ResourceCacher.removeResModel(resModel.getId());
					}else{
						/*删除物理文件*/
						FileUploadUtils.deleteFile(resModel.getModelPath());
						resModelService.delete(resModel.getId());
						ResourceCacher.removeResModel(resModel.getId());
					}
				}
		    }
			
			if (designPlan.getConfigFileId() != null && designPlan.getConfigFileId()>0) {
				//ResFile resFile = resFileService.get(designPlan.getConfigFileId());
				ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());
				if (resDesign != null) {
					/*查询该数据是否有共享一个文件 ，如果有只删除数据库数据，不删除物理文件 **/
					int picPathCount = resDesignService.filePathCount(resDesign.getFilePath());
					if( picPathCount > 1 ){
						resDesignService.delete(resDesign.getId());
						ResourceCacher.removeResFile(resDesign.getId());
					}else{
						/*删除物理文件*/
						FileUploadUtils.deleteFile(resDesign.getFilePath());
						resDesignService.delete(resDesign.getId());
						if(Utils.enableRedisCache()){
							ResourceCacher.removeResFile(resDesign.getId());
						}
					}
				}
			}
			 //删除方案订单信息
			 designPlanCustomizedProductOrderService.deleteByPlanId(designPlan.getId());
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<DesignPlan> getPlanListByAreas(DesignPlan designPlan) {
		return designPlanMapper.getPlanListByAreas(designPlan);
	}
	
	/**
	 * 渲染任务
	 * @return
	 * @throws Exception 
	 */
	public Object renderTask() throws Exception{
        //允许执行的次数
        Integer allowExecCount = 2;
        
        if(JOB_LOG_FLAG)
           logger.debug("开始执行渲染任务。。。");
        
        initRender();
        
        try{
            // 检查配置中的渲染机是否有正在执行中的任务
            SysTaskSearch sysTaskSearch = new SysTaskSearch();
            for( String renderServer : renderServersMap.keySet() ){
                sysTaskSearch.setRenderServer(renderServer);
                sysTaskSearch.setState(SysTaskStatus.EXECUTING); // 执行中
                // 检查是否有执行中的任务
                Integer count = sysTaskService.getCount(sysTaskSearch);
                if( count > 0 ){
                    if(JOB_LOG_FLAG)
                        logger.debug("还有渲染任务正在执行中.  continue this task!!!");
                    continue;
                }

                //查询出需要本机执行的渲染任务
                SysTask sysTask = new SysTask();
                sysTask.setState(SysTaskStatus.WAIT_EXECUTE);
                sysTask.setTaskServer(TASK_SERVER_HOSTS);
                sysTask.setRenderWay("local");
                sysTask.setOrder(" priority,gmt_create ");
                sysTask.setOrderNum(" asc ");
                List<SysTask> taskList = new ArrayList<>();
                try{
                    taskList = sysTaskService.getList(sysTask);
                }catch(Exception e){
                    logger.error("查询需要本机执行的渲染任务异常："+e);
                }
                if(taskList != null && taskList.size() > 0){
                    SysTask renderTask = taskList.get(0);
                    // 先把当前处理中的任务状态变更为处理中，避免该数据被执行多次
                    if(renderTask != null){
                        SysTask newSysTask = new SysTask();
                        newSysTask.setId(renderTask.getId());
                        newSysTask.setState(SysTaskStatus.EXECUTING);
                        sysTaskService.update(newSysTask);
                    }

                    renderTask.setExecCount(renderTask.getExecCount()+1);//执行次数加1
                    String params = "";
                    String paramFilePath = renderTask.getParams();
                    try{
                        if( StringUtils.isNotBlank(paramFilePath) ){
                            Integer uploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
                            if( uploadMethod == 1 ){
                                /*params = FileUploadUtils.getFileContext(Constants.UPLOAD_ROOT + paramFilePath);*/
                            	params = FileUploadUtils.getFileContext(Utils.getAbsolutePath(paramFilePath, Utils.getAbsolutePathType.encrypt));
                            }else{
                                params = FtpUploadUtils.getFileContext(paramFilePath);
                            }
                        }
                        if( StringUtils.isNotBlank(params) ){
                            RenderConfig renderConfig = renderServersMap.get(renderTask.getRenderServer());
                            if(renderConfig==null){
                                logger.debug("renderServersMap="+renderServersMap.toString()+";renderTask.getRenderServer()="+renderTask.getRenderServer());
                                //renderTask.setRemark("渲染配置文件未找到");
                                renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，执行失败！渲染配置文件未找到.");
                                if(renderTask.getExecCount()>allowExecCount){
                                    renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);
                                }else{
                                    renderTask.setState(SysTaskStatus.WAIT_EXECUTE);
                                }
                                sysTaskService.update(renderTask);
                                return null;
                            }
                            // 解压文件存储方式
                            String storageType = renderConfig.getStorageType();
                            // 生成渲染配置文件
                            boolean dataFlag = false;
                            if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
                                dataFlag = RenderUtil.replaceFile(params,renderConfig);
                            }else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) || RenderUtil.STORAGE_TYPE_RUIYUN.equals(storageType) ){
                                dataFlag = RenderUtil.createDataFile(params, renderConfig.getRenderRoot());
                            }
                            if( !dataFlag ){
                                if(JOB_LOG_FLAG) {
                                    logger.debug(Utils.getLineNumber()+"生成渲染配置文件Data.txt失败......");
                                }
                                renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，执行失败！生成渲染配置文件Data.txt失败.");
                                if(renderTask.getExecCount()>allowExecCount){
                                    renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);
                                }else{
                                    renderTask.setState(SysTaskStatus.WAIT_EXECUTE);
                                }
                                sysTaskService.update(renderTask);
                                return null;
                            }
                            //解压渲染所需文件
                            /*2016.11.07修改为预解压*/
                            if(JOB_LOG_FLAG) {
                                logger.debug(Utils.getLineNumber()+"开始解压渲染所需文件，planId：" + renderTask.getBusinessId() + ";code=" + renderTask.getBusinessCode());
                            }
                            Integer planId=renderTask.getBusinessId();
                            if(renderTask.getPlanId()!=null&&renderTask.getPlanId()>0)
                                planId=renderTask.getPlanId();
                            //JSONObject jsonObject = (JSONObject)startRender(renderTask.getBusinessId(), renderTask.getBusinessCode(),renderConfig,null);
                            JSONObject jsonObject = (JSONObject)startRender2(planId, renderTask.getBusinessCode(),renderConfig);
                            if( !(Boolean)jsonObject.get("success") ){
                                if( renderTask.getExecCount() > allowExecCount ){
                                    renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
                                    //失败之后发送信息
                                    if(renderTask.getExecCount().intValue() >= allowExecCount.intValue()){
                                        // 发送消息
                                        sendRenderMessage(renderTask,1,null);
                                    }
                                }else{
                                    renderTask.setState(SysTaskStatus.WAIT_EXECUTE);
                                }
                                renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，执行失败！" + jsonObject.get("message")+"！]");
                                renderTask.setGmtModified(new Date());
                                sysTaskService.update(renderTask);
                                return null;
                            }
                            /*2016.11.07修改为预解压->end*/
                            String osType = FileUploadUtils.SYSTEM_FORMAT;
    //                      String occu = Utils.getValue("design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
                            String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
                            String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
                            if( "windows".equals(osType) ){
                                occu = occu.replace("/", "\\");
                            }

                            try{
                                //调用渲染
                                if(JOB_LOG_FLAG) {
                                    logger.info("调用渲染服务器，发送渲染指令。。。");
                                }
                                // 生成3dmax渲染脚本
                                logger.debug(Utils.getLineNumber()+"----------------渲染方式："+renderTask.getRenderWay());
                                JSONObject createScriptResult = RenderUtil.createScript(renderConfig, renderTask.getBusinessCode(),params);
                                boolean flag = false;
                                if( createScriptResult != null && createScriptResult.getBoolean("success") ){
                                    flag = RenderUtil.sendRequest("open:"+renderTask.getBusinessCode(),renderTask.getRenderServer());
                                }else{
                                    logger.debug(Utils.getLineNumber()+"["+renderTask.getBusinessCode()+"]生成脚本文件失败！"+createScriptResult.get("message"));
                                }
    //                          boolean flag = RenderUtil.sendRequest("open",renderTask.getRenderServer());
                                if(flag){
                                    //状态修改为执行中
                                    renderTask.setState(SysTaskStatus.EXECUTING);
                                    renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark()) ? "" : renderTask.getRemark() + "\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，已将渲染指令提交给渲染服务器，等待渲染执行结果返回。。。]");
                                    renderTask.setGmtModified(new Date());
                                    renderTask.setInstructionTime(new Date());// 记录发送指令的时间
                                    sysTaskService.update(renderTask);
                                    RenderUtil.flag = false;
                                }else{
                                    renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
                                    renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark()) ? "" : renderTask.getRemark() + "\r\n") + "[无法连接渲染服务器或给服务器" + renderTask.getRenderServer() + "发送open命令失败，任务终止]");
                                    renderTask.setGmtModified(new Date());
                                    sysTaskService.update(renderTask);

                                    //失败之后发送信息
                                    logger.debug(Utils.getLineNumber()+"第"+renderTask.getExecCount()+"次执行渲染任务，发送渲染指令失败！");
                                    logger.debug(Utils.getLineNumber()+"当前为第"+renderTask.getExecCount()+"次执行，共允许执行"+allowExecCount+"次！");
                                    logger.debug(Utils.getLineNumber()+"给app发送websoket消息：");
                                    if(renderTask.getExecCount().intValue() >= allowExecCount.intValue()){
                                        // 发送消息
                                        sendRenderMessage(renderTask,1,null);
                                    }

                                    //删除目录
                                    String renderPath = renderConfig.getRenderRoot() + occu + scriptPath + renderTask.getBusinessCode();
                                    if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
                                        logger.warn(Utils.getLineNumber()+"删除目录："+"/"+renderPath.replaceAll("\\\\","/") + renderTask.getBusinessCode());
                                        FtpUploadUtils.deleteDir("/"+renderPath.replaceAll("\\\\","/"),renderTask.getBusinessCode(),renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
                                    }else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
                                        logger.warn(Utils.getLineNumber() + "删除目录：" + renderPath);
                                        RenderUtil.deleteFile(renderPath);
                                    }

                                    return null;
                                }
                            }catch (IOException e){
                                //删除解压出来的文件
                                e.printStackTrace();
                                //删除目录
                                String renderPath = renderConfig.getRenderRoot() + occu + scriptPath + renderTask.getBusinessCode();
                                if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
                                    logger.warn(Utils.getLineNumber()+"删除目录："+"/"+renderPath.replaceAll("\\\\","/") + renderTask.getBusinessCode());
                                    FtpUploadUtils.deleteDir("/"+renderPath.replaceAll("\\\\","/"),renderTask.getBusinessCode(),renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
                                }else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
                                    logger.warn(Utils.getLineNumber() + "删除目录：" + renderPath);
                                    RenderUtil.deleteFile(renderPath);
                                }

                                //保存执行日志
                                if(renderTask.getExecCount() > allowExecCount){
                                    renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
                                }else{
                                    renderTask.setState(SysTaskStatus.WAIT_EXECUTE);//更新状态等待中，等待再次执行 不超过三次
                                }
                                renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n")+"["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]:第" + renderTask.getExecCount() + "次渲染，执行失败1！");
                                renderTask.setGmtModified(new Date());
                                sysTaskService.update(renderTask);

                                return null;
                            }
                        }else{
                            if(renderTask.getExecCount() > allowExecCount){
                                renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
                            }else{
                                renderTask.setState(SysTaskStatus.WAIT_EXECUTE);//更新状态等待中，等待再次执行 不超过三次
                            }
                            renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n")+"["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]:第" + renderTask.getExecCount() + "次渲染，执行失败！没有渲染参数2！");
                            renderTask.setGmtModified(new Date());
                            sysTaskService.update(renderTask);
                            logger.debug(Utils.getLineNumber()+"渲染失败！没有渲染参数。param path = " + renderTask.getParams());
                            return null;
                        }
                    }catch(Exception e){
                        logger.error(Utils.getLineNumber()+e.getMessage());
                        renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);
                        renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n")+"["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]:第" + renderTask.getExecCount() + "次渲染，执行异常！");
                        renderTask.setGmtModified(new Date());
                        sysTaskService.update(renderTask);
                        return null;
                    }
                }else{
                    if(JOB_LOG_FLAG)
                        logger.info("没有需要执行的渲染任务。。。");
                }
            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        if(JOB_LOG_FLAG)
           logger.info("end render task!!!");
        return null;
    }

	/**
	 * 复制渲染模型文件到指定渲染目录
	 * @author huangsongbo
	 * @param planId
	 * @param rootDirName
	 * @param renderConfig
	 * @return
	 */
	public Object startRender2(Integer planId,String rootDirName,RenderConfig renderConfig){
		String storageType = renderConfig.getStorageType();//local
		String renderServer = renderConfig.getRenderRoot();//F:\chengdu\MaxRender
		JSONObject jsonObject = new JSONObject();
		long beginTime =  System.currentTimeMillis();
		if( planId == null ){
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message","设计方案ID不能为空");
			return jsonObject;
		}
		DesignPlan designPlan = designPlanService.get(planId);
		if( designPlan == null ){
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message", "没有找到ID为 " + planId + " 的设计方案");
			return jsonObject;
		}
		String osType = FileUploadUtils.SYSTEM_FORMAT;//windows
		String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");///MaxScript/
		String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");///MaxRender
		String renderPath = "/".equals(renderServer)?"":renderServer + occu + scriptPath + rootDirName + "/" + rootDirName;//F:\chengdu\MaxRender/MaxRender/MaxScript/C07_0022_001_621538_20161026151746469768/C07_0022_001_621538_20161026151746469768
		String deleteRenderPath = renderServer + occu + scriptPath;//F:\chengdu\MaxRender/MaxRender/MaxScript/
		String renderResourcesPath=Utils.getPropertyName("render", "render.renderResourcesPath", "/home/nork/MaxRender/resources");//F:\chengdu\MaxRender\resources
		occu=Utils.dealWithPath(occu, osType);
		scriptPath=Utils.dealWithPath(scriptPath, osType);
		renderPath=Utils.dealWithPath(renderPath, osType);
		deleteRenderPath=Utils.dealWithPath(deleteRenderPath, osType);
		renderResourcesPath=Utils.dealWithPath(renderResourcesPath, osType);
		if(designPlan.getSpaceCommonId()==null||designPlan.getSpaceCommonId()<1){
			/*spaceCommonId为null*/
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message","该设计方案中没有空间ID");
			/*删除目录*/
			deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
			return jsonObject;
		}
		SpaceCommon spaceCommon=spaceCommonService.get(designPlan.getSpaceCommonId());
		if(spaceCommon==null){
			/*空间未找到*/
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message","没有找到ID为 "+designPlan.getSpaceCommonId()+" 的空间!SpaceCommon(id="+designPlan.getSpaceCommonId()+") is null! ");
			/*删除目录*/
			deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
			return jsonObject;
		}
		if(StringUtils.isBlank(spaceCommon.getSpaceCode())){
			/*空间code为空*/
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message","空间code为空:spaceCommonId:"+spaceCommon.getIds());
			/*删除目录*/
			deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
			return jsonObject;
		}
		/*检查预解压模型目录(空间灯光模型)有没有预解压模型文件*/
		logger.debug("------开始生成空间灯光模型文件和场景模型文件(阳台)");
		String checkPath=renderResourcesPath+"/spaceCommon/"+spaceCommon.getSpaceCode();
		if(RenderUtil.STORAGE_TYPE_FTP.equals(storageType)){
			/*ftp未处理*/
			
		}else{
			File checkDir=new File(Utils.dealWithPath(checkPath, osType));//F:\chengdu\MaxRender\resources\spaceCommon\F01_0007
			logger.debug("------与解压目录，checkDir:"+Utils.dealWithPath(checkPath, osType));
			if(checkDir.exists()&&checkDir.listFiles().length>0){
				/*组装模型(复制)到渲染目录*/
				File renderPathDir=new File(renderPath);//E:\MaxRender\MaxScript\F01_0007_001_784711_20161116143831637787\F01_0007_001_784711_20161116143831637787
				try {
					//FileUtils.copyDirectoryToDirectory(checkDir, renderPathDir);
					
					logger.debug("------渲染资源目录，renderPathDir:"+renderPathDir);
					Utils.copyDirectoryToDirectory(checkDir,renderPathDir);
				} catch (IOException e) {
					logger.debug("==============拷贝文件失败:"+e.getMessage());
					jsonObject.accumulate("success",false);
					//jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir);
					jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(1)error:"+e.getMessage());
					deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
					return jsonObject;
				}
				/*组装模型(复制)到渲染目录->end*/
			}else{
				/*空间code为空*/
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message","空间模型文件未找到(可能原因:1.空间模型文件不存在;2,空间模型文件未预解压;3...):spaceCommonId:"+spaceCommon.getId());
				/*删除目录*/
				deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
				logger.debug("------空间模型文件未找到(可能原因:1.空间模型文件不存在;2,空间模型文件未预解压;3...):spaceCommonCode:"+spaceCommon.getSpaceCode());
				return jsonObject;
			}
		}
		/*检查预解压模型目录(空间灯光模型)有没有预解压模型文件->end*/
		/*检查预解压模型目录(设计方案产品)有没有预解压模型文件*/
		logger.debug("------开始生成设计方案产品模型文件");
		DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
		designPlanProductSearch.setPlanId(designPlan.getId());
		designPlanProductSearch.setIsDeleted(0);
		List<DesignPlanProduct> designPlanProductList = designPlanProductService.getList(designPlanProductSearch);
		StringBuffer productMessage = new StringBuffer();
		StringBuffer processProductMessage = new StringBuffer("=========渲染时处理的产品汇总========\r\n");
		if(designPlanProductList==null||designPlanProductList.size()<1){
			/*空间code为空*/
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message","设计方案产品未找到,planId:"+designPlan.getId());
			/*删除目录*/
			deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
			return jsonObject;
		}
		processProductMessage.append("总共需要处理"+designPlanProductList.size()+"个产品的渲染资源！\r\n");
		logger.debug("------总共需要处理"+designPlanProductList.size()+"个产品的渲染资源！");
		for(DesignPlanProduct designPlanProduct:designPlanProductList){
			BaseProduct baseProduct=baseProductService.get(designPlanProduct.getProductId());
			processProductMessage.append("开始处理产品["+baseProduct.getProductCode()+"]的渲染资源！\r\n");
			logger.debug("------开始处理产品["+baseProduct.getProductCode()+"]的渲染资源！");
			if(baseProduct==null){
				productMessage.append("产品 [productId:"+designPlanProduct.getProductId()+"] 未找到！！\r\n");
				logger.debug("------产品 [productId:"+designPlanProduct.getProductId()+"] 未找到！！");
				continue;
			}
			if(StringUtils.isBlank(baseProduct.getProductCode())){
				productMessage.append("产品 [productId:"+designPlanProduct.getProductId()+"] code不能为空！！\r\n");
				logger.debug("------产品 [productId:"+designPlanProduct.getProductId()+"] code不能为空！！");
				continue;
			}
			// 如果是白模则不处理
			logger.debug("=============================="+baseProduct.getProductCode());
			if( baseProduct.getProductCode().indexOf("baimo") > -1 ){
				logger.debug("------产品"+baseProduct.getProductCode()+"为白模，不处理。");
				continue;
			}
			boolean isHard = isHard(baseProduct);
			if(isHard){
				logger.debug("------产品为贴图产品！");
				processProductMessage.append("产品为贴图产品！\r\n");
				/*组合白膜和材质文件*/
				/*复制模型文件(从白膜)*/
				Integer baimoId=designPlanProduct.getInitProductId();
				if(baimoId==null||baimoId<1){
					productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"]对应的白膜未找到:[baimoId:"+baimoId+"] \r\n");
					logger.debug("------产品 [productCode:"+baseProduct.getProductCode()+"]对应的白膜未找到:[baimoId:"+baimoId+"]");
					continue;
				}
				BaseProduct baimoProduct=baseProductService.get(baimoId);
				if(baimoProduct==null){
					productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"]对应的白膜未找到:[baimoId:"+baimoId+"] \r\n");
					logger.debug("------产品 [productCode:"+baseProduct.getProductCode()+"]对应的白膜未找到:[baimoId:"+baimoId+"]");
					continue;
				}
				String productCheckPath=renderResourcesPath+"/baseProduct/"+baimoProduct.getProductCode();
				if(RenderUtil.STORAGE_TYPE_FTP.equals(storageType)){
					/*ftp未处理*/
					
				}else{
					File checkDir=new File(productCheckPath);
					logger.debug("------当前贴图产品对应的白模产品为：" + baimoProduct.getProductCode());
					logger.debug("------先拷贝白模产品预解压文件！");
					logger.debug("------白模产品预解压路径，checkDir:"+productCheckPath);
					
					// 如果未发现预解压文件,重新解压一遍 ->start
					if(checkDir.exists()&&checkDir.listFiles().length>0){
						
					}else{
						resModelService.decompressModel(baimoProduct.getModelId(), baimoProduct.getProductCode());
					}
					// 如果未发现预解压文件,重新解压一遍 ->end
					
					if(checkDir.exists()&&checkDir.listFiles().length>0){
						/*组装模型(复制)到渲染目录*/
						//File renderPathDir=new File(Utils.dealWithPath(renderPath+"/"+baimoProduct.getProductCode(), osType));
						File renderPathDir=new File(Utils.dealWithPath(renderPath, osType));
						try {
							//FileUtils.copyDirectoryToDirectory(checkDir, renderPathDir);
							
							logger.debug("------渲染资源目录，renderPathDir:"+renderPathDir.getPath());
							Utils.copyDirectoryToDirectory(checkDir, renderPathDir);
							processProductMessage.append("拷贝产品渲染资源["+productCheckPath+"]到["+Utils.dealWithPath(renderPath, osType)+"]\r\n");
							processProductMessage.append("处理产品多材质\r\n");
							// 多材质处理
							JSONObject textureMessageJson = processTexture(baseProduct.getProductCode(),designPlanProduct,Utils.dealWithPath(renderPath, osType));
							if( textureMessageJson != null && !textureMessageJson.getBoolean("success") && StringUtils.isNotBlank(textureMessageJson.getString("message")) ){
								productMessage.append(textureMessageJson.getString("message"));
							}
						} catch (IOException e) {
							logger.debug("==============拷贝产品预解压文件失败:"+e.getMessage());
							jsonObject.accumulate("success",false);
							//jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir);
							jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(2)error:"+e.getMessage());
							logger.debug("拷贝产品预解压文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(2)error:"+e.getMessage());
							deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
							return jsonObject;
						}
						/*组装模型(复制)到渲染目录->end*/
					}else{
						/*产品模型不存在*/
						productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"] 预解压模型文件未找到,预解压路径:"+productCheckPath+"\r\n");
						logger.debug("------产品 [productCode:"+baseProduct.getProductCode()+"] 预解压模型文件未找到,预解压路径:"+productCheckPath);
						continue;
					}
				}
				/*复制模型文件(从白膜)->end*/
				/*复制材质文件*/
				logger.debug("------拷贝贴图产品预解压文件！");
				String productCheckPath1=renderResourcesPath+"/baseProduct/"+baseProduct.getProductCode();
				if(RenderUtil.STORAGE_TYPE_FTP.equals(storageType)){
					/*ftp未处理*/
					
				}else{
					File checkDir=new File(Utils.dealWithPath(productCheckPath1,osType));
					logger.debug("------贴图产品预解压目录，checkDir:"+productCheckPath1);
					if(checkDir.exists()&&checkDir.listFiles().length>0){
						/*组装材质(复制)到渲染目录*/
						File renderPathDir=new File(Utils.dealWithPath(renderPath+"/"+baimoProduct.getProductCode(), osType));
						//File renderPathDir=new File(Utils.dealWithPath(renderPath, osType));
						try {
							//FileUtils.copyDirectoryToDirectory(checkDir, renderPathDir);
							
							logger.debug("------渲染资源目录，renderPathDir:"+renderPathDir);
							Utils.copyDirectoryToDirectory(checkDir, renderPathDir);
						} catch (IOException e) {
							logger.debug("==============拷贝贴图产品模型文件失败:"+e.getMessage());
							jsonObject.accumulate("success",false);
							//jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir);
							jsonObject.accumulate("message","拷贝贴图产品模型文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(3)error:"+e.getMessage());
							logger.debug("拷贝贴图产品模型文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(3)error:"+e.getMessage());
							deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
							return jsonObject;
						}
						/*组装材质(复制)到渲染目录->end*/
					}else{
						/*产品模型不存在*/
						productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"] 材质文件未找到,预解压路径:"+checkDir+"\r\n");
						logger.debug("产品 [productCode:"+baseProduct.getProductCode()+"] 材质文件未找到,预解压路径:"+checkDir);
						continue;
					}
				}
				/*复制材质文件->end*/
			}else{
				/*直接复制*/
				/*if(baseProduct.getModelId()==null&&baseProduct.getModelId()<1){
					productMessage.append("产品 [productId:"+designPlanProduct.getProductId()+"] 3dmax文件不能为空！！\r\n");
					continue;
				}
				ResModel resModel=resModelService.get(baseProduct.getModelId());
				if(resModel==null){
					productMessage.append("产品 [productId:"+designPlanProduct.getProductId()+"] 3dmax模型文件未找到,modelId:"+baseProduct.getModelId()+"！！\r\n");
					continue;
				}*/
				processProductMessage.append("产品为通用产品！！\r\n");
				String productCheckPath=renderResourcesPath+"/baseProduct/"+baseProduct.getProductCode();
				if(RenderUtil.STORAGE_TYPE_FTP.equals(storageType)){
					/*ftp未处理*/
					
				}else{
					File checkDir=new File(Utils.dealWithPath(productCheckPath,osType));
					logger.debug("------产品与解压目录，checkDir:"+productCheckPath);
					
					// 如果未发现预解压文件,重新解压一遍 ->start
					if(checkDir.exists()&&checkDir.listFiles().length>0){
						
					}else{
						resModelService.decompressModel(baseProduct.getModelId(), baseProduct.getProductCode());
					}
					// 如果未发现预解压文件,重新解压一遍 ->end
					
					if(checkDir.exists()&&checkDir.listFiles().length>0){
						/*组装模型(复制)到渲染目录*/
						//File renderPathDir=new File(Utils.dealWithPath(renderPath+"/"+baseProduct.getProductCode(), osType));
						File renderPathDir=new File(Utils.dealWithPath(renderPath, osType));
						String toPath=renderPathDir.getPath()+"/"+baseProduct.getProductCode()+"/texture";
						try {
							//FileUtils.copyDirectoryToDirectory(checkDir, renderPathDir);
							
							logger.debug("------渲染资源目录，renderPathDir:"+renderPathDir);
							Utils.copyDirectoryToDirectory(checkDir, renderPathDir);
							processProductMessage.append("拷贝产品渲染模型资源["+productCheckPath+"]到["+Utils.dealWithPath(renderPath, osType)+"]\r\n");
							logger.debug("------拷贝产品渲染模型资源["+productCheckPath+"]到["+Utils.dealWithPath(renderPath, osType)+"]");
							/*应对同类型新增的产品的情况,把材质文件覆盖*/
							if(checkDir.isDirectory()){
								File[] files=checkDir.listFiles();
								/*查找texture目录*/
								File fileTexture=null;
								for(File file:files){
									if(StringUtils.equals("texture", file.getName())){
										fileTexture=file;
										break;
									}
								}
								if(fileTexture!=null){
									if(baseProduct.getParentId()!=null&&baseProduct.getParentId()>0&&baseProduct.getId()!=baseProduct.getParentId()){
										BaseProduct baseProductParent=baseProductService.get(baseProduct.getParentId());
										if(baseProductParent!=null){
											toPath=renderPathDir.getPath()+"/"+baseProductParent.getProductCode()+"/texture";
											toPath=Utils.dealWithPath(toPath, osType);
											Utils.copyDirectoryToDirectory(fileTexture, new File(toPath));
											processProductMessage.append("拷贝产品渲染材质资源["+fileTexture.getPath()+"]到["+toPath+"]\r\n");
											logger.debug("------拷贝产品渲染材质资源["+fileTexture.getPath()+"]到["+toPath+"]");
										}
									}
								}
							}
							/*应对同类型新增的产品的情况,把材质文件覆盖->end*/
							// 多材质处理
							processProductMessage.append("处理产品多材质\r\n");
							logger.debug("-----处理产品多材质");
							JSONObject textureMessageJson = processTexture(baseProduct.getProductCode(),designPlanProduct,toPath);
							if( textureMessageJson != null && !textureMessageJson.getBoolean("success") ){
								productMessage.append(textureMessageJson.getString("message"));
							}
						} catch (IOException e) {
							logger.debug(Utils.getLineNumber()+"==============拷贝普通产品预解压文件失败:"+e.getMessage());
							jsonObject.accumulate("success",false);
							//jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir);
							jsonObject.accumulate("message","拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(4)error:"+e.getMessage());
							logger.debug("------拷贝文件失败:原路径:"+checkDir+";拷贝到:"+renderPathDir+"->(4)error:"+e.getMessage());
							deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
							return jsonObject;
						}
						/*组装模型(复制)到渲染目录->end*/
					}else{
						/*产品模型不存在*/
						productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"] 预解压模型文件未找到,预解压路径:"+productCheckPath+"\r\n");
						continue;
					}
				}
			}
		}
		/*检查预解压模型目录(设计方案产品)有没有预解压模型文件->end*/
		/*如果有产品压缩错误信息，则直接返回错误结果，并删除渲染文件根目录*/
		logger.debug(processProductMessage);
		if( productMessage.length() > 0 ){
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message",productMessage.toString());
			logger.debug("解压渲染文件失败：" + productMessage.toString());
			/*删除目录*/
			deleteRenderDir(storageType,deleteRenderPath,rootDirName,renderConfig);
			return jsonObject;
		}
		jsonObject.accumulate("success", true);
		jsonObject.accumulate("message", rootDirName);
		long endTime = System.currentTimeMillis();
		//////System.out.println("======================================文件解压完成，耗时:" + (endTime-beginTime)/1000+"S;\n"+"解压目录=" + renderPath);
		return jsonObject;
	}
	/**
	 * 渲染时处理多材质产品的材质
	 * @param productCode
	 * @param planProduct
	 * @return
	 */
	public List<String> processTexture2(List<String> list, String productCode,DesignPlanProduct planProduct,SysTask priorityTask){
		StringBuffer productMessage = new StringBuffer();
		if( planProduct == null ){
			return null;
		}
		String osType = FileUploadUtils.SYSTEM_FORMAT;
		try {
			String renderResourceRoot = Utils.getPropertyName("render","render.renderResourcesPath","");
			//String renderResourceRootOriginal=Utils.getPropertyName("render", "render.renderResourcesPath.original", "X:\\resources");//Max渲染机中的预解压路径
			String renderResourceRootOriginal="";//Max渲染机中的预解压路径
			// 多材质产品处理
			if (StringUtils.isNotBlank(planProduct.getSplitTexturesChooseInfo())) {
				if( planProduct.getProductId() != null){
					//针对单材质产品替换多材质产品（异类）
					BaseProduct baseProduct = baseProductService.get(planProduct.getProductId());
					if( baseProduct != null && StringUtils.isBlank(baseProduct.getSplitTexturesInfo()) ){
						return list;
					}
				}
				String splitTextureInfo = planProduct.getSplitTexturesChooseInfo();
				JSONArray textureInfoArray = JSONArray.fromObject(splitTextureInfo);
				if (textureInfoArray != null && textureInfoArray.size() > 0) {
					for (Object a : textureInfoArray) {
						JSONObject ab = JSONObject.fromObject(a);
						String textureId = ab.getString("defaultId");// 使用的材质ID
						if (StringUtils.isNotBlank(textureId)) {
							ResTexture texturePic = resTextureService.get(Integer.valueOf(textureId));
							if( texturePic != null ){
								String fileName = texturePic.getFilePath().substring(texturePic.getFilePath().lastIndexOf("/"));
								File textureFile = new File(renderResourceRoot + Utils.dealWithPath("/baseProduct/" + productCode + "/texture" + fileName, osType));
								File textureFileOriginal = new File(renderResourceRootOriginal + Utils.dealWithPath("/baseProduct/" + productCode + "/texture" + fileName, osType));
								//File textureFile = new File("X:\\resources\\baseProduct\\AE_qiangzhi_0001\\texture\\688430_20170207174024568.jpg");//造数据
								if( !textureFile.exists() ){
									productMessage.append("多材质产品["+productCode+"]的["+ab.getString("name")+"]材质物理文件没有找到！路径："+textureFile.getPath());
									logger.error("多材质产品["+productCode+"]的["+ab.getString("name")+"]材质物理文件没有找到！"+textureFile.getPath());
									return null;
								}else{
									//logger.error("获取产品材质["+renderResourceRoot + Utils.dealWithPath("/baseProduct/" + productCode + "/texture" + fileName, osType)+"路径");
									list.add(textureFileOriginal.getPath());
								}
							}else{
								productMessage.append("多材质产品["+productCode+"]的[textureId:"+textureId+"->数据不存在]材质数据没有找到！");
								logger.error("多材质产品["+productCode+"]的[textureId:"+textureId+"->数据不存在]材质数据没有找到！");
								return null;
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(Utils.getLineNumber()+"=======处理产品["+productCode+"]的多材质异常！！！"+e.getMessage());
		}
		if( productMessage.length() > 0 ){
			logger.error("Id为"+priorityTask.getId()+"的渲染任务拼装多材质预解压路径错误：" + productMessage.toString());
			SysTask sysTask = new SysTask();
			sysTask.setId(priorityTask.getId());
			sysTask.setRemark(productMessage.toString());
			sysTaskService.update(sysTask );
			return null;
		}
		return list;
	}
	/**
	 * 渲染时处理多材质产品的材质
	 * @param productCode
	 * @param planProduct
	 * @param toPath
	 * @return
	 */
	public JSONObject processTexture(String productCode,DesignPlanProduct planProduct,String toPath){
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("success",false);
		jsonObject.accumulate("message","");
		if( planProduct == null ){
			return jsonObject;
		}
		String osType = FileUploadUtils.SYSTEM_FORMAT;
		try {
			String renderResourceRoot = Utils.getPropertyName("render","render.renderResourcesPath","");
			// 多材质产品处理
			if (StringUtils.isNotBlank(planProduct.getSplitTexturesChooseInfo())) {
				String splitTextureInfo = planProduct.getSplitTexturesChooseInfo();
				JSONArray textureInfoArray = JSONArray.fromObject(splitTextureInfo);
				if (textureInfoArray != null && textureInfoArray.size() > 0) {
					for (Object a : textureInfoArray) {
						JSONObject ab = JSONObject.fromObject(a);
						String textureId = ab.getString("defaultId");// 使用的材质ID
						logger.debug("产品["+productCode+"]的["+ab.getString("name")+"]在当前设计方案中使用的材质为textureId:"+textureId);
						if (StringUtils.isNotBlank(textureId)) {
							ResTexture texturePic = resTextureService.get(Integer.valueOf(textureId));
							if( texturePic != null ){
//								File sourceFile = new File(FileUploadUtils.UPLOAD_ROOT + texturePic.getFilePath());
								String fileName = texturePic.getFilePath().substring(texturePic.getFilePath().lastIndexOf("/"));
								File textureFile = new File( Utils.dealWithPath(renderResourceRoot +"/baseProduct/" + productCode + "/texture" + fileName, osType));
								logger.debug(Utils.getLineNumber()+"========="+textureFile.getPath());
								if( !textureFile.exists() ){

									jsonObject.remove("success");
									jsonObject.accumulate("success",false);
									jsonObject.accumulate("message","多材质产品["+productCode+"]的["+ab.getString("name")+"]材质物理文件没有找到！");
									break;
								}else{
									logger.debug("===========拷贝产品材质["+renderResourceRoot + Utils.dealWithPath("/baseProduct/" + productCode + "/texture" + fileName, osType)+"]到["+toPath+"]");
//									FileUtils.copyFileToDirectory(textureFile, new File(toPath));
									FileUploadUtils.copyfile(textureFile.getPath(),toPath + fileName);
								}
							}else{
								jsonObject.remove("success");
								jsonObject.accumulate("success",false);
								jsonObject.accumulate("message","多材质产品["+productCode+"]的[textureId:"+textureId+"->数据不存在]材质数据没有找到！");
								break;
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug(Utils.getLineNumber()+"=======处理产品["+productCode+"]的多材质异常！！！");
		}
		return jsonObject;
	}

	/**
	 * 删除渲染目录
	 * @author huangsongbo
	 * @param storageType
	 * @param deleteRenderPath
	 * @param rootDirName
	 * @param renderConfig
	 */
	private void deleteRenderDir(String storageType, String deleteRenderPath, String rootDirName,
			RenderConfig renderConfig) {
		if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
			logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
			FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
		}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
			logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
			FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
		}
	}
	
	/**
	 * 准备3d渲染。将3d模型文件解压到渲染目录
	 * @param planId 设计方案ID
	 * @return
	 */
	//String renderServer
	public Object startRender(Integer planId,String rootDirName,RenderConfig renderConfig,String type){
		// 解压文件存储方式
		String storageType = renderConfig.getStorageType();
		// 压缩文件存储根目录
		String renderServer = renderConfig.getRenderRoot();
		// 资源文件存储方式
		Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD,1);
		boolean isSavedFtp = false;
		if( ftpUploadMethod == 2 || ftpUploadMethod == 3 ){
			isSavedFtp = true;
		}
		JSONObject jsonObject = new JSONObject();
		long beginTime =  System.currentTimeMillis();
		//设计方案ID不能为空
		if( planId == null ){
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message","设计方案ID不能为空");
			return jsonObject;
		}
		DesignPlan designPlan = designPlanService.get(planId);
		if( designPlan == null ){
			//没有找到ID为planId的设计方案
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message", "没有找到ID为 " + planId + " 的设计方案");
			return jsonObject;
		}

		//生成渲染根目录
		String osType = FileUploadUtils.SYSTEM_FORMAT;
		String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
//		String occu = Utils.getValue("design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
		String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
		String renderPath = "/".equals(renderServer)?"":renderServer + occu + scriptPath + rootDirName + "/" + rootDirName;
		String deleteRenderPath = renderServer + occu + scriptPath;
		if( "windows".equals(osType) ){
			occu = occu.replace("/", "\\");
			scriptPath = scriptPath.replace("/","\\");
			renderPath = renderPath.replace("/","\\");
			deleteRenderPath = deleteRenderPath.replace("/","\\");
		}
//		String renderServer = Utils.getValue("app.render.root","D:\\MaxRender").trim();
		logger.debug("==================解压目录===================");
		logger.debug(renderPath);
		//测试解压
		if( "test".equals(type) ){
			//渲染文件生成目录
			renderPath = rootDirName;
		}
		
		logger.debug("渲染路径renderPath=" + renderPath);
		if( "linux".equals(osType) ){
		   renderPath = renderPath.replace("\\", "/");
		}
		File renderRoot = new File(renderPath);
		if( !renderRoot.exists() ) {
			renderRoot.mkdirs();
		}

		try {
			logger.info("开始解压灯光模型文件");
			/*TODO 解压灯光文件到渲染根目录*/
			if( designPlan.getSpaceCommonId() == null ){
				//该设计方案中没有样板房ID
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message","该设计方案中没有空间ID");
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FtpUploadUtils.deleteDir(renderServer + occu,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FileUploadUtils.deleteDir(renderRoot);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}
			SpaceCommon spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
			if( spaceCommon == null ){
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message","没有找到ID为 "+designPlan.getSpaceCommonId()+" 的空间!SpaceCommon(id="+designPlan.getSpaceCommonId()+") is null! ");
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FtpUploadUtils.deleteDir(renderServer + occu,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FileUploadUtils.deleteDir(renderRoot);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}
			Integer space3dModelId = StringUtils.isBlank(spaceCommon.getModel3dId())?null:Integer.valueOf(spaceCommon.getModel3dId());
			if( space3dModelId == null ){
				//样板房没有上传3d模型数据
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message","空间中没有上传灯光文件!model3dId is null!");
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FtpUploadUtils.deleteDir(renderServer + occu,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FileUploadUtils.deleteDir(renderRoot);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}
			ResModel space3dModel = resModelService.get(space3dModelId);
			if( space3dModel == null ){
				//样板房的3d模型数据没有找到
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message","空间 "+spaceCommon.getSpaceCode()+" 的灯光文件数据没有找到");
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FtpUploadUtils.deleteDir(renderServer + occu,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FileUploadUtils.deleteDir(renderRoot);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}
			if( StringUtils.isBlank(space3dModel.getModelPath()) ){
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message", "空间 " + spaceCommon.getSpaceCode() + " 的灯光文件数据path为空。ResModel(id=" + space3dModel.getId() + ") is null！！");
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FtpUploadUtils.deleteDir(renderServer + occu,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FileUploadUtils.deleteDir(renderRoot);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}
			//如果文件存储到ftp上，先把ftp上的文件下载到本地
			String spaceModelPath = "";

			if( isSavedFtp ){
				/*spaceModelPath = FileUploadUtils.downloadFile(space3dModel.getModelPath(),Constants.UPLOAD_ROOT+space3dModel.getModelPath().substring(0, space3dModel.getModelPath().lastIndexOf("/") + 1));*/
				spaceModelPath = FileUploadUtils.downloadFile(space3dModel.getModelPath(),Utils.getAbsolutePath(space3dModel.getModelPath().substring(0, space3dModel.getModelPath().lastIndexOf("/") + 1), Utils.getAbsolutePathType.encrypt));
			}else {
				/*spaceModelPath = Constants.UPLOAD_ROOT + space3dModel.getModelPath();*/
				spaceModelPath = Utils.getAbsolutePath(space3dModel.getModelPath(), Utils.getAbsolutePathType.encrypt);
			}
			if( FileUploadUtils.SYSTEM_FORMAT.equals("linux") ){
				spaceModelPath = spaceModelPath.replace("\\", "/");
			}
			File spaceModelFile = new File(spaceModelPath);
			if( !spaceModelFile.exists() ){
				//样板房3d模型文件没有找到
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message", "空间 " + spaceCommon.getSpaceCode() + " 的灯光文件没有找到");
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FtpUploadUtils.deleteDir(renderServer + occu, rootDirName, renderConfig.getRenderServer(), renderConfig.getUserName(), renderConfig.getPassword());
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//					FileUploadUtils.deleteDir(renderRoot);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}

			/**
			 * 如果是测试，则现在服务器上的压缩包拷贝到本地后再解压
			 */
			if( "test".equals(type) ) {
				//拷贝服务器上的压缩包到本地
				String localSpaceModelPath = "";
				try{
					localSpaceModelPath = FileUploadUtils.downloadFile(space3dModel.getModelPath(),renderRoot.getPath()+"\\zips\\");
				}catch(IOException e){
					e.printStackTrace();
					jsonObject.accumulate("success", false);
					jsonObject.accumulate("message", "从服务器下载空间 " + spaceCommon.getSpaceCode() + " 的灯光文件到本地失败！");
					//删除目录
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
					RenderUtil.deleteFile(deleteRenderPath + rootDirName);
					return jsonObject;
				}
				//解压
				DeCompressUtil.deCompress(localSpaceModelPath, renderPath, "空间 " + spaceCommon.getSpaceCode() + " 的灯光文件格式错误!");
			}else{
				/** 解压 **/
				// 如果压缩文件存储方式为ftp.则需要把渲染压缩包解压到配置的ftp目录下
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					// 先压缩到本地
					//压缩文件解压后的临时存放目录
					/*String tempPath = Constants.UPLOAD_ROOT + occu + rootDirName;*/
					String tempPath = Utils.getAbsolutePath(occu + rootDirName, Utils.getAbsolutePathType.encrypt);
					File tempFile = new File(tempPath);
					if( !tempFile.exists() ){
						tempFile.mkdirs();
					}
					DeCompressUtil.deCompress(spaceModelPath,tempPath,"空间 "+spaceCommon.getSpaceCode()+" 的灯光文件格式错误!");
					// 上传到ftp目录,并删除临时目录
					FtpUploadUtils.uploadDirectory(tempFile.getParentFile(), renderConfig.getRenderServer(), renderConfig.getUserName(), renderConfig.getPassword(), true);
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					DeCompressUtil.deCompress(spaceModelPath, renderPath, "空间 " + spaceCommon.getSpaceCode() + " 的灯光文件格式错误!");
				}
			}

			/**TODO 解压空间场景模型文件**/
			logger.info("开始解压场景模型文件");
			Integer spaceScene3dId = spaceCommon.getScene3dId();
			if( spaceScene3dId != null && spaceScene3dId > 0 ){
				ResModel space3dScene = resModelService.get(spaceScene3dId);
				if( space3dScene == null ){
					//样板房的3d模型数据没有找到
					jsonObject.accumulate("success",false);
					jsonObject.accumulate("message","空间 "+spaceCommon.getSpaceCode()+" 的场景文件数据没有找到");
					//删除目录
					if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//						FtpUploadUtils.deleteDir(renderServer + occu,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
						FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
//						FileUploadUtils.deleteDir(renderRoot);
						FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
					}
					return jsonObject;
				}
				if( StringUtils.isBlank(space3dScene.getModelPath()) ){
					jsonObject.accumulate("success",false);
					jsonObject.accumulate("message", "空间 " + spaceCommon.getSpaceCode() + " 的场景文件数据path为空。ResModel(id=" + space3dScene.getId() + ") is null！！");
					//删除目录
					if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
						FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
						FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
					}
					return jsonObject;
				}
				//如果文件存储到ftp上，先把ftp上的文件下载到本地
				String spaceScenePath = "";
				if( isSavedFtp ){
					/*spaceScenePath = FileUploadUtils.downloadFile(space3dScene.getModelPath(),Constants.UPLOAD_ROOT+space3dScene.getModelPath().substring(0, space3dScene.getModelPath().lastIndexOf("/") + 1));*/
					spaceScenePath = FileUploadUtils.downloadFile(space3dScene.getModelPath(),Utils.getAbsolutePath(space3dScene.getModelPath().substring(0, space3dScene.getModelPath().lastIndexOf("/") + 1), Utils.getAbsolutePathType.encrypt));
				}else {
					/*spaceScenePath = Constants.UPLOAD_ROOT + space3dScene.getModelPath();*/
					spaceScenePath = Utils.getAbsolutePath(space3dScene.getModelPath(), Utils.getAbsolutePathType.encrypt);
				}
				if( FileUploadUtils.SYSTEM_FORMAT.equals("linux") ){
					spaceScenePath = spaceScenePath.replace("\\", "/");
				}
				File spaceSceneFile = new File(spaceScenePath);
				if( !spaceSceneFile.exists() ){
					//样板房3d模型文件没有找到
					jsonObject.accumulate("success",false);
					jsonObject.accumulate("message", "空间 " + spaceCommon.getSpaceCode() + " 的场景文件没有找到");
					//删除目录
					if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
						FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
						FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
					}
					return jsonObject;
				}

				/**
				 * 如果是测试，则现在服务器上的压缩包拷贝到本地后再解压
				 */
				if( "test".equals(type) ) {
					//拷贝服务器上的压缩包到本地
					String localSpaceScenePath = "";
					try{
						localSpaceScenePath = FileUploadUtils.downloadFile(space3dScene.getModelPath(),renderRoot.getPath()+"\\zips\\");
					}catch(IOException e){
						e.printStackTrace();
						jsonObject.accumulate("success", false);
						jsonObject.accumulate("message", "从服务器下载空间 " + spaceCommon.getSpaceCode() + " 的场景文件到本地失败！");
						//删除目录
						logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
						RenderUtil.deleteFile(deleteRenderPath + rootDirName);
						return jsonObject;
					}
					//解压
					DeCompressUtil.deCompress(localSpaceScenePath, renderPath, "空间 " + spaceCommon.getSpaceCode() + " 的场景文件格式错误!");
				}else{
					/** 解压 **/
					// 如果压缩文件存储方式为ftp.则需要把渲染压缩包解压到配置的ftp目录下
					if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
						// 先压缩到本地
						//压缩文件解压后的临时存放目录
						/*String tempPath = Constants.UPLOAD_ROOT + occu + rootDirName;*/
						String tempPath = Utils.getAbsolutePath(occu + rootDirName, Utils.getAbsolutePathType.encrypt);
						
						File tempFile = new File(tempPath);
						if( !tempFile.exists() ){
							tempFile.mkdirs();
						}
						DeCompressUtil.deCompress(spaceScenePath,tempPath,"空间 "+spaceCommon.getSpaceCode()+" 的场景文件格式错误!");
						// 上传到ftp目录,并删除临时目录
						FtpUploadUtils.uploadDirectory(tempFile.getParentFile(), renderConfig.getRenderServer(), renderConfig.getUserName(), renderConfig.getPassword(), true);
					}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
						DeCompressUtil.deCompress(spaceScenePath, renderPath, "空间 " + spaceCommon.getSpaceCode() + " 的场景文件格式错误!");
					}
				}
			}

			logger.info("开始解压设计方案产品模型文件");
			/*TODO 解压设计方案产品到渲染根目录*/
			DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
			designPlanProductSearch.setPlanId(designPlan.getId());
			designPlanProductSearch.setIsDeleted(0);
			List<DesignPlanProduct> designPlanProducts = designPlanProductService.getList(designPlanProductSearch);
			StringBuffer productMessage = new StringBuffer();
			if( designPlanProducts != null && designPlanProducts.size() > 0 ){
				BaseProduct baseProduct = null;
				BaseProduct baimoProduct = null;
				logger.debug("===========planId==============:" + designPlan.getId());
				for( DesignPlanProduct designPlanProduct : designPlanProducts ){
					Integer modelId = null;
					baseProduct = baseProductService.get(designPlanProduct.getProductId());
					boolean isHard = isHard(baseProduct);
					if( isHard ){
						logger.debug("===========designPlanProduct.getInitProductId()==============:" + designPlanProduct.getInitProductId());
						logger.debug("===========designPlanProduct.getId()==============:" + designPlanProduct.getId());
						baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
						modelId = baimoProduct.getModelId();
					}else {
						modelId = baseProduct.getModelId();
					}
					//产品没有上传3d模型
					if( modelId == null ){
						if( isHard ){
							productMessage.append("产品 ["+baseProduct.getProductCode()+"] 所属的白模产品 ["+baimoProduct.getProductCode()+"] 没有上传3dmax模型！！\r\n");
						}else{
							productMessage.append("产品 ["+baseProduct.getProductCode()+"] 没有上传3d模型！！\r\n");
						}
						continue;
					}
					ResModel resModel = resModelService.get(modelId);
					//没有找到3d模型文件
					if( resModel == null ){
						if( isHard ){
							productMessage.append("产品 ["+baseProduct.getProductCode()+"] 所属的白模产品 ["+baimoProduct.getProductCode()+"] 的3dmax模型文件为空。ResMoel(id="+ modelId +") is null！！\r\n");
						}else{
							productMessage.append("产品 ["+baseProduct.getProductCode()+"] 的3d模型文件为空。ResMoel(id="+ modelId +") is null！！\r\n");
						}
						continue;
					}
					if( StringUtils.isBlank(resModel.getModelPath()) ){
						if( isHard ){
							productMessage.append("产品 ["+baseProduct.getProductCode()+"] 所属的白模产品 ["+baimoProduct.getProductCode()+"] 的3dmax模型数据path为空。ResMoel(id="+ modelId +") is null！！\r\n");
						}else{
							productMessage.append("产品 ["+baseProduct.getProductCode()+"] 的3d模型数据path为空。ResMoel(id="+ modelId +") is null！！\r\n");
						}
						continue;
					}
					//如果文件存储到ftp上，先把ftp上的文件下载到本地
					String productModelPath = "";
					if( isSavedFtp ){
						/*productModelPath = FileUploadUtils.downloadFile(resModel.getModelPath(),Constants.UPLOAD_ROOT+resModel.getModelPath().substring(0, resModel.getModelPath().lastIndexOf("/") + 1));*/
						productModelPath = FileUploadUtils.downloadFile(resModel.getModelPath(),Utils.getAbsolutePath(resModel.getModelPath().substring(0, resModel.getModelPath().lastIndexOf("/") + 1), Utils.getAbsolutePathType.encrypt));
					}else {
						/*productModelPath = Constants.UPLOAD_ROOT + resModel.getModelPath();*/
						productModelPath = Utils.getAbsolutePath(resModel.getModelPath(), Utils.getAbsolutePathType.encrypt);
					}
					if( FileUploadUtils.SYSTEM_FORMAT.equals("linux") ){
						productModelPath = productModelPath.replace("\\", "/");
					}
					productModelPath = productModelPath.replaceAll("\r","").replaceAll("\n","");
					File productModelFile = new File(productModelPath);
					logger.warn(Utils.getLineNumber()+"========================================"+productModelPath+","+productModelFile.exists());
					if( !productModelFile.exists() ){
						//没有找到产品的3d模型文件
						jsonObject.accumulate("success",false);
						if( isHard ){
							jsonObject.accumulate("message", "没有找到产品 [" + baseProduct.getProductCode() + "] 所属白模产品 ["+baimoProduct.getProductCode()+"] 的3d模型文件。path：" + productModelPath);
						}else{
							jsonObject.accumulate("message", "没有找到产品 [" + baseProduct.getProductCode() + "] 的3d模型文件。path：" + productModelPath);
						}
						//删除目录
						if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
							logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
							FtpUploadUtils.deleteDir(deleteRenderPath, rootDirName, renderConfig.getRenderServer(), renderConfig.getUserName(), renderConfig.getPassword());
						}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
							logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
							FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
						}
						return jsonObject;
					}

					/**
					 * 如果是测试，则现在服务器上的压缩包拷贝到本地后再解压
					 */
					if ("test".equals(type) ) {
						//拷贝服务器上的压缩包到本地
						String localProductModelPath = "";
						try {
							localProductModelPath = FileUploadUtils.downloadFile(resModel.getModelPath(), renderRoot.getPath() + "\\zips\\");
						}catch(IOException e){
							e.printStackTrace();
							productMessage.append("从服务器下载产品 " + baseProduct.getProductCode() + " 的3dmax模型到本地失败！\r\n");
							continue;
						}
						if( FileUploadUtils.SYSTEM_FORMAT.equals("linux") ){
							productModelPath = productModelPath.replace("\\", "/");
						}
						//解压
						DeCompressUtil.deCompress(localProductModelPath, renderRoot.getPath(), "产品 " + baseProduct.getProductCode() + " 的3d模型文件格式错误!");
					}else{
						/** 解压 **/
						// 如果压缩文件存储方式为ftp.则需要把渲染压缩包解压到配置的ftp目录下
						if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
							// 先压缩到本地
							//压缩文件解压后的临时存放目录
							/*String tempPath = Constants.UPLOAD_ROOT + occu + rootDirName;*/
							String tempPath = Utils.getAbsolutePath(occu + rootDirName, Utils.getAbsolutePathType.encrypt);
							File tempFile = new File(tempPath);
							if( !tempFile.exists() ){
								tempFile.mkdirs();
							}
							DeCompressUtil.deCompress(productModelPath,tempPath+"\\"+baseProduct.getProductCode(),"产品 "+baseProduct.getProductCode()+" 的3d模型文件格式错误!");
							// 上传到ftp目录,并删除临时目录
							FtpUploadUtils.uploadDirectory(tempFile.getParentFile(),renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword(),true);
						}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
							DeCompressUtil.deCompress(productModelPath,renderPath, "产品 "+baseProduct.getProductCode()+" 的3d模型文件格式错误!");
						}
					}

					//产品材质图片
					String materialPicIds = baseProduct.getMaterialPicIds();
					if( StringUtils.isNotBlank(materialPicIds) ){
						String[] materialPicIdArray = materialPicIds.split(",");
						FTPClient ftpc = null;
						if( materialPicIdArray.length > 0 && RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ) {
							ftpc = new FTPClient();
							ftpc.connect(renderConfig.getRenderServer());
							ftpc.login(renderConfig.getUserName(), renderConfig.getPassword());
							int reply = ftpc.getReplyCode();
							if (!FTPReply.isPositiveCompletion(reply)) {
								logger.info("----------登录失败！");
								ftpc.disconnect();
								jsonObject.accumulate("success", false);
								jsonObject.accumulate("message", "FTP ["+renderConfig.getRenderServer()+"] 登录失败！用户名："+renderConfig.getUserName()+"，密码："+renderConfig.getPassword());
								return jsonObject;
							}
						}
						for( String materialId : materialPicIdArray ){
							if( StringUtils.isNotBlank(materialId) ){
								if( Integer.valueOf(materialId) < 1 ){
									continue;
								}
								ResTexture materialRes = resTextureService.get(Integer.valueOf(materialId));
								if( materialRes == null ){
									productMessage.append("没有找到材质资源！（材质ID："+materialId+"；产品CODE："+baseProduct.getProductCode());
									break;
								}
								//判断文件存储位置。如果为ftp，则先把ftp文件下载到本地，返回本地路径。
								String materialPicPath = "";
								if( isSavedFtp ){
									/*materialPicPath = FileUploadUtils.downloadFile(materialRes.getFilePath(),Constants.UPLOAD_ROOT+materialRes.getFilePath().substring(0, materialRes.getFilePath().lastIndexOf("/") + 1));*/
									materialPicPath = FileUploadUtils.downloadFile(materialRes.getFilePath(),Utils.getAbsolutePath(materialRes.getFilePath().substring(0, materialRes.getFilePath().lastIndexOf("/") + 1), Utils.getAbsolutePathType.encrypt));
								}else {
									/*materialPicPath = Constants.UPLOAD_ROOT + materialRes.getFilePath();*/
									materialPicPath = Utils.getAbsolutePath(materialRes.getFilePath(), Utils.getAbsolutePathType.encrypt);
								}
								if( FileUploadUtils.SYSTEM_FORMAT.equals("linux") ){
									materialPicPath = materialPicPath.replace("\\", "/");
								}
								File materialPic = new File(materialPicPath);
								if( !materialPic.exists() ){
									productMessage.append("没有找到材质资源文件！（材质path："+materialPicPath+"；产品CODE："+baseProduct.getProductCode());
									break;
								}
								/* 如果为同类型新增出来的产品，则该产品和主产品是共用一个3dmax模型的。该产品渲染解压时产生的文件夹名称其实是以主产品code命名的。
								 所以，如果该产品有材质，那么需要把它放到以主产品code命名的目录的texture子目录中。*/
								String productCodeDir = baseProduct.getProductCode();
								if( baseProduct.getParentId() != null && baseProduct.getParentId() > 0 && baseProduct.getParentId() != baseProduct.getId() ){
									BaseProduct parentProduct = baseProductService.get(baseProduct.getParentId());
									if( parentProduct != null ){
										productCodeDir = parentProduct.getProductCode();
									}else{
										productMessage.append("产品 ["+baseProduct.getProductCode()+"] 的主产品没有找到！");
										break;
									}
								}else {
									/*如果是硬装（地面、墙面）产品，则该产品用到的模型为所属白模产品（空间白模产品）的3dmax模型文件。该产品渲染解压时产生的文件夹名称其实是以所属白模产品的产品code命名的。
									 所以，要把该产品的材质放到以所属白模产品code命名的目录的texture子目录下。*/
									if (isHard) {
										productCodeDir = baimoProduct.getProductCode();
									}
								}
								// 如果压缩文件存储方式为ftp.则需要把材质文件上传到该产品对应的ftp目录下
								if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType)){
									RenderUtil.makeFtpDirectory(ftpc, renderPath.replaceAll("\\\\","/") + "/" + productCodeDir + "/texture");
									ftpc.changeWorkingDirectory(renderPath.replaceAll("\\\\", "/") + "/" + productCodeDir +"/texture");
									FtpUploadUtils.upload(materialPic,ftpc);
								} else {
									FileUploadUtils.fileCopy(materialPicPath
											, renderRoot.getPath() + ("linux".equals(FileUploadUtils.SYSTEM_FORMAT) ? "/" : "\\")
											+ productCodeDir + ("linux".equals(FileUploadUtils.SYSTEM_FORMAT) ? "/" : "\\")
											+ "texture" + ("linux".equals(FileUploadUtils.SYSTEM_FORMAT) ? "/" : "\\")
											+ materialRes.getFilePath().substring(materialRes.getFilePath().lastIndexOf("/")+1));
								}
							}
						}
						if( ftpc != null ){
							ftpc.disconnect();
						}
					}
				}
			}

			// 如果有产品压缩错误信息，则直接返回错误结果，并删除渲染文件根目录
			if( productMessage.length() > 0 ){
				jsonObject.accumulate("success",false);
				jsonObject.accumulate("message",productMessage.toString());
				logger.debug("解压渲染文件失败：" + productMessage.toString());
				//删除目录
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
					FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
					FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
				}
				return jsonObject;
			}

			/*TODO 解压renderRoot下的zip。最大处理层级为5，被压缩5次以上的内容无法解压*/
			int count = 0;
			while( RenderUtil.hasZip(renderRoot.getPath()) && count < 5 ){
				RenderUtil.unzip(renderRoot.getPath(), renderRoot.getPath());
				count++;
			}

			/**
			 * 如果是测试，则现在服务器上的压缩包拷贝到本地后再解压
			 */
			if( "test".equals(type) ) {
				jsonObject.accumulate("success", true);
				jsonObject.accumulate("message", productMessage.toString());
			}else{
				jsonObject.accumulate("success", true);
				jsonObject.accumulate("message", rootDirName);
			}
			long endTime = System.currentTimeMillis();
			//////System.out.println("======================================文件解压完成，耗时:" + (endTime-beginTime)/1000+"S;\n"+"解压目录=" + renderPath);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			//删除目录
			if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
				logger.warn(Utils.getLineNumber()+"删除目录："+deleteRenderPath + rootDirName);
				FtpUploadUtils.deleteDir(deleteRenderPath,rootDirName,renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
			}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
				logger.warn(Utils.getLineNumber() + "删除目录：" + deleteRenderPath + rootDirName);
				FileUploadUtils.deleteDir(new File(deleteRenderPath + rootDirName));
			}
			 //解压文件失败
			jsonObject.accumulate("success",false);
			jsonObject.accumulate("message", "解压文件失败!" + e.getMessage());
			logger.debug("解压文件失败!" + e.getMessage());
			return jsonObject;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nork.design.service.DesignPlanService#getPlanDesignPicJobByTask()
	 * 
	 * ////检查指定的文件夹目录，发现文件后，检查是否为存在的设计方案id.jpg文件，是，则拷贝到渲染图文件下，并进行数据库存储，并发送消息给用户,同时更新任务状态
	 * 
	 */
	public void getPlanDesignPicJobByTask() throws Exception{
//		MessageResponse messageResponse = new MessageResponse();
//		messageResponse.setType(1);
//		messageResponse.setSuccess(true);/**渲染成功*/
//		Session session =WebSocketServer.clientMap.get(240);
//		for (int i = 0; i < 3; i++) {
//			WebSocketServer.sendMessage(session, messageResponse);
//		}
//		if(1==1){
//			return;
//		}
		if(JOB_LOG_FLAG)
		  logger.info("开始检查任务中的效果图是否渲染完成。。。");

		SysTask sysTask = new SysTask();
		sysTask.setState(SysTaskStatus.EXECUTING);//正在执行的数据
		sysTask.setTaskServer(TASK_SERVER_HOSTS);//渲染任务服务器IP
		List<SysTask> sysTaskList = sysTaskService.getList(sysTask);
		//追加暂停的任务--暂停的任务不计入再次运算范围
		sysTask.setStateList(Arrays.asList(SysTaskStatus.SUSPEND,SysTaskStatus.SUSPEND_BY_SYSTEM));// 手动暂停和系统暂停的任务
		List<SysTask> sysTaskWaitList = sysTaskService.getList(sysTask);
		if(sysTaskWaitList != null &&  sysTaskWaitList.size()>0){
			sysTaskList.addAll(sysTaskWaitList);
			if(JOB_LOG_FLAG)
			   logger.info("追加暂停任务数量为"+sysTaskWaitList.size());
		}
		if(sysTaskList == null || sysTaskList.size()==0){
			if(JOB_LOG_FLAG)
		       logger.info("当前没有正在渲染的任务。。。");
		   return;
		}

		/**
		 * 键为 任务id  值为1.任务失败 	2.任务成功 	3.任务暂停
		 */
		Map<Integer,String>resultsMap=new HashMap<Integer,String>();
		
		
		//需要通过循环获取图片信息todo
		if (sysTaskList != null && sysTaskList.size() > 0) {
			String osType = FileUploadUtils.SYSTEM_FORMAT;
//			String occu = Utils.getValue("design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
			String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
			if( "windows".equals(osType) ){
				occu = occu.replace("/", "\\");
			}

			RenderConfig renderConfig = null;
			if(JOB_LOG_FLAG)
			   logger.info("当前需要执行的任务数量为" + sysTaskList.size());
			int j=sysTaskList.size();
			for(int i=0;i< sysTaskList.size();i++){
				SysTask newsysTask = sysTaskList.get(i);
				renderConfig = renderServersMap.get(newsysTask.getRenderServer());
				if(renderConfig==null){
					logger.debug("renderServersMap="+renderServersMap.toString()+";renderTask.getRenderServer()="+newsysTask.getRenderServer());
					continue;
				}
//				// 读取3dmax渲染图
//				//3dmax渲染目录根目录
				String  _3dmaxRoot = renderConfig.getRenderRoot();
//				//应用根节点目录
				/*String _uploadRoot = Utils.getValue("app.upload.root", "D:\\nork\\Resource").trim();*/
//				//3dmax生成图片路径
				String filepath = Utils.getPropertyName("render","app.3dmax.render.upload.path", "/RenderPic/").trim();
				//渲染图存放路径
				String storepath = Utils.getPropertyName("config/res", "design.designPlan.render.upload.path", "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/render/").trim();
				storepath = Utils.replaceDate(storepath);
				//				//渲染图生成路径
				String realPath = _3dmaxRoot + ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)?filepath:filepath.replace("/", "\\"));
//				//渲染图业务路径
				/*String storeRealPath = _uploadRoot+ ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)?storepath:storepath.replace("/", "\\"));*/
				String storeRealPath = Utils.getAbsolutePath(("linux".equals(FileUploadUtils.SYSTEM_FORMAT)?storepath:storepath.replace("/", "\\")), Utils.getAbsolutePathType.encrypt);
//
				/*解决[code]没有替换问题*/
				DesignPlan designPlan = designPlanService.get(newsysTask.getBusinessId());
				if(designPlan!=null){
					storepath=storepath.replace("[code]", designPlan.getPlanCode());
					 
					
					storeRealPath=storeRealPath.replace("[code]", designPlan.getPlanCode());
					storeRealPath = Utils.replaceDate(storeRealPath);
				}
				/*解决[code]没有替换问题->end*/
				if( renderConfig == null ){
					logger.debug("配置中没有"+newsysTask.getRenderServer()+"的渲染服务器！结束执行。。。");
					return;
				}
				String fpath = "";
				if( "ruiyun".equals(newsysTask.getRenderWay()) && null != newsysTask.getRenderTaskId() ){
					fpath = realPath + newsysTask.getRenderTaskId()+"_"+newsysTask.getBusinessCode() + "/"+newsysTask.getBusinessCode()+"_0001.jpg";
					fpath = ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)?fpath:fpath.replace("/", "\\"));
					realPath = realPath + newsysTask.getRenderTaskId()+"_"+newsysTask.getBusinessCode() + "/";
					realPath = ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)?realPath:realPath.replace("/", "\\"));
				}
				if( !"ruiyun".equals(newsysTask.getRenderWay())){
					fpath = realPath +  newsysTask.getBusinessCode() +".jpg";
				}
				
				logger.warn("--------------图片路径："+fpath+" RenderWay: "+newsysTask.getRenderWay());
				File f = new File(fpath);
				if(newsysTask == null
				|| StringUtils.isEmpty(newsysTask.getBusinessCode())
				|| newsysTask.getBusinessId() == null
			    || new Integer(newsysTask.getBusinessId()).intValue() <= 0){
					  logger.debug("任务中信息不完全，放弃本次执行...");
					  SysTask  newnewsysTask = new  SysTask();
					  newnewsysTask.setId(newsysTask.getId());
					  newnewsysTask.setGmtModified(new Date());
					  newnewsysTask.setRemark(newsysTask.getRemark() + ";任务中信息不完全，放弃本次执行！,taskId =" + newsysTask.getId());
					  newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
					  sysTaskService.update(newnewsysTask);
					  /************/
					  if(!resultsMap.containsKey(newnewsysTask.getId())){
						  resultsMap.put(newnewsysTask.getId(), "1");
					  }
					//删除目录
					logger.warn(Utils.getLineNumber()+"删除目录：");
					deleteRenderResource(newsysTask, renderConfig, occu);
					// 删除图片
					if( f.exists() ){
						f.delete();
					}
					j--;
					continue;
				}
				//匹配任务与渲染图，通过任务去找名字
				if(f.exists()){
					logger.debug("效果图已找到！fpath="+fpath);
					String newRenderPicPath = storepath + f.getName();
					//DesignPlan designPlan = designPlanService.get(newsysTask.getBusinessId());
					if(designPlan == null){
						 logger.debug("任务中找不到设计方案信息,无法采集..."+",taskId =" + newsysTask.getId());
						  SysTask  newnewsysTask = new  SysTask();
						  newnewsysTask.setId(newsysTask.getId());
						  newnewsysTask.setGmtModified(new Date());
						  newnewsysTask.setRemark(newsysTask.getRemark()+";任务中找不到设计方案信息,无法采集！,taskId ="+ newsysTask.getId());
						  newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						  sysTaskService.update(newnewsysTask);
						  /************/
						  if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "1");
						  }
						  
						// 删除目录
						logger.warn(Utils.getLineNumber()+"删除目录：");
						deleteRenderResource(newsysTask,renderConfig,occu);
						// 删除图片
						if( f.exists() ){
							f.delete();
						}
						j--;
						continue;
					}
				
					//如果图片库中存在相同的文件，则放弃渲染，防止定时钟重复执行
					ResPicSearch resPicSearch = new ResPicSearch();
					resPicSearch.setPicPath(newRenderPicPath);
					int num = resPicService.getCount(resPicSearch);
					if(num>0){
						  logger.debug("渲染图已存在数据库中,放弃本次执行。。。newRenderPicPath="+newRenderPicPath +",taskId =" + newsysTask.getId());
						  SysTask  newnewsysTask = new  SysTask();
						  newnewsysTask.setId(newsysTask.getId());
						  newnewsysTask.setGmtModified(new Date());
						  newnewsysTask.setRemark(newsysTask.getRemark()+";渲染图已存在数据库中,放弃本次执行！,taskId ="+ newsysTask.getId());
						  newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						  sysTaskService.update(newnewsysTask);
						  /************/
						  if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "1");
						  }
						  
						// 删除目录
						logger.warn(Utils.getLineNumber()+"删除目录：");
						deleteRenderResource(newsysTask,renderConfig,occu);
						// 删除图片
						if( f.exists() ){
							f.delete();
						}
						j--;
						continue;
					}
				
					Map map = FileUploadUtils.getMap(f,"design.designPlan.render.upload.path",false);
					ResPic resPic = assembleResPic(map);
					resPic.setPicPath(newRenderPicPath);
					resPic.setGmtCreate(new Date());
					String attribute=newsysTask.getAttribute();
					logger.debug("==============================="+attribute);
					if(StringUtils.isNotBlank(attribute)){
						//{"viewPoint":1,"scene":1}
						JSONObject json=JSONObject.fromObject(attribute);
						resPic.setViewPoint(json.getInt("viewPoint"));
						resPic.setScene(json.getInt("scene"));
						resPic.setRenderingType(json.getString("renderingType"));
						logger.debug("===============================renderingType="+resPic.getRenderingType());
					}else{
						logger.debug("------sysTaskId:"+newsysTask.getId()+"未找到渲染角度和场景参数属性(attribute)");
						// 删除目录
						logger.warn(Utils.getLineNumber()+"删除目录：");
						deleteRenderResource(newsysTask,renderConfig,occu);
						// 删除图片
						if( f.exists() ){
							f.delete();
						}
						/************/
						if(!resultsMap.containsKey(newsysTask.getId())){
							  resultsMap.put(newsysTask.getId(), "1");
						}
						continue;
					}
					resPic.setCreator("sys");
					resPic.setGmtModified(new Date());
					resPic.setModifier("sys");
					resPic.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
					resPic.setBusinessId(newsysTask.getBusinessId());
					resPic.setPicType("3DMax渲染图");
					Integer resPicId = resPicService.add(resPic);

					//生成缩略图
					Integer smallResPicId = null;
					try {
						String fileName = f.getName();
						String smallFileName = Utils.generateRandomDigitString(6)
								+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
								+ fileName.substring(fileName.indexOf("."));
						String targetSmallFilePath = storeRealPath + "small/" + smallFileName;
						logger.warn("缩略图存放目录："+targetSmallFilePath);
						ResizeImage.createThumbnail(fpath, ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) ? targetSmallFilePath : targetSmallFilePath.replaceAll("/", "\\\\"), 280, 215);
						File targetSmallFile = new File(targetSmallFilePath);
						Map smallFileMap = FileUploadUtils.getMap(targetSmallFile, "design.designPlan.render.upload.path", false);
						ResPic smallResPic = assembleResPic(smallFileMap);
						smallResPic.setPicPath(storepath + "small/" + smallFileName);
						smallResPic.setGmtCreate(new Date());
						smallResPic.setCreator("sys");
						smallResPic.setGmtModified(new Date());
						smallResPic.setModifier("sys");
						smallResPic.setSysCode(Utils
								.getCurrentDateTime(Utils.DATETIMESSS)
								+ "_"
								+ Utils.generateRandomDigitString(6));
						smallResPic.setBusinessId(newsysTask.getBusinessId());
						smallResPic.setPicType("3DMax渲染图-缩略图");
						if(StringUtils.isNotBlank(attribute)){
							//{"viewPoint":1,"scene":1}
							JSONObject json=JSONObject.fromObject(attribute);
							smallResPic.setViewPoint(json.getInt("viewPoint"));
							smallResPic.setScene(json.getInt("scene"));
						}else{
							logger.debug("------sysTaskId:"+newsysTask.getId()+"未找到渲染角度和场景参数属性(attribute)");
							// 删除目录
							logger.warn(Utils.getLineNumber()+"删除目录：");
							deleteRenderResource(newsysTask,renderConfig,occu);
							// 删除图片
							if( f.exists() ){
								f.delete();
							}
							/************/
							if(!resultsMap.containsKey(newsysTask.getId())){
								  resultsMap.put(newsysTask.getId(), "1");
							}
							continue;
						}
						//保存缩略图
						smallResPicId = resPicService.add(smallResPic);
					}catch(Exception e){
						e.printStackTrace();
						logger.debug(e.getMessage()+";taskId"+newsysTask.getId());
						SysTask  newnewsysTask = new  SysTask();
						newnewsysTask.setId(newsysTask.getId());
						newnewsysTask.setGmtModified(new Date());
						newnewsysTask.setRemark(newsysTask.getRemark() + "["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"生成高清渲染效果图的缩略图失败]");
						newnewsysTask.setState(SysTaskStatus.END_OF_EXECUTION);//执行结束
						sysTaskService.update(newnewsysTask);
						/************/
						if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "2");
						}
						continue;
					}
					
					if( resPicId != null && resPicId > 0 ){
						logger.warn("效果图已入库id=" + resPicId + ",newRenderPicPath=" + newRenderPicPath + ",taskId =" + newsysTask.getId());
						SysTask  newnewsysTask = new  SysTask();
						newnewsysTask.setId(newsysTask.getId());
						newnewsysTask.setGmtModified(new Date());
						if(newsysTask.getRemark() != null){
							newnewsysTask.setRemark(newsysTask.getRemark() + "效果图已入库id=" + resPicId);
						}else{
							newnewsysTask.setRemark("效果图已入库id=" + resPicId);
						}
						newnewsysTask.setState(SysTaskStatus.END_OF_EXECUTION);//执行结束
						sysTaskService.update(newnewsysTask);
						
						/************/
						if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "2");
						}
					}else{
						logger.debug("效果图入库异常"+",taskId =" + newsysTask.getId());
						SysTask  newnewsysTask = new  SysTask();
						newnewsysTask.setId(newsysTask.getId());
						newnewsysTask.setGmtModified(new Date());
						newnewsysTask.setRemark(newsysTask.getRemark()+";效果图入库异常！,taskId ="+ newsysTask.getId());
						newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						sysTaskService.update(newnewsysTask);
						// 删除目录
						logger.warn(Utils.getLineNumber()+"删除目录：");
						deleteRenderResource(newsysTask,renderConfig,occu);
						// 删除图片
						if( f.exists() ){
							f.delete();
						}
						 j--;
						/************/
						if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "1");
						}
					     continue;
					}
					
					//更新设计方案中的效果图
					JSONArray renderPicArray = new JSONArray();
					JSONObject renderObj = new JSONObject();
					renderObj.accumulate("smallRenderPicId",smallResPicId);
					renderObj.accumulate("renderPicId",resPicId);
					renderPicArray.add(renderObj);

					DesignPlan desPlan = new DesignPlan();
					desPlan.setId(newsysTask.getBusinessId());
					desPlan.setGmtModified(new Date());
					desPlan.setModifier("sys");
				    int m = designPlanService.update(desPlan);
				    if(m==1){
				    	logger.info("效果图已更新到设计方案中id="+desPlan.getId()+";newdesignPlanRenderPicIds=");
						SysTask  newnewsysTask = new  SysTask();
						newnewsysTask.setId(newsysTask.getId());
						newnewsysTask.setGmtModified(new Date());
						newnewsysTask.setRemark(newsysTask.getRemark()+";["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]高清渲染图已放入设计方案中。");
						newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						sysTaskService.update(newnewsysTask);
						/************/
						if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "1");
						}
				    }else{
				    	logger.debug("效果图更新到设计方案中异常,放弃渲染,设计方案更新返回"+ m+"条"+",taskId =" + newsysTask.getId());
				    	  SysTask  newnewsysTask = new  SysTask();
						  newnewsysTask.setId(newsysTask.getId());
						  newnewsysTask.setGmtModified(new Date());
						  newnewsysTask.setRemark(newsysTask.getRemark()+";效果图更新到设计方案中异常,放弃渲染,设计方案更新返回"+ m+"条"+ ",taskId ="+ newsysTask.getId());
						  newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						  sysTaskService.update(newnewsysTask);
				    	j--;
				    	 resPicService.delete(resPicId);
						 logger.debug("效果图更新到设计方案中异常,删除已存入的图片信息resPicId="+resPicId+",taskId =" + newsysTask.getId());
						// 删除目录
						logger.warn(Utils.getLineNumber()+"删除目录：");
						deleteRenderResource(newsysTask,renderConfig,occu);
						// 删除图片
						if( f.exists() ){
							f.delete();
						}
						/************/
						if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "1");
						}
						continue;
				    }
				    
				    Integer userId = designPlan.getUserId();
					if (userId == null || userId.intValue()<= 0) {
						logger.debug("用户信息异常，无法发送消息..."+",taskId =" + newsysTask.getId());
						  SysTask  newnewsysTask = new  SysTask();
						  newnewsysTask.setId(newsysTask.getId());
						  newnewsysTask.setGmtModified(new Date());
						  newnewsysTask.setRemark(newsysTask.getRemark()+";用户信息异常，无法发送消息！,taskId ="+ newsysTask.getId());
						  newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						  sysTaskService.update(newnewsysTask);
						/************/
						if(!resultsMap.containsKey(newnewsysTask.getId())){
							  resultsMap.put(newnewsysTask.getId(), "1");
						}
						continue;
					}else{
						// 发消息
						Integer messageid = sendRenderMessage(newsysTask,2,resPicId);
						
						if(messageid.intValue() <= 0){
							logger.debug("消息发送异常..."+",taskId =" + newsysTask.getId());
							  SysTask  newnewsysTask = new  SysTask();
							  newnewsysTask.setId(newsysTask.getId());
							  newnewsysTask.setGmtModified(new Date());
							  newnewsysTask.setRemark(newsysTask.getRemark()+";消息发l送异常！,taskId ="+ newsysTask.getId());
							  newnewsysTask.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
							  sysTaskService.update(newnewsysTask);
							  /************/
								if(!resultsMap.containsKey(newnewsysTask.getId())){
									  resultsMap.put(newnewsysTask.getId(), "1");
								}
							continue;
						}
					}
			    
					//更新任务状态
					SysTask	newnewsysTask = new SysTask();
					newnewsysTask.setId(newsysTask.getId());
					newnewsysTask.setState(SysTaskStatus.END_OF_EXECUTION);
					newnewsysTask.setGmtModified(new Date());
//					newnewsysTask.setModifier("sys");
					int idt = sysTaskService.update(newnewsysTask);
					/************/
					if(!resultsMap.containsKey(newnewsysTask.getId())){
						  resultsMap.put(newnewsysTask.getId(), "2");
					}
					logger.debug("任务更新"+ idt+"条,taskId =" + newnewsysTask.getId());
					if(idt>0){
						   logger.debug("任务状态已更新成功...");
						try{
							 String realNamePath = realPath +  f.getName();
							 String storeRealNamePath = storeRealPath + f.getName();
							 //////System.out.println("渲染图已与任务匹配，原始路径为realNamePath="+realNamePath +",taskId =" + newsysTask.getId() + ";storeRealNamePath="+storeRealNamePath);
							 // 将图片拷贝并存入设计方案表中
							 File f1 = new File(realNamePath);
							 File f2 = new File(storeRealNamePath);
							 FileUploadUtils.fileCopy(f1, f2);
							logger.warn("realNamePath=" + realNamePath + "拷贝到" + storeRealNamePath + "完成！" + ",taskId =" + newsysTask.getId());
							 
							 if("true".equals(Utils.getValue("design.designPlanPic.isWaterMarkingFlag", "true"))){
								  //追加水印
	//							  ImageUtils.pressText(Utils.getCurrentDateTime(Utils.DATE_TIME), storeRealNamePath, ImageUtils.RIGHT_DOWN);
	//							  ImageUtils.pressText("高清", storeRealNamePath, ImageUtils.RIGHT_UP);
								  	String attr=newsysTask.getAttribute();
								  	logger.debug("------newsysTask.getAttribute():"+attr);
								  	JSONObject json=JSONObject.fromObject(attr);
								  	logger.warn("------logger:attr:"+attr);
								  	//Integer isTurnOn=new Integer(json.getInt("isTurnOn"));
									Integer scene=new Integer(json.getInt("scene"));
									/*水印图方案一*/
	//								StringBuffer str=new StringBuffer();
	//								if(new Integer(1).equals(scene)){
	//									str.append("白天");
	//								}else if(new Integer(2).equals(scene)){
	//									str.append("黑夜");
	//								}else{
	//									str.append("黄昏");
	//								}
	//								str.append("高清");
	//							  ImageUtils.watermarking(storeRealNamePath, str.toString(), isTurnOn);
									/*水印图方案一->end*/
									//ImageUtils.watermarking2(storeRealNamePath, scene, 1, isTurnOn);
									ImageUtils.watermarking2(storeRealNamePath, scene, 1, 1);
								    //删除原始效果图文件
									if(f1.exists()){
									   f1.delete();
									   logger.info("删除原始效果图文件完成"+",taskId =" + newsysTask.getId());
									}
									logger.warn("渲染图增加水印完成，生成的新文件名称为storeRealNamePath="+storeRealNamePath);
							}

							/**************************创建浏览器查看全景图begin************************/
							if( "4".equals(resPic.getRenderingType()) ){
								logger.debug("============创建浏览器查看全景图============");
								JSONObject panoJson = 
										/*RenderUtil.generatePano(Constants.UPLOAD_ROOT + resPic.getPicPath(),newsysTask.getBusinessCode());*/
										RenderUtil.generatePano(Utils.getAbsolutePath(resPic.getPicPath(), Utils.getAbsolutePathType.encrypt),newsysTask.getBusinessCode());
								logger.debug("执行结果："+panoJson);
								if( panoJson != null && panoJson.getBoolean("success") ){
									resPic.setPanoPath("/" + newsysTask.getBusinessCode() + "/" + newsysTask.getBusinessCode() + ".html");
									logger.debug("=====panoPath:"+resPic.getPanoPath());
									resPicService.update(resPic);
								}
							}
							/**************************创建浏览器查看全景图end************************/

							 if("true".equals(Utils.getValue("design.designPlanPic.isFileDeleteFlag", "true"))){
						    }
							/**********删除高清渲染预览图，删除图片列表那条数据，清空任务列表picid**********/
							if( newsysTask.getPicId() != null ){
								ResPic pic = resPicService.get(newsysTask.getPicId());
								String method=app.getString(APP_OPLOAD_METHOD);/**1为web服务器 2为ftp 3为web+ftp*/
								if( pic != null ){
									if("1".equals(method)){
										/*String picPath = Constants.UPLOAD_ROOT + pic.getPicPath();*/
										String picPath = Utils.getAbsolutePath(pic.getPicPath(), Utils.getAbsolutePathType.encrypt);
										RenderUtil.deleteFile(picPath);
										resPicService.delete(pic.getId());
									}
									if("2".equals(method)){
										String picPath = FileUploadUtils.FTP_UPLOAD_ROOT + pic.getPicPath();
										RenderUtil.deleteFile(picPath);
										resPicService.delete(pic.getId());
									}
									if("3".equals(method)){
										String picPath=null;
										/** 删除预览图物理文件**/
										/*picPath = Constants.UPLOAD_ROOT + pic.getPicPath();*/
										picPath = Utils.getAbsolutePath(pic.getPicPath(), Utils.getAbsolutePathType.encrypt);
										RenderUtil.deleteFile(picPath);
										picPath = FileUploadUtils.FTP_UPLOAD_ROOT + pic.getPicPath();/** 删除预览图物理文件**/
										RenderUtil.deleteFile(picPath);
										resPicService.delete(pic.getId());/** 删除预览图数据*/
									}
								}
							}
						}catch(Exception e){
							e.printStackTrace();
							logger.debug("文件复制或删除文件失败" + ",taskId =" + newsysTask.getId());
							logger.debug(e.getMessage());
							  SysTask  newnewsysTask2 = new  SysTask();
							  newnewsysTask2.setId(newsysTask.getId());
							  newnewsysTask2.setGmtModified(new Date());
							  newnewsysTask2.setRemark(newsysTask.getRemark()+";文件复制或删除文件失败！,taskId ="+ newsysTask.getId());
							  newnewsysTask2.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
							  sysTaskService.update(newnewsysTask2);
							j--;
							/************/
							if(!resultsMap.containsKey(newnewsysTask2.getId())){
								  resultsMap.put(newnewsysTask2.getId(), "1");
							}
							continue;
							
						}
					}else{
						  logger.debug("任务状态更新异常...");
						  SysTask  newnewsysTask2 = new  SysTask();
						  newnewsysTask2.setId(newsysTask.getId());
						  newnewsysTask2.setGmtModified(new Date());
						  newnewsysTask2.setRemark(newsysTask.getRemark()+";任务状态更新异常！,taskId ="+ newsysTask.getId());
						  newnewsysTask2.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
						  sysTaskService.update(newnewsysTask2);
						j--;
						 resPicService.delete(resPicId);
						 logger.debug("任务状态更新异常,删除已存入的图片信息resPicId="+resPicId+",taskId =" + newsysTask.getId());
						desPlan.setGmtModified(new Date());
					    designPlanService.update(desPlan);
					    logger.debug("任务状态更新异常,还原设计方案图片列表designPlanRenderPicIds=,taskId =" + newsysTask.getId());
					    /************/
						if(!resultsMap.containsKey(newnewsysTask2.getId())){
							  resultsMap.put(newnewsysTask2.getId(), "1");
						}
					}
				}else{
					if(JOB_LOG_FLAG)
					   logger.info("效果图未找到...");
					j--;
					//任务暂定后，重新启动执行5次后，则自行终止任务执行
					int suspendMaxExecCount = Integer.valueOf(Utils.getPropertyName("render","app.render.suspend.maxExecCount","5"));
					if(newsysTask.getState().intValue() == SysTaskStatus.SUSPEND && newsysTask.getNuma1() != null && newsysTask.getNuma1() > suspendMaxExecCount ){
					  SysTask	newnewsysTask = new SysTask();
					  newnewsysTask.setId(newsysTask.getId());
					  newnewsysTask.setGmtModified(new Date());
					  newnewsysTask.setState(SysTaskStatus.TASK_TERMINATION);//任务终止
					  newnewsysTask.setRemark(newsysTask.getRemark()+"[暂停后执行5次，仍未找到效果图，任务终止！,taskId = " + newsysTask.getId() + "]");
					  sysTaskService.update(newnewsysTask);
					  logger.debug("[暂停后执行5次，仍未找到效果图，任务终止！,taskId = " + newsysTask.getId() + "]");
					  /************/
					  if(!resultsMap.containsKey(newnewsysTask.getId())){
						resultsMap.put(newnewsysTask.getId(), "1");
					  }
					}
					//logger.debug("当前任务效果图寻找次数为"+ newsysTask.getNuma1() +"次,taskId =" + newsysTask.getId());
					//每次任务运行超过5分钟后，则自动暂停任务。
					String waitDateStr = newsysTask.getAtt1();
					Date waitDate = null;
					if(StringUtils.isNotBlank(waitDateStr)){
					   waitDate = Utils.parseDate(waitDateStr, Utils.DATE_TIME);
					}
					int suspendWaitingForExecTime = Integer.valueOf(Utils.getPropertyName("render","app.render.suspend.waitingForExecTime","3600000"));
					String attribute = newsysTask.getAttribute();
					String renderingType = "";
					if(StringUtils.isNotBlank(attribute)){
						//{"viewPoint":1,"scene":1}
						JSONObject json = JSONObject.fromObject(attribute);
						if( json != null ){
							renderingType = StringUtils.isNotBlank(json.getString("renderingType"))?"":json.getString("renderingType");
						}
					}
					
					if((waitDate ==null && newsysTask.getInstructionTime()!=null && System.currentTimeMillis()-newsysTask.getInstructionTime().getTime() > suspendWaitingForExecTime && !"4".equals(renderingType))
					||(waitDate != null && System.currentTimeMillis()-waitDate.getTime()>1000*60*60) && !"4".equals(renderingType)){
					  SysTask	newnewsysTask = new SysTask();
					  newnewsysTask.setId(newsysTask.getId());
					  newnewsysTask.setGmtModified(new Date());
					  newnewsysTask.setAtt1(Utils.getCurrentDateTime());//存储暂停时间
					  newnewsysTask.setRemark(newsysTask.getRemark()+";任务寻找效果图超时60分钟，仍未找到，任务暂停！,taskId ="+ newsysTask.getId());
					  newnewsysTask.setState(SysTaskStatus.SUSPEND);//任务暂停
                      newnewsysTask.setSuspendTime(new Date());
					  newnewsysTask.setNuma1((newsysTask.getNuma1()==null?0:newsysTask.getNuma1())+1);//暂停次数加1
					  sysTaskService.update(newnewsysTask);
					  logger.debug("任务寻找效果图超时60分钟，仍未找到...，任务暂停！,taskId ="+ newsysTask.getId());
					}else{
						int panoTime = Integer.valueOf(Utils.getPropertyName("render","app.pano.render.time","3600000"));
						if( newsysTask.getInstructionTime()!=null && System.currentTimeMillis()-newsysTask.getInstructionTime().getTime() > panoTime ){
							SysTask	sysTask_s = new SysTask();
							sysTask_s.setId(newsysTask.getId());
							sysTask_s.setGmtModified(new Date());
							sysTask_s.setAtt1(Utils.getCurrentDateTime());//存储暂停时间
							sysTask_s.setRemark(newsysTask.getRemark()+";任务寻找效果图超时60分钟，仍未找到，任务暂停！,taskId ="+ newsysTask.getId());
							sysTask_s.setState(SysTaskStatus.SUSPEND);//任务暂停
							sysTask_s.setSuspendTime(new Date());
							sysTask_s.setNuma1((newsysTask.getNuma1()==null?0:newsysTask.getNuma1())+1);//暂停次数加1
							sysTaskService.update(sysTask_s);
							logger.debug("任务寻找效果图超时60分钟，仍未找到...，任务暂停！,taskId ="+ newsysTask.getId());
						}
					}
				}
			}
 
			if(JOB_LOG_FLAG)
			   logger.info("一次任务执行完毕，总任务数=" + sysTaskList.size() + ",本次成功执行次数=" + j);
			}
	}

	public ResRenderPic assembleResRenderPic(Map map){
		ResRenderPic renderPic = new ResRenderPic();
		if( map.get(FileModel.PIC_WEIGHT) != null ){
			renderPic.setPicWeight(Integer.getInteger((map.get(FileModel.PIC_WEIGHT).toString())));
		}
		if( map.get(FileModel.PIC_HEIGHT) != null ){
			renderPic.setPicHigh(Integer.getInteger((map.get(FileModel.PIC_HEIGHT).toString())));
		}
		if( map.get(FileModel.FILE_KEY) != null ){
			renderPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
		}
		if(  map.get(FileModel.FILE_NAME) != null){
			renderPic.setPicName(map.get(FileModel.FILE_NAME).toString());
		}
		if(  map.get(FileModel.FILE_ORIGINAL_NAME) != null){
			renderPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		}
		if( map.get(FileModel.FILE_SUFFIX) != null ){
			renderPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		}
		if( map.get(FileModel.FILE_SIZE) != null ){
			renderPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE).toString()));
		}
		if( map.get(FileModel.FORMAT) != null ){
			renderPic.setPicFormat(map.get(FileModel.FORMAT).toString());
		}
		if( map.get(FileModel.FILE_PATH) != null ){
			renderPic.setPicPath(map.get(FileModel.FILE_PATH).toString());
		}
		return renderPic;
	}
	public ResPic assembleResPic(Map map){
		ResPic resPic = new ResPic();
		if( map.get(FileModel.PIC_WEIGHT) != null ){
			resPic.setPicWeight(map.get(FileModel.PIC_WEIGHT).toString());
		}
		if( map.get(FileModel.PIC_HEIGHT) != null ){
			resPic.setPicHigh(map.get(FileModel.PIC_HEIGHT).toString());
		}
		if( map.get(FileModel.FILE_KEY) != null ){
			resPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
		}
		if(  map.get(FileModel.FILE_NAME) != null){
			resPic.setPicName(map.get(FileModel.FILE_NAME).toString());
		}
		if(  map.get(FileModel.FILE_ORIGINAL_NAME) != null){
			resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		}
		if( map.get(FileModel.FILE_SUFFIX) != null ){
			resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		}
		if( map.get(FileModel.FILE_SIZE) != null ){
			resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE).toString()));
		}
		if( map.get(FileModel.FORMAT) != null ){
			resPic.setPicFormat(map.get(FileModel.FORMAT).toString());
		}
		return resPic;
	}
	
	
	public ResDesign assembleResDesign(Map map){
		ResDesign resDesign = new ResDesign();
		if( map.get(FileModel.PIC_WEIGHT) != null ){
			resDesign.setFileWeight(map.get(FileModel.PIC_WEIGHT).toString());
		}
		if( map.get(FileModel.PIC_HEIGHT) != null ){
			resDesign.setFileHigh(map.get(FileModel.PIC_HEIGHT).toString());
		}
		if( map.get(FileModel.FILE_KEY) != null ){
			resDesign.setFileKey(map.get(FileModel.FILE_KEY).toString());
		}
		if(  map.get(FileModel.FILE_NAME) != null){
			resDesign.setFileName(map.get(FileModel.FILE_NAME).toString());
		}
		if(  map.get(FileModel.FILE_ORIGINAL_NAME) != null){
			resDesign.setFileName(map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		}
		if( map.get(FileModel.FILE_SUFFIX) != null ){
			resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		}
		if( map.get(FileModel.FILE_SIZE) != null ){
			resDesign.setFileSize(map.get(FileModel.FILE_SIZE).toString());
		}
		if( map.get(FileModel.FORMAT) != null ){
			resDesign.setFileFormat(map.get(FileModel.FORMAT).toString());
		}
		return resDesign;
	}

	@Override
	public List<DesignPlan> getDesignsList(DesignPlan designPlan) {
		return designPlanMapper.getDesignList(designPlan);
	}

	@Override
	public RoomExt getRoomExt(Integer designPlanId) {
		RoomExt roomExt = new RoomExt();
		if(designPlanId==null||new Integer(designPlanId).intValue()<0){
		   return null;
		}
		DesignPlan designPlan = designPlanMapper.selectByPrimaryKey(designPlanId);
		roomExt.setDesignPlanId(designPlan.getId());
		roomExt.setSpaceCommonId(designPlan.getSpaceCommonId());
		return roomExt;
	}

	@Override
	public ItemExt getItemExt(String posIndexPath,String posName,String itemCode,DesignPlan designPlan,HttpServletRequest request) {
		ItemExt itemExt = new ItemExt();
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setProductCode(itemCode);
		List<BaseProduct> list = baseProductService.getList(baseProduct);
		if( list != null && list.size() > 0 ) {
			baseProduct = list.get(0);
			itemExt.setProductId(baseProduct.getId());
			itemExt.setProductName(baseProduct.getProductName());
			itemExt.setProductCode(baseProduct.getProductCode());
			//获取产品类型（1硬装或2软装)
			if( StringUtils.isNotBlank(baseProduct.getProductTypeValue()) && baseProduct.getProductSmallTypeValue() != null ){
				SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(baseProduct.getProductTypeValue()));
				SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(),Integer.valueOf(baseProduct.getProductSmallTypeValue()));
				//空或者2表示软装
				if( StringUtils.isBlank(productSmallTypeSysDic.getAtt1()) || Integer.valueOf(productSmallTypeSysDic.getAtt1()) == 2 ){
					itemExt.setRootType("2");//软装
				}else if( StringUtils.isNotBlank(productSmallTypeSysDic.getAtt1()) && Integer.valueOf(productSmallTypeSysDic.getAtt1()) == 1 ){
					itemExt.setRootType("1");//硬装
				}
				itemExt.setProductTypeCode(productTypeSysDic.getValuekey());
				itemExt.setProductTypeName(productTypeSysDic.getName());
				itemExt.setProductTypeValue(productTypeSysDic.getValue());
				itemExt.setSmallTypeCode(productSmallTypeSysDic.getValuekey());
				itemExt.setSmallTypeName(productSmallTypeSysDic.getName());
				itemExt.setSmallTypeValue(productSmallTypeSysDic.getValue());
			}

			DesignPlanProduct designPlanProduct = new DesignPlanProduct();
			designPlanProduct.setProductId(baseProduct.getId());
			designPlanProduct.setPlanId(designPlan.getId());
			designPlanProduct.setPosIndexPath(posIndexPath);
			designPlanProduct.setPosName(posName);
			List<DesignPlanProduct> planProductList = designPlanProductService.getList(designPlanProduct);
			if( planProductList != null && planProductList.size() > 0 ) {
				designPlanProduct = planProductList.get(0);
				itemExt.setDesignPlanProductId(designPlanProduct.getId());
				itemExt.setDesignTempletProductId(designPlanProduct.getDesignPlanProductId());
			}

			//获取产品所属房型信息
			String productHouseTypeValues = baseProduct.getHouseTypeValues();
			String productHouseTypeNames = "";
			String productHouseTypeKeys = "";
			if( StringUtils.isNotBlank(productHouseTypeValues)){
				String[] houseTypeIds = productHouseTypeValues.split(",");
				for( String houseType : houseTypeIds ){
					if(StringUtils.isNotBlank(houseType) ) {
						Integer houseTypeId = Integer.valueOf(houseType);
						SysDictionary houseTypeSysDic = sysDictionaryService.getSysDictionaryByValue("houseType", houseTypeId);
						if( houseTypeSysDic != null ) {
							productHouseTypeNames += houseTypeSysDic.getName() + ",";
							productHouseTypeKeys += houseTypeSysDic.getValuekey() + ",";
						}
					}
				}
				if( StringUtils.isNotBlank(productHouseTypeKeys) ) {
					itemExt.setHouseTypeKeys(productHouseTypeKeys.substring(0, productHouseTypeKeys.lastIndexOf(",")));
				}
				if( StringUtils.isNotBlank(productHouseTypeNames) ) {
					itemExt.setHouseTypeNames(productHouseTypeNames.substring(0, productHouseTypeNames.lastIndexOf(",")));
				}
				itemExt.setHouseTypeValues(productHouseTypeValues);
			}

			itemExt.setIsDeleted(0);
			itemExt.setIsHide(0);
		}
		return itemExt;
	}

	@Override
	public Root getRoomByConfig(String jsonConfig) {
	
		return null;
	}

	@Override
	public boolean InitDesignPlanProduct(String jsonConfig) {
		
		return false;
	}

	@Override
	public boolean UpdateDesignPlanProduct(String jsonConfig) {
		
		return false;
	}

	/**
	 * 填充设计方案配置文件
	 * @param planId
	 * @param context
	 * @return
	 */
	@Override
	public boolean updateDesignPlanConfig(Integer planId,String context,HttpServletRequest request){
		if( planId == null || StringUtils.isBlank(context) ){
			return false;
		}
		DesignPlan designPlan = designPlanService.get(planId);
		if( designPlan == null ){
			logger.debug("没有找到设计方案！（planId=）"+planId);
			return false;
		}
		try {
			// 获取配置路径
			String filePath = "";
			if (designPlan.getConfigFileId() != null) {
				JSONObject jsonObject = JSONObject.fromObject(context);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setRootClass(RoomConfig.class);
				//处理json中key的首字母大写情况
				jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
					@Override
					public String transformToJavaIdentifier(String s) {
						char[] chars = s.toCharArray();
						chars[0] = Character.toLowerCase(chars[0]);
						return new String(chars);
					}
				});
				//过滤在json中Entity没有的属性
				jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
				Root root = new Root();
				Object mainCameraIndexPathObj = jsonObject.get("MainCameraIndexPath");
				if (mainCameraIndexPathObj != null) {
					root.setMainCameraIndexPath(mainCameraIndexPathObj.toString());
				}
				JSONArray jsonArray = (JSONArray) jsonObject.get("RoomConfig");
				//添加房间信息到配置文件中
				RoomExt roomExt = new RoomExt();
				roomExt.setSpaceCommonId(designPlan.getSpaceCommonId());
				roomExt.setMediaType(designPlan.getMediaType());
				roomExt.setDesignPlanId(designPlan.getId());
				roomExt.setDesignTempletId(designPlan.getDesignTemplateId());
				roomExt.setDesignSourceType(designPlan.getDesignSourceType());
				roomExt.setDesignSourceId(designPlan.getDesignId());//设计方案来源ID。如果是通过设计方案创建的则是来源设计方案的ID
//				roomExt.setDesignMode(designPlan.getNightFileId() == null ? null : designPlan.getNightFileId().toString());//设计方案模式。0表示空房；1表示样板房模式；null表示没有选模式
				root.setRoomExt(roomExt);

				//添加产品信息到配置文件中
				if( jsonArray == null || jsonArray.size() == 0  ){
					logger.debug("没有在配置文件中找到产品的信息！！");
					return false;
				}
				List<RoomConfig> roomConfigs = (List<RoomConfig>) JSONArray.toCollection(jsonArray, jsonConfig);
				if (roomConfigs != null && roomConfigs.size() > 0) {
					ItemExt itemExt = null;
					BaseProduct baseProduct = null;
					for (RoomConfig roomConfig : roomConfigs) {
						roomConfig.setItemExt(getItemExt(roomConfig.getPosIndexPath(),roomConfig.getPosName(), roomConfig.getItemCode(), designPlan, request));
					}
				}
				root.setRoomConfig(roomConfigs);

				//把新组装的内容写入配置文件
				//ResFile resFile = resFileService.get(designPlan.getConfigFileId());
				ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());
				/*filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath().replace("/", "\\");*/
				filePath = Utils.getAbsolutePath(resDesign.getFilePath().replace("/", "\\"), Utils.getAbsolutePathType.encrypt);
				/*if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath();
				}*/

				/**TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。**/
				Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
				boolean uploadFtpFlag = false;
				//上传方式为2或者3表示文件在ftp上
				if( ftpUploadMethod == 1 || ftpUploadMethod == 3 ){
					//替换本地
					uploadFtpFlag = Utils.replaceFile(filePath, JSONObject.fromObject(root).toString());
					if( ftpUploadMethod == 3 ) {
						//替换ftp
						uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), JSONObject.fromObject(root).toString());
					}
				}else if( ftpUploadMethod == 2 ){
					//替换ftp
					uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), JSONObject.fromObject(root).toString());
				}
				return uploadFtpFlag;
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			return false;
		}
		return true;
	}
	
	public static JsonConfig getJsonConfig(String type){
		JsonConfig jsonConfig = new JsonConfig();
		if(type.equals("RoomConfig")){
			jsonConfig.setRootClass(RoomConfig.class);
		}else if(type.equals("TaskResult")){
			jsonConfig.setRootClass(TaskResult.class);
		}else{
			jsonConfig.setRootClass(BindPointDataEx.class);
		}
		//处理json中key的首字母大写情况
		jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String s) {
				char[] chars = s.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		//过滤在json中Entity没有的属性
		jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
		return jsonConfig;
	}
	/**
	 * 通过配置内容修改设计方案产品
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean updatePlanProductByConfig(String context,Integer planId,boolean isModifySceneTime){
	    boolean res = updatePlanProductByConfig(context,planId,ContextType.CONTEXT_TYPE_OF_PRODUCT);
	    if(res){
	        
            if (isModifySceneTime){
                DesignPlan newDesignPlan = new DesignPlan();
                newDesignPlan.setId(planId);
                newDesignPlan.setIsChange(new Integer(1));
                newDesignPlan.setSceneModified(Utils.getCurrentTimeMillis());//设计方案的配置文件发生改变要更新时间戳
                newDesignPlan.setVisible(PlanVisibleCode.DESIGN_VISIBLE);
                update(newDesignPlan);
            }
            
	        //如果是渲染的场景对应的临时方案发生改变，此时需要把该方案转为正式的，用户可见的
	        boolean invisible=designPlanRenderService.isInvisible4Render(planId) ;
	        if(invisible){
	        	designPlanRenderService.changeTempDesignPalnVisible(planId);
	        }
	    }
		return res;
	}

	/**
	 * 通过配置内容修改设计方案产品
	 * @param context
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean updatePlanProductByConfig(String context,Integer planId, String contextType){
		if(ContextType.CONTEXT_TYPE_OF_STRUCTURE.equals(contextType)){

		}else if (ContextType.CONTEXT_TYPE_OF_PRODUCT.equals(contextType)){
			//配置文件解密
			try {
				if(PASSWORD_CRYPT_KEY.length() < 8){
					PASSWORD_CRYPT_KEY = String.format("%1$0"+(8-PASSWORD_CRYPT_KEY.length())+"d",0);
				}else{
					PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0,8);
				}
				logger.info("文件解密前截取长度为10参数:context="+context.substring(0,10));
				logger.info("文件解密key="+PASSWORD_CRYPT_KEY);
				context = AESUtil2.decrypt(context,PASSWORD_CRYPT_KEY);
			} catch (Exception e1) {
				logger.error("文件解密异常,contextType="+contextType+",:planId="+planId+ e1.getMessage());
				return false;
			}
		}else if(ContextType.CONTEXT_TYPE_OF_GROUP.equals(contextType)){

		}else{
			logger.error("更新配置文件未识别文档类型:"+contextType);
		}

		JSONObject jsonObject =null;
		try{
			jsonObject = JSONObject.fromObject(context);
		}catch(Exception e){
			logger.error("配置文件context异常,无法转换为json串context="+context+";\n" + "planId="+planId+";\n" + e.getMessage() );
			return false;
		}
		JSONArray jsonArray = null;
		try{
			jsonArray = (JSONArray) jsonObject.get("RoomConfig");
		}catch(Exception e){
			logger.error("配置文件获取RoomConfig异常,无法转换为json串context="+context+ ";\n" +"jsonArray=" +jsonArray+";\n"+"jsonObject="+jsonObject+";\n"+"planId="+planId+";\n" + e.getMessage() );
			return false;
		}
		if( jsonArray == null || jsonArray.size() < 1 ){
			return true;
		}
		try{
			List<RoomConfig> roomConfigs = (List<RoomConfig>) JSONArray.toCollection(jsonArray, getJsonConfig("RoomConfig"));
			List<DesignPlanProduct> planProductList = new ArrayList<>();
			DesignPlanProduct designPlanProduct = null;
			Integer planProductId = 0;
			int updateState=0;/**大于0说明用户有操作，更新设计方案修改时间，否则只是进入 无需修改时间*/
			for( RoomConfig roomConfig : roomConfigs ){
				designPlanProduct = new DesignPlanProduct();
				planProductId = roomConfig.getPlanProductId();
				if( planProductId != null ) {
					designPlanProduct = designPlanProductService.get(planProductId);
					if(designPlanProduct != null) {
						Integer baseProductId = designPlanProduct.getProductId();//当前产品的产品ID

						if ( designPlanProduct != null ) {
							DesignPlanProduct newPlanProduct = new DesignPlanProduct();
							newPlanProduct.setId(designPlanProduct.getId());
							/**更新挂节点*/
							boolean flag=false;
							logger.debug("===============数据库PosIndexPath = " + designPlanProduct.getPosIndexPath() + "; 配置文件PosIndexPath = " + roomConfig.getPosIndexPath());
							if( !designPlanProduct.getPosIndexPath().equals(roomConfig.getPosIndexPath()) ){
								newPlanProduct.setPosIndexPath(roomConfig.getPosIndexPath());
								flag=true;
								updateState=updateState+1;
							}
							/** 更新挂节点名称*/
							if( roomConfig.getPosName() != null ) {
								if (!roomConfig.getPosName().equals(designPlanProduct.getPosName())) {
									newPlanProduct.setPosName(roomConfig.getPosName());
									flag=true;
									updateState=updateState+1;
								}
							}
							if(flag){
								logger.error("newPlanProduct productid==" + newPlanProduct.getProductId());
								designPlanProductService.update(newPlanProduct);
								logger.error("===============通过配置内容更新设计方案产品列表：planProductId = "+ designPlanProduct.getId() +"; newPlanProduct productId = " + newPlanProduct.getProductId() + "; posIndexPath = " + designPlanProduct.getPosIndexPath());
							}
						}
					}
				}
			}
			DesignPlan designPlan = null;
			ResDesign resDesign = null;
			designPlan =  designPlanService.get(planId);
			if(designPlan!=null){
				resDesign = resDesignService.get(designPlan.getConfigFileId());
				if(resDesign!=null){
					ResDesign newResDesign = new ResDesign();
					newResDesign.setId(resDesign.getId());
					newResDesign.setGmtModified(new Date());
					resDesignService.update(newResDesign);
				}
			}
			if(designPlan==null){
				return false;
			}
			return true;
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage()+"\n" + "error===context=" +context + ";planId="+planId);
			return false;
		}
	}

	/**
	 * 删除渲染根目录
	 * @param
	 */
	public void deleteRenderResource(SysTask sysTask,RenderConfig renderConfig,String occu){
		logger.warn("deleteRenderResource方法删除文件：");
		logger.warn(renderConfig);
		if( renderConfig != null ) {
			String storageType = renderConfig.getStorageType();
			String renderRoot = renderConfig.getRenderRoot();
			String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
			String renderPath = "/".equals(renderRoot)?"":renderRoot + occu + scriptPath + sysTask.getBusinessCode();
			logger.warn("storageType:"+storageType);
			if (RenderUtil.STORAGE_TYPE_FTP.equals(storageType)) {
				FtpUploadUtils.deleteDir(renderConfig.getRenderRoot() + occu + scriptPath, sysTask.getBusinessCode(), renderConfig.getRenderServer(), renderConfig.getUserName(), renderConfig.getPassword());
				logger.warn(renderConfig.getRenderRoot() + occu + scriptPath);
			} else if (RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType)) {
				File renderDir = new File(renderPath);
				if( renderDir.exists() ) {
					FileUploadUtils.deleteDir(renderDir);
					logger.warn(renderDir);
				}
			}
		}
	}

	
	/**
	 * 删除渲染根目录
	 * @param
	 */
	public void deleteRenderResource(SysTask sysTask){
		String path=render.getString("3DmaxModelStorageServerHost");
		String renderPath=path+"/"+sysTask.getBusinessCode();
		File renderDir = new File(renderPath);
		if( renderDir.exists() ) {
			FileUploadUtils.deleteDir(renderDir);
			logger.warn(renderDir);
		}
	}
	
	
	/**
	 * 通过plan_id获得设计方案状态
	 * @return
	 */
	@Override
	public int getDesignPlanState(Integer designPlanId) {
		return designPlanMapper.getDesignPlanState(designPlanId);
	}

	/**
	 * 判断是否为硬装产品
	 * @param baseProduct
	 */
	public boolean isHard(BaseProduct baseProduct){
		boolean flag = false;
		String typeValue = baseProduct.getProductTypeValue();
		if( StringUtils.isNotBlank(typeValue) ){
			SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType", Integer.valueOf(typeValue));
			if( "tianh,qiangm,dim".indexOf(dlDic.getValuekey()) > -1 ){
				// 产品小类
				Integer smallType = baseProduct.getProductSmallTypeValue();
				if( smallType != null && smallType.intValue() > 0 ){
					SysDictionary xlDic = sysDictionaryService.findOneByTypeAndValue(dlDic.getValuekey(), smallType);
					if( "dim".equals(dlDic.getValuekey()) || "tul,qiangzhi,qiangz,yaox,bodx,qiangs".indexOf(xlDic.getValuekey()) > -1 ){
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 清理渲染任务：
	 * 1、清理所有为结束状态（异常结束、执行结束、任务终止）的渲染任务所产生的资源文件和渲染图原图
	 * 2、把发送渲染指令后，等待时长超过最大等待时间的任务状态置为异常结束
	 * 3、清理暂停超过3个工作日的渲染任务
	 * 4、清理被系统暂停5次或以上的任务
	 * 5、可通过render.properties配置中sysTask.cleanAction的来决定做什么清理动作。
	 */
	public void cleanRenderTaskJob(){
		initRender();
		/* TODO 清理所有为结束状态的任务资源文件 */
		cleanExceptionTask();

		/* TODO 清理发送渲染指令后，等待时长超出最大等待时间的任务资源文件，并把任务状态置为异常结束 */
		cleanExecutingTask();

		/* TODO 清理手动暂停时间超过配置中允许最大暂停时间的任务。 */
		cleanSuspendTask();

		/* TODO 清理被系统暂停5次或以上的任务 */
		cleanSuspendBySystemTask();
	}

	/**
	 * 清理所有为结束状态的任务
	 */
	@Override
	public void cleanExceptionTask(){
		logger.debug(Utils.getLineNumber() + "开始清理异常渲染任务资源文件...");
		// 获取状态异常的渲染任务
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setStateList(Arrays.asList(SysTaskStatus.END_OF_EXCEPTION,SysTaskStatus.END_OF_EXECUTION,SysTaskStatus.TASK_TERMINATION));
		sysTaskSearch.setOrders(" gmt_create asc ");
		sysTaskSearch.setGmtModifiedEnd(Utils.parseDate(Utils.getYestodayDate(),"yyyy-MM-dd"));
		sysTaskSearch.setLimit(Integer.valueOf(Utils.getPropertyName("render","sysTask.cleanDataCount","5")));// 每次处理数据条数
		logger.debug(Utils.getLineNumber() + "搜索是否存在为结束状态的任务...");
		List<SysTask> sysTasks = sysTaskService.getPaginatedList(sysTaskSearch);



		if( sysTasks != null && sysTasks.size() > 0 ){
			logger.debug(Utils.getLineNumber() + "共找到 " +sysTasks.size()+ " 条需要清理的任务...");
			int taskIndex = 1;
			for( SysTask sysTask : sysTasks ){
				logger.debug(Utils.getLineNumber() + "开始清理第 " + taskIndex + " 条任务。任务ID：" + sysTask.getId() + ", 任务状态：" + sysTask.getState());
				// 修改任务状态和删除资源文件
				sysTask.setRemark(sysTask.getRemark()==null?"":sysTask.getRemark() + "["+Utils.getCurrentDateTime()+":由处理异常定时任务cleanExceptionTask清理.清理原因：任务异常结束、任务执行完毕、任务终止。任务创建时间："+Utils.formatDate(sysTask.getGmtCreate(),"yyyy-MM-dd HH:mm:ss")+";清理时任务状态："+sysTask.getState()+"]");
				updateTaskAndDeleteResources(sysTask,SysTaskStatus.TASK_DESTROYED,true);
				taskIndex++;
			}
		}else{
			logger.debug(Utils.getLineNumber() + "共找到 0 条需要清理的任务...");
		}
	}

	/**
	 * 清理发送渲染指令后，等待时长超出最大等待时间的任务资源文件，并把任务状态置为异常结束
	 */
	@Override
	public void cleanExecutingTask(){
		logger.debug(Utils.getLineNumber() + "开始处理等待超时的执行中的任务资源文件...");
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setState(SysTaskStatus.EXECUTING);// 正在执行
		sysTaskSearch.setOrders(" gmt_create asc ");
		sysTaskSearch.setGmtModifiedEnd(Utils.parseDate(Utils.getYestodayDate(),"yyyy-MM-dd"));
		sysTaskSearch.setLimit(Integer.valueOf(Utils.getPropertyName("render","sysTask.cleanDataCount","5")));// 每次处理数据条数
		logger.debug(Utils.getLineNumber() + "搜索正在执行中的任务...");
		List<SysTask> sysTasks = sysTaskService.getPaginatedList(sysTaskSearch);

		if( sysTasks != null && sysTasks.size() > 0 ){
			logger.debug(Utils.getLineNumber() + "共找到 " +sysTasks.size()+ " 条正在执行中的任务...");
			int taskIndex = 1;
			for( SysTask sysTask : sysTasks ){
				logger.debug(Utils.getLineNumber() + "开始处理第 " + taskIndex + " 条任务。任务ID：" + sysTask.getId());
				// 如果发送指令时间为空，可能正在拷贝预解压文件到渲染机。（目前暂不考虑状态为执行中的，并且为发送渲染指令的超时情况。）
				if( sysTask.getInstructionTime() == null ){
					logger.debug(Utils.getLineNumber() + "当前渲染任务正在拷贝预解压文件到渲染机，不需要处理！");
					continue;
				}
				// 根据不同的渲染级别获取最大等待时长
				Integer maxWaitTime = Integer.valueOf(Utils.getPropertyName("render","app.render.normal.maxWaitMinutes","20"));
				Integer renderingType = 1;// 默认为1	.普通渲染
				if( StringUtils.isNotBlank(sysTask.getAttribute()) ){
					JSONObject jsonObject = JSONObject.fromObject(sysTask.getAttribute());
					if( jsonObject.get("renderingType") != null ){
						renderingType = Integer.valueOf(jsonObject.get("renderingType").toString());
						if( renderingType == 2 ){
							maxWaitTime = Integer.valueOf(Utils.getPropertyName("render","app.render.HD.maxWaitMinutes","120"));
						}
					}
				}
				logger.debug(Utils.getLineNumber() + "当前任务的渲染级别为：" + renderingType + "；允许最大等待时长为：" + maxWaitTime + "分钟...");
				logger.debug(Utils.getLineNumber() + "发送渲染指令时间为：" + Utils.formatDate(sysTask.getInstructionTime(),"yyyy-MM-dd HH:mm:ss"));
				long waitMinutes = RenderUtil.timeDiff(sysTask.getInstructionTime(),new Date());
				logger.debug(Utils.getLineNumber() + "当前任务发送渲染指令后，等待时长为：" + waitMinutes + "分钟...");
				// 如果等待时长超出最大允许等待时长
				if( waitMinutes > maxWaitTime ){
//					// 修改任务状态和删除资源文件
//					updateAndDeleteResources(sysTask,SysTaskStatus.TASK_DESTROYED,false);
					// 修改任务状态
					sysTask.setState(SysTaskStatus.SUSPEND_BY_SYSTEM);// 系统暂停
					sysTask.setRemark(sysTask.getRemark()==null?"":sysTask.getRemark() + "["+Utils.getCurrentDateTime()+":由处理异常定时任务cleanExecutingTask清理.任务创建时间："+Utils.formatDate(sysTask.getGmtCreate(),"yyyy-MM-dd HH:mm:ss")+";清理时任务状态："+sysTask.getState()+"]");
					sysTaskService.update(sysTask);
				}
				taskIndex++;
			}
		}else{
			logger.debug(Utils.getLineNumber() + "共找到 0 条需要清理的任务...");
		}
	}

	/**
	 * 清理暂停时间超过配置中允许最大暂停时间的任务
	 */
	@Override
	public void cleanSuspendTask(){
		logger.debug(Utils.getLineNumber() + "开始处理暂停时间超过最大允许时间的任务资源文件...");
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setOrders(" gmt_create asc ");
		sysTaskSearch.setGmtModifiedEnd(Utils.parseDate(Utils.getYestodayDate(),"yyyy-MM-dd"));
		sysTaskSearch.setLimit(Integer.valueOf(Utils.getPropertyName("render","sysTask.cleanDataCount","5")));// 每次处理数据条数
		Integer maxAllowLiveTime = Integer.valueOf(Utils.getPropertyName("render","app.render.maxSuspendDays","3"));
		sysTaskSearch.setMaxAllowLiveTime(maxAllowLiveTime);// 允许最大暂停时间
		sysTaskSearch.setState(SysTaskStatus.SUSPEND);// 手动暂停
		logger.debug(Utils.getLineNumber() + "搜索暂停时间超过" + maxAllowLiveTime + "天的任务...");
		List<SysTask> sysTasks = sysTaskService.getPaginatedList(sysTaskSearch);
		if( sysTasks != null && sysTasks.size() > 0 ){
			logger.debug(Utils.getLineNumber() + "共找到 " +sysTasks.size()+ " 条暂停超时的任务...");
			int taskIndex = 1;
			for( SysTask sysTask : sysTasks ){
				logger.debug(Utils.getLineNumber() + "开始处理第 " + taskIndex + " 条任务。任务ID：" + sysTask.getId());
				// 修改任务状态和删除资源文件
				sysTask.setRemark(sysTask.getRemark()==null?"":sysTask.getRemark() + "["+Utils.getCurrentDateTime()+":由处理异常定时任务cleanSuspendTask清理.任务创建时间："+Utils.formatDate(sysTask.getGmtCreate(),"yyyy-MM-dd HH:mm:ss")+";清理时任务状态："+sysTask.getState()+"]");
				updateTaskAndDeleteResources(sysTask,SysTaskStatus.TASK_DESTROYED,true);

				taskIndex++;
			}
		}else{
			logger.debug(Utils.getLineNumber() + "共找到 0 条需要清理的任务...");
		}
	}

	/**
	 * 清理被系统暂停5次或以上的任务
	 */
	@Override
	public void cleanSuspendBySystemTask(){
		logger.debug(Utils.getLineNumber() + "开始清理被系统暂停5次或以上的任务...");
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setOrders(" gmt_create asc ");
		sysTaskSearch.setGmtModifiedEnd(Utils.parseDate(Utils.getYestodayDate(),"yyyy-MM-dd"));
		sysTaskSearch.setLimit(Integer.valueOf(Utils.getPropertyName("render","sysTask.cleanDataCount","5")));// 每次处理数据条数
		sysTaskSearch.setStateList(Arrays.asList(SysTaskStatus.SUSPEND_BY_SYSTEM,SysTaskStatus.SUSPEND));// 系统暂停和手动暂停
		sysTaskSearch.setMoreThanExecCount(5);// 大于等于5
		logger.debug(Utils.getLineNumber() + "搜索被系统暂停5次或以上的任务...");
		List<SysTask> sysTasks = sysTaskService.getPaginatedList(sysTaskSearch);

		if( sysTasks != null && sysTasks.size() > 0 ) {
			logger.debug(Utils.getLineNumber() + "共找到 " + sysTasks.size() + " 条被系统暂停5次或以上的任务...");
			int taskIndex = 1;
			for( SysTask sysTask : sysTasks ){
				logger.debug(Utils.getLineNumber() + "开始处理第 " + taskIndex + " 条任务。任务ID：" + sysTask.getId());
				// 修改任务状态和删除资源文件
				sysTask.setRemark(sysTask.getRemark()==null?"":sysTask.getRemark() + "["+Utils.getCurrentDateTime()+":由处理异常定时任务cleanSuspendBySystemTask清理.任务创建时间："+Utils.formatDate(sysTask.getGmtCreate(),"yyyy-MM-dd HH:mm:ss")+";清理时任务状态："+sysTask.getState()+"]");
				updateTaskAndDeleteResources(sysTask,SysTaskStatus.TASK_DESTROYED,true);
				taskIndex++;
			}
		}else{
			logger.debug(Utils.getLineNumber() + "共找到 0 条需要清理的任务...");
		}
	}

	/**
	 * 修改任务状态并删除资源文件
	 * @param sysTask 任务对象
	 * @param taskState 要设置的状态
	 * @param isProcessFile 是否处理资源文件
	 */
	@Override
	public void updateTaskAndDeleteResources(SysTask sysTask,Integer taskState,Boolean isProcessFile){
		if( sysTask == null || sysTask.getId() == null ){
			return;
		}
		if( taskState == null ){
			return;
		}
		if( isProcessFile == null ){
			isProcessFile = false;// 默认不删除资源文件
		}
		// 执行清理操作
		boolean flag = false;
		if( isProcessFile ){
			RenderConfig renderConfig = renderServersMap.get(sysTask.getRenderServer());
			if( renderConfig != null ) {
				flag = doClean(renderConfig, sysTask.getBusinessCode());
			}else{
				logger.debug(Utils.getLineNumber() + "在最新的配置中没有找到该渲染机。[" + sysTask.getRenderServer() + "] 不需要清理资源文件！");
				// 任务中记录的渲染服务器ip在render.properties中没有配置。可能是之前的老数据，并不是通过最新的配置产生的渲染任务。直接修改状态为资源已销毁
				sysTaskService.update(sysTask);
				sysTask.setState(taskState);// 资源已销毁
			}
		}

		// 更新任务状态
		if( flag ) {
			sysTask.setState(taskState);// 任务资源已销毁
			sysTaskService.update(sysTask);
		}

		// 移动任务到历史表中
		if( SysTaskStatus.TASK_DESTROYED == taskState ) {
			removeTaskToHistory(sysTask);
		}
	}

	/**
	 * 执行清理操作
	 * @param renderConfig 渲染配置
	 * @param businessCode 任务CODE
	 * @return
	 */
	public boolean doClean(RenderConfig renderConfig,String businessCode){
		try {
			String cleanAction = Utils.getPropertyName("render", "sysTask.cleanAction", "DELETE");// 清理动作
			logger.debug(Utils.getLineNumber() + "执行 "+ cleanAction +" 清理动作...");
			String osType = FileUploadUtils.SYSTEM_FORMAT;// 资源存储方式
			if ("DELETE".equals(cleanAction)) {
				// 删除
				return deleteResources(renderConfig,businessCode,osType);
			} else if ("BACKUPS".equals(cleanAction)) {
				// 备份
				return backupsResources(renderConfig,businessCode,osType);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.debug(Utils.getLineNumber() + e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * 删除资源文件
	 * @param renderConfig 渲染配置
	 * @param businessCode 任务CODE
	 * @param osType 资源存储方式
	 * @return
	 */
	public boolean deleteResources(RenderConfig renderConfig,String businessCode,String osType){
		try {
			String renderRoot = renderConfig.getRenderRoot();// 渲染资源存储根目录
			String relativeDir = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender");// 渲染资源存储相对目录
			String maxScript = Utils.getPropertyName("render", "script.path", "/MaxScript/");// 脚本目录（资源文件在与脚本在同目录）
			String resRealPath = renderRoot + relativeDir + maxScript + businessCode;// 资源文件绝对路径

			if ("windows".equals(osType)) {
				resRealPath = resRealPath.replace("/", "\\");
			}

			logger.debug(Utils.getLineNumber() + "当前任务资源文件存储目录为：" + resRealPath);

			// 删除资源文件夹
			FileUploadUtils.deleteDir(new File(resRealPath));
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(Utils.getLineNumber() + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 备份资源文件
	 * @return
	 */
	public boolean backupsResources(RenderConfig renderConfig,String businessCode,String osType){
		try{
			String renderRoot = renderConfig.getRenderRoot();// 渲染资源存储根目录
			String relativeDir = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender");// 渲染资源存储相对目录
			String maxScript = Utils.getPropertyName("render", "script.path", "/MaxScript/");// 脚本目录（资源文件在与脚本在同目录）
			String backupsDir = Utils.getPropertyName("render", "script.backups.path", "/Backups/");// 备份路径
			String resRealPath = renderRoot + relativeDir + maxScript + businessCode;// 资源文件绝对路径
			String backupsPath = renderRoot + relativeDir + backupsDir + businessCode;// 备份路径

			if("windows".equals(osType)) {
				resRealPath = resRealPath.replace("/", "\\");
				backupsPath = backupsPath.replace("/", "\\");
			}

			logger.debug(Utils.getLineNumber() + "当前任务资源文件存储目录为：" + resRealPath);
			logger.debug(Utils.getLineNumber() + "备份到：" + backupsPath);

			// 移动到备份目录
			RenderUtil.moveDir(resRealPath,backupsPath);
		}catch( Exception e ){
			e.printStackTrace();
			logger.debug(Utils.getLineNumber() + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 把任务移动到历史表中
	 */
	@Override
	public void removeTaskToHistory(SysTask sysTask){
		if( sysTask == null ){
			logger.debug(Utils.getLineNumber() + "没有找到此行需要移到备份表中的数据");
			return;
		}
		logger.debug(Utils.getLineNumber() + "开始移动状态为【已销毁】的任务到备份表");
		try {
			SysTaskRecord sysTaskRecord = new SysTaskRecord();
			BeanUtils.copyProperties(sysTaskRecord,sysTask);
			sysTaskRecord.setId(null);
			// 添加到任务历史表中
			sysTaskRecordService.add(sysTaskRecord);
			// 删除任务表数据
			sysTaskService.delete(sysTask.getId());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.debug(Utils.getLineNumber() + e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.debug(Utils.getLineNumber() + e.getMessage());
		} catch( Exception e ){
			e.printStackTrace();
			logger.debug(Utils.getLineNumber() + e.getMessage());
		}
	}

	/**
	 * 渲染成功/失败发送消息
	 * @param task
	 * @throws Exception 
	 */
	public Integer sendRenderMessage(SysTask task,Integer state,Integer picId) throws Exception{
		logger.debug("准备发送websocket消息......");
		Integer messageId = 0;
		//失败之后发送信息
		Integer userId=null;
		DesignPlan designPlan_=null;
		designPlan_=designPlanService.get(task.getBusinessId());
		if(designPlan_ != null){
			userId = designPlan_.getUserId();
		}
		SysUser sysUser = sysUserService.get(userId);
		if(sysUser != null){
			logger.debug(task.getExecCount()+"+++++++++++++++++++++++++++++--------------------------");
			BaseMessage baseMessage= new BaseMessage();
			baseMessage.setUserId(userId);
//			baseMessage.setBusinessTypeId(task.getRenderType() == null? RenderTypeCode.COMMON_720_LEVEL : task.getRenderType());
			baseMessage.setBusinessTypeId(5);
			baseMessage.setBusinessObjId(designPlan_.getId());
			baseMessage.setBusinessObjType("design_plan");
			baseMessage.setMessageType(0);
			baseMessage.setCreator(sysUser.getNickName());
			baseMessage.setGmtCreate(new Date());
			baseMessage.setModifier(sysUser.getNickName());
			baseMessage.setGmtModified(new Date());
			baseMessage.setIsDeleted(0);
			if(picId!=null&&picId>0){
				baseMessage.setNuma1(picId);
			}
			if(state==2){
				if( task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL ) {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode() + "</color>" + ProductType.PANORAMA_RENDER_NAME + "成功");
				}else if( task.getRenderType().intValue() == RenderTypeCode.ROAM_720_LEVEL ){
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode() + "</color>" + ProductType.ROAM_PANORAMA_RENDER_NAME + "成功");
				}else if( task.getRenderType().intValue() == RenderTypeCode.COMMON_VIDEO ){
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode() + "</color>" + ProductType.ROAM_VIDEO_RENDER_NAME + "成功");
				}else{
					baseMessage.setContent("<color='#01017A'>"+designPlan_.getPlanName()+designPlan_.getPlanCode()+"</color>"+ProductType.PICTURE_RENDER_NAME+"成功");
				}
			}else{
				if( task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL ) {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode() + "</color>" + ProductType.PANORAMA_RENDER_NAME + "失败,请联系客服,渲染费用已经退到您的账户余额");
				}else if( task.getRenderType().intValue() == RenderTypeCode.ROAM_720_LEVEL ){
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode() + "</color>" + ProductType.ROAM_PANORAMA_RENDER_NAME + "失败,请联系客服,渲染费用已经退到您的账户余额");
				}else if( task.getRenderType().intValue() == RenderTypeCode.COMMON_VIDEO ){
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode() + "</color>" + ProductType.ROAM_VIDEO_RENDER_NAME + "失败,请联系客服,渲染费用已经退到您的账户余额");
				}else{
					baseMessage.setContent("<color='#01017A'>"+designPlan_.getPlanName()+designPlan_.getPlanCode()+"</color>"+ProductType.PICTURE_RENDER_NAME+"失败,请联系客服,渲染费用已经退到您的账户余额");
				}
			}
			messageId = baseMessageService.add(baseMessage);
		
			BaseMessageRecieve baseMessageRecieve= new BaseMessageRecieve();
			baseMessageRecieve.setMessageId(messageId);
			baseMessageRecieve.setUserId(userId);
			baseMessageRecieve.setIsReaded(0);
			baseMessageRecieve.setCreator(sysUser.getNickName());
			baseMessageRecieve.setGmtCreate(new Date());
			baseMessageRecieve.setModifier(sysUser.getNickName());
			baseMessageRecieve.setGmtModified(new Date());
			baseMessageRecieve.setIsDeleted(0);
			baseMessageRecieveService.add(baseMessageRecieve);
		}
		MessageResponse messageResponse = new MessageResponse();
		logger.debug("=======================任务状态："+state + "        " +(state.intValue() == 2));
		
		String houseName = "";
		SpaceCommon spaceCommon = null;
		spaceCommon = spaceCommonService.get(designPlan_.getSpaceCommonId());
		if(spaceCommon!=null){
			Integer spaceFunctionId = spaceCommon.getSpaceFunctionId();
			if(spaceFunctionId!=null){
				SysDictionary sysDictionary = new SysDictionary();
				sysDictionary.setValue(spaceFunctionId);
				sysDictionary.setType("houseType");
				sysDictionary.setIsDeleted(0);
				List<SysDictionary>houseNameList = sysDictionaryService.getList(sysDictionary);
				if(houseNameList!=null&&houseNameList.size()==1){
					houseName = houseNameList.get(0).getName();
				}
			}
		}
		String renderTypeName = "";
		if(task.getRenderType()!=null){
			if(task.getRenderType().intValue() == RenderTypeCode.SCREEN_OF_PIC){
				renderTypeName = ",高清图片";
			}else if(task.getRenderType().intValue() == RenderTypeCode.COMMON_PICTURE_LEVEL){
				renderTypeName = ",照片级普通 ";
			}else if(task.getRenderType().intValue() == RenderTypeCode.HD_PICTURE_LEVEL){
				renderTypeName = ",照片级高清";
			}else if(task.getRenderType().intValue() == RenderTypeCode.ULTRA_HD_PICTURE_LEVEL){
				renderTypeName = ",照片级超高清";
			}else if(task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL){
				renderTypeName = ",720度普通";
			}else if(task.getRenderType().intValue() == RenderTypeCode.HD_720_LEVEL){
				renderTypeName = ",720度高清";
			}else if(task.getRenderType().intValue() == RenderTypeCode.COMMON_VIDEO){
				renderTypeName = ",普通漫游视频";
			}else if(task.getRenderType().intValue() == RenderTypeCode.HD_VIDEO){
				renderTypeName = ",高清漫游视频";
			}else if( task.getRenderType().intValue() == RenderTypeCode.ROAM_720_LEVEL ){
				renderTypeName = ",多点全景";
			}
		}
		
		
		if( state.intValue() == 2 ){
			messageResponse.setSuccess(true);
			//messageResponse.setMessage("渲染成功!");
			String message = houseName + designPlan_.getPlanCode() + renderTypeName + "渲染成功！";
			messageResponse.setMessage(message);
			 
		}else{
			messageResponse.setSuccess(false);
			//messageResponse.setMessage("渲染失败!");
			String msg ="渲染失败,本次扣款将退回到您的账户里！";
			PayOrder findOneByTaskId = payOrderService.findOneByTaskId(task.getId());
			if(findOneByTaskId != null){
				if(findOneByTaskId.getTotalFee() != null &&  findOneByTaskId.getTotalFee() == 0 && PayType.PREDEPOSIT == findOneByTaskId.getPayType()){
					msg="渲染失败";
				}
			}
			String message = houseName + designPlan_.getPlanCode() + renderTypeName + msg;
			messageResponse.setMessage(message);
		}
		messageResponse.setType(1);
		Map<String,String>map=new HashMap<String,String>();
		map.put("designPlanId", designPlan_.getId()+"");
		messageResponse.setObj(map);
		JSONObject jsonObject = JSONObject.fromObject(messageResponse);
		String message=jsonObject.toString();
		//noticeWsServer(userId+"",message);
		try {
			WebSocketUtils.sendMessage("app.webSocket.message", userId.toString(), message);
		}catch(Exception e){
			logger.error("message websocket链接异常"+e);
			// 发送邮件
			List<SysUser> warningUserList = sysUserService.getUserByRoleCode("RENDER_WARNING");
			if( warningUserList != null && warningUserList.size() > 0 ){
				StringBuffer toEmailsStr = new StringBuffer();
				int count = 0;
				for( SysUser warningUser : warningUserList ){
					if( com.nork.common.util.StringUtils.isNotBlank(warningUser.getEmail()) ) {
						if( count < warningUserList.size() ) {
							toEmailsStr.append(warningUser.getEmail() + ",");
						}else{
							toEmailsStr.append(warningUser.getEmail());
						}
						count++;
					}
				}
				logger.error("to mail:"+toEmailsStr.toString());
				if( toEmailsStr.length() > 0 ) {
					String[] toEmails = toEmailsStr.toString().split(",");
					StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
					stringBuffer.append("websocket服务器可能已中断,"+ WebSocketUtils.webSocket.getString("app.webSocket.message"));
					String subject = "【websocket链接异常】";
					SendEmail.massSend(toEmails, subject, stringBuffer.toString());
				}
			}else{
				logger.error("warningUserList is null");
			}
		}
		return messageId;
	}

	/**
	 * 更新渲染任务状态
	 * @return
	 * @throws Exception 
	 */
	public Object updateRenderTaskStateJob() throws Exception{
//		logger.warn("开始检测云渲染任务状态并回填。。。");
//		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		SysTask sys_task = new SysTask();
		sys_task.setIsDeleted(0);
		//等待执行0
		sys_task.setState(SysTaskStatus.RY_EXECUTING);
//		sysTaskSearch.setStateList(Arrays.asList(RenderTaskStatus.ANALYSING,RenderTaskStatus.ANALYSED,RenderTaskStatus.PREPROCESSING,RenderTaskStatus.START));
		//瑞云渲染方式
		sys_task.setRenderWay("ruiyun");
		sys_task.setOrder(" priority,gmt_create ");
		sys_task.setOrderNum(" asc ");
//		sysTaskSearch.setLimit(1);
//		List<SysTask> list = sysTaskService.getPaginatedList(sysTaskSearch);
		List<SysTask> list = sysTaskService.getList(sys_task);
		if( list != null && list.size() > 0 ){
			for (SysTask sysTask : list) {
				if( sysTask != null && sysTask.getRenderTaskId() != null ){
					try {
						String uri = "https://www2.renderbus.com/api/v1/task/";
						RequestHeadEntity headEntity = new RequestHeadEntity();
						headEntity.setAccess_key("MjAxNjEyMDgxNDIxMjE4OTA1Njg=");
						headEntity.setAccount("panda");
						headEntity.setMsg_locale("zh");
						headEntity.setAction("query_task");
						
						RequestQueryTaskEntity queryTask = new RequestQueryTaskEntity();
						queryTask.setTask_id(sysTask.getRenderTaskId().toString());
						Map<String, Object> request = new HashMap<String, Object>();
						request.put("head", headEntity);
						request.put("body", queryTask);
						
						CloseableHttpResponse response = HttpClientUtil.getResponse(uri, request);
						// 检验状态码，如果成功接收数据
						int code = response.getStatusLine().getStatusCode();
						
						if (code == 200) {
							BufferedReader br = null;
							try {
								br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
								String result = br.readLine();
								logger.warn("result:"+result);
								JSONObject obj = JSONObject.fromObject(result);
								JSONObject dataList = JSONObject.fromObject(obj.getString("body"));
								if( dataList != null ){
									JSONArray jsonArray = (JSONArray) dataList.get("data");
									JsonConfig jsonConfig = getJsonConfig("TaskResult");
									List<TaskResult> taskList = (List<TaskResult>)JSONArray.toCollection(jsonArray,jsonConfig);
									for(TaskResult taskResult : taskList){
										SysTask sysTask_s = new SysTask();
										sysTask_s.setId(sysTask.getId());
										sysTask_s.setState(RenderTaskStatus.getTaskState(taskResult.getTask_status()));
										sysTask_s.setRemark(RenderTaskStatus.getTaskRemark(taskResult.getTask_status()));
										sysTask_s.setGmtModified(new Date());
										sysTask_s.setRenderMoney(taskResult.getFee());
										sysTask_s.setInstructionTime(new SimpleDateFormat(Utils.DATE_TIME).parse(taskResult.getSubmit_time()));
										sysTaskService.update(sysTask_s);
									}
								}
							} finally {
								response.close();
								if(br != null) {
									br.close();
								}
							}
						}else{
							logger.debug("无效http返回code码============code:"+code);
						}
					} catch (ClientProtocolException e) {
						logger.debug(e.getMessage());
					} catch (IOException e) {
						logger.debug(e.getMessage());
					} catch (Exception e) {
						logger.debug(e.getMessage());
					}
				}else{
					continue;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 下载ry任务渲染图
	 * @return
	 * @throws Exception 
	 */
	public Object downloadRenderPicJob_old() throws Exception{
//		logger.warn("开始检测云渲染任务状态并下载渲染图。。。");
		initRender();
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setIsDeleted(0);
		//瑞云渲染完成开始找图
		sysTaskSearch.setState(SysTaskStatus.RENDER_END);
//		sysTaskSearch.setStateList(Arrays.asList(RenderTaskStatus.SYSTEM_DONE));
		//瑞云渲染方式
		sysTaskSearch.setRenderWay("ruiyun");
		//sysTaskSearch.setPicDownloadState(0);
		sysTaskSearch.setOrder(" priority,gmt_create ");
		sysTaskSearch.setOrderNum(" asc ");
		sysTaskSearch.setLimit(1);
		List<SysTask> list = sysTaskService.getPaginatedList(sysTaskSearch);
		if( Lists.isNotEmpty(list) ){
			SysTask sysTask = list.get(0);
			if( sysTask != null && sysTask.getRenderTaskId() != null ){
				String osType = FileUploadUtils.SYSTEM_FORMAT;
				String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
				String maxRender = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
				String renderPic = Utils.getPropertyName("render", "app.3dmax.render.upload.path", "/MaxRender/RenderPic/");
				// 脚本模版存储目录
		        String scriptTemplateDir = Utils.getPropertyName("render", "script.template.path", "/MaxTemplate/");
				RenderConfig renderConfig = renderServersMap.get(sysTask.getRenderServer());
				String renderPathBat = renderConfig.getRenderRoot() + maxRender + scriptPath + sysTask.getBusinessCode()+"/";
				String scriptTemplatePath = renderConfig.getRenderRoot() + maxRender + scriptTemplateDir;// 脚本模版存放路径
				logger.debug("下载渲染图批处理路径："+renderPathBat);
				if( "windows".equals(osType) ){
					renderPathBat = renderPathBat.replace("/", "\\");
					scriptTemplatePath = scriptTemplatePath.replace("/", "\\");
				}
				String downPicBat = "downloadRenderPic.bat";
				File f = new File(scriptTemplatePath+downPicBat);
				if(f.exists()){
					String batPath = renderPathBat+downPicBat; 
					FileUploadUtils.fileCopy(scriptTemplatePath+downPicBat,batPath);
					File file = new File(batPath);
					if( file.exists() ){
			            // 读取模板内容
			            String downContext = FileUploadUtils.getFileContext(batPath);
			            // 生成脚本文件
			            downContext = downContext.replaceAll("\\{renderTaskId\\}", String.valueOf(sysTask.getRenderTaskId())).replaceAll("\\{designPlanCode\\}", sysTask.getBusinessCode());
			            FileUploadUtils.writeTxtFile(batPath, downContext);
			            logger.debug("生成成功！"+batPath);
			        }else{
			        	logger.debug("找不到这个目录文件："+batPath+"！");
			        	return null;
			        }
				}
				boolean flag = false;
				//0去下载
				if(sysTask.getPicDownloadState() == 0){
					flag = RenderUtil.sendRequest("openbat:"+sysTask.getBusinessCode(),sysTask.getRenderServer());
				}

				if(!flag && sysTask.getPicDownloadState() == 0){
					logger.debug("下载批处理执行失败！");
					return null;
//					logger.debug("任务"+sysTask.getId()+"渲染图下载失败！");
//					sysTask.setPicDownloadState(2);
//					sysTaskService.update(sysTask);
				}else{
					String renderPathPic = renderConfig.getRenderRoot() + renderPic + sysTask.getRenderTaskId()+"_"+sysTask.getBusinessCode()+"/";
					if( "windows".equals(osType) ){
						renderPathPic = renderPathPic.replace("/", "\\");
					}
					File fl = new File(renderPathPic);
//					判断文件是否存在
					if( fl.isDirectory() ){
						if( fl.listFiles().length > 0 ){
							SysTask sysTask_s = new SysTask();
							sysTask_s.setId(sysTask.getId());
							sysTask_s.setPicDownloadState(1);
							sysTask_s.setState(SysTaskStatus.EXECUTING);
							sysTask_s.setGmtModified(new Date());
							sysTaskService.update(sysTask_s);
							logger.warn("渲染图下载成功"+renderPathPic);
							
						}else{
							if(System.currentTimeMillis()-sysTask.getGmtModified().getTime()>300000 ){
								SysTask sysTask_s = new SysTask();
								sysTask_s.setId(sysTask.getId());
								sysTask_s.setPicDownloadState(2);
								sysTask_s.setState(SysTaskStatus.END_OF_EXECUTION);
								sysTaskService.update(sysTask_s);
								logger.debug("找不到"+sysTask.getId()+"的渲染图！");
							}else{
								SysTask sysTask_s = new SysTask();
								sysTask_s.setId(sysTask.getId());
								sysTask_s.setPicDownloadState(3);//图片下载中。。
								sysTaskService.update(sysTask_s);
							}
						}
					}else{
						logger.debug("任务"+sysTask.getId()+"渲染图下载失败！");
					}
				}
			}
		}
		return null;
	}

	/**
	 * 下载ry任务渲染图
	 * @return
	 * @throws Exception
	 */
	public Object downloadRenderPicJob() throws Exception{
		/*logger.warn("开始检测云渲染任务状态并下载渲染图。。。");*/
		initRender();
//		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		SysTask sys_task = new SysTask();
		sys_task.setIsDeleted(0);
		//瑞云渲染完成开始找图
		sys_task.setState(SysTaskStatus.RENDER_END);
//		sysTaskSearch.setStateList(Arrays.asList(RenderTaskStatus.SYSTEM_DONE));
		//瑞云渲染方式
		sys_task.setRenderWay("ruiyun");
		//sysTaskSearch.setPicDownloadState(0);
		sys_task.setOrder(" priority,gmt_create ");
		sys_task.setOrderNum(" asc ");
//		sysTaskSearch.setLimit(1);
//		List<SysTask> list = sysTaskService.getPaginatedList(sysTaskSearch);
		List<SysTask> list = sysTaskService.getList(sys_task);
		for (SysTask sysTask : list) {
//			SysTask sysTask = list.get(0);
			if( sysTask.getRenderTaskId() != null ){
				String osType = FileUploadUtils.SYSTEM_FORMAT;
				String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
				String maxRender = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
				String renderPic = Utils.getPropertyName("render", "app.3dmax.render.upload.path", "/MaxRender/RenderPic/");
				// 脚本模版存储目录
				String scriptTemplateDir = Utils.getPropertyName("render", "script.template.path", "/MaxTemplate/");
				RenderConfig renderConfig = renderServersMap.get(sysTask.getRenderServer());
				String renderPathBat = renderConfig.getRenderRoot() + maxRender + scriptPath + sysTask.getBusinessCode()+"/";
				String scriptTemplatePath = renderConfig.getRenderRoot() + maxRender + scriptTemplateDir;// 脚本模版存放路径
				logger.debug("下载渲染图批处理路径："+renderPathBat);
				if( "windows".equals(osType) ){
					renderPathBat = renderPathBat.replace("/", "\\");
					scriptTemplatePath = scriptTemplatePath.replace("/", "\\");
				}
				String downPicBat = "downloadRenderPic.bat";
				File f = new File(scriptTemplatePath+downPicBat);
				if(f.exists()){
					String batPath = renderPathBat+downPicBat;
					FileUploadUtils.fileCopy(scriptTemplatePath+downPicBat,batPath);
					File file = new File(batPath);
					if( file.exists() ){
						// 读取模板内容
						String downContext = FileUploadUtils.getFileContext(batPath);
						// 生成脚本文件
						downContext = downContext.replaceAll("\\{renderTaskId\\}", String.valueOf(sysTask.getRenderTaskId())).replaceAll("\\{designPlanCode\\}", sysTask.getBusinessCode());
						FileUploadUtils.writeTxtFile(batPath, downContext);
						logger.debug("生成成功！"+batPath);
					}else{
						logger.debug("找不到这个目录文件："+batPath+"！");
						return null;
					}
				}
				boolean flag = false;
				//0去下载
				if(sysTask.getPicDownloadState() == 0){
					flag = RenderUtil.sendRequest("openbat:"+sysTask.getBusinessCode(),sysTask.getRenderServer());
				}
				if(!flag && sysTask.getPicDownloadState() == 0){
					logger.debug("下载批处理执行失败！");
					return null;
//					logger.debug("任务"+sysTask.getId()+"渲染图下载失败！");
//					sysTask.setPicDownloadState(2);
//					sysTaskService.update(sysTask);
				}else{
					String renderPathPic = renderConfig.getRenderRoot() + renderPic + sysTask.getRenderTaskId()+"_"+sysTask.getBusinessCode()+"/";
					if( "windows".equals(osType) ){
						renderPathPic = renderPathPic.replace("/", "\\");
					}
					File fl = new File(renderPathPic);
//					判断文件是否存在
					if( fl.isDirectory() ){
						if( fl.listFiles().length > 0 ){
							SysTask sysTask_s = new SysTask();
							sysTask_s.setId(sysTask.getId());
							sysTask_s.setPicDownloadState(1);
							sysTask_s.setState(SysTaskStatus.EXECUTING);
							sysTask_s.setGmtModified(new Date());
							sysTask_s.setPicDownloadTime(new Date());
							sysTaskService.update(sysTask_s);
							logger.warn("渲染图下载成功"+renderPathPic);
						}else{
							//超过两分钟默认为该任务渲染出现异常
							if(System.currentTimeMillis()-sysTask.getGmtModified().getTime()>120000 ){
//								RenderUtil.sendRequest("openbat:"+sysTask.getBusinessCode(),sysTask.getRenderServer());
								SysTask sysTask_s = new SysTask();
								sysTask_s.setId(sysTask.getId());
								sysTask_s.setPicDownloadState(2);
								sysTask_s.setState(SysTaskStatus.END_OF_EXECUTION);
								sysTaskService.update(sysTask_s);
								logger.debug("找不到"+sysTask.getId()+"的渲染图！");
							}else{
								//以防万一最后十秒继续调用下载批处理
								if( System.currentTimeMillis()-sysTask.getGmtModified().getTime()>110000 ){
									RenderUtil.sendRequest("openbat:"+sysTask.getBusinessCode(),sysTask.getRenderServer());
								}
								SysTask sysTask_s = new SysTask();
								sysTask_s.setId(sysTask.getId());
								sysTask_s.setPicDownloadState(3);//图片下载中。。
								sysTaskService.update(sysTask_s);
							}
						}
					}else{
						logger.debug("任务"+sysTask.getId()+"渲染图下载失败！");
					}
				}
			}
		}
		return null;
	}
	
	
	public JSONObject assembleRenderConfig(SysTask renderTask,RenderConfig renderConfig){
		JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("success", true);
		String params = "";
		String paramFilePath = renderTask.getParams();
		if( StringUtils.isNotBlank(paramFilePath) ){
			Integer uploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
			if( uploadMethod == 1 ){
				/*params = FileUploadUtils.getFileContext(Constants.UPLOAD_ROOT + paramFilePath);*/
				params = FileUploadUtils.getFileContext(Utils.getAbsolutePath(paramFilePath, Utils.getAbsolutePathType.encrypt));
			}else{
				params = FtpUploadUtils.getFileContext(paramFilePath);
			}
		}
		if( StringUtils.isNotBlank(params) ){
//			RenderConfig renderConfig = renderServersMap.get(renderTask.getRenderServer());
			if(renderConfig==null){
				logger.debug("renderServersMap="+renderServersMap.toString()+";renderTask.getRenderServer()="+renderTask.getRenderServer());
				//renderTask.setRemark("渲染配置文件未找到");
				SysTask sysTask_s = new SysTask();
				sysTask_s.setId(renderTask.getId());
				sysTask_s.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，执行失败！渲染配置文件未找到.");
				sysTask_s.setState(SysTaskStatus.END_OF_EXCEPTION);
				sysTask_s.setGmtModified(new Date());
				sysTaskService.update(sysTask_s);
				jsonObject.remove("success");
                jsonObject.accumulate("success", false);
                jsonObject.accumulate("message", "渲染配置文件未找到！");
                logger.debug("渲染配置文件未找到！");
                return jsonObject;
			}
			// 解压文件存储方式
			String storageType = renderConfig.getStorageType();
			// 生成渲染配置文件
			boolean dataFlag = false;
			if( RenderUtil.STORAGE_TYPE_RUIYUN.equals(storageType) ){
				dataFlag = RenderUtil.createDataFile(params, renderConfig.getRenderRoot());
			}
			if( !dataFlag ){
				if(JOB_LOG_FLAG) {
					logger.debug("生成渲染配置文件Data.txt失败......");
				}
				SysTask sysTask_s = new SysTask();
				sysTask_s.setId(renderTask.getId());
				sysTask_s.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，执行失败！生成渲染配置文件Data.txt失败.");
				sysTask_s.setState(SysTaskStatus.END_OF_EXCEPTION);
				sysTask_s.setGmtModified(new Date());
				sysTaskService.update(sysTask_s);
				jsonObject.remove("success");
                jsonObject.accumulate("success", false);
                jsonObject.accumulate("message", "生成渲染配置文件Data.txt失败");
                return jsonObject;
			}
			//解压渲染所需文件
			if(JOB_LOG_FLAG) {
				logger.debug("开始解压渲染所需文件，planId：" + renderTask.getBusinessId() + ";code=" + renderTask.getBusinessCode());
			}
			Integer planId=renderTask.getBusinessId();
			if(renderTask.getPlanId()!=null&&renderTask.getPlanId()>0)
				planId=renderTask.getPlanId();
			//JSONObject jsonObject = (JSONObject)startRender(renderTask.getBusinessId(), renderTask.getBusinessCode(),renderConfig,null);
			jsonObject = (JSONObject)startRender2(planId, renderTask.getBusinessCode(),renderConfig);
			if( !(Boolean)jsonObject.get("success") ){
				//失败之后发送信息
				try {
					sendRenderMessage(renderTask,1,null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				SysTask sysTask_s = new SysTask();
				sysTask_s.setId(renderTask.getId());
				sysTask_s.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，执行失败！" + jsonObject.get("message")+"！]");
				sysTask_s.setGmtModified(new Date());
				sysTask_s.setState(SysTaskStatus.END_OF_EXCEPTION);
				sysTaskService.update(sysTask_s);
				jsonObject.remove("success");
                jsonObject.accumulate("success", false);
                jsonObject.accumulate("message", "复制渲染文件失败");
                return jsonObject;
			}
			/*2016.11.07修改为预解压->end*/
			String osType = FileUploadUtils.SYSTEM_FORMAT;
//			String occu = Utils.getValue("design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
			String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
			String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
			if( "windows".equals(osType) ){
				occu = occu.replace("/", "\\");
			}
			try{
				//调用渲染
				if(JOB_LOG_FLAG) {
					logger.info("调用渲染服务器，发送渲染指令。。。");
				}
				// 生成3dmax渲染脚本
				logger.debug("----------------渲染方式："+renderTask.getRenderWay());
				JSONObject createScriptResult = RenderUtil.createScript(renderConfig, renderTask.getBusinessCode(),params);
				if( createScriptResult != null && !createScriptResult.getBoolean("success") ){
					SysTask sysTask_s = new SysTask();
					sysTask_s.setId(renderTask.getId());
					sysTask_s.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
					sysTask_s.setRemark((StringUtils.isBlank(renderTask.getRemark()) ? "" : renderTask.getRemark() + "\r\n") + "[无法连接渲染服务器或给服务器" + renderTask.getRenderServer() + "发送open命令失败，任务终止]");
					sysTask_s.setGmtModified(new Date());
					sysTaskService.update(sysTask_s);

					//失败之后发送信息
					logger.debug("ry执行渲染任务，发送渲染指令失败！");
					logger.debug("给app发送websoket消息：");
						// 发送消息
					sendRenderMessage(renderTask,1,null);
					//删除目录
					String renderPath = renderConfig.getRenderRoot() + occu + scriptPath + renderTask.getBusinessCode();
					if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+"/"+renderPath.replaceAll("\\\\","/") + renderTask.getBusinessCode());
						FtpUploadUtils.deleteDir("/"+renderPath.replaceAll("\\\\","/"),renderTask.getBusinessCode(),renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
						logger.warn(Utils.getLineNumber() + "删除目录：" + renderPath);
						RenderUtil.deleteFile(renderPath);
					}
					jsonObject.remove("success");
	                jsonObject.accumulate("success", false);
	                jsonObject.accumulate("message", "创建脚本文件失败");
	                return jsonObject;
				}
				/*boolean flag = false;
				if( createScriptResult != null && createScriptResult.getBoolean("success") ){
					flag = RenderUtil.sendRequest("open:"+renderTask.getBusinessCode(),renderTask.getRenderServer());
				}else{
					logger.error("["+renderTask.getBusinessCode()+"]生成脚本文件失败！"+createScriptResult.get("message"));
				}
//				boolean flag = RenderUtil.sendRequest("open",renderTask.getRenderServer());
				if(flag){
					//状态修改为执行中
					renderTask.setState(SysTaskStatus.EXECUTING);
					renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark()) ? "" : renderTask.getRemark() + "\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + renderTask.getExecCount() + "次渲染，已将渲染指令提交给渲染服务器，等待渲染执行结果返回。。。]");
					renderTask.setGmtModified(new Date());
					renderTask.setInstructionTime(new Date());// 记录发送指令的时间
					sysTaskService.update(renderTask);
					RenderUtil.flag = false;
				}else{
					renderTask.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
					renderTask.setRemark((StringUtils.isBlank(renderTask.getRemark()) ? "" : renderTask.getRemark() + "\r\n") + "[无法连接渲染服务器或给服务器" + renderTask.getRenderServer() + "发送open命令失败，任务终止]");
					renderTask.setGmtModified(new Date());
					sysTaskService.update(renderTask);

					//失败之后发送信息
					logger.error("第"+renderTask.getExecCount()+"次执行渲染任务，发送渲染指令失败！");
					logger.error("当前为第"+renderTask.getExecCount()+"次执行，共允许执行"+allowExecCount+"次！");
					logger.error("给app发送websoket消息：");
					if(renderTask.getExecCount().intValue() >= allowExecCount.intValue()){
						// 发送消息
						sendFailMessage(renderTask,1,null);
					}

					//删除目录
					String renderPath = renderConfig.getRenderRoot() + occu + scriptPath + renderTask.getBusinessCode();
					if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
						logger.warn(Utils.getLineNumber()+"删除目录："+"/"+renderPath.replaceAll("\\\\","/") + renderTask.getBusinessCode());
						FtpUploadUtils.deleteDir("/"+renderPath.replaceAll("\\\\","/"),renderTask.getBusinessCode(),renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
					}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
						logger.warn(Utils.getLineNumber() + "删除目录：" + renderPath);
						RenderUtil.deleteFile(renderPath);
					}
				
					return null;
				}*/
			}catch (Exception e){
				//删除解压出来的文件
				e.printStackTrace();
				//删除目录
				String renderPath = renderConfig.getRenderRoot() + occu + scriptPath + renderTask.getBusinessCode();
				if( RenderUtil.STORAGE_TYPE_FTP.equals(storageType) ){
					logger.warn(Utils.getLineNumber()+"删除目录："+"/"+renderPath.replaceAll("\\\\","/") + renderTask.getBusinessCode());
					FtpUploadUtils.deleteDir("/"+renderPath.replaceAll("\\\\","/"),renderTask.getBusinessCode(),renderConfig.getRenderServer(),renderConfig.getUserName(),renderConfig.getPassword());
				}else if( RenderUtil.STORAGE_TYPE_LOCAL.equals(storageType) ){
					logger.warn(Utils.getLineNumber() + "删除目录：" + renderPath);
					RenderUtil.deleteFile(renderPath);
				}
				SysTask sysTask_s = new SysTask();
				sysTask_s.setId(renderTask.getId());
				sysTask_s.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
				sysTask_s.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n")+"["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]:第" + renderTask.getExecCount() + "次渲染，执行失败11！");
				sysTask_s.setGmtModified(new Date());
				sysTaskService.update(sysTask_s);
				jsonObject.remove("success");
                jsonObject.accumulate("success", false);
                jsonObject.accumulate("message", "创建脚本失败");
                return jsonObject;
			}
		}else{
			SysTask sysTask_s = new SysTask();
			sysTask_s.setId(renderTask.getId());
			sysTask_s.setState(SysTaskStatus.END_OF_EXCEPTION);//执行失败
			sysTask_s.setRemark((StringUtils.isBlank(renderTask.getRemark())?"":renderTask.getRemark()+"\r\n")+"["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]:第" + renderTask.getExecCount() + "次渲染，执行失败！没有渲染参数22！");
			sysTask_s.setGmtModified(new Date());
			sysTaskService.update(sysTask_s);
			jsonObject.remove("success");
            jsonObject.accumulate("success", false);
            jsonObject.accumulate("message", "params参数为空！");
            return jsonObject;
		}
		SysTask task = new SysTask();
		task.setId(renderTask.getId());
		task.setState(SysTaskStatus.READY_RENDER);
		task.setGmtModified(new Date());
		task.setAtt2(Utils.getCurrentDateTime());//解压文件时间
		sysTaskService.update(task);
		return jsonObject;
	}
	
	//批量提交云渲染任务
	public Object batchRenderTaskJob() throws Exception{
		/*logger.warn("开始批量提交渲染任务。。。");*/
		Integer batchNum = sysTaskService.getRenderBatchSum();
		if( batchNum != null && batchNum > 6 ){
			return null;
		}
		initRender();
		SysTask sysTask = new SysTask();
		sysTask.setIsDeleted(0);
		sysTask.setState(SysTaskStatus.READY_RENDER);
		sysTask.setRenderWay("ruiyun");
		List<SysTask> list = sysTaskService.getList(sysTask); 
		StringBuffer sbCode = new StringBuffer();
		String fileName = Utils.getCurrentDateTime(Utils.DATETIME)+"_"+Utils.generateRandomDigitString(6);
		String osType = FileUploadUtils.SYSTEM_FORMAT;
		String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
		String maxRender = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
		// 脚本模版存储目录
        String scriptTemplateDir = Utils.getPropertyName("render", "script.template.path", "/MaxTemplate/");
		RenderConfig renderConfig = null;	// 解压文件存储方式
		for (int i =0;i<list.size();i++) {
			SysTask task = list.get(i);
			if(i==0){
				renderConfig = renderServersMap.get(task.getRenderServer());	
			}
			SysTask sysTask_s = new SysTask();
			sysTask_s.setId(task.getId());
			sysTask_s.setState(SysTaskStatus.RY_EXECUTING);
			sysTask_s.setNuma2(fileName);
			sysTask_s.setExecCount(1);
			sysTaskService.update(sysTask_s);
			sbCode.append(task.getBusinessCode()).append("\r\n");
			//保存该批量提交的任务
//			SysBatchRenderTask batchRenderTask = new SysBatchRenderTask();
//			batchRenderTask.setTaskId(task.getId());
//			batchRenderTask.setBueinessCode(task.getBusinessCode());
//			batchRenderTask.setBatchState("0");
//			batchRenderTask.setBatchNumber(fileName);
//			sysBatchRenderTaskService.add(batchRenderTask);
			
		}
		if( sbCode.toString().length() > 0 ){
			sbCode.append("end");
			if( renderConfig != null ){
				logger.warn("渲染任务根目录-------------"+renderConfig.getRenderRoot());
				String batchPath = renderConfig.getRenderRoot()+maxRender+scriptPath+fileName+"/";
				String batchTempletPath = renderConfig.getRenderRoot()+maxRender+scriptTemplateDir;
				if( "windows".equals(osType) ){
					batchPath = batchPath.replace("/", "\\");
					batchTempletPath = batchTempletPath.replace("/", "\\");
				}
				String filePath = batchPath+"list.txt";
				boolean flag = FileUploadUtils.writeTxtFile(filePath, sbCode.toString());
				if(flag){
					String batchName = "batchRenderTask.ms";
					filePath = batchPath + batchName;
					File file = new File(batchTempletPath+batchName);
					if( file.exists() ){
						FileUploadUtils.fileCopy(batchTempletPath+batchName,filePath);
						File f = new File(filePath);
						if( f.exists() ){
							String renderContext = FileUploadUtils.getFileContext(filePath);
							// 读取模板内容
							renderContext = renderContext.replaceAll("\\{batchCode\\}", fileName);
					        // 生成脚本文件
							FileUploadUtils.writeTxtFile(filePath, renderContext);
							logger.debug("生成文件成功:"+batchPath+batchName);
						}else{
							logger.debug("找不到这个目录文件："+batchPath+batchName+"！");
							return null;
						}
					}else{
						logger.debug("找不到这个目录文件："+batchTempletPath+batchName+"！");
						return null;
					}
					String renderName = "Render_batch.bat";
					String renderTempletPath = batchTempletPath + renderName;
					String rederBatPath = batchPath + "Render.bat";
					File file2 = new File(renderTempletPath);
					if( file2.exists() ){
						FileUploadUtils.fileCopy(renderTempletPath,rederBatPath);
						File f = new File(rederBatPath);
						if( f.exists() ){
							String renderContext = FileUploadUtils.getFileContext(rederBatPath);
							// 读取模板内容
							renderContext = renderContext.replaceAll("\\{batchCode\\}", fileName);
					        // 生成脚本文件
							FileUploadUtils.writeTxtFile(rederBatPath, renderContext);
							logger.debug("生成文件成功:"+rederBatPath);
							
							flag = RenderUtil.sendRequest("open:"+fileName,renderConfig.getRenderServer());
							if(!flag){
								logger.debug("找不到这个目录文件："+rederBatPath+"！");
							}
						}else{
							logger.debug("找不到这个目录文件："+rederBatPath+"！");
						}
					}else{
						logger.debug("找不到这个目录文件："+renderTempletPath+"！");
					}
					
				}else{
					logger.debug("生成"+filePath+"文件失败");
				}
			}
		}
		return null;
	}

	
	

	@Override
	public DesignPlanModel selectDesignPlanInfo(Integer id) {
		return designPlanMapper.selectDesignPlanInfo(id);
	}
	@Override
	public DesignPlanModel selectIosDesignPlanInfo(Integer id) {
		return designPlanMapper.selectIosDesignPlanInfo(id);
	}
	@Override
	public DesignPlanModel selectAndroidDesignPlanInfo(Integer id) {
		return designPlanMapper.selectAndroidDesignPlanInfo(id);
	}

	@Override
	public UnityPlanProduct getPlanProductStructureJson(UnityPlanProduct unityPlanProduct,DesignPlanProduct planProduct,DesignPlanModel designPlan, LoginUser loginUser) {
		int groupId = planProduct.getProductGroupId()==null?0:planProduct.getProductGroupId();
		int isMain = planProduct.getIsMainProduct()==null?0:planProduct.getIsMainProduct();
		Integer groupType=planProduct.getGroupType();
		unityPlanProduct.setGroupType(groupType);
		Map<Integer,Integer> memoryMap=new HashMap<Integer, Integer>();
		if(groupType==null||new Integer(0).equals(groupType)){
					/*组合*/
			unityPlanProduct.setProductGroupId(groupId);
			unityPlanProduct.setIsMainProduct(isMain);
			unityPlanProduct.setPlanGroupId(planProduct.getPlanGroupId());
			unityPlanProduct.setPlanStructureId("");
					/*识别是否是结构组合->是->得到对应结构的id*/
					/*先从memoryMap中找(防止重复访问数据库)*/
			if(groupId>0){
				if(memoryMap.containsKey(groupId)){
					unityPlanProduct.setProductStructureId(memoryMap.get(groupId));
				}else{
							/*如果该组合是结构组合,找对应的结构id*/
					GroupProduct groupProduct=groupProductService.get(groupId);
					if(groupProduct!=null){
						Integer structureId=groupProduct.getStructureId();
						if(structureId!=null&&structureId>0){
							unityPlanProduct.setProductStructureId(structureId);
							String PlanGroupId=unityPlanProduct.getPlanGroupId();
							if(StringUtils.isNotBlank(PlanGroupId)){
								String[] strs=PlanGroupId.split("_");
								unityPlanProduct.setPlanStructureId(structureId+"_"+strs[1]);
							}
							memoryMap.put(groupId, structureId);
						}
					}
				}
			}
					/*识别是否是结构组合->是->得到对应结构的id->end*/
			if(unityPlanProduct.getProductStructureId()!=null&&unityPlanProduct.getProductStructureId()>0){

			}else{
				unityPlanProduct.setProductStructureId(new Integer(0));
			}
		}else if(new Integer(1).equals(groupType)){
					/*结构*/
			unityPlanProduct.setIsMainProduct(new Integer(0));
			unityPlanProduct.setProductGroupId(new Integer(0));
			unityPlanProduct.setPlanGroupId("");
			unityPlanProduct.setPlanStructureId(planProduct.getPlanGroupId());
			unityPlanProduct.setProductStructureId(groupId);
		}

		//如果该产品是主产品，则返回该方案产品组合的方案产品ID数据
		GroupProductDetails groupProductDetails = new GroupProductDetails();
		//if( isMain == 1 || groupId > 0 ){
		if(groupId>0){
			DesignPlanProduct planProducts = new DesignPlanProduct();
			/*planProducts.setProductGroupId(groupId);*/
			planProducts.setPlanGroupId(planProduct.getPlanGroupId());
			planProducts.setPlanId(designPlan.getDesignPlanId());
			planProducts.setGroupType(groupType);
			planProducts.setIsDeleted(0);
			List<DesignPlanProduct> dppList=null;
			if(Utils.enableRedisCache()){
				dppList = DesignPlanProductCacher.getAllList(planProducts);
			}else{
				dppList = designPlanProductService.getList(planProducts);
			}

			if( Lists.isNotEmpty(dppList) ){
				Integer arrayIds[] = new Integer[dppList.size()];
				for(int i=0;i<dppList.size();i++){
					DesignPlanProduct dpp = dppList.get(i);
					arrayIds[i] = dpp.getId();
				}
				if(new Integer(1).equals(groupType))
					unityPlanProduct.setPlanProductStructureIds(arrayIds);
				else{
					/*if(new Integer(1).equals(isMain))*/
					unityPlanProduct.setPlanProductGroupIds(arrayIds);
					unityPlanProduct.setPlanProductStructureIds(arrayIds);
				}
			}
		}else{
			//如果是次产品则去检索它是否有组合过并且是主产品，是则设置成1
			//白模产品不需要检索
					/*查询条件设置组合查询的state(根据用户类型,内部用户能查到测试和上架的组合,其他用户只能查到上架的组合)*/

			Integer userType=loginUser.getUserType();
			List<Integer> statusList=new ArrayList<Integer>();
			statusList.add(1);
			if(userType==1){
				statusList.add(2);
			}
			groupProductDetails.setStatusList(statusList);
					/*查询条件设置组合查询的state(根据用户类型,内部用户能查到测试和上架的组合,其他用户只能查到上架的组合)->end*/
			if(!planProduct.getProductId().equals(planProduct.getInitProductId())){
				groupProductDetails.setProductId(planProduct.getProductId());
				groupProductDetails.setIsMain(1);
				List<GroupProductDetails> gpdList=null;
				if(Utils.enableRedisCache()){
					gpdList = GroupProductDetailsCache.getList(groupProductDetails);
				}else{
					gpdList = groupProductDetailsService.getList(groupProductDetails);
				}
				if( gpdList.size() > 0 ){
					unityPlanProduct.setIsMainProduct(1);
				}
			}
		}
		return unityPlanProduct;
	}

	@Override
	public List<UnityPlanProduct> getDecorationNavigationInfo(
			List<UnityPlanProduct> unityPlanProductList,
			List<UnityPlanProduct> newUnityPlanProductList,
			TreeSet<String> productTypeCodeSet,
			Map<String, UnityPlanProduct> unityPlanProductMap_p
			) {

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
				if(punityPlanProduct.getPlanProductId().intValue() ==3093779) {
					logger.error("getDecorationNavigationInfo==>test==s06sf01" + punityPlanProduct.getProductId() + "pproductTypeCode=" + pproductTypeCode);
				}
				
				newUnityPlanProductList.add(punityPlanProduct);
				// 追加下属所有子节点信息
				for (UnityPlanProduct sunityPlanProduct : unityPlanProductList) {
					if (pproductTypeCode.equals(sunityPlanProduct.getProductTypeCode())) {
						/*if(punityPlanProduct.getPlanProductId().intValue() ==3093779) {
							logger.error("getDecorationNavigationInfo==>sunityPlanProduct==>test==s06sf01" + punityPlanProduct.getProductId() + "pproductTypeCode=" + pproductTypeCode);
						}*/
					
						// if(list.size()>1){
						newUnityPlanProductList.add(sunityPlanProduct);
						// }
					}
				}
			}
		}
		
		// add by huangsongbo 2018.6.11 oldPosName ->start
		/*String oldPosName = this.getOldPosName(sunityPlanProduct);*/
		/*for(UnityPlanProduct unityPlanProduct : newUnityPlanProductList) {
			
			sss;
		}*/
		// add by huangsongbo 2018.6.11 oldPosName ->end
		
		return newUnityPlanProductList;
	}


	@Override
	public List<DesigPlanWithResult> selectDesignList(DesignPlan designPlan) throws Exception {
		List<DesigPlanWithResult> dataList = new ArrayList<DesigPlanWithResult>();
		dataList = designPlanMapper.selectDesignList(designPlan);
		return dataList;
	}

	@Override
	public Integer CopyDesignTemplet(String mediaType, DesignPlan designPlan,
			DesignTemplet designTemplet, LoginUser loginUser,
			HttpServletRequest request,String modelType) {
		Integer designPlanId=null;
		designPlan.setSceneModified(Utils.getCurrentTimeMillis());//设计方案给个初始时间戳
		designPlan.setMediaType(Utils.getIntValue(mediaType));
		designPlan.setPlanCode(designTemplet.getDesignCode() + "_" + Utils.generateRandomDigitString(6));
		designPlan.setPlanName(designTemplet.getDesignName());			/*设计方案名称*/
		designPlan.setUserId(loginUser == null ? -1 : loginUser.getId());	/* 用户id*/
		designPlan.setDesignSourceType(7);											/* 样板房*/
		designPlan.setDesignId(designTemplet.getId());							/*样板房id*/
		designPlan.setPicId(designTemplet.getPicId());							/* 设计方案缩略图*/
		designPlan.setSysCode(designTemplet.getSpaceCommonCode()+ "_" + Utils.generateRandomDigitString(6));
		//存储设计方案 业务来源 
        designPlan.setBusinessType(DesignPlanBusinessTypeConstant.TYPE_DIY);
		designPlan.setDesignTemplateId(designTemplet.getId());/* 样板房ID*/
		/* 公共空间拷贝//u3d模型直接引用样板房的模型*/
		String modelId = this.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), designTemplet,modelType);
		if (StringUtils.isNotBlank(modelId) && Utils.getIntValue(modelId) > 0) {
			logger.info("wpc.copyFile.modelId=" + modelId);
			designPlan.setModelId(Utils.getIntValue(modelId));
		}else{
			designPlan.setModelId(-1);
		}

		designPlan.setSpaceCommonId(designTemplet.getSpaceCommonId());/* 适用的空间ID*/
		designPlan.setDesignStyleId(null);													 /* 设计风格,后续在更新产品时,需根据产品类型判断*/

		/*拷贝了一份 样板房的 配置文件 到 设计方案中*/
		Integer resFileId = this.planCopyFile(designTemplet.getConfigFileId() == null ? "" : designTemplet.getConfigFileId().toString(), "file","design.designPlan.u3dconfig", null, request, designPlan.getPlanCode());
		designPlan.setConfigFileId(resFileId);						/*设计方案配置文件*/
		if(resFileId == null || resFileId <= 0){
			logger.error("新建方案配置文件copy失败！templetId="+designTemplet.getId()+",configID="+designTemplet.getConfigFileId());
			return -1;
		}
		if(resFileId == null || resFileId <= 0){
			logger.error("新建方案配置文件copy失败！templetId="+designTemplet.getId()+",configID="+designTemplet.getConfigFileId());
			return -1;
		}
		/* 创建新的设计方案信息*/
		designPlan.setIsOpen(0);
		sysSave(designPlan, request);
		designPlanId = designPlanService.add(designPlan);
		
		logger.info("add:id=" + designPlanId);
		designPlan.setId(designPlanId);
		if(Utils.enableRedisCache()){
			DesignCacher.removePlan(designPlanId);
		}
		/* 回写资源信息*/
		ResDesign rf = new ResDesign();
		rf.setId(resFileId);
		rf.setBusinessId(designPlanId);
		resDesignService.update(rf);
		if(Utils.enableRedisCache()){
			ResourceCacher.removeResFile(rf.getId());
		}
		return designPlanId;
	}

	
	/*
	 * 自动存储(加code)
	 */
	public Integer planCopyFile(String resId, String resType, String fileKey, Integer bussniess, HttpServletRequest request,
			String code) {
		logger.debug("resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess
				+ ";code=" + code);
		String resFilePath = "";
		Integer newResId = -1;

		ResDesign resDesign = new ResDesign();
		//ResFile resFile = new ResFile();
		ResModel resModel = new ResModel();
		ResPic resPic = new ResPic();
		if (!StringUtils.isEmpty(resId)) {
			if ("file".equals(resType)) {
				ResFile file = resFileService.get(new Integer(resId));
				//ResDesign  file = resFileService.get(new Integer(resId));
				if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
					resFilePath = file.getFilePath();
					//resFile = file.copy();
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
			if ("pic".equals(resType)){
				ResPic pic = resPicService.get(new Integer(resId));
				if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
					resFilePath = pic.getPicPath();
					resPic = pic.copy();
				}

			}
			if (!StringUtils.isEmpty(resFilePath)) {
				// String uploadRoot = "";
				/*String local_uploadRoot = Constants.UPLOAD_ROOT;*/
				String srcPath = "";
				/*if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					srcPath = local_uploadRoot + resFilePath;
				} else {
					srcPath = local_uploadRoot + resFilePath.replace("/", "\\");
				}*/
				srcPath = Utils.dealWithPath(Utils.getAbsolutePath(resFilePath, Utils.getAbsolutePathType.encrypt), null);
				File srcresourcesFile = new File(srcPath);

				if (!srcresourcesFile.getParentFile().exists()) {
					srcresourcesFile.getParentFile().mkdirs();
				}
				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
						resFilePath.length());
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
				}

				//String newPath = app.getString(fileKey + ".upload.path");
				//newPath = newPath.replace("[code]", code);
				String newPath = Utils.getPropertyName("config/res", fileKey + ".upload.path", "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/").trim();
				newPath = Utils.replaceDate(newPath);
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
				String targetName = newPath + tarName;
				targetName = targetName.replace(".txt", AESFileConstant.AES_FIRST+".txt");//复制的配置文件因为源文件已经加密，要在文件后面加个标识
				/*String local_targetPath = local_uploadRoot + targetName.replace("/", "\\");*/
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);
				/*if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					local_targetPath = local_uploadRoot + targetName;
				}*/

				File local_targetFile = new File(local_targetPath);

				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0, resFilePath.lastIndexOf("/") + 1);
				/*String dbFilePath = Constants.UPLOAD_ROOT.replace("\\", "/") + newPath + tarName;*/
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
					} else {
						logger.error("srcresourcesFile is not  exists !srcresourcesFile="+srcresourcesFile);
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
						//System.out.println("resDesign 对象"+resDesign== null);
						newResId = resDesignService.add(resDesign);
//						newResId = resDesign.getId();
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
		     /*   Map<String,Object> map1=(Map<String, Object>) request.getAttribute("AuthorizationData");
	                String uid=map1.get("uid").toString();
	                String signflat=request.getParameter("signflat");
	                String key=signflat+uid;
	                LoginUser loginUser =(LoginUser) JedisUserUtils.getObject(key);*/
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

	
	
	@Override
	public Boolean CopyDesignTempletProduct(Integer id,DesignTemplet designTemplet ,HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		Boolean flag=true;
		try{
			DesignTempletProduct templetProduct = new DesignTempletProduct();
			templetProduct.setIsDeleted(0);
			templetProduct.setDesignTempletId(designTemplet.getId());
			List<DesignTempletProduct> temProductList=null;
			if(Utils.enableRedisCache()){
				temProductList = DesignCacher.getTempletProductList(templetProduct);
			}else{
				temProductList = designTempletProductService.getList(templetProduct);
			}
			List<DesignPlanProduct>planProductList=new ArrayList<DesignPlanProduct>();
			// 存储组合对应的唯一值
			/*Map<Integer, > */
			for (DesignTempletProduct dtp : temProductList) {
				DesignPlanProduct planProduct = new DesignPlanProduct();
				planProduct.setPlanId(id);
				/* 拷贝挂节点*/
				planProduct.setPosIndexPath(dtp.getPosIndexPath());
				planProduct.setPosName(dtp.getPosName());
				planProduct.setProductId(dtp.getProductId());
				planProduct.setProductSequence(dtp.getProductSequence());
				/* 拷贝样板房中产品组信息*/
				planProduct.setProductGroupId(dtp.getProductGroupId());
				planProduct.setIsMainProduct(dtp.getIsMainProduct());
				planProduct.setPlanGroupId(dtp.getPlanGroupId());
				planProduct.setGroupType(dtp.getGroupType());
				// 设置组合产品的唯一值(groupProductUniqueId)
				/*this.setGroupProductUniqueId(planProduct, );*/
				
				String isHide = Utils.getValue("design.designPlan.product.isHide", "true");
				if ("true".equals(isHide.trim())) {
					BaseProduct baseProduct=null;
					if(Utils.enableRedisCache()){
						baseProduct = BaseProductCacher.get(dtp.getProductId());
					}else{
						baseProduct = baseProductService.get(dtp.getProductId());
					}
					planProduct.setIsHide(1);
					if (baseProduct != null) {
						if (StringUtils.isNotBlank(baseProduct.getProductTypeValue())&& baseProduct.getProductSmallTypeValue() != null) {
							/*天花，地面,墙面,门的原模默认显示*/
							if (("1".equals(baseProduct.getProductTypeValue()))|| ("2".equals(baseProduct.getProductTypeValue()))|| ("3".equals(baseProduct.getProductTypeValue()))|| ("4".equals(baseProduct.getProductTypeValue()))) {
								planProduct.setIsHide(0);
								if("3".equals(baseProduct.getProductTypeValue()) && "16".equals(baseProduct.getProductSmallTypeValue()+"")){
									planProduct.setIsHide(1);
								}else if("3".equals(baseProduct.getProductTypeValue()) && "17".equals(baseProduct.getProductSmallTypeValue()+"")){
									planProduct.setIsHide(1);
								}
							}
						}
					}
				}
				if (!"true".equals(isHide.trim())) {
					planProduct.setIsHide(0);
				}
				planProduct.setPlanProductId(dtp.getId());
				planProduct.setInitProductId(dtp.getProductId());
				planProduct.setPosIndexPath(dtp.getPosIndexPath());// 挂节点信息
				planProduct.setPosName(dtp.getPosName());
				planProduct.setBindParentProductId(dtp.getBindParentProductId());
				planProduct.setSameProductTypeIndex(dtp.getSameProductTypeIndex());
				/*天花、地面、地面结构拆分*/
				planProduct.setIsStandard(dtp.getIsStandard());
				planProduct.setCenter(dtp.getCenter());
				planProduct.setRegionMark(dtp.getRegionMark());
				planProduct.setStyleId(dtp.getStyleId());
				planProduct.setMeasureCode(dtp.getMeasureCode());
				planProduct.setDescribeInfo(dtp.getDescribeInfo());
				planProduct.setProductIndex(dtp.getProductIndex());
				
				planProduct.setIsGroupReplaceWay(dtp.getIsGroupReplaceWay());
				planProduct.setIsMainStructureProduct(dtp.getIsMainStructureProduct());
				planProduct.setWallType(dtp.getWallType());
				planProduct.setWallOrientation(dtp.getWallOrientation());

				sysSave(planProduct, request);
				planProductList.add(planProduct);
				/*designPlanProductService.add(planProduct);*/
			}
			designPlanProductService.batchAdd(planProductList);
			Long endTime = System.currentTimeMillis();
			logger.error("复制设计方案产品耗时："+(endTime - startTime));
			return flag;
		}catch(Exception e){
			e.printStackTrace();
			logger.warn("方法名：CopyDesignTempletProduct："+e);
			flag=false;
			return flag;
		}
	}
	
	
	
	
	/*
	 * 自动存储(加code)
	 */
	public Integer copyFile(String resId, String resType, String fileKey, Integer bussniess, HttpServletRequest request,
			String code) {
		logger.debug("resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess
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
			if ("pic".equals(resType)){
				ResPic pic = resPicService.get(new Integer(resId));
				if (pic != null && !StringUtils.isEmpty(pic.getPicPath())) {
					resFilePath = pic.getPicPath();
					resPic = pic.copy();
				}

			}
			if (!StringUtils.isEmpty(resFilePath)) {
				// String uploadRoot = "";
				/*String local_uploadRoot = Constants.UPLOAD_ROOT;*/
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

//				String newPath = app.getString(fileKey + ".upload.path");
//				newPath = newPath.replace("[code]", code);
				String newPath = Utils.getPropertyName("config/res", fileKey + ".upload.path", "").trim();
				newPath = Utils.replaceDate(newPath);
				
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
				String targetName = newPath + tarName;
				/*String local_targetPath = local_uploadRoot + targetName.replace("/", "\\");
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					local_targetPath = local_uploadRoot + targetName;
				}*/
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);
						
				File local_targetFile = new File(local_targetPath);

				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0, resFilePath.lastIndexOf("/") + 1);
				/*String dbFilePath = Constants.UPLOAD_ROOT.replace("\\", "/") + newPath + tarName;*/
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
					} else {
						logger.debug("srcresourcesFile is not  exists !");
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
						logger.debug("copy file is error");
						return -1;
					}

				} else {
					// 3 本地和ftp同时上传(默认是本地上传)
					// resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (!flag || FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						logger.debug("copy file is error");
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

		        /*Map<String,Object> map1=(Map<String, Object>) request.getAttribute("AuthorizationData");
	                String uid=map1.get("uid").toString();
	                String signflat=request.getParameter("signflat");
	                String key=signflat+uid;
	                LoginUser loginUser =(LoginUser) JedisUserUtils.getObject(key);*/
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
			

		   /*     Map<String,Object> map1=(Map<String, Object>) request.getAttribute("AuthorizationData");
	                String uid=map1.get("uid").toString();
	                String signflat=request.getParameter("signflat");
	                String key=signflat+uid;
	                LoginUser loginUser =(LoginUser) JedisUserUtils.getObject(key);*/
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
	
	
	
	/*
	 * 根据不同的媒介获取不同的模型
	 */
	public String getU3dModelId(String mediaType, SpaceCommon spaceCommon) {
		if (spaceCommon == null || mediaType == null) {
			return "";
		}
		if ("3".equals(mediaType)) {
			return spaceCommon.getWindowsPcU3dModelId();
		} else if ("4".equals(mediaType)) {
			return spaceCommon.getMacBookPcU3dModelId();
		} else if ("5".equals(mediaType)) {
			return spaceCommon.getIosU3dModelId();
		} else if ("6".equals(mediaType)) {
			return spaceCommon.getAndroidU3dModelId();
		} else if ("7".equals(mediaType)) {
			return spaceCommon.getIpadU3dModelId();
		} else {
			return spaceCommon.getModelU3dId();
		}
	}
 
	/*
	 * 根据不同的媒介获取不同的空间模型
	 */
	public String getU3dModelIdV2(String mediaType, DesignTemplet designTemplet) {
		if (designTemplet == null || mediaType == null) {
			return "";
		}
		if ("3".equals(mediaType)) {
			return designTemplet.getWindowsPcU3dModelId();
		} else if ("4".equals(mediaType)) {
			return designTemplet.getMacBookPcU3dModelId();
		} else if ("5".equals(mediaType)) {
			return designTemplet.getIosU3dModelId();
		} else if ("6".equals(mediaType)) {
			return designTemplet.getAndroidU3dModelId();
		} else if ("7".equals(mediaType)) {
			return designTemplet.getIpadU3dModelId();
		} else {
			return designTemplet.getModelU3dId();
		}
	}
	
	/*
	 * 根据不同的媒介获取不同的样板房模型
	 */
	public String getU3dModelId(String mediaType, DesignTemplet designTemplet,String modelType) {
	        String U3dModel="";
			logger.error("getU3dModelId.designTemplet.mediaType="+mediaType+",modelType="+modelType);
	        if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){
	            if (designTemplet == null || mediaType == null) {
                            return "";
                    }
                    if ("3".equals(mediaType)) {
                            U3dModel=designTemplet.getPcModelU3dId() == null ? "" : designTemplet.getPcModelU3dId().toString();
                            return U3dModel;
                    } else if ("4".equals(mediaType)) {
                            U3dModel=designTemplet.getMacBookpcModelU3dId() == null ? "" : designTemplet.getMacBookpcModelU3dId().toString();
                            return U3dModel;
                    } else if ("5".equals(mediaType)) {
                            U3dModel=designTemplet.getIosModelU3dId() == null ? "" : designTemplet.getIosModelU3dId().toString();
                            return U3dModel;
                    } else if ("6".equals(mediaType)) {
                            U3dModel=designTemplet.getAndroidModelU3dId() == null ? "" : designTemplet.getAndroidModelU3dId().toString();
                            return U3dModel;
                    } else if ("7".equals(mediaType)) {
                        U3dModel=designTemplet.getIpadModelU3dId() == null ? "" : designTemplet.getIpadModelU3dId().toString();
                            return U3dModel;
                    } else {
                            U3dModel=designTemplet.getWebModelU3dId() == null ? "" : designTemplet.getWebModelU3dId().toString();
                            return U3dModel;
                    }
	        }if(modelType.equals("IPhonePlayer")){
	            U3dModel=designTemplet.getIosModelU3dId() == null ? "" : designTemplet.getIosModelU3dId().toString();
	            return U3dModel;
	        }if(modelType.equals("Android")){
	            U3dModel=designTemplet.getAndroidModelU3dId() == null ? "" : designTemplet.getAndroidModelU3dId().toString();
	            return U3dModel;
	        }	
	        return U3dModel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public DesignPlan getMaxList(Map map) {
		DesignPlan designPlan=null;
		if (map != null && map.size() > 0) {
			List list = (List) map.get("list");
			if (list != null && list.size() > 0) {
				Map resultMap = (Map) list.get(0);
				Integer spaceCommonId = (Integer) resultMap.get("space_common_id");
				Date gmtCreate = Utils.parseDate(
						Utils.formatDate((Date) resultMap.get("max(gmt_create)"), Utils.DATE_TIME),
						Utils.DATE_TIME);
				Integer userIdDb = (Integer) resultMap.get("user_id");
				DesignPlan lastdesignPlan = new DesignPlan();
				lastdesignPlan.setUserId(userIdDb);
				lastdesignPlan.setGmtCreate(gmtCreate);
				lastdesignPlan.setSpaceCommonId(spaceCommonId);
				lastdesignPlan.setIsDeleted(0);

				List<DesignPlan> newList = designPlanService.getList(lastdesignPlan);
				designPlan = newList.get(0);
			}
		}
		return designPlan;
	}
	
	/**
	 * @param designPlan 
	 * @param params 
	 *  add by yanghz
	 * @Description: 保存渲染任务参数（新渲染系统）      
	 * @return void    返回类型 
	 * @throws
	 */
	@Override
	public String saveRenderParams(String params, DesignPlan designPlan) {
		//预解压文件的路径
		String renderResourcesPath=Utils.getPropertyName("render", "render.renderResourcesPath", "/home/nork/MaxRender/resources");
		String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)+Utils.generateRandomDigitString(6);
		/**先把文件保存到本地*/
		Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
		String taskConfigUploadPath = Utils.getPropertyName("render","taskConfig.upload.path","/system/sysTask/taskConfig/")+designPlan.getPlanCode()+"/";
		String fileName = rootDirName+".txt";
		/*String filePath = Constants.UPLOAD_ROOT + taskConfigUploadPath + fileName;*/
		String filePath = Utils.getAbsolutePath(taskConfigUploadPath + fileName, null);
		
		//换成预解压文件的路径
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,params);
		if( uploadFtpFlag ) {
			/**上传方式为2或者3表示文件在ftp上*/
			if (ftpUploadMethod == 2) {
				uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, taskConfigUploadPath);
				if( uploadFtpFlag ){
					/**删除本地文件*/
					FileUploadUtils.deleteDir(new File(filePath));
				}else{
					logger.error("生成新渲染系统任务参数文件失败");
				}
			} else if (ftpUploadMethod == 3) {
				uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, taskConfigUploadPath);
				if( !uploadFtpFlag ){
					logger.error("生成新渲染系统任务参数文件失败");
				}
			}
		}else{
			logger.error("生成新渲染系统任务参数文件失败");
		}
		return taskConfigUploadPath+fileName;
	}
	
	/**
	 * 
	* @Description: 组装渲染需要参数的路径
	* @param planId
	* @return
	* @return Object    返回类型 
	* @throws
	 */
	public List<String> getRenderParamsFilePath(Integer planId, SysTask priorityTask) {
		List<String> list = new ArrayList<String>();
		StringBuffer productMessage = new StringBuffer();
		if( planId == null ){
			logger.error("设计方案ID不能为空");
			return null;
		}
		DesignPlan designPlan = designPlanService.get(planId);
		if( designPlan == null ){
			logger.error("没有找到ID为 " + planId + " 的设计方案");
			return null;
		}
		String osType = FileUploadUtils.SYSTEM_FORMAT;//windows
		String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");///MaxScript/
		String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");///MaxRender
		String renderResourcesPath=Utils.getPropertyName("render", "render.renderResourcesPath", "/home/nork/MaxRender/resources");//F:\chengdu\MaxRender\resources
		//String renderResourcesPathOriginal=Utils.getPropertyName("render", "render.renderResourcesPath.original", "X:\\resources");//Max渲染机中的预解压路径
		String renderResourcesPathOriginal="";//Max渲染机中的预解压路径
		occu=Utils.dealWithPath(occu, osType);
		scriptPath=Utils.dealWithPath(scriptPath, osType);
		renderResourcesPath=Utils.dealWithPath(renderResourcesPath, osType);
		if(designPlan.getSpaceCommonId()==null||designPlan.getSpaceCommonId()<1){
			//logger.error("Id为 " + planId + " 的设计方案中没有空间ID");
			productMessage.append("Id为 " + planId + " 的设计方案中没有空间ID");
			//return null;
		}
		SpaceCommon spaceCommon=spaceCommonService.get(designPlan.getSpaceCommonId());
		if(spaceCommon==null){
			/*空间未找到*/
			productMessage.append("没有找到Id为 "+designPlan.getSpaceCommonId()+" 的空间!SpaceCommon(id="+designPlan.getSpaceCommonId()+") is null! ");
			//logger.error("没有找到Id为 "+designPlan.getSpaceCommonId()+" 的空间!SpaceCommon(id="+designPlan.getSpaceCommonId()+") is null! ");
			//return null;
		}
		if(StringUtils.isBlank(spaceCommon.getSpaceCode())){
			/*空间code为空*/
			productMessage.append("空间code为空:spaceCommonId:"+spaceCommon.getIds());
			//logger.error("空间code为空:spaceCommonId:"+spaceCommon.getIds());
			//return null;
		}
		/*检查预解压模型目录(空间灯光模型)有没有预解压模型文件*/
		logger.info("------开始生成空间灯光模型文件和场景模型文件(阳台)");
		String checkPath=renderResourcesPath+"/spaceCommon/"+spaceCommon.getSpaceCode();
		String checkPathOriginal=renderResourcesPathOriginal+"/spaceCommon/"+spaceCommon.getSpaceCode();
			File checkDir=new File(Utils.dealWithPath(checkPath, osType));//F:\chengdu\MaxRender\resources\spaceCommon\F01_0007
			File checkDirOriginal=new File(Utils.dealWithPath(checkPathOriginal, osType));//F:\chengdu\MaxRender\resources\spaceCommon\F01_0007
			if(checkDir.exists()&&checkDir.listFiles().length>0){
				list.add(checkDirOriginal.toString());
				/*组装模型(复制)到渲染目录->end*/
			}else{
				productMessage.append("空间模型文件未找到(可能原因:1.空间模型文件不存在;2,空间模型文件未预解压;3...):spaceCommonId:"+spaceCommon.getId());
				//logger.error("空间模型文件未找到(可能原因:1.空间模型文件不存在;2,空间模型文件未预解压;3...):spaceCommonId:"+spaceCommon.getId());
				//return null;
			}
		/*检查预解压模型目录(空间灯光模型)有没有预解压模型文件->end*/
		/*检查预解压模型目录(设计方案产品)有没有预解压模型文件*/
		DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
		designPlanProductSearch.setPlanId(designPlan.getId());
		designPlanProductSearch.setIsDeleted(0);
		List<DesignPlanProduct> designPlanProductList = designPlanProductService.getList(designPlanProductSearch);
		
		StringBuffer processProductMessage = new StringBuffer("=========渲染时处理的产品汇总========\r\n");
		if(designPlanProductList==null||designPlanProductList.size()<1){
			/*空间code为空*/
			productMessage.append("设计方案产品未找到,planId:"+designPlan.getId());
			//logger.error("设计方案产品未找到,planId:"+designPlan.getId());
			//return null;
		}
		processProductMessage.append("planId: " + designPlan.getId() +", 总共需要处理"+designPlanProductList.size()+"个产品的渲染资源！\r\n");
		for(DesignPlanProduct designPlanProduct:designPlanProductList){
			BaseProduct baseProduct=baseProductService.get(designPlanProduct.getProductId());
			if(baseProduct==null){
				productMessage.append("产品 [productId:"+designPlanProduct.getProductId()+"] 未找到！！\r\n");
				continue;
			}
			if(StringUtils.isBlank(baseProduct.getProductCode())){
				productMessage.append("产品 [productId:"+designPlanProduct.getProductId()+"] code不能为空！！\r\n");
				continue;
			}
			processProductMessage.append("开始处理产品["+baseProduct.getProductCode()+"]的渲染资源！\r\n");
			// 如果是白模则不处理
			if( baseProduct.getProductCode().indexOf("baimo") > -1 ){
				continue;
			}
			boolean isHard = isHard(baseProduct);
			if(isHard){
				processProductMessage.append("产品为贴图产品！\r\n");
				/*组合白膜和材质文件*/
				/*复制模型文件(从白膜)*/
				Integer baimoId=designPlanProduct.getInitProductId();
				if(baimoId==null||baimoId<1){
					productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"]对应的白膜未找到:[baimoId:"+baimoId+"] \r\n");
					continue;
				}
				BaseProduct baimoProduct=baseProductService.get(baimoId);
				if(baimoProduct==null){
					productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"]对应的白膜未找到:[baimoId:"+baimoId+"] \r\n");
					continue;
				}
				String productCheckPath=renderResourcesPath+"/baseProduct/"+baimoProduct.getProductCode();
				String productCheckPathOriginal=renderResourcesPathOriginal+"/baseProduct/"+baimoProduct.getProductCode();
					File checkDir1=new File(productCheckPath);
					File checkDir1Original=new File(productCheckPathOriginal);
					logger.info("------checkDir:"+productCheckPath);
					
					// 如果未发现预解压文件,重新解压一遍 ->start
					if(checkDir1.exists()&&checkDir1.listFiles().length>0){
						
					}else{
						resModelService.decompressModel(baimoProduct.getModelId(), baimoProduct.getProductCode());
					}
					// 如果未发现预解压文件,重新解压一遍 ->end
					
					if(checkDir1.exists()&&checkDir1.listFiles().length>0){
						/*组装模型(复制)到渲染目录*/
							list.add(checkDir1Original.toString());
							processProductMessage.append("处理产品多材质\r\n");
							// 多材质处理
							List<String> list2 = processTexture2(list,baseProduct.getProductCode(),designPlanProduct,priorityTask);
							if(list2 != null){
								list = list2;
							}else{
								productMessage.append("产品编码为："+baseProduct.getProductCode()+"多材质路径获取错误");
							}
						/*组装模型(复制)到渲染目录->end*/
					}else{
						/*产品模型不存在*/
						productMessage.append("产品 [productCode:"+baimoProduct.getProductCode()+"] 预解压模型文件未找到,预解压路径1:"+productCheckPath+"\r\n");
						continue;
					}
				/*复制模型文件(从白膜)->end*/
				
				/*复制材质文件*/
				String productCheckPath1=renderResourcesPath+"/baseProduct/"+baseProduct.getProductCode();
				String productCheckPath1Original=renderResourcesPathOriginal+"/baseProduct/"+baseProduct.getProductCode();
					//String productCheckPath1="X:\\resources\\spaceCommon\\F01_0007";//造数据
					File checkDir2=new File(productCheckPath1);
					File checkDir2Original=new File(productCheckPath1Original);
					if(checkDir2.exists()&&checkDir2.listFiles().length>0){
						/*组装材质(复制)到渲染目录*/
						list.add(checkDir2Original.toString());
						/*组装材质(复制)到渲染目录->end*/
					}else{
						/*产品模型不存在*/
						productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"] 材质文件未找到,预解压路径:"+checkDir2+"\r\n");
						continue;
					}
				/*复制材质文件->end*/
			}else{
				processProductMessage.append("产品为通用产品！！\r\n");
				String productCheckPath=renderResourcesPath+"/baseProduct/"+baseProduct.getProductCode();
				String productCheckPathOriginal=renderResourcesPathOriginal+"/baseProduct/"+baseProduct.getProductCode();
					File checkDir3=new File(productCheckPath);
					File checkDir3Original=new File(productCheckPathOriginal);
					logger.info("------checkDir:"+productCheckPath);
					
					// 如果未发现预解压文件,重新解压一遍 ->start
					if(checkDir3.exists()&&checkDir3.listFiles().length>0){
						
					}else{
						resModelService.decompressModel(baseProduct.getModelId(), baseProduct.getProductCode());
					}
					// 如果未发现预解压文件,重新解压一遍 ->end
					
					if(checkDir3.exists()&&checkDir3.listFiles().length>0){
						/*组装模型(复制)到渲染目录*/
							list.add(checkDir3Original.toString());
							processProductMessage.append("组装产品渲染模型资源目录");
							/*应对同类型新增的产品的情况,把材质文件覆盖->end*/
							// 多材质处理
							processProductMessage.append("处理产品多材质\r\n");
							List<String> list3  = processTexture2(list,baseProduct.getProductCode(),designPlanProduct,priorityTask);
							if(list3 != null){
								list = list3;
							}else{
								productMessage.append("产品编码为："+baseProduct.getProductCode()+"多材质路径获取错误");
							}
						/*组装模型(复制)到渲染目录->end*/
					}else{
						/*产品模型不存在*/
						productMessage.append("产品 [productCode:"+baseProduct.getProductCode()+"] 预解压模型文件未找到,预解压路径2:"+productCheckPath+"\r\n");
						continue;
					}
			}
		}
		/*检查预解压模型目录(设计方案产品)有没有预解压模型文件->end*/
		/*如果有产品压缩错误信息，则直接返回错误结果，并删除渲染文件根目录*/
		logger.info(processProductMessage);
		if( productMessage.length() > 0 ){
			logger.error("Id为"+priorityTask.getId()+"的渲染任务拼装预解压路径错误：" + productMessage.toString());
			logger.error("Id为"+priorityTask.getId()+"的渲染任务拼装预解压路径错误！参数汇总：" + list.toString());
			SysTask sysTask = new SysTask();
			sysTask.setId(priorityTask.getId());
			sysTask.setRemark(productMessage.toString());
			// 如果渲染异常结束，则把渲染金额退到用户账户余额中
			String renderErroMsg = productMessage.toString();
			renderTaskService.renderRefund(priorityTask,renderErroMsg);

			sysTaskService.update(sysTask );
			/*删除目录*/
			return null;
		}
		return list;
	}
	
	
	/**
	 * 保存渲染图片
	 * @param request
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Map<String, String> saveRenderPic(HttpServletRequest request) throws Exception {
		Map<String,String> resMap = new HashMap<String,String>();
		
		SysTask sysTask =null;
		DesignPlan plan=null;
		Integer resPicId = null;
		JSONObject json = null;
		SysUser sysUser = null;
		ResRenderPic resRenderPic = null;
		/*获得文件流*/
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		
		String taskAesID = multiRequest.getParameter("taskID");
		
		if ( StringUtils.isEmpty(taskAesID) ) {
			resMap.put("state", "2");
			resMap.put("data", "缺少参数 taskAesID");
			logger.error("=========缺少参数 taskAesID");
			return resMap;
		}
		else {
			
		}
		
		String taskID = "";
		try {
			taskID = AESUtil.decrypt(taskAesID, "");
		} catch (Exception e) {
			logger.error("decode taskAesID : " + taskAesID + " error, ", e);
			resMap.put("state", "3");
			resMap.put("data", "解密失败");
			return resMap; 
		}
		
		
		Iterator<String> iter = multiRequest.getFileNames();
		MultipartFile file = multiRequest.getFile(iter.next());
		String fileName = taskID + ".jpg"; // 历史遗留问题，我真是醉了， added by louxinhua
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String fName = fileName.substring(0, fileName.indexOf("."));
 
		/*在app配置里看当前系统是否为 widows */
		String osType = FileUploadUtils.SYSTEM_FORMAT;
		/*3dmax文件在渲染机上的目录*/
		String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
		if( "windows".equals(osType) ){
			occu = occu.replace("/", "\\");
		}
		
		/*应用根节点目录*/
		/*渲染图存放相对路径*/
		//String storePath = Utils.getValue("design.designPlan.render.upload.path", "/design/designPlan/render/").trim();
		String storePath = Utils.getPropertyName("config/res", "design.designPlan.render.upload.path", "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/render/").trim();
		storePath = Utils.replaceDate(storePath);
		storePath=("linux".equals(FileUploadUtils.SYSTEM_FORMAT)?storePath:storePath.replace("/", "\\"));
		String uploadRoot =Tools.getRootPath(storePath, "D:\\nork\\Resource");
		/*渲染图存放绝对路径*/
		String storeRealPath = uploadRoot+storePath;
		/*String storeRealPath = Utils.getAbsolutePath(storePath, null);*/

		/*判断任务信息是否完整*/
		sysTask = sysTaskService.get(Integer.parseInt(fName));
		if(sysTask==null){
			logger.error("，task id为："+fName+"的数据不存在");
			resMap.put("state", "1");
			resMap.put("data", "，task id为："+fName+"的数据不存在");
			return resMap;
		}
 	
		/*检查render_task表中任务信息是否完整*/
		RenderTask byTaskId = renderTaskService.getByTaskId(Integer.parseInt(fName));
		if(byTaskId == null){
			logger.error("渲染图片回调：render_task表中的task id为："+fName+"的数据不存在");
			resMap.put("state", "1");
			resMap.put("data", "，task id为："+fName+"的数据不存在");
			return resMap;
		}
		
		if(StringUtils.isEmpty(sysTask.getBusinessCode())|| sysTask.getBusinessId() == null|| new Integer(sysTask.getBusinessId()).intValue() <= 0){
			logger.error("任务中信息不完全，放弃本次执行...");
			SysTask  newSysTask = new  SysTask();
			newSysTask.setId(sysTask.getId());
			newSysTask.setGmtModified(new Date());
			newSysTask.setRemark(sysTask.getRemark()+";任务中信息不完全，放弃本次执行！,taskId =" + sysTask.getId());
			newSysTask.setState(SysTaskStatus.END_OF_EXCEPTION);/*任务异常*/
			sysTaskService.update(newSysTask);
			
			//更新render_task表任务状态
			RenderTask renderTask1 = new RenderTask();
			renderTask1.setId(byTaskId.getId());
			renderTask1.setGmtModified(new Date());
			renderTask1.setStatus(RenderTaskStates.END_RENDER_EXCEPTION);
			renderTask1.setGmtRenderEnd(new Date());
			renderTask1.setGmtTaskEnd(new Date());
			renderTaskService.update(renderTask1);
			
			resMap.put("state", "1");
			resMap.put("data", ";任务中信息不完全，放弃本次执行！,taskId =" + sysTask.getId());
			return resMap;
		}
		
		/*判断渲染角度和场景参数属性*/
		String attribute=sysTask.getAttribute();
		//logger.error("==============================="+attribute);
		if(StringUtils.isBlank(attribute)){
			logger.error("------sysTaskId:"+sysTask.getId()+"未找到渲染角度和场景参数属性(attribute)");
			resMap.put("state", "1");
			resMap.put("data", "------sysTaskId:"+sysTask.getId()+"未找到渲染角度和场景参数属性(attribute)");
			return resMap;
		}else{
			json=JSONObject.fromObject(attribute); 
		}
		
		/*判断设计方案是否存在:不存在也返回成功状态，任务状态置为异常结束，防止取图一直回调*/
		plan=designPlanService.get(sysTask.getBusinessId());
		if(plan==null){
			logger.error("任务中找不到设计方案信息,无法采集，防止一直回调返回成功状态！..."+",taskId =" + sysTask.getId());
			SysTask sysTask2 = new SysTask();
			sysTask2.setId(sysTask.getId());
			sysTask2.setGmtModified(new Date());
			sysTask2.setRemark(sysTask.getRemark()+";任务中找不到设计方案信息,无法采集，防止一直回调返回成功状态！,taskId ="+ sysTask.getId());
			sysTask2.setState(SysTaskStatus.END_OF_EXCEPTION);//任务异常
			sysTaskService.update(sysTask2);
			
			//更新render_task表任务状态
			RenderTask renderTask2 = new RenderTask();
			renderTask2.setId(byTaskId.getId());
			renderTask2.setGmtModified(new Date());
			renderTask2.setStatus(RenderTaskStates.END_RENDER_EXCEPTION);
			renderTask2.setGmtRenderEnd(new Date());
			renderTask2.setGmtTaskEnd(new Date());
			renderTaskService.update(renderTask2);
			
			resMap.put("state", "2");
			resMap.put("data", ";任务中找不到设计方案信息,无法采集，防止一直回调返回成功状态！,taskId ="+ sysTask.getId());
			return resMap;
		}
		
		
		/*判断用户是否存在*/
		sysUser=sysUserService.get(plan.getUserId());
		if(sysUser==null){
			resMap.put("state", "1");
			resMap.put("data", ";该任务ID绑定的用户不存在："+ sysTask.getId());
			return resMap;
		}
 
		
		/*解决[code]没有替换问题*/
//		storePath=storePath.replace("[code]", plan.getPlanCode());
//		storeRealPath=storeRealPath.replace("[code]", plan.getPlanCode());

		storePath = Utils.replaceDate(storePath);
		storeRealPath = Utils.replaceDate(storeRealPath);
		File f = new File(storePath);
		if(!f.exists()){
			f.mkdirs();
		}
		
		
		String bigFileName=sysTask.getBusinessCode()+"_"+ Utils.generateRandomDigitString(6)+suffix;
		try{
			/*生成渲染图片*/
			FileUtils.copyInputStreamToFile(file.getInputStream(),new File(storeRealPath, bigFileName));
			 
			/*保存渲染图片*/
			File renderPic  = new File(storeRealPath+bigFileName);
			Map map = FileUploadUtils.getMap(renderPic,ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY,false);
			resRenderPic = assembleResRenderPic(map);
			resRenderPic.setIsDeleted(0);
			resRenderPic.setPicPath(storePath+bigFileName);
			resRenderPic.setGmtCreate(new Date());
			resRenderPic.setViewPoint(json.getInt("viewPoint"));
			resRenderPic.setScene(json.getInt("scene"));
			resRenderPic.setRenderingType(json.getInt("renderingType"));
			resRenderPic.setCreator("sys");
			resRenderPic.setGmtModified(new Date());
			resRenderPic.setModifier("sys");
			resRenderPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6));
			resRenderPic.setBusinessId(sysTask.getBusinessId());
			resRenderPic.setPicType("3DMax渲染原图");
			resRenderPic.setTaskCreateTime(sysTask.getGmtCreate());//任务创建时间
			resPicId = resRenderPicService.add(resRenderPic);
			
			if(resPicId!=null){
				SysTask task = new SysTask();
				task.setId(sysTask.getId());
				task.setGmtModified(new Date());
				if(sysTask.getRemark() != null){
					task.setRemark(sysTask.getRemark() + "效果图已入库id=" + resPicId);
				}else{
					task.setRemark("效果图已入库id=" + resPicId);
				}
				task.setState(SysTaskStatus.END_OF_EXECUTION);//执行结束
				sysTaskService.update(task);
				
				//更新render_task表任务状态
				RenderTask renderTask = new RenderTask();
				renderTask.setId(byTaskId.getId());
				renderTask.setGmtModified(new Date());
				renderTask.setStatus(RenderTaskStates.END_OF_RENDER);
				renderTask.setGmtRenderEnd(new Date());
				renderTask.setGmtTaskEnd(new Date());
				renderTaskService.update(renderTask);
			}else{
				sendRenderMessage(sysTask,1,null);
				SysTask task = new SysTask();
				task.setId(sysTask.getId());
				task.setGmtModified(new Date());
				task.setRemark(sysTask.getRemark()+"效果图入库失败  任务id为："+sysTask.getId());
				task.setState(SysTaskStatus.END_OF_EXCEPTION);//执行结束
				sysTaskService.update(task);
				
				
				//更新render_task表任务状态
				RenderTask renderTask = new RenderTask();
				renderTask.setId(byTaskId.getId());
				renderTask.setGmtModified(new Date());
				renderTask.setStatus(RenderTaskStates.END_RENDER_EXCEPTION);
				renderTask.setGmtRenderEnd(new Date());
				renderTask.setGmtTaskEnd(new Date());
			    renderTaskService.update(renderTask);
			    
				resMap.put("state", "1");
				resMap.put("data", sysTask.getRemark() + "效果图入库失败  任务id为："+sysTask.getId());
				return resMap;
			}
		}catch (Exception e) {
			logger.error("效果图保存失败，task id为："+sysTask.getId(), e);
			
			if( f.exists() ){
				f.delete();
			}
			resMap.put("state", "1");
			resMap.put("data", "效果图保存失败，task id为："+sysTask.getId());
			return resMap;
		}
		
		/*生成缩略图*/
		String smallFileName = Utils.generateRandomDigitString(6)+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS) + fileName.substring(fileName.indexOf("."));
		logger.info("生成缩略图  generateRandomDigitString 方法结束");
		String targetSmallFilePath = storeRealPath + "small/" + smallFileName;
		logger.warn("缩略图存放目录："+targetSmallFilePath);
		ResizeImage.createThumbnail(storeRealPath+bigFileName, ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) ? targetSmallFilePath : targetSmallFilePath.replaceAll("/", "\\\\"), 280, 215);
		
		/*保存缩略图*/
		File targetSmallFile = new File(targetSmallFilePath);
		Map smallFileMap = FileUploadUtils.getMap(targetSmallFile, ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY, false);
		ResRenderPic smallRenderResPic =assembleResRenderPic(smallFileMap);
		smallRenderResPic.setIsDeleted(0);
		smallRenderResPic.setGmtCreate(new Date());
		smallRenderResPic.setPicPath(storePath + "small/" + smallFileName);
		smallRenderResPic.setCreator("sys");
		smallRenderResPic.setGmtModified(new Date());
		smallRenderResPic.setRenderingType(json.getInt("renderingType"));
		smallRenderResPic.setModifier("sys");
		smallRenderResPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6));
		smallRenderResPic.setBusinessId(sysTask.getBusinessId());
		smallRenderResPic.setPicType("3DMax渲染图-缩略图");
		smallRenderResPic.setViewPoint(json.getInt("viewPoint"));
		smallRenderResPic.setScene(json.getInt("scene"));
		smallRenderResPic.setPid(resPicId);
		Integer smallResPicId = null;
		smallResPicId = resRenderPicService.add(smallRenderResPic);
		
		/*修改任务状态，任务为结束状态*/
		SysTask task = new SysTask();
		task.setId(sysTask.getId());
		task.setGmtModified(new Date());
		if(sysTask.getRemark() != null){
			task.setRemark(sysTask.getRemark() + "效果图已入库id=" + resPicId);
		}else{
			task.setRemark("效果图已入库id=" + resPicId);
		}
		task.setState(SysTaskStatus.END_OF_EXECUTION);/*执行结束*/
		sysTaskService.update(task);
		
		//更新render_task表任务状态
		RenderTask renderTask = new RenderTask();
		renderTask.setId(byTaskId.getId());
		renderTask.setGmtModified(new Date());
		renderTask.setStatus(RenderTaskStates.END_OF_RENDER);
		renderTask.setGmtTaskEnd(new Date());
		renderTask.setGmtRenderEnd(new Date());
		renderTaskService.update(renderTask);
		
	    if(smallResPicId != null){
	    	SysTask task1 = new SysTask();
	    	task1.setId(sysTask.getId());
	    	task1.setGmtModified(new Date());
	    	if (sysTask.getRemark() != null && sysTask.getRemark().length() > 0){
				task1.setRemark(sysTask.getRemark()+";["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]高清渲染图已保存");
			}else {
				task1.setRemark("["+Utils.getCurrentDateTime(Utils.DATE_TIME)+"]高清渲染图已保存");
			}
	    	task1.setState(SysTaskStatus.END_OF_EXECUTION);
			sysTaskService.update(task1);
			
			//更新render_task表任务状态
			RenderTask renderTask1 = new RenderTask();
			renderTask1.setId(byTaskId.getId());
			renderTask1.setGmtModified(new Date());
			renderTask1.setStatus(RenderTaskStates.END_OF_RENDER);
			renderTask1.setGmtTaskEnd(new Date());
			renderTask1.setGmtRenderEnd(new Date());
		    renderTaskService.update(renderTask1);
 
	    }else{
	      logger.error("渲染任务Id=" + sysTask.getId()+"的任务保存渲染图失败！");
	      SysTask task2 = new SysTask();
	      task2.setId(sysTask.getId());
	      task2.setGmtModified(new Date());
	      task2.setRemark("保存渲染图失败！");
	      task2.setState(SysTaskStatus.END_OF_EXCEPTION);/*任务异常*/
    	  sysTaskService.update(task2);
		  
		  //更新render_task表任务状态
    	  RenderTask renderTask2 = new RenderTask();
    	  renderTask2.setId(byTaskId.getId());
    	  renderTask2.setGmtModified(new Date());
    	  renderTask2.setStatus(RenderTaskStates.END_RENDER_EXCEPTION);
    	  renderTask2.setGmtTaskEnd(new Date());
    	  renderTask2.setGmtRenderEnd(new Date());
		  renderTaskService.update(renderTask2);
		  
		  resRenderPicService.delete(resPicId);
		  logger.error("效果图更新到设计方案中异常,删除已存入的图片信息resPicId="+resPicId+",taskId =" + sysTask.getId());
		  /* 删除目录*/
		  logger.warn(Utils.getLineNumber()+"删除目录：");
		  /* 删除图片*/
		  if( f.exists() ){
			  f.delete();
		  }
	    }

		/*创建浏览器查看全景图*/
		/*if( RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()){
			logger.info("============创建浏览器查看全景图============");
			GeneratePanoramaExecutor panoramaExecutor = GeneratePanoramaExecutor.getInstance();
			GeneratePanoramaTask panoramaTask = new GeneratePanoramaTask(
					sysTask.getId(), sysTask.getBusinessId(),
					sysTask.getBusinessCode(), sysTask.getPicId(),
					Constants.UPLOAD_ROOT + resRenderPic.getPicPath(),
					resRenderPic.getId(), smallResPicId, resRenderPic.getPanoLevel());
			panoramaExecutor.addTask(panoramaTask);
		}*/
	    
	    /*追加水印*/
		String storeRealNamePath = storeRealPath + bigFileName;
		/* 将图片拷贝并存入设计方案表中*/
		if("true".equals(Utils.getValue("design.designPlanPic.isWaterMarkingFlag", "true"))){
		  String attr=sysTask.getAttribute();
		  logger.debug("------newsysTask.getAttribute():"+attr); 
		  logger.warn("------logger:attr:"+attr);
		  Integer scene=new Integer(json.getInt("scene"));
		  ImageUtils.watermarking2(storeRealNamePath, 1, 1, 1);
		  /*水印图方案一*/
		  ImageUtils.watermarking2(storeRealNamePath, scene, 1, 1);
		  logger.warn("渲染图增加水印完成，生成的新文件名称为storeRealNamePath="+storeRealNamePath);
		}
		
		
	    /*删除高清渲染预览图*/
		if( sysTask.getPicId() != null && RenderTypeCode.COMMON_720_LEVEL != resRenderPic.getRenderingType()){
			ResPic pic = resPicService.get(sysTask.getPicId());
			String method=app.getString(APP_OPLOAD_METHOD);/**1为web服务器 2为ftp 3为web+ftp*/
			if( pic != null ){
				if("1".equals(method)){
					/*String picPath = Constants.UPLOAD_ROOT + pic.getPicPath();*/
					String picPath = Utils.getAbsolutePath(pic.getPicPath(), null);
					RenderUtil.deleteFile(picPath);
					resPicService.delete(pic.getId());
				}
				if("2".equals(method)){
					String picPath = FileUploadUtils.FTP_UPLOAD_ROOT + pic.getPicPath();
					RenderUtil.deleteFile(picPath);
					resPicService.delete(pic.getId());
				}
				if("3".equals(method)){
					String picPath=null;
					/** 删除预览图物理文件**/
					/*picPath = Constants.UPLOAD_ROOT + pic.getPicPath();*/
					picPath = Utils.getAbsolutePath(pic.getPicPath(), null);
					RenderUtil.deleteFile(picPath);
					picPath = FileUploadUtils.FTP_UPLOAD_ROOT + pic.getPicPath();/** 删除预览图物理文件**/
					RenderUtil.deleteFile(picPath);
					resPicService.delete(pic.getId());/** 删除预览图数据*/
				}
			}
		}
		
		/*消息推送*/
		Integer messageid = sendRenderMessage(sysTask,2,resPicId);
		if(messageid.intValue() <= 0){
		   logger.error("消息发送异常..."+",taskId =" + sysTask.getId());
		  
		   resMap.put("state", "2");
		   resMap.put("data", "渲染完成!但是消息发送失败！");
		   return resMap;
		}
		
		resMap.put("state", "2");
		resMap.put("data", "渲染完成");
		return resMap;
	}

 
	
	/**
	 * 返回拼花结构格式
	 * @param designPlan
	 * @param unityDesignPlan
	 */
	@SuppressWarnings("unchecked")
	public void spellingFlowerData(LoginUser loginUser,String modelType,DesignPlanModel designPlan,UnityDesignPlan unityDesignPlan) {
		if(designPlan == null || unityDesignPlan == null) {
			return;
		}
		if(designPlan.getSpellingFlowerFileId()!=null && designPlan.getSpellingFlowerFileId().intValue() > 0) {
			ResDesign resDesign  = resDesignService.get(designPlan.getSpellingFlowerFileId());
			if(resDesign != null) {
				unityDesignPlan.setSpellingFlower(resDesign.getFilePath());
			}
		}
		if (StringUtils.isEmpty(designPlan.getSpellingFlowerProduct())) {
			return ;
		}
		String [] arr = designPlan.getSpellingFlowerProduct().split(",");
		List<BaseProduct>productList = null;
		if(arr != null && arr.length > 0 && !"[]".equals(arr)) {
			List<Integer>ids = new ArrayList<Integer>();
			for (String id : arr) {
				/*int productId = 0;
				if(id.indexOf("_")!=-1) {
					String idStr = id.substring(0, id.indexOf("_"));
					productId = Integer.parseInt(idStr);
					String str = id.split("_")[1];
					idMap.put(productId, str);
				}else {
					productId = Integer.parseInt(id);
				}*/
				ids.add(Integer.parseInt(id));
			}
			productList = baseProductServiceV2.getBatchData(ids);
		}
		Map<String,Object>spellingFlowerProductMap = new HashMap<String,Object>();
		if(productList != null && productList.size() > 0) {
			for (BaseProduct product : productList) {
				List<SplitTextureDTO> splitTextureDTOList = new ArrayList<>();
				/*Integer isSplit = 0;*/
				
				/* 2018-11-15 edit by zhangwj TODO:现在没有区分拼花产品
				if(ProductConstant.ISCOMPLEXPARQUET_TRUE.equals(product.getIsComplexParquet())) {
					// update by huangsongbo 2018.6.19 针对于拼花产品 ->start
					splitTextureDTOList = resTextureService.getSplitTexturesChooseForSpellingflower(product.getParquetTextureIds(), product.getId());
					// update by huangsongbo 2018.6.19 针对于拼花产品 ->end
				}else {*/
					// 针对于除拼花产品外的其他产品
				if(StringUtils.isNotEmpty(product.getSplitTexturesInfo())){
					Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(loginUser,modelType,product.getId(), product.getSplitTexturesInfo(), "choose");
					splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
				}else {
					if(StringUtils.isNotBlank(product.getMaterialPicIds())){
						this.getSingleTexture(loginUser,modelType,product, splitTextureDTOList);
					}
				}
				/*}*/

				if(null!=splitTextureDTOList&&splitTextureDTOList.size()>0){
					spellingFlowerProductMap.put(product.getId()+"", splitTextureDTOList);
				}
			}	
		}
		unityDesignPlan.setSpellingFlowerProductMap(spellingFlowerProductMap);
	}

	public String spellingFlowerFileTo(){

//		String rootPath = "/data001/mfs";
		String rootPath = "/data001/resource";
		StringBuffer result = new StringBuffer("执行完成；失败id");
		List<ResDesign> resDesignList = resDesignService.getSpellingFlowerFileTo();

		if(null != resDesignList && resDesignList.size() > 0){
			for (ResDesign resDesign : resDesignList) {
				String newFilePath = "";
				String oldFilePath = resDesign.getFilePath();
				boolean bool = false;

				String newFilePathPrefix = oldFilePath.substring(0,oldFilePath.lastIndexOf("/")+1);
				String fileName = "";
				if(oldFilePath.contains("_1")){
					fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+"_1.txt";
				}else{
					fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
				}
				newFilePath = newFilePathPrefix+fileName;

				try {
					File oldFile = new File(rootPath+oldFilePath);
					bool = oldFile.renameTo(new File(rootPath+newFilePath));
				}catch (Exception e){
					bool = false;
					result.append(","+resDesign.getId());
					logger.error("设计方案文件重命名失败resDesign="+resDesign.getId()+",filePath="+oldFilePath,e);
				}

				if(bool){
					//更改数据库
					ResDesign resDesignN = new ResDesign();
					resDesignN.setId(resDesign.getId());
					resDesignN.setFilePath(newFilePath);
					resDesignService.update(resDesignN);
				}
			}
		}

		return result.toString();
	}


	public void spellingFlowerData(DesignPlanModel designPlan,UnityDesignPlan unityDesignPlan) {
		if(designPlan == null || StringUtils.isEmpty(designPlan.getSpellingFlowerProduct()) || unityDesignPlan == null) {
			return;
		}
		if(designPlan.getSpellingFlowerFileId()!=null && designPlan.getSpellingFlowerFileId().intValue() > 0) {
			ResDesign resDesign  = resDesignService.get(designPlan.getSpellingFlowerFileId());
			if(resDesign != null) {
				unityDesignPlan.setSpellingFlower(resDesign.getFilePath());
			}
		}
		//Map<Integer,String>idMap = new HashMap<Integer,String>();
		String [] arr = designPlan.getSpellingFlowerProduct().split(",");
		List<BaseProduct>productList = null;
		if(arr != null && arr.length > 0 && !"[]".equals(arr)) {
			List<Integer>ids = new ArrayList<Integer>();
			for (String id : arr) {
				/*int productId = 0;
				if(id.indexOf("_")!=-1) {
					String idStr = id.substring(0, id.indexOf("_"));
					productId = Integer.parseInt(idStr);
					String str = id.split("_")[1];
					idMap.put(productId, str);
				}else {
					productId = Integer.parseInt(id);
				}*/
				ids.add(Integer.parseInt(id));
			}
			productList = baseProductServiceV2.getBatchData(ids);
		}
		Map<String,Object>spellingFlowerProductMap = new HashMap<String,Object>();
		if(productList != null && productList.size() > 0) {
			for (BaseProduct product : productList) {
				List<SplitTextureDTO> splitTextureDTOList = new ArrayList<>();
				Integer isSplit = 0;
				if(StringUtils.isNotEmpty(product.getSplitTexturesInfo())){
					Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(product.getId(), product.getSplitTexturesInfo(), "choose");
					isSplit = (Integer) map.get("isSplit");
					splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
				}else {
					if(StringUtils.isNotBlank(product.getMaterialPicIds())){
						this.getSingleTexture(product, splitTextureDTOList);
					}
				}
				/*product.setIsSplit(isSplit);
				product.setSplitTexturesChoose(splitTextureDTOList);
				if(idMap != null && idMap.size()>0) {
					idMap.get(product.getId())
				}
				for (SplitTextureDTO splitTextureDTO : splitTextureDTOList) {

				}*/
				if(null!=splitTextureDTOList&&splitTextureDTOList.size()>0){
					spellingFlowerProductMap.put(product.getId()+"", splitTextureDTOList);
				}
			}
		}
		unityDesignPlan.setSpellingFlowerProductMap(spellingFlowerProductMap);
	}

	public void getSingleTexture(BaseProduct product,List<SplitTextureDTO> splitTextureDTOList) {
		if( splitTextureDTOList == null ){
			return;
		}
		ResModel textureBallModel = null;
		String materialPath = "";
		ResPic normalPic = null;
		String normalParam = "";
		String normalPath = "";
		SplitTextureDTO splitTextureDTO = null;
		ResTexture resTexture = resTextureService.get(Integer.valueOf(product.getMaterialPicIds()));
		if(null!=resTexture){
			if(resTexture.getTextureBallFileId() != null){
				textureBallModel = resModelService.get(resTexture.getTextureBallFileId());
				if(textureBallModel != null){
					materialPath = textureBallModel.getModelPath();
					materialPath = Utils.dealWithPath(materialPath, "linux");
				}
			}
			if(resTexture.getNormalPicId()!=null){
				normalParam = resTexture.getNormalParam();
				normalPic =  resPicService.get(resTexture.getNormalPicId());
				if(normalPic!=null){
					normalPath = normalPic.getPicPath();
					normalPath = Utils.dealWithPath(normalPath, "linux");
				}
			}

			String resTexturePic = null; //材质图片
			if(resTexture.getPicId() !=null && resTexture.getPicId().intValue() > 0) {
				ResPic resPic = resPicService.get(resTexture.getPicId());
				if(resPic != null) {
					resTexturePic = resPic.getPicPath();
				}
			}

			// 单材质产品
			splitTextureDTO = new SplitTextureDTO(product.getProductCode(), "", null);
			SplitTextureDTO.ResTextureDTO resTextureDTO = splitTextureDTO.new ResTextureDTO(
					Integer.valueOf(product.getMaterialPicIds()),resTexturePic,resTexture.getTextureAttrValue(),
					resTexture.getFileHeight(),resTexture.getFileWidth(),resTexture.getLaymodes(),materialPath,normalParam,normalPath);
			resTextureDTO.setKey("1");
			resTextureDTO.setProductId(product.getId());
			List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
			resTextureDTOList.add(resTextureDTO);
			splitTextureDTO.setList(resTextureDTOList);
			
			//加入
			splitTextureDTOList.add(splitTextureDTO);
		}
	}

	public void getSingleTexture(LoginUser loginUser,String modelType,BaseProduct product,List<SplitTextureDTO> splitTextureDTOList) {
		if( splitTextureDTOList == null ){
			return;
		}
		ResModel textureBallModel = null;
		String materialPath = "";
		ResPic normalPic = null;
		String normalParam = "";
		String normalPath = "";
		Integer textureBallFileId = null;
		SplitTextureDTO splitTextureDTO = null;
		
		// update by huangsongbo 2018.6.11 ->start
		/*String mediaType = loginUser.getMediaType();//媒介*/
		String mediaType = null;
		if(loginUser != null) {
			mediaType = loginUser.getMediaType();
		}
		// update by huangsongbo 2018.6.11 ->end

		if( StringUtils.isNotBlank(product.getMaterialPicIds()) ){
			List<String> productMaterialList = Arrays.asList(product.getMaterialPicIds().split(","))
					.stream()
					.filter(productMaterialId -> ( !StringUtils.isEmpty(productMaterialId) && !"-1".equals(productMaterialId) && !"0".equals(productMaterialId) ))
					.collect(Collectors.toList());
			for( String productMaterialId : productMaterialList ){
				ResTexture resTexture = resTextureService.get(Integer.valueOf(productMaterialId));
				if(null!=resTexture){
					logger.error("getSingleTexture.mediaType="+mediaType+",modelType="+modelType);

					//根据媒介类型取值
					if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){
						if ("5".equals(mediaType)) {
							textureBallFileId = resTexture.getIosTextureBallFileId();
						} else if ("6".equals(mediaType)) {
							textureBallFileId = resTexture.getAndroidTextureBallFileId();
						}else{
							textureBallFileId = resTexture.getTextureBallFileId();
						}
					}else{
						if(modelType.equals("IPhonePlayer")){
							textureBallFileId = resTexture.getIosTextureBallFileId();
						}if(modelType.equals("Android")){
							textureBallFileId = resTexture.getAndroidTextureBallFileId();
						}else{
							textureBallFileId = resTexture.getTextureBallFileId();
						}
					}

					if(textureBallFileId != null && textureBallFileId.intValue()>0){
						textureBallModel = resModelService.get(textureBallFileId);
						if(textureBallModel != null){
							materialPath = textureBallModel.getModelPath();
							materialPath = Utils.dealWithPath(materialPath, "linux");
						}
					}

					if(resTexture.getNormalPicId()!=null){
						normalParam = resTexture.getNormalParam();
						normalPic =  resPicService.get(resTexture.getNormalPicId());

						if(normalPic!=null){
							String smallPicInfo = normalPic.getSmallPicInfo();
							Map<String,String> map = this.getSmallPicInfoMap(smallPicInfo);

							//根据媒介类型取值
							if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){

								if ("5".equals(mediaType)) {
									Integer iosId = null != map.get("ios") ? Integer.valueOf(map.get("ios")) : null;
									ResPic iosResPic = iosId != null ? resPicService.get(iosId) : null;
									normalPath = iosResPic != null ? iosResPic.getPicPath() : "";
								} else if ("6".equals(mediaType)) {
									Integer androidId = null != map.get("android") ? Integer.valueOf(map.get("android")) : null;
									ResPic androidResPic = androidId != null ? resPicService.get(androidId) : null;
									normalPath = androidResPic != null ? androidResPic.getPicPath() : "";
								}else{
									normalPath=normalPic.getPicPath();
								}

							}else{

								if(modelType.equals("IPhonePlayer")){
									Integer iosId = null != map.get("ios") ? Integer.valueOf(map.get("ios")) : null;
									ResPic iosResPic = iosId != null ? resPicService.get(iosId) : null;
									normalPath = iosResPic != null ? iosResPic.getPicPath() : "";
								}if(modelType.equals("Android")){
									Integer androidId = null != map.get("android") ? Integer.valueOf(map.get("android")) : null;
									ResPic androidResPic = androidId != null ? resPicService.get(androidId) : null;
									normalPath = androidResPic != null ? androidResPic.getPicPath() : "";
								}else{
									normalPath=normalPic.getPicPath();
								}

							}

							normalPath = Utils.dealWithPath(normalPath, "linux");
						}
					}

					String resTexturePic = null; //材质图片
					if(resTexture.getPicId() !=null && resTexture.getPicId().intValue() > 0) {
						ResPic resPic = resPicService.get(resTexture.getPicId());
						if(resPic != null) {
							String smallPicInfo = resPic.getSmallPicInfo();
							Map<String,String> map = this.getSmallPicInfoMap(smallPicInfo);

							//根据媒介类型取值
							if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){

								if ("5".equals(mediaType)) {
									Integer iosId = null==map.get("ios")?null:Integer.valueOf(map.get("ios"));
									ResPic iosResPic = iosId == null ? null : resPicService.get(iosId);
									resTexturePic = iosResPic != null ? iosResPic.getPicPath() : "";
								} else if ("6".equals(mediaType)) {
									Integer androidId = null==map.get("android")?null:Integer.valueOf(map.get("android"));
									ResPic androidResPic = androidId == null ? null : resPicService.get(androidId);
									resTexturePic = androidResPic != null ? androidResPic.getPicPath() : "";
								}else{
									resTexturePic=resPic.getPicPath();
								}

							}else{

								if(modelType.equals("IPhonePlayer")){
									Integer iosId = null==map.get("ios")?null:Integer.valueOf(map.get("ios"));
									ResPic iosResPic = iosId == null ? null : resPicService.get(iosId);
									resTexturePic = iosResPic != null ? iosResPic.getPicPath() : null;
								}if(modelType.equals("Android")){
									Integer androidId = null==map.get("android")?null:Integer.valueOf(map.get("android"));
									ResPic androidResPic = androidId == null ? null : resPicService.get(androidId);
									resTexturePic = androidResPic != null ? androidResPic.getPicPath() : "";
								}else{
									resTexturePic=resPic.getPicPath();
								}

							}

						}
					}

					// 单材质产品
					splitTextureDTO = new SplitTextureDTO(product.getProductCode(), "", null);
					SplitTextureDTO.ResTextureDTO resTextureDTO = splitTextureDTO.new ResTextureDTO(
							Integer.valueOf(productMaterialId),resTexturePic,resTexture.getTextureAttrValue(),
							resTexture.getFileHeight(),resTexture.getFileWidth(),resTexture.getLaymodes(),materialPath,normalParam,normalPath);
					resTextureDTO.setKey("1");
					resTextureDTO.setProductId(product.getId());
					List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
					resTextureDTOList.add(resTextureDTO);
					splitTextureDTO.setList(resTextureDTOList);

					//加入
					splitTextureDTOList.add(splitTextureDTO);
				}
			}
		}
	}

	private Map getSmallPicInfoMap(String smallPicInfo){
		Map<String,String> map = new HashMap<String,String>();
		String[] strs = StringUtils.isEmpty(smallPicInfo) ? new String[0] : smallPicInfo.split(";");
		for(String s:strs){
			String[] ms = s.split(":");
			map.put(ms[0], ms[1]);
		}
		return map;
	}

	public Object getDesignPlanInfo(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName,Boolean isRelease, LoginUser loginUser, String mediaType, String needOrNoCache) {
	                // 参数验证/处理 ->start
	                if(StringUtils.isEmpty(needOrNoCache)) {
	                        needOrNoCache = Constants.NEED_CACHE;
	                }
	                String msg = "";
	                if (newFlag == null) {
	                        newFlag = 0;
	                }
	                
	                DesignPlanModel designPlan = designPlanService.selectDesignPlanInfo(designPlanId);

	                if (designPlan == null) {
	                        return new ResponseEnvelope<UnityDesignPlan>(false, msg);
	                }
	                if (StringUtils.isEmpty(designPlan.getFilePath())) {
	                        logger.error("找不到方案ID="+designPlan.getDesignPlanId()+"配置文件！templetId="+designPlan.getDesignTemplateId());
	                        return new ResponseEnvelope<UnityDesignPlan>(false,"配置文件异常！");
	                }
	                // 构建unity模型加载时需要的model对象:返回设计方案,空间模型,产品列表及产品配置等信息
	                UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
	                //获取拼花信息
//	              this.spellingFlowerData(designPlan,unityDesignPlan);
	                unityDesignPlan.setNewFlag(newFlag);// 是否是第一次进入
	                // 一.获取设计方案信息
	                unityDesignPlan.setServiceUrl(SERVERURL);// 访问地址
	                // unityDesignPlan.setUploadDir(UPLOADDIR);//文件上传路径,资源类信息需使用访问地址+上传路径+文件路径
	                unityDesignPlan.setResourcesUrl(RESOURCESURL);// 资源文件访问地址
	                
	                //获取拼花信息
	                this.spellingFlowerData(designPlan,unityDesignPlan);
	                // U3D界面UI文件
	                unityDesignPlan.setDesignPlanUIPath("/pages/online/resource/UI.assetbundle");
	                unityDesignPlan.setDesignPlanId(designPlan.getDesignPlanId());// 设计方案id
	                unityDesignPlan.setPlanName(designPlan.getPlanName());// 设计方案名称
	                unityDesignPlan.setPlanCode(designPlan.getPlanCode());// 设计方案编码
	                Integer modelTemplteFileId = designPlan.getModelId();
	                // 模型设计方案公共资源(同样板房资源)
	                if (StringUtils.isNotBlank(designPlan.getModelPath())) {
	                        unityDesignPlan.setU3dModelPath(designPlan.getModelPath());
	                } else {
	                        unityDesignPlan.setU3dModelPath("");
	                }
	                // 模型总体配置文件
	                unityDesignPlan.setDesignPlanConfigPath(designPlan.getFilePath());

	                // 二.获取空间信息
	                UnitySpaceCommon unitySpaceCommon = new UnitySpaceCommon();
	                Integer spaceId = designPlan.getSpaceCommonId();
	                unitySpaceCommon.setSpaceCommonId(spaceId);
	                unitySpaceCommon.setHouseId(houseId);
	                unitySpaceCommon.setLivingId(livingId);
	                unitySpaceCommon.setPlanSource(designPlan.getPlanSource());
	                unitySpaceCommon.setSpaceCode(designPlan.getSpaceCode());// 空间编码(场景编码)
	                unitySpaceCommon.setSpaceName(designPlan.getSpaceName());
	                unitySpaceCommon.setSpaceAreas(designPlan.getSpaceAreas());
	                unitySpaceCommon.setResidentialUnitsName(residentialUnitsName); // 小区户型名称
	                unitySpaceCommon.setHouseTypeValue(designPlan.getHouseTypeValue());
	                unitySpaceCommon.setHouseTypeName(designPlan.getHouseTypeName());
	                unityDesignPlan.setUnitySpaceCommon(unitySpaceCommon);
	                //空间灯光（白天、黄昏、黑夜）u3d模型文件
	                if(designPlan.getDaylightU3dModelId() != null && designPlan.getDaylightU3dModelId() > 0){
	                        ResModel resModel = resModelService.get(designPlan.getDaylightU3dModelId());//res_model 中获取白天的模型文件
	                        unitySpaceCommon.setDaylightU3dModelPath(resModel==null?"":resModel.getModelPath());
	                }else{
	                        unitySpaceCommon.setDaylightU3dModelPath("");
	                }
	                if(designPlan.getDusklightU3dModelId() != null && designPlan.getDusklightU3dModelId() > 0){
	                        ResModel resModel = resModelService.get(designPlan.getDusklightU3dModelId());//res_model 中获取黄昏的模型文件
	                        unitySpaceCommon.setDusklightU3dModelPath(resModel==null?"":resModel.getModelPath());
	                }else{
	                        unitySpaceCommon.setDaylightU3dModelPath("");
	                }
	                if(designPlan.getNightlightU3dModelId() != null && designPlan.getNightlightU3dModelId() > 0){
	                        ResModel resModel = resModelService.get(designPlan.getNightlightU3dModelId());//res_model 中获取夜晚的模型文件
	                        unitySpaceCommon.setNightlightU3dModelPath(resModel==null?"":resModel.getModelPath());
	                }else{
	                        unitySpaceCommon.setDaylightU3dModelPath("");
	                }

	                //设计模式
	                if( designPlan.getDesignTempletCode() == null ){
	                        msg = "designTemplet is null !designTempletId = "+designPlan.getDesignTemplateId();
	                        return new ResponseEnvelope<UnityDesignPlan>(false,msg);
	                }else {
	                        if (designPlan.getDesignTempletCode().endsWith("_000")) {
	                                unityDesignPlan.setDesignMode("0");
	                        } else {
	                                unityDesignPlan.setDesignMode("1");
	                        }
	                }

	                // 三.获取方案中用到的产品列表
	                // 产品信息,返回装修顺序及相机参数值
	                // 产品级别数据存储
	                List<UnityPlanProduct> unityPlanProductList = new ArrayList<UnityPlanProduct>();
	                // 最终数据U3DUI界面转换存储
	                List<UnityPlanProduct> newUnityPlanProductList = new ArrayList<UnityPlanProduct>();
	                // 获取方案产品列表
	                DesignPlanProduct designPlanProduct = new DesignPlanProduct();
	                designPlanProduct.setIsDeleted(0);
	                designPlanProduct.setPlanId(designPlan.getDesignPlanId());
	                List<DesignPlanProduct> designProductList = null;
	                if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
	                        designProductList = DesignPlanProductCacher.getAllList(designPlanProduct);
	                } else {
	                        designProductList = designPlanProductService.getList(designPlanProduct);//设计方案中使用到的产品
	                }
	                

	                if (designProductList != null && designProductList.size() > 0) {
	                        
	                        TreeSet<String> productTypeCodeSet = new TreeSet<String>();
	                        Map<String, UnityPlanProduct> unityPlanProductMap_p = new HashMap<String, UnityPlanProduct>();
	                        for (DesignPlanProduct planProduct : designProductList) {
	                                UnityPlanProduct unityPlanProduct = new UnityPlanProduct();
	                                unityPlanProduct.setIsDirty(planProduct.getIsDirty());
	                                unityPlanProduct.setPlanProductId(planProduct.getId());
	                                unityPlanProduct.setProductSequence(planProduct.getProductSequence());
	                                unityPlanProduct.setMaterialPicPaths(new String[]{});
	                                unityPlanProduct.setDecorationModelPath(new String[]{});
	                                unityPlanProduct.setPosIndexPath(planProduct.getPosIndexPath());
	                                unityPlanProduct.setPosName(planProduct.getPosName());
	                                unityPlanProduct.setBindProductId(planProduct.getBindParentProductId());
	                                
	                                /*天花、地面、地面结构拆分*/
	                                unityPlanProduct.setIsStandard(planProduct.getIsStandard());
	                                unityPlanProduct.setCenter(planProduct.getCenter());
	                                unityPlanProduct.setRegionMark(planProduct.getRegionMark());
	                                unityPlanProduct.setStyleId(planProduct.getStyleId());
	                                unityPlanProduct.setMeasureCode(planProduct.getMeasureCode());
	                                unityPlanProduct.setDescribeInfo(planProduct.getDescribeInfo());
	                                unityPlanProduct.setProductIndex(planProduct.getProductIndex());
	                                
	                                unityPlanProduct.setIsGroupReplaceWay(planProduct.getIsGroupReplaceWay());
	                                unityPlanProduct.setIsMainStructureProduct(planProduct.getIsMainStructureProduct());

	                                /*处理结构返回格式*/
	                                unityPlanProduct = this.getPlanProductStructureJson(unityPlanProduct, planProduct, designPlan, loginUser);
	                                // 产品的基本信息
	                                BaseProduct baseProduct = null;
	                                if (planProduct.getProductId() != null && planProduct.getProductId() > 0) {
	                                        if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
	                                                baseProduct = BaseProductCacher.get(planProduct.getProductId());
	                                        } else {
	                                                baseProduct = baseProductService.get(planProduct.getProductId());//产品公用基本信息   '产品品牌',  '产品风格', '产品规格',  '产品颜色',  '产品长度', '产品宽度', '产品高度', '销售价格',等等
	                                        }
	                                }
	                                if (null == baseProduct) {
	                                        msg = "planProduct.getProductId():" + planProduct.getProductId();
	                                        return new ResponseEnvelope<UnityDesignPlan>(false, msg);
	                                }

	                                String productTypeValue = baseProduct.getProductTypeValue();
	                                Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
	                                SysDictionary sysDictionary = new SysDictionary();
	                                if (baseProduct != null && productSmallTypeValue != null
	                                                && StringUtils.isNotBlank(productTypeValue)) {
	                                        //通过大小类获取小类信息
	                                        Map<String, Object> map = new HashMap<>();
	                                        map.put("smallTypeValue", productSmallTypeValue);
	                                        map.put("typeValue", productTypeValue);
	                                        sysDictionary = sysDictionaryService.selectSmallTypeObj(map);

	                                        if (sysDictionary != null) {
	                                                unityPlanProduct.setMoveWay(sysDictionary.getAtt5());
	                                                if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getType())) {//墙面
	                                                        unityPlanProduct.setBgWall(Utils.getIsBgWall(sysDictionary == null ? "" : sysDictionary.getValuekey()));
	                                                } else {
	                                                        unityPlanProduct.setBgWall(0);
	                                                }
	                                        } else {
	                                                logger.debug("sDictionary is null, sd.getValuekey()=" + /*sysDictionary.getValuekey() */null
	                                                + ";baseProduct.getProductSmallTypeValue()=" + baseProduct.getProductSmallTypeValue() 
	                                                + ";productid=" + baseProduct.getProductId() + ";baseProduct.getProductTypeValue()=" 
	                                                + baseProduct.getProductTypeValue()+";planProduct.getProductId()="+planProduct.getProductId());
	                                        }
	                                        if (baseProduct.getBmIds() != null) {
	                                                unityPlanProduct.setIsCustomized(1);
	                                        }
	                                        //系列标志
	                                        unityPlanProduct.setSeriesSign(baseProductService.getSeriesSign(sysDictionary.getValuekey()));
	                                }
	                                if (null != baseProduct) {
	                                        if(baseProduct.getProductCode().equals("nork_ddli_0021")) {
	                                                logger.error("getDesignPlanInfo nork_ddli_0021==" + baseProduct.getId());
	                                        }
	                                        
	                                        if(baseProduct.getProductCode().equals("masd_ddli_0004")) {
	                                                logger.error("getDesignPlanInfo masd_ddli_0004==" + baseProduct.getId());
	                                        }
	                                        unityPlanProduct.setProductId(baseProduct.getId());
	                                        unityPlanProduct.setProductCode(baseProduct.getProductCode());
	                                        unityPlanProduct.setParentProductId(baseProduct.getParentId());
	                                        unityPlanProduct.setProductLength(baseProduct.getProductLength());
	                                        unityPlanProduct.setProductWidth(baseProduct.getProductWidth());
	                                        unityPlanProduct.setProductHeight(baseProduct.getProductHeight());
	                                        unityPlanProduct.setMinHeight(baseProduct.getMinHeight());
	                                        //如果该墙面有绑定关系，则取绑定产品白模长宽高
	                                        String bindProductid = planProduct.getBindParentProductId();
	                                        if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary == null ? "" : sysDictionary.getType()) && StringUtils.isNotBlank(bindProductid)) {
	                                                String array[] = bindProductid.split(",");
	                                                BaseProduct baiMoProduct = null;
	                                                StringBuffer fullPaveLength = new StringBuffer();

	                                                for (String bindId : array) {
	                                                        if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
	                                                                baiMoProduct = BaseProductCacher.get(Utils.getIntValue(bindId));
	                                                        } else {
	                                                                baiMoProduct = baseProductService.get(Utils.getIntValue(bindId));
	                                                        }
	                                                        if (baiMoProduct != null && baiMoProduct.getFullPaveLength() != null) {
	                                                                fullPaveLength.append(baiMoProduct.getFullPaveLength() + ",");
	                                                        }
	                                                }
	                                                if (fullPaveLength.toString().length() > 0) {
	                                                        String fullPave = fullPaveLength.toString();
	                                                        unityPlanProduct.setFullPaveLength(fullPave != null ? fullPave.substring(0, fullPave.length() - 1) : fullPave);
	                                                }
	                                        }
	                                        //如果是背景墙、窗帘、淋浴屏则取白模产品的长宽高
	                                        Integer baiMoId = planProduct.getInitProductId();
	                                        Map<String,String> stretchZoomMap = baseProductService.getStretchZoomLength(sysDictionary == null ? "" : sysDictionary.getValuekey());
	                                        if (stretchZoomMap != null && stretchZoomMap.size() > 0 && baiMoId != null && baiMoId.intValue() > 0) {
	                                                BaseProduct baiMoProduct = null;
	                                                if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
	                                                        baiMoProduct = BaseProductCacher.get(baiMoId);
	                                                } else {
	                                                        baiMoProduct = baseProductService.get(baiMoId);
	                                                }
	                                                if (baiMoProduct != null) {
	                                                        if (StringUtils.isNotEmpty(baiMoProduct.getProductLength())) {
	                                                                unityPlanProduct.setInitModelLength(Integer.parseInt(baiMoProduct.getProductLength()));
	                                                        }
	                                                        if (StringUtils.isNotEmpty(baiMoProduct.getProductWidth())) {
	                                                                unityPlanProduct.setInitModelWidth(Integer.parseInt(baiMoProduct.getProductWidth()));
	                                                        }
	                                                        if (StringUtils.isNotEmpty(baiMoProduct.getProductHeight())) {
	                                                                unityPlanProduct.setInitModelHeight(Integer.parseInt(baiMoProduct.getProductHeight()));
	                                                        }
	                                                        if (baiMoProduct.getFullPaveLength() != null) {
	                                                                unityPlanProduct.setFullPaveLength(baiMoProduct.getFullPaveLength());
	                                                        }
	                                                }
	                                        }
	                                }

	                                String u3dModelId = baseProductService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), baseProduct);
	                                ResModel resModel = resModelService.get(StringUtils.isEmpty(u3dModelId) ? 0 : new Integer(u3dModelId));
	                                if (resModel != null) {
	                                        unityPlanProduct.setProductModelPath(resModel.getModelPath());
	                                        unityPlanProduct.setModelLength(resModel.getLength());
	                                        unityPlanProduct.setModelWidth(resModel.getWidth());
	                                        unityPlanProduct.setModelHeight(resModel.getHeight());
	                                        unityPlanProduct.setModelMinHeight(resModel.getMinHeight());
	                                } else {
	                                        //unityPlanProduct.setProductModelPath("");
	                                        /*应对只有材质的硬装产品无模型的情况*/
	                                        boolean isHard = false;
	                                        if (baseProduct != null) {
	                                                isHard = baseProductService.isHard(baseProduct);
	                                        }
	                                        if (isHard) {
	                                                BaseProduct baimoProduct = null;
	                                                Integer currentProductId = null;
	                                                //换贴图应找当前产品模型
	                                                if (planProduct.getModelProductId() != null && planProduct.getModelProductId() != 0) {
	                                                        currentProductId = planProduct.getModelProductId();
	                                                        unityPlanProduct.setModelProductId(planProduct.getModelProductId());
	                                                } else {
	                                                        currentProductId = planProduct.getInitProductId();
	                                                }

//	                                              BaseProduct baseProduct_ = new BaseProduct();
//	                                              baseProduct_.setId(currentProductId);
//	                                              baseProduct_.setMediaType(mediaType);
	                                                if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
	                                                        baimoProduct = BaseProductCacher.get(currentProductId);
//	                                                      baimoProduct = BaseProductCacher.getDataAndModel(baseProduct_);
	                                                } else {
	                                                        baimoProduct = baseProductService.get(currentProductId);
//	                                                      baimoProduct = baseProductService.getDataAndModel(baseProduct_);
	                                                }
	                                                /*获取不同媒介u3d模型*/
	                                                String modelId = baseProductService.getU3dModelId(mediaType,baimoProduct);
	                                                if( StringUtils.isNotBlank(modelId) ){
	                                                        ResModel resModel1 = null ;
	                                                        if(Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)){
	                                                                resModel1 = ResourceCacher.getModel(Integer.valueOf(modelId));
	                                                        }else{
	                                                                resModel1 = resModelService.get(Integer.valueOf(modelId));
	                                                        }
	                                                        if( resModel1 != null ){
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
	                                                ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));//材质库
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
	                                        unityPlanProduct.setMaterialPicPaths((String[]) materialPicList.toArray(new String[materialPicList.size()]));
	                                }

	                                /*---------------------xiaoxc  end-----*/
	                                /* 产品子集数量*/
	                                unityPlanProduct.setLeafNum(0);
	                                /* 标示产品在界面中的展示类型*/
	                                unityPlanProduct.setIsLeaf(new Integer(1));
	                                /* 产品是否隐藏*/
	                                unityPlanProduct.setIsHide(planProduct.getIsHide());

	                                String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
	                                if (StringUtils.isNotBlank(splitTexturesInfo)) {
	                                        if (StringUtils.isNotBlank(planProduct.getSplitTexturesChooseInfo())) {
	                                                splitTexturesInfo = planProduct.getSplitTexturesChooseInfo();
	                                        }
	                                        Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo, "choose");
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
	                                                        List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
	                                                        SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
	                                                        SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
	                                                        resTextureDTO.setKey(splitTextureDTO.getKey());
	                                                        resTextureDTO.setProductId(baseProduct.getId());
	                                                        resTextureDTOList.add(resTextureDTO);
	                                                        splitTextureDTO.setList(resTextureDTOList);
	                                                        splitTextureDTOList.add(splitTextureDTO);
	                                                        unityPlanProduct.setSplitTexturesChoose(splitTextureDTOList);
	                                                }
	                                        }
	                                }

	                                /*处理拆分材质产品的默认材质信息->end*/
	                                UnityPlanProduct unityPlanProduct_p = unityPlanProduct.copy();
	                                // 产品类别信息
	                                if (!StringUtils.isEmpty(productTypeValue)) {
	                                        SysDictionary sd = sysDictionaryService.getSysDictionary("productType", new Integer(productTypeValue));
	                                        if (sd != null) {
	                                                /* 为保证父节点与子节点的productTypeCode相同，指定如下规则：
	                                                子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
	                                                父节点时，parentTypeCode存在值(暂时不取)，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值*/
	                                                unityPlanProduct.setProductTypeValue(sd.getValue());
	                                                unityPlanProduct.setProductTypeCode(sd.getValuekey());
	                                                unityPlanProduct.setProductTypeName(sd.getName());

	                                                unityPlanProduct_p.setProductTypeValue(sd.getValue());
	                                                unityPlanProduct_p.setProductTypeCode(sd.getValuekey());
	                                                unityPlanProduct_p.setProductTypeName(sd.getName());
	                                                /* 获取子节点的父节点信息*/
	                                                unityPlanProduct.setParentTypeCode(sd.getValuekey());
	                                                unityPlanProduct.setParentTypeName(sd.getName());
	                                                unityPlanProduct.setParentTypeValue(sd.getValue());

	                                                unityPlanProduct_p.setParentTypeValue(-1);
	                                                unityPlanProduct_p.setParentTypeCode("");
	                                                unityPlanProduct_p.setParentTypeName("");

	                                                /* 获取子节点的节点信息*/
	                                                if (productSmallTypeValue != null && new Integer(productSmallTypeValue).intValue() > 0) {
	                                                        if (sysDictionary != null) {
	                                                                unityPlanProduct.setSmallTypeValue(sysDictionary.getValue());
	                                                                unityPlanProduct.setSmallTypeCode(sysDictionary.getValuekey());
	                                                                unityPlanProduct.setSmallTypeName(sysDictionary.getName());
	                                                                /* 是否是白模*/
	                                                                Integer isBm = 0;
	                                                                if ("baimo".equals(sysDictionary.getAtt3())) {
	                                                                        isBm = 1;
	                                                                        String bjType = Utils.getValue("app.smallProductType.beiJingWall", "");
	                                                                        if( bjType.indexOf(sysDictionary.getValuekey()) != -1 ){
	                                                                                unityPlanProduct.setIsHide(1);
	                                                                        }
	                                                                }
	                                                                unityPlanProduct.setIsBaimo(isBm);
	                                                                unityPlanProduct_p.setIsBaimo(isBm);
	                                                                /* 软装硬装以下规则处理，同时按最小基本的数据定义-按1硬装2软装,默认软装*/
	                                                                String rootType = StringUtils.isEmpty(sysDictionary.getAtt1()) ? "2" : sysDictionary.getAtt1().trim();
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

	                                /* 存储产品分类集合,便于组装UI界面*/
	                                if (!StringUtils.isEmpty(unityPlanProduct.getProductTypeCode())) {
	                                        productTypeCodeSet.add(unityPlanProduct.getProductTypeCode());
	                                        // 默认使用第一条记录信息做数据代入*/
	                                        if (!unityPlanProductMap_p.containsKey(unityPlanProduct.getProductTypeCode())) {
	                                                /* 为保证父节点与子节点的productTypeCode相同，指定如下规则：
	                                                子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
	                                                 父节点时，parentTypeCode存在值，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值*/
	                                                unityPlanProductMap_p.put(unityPlanProduct.getProductTypeCode(), unityPlanProduct_p);
	                                        }
	                                } else {
	                                        logger.debug("unityPlanProduct.getProductTypeCode() is null ;unityPlanProduct.getProductTypeCode()=" + unityPlanProduct.getProductTypeCode() + ";unityPlanProduct.getProductId=" + unityPlanProduct.getProductId() + ";unityPlanProduct.getProductCode=" + unityPlanProduct.getProductCode());
	                                }

	                                Map<String, String> map = new HashMap<String, String>();
	                                /*将材质的长宽也给  塞到这个list 中取 */
	                                String aterialPicIds = baseProduct.getMaterialPicIds();/**材质id**/
	                                if (aterialPicIds != null && !"".equals(aterialPicIds)) {
	                                        if (StringUtils.isNumeric(aterialPicIds)) {
	                                                ResTexture resTexture = resTextureService.get(Integer.parseInt(aterialPicIds));
	                                                if (resTexture != null) {
	                                                        unityPlanProduct.setTextureWidth(resTexture.getFileWidth() + "");
	                                                        unityPlanProduct.setTextureHeight(resTexture.getFileHeight() + "");
	                                                }
	                                        }
	                                }
	                                /*在组合产品查询列表 中 增加产品属性*/
	                                map = productAttributeService.getPropertyMap(baseProduct.getId());//产品属性
	                                unityPlanProduct.setPropertyMap(map);

	                                // 关联白模产品的属性
	                                Map<String,String> basicPropertyMap = new HashMap<>();
	                                basicPropertyMap = productAttributeService.getPropertyMap(planProduct.getInitProductId());
	                                unityPlanProduct.setBasicPropertyMap(basicPropertyMap);

	                                /* 样板房产品ID*/
	                                unityPlanProduct.setTemplateProductId(
	                                                planProduct.getInitProductId() == null ? "" : planProduct.getInitProductId().toString());

	                                /* 组装产品的规则*/
	                                String productTypeCode = unityPlanProduct.getProductTypeCode();/* 产品大类*/
	                                String productSmallTypeCode = unityPlanProduct.getSmallTypeCode();/* 产品小类*/
	                                String productId = null;// 产品ID
	                                if (unityPlanProduct.getProductId() != null) {
	                                        productId = unityPlanProduct.getProductId().toString();/*产品ID*/
	                                }

	                                /*获取规则*/
	                                Map<String, String> rulesMap = new HashMap<>();
	                                Map<Object, Object> rulesParamsMap = new HashMap<>();
	                                rulesParamsMap.put("rulesProductId", productId);
	                                ResponseEnvelope rulesResponseMapResult = null;
	                                if(Utils.enableRedisCache()){
	                                        rulesResponseMapResult = CommonCacher.getAll(ModuleType.DesignPlan, "getRulesSecondaryList", rulesParamsMap);
	                                }
	                                
	                                if (rulesResponseMapResult != null) {
	                                        rulesMap = (Map<String, String>) rulesResponseMapResult.getObj();
	                                } else {
	                                        rulesMap = designRulesService.getRulesSecondaryList(productId, productTypeCode,
	                                                        productSmallTypeCode, spaceId, designPlan.getDesignTemplateId(), new DesignRules(), map);

	                                }
	                                unityPlanProduct.setRulesMap(rulesMap);
	                                if(unityPlanProduct.getPlanProductId().intValue() == 3093779) {
	                                        logger.error("getDesignPlanInfo==>test==s06sf01" + unityPlanProduct.getProductId() + "pproductTypeCode=" + unityPlanProduct.getProductCode());
	                                }
	                                
	                                unityPlanProductList.add(unityPlanProduct);
	                        }
	                        /*大循环的结束括号*/

	                        // 四.定制装修导航(在产品列表中该增加大按钮性质,同时,只有一个时,删除小按钮)
	                        // 组装每个产品的分类信息
	                        this.getDecorationNavigationInfo(unityPlanProductList, newUnityPlanProductList, productTypeCodeSet, unityPlanProductMap_p);
	                }

	                ComparatorT cpmparator = new ComparatorT();
	                Collections.sort(newUnityPlanProductList, cpmparator);
	                unityDesignPlan.setDatalist(newUnityPlanProductList);
	                ResponseEnvelope responseEnvelope = new  ResponseEnvelope<UnityDesignPlan>(true,unityDesignPlan);

	                return responseEnvelope;

	        }

	@Override
	public Object getDesignPlanInfo(
			Integer designPlanId,
			Integer newFlag,
			String houseId,
			String livingId,
			String residentialUnitsName,
			Boolean isRelease, 
			LoginUser loginUser, 
			String mediaType, 
			String needOrNoCache,
			String modelType) {
		String logPrefix = "function:DesignPlanServiceImpl.getDesignPlanInfo -> ";
		
		// 参数验证/处理 ->start
		if(StringUtils.isEmpty(needOrNoCache)) {
			needOrNoCache = Constants.NEED_CACHE;
		}
		String msg = "";
		if (newFlag == null) {
			newFlag = 0;
		}

		DesignPlanModel designPlan = null;
		//logger.error("selectDesignPlanInfo.mediaType="+mediaType+",modelType="+modelType);
		if(StringUtils.isEmpty(modelType) || "null".equals(modelType)) {
			if ("5".equals(mediaType)) {
				designPlan = designPlanService.selectIosDesignPlanInfo(designPlanId);
			} else if ("6".equals(mediaType)) {
				designPlan = designPlanService.selectAndroidDesignPlanInfo(designPlanId);
			}else{
				designPlan = designPlanService.selectDesignPlanInfo(designPlanId);
			}
		}else{
			if(modelType.equals("IPhonePlayer")){
				designPlan = designPlanService.selectIosDesignPlanInfo(designPlanId);
			}if(modelType.equals("Android")){
				designPlan = designPlanService.selectAndroidDesignPlanInfo(designPlanId);
			}else{
				designPlan = designPlanService.selectDesignPlanInfo(designPlanId);
			}
		}

		if (designPlan == null) {
			logger.error("找不到方案,designPlanId: " + designPlanId);
			return new ResponseEnvelope<UnityDesignPlan>(false, "找不到方案！");
		}
		if (StringUtils.isEmpty(designPlan.getFilePath())) {
			logger.error("找不到方案ID="+designPlan.getDesignPlanId()+"配置文件！templetId="+designPlan.getDesignTemplateId());
			return new ResponseEnvelope<UnityDesignPlan>(false,"配置文件异常！");
		}
		// 构建unity模型加载时需要的model对象:返回设计方案,空间模型,产品列表及产品配置等信息
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		//获取拼花信息
//		this.spellingFlowerData(designPlan,unityDesignPlan);
		unityDesignPlan.setNewFlag(newFlag);// 是否是第一次进入
		// 一.获取设计方案信息
		unityDesignPlan.setServiceUrl(SERVERURL);// 访问地址
		// unityDesignPlan.setUploadDir(UPLOADDIR);//文件上传路径,资源类信息需使用访问地址+上传路径+文件路径
		unityDesignPlan.setResourcesUrl(RESOURCESURL);// 资源文件访问地址
		
		//获取拼花信息
		this.spellingFlowerData(loginUser,modelType,designPlan,unityDesignPlan);
		// U3D界面UI文件
		unityDesignPlan.setDesignPlanUIPath("/pages/online/resource/UI.assetbundle");
		unityDesignPlan.setDesignTempletId(designPlan.getDesignTemplateId());//样板房id
		unityDesignPlan.setDesignPlanId(designPlan.getDesignPlanId());// 设计方案id
		unityDesignPlan.setPlanName(designPlan.getPlanName());// 设计方案名称
		unityDesignPlan.setPlanCode(designPlan.getPlanCode());// 设计方案编码
		// 模型设计方案公共资源(同样板房资源)
		if (StringUtils.isNotBlank(designPlan.getModelPath())) {
			unityDesignPlan.setU3dModelPath(designPlan.getModelPath());
		} else {
			unityDesignPlan.setU3dModelPath("");
		}
		// 模型总体配置文件
		unityDesignPlan.setDesignPlanConfigPath(designPlan.getFilePath());

		/**
		 * 方案定制产品信息获取
		 * add by xiaoxc 20180828
		 */
		Integer customizedProductConfigFileId = designPlan.getCustomizedProductConfigId();
		if (null != customizedProductConfigFileId && 0 < customizedProductConfigFileId) {
			ResDesign resDesign = resDesignService.get(customizedProductConfigFileId);
			unityDesignPlan.setCustomizedProductConfigPath(resDesign == null ? "" : resDesign.getFilePath());
		}
		unityDesignPlan.setCustomizedProductOrderCode(designPlan.getCustomizedProductOrderCode());
		unityDesignPlan.setOrderId(designPlan.getOrderId() == null ? 0 : designPlan.getOrderId());
		unityDesignPlan.setPartnersName(designPlan.getPartnersName());
		unityDesignPlan.setExteriorOrderId(designPlan.getExteriorOrderId()+"");


		// 二.获取空间信息
		UnitySpaceCommon unitySpaceCommon = new UnitySpaceCommon();
		Integer spaceId = designPlan.getSpaceCommonId();
		unitySpaceCommon.setSpaceCommonId(spaceId);
		unitySpaceCommon.setHouseId(houseId);
		unitySpaceCommon.setLivingId(livingId);
		unitySpaceCommon.setPlanSource(designPlan.getPlanSource());
		unitySpaceCommon.setSpaceCode(designPlan.getSpaceCode());// 空间编码(场景编码)
		unitySpaceCommon.setSpaceName(designPlan.getSpaceName());
		unitySpaceCommon.setSpaceAreas(designPlan.getSpaceAreas());
		unitySpaceCommon.setResidentialUnitsName(residentialUnitsName); // 小区户型名称
		unitySpaceCommon.setHouseTypeValue(designPlan.getHouseTypeValue());
		unitySpaceCommon.setHouseTypeName(designPlan.getHouseTypeName());
		unityDesignPlan.setUnitySpaceCommon(unitySpaceCommon);
		//空间灯光（白天、黄昏、黑夜）u3d模型文件
		if(designPlan.getDaylightU3dModelId() != null && designPlan.getDaylightU3dModelId() > 0){
			ResModel resModel = resModelService.get(designPlan.getDaylightU3dModelId());//res_model 中获取白天的模型文件
			unitySpaceCommon.setDaylightU3dModelPath(resModel==null?"":resModel.getModelPath());
		}else{
			unitySpaceCommon.setDaylightU3dModelPath("");
		}
		if(designPlan.getDusklightU3dModelId() != null && designPlan.getDusklightU3dModelId() > 0){
			ResModel resModel = resModelService.get(designPlan.getDusklightU3dModelId());//res_model 中获取黄昏的模型文件
			unitySpaceCommon.setDusklightU3dModelPath(resModel==null?"":resModel.getModelPath());
		}else{
			unitySpaceCommon.setDaylightU3dModelPath("");
		}
		if(designPlan.getNightlightU3dModelId() != null && designPlan.getNightlightU3dModelId() > 0){
			ResModel resModel = resModelService.get(designPlan.getNightlightU3dModelId());//res_model 中获取夜晚的模型文件
			unitySpaceCommon.setNightlightU3dModelPath(resModel==null?"":resModel.getModelPath());
		}else{
			unitySpaceCommon.setDaylightU3dModelPath("");
		}

		//设计模式
		if( designPlan.getDesignTempletCode() == null ){
			msg = "designTemplet is null !designTempletId = "+designPlan.getDesignTemplateId();
			return new ResponseEnvelope<UnityDesignPlan>(false,msg);
		}else {
			if (designPlan.getDesignTempletCode().endsWith("_000")) {
				unityDesignPlan.setDesignMode("0");
			} else {
				unityDesignPlan.setDesignMode("1");
			}
		}

		// 三.获取方案中用到的产品列表
		// 产品信息,返回装修顺序及相机参数值
		// 产品级别数据存储
		List<UnityPlanProduct> unityPlanProductList = new ArrayList<UnityPlanProduct>();
		// 最终数据U3DUI界面转换存储
		List<UnityPlanProduct> newUnityPlanProductList = new ArrayList<UnityPlanProduct>();
		// 获取方案产品列表
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setPlanId(designPlan.getDesignPlanId());
		List<DesignPlanProduct> designProductList = null;
		//add by xiaoxc 这块用缓存会存在问题（还原和改造同一方案进去会列表有变更）
//		if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
//			designProductList = DesignPlanProductCacher.getAllList(designPlanProduct);
//		} else {
//			designProductList = designPlanProductService.getList(designPlanProduct);//设计方案中使用到的产品
//		}
		
		// update by huangsongbo 2018.6.11,left join 查出oldPosName ->start
		designProductList = designPlanProductService.getList(designPlanProduct);
		/*designProductList = designPlanProductService.getListMoreInfo(designPlanProduct);*/
		// update by huangsongbo 2018.6.11,left join 查出oldPosName ->end
		
		//状态权限
		List<Integer> putawayStateList = baseProductService.getPutawayList(loginUser);

		Long startTime = System.currentTimeMillis();
		if (designProductList != null && designProductList.size() > 0) {
			
			TreeSet<String> productTypeCodeSet = new TreeSet<String>();
			Map<String, UnityPlanProduct> unityPlanProductMap_p = new HashMap<String, UnityPlanProduct>();

			for (DesignPlanProduct planProduct : designProductList) {
				UnityPlanProduct unityPlanProduct = new UnityPlanProduct();
				unityPlanProduct.setIsDirty(planProduct.getIsDirty());
				unityPlanProduct.setPlanProductId(planProduct.getId());
				unityPlanProduct.setProductSequence(planProduct.getProductSequence());
				unityPlanProduct.setMaterialPicPaths(new String[]{});
				unityPlanProduct.setDecorationModelPath(new String[]{});
				unityPlanProduct.setPosIndexPath(planProduct.getPosIndexPath());
				unityPlanProduct.setPosName(planProduct.getPosName());
				unityPlanProduct.setBindProductId(planProduct.getBindParentProductId());
				
				/*天花、地面、地面结构拆分*/
				unityPlanProduct.setIsStandard(planProduct.getIsStandard());
				unityPlanProduct.setCenter(planProduct.getCenter());
				unityPlanProduct.setRegionMark(planProduct.getRegionMark());
				unityPlanProduct.setStyleId(planProduct.getStyleId());
				unityPlanProduct.setMeasureCode(planProduct.getMeasureCode());
				unityPlanProduct.setDescribeInfo(planProduct.getDescribeInfo());
				unityPlanProduct.setProductIndex(planProduct.getProductIndex());
				
				unityPlanProduct.setIsGroupReplaceWay(planProduct.getIsGroupReplaceWay());
				unityPlanProduct.setIsMainStructureProduct(planProduct.getIsMainStructureProduct());

				/*处理结构返回格式*/
				unityPlanProduct = this.getPlanProductStructureJson(unityPlanProduct, planProduct, designPlan, loginUser);

				/** 处理产品 橱柜 信息*/
				baseProductCountertopsService.setCountertopsDetails(unityPlanProduct,planProduct);

				// 产品的基本信息
				BaseProduct baseProduct = null;
				if (planProduct.getProductId() != null && planProduct.getProductId() > 0) {
					if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
						baseProduct = BaseProductCacher.get(planProduct.getProductId());
					} else {
						baseProduct = baseProductService.get(planProduct.getProductId());//产品公用基本信息   '产品品牌',  '产品风格', '产品规格',  '产品颜色',  '产品长度', '产品宽度', '产品高度', '销售价格',等等
					}
				}
				if (null == baseProduct) {
					msg = "planProduct.getProductId():" + planProduct.getProductId();
					return new ResponseEnvelope<UnityDesignPlan>(false, msg);
				}

				String productTypeValue = baseProduct.getProductTypeValue();
				Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
				SysDictionary sysDictionary = new SysDictionary();
				if (baseProduct != null && productSmallTypeValue != null
						&& StringUtils.isNotBlank(productTypeValue)) {
					//通过大小类获取小类信息
					Map<String, Object> map = new HashMap<>();
					map.put("smallTypeValue", productSmallTypeValue);
					map.put("typeValue", productTypeValue);
					sysDictionary = sysDictionaryService.selectSmallTypeObj(map);

					if (sysDictionary != null) {
						unityPlanProduct.setMoveWay(sysDictionary.getAtt5());
						if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getType())) {//墙面
							unityPlanProduct.setBgWall(Utils.getIsBgWall(sysDictionary == null ? "" : sysDictionary.getValuekey()));
						} else {
							unityPlanProduct.setBgWall(0);
						}
						//系列标志
						unityPlanProduct.setSeriesSign(baseProductService.getSeriesSign(sysDictionary.getValuekey()));
					} else {
						logger.debug("sDictionary is null, sd.getValuekey()=" + /*sysDictionary.getValuekey() */null
						+ ";baseProduct.getProductSmallTypeValue()=" + baseProduct.getProductSmallTypeValue() 
						+ ";productid=" + baseProduct.getProductId() + ";baseProduct.getProductTypeValue()=" 
						+ baseProduct.getProductTypeValue()+";planProduct.getProductId()="+planProduct.getProductId());
					}
					if (baseProduct.getBmIds() != null) {
						unityPlanProduct.setIsCustomized(1);
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
					//床沿尺寸
					if(null != baseProduct.getBedsideDimensions())
						unityPlanProduct.setBedsideDimensions(new Double(baseProduct.getBedsideDimensions()) / 100);

					//如果该墙面有绑定关系，则取绑定产品白模长宽高
					String bindProductid = planProduct.getBindParentProductId();
					if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary == null ? "" : sysDictionary.getType()) && StringUtils.isNotBlank(bindProductid)) {
						String array[] = bindProductid.split(",");
						BaseProduct baiMoProduct = null;
						StringBuffer fullPaveLength = new StringBuffer();

						for (String bindId : array) {
							if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
								baiMoProduct = BaseProductCacher.get(Utils.getIntValue(bindId));
							} else {
								baiMoProduct = baseProductService.get(Utils.getIntValue(bindId));
							}
							if (baiMoProduct != null && baiMoProduct.getFullPaveLength() != null) {
								fullPaveLength.append(baiMoProduct.getFullPaveLength() + ",");
							}
						}
						if (fullPaveLength.toString().length() > 0) {
							String fullPave = fullPaveLength.toString();
							unityPlanProduct.setFullPaveLength(fullPave != null ? fullPave.substring(0, fullPave.length() - 1) : fullPave);
						}
					}
					//如果是背景墙、窗帘、淋浴屏则取白模产品的长宽高
					Integer baiMoId = planProduct.getInitProductId();
					Map<String,String> stretchZoomMap = baseProductService.getStretchZoomLength(sysDictionary == null ? "" : sysDictionary.getValuekey());
					if (
							(stretchZoomMap != null && stretchZoomMap.size() > 0 && baiMoId != null && baiMoId.intValue() > 0)
							// add by huangsongbo 2018.7.10 ->start
							// 衣柜要带上对应白膜的全铺长度
							|| (sysDictionary != null && StringUtils.equals(ProductTypeConstant.PRODUCT_SMALL_TYPE_WACA, sysDictionary.getValuekey()))
							// add by huangsongbo 2018.7.10 ->end
							) {
						BaseProduct baiMoProduct = null;
						if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
							baiMoProduct = BaseProductCacher.get(baiMoId);
						} else {
							baiMoProduct = baseProductService.get(baiMoId);
						}
						if (baiMoProduct != null) {
							if (StringUtils.isNotEmpty(baiMoProduct.getProductLength())) {
								unityPlanProduct.setInitModelLength(Integer.parseInt(baiMoProduct.getProductLength()));
							}
							if (StringUtils.isNotEmpty(baiMoProduct.getProductWidth())) {
								unityPlanProduct.setInitModelWidth(Integer.parseInt(baiMoProduct.getProductWidth()));
							}
							if (StringUtils.isNotEmpty(baiMoProduct.getProductHeight())) {
								unityPlanProduct.setInitModelHeight(Integer.parseInt(baiMoProduct.getProductHeight()));
							}
							if (baiMoProduct.getFullPaveLength() != null) {
								unityPlanProduct.setFullPaveLength(baiMoProduct.getFullPaveLength());
							}
						}
					}
				}

				//logger.debug("baseProduct.json="+JSON.toJSONString(baseProduct));
				
				// 如果是天花,则模型路径传对应白膜的模型路径(为了适配户型绘制功能) update by huangsongbo 2018.3.26 ->start
				String u3dModelId = null;
				if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_VALUE_TIANH, baseProduct.getProductTypeValue())) {
					if(planProduct != null && planProduct.getInitProductId() != null) {
						BaseProduct baseProductBaimo = baseProductService.get(planProduct.getInitProductId());
						if(baseProductBaimo != null) {
							u3dModelId = baseProductService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), baseProductBaimo);
						}else {
							logger.error(logPrefix + "baseProductBaimo = null;productId = " + planProduct.getInitProductId());
						}
					}else {
						logger.error(logPrefix + "(planProduct != null && planProduct.getInitProductId() != null) = true");
					}
				}
				
				if(u3dModelId == null) {
					u3dModelId = baseProductService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), baseProduct);
				}
				// 如果是天花,则模型路径传对应白膜的模型路径(为了适配户型绘制功能) update by huangsongbo 2018.3.26 ->end
				
				ResModel resModel = resModelService.get(StringUtils.isEmpty(u3dModelId) ? 0 : new Integer(u3dModelId));
				if (resModel != null) {
					unityPlanProduct.setProductModelPath(resModel.getModelPath());
					unityPlanProduct.setModelLength(resModel.getLength());
					unityPlanProduct.setModelWidth(resModel.getWidth());
					unityPlanProduct.setModelHeight(resModel.getHeight());
					unityPlanProduct.setModelMinHeight(resModel.getMinHeight());
					unityPlanProduct.setModelCode(resModel.getModelCode());
				} else {
					//unityPlanProduct.setProductModelPath("");
					/*应对只有材质的硬装产品无模型的情况*/
					boolean isHard = false;
					if (baseProduct != null) {
						isHard = baseProductService.isHard(baseProduct);
					}
					if (isHard) {
						BaseProduct baimoProduct = null;
						Integer currentProductId = null;
						//换贴图应找当前产品模型
						if (planProduct.getModelProductId() != null && planProduct.getModelProductId() != 0) {
							currentProductId = planProduct.getModelProductId();
							unityPlanProduct.setModelProductId(planProduct.getModelProductId());
						} else {
							currentProductId = planProduct.getInitProductId();
						}

						if (Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)) {
							baimoProduct = BaseProductCacher.get(currentProductId);
						} else {
 							baimoProduct = baseProductService.get(currentProductId);
						}
						/*获取不同媒介u3d模型*/
						String modelId = baseProductService.getU3dModelId(mediaType,baimoProduct,modelType);
						if( StringUtils.isNotBlank(modelId) ){
							ResModel resModel1 = null ;
							if(Utils.enableRedisCache() && StringUtils.equals(Constants.NEED_CACHE, needOrNoCache)){
								resModel1 = ResourceCacher.getModel(Integer.valueOf(modelId));
							}else{
								resModel1 = resModelService.get(Integer.valueOf(modelId));
							}
							if( resModel1 != null ){
								unityPlanProduct.setProductModelPath(resModel1.getModelPath());
								unityPlanProduct.setModelCode(resModel1.getModelCode());
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
						ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));//材质库
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
					unityPlanProduct.setMaterialPicPaths((String[]) materialPicList.toArray(new String[materialPicList.size()]));
				}

				/* 产品子集数量*/
				unityPlanProduct.setLeafNum(0);
				/* 标示产品在界面中的展示类型*/
				unityPlanProduct.setIsLeaf(new Integer(1));
				/* 产品是否隐藏*/
				unityPlanProduct.setIsHide(planProduct.getIsHide());

				String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
				if (StringUtils.isNotBlank(splitTexturesInfo)) {
					//设值产品多维标识
					unityPlanProduct.setIsMultidimensional(1);
					if (StringUtils.isNotBlank(planProduct.getSplitTexturesChooseInfo())) {
						splitTexturesInfo = planProduct.getSplitTexturesChooseInfo();
					}

					if(null != baseProduct.getWindowsU3dModelId()){

						List<ProductDefaultUV> defaultUVList = designPlanMapper.selectProductDefaultUV(baseProduct.getWindowsU3dModelId());
						if(null != defaultUVList && defaultUVList.size() > 0){
							Map<Integer,ProductDefaultUV> productDefaultUVMap = defaultUVList.stream().collect(Collectors.toMap(ProductDefaultUV::getCode,o -> o));
							loginUser.setProductDefaultUVMap(productDefaultUVMap);
						}
					}

					Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(loginUser,modelType,baseProduct.getId(), splitTexturesInfo, "choose");
					unityPlanProduct.setIsSplit((Integer) map.get("isSplit"));
					unityPlanProduct.setSplitTexturesChoose((List<SplitTextureDTO>) map.get("splitTexturesChoose"));
				} else {
					String materialIds = baseProduct.getMaterialPicIds();
//					Integer materialId = 0;
					// 2018-11-16 edit by zhangwj TODO: 贴图产品可以有多张贴图
					List<String> materialIdStrList = null;
					if (StringUtils.isNotBlank(materialIds)) {
						materialIdStrList = Utils.getListFromStr(materialIds);
					}
					if (materialIdStrList != null && materialIdStrList.size() > 0) {
						List<SplitTextureDTO> splitTextureDTOList = new ArrayList<SplitTextureDTO>();
						SplitTextureDTO splitTextureDTO = null;
						for( String materialId : materialIdStrList ) {
							ResTexture resTexture = resTextureService.get(Integer.valueOf(materialId));
							if (resTexture != null) {
								List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
								splitTextureDTO = new SplitTextureDTO("1", "", null);
								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture);
								resTextureDTO.setKey(splitTextureDTO.getKey());
								resTextureDTO.setProductId(baseProduct.getId());
								resTextureDTOList.add(resTextureDTO);
								splitTextureDTO.setList(resTextureDTOList);
								splitTextureDTOList.add(splitTextureDTO);
							}
						}
						unityPlanProduct.setSplitTexturesChoose(splitTextureDTOList);
					}
				}

				/*处理拆分材质产品的默认材质信息->end*/
				UnityPlanProduct unityPlanProduct_p = new UnityPlanProduct();
				org.springframework.beans.BeanUtils.copyProperties(unityPlanProduct, unityPlanProduct_p);
				// 产品类别信息
				if (!StringUtils.isEmpty(productTypeValue)) {
					SysDictionary sd = sysDictionaryService.getSysDictionary("productType", new Integer(productTypeValue));
					if (sd != null) {
						/* 为保证父节点与子节点的productTypeCode相同，指定如下规则：
						子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
						父节点时，parentTypeCode存在值(暂时不取)，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值*/
						unityPlanProduct.setProductTypeValue(sd.getValue());
						unityPlanProduct.setProductTypeCode(sd.getValuekey());
						unityPlanProduct.setProductTypeName(sd.getName());

						unityPlanProduct_p.setProductTypeValue(sd.getValue());
						unityPlanProduct_p.setProductTypeCode(sd.getValuekey());
						unityPlanProduct_p.setProductTypeName(sd.getName());
						/* 获取子节点的父节点信息*/
						unityPlanProduct.setParentTypeCode(sd.getValuekey());
						unityPlanProduct.setParentTypeName(sd.getName());
						unityPlanProduct.setParentTypeValue(sd.getValue());

						unityPlanProduct_p.setParentTypeValue(-1);
						unityPlanProduct_p.setParentTypeCode("");
						unityPlanProduct_p.setParentTypeName("");

						/* 获取子节点的节点信息*/
						if (productSmallTypeValue != null && new Integer(productSmallTypeValue).intValue() > 0) {
							if (sysDictionary != null) {
								unityPlanProduct.setSmallTypeValue(sysDictionary.getValue());
								unityPlanProduct.setSmallTypeCode(sysDictionary.getValuekey());
								unityPlanProduct.setSmallTypeName(sysDictionary.getName());
								/* 是否是白模*/
								Integer isBm = 0;
								if ("baimo".equals(sysDictionary.getAtt3())) {
									isBm = 1;
									String bjType = Utils.getValue("app.smallProductType.beiJingWall", "");
									if( bjType.indexOf(sysDictionary.getValuekey()) != -1 ){
										unityPlanProduct.setIsHide(1);
									}
								}
								unityPlanProduct.setIsBaimo(isBm);
								unityPlanProduct_p.setIsBaimo(isBm);
								/* 软装硬装以下规则处理，同时按最小基本的数据定义-按1硬装2软装,默认软装*/
								String rootType = StringUtils.isEmpty(sysDictionary.getAtt1()) ? "2" : sysDictionary.getAtt1().trim();
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

				/* 存储产品分类集合,便于组装UI界面*/
				if (!StringUtils.isEmpty(unityPlanProduct.getProductTypeCode())) {
					productTypeCodeSet.add(unityPlanProduct.getProductTypeCode());
					// 默认使用第一条记录信息做数据代入*/
					if (!unityPlanProductMap_p.containsKey(unityPlanProduct.getProductTypeCode())) {
						/* 为保证父节点与子节点的productTypeCode相同，指定如下规则：
						子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
						 父节点时，parentTypeCode存在值，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值*/
						unityPlanProductMap_p.put(unityPlanProduct.getProductTypeCode(), unityPlanProduct_p);
					}
				} else {
					logger.debug("unityPlanProduct.getProductTypeCode() is null ;unityPlanProduct.getProductTypeCode()=" + unityPlanProduct.getProductTypeCode() + ";unityPlanProduct.getProductId=" + unityPlanProduct.getProductId() + ";unityPlanProduct.getProductCode=" + unityPlanProduct.getProductCode());
				}

				Map<String, String> map = new HashMap<String, String>();
				/*将材质的长宽也给  塞到这个list 中取 */
				String aterialPicIds = baseProduct.getMaterialPicIds();/**材质id**/
				if (aterialPicIds != null && !"".equals(aterialPicIds)) {
					if (StringUtils.isNumeric(aterialPicIds)) {
						ResTexture resTexture = resTextureService.get(Integer.parseInt(aterialPicIds));
						if (resTexture != null) {
							unityPlanProduct.setTextureWidth(resTexture.getFileWidth() + "");
							unityPlanProduct.setTextureHeight(resTexture.getFileHeight() + "");
						}
					}
				}
				/*在组合产品查询列表 中 增加产品属性*/
				map = productAttributeService.getPropertyMap(baseProduct.getId());//产品属性
				unityPlanProduct.setPropertyMap(map);

				// 关联白模产品的属性
				Map<String,String> basicPropertyMap = new HashMap<>();
				basicPropertyMap = productAttributeService.getPropertyMap(planProduct.getInitProductId());
				unityPlanProduct.setBasicPropertyMap(basicPropertyMap);

				//新逻辑客户端用到的天花数据存储到单独VO里
				this.getProductCeilingVO(unityPlanProduct, planProduct, baseProduct, putawayStateList);

				/* 样板房产品ID*/
				unityPlanProduct.setTemplateProductId(
						planProduct.getInitProductId() == null ? "" : planProduct.getInitProductId().toString());

				/* 组装产品的规则*/
				String productTypeCode = unityPlanProduct.getProductTypeCode();/* 产品大类*/
				String productSmallTypeCode = unityPlanProduct.getSmallTypeCode();/* 产品小类*/
				String productId = null;// 产品ID
				if (unityPlanProduct.getProductId() != null) {
					productId = unityPlanProduct.getProductId().toString();/*产品ID*/
				}

				/*获取规则*/
				Map<String, String> rulesMap = new HashMap<>();
				Map<Object, Object> rulesParamsMap = new HashMap<>();
				rulesParamsMap.put("rulesProductId", productId);
				ResponseEnvelope rulesResponseMapResult = null;
				if(Utils.enableRedisCache()){
					rulesResponseMapResult = CommonCacher.getAll(ModuleType.DesignPlan, "getRulesSecondaryList", rulesParamsMap);
				}
				
				if (rulesResponseMapResult != null) {
					rulesMap = (Map<String, String>) rulesResponseMapResult.getObj();
				} else {
					rulesMap = designRulesService.getRulesSecondaryList(productId, productTypeCode,
							productSmallTypeCode, spaceId, designPlan.getDesignTemplateId(), new DesignRules(), map);

				}
				unityPlanProduct.setRulesMap(rulesMap);
				unityPlanProductList.add(unityPlanProduct);
			}
			/*大循环的结束括号*/

			// 四.定制装修导航(在产品列表中该增加大按钮性质,同时,只有一个时,删除小按钮)
			// 组装每个产品的分类信息
			this.getDecorationNavigationInfo(unityPlanProductList, newUnityPlanProductList, productTypeCodeSet, unityPlanProductMap_p);
			
			// 地面通铺结构
			this.handlerStructure(designPlanId, spaceId, getValue(mediaType), unityDesignPlan);
		}
		//logger.error("获取设计方案产品信息消耗时间="+(System.currentTimeMillis() - startTime));
		ComparatorT cpmparator = new ComparatorT();
		Collections.sort(newUnityPlanProductList, cpmparator);
		unityDesignPlan.setDatalist(newUnityPlanProductList);
		
		// 取水刀配置信息 ->start
		List<WaterjetInfoReturnDTO> waterjetInfoReturnDTOList = this.getWaterjetInfoReturnDTOListByJson(designPlan.getWaterjetInfo());
		unityDesignPlan.setWaterjetInfoList(waterjetInfoReturnDTOList);
		// 取水刀配置信息 ->end
		
		ResponseEnvelope responseEnvelope = new  ResponseEnvelope<UnityDesignPlan>(true,unityDesignPlan);
		
		return responseEnvelope;
	}
	
	@Override
	public List<WaterjetInfoReturnDTO> getWaterjetInfoReturnDTOListByJson(String waterjetInfo) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(waterjetInfo)) {
			logger.error(logPrefix + "StringUtils.isEmpty(waterjetInfo) = true");
			return null;
		}
		// 参数验证 ->end
		
		/*List<WaterJetInfoDTO> waterJetInfoDTOList = JSONArray.fromObject(waterjetInfo);*/
		JSONArray jsonArray = JSONArray.fromObject(waterjetInfo);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("waterJetTextureInfoList", WaterJetTextureInfoDTO.class);
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<WaterJetInfoDTO> waterJetInfoDTOList = JSONArray.toList(jsonArray, WaterJetInfoDTO.class, classMap);
		
		
		return this.waterJetInfoDTOToWaterjetInfoReturnDTO(waterJetInfoDTOList);
	}

	/**
	 * bean:List<WaterJetInfoDTO> 转化为 bean:List<WaterjetInfoReturnDTO>
	 * 
	 * @author huangsongbo
	 * @param waterJetInfoDTOList
	 * @return
	 */
	private List<WaterjetInfoReturnDTO> waterJetInfoDTOToWaterjetInfoReturnDTO(
			List<WaterJetInfoDTO> waterJetInfoDTOList) {
		// 参数验证 ->start
		if(Lists.isEmpty(waterJetInfoDTOList)) {
			logger.error(logPrefix + "Lists.isEmpty(waterJetInfoDTOList) = true");
			return null;
		}
		// 参数验证 ->end
		
		List<WaterjetInfoReturnDTO> returnList = new ArrayList<WaterjetInfoReturnDTO>();
		for(WaterJetInfoDTO waterJetInfoDTO : waterJetInfoDTOList) {
			WaterjetInfoReturnDTO waterjetInfoReturnDTO = this.waterJetInfoDTOToWaterjetInfoReturnDTO(waterJetInfoDTO);
			returnList.add(waterjetInfoReturnDTO);
		}
		return returnList;
	}

	/**
	 * bean:WaterJetInfoDTO 转化为 bean:WaterjetInfoReturnDTO
	 * 
	 * @author huangsongbo
	 * @param waterJetInfoDTOList
	 * @return
	 */
	private WaterjetInfoReturnDTO waterJetInfoDTOToWaterjetInfoReturnDTO(WaterJetInfoDTO waterJetInfoDTO) {
		// 参数验证 ->start
		if(waterJetInfoDTO == null) {
			return null;
		}
		// 参数验证 ->end
		
		WaterjetInfoReturnDTO waterjetInfoReturnDTO = new WaterjetInfoReturnDTO();
		waterjetInfoReturnDTO.setPosX(waterJetInfoDTO.getPosX());
		waterjetInfoReturnDTO.setPosY(waterJetInfoDTO.getPosY());
		waterjetInfoReturnDTO.setPosZ(waterJetInfoDTO.getPosZ());
		waterjetInfoReturnDTO.setRotX(waterJetInfoDTO.getRotX());
		waterjetInfoReturnDTO.setRotY(waterJetInfoDTO.getRotY());
		waterjetInfoReturnDTO.setRotZ(waterJetInfoDTO.getRotZ());
		waterjetInfoReturnDTO.setScaleX(waterJetInfoDTO.getScaleX());
		waterjetInfoReturnDTO.setScaleY(waterJetInfoDTO.getScaleY());
		waterjetInfoReturnDTO.setScaleZ(waterJetInfoDTO.getScaleZ());
		waterjetInfoReturnDTO.setWaterjetTemplateId(waterJetInfoDTO.getWaterjetTemplateId());
		List<SplitTextureDTO> splitTexturesChooseList = this.waterJetTextureInfoDTOToSplitTextureDTO(waterJetInfoDTO.getWaterJetTextureInfoList());
		waterjetInfoReturnDTO.setSplitTexturesChoose(splitTexturesChooseList);

		// 模型|CAD文件路径 ->start
		if(waterJetInfoDTO.getWaterjetTemplateId() != null) {
			BaseWaterjetTemplate baseWaterjetTemplate = baseWaterjetTemplateService.getMoreInfoById(waterJetInfoDTO.getWaterjetTemplateId());
			if(baseWaterjetTemplate != null) {
				waterjetInfoReturnDTO.setWaterjetTemplateFileUrl(baseWaterjetTemplate.getTemplateFileUrl());
				waterjetInfoReturnDTO.setWaterCadPath(baseWaterjetTemplate.getWaterCadPath());
			}
		}
		// 模型|CAD文件路径 ->end
		
		return waterjetInfoReturnDTO;
	}

	private List<SplitTextureDTO> waterJetTextureInfoDTOToSplitTextureDTO(
			List<WaterJetTextureInfoDTO> waterJetTextureInfoList) {
		// 参数验证 ->start
		if(Lists.isEmpty(waterJetTextureInfoList)) {
			logger.error(logPrefix + "Lists.isEmpty(waterJetTextureInfoList) = true");
			return null;
		}
		// 参数验证 ->end
		
		List<SplitTextureDTO> splitTextureDTOList = new ArrayList<SplitTextureDTO>();
		for(WaterJetTextureInfoDTO waterJetTextureInfoDTO : waterJetTextureInfoList) {
			SplitTextureDTO splitTextureDTO = this.waterJetTextureInfoDTOToSplitTextureDTO(waterJetTextureInfoDTO);
			splitTextureDTOList.add(splitTextureDTO);
		}
		return splitTextureDTOList;
	}

	private SplitTextureDTO waterJetTextureInfoDTOToSplitTextureDTO(WaterJetTextureInfoDTO waterJetTextureInfoDTO) {
		// 参数验证 ->start
		if(waterJetTextureInfoDTO == null) {
			logger.error(logPrefix + "waterJetTextureInfoDTO = null");
			return null;
		}
		// 参数验证 ->end
		
		SplitTextureDTO splitTextureDTO = new SplitTextureDTO();
		splitTextureDTO.setKey(waterJetTextureInfoDTO.getKey());
		
		// List<SplitTextureDTO.ResTextureDTO> ->start
		if(waterJetTextureInfoDTO.getTextureId() != null) {
			List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
			ResTexture resTexture = resTextureService.get(waterJetTextureInfoDTO.getTextureId());
			if (resTexture != null && resTexture.getId() != null && resTexture.getId() > 0) {
				SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService.fromResTexture(null, resTexture, null);
				resTextureDTO.setKey(waterJetTextureInfoDTO.getKey());
				if( resTextureDTO.getTextureWidth() == 0 || resTextureDTO.getTextureWidth() == null ){
					resTextureDTO.setTextureWidth(80); //徐扬确认。如果材质长度为空/0则给默认80
				}
				if( resTextureDTO.getTextureHeight() == 0 || resTextureDTO.getTextureHeight() == null ){
					resTextureDTO.setTextureHeight(80); //徐扬确认。如果材质长度为空/0则给默认80
				}
				resTextureDTOList.add(resTextureDTO);
			}
			splitTextureDTO.setList(resTextureDTOList);
		}
		// List<SplitTextureDTO.ResTextureDTO> ->end
		
		return splitTextureDTO;
	}

	Integer getValue(Object arg) {
		return arg == null ? null : Integer.valueOf(arg.toString());
	}

	/**
	 * 获取截面天花数据
	 * @param unityPlanProduct
	 * @param planProduct
	 */
	private void getProductCeilingVO(UnityPlanProduct unityPlanProduct, DesignPlanProduct planProduct,
									 BaseProduct baseProduct, List<Integer> putawayStateList) {
		if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(unityPlanProduct.getProductTypeCode())) {
			ProductCeilingVO productCeilingVO = new ProductCeilingVO();
			//TODO 是截面天花直接获取本身产品信息，否则通过款式匹配，匹配不到给默认截面信息和款式
			if (ProductTypeConstant.PRODUCT_SMALL_TYPE_JIEM.equals(unityPlanProduct.getSmallTypeCode())) {
				List<ProductCeilingVO> productCeilingVOList = baseProductService.getCeilingInfoByProductCode(baseProduct.getProductCode());
				if (null != productCeilingVOList && 0 < productCeilingVOList.size()) {
					productCeilingVO = productCeilingVOList.get(0);
				}
			} else {
				//通过款式匹配
				int styCount = baseProductService.getProductStyleCountById(baseProduct.getStyleId());
				List<ProductCeilingVO> productCeilingVOList = null;
				if(styCount > 0){
					productCeilingVOList = baseProductService.getCeilingCrossSectionDataByStyleId(putawayStateList, baseProduct.getStyleId());
				}
				if (null != productCeilingVOList && 0 < productCeilingVOList.size()) {
					productCeilingVO = productCeilingVOList.get(0);
				} else {
					//查询默认的款式截面天花
					productCeilingVOList = baseProductService.getCeilingInfoByProductCode(ProductModelConstant.DEFAULT_JIEM_TIANH_PRODUCT_CODE);
					if (null != productCeilingVOList && 0 < productCeilingVOList.size()) {
						productCeilingVO = productCeilingVOList.get(0);
					}
				}
			}
			ProductCeilingVO productCeilingVOClone = null;
			try {
				productCeilingVOClone = productCeilingVO.clone();
			} catch (CloneNotSupportedException e) {
				logger.error(e);
				productCeilingVOClone = productCeilingVO;
			}
			productCeilingVOClone.setRegionMark(planProduct.getRegionMark());
			unityPlanProduct.setProductCeilingVO(productCeilingVOClone);
		}
	}
	
	/**
	 * 地面通铺结构，songjianming@sandusapce.com
	 * @param designPlanId
	 * @param spaceId
	 * @param mediaType
	 * @return
	 */
	List<SearchStructureProductResult> handlerStructure(Integer designPlanId, Integer spaceId, Integer mediaType, UnityDesignPlan unityDesignPlan) {
		List<SearchStructureProductResult> list = new ArrayList<SearchStructureProductResult>();
		Integer dataType = designPlanMapper.getDesignPlanSource(designPlanId);
		if (dataType == null || dataType != DesignPlanConstants.ORIGIN_DRAW) {
			// 原先是否为通铺结构   0 =（结构产品 -通铺结构）
			Integer count = designPlanMapper.countGroundStructure(designPlanId);
			Integer countProduct = designPlanMapper.countGroundStructureProduct(designPlanId);
			if (countProduct - count > 0) {
				logger.info("获取设计方案的地面通铺结构");
				List<StructureProduct> listStructure = structureProductMapper.listStructure(designPlanId);
				if (listStructure != null && !listStructure.isEmpty()) {
					for (StructureProduct product : listStructure) {
						SearchStructureProductResult result = handler(product, spaceId, mediaType);
						list.add(result);
					}
				}
			}
			
			// 数据类型 1、老数据
			unityDesignPlan.setDataType(1);
		} else {
			// 数据类型 1、老数据
			unityDesignPlan.setDataType(2);
		}
		
		unityDesignPlan.setGroundStructure(list);
		
		return list;
	}
    
	SearchStructureProductResult handler(StructureProduct structureProduct, Integer spaceId, Integer mediaType) {
		SearchStructureProductResult searchStructureProductResult = new SearchStructureProductResult(structureProduct.getId(),
				structureProduct.getStructureName(), structureProduct.getStructureCode(), "", "", null, "", "");

		/* 封面图片路径 */
		Integer picId = structureProduct.getPicId();
		if (picId != null && picId > 0) {
			ResPic resPic = resPicService.get(picId);
			if (resPic != null) {
				searchStructureProductResult.setPicPath(resPic.getPicPath());
			}
			Integer smallPicId = Utils.getSmallPicId(resPic, "ipad");
			if (smallPicId != null && smallPicId.intValue() > 0) {
				ResPic resSmallPic = resPicService.get(smallPicId);
				searchStructureProductResult.setSmallPicPath(resSmallPic == null ? "" : resSmallPic.getPicPath());
			}
		}

		Integer descPicPath = structureProduct.getDescPicId();
		if (descPicPath != null && descPicPath > 0) {
			ResPic resPic = resPicService.get(descPicPath);
			if (resPic != null) {
				searchStructureProductResult.setDescPicPath(resPic.getPicPath());
			}
		}
		searchStructureProductResult.setStructureDes(structureProduct.getRemark());

		/* 封面图片路径->end */
		/* config(从文件中取) */
		Integer fileId = structureProduct.getConfigFileId();
		if (fileId != null && fileId > 0) {
			ResFile resFile = resFileService.get(fileId);
			if (resFile != null) {
				// String url=Utils.getValue("app.upload.root", "")+resFile.getFilePath();
				// String config=FileUploadUtils.getFileContext(url);
				// searchStructureProductResult.setStructureConfig(config);
				String url = resFile.getFilePath();
				searchStructureProductResult.setFilePath(url);
			}
		}

		/* config(从文件中取)->end */
		/* 明细list */
		/* 查询明细 */
		List<SearchStructureProductDetailResult> searchStructureProductDetailResults = new ArrayList<SearchStructureProductDetailResult>();
		List<StructureProductDetails> structureProductDetailList = structureProductDetailsService.findAllByStructureId(structureProduct.getId());
		if (structureProductDetailList != null && structureProductDetailList.size() > 0) {
			for (StructureProductDetails structureProductDetail : structureProductDetailList) {
				Integer productId = structureProductDetail.getProductId();
				BaseProduct baseProduct = null;
				if (productId != null && productId > 0) {
					if (Utils.enableRedisCache())
						baseProduct = BaseProductCacher.get(productId);
					else
						baseProduct = baseProductService.get(productId);
				}
				if (baseProduct == null)
					continue;
				String productTypeValue = baseProduct.getProductTypeValue();
				if (StringUtils.isNotBlank(productTypeValue)) {
					SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(productTypeValue));
					baseProduct.setProductTypeMark(productTypeSysDic.getValuekey());
					Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
					if (productTypeSysDic.getValue() != null && productSmallTypeValue != null) {
						SysDictionary productSmallTypeSysDic = sysDictionaryService.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue);
						baseProduct.setProductSmallTypeMark(productSmallTypeSysDic.getValuekey());
					}
				}
				SearchStructureProductDetailResult searchStructureProductDetailResult = baseProductService.getStructureDetailsSearch(baseProduct, mediaType);
				searchStructureProductDetailResult.setCameraLook(structureProductDetail.getCameraLook());
				searchStructureProductDetailResult.setCameraView(structureProductDetail.getCameraView());
				searchStructureProductDetailResult.setProductStructureId(structureProduct.getId());
				searchStructureProductDetailResult.setStyleId(structureProductDetail.getStyleId());
				searchStructureProductDetailResult.setMeasureCode(structureProductDetail.getMeasureCode());
				searchStructureProductDetailResult.setRegionMark(structureProduct.getRegionMark());
				searchStructureProductDetailResult.setDescribeInfo(structureProductDetail.getDescribeInfo());
				searchStructureProductDetailResult.setTemplateProductId(productId);
				searchStructureProductDetailResult.setBasicModelType(baseProduct.getProductSmallTypeMark().replace("basic_", ""));
				/* rulesMap */
				Map<String, String> map = new HashMap<String, String>();
				map = productAttributeService.getPropertyMap(baseProduct.getId());
				baseProduct.setPropertyMap(map);
				searchStructureProductDetailResult.setStructureProductSign(map.get("structureSign"));
				Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(), baseProduct.getProductTypeMark(), baseProduct.getProductSmallTypeMark(), spaceId, null, new DesignRules(), map);
				searchStructureProductDetailResult.setRulesMap(rulesMap);
				searchStructureProductDetailResult.setPropertyMap(map);
				searchStructureProductDetailResult.setBasicPropertyMap(map);
				/* rulesMap->end */
				searchStructureProductDetailResults.add(searchStructureProductDetailResult);
			}
		}
		searchStructureProductResult.setStructureProductList(searchStructureProductDetailResults);
		/* 明细list->end */
		return searchStructureProductResult;
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

	//更新进入设计方案缓存数据
	public void updateDesignPlanCache(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName,HttpServletRequest request){

		UpdateDesignPlanCacheParameter parameter = new UpdateDesignPlanCacheParameter(designPlanId,houseId,livingId,residentialUnitsName,newFlag,request);
		UpdateDesignPlanCacheTask task = new UpdateDesignPlanCacheTask(parameter);
		FutureTask<Result> futureTask = new FutureTask<Result>(task);
		TaskExecutor.getInstance().addTask(futureTask);

	}

	/**
	 * 设计方案的样板房和产品上架验证
	 * @return
	 */
	public JSONObject shelvesValidate(DesignPlan designPlan ,LoginUser loginUser){
		JSONObject jsonObject = new JSONObject();
		int i = 0;
		try{
			jsonObject.accumulate("success",true);
			if( designPlan == null ){
				jsonObject.accumulate("message","没有在系统中找到设计方案！");
				logger.debug("没有在系统中找到设计方案！planId is null");
				return jsonObject;
			}
			// 验证空间是否发布
			Integer spaceCommonId = designPlan.getSpaceCommonId();
			if( spaceCommonId == null ){
				jsonObject.accumulate("message","找不到设计方案所属的空间！");
				logger.debug("找不到设计方案所属得空间！planId = " + designPlan.getId() + "; spaceCommonId is null!");
				return jsonObject;
			}
			SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
			if( spaceCommon == null ){
				jsonObject.accumulate("message","找不到设计方案所属的空间！");
				logger.debug("找不到设计方案所属得空间！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
				return jsonObject;
			}
			if( loginUser != null && loginUser.getUserType().intValue() == 1 ){
				if( spaceCommon.getStatus() != SpaceCommonStatus.IS_RELEASE.intValue() && spaceCommon.getStatus() != SpaceCommonStatus.IS_UP.intValue() && spaceCommon.getStatus() != SpaceCommonStatus.IS_TEST.intValue() ){
					jsonObject.accumulate("message","设计方案所属的空间没有发布！");
					logger.debug("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
					return jsonObject;
				}
			}else{
				if( spaceCommon.getStatus() != SpaceCommonStatus.IS_RELEASE.intValue() && spaceCommon.getStatus() != SpaceCommonStatus.IS_UP.intValue() ){
					jsonObject.accumulate("message","设计方案所属的空间没有发布！");
					logger.debug("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; spaceCommonId = " + spaceCommonId);
					return jsonObject;
				}
			}
			
			// 验证样板房是否上架
			Integer designTempletId = designPlan.getDesignTemplateId();
			if( designTempletId == null ){
				jsonObject.accumulate("message","找不到设计方案所属的样板房！");
				logger.debug("找不到设计方案所属的样板房！planId = " + designPlan.getId() + "; designTempletId is null!");
				return jsonObject;
			}
			DesignTemplet designTemplet = designTempletService.get(designTempletId);
			if( designTemplet == null ){
				jsonObject.accumulate("message","找不到设计方案所属的样板房！");
				logger.debug("设计方案所属的空间没有发布！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
				return jsonObject;
			}
			/*if( designTemplet.getPutawayState() != 1 && designTemplet.getPutawayState() != 2 ){
				jsonObject.accumulate("message","设计方案所属的样板房没有上架！");
				logger.debug("设计方案所属的样板房没有上架！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
				return jsonObject;
			}*/
		
			if( designTemplet.getPutawayState() != DesignTempletPutawayState.IS_UP.intValue()
					&& designTemplet.getPutawayState() != DesignTempletPutawayState.IS_TEST.intValue() 
					&& designTemplet.getPutawayState() != DesignTempletPutawayState.IS_RELEASE.intValue() ){
				jsonObject.accumulate("message","设计方案所属的样板房没有上架 或 测试 或 发布！");
				logger.debug("设计方案所属的样板房没有上架  或 测试 或 发布！planId = " + designPlan.getId() + "; designTempletId = " + designTempletId);
				return jsonObject;
			}
			
			// 验证设计方案产品是否上架
			DesignPlanProduct designPlanProductSearch = new DesignPlanProduct();
			designPlanProductSearch.setPlanId(designPlan.getId());
			designPlanProductSearch.setIsDeleted(0);
			List<DesignPlanProduct> designPlanProducts = designPlanProductService.getList(designPlanProductSearch);
			StringBuffer productMessage = new StringBuffer();
			List<String> productCodeList = new ArrayList<String>();
 
			if( designPlanProducts != null && designPlanProducts.size() > 0 ){
 
				for( DesignPlanProduct designPlanProduct : designPlanProducts ){
					Integer productId = designPlanProduct.getProductId();
					if( productId != null ){
						BaseProduct baseProduct = baseProductService.get(productId);
						if( baseProduct != null ){
							//内部用户才能看到 上架 测试 和 发布的，不然只能看到发布的
							if(loginUser!=null && loginUser.getUserType().intValue()==1){
								if(baseProduct.getPutawayState().intValue() == BaseProductPutawayState.IS_DOWN.intValue()||baseProduct.getPutawayState().intValue() == BaseProductPutawayState.NO_UP.intValue()){  /*内部用户 只要不是已下架都可以渲染*/
									productMessage.append("产品 ["+baseProduct.getProductCode()+"] 已下架,请替换或删除该产品!");
									productCodeList.add(baseProduct.getProductCode());
									logger.debug("产品 ["+baseProduct.getProductCode()+"] 已下架,请替换或删除该产品!");
									i = i + 1;
									break;
								}
							}else{
								if(baseProduct.getPutawayState().intValue() != BaseProductPutawayState.IS_RELEASE.intValue()){  /*外部用户 必须是已经发布才能渲染*/
									productMessage.append("产品 ["+baseProduct.getProductCode()+"] 已下架,请替换或删除该产品!");
									productCodeList.add(baseProduct.getProductCode());
									logger.debug("产品 ["+baseProduct.getProductCode()+"] 已下架,请替换或删除该产品");
								}
							}
						}
					}
				}
			}
			if(i==0){
				jsonObject.remove("success");
				jsonObject.accumulate("success",true);
				jsonObject.accumulate("message",productMessage.toString());
				jsonObject.accumulate("productCodeList",productCodeList);
				logger.debug(productMessage);
			}else{
				jsonObject.remove("success");
				jsonObject.accumulate("success",true);
				jsonObject.accumulate("message",productMessage.toString());
				jsonObject.accumulate("productCodeList",productCodeList);
				logger.debug(productMessage);
			}
		}catch (Exception e) {
			logger.error("shelvesValidate"+e);
			jsonObject.remove("success");
			jsonObject.accumulate("success",false);
		}
		return jsonObject;
	}

	public boolean copyPlan_new(DesignPlan plan, SysTask sysTask,HttpServletRequest request){
		Integer designPlanId = plan.getId();
		String creator = plan.getCreator();
		String modifier = plan.getModifier();
		DesignPlan desPlan = plan;
		desPlan.setId(null);
		if (plan.getPlanCode() != null) {
			desPlan.setPlanCode(plan.getPlanCode().substring(0, plan.getPlanCode().length() - 6)
					+ Utils.generateRandomDigitString(6));
		}
		desPlan.setDesignSourceType(9);//来源渲染copy
		desPlan.setDesignId(designPlanId);
		desPlan.setIsChange(0);
		desPlan.setIsOpen(0);
		desPlan.setSysCode(plan.getSysCode().substring(0, plan.getSysCode().length() - 6) + Utils.generateRandomDigitString(6));
		sysSave(desPlan, request);
		desPlan.setCreator(creator);
		desPlan.setModifier(modifier);
		int id = designPlanService.add(desPlan);
		if( id > 0 ){
			SysTask sys_task = sysTaskService.get(sysTask.getId());
			if(sys_task != null){
				SysTask newSysTask = new SysTask();
				newSysTask.setId(sys_task.getId());
				newSysTask.setPlanId(id);
				sysTaskService.update(newSysTask);
			}
		}
		if( designPlanId != null ){
			// 设计方案的产品列表代入
			DesignPlanProduct desPlanProduct = new DesignPlanProduct();
			desPlanProduct.setIsDeleted(0);
			desPlanProduct.setPlanId(designPlanId);
			List<DesignPlanProduct> planProductList = designPlanProductService.getList(desPlanProduct);
			for (DesignPlanProduct dpProduct : planProductList) {
				DesignPlanProduct planProduct = new DesignPlanProduct();
				planProduct=dpProduct;
				planProduct.setId(null);
				planProduct.setPlanId(id);
				sysSave(planProduct, request);
				designPlanProductService.add(planProduct);
			}
		}

		return true;
	}

	/**
	 *
	 * 设计方案详情获取渲染原图(原方案) add by yanghz
	 */
	@Override
	public DesignPlan getMyDesignDetailPic(DesignPlan designPlan) {

		Integer planId = designPlan.getId();
		ResHousePic resHousePic_=null;
		ResPic renderPic = null;

		//查询该设计方案的全部渲染原图列表
		List<ResRenderPic> picList = new ArrayList<>();
		ResRenderPic resRenderPic = new ResRenderPic();
		resRenderPic.setBusinessId(planId);
		resRenderPic.setIsDeleted(0);
		resRenderPic.setLimit(-1);
		resRenderPic.setOrder(" gmt_create desc ");
		List<String>fileKeys = new ArrayList<String>();
		fileKeys.add(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
		fileKeys.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
		resRenderPic.setFileKeyList(fileKeys);
		picList = resRenderPicService.getList(resRenderPic );


		List<RenderPicInfo> renderPicList = new ArrayList<RenderPicInfo>();
		/*封面摆在图片列表的第一位*/
		Integer coverPicId = designPlan.getCoverPicId();
		if(coverPicId!=null && coverPicId.intValue()>0){
			ResRenderPic coverPic = resRenderPicService.get(coverPicId);
			if(coverPic!=null && coverPic.getIsDeleted().intValue() == 0){
				renderPicList.add(new RenderPicInfo(coverPic.getPicPath(), coverPic.getRenderingType(),coverPic.getId(),""));
			}
		}

		/*有渲染图则优先取渲染原图，无渲染图则取俯瞰图*/
		if(picList != null && picList.size() > 0){/*取渲染原图*/
			for(ResRenderPic tempPic : picList){
				if(coverPicId!=null && coverPicId.intValue()>0){/*封面摆在图片列表的第一位，所以下面不需要重复  设置*/
					if(tempPic.getId().intValue() == coverPicId.intValue()){
						continue;
					}
				}
				ResRenderPic rp = null;
				if(tempPic.getSysTaskPicId() == null){
					logger.error("设计方案planId="+planId+"获取渲染图片列表接口：sysTaskPicId is null!");
				}else{
					rp = resRenderPicService.get(tempPic.getSysTaskPicId());
				}
				if(tempPic.getRenderingType() != null){
					if(RenderTypeCode.COMMON_720_LEVEL == tempPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == tempPic.getRenderingType()){
						if(rp == null){
							logger.error("设计方案planId="+planId+"获取渲染图片列表接口：截图信息为空!");
						}else{
							if(rp.getPicPath() == null){
								logger.error("设计方案planId="+planId+"获取渲染图片列表接口列表接口：图片id="+rp.getId()+"图片路径为空！");
							}else{
								renderPicList.add(new RenderPicInfo(rp.getPicPath(), tempPic.getRenderingType(),tempPic.getId(),""));
								designPlan.setPicType(1);
							}
						}
					}else{
						designPlan.setPicType(1);
						renderPicList.add(new RenderPicInfo(tempPic.getPicPath(), tempPic.getRenderingType(),tempPic.getId(),""));
					}
				}else{
					designPlan.setPicType(1);
					renderPicList.add(new RenderPicInfo(tempPic.getPicPath(), tempPic.getRenderingType(),tempPic.getId(),""));
				}
			}
			designPlan.setPicList(renderPicList);
		}else{//取顶视图
			DesignTemplet designTemplet = designTempletService.get(designPlan.getDesignTemplateId());/**显示样板房里的顶视图**/
			String smallPicObj = null;
			if(designTemplet!=null){
				String picId="";
				if(StringUtils.isNotBlank(designTemplet.getEffectPlanIds())){
					picId=designTemplet.getEffectPlanIds();
				}
				if(StringUtils.isNotBlank(picId)){
					if(StringUtils.isNumeric(picId)){
						renderPic=resPicService.get(Integer.parseInt(picId));
					}
					if(!StringUtils.isNumeric(picId)){
						String [] arr = picId.split(",");
						renderPic=resPicService.get(Integer.parseInt(arr[arr.length-1]));
					}

				}
				if(renderPic!=null){
					Map<String,String> map=readFileDesc(renderPic.getSmallPicInfo());
					String smallPicInfo = renderPic.getSmallPicInfo();/**样板房空间布局图-缩略图*/
					smallPicObj = map.get("ipad");
					if(smallPicObj != null ){
						ResPic smallPic = resPicService.get(Integer.valueOf(smallPicObj));
						designPlan.setPicPath(smallPic==null?"":smallPic.getPicPath());
						designPlan.setPicType(0);
					}else{
						designPlan.setPicPath(renderPic.getPicPath());
						smallPicObj = renderPic.getId()+"";
					}
					renderPicList.add(new RenderPicInfo(renderPic.getPicPath(), -1, renderPic.getId(),""));
					designPlan.setPicList(renderPicList);
				}

			}
			if(designTemplet==null||smallPicObj==null){/**渲染图，无渲染图显示空间布局图**/
				designPlan.setPicPath(resHousePic_==null?"":resHousePic_.getPicPath());
				designPlan.setPicType(0);
			}
		}
		return designPlan;
	}

	/**
	 *
	 * 设计方案详情获取渲染原图(副本) add by yanghz
	 */
	@Override
	public DesignPlanRenderScene getMyDesignSceneDetailPic(DesignPlanRenderScene planRenderScene) {
		
		Integer planId = planRenderScene.getId();
		ResHousePic resHousePic_=null;
		ResPic renderPic = null;

 
		List<ResRenderPic> picList = new ArrayList<>(); 
		ResRenderPic resRenderPic = new ResRenderPic();
//		resRenderPic.setBusinessId(planId);
		resRenderPic.setDesignSceneId(planId);
		resRenderPic.setIsDeleted(0);
		resRenderPic.setLimit(-1);
		resRenderPic.setOrder(" gmt_create desc ");
		/*resRenderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);*/
//		resRenderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
		List<String>fileKeys = new ArrayList<String>();
		fileKeys.add(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
		fileKeys.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
		resRenderPic.setFileKeyList(fileKeys);
		picList = resRenderPicService.getList(resRenderPic );
		logger.error(planId+"_picList_"+picList.size() );

		List<RenderPicInfo> renderPicList = new ArrayList<RenderPicInfo>();
		/*封面摆在图片列表的第一位*/
		Integer coverPicId = planRenderScene.getCoverPicId();
		if(coverPicId!=null && coverPicId.intValue()>0){
			ResRenderPic coverPic = resRenderPicService.get(coverPicId);
			if(coverPic!=null && coverPic.getIsDeleted().intValue() == 0){
				logger.error(planId+"add_");
				renderPicList.add(new RenderPicInfo(coverPic.getPicPath(), coverPic.getRenderingType(),coverPic.getId(),""));
			}
		}
		
		/*有渲染图则优先取渲染原图，无渲染图则取俯瞰图*/
		if(picList != null && picList.size() > 0){/*取渲染原图*/
			for(ResRenderPic tempPic : picList){
				logger.error(planId+"_tempPic_"+tempPic.getId() );
				if(coverPicId!=null && coverPicId.intValue()>0){/*封面摆在图片列表的第一位，所以下面不需要重复  设置*/
					if(tempPic.getId().intValue() == coverPicId.intValue()){
						continue;
					}
				}
				ResRenderPic rp = null;
				if(tempPic.getSysTaskPicId() == null || tempPic.getSysTaskPicId().intValue()<=0){
					logger.error("设计方案planId="+planId+"获取渲染图片列表接口：sysTaskPicId is null!");
				}else{
					rp = resRenderPicService.get(tempPic.getSysTaskPicId());
				}
				if(tempPic.getRenderingType() != null){
					if(RenderTypeCode.COMMON_720_LEVEL == tempPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == tempPic.getRenderingType()){
						if(rp == null){
							logger.error("设计方案planId="+planId+"获取渲染图片列表接口：截图信息为空!");
						}else{
							if(rp.getPicPath() == null){
								logger.error("设计方案planId="+planId+"获取渲染图片列表接口列表接口：图片id="+rp.getId()+"图片路径为空！");
							}else{
								logger.error(planId+"add_");
								renderPicList.add(new RenderPicInfo(rp.getPicPath(), tempPic.getRenderingType(),tempPic.getId(),""));
								planRenderScene.setPicType(1);
							}
						}
					}else{
						logger.error(planId+"add_");
						planRenderScene.setPicType(1);
						renderPicList.add(new RenderPicInfo(tempPic.getPicPath(), tempPic.getRenderingType(),tempPic.getId(),""));
					}
				}else{
					logger.error(planId+"add_");
					planRenderScene.setPicType(1);
					renderPicList.add(new RenderPicInfo(tempPic.getPicPath(), tempPic.getRenderingType(),tempPic.getId(),""));
				}
			}
			planRenderScene.setPicList(renderPicList);
		}else{//取顶视图
			DesignTemplet designTemplet = designTempletService.get(planRenderScene.getDesignTemplateId());/**显示样板房里的顶视图**/
			String smallPicObj = null;
			if(designTemplet!=null){
				String picId="";
				if(StringUtils.isNotBlank(designTemplet.getEffectPlanIds())){
					picId=designTemplet.getEffectPlanIds();
				}
				if(StringUtils.isNotBlank(picId)){
					if(StringUtils.isNumeric(picId)){
						renderPic=resPicService.get(Integer.parseInt(picId));
					}
					if(!StringUtils.isNumeric(picId)){
						String [] arr = picId.split(",");
						renderPic=resPicService.get(Integer.parseInt(arr[arr.length-1]));
					}

				}
				if(renderPic!=null){
					Map<String,String> map=readFileDesc(renderPic.getSmallPicInfo());
					String smallPicInfo = renderPic.getSmallPicInfo();/**样板房空间布局图-缩略图*/
					smallPicObj = map.get("ipad");
					if(smallPicObj != null ){
						ResPic smallPic = resPicService.get(Integer.valueOf(smallPicObj));
						planRenderScene.setPicPath(smallPic==null?"":smallPic.getPicPath());
						planRenderScene.setPicType(0);
					}else{
						planRenderScene.setPicPath(renderPic.getPicPath());
						smallPicObj = renderPic.getId()+"";
					}
					renderPicList.add(new RenderPicInfo(renderPic.getPicPath(), -1, renderPic.getId(),""));
					planRenderScene.setPicList(renderPicList);
				}

			}
			if(designTemplet==null||smallPicObj==null){/**渲染图，无渲染图显示空间布局图**/
				planRenderScene.setPicPath(resHousePic_==null?"":resHousePic_.getPicPath());
				planRenderScene.setPicType(0);
			}
		}
		return planRenderScene;
	}
	
	/**
	 * 取设计方缩略图列表 add by yanghz
	 */
	@Override
	public DesignPlan getMyDesignPic(DesignPlan desPlan) {

		Integer coverPicId = desPlan.getCoverPicId();//优先取封面图片
		if(coverPicId != null && coverPicId.intValue()>0){
			ResRenderPic coverPic = resRenderPicService.get(coverPicId);
			if(coverPic!=null){
				desPlan.setPicPath(coverPic.getPicPath());
				desPlan.setPicType(1);
				return desPlan;
			}
		}

		Integer planId = desPlan.getId();
		ResHousePic resHousePic_=null;
		ResPic renderPic = null;

		List<ResRenderPic> picList = new ArrayList<>(); //查询该设计方案的全部渲染缩略图列表
		ResRenderPicQO resRenderPicQO = new ResRenderPicQO();
		resRenderPicQO.setBusinessId(planId);
		resRenderPicQO.setIsDeleted(0);
		resRenderPicQO.setOrders(" id desc ");
		List<String>fileKeyLists = new ArrayList<String>();
		fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
		fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
		resRenderPicQO.setFileKeys(fileKeyLists);
		picList = resRenderPicService.selectListByFileKeys(resRenderPicQO);

		if(picList != null && picList.size() > 0){//有渲染图则优先取渲缩略图，无渲染图则取俯瞰图
			desPlan.setPicType(1);
			int id = 0;
			for (ResRenderPic resRenderPic : picList) {
				if(resRenderPic.getRenderingType() != null){
					if(id > resRenderPic.getId().intValue()){
						continue;
					}
					id = resRenderPic.getId().intValue();
					if(RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()){
						ResRenderPic smallRenderPic = null;
						if(resRenderPic.getSysTaskPicId() != null){
							smallRenderPic = resRenderPicService.get(resRenderPic.getSysTaskPicId());
						}
						if(smallRenderPic!=null){
							desPlan.setPicPath(smallRenderPic.getPicPath());
						}
						if(smallRenderPic == null){
							desPlan.setPicPath(resRenderPic.getPicPath());//如果截图信息也为空则显示原图
						}
					}else{
						desPlan.setPicPath(resRenderPic.getPicPath());
					}
				}else{
					desPlan.setPicPath(resRenderPic.getPicPath());
				}
			}
		}
		if(picList == null || picList.size()<=0){//取顶视图
			DesignTemplet designTemplet = designTempletService.get(desPlan.getDesignTemplateId());//显示样板房里的顶视图
			String smallPicObj = null;
			if(designTemplet!=null){
				String picId="";
				if(StringUtils.isNotBlank(designTemplet.getEffectPlanIds())){
					picId = designTemplet.getEffectPlanIds();
				}
				if(StringUtils.isNotBlank(picId)){
					if(StringUtils.isNumeric(picId)){
						renderPic=resPicService.get(Integer.parseInt(picId));
					}
					if(!StringUtils.isNumeric(picId)){
						String [] arr = picId.split(",");
						renderPic=resPicService.get(Integer.parseInt(arr[arr.length-1]));
					}
				}		
				if(renderPic!=null){
					desPlan.setPicType(0);
					Map<String,String> map = this.readFileDesc(renderPic.getSmallPicInfo());
					smallPicObj = map.get("ipad");//样板房空间布局图-缩略图
					if(smallPicObj != null ){
						ResPic smallPic = resPicService.get(Integer.valueOf(smallPicObj));
						desPlan.setPicPath(smallPic==null?"":smallPic.getPicPath());
					}else{
						desPlan.setPicPath(renderPic.getPicPath());
						smallPicObj = renderPic.getId()+"";
					}
				}
			}
			if(designTemplet==null||smallPicObj==null){//渲染图，无渲染图显示空间布局图
				desPlan.setPicPath(resHousePic_==null?"":resHousePic_.getPicPath());
				desPlan.setPicType(2);
			}
		}
		return desPlan;
	}
	
	/**解析固定格式字符串*/
	private Map<String,String> readFileDesc(String fileDesc){
		Map<String, String> map = new HashMap<String, String>();
		if( StringUtils.isNotBlank(fileDesc) ){
			String[] strs = fileDesc.split(";");
			for (String str : strs) {
				if (str.split(":").length == 2) {
					map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
				}
			}
		}
		return map;
	}
	
	// TODO:增加了一个“720全景图清晰度等级”的参数panoLevel          add by yangzhun 
	public Integer saveRenderPicOf720(DesignPlan designPlan,
			Map<String, MultipartFile> fileMap, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType,
			LoginUser loginUser, Integer taskId, Integer panoLevel,String roamJson, Integer sourcePlanId, Integer templateId) {
		/* 在app配置里看当前系统是否为 widows */
		String osType = FileUploadUtils.SYSTEM_FORMAT;
		/* 3dmax文件在渲染机上的目录 */
		String occu = Utils.getPropertyName("render",
				"design.designPlan.maxrender.designPlan.upload.path",
				"/MaxRender/");
		if ("windows".equals(osType)) {
			occu = occu.replace("/", "\\");
		}

		/* 应用根节点目录 */
		/* 渲染图存放相对路径 */
		String storePath = Utils.getPropertyName("config/res",
				"design.designPlan.render.upload.path",
				"/design/designPlan/render/").trim();
		storePath = Utils.replaceDate(storePath, null);
		/* 渲染图存放绝对路径 */
		storePath= ("linux".equals(FileUploadUtils.SYSTEM_FORMAT) ? storePath
						: storePath.replace("/", "\\"));
		/*String uploadRoot = Tools.getRootPath(storePath,"D:\\nork\\Resource");*/
		/*String storeRealPath = uploadRoot+storePath;*/
		String storeRealPath = Utils.getAbsolutePath(storePath, null);

		Integer resPicId = 0;
		Integer resScreenPicId = null;// 小图回填父id
		Integer smallPicId = null;// 缩略图id
		Integer renderRoamId = null;// 漫游ID。design_render_roam表ID
		ResRenderPic resRenderPic = new ResRenderPic();
		Date date = new Date();
		// 720漫游使用（多720图片上传）
		JSONArray roamArray  = new JSONArray();
		Map<String,JSONObject> roamMap = null;
		if(  !StringUtils.isEmpty(roamJson)
				&& RenderTypeCode.ROAM_720_LEVEL == renderingType){
			// 720漫游使用（多720图片上传）
		    roamArray  = JSONArray.fromObject(roamJson);
		    roamMap= getRoamMap(roamArray);
			roamArray = new JSONArray();
		}
		Integer roamMainPicId = null;// 多点漫游，主场景720图片ID
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			String filedName = mf.getName();
			logger.error("上传文件名称fileName = " + mf.getName() + "   --"
					+ mf.getOriginalFilename());
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String fName = fileName.substring(0, fileName.indexOf("."));

			/* 解决[code]没有替换问题 */
			storePath = storePath.replace("[code]", designPlan.getPlanCode());
			storeRealPath = Utils.replaceDate(storeRealPath.replace("[code]",
					designPlan.getPlanCode()));
			File f = new File(storeRealPath);
			if (!f.exists()) {
				f.mkdirs();
			}
			String bigFileName = designPlan.getPlanCode() + "_"
					+ Utils.generateRandomDigitString(6) + suffix;
			logger.error("图片路径================" + bigFileName);
			String serverFilePath = "";
			try {
				if ("renderSmallPicOf720".equals(filedName)) {// 保存截图
					Map map = new HashMap();
					if( RenderTypeCode.ROAM_720_LEVEL == renderingType.intValue() ){
						map.put(FileUploadUtils.UPLOADPATHTKEY,
								ResProperties.DESIGNPLAN_RENDER_ROAM_SCREEN_PIC_FILEKEY);
					}else {
						map.put(FileUploadUtils.UPLOADPATHTKEY,
								"design.designPlan.render.upload.path");
					}
					boolean flag = false;
					map.put("code", designPlan.getPlanCode());
					map.put(FileUploadUtils.FILE, mf);
					flag = FileUploadUtils.saveFile(map);
					// 720度渲染生成水印图 ->start
					if (RenderTypeCode.COMMON_720_LEVEL == renderingType.intValue() || RenderTypeCode.ROAM_720_LEVEL == renderingType.intValue()) {
						// if(new Integer(4).equals(renderingType)){
						serverFilePath = map.get(
								FileUploadUtils.SERVER_FILE_PATH).toString();
						try {
							ImageUtils.watermarking2(serverFilePath, scene, 2,
									isTurnOn);
						} catch (IOException e) {
							logger.error("水印图生成失败:" + e);
						}
					}
					// 720度渲染生成水印图 ->end
					if (flag) {
						ResRenderPic resPic = new ResRenderPic();
						resPic = assembleResRenderPic(map);
						resPic.setPicPath(map.get("dbFilePath") + "");
						resPic.setPicFormat(map.get("format") + "");
						resPic.setPicCode(map.get("code") + "");
						resPic.setPicWeight(Integer.parseInt(map
								.get("picWeight") + ""));
						resPic.setFileKey(map.get("fileKey") + "");
						resPic.setPicName(map.get("fileName") + "");
						resPic.setPicHigh(Integer.parseInt(""
								+ map.get("picHeight")));
						resPic.setPicType("渲染截图");
						resPic.setRenderingType(renderingType);
						resPic.setSysCode(Utils
								.getCurrentDateTime(Utils.DATETIMESSS)
								+ "_"
								+ Utils.generateRandomDigitString(6));
						resPic.setGmtCreate(new Date());
						resPic.setCreator(loginUser.getLoginName());
						resPic.setGmtModified(resPic.getGmtCreate());
						resPic.setModifier(resPic.getCreator());
						resPic.setIsDeleted(0);
						resPic.setBusinessId(designPlan.getId());
						resPic.setRenderingType(renderingType);
						resPic.setTaskCreateTime(date);// 任务创建时间
						resPic.setViewPoint(viewPoint);
						resPic.setScene(scene);
						resPic.setPanoLevel(panoLevel);
						resPic.setSourcePlanId(sourcePlanId);
						resPic.setTemplateId(templateId);
						resRenderPic.setCreator(loginUser.getLoginName());
						resRenderPic.setModifier(loginUser.getLoginName());
						resScreenPicId = resRenderPicService.add(resPic);
						if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){//720 漫游的截图id和SysTaskPicId 一样
							resPicId = resScreenPicId;
							// 保存720漫游记录
							DesignRenderRoam designRenderRoam = new DesignRenderRoam();
							designRenderRoam.setCreator(loginUser.getLoginName());
							designRenderRoam.setGmtCreate(new Date());
							designRenderRoam.setModifier(loginUser.getLoginName());
							designRenderRoam.setGmtModified(designRenderRoam.getGmtCreate());
							designRenderRoam.setIsDeleted(0);
							designRenderRoam.setScreenShotId(resScreenPicId);
							designRenderRoam.setUuid(Utils.getUUID());
							renderRoamId = designRenderRoamService.add(designRenderRoam);
						}
					} else {
						logger.error("方案id:" + designPlan.getId()
								+ "生成720水印图失败");
					}
					if (resPicId != null && resPicId > 0) {
						ResRenderPic rrp = new ResRenderPic();
						rrp.setId(resPicId);
						rrp.setSysTaskPicId(resScreenPicId);
						resRenderPicService.update(rrp);
					} else {

					}
				} else {// 保存曲线图
					// 如果为720漫游
					if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){
						resPicId = saveRoam720(resRenderPic,storePath,storeRealPath,mf,bigFileName,viewPoint,scene,renderingType,loginUser,designPlan,panoLevel,resScreenPicId,date);
						JSONObject roamObj = roamMap.get(filedName);
						if( roamObj.getInt("index") == 1 ){// 默认把第一张图片作为主场景
							roamMainPicId = resPicId;
						}
						roamObj.remove("fieldName");
						roamObj.accumulate("fieldName",resPicId);
						roamArray.add(roamObj);
					}else {
						/* 生成渲染图片 */
						FileUtils.copyInputStreamToFile(mf.getInputStream(),
								new File(storeRealPath, bigFileName));

						/* 保存渲染图片 */
						File renderPic = new File(storeRealPath + bigFileName);
						Map map = FileUploadUtils.getMap(renderPic,
								ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY, false);
						resRenderPic = assembleResRenderPic(map);
						resRenderPic.setIsDeleted(0);
						resRenderPic.setGmtCreate(new Date());
						resRenderPic.setViewPoint(viewPoint);
						resRenderPic.setScene(scene);
						resRenderPic.setRenderingType(renderingType);
						resRenderPic.setCreator(loginUser.getLoginName());
						resRenderPic.setGmtModified(new Date());
						resRenderPic.setModifier(loginUser.getLoginName());
						resRenderPic.setSysCode(Utils
								.getCurrentDateTime(Utils.DATETIMESSS)
								+ "_"
								+ Utils.generateRandomDigitString(6));
						resRenderPic.setBusinessId(designPlan.getId());
						resRenderPic.setPicType("3DMax渲染原图");
						resRenderPic.setSourcePlanId(sourcePlanId);
						resRenderPic.setTemplateId(templateId);
						resRenderPic.setTaskCreateTime(date);// 任务创建时间
						//TODO： 720全景图清晰度等级
						resRenderPic.setPanoLevel(panoLevel);
						if (resScreenPicId != null && resScreenPicId > 0) {
							resRenderPic.setSysTaskPicId(resScreenPicId);// 关联截图信息
							resPicId = resRenderPicService.add(resRenderPic);// 保存曲线图
						} else {
							resPicId = resRenderPicService.add(resRenderPic);// 保存曲线图
						}
					}
				}

				if (resPicId > 0) {

				} else {
					logger.error("保存渲染图失败,删除已存入的图片信息resPicId=" + resPicId);
					/* 删除目录 */
					logger.warn(Utils.getLineNumber() + "删除目录：");
					/* 删除图片 */
					if (f.exists()) {
						f.delete();
					}
					return resScreenPicId;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				if (f.exists()) {
					f.delete();
				}
				return resScreenPicId;
			}
			// 生成缩略图
			if ("renderSmallPicOf720".equals(filedName)
					&& resScreenPicId != null && resScreenPicId > 0) {// app端传过来的截图才需要生成缩略图。
				try {
					/* 生成缩略图 */
					String smallFileName = Utils.generateRandomDigitString(6)
							+ "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
							+ fileName.substring(fileName.indexOf("."));

					String targetSmallFilePath = Utils.replaceDate(storeRealPath) + "small/"
							+ smallFileName;
					try {
						ResizeImage
								.createThumbnail(
										serverFilePath,
										("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) ? targetSmallFilePath
												: targetSmallFilePath
												.replaceAll("/", "\\\\"),
										280, 215);
					} catch (IOException e) {
						e.printStackTrace();
					}
					/* 保存缩略图 */
					File targetSmallFile = new File(targetSmallFilePath);
					if (!targetSmallFile.exists()) {
						logger.error("路径targetSmallFilePath："
								+ targetSmallFilePath + "不存在");
					}
					Map smallFileMap = FileUploadUtils.getMap(targetSmallFile,//缩略图file_key此时根本不是DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY
							ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY,
							false);
					ResRenderPic smallRenderResPic = assembleResRenderPic(smallFileMap);
					smallRenderResPic.setIsDeleted(0);
					smallRenderResPic.setGmtCreate(new Date());
					smallRenderResPic.setPicPath(smallFileMap.get(FileModel.FILE_PATH).toString());
					smallRenderResPic.setCreator(loginUser.getLoginName());
					smallRenderResPic.setGmtModified(new Date());
					smallRenderResPic.setRenderingType(renderingType);
					smallRenderResPic.setModifier(loginUser.getLoginName());
					smallRenderResPic.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
					smallRenderResPic.setBusinessId(designPlan.getId());
					smallRenderResPic.setPicType("3DMax渲染图-缩略图");
					smallRenderResPic.setViewPoint(viewPoint);
					smallRenderResPic.setScene(scene);
					smallRenderResPic.setPid(resPicId);
					smallRenderResPic.setTaskCreateTime(date);// 任务创建时间
					smallRenderResPic.setSourcePlanId(sourcePlanId);
					smallRenderResPic.setTemplateId(templateId);
					//TODO： 720全景图清晰度等级
					smallRenderResPic.setPanoLevel(panoLevel);
					if (resScreenPicId != null && resScreenPicId > 0) {
						smallRenderResPic.setSysTaskPicId(resScreenPicId);
						smallPicId = resRenderPicService.add(smallRenderResPic);
					} else {
						smallPicId = resRenderPicService.add(smallRenderResPic);
					}

					if (smallPicId != null) {

					} else {
						logger.error("保存渲染缩略图异常,删除已存入的图片信息smallResPicId="
								+ smallPicId);
						/* 删除目录 */
						logger.warn(Utils.getLineNumber() + "删除目录：");
						/* 删除图片 */
						if (f.exists()) {
							f.delete();
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
					if (f.exists()) {
						f.delete();
					}
					return resScreenPicId;
				}
			}
			/* 创建浏览器查看全景图 */
			/*if ("renderPicOf720".equals(filedName) && resPicId != null
					&& resPicId > 0) {// 曲线图才需要生成全景图
				this.generatePanoramaPic(resRenderPic, designPlan, smallPicId,
						resScreenPicId);
			}*/
		}
		if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){
			// 把多张720图片的关联关系存到漫游表中
			/*ResRenderPic roamRenderPic = new ResRenderPic();
			roamRenderPic.setId(roamMainPicId);
			roamRenderPic.setRoam(roamArray.toString());
			resRenderPicService.update(roamRenderPic);*/
			DesignRenderRoam designRenderRoam = new DesignRenderRoam();
			designRenderRoam.setId(renderRoamId);
			// 关联关系保存到文件中
			int resFileId = saveRenderRoamConfig(loginUser,roamArray.toString(),renderRoamId);
			designRenderRoam.setRoamConfig(resFileId);
			designRenderRoamService.update(designRenderRoam);
			return roamMainPicId;
		}
		return resScreenPicId;
	}
	
	/**
	 * 生成全景图
	 * add by yanghz
	 */
	public Boolean generatePanoramaPic(ResRenderPic resRenderPic,DesignPlan designPlan,Integer smallResPicId,Integer resScreenPicId){
		/*创建浏览器查看全景图*/
		if( RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resRenderPic.getRenderingType()){
			logger.info("============创建浏览器查看全景图============");
			GeneratePanoramaExecutor panoramaExecutor = GeneratePanoramaExecutor.getInstance();
			GeneratePanoramaTask panoramaTask = null;
			try {
				panoramaTask = new GeneratePanoramaTask(designPlan.getId(),
						designPlan.getId(), designPlan.getPlanCode() + "_"
						+ Utils.getCurrentDateTime(Utils.DATETIMESSS),
						resScreenPicId,
						/*Constants.UPLOAD_ROOT + resRenderPic.getPicPath(),*/
						Utils.getAbsolutePath(resRenderPic.getPicPath(), null),
						resRenderPic.getId(), smallResPicId,resRenderPic.getPanoLevel());
			} catch (GeneratePanoramaException e) {
				e.printStackTrace();
			}
			panoramaExecutor.addTask(panoramaTask);
		}else{
		}
		return true;
	}

	public DesignPlan createDesignPlan(DesignPlan designPlan,DesignPlanRecommended designPlanRecommended,DesignTemplet designTemplet,
									   String mediaType,LoginUser loginUser, Integer isDeleted, Integer opType){
		// 参数验证/参数处理 ->start
		if(isDeleted == null) {
			isDeleted = 0;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证/参数处理 ->end
		
		designPlan.setMediaType(Utils.getIntValue(mediaType));
		designPlan.setPlanCode(designTemplet.getDesignCode() + "_" + Utils.generateRandomDigitString(6));
		designPlan.setPlanName(designTemplet.getDesignName());
		designPlan.setUserId(loginUser == null ? -1 : loginUser.getId());
		designPlan.setDesignSourceType(7);
		designPlan.setDesignId(designTemplet.getId());
		designPlan.setPicId(designTemplet.getPicId());
		designPlan.setSysCode(designTemplet.getDesignCode()+ "_" + Utils.generateRandomDigitString(6));
		designPlan.setDesignTemplateId(designTemplet.getId());/* 样板房ID*/
		designPlan.setSpaceCommonId(designTemplet.getSpaceCommonId());/* 适用的空间ID*/
		designPlan.setDesignStyleId(null);
 
		//存储设计方案业务来源 
		designPlan.setBusinessType(DesignPlanBusinessTypeConstant.TYPE_INTELLIGENCE);
 
		
		if(designPlanRecommended != null) {
			//拼花
			designPlan.setSpellingFlowerProduct(designPlanRecommended.getSpellingFlowerProduct());
			/*拷贝效果图拼花配置文件*/
			if (designPlanRecommended.getSpellingFlowerFileId() != null) {
				Integer resFileId = this.planCopyFileFromResDesignScene(designPlanRecommended.getSpellingFlowerFileId().toString(), 
					"design.designPlan.spellingFlowerFile","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/",
					null, designPlanRecommended.getPlanCode(),loginUser);
				designPlan.setSpellingFlowerFileId(resFileId);
			} else {
				designPlan.setSpellingFlowerFileId(-1);
			}
		}
 
		/* 公共空间拷贝//u3d模型直接引用样板房的模型*/
		String modelId = this.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), designTemplet);
		if (StringUtils.isNotBlank(modelId)) {
			designPlan.setModelId(Utils.getIntValue(modelId));
		}else{
			designPlan.setModelId(-1);
		}

		designPlan.setIsOpen(0);
		sysSave(designPlan, loginUser);
		designPlan.setIsDeleted(isDeleted);
		Integer designPlanId = null;
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			designPlanId = designPlanService.add(designPlan);
		}else {
			// 自动渲染,则把设计方案产品数据存在自动渲染产品表里
			designPlanId = optimizePlanService.add(designPlan);
		}
		logger.info("add:id=" + designPlanId);
		designPlan.setId(designPlanId);
		if( designPlanId > 0 ){
			return designPlan;
		}else{
			return null;
		}
	}
	
	@Override
	public Integer saveRenderPicOfPhoto(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType,String level, LoginUser loginUser,Integer taskId,Integer sourcePlanId, Integer templateId) {
		String fileIds = "";
		String fileName = null;
		String code="code";
		if(designPlan != null){
			code=designPlan.getPlanCode();
		}
		List<Map> list = new ArrayList<Map>();
		Integer renderSuccess = 0;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			logger.debug("文件上传名称：" + fileName);
//			response.setContentType("text/html;charset=utf-8");
			// 获取文件列表并将物理文件保存到服务器中

			// filePath设置到model对象中，由model存入数据库中
			Map map = new HashMap();
			map.put(FileUploadUtils.UPLOADPATHTKEY,"design.designPlan.render.upload.path");
			boolean flag = false;
			map.put("code",code);
			map.put(FileUploadUtils.FILE, mf);
			flag = FileUploadUtils.saveFile(map);
			String serverFilePath = map.get(FileUploadUtils.SERVER_FILE_PATH).toString();
			logger.error("追加渲染图水印,serverFilePath=" + serverFilePath);
			try {
				ImageUtils.watermarking2(serverFilePath, scene, 2, isTurnOn);
			} catch (IOException e) {
				logger.error("水印图生成失败");
				e.printStackTrace();
			}
			// 生成图片,支持多张,需写到server
			if (flag) {
				/**TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。**/
				Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
				String finalFlieName = (String) map.get("finalFlieName");
				String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
				String ftpFilePath =(String) map.get("filePath");
				//上传方式为2或者3都需要上传到ftp
				boolean uploadFtpFlag = false;
				if( ftpUploadMethod == 2 || ftpUploadMethod == 3 ){
					uploadFtpFlag = FtpUploadUtils.uploadFile(finalFlieName,serverFilePath,ftpFilePath);
				}
				// 生成缩略图
				try {
					String smallFileName = Utils
							.generateRandomDigitString(6)
							+ "_"
							+ Utils.getCurrentDateTime(Utils.DATETIMESSS)
							+ fileName.substring(fileName.indexOf("."));
					String storePath = Utils.getPropertyName("config/res", map.get(FileUploadUtils.UPLOADPATHTKEY)+"", "/design/designPlan/[code]/render/").replace("[code]", code)
                    + "/small/" + smallFileName;
					storePath = Utils.replaceDate(storePath);
					/*String targetSmallFilePath = Tools.getRootPath(storePath,"D:\\app") + storePath;*/
					String targetSmallFilePath = Utils.getAbsolutePath(storePath, null);
					if ("windows".equals(Utils.getValue("app.system.format", "linux").trim())) {
						targetSmallFilePath = targetSmallFilePath.replace("/", "\\");
					}
					ResizeImage.createThumbnail(serverFilePath,
							targetSmallFilePath, 280, 215);
					File targetSmallFile = new File(targetSmallFilePath);
					Map smallFileMap = FileUploadUtils.getMap(
							targetSmallFile,
							"design.designPlan.render.upload.path", false);
					map.put("viewPoint", viewPoint);
					map.put("scene",scene);

					smallFileMap.put("original", map);
					/*传递渲染图的视角和渲染图场景*/
					smallFileMap.put("viewPoint", viewPoint);
					smallFileMap.put("scene",scene);
					list.add(smallFileMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//如果上传方式为2，则删除掉临时上传的文件
				if( ftpUploadMethod == 2 ){
					if( uploadFtpFlag ){
						//删除本地
						FileUploadUtils.deleteFile(dbFilePath);
					}
				}
			}
		}
		// 将图片信息记录到数据库中
		logger.info("designPlan.getId()="+designPlan.getId());
		renderSuccess = resPicService.savePlanRenderPicOfPhot(designPlan.getId(), list, level,renderingType,sourcePlanId,templateId);
		return renderSuccess;
	}
	
	public List<ProductDTO> getProductDTOList(Integer designPlanId) {
		return designPlanMapper.getProductDTOList(designPlanId);
	}

	@Override
	public List<DesignPlan> getMyDesignPlanList(LoginUser loginUser, DesignPlan designPlan, int total, String mediaType) {
		designPlan = getSearchDesignPlan(loginUser, designPlan, mediaType);
		List<DesignPlan> list = new ArrayList<DesignPlan>();
		/* 判断是否拥有设计方案拷贝 和 发布的权限 */
		boolean flag = designPlanRecommendedService.isPermissions(loginUser);
		if (total > 0) {
			list = designPlanService.getPlanList(designPlan);
			for (DesignPlan desPlan : list) {
			        /**
			         * 一键生成方案名称显示，如果为内部用户显示括号内的内容，外部用户不显示
			         * */
			        if(!UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType())){ 
			            if (!StringUtils.isBlank(desPlan.getPlanName())) {
        			       String name = desPlan.getPlanName().split("\\(")[0];
        			       desPlan.setPlanName(name);
			            }
			        }
				/* 此设计方案的白膜产品多余0 那么就是未装修完成，小于0就是完成 */
				int DesignPlanState = designPlanService.getDesignPlanState(desPlan.getId());
				if (DesignPlanState == 0) {/* '是否装修完成状态 (1.未装修完成 2.已装修完成)', **/
				    desPlan.setDesignPlanState("2");
				} else {
				    desPlan.setDesignPlanState("1");
				}
				/* 发布权限 和 复制权限 暂时定位内部用户能使用 */
				if (flag) {
					desPlan.setCopyPermissions(1);
					desPlan.setReleasePermissions(1);
				} else {
					desPlan.setCopyPermissions(0);
					desPlan.setReleasePermissions(0);
				}
				SysUser user = null;
				if (Utils.enableRedisCache()) {
					user = SysUserCacher.get(desPlan.getUserId());
				} else {
					user = sysUserService.get(desPlan.getUserId());
				}
				/* 填充用户名 */
				if (user != null) {
					desPlan.setPlanUserName(user.getUserName());
				}
				SpaceCommon spaceCommon = null;
				if (desPlan.getSpaceCommonId() != null) {
					if (Utils.enableRedisCache()) {
						spaceCommon = SpaceCommonCacher.get(desPlan.getSpaceCommonId());
					} else {
						spaceCommon = spaceCommonService.get(desPlan.getSpaceCommonId());
					}
					Integer spaceFunctionId = spaceCommon.getSpaceFunctionId();
					String spaceAreas = spaceCommon.getSpaceAreas();
					SysDictionary houseTypeSysDictionary = sysDictionaryService.getSysDictionaryByValue("houseType",
							spaceFunctionId);
					SysDictionary spaceAreasSysDictionary = sysDictionaryService.getSysDictionaryByValue(
							houseTypeSysDictionary.getValuekey(), Integer.parseInt(spaceAreas));
					if (spaceCommon != null) {
						desPlan.setSpaceCode(spaceCommon.getSpaceCode());
						desPlan.setSpaceName(spaceCommon.getSpaceName());
						desPlan.setSpaceAreas(spaceAreasSysDictionary.getName());
						desPlan.setMainLength(spaceCommon.getMainLength());
						desPlan.setMainWidth(spaceCommon.getMainWidth());
					}
				}
				/* 空间数量 */
				DesignPlanCommentSearch commentSearch = new DesignPlanCommentSearch();
				commentSearch.setDesignPlanId(desPlan.getId());
				int count = 0;
				if (Utils.enableRedisCache()) {
					count = DesignPlanCommentCacher.getTotalWithParameter(commentSearch);
				} else {
					count = designPlanCommentService.getCount(commentSearch);
				}
				desPlan.setCommentCount(count);
				/* 点赞数量 */
				DesignPlanLikeSearch search = new DesignPlanLikeSearch();
				search.setDesignId(desPlan.getId());
				int likeCount = 0;
				if (Utils.enableRedisCache()) {
					likeCount = DesignPlanLikeCacher.getTotalWithParameter(search);
				} else {
					likeCount = designPlanLikeService.getCount(search);
				}
				desPlan.setLikeCount(likeCount);
				/* 获取渲染图列表(如果有渲染图则优先取渲染图，无则取俯瞰图) */
				desPlan = designPlanService.getMyDesignPic(desPlan);
			}
		}
		
		return list;
	}
	
	
	
	
	
	

	
	@Override
	public int getTotalFromMyDesignPlan(LoginUser loginUser, DesignPlan designPlan, String mediaType) {
		designPlan = this.getSearchDesignPlan( loginUser,  designPlan, mediaType);
		return this.getPlanCount(designPlan);
	}

	/**
	 * 构造查询条件
	 * @param loginUser
	 * @param designPlan
	 * @param mediaType
	 * @return DesignPlan
	 */
	private DesignPlan  getSearchDesignPlan(LoginUser loginUser, DesignPlan designPlan, String mediaType) {
		designPlan.setUserId(loginUser.getId());
		/* 根据修改时间倒序 */
		designPlan.setOrder("gmt_modified desc");
		/* 验证媒介类型是否一致 */
		designPlan.setMediaType(Utils.getIntValue(mediaType));
		/* 多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名 */
		String multifunctionQuery = designPlan.getMultifunctionQuery();
		if (StringUtils.isNoneBlank(multifunctionQuery)) {
			if (multifunctionQuery.startsWith("@") && multifunctionQuery.endsWith("@")) {
				String brandName = multifunctionQuery.replace("@", "");
				designPlan.setBrandName(brandName);
			} else if (multifunctionQuery.startsWith("*") && multifunctionQuery.endsWith("*")) {
				multifunctionQuery = multifunctionQuery.replace("*", "");
				designPlan.setMultifunctionQuery(null);
				designPlan.setPlanNumber(multifunctionQuery);
			} else {
				designPlan.setMultifunctionQuery(null);
				designPlan.setPlanName(multifunctionQuery);
			}
		}
		return designPlan;
	}
	
	
	/**
	 * 保存720渲染视频 add by yangzhun
	 * 
	 * @param designPlan
	 * @param fileMap
	 * @param renderingType
	 * @param loginUser
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Integer saveRenderPicOfVideo(DesignPlan designPlan,
			Map<String, MultipartFile> fileMap, Integer renderingType,
			LoginUser loginUser, Integer taskId, Integer sourcePlanId, Integer templateId) {
		// 应用根节点目录
		// 渲染视频存放相对路径
	    String storePath = Utils.getPropertyName("config/res",
                "design.designPlan.render.video.upload.path",
                "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/render/video/").trim();
        String storePath2 = Utils.getPropertyName("config/res",
                "design.designPlan.render.video.cover.upload.path",
                "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/render/video/cover/").trim();
		/*String uploadRoot =Tools.getRootPath(storePath,"D:\\nork\\Resource");*/
        /*String storeRealPath = uploadRoot + ("linux".equals(FileUploadUtils.SYSTEM_FORMAT) ? storePath : storePath.replace("/", "\\"));*/
		// 渲染视频存放绝对路径
        String relativePath = ("linux".equals(FileUploadUtils.SYSTEM_FORMAT) ? storePath : storePath.replace("/", "\\"));
        String storeRealPath = Utils.getAbsolutePath(relativePath, null);

		ResRenderVideo resRenderVideo = null;//720渲染视频
		ResRenderPic resRenderPic = null;//720视频封面图
		Integer resVideoId = null;//渲染视频Id
		Integer resPicId = null;//封面图id
		Date date = new Date();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				String fileName = mf.getOriginalFilename();
			String filedName = mf.getName();
			logger.debug("上传文件名称fileName = " + filedName + "   --" + fileName);
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			//String fName = fileName.substring(0, fileName.indexOf("."));
			// 解决[code]没有替换问题
			storePath = storePath.replace("[code]", designPlan.getPlanCode());
			storePath2 = storePath2.replace("[code]", designPlan.getPlanCode());
			storeRealPath = storeRealPath.replace("[code]", designPlan.getPlanCode());//替换code
			// 生成一个随机的文件名：设计方案编码 + _ + 6位的随机数 + 后缀
			String bigFileName = designPlan.getPlanCode() + "_"
					+ Utils.generateRandomDigitString(6) + "_" + fileName;
			String serverFilePath = "";// 定义一个服务器保存文件的路径

			//保存720渲染视频
			if("renderSmallPicOfVideo".equals(filedName)){
				logger.debug("在服务器保存漫游视频");
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY,
							"design.designPlan.render.video.upload.path");
				boolean flag = false;
				map.put("code", designPlan.getPlanCode());
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveVideoFile(map);//上传文件
				if(flag){
					logger.debug("保存漫游视频成功");
					//保存好视频文件后，把视频文件保存到数据库
					resRenderVideo = this.assembleResRenderVideo(map);//将map中的值set到resRenderVideo中
					resRenderVideo.setIsDeleted(0);
					resRenderVideo.setVideoPath((String)map.get(FileUploadUtils.DB_FILE_PATH));
					resRenderVideo.setGmtCreate(new Date());
					resRenderVideo.setRenderingType(renderingType);
					resRenderVideo.setCreator(loginUser == null?"":loginUser.getLoginName());
					resRenderVideo.setGmtModified(new Date());
					resRenderVideo.setModifier(loginUser == null?"":loginUser.getLoginName());
					//随机生成系统编码作为sys_code和video_code
					String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6);
					resRenderVideo.setVideoCode(sysCode);
					resRenderVideo.setSysCode(sysCode);
					resRenderVideo.setBusinessId(designPlan.getId());
					resRenderVideo.setVideoType("720渲染漫游视频");
					resRenderVideo.setTaskCreateTime(date);// 任务创建时间
					resRenderVideo.setSourcePlanId(sourcePlanId);
					resRenderVideo.setTemplateId(templateId);
					resVideoId = resRenderVideoService.add(resRenderVideo);// 保存视频文件到数据库中
					logger.debug(resVideoId+">0,保存成功");
				}
			}
			if("renderSmallPicOfVideoCover".equals(filedName)){
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY, "design.designPlan.render.video.cover.upload.path");
				boolean flag = false;
				map.put("code", designPlan.getPlanCode());
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveFile(map);
				//文件保存成功后给封面图追加水印
				if(flag){
					logger.debug("封面图保存成功！");
					serverFilePath = map.get(FileUploadUtils.SERVER_FILE_PATH).toString();//获取封面图的服务器保存路径
					try {
						ImageUtils.watermarking2(serverFilePath, null, 2, null);//给封面图加水印
					} catch (IOException e) {
						logger.debug("水印图生成失败");
						e.printStackTrace();
					}
					//保存好封面图文件后，把封面图保存在数据库
					resRenderPic = this.assembleResRenderPic(map);//将map中的值set到resRenderPic中
					resRenderPic.setIsDeleted(0);
					resRenderPic.setPicPath((String)map.get(FileUploadUtils.DB_FILE_PATH));
					resRenderPic.setGmtCreate(new Date());
					resRenderPic.setRenderingType(renderingType);
					resRenderPic.setCreator(loginUser == null?"":loginUser.getLoginName());
					resRenderPic.setGmtModified(new Date());
					resRenderPic.setModifier(loginUser == null?"":loginUser.getLoginName());
					//随机生成系统编码作为sys_code和pic_code
					String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6);
					resRenderPic.setPicCode(sysCode);
					resRenderPic.setSysCode(sysCode);
					resRenderPic.setBusinessId(designPlan.getId());
					resRenderPic.setPicType("720渲染视频封面");
					resRenderPic.setTaskCreateTime(date);// 任务创建时间
					resRenderPic.setSourcePlanId(sourcePlanId);
					resRenderPic.setTemplateId(templateId);
					resPicId = resRenderPicService.add(resRenderPic);// 保存视频的封面图到数据库中
					
					logger.debug(resPicId+"-----------,封面图保存成功。");
					
					//将封面图Id赋给video的sysTaskVideoId
					if( resPicId != null && resPicId >0 ){
						ResRenderVideo resRenderVideo2 = new ResRenderVideo();
						resRenderVideo2.setId(resVideoId);
						resRenderVideo2.setSysTaskPicId(resPicId);//关联封面图信息
						resRenderVideoService.update(resRenderVideo2);
						logger.debug("漫游视频关联封面图成功");
					}
				}
			}
		}
		return resPicId;
	}

	@Override
	public DesignPlan getSceneModifiedById(int designPlanId) {
		return designPlanMapper.getSceneModifiedById(designPlanId);
	}

	/**
	 * 将map中的值赋给resRenderVideo对象
	 * @param map
	 * @return
	 */
	private ResRenderVideo assembleResRenderVideo(Map map) {
		ResRenderVideo resRenderVideo = new ResRenderVideo();
		if (map.get(FileModel.FILE_KEY) != null) {
			resRenderVideo.setFileKey(map.get(FileModel.FILE_KEY).toString());
		}
		if (map.get(FileModel.FILE_NAME) != null) {
			resRenderVideo.setVideoName(map.get(FileModel.FILE_NAME).toString());
		}
		if (map.get(FileModel.FILE_ORIGINAL_NAME) != null) {
			resRenderVideo.setVideoFileName(map.get(FileModel.FILE_ORIGINAL_NAME)
					.toString());
		}
		if (map.get(FileModel.FILE_SUFFIX) != null) {
			resRenderVideo.setVideoSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		}
		if (map.get(FileModel.FILE_SIZE) != null) {
			resRenderVideo.setVideoSize(Integer.valueOf(map.get(FileModel.FILE_SIZE)
					.toString()));
		}
		if (map.get(FileModel.FORMAT) != null) {
			resRenderVideo.setVideoFormat(map.get(FileModel.FORMAT).toString());
		}
		return resRenderVideo;
	}

	/**
	 * 保存720漫游图片
	 * @param resRenderPic
	 * @param storePath
	 * @param storeRealPath
	 * @param mf
	 * @param bigFileName
	 * @param viewPoint
	 * @param scene
	 * @param renderingType
	 * @param loginUser
	 * @param designPlan
	 * @param panoLevel
	 * @param resScreenPicId
	 * @param date
	 * @return
	 */
	public int saveRoam720(ResRenderPic resRenderPic, String storePath, String storeRealPath, MultipartFile mf,
							String bigFileName, Integer viewPoint, Integer scene, Integer renderingType, LoginUser loginUser,
							DesignPlan designPlan, Integer panoLevel, Integer resScreenPicId, Date date){
		int resPicId = 0;
		try{
			/* 生成渲染图片 */
			/*storeRealPath = FileUploadUtils.UPLOAD_ROOT + Utils.getPropertyName("config/res",ResProperties.DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY,"/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/render/roam/config/");*/
			storeRealPath = Utils.getAbsolutePath(Utils.getPropertyName("config/res",ResProperties.DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY,"/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/render/roam/config/"), null);
			storeRealPath = Utils.replaceDate(storeRealPath);
			FileUtils.copyInputStreamToFile(mf.getInputStream(),
					new File(storeRealPath, bigFileName));
		}catch(IOException e){
			logger.error(e.getMessage());
			return resPicId;
		}
		/* 保存渲染图片 */
		File renderPic = new File(storeRealPath + bigFileName);
		Map map = FileUploadUtils.getMap(renderPic,
				ResProperties.DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY, false);
		resRenderPic = assembleResRenderPic(map);
		resRenderPic.setIsDeleted(0);
		//随机生成系统编码作为sys_code和pic_code
		String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6);
		resRenderPic.setPicCode(sysCode);
		resRenderPic.setGmtCreate(new Date());
		resRenderPic.setViewPoint(viewPoint);
		resRenderPic.setScene(scene);
		resRenderPic.setRenderingType(renderingType);
		resRenderPic.setCreator(loginUser.getLoginName());
		resRenderPic.setGmtModified(new Date());
		resRenderPic.setModifier(loginUser.getLoginName());
		resRenderPic.setSysCode(sysCode);
		resRenderPic.setBusinessId(designPlan.getId());
		resRenderPic.setPicType("3DMax渲染原图");
		resRenderPic.setTaskCreateTime(date);// 任务创建时间
		//TODO： 720全景图清晰度等级
		resRenderPic.setPanoLevel(panoLevel);
		if (resScreenPicId != null && resScreenPicId > 0) {
			resRenderPic.setSysTaskPicId(resScreenPicId);// 关联截图信息
			resPicId = resRenderPicService.add(resRenderPic);// 保存曲线图
		} else {
			resPicId = resRenderPicService.add(resRenderPic);// 保存曲线图
		}
		return resPicId;
	}

	/**
	 * 720漫游
	 * @return
	 */
	public Map<String,JSONObject> getRoamMap(JSONArray roamArray){
		Map<String,JSONObject> roamMap = new HashMap<>();
		if( roamArray != null && roamArray.size() > 0 ){
			for( Object roam : roamArray ){
				if( roam != null ){
					JSONObject roamJ = (JSONObject)roam;
					String filedName = roamJ.getString("fieldName");
					roamMap.put(filedName,roamJ);
				}
			}
		}
		return roamMap;
	}

	@Override
	public void deleteAllData(long planId) {
		DesignPlan plan = designPlanService.get((int)planId);
		
		if(plan == null){
			logger.error("------设计方案未找到,planId:" + planId);
		}
		
        // .测试发布中 、发布中 、待审核的方案不能删除、修改,点击删除提示用户"请先取消发布!"
        if (plan.getIsRelease() != null) {
            if (plan.getIsRelease().intValue() == RecommendedDecorateState.IS_RELEASEING
                    || plan.getIsRelease().intValue() == RecommendedDecorateState.IS_TEST_RELEASE
                    || plan.getIsRelease().intValue() == RecommendedDecorateState.WAITING_CHECK_RELEASE) {
            	logger.error("------测试发布中 、发布中 、待审核的方案不能删除,planId:" + planId);
                return;
            }
        }
        /* 删除设计方案，然后异步删除模型 图片等其他操作 */
        designPlanMapper.deleteByPrimaryKey((int)planId);
        AssembleDesignPlanDeleted designPlanDeleted = new AssembleDesignPlanDeleted(plan);
        FutureTask<Result> futureTask = new FutureTask<Result>(
                designPlanDeleted);
        TaskExecutor.getInstance().addTask(futureTask);
        logger.warn("任务ID：" + planId + "  异步处理完毕！");
	}

	@Override
	public List<DesignPlan> getTempDesignPaln4RenderBakSceneTask() {
		return designPlanMapper.getTempDesignPaln4RenderBakSceneTask();
	}

	@Override
	public void batchDelTempDesign(List<Integer> delPlanIdList) {
		if(delPlanIdList != null && delPlanIdList.size() > 0){
			designPlanMapper.batchDelTempDesign(delPlanIdList);
		}
	}


	/**
	 * 将720漫游的位置关系保存到文件中
	 * @param context
	 * @return
	 */
	@Override
	public Integer saveRenderRoamConfig(LoginUser loginUser, String context, Integer renderRoamId){
		Integer roamConfigId = 0;
		if( StringUtils.isNotBlank(context) ){
			// 保存文件
			String roamConfigPath = Utils.getPropertyName("config/res","design.designPlan.render.roam.config.upload.path","");
			/*String filePath = FileUploadUtils.UPLOAD_ROOT + roamConfigPath;*/
			String filePath = Utils.getAbsolutePath(roamConfigPath, null);
			String fileName = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6) + ".txt";
			boolean flag = FileUploadUtils.writeTxtFile(Utils.replaceDate(filePath)+fileName,context);
			if( flag ){
				File file = new File(Utils.replaceDate(filePath)+fileName);
				Map<String, String> map = FileUploadUtils.getMap(file,"design.designPlan.render.roam.config.upload.path",true);
				ResDesign resDesign = new ResDesign();
				resDesign.setFileCode(map.get(FileModel.FILE_NAME));
				resDesign.setFileName(map.get(FileModel.FILE_NAME));
				resDesign.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
				resDesign.setFileType("720漫游位置关系文件");
				resDesign.setFileSize(map.get(FileModel.FILE_SIZE));
				resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
				resDesign.setFilePath(map.get(FileModel.FILE_PATH));
				resDesign.setFileKey(map.get(FileModel.FILE_KEY));
				resDesign.setBusinessId(renderRoamId);

				resDesign.setSysCode(map.get(FileModel.FILE_NAME));
				resDesign.setCreator(loginUser.getName());
				resDesign.setModifier(loginUser.getName());
				resDesign.setGmtCreate(new Date());
				resDesign.setGmtModified(resDesign.getGmtCreate());
				resDesign.setIsDeleted(0);

				roamConfigId = resDesignService.add(resDesign);
			}
		}
		return roamConfigId;
	}

    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanService#getTempDesignPalnId(int, int)    
     */
    @Override
    public DesignPlan getTempDesignPalnId(int designPlanRenderSceneId, int userId) {

        DesignPlan designPlan = new DesignPlan();
        designPlan.setUserId(userId);
        designPlan.setDesignSceneId(designPlanRenderSceneId);
        
        return  designPlanMapper.getTempDesignPalnBySceneId(designPlan);
    
    }

	@Override
	public Integer createPlanByConfig(
			Integer designTempletId, Integer recommendedPlanId,
			String configEncrypt, String mediaType, LoginUser loginUser,
			List<PosNameInfo> posNameInfoList,
			Integer opType
			) {
		
		// *参数验证 ->start
		if(designTempletId == null) {
			logger.error("------function:DesignPlanServiceImpl.createPlanByConfig(......)->\n参数designTempletId不能为空");
			return null;
		}
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if(designTemplet == null) {
			logger.error("------function:DesignPlanServiceImpl.createPlanByConfig(......)->\n样板房未找到(designTempletId = " + designTempletId + ")");
		}
		if(recommendedPlanId == null) {
			logger.error("------function:DesignPlanServiceImpl.createPlanByConfig(......)->\n参数recommendedPlanId不能为空");
			return null;
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if(designPlanRecommended == null) {
			return null;
		}
		if(StringUtils.isEmpty(configEncrypt)) {
			return null;
		}
		if(StringUtils.isEmpty(mediaType)) {
			return null;
		}
		if(loginUser == null) {
			return null;
		}
		if(Lists.isEmpty(posNameInfoList)) {
			return null;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证 ->end
		
		// *得到designPlan ->start
		Integer designPlanProductId = posNameInfoList.get(0).getDeignPlanProductId();
		Integer planId = null;
		if(designPlanProductId != null) {
			
			DesignPlanProduct designPlanProduct = null;
			if(DesignPlanConstants.AUTO_RENDER != opType) {
				designPlanProduct = designPlanProductService.get(designPlanProductId);
			}else {
				// 自动渲染
				designPlanProduct = optimizePlanService.getPlanProduct(designPlanProductId);
			}
			if(designPlanProduct != null) {
				planId = designPlanProduct.getPlanId();
			}
		}
		if(planId == null || planId < 1) {
			logger.error("------function:DesignPlanServiceImpl.createPlanByConfig(......)->\n设计方案未找到,该设计方案的某个设计方案产品id:designPlanProductId:" + designPlanProductId);
			return null;
		}
		
		DesignPlan designPlan = null;
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			designPlan = this.get(planId);
		}else {
			// 自动渲染
			designPlan = optimizePlanService.getPlan(planId);
		}
		
		if(designPlan == null) {
			logger.error("------function:DesignPlanServiceImpl.createPlanByConfig(......)->\n设计方案未找到;planId:" + planId);
			return null;
		}
		// *得到designPlan ->end
		
		// *保存配置文件 ->start
		String configKey = Utils.getValueByFileKey(ResProperties.RES, ResProperties.DESIGNPLAN_U3DCONFIG_FILEKEY, "/AA/d_userdesign/[YYYY]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/");
		//文件名称
		String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
		//先把文件保存到本地
		String filePath = Utils.getAbsolutePath(configKey + fileName, null);
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath, configEncrypt);
		if(!uploadFtpFlag) {
			// 设计文件保存失败
			logger.error("function:DesignPlanServiceImpl.createPlanByConfig(....)->\n设计方案配置文件保存失败");
			return null;
		}
		Integer resDesignId = resDesignService.createResDesignByFile(
				new File(filePath), ResProperties.DESIGNPLAN_U3DCONFIG_FILEKEY,
				designPlan.getPlanCode(), loginUser.getLoginName(), designPlan.getId(),
				opType);
		if(resDesignId == null || resDesignId < 1) {
			logger.error("function:DesignPlanServiceImpl.createPlanByConfig(....)->\\n设计方案配置文件数据(ResDesign)新建失败");
			return null;
		}
		
		/*拷贝效果图拼花配置文件*/
		if (designPlanRecommended.getSpellingFlowerFileId() != null) {
			Integer resFileId = this.planCopyFileFromResDesignScene(designPlanRecommended.getSpellingFlowerFileId().toString(), 
				"design.designPlan.spellingFlowerFile","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/",
				null, designPlanRecommended.getPlanCode(),loginUser);
			designPlan.setSpellingFlowerFileId(resFileId);
		} else {
			designPlan.setSpellingFlowerFileId(-1);
		}

		designPlan.setConfigFileId(resDesignId);
		designPlan.setSpellingFlowerProduct(designPlanRecommended.getSpellingFlowerProduct());
		// *保存配置文件 ->end
		
		// *生成设计方案产品列表 ->start
		boolean flag = designPlanProductService.updateByConfig(configEncrypt, posNameInfoList, opType);
		if(!flag) {
			return null;
		}
		// *生成设计方案产品列表 ->end
		
		designPlan.setIsDeleted(0);
		designPlan.setRecommendedPlanId(recommendedPlanId);
		
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			this.update(designPlan);
		}else {
			// 自动渲染
			optimizePlanService.update(designPlan);
		}
		return designPlan.getId();
	}

	private boolean checkContext(String context){

		//配置文件解密
		try {
			context = AESUtilN.getInstance().decrypt(context);
		} catch (Exception e) {
			logger.error( "出现错误当前样板房配置文件解密异常id=",e);
			return false;
		}

		//json转换
		JSONObject jsonObject = JSONObject.fromObject(context);

		//转换配置
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(RoomConfig.class);
		//处理json中key的首字母大写情况
		jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String s) {
				char[] chars = s.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		//过滤在json中Entity没有的属性
		jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());

		//数据获取与校验
		JSONArray jsonArray = (JSONArray) jsonObject.get("RoomConfig");
		List<RoomConfig> roomConfigs = (List<RoomConfig>) JSONArray.toCollection(jsonArray, jsonConfig);

		if(null != roomConfigs && roomConfigs.size() > 0){

			List<String> posIndexPathList = roomConfigs.stream().map(roomConfig -> roomConfig.getPosIndexPath()).collect(Collectors.toList());
			List<String> posNameList = roomConfigs.stream().map(roomConfig -> roomConfig.getPosName()).collect(Collectors.toList());

			long dPosIndexCount = posIndexPathList.stream().distinct().count();
			long dPosNameCount = posNameList.stream().distinct().count();

			boolean posIndexBool = dPosIndexCount < posIndexPathList.size();
			boolean posNameBool = dPosNameCount < posNameList.size();

			if(posIndexBool || posNameBool){
				return false;
			}
		}

		return true;
	}


//	public static void main(String[] args) {
		//String context = "{\"AreaBlockData\":{\"ceilingData\":null,\"floorData\":null,\"originData\":{\"blockName\":\"3a4dda22-1307-428c-b1eb-f04b8df3f8df\",\"modelUniqueId\":null,\"mark\":null,\"bounds\":[{\"x\":0.686352,\"y\":0.968318641,\"normalized\":{\"x\":0.5782752,\"y\":0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872014},{\"x\":0.686352,\"y\":-0.9683186,\"normalized\":{\"x\":0.5782752,\"y\":-0.815841734,\"normalized\":{\"x\":0.578275263,\"y\":-0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872},{\"x\":-0.6863513,\"y\":-0.9683186,\"normalized\":{\"x\":-0.578274846,\"y\":-0.815842032,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689466,\"sqrMagnitude\":1.408719},{\"x\":-0.6863513,\"y\":-0.0519999266,\"normalized\":{\"x\":-0.9971423,\"y\":-0.07554634,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.6883183,\"sqrMagnitude\":0.4737821},{\"x\":-0.6863513,\"y\":0.968318641,\"normalized\":{\"x\":-0.578274846,\"y\":0.8158421,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.18689466,\"sqrMagnitude\":1.40871906}],\"areaItems\":[{\"itemName\":\"主区域\",\"area\":2.65842819,\"bounds\":[{\"x\":-0.6863513,\"y\":-0.9683186,\"normalized\":{\"x\":-0.578274846,\"y\":-0.815842032,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689466,\"sqrMagnitude\":1.408719},{\"x\":-0.6863513,\"y\":0.968318641,\"normalized\":{\"x\":-0.578274846,\"y\":0.8158421,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.18689466,\"sqrMagnitude\":1.40871906},{\"x\":0.686352,\"y\":0.968318641,\"normalized\":{\"x\":0.5782752,\"y\":0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872014},{\"x\":0.686352,\"y\":-0.9683186,\"normalized\":{\"x\":0.5782752,\"y\":-0.815841734,\"normalized\":{\"x\":0.578275263,\"y\":-0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872}]}]}},\"MainCameraIndexPath\":\"\\\"5/0/\\\"\",\"DrawCabinetDatas\":[],\"RoomConfig\":[{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/0/\\\"\",\"ItemCode\":\"baimo_dim_basic_dimz_1887248763\",\"PosName\":\"dimz01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/1/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":0.0,\"z\":-1.1920929E-07,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":2.66560079E-07,\"sqrMagnitude\":7.10542736E-14},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/0/\\\"\",\"ItemCode\":\"baimo_dim_basic_dimmk_1232094961\",\"PosName\":\"dimz01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/2/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.746351242,\"y\":0.0,\"z\":-0.580166,\"normalized\":{\"x\":-0.7895211,\"y\":0.0,\"z\":-0.613723457,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.9453215,\"sqrMagnitude\":0.89363277},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/2/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1612672389\",\"PosName\":\"qiangm01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/3/\\\"\",\"LookPoint\":[\"0\",\"6\",\"7\"],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":1.45,\"z\":1.02831864,\"normalized\":{\"x\":1.34122217E-07,\"y\":0.815696537,\"z\":0.578479946,\"normalized\":{\"x\":1.34122232E-07,\"y\":0.8156966,\"z\":0.57848,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":1.77762187,\"sqrMagnitude\":3.15993929},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"w\":-4.371139E-08,\"eulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":180.0,\"sqrMagnitude\":32400.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/3/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1225457409\",\"PosName\":\"qiangm02\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/4/\\\"\",\"LookPoint\":[\"2\",\"3\",\"4\"],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":1.45,\"z\":-1.02831852,\"normalized\":{\"x\":1.34122217E-07,\"y\":0.8156966,\"z\":-0.578479946,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.77762175,\"sqrMagnitude\":3.159939},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-2.18556941E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-2.504478E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":2.504478E-06,\"sqrMagnitude\":6.27241054E-12}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/4/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1136980769\",\"PosName\":\"qiangm03\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/5/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.746351242,\"y\":1.45,\"z\":1.1920929E-07,\"normalized\":{\"x\":-0.457656831,\"y\":0.889128864,\"z\":7.309822E-08,\"normalized\":{\"x\":-0.45765686,\"y\":0.8891289,\"z\":7.309823E-08,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.63080978,\"sqrMagnitude\":2.65954041},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/5/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1484705808\",\"PosName\":\"qiangm04\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/6/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.746351957,\"y\":1.45,\"z\":1.1920929E-07,\"normalized\":{\"x\":0.457657218,\"y\":0.889128745,\"z\":7.309821E-08,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.63081,\"sqrMagnitude\":2.65954137},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":-0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/6/\\\"\",\"ItemCode\":\"baimo_meng_basic_weisjm_7942516577\",\"PosName\":\"weisjm01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/7/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.746351242,\"y\":1.05000031,\"z\":-0.580166,\"normalized\":{\"x\":-0.5282609,\"y\":0.7431811,\"z\":-0.410636425,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.41284585,\"sqrMagnitude\":1.99613345},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/7/\\\"\",\"ItemCode\":\"baimo_tianh_basic_tianh_1908584986\",\"PosName\":\"tianh01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/8/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":3.05000019,\"z\":1.1920929E-07,\"normalized\":{\"x\":7.817002E-08,\"y\":1.0,\"z\":3.908501E-08,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":3.05000019,\"sqrMagnitude\":9.302501},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/0/\\\"\",\"ItemCode\":\"baimo_coba_0001\",\"PosName\":\"coba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/11/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.426902771,\"y\":0.459999979,\"z\":-0.741318345,\"normalized\":{\"x\":0.4395227,\"y\":0.473598331,\"z\":-0.7632329,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.9712872,\"sqrMagnitude\":0.943398833},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-5.96046448E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-6.83018925E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":6.83018925E-06,\"sqrMagnitude\":4.66514848E-11}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/1/\\\"\",\"ItemCode\":\"baimo_toba_0001\",\"PosName\":\"toba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/9/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.329255342,\"y\":0.311885923,\"z\":0.0559786558,\"normalized\":{\"x\":0.72052896,\"y\":0.6825184,\"z\":0.122501411,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.45696336,\"sqrMagnitude\":0.208815515},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":-0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/2/\\\"\",\"ItemCode\":\"baimo_ptbp_0001\",\"PosName\":\"ptbp01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/10/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.614266634,\"y\":0.3128623,\"z\":-0.296885848,\"normalized\":{\"x\":0.818405867,\"y\":0.416835845,\"z\":-0.395549923,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.7505648,\"sqrMagnitude\":0.5633475},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":-0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/3/\\\"\",\"ItemCode\":\"baimo_twbp_0002\",\"PosName\":\"twbp01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/12/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.116858959,\"y\":1.6,\"z\":-0.930132031,\"normalized\":{\"x\":-0.06301711,\"y\":0.862812459,\"z\":-0.501580954,\"normalized\":{\"x\":-0.063017115,\"y\":0.8628125,\"z\":-0.501581,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.85440063,\"sqrMagnitude\":3.43880177},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":8.742278E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":1.00179122E-05,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.00179122E-05,\"sqrMagnitude\":1.00358569E-10}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/4/\\\"\",\"ItemCode\":\"baimo_ltbp_0002\",\"PosName\":\"ltbp01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/13/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.404073954,\"y\":1.03296673,\"z\":-0.8362824,\"normalized\":{\"x\":0.2908841,\"y\":0.7436104,\"z\":-0.6020216,\"normalized\":{\"x\":0.290884137,\"y\":0.743610442,\"z\":-0.602021635,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.38912356,\"sqrMagnitude\":1.92966425},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-5.96046448E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-6.83018925E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":6.83018925E-06,\"sqrMagnitude\":4.66514848E-11}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/5/\\\"\",\"ItemCode\":\"baimo_ddli_0002\",\"PosName\":\"ddli01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/14/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.0263330936,\"y\":2.57499981,\"z\":-0.531550169,\"normalized\":{\"x\":-0.0100147817,\"y\":0.979302347,\"z\":-0.202154711,\"normalized\":{\"x\":-0.0100147827,\"y\":0.9793024,\"z\":-0.202154726,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":2.62942266,\"sqrMagnitude\":6.913863},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/6/\\\"\",\"ItemCode\":\"baimo_asba_0002\",\"PosName\":\"asba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/15/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.284351349,\"y\":1.10999978,\"z\":0.5663179,\"normalized\":{\"x\":-0.222470775,\"y\":0.868441463,\"z\":0.443075716,\"normalized\":{\"x\":-0.22247079,\"y\":0.8684415,\"z\":0.443075746,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":1.27815151,\"sqrMagnitude\":1.63367116},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"w\":-4.371139E-08,\"eulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":180.0,\"sqrMagnitude\":32400.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/7/\\\"\",\"ItemCode\":\"baimo_haba_0003\",\"PosName\":\"haba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/16/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.387131453,\"y\":1.3,\"z\":0.5940624,\"normalized\":{\"x\":-0.261433274,\"y\":0.8779014,\"z\":0.401175559,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.48080409,\"sqrMagnitude\":2.19278073},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/8/\\\"\",\"ItemCode\":\"baimo_mipe_0004\",\"PosName\":\"mipe01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/17/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.434351921,\"y\":1.59346128,\"z\":-0.9387375,\"normalized\":{\"x\":0.22863768,\"y\":0.838779032,\"z\":-0.494140238,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.899739,\"sqrMagnitude\":3.60900855},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-5.96046448E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-6.83018925E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":6.83018925E-06,\"sqrMagnitude\":4.66514848E-11}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/9/\\\"\",\"ItemCode\":\"baimo_ho_basic_dzho_9628908410\",\"PosName\":\"dzho01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/18/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.667216539,\"y\":2.126957,\"z\":0.197796464,\"normalized\":{\"x\":0.298142523,\"y\":0.9504205,\"z\":0.0883844,\"normalized\":{\"x\":0.298142552,\"y\":0.950420558,\"z\":0.0883844048,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":2.23791146,\"sqrMagnitude\":5.00824738},\"LinkScale\":{\"x\":0.467320949,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.313759178,\"y\":0.6713997,\"z\":0.6713997,\"normalized\":{\"x\":0.3137592,\"y\":0.6713998,\"z\":0.6713998,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.48942566,\"sqrMagnitude\":2.2183888},\"LinkQua\":{\"x\":0.0,\"y\":-0.7071069,\"z\":0.0,\"w\":0.7071067,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/10/\\\"\",\"ItemCode\":\"baimo_ho_basic_dzho_2035905809\",\"PosName\":\"dzho02\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/19/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.667217,\"y\":2.126957,\"z\":-0.309524417,\"normalized\":{\"x\":0.296469748,\"y\":0.9450874,\"z\":-0.1375334,\"normalized\":{\"x\":0.296469778,\"y\":0.945087433,\"z\":-0.137533411,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":2.25054,\"sqrMagnitude\":5.06493},\"LinkScale\":{\"x\":0.467320949,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.313759178,\"y\":0.6713997,\"z\":0.6713997,\"normalized\":{\"x\":0.3137592,\"y\":0.6713998,\"z\":0.6713998,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.48942566,\"sqrMagnitude\":2.2183888},\"LinkQua\":{\"x\":0.0,\"y\":-0.7071069,\"z\":0.0,\"w\":0.7071067,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1}]}";
		//String context = "{\"AreaBlockData\":{\"ceilingData\":null,\"floorData\":null,\"originData\":{\"blockName\":\"3a4dda22-1307-428c-b1eb-f04b8df3f8df\",\"modelUniqueId\":null,\"mark\":null,\"bounds\":[{\"x\":0.686352,\"y\":0.968318641,\"normalized\":{\"x\":0.5782752,\"y\":0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872014},{\"x\":0.686352,\"y\":-0.9683186,\"normalized\":{\"x\":0.5782752,\"y\":-0.815841734,\"normalized\":{\"x\":0.578275263,\"y\":-0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872},{\"x\":-0.6863513,\"y\":-0.9683186,\"normalized\":{\"x\":-0.578274846,\"y\":-0.815842032,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689466,\"sqrMagnitude\":1.408719},{\"x\":-0.6863513,\"y\":-0.0519999266,\"normalized\":{\"x\":-0.9971423,\"y\":-0.07554634,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.6883183,\"sqrMagnitude\":0.4737821},{\"x\":-0.6863513,\"y\":0.968318641,\"normalized\":{\"x\":-0.578274846,\"y\":0.8158421,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.18689466,\"sqrMagnitude\":1.40871906}],\"areaItems\":[{\"itemName\":\"主区域\",\"area\":2.65842819,\"bounds\":[{\"x\":-0.6863513,\"y\":-0.9683186,\"normalized\":{\"x\":-0.578274846,\"y\":-0.815842032,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689466,\"sqrMagnitude\":1.408719},{\"x\":-0.6863513,\"y\":0.968318641,\"normalized\":{\"x\":-0.578274846,\"y\":0.8158421,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.18689466,\"sqrMagnitude\":1.40871906},{\"x\":0.686352,\"y\":0.968318641,\"normalized\":{\"x\":0.5782752,\"y\":0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872014},{\"x\":0.686352,\"y\":-0.9683186,\"normalized\":{\"x\":0.5782752,\"y\":-0.815841734,\"normalized\":{\"x\":0.578275263,\"y\":-0.8158418,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.18689513,\"sqrMagnitude\":1.40872}]}]}},\"MainCameraIndexPath\":\"\\\"5/0/\\\"\",\"DrawCabinetDatas\":[],\"RoomConfig\":[{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/0/\\\"\",\"ItemCode\":\"baimo_dim_basic_dimz_1887248763\",\"PosName\":\"dimz01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/1/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":0.0,\"z\":-1.1920929E-07,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":2.66560079E-07,\"sqrMagnitude\":7.10542736E-14},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/1/\\\"\",\"ItemCode\":\"baimo_dim_basic_dimmk_1232094961\",\"PosName\":\"dimmk01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/2/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.746351242,\"y\":0.0,\"z\":-0.580166,\"normalized\":{\"x\":-0.7895211,\"y\":0.0,\"z\":-0.613723457,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.9453215,\"sqrMagnitude\":0.89363277},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/2/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1612672389\",\"PosName\":\"qiangm01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/3/\\\"\",\"LookPoint\":[\"0\",\"6\",\"7\"],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":1.45,\"z\":1.02831864,\"normalized\":{\"x\":1.34122217E-07,\"y\":0.815696537,\"z\":0.578479946,\"normalized\":{\"x\":1.34122232E-07,\"y\":0.8156966,\"z\":0.57848,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":1.77762187,\"sqrMagnitude\":3.15993929},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"w\":-4.371139E-08,\"eulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":180.0,\"sqrMagnitude\":32400.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/3/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1225457409\",\"PosName\":\"qiangm02\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/4/\\\"\",\"LookPoint\":[\"2\",\"3\",\"4\"],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":1.45,\"z\":-1.02831852,\"normalized\":{\"x\":1.34122217E-07,\"y\":0.8156966,\"z\":-0.578479946,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.77762175,\"sqrMagnitude\":3.159939},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-2.18556941E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-2.504478E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":2.504478E-06,\"sqrMagnitude\":6.27241054E-12}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/4/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1136980769\",\"PosName\":\"qiangm03\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/5/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.746351242,\"y\":1.45,\"z\":1.1920929E-07,\"normalized\":{\"x\":-0.457656831,\"y\":0.889128864,\"z\":7.309822E-08,\"normalized\":{\"x\":-0.45765686,\"y\":0.8891289,\"z\":7.309823E-08,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.63080978,\"sqrMagnitude\":2.65954041},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/5/\\\"\",\"ItemCode\":\"baimo_qiangm_basic_qiangm_1484705808\",\"PosName\":\"qiangm04\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/6/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.746351957,\"y\":1.45,\"z\":1.1920929E-07,\"normalized\":{\"x\":0.457657218,\"y\":0.889128745,\"z\":7.309821E-08,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.63081,\"sqrMagnitude\":2.65954137},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":-0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/6/\\\"\",\"ItemCode\":\"baimo_meng_basic_weisjm_7942516577\",\"PosName\":\"weisjm01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/7/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.746351242,\"y\":1.05000031,\"z\":-0.580166,\"normalized\":{\"x\":-0.5282609,\"y\":0.7431811,\"z\":-0.410636425,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.41284585,\"sqrMagnitude\":1.99613345},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"1/7/\\\"\",\"ItemCode\":\"baimo_tianh_basic_tianh_1908584986\",\"PosName\":\"tianh01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/8/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":2.38418579E-07,\"y\":3.05000019,\"z\":1.1920929E-07,\"normalized\":{\"x\":7.817002E-08,\"y\":1.0,\"z\":3.908501E-08,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":3.05000019,\"sqrMagnitude\":9.302501},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/0/\\\"\",\"ItemCode\":\"baimo_coba_0001\",\"PosName\":\"coba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/11/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.426902771,\"y\":0.459999979,\"z\":-0.741318345,\"normalized\":{\"x\":0.4395227,\"y\":0.473598331,\"z\":-0.7632329,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.9712872,\"sqrMagnitude\":0.943398833},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-5.96046448E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-6.83018925E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":6.83018925E-06,\"sqrMagnitude\":4.66514848E-11}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/1/\\\"\",\"ItemCode\":\"baimo_toba_0001\",\"PosName\":\"toba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/9/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.329255342,\"y\":0.311885923,\"z\":0.0559786558,\"normalized\":{\"x\":0.72052896,\"y\":0.6825184,\"z\":0.122501411,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.45696336,\"sqrMagnitude\":0.208815515},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":-0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/2/\\\"\",\"ItemCode\":\"baimo_ptbp_0001\",\"PosName\":\"ptbp01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/10/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.614266634,\"y\":0.3128623,\"z\":-0.296885848,\"normalized\":{\"x\":0.818405867,\"y\":0.416835845,\"z\":-0.395549923,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.7505648,\"sqrMagnitude\":0.5633475},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":-0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/3/\\\"\",\"ItemCode\":\"baimo_twbp_0002\",\"PosName\":\"twbp01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/12/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.116858959,\"y\":1.6,\"z\":-0.930132031,\"normalized\":{\"x\":-0.06301711,\"y\":0.862812459,\"z\":-0.501580954,\"normalized\":{\"x\":-0.063017115,\"y\":0.8628125,\"z\":-0.501581,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.85440063,\"sqrMagnitude\":3.43880177},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":8.742278E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":1.00179122E-05,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.00179122E-05,\"sqrMagnitude\":1.00358569E-10}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/4/\\\"\",\"ItemCode\":\"baimo_ltbp_0002\",\"PosName\":\"ltbp01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/13/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.404073954,\"y\":1.03296673,\"z\":-0.8362824,\"normalized\":{\"x\":0.2908841,\"y\":0.7436104,\"z\":-0.6020216,\"normalized\":{\"x\":0.290884137,\"y\":0.743610442,\"z\":-0.602021635,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.38912356,\"sqrMagnitude\":1.92966425},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-5.96046448E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-6.83018925E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":6.83018925E-06,\"sqrMagnitude\":4.66514848E-11}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/5/\\\"\",\"ItemCode\":\"baimo_ddli_0002\",\"PosName\":\"ddli01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/14/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.0263330936,\"y\":2.57499981,\"z\":-0.531550169,\"normalized\":{\"x\":-0.0100147817,\"y\":0.979302347,\"z\":-0.202154711,\"normalized\":{\"x\":-0.0100147827,\"y\":0.9793024,\"z\":-0.202154726,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":2.62942266,\"sqrMagnitude\":6.913863},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/6/\\\"\",\"ItemCode\":\"baimo_asba_0002\",\"PosName\":\"asba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/15/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.284351349,\"y\":1.10999978,\"z\":0.5663179,\"normalized\":{\"x\":-0.222470775,\"y\":0.868441463,\"z\":0.443075716,\"normalized\":{\"x\":-0.22247079,\"y\":0.8684415,\"z\":0.443075746,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":1.27815151,\"sqrMagnitude\":1.63367116},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"w\":-4.371139E-08,\"eulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":180.0,\"sqrMagnitude\":32400.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/7/\\\"\",\"ItemCode\":\"baimo_haba_0003\",\"PosName\":\"haba01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/16/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":-0.387131453,\"y\":1.3,\"z\":0.5940624,\"normalized\":{\"x\":-0.261433274,\"y\":0.8779014,\"z\":0.401175559,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.48080409,\"sqrMagnitude\":2.19278073},\"LinkScale\":{\"x\":1.000001,\"y\":1.0,\"z\":1.000001,\"normalized\":{\"x\":0.5773505,\"y\":0.5773499,\"z\":0.5773505,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":1.73205185,\"sqrMagnitude\":3.00000381},\"LinkQua\":{\"x\":0.0,\"y\":0.7071068,\"z\":0.0,\"w\":0.7071068,\"eulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":90.0,\"sqrMagnitude\":8100.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/8/\\\"\",\"ItemCode\":\"baimo_mipe_0004\",\"PosName\":\"mipe01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/17/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.434351921,\"y\":1.59346128,\"z\":-0.9387375,\"normalized\":{\"x\":0.22863768,\"y\":0.838779032,\"z\":-0.494140238,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":1.899739,\"sqrMagnitude\":3.60900855},\"LinkScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.577350259,\"y\":0.577350259,\"z\":0.577350259,\"normalized\":{\"x\":0.5773503,\"y\":0.5773503,\"z\":0.5773503,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.73205078,\"sqrMagnitude\":3.0},\"LinkQua\":{\"x\":0.0,\"y\":-5.96046448E-08,\"z\":0.0,\"w\":1.0,\"eulerAngles\":{\"x\":0.0,\"y\":-6.83018925E-06,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"magnitude\":0.0,\"sqrMagnitude\":0.0},\"magnitude\":6.83018925E-06,\"sqrMagnitude\":4.66514848E-11}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/9/\\\"\",\"ItemCode\":\"baimo_ho_basic_dzho_9628908410\",\"PosName\":\"dzho01\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/18/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.667216539,\"y\":2.126957,\"z\":0.197796464,\"normalized\":{\"x\":0.298142523,\"y\":0.9504205,\"z\":0.0883844,\"normalized\":{\"x\":0.298142552,\"y\":0.950420558,\"z\":0.0883844048,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":2.23791146,\"sqrMagnitude\":5.00824738},\"LinkScale\":{\"x\":0.467320949,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.313759178,\"y\":0.6713997,\"z\":0.6713997,\"normalized\":{\"x\":0.3137592,\"y\":0.6713998,\"z\":0.6713998,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.48942566,\"sqrMagnitude\":2.2183888},\"LinkQua\":{\"x\":0.0,\"y\":-0.7071069,\"z\":0.0,\"w\":0.7071067,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1},{\"RootName\":\"\",\"PosIndexPath\":\"\\\"2/10/\\\"\",\"ItemCode\":\"baimo_ho_basic_dzho_2035905809\",\"PosName\":\"dzho02\",\"ChangeTimeCount\":0,\"CameraIndexPath\":\"\\\"5/19/\\\"\",\"LookPoint\":[],\"LinkPosition\":{\"x\":0.667217,\"y\":2.126957,\"z\":-0.309524417,\"normalized\":{\"x\":0.296469748,\"y\":0.9450874,\"z\":-0.1375334,\"normalized\":{\"x\":0.296469778,\"y\":0.945087433,\"z\":-0.137533411,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":0.99999994,\"sqrMagnitude\":0.9999999},\"magnitude\":2.25054,\"sqrMagnitude\":5.06493},\"LinkScale\":{\"x\":0.467320949,\"y\":1.0,\"z\":1.0,\"normalized\":{\"x\":0.313759178,\"y\":0.6713997,\"z\":0.6713997,\"normalized\":{\"x\":0.3137592,\"y\":0.6713998,\"z\":0.6713998,\"magnitude\":1.0,\"sqrMagnitude\":1.00000012},\"magnitude\":0.99999994,\"sqrMagnitude\":0.99999994},\"magnitude\":1.48942566,\"sqrMagnitude\":2.2183888},\"LinkQua\":{\"x\":0.0,\"y\":-0.7071069,\"z\":0.0,\"w\":0.7071067,\"eulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0,\"normalized\":{\"x\":0.0,\"y\":1.0,\"z\":0.0,\"magnitude\":1.0,\"sqrMagnitude\":1.0},\"magnitude\":270.0,\"sqrMagnitude\":72900.0}},\"bindPointDataEx\":[],\"bindParentObjStr\":null,\"bindParentObjPosNameStr\":null,\"nBindIndexInParent\":1}]}";

//		String context = "AE85E9CB15732D1C98749E99A8C5CED6375D2C8F5F707F9A8579272B40AA90E5E195FCF74F0E360CB9F7B3A858A12A4F493D96891B9905D4E54290EF8E0582FCD0CC22F9782DD7DA1D7B09C1380C1D484F09E1E04A3A5C493B531F0E3D605F91EB9F3F6DCB896AE4D2C7649822DF3D3FCC51949EBCFDDE0782F631B9BA55400A482493147E5AEAE61BCE28C5621550EC3A6BDF7136837EEDF615F25C9D690C21ED86D00A25F415006C259B65F125FEC079FF6EBFD05D368F13157EDDEAC73805DB3AB8D5672EFDE1DC9A381FD752C92D5963D779C26C5AD4E5B3B38BB7FC30CF911B1F3A1D87C4ADB27B8A64C76BBCE131CC07F99192BB5D1EB4C979781206CC26348B6872EEEDC272AAD71B6CD0CAA6ADB7C941F67B886F77DAC1A0850915F63A4AE3FF51DEF4DF9981955D570BED05BC645DA4BCA8E9B71E23423D10C6293C7B82CB8FDB21F23D8F1122F3720D7AF7D6AC83FD40760131C79091DA67D0DCC3DA1CAED9B3539E92F4B70B7AACD88AB38A1C7594EAAD9A1A1CE82F7757B95B2C5D8B0E9FBC65FA510BC174ECBDCC78B11BDF8D208351120A61FAF66483AB5E4E49F40E35C445201FBFCEAD81C17F94B8DCE8C4BB220B39DA58DA833AD5E8D356C8F34DDE2E06E8362CF41E757D6D897692FC0C690F10E7D5638C6F6DC06FCE832EC40821B10A5C49BD6737B71D95D6B460CBE2726C4D0D52EC87EA039C24F3013196E657F4D02FF875DCEF1D54B55A14B4637E31FD7A09F4387D59DACF0CC7DAA65859C42F950F184D0C6990F6597B5896D0ED3BFB7AFC8C4AFA19B546AB9C6B0C2FEC5AA7B8621D72A42E2BBBA86BE5EB198FC4CB1B34811ACFB7059733D18EC89F09575B9A412A970BB273850A219CF6D1972578B6DD6803F00FC99818BE13F942F8819007F79E70ADAD531D0F62EE34DB67D5D264C736EECBB9929273CE197C6E996188C15AC96042AF42BC1C5FD693F240DA90B4F3B8666CA6ED15B27D1C67FC2510F612298466B44540188CEAC703C9BD15D47811300F027871607060AEAF3CA430803ABBCA23E32D93BCBEB77DEB18D53274F3E6FBE25888A25D90A08DE7260DECA7198BAA3B0B0203122FE9207D2AD0C2A8E37DD597FFC849E85059EC7424B50BF7AAF0F9AB754D75532880AEEA2ACFD2F6E77A71B56847C28CC55564D8A80DC653A6781A3CD5695E8A63C821035518E8976BDC3265241B9CDAD7E8244CCA37E5F6DCC7E451F34535E5BCF16908DB973D59CFFD3DC797DD6971243D0A6EB4A683F2FB63C2DDDB52A32D0972671DD30886F5D908CC08D4C981C4B61A33D491155DC55C328D17B355FEED541DAC2C40BA55C7AFC57FE3058C23FB750DF0DC984B4308BFF837BC59632A2A12E7E19C31C1403C6542D9ED8ADE0960602052475E4757CD556730CCDF71856E5BF3192483E9A26615DE6A9F619C688597C3A2D89DA6E22543BD0A3E14524E7B3BD9BA8A40D9A57D737326872925A27AD130E0DC82622AFCFF6F8CF0F8F6C189D3FFB1DA4A3581CDE2C945439F79A33826D4F8FCEB1761C049CEC56D8BFB3AC02378B40E9E17B4C37C8A47D25BFE1C0DE27ECE4B3389A2CFF9D294512D9B2C52A24A80F12D80C349675DFCDC9C0EB871EA8935921DB2229C7896623B3905FEBC8AF9CAB84EBE41256909B5B6CD535302C3D53A18A82592883DF5FB429DDF6FA179CC5B9AC65480B6CFC92ABAD0D128A1BF4AAD184AB824CD173222BFAD70EFE0176EA60B58F1CA1D5B153594CCBF5CADF75919BB33F73B1582BB4C05E4250FD4C1270B4428D6606CFD2CA04CA6A358069C829FD41968F83654D8EE7E43D0A9BE54E9BECAA9C4567D2B2A71335AF370F292258191BB47F42CA02C970B3B4E97241FDF5CEE5BE96CEDA091DF8063BB19FE10A4B61C8F2EB42F5298D0FF02D8A98EDA0992460AF9228713E23091772D2E9BF0F382078CB9E0FC2C6E83D610C2F2F0996C77880FA16438F5B920CF9D3747E8C22B1A547F955415434A590DE7335810F67D0EBE9964427E9046081CE865B247C212726F605DCCE43EA9AEFF80E8804B78E21816ED735531A1F063CFF9D661C5003D8D47192B9E092645EDDEEBF60A9A3F1BE80F97FCBB8F46624A322AC614866F15B633D37E3B762B97580D3436F63BE3D337625EF23CDFCF917B5E1E0553BBB2C4561751D66FA1DC2ACDBAFEF90F4CEDE9D0674A76C108069F7B74663AA88CFE0706E62FEA4D6D4F1F8EC4FE324363BEF7D35CCAD2ABCD4072FEF3B96D2073FB5FAA420DE091919EEE8B85380AEF90F8CED204CE2F289ADB8F74BFC77D9562B6E5F5DB6460AE46618CFB380292B51420ABA0F0577E30CC24033AF510769271B24282CE93E3D59C74DC1ECFA836BAF76AD604CEC1CA08AB2F1F28BAEF5F2EC4A9354C44E992AFED3BEA20B27AC9ED985D784498A9904187F90CA24AB46BE9631E04FA25F9A123CD1B177992652A73DB6C9C00EA54EDA51451A7DAB0F68B1EC519F00023D1E55397726E8BC61772D78E000BCC70BB1BE020A0BA7650729BF81107A51B7C46EC03FB2D200102F66BE65D82B60F731FE0E40E89E7F98B06A2CE4DF958688CDB06B877B36B2788AC96EB9BB3245AE88535B44B3136FC1A1690071644FC6FB5AD97A778DF0AF970818BE89FD3417BE0875F28129AF2F611774746058D9305BF0E3AB63A0B159801B0182086229B2F8BC64B5F587F45D8D5A21789E5A17F4FABAF2A43FD1AA6639C85FAC4C3FCE47D01C38EB0CB272A014E0D693187EAF6158B4DE10812BFE26130101E3E18D8C286179F4C7B0A36161E723B2C20B02F74075D9949892BAE26DAC27D4898F798B2BC5F73FBA055BFF9A75E8195079234C3690F876303F9893DE22A11B7FB1EFCB65BB7189C265EFCF40564883C560C3E32C692A2CE0284D8AE00D42076481E50E095C8D0BAF559596ACE3ABD0B90022D6FB2B5AD4E17038E1E6E62B567D8EC036B77DBAE07AE0666FA7859D51C939C6D84CA9496073FF6D36713FF7DDD74784DCA345AF5831A514832314E4AA4B54EB799FF14B6455BCECB5850FFCED3ECBD132EAFA2FD69EAD315E4757C6EFE44D1315656C75AEC8435FBDCF18CF899D734E0987B49BB1B58CFD93786A6B237B4208C3E8F34D989AF6556AF4C5B2C836BA20B01B95DFBB29233BDCD99BDF1ED79477C95922488FCF2BA4A0DA68281741376581669230C96DEA244FFE7D50D547EC98191E7929135C4B2A3EA1A4437D5982296940F20DBE603FD192D7F6620E20C09FDB3439174874E55789AA34B1D59CAF08F5F7F69F2335E57465D6B7421311ED834C0C506C68D50089DEE66DE3FC833205906F6CC4CEE5F160DA5AB7AFC6D3D51B0D1981DAF387310BABBDCC50121F4E1A158457BC4AF322CAC2DFC468D92D5F76FF1FD55DB87357034EBA7B22CE1284DCD39188F64CC5B6E69869C9E5D3E0F2B382CB933230D32A8E231960A9BCB5D12BBF7BE03D351830D53213FC8CAD1D08E26419CE28B0A9D5FC2AE7BF86CC5F62F8A9F0520D5BB186CC7BF60C83D3CD77953DD8B3F87A9464196828F8EDBCB69BAE2C5C790D2426C7751EEAF41B85DD58B25316B14AFDC464E6B78BA8607CF72145C055196580E407C8E2B45BCFE608D5D8E15E6BD408E2BC84F96FBB181A3B547BA172FDEE386126148410AF7596F7E72F76D3D9AF6348D7BA75A4B9EFAD6B44A8B4521FA1FC517B689D39EE4D81A7E1D425EAD09EBD8EF9B87500B81EB457AEACF1F872E5E82A74782A500C1E9C236D45D58CE6031E3649604D572F9721F96612489842BBA4413F4E9A44E8CFE5A43AA2F3AE66CE08B482F24E49266634EF87DA81CB24F28B7B3368DBD5FA076FD48960F60DBD25217AF65D2CDCC21C9A33038EB62320A5B3C2608C97142A5320133A7F6F6CC3952609BA567C99CD229C8D62B02090F290CF51BEF08D18FBD7E768847EB1D5BE6FCF6923E77C46116BBB71E57E3D1ACA0ACF51D85B51EB6674C93E934A128658D946987C978A4E3485AD365EA8C6D45A93373B63D02DAC61A1476BA089EABA50B40E3DCA97C9A9F258830BC8500DB388F75AC736FC8726026EEE04BD6AEDB86AE667E14E161331646B8C1C3050FC3F634D967D5878F992CA39E4AF0585C92ABACF3D0C65240B34604589E408FE3C5463689FB31A8EE1EAE9C8B09DA0CC08A224D19C95F0AE96EC8E4C25EA3CB6BEA8B97FB5190F8736519C5F14149029A333BD7018EAEF1DB96F18FAD7DCB68A5BAF5546132E7C17E761240702536F8D70959597A269686101B0C15112CB96935577B4AA6224DAF51C978FC3F734E1EE30E2AE0DB91EA2A067413964A4F6EF0364BD0264E61D3B5916AF4FB8C76365F91996C2BD89C8DA9509B1F362B1BCEB971022A3D2A180F1C5BD1441D8CCF5865B818BAD130DF6F796948A74674F7F38EF711B8243F8C49342DAB5DA12F0C9C124661D7C5697B55431225DE266685ECC00935086948FBB857E269D7E8C5BAA8D22630EB8A49A10627EFC352BA0D5EF826FFFFF0160C693916A66714C99AA62CF061F894737167CA3FA0988B62545C58999AEB0BDA1999D7A3780B0B9038C0AEDF916B6F2750761DC0ADBFBF5B814161EC92B01332213554FB022C4EA098E1A000D80A45965BC18FC5E8F1E03B3C5DD8FE6E21F35017E47AF66816FE0483CF9A8D77E628DC3CE97619A52FDF4D1B0DDDB3EAED61ECF137041BE55600D15B875CA6C18B36E35FE13D3FF3852A526B54D1F039C13AACF5E98ECDE5AD62DF78BCFC808C94D0EF249CA5C202792B0B6536305398C041866CA8D92287355E4B68F4D3C115E24372A384ED32DB0ED6A00EF2CA10774F6827FEC0BFF0032D5E14B2998A0D5ED0E7C4093DC0B5061B4BE8003916090DBAC1256503463D2F3834CD7FFC3E82C5E97FD10AE601DA6F832C9EE69B03ACE3BD58B471467E21D38355C67E025688B14A2CB725C04F4A8C08C0C0B797DAC26CEEF42F16AE622E687F02653C66D81AD440C417D95A58309469CB95191B2F2C00FA989F54C23A88CA07F3F222AB5BAE4BC46FAC92C57EE2C12635E74329ABAC249874802EEC88B21DB91A79A17E4D0C864ADD0CAFD778DB702222C222F136F9D1093DB496B8F631C6A1E556D77DEAFCAFCF64EA7B613AAD960359C01CE74DC4D1FC43208C3FD84BBE6DFDA0E5980AB5797D8F5031E9A54661A5C5B94C867C3195F2DFFFDE252736F7FF8718E40B499C2FB3B550B568A6C515C5FAC2883AF46CF4E64464EE5F0CA4A605E50C6E8D0278C6939664626455E7AB2984017EFC901B2BD1341D4FA7F0588176DC14DB23FB6C0F04C1D1AD5984D470B77E1EBC2C84718BC8435E00635B83A9E3EDF54EFB1575F5F7FAB73ACB49AE34C5050275A85E7BECB6951113090AA12EACA249D2E670A01E1392B0A8EBF62AA97C625F0B36B04118979CED2FB465D438D81FDCE50654BDFB3911F7AAD7D7825BB9C1E99ED6C9EB03D2D17024975BC5D6E925D59CD2751484BA1718A267F64A4BE05BCF554262914835E2CD0EE1F03FA8CDA0F120BF96687BEA0848D59B25AD60B59FFE1DE9DB33C6599F870F0BCB273267CA011E8561909D4FB659D13EAC9BDFA04DFDEE484FB936B694E2D0E4A1F20961A0FDD1F8A0BC4F7CD5714AA8DF0CE976FA6A12FF26A2131557107B75A2B886BF85CB74E80DCB2BC09EE9B79A926468CC43EACC5B05B1009F3CD502F967E2D5B4F8C78DC19E646BA95A802A072212FF2C3FD22EDFEFD674E825E13BC96984EAB2DCBF586F6DDC07A4009AC7C8CEC9C77CD22C6615D719D59C93C66CFAF0824BD0421974CB8970380D1EF77FF5A4378B00EF5FDB75A6829859D173F5CF5833582AD66E382509E28B08279EC33FC8BE5036502BBBB293483ED9258667C316847CE1084D78DFF1466782F1FC45489355FA233224A17331EB427C33E0E7A3C70C7B1D80DA63E19D316E7B2F490090A27A29B0415275B0B5C7B590C64F4BF0B11D38792CA0D133EA54AF6E90B42E9D5D8326BD5999920E9638051F085D3CBC51E3905F188C17D52564EC069AEA20F171774AAB7449463C4F414B9ECAB50B5FB44D9E72470D450FABC5206A6A3217164CB1F5DF397BE5F2B2DD6493878F9CB25A2F09AE02778DAC5E6C7352FC857F2E1763F300625208DD7E30F0F6239BE00216A982F4C3B3C856CEE5DA3AAC526A04D75792CE40816C87B37629FF990AB20C8404DCB4D15C4EE3191B8D770D15B5DC04ACCBC986C926A2CBF0D705B835CC9F2D860558CC6CE5188AAD3B8FA87E4577286815802409E8AAE82850D8F6CA3C1B17CE357982B9E7FABB661F799629F74C67FCBF82EA58C4E6D0B486F48C758FC0702034A9FAD47C475E4AD58CD363524314F72B5B098944BF54A4CA57AA5223386F88763F8F2CE8D9E77583B2F2515FFA1EFAA2C3CFFD6ACC5BF429E8997620ADC24E8A20C6EEDA2BCE595E03C2402905813F8501C5105A21CAA943064EBC47A0ABA6B0259833D510304E4B449FB8EBE6D645E7F739609111730B74FEF0D026AB6122E81F2FC4B12A56B9171470E4F137B01242D05FA5E6DD41138F54BF5BAF34A52054F5F0A65C60E6B60D77785285CECD3AB160E8197DCB1A1F3DBA70E17FE2A98471B5B6B19630D0B2CF63FBBF72AB36E4BEDA9965BBD700AA394661DAE4C56CE33937C2857E8677335CC1FD476DA445CA209B66D0819109191601B5F2F0AAABE3ACFBE8C740028200D8A14CC82A103C9BF5591592FD546BF3A3A3EF44D2340F45A40FD059F45B589F82BDD46E6DA2B0C2B22E2E83FF6884234C2D705E3EE5C4061A60D5AC40E4441969C9F6B66049D53BB220B9A6A019674860E1E027C2ECDFD5391CB77B9926E5A3C43471E3073BBF9558D63ACA3A46A483F9D1CA2A4FAB8AC141FDAD1A7220272D36C5E6A88B06436896CB0B5E2A784610845410FC89EF567CFFE06AF63F351A5D27D312B39C693AFB2502182BF59A211D12FDFA67D75A9F2210956248B81820FB4A1DA6C1B7EC9CC53E5CDCA3C61DD7A494C3400A1FC1AF528C44F88C28C6D7FDAD824C99878B27EF236AE8DCD913C3B3F8FDE1DFA81DF2F63D2460C173B6824D38F8EDA4C244FD905734530C1326C0D4AD7955D9304C72F9D633577637B8C464C8B4E0EDFED74A0B8542501482CB9FBD94A613E4D3D4D99EF140191BD10CD85D059613FD52427D3BB49DA44A3E7D7C3408F632970C0693CBBEB2B8EDB5631686C14CF63AB7A2B9AD4DBECA322A36296786DB6734A34D0659879DA329A2351BCA9797B77546BFAA1B04C87FC37B1C728EB849CA1A0C3F68F2D034629F252A400718473C0362B05CE2A2490DB7E96ECEF15FF62E38A5DC75D76F16AE646F83DE28F13F8057CBC38185E7900A606175EAA3A52B8ADCD2CC453B8476E9E8B6DBF7089145C4A39AD74ABE177ACC8CBE101FC878881CF067753AB7F975D1219C16660B35F5FAEF9012363E9EC29DF2D09F0BA8FC89FDD8B7DB622C0A053F97891770C2E3373FBD81F2D7B2935A568F0D9903E68E85FA15E5FC01AAEC50EEF20FB392A12CCD8D0D1AD58AA7480A5BEF5B2F0C47B2B00CC584DFC4037DF984AF659B1B4A7C6F0734C8BF8E3C4F3BF700F412F57E62B15B30C21793D28F7FC245DF44F2A26921E675F950270F07EEB34B72BE6A0D2FDF88CE9D138DA8258E16F81B9B73D8A4F0B9589ED64900D17524235F117561D0AD3189D63EF2E82F925262BD1E865452971CDFA974C0FE0C42FA2A2D53F9FB94DB33C356C78A1BDC18FB4F5A4BF741BC1A9B9C7294599946D26D92701F7865C60B872AC79C65F2292F01BE08EE044EF58D87616F3781682A06EE4538543F506DF811DC7AFFD9F8C6F67FA1C2F9720D9488C69299CEA57837636E088324CAA94F08F9E7090ECF796F9095E44A5D8758A39F9617DDCC2079196F577568A00A135D191A597E8DB90086887B8B9CBBC91BE1E66DA551E68DA9989AD4ED416CD47C07517185C8933B28D2D73EA86C6827727CB153022ABD1ED908465B17C8FEEDDFB851B818B66229850EBC774BF14A399D52184B758CD59D3ABBA5F3446204FCDE52477ADD0A017082F750C9A664693EDF0AEA19BDDEC7C907C531D2E78A9E3D49F7A0536A077C80305900E0597931B15C55DC9F7856FC2130D7C46A977441329AD753D1C2F917F81E59636C4D4C37C39AD2BC04BFFFC8AE7DB7EA4BD70F34A17800739F417613F095322A3347F30AC09515C288201A60752AF11B5D61E4B891808DB8EBE413BEB365ABED5CE2F889BCBCC16791846679FA218A18253ED4B0F35C46A374AE8A02A9A0AD1AC380A44C4A32BD6146FEE77081DD7BC4BF5B4B3440F1478558ED1E95E4ED659C8B1E8790BE099D4B95460FBD7156C590E2CDB65B261BA67294CFA8C8DD4AFC034B2A016FB5392664F2D9EE17BA9DA6D2F4BE2569661A740038F1CC3D8AAE41C9ED94E60653A5EE6E76B415FCF02ACBF30D8FA8421877423AE42B3C56008E595051FD3CB6FF43BD83223DFACB663307C56935F145711679CEBEF5099143E3CD09F6E264245B500D2DFC01929BE829C3ADE74016B6D56AEF809B4A0145873BD99C9DBA96674FE4A19BB54680CCCFD126B0BB6E08C2340C7C48D83C9E01FD8026160773EE827CA14AAF8D4227A300EC676BBCA983CE5603AFDD55AC2581A9100C294FDBD30B2A81AFC8CC591195542D9EC31C7A092CD2A887E52C675F44CD09147A3151E9817F95E21659AFB00880515C2FD887482E2229C6A867F97AD8F7B78D4C8D76FBEBB1FF21C56657EDC69EA0B0F44D73D95672F698072E2AE7B497C56367D5DD58306653698F8B54B6C79054FD6351F0F5D110B6D7F8FFB825EE03D6AEAF6EC859118F66116DAE1940DB5AD1A1F96E4F51CD0203259D3FA531B2747F6607356EB6DAB0D6873548F6730B5B29118560D5DD6D2971A2DF7C1468AB394166F7F331AEE0DA15E0DF0281CCDD96B4E56D0409344089E5CFA4C68B565C23A1C42F495FB68FD4D98B212A20F4C1376A246C77624D7232D274101ECB5DD2FA7B466B3B42F1AD4E3434904437A5BD7746019D015A2A73204672DE737F18781CD522E7FF6B3F9AC082D9AFEB0634E29C57619E8485A0FF68C459A2F9E130383619C5D07F79119F20BFCD335C39BEF3016E0FBE8DC45F38376A2E9B819044D5C29297201DA6311E76AB2A43A6CF6C087466FCA62D1FB7294F70A9477DE024C1F52BCE471C06F807A2D3095C21D655F7FD22C388864FF41465D71DD6352AA5F3D2A5B61867C1EA5DF470B832C06F0B09ED2373E9C3E4BAEEF218306F473A2B681F62843D81EA16CA72DB51889A07CFACF5D52DC0BD0C64C7A30F82FF697C342A641B26953632EA97401450D68047E381CEB10666953FE0E4BE9729AAD9D17FD24D4FB4E0CBB5A4AB38E5622CABBC4E9E7E8E2811AA122BB180F81B78E08F250A2FE682A478DA8B7BE80F4B010DA0C5814BA6ECBE48A967B2CEB0F873A6B469E3186A641AB6341B4B61C1F79D6E133A04B38A08C4C38FB7F7AF57A13FE1CFCA008F0C0D0CE7B76928665CA9CA71AD80A924889C2674429F5F539ED44BE1D7DB3B3A3B23F1E2D23CD5D83B806956E8056317CAD7304534FD6C4B6159EF84E81AF9D8033C7937C8557D3843A1B15F03F10BCBD76224A46A7A5B1FCB46A21DD3ED31B29AA420CBDF8DBE260DF11C3BB4E948584E7A1EE2B25DA262A50574D44239B10DDD5CDEEAAFA52BEA174A39E806CF189806A0A6728BD0B0E37F24BF5483A692B0F8D72E58D914295744CCBE3E301AF80C5375C7E546E96ADBD2A218758CB79D168FFE953BB798CAEA66C795133DE33D41EC6AB7983AC2F1AD19EFD34939F122A72788B3980CFC9E796F5628A381D53B4DCAAC823F298901F5CEAD8205547C100545C0FDB76BC5AFD17FEF5F8A0948840DDE3D846FE0577788416569CF4663750979DB3027BEC21B337FF01588C69E205AEF7623CE9F9237E4C5113941146DA3B40DAB4985ABBAEE517C64AFF4C520D63418B473C7BB453D9138E84DF33D02DFCE09974C3CED628D62CE212C3D931580A94020EDDDC1EE56E76360B624180A75BB4CEBDA4E11BC1A3CB148FEC1FF6525DDE8550FD9FC46DEECA555824089CA689B78BC754CF795EC896199D8B7ED5CB48392F1928020564EFDB87D7D3FDE2B883731C9841F49AFF183BBE8DED642BF5FD5AC2B530424E0EAB2622E5344751181736653B6E7179024797DC7204A758A551C4B83A28F1F8451FEBA8F80B0AD266472C3ED5BDA06EA809BDD38BDB2059208D93FB6509DF3052EFC00A35855D7DDEAFFCE7AD631F33DFDAC3D65B42EE8A8002A32876DA3E6A21879BC46CDB25B089756EBD0C7140BCE4799158DCE02BF3D87D3E4D46AF34802C42391ECC40EE0FF7AEB71AC8CB25B092B2CB88BC44A6B28F6B016D2CA07ABB1A4EDC9C15BF765A7A5A04592209C4F2A0CCB604F8175997A3B10B8C078E7C6122FC7219780A5656687CEED6A4DE29E00D9B97AF76192A216DB5737C593872615530B329A3B910C936440F78712F4473727D262A44ADE9ADF44EA758A7A106AE93AE831D06568CD38CB1BF5AD34B2FE82BC5A40D3D18A9F5306BA0E57BCEAFD87E39F2FED0FB1D1950AB9F0068F91612A05A68170AAEE56F32E9F9056D0D18080FCAAE4A033CBF263FF5B7FCF2380577DDE321807936069B4BB5A1450711A086924CE5F7481546EE3A77EBAC8D36E057041F27663A43B2E1A3AB4C356D6BB721021BD5ED88F03810DC05A10462189250CE91D3564776959BADDBF4A5D696A9E76B5F860C93ED94A48F460BDDEFFA660018ED98C710BAEC7171420B1D7FCCA1C1284370CF238A2FFD8A83A7DBE73D7CD52868C8D6AB9EE232957324178B7FC78978A24D5579E399BE9EAE1A137A799BC5868F38B114BD6DB1B97CBF65FB4227B5C1577F951202B2068A66CE492263AFA9A92FEAF958D8AA0A6228D69EEE0A4BF9AA722ACEDF16A99F0210979FED7A4E6C280A1B0C0F6881659AA794E4E5545B616E738FC81E3955F8E747796544BC93F94D36F5DAF6F9AF2792643D1CBB708CC70BC786D0B5DD7FC81884F61EACB633C60BCD8E46924B7B006BF3C19818271D588DF0B7E60C27BD67FE3F557E7E7732230F8B6B2A82287D63223A2079111B90A0D5248ECE64CE5795FA272FA120155F996B25FE3EEA8C11E220A4C1312CCEE29D815AE8B45CA533097C66FAF2DFB47524B4E91E90CD49D36506E5D89D7EF6B93E09DB5029AD0689D418CC97B4CE0BB755C470B9468041232A2386DEDD01CABE9B3EF97C931C9E6239A585FBCE95B99CB9A1DA0B0AB8BDDD00FC6245DC70718A38BDA694C302A04B6442914DA7AA920E9CC7D96F7FC9E6FCB6D0EE066E507BD19C6BBDA5F5ECADF1C0A2C355C64FC389AF90543737F15FE62B32DE4F73823AAD187826B691270CFFD28836AADA45E7E755846FC74C26279C33F2C5CAECAF9EE0B130F91E9B76841EEF83AE70C5C6EB98E487D9CBAB0F7C2C9573A1149B1D4E4D39B65339981C8B2C3B1E2EEE4B01A714398E48AF338B3363690C6B3D82FCED029C8B9C6F5911F07743084DD822F4F3BDF4978889A002C441EA2FAE3493CE6A156BDABBC869F2F234456D0322CDB7E22DB30AD7B8B66EABA7A4181444429DA347FDF22DA4462370E20A951D486BBDE3E449DF561561DECDB8F776BF058869889F2E460898422AD11992C9ED2E47D180B471E1E85918D5EB8038024F05E970E1BBA0EC97704B63CB6898E96F67D86C121F1F2CC7674A870CE5FFBD2478C18C23F02054B43F2ECDF4A26E3A6B57AB0C4FF9575996575A156197B67EDC3A4C9DBACB7FF054A469032253E8562D4A7AEAB27441487D0992AB4E434EFE9AB2A339024854E475F84561FD245206A48C8485F35341D7083FB02A20BC13BCCF704650B1D6815D638024E9F363984938BD90B62E14C64AE3C28C80B0AEF45C9303F16ECBFF31E13AAD79923215C4EE0E61B5AFC0753E38FA9244BBB4BDD169E602D86C8707609DDBD7827C20506B7A4739813D11135D92FD6FA4842EC1E1D59D1F2AC9A5A7E39B05E0BCB8BF5CACDA7DDEC579D4BB0B26B0F164C3DB7BA62129497E6742E973EBE570D388BC1F26FE1DB41C327D7A04DEE71BDF4C5D83D6E4CDC5EE630BCA00E0BBF365440B7F3F3784FDEA7E993F40F86EE3533659029376DF89EC267AE2D4BF30C5D07D863A78E4DA8CDD18856EB3AA28DBD843926CB1DB7CAA76D4AB3F8C1D51F1B8C8637D1584A6E357A757DE84FB596BC17A3BDEFF71E77BAFF8689A2B55568BC4B355718C4E5DCD4B228684E580A9E9548E3EC237AF3F2DFDCFF0A4493EFEBB07687488FE41CBF88F820C97826445541D9472A91B01F48909322DAA3942732859C62EA79320E43727C47EB845A4B22E7C31457076F1B7E2D3050829B8106519025370EA6D99B6C32ED9BA8D396B02876E72D91D97D44D27AA49F4209CEE647BC0325844D78D38C4C0C4251D01CD029F0F8ACFF7639D9060864D0080ADEC4EAE1CCA0ED654211C75415032C77B990BA9B7BF3A9AEFE64373230DCA5B6291501999636284DF4B52A40107606CF130D18FB1D2D1B599B90B4E1218DC377D3A031811E153E13729ADEBB2248F68D5308B37E3EA0D929EDE81CBC9D47F754E8E8C6F16217A502A29C2E1148EFB571B77B52756F7E9F68C7B850DBD29355B33BAA9BA7D21F5375BCAC961F8CE8465E35F6231AC4A5FFE485AC255D8C8120790ED987D64575556B1C45A11DE07CB75D02ACD0134F4BA3707B8AD3E2659C390F1AE845381C1FBCD278F67E284DED514ADB6814E50D22B212346D9AFDEB97FB31AB6B248EFE311E0F1DF0D549F794C8D5589D086B2D8F557FEBDB14A0A3BB9DA0E883E26266EBF1DE03A76D047C7024C3E237E72A3B642941A2513C4D3C1385D7117CF21CF3C2627775985D2CAA070DFF327181D3703E7FB45837D78C49E1840F550AC1751F9AF51C11FA6E224964C24D1CA812B16F57EE781B45F1B30A007FD34C8AD95841B21DA06C568ACD2C80830D23471F0E46A643A4BC3E4314CB828EB16A7AB586947C9DA2CED0AD2126082C36F520D275A766E65B9B2B6911ADC5100471F283C5F3309B95A76A388E9C884BC4A94AAE4EB702AD33A2128A8C098DB08ED3518D9AE2C9BA93F52C58AC609732ACB0F28E556EF9F2DF177B3A861E1A593BE04AF4CD8B54AC42F79708E8ECC7CE11CE86FE4C68BDE4AA14D931EFAA176DBA93CA8CE476335C892E0C97C01DA89DA96197556C17886D2212FBD8B5F4073B3FCD0AB62138F2624901C9D31787F4B38F68042E1B9BC7AB0007A8B9E0434D0E0760CD992C1BFDE5030E2046B36A57F81B1188B259FA01C729812074AED9F77AA2A7486651414D008CE65543C124258878983DC39D297124DE43675CA2BB547AB07D2D138899AAB86B1FB4A034BB5DD8AD9FF93E1FF7F4EF22F7B773FF4F7860A37051EC8099FD47FFFE9754B01227115D2934DB2B30D03A51EB269CEEC322ECC79FB6804259A3CCA7F8E56FDBA44DBC6E61D3D5508AB00F31845DC045785EC4BEA7E9201F6DF5DC8EFCA49145C862C069EA2E241F46DCCAC4DD9A864546CF7C0616A59A80E3C8F25513F3356B5F701C9AB9895088A88B6CED2A14D450F8B0CBC0A0818132247D52D6E4FB951CA7A3F119E74B5F40905BDCAED41B5E5AF49ED960121DFB1EEB39856CF2AAF95FAA93E46D0CBEEDA0ADFE2EABE14C5EA20AE8E108BF7BFCE270ADDEC0AF747BFC8DA4B677665DB2878A8C9C4D8C84F07BC510BF55FD6AA53EB40024E8072C85B556A2E585F09E19271734C2DCE0F167A7150E305198F7C66265DDF59E57EAB4430213763B7E6244A01BB53C628D541D8A8F1EB3EBD6847D949D2A0A2D6367446DADD3234A7DA727BF4C02C3266AFB7022714E5381ADC07C257E8D0AFBF1CFF55AEE4FC424991CCD999C1BCBAD6D026AA22CA76F4CE05DC746A7129F7AF184AEC5966CC16A6E1618BF2E7DC8BF7714B432A17F40D6B17A98D389FED717F8EC1559B11D7A13320158ACB5BDEA977936BD2C173FEDC1C5D5AC5A420BC52E46BB75EA733E9CA12E03490AE9A57B8EB4755233316EC64364570D61DE42D1C5D9E75191010C7ED45AEE1AD738777BF5113AC220796A73C92ED5F56CFF4B9959EEEC240B2A39245609723BC5952DB3739E183489ADF5147B61201E0681B60348AB28C49E660792386E81F80C8A41149558A106AD0A47FD6A9341296380978C28508160812CA8018E8C302B42FA7FB189435326694A50590020F9AA0CBD8FF660DB20CCA908708C748873E44A2CAD86B9809643688E8304A97EA1149AC7E0B96A52C9FF64C9B4063DFF08641C75DC575C764F2A3611EA3D5F05236813D09BDD1F221A0A5F5B0BE0749413D9BEB064351751868D1396DD9829C237C7AF28D927D6E551A797374A63F18E2A668FDF7FF534F1A57542EE512D2414324E75D25BC5F246BD1908A174EEC9D6960B637AF4D156ABF42C17CB541B3B5D81FB44A78111CDBA410D929C1CB2BFFD937B30C82082647B2791EFAC1DAA958D8FDAB8C2400BD80EF0FC487F4D7BC59E461A0F73AB05EF9D64ABBB40882BAC1CAD05EA129AC455C651B815EBE68C6FBBE6BEFC7EAAB8EF9E4E2B17AD7F8647B4E0C9AA844534B737739A5BBE833948B98A4342F6F3757955CC6CA6C91DC170F2EF70859D541352042371B9A407D018BCB7BD702C44F409FB68D0D2A42CE015923EC771D7F63CC7EB96B382681AB8CD569674A0A71D07F7888BC2D0A8EAED969A908F7E9EC655710D42FF1A0F26C8F1182732E383D92F0CF7079FD378AE09D80968B4B8D50E6CA3F53310AE254775B189220A073252B0FDA10DCE4DD2A152D7255BEB6004341277B2693C3B7ECC02D835A644A0D7B1C5ADB3B4FDA0BBBAC71E90718A675168D73958BE20BB4318954B1300C15429A83A73DBC935285CE833C28356370580DCE4069FE604417282201D47BB4A26CC06A0B8FA0FD24B29912133179359968B00469D33E8D396FC8BECDEE7C5BE481D5B6842DD6C8A85FD687AA2F13AE625BEAA213DE1A4553A07143BE6F4E7989617D4C4855CEEBFA82A59276BD270CE719C3567A436DB5ED92DFE58CB418701BBACFA85A90125CAC86E1C9CEDAF4DE818885ABB83AE5C0D8944CA6B48AB427D29A7EC9475D56D4B12D8C8445D917C452272340A4EE3E2765F4429BF5C36BFFA49499295D63356D07B358F5F4C38D512BD49F210EFEF59A8FAAF414085F8C7FBB682EE466006F793047A696690D0C54C40BD570135AF0C3E5C4295B62D98B66C08F66D95DC203FCB34FC603DAA24B61C9B6BD1AED944131FBE47227F4ABAC1D89ABDBA0E251957647CB6E70A31340426C683BCEFD5E064296656ED1B9B932A6F293A5357490B85984D24E0EBC0B18BBB0805BEF52BCC59F4D8375008895942D9355438C2729ED20FAF7D0349AE283104C3AFA93E9DC0F1D9D1E73FAAF1138E64844EC1F2F41C8563EE068481C48BCD3E4B2411CAD527FE3D19CDBFF834A83ABF8A1F3BCB64A98800C55E90E6979E37797597B655E07860D0BC1C3AADA48517472A4D282A5367A4636AADE64D4FBDB1D93EEAC6EE4ACC03B63CFF0E450DA11BD572194D03CD3E307E89E37D37911D41329CDC200B4DE6D85E4556DA1AC28D24692B1CA8BDE204824E120726037F84CFE5C6982A8EC59EBB39726AC86128C84237D8C85BA84300A3BC015B2FD7FC30CB6336A8562392397409DA85C65048E30CA235671048D57196FDEA2DF19F52C7307452D175C148D0AD8C6C315DEA8545577889C01BF96597C2A8001D6A951F7E3B3DAC5E7D0CE7AD7542DE15CC9BD3E59633BF0BDCFB6029F2E31DADF5D29CBEE0D90074E0AB6A37DA19F9F61BF6B4931E1E8DFA6EDF977536C09B0F72A86A09DA79C46C55F09D8A5E5CC4DC9DC7645D63A6888B3D52B8FAEEEB5B71115BA4F1BCA1A6F5AEA56570623F1936B41DA3A277C7D98A83B4CBA9B6B466A3A5980F2A58F7BD58B3832F49CA95382D1353BD4403AECBEAC905DF1FC635BDB0F72FC6E0D01896A1236B93CE8CB4B44DF762ADF0DD9D20103D154DD5DDD73E90102C53B64C3DCEE888F0C98D756EFBF61F7F5DDDA6AAC9C2F03E658755AAF490DBA38932FCEBE25914400D0DC9488351223D402D1B5625A2AF7167A8AFD7EC51E3AFAAEBFF85138D61AFC1AEE6D4D248D6B1ABA34E9124DF09333C74DF1B80FEA9727A20175C056BEBFFBBE784EEFFAA9698DA37CFA544A1893BD48F03BEEFBF4EECCA8105EC7E4563C67E24D77F71AD3F233383D0E22E74749B2B716A98683BEEDD1291EFB4606E4040B5875CE3989B893A7EDB44815E51F720F85A408AE1293B69AF19E2132E4398D8616EBFE03D3BCD1D57CF94976A8B83C69FBAAE1DC0C56FA39673F1897781FDEE785C45679BBD324E31083951D09AAC5176CCF86FDACE778825325F5AA867A18BDAACCD2E58B69D82BA37B7468F1AEC55EE35E7552F873EE75B04A3EA9C704BC66D5CC6C08FC0501CFAB058EEE1AEE1646789C9018FAD593C4812A8568AFC5F36CBFD141599D5C9939C310FF5EF5412C0CB9A366735674ECD1D89AB86F14DD485EAE64E6456B8D621704FAB4B9818842BAA9CDC3AD56CEA375A443CC0053CB45251CF1240F39CE2F325FC24006E8F8216940A49FB5A7C90C80FD7988358E7B8FAA52BECBB61BC034BB770F0308B012E1770F9DEC2C011B5B9A0CA792DB0EAB08F625D710870707E3B3F5C3998ECF954D08E24F53EC565EEDF690FAFBA9655FE68A07AC74BE73D0C45A693D94EAE607B4DEFA75DE498EE47B268E839701EE1293CA692DB1EF72087FA83043D6772142935313D02F0B45DC20ABF1C474F3E0DC9A691DE0E6DD1C4A0FFBCD84A50780CD66465BE3A2F1E174BC6309270B2F93464C631CB0A9FC9232D777564616E7C834A9ACC21F06838F0F1C025E6AA105B2656AD2FD279BE084E779E60E4E6FFECFC8F44BD9C267B8CE7DBBCCF9BB1D178F5849495635189B20110DF5F930D3FA729E12DF3251C2ED98B95A33020D23B981F83943C36FDB611090A905D5174060B0A294DB51AD7254E93694A9E49FAE168BC9FF41256A070BC27CC58B728AE0060EB716AE9140778CA2EDC7E025A56AE658A7BF9F50E3360040C0A6B61EDB668610CB3B771721C7FBDBF6C61F19990E15BE3FDBAD60923069D11AA1D06F851AAC48ABBEA5F8CB32F66F5D4FE5BFBDEB42053DE2B352ED67ABDFC87729419A70AF5821BBD8B2C84928F3F80D441A2E08CE2C4680596A488AF32820FD052D902C2484F5809DD9AB0346C2CE28412F0270F8F88F2719F6F78D887730B7BBBA4CBB95CFD74FA859CD33656C8B4591F2BDD20CF043CFB89676E3B3DB0CC71D0D95DE0075B9F40767A6971A09D9C874A484ADE40DA12B2E59CD52CAA740D5CDF58F2F1A093EDF8D740E1A9A9D1AA4272CECD5E9CCD362B4AC89CBD1CC29AD39E600B09AC24BB64F8E323A937A2901F5646E3FBEC9F628F0DE1F185F70A296FA9063C2CC6CAE94BB39FB49E3FBD69CBF6C036CBD6ACFFB65D31477F8776B2A703CF129E7DE803BCCA996C57D6E71D768BFE5F143C0282F19CCEC530873ED14130087EB7630241F6E3EE0992E6C182F3E93E78A14EE7B2AC0B81E51A2928D4E488E641F27AD66471E8ADFA13CB825C0AEF7EA023FD8BFFE1BE24D261C23F6E73D98F22104E545FBF9329C00ABC63A6865BD2AB22A0531DAB2851ADD6AC6C38A95A70DDB35791BCC9344AC8623F7E415B6FB62D3D01C8C7B318D36E77C0F8DF30E170A945E04EC37BEDEE22C4287A26E14388C1F93783A8B8061E718499EE42DA80A5DC9A81724F39D32E65F1C2F30D5A27C2E074D75D18D95847E210C5A52A443F58DB1750C0F14B56F9539F749AD7401599BE0114DA3F3911E345944DC0CCC77FB7CA9971C45FD8EEBEEF2F981D69DAAF627E41A4DFF5558719E9E7E6CF1332E117FC6D19476A1DD50EC20BE2DE9166BAE09D56CCDFA3B8704B3B29DACFDB3945D02D4FD60498508447C5B62003DC27B2AE41FE59BABC509DB965CF024EFD5D8372E702DF3023369F1E1EBEDD65F02F9E7660EA09C5B06DF68DF592A04262F2D635199823EBDBD93B2D6DC7E17CF0B0EBB53AD75222BFC3C005F77C37D041813AAAE5D61F8C4A3A648CFAB8263D1A5279EAB3F5662786F4B74D2F043ED4AD5C605132C40DD126765146D13A7AF7DAAA04A4ADBFFB373C1B9FEF5540157D68DAA73845BD751997370277502FF1C7DD9E1253D6A7E941FD20F51ABFCB7A5FAE10F858C4E54B1BCBC3193951A0C2CC2407072E1DA0CFA2D1281FCEEB86C3F7D63324B7B1EF175285C17E6ABC5F125010CF27F231B919603566164E964835E56B49FD7347B8D55851D0959A6360E2273C2F27CF444B80E1FDA86490BB8EEF1185A7EA6954C32BAAF6351CEFC87A0001F552F26E09F2111A39E1A1DB23586A80C0B63E4DBEA9C353CB7F9713F7D88AD0CF7293006EF45485E74A85C04C520E9032497811787C17A79CF6D5038A6EB4CEB30855E395F80636F002C2B9A0AA2FA00FA047ED19968F1B89E3F5443665E3F5C4994FCF0BD3390F2EE363B39018A533540409310138E68A206CBDD85A75CA9D8D85E5FAE613D3251F0F1AA5C070B7970E1CF57D95D0B2340EFEE429E2C75C7E127AB8A97FA5EA3B5DAE2D2FBD78674F0B956C7A76BF5EBA62FE5C923E69DA1C717F2417198DB0397B6A8074B7E654CB318A610C11380BE26C76591B85AEA4716738EBDAD25A4FF331700A446895A8C1092C29EDB9946FFDC92002FAA30F1359B1267C26441F521B1CF4176B6510A02D75DAEDC8906F37A457425649781F1B5AC1DD98212DB2BA11FFA9CF0341FC8463CD73876967E82D984CD79D6E69C88BFC211BE7214DBCFB3188F1917601C7A9EB858365EBAFE41C075C490A6D8F34226F117CB4AE562786851DC26B5BA713222DB7463521D1BB94D42523B02E6E80FE1E18BA337B6EE0A9E8B9A090521FF7E9B81CAD8183CD9BABDC9E306DA98F3A32C6271B2B875F831AF2D6305CE709120721AA839D4850D91A2E3F5736429FEA3A3975F4299BC44DC09E9A070755EC67B8C476E50FB773C9E2B25118D6556F94646797A105885E29605E61D8413D0C872C739B84D9C4A1E4F587B14731FED98C581BCEA74C7037B33228755C25F9F780F253F09687BFB9AE927CFDD6F83DC9FA5BB9804DD4423641BF87259832984EA3DFD7DCC0D63D98CEF485C938469E42B0C4CEF8B2CFF5BF025C272C323DE5625B42AE0AE06BC45C6D87F42440585C275BACBEF2DFC8D9B0ADD9D3822029EE2F27B54289781E547464389A13536E083AE2A0CDB17D4D601FD51766F039A0F064794840C55C16128F74F130BAF1F3EE82E7616BBA97626CC73FA36AFBF38A5F28FADC63B3421F730A2667600173D7CB038B2D0C209246B875482182F0D1C1789769B261ED2BB0639A1E401980EC4CAAEDA1491FFAF5F00E49847355D855284816396EC1545122FC8AAF8E5EB81EED40944251BC748564E44C96FC8D15F9395851191782A130706B6775A7C0A005ED348F8B8F728C109A7EFAD65250FF435DEDE4A3448EF1DC5C6CBC9B69B6FB10E34A0721EE53B08F5110ED8915FBCD4086B5178F54C30121E3F032839C95941DFA64429A27BF8862E13126591F040E4C4778830B9A9B2AFFAF1E5DF22997C1B550F943EA61695F1EB664B0491D832CB4CA4E1997AE3783CCD0CA5E57737692CB75F4AFA59A4C1ADEAD8D94BDFA9EDB5124BE26B3483D28A707F5108ABC59DA878198FCF1C5576087C508409F101CF2DBA48DE62348BD5F7FFEEBC7F4538EF9BCAD2BA712E67D9A4683F4CA5FAD8021BF3FB0D15911DA0F6893EA716128E0476A16A2325229D4A44AE9B421570FD35F9BD9918905A40C0EE3C7A8C002BAC136311975C22627CB839EF49B7AE93D706475B7B1A1FE90527ACC33BA5B6A4E9A4EDF69099D03B14BA57F039C2B21F52923316B025616B178C5A8E3AE2052520FF0C943365D4ED1D1D7ADAD6366246FE11FD58328CDBF9B94B55653274C9A67BE254AD1BEFA8E57EA9FECB8CB0C44C6860572AFD4B8AF67F3AE6EDAABED5120B5C14D64F8088C77E569839F84212691F948C623982D204E2909E0D469EFBB3572B7E59AB8822E52CD06C51FA5F683FF887AE3E04ACC90DCB75A1EB0751C4249705A8171ECF48AD2D0E1E6D926DCDA413EC21EB7A7349F6EC0B1402690978C6BE032E268449DBF26F4B7C4B5894B4FCF037BFD8C885C95CD053D6CC30F9BBA2F447696D4CFD0F78F5A79F1DF7A23840F38F3DFAEE031D01D6C97BAB9B9D18F9D772BE676639EDE4C77373FC259B858E97ACF17A5403EABA1729E46C4D3CB67358D582051EB6CA2A68D8DA36F9AD2A90F01E75F5253E8192C4B0DD86926994CB818B07B6DFFBCE102AA1CA4B263EF67136B0BB642628C23435CF71A4129F060CD7630E8F8750C2077DDB5C40D443D1C48CED191241CF1C7227C50CEB2C57BC1C0BCB8F3C8D41C96E133861BAE59E03E4E04B657D0B0FA34A8A5EEA74EF70DC93520E01E48EC9F8C851D9C668BF37E5C1261F30357BEAA5547B1E16BBA3C16139C735F9154D7E981D662A3DD52074FB87E6673C90FF0BDA55B3E825F2C709206FCC40011614F2660AADB895C3BE231D37E47B6033CE5FCBE0622FCD03C438E803748BF550C7763FB54D2BFD5CECCA9196D60A554777CCD5CBA3D77F9459B58401783526837B27FCEFBEBCAD869FF1B604639BDE7F4BE06A44E317A78ADAAAD0273A7D2C946CD7DC38CC320382687352C199505297B3FFCBC7403668D2DE60249C3AA4DAD3C81CCE149ECFE02648C3CBD7461E83E641F3ECA0DACB695BC6FBBCF8CC0BDEF1C8C85269823204E63CFABFBF191411368139C06406E42CFBA3517EEC38735689EEA7B9FF30B6CC80B217219221F050356EE3EEFC4D3780799A1729112C30EE5F991FA12F17D1A0A3CEF2C6B88EB7DCBBFFB12A5FA161B479E24E4FC25B9FBC7725A7595520CB2A8D2A7E60BEBC145BC50A849032321FC4B070FD12C3CB013648ECB23FC7BDF5CB90BEBF83785453045A4B71ADF1E6CBCA3A197D94DA4F36EA5EDCB1B3F8D5AEE7C414E5F51C5D119BBC533A23F345151B682B3183674D5302749F609D858C481772F554EFE52A7E43D88FB8036C1AE8BBF2D55735D63C2138E60BABA5A889AEC1C9C2EFAC2C3EF88AD03DA51CA4494A8DD297E5A49EA88D1272B7A7DFD9CBAB68497DA31123B3DDB5E3F143D75389ECAAFAA0458D7FFBFE96ADA091DB3853DF7904C8F47455DE2E33B60B3593ECF1C66E8D3E5E04A1A6711588D305F54583D1E4F17A083DB75CA164F1A756B86B2355356A31484ACFFDEA0EEEA9852F1D5B2FCE224AE6DDC21B94CC77457B448CEB5C20BF6FED246C55AB6CDD07F25C907000F24CBDE0BEEE7138003C170804B4A6A672ECDFF9CE172A86554D5BF7FEBA87ADD34E45CAE614D6C711187C8BDADFECB2461DABE19C47444F61BACEC09C0E91BA0C29E2556D2EE361ECA96E234CB9C51188FA3B1568E461C1003A2CB22B3EAB944897605A7447C58C4BE03ADCE589A652A8292A5F59514A197EAAA855C7727EAE076F0044F9205BE938C95C27CC070345D2B879E465D54DD16D866974865497CC300A61B52410E88946F22B7DA3B4DC72462BADC4D8154B8A768421F8FD8B5163419BAD5B0BFA36E449B7B59B513F1B5DEE1992B75539392DD4C29D3129D9E4D0B6A8A91D85618264517738E4D3184360F2209543DBCE091C2787D2FB7EC2CFF69BA670C740B88269B9F54DEF49755C658384DE55AB70B2C7B05B6A007650D7C464044B3BE8D996EAFE9650DAA8BC9B814EB7CE25E58C1FE80B503961E971E1DDD545A76B95A534B2407C48AF78482579D402255732E7ACD7020A60610D411A3316CAADC7B61AAB57A433928642890BC68541FC3ED8EC495CFC6F9DF9CE931BFF74E01657F1C4AE8D2DBFD251C5ED9BFBCC4819A3E53CFA78EC53139A61839FD5BA33E309FFA8FD3176E6C0C7DACEC85AE2AF8C619B01428826F4E6A33406575BCBBE08146D1BD2A357265AE64626F149493236E0C8E881C9C77064DCD12C9ABBAA3E42AB5F57F25A9F68CE620DC886302AE638C78B8FCA1CC0F9F85130A1E7A89DDC5717EC26CFEDDAD0766791D550DC07F3C2DF48A7B63F6C869498CB782FA10F3E8A13A3B5AFD10FDFE4764006AB5AEE00655A5F0E978331865D4F0A5FADB983046B550194559A107250C1392B9015392B02B540B2BB19F18EDD7A94B64E95024D0C2FFBCA4B088DECE90403ED53B42C2BB765968D50B9DC0D0CC9DD17940256748828F1EDE033432711003334CEF270B1876774935B5FC62657A3473DA82EE3388F60430F3918F834B918B2FC005C4EE32E8BDEA1A2B71A01EE01BDDB7EFD7B8ED13F03CA86D5E6A5D5053D2880F69EEFC3D3BBBA67B4F1208204A1104DF41B48C88971785531013F2CEA9E57D8384866E40E880AB814F8EE258B988082F019ACB849811DB1ED0D23613ED5FDA1CB0B5788DCDDAE1204119BD6923C3239049F293C73B5A2D9F65FF46994116A66E4C7EF6AB4EB1A71055A9C60F085CA77E1E1E1B66D00053C2EBD047C16EB0DF41F883A6B6971EE6466640D7C37F9E9E557AFF176015735417CEECA3BCCCD97DA0A2CF84FD69F9B3323A189AC65B8DC7BAFA57A425D579C78B37D4EE914A5F1B0645E9DDCBF329D553FCF8C1FA1716DFF3FC6A6DFFF3720A92F376766345DBF5331BE84D9055F8D5138DB5F394E1195290EB691AE4E863A306BC964DA3E0911BE4826FE07379B87CD045AE99CB49C3D2E13012BECE1E6E036BCCC469C15D39071FBAF27E944B1E58E5441222F3F64895A3890C73E0689273310DD937E3DD55E9D0E32789DECBD34AD5BA606C6F4ED28A150F5B4E97F083551DE7360408EB6BA8AC14ADB3AE17C061AF753E105B701A1A33143E56AFD5AD305FF8EDAC9DE69F137F26E4E6794A0AC3A87EB93A2DC64AA27B9D65185A22082E4832E1920BA2E85D7DC5ADA34F4EFC2B5F42A20E15BE0AA3EDB8C05CF32C5D9505DE5A6F7628E5D2A1610CFE5667D5ABE98034166A7E4D9AEFA4731B49A3346A3241D93D64005EAA5536680B9648BFB3BC50C2ACB490BCBCE281A23AEEC6125FECE1023CA7616E55617DE48B38B4F0C40DA6A315FD8E349ECAD2DEC762FCA6C35037ABA68DCA658A72F087166B80A462527B9DE2ADF0D253B79F6D134B34C0A4BD5FEA30543EFF20F84D9284351E7737BD305CCBDF6E03002044979D8B8E690EE1E84FA1DD4C97615B71BBE65E60A12E48FC5FD855602E137D35D35B7C4830B76B3E42CDE8D9C64B2464BE5DA316A04D50167D5853F76631A140C4CF8471935B4993A2F2FD4475EC75419BC4A97E184B5C2D0EFEA4327AAA10C6B7B878344AB14E1ECCE3A8A630CC2B21081CCAF552A2BADB893419F006EFC31208FECF01009AB285AA1222F6B79F1E8E881F24014AA5E356DAA4F8168098990C6568BF82A10626AE6BDFF83E2C8FEEE04438C2B94EEE19EE18CE2C6C9D7CA06C7300EE5AED0AF90ABF7959141D58BB91F85508BD65807DBEB1EBEE0F1791EE44C76A706F95DAF253C3D871BF69A1EC40E3A778AEEF2238A33FBD058FFDD2BEB5DBA543FDB58E07A688E5FFD062730D2A0FBB42CCFAD2E387D185602220D483DB3E844D75B809C169D6EC71ABF03D0F8C21C1BF1D38DCC3B97B09FE249534D3A89B3A86CF0DB16BD4E881DF428008595CFF53B9EDCB8AD730BF971B9556144C7ED40566C45D612CA88CA4EFB15DE5517EDDAE02B51524E8EBBD5D03B17B4B63B123B3B0750CC4CD9B1EF6C9F29E6423EBBCE284C4A741A3F70B4E6C7D126BD10C133C4DEFB59607A01E3DCC7DAF522CA8D1C8281BC4E9940DD5";

//		String designPicPath = "C:\\Users\\Administrator\\Desktop\\111\\cc.txt";
//		//读取配置文件信息
//		BufferedReader br = null;
//		StringBuffer sb = null;
//		try {
//			br = new BufferedReader(new InputStreamReader(new FileInputStream(designPicPath), "GBK")); //这里可以控制编码
//			sb = new StringBuffer();
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//		} catch (Exception e) {
////					logger.error("出现错误样板房配置文件读取失败{}",e);
//		} finally {
//			try {
//				if(null != br)
//					br.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		String context = sb.toString();
//		System.out.println(new DesignPlanServiceImpl().checkContext(context));
//	}

    @Override
	public ResponseEnvelope<DesignPlan> updatePlanConfig(LoginUser loginUser,Integer planId,Integer opType,String context,String bDirtyConfig,String msgId){

		DesignPlan designPlan = null;
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}

		try {
			StringBuffer checkContext = new StringBuffer(context);
			//校验配置文件是否有重复的posIndexPath|posName
			boolean contextBool = this.checkContext(checkContext.toString());
			if(!contextBool){
				return new ResponseEnvelope<DesignPlan>(false, "配置文件存在重复内容！", msgId);
			}
		}catch (Exception e){
			logger.error("校验配置文件是否有重复出错",e);
			return new ResponseEnvelope<DesignPlan>(false, "校验配置文件错误！", msgId);
		}

		try {
			String cacheEnable = Utils.getValue("redisCacheEnable", "0");
			if (opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
				designPlan = designPlanService.get(planId);
			} else {
				designPlan = optimizePlanService.getPlan(planId);
			}
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(planId);
				/* 设置设计方案ischange为1(设计方案被改变) */
			newDesignPlan.setIsChange(new Integer(1));
			if ("true".equals(bDirtyConfig.toLowerCase())) {
				newDesignPlan.setSceneModified(Utils.getCurrentTimeMillis());// 设计方案的配置文件发生改变要更新时间戳
				newDesignPlan.setVisible(PlanVisibleCode.DESIGN_VISIBLE);
			}
			ResDesign resDesign = null;
			logger.error("updatePlanConfig=>config ID=>"+designPlan.getConfigFileId());
			if (opType != null && opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
				designPlanService.update(newDesignPlan);
					/* 设置设计方案ischange为1(设计方案被改变)->end */
				// 解除planProductId设计产品的组(解组)->end

				resDesign = resDesignService.get(designPlan.getConfigFileId());
			} else {
				optimizePlanService.update(newDesignPlan);
				resDesign = optimizePlanService.get(designPlan.getConfigFileId());
			}

			/**
			 * 更新配置文件和设计方案产品列表中的挂节点信息 先更新数据库，再更新配置文件
			 **/
			boolean flag;
			if (opType != null && opType.intValue() == DesignPlanConstants.USER_RENDER.intValue()) {
				flag = designPlanService.updatePlanProductByConfig(context, planId,"true".equals(bDirtyConfig.toLowerCase())?true:false);
			}else {
				flag = optimizePlanService.updatePlanProductByConfig(context, planId);
			}
			
			logger.error("=======updatePlanConfig flag===" + flag);
			if (!flag) {
				return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
			} else {
				String filePath = FileUploadUtils.UPLOAD_ROOT + resDesign.getFilePath().replace("/", "\\");
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					filePath = FileUploadUtils.UPLOAD_ROOT + resDesign.getFilePath();
				}
				logger.error("=======updatePlanConfig filePath===" + filePath);
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
				logger.error("=======updatePlanConfig uploadFtpFlag===" + uploadFtpFlag);
			}

			// TODO:创建设计方案操作日志记录的数据>>start
			DesignPlanOperationLog designPlanOperationLog = new DesignPlanOperationLog();

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

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
		}
		return new ResponseEnvelope<DesignPlan>(true, "配置文件更新成功！", msgId);
	}

	@Override
	public ResponseEnvelope<DesignPlan> addDesignPlanProduct(Integer designPlanId,String posIndexPath,String posName,Integer productId,String context,Integer bindProductId,LoginUser loginUser,String msgId){
		// 添加一条设计方案产品数据
		Integer designPlanProductId = designPlanProductService.updateDesignPlanProduct(designPlanId, posIndexPath,
				productId, posName, context, bindProductId, loginUser);
		DesignPlanCacher.removeDesignPlanProduct(designPlanId);

		if (designPlanProductId == null || designPlanProductId.intValue() < 0) {
			return new ResponseEnvelope<DesignPlan>(false, "设计方案产品更新异常！", msgId);
		}
		// AddDesignPlanProductParameter parameter = new
		// AddDesignPlanProductParameter(context,posIndexPath,posName,designPlanId,productId,request);
		// AddDesignPlanProductTask task = new
		// AddDesignPlanProductTask(parameter);
		// FutureTask<Result> futureTask = new FutureTask<Result>(task);
		// TaskExecutor.getInstance().addTask(futureTask);

		try {
			DesignPlan designPlan = designPlanService.get(designPlanId);
			// 把新组装的内容写入配置文件
			ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());
			String filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);

			/**
			 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值， 则上传到web服务器。
			 **/
			Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
			// 上传方式为2或者3表示文件在ftp上
			if (ftpUploadMethod == 1 || ftpUploadMethod == 3) {
				// 替换本地
				Utils.replaceFile(filePath, context);
				if (ftpUploadMethod == 3) {
					// 替换ftp
					FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
				}
			} else if (ftpUploadMethod == 2) {
				// 替换ftp
				FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		boolean flag1 = designPlanService.updatePlanProductByConfig(context,
				designPlanId,false);
		if (!flag1) {
			return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
		}
		DesignPlan designPlan = designPlanService.get(designPlanId);
		DesignPlan newDesignPlan = new DesignPlan();
		if (designPlan != null) {
			newDesignPlan.setId(designPlan.getId());
			newDesignPlan.setGmtModified(new Date());
			/* 设置设计方案ischange为1(设计方案被改变) */
			newDesignPlan.setIsChange(new Integer(1));
			designPlanService.update(newDesignPlan);
			/* 设置设计方案ischange为1(设计方案被改变)->end */
		}
		/* 更新产品使用次数表 */
		/* 获取用户信息 */

		if (loginUser != null && loginUser.getId() > 0) {
			productUsageCountService.update(loginUser.getId(), productId, 1);
		}
		/* 更新产品使用次数表->end */

		// 异步更新进入设计方案缓存
		/*
		 * 设计方案产品列表不走缓存。2017-07-31 designPlanService.updateDesignPlanCache(designPlanId,
		 * newFlag, houseId, livingId, residentialUnitsName, request);
		 */

		return new ResponseEnvelope<DesignPlan>(designPlanProductId, msgId, true);
	}

	@Override
	public ResponseEnvelope<DesignPlan> unityUpdate(DesignPlan designPlan,Integer designPlanId,String planProductJson,String materialPicId,String context,String splitTexturesChoose,HttpServletRequest request,String msgId){
		boolean flag = false;
		try {
			flag = designPlanService.updateDesignPlan(designPlan, designPlanId, planProductJson,
                    materialPicId, context, request, splitTexturesChoose);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "unityUpdate data Exception! ", msgId);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "unityUpdate data Exception! ", msgId);
		}

		if (flag) {
			UnitUpdateParameter parameter = new UnitUpdateParameter(designPlanId, context, null, null,
					materialPicId, msgId, request, splitTexturesChoose);
			UnitUpdateTask task = new UnitUpdateTask(parameter);
			FutureTask<Result> futureTask = new FutureTask<Result>(task);
			TaskExecutor.getInstance().addTask(futureTask);
		}

		// 异步更新进入设计方案缓存
		/*
		 * 设计方案产品列表不走缓存。2017-07-31 designPlanService.updateDesignPlanCache(designPlanId,
		 * newFlag, houseId, livingId, residentialUnitsName, request);
		 */

		return new ResponseEnvelope<DesignPlan>(true, msgId, true);
	}

	@Override
	public ResponseEnvelope unityGroupProduct(Integer designPlanId,String groupProductJson,Integer groupId,Integer planProductId,
											  String materialPicId,String context,String planGroupId,LoginUser loginUser,
											  Integer isStandard,String center,String regionMark,Integer styleId,String measureCode,String msgId){
		String cacheEnable = Utils.getValue("redisCacheEnable", "0");
		DesignPlan desPlan = null;
		if (StringUtils.equals("1", cacheEnable)) {
			desPlan = DesignCacher.get(designPlanId);
		} else {
			desPlan = designPlanService.get(designPlanId);
		}
		if (desPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false, "方案不存在", msgId);
		}

		// 替换配置内容
		List<UnityPlanProduct> list = null;
		try {
			list = designPlanService.batchSaveDesignPlan(desPlan, designPlanId, groupProductJson,
                    groupId, planProductId, materialPicId, context, planGroupId, loginUser, null, isStandard, center,
                    regionMark, styleId, measureCode);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "新增方案组合产品数据异常！", msgId);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlan>(false, "新增方案组合产品数据异常！", msgId);
		}
		if (list == null) {
			return new ResponseEnvelope<DesignPlan>(false, "新增方案组合产品数据失败！", msgId);
		} else {
			// 获取配置路径
			/* 2017-11-16 add by zhangwj 注释通过配置文件更新方案产品列表代码。
			String filePath = "";
			try {
				if (desPlan.getConfigFileId() != null) {
					ResDesign resDesign = null;
					// ResFile resFile=null;
					if (StringUtils.equals("1", cacheEnable)) {
						resDesign = ResourceCacher.getResDesign(desPlan.getConfigFileId());
					} else {
						resDesign = resDesignService.get(desPlan.getConfigFileId());
					}

					filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);
					*//**
					 * TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值， 则上传到web服务器。
					 **//*
					Long startTimeUp = System.currentTimeMillis();
					Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD, 1);
					boolean uploadFtpFlag = false;
					// 上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 2 || ftpUploadMethod == 3) {
						uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
					} else if (ftpUploadMethod == 1) {
						uploadFtpFlag = Utils.replaceFile(filePath, context);
					}
					Long endTimeUp = System.currentTimeMillis();
					if (!uploadFtpFlag) {
						return new ResponseEnvelope<DesignPlan>(false, "写入文件失败", msgId);
					}
					if (uploadFtpFlag) {
						boolean flag = designPlanService.updatePlanProductByConfig(context, designPlanId,
								ContextType.CONTEXT_TYPE_OF_PRODUCT);
						if (!flag) {
							return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
						}
					} else {
						return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEnvelope<DesignPlan>(false, "方案数异常", msgId);
			}*/
		}
		DesignPlan designPlan = null;
		if (StringUtils.equals("1", cacheEnable)) {
			designPlan = DesignPlanCacher.getDesignPlan(designPlanId);
		} else {
			designPlan = designPlanService.get(designPlanId);
		}

		if (designPlan != null) {
			designPlan.setGmtModified(new Date());
		}

		// 异步更新进入设计方案缓存
		/*
		 * 设计方案产品列表不走缓存。2017-07-31 designPlanService.updateDesignPlanCache(designPlanId,
		 * newFlag, houseId, livingId, residentialUnitsName, request);
		 */

		return new ResponseEnvelope<>(list, msgId);
	}


	@Override
	public UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan) {
		List<UnityPlanProduct> dataList = unityDesignPlan.getDatalist();
		List<ProductDTO> list = designPlanService.getProductDTOList(designPlanId);
		for(UnityPlanProduct upp : dataList) {
			Integer upp_productId = upp.getProductId();
			for(ProductDTO productDTO : list) {
				Integer productId = productDTO.getProductId();
				if(upp_productId.equals(productId) && upp.getPosIndexPath().equals(productDTO.getPosIndexPath())) {
					String valueKey = productDTO.getValueKey();
					if(StringUtils.isNotBlank(valueKey)) {
						if(valueKey.indexOf("_") != -1) {
							String[] split = valueKey.split("_");
							upp.setBasicModelType(split[1]);
						} else {
							upp.setBasicModelType(valueKey);
						}
					}
				}
			}
		}

		return unityDesignPlan;
	}



	/**
	 * 保存设计方案拼花信息
	 * @param model 拼花信息
	 * @param isRenderServer 1:自动渲染
	 * @return
	 */
	@Override
	public Object spellingFlower(SpellingFlowerModel model,String isRenderServe,String opType) {
		String msgId = model.getMsgId();
		LoginUser loginUser = model.getLoginUser();
		String planId = model.getPlanId();
		String spellingFlowerProduct = model.getSpellingFlowerProduct();
		String spellingFlower = model.getSpellingFlower();
		
		if(loginUser == null || loginUser.getId().intValue() < 1) {
			return new ResponseEnvelope<DesignPlan>(false,"请重新登录",msgId);
		}
		if(!checkParam(msgId, planId)) {   
			return new ResponseEnvelope<DesignPlan>(false,"缺少参数",msgId);
		}

		DesignPlan plan = null;
		if("0".equals(opType)&&"1".equals(isRenderServe))//一键、自动渲染
			plan = optimizePlanService.getPlan(Integer.parseInt(planId));
		else
			plan = this.get(Integer.parseInt(planId));
		if(plan == null) {
			return new ResponseEnvelope<DesignPlan>(false,"草图方案不存在",msgId);
		}
		if(loginUser.getId().intValue() != plan.getUserId().intValue()) {
			return new ResponseEnvelope<DesignPlan>(false,"无法操作他人方案",msgId);
		}
		
		if(StringUtils.isEmpty(spellingFlower)) {  //和APP 协定没有则清空
			DesignPlan planDelSpellingFlower = new DesignPlan();
			planDelSpellingFlower.setId(plan.getId());
			planDelSpellingFlower.setSpellingFlowerFileId(0);
			if("0".equals(opType)&&"1".equals(isRenderServe))//一键、自动渲染
				optimizePlanService.update(planDelSpellingFlower);
			else
				this.update(planDelSpellingFlower);
		}else {
			//保存 拼花  产品ids 
			DesignPlan designPlan = new DesignPlan();
			designPlan.setId(plan.getId());
			designPlan.setSpellingFlowerProduct(spellingFlowerProduct);
			if("0".equals(opType)&&"1".equals(isRenderServe))//一键、自动渲染
				optimizePlanService.update(designPlan);
			else
				this.update(designPlan);
		}
		
		/*if(StringUtils.isEmpty(spellingFlowerProduct)) {//和APP 协定没有则清空
			DesignPlan planDelSpellingFlowerProduct = new DesignPlan();
			planDelSpellingFlowerProduct.setId(plan.getId());
			planDelSpellingFlowerProduct.setSpellingFlowerProduct("");
			this.update(planDelSpellingFlowerProduct);
		}else {
			//将 拼花文本信息 保存并回填到 设计方案
			Map<String,String>resMap = this.saveSpellingFlowerFile(spellingFlower,spellingFlowerProduct,plan);
			if(resMap == null || resMap.size()<=0){
				return new ResponseEnvelope<DesignPlan>(false,"保存设计方案拼花文件失败",msgId);
			}else if(!"true".equals(resMap.get("success"))){
				return new ResponseEnvelope<DesignPlan>(false,resMap.get("data"),msgId);
			}
		}	*/

		//将 拼花文本信息 保存并回填到 设计方案
		Map<String,String>resMap = this.saveSpellingFlowerFile(spellingFlower,spellingFlowerProduct,plan,isRenderServe,opType);
		if(resMap == null || resMap.size()<=0){
			return new ResponseEnvelope<DesignPlan>(false,"保存设计方案拼花文件失败",msgId);
		}else if(!"true".equals(resMap.get("success"))){
			return new ResponseEnvelope<DesignPlan>(false,resMap.get("data"),msgId);
		}

		return new ResponseEnvelope<DesignPlan>(true,"操作成功",msgId);
	}
	
	
	
	public Map<String,String> saveSpellingFlowerFile(String spellingFlower,String spellingFlowerProduct,DesignPlan designPlan,String isRenderServe,String opType) {
		Map<String,String>resMap = new HashMap<String,String>();
		if(StringUtils.isEmpty(spellingFlower) || designPlan == null) {
			resMap.put("success", "false");
			return resMap;
		}
		String filePath = null;
		if(designPlan.getSpellingFlowerFileId()!=null && designPlan.getSpellingFlowerFileId().intValue() > 0) {
			ResDesign resDesign = resDesignService.get(designPlan.getSpellingFlowerFileId());
			if(resDesign != null) {
				filePath = Utils.getAbsolutePath(resDesign.getFilePath(), null);
			}
		}
		String fileKey = "design.designPlan.spellingFlowerFile.upload.path";
		if(StringUtils.isEmpty(filePath)) { //如果拼花路径为空  代表第1次增加拼花信息
			String defaultPath = "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/"; //如果res配置文件中没有该filekey,用的默认地址
			String configKey = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
			configKey = Utils.replaceDate(configKey);
			String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
			filePath = Utils.getAbsolutePath(configKey + fileName, null);
		}
		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
			filePath = filePath.replace("\\", "/");
		}
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,spellingFlower);
		logger.error("---------------uploadFtpFlag = "+uploadFtpFlag);
		if(uploadFtpFlag){
			Map <String, String>map = FileUploadUtils.getMap(new File(filePath), fileKey, false);
			designPlan.setSpellingFlowerProduct(spellingFlowerProduct);
			boolean flag = resDesignService.saveSpellingFlowerFile(designPlan,map,isRenderServe,opType);//生成资源数据
			logger.error("---------------flag = "+flag);
			if( !flag ){
				resMap.put("success", "false");
				resMap.put("data", "保存资源数据库失败");
				return resMap;
			}
		}else{
			resMap.put("success", "false");
			resMap.put("data", "保存设计方案拼花文件失败");
			return resMap;
		}
		resMap.put("success", "true");
		return resMap;
 
	}
	
	/**
	 * 参数完整性判断
	 * @param args
	 * @return
	 */
	private boolean checkParam(String... args) {
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
	 * 方案推荐改造
	 * @param model
	 * @return Map<String,String>
	 */
	@Override
	public Map<String,String> transformAndCopyPlan(TransformAndCopyPlanModel model) {	
		Integer planRecommendedId = model.getPlanRecommendedId();
		Integer taskId = model.getTaskId();
		LoginUser loginUser = model.getLoginUser();
		
		Map<String,String>resMap = new HashMap<String,String>();
		if(loginUser == null || loginUser.getId() == null || loginUser.getId().intValue() <= 0){
			resMap.put("data", "请重新登录");
			resMap.put("success", "false");
			return resMap;
		}
		DesignPlanRecommended  designPlanRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
		if(designPlanRecommended == null){
			resMap.put("data", "方案推荐不存在，或已被下架");
			resMap.put("success", "false");
			return resMap;
		}
		DesignPlan designPlan = designPlanRecommended.recommendedCopy();
		 
		designPlan.setId(null);
		if (designPlanRecommended.getPlanCode() != null) {
			designPlan.setPlanCode(designPlanRecommended.getPlanCode().substring(0, designPlanRecommended.getPlanCode().length() - 6)+ Utils.generateRandomDigitString(6));
		}

		designPlan.setUserId(loginUser.getId());
		designPlan.setIsChange(0);
		designPlan.setIsOpen(0);
		designPlan.setSysCode(designPlanRecommended.getSysCode().substring(0, designPlanRecommended.getSysCode().length() - 6) + Utils.generateRandomDigitString(6));
		//存储设计方案业务来源 为改造方案
        designPlan.setBusinessType(DesignPlanBusinessTypeConstant.TYPE_TRANSFORM);
        
		sysSave(designPlan, loginUser);
		designPlan.setCoverPicId(null);
		/*拷贝效果图配置文件*/
		if (designPlanRecommended.getConfigFileId() != null) {
			Integer resFileId = this.planCopyFileFromResDesignScene(designPlanRecommended.getConfigFileId().toString(), 
				"design.designPlan.u3dconfig","/AA/d_userdesign/[YYYY]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/",
				null, designPlanRecommended.getPlanCode(),loginUser);
			designPlan.setConfigFileId(resFileId);
		} else {
			designPlan.setConfigFileId(-1);
		}
		
		/*拷贝效果图拼花配置文件*/
		if (designPlanRecommended.getSpellingFlowerFileId() != null) {
			Integer resFileId = this.planCopyFileFromResDesignScene(designPlanRecommended.getSpellingFlowerFileId().toString(), 
				"design.designPlan.spellingFlowerFile","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/",
				null, designPlanRecommended.getPlanCode(),loginUser);
			designPlan.setSpellingFlowerFileId(resFileId);
		} else {
			designPlan.setSpellingFlowerFileId(-1);
		}
		
		Integer planId = designPlanService.add(designPlan);
		if(planId !=null){
		  List<ProductReplaceTaskDetail> replaceProductList = null;
		  if(taskId != null) {
		    logger.error("taskId===" + taskId);
		    replaceProductList = mobileRenderRecordMapper.selectListByTaskId(taskId);
		  }
			DesignPlanRecommendedProduct designPlanRecommendedProduct = new DesignPlanRecommendedProduct();/*设计方案的产品列表代入*/
			designPlanRecommendedProduct.setIsDeleted(0);
			designPlanRecommendedProduct.setPlanRecommendedId(designPlanRecommended.getId());
			List<DesignPlanRecommendedProduct> planProductList = designPlanRecommendedProductServiceV2.getList(designPlanRecommendedProduct);
			for (DesignPlanRecommendedProduct dprProduct : planProductList) {
				DesignPlanProduct planProduct = new DesignPlanProduct();
				planProduct = dprProduct.recommendedProductCopy();
				if (replaceProductList != null) {
	                  logger.error("replaceProductList===" + replaceProductList.size());
	                   for(ProductReplaceTaskDetail replaceProduct : replaceProductList) {
	                     logger.error("getPosIndexPath===" + replaceProduct.getPosIndexPath() + "dprProduct.getPosIndexPath()=" + dprProduct.getPosIndexPath() );
	                     if(replaceProduct.getPosIndexPath().equals(dprProduct.getPosIndexPath())) {
	                       planProduct.setProductId(replaceProduct.getDestProductId());
							 //update by steve, add the texture replace
							 if(StringUtils.isNotBlank(replaceProduct.getReplaceSplitTexturesParam())) {
								 String newTexturesInfo = mobilePayRenderService.getNewSplitTexturesChooseInfoByProductId(replaceProduct.getDestProductId(),replaceProduct.getReplaceSplitTexturesParam());
								 planProduct.setSplitTexturesChooseInfo(newTexturesInfo);
								 planProduct.setIsReplaceTexture(taskId);
							 }
	                       logger.error("dprProduct=" + dprProduct.getProductId() + "new planproductid=" + planProduct.getProductId());
	                     }
	                   }
	                }
				planProduct.setId(null);
				planProduct.setPlanId(planId);
				sysSave(planProduct, loginUser);
				
				designPlanProductService.add(planProduct);
			}
		}
		resMap.put("planId", planId + "");
		resMap.put("success", "true");
		return resMap;
	}
	
	
	/**
	 * 处理副本配置文件
	 * @param resId
	 * @param resType
	 * @param fileKey
	 * @param bussniess
	 * @param request
	 * @param code
	 * @return
	 */
	public Integer planCopyFileFromResDesignScene(String resId,String fileKey,String defaultPath, Integer bussniess,String code,LoginUser loginUser) {
		String resFilePath = "";
		Integer newResId = -1;
		ResDesign resDesign = new ResDesign();
		if (!StringUtils.isEmpty(resId)) {
			String targetName = null;
			ResDesign file = resDesignService.get(Integer.parseInt(resId));
			if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
				resFilePath = file.getFilePath();
				resDesign = file.resDesignCopy();
			}
			if (!StringUtils.isEmpty(resFilePath)) {
				String srcPath = Utils.dealWithPath(Utils.getAbsolutePath(resFilePath, null), null);
				File srcresourcesFile = new File(srcPath);
				if (!srcresourcesFile.getParentFile().exists()) {
					srcresourcesFile.getParentFile().mkdirs();
				}
				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
						resFilePath.length());
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
				}

				String newPath = Utils.getPropertyName("config/res", fileKey + ".upload.path", defaultPath).trim();
				newPath = Utils.replaceDate(newPath);

				//去除在名称后缀追加时间戳逻辑，因为此逻辑文件名称会无限变长，所以改为重新生成名称
//				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
//						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ resourcesName.substring(resourcesName.indexOf("."));
				String tarName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";

				targetName = newPath + tarName;
				targetName = targetName.replace(".txt", AESFileConstant.AES_FIRST+".txt");//复制的配置文件因为源文件已经加密，要在文件后面加个标识
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);
				File local_targetFile = new File(local_targetPath);
				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0, resFilePath.lastIndexOf("/") + 1);
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
					} else {
						logger.error("srcresourcesFile is not  exists !srcresourcesFile="+srcresourcesFile);
					}
				} else if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						if (flag) {
							flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
							if (flag) {
								FileUploadUtils.deleteFile(newPath + tarName);	// 删除本地
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
					// 3 本地和ftp同时上传(默认是本地上传)resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (!flag || FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						logger.error("copy file is error");
						return -1;
					}
					if (flag) {// tarName:文件名称，dbFilePath:本地文件路径，newPath:ftp服务器存放文件路径
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
						if (!flag) {
							return newResId;
						}
					} else {
						return newResId;
					}
				}
				
				}
			resDesign.setSysCode(code);
			resDesign.setFilePath(targetName);
			resDesign.setFileKey(fileKey);
			resDesign.setBusinessId(bussniess);
			resDesign.setFileCode(code);
			sysSave(resDesign, loginUser);
			newResId = resDesignService.add(resDesign);
		}

		return newResId;
	}


	/**
	 * 处理副本配置文件
	 * @param resId
	 * @param resType
	 * @param fileKey
	 * @param bussniess
	 * @param request
	 * @param code
	 * @return
	 */
	public Integer planCopyFileFromResDesignSceneByPC(String resId,String fileKey,String defaultPath, Integer bussniess,String code,LoginUser loginUser) {
		String resFilePath = "";
		Integer newResId = -1;
		ResDesign resDesign = new ResDesign();
		if (!StringUtils.isEmpty(resId)) {

			String targetName = null;
			ResDesignRenderScene file = resDesignRenderSceneService.get(new Integer(resId));
			if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
				resFilePath = file.getFilePath();
				resDesign = file.resDesignCopy();
			}

			if (!StringUtils.isEmpty(resFilePath)) {
				String srcPath = Utils.dealWithPath(Utils.getAbsolutePath(resFilePath, null), null);
				File srcresourcesFile = new File(srcPath);
				if (!srcresourcesFile.getParentFile().exists()) {
					srcresourcesFile.getParentFile().mkdirs();
				}
				String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,
						resFilePath.length());
				if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
					resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
				}

				String newPath = Utils.getPropertyName("config/res", fileKey + ".upload.path", defaultPath).trim();
				newPath = Utils.replaceDate(newPath);

				//去除在名称后缀追加时间戳逻辑，因为此逻辑文件名称会无限变长，所以改为重新生成名称
//				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
//						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//						+ resourcesName.substring(resourcesName.indexOf("."));
				String tarName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";

				targetName = newPath + tarName;
				targetName = targetName.replace(".txt", AESFileConstant.AES_FIRST+".txt");//复制的配置文件因为源文件已经加密，要在文件后面加个标识
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);
				File local_targetFile = new File(local_targetPath);
				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0, resFilePath.lastIndexOf("/") + 1);
				String dbFilePath = Utils.getAbsolutePath(newPath + tarName, null);
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
					if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { // 判断文件是否存在
						flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
					} else {
						logger.error("srcresourcesFile is not  exists !srcresourcesFile="+srcresourcesFile);
					}
				} else if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						if (flag) {
							flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
							if (flag) {
								FileUploadUtils.deleteFile(newPath + tarName);	// 删除本地
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
					// 3 本地和ftp同时上传(默认是本地上传)resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称
					flag = FtpUploadUtils.downFile(resPath, resourcesName);// 下载到本地
					if (!flag || FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
						logger.error("copy file is error");
						return -1;
					}
					if (flag) {// tarName:文件名称，dbFilePath:本地文件路径，newPath:ftp服务器存放文件路径
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);// 上传ftp服务器
						if (!flag) {
							return newResId;
						}
					} else {
						return newResId;
					}
				}

				}

			resDesign.setSysCode(code);
			resDesign.setFilePath(targetName);
			resDesign.setFileKey(fileKey);
			resDesign.setBusinessId(bussniess);
			resDesign.setFileCode(code);
			sysSave(resDesign, loginUser);
			newResId = resDesignService.add(resDesign);
		}

		return newResId;
	}


	 /*<!-- COMMON-525 -->*/
	/**
	 * 查询该品牌所经营的大小分类所过滤出的设计方案中产品中不是该品牌的产品编码
	 * @param brandIds 品牌s
	 * @param designPlanId 设计方案id
	 * @return
	 */
	@Override
	public Set<String> getProductCodeLogic(String brandIds,Integer designPlanId){
		Set<String> productCode = new HashSet<String>();
		String[] ids = brandIds.split(",");
		for (String id : ids) {
			Integer brandId = null;
			try {
				 brandId = Integer.parseInt(id);
			}catch (Exception e){
				logger.error("品牌id="+id+"转换Integer失败",e);
			}
			if(null!=brandId){
				//品牌所在企业所经营的产品大分类
				try {
					String valuekeys = designPlanMapper.getCompanyValueKey(brandId);
					if(null!=valuekeys&&""!=valuekeys){
						String[] valuekeyList = valuekeys.split(",");
						//查询该品牌所经营的大小分类所过滤出的设计方案中产品中不是该品牌的产品编码
						logger.info("getProductCode查询参数designPlanId="+designPlanId+"valuekeyList="+valuekeys+"valuekey="+"brandId="+brandId);
						List<String> code = designPlanMapper.getProductCode(designPlanId,valuekeyList,brandId);
						logger.info("getProductCode查询结果productCode="+ JSON.toJSONString(code));
						productCode.addAll(code);
					}
				}catch (Exception e){
					logger.error("查询数据库报错，错误信息="+e.getMessage(),e);
				}

			}
		}
		return productCode;
	}

    @Override
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

	@Override
	public List<AddDesignPlanProductsResultVO> addDesignPlanProducts(
			String newProductsJson, 
			String context,
			DesignPlan designPlan,
			LoginUser loginUser
			) throws DesignPlanException {
		// 参数验证 ->start
		if(StringUtils.isEmpty(newProductsJson)) {
			throw new DesignPlanException("没有得到newProductsJson信息(新增产品对应信息)");
		}
		// 不需要更新配置文件,放在退出房间的时候更新
		/*if(StringUtils.isEmpty(context)) {
			throw new DesignPlanException("没有得到context信息(设计方案配置文件)");
		}*/
		if(loginUser == null) {
			throw new DesignPlanException("没有获取到用户的登录信息");
		}
		// 参数验证 ->end
		
		// 保存新增的产品 ->start
		List<AddDesignPlanProductsResultVO> result = this.saveProductByJson(newProductsJson, designPlan, loginUser);
		// 保存新增的产品 ->end
		
		// 更新配置文件 ->start
		// 不需要更新配置文件,放在退出房间的时候更新
		/*this.updateContext(context, designPlan);*/
		// 更新配置文件 ->end
		return result;
	}

	@Override
	public void updateContext(String context, DesignPlan designPlan) {
		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	@Transactional
	public List<AddDesignPlanProductsResultVO> saveProductByJson(
			String newProductsJson, 
			DesignPlan designPlan, 
			LoginUser loginUser
			) throws DesignPlanException {
		// 参数验证 ->start
		if(StringUtils.isEmpty(newProductsJson)) {
			return null;
		}
		if(designPlan == null) {
			return null;
		}
		if(designPlan.getId() == null) {
			return null;
		}
		// 参数验证 ->end
		List<NewProductsJsonBean> newProductsJsonBeanList = null;
		try {
			JSONArray jsonArray = JSONArray.fromObject(newProductsJson);
			newProductsJsonBeanList = JSONArray.toList(jsonArray, NewProductsJsonBean.class);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DesignPlanException("json解析错误,请检查newProductsJson是否有误");
		}
		if(newProductsJsonBeanList == null) {
			return null;
		}
		
		List<AddDesignPlanProductsResultVO> result = new ArrayList<AddDesignPlanProductsResultVO>();
		
		for(NewProductsJsonBean newProductsJsonBean : newProductsJsonBeanList) {
			if(newProductsJsonBean.getProductId() == null) {
				throw new DesignPlanException("参数newProductsJson解析,存在productId=null的情况,新增失败");
			}
			BaseProduct baseProduct = baseProductService.get(newProductsJsonBean.getProductId());
			if(baseProduct == null) {
				throw new DesignPlanException("参数newProductsJson解析出来的productId(" + newProductsJsonBean.getProductId() + ")"
						+ "没有找到对应产品信息");
			}
			DesignPlanProduct designPlanProduct = new DesignPlanProduct();
			// 获取属性 ->start
			designPlanProduct.setPlanId(designPlan.getId());
			designPlanProduct.setPosIndexPath(newProductsJsonBean.getPosIndexPath());
			designPlanProduct.setPosName(newProductsJsonBean.getPosName());
			designPlanProduct.setProductId(newProductsJsonBean.getProductId());
			// 获取属性 ->end
			
			// 默认属性 ->start
			designPlanProduct.setIsDeleted(0);
			designPlanProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
			designPlanProduct.setPlanProductId(0);
			designPlanProduct.setIsHide(0);
			designPlanProduct.setDisplayStatus(0);
			designPlanProduct.setInitProductId(newProductsJsonBean.getProductId());
			if(newProductsJsonBean.getBindProductId() != null && newProductsJsonBean.getBindProductId() > 0){
				designPlanProduct.setInitProductId(newProductsJsonBean.getBindProductId());
			}
			sysSave(designPlanProduct, loginUser);
			designPlanProductService.add(designPlanProduct);
			// 默认属性 ->end
			
			// 添加已使用记录 ->start
			UsedProducts usedProducts = new UsedProducts();
			usedProducts.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			usedProducts.setCreator(loginUser.getLoginName());
			usedProducts.setGmtCreate(new Date());
			usedProducts.setGmtModified(new Date());
			usedProducts.setModifier(loginUser.getName());
			usedProducts.setIsDeleted(0);
			usedProducts.setUserId(loginUser.getId());
			usedProducts.setDesignId(designPlanProduct.getPlanId());
			usedProducts.setProductId(designPlanProduct.getProductId());
			usedProducts.setPlanProductId(designPlanProduct.getPlanProductId());
			usedProductsService.add(usedProducts);
			productUsageCountService.update(loginUser.getId(), designPlanProduct.getProductId(), 1);
			// 添加已使用记录 ->end
			
			// 组装结果集 ->start
			AddDesignPlanProductsResultVO addDesignPlanProductsResultVO = new AddDesignPlanProductsResultVO();
			addDesignPlanProductsResultVO.setDesignPlanProductId(designPlanProduct.getId());
			addDesignPlanProductsResultVO.setPosName(designPlanProduct.getPosName());
			result.add(addDesignPlanProductsResultVO);
			// 组装结果集 ->end
		}
		return result;
	}


	@Override
	public MessageUtil updatePlanCustomizedProductConfig(Integer planId, String context, LoginUser loginUser) {
		if (planId == null || planId == 0) {
			return new MessageUtil(false, "param is empty!");
		}
		//1、配置内容为空，说明客户端把定制产品都删了，所以需要把之前的配置内容删除并更新方案数据
		//2、配置不为空，说明客户端新增产品或者替换产品，需要保存、更新配置更新方案数据
		if (!StringUtils.isEmpty(context)) {
			DesignPlan designPlan = this.get(planId);
			if (null == designPlan) {
				return new MessageUtil(false, "找不到方案记录! planId = " + planId);
			}
			Integer customizedProductConfigId = designPlan.getCustomizedProductConfigId();
			if (null != customizedProductConfigId && 0 < customizedProductConfigId) {
				ResDesign resDesign = resDesignService.get(customizedProductConfigId);
				if (null != resDesign) {
					if (!StringUtils.isEmpty(resDesign.getFilePath())) {
						int i = resDesignService.updateFile(resDesign, context, loginUser);
						if (1 > i) {
							return new MessageUtil(false, "更新方案定制产品配置文件失败!");
						}
					}
				}
			} else {
				Integer resFileId = resDesignService.saveFile("alpha.customized.product.config", context, loginUser, planId, "方案定制产品配置文件");
				if (null == resFileId || 1 > resFileId) {
					return new MessageUtil(false, "保存方案定制产品配置文件失败!");
				}
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setId(planId);
				newDesignPlan.setCustomizedProductConfigId(resFileId);
				this.sysSave(newDesignPlan, loginUser);
				int i = this.update(newDesignPlan);
				if (1 > i) {
					return new MessageUtil(false, "更新方案定制产品数据失败!");
				}
			}
		} else {
			DesignPlan designPlan = this.get(planId);
			if (null == designPlan) {
				return new MessageUtil(false, "找不到方案记录! planId = " + planId);
			}
			Integer customizedProductConfigId = designPlan.getCustomizedProductConfigId();
			if (null != customizedProductConfigId && 0 < customizedProductConfigId) {
				ResDesign resDesign = resDesignService.get(customizedProductConfigId);
				//物理删除资源
				if (null != resDesign) {
					if (!StringUtils.isEmpty(resDesign.getFilePath())) {
						FileUploadUtils.deleteFile(resDesign.getFilePath());
					}
					resDesignService.physicallyDelete(customizedProductConfigId);
				}
				//更新方案定制产品信息
				DesignPlan newDesignPlan = new DesignPlan();
				newDesignPlan.setId(planId);
				newDesignPlan.setCustomizedProductConfigId(0);
				this.sysSave(newDesignPlan, loginUser);
				int i = this.update(newDesignPlan);
				if (1 > i) {
					return new MessageUtil(false, "更新方案定制产品信息失败!");
				}
			}
		}

		return new MessageUtil(true);
	}

	@Override
	public MessageUtil updatePlanCustomizedProductOrderCode(Integer planId, String orderCode, LoginUser loginUser) {
		DesignPlan designPlan = this.get(planId);
		if (null == designPlan) {
			return new MessageUtil(false, "找不到方案记录! planId = " + planId);
		}
		//更新方案定制产品信息
		DesignPlan newDesignPlan = new DesignPlan();
		newDesignPlan.setId(planId);
		newDesignPlan.setCustomizedProductOrderCode(orderCode);
		this.sysSave(newDesignPlan, loginUser);
		int i = this.update(newDesignPlan);
		if (1 > i) {
			return new MessageUtil(false, "更新方案定制产品信息失败!");
		}
		return new MessageUtil(true);
	}

}
