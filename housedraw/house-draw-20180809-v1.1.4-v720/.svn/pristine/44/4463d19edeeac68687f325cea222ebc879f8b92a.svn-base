package com.sandu.service.house.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sandu.api.area.bo.BaseAreaLivingBO;
import com.sandu.api.house.service.*;
import com.sandu.api.res.service.ResHandlerErrorService;
import com.sandu.api.v2.house.dto.DrawBaseHouseCallbackDTO;
import com.sandu.api.v2.house.service.DrawBaseHouseServiceV2;
import com.sandu.api.mail.model.PerfectHouseMailMessage;
import com.sandu.api.volume.room.model.VolumeRoomHouseBO;
import com.sandu.api.volume.room.service.DrawVolumeRoomService;
import com.sandu.common.constant.bake.BakeCallbackStatus;
import com.sandu.common.constant.bake.BakeTaskQueue;
import com.sandu.common.constant.kechuang.PlatformType;
import com.sandu.common.constant.kechuang.VolumeRoomConstant;
import com.sandu.exception.BizException;
import com.sandu.mq.queue.SyncMessage;
import com.sandu.mq.queue.service.QueueService;
import com.sandu.service.volume.room.dao.DrawVolumeRoomMapper;
import com.sandu.util.http.HttpContextUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.house.bo.BaseAreaBO;
import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.bo.DrawBaseProductBO;
import com.sandu.api.house.bo.DrawHouseResouceBO;
import com.sandu.api.house.bo.DrawSpaceCommonBO;
import com.sandu.api.house.bo.LivingCodeBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseCheck;
import com.sandu.api.house.input.DrawBaseHouseControllerCallBackDTO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawBaseHouseNew;
import com.sandu.api.house.input.DrawBaseHouseSearch;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.DrawDesignTempletDTO;
import com.sandu.api.house.input.DrawFileDataNew;
import com.sandu.api.house.model.BaseHouse;
import com.sandu.api.house.model.BaseLiving;
import com.sandu.api.house.model.DrawBakeTask;
import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.model.DrawResHousePic;
import com.sandu.api.house.model.DrawSpaceCommon;
import com.sandu.api.res.model.ResFile;
import com.sandu.api.res.model.ResHousePic;
import com.sandu.api.house.output.DrawHouseAndAreaVO;
import com.sandu.api.house.search.DrawDesignTempletProductSearch;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.mail.service.TemplateMailService;
import com.sandu.common.DrawType;
import com.sandu.common.HouseRole;
import com.sandu.common.constant.FileKey;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawDesignTempletProductConstant;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.file.dao.DrawResFileMapper;
import com.sandu.service.file.dao.ResFileMapper;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.service.living.dao.BaseLivingMapper;
import com.sandu.service.pic.dao.DrawResHousePicMapper;
import com.sandu.service.pic.dao.ResHousePicMapper;
import com.sandu.util.Utils;
import com.sandu.util.auth.HouseAuthUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/28
 */

@Slf4j
@Service("drawBaseHouseService")
public class DrawBaseHouseServiceImpl implements DrawBaseHouseService {

    public final static Map<Integer, Integer[]> ARGS_MAP = new HashMap<>();

    static {
        ARGS_MAP.put(1, null);
        ARGS_MAP.put(0, new Integer[]{DrawBaseHouseConstant.CHECK_APPLY});
        ARGS_MAP.put(2, new Integer[]{DrawBaseHouseConstant.HOUSE_WAIT_PERFECT, DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING});
    }

    @Autowired
    private DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    private DrawResHousePicMapper drawResHousePicMapper;

    @Autowired
    private DrawResFileMapper drawResFileMapper;

    @Autowired
    private DrawSpaceCommonService drawSpaceCommonService;

    @Autowired
    private DrawBaseProductService drawBaseProductService;

    @Autowired
    private BaseLivingService baseLivingService;

    @Autowired
    private DrawDesignTempletService drawDesignTempletService;

    @Autowired
    private DrawDesignTempletProductService drawDesignTempletProductService;

    @Autowired
    private DrawBakeTaskService drawBakeTaskService;

    @Autowired
    private DrawBakeTaskDetailService drawBakeTaskDetailService;

    @Autowired
    private BaseLivingMapper baseLivingMapper;

    @Autowired
    private DrawBaseHouseService drawBaseHouseService;

    @Autowired
    private BaseHouseMapper baseHouseMapper;

    @Autowired
    private ResHousePicMapper resHousePicMapper;

    @Autowired
    private ResFileMapper resFileMapper;

    @Autowired
    private TemplateMailService templateMailService;

    @Autowired
    private QueueService queueService;

    @Autowired
    private DesignTempletJumpPositionRelService designTempletJumpPositionRelService;

    @Autowired
    private DrawBaseHouseServiceV2 drawBaseHouseServiceV2;

    @Autowired
    private DrawVolumeRoomMapper drawVolumeRoomMapper;

    @Autowired
    @Qualifier("KCDrawVolumeRoomServiceImpl")
    private DrawVolumeRoomService drawVolumeRoomService;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    ResHandlerErrorService resHandlerErrorService;

    @Override
    public Map<String, Object> listDrawHouse(DrawBaseHouseSearch query) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> localAreaCache = new HashMap<>();

        List<DrawBaseHouseBO> list = new ArrayList<>();

        if (query.getUserId() == null) {
            query.setUserId(HouseAuthUtils.getRequestUserId());
        }

        // 检查权限
        this.wrapCheckArgs(query);

        Long total = drawBaseHouseMapper.countDrawHouse(query);
        if (total > 0) {
            list = drawBaseHouseMapper.listDrawHouse(query);
        }

        if (list != null && !list.isEmpty()) {
            // 图片资源
            Map<Long, String> mapResource = this.mapResource(list);
            // 量房资源
            Map<Long, VolumeRoomHouseBO> mapVolumeRoom = this.mapVolumeRoomHouse(list);

            StringBuilder buf = new StringBuilder();
            for (DrawBaseHouseBO house : list) {
                if (StringUtils.isNoneBlank(house.getAreaLongCode())) {
                    String detailAddress = getDetailAddress(localAreaCache, house.getAreaLongCode());
                    buf.append(detailAddress).append(house.getLivingName());
                    house.setDetailAddress(buf.toString());
                    // clear
                    buf.setLength(0);
                }

                // 图片资源优先级 ： 截图 > 户型图
                house.setPicPath(mapResource.get(house.getId()));

                // 量房 lf 文件处理
                VolumeRoomHouseBO volumeRoom = mapVolumeRoom.get(house.getId());
                if (volumeRoom != null && !PlatformType.SANDU.getType().equals(house.getPlatformType())) {
                    house.setFilePath(volumeRoom.getLfFilePath());
                }
            }
        }

        map.put("total", total);
        map.put("list", list);

        return map;
    }

    /**
     * 加入本地临时缓存，缓解数据库压力
     * @param localAreaCache
     * @param areaLongCode
     * @return
     */
    private String getDetailAddress(Map<String, String> localAreaCache, String areaLongCode) {
        // 本地缓存
        String localCacheValue;
        if (localAreaCache.containsKey(areaLongCode) && (localCacheValue = localAreaCache.get(areaLongCode)) != null) {
            return localCacheValue;
        }

        // TODO redis缓存

        // db
        String[] split = areaLongCode.substring(1, areaLongCode.length() - 1).split("\\.");
        String detailAddress = baseLivingService.getDetailAddress(split);
        if (!Strings.isNullOrEmpty(detailAddress)) {
            localAreaCache.put(areaLongCode, detailAddress);
        }

        return detailAddress == null ? Utils.VOID_VALUE : detailAddress;
    }

    /**
     * 审核列表(0) => 审核 => house_status = 2
     * </p>
     * 我的绘制(1) => 草稿、驳回 => house_status = all
     * </p>
     * 完善列表(2) => 完善绘制员可见 => hosue_status = 8
     * </p>
     * 默认我的绘制
     *
     * @param query
     */
    private void wrapCheckArgs(DrawBaseHouseSearch query) {
        Integer checkStatus = query.getCheckStatus();
        // 我的绘制列表
        if (checkStatus == null || checkStatus == HouseListStatus.DRAW.getStatus()) {
            return;
        }

        // 审核列表，审核户型权限
        if (checkStatus == HouseListStatus.CHECK.getStatus() && HouseAuthUtils.hasApprovedPermission()) {
            throw new BusinessException(false, ResponseEnum.FORBIDDEN);
        }
        // 完善列表，完善户型权限
        else if (checkStatus == HouseListStatus.IMPROVER.getStatus() && !HouseAuthUtils.hasImproverPermission()) {
            throw new BusinessException(false, ResponseEnum.FORBIDDEN);
        }

        // 特别注意，只有完善员和审核员角色才可以userId = null
        query.setUserId(null);

        // 默认我的绘制 => 草稿、驳回
        query.setCheckArgs(ARGS_MAP.get(checkStatus));
    }

    @Getter
    @AllArgsConstructor
    enum HouseListStatus {
        // 审核
        CHECK(0),
        // 绘制
        DRAW(1),
        // 完善
        IMPROVER(2);
        private int status;
    }

    /**
     * 户型资源
     *
     * @param drawHouses
     * @return
     */
    @Override
    public Map<Long, String> mapResource(List<DrawBaseHouseBO> drawHouses) {
        Map<Long, String> map = new HashMap<>();
        if (drawHouses == null || drawHouses.isEmpty()) {
            return map;
        }

        // filter null
        List<DrawBaseHouseBO> collect = drawHouses.stream().filter(h -> !(h.getSnapPicId() == null
                || h.getSnapPicId() <= 0)).collect(Collectors.toList());

        // 查询snap pic
        // 图片资源优先级 ： 截图 > 户型图
        List<DrawBaseHouseBO> listResource = drawBaseHouseMapper.listDrawHouseResource(1, collect);

        // 查询res_pic
        if (listResource.size() < drawHouses.size()) {
            List<DrawBaseHouseBO> snap = drawHouses.stream().filter(p -> (p.getSnapPicId() == null || p.getSnapPicId() <= 0)
                    && (p.getPicRes1Id() != null && p.getPicRes1Id() > 0)).collect(Collectors.toList());

            // Fuck NullPointerException
            if (snap != null && !snap.isEmpty()) {
                snap = drawBaseHouseMapper.listDrawHouseResource(null, snap);
                listResource.addAll(snap);
            }
        }

        if (!listResource.isEmpty()) {
            // (prev, next) -> next 解决重复key抛出异常问题，取后一个的值为准
            map = listResource.stream().collect(Collectors.toMap(DrawBaseHouseBO::getId, DrawBaseHouseBO::getPicPath, (prev, next) -> next));
        }

        return Optional.of(map).orElse(new HashMap<>());
    }

    /**
     * 量房资源
     *
     * @param drawHouses
     * @return
     */
    @Override
    public Map<Long, VolumeRoomHouseBO> mapVolumeRoomHouse(List<DrawBaseHouseBO> drawHouses) {
        Map<Long, VolumeRoomHouseBO> map = new HashMap<>();
        if (drawHouses == null || drawHouses.isEmpty()) {
            return map;
        }

        List<DrawBaseHouseBO> volumeRooms = drawHouses.stream().filter(p -> !PlatformType.SANDU.getType().equals(p.getPlatformType())
                && StringUtils.isEmpty(p.getFilePath())).collect(Collectors.toList());
        if (volumeRooms == null || volumeRooms.isEmpty()) {
            return map;
        }

        List<VolumeRoomHouseBO> listVolumeRooms = drawVolumeRoomMapper.listVolumeRoomByHouseId(volumeRooms, VolumeRoomConstant.DEFAULTS.getStatus());
        if (!listVolumeRooms.isEmpty()) {
            // (prev, next) -> next 解决重复key抛出异常问题，取后一个的值为准
            map = listVolumeRooms.stream().collect(Collectors.toMap(VolumeRoomHouseBO::getDrawHouseId, v -> v, (p, n) -> n));
        }

        return Optional.of(map).orElse(new HashMap<>());
    }

    /**
     * v2版本的保存户型，添加了异常时，删除资源文件处理功能
     * @param data
     * @param userId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> saveDrawHouseV2(String data, String userId) {
        try {
            return this.saveDrawBaseHouse(data, userId);
        } catch (Exception e) {
            // 保存异常，删除已上传过的文件资源
            // 保存户型的主要有截图、户型图片(.png)、户型的还原文件(.sdkj)三个资源文件，异常时删除这三个文件即可
            resHandlerErrorService.handlerErrorWithSave(data);
            throw e;
        }
    }

    /**
     * 保存户型
     * @param data
     * @param userId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> saveDrawBaseHouse(String data, String userId) {
        Map<String, Object> map = new HashMap<>();
        // 解析请求参数
        DrawBaseHouseNew houseNew = getDrawBaseHouseNew(data, userId);

        // 户型处理
        DrawBaseHouse house = handlerDrawBaseHouse(houseNew);
        map.put("house", house);

        DrawResHousePic pic = new DrawResHousePic();
        DrawResHousePic snapPic = new DrawResHousePic();
        DrawResFile file = new DrawResFile();

        // 处理户型和文件的关系
        for (DrawFileDataNew drawFileDataNew : houseNew.getDatas()) {
            if (SystemConfigEnum.HOUSE_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(drawFileDataNew.getFileType()))) {
                // 户型图
                pic = adapterResHousePic(house, drawFileDataNew);
                drawResHousePicMapper.saveDrawResHousePic(pic);
                // 户型图
                house.setPicRes1Id(pic.getId());
            } else if (SystemConfigEnum.HOUSE_SNAP_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(drawFileDataNew.getFileType()))) {
                // 户型快照图片（截图）
                snapPic = adapterResHousePic(house, drawFileDataNew);
                drawResHousePicMapper.saveDrawResHousePic(snapPic);
                // 户型快照图片（截图）
                house.setSnapPicId(snapPic.getId().longValue());
            } else {
                // 还原文件
                file = getDrawResFile(house, drawFileDataNew);
                drawResFileMapper.saveDrawResFile(file);
                // 还原文件
                house.setRestoreFileId(file.getId());
            }
        }

        // 更新户型图、截图、原因文件、修改时间
        house.setGmtModified(new Date());
        house.setModifier(HouseAuthUtils.getUserLoginBO().getLoginName());

        // 审核时编辑不需要转移户型
        if (!Objects.equals(houseNew.getDrawType(), DrawType.DRAW_AUDITOR_EDIT)
                && !Objects.equals(houseNew.getDrawType(), DrawType.DRAW_ROUGH_HOUSE)) {
            // 搜索去修改，户型归属转移给修改的人，需要平台数据才可以
            if (DrawBaseHouseConstant.DATA_PLATFORM.equals(house.getDataType()) && HouseAuthUtils.hasDrawPermission()) {
                house.setIsChangedUser(1);
                house.setUserId(HouseAuthUtils.getRequestUserId());
                log.info("{}(houseId)户型归属转交给 {}(userId)用户", house.getId(), house.getUserId());
            }
        }

        int updateBaseHouse = drawBaseHouseMapper.updateDrawBaseHouse(house);
        if (updateBaseHouse <= 0) {
            throw new BizException("更新资源文件异常");
        }

        map.put("pic", pic);
        map.put("snapPic", snapPic);
        map.put("file", file);

        return map;
    }

    private DrawBaseHouseNew getDrawBaseHouseNew(String dataJson, String userId) {
        if (!StringUtils.isNotBlank(dataJson)) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        DrawBaseHouseNew drawHouseNew;
        try {
            drawHouseNew = new ObjectMapper().readValue(dataJson, DrawBaseHouseNew.class);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }

        if (drawHouseNew == null) {
            throw new BizException("保存的户型为空");
        }

        if (!StringUtils.isNotBlank(drawHouseNew.getHouseName())) {
            throw new BizException("户型名不能为空");
        }

        // userId valid
        if (!StringUtils.isNotBlank(userId)) {
            Long requestUserId = HouseAuthUtils.getRequestUserId();
            if (requestUserId == null) {
                throw new BusinessException(false, ResponseEnum.USER_INFO_IS_NULL);
            }
            userId = requestUserId.toString();
        }
        drawHouseNew.setUserId(Long.valueOf(userId));

        if (drawHouseNew.getDatas().isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        return drawHouseNew;
    }

    private DrawBaseHouse handlerDrawBaseHouse(DrawBaseHouseNew data) throws BusinessException {
        // 过滤
        DrawBaseHouse drawHouse = drawFilter(data);

        // 新增绘制、绘制通用PC户型老数据
        if (null == data.getHouseId()) {
            // 小区
            BaseLiving living = getBaseLiving(data);

            // 产生0-10000000的整数随机数
            // int randomNum = (int) (Math.random() * 10000000);
            String randomNum = Utils.random();
            drawHouse.setHouseCode(null == living ? ("0" + randomNum) : (living.getLivingCode() + randomNum));
            drawHouse.setUserId(data.getUserId());
            drawHouse.setHouseName(data.getHouseName());
            drawHouse.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            drawHouse.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
            drawHouse.setLivingId(null == living ? 0 : living.getId().intValue());
            drawHouse.setTotalArea(data.getTotalArea());
            drawHouse.setAreaLongCode(null == living ? "" : living.getAreaLongCode());
            drawHouse.setHouseStatus(String.valueOf(DrawBaseHouseConstant.CHECK_INIT));
            drawHouse.setGmtCreate(new Date());
            drawHouse.setCreator(HouseAuthUtils.getLoginName());
            // 数据类型 0、非公开 1、公开
            drawHouse.setIsPublic(Utils.getHouseIsPublic());
            // 数据类型 0、平台数据 1、用户个人数据
            drawHouse.setDataType(Utils.getUserDataType());

            // 保存户型数据
            drawBaseHouseMapper.insertSelective(drawHouse);
        } else {
            drawHouse = drawBaseHouseMapper.getBaseHouseById(data.getHouseId());
            if (null == drawHouse) {
                throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
            }

            if (Integer.valueOf(drawHouse.getHouseStatus()).intValue() == DrawBaseHouseConstant.HOUSE_BAKE_WAIT) {
                throw new BusinessException(false, ResponseEnum.HOUSE_ALREADY_AUDIT_PASS);
            }

            drawHouse.setHouseName(data.getHouseName());
            drawHouse.setTotalArea(data.getTotalArea());

            // 草稿状态
            // 非完善户型和审核时编辑
            String houseStatus = drawHouse.getHouseStatus();
            if (!Objects.equals(houseStatus, DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString())
                    && !Objects.equals(houseStatus, DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING.toString())
                    && !Objects.equals(data.getDrawType(), DrawType.DRAW_AUDITOR_EDIT)) {
                drawHouse.setHouseStatus(DrawBaseHouseConstant.CHECK_INIT.toString());
            }

            // 删除资源处理
            this.deletedFile(drawHouse, data.getDatas());
        }

        // 处理量房status
        drawVolumeRoomService.handlerVolumeRoom(drawHouse, VolumeRoomConstant.PARSING_SUCCESS.getStatus(), VolumeRoomConstant.DEFAULTS.getStatus());

        return drawHouse;
    }

    /**
     * @param drawHouse
     * @param files
     */
    public void deletedFile(DrawBaseHouse drawHouse, List<DrawFileDataNew> files) {
        List<Integer> deletedFile = new ArrayList<>();
        for (DrawFileDataNew file : files) {
//            if (SystemConfigEnum.HOUSE_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(file.getFileType()))) {
//                if (drawHouse.getPicRes1Id() != null) {
//                    deletedFile.add(drawHouse.getPicRes1Id());
//                }
//            }
            if (SystemConfigEnum.HOUSE_SNAP_PIC_UPLOAD.equals(SystemConfigEnum.valueOf(file.getFileType()))) {
                if (drawHouse.getSnapPicId() != null) {
                    deletedFile.add(drawHouse.getSnapPicId().intValue());
                }
            }
        }

        // 删除户型图片、截图
        if (!deletedFile.isEmpty()) {
            drawResHousePicMapper.deleteDrawResHousePic(deletedFile.toArray(new Integer[]{}));
        }

        // 删除户型还原文件
        drawResFileMapper.deleteDrawResFile(drawHouse.getRestoreFileId(), DrawBaseHouseConstant.RESTORE_FILE_TYPE);
    }

    private DrawBaseHouse drawFilter(DrawBaseHouseNew houseNew) {
        if (houseNew.getDrawType() == null || houseNew.getDrawType() == DrawType.DRAW_NEW) {
            return drawNewHouseFilter(houseNew);
        } else if (houseNew.getDrawType() == DrawType.DRAW_OLD) {
            // 临摹老数据的户型
            return drawOldHouseFilter(houseNew);
        } else if (houseNew.getDrawType() == DrawType.DRAW_AUDITOR_EDIT) {
            // 审核人编辑
            return drawCheckHouseFilter(houseNew);
        } else if (houseNew.getDrawType() == DrawType.DRAW_ROUGH_HOUSE) {
            // 毛坯房
            DrawBaseHouse drawBaseHouse = drawRoughHouseFilter(houseNew);
            drawBaseHouse.setOrigin(DrawBaseHouseConstant.ORIGIN_ROUGH_HOUSE);
            return drawBaseHouse;
        } else {
            throw new BizException("未知的绘制类型, drawType=" + houseNew.getDrawType());
        }
    }

    /**
     * 绘制工具绘制的新户型过滤处理
     * <pre>
     *     1、待审核的户型不能绘制
     *     2、烘焙中的户型不能绘制
     *     3、烘焙中的户型不能绘制
     *     4、一个户型不能有多个人绘制
     * </pre>
     * {@link com.sandu.common.constant.house.DrawBaseHouseConstant}
     *
     * @param houseNew
     * @return
     */
    private DrawBaseHouse drawNewHouseFilter(DrawBaseHouseNew houseNew) {
        // 通过绘制工具绘制的户型
        if (null != houseNew.getHouseId()) {
            DrawBaseHouse drawBaseHouse = drawBaseHouseMapper.selectByPrimaryKey(houseNew.getHouseId());
            if (drawBaseHouse != null) {
                if (drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.CHECK_APPLY.toString())) {
                    // 待审核
                    throw new BizException("待审核的户型不能绘制");
                } else if (drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_WAIT.toString())
                        || drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_PROCESSING.toString())
                        || drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_FAIL.toString())) {
                    // 待烘焙、烘焙中、烘焙失败
                    throw new BizException("烘焙中的户型不能绘制");
                }

                if (drawBaseHouse.getBaseHouseId() != null && drawBaseHouse.getBaseHouseId() != 0) {
                    // 老数据重新绘制
                    BaseHouse baseHouse = baseHouseMapper.selectByPrimaryKey(drawBaseHouse.getBaseHouseId());
                    if (baseHouse != null) {
                        if (StringUtils.isNotBlank(baseHouse.getDealStatus())) {
                            // 是否正在烘焙中
                            if (DrawBaseHouseConstant.DEAL_STATUS_BAKE.equals(baseHouse.getDealStatus())) {
                                throw new BizException("烘焙中的户型不能绘制");
                            }

                            // 是否有人正在绘制, 没有人绘制就锁住当前绘制的户型
                            if (!DrawBaseHouseConstant.DEAL_STATUS_RUNNING.equals(baseHouse.getDealStatus())
                                    && !DrawBaseHouseConstant.DEAL_STATUS_RERUNNING.equals(baseHouse.getDealStatus())) {
                                // 锁住当前绘制的户型
                                this.lockHouse(baseHouse.getDealStatus(), houseNew.getUserId(), baseHouse.getId());
                            } else if ((DrawBaseHouseConstant.DEAL_STATUS_RUNNING.equals(baseHouse.getDealStatus())
                                    || DrawBaseHouseConstant.DEAL_STATUS_RERUNNING.equals(baseHouse.getDealStatus()))
                                    && !houseNew.getUserId().equals(baseHouse.getUserId())) {
                                // 绘制的人是否是我
                                throw new BizException("一个户型不能有多个人绘制");
                            }
                        } else {
                            // 第一次处理 dealStatus = null
                            this.lockHouse(baseHouse.getDealStatus(), houseNew.getUserId(), houseNew.getHouseId());
                        }
                    }
                }
            } else {
                throw new BizException("绘制的户型[" + houseNew.getHouseId() + "(houseId), " + houseNew.getHouseName() + "(houseName)]不存在");
            }
        }

        return new DrawBaseHouse();
    }

    /**
     * 锁住当前绘制的户型
     *
     * @param dealStatus
     * @param userId
     * @param houseId
     */
    private void lockHouse(String dealStatus, Long userId, Long houseId) {
        String status = DrawBaseHouseConstant.DEAL_STATUS_EDIT.equals(dealStatus) ?
                DrawBaseHouseConstant.DEAL_STATUS_RERUNNING : DrawBaseHouseConstant.DEAL_STATUS_RUNNING;
        int updateHouseDealStatus = baseHouseMapper.updateHouseDealStatusForLock(status, userId, houseId);
        if (updateHouseDealStatus <= 0) {
            throw new BizException("数据出现异常");
        }
    }

    /**
     * 临摹通用app老数据的户型
     * <pre>
     *     1、烘焙中的户型不能绘制
     *     2、一个户型不能有多个人绘制
     * </pre>
     * {@link com.sandu.common.constant.house.DrawBaseHouseConstant}
     *
     * @param houseNew
     * @return
     */
    private DrawBaseHouse drawOldHouseFilter(DrawBaseHouseNew houseNew) {
        DrawBaseHouse drawBaseHouse = new DrawBaseHouse();
        if (houseNew.getHouseId() != null && houseNew.getBaseHouseId() == null) {
            BaseHouse baseHouse = baseHouseMapper.selectByPrimaryKey(houseNew.getHouseId());

            if (baseHouse != null) {
                // 是否正在烘焙中
                if (DrawBaseHouseConstant.DEAL_STATUS_BAKE.equals(baseHouse.getDealStatus())) {
                    throw new BizException("烘焙中的户型不能绘制");
                }

                // 是否有人正在绘制
                if ((StringUtils.isEmpty(baseHouse.getDealStatus()) || baseHouse.getUserId().equals(0L))
                        || (!DrawBaseHouseConstant.DEAL_STATUS_RUNNING.equals(baseHouse.getDealStatus())
                        && !DrawBaseHouseConstant.DEAL_STATUS_RERUNNING.equals(baseHouse.getDealStatus()))) {
                    // 将户型变成绘制中
                    this.lockHouse(baseHouse.getDealStatus(), houseNew.getUserId(), baseHouse.getId());
                } else if ((DrawBaseHouseConstant.DEAL_STATUS_RUNNING.equals(baseHouse.getDealStatus())
                        || DrawBaseHouseConstant.DEAL_STATUS_RERUNNING.equals(baseHouse.getDealStatus()))
                        && !baseHouse.getUserId().equals(houseNew.getUserId())) {
                    // 绘制的人是否是我
                    throw new BizException("一个户型不能有多个人绘制");
                }

                drawBaseHouse.setBaseHouseId(houseNew.getHouseId());
                drawBaseHouse.setOrigin(DrawBaseHouseConstant.ORIGIN_OLD_COPY); // 老数据临摹标识
                // drawBaseHouse.setHouseCode(baseHouse.getHouseCode());	// 使用老数据的house_code

                // 是否关联过绘制数据
                DrawBaseHouse drawHouse = drawBaseHouseMapper.getDrawHouseByBaseHouseId(drawBaseHouse.getBaseHouseId());
                // 是否新增一条绘制数据和户型老数据关联
                houseNew.setHouseId(drawHouse != null ? drawHouse.getId() : null);
            } else {
                throw new BizException("绘制的户型[" + houseNew.getHouseId() + "(baseHouseId), " + houseNew.getHouseName() + "(houseName)]不存在");
            }
        } else if (houseNew.getHouseId() != null && houseNew.getBaseHouseId() != null) {
            // 重绘制
            return drawNewHouseFilter(houseNew);
        } else {
            throw new BizException("绘制的户型[" + houseNew.getHouseId() + "(baseHouseId), " + houseNew.getHouseName() + "(houseName)]数据异常");
        }

        return drawBaseHouse;
    }

    /**
     * 审核时进行编辑
     *
     * @param houseNew
     * @return
     */
    private DrawBaseHouse drawCheckHouseFilter(DrawBaseHouseNew houseNew) {
        if (null == houseNew.getHouseId()) {
            throw new BizException("绘制的户型[" + houseNew.getHouseId() + "(houseId), " + houseNew.getHouseName() + "(houseName)]数据异常");
        }

        DrawBaseHouse drawBaseHouse = drawBaseHouseMapper.selectByPrimaryKey(houseNew.getHouseId());
        if (drawBaseHouse != null) {
            if (drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_WAIT.toString())
                    || drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_PROCESSING.toString())
                    || drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_FAIL.toString())) {
                // 待烘焙、烘焙中、烘焙失败
                throw new BizException("烘焙中的户型不能绘制");
            }
        } else {
            throw new BizException("绘制的户型[" + houseNew.getHouseId() + "(houseId), " + houseNew.getHouseName() + "(houseName)]不存在");
        }

        return drawBaseHouse;
    }

    /**
     * 毛坯房
     *
     * @param houseNew
     * @return
     */
    private DrawBaseHouse drawRoughHouseFilter(DrawBaseHouseNew houseNew) {
        DrawBaseHouse drawBaseHouse = drawBaseHouseMapper.selectByPrimaryKey(houseNew.getHouseId());
        if (drawBaseHouse != null) {
            if (drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_WAIT.toString())
                    || drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_PROCESSING.toString())
                    || drawBaseHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_FAIL.toString())) {
                // 待烘焙、烘焙中、烘焙失败
                throw new BizException("烘焙中的户型不能绘制");
            }
        }

        return drawBaseHouse == null ? new DrawBaseHouse() : drawBaseHouse;
    }

    private DrawResFile getDrawResFile(DrawBaseHouse house, DrawFileDataNew drawFileDataNew) {
        DrawResFile drawResFile = new DrawResFile();
        drawResFile.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
        drawResFile.setBusinessId(String.valueOf(house.getId()));
        drawResFile.setSysCode(house.getHouseCode());
        drawResFile.setFileOriginalName(drawFileDataNew.getFileOriginalName());
        drawResFile.setFileName(drawFileDataNew.getFileName());
        drawResFile.setFilePath(drawFileDataNew.getFilePath());
        drawResFile.setFileSize(drawFileDataNew.getFileSize());
        drawResFile.setFileSuffix(drawFileDataNew.getFileSuffix());
        drawResFile.setFileType(drawFileDataNew.getFileType());
        drawResFile.setFileCode(house.getHouseCode());
        drawResFile.setCreator(house.getUserId() + "");
        drawResFile.setModifier(house.getUserId() + "");
        drawResFile.setGmtCreate(new Date());
        drawResFile.setFileKey(FileKey.HOUSE_DRAW_FILE.getFileKey());
        return drawResFile;
    }

    /**
     * 获取保存户型的小区信息
     *
     * @param data
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseLiving getBaseLiving(DrawBaseHouseNew data) {
        BaseLiving living = new BaseLiving();
        StringBuilder buf = new StringBuilder();
        if (data.getLivingId() == null || data.getLivingId() == 0) {
            String firstAreaCode = data.getFirstAreaCode();
            String secondAreaCode = data.getSecondAreaCode();
            if (StringUtils.isNotBlank(firstAreaCode)) {
                buf.append(".").append(firstAreaCode).append(".");
            }
            if (StringUtils.isNotBlank(secondAreaCode)) {
                buf.append(secondAreaCode).append(".");
            }
            if (StringUtils.isNotBlank(data.getThirdAreaCode())) {
                buf.append(data.getThirdAreaCode()).append(".");
                living.setAreaId(data.getThirdAreaCode());
            } else {
                living.setAreaId(StringUtils.isNotBlank(secondAreaCode) ? secondAreaCode : firstAreaCode);
            }

            /*
             * v1.0.2
             * 查询areaId下相同小区名的小区, 如果有多条时limit 1
             */
            if (StringUtils.isNoneBlank(data.getLivingName())) {
                BaseLiving queryLiving = baseLivingService.getLivingByName(living.getAreaId(), data.getLivingName());
                if (queryLiving != null) {
                    return queryLiving;
                }
            }

            living.setLivingName(data.getLivingName());
            living.setAreaLongCode(buf.toString());
            living.setCreator(data.getUserId() + "");
            living.setGmtCreate(new Date());
            living.setModifier(data.getUserId() + "");
            living.setGmtModified(new Date());
            living.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
            living.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));

            // livingCode
            LivingCodeBO livingCode = baseLivingService.getLivingCode(living.getAreaId());
            if (livingCode != null) {
                Integer maxCount = livingCode.getMaxCount();
                if (maxCount != null && livingCode.getZipCode() != null) {
                    living.setLivingCode(livingCode.getZipCode() + "A" + (maxCount + 1));
                }
            }

            baseLivingService.insertSelective(living);
        } else {
            // 小区
            living = baseLivingService.getLiving(data.getLivingId());
        }

        return living;
    }

    private DrawResHousePic adapterResHousePic(DrawBaseHouse house, DrawFileDataNew drawFileDataNew) {
        DrawResHousePic pic = new DrawResHousePic();
        pic.setBusinessId(house.getId().intValue());
        pic.setPicHigh(drawFileDataNew.getHeight());
        pic.setPicWeight(drawFileDataNew.getWidth());
        pic.setPicSuffix(drawFileDataNew.getFileSuffix());
        pic.setPicSize(drawFileDataNew.getFileSize());
        pic.setPicPath(drawFileDataNew.getFilePath());
        pic.setPicName(drawFileDataNew.getFileName());
        pic.setPicFormat("");
        pic.setSysCode(house.getHouseCode());
        pic.setPicCode(house.getHouseCode());
        pic.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
        pic.setPicType(drawFileDataNew.getFileType());
        pic.setFileKey(FileKey.BASE_HOUSE_PIC.getFileKey());
        pic.setGmtCreate(new Date());
        return pic;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDrawBaseHouse(Long houseId, Long userId) throws BusinessException {
        DrawBaseHouse drawHouse = validateHouse(houseId, userId);

        // v1.0.10 删除的户型执行删除动作默认为成功
        if (drawHouse.getIsDeleted() == DrawBaseHouseConstant.IS_DELETED) {
            log.warn("{}(houseId), {}", houseId, "户型已被删除过");
            return;
        }

        // 解锁绘制中状态
        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() != 0) {
            if (drawHouse.getHouseStatus().equals(DrawBaseHouseConstant.CHECK_APPLY.toString())) {
                throw new BusinessException(false, ResponseEnum.DELETE_FAIL);
            }
            if (drawHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_WAIT.toString())
                    || drawHouse.getHouseStatus().equals(DrawBaseHouseConstant.HOUSE_BAKE_PROCESSING.toString())) {
                throw new BusinessException(false, ResponseEnum.DELETE_FAIL);
            }

            // 解锁户型绘制状态
            log.info("户型{}(baseHouseId)解锁", drawHouse.getBaseHouseId());
            baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_FACSIMILE, houseId);
        }

        // 删除 draw_base_house
        log.info("删除户型{}(houseId)", houseId);
        drawBaseHouseMapper.deleteById(houseId);
    }

    /**
     * 验证户型
     * @param houseId
     * @param userId
     * @return
     * @throws BusinessException
     */
    private DrawBaseHouse validateHouse(Long houseId, Long userId) throws BusinessException {
        if (null == houseId) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        // 登录USERID
        if (userId == null) {
            userId = HouseAuthUtils.getRequestUserId();
        }

        // 检测数据是否存在
        DrawBaseHouse drawHouse = drawBaseHouseMapper.get(houseId);
        if (null == drawHouse) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // 检测当前登陆者是否有权限操作
        if (!drawHouse.getUserId().equals(userId)) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_BELONG_USER);
        }

        return drawHouse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void checkDrawBaseHouse(DrawBaseHouseCheck check) throws BusinessException {
        // 校验部分参数
        DrawBaseHouse drawHouse = validateAndResponseDrawBaseHouse(check.getUserId(), check.getHouseId(), check.getCheckStatus());
        // 处理户型
        this.handlerCheck(check, drawHouse);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void handlerCheck(DrawBaseHouseCheck check, DrawBaseHouse drawHouse) {

        log.info("户型 {}(houseId)进行审核处理", drawHouse.getId());

        // 修改户型审核状态
        DrawBaseHouse checkHouse = new DrawBaseHouse();
        checkHouse.setHouseStatus(DrawBaseHouseConstant.CHECK_APPLY.toString());
        checkHouse.setRejectReason(check.getRejectReason());
        checkHouse.setId(check.getHouseId());
        // 审核人
        checkHouse.setAuditorId(HouseAuthUtils.getRequestUserId());
        // 审核时间
        checkHouse.setAuditTime(new Date());

        int updateCount = drawBaseHouseMapper.updateDrawHouseStatus3(checkHouse, check.getCheckStatus());
        if (updateCount == 1) {
            // 判断前端需要处理的类型, 通过就生成任务
            if (check.getCheckStatus().intValue() == DrawBaseHouseConstant.HOUSE_BAKE_WAIT) {
                // 烘焙任务
                DrawBakeTask task = getDrawBakeTask(check.getUserId(), drawHouse);
                drawBakeTaskService.saveDrawBakeTask(task);

                // 烘焙任务明细
                List<DrawBakeTaskDetail> drawBakeTaskDetails = getDrawBakeTaskDetails(check.getHouseId(), task);
                // 批量保存烘焙任务明细
                drawBakeTaskService.batchSaveDrawBakeTaskDetail(drawBakeTaskDetails);

                // 已被烘焙数据更改状态 ==> 烘焙中(base_house => 4)
                baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_BAKE, check.getHouseId());

                log.info("{}(houseId)户型添加到烘焙队列中", drawHouse.getId());
            }
        }
    }

    @Override
    public DrawBaseHouse getBaseHouse(Long houseId) {
        return drawBaseHouseMapper.getBaseHouseById(houseId);
    }

    @Override
    public DrawBaseHouseBO loadDrawBaseHouse(Long houseId, Long userId) throws BusinessException {
        // 户型数据
        DrawBaseHouse house = validateHouse(houseId, userId);

        // 户型空间数据
        List<DrawSpaceCommonBO> spaceCommons = drawSpaceCommonService.listSpaceCommon(houseId);
        List<Long> spaceIds = new ArrayList<>();
        for (DrawSpaceCommonBO spaceCommonBO : spaceCommons) {
            spaceIds.add(spaceCommonBO.getSpaceId());
        }

        // 空间产品数据
        Map<Long, List<DrawBaseProductBO>> productMap = getProductDataMap(spaceIds);
        // 组装返回数据对象
        return getDrawBaseHouseBO(house, spaceCommons, productMap);
    }

    private DrawBaseHouseBO getDrawBaseHouseBO(DrawBaseHouse house, List<DrawSpaceCommonBO> spaceCommons, Map<Long, List<DrawBaseProductBO>> productMap) {
        DrawBaseHouseBO response = new DrawBaseHouseBO();
        response.setHouseCode(house.getHouseCode());
        response.setId(house.getId());
        response.setHouseName(house.getHouseName());

        for (DrawSpaceCommonBO space : spaceCommons) {
            List<DrawBaseProductBO> drawBaseProductBOList = productMap.get(space.getSpaceId());
            space.setProductData(drawBaseProductBOList);
        }
        response.setDrawSpaceCommonBOList(spaceCommons);
        return response;
    }

    private DrawBaseHouse validateAndResponseDrawBaseHouse(Long userId, Long houseId, Integer checkStatus) throws BusinessException {
        // 是否有审核的权限
        if (HouseAuthUtils.hasApprovedPermission()) {
            throw new BusinessException(false, ResponseEnum.FORBIDDEN);
        }

        // 简单校验数据
        userId = (userId == null) ? HouseAuthUtils.getRequestUserId() : userId;
        if (null == userId || null == houseId || null == checkStatus) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }
        // 检验审核操作数据合法
        if (checkStatus.intValue() != DrawBaseHouseConstant.HOUSE_BAKE_WAIT && checkStatus.intValue() != DrawBaseHouseConstant.CHECK_REJECT) {
            throw new BusinessException(false, ResponseEnum.CHECK_STATUS_UNKNOW);
        }
        // 检验户型数据是否存在
        DrawBaseHouse house = drawBaseHouseMapper.getBaseHouseById(houseId);
        if (null == house) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        return house;
    }

    private DrawBakeTask getDrawBakeTask(Long userId, DrawBaseHouse house) {
        DrawBakeTask task = new DrawBakeTask();
        task.setHouseCode(house.getHouseCode());
        task.setHouseId(house.getId());
        task.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_INIT);
        task.setUserId(userId);
        task.setGmtCreate(new Date());
        task.setQueueName(getQueueName(house.getId()));
        task.setPriority(5);
        return task;
    }

    /**
     * 获取队列名
     * 1、内部用户绘制的户型，进入内部队列 INTERNAL,
     * 2、外部的队列分成两种
     *  1)、普通的外部户型，进入外部队列(EXTERNAL)
     *  2)、外部用户绘制的毛坯房，进入外部的毛坯房队列(ROUGH_HOUSE)
     * @param houseId
     * @return
     */
    private String getQueueName(Long houseId) {
        // 数据类型
        Map<String, Integer> map = drawBaseHouseMapper.getDataType(houseId);

        Integer origin = map.get("origin");
        Integer dataType = map.get("dataType");

        log.debug("获取烘焙的队列名，{}(origin), {}(dataType)", origin, dataType);

        // 内部户型队列
        if (Objects.isNull(dataType) || dataType.equals(DrawBaseHouseConstant.DATA_PLATFORM)) {
            return BakeTaskQueue.INTERNAL.toString();
        }

        // 毛坯房
        if (DrawBaseHouseConstant.ORIGIN_ROUGH_HOUSE.equals(origin)) {
            return BakeTaskQueue.ROUGH_HOUSE.toString();
        }

        // 普通外部队列
        return BakeTaskQueue.EXTERNAL.toString();
    }

    /**
     * 获取烘焙任务的内容
     *
     * @param houseId
     * @param task
     * @return
     * @throws BusinessException
     */
    private List<DrawBakeTaskDetail> getDrawBakeTaskDetails(Long houseId, DrawBakeTask task) throws BusinessException {
        // 户型下的空间集合
        List<DrawSpaceCommonBO> spaceCommons = drawSpaceCommonService.listSpaceCommon(houseId);
        if (spaceCommons == null || spaceCommons.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.SPACE_NOT_FOUND);
        }

        // 空间主键集合
        List<Long> spaceIds = spaceCommons.stream().map(DrawSpaceCommonBO::getSpaceId).collect(Collectors.toList());

        // 获取空间下的产品信息
        Map<Long, List<DrawBaseProductBO>> productMap = getProductDataMap(spaceIds);

        // objectMapper null值不参与序列化
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 烘焙任务详情集合
        List<DrawBakeTaskDetail> drawBakeTaskDetails = new ArrayList<>();
        for (DrawSpaceCommonBO spaceCommonBO : spaceCommons) {
            DrawBakeTaskDetail taskDetail = new DrawBakeTaskDetail();

            taskDetail.setSpaceCode(spaceCommonBO.getSpaceCode());
            taskDetail.setSpaceFileIds(String.valueOf(spaceCommonBO.getFileId()));
            taskDetail.setSpaceFilePath(spaceCommonBO.getFilePath());
            taskDetail.setSpaceId(spaceCommonBO.getSpaceId());
            taskDetail.setTaskId(task.getId());
            taskDetail.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_INIT);

            // 空间下的产品集合, 产品json串信息
            String jsonProductInfo = getJsonProductInfo(objectMapper, productMap.get(spaceCommonBO.getSpaceId()));
            taskDetail.setProductData(jsonProductInfo);

            // add
            drawBakeTaskDetails.add(taskDetail);
        }

        return drawBakeTaskDetails;
    }

    /**
     * 空间下的产品集合, 产品json串信息
     *
     * @param objectMapper
     * @param product
     * @return
     */
    private String getJsonProductInfo(ObjectMapper objectMapper, List<DrawBaseProductBO> product) {
        if (product == null || product.isEmpty()) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }
    }

    /**
     * 获取空间产品信息
     *
     * @param spaceIds
     * @return
     */
    private Map<Long, List<DrawBaseProductBO>> getProductDataMap(List<Long> spaceIds) {
        // 空间id为key，空间下的产品信息集合为value
        Map<Long, List<DrawBaseProductBO>> productMap = new HashMap<>();
        // 空间下的所有的商品信息
        List<DrawBaseProductBO> products = drawBaseProductService.listBaseProductBo(spaceIds, 1);
        // List<DrawBaseProductBO> products = drawBaseProductService.listBaseProductBo(spaceIds, null);
        if (products != null && !products.isEmpty()) {
            productMap = products.stream().collect(Collectors.groupingBy(DrawBaseProductBO::getSpaceId));
        }

        return productMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DrawBaseHouseSubmitDTO submit(String data, UserLoginBO loginBO) throws DrawBusinessException {
        // 验证数据
        DrawBaseHouseSubmitDTO dtoNew = validateSubmitDTO(data);
        if (loginBO == null) {
            log.warn("用户信息为空");
            loginBO = HouseAuthUtils.getUnknownUser();
        }

        // 清空旧数据处理
        this.handlerSubmitHouse(dtoNew.getHouseId().longValue());

        // 保存所有信息(空间/样板房/产品)
        dtoNew = this.saveDrawBaseHouseBySubmit(dtoNew, loginBO);

        // 提交后相当于发起审核操作，保存未提交都不能再审核列表中展示
        drawBaseHouseMapper.updateDrawHouseStatus(DrawBaseHouseConstant.CHECK_APPLY, null, dtoNew.getHouseId().longValue());

        // 用户个人户型数据(普通绘制员、内部演示员绘制的数据)直接跳过审核权限，进入烘焙阶段
        this.handlerJumpHouseCheck(dtoNew, loginBO);

        return dtoNew;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void submitV2(String data) throws DrawBusinessException {
        DrawBaseHouseSubmitDTO dtoNew = null;
        try {
            UserLoginBO loginBO = HouseAuthUtils.getUserLoginBO();
            // 验证数据
            dtoNew = validateSubmitDTO(data);

            // 清空旧数据处理
            this.handlerSubmitHouse(dtoNew.getHouseId().longValue());

            /**
             * v2 submit 处理
             */
            drawBaseHouseServiceV2.submit(dtoNew, loginBO);

            // 提交后相当于发起审核操作，保存未提交都不能再审核列表中展示
            drawBaseHouseMapper.updateDrawHouseStatus(DrawBaseHouseConstant.CHECK_APPLY, null, dtoNew.getHouseId().longValue());

            // 用户个人户型数据(普通绘制员、内部演示员绘制的数据)直接跳过审核权限，进入烘焙阶段
            this.handlerJumpHouseCheck(dtoNew, loginBO);

            // 处理量房status
            drawVolumeRoomService.handlerVolumeRoom(dtoNew.getDrawHouse(), VolumeRoomConstant.PERFECTED.getStatus(), VolumeRoomConstant.PARSING_SUCCESS.getStatus());
        } catch (Exception e) {
            // 异常时，删除上传的资源文件
            resHandlerErrorService.handlerErrorWithSubmit(dtoNew, data);

            throw e;
        }
    }

    /**
     * 验证提交户型的DTO
     *
     * @param data
     * @return
     */
    private DrawBaseHouseSubmitDTO validateSubmitDTO(String data) {
        if (StringUtils.isEmpty(data)) {
            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
        }

        DrawBaseHouseSubmitDTO dtoNew;
        try {
            dtoNew = new ObjectMapper().readValue(data, DrawBaseHouseSubmitDTO.class);
        } catch (Exception e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }

        if (dtoNew == null || dtoNew.getHouseId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        // 验证户型是否存在
        DrawBaseHouse drawHouse = drawBaseHouseMapper.getBaseHouseById(dtoNew.getHouseId().longValue());
        if (drawHouse == null) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }
        // 绘制户型
        dtoNew.setDrawHouse(drawHouse);
        dtoNew.setDataType(Objects.isNull(drawHouse.getDataType()) ? DrawBaseHouseConstant.DATA_PLATFORM : drawHouse.getDataType());

        return dtoNew;
    }

    /**
     * 保存所有数据(空间/样板房/产品/样板房产品表)
     *
     * @param loginBO
     * @param dto
     * @return
     * @throws DrawBusinessException
     * @author huangsongbo
     */
    private DrawBaseHouseSubmitDTO saveDrawBaseHouseBySubmit(DrawBaseHouseSubmitDTO dto, UserLoginBO loginBO) throws DrawBusinessException {
        // 创建空间数据(同时更新对应文件信息)
        dto = drawSpaceCommonService.saveDrawSpaceCommonBySubmit(dto, loginBO);

        // 创建样板房数据(同时更新对应文件信息)
        dto = drawDesignTempletService.saveDrawDesignTempletBySubmit(dto, loginBO);

        // 创建基本产品数据(同时更新对应文件信息)
        dto = drawBaseProductService.saveDrawBaseProductBySubmit(dto, loginBO);

        // 创建样板房产品表(同时更新对应文件信息)
        dto = drawDesignTempletProductService.saveDesignTempletProductBySubmit(dto, loginBO);

        return dto;
    }

    /**
     * 普通绘制员、内部演示员直接跳过审核权限，进入烘焙阶段
     *
     * @param dtoNew
     */
    private void handlerJumpHouseCheck(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginUser) {
        if (!DrawBaseHouseConstant.DATA_PERSONAL.equals(dtoNew.getDataType())) {
            log.info("{}(houseId)是内部员绘制户型，需要审核户型流程", dtoNew.getHouseId());
            return;
        }

        if (!HouseAuthUtils.hasJumpHouseCheckPermission()) {
            log.info("{}(userId)用户权限不足，无法跳过审核户型流程", loginUser.getId());
            return;
        }

        DrawBaseHouse drawHouse = drawBaseHouseMapper.selectByPrimaryKey(dtoNew.getHouseId().longValue());
        if (drawHouse == null) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        log.info("{}(houseId)户型跳过审核户型流程，直接进入烘焙队列", drawHouse.getId());

        DrawBaseHouseCheck drawCheck = new DrawBaseHouseCheck();
        drawCheck.setHouseId(drawHouse.getId());
        drawCheck.setCheckStatus(DrawBaseHouseConstant.HOUSE_BAKE_WAIT);
        drawCheck.setUserId(HouseAuthUtils.getRequestUserId());

        // 审核户型流程处理
        this.handlerCheck(drawCheck, drawHouse);
    }

    @Override
    public Map<String, Object> getHouses(DrawBaseHouseSearch query) {
        Map<String, Object> dataMap = new HashMap<>();
        // 判断用户的户型绘制角色
        boolean checkUser = false;
        String role = checkUserDrawRole();
        if (HouseRole.COMMON_HOUSE_DRAW.equals(role)) {
            checkUser = true;
        }
        if (checkUser) {
            query.setIsPublic(1);
        }
        query.setPageNum((query.getPageNum() - 1) * query.getPageSize());

        int count;
        List<DrawBaseHouseBO> list;

        if (StringUtils.isEmpty(query.getAreaCode()) && query.getLivingId() == null) {
            count = drawBaseHouseMapper.selectCountAllHouses();
        } else {
            count = drawBaseHouseMapper.selectConutHouses(query);
        }

        if (count > 0) {
            list = drawBaseHouseMapper.getHouses(query);
            list.stream().filter(l -> l.getPicPath() == null).forEach(r -> {
                String picPath = drawBaseHouseMapper.getSnapPicPathById(r.getId());
                r.setPicPath(picPath);
            });
            for (DrawBaseHouseBO drawBaseHouseBO : list) {
                if (StringUtils.isEmpty(drawBaseHouseBO.getDealStatus())) {
                    drawBaseHouseBO.setDealStatus(DrawBaseHouseConstant.DEAL_STATUS_FACSIMILE);
                }
                if (checkUser) {
                    if (query.getUserId().equals(drawBaseHouseBO.getUserId())) {
                        drawBaseHouseBO.setDealStatus(DrawBaseHouseConstant.DEAL_STATUS_EDIT);
                    } else {
                        drawBaseHouseBO.setDealStatus(DrawBaseHouseConstant.DEAL_STATUS_FACSIMILE);
                    }
                } else {
                    // 自己继续修改
                    if (DrawBaseHouseConstant.DEAL_STATUS_RERUNNING.equals(drawBaseHouseBO.getDealStatus())
                            && query.getUserId().equals(drawBaseHouseBO.getUserId())) {
                        drawBaseHouseBO.setDealStatus(DrawBaseHouseConstant.DEAL_STATUS_EDIT);
                    }
                }

//                // v1.1.2  检验编辑权限
//                // add by songjianming@sanduspace.cn on 2018/7/31
//                if (!HouseAuthUtils.hasHouseModification() &&
//                        DrawBaseHouseConstant.DEAL_STATUS_EDIT.equals(drawBaseHouseBO.getDealStatus())) {
//                    // 禁止编辑
//                    drawBaseHouseBO.setDealStatus(DrawBaseHouseConstant.DEAL_STATUS_DISABLE);
//                }
            }

            dataMap.put("list", list);
            dataMap.put("total", count);
        }

        return dataMap;
    }

    private String checkUserDrawRole() {
        UserLoginBO userLoginBO = HouseAuthUtils.getUserLoginBO();
        Set<String> roles = userLoginBO.getRoles();
        if (roles != null) {
            if (!roles.contains(HouseRole.COMMON_HOUSE_DRAW) && !roles.contains(HouseRole.INTERNAL_HOUSE_DRAW)) {
                throw new BizException("您没有户型绘制员的角色");
            }
            if ((roles.contains(HouseRole.COMMON_HOUSE_DRAW) && roles.contains(HouseRole.INTERNAL_HOUSE_DRAW))
                    || roles.contains(HouseRole.INTERNAL_HOUSE_DRAW)) {
                return HouseRole.INTERNAL_HOUSE_DRAW;
            } else {
                return HouseRole.COMMON_HOUSE_DRAW;
            }
        }
        return HouseRole.COMMON_HOUSE_DRAW;
    }

    public DrawHouseAndAreaVO facsimileHouse(Long houseId, String type) {
        DrawBaseHouseBO drawBaseHouseBO = null;
        switch (type) {
            case "facsimile":
                drawBaseHouseBO = drawBaseHouseMapper.selectHouseAndPicPathById(houseId);
                break;
            case "continueDraw":
                drawBaseHouseBO = drawBaseHouseMapper.selectDrawHouseRestoreFile(houseId);
                // 量房数据
                if (drawBaseHouseBO != null &&  !PlatformType.SANDU.getType().equals(drawBaseHouseBO.getPlatformType())) {
                    List<VolumeRoomHouseBO> volumeRoom = drawVolumeRoomMapper.listVolumeRoomByHouseId(Lists.newArrayList(drawBaseHouseBO), VolumeRoomConstant.DEFAULTS.getStatus());
                    if (volumeRoom != null && !volumeRoom.isEmpty()) {
                        drawBaseHouseBO.setFilePath(volumeRoom.get(0).getLfFilePath());
                    }
                }

                break;
            case "edit":
                drawBaseHouseBO = drawBaseHouseMapper.selectHouseAndRestoreFile(houseId);
                // v1.1.1 重置的户型，没有还原文件，取绘制表里的还原文件
                if (drawBaseHouseBO != null &&  drawBaseHouseBO.getFilePath() == null) {
                    DrawHouseResouceBO houseFileResource = drawBaseHouseMapper.getHouseFileResource(houseId);
                    drawBaseHouseBO.setFilePath(houseFileResource == null ? null : houseFileResource.getRestoreFilePath());
                }
                break;
            default:
                break;
        }

        DrawHouseAndAreaVO drawHouseAndAreaVO = new DrawHouseAndAreaVO();
        drawHouseAndAreaVO.setDrawBaseHouseBO(drawBaseHouseBO);
        drawHouseAndAreaVO.setBaseAreaBO(handlerAreaByLivingId(drawBaseHouseBO));
        return drawHouseAndAreaVO;
    }

    /**
     * v1.1.1
     * 1、优化省市区查询，根据小区检索其所在的省市区（以后版本需要加入redis缓存）
     * 、而不检索出所有的省市区进行对比，这样非常低效；
     * 2、修复临摹时，省市区某个一个值引起 NullPointerException 问题
     *
     * @param drawBaseHouseBO
     * @return
     */
    private BaseAreaBO handlerAreaByLivingId(DrawBaseHouseBO drawBaseHouseBO) {
        if (drawBaseHouseBO == null || drawBaseHouseBO.getAreaId() == null) {
            return new BaseAreaBO();
        }

        BaseAreaLivingBO livingBO = baseLivingMapper.listAreaByLivingId(drawBaseHouseBO.getAreaId());
        if (livingBO == null) {
            return new BaseAreaBO();
        }

        // 区
        BaseAreaBO distArea = new BaseAreaBO();
        distArea.setAreaCode(livingBO.getDistCode());
        distArea.setAreaName(livingBO.getDistName());

        // 市
        BaseAreaBO cityArea = new BaseAreaBO();
        cityArea.setAreaCode(livingBO.getCityCode());
        cityArea.setAreaName(livingBO.getCityName());
        cityArea.setChildAreas(Lists.newArrayList(distArea));

        // 省
        BaseAreaBO proArea = new BaseAreaBO();
        proArea.setAreaCode(livingBO.getProCode());
        proArea.setAreaName(livingBO.getProName());
        proArea.setChildAreas(Lists.newArrayList(cityArea));

        return proArea;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> callback(String jsonStr) throws DrawBusinessException {
        Map<String, Object> map = new HashMap<>();

        // 转化json
        DrawBaseHouseControllerCallBackDTO dto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 如果是空对象的时候,不抛异常
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 反序列化的时候如果多了其他属性,不抛出异常
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            dto = objectMapper.readValue(jsonStr, DrawBaseHouseControllerCallBackDTO.class);

//			dto = new ObjectMapper().readValue(jsonStr, DrawBaseHouseControllerCallBackDTO.class);
        } catch (Exception e) {
            throw new DrawBusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }

        if (dto == null) {
            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        log.info("正在处理烘焙任务 {}(taskDetailId)", dto.getTaskDetailId());

        // 烘焙失败处理
        if (Objects.equals(dto.getStatus(), BakeCallbackStatus.FAILURE.getStatus())) {
            this.handlerBakeTaskFailed(dto);

            String msg = Utils.replace(ResponseEnum.BAKE_TASK_FIAL.getMessage(), Objects.toString(dto.getTaskDetailId()));
            map.put("msg", msg);
        } else {
            // 以下是烘焙成功的处理
            // 更新挂节点/模型文件等信息
            dto = this.updateAllData(dto);

            // 转换数据
            Map<Long, Long> transformAllData = this.transformAllData(dto.getTaskDetailId());
            map.put("msg", "烘焙成功");

            // 推送搜索服务产品
            if (!transformAllData.isEmpty()) {
                Collection<Long> values = transformAllData.values();
                this.doSyncToIndex(SyncMessage.ACTION_ADD, new ArrayList<>(values));
                log.info("推送搜索产品，push data => {}", Arrays.toString(values.toArray()));
            }
        }

        log.info("{}(taskDetailId)烘焙任务处理完成", dto.getTaskDetailId());

        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String callbackV2(DrawBaseHouseCallbackDTO dtoNew) {
        return drawBaseHouseServiceV2.callback(dtoNew);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void doSyncToIndex(Integer messageAction, List<Long> ids) {
        try {
            List<Map> content = ids.stream().map(item -> {
                HashMap<String, Long> tmp = new HashMap<>(1);
                tmp.put("id", item);
                return tmp;
            }).collect(Collectors.toList());
            SyncMessage message = new SyncMessage();
            message.setAction(messageAction);
            message.setMessageId("house-draw-" + System.currentTimeMillis());
            message.setModule(SyncMessage.MODULE_PRODUCT);
            message.setPlatformType(SyncMessage.PlatFormType.pcHouseDraw.toString());
            message.setObject(content);

            queueService.send(message);
        } catch (Exception e) {
            // 推送服务不参与事务处理，不抛异常
            log.error("推送搜索异常 => {}", e.getMessage(), e);
        }
    }

    /**
     * 转换数据
     *
     * @param taskDetailId
     * @throws DrawBusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<Long, Long> transformAllData(Long taskDetailId) throws DrawBusinessException {
        String logMes = "function:DrawBaseHouseServiceImpl.transformAllData->";

        // 参数验证 ->start
        if (taskDetailId == null) {
            log.error(logMes + "taskDetailId = null");
            throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSESERVICEIMPL_TRANSFORMALLDATA_FAILED);
        }

        DrawBakeTaskDetail drawBakeTaskDetail = drawBakeTaskDetailService.get(taskDetailId);
        if (drawBakeTaskDetail == null) {
            log.error(logMes + "drawBakeTaskDetail = null; taskDetailId = " + taskDetailId);
            throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSESERVICEIMPL_TRANSFORMALLDATA_FAILED);
        }

        DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.get(drawBakeTaskDetail.getSpaceId());
        if (drawSpaceCommon == null) {
            log.error(logMes + "drawSpaceCommon = null; drawSpaceCommonId = " + drawBakeTaskDetail.getSpaceId());
            throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSESERVICEIMPL_TRANSFORMALLDATA_FAILED);
        }

        List<DrawDesignTemplet> drawDesignTempletList = drawDesignTempletService.findAllBySpaceCommonId(drawBakeTaskDetail.getSpaceId());
        if (drawDesignTempletList == null || drawDesignTempletList.size() == 0) {
            log.error(logMes + "drawDesignTempletList = null or size = 0; drawSpaceCommonId = " + drawBakeTaskDetail.getSpaceId());
            throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSESERVICEIMPL_TRANSFORMALLDATA_FAILED);
        }

        DrawBaseHouse drawBaseHouse = drawBaseHouseService.get(drawSpaceCommon.getDrawBaseHouseId());
        if (drawBaseHouse == null) {
            log.error(logMes + "drawBaseHouse = null; drawBaseHouseId = " + drawSpaceCommon.getDrawBaseHouseId());
            throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSESERVICEIMPL_TRANSFORMALLDATA_FAILED);
        }
        // 参数验证 ->end

        // TODO 清空正式表数据
        this.handlerCallbackHouse(drawBaseHouse.getId(), drawBakeTaskDetail.getTaskId());

        // 转换户型数据
        Map<Long, Long> drawBaseHouseTransformMap = new HashMap<>();
        if (drawBaseHouse.getBaseHouseId() != null && drawBaseHouse.getBaseHouseId() > 0) {
            // 标识该drawBaseHouse已经被转换过
            drawBaseHouseTransformMap.put(drawBaseHouse.getId(), drawBaseHouse.getBaseHouseId());
            log.info("户型 {}(drawBaseHouseId)已经被转换过, 不需要再转化", drawBaseHouse.getId());
        } else {
            drawBaseHouseTransformMap = this.transformToBaseHouse(drawBaseHouse);
        }

        // 同步户型资源
        this.transformHouseResource(drawBaseHouse, drawBakeTaskDetail.getTaskId());

        // 转换产品数据
        Map<Long, Long> drawBaseProductTransformMap = new HashMap<>();
        for (DrawDesignTemplet drawDesignTemplet : drawDesignTempletList) {
            List<DrawBaseProduct> drawBaseProductList = drawBaseProductService.findAllByDrawDesignTempletId(drawDesignTemplet.getId());

            // 剔除软装白膜(因为正是表中已经存在了软装白膜产品数据,所以不需要重复新增) ->start
            if (drawBaseProductList != null && drawBaseProductList.size() > 0) {
                for (int index = 0; index < drawBaseProductList.size(); index++) {
                    DrawBaseProduct drawBaseProduct = drawBaseProductList.get(index);
                    if (DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(drawBaseProduct.getCreateProductStatus())) {
                        log.debug("跳过软装产品 {}(productId)处理", drawBaseProduct.getId());
                        drawBaseProductList.remove(index);
                        index--;
                    }
                }
            }
            // 提出软装白膜(因为正是表中已经存在了软装白膜产品数据,所以不需要重复新增) ->end

            Map<Long, Long> drawBaseProductTransformMapItem = drawBaseProductService.transformToBaseProduct(drawBaseProductList);
            drawBaseProductTransformMap.putAll(drawBaseProductTransformMapItem);
        }

        // 转换空间数据
        Map<Long, Long> drawSpaceCommonTransformMap = drawSpaceCommonService.transformToSpaceCommon(drawSpaceCommon,
                drawBaseHouseTransformMap.get(drawBaseHouse.getId()));

        // 转换样板房数据
        Map<Long, Long> drawDesignTempletTransformMap = drawDesignTempletService.transformToDesignTemplet(drawDesignTempletList,
                drawSpaceCommonTransformMap.get(drawSpaceCommon.getId()));

        // 转换样板房产品数据
        for (DrawDesignTemplet drawDesignTemplet : drawDesignTempletList) {
            List<DrawDesignTempletProduct> drawDesignTempletProductList = drawDesignTempletProductService.findAllByDesignTempletId(drawDesignTemplet.getId());
            drawDesignTempletProductService.transformToDesignTempletProduct(drawDesignTempletProductList, drawDesignTempletTransformMap.get(drawDesignTemplet.getId()),
                    drawBaseProductTransformMap);
        }

        // v1.0.7处理相邻样板房的跳跃点信息(埋点)
        // created by songjianming@sanduspace.cn on 2018/5/24
        designTempletJumpPositionRelService.transformJumpPositionRel(drawBaseHouse);

        // 处理户型的状态和资源
        // created by songjianming@sanduspace.cn on 2018/2/6
        this.handlerHouseStatus(drawBaseHouse, taskDetailId);

        return drawBaseProductTransformMap;
    }

    /**
     * 处理户型状态
     *
     * @param drawHouse
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerHouseStatus(DrawBaseHouse drawHouse, Long taskDetailId) {
        if (drawHouse != null && taskDetailId != null) {
            // 剩余需要烘焙的空间
            int taskCount = drawBakeTaskService.getTaskDetailCount(taskDetailId);
            if (taskCount <= 1) {
                // draw_base_house ==> house_status(HOUSE_BAKE_SUCCESS)
                drawBaseHouseMapper.updateDrawHouseStatus(DrawBaseHouseConstant.HOUSE_BAKE_SUCCESS, null, drawHouse.getId());
                // 主任务
                drawBakeTaskService.updateTaskStatusByTaskDetailId(DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS, taskDetailId);

                // base_house的所有的空间烘焙完成 deal_status(DEAL_STATUS_EDIT)
                baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_EDIT, drawHouse.getId());
                log.info("{}(houseId)户型下的所有空间烘焙完成", drawHouse.getId());
            }

            // 更新任务详情
            drawBakeTaskDetailService.updateTaskDetailStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS, taskDetailId);
        } else {
            log.warn("烘焙的户型状态处理异常, drawHouse => {}, taskDetailId => {}", drawHouse, taskDetailId);
        }
    }

    /**
     * 转化户型资源
     *
     * @param drawHouse
     * @param taskId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transformHouseResource(DrawBaseHouse drawHouse, Long taskId) {
        if (drawHouse == null) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // 是否第一个空间烘焙
        int taskCount = drawBakeTaskService.getBakeSuccessTask(taskId);
        if (taskCount != DrawBaseHouseConstant.EMPTY_DATA_INIT_FLAG) {
            log.info("户型 {}(houseId)的资源已被同步过，不需要同步", drawHouse.getId());
            return;
        }

        this.updateHouseResource(drawHouse);
    }

    /**
     * 截图、还原文件
     *
     * @param drawHouse
     */
    private void updateHouseResource(DrawBaseHouse drawHouse) {
        if (drawHouse == null) {
            log.warn("户型的不存在");
            return;
        }

        log.info("开始同步绘制户型{}(houseId)图片资源", drawHouse.getId());

        if (drawHouse.getBaseHouseId() == null || drawHouse.getBaseHouseId() <= 0) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // base_house
        BaseHouse house = baseHouseMapper.selectByPrimaryKey(drawHouse.getBaseHouseId());
        if (house == null) {
            log.error("户型 {}(baseHouseId)不存在或被删除", drawHouse.getBaseHouseId());
            // 户型被删除不处理
            // throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
            return;
        }

        // 绘制户型的资源 => pic 、snap_pic、restore_file
        DrawHouseResouceBO drawResource = drawBaseHouseMapper.getDrawHouseFileResource(drawHouse.getId());
        if (drawResource == null) {
            log.warn("户型 {}(houseId)的资源不存在", drawHouse.getId());
            return;
        }

        // update snap_pic_id
        Long snapPicId = house.getSnapPicId();
        if (snapPicId != null && snapPicId > 0) {
            if (!StringUtils.isEmpty(drawResource.getSnapPicPath())) {
                ResHousePic snap = new ResHousePic();
                snap.setId(snapPicId);
                snap.setPicPath(drawResource.getSnapPicPath());
                resHousePicMapper.updateByPrimaryKeySelective(snap);
            }
        } else {
            // 同步 snap_pic_id
            copySnapPicById(drawResource.getSnapPicId(), house);
        }

        // origin => 1 是手动绘制、导入图临摹需要同步户型图，老数据不需要同步户型图
        if (drawHouse.getOrigin() != null && !drawHouse.getOrigin().equals(DrawBaseHouseConstant.ORIGIN_OLD_COPY)) {
            // pic_res_1id
            Integer picRes1Id = house.getPicRes1Id();
            if (picRes1Id != null && picRes1Id > 0) {
                if (!StringUtils.isEmpty(drawResource.getPicRes1Path())) {
                    ResHousePic pic = new ResHousePic();
                    pic.setId(picRes1Id.longValue());
                    pic.setPicPath(drawResource.getPicRes1Path());
                    resHousePicMapper.updateByPrimaryKeySelective(pic);
                }
            } else {
                // 同步 pic_res1_id
                copyPicRes1ById(Long.valueOf(Objects.toString(drawResource.getPicRes1Id(), "0")), house, drawHouse.getOrigin());
            }
        }

        // restore_file_id
        Long restoreFileId = house.getRestoreFileId();
        if (restoreFileId != null && restoreFileId > 0) {
            if (!StringUtils.isEmpty(drawResource.getRestoreFilePath())) {
                ResFile restoreFile = new ResFile();
                restoreFile.setId(restoreFileId);
                restoreFile.setFilePath(drawResource.getRestoreFilePath());
                resFileMapper.updateByPrimaryKeySelective(restoreFile);
            }
        } else {
            // 同步 restore file
            copyRestoreFileById(drawResource.getRestoreFileId(), house);
        }

        // 更新资源
        if ((house.getPicRes1Id() != null && house.getPicRes1Id() > 0)
                || (house.getSnapPicId() != null && house.getSnapPicId() > 0)
                || (house.getRestoreFileId() != null && house.getRestoreFileId() > 0)) {
            baseHouseMapper.updateHouseResouce(house);
            log.info("update sql户型{}(houseId)的资源picResId = {}, snapPicId = {}, restoreFileId = {}", house.getId(),
                    house.getPicRes1Id(), house.getSnapPicId(), house.getRestoreFileId());
        }

        log.info("绘制户型{}(houseId)同步图片资源结束", drawHouse.getId());
    }

    /**
     * copy restore file
     *
     * @param restoreFileId
     */
    private void copyRestoreFileById(Long restoreFileId, BaseHouse house) {
        // restore_file_id;
        if (restoreFileId != null && restoreFileId != 0) {
            DrawResFile drawFile = drawResFileMapper.selectByPrimaryKey(restoreFileId);
            if (drawFile != null) {
                ResFile file = new ResFile();
                try {
                    drawFile.setId(null);
                    BeanUtils.copyProperties(file, drawFile);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("同步户型 {}(houseId)还原文件资源异常", house.getId(), e);
                    throw new BusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("同步户型" + house.getId() + "(houseId)还原文件资源异常"));
                }

                resFileMapper.insertSelective(file);
                house.setRestoreFileId(file.getId());
            }
        }
    }

    /**
     * copy snap pic
     *
     * @param snapPicId
     * @param house
     */
    private void copySnapPicById(Long snapPicId, BaseHouse house) {
        if (snapPicId != null && snapPicId != 0) {
            DrawResHousePic drawSnap = drawResHousePicMapper.getDrawResHousePic(snapPicId);
            if (drawSnap != null) {
                ResHousePic snap = new ResHousePic();
                try {
                    drawSnap.setId(null);
                    BeanUtils.copyProperties(snap, drawSnap);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("同步户型 {}(houseId)截图资源异常", house.getId(), e);
                    throw new BusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("同步户型" + house.getId() + "(houseId)截图资源异常"));
                }

                resHousePicMapper.insertSelective(snap);
                house.setSnapPicId(snap.getId());
            }
        }
    }

    /**
     * copy pic res1
     *
     * @param picRes1Id
     */
    private void copyPicRes1ById(Long picRes1Id, BaseHouse house, Integer origin) {
        // pic_res1_id
        // origin => 1 是手动绘制、导入图临摹需要同步户型图，老数据不需要同步户型图
        if (origin != null && !origin.equals(DrawBaseHouseConstant.ORIGIN_OLD_COPY)) {
            if (picRes1Id != null && picRes1Id != 0) {
                DrawResHousePic drawPic = drawResHousePicMapper.getDrawResHousePic(picRes1Id);
                if (drawPic == null) {
                    log.warn("未找到 {}(picRes1Id)的户型图资源", picRes1Id);
                    return;
                }

                ResHousePic pic = new ResHousePic();
                try {
                    drawPic.setId(null);
                    BeanUtils.copyProperties(pic, drawPic);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("同步户型 {}(houseId)图资源异常", house.getId(), e);
                    throw new BusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("同步户型" + house.getId() + "(houseId)图资源异常"));
                }

                resHousePicMapper.insertSelective(pic);
                house.setPicRes1Id(pic.getId().intValue());
            }
        }
    }

    /**
     * 更新模型文件/挂节点信息
     *
     * @param dtoUpdate
     * @throws DrawBusinessException
     * @author huangsongbo
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DrawBaseHouseControllerCallBackDTO updateAllData(DrawBaseHouseControllerCallBackDTO dtoUpdate) throws DrawBusinessException {
        if (dtoUpdate == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("参数为空"));
        }

        Long taskDetailId = dtoUpdate.getTaskDetailId();
        if (taskDetailId == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("烘焙任务ID为空"));
        }

        DrawBakeTaskDetail drawBakeTaskDetail = drawBakeTaskDetailService.get(taskDetailId);
        if (drawBakeTaskDetail == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("不存在" + taskDetailId + "烘焙任务"));
        }

        Long drawSpaceCommonId = drawBakeTaskDetail.getSpaceId();
        if (drawSpaceCommonId == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("烘焙的空间不存在taskDetailId=" + taskDetailId));
        }

        DrawDesignTempletDTO drawDesignTempletDTO = dtoUpdate.getDrawDesignTempletDTO();
        if (drawDesignTempletDTO == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("参数drawDesignTempletDTO错误！"));
        }

        // 处理资源
        DrawDesignTemplet drawDesignTemplet = this.updateResource(drawDesignTempletDTO, drawSpaceCommonId);

        // 处理产品信息
        this.updateProduct(drawDesignTempletDTO, drawDesignTemplet);

        return dtoUpdate;
    }

    /**
     * 处理资源
     *
     * @param drawDesignTempletDTO
     * @param drawSpaceCommonId
     * @return
     */
    @Override
    public DrawDesignTemplet updateResource(DrawDesignTempletDTO drawDesignTempletDTO, Long drawSpaceCommonId) {
        DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.getSpaceCommon(drawSpaceCommonId);
        if (drawSpaceCommon == null) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("更新数据失败"));
        }

        // 处理空间灯光文件
        Utils.requireGreaterZero(drawDesignTempletDTO.getDaylightPcU3dModelId(), ResponseEnum.SPACE_DAYLIGHT_EMPTY.getMessage());
        Utils.requireGreaterZero(drawDesignTempletDTO.getDusklightPcU3dModelId(), ResponseEnum.SPACE_DUSKLIGHT_EMPTY.getMessage());
        Utils.requireGreaterZero(drawDesignTempletDTO.getNightlightPcU3dModelId(), ResponseEnum.SPACE_NIGHTLIGHT_EMPTY.getMessage());

        // 处理空间
        drawSpaceCommon = new DrawSpaceCommon();
        drawSpaceCommon.setId(drawSpaceCommonId);

        // 白天
        drawSpaceCommon.setDaylightU3dModelId(Integer.valueOf(drawDesignTempletDTO.getDaylightPcU3dModelId() + ""));
        // 黄昏
        drawSpaceCommon.setDusklightU3dModelId(Integer.valueOf(drawDesignTempletDTO.getDusklightPcU3dModelId() + ""));
        // 晚上
        drawSpaceCommon.setNightlightU3dModelId(Integer.valueOf(drawDesignTempletDTO.getNightlightPcU3dModelId() + ""));
        drawSpaceCommonService.update(drawSpaceCommon);

        // 更新样板房数模型、配置文件数据
        List<DrawDesignTemplet> drawDesignTempletList = drawDesignTempletService.findAllBySpaceCommonId(drawSpaceCommonId);
        if (drawDesignTempletList == null || drawDesignTempletList.isEmpty()) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("户型绘制样板房信息没有找到: drawSpaceCommonId = " + drawSpaceCommonId));
        }

        Utils.requireGreaterZero(drawDesignTempletDTO.getDesignTempletPcModelU3dId(), ResponseEnum.DESIGN_TEMPLET_U3DMODLE_EMPTY.getMessage());
        Utils.requireGreaterZero(drawDesignTempletDTO.getDesignTempletConfigFileId(), ResponseEnum.DESIGN_TEMPLET_CONFIG_EMPTY.getMessage());

        // update draw design templet
        DrawDesignTemplet drawDesignTemplet = new DrawDesignTemplet();
        drawDesignTemplet.setId(drawDesignTempletList.get(0).getId());
        drawDesignTemplet.setPcModelU3dId(Integer.valueOf(drawDesignTempletDTO.getDesignTempletPcModelU3dId() + ""));
        drawDesignTemplet.setConfigFileId(Integer.valueOf(drawDesignTempletDTO.getDesignTempletConfigFileId() + ""));
        // update
        drawDesignTempletService.update(drawDesignTemplet);

        return drawDesignTemplet;
    }

    /**
     * 处理产品信息
     *
     * @param drawDesignTempletDTO
     * @param drawDesignTemplet
     */
    @Override
    public void updateProduct(DrawDesignTempletDTO drawDesignTempletDTO, DrawDesignTemplet drawDesignTemplet) {
        // 更新样板房产品表/基本产品表
        DrawDesignTempletProductSearch drawDesignTempletProductSearch = new DrawDesignTempletProductSearch();
        drawDesignTempletProductSearch.setDesignTempletId(Integer.valueOf(drawDesignTemplet.getId() + ""));
        List<DrawDesignTempletProduct> drawDesignTempletProductList = drawDesignTempletProductService.findAllBySearch(drawDesignTempletProductSearch);
        if (drawDesignTempletProductList == null || drawDesignTempletProductList.size() == 0) {
            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("户型绘制样板房产品明细没有找到: drawDesignTempletId = " + drawDesignTemplet.getId()));
        }

        // 验证产品数据
        Map<String, DrawDesignTempletProduct> drawDesignTempletProductMap = new HashMap<>();
        for (DrawDesignTempletProduct drawDesignTempletProduct : drawDesignTempletProductList) {
            if (StringUtils.isEmpty(drawDesignTempletProduct.getUniqueId())) {
                throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("draw样板房产品表,uniqueId为空: drawDesignTempletProductId = " + drawDesignTempletProduct.getId()));
            }
            drawDesignTempletProductMap.put(drawDesignTempletProduct.getUniqueId(), drawDesignTempletProduct);
        }

        // base product
        List<DrawBaseProduct> drawBaseProducts = new ArrayList<>();
        // draw design templet product
        List<DrawDesignTempletProduct> drawDesignTempletProducts = new ArrayList<>();

        // 产品
        Map<Long, DrawBaseProduct> mapPorducts = this.mapDrawBaseProduct(new ArrayList<>(drawDesignTempletProductMap.values()));

        for (DrawBaseProductDTO drawBaseProductDTO : drawDesignTempletDTO.getDrawBaseProductDTOList()) {
            if (StringUtils.isEmpty(drawBaseProductDTO.getUniqueId())) {
                throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("jsonStr中,某个产品信息uniqueId为空"));
            }

            DrawDesignTempletProduct drawDesignTempletProduct = drawDesignTempletProductMap.get(drawBaseProductDTO.getUniqueId());
            if (drawDesignTempletProduct == null) {
                throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("jsonStr中,某个产品信息uniqueId和数据库对应不上,uniqueId = " + drawBaseProductDTO.getUniqueId()));
            }

            // 挂节点信息更新
            drawDesignTempletProduct.setPosIndexPath(drawBaseProductDTO.getPosIndexPath());
            drawDesignTempletProduct.setPosName(drawBaseProductDTO.getPosName());
            drawDesignTempletProduct.setProductSequence(Utils.replaceString(drawBaseProductDTO.getPosIndexPath(), "/"));
            // 产品空间坐标，烘焙完成后获取
            drawDesignTempletProduct.setProPosition(drawBaseProductDTO.getProPosition());

            // add
            drawDesignTempletProducts.add(drawDesignTempletProduct);

            // 更新基本产品信息
            // update by huangsongbo 软装不需要更新产品信息(因为软装在base_product表中已经存在记录)
            if (drawDesignTempletProduct.getCreateProductStatus() != null
                    && DrawDesignTempletProductConstant.SOFT_PRODUCT.intValue() == drawDesignTempletProduct.getCreateProductStatus().intValue()) {
                log.debug("drawDesignTempletProduct.getCreateProductStatus() = {}", drawDesignTempletProduct.getCreateProductStatus());
            } else {
                DrawBaseProduct drawBaseProduct = mapPorducts.get(drawDesignTempletProduct.getProductId().longValue());
                if (drawBaseProduct == null) {
                    throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("样板房对应的某个产品not found"));
                }

                // 硬装产品部分
                drawBaseProduct.setWindowsU3dmodelId(Integer.valueOf(drawBaseProductDTO.getWindowsU3dmodelId() + ""));
                // add
                drawBaseProducts.add(drawBaseProduct);
            }
        }

        // 批量更新挂节点、埋点信息
        drawDesignTempletProductService.batchUpdateDesignTempletProduct(drawDesignTempletProducts);

        // 批量更新windows u3d model 资源
        drawBaseProductService.batchUpdateDrawBaseProduct(drawBaseProducts);
    }

    /**
     * 获取空间的产品
     *
     * @param drawDesignTempletProducts
     * @return
     */
    Map<Long, DrawBaseProduct> mapDrawBaseProduct(List<DrawDesignTempletProduct> drawDesignTempletProducts) {
        List<Long> ids = drawDesignTempletProducts.stream().filter(dtp -> dtp != null && dtp.getProductId() != null)
                .map(dtp -> dtp.getProductId().longValue()).collect(Collectors.toList());
        List<DrawBaseProduct> drawBaseProducts = drawBaseProductService.listDrawBaseProductByIds(ids);
        if (drawBaseProducts == null || drawBaseProducts.isEmpty()) {
            return new HashMap<>();
        }

        Map<Long, DrawBaseProduct> map = drawBaseProducts.stream().collect(Collectors.toMap(DrawBaseProduct::getId, dp -> dp, (p, n) -> n));
        return Optional.of(map).orElse(new HashMap<>());
    }

    @Override
    public DrawBaseHouse get(Long id) {
        if (id == null) {
            return null;
        }

        return drawBaseHouseMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<Long, Long> transformToBaseHouse(DrawBaseHouse drawBaseHouse) {

        Map<Long, Long> returnMap = new HashMap<>();

        // 参数验证 ->start
        if (drawBaseHouse == null) {
            return returnMap;
        }
        // 参数验证 ->end

        // 清空资源
        drawBaseHouse.setPicRes1Id(null);
        drawBaseHouse.setSnapPicId(null);
        drawBaseHouse.setRestoreFileId(null);

        drawBaseHouseMapper.transformToBaseHouse(drawBaseHouse);

        // update base_house_id
        this.update(drawBaseHouse);

        returnMap.put(drawBaseHouse.getId(), drawBaseHouse.getBaseHouseId());
        return returnMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(DrawBaseHouse drawBaseHouse) {
        // 参数验证 ->start
        if (drawBaseHouse == null) {
            return;
        }
        // 参数验证 ->end

        drawBaseHouseMapper.updateByPrimaryKeySelective(drawBaseHouse);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void uploadHouse(Long houseId) throws DrawBusinessException {
        DrawBaseHouse drawHouse = new DrawBaseHouse();
        drawHouse.setId(houseId);
        drawHouse.setHouseStatus(DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString());
        int update = drawBaseHouseMapper.updateByPrimaryKeySelective(drawHouse);

        if (update == 1) {
            Map<String, Object> house = drawBaseHouseMapper.getBaseHouseInfo(houseId);
            if (house == null) {
                throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
            }

            String baseHouseId = Objects.toString(house.get("baseHouseId"), "0");
            if (baseHouseId != null && !baseHouseId.equals("0")) {
                // 已被烘焙数据更改状态 ==> 烘焙中(base_house => 4)
                baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_FACSIMILE, Long.valueOf(baseHouseId));
            }

            // 邮件推送
            this.handlerMailPush(house);
        }
    }

    /**
     * 邮件推送
     *
     * @param house
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void handlerMailPush(Map<String, Object> house) {
        // 邮件通知完善员
        UserLoginBO login = HouseAuthUtils.getUserLoginBO();
        String userName = Objects.isNull(login.getName()) ? login.getLoginName() : login.getName() + "(" + login.getLoginName() + ")";
        // 地址
        String areaLongCode = Objects.toString(house.get("areaLongCode"), "0");
        String[] split = areaLongCode.substring(1, areaLongCode.length() - 1).split("\\.");
        String detailAddress = baseLivingService.getDetailAddress(split);
        // 户型名
        String houseName = Objects.toString(house.get("houseName"), "");
        // 小区名
        String livingName = Objects.toString(house.get("livingName"), "");
        String args = detailAddress + livingName + "-" + houseName;
        PerfectHouseMailMessage msg = new PerfectHouseMailMessage(new String[]{userName, args}, args);

        templateMailService.push(msg);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DrawHouseAndAreaVO perfectHouse(Long houseId) {
        final String type = "edit";

        if (null == houseId) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        // 完善户型员
        if (!HouseAuthUtils.hasImproverPermission()) {
            throw new BusinessException(false, ResponseEnum.FORBIDDEN);
        }

        // 户型完善
        Long userId = HouseAuthUtils.getRequestUserId();
        int lockPerfectHouse = drawBaseHouseMapper.lockPerfectHouse(houseId, userId,
                DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING, DrawBaseHouseConstant.HOUSE_WAIT_PERFECT);
        if (lockPerfectHouse != 1) {
            log.warn("有人正在完善户型 {}(houseId)", houseId);
            // 有人正在完善户型...
            DrawBaseHouse house = drawBaseHouseMapper.getBaseHouseById(houseId);
            if (house != null && !house.getPerfectUserId().equals(userId)) {
                throw new BusinessException(false, ResponseEnum.HOUSE_PERFECT_PROCESSING_ERROR);
            }
        }

        return facsimileHouse(houseId, type);
    }

    /**
     * 清空原先的户型数据
     *
     * @param houseId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerSubmitHouse(Long houseId) {
        if (houseId == null) {
            return;
        }

        DrawBaseHouse drawHouse = drawBaseHouseMapper.getBaseHouseById(houseId);
        if (drawHouse == null) {
            log.warn("绘制的户型{}(houseId)不存在或被删除", houseId);
            return;
        }

        log.info("开始删除老数据绘制户型数据，{}(houseId)", houseId);
        List<Long> emptySpaces = drawSpaceCommonService.getDealDrawSpaceCommon(DrawBaseHouseConstant.IS_DELETE_INIT, houseId);

        if (emptySpaces != null && !emptySpaces.isEmpty()) {
            log.info("需要删除绘制户型老数据, emptySpaces ==> {}", emptySpaces);

            final int status = 1;
            // 空间
            drawSpaceCommonService.deleteDrawSpaceCommon(status, emptySpaces);

            // 样板房
            drawDesignTempletService.deleteDrawDesignTemplet(status, emptySpaces);

            // 样板房硬装产品和软装产品
            List<Long> emptyTemplets = drawDesignTempletService.getEmptyDrawDesignTemplet(emptySpaces);
            drawDesignTempletProductService.deleteDrawDesignTempletProduct(status, emptyTemplets);

            // 硬装产品
            List<Long> baseProducts = drawBaseProductService.getDeleteDrawBaseProductId(emptySpaces);
            if (baseProducts != null && !baseProducts.isEmpty()) {
                drawBaseProductService.deleteDrawBaseProduct(status, baseProducts);
            }

            // 清除空间的旧数据资源
            // drawResFileService.clearDrawResFileResource(emptySpaces, null);
        }

        log.info("删除提交的绘制户型数据完成，{}(houseId)", houseId);
    }

    /**
     * 清空原先的正式表的数据户型数据
     *
     * @param houseId
     * @param taskId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerCallbackHouse(Long houseId, Long taskId) {
        if (houseId == null || taskId == null) {
            log.warn("参数 houseId 为空");
            return;
        }

        // 是否第一个空间烘焙
        int taskCount = drawBakeTaskService.getBakeSuccessTask(taskId);
        if (taskCount != DrawBaseHouseConstant.EMPTY_DATA_INIT_FLAG) {
            log.debug("已清空过正式表数据，不需要清空数据");
            return;
        }

        log.info("开始删除正式老数据户型数据，{}(houseId)，{}(taskId)", taskId);

        DrawBaseHouse drawHouse = drawBaseHouseMapper.getBaseHouseById(houseId);
        if (drawHouse != null && drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() != 0) {
            log.info("需要删除的正式户型老数据, {}(baseHouseId)", drawHouse.getBaseHouseId());

            List<Long> emptySpaces = drawSpaceCommonService.getEmptyDealSpaceCommon(drawHouse.getBaseHouseId());
            if (emptySpaces != null && !emptySpaces.isEmpty()) {
                log.info("需要删除正式户型老数据, emptySpaces ==> {}", emptySpaces);

                final int status = 1;
                // 空间
                log.info("删除正式户型空间老数据信息");
                drawSpaceCommonService.emptySpaceCommon(status, emptySpaces);

                // 户型、空间关系
                log.info("删除正式户型户型、空间关系老数据信息");
                drawSpaceCommonService.emptyHouseSpaceRelation(status, emptySpaces);

                // 这个查询要在删除design_templet之前执行
                List<Long> designTemplets = drawDesignTempletService.getEmptyDesignTemplet(emptySpaces);
                // 样板房硬装产品和软装产品
                if (designTemplets != null && !designTemplets.isEmpty()) {
                    log.info("删除正式样板房硬装产品和软装产品老数据信息");
                    drawDesignTempletProductService.emptyDesignTempletProduct(status, designTemplets);
                }

                // 样板房
                log.info("删除正式户型样板房老数据信息");
                drawDesignTempletService.emptyDesignTemplet(status, emptySpaces);

                // 硬装产品
                List<Long> baseProducts = drawBaseProductService.getEmptyBaseProduct(emptySpaces);
                if (baseProducts != null && !baseProducts.isEmpty()) {
                    log.info("删除正式户型硬装产品老数据信息, baseProducts ==> {}", baseProducts);
                    drawBaseProductService.emptyBaseProduct(status, baseProducts);
                }

                // 清除assetbundle资源文件、draw_res_file表相关的assetbundle的文件记录
                List<Long> emptyDrawSpaces = drawSpaceCommonService.getDealDrawSpaceCommon(DrawBaseHouseConstant.IS_DELETED, houseId);
                if (emptyDrawSpaces != null && !emptyDrawSpaces.isEmpty()) {
                    drawResFileService.clearDrawResFileResource(emptyDrawSpaces, 1);
                }
            }
        }

        log.info("删除正式表的老数据户型数据完成，{}(houseId)", houseId);
    }

    /**
     * 处理烘焙任务失败
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void handlerBakeTaskFailed(DrawBaseHouseControllerCallBackDTO dto) {
        // 烘焙失败, update status = 4
        if (dto != null) {
            log.warn("回滚处理失败的烘焙任务 {}(taskDetailId), message ==> {}", dto.getTaskDetailId(), dto.getMessage());
            DrawBakeTaskDetail record = new DrawBakeTaskDetail();
            record.setId(dto.getTaskDetailId());
            // ip 记录
            record.setMessage("Bake Client IP => " + HttpContextUtils.getIpAddress() + "\n" + dto.getMessage());
            record.setGmtModified(new Date());
            record.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_FAIL);
            record.setBakeFailCount(1);
            drawBakeTaskDetailService.updateByPrimaryKeySelective(record);
        }
    }

    /**
     * 处理烘焙任务失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long handlerBakeTaskFailed(String data, String message) throws IOException {
        DrawBaseHouseControllerCallBackDTO dto = null;
        if (StringUtils.isNoneBlank(data)) {
            ObjectMapper objectMapper = new ObjectMapper();
            // 如果是空对象的时候,不抛异常
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 反序列化的时候如果多了其他属性,不抛出异常
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            dto = objectMapper.readValue(data, DrawBaseHouseControllerCallBackDTO.class);
        }

        if (dto == null) {
            return -1L;
        }

        dto.setMessage(message);
        this.handlerBakeTaskFailed(dto);

        return dto.getTaskDetailId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transformHouse(String phone, Long houseId) {
        // 外部全流程绘制(内部演示员)
        if (!HouseAuthUtils.hasInternalDemoPermission()) {
            throw new BusinessException(false, ResponseEnum.FORBIDDEN);
        }

        // 验证户型
        DrawBaseHouse drawHouse = drawBaseHouseMapper.getBaseHouseById(houseId);
        if (drawHouse == null) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // 不是自己的户型不能转换
        if (!drawHouse.getUserId().equals(HouseAuthUtils.getRequestUserId())) {
            throw new BusinessException(false, ResponseEnum.FORBIDDEN);
        }

        // 验证用户
        Map<String, Object> user = drawBaseHouseMapper.getUserByPhone(phone);
        if (user == null || user.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.USER_INFO_IS_NULL);
        }

        String userId = Objects.toString(user.get("userId"));
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException(false, ResponseEnum.USER_INFO_IS_NULL);
        }

        // draw base house
        DrawBaseHouse updateDrawHouse = new DrawBaseHouse();
        updateDrawHouse.setId(houseId);
        // 设置公开数据
        updateDrawHouse.setIsPublic(DrawBaseHouseConstant.HOUSE_IS_PUBLIC);
        updateDrawHouse.setUserId(Long.parseLong(userId));
        drawBaseHouseMapper.updateByPrimaryKeySelective(updateDrawHouse);

        // base house
        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
            BaseHouse baseHouse = new BaseHouse();
            baseHouse.setId(drawHouse.getBaseHouseId());
            baseHouse.setUserId(Long.parseLong(userId));
            baseHouseMapper.updateByPrimaryKeySelective(baseHouse);
        }
    }

    @Override
    public void resetDrawHouse(List<Integer> houseIds) {
        if (houseIds == null || houseIds.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        // draw house
        drawBaseHouseMapper.resetDrawHouse(DrawBaseHouseConstant.HOUSE_BAKE_SUCCESS, houseIds);

        // base house
        baseHouseMapper.resetDrawHouse(Integer.valueOf(DrawBaseHouseConstant.DEAL_STATUS_EDIT), houseIds);

        // task、task detail
        drawBakeTaskService.resetDrawHouse(DrawBaseHouseConstant.IS_DELETED, houseIds);
    }
}
