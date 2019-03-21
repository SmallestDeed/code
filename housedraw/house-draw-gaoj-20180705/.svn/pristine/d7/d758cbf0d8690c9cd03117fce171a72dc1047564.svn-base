package com.sandu.service.v2.house.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawSpaceCommon;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.service.DrawHouseSubmitService;
import com.sandu.common.constant.DesignTempletStatusConstant;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SpaceLayoutType;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawDesignTempletConstant;
import com.sandu.common.constant.house.HouseType;
import com.sandu.exception.BizException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.templet.dao.DrawDesignTempletMapper;
import com.sandu.util.Regex;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("drawDesignTempletServiceV2")
public class DrawDesignTempletServiceV2Impl implements DrawHouseSubmitService {

    @Autowired
    BaseHouseMapper baseHouseMapper;

    @Autowired
    DrawDesignTempletMapper drawDesignTempletMapper;

    @Override
    public <T> T handlerHouseSubmit(DrawHouseSubmitBO houseSubmitBO) {
        DrawSpaceCommon drawSpaceCommon = houseSubmitBO.getDrawSpaceCommon();
        DrawSpaceCommonDTO drawSpaceCommonDTO = houseSubmitBO.getDrawSpaceCommonDTO();

        DrawDesignTemplet drawDesignTemplet = this.getDrawDesignTemplet(drawSpaceCommon, drawSpaceCommonDTO, houseSubmitBO);
        // setDrawDesignTemplet
        houseSubmitBO.getDrawSpaceBO().setDrawDesignTemplet(drawDesignTemplet);

        // 处理卫生间 space layout type 值
        this.handlerSpaceLayoutType(drawSpaceCommonDTO, drawDesignTemplet);

        return (T) drawDesignTemplet;
    }

    @Override
    public Integer batchSaveByHouseSubmit(List<DrawSpaceBO> drawSpaceBOS) {
        if (drawSpaceBOS == null || drawSpaceBOS.isEmpty()) {
            log.warn("参数 drawSpaceBOS 为空");
            return -1;
        }

        // before handler
        this.beforeSaveHandler(drawSpaceBOS);

        List<DrawDesignTemplet> drawDesignTemplets = new ArrayList<>();
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            drawDesignTemplets.add(drawSpaceBO.getDrawDesignTemplet());
        }

        if (drawDesignTemplets.isEmpty()) {
            log.warn("drawDesignTemplets 是空");
            return -1;
        }

        return drawDesignTempletMapper.batchSaveDrawDesignTemplet(drawDesignTemplets);
    }

    /**
     * get draw design templet
     *
     * @param drawSpaceCommon
     * @param drawSpaceCommonDTO
     * @param houseSubmitBO
     * @return
     */
    private DrawDesignTemplet getDrawDesignTemplet(DrawSpaceCommon drawSpaceCommon,
                                                   DrawSpaceCommonDTO drawSpaceCommonDTO,
                                                   DrawHouseSubmitBO houseSubmitBO) {
        DrawDesignTemplet drawDesignTemplet = new DrawDesignTemplet();
        Date now = new Date();
        drawDesignTemplet.setSysCode(Utils.getSysCode(6));
        drawDesignTemplet.setDesignCode(getDesignCode(drawSpaceCommon.getSpaceCode()));
        drawDesignTemplet.setDesignName(drawSpaceCommon.getSpaceName() + "户型绘制样板房");
        drawDesignTemplet.setIsRecommend(1);
        drawDesignTemplet.setGmtCreate(now);
        drawDesignTemplet.setGmtModified(now);
        drawDesignTemplet.setCreator(houseSubmitBO.getLoginBO().getLoginName());
        drawDesignTemplet.setModifier(houseSubmitBO.getLoginBO().getLoginName());
        drawDesignTemplet.setUserId(Integer.valueOf(houseSubmitBO.getLoginBO().getId() + ""));
        drawDesignTemplet.setIsDeleted(DrawDesignTempletConstant.DESIGNTEMPLET_ISDELETE_DEFAULT);
        drawDesignTemplet.setSyncStatus("NOT_SYNCHRONIZED");
        // 样板房图
        drawDesignTemplet.setPicId(drawSpaceCommon.getPicId());
        drawDesignTemplet.setEffectPic(Objects.toString(drawSpaceCommon.getPicId(), ""));
        drawDesignTemplet.setEffectPlanIds(Objects.toString(drawSpaceCommon.getPicId(), ""));
        // 是否有天花区域；0、否(defaults)；1、是
        drawDesignTemplet.setIsRegionalCeiling(drawSpaceCommonDTO.getIsRegionalCeiling() == null
                ? DrawDesignTempletConstant.DEFAULTS_REGIONAL_CEILING : drawSpaceCommonDTO.getIsRegionalCeiling());

        // 0、平台数据；1、个人数据
        int dataType = Objects.isNull(houseSubmitBO.getDrawHouse().getDataType())
                ? DrawBaseHouseConstant.DATA_PLATFORM : houseSubmitBO.getDrawHouse().getDataType();
        drawDesignTemplet.setDataType(dataType);

        // 获取发布状态
        drawDesignTemplet.setPutawayState(getPutawayState(houseSubmitBO.getDrawHouse()));

        return drawDesignTemplet;
    }

    /**
     * 处理卫生间 space layout type 值
     *
     * @param drawSpaceCommonDTO
     * @param drawDesignTemplet
     */
    private void handlerSpaceLayoutType(DrawSpaceCommonDTO drawSpaceCommonDTO, DrawDesignTemplet drawDesignTemplet) {
        // 卫生间处理 space_layout_type
        if (HouseType.TOILET.toString().equalsIgnoreCase(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey())) {
            String spaceLayoutType = getSpaceLayoutType(drawSpaceCommonDTO.getDrawBaseProductDTOList());
            drawDesignTemplet.setSpaceLayoutType(spaceLayoutType);
            log.info("卫生间 spaceLayoutType 处理 ==> {}", spaceLayoutType);
        }
    }

    /**
     * 处理卫生间 space layout type 值
     * </p>
     * {@link SpaceLayoutType}
     * </p>
     *
     * @param dtp
     * @return (A, B, AB, N)
     */
    private String getSpaceLayoutType(List<DrawBaseProductDTO> dtp) {
        if (dtp == null || dtp.isEmpty()) {
            return SpaceLayoutType.N.toString();
        }

        Set<String> set = new HashSet<>(3);
        for (DrawBaseProductDTO p : dtp) {
            // A => basic_edba, basic_baba
            // B => basic_ahba, basic_dzas, basic_asba
            // AB => A + B 都含有
            // N => 啥都没有
            SpaceLayoutType layoutType = SpaceLayoutType.contains(p.getSmallTypeValueKey());
            set.add(layoutType.toString());
        }

        if (set.isEmpty()) {
            return SpaceLayoutType.N.toString();
        }

        String layout = set.stream().collect(Collectors.joining());
        if (layout.length() >= 2 && layout.contains(SpaceLayoutType.N.toString())) {
            layout = layout.replace(SpaceLayoutType.N.toString(), "");
        }

        return layout.matches(Regex.SPACE_LAYOUT_B_TYPE.getValue()) ? StringUtils.reverse(layout) : layout;
    }

    private String getDesignCode(String arg) {
        if (arg == null) {
            return "unknown_" + Utils.random();
        }

        String[] split = arg.split("_");
        if (split.length > 0) {
            split[split.length - 1] = Utils.generateRandomDigitString(6);
            return Arrays.stream(split).collect(Collectors.joining("_"));
        }

        return "unknown_" + Utils.random();
    }

    private Integer getPutawayState(DrawBaseHouse drawHouse) {
        // JIRA COREFUN-83
        // 烘培完成后该样板户型自动公开（对应的空间、样板房一起公开）
        return DesignTempletStatusConstant.HAS_BEEN_RELEASE;
//        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
//            return DesignTempletStatusConstant.HAS_BEEN_RELEASE;
//        }
//
//        // 检查原先是否发布，如果发布继续为发布状态
//        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
//            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
//            return DesignTempletStatusConstant.HAS_BEEN_RELEASE.equals(putawayState) ? DesignTempletStatusConstant.HAS_BEEN_RELEASE : DesignTempletStatusConstant.TESTING;
//            // return (putawayState == null) ? DesignTempletStatusConstant.TESTING : putawayState;
//        }
//
//        return DesignTempletStatusConstant.TESTING;
    }

    @Override
    public void beforeSaveHandler(List<DrawSpaceBO> drawSpaceBOS) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            try {
                // 公共部分（栏杆、阳台门等）
                if (drawSpaceBO.getPublicProductInfoDTOList() != null && !drawSpaceBO.getPublicProductInfoDTOList().isEmpty()) {
                    String publicProductInfo = objectMapper.writeValueAsString(drawSpaceBO.getPublicProductInfoDTOList());
                    drawSpaceBO.getDrawDesignTemplet().setPublicProductInfo(publicProductInfo);
                }
            } catch (JsonProcessingException e) {
                throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED, e);
            }

            // space common id
            if (drawSpaceBO.getDrawSpaceCommon().getId() == null
                    || drawSpaceBO.getDrawSpaceCommon().getId() <= 0) {
                throw new BizException("保存空间信息失败");
            }
            drawSpaceBO.getDrawDesignTemplet().setSpaceCommonId(drawSpaceBO.getDrawSpaceCommon().getId().intValue());
        }
    }
}
