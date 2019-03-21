package com.sandu.service.v2.house.impl;

import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.api.house.model.*;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.api.product.bo.TransformProductBO;
import com.sandu.api.product.model.BaseProduct;
import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.service.ProductAttributeService;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.v2.house.bo.CallbackTransformBO;
import com.sandu.api.v2.house.bo.DrawHouseProductBO;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.service.DrawFlowGeneralService;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.common.constant.BaseProductStatusConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.attr.DoorAttr;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.product.dao.BaseProductMapper;
import com.sandu.service.product.dao.DrawBaseProductMapper;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("drawBaseProductServiceV2")
public class DrawBaseProductServiceV2Impl implements DrawFlowGeneralService {

    @Autowired
    DoorAttr doorAttr;

    @Autowired
    BaseHouseMapper baseHouseMapper;

    @Autowired
    DrawBaseProductMapper drawBaseProductMapper;

    @Autowired
    SystemDictionaryService dictionaryService;

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    DrawDesignTempletProductServiceV2Impl drawDesignTempletProductServiceV2;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    BaseProductMapper baseProductMapper;

    @Autowired
    ProductAttributeService productAttributeService;

    @Autowired
    ResModelService resModelService;

    @Override
    public <T> T handlerHouseSubmit(DrawHouseSubmitBO houseSubmitBO) {
        List<DrawHouseProductBO> drawHouseProductBOS = new ArrayList<>();
        DrawSpaceCommonDTO drawSpaceCommonDTO = houseSubmitBO.getDrawSpaceCommonDTO();
        for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
            // 验证
            this.validateProduct(drawBaseProductDTO);

            // base product表产品处理
            DrawBaseProduct drawBaseProduct = this.handlerDrawBaseProduct(drawBaseProductDTO, houseSubmitBO);

            // design templet product 处理
            DrawDesignTempletProduct drawDesignTempletProduct = drawDesignTempletProductServiceV2.handlerHouseSubmit(drawBaseProductDTO, houseSubmitBO);
            // save product
            drawHouseProductBOS.add(new DrawHouseProductBO(drawBaseProduct, drawDesignTempletProduct, drawBaseProductDTO));
        }
        // setDrawHouseProductBOS
        houseSubmitBO.getDrawSpaceBO().setDrawHouseProductBOS(drawHouseProductBOS);

        return null;
    }

    @Override
    public Integer batchSaveByHouseSubmit(List<DrawSpaceBO> drawSpaceBOS) {
        if (drawSpaceBOS == null || drawSpaceBOS.isEmpty()) {
            log.warn("参数 drawSpaceBOS 为空");
            return -1;
        }

        int updateCount = 0;

        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            if (drawSpaceBO.getDrawHouseProductBOS() == null || drawSpaceBO.getDrawHouseProductBOS().isEmpty()) {
                continue;
            }

            List<DrawBaseProduct> drawBaseProducts = new ArrayList<>();
            for (DrawHouseProductBO drawHouseProductBO : drawSpaceBO.getDrawHouseProductBOS()) {
                if (drawHouseProductBO.getDrawBaseProduct() != null) {
                    drawBaseProducts.add(drawHouseProductBO.getDrawBaseProduct());
                }
            }

            if (drawBaseProducts.isEmpty()) {
                continue;
            }

            updateCount += drawBaseProductMapper.batchSaveDrawBaseProduct(drawBaseProducts);
        }

        return updateCount;
    }

    /**
     * base product表产品处理
     *
     * @param drawBaseProductDTO
     * @param houseSubmitBO
     * @return
     */
    private DrawBaseProduct handlerDrawBaseProduct(DrawBaseProductDTO drawBaseProductDTO, DrawHouseSubmitBO houseSubmitBO) {
        // 软装部分验证，软装不用添加处理
        if (DrawBaseProductConstant.MODEL_TYPE_SOFT.intValue() == drawBaseProductDTO.getModelType().intValue()) {
            return null;
        }

        // 公共部分 识别为窗框/栏杆之类/阳台门
        if (DrawBaseProductConstant.MODEL_TYPE_PUBLIC.intValue() == drawBaseProductDTO.getModelType().intValue()) {
            if (drawBaseProductDTO.getProductFileId() == null) {
                throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的productFileId字段不能为空"));
            }

            PublicProductInfoDTO publicProductInfoDTO = new PublicProductInfoDTO(drawBaseProductDTO.getProductFileId(), drawBaseProductDTO.getUniqueId());
            // 保存public文件(窗框/栏杆/阳台门等)信息在draw_design_templet表中
            if (houseSubmitBO.getDrawSpaceBO().getPublicProductInfoDTOList() == null) {
                houseSubmitBO.getDrawSpaceBO().setPublicProductInfoDTOList(new ArrayList<>());
            }
            houseSubmitBO.getDrawSpaceBO().getPublicProductInfoDTOList().add(publicProductInfoDTO);

            return null;
        }

        // 产品大小分类验证
        if (StringUtils.isEmpty(drawBaseProductDTO.getBigTypeValueKey())) {
            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
        }
        if (StringUtils.isEmpty(drawBaseProductDTO.getSmallTypeValueKey())) {
            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
        }

        // base product
        DrawBaseProduct drawBaseProduct = this.getDrawBaseProduct(drawBaseProductDTO, houseSubmitBO);

        // 厨房门、入户门、房间门、卫生间门属性处理
        this.handlerDoorAttr(drawBaseProductDTO, drawBaseProduct);

        return drawBaseProduct;
    }

    /**
     * 厨房门、入户门、房间门、卫生间门属性处理
     *
     * @param drawBaseProductDTO
     * @param drawBaseProduct
     */
    private void handlerDoorAttr(DrawBaseProductDTO drawBaseProductDTO, DrawBaseProduct drawBaseProduct) {
        // 厨房门、入户门、房间门、卫生间门属性处理
        if (doorAttr.contains(drawBaseProduct.getProductSmallTypeMark()) && doorAttr.containsProAttrType(drawBaseProductDTO.getProAttrType())) {
            DoorAttr.DoorAttrType attrType = doorAttr.getDoorAttrType(drawBaseProduct.getProductSmallTypeMark());
            if (attrType != null) {
                drawBaseProduct.setProAttrKey(Objects.toString(attrType.getLongCode(), ""));
                String attrValType = doorAttr.getDoorAttrValueType(drawBaseProduct.getProductSmallTypeMark(), drawBaseProductDTO.getProAttrType());
                drawBaseProduct.setProAttrValKey(attrValType);
                log.debug("处理门的属性，{}(proAttrType), {}(attrType), {}(attrValType)", attrType, attrValType, drawBaseProductDTO.getProAttrType());
            }
        }
    }

    /**
     * @param drawBaseProductDTO
     * @param houseSubmitBO
     * @return
     */
    DrawBaseProduct getDrawBaseProduct(DrawBaseProductDTO drawBaseProductDTO, DrawHouseSubmitBO houseSubmitBO) {
        SystemDictionary smallTypeDict = dictionaryService.findOneByValueKey(drawBaseProductDTO.getSmallTypeValueKey());
        if (smallTypeDict == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("数据字典未找到;valuekey = " + drawBaseProductDTO.getSmallTypeValueKey()));
        }

        SystemDictionary bigTypeDict = dictionaryService.findOneByValueKey(smallTypeDict.getType());
        if (bigTypeDict == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("数据字典未找到;valuekey = " + drawBaseProductDTO.getBigTypeValueKey()));
        }

        Date now = new Date();
        DrawBaseProduct drawBaseProduct = new DrawBaseProduct();

        // product_code
        String productCode = drawBaseProductService.getProductCode(smallTypeDict.getValuekey());
        drawBaseProduct.setProductCode(productCode);
        drawBaseProduct.setProductName(this.getProductName(smallTypeDict, bigTypeDict));
        drawBaseProduct.setProductLength(drawBaseProductDTO.getProductLength());
        drawBaseProduct.setProductWidth(drawBaseProductDTO.getProductWidth());
        drawBaseProduct.setProductHeight(drawBaseProductDTO.getProductHeight());
        drawBaseProduct.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        drawBaseProduct.setGmtCreate(now);
        drawBaseProduct.setGmtModified(now);
        drawBaseProduct.setCreator(houseSubmitBO.getLoginBO().getLoginName());
        drawBaseProduct.setModifier(houseSubmitBO.getLoginBO().getLoginName());
        drawBaseProduct.setIsDeleted(DrawBaseProductConstant.DEFAULTS_IS_DELETED_);
        drawBaseProduct.setHouseTypeValues(DrawBaseProductConstant.DEFAULTS_HOUSE_TYPES);
        drawBaseProduct.setProductTypeValue(bigTypeDict.getValue() + "");
        drawBaseProduct.setProductSmallTypeValue(smallTypeDict.getValue());
        drawBaseProduct.setProductTypeMark(bigTypeDict.getValuekey());
        drawBaseProduct.setProductSmallTypeMark(smallTypeDict.getValuekey());
        drawBaseProduct.setSyncStatus("NOT_SYNCHRONIZED");
        drawBaseProduct.setFullPaveLength(drawBaseProductDTO.getProductLength());
        drawBaseProduct.setBakeBeforeFileId(drawBaseProductDTO.getProductFileId());
        drawBaseProduct.setBrandId(-1);
        drawBaseProduct.setCodeIsEmploy(1);

        // 发布状态
        drawBaseProduct.setPutawayState(getPutawayState(houseSubmitBO.getDrawHouse()));

        return drawBaseProduct;
    }

    private void validateProduct(DrawBaseProductDTO drawBaseProductDTO) {
        if (drawBaseProductDTO.getModelType() == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的modleType字段不能为空"));
        }

        if (drawBaseProductDTO.getUniqueId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的uniqueId字段不能为空"));
        }
    }

    /**
     * 产品名
     *
     * @param smallTypeDict
     * @param bigTypeDict
     * @return
     */
    private String getProductName(SystemDictionary smallTypeDict, SystemDictionary bigTypeDict) {
        return Objects.toString(bigTypeDict.getName()) + "_"
                + Objects.toString(smallTypeDict.getName()) + "_"
                + Utils.generateRandomDigitString(6);
        // return bigTypeDict.getName() + "_" + smallTypeDict.getName() + "_" + Utils.generateRandomDigitString(6);
    }

    /**
     * 获取发布状态
     *
     * @param drawHouse
     * @return
     */
    private Integer getPutawayState(DrawBaseHouse drawHouse) {
        // JIRA COREFUN-83
        // 烘培完成后该样板户型自动公开（对应的空间、样板房一起公开）
        return BaseProductStatusConstant.HAS_BEEN_RELEASE;
//        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
//            return BaseProductStatusConstant.HAS_BEEN_RELEASE;
//        }
//
//        // 检查原先是否发布，如果发布继续为发布状态
//        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
//            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
//            return (putawayState == null) ? BaseProductStatusConstant.TESTING : putawayState;
//        }
//
//        return BaseProductStatusConstant.TESTING;
    }

    @Override
    public void beforeSaveHandler(List<DrawSpaceBO> drawSpaceBOS) {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerCallbackTransform(CallbackTransformBO transformBO) {
        List<DrawBaseProduct> hardProducts = transformBO.getHardProducts();

        if (hardProducts == null || hardProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        // get transform draw products
        List<TransformProductBO> transformDrawProducts = this.getTransformDrawProducts(hardProducts);
        if (transformDrawProducts == null || transformDrawProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        // 处理产品的model资源
        this.handlerBaseProductModel(transformDrawProducts, transformBO.getBakeFiles());

        // save
        this.handlerBaseProduct(transformDrawProducts);
    }

    /**
     * 获取需要转换的产品和产品的相关的资源文件
     *
     * @param drawProducts
     * @return
     */
    private List<TransformProductBO> getTransformDrawProducts(List<DrawBaseProduct> drawProducts) {
        List<TransformProductBO> transformDrawProducts = new ArrayList<>();
        if (drawProducts == null || drawProducts.isEmpty()) {
            return transformDrawProducts;
        }

        // 产品的模型资源
        Map<Long, DrawResFile> drawModels = getDrawModelFileMaps(drawProducts);

        // 产品 productMaps
        Map<String, BaseProduct> productMaps = getProductsByCode(drawProducts);

        for (DrawBaseProduct drawProduct : drawProducts) {
            TransformProductBO transformProduct = new TransformProductBO();

            // draw res model
            Integer windowsU3dmodelId = drawProduct.getWindowsU3dmodelId();
            if (windowsU3dmodelId != null) {
                transformProduct.setDrawResFile(drawModels.get(windowsU3dmodelId.longValue()));
            }

            // base product
            BaseProduct baseProduct = productMaps.get(drawProduct.getProductCode());
            if (baseProduct == null || baseProduct.getId() == null) {
                // add product
                transformProduct.setDataType(TransformProductBO.ADD_TYPE);
            } else {
                // update product
                // base product id
                drawProduct.setBaseProductId(baseProduct.getId());
                transformProduct.setDataType(TransformProductBO.UPDATE_TYPE);
            }
            transformProduct.setDrawBaseProduct(drawProduct);

            // add list
            transformDrawProducts.add(transformProduct);
        }

        return transformDrawProducts;
    }

    /**
     * 处理产品的model资源
     *
     * @param transformProducts
     */
    private void handlerBaseProductModel(List<TransformProductBO> transformProducts, List<String> bakeFiles) {
        List<DrawResFile> drawResFiles = transformProducts.stream().filter(Objects::nonNull).map(TransformProductBO::getDrawResFile).collect(Collectors.toList());

        // add batch model file
        Map<Long, ResModel> resModelMap = drawResFileService.addBatchModelFile(drawResFiles, SystemConfigEnum.BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), true, true);

        for (TransformProductBO transformProduct : transformProducts) {
            if (transformProduct.getDrawResFile() == null) {
                continue;
            }

            ResModel resModel = resModelMap.get(transformProduct.getDrawResFile().getId());
            if (resModel != null) {
                // 烘焙文件
                bakeFiles.add(resModel.getModelPath());

                // pc端需要的文件
                transformProduct.getDrawBaseProduct().setWindowsU3dmodelId(resModel.getId().intValue());
                transformProduct.setResModel(resModel);
            }
        }
    }

    /**
     * @param transformProducts
     */
    private void handlerBaseProduct(List<TransformProductBO> transformProducts) {
        if (transformProducts == null || transformProducts.isEmpty()) {
            log.warn("参数drawProductBOS为空");
            return;
        }

        // add product
        List<DrawBaseProduct> addProducts = transformProducts.stream().filter(p -> Objects.equals(p.getDataType(), TransformProductBO.ADD_TYPE))
                .map(TransformProductBO::getDrawBaseProduct).collect(Collectors.toList());
        if (addProducts != null && !addProducts.isEmpty()) {
            baseProductMapper.addBatchBaseProduct(addProducts);
            log.debug("新增转换产品信息 => {}", addProducts);
        }

        // update product
        List<DrawBaseProduct> updateProducts = transformProducts.stream().filter(p -> Objects.equals(p.getDataType(), TransformProductBO.UPDATE_TYPE))
                .map(TransformProductBO::getDrawBaseProduct).collect(Collectors.toList());
        if (updateProducts != null && !updateProducts.isEmpty()) {
            for (DrawBaseProduct updateProduct : updateProducts) {
                baseProductMapper.updateByPrimaryKeySelective1(updateProduct);
                log.info("更新转换产品信息 => {}", updateProduct);
            }
        }

        List<DrawBaseProduct> drawProducts = transformProducts.stream().map(TransformProductBO::getDrawBaseProduct).collect(Collectors.toList());

        // 模型资源回填BusinessId
        this.handlerBaseProductResModelSave(drawProducts);

        // 厨房门、入户门、房间门、卫生间门属性处理
        // 子母门1、单开门2、双开门3、推拉门5
        this.handlerBaseProductAttrSave(drawProducts);

        // 更新draw_base_product => base_product_id 字段
        this.handlerDrawBaseProductCallback(drawProducts);
    }

    /**
     * @param drawProducts
     * @return
     */
    private Integer handlerBaseProductResModelSave(List<DrawBaseProduct> drawProducts) {
        if (drawProducts == null || drawProducts.isEmpty()) {
            log.warn("参数drawProducts为空");
            return -1;
        }

        // base_product_id = windowU3dmodelId
        return resModelService.updateBatchBusinessId(drawProducts);
    }

    /**
     * 厨房门、入户门、房间门、卫生间门属性处理
     * ## 子母门1、单开门2、双开门3、推拉门5
     *
     * @param drawProducts
     */
    private Integer handlerBaseProductAttrSave(List<DrawBaseProduct> drawProducts) {
        if (drawProducts == null || drawProducts.isEmpty()) {
            log.warn("参数drawProducts为空");
            return -1;
        }

        int updateCount = 0;

        for (DrawBaseProduct drawProduct : drawProducts) {
            if (doorAttr.contains(drawProduct.getProductSmallTypeMark())) {
                ProductAttribute proAttr = productAttributeService.getProAttr(drawProduct.getProAttrKey(), drawProduct.getProAttrValKey());
                if (proAttr == null) {
                    continue;
                }

                proAttr.setProductCode(drawProduct.getProductCode());
                proAttr.setProductId(drawProduct.getBaseProductId().intValue());
                // 产品小分类（产品属性父级）
                proAttr.setAttributeTypeValue(drawProduct.getProductSmallTypeMark());
                updateCount += productAttributeService.addProAttr(proAttr);
            }
        }

        return updateCount;
    }

    /**
     * 更新draw_base_product => base_product_id 字段
     *
     * @param drawProducts
     * @return
     */
    private Integer handlerDrawBaseProductCallback(List<DrawBaseProduct> drawProducts) {
        if (drawProducts == null || drawProducts.isEmpty()) {
            log.warn("参数drawProducts为空");
            return -1;
        }

        return drawBaseProductMapper.updateBatchDrawBaseProduct(drawProducts);
    }

    /**
     * 获取产品的model资源
     *
     * @param drawProducts
     * @return
     */
    private Map<Long, DrawResFile> getDrawModelFileMaps(List<DrawBaseProduct> drawProducts) {
        if (drawProducts == null || drawProducts.isEmpty()) {
            log.warn("参数drawProducts为空，产品没有Model资源");
            return new HashMap<>();
        }

        List<Integer> u3dOfWindows = drawProducts.stream().map(DrawBaseProduct::getWindowsU3dmodelId).collect(Collectors.toList());
        if (u3dOfWindows == null || u3dOfWindows.isEmpty()) {
            log.warn("u3dOfWindows为空，产品没有Model资源");
            return new HashMap<>();
        }

        List<DrawResFile> drawResFiles = drawResFileService.listDrawResFileById(u3dOfWindows);
        if (drawResFiles != null && !drawResFiles.isEmpty()) {
            Map<Long, DrawResFile> fileMap = drawResFiles.stream().collect(Collectors.toMap(DrawResFile::getId, f -> f, (p, n) -> n));
            return Optional.of(fileMap).orElse(new HashMap<>());
        }

        return new HashMap<>();
    }

    /**
     * 获取产品
     *
     * @param drawProducts
     * @return
     */
    private Map<String, BaseProduct> getProductsByCode(List<DrawBaseProduct> drawProducts) {
        if (drawProducts == null || drawProducts.isEmpty()) {
            log.warn("参数drawProducts为空，产品没有Model资源");
            return new HashMap<>();
        }

        List<String> productCodes = drawProducts.stream().map(DrawBaseProduct::getProductCode).collect(Collectors.toList());
        if (productCodes == null || productCodes.isEmpty()) {
            log.warn("productCodes为空，没有产品");
            return new HashMap<>();
        }

        List<BaseProduct> products = drawBaseProductService.listProductByProductCode(productCodes);
        if (products != null && !products.isEmpty()) {
            Map<String, BaseProduct> fileMap = products.stream().collect(Collectors.toMap(BaseProduct::getProductCode, f -> f, (p, n) -> n));
            return Optional.of(fileMap).orElse(new HashMap<>());
        }

        return new HashMap<>();
    }
}
