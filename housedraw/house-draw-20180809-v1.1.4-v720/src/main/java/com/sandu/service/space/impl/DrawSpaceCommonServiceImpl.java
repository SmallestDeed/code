package com.sandu.service.space.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sandu.api.house.model.*;
import com.sandu.api.res.model.ResPic;
import com.sandu.common.constant.*;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.util.Regex;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sandu.api.house.bo.DrawSpaceCommonBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.input.SystemDictionarySearch;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.house.service.DrawResHousePicService;
import com.sandu.api.house.service.DrawSpaceCommonService;
import com.sandu.api.house.service.HouseSpaceNewService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawSpaceCommonConstant;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.space.dao.DrawSpaceCommonMapper;
import com.sandu.service.space.dao.SpaceCommonMapper;
import com.sandu.util.Utils;
import com.sandu.util.auth.HouseAuthUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */

@Slf4j
@Service
public class DrawSpaceCommonServiceImpl implements DrawSpaceCommonService {

    private final static String DEFAULT_SPACE_NAME = "其它";

    @Autowired
    private DrawSpaceCommonMapper drawSpaceCommonMapper;

    @Autowired
    private SpaceCommonMapper spaceCommonMapper;

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @Autowired
    private HouseSpaceNewService houseSpaceNewService;

    @Autowired
    private ResModelService resModelService;

    @Autowired
    private DrawResFileService drawResFileService;

    @Autowired
    private DrawResHousePicService drawResHousePicService;

    @Autowired
    private DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    private BaseHouseMapper baseHouseMapper;

    @Override
    public List<DrawSpaceCommonBO> listSpaceCommon(Long houseId) {
        return drawSpaceCommonMapper.listSpaceCommon(houseId);
    }

    @Override
    public List<DrawSpaceCommonBO> listSpaceCommon(List<DrawSpaceCommon> drawSpaces) {
        if (drawSpaces == null || drawSpaces.isEmpty()) {
            return new ArrayList<>();
        }

        return drawSpaceCommonMapper.listSpaceCommonByIds(drawSpaces);
    }

    @Override
    public String getSpaceCommonCode(Integer spaceFunctionId, String spaceAreas) throws BusinessException {

        // 匹配出对应的空间
        SystemDictionary dictionary = getSpaceFunction(spaceFunctionId);
        String functionCode = dictionary.getAtt2();

        // 查出对应空间的面积
        String spaceAreaCode = getSpaceArea(spaceAreas, functionCode).getAtt6();

        if (StringUtils.isEmpty(functionCode) || StringUtils.isEmpty(spaceAreaCode)) {
            throw new BusinessException(false, ResponseEnum.SPACE_AREA_ERROR);
        }

        return functionCode + spaceAreaCode;
    }

    @Override
    public SystemDictionary getSpaceFunction(Integer spaceFunctionId) throws BusinessException {

        SystemDictionary dictionary = null;
        // 所有的房型
        SystemDictionarySearch systemDictionarySearch = new SystemDictionarySearch();
        systemDictionarySearch.setType(DictionaryTypeEnum.HOUSE_TYPE.getType());
        List<SystemDictionary> dicts = systemDictionaryService.listSysDictionary(systemDictionarySearch);
        // 匹配出绘制的房型数据字典
        for (SystemDictionary systemDictionary : dicts) {
            if (spaceFunctionId.equals(systemDictionary.getValue())) {
                dictionary = systemDictionary;
            }
        }

        if (null == dictionary) {
            throw new BusinessException(false, ResponseEnum.ERROR_SPACE_FUNCTION);
        }
        return dictionary;
    }

    @Override
    public SystemDictionary getSpaceArea(String spaceAreas, String type) throws BusinessException {
        if (StringUtils.isEmpty(type)) {
            throw new BusinessException(false, ResponseEnum.ERROR_SPACE_FUNCTION);
        }
        SystemDictionarySearch systemDictionarySearch = new SystemDictionarySearch();
        systemDictionarySearch.setType(type);
        List<SystemDictionary> systemDictionarys = systemDictionaryService.listSysDictionary(systemDictionarySearch);
        SystemDictionary spaceAreaCode = null;
        for (SystemDictionary sysDictionary : systemDictionarys) {
            if (Double.valueOf(sysDictionary.getAtt4()) <= Double.valueOf(spaceAreas)
                    && Double.valueOf(spaceAreas) <= Double.valueOf(sysDictionary.getAtt5())) {
                spaceAreaCode = sysDictionary;
            }
        }
        return spaceAreaCode;
    }

    @Override
    public Integer countSpaceCommonBySpaceCode(String spaceCodeSuffix) {
        return drawSpaceCommonMapper.countSpaceCommonBySpaceCode(spaceCodeSuffix);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long saveSpaceCommon(DrawSpaceCommon spaceCommon) {
        return drawSpaceCommonMapper.saveSpaceCommon(spaceCommon);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveHouseAndSpaceRelation(HouseSpaceNew relation) {
        drawSpaceCommonMapper.saveHouseSpaceNew(relation);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSpaceLight(Long resModelId, Long spaceId, String modelType) {
        drawSpaceCommonMapper.updateSpaceCommon(resModelId, spaceId, modelType);
    }

    @Override
    public DrawSpaceCommon getSpaceCommon(Long id) {
        return drawSpaceCommonMapper.get(id);
    }

    @Override
    public DrawSpaceCommon getSpaceCommon2(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return drawSpaceCommonMapper.get2(id);
    }

    @Override
    public DrawSpaceCommon getBakeSpaceCommon(Long drawSpaceId) {
        if (drawSpaceId == null || drawSpaceId <= 0) {
            return null;
        }

        return drawSpaceCommonMapper.getBakeSpaceCommon(drawSpaceId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long saveDrawSpaceCommon(DrawSpaceCommon drawSpaceCommon) {
        return drawSpaceCommonMapper.saveDrawSpaceCommon(drawSpaceCommon);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDrawHouseAndSpaceRelation(HouseSpaceNew relation) {
        drawSpaceCommonMapper.saveDrawHouseAndSpaceRelation(relation);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDrawSpaceCommon(List<Long> spaceIds) {
        drawSpaceCommonMapper.delete(spaceIds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDrawHouseSpaceRelation(Long houseId) {
        drawSpaceCommonMapper.deleteDrawHouseSpaceRelation(houseId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DrawBaseHouseSubmitDTO saveDrawSpaceCommonBySubmit(
            DrawBaseHouseSubmitDTO dto, UserLoginBO userLoginBO) {
        // 参数判断 ->start
        if (dto == null) {
            return null;
        }
        if (userLoginBO == null) {
            userLoginBO = HouseAuthUtils.getUnknownUser();
        }
        // 参数判断 ->end

        // 户型的总面积处理
        BigDecimal totalArea = new BigDecimal(0);

        for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
            DrawSpaceCommon drawSpaceCommon = new DrawSpaceCommon();
            drawSpaceCommon.setSysCode(Utils.getSysCode(6));

            // 类型/面积 ->start
            SystemDictionary dict = null;
            if (!StringUtils.isEmpty(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey())) {
                dict = systemDictionaryService.findOneByValueKey(drawSpaceCommonDTO.getSpaceCommonFunctionValueKey());
            }
            SystemDictionary area = null;
            if (!StringUtils.isEmpty(drawSpaceCommonDTO.getSpaceCommonArea()) && dict != null) {
                area = systemDictionaryService.findOneByTypeAndArea(dict.getValuekey(),
                        Double.valueOf(drawSpaceCommonDTO.getSpaceCommonArea()));
            }
            // 类型/面积 ->end

            // 空间编码
            drawSpaceCommon.setSpaceCode(this.getSpaceCode(area, Utils.generateRandomDigitString(6)));
            // 空间名
            drawSpaceCommon.setSpaceName(this.getSpaceName(dict, area, drawSpaceCommonDTO.getSpaceCommonArea()));
            drawSpaceCommon.setSpaceFunctionId(dict == null ? null : dict.getValue());
            drawSpaceCommon.setMainLength(drawSpaceCommonDTO.getSpaceCommonMainLength());
            drawSpaceCommon.setMainWidth(drawSpaceCommonDTO.getSpaceCommonMainWidth());
            // 空间面积
            String nullDefault = Utils.getDecimalFormat(new Double(drawSpaceCommonDTO.getSpaceCommonArea()));
            drawSpaceCommon.setSpaceAreas(area == null ? nullDefault : Objects.toString(area.getValue(), nullDefault));
            // 空间形状:前端无法获取,暂时默认为长方形
            drawSpaceCommon.setSpaceShape(DrawSpaceCommonConstant.DEFAULTS_SHAPE_RECTANGLE + "");
            // 空间图片:前端暂时无法获取
            drawSpaceCommon.setPicId(drawSpaceCommonDTO.getSpacePicId());

            // 未知属性
            drawSpaceCommon.setSpaceNum(1);
            // 没有意义,转换到space_common表要重新定义该值
            drawSpaceCommon.setCreator(userLoginBO.getLoginName());
            drawSpaceCommon.setModifier(userLoginBO.getLoginName());
            Date now = new Date();
            drawSpaceCommon.setGmtCreate(now);
            drawSpaceCommon.setGmtModified(now);
            drawSpaceCommon.setIsDeleted(DrawSpaceCommonConstant.DEFAULTS_IS_DELETED);
            drawSpaceCommon.setIsStandardSpace(1);
            drawSpaceCommon.setSyncStatus("NOT_SYNCHRONIZED");
            drawSpaceCommon.setOpenStatus(2);
            drawSpaceCommon.setBakeBeforeFileId(drawSpaceCommonDTO.getSpaceCommonFileId());
            drawSpaceCommon.setDrawBaseHouseId(Long.valueOf(dto.getHouseId() + ""));

            // 0、平台；1、个人
            drawSpaceCommon.setDataType(dto.getDataType());
            // 发布状态
            drawSpaceCommon.setStatus(getPutawayState(dto.getDrawHouse()));

            // 空间保存处理
            this.saveDrawSpaceCommon(drawSpaceCommon);
            drawSpaceCommonDTO.setSpaceCommonId(drawSpaceCommon.getId());

            // 累计空间面积
            totalArea = this.getSpaceTotalArea(totalArea, drawSpaceCommonDTO.getSpaceCommonArea());
        }

        // 更新户型的总面积
        DrawBaseHouse drawHouse = new DrawBaseHouse();
        drawHouse.setTotalArea(Math.round(totalArea.doubleValue()) + "");
        drawHouse.setId(dto.getHouseId().longValue());
        drawBaseHouseMapper.updateDrawBaseHouse(drawHouse);

        return dto;
    }

    /**
     * 累计空间面积
     * @param totalArea
     * @param spaceTotalArea
     * @return
     */
    private BigDecimal getSpaceTotalArea(BigDecimal totalArea, String spaceTotalArea) {
        return (org.apache.commons.lang3.StringUtils.isNoneBlank(spaceTotalArea) && spaceTotalArea.matches(Regex.DOUBLE_NUMBER.getValue()))
                ? totalArea.add(new BigDecimal(spaceTotalArea)) : totalArea;
    }

    /**
     * 字典里没有匹配的数据，设置真实的平米数据.
     * </p>
     *
     * @param dict
     * @param area
     * @param nullDefault
     * @return space_name = 类型名 + 平米数据 + "平"
     */
    private String getSpaceName(SystemDictionary dict, SystemDictionary area, String nullDefault) {
        nullDefault = Utils.getDecimalFormat(new Double(nullDefault));
        String spaceArea = Objects.toString(area == null ? nullDefault : area.getValue(), nullDefault);
        return (dict == null ? DEFAULT_SPACE_NAME : Objects.toString(dict.getName(), DEFAULT_SPACE_NAME)) + spaceArea + "平";
    }

    /**
     * 获取发布状态
     *
     * @param drawHouse
     * @return
     */
    private Integer getPutawayState(DrawBaseHouse drawHouse) {
        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
            return SpaceCommonStatusConstant.HAS_BEEN_RELEASE;
        }

        // 检查原先是否发布，如果发布继续为发布状态
        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
            return DesignTempletStatusConstant.HAS_BEEN_RELEASE.equals(putawayState) ? SpaceCommonStatusConstant.HAS_BEEN_RELEASE : SpaceCommonStatusConstant.TESTING;
            // return (putawayState == null) ? SpaceCommonStatusConstant.TESTING : putawayState;
        }

        return SpaceCommonStatusConstant.TESTING;
    }

    /**
     * 前缀+年月日时分秒+6位随机数 eg:RT01+20170210120101+632534
     *
     * @param dict
     * @param randomNumStr
     * @return
     */
    private String getSpaceCode(SystemDictionary dict, String randomNumStr) {
        return (dict == null ? "unknown" : dict.getAtt6()) + "_" + System.nanoTime() + "_" + randomNumStr;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(DrawSpaceCommon drawSpaceCommon) {
        // 参数验证 ->start
        if (drawSpaceCommon == null) {
            return;
        }
        // 参数验证 ->end

        drawSpaceCommonMapper.updateByPrimaryKeySelective(drawSpaceCommon);
    }

    @Override
    public DrawSpaceCommon get(Long id) {
        // 参数验证 ->start
        if (id == null) {
            return null;
        }
        // 参数验证 ->end

        return drawSpaceCommonMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<Long, Long> transformToSpaceCommon(DrawSpaceCommon drawSpaceCommon, Long baseHouseId) throws DrawBusinessException {

        Map<Long, Long> returnMap = new HashMap<>();
        // 参数验证 ->start
        if (drawSpaceCommon == null) {
            log.warn("需要转换的空间为空");
            return returnMap;
        }

        if (baseHouseId == null) {
            log.warn("参数baseHouseId为空");
            return returnMap;
        }

        log.info("开时转化空间信息数据, {}(spaceId)", drawSpaceCommon.getId());

        // 参数验证 ->end

        // 空间灯光文件转换 ->start
        // 白天
        DrawResFile drawResFileDaylightModel = drawResFileService.get(Long.valueOf(drawSpaceCommon.getDaylightU3dModelId() + ""));
        Long daylightPcU3dModelId = resModelService.createByDrawResFile(drawResFileDaylightModel, SystemConfigEnum.SPACE_MODEL_DAYLIGHT_UPLOAD.getFileKey(), true);
        if (daylightPcU3dModelId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawSpaceCommon.setDaylightU3dModelId(Integer.valueOf(daylightPcU3dModelId + ""));

        // 黄昏
        DrawResFile drawResFileDusklightModel = drawResFileService.get(Long.valueOf(drawSpaceCommon.getDusklightU3dModelId() + ""));
        Long dusklightU3dModelId = resModelService.createByDrawResFile(drawResFileDusklightModel, SystemConfigEnum.SPACE_MODEL_DUSKLIGHT_UPLOAD.getFileKey(), true);
        if (dusklightU3dModelId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawSpaceCommon.setDusklightU3dModelId(Integer.valueOf(dusklightU3dModelId + ""));

        // 晚上
        DrawResFile drawResFileNightlightModel = drawResFileService.get(Long.valueOf(drawSpaceCommon.getNightlightU3dModelId() + ""));
        Long nightlightU3dModelId = resModelService.createByDrawResFile(drawResFileNightlightModel, SystemConfigEnum.SPACE_MODEL_NIGHTLIGHT_UPLOAD.getFileKey(), true);
        if (nightlightU3dModelId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawSpaceCommon.setNightlightU3dModelId(Integer.valueOf(nightlightU3dModelId + ""));

        // 处理空间图片
        this.handlerSpacePic(drawSpaceCommon);
        // 空间灯光文件转换 ->end

        // 转换空间数据
        drawSpaceCommon.setOrigin(SpaceCommonStatusConstant.ORIGIN_DRAW);

        // 已存在的空间
        this.handlerSpaceCommon(drawSpaceCommon);
//        SpaceCommon spaceCommon = spaceCommonMapper.getSpaceCommonBySpaceCode(drawSpaceCommon.getSpaceCode());
//        if (spaceCommon == null) {
//            drawSpaceCommonMapper.transformToSpaceCommon(drawSpaceCommon);
//            log.debug("新增空间{}信息 SpaceCommonId = {}", drawSpaceCommon.getSpaceCode(), drawSpaceCommon.getSpaceCommonId());
//        } else {
//            log.info("更新空间{}信息", drawSpaceCommon.getSpaceCode());
//            SpaceCommon spaceCommon2 = new SpaceCommon();
//            try {
//                BeanUtils.copyProperties(spaceCommon2, drawSpaceCommon);
//                spaceCommon2.setId(spaceCommon.getId());
//                // 恢复数据
//                spaceCommon2.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
//            } catch (IllegalAccessException | InvocationTargetException e) {
//                throw new DrawBusinessException(false, ResponseEnum.ERROR);
//            }
//            spaceCommonMapper.updateByPrimaryKeySelective(spaceCommon2);
//            drawSpaceCommon.setSpaceCommonId(spaceCommon.getId());
//        }

        // 回填模型数据businessId
        List<Integer> resModelIdList = new ArrayList<Integer>();
        resModelIdList.add(Integer.valueOf(daylightPcU3dModelId + ""));
        resModelIdList.add(Integer.valueOf(dusklightU3dModelId + ""));
        resModelIdList.add(Integer.valueOf(nightlightU3dModelId + ""));
        resModelService.updateBusinessId(resModelIdList, Integer.valueOf(drawSpaceCommon.getSpaceCommonId() + ""));

        // update space_common_id
        drawSpaceCommon.setDaylightU3dModelId(null);
        drawSpaceCommon.setDusklightU3dModelId(null);
        drawSpaceCommon.setDaylightU3dModelId(null);
        drawSpaceCommon.setStatus(null);
        drawSpaceCommon.setPicId(null);
        this.update(drawSpaceCommon);

        returnMap.put(drawSpaceCommon.getId(), drawSpaceCommon.getSpaceCommonId());

        // 建立空间与户型的关系
        HouseSpaceNew houseSpaceNew = houseSpaceNewService.getHouseSpaceNewBySpaceCommonIdAndBaseHouseId(drawSpaceCommon.getSpaceCommonId(), baseHouseId);
        houseSpaceNewService.add(houseSpaceNew);

        log.info("转化空间信息数据结束, {}(spaceId)", drawSpaceCommon.getId());

        return returnMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerSpaceCommon(DrawSpaceCommon drawSpaceCommon) {
        // 已存在的空间
        SpaceCommon spaceCommon = spaceCommonMapper.getSpaceCommonBySpaceCode(drawSpaceCommon.getSpaceCode());
        if (spaceCommon == null) {
            drawSpaceCommonMapper.transformToSpaceCommon(drawSpaceCommon);
            log.debug("新增空间{}信息 SpaceCommonId = {}", drawSpaceCommon.getSpaceCode(), drawSpaceCommon.getSpaceCommonId());
        } else {
            log.info("更新空间{}信息", drawSpaceCommon.getSpaceCode());
            SpaceCommon spaceCommon2 = new SpaceCommon();
            try {
                BeanUtils.copyProperties(spaceCommon2, drawSpaceCommon);
                spaceCommon2.setId(spaceCommon.getId());
                // 恢复数据
                spaceCommon2.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new DrawBusinessException(false, ResponseEnum.ERROR);
            }

            spaceCommonMapper.updateByPrimaryKeySelective(spaceCommon2);
            drawSpaceCommon.setSpaceCommonId(spaceCommon.getId());
        }
    }

    /**
     * 处理空间图片
     *
     * @param drawSpaceCommon
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerSpacePic(DrawSpaceCommon drawSpaceCommon) {
        this.handlerSpacePic(drawSpaceCommon, null);
    }

    /**
     * 处理空间图片
     * @param drawSpaceCommon
     * @param bakeFiles
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerSpacePic(DrawSpaceCommon drawSpaceCommon, List<String> bakeFiles) {
        log.info("{}(spaceId)同步空间图片资源处理", drawSpaceCommon.getId());
        // 样板房effectPic
        DrawResHousePic pic = drawResHousePicService.getDrawResHousePic(Long.valueOf(Objects.toString(drawSpaceCommon.getPicId(), "-1")));
        if (pic == null || StringUtils.isEmpty(pic.getPicPath())) {
            // v1.1.1不加图片验证
//            throw new BusinessException(false, ResponseEnum.SPACE_PIC_IS_EMPTY);
            log.warn("{}(drawSpaceId)空间没有截图", drawSpaceCommon.getId());
            return;
        }

        ResPic effectPic = drawResHousePicService.copyFile(pic, SystemConfigEnum.SPACE_PIC_UPLOAD.getFileKey(), false, true);
        if (effectPic == null || effectPic.getId() == null || effectPic.getId() <= 0
                || StringUtils.isEmpty(effectPic.getPicPath())) {
            // v1.1.1不加图片验证
//            throw new BusinessException(false, ResponseEnum.SPACE_PIC_IS_EMPTY);
            log.warn("{}(drawSpaceId)空间复制截图失败", drawSpaceCommon.getId());
            return;
        }

        if (bakeFiles != null) {
            bakeFiles.add(effectPic.getPicPath());
        }

        drawSpaceCommon.setPicId(effectPic.getId().intValue());
    }

    @Override
    public List<Long> getDealDrawSpaceCommon(Integer isDeleted, Long houseId) {
        if (houseId == null) {
            return null;
        }
        return drawSpaceCommonMapper.getDealDrawSpaceCommon(isDeleted, houseId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteDrawSpaceCommon(Integer status, List<Long> emptySpaces) {
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return 0;
        }
        return drawSpaceCommonMapper.deleteDrawSpaceCommon(status, emptySpaces);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteSpaceCommon(Integer status, List<Long> emptySpaces) {
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return 0;
        }
        return spaceCommonMapper.deleteSpaceCommon(status, emptySpaces);
    }

    @Override
    public List<Long> getDealSpaceCommon(Long spaceId) {
        if (spaceId == null) {
            return null;
        }
        return drawSpaceCommonMapper.getDealSpaceCommon(spaceId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteHouseSpaceRelation(Integer status, List<Long> emptySpaces) {
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return 0;
        }
        return spaceCommonMapper.deleteHouseSpaceRelation(status, emptySpaces);
    }

    @Override
    public List<Long> getEmptyDealSpaceCommon(Long houseId) {
        if (houseId == null || houseId <= 0) {
            return null;
        }
        return drawSpaceCommonMapper.getEmptyDealSpaceCommon(houseId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer emptySpaceCommon(Integer status, List<Long> emptySpaces) {
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return -1;
        }
        return spaceCommonMapper.emptySpaceCommon(status, emptySpaces);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer emptyHouseSpaceRelation(Integer status, List<Long> emptySpaces) {
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return -1;
        }
        return spaceCommonMapper.emptyHouseSpaceRelation(status, emptySpaces);
    }
}
