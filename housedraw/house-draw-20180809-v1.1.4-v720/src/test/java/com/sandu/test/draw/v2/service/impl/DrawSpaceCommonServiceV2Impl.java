//package com.sandu.test.draw.v2.service.impl;
//
//import com.sandu.api.house.input.DrawSpaceCommonDTO;
//import com.sandu.api.house.model.DrawBaseHouse;
//import com.sandu.api.house.model.DrawSpaceCommon;
//import com.sandu.api.house.model.SystemDictionary;
//import com.sandu.api.house.service.SystemDictionaryService;
//import com.sandu.common.constant.DesignTempletStatusConstant;
//import com.sandu.common.constant.SpaceCommonStatusConstant;
//import com.sandu.common.constant.house.DrawBaseHouseConstant;
//import com.sandu.common.constant.house.DrawSpaceCommonConstant;
//import com.sandu.service.house.dao.BaseHouseMapper;
//import com.sandu.service.space.dao.DrawSpaceCommonMapper;
//import com.sandu.api.v2.house.submit.bo.DrawHouseSubmitBO;
//import com.sandu.api.v2.house.submit.bo.DrawSpaceBO;
//import com.sandu.test.draw.v2.service.DrawHouseSubmitService;
//import com.sandu.util.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//@Slf4j
//@Service("drawSpaceCommonServiceV2")
//public class DrawSpaceCommonServiceV2Impl implements DrawHouseSubmitService {
//
//
//    final static String DEFAULTS_SPACE_NAME = "其它";
//
//    @Autowired
//    BaseHouseMapper baseHouseMapper;
//
//    @Autowired
//    SystemDictionaryService dictionaryService;
//
//    @Autowired
//    DrawSpaceCommonMapper drawSpaceCommonMapper;
//
//    /**
//     * 绘制空间处理
//     *
//     * @param houseSubmitBO
//     * @return
//     */
//    @Override
//    public <T> T handlerHouseSubmit(DrawHouseSubmitBO houseSubmitBO) {
//        DrawSpaceCommonDTO drawSpaceCommonDTO = houseSubmitBO.getDrawSpaceCommonDTO();
//        // draw space common
//        DrawSpaceCommon drawSpaceCommon = this.getDrawSpaceCommon(drawSpaceCommonDTO, houseSubmitBO);
//        houseSubmitBO.getDrawSpaceBO().setDrawSpaceCommon(drawSpaceCommon);
//
//        // 类型、面积、空间名、状态处理
//        this.handlerDrawSpace(drawSpaceCommonDTO, houseSubmitBO.getDrawSpaceBO());
//
//        return (T) drawSpaceCommon;
//    }
//
//    @Override
//    @Transactional
//    public Integer batchSaveByHouseSubmit(List<DrawSpaceBO> drawSpaceBOS) {
//        if (drawSpaceBOS == null || drawSpaceBOS.isEmpty()) {
//            log.warn("参数 drawSpaceBOS 为空");
//            return -1;
//        }
//
//        List<DrawSpaceCommon> drawSpaceCommons = new ArrayList<>();
//        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
//            drawSpaceCommons.add(drawSpaceBO.getDrawSpaceCommon());
//        }
//
//        if (drawSpaceCommons.isEmpty()) {
//            return -1;
//        }
//
//        return drawSpaceCommonMapper.batchSaveDrawSpaceCommon(drawSpaceCommons);
//    }
//
//
//    /**
//     * @param drawSpaceCommonDTO
//     * @param houseSubmitBO
//     * @return
//     */
//    public DrawSpaceCommon getDrawSpaceCommon(DrawSpaceCommonDTO drawSpaceCommonDTO, DrawHouseSubmitBO houseSubmitBO) {
//        DrawSpaceCommon drawSpaceCommon = new DrawSpaceCommon();
//        drawSpaceCommon.setSysCode(Utils.getSysCode(6));
//        drawSpaceCommon.setMainLength(drawSpaceCommonDTO.getSpaceCommonMainLength());
//        drawSpaceCommon.setMainWidth(drawSpaceCommonDTO.getSpaceCommonMainWidth());
//        drawSpaceCommon.setSpaceShape(DrawSpaceCommonConstant.DEFAULTS_SHAPE_RECTANGLE + "");
//        // 空间图片:前端暂时无法获取
//        drawSpaceCommon.setPicId(drawSpaceCommonDTO.getSpacePicId());
//
//        // 未知属性
//        drawSpaceCommon.setSpaceNum(1);
//        Date now = new Date();
//        drawSpaceCommon.setGmtCreate(now);
//        drawSpaceCommon.setGmtModified(now);
//        drawSpaceCommon.setCreator(houseSubmitBO.getLoginBO().getLoginName());
//        drawSpaceCommon.setModifier(houseSubmitBO.getLoginBO().getLoginName());
//        drawSpaceCommon.setIsDeleted(DrawSpaceCommonConstant.DEFAULTS_IS_DELETED);
//        drawSpaceCommon.setIsStandardSpace(1);
//        drawSpaceCommon.setSyncStatus("NOT_SYNCHRONIZED");
//        drawSpaceCommon.setOpenStatus(2);
//        drawSpaceCommon.setBakeBeforeFileId(drawSpaceCommonDTO.getSpaceCommonFileId());
//        drawSpaceCommon.setDrawBaseHouseId(houseSubmitBO.getDrawHouse().getId());
//
//        // 0、平台；1、个人
//        drawSpaceCommon.setDataType(houseSubmitBO.getDrawHouse().getDataType());
//        // 发布状态
//        drawSpaceCommon.setStatus(getPutawayState(houseSubmitBO.getDrawHouse()));
//
//        return drawSpaceCommon;
//    }
//
//    /**
//     * 类型、面积、空间名、状态处理
//     *
//     * @param drawSpaceCommonDTO
//     * @param drawSpaceBO
//     */
//    public void handlerDrawSpace(DrawSpaceCommonDTO drawSpaceCommonDTO, DrawSpaceBO drawSpaceBO) {
//        // 类型/面积
//        SystemDictionary dict = null;
//        if (!StringUtils.isEmpty(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey())) {
//            dict = dictionaryService.findOneByValueKey(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey());
//        }
//
//        SystemDictionary area = null;
//        if (!StringUtils.isEmpty(drawSpaceCommonDTO.getSpaceCommonArea()) && dict != null) {
//            area = dictionaryService.findOneByTypeAndArea(dict.getValuekey(), Double.valueOf(drawSpaceCommonDTO.getSpaceCommonArea()));
//        }
//
//        // 空间类型
//        drawSpaceBO.getDrawSpaceCommon().setSpaceFunctionId(dict == null ? null : dict.getValue());
//        // 空间编码
//        drawSpaceBO.getDrawSpaceCommon().setSpaceCode(this.getSpaceCode(area, Utils.generateRandomDigitString(6)));
//        // 空间名
//        drawSpaceBO.getDrawSpaceCommon().setSpaceName(this.getSpaceName(dict, area, drawSpaceCommonDTO.getSpaceCommonArea()));
//        // 空间面积
//        String nullDefault = Utils.getDecimalFormat(new Double(drawSpaceCommonDTO.getSpaceCommonArea()));
//        drawSpaceBO.getDrawSpaceCommon().setSpaceAreas(area == null ? nullDefault : Objects.toString(area.getValue(), nullDefault));
//    }
//
//    /**
//     * get space code
//     *
//     * @param dict
//     * @param randomNumStr
//     * @return
//     */
//    private String getSpaceCode(SystemDictionary dict, String randomNumStr) {
//        return (dict == null ? "unknown" : dict.getAtt6()) + "_" + System.nanoTime() + "_" + randomNumStr;
//    }
//
//    /**
//     * get space name
//     *
//     * @param dict
//     * @param area
//     * @param nullDefault
//     * @return
//     */
//    private String getSpaceName(SystemDictionary dict, SystemDictionary area, String nullDefault) {
//        nullDefault = Utils.getDecimalFormat(new Double(nullDefault));
//        String spaceArea = Objects.toString(area == null ? nullDefault : area.getValue(), nullDefault);
//        return (dict == null ? DEFAULTS_SPACE_NAME : Objects.toString(dict.getName(),
//                DEFAULTS_SPACE_NAME)) + spaceArea + "平";
//    }
//
//    /**
//     * 空间状态处理
//     *
//     * @param drawHouse
//     * @return
//     */
//    private Integer getPutawayState(DrawBaseHouse drawHouse) {
//        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
//            return SpaceCommonStatusConstant.HAS_BEEN_RELEASE;
//        }
//
//        // 检查原先是否发布，如果发布继续为发布状态
//        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
//            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
//            return DesignTempletStatusConstant.HAS_BEEN_RELEASE.equals(putawayState) ? SpaceCommonStatusConstant.HAS_BEEN_RELEASE : SpaceCommonStatusConstant.TESTING;
//            // return (putawayState == null) ? SpaceCommonStatusConstant.TESTING : putawayState;
//        }
//
//        return SpaceCommonStatusConstant.TESTING;
//    }
//
//    @Override
//    public void beforeSaveHandler(List<DrawSpaceBO> drawSpaceBOS) {
//    }
//}
//
