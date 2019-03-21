package com.sandu.service.templet.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sandu.api.house.bo.JumpPositionDesignTempletProductBO;
import com.sandu.common.constant.house.DoorBigType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.product.model.BaseProduct;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.house.search.DrawDesignTempletProductSearch;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.house.service.DrawDesignTempletProductService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.common.constant.RegionMarkConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.common.constant.house.DrawDesignTempletProductConstant;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.product.dao.BaseProductMapper;
import com.sandu.service.templet.dao.DrawDesignTempletProductMapper;
import com.sandu.util.Utils;
import com.sandu.util.auth.HouseAuthUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("DrawDesignTempletProductService")
public class DrawDesignTempletProductServiceImpl implements DrawDesignTempletProductService {

	int DEFAULTS_UPDATE_COUNT = 10;
	
	@Autowired
	private ResModelService resModelService;

	@Autowired
	private BaseProductMapper baseProductMapper;
	
	@Autowired
	private DrawBaseProductService drawBaseProductService;
	
	@Autowired
	private DrawDesignTempletProductMapper drawDesignTempletProductMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DrawBaseHouseSubmitDTO saveDesignTempletProductBySubmit(DrawBaseHouseSubmitDTO dto, UserLoginBO userLoginBO) throws DrawBusinessException {
		if (dto == null) {
			return null;
		}
		if (userLoginBO == null) {
			userLoginBO = HouseAuthUtils.getUnknownUser();
		}

		// group index存放
		Map<String, String> idxMap = new HashMap<>();
		
		// 区域标识index
		List<RegionMark> regions = this.buildRegionMark();
		
		// 整理绑定点信息
		Map<String, List<Long>> bingInfoMap = this.getBingInfoMap(dto);

		for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
			// 重置区域标识index
			this.resetRegionMark(regions);

			for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
				if (drawBaseProductDTO.getModelType() == null) {
					throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的modleType字段不能为空"));
				}
				
				// public文件不保存在样板房产品表
				if (DrawBaseProductConstant.MODEL_TYPE_PUBLIC.intValue() == drawBaseProductDTO.getModelType().intValue()) {
					log.debug("跳过不处理的公共部分{}(productId)", drawBaseProductDTO.getUniqueId());
					continue;
				}
				
				// wrap
				DrawDesignTempletProduct drawDesignTempletProduct = this.wrapDrawDesignTempletProduct(drawSpaceCommonDTO, drawBaseProductDTO, userLoginBO);
				drawDesignTempletProduct.setBindParentProductid(Utils.listToStr(bingInfoMap.get(drawBaseProductDTO.getUniqueId())));

				// 组合产品处理
				if (drawBaseProductDTO.getGroupProductId() != null && drawBaseProductDTO.getGroupProductId() > 0) {
					// 组合产品
					drawDesignTempletProduct.setProductGroupId(drawBaseProductDTO.getGroupProductId());
					// 是否主产品 ==> 0-> 否、1-> 是
					drawDesignTempletProduct.setIsMainProduct(drawBaseProductDTO.getIsMainProduct());
					// 组合类型 ==> 0-> 组合、1-> 结构
					drawDesignTempletProduct.setGroupType(DrawDesignTempletProductConstant.GROUP_PRODUCT_TYPE);
					// 组合产品唯一标识，判断存在两个相同的组合产品的唯一标识
					drawDesignTempletProduct.setGroupUniqueId(drawBaseProductDTO.getGroupUniqueId());

					// 通用app需要这样处理,用来区分多个相同的组合产品;plan_group_id.
					// 处理组合的产品，相同的组合产品的planGroupId = groupProductId_后缀一样
					drawDesignTempletProduct.setPlanGroupId(drawBaseProductDTO.getGroupProductId() + "_" + getGroupIndex(idxMap, drawBaseProductDTO.getGroupUniqueId()));
				}

				// 地面、天花区域标识处理
				// 相同的类型以基数累加，
				// eg: 同一个空间里有5块地面以基数10开始累加，regionMark ==> 10 11...15;
				// 不同类型基数不一样{@link com.sandu.common.constant.RegionMarkConstant}
				if (RegionMarkConstant.contains(drawBaseProductDTO.getSmallTypeValueKey())) {
					int regionMark = getRegionMark(regions, drawBaseProductDTO.getSmallTypeValueKey());
					log.debug("地面、天花区域标识处理, regionMark ==> {}", regionMark);
					if (regionMark > 0) {
						drawDesignTempletProduct.setRegionMark(Objects.toString(regionMark, Utils._ONE_VALUE));
					}
				}

				// 加入软装白膜逻辑
				if (drawBaseProductDTO.isSoft()) {
					drawDesignTempletProduct.setCreateProductStatus(DrawDesignTempletProductConstant.SOFT_PRODUCT);

					// 处理拉伸过的软装白膜产品
					// copy一条被拉伸的白膜产品，fullPaveLength = 被拉伸的长度
					this.handlerChangedSoftProduct(drawBaseProductDTO, drawDesignTempletProduct);
				}

				this.add(drawDesignTempletProduct);
				drawBaseProductDTO.setDesignTempletProductId(drawDesignTempletProduct.getId());
			}
		}

		return dto;
	}
	
	/**
	 * 处理拉伸过的软装白膜产品.
	 * </p>
	 * copy一条拉被伸的白膜产品，fullPaveLength = 被拉伸的长度
	 * 
	 * @param baseProduct
	 * @param wrapProduct
	 */
	private void handlerChangedSoftProduct(DrawBaseProductDTO baseProduct, DrawDesignTempletProduct wrapProduct) {
		if (baseProduct == null) {
			log.warn("参数[baseProduct]为空！");
			return;
		}

		// 是否拉伸过 == !1
		if (!DrawBaseProductConstant.IS_CHANGED_SOFT.equals(baseProduct.getIsChanged())) {
			log.debug("白膜产品 {}(productId)没有拉伸，不处理", baseProduct.getProductId());
			return;
		}

		log.debug("处理拉伸的软装白膜产品, {}(productId)", baseProduct.getProductId());
		BaseProduct newProduct = baseProductMapper.selectByPrimaryKey(baseProduct.getProductId());
		
		Objects.requireNonNull(newProduct, ResponseEnum.SOFT_PRODUCT_NOT_FOUND.getMessage());
		Objects.requireNonNull(newProduct.getWindowsU3dmodelId(), ResponseEnum.U3D_MODEL_NOT_FOUND.getMessage());

		// copy 产品的模型文件
		ResModel resFile = resModelService.selectByPrimaryKey(newProduct.getWindowsU3dmodelId());
		Long windowsU3dmodelId = resModelService.copyModel(resFile, SystemConfigEnum.BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), false);
		if (windowsU3dmodelId == null) {
			throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
		}

		Date now = new Date();
		// id ==> null
		newProduct.setId(null);
		newProduct.setGmtCreate(now);
		newProduct.setGmtModified(now);
		// 拉伸标识
		newProduct.setIsStretched(DrawBaseProductConstant.IS_CHANGED_SOFT);
		// 全铺长度
		newProduct.setFullPaveLength(baseProduct.getProductLength());
		// u3d model 文件
		newProduct.setWindowsU3dmodelId(windowsU3dmodelId.intValue());
		// product_code
		String productCode = drawBaseProductService.getProductCode(baseProduct.getSmallTypeValueKey());
		newProduct.setProductCode(productCode);

		// save
		baseProductMapper.insertSelective(newProduct);
		wrapProduct.setProductId(newProduct.getId().intValue());
	}
	
	/**
	 * 处理组合的产品，相同的组合产品的planGroupId = groupProductId_后缀一样
	 * 
	 * @param idxMap
	 * @param groupUniqueId
	 * @return
	 */
	private String getGroupIndex(Map<String, String> idxMap, String groupUniqueId) {
		if (StringUtils.isEmpty(groupUniqueId)) {
			throw new BusinessException(false, ResponseEnum.GROUP_UNIQUEID_EMPTY);
		}

		if (idxMap.containsKey(groupUniqueId)) {
			return idxMap.get(groupUniqueId);
		} else {
			String groupIndex = Objects.toString(System.nanoTime());
			idxMap.put(groupUniqueId, groupIndex);
			return groupIndex;
		}
	}
	
	/**
	 * 拼装绑定点信息
	 * Map<String, List<String>> bingInfoMap
	 * key:主墙uniqueId
	 * value:背景墙draw_base_product -> id
	 * @param dto
	 * @return
	 */
	private Map<String, List<Long>> getBingInfoMap(DrawBaseHouseSubmitDTO dto) {
		String logMesPrefix = "DrawDesignTempletProductServiceImpl.getBingInfoMap -> ";

		// 先整理出一份uniqueId对应drawBaseProductId的map ->start
		Map<String, Long> uniqueIdToDrawBaseProductIdMap = new HashMap<>();

		for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
			for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
				if (StringUtils.isEmpty(drawBaseProductDTO.getUniqueId())) {
					log.error(logMesPrefix + "StringUtils.isEmpty(drawBaseProductDTO.getUniqueId()) = true");
					throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
				}
				uniqueIdToDrawBaseProductIdMap.put(drawBaseProductDTO.getUniqueId(), drawBaseProductDTO.getProductId());
			}
		}
		// 先整理出一份uniqueId对应drawBaseProductId的map ->end

		Map<String, List<Long>> bingInfoMap = new HashMap<>();

		for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
			for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
				String bindUniqueId = drawBaseProductDTO.getBindUniqueId();
				String uniqueId = drawBaseProductDTO.getUniqueId();
				if (StringUtils.isEmpty(bindUniqueId)) {
					continue;
				}
				if (StringUtils.isEmpty(uniqueId)) {
					log.error(logMesPrefix + "StringUtils.isEmpty(uniqueId) = true");
					throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
				}
				if (!uniqueIdToDrawBaseProductIdMap.containsKey(uniqueId)) {
					log.error(logMesPrefix + "cannot find value by key:{}; map:{}", bindUniqueId, uniqueIdToDrawBaseProductIdMap.toString());
				}
				if (bingInfoMap.containsKey(bindUniqueId)) {
					List<Long> drawProductIdList = bingInfoMap.get(bindUniqueId);
					if (drawProductIdList == null || drawProductIdList.size() == 0) {
						drawProductIdList = new ArrayList<>();
						drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
						bingInfoMap.put(bindUniqueId, drawProductIdList);
					} else {
						drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
					}
				} else {
					List<Long> drawProductIdList = new ArrayList<>();
					drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
					bingInfoMap.put(bindUniqueId, drawProductIdList);
				}
			}
		}
		return bingInfoMap;
	}

	private DrawDesignTempletProduct wrapDrawDesignTempletProduct(DrawSpaceCommonDTO drawSpace, DrawBaseProductDTO drawBaseProduct, UserLoginBO userLoginBO) {
		Date now = new Date();
		DrawDesignTempletProduct drawDesignTempletProduct = new DrawDesignTempletProduct();
		drawDesignTempletProduct.setProductId(Integer.valueOf(drawBaseProduct.getProductId() + ""));
		drawDesignTempletProduct.setDesignTempletId(Integer.valueOf(drawSpace.getDesignTempletId() + ""));
		drawDesignTempletProduct.setGmtCreate(now);
		drawDesignTempletProduct.setGmtModified(now);
		drawDesignTempletProduct.setCreator(userLoginBO.getLoginName());
		drawDesignTempletProduct.setModifier(userLoginBO.getLoginName());
		drawDesignTempletProduct.setIsDeleted(DrawDesignTempletProductConstant.DEFAULTS_IS_DELETED);
		drawDesignTempletProduct.setUniqueId(drawBaseProduct.getUniqueId());
		drawDesignTempletProduct.setWallOrientation(drawBaseProduct.getWallOrientation());
		drawDesignTempletProduct.setWallType(drawBaseProduct.getWallType());
		drawDesignTempletProduct.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));

		return drawDesignTempletProduct;
	}
	
	private Long add(DrawDesignTempletProduct drawDesignTempletProduct) {
		// 参数验证 ->end
		if (drawDesignTempletProduct == null) {
			return null;
		}
		// 参数验证 ->start

		drawDesignTempletProductMapper.insertSelective(drawDesignTempletProduct);
		return drawDesignTempletProduct.getId();
	}

	@Override
	public List<DrawDesignTempletProduct> findAllBySearch(DrawDesignTempletProductSearch drawDesignTempletProductSearch) {
		// 参数验证 ->start
		if (drawDesignTempletProductSearch == null) {
			return null;
		}
		// 参数验证 ->end

		return drawDesignTempletProductMapper.findAllBySearch(drawDesignTempletProductSearch);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(DrawDesignTempletProduct drawDesignTempletProduct) {
		// 参数验证 ->start
		if (drawDesignTempletProduct == null) {
			return;
		}
		// 参数验证 ->end

		drawDesignTempletProductMapper.updateByPrimaryKeySelective(drawDesignTempletProduct);
	}

	@Override
	public List<DrawDesignTempletProduct> findAllByDesignTempletId(Long designTempletId) {
		// 参数验证 ->start
		if (designTempletId == null) {
			return null;
		}
		// 参数验证 ->end

		DrawDesignTempletProductSearch drawDesignTempletProductSearch = new DrawDesignTempletProductSearch();
		drawDesignTempletProductSearch.setDesignTempletId(Integer.valueOf(designTempletId + ""));
		return this.findAllBySearch(drawDesignTempletProductSearch);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<Long, Long> transformToDesignTempletProduct(
			List<DrawDesignTempletProduct> drawDesignTempletProductList, 
			Long designTempletId, Map<Long, Long> drawBaseProductTransformMap) throws DrawBusinessException {
		Map<Long, Long> returnMap = new HashMap<>();

		// 参数验证 ->start
		if (drawDesignTempletProductList == null || drawDesignTempletProductList.size() == 0) {
			return returnMap;
		}
		if (designTempletId == null) {
			return returnMap;
		}
		if (drawBaseProductTransformMap == null) {
			return returnMap;
		}
		// 参数验证 ->end
		
		log.info("开始转化design_templet_product信息数据");

		for (DrawDesignTempletProduct drawDesignTempletProduct : drawDesignTempletProductList) {
			drawDesignTempletProduct.setDesignTempletId(Integer.valueOf(designTempletId + ""));
			if (DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(drawDesignTempletProduct.getCreateProductStatus())) {
				log.debug("同步{}(productId)软装产品信息", drawDesignTempletProduct.getProductId());
			} else {
				if (drawBaseProductTransformMap.get(Long.valueOf(drawDesignTempletProduct.getProductId() + "")) == null) {
					log.error("产品 {}(productId)的数据丢失", drawDesignTempletProduct.getProductId());
					throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品"+ drawDesignTempletProduct.getProductId() +"(productId)的数据丢失"));
				}
				drawDesignTempletProduct.setProductId(Integer.valueOf(drawBaseProductTransformMap.get(Long.valueOf(drawDesignTempletProduct.getProductId() + "")) + ""));
				drawDesignTempletProduct.setBindParentProductid(this.getBindParentProductidByDrawBaseProductTransformMap(drawDesignTempletProduct.getBindParentProductid(),
						drawBaseProductTransformMap));
			}
			
			drawDesignTempletProductMapper.transformToDesignTempletProduct(drawDesignTempletProduct);
			drawDesignTempletProduct.setDesignTempletId(null);
			drawDesignTempletProduct.setProductId(null);
			drawDesignTempletProduct.setBindParentProductid(null);
			this.update(drawDesignTempletProduct);
			returnMap.put(drawDesignTempletProduct.getId(), drawDesignTempletProduct.getDesignTempletProductId());
		}
		
		log.info("转化design_templet_product信息数据结束");

		return returnMap;
	}

	private String getBindParentProductidByDrawBaseProductTransformMap(String drawBindParentProductidListStr, 
			Map<Long, Long> drawBaseProductTransformMap) {
		// 参数验证 ->start
		if (StringUtils.isEmpty(drawBindParentProductidListStr)) {
			return null;
		}
		if (drawBaseProductTransformMap == null) {
			return null;
		}
		// 参数验证 ->end

		List<Long> drawBindParentProductIdList = Utils.getLongListByStr(drawBindParentProductidListStr);
		if (drawBindParentProductIdList == null || drawBindParentProductIdList.size() == 0) {
			return null;
		}
		List<Long> bindParentProductIdList = new ArrayList<>();
		for (Long drawBindParentProductId : drawBindParentProductIdList) {
			Long bindParentProductId = drawBaseProductTransformMap.get(drawBindParentProductId);
			if (bindParentProductId == null) {
				return null;
			}
			bindParentProductIdList.add(bindParentProductId);
		}
		return Utils.listToStr(bindParentProductIdList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDrawDesignTempletProduct(Integer status, List<Long> emptyTemplets) {
		if (emptyTemplets == null || emptyTemplets.isEmpty()) {
			return -1;
		}
		return drawDesignTempletProductMapper.deleteDrawDesignTempletProduct(status, emptyTemplets);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDesignTempletProduct(Integer status, List<Long> designTemplets) {
		if (designTemplets == null || designTemplets.isEmpty()) {
			return 0;
		}
		return drawDesignTempletProductMapper.deleteDesignTempletProduct(status, designTemplets);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer emptyDesignTempletProduct(Integer status, List<Long> designTemplets) {
		if (designTemplets == null || designTemplets.isEmpty()) {
			return -1;
		}
		return drawDesignTempletProductMapper.emptyDesignTempletProduct(status, designTemplets);
	}

	@Override
	public List<JumpPositionDesignTempletProductBO> getJumpPositionDesignTempletProduct(String proType, Long houseId) {
		if (houseId == null || houseId <= 0) {
			log.warn("参数 designTemplets 为空");
			return null;
		}

		return drawDesignTempletProductMapper.getJumpPositionDesignTempletProduct(Objects.toString(proType, DoorBigType.MENG.toString()), houseId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer batchUpdateDesignTempletProduct(List<DrawDesignTempletProduct> drawDesignTempletProducts) {
		if (drawDesignTempletProducts == null || drawDesignTempletProducts.isEmpty()) {
			log.warn("参数 drawDesignTempletProducts 为空");
			return -1;
		}

		int updateCount = 0;
		List<DrawDesignTempletProduct> updateProducts = new ArrayList<>();
		for (DrawDesignTempletProduct drawDesignTempletProduct : drawDesignTempletProducts) {
			updateCount += this.handlerBatchUpdate(drawDesignTempletProduct, updateProducts);
		}

		if (!updateProducts.isEmpty()) {
			updateCount += drawDesignTempletProductMapper.batchUpdateDesignTempletProduct(updateProducts);
		}

		return updateCount;
	}

	Integer handlerBatchUpdate(DrawDesignTempletProduct dtp, List<DrawDesignTempletProduct> updateProducts) {
		updateProducts.add(dtp);
		if (updateProducts.size() >= DEFAULTS_UPDATE_COUNT) {
			int updateCount = drawDesignTempletProductMapper.batchUpdateDesignTempletProduct(updateProducts);
			updateProducts.clear();
			return updateCount;
		}
		return 0;
	}

	/**
	 * 
	 * 地面、天花区域标识处理.
	 * 
	 * <pre>
	 * 相同的类型以基数累加.
	 * 	eg: 同一个空间里有5块地面以基数10开始累加，regionMark ==> 10 11...15;
	 * 不同类型基数不一样{@link com.sandu.common.constant.RegionMarkConstant}
	 * </pre>
	 * 
	 * @param regions
	 * @param valueKey
	 * @return -1 标识不是地面类型处理
	 */
	private int getRegionMark(List<RegionMark> regions, String valueKey) {
		if (regions == null || regions.isEmpty()) {
			return -1;
		}

		for (RegionMark region : regions) {
			int regionMark = region.getRegionMark(valueKey);
			if (regionMark > 0) {
				return regionMark;
			}
		}

		return -1;
	}

	/**
	 * @return
	 */
	private List<RegionMark> buildRegionMark() {
		List<RegionMark> regions = new ArrayList<>();
		// 地面
//		regions.add(new RegionMark(RegionMarkConstant.PORCH_FLOOR));// 玄关地面 => 20
//		regions.add(new RegionMark(RegionMarkConstant.AISLE_FLOOR));// 过道地面 => 30
//		regions.add(new RegionMark(RegionMarkConstant.MAIN_FLOOR));// 主地面地面 => 10
//
//		// 天花
//		regions.add(new RegionMark(RegionMarkConstant.PORCH_PLATFOND));// 玄关天花 => 20
//		regions.add(new RegionMark(RegionMarkConstant.AISLE_PLATFOND));// 过道天花 => 30
//		regions.add(new RegionMark(RegionMarkConstant.MAIN_PLATFOND));// 主地面天花 => 10

		RegionMarkConstant[] regionMarks = RegionMarkConstant.values();
		for (RegionMarkConstant regionMark : regionMarks) {
			regions.add(new RegionMark(regionMark));
		}

		return regions;
	}
	
	/**
	 * 重置区域标识index
	 * 
	 * @param regions
	 */
	private void resetRegionMark(List<RegionMark> regions) {
		for (RegionMark region : regions) {
			region.reset();
		}
	}

	/**
	 * 区域标识处理类
	 * 
	 * created by songjianming@sanduspace.cn on 2018/3/19
	 */
	class RegionMark {

		private int index;
		private RegionMarkConstant region;

		RegionMark(RegionMarkConstant region) {
			this.region = region;
			this.index = region.getIndex();
		}

		int getRegionMark(String valueKey) {
			if (this.region.getValuekey().equals(valueKey)) {
				return index++;
			}
			return -1;
		}

		void reset() {
			this.index = region.getIndex();
		}
	}
}