package com.nork.design.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.ClientEndpoint;

import com.nork.common.properties.ResProperties;
import com.nork.product.model.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.nork.aes.model.AESFileConstant;
import com.nork.aes.util.FileEncrypt;
import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.AesProperties;
import com.nork.common.util.AESUtil2;
import com.nork.common.util.ClassReflectionUtils;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.GsonUtil;
import com.nork.common.util.IgnoreJsonPropertyFilter;
import com.nork.common.util.SendEmail;
import com.nork.common.util.Utils;
import com.nork.common.util.WebSocketUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignPlanProductCacher;
import com.nork.design.cache.UsedProductsCacher;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.OptimizePlanMapper;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanModel;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignPlanRes;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductDTO;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.design.model.UnityDesignPlan;
import com.nork.design.model.UnityPlanProduct;
import com.nork.design.model.UnitySpaceCommon;
import com.nork.design.model.UsedProducts;
import com.nork.design.model.constant.ContextType;
import com.nork.design.model.constant.PlanVisibleCode;
import com.nork.design.model.unity.BindPointDataEx;
import com.nork.design.model.unity.ItemExt;
import com.nork.design.model.unity.RoomConfig;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedProductServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanRenderService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignRenderRoamService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.OptimizePlanService;
import com.nork.design.service.UsedProductsService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.pano.model.roam.Roam;
import com.nork.pay.metadata.PayType;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.GroupProductDetailsCache;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.result.DesignProductResult;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductServiceV2;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.ProductUsageCountService;
import com.nork.product.service.StructureProductService;
import com.nork.render.model.RenderTypeCode;
import com.nork.resource.model.ResUsedProducts;
import com.nork.resource.service.ResUsedProductsService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.dao.ResDesignRenderSceneMapper;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.dao.ResRenderVideoMapper;
import com.nork.system.dao.SysDictionaryMapper;
import com.nork.system.dao.SysDictonaryDao;
import com.nork.system.model.BaseMessage;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResDesignRenderScene;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

@Service("optimizePlanService")
@Transactional
@ClientEndpoint
public class OptimizePlanServiceImpl implements OptimizePlanService {

	private static String PASSWORD_CRYPT_KEY = Utils.getValueByFileKey(AesProperties.AES,
			AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
	private final String SERVERURL = Utils.getValue("app.server.url", "http://localhost:18080/onlineDecorate");
	private final String RESOURCESURL = Utils.getValue("app.resources.url",
			"http://localhost:18080/onlineDecorate/upload");
	public final static String APP_OPLOAD_METHOD = "app.upload.method";
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	private static Logger logger = Logger.getLogger(IntelligenceDecorationServiceImpl.class);
	@Autowired
	private OptimizePlanMapper optimizePlanMapper;

	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	private ResDesignService resDesignService;
 
	
	@Autowired
	private UsedProductsService usedProductsService;

	@Autowired
	private ResFileService resFileService;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private SysDictonaryDao sysDictonaryDao;

	@Autowired
	private BaseProductMapper baseProductMapper;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignRulesService designRulesService;

	@Autowired
	private BaseProductServiceV2 baseProductServiceV2;
	
	@Autowired
	private ResPicService resPicService;
	
	@Autowired
	private StructureProductService structureProductService;
	@Resource
	private ResModelService resModelService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private ResDesignRenderSceneMapper resDesignRenderSceneMapper;
	@Autowired
	private DesignPlanRenderService designPlanRenderService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private BaseMessageService baseMessageService;
	@Autowired
	private ProductUsageCountService productUsageCountService;
	@Autowired
	private ResUsedProductsService resUsedProductsService;
	@Autowired
	private DesignPlanRecommendedProductServiceV2 designPlanRecommendedProductServiceV2;
	@Autowired
	private AuthorizedConfigMapper authorizedConfigMapper;
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private ResDesignRenderSceneService resDesignRenderSceneService;
	@Autowired
	private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
	// add by yangzhun -->start
	@Autowired
	private ResRenderVideoService resRenderVideoService;
	@Autowired
	private ResRenderPicMapper resRenderPicMapper;
	@Autowired
	private SpaceCommonMapper spaceCommonMapper;
	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	@Autowired
	private ResRenderVideoMapper resRenderVideoMapper;
	@Autowired
	private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private DesignRenderRoamService designRenderRoamService;
	// end

	public DesignPlan createDesignPlan(DesignPlan designPlan, DesignPlanRecommended designPlanRecommended,DesignTemplet designTemplet, String mediaType,
			LoginUser loginUser) {
		designPlan.setMediaType(Utils.getIntValue(mediaType));
		designPlan.setPlanCode(designTemplet.getDesignCode() + "_" + Utils.generateRandomDigitString(6));
		designPlan.setPlanName(designTemplet.getDesignName());
		designPlan.setUserId(loginUser == null ? -1 : loginUser.getId());
		designPlan.setDesignSourceType(7);
		designPlan.setDesignId(designTemplet.getId());
		designPlan.setPicId(designTemplet.getPicId());
		designPlan.setSysCode(designTemplet.getDesignCode() + "_" + Utils.generateRandomDigitString(6));
		designPlan.setDesignTemplateId(designTemplet.getId());/* 样板房ID */
		designPlan.setSpaceCommonId(designTemplet.getSpaceCommonId());/* 适用的空间ID */
		designPlan.setDesignStyleId(null);
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
		
		/* 公共空间拷贝//u3d模型直接引用样板房的模型 */
		String modelId = designPlanService.getU3dModelId(mediaType == null ? "2" : mediaType.toString(), designTemplet);
		if (StringUtils.isNotBlank(modelId)) {
			designPlan.setModelId(Utils.getIntValue(modelId));
		} else {
			designPlan.setModelId(-1);
		}

		designPlan.setIsOpen(0);
		designPlan.setIsParent(1);
		sysSave(designPlan, loginUser);
		Integer designPlanId = this.add(designPlan);
		logger.info("add:id=" + designPlanId);
		designPlan.setId(designPlanId);
		if (designPlanId > 0) {
			return designPlan;
		} else {
			return null;
		}
	}

	/**
	 * 将设计方案配置文件信息记录到数据库中
	 */
	public boolean saveResDesign(DesignPlan designPlan, Map map) {
		if (map != null && map.size() > 0) {
			ResDesign resDesign = new ResDesign();
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resDesign.setSysCode(designPlan.getPlanCode());
			resDesign.setFileCode(designPlan.getPlanCode());
			resDesign.setGmtCreate(new Date());
			resDesign.setGmtModified(new Date());
			resDesign.setCreator(designPlan.getCreator());
			resDesign.setModifier(designPlan.getCreator());
			resDesign.setIsDeleted(0);
			resDesign.setFileName(fileName);
			resDesign.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resDesign.setBusinessId(Integer.valueOf(designPlan.getId()));
			resDesign.setFileOriginalName(
					Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			resDesign.setFilePath(dbFilePath);
			resDesign.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resDesign.setFileLevel("0");
			resDesign.setFileType("设计方案配置文件");
			Integer id = this.add(resDesign);
			if (id > 0) {
				DesignPlan newDesginPlan = new DesignPlan();
				newDesginPlan.setId(designPlan.getId());
				newDesginPlan.setConfigFileId(id);
				this.update(newDesginPlan);
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public int countOnekeyPlan(DesignPlan designPlan) {

		return optimizePlanMapper.countOnekeyPlan(designPlan);
	}

	/**
	 * 通过解析配置文件中的内容，解析json，并自动生成产品列表
	 * 
	 * @param designPlan
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public JSONObject analysisJson(Integer designTempletId, Integer recommendedPlanId, DesignPlan designPlan,
			String context, LoginUser loginUser) throws Exception {
		JSONObject resultJSON = new JSONObject();
		resultJSON.accumulate("success", false);
		StringBuffer sb = new StringBuffer("");
		String newDate = String.valueOf(System.currentTimeMillis());
		// 存结构名称->initProductCode
		Map<String, String> map = new HashMap<String, String>();
		// 配置文件解密
		try {
			if (PASSWORD_CRYPT_KEY.length() < 8) {
				PASSWORD_CRYPT_KEY = String.format("%1$0" + (8 - PASSWORD_CRYPT_KEY.length()) + "d", 0);
			} else {
				PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0, 8);
			}
			context = AESUtil2.decrypt(context, PASSWORD_CRYPT_KEY);
		} catch (Exception e1) {
			logger.error("文件解密异常:designTempletId=" + designTempletId + e1.getMessage());
			resultJSON.remove("success");
			resultJSON.accumulate("success", false);
			resultJSON.accumulate("message", "文件解密异常");
			return resultJSON;
		}
		try {
			// 读取配置文件中的内容
			// String fileContext = FileUploadUtils.getFileContext(filePath);
			// 将配置文件中的内容转换成json格式
			JSONObject jsonObject = JSONObject.fromObject(context);
			JSONArray jsonArray = (JSONArray) jsonObject.get("RoomConfig");
			JsonConfig jsonConfig = getJsonConfig("JsonProduct");
			List<JsonProduct> productCodes = (List<JsonProduct>) JSONArray.toCollection(jsonArray, jsonConfig);

			// 白膜的产品列表
			List<DesignProductResult> templetProductList = baseProductMapper.getTempletProductList(null,
					designTempletId);
			if (Lists.isEmpty(templetProductList)) {
				resultJSON.remove("success");
				resultJSON.accumulate("success", false);
				resultJSON.accumulate("message", "找不到样板房产品列表");
				return resultJSON;
			}
			// 设计方案产品列表
			/*
			 * List<DesignProductResult> planProductList =
			 * baseProductMapper.getPlanProductList(planId);
			 */
			List<DesignProductResult> planProductList = designPlanRecommendedProductServiceV2
					.getPlanProductList(recommendedPlanId);
			if (Lists.isEmpty(planProductList)) {
				resultJSON.remove("success");
				resultJSON.accumulate("success", false);
				resultJSON.accumulate("message", "找不到模板方案产品列表");
				return resultJSON;
			}

			// 组装需要保存的数据
			List<DesignPlanProduct> designProductList = new ArrayList<>();
			for (JsonProduct jsonProduct : productCodes) {
				DesignPlanProduct designProduct = new DesignPlanProduct();
				BaseProduct baseProduct = null;
				BaseProduct structureBmProduct = null;
				DesignProductResult initProduct = null;
				DesignProductResult planProduct = null;
				DesignProductResult planProduct1 = null;
				DesignProductResult planProduct2 = null;
				DesignProductResult planProduct3 = null;
				DesignProductResult wallTypePlan = null;
				DesignProductResult wallOrientationPlan = null;
				// 获取产品对象
				if (StringUtils.isNotEmpty(jsonProduct.getItemCode())) {
					baseProduct = baseProductService.findOneByCode(jsonProduct.getItemCode());
				} else {
					continue;
				}
				// 获取初始化产品对象
				if (StringUtils.isNotEmpty(jsonProduct.getInitProductCode())) {
					for (DesignProductResult templetProductResult : templetProductList) {
						if (jsonProduct.getInitProductCode().equals(templetProductResult.getProductCode())
								&& jsonProduct.getInitPosName().equals(templetProductResult.getPosName())) {
							initProduct = templetProductResult;
							break;
						}
					}
				} else {

					// 组合没有存InitProductCode
					for (DesignProductResult designProductResult : planProductList) {
						if (jsonProduct.getItemCode().equals(designProductResult.getProductCode())
								&& jsonProduct.getPlanGroupId().equals(designProductResult.getPlanGroupId())) {
							initProduct = designProductResult;
							break;
						}
					}

					// 说明plan_group_id是重新生成的,(适用于多对一/多对少),就只对比plan_group_id "_"前面的字符(group_id)
					if (initProduct == null) {
						for (DesignProductResult designProductResult : planProductList) {
							if (jsonProduct.getItemCode().equals(designProductResult.getProductCode()) && jsonProduct
									.getPlanGroupId().substring(0, jsonProduct.getPlanGroupId().indexOf("_"))
									.equals(designProductResult.getPlanGroupId().substring(0,
											designProductResult.getPlanGroupId().indexOf("_")))) {
								initProduct = designProductResult;
								break;
							}
						}
					}

				}
				// 结构才有这个属性值，结构白膜产品编码
				if (StringUtils.isNotEmpty(jsonProduct.getStructureProductCode())) {
					structureBmProduct = baseProductService.findOneByCode(jsonProduct.getStructureProductCode());
				}
				if (baseProduct != null && baseProduct.getId() > 0) {
					sysSave(designProduct, loginUser);
					designProduct.setProductId(baseProduct.getId());
					designProduct.setPlanId(designPlan.getId());
					// 通过挂节点来生成排序
					String productSequence = com.nork.common.util.StringUtils
							.replaceString(jsonProduct.getPosIndexPath(), "/");
					designProduct.setProductSequence(productSequence);
					designProduct.setPosIndexPath(jsonProduct.getPosIndexPath());
					designProduct.setPosName(jsonProduct.getPosName());
					if (baseProduct.getProductCode().startsWith("baimo_")) {
						designProduct.setIsMainProduct(initProduct.getIsMainProduct());
						designProduct.setPlanGroupId(initProduct.getPlanGroupId());
						designProduct.setProductGroupId(initProduct.getProductGroupId());
						designProduct.setGroupType(initProduct.getGroupType());
						// 3.0版本新增的字段信息
						setPlanProductInfo(designProduct, initProduct);

					} else {
						// 获取方案产品属性或样板房白膜产品属性、（同产品同白膜分类）
						for (DesignProductResult designProductResult : planProductList) {
							if (initProduct != null
									&& initProduct.getBigTypeValue().equals(designProductResult.getBigTypeValue())) {
								if (baseProduct.getId().equals(designProductResult.getProductId())) {
									// 组合产品
									if (StringUtils.isEmpty(jsonProduct.getInitProductCode()) && jsonProduct
											.getPlanGroupId().equals(designProductResult.getPlanGroupId())) {
										planProduct1 = designProductResult;
										break;
									} else {
										// 同分类同产品
										planProduct = designProductResult;
										break;
									}
								} else {
									// 同分类不同产品
									// baseProduct.getProductCode());
									if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(designProductResult.getBigTypeKey())) {
										if (initProduct.getProductGroupId() != null
												&& initProduct.getProductGroupId() > 0) {
											if (StringUtils.isNotEmpty(initProduct.getWallType()) && initProduct
													.getWallType().equals(designProductResult.getWallType())) {
												// 有结构ID则在按序号匹配
												if (initProduct.getProductIndex()
														.equals(designProductResult.getProductIndex())) {
													planProduct2 = designProductResult;
													break;
												} else {
													wallTypePlan = designProductResult;
												}
											} else {
												if (StringUtils.isNotEmpty(initProduct.getWallOrientation())
														&& initProduct.getWallOrientation()
																.equals(designProductResult.getWallOrientation())) {
													wallOrientationPlan = designProductResult;
												}
											}
										} else {
											// 不是结构组则按墙体方位和墙体类型匹配
											if (StringUtils.isNotEmpty(initProduct.getWallType())
													&& StringUtils.isNotEmpty(initProduct.getWallOrientation())
													&& initProduct.getWallType()
															.equals(designProductResult.getWallType())
													&& initProduct.getWallOrientation()
															.equals(designProductResult.getWallOrientation())) {
												planProduct2 = designProductResult;
												break;
											} else {
												if (StringUtils.isNotEmpty(initProduct.getWallType()) && initProduct
														.getWallType().equals(designProductResult.getWallType())) {
													wallTypePlan = designProductResult;
												}
												if (StringUtils.isNotEmpty(initProduct.getWallOrientation())
														&& initProduct.getWallOrientation()
																.equals(designProductResult.getWallOrientation())) {
													wallOrientationPlan = designProductResult;
												}
											}
										}
									} else if ("dzho".equals(designProductResult.getSmallTypeKey())) {
										if (StringUtils.isNotEmpty(initProduct.getWallType()) && initProduct
												.getWallType().equals(designProductResult.getWallType())) {
											planProduct2 = designProductResult;
											break;
										} else {
											wallTypePlan = designProductResult;
										}
									} else {
										planProduct3 = designProductResult;
										continue;
									}
								}
							}
						}
						if (planProduct2 == null && wallTypePlan != null) {
							planProduct2 = wallTypePlan;
						}
						if (planProduct2 == null && wallOrientationPlan != null) {
							planProduct2 = wallOrientationPlan;
						}
						if (planProduct2 == null && planProduct3 != null) {
							planProduct2 = planProduct3;
						}
						if (StringUtils.isNotEmpty(jsonProduct.getStructCode())) {
							// 结构处理
							StructureProductSearch structureProductSearch = new StructureProductSearch();
							structureProductSearch.setStructureCode(jsonProduct.getStructCode());
							structureProductSearch.setIsDeleted(0);
							structureProductSearch.setStart(0);
							structureProductSearch.setLimit(1);
							List<StructureProduct> list = structureProductService
									.getPaginatedList(structureProductSearch);
							if (list != null && list.size() > 0) {
								StructureProduct structureProduct = list.get(0);

								if (map.containsKey(jsonProduct.getInitProductCode())) {
									// 应对特殊情况:有两个一模一样的结构,plan_group_id的时间搓要更新
									newDate = map.get(jsonProduct.getInitProductCode());
								} else {
									newDate = String
											.valueOf(System.currentTimeMillis() + Utils.generateRandomDigitString(6));
								}

								designProduct.setPlanGroupId(structureProduct.getId() + "_" + newDate);
								designProduct.setProductGroupId(structureProduct.getId());
								designProduct.setGroupType(1);
								setPlanProductInfo(designProduct, initProduct);

								// 特殊处理
								map.put(jsonProduct.getInitProductCode(), newDate);

							}
						} else if (planProduct != null) {
							setPlanProductInfo(designProduct, planProduct, initProduct);
						} else if (planProduct1 != null) {
							setPlanProductInfo(designProduct, planProduct1, initProduct);
						} else if (planProduct2 != null) {
							setPlanProductInfo(designProduct, planProduct2, initProduct);
						} else {
							designProduct.setSplitTexturesChooseInfo(baseProduct.getSplitTexturesInfo());
						}
					}
				}
				// 更新绑定点信息（如背景墙绑定墙，需把背景墙信息更新到墙的BindParentProductId）
				// 如果该产品有绑定点，则去找样板房白膜绑定点关系（配置文件记录的是产品）
				JSONArray bindPointDataEx = jsonProduct.getBindPointDataEx();
				JsonConfig jc = getJsonConfig("BindPointDataEx");
				List<BindPointDataEx> bindPointDataExList = (List<BindPointDataEx>) JSONArray
						.toCollection(bindPointDataEx, jc);
				if (Lists.isNotEmpty(bindPointDataExList)) {
					// test ->start
					/*
					 * if(initProduct == null){ //System.out.println(); }
					 */
					// test ->end
					designProduct.setBindParentProductId(initProduct.getBindParentProductId());
				}
				if (structureBmProduct != null) {
					designProduct.setInitProductId(structureBmProduct.getId());
				} else if (initProduct != null) {
					designProduct.setInitProductId(initProduct.getProductId());
				} else {
					designProduct.setInitProductId(baseProduct.getId());
				}
				designProductList.add(designProduct);
			}
			String sb_ = sb.toString();
			if (sb_ != null && !"".equals(sb_)) {
				resultJSON.remove("success");
				resultJSON.accumulate("success", false);
				resultJSON.accumulate("message", sb_);
				return resultJSON;
			}
			// 排序
			ComparatorT comparatorT = new ComparatorT();
			Collections.sort(designProductList, comparatorT);

			// 保存到样板间产品表
			for (DesignPlanProduct configData : designProductList) {
				optimizePlanMapper.insertDesignPlanProductOnekey(configData);
				// designPlanProductMapper.insertSelective(configData);
				// 存储产品到已使用列表
				UsedProducts usedProducts = new UsedProducts();
				usedProducts.setCreator(designPlan.getCreator());
				usedProducts.setUserId(designPlan.getUserId());
				usedProducts.setDesignId(designPlan.getId());
				usedProducts.setProductId(configData.getProductId());
				usedProducts.setRemark("一键生成");
				sysSave(usedProducts, loginUser);
				usedProductsService.add(usedProducts);
			}
			resultJSON.remove("success");
			resultJSON.accumulate("success", true);
		} catch (Exception e) {
			/* e.printStackTrace(); */
			logger.error(e.getMessage(), e);
			resultJSON.remove("success");
			resultJSON.accumulate("success", false);
			resultJSON.accumulate("message", "更新设计方案产品列表数据异常");
			return resultJSON;
		}
		return resultJSON;
	}

	public static JsonConfig getJsonConfig(String type) {
		JsonConfig jsonConfig = new JsonConfig();
		if (type.equals("JsonProduct")) {
			jsonConfig.setRootClass(JsonProduct.class);
		} else if (type.equals("RoomConfig")) {
			jsonConfig.setRootClass(RoomConfig.class);
		} else {
			jsonConfig.setRootClass(BindPointDataEx.class);
		}
		// 处理json中key的首字母大写情况
		jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String s) {
				char[] chars = s.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		// 过滤在json中Entity没有的属性
		jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
		return jsonConfig;
	}

	private void setPlanProductInfo(DesignPlanProduct designProduct, DesignProductResult initProduct) {
		designProduct.setMeasureCode(initProduct.getMeasureCode());
		designProduct.setRegionMark(initProduct.getRegionMark());
		designProduct.setCenter(initProduct.getCenter());
		designProduct.setStyleId(initProduct.getStyleId());
		designProduct.setIsStandard(initProduct.getIsStandard());
		designProduct.setProductIndex(initProduct.getProductIndex());
		designProduct.setWallType(initProduct.getWallType());
		designProduct.setWallOrientation(initProduct.getWallOrientation());
		designProduct.setIsGroupReplaceWay(initProduct.getIsGroupReplaceWay());
		designProduct.setIsMainStructureProduct(initProduct.getIsMainStructureProduct());
	}

	private void setPlanProductInfo(DesignPlanProduct designProduct, DesignProductResult planProduct,
			DesignProductResult initProduct) {
		designProduct.setSplitTexturesChooseInfo(planProduct.getSplitTexturesChooseInfo());
		// 结构组合用的样板房结构数据
		if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(initProduct.getBigTypeKey()) && initProduct.getProductGroupId() != null
				&& initProduct.getProductGroupId() > 0) {
			designProduct.setIsMainProduct(initProduct.getIsMainProduct());
			designProduct.setPlanGroupId(initProduct.getPlanGroupId());
			designProduct.setProductGroupId(initProduct.getProductGroupId());
			designProduct.setGroupType(initProduct.getGroupType());
		} else {
			designProduct.setIsMainProduct(planProduct.getIsMainProduct());
			designProduct.setPlanGroupId(planProduct.getPlanGroupId());
			designProduct.setProductGroupId(planProduct.getProductGroupId());
			designProduct.setGroupType(planProduct.getGroupType());
		}
		setPlanProductInfo(designProduct, initProduct);
	}

	// 根据产品顺序排序（升序）
	public class ComparatorT implements Comparator {
		public int compare(Object obj1, Object obj2) {
			DesignPlanProduct unity1 = (DesignPlanProduct) obj1;
			DesignPlanProduct unity2 = (DesignPlanProduct) obj2;
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

	// 根据产品顺序排序（升序）
	public class ComparatorC implements Comparator {
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

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model, LoginUser loginUser) {
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

	private void sysSave(DesignPlan model, LoginUser loginUser) {
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

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UsedProducts model, LoginUser loginUser) {
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

	@Override
	public DesignPlan getDesignPlanByRecomendPlanIdAndTPId(Integer recommendedPlanId, Integer templateId) {
		// TODO Auto-generated method stub
		return optimizePlanMapper.getDesignPlanByRecomendPlanIdAndTPId(recommendedPlanId, templateId);
	}

	@Override
	public ResponseEnvelope<UnityDesignPlan> getUnityDesignPlan(Integer recommendedPlanId, Integer designTempletId,
			Integer isParent, Integer isAllreplace, String msgId, LoginUser loginUser, String mediaType, Integer opType)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designTemplet == null) {
			return new ResponseEnvelope<UnityDesignPlan>(false, null, "找不到样板房！", msgId);
		}
		DesignPlan designPlan = new DesignPlan();
		ResponseEnvelope<UnityDesignPlan> result = null;
		designPlan = getDesignPlanByRecomendPlanIdAndTPId(recommendedPlanId, designTempletId);
		if (designPlan == null) {
			return new ResponseEnvelope<>(true, null, "创建设计方案失败！", msgId);
		}
		if (designPlan != null && designPlan.getId() != null && isAllreplace == 1) {
			if (opType.intValue() == DesignPlanConstants.AUTO_RENDER.intValue()) {
				UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
				ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope) this
						.getDesignPlanInfo(designPlan.getId(), null, null, null, null, false, loginUser, mediaType);

				if (responseEnvelopeInfo.isSuccess()) {
					unityDesignPlan = (UnityDesignPlan) responseEnvelopeInfo.getObj();
				} else {
					result = new ResponseEnvelope<UnityDesignPlan>(false, null, "进入设计方案失败！", msgId);
				}
				result = new ResponseEnvelope<UnityDesignPlan>(true, unityDesignPlan, "找到设计方案", msgId);

			} else {
				DesignPlan newDesignPlan = new DesignPlan();
				BeanUtils.copyProperties(newDesignPlan, designPlan);
				newDesignPlan.setId(null);
				// newDesignPlan.setIsParent(0);
				// copy方案
				sysSave(newDesignPlan, loginUser);
				Integer newDesignPlanId = designPlanService.add(newDesignPlan);
				// Integer newDesignPlanId = this.add(newDesignPlan);
				// 获取配置文件
				ResDesign resDesign = null;

				if (designPlan.getConfigFileId() != null) {
					resDesign = this.get(designPlan.getConfigFileId());
				}
				// TODO copy 配置文件
				Long resultId = null;
				if (resDesign != null) {
					resultId = copyFromAutoResDesign(resDesign, newDesignPlanId);
					logger.warn("getUnityDesignPlan=>copyFromResDesign resultId=" + resultId);
					DesignPlan designPlan2 = designPlanService.get(newDesignPlanId);
					DesignPlan newDesignPlan2 = new DesignPlan();
					if (designPlan2 != null) {
						// 回填
						newDesignPlan2.setId(newDesignPlanId);
						newDesignPlan2.setConfigFileId(resultId.intValue());
						designPlanService.update(newDesignPlan2);
					}
				} else {
					result = new ResponseEnvelope<UnityDesignPlan>(false, null, "保存资源数据失败！", msgId);
				}
				boolean flagPlan = this.CopyDesignPlanProductOnekey(newDesignPlanId, designPlan, loginUser);
				if (flagPlan) {
					// 获取设计方案信息
					UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
					ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope) designPlanService
							.getDesignPlanInfo(newDesignPlanId, null, null, null, null, false, loginUser, mediaType, null);

					if (responseEnvelopeInfo.isSuccess()) {
						unityDesignPlan = (UnityDesignPlan) responseEnvelopeInfo.getObj();
					} else {
						logger.error("OptimizePlanServiceImpl=>getUnityDesignPlan=> fail to open the designPlan ID="
								+ unityDesignPlan.getDesignPlanId());
						result = new ResponseEnvelope<UnityDesignPlan>(false, null, "进入设计方案失败！", msgId);
					}
					result = new ResponseEnvelope<UnityDesignPlan>(true, unityDesignPlan, "找到设计方案", msgId);
				} else {
					result = new ResponseEnvelope<UnityDesignPlan>(true, null, "保存方案产品列表失败！", msgId);
				}
			}
		} else {
			result = new ResponseEnvelope<UnityDesignPlan>(true, null, "没有设计方案", msgId);
		}
		logger.error("getUnityDesignPlan=>msgId=" + msgId);
		return result;
	}

	public int add(DesignPlan designPlan) {
		optimizePlanMapper.insertDesignPlanOnekey(designPlan);
		return designPlan.getId();
	}

	@Override
	public ResDesign get(Integer id) {
		return optimizePlanMapper.selectByPrimaryKeyRes(id);
	}

	public long copyFromResDesign(ResDesign resDesign, long businessId) {

		if (resDesign == null) {
			logger.error("------resDesign = null");
			return 0;
		}

		if (StringUtils.isEmpty(resDesign.getFilePath())) {
			logger.error("------StringUtils.isEmpty(resDesign.getFilePath()) = true");
			return 0;
		}

		// 拷贝一份物理文件 ->start
		String oldPath = FileEncrypt.defaultUploadRoot + resDesign.getFilePath();
		File oldFile = new File(oldPath);
		if (!oldFile.exists()) {
			logger.error("------file.exists() = false,path:" + oldFile.getPath());
			return 0;
		}
		String newFileName = Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
				+ "_1" + oldPath.substring(oldPath.lastIndexOf("."));

		String newFilePath = oldPath.substring(0, oldPath.lastIndexOf("/") + 1) + newFileName;
		File newFile = new File(newFilePath);
		try {
			FileUtils.copyFile(oldFile, newFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		// 拷贝一份物理文件 ->end

		// 新建配置文件数据 ->start
		ResDesign resDesignConfig = new ResDesign();
		try {
			ClassReflectionUtils.reflectionAttr(resDesign, resDesignConfig);
		} catch (Exception e) {
			logger.error(e.toString());

			if (newFile.exists()) {
				newFile.delete();
			}

			return 0;
		}

		// 设置resDesignEctype属性
		resDesignConfig.setId(null);
		resDesignConfig.setFilePath(
				resDesign.getFilePath().substring(0, resDesign.getFilePath().lastIndexOf("/") + 1) + newFileName);
		if (businessId > 0) {
			resDesignConfig.setBusinessId((int) businessId);
		}
		Date now = new Date();
		resDesignConfig.setGmtCreate(now);
		resDesignConfig.setGmtModified(now);
		// resDesignConfig.setIsParent(1);
		resDesignConfig.setRemark("配置文件来自一键装修");

		// long resDesignId = this.add(resDesignConfig);
		long resDesignId = resDesignService.add(resDesignConfig);
		// 新建配置文件数据 ->end

		return resDesignId;
	}

	public long copyFromAutoResDesign(ResDesign resDesign, long businessId) {

		if (resDesign == null) {
			logger.error("------resDesign = null");
			return 0;
		}

		if (StringUtils.isEmpty(resDesign.getFilePath())) {
			logger.error("------StringUtils.isEmpty(resDesign.getFilePath()) = true");
			return 0;
		}

		// 拷贝一份物理文件 ->start
		String oldPath = FileEncrypt.defaultUploadRoot + resDesign.getFilePath();
		File oldFile = new File(oldPath);
		if (!oldFile.exists()) {
			logger.error("------file.exists() = false,path:" + oldFile.getPath());
			return 0;
		}
		String fileKeyNew = ResProperties.AUTO_DESIGNPLAN_U3DCONFIG_FILEKEY.replaceAll(".upload.path", "");
		String uploadUrl = Utils.getPropertyName("config/res", fileKeyNew + ".upload.path",
				"/AA/d_userdesign_auto/[YYYY]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/");
		String uploadDateUrl = Utils.replaceDate(uploadUrl);
		String newFileName = Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
				+ "_1" + oldPath.substring(oldPath.lastIndexOf("."));

		String newFilePath = uploadDateUrl + newFileName;
		File newFile = new File(newFilePath);
		try {
			FileUtils.copyFile(oldFile, newFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		// 拷贝一份物理文件 ->end

		// 新建配置文件数据 ->start
		ResDesign resDesignConfig = new ResDesign();
		try {
			ClassReflectionUtils.reflectionAttr(resDesign, resDesignConfig);
		} catch (Exception e) {
			logger.error(e.toString());

			if (newFile.exists()) {
				newFile.delete();
			}

			return 0;
		}

		// 设置resDesignEctype属性
		resDesignConfig.setId(null);
		resDesignConfig.setFilePath(newFilePath);
		resDesignConfig.setFileKey(fileKeyNew);
		if (businessId > 0) {
			resDesignConfig.setBusinessId((int) businessId);
		}
		Date now = new Date();
		resDesignConfig.setGmtCreate(now);
		resDesignConfig.setGmtModified(now);
		// resDesignConfig.setIsParent(1);
		resDesignConfig.setRemark("配置文件来自一键装修");

		// long resDesignId = this.add(resDesignConfig);
		long resDesignId = resDesignService.add(resDesignConfig);
		// 新建配置文件数据 ->end

		return resDesignId;
	}

	public long add(ResDesignRenderScene resDesignRenderScene) {
		resDesignRenderSceneMapper.insert(resDesignRenderScene);
		return resDesignRenderScene.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param resRenderVideo
	 * @return int
	 */
	@Override
	public int update(ResRenderVideo resRenderVideo) {
		return optimizePlanMapper.updateByPrimaryKeySelective(resRenderVideo);
	}

	@Override
	public int add(ResDesign resDesign) {
		optimizePlanMapper.insertResDesignOnekey(resDesign);
		return resDesign.getId();
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return DesignPlan
	 */
	@Override
	public DesignPlan getPlan(Integer id) {
		return optimizePlanMapper.selectByPrimaryKeyPlan(id);
	}

	@Override
	public int add(ResRenderVideo resRenderVideo) {
		optimizePlanMapper.insertResRenderVideoOnekey(resRenderVideo);
		return resRenderVideo.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param designPlan
	 * @return int
	 */
	@Override
	public int update(DesignPlan designPlan) {
		return optimizePlanMapper.updateByPrimaryKeyPlan(designPlan);
	}

	@Override
	public void add(List<DesignPlanProduct> designPlanProductRenderList) {
		optimizePlanMapper.insertList(designPlanProductRenderList);
	}

	public Boolean CopyDesignPlanProductOnekey(Integer id, DesignPlan designPlan, LoginUser loginUser) {
		Long startTime = System.currentTimeMillis();
		Boolean flag = false;
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setIsDeleted(0);
		designPlanProduct.setPlanId(designPlan.getId());
		List<DesignPlanProduct> designPlanList = this.getList(designPlanProduct);

		List<DesignPlanProduct> planProductList = new ArrayList<DesignPlanProduct>();
		for (DesignPlanProduct designPlanProduct1 : designPlanList) {
			DesignPlanProduct newDesignPlanProduct = new DesignPlanProduct();
			try {
				ClassReflectionUtils.reflectionAttr(designPlanProduct1, newDesignPlanProduct);
				newDesignPlanProduct.setId(null);
				newDesignPlanProduct.setPlanId(id);
				newDesignPlanProduct.setGmtModified(new Date());
				/* this.add(designPlanProductRenderScene); */
				planProductList.add(newDesignPlanProduct);
				sysSave(newDesignPlanProduct, loginUser);
			} catch (Exception e) {
				logger.error(e.toString());
				// 没有写回滚食物(或者用手动事务?)
				return false;
			}

		}
		// for (DesignPlanProduct dpp : designPlanList) {
		// DesignPlanProduct planProduct = new DesignPlanProduct();
		// planProduct.setPlanId(designPlan.getId());
		// planProduct.setWallOrientation(dpp.getWallOrientation());
		// planProduct.setWallType(dpp.getWallType());
		// planProduct.setIsMainProduct(dpp.getIsMainProduct());
		// planProduct.setIsMainStructureProduct(dpp.getIsMainStructureProduct());
		// planProduct.setProductIndex(dpp.getProductIndex());
		// planProduct.setIsGroupReplaceWay(dpp.getIsGroupReplaceWay());
		// planProduct.setIsStandard(dpp.getIsStandard());
		// planProduct.setPlanGroupId(dpp.getPlanGroupId());
		// planProduct.setCenter(dpp.getCenter());
		// planProduct.setRegionMark(dpp.getRegionMark());
		// planProduct.setStyleId(dpp.getStyleId());
		// planProduct.setMeasureCode(dpp.getMeasureCode());
		// planProduct.setDescribeInfo(dpp.getDescribeInfo());
		// planProduct.setSysCode(dpp.getSysCode());
		// planProduct.setSplitTexturesChoose(dpp.getSplitTexturesChoose());
		// planProduct.setIsDeleted(0);
		// planProduct.setGroupType(dpp.getGroupType());
		// planProduct.setModelProductId(dpp.getModelProductId());
		// planProduct.setIsDirty(dpp.getIsDirty());
		// planProduct.setProductId(dpp.getProductId());
		// planProduct.setInitProductId(dpp.getInitProductId());
		// planProduct.setLocationFileId(dpp.getLocationFileId());
		// planProduct.setProductSequence(dpp.getProductSequence());
		// planProduct.setMaterialPicId(dpp.getMaterialPicId());
		// planProduct.setAtt2(dpp.getAtt2());
		// planProduct.setAtt3(dpp.getAtt3());
		// planProduct.setAtt4(dpp.getAtt4());
		// planProduct.setAtt5(dpp.getAtt5());
		// planProduct.setAtt6(dpp.getAtt6());
		// planProduct.setDateAtt1(dpp.getDateAtt1());
		// planProduct.setDateAtt2(dpp.getDateAtt2());
		// planProduct.setPlanProductId(dpp.getPlanProductId());
		// planProduct.setIsMainProduct(dpp.getIsMainProduct());
		// planProduct.setDisplayStatus(dpp.getDisplayStatus());
		// planProduct.setNumAtt3(dpp.getNumAtt3());
		// planProduct.setNumAtt4(dpp.getNumAtt4());
		// planProduct.setRemark(dpp.getRemark());
		// planProduct.setIsHide(dpp.getIsHide());
		// planProduct.setPosIndexPath(dpp.getPosIndexPath());
		// planProduct.setPlanGroupId(dpp.getPlanGroupId());
		// planProduct.setPosName(dpp.getPosName());
		// planProduct.setBindParentProductId(dpp.getBindParentProductId());
		// planProduct.setSameProductTypeIndex(dpp.getSameProductTypeIndex());
		// sysSave(planProduct, loginUser);
		// planProductList.add(planProduct);
		// }
		// this.batchAdd(planProductList);
		Long endTime = System.currentTimeMillis();
		designPlanProductService.batchAdd(planProductList);
		flag = true;
		logger.info("复制设计方案产品耗时：" + (endTime - startTime));
		return flag;

	}

	/**
	 * 所有数据
	 * 
	 * @param designPlan
	 * @return List<DesignTempletProduct>
	 */
	@Override
	public List<DesignPlanProduct> getList(DesignPlanProduct designPlan) {
		return optimizePlanMapper.selectList(designPlan);
	}

	@Override
	public void batchAdd(List<DesignPlanProduct> planProductList) {
		if (planProductList != null && planProductList.size() > 0) {
			optimizePlanMapper.batchAdd(planProductList);
		}
	}
	
	
	/**
	 * 返回拼花结构格式
	 * @param designPlan
	 * @param unityDesignPlan
	 */
	@SuppressWarnings("unchecked")
	public void spellingFlowerData(DesignPlanModel designPlan,UnityDesignPlan unityDesignPlan,LoginUser loginUser) {
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
			List<Integer>  productPutawayStateList = this.initProductStatus(loginUser);
			productList = baseProductServiceV2.getBatchData(ids,productPutawayStateList);
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

	/**
	 * 判断该用户该环境 拥有 那些产品
	 * @param loginUser
	 */
	public List<Integer> initProductStatus(LoginUser loginUser) {
		if (loginUser == null || loginUser.getId() < 1) {
			return null;
		}

		List<Integer> productPutawayStateList = new ArrayList<Integer>(); //存放产品状态的list  用于in 查询

		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();//1为正式2为内部,默认为正式

		if (loginUser.getUserType() != null && 1 == loginUser.getUserType().intValue() && "2".equals(versionType)) {//内部环境并且内部用户才能看到测试中上架中发布中的
			productPutawayStateList.add(BaseProductPutawayState.IS_RELEASE);
			productPutawayStateList.add(BaseProductPutawayState.IS_UP);
			productPutawayStateList.add(BaseProductPutawayState.IS_TEST);
		} else {
			productPutawayStateList.add(BaseProductPutawayState.IS_RELEASE);
		}

		return productPutawayStateList;
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
	
	public Object getDesignPlanInfo(Integer designPlanId, Integer newFlag, String houseId, String livingId,
			String residentialUnitsName, Boolean isRelease, LoginUser loginUser, String mediaType) {
		String msg = "";
		if (newFlag == null) {
			newFlag = 0;
		}
		// DesignPlanModel designPlan =
		// designPlanService.selectDesignPlanInfo(designPlanId);
		DesignPlanModel designPlan = this.selectDesignPlanInfo(designPlanId);

		if (designPlan == null) {
			return new ResponseEnvelope<UnityDesignPlan>(false, msg);
		}
		// 构建unity模型加载时需要的model对象:返回设计方案,空间模型,产品列表及产品配置等信息
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		unityDesignPlan.setNewFlag(newFlag);// 是否是第一次进入

		//获取拼花信息
		this.spellingFlowerData(designPlan,unityDesignPlan,loginUser);

		// 一.获取设计方案信息
		unityDesignPlan.setServiceUrl(SERVERURL);// 访问地址
		// unityDesignPlan.setUploadDir(UPLOADDIR);//文件上传路径,资源类信息需使用访问地址+上传路径+文件路径
		unityDesignPlan.setResourcesUrl(RESOURCESURL);// 资源文件访问地址
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
		// 空间灯光（白天、黄昏、黑夜）u3d模型文件
		if (designPlan.getDaylightU3dModelId() != null && designPlan.getDaylightU3dModelId() > 0) {
			ResModel resModel = resModelService.get(designPlan.getDaylightU3dModelId());// res_model 中获取白天的模型文件
			unitySpaceCommon.setDaylightU3dModelPath(resModel == null ? "" : resModel.getModelPath());
		} else {
			unitySpaceCommon.setDaylightU3dModelPath("");
		}
		if (designPlan.getDusklightU3dModelId() != null && designPlan.getDusklightU3dModelId() > 0) {
			ResModel resModel = resModelService.get(designPlan.getDusklightU3dModelId());// res_model 中获取黄昏的模型文件
			unitySpaceCommon.setDusklightU3dModelPath(resModel == null ? "" : resModel.getModelPath());
		} else {
			unitySpaceCommon.setDaylightU3dModelPath("");
		}
		if (designPlan.getNightlightU3dModelId() != null && designPlan.getNightlightU3dModelId() > 0) {
			ResModel resModel = resModelService.get(designPlan.getNightlightU3dModelId());// res_model 中获取夜晚的模型文件
			unitySpaceCommon.setNightlightU3dModelPath(resModel == null ? "" : resModel.getModelPath());
		} else {
			unitySpaceCommon.setDaylightU3dModelPath("");
		}
		// 设计模式
		if (designPlan.getDesignTempletCode() == null) {
			msg = "designTemplet is null !designTempletId = " + designPlan.getDesignTemplateId();
			return new ResponseEnvelope<UnityDesignPlan>(false, msg);
		} else {
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
		designProductList = this.getList(designPlanProduct);// 设计方案中使用到的产品 TODO
		// if (Utils.enableRedisCache()) {
		// designProductList = DesignPlanProductCacher.getAllList2(designPlanProduct);
		// } else {
		// designProductList = this.getList(designPlanProduct);// 设计方案中使用到的产品
		// // designProductList =
		// // designPlanProductService.getList(designPlanProduct);//设计方案中使用到的产品
		// }

		if (designProductList != null && designProductList.size() > 0) {
			TreeSet<String> productTypeCodeSet = new TreeSet<String>();
			Map<String, UnityPlanProduct> unityPlanProductMap_p = new HashMap<String, UnityPlanProduct>();
			for (DesignPlanProduct planProduct : designProductList) {
				UnityPlanProduct unityPlanProduct = new UnityPlanProduct();
				unityPlanProduct.setIsDirty(planProduct.getIsDirty());
				unityPlanProduct.setPlanProductId(planProduct.getId());
				unityPlanProduct.setProductSequence(planProduct.getProductSequence());
				unityPlanProduct.setMaterialPicPaths(new String[] {});
				unityPlanProduct.setDecorationModelPath(new String[] {});
				unityPlanProduct.setPosIndexPath(planProduct.getPosIndexPath());
				unityPlanProduct.setPosName(planProduct.getPosName());
				unityPlanProduct.setBindProductId(planProduct.getBindParentProductId());

				/* 天花、地面、地面结构拆分 */
				unityPlanProduct.setIsStandard(planProduct.getIsStandard());
				unityPlanProduct.setCenter(planProduct.getCenter());
				unityPlanProduct.setRegionMark(planProduct.getRegionMark());
				unityPlanProduct.setStyleId(planProduct.getStyleId());
				unityPlanProduct.setMeasureCode(planProduct.getMeasureCode());
				unityPlanProduct.setDescribeInfo(planProduct.getDescribeInfo());
				unityPlanProduct.setProductIndex(planProduct.getProductIndex());

				unityPlanProduct.setIsGroupReplaceWay(planProduct.getIsGroupReplaceWay());
				unityPlanProduct.setIsMainStructureProduct(planProduct.getIsMainStructureProduct());

				/* 处理结构返回格式 */
				unityPlanProduct = this.getPlanProductStructureJson(unityPlanProduct, planProduct, designPlan,
						loginUser);
				// 产品的基本信息
				BaseProduct baseProduct = null;
				if (planProduct.getProductId() != null && planProduct.getProductId() > 0) {
					if (Utils.enableRedisCache()) {
						baseProduct = BaseProductCacher.get(planProduct.getProductId());
					} else {
						baseProduct = baseProductService.get(planProduct.getProductId());// 产品公用基本信息 '产品品牌', '产品风格',
																							// '产品规格', '产品颜色', '产品长度',
																							// '产品宽度', '产品高度', '销售价格',等等
					}
				}
				if (null == baseProduct) {
					msg = "planProduct.getProductId():" + planProduct.getProductId();
					return new ResponseEnvelope<UnityDesignPlan>(false, msg);
				}

				String productTypeValue = baseProduct.getProductTypeValue();
				Integer productSmallTypeValue = baseProduct.getProductSmallTypeValue();
				SysDictionary sysDictionary = new SysDictionary();
				if (baseProduct != null && productSmallTypeValue != null && StringUtils.isNotBlank(productTypeValue)) {
					// 通过大小类获取小类信息
					Map<String, Object> map = new HashMap<>();
					map.put("smallTypeValue", productSmallTypeValue);
					map.put("typeValue", productTypeValue);
					sysDictionary = sysDictionaryService.selectSmallTypeObj(map);

					if (sysDictionary != null) {
						unityPlanProduct.setMoveWay(sysDictionary.getAtt5());
					} else {
						logger.info("sDictionary is null, sd.getValuekey()=" + sysDictionary.getValuekey()
								+ ";baseProduct.getProductSmallTypeValue()=" + baseProduct.getProductSmallTypeValue()
								+ ";productid=" + baseProduct.getProductId() + ";baseProduct.getProductTypeValue()="
								+ baseProduct.getProductTypeValue());
					}
					if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getType())) {// 墙面
						unityPlanProduct
								.setBgWall(Utils.getIsBgWall(sysDictionary == null ? "" : sysDictionary.getValuekey()));
					} else {
						unityPlanProduct.setBgWall(0);
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
					// 如果该墙面有绑定关系，则取绑定产品白模长宽高
					String bindProductid = planProduct.getBindParentProductId();
					if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(sysDictionary.getType()) && StringUtils.isNotBlank(bindProductid)) {
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
						if (fullPaveLength.toString().length() > 0) {
							String fullPave = fullPaveLength.toString();
							unityPlanProduct.setFullPaveLength(
									fullPave != null ? fullPave.substring(0, fullPave.length() - 1) : fullPave);
						}
					}
					// 如果是背景墙、窗帘、淋浴屏则取白模产品的长宽高
					Integer baiMoId = planProduct.getInitProductId();
					String bjType = Utils.getValue("app.smallProductType.stretch", "");
					if (Utils.isMateProductType(sysDictionary == null ? "" : sysDictionary.getValuekey(), bjType)
							&& baiMoId != null && baiMoId.intValue() > 0) {
						BaseProduct baiMoProduct = null;
						if (Utils.enableRedisCache()) {
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

						// BaseProduct baseProduct_ = new BaseProduct();
						// baseProduct_.setId(currentProductId);
						// baseProduct_.setMediaType(mediaType);
						if (Utils.enableRedisCache()) {
							baimoProduct = BaseProductCacher.get(currentProductId);
							// baimoProduct = BaseProductCacher.getDataAndModel(baseProduct_);
						} else {
							baimoProduct = baseProductService.get(currentProductId);
							// baimoProduct = baseProductService.getDataAndModel(baseProduct_);
						}
						/* 获取不同媒介u3d模型 */
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
						ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));// 材质库
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

				/*---------------------xiaoxc  end-----*/
				/* 产品子集数量 */
				unityPlanProduct.setLeafNum(0);
				/* 标示产品在界面中的展示类型 */
				unityPlanProduct.setIsLeaf(new Integer(1));
				/* 产品是否隐藏 */
				unityPlanProduct.setIsHide(planProduct.getIsHide());

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

				/* 处理拆分材质产品的默认材质信息->end */
				UnityPlanProduct unityPlanProduct_p = unityPlanProduct.copy();
				// 产品类别信息
				if (!StringUtils.isEmpty(productTypeValue)) {
					SysDictionary sd = sysDictionaryService.getSysDictionary("productType",
							new Integer(productTypeValue));
					if (sd != null) {
						/*
						 * 为保证父节点与子节点的productTypeCode相同，指定如下规则：
						 * 子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，
						 * smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
						 * 父节点时，parentTypeCode存在值(暂时不取)，productTypeCode为节点本身信息值，因为子节点太多，
						 * 故子节点smallTyeCode为空值
						 */
						unityPlanProduct.setProductTypeValue(sd.getValue());
						unityPlanProduct.setProductTypeCode(sd.getValuekey());
						unityPlanProduct.setProductTypeName(sd.getName());

						unityPlanProduct_p.setProductTypeValue(sd.getValue());
						unityPlanProduct_p.setProductTypeCode(sd.getValuekey());
						unityPlanProduct_p.setProductTypeName(sd.getName());
						/* 获取子节点的父节点信息 */
						unityPlanProduct.setParentTypeCode(sd.getValuekey());
						unityPlanProduct.setParentTypeName(sd.getName());
						unityPlanProduct.setParentTypeValue(sd.getValue());

						unityPlanProduct_p.setParentTypeValue(-1);
						unityPlanProduct_p.setParentTypeCode("");
						unityPlanProduct_p.setParentTypeName("");

						/* 获取子节点的节点信息 */
						if (productSmallTypeValue != null && new Integer(productSmallTypeValue).intValue() > 0) {
							if (sysDictionary != null) {
								unityPlanProduct.setSmallTypeValue(sysDictionary.getValue());
								unityPlanProduct.setSmallTypeCode(sysDictionary.getValuekey());
								unityPlanProduct.setSmallTypeName(sysDictionary.getName());
								/* 是否是白模 */
								Integer isBm = 0;
								if ("baimo".equals(sysDictionary.getAtt3())) {
									isBm = 1;
									String bjType = Utils.getValue("app.smallProductType.beiJingWall", "");
									if (bjType.indexOf(sysDictionary.getValuekey()) != -1) {
										unityPlanProduct.setIsHide(1);
									}
								}
								unityPlanProduct.setIsBaimo(isBm);
								unityPlanProduct_p.setIsBaimo(isBm);
								/* 软装硬装以下规则处理，同时按最小基本的数据定义-按1硬装2软装,默认软装 */
								String rootType = StringUtils.isEmpty(sysDictionary.getAtt1()) ? "2"
										: sysDictionary.getAtt1().trim();
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

				/* 存储产品分类集合,便于组装UI界面 */
				if (!StringUtils.isEmpty(unityPlanProduct.getProductTypeCode())) {
					productTypeCodeSet.add(unityPlanProduct.getProductTypeCode());
					// 默认使用第一条记录信息做数据代入*/
					if (!unityPlanProductMap_p.containsKey(unityPlanProduct.getProductTypeCode())) {
						/*
						 * 为保证父节点与子节点的productTypeCode相同，指定如下规则：
						 * 子节点时，parentTypeCode和smallTyeCode，productTypeCode三者都存在值，
						 * smallTyeCode为本身的节点的信息值parentTypeCode与productTypeCode相等
						 * 父节点时，parentTypeCode存在值，productTypeCode为节点本身信息值，因为子节点太多，故子节点smallTyeCode为空值
						 */
						unityPlanProductMap_p.put(unityPlanProduct.getProductTypeCode(), unityPlanProduct_p);
					}
				} else {
					logger.info("unityPlanProduct.getProductTypeCode() is null ;unityPlanProduct.getProductTypeCode()="
							+ unityPlanProduct.getProductTypeCode() + ";unityPlanProduct.getProductId="
							+ unityPlanProduct.getProductId() + ";unityPlanProduct.getProductCode="
							+ unityPlanProduct.getProductCode());
				}

				Map<String, String> map = new HashMap<String, String>();
				/* 将材质的长宽也给 塞到这个list 中取 */
				String aterialPicIds = baseProduct.getMaterialPicIds();/** 材质id **/
				if (aterialPicIds != null && !"".equals(aterialPicIds)) {
					if (StringUtils.isNumeric(aterialPicIds)) {
						ResTexture resTexture = resTextureService.get(Integer.parseInt(aterialPicIds));
						if (resTexture != null) {
							unityPlanProduct.setTextureWidth(resTexture.getFileWidth() + "");
							unityPlanProduct.setTextureHeight(resTexture.getFileHeight() + "");
						}
					}
				}
				/* 在组合产品查询列表 中 增加产品属性 */
				map = productAttributeService.getPropertyMap(baseProduct.getId());// 产品属性
				unityPlanProduct.setPropertyMap(map);

				// 关联白模产品的属性
				Map<String, String> basicPropertyMap = new HashMap<>();
				basicPropertyMap = productAttributeService.getPropertyMap(planProduct.getInitProductId());
				unityPlanProduct.setBasicPropertyMap(basicPropertyMap);

				/* 样板房产品ID */
				unityPlanProduct.setTemplateProductId(
						planProduct.getInitProductId() == null ? "" : planProduct.getInitProductId().toString());

				/* 组装产品的规则 */
				String productTypeCode = unityPlanProduct.getProductTypeCode();/* 产品大类 */
				String productSmallTypeCode = unityPlanProduct.getSmallTypeCode();/* 产品小类 */
				String productId = null;// 产品ID
				if (unityPlanProduct.getProductId() != null) {
					productId = unityPlanProduct.getProductId().toString();/* 产品ID */
				}

				/* 获取规则 */
				Map<String, String> rulesMap = new HashMap<>();
				Map<Object, Object> rulesParamsMap = new HashMap<>();
				rulesParamsMap.put("rulesProductId", productId);
				ResponseEnvelope rulesResponseMapResult = null;
				if (Utils.enableRedisCache()) {
					rulesResponseMapResult = CommonCacher.getAll(ModuleType.DesignPlan, "getRulesSecondaryList",
							rulesParamsMap);
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
			/* 大循环的结束括号 */

			// 四.定制装修导航(在产品列表中该增加大按钮性质,同时,只有一个时,删除小按钮)
			// 组装每个产品的分类信息
			this.getDecorationNavigationInfo(unityPlanProductList, newUnityPlanProductList, productTypeCodeSet,
					unityPlanProductMap_p);
		}
		ComparatorC cpmparator = new ComparatorC();
		Collections.sort(newUnityPlanProductList, cpmparator);
		unityDesignPlan.setDatalist(newUnityPlanProductList);//
		ResponseEnvelope responseEnvelope = new ResponseEnvelope<UnityDesignPlan>(true, unityDesignPlan);

		return responseEnvelope;

	}

	public UnityPlanProduct getPlanProductStructureJson(UnityPlanProduct unityPlanProduct,
			DesignPlanProduct planProduct, DesignPlanModel designPlan, LoginUser loginUser) {
		int groupId = planProduct.getProductGroupId() == null ? 0 : planProduct.getProductGroupId();
		int isMain = planProduct.getIsMainProduct() == null ? 0 : planProduct.getIsMainProduct();
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
			if (unityPlanProduct.getProductStructureId() != null && unityPlanProduct.getProductStructureId() > 0) {

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

		// 如果该产品是主产品，则返回该方案产品组合的方案产品ID数据
		GroupProductDetails groupProductDetails = new GroupProductDetails();
		// if( isMain == 1 || groupId > 0 ){
		if (groupId > 0) {
			DesignPlanProduct planProducts = new DesignPlanProduct();
			planProducts.setProductGroupId(groupId);
			planProducts.setPlanId(designPlan.getDesignPlanId());
			planProducts.setGroupType(groupType);
			planProducts.setIsDeleted(0);
			List<DesignPlanProduct> dppList = null;
			if (Utils.enableRedisCache()) {
				dppList = DesignPlanProductCacher.getAllList2(planProducts);
			} else {
				// dppList = designPlanProductService.getList(planProducts);
				dppList = this.getList(planProducts);
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

			Integer userType = loginUser.getUserType();
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(1);
			if (userType == 1) {
				statusList.add(2);
			}
			groupProductDetails.setStatusList(statusList);
			/* 查询条件设置组合查询的state(根据用户类型,内部用户能查到测试和上架的组合,其他用户只能查到上架的组合)->end */
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
		return unityPlanProduct;
	}

	@Override
	public List<UnityPlanProduct> getDecorationNavigationInfo(List<UnityPlanProduct> unityPlanProductList,
			List<UnityPlanProduct> newUnityPlanProductList, TreeSet<String> productTypeCodeSet,
			Map<String, UnityPlanProduct> unityPlanProductMap_p) {
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
		return newUnityPlanProductList;
	}

	@Override
	public DesignPlanModel selectDesignPlanInfo(Integer id) {
		return optimizePlanMapper.selectDesignPlanInfo(id);
	}

	/**
	 * 通过配置内容修改设计方案产品
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean updatePlanProductByConfig(String context, Integer planId) {
		boolean res = updatePlanProductByConfig(context, planId, ContextType.CONTEXT_TYPE_OF_PRODUCT);
		if (res) {
			// 如果是渲染的场景对应的临时方案发生改变，此时需要把该方案转为正式的，用户可见的
			boolean invisible = this.isInvisible4Render(planId);
			if (invisible) {
				this.changeTempDesignPalnVisible(planId);
			}
		}
		return res;
	}

	/**
	 * 通过配置内容修改设计方案产品
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean updatePlanProductByConfig(String context, Integer planId, String contextType) {
		if (ContextType.CONTEXT_TYPE_OF_STRUCTURE.equals(contextType)) {

		} else if (ContextType.CONTEXT_TYPE_OF_PRODUCT.equals(contextType)) {
			// 配置文件解密
			try {
				if (PASSWORD_CRYPT_KEY.length() < 8) {
					PASSWORD_CRYPT_KEY = String.format("%1$0" + (8 - PASSWORD_CRYPT_KEY.length()) + "d", 0);
				} else {
					PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0, 8);
				}
				logger.info("文件解密前截取长度为10参数:context=" + context.substring(0, 10));
				logger.info("文件解密key=" + PASSWORD_CRYPT_KEY);
				context = AESUtil2.decrypt(context, PASSWORD_CRYPT_KEY);
			} catch (Exception e1) {
				logger.error("文件解密异常,contextType=" + contextType + ",:planId=" + planId + e1.getMessage());
				return false;
			}
		} else if (ContextType.CONTEXT_TYPE_OF_GROUP.equals(contextType)) {

		} else {
			logger.error("更新配置文件未识别文档类型:" + contextType);
		}

		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(context);
		} catch (Exception e) {
			logger.error(
					"配置文件context异常,无法转换为json串context=" + context + ";\n" + "planId=" + planId + ";\n" + e.getMessage());
			return false;
		}
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) jsonObject.get("RoomConfig");
		} catch (Exception e) {
			logger.error("配置文件获取RoomConfig异常,无法转换为json串context=" + context + ";\n" + "jsonArray=" + jsonArray + ";\n"
					+ "jsonObject=" + jsonObject + ";\n" + "planId=" + planId + ";\n" + e.getMessage());
			return false;
		}
		if (jsonArray == null || jsonArray.size() < 1) {
			return true;
		}
		try {
			List<RoomConfig> roomConfigs = (List<RoomConfig>) JSONArray.toCollection(jsonArray,
					getJsonConfig("RoomConfig"));
			List<DesignPlanProduct> planProductList = new ArrayList<>();
			DesignPlanProduct designPlanProduct = null;
			ItemExt itemExt = null;
			Integer planProductId = 0;
			int updateState = 0;/** 大于0说明用户有操作，更新设计方案修改时间，否则只是进入 无需修改时间 */
			for (RoomConfig roomConfig : roomConfigs) {
				designPlanProduct = new DesignPlanProduct();
				planProductId = roomConfig.getPlanProductId();
				if (planProductId != null) {
					// TODO designPlanProduct = designPlanProductService.get(planProductId);
					designPlanProduct = this.getPlanProduct(planProductId);
					if (designPlanProduct != null) {
						DesignPlanProduct newPlanProduct = new DesignPlanProduct();
						newPlanProduct.setId(designPlanProduct.getId());
						/** 更新挂节点 */
						boolean flag = false;
						logger.info("===============数据库PosIndexPath = " + designPlanProduct.getPosIndexPath()
								+ "; 配置文件PosIndexPath = " + roomConfig.getPosIndexPath());
						if (!designPlanProduct.getPosIndexPath().equals(roomConfig.getPosIndexPath())) {
							newPlanProduct.setPosIndexPath(roomConfig.getPosIndexPath());
							// designPlanProductService.update(designPlanProduct);
							flag = true;
							updateState = updateState + 1;
						}
						/** 更新挂节点名称 */
						if (roomConfig.getPosName() != null) {
							if (!roomConfig.getPosName().equals(designPlanProduct.getPosName())) {
								newPlanProduct.setPosName(roomConfig.getPosName());
								// designPlanProductService.update(designPlanProduct);
								flag = true;
								updateState = updateState + 1;
							}
						}
						if (flag) {
							// TODO designPlanProductService.update(newPlanProduct);
							this.updatePlanProduct(newPlanProduct);
							logger.info("===============通过配置内容更新设计方案产品列表：planProductId = " + designPlanProduct.getId()
									+ "; productId = " + designPlanProduct.getProductId() + "; posIndexPath = "
									+ designPlanProduct.getPosIndexPath());
						}
						/** 更新绑定点信息（如背景墙绑定墙，需把背景墙信息更新到墙的BindParentProductId） */
					}
				}
			}
			DesignPlan designPlan = null;
			ResDesign resDesign = null;
			// TODO designPlan = designPlanService.get(planId);
			designPlan = this.getPlan(planId);
			if (designPlan != null) {
				// TODO resDesign = resDesignService.get(designPlan.getConfigFileId());
				resDesign = this.get(designPlan.getConfigFileId());
				if (resDesign != null) {
					ResDesign newResDesign = new ResDesign();
					newResDesign.setId(resDesign.getId());
					newResDesign.setGmtModified(new Date());
					// TODO resDesignService.update(newResDesign);
					this.updateRes(newResDesign);
				}
			}
			if (designPlan == null) {
				return false;
			}

			// if(updateState>0){
			DesignPlan newDesignPlan = new DesignPlan();
			newDesignPlan.setId(planId);
			newDesignPlan.setGmtModified(new Date());
			newDesignPlan.setSceneModified(Utils.getCurrentTimeMillis());// 设计方案的配置文件发生改变要更新时间戳
			// newDesignPlan.setIsRelease(DesignPlanRelease.stateless);//发布中的方案一旦被编辑，那么变成取消发布，但保留品牌绑定关系
			// TODO designPlanService.update(newDesignPlan);
			this.update(newDesignPlan);
			// }
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "\n" + "error===context=" + context + ";planId=" + planId);
			return false;
		}
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return DesignPlanProduct
	 */
	@Override
	public DesignPlanProduct getPlanProduct(Integer id) {
		return optimizePlanMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新数据
	 *
	 * @param designPlanProduct
	 * @return int
	 */
	@Override
	public int updatePlanProduct(DesignPlanProduct designPlanProduct) {
		/* 删除 进入该样板房的缓存 */
		Map<Object, Object> paramsMap = new HashMap<>();
		paramsMap.put("designPlanId", designPlanProduct.getDesignPlanId());
		if (Utils.enableRedisCache()) {
			CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);
		}
		return optimizePlanMapper.updateByPrimaryKeyPlanProduct(designPlanProduct);
	}

	@Override
	public int updateRes(ResDesign resDesign) {
		return optimizePlanMapper.updateByPrimaryKeyRes(resDesign);
	}

	/**
	 * 新增数据
	 *
	 * @param designPlanProduct
	 * @return int
	 */
	@Override
	public int addPlanProduct(DesignPlanProduct designPlanProduct) {
		/* 删除 进入该样板房的缓存 */
		Map<Object, Object> paramsMap = new HashMap<>();
		paramsMap.put("designPlanId", designPlanProduct.getDesignPlanId());
		if (Utils.enableRedisCache()) {
			CommonCacher.removeAll(ModuleType.DesignPlan, "getDesignPlanWeb", paramsMap);
		}
		optimizePlanMapper.insertDesignPlanProductOnekey(designPlanProduct);
		return designPlanProduct.getId();
	}

	/**
	 * @param designPlan
	 * @param params
	 *            add by yanghz @Description: 保存渲染任务参数（新渲染系统） @return void
	 *            返回类型 @throws
	 */
	@Override
	public String saveRenderParams(String params, DesignPlan designPlan) {
		// 预解压文件的路径
		String renderResourcesPath = Utils.getPropertyName("render", "render.renderResourcesPath",
				"/home/nork/MaxRender/resources");
		String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
				+ Utils.generateRandomDigitString(6);
		/** 先把文件保存到本地 */
		Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
		String taskConfigUploadPath = Utils.getPropertyName("render", "taskConfig.upload.path",
				"/system/sysTask/taskConfig/") + designPlan.getPlanCode() + "/";
		String fileName = rootDirName + ".txt";
		// String filePath = Constants.UPLOAD_ROOT + taskConfigUploadPath + fileName;
		String filePath = Utils.getAbsolutePath(taskConfigUploadPath + fileName, null);

		// 换成预解压文件的路径
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath, params);
		if (uploadFtpFlag) {
			/** 上传方式为2或者3表示文件在ftp上 */
			if (ftpUploadMethod == 2) {
				uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, taskConfigUploadPath);
				if (uploadFtpFlag) {
					/** 删除本地文件 */
					FileUploadUtils.deleteDir(new File(filePath));
				} else {
					logger.error("生成新渲染系统任务参数文件失败");
				}
			} else if (ftpUploadMethod == 3) {
				uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, taskConfigUploadPath);
				if (!uploadFtpFlag) {
					logger.error("生成新渲染系统任务参数文件失败");
				}
			}
		} else {
			logger.error("生成新渲染系统任务参数文件失败");
		}
		return taskConfigUploadPath + fileName;
	}

	public ResPic assembleResPic(Map map) {
		ResPic resPic = new ResPic();
		if (map.get(FileModel.PIC_WEIGHT) != null) {
			resPic.setPicWeight(map.get(FileModel.PIC_WEIGHT).toString());
		}
		if (map.get(FileModel.PIC_HEIGHT) != null) {
			resPic.setPicHigh(map.get(FileModel.PIC_HEIGHT).toString());
		}
		if (map.get(FileModel.FILE_KEY) != null) {
			resPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
		}
		if (map.get(FileModel.FILE_NAME) != null) {
			resPic.setPicName(map.get(FileModel.FILE_NAME).toString());
		}
		if (map.get(FileModel.FILE_ORIGINAL_NAME) != null) {
			resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		}
		if (map.get(FileModel.FILE_SUFFIX) != null) {
			resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		}
		if (map.get(FileModel.FILE_SIZE) != null) {
			resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE).toString()));
		}
		if (map.get(FileModel.FORMAT) != null) {
			resPic.setPicFormat(map.get(FileModel.FORMAT).toString());
		}
		return resPic;
	}

	public ResRenderPic assembleResRenderPic(Map map) {
		ResRenderPic renderPic = new ResRenderPic();
		if (map.get(FileModel.PIC_WEIGHT) != null) {
			renderPic.setPicWeight(Integer.getInteger((map.get(FileModel.PIC_WEIGHT).toString())));
		}
		if (map.get(FileModel.PIC_HEIGHT) != null) {
			renderPic.setPicHigh(Integer.getInteger((map.get(FileModel.PIC_HEIGHT).toString())));
		}
		if (map.get(FileModel.FILE_KEY) != null) {
			renderPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
		}
		if (map.get(FileModel.FILE_NAME) != null) {
			renderPic.setPicName(map.get(FileModel.FILE_NAME).toString());
		}
		if (map.get(FileModel.FILE_ORIGINAL_NAME) != null) {
			renderPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		}
		if (map.get(FileModel.FILE_SUFFIX) != null) {
			renderPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		}
		if (map.get(FileModel.FILE_SIZE) != null) {
			renderPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE).toString()));
		}
		if (map.get(FileModel.FORMAT) != null) {
			renderPic.setPicFormat(map.get(FileModel.FORMAT).toString());
		}
		if (map.get(FileModel.FILE_PATH) != null) {
			renderPic.setPicPath(map.get(FileModel.FILE_PATH).toString());
		}
		return renderPic;
	}

	/**
	 * 渲染成功/失败发送消息
	 * 
	 * @param task
	 * @throws Exception
	 */
	public Integer sendRenderMessage(SysTask task, Integer state, Integer picId) throws Exception {
		logger.info("准备发送websocket消息......");
		Integer messageId = 0;
		// 失败之后发送信息
		Integer userId = null;
		DesignPlan designPlan_ = null;
		designPlan_ = getPlan(task.getBusinessId());
		// designPlan_=designPlanService.get(task.getBusinessId());
		if (designPlan_ != null) {
			userId = designPlan_.getUserId();
		}
		SysUser sysUser = sysUserService.get(userId);
		if (sysUser != null) {
			logger.info(task.getExecCount() + "+++++++++++++++++++++++++++++--------------------------");
			BaseMessage baseMessage = new BaseMessage();
			baseMessage.setUserId(userId);
			// baseMessage.setBusinessTypeId(
			// task.getRenderType() == null ? RenderTypeCode.COMMON_720_LEVEL :
			// task.getRenderType());
			baseMessage.setBusinessTypeId(5);
			baseMessage.setBusinessObjId(designPlan_.getId());
			baseMessage.setBusinessObjType("design_plan");
			baseMessage.setMessageType(0);
			baseMessage.setCreator(sysUser.getNickName());
			baseMessage.setGmtCreate(new Date());
			baseMessage.setModifier(sysUser.getNickName());
			baseMessage.setGmtModified(new Date());
			baseMessage.setIsDeleted(0);
			if (picId != null && picId > 0) {
				baseMessage.setNuma1(picId);
			}
			if (state == 2) {
				if (task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL) {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode()
							+ "</color>" + ProductType.PANORAMA_RENDER_NAME + "成功");
				} else if (task.getRenderType().intValue() == RenderTypeCode.ROAM_720_LEVEL) {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode()
							+ "</color>" + ProductType.ROAM_PANORAMA_RENDER_NAME + "成功");
				} else {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode()
							+ "</color>" + ProductType.PICTURE_RENDER_NAME + "成功");
				}
			} else {
				if (task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL) {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode()
							+ "</color>" + ProductType.PANORAMA_RENDER_NAME + "失败,请联系客服,渲染费用已经退到您的账户余额");
				} else if (task.getRenderType().intValue() == RenderTypeCode.ROAM_720_LEVEL) {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode()
							+ "</color>" + ProductType.ROAM_PANORAMA_RENDER_NAME + "失败,请联系客服,渲染费用已经退到您的账户余额");
				} else {
					baseMessage.setContent("<color='#01017A'>" + designPlan_.getPlanName() + designPlan_.getPlanCode()
							+ "</color>" + ProductType.PICTURE_RENDER_NAME + "失败,请联系客服,渲染费用已经退到您的账户余额");
				}
			}
			messageId = baseMessageService.add(baseMessage);

			BaseMessageRecieve baseMessageRecieve = new BaseMessageRecieve();
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
		logger.info("=======================任务状态：" + state + "        " + (state.intValue() == 2));

		String houseName = "";
		SpaceCommon spaceCommon = null;
		spaceCommon = spaceCommonService.get(designPlan_.getSpaceCommonId());
		if (spaceCommon != null) {
			Integer spaceFunctionId = spaceCommon.getSpaceFunctionId();
			if (spaceFunctionId != null) {
				SysDictionary sysDictionary = new SysDictionary();
				sysDictionary.setValue(spaceFunctionId);
				sysDictionary.setType("houseType");
				sysDictionary.setIsDeleted(0);
				List<SysDictionary> houseNameList = sysDictionaryService.getList(sysDictionary);
				if (houseNameList != null && houseNameList.size() == 1) {
					houseName = houseNameList.get(0).getName();
				}
			}
		}
		String renderTypeName = "";
		if (task.getRenderType() != null) {
			if (task.getRenderType().intValue() == RenderTypeCode.SCREEN_OF_PIC) {
				renderTypeName = ",高清图片";
			} else if (task.getRenderType().intValue() == RenderTypeCode.COMMON_PICTURE_LEVEL) {
				renderTypeName = ",照片级普通 ";
			} else if (task.getRenderType().intValue() == RenderTypeCode.HD_PICTURE_LEVEL) {
				renderTypeName = ",照片级高清";
			} else if (task.getRenderType().intValue() == RenderTypeCode.ULTRA_HD_PICTURE_LEVEL) {
				renderTypeName = ",照片级超高清";
			} else if (task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL) {
				renderTypeName = ",720度普通";
			} else if (task.getRenderType().intValue() == RenderTypeCode.HD_720_LEVEL) {
				renderTypeName = ",720度高清";
			} else if (task.getRenderType().intValue() == RenderTypeCode.COMMON_VIDEO) {
				renderTypeName = ",普通漫游视频";
			} else if (task.getRenderType().intValue() == RenderTypeCode.HD_VIDEO) {
				renderTypeName = ",高清漫游视频";
			} else if (task.getRenderType().intValue() == RenderTypeCode.ROAM_720_LEVEL) {
				renderTypeName = ",多点全景";
			}
		}

		if (state.intValue() == 2) {
			messageResponse.setSuccess(true);
			// messageResponse.setMessage("渲染成功!");
			String message = houseName + designPlan_.getPlanCode() + renderTypeName + "渲染成功！";
			messageResponse.setMessage(message);

		} else {
			messageResponse.setSuccess(false);
			// messageResponse.setMessage("渲染失败!");
			String msg = "渲染失败,本次扣款将退回到您的账户里！";
			PayOrder findOneByTaskId = payOrderService.findOneByTaskId(task.getId());
			if (findOneByTaskId != null) {
				if (findOneByTaskId.getTotalFee() != null && findOneByTaskId.getTotalFee() == 0
						&& PayType.PREDEPOSIT == findOneByTaskId.getPayType()) {
					msg = "渲染失败";
				}
			}
			String message = houseName + designPlan_.getPlanCode() + renderTypeName + msg;
			messageResponse.setMessage(message);
		}
		messageResponse.setType(1);
		Map<String, String> map = new HashMap<String, String>();
		map.put("designPlanId", designPlan_.getId() + "");
		messageResponse.setObj(map);
		JSONObject jsonObject = JSONObject.fromObject(messageResponse);
		String message = jsonObject.toString();
		// noticeWsServer(userId+"",message);
		try {
			WebSocketUtils.sendMessage("app.webSocket.message", userId.toString(), message);
		} catch (Exception e) {
			logger.error("message websocket链接异常" + e);
			// 发送邮件
			List<SysUser> warningUserList = sysUserService.getUserByRoleCode("RENDER_WARNING");
			if (warningUserList != null && warningUserList.size() > 0) {
				StringBuffer toEmailsStr = new StringBuffer();
				int count = 0;
				for (SysUser warningUser : warningUserList) {
					if (com.nork.common.util.StringUtils.isNotBlank(warningUser.getEmail())) {
						if (count < warningUserList.size()) {
							toEmailsStr.append(warningUser.getEmail() + ",");
						} else {
							toEmailsStr.append(warningUser.getEmail());
						}
						count++;
					}
				}
				if (toEmailsStr.length() > 0) {
					String[] toEmails = toEmailsStr.toString().split(",");
					StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
					stringBuffer
							.append("websocket服务器可能已中断," + WebSocketUtils.webSocket.getString("app.webSocket.message"));
					String subject = "【websocket链接异常】";
					SendEmail.massSend(toEmails, subject, stringBuffer.toString());
				}
			} else {
				logger.error("warningUserList is null");
			}
		}
		return messageId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nork.design.service.DesignPalnRenderService#changeTempDesignPalnVisible(
	 * long)
	 */
	@Override
	public void changeTempDesignPalnVisible(long planId) {

		if (planId < 1) {
			logger.error("------function:changeTempDesignPalnVisible->planId不能小于0;(planId = " + planId + ")");
			return;
		}

		DesignPlan designPlan = this.getPlan((int) planId);
		if (designPlan == null) {
			logger.error("------function:changeTempDesignPalnVisible->designPlan = null;(planId = " + planId + ")");
			return;
		}

		designPlan.setVisible(PlanVisibleCode.DESIGN_VISIBLE);
		this.update(designPlan);
	}

	// ======================分界线=====================================

	/**
	 * 更新设计方案
	 * 
	 * @param desPlan
	 *            方案对象
	 * @param designPlanId
	 *            方案ID
	 * @param planProductId
	 *            方案产品Id
	 * @param productId
	 *            产品id
	 * @param materialPicId
	 *            材质
	 * @return boolean
	 */
	public boolean updateDesignPlan(DesignPlan desPlan, Integer designPlanId, Integer planProductId, Integer productId,
			String materialPicId, String context, HttpServletRequest request, String splitTexturesChoose)
			throws InvocationTargetException, IllegalAccessException {
		try {
			// 替换方案产品列表中的数据
			// DesignPlanProduct designPlanProduct =
			// designPlanProductService.get(planProductId);
			DesignPlanProduct designPlanProduct = this.getPlanProduct(planProductId);
			Integer oldProductId = designPlanProduct.getProductId();
			BaseProduct newProduct = baseProductService.get(productId);

			/* 如果产品一样 */
			if (oldProductId.equals(productId)) {
				/* 保存时,当oldProductId和productId相等,则识别为:可拆分材质产品保存材质 */

				if (StringUtils.isNotBlank(newProduct.getSplitTexturesInfo())) {
					/* 识别为该产品是可拆分材质产品 */
					if (StringUtils.isBlank(splitTexturesChoose)) {
						/* 识别为该产品是替换操作,并没有选择材质->赋予默认材质 */
						designPlanProduct.setSplitTexturesChooseInfo(newProduct.getSplitTexturesInfo());
					} else {
						/* 保存用户选择的材质 */
						// JSONArray jsonArray=JSONArray.fromObject(designPlanProduct)
						String oldSplitTexturesChooseInfo = "";
						if (StringUtils.isBlank(designPlanProduct.getSplitTexturesChooseInfo())) {
							/* 从产品表中查找默认材质信息 */
							oldSplitTexturesChooseInfo = newProduct.getSplitTexturesInfo();
						} else {
							oldSplitTexturesChooseInfo = designPlanProduct.getSplitTexturesChooseInfo();
						}
						JSONArray jsonArray = JSONArray.fromObject(oldSplitTexturesChooseInfo);
						@SuppressWarnings({ "unchecked", "deprecation" })
						List<SplitTextureInfoDTO> splitTextureDTOList = (List<SplitTextureInfoDTO>) JSONArray
								.toList(jsonArray, SplitTextureInfoDTO.class);
						List<String> list = Utils.getListFromStr2(splitTexturesChoose, ";");
						/* 循环修改 key 相等的 默认材质id */
						for (String str : list) {
							// 更新方案产品列表相同产品的多材质信息
							/*
							 * boolean flag =
							 * updateSameProductTextureInfo(designPlanId,planProductId,newProduct,str);
							 * if(flag){
							 * splitTextureDTOList=this.updateSplitTexture(splitTextureDTOList,str); }
							 */
							splitTextureDTOList = this.updateSplitTexture(splitTextureDTOList, str);
						}
						JSONArray jsonArray2 = JSONArray.fromObject(splitTextureDTOList);
						designPlanProduct.setSplitTexturesChooseInfo(jsonArray2.toString());
					}
				}
			} else {
				designPlanProduct.setSplitTexturesChooseInfo(
						newProduct.getSplitTexturesInfo() == null ? "" : newProduct.getSplitTexturesInfo());
			}
			designPlanProduct.setProductId(productId);
			designPlanProduct.setMaterialPicId(materialPicId);
			String productSequence = com.nork.common.util.StringUtils.replaceString(designPlanProduct.getPosIndexPath(),
					"/");
			/* 顺序 */
			designPlanProduct.setProductSequence(productSequence);
			// 替换时，产品显示
			designPlanProduct.setIsHide(0);
			// 媒介类型.如果没有值，则表示为web前端（2）
			String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);
			/*
			 * 识别要替换的产品有没有模型,有->替换modelProductId为要替换的产品id;没有->识别modelProductId是不是为空,
			 * 为空则为原产品id,有则不变
			 */
			String modelId = baseProductService.getU3dModelId(mediaType, newProduct);
			//// //System.out.println("替换贴图存当前模型---------"+productId +"+++"+modelId);
			if (newProduct != null && StringUtils.isNotBlank(modelId) && Utils.getIntValue(modelId) > 0) {
				/* 模型不为空->modelProductId储存新的产品id */
				// 如果该产品是白膜,则同时更新initProductId字段
				if (StringUtils.isNotBlank(newProduct.getProductCode())
						&& newProduct.getProductCode().startsWith("baimo_")) {
					designPlanProduct.setInitProductId(newProduct.getId());
				}
				designPlanProduct.setModelProductId(newProduct.getId());
			}
			// else{
			// if(designPlanProduct.getModelProductId()!=null&&designPlanProduct.getModelProductId()>0){
			// /*不变*/
			// }else{
			// designPlanProduct.setModelProductId(oldProductId);
			// }
			// }
			/*
			 * 识别要替换的产品有没有模型,有->替换modelProductId为要替换的产品id;没有->识别modelProductId是不是为空,
			 * 为空则为原产品id,有则不变->end
			 */
			/* 如果是可拆分材质产品,则保存选择的材质信息 */

			/* 如果是可拆分材质产品,则保存选择的材质信息->end */
			sysSave(designPlanProduct, request);
			// designPlanProductMapper.updateByPrimaryKeySelective(designPlanProduct);
			this.updatePlanProduct(designPlanProduct);
			if (Utils.enableRedisCache()) {
				DesignPlanProductCacher.remove(designPlanProduct.getId());
			}
			/* 更新产品使用次数表 */
			/* designPlanProduct:productId=909; */
			Integer userId = desPlan.getUserIdTemp();
			if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0 && userId != null
					&& userId > 0)
				productUsageCountService.update(userId, designPlanProduct.getProductId(), 1);
			/* 更新产品使用次数表->end */

			// 根据产品列表更新装修风格

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
				// 更新设计方案设置是否变更为1，是否装修过1，是否公开为0，
				designPlan.setIsChange(1);
				designPlan.setIsDecorated(1);
				designPlan.setIsOpen(0);
				designPlan.setSceneModified(Utils.getCurrentTimeMillis());// 设计方案的配置文件发生改变要更新时间戳
				sysSave(designPlan, request);
				this.update(designPlan);
				// designPlanMapper.updateByPrimaryKeySelective(designPlan);
				if (Utils.enableRedisCache()) {
					DesignCacher.removePlan(designPlan.getId());
				}
			}

			/* 新增一条用户使用产品记录 */
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
				// usedProducts.setAtt1(context);
				/** sysSave(usedProducts, request); */
				usedProducts.setSysCode(
						Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				int id = usedProductsService.add(usedProducts);
				if (Utils.enableRedisCache()) {
					UsedProductsCacher.remove(id);
				}
				/*
				 * UsedProfiles usedProfiles = new UsedProfiles(); usedProfiles.setUsedId(id);
				 * usedProfiles.setTextContent(context);//配置文件文本信息 int filesId =
				 * usedProfilesService.add(usedProfiles);
				 */

				// logger.info("usedProducts add:id=" + id);
				// logger.info("usedProfiles add:filesId=" + filesId);

				/* 将该样板房的 应使用产品位置 坐标信息保存下到本地，上传到自定路径。，并且保存 */
				// 配置内容存储到文件中
				/** TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。 **/
				Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
				boolean uploadFtpFlag = false;
				// 已使用配置内容存储路径
				// String usedConfigPath =
				// Utils.getValue("design.designPlan.usedConfig.upload.path","/design/designPlan/[code]/usedConfig/");
				// usedConfigPath = usedConfigPath.replace("[code]",desPlan.getPlanCode());
				String usedConfigPath = Utils.getValue("design.designPlan.usedConfig.upload.path",
						"/AA/e_userlogs/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/usedConfig/");
				usedConfigPath = Utils.replaceDate(usedConfigPath);
				// 文件名称
				String fileName = id + ".txt";
				// 先把文件保存到本地
				// String filePath = Constants.UPLOAD_ROOT + usedConfigPath + fileName;
				String filePath = Utils.getAbsolutePath(usedConfigPath + fileName, null);
				uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath, context);
				// 获取文件大小
				Map map = FileUploadUtils.getMap(new File(filePath), "design.designPlan.usedConfig.upload.path", false);
				String fileSize = map.get(FileModel.FILE_SIZE).toString();
				if (uploadFtpFlag) {
					// 上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 2) {
						uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
					} else if (ftpUploadMethod == 3) {

						uploadFtpFlag = FtpUploadUtils.uploadFile(fileName, filePath, usedConfigPath);
						// 删除本地文件
						// FileUploadUtils.deleteDir(new File(Constants.UPLOAD_ROOT +
						// usedConfigPath).getParentFile());

					}
				}
				/* 已使用产品资源记录 start */
				ResUsedProducts resUsedProducts = new ResUsedProducts();
				resUsedProducts.setFileCode(designPlanProduct.getSysCode());
				resUsedProducts.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
				resUsedProducts.setFileOriginalName(fileName);
				// resUsedProducts.setFileType(fileType);
				resUsedProducts.setFileSize(fileSize);
				resUsedProducts.setFileSuffix(fileName.substring(fileName.lastIndexOf(".")));
				// resUsedProducts.setFileLevel();
				resUsedProducts.setFilePath(usedConfigPath + fileName);
				// resUsedProducts.setFileDesc(fileDesc);
				// resUsedProducts.setFileOrdering(fileOrdering);
				resUsedProducts.setFileKey("design.designPlan.usedConfig");
				resUsedProducts.setBusinessId(id);// 已使用产品id
				resUsedProducts.setGmtModified(new Date());
				// resUsedProducts.setRemark(remark);
				sysSave(resUsedProducts, request);
				int resId = resUsedProductsService.add(resUsedProducts);
				if (resId == 0) {
					return false;
				}
				// 回填fileId
				UsedProducts products = usedProductsService.get(id);
				products.setResUsedId(resId);
				usedProductsService.update(products);
				return uploadFtpFlag;
			}
			/* 已使用产品资源记录 end */
			/* 解除planProductId设计产品的组(解组) */
			/*
			 * String planGroupId=designPlanProduct.getPlanGroupId();
			 * if(StringUtils.isNotBlank(planGroupId)&&!StringUtils.equals("0",
			 * planGroupId)){
			 * designPlanProductService.relieveGroupByPlanIdAndplanGroupId(designPlanId,
			 * planGroupId);
			 * logger.warn("替换产品操作导致解组:时间:"+Utils.getTimeStr()+";设计方案id:"+designPlanId+
			 * ";planProductId:"+planProductId+";替换为:"+productId); }
			 */
			/* 解除planProductId设计产品的组(解组)->end */
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 更新splitTextureInfo信息
	 * 
	 * @author huangsongbo
	 * @param splitTextureDTOList
	 * @param str
	 *            格式:key:textureId
	 * @return
	 */
	private List<SplitTextureInfoDTO> updateSplitTexture(List<SplitTextureInfoDTO> splitTextureDTOList, String str) {
		if (str.indexOf(":") == -1)
			return splitTextureDTOList;
		String[] strs = str.split(":");
		if (strs.length < 2)
			return splitTextureDTOList;
		String key = strs[0];
		String value = strs[1];
		for (SplitTextureInfoDTO splitTextureDTO : splitTextureDTOList) {
			if (StringUtils.equals(key, splitTextureDTO.getKey())) {
				splitTextureDTO.setDefaultId(Integer.valueOf(value));
				break;
			}
		}
		return splitTextureDTOList;
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model, HttpServletRequest request) {
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
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
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
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public DesignPlanRes getDesignPlanRes(long designPlanId) {

		DesignPlanRes designPlanRes = null;

		// 获取设计方案信息 ->start
		DesignPlan designPlan = designPlanService.get((int) designPlanId);
		// DesignPlan designPlan = this.getPlan((int)designPlanId);
		if (designPlan == null) {
			logger.error("------designPlan is null, designPlanId:" + designPlanId);
			return null;
		}

		// 获取设计方案信息 ->end

		// 获取模型文件 ->start
		ResModel resModel = null;
		if (designPlan.getModelId() != null) {
			resModel = resModelService.get(designPlan.getModelId());
		}

		if (resModel == null) {
			logger.error("------resModel is null, resModelId:" + designPlan.getModelId());
			return null;
		}
		// 获取模型文件 ->end

		// 获取配置文件 ->start
		ResDesign resDesign = null;
		if (designPlan.getConfigFileId() != null) {
			// resDesign = this.get(designPlan.getConfigFileId());
			resDesign = resDesignService.get(designPlan.getConfigFileId());
		}

		if (resDesign == null) {
			logger.error("------resDesign is null, resDesignId:" + designPlan.getConfigFileId());
			return null;
		}
		// 获取配置文件 ->end

		// 获取设计方案产品列表 ->start
		List<DesignPlanProduct> designPlanProductList = designPlanProductService.getListByPlanId(designPlan.getId());
		// List<DesignPlanProduct> designPlanProductList =
		// this.getListByPlanId(designPlan.getId());
		if (designPlanProductList == null || designPlanProductList.size() == 0) {
			logger.error("------designPlanProductList is null or size = 0, designPlanId:" + designPlan.getId());
			return null;
		}
		// 获取设计方案产品列表 ->end
		designPlanRes = new DesignPlanRes(designPlan, resModel, resDesign, designPlanProductList, null);

		return designPlanRes;
	}

	@Override
	public long saveAsRenderOnekey(DesignPlanRes designPlanRes) {

		if (designPlanRes == null) {
			return 0;
		}

		// 设计方案存副本 ->start
		DesignPlan designPlan = new DesignPlan();
		DesignPlan newdesignPlan = designPlanRes.getDesignPlan();

		try {
			ClassReflectionUtils.reflectionAttr(newdesignPlan, designPlan);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}

		// 修改部分属性
		Date now = new Date();
		designPlan.setGmtCreate(now);
		designPlan.setGmtModified(now);
		designPlan.setDesignPlanId(newdesignPlan.getId());
		designPlan.setIsParent(1);
		designPlan.setRemark("方案来自一键装修");
		Integer id = this.add(designPlan);
		// Integer id = designPlanService.add(newdesignPlan);
		// 设计方案存副本 ->end

		// 配置文件copy一份,存副本 ->start
		Long resDesignEctypeId = this.copyFromResDesign(designPlanRes.getResDesign(), id);
		if (resDesignEctypeId < 1) {
			// copyFromResDesign方法执行失败,删除designPlanEctype
			// designPlanRenderSceneService.delete(id);
			// designPlanService.delete(id);
			this.delete(id);
			return 0;

		}

		// 回填configFileId
		newdesignPlan.setConfigFileId(resDesignEctypeId.intValue());
		// designPlanService.update(designPlan);
		this.update(newdesignPlan);
		// 配置文件copy一份,存副本 ->end

		// 设计方案产品列表存副本 ->start
		// designPlanProductRenderSceneService.copyFromDesignPlanProductList(designPlanRes.getDesignPlanProductList(),
		// id);
		this.copyFromDesignPlanProductList(designPlanRes.getDesignPlanProductList(), id);
		// 设计方案产品列表存副本 ->end

		return id;

	}

	public boolean copyFromDesignPlanProductList(List<DesignPlanProduct> designPlanProductList, long planId) {
		// 参数验证 ->start
		if (designPlanProductList == null || designPlanProductList.size() == 0) {
			return false;
		}

		if (planId < 1) {
			return false;
		}
		// 参数验证 ->end

		// 保存设计方案产品副本 ->start
		List<DesignPlanProduct> designPlanProductRenderList = new ArrayList<>();

		for (DesignPlanProduct designPlanProduct : designPlanProductList) {
			DesignPlanProduct newDesignPlanProduct = new DesignPlanProduct();
			try {
				ClassReflectionUtils.reflectionAttr(designPlanProduct, newDesignPlanProduct);
				newDesignPlanProduct.setId(null);
				newDesignPlanProduct.setPlanId((int) planId);
				newDesignPlanProduct.setGmtModified(new Date());
				/* this.add(designPlanProductRenderScene); */
				designPlanProductRenderList.add(newDesignPlanProduct);

			} catch (Exception e) {
				// 没有写回滚食物(或者用手动事务?)
				return false;
			}

		}
		this.add(designPlanProductRenderList);
		// designPlanProductService.save(designPlanProductRenderSceneList);
		// 保存设计方案产品副本 ->end

		return true;
	}

	@Override
	public List<DesignPlanProduct> getListByPlanId(Integer planId) {
		/*
		 * DesignPlanProductSearch designPlanProductSearch = new
		 * DesignPlanProductSearch(); designPlanProductSearch.setStart(-1);
		 * designPlanProductSearch.setLimit(-1);
		 * designPlanProductSearch.setIsDeleted(0);
		 * designPlanProductSearch.setPlanId(planId); return
		 * this.getPaginatedList(designPlanProductSearch);
		 */
		return optimizePlanMapper.getListByPlanIdAndIsDeleted(planId, 0);
	}

	@Override
	public void delete(Integer id) {
		optimizePlanMapper.deleteByPlanKey(id);
	}

	/**
	 * 更新数据
	 *
	 * @param resRenderPic
	 * @return int
	 */
	@Override
	public int update(ResRenderPic resRenderPic) {
		return optimizePlanMapper.updateByPrimaryKeyPic(resRenderPic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nork.design.service.DesignPalnRenderService#isInvisible4Render(long)
	 */
	@Override
	public boolean isInvisible4Render(long designPlanId) {
		DesignPlan designPlan = this.getPlan((int) designPlanId);
		if (designPlan == null) {
			logger.error("------function:isInvisible4Render->designPlan = null(designPlanId:" + designPlanId + ")");
			return false;
		}
		// 如果设计方案的副本id>0且设置为可见,则返回true
		if (designPlan.getDesignSceneId() != null && designPlan.getDesignSceneId() > 0
				&& new Integer(0).equals(designPlan.getVisible())) {
			return true;
		}
		return false;
	}

	// ----------------------分割线------------------------------------------
	/**
	 * 一键生成设计方案 根据配置生成设计方案及设计方案产品列表
	 */
	@Override
	public ResponseEnvelope<UnityDesignPlan> findOnekeyAutoRenderPlanInfo(Integer designTempletId,
			DesignPlanRecommended designPlanRecommended, String context, String msgId, LoginUser loginUser, String mediaType) {
		// TODO Auto-generated method stub
		DesignTemplet designTemplet = designTempletService.get(designTempletId);
		if (designTemplet == null) {
			return new ResponseEnvelope<>(false, "找不到样板房！designTempletId = " + designTemplet, msgId);
		}
		DesignPlan designPlan = new DesignPlan();
		designPlan.setDesignTemplateId(designTempletId);
		designPlan.setRecommendedPlanId(designPlanRecommended.getId());
		/** 通过样板房创建设计方案信息 **/
		// designPlan =
		// designPlanService.createDesignPlan(designPlan,designTemplet,mediaType,loginUser);
		designPlan = this.createDesignPlan(designPlan, designPlanRecommended,designTemplet, mediaType, loginUser);
		if (designPlan == null) {
			return new ResponseEnvelope<>(false, "创建设计方案失败！", msgId);
		}

		
		// 配置内容存储路径
		// String configKey =
		// Utils.getValue("design.designPlan.u3dconfig.upload.path","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/");
		// configKey = configKey.replace("[code]",designPlan.getPlanCode());
		String configKey = Utils.getPropertyName("config/res", "auto.design.designPlan.u3dconfig.upload.path",
				"/AA/d_userdesign_auto/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/u3dconfig/").trim();
		configKey = Utils.replaceDate(configKey);
		// 文件名称
		String fileName = System.currentTimeMillis() + "_" + Utils.generateRandomDigitString(6) + ".txt";
		// 先把文件保存到本地
		// String filePath = Constants.UPLOAD_ROOT + configKey + fileName;
		String filePath = Utils.getAbsolutePath(configKey + fileName, null);
		if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
			filePath = filePath.replace("\\", "/");
		}
		/** 写入文件存储到指定目录 **/
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath, context);
		if (uploadFtpFlag) {
			Map<String, String> map = FileUploadUtils.getMap(new File(filePath),
					"auto.design.designPlan.u3dconfig.upload.path", false);
			// 生成资源数据
			// boolean flag = resDesignService.saveResDesign(designPlan,map);
			boolean flag = this.saveResDesign(designPlan, map);
			if (!flag) {
				return new ResponseEnvelope<>(false, "保存资源数据库失败！", msgId);
			}
		} else {
			return new ResponseEnvelope<>(false, "保存设计方案配置文件失败！", msgId);
		}
		/** 解析配置文件生成设计方案列表 **/
		JSONObject resultJSON = null;
		try {
			// resultJSON =
			// designPlanProductService.analysisJson(designTempletId,recommendedPlanId,designPlan,context,
			// loginUser);
			resultJSON = this.analysisJson(designTempletId, designPlanRecommended.getId(), designPlan, context, loginUser);
			if (resultJSON != null && !resultJSON.getBoolean("success")) {
				return new ResponseEnvelope<>(false, "保存设计方案产品列表失败！", msgId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEnvelope<>(false, "保存设计方案产品列表异常！", msgId);
		}
		// 获取设计方案信息
		UnityDesignPlan unityDesignPlan = new UnityDesignPlan();
		ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo = (ResponseEnvelope) this
				.getDesignPlanInfo(designPlan.getId(), null, null, null, null, false, loginUser, mediaType);
		// ResponseEnvelope<UnityDesignPlan> responseEnvelopeInfo =
		// (ResponseEnvelope)designPlanService.getDesignPlanInfo(designPlan.getId(),null,null,null,null,false,loginUser,mediaType);
		if (responseEnvelopeInfo.isSuccess()) {
			unityDesignPlan = (UnityDesignPlan) responseEnvelopeInfo.getObj();
		} else {
			logger.error(responseEnvelopeInfo.getMessage());
			return new ResponseEnvelope<>(false, "进入设计方案失败！", msgId);
		}

		return new ResponseEnvelope<UnityDesignPlan>(unityDesignPlan, msgId, true);

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
	
	@Override
	public int costTypeListCount(DesignPlanProduct designPlanProduct) {
		return optimizePlanMapper.costTypeListCount(designPlanProduct);
	}

	/**
	 * 结算类型汇总清单
	 * 
	 * @param designPlanProduct
	 * @return
	 */
	@Override
	public List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct) {
		return optimizePlanMapper.costTypeList(designPlanProduct);
	}

	/**
	 * 结算汇总清单
	 * 
	 * @return
	 */
	@Override
	public List<ProductsCost> costList(ProductsCostType productsCostType) {
		return optimizePlanMapper.costList(productsCostType);
	}

	/**
	 * 结算清单明细
	 * 
	 * @param cost
	 * @return
	 */
	@Override
	public List<ProductCostDetail> costDetail(ProductsCost cost) {
		return optimizePlanMapper.costDetail(cost);
	}

	/**
	 * 获取设计方案产品费用清单
	 * 
	 * @param planId
	 * @param userId
	 * @param userType
	 * @return
	 */
	@Override
	public List<ProductsCostType> getProductsCost(Integer planId, Integer userId, Integer userType) {
		List<ProductsCostType> list = null;
		if (planId == null || userId == null || userType == null) {
			return null;
		}
		// 获取产品列表
		LoginUser loginUser = new LoginUser();
		loginUser.setId(userId);
		loginUser.setUserType(userType);
		DesignPlanProduct designPlanProduct = new DesignPlanProduct();
		designPlanProduct.setPlanId(planId);
		designPlanProduct.setUserId(1);
		list = this.costListPlan(loginUser, designPlanProduct);
		return list;
	}

	/**
	 * 获取设计方案费用列表
	 * 
	 * @param loginUser
	 * @param designPlanProduct
	 */
	@Override
	public List<ProductsCostType> costListPlan(LoginUser loginUser, DesignPlanProduct designPlanProduct) {
		/* 查询用户关联的序列号list */
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(new Integer(1));
		// authorizedConfig.setTerminalImei(terminalImei);
		List<AuthorizedConfig> list = authorizedConfigMapper.selectList(authorizedConfig);
		/* 查询用户关联的序列号list->end */
		/* add品牌,大类,小类,产品查询条件 */
		List<BaseProduct> baseProductList = new ArrayList<BaseProduct>();
		if (3 == loginUser.getUserType()) {
			if (list != null && list.size() > 0) {
				for (AuthorizedConfig authorizedConfigItem : list) {
					/* 查询条件注入到BaseProduct类中 */
					BaseProduct baseProduct = baseProductService
							.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
					baseProductList.add(baseProduct);
				}
				/* 设置查询条件(序列号) */
				designPlanProduct.setBaseProduct(baseProductList);
			}
		}
		/* add品牌,大类,小类,产品查询条件->end */
		int total = this.costTypeListCount(designPlanProduct);
		List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
		List<ProductsCost> costList = new ArrayList<ProductsCost>();
		if (total > 0) {
			SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
			/* 查询软硬装下面包含的产品小类等信息(带入序列号查询条件) */
			costTypeList = this.costTypeList(designPlanProduct);
			for (ProductsCostType costType : costTypeList) {
				costType.setPlanId(designPlanProduct.getPlanId());
				costType.setUserId(loginUser.getId());
				// costType.setAuthorizedConfigList(list);
				costType.setBaseProduct(baseProductList);
				/*
				 * 得到结算汇总清单 设置查询条件(序列号)
				 */
				costList = this.costList(costType);
				List<ProductCostDetail> costDetails = null;
				int productCount = 0;
				for (ProductsCost cost : costList) {
					productCount += cost.getProductCount();
					/** 得到结算详情清单 */
					cost.setPlanId(designPlanProduct.getPlanId());
					cost.setUserId(loginUser.getId());
					cost.setAuthorizedConfigList(list);
					cost.setBaseProduct(baseProductList);
					if (dictionary != null) {
						cost.setSalePriceValueName(dictionary.getName());
					}
					if ("1".equals(cost.getCostTypeValue()) || "2".equals(cost.getCostTypeValue())
							|| "3".equals(cost.getCostTypeValue())) {
						BigDecimal salePrice = cost.getTotalPrice();
						BigDecimal totalPrice = costType.getTotalPrice();
						BigDecimal new_totalPrice = totalPrice.subtract(salePrice);
						costType.setTotalPrice(new_totalPrice);
						cost.setTotalPrice(null);
					}
					costDetails = this.costDetail(cost);
					if (costDetails != null && costDetails.size() > 0) {
						for (ProductCostDetail productCostDetail : costDetails) {
							BaseProduct baseProduct = baseProductService.get(productCostDetail.getProductId());
							// 讲 地面 墙面 天花的 价格 从总价中 去除
							if ("1".equals(baseProduct.getProductTypeValue())
									|| "2".equals(baseProduct.getProductTypeValue())
									|| "3".equals(baseProduct.getProductTypeValue())) {
								if (baseProduct != null) {
									productCostDetail.setTotalPrice(null);
									productCostDetail.setUnitPrice(null);
								}

							}
						}
					}
					cost.setDetailList(costDetails);
				}
				costType.setDetailList(costList);
				costType.setProductCount(productCount);
				if (dictionary != null) {
					costType.setSalePriceValueName(dictionary.getName());
				}
				costType.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
			}
		}
		return costTypeList;
	}

	@Override
	public int countOnekeyDesignPlan(ResRenderPic resRenderPic) {
		return optimizePlanMapper.countOnekeyDesignPlan(resRenderPic);
	}

	@Override
	public Map<String, String> get720SysCode(Integer planRecommendedId, Integer templateId, Integer renderingType,Integer taskId) {
		String sysCode = null;
		Integer picId = 0;
		Integer autoRenderPlanId = 0;
		List<ResRenderPic> resultList = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		if (templateId.intValue() == -1 || templateId.intValue() == 0) {// 替换渲染
			AutoRenderTaskState arts = new AutoRenderTaskState();
			arts.setTaskId(taskId);
			arts = designPlanAutoRenderMapper.getTaskType(arts);

			if (arts != null && arts.getBusinessId() != null) {
				logger.error("arts.getBusinessId()   == " + arts.getBusinessId());
				ResRenderPic pic = new ResRenderPic();
				pic.setDesignSceneId(arts.getBusinessId());// 将新渲染得到的副本id作为查询条件
				pic.setRenderingType(renderingType);
				pic.setIsDeleted(new Integer(0));
				List<String> fileKeyList = new ArrayList<>();
				fileKeyList.add("design.designPlan.render.pic");
				fileKeyList.add("design.designPlan.render.video.cover");
				// fileKeyList.add("auto.design.designPlan.render.pic");
				// fileKeyList.add("auto.design.designPlan.render.video.cover");
				pic.setFileKeyList(fileKeyList);
				resultList = resRenderPicMapper.getReplaceRenderResult(pic);
			}

		} else {// 装进我家
			resultList = optimizePlanMapper.selectByPlanIdAndTemplateId(planRecommendedId, templateId, renderingType);
		}

		if (Lists.isNotEmpty(resultList)) {
			if (renderingType.intValue() == RenderTypeCode.COMMON_720_LEVEL 
					|| renderingType.intValue() == RenderTypeCode.ROAM_720_LEVEL) {
				ResRenderPic resRenderPic = resultList.get(0);
				sysCode = resRenderPic.getSysCode();
				picId = resRenderPic.getId();
				autoRenderPlanId = resRenderPic.getBusinessId();
				returnMap.put("code", sysCode);
				returnMap.put("contentUrl", picId.toString());
				returnMap.put("planId", autoRenderPlanId.toString());
			} else if (renderingType.intValue() == RenderTypeCode.COMMON_VIDEO) {
				ResRenderPic resRenderPic = resultList.get(0);
				ResRenderVideo video = null;
				if (templateId.intValue() == -1 || templateId.intValue() == 0) {
					video = resRenderVideoMapper.selectBySysTaskPicId(resRenderPic.getId());
				} else {
					video = resRenderVideoMapper.selectAutoRenderVideoBySysTaskPicId(resRenderPic.getId());
				}
				String videoPath = video.getVideoPath();
				returnMap.put("coverPath", resRenderPic.getPicPath());//封面图路径
				returnMap.put("url", videoPath);
			}else if(renderingType.intValue() == RenderTypeCode.COMMON_PICTURE_LEVEL) {
				ResRenderPic resRenderPic = resultList.get(0);
				ResRenderPic pic = null;
				if (templateId.intValue() == -1 || templateId.intValue() == 0) {
					pic = resRenderPicMapper.selectByPrimaryKey(resRenderPic.getPid());
				} else {
					pic = resRenderPicMapper.selectByPrimaryKey(resRenderPic.getPid());
				}
				returnMap.put("coverPath", resRenderPic.getPicPath());//缩略图路径
				returnMap.put("url", pic.getPicPath());
			}
		}
		return returnMap;
	}

	/******************************************
	 * 分割线
	 ***************************************************/
	@Override
	public DesignPlanRes getAutoRenderDesignPlanRes(Integer designPlanId) {

		logger.error("designPlanId====================" + designPlanId);
		DesignPlanRes designPlanRes = null;

		// 获取设计方案信息 ->start
		DesignPlan designPlan = this.getPlan(designPlanId);// 这里获取的是自动渲染的设计方案
		if (designPlan == null) {
			logger.error("------designPlan is null, designPlanId:" + designPlanId);
			return null;
		}
		logger.info("designPlanId_sceneModified_" + designPlan.getSceneModified());

		// 获取设计方案信息 ->end

		// 获取模型文件 ->start
		ResModel resModel = null;
		if (designPlan.getModelId() != null) {
			resModel = resModelService.get(designPlan.getModelId());// 这里获取的是自动渲染的设计方案的模型
		}

		if (resModel == null) {
			logger.error("------resModel is null, resModelId:" + designPlan.getModelId());
			return null;
		}
		// 获取模型文件 ->end

		// 获取配置文件 ->start
		ResDesign resDesign = null;
		if (designPlan.getConfigFileId() != null) {
			resDesign = this.get(designPlan.getConfigFileId());// 这里获取的是自动渲染的设计方案的配置文件
		}

		if (resDesign == null) {
			logger.error("------resDesign is null, resDesignId:" + designPlan.getConfigFileId());
			return null;
		}
		// 获取配置文件 ->end

		// 获取设计方案产品列表 ->start
		List<DesignPlanProduct> designPlanProductList = this.getListByPlanId(designPlan.getId());// 这里获取的是自动渲染的设计方案使用的产品
		if (designPlanProductList == null || designPlanProductList.size() == 0) {
			logger.error("------designPlanProductList is null or size = 0, designPlanId:" + designPlan.getId());
			return null;
		}
		// 获取设计方案产品列表 ->end

		List<ResRenderPic> resRenderPicList = optimizePlanMapper.selectAutoPicByBusinessId(designPlanId);
		List<ResRenderVideo> resRenderVideoList = optimizePlanMapper.selectAutoVideoByBusinessId(designPlanId);
		if (resRenderPicList == null || resRenderPicList.size() == 0) {
			logger.error("------resRenderPicList is null or size = 0, designPlanId:" + designPlanId);
		}
		// List<ResRenderVideo> resRenderVideoList =
		// optimizePlanMapper.selectAutoVideoByBusinessId(designPlanId);
		if (resRenderVideoList == null || resRenderVideoList.size() == 0) {
			logger.error("------resRenderVideoList is null or size = 0, designPlanId:" + designPlanId);
		}

		designPlanRes = new DesignPlanRes(designPlan, resModel, resDesign, designPlanProductList, resRenderPicList,
				resRenderVideoList);

		return designPlanRes;
	}

	@Override
	public Integer saveAsRenderBakScene(DesignPlanRes designPlanRes, LoginUser loginUser) {

		if (designPlanRes == null) {
			return 0;
		}

		// 设计方案存副本 ->start
		DesignPlanRenderScene designPlanEctype = new DesignPlanRenderScene();
		DesignPlan designPlan = designPlanRes.getDesignPlan();

		try {
			ClassReflectionUtils.reflectionAttr(designPlan, designPlanEctype);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}

		// 修改部分属性
		designPlanEctype.setUserId(loginUser.getId());
		designPlanEctype.setCreator(loginUser.getLoginName());
		designPlanEctype.setGmtCreate(new Date());
		designPlanEctype.setModifier(loginUser.getLoginName());
		designPlanEctype.setGmtModified(new Date());
		designPlanEctype
				.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		designPlanEctype.setDesignPlanId(designPlan.getId());

		Integer desiginPlanSceneId = designPlanRenderSceneService.add(designPlanEctype);// 效果图方案
		logger.error("saveAsRenderBakScene designPlanSceneId add====>" + desiginPlanSceneId);
		DesignPlan newDesignPlan = designPlan.copy();
		newDesignPlan.setId(null);
		newDesignPlan.setUserId(loginUser.getId());
		newDesignPlan.setCreator(loginUser.getLoginName());
		newDesignPlan.setGmtCreate(new Date());
		newDesignPlan.setModifier(loginUser.getLoginName());
		newDesignPlan.setGmtModified(new Date());
		newDesignPlan
				.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		newDesignPlan.setDesignPlanId(designPlan.getId());

		newDesignPlan.setIsChange(0);
		newDesignPlan.setIsOpen(0);
		if (designPlan.getPlanCode() != null) {
			// 重新生成一个随机的设计方案编码
			newDesignPlan.setPlanCode(designPlan.getPlanCode().substring(0, designPlan.getPlanCode().length() - 6)
					+ Utils.generateRandomDigitString(6));
		}
		if (designPlan.getConfigFileId() != null) {
			Integer resFileId = this.planCopyFile(designPlan.getConfigFileId().toString(), "file",
					"design.designPlan.u3dconfig", null, loginUser, designPlan.getPlanCode());
			newDesignPlan.setConfigFileId(resFileId);/* 设计方案配置文件 */
		} else {
			newDesignPlan.setConfigFileId(-1);/* 设计方案配置文件 */
		}
		Integer sketchId = designPlanService.add(newDesignPlan);// 草图方案
		// 设计方案存副本 ->end

		// 配置文件copy一份,存副本 ->start
		long resDesignEctypeId = resDesignRenderSceneService.copyFromResDesign(designPlanRes.getResDesign(),
				desiginPlanSceneId.longValue());// 效果图方案

		if (resDesignEctypeId < 1) {
			// copyFromResDesign方法执行失败,删除designPlanEctype
			designPlanRenderSceneService.delete(desiginPlanSceneId.longValue());
			return 0;
		}
		logger.error("saveAsRenderBakScene designPlanSceneId====>" + desiginPlanSceneId);
		// 回填configFileId
		designPlanEctype.setConfigFileId((int) resDesignEctypeId);
		designPlanRenderSceneService.update(designPlanEctype);

		// 配置文件copy一份,存副本 ->end

		// 设计方案产品列表存副本 ->start
		designPlanProductRenderSceneService.copyFromDesignPlanProductList(designPlanRes.getDesignPlanProductList(),
				desiginPlanSceneId.longValue());// 效果图方案

		designPlanProductService.saveDesignPlanProductList(designPlanRes.getDesignPlanProductList(),
				sketchId.longValue());// 草图方案
		/*
		 * if (sketchId != null) { // 查询出改方案所有的产品 List<DesignPlanProduct>
		 * planProductList = designPlanRes.getDesignPlanProductList(); // 把查询出来的产品复制到产品表
		 * for (DesignPlanProduct dprProduct : planProductList) { DesignPlanProduct
		 * planProduct = new DesignPlanProduct(); planProduct = dprProduct.copy();
		 * planProduct.setId(null); planProduct.setPlanId(sketchId);
		 * planProduct.setGmtCreate(new Date());
		 * planProduct.setCreator(loginUser.getLoginName());
		 * planProduct.setIsDeleted(0);
		 * planProduct.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" +
		 * Utils.generateRandomDigitString(6)); planProduct.setGmtModified(new Date());
		 * planProduct.setModifier(loginUser.getLoginName());
		 * designPlanProductService.add(planProduct); } }
		 */
		// 设计方案产品列表存副本 ->end

		// 查询原推荐方案 所有的渲染图
		List<ResRenderPic> picList = designPlanRes.getResRenderPicList();
		Integer smallId = 0;// 自动渲染的720的缩略图id
		Integer originalPicId = 0;// 自动渲染的720的原图id
		Integer screenshotId = 0;//新的多点的渲染截图的id
		List<Integer> sourcePicIds = new ArrayList<>();
		Integer renderRoamId = 0;//新的多点渲染的关联表的id
		Integer sourcePicId = 0;//需要复制的720多点的缩略图的id

		if (Lists.isNotEmpty(picList)) {
			for (ResRenderPic resPic : picList) {
				resPic.setCreator(loginUser.getLoginName());
				resPic.setGmtCreate(new Date());
				resPic.setModifier(loginUser.getLoginName());
				resPic.setGmtModified(new Date());
				resPic.setSysCode(
						Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));

				resPic.setDesignSceneId(desiginPlanSceneId);
				resPic.setCreateUserId(loginUser.getId());
				resPic.setPlanRecommendedId(0);
				resPic.setBusinessId(sketchId);

				resPic.setDesignPlanName(designPlanEctype.getPlanName());
				SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlanEctype.getSpaceCommonId());

				if (spaceCommon != null) {
					resPic.setArea(spaceCommon.getSpaceAreas());// 空间面积大小
					SysDictionary query = new SysDictionary();
					query.setType(Constants.SYS_DICTIONARY_TYPE_HOUSETYPE);
					query.setValue(spaceCommon.getSpaceFunctionId());
					SysDictionary sysDictionary = sysDictionaryMapper.getNameByType(query);
					if (sysDictionary != null)
						resPic.setSpaceType(sysDictionary.getName());// 设置房屋空间类型
				}
				//修改file_key  start
				String _fileKey = resPic.getFileKey();
				if (StringUtils.isNotBlank(_fileKey) && _fileKey.startsWith("auto.")) {
					_fileKey = _fileKey.replaceAll("auto.", "");
					resPic.setFileKey(_fileKey);
				}
				//修改file_key  end
				
				//如果是720多点的缩略图就把对应的DesignRenderRoam复制一份
//				if(resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY)
//						|| resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER)) {
					sourcePicId = resPic.getId();
//				}
				Integer picId = resRenderPicService.insertSelective(resPic);
				if(resPic.getRenderingType() == RenderTypeCode.ROAM_720_LEVEL) {
					//记录下新的多点的渲染截图的id
					if(resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY)) {
						screenshotId = picId;
					}
					//记录下新的多点的渲染原图的id保存
					if(resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY_NEW)) {
						sourcePicIds.add(picId);
					}
					//将所有多点的图的sys_task_pic_id修改为新的截图的id
					resPic.setSysTaskPicId(screenshotId);
					resRenderPicService.update(resPic);
					
					if(resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY)
							|| resPic.getFileKey().equals(ResProperties.DESIGNPLANRECOMMENDED_RENDER_PIC_FILEKEY)) {
						logger.error("resPic.getFileKey() " + resPic.getFileKey());
						renderRoamId = this.copyDesignRenderRoam(sourcePicId, picId);
					}
					
				}
				logger.error("saveAsRenderBakScene picId fk====>" + picId + "resPic==>" + resPic.getDesignPlanName());
				if (resPic.getRenderingType().intValue() == 4) {
					// 获取自动渲染的720的缩略图id
					if (resPic.getFileKey().equals(ResProperties.AUTO_DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY)
							|| resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY)) {
						smallId = resPic.getId();
					}
					// 获取自动渲染的720的原图id
					if (resPic.getFileKey().equals(ResProperties.AUTO_DESIGNPLAN_RENDER_PIC_FILEKEY)
							|| resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY)) {
						originalPicId = resPic.getId();
					}
				} else if (resPic.getRenderingType().intValue() == 8) {
					// 获取自动渲染的720roam的缩略图id
					if (resPic.getFileKey().equals(ResProperties.AUTO_DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY)
							|| resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY)) {
						smallId = resPic.getId();
					}
					// 获取自动渲染的720roam的原图id
					if (resPic.getFileKey().equals(ResProperties.AUTO_DESIGNPLAN_RENDER_PIC_FILEKEY)
							|| resPic.getFileKey().equals(ResProperties.DESIGNPLAN_RENDER_FILEKEY)) {
						originalPicId = resPic.getId();
					}
				}
				logger.error("saveAsRenderBakScene designPlanSceneId fk====>" + desiginPlanSceneId);
				if (picId.intValue() == 0) {
					logger.error("picId=======" + picId);
					return 0;
				}
				logger.error("saveAsRenderBakScene designPlanSceneId fk2====>" + desiginPlanSceneId);
				// 如果是漫游视频就把视频表的数据也复制一份
				if (resPic.getRenderingType().intValue() == RenderTypeCode.COMMON_VIDEO) {
					logger.error("进入了复制渲染视频的方法   sourcePicId = " + sourcePicId + ",picId:" + picId);
					ResRenderVideo video = optimizePlanMapper.selectBySysTaskPicId(sourcePicId);
					if (video != null) {
						logger.error("进入了tianjian渲染视频的方法   sourcePicId = " + sourcePicId + ",picId:" + picId);
						video.setId(null);
						video.setCreator(loginUser.getLoginName());
						video.setGmtCreate(new Date());
						video.setModifier(loginUser.getLoginName());
						video.setGmtModified(new Date());
						video.setSysCode(
								Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
						// video.setFileKey("design.designPlan.render.video");
						video.setSysTaskPicId(picId);
						resRenderVideoService.add(video);
					}
				}
			}

			if (smallId.intValue() != 0 && originalPicId.intValue() != 0) {
				logger.error("saveAsRenderBakScene designPlanSceneId fk3====>" + desiginPlanSceneId+",smallId==" + smallId+",originalPicId=="+originalPicId);

				// 将720原图id赋值给缩略图的pid，修改保存进数据库
				ResRenderPic pic = resRenderPicMapper.selectByPrimaryKey(smallId);
				pic.setPid(originalPicId);
				logger.error("saveAsRenderBakScene designPlanSceneId fk4 originalPicId====>" + originalPicId);
				resRenderPicMapper.updateByPrimaryKeySelective(pic);
				logger.error("saveAsRenderBakScene designPlanSceneId fk4====>" + desiginPlanSceneId);
			}
		}
		
		//修改多点的配置文件 TODO
		if(screenshotId.intValue() > 0 && renderRoamId.intValue() > 0 && sourcePicIds.size() > 0) {
			updateRoamConfig(screenshotId,sourcePicIds,renderRoamId);
		}
		
		logger.error("saveAsRenderBakScene designPlanSceneId end====>" + desiginPlanSceneId);
		return desiginPlanSceneId;
	}

	/*
	 * 自动存储(加code)
	 */
	public Integer planCopyFile(String resId, String resType, String fileKey, Integer bussniess, LoginUser loginUser,
			String code) {
		logger.info("resId=" + resId + ";resType=" + resType + ";fileKey=" + fileKey + ";bussniess=" + bussniess
				+ ";code=" + code);
		String resFilePath = "";
		Integer newResId = -1;

		ResDesign resDesign = new ResDesign();
		if (!StringUtils.isEmpty(resId)) {
			if ("file".equals(resType)) {
				ResDesign file = optimizePlanMapper.selectByPrimaryKeyRes(new Integer(resId));
				if (file != null && !StringUtils.isEmpty(file.getFilePath())) {
					resFilePath = file.getFilePath();
					resDesign = file.resDesignCopy();
				}
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

				String newPath = Utils.getPropertyName("config/res", fileKey + ".upload.path", "").trim();
				newPath = Utils.replaceDate(newPath);
				String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,
						resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
						+ resourcesName.substring(resourcesName.indexOf("."));
				String targetName = newPath + tarName;
				// 复制的配置文件因为源文件已经加密，要在文件后面加个标识
				targetName = targetName.replace(".txt", AESFileConstant.AES_FIRST + ".txt");
				String local_targetPath = Utils.dealWithPath(Utils.getAbsolutePath(targetName, null), null);

				File local_targetFile = new File(local_targetPath);

				if (!local_targetFile.getParentFile().exists()) {
					local_targetFile.getParentFile().mkdirs();
				}
				boolean flag = false;
				String resPath = resFilePath.substring(0, resFilePath.lastIndexOf("/") + 1);
				/*
				 * String dbFilePath = Constants.UPLOAD_ROOT.replace("\\", "/") + newPath +
				 * tarName;
				 */
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
						resDesign.setGmtCreate(new Date());
						resDesign.setCreator(loginUser.getLoginName());
						resDesign.setIsDeleted(0);

						resDesign.setGmtModified(new Date());
						resDesign.setModifier(loginUser.getLoginName());
						newResId = resDesignService.add(resDesign);
					}
				}
			}
		}

		return newResId;
	}

	@Override
	public UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan) {
		List<UnityPlanProduct> dataList = unityDesignPlan.getDatalist();
		List<ProductDTO> list = this.getProductDTOList(designPlanId);
		for (UnityPlanProduct upp : dataList) {
			Integer upp_productId = upp.getProductId();
			for (ProductDTO productDTO : list) {
				Integer productId = productDTO.getProductId();
				if (upp_productId.equals(productId) && upp.getPosIndexPath().equals(productDTO.getPosIndexPath())) {
					String valueKey = productDTO.getValueKey();
					if (StringUtils.isNotBlank(valueKey)) {
						if (valueKey.indexOf("_") != -1) {
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

	public List<ProductDTO> getProductDTOList(Integer designPlanId) {
		return optimizePlanMapper.getProductDTOList(designPlanId);
	}

	// DesignPlanRes designPlanRes
	// =optimizePlanService.getAutoRenderDesignPlanRes(planId);
	// Long resultId = optimizePlanService.saveAsRenderBakScene(designPlanRes);
	/**
	 * 复制一份多点的信息
	 * @param sourcePicId
	 * @param picId
	 * @return
	 */
	private Integer copyDesignRenderRoam(Integer sourcePicId,Integer picId) {
		logger.error("进入了copyDesignRenderRoam的方法          sourcePicId : " + sourcePicId +",picId  "+picId);
		//根据原渲染图id找到对应的数据
		DesignRenderRoam designRenderRoam = designRenderRoamService.selectByScreenId(sourcePicId);
		logger.error("根据screen_shot_id得到的   designRenderRoam = " + designRenderRoam.getId());
		//修改缩略图ID为新的缩略图ID
		designRenderRoam.setScreenShotId(picId);
		designRenderRoam.setUuid(Utils.getUUID());
		
		//添加到原表当中
		int id = designRenderRoamService.add(designRenderRoam);
		if(id > 0 ) {
			logger.error("复制成功      新的   id = " + id);
			return new Integer(id);
		}else {
			return new Integer(0);
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	private void updateRoamConfig(Integer sourcePicId,List<Integer> picList,Integer renderRoamId) {
		logger.error("sourcePicId"+sourcePicId+",picList="+picList+",renderRoamId="+renderRoamId);
		//根据原渲染图id找到对应的数据
		DesignRenderRoam designRenderRoam = designRenderRoamService.selectByScreenId(sourcePicId);
		//找到对应的配置文件
		logger.error("designRenderRoam.getRoamConfig() == " +designRenderRoam.getRoamConfig());
		ResDesign roamConfig = resDesignService.get(designRenderRoam.getRoamConfig());
		
		// 获取多720图片之间的关系信息
        String roamConfigContext = "";
        List<Roam> roamList = null;
		if( roamConfig != null ){
        	roamConfigContext = FileUploadUtils.getFileContext(Utils.getAbsolutePath(roamConfig.getFilePath(), null));
        }
		if( StringUtils.isNotBlank(roamConfigContext) ){
            JSONArray jsonArray = JSONArray.fromObject(roamConfigContext);
            roamList = JSONArray.toList(jsonArray,Roam.class);
        }
		String roamListStr = new Gson().toJson(roamList); 
		logger.error("roamListStr1111111111111=============="+roamListStr);
		for(Roam roam : roamList) {
			for(int i = 0 ; i <= picList.size(); i++) {
				logger.error("i==========================="+i);
				if(roam.getIndex() == i+1) {
					logger.error("picList.get(i).intValue()  =======" + picList.get(i).intValue());
					roam.setFieldName(picList.get(i).intValue());
				}
			}
		}
//		JsonArray roamJsonArray = new JsonParser().parse(roamListStr).getAsJsonArray();
		roamListStr = new Gson().toJson(roamList); 
		logger.error("roamListStr222222222=============="+roamListStr);
		//FIXME:测试
		LoginUser loginUser = new LoginUser();
		loginUser.setName("yz");
		
//		Integer resFileId = designPlanService.saveRenderRoamConfig(null,roamJsonArray.toString(),renderRoamId);
		Integer resFileId = designPlanService.saveRenderRoamConfig(loginUser,roamListStr,renderRoamId);
		logger.error("resFileId = = ===="+resFileId);
		designRenderRoam.setRoamConfig(resFileId);
		designRenderRoamService.update(designRenderRoam);
		
	}

 
 
	
}
