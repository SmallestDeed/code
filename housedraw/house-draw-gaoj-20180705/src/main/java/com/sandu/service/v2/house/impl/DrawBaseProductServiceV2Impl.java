package com.sandu.service.v2.house.impl;

import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.house.model.SystemDictionary;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.api.v2.house.bo.DrawHouseProductBO;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.service.DrawHouseSubmitService;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.common.constant.BaseProductStatusConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.attr.DoorAttr;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.product.dao.DrawBaseProductMapper;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service("drawBaseProductServiceV2")
public class DrawBaseProductServiceV2Impl implements DrawHouseSubmitService {

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
}
