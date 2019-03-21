//<<<<<<< .working
//package com.nork.onekeydesign.service.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import DesignPlan.DesignPlanRelease;
//
//import com.nork.common.model.LoginUser;
//import com.nork.common.model.ResponseEnvelope;
//import com.nork.common.util.Utils;
//import com.nork.onekeydesign.common.DesignPlanConstants;
//import com.nork.onekeydesign.dao.DesignPlanBrandMapper;
//import com.nork.onekeydesign.dao.DesignPlanCheckMapper;
//import com.nork.onekeydesign.dao.DesignPlanMapper;
//import com.nork.onekeydesign.model.DesignPlan;
//import com.nork.onekeydesign.model.DesignPlanBrand;
//import com.nork.onekeydesign.model.DesignPlanCheck;
//import com.nork.onekeydesign.model.DesignPlanRecommendedResult;
//import com.nork.onekeydesign.model.ReleaseDesignPlanModel;
//import com.nork.onekeydesign.service.DesignPlanBrandService;
//import com.nork.onekeydesign.service.DesignPlanCheckService;
//import com.nork.onekeydesign.service.DesignPlanRecommendedService;
//import com.nork.onekeydesign.service.DesignPlanService;
//import com.nork.onekeydesign.service.NewDesignPlanRecommendService;
//import com.nork.product.model.AuthorizedConfig;
//import com.nork.product.model.BaseBrand;
//import com.nork.product.model.BaseProductStyle;
//import com.nork.product.service.AuthorizedConfigService;
//import com.nork.system.model.ResRenderPic;
//import com.nork.system.model.SysDictionary;
//import com.nork.system.model.SysUserPlanRecommended;
//import com.nork.system.service.ResRenderPicService;
//import com.nork.system.service.SysDictionaryService;
//
//@Service("newDesignPlanRecommendServiceImpl")
//public class NewDesignPlanRecommendServiceImpl implements NewDesignPlanRecommendService{
//
//	private static Logger logger = Logger.getLogger(NewDesignPlanRecommendServiceImpl.class);
//	
//	@Autowired
//	private DesignPlanBrandService designPlanBrandService;
//	
//	@Autowired
//	private AuthorizedConfigService authorizedConfigService;
//	
//	@Autowired
//	private DesignPlanService designPlanService;
//	
//	@Autowired
//	private SysDictionaryService sysDictionaryService;
//	
//	@Autowired
//	private ResRenderPicService resRenderPicService;
//
//
//package com.nork.onekeydesign.service.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.nork.common.model.LoginUser;
//import com.nork.common.model.ResponseEnvelope;
//import com.nork.common.util.Utils;
//import com.nork.onekeydesign.common.DesignPlanConstants;
//import com.nork.onekeydesign.dao.DesignPlanBrandMapper;
//import com.nork.onekeydesign.dao.DesignPlanCheckMapper;
//import com.nork.onekeydesign.dao.DesignPlanMapper;
//import com.nork.onekeydesign.model.DesignPlan;
//import com.nork.onekeydesign.model.DesignPlanBrand;
//import com.nork.onekeydesign.model.DesignPlanCheck;
//import com.nork.onekeydesign.model.DesignPlanRecommendedResult;
//import com.nork.onekeydesign.model.ReleaseDesignPlanModel;
//import com.nork.onekeydesign.service.DesignPlanBrandService;
//import com.nork.onekeydesign.service.DesignPlanCheckService;
//import com.nork.onekeydesign.service.DesignPlanRecommendedService;
//import com.nork.onekeydesign.service.DesignPlanService;
//import com.nork.onekeydesign.service.NewDesignPlanRecommendService;
//import com.nork.product.model.AuthorizedConfig;
//import com.nork.product.model.BaseBrand;
//import com.nork.product.model.BaseProductStyle;
//import com.nork.product.service.AuthorizedConfigService;
//
//import com.nork.system.model.ResRenderPic;
//import com.nork.system.model.SysDictionary;
//import com.nork.system.model.SysUserPlanRecommended;
//import com.nork.system.service.ResRenderPicService;
//import com.nork.system.service.SysDictionaryService;
//
//
//import DesignPlan.DesignPlanRelease;
//
//@Service("newDesignPlanRecommendServiceImpl")
//public class NewDesignPlanRecommendServiceImpl implements NewDesignPlanRecommendService{
//
//	private static Logger logger = Logger.getLogger(NewDesignPlanRecommendServiceImpl.class);
//	
//	@Autowired
//	private DesignPlanBrandService designPlanBrandService;
//	
//	@Autowired
//	private AuthorizedConfigService authorizedConfigService;
//	
//	@Autowired
//	private DesignPlanService designPlanService;
//	
//	@Autowired
//	private SysDictionaryService sysDictionaryService;
//	
//	@Autowired
//	private ResRenderPicService resRenderPicService;
//
////	
////	@Autowired
////	private BaseBrandService baseBrandService;
////	
////	@Autowired
////	private BaseProductStyleService baseProductStyleService;
////	
////	@Autowired
////	private DesignTempletService designTempletService;
////	
////	@Autowired
////	private SpaceCommonService spaceCommonService;
////	
////	@Autowired
////	private DesignPlanProductService designPlanProductService;
////	
////	@Autowired
////	private BaseProductService baseProductService;
//	
//	@Autowired
//	private DesignPlanCheckService designPlanCheckService;
//	
////	@Autowired
////	private SysUserService sysUserService;
////	
////	@Autowired
////	private SysRoleService sysRoleService;
////	
////	@Autowired
////	private SysUserRoleService sysUserRoleService;
//	
//	@Autowired
//	private DesignPlanRecommendedService designPlanRecommendedService;
//	
//	@Autowired
//	private DesignPlanCheckMapper designPlanCheckMapper;
//	
//	@Autowired
//	private DesignPlanBrandMapper designPlanBrandMapper;
//	
//	@Autowired
//	private DesignPlanMapper designPlanMapper;
//	
//	@Override
//	public ResponseEnvelope<DesignPlanRecommendedResult> getPlanRecommendedList(String houseType, String livingName,
//			String areaValue, String designRecommendedStyleId, String isMainList, String msgId, LoginUser loginUser) {
//	
//		/* 装着品牌id的list，没有授权码的用户也能看到 ，选择“推荐所有”的设计方案，推荐所有的设计方案品牌ID = -1 */
//		List<Integer> brandIds = new ArrayList<Integer>();
//		brandIds.add(-1);
//		/* 查询该用户的授权码品牌 */
//		List<AuthorizedConfig> authorizedConfigList = null;
//		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
//		authorizedConfig.setIsDeleted(0);
//		authorizedConfig.setState(1);
//		authorizedConfig.setUserId(loginUser.getId());
//		authorizedConfigList = authorizedConfigService.getList(authorizedConfig);
//		if (authorizedConfigList != null && authorizedConfigList.size() > 0) {
//			for (AuthorizedConfig authorizedConfig_ : authorizedConfigList) {
//				String brandId = authorizedConfig_.getBrandIds();
//				if (brandId != null && !"".equals(brandId)) {
//					brandIds.add(Integer.parseInt(brandId));
//				}
//			}
//		}
//		/* 查询 */
//		DesignPlanBrand designPlanBrand = new DesignPlanBrand();
//		designPlanBrand.setBrandIds(brandIds);
//
//		if (StringUtils.isBlank(areaValue)) {
//			designPlanBrand.setIsRecommended(1);
//		} else {
//			designPlanBrand.setIsRecommended(0);
//		}
//		if (StringUtils.isNotBlank(houseType)) { // 空间功能类型
//			designPlanBrand.setSpaceFunctionId(Integer.parseInt(houseType));
//		}
//		if (StringUtils.isNotBlank(areaValue)) { // 空间面积
//			designPlanBrand.setAreaValue(areaValue);
//			designPlanBrand.setIsRecommended(2);/* 等于2 同事查询 默认和非默认的 */
//		} else {
//			designPlanBrand.setIsRecommended(
//					1);/* 等于1只查询 默认推荐 设置后将出现在方案推荐列表默认列表中；否则只能通过面积过滤筛选查找 */
//		}
//		if (StringUtils.isNotBlank(livingName)) { /* 小区名称 */
//			designPlanBrand.setLivingName(livingName);
//		}
//		if (StringUtils.isNotBlank(designRecommendedStyleId)) { /* 推荐方案风格 */
//			designPlanBrand.setDesignRecommendedStyleId(Integer.parseInt(designRecommendedStyleId));
//		}
//		/* 1代表祝列表。其他代表一键装修处的小列表。小列表只能查询支持一件装修的数据 */
//		if (isMainList == null || !"1".equals(isMainList)) {
//			designPlanBrand.setIsDefaultDecorate(1);
//		}
//		String sysVersionType = Utils.getPropertyName("app", "sys.version.type", "1")
//				.trim();/* 1为外网 2 为内网 */
//		if ("2".equals(sysVersionType) && loginUser.getUserType().intValue() == 1) { // 内网内部用户的能看到测试发布中的
//			designPlanBrand.setIsInternalUser("yes");
//		}
//		Integer total = 0;
//		List<DesignPlanRecommendedResult> list = null;
//		total = designPlanBrandService.getPlanRecommendedCount(designPlanBrand);
//		if (total != null && total.intValue() > 0) {
//			list = designPlanBrandService.getPlanRecommendedList(designPlanBrand);
//			if (list != null && list.size() > 0) {
//				for (DesignPlanRecommendedResult result : list) {
//					SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("houseType",
//							result.getSpaceFunctionId());
//					if (sysDictionary != null) {
//						result.setHouseTypeName(sysDictionary.getName());
//					}
//					/* 得到最新一张720渲染原图地址 */
//					ResRenderPic renderPic=resRenderPicService.getResRenderPicByBusinessId(result.getPlanId(),4);
//					if(renderPic !=null){
//						result.setResRenderPicPath(renderPic.getPicPath());
//					}
//				}
//			}
//		}
//
//		return new ResponseEnvelope<DesignPlanRecommendedResult>(total, list, msgId);
//	}
//
//	@Override
//	public ResponseEnvelope<DesignPlanRecommendedResult> recommendDesignPlan(ReleaseDesignPlanModel model, String msgId, LoginUser loginUser) {
//		String brandIds = model.getBrandIds(); /*品牌ids*/
//		String checkIds = model.getCheckIds();  /*审核人员ids*/
//		String styleId = model.getStyleId(); /*风格id*/
//		String planId = model.getPlanId();/*方案id*/
//		String isRelease = model.getIsRelease();/*设计方案是否发布 0 否 1是*/
//		String isRecommended = model.getIsRecommended();/*是否默认推荐  1 默认 0 非默认*/
//		String isDefaultDecorate = model.getIsDefaultDecorate();/*是否支持一键装修  1 支持  0不支持*/
//		String planNumber = model.getPlanNumber();/*方案编号*/
//		
//		DesignPlan designPlan = designPlanService.get(Integer.parseInt(planId));
//		if(designPlan==null){
//			return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"方案不存在,或者已经被删除",msgId);
//		}
//		if (designPlan.getIsRelease() != null) {
//			int currentReleaseState = designPlan.getIsRelease().intValue();
//			if(currentReleaseState == DesignPlanConstants.IS_TEST_RELEASE 
//					|| currentReleaseState == DesignPlanConstants.IS_RELEASEING) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "请先取消发布!", msgId);
//			}
//		}
//		int isReleaseState = Integer.parseInt(isRelease);
//		if (isReleaseState == DesignPlanConstants.IS_TEST_RELEASE
//				|| isReleaseState == DesignPlanConstants.IS_RELEASEING) {
//			if (styleId == null || "".equals(styleId)) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "缺少参数styleId", msgId);
//			}
//			if (StringUtils.isBlank(isRecommended)) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "缺少参数isRecommended", msgId);
//			} else if (!"0".equals(isRecommended) && !"1".equals(isRecommended)) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "参数错误isRecommended", msgId);
//			}
//			if (StringUtils.isBlank(isDefaultDecorate)) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "缺少参数isDefaultDecorate", msgId);
//			} else if (!"0".equals(isDefaultDecorate) && !"1".equals(isDefaultDecorate)) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "参数错误isDefaultDecorate", msgId);
//			}
//			designPlan.setIsRecommended(Integer.parseInt(isRecommended));
//			designPlan.setIsDefaultDecorate(Integer.parseInt(isDefaultDecorate));
//			designPlan.setPlanNumber(planNumber);
//			designPlan.setDesignRecommendedStyleId(Integer.parseInt(styleId));
//		}
//		/* 进入发布方法 */
//		if (isReleaseState == DesignPlanConstants.IS_RELEASEING) {
//			List<String> brandIdList = null;
//			DesignPlanBrand designPlanBrand = new DesignPlanBrand();
//			designPlanBrand.setPlanId(Integer.parseInt(planId));
//			designPlanBrand.setIsDeleted(0);
//			if (StringUtils.isBlank(brandIds)) {
//				Integer count = designPlanBrandService.getCount(designPlanBrand);
//				if (count.intValue() <= 0) {
//					return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "请选择品牌！", msgId);
//				}
//			} else {
//				String[] arr = brandIds.split(",");
//				brandIdList = Arrays.asList(arr);
//			}
//
//			List<String> checkIdList = null;
//			DesignPlanCheck designPlanCheck = new DesignPlanCheck();
//			designPlanCheck.setPlanId(Integer.parseInt(planId));
//			designPlanCheck.setIsDeleted(0);
//			if (StringUtils.isBlank(checkIds)) {
//				Integer count = designPlanCheckService.getCount(designPlanCheck);
//				if (count.intValue() <= 0) {
//					return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "请选择审核人员！", msgId);
//				}
//			} else {
//				String[] arr = checkIds.split(",");
//				checkIdList = Arrays.asList(arr);
//			}
//
//			/* 判断是否拥有设计方案拷贝 和 发布的权限 */
//			boolean flag = designPlanRecommendedService.isPermissions(loginUser);
//			if (!flag) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "您没有权限！", msgId);
//			}
//			/* 方案推荐 《发布》 需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染 */
//			Map<String, String> resMap = designPlanRecommendedService.planIsReleaseCheck(designPlan, brandIdList);
//			if ("false".equals(resMap.get("success"))) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, resMap.get("data"), msgId);
//			}
//			if (!"true".equals(resMap.get("success"))) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, resMap.get("data"), msgId);
//			}
//			// 进入发布
//			boolean flag2 = planIsRelease(designPlan, Integer.parseInt(isRelease), brandIdList, checkIdList, loginUser);
//			if (flag2) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(true, "发布成功，请等待审核结果！", msgId);
//			} else {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "发布失败", msgId);
//			}
//
//			/* 进入测试发布方法 */
//		} else if (isReleaseState == DesignPlanConstants.IS_TEST_RELEASE) {
//
//			List<String> brandIdList = null;
//			DesignPlanBrand designPlanBrand = new DesignPlanBrand();
//			designPlanBrand.setPlanId(Integer.parseInt(planId));
//			designPlanBrand.setIsDeleted(0);
//			if (StringUtils.isBlank(brandIds)) {
//				Integer count = designPlanBrandService.getCount(designPlanBrand);
//				if (count.intValue() <= 0) {
//					return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "请选择品牌！", msgId);
//				}
//			} else {
//				String[] arr = brandIds.split(",");
//				brandIdList = Arrays.asList(arr);
//			}
//			/* 方案推荐 《测试发布》 需要校验 1.是否有封面，是否有720渲染，是否有照片级渲染 */
//			Map<String, String> resMap = designPlanRecommendedService.planIsTestCheck(designPlan, brandIdList);
//			if ("false".equals(resMap.get("success"))) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, resMap.get("data"), msgId);
//			}
//			// 进入测试发布
//			boolean flag = planIsRelease(designPlan, Integer.parseInt(isRelease), brandIdList, null, loginUser);
//			if (flag) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(true, "测试发布成功", msgId);
//			} else {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "取消发布失败", msgId);
//			}
//
//			/* 进入取消发布方法 */
//		} else if (isReleaseState == DesignPlanConstants.NO_RELEASE) {
//			boolean flag = designPlanRecommendedService.planNoRelease(designPlan);
//			if (flag) {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(true, "取消发布成功", msgId);
//			} else {
//				return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "取消发布失败", msgId);
//			}
//		} else {
//			return new ResponseEnvelope<DesignPlanRecommendedResult>(false, "参数isRelease错误", msgId);
//		}
//	}
//	
//
//	private boolean planIsRelease(DesignPlan designPlan,Integer isRelease, List<String> brandIds,List<String>  checkIdList, LoginUser loginUser) {
//		boolean flag = false;
//		if(designPlan==null){
//			return flag;
//		}
//		try{
//			int i = 0;
//			int j = 0 ;
//			// 设计师点发布  需要 选择审核人员，  测试发布则不需要
//			if(DesignPlanRelease.is_release == isRelease.intValue()){  
//				if(checkIdList!=null){
//					//通过设计方案id  删除 审核人员
//					designPlanCheckMapper.deletedBydesignPlanId(designPlan.getId());
//					for (String userId : checkIdList) {
//						DesignPlanCheck designPlanCheck = new DesignPlanCheck();
//						designPlanCheck.setIsDeleted(0);
//						designPlanCheck.setPlanId(designPlan.getId());
//						designPlanCheck.setUserId(Integer.parseInt(userId));
//						sysSave(designPlanCheck, loginUser);
//						designPlanCheckMapper.insertSelective(designPlanCheck);
//					}
//				}
//			}
//			if(brandIds!=null){
//				i = brandIds.size();
//				//这里可以改成批量新增
//				for (String brandId : brandIds) {
//					DesignPlanBrand designPlanBrand = new DesignPlanBrand();
//					designPlanBrand.setIsDeleted(0);
//					designPlanBrand.setPlanId(designPlan.getId());
//					designPlanBrand.setBrandId(Integer.parseInt(brandId));
//					sysSave(designPlanBrand, loginUser);
//					designPlanBrandMapper.insertSelective(designPlanBrand);
//					j  = j  + 1;
//				}
//			}
//			if(j == i){
//				DesignPlan newDesignPlan = new DesignPlan();
//				newDesignPlan.setIsRecommended(designPlan.getIsRecommended());
//				newDesignPlan.setIsDefaultDecorate(designPlan.getIsDefaultDecorate());
//				newDesignPlan.setPlanNumber(designPlan.getPlanNumber());
//				newDesignPlan.setDesignRecommendedStyleId(designPlan.getDesignRecommendedStyleId());
//				newDesignPlan.setId(designPlan.getId());
//				newDesignPlan.setGmtModified(new Date());
//				newDesignPlan.setReleaseTime(new Date());  //每次发布 更新发布时间，方便排序
//				if(DesignPlanRelease.is_release == isRelease.intValue() ){
//					newDesignPlan.setIsRelease(DesignPlanRelease.waiting_check);  //提交发布成功后进入审核
//				}
//				if(DesignPlanRelease.is_test == isRelease.intValue() ){
//					newDesignPlan.setIsRelease(DesignPlanRelease.is_test);  //测试发布  无需审核
//				}
//				designPlanMapper.updateByPrimaryKeySelective(newDesignPlan);
//				flag = true;
//			} 
//		}catch (Exception e) {
//			logger.error("planIsRelease  methods the error  :" + e);
//			return flag;
//		}
//		return flag;
//	}
//	
//	/**
//	 * 自动存储系统字段
//	 */
//	private void sysSave(DesignPlanBrand model, LoginUser loginUser){
//		if(model != null){
//			if(model.getId() == null){
//				model.setGmtCreate(new Date());
//				model.setCreator(loginUser.getLoginName());
//				model.setIsDeleted(0);
//				if(model.getSysCode()==null || "".equals(model.getSysCode())){
//					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
//				}
//			}
//
//			model.setGmtModified(new Date());
//			model.setModifier(loginUser.getLoginName());
//		}
//	}
//
//	/**
//	 * 自动存储系统字段
//	 */
//	private void sysSave(DesignPlanCheck model, LoginUser loginUser){
//		if(model != null){
//			if(model.getId() == null){
//				model.setGmtCreate(new Date());
//				model.setCreator(loginUser.getLoginName());
//				model.setIsDeleted(0);
//				if(model.getSysCode()==null || "".equals(model.getSysCode())){
//					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
//				}
//			}
//
//			model.setGmtModified(new Date());
//			model.setModifier(loginUser.getLoginName());
//		}
//	}
//
//	@Override
//	public ResponseEnvelope<BaseBrand> getRecommendedBrandList() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<BaseBrand> getCurrentBrandListByPlanId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<BaseBrand> delCurrentBrandListByPlanId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<DesignPlan> recommendDesignPlanDetails() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<BaseProductStyle> getDesignStyleList() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<SysUserPlanRecommended> getCheckUserListOfPlan() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<SysUserPlanRecommended> delCheckUserOfPlan() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<DesignPlan> checkDesignPlanRecommendedCheck() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<DesignPlanRecommendedResult> getCheckListOfDesignPlan() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEnvelope<DesignPlan> getRecommendedDesignPlanDetails() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
