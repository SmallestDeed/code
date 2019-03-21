package com.nork.design.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.nork.product.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.aes.model.AESFileConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.AppProperties;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.controller.web.IntelligenceDecorationController;
import com.nork.design.dao.DesignTempletProductMapper;
import com.nork.design.exception.IntelligenceDecorationException;
import com.nork.design.model.BedroomProductMatchDTO;
import com.nork.design.model.BedroomProductMatchDTO.DetailInfo;
import com.nork.design.model.BedroomProductMatchDTO.GroupMatchInfoDTO;
import com.nork.design.model.BedroomProductMatchDTO.ProductMatchInfoDTO;
import com.nork.design.model.BedroomProductMatchDTO.StructureMatchInfoDTO;
import com.nork.design.model.BedroomProductMatchDTO.WallTypeConstant;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductResult;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.DesignTempletProductResult;
import com.nork.design.model.DesignTempletResult;
import com.nork.design.model.ProductListByTypeInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanGroupInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.design.model.ProductListByTypeInfo.PlanStructureInfo;
import com.nork.design.model.TempletProductIndexInfo;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.model.unity.ProductDataEx;
import com.nork.design.model.unity.ProductIndexConfig;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.IntelligenceDecorationService;
import com.nork.design.service.TempletProductIndexInfoService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.PrepProductPropsInfoService;
import com.nork.product.service.StructureProductService;
import com.nork.product.service.impl.ProductCategoryRelServiceImpl;
import com.nork.product.service.impl.BaseProductServiceImpl.getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum;
import com.nork.productprops.service.ProductPropsService;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.SysDictionaryService;
import com.nork.user.model.UserTypeCode;

import net.sf.json.JSONObject;

@Service("intelligenceDecorationService")
@Transactional
public class IntelligenceDecorationServiceImpl implements IntelligenceDecorationService {

	private static Logger logger = Logger.getLogger(IntelligenceDecorationServiceImpl.class);
	
	// 白膜背景墙valukeyList
	private static List<String> beijingValuekeyList = Utils.getListFromStr(
			Utils.getValueByFileKey(AppProperties.APP, AppProperties.SMALLPRODUCTTYPE_BEIJINGWALL_FILEKEY, "basic_dians,basic_shaf,basic_cant,basic_chuangt,basic_xingx,basic_beij")
			);
	
	private static List<Integer> beijingValueList = null;
	
	private static int beijingRange = Integer.parseInt(Utils.getValue("app.filter.stretch.length", "10"));
	
	private static final String CLASSNAME = "[IntelligenceDecorationServiceImpl]";
	
	/*private static final String PRODUCT_TYPE_1 = "1";
	private static final String PRODUCT_TYPE_2 = "2";
	private static final String PRODUCT_TYPE_3 = "3";*/
	
	@Autowired
	private DesignPlanService designPlanService;
	
	@Autowired
	private DesignTempletService designTempletService;
	
	@Autowired
	private ResDesignService resDesignService;
	
	@Autowired
	private ResFileService resFileService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private BaseProductMapper baseProductMapper;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private DesignPlanProductService designPlanProductService;
	
	@Autowired
	private StructureProductService structureProductService;
	
	@Autowired
	private DesignTempletProductService designTempletProductService;
	
	@Autowired
	private DesignTempletProductMapper designTempletProductMapper;
	
	@Autowired
	private TempletProductIndexInfoService templetProductIndexInfoService;
	
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;

    @Autowired
    private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
    
    @Autowired
    private GroupProductService groupProductService;
    
    @Autowired
    private ProductPropsService productPropsService;

    @Autowired
    private PrepProductPropsInfoService prepProductPropsInfoService;
    
	/**
	 * @param recommendedPlanId 推荐方案ID
	 * @param designTempletId 样板房ID
	 * @param msgId
	 * @return
	 */
	@Override
	public ResponseEnvelope<Map<String,String>> findDesignConfig(Integer recommendedPlanId, Integer designTempletId, String msgId) {
		ResponseEnvelope <Map<String,String>>result = null;
		Map<String,String> map = new HashMap<>();
//		DesignPlan designPlan = designPlanService.get(recommendedPlanId) ;
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designPlanRecommended == null || designTemplet == null) {
			result = new ResponseEnvelope<>(false, "找不到推荐的方案或样板房！", msgId);
			return result;
		}
		
		if (designPlanRecommended.getDesignTemplateId() == designTempletId) {
			result = new ResponseEnvelope<Map<String,String>>(true, map, msgId);
			return result;
		}

		if (designPlanRecommended.getConfigFileId() != null) {
			ResDesign resDesign = resDesignService.get(designPlanRecommended.getConfigFileId());
			map.put("designPlanRecommendConfigPath", resDesign == null ? "" : resDesign.getFilePath());
		}
		if (designTemplet.getConfigFileId() != null) {
			ResFile resFile = resFileService.get(designTemplet.getConfigFileId());
			map.put("designTempletConfigPath", resFile == null ? "" : resFile.getFilePath());
		}
		result = new ResponseEnvelope<Map<String,String>>(true, map, msgId);//success, obj, msgId
		return result;
	}

	@Override
	public ResponseEnvelope<BaseProduct> disposeOneKeyDecorateData(String templetProductCode, String designTempletProductCode,
			String msgId, LoginUser loginUser) {
		ResponseEnvelope<BaseProduct> result = null;
		Map<String,String> map = new HashMap<String,String>();
		BaseProduct baseProduct =  baseProductService.findDataByCode(templetProductCode);
		BaseProduct bmBaseProduct = baseProductService.findDataByCode(designTempletProductCode);
		if(baseProduct == null || bmBaseProduct == null) {
			result = new ResponseEnvelope<BaseProduct>(false, "找不到产品productCode="+templetProductCode+" 或 "+designTempletProductCode, msgId);
			logger.error("找不到产品productCode="+templetProductCode+" 或 "+designTempletProductCode);
			return result;
		}
		
		BaseProductSearch searchProduct = new BaseProductSearch();
		if ("1".equals(baseProduct.getProductTypeValue()) || "3".equals(baseProduct.getProductTypeValue())) {
			searchProduct.setBmIds(String.valueOf(bmBaseProduct.getId()));
		} else {
			searchProduct.setProductTypeValue(bmBaseProduct.getProductTypeValue());
		}
		searchProduct.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
		searchProduct.setProductTypeCode(baseProduct.getProductTypeMark());
		searchProduct.setPutawayStateList(baseProductService.getPutawayList(loginUser));
		searchProduct.setStyleId(baseProduct.getStyleId());
		searchProduct.setProductStyleIdInfo(baseProduct.getProductStyleIdInfo());
		searchProduct.setColorId(baseProduct.getColorId());
		searchProduct.setStart(0);
		searchProduct.setLimit(1);
		List<BaseProduct> productList = baseProductService.getProductData(searchProduct);
		if (productList != null && productList.size() > 0) {
			BaseProduct productObj = productList.get(0);
			map.put("productCode",productObj.getProductCode());
			map.put("bmProductCode",designTempletProductCode);
		}
		return new ResponseEnvelope<BaseProduct>(true, map, msgId);
	}

	/**
	 * 设置返回的map值
	 * @author xiaoxc
	 * @param templetProduct
	 * @param productCode
	 * @return
	 */
	public Map<String,String> setMap(DesignProductResult templetProduct,String productCode){
		//区分墙结构 0、实体墙  1、背景墙 2、门框
		String bgWall = "0";
		if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey())) {
			bgWall = Utils.getIsBgWall(templetProduct.getSmallTypeKey())+"";
		}
		Map<String,String> map = new HashMap<>();
		map.put("bmPosName",templetProduct.getPosName());
		map.put("productCode",productCode);
		map.put("isBgWall",bgWall);
		map.put("matchLogInfo",templetProduct.getMatchErrorInfo());

		return map;
	}

	/**
	 * 设置返回匹配不到的产品map值
	 * @author xiaoxc
	 * @param templetProduct
	 * @return
	 */
	public Map<String,String> setMap(DesignProductResult templetProduct){
		Map<String,String> map = new HashMap<>();
		map.put("bmPosName",templetProduct.getPosName());
		map.put("productCode","");
		map.put("isBgWall","0");
		map.put("matchLogInfo",templetProduct.getMatchErrorInfo());
		return map;
	}

	public boolean isMatchingPlanProduct(DesignProductResult templetProduct, DesignProductResult planProduct, StringBuffer matchErrorInfo){
		String bjType = Utils.getValue("app.smallProductType.stretch", "");
		int filterLength = Utils.getIntValue(Utils.getValue("app.filter.stretch.length", "0"));
		int curtainLength = Utils.getIntValue(Utils.getValue("app.filter.3mAbove.stretch.length", "0"));

		boolean stretch = Utils.isMateProductType(templetProduct.getSmallTypeKey(), bjType);
		int planProductLength = Utils.getIntValue(planProduct.getProductLength());
		int planProductHeight = Utils.getIntValue(planProduct.getProductHeight());
		int planProductWidth = Utils.getIntValue(planProduct.getProductWidth());
		int templetProductLength = Utils.getIntValue(templetProduct.getProductLength());
		int templetProductHeight = Utils.getIntValue(templetProduct.getProductHeight());
		int templetProductWidth = Utils.getIntValue(templetProduct.getProductWidth());

		if (stretch) {
			int fullPaveLength = 0;
			int startLength = 0;
			int endLength = 0;
			if (StringUtils.isNotEmpty(templetProduct.getFullPaveLength())) {
				fullPaveLength = Utils.getIntValue(templetProduct.getFullPaveLength()) ;
			}else{
//				fullPaveLength = Utils.getIntValue(templetProduct.getProductLength());
			}
			if (fullPaveLength < 1) {
				matchErrorInfo.append("白膜产品编码"+templetProduct.getProductCode()+"无全铺长度;");
				templetProduct.setMatchErrorInfo(matchErrorInfo.toString());
				return false;
			}else {
				if ("16".equals(templetProduct.getBigTypeValue()) && fullPaveLength > 300) {
					startLength = fullPaveLength - curtainLength;
					endLength = fullPaveLength + curtainLength;
				}else{
					startLength = fullPaveLength - filterLength;
					endLength = fullPaveLength + filterLength;
				}
			}
			if (startLength < planProductLength && planProductLength <= endLength){
				if ("3".equals(templetProduct.getBigTypeValue())) { //背景墙逻辑
					if ("30".equals(templetProduct.getProductHeight()) || "50".equals(templetProduct.getProductHeight())) {
						if (planProductHeight == templetProductHeight) {
							return true;
						}else {
							return false;
						}
					}else{
						if (planProductHeight == templetProductHeight || (planProductHeight > 50 && planProductHeight < 230)) {
							return true;
						}else{
							return false;
						}
					}
				}else {//窗帘、淋浴屏。。。
					if (planProductHeight == templetProductHeight) {
						return true;
					}else {
						return false;
					}
				}
			}else{
				return false;
			}
		}

		//过滤产品长、 高信息
		String productSmallTypes_LH = Utils.getValue("filter.productLH.productSmallType","");
		if (Utils.isMateProductType(templetProduct.getSmallTypeKey(),productSmallTypes_LH)) {
			if (planProductHeight == templetProductHeight && planProductLength == templetProductLength) {
				return true;
			}else{
				return false;
			}
		}

		//过滤产品长、宽信息
		String productSmallTypes_LW = Utils.getValue("filter.productLW.productSmallType","");
		if (Utils.isMateProductType(templetProduct.getSmallTypeKey(),productSmallTypes_LW)) {
			if (planProductLength == templetProductLength && planProductWidth <= templetProductWidth) {
				return true;
			}else{
				return false;
			}
		}

		return true;
	}

	/**
	 * 按条件匹配系统单品
	 * @author xiaoxc
	 * @param templetProduct
	 * @param planProduct
	 * @param loginUser
	 * @return
	 */
	private BaseProduct getMatchSystemProduct(DesignProductResult templetProduct,DesignProductResult planProduct,LoginUser loginUser){
		BaseProduct matchProductData = null;
		BaseProductSearch searchProduct = new BaseProductSearch();
		searchProduct.setProductTypeValue(templetProduct.getBigTypeValue());
		baseProductService.getProductInfoFilter(searchProduct,templetProduct);
		searchProduct.setProductTypeCode(templetProduct.getBigTypeKey());
		searchProduct.setPutawayStateList(baseProductService.getPutawayList(loginUser));
		searchProduct.setStart(0);
		searchProduct.setLimit(1);
		searchProduct.setProductStyleIdInfo(planProduct.getProductStyleIdInfo());
		searchProduct.setColorId(planProduct.getColorId());
		searchProduct.setProductSmallTypeValue(planProduct.getSmallTypeValue());
		searchProduct.setStyleId(planProduct.getProStyleId());
		searchProduct.setProductModelNumber(planProduct.getProductModelNumber());
		if ("tianh".equals(templetProduct.getBigTypeKey())) {
			searchProduct.setMeasureCode(templetProduct.getMeasureCode());
			searchProduct.setStyleId(planProduct.getStyleId());
			searchProduct.setRegionMark(templetProduct.getRegionMark());
		}
		// 如果是背景墙
		if(beijingValuekeyList.indexOf(templetProduct.getSmallTypeKey()) != -1) {
			// 可以搜索多小类,但是本小类排前面
			searchProduct.setProductSmallTypeValue(null);
			searchProduct.setSmallTypeValueListForShowAll(this.getBeijingValueList());
			searchProduct.setOrderSmallTypeValue(planProduct.getSmallTypeValue());
		}
		List<BaseProduct> productList = baseProductService.getProductData(searchProduct);
		if (productList != null && productList.size() > 0) {
			matchProductData = productList.get(0);
			return matchProductData;
		}else{
			return null;
		}
	}

	/**
	 * 按条件匹配单品
	 * @author xiaoxc
	 * @vserion:1.0版本
	 * @param baseProduct
	 * @param designPlanId
	 * @param type
	 * @param cacheEnable
	 * @param loginUser
	 * @return
	 */
	private BaseProduct getMatchingProductData(BaseProduct baseProduct,Integer designPlanId,String type,Integer temp,String cacheEnable, LoginUser loginUser){

		BaseProduct matchProductData = null;
		BaseProductSearch searchProduct = new BaseProductSearch();
		searchProduct.setProductTypeValue(baseProduct.getProductTypeValue());
		searchProduct.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
//		baseProductService.getProductInfoFilter(searchProduct,baseProduct);
		searchProduct.setProductTypeCode(baseProduct.getProductTypeMark());
		searchProduct.setPutawayStateList(baseProductService.getPutawayList(loginUser));
		searchProduct.setPlanId(designPlanId);
		if("15".equals(baseProduct.getProductTypeValue())) {
			searchProduct.setStart(temp);
		}else{
			searchProduct.setStart(0);
		}
		searchProduct.setLimit(1);
		List<BaseProduct> productList = new ArrayList<>();
		List<BaseProduct> planProductList = null;
		
		if( "templetType".equals(type) ){
			//实体墙直接取模板贴图
			if( ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(baseProduct.getProductTypeMark()) && baseProduct.getProductTypeValue() != null && baseProduct.getProductSmallTypeValue() == 8 ){
				if( StringUtils.equals("1", cacheEnable) ){
					productList = BaseProductCacher.getByPlanIdProductList(designPlanId,baseProduct.getProductTypeValue(),baseProduct.getProductSmallTypeValue());
				}else{
					productList = baseProductService.getByPlanIdProductList(designPlanId,baseProduct.getProductTypeValue(),baseProduct.getProductSmallTypeValue());
				}
			}else{
				productList = baseProductService.getTempletProductData(searchProduct);
			}
		}else{
			String bjType = Utils.getValue("app.smallProductType.stretch", "");
			boolean stretch = Utils.isMateProductType(baseProduct.getProductSmallTypeMark(), bjType);
			//如果样板房中的白膜，在设计方案中未找到对应的产品，该白膜删除，不随机匹配
			if( stretch && baseProduct.getProductTypeValue() != null && baseProduct.getProductSmallTypeValue() != null || "4".equals(baseProduct.getProductTypeValue())){
				planProductList = baseProductService.getByPlanIdProductList(designPlanId,baseProduct.getProductTypeValue(),baseProduct.getProductSmallTypeValue());
				if( Lists.isNotEmpty(planProductList) ){
					BaseProduct planProduct = planProductList.get(0) ;
					searchProduct.setProductStyleIdInfo(planProduct.getProductStyleIdInfo());
					searchProduct.setColorId(planProduct.getColorId());
//					searchProduct.setProductModelNumber(planProduct.getProductModelNumber());
					searchProduct.setProductSmallTypeValue(planProduct.getProductSmallTypeValue());
					searchProduct.setStyleId(planProduct.getStyleId());
					productList = baseProductService.getProductData(searchProduct);
				}
			}
//			productList = baseProductService.getProductData(searchProduct);
		}
		if (productList != null && productList.size() > 0) {
			matchProductData = productList.get(0);
			//区分墙结构 0、实体墙  1、背景墙 2、门框
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(baseProduct.getProductTypeMark())) {
				matchProductData.setBgWall(Utils.getIsBgWall(baseProduct.getProductSmallTypeMark()));
			} else {
				matchProductData.setBgWall(0);
			}
			return matchProductData;
		}else{
			return null;
		}
	}

	@Override
	public ResponseEnvelope<UnityDesignPlan> findGeneratePlanInfo(Integer designTempletId, DesignPlanRecommended designPlanRecommended,
			String context, String msgId , LoginUser loginUser, String mediaType) {
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designTemplet == null) {
			return new ResponseEnvelope<>(false, "找不到样板房！designTempletId = " + designTemplet, msgId);
		}
		DesignPlan designPlan = new DesignPlan();
		designPlan.setDesignTemplateId(designTempletId);
		designPlan.setRecommendedPlanId(designPlanRecommended.getId());
		/** 通过样板房创建设计方案信息 **/
		designPlan = designPlanService.createDesignPlan(designPlan,designPlanRecommended,designTemplet,mediaType,loginUser, null, null);
//		designPlan = optimizePlanService.createDesignPlan(recommendedPlanId ,designPlan,designTemplet,mediaType,loginUser);

		if( designPlan == null ){
			return new ResponseEnvelope<>(false, "创建设计方案失败！", msgId);
		}
		//配置内容存储路径
//		String configKey = Utils.getValue("design.designPlan.u3dconfig.upload.path","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/");
//		configKey = configKey.replace("[code]",designPlan.getPlanCode());
		String configKey = Utils.getPropertyName("config/res", "design.designPlan.u3dconfig.upload.path", "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/").trim();
		configKey = Utils.replaceDate(configKey);
		//文件名称
		String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
		//先把文件保存到本地
		/*String filePath = Constants.UPLOAD_ROOT + configKey + fileName;*/
		String filePath = Utils.getAbsolutePath(configKey + fileName, null);
		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
			filePath = filePath.replace("\\", "/");
		}
		/**写入文件存储到指定目录**/
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,context);
		if( uploadFtpFlag ){
			Map <String, String>map = FileUploadUtils.getMap(new File(filePath), "design.designPlan.u3dconfig.upload.path", false);
			//生成资源数据
			boolean flag = resDesignService.saveResDesign(designPlan,map);
//			boolean flag = optimizePlanService.saveResDesign(designPlan,map);
			if( !flag ){
				return new ResponseEnvelope<>(false, "保存资源数据库失败！", msgId);
			}
		}else{
			return new ResponseEnvelope<>(false, "保存设计方案配置文件失败！", msgId);
		}
		/** 解析配置文件生成设计方案列表 **/
		JSONObject resultJSON = null;
		try {
			resultJSON = designPlanProductService.analysisJson(designTempletId,designPlanRecommended.getId(),designPlan,context, loginUser);
//			resultJSON = optimizePlanService.analysisJson(designTempletId,recommendedPlanId,designPlan,context, loginUser);
			if( resultJSON != null && !resultJSON.getBoolean("success") ){
				return new ResponseEnvelope<>(false, resultJSON.getBoolean("message"), msgId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<>(false, "保存设计方案产品列表异常！", msgId);
		}
		
		//获取设计方案信息
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope)designPlanService.getDesignPlanInfo(designPlan.getId(),null,null,null,null,false,loginUser,mediaType, null);

		if( responseEnvelopeInfo.isSuccess() ){
			unityDesignPlan = (UnityDesignPlan)responseEnvelopeInfo.getObj();
		}else{
			logger.error(responseEnvelopeInfo.getMessage());
			return new ResponseEnvelope<>(false, "进入设计方案失败！", msgId);
		}
		unityDesignPlan = designPlanService.wrapperData(designPlan.getId(), unityDesignPlan);

		return new  ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);
		
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
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
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
	 * 自动存储系统字段
	 */
	private void sysSave(ResDesign model, LoginUser loginUser) {
		if (model != null) {
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
//	@Override
//	public ResponseEnvelope<SearchStructureProductResult> matchingStructureData(String structureCodes,
//			Integer designTempletId, String msgId, LoginUser loginUser) {
//		// TODO Auto-generated method stub
//		String cacheEnable=Utils.getValue("redisCacheEnable", "0");
//		DesignTemplet designTemplet = null;
//		if(StringUtils.equals("1", cacheEnable)){
//			designTemplet = DesignCacher.getTemplet(designTempletId);
//		}else{
//			designTemplet = designTempletService.get(designTempletId);
//		}
//		if (designTemplet == null) {
//			return new ResponseEnvelope<SearchStructureProductResult>(false, "样板房不存在", msgId);
//		}
//
//		List<SearchStructureProductResult> list = new ArrayList<SearchStructureProductResult>();
//		String codesArry[] = structureCodes.split(",");
//		for (String structureCode : codesArry) {
//			StructureProductSearch structureProductSearch = new StructureProductSearch();
//			StructureProduct structureProduct = null;
//			//通过结构编码找到结构对象
//			structureProductSearch.setStructureCode(structureCode);
//			structureProductSearch.setIsDeleted(0);
//			structureProductSearch.setStart(0);
//			structureProductSearch.setLimit(1);
//			List<StructureProduct> structureList = structureProductService.getPaginatedList(structureProductSearch);
//
//			if (Lists.isNotEmpty(structureList)) {
//				structureProduct = structureList.get(0);
//			} else {
//				return new ResponseEnvelope<SearchStructureProductResult>(false, "方案结构不存在！structureCode=" + structureCode, msgId);
//			}
//			//搜索匹配的结构及结构明细
//			StructureProductSearch templetStructureSearch = new StructureProductSearch();
//			templetStructureSearch.setStructureType(structureProduct.getStructureType());//结构类型
//			templetStructureSearch.setTempletId(designTempletId);
//			templetStructureSearch.setStructureNumber(structureProduct.getStructureNumber());
//			templetStructureSearch.setStyleId(structureProduct.getStyleId());//结构款式
//			List<Integer> statusList = new ArrayList<>();
//			if (loginUser.getUserType().intValue() == UserTypeCode.USER_TYPE_INNER.intValue()) {
//				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
//				statusList.add(StructureProductStatusCode.TESTING);
//				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
//			} else {
//				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
//			}
//			templetStructureSearch.setStatusList(statusList);
//			templetStructureSearch.setIsDeleted(0);
//			templetStructureSearch.setStart(0);
//			templetStructureSearch.setLimit(1);
//			List<StructureProduct> templetStructureList = structureProductService.getStructureObject(templetStructureSearch);
//			StructureProduct structureProduct1 = null;
//			if (Lists.isNotEmpty(templetStructureList)) {
//				structureProduct1 = templetStructureList.get(0);
//			} else {
//				return new ResponseEnvelope<SearchStructureProductResult>(false, "找不到匹配" + structureCode + "的结构！designTempletId=" + designTempletId, msgId);
//			}
//			//结构列表json
//			SearchStructureProductResult searchStructureProductResult = structureProductService.getSearchStructureProductResult(structureProduct1);
//			list.add(searchStructureProductResult);
//		}
//		int total = Utils.getListTotal(list) ;
//
//		return new ResponseEnvelope<SearchStructureProductResult>(total,list, msgId);
//	}

	@Override
	public ResponseEnvelope<DesignTempletProductResult> findDesignTempletProductList(Integer designTempletId,
																					 Integer groupType, String msgId) {
		DesignTempletProduct designTempletproduct = new DesignTempletProduct();
		designTempletproduct.setDesignTempletId(designTempletId);
		designTempletproduct.setGroupType(groupType);
		List<DesignTempletProductResult> list = designTempletProductService.getTempletIdProductList(designTempletproduct);
		int total = Utils.getListTotal(list);
		return new  ResponseEnvelope<DesignTempletProductResult>(total,list, msgId);
	}

	@Override
	public ResponseEnvelope<SearchStructureProductResult> matchingStructureData(String structureCodes,
											Integer designTempletId, Integer designPlanId, String msgId, LoginUser loginUser) {
		DesignTemplet designTemplet = null;
		if(Utils.enableRedisCache()){
			designTemplet = DesignCacher.getTemplet(designTempletId);
		}else{
			designTemplet = designTempletService.get(designTempletId);
		}
		if (designTemplet == null) {
			return new ResponseEnvelope<SearchStructureProductResult>(false, "样板房不存在", msgId);
		}

		List<SearchStructureProductResult> list = new ArrayList<SearchStructureProductResult>();
//		String codesArry[] = structureCodes.split(",");
//		List<String> codesArrys = Arrays.asList(codesArry);
		List<StructureProduct> structureList = structureProductService.getStructuresByDesignPlanId(designPlanId);

		Map<String,StructureProduct> structure_map = new HashMap<>();
		if (structureList != null && structureList.size() > 0) {
			for (StructureProduct structureProduct : structureList){
				structure_map.put(structureProduct.getStructureCode(),structureProduct);
			}
		}
		List<DesignTempletProduct> templetStructureProducts = designTempletProductService.getStructureProductByDesignId(designTempletId);

		for (DesignTempletProduct templetProduct : templetStructureProducts) {
			SearchStructureProductResult searchStructureProductResult = null;
			int djTemp = 0;
			StructureProduct djStructureProduct = null;
			for (StructureProduct planStructureProduct : structureList) {
//				StructureProduct planStructureProduct = structure_map.get(structureCode);
				//匹配方案结构区域、尺寸代码
				if ("DJ".equals(templetProduct.getStructureType()) && "DJ".equals(planStructureProduct.getStructureType())) {
					//如果是主地面则匹配方案主地面，没有当单品处理
					if ("1".equals(templetProduct.getRegionMark())) {
						if ("1".equals(planStructureProduct.getPlanStructureRegionMark())) {
							djTemp++;
							if (templetProduct.getMeasureCode().equals(planStructureProduct.getMeasureCode())) {
								//结构详情列表json
								searchStructureProductResult = structureProductService.getSearchStructureProductResult(planStructureProduct, templetProduct.getStructureCode());
								break;
							} else {
								StructureProduct structure = matcheSystemStructure(templetProduct, planStructureProduct, loginUser);
								if (structure != null) {
									//结构详情列表json
									searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure, templetProduct.getStructureCode());
									break;
								}
							}
						}
					}else {
						if (templetProduct.getRegionMark().equals(planStructureProduct.getPlanStructureRegionMark())) {
							djTemp++;
							if (templetProduct.getMeasureCode().equals(planStructureProduct.getMeasureCode())) {
								//结构详情列表json
								searchStructureProductResult = structureProductService.getSearchStructureProductResult(planStructureProduct,templetProduct.getStructureCode());
								break;
							} else {
								StructureProduct structure = matcheSystemStructure(templetProduct, planStructureProduct, loginUser);
								if (structure != null) {
									//结构详情列表json
									searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure,templetProduct.getStructureCode());
									break;
								}
							}
						} else {
							djStructureProduct = planStructureProduct;//记录该地面结构需要用它的款式去匹配（structureList有按区域正序排序，取最后一条去匹配）
							continue;
						}
					}
				} else {
					StructureProduct structure = matcheSystemStructure(templetProduct, planStructureProduct, loginUser);
					if (structure != null) {
						//结构详情列表json
						searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure,templetProduct.getStructureCode());
						break;
					}
				}
			}
			if (!"1".equals(templetProduct.getRegionMark()) && djTemp == 0) {
				StructureProduct structure = matcheSystemStructure(templetProduct, djStructureProduct, loginUser);
				if (structure != null) {
					//结构详情列表json
					searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure,templetProduct.getStructureCode());
				}
			}
			if (searchStructureProductResult != null) {
				list.add(searchStructureProductResult);
			}
		}
		int total = Utils.getListTotal(list) ;

		return new ResponseEnvelope<SearchStructureProductResult>(total,list, msgId);
	}

	/**
	 * 根据方案结构类型序号和样板房款式和尺寸代码匹配结构
	 * @author xiaoxc
	 * @param designTempletProduct
	 * @param planStructure
	 * @param loginUser
	 * @return
	 */
	private StructureProduct matcheSystemStructure(DesignTempletProduct designTempletProduct,StructureProduct planStructure,LoginUser loginUser){
		
		// 参数验证 ->start
		if(planStructure == null){
			return null;
		}
		// 参数验证 ->start
		
		//匹配结构条件搜索
		StructureProductSearch templetStructureSearch = new StructureProductSearch();
		templetStructureSearch.setStructureType(planStructure.getStructureType());//方案结构类型
		templetStructureSearch.setStructureNumber(planStructure.getStructureNumber());//方案结构序号
		templetStructureSearch.setStyleId(planStructure.getStyleId());//结构款式
		//通用结构按尺寸代码匹配，定制按样板房ID匹配
		if (designTempletProduct.getIsStandard() == 1) {
			templetStructureSearch.setMeasureCode(designTempletProduct.getMeasureCode());//尺寸代码
		}else{
			templetStructureSearch.setTempletId(designTempletProduct.getDesignTempletId());
		}
		List<Integer> statusList = new ArrayList<>();
		if (loginUser.getUserType().intValue() == UserTypeCode.USER_TYPE_INNER.intValue()) {
			statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
			statusList.add(StructureProductStatusCode.TESTING);
			statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
		} else {
//			statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
//			statusList.add(StructureProductStatusCode.TESTING);
			statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
		}
		templetStructureSearch.setStatusList(statusList);
		templetStructureSearch.setIsDeleted(0);
		templetStructureSearch.setStart(0);
		templetStructureSearch.setLimit(1);
		List<StructureProduct> templetStructureList = structureProductService.getStructureObject(templetStructureSearch);
		if (templetStructureList != null && templetStructureList.size() > 0) {
			StructureProduct stp = templetStructureList.get(0);
			stp.setPlanStructureRegionMark(planStructure.getPlanStructureRegionMark());
			return stp;
		}else{
			return null;
		}
	}

	@Override
	public ResponseEnvelope<MatchGroupProductResult> getGroupProductData(Integer templetId, Integer designTempletId,
			String msgId) {
		List<MatchGroupProductResult> list = new ArrayList<MatchGroupProductResult>();
		DesignPlan designPlan = designPlanService.get(templetId);
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if( designPlan== null || designTemplet == null){
			return new ResponseEnvelope<>(true, "找不到模板或样板房！", msgId);
		}
		//查询样板房主组合
		DesignTempletProduct designTempletProduct = new DesignTempletProduct();
		designTempletProduct.setDesignTempletId(designTempletId);
		designTempletProduct.setGroupType(0);
		designTempletProduct.setIsDeleted(0);
		designTempletProduct.setIsMainProduct(1);
		// 样板房中有哪些白膜组合(取每个白膜组合的主产品)
		List<DesignTempletProduct> templetList = designTempletProductService.getByTempletIdMainProduct(designTempletProduct);
		if( Lists.isEmpty(templetList) ){
			return new ResponseEnvelope<>(true, "样板房无白膜组合产品！", msgId);
		}
		//模板组合主产品
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setPlanId(templetId);
		designPlanProduct.setGroupType(0);
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setIsMainProduct(1);
		// 方案推荐中有哪些组合(取每个白膜组合的主产品)
		List<DesignPlanProductResult> planList = designPlanProductService.getByPlanIdGroupMainProduct(designPlanProduct);
		if( Lists.isEmpty(planList) ){
			return new ResponseEnvelope<>(true, "推荐方案中没有组合", msgId);
		}
		List<DesignPlanProductResult> addList = new ArrayList<>();
		MatchGroupProductResult matchGroupProductResult = null ;
		for ( DesignTempletProduct templetProduct : templetList){
			matchGroupProductResult = new MatchGroupProductResult();
			int temp = 0 ;
			for (DesignPlanProductResult planProduct : planList) {
				// 推荐方案组合的主产品分类信息
				String productTypeValue = planProduct.getProductTypeValue();
				Integer productSmallTypeValue = planProduct.getProductSmallTypeValue();
				//匹配同分类组合产品
				if (productTypeValue != null && Utils.getIntValue(productTypeValue) == templetProduct.getProductTypeValue().intValue()) {
					//柜子按小类过滤组合，临时解决方案，后续加组合类型过滤
					if ("10".equals(productTypeValue)) {
						if(productSmallTypeValue == 1 && templetProduct.getProductSmallTypeValue() == 14) {

						} else if (productSmallTypeValue == 2 && templetProduct.getProductSmallTypeValue() == 15) {

						} else if (productSmallTypeValue == 11 && templetProduct.getProductSmallTypeValue() == 15) {

						} else {
							continue;
						}
					}
					temp ++ ;
					planList.remove(planProduct);//匹配到就移除（eg：餐柜有两套不能重复匹配）
					addList.add(planProduct);//预防二次匹配使用
					getGroupMatcheData(planProduct,matchGroupProductResult,templetId);

					break;
				}else{
					continue;
				}
			}
			//二次匹配
			if (temp == 0) {
				for (DesignPlanProductResult planProduct : addList) {
					String productTypeValue = planProduct.getProductTypeValue();
					Integer productSmallTypeValue = planProduct.getProductSmallTypeValue();
					//匹配同分类组合产品
					if (productTypeValue != null && Utils.getIntValue(productTypeValue) == templetProduct.getProductTypeValue().intValue()) {
						//柜子按小类过滤组合，临时解决方案，后续加组合类型过滤
						if ("10".equals(productTypeValue)) {
							if(productSmallTypeValue == 1 && templetProduct.getProductSmallTypeValue() == 14) {

							} else if (productSmallTypeValue == 2 && templetProduct.getProductSmallTypeValue() == 15) {

							} else {
								continue;
							}
						}
						temp ++ ;
						getGroupMatcheData(planProduct,matchGroupProductResult,templetId);
						break;
					}else{
						continue;
					}
				}
			}
			if (temp > 0) {
				matchGroupProductResult.setBmMianProductCode(templetProduct.getProductCode());
//				List<GroupProductDetails> groupProductCodeList = groupProductDetailsService.getByGroupIdProductCodeList(templetProduct.getProductGroupId());
				DesignTempletProduct templetGroupProduct = new DesignTempletProduct();
				templetGroupProduct.setDesignTempletId(designTempletId);
				templetGroupProduct.setProductGroupId(templetProduct.getProductGroupId());
				templetGroupProduct.setPlanGroupId(templetGroupProduct.getPlanGroupId());
				templetGroupProduct.setIsDeleted(0);
				templetGroupProduct.setGroupType(0);
				List<DesignTempletProduct> groupProductList = designTempletProductMapper.byTempletIdMainProduct(templetGroupProduct);
				List<Map<String,String>> bmGroupProductList = new ArrayList<>();
				Map<String,String> map = null;
				if (Lists.isNotEmpty(groupProductList)) {
					for (DesignTempletProduct groupProduct : groupProductList) {
						map = new HashMap<>();
						map.put("productCode",groupProduct.getProductCode());
						map.put("posIndexPath",groupProduct.getPosIndexPath());
						bmGroupProductList.add(map);
					}
				}
				matchGroupProductResult.setBmGroupProductList(bmGroupProductList);
				list.add(matchGroupProductResult);
			}
		}
		int total = Utils.getListTotal(list) ;

		return new ResponseEnvelope<MatchGroupProductResult>(total,list, msgId);
	}

	/**
	 * 拼装匹配数据(要添加的组合产品信息)
	 * 
	 * @param planProduct
	 * @param matchGroupProductResult
	 * @param planId
	 */
	private void getGroupMatcheData(DesignPlanProductResult planProduct,MatchGroupProductResult matchGroupProductResult,Integer planId){
		matchGroupProductResult.setTempletMianProductCode(planProduct.getProductCode());
		matchGroupProductResult.setTempletPosName(planProduct.getPosName());
		matchGroupProductResult.setPlanGroupId(planProduct.getPlanGroupId());
		// 目前数据库 location字段， 分为 txt 文件路径、json 串。
		String filePath = planProduct.getFilePath();
		if ( filePath != null && !filePath.trim().isEmpty() ) {
			/*String url = app.getString("app.upload.root") + filePath;*/
			String url = Utils.getAbsolutePath(filePath, Utils.getAbsolutePathType.encrypt);
			String text = FileUploadUtils.getFileContext(url);
			matchGroupProductResult.setTempletGroupConfig(text);
		}else{
			matchGroupProductResult.setTempletGroupConfig(planProduct.getLocation());
		}

		//方案组合产品 （方案相同产品客户端无法匹配）
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setPlanId(planId);
		designPlanProduct.setGroupType(0);
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setProductGroupId(planProduct.getGroupId());
		List<DesignPlanProductResult> planList = designPlanProductService.getByPlanIdGroupMainProduct(designPlanProduct);
		Map<String,String> map = null;
		List<Map<String,String>> groupProductList = new ArrayList<>();
		if (Lists.isNotEmpty(planList)) {
			for (DesignPlanProductResult groupProduct : planList) {
				map = new HashMap<>();
				map.put("productCode",groupProduct.getProductCode());
				map.put("posIndexPath",groupProduct.getPosIndexPath());
				groupProductList.add(map);
			}
		}
		matchGroupProductResult.setTempletGroupProductList(groupProductList);
	}

	private void getGroupMatcheDataV2(
			DesignPlanRecommendedProduct designPlanRecommendedProduct,
			MatchGroupProductResult matchGroupProductResult,
			Integer planRecommendedId
			){
		
		// 参数验证 ->start
		
		// 参数验证 ->end
		
		matchGroupProductResult.setTempletMianProductCode(designPlanRecommendedProduct.getProductCode());
		matchGroupProductResult.setTempletPosName(designPlanRecommendedProduct.getPosName());
		matchGroupProductResult.setPlanGroupId(designPlanRecommendedProduct.getPlanGroupId());
		// 目前数据库 location字段， 分为 txt 文件路径、json 串。
		String filePath = designPlanRecommendedProduct.getFilePath();
		logger.error("getGroupMatcheDataV2().filePath = " + filePath);

		if ( filePath != null && !filePath.trim().isEmpty() ) {
			/*String url = app.getString("app.upload.root") + filePath;*/
			String url = Utils.getAbsolutePath(filePath, Utils.getAbsolutePathType.encrypt);
			logger.error("getGroupMatcheDataV2().url = " + url);
			String text = FileUploadUtils.getFileContext(url);
			logger.error("getGroupMatcheDataV2().text = " + text);
			matchGroupProductResult.setTempletGroupConfig(text);
		}else{
			matchGroupProductResult.setTempletGroupConfig(designPlanRecommendedProduct.getLocation());
			logger.error("getGroupMatcheDataV2().Location = " + designPlanRecommendedProduct.getLocation());
		}

		//推荐方案组合产品 （方案相同产品客户端无法匹配）
		List<DesignPlanRecommendedProduct> designPlanRecommendedProductList = 
				designPlanRecommendedProductServiceV2.getListByPlanIdAndGroupId(planRecommendedId, designPlanRecommendedProduct.getProductGroupId());
		Map<String,String> map = null;
		List<Map<String,String>> groupProductList = new ArrayList<>();
		if (Lists.isNotEmpty(designPlanRecommendedProductList)) {
			for (DesignPlanRecommendedProduct designPlanRecommendedProductListItem : designPlanRecommendedProductList) {
				map = new HashMap<>();
				map.put("productCode",designPlanRecommendedProductListItem.getProductCode());
				map.put("posIndexPath",designPlanRecommendedProductListItem.getPosIndexPath());
				groupProductList.add(map);
			}
		}
		matchGroupProductResult.setTempletGroupProductList(groupProductList);
	}

	@Override
	public ResponseEnvelope<Object> getTempletProductIndexConfig(String msgId,String templetCode ){
		 
		List<TempletProductIndexInfo> list = templetProductIndexInfoService.findIndexInfoByCode(templetCode);
		String productIndexInfo = "";
		if( Lists.isNotEmpty(list) ){
			productIndexInfo = list.get(0).getProductIndexInfo();
		}
		return new ResponseEnvelope(true,productIndexInfo,msgId);
	}

	@Override
	public ResponseEnvelope byConfigSaveProductIndex(String msgId, String templetCode, String context,
			LoginUser loginUser){
		DesignTemplet designTemplet = designTempletService.getDesignTempletByCode(templetCode);
		if( designTemplet == null || designTemplet.getId() < 1){
			return new ResponseEnvelope(false,"找不到样板房信息！templetCode="+templetCode,msgId);
		}
		Integer designTempletId = designTemplet.getId();
		//存储context，如果之前样板房有存储先逻辑删除
		TempletProductIndexInfo  templetProductIndexInfo = new TempletProductIndexInfo();
		templetProductIndexInfo.setDesignTempletId(designTempletId);
		templetProductIndexInfo.setIsDeleted(0);
		List<TempletProductIndexInfo> list = templetProductIndexInfoService.getList(templetProductIndexInfo);
		List<Integer> indexInfoIds = new ArrayList<>();
		for( TempletProductIndexInfo templetProductIndexInfo1 : list ){
			indexInfoIds.add(templetProductIndexInfo1.getId());
		}
		//批量更新
		if( indexInfoIds != null && indexInfoIds.size() > 0 ){
			TempletProductIndexInfo productIndexInfo = new TempletProductIndexInfo();
			productIndexInfo.setIsDeleted(1);
			productIndexInfo.setIndexInfoIds(indexInfoIds);
			templetProductIndexInfoService.updateIndexInfoByIds(productIndexInfo);
		}
		templetProductIndexInfo.setProductIndexInfo(context);
		templetProductIndexInfoService.sysSave(templetProductIndexInfo,loginUser);
		templetProductIndexInfoService.add(templetProductIndexInfo);

		//存储样板房产品序号
		Gson gson2 = new Gson();
		List<ProductIndexConfig> productIndexList = gson2.fromJson(context, new TypeToken<List<ProductIndexConfig>>() {}.getType());
		if (productIndexList == null || productIndexList.size() == 0) {
			logger.error("样板房code = "+templetCode+" 无更新的样板房产品属性。");
		}
		StringBuffer sb = new StringBuffer();
		for (ProductIndexConfig productIndexConfig : productIndexList) {
			List<ProductDataEx> productList = productIndexConfig.getProductDataEx();
			if (productList == null || productList.size() == 0) {
				logger.error("分类productSmallType = "+productIndexConfig.getProductSmallType()+" 无更新的产品属性。");
			}
			for (ProductDataEx product : productList ) {
				DesignTempletProduct designTempletProduct = new DesignTempletProduct();
				designTempletProduct.setPosName(product.getPosName());
				designTempletProduct.setDesignTempletId(designTempletId);
				designTempletProduct.setSameProductTypeIndex(product.getIndex());
				designTempletProduct.setIsStandard(product.getIsStandard());
//				designTempletProduct.setCenter(product.getCenter());
				designTempletProduct.setRegionMark(product.getRegionMark());
				designTempletProduct.setStyleId(product.getStyleId());
				designTempletProduct.setMeasureCode(product.getMeasureCode());
				designTempletProduct.setProductIndex(product.getProductIndex());
				//如果是背景墙结构序号为10的默认为主产品
				if (product.getProductIndex() != null && product.getProductIndex() == 10) {
					designTempletProduct.setIsMainStructureProduct(1);
					designTempletProduct.setIsGroupReplaceWay(1);
				}else{
					designTempletProduct.setIsMainStructureProduct(product.getIsMainStructureProduct());
					designTempletProduct.setIsGroupReplaceWay(product.getIsGroupReplaceWay());
				}
				designTempletProduct.setWallOrientation(product.getWallOrientation());
				designTempletProduct.setWallType(product.getWallType());
				int i = designTempletProductService.updateSameProductTypeIndex(designTempletProduct);
				if (i == 0) {
					logger.error("分类："+productIndexConfig.getProductSmallType()+";posName="+product.getPosName()+";designTempletId="+designTempletId+"更新失败");
					sb.append("productCode="+product.getItemCode()+",");
				}
			}
		}
		if (sb != null && sb.length() > 0) {
			return new ResponseEnvelope(true,sb.toString()+"保存失败！",msgId);
		}
		return new ResponseEnvelope(true,"保存成功！",msgId);
	}

	@Override
	public List<SearchStructureProductResult> matchingStructureDataV2(
			DesignTemplet designTemplet, 
			DesignPlanRecommended designPlanRecommended, 
			LoginUser loginUser,
			List<String> regionMarkList
			) {
		List<SearchStructureProductResult> list = new ArrayList<SearchStructureProductResult>();
		
		// 参数验证 ->start
		if(designTemplet == null){
			return list;
		}
		if(designPlanRecommended == null){
			return list;
		}
		if(loginUser == null){
			return list;
		}
		// 参数验证 ->end
			
		if(regionMarkList == null || regionMarkList.size() == 0){
			return list;
		}
		
		List<StructureProduct> structureList = structureProductService.getStructuresByRecommendedPlanId(designPlanRecommended.getId());

		Map<String,StructureProduct> structure_map = new HashMap<>();
		if (structureList != null && structureList.size() > 0) {
			for (StructureProduct structureProduct : structureList){
				structure_map.put(structureProduct.getStructureCode(),structureProduct);
			}
		}
		List<DesignTempletProduct> templetStructureProducts = designTempletProductService.getStructureProductByDesignId(designTemplet.getId());

		for (DesignTempletProduct templetProduct : templetStructureProducts) {
			SearchStructureProductResult searchStructureProductResult = null;
			int djTemp = 0;
			StructureProduct djStructureProduct = null;
			for (StructureProduct planStructureProduct : structureList) {
				//匹配方案结构区域、尺寸代码
				if ("DJ".equals(templetProduct.getStructureType()) && "DJ".equals(planStructureProduct.getStructureType())) {
					if (templetProduct.getRegionMark().equals(planStructureProduct.getPlanStructureRegionMark())) {
						djTemp++;
						if (templetProduct.getMeasureCode().equals(planStructureProduct.getMeasureCode())) {
							//结构详情列表json
							searchStructureProductResult = structureProductService.getSearchStructureProductResult(planStructureProduct,templetProduct.getStructureCode());
							break;
						} else {
							StructureProduct structure = null;
							if (planStructureProduct.getStructureNumber().intValue() == 1) { //如果是整块结构则查本身结构数据
								structure = structureProductService.get(templetProduct.getProductGroupId());
							}else{
								structure = matcheSystemStructure(templetProduct, planStructureProduct, loginUser);
							}
							if (structure != null) {
								//结构详情列表json
								searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure,templetProduct.getStructureCode());
								break;
							}
						}
					} else {
						djStructureProduct = planStructureProduct;//记录该地面结构需要用它的款式去匹配（structureList有按区域正序排序，取最后一条去匹配）
						continue;
					}
				} else {
					StructureProduct structure = matcheSystemStructure(templetProduct, planStructureProduct, loginUser);
					if (structure != null) {
						//结构详情列表json
						searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure,templetProduct.getStructureCode());
						break;
					}
				}
			}
			if (!"1".equals(templetProduct.getRegionMark()) && djTemp == 0) {
				StructureProduct structure = matcheSystemStructure(templetProduct, djStructureProduct, loginUser);
				if (structure != null) {
					//结构详情列表json
					searchStructureProductResult = structureProductService.getSearchStructureProductResult(structure,templetProduct.getStructureCode());
				}
			}
			if (searchStructureProductResult != null) {
				list.add(searchStructureProductResult);
			}
		}
		
		// 检测该设计方案结构有没有换成别的结构(是不是初始化结构,如果是初始化结构,则continue) ->start add by huangsongbo
		// 该逻辑适用于U3D前端
		if(list != null){
			for(int index = 0; index < list.size(); index ++){
				if(regionMarkList.indexOf(list.get(index).getPlanStructureRegionMark()) == -1){
					list.remove(index);
					index --;
				}
			}
		}
		// 检测该设计方案结构有没有换成别的结构(是不是初始化结构,如果是初始化结构,则continue) ->end add by huangsongbo
		
		return list;
	}

	@Override
	public List<Map<String, String>> findByItemCodeInfoV2(
			String posNames, DesignTemplet designTemplet,
			DesignPlanRecommended designPlanRecommended, 
			LoginUser loginUser
			) throws IntelligenceDecorationException {
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		
		// 参数验证 ->start
		if(StringUtils.isEmpty(posNames)){
			return dataList;
		}
		
		if(designTemplet == null){
			return dataList;
		}
		
		if(designPlanRecommended == null){
			return dataList;
		}
		// 参数验证 ->end
		
		String arrayPosName[] = posNames.split(",");
		Map<String,String> map = null;
		Map<DesignProductResult, DesignProductResult> againMatchMap = new HashMap<DesignProductResult, DesignProductResult>();
		List<String> posNameList = Arrays.asList(arrayPosName);//白膜的产品Code列表
		//白膜的产品列表
		List<DesignProductResult> templetProductList =  baseProductMapper.getTempletProductList(posNameList, designTemplet.getId());
		//设计方案产品列表
		List<DesignProductResult> planProductList = baseProductMapper.getPlanProductRecommendedList(designPlanRecommended.getId());

		if (Lists.isEmpty(planProductList)) {
			return dataList;
		}

		// 代码注释原因:删除背景墙逻辑需求变更
		// update by huangsongbo 2017.11.1
		/*// *得到一个List:推荐方案中,被删除的背景墙的墙体方位List ->start
		List<String> deleteWallOrientationList = designPlanRecommendedProductServiceV2.getDeleteWallOrientationList(designPlanRecommended.getId());
		// *得到一个List:推荐方案中,被删除的背景墙的墙体方位List ->end*/		
		
		HashMap<String, DesignProductResult> code_pro_map = new HashMap <String, DesignProductResult>();
		for (DesignProductResult pro : templetProductList) {
			code_pro_map.put(pro.getPosName(), pro);
		}
		int temp = 0;
		List<DesignProductResult> list = null;

		// 用来储存样板房背景墙数据,放在后面匹配(普通背景墙+结构背景墙)
		List<DesignProductResult> designProductResultListBeijing = new ArrayList<DesignProductResult>();
		
		for (String posName : arrayPosName) {
			StringBuffer matchErrorInfo = new StringBuffer();
			// test ->start
			/*if(StringUtils.equals("arpe01", posName)){
				//System.out.println();
			}*/
			// test ->end
			map = new HashMap<>();
			DesignProductResult	templetProduct = code_pro_map.get(posName);
			
			if (templetProduct == null) {
				continue;
			}
			
			// 背景墙单独匹配,先放到designProductResultListBeijing中,匹配完其他产品再匹配背景墙
			if(beijingValuekeyList.indexOf(templetProduct.getSmallTypeKey()) != -1) {
				designProductResultListBeijing.add(templetProduct);
				continue;
			}
			
			matchErrorInfo = getLogInfo(templetProduct,null,matchErrorInfo);//获取错误数据日志
			templetProduct.setMatchErrorInfo(matchErrorInfo==null?"":matchErrorInfo.toString());
			//包含有组合产品的不予匹配
			if (templetProduct.getProductGroupId() != null && templetProduct.getProductGroupId() > 0 && 0 == templetProduct.getGroupType().intValue()) {
				map = setMap(templetProduct,null);
				dataList.add(map);
				continue;
			}
			String orientation = "";
			String viceOrientation = "";//副方位
			String wallOrientae = templetProduct.getWallOrientation();
			if (StringUtils.isNotEmpty(wallOrientae) && !"0".equals(wallOrientae)) {
				orientation = wallOrientae.substring(0,1);
				if(wallOrientae.length() == 3){
					viceOrientation = templetProduct.getWallOrientation().substring(1,3);
				}else{
					matchErrorInfo.append("[designTempletId="+designTemplet.getId()+"posName="+posName+",WallOrientation="+wallOrientae+",录入错误]").append(";");
				}
			}
			list = new ArrayList<>();
			int tianhTemp = 0;
			int dimTemp = 0;
			int bgWallTemp = 0;
			DesignProductResult noRegionTianh = null;
			DesignProductResult noRegionDim = null;
			DesignProductResult sameTypeDim = null;
			DesignProductResult sameTypeOrientation = null;
			DesignProductResult bgWallType = null;
			DesignProductResult bgWallTypeOrientation = null;
			
			// 代码注释原因:删除背景墙逻辑需求变更
			// update by huangsongbo 2017.11.1
			/*// 检测推荐方案中哪个背景墙被删除了,生成的一件装修方案也删除对应的背景墙 ->start
			if(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey())) {
				String wallOrientation = templetProduct.getWallOrientation();
				if(StringUtils.isEmpty(wallOrientation) || "0".equals(wallOrientation)) {
					
				}else {
					if(this.judgeWhetherToDelete(wallOrientation, deleteWallOrientationList)) {
						map = setMap(templetProduct, null);
						dataList.add(map);
						matchErrorInfo.append("[posName="+posName+";productCode="+templetProduct.getProductCode()+"方案删除了该墙体分类背景墙，无法匹配];");
						continue;
					}
				}
			}
			// 检测推荐方案中哪个背景墙被删除了,生成的一件装修方案也删除对应的背景墙 ->end*/
			
			for (DesignProductResult planProduct : planProductList) {
				
				// 背景墙放在最后匹配 ->start
				if(beijingValuekeyList.indexOf(planProduct.getSmallTypeKey()) != -1) {
					continue;
				}
				// 背景墙放在最后匹配 ->end
				
				if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(templetProduct.getBigTypeKey())
						&& ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(planProduct.getBigTypeKey())) {
					matchErrorInfo = getLogInfo(null,planProduct,matchErrorInfo);//获取错误数据日志
					templetProduct.setMatchErrorInfo(matchErrorInfo==null?"":matchErrorInfo.toString());
					//天花通过区域标识匹配/尺寸代码、，如果匹配不上通过方案天花款式、白膜尺寸代码、区域标识匹配系统数据
					if (templetProduct.getRegionMark().equals(planProduct.getRegionMark())) {
						tianhTemp ++ ;
						if (templetProduct.getMeasureCode().equals(planProduct.getMeasureCode())) {
							map = setMap(templetProduct,planProduct.getProductCode());
							break;
						}else{
							againMatchMap.put(templetProduct,planProduct);
							break;
						}
					}else{
						noRegionTianh = planProduct;//记录该天花需要用它的款式去匹配（planProductList我有按区域正序排序，去最后一条去匹配）
						continue;
					}

				}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_DIM.equals(templetProduct.getBigTypeKey())
						&& ProductTypeConstant.PRODUCT_BIG_TYPE_DIM.equals(planProduct.getBigTypeKey())){
					if (StringUtils.isNotEmpty(templetProduct.getRegionMark()) && templetProduct.getRegionMark().equals(planProduct.getRegionMark())) {
						dimTemp ++ ;
						map = setMap(templetProduct,planProduct.getProductCode());
						break;
					}else{
						//同分类地面取同小分类地面产品（阳台、门槛石没录区域）
						if (templetProduct.getSmallTypeValue().equals(planProduct.getBmSmallTypeValue())) {
							sameTypeDim = planProduct;
						}else{
							//跳过门槛石匹配
							if (!"17".equals(planProduct.getBmSmallTypeValue())){
								noRegionDim = planProduct;
							}
						}
					}
				}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey())
						&& ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(planProduct.getBigTypeKey())) {
					matchErrorInfo = getLogInfo(null,planProduct,matchErrorInfo);//获取错误数据日志
					templetProduct.setMatchErrorInfo(matchErrorInfo==null?"":matchErrorInfo.toString());
					//实体墙匹配
					if (templetProduct.getSmallTypeValue() != null && templetProduct.getSmallTypeValue() == 8) {
						if (planProduct.getBmSmallTypeValue() == 8) {
							bgWallTemp ++ ;
							map = setMap(templetProduct, planProduct.getProductCode());
							break;
						}else{
							continue;
						}
					}
					//门框匹配
					if (templetProduct.getSmallTypeValue() != null && templetProduct.getSmallTypeValue() == 19) {
						if (planProduct.getBmSmallTypeValue() == 19) {
							if (isMatchingPlanProduct(templetProduct, planProduct, matchErrorInfo) && planProduct.getMatchSign()==null) {
								bgWallTemp ++ ;
								map = setMap(templetProduct, planProduct.getProductCode());
								planProduct.setMatchSign(1);//已匹配标记
								break;
							} else {
								list.add(planProduct); //匹配不到的数据存起来，匹配系统产品用
//								againMatchMap.put(templetProduct,planProduct);
								continue;
							}
						}else{
							continue;
						}
					}
					//窗框匹配
					if (templetProduct.getSmallTypeValue() != null && templetProduct.getSmallTypeValue() == 18) {
						if (planProduct.getBmSmallTypeValue() == 18) {
							bgWallTemp ++ ;
							if (isMatchingPlanProduct(templetProduct, planProduct, matchErrorInfo)) {
								map = setMap(templetProduct, planProduct.getProductCode());
								break;
							} else {
								againMatchMap.put(templetProduct,planProduct);
								break;
							}
						}else{
							continue;
						}
					}
					
					// 为什么注释:
					// 背景墙/结构背景墙匹配逻辑要大改,准备单独拎出背景墙的匹配逻辑,重构
					// update by huangsongbo 2017.11.1
					/*//背景墙匹配 通过墙体类型和墙体方位匹配，如果匹配不上在通过墙体类型，在墙体方位匹配，没有则删除
					//结构背景墙
					if (templetProduct.getProductGroupId() != null && templetProduct.getProductGroupId() > 0) {
						int tmp = 0;
						if (StringUtils.isNotEmpty(planProduct.getWallType()) && StringUtils.isNotEmpty(planProduct.getWallOrientation())) {
							//判断样板房结构背景墙分类和方位是否在方案中存在。
							for (DesignProductResult desPro : planProductList) {
								if (StringUtils.isNotEmpty(templetProduct.getWallType())
										&& templetProduct.getWallType().equals(desPro.getWallType())) {
									tmp++;
									break;
								}
								if (StringUtils.isNotEmpty(templetProduct.getWallOrientation())
										&& templetProduct.getWallOrientation().equals(desPro.getWallOrientation())) {
									tmp++;
									break;
								}
							}
							//不存在进行处理一层
							if (tmp == 0) {
								//由于副墙墙体分类和主墙墙体分类不一致，导致副墙匹配的背景墙和主墙不一致。程序纠正副墙墙体分类
								if (StringUtils.isNotEmpty(viceOrientation) && !viceOrientation.equals("00")) {
									for (DesignProductResult dpr : templetProductList) {
										if ((orientation + "00").equals(dpr.getWallOrientation())) {
											templetProduct.setWallType(dpr.getWallType());
											break;
										} else {
											continue;
										}
									}
								}
							}
						}
						if (StringUtils.isNotEmpty(templetProduct.getWallType())
							&& templetProduct.getWallType().equals(planProduct.getWallType())) {
							//有结构ID则在按序号匹配
							if (templetProduct.getProductIndex().equals(planProduct.getProductIndex())) {
								bgWallTemp++;
								if (isMatchingPlanProduct(templetProduct, planProduct, matchErrorInfo)) {
									map = setMap(templetProduct, planProduct.getProductCode());
									break;
								} else {
									againMatchMap.put(templetProduct, planProduct);
									break;
								}
							}else{
								bgWallType = planProduct;
								continue;
							}
						} else {
							if (StringUtils.isNotEmpty(templetProduct.getWallOrientation())
									&& templetProduct.getWallOrientation().equals(planProduct.getWallOrientation())) {
								bgWallTypeOrientation = planProduct;
								continue;
							}
						}
					} else {
						//由于副墙墙体分类和主墙墙体分类不一致，导致副墙匹配的背景墙和主墙不一致。程序纠正副墙墙体分类
						if (StringUtils.isNotEmpty(viceOrientation) && !viceOrientation.equals("00")) {
							for (DesignProductResult dpr : templetProductList) {
								if ((orientation+"00").equals(dpr.getWallOrientation())) {
									templetProduct.setWallType(dpr.getWallType());
									break;
								}else {
									continue;
								}
							}
						}
						//不是结构组则按墙体方位和墙体类型匹配
						if (StringUtils.isNotEmpty(templetProduct.getWallType())
								&& StringUtils.isNotEmpty(templetProduct.getWallOrientation())
								&& templetProduct.getWallType().equals(planProduct.getWallType())
								&& templetProduct.getWallOrientation().equals(planProduct.getWallOrientation())) {
							bgWallTemp ++ ;
							if (isMatchingPlanProduct(templetProduct, planProduct, matchErrorInfo)) {
								map = setMap(templetProduct, planProduct.getProductCode());
								break;
							}else{
								againMatchMap.put(templetProduct,planProduct);
								break;
							}
						}else if (StringUtils.isNotEmpty(templetProduct.getWallType())
								&& StringUtils.isNotEmpty(templetProduct.getWallOrientation())
								&& templetProduct.getWallType().equals(planProduct.getWallType())
								&& StringUtils.isNotEmpty(orientation)
								&& ("."+planProduct.getWallOrientation()).indexOf("."+orientation) != -1) {
							sameTypeOrientation = planProduct;
							continue;
						} else {
							if (StringUtils.isNotEmpty(templetProduct.getWallType())
									&& templetProduct.getWallType().equals(planProduct.getWallType())) {
								bgWallType = planProduct;
								continue;
							}
							if (StringUtils.isNotEmpty(templetProduct.getWallOrientation())) {
								if (templetProduct.getWallOrientation().equals(planProduct.getWallOrientation())) {
									bgWallTypeOrientation = planProduct;
									continue;
								} else if (StringUtils.isNotEmpty(orientation) && ("."+planProduct.getWallOrientation()).indexOf("."+orientation) != -1) {
									bgWallTypeOrientation = planProduct;
									continue;
								}else{
									continue;
								}
							}
						}
					}*/
				}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_MENG.equals(templetProduct.getBigTypeKey())
						&& ProductTypeConstant.PRODUCT_BIG_TYPE_MENG.equals(planProduct.getBigTypeKey())) {
					//门按白膜小分类匹配，相同房间门用一个去替换（强子定的）
					if (templetProduct.getBigTypeValue().equals(planProduct.getBigTypeValue())
							&& templetProduct.getSmallTypeValue() == planProduct.getBmSmallTypeValue()) {
						if (isMatchingPlanProduct(templetProduct, planProduct, matchErrorInfo)) {
							map = setMap(templetProduct, planProduct.getProductCode());
							break;
						}else{
							againMatchMap.put(templetProduct,planProduct);
							break;
						}
					}else {
						continue;
					}
				}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_LI.equals(templetProduct.getBigTypeKey())
						&& ProductTypeConstant.PRODUCT_BIG_TYPE_LI.equals(planProduct.getBigTypeKey())) {
					matchErrorInfo = getLogInfo(null,planProduct,matchErrorInfo);//获取错误数据日志
					templetProduct.setMatchErrorInfo(matchErrorInfo==null?"":matchErrorInfo.toString());
					//灯具按白膜小分类、墙体分类匹配,
					if (templetProduct.getSmallTypeValue() == planProduct.getBmSmallTypeValue()) {
						if (templetProduct.getWallType().trim().equals(planProduct.getWallType().trim())) {
							map = setMap(templetProduct, planProduct.getProductCode());
							break;
						}else{
							continue;
						}
					}
				}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_PE.equals(templetProduct.getBigTypeKey())
						&& ProductTypeConstant.PRODUCT_BIG_TYPE_PE.equals(planProduct.getBigTypeKey())) {
					// 并且要满足互搜逻辑
					@SuppressWarnings("unchecked")
					Map<String, List<String>> showMoreSmallTypeInfoMap = (Map<String, List<String>>) ProductCategoryRelServiceImpl.showMoreSmallTypeMap.get("showMoreSmallTypeInfoMap");
					/*//System.out.println(templetProduct.getSmallTypeKey().replace("basic_", ""));*/
					if(showMoreSmallTypeInfoMap.containsKey(templetProduct.getSmallTypeKey().replace("basic_", ""))){
						List<String> smallTypeList = showMoreSmallTypeInfoMap.get(templetProduct.getSmallTypeKey().replace("basic_", ""));
						if(smallTypeList.indexOf(planProduct.getSmallTypeKey().replace("basic_", "")) != -1){
							//挂画按墙体方位匹配或墙体分类
							if (templetProduct.getWallOrientation().equals(planProduct.getWallOrientation())) {
								map = setMap(templetProduct, planProduct.getProductCode());
								break;
							}else if (templetProduct.getWallType().equals(planProduct.getWallType())) {
								map = setMap(templetProduct, planProduct.getProductCode());
								break;
							}else{
								continue;
							}
						}
					}
				}else{
					if (templetProduct.getBigTypeValue().equals(planProduct.getBigTypeValue()) && templetProduct.getSmallTypeValue() == planProduct.getBmSmallTypeValue()) {
						if (isMatchingPlanProduct(templetProduct, planProduct, matchErrorInfo)) {
							map = setMap(templetProduct, planProduct.getProductCode());
							break;
						}else{
							againMatchMap.put(templetProduct,planProduct);
							break;
						}
					}else {
						continue;
					}
				}
			}
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(templetProduct.getBigTypeKey()) && tianhTemp == 0 && noRegionTianh != null) {
				againMatchMap.put(templetProduct,noRegionTianh);
			}
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(templetProduct.getBigTypeKey()) && tianhTemp == 0 && noRegionTianh == null) {
				map = setMap(templetProduct);
			}
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_DIM.equals(templetProduct.getBigTypeKey()) && dimTemp == 0 && sameTypeDim != null) {
				map = setMap(templetProduct,sameTypeDim.getProductCode());
			}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_DIM.equals(templetProduct.getBigTypeKey()) && dimTemp == 0 && noRegionDim != null) {
				map = setMap(templetProduct,noRegionDim.getProductCode());
			}
			//背景墙匹配同类型或同方位背景墙
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey()) && bgWallTemp == 0 && sameTypeOrientation != null) {
				if (isMatchingPlanProduct(templetProduct, sameTypeOrientation, matchErrorInfo)) {
					map = setMap(templetProduct, sameTypeOrientation.getProductCode());
				}else{
					againMatchMap.put(templetProduct,sameTypeOrientation);
				}
			}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey()) && bgWallTemp == 0 && bgWallType != null) {
				if (isMatchingPlanProduct(templetProduct, bgWallType, matchErrorInfo)) {
					map = setMap(templetProduct, bgWallType.getProductCode());
				}else{
					againMatchMap.put(templetProduct,bgWallType);
				}
			}else if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey()) && bgWallTemp == 0 && bgWallTypeOrientation != null) {
				if (isMatchingPlanProduct(templetProduct, bgWallTypeOrientation, matchErrorInfo)) {
					map = setMap(templetProduct, bgWallTypeOrientation.getProductCode());
				}else{
					againMatchMap.put(templetProduct,bgWallTypeOrientation);
				}
			}else {}
			//匹配不到删除背景墙
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey()) && bgWallTemp == 0 && bgWallType == null && bgWallTypeOrientation == null && sameTypeOrientation == null) {
				if (list != null && list.size() > 0) {
					for (DesignProductResult matchResult : list) {
						againMatchMap.put(templetProduct,matchResult);
						break;
					}
				}else{
					map = setMap(templetProduct);
				}
			}
			if (againMatchMap != null && againMatchMap.size() == temp) {
				if (map == null || map.size() == 0) {
					map = setMap(templetProduct);
				}
				dataList.add(map);
			}else{
				temp = againMatchMap.size();
			}

		}
		//方案匹配不到，则匹配系统产品
		if (againMatchMap != null && againMatchMap.size() > 0) {
			for (Map.Entry<DesignProductResult, DesignProductResult> entry : againMatchMap.entrySet()) {
				DesignProductResult templetProductResult = entry.getKey();
				logger.debug("posname:" + templetProductResult.getPosName());
				DesignProductResult planProductResult = entry.getValue();
				BaseProduct matchProductData = getMatchSystemProduct(templetProductResult,planProductResult,loginUser);
				if (matchProductData != null) {
					map = setMap(templetProductResult,matchProductData.getProductCode());
				}else{
					map = setMap(templetProductResult);
				}
				dataList.add(map);
			}
		}
		
		// 背景墙的删除/匹配 ->start
		
		// 取出推荐方案中所有的背景墙,并且剔除非主墙 ->start
		List<DesignProductResult> designProductRecommendedResultList = this.getDesignProductRecommendedResultList(planProductList);
		// 取出推荐方案中所有的背景墙,并且剔除非主墙 ->end
		
		// 整理出一份墙体方位:推荐方案产品信息的Map:墙体方位:List<墙体类型>
		Map<String, List<String>> recommendedProductMapByWallOrientation = this.getRecommendedProductMapByWallOrientation(designProductRecommendedResultList);
		
		// 取出样板房中附墙+非主墙/主墙的list
		Map<getDesignProductResultListBeijingMapEnum, List<DesignProductResult>> designProductResultListBeijingMap = this.getDesignProductResultListBeijingMap(designProductResultListBeijing);
		List<DesignProductResult> designTempletMainBeijingInfoList = designProductResultListBeijingMap.get(getDesignProductResultListBeijingMapEnum.mainBeijing);
		List<DesignProductResult> designTempletDeputyBeijingInfoList = designProductResultListBeijingMap.get(getDesignProductResultListBeijingMapEnum.deputyBeijing);
		// 整理推荐方案中的背景墙:整理成wallType:背景墙数据的格式 ->start
		Map<String, List<DesignProductResult>> designProductRecommendedResultListByWallType = this.getDesignProductResultListByWallType(designProductRecommendedResultList);
		// 整理推荐方案中的背景墙:整理成wallType:背景墙数据的格式 ->end
		
		// 整理样板房中的背景墙:整理成wallType:背景墙数据的格式 ->start
		Map<String, List<DesignProductResult>> designProductTempletResultListByWallType = this.getDesignProductResultListBeijingByWallType(designTempletMainBeijingInfoList);
		// 整理样板房中的背景墙:整理成wallType:背景墙数据的格式 ->end
		
		// 删除背景墙逻辑 ->start
		// designProductResultList 推荐方案中的背景墙 designProductResultListBeijing 样板房背景墙
		
		// 获取被删除的背景墙的数据(主墙) ->start
		List<Map<deletedBeijingProductInfoMapKeyEnum, String>> deletedBeijingProductInfoList = this.getDeletedBeijingProductInfo(designPlanRecommended.getId());
		// 获取被删除的背景墙的数据(主墙) ->end
		 
		// 获取样板房中不同类别背景墙要删除的个数 ->start
		// wallType:数量,eg:A:2,标识墙体分类为A的墙还要删除2面
		// {E=1, H=2}
		Map<String, Integer> deleteNumInfoMap = this.getDeleteNumInfoMap(designProductTempletResultListByWallType, designProductRecommendedResultListByWallType, deletedBeijingProductInfoList);
		// 获取样板房中不同类别背景墙要删除的个数 ->end
		
		// 删除背景墙 ->start
		this.deleteTempletBeijingProduct(dataList, deleteNumInfoMap, designTempletMainBeijingInfoList, recommendedProductMapByWallOrientation);
		// 删除背景墙 ->end
		
		// 删除背景墙逻辑 ->end
		
		// 背景墙匹配(主墙) ->start
		// 得到一个墙体方位:产品信息的map,为了附墙根据主墙去搜索
		Map<String, DesignProductResult> matchResponseInfoMap = this.matchMainBeijingProduct(dataList, designTempletMainBeijingInfoList, designProductRecommendedResultList, loginUser);
		// 背景墙匹配(主墙) ->end
		
		// 背景墙附墙/结构背景墙非主墙匹配 ->start
		this.matchDeputyBeijingProduct(dataList, designTempletDeputyBeijingInfoList, matchResponseInfoMap, loginUser);
		// 背景墙附墙/结构背景墙非主墙匹配 ->end
		
		// 背景墙的删除/匹配 ->end
		
		return dataList;
	}

	/**
	 * 背景墙附墙/结构背景墙非主墙匹配(根据主墙搜索)
	 * 
	 * @author huangsongbo
	 * @param dataList
	 * @param designTempletDeputyBeijingInfoList
	 * @param matchResponseInfoMap
	 * @throws IntelligenceDecorationException 
	 */
	private void matchDeputyBeijingProduct(List<Map<String, String>> dataList,
			List<DesignProductResult> designTempletDeputyBeijingInfoList,
			Map<String, DesignProductResult> matchResponseInfoMap,
			LoginUser loginUser
			) throws IntelligenceDecorationException {
		// 参数验证/处理 ->start
		if(dataList == null) {
			return;
		}
		if(Lists.isEmpty(designTempletDeputyBeijingInfoList)) {
			return;
		}
		if(matchResponseInfoMap == null) {
			return;
		}
		// 参数验证/处理 ->end
		
		for(DesignProductResult designProductResult : designTempletDeputyBeijingInfoList) {
			logger.debug("productCode:" + designProductResult.getProductCode() + ";posName:" + designProductResult.getPosName());
			Map<String, String> map = new HashMap<String, String>();
			// 识别对应的墙体方位
			String wallOrientation = designProductResult.getWallOrientation().trim();
			if(StringUtils.isBlank(wallOrientation)) {
				throw new IntelligenceDecorationException("样板房中某个背景墙的墙体方位为空,productCode:" + designProductResult.getProductCode());
			}
			if(matchResponseInfoMap.containsKey(wallOrientation.substring(0, 1))) {
				DesignProductResult designProductResultMatchedMainProduct = matchResponseInfoMap.get(wallOrientation.substring(0, 1));
				BaseProduct baseProduct = getMatchSystemProduct(designProductResult, designProductResultMatchedMainProduct, loginUser);
				if(baseProduct != null) {
					map = setMap(designProductResult, baseProduct.getProductCode());
				}else {
					map = setMap(designProductResult);
				}
			}else {
				map = setMap(designProductResult);
			}
			dataList.add(map);
		}
	}

	/**
	 * 匹配样板房背景墙主墙,返回一个匹配结构供附墙搜索用
	 * 
	 * @author huangsongbo
	 * @param dataList
	 * @param designTempletMainBeijingInfoList
	 * @param designProductRecommendedResultList
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private Map<String, DesignProductResult> matchMainBeijingProduct(List<Map<String, String>> dataList,
			List<DesignProductResult> designTempletMainBeijingInfoList,
			List<DesignProductResult> designProductRecommendedResultList,
			LoginUser loginUser) throws IntelligenceDecorationException {
		Map<String, DesignProductResult> returnMap = new HashMap<String, DesignProductResult>();
		// 参数验证/参数处理 ->start
		if(dataList == null) {
			return returnMap;
		}
		if(Lists.isEmpty(designTempletMainBeijingInfoList)) {
			return returnMap;
		}
		if(Lists.isEmpty(designProductRecommendedResultList)) {
			return returnMap;
		}
		if(loginUser == null) {
			return returnMap;
		}
		// 参数验证/参数处理 ->end
		
		for(DesignProductResult templetProductInfo : designTempletMainBeijingInfoList) {
			logger.debug("templetProductInfo.posName:" + templetProductInfo.getPosName() + ";wallType:" + templetProductInfo.getWallType() + ";wallOrientation:" + templetProductInfo.getWallOrientation());
			Map<String, String> map = null;
			DesignProductResult designProductResult = null;
			DesignProductResult designProductResultMatched = this.matchBeijingProduct(templetProductInfo, designProductRecommendedResultList);
			if(designProductResultMatched == null) {
				
			}else {
				// 验证长宽高
				if(this.isMatchingPlanProduct(templetProductInfo, designProductResultMatched, new StringBuffer(""))) {
					// 背景墙长高匹配成功
					map = this.setMap(templetProductInfo, designProductResultMatched.getProductCode());
					designProductResult = designProductResultMatched;
				}else {
					// 自行搜索
					BaseProduct baseProduct = getMatchSystemProduct(templetProductInfo ,designProductResultMatched, loginUser);
					if(baseProduct != null) {
						map = setMap(templetProductInfo, baseProduct.getProductCode());
						// baseProduct转化为DesignProductResult
						designProductResult = this.getDesignProductResultByBaseProduct(baseProduct);
						/*String splitTexturesInfo = designPlanProductService.matchSplitTexturesInfo(planProductInfoMatched.getSplitTexturesChooseInfo(), productIdRecommended, splitTexturesInfoRecommended);
						designProductResult.setSplitTexturesChooseInfo(splitTexturesInfo);*/
					}
				}
			}

			if(map != null) {
				
			}else {
				map = setMap(templetProductInfo);
			}
			dataList.add(map);
			
			// 将匹配结果加入Map<String, DesignProductResult>中 ->start
			if(designProductResult != null) {
				Integer wallOrientationStart = null;
				if(StringUtils.isNotEmpty(templetProductInfo.getWallOrientation())) {
					Integer wallOrientationInteger = null;
					try {
						wallOrientationInteger = Integer.valueOf(templetProductInfo.getWallOrientation().trim());
					}catch (Exception e) {
						
					}
					if(wallOrientationInteger != null) {
						wallOrientationStart = wallOrientationInteger / 100;
					}
				}
				returnMap.put(wallOrientationStart + "", designProductResultMatched);
			}
			// 将匹配结果加入Map<String, DesignProductResult>中 ->end
		}
		return returnMap;
	}

	/**
	 * BaseProduct转化为DesignProductResult
	 * 
	 * @author huangsongbo
	 * @param baseProduct
	 * @return
	 */
	private DesignProductResult getDesignProductResultByBaseProduct(BaseProduct baseProduct) {
		// 参数验证 ->start
		if(baseProduct == null) {
			return null;
		}
		// 参数验证 ->end
		DesignProductResult designProductResult = new DesignProductResult();
		designProductResult.setBigTypeValue(baseProduct.getProductTypeValue());
		designProductResult.setProductCode(baseProduct.getProductCode());
		designProductResult.setProductHeight(baseProduct.getProductHeight());
		designProductResult.setProductId(baseProduct.getId());
		designProductResult.setProductLength(baseProduct.getProductLength());
		designProductResult.setProductModelNumber(baseProduct.getProductModelNumber());
		designProductResult.setProductWidth(baseProduct.getProductWidth());
		designProductResult.setSmallTypeValue(baseProduct.getProductSmallTypeValue());
		designProductResult.setStyleId(baseProduct.getStyleId());
		return designProductResult;
	}

	/**
	 * 墙体方位+墙体类型匹配背景墙
	 * 
	 * @author huangsongbo
	 * @param templetProductInfo
	 * @param designProductRecommendedResultList
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private DesignProductResult matchBeijingProduct(DesignProductResult templetProductInfo,
			List<DesignProductResult> designProductRecommendedResultList) throws IntelligenceDecorationException {
		// 参数验证/处理 ->start
		if(templetProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(designProductRecommendedResultList)) {
			return null;
		}
		// 参数验证/处理 ->end
		
		// 得到匹配分数的一个list
		List<Integer> scoreList = this.getScoreListBeijing(templetProductInfo, designProductRecommendedResultList);
		// 取分数最高的index
		int index = Utils.getMaxNumIndexFromList(scoreList);
		Integer scoreMax = scoreList.get(index);
		if(-1 == scoreMax.intValue()) {
			// 没有一个背景墙匹配上
			return null;
		}
		
		return designProductRecommendedResultList.get(index);
	}

	/**
	 * 匹配背景墙,得到一个分数的结果集(墙体分类对上,加2分,墙体方位对上,加1分)
	 * 
	 * @author huangsongbo
	 * @param templetProductInfo
	 * @param designProductRecommendedResultList
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private List<Integer> getScoreListBeijing(DesignProductResult templetProductInfo,
			List<DesignProductResult> designProductRecommendedResultList) throws IntelligenceDecorationException {
		// 参数验证/处理 ->start
		if(templetProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(designProductRecommendedResultList)) {
			return null;
		}
		// 参数验证/处理 ->end
		
		List<Integer> scoreList = new ArrayList<Integer>();
		String wallType = templetProductInfo.getWallType().trim();
		if(StringUtils.isBlank(wallType)) {
			throw new IntelligenceDecorationException("样板房中某个背景墙墙体分类为空;productCode:" + templetProductInfo.getProductCode());
		}
		String wallOrientation = templetProductInfo.getWallOrientation().trim();
		if(StringUtils.isBlank(wallOrientation)) {
			throw new IntelligenceDecorationException("样板房中某个背景墙墙体方位为空;productCode:" + templetProductInfo.getProductCode());
		}
		for(DesignProductResult recommendedProductResult : designProductRecommendedResultList) {
			Integer score = -1;
			// 墙体方位匹配上,加2分
			if(StringUtils.equals(wallType, recommendedProductResult.getWallType().trim())) {
				score += 2;
			}
			// 墙体方位匹配上,加1分
			if(StringUtils.equals(wallOrientation, recommendedProductResult.getWallOrientation().trim())) {
				score += 1;
			}
			scoreList.add(score);
		}
		
		return scoreList;
	}

	/**
	 * 整理出一份墙体方位:推荐方案产品信息的Map:墙体方位:List<墙体类型>
	 * 
	 * @author huangsongbo
	 * @param designProductRecommendedResultList
	 * @return
	 */
	private Map<String, List<String>> getRecommendedProductMapByWallOrientation(
			List<DesignProductResult> designProductRecommendedResultList) {
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		// 参数验证/处理 ->start
		if(Lists.isEmpty(designProductRecommendedResultList)) {
			return returnMap;
		}
		// 参数验证/处理 ->end
		
		for(DesignProductResult designProductResult : designProductRecommendedResultList) {
			String wallOrientation = designProductResult.getWallOrientation();
			if(StringUtils.isBlank(wallOrientation)) {
				continue;
			}
			if(returnMap.containsKey(wallOrientation)) {
				List<String> wallTypeList = returnMap.get(wallOrientation);
				wallTypeList.add(designProductResult.getWallType());
			}else {
				List<String> wallTypeList = new ArrayList<String>();
				wallTypeList.add(designProductResult.getWallType());
				returnMap.put(wallOrientation, wallTypeList);
			}
		}
		return returnMap;
	}

	/**
	 * 根据deleteNumInfoMap(根据推荐方案数据和推荐方案对应的样板房数据,得出的?类型墙面删除?面的一个数据信息)删除样房中的背景墙
	 * 
	 * @author huangsongbo
	 * @param dataList 匹配结果集
	 * @param deleteNumInfoMap 根据推荐方案数据和推荐方案对应的样板房数据,得出的?类型墙面删除?面的一个数据信息
	 * @param designTempletMainBeijingInfoList 样板房中主墙信息
	 * @param recommendedProductMapByWallOrientation 推荐方案中主墙信息(墙体方位:List<墙体类型>)
	 */
	private void deleteTempletBeijingProduct(List<Map<String, String>> dataList, Map<String, Integer> deleteNumInfoMap,
			List<DesignProductResult> designTempletMainBeijingInfoList, Map<String, List<String>> recommendedProductMapByWallOrientation) {
		// 参数验证/处理 ->start
		if(deleteNumInfoMap == null) {
			return;
		}
		if(Lists.isEmpty(designTempletMainBeijingInfoList)) {
			return;
		}
		if(recommendedProductMapByWallOrientation == null) {
			return;
		}
		// 参数验证/处理 ->end
		
		for (int index = 0; index < designTempletMainBeijingInfoList.size(); index++) {
			DesignProductResult designTempletProductResult = designTempletMainBeijingInfoList.get(index);
			
			// 根据墙体分类判断该背景墙要不要被删除
			if(designTempletProductResult == null) {
				continue;
			}
			String wallType = designTempletProductResult.getWallType();
			if(StringUtils.isBlank(wallType)) {
				continue;
			}
			if(deleteNumInfoMap.containsKey(wallType)) {
				Integer num = deleteNumInfoMap.get(wallType);
				if(num != null && num.intValue() > 0) {
					// 校验要不要删除这面背景墙(检验相同墙体方位的推荐方案中的背景墙,墙体类型是否一样,如果一样,则不删除) ->start
					String wallOrientation = designTempletProductResult.getWallOrientation();
					if(StringUtils.isBlank(wallOrientation)) {
						continue;
					}
					if(recommendedProductMapByWallOrientation.containsKey(wallOrientation)) {
						List<String> wallTypelist = recommendedProductMapByWallOrientation.get(wallOrientation);
						if(Lists.isNotEmpty(wallTypelist)) {
							if(wallTypelist.indexOf(wallType.trim()) == -1) {
								designTempletMainBeijingInfoList.remove(index);
								index --;
								num --;
								deleteNumInfoMap.put(wallType, num);
								// 加入结果集
								Map<String, String> map = new HashMap<String, String>();
								map = setMap(designTempletProductResult);
								dataList.add(map);
							}
						}
					}else {
						designTempletMainBeijingInfoList.remove(index);
						index --;
						num --;
						deleteNumInfoMap.put(wallType, num);
						// 加入结果集
						Map<String, String> map = new HashMap<String, String>();
						map = setMap(designTempletProductResult);
						dataList.add(map);
					}
					// 校验要不要删除这面背景墙(检验相同墙体方位的推荐方案中的背景墙,墙体类型是否一样,如果一样,则不删除) ->end
				}
			}
		}
	}

	/**
	 * 得到一个map数据,标识?类型的背景墙,要删除?面
	 * Map<String, Integer> : 墙体分类:样板房中要删除的面数
	 * 
	 * @author huangsongbo
	 * @param designProductTempletResultListByWallType 样板房中现有主墙信息
	 * @param designProductRecommendedResultListByWallType 推荐方案中现有主墙信息
	 * @param deletedBeijingProductInfoList 推荐方案中已经被删除的背景墙信息
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private Map<String, Integer> getDeleteNumInfoMap(
			Map<String, List<DesignProductResult>> designProductTempletResultListByWallType,
			Map<String, List<DesignProductResult>> designProductRecommendedResultListByWallType,
			List<Map<deletedBeijingProductInfoMapKeyEnum, String>> deletedBeijingProductInfoList
			) throws IntelligenceDecorationException {
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		
		// 参数验证/处理 ->start
		if(designProductTempletResultListByWallType == null) {
			return returnMap;
		}
		if(designProductRecommendedResultListByWallType == null) {
			return returnMap;
		}
		if(deletedBeijingProductInfoList == null) {
			return returnMap;
		}
		// 参数验证/处理 ->end
		
		for(Map<deletedBeijingProductInfoMapKeyEnum, String> map : deletedBeijingProductInfoList) {
			String wallType = map.get(deletedBeijingProductInfoMapKeyEnum.wallType);
			if(StringUtils.isBlank(wallType)) {
				throw new IntelligenceDecorationException("推荐方案对应的样板房某个背景墙的墙体分类为空,一键装修失败");
			}
			
			// 检测推荐方案中该墙体类型的背景墙还剩几个
			int num = 0;
			if(designProductRecommendedResultListByWallType.containsKey(wallType)) {
				List<DesignProductResult> designProductResultList = designProductRecommendedResultListByWallType.get(wallType);
				if(Lists.isNotEmpty(designProductResultList)) {
					num = designProductResultList.size();
				}
			}
			
			// 计算出样板房要删除几个该墙体类型的背景墙
			int num2 = 0;
			if(designProductTempletResultListByWallType.containsKey(wallType)) {
				List<DesignProductResult> designProductResultList = designProductTempletResultListByWallType.get(wallType);
				if(Lists.isNotEmpty(designProductResultList)) {
					num2 = designProductResultList.size();
				}
			}
			
			returnMap.put(wallType, num2 - num);
		}
		return returnMap;
	}

	private Map<String, List<DesignProductResult>> getDesignProductResultListBeijingByWallType(
			List<DesignProductResult> designProductResultListBeijing) throws IntelligenceDecorationException {
		
		Map<String, List<DesignProductResult>> returnMap = new HashMap<String, List<DesignProductResult>>();
		// 参数验证/处理 ->start
		if(Lists.isEmpty(designProductResultListBeijing)) {
			return returnMap;
		}
		// 参数验证/处理 ->end
		
		// 墙体分类归类 ->start
		for(DesignProductResult designProductResult : designProductResultListBeijing) {
			String wallType = designProductResult.getWallType();
			if(StringUtils.isBlank(wallType)) {
				throw new IntelligenceDecorationException("样板房中有背景墙没有录墙体类型,productCode:" + designProductResult.getProductCode());
			}
			if(returnMap.containsKey(wallType)) {
				List<DesignProductResult> designProductResultList = returnMap.get(wallType);
				designProductResultList.add(designProductResult);
			}else {
				List<DesignProductResult> designProductResultList = new ArrayList<DesignProductResult>();
				designProductResultList.add(designProductResult);
				returnMap.put(wallType, designProductResultList);
			}
		}
		// 墙体分类归类 ->end
		
		return returnMap;
	}

	/**
	 *  取出样板房中附墙+非主墙/主墙的list
	 *  
	 *  @author huangsongbo
	 * @param designProductResultListBeijing
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private Map<getDesignProductResultListBeijingMapEnum, List<DesignProductResult>> getDesignProductResultListBeijingMap(
			List<DesignProductResult> designProductResultListBeijing) throws IntelligenceDecorationException {
		Map<getDesignProductResultListBeijingMapEnum, List<DesignProductResult>> returnMap = new HashMap<IntelligenceDecorationServiceImpl.getDesignProductResultListBeijingMapEnum, List<DesignProductResult>>();
		List<DesignProductResult> designTempletMainBeijingInfoList = new ArrayList<DesignProductResult>();
		List<DesignProductResult> designTempletDeputyBeijingInfoList = new ArrayList<DesignProductResult>();
		
		// 分类 ->start
		if(Lists.isNotEmpty(designProductResultListBeijing)) {
			for(DesignProductResult designProductResult : designProductResultListBeijing) {
				if(designProductResult.getGroupType() != null && 1 == designProductResult.getGroupType().intValue()
						&& designProductResult.getIsMainStructureProduct() != null && 1 != designProductResult.getIsMainStructureProduct().intValue()) {
					// 结构背景墙中的非主墙
					designTempletDeputyBeijingInfoList.add(designProductResult);
					continue;
				}
				Integer wallOrientationInteger = null;
				try {
					wallOrientationInteger = Integer.valueOf(designProductResult.getWallOrientation().trim());
				}catch (Exception e) {
					throw new IntelligenceDecorationException("样板房中某个背景墙的墙体方位数据异常:wallOrientation:" + designProductResult.getWallOrientation());
				}
				if(wallOrientationInteger != null) {
					if(wallOrientationInteger % 100 != 0) {
						// 附墙
						designTempletDeputyBeijingInfoList.add(designProductResult);
						continue;
					}
				}
				
				// 剩下的为主墙
				designTempletMainBeijingInfoList.add(designProductResult);
			}
		}
		// 分类 ->end
		
		returnMap.put(getDesignProductResultListBeijingMapEnum.mainBeijing, designTempletMainBeijingInfoList);
		returnMap.put(getDesignProductResultListBeijingMapEnum.deputyBeijing, designTempletDeputyBeijingInfoList);
		return returnMap;
	}

	/**
	 * mainBeijing 主墙
	 * deputyBeijing 附墙+结构背景墙非主墙
	 * 
	 * @author huangsongbo
	 */
	private enum getDesignProductResultListBeijingMapEnum{
		mainBeijing, deputyBeijing
	}
	
	/**
	 * 剔除部分不需要匹配的背景墙(结构背景墙非主墙/附墙)
	 * 
	 * @author huangsongbo
	 * @param planProductList
	 * @return
	 * @throws IntelligenceDecorationException
	 */
	private List<DesignProductResult> getDesignProductRecommendedResultList(List<DesignProductResult> planProductList)
			throws IntelligenceDecorationException {
		List<DesignProductResult> designProductRecommendedResultList = new ArrayList<DesignProductResult>();
		if(planProductList == null) {
			return designProductRecommendedResultList;
		}
		for(DesignProductResult designProductResult : planProductList) {
			logger.debug("posName:" + designProductResult.getPosName());
			if(beijingValuekeyList.indexOf(designProductResult.getSmallTypeKey()) != -1) {
				// 结构背景墙非主墙不参与匹配 ->start
				if(designProductResult.getGroupType() != null 
						&& 1 == designProductResult.getGroupType().intValue() 
						&& (designProductResult.getIsMainStructureProduct() == null || 1 != designProductResult.getIsMainStructureProduct().intValue())) {
					// 是结构背景墙但是非主墙
					continue;
				}
				// 结构背景墙非主墙不参与匹配 ->end
				// 附墙不参与匹配 ->start
				if(StringUtils.isNotBlank(designProductResult.getWallOrientation())) {
					Integer wallOrientationInteger = null;
					try {
						wallOrientationInteger = Integer.valueOf(designProductResult.getWallOrientation().trim());
					}catch (Exception e) {
						throw new IntelligenceDecorationException("样板房中某个背景墙的墙体方位数据异常:wallOrientation:" + designProductResult.getWallOrientation());
					}
					if(wallOrientationInteger != null) {
						if(wallOrientationInteger % 100 != 0) {
							// 附墙
							continue;
						}
					}
				}
				// 附墙不参与匹配 ->end
				designProductRecommendedResultList.add(designProductResult);
			}
		}
		return designProductRecommendedResultList;
	}

	/**
	 * 背景墙数据list分类(按照墙体分类)
	 * 
	 * @author huangsongbo
	 * @param designProductRecommendedResultList
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private Map<String, List<DesignProductResult>> getDesignProductResultListByWallType(
			List<DesignProductResult> designProductRecommendedResultList) throws IntelligenceDecorationException {
		
		Map<String, List<DesignProductResult>> returnMap = new HashMap<String, List<DesignProductResult>>();
		// 参数验证/处理 ->start
		if(Lists.isEmpty(designProductRecommendedResultList)) {
			return returnMap;
		}
		// 参数验证/处理 ->end
		
		// 墙体分类归类 ->start
		for(DesignProductResult designProductResult : designProductRecommendedResultList) {
			String wallType = designProductResult.getWallType();
			if(StringUtils.isBlank(wallType)) {
				throw new IntelligenceDecorationException("样板房中有背景墙没有录墙体类型,productCode:" + designProductResult.getProductCode());
			}
			if(returnMap.containsKey(wallType)) {
				List<DesignProductResult> designProductResultList = returnMap.get(wallType);
				designProductResultList.add(designProductResult);
			}else {
				List<DesignProductResult> designProductResultList = new ArrayList<DesignProductResult>();
				designProductResultList.add(designProductResult);
				returnMap.put(wallType, designProductResultList);
			}
		}
		// 墙体分类归类 ->end
		
		return returnMap;
	}

	/**
	 * 获取被删除的背景墙信息
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private List<Map<deletedBeijingProductInfoMapKeyEnum, String>> getDeletedBeijingProductInfo(Integer recommendedPlanId) throws IntelligenceDecorationException {
		if(recommendedPlanId == null) {
			throw new IntelligenceDecorationException("推荐方案id不能为空");
		}
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
		if(designPlanRecommended == null) {
			throw new IntelligenceDecorationException("推荐方案没有找到,推荐方案id:" + recommendedPlanId);
		}
		
		// 返回结果
		Integer designTempletId = designPlanRecommended.getDesignTemplateId();
		
		// 获取样板房中所有背景墙信息
		List<DesignTempletProduct> beijingDesignTempletProductList = designTempletProductService.getBeijingProductInfo(designTempletId);
		List<Map<deletedBeijingProductInfoMapKeyEnum, String>> beijingDesignTempletProductMapList= new ArrayList<Map<deletedBeijingProductInfoMapKeyEnum,String>>();
		if(Lists.isNotEmpty(beijingDesignTempletProductList)) {
			for(DesignTempletProduct designTempletProduct : beijingDesignTempletProductList) {
				Map<deletedBeijingProductInfoMapKeyEnum, String> designTempletProductMap = new HashMap<IntelligenceDecorationServiceImpl.deletedBeijingProductInfoMapKeyEnum, String>();
				designTempletProductMap.put(deletedBeijingProductInfoMapKeyEnum.groupType, designTempletProduct.getGroupType() + "");
				designTempletProductMap.put(deletedBeijingProductInfoMapKeyEnum.isMainStructureProduct, designTempletProduct.getIsMainStructureProduct() + "");
				designTempletProductMap.put(deletedBeijingProductInfoMapKeyEnum.wallOrientation, designTempletProduct.getWallOrientation());
				designTempletProductMap.put(deletedBeijingProductInfoMapKeyEnum.wallType, designTempletProduct.getWallType());
				beijingDesignTempletProductMapList.add(designTempletProductMap);
			}
		}
		
		// 获取推荐方案中所有背景墙信息
		List<DesignPlanRecommendedProduct> beijingDesignPlanRecommendedProductList = designPlanRecommendedProductServiceV2.getBeijingProductInfo(recommendedPlanId);
		List<Map<deletedBeijingProductInfoMapKeyEnum, String>> beijingDesignPlanRecommendedProductMapList= new ArrayList<Map<deletedBeijingProductInfoMapKeyEnum,String>>();
		if(Lists.isNotEmpty(beijingDesignPlanRecommendedProductList)) {
			for(DesignPlanRecommendedProduct designPlanRecommendedProduct : beijingDesignPlanRecommendedProductList) {
				Map<deletedBeijingProductInfoMapKeyEnum, String> designPlanRecommendedProductMap = new HashMap<IntelligenceDecorationServiceImpl.deletedBeijingProductInfoMapKeyEnum, String>();
				designPlanRecommendedProductMap.put(deletedBeijingProductInfoMapKeyEnum.groupType, designPlanRecommendedProduct.getGroupType() + "");
				designPlanRecommendedProductMap.put(deletedBeijingProductInfoMapKeyEnum.isMainStructureProduct, designPlanRecommendedProduct.getIsMainStructureProduct() + "");
				designPlanRecommendedProductMap.put(deletedBeijingProductInfoMapKeyEnum.wallOrientation, designPlanRecommendedProduct.getWallOrientation());
				designPlanRecommendedProductMap.put(deletedBeijingProductInfoMapKeyEnum.wallType, designPlanRecommendedProduct.getWallType());
				beijingDesignPlanRecommendedProductMapList.add(designPlanRecommendedProductMap);
			}
		}
		// 处理出被删除的样板房信息
		beijingDesignTempletProductMapList.removeAll(beijingDesignPlanRecommendedProductMapList);
		
		// 剔除非主墙背景墙(结构背景墙非主墙+附墙) ->start
		for (int index = 0; index < beijingDesignTempletProductMapList.size(); index++) {
			Map<deletedBeijingProductInfoMapKeyEnum, String> deletedBeijingProductInfoMap = beijingDesignTempletProductMapList.get(index);
			if(deletedBeijingProductInfoMap == null) {
				beijingDesignTempletProductMapList.remove(index);
				index --;
				continue;
			}
			// 踢除附墙 ->start
			String wallOrientation = deletedBeijingProductInfoMap.get(deletedBeijingProductInfoMapKeyEnum.wallOrientation);
			Integer wallOrientationInteger = null;
			try {
				wallOrientationInteger = Integer.valueOf(wallOrientation.trim());
			}catch (Exception e) {
				throw new IntelligenceDecorationException("样板房中某个背景墙的墙体方位数据异常:wallOrientation:" + wallOrientation);
			}
			if(wallOrientationInteger == null) {
				throw new IntelligenceDecorationException("样板房中某个背景墙的墙体方位数据异常:wallOrientation:" + wallOrientation);
			}
			if(wallOrientationInteger % 100 != 0) {
				// 附墙
				beijingDesignTempletProductMapList.remove(index);
				index --;
				continue;
			}
			// 踢除附墙 ->end
			// 提出结构背景墙非主墙 ->start
			String groupType = deletedBeijingProductInfoMap.get(deletedBeijingProductInfoMapKeyEnum.groupType);
			String isMainStructureProduct = deletedBeijingProductInfoMap.get(deletedBeijingProductInfoMapKeyEnum.isMainStructureProduct);
			if(StringUtils.equals("1", groupType) && !StringUtils.equals("1", isMainStructureProduct)) {
				beijingDesignTempletProductMapList.remove(index);
				index --;
				continue;
			}
			// 提出结构背景墙非主墙 ->end
		}
		// 剔除非主墙背景墙(结构背景墙非主墙+附墙) ->end
		return beijingDesignTempletProductMapList;
	}

	/**
	 * wallType 墙体分类
	 * wallOrientation 墙体方位
	 * groupType 是否是结构背景墙 0:否;1:是
	 * isMainStructureProduct 是否是主墙
	 * @author huangsongbo
	 *
	 */
	public enum deletedBeijingProductInfoMapKeyEnum {
		wallType, wallOrientation, groupType, isMainStructureProduct
	}
	
	/**
	 * @author xiaoxc
	 * 错误数据日志打印
	 * @param templetProduct
	 * @param planProduct
	 * @param logInfo
	 * @return
	 */
	private StringBuffer getLogInfo(DesignProductResult templetProduct,DesignProductResult planProduct,StringBuffer logInfo){
		String bjType = Utils.getValue("app.smallProductType.beiJingWall", "");
		if (templetProduct != null) {
			String templetProductCode = templetProduct.getProductCode();
			String templetWallType = templetProduct.getWallType();
			String templetWallOrientation = templetProduct.getWallOrientation();
			Integer templetProductIndex = templetProduct.getProductIndex();
			String templetPosName = templetProduct.getPosName();
			String templetRegionMark = templetProduct.getRegionMark();
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(templetProduct.getBigTypeKey())) {
				if (StringUtils.isEmpty(templetRegionMark) || "0".equals(templetRegionMark)) {
					logInfo.append("[样板房:posName="+templetPosName+";productCode="+templetProductCode+";regionMark="+templetRegionMark+";尺寸代码为空];");
				}
			}
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(templetProduct.getBigTypeKey())) {
				if (templetProduct.getProductGroupId() != null && templetProduct.getProductGroupId() > 0) {
					if (StringUtils.isEmpty(templetWallOrientation) || "0".equals(templetWallOrientation)
							|| StringUtils.isEmpty(templetWallType) || "0".equals(templetWallType)
							|| templetProductIndex == null || templetProductIndex == 0) {
						logInfo.append("[样板房:posName="+templetPosName+";productCode="+templetProductCode+";wallType="+templetWallType+";wallOrientation="+templetWallOrientation+";productIndex"+templetProductIndex+"];");
					}
				}else{
					if(bjType.indexOf(templetProduct.getSmallTypeKey()) != -1){
						if (StringUtils.isEmpty(templetWallOrientation) || "0".equals(templetWallOrientation)
								|| StringUtils.isEmpty(templetWallType) || "0".equals(templetWallType)) {
							logInfo.append("[样板房:posName="+templetPosName+";productCode="+templetProductCode+";wallType="+templetWallType+";wallOrientation="+templetWallOrientation+"];");
						}
					}
				}
			}
			if(ProductTypeConstant.PRODUCT_BIG_TYPE_LI.equals(templetProduct.getBigTypeKey())){
				if (StringUtils.isEmpty(templetWallType) || "0".equals(templetWallType)) {
					logInfo.append("[样板房:posName="+templetPosName+";productCode="+templetProductCode+";wallType="+templetWallType+"];");
				}
			}
		}

		if (planProduct != null) {
			String planProductCode = planProduct.getProductCode();
			String planWallType = planProduct.getWallType();
			String planWallOrientation = planProduct.getWallOrientation();
			Integer planProductIndex = planProduct.getProductIndex();
			String planPosName = planProduct.getPosName();
			String planRegionMark = planProduct.getRegionMark();

			if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(planProduct.getBigTypeKey())) {
				if (StringUtils.isEmpty(planRegionMark) || "0".equals(planRegionMark)) {
					logInfo.append("[方案：posName="+planPosName+";productCode="+planProductCode+";regionMark="+planRegionMark+";尺寸代码为空];");
				}
			}
			if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(planProduct.getBigTypeKey())) {
				if (planProduct.getProductGroupId() != null && planProduct.getProductGroupId() > 0) {
					if (StringUtils.isEmpty(planWallType) || "0".equals(planWallType)
							|| StringUtils.isEmpty(planWallOrientation) || "0".equals(planWallOrientation)
							|| planProductIndex == null || planProductIndex == 0) {
						logInfo.append("方案：posName="+planPosName+";productCode="+planProductCode+";wallType="+planWallType+";wallOrientation="+planWallOrientation+";planProductIndex"+planProductIndex+"];");
					}
				}else{
					if(bjType.indexOf(planProduct.getSmallTypeKey()) != -1){
						if (StringUtils.isEmpty(planWallType) || "0".equals(planWallType)
								|| StringUtils.isEmpty(planWallOrientation) || "0".equals(planWallOrientation)) {
							logInfo.append("方案：posName="+planPosName+";productCode="+planProductCode+";wallType="+planWallType+";wallOrientation="+planWallOrientation+"];");
						}
					}
				}
			}
			if(ProductTypeConstant.PRODUCT_BIG_TYPE_LI.equals(planProduct.getBigTypeKey())){
				if (StringUtils.isEmpty(planWallType) || "0".equals(planWallType)){
					logInfo.append("方案：posName="+planPosName+";productCode="+planProductCode+";wallType="+planWallType+"];");
				}
			}
		}

		return logInfo;
	}

	/**
	 * 判断是否需要删除背景墙
	 * 场景:
	 * 主墙被删除,一件装修后副墙也应该被删除
	 * 
	 * @author huangsongbo
	 * @param wallOrientation
	 * @param deleteWallOrientationList
	 * @return
	 */
	private boolean judgeWhetherToDelete(String wallOrientation, List<String> deleteWallOrientationList) {
		// 参数验证 ->start
		if(StringUtils.isBlank(wallOrientation)) {
			return false;
		}
		if(Lists.isEmpty(deleteWallOrientationList)) {
			return false;
		}
		// 参数验证 ->end
		
		// wallOrientation:200; deleteWallOrientationList:[200]
		// 前缀是2,且wallOrientation>deleteWallOrientationList(主墙一般是200,副墙是210,220..)
		// 找出deleteWallOrientationList中是否有前缀2的墙体方位
		for(String deleteWallOrientation : deleteWallOrientationList) {
			if(wallOrientation.substring(0, 1).equals(deleteWallOrientation.substring(0, 1))) {
				if(Pattern.matches(wallOrientation.substring(0, 1) + "0+", wallOrientation)) {
					// 如果wallOrientation是200,那么deleteWallOrientation也要是200
					if(StringUtils.equals(wallOrientation, deleteWallOrientation)) {
						return true;
					}
				}else {
					// 必须相等,或者deleteWallOrientation是200
					if(StringUtils.equals(wallOrientation, deleteWallOrientation) || Pattern.matches(deleteWallOrientation.substring(0, 1) + "0+", deleteWallOrientation)) {
						return true;
					}
				}
			}else {
				
			}
		}
		return false;
	}

	@Override
	public List<MatchGroupProductResult> getGroupProductDataV2(DesignPlanRecommended designPlanRecommended,
			DesignTemplet designTemplet) {
		
		// 返回结果
		List<MatchGroupProductResult> returnList = new ArrayList<MatchGroupProductResult>();
		
		// 参数验证 ->start
		if(designPlanRecommended == null){
			return returnList;
		}
		if(designTemplet == null){
			return returnList;
		}
		// 参数验证 ->end
		
		// 查询样板房白膜组合的主产品列表
		List<DesignTempletProduct> designTempletProductList = designTempletProductService.getMainProductListByTempletId(designTemplet.getId());
		
		if(designTempletProductList == null || designTempletProductList.size() == 0){
			return returnList;
		}
		
		//查询方案推荐中存在的组合的主产品列表
		List<DesignPlanRecommendedProduct> designPlanRecommendedProductList = 
				designPlanRecommendedProductServiceV2.getMainProductListByPlanRecommendedId(designPlanRecommended.getId());

		if (Lists.isEmpty(designPlanRecommendedProductList)) {
			return returnList;
		}
		
		if (designPlanRecommendedProductList == null || designPlanRecommendedProductList.size() < 0) {
			logger.error("designTempletProductList size为==>"+designTempletProductList.size());
		}

		// 匹配组合 ->start
		
		// 记录已经推荐方案中已经匹配过的组合
		List<String> planGroupIdList = new ArrayList<String>();
		
		for(DesignTempletProduct designTempletProduct : designTempletProductList){
			
			MatchGroupProductResult matchGroupProductResult = new MatchGroupProductResult();
			
			// 得到匹配分数
			List<Integer> scoreList = this.getScoreList(designTempletProduct, designPlanRecommendedProductList);
			
			// 如果scoreList的元素都是0,意思是没有匹配上组合,需要把样板房该白膜组合删除
			boolean noMatch = this.getNoMatch(scoreList);
			
			// 设置主产品对应的组合的所有产品信息(bmGroupProductList)
			List<Map<String,String>> bmGroupProductList = this.getBmGroupProductListByTempletIdAndGroupId(designTemplet.getId(), designTempletProduct.getProductGroupId());
			matchGroupProductResult.setBmMianProductCode(designTempletProduct.getProductCode());
			matchGroupProductResult.setBmGroupProductList(bmGroupProductList);
			
			if(noMatch){
				
			}else{
				// 找出匹配分数最高的组合主产品
				int indexMaxScore = this.getIndexMaxScore(scoreList);
				// 识别该组合是否被匹配过,及对应的逻辑处理 ->start
				String planGroupId = designPlanRecommendedProductList.get(indexMaxScore).getPlanGroupId();
				if(StringUtils.isNotEmpty(planGroupId)){
					if(planGroupIdList.indexOf(planGroupId) == -1){
						planGroupIdList.add(planGroupId);
					}else{
						matchGroupProductResult.setIsReName(ProductConstant.MATCHGROUPPRODUCTRESULT_ISRENAME_ISMATCHED);
					}
				}
				// 识别该组合是否被匹配过,及对应的逻辑处理 ->end
				this.getGroupMatcheDataV2(designPlanRecommendedProductList.get(indexMaxScore), matchGroupProductResult, designPlanRecommended.getId());
				if(matchGroupProductResult.getIsReName().intValue() == ProductConstant.MATCHGROUPPRODUCTRESULT_ISRENAME_ISMATCHED.intValue()){
					String planGroupIdOld = matchGroupProductResult.getPlanGroupId();
					String planGroupIdNew = planGroupIdOld.substring(0, planGroupIdOld.indexOf("_")) + "_" + new Date().getTime();
					matchGroupProductResult.setPlanGroupId(planGroupIdNew);
				}
			}
			if(StringUtils.isNotEmpty(matchGroupProductResult.getTempletMianProductCode())){
				returnList.add(matchGroupProductResult);
			}else{}
		}
		
		return returnList;
	}

	/**
	 * scoreList得到最大元素的index
	 * @param scoreList
	 * @return
	 */
	private int getIndexMaxScore(List<Integer> scoreList) {
		int MaxScoreIndex = 0;
		Integer item = 0;
		for (int index = 0; index < scoreList.size(); index ++) {
			Integer score = scoreList.get(index);
			if(score > item){
				item = score;
				MaxScoreIndex = index;
			}
		}
		return MaxScoreIndex;
	}

	/**
	 * 检测scoreList的元素是不是都是0,如果都是0,返回true
	 * 
	 * @author huangsongbo
	 * @param scoreList
	 * @return
	 */
	private boolean getNoMatch(List<Integer> scoreList) {
		boolean returnBoolean = true;
		if(scoreList == null || scoreList.size() == 0){
			return false;
		}
		for(Integer score : scoreList){
			if(0 != score.intValue()){
				return false;
			}
		}
		return returnBoolean;
	}

	/**
	 * 通过样板房id,组合id,获取该样板房的组合明细信息
	 * copy from xiaoxiangcheng's function
	 * 
	 * @author huangsongbo
	 * @param designTempletId
	 * @param productGroupId
	 * @return
	 */
	private List<Map<String, String>> getBmGroupProductListByTempletIdAndGroupId(Integer designTempletId, Integer productGroupId) {
		// 参数验证 ->start
		if(designTempletId == null){
			return null;
		}
		if(productGroupId == null){
			return null;
		}
		// 参数验证 ->end
		
		DesignTempletProduct templetGroupProduct = new DesignTempletProduct();
		templetGroupProduct.setDesignTempletId(designTempletId);
		templetGroupProduct.setProductGroupId(productGroupId);
		templetGroupProduct.setPlanGroupId(templetGroupProduct.getPlanGroupId());
		templetGroupProduct.setIsDeleted(0);
		templetGroupProduct.setGroupType(0);
		List<DesignTempletProduct> groupProductList = designTempletProductMapper.byTempletIdMainProduct(templetGroupProduct);
		List<Map<String,String>> bmGroupProductList = new ArrayList<>();
		Map<String,String> map = null;
		if (Lists.isNotEmpty(groupProductList)) {
			for (DesignTempletProduct groupProduct : groupProductList) {
				map = new HashMap<>();
				map.put("productCode",groupProduct.getProductCode());
				map.put("posIndexPath",groupProduct.getPosIndexPath());
				bmGroupProductList.add(map);
			}
		}
		return bmGroupProductList;
	}

	/**
	 * 匹配样板房白膜组合主产品和对应的推荐方案的所有组合(主产品),得到一个分数的list
	 * (该list的index为推荐方案组合主产品List的index,分数越高则匹配度越高,0为未匹配上,未匹配上的白膜组合要删除)
	 * 
	 * @author huangsongbo
	 * @param designTempletProduct
	 * @param designPlanRecommendedProductList
	 * @return
	 */
	private List<Integer> getScoreList(DesignTempletProduct designTempletProduct,
			List<DesignPlanRecommendedProduct> designPlanRecommendedProductList) {
		
		List<Integer> scoreList = new ArrayList<Integer>();
		
		// 参数验证 ->start
		if(designTempletProduct == null){
			return scoreList;
		}
		if(designPlanRecommendedProductList == null || designPlanRecommendedProductList.size() == 0){
			return scoreList;
		}
		Integer templetProductBigTypeValue = designTempletProduct.getProductTypeValue();
		Integer templetProductSmallTypeValue = designTempletProduct.getProductSmallTypeValue();
		// 需要转化为白膜对应的小分类
		templetProductSmallTypeValue = baseProductService.dealWithBaimoSmallTypeValue(templetProductBigTypeValue, templetProductSmallTypeValue);
		if(templetProductBigTypeValue == null){
			return scoreList;
		}
		// 参数验证 ->end
		
		// 初始化scoreList ->start
		for (int index = 0; index < designPlanRecommendedProductList.size(); index++) {
			scoreList.add(0);
		}
		// 初始化scoreList ->end
		
		// 匹配组合 ->start
		for (int index = 0; index < designPlanRecommendedProductList.size(); index++) {
			DesignPlanRecommendedProduct designPlanRecommendedProduct = designPlanRecommendedProductList.get(index);
			// 匹配组合类型
			if (designTempletProduct.getCompositeType() != null && designTempletProduct.getCompositeType().equals(designPlanRecommendedProduct.getCompositeType())) {
				// 大类匹配成功,score ++,并且匹配区域编码
				scoreList.set(index, scoreList.get(index) + 1);

				if(StringUtils.isEmpty(designTempletProduct.getRegionMark()) && StringUtils.isEmpty(designPlanRecommendedProduct.getRegionMark())){
					continue;
				}
				if(StringUtils.equals(designTempletProduct.getRegionMark(), designPlanRecommendedProduct.getRegionMark())){
					// 区域编码匹配成功,score += 1
					scoreList.set(index, scoreList.get(index) + 1);
				}
			}
			// 匹配组合大类,小类(也就是主产品大类,小类)
//			if(
//					designPlanRecommendedProduct.getProductBigTypeValue() != null && templetProductBigTypeValue.intValue() == designPlanRecommendedProduct.getProductBigTypeValue().intValue()
//				// 代码注释原因:修改为只有柜子才识别小类
//					/*&& designPlanRecommendedProduct.getProductSmallTypeValue() != null && templetProductSmallTypeValue.intValue() == designPlanRecommendedProduct.getProductSmallTypeValue()*/
//					){
//				boolean flag = true;
//				if(designPlanRecommendedProduct.getProductBigTypeValue().intValue() == 10){
//					if(designPlanRecommendedProduct.getProductSmallTypeValue() != null && templetProductSmallTypeValue.intValue() == designPlanRecommendedProduct.getProductSmallTypeValue()){
//
//					}else{
//						flag = false;
//					}
//				}
//				if(flag){
//					// 大类匹配成功,score ++,并且匹配区域编码
//					scoreList.set(index, scoreList.get(index) + 1);
//
//					// 小类也一致,再加一分
//					if(designPlanRecommendedProduct.getProductSmallTypeValue() != null && templetProductSmallTypeValue.intValue() == designPlanRecommendedProduct.getProductSmallTypeValue()){
//						scoreList.set(index, scoreList.get(index) + 1);
//					}
//
//					if(StringUtils.isEmpty(designTempletProduct.getRegionMark()) && StringUtils.isEmpty(designPlanRecommendedProduct.getRegionMark())){
//						continue;
//					}
//
//					if(StringUtils.equals(designTempletProduct.getRegionMark(), designPlanRecommendedProduct.getRegionMark())){
//						// 区域编码匹配成功,score += 2
//						scoreList.set(index, scoreList.get(index) + 2);
//					}
//				}
//			}
		}
		// 匹配组合 ->end
		
		return scoreList;
	}

	@Override
	public List<ProductMatchInfoDTO> productListMatch(List<PlanProductInfo> templetProductList,
		Map<String, List<PlanProductInfo>> productListmap, Integer planId, String username, Integer matchType, Integer opType, DesignTemplet designTemplet) throws IntelligenceDecorationException {
		
		List<ProductMatchInfoDTO> productMatchInfoDTOList = new ArrayList<BedroomProductMatchDTO.ProductMatchInfoDTO>();
		
		// *参数验证 ->start
		if(Lists.isEmpty(templetProductList)) {
			return productMatchInfoDTOList;
		}
		if(productListmap == null) {
			return productMatchInfoDTOList;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证 ->end
		
		// 记录下所有背景墙信息,后面再特殊匹配
		List<PlanProductInfo> beijingPlanProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
		
		// 存系列产品,用来后面单独匹配(系列标识:样板房产品信息)(系列标识:该系列标识对应的样板房产品list)
		Map<String, List<PlanProductInfo>> seriesProductInfoMap = new HashMap<String, List<PlanProductInfo>>();
		
		for(PlanProductInfo planProductInfo : templetProductList) {
			
			PlanProductInfo planProductInfoMatched = null;
			
			// 组装系列产品,放到后面去匹配 ->start
			String seriesSign = planProductInfo.getSeriesSign();
			if(StringUtils.isNotEmpty(seriesSign)){
				// 硬装替换,不需要进行系列匹配(默认系列都是软装)
				if(seriesProductInfoMap.containsKey(seriesSign)) {
					List<PlanProductInfo> planProductInfoList = seriesProductInfoMap.get(seriesSign);
					if(Lists.isNotEmpty(planProductInfoList)) {
						planProductInfoList.add(planProductInfo);
					}else {
						planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
						planProductInfoList.add(planProductInfo);
						seriesProductInfoMap.put(seriesSign, planProductInfoList);
					}
				}else {
					List<PlanProductInfo> planProductInfoList = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
					planProductInfoList.add(planProductInfo);
					seriesProductInfoMap.put(seriesSign, planProductInfoList);
				}
				continue;
			}
			// 组装系列产品,放到后面去匹配 ->end
			
			StringBuffer matchInfo = new StringBuffer("");
			
			matchInfo.append("正在匹配:posName:" + planProductInfo.getPosName() + ";baseProductCode:" + planProductInfo.getProductCode() + "\n");
			
			// *参数验证 ->start
			if(StringUtils.isEmpty(planProductInfo.getBigTypeValuekey())) {
				continue;
			}
			// *参数验证 ->end
			
			// 用于debug(最后要删除) ->start
			if(StringUtils.isNotEmpty(IntelligenceDecorationController.posNameDebug)) {
				if(StringUtils.equals(IntelligenceDecorationController.posNameDebug, planProductInfo.getPosName())) {
					logger.debug("debug");
				}
			}
			// 用于debug(最后要删除) ->end
			
			ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
			productMatchInfoDTO.setDelPosName(planProductInfo.getPosName());
			
			// 决定要不要执行productMatchInfoDTOList.add(productMatchInfoDTO)
			// flag = true:添加productMatchInfoDTO;flag = false:检测到匹配为特殊背景墙匹配逻辑,放在后面再特殊匹配
			// 为了后面再特殊处理背景墙(匹配背景墙逻辑特殊,先匹配小类,匹配剩下的再按优先级(背景墙小类有一个小类优先级)匹配)
			boolean flag = true;
			
			String bigTypeValuekey = planProductInfo.getBigTypeValuekey();
			String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
			List<PlanProductInfo> theSameBigTypePlanProductInfoList = null;
			
			if(productListmap.containsKey(bigTypeValuekey)) {
				theSameBigTypePlanProductInfoList = productListmap.get(bigTypeValuekey);
			}
			
			if(0 == matchType) {
				if(Lists.isEmpty(theSameBigTypePlanProductInfoList)) {
					// 未检测到推荐方案中有同大类的产品
					matchInfo.append("匹配失败(未检测到推荐方案有该白膜对应的大类)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					productMatchInfoDTO.setMatchInfo(matchInfo.toString());
					productMatchInfoDTOList.add(productMatchInfoDTO);
					continue;
				}
			}
			
			// *匹配地面->start
			if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_DIM, bigTypeValuekey)) {
				if(0 != matchType && (StringUtils.equals("basic_dit", smallTypeValuekey) || StringUtils.equals("dit", smallTypeValuekey))) {
					// 硬装替换的话,地毯不算硬装,特殊处理 ->start
					planProductInfoMatched = planProductInfo.clone();
					// 硬装替换的话,地毯不算硬装,特殊处理 ->end
				}else {
					planProductInfoMatched = this.productListMatchDim(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}
			}
			// *匹配地面->end
			
			// *匹配门->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_MENG, bigTypeValuekey)) {
				planProductInfoMatched = this.productListMatchMeng(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
			}
			// *匹配门->start
			
			// *匹配墙面 ->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, bigTypeValuekey)) {
				if(beijingValuekeyList.indexOf(smallTypeValuekey) != -1) {
					// *背景墙特殊处理(处理完其他的单品,再单独处理背景墙list) ->start
					beijingPlanProductInfoList.add(planProductInfo);
					flag = false;
					// *背景墙特殊处理(处理完其他的单品,再单独处理背景墙list) ->end
				}else {
					// 其他qiangm
					planProductInfoMatched = productListMatchQiangm(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}

			}
			// *匹配墙面 ->end
			
			// *匹配天花 ->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH, bigTypeValuekey)) {
				planProductInfoMatched = productListMatchTianh(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo, designTemplet.getSmallpoxIdentify());
			}
			// *匹配天花 ->end
			
			// *匹配家纺 ->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_HO, bigTypeValuekey)) {
				if(0 == matchType) {
					planProductInfoMatched = productListMatchHo(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}else {
					planProductInfoMatched = planProductInfo.clone();
				}
			}
			// *匹配家纺 ->end
			
			// 匹配柜子 ->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_CA, bigTypeValuekey)) {
				if(0 == matchType) {
					planProductInfoMatched = productListMatchCa(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}else {
					planProductInfoMatched = planProductInfo.clone();
				}
			}
			// 匹配柜子 ->end
			
			// 匹配卫浴(淋浴房) ->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_CA, bigTypeValuekey)) {
				if(0 == matchType) {
					planProductInfoMatched = productListMatchBa(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}else {
					planProductInfoMatched = planProductInfo.clone();
				}
			}
			// 匹配卫浴(淋浴房) ->end
			
			// 厨房配件匹配(水槽) ->start
			else if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_KP, bigTypeValuekey)) {
				if(0 == matchType) {
					planProductInfoMatched = productListMatchKp(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}else {
					planProductInfoMatched = planProductInfo.clone();
				}
			}
			// 厨房配件匹配(水槽) ->end
			
			// *其他大类 ->start
			else {
				if(0 == matchType) {
					planProductInfoMatched = productListMatchOther(planProductInfo, theSameBigTypePlanProductInfoList, matchInfo);
				}else {
					planProductInfoMatched = planProductInfo.clone();
				}
			}
			// *其他大类 ->end
			
			if(flag) {
				// 添加设计方案产品数据
				this.getproductMatchInfoDTO(planId, username, opType, planProductInfo, matchInfo, productMatchInfoDTO, planProductInfoMatched);
				productMatchInfoDTOList.add(productMatchInfoDTO);
			}
			
		}
		
		// 再特殊处理背景墙 ->start
		List<PlanProductInfo> theSameBigTypePlanProductInfoList = null;
		if(productListmap.containsKey(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM)) {
			theSameBigTypePlanProductInfoList = productListmap.get(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM);
		}
		// 只保留背景墙
		for (int index = 0; index < theSameBigTypePlanProductInfoList.size(); index++) {
			if(beijingValuekeyList.indexOf(theSameBigTypePlanProductInfoList.get(index).getSmallTypeValuekey()) != -1) {
				
			}else {
				theSameBigTypePlanProductInfoList.remove(index);
				index --;
			}
		}
		
		this.productListMatchBeijingQiangm(beijingPlanProductInfoList, theSameBigTypePlanProductInfoList, productMatchInfoDTOList, planId, username, opType);
		// 再特殊处理背景墙 ->end
		
		// 特殊处理系列产品 ->start
		this.productListMatchSeries(seriesProductInfoMap, productListmap, productMatchInfoDTOList, planId, username, opType, matchType);
		// 特殊处理系列产品 ->end
		
		return productMatchInfoDTOList;
	}

	private PlanProductInfo productListMatchKp(PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo) {
		// 参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(recommendedPlanProductInfoList)) {
			return null;
		}
		// 参数验证 ->end
		logger.debug("posName:" + planProductInfo.getPosName());
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		PlanProductInfo planProductInfoMatched = null;
		if(StringUtils.equals("basic_sikp", smallTypeValuekey)) {
			// 匹配水槽(添加过滤属性匹配)(basic_sikp)

			// *匹配参数设置 ->start
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekey(planProductInfo.getSmallTypeValuekey().replace("basic_", ""));
			/*planProductInfoParam.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));*/
			// *匹配参数设置 ->end
			
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				matchInfo.append("匹配失败(按初始化白膜的大小类匹配),删除样板房白膜(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				// 匹配产品属性
				matchInfo.append("匹配成功(按初始化白膜大小类)(匹配到:" + planProductInfoMatched.getProductCode() + "),准备匹配过滤属性\n");
				
				// 获取过滤属性 ->start
				planProductInfo.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));
				planProductInfoMatched.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfoMatched.getProductId()));
				// 获取过滤属性 ->end
			
				if(Utils.isMatched(planProductInfo.getProductFilterPropList(), planProductInfoMatched.getProductFilterPropList())){
					// 属性匹配成功,直接用推荐方案中的该产品
					matchInfo.append("属性匹配成功\n");
				}else {
					// 自行搜索
					matchInfo.append("属性匹配失败,带上推荐方案的衣柜的款式去系统中搜索\n");
					planProductInfoParam.setOrderStyleId(planProductInfoMatched.getStyleId());
					planProductInfoParam.setProductFilterPropList(planProductInfo.getProductFilterPropList());
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
					/*selectOneProduct方法增加过滤属性搜索条件*/
				}
			}
		
		}else {
			// 其他小类
			planProductInfoMatched = productListMatchOther(planProductInfo, recommendedPlanProductInfoList, matchInfo);
		}
		
		return planProductInfoMatched;
	}

	/**
	 * 匹配系列
	 * 
	 * @author huangsongbo
	 * @param seriesProductInfoMap 待匹配的系列产品信息
	 * @param productListmap 推荐方案中所有产品
	 * @param productMatchInfoDTOList 返回结果集
	 * @param matchType 0:全屋替换;1:硬装替换
	 * @throws IntelligenceDecorationException 
	 */
	private void productListMatchSeries(Map<String, List<PlanProductInfo>> seriesProductInfoMap,
			Map<String, List<PlanProductInfo>> productListmap,
			List<ProductMatchInfoDTO> productMatchInfoDTOList,
			Integer planId,
			String username,
			Integer opType, Integer matchType
			) throws IntelligenceDecorationException {
		// 参数验证/处理 ->start
		if(seriesProductInfoMap == null) {
			return;
		}
		if(productListmap == null) {
			return;
		}
		if(productMatchInfoDTOList == null) {
			return;
		}
		if(matchType == null) {
			matchType = 0;
		}
		// 参数验证/处理 ->end
		
		// 检测油烟机柜是否被删除 ->start
		boolean isDeleteedDyki = true;
		// 油烟机柜大类
		String bigTypeDgki = "dgki";
		// 油烟机柜小类
		String smallTypeDgki = "dyki";
		if(productListmap.containsKey(bigTypeDgki)) {
			List<PlanProductInfo> planProductInfoListDgki = productListmap.get(bigTypeDgki);
			if(Lists.isNotEmpty(planProductInfoListDgki)) {
				for(PlanProductInfo planProductInfo : planProductInfoListDgki) {
					if(StringUtils.equals(smallTypeDgki, planProductInfo.getSmallTypeValuekey())) {
						isDeleteedDyki = false;
						break;
					}
				}
			}
		}
		// 检测油烟机柜是否被删除 ->end
		
		// 系列对应的匹配产品(所有同组系列的产品,都按这个匹配到的产品的信息进行搜索)
		Map<String, PlanProductInfo> seriesMatchInfoMap = this.getSeriesMatchInfoMap(seriesProductInfoMap, productListmap);
		for(String key : seriesProductInfoMap.keySet()) {
			List<PlanProductInfo> templetPlanProductInfoList = seriesProductInfoMap.get(key);
			if(Lists.isNotEmpty(templetPlanProductInfoList)) {
				for(PlanProductInfo templetPlanProductInfo : templetPlanProductInfoList) {
					logger.debug("posName:" + templetPlanProductInfo.getPosName() + "productCode:" + templetPlanProductInfo.getProductCode());
					// test ->start
					if(StringUtils.equals("ctyki01", templetPlanProductInfo.getPosName())) {
						logger.debug("posName:" + templetPlanProductInfo.getPosName());
					}
					// test ->end
					// 根据系列匹配到的产品带上自身白膜属性自行搜索
					StringBuffer matchInfo = new StringBuffer();
					matchInfo.append("开始匹配posname:" + templetPlanProductInfo.getPosName() + ";productCode:" + templetPlanProductInfo.getProductCode() + "\n");
					ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
					PlanProductInfo planProductInfoMatched = null;
					if(0 == matchType) {
						// 如果推荐方案中油烟机柜删除,一键生成的方案也要删除
						if(StringUtils.equals("basic_" + smallTypeDgki, templetPlanProductInfo.getSmallTypeValuekey()) && isDeleteedDyki) {
							planProductInfoMatched = null;
						}else {
							planProductInfoMatched =	this.getProductMatchInfoDTOSeries(templetPlanProductInfo, seriesMatchInfoMap.get(key), matchInfo);
						}
					}else {
						planProductInfoMatched = templetPlanProductInfo.clone();
					}
					productMatchInfoDTO.setDelPosName(templetPlanProductInfo.getPosName());
					this.getproductMatchInfoDTO(planId, username, opType, templetPlanProductInfo, matchInfo, productMatchInfoDTO, planProductInfoMatched);
					productMatchInfoDTOList.add(productMatchInfoDTO);
				}
			}
		}
	}

	/**
	 * 系列匹配
	 * 根据匹配到的产品自行搜索
	 * 
	 * @author huangsongbo
	 * @param templetPlanProductInfo
	 * @param planProductInfo
	 * @param matchInfo 
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private PlanProductInfo getProductMatchInfoDTOSeries(PlanProductInfo templetPlanProductInfo,
			PlanProductInfo planProductInfo, StringBuffer matchInfo) throws IntelligenceDecorationException {
		// 参数验证/处理 ->start
		if(templetPlanProductInfo == null) {
			return null;
		}
		if(planProductInfo == null) {
			return null;
		}
		if(matchInfo == null) {
			matchInfo = new StringBuffer();
		}
		// 参数验证/处理 ->end
		
		Integer seriesId = planProductInfo.getSeriesId();
		if(seriesId == null || 0 == seriesId.intValue()) {
			throw new IntelligenceDecorationException("推荐方案中,产品" + planProductInfo.getProductCode() + "需要录系列属性");
		}
		
		PlanProductInfo planProductInfoParam = new PlanProductInfo();
		planProductInfoParam.setBigTypeValuekey(templetPlanProductInfo.getBigTypeValuekey());
		/*planProductInfoParam.setSmallTypeValuekey(templetPlanProductInfo.getSmallTypeValuekey().replace("basic_", ""));*/
		planProductInfoParam.setOrderSmallTypeValueKey(templetPlanProductInfo.getSmallTypeValuekey().replace("basic_", ""));
		/*planProductInfoParam.setSmallTypeValueList(baseProductService.getsmallTypeValueListBySmallTypeValueKey(templetPlanProductInfo.getSmallTypeValuekey().replace("basic_", "")));*/
		this.setSmallTypeListParam(planProductInfoParam, templetPlanProductInfo.getSmallTypeValuekey());
		planProductInfoParam.setSeriesId(seriesId);
		Map<String,String> stretchMap = baseProductService.getStretchZoomLength(templetPlanProductInfo.getSmallTypeValuekey());
		if (stretchMap != null && stretchMap.size() > 0) {
			int stretchLength = Utils.getIntValue(stretchMap.get(ProductModelConstant.STRETCH_LENGTH));
			this.setBeijingMatchParams(templetPlanProductInfo, planProductInfoParam, stretchLength);
		} else {
			planProductInfoParam.setProductLength(templetPlanProductInfo.getFullPaveLength());
		}
		matchInfo.append("搜索条件:seriesId:" + seriesId);
		// 设置属性过滤条件
		planProductInfoParam.setProductFilterPropList(baseProductService.getProductFilterPropList(templetPlanProductInfo.getProductId()));
		PlanProductInfo planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
		return planProductInfoMatched;
	}

	/**
	 * 设置小类list查询条件 in/not in
	 * 
	 * @author huangsongbo
	 * @param planProductInfoParam
	 * @param smallTypeValuekey
	 */
	private void setSmallTypeListParam(PlanProductInfo planProductInfoParam, String smallTypeValuekey) {
		// 参数验证/处理 ->start
		if(planProductInfoParam == null) {
			return;
		}
		if(StringUtils.isEmpty(smallTypeValuekey)) {
			return;
		}
		smallTypeValuekey = smallTypeValuekey.replace("basic_", "");
		// 参数验证/处理 ->end
		Map<getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum, List<Integer>> smallTypeListProperties = baseProductService.getsmallTypeValueListBySmallTypeValueKey(smallTypeValuekey);
		if(smallTypeListProperties == null) {
			return;
		}
		if(smallTypeListProperties.containsKey(getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum.in)) {
			planProductInfoParam.setSmallTypeValueList(smallTypeListProperties.get(getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum.in));
		}else {
			if(smallTypeListProperties.containsKey(getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum.notIn)) {
				planProductInfoParam.setExcludeSmallTypeValueList(smallTypeListProperties.get(getsmallTypeValueListBySmallTypeValueKeyMapKeyEnum.notIn));
			}
		}
	}

	/**
	 * 匹配系列产品,获得一个匹配结果集
	 * 
	 * @author huangsongbo
	 * @param seriesProductInfoMap
	 * @param productListmap
	 * @return
	 */
	private Map<String, PlanProductInfo> getSeriesMatchInfoMap(Map<String, List<PlanProductInfo>> seriesProductInfoMap,
			Map<String, List<PlanProductInfo>> productListmap) {
		Map<String, PlanProductInfo> returnMap = new HashMap<String, ProductListByTypeInfo.PlanProductInfo>();
		// 参数验证/处理 ->start
		if(seriesProductInfoMap == null) {
			return returnMap;
		}
		if(productListmap == null) {
			return returnMap;
		}
		// 参数验证/处理 ->end
		
		for(String key : seriesProductInfoMap.keySet()) {
			List<PlanProductInfo> templetPlanProductInfoList = seriesProductInfoMap.get(key);
			if(Lists.isNotEmpty(templetPlanProductInfoList)) {
				for(PlanProductInfo templetPlanProductInfo : templetPlanProductInfoList) {
					// 匹配参数设置 ->start
					PlanProductInfo planProductInfoParam = new PlanProductInfo();
					planProductInfoParam.setBigTypeValuekey(templetPlanProductInfo.getBigTypeValuekey());
					planProductInfoParam.setSmallTypeValuekey(templetPlanProductInfo.getSmallTypeValuekey().replace("basic_", ""));
					// 匹配参数设置 ->end
					
					PlanProductInfo planProductInfoMatched = this.checkDataFromMap
							(planProductInfoParam, productListmap.get(templetPlanProductInfo.getBigTypeValuekey()), true, checkDataFromMapEnum.defaultType);
					if(planProductInfoMatched == null) {
						
					}else {
						returnMap.put(key, planProductInfoMatched);
						break;
					}
				}
			}
		}
		return returnMap;
	}

	/**
	 * 保存设计方案产品
	 * 
	 * @author huangsongbo
	 * @param planId 设计方案id
	 * @param username 用户名
	 * @param opType 是否是自动渲染
	 * @param planProductInfo 样板房产品信息
	 * @param matchInfo 匹配信息
	 * @param productMatchInfoDTO 返回信息
	 * @param planProductInfoMatched 匹配上的产品信息
	 */
	private void getproductMatchInfoDTO(Integer planId, String username, Integer opType,
			PlanProductInfo planProductInfo, StringBuffer matchInfo, ProductMatchInfoDTO productMatchInfoDTO,
			PlanProductInfo planProductInfoMatched) {
		if(planProductInfoMatched != null) {
			planProductInfoMatched.setMatched(true);
			planProductInfoMatched.setPlanProductId(planProductInfo.getPlanProductId());
			planProductInfoMatched.setInitProductId(planProductInfo.getInitProductId());
			planProductInfoMatched.setBindParentProductId(planProductInfo.getBindParentProductId());
			planProductInfoMatched.setMeasureCode(planProductInfo.getMeasureCode());
			planProductInfoMatched.setRegionMark(planProductInfo.getRegionMark());
			
			// 有些结构当单品处理了,也要保留结构相关信息 ->start
			planProductInfoMatched.setGroupOrStructureId(planProductInfo.getGroupOrStructureId());
			planProductInfoMatched.setPlanGroupId(planProductInfo.getPlanGroupId());
			planProductInfoMatched.setGroupType(planProductInfo.getGroupType());
			planProductInfoMatched.setIsStandard(planProductInfo.getIsStandard());
			// 有些结构当单品处理了,也要保留结构相关信息 ->end
			
			productMatchInfoDTO.setProductCode(planProductInfoMatched.getProductCode());
			productMatchInfoDTO.setBigTypeValuekey(planProductInfoMatched.getBigTypeValuekey());
			productMatchInfoDTO.setSmallTypeValuekey(planProductInfoMatched.getSmallTypeValuekey());
		}
		Integer designPlanProductId = designPlanProductService.createByPlanProductInfo(planProductInfoMatched, planId, username, opType);
		productMatchInfoDTO.setDesignPlanProductId(designPlanProductId);
		productMatchInfoDTO.setMatchInfo(matchInfo.toString());
		// 处理isBJWall参数
		if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, planProductInfo.getBigTypeValuekey())) {
			if(StringUtils.equals("basic_mengk", planProductInfo.getSmallTypeValuekey())) {
				productMatchInfoDTO.setWallType(WallTypeConstant.MENGKUANG);
			}else {
				productMatchInfoDTO.setWallType(WallTypeConstant.NORMAL);
			}
		}
	}

	/**
	 * 匹配卫浴
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param theSameBigTypePlanProductInfoList
	 * @param matchInfo
	 * @return
	 */
	private PlanProductInfo productListMatchBa(PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo) {
		// 参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(recommendedPlanProductInfoList)) {
			return null;
		}
		// 参数验证 ->end
		logger.debug("posName:" + planProductInfo.getPosName());
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		PlanProductInfo planProductInfoMatched = null;
		// basic_waca,basic_dyca 衣柜/定制衣柜
		if(StringUtils.equals("basic_asba", smallTypeValuekey)) {
			matchInfo.append("识别为淋浴房(弧形,砖石型..)\n");
			// 根据类别匹配
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValueKeyList(Arrays.asList(new String[] {"ahba", "asba"}));
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				matchInfo.append("没有匹配到对应产品(大小类)\n");
			}else {
				matchInfo.append("匹配到对应产品(大小类),准备匹配产品的过滤属性\n");
				// 获取过滤属性 ->start
				planProductInfo.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));
				planProductInfoMatched.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfoMatched.getProductId()));
				// 获取过滤属性 ->end
				if(Utils.isMatched(planProductInfo.getProductFilterPropList(), planProductInfoMatched.getProductFilterPropList())){
					matchInfo.append("属性匹配成功,应用此产品\n");
				}else {
					matchInfo.append("属性匹配失败,准备自行搜索(过滤属性+型号+品牌)\n");
					planProductInfoParam.setProductFilterPropList(planProductInfo.getProductFilterPropList());
					planProductInfoMatched = selectOneProductBa(planProductInfo, planProductInfoMatched, planProductInfoParam);
				}
			}
		}else if(StringUtils.equals("basic_ahba", smallTypeValuekey)) {
			matchInfo.append("识别为一字型淋浴房\n");
			// 检测推荐方案有没有淋浴房
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			/*planProductInfo.setSmallTypeValueKeyList(Arrays.asList(new String[] {"ahba", "asba"}));*/
			planProductInfoParam.setSmallTypeValuekey(planProductInfo.getSmallTypeValuekey().replace("basic_", ""));
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 删除样板房中的白膜
				matchInfo.append("没有匹配到对应产品(小类:一字型淋浴房),准备去匹配淋浴房\n");
				planProductInfoParam.setSmallTypeValuekey("asba");
				planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
				if(planProductInfoMatched == null) {
					matchInfo.append("淋浴房也没有匹配上,删除白膜");
				}else {
					matchInfo.append("一字型淋浴房匹配上淋浴房,带淋浴房的型号+品牌+本身的全铺长度去系统中搜索");
					planProductInfoMatched = selectOneProductBa(planProductInfo, planProductInfoMatched, planProductInfoParam);
				}
			}else {
				// 匹配全铺长度
				matchInfo.append("匹配到对应产品(大小类),准备匹配全铺长度\n");
				this.setBeijingMatchParams(planProductInfoMatched, planProductInfoParam, null);
				if(this.productMatchCheckMoreInfo(planProductInfoMatched, planProductInfoParam)) {
					matchInfo.append("匹配全铺长度成功,应用此产品\n");
				}else {
					// 带型号+品牌去系统中搜索
					matchInfo.append("全铺长度匹配失败,自行搜索(型号+品牌+全铺长度)\n");
					planProductInfoMatched = selectOneProductBa(planProductInfo, planProductInfoMatched, planProductInfoParam);
				}
			}
		}else if(StringUtils.equals("basic_baba", smallTypeValuekey) || StringUtils.equals("basic_edba", smallTypeValuekey)) {
			// 浴缸匹配
			matchInfo.append("识别为浴缸\n");
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValueKeyList(Arrays.asList(new String[] {"baba", "edba"}));
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				matchInfo.append("匹配失败,推荐方案中没有浴缸\n");
			}else {
				matchInfo.append("匹配成功,验证是否为同种类浴缸(嵌入式/独立式)\n");
				if(StringUtils.equals(planProductInfo.getSmallTypeValuekey().replace("basic_", ""), planProductInfoMatched.getSmallTypeValuekey())) {
					matchInfo.append("验证为同种类浴缸,应用此产品");
				}else {
					matchInfo.append("验证为不同种类浴缸,带型号+品牌去系统中搜素");
					planProductInfoParam.setSmallTypeValueKeyList(null);
					planProductInfoParam.setSmallTypeValuekey(planProductInfo.getSmallTypeValuekey().replace("basic_", ""));
					planProductInfoParam.setOrderBrandId(planProductInfoMatched.getBrandId());
					planProductInfoParam.setOrderProductModelNumber(planProductInfoMatched.getProductModelNumber());
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
				}
			}
		}else if(StringUtils.equals("basic_haba", smallTypeValuekey)) {
			// 匹配花洒(添加过滤属性匹配)(basic_haba)

			// *匹配参数设置 ->start
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			planProductInfoParam.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));
			// *匹配参数设置 ->end
			
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				matchInfo.append("匹配失败(按初始化白膜的大小类匹配),删除样板房白膜(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				matchInfo.append("匹配成功(按初始化白膜的大小类匹配)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}
		
		}else {
			// 其他小类
			planProductInfoMatched = productListMatchOther(planProductInfo, recommendedPlanProductInfoList, matchInfo);
		}
		
		return planProductInfoMatched;
	}

	/**
	 * 淋浴房搜索
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param planProductInfoMatched
	 * @param planProductInfoParam
	 * @return
	 */
	private PlanProductInfo selectOneProductBa(PlanProductInfo planProductInfo, PlanProductInfo planProductInfoMatched, PlanProductInfo planProductInfoParam) {
		// 参数验证/处理 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(planProductInfoMatched == null) {
			return null;
		}
		if(planProductInfoParam == null) {
			planProductInfoParam = new PlanProductInfo();
		}
		// 参数验证/处理 ->end
		
		planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
		planProductInfoParam.setSmallTypeValuekey(planProductInfo.getSmallTypeValuekey().replace("basic_", ""));
		planProductInfoParam.setOrderBrandId(planProductInfoMatched.getBrandId());
		planProductInfoParam.setOrderProductModelNumber(planProductInfoMatched.getProductModelNumber());
		planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
		return planProductInfoMatched;
	}

	/**
	 * 匹配柜子
	 * 
	 * @author huangsongbo
	 * @param planProductInfo 样板房柜子白膜信息
	 * @param recommendedPlanProductInfoList 推荐方案中所有柜子类产品信息
	 * @param matchInfo
	 * @return
	 */
	private PlanProductInfo productListMatchCa(PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo) {
		// 参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(recommendedPlanProductInfoList)) {
			return null;
		}
		// 参数验证 ->end
		
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		PlanProductInfo planProductInfoMatched = null;
		// basic_waca,basic_dyca 衣柜/定制衣柜
		if(StringUtils.equals("basic_waca", smallTypeValuekey) || StringUtils.equals("basic_dyca", smallTypeValuekey)) {
			matchInfo.append("识别为衣柜(匹配条件加上过滤属性(朝向))\n");
			
			// *匹配参数设置 ->start
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			// *匹配参数设置 ->end
			
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到,搜索单品
				matchInfo.append("匹配失败(初始化白膜大小类)\n");
			}else {
				matchInfo.append("匹配成功(按初始化白膜大小类)(匹配到:" + planProductInfoMatched.getProductCode() + "),准备匹配过滤属性\n");
				
				// 获取过滤属性 ->start
				planProductInfo.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));
				planProductInfoMatched.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfoMatched.getProductId()));
				// 获取过滤属性 ->end
			
				if(Utils.isMatched(planProductInfo.getProductFilterPropList(), planProductInfoMatched.getProductFilterPropList())){
					// 属性匹配成功,直接用推荐方案中的该产品
					matchInfo.append("属性匹配成功\n");
				}else {
					// 自行搜索
					matchInfo.append("属性匹配失败,带上推荐方案的衣柜的款式去系统中搜索\n");
					planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
					planProductInfoParam.setSmallTypeValuekey(Utils.baimoSmallTypeKeyToSmallTypeKey(planProductInfoMatched.getSmallTypeValuekey()));
					planProductInfoParam.setOrderStyleId(planProductInfoMatched.getStyleId());
					planProductInfoParam.setProductFilterPropList(planProductInfo.getProductFilterPropList());
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
					/*selectOneProduct方法增加过滤属性搜索条件*/
				}
			}
			return planProductInfoMatched;
		}else {
			// 其他小类
			planProductInfoMatched = productListMatchOther(planProductInfo, recommendedPlanProductInfoList, matchInfo);
		}
		
		return planProductInfoMatched;
	}

	/**
	 * 家纺匹配(主要是窗帘要匹配长高)
	 * @param planProductInfo
	 * @param recommendedPlanProductInfoList
	 * @param matchInfo
	 * @return
	 */
	private PlanProductInfo productListMatchHo(PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo) {
		// 参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(recommendedPlanProductInfoList)) {
			return null;
		}
		// 参数验证 ->end
		
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		PlanProductInfo planProductInfoMatched = null;
		// 窗帘/定制窗帘
		if(StringUtils.equals("basic_cuho", smallTypeValuekey) || StringUtils.equals("basic_dzho", smallTypeValuekey)) {
			
			// 设置匹配条件(附加属性,产品长,产品高) ->start
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekeyInit());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekeyInit());
			// 设置匹配条件(附加属性,产品长,产品高) ->end
			
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到(说明没有窗帘)
				matchInfo.append("匹配失败(按大小类),准备在系统中搜索(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				matchInfo.append("匹配成功(按大小类)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				matchInfo.append("准备验证长高是否符合条件\n");
				planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());
				this.setBeijingMatchParams(planProductInfo, planProductInfoParam, null);
				// 添加过滤属性匹配条件
				planProductInfoParam.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));
				if(this.productMatchCheckMoreInfo(planProductInfoMatched, planProductInfoParam)) {
					// 长宽也匹配
					matchInfo.append("匹配成功\n");
				}else {
					matchInfo.append("匹配失败,准备去系统中搜索\n");
					// 长宽不匹配,准备带款式去系统中搜素
					planProductInfoParam.setBigTypeValuekeyInit(null);
					planProductInfoParam.setSmallTypeValuekeyInit(null);
					planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
					planProductInfoParam.setSmallTypeValuekey(Utils.baimoSmallTypeKeyToSmallTypeKey(planProductInfo.getSmallTypeValuekey()));
					planProductInfoParam.setOrderStyleId(planProductInfoMatched.getStyleId());
					planProductInfoParam.setOrderProductModelNumber(planProductInfoMatched.getProductModelNumber());
					String splitTexturesInfoRecommended = planProductInfoMatched.getSplitTexturesChooseInfo();
					Integer productIdRecommended = planProductInfoMatched.getProductId();
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
					if(planProductInfoMatched == null) {
						matchInfo.append("在系统中也没有搜索到产品\n");
					}else {
						String splitTexturesInfo = designPlanProductService.matchSplitTexturesInfo(planProductInfoMatched.getSplitTexturesChooseInfo(), productIdRecommended, splitTexturesInfoRecommended);
						planProductInfoMatched.setSplitTexturesChooseInfo(splitTexturesInfo);
						matchInfo.append("在系统中搜索到产品");
					}
				}
			}
		}else {
			// 其他小类
			planProductInfoMatched = productListMatchOther(planProductInfo, recommendedPlanProductInfoList, matchInfo);
		}
		
		return planProductInfoMatched;
	}

	/**
	 * 匹配其他大类产品
	 * @param planProductInfo
	 * @param recommendedPlanProductInfoList
	 * @param matchInfo
	 * @return
	 */
	private PlanProductInfo productListMatchOther(PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo) {
		
		// *匹配参数设置 ->start
		PlanProductInfo planProductInfoParam = new PlanProductInfo();
		if(StringUtils.equals("basic_frel", planProductInfo.getSmallTypeValuekey())) {
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekey(Utils.baimoSmallTypeKeyToSmallTypeKey(planProductInfo.getSmallTypeValuekey()));
		}else {
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
		}
		// *匹配参数设置 ->end
		
		boolean isMatchMatched = true;
		if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, planProductInfo.getBigTypeValuekey()) && StringUtils.equals("basic_pipe", planProductInfo.getSmallTypeValuekey())) {
			isMatchMatched = false;
		}
		
		PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, isMatchMatched, checkDataFromMapEnum.defaultType);
		if(planProductInfoMatched == null && !isMatchMatched) {
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
		}
		if(planProductInfoMatched == null) {
			// 没有匹配到,搜索单品
			// 未完成
			/*planProductInfoMatched = 搜索单品*/
			matchInfo.append("匹配失败(按初始化白膜的大小类匹配),删除样板房白膜(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
		}else {
			matchInfo.append("匹配成功(按初始化白膜的大小类匹配)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
		}
		return planProductInfoMatched;
	}

	/**
	 * 匹配天花
	 * @param planProductInfo
	 * @param recommendedPlanProductInfoList
	 * @param matchInfo 
	 * @param integer 
	 * @return
	 */
	private PlanProductInfo productListMatchTianh(PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo, String smallpoxIdentify) {
		
		// *参数验证 ->start
		if(matchInfo == null) {
			return null;
		}
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		if(StringUtils.isEmpty(smallTypeValuekey)) {
			return null;
		}
		if(recommendedPlanProductInfoList == null) {
			return null;
		}
		// *参数验证 ->end
		
		// *设置匹配参数 ->start
		PlanProductInfo planProductInfoParam = new PlanProductInfo();
		
		PlanProductInfo planProductInfoMatched = null;
				
		planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
		if(StringUtils.isEmpty(planProductInfo.getRegionMark()) || StringUtils.isEmpty(planProductInfo.getMeasureCode())) {
			matchInfo.append("样板房天花白膜没有录区域标识或者尺寸编码(regionMark or measureCode)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			matchInfo.append("准备按定制规则在系统中搜索一款天花\n");
			// *搜索定制天花 ->start
			planProductInfoParam.setBmIds(planProductInfo.getProductId() + "");
			// 布局标识搜索条件(product_smallpox_identify),带自身白膜的布局标识
			planProductInfoParam.setProductSmallpoxIdentify(planProductInfo.getProductSmallpoxIdentify());
			planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
			if(planProductInfoMatched == null) {
				matchInfo.append("没有搜索到产品\n");
			}else {
				matchInfo.append("搜索到产品\n");
			}
			// *搜索定制天花 ->start
			return planProductInfoMatched;
		}
		// *设置匹配参数 ->end
		
		planProductInfoParam.setRegionMark(planProductInfo.getRegionMark());
		planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
		
		// 区域标识匹配
		if(planProductInfoMatched != null) {
			// 匹配尺寸编码+布局标识
			PlanProductInfo planProductInfoMatchParam = new PlanProductInfo();
			planProductInfoMatchParam.setMeasureCode(planProductInfo.getMeasureCode());
			planProductInfoMatchParam.setProductSmallpoxIdentifyList(Utils.getIdentifyList(smallpoxIdentify));
			if(this.productMatchCheckMoreInfo(planProductInfoMatched, planProductInfoMatchParam)) {
			/*if(StringUtils.equals(planProductInfo.getMeasureCode(), planProductInfoMatched.getMeasureCode())*/
					// 样板房天花白膜有布局标识并且布局标识和推荐方案中的一样
					/*&& (!(smallpoxIdentify != null && smallpoxIdentify.intValue() != 0)*/
					/*&& (StringUtils.equals("0", smallpoxIdentify)*/
					/*|| smallpoxIdentify.intValue() == planProductInfoMatched.getProductSmallpoxIdentify())*/
					/*|| StringUtils.equals(smallpoxIdentify, planProductInfoMatched.getProductSmallpoxIdentify()))
					) {*/
				// 尺寸编码匹配->直接用推荐方案中的该产品
				matchInfo.append("样板房天花白膜匹配上了推荐方案的天花(regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				return planProductInfoMatched;
			}else {
				// 尺寸编码没有匹配上->带自身的尺寸编码+自身的布局标识+推荐方案中的产品款式搜索
				matchInfo.append("样板房天花白膜匹配上了推荐方案的天花,但是尺寸编码不一样,所以带款式+尺寸编码+搜索系统中的天花(regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				// 加上天花布局标识匹配/搜索条件
				planProductInfoMatched = selectOneProductTianh(planProductInfo, matchInfo, planProductInfoParam, planProductInfoMatched, smallpoxIdentify);
			}
		}else {
			// 区域标识没有匹配上->匹配备用的区域标识(过道匹配其他过道,没有过道匹配主天花)
			// 主天花的区域编码是1开头(10,11),过道优先匹配其他过道,再玄关,再主天花
			// 玄关优先匹配玄关,再过道,再主天花
			// 例如过道的区域标识是21,则推荐方案中区域标识2开头的天花
			planProductInfoParam.setRegionMark(null);
			planProductInfoParam.setRegionMarkLikeStart(planProductInfo.getRegionMark().substring(0, 1));
			// 优先匹配同类型天花(过道匹配过道,玄关匹配玄关)
			planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 如果是主天花没有匹配上,不应该出现这种情况(因为推荐方案肯定会有一个主天花)
				if(planProductInfo.getRegionMark().startsWith("1")) {
					matchInfo.append("主天花都没有匹配上,检查推荐方案的主天花是否录了区域标识(regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				}else {
					// 匹配区域标识非1开头的天花(eg:过道天花匹配不到过道天花后,优先匹配玄关天花)
					planProductInfoParam.setRegionMarkLikeStart(null);
					planProductInfoParam.setRegionMarkNotLikeStart("1");
					planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
					if(planProductInfoMatched == null) {
						// 匹配主天花
						planProductInfoParam.setRegionMarkNotLikeStart(null);
						planProductInfoParam.setRegionMarkLikeStart("1");
						planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
						if(planProductInfoMatched == null) {
							matchInfo.append("推荐方案没有发现主天花,请检查推荐方案主天花的区域标识(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
						}else {
							matchInfo.append("非主天花匹配到推荐方案的主天花,带款式+尺寸编码搜索系统中的天花(regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
							// 加上天花布局标识匹配/搜索条件
							planProductInfoMatched = selectOneProductTianh(planProductInfo, matchInfo, planProductInfoParam, planProductInfoMatched, null);
						}
					}else {
						// 玄关匹配到了过道天花
						if(StringUtils.isNotEmpty(planProductInfo.getMeasureCode()) && StringUtils.equals(planProductInfo.getMeasureCode(), planProductInfoMatched.getMeasureCode())) {
							// 尺寸编码匹配->直接用推荐方案中的该产品
							matchInfo.append("样板房天花白膜匹配上了推荐方案的天花(不同类型(非主天花))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
							return planProductInfoMatched;
						}else {
							// 尺寸编码没有匹配上->带自身的尺寸编码+推荐方案中的产品款式搜索
							matchInfo.append("样板房天花白膜匹配上了推荐方案的天花(不同类型(非主天花)),但是尺寸编码不一样,所以带款式+尺寸编码搜索系统中的天花(regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
							planProductInfoMatched = selectOneProductTianh(planProductInfo, matchInfo, planProductInfoParam, planProductInfoMatched, null);
						}
						return planProductInfoMatched;
					}
				}
			}else {
				if(StringUtils.equals(planProductInfo.getMeasureCode(), planProductInfoMatched.getMeasureCode())
						/*&& (planProductInfo.getProductSmallpoxIdentify() != null && planProductInfo.getProductSmallpoxIdentify().intValue() != 0
								&& planProductInfo.getProductSmallpoxIdentify().intValue() == planProductInfoMatched.getProductSmallpoxIdentify())*/
						) {
					// 尺寸编码and布局标识匹配->直接用推荐方案中的该产品
					matchInfo.append("样板房天花白膜匹配上了推荐方案的天花(同类型,不同regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					return planProductInfoMatched;
				}else {
					// 尺寸编码and布局标识没有匹配上->带自身的尺寸编码+推荐方案中的产品款式搜索
					matchInfo.append("样板房天花白膜匹配上了推荐方案的天花(同类型,不同regionMark),但是尺寸编码不一样,所以带款式+尺寸编码搜索系统中的天花(regionMark)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					planProductInfoMatched = selectOneProductTianh(planProductInfo, matchInfo, planProductInfoParam, planProductInfoMatched, null);
				}
				return planProductInfoMatched;
			}
		}
		
		return planProductInfoMatched;
	}

	/**
	 * 天花专用搜索逻辑 
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param matchInfo
	 * @param planProductInfoParam
	 * @param planProductInfoMatched
	 * @param smallpoxIdentify 天花布局标识
	 * @return
	 */
	private PlanProductInfo selectOneProductTianh(PlanProductInfo planProductInfo, StringBuffer matchInfo,
			PlanProductInfo planProductInfoParam, PlanProductInfo planProductInfoMatched, String smallpoxIdentify) {
		planProductInfoParam.setRegionMark(null);
		planProductInfoParam.setMeasureCode(planProductInfo.getMeasureCode());
		planProductInfoParam.setOrderStyleId(planProductInfoMatched.getStyleId());
		// 天花区域标识搜索条件
		/*if(smallpoxIdentify != null && smallpoxIdentify != 0) {*/
		/*if(StringUtils.equals("0", smallpoxIdentify)) {
			planProductInfoParam.setProductSmallpoxIdentify(smallpoxIdentify);
		}*/
		planProductInfoParam.setProductSmallpoxIdentifyList(Utils.getIdentifyList(smallpoxIdentify));
		// 布局标识排序:如果样板房布局标识是1,优先搜出布局标识为1的天花
		planProductInfoParam.setOrderProductSmallpoxIdentify(smallpoxIdentify);
		planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
		if(planProductInfoMatched == null) {
			matchInfo.append("在系统中也没有搜索到产品,"
					+ "搜索条件:bigTypeValuekey:" + planProductInfoParam.getBigTypeValuekey() + ";measureCode:" + planProductInfoParam.getMeasureCode()
					 + ";styleId:" + planProductInfoParam.getStyleId()
					+ "(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			// *去掉styleId搜索条件再搜索一次 ->start
			matchInfo.append("去掉风格搜索条件再搜素一次\n");
			planProductInfoParam.setStyleId(null);
			planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
			if(planProductInfoMatched == null) {
				matchInfo.append("没有搜索到产品,准备搜索定制天花\n");
				planProductInfoParam.setMeasureCode(null);
				planProductInfoParam.setBmIds(planProductInfo.getProductId() + "");
				// 用定制产品的方法搜索(bm_ids = productId),搜索定制天花
				planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
				if(planProductInfoMatched == null) {
					matchInfo.append("没有搜索到产品\n");
				}else {
					matchInfo.append("搜索到产品\n");
				}
			}else {
				matchInfo.append("搜索到产品\n");
			}
			// *去掉styleId搜索条件再搜索一次 ->end
		}else {
			matchInfo.append("在系统中搜索到产品\n");
		}
		return planProductInfoMatched;
	}

	/**
	 * 匹配完其他的单品之后,再特殊处理背景墙(由于背景墙匹配规则和其他单品不一样,所以单独拎出)
	 * 
	 * @author huangsongbo
	 * @param productMatchInfoDTOList
	 * @param beijingPlanProductInfoList
	 * @param username 
	 * @param planId 
	 */
	private void productListMatchBeijingQiangm(
			List<PlanProductInfo> beijingPlanProductInfoList, 
			List<PlanProductInfo> recommendedPlanProductInfoList,
			List<ProductMatchInfoDTO> productMatchInfoDTOList, Integer planId, String username, Integer opType) {
		// 参数验证 ->start
		if(Lists.isEmpty(beijingPlanProductInfoList)) {
			return;
		}
		if(planId == null) {
			return;
		}
		if(StringUtils.isEmpty(username)) {
			return;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证 ->end
		
		// 存结构背景墙的匹配信息
		Map<String, PlanProductInfo> structureBeijingMatchInfo = new HashMap<String, ProductListByTypeInfo.PlanProductInfo>();
		
		// 结构背景墙的附墙,留在最后匹配 ->start
		List<PlanProductInfo> structureBeijingDeputyPlanProductInfoList = new ArrayList<PlanProductInfo>();
		for(int index = 0; index < beijingPlanProductInfoList.size(); index ++) {
			PlanProductInfo planProductInfo = beijingPlanProductInfoList.get(index);
			if(planProductInfo.getGroupType() != null && 1 == planProductInfo.getGroupType().intValue() 
					&& (planProductInfo.getIsMainStructureProduct() == null || 1 != planProductInfo.getIsMainStructureProduct().intValue())) {
				structureBeijingDeputyPlanProductInfoList.add(planProductInfo);
				beijingPlanProductInfoList.remove(index);
				index --;
			}
		}
		// 结构背景墙的附墙,留在最后匹配 ->end
		
		// 先匹配小类,匹配剩下的再按优先级(背景墙小类有一个小类优先级)匹配
		// 现匹配小类
		for(int index = 0; index < beijingPlanProductInfoList.size(); index ++) {
			PlanProductInfo planProductInfo = beijingPlanProductInfoList.get(index);
			logger.debug("------posName:" + planProductInfo.getPosName());
			StringBuffer matchInfo = new StringBuffer();
			matchInfo.append("正在匹配背景墙(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ";full_pave_length:" + planProductInfo.getFullPaveLength() + ")\n");
			
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			
			// *设置匹配附加条件(wallType) ->start
			if(StringUtils.isEmpty(planProductInfo.getWallType())) {
				matchInfo.append("该背景墙没有录墙体类型,准备采用墙体优先级匹配方式\n");
				planProductInfo.setMatchInfo(matchInfo.toString());
				continue;
			}
			planProductInfoParam.setWallType(planProductInfo.getWallType());
			planProductInfoParam.setBeijing(true);
			// 设置背景墙长宽过滤(范围)
			/*this.setBeijingMatchParams(planProductInfo, planProductInfoParam);*/
			// *设置匹配附加条件(wallType) ->end
			
			PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, false, checkDataFromMapEnum.defaultType);
			
			if(planProductInfoMatched == null) {
				matchInfo.append("背景墙匹配失败(按墙体类别wall_type)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				planProductInfo.setMatchInfo(matchInfo.toString());
				continue;
			}else {
				planProductInfoMatched.setMatched(true);
				matchInfo.append("背景墙类型匹配成功,准备匹配背景墙长高(匹配到" + planProductInfoMatched.getProductCode() + ";styleId = " + planProductInfoMatched.getStyleId() + ")\n");
				// 匹配到了
				// 验证背景墙长高
				this.setBeijingMatchParams(planProductInfo, planProductInfoParam, null);
				planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());
				if(this.productMatchCheckMoreInfo(planProductInfoMatched, planProductInfoParam)) {
					// 背景墙长高也符合->匹配成功
					matchInfo.append("背景墙长高匹配成功,背景墙匹配成功\n");
				}else {
					// 没有匹配上,带背景墙的款式+wallType+长宽限制去搜索
					matchInfo.append("背景墙长高匹配失败,款式+长高去系统中搜索\n");
					/*planProductInfoParam.setStyleId(planProductInfoMatched.getStyleId());*/
					planProductInfoParam.setOrderStyleId(planProductInfoMatched.getStyleId());
					planProductInfoParam.setOrderProductModelNumber(planProductInfoMatched.getProductModelNumber());
					/*planProductInfoParam.setSmallTypeValuekey(planProductInfoMatched.getSmallTypeValuekey());*/
					planProductInfoParam.setWallType(null);
					planProductInfoParam.setSmallTypeValueList(this.getBeijingValueList());
					planProductInfoParam.setOrderSmallTypeValueKey(planProductInfoMatched.getSmallTypeValuekey());
					String splitTexturesInfoRecommended = planProductInfoMatched.getSplitTexturesChooseInfo();
					Integer productIdRecommended = planProductInfoMatched.getProductId();
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
					matchInfo.append(planProductInfoParam.getMatchInfo());
					if(planProductInfoMatched == null) {
						matchInfo.append("没有搜索到对应产品,删除背景墙\n");
						ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
						productMatchInfoDTO.setDelPosName(planProductInfo.getPosName());
						productMatchInfoDTO.setWallType(WallTypeConstant.BEIJING);
						productMatchInfoDTO.setMatchInfo(matchInfo.toString());
						productMatchInfoDTOList.add(productMatchInfoDTO);
						beijingPlanProductInfoList.remove(index);
						index --;
					}else {
						matchInfo.append("搜索到对应产品\n");
						// 匹配材质
						String splitTexturesInfo = designPlanProductService.matchSplitTexturesInfo(planProductInfoMatched.getSplitTexturesChooseInfo(), productIdRecommended, splitTexturesInfoRecommended);
						planProductInfoMatched.setSplitTexturesChooseInfo(splitTexturesInfo);
					}
				}
			}
			
			if(planProductInfoMatched != null) {
				
				// 记录结构背景墙的匹配 ->start
				if(StringUtils.isNotEmpty(planProductInfo.getPlanGroupId()) 
						&& planProductInfo.getGroupType() != null 
						&& planProductInfo.getGroupType() == 1) {
					structureBeijingMatchInfo.put(planProductInfo.getPlanGroupId(), planProductInfoMatched);
				}
				// 记录结构背景墙的匹配 ->end
				
				ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
				productMatchInfoDTO.setWallType(WallTypeConstant.BEIJING);
				productMatchInfoDTO.setDelPosName(planProductInfo.getPosName());
				productMatchInfoDTO.setProductCode(planProductInfoMatched.getProductCode());
				planProductInfoMatched.setInitProductId(planProductInfo.getInitProductId());
				planProductInfoMatched.setGroupType(planProductInfo.getGroupType());
				planProductInfoMatched.setPlanGroupId(planProductInfo.getPlanGroupId());
				planProductInfoMatched.setGroupOrStructureId(planProductInfo.getGroupOrStructureId());
				planProductInfoMatched.setIsMainStructureProduct(planProductInfo.getIsMainStructureProduct());
				planProductInfoMatched.setIsGroupReplaceWay(planProductInfo.getIsGroupReplaceWay());
				planProductInfoMatched.setProductIndex(planProductInfo.getProductIndex());
				Integer designPlanProductId = designPlanProductService.createByPlanProductInfo(planProductInfoMatched, planId, username, opType);
				productMatchInfoDTO.setDesignPlanProductId(designPlanProductId);
				productMatchInfoDTO.setMatchInfo(matchInfo.toString());
				productMatchInfoDTO.setBigTypeValuekey(planProductInfoMatched.getBigTypeValuekey());
				productMatchInfoDTO.setSmallTypeValuekey(planProductInfoMatched.getSmallTypeValuekey());
				productMatchInfoDTOList.add(productMatchInfoDTO);
				beijingPlanProductInfoList.remove(index);
				index --;
			}else {
				planProductInfo.setMatchInfo(matchInfo.toString());
			}
			
		}
		
		// *背景墙优先级匹配 ->start
		for(int index = 0; index < beijingPlanProductInfoList.size(); index ++) {
			PlanProductInfo planProductInfo = beijingPlanProductInfoList.get(index);
			logger.debug("------posName:" + planProductInfo.getPosName());
			StringBuffer matchInfo = new StringBuffer(planProductInfo.getMatchInfo() == null ? "" : planProductInfo.getMatchInfo());
			
			PlanProductInfo planProductInfoParam = new PlanProductInfo();
			planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setBeijing(true);
			
			matchInfo.append("背景墙按正在按优先级匹配\n");
			PlanProductInfo planProductInfoMatched = this.productListMatchBeijingQiangmScore(planProductInfo, recommendedPlanProductInfoList);
			if(planProductInfoMatched == null) {
				matchInfo.append("背景墙按优先级匹配失败\n");
			}else {
				// 长宽匹配
				matchInfo.append("背景墙按优先级匹配成功,验证长高是否匹配(匹配到" + planProductInfoMatched.getProductCode() + ";styleId = " + planProductInfoMatched.getStyleId() + ")\n");
				planProductInfoMatched.setMatched(true);
				this.setBeijingMatchParams(planProductInfo, planProductInfoParam, null);
				planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());
				if(this.productMatchCheckMoreInfo(planProductInfoMatched, planProductInfoParam)) {
					// 背景墙长高也符合->匹配成功
					matchInfo.append("背景墙长高匹配成功,背景墙匹配成功\n");
				}else {
					// 没有匹配上,带背景墙的款式+wallType+长宽限制去搜索
					matchInfo.append("背景墙长高匹配失败,准备带背景墙类型+款式+长高去系统中搜索\n");
					/*planProductInfoParam.setStyleId(planProductInfoMatched.getStyleId());*/
					planProductInfoParam.setOrderStyleId(planProductInfoMatched.getStyleId());
					planProductInfoParam.setOrderProductModelNumber(planProductInfoMatched.getProductModelNumber());
					planProductInfoParam.setWallType(null);
					planProductInfoParam.setSmallTypeValueList(this.getBeijingValueList());
					planProductInfoParam.setOrderSmallTypeValueKey(planProductInfoMatched.getSmallTypeValuekey());
					String splitTexturesInfoRecommended = planProductInfoMatched.getSplitTexturesChooseInfo();
					Integer productIdRecommended = planProductInfoMatched.getProductId();
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
					matchInfo.append(planProductInfoParam.getMatchInfo());
					if(planProductInfoMatched == null) {
						matchInfo.append("没有搜索到对应产品\n");
					}else {
						matchInfo.append("搜索到对应产品\n");
						// 匹配材质
						String splitTexturesInfo = designPlanProductService.matchSplitTexturesInfo(planProductInfoMatched.getSplitTexturesChooseInfo(), productIdRecommended, splitTexturesInfoRecommended);
						planProductInfoMatched.setSplitTexturesChooseInfo(splitTexturesInfo);
					}
				}
			}
			
			if(planProductInfoMatched != null) {
				
				// 记录结构背景墙的匹配 ->start
				if(StringUtils.isNotEmpty(planProductInfo.getPlanGroupId()) 
						&& planProductInfo.getGroupType() != null 
						&& planProductInfo.getGroupType() == 1) {
					structureBeijingMatchInfo.put(planProductInfo.getPlanGroupId(), planProductInfoMatched);
				}
				// 记录结构背景墙的匹配 ->end
				
				ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
				productMatchInfoDTO.setWallType(WallTypeConstant.BEIJING);
				productMatchInfoDTO.setDelPosName(planProductInfo.getPosName());
				productMatchInfoDTO.setProductCode(planProductInfoMatched.getProductCode());
				planProductInfoMatched.setInitProductId(planProductInfo.getInitProductId());
				planProductInfoMatched.setGroupType(planProductInfo.getGroupType());
				planProductInfoMatched.setPlanGroupId(planProductInfo.getPlanGroupId());
				planProductInfoMatched.setGroupOrStructureId(planProductInfo.getGroupOrStructureId());
				planProductInfoMatched.setIsMainStructureProduct(planProductInfo.getIsMainStructureProduct());
				planProductInfoMatched.setIsGroupReplaceWay(planProductInfo.getIsGroupReplaceWay());
				planProductInfoMatched.setProductIndex(planProductInfo.getProductIndex());
				Integer designPlanProductId = designPlanProductService.createByPlanProductInfo(planProductInfoMatched, planId, username, opType);
				productMatchInfoDTO.setDesignPlanProductId(designPlanProductId);
				productMatchInfoDTO.setMatchInfo(matchInfo.toString());
				productMatchInfoDTO.setBigTypeValuekey(planProductInfoMatched.getBigTypeValuekey());
				productMatchInfoDTO.setSmallTypeValuekey(planProductInfoMatched.getSmallTypeValuekey());
				productMatchInfoDTOList.add(productMatchInfoDTO);
				beijingPlanProductInfoList.remove(index);
				index --;
				continue;
			}else {
				planProductInfo.setMatchInfo(matchInfo.toString());
			}
			
		}
		// *背景墙优先级匹配 ->end
		
		// 匹配剩下的结构结构背景墙(非主墙),按主墙的款式匹配 ->start
		for(int index = 0; index < structureBeijingDeputyPlanProductInfoList.size(); index ++) {
			PlanProductInfo planProductInfo = structureBeijingDeputyPlanProductInfoList.get(index);
			logger.debug("------posName:" + planProductInfo.getPosName());
			PlanProductInfo planProductInfoStructure = structureBeijingMatchInfo.get(planProductInfo.getPlanGroupId());
			if(planProductInfoStructure != null) {
				PlanProductInfo planProductInfoParam = new PlanProductInfo();
				planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
				planProductInfoParam.setBeijing(true);
				this.setBeijingMatchParams(planProductInfo, planProductInfoParam, null);
				planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());
				planProductInfoParam.setSmallTypeValueList(this.getBeijingValueList());
				/*planProductInfoParam.setStyleId(planProductInfoStructure.getStyleId());*/
				planProductInfoParam.setOrderStyleId(planProductInfoStructure.getStyleId());
				planProductInfoParam.setOrderProductModelNumber(planProductInfoStructure.getProductModelNumber());
				planProductInfoParam.setOrderSmallTypeValueKey(planProductInfoStructure.getSmallTypeValuekey());
				PlanProductInfo planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
				if(planProductInfoMatched == null) {
					planProductInfoParam.setStyleId(null);
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
				}
				if(planProductInfoMatched != null) {
					ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
					productMatchInfoDTO.setWallType(WallTypeConstant.BEIJING);
					productMatchInfoDTO.setDelPosName(planProductInfo.getPosName());
					productMatchInfoDTO.setProductCode(planProductInfoMatched.getProductCode());
					planProductInfoMatched.setInitProductId(planProductInfo.getInitProductId());
					planProductInfoMatched.setGroupType(planProductInfo.getGroupType());
					planProductInfoMatched.setPlanGroupId(planProductInfo.getPlanGroupId());
					planProductInfoMatched.setGroupOrStructureId(planProductInfo.getGroupOrStructureId());
					planProductInfoMatched.setIsMainStructureProduct(planProductInfo.getIsMainStructureProduct());
					planProductInfoMatched.setIsGroupReplaceWay(planProductInfo.getIsGroupReplaceWay());
					planProductInfoMatched.setProductIndex(planProductInfo.getProductIndex());
					Integer designPlanProductId = designPlanProductService.createByPlanProductInfo(planProductInfoMatched, planId, username, opType);
					productMatchInfoDTO.setDesignPlanProductId(designPlanProductId);
					productMatchInfoDTO.setBigTypeValuekey(planProductInfoMatched.getBigTypeValuekey());
					productMatchInfoDTO.setSmallTypeValuekey(planProductInfoMatched.getSmallTypeValuekey());
					productMatchInfoDTOList.add(productMatchInfoDTO);
					continue;
				}else {
					// 没有匹配到,删除背景墙
					beijingPlanProductInfoList.add(planProductInfo);
				}
			}else {
				// 主墙没有匹配到背景墙,则附墙也删除
				beijingPlanProductInfoList.add(planProductInfo);
			}
		}
		// 匹配剩下的结构结构背景墙(非主墙),按主墙的款式匹配 ->end
		
		// *处理剩下没有匹配的背景墙(删除,只设置delPosName) ->start
		for(PlanProductInfo planProductInfo : beijingPlanProductInfoList) {
			ProductMatchInfoDTO productMatchInfoDTO = new BedroomProductMatchDTO().new ProductMatchInfoDTO();
			productMatchInfoDTO.setDelPosName(planProductInfo.getPosName());
			productMatchInfoDTO.setWallType(WallTypeConstant.BEIJING);
			productMatchInfoDTO.setMatchInfo(planProductInfo.getMatchInfo());
			productMatchInfoDTOList.add(productMatchInfoDTO);
		}
		// *处理剩下没有匹配的背景墙(删除,只设置delPosName) ->end
	}

	/**
	 * 得到背景墙的value
	 * @return
	 */
	private List<Integer> getBeijingValueList() {
		if(beijingValueList == null) {
			if(Lists.isEmpty(beijingValuekeyList)) {
				return null;
			}else {
				beijingValueList = sysDictionaryService.getValueByTypeAndValueKeylist(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, beijingValuekeyList);
				if(Lists.isEmpty(beijingValueList)) {
					beijingValueList = null;
				}else {
					
				}
			}
		}else {
			
		}
		return beijingValueList;
	}

	/**
	 * 背景墙优先级匹配
	 * @param planProductInfo
	 * @param beijingPlanProductInfoList
	 * @return
	 */
	private PlanProductInfo productListMatchBeijingQiangmScore(PlanProductInfo planProductInfo,
			List<PlanProductInfo> beijingPlanProductInfoList) {
		
		// 参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(beijingPlanProductInfoList)) {
			return null;
		}
		// 参数验证 ->end
		
		PlanProductInfo planProductInfoMatched = null;
		int score = -99;
		for(PlanProductInfo planProductInfoItem : beijingPlanProductInfoList) {
			int scoreItem = planProductInfoItem.getWallTypeScore() == null ? -1 : planProductInfoItem.getWallTypeScore();
			if(planProductInfoItem.isMatched()) {
				scoreItem = -99;
			}
			if(score < scoreItem) {
				score = scoreItem;
				planProductInfoMatched = planProductInfoItem;
			}
		}
		//标识背景墙已全部被匹配过
		if(score == -99) {
			planProductInfoMatched = null;
		}
		return planProductInfoMatched;
	}

	/**
	 * 匹配墙面(除了背景墙)
	 * @param planProductInfo
	 * @param recommendedPlanProductInfoList
	 * @param matchInfo 
	 * @return
	 */
	private PlanProductInfo productListMatchQiangm(
			PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo
			) {
		
		// *参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		if(Lists.isEmpty(recommendedPlanProductInfoList)) {
			return null;
		}
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		if(StringUtils.isEmpty(smallTypeValuekey)) {
			return null;
		}
		// *参数验证 ->end
		
		// 匹配条件
		PlanProductInfo planProductInfoParam = new PlanProductInfo();
		
		if((StringUtils.equals("basic_mengk", smallTypeValuekey) || StringUtils.equals("basic_chuangk", smallTypeValuekey))) {
			// 特殊处理门框/窗框(小类/长高)
			// 设置匹配条件(附加属性,产品长,产品高)
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			/*planProductInfoParam.setProductLength(planProductInfo.getProductLength());
			planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());*/
			
			PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到
				matchInfo.append("匹配失败(大小类)删除门框.窗框\n");
			}else {
				matchInfo.append("大小类匹配成功,然后验证窗框.门框的长高\n");
				planProductInfoParam.setProductLength(planProductInfo.getProductLength());
				planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());
				if(this.productMatchCheckMoreInfo(planProductInfoMatched, planProductInfoParam)) {
					matchInfo.append("窗框.门框的长高也匹配成功,应用此产品(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				}else {
					// 自行搜索
					planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
					if(planProductInfoMatched == null) {
						planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
						planProductInfoParam.setSmallTypeValuekey(Utils.baimoSmallTypeKeyToSmallTypeKey(planProductInfo.getSmallTypeValuekey()));
						matchInfo.append("在系统中也没有搜索到产品,"
								+ "搜索条件:bigTypeValuekey:" + planProductInfoParam.getBigTypeValuekey() + ";smallTypeValuekey:" + planProductInfoParam.getSmallTypeValuekey()
								+ ";productLength:" + planProductInfoParam.getProductLength() + ";productHeight:" + planProductInfoParam.getProductHeight()
								+ "(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					}else {
						matchInfo.append("在系统中搜索到产品");
					}
				}
			}
			return planProductInfoMatched;
		}else {
			// 设置匹配条件
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			
			PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到
				matchInfo.append("匹配失败(按初始化白膜大小类)(" + planProductInfo.getSmallTypeValuekeyInit() + "))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				matchInfo.append("匹配成功(按初始化白膜大小类)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}
			return planProductInfoMatched;
		}
	}

	/**
	 * 设置背景墙长高范围匹配条件
	 * 
	 * @author huangsongbo
	 * @param planProductInfoParam
	 * @param planProductInfoParam 
	 * @param range +-范围
	 */
	private void setBeijingMatchParams(PlanProductInfo planProductInfo, PlanProductInfo planProductInfoParam, Integer range) {
		// 参数验证/处理 ->start
		if(planProductInfo == null) {
			return;
		}
		if(planProductInfoParam == null) {
			return;
		}
		if(range == null) {
			range = beijingRange;
		}
		// 参数验证/处理 ->end
		
		int fullPaveLength = 0;
		try {
			fullPaveLength = planProductInfo.getFullPaveLength() == null ? 0 : Integer.parseInt(planProductInfo.getFullPaveLength());
		}catch (Exception e) {
			fullPaveLength = 0;
		}
		
		if(fullPaveLength == 0) {
			try {
				fullPaveLength = Integer.parseInt(planProductInfo.getProductLength());
			}catch (Exception e) {
				fullPaveLength = 0;
			}
		}
		
		if(fullPaveLength != 0) {
			Integer productLengthStart = fullPaveLength - range;
			Integer productLengthEnd = fullPaveLength + range;
			if(StringUtils.equals("basic_cuho", planProductInfo.getSmallTypeValuekey()) || StringUtils.equals("basic_dzho", planProductInfo.getSmallTypeValuekey())) {
				if(fullPaveLength > 300) {
					productLengthStart -= 10;
					productLengthEnd += 10;
				}
			}
			planProductInfoParam.setProductLengthStart(productLengthStart);
			planProductInfoParam.setProductLengthEnd(productLengthEnd);
		}
		
	}

	/**
	 * 匹配门
	 * @param planProductInfo
	 * @param matchInfo 
	 * @param theSameBigTypePlanProductInfoList
	 * @return
	 */
	private PlanProductInfo productListMatchMeng(
			PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo
			) {
		// 设置匹配条件(附加属性,产品长,产品高)
		PlanProductInfo planProductInfoParam = new PlanProductInfo();
		planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
		planProductInfoParam.setSmallTypeValuekey(Utils.baimoSmallTypeKeyToSmallTypeKey(planProductInfo.getSmallTypeValuekey()));
		planProductInfoParam.setProductLength(planProductInfo.getProductLength());
		planProductInfoParam.setProductHeight(planProductInfo.getProductHeight());
		
		PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
		if(planProductInfoMatched == null) {
			// 没有匹配到
			matchInfo.append("匹配失败(按大小类+产品长高),准备在系统中搜索(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			// 去系统中搜索
			planProductInfoMatched = this.selectOneProduct(planProductInfoParam);
			if(planProductInfoMatched == null) {
				matchInfo.append("在系统中也没有搜索到产品,"
						+ "搜索条件:bigTypeValuekey:" + planProductInfoParam.getBigTypeValuekey() + ";smallTypeValuekey:" + planProductInfoParam.getSmallTypeValuekey()
						+ ";productLength:" + planProductInfoParam.getProductLength() + ";productHeight:" + planProductInfoParam.getProductHeight()
						+ "(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				matchInfo.append("在系统中搜索到产品");
			}
		}else {
			matchInfo.append("匹配成功(按大小类+产品长高)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
		}
		return planProductInfoMatched;
	}

	/**
	 * 匹配dim类型单品
	 * 匹配不到,可能去系统中搜索
	 * 
	 * @author huangsongbo
	 * @param PlanProductInfoList
	 * @param smallTypeValuekey
	 * @param planProductInfo
	 * @param matchInfo 
	 * @return
	 */
	private PlanProductInfo productListMatchDim(
			PlanProductInfo planProductInfo,
			List<PlanProductInfo> recommendedPlanProductInfoList, StringBuffer matchInfo
			) {
		
		// *参数验证 ->start
		String smallTypeValuekey = planProductInfo.getSmallTypeValuekey();
		if(StringUtils.isEmpty(smallTypeValuekey)) {
			return null;
		}
		if(recommendedPlanProductInfoList == null) {
			return null;
		}
		// *参数验证 ->end
		
		// 地面的话,只要匹配类别基本属性,不需要匹配长宽高等附加属性
		PlanProductInfo planProductInfoParam = new PlanProductInfo();
		planProductInfoParam.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
		if(StringUtils.equals("basic_dimmk", smallTypeValuekey)) {
			
			// *匹配参数设置 ->start
			// 门槛石匹配:样板房的白膜小类与推荐方案的初始化白膜小类
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			// *匹配参数设置 ->end
			
			PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到
				matchInfo.append("门槛石匹配失败(推荐方案中没有门槛石)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				matchInfo.append("门槛石匹配成功(匹配上了推荐方案中的门槛石)(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}
			return planProductInfoMatched;
		}else if(StringUtils.equals("basic_dimct", smallTypeValuekey)) {
			// *匹配参数设置 ->start
			// 窗台石匹配:样板房的白膜小类与推荐方案的初始化白膜小类
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekeyInit());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekeyInit());
			// *匹配参数设置 ->end
			
			// 特殊处理窗台石(小类/备用小类匹配)
			PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到窗台石,就去匹配门槛石
				// *匹配参数设置 ->start
				planProductInfoParam.setSmallTypeValuekeyInit("basic_dimmk");
				// *匹配参数设置 ->end
				
				planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			}else {
				matchInfo.append("窗台石(basic_dimct)匹配成功(匹配上推荐方案中的窗台石))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				return planProductInfoMatched;
			}
			if(planProductInfoMatched == null) {
				// 没有匹配到
				matchInfo.append("窗台石(basic_dimct)匹配失败(未在推荐方案中找到窗台石/门槛石))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}else {
				matchInfo.append("窗台石(basic_dimct)匹配成功(匹配上推荐方案中的门槛石))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}
			return planProductInfoMatched;
		}else {
			
			// *匹配参数设置 ->start
			planProductInfoParam.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfoParam.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			// *匹配参数设置 ->end
			
			PlanProductInfo planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
			if(planProductInfoMatched == null) {
				// 没有匹配到
				matchInfo.append("匹配失败(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
				// 主地面备用匹配逻辑
				if(StringUtils.equals("basic_dim", planProductInfoParam.getSmallTypeValuekeyInit())) {
					matchInfo.append("basic_dim再去匹配推荐方案中的basic_dimz\n");
					// 再去匹配"basic_dimz"
					planProductInfoParam.setSmallTypeValuekeyInit("basic_dimz");
					
					planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
					if(planProductInfoMatched == null) {
						// 没有匹配到
						matchInfo.append("匹配失败(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					}else {
						matchInfo.append("匹配成功(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					}
				}
				// 主地面结构中间块地面备用匹配逻辑
				else if(StringUtils.equals("basic_dimz", planProductInfoParam.getSmallTypeValuekeyInit())) {
					matchInfo.append("basic_dimz再去匹配推荐方案中的basic_dim\n");
					// 再去匹配"basic_dimz"
					planProductInfoParam.setSmallTypeValuekeyInit("basic_dim");
					
					planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
					if(planProductInfoMatched == null) {
						// 没有匹配到
						matchInfo.append("匹配失败(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					}else {
						matchInfo.append("匹配成功(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					}
				}
				// 过道地面备用匹配逻辑
				else if(StringUtils.equals("basic_dimgd", planProductInfoParam.getSmallTypeValuekeyInit())) {
					matchInfo.append("basic_dimgd再去匹配推荐方案中的basic_dimz\n");
					// 再去匹配"basic_dimz"
					planProductInfoParam.setSmallTypeValuekeyInit("basic_dimz");
					planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
					if(planProductInfoMatched == null) {
						// 没有匹配到
						matchInfo.append("匹配失败(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
						// 再去匹配basic_dim
						matchInfo.append("basic_dimgd再去匹配推荐方案中的basic_dim\n");
						planProductInfoParam.setSmallTypeValuekeyInit("basic_dim");
						planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
						if(planProductInfoMatched == null) {
							// 没有匹配到
							matchInfo.append("匹配失败(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
						}else {
							matchInfo.append("匹配成功(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
						}
					}else {
						matchInfo.append("匹配成功(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
					}
				}
				// 阳台地面备用匹配逻辑(阳台地面>主地面>结构主地面)
				else if(StringUtils.equals("basic_dimyt", planProductInfoParam.getSmallTypeValuekeyInit())) {
					matchInfo.append("阳台地面匹配备用小类:主地面(dim)\n");
					planProductInfoParam.setSmallTypeValuekeyInit("basic_dim");
					planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
					if(planProductInfoMatched == null) {
						matchInfo.append("匹配失败\n");
						matchInfo.append("阳台地面匹配备用小类:结构主地面(dimz)\n");
						planProductInfoParam.setSmallTypeValuekeyInit("basic_dimz");
						planProductInfoMatched = this.checkDataFromMap(planProductInfoParam, recommendedPlanProductInfoList, true, checkDataFromMapEnum.defaultType);
						if(planProductInfoMatched == null) {
							matchInfo.append("匹配失败\n");
						}else {
							matchInfo.append("匹配成功\n");
						}
					}else {
						matchInfo.append("匹配成功\n");
					}
				}
			}else {
				matchInfo.append("匹配成功(按产品的初始化白膜大小类匹配))(posName:" + planProductInfo.getPosName() + ";productCode:" + planProductInfo.getProductCode() + ")\n");
			}
			
			return planProductInfoMatched;
		}
	}

	/**
	 * 如果匹配不到产品,就去系统中搜索(取一个)
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @return
	 */
	private PlanProductInfo selectOneProduct(PlanProductInfo planProductInfo) {
		
		// *参数验证 ->start
		if(planProductInfo == null) {
			return null;
		}
		// *参数验证 ->end
		
		// *设置查询条件 ->start
		BaseProductSearch baseProductSearch = baseProductService.getBaseProductSearchByPlanProductInfo(planProductInfo);
		baseProductSearch.setStart(0);
		baseProductSearch.setLimit(1);
		// *设置查询条件 ->end
		
		BaseProduct baseProductSelected = null;
		if(baseProductSearch != null) {
			List<BaseProduct> baseProductList = baseProductService.selectProductEasy(baseProductSearch);
			if(Lists.isNotEmpty(baseProductList)) {
				baseProductSelected = baseProductList.get(0);
			}
		}
		
		// BaseProduct->PlanProductInfo ->start
		return this.getBaseProductByPlanProductInfo(baseProductSelected);
		// BaseProduct->PlanProductInfo ->end
	}

	/**
	 * BaseProduct ->PlanProductInfo
	 * 
	 * @author huangsongbo
	 * @param baseProductSelected
	 * @return
	 */
	private PlanProductInfo getBaseProductByPlanProductInfo(BaseProduct baseProduct) {
		
		// *参数验证 ->start
		if(baseProduct == null) {
			return null;
		}
		// *参数验证 ->end
		
		PlanProductInfo planProductInfo = new PlanProductInfo();
		planProductInfo.setProductId(baseProduct.getId());
		planProductInfo.setBigTypeValuekey(baseProduct.getBigTypeValueKey());
		planProductInfo.setSmallTypeValuekey(baseProduct.getSmallTypeValueKey());
		planProductInfo.setProductCode(baseProduct.getProductCode());
		planProductInfo.setProductLength(baseProduct.getProductLength());
		planProductInfo.setProductWidth(baseProduct.getProductWidth());
		planProductInfo.setProductHeight(baseProduct.getProductHeight());
		planProductInfo.setFullPaveLength(baseProduct.getFullPaveLength());
		planProductInfo.setMeasureCode(baseProduct.getMeasureCode());
		planProductInfo.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
		planProductInfo.setStyleId(baseProduct.getStyleId());
		return planProductInfo;
	}

	/**
	 * smallTypeInit:样板房的小类匹配推荐方案initProduct(初始白膜)的小类
	 * defaultType:匹配其他信息(对等信息,比如样板房region_mark和推荐方案region_mark匹配...)
	 * @author huangsongbo
	 *
	 */
	private enum checkDataFromMapEnum{
		defaultType
	}
	
	/**
	 * 标记已被匹配过,并且返回productCode
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @return
	 */
	private PlanProductInfo getEntityAndSignMatched(PlanProductInfo planProductInfo) {
		if(planProductInfo == null) {
			return null;
		}
		planProductInfo.setMatched(true);
		return planProductInfo;
	}

	/**
	 * 遍历smallTypemapInfoMap检查smallTypeValuekeyInit,取出smallTypeValuekeyInit与smallTypeValuekey匹配的PlanProductInfo
	 * 使用范围:不按小类匹配,按其他属性匹配(initProductSmallTypeKey,区域标识,尺寸编码等等)
	 * 
	 * @author huangsongbo
	 * @param smallTypeValuekey 从smallTypemapInfo中取小类为smallTypeValuekey的PlanProductInfo
	 * @param planProductInfoCheck 验证附加属性(比如产品长宽高)
	 * @param isMatchMatched 是否匹配已经匹配过的产品 true:匹配过的也可以匹配;false:不匹配已经匹配过的产品
	 * @param wallorientation 
	 * @param smallTypemapInfo
	 * @return
	 */
	private PlanProductInfo checkDataFromMap(
			PlanProductInfo planProductInfoCheck, List<PlanProductInfo> recommendedPlanProductInfoList, 
			boolean isMatchMatched, checkDataFromMapEnum type
			) {
		
		if(recommendedPlanProductInfoList == null) {
			// 参数验证 ->start
			logger.error("------function:IntelligenceDecorationServiceImpl.checkDataFromMap(...) ->\n"
					+ "param:smallTypemapInfoMap = null");
			return null;
		}
		if(type == null) {
			type = checkDataFromMapEnum.defaultType;
		}
		// 参数验证 ->end
		
		for(PlanProductInfo planProductInfo : recommendedPlanProductInfoList) {
			if(type == checkDataFromMapEnum.defaultType) {
				boolean flag = this.productMatchCheckMoreInfo(planProductInfo, planProductInfoCheck);
				if(!isMatchMatched) {
					// 不匹配已经匹配过的产品
					if(planProductInfo.isMatched()) {
						flag = false;
					}
				}
				if(flag) {
					return planProductInfo;
				}
			}else {
				// 方便扩展
			}
		}
		
		return null;
	}

	@Override
	public boolean productMatchCheckMoreInfo(PlanProductInfo planProductInfo, PlanProductInfo planProductInfoCheck) {
		// 天花布局标识List匹配
		if(Lists.isNotEmpty(planProductInfoCheck.getProductSmallpoxIdentifyList())) {
			if(planProductInfoCheck.getProductSmallpoxIdentifyList().indexOf(planProductInfo.getProductSmallpoxIdentify()) == -1) {
				return false;
			}
		}
		// 地面布局标识List匹配
		if(Lists.isNotEmpty(planProductInfoCheck.getStructureProductSmallpoxIdentifyList())) {
			if(planProductInfoCheck.getStructureProductSmallpoxIdentifyList().indexOf(planProductInfo.getStructureProductSmallpoxIdentify()) == -1) {
				return false;
			}
		}
		// 产品过滤属性匹配
		if(Lists.isNotEmpty(planProductInfoCheck.getProductFilterPropList())) {
			planProductInfo.setProductFilterPropList(baseProductService.getProductFilterPropList(planProductInfo.getProductId()));
			if(!Utils.isMatched(planProductInfoCheck.getProductFilterPropList(), planProductInfo.getProductFilterPropList())) {
				return false;
			}
		}
		// 多小类匹配
		if(Lists.isNotEmpty(planProductInfoCheck.getSmallTypeValueKeyList())) {
			List<String> smallTypeValueKeyList = new ArrayList<String>(planProductInfoCheck.getSmallTypeValueKeyList());
			Utils.dealWithBaimoType(smallTypeValueKeyList);
			if(smallTypeValueKeyList.indexOf(planProductInfo.getSmallTypeValuekey()) == -1) {
				return false;
			}
		}
		// 匹配产品高度
		if(StringUtils.isNotEmpty(planProductInfoCheck.getProductHeight())) {
			int baseProductHeight = 0;
			if(StringUtils.isEmpty(planProductInfo.getProductHeight())){
				
			}else {
				baseProductHeight = Integer.parseInt(planProductInfo.getProductHeight());
			}
			if(planProductInfoCheck.isBeijing()) {
				// 背景墙匹配
				if(StringUtils.equals("30", planProductInfoCheck.getProductHeight()) || StringUtils.equals("50", planProductInfoCheck.getProductHeight())) {
					if(!StringUtils.equals(planProductInfo.getProductHeight(), planProductInfoCheck.getProductHeight())){
						return false;
					}
				}else {
					if(StringUtils.equals(planProductInfo.getProductHeight(), planProductInfoCheck.getProductHeight()) || (baseProductHeight < 230 && baseProductHeight > 50)) {
						
					}else {
						return false;
					}
				}
			}else {
				if(!StringUtils.equals(planProductInfo.getProductHeight(), planProductInfoCheck.getProductHeight())){
					return false;
				}
			}
		}
		// 匹配产品长度
		if(StringUtils.isNotEmpty(planProductInfoCheck.getProductLength())) {
			if(!StringUtils.equals(planProductInfo.getProductLength(), planProductInfoCheck.getProductLength())){
				return false;
			}
		}
		// 匹配产品宽度
		if(StringUtils.isNotEmpty(planProductInfoCheck.getProductWidth())) {
			if(!StringUtils.equals(planProductInfo.getProductWidth(), planProductInfoCheck.getProductWidth())){
				return false;
			}
		}
		// 匹配产品长度范围
		Integer productLengthStart = planProductInfoCheck.getProductLengthStart();
		Integer productLengthEnd = planProductInfoCheck.getProductLengthEnd();
		if(productLengthStart != null && productLengthStart > 0
				&& productLengthEnd != null && productLengthEnd > 0) {
			if(StringUtils.isEmpty(planProductInfo.getProductLength())) {
				return false;
			}
			int productLength = 0;
			try {
				productLength = Integer.parseInt(planProductInfo.getProductLength());
			}catch (Exception e) {
				
			}
			
			if(!(productLength >= productLengthStart && productLength <= productLengthEnd)) {
				return false;
			}
		}
		// 匹配初始化白膜大类
		if(StringUtils.isNotEmpty(planProductInfoCheck.getBigTypeValuekeyInit())) {
			if(!StringUtils.equals(planProductInfo.getBigTypeValuekeyInit(), planProductInfoCheck.getBigTypeValuekeyInit())){
				return false;
			}
		}
		// 匹配初始化白膜小类
		if(StringUtils.isNotEmpty(planProductInfoCheck.getSmallTypeValuekeyInit())) {
			if(!StringUtils.equals(planProductInfo.getSmallTypeValuekeyInit(), planProductInfoCheck.getSmallTypeValuekeyInit())){
				return false;
			}
		}
		// 匹配大类
		if(StringUtils.isNotEmpty(planProductInfoCheck.getBigTypeValuekey())) {
			if(!StringUtils.equals(planProductInfo.getBigTypeValuekey(), planProductInfoCheck.getBigTypeValuekey())){
				return false;
			}
		}
		// 匹配小类
		if(StringUtils.isNotEmpty(planProductInfoCheck.getSmallTypeValuekey())) {
			if(!StringUtils.equals(planProductInfo.getSmallTypeValuekey(), planProductInfoCheck.getSmallTypeValuekey())){
				return false;
			}
		}
		// 区域标识
		if(StringUtils.isNotEmpty(planProductInfoCheck.getRegionMark())) {
			if(!StringUtils.equals(planProductInfo.getRegionMark(), planProductInfoCheck.getRegionMark())){
				return false;
			}
		}
		// 区域标识前缀
		if(StringUtils.isNotEmpty(planProductInfoCheck.getRegionMarkLikeStart())) {
			if(
					StringUtils.isNotEmpty(planProductInfo.getRegionMark()) 
					&& planProductInfo.getRegionMark().startsWith(planProductInfoCheck.getRegionMarkLikeStart())
					) {
				
			}else {
				return false;
			}
		}
		// 区域标识前缀过滤(not like)
		if(StringUtils.isNotEmpty(planProductInfoCheck.getRegionMarkNotLikeStart())) {
			if(
					StringUtils.isNotEmpty(planProductInfo.getRegionMark()) 
					&& !planProductInfo.getRegionMark().startsWith(planProductInfoCheck.getRegionMarkNotLikeStart())
					) {
				
			}else {
				return false;
			}
		}
		// 尺寸编码
		if(StringUtils.isNotEmpty(planProductInfoCheck.getMeasureCode())) {
			if(!StringUtils.equals(planProductInfo.getMeasureCode(), planProductInfoCheck.getMeasureCode())){
				return false;
			}
		}
		// 墙体类型
		if(StringUtils.isNotEmpty(planProductInfoCheck.getWallType())) {
			if(!StringUtils.equals(planProductInfo.getWallType(), planProductInfoCheck.getWallType())){
				return false;
			}
		}
		return true;
	}

	/**
	 * initProduct:匹配initProduct(推荐方案的初始白膜)(大类)
	 * defaultProduct:匹配推荐方案的产品(大小类)
	 * beijingProduct:匹配背景墙(墙体类别)
	 * regionMark:匹配区域标识(大类+区域标识)
	 * regionMarkStandBy:匹配备用的区域标识;示例:主天花的区域编码是1开头(10,11),过道优先匹配其他过道,再玄关,再主天花,玄关优先匹配玄关,再过道,再主天花
	 * @author huangsongbo
	 * @throws IntelligenceDecorationException 
	 *
	 */
	/*private enum productMatchEnum{
		initProduct, defaultProduct, beijingProduct, regionMarkAndMeasureCode, regionMarkStandBy
	}*/
	
	@Override
	public List<StructureMatchInfoDTO> structureListMatch(
			/*PlanStructureInfo planStructureInfo, */
			ProductListByTypeInfo productListByTypeInfo,
			PlanStructureInfo planStructureInfoRecommended, Integer planId,
			String username, Integer opType, Map<String, List<PlanProductInfo>> productListmap, String spaceCode, DesignTemplet designTemplet) throws IntelligenceDecorationException {
		// 参数验证/参数处理 ->start
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 暂时没用,用来以后扩展(处理不同房间类型不同的结构匹配逻辑)
		if(StringUtils.isEmpty(spaceCode)) {
			// 默认为卧室一件装修逻辑
			spaceCode = "D";
		}
		// 参数验证/参数处理 ->end
		
		List<StructureMatchInfoDTO> structureMatchInfoDTOList = null;
		
		/*if(spaceCode.startsWith(SpaceCommonConstant.HOUSETYPE_TOILET)) {
			// 卫生间结构匹配逻辑
			structureMatchInfoDTOList = structureListMatchToilet(productListByTypeInfo, planStructureInfoRecommended, planId, username, opType, productListmap);
		}else {
			// 默认是卧室结构匹配逻辑
			structureMatchInfoDTOList = structureListMatchBedroom(productListByTypeInfo, planStructureInfoRecommended, planId, username, opType, productListmap);
		}*/
		
		// 暂时是通用逻辑,并没有发现结构匹配逻辑有特殊点
		structureMatchInfoDTOList = structureListMatchBedroom(
				productListByTypeInfo, planStructureInfoRecommended,
				planId, username, 
				opType, productListmap, designTemplet);
		return structureMatchInfoDTOList;
	}

	/**
	 * 卫生间结构匹配逻辑
	 * 
	 * @author huangsongbo
	 * @param productListByTypeInfo
	 * @param planStructureInfoRecommended
	 * @param planId
	 * @param username
	 * @param opType
	 * @param productListmap
	 * @param designTempletId 样板房id
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	/*private List<StructureMatchInfoDTO> structureListMatchToilet(
			ProductListByTypeInfo productListByTypeInfo,
			PlanStructureInfo planStructureInfoRecommended, Integer planId,
			String username, Integer opType, Map<String, List<PlanProductInfo>> productListmap,
			Integer designTempletId) throws IntelligenceDecorationException {
		return null;
	}*/
	
	/**
	 * 卧室结构匹配逻辑
	 * 
	 * @author huangsongbo
	 * @param productListByTypeInfo
	 * @param planStructureInfoRecommended
	 * @param planId
	 * @param username
	 * @param opType
	 * @param productListmap
	 * @param designTemplet 样板房信息
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private List<StructureMatchInfoDTO> structureListMatchBedroom(
			ProductListByTypeInfo productListByTypeInfo,
			PlanStructureInfo planStructureInfoRecommended, Integer planId,
			String username, Integer opType, Map<String, List<PlanProductInfo>> productListmap,
			DesignTemplet designTemplet) throws IntelligenceDecorationException {
		
		// 参数验证/参数处理 ->start
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证/参数处理 ->end
		
		List<StructureMatchInfoDTO> structureMatchInfoDTOList = new ArrayList<BedroomProductMatchDTO.StructureMatchInfoDTO>();
		
		// *取出需要的信息 ->start
		PlanStructureInfo planStructureInfo = productListByTypeInfo.getStructureInfo();
		Map<String, PlanProductInfo> templetStructureMainProudctMap = planStructureInfo.getMainProudctMap();
		Map<String, List<PlanProductInfo>> templetStructureProudctListMap = planStructureInfo.getStructureProudctListMap();
		Map<String, PlanProductInfo> planStructureMainProudctMap = planStructureInfoRecommended.getMainProudctMap();
		Map<String, List<PlanProductInfo>> planStructureProudctListMap = planStructureInfoRecommended.getStructureProudctListMap();
		// *取出需要的信息 ->end
		
		if(planStructureInfo.getMainProudctMap() != null) {
			for(String key : templetStructureMainProudctMap.keySet()) {
				StructureMatchInfoDTO structureMatchInfoDTO = new BedroomProductMatchDTO().new StructureMatchInfoDTO();
				PlanProductInfo templetMainPlanProductInfo = templetStructureMainProudctMap.get(key);
				// 记录结构匹配信息
				StringBuffer matchInfo = new StringBuffer("");
				matchInfo.append("正在匹配结构(plan_group_id:" + key + ")\n");
				List<PlanProductInfo> planProductInfoListMatched = this.structureMatch(
						templetMainPlanProductInfo, planStructureMainProudctMap, matchInfo,
						planStructureProudctListMap, productListmap, designTemplet.getId(), designTemplet.getGroundIdentify()
						);
				if(Lists.isNotEmpty(planProductInfoListMatched)) {
					// 匹配/搜索到了结构,insert设计方案产品,并组装结构返回数据
					structureMatchInfoDTO = this.getStructureMatchInfoDTO(
							planProductInfoListMatched,
							planId, username, templetMainPlanProductInfo.getCenter(),
							templetMainPlanProductInfo.getRegionMark(), templetMainPlanProductInfo.getMeasureCode(),
							opType);
				}else {
					// 没有匹配/搜索到结构
					// 通知数据组录数据,做一个容错处理,将样板房中该结构当作单品匹配
					matchInfo.append("结构没有匹配到,启动备用方案:将结构当单品处理\n");
					List<PlanProductInfo> templetProductInfoList = templetStructureProudctListMap.get(key);
					if(Lists.isNotEmpty(templetProductInfoList)) {
						List<PlanProductInfo> planProductInfoList = productListByTypeInfo.getProductList();
						for(PlanProductInfo planProductInfo : templetProductInfoList) {
							planProductInfoList.add(planProductInfo);
						}
					}
				}
				this.setSomeInfo(structureMatchInfoDTO, templetStructureProudctListMap.get(key));
				structureMatchInfoDTO.setMatchInfo(matchInfo.toString());
				structureMatchInfoDTOList.add(structureMatchInfoDTO);
			}
		}else {
			// 没有结构需要匹配
		}
		return structureMatchInfoDTOList;
	}
	
	/**
	 * 设置StructureMatchInfoDTO的部分信息(delPosNameList, delStructureCenter)
	 * @param structureMatchInfoDTO
	 * @param list
	 */
	private void setSomeInfo(StructureMatchInfoDTO structureMatchInfoDTO, List<PlanProductInfo> planProductInfoList) {
		
		// 参数验证 ->start
		if(structureMatchInfoDTO == null) {
			structureMatchInfoDTO = new BedroomProductMatchDTO().new StructureMatchInfoDTO();
		}
		if(planProductInfoList == null) {
			return;
		}
		// 参数验证 ->end
		
		List<String> delPosNameList = new ArrayList<String>();
		String delStructureCenter = null;
		String delStructureRegionMark = null;
		for(PlanProductInfo planProductInfo : planProductInfoList) {
			delPosNameList.add(planProductInfo.getPosName());
			if(StringUtils.isEmpty(delStructureCenter)) {
				delStructureCenter = planProductInfo.getCenter();
			}
			if(StringUtils.isEmpty(delStructureRegionMark)) {
				delStructureRegionMark = planProductInfo.getRegionMark();
			}
		}
		
		structureMatchInfoDTO.setDelPosNameList(delPosNameList);
		structureMatchInfoDTO.setDelStructureCenter(delStructureCenter);
		structureMatchInfoDTO.setDelStructureRegionMark(delStructureRegionMark);
	}

	/**
	 * 组装StructureMatchInfoDTO
	 * 
	 * @author huangsongbo
	 * @param planStructureInfoList 匹配上的结构的产品list(推荐方案中)
	 * @param planId
	 * @param username
	 * @param center
	 * @param regionMark
	 * @param measureCode
	 * @param opType
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private StructureMatchInfoDTO getStructureMatchInfoDTO(
			List<PlanProductInfo> planStructureInfoList,
			Integer planId, String username, String center,
			String regionMark, String measureCode, Integer opType) throws IntelligenceDecorationException {
		
		// *参数验证 ->start
		if(Lists.isEmpty(planStructureInfoList)) {
			logger.error("function:IntelligenceDecorationServiceImpl.getStructureMatchInfoDTO(....)->\n参数planStructureInfoList = null");
			return null;
		}
		if(planId == null) {
			logger.error("function:IntelligenceDecorationServiceImpl.getStructureMatchInfoDTO(....)->\n参数planStructureId:planId = null");
			return null;
		}
		if(StringUtils.isEmpty(username)) {
			logger.error("function:IntelligenceDecorationServiceImpl.getStructureMatchInfoDTO(....)->\n参数(StringUtils.isEmpty(username)) = true");
			return null;
		}
		String planStructureId = planStructureInfoList.get(0).getPlanGroupId();
		if(StringUtils.isEmpty(planStructureId)) {
			logger.error("function:IntelligenceDecorationServiceImpl.getStructureMatchInfoDTO(....)->\n"
					+ "String planStructureId = planStructureInfoList.get(0).getPlanGroupId()->planStructureId = null");
			return null;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证 ->end
		
		StructureMatchInfoDTO structureMatchInfoDTO = new BedroomProductMatchDTO().new StructureMatchInfoDTO();
		List<DetailInfo> structureDetailInfoList = new ArrayList<DetailInfo>();
		
		String newPlanStructureId = null;
		Integer groupOrStructureId = null;
		String flag = Utils.generateRandomDigitString(12);
		if(planStructureId.indexOf("_") != -1) {
			groupOrStructureId = Integer.valueOf(planStructureId.substring(0, planStructureId.indexOf("_")));
			newPlanStructureId = groupOrStructureId + "_" + flag;
		}else {
			throw new RuntimeException("匹配结构成功,生成新的结构plan_group_id失败:没有获取到推荐方案中结构的plan_group_id or plan_group_id格式不正确;");
		}
		
		for(PlanProductInfo planProductInfo : planStructureInfoList) {
			planProductInfo.setGroupOrStructureId(groupOrStructureId);
			planProductInfo.setGroupType(1);
			planProductInfo.setPlanGroupId(newPlanStructureId);
			planProductInfo.setIsMainProduct(0);
			planProductInfo.setCenter(center);
			planProductInfo.setRegionMark(regionMark);
			planProductInfo.setMeasureCode(measureCode);
			Integer designPlanProductId = designPlanProductService.createByPlanProductInfo(planProductInfo, planId, username, opType);
			DetailInfo detailInfo = new BedroomProductMatchDTO().new DetailInfo();
			detailInfo.setDesignPlanProductId(designPlanProductId);
			detailInfo.setRecommendedPlanProductCode(planProductInfo.getProductCode());
			detailInfo.setRecommendedPlanProductPosName(planProductInfo.getPosName());
			detailInfo.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			detailInfo.setSmallTypeValuekey(planProductInfo.getSmallTypeValuekey());
			detailInfo.setBasicModelCode(planProductInfo.getInitProductCode());
			structureDetailInfoList.add(detailInfo);
		}
		
		// *结构配置文件路径 ->start
		String structureConfig = null;
		StructureProduct structureProduct = structureProductService.get(groupOrStructureId);
		if(structureProduct == null) {
			/*throw new RuntimeException("结构未找到,structureId:" + groupOrStructureId);*/
			throw new IntelligenceDecorationException("推荐方案中某个结构的相关信息已经被删除");
		}
		Integer configId = structureProduct.getConfigFileId();
		if(configId == null) {
			/*throw new RuntimeException("结构的配置文件id为空,structureId:" + groupOrStructureId);*/
			throw new IntelligenceDecorationException("推荐方案中某个结构的配置文件id为空");
		}
		ResFile resFile = resFileService.get(configId);
		if(resFile == null) {
			/*throw new RuntimeException("配置文件没有找到,resFileId:" + configId);*/
			throw new IntelligenceDecorationException("推荐方案中某个结构的配置文件没有找到");
		}
		/*structureConfig = resFile.getFilePath();*/
		structureConfig=FileUploadUtils.getFileContext(Utils.getAbsolutePath(resFile.getFilePath(), null));
		// *结构配置文件路径 ->end
		
		structureMatchInfoDTO.setStructureDetailInfoList(structureDetailInfoList);
		/*structureMatchInfoDTO.setNewPlanStructureId(newPlanStructureId);*/
		structureMatchInfoDTO.setStructureConfig(structureConfig);
		structureMatchInfoDTO.setDelStructureRegionMark(regionMark);
		return structureMatchInfoDTO;
	}

	/**
	 * 结构匹配
	 * 
	 * @author huangsongbo
	 * @param templetMainPlanProductInfo 参照结构信息
	 * @param planStructureMainProudctMap 结构list(被匹配的推荐方案中的结构)
	 * @param matchInfo 记录匹配信息
	 * @param planStructureProudctListMap 
	 * @param productListmap 推荐方案产品信息,为了结构没有匹配上对应的贴图而做的备用方案
	 * @param designTempletId 一键装修的样板房id:用于自行搜索墙面结构(墙面结构暂定为定制结构)
	 * @param groundIdentify 样板房地面布局标识
	 * @return 
	 * @throws IntelligenceDecorationException 
	 */
	private List<PlanProductInfo> structureMatch(PlanProductInfo templetMainPlanProductInfo,
			Map<String, PlanProductInfo> planStructureMainProudctMap, StringBuffer matchInfo,
			Map<String, List<PlanProductInfo>> planStructureProudctListMap,
			Map<String, List<PlanProductInfo>> productListmap,
			Integer designTempletId, String groundIdentify) throws IntelligenceDecorationException {
		
		// *参数验证 ->start
		if(templetMainPlanProductInfo == null) {
			return null;
		}
		if(planStructureMainProudctMap == null) {
			return null;
		}
		
		List<PlanProductInfo> planProductInfoListMatched = new ArrayList<ProductListByTypeInfo.PlanProductInfo>();
		
		// 地面结构匹配区别于墙面结构匹配
		if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, templetMainPlanProductInfo.getBigTypeValuekey())) {
			// 匹配墙面结构
			// 检测推荐方案中有没有墙面结构,如果有的话,直接匹配上
			for(String key : planStructureMainProudctMap.keySet()) {
				PlanProductInfo planProductInfo = planStructureMainProudctMap.get(key);
				if(StringUtils.equals(ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM, planProductInfo.getBigTypeValuekey())) {
					// 匹配上,根据匹配的结构的属性自行搜索墙面结构
					
					if(StringUtils.isNotEmpty(planProductInfo.getPlanGroupId())) {
						try {
							planProductInfo.setGroupOrStructureId(Integer.valueOf(planProductInfo.getPlanGroupId().split("_")[0]));
						}catch (Exception e) {
							logger.error(e);
						}
					}
					
					planProductInfoListMatched = this.selectOneStructureAndMatchProductForQiangm(
							planProductInfo.getGroupOrStructureId(),
							planStructureProudctListMap.get(planProductInfo.getPlanGroupId()),
							productListmap, designTempletId, matchInfo);
					break;
				}
			}
		}else {
			// 匹配地面结构
			// 区域标识
			String regionMark = templetMainPlanProductInfo.getRegionMark();
			// 是否需要匹配地面布局标识
			/*boolean idNeedMatchProductSmallpoxIdentify = false;*/
			/*if(groundIdentify != null && groundIdentify != 0) {*/
			/*if(!StringUtils.equals("0", groundIdentify)) {
				idNeedMatchProductSmallpoxIdentify = true;
			}*/
			if(StringUtils.isEmpty(regionMark)) {
				/*matchInfo.append("样板房产品表中,结构的区域标识没有录,无法匹配,只能去系统中搜索(templet_plan_group_id:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");*/
				matchInfo.append("样板房产品表中,结构的区域标识没有录,无法匹配(templet_plan_group_id:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
				return null;
			}
			// 尺寸编码
			String measureCode = templetMainPlanProductInfo.getMeasureCode();
			if(StringUtils.isEmpty(measureCode)) {
				matchInfo.append("样板房产品表中,结构的尺寸编码没有录,无法匹配,也无法搜索(templet_plan_group_id:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
				return null;
			}
			// *参数验证 ->end
			
			String planStructureId = null;
			
			// 第一轮正常匹配 ->start
			matchInfo.append("正在进行区域标识匹配\n");
			for(String key : planStructureMainProudctMap.keySet()) {
				
				PlanProductInfo planProductInfo = planStructureMainProudctMap.get(key);
				if(StringUtils.isNotEmpty(planProductInfo.getPlanGroupId())) {
					try {
						planProductInfo.setGroupOrStructureId(Integer.valueOf(planProductInfo.getPlanGroupId().split("_")[0]));
					}catch (Exception e) {
						logger.error(e);
					}
				}
				
				// 匹配区域标识
				if(StringUtils.equals(regionMark, planProductInfo.getRegionMark())) {
					// 匹配上区域标识
					matchInfo.append("区域标识标识匹配成功\n");
					// 检测尺寸编码+布局标识是否一致
					if(StringUtils.equals(measureCode, planProductInfo.getMeasureCode())
							/*&& (!idNeedMatchProductSmallpoxIdentify || groundIdentify.intValue() == planProductInfo.getStructureProductSmallpoxIdentify().intValue())*/
							/*&& (!idNeedMatchProductSmallpoxIdentify || StringUtils.equals(groundIdentify, planProductInfo.getStructureProductSmallpoxIdentify()))*/
							&& (Utils.getIdentifyList(groundIdentify).indexOf(planProductInfo.getStructureProductSmallpoxIdentify()) != -1)
							) {
						matchInfo.append("尺寸编码/布局标识匹配成功,应用此结构(planStructureId:" + planStructureId + ")");
						// 应用该结构
						planStructureId = planProductInfo.getPlanGroupId();
					}else {
						// 带自身的尺寸编码+planProductInfoMatched的款式+planProductInfoMatched的structure_number去系统中搜索
						matchInfo.append("尺寸编码/布局标识匹配失败,去系统中搜索结构\n");
						planProductInfoListMatched = this.selectOneStructureAndMatchProduct(
								measureCode, planProductInfo.getGroupOrStructureId(), planStructureProudctListMap.get(planProductInfo.getPlanGroupId()), productListmap,
								regionMark, ProductModelConstant.PRODUCTATTRCODE_DIM, groundIdentify, designTempletId
								);
						if(planProductInfoListMatched == null) {
							matchInfo.append("没有搜索到符合条件的结构\n");
						}else {
							matchInfo.append("搜索到符合条件的结构\n");
							try {
								matchInfo.append("structureId:" + planProductInfoListMatched.get(0).getPlanGroupId());
							}catch (Exception e) {
								
							}
						}
						return planProductInfoListMatched;
					}
				}
			}
			// 第一轮正常匹配 ->end
			
			// 第二轮备用区域标识匹配 ->start
			if(StringUtils.isEmpty(planStructureId)) {
				matchInfo.append("正在执行备用区域标识匹配逻辑\n");
				// 匹配备用的区域标识
				PlanProductInfo planProductInfoMatched = this.structureMatchStandByRegionMark(regionMark, planStructureMainProudctMap);
				matchInfo.append("备用区域标识匹配成功\n");
				// 带自身的尺寸编码+planProductInfoMatched的款式去系统中搜索
				if(planProductInfoMatched != null) {
					matchInfo.append("根据匹配上的结构属性在系统中搜索结构\n");
					/*planProductInfoListMatched = this.selectOneStructure(templetMainPlanProductInfo.getGroupOrStructureId(), planProductInfoMatched.getGroupOrStructureId(), planStructureProudctListMap.get(planStructureId));*/
					planProductInfoListMatched = this.selectOneStructureAndMatchProduct(
							measureCode, planProductInfoMatched.getGroupOrStructureId(),
							planStructureProudctListMap.get(planProductInfoMatched.getPlanGroupId()), productListmap,
							regionMark, ProductModelConstant.PRODUCTATTRCODE_DIM, 
							groundIdentify,
							designTempletId
							);
					if(planProductInfoListMatched == null) {
						matchInfo.append("没有搜索到符合条件的结构\n");
					}else {
						matchInfo.append("搜索到符合条件的结构\n");
						try {
							matchInfo.append("structureId:" + planProductInfoListMatched.get(0).getPlanGroupId());
						}catch (Exception e) {
							
						}
					}
					return planProductInfoListMatched;
				}
			}
			// 第二轮备用区域标识匹配 ->end
			
			if(StringUtils.isEmpty(planStructureId)) {
				// 没有匹配上,尺寸编码+款式在系统中搜索
				matchInfo.append("区域标识匹配/备用区域标识匹配均失败\n");
				// 搜索结构(未完成)
			}else {
				// 匹配上了推荐方案中的结构
				matchInfo.append("匹配上了推荐方案中的结构(templet_plan_group_id:" + templetMainPlanProductInfo.getPlanGroupId() + ";recommended_plan_group_id:" + planStructureId + ")\n");
				planProductInfoListMatched = planStructureProudctListMap.get(planStructureId);
				// test ->start
				/*System.out.println(planProductInfoListMatched.get(0));*/
				// test ->end
			}
			
		}
		
		return planProductInfoListMatched;
	}

	/**
	 * 墙面结构的搜索
	 * 
	 * @author huangsongbo
	 * @param groupOrStructureId 匹配到的墙面结构id
	 * @param designTempletId 
	 * @param planProductInfoRecommendedList 推荐方案中该结构的明细
	 * @param productListmap
	 * @param matchInfo 匹配信息
	 * @return
	 */
	private List<PlanProductInfo> selectOneStructureAndMatchProductForQiangm(Integer groupOrStructureId,
		List<PlanProductInfo> planProductInfoRecommendedList, Map<String, List<PlanProductInfo>> productListmap, Integer designTempletId, StringBuffer matchInfo) {
		List<PlanProductInfo> planProductInfoList = this.selectOneStructureForQiangm(groupOrStructureId, designTempletId, matchInfo);
		// 结构的白膜明细匹配对应材质产品
		return matchStructureProduct(planProductInfoList, planProductInfoRecommendedList, productListmap, ProductModelConstant.PRODUCTATTRCODE_QIANGM);
	}

	/**
	 * 墙面结构的搜索
	 * 
	 * @author huangsongbo
	 * @param structureId 匹配上的结构的id
	 * @param designTempletId 一件装修样板房id(用于搜索定制墙面结构)
	 * @param matchInfo 
	 * @return
	 */
	private List<PlanProductInfo> selectOneStructureForQiangm(Integer structureId, Integer designTempletId, StringBuffer matchInfo) {
		// 参数验证 ->start
		if(structureId == null) {
			return null;
		}
		StructureProduct structureProduct = structureProductService.get(structureId);
		if(structureProduct == null) {
			logger.error(CLASSNAME + "selectOneStructureForQiangm:结构没有找到,structureId:" + structureId);
			return null;
		}
		if(designTempletId == null) {
			return null;
		}
		// 参数验证 ->end

		matchInfo.append("自行搜索墙面结构\n");
		
		// 根据matchStructureId获取结构的搜索条件(styleId,structure_number) ->start
		StructureProductSearch structureProductSearch = new StructureProductSearch();
		structureProductSearch.setTempletId(designTempletId);
		structureProductSearch.setStructureType(StructureProductConstant.STRUCTURETYPE_QIAMGM);
		structureProductSearch.setStructureNumber(structureProduct.getStructureNumber());
		matchInfo.append("搜索墙面结构条件:tempetId:" + structureProductSearch.getTempletId() + 
				";structureType:" + structureProductSearch.getStructureType() + 
				";structureNumber:" + structureProductSearch.getStructureNumber());
		Integer structureIdSeleted = structureProductService.easySearchV2(structureProductSearch);
		// 根据matchStructureId获取结构的搜索条件(styleId,structure_number) ->end

		if (structureIdSeleted == null) {
			return null;
		}
		matchInfo.append("匹配上结构,structureId:" + structureIdSeleted);
		
		String planGroupId = structureIdSeleted + "_" + Utils.generateRandomDigitString(12);

		// 取出结构明细拼装成List<PlanProductInfo>
		List<PlanProductInfo> planProductInfoList = structureProductService
				.getPlanProductInfoListByStructureId(structureIdSeleted);
		// 取出结构明细的属性(用于结构白膜匹配推荐方案中的结构替换成的材质)
		for (PlanProductInfo planProductInfo : planProductInfoList) {
			planProductInfo.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfo.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			planProductInfo.setInitProductId(planProductInfo.getProductId());
			planProductInfo.setMeasureCode(null);
			planProductInfo.setRegionMark(null);
			planProductInfo.setPlanGroupId(planGroupId);
			planProductInfo.setIsStandard(0);
			planProductInfo.setIsMainStructureProduct(0);
			planProductInfo.setIsGroupReplaceWay(0);
			planProductInfo.setStructureProductProp(
					productPropsService.getStructureProductPropValueByProductId(planProductInfo.getProductId(), ProductModelConstant.PRODUCTATTRCODE_QIANGM));
			planProductInfo.setInitProductCode(planProductInfo.getProductCode());
		}
		return planProductInfoList;
	}

	/**
	 * 搜索结构并且将结构信息组装成List<PlanProductInfo>
	 * 
	 * @author huangsongbo
	 * @param measureCode
	 * @param matchStructureId
	 * @param planProductInfoRecommendedList
	 * @param productListmap 推荐方案中的单品信息,用于结构的明细产品之间匹配失败,就当作单品匹配
	 * @param regionMark 
	 * @param list 结构布局标识 
	 * @param productList 如果结构的白膜匹配不到对应产品(根据属性匹配),则当单品处理
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private List<PlanProductInfo> selectOneStructureAndMatchProduct(String measureCode, Integer matchStructureId,
			List<PlanProductInfo> planProductInfoRecommendedList, Map<String, List<PlanProductInfo>> productListmap, String regionMark, String productAttrCode, String groundIdentify, Integer designTempletId) throws IntelligenceDecorationException {
		List<PlanProductInfo> planProductInfoList = this.selectOneStructure(measureCode, matchStructureId, regionMark, productAttrCode, groundIdentify, designTempletId);
		// 结构的白膜明细匹配对应材质产品
		return matchStructureProduct(planProductInfoList, planProductInfoRecommendedList, productListmap, productAttrCode);
	}

	/**
	 * 结构白膜匹配单品/如果匹配不到则放到后面走单品搜索逻辑
	 * 
	 * @author huangsongbo
	 * @param planProductInfoList
	 * @param planProductInfoRecommendedList
	 * @param productListmap 推荐方案中的单品信息(用于结构之间的明细产品匹配失败,则当单品处理)
	 * @param productAttrCode 
	 * @param productList
	 * @return
	 */
	private List<PlanProductInfo> matchStructureProduct(List<PlanProductInfo> planProductInfoList,
			List<PlanProductInfo> planProductInfoRecommendedList, Map<String, List<PlanProductInfo>> productListmap, String productAttrCode) {
		// 参数验证 ->start
		if(Lists.isEmpty(planProductInfoList)) {
			return null;
		}
		if(Lists.isEmpty(planProductInfoRecommendedList)) {
			return null;
		}
		// 参数验证 ->end
		
		// 补充属性信息 ->start
		for(PlanProductInfo planProductInfoItem : planProductInfoRecommendedList) {
			planProductInfoItem.setStructureProductProp(productPropsService.getStructureProductPropValueByProductId(planProductInfoItem.getInitProductId(), productAttrCode));
			logger.debug("structureProductProp:" + planProductInfoItem.getStructureProductProp());
		}
		// 补充属性信息 ->end
		
		for(PlanProductInfo planProductInfo : planProductInfoList) {
			// 通过structureProductProp匹配
			
			boolean flag = this.matchStructureProductItem(planProductInfo, planProductInfoRecommendedList);
			if(flag) {
				
			}else {
				// 单品匹配
				this.matchStructureProductItem(planProductInfo, productListmap);
			}
		}
		return planProductInfoList;
	}

	/**
	 * 结构按照单品匹配(大小类)
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param productListmap
	 */
	private void matchStructureProductItem(PlanProductInfo planProductInfo,
			Map<String, List<PlanProductInfo>> productListmap) {
		StringBuffer matchInfo = new StringBuffer();
		PlanProductInfo planProductInfoMatched = this.productListMatchDim(planProductInfo, productListmap.get(planProductInfo.getBigTypeValuekey()), matchInfo);
		this.transferInfoWhenStructureProductMatched(planProductInfo, planProductInfoMatched);
	}

	/**
	 * 样板房中的结构明细白膜和推荐方案中的结构明细匹配
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param planProductInfoRecommendedList
	 * @return
	 */
	private boolean matchStructureProductItem(PlanProductInfo planProductInfo,
			List<PlanProductInfo> planProductInfoRecommendedList) {
		// 参数验证 ->start
		
		// 参数验证 ->end
		
		// 匹配并补充属性 ->start
		for(PlanProductInfo planProductInfoItem : planProductInfoRecommendedList) {
			if(StringUtils.equals(planProductInfo.getStructureProductProp(), planProductInfoItem.getStructureProductProp())) {
				this.transferInfoWhenStructureProductMatched(planProductInfo, planProductInfoItem);
				return true;
			}
		}
		// 匹配并补充属性 ->end
		
		return false;
	}

	/**
	 * 当样板房结构白膜匹配到推荐方案结构白膜应用的贴图(产品)时,填充planProductInfo属性
	 * 
	 * @author huangsongbo
	 * @param planProductInfo
	 * @param planProductInfoItem
	 */
	private void transferInfoWhenStructureProductMatched(PlanProductInfo planProductInfo,
			PlanProductInfo planProductInfoItem) {
		// 参数验证 ->start
		if(planProductInfo == null) {
			return;
		}
		if(planProductInfoItem == null) {
			return;
		}
		// 参数验证 ->end
		
		planProductInfo.setProductId(planProductInfoItem.getProductId());
		planProductInfo.setBigTypeValuekey(planProductInfoItem.getBigTypeValuekey());
		planProductInfo.setSmallTypeValuekey(planProductInfoItem.getSmallTypeValuekey());
		planProductInfo.setProductCode(planProductInfoItem.getProductCode());
		planProductInfo.setProductLength(planProductInfoItem.getProductLength());
		planProductInfo.setProductWidth(planProductInfoItem.getProductWidth());
		planProductInfo.setProductHeight(planProductInfoItem.getProductHeight());
		planProductInfo.setStyleId(planProductInfoItem.getStyleId());
	}

	/**
	 * 在系统中搜索结构,并且组装成List<PlanProductInfo>信息
	 * 
	 * @author huangsongbo
	 * @param measureCode 尺寸编码
	 * @param regionMark 区域标识
	 * @param identifylist 结构布局标识
	 * @param matchStructureId 参考结构A的属性进行搜索,结构A的id = matchStructureId
	 * @param designTempletId 样板房id
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private List<PlanProductInfo> selectOneStructure(String measureCode, Integer matchStructureId, String regionMark, String productAttrCode, String groundIdentify, Integer designTempletId) throws IntelligenceDecorationException {
		// 参数验证 ->start
		if(StringUtils.isEmpty(measureCode)) {
			return null;
		}
		if(matchStructureId == null) {
			return null;
		}
		StructureProduct structureProduct = structureProductService.get(matchStructureId);
		if(structureProduct == null) {
			/*return null;*/
			throw new IntelligenceDecorationException("推荐方案中某结构被删除,一键装修失败");
		}
		// 参数验证 ->end
		
		// 根据matchStructureId获取结构的搜索条件(styleId,structure_number) ->start
		Integer structureIdSeleted = structureProductService.easySearch(measureCode, structureProduct.getStyleId(), structureProduct.getStructureNumber(), Utils.getIdentifyList(groundIdentify), groundIdentify, designTempletId);
		// 根据matchStructureId获取结构的搜索条件(styleId,structure_number) ->end
		
		if(structureIdSeleted == null) {
			return null;
		}

		String planGroupId = structureIdSeleted + "_" + Utils.generateRandomDigitString(12);
		
		// 取出结构明细拼装成List<PlanProductInfo>
		List<PlanProductInfo> planProductInfoList = structureProductService.getPlanProductInfoListByStructureId(structureIdSeleted);
		// 取出结构明细的属性(用于结构白膜匹配推荐方案中的结构替换成的材质)
		for(PlanProductInfo planProductInfo : planProductInfoList) {
			planProductInfo.setBigTypeValuekeyInit(planProductInfo.getBigTypeValuekey());
			planProductInfo.setSmallTypeValuekeyInit(planProductInfo.getSmallTypeValuekey());
			planProductInfo.setInitProductId(planProductInfo.getProductId());
			planProductInfo.setMeasureCode(measureCode);
			planProductInfo.setRegionMark(regionMark);
			planProductInfo.setPlanGroupId(planGroupId);
			planProductInfo.setIsStandard(1);
			planProductInfo.setIsMainStructureProduct(0);
			planProductInfo.setIsGroupReplaceWay(0);
			planProductInfo.setStructureProductProp(productPropsService.getStructureProductPropValueByProductId(planProductInfo.getProductId(), productAttrCode));
			planProductInfo.setInitProductCode(planProductInfo.getProductCode());
		}
		return planProductInfoList;
	}

	/**
	 * 匹配备用的区域标识的结构
	 * @param regionMark 区域标识
	 * @param planStructureMainProudctMap
	 * @return
	 */
	private PlanProductInfo structureMatchStandByRegionMark(String regionMark, Map<String, PlanProductInfo> planStructureMainProudctMap) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(regionMark)) {
			return null;
		}
		// 参数验证 ->end
		
		PlanProductInfo planProductInfo = null;
		if(regionMark.startsWith("1")) {
			// 识别为主地面只和1%的匹配
			planProductInfo = this.structureMatchStandByRegionMarkSon(planStructureMainProudctMap, "1");
		}else {
			// 优先级先匹配自身开头%的,再非1%的,匹配不到再匹配1%
			planProductInfo = this.structureMatchStandByRegionMarkSon(planStructureMainProudctMap, regionMark.substring(0, 1));
			if(planProductInfo == null) {
				planProductInfo = this.structureMatchStandByRegionMarkSon(planStructureMainProudctMap, "!1");
				if(planProductInfo == null) {
					planProductInfo = this.structureMatchStandByRegionMarkSon(planStructureMainProudctMap, "1");
				}
			}
		}
		return planProductInfo;
	}

	/**
	 * 模糊匹配区域标识
	 * 
	 * @param planStructureMainProudctMap
	 * @param str 匹配条件
	 * @return
	 */
	private PlanProductInfo structureMatchStandByRegionMarkSon(Map<String, PlanProductInfo> planStructureMainProudctMap,
			String str) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		// 参数验证 ->end
		
		for(String key : planStructureMainProudctMap.keySet()) {
			PlanProductInfo planProductInfo = planStructureMainProudctMap.get(key);
			String measureCode = planProductInfo.getMeasureCode();
			if(StringUtils.isNotEmpty(measureCode)) {
				if(str.startsWith("!")) {
					if(!measureCode.startsWith(str.substring(1))) {
						return planProductInfo;
					}
				}else {
					if(measureCode.startsWith(str)) {
						return planProductInfo;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<GroupMatchInfoDTO> groupListMatch(
			PlanGroupInfo planGroupInfo, PlanGroupInfo planGroupInfoRecommended,
			Integer planId, String username, Integer opType) {
		// 参数验证/参数处理 ->start
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证/参数处理 ->end
		List<GroupMatchInfoDTO> groupMatchInfoDTOList = new ArrayList<BedroomProductMatchDTO.GroupMatchInfoDTO>();
	
		// *取出需要的信息 ->start
		Map<String, PlanProductInfo> templetGroupMainProudctMap = planGroupInfo.getMainProudctMap();
		Map<String, List<PlanProductInfo>> templetGroupProudctListMap = planGroupInfo.getGroupProudctListMap();
		Map<String, PlanProductInfo> planGroupMainProudctMap = planGroupInfoRecommended.getMainProudctMap();
		Map<String, List<PlanProductInfo>> planGroupProudctListMap = planGroupInfoRecommended.getGroupProudctListMap();
		// *取出需要的信息 ->end
		
		if(planGroupInfo.getMainProudctMap() != null) {
			for(String key : templetGroupMainProudctMap.keySet()) {
				GroupMatchInfoDTO groupMatchInfoDTO = new BedroomProductMatchDTO().new GroupMatchInfoDTO();
				PlanProductInfo templetMainPlanProductInfo = templetGroupMainProudctMap.get(key);
				// 记录组合匹配信息
				StringBuffer matchInfo = new StringBuffer("");
				String planGroupIdMatched = this.groupMatch(templetMainPlanProductInfo, planGroupMainProudctMap, matchInfo);
				if(StringUtils.isNotEmpty(planGroupIdMatched)) {
					groupMatchInfoDTO = this.getGroupMatchInfoDTO(planGroupProudctListMap.get(planGroupIdMatched), planGroupIdMatched, planId, username, opType);
				}else {
					// 组合没匹配上
					
				}
				this.setSameInfo(groupMatchInfoDTO, templetGroupProudctListMap.get(key));
				groupMatchInfoDTO.setMainProductPosName(templetMainPlanProductInfo.getPosName());
				groupMatchInfoDTO.setMatchInfo(matchInfo.toString());
				groupMatchInfoDTOList.add(groupMatchInfoDTO);
			}
		}else {
			// 没有组合需要匹配
		}
		return groupMatchInfoDTOList;
	}

	private GroupMatchInfoDTO getGroupMatchInfoDTO(
			List<PlanProductInfo> planGroupInfoList,
			String planGroupId, Integer planId, String username, Integer opType) {
		
		// *参数验证 ->start
		if(planGroupInfoList == null) {
			logger.error("function:IntelligenceDecorationServiceImpl.getGroupMatchInfoDTO(....)->\n参数planGroupInfoList = null");
			return null;
		}
		if(StringUtils.isEmpty(planGroupId)) {
			logger.error("function:IntelligenceDecorationServiceImpl.getGroupMatchInfoDTO(....)->\n参数planStructureId:(StringUtils.isEmpty(planGroupId)) = true");
			return null;
		}
		if(planId == null) {
			logger.error("function:IntelligenceDecorationServiceImpl.getGroupMatchInfoDTO(....)->\n参数planStructureId:planId = null");
			return null;
		}
		if(StringUtils.isEmpty(username)) {
			logger.error("function:IntelligenceDecorationServiceImpl.getGroupMatchInfoDTO(....)->\n参数(StringUtils.isEmpty(username)) = true");
			return null;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证 ->end
		
		GroupMatchInfoDTO groupMatchInfoDTO = new BedroomProductMatchDTO().new GroupMatchInfoDTO();
		List<DetailInfo> groupDetailInfoList = new ArrayList<DetailInfo>();
		
		String newPlanGroupId = null;
		Integer groupOrStructureId = null;
		String flag = Utils.generateRandomDigitString(12);
		if(planGroupId.indexOf("_") != -1) {
			groupOrStructureId = Integer.valueOf(planGroupId.substring(0, planGroupId.indexOf("_")));
			newPlanGroupId = groupOrStructureId + "_" + flag;
		}else {
			throw new RuntimeException("匹配组合成功,生成新的组合plan_group_id失败:没有获取到推荐方案中组合的plan_group_id or plan_group_id格式不正确;");
		}
		
		for(PlanProductInfo planProductInfoItem : planGroupInfoList) {
			PlanProductInfo planProductInfo = planProductInfoItem.clone();
			planProductInfo.setGroupType(0);
			planProductInfo.setGroupOrStructureId(groupOrStructureId);
			planProductInfo.setPlanGroupId(newPlanGroupId);
			Integer designPlanProductId = designPlanProductService.createByPlanProductInfo(planProductInfo, planId, username, opType);
			
			DetailInfo detailInfo = new BedroomProductMatchDTO().new DetailInfo();
			detailInfo.setRecommendedPlanProductCode(planProductInfo.getProductCode());
			detailInfo.setRecommendedPlanProductPosName(planProductInfo.getPosName());
			detailInfo.setDesignPlanProductId(designPlanProductId);
			detailInfo.setBigTypeValuekey(planProductInfo.getBigTypeValuekey());
			detailInfo.setSmallTypeValuekey(planProductInfo.getSmallTypeValuekey());
			detailInfo.setIsMainProduct(planProductInfo.getIsMainProduct());
			detailInfo.setGroupProductUniqueId(planProductInfo.getGroupProductUniqueId());
			groupDetailInfoList.add(detailInfo);
			if(StringUtils.isEmpty(newPlanGroupId)) {
				newPlanGroupId = planProductInfo.getPlanGroupId();
			}
		}
		
		// *结构配置文件路径 ->start
		String groupConfig = null;
		GroupProduct groupProduct = groupProductService.get(groupOrStructureId);
		if(groupProduct == null) {
			throw new RuntimeException("组合未找到,groupId:" + groupOrStructureId);
		}
		Integer configId = Integer.valueOf(groupProduct.getLocation());
		if(configId == null) {
			throw new RuntimeException("组合的配置文件id为空,structureId:" + groupOrStructureId);
		}
		ResFile resFile = resFileService.get(configId);
		if(resFile == null) {
			throw new RuntimeException("配置文件没有找到,resFileId:" + configId);
		}
		/*groupConfig = resFile.getFilePath();*/
		groupConfig=FileUploadUtils.getFileContext(Utils.getAbsolutePath(resFile.getFilePath(), null));
		// *结构配置文件路径 ->end
		
		groupMatchInfoDTO.setGroupDetailInfoList(groupDetailInfoList);
		groupMatchInfoDTO.setNewPlanGroupId(newPlanGroupId);
		groupMatchInfoDTO.setGroupConfig(groupConfig);
		
		return groupMatchInfoDTO;
	}

	private void setSameInfo(GroupMatchInfoDTO groupMatchInfoDTO, List<PlanProductInfo> planProductInfoList) {
		
		// 参数验证 ->start
		if(groupMatchInfoDTO == null) {
			groupMatchInfoDTO = new BedroomProductMatchDTO().new GroupMatchInfoDTO();
		}
		if(planProductInfoList == null) {
			return;
		}
		// 参数验证 ->end
		
		List<String> delPosNameList = new ArrayList<String>();
		
		for(PlanProductInfo planProductInfo : planProductInfoList) {
			delPosNameList.add(planProductInfo.getPosName());
		}
		
		groupMatchInfoDTO.setDelPosNameList(delPosNameList);
	}
	
	/**
	 * 组合匹配
	 * 
	 * @author huangsongbo
	 * @param templetMainPlanProductInfo 参照组合信息
	 * @param planGroupMainProudctMap 待匹配的组合信息list
	 * @param matchInfo 匹配信息
	 * @return
	 */
	private String groupMatch(PlanProductInfo templetMainPlanProductInfo,
			Map<String, PlanProductInfo> planGroupMainProudctMap, StringBuffer matchInfo) {
		
		// *参数验证 ->start
		if(templetMainPlanProductInfo == null) {
			return null;
		}
		if(planGroupMainProudctMap == null) {
			return null;
		}
		// 区域标识
		String regionMark = templetMainPlanProductInfo.getRegionMark();
		if(StringUtils.isEmpty(regionMark)) {
			matchInfo.append("样板房产品表中,组合的区域标识没有录,只能去匹配组合类型(组合planGroupId:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
		}
		if(templetMainPlanProductInfo.getGroupType() == null) {
			matchInfo.append("组合没有录组合类型!(组合planGroupId:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
			return null;
		}
		// *参数验证 ->end
		
		// 区域标识+组合类型+主产品过滤属性均匹配上的组合
		PlanProductInfo planProductInfoMatchedAll = null;
		
		// 只匹配上组合类型+主产品过滤属性的组合
		PlanProductInfo planProductInfoMatchedType = null;
		
		for(String key : planGroupMainProudctMap.keySet()) {
			// 匹配区域标识+组合类型
			PlanProductInfo planProductInfo = planGroupMainProudctMap.get(key);
			// 匹配组合类型+主产品过滤属性
			if(templetMainPlanProductInfo.getGroupType().intValue() == planProductInfo.getGroupType().intValue()
					&& Utils.isMatched(
							templetMainPlanProductInfo.getProductFilterPropList(), planProductInfo.getProductFilterPropList())
					) {
				if(planProductInfoMatchedType == null) {
					planProductInfoMatchedType = planProductInfo;
				}
				// 再匹配区域标识
				if(!StringUtils.isEmpty(regionMark) && StringUtils.equals(regionMark, planProductInfo.getRegionMark())) {
					planProductInfoMatchedAll = planProductInfo;
				}
			}
		}
		
		if(planProductInfoMatchedAll == null && planProductInfoMatchedType == null) {
			// 去掉过滤属性再匹配一次
			for(String key : planGroupMainProudctMap.keySet()) {
				// 匹配区域标识+组合类型
				PlanProductInfo planProductInfo = planGroupMainProudctMap.get(key);
				// 匹配组合类型+主产品过滤属性
				if(templetMainPlanProductInfo.getGroupType().intValue() == planProductInfo.getGroupType().intValue()) {
					if(planProductInfoMatchedType == null) {
						planProductInfoMatchedType = planProductInfo;
					}
					// 再匹配区域标识
					if(!StringUtils.isEmpty(regionMark) && StringUtils.equals(regionMark, planProductInfo.getRegionMark())) {
						planProductInfoMatchedAll = planProductInfo;
					}
				}
			}
		}
		
		if(planProductInfoMatchedAll != null) {
			matchInfo.append("匹配上了推荐方案中的组合(区域标识+组合类型+主产品过滤属性)!(组合planGroupId:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
			return planProductInfoMatchedAll.getPlanGroupId();
		}else {
			if(planProductInfoMatchedType != null) {
				matchInfo.append("匹配上了推荐方案中的组合(组合类型+主产品过滤属性)!(组合planGroupId:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
				return planProductInfoMatchedType.getPlanGroupId();
			}else {
				// 没有匹配上推荐方案中的组合
				matchInfo.append("没有匹配上推荐方案中的组合(推荐方案中没有相同类型的组合(或者主产品的过滤属性不匹配)(组合类型groupType:" + templetMainPlanProductInfo.getGroupType().intValue() + "))!(组合planGroupId:" + templetMainPlanProductInfo.getPlanGroupId() + ")\n");
				return null;
			}
		}
	}

}
