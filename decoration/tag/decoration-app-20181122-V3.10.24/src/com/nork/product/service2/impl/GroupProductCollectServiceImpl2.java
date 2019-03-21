package com.nork.product.service2.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.service.DesignPlanService;
import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.dao2.BaseBrandMapper;
import com.nork.product.dao2.GroupCollectDetailsMapper;
import com.nork.product.dao2.GroupProductCollectMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.BaseProductDTO;
import com.nork.product.model.GroupCollectDetails;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.search.GroupCollectDetailsSearch;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupCollectDetailsService;
import com.nork.product.service.GroupProductDetailsService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service.impl.GroupProductCollectServiceImpl;
import com.nork.product.service2.GroupProductCollectService2;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

/**
 * @Title: GroupProductCollectServiceImpl2.java
 * @Package com.nork.product.service2.impl
 * @Description:产品模块-组合收藏表ServiceImpl
 * @CreateAuthor yangzhun
 * @CreateDate 2017-6-19 17:06:55
 */
@Service("groupProductCollectService2")
public class GroupProductCollectServiceImpl2 implements
		GroupProductCollectService2 {

	Logger logger = LoggerFactory
			.getLogger(GroupProductCollectServiceImpl.class);

	@Autowired
	private GroupProductCollectMapper groupProductCollectMapper2;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private GroupProductDetailsService groupProductDetailsService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DesignRulesService designRulesService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseBrandMapper baseBrandMapper2;
	@Autowired
	private GroupCollectDetailsMapper groupCollectDetailsMapper2;
	@Autowired
	private GroupCollectDetailsService groupCollectDetailsService;

	/**
	 * updateDetails方法验证参数
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @param productId
	 * @param type
	 * @return
	 */
	@Override
	public Map<String, Object> updateDetailsVerifyParams(Integer groupId,
			Integer productId, Integer type, String msgId) {
		boolean success = true;
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			success = false;
			msg = SystemCommonConstant.MSGID_NOT_NULL;
		} else if (groupId == null) {
			success = false;
			msg = SystemCommonConstant.GROUPID_NOT_NULL;
		} else if (productId == null) {
			success = false;
			msg = SystemCommonConstant.PRODUCTID_NOT_NULL;
		} else if (type == null) {
			success = false;
			msg = SystemCommonConstant.TYPE_NOT_NULL;
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("success", success);
		returnMap.put("msg", msg);
		return returnMap;
	}

	/**
	 * 取得收藏的封面图并保存
	 * 
	 * @author huangsongbo
	 * @param groupProductCollect
	 */
	@Override
	public void savePicUrl(GroupProductCollect groupProductCollect) {
		if (groupProductCollect.getPicId() == null) {
			// 取关联产品缩略图(优先主产品,没有主产品取第一个产品)
			groupProductCollect
					.setProductPath(getPicUrlFromProducts(groupProductCollect
							.getId()));
		} else {
			ResPic resPic = resPicService.get(groupProductCollect.getPicId());
			if (resPic == null) {
				// 取关联产品缩略图(优先主产品,没有主产品取第一个产品)
				groupProductCollect
						.setProductPath(getPicUrlFromProducts(groupProductCollect
								.getId()));
			} else {
				groupProductCollect.setProductPath(resPic.getPicPath());
			}
		}
	}

	/**
	 * 取关联产品缩略图(优先主产品,没有主产品取第一个产品)
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	private String getPicUrlFromProducts(Integer id) {
		// List<GroupCollectDetails> list =
		// groupCollectDetailsService.findAllByGroupId(id);
		List<GroupCollectDetails> list = groupCollectDetailsMapper2
				.findAllByGroupId(id);
		String url = "";
		if (list == null || list.size() == 0) {
			logger.info("------id为{}的组合收藏中没有关联产品,这种情况不允许出现", id);
			return null;
		}
		// 识别list里面有没有主产品->有,取得缩略图url;无:取第一个产品的url
		Integer mainProductId = 0;
		for (GroupCollectDetails groupCollectDetails : list) {
			if (new Integer(1)
					.equals(groupCollectDetails.getIsMain() == null ? 0
							: groupCollectDetails.getIsMain())) {
				mainProductId = groupCollectDetails.getProductId();
				break;
			}
		}
		if (!new Integer(0).equals(mainProductId)) {
			// 存在主产品
			url = baseProductService.getPicUrlFromProductId(mainProductId);
		} else {
			// 不存在主产品
			url = baseProductService.getPicUrlFromProductId(list.get(0)
					.getProductId());
		}
		// 识别list里面有没有主产品->有,取得缩略图url;无:取第一个产品的url->end
		return url;
	}

	/**
	 * 收藏组合
	 * 
	 * @author huangsongbo
	 * @param groupProduct
	 * @return id
	 */
	@Override
	public int saveCollectByGroup(GroupProduct groupProduct, LoginUser loginUser) {
		GroupProductCollect groupProductCollect = new GroupProductCollect();
		groupProductCollect.setUserId(loginUser.getId());
		groupProductCollect.setName(groupProduct.getGroupName());
		groupProductCollect.setTypeValue(groupProduct.getType());
		groupProductCollect.setCoordinates(null);// 等待更新字段
		groupProductCollect.setGroupId(groupProduct.getId());
		sysSave(groupProductCollect, loginUser);
		int id = groupProductCollectMapper2
				.insertSelective(groupProductCollect);
		return id;
	}

	private void sysSave(GroupProductCollect model, LoginUser loginUser) {
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
	 * 删除组合收藏,通过参数groupId和userId
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteByGroupIdAndUserId(Integer groupId, Integer userId) {
		// 查找指定组合
		int num = 0;
		GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
		groupProductCollectSearch.setStart(0);
		groupProductCollectSearch.setLimit(1);
		groupProductCollectSearch.setGroupId(groupId);
		groupProductCollectSearch.setUserId(userId);
		List<GroupProductCollect> groupProductCollects = groupProductCollectMapper2
				.selectPaginatedList(groupProductCollectSearch);
		if (groupProductCollects != null && groupProductCollects.size() > 0) {
			GroupProductCollect groupProductCollect = groupProductCollects
					.get(0);
			// 删除收藏详情表中的关联数据
			// groupCollectDetailsService.deleteByGroupCollectId(groupProductCollect.getId());
			groupCollectDetailsMapper2
					.deleteByGroupCollectId(groupProductCollect.getId());
			// 删除收藏详情表中的关联数据->end
			num = groupProductCollectMapper2
					.deleteByPrimaryKey(groupProductCollect.getId());
		}
		return num;
	}

	@Override
	public int getGroupProductCollectCount(
			GroupProductCollectSearch groupProductCollectSearch) {
		return groupProductCollectMapper2
				.groupProductCollectCount(groupProductCollectSearch);
	}

	@Override
	public List<GroupProductCollect> getGroupProductCollectList(
			GroupProductCollectSearch groupProductCollecttSearch) {
		return groupProductCollectMapper2
				.groupProductCollectList(groupProductCollecttSearch);
	}

	/**
	 * 编辑组合收藏接口(未完成,备用)
	 * 
	 * @author huangsongbo
	 * @param groupId 组合id
	 * @param productId 产品id
	 * @param type 0:新增
	 * @param msgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope updateDetails(Integer groupId, Integer productId,
			Integer type, String msgId) {
		Map<String, Object> map = this.updateDetailsVerifyParams(groupId,
				productId, type, msgId);
		boolean success = (boolean) map.get("success");
		if (!success) {
			return new ResponseEnvelope<>(false, (String) map.get("msg"), msgId);
		}
		// 识别组合是否存在
		GroupProductCollect groupProductCollect = groupProductCollectMapper2
				.selectByPrimaryKey(groupId);
		if (groupProductCollect == null || groupProductCollect.getId() == null) {
			return new ResponseEnvelope<>(false, "id为" + groupId
					+ "的组合收藏未找到,操作失败", msgId);
		}
		// 识别组合是否存在->end
		// 识别该产品是否存在
		BaseProduct baseProduct = baseProductService.get(productId);
		if (baseProduct == null || baseProduct.getId() == null) {
			return new ResponseEnvelope<>(false, "id为" + baseProduct
					+ "的产品未找到,操作失败", msgId);
		}
		// 识别该产品是否存在->end
		// if (new Integer(0).equals(type)) {
		// 组合收藏新增产品
		// groupCollectDetailsService.create(groupId,productId);
		// }
		return null;

	}

	/**
	 * 组合收藏列表
	 * 
	 * @param groupProductCollectSearch
	 * @param spaceCommonId
	 * @param loginUser
	 * @param mediaType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEnvelope list(
			GroupProductCollectSearch groupProductCollectSearch,
			Integer spaceCommonId, LoginUser loginUser, String mediaType) {
		// 开启cache
		String cacheEnable = Utils.getValue(
				SystemCommonConstant.REDIS_CACHE_ENABLE,
				SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE);

		groupProductCollectSearch.setUserType(loginUser.getUserType());
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();// 1为外网 2 为内网 默认为外网
		groupProductCollectSearch.setVersionType(versionType);
		groupProductCollectSearch.setUserId(loginUser.getId());
		int total = this.getGroupProductCollectCount(groupProductCollectSearch);
		List<GroupProductCollect> list = new ArrayList<GroupProductCollect>();
		if (total > 0) {
			int brandState = 0;

			// 获取该用户绑定的序列号品牌
			AuthorizedConfig authorizedConfig = new AuthorizedConfig();
			authorizedConfig.setUserId(loginUser.getId());
			authorizedConfig.setState(1);
			List<AuthorizedConfig> authorizedList = new ArrayList<>();
			authorizedList = authorizedConfigService.getList(authorizedConfig);
			String brandIds = "";
			for (AuthorizedConfig ac : authorizedList) {
				if (StringUtils.isNotBlank(ac.getBrandIds())) {
					brandIds += ac.getBrandIds() + ",";
				}
			}

			list = this.getGroupProductCollectList(groupProductCollectSearch);
			
			for (GroupProductCollect groupProductCollect : list) {
				// 方便前端取组合名
				groupProductCollect.setProductName(groupProductCollect.getName());

				// 获取主产品的id
				GroupCollectDetailsSearch groupCollectDetailsSearch = new GroupCollectDetailsSearch();
				groupCollectDetailsSearch.setGroupCollectId(groupProductCollect.getId());
				List<GroupCollectDetails> groupCollectDetails = groupCollectDetailsMapper2.selectPaginatedList(groupCollectDetailsSearch);
				if (groupCollectDetails != null && groupCollectDetails.size() > 0) {
					for (GroupCollectDetails groupCollectDetails2 : groupCollectDetails) {
						Integer isMain = groupCollectDetails2.getIsMain();
						if (isMain != null && isMain == 1) {
							Integer productId = groupCollectDetails2.getProductId();
							groupProductCollect.setProductId(productId);
							BaseProduct baseProduct = baseProductService.get(productId);
							// 品牌是否被绑定状态
							String productBrandId = ","+ baseProduct.getBrandId() + ",";
							if (StringUtils.isEmpty(brandIds)) {
								brandState = brandState + 1;
							}
							if (("," + brandIds).indexOf(productBrandId) == -1) {
								brandState = brandState + 1;
							}
							Integer brandId = baseProduct.getBrandId();
							if (brandId != null) {
								BaseBrand baseBrand = baseBrandMapper2.selectByPrimaryKey(brandId);
								if (baseBrand != null && baseBrand.getId() > 0)
									groupProductCollect.setBrandName(baseBrand.getBrandName());
							}
						}
						if (brandState == 0) {
							if (isMain == null || isMain != 1) {
								Integer productId = groupCollectDetails2.getProductId();
								BaseProduct baseProduct = baseProductService.get(productId);
								// 品牌是否被绑定状态
								String productBrandId = ","+ baseProduct.getBrandId() + ",";
								if (StringUtils.isEmpty(brandIds)) {
									brandState = brandState + 1;
								}
								if (("," + brandIds).indexOf(productBrandId) == -1) {
									brandState = brandState + 1;
								}
							}
						}
					}
				}
				// 调用处理groupConfig 的方法
				//groupProductCollect = this.dealGroupConfig(groupProductCollect);
				GroupProduct groupProduct = groupProductService.get(groupProductCollect.getGroupId());
				if (groupProduct != null) {
					Integer picId = groupProduct.getPicId();
					if (picId != null && picId > 0) {
						ResPic resPic = resPicService.get(picId);
						if (resPic != null) {
							groupProductCollect.setProductPath(resPic.getPicPath());
						}
					}
				}

				if (groupProduct != null && groupProduct.getId() > 0) {
					groupProductCollect.setGroupConfig(groupProduct.getLocation());
				}
				// 处理组合的价格
				Double salePrice = groupProductService.getSalePrice(groupProductCollect.getGroupId());
				groupProductCollect.setSalePrice(salePrice);
				// 处理组合的价格->end
				// 组装组合里的产品详情
				GroupProductDetails groupProductDetailSearch = new GroupProductDetails();
				groupProductDetailSearch.setGroupId(groupProductCollect.getGroupId());
				List<GroupProductDetails> groupProductDetails = groupProductDetailsService.getList(groupProductDetailSearch);
				List<SearchProductGroupDetail> details = null;
				SearchProductGroupDetail groupDetail = null;
				// 媒介类型.如果没有值，则表示为web前端（2）
				if (groupProductDetails != null && groupProductDetails.size() > 0) {
					details = new ArrayList<>();
					for (GroupProductDetails detailEntity : groupProductDetails) {
						groupDetail = new SearchProductGroupDetail();
						groupDetail.setProductId(detailEntity.getProductId());
						groupDetail.setProductGroupId(groupProductCollect.getGroupId());
						int isMain = detailEntity.getIsMain() == null ? 0 : detailEntity.getIsMain();
						groupDetail.setIsMainProduct(isMain);
						if (detailEntity.getProductId() > 0) {
							BaseProduct baseProduct = baseProductService.get(detailEntity.getProductId());
							if (baseProduct != null) {
								groupDetail.setProductCode(baseProduct.getProductCode());
								// 调用  组装产品模型地址 的方法								
								groupDetail = this.assembly(groupDetail, mediaType, baseProduct);

								// 产品材质图片列表
								String materialIds = baseProduct.getMaterialPicIds();
								if (StringUtils.isNotBlank(materialIds)) {
									String[] mIds = materialIds.split(",");
									if (mIds != null) {
										ResPic resPic = new ResPic();
										resPic.setFileKey("product.baseProduct.material");
										resPic.setBusinessId(baseProduct.getId());
										resPic.setOrders(" sequence asc ");
										List<ResPic> materialPics = null;
										if (StringUtils.equals(SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE, cacheEnable)) {
											materialPics = ResourceCacher.getAllPicList(resPic);
										} else {
											materialPics = resPicService.getList(resPic);
										}

										if (materialPics != null && materialPics.size() > 0) {
											String[] materialPaths = new String[materialPics.size()];
											for (int i = 0; i < materialPics.size(); i++) {
												resPic = materialPics.get(i);
												if (resPic != null) {
													materialPaths[i] = resPic.getPicPath();
												}
											}
											groupDetail.setMaterialPicPaths(materialPaths);
										}
									}
								}

								// 在组合产品查询列表 中 增加产品属性
								Map<String, String> map = new HashMap<String, String>();
								map = productAttributeService.getPropertyMap(baseProduct.getId());
								baseProduct.setPropertyMap(map);

								// 产品规则
								Map<String, String> rulesMap = designRulesService.getRulesSecondaryList(
												baseProduct.getId().toString(),
												baseProduct.getProductTypeMark(),
												baseProduct.getProductSmallTypeMark(),
												spaceCommonId, null,
												new DesignRules(), map);
								groupDetail.setRulesMap(rulesMap);
							}
						}
						details.add(groupDetail);
					}
					if (brandState > 0) {
						groupProductCollect.setSalePrice(-1.0);
					}
					groupProductCollect.setGroupProductDetails(details);
				}
			}
		}
		return new ResponseEnvelope<GroupProductCollect>(total, list,
				groupProductCollectSearch.getMsgId());

	}
	
	//处理GroupConfig
	private GroupProductCollect dealGroupConfig(GroupProductCollect groupProductCollect){
		GroupProduct groupProduct = groupProductService.get(groupProductCollect.getGroupId());
		if (groupProduct != null) {
			Integer picId = groupProduct.getPicId();
			if (picId != null && picId > 0) {
				ResPic resPic = resPicService.get(picId);
				if (resPic != null) {
					groupProductCollect.setProductPath(resPic.getPicPath());
				}
			}
		}

		if (groupProduct != null && groupProduct.getId() > 0) {
			groupProductCollect.setGroupConfig(groupProduct.getLocation());
		}
		
		return groupProductCollect;
	}
	
	//组装产品模型地址，产品分类信息
	private SearchProductGroupDetail assembly(SearchProductGroupDetail groupDetail, String mediaType, BaseProduct baseProduct){
		String modelId = baseProductService.getU3dModelId(mediaType, baseProduct);
		if (StringUtils.isNotBlank(modelId)) {
			ResModel resModel = resModelService.get(Integer.valueOf(modelId));
			if (resModel != null) {
				/*File modelFile = new File(Tools.getRootPath(resModel.getModelPath(),"") + resModel.getModelPath());*/
				File modelFile = new File(Utils.getAbsolutePath(resModel.getModelPath(), null));
				if (modelFile.exists()) {
					groupDetail.setU3dModelPath(resModel.getModelPath());
				}
			}
		}
		// 产品大类信息
		String typeValue = baseProduct.getProductTypeValue();
		if (StringUtils.isNotBlank(typeValue)) {
			SysDictionary dlDic = sysDictionaryService.findOneByTypeAndValue("productType",Integer.valueOf(typeValue));
			groupDetail.setProductTypeValue(dlDic.getValue());
			groupDetail.setProductTypeCode(dlDic.getValuekey());
			groupDetail.setProductTypeName(dlDic.getName());

			// 产品小类
			Integer smallType = baseProduct.getProductSmallTypeValue();
			if (smallType != null && smallType.intValue() > 0) {
				SysDictionary xlDic = sysDictionaryService.findOneByTypeAndValue(
								dlDic.getValuekey(),smallType);
				groupDetail.setProductSmallTypeValue(xlDic.getValue().toString());
				groupDetail.setProductSmallTypeCode(xlDic.getValuekey());
				groupDetail.setProductSmallTypeName(xlDic.getName());

				String rootType = StringUtils
						.isEmpty(xlDic.getAtt1()) ? "2"
						: xlDic.getAtt1().trim();
				groupDetail.setRootType(rootType);
			}
		}
		if (baseProduct.getParentId() != null && baseProduct.getParentId().intValue() > 0) {
			BaseProduct parentProduct = baseProductService.get(baseProduct.getParentId());
			if (parentProduct != null) {
				String parentTypeValue = parentProduct.getProductTypeValue();
				if (StringUtils.isNotBlank(parentTypeValue)) {
					SysDictionary dlDic = sysDictionaryService
							.findOneByTypeAndValue("productType",Integer.valueOf(parentTypeValue));
					groupDetail.setParentTypeValue(dlDic.getValue());
					groupDetail.setParentTypeCode(dlDic.getValuekey());
					groupDetail.setParentTypeName(dlDic.getName());
				}
			}
			groupDetail.setParentProductId(baseProduct.getParentId());
		}
		// 长宽高
		groupDetail.setProductWidth(baseProduct.getProductWidth());
		groupDetail.setProductHeight(baseProduct.getProductHeight());
		groupDetail.setProductLength(baseProduct.getProductLength());
		
		return groupDetail;
	}
	/**
	 * 得到组合收藏详情接口
	 * 
	 * @param collectId
	 * @param msgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope getDetails(Integer collectId, String msgId) {
		GroupProductCollect groupProductCollect = groupProductCollectMapper2
				.selectByPrimaryKey(collectId);
		List<GroupCollectDetails> list = groupCollectDetailsMapper2
				.findAllByGroupId(collectId);
		List<BaseProductDTO> baseProductDTOs = new ArrayList<BaseProductDTO>();
		if (list == null || list.size() == 0)
			return new ResponseEnvelope<>(groupProductCollect, msgId, true);
		// 得到产品的id+缩略图
		for (GroupCollectDetails groupCollectDetails : list) {
			String picUrl = baseProductService
					.getPicUrlFromProductId(groupCollectDetails.getProductId());
			baseProductDTOs.add(new BaseProductDTO(groupCollectDetails
					.getProductId(), picUrl));
		}
		groupProductCollect.setBaseProducts(baseProductDTOs);
		return new ResponseEnvelope<>(groupProductCollect, msgId, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope collectGroup(Integer groupId, String type,
			String msgId, LoginUser loginUser) {

		if (groupId == null) {
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.GROUPID_NOT_NULL, msgId);
		}
		GroupProduct groupProduct = groupProductService.get(groupId);
		if (groupProduct == null || groupProduct.getId() == null) {
			return new ResponseEnvelope<>(false, "id为:" + groupId
					+ "的组合未找到,收藏失败", msgId);
		}
		List<GroupProductDetails> list = groupProductDetailsService
				.findDetailsByGroupId(groupId);

		if (StringUtils.equals("1", type)) {
			// 收藏表中删除该组合
			int num = this.deleteByGroupIdAndUserId(groupId, loginUser.getId());
			if (num > 0) {
				return new ResponseEnvelope<>(true,
						SystemCommonConstant.DELETE_SUCCESS, msgId);
			} else {
				return new ResponseEnvelope<>(true, "该用户未收藏该组合->userid:"
						+ loginUser.getId() + ";groupId:" + groupId, msgId);
			}
		} else {
			// 新增组合
			int id = this.saveCollectByGroup(groupProduct, loginUser);
			// 保存组合收藏明细数据
			for (GroupProductDetails groupProductDetails : list) {
				groupCollectDetailsService.saveByGroupDetails(
						groupProductDetails, id, loginUser);
			}
			return new ResponseEnvelope<>(true,
					SystemCommonConstant.COLLECTION_SUCCESS, msgId);
		}
	}
}
