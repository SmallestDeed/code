package com.sandu.product.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.common.model.PageModel;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.common.util.collections.Lists;
import com.sandu.design.model.AutoCarryBaimoProducts;
import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.DesignTemplet;
import com.sandu.designconfig.model.DesignRules;
import com.sandu.designconfig.service.DesignRulesService;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.model.DesignPlanRenderScene;
import com.sandu.designplan.service.DesignPlanProductService;
import com.sandu.designplan.service.DesignPlanRenderSceneService;
import com.sandu.designplan.service.DesignPlanService;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.product.dao.BaseProductMapper;
import com.sandu.product.dao.ProductCategoryRelMapper;
import com.sandu.product.dao.UserProductCollectMapper;
import com.sandu.product.model.*;
import com.sandu.product.model.SplitTextureDTO.ResTextureDTO;
import com.sandu.product.model.search.BaseProductSearch;
import com.sandu.product.model.search.UserProductCollectSearch;
import com.sandu.product.service.*;
import com.sandu.system.model.*;
import com.sandu.system.service.*;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.UserSO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @version V1.0
 * @Title: BaseProductServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品模块-产品库ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:37
 */
@Service("baseProductService")
public class BaseProductServiceImpl implements BaseProductService {

	private static Logger logger = LoggerFactory.getLogger(BaseProductServiceImpl.class);
	private static Gson gson = new Gson();
	private final BaseProductMapper baseProductMapper;
	private final ResPicService resPicService;
	private final ResModelService resModelService;

	private final ResFileService resFileService;
	private final SysDictionaryService sysDictionaryService;
	private final DesignRulesService designRulesService;
	private final ProductCategoryRelMapper productCategoryRelMapper;
	private final ProductAttributeService productAttributeService;
	private final ResTextureService resTextureService;
	private final UserProductCollectMapper userProductCollectMapper;
	private final BaseProductStyleService baseProductStyleService;
	private final BaseBrandService baseBrandService;
	private final DesignPlanProductService designPlanProductService;
	private final DesignTempletService designTempletService;
	private final DesignPlanService designPlanService;
	private final AuthorizedConfigService authorizedConfigService;
	private final DesignPlanRenderSceneService designPlanRenderSceneService;

	@Autowired
	public BaseProductServiceImpl(ResPicService resPicService,
								  BaseProductMapper baseProductMapper,
								  DesignPlanProductService designPlanProductService,
								  DesignTempletService designTempletService,
								  AuthorizedConfigService authorizedConfigService,
								  ResModelService resModelService,
								  ResFileService resFileService,
								  SysDictionaryService sysDictionaryService, BaseBrandService baseBrandService, DesignPlanRenderSceneService designPlanRenderSceneService, DesignRulesService designRulesService, ProductCategoryRelMapper productCategoryRelMapper, ProductAttributeService productAttributeService, ResTextureService resTextureService, DesignPlanService designPlanService,
								  UserProductCollectMapper userProductCollectMapper, BaseProductStyleService baseProductStyleService) {
		this.resPicService = resPicService;
		this.baseProductMapper = baseProductMapper;
		this.designPlanProductService = designPlanProductService;
		this.designTempletService = designTempletService;
		this.authorizedConfigService = authorizedConfigService;
		this.resModelService = resModelService;
		this.resFileService = resFileService;
		this.sysDictionaryService = sysDictionaryService;
		this.baseBrandService = baseBrandService;
		this.designPlanRenderSceneService = designPlanRenderSceneService;
		this.designRulesService = designRulesService;
		this.productCategoryRelMapper = productCategoryRelMapper;
		this.productAttributeService = productAttributeService;
		this.resTextureService = resTextureService;
		this.designPlanService = designPlanService;
		this.userProductCollectMapper = userProductCollectMapper;
		this.baseProductStyleService = baseProductStyleService;
	}

	/**
	 * 新增数据
	 *
	 * @param baseProduct
	 * @return int
	 */
	@Override
	public int add(BaseProduct baseProduct) {
		baseProductMapper.insertSelective(baseProduct);
		return baseProduct.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param baseProduct
	 * @return int
	 */
	@Override
	public int update(BaseProduct baseProduct) {
		return baseProductMapper.updateByPrimaryKeySelective(baseProduct);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return baseProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return BaseProduct
	 */
	@Override
	public BaseProduct get(Integer id) {
		return baseProductMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean add(BaseProduct arg0, String arg1, HttpServletRequest arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer isSplit = new Integer(0);
		List<SplitTextureDTO> splitTexturesChooseList = new ArrayList<SplitTextureDTO>();
		List<SplitTextureDTO> splitTexturesInfoList = new ArrayList<SplitTextureDTO>();
		JSONArray jsonArray = JSONArray.fromObject(splitTexturesInfo);
		try {
			List<SplitTextureInfoDTO> SplitTextureList = JSONArray.toList(jsonArray, SplitTextureInfoDTO.class);
			if (SplitTextureList != null && SplitTextureList.size() >= 1) {
				for (SplitTextureInfoDTO splitTextureInfoDTO : SplitTextureList) {
					/* 处理默认材质 */
					if (StringUtils.equals("choose", type) || StringUtils.equals("all", type)) {
						Integer defaultId = splitTextureInfoDTO.getDefaultId();
						List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
						if (defaultId != null && defaultId > 0) {
							ResTexture resTexture = resTextureService.get(defaultId);
							if (resTexture != null && resTexture.getId() != null && resTexture.getId() > 0) {
								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService
										.fromResTexture(resTexture);
								resTextureDTO.setKey(splitTextureInfoDTO.getKey());
								resTextureDTO.setProductId(baseProductId);
								if (resTextureDTO.getTextureWidth() == 0 || resTextureDTO.getTextureWidth() == null) {
									resTextureDTO.setTextureWidth(80); // 徐扬确认。如果材质长度为空/0则给默认80
								}
								if (resTextureDTO.getTextureHeight() == 0 || resTextureDTO.getTextureHeight() == null) {
									resTextureDTO.setTextureHeight(80); // 徐扬确认。如果材质长度为空/0则给默认80
								}
								resTextureDTOList.add(resTextureDTO);
							}
						}
						splitTexturesChooseList.add(new SplitTextureDTO(splitTextureInfoDTO.getKey(),
								splitTextureInfoDTO.getName(), resTextureDTOList));
					}
					/* 处理默认材质->end */
					/* 处理可选材质 */
					if (StringUtils.equals("info", type) || StringUtils.equals("all", type)) {
						String textureIdsStr = splitTextureInfoDTO.getTextureIds();
						List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
						List<String> textureIdStrList = Utils.getListFromStr(textureIdsStr);
						if (textureIdStrList != null && textureIdStrList.size() > 0) {
							List<ResTexture> textureList = null;
							/* 优化后 */
							ResTexture resTexture_ = new ResTexture();
							resTexture_.setResTextureIds(textureIdStrList);
							textureList = resTextureService.getBatchGet(resTexture_);
							// 按照textureIdStrList排序 ->start
							List<ResTexture> listNew = new ArrayList<ResTexture>();
							// textureIdStrList:[406, 407, 409, 410]
							// 将默认材质和textureIdStrList第一个元素互换位置 ->start
							Integer defaultId = splitTextureInfoDTO.getDefaultId();
							if (defaultId != null) {
								int index = textureIdStrList.indexOf(defaultId + "");
								if (index != -1) {
									String item = textureIdStrList.get(0);
									textureIdStrList.set(0, defaultId + "");
									textureIdStrList.set(index, item);
								}
							}
							// 将默认材质和textureIdStrList第一个元素互换位置 ->end
							if (textureIdStrList != null && textureIdStrList.size() > 0) {
								for (int i = 0; i < textureIdStrList.size(); i++) {
									listNew.add(null);
								}
							}
							if (textureList != null && textureList.size() > 0) {
								for (ResTexture resTexture : textureList) {
									if (textureIdStrList.indexOf("" + resTexture.getId()) != -1) {
										listNew.set(textureIdStrList.indexOf("" + resTexture.getId()), resTexture);
									}
								}
							}
							/* listNew.remove(null); */
							textureList = listNew;
							// 按照textureIdStrList排序 ->end
							for (ResTexture resTexture : textureList) {
								if (resTexture == null) {
									continue;
								}
								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureService
										.fromResTexture(resTexture);
								resTextureDTO.setKey(splitTextureInfoDTO.getKey());
								resTextureDTO.setProductId(baseProductId);
								if (resTextureDTO.getTextureWidth() == 0 || resTextureDTO.getTextureWidth() == null) {
									resTextureDTO.setTextureWidth(80); // 徐扬确认。如果材质长度为空/0则给默认80
								}
								if (resTextureDTO.getTextureHeight() == 0 || resTextureDTO.getTextureHeight() == null) {
									resTextureDTO.setTextureHeight(80); // 徐扬确认。如果材质长度为空/0则给默认80
								}
								resTextureDTOList.add(resTextureDTO);
							}

							/*
							 * for(String textureIdStr:textureIdStrList){ ResTexture
							 * resTexture=resTextureService.get(Integer.valueOf(textureIdStr));
							 * if(resTexture!=null){ SplitTextureDTO.ResTextureDTO
							 * resTextureDTO=resTextureService.fromResTexture(resTexture);
							 * resTextureDTO.setKey(splitTextureInfoDTO.getKey());
							 * resTextureDTO.setProductId(baseProductId);
							 * resTextureDTOList.add(resTextureDTO); } }
							 */
							splitTexturesInfoList.add(new SplitTextureDTO(splitTextureInfoDTO.getKey(),
									splitTextureInfoDTO.getName(), resTextureDTOList));
						}
					}

					/* 处理可选材质->end */
				}
				isSplit = new Integer(1);
				map.put("isSplit", isSplit);
				map.put("splitTexturesChoose", splitTexturesChooseList);
				map.put("splitTexturesInfo", splitTexturesInfoList);
			}
		} catch (Exception e) {
			logger.warn("------baseProduct的SplitTextureInfo信息格式错误,SplitTextureInfo:" + splitTexturesInfo);
		}
		return map;
	}

	@Override
	public int getBrandProductsCount(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CategoryProductResult> getBrandProductsList(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseProduct> getByPlanIdProductList(Integer arg0, String arg1, Integer arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(BaseProductSearch arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BaseProduct getDataAndModel(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDetailProduct(Map arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseProduct> getList(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseProduct> getPaginatedList(BaseProductSearch arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getU3dModelId(String mediaType, BaseProduct baseProduct) {
		if(baseProduct == null || mediaType==null){
			return "";
		}
		if("3".equals(mediaType)){
			return baseProduct.getWindowsU3dModelId()==null?"":baseProduct.getWindowsU3dModelId().toString();
		}else if("4".equals(mediaType)){
			return baseProduct.getMacBookU3dModelId()==null?"":baseProduct.getMacBookU3dModelId().toString();
		}else if("5".equals(mediaType)){
			return baseProduct.getIosU3dModelId()==null?"":baseProduct.getIosU3dModelId().toString();
		}else if("6".equals(mediaType)){
			return baseProduct.getAndroidU3dModelId()==null?"":baseProduct.getAndroidU3dModelId().toString();
		}else if("7".equals(mediaType)){
			return baseProduct.getIpadU3dModelId()==null?"":baseProduct.getIpadU3dModelId().toString();
		}else{
			return baseProduct.getWindowsU3dModelId()==null?"":baseProduct.getWindowsU3dModelId().toString();
		}
	}

	@Override
	public boolean isHard(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer selectProductByNameAndBrandCount(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryProductResult> selectProductByNameAndBrandId(BaseProduct arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, CategoryProductResult> setbaimoRuleMap(Integer spaceCommonId, String mediaType, Integer userId,
															  String productSmallTypeCode, DesignPlan designPlan, Map<String, String> map) {
		Map<String, CategoryProductResult> basicModelMap = new HashMap<>();
		// 获取需要自动携带白模产品的配置
		Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
		String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app", "design.designPlan.autoCarryBaimoProducts",
				"");
		if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
			JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
			try {
				@SuppressWarnings("unchecked")
				List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
						.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
				if (autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0) {
					for (AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs) {
						autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(), autoCarryBaimoProductsConfig);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取自动携带白模产品配置异常！");
			}
		}
		// 如果产品在自动携带白模产品的配置中，则根据产品属性获取相应白模产品
		if (autoCarryMap != null && autoCarryMap.containsKey(productSmallTypeCode)) {
			logger.info("当前产品分类：" + productSmallTypeCode + "需要自动携带白模信息。");
			// 获取当前产品得配置
			AutoCarryBaimoProducts autoCarryBaimoProducts = autoCarryMap.get(productSmallTypeCode);
			logger.info("当前产品要对比的属性：" + autoCarryBaimoProducts.getContrastAttributeKey());
			if (autoCarryBaimoProducts != null) {
				// 获取需要携带得白模有哪些
				JSONArray baimoProductsConfigArray = autoCarryBaimoProducts.getBaimoProducts();
				List<AutoCarryBaimoProducts> baimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
						.toCollection(baimoProductsConfigArray, AutoCarryBaimoProducts.class);
				// 根据产品的属性，查询同属性的白模产品
				if (baimoProductsConfigs != null && baimoProductsConfigs.size() > 0) {
					ProductAttribute pa1 = null;
					List<ProductAttribute> tempList = null;
					// 产品属性(需要对比的属性)
					JSONArray productPropertyArray = autoCarryBaimoProducts.getContrastAttributeKey();
					// 白模产品属性（需要对比的属性）
					JSONArray baimoProductPropertyArray = null;
					Map<String, String> baimoPropertyMap = new HashMap<>();
					for (AutoCarryBaimoProducts baimoProductConfig : baimoProductsConfigs) {
						// 白模产品的属性
						baimoProductPropertyArray = baimoProductConfig.getContrastAttributeKey();
						if (baimoProductPropertyArray == null) {
							continue;
						}
						if (baimoProductPropertyArray.size() != productPropertyArray.size()) {
							continue;
						}
						for (Object object : baimoProductPropertyArray) {
							String[] objStr = object.toString().split("#");
							baimoPropertyMap.put(objStr[0], objStr[1]);
						}
						// 对比产品和白模产品的每个属性
						String productPropertyStr = "";
						String baimoProductPropertyStr = "";
						int i = 0;
						Map<String, Object> conditionMap = new HashMap<>();
						List<String> conditionList = new ArrayList<>();
						StringBuffer conditionStr = new StringBuffer();
						for (Object object : productPropertyArray) {
							productPropertyStr = object.toString();
							baimoProductPropertyStr = baimoPropertyMap.get(productPropertyStr);
							if (StringUtils.isNotBlank(baimoProductPropertyStr)) {
								Integer propValue = map.get(productPropertyStr) != null
										? Integer.valueOf(map.get(productPropertyStr))
										: 0;
								conditionStr.append("pa.attribute_key = '" + baimoProductPropertyStr
										+ "' AND pa.attribute_type_value = '" + baimoProductConfig.getSmallTypeCode()
										+ "' AND pp.prop_value = " + propValue);
								if (i < productPropertyArray.size() - 1) {
									conditionStr.append(" union all ");
								}
							}
							i++;
							conditionList.add(conditionStr.toString());
						}
						conditionMap.put("conditionCount", conditionList.size());
						conditionMap.put("conditionList", conditionList);

						tempList = productAttributeService.selectIntersectProductAttribute(conditionMap);
						if (tempList != null && tempList.size() > 0) {
							logger.info("共查到" + tempList.size() + "条满足要求的白模数据！！！");
							Integer baimoProductId = tempList.get(0).getProductId();
							CategoryProductResult baimoProducts = this.assemblyUnityPanProduct(baimoProductId,
									spaceCommonId, designPlan, userId, mediaType);
							basicModelMap.put(baimoProductConfig.getSmallTypeCode(), baimoProducts);
						}
					}
				}
			}
		}
		return basicModelMap;
	}

	@Override
	public CategoryProductResult assemblyUnityPanProduct(Integer productId, Integer spaceCommonId,
														 DesignPlan designPlan, Integer userId, String mediaType) {
		ProductCategoryRel productCategoryRel = new ProductCategoryRel();
		productCategoryRel.setProductId(productId);
		productCategoryRel.setUserId(userId);
		CategoryProductResult productResult = productCategoryRelMapper
				.getCategoryProductResultByProductId(productCategoryRel);
		if (productResult == null) {
			return productResult;
		}
		BaseProduct baseProduct = null;
		if (Utils.enableRedisCache()) {
			baseProduct = BaseProductCacher.get(productId);
		} else {
			baseProduct = baseProductMapper.selectByPrimaryKey(productId);
		}
		if (baseProduct == null) {
			return productResult;
		}
		String modelId = getU3dModelId(mediaType, baseProduct);
		if (StringUtils.isNotEmpty(modelId)) {
			ResModel resModel = null;
			resModel = resModelService.get(Integer.valueOf(modelId));
			if (resModel != null) {
				productResult.setU3dModelPath(resModel.getModelPath());
				productResult.setProductModelPath(resModel.getModelPath());
				productResult.setModelLength(resModel.getLength());
				productResult.setModelWidth(resModel.getWidth());
				productResult.setModelHeight(resModel.getHeight());
				productResult.setModelMinHeight(resModel.getMinHeight());
			}
		}
		String productTypeValue2 = baseProduct.getProductTypeValue();
		if (StringUtils.isNotBlank(productTypeValue2)) {
			SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType",
					Integer.valueOf(productTypeValue2));
			productResult.setProductTypeValue(productTypeSysDic.getValue());
			productResult.setProductTypeCode(productTypeSysDic.getValuekey());
			productResult.setProductTypeName(productTypeSysDic.getName());
			Integer productSmallTypeValue2 = baseProduct.getProductSmallTypeValue();
			if (productTypeSysDic.getValue() != null && productSmallTypeValue2 != null) {
				SysDictionary productSmallTypeSysDic = sysDictionaryService
						.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue2);
				String rootType = "";
				if(productSmallTypeSysDic!=null) {
					productResult.setProductSmallTypeCode(productSmallTypeSysDic.getValuekey());
					productResult.setProductSmallTypeName(productSmallTypeSysDic.getName());
					productResult.setProductSmallTypeValue(productSmallTypeSysDic.getValue());
					rootType = StringUtils.isEmpty(productSmallTypeSysDic.getAtt1()) ? "2"
							: productSmallTypeSysDic.getAtt1().trim();
				}
				productResult.setRootType(rootType);
				// 获取是否是背景墙
				if ("qiangm".equals(productTypeSysDic.getValuekey())) {
					productResult.setBgWall(Utils
							.getIsBgWall(productSmallTypeSysDic == null ? "" : productSmallTypeSysDic.getValuekey()));
				}
			}
		}
		// 空间ID
		productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
		// 产品长宽高
		productResult.setProductLength(baseProduct.getProductLength());
		productResult.setProductWidth(baseProduct.getProductWidth());
		productResult.setProductHeight(baseProduct.getProductHeight());
		/* 产品材质路径 */
		String materialIds = productResult.getMaterialPicId();
		if (StringUtils.isNotBlank(materialIds)) {
			List<String> idsInfo = Utils.getListFromStr(materialIds);
			List<String> materialPathList = new ArrayList<String>();
			ResTexture resTextureTemp = null;
			for (String idStr : idsInfo) {
				ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));
				if (resTexture != null && resTextureTemp == null) {
					resTextureTemp = resTexture;
					productResult.setTextureAttrValue(resTextureTemp.getTextureAttrValue());
					productResult.setLaymodes(resTextureTemp.getLaymodes());
					productResult.setTextureWidth(resTexture.getFileWidth() + "");
					productResult.setTextureHeight(resTexture.getFileHeight() + "");
				}
				if (resTexture != null && resTexture.getId() != null) {
					materialPathList.add(resTexture.getFilePath());
				}
			}
			if (Lists.isNotEmpty(materialPathList)) {
				productResult.setMaterialPicPaths(materialPathList.toArray(new String[materialPathList.size()]));
			}
		}

		/* 产品材质路径->end */
		// 材质配置文件路径
		Integer materialConfigId = baseProduct.getMaterialFileId();
		if (materialConfigId != null) {
			ResFile resFile = null;
			resFile = resFileService.get(materialConfigId);
			if (resFile != null) {
				productResult.setMaterialConfigPath(resFile.getFilePath());
			}
		}

		productResult.setParentProductId(baseProduct.getParentId());

		// 组装产品的规则
		if (StringUtils.isBlank(baseProduct.getProductTypeMark())
				|| StringUtils.isBlank(baseProduct.getProductSmallType())) {
			SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType",
					Utils.getIntValue(baseProduct.getProductTypeValue()));
			if (bigSd != null) {
				if (StringUtils.isNotEmpty(bigSd.getValuekey()) && baseProduct.getProductSmallTypeValue() != null) {
					SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
							baseProduct.getProductSmallTypeValue());
					if (smallSd != null) {
						baseProduct.setProductSmallTypeMark(smallSd.getValuekey());
					}
				}
				baseProduct.setProductTypeMark(bigSd.getValuekey());
			}
		}

		/*** 在组合产品查询列表 中 增加产品属性 ****/
		Map<String, String> map = new HashMap<String, String>();
		map = productAttributeService.getPropertyMap(productResult.getProductId());
		productResult.setPropertyMap(map);

		Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
				baseProduct.getProductTypeMark(), baseProduct.getProductSmallTypeMark(), spaceCommonId,
				designPlan == null ? null : designPlan.getDesignTemplateId(), new DesignRules(), map);
		productResult.setRulesMap(rulesMap);
		return productResult;
	}

	@Override
	public BaseProduct productMaterial(BaseProduct baseProduct, LoginUser loginUser, ProductVO productVO)
			throws Exception {
		DesignTemplet designTemplet = new DesignTemplet();
		DesignPlan designPlan = null;
		if (productVO.getDesignPlanId() != null) {
			designPlan = designPlanService.get(productVO.getDesignPlanId());
			designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
		}
		// 详情逻辑
		productDetailContent(baseProduct, designPlan, productVO, loginUser);

		if (StringUtils.isBlank(baseProduct.getProductTypeMark())) {
			SysDictionary bigSd = sysDictionaryService.getSysDictionaryByValue("productType",
					Utils.getIntValue(baseProduct.getProductTypeValue()));
			if (bigSd != null) {
				if (StringUtils.isNotEmpty(bigSd.getValuekey()) && baseProduct.getProductSmallTypeValue() != null) {
					SysDictionary smallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
							baseProduct.getProductSmallTypeValue());
					if (smallSd != null) {
						baseProduct.setProductSmallTypeMark(smallSd.getValuekey());
					}
				}
				baseProduct.setProductTypeMark(bigSd.getValuekey());
			}
		}
		// #################产品带上价格单位 satart
		// 产品价格单位名称
		Integer spvValue = baseProduct.getSalePriceValue();
		if (spvValue != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice",
					new Integer(spvValue));
			baseProduct.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
		}
		// #################产品带上价格单位 end
		/*** 在组合产品查询列表 中 增加产品属性 ****/
		Map<String, String> map = new HashMap<String, String>();
		map = productAttributeService.getPropertyMap(baseProduct.getId());
		baseProduct.setPropertyMap(map);

		// 组装产品的规则
		Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(baseProduct.getId().toString(),
				baseProduct.getProductTypeMark(), baseProduct.getProductSmallTypeMark(), productVO.getSpaceCommonId(),
				designTemplet == null ? null : designTemplet.getId(), new DesignRules(), map);
		baseProduct.setRulesMap(rulesMap);
		/* 关联查询材质的两个属性(textureAttrValue,laymodes) */
		if (baseProduct != null && StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
			String materialIds = baseProduct.getMaterialPicIds();
			List<String> idsInfo = Utils.getListFromStr(materialIds);
			if (idsInfo != null && idsInfo.size() > 0) {
				ResTexture resTexture = resTextureService.get(Integer.valueOf(idsInfo.get(0)));
				if (resTexture != null) {
					baseProduct.setTextureAttrValue(resTexture.getTextureAttrValue());
					baseProduct.setLaymodes(resTexture.getLaymodes());
				}
			}
		}
		/* 关联查询材质的两个属性(textureAttrValue,laymodes)->end */
		/* 处理拆分材质产品返回默认材质 */
		String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
		Integer isSplit = 0;
		List<SplitTextureDTO> splitTextureDTOList = null;
		if (StringUtils.isNotBlank(splitTexturesInfo)) {
			Map<String, Object> map1 = dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo, "choose");
			isSplit = (Integer) map1.get("isSplit");
			splitTextureDTOList = (List<SplitTextureDTO>) map1.get("splitTexturesChoose");
		} else {
			/* 普通产品 */
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
					baseProduct.setSplitTexturesChoose(splitTextureDTOList);
				}
			}
		}
		/* 处理拆分材质产品返回默认材质->end */
		baseProduct.setProductId(productVO.getId());
		// Long endTime = System.currentTimeMillis();
		Map<String, CategoryProductResult> baimoRule = setbaimoRuleMap(productVO.getSpaceCommonId(),
				productVO.getMediaType(), loginUser.getId(), baseProduct.getProductSmallTypeCode(), designPlan, map);
		baseProduct.setBasicModelMap(baimoRule);
		return baseProduct;
	}

	// 详情逻辑
	public void productDetailContent(BaseProduct baseProduct, DesignPlan designPlan, ProductVO productVO,
									 LoginUser loginUser) throws Exception {
		// 媒介类型.如果没有值，则表示为web前端（2）

		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setUserId(loginUser.getId());
		userProductCollectSearch.setProductId(baseProduct.getId());
		Integer count = userProductCollectMapper.selectCount(userProductCollectSearch);
		if (count > 0) {
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
		baseProduct.setSplitTexturesInfo(null);
		// 获取不同媒介u3d模型

		String modelId = getU3dModelId(productVO.getMediaType(), baseProduct);

		if (StringUtils.isNotBlank(modelId)) {
			ResModel resModel = resModelService.get(Utils.getIntValue(modelId));
			if (resModel != null) {
				baseProduct.setU3dModelPath(resModel.getModelPath());
				baseProduct.setModelLength(resModel.getLength());
				baseProduct.setModelWidth(resModel.getWidth());
				baseProduct.setModelHeight(resModel.getHeight());
				baseProduct.setModelMinHeight(resModel.getMinHeight());
			}
		}

		Integer brandId = baseProduct.getBrandId();
		if (brandId != null) {
			BaseBrand baseBrand = baseBrandService.get(brandId);
			if (baseBrand != null) {
				baseProduct.setBrandName(baseBrand.getBrandName());
			}
		}

		// 风格信息 ->start
		if (StringUtils.isNotBlank(baseProduct.getProductStyleIdInfo())) {
			JSONObject styleJson = JSONObject.fromObject(baseProduct.getProductStyleIdInfo());
			List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
			StringBuffer stringBuffer = new StringBuffer("");
			for (String str : styleInfoList) {
				stringBuffer.append(str + "  ");
			}
			baseProduct.setProductStyle(stringBuffer.toString());
			baseProduct.setProductStyleInfoList(styleInfoList);
		}
		// 风格信息 ->end

		String productType = baseProduct.getProductTypeValue();
		if (!StringUtils.isEmpty(productType)) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType",
					new Integer(productType));
			baseProduct.setProductType(dictionary.getName());
			baseProduct.setProductTypeKey(dictionary.getValuekey());
			baseProduct.setProductTypeCode(dictionary.getValuekey());
			baseProduct.setProductTypeName(dictionary.getName());
		}

		if (baseProduct.getProductSmallTypeValue() != null
				&& StringUtils.isNotBlank(baseProduct.getProductTypeValue())) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue(baseProduct.getProductTypeKey(),
					baseProduct.getProductSmallTypeValue());
			String rootType = "";
			if(dictionary!=null) {
				baseProduct.setProductSmallTypeCode(dictionary.getValuekey());
				baseProduct.setProductSmallTypeName(dictionary.getName());
				rootType =  StringUtils.isEmpty(dictionary.getAtt1()) ? "2" : dictionary.getAtt1().trim();
				baseProduct.setRootType(rootType);
			}


			// 获取是否是背景墙
			if ("qiangm".equals(baseProduct.getProductTypeKey())) {
				baseProduct.setBgWall(Utils.getIsBgWall(dictionary == null ? "" : dictionary.getValuekey()));
			} else {
				baseProduct.setBgWall(0);
			}
		}

		// 产品图片列表
		String smallPicIds = baseProduct.getPicIds();
		List<ResPic> list2 = new ArrayList<ResPic>();
		if (StringUtils.isNoneBlank(smallPicIds)) {
			String[] strs = smallPicIds.split(",");
			int j = strs.length;
			for (int i = (strs.length - 1); i > -1; i--) {
				ResPic resPic = resPicService.get(Utils.getIntValue(strs[i]));
				if (resPic != null) {
					list2.add(resPic);
				}
			}
		}
		Collections.sort(list2, new Comparator<ResPic>() {
			@Override
			public int compare(ResPic o1, ResPic o2) {
				return (int) (o1.getGmtCreate().getTime() - o2.getGmtCreate().getTime());
			}
		});
		for (ResPic resPic : list2) {
			baseProduct.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
		}
		// 产品图片列表
		for (ResPic resPic : list2) {
			if (null!=resPic&&StringUtils.isNotBlank(resPic.getSmallPicInfo())) {
				Map<String, String> map = Utils.getMapFromStr(resPic.getSmallPicInfo());
				if (StringUtils.isNotBlank(map.get("ipad"))) {
					ResPic ipadPic = resPicService.get(Utils.getIntValue(map.get("ipad")));
					resPic.setIpadThumbnailPath(ipadPic == null ? "" : ipadPic.getPicPath());
				}
				if (StringUtils.isNotBlank(map.get("web"))) {
					ResPic webPic = resPicService.get(Utils.getIntValue(map.get("web")));
					resPic.setWebThumbnailPath(webPic == null ? "" : webPic.getPicPath());
				}
			} else {
				// 如果没有缩略图就显示原图
				resPic.setIpadThumbnailPath(resPic == null ? "" : resPic.getPicPath());
				resPic.setWebThumbnailPath(resPic == null ? "" : resPic.getPicPath());
			}
			baseProduct.getThumbnailList().add(resPic);
		}
		Integer colorId = baseProduct.getColorId();
		if (colorId != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId);
			baseProduct.setProductColorKey(dictionary.getAtt1());
		}
		// 材质
		String materialIds = baseProduct.getMaterialPicIds();
		ResTexture resTexture1 = null;
		if (StringUtils.isNotBlank(materialIds)) {
			resTexture1 = new ResTexture();
			String[] strs = materialIds.split(",");
			baseProduct.setMaterialId(strs[0]);
			resTexture1 = resTextureService.get(Integer.valueOf(strs[0]));
		}
		/* 处理材质返回格式(huangsongbo 2017.1.4) */
		String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
		Integer isSplit = 0;
		List<SplitTextureDTO> splitTextureDTOList = null;
		if (StringUtils.isNotBlank(splitTexturesInfo)) {
			Map<String, Object> map = dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo, "choose");
			isSplit = (Integer) map.get("isSplit");
			splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
		} else {
			if (resTexture1 != null) {
				splitTextureDTOList = new ArrayList<SplitTextureDTO>();
				List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
				SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
				ResTextureDTO resTextureDTO = resTextureService.fromResTexture(resTexture1);
				resTextureDTO.setKey(splitTextureDTO.getKey());
				resTextureDTO.setProductId(baseProduct.getId());
				resTextureDTOList.add(resTextureDTO);
				splitTextureDTO.setList(resTextureDTOList);
				splitTextureDTOList.add(splitTextureDTO);
			}
		}
		baseProduct.setIsSplit(isSplit);
		baseProduct.setSplitTexturesChoose(splitTextureDTOList);
		String mergeIds = getParentIds(baseProduct);
		if (StringUtils.isBlank(mergeIds)) {

			// 材质
			if (StringUtils.isNotBlank(materialIds)) {
				MergeProductResult mergeProductResult = new MergeProductResult();
				mergeProductResult.setState(1);
				String[] strs = materialIds.split(",");
				ResTexture resTexture2 = resTextureService.get(Integer.valueOf(strs[0]));
				if (resTexture2 != null) {
					mergeProductResult.setState(2);
					mergeProductResult.setProductId(baseProduct.getId());
					mergeProductResult.setMaterialName(resTexture2.getFileName());
					mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
					mergeProductResult.setMaterialPicPath(resTexture2.getFilePath());
					baseProduct.getMateriallist().add(mergeProductResult);
				}

				// 材质配置文件路径
				Integer materialConfigId = baseProduct.getMaterialFileId();
				if (materialConfigId != null) {
					ResFile resFile = resFileService.get(materialConfigId);
					if (resFile != null) {
						baseProduct.setMaterialConfigPath(resFile.getFilePath());
					}
				}
			}
		}
		List<Integer> proIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(mergeIds)) {
			String arr[] = mergeIds.split(",");
			String colorValue = ".";
			String materialName = ".";
			for (String mergeId : arr) {
				proIdList.add(Integer.parseInt(mergeId));
				BaseProduct bp = get(Utils.getIntValue(mergeId));
				if (bp != null) {
					// 材质
					String materialPicIds = bp.getMaterialPicIds();
					if (StringUtils.isNotBlank(materialPicIds)) {
						MergeProductResult mergeProductResult = new MergeProductResult();
						mergeProductResult.setState(1);
						String[] strs = materialPicIds.split(",");
						ResTexture resTexture = resTextureService.get(Integer.valueOf(strs[0]));
						// ResPic resPic =
						// resPicService.get(Utils.getIntValue(strs[0]));
						if (resTexture != null) {
							if (materialName.indexOf(("." + resTexture.getFileName() + ".")) == -1) {
								// 页面默认选中材质（状态2）
								if (resTexture1 != null) {
									if (resTexture1.getFileName().equals(resTexture.getFileName())) {
										mergeProductResult.setState(2);
									}
								}
								if (mergeId.equals(baseProduct.getId() + "")) {
									mergeProductResult.setState(2);
								}
								mergeProductResult.setProductId(Utils.getIntValue(mergeId));
								mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
								mergeProductResult.setMaterialName(resTexture.getFileName());
								mergeProductResult.setMaterialPicPath(resTexture.getFilePath());
								baseProduct.getMateriallist().add(mergeProductResult);
							}
							materialName = materialName + resTexture.getFileName() + ".";
						}
					}

					// 材质配置文件路径
					Integer materialConfigId = baseProduct.getMaterialFileId();
					if (materialConfigId != null) {
						ResFile resFile = resFileService.get(materialConfigId);
						if (resFile != null) {
							baseProduct.setMaterialConfigPath(resFile.getFilePath());
						}
					}
				}
			}
		}

		// 产品属性展示
		if (CustomerListUtils.isEmpty(proIdList)) {
			proIdList.add(baseProduct.getId());
		}
		if (CustomerListUtils.isNotEmpty(proIdList)) {
			Map<String, List<ProductAttribute>> map = new HashMap<String, List<ProductAttribute>>();
			List<ProductAttribute> list = new ArrayList<ProductAttribute>();
			ProductAttribute attribute = new ProductAttribute();
			attribute.setProductIdList(proIdList);
			// 查询合并的产品所有属性
			list = productAttributeService.getMergeAttribute(attribute);
			// 过滤同attributeKey的属性
			Set<String> set = new HashSet<String>();
			if (CustomerListUtils.isNotEmpty(list)) {
				for (ProductAttribute pa : list) {
					set.add(pa.getAttributeKey());
				}
			}
			// 匹配产品属性
			if (CustomerListUtils.isNotEmpty(set)) {
				for (String str : set) {
					ProductAttribute attributeKey = new ProductAttribute();
					attributeKey.setProductIdList(proIdList);
					attributeKey.setAttributeKey(str);
					List<ProductAttribute> keyList = productAttributeService.getMergeAttribute(attributeKey);
					List<ProductAttribute> valueKeyList = new ArrayList<ProductAttribute>();
					if (CustomerListUtils.isNotEmpty(keyList)) {
						for (int i = 0; i < keyList.size(); i++) {
							boolean flag = true;
							ProductAttribute pai = keyList.get(i);
							for (int j = 0; j < i; j++) {
								ProductAttribute paj = keyList.get(j);
								if (paj.getAttributeValueKey().equals(pai.getAttributeValueKey())) {
									if (pai.getProductId().intValue() == baseProduct.getId().intValue()) {
										ProductAttribute pak = valueKeyList.get(j);
										pak.setState(2);
									}
									flag = false;
									break;
								}
							}
							if (flag) {
								if (pai.getProductId().intValue() == baseProduct.getId().intValue()) {
									pai.setState(2);
								}
								valueKeyList.add(pai);
							}
						}
						map.put(valueKeyList.get(0).getAttributeName(), valueKeyList);
					}
				}
			}
			baseProduct.setMap(map);
		}
		baseProduct.setParentProductId(baseProduct.getParentId());

		// 贴图产品获取白模模型
		if (StringUtils.isNotBlank(materialIds)) {
			List<String> idsInfo = Utils.getListFromStr(materialIds);
			List<String> materialPathList = new ArrayList<String>();
			for (String idStr : idsInfo) {
				ResTexture resTexture = resTextureService.get(Integer.valueOf(idStr));
				if (resTexture != null && resTexture.getId() != null) {
					materialPathList.add(resTexture.getFilePath());
				}
			}
			if (Lists.isNotEmpty(materialPathList)) {
				baseProduct.setMaterialPicPaths(materialPathList.toArray(new String[materialPathList.size()]));
				if (StringUtils.isNotBlank(productVO.getPlanProductId())) {
					DesignPlanProduct dpp = designPlanProductService
							.get(Utils.getIntValue(productVO.getPlanProductId()));
					if (dpp != null) {
						BaseProduct product = get(dpp.getProductId());
						boolean isHard = false;
						if (product != null) {
							isHard = isHard(product);
						}
						if (isHard) {
							BaseProduct baimoProduct = get(dpp.getInitProductId());
							// 获取不同媒介u3d模型
							String u3dModelId = getU3dModelId(productVO.getMediaType(), baimoProduct);
							if (StringUtils.isNotBlank(u3dModelId)) {
								ResModel resModel = resModelService.get(Integer.valueOf(u3dModelId));
								if (resModel != null) {
									baseProduct.setU3dModelPath(resModel.getModelPath());
								}
							}
						}
					}
				}
			}
		}
	}

	public String getParentIds(BaseProduct baseProduct) {
		StringBuffer parentIds = new StringBuffer();
		List<BaseProduct> list = new ArrayList<>();
		if (baseProduct.getParentId() != null && baseProduct.getParentId() > 0) {
			BaseProduct product = new BaseProduct();
			product.setParentId(baseProduct.getParentId());
			product.setIsDeleted(0);
			list = getList(product);
			if (Lists.isNotEmpty(list)) {
				for (BaseProduct bp : list) {
					parentIds.append(bp.getId()).append(",");
				}
			}
		}
		if (StringUtils.isNotBlank(parentIds)) {
			return parentIds.toString().substring(0, parentIds.length() - 1);
		} else {
			return parentIds.toString();
		}
	}

	/**
	 * 设置查询条件,从序列号信息中获取
	 *
	 * @param authorizedConfig
	 * @return
	 * @author huangsongbo
	 */
	@Override
	public BaseProduct getBaseProductFromAuthorizedConfig(AuthorizedConfig authorizedConfig) {
		BaseProduct baseProduct = new BaseProduct();
		/* 去重复,序列号只能绑定一个品牌,一个大类,一个小类,因对于以前的错误数据 */
		authorizedConfigService.dealWithRepeat(authorizedConfig);
		/* 品牌 */
		String brandId = authorizedConfig.getBrandIds();
		Integer brandIdInt = null;
		try {
			brandIdInt = Integer.valueOf(brandId);
		} catch (Exception e) {

		}
		if (brandIdInt != null) {
			BaseBrand baseBrand = baseBrandService.get(brandIdInt);
			if (baseBrand != null) {
				baseProduct.setStatusShowWu(baseBrand.getStatusShowWu());
			}
		}
		if (StringUtils.isNotBlank(brandId)) {
			baseProduct.setBrandId(Integer.parseInt(brandId));
		}
		/* 品牌->end */
		/* 大类 */
		String bigType = authorizedConfig.getBigType();
		if (StringUtils.isNotBlank(bigType)) {
			SysDictionary sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey("productType", bigType);
			if (sysDictionaryBigType == null) {
				logger.info("------数据字典未找到:(type:productType,valuekey:" + bigType + ")");
			} else {
				baseProduct.setProductTypeValue("" + sysDictionaryBigType.getValue());
			}
		}
		/* 大类->end */
		/* 小类 */
		String smallType = authorizedConfig.getSmallTypeIds();
		if (StringUtils.isNotBlank(smallType)) {
			SysDictionary sysDictionarySmallType = sysDictionaryService.get(Integer.parseInt(smallType));
			if (sysDictionarySmallType == null) {
				logger.info("------数据字典未找到:(id:" + smallType + ")");
			} else {
				baseProduct.setProductSmallTypeValue(sysDictionarySmallType.getValue());
			}
		}
		/* 小类->end */
		/* 产品ids */
		String productIds = authorizedConfig.getProductIds();
		List<Integer> productIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(productIds)) {
			List<String> productIdStrList = Utils.getListFromStr(productIds);
			if (productIdStrList != null && productIdStrList.size() > 0) {
				for (String productIdStr : productIdStrList) {
					productIdList.add(Integer.parseInt(productIdStr));
				}
			}
		}
		baseProduct.setProductIdList(productIdList);
		/* 产品ids->end */
		return baseProduct;
	}

	@Override
	public List<List<ProCategoryPo>> getAllProductCategory(Integer companyId) {
		List<List<ProCategoryPo>> list = new ArrayList<>();
		//查询出公司的分类并重新过滤封装
		List<ProCategoryPo> allProductCategory = baseProductMapper.selectAllProductCategory(companyId);


		List<ProCategoryPo> oneLevelCategory = new ArrayList<>();
		List<ProCategoryPo> twoLevelCategory = new ArrayList<>();
		List<ProCategoryPo> threeLevelCategory = new ArrayList<>();
		List<ProCategoryPo> fourLevelCategory = new ArrayList<>();
		List<ProCategoryPo> fiveLevelCategory = new ArrayList<>();
		for (ProCategoryPo proCategoryPo : allProductCategory) {
			if(proCategoryPo.getLevel()==1) {
				oneLevelCategory.add(proCategoryPo);
			}else if (proCategoryPo.getLevel()==2) {
				twoLevelCategory.add(proCategoryPo);
			}else if (proCategoryPo.getLevel()==3) {
				threeLevelCategory.add(proCategoryPo);
			}else if (proCategoryPo.getLevel()==4){
				fourLevelCategory.add(proCategoryPo);
			}else if(proCategoryPo.getLevel()==5){
				fiveLevelCategory.add(proCategoryPo);
			}
		}
		List<Integer> ids = new ArrayList<>();
		//根据PID查询出产品的所有分类
		if(oneLevelCategory!=null&&twoLevelCategory!=null&&threeLevelCategory!=null
				&& oneLevelCategory.size() > 0 && twoLevelCategory.size()>0 && threeLevelCategory.size()>0
				&& companyId !=0) {
			for (ProCategoryPo proCategoryPo : threeLevelCategory) {
				ids.add(proCategoryPo.getId());
			}
			fourLevelCategory = baseProductMapper.selectAllProductCategoryByids(ids);
			ids.clear();
			for (ProCategoryPo proCategoryPo : fourLevelCategory) {
				ids.add(proCategoryPo.getId());
			}
			if(companyId != 0 &&null != ids && ids.size() > 0 ){
				fiveLevelCategory = baseProductMapper.selectAllProductCategoryByids2(ids,companyId);
			}

		}


		//查询公司的主营分类
		String allCompanyMainCategory = baseProductMapper.selectAllCompanyMainCategory(companyId);
		if(StringUtils.isNotBlank(allCompanyMainCategory)&&twoLevelCategory!=null){
			List<String> companyMainCategoryList = Utils.getListFromStr(allCompanyMainCategory);
			for (String companyMainCategory : companyMainCategoryList ){
				for (ProCategoryPo proCategoryPo : twoLevelCategory) {
					if(proCategoryPo.getId().intValue()==Integer.valueOf(companyMainCategory).intValue()){
						proCategoryPo.setCompanyMainCategory(Integer.valueOf(companyMainCategory));
					}
				}
			}
			//根据公司主营分类对二级分类进行排序
			Collections.sort(twoLevelCategory, new Comparator<ProCategoryPo>() {
				@Override
				public int compare(ProCategoryPo o1, ProCategoryPo o2) {
					if(o1.getCompanyMainCategory()==null&&o2.getCompanyMainCategory()==null){
						return 0;
					}

					if(o1.getCompanyMainCategory()!=null&&o2.getCompanyMainCategory()==null){
						return -1;
					}

					if(o1.getCompanyMainCategory()==null&&o2.getCompanyMainCategory()!=null){
						return 1;
					}

					if(o1.getCompanyMainCategory()!=null&&o2.getCompanyMainCategory()!=null){
						int i1 = companyMainCategoryList.indexOf(o1.getCompanyMainCategory().toString());
						int i2 = companyMainCategoryList.indexOf(o2.getCompanyMainCategory().toString());
						return i1-i2;
					}

					return 0;
				}
			});

		}

		list.add(oneLevelCategory);
		list.add(twoLevelCategory);
		list.add(threeLevelCategory);
		list.add(fourLevelCategory);
		list.add(fiveLevelCategory);

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProducDetail getProducDetail(BaseProduct baseProduct, UserSO userSo) {
		Integer productId = baseProduct.getId();
		Integer isSandu = baseProduct.getIsSandu();
		Integer platformId = baseProduct.getPlatformId();
		ProducDetail productDetail = null;
		if(null!=productId&&productId.intValue()>0 && null!= platformId && platformId.intValue() > 0) {
			productDetail = baseProductMapper.selectProductDetail(baseProduct);
		}
		if (null == productDetail) {
			return productDetail;
		}

		//add by zhoujc 2019-1-28
		//处理方案中产品默认材质状态
		if (baseProduct.getPlanId() > 0) {
			int planId = baseProduct.getPlanId();
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(planId);
			if (designPlanRenderScene != null) {
				planId = designPlanRenderScene.getDesignPlanId();
				DesignPlanProduct query = new DesignPlanProduct();
				query.setIsDeleted(0);
				query.setPlanId(planId);
				query.setProductId(productDetail.getProductId());
				List<DesignPlanProduct> planProducts = designPlanProductService.getList(query);
				if (!planProducts.isEmpty()) {
					String planSplitTextures = planProducts.get(0).getSplitTexturesChooseInfo();
					productDetail.setSplitTexturesInfo(planSplitTextures);
				}
			}
		}

		//查询产品价格
		if(null==productDetail.getSalePrice()||productDetail.getSalePrice()<0) {
			productDetail.setSalePrice(0.0);
		}
		Integer companyId = baseProduct.getCompanyId();

			//产品不是所属登录公司的话把价格设置为0
			if (companyId != null && !companyId.equals(productDetail.getCompanyId()) && isSandu.intValue() == 0)
			{
				productDetail.setSalePrice(0.0);
			}
		// 查询该产品的风格
		if (StringUtils.isNotEmpty(productDetail.getProductStyleIdInfo())) {
			String[] split = productDetail.getProductStyleIdInfo().split(",");
			List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(split);
			productDetail.setProductStyleInfoList(styleInfoList);
		}

		// 查询该产品的类型
		if (null != productDetail.getProductTypeValue() && productDetail.getProductTypeValue() > 0) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType",
					productDetail.getProductTypeValue());
			productDetail.setProductTypeName(dictionary.getName());
		}

		// 产品图片列表
		String smallPicIds = productDetail.getPicIds();
		List<ResPic> list2 = new ArrayList<ResPic>();
		if (StringUtils.isNotBlank(smallPicIds)) {
			String[] strs = smallPicIds.split(",");
			int j = strs.length;
			for (int i = (strs.length - 1); i > -1; i--) {
				ResPic resPic = resPicService.get(Utils.getIntValue(strs[i]));
				if (resPic != null) {
/*					if ("product.baseProduct.piclist".equals(resPic.getFileKey())) {
					list2.add(resPic);
					}*/
					list2.add(resPic);
				}
			}
		}
		for (ResPic resPic : list2) {
			productDetail.getSmallPiclist().add(resPic == null ? "" : resPic.getPicPath());
		}
		for (ResPic resPic : list2) {
			if (null!=resPic&&StringUtils.isNotBlank(resPic.getSmallPicInfo())) {
				Map<String, String> map = Utils.getMapFromStr(resPic.getSmallPicInfo());
				if (StringUtils.isNotBlank(map.get("ipad"))) {
					ResPic ipadPic = resPicService.get(Utils.getIntValue(map.get("ipad")));
					resPic.setIpadThumbnailPath(ipadPic == null ? "" : ipadPic.getPicPath());
				}
				if (StringUtils.isNotBlank(map.get("web"))) {
					ResPic webPic = resPicService.get(Utils.getIntValue(map.get("web")));
					resPic.setWebThumbnailPath(webPic == null ? "" : webPic.getPicPath());
				}
			} else {
				// 如果没有缩略图就显示原图
				resPic.setIpadThumbnailPath(resPic == null ? "" : resPic.getPicPath());
				resPic.setWebThumbnailPath(resPic == null ? "" : resPic.getPicPath());
			}
			productDetail.getThumbnailList().add(resPic);
		}

		// 查询该产品的所有材质
		String texturesInfo = productDetail.getSplitTexturesInfo();
		if(StringUtils.isNotBlank(texturesInfo)) {
			//JSONArray jsonArray = JSONArray.fromObject(texturesInfo);
			//List<SplitTextureInfoDTO> splitTextureList = JSONArray.toList(jsonArray, SplitTextureInfoDTO.class);
			Type type = new TypeToken<List<SplitTextureInfoDTO>>(){}.getType();
			List<SplitTextureInfoDTO> splitTextureList = gson.fromJson(texturesInfo, type);
			if (null != splitTextureList && splitTextureList.size() > 0) {
				for (SplitTextureInfoDTO splitTextureInfoDTO : splitTextureList) {
					String str = splitTextureInfoDTO.getTextureIds();
					Integer defaultId = splitTextureInfoDTO.getDefaultId();
					if(StringUtils.isNotEmpty(str)) {
						String[] ids = str.split(",");
						for (int i = 0; i < ids.length; i++) {
							if(defaultId.intValue()==Integer.parseInt(ids[i])) {
								String temp = ids[0];
								ids[0] = defaultId+"";
								ids[i] = temp;
							}
						}
						String newStr = StringUtils.join(ids, ",");
						splitTextureInfoDTO.setTextureIds(newStr);
					}
					List<String> textureIdStrList = Utils.getListFromStr(splitTextureInfoDTO.getTextureIds());
					List<ResTexture> textureList = null;
					if (textureIdStrList != null && textureIdStrList.size() > 0) {
						/* 优化后 */
						ResTexture resTexture_ = new ResTexture();
						resTexture_.setResTextureIds(textureIdStrList);
						textureList = resTextureService.getBatchGet(resTexture_);
						if (textureList != null) {
							textureList.sort(Comparator.comparing(it ->
									it.getId().equals(splitTextureInfoDTO.getDefaultId()) ? -1 : 1)
							);
						}
					}
					splitTextureInfoDTO.setTextureList(textureList);
				}
			}
			productDetail.setSplitTextureList(splitTextureList);
		}


		// 查询该产品是否被收藏
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setProductId(productId);
		if (userSo != null) {
			userProductCollectSearch.setUserId(userSo.getUserId());
		} else {
			userProductCollectSearch.setUserId(0);
		}
		int count = userProductCollectMapper.selectCount(userProductCollectSearch);
		if (count > 0) {
			productDetail.setIsFavorite(1);
		} else {
			productDetail.setIsFavorite(0);
		}

		return productDetail;
	}


	@Override
	public List<ProductListVo> getAllProductByIds(List<Integer> ids,PageModel pageModel,UserSO userSo) {
		Integer start = 0;
		Integer limit = 0;
		if (null != pageModel && 0 != pageModel.getPageSize()) {
			start =pageModel.getStart();
			limit =pageModel.getPageSize();
		} else {
			limit = PageModel.DEFAULT_PAGE_PAGESIZE;
		}

		List<ProductListVo> products = baseProductMapper.selectAllProductByIds(ids,start,limit);

		if(products==null||products.size()==0) {
			return products;
		}
		for (ProductListVo productListVo : products) {
			// 查询该产品的风格
			if (StringUtils.isNotBlank(productListVo.getProductStyleIdInfo())) {
				JSONObject styleJson = JSONObject.fromObject(productListVo.getProductStyleIdInfo());
				List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
				StringBuffer stringBuffer = new StringBuffer("");
				for (String str : styleInfoList) {
					stringBuffer.append(str + "  ");
				}
				productListVo.setProductStyleInfoList(styleInfoList);
			}

			// 查询该产品是否被收藏
			UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
			userProductCollectSearch.setProductId(productListVo.getProductId());
			if (userSo != null) {
				userProductCollectSearch.setUserId(userSo.getUserId());
			} else {
				userProductCollectSearch.setUserId(0);
			}
			int count = userProductCollectMapper.selectCount(userProductCollectSearch);
			if (count > 0) {
				productListVo.setIsFavorite(1);
			} else {
				productListVo.setIsFavorite(0);
			}

		}
		//根据id排序
		List<ProductListVo> newProducts = new ArrayList<>();
		for (Integer id : ids) {
			for (ProductListVo productListVo : products) {
				if(productListVo.getProductId().intValue()==id.intValue()) {
					newProducts.add(productListVo);
				}
			}
		}
		return newProducts;
	}

	@Override
	public Integer getAllProductCountByIds(List<Integer> ids) {

		return baseProductMapper.selectAllProductCountByIds(ids);
	}


	/**
	 * 获得产品的缩略图url
	 * @return
	 */
	@Override
	public String getPicUrlFromProductId(Integer productId) {
		String url="";
		BaseProduct baseProduct=get(productId);
		if (baseProduct == null) {
			return url;
		}
		if (baseProduct.getPicId() == null) {
			return url;
		}
		ResPic resPic=resPicService.get(baseProduct.getPicId());
		if (resPic == null) {
			return url;
		}
		if (StringUtils.isBlank(url)) {
			url=resPic.getPicPath();
		}
		return url;
	}

	@Override
	public Integer getProductCompanyId(Integer productId) {
		return baseProductMapper.getProductCompanyId(productId);
	}

	@Override
	public String getProductCompanyDomainName(Integer productId) {
		return baseProductMapper.getProductCompanyDomainName(productId);
	}

	@Override
	public List<BaseProduct> getAllSameTypeProduct(BaseProduct baseProduct) {
		BaseProduct product = baseProductMapper.selectByPrimaryKey(baseProduct.getId());
		if(null==product.getParentId()||product.getParentId()==0){
			return null;
		}
		return baseProductMapper.selectAllSameTypeProduct(product.getParentId());
	}

	/**
	 * 根据可见范围查询到所有可见的分类编码
	 */
	@Override
	public List<String> getCodeListByIdList(List<Integer> visibilityRangeIdList) {// TODO Auto-generated method stub
		return baseProductMapper.getCodeListByIdList(visibilityRangeIdList);
	}

	@Override
	public List<ProCategoryPo> getExactProductCategory(Integer pid) {
		List<ProCategoryPo> categoryPos = baseProductMapper.selectExactProductCategory(pid);
		List<ProCategoryPo> newCategoryList = new ArrayList<>();
		for(ProCategoryPo fourProCategoryPo : categoryPos){
			if(fourProCategoryPo.getLevel()==4){
				List<ProCategoryPo> fiveProCategoryList = new ArrayList<>();
				for(ProCategoryPo fiveProCategoryPo : categoryPos){
					if(fiveProCategoryPo.getLevel()==5 && fourProCategoryPo.getId().intValue()==fiveProCategoryPo.getPid().intValue()){
						fiveProCategoryList.add(fiveProCategoryPo);
					}
				}
				fourProCategoryPo.setCategories(fiveProCategoryList);
				newCategoryList.add(fourProCategoryPo);
			}
		}

		return newCategoryList;
	}

	@Override
	public BigDecimal getProductExpense(Integer goodsSpuId) {
		return baseProductMapper.selectProductExpense(goodsSpuId);
	}

	@Override
	public Integer getProductPlatformPrice(Integer productId) {
		return baseProductMapper.selectProductPlatformPrice(productId);
	}

	@Override
	public List<BaseProduct> getProductListByType(List<Integer> productIds, Integer productBatchType) {
		return baseProductMapper.getProductListByType(productIds,productBatchType);
	}


}