package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.util.Utils;
import com.nork.design.cache.DesignCacher;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.controller2.web.StructureProductParameter;
import com.nork.product.dao.UserProductCollectMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.UserProductsConllect;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductServiceV2;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service2.UserProductCollectServiceV2;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: UserProductCollectServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-我的产品收藏ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 18:27:21
 * @version V1.0   
 */
@Service("userProductCollectService2")
@Transactional
public class UserProductCollectServiceImplV2 implements UserProductCollectServiceV2 {
	private static Logger logger = Logger.getLogger(UserProductCollectServiceImplV2.class);
	private UserProductCollectMapper userProductCollectMapper;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private BaseProductServiceV2 baseBrandServiceV2;
	@Autowired
	public void setUserProductCollectMapper(
			UserProductCollectMapper userProductCollectMapper) {
		this.userProductCollectMapper = userProductCollectMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userProductCollect
	 * @return  int 
	 */
	@Override
	public int add(UserProductCollect userProductCollect) {
		userProductCollectMapper.insertSelective(userProductCollect);
		return userProductCollect.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param userProductCollect
	 * @return  int 
	 */
	@Override
	public int update(UserProductCollect userProductCollect) {
		return userProductCollectMapper
				.updateByPrimaryKeySelective(userProductCollect);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return userProductCollectMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserProductCollect 
	 */
	@Override
	public UserProductCollect get(Integer id) {
		return userProductCollectMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userProductCollect
	 * @return   List<UserProductCollect>
	 */
	@Override
	public List<UserProductCollect> getList(UserProductCollect userProductCollect) {
	    return userProductCollectMapper.selectList(userProductCollect);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userProductCollect
	 * @return   int
	 */
	@Override
	public int getCount(UserProductCollectSearch userProductCollectSearch){
		return  userProductCollectMapper.selectCount(userProductCollectSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  userProductCollect
	 * @return   List<UserProductCollect>
	 */
	@Override
	public List<UserProductCollect> getPaginatedList(
			UserProductCollectSearch userProductCollectSearch) {
		return userProductCollectMapper.selectPaginatedList(userProductCollectSearch);
	}

	@Override
	public List<UserProductsConllect> getUserProductsConllectList(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.getUserProductsConllectList(userProductsConllect);
	}

	@Override
	public UserProductCollect getUserProductConllect(UserProductCollect userProductCollect) {
		return userProductCollectMapper.getUserProductConllect(userProductCollect);
	}

	@Override
	public List<UserProductsConllect> selectUserNamelist(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.selectUserNamelist(userProductsConllect);
	}

	@Override
	public int getUserProductsConllectCount(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.getUserProductsConllectCount(userProductsConllect);
	}

	@Override
	public Integer cancelConllect(UserProductCollect userProductCollect) {
		return userProductCollectMapper.cancelConllect(userProductCollect);
	}

	@Override
	public List<UserProductsConllect> getAllList(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.getAllList(userProductsConllect);
	}

	@Override
	public void transferCollection(
			UserProductCollectSearch userProductCollectSearch) {
		userProductCollectMapper.transferCollection(userProductCollectSearch);
		
	}

	@Override
	public List getUserProductsCollectByValue(
			List<Map<Object, Object>> valueList) {
		return userProductCollectMapper.getUserProductsCollectByValue(valueList);
		
	}

	@Override
	public int getUserProductsConllectCount_All(
			UserProductsConllect userProductsCollect) {
		return userProductCollectMapper.getUserProductsConllectCount_All(userProductsCollect);
	}

	@Override
	public List<UserProductsConllect> getAllList_All(
			UserProductsConllect userProductsCollect) {
		return userProductCollectMapper.getAllList_All(userProductsCollect);
	}

	@Override
	public int deleteBatch(List<Integer> list) {
		return userProductCollectMapper.deleteBatch(list);
	}

	/**
	 * 产品收藏列表----接口
	 */
	public StructureProductParameter collectionList(UserProductsConllect userProductsCollect,StructureProductParameter sParameter) {
		int total = 0;
		List<UserProductsConllect> list = null;
		/** 获取该用户绑定的序列号品牌 */
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(sParameter.getLoginUser().getId());
		authorizedConfig.setState(1);
		List<AuthorizedConfig> authorizedList = new ArrayList<>();
		authorizedList = authorizedConfigService.getList(authorizedConfig);
		String brandIds = "";
		for (AuthorizedConfig ac : authorizedList) {
			if (StringUtils.isNotBlank(ac.getBrandIds())) {
				brandIds += ac.getBrandIds() + ",";
			}
		}
		SysDictionary sysDictionary = new SysDictionary();
		String ProductTypeValue = userProductsCollect.getProductTypeValue();
		String smallValueKeyS = null;
		StringBuffer smallValueKeyS_ = new StringBuffer("");
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();/* 1为外网 2 为内网 默认为外网 */
		if (sParameter.getLoginUser().getUserType() == 1 && "2".equals(versionType)) {
			userProductsCollect.setIsInternalUser("yes");
		}

		if (ProductTypeValue != null && !"".equals(ProductTypeValue)) {
			sysDictionary.setValue(Integer.parseInt(userProductsCollect.getProductTypeValue()));
			sysDictionary.setType("classifiedCollect");
			List<SysDictionary> sysDictionaryList = sysDictionaryService.getList(sysDictionary);
			if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
				smallValueKeyS = sysDictionaryList.get(0).getAtt1(); /** 2.通过收藏分类ID 获取 下面的产品小分类 */
				if (smallValueKeyS == null || "".equals(smallValueKeyS)) {
					smallValueKeyS = "";
				}
			}
			userProductsCollect.setSmallValueKeyS(smallValueKeyS);
			userProductsCollect.setUserId(sParameter.getLoginUser().getId());
			total = getUserProductsConllectCount(userProductsCollect);
			sParameter.setTotal(total);
			logger.info("total:" + total);
			list = getAllList(userProductsCollect);
			sParameter.setUpcList(list);
			if (list != null && list.size() > 0) {
				for (UserProductsConllect productCollect : list) {
					int brandState = 0;
					int proId = productCollect.getProductId() != null ? Integer.valueOf(productCollect.getProductId()) : -1;
					BaseProduct baseProduct = null;
					baseProduct = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? BaseProductCacher.get(proId) : baseProductService.get(proId);
					if (baseProduct != null) {
						if (brandState == 0) {
							/** 品牌是否被绑定状态 */
							String productBrandId = "," + baseProduct.getBrandId() + ",";
							if (StringUtils.isEmpty(brandIds)) {
								brandState = brandState + 1;
							}
							if (("," + brandIds).indexOf(productBrandId) == -1) {
								brandState = brandState + 1;
							}
						}
					}
					if (brandState > 0) {
						productCollect.setSalePrice(-1.0);
					} else {
						// 带上销售价格单位
						if (productCollect.getProductId().length() > 0) {
							Integer productId = Integer.parseInt(productCollect.getProductId());
							if (productId != null) {
								BaseProduct basePro = baseProductService.get(productId);
								Integer spvValue = basePro.getSalePriceValue();
								if (spvValue != null) {
									SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice", spvValue);
									productCollect.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
								}
							}
						}
					}

				}
			}
		}
		if (ProductTypeValue == null || "".equals(ProductTypeValue)) {
			userProductsCollect.setUserId(sParameter.getLoginUser().getId());
			total = getUserProductsConllectCount_All(userProductsCollect);
			sParameter.setTotal(total);
			logger.info("total:" + total);
			list = getAllList_All(userProductsCollect);
			sParameter.setUpcList(list);
			if (list != null && list.size() > 0) {
				for (UserProductsConllect productCollect : list) {
					int brandState = 0;
					int proId = productCollect.getProductId() != null ? Integer.valueOf(productCollect.getProductId()) : -1;
					BaseProduct baseProduct = null;
					baseProduct = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? BaseProductCacher.get(proId) : baseProductService.get(proId);
					if (baseProduct != null) {
						if (brandState == 0) {
							/** 品牌是否被绑定状态 */
							String productBrandId = "," + baseProduct.getBrandId() + ",";
							if (StringUtils.isEmpty(brandIds)) {
								brandState = brandState + 1;
							}
							if (("," + brandIds).indexOf(productBrandId) == -1) {
								brandState = brandState + 1;
							}
						}
					}
					if (brandState > 0) {
						productCollect.setSalePrice(-1.0);
					} else {
						// 带上销售价格单位
						if (productCollect.getProductId().length() > 0) {
							Integer productId = Integer.parseInt(productCollect.getProductId());
							if (productId != null) {
								BaseProduct basePro = baseProductService.get(productId);
								Integer spvValue = basePro.getSalePriceValue();
								if (spvValue != null) {
									SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productUnitPrice", spvValue);
									productCollect.setSalePriceValueName(dictionary == null ? "" : dictionary.getName());
								}
							}
						}
					}

				}
			}
		}

		// 媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = SystemCommonUtil.getMediaType(sParameter.getRequest());
		if (list != null && list.size() > 0) {
			for (UserProductsConllect productCollect : list) {
				int brandState = 0;
				int proId = productCollect.getProductId() != null ? Integer.valueOf(productCollect.getProductId()) : -1;
				BaseProduct baseProduct = null;
				baseProduct = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? BaseProductCacher.get(proId) : baseProductService.get(proId);
				if (baseProduct != null) {
					if (brandState == 0) {
						/** 品牌是否被绑定状态 */
						String productBrandId = "," + baseProduct.getBrandId() + ",";
						if (StringUtils.isEmpty(brandIds)) {
							brandState = brandState + 1;
						}
						if (("," + brandIds).indexOf(productBrandId) == -1) {
							brandState = brandState + 1;
						}
					}
					if (brandState > 0) {
						productCollect.setSalePrice(-1.0);
					}
					// 产品大类
					String productType = baseProduct.getProductTypeValue();
					SysDictionary dictionary = new SysDictionary();
					if (StringUtils.isNotEmpty(productType)) {
						dictionary = sysDictionaryService.getSysDictionaryByValue("productType",new Integer(productType));
						productCollect.setProductType(dictionary.getName());
						productCollect.setProductTypeKey(dictionary.getValuekey());
						productCollect.setProductTypeCode(dictionary.getValuekey());
						productCollect.setProductTypeName(dictionary.getName());
					}
					if (StringUtils.isNotBlank(productType) && baseProduct.getProductSmallTypeValue() != null) {
						SysDictionary sd = sysDictionaryService.getSysDictionaryByValue(dictionary.getValuekey(),baseProduct.getProductSmallTypeValue());
						productCollect.setProductSmallTypeCode(sd.getValuekey());
						productCollect.setProductSmallTypeName(sd.getName());
						productCollect.setProductSmallTypeValue(baseProduct.getProductSmallTypeValue());
						String rootType = StringUtils.isEmpty(sd.getAtt1()) ? "2" : sd.getAtt1().trim();
						productCollect.setRootType(rootType);
					}
					String materialPicIds = baseProduct.getMaterialPicIds();
					if (StringUtils.isNotBlank(materialPicIds)) {
						String[] mIds = materialPicIds.split(",");
						if (mIds != null) {
							ResPic resPic = new ResPic();
							resPic.setFileKey("product.baseProduct.material");
							resPic.setBusinessId(baseProduct.getId());
							resPic.setOrders(" sequence asc ");
							List<ResPic> materialPics = null;
							materialPics = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? 
							ResourceCacher.getAllPicList(resPic) : resPicService.getList(resPic);
							if (materialPics != null && materialPics.size() > 0) {
								String[] materialPaths = new String[materialPics.size()];
								for (int j = 0; j < materialPics.size(); j++) {
									resPic = materialPics.get(j);
									if (resPic != null) {
										materialPaths[j] = resPic.getPicPath();
									}
								}
								productCollect.setMaterialPicPaths(materialPaths);
							}
						}
					}
					DesignPlan designPlan = null;
					DesignTemplet designTemplet = new DesignTemplet();
					if (sParameter.getDesignPlanId() != null) {
						designPlan = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) 
								? DesignCacher.get(sParameter.getDesignPlanId()) : designPlanService.get(sParameter.getDesignPlanId());
						if (designPlan != null) {
							designTemplet = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? 
									DesignTempletCacher.get(designPlan.getDesignTemplateId()) : designTempletService.get(designPlan.getDesignTemplateId());
						}
					}

					// 材质配置文件路径
					Integer materialConfigId = baseProduct.getMaterialFileId();
					if (materialConfigId != null) {
						ResFile resFile = null;
						resFile = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? 
							ResourceCacher.getFile(materialConfigId) : resFileService.get(materialConfigId);
						if (resFile != null) {
							productCollect.setMaterialConfigPath(resFile.getFilePath());
						}
					}
					productCollect.setProductLength(baseProduct.getProductLength());
					productCollect.setProductWidth(baseProduct.getProductWidth());
					productCollect.setProductHeight(baseProduct.getProductHeight());
					productCollect.setParentProductId(baseProduct.getParentId());
					// u3dModelPath
					String u3dModelId = baseProductService.getU3dModelId(mediaType, baseProduct);
					if (StringUtils.isNotBlank(u3dModelId)) {
						ResModel resModel = StringUtils.equals(SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE, sParameter.getCacheEnable()) ? 
							ResourceCacher.getModel(Integer.valueOf(u3dModelId)) : resModelService.get(Integer.valueOf(u3dModelId));
						if (resModel != null) {
							productCollect.setU3dModelPath(resModel.getModelPath());
						}
					}

					/*** 在组合产品查询列表 中 增加产品属性 ****/
					Map<String, String> map = new HashMap<String, String>();
					map = productAttributeService.getPropertyMap(baseProduct.getId());
					baseProduct.setPropertyMap(map);

					// 组装产品的规则
					Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(
							baseProduct.getId().toString(), productCollect.getProductTypeCode(),
							productCollect.getProductSmallTypeCode(), sParameter.getSpaceCommonId(),
							designTemplet == null ? null : designTemplet.getId(), new DesignRules(), map);
					productCollect.setRulesMap(rulesMap);
				}
				/* 关联查询材质的两个属性(textureAttrValue,laymodes)->end */
				/* 处理拆分材质产品返回默认材质 */
				String splitTexturesInfo = baseProduct.getSplitTexturesInfo();
				Integer isSplit = 0;
				List<SplitTextureDTO> splitTextureDTOList = null;
				if (StringUtils.isNotBlank(splitTexturesInfo)) {
					Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(baseProduct.getId(), splitTexturesInfo, "choose");
					isSplit = (Integer) map.get("isSplit");
					splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
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
							/* 同时存一份数据在老的数据结构上 */
							productCollect.setTextureWidth(resTexture.getFileWidth() + "");
							productCollect.setTextureHeight(resTexture.getFileHeight() + "");
							productCollect.setTextureAttrValue(resTexture.getTextureAttrValue());
							productCollect.setLaymodes(resTexture.getLaymodes());
							String[] materialPathList = new String[] { resTextureDTO.getPath() };
							productCollect.setMaterialPicPaths(materialPathList);
							/* 同时存一份数据在老的数据结构上->end */
						}
					}
				}
				productCollect.setIsSplit(isSplit);
				productCollect.setSplitTexturesChoose(splitTextureDTOList);
			}
		}
		return sParameter;
	}

	
	
	@Override
	public StructureProductParameter jsplist(UserProductCollectSearch userProductCollectSearch,StructureProductParameter sParameter) {
		
		List<UserProductCollect> list = new ArrayList<UserProductCollect>();
		int total = getCount(userProductCollectSearch);
		sParameter.setTotal(total);
		logger.info("total:" + total);
		if (total > 0) {
			list = getPaginatedList(userProductCollectSearch);
			sParameter.setLists(list);
		}
		UserProductsConllect upc = new UserProductsConllect();
		if (StringUtils.isNotBlank(sParameter.getBrandIdStr())) {
			Integer brandId = Integer.parseInt(sParameter.getBrandIdStr());
			upc.setBrandId(brandId);
		}
		upc.setProductTypeValue(sParameter.getProductTypeValue());

		List<UserProductsConllect> upcList = new ArrayList<UserProductsConllect>();
		upcList = getUserProductsConllectList(upc);
		sParameter.setUpcList(upcList);
		List<Integer> listId = new ArrayList<Integer>();
		for(int i = 0; i < upcList.size(); i++){
			listId.add(Integer.parseInt(upcList.get(i).getProductId()));
		}
		List<BaseProduct> bpList = baseBrandServiceV2.getBatchData(listId);
		UserProductsConllect userProductsConllect = new UserProductsConllect();
		for(int i = 0; i < bpList.size(); i++){
			if (StringUtils.isNotBlank(bpList.get(i).getProductTypeValue()) && bpList.get(i).getProductSmallTypeValue() != null) {
				SysDictionary productTypeDic = sysDictionaryService.getSysDictionaryByValue("productType", Integer.valueOf(bpList.get(i).getProductTypeValue()));
				userProductsConllect.setProductTypeKey(productTypeDic.getValuekey());
				userProductsConllect.setProductTypeName(productTypeDic.getName());
				userProductsConllect.setProductTypeValue(productTypeDic.getValue().toString());
				
				SysDictionary productSmallTypeDic = sysDictionaryService.getSysDictionaryByValue(productTypeDic.getValuekey(), bpList.get(i).getProductSmallTypeValue());
				userProductsConllect.setProductSmallTypeName(productSmallTypeDic.getName());
				userProductsConllect.setProductSmallTypeValue(productSmallTypeDic.getValue());
				userProductsConllect.setProductSmallTypeCode(productSmallTypeDic.getValuekey());
			}
			// u3dmodelpath
			String u3dModelPath = baseProductService.getU3dModelId(sParameter.getMediaType(), bpList.get(i));
			if (StringUtils.isNotBlank(u3dModelPath)) {
				ResModel resModel = resModelService.get(Integer.valueOf(u3dModelPath));
				if (resModel != null) {
					userProductsConllect.setU3dModelPath(resModel.getModelPath());
				}
			}
		}
		return sParameter;
	}

}
