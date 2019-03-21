package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.GroupProductVO;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.dao.GroupProductDetailsMapper;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.MergeProductResult;
import com.nork.product.model.ProductAttribute;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.model.search.GroupProductDetailsSearch;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductCollectService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service2.GroupProductDetailsService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: GroupProductDetailsServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品组合关联表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-06-22 20:37:16
 * @version V1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GroupProductDetailsServiceImpl2 implements GroupProductDetailsService {

	private static final String MSGID_EMPTY = "msgId为空!";
	private static final String LACK_COLORID = "参数缺少colorId!";
	private static final String LACK_PRODUCTID = "参数缺少productId!";
	private static final String LACK_MATERIALID = "参数缺少materialId!";
	private static final String LACK_GROUPID = "参数缺少groupId!";
	private static final String PRODUCT_NOT_EXIST = "产品不存在!";
	private static final String DATA_EXCEPTION = "数据异常!";
 
	@Autowired
	private GroupProductCollectService groupProductCollectService;
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ProductAttributeService productAttributeService;

	private GroupProductDetailsMapper groupProductDetailsMapper;

	@Autowired
	public void setGroupProductDetailsMapper(GroupProductDetailsMapper groupProductDetailsMapper) {
		this.groupProductDetailsMapper = groupProductDetailsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param groupProductDetails
	 * @return int
	 */
	@Override
	public int add(GroupProductDetails groupProductDetails) {
		groupProductDetailsMapper.insertSelective(groupProductDetails);
		return groupProductDetails.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param groupProductDetails
	 * @return int
	 */
	@Override
	public int update(GroupProductDetails groupProductDetails) {
		return groupProductDetailsMapper.updateByPrimaryKeySelective(groupProductDetails);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return groupProductDetailsMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return GroupProductDetails
	 */
	@Override
	public GroupProductDetails get(Integer id) {
		return groupProductDetailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param groupProductDetails
	 * @return List<GroupProductDetails>
	 */
	@Override
	public List<GroupProductDetails> getList(GroupProductDetails groupProductDetails) {
		return groupProductDetailsMapper.selectList(groupProductDetails);
	}

	/**
	 * 获取数据数量
	 *
	 * @param groupProductDetails
	 * @return int
	 */
	@Override
	public int getCount(GroupProductDetailsSearch groupProductDetailsSearch) {
		return groupProductDetailsMapper.selectCount(groupProductDetailsSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param groupProductDetails
	 * @return List<GroupProductDetails>
	 */
	@Override
	public List<GroupProductDetails> getPaginatedList(GroupProductDetailsSearch groupProductDetailsSearch) {
		return groupProductDetailsMapper.selectPaginatedList(groupProductDetailsSearch);
	}

	/**
	 * 根据组合id查找组合明细
	 * 
	 * @author huangsongbo
	 * @param groupId
	 *            组合id
	 * @return
	 */
	public List<GroupProductDetails> findDetailsByGroupId(Integer groupId) {
		return groupProductDetailsMapper.findDetailsByGroupId(groupId);
	}

	/**
	 * 根据组合id查找产品编码
	 * 
	 * @author xiaoxc
	 * @param groupId
	 *            组合id
	 * @return
	 */
	public List<GroupProductDetails> getByGroupIdProductCodeList(Integer groupId) {
		return groupProductDetailsMapper.byGroupIdProductCodeList(groupId);
	}

	/***
	 * 检查组合产品信息
	 * 
	 * @param groupProductVO
	 * @return
	 */
	protected ResponseEnvelope<BaseProduct> groupProduct(GroupProductVO groupProductVO) {
		if (StringUtils.isBlank(groupProductVO.getMsgId())) {
			return new ResponseEnvelope<BaseProduct>(false, MSGID_EMPTY, groupProductVO.getMsgId());
		}
		if (groupProductVO.getColorId() == null) {
			return new ResponseEnvelope<BaseProduct>(false, LACK_COLORID, groupProductVO.getMsgId());
		}
		if (groupProductVO.getProductId() == null) {
			return new ResponseEnvelope<BaseProduct>(false, LACK_PRODUCTID, groupProductVO.getMsgId());
		}
		if (StringUtils.isBlank(groupProductVO.getMaterialId())) {
			return new ResponseEnvelope<BaseProduct>(false, LACK_MATERIALID, groupProductVO.getMsgId());
		}
		if (groupProductVO.getGroupId() == null) {
			return new ResponseEnvelope<BaseProduct>(false, LACK_GROUPID, groupProductVO.getMsgId());
		}
		return new ResponseEnvelope<BaseProduct>(true, "success");
	}

	public Object selectProductAttribute(GroupProductVO groupProductVO, String mediaType, LoginUser loginUser) {

		ResponseEnvelope<BaseProduct> res = groupProduct(groupProductVO);
		if (!res.isSuccess()) {
			return res;
		}
		if (StringUtils.isBlank(groupProductVO.getOnclickType())) {
			groupProductVO.setOnclickType("material");
		}

		Integer id = -1;
		if (StringUtils.isNotEmpty(groupProductVO.getMergeProductIds())) {
			Map map = new HashMap();
			map.put("colorId", groupProductVO.getColorId());
			map.put("materialId", groupProductVO.getMaterialId());
			map.put("onclickType", groupProductVO.getOnclickType());// color ,
																	// material
			map.put("mergeProductIds", groupProductVO.getMergeProductIds());
			if (Utils.enableRedisCache()) {
				id = BaseProductCacher.getDetailProduct(map);
			} else {
				id = baseProductService.getDetailProduct(map);
			}

		} else {
			id = groupProductVO.getProductId();
		}
		if (StringUtils.isNotBlank(groupProductVO.getAttributeKey())
				&& StringUtils.isNotBlank(groupProductVO.getAttributeValueKey())) {
			// 得到当前产品的属性
			List<ProductAttribute> currentList = new ArrayList<ProductAttribute>();
			ProductAttribute currentPa = new ProductAttribute();
			currentPa.setProductId(id);
			if (Utils.enableRedisCache()) {
				currentList = BaseProductCacher.getProductAttributeList(currentPa);
			} else {
				currentList = productAttributeService.getList(currentPa);
			}

			// 需要匹配的属性
			List<ProductAttribute> newList = new ArrayList<ProductAttribute>();
			ProductAttribute newPa = new ProductAttribute();
			newPa.setAttributeKey(groupProductVO.getAttributeKey());
			newPa.setAttributeValueKey(groupProductVO.getAttributeValueKey());
			List<Integer> pidList = new ArrayList<Integer>();
			if (StringUtils.isNoneBlank(groupProductVO.getMergeProductIds())) {
				String mergeArr[] = groupProductVO.getMergeProductIds().split(",");
				for (String mergeIds : mergeArr) {
					pidList.add(Utils.getIntValue(mergeIds));
				}
				newPa.setProductIdList(pidList);
			}
			if (Utils.enableRedisCache()) {
				newList = BaseProductCacher.getMergeAttribute(newPa);
			} else {
				newList = productAttributeService.getMergeAttribute(newPa);
			}

			Set<ProductAttribute> currentSet = new HashSet<ProductAttribute>();
			// 过滤当前的attributeKey
			if (CustomerListUtils.isNotEmpty(currentList)) {
				for (ProductAttribute pa : currentList) {
					if (!pa.getAttributeKey().equals(groupProductVO.getAttributeKey())) {
						currentSet.add(pa);
					}
				}
			}
			// 如果当前没有其它属性，则默认显示传过来的ID产品
			if (CustomerListUtils.isEmpty(currentSet)) {
				id = groupProductVO.getProductId();
			}
			// 匹配属性，获取产品ID
			if (CustomerListUtils.isNotEmpty(newList)) {
				id = getProductId(groupProductVO, id, newList, currentSet);
			}
		}
		BaseProduct baseProduct = null;
		if (Utils.enableRedisCache()) {
			baseProduct = BaseProductCacher.getBaseProductServiceById(id);
		} else {
			baseProduct = baseProductService.get(id);
		}

		if (baseProduct == null) {
			return new ResponseEnvelope<BaseProduct>(false, PRODUCT_NOT_EXIST, groupProductVO.getMsgId());
		}
		try {
			// 详情逻辑
			productDetailContent(baseProduct, groupProductVO.getProductId(), loginUser, mediaType);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProduct>(false, DATA_EXCEPTION, groupProductVO.getMsgId());
		}
		return new ResponseEnvelope<BaseProduct>(baseProduct, groupProductVO.getMsgId(), true);
	}

	/***
	 * 匹配属性获取产品ID
	 * 
	 * @param groupProductVO
	 * @param cacheEnable
	 * @param id
	 * @param newList
	 * @param currentSet
	 * @return
	 */
	// TODO
	private Integer getProductId(GroupProductVO groupProductVO, Integer id,
			List<ProductAttribute> newList, Set<ProductAttribute> currentSet) {
		Integer arr[] = new Integer[newList.size()];
		int index = 0;
		int temp = 0;
		for (int n = 0; n < newList.size(); n++) {
			int tempNum = 0;
			ProductAttribute pa = newList.get(n);
			ProductAttribute attribute = new ProductAttribute();
			attribute.setProductId(pa.getProductId());
			List<ProductAttribute> list = null;
			if (Utils.enableRedisCache()) {
				list = BaseProductCacher.getProductAttributeList(attribute);
			} else {
				list = productAttributeService.getList(attribute);
			}

			if (CustomerListUtils.isNotEmpty(list)) {
				arr[n] = list.size();
				for (ProductAttribute pai : list) {
					if (pai.getAttributeKey().equals(groupProductVO.getAttributeKey())) {
						continue;
					}
					Iterator<ProductAttribute> iterator = currentSet.iterator();
					while (iterator.hasNext()) {
						ProductAttribute iteratorPa = iterator.next();
						if (iteratorPa.getAttributeKey().equals(pai.getAttributeKey())
								&& iteratorPa.getAttributeValueKey().equals(pai.getAttributeValueKey())) {
							id = pai.getProductId();
							tempNum = 1;
							break;
						}
					}
					if (tempNum == 1)
						break;
				}
				// tmpNum为1已匹配到产品ID，没有则判断属性最少的产品
				if (tempNum == 1)
					temp = 1;
				else {
					if (temp == 0) {
						if (arr[index] > arr[n]) {
							index = n;
						}
						id = newList.get(index).getProductId();
					}
				}
			} else {
				id = pa.getProductId();
				break;
			}
		}
		return id;
	}

	public void productDetailContent(BaseProduct baseProduct, Integer groupId, LoginUser loginUser, String mediaType)
			throws Exception {
		// 当前登录人是否已经收藏该产品组合
		GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
		groupProductCollectSearch.setUserId(loginUser.getId());
		Integer count = groupProductCollectService.getCount(groupProductCollectSearch);
		if (count > 0) {
			baseProduct.setCollectState(1);
		} else {
			baseProduct.setCollectState(0);
		}
		// 组合产品列表
		GroupProductDetails groupProductDetails = new GroupProductDetails();
		groupProductDetails.setGroupId(groupId);
		/*
		 * 未使用代码 List<GroupProductDetails> gpdList =
		 * groupProductDetailsService.getList(groupProductDetails);
		 * List<BaseProduct> bpList = new ArrayList<BaseProduct>();
		 * for(GroupProductDetails gpd : gpdList){ BaseProduct bp =
		 * baseProductService.get(gpd.getProductId()); if( bp != null &&
		 * bp.getPicId() != null & bp.getPicId() > 0 ){ ResPic resPic =
		 * resPicService.get(bp.getPicId());
		 * bp.setPicPath(resPic==null?"":resPic.getPicPath()); } bpList.add(bp);
		 * }
		 */
		// baseProduct.setGroupProductList(bpList);
		baseProduct.setGroupId(groupId);// 组合产品ID

		Integer brandId = baseProduct.getBrandId();
		if (brandId != null) {
			BaseBrand baseBrand = baseBrandService.get(brandId);
			if (baseBrand != null) {
				baseProduct.setBrandName(baseBrand.getBrandName());
			}
		}
		// 风格
		Integer productStyleId = baseProduct.getProStyleId();
		if (productStyleId != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productStyle", productStyleId);
			baseProduct.setProductStyle(dictionary.getName());
		}
		String productType = baseProduct.getProductTypeValue();
		if (!StringUtils.isEmpty(productType)) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productType", new Integer(productType));
			baseProduct.setProductType(dictionary.getName());
			baseProduct.setProductTypeKey(dictionary.getValuekey());
			baseProduct.setProductTypeCode(dictionary.getValuekey());
			baseProduct.setProductTypeName(dictionary.getName());
		}
		if (baseProduct.getProductSmallTypeValue() != null
				&& StringUtils.isNotBlank(baseProduct.getProductTypeValue())) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue(baseProduct.getProductTypeKey(),
					baseProduct.getProductSmallTypeValue());
			baseProduct.setProductSmallTypeCode(dictionary.getValuekey());
			baseProduct.setProductSmallTypeName(dictionary.getName());
		}

		// 产品图片列表
		String smallPicIds = baseProduct.getPicIds();
		if (StringUtils.isNoneBlank(smallPicIds)) {
			// TODO resIdList
			ResPic resPic = new ResPic();
			resPic.setResIdList(Utils.getIntegerListFromStringList(smallPicIds));
			List<ResPic> respic = resPicService.getList(resPic);
			for (int i = 0; i < respic.size(); i++) {
				baseProduct.getSmallPiclist().add(respic.get(i) == null ? "" : respic.get(i).getPicPath());
			}
		}
		Integer colorId = baseProduct.getColorId();
		if (colorId != null) {
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId);
			baseProduct.setProductColorKey(dictionary.getAtt1());
		}
		// 材质
		String materialIds = baseProduct.getMaterialPicIds();
		ResPic resp = new ResPic();
		if (StringUtils.isNotBlank(materialIds)) {
			String[] strs = materialIds.split(",");
			baseProduct.setMaterialId(strs[0]);
			resp = resPicService.get(Utils.getIntValue(strs[0]));
		}
		// 合并的产品Id
		String mergeIds = baseProduct.getMergeProductIds();

		if (StringUtils.isBlank(mergeIds)) {
			Integer colorId2 = baseProduct.getColorId();
			// 颜色
			if (colorId2 != null) {
				SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", colorId2);
				MergeProductResult mergeProductResult = new MergeProductResult();
				mergeProductResult.setState(2);
				mergeProductResult.setProductId(baseProduct.getId());
				mergeProductResult.setColorId(colorId2);
				mergeProductResult.setColorName(dictionary.getName());
				baseProduct.getColorlist().add(mergeProductResult);
			}
			// 材质
			if (StringUtils.isNotBlank(materialIds)) {
				// 获取材质信息
				getMaterialInfo(baseProduct, materialIds);
			}
		}
		List<Integer> proIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(mergeIds) && mergeIds.contains(",")) {
			List<String> sList = Utils.getListFromStr(mergeIds);
			List<String> picList = new ArrayList<>();
			BaseProduct paramVo = new BaseProduct();
			paramVo.setList(sList);
			List<BaseProduct> resList = baseProductService.getList2(paramVo);
			String colorValue = ".";
			String materialName = ".";
			// TODO
			for (int i = 0; i < resList.size(); i++) {
				BaseProduct bp = resList.get(i);
				Integer mergeId = bp.getId();
				proIdList.add(mergeId);
				// 颜色
				if (bp.getColorId() != null) {
					//
					colorValue = getColorInfo(baseProduct, colorValue, bp, mergeId);
				} // end 颜色
				String materialPicIds = bp.getMaterialPicIds();
				if (StringUtils.isNotBlank(materialPicIds)) {
					String[] strs = bp.getMaterialPicIds().split(",");
					picList.add(strs[0]);
				}
				// 材质配置文件路径
				baseProduct.setMaterialConfigPath(bp.getMaterialConfigPath());
			}
			// 获取材质
			getMaterialInfo(baseProduct, resp, picList, materialName);

			if (picList != null) {
				// 材质路径
				getMaterialPath(baseProduct);
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
				matchProductParam(baseProduct, proIdList, map, set);
			}
			baseProduct.setMap(map);
		}
		// 媒介类型.如果没有值，则表示为web前端（2）
		Integer u3dModel = getU3dModelId(baseProduct);// 获取对应的U3dModel
		if (u3dModel != null && u3dModel > 0) {
			ResModel resModel = resModelService.get(u3dModel);
			baseProduct.setU3dModelPath(resModel == null ? "" : resModel.getModelPath());
		}
		baseProduct.setParentProductId(baseProduct.getParentId());
	}

	/**
	 * 获取材质信息
	 * 
	 * @param baseProduct
	 * @param materialIds
	 */
	private void getMaterialInfo(BaseProduct baseProduct, String materialIds) {
		MergeProductResult mergeProductResult = new MergeProductResult();
		mergeProductResult.setState(1);
		String[] strs = materialIds.split(",");
		ResPic resPic = resPicService.get(Utils.getIntValue(strs[0]));
		if (resPic != null) {
			mergeProductResult.setState(2);
			mergeProductResult.setProductId(baseProduct.getId());
			mergeProductResult.setMaterialName(resPic.getPicName());
			mergeProductResult.setMaterialPicId(Utils.getIntValue(strs[0]));
			mergeProductResult.setMaterialPicPath(resPic == null ? "" : resPic.getPicPath());
			baseProduct.getMateriallist().add(mergeProductResult);
		}

		// 产品材质路径
		if (StringUtils.isNotBlank(materialIds)) {
			String[] mIds = materialIds.split(",");
			if (mIds != null) {
				getMaterialPath(baseProduct);
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

	// 颜色
	private String getColorInfo(BaseProduct baseProduct, String colorValue, BaseProduct bp, Integer mergeId) {
		if (colorValue.indexOf(("." + bp.getColorId() + ".")) == -1) {
			MergeProductResult mergeProductResult = new MergeProductResult();
			SysDictionary dictionary = sysDictionaryService.getSysDictionaryByValue("productColor", bp.getColorId());
			// 是该本产品 页面默认选中颜色（状态2）
			mergeProductResult.setState(1);
			if (bp.getColorId().intValue() == baseProduct.getColorId().intValue()) {
				mergeProductResult.setState(2);
			}
			mergeProductResult.setColorId(bp.getColorId());
			mergeProductResult.setProductId(mergeId);
			mergeProductResult.setColorName(dictionary.getName());
			baseProduct.getColorlist().add(mergeProductResult);
		}
		colorValue = colorValue + bp.getColorId() + ".";
		return colorValue;
	}

	// 匹配产品属性
	// TODO
	private void matchProductParam(BaseProduct baseProduct, List<Integer> proIdList,
			Map<String, List<ProductAttribute>> map, Set<String> set) {
		for (String str : set) {
			ProductAttribute attributeKey = new ProductAttribute();
			// list 不会抽
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

	/**
	 * 产品材质路径
	 * 
	 * @param baseProduct
	 */
	private void getMaterialPath(BaseProduct baseProduct) {
		ResPic resPic;
		resPic = new ResPic();
		resPic.setFileKey("product.baseProduct.material");
		resPic.setBusinessId(baseProduct.getId());
		resPic.setOrders(" sequence asc ");
		List<ResPic> materialPics = resPicService.getList(resPic);
		if (materialPics != null && materialPics.size() > 0) {
			String[] materialPaths = new String[materialPics.size()];
			for (int i = 0; i < materialPics.size(); i++) {
				resPic = materialPics.get(i);
				if (resPic != null) {
					materialPaths[i] = resPic.getPicPath();
				}
			}
			baseProduct.setMaterialPicPaths(materialPaths);
		}
	}

	/***
	 * 获取材质
	 * 
	 * @param baseProduct
	 * @param resp
	 * @param picList
	 * @param materialName
	 */
	private void getMaterialInfo(BaseProduct baseProduct, ResPic resp, List<String> picList, String materialName) {
		List<ResPic> resPicList = resPicService.getPicList(picList);
		// start sql 材质
		for (int i = 0; i < resPicList.size(); i++) {
			MergeProductResult mergeProductResult = new MergeProductResult();
			mergeProductResult.setState(1);
			ResPic resPic = resPicList.get(i);
			if (resPic != null) {
				if (materialName.indexOf(("." + resPic.getPicName() + ".")) == -1) {
					// 页面默认选中材质（状态2）
					if (resp != null) {
						if (resp.getPicName().equals(resPic.getPicName())) {
							mergeProductResult.setState(2);
						}
					}
					if (resPic.getProductId() == baseProduct.getId()) {
						mergeProductResult.setState(2);
					}
					mergeProductResult.setProductId(resPic.getProductId());
					mergeProductResult.setMaterialPicId(resPic.getId());
					mergeProductResult.setMaterialName(resPic.getPicName());
					mergeProductResult.setMaterialPicPath(resPic.getPicPath());
					baseProduct.getMateriallist().add(mergeProductResult);
				}
				materialName = materialName + resPic.getPicName() + ".";
			}
		} // end sql 材质
	}

	/***
	 * 根据类型取对应u3dModel
	 * 
	 * @param baseProduct
	 * @param mediaType
	 * @return
	 */
	public static Integer getU3dModelId(BaseProduct baseProduct) {
		Integer u3dModel = 0;
		String mediaType = baseProduct.getMediaType();
		if ("3".equals(mediaType)) {
			u3dModel = baseProduct.getWindowsU3dModelId();
			baseProduct.setIpadU3dModelId(0);
		} else if ("4".equals(mediaType)) {
			u3dModel = baseProduct.getMacBookU3dModelId();

		} else if ("5".equals(mediaType)) {
			u3dModel = baseProduct.getIosU3dModelId();

		} else if ("6".equals(mediaType)) {
			u3dModel = baseProduct.getAndroidU3dModelId();

		} else if ("7".equals(mediaType)) {
			u3dModel = baseProduct.getIpadU3dModelId();

		} else {
			u3dModel = Utils.getIntValue(baseProduct.getU3dModelId());

		}
		return u3dModel;
	}
}
