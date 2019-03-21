package com.sandu.service.v2.house.impl;

import com.google.common.collect.Lists;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.model.*;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.house.service.DrawSpaceCommonService;
import com.sandu.api.house.service.HouseSpaceNewService;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.v2.house.bo.CallbackTransformBO;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.service.DrawFlowGeneralService;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SpaceCommonStatusConstant;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawSpaceCommonConstant;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.space.dao.DrawSpaceCommonMapper;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("drawSpaceCommonServiceV2")
public class DrawSpaceCommonServiceV2Impl implements DrawFlowGeneralService {


    final static String DEFAULTS_SPACE_NAME = "其它";

    @Autowired
    BaseHouseMapper baseHouseMapper;

    @Autowired
    SystemDictionaryService dictionaryService;

    @Autowired
    DrawSpaceCommonMapper drawSpaceCommonMapper;

    @Autowired
    DrawSpaceCommonService drawSpaceCommonService;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    ResModelService resModelService;

    @Autowired
    HouseSpaceNewService houseSpaceNewService;

    /**
     * 绘制空间处理
     *
     * @param houseSubmitBO
     * @return
     */
    @Override
    public <T> T handlerHouseSubmit(DrawHouseSubmitBO houseSubmitBO) {
        DrawSpaceCommonDTO drawSpaceCommonDTO = houseSubmitBO.getDrawSpaceCommonDTO();
        // draw space common
        DrawSpaceCommon drawSpaceCommon = this.getDrawSpaceCommon(drawSpaceCommonDTO, houseSubmitBO);
        houseSubmitBO.getDrawSpaceBO().setDrawSpaceCommon(drawSpaceCommon);

        // 类型、面积、空间名、状态处理
        this.handlerDrawSpace(drawSpaceCommonDTO, houseSubmitBO.getDrawSpaceBO());

        return (T) drawSpaceCommon;
    }

    @Override
    @Transactional
    public Integer batchSaveByHouseSubmit(List<DrawSpaceBO> drawSpaceBOS) {
        if (drawSpaceBOS == null || drawSpaceBOS.isEmpty()) {
            log.warn("参数 drawSpaceBOS 为空");
            return -1;
        }

        List<DrawSpaceCommon> drawSpaceCommons = new ArrayList<>();
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            drawSpaceCommons.add(drawSpaceBO.getDrawSpaceCommon());
        }

        if (drawSpaceCommons.isEmpty()) {
            return -1;
        }

        return drawSpaceCommonMapper.batchSaveDrawSpaceCommon(drawSpaceCommons);
    }


    /**
     * @param drawSpaceCommonDTO
     * @param houseSubmitBO
     * @return
     */
    public DrawSpaceCommon getDrawSpaceCommon(DrawSpaceCommonDTO drawSpaceCommonDTO, DrawHouseSubmitBO houseSubmitBO) {
        // v1.1.1 添加图片、文件验证
        // Utils.requireGreaterZero(drawSpaceCommonDTO.getSpacePicId(), "空间截图不能为空");
        // Utils.requireGreaterZero(drawSpaceCommonDTO.getSpaceCommonFileId(), "空间文件不能为空");

        DrawSpaceCommon drawSpaceCommon = new DrawSpaceCommon();
        drawSpaceCommon.setSysCode(Utils.getSysCode(6));
        drawSpaceCommon.setMainLength(drawSpaceCommonDTO.getSpaceCommonMainLength());
        drawSpaceCommon.setMainWidth(drawSpaceCommonDTO.getSpaceCommonMainWidth());
        drawSpaceCommon.setSpaceShape(DrawSpaceCommonConstant.DEFAULTS_SHAPE_RECTANGLE + "");
        // 空间图片:前端暂时无法获取
        drawSpaceCommon.setPicId(drawSpaceCommonDTO.getSpacePicId());

        // 未知属性
        drawSpaceCommon.setSpaceNum(1);
        Date now = new Date();
        drawSpaceCommon.setGmtCreate(now);
        drawSpaceCommon.setGmtModified(now);
        drawSpaceCommon.setCreator(houseSubmitBO.getLoginBO().getLoginName());
        drawSpaceCommon.setModifier(houseSubmitBO.getLoginBO().getLoginName());
        drawSpaceCommon.setIsDeleted(DrawSpaceCommonConstant.DEFAULTS_IS_DELETED);
        drawSpaceCommon.setIsStandardSpace(1);
        drawSpaceCommon.setSyncStatus("NOT_SYNCHRONIZED");
        drawSpaceCommon.setOpenStatus(2);
        drawSpaceCommon.setBakeBeforeFileId(drawSpaceCommonDTO.getSpaceCommonFileId());
        drawSpaceCommon.setDrawBaseHouseId(houseSubmitBO.getDrawHouse().getId());

        // 0、平台；1、个人
        int dataType = Objects.isNull(houseSubmitBO.getDrawHouse().getDataType())
                ? DrawBaseHouseConstant.DATA_PLATFORM : houseSubmitBO.getDrawHouse().getDataType();
        drawSpaceCommon.setDataType(dataType);
        // 发布状态
        drawSpaceCommon.setStatus(getPutawayState(houseSubmitBO.getDrawHouse()));

        return drawSpaceCommon;
    }

    /**
     * 类型、面积、空间名、状态处理
     *
     * @param drawSpaceCommonDTO
     * @param drawSpaceBO
     */
    public void handlerDrawSpace(DrawSpaceCommonDTO drawSpaceCommonDTO, DrawSpaceBO drawSpaceBO) {
        // 类型/面积
        SystemDictionary dict = null;
        if (!StringUtils.isEmpty(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey())) {
            dict = dictionaryService.findOneByValueKey(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey());
        }

        SystemDictionary area = null;
        if (!StringUtils.isEmpty(drawSpaceCommonDTO.getSpaceCommonArea()) && dict != null) {
            area = dictionaryService.findOneByTypeAndArea(dict.getValuekey(), Double.valueOf(drawSpaceCommonDTO.getSpaceCommonArea()));
        }

        // 主面积（空间面积）是实际房间面积减去玄关、过道等区域的面积，用户方案搜索优化
        if (drawSpaceCommonDTO.getSpaceCommonArea() != null) {
            drawSpaceBO.getDrawSpaceCommon().setMainArea(Utils.getDecimalFormat(Double.valueOf(drawSpaceCommonDTO.getSpaceCommonArea())));
        }

        // 实际房间面积是户型绘制出来时，整个房间的面积，用于计算造价
        if (drawSpaceCommonDTO.getSpaceCommonRealArea() != null) {
            drawSpaceBO.getDrawSpaceCommon().setRealArea(Utils.getDecimalFormat(Double.valueOf(drawSpaceCommonDTO.getSpaceCommonRealArea())));
        }

        // 空间类型
        drawSpaceBO.getDrawSpaceCommon().setSpaceFunctionId(dict == null ? null : dict.getValue());
        // 空间编码
        drawSpaceBO.getDrawSpaceCommon().setSpaceCode(this.getSpaceCode(area, Utils.generateRandomDigitString(6)));
        // 空间名 空间类型 + 面积（ 取真实面积、如果没有，取区域面积）+ 平
        String spaceArea = drawSpaceCommonDTO.getSpaceCommonRealArea() == null ? drawSpaceCommonDTO.getSpaceCommonArea() : drawSpaceCommonDTO.getSpaceCommonRealArea();
        drawSpaceBO.getDrawSpaceCommon().setSpaceName(this.getSpaceName(dict, area, spaceArea));
        // 空间面积
        String nullDefault = Utils.getDecimalFormat(new Double(drawSpaceCommonDTO.getSpaceCommonArea()));
        drawSpaceBO.getDrawSpaceCommon().setSpaceAreas(area == null ? nullDefault : Objects.toString(area.getValue(), nullDefault));
    }

    /**
     * get space code
     *
     * @param dict
     * @param randomNumStr
     * @return
     */
    private String getSpaceCode(SystemDictionary dict, String randomNumStr) {
        return (dict == null ? "unknown" : dict.getAtt6()) + "_" + System.nanoTime() + "_" + randomNumStr;
    }

    /**
     * get space name
     *
     * @param dict
     * @param area
     * @param nullDefault
     * @return
     */
    private String getSpaceName(SystemDictionary dict, SystemDictionary area, String nullDefault) {
//        nullDefault = Utils.getDecimalFormat(new Double(nullDefault));
//        String spaceArea = Objects.toString(area == null ? nullDefault : area.getValue(), nullDefault);
//        return (dict == null ? DEFAULTS_SPACE_NAME : Objects.toString(dict.getName(),
//                DEFAULTS_SPACE_NAME)) + spaceArea + "平";

        // v1.1.3(2018/8/3) COMMON-1203【户型绘制】户型绘制的样板房、空间名称显示精准面积
        String spaceArea = Objects.toString(Utils.getDecimalFormat(new Double(nullDefault)), "");
        return (dict == null ? DEFAULTS_SPACE_NAME : Objects.toString(dict.getName(),
                DEFAULTS_SPACE_NAME)) + spaceArea + "平";
    }

    /**
     * 空间状态处理
     *
     * @param drawHouse
     * @return
     */
    private Integer getPutawayState(DrawBaseHouse drawHouse) {
        // jira COREFUN-83
        // 烘培完成后该样板户型自动公开（对应的空间、样板房一起公开）
        return SpaceCommonStatusConstant.HAS_BEEN_RELEASE;
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
    }

    @Override
    public void beforeSaveHandler(List<DrawSpaceBO> drawSpaceBOS) {
    }

    @Override
    public void handlerCallbackTransform(CallbackTransformBO transformBO) {
        DrawSpaceCommon drawSpace = transformBO.getDrawSpace();
        Long baseHouseId = transformBO.getDrawHouse().getBaseHouseId();

        log.info("开时转化空间信息数据, {}(spaceId)", drawSpace.getId());

        // 空间灯光文件转换
        this.handlerTransformSpaceCommonModel(drawSpace, transformBO.getBakeFiles());

        // 处理空间图片
        drawSpaceCommonService.handlerSpacePic(drawSpace, transformBO.getBakeFiles());

        // 转换空间数据
        drawSpace.setOrigin(SpaceCommonStatusConstant.ORIGIN_DRAW);

        /**
         * 标识修复类型
         * @see com.sandu.common.constant.house.FixType
         */
        // drawSpace.setFixType(FixType.FIX_CUPBOARD.getStatus());

        // add space common
        drawSpaceCommonService.handlerSpaceCommon(drawSpace);

        // 建立空间与户型的关系(house space new)
        HouseSpaceNew houseSpaceNew = houseSpaceNewService.getHouseSpaceNewBySpaceCommonIdAndBaseHouseId(drawSpace.getSpaceCommonId(), baseHouseId);
        houseSpaceNewService.add(houseSpaceNew);

        // 处理空间转化后的资源回填
        this.handlerSpaceCommonResourceCallback(drawSpace);

        log.info("转化空间信息数据结束, {}(spaceId)", drawSpace.getId());
    }

    /**
     * 空间灯光文件转换
     *
     * @param drawSpace
     * @param bakeFiles
     */
    private void handlerTransformSpaceCommonModel(DrawSpaceCommon drawSpace, List<String> bakeFiles) {
        List<Integer> filesIds = Lists.newArrayList(drawSpace.getDaylightU3dModelId(), drawSpace.getDusklightU3dModelId(), drawSpace.getNightlightU3dModelId());
        List<DrawResFile> drawResFiles = drawResFileService.listDrawResFileById(filesIds);
        if (drawResFiles == null || drawResFiles.isEmpty()) {
            throw new DrawBusinessException(false, ResponseEnum.LIGHT_FILE_IS_EMPTY);
        }

        Map<Long, DrawResFile> fileMaps = drawResFiles.stream().collect(Collectors.toMap(DrawResFile::getId, f -> f, (p, n) -> n));

        // 白天
        DrawResFile daylightModel = fileMaps.get(drawSpace.getDaylightU3dModelId().longValue());
        ResModel daylightU3dModel = resModelService.createByDrawResFile(daylightModel, SystemConfigEnum.SPACE_MODEL_DAYLIGHT_UPLOAD.getFileKey(), true, true);
        if (daylightU3dModel == null || daylightU3dModel.getId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.SPACE_DAYLIGHT_EMPTY);
        }
        bakeFiles.add(daylightU3dModel.getModelPath());
        drawSpace.setDaylightU3dModelId(daylightU3dModel.getId().intValue());

        // 黄昏
        DrawResFile duskLightModel = fileMaps.get(drawSpace.getDusklightU3dModelId().longValue());
        ResModel duskLightU3dModel = resModelService.createByDrawResFile(duskLightModel, SystemConfigEnum.SPACE_MODEL_DUSKLIGHT_UPLOAD.getFileKey(), true, true);
        if (duskLightU3dModel == null || duskLightU3dModel.getId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.SPACE_DUSKLIGHT_EMPTY);
        }
        bakeFiles.add(duskLightU3dModel.getModelPath());
        drawSpace.setDusklightU3dModelId(duskLightU3dModel.getId().intValue());

        // 晚上
        DrawResFile nightlightModel = fileMaps.get(drawSpace.getNightlightU3dModelId().longValue());
        ResModel nightlightU3dModel = resModelService.createByDrawResFile(nightlightModel, SystemConfigEnum.SPACE_MODEL_NIGHTLIGHT_UPLOAD.getFileKey(), true, true);
        if (nightlightU3dModel == null || nightlightU3dModel.getId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.SPACE_NIGHTLIGHT_EMPTY);
        }
        bakeFiles.add(nightlightU3dModel.getModelPath());
        drawSpace.setNightlightU3dModelId(nightlightU3dModel.getId().intValue());
    }

    /**
     * 处理空间转化后的资源回填
     *
     * @param drawSpace
     */
    private void handlerSpaceCommonResourceCallback(DrawSpaceCommon drawSpace) {
        // model 资源回填BusinessId
        List<Integer> resModelIdList = new ArrayList<>();
        resModelIdList.add(drawSpace.getDaylightU3dModelId());
        resModelIdList.add(drawSpace.getDusklightU3dModelId());
        resModelIdList.add(drawSpace.getNightlightU3dModelId());
        resModelService.updateBusinessId(resModelIdList, drawSpace.getSpaceCommonId().intValue());

        // update space_common_id
        drawSpace.setPicId(null);
        drawSpace.setStatus(null);
        drawSpace.setDaylightU3dModelId(null);
        drawSpace.setDusklightU3dModelId(null);
        drawSpace.setDaylightU3dModelId(null);

        drawSpaceCommonService.update(drawSpace);
    }
}

