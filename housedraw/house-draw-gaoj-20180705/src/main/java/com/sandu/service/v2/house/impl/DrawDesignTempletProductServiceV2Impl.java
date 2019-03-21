package com.sandu.service.v2.house.impl;

import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.v2.house.bo.DrawHouseProductBO;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.bo.RegionMark;
import com.sandu.api.v2.house.service.DrawHouseSubmitService;
import com.sandu.api.product.model.BaseProduct;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResModelService;
import com.sandu.common.constant.RegionMarkConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.common.constant.house.DrawDesignTempletProductConstant;
import com.sandu.exception.BizException;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.product.dao.BaseProductMapper;
import com.sandu.service.templet.dao.DrawDesignTempletProductMapper;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("drawDesignTempletProductServiceV2")
public class DrawDesignTempletProductServiceV2Impl implements DrawHouseSubmitService {

    @Autowired
    ResModelService resModelService;

    @Autowired
    BaseProductMapper baseProductMapper;

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    DrawDesignTempletProductMapper drawDesignTempletProductMapper;

    @Override
    public <T> T handlerHouseSubmit(DrawHouseSubmitBO houseSubmitBO) {
        return null;
    }

    @Override
    public Integer batchSaveByHouseSubmit(List<DrawSpaceBO> drawSpaceBOS) {
        if (drawSpaceBOS == null || drawSpaceBOS.isEmpty()) {
            log.warn("参数 drawSpaceBOS 为空");
            return -1;
        }

        int updateCount = 0;
        // 拼装绑定点信息
        Map<String, List<Long>> bingInfoMap = this.getBingInfoMap(drawSpaceBOS);

        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            if (drawSpaceBO.getDrawHouseProductBOS() == null || drawSpaceBO.getDrawHouseProductBOS().isEmpty()) {
                continue;
            }

            List<DrawDesignTempletProduct> drawDesignTempletProducts = new ArrayList<>();
            for (DrawHouseProductBO drawHouseProductBO : drawSpaceBO.getDrawHouseProductBOS()) {
                if (drawHouseProductBO.getDrawDesignTempletProduct() != null) {
                    drawDesignTempletProducts.add(this.getDrawDesignTempletProduct(drawSpaceBO, bingInfoMap, drawHouseProductBO));
                }
            }

            if (drawDesignTempletProducts.isEmpty()) {
                continue;
            }

            updateCount += drawDesignTempletProductMapper.batchSaveDesignTempletProduct(drawDesignTempletProducts);
        }

        return updateCount;
    }

    /**
     * @param drawSpaceBO
     * @param drawHouseProductBO
     * @return
     */
    DrawDesignTempletProduct getDrawDesignTempletProduct(DrawSpaceBO drawSpaceBO,
                                                         Map<String, List<Long>> bingInfoMap,
                                                         DrawHouseProductBO drawHouseProductBO) {
        if (drawHouseProductBO.getDrawBaseProduct() != null) {
            if (drawHouseProductBO.getDrawBaseProduct().getId() == null) {
                throw new BizException("保存产品信息失败，请重试！");
            }

            // product id
            if (drawHouseProductBO.getDrawDesignTempletProduct().getProductId() == null
                    || drawHouseProductBO.getDrawDesignTempletProduct().getProductId() <= 0) {
                drawHouseProductBO.getDrawDesignTempletProduct().setProductId(drawHouseProductBO.getDrawBaseProduct().getId().intValue());
            }

            // 绑定产品处理
            String bindInfoMapStr = Utils.listToStr(bingInfoMap.get(drawHouseProductBO.getDrawBaseProductDTO().getUniqueId()));
            drawHouseProductBO.getDrawDesignTempletProduct().setBindParentProductid(bindInfoMapStr);
        }

        // design templet id
        if (drawSpaceBO.getDrawDesignTemplet().getId() == null) {
            throw new BizException("保存样板房信息失败，请重试！");
        }
        drawHouseProductBO.getDrawDesignTempletProduct().setDesignTempletId(drawSpaceBO.getDrawDesignTemplet().getId().intValue());

        return drawHouseProductBO.getDrawDesignTempletProduct();
    }


    /**
     * 样板房产品处理
     *
     * @param drawBaseProductDTO
     * @param houseSubmitBO
     * @return
     */
    public DrawDesignTempletProduct handlerHouseSubmit(DrawBaseProductDTO drawBaseProductDTO,
                                                       DrawHouseSubmitBO houseSubmitBO) {
        // public文件不保存在样板房产品表
        if (DrawBaseProductConstant.MODEL_TYPE_PUBLIC.intValue() == drawBaseProductDTO.getModelType().intValue()) {
            log.debug("跳过不处理的公共部分{}(productId)", drawBaseProductDTO.getUniqueId());
            return null;
        }

        // getDrawDesignTempletProduct
        DrawDesignTempletProduct drawDesignTempletProduct = this.getDrawDesignTempletProduct(drawBaseProductDTO, houseSubmitBO.getLoginBO());

        // 组合产品处理
        this.handlerGroupProduct(drawDesignTempletProduct, drawBaseProductDTO, houseSubmitBO);

        // 地面、天花区域标识处理
        this.handlerRegionMark(drawDesignTempletProduct, drawBaseProductDTO, houseSubmitBO);

        // 处理软装白膜产品
        this.handlerSoftProduct(drawDesignTempletProduct, drawBaseProductDTO);

        // 处理拉伸过的软装白膜产品
        // copy一条被拉伸的白膜产品，fullPaveLength = 被拉伸的长度
        this.handlerChangedSoftProduct(drawDesignTempletProduct, drawBaseProductDTO);

        return drawDesignTempletProduct;
    }

    /**
     * 处理软装白膜产品.
     *
     * @param drawBaseProductDTO
     * @param drawDesignTempletProduct
     */
    private void handlerSoftProduct(DrawDesignTempletProduct drawDesignTempletProduct, DrawBaseProductDTO drawBaseProductDTO) {
        /**
         * 0 软装，1、非软装
         */
        if (DrawBaseProductConstant.MODEL_TYPE_SOFT.intValue() == drawBaseProductDTO.getModelType().intValue()) {
            drawDesignTempletProduct.setCreateProductStatus(DrawDesignTempletProductConstant.SOFT_PRODUCT);
        } else {
            drawDesignTempletProduct.setCreateProductStatus(DrawDesignTempletProductConstant.NON_SOFT_PRODUCT);
        }
    }

    /**
     * @param drawBaseProduct
     * @param userLoginBO
     * @return
     */
    private DrawDesignTempletProduct getDrawDesignTempletProduct(DrawBaseProductDTO drawBaseProduct,
                                                                 UserLoginBO userLoginBO) {
        Date now = new Date();
        DrawDesignTempletProduct drawDesignTempletProduct = new DrawDesignTempletProduct();
        drawDesignTempletProduct.setGmtCreate(now);
        drawDesignTempletProduct.setGmtModified(now);
        drawDesignTempletProduct.setCreator(userLoginBO.getLoginName());
        drawDesignTempletProduct.setModifier(userLoginBO.getLoginName());
        drawDesignTempletProduct.setIsDeleted(DrawDesignTempletProductConstant.DEFAULTS_IS_DELETED);
        drawDesignTempletProduct.setUniqueId(drawBaseProduct.getUniqueId());
        drawDesignTempletProduct.setWallOrientation(drawBaseProduct.getWallOrientation());
        drawDesignTempletProduct.setWallType(drawBaseProduct.getWallType());
        drawDesignTempletProduct.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        // 软装产品ID, 硬装部分在保存之前处理
        drawDesignTempletProduct.setProductId(drawBaseProduct.getProductId() != null ? drawBaseProduct.getProductId().intValue() : null);

        return drawDesignTempletProduct;
    }

    /**
     * 组合产品处理，
     * 通用app需要这样处理,用来区分多个相同的组合产品;plan_group_id.
     * 处理组合的产品，相同的组合产品的planGroupId = groupProductId_后缀一样
     *
     * @param drawDesignTempletProduct
     * @param drawBaseProductDTO
     * @param houseSubmitBO
     */
    private void handlerGroupProduct(DrawDesignTempletProduct drawDesignTempletProduct,
                                     DrawBaseProductDTO drawBaseProductDTO,
                                     DrawHouseSubmitBO houseSubmitBO) {
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
            drawDesignTempletProduct.setPlanGroupId(drawBaseProductDTO.getGroupProductId() + "_"
                    + getGroupIndex(houseSubmitBO.getIdxMap(), drawBaseProductDTO.getGroupUniqueId()));
        } else {
            // defaults value
            drawDesignTempletProduct.setProductGroupId(DrawDesignTempletProductConstant.DEFAULTS_GROUP_PRODUCT_ID);
            drawDesignTempletProduct.setIsMainProduct(DrawDesignTempletProductConstant.DEFAULTS_IS_MAIN_PRODUCT);
            drawDesignTempletProduct.setGroupType(DrawDesignTempletProductConstant.GROUP_PRODUCT_TYPE);
            drawDesignTempletProduct.setPlanGroupId(DrawDesignTempletProductConstant.DEFAULTS_PLAN_GROUP_ID.toString());
        }
    }

    /**
     * 地面、天花区域标识处理.
     * 相同的类型以基数累加.
     * eg: 同一个空间里有5块地面以基数10开始累加，regionMark ==> 10 11...15;
     * 不同类型基数不一样{@link RegionMarkConstant}
     *
     * @param drawDesignTempletProduct
     * @param drawBaseProductDTO
     */
    private void handlerRegionMark(DrawDesignTempletProduct drawDesignTempletProduct,
                                   DrawBaseProductDTO drawBaseProductDTO,
                                   DrawHouseSubmitBO houseSubmitBO) {

        if (RegionMarkConstant.contains(drawBaseProductDTO.getSmallTypeValueKey())) {
            // 第一次时，初始化区域标识
            if (houseSubmitBO.getDrawSpaceBO().getRegionMarks() == null
                    || houseSubmitBO.getDrawSpaceBO().getRegionMarks().isEmpty()) {
                houseSubmitBO.getDrawSpaceBO().setRegionMarks(this.getInitRegionMarks());
            }

            // get region mark
            int regionMark = getRegionMark(houseSubmitBO.getDrawSpaceBO().getRegionMarks(),
                    drawBaseProductDTO.getSmallTypeValueKey());
            if (regionMark > 0) {
                drawDesignTempletProduct.setRegionMark(Objects.toString(regionMark, Utils._ONE_VALUE));
            }

            log.debug("地面、天花区域标识处理, regionMark ==> {}", regionMark);
        }
    }

    /**
     * 获取初始化区域标识
     *
     * @return
     */
    private List<RegionMark> getInitRegionMarks() {
        List<RegionMark> regions = new ArrayList<>();
        RegionMarkConstant[] regionMarks = RegionMarkConstant.values();
        for (RegionMarkConstant regionMark : regionMarks) {
            regions.add(new RegionMark(regionMark));
        }
        return regions;
    }

    /**
     * 地面、天花区域标识处理.
     *
     * <pre>
     * 相同的类型以基数累加.
     * 	eg: 同一个空间里有5块地面以基数10开始累加，regionMark ==> 10 11...15;
     * 不同类型基数不一样{@link RegionMarkConstant}
     * </pre>
     *
     * @param regions
     * @param valueKey
     * @return -1 标识不是地面类型处理
     */
    private int getRegionMark(List<RegionMark> regions, String valueKey) {
        // 地面区域标识
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
     * 处理拉伸过的软装白膜产品.
     * </p>
     * copy一条拉被伸的白膜产品，fullPaveLength = 被拉伸的长度
     *
     * @param drawDesignTempletProduct
     * @param drawBaseProductDTO
     */
    private void handlerChangedSoftProduct(DrawDesignTempletProduct drawDesignTempletProduct, DrawBaseProductDTO drawBaseProductDTO) {
        if (drawBaseProductDTO == null) {
            log.warn("参数[baseProduct]为空！");
            return;
        }

        // 非软装白膜产品不处理.
        if (DrawBaseProductConstant.MODEL_TYPE_SOFT.intValue() != drawBaseProductDTO.getModelType().intValue()) {
            return;
        }

        // 是否拉伸过 == !1
        if (!DrawBaseProductConstant.IS_CHANGED_SOFT.equals(drawBaseProductDTO.getIsChanged())) {
            log.debug("白膜产品 {}(productId)没有拉伸，不处理", drawBaseProductDTO.getProductId());
            return;
        }

        log.info("处理拉伸的软装白膜产品, {}(productId)", drawBaseProductDTO.getProductId());
        BaseProduct newProduct = baseProductMapper.selectByPrimaryKey(drawBaseProductDTO.getProductId());

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
        newProduct.setFullPaveLength(drawBaseProductDTO.getProductLength());
        // u3d model 文件
        newProduct.setWindowsU3dmodelId(windowsU3dmodelId.intValue());
        // product_code
        String productCode = drawBaseProductService.getProductCode(drawBaseProductDTO.getSmallTypeValueKey());
        newProduct.setProductCode(productCode);

        // save
        baseProductMapper.insertSelective(newProduct);
        drawDesignTempletProduct.setProductId(newProduct.getId().intValue());
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

    @Override
    public void beforeSaveHandler(List<DrawSpaceBO> drawSpaceBOS) {
    }

    /**
     * 拼装绑定点信息
     * Map<String, List<String>> bingInfoMap
     * key:主墙uniqueId
     * value:背景墙draw_base_product -> id
     *
     * @param drawSpaceBOS
     * @return
     */
    private Map<String, List<Long>> getBingInfoMap(List<DrawSpaceBO> drawSpaceBOS) {
        // 先整理出一份uniqueId对应drawBaseProductId的map
        Map<String, Long> uniqueIdToDrawBaseProductIdMap = new HashMap<>();
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            for (DrawHouseProductBO drawHouseProductBO : drawSpaceBO.getDrawHouseProductBOS()) {
                if (StringUtils.isEmpty(drawHouseProductBO.getDrawBaseProductDTO().getUniqueId())) {
                    throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
                }
                uniqueIdToDrawBaseProductIdMap.put(drawHouseProductBO.getDrawBaseProductDTO().getUniqueId(),
                        drawHouseProductBO.getDrawBaseProduct() != null ? drawHouseProductBO.getDrawBaseProduct().getId() : null);
            }
        }

        Map<String, List<Long>> bingInfoMap = new HashMap<>();
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            for (DrawHouseProductBO drawHouseProductBO : drawSpaceBO.getDrawHouseProductBOS()) {
                String bindUniqueId = drawHouseProductBO.getDrawBaseProductDTO().getBindUniqueId();
                String uniqueId = drawHouseProductBO.getDrawBaseProductDTO().getUniqueId();
                if (StringUtils.isEmpty(bindUniqueId)) {
                    continue;
                }
                if (StringUtils.isEmpty(uniqueId)) {
                    throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
                }
                if (!uniqueIdToDrawBaseProductIdMap.containsKey(uniqueId)) {
                    log.error("cannot find value by key:{}; map:{}", bindUniqueId, uniqueIdToDrawBaseProductIdMap.toString());
                }

                List<Long> drawProductIdList;
                if (bingInfoMap.containsKey(bindUniqueId) && (drawProductIdList = bingInfoMap.get(bindUniqueId)) != null) {
                    drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
                } else {
                    drawProductIdList = new ArrayList<>();
                    drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
                    bingInfoMap.put(bindUniqueId, drawProductIdList);
                }
            }
        }

        return bingInfoMap;
    }
}
