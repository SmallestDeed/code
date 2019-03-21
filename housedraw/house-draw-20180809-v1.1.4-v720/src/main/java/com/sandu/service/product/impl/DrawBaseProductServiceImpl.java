package com.sandu.service.product.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sandu.api.house.model.*;
import com.sandu.api.house.service.*;
import com.sandu.api.product.model.BaseProduct;
import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.product.service.ProductAttributeService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.common.constant.attr.DoorAttr;
import com.sandu.service.house.dao.BaseHouseMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.house.bo.DrawBaseProductBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.common.constant.BaseProductStatusConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.product.dao.BaseProductMapper;
import com.sandu.service.product.dao.DrawBaseProductMapper;
import com.sandu.util.Utils;
import com.sandu.util.auth.HouseAuthUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */

@Slf4j
@Service
public class DrawBaseProductServiceImpl implements DrawBaseProductService {

	public static final int DEFAULT_STRATEGY_LOOP = 20;
	public static final String PRODUCT_CODE_PREFIX = "baimo_";
	public static final int DEFAULTS_UPDATE_COUNT = 10;

	@Autowired
	private DrawBaseProductMapper drawBaseProductMapper;

	@Autowired
	private SystemDictionaryService systemDictionaryService;

	@Autowired
	private DrawResFileService drawResFileService;

	@Autowired
	private ResModelService resModelService;

	@Autowired
	private BaseProductMapper baseProductMapper;
	
	@Autowired
	private DrawDesignTempletService drawDesignTempletService;

	@Autowired
	private ProductAttributeService productAttributeService;

	@Autowired
	private BaseHouseMapper baseHouseMapper;

	@Override
	public List<DrawBaseProductBO> listBaseProductBo(List<Long> spaceIds, Integer createProductStatus) {
		return drawBaseProductMapper.listBaseProductBo(spaceIds, createProductStatus);
	}

	@Override
	public List<DrawBaseProduct> listDrawBaseProductByIds(List<Long> ids) {
	    if (ids == null || ids.isEmpty()) {
	        return null;
        }
		return drawBaseProductMapper.listDrawBaseProductByIds(ids);
	}

	@Override
	public Long saveBaseProduct(DrawBaseProduct baseProduct) {
		return drawBaseProductMapper.saveBaseProduct(baseProduct);
	}

	@Override
	public void updateProductModel(Long resModelId, Long productId, String fileType) {
		drawBaseProductMapper.updateBaseProduct(resModelId, productId, fileType);
	}

	@Override
	public Long saveDrawBaseProduct(DrawBaseProduct product) {
		return drawBaseProductMapper.saveDrawBaseProduct(product);
	}

	@Override
	public void batchDeleteDrawBaseProduct(List<Long> productIds) {
		drawBaseProductMapper.batchDelete(productIds);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DrawBaseHouseSubmitDTO saveDrawBaseProductBySubmit(DrawBaseHouseSubmitDTO dto,
															  UserLoginBO userLoginBO) throws DrawBusinessException {
		// 参数校验 ->start
		if (dto == null) {
			return null;
		}
		if (userLoginBO == null) {
			userLoginBO = HouseAuthUtils.getUnknownUser();
		}
		// 参数校验 ->end

		DoorAttr doorAttr = new DoorAttr();

		for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
			// 存放公共部分数据
			List<PublicProductInfoDTO> publicProductInfoDTOList = new ArrayList<>();
			for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
				if (drawBaseProductDTO.getModelType() == null) {
					throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的modleType字段不能为空"));
				}
				
				if (drawBaseProductDTO.getUniqueId() == null) {
					throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的uniqueId字段不能为空"));
				}
				
				if (DrawBaseProductConstant.MODEL_TYPE_SOFT.intValue() == drawBaseProductDTO.getModelType().intValue()) {
					drawBaseProductDTO.setSoft(true);
					continue;
				} else if (DrawBaseProductConstant.MODEL_TYPE_PUBLIC.intValue() == drawBaseProductDTO.getModelType().intValue()) {
					// 识别为窗框/栏杆之类/阳台门
					if (drawBaseProductDTO.getProductFileId() == null) {
						throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的productFileId字段不能为空"));
					}

					PublicProductInfoDTO publicProductInfoDTO = new PublicProductInfoDTO(drawBaseProductDTO.getProductFileId(), drawBaseProductDTO.getUniqueId());
					publicProductInfoDTOList.add(publicProductInfoDTO);
					continue;
				}
				// 软装产品不用保存 ->end

				if (StringUtils.isEmpty(drawBaseProductDTO.getBigTypeValueKey())) {
					throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
				}
				
				if (StringUtils.isEmpty(drawBaseProductDTO.getSmallTypeValueKey())) {
					throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
				}

				// TODO 可优化
				SystemDictionary systemDictionarySmallType = systemDictionaryService.findOneByValueKey(drawBaseProductDTO.getSmallTypeValueKey());
				if (systemDictionarySmallType == null) {
					throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("数据字典未找到;valuekey = " + drawBaseProductDTO.getSmallTypeValueKey()));
				}
				
				SystemDictionary systemDictionaryBigType = systemDictionaryService.findOneByValueKey(systemDictionarySmallType.getType());
				if (systemDictionaryBigType == null) {
					throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("数据字典未找到;valuekey = " + drawBaseProductDTO.getBigTypeValueKey()));
				}
				// TODO 可优化

				String randomNumStr = Utils.generateRandomDigitString(6);
				String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
				Date now = new Date();

				DrawBaseProduct drawBaseProduct = new DrawBaseProduct();
				// product_code
				String productCode = getProductCode(systemDictionarySmallType.getType(), systemDictionarySmallType.getValuekey());
				drawBaseProduct.setProductCode(productCode);
				drawBaseProduct.setProductName(systemDictionaryBigType.getName() + "_" + systemDictionarySmallType.getName() + "_" + randomNumStr);
				drawBaseProduct.setProductLength(drawBaseProductDTO.getProductLength());
				drawBaseProduct.setProductWidth(drawBaseProductDTO.getProductWidth());
				drawBaseProduct.setProductHeight(drawBaseProductDTO.getProductHeight());
				drawBaseProduct.setSysCode(sysCode);
				drawBaseProduct.setCreator(userLoginBO.getLoginName());
				drawBaseProduct.setGmtCreate(now);
				drawBaseProduct.setModifier(userLoginBO.getLoginName());
				drawBaseProduct.setGmtModified(now);
				drawBaseProduct.setIsDeleted(DrawBaseProductConstant.DEFAULTS_IS_DELETED_);
				drawBaseProduct.setHouseTypeValues(DrawBaseProductConstant.DEFAULTS_HOUSE_TYPES);
				drawBaseProduct.setProductTypeValue(systemDictionaryBigType.getValue() + "");
				drawBaseProduct.setProductSmallTypeValue(systemDictionarySmallType.getValue());
				drawBaseProduct.setProductTypeMark(systemDictionaryBigType.getValuekey());
				drawBaseProduct.setProductSmallTypeMark(systemDictionarySmallType.getValuekey());
				drawBaseProduct.setSyncStatus("NOT_SYNCHRONIZED");
				drawBaseProduct.setCodeIsEmploy(1);
				drawBaseProduct.setFullPaveLength(drawBaseProductDTO.getProductLength());
				drawBaseProduct.setBrandId(-1);
				drawBaseProduct.setBakeBeforeFileId(drawBaseProductDTO.getProductFileId());

				// 厨房门、入户门、房间门、卫生间门属性处理
				if (doorAttr.contains(drawBaseProduct.getProductSmallTypeMark()) && doorAttr.containsProAttrType(drawBaseProductDTO.getProAttrType())) {
					DoorAttr.DoorAttrType attrType = doorAttr.getDoorAttrType(drawBaseProduct.getProductSmallTypeMark());
					if (attrType != null) {
						drawBaseProduct.setProAttrKey(Objects.toString(attrType.getLongCode(), ""));
						String attrValType = doorAttr.getDoorAttrValueType(drawBaseProduct.getProductSmallTypeMark(), drawBaseProductDTO.getProAttrType());
						drawBaseProduct.setProAttrValKey(attrValType);
						log.info("处理门的属性，{}(proAttrType), {}(attrType), {}(attrValType)", attrType, attrValType, drawBaseProductDTO.getProAttrType());
					}
				}

				// 发布状态
				drawBaseProduct.setPutawayState(getPutawayState(dto.getDrawHouse()));
				
				// save
				this.add(drawBaseProduct);
				drawBaseProductDTO.setProductId(drawBaseProduct.getId());
			}

			StringBuilder publicProductInfo = new StringBuilder();
			try {
				publicProductInfo.append(new ObjectMapper().writeValueAsString(publicProductInfoDTOList));
			} catch (JsonProcessingException e) {
				throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED, e);
			}

			// 保存public文件(窗框/栏杆/阳台门等)信息在draw_design_templet表中 ->start
			DrawDesignTemplet drawDesignTemplet = new DrawDesignTemplet();
			if (drawSpaceCommonDTO.getDesignTempletId() == null) {
				throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
			}
			drawDesignTemplet.setId(drawSpaceCommonDTO.getDesignTempletId());
			drawDesignTemplet.setPublicProductInfo(publicProductInfo.toString());
			drawDesignTempletService.update(drawDesignTemplet);
			// 保存public文件(窗框/栏杆/阳台门等)信息在draw_design_templet表中 ->end
		}
		
		return dto;
	}

	/**
	 * 获取发布状态
	 *
	 * @param drawHouse
	 * @return
	 */
	private Integer getPutawayState(DrawBaseHouse drawHouse) {
		if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
			return BaseProductStatusConstant.HAS_BEEN_RELEASE;
		}

		// 检查原先是否发布，如果发布继续为发布状态
		if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
			Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
			return (putawayState == null) ? BaseProductStatusConstant.TESTING : putawayState;
		}

		return BaseProductStatusConstant.TESTING;
	}
	
	/**
	 * 解决product code 重复问题
	 * 
	 * @param dictType
	 * @param dictValueKey
	 * @return
	 * @throws DrawBusinessException
	 */
	private String getProductCode(String dictType, String dictValueKey) throws DrawBusinessException {
		boolean isNnique;
		int loop = DEFAULT_STRATEGY_LOOP;

		StringBuilder prefix = new StringBuilder();
		prefix.append(PRODUCT_CODE_PREFIX).append(dictType).append("_").append(dictValueKey);
		StringBuilder suffix = new StringBuilder();

		do {
			// suffix.append(prefix).append("_").append(Utils.generateRandomDigitString(6));
			// random => 10位随机数
			suffix.append(prefix).append("_").append(Utils.random());
			isNnique = (drawBaseProductMapper.findBaseProductByProductCode(suffix.toString()) <= 0);
			log.debug("Draw base product code. isNnique => {}", isNnique);
			
			if (isNnique) {
				// base_product
				isNnique = (baseProductMapper.findBaseProductByProductCode(suffix.toString()) <= 0);
				log.debug("Base product code. isNnique => {}", isNnique);
			}
			
			log.debug("Generator {}(productCode). loop count ==> {}, isNnique ==> {}", suffix.toString(), loop, isNnique);
			if (!isNnique) {
				suffix.setLength(0);
			}
		} while ((loop--) <= 0 || !isNnique);

		if (isNnique) {
			return suffix.toString();
		}

		log.error("生成产品编码异常, isNnique ==> {}", isNnique);
		throw new DrawBusinessException(false, ResponseEnum.GENERATE_PRODUCT_CODE_FAILED);
	}
	
	@Override
	public String getProductCode(String typeValueKey) throws DrawBusinessException {
		String errorMsg = "数据字典未找到;valuekey=" + typeValueKey;
		if (StringUtils.isEmpty(typeValueKey)) {
			throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
		}

		// dict
		SystemDictionary dict = systemDictionaryService.findOneByValueKey(typeValueKey);
		Objects.requireNonNull(dict, errorMsg);

		return getProductCode(dict.getType(), dict.getValuekey());
	}

	@Override
	public Long add(DrawBaseProduct drawBaseProduct) {
		// 参数验证 ->start
		if (drawBaseProduct == null) {
			return null;
		}
		// 参数验证 ->end

		drawBaseProductMapper.insertSelective(drawBaseProduct);

		return drawBaseProduct.getId();
	}

	@Override
	public DrawBaseProduct get(Long id) {
		// 参数验证 ->start
		if (id == null) {
			return null;
		}
		// 参数验证 ->end

		return drawBaseProductMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(DrawBaseProduct drawBaseProduct) {
		// 参数验证 ->start
		if (drawBaseProduct == null) {
			return;
		}
		// 参数验证 ->end

		drawBaseProductMapper.updateByPrimaryKeySelective(drawBaseProduct);
	}

	@Override
	public List<DrawBaseProduct> findAllByDrawDesignTempletId(Long drawDesignTempletId) {
		if (drawDesignTempletId == null) {
			return null;
		}

		return drawBaseProductMapper.findAllByDrawDesignTempletId(drawDesignTempletId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<Long, Long> transformToBaseProduct(List<DrawBaseProduct> drawBaseProductList) throws DrawBusinessException {
		Map<Long, Long> returnMap = new HashMap<>();

		// 参数验证 ->start
		if (drawBaseProductList == null || drawBaseProductList.size() == 0) {
			return returnMap;
		}
		// 参数验证 ->end
		
		log.info("开发转化base_product信息数据");

		DoorAttr doorAttr = new DoorAttr();

		for (DrawBaseProduct drawBaseProduct : drawBaseProductList) {
			// 转换产品模型文件 ->start
			if (drawBaseProduct.getWindowsU3dmodelId() == null) {
				log.warn("WindowsU3dmodelId为空！ ==> {}", drawBaseProduct);
				continue;
			}
			
			DrawResFile drawResFile = drawResFileService.get(Long.valueOf(drawBaseProduct.getWindowsU3dmodelId() + ""));
			Long windowsU3dmodelId = resModelService.createByDrawResFile(drawResFile, SystemConfigEnum.BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), true);
			if (windowsU3dmodelId == null) {
				throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
			}
			drawBaseProduct.setWindowsU3dmodelId(Integer.valueOf(windowsU3dmodelId + ""));
//			drawBaseProduct.setPutawayState(BaseProductStatusConstant.DRAW);
			// 转换产品模型文件 ->end
			
			// 产品信息已同步
			BaseProduct baseProduct = baseProductMapper.getBaseProductByProductCode(drawBaseProduct.getProductCode());
			if (baseProduct == null) {
				drawBaseProductMapper.transformToBaseProduct(drawBaseProduct);
				log.debug("新增产品{}信息", drawBaseProduct.getProductCode());

				// 厨房门、入户门、房间门、卫生间门属性处理
				// ## 子母门1、单开门2、双开门3、推拉门5
				if (doorAttr.contains(drawBaseProduct.getProductSmallTypeMark())) {
					this.handlerProAttr(drawBaseProduct);
				}
			} else {
				log.info("更新产品{}信息", drawBaseProduct.getProductCode());
				BaseProduct baseProduct2 = new BaseProduct();
				try {
					BeanUtils.copyProperties(baseProduct2, drawBaseProduct);
					baseProduct2.setId(baseProduct.getId());
					// 恢复数据
					baseProduct2.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new DrawBusinessException(false, ResponseEnum.ERROR);
				}
				baseProductMapper.updateByPrimaryKeySelective(baseProduct2);
				drawBaseProduct.setBaseProductId(baseProduct.getId());
			}
			
			// 回填resModel ->start
			List<Integer> resModelIdList = new ArrayList<Integer>();
			resModelIdList.add(Integer.valueOf(windowsU3dmodelId + ""));
			resModelService.updateBusinessId(resModelIdList, Integer.valueOf(drawBaseProduct.getBaseProductId() + ""));
			// 回填resModel ->end

			// 更新base_product_id ->start
			drawBaseProduct.setPutawayState(null);
			drawBaseProduct.setWindowsU3dmodelId(null);
			this.update(drawBaseProduct);
			// 更新base_product_id ->start

			returnMap.put(drawBaseProduct.getId(), drawBaseProduct.getBaseProductId());
		}
		
		log.info("转化base_product信息数据结束");

		return returnMap;
	}

	/**
	 * 处理产品的数据
	 *
	 * @param drawBaseProduct
	 * @return
	 */
	private Integer handlerProAttr(DrawBaseProduct drawBaseProduct) {
		ProductAttribute proAttr = productAttributeService.getProAttr(drawBaseProduct.getProAttrKey(), drawBaseProduct.getProAttrValKey());
		if (proAttr == null) {
			return -1;
		}

		proAttr.setProductCode(drawBaseProduct.getProductCode());
		proAttr.setProductId(drawBaseProduct.getBaseProductId().intValue());
		// 产品小分类（产品属性父级）
		proAttr.setAttributeTypeValue(drawBaseProduct.getProductSmallTypeMark());

		log.info("处理{}(proAttrKey), {}(proAttrValKey)的属性", drawBaseProduct.getProAttrKey(), drawBaseProduct.getProAttrValKey());

		return productAttributeService.addProAttr(proAttr);
	}

	@Override
	public List<Long> getDeleteDrawBaseProductId(List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return null;
		}
		return drawBaseProductMapper.getDeleteDrawBaseProductId(emptySpaces);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDrawBaseProduct(Integer status, List<Long> baseProducts) {
		if (baseProducts == null || baseProducts.isEmpty()) {
			return -1;
		}
		return drawBaseProductMapper.deleteDrawBaseProduct(status, baseProducts);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteBaseProduct(Integer status, List<Long> baseProducts) {
		if (baseProducts == null || baseProducts.isEmpty()) {
			return -1;
		}
		return drawBaseProductMapper.deleteBaseProduct(status, baseProducts);
	}

	@Override
	public List<Long> getDeleteBaseProductId(Long drawSpaceId) {
		if (drawSpaceId == null) {
			return null;
		}
		return drawBaseProductMapper.getDeleteBaseProductId(drawSpaceId);
	}

	@Override
	public List<Long> getEmptyBaseProduct(List<Long> emptySpaces) {
		if (emptySpaces == null || emptySpaces.isEmpty()) {
			return null;
		}
		return drawBaseProductMapper.getEmptyBaseProduct(emptySpaces);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer emptyBaseProduct(Integer status, List<Long> baseProducts) {
		if (baseProducts == null || baseProducts.isEmpty()) {
			log.warn("参数 baseProducts 为空");
			return -1;
		}
		return drawBaseProductMapper.emptyBaseProduct(status, baseProducts);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer batchUpdateDrawBaseProduct(List<DrawBaseProduct> drawBaseProducts) {
		if (drawBaseProducts == null || drawBaseProducts.isEmpty()) {
			log.warn("参数 drawBaseProducts 为空");
			return -1;
		}

		int updateCount = 0;
		List<DrawBaseProduct> updateProducts = new ArrayList<>();
		for (DrawBaseProduct drawBaseProduct : drawBaseProducts) {
			updateCount += this.handlerBatchUpdate(drawBaseProduct, updateProducts);
		}

		if (!updateProducts.isEmpty()) {
			updateCount += drawBaseProductMapper.batchUpdateDrawBaseProduct(updateProducts);
		}

		return updateCount;
	}

	@Override
	public List<BaseProduct> listProductByProductCode(List<String> productCodes) {
		if (productCodes == null || productCodes.isEmpty()) {
			return null;
		}

		return drawBaseProductMapper.listProductByProductCode(productCodes);
	}

	Integer handlerBatchUpdate(DrawBaseProduct dtp, List<DrawBaseProduct> updateProducts) {
		updateProducts.add(dtp);
		if (updateProducts.size() >= DEFAULTS_UPDATE_COUNT) {
			int updateCount = drawBaseProductMapper.batchUpdateDrawBaseProduct(updateProducts);
			updateProducts.clear();
			return updateCount;
		}
		return 0;
	}
}
