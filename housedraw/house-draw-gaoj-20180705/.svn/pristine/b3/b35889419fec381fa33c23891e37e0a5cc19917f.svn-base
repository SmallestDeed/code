package com.sandu.service.fix.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sandu.api.fix.model.*;
import com.sandu.api.fix.service.BakeTransformCallback;
import com.sandu.api.fix.service.FixCupboardService;
import com.sandu.api.house.bo.*;
import com.sandu.api.house.input.DrawDesignTempletDTO;
import com.sandu.api.house.input.DrawFileDataNew;
import com.sandu.api.house.model.*;
import com.sandu.api.house.service.*;
import com.sandu.api.product.bo.TransformProductBO;
import com.sandu.api.product.model.BaseProduct;
import com.sandu.api.product.model.ProductAttribute;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.product.service.ProductAttributeService;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResFileService;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.service.DrawBaseHouseServiceV2;
import com.sandu.api.volume.room.model.VolumeRoomHouseBO;
import com.sandu.common.constant.*;
import com.sandu.common.constant.attr.DoorAttr;
import com.sandu.common.constant.bake.BakeCallbackStatus;
import com.sandu.common.constant.bake.BakeTaskQueue;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawDesignTempletProductConstant;
import com.sandu.common.constant.kechuang.PlatformType;
import com.sandu.exception.BizException;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.mq.queue.SyncMessage;
import com.sandu.service.bake.dao.DrawBakeRecordMapper;
import com.sandu.service.bake.dao.DrawBakeTaskMapper;
import com.sandu.service.file.dao.DrawResFileMapper;
import com.sandu.service.fix.dao.FixCupboardMapper;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.service.pic.dao.DrawResHousePicMapper;
import com.sandu.service.product.dao.BaseProductMapper;
import com.sandu.service.product.dao.DrawBaseProductMapper;
import com.sandu.service.space.dao.DrawSpaceCommonMapper;
import com.sandu.service.templet.dao.DrawDesignTempletProductMapper;
import com.sandu.util.Utils;
import com.sandu.util.auth.HouseAuthUtils;
import com.sandu.util.http.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * save
 * submit
 * check
 * get task
 * get sub task
 * callback
 * <p>
 * 修复橱柜数据服务
 */

@Slf4j
@Service
public class FixCupboardServiceImpl implements FixCupboardService {

    private final static String DEFAULTS_CF_PREFIX = "E";
    private final static String DEFAULTS_PRODUCT_CODE = "baimo_ctqki_0001";
    private final static String DEFAULTS_GMT_CREATE = "2018-6-30 23:59:59";
    private final static int DEFAULTS_UPDATE_COUNT = 10;

    @Autowired
    DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    DrawResHousePicMapper drawResHousePicMapper;

    @Autowired
    DrawResFileMapper drawResFileMapper;

    @Autowired
    DrawSpaceCommonMapper drawSpaceCommonMapper;

    @Autowired
    DrawSpaceCommonService drawSpaceCommonService;

    @Autowired
    DrawDesignTempletService drawDesignTempletService;

    @Autowired
    DrawDesignTempletProductService drawDesignTempletProductService;

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    DrawBaseHouseServiceV2 drawBaseHouseServiceV2;

    @Autowired
    DrawBakeTaskService drawBakeTaskService;

    @Autowired
    BaseHouseMapper baseHouseMapper;

    @Autowired
    DrawBakeTaskMapper drawBakeTaskMapper;

    @Autowired
    DrawBakeRecordMapper drawBakeRecordMapper;

    @Autowired
    DrawBakeTaskDetailService drawBakeTaskDetailService;

    @Autowired
    DrawBaseHouseService drawBaseHouseService;

    @Autowired
    DesignTempletJumpPositionRelService designTempletJumpPositionRelService;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    ResModelService resModelService;

    @Autowired
    HouseSpaceNewService houseSpaceNewService;

    @Autowired
    ResFileService resFileService;

    @Autowired
    DrawDesignTempletProductMapper drawDesignTempletProductMapper;

    @Autowired
    BaseProductMapper baseProductMapper;

    @Autowired
    DoorAttr doorAttr;

    @Autowired
    ProductAttributeService productAttributeService;

    @Autowired
    DrawBaseProductMapper drawBaseProductMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FixCupboardMapper fixCupboardMapper;

    @Autowired
    BaseLivingService baseLivingService;

    @Override
    public Map<String, Object> listFixCupboardHouse(FixCupboardQuery query) {
        Map<String, Object> map = new HashMap<>();

        // 处理默认的查询参数
        this.handlerDefaultsQueryParams(query);

        List<DrawBaseHouseBO> fixCupboards = fixCupboardMapper.listFixCupboardHouse(query);
        if (fixCupboards != null && !fixCupboards.isEmpty()) {
            // 图片资源
            Map<Long, String> mapResource = drawBaseHouseService.mapResource(fixCupboards);
            // 量房资源
            Map<Long, VolumeRoomHouseBO> mapVolumeRoom = drawBaseHouseService.mapVolumeRoomHouse(fixCupboards);

            StringBuilder buf = new StringBuilder();
            for (DrawBaseHouseBO fixCupboard : fixCupboards) {
                if (StringUtils.isNoneBlank(fixCupboard.getAreaLongCode())) {
                    String[] split = fixCupboard.getAreaLongCode().substring(1, fixCupboard.getAreaLongCode().length() - 1).split("\\.");
                    buf.append(baseLivingService.getDetailAddress(split)).append(fixCupboard.getLivingName());
                    fixCupboard.setDetailAddress(buf.toString());
                    // clear
                    buf.setLength(0);
                }

                // 图片资源优先级 ： 截图 > 户型图
                fixCupboard.setPicPath(mapResource.get(fixCupboard.getId()));
                // 量房 lf 文件处理
                VolumeRoomHouseBO volumeRoom = mapVolumeRoom.get(fixCupboard.getId());
                if (volumeRoom != null && !PlatformType.SANDU.getType().equals(fixCupboard.getPlatformType())) {
                    fixCupboard.setFilePath(volumeRoom.getLfFilePath());
                }
            }
        }

        map.put("list", fixCupboards);

        return map;
    }

    /**
     * 处理默认的查询参数
     *
     * @param query
     */
    private void handlerDefaultsQueryParams(FixCupboardQuery query) {
        // defaults space code E
        if (StringUtils.isEmpty(query.getSpaceCode())) {
            query.setSpaceCode(DEFAULTS_CF_PREFIX);
        }

        // defaults product code baimo_ctqki_0001
        if (StringUtils.isEmpty(query.getProductCode())) {
            query.setProductCode(DEFAULTS_PRODUCT_CODE);
        }

        if (StringUtils.isEmpty(query.getGmtCreate())) {
            query.setGmtCreate(DEFAULTS_GMT_CREATE);
        }

        // defaults house status DrawBaseHouseConstant.HOUSE_BAKE_SUCCESS
        if (query.getHouseStatus() == null) {
            query.setHouseStatus(DrawBaseHouseConstant.HOUSE_BAKE_SUCCESS.toString());
        }

        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(1);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> save(FixCupboardSaveBO fixCupboard) {
        // TODO 只有更新，没有新增
        Map<String, Object> response = new HashMap<>();
        DrawBaseHouse drawHouse = getSaveHouseByValid(fixCupboard);

        DrawResFile file = new DrawResFile();
        DrawResHousePic pic = new DrawResHousePic();
        DrawResHousePic snapPic = new DrawResHousePic();

        // 资源处理
        for (DrawFileDataNew drawFile : fixCupboard.getDatas()) {
            if (Objects.equals(SystemConfigEnum.HOUSE_PIC_UPLOAD.toString(), drawFile.getFileType())) {
                // house pic
                pic = getDrawHousePicResource(drawHouse, drawFile);
                drawHouse.setPicRes2Id(pic.getId());
            } else if (Objects.equals(SystemConfigEnum.HOUSE_SNAP_PIC_UPLOAD.toString(), drawFile.getFileType())) {
                // snap pic
                snapPic = getDrawHousePicResource(drawHouse, drawFile);
                drawHouse.setSnapPicId(snapPic.getId().longValue());
            } else {
                // restore file
                file = this.getDrawFileResource(drawHouse, drawFile);
                drawHouse.setRestoreFileId(file.getId());
            }
        }

        drawHouse.setGmtModified(new Date());
        drawHouse.setModifier(HouseAuthUtils.getUserLoginBO().getLoginName());
        drawHouse.setHouseStatus(DrawBaseHouseConstant.CHECK_INIT.toString());

        // update draw house
        drawBaseHouseMapper.updateByPrimaryKeySelective(drawHouse);

        response.put("pic", pic);
        response.put("file", file);
        response.put("snapPic", snapPic);

        // draw house
        response.put("house", drawHouse);

        return response;
    }

    private DrawBaseHouse getSaveHouseByValid(FixCupboardSaveBO fixCupboard) {
        if (fixCupboard.getHouseId() == null) {
            throw new BizException("houseId不能为空");
        }

        if (!StringUtils.isNotBlank(fixCupboard.getHouseName())) {
            throw new BizException("户型名不能为空");
        }

        if (fixCupboard.getDatas() == null || fixCupboard.getDatas().isEmpty()) {
            throw new BizException("文件资源不能为空");
        }

        DrawBaseHouse drawHouse = drawBaseHouseMapper.get(fixCupboard.getHouseId());
        if (drawHouse == null || drawHouse.getIsDeleted() == DrawBaseHouseConstant.IS_DELETED) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // 待烘焙的户型不能保存
        if (Objects.equals(drawHouse.getHouseStatus(), DrawBaseHouseConstant.HOUSE_BAKE_WAIT.toString())) {
            throw new BusinessException(false, ResponseEnum.HOUSE_ALREADY_AUDIT_PASS);
        }

        // add user id
        fixCupboard.setUserId(HouseAuthUtils.getRequestUserId());

        return drawHouse;
    }

    /**
     * 处理保存户型的图片资源
     *
     * @param drawHouse
     * @param drawFile
     * @return
     */
    DrawResHousePic getDrawHousePicResource(DrawBaseHouse drawHouse, DrawFileDataNew drawFile) {
        if (drawHouse.getPicRes1Id() == null || drawHouse.getPicRes1Id() <= 0
                || drawHouse.getSnapPicId() == null || drawHouse.getSnapPicId() <= 0) {
            // add pic
            return addDrawHousePic(drawHouse, drawFile);
        } else {
            DrawResHousePic drawHousePic = drawResHousePicMapper.selectByPrimaryKey(drawHouse.getPicRes1Id().longValue());
            if (drawHousePic == null || drawHousePic.getId() == null) {
                // add pic
                return addDrawHousePic(drawHouse, drawFile);
            }

            drawHousePic.setPicPath(drawFile.getFilePath());
            drawHousePic.setPicName(drawFile.getFileName());
            drawHousePic.setBusinessId(drawHouse.getId().intValue());
            drawHousePic.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);

            // update pic
            drawResHousePicMapper.updateByPrimaryKeySelective(drawHousePic);

            return drawHousePic;
        }
    }

    /**
     * 添加户型图片资源
     *
     * @param drawHouse
     * @param drawFile
     * @return
     */
    DrawResHousePic addDrawHousePic(DrawBaseHouse drawHouse, DrawFileDataNew drawFile) {
        DrawResHousePic pic = new DrawResHousePic();
        pic.setBusinessId(drawHouse.getId().intValue());
        pic.setPicHigh(drawFile.getHeight());
        pic.setPicWeight(drawFile.getWidth());
        pic.setPicSuffix(drawFile.getFileSuffix());
        pic.setPicSize(drawFile.getFileSize());
        pic.setPicPath(drawFile.getFilePath());
        pic.setPicName(drawFile.getFileName());
        pic.setPicFormat("");
        pic.setSysCode(drawHouse.getHouseCode());
        pic.setPicCode(drawHouse.getHouseCode());
        pic.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
        pic.setPicType(drawFile.getFileType());
        pic.setFileKey(FileKey.BASE_HOUSE_PIC.getFileKey());
        pic.setGmtCreate(new Date());

        drawResHousePicMapper.saveDrawResHousePic(pic);

        return pic;
    }

    /**
     * 处理出现的资源文件
     *
     * @param drawHouse
     * @param drawFile
     * @return
     */
    DrawResFile getDrawFileResource(DrawBaseHouse drawHouse, DrawFileDataNew drawFile) {
        if (drawHouse.getRestoreFileId() == null || drawHouse.getRestoreFileId() <= 0) {
            // add file
            return addDrawHouseFile(drawHouse, drawFile);
        } else {
            DrawResFile drawResFile = drawResFileMapper.selectByPrimaryKey(drawHouse.getRestoreFileId());
            if (drawResFile == null || drawResFile.getId() == null) {
                // add file
                return addDrawHouseFile(drawHouse, drawFile);
            }

            drawResFile.setFilePath(drawFile.getFilePath());
            drawResFile.setFileName(drawFile.getFileName());
            drawResFile.setBusinessId(drawHouse.getId().toString());
            drawResFile.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);

            // update file
            drawResFileMapper.updateByPrimaryKeySelective(drawResFile);

            return drawResFile;
        }
    }

    /**
     * 添加户型的资源文件
     *
     * @param drawHouse
     * @param drawFile
     * @return
     */
    DrawResFile addDrawHouseFile(DrawBaseHouse drawHouse, DrawFileDataNew drawFile) {
        DrawResFile drawResFile = new DrawResFile();
        drawResFile.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
        drawResFile.setBusinessId(String.valueOf(drawHouse.getId()));
        drawResFile.setSysCode(drawHouse.getHouseCode());
        drawResFile.setFileOriginalName(drawFile.getFileOriginalName());
        drawResFile.setFileName(drawFile.getFileName());
        drawResFile.setFilePath(drawFile.getFilePath());
        drawResFile.setFileSize(drawFile.getFileSize());
        drawResFile.setFileSuffix(drawFile.getFileSuffix());
        drawResFile.setFileType(drawFile.getFileType());
        drawResFile.setFileCode(drawHouse.getHouseCode());
        drawResFile.setCreator(drawHouse.getUserId() + "");
        drawResFile.setModifier(drawHouse.getUserId() + "");
        drawResFile.setGmtCreate(new Date());
        drawResFile.setFileKey(FileKey.HOUSE_DRAW_FILE.getFileKey());

        drawResFileMapper.saveDrawResFile(drawResFile);

        return drawResFile;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void submit(FixCupboardSubmitBO fixCupboard) {
        UserLoginBO loginBO = HouseAuthUtils.getUserLoginBO();
        // valid
        this.validSubmitHouse(fixCupboard);

        // Clearing the space containing the cupboard
        boolean isFixCupboard = this.clearCupboardSpaceBySubmit(fixCupboard.getHouseId());

        // 是否含有厨房空间，true 执行下一步处理
        if (isFixCupboard) {
            // submit
            List<DrawSpaceBO> drawSpaceBOS = drawBaseHouseServiceV2.submit(loginBO, fixCupboard, (s) -> !s.getSpaceCode().startsWith(DEFAULTS_CF_PREFIX));

            // 提交后相当于发起审核操作，保存未提交都不能再审核列表中展示
            drawBaseHouseMapper.updateDrawHouseStatus(DrawBaseHouseConstant.CHECK_APPLY, null, fixCupboard.getHouseId());

            // jump check
            this.check(fixCupboard.getDrawHouse(), drawSpaceBOS, loginBO);
        }
    }

    /**
     * 清除旧数据
     *
     * @param houseId
     * @return
     */
    private boolean clearCupboardSpaceBySubmit(Long houseId) {
        List<Long> clearCupboards = drawSpaceCommonMapper.getClearCupboardSpaceBySubmit(houseId);
        if (clearCupboards == null || clearCupboards.isEmpty()) {
            log.info("{}(houseId)没有需要处理清除的厨房空间", houseId);
            return false;
        }

        log.info("Clear Cupboards => {}", clearCupboards);
        // 空间
        Integer updateCount = drawSpaceCommonService.deleteDrawSpaceCommon(DrawBaseHouseConstant.IS_DELETED, clearCupboards);

        // 样板房
        updateCount += drawDesignTempletService.deleteDrawDesignTemplet(DrawBaseHouseConstant.IS_DELETED, clearCupboards);

        // 样板房硬装产品和软装产品
        List<Long> emptyTemplets = drawDesignTempletService.getEmptyDrawDesignTemplet(clearCupboards);
        updateCount += drawDesignTempletProductService.deleteDrawDesignTempletProduct(DrawBaseHouseConstant.IS_DELETED, emptyTemplets);

        // 硬装产品
        List<Long> baseProducts = drawBaseProductService.getDeleteDrawBaseProductId(clearCupboards);
        if (baseProducts != null && !baseProducts.isEmpty()) {
            updateCount += drawBaseProductService.deleteDrawBaseProduct(DrawBaseHouseConstant.IS_DELETED, baseProducts);
        }

        log.info("清除厨房户型信息完成");

        return updateCount > 0;
    }

    private void validSubmitHouse(FixCupboardSubmitBO fixCupboard) {
        if (fixCupboard == null || fixCupboard.getHouseId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        DrawBaseHouse drawHouse = drawBaseHouseMapper.getBaseHouseById(fixCupboard.getHouseId());
        if (drawHouse == null) {
            throw new BusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        fixCupboard.setDrawHouse(drawHouse);
        fixCupboard.setDataType(Objects.isNull(drawHouse.getDataType()) ? DrawBaseHouseConstant.DATA_PLATFORM : drawHouse.getDataType());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void check(DrawBaseHouse drawHouse, List<DrawSpaceBO> drawSpaceBOS, UserLoginBO loginBO) {
        if (drawSpaceBOS == null || drawSpaceBOS.isEmpty()) {
            log.warn("没有需要审核的空间");
            return;
        }

        DrawBaseHouse checkHouse = new DrawBaseHouse();
        checkHouse.setId(drawHouse.getId());
        checkHouse.setAuditTime(new Date());
        checkHouse.setRejectReason("修复厨房橱柜");
        checkHouse.setHouseCode(drawHouse.getHouseCode());
        checkHouse.setAuditorId(HouseAuthUtils.getRequestUserId());
        checkHouse.setHouseStatus(DrawBaseHouseConstant.CHECK_APPLY.toString());

        int updateCount = drawBaseHouseMapper.updateDrawHouseStatus3(checkHouse, DrawBaseHouseConstant.HOUSE_BAKE_WAIT);
        if (updateCount == 1) {
            List<DrawSpaceCommon> drawSpaces = drawSpaceBOS.stream().map(DrawSpaceBO::getDrawSpaceCommon).collect(Collectors.toList());

            // task
            DrawBakeTask task = getDrawBakeTask(loginBO.getId(), checkHouse);
            drawBakeTaskService.saveDrawBakeTask(task);

            // task detail
            List<DrawBakeTaskDetail> drawBakeTaskDetails = getDrawBakeTaskDetails(drawSpaces, task);
            drawBakeTaskService.batchSaveDrawBakeTaskDetail(drawBakeTaskDetails);

            // 已被烘焙数据更改状态
            baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_BAKE, drawHouse.getId());
        }
    }

    private DrawBakeTask getDrawBakeTask(Long userId, DrawBaseHouse house) {
        DrawBakeTask task = new DrawBakeTask();
        task.setUserId(userId);
        task.setGmtCreate(new Date());
        task.setHouseId(house.getId());
        task.setHouseCode(house.getHouseCode());
        task.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_INIT);
        task.setQueueName(BakeTaskQueue.FIX_CUPBOARD.toString());
        task.setPriority(5);
        return task;
    }

    private List<DrawBakeTaskDetail> getDrawBakeTaskDetails(List<DrawSpaceCommon> drawSpaces, DrawBakeTask task) throws BusinessException {
        // 户型下的空间集合
        List<DrawSpaceCommonBO> spaceCommons = drawSpaceCommonService.listSpaceCommon(drawSpaces);
        if (spaceCommons == null || spaceCommons.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.SPACE_NOT_FOUND);
        }

        // 空间主键集合
        List<Long> spaceIds = spaceCommons.stream().map(DrawSpaceCommonBO::getSpaceId).collect(Collectors.toList());

        // 获取空间下的产品信息
        Map<Long, List<DrawBaseProductBO>> productMap = getProductDataMap(spaceIds);

        // 烘焙任务详情集合
        List<DrawBakeTaskDetail> drawBakeTaskDetails = new ArrayList<>();
        for (DrawSpaceCommonBO space : spaceCommons) {
            DrawBakeTaskDetail taskDetail = new DrawBakeTaskDetail();

            taskDetail.setSpaceCode(space.getSpaceCode());
            taskDetail.setSpaceFileIds(String.valueOf(space.getFileId()));
            taskDetail.setSpaceFilePath(space.getFilePath());
            taskDetail.setSpaceId(space.getSpaceId());
            taskDetail.setTaskId(task.getId());
            taskDetail.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_INIT);

            // 空间下的产品集合, 产品json串信息
            String jsonProductInfo = getJsonProductInfo(objectMapper, productMap.get(space.getSpaceId()));
            taskDetail.setProductData(jsonProductInfo);

            // add
            drawBakeTaskDetails.add(taskDetail);
        }

        return drawBakeTaskDetails;
    }

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
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTaskId(String queueName) {
        return drawBakeTaskService.getBakeTaskId(StringUtils.isEmpty(queueName) ? BakeTaskQueue.FIX_CUPBOARD.toString() : queueName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<DrawBakeTaskBO> getSubTask(String queueName, Long taskId) {
        return drawBakeTaskService.listBakeTask(StringUtils.isEmpty(queueName) ? BakeTaskQueue.FIX_CUPBOARD.toString() : queueName, taskId);
    }

    /**
     * 烘焙户型回调
     *
     * @param fixCupboard
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String callback(FixCupboardCallbackBO fixCupboard) {
        log.info("正在处理烘焙任务{}(taskDetailId)", fixCupboard.getTaskDetailId());

        // sub task
        DrawBakeTaskDetail subTask = getDrawBakeTaskDetailByValid(fixCupboard);

        // 任务被删除
        if (Objects.equals(subTask.getIsDeleted(), DrawBaseHouseConstant.IS_DELETED)) {
            return fixCupboard.getTaskDetailId() + "任务不存在或被删除了";
        }

        // 已被烘焙的任务
        if (Objects.equals(subTask.getStatus(), DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS)) {
            return fixCupboard.getTaskDetailId() + "任务已烘焙过，不能重复烘焙";
        }

        if (Objects.equals(fixCupboard.getStatus(), BakeCallbackStatus.FAILURE.getStatus())) {
            // failed
            this.handlerCallbackError(fixCupboard, null);

            log.info("{}(taskDetailId)处理烘焙任务异常", fixCupboard.getTaskDetailId());

            return BakeCallbackStatus.FAILURE.getMessage();
        } else {
            DrawDesignTempletDTO drawDesignTempletDTO = fixCupboard.getDrawDesignTempletDTO();
            if (drawDesignTempletDTO == null) {
                throw new BizException(("参数drawDesignTempletDTO错误！"));
            }

            // 更新挂节点/模型文件等信息
            this.handlerBakeResource(fixCupboard, subTask);

            // base_house、base_product 、space_common
            // design_templet 、design_templet_product
            this.handlerTransform(subTask, (drawHouse, drawSpace, drawDesignTemplets, drawProducts, drawDesignTempletProducts) -> {
                // v1.0.5 rabbitmq搜索推送
                List<Long> pushProducts = drawProducts.stream().filter(p -> !DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(p.getCreateProductStatus()))
                        .map(DrawBaseProduct::getBaseProductId).collect(Collectors.toList());
                drawBaseHouseService.doSyncToIndex(SyncMessage.ACTION_ADD, pushProducts);

                // v1.0.7 处理相邻样板房的跳跃点信息(埋点)
                designTempletJumpPositionRelService.transformJumpPositionRel(drawHouse);
            });

            log.info("{}(taskDetailId)处理烘焙任务完成", fixCupboard.getTaskDetailId());

            return BakeCallbackStatus.SUCCEED.getMessage();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerCallbackError(FixCupboardCallbackBO fixCupboard, String data) {
        if (fixCupboard == null) {
            if (StringUtils.isEmpty(data)) {
                throw new BusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
            }

            try {
                fixCupboard = objectMapper.readValue(data, FixCupboardCallbackBO.class);
            } catch (IOException e) {
                throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
            }

            if (fixCupboard == null) {
                throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
            }
        }

        String bakeIpAddress = HttpContextUtils.getBakeIpAddress();
        DrawBakeTaskDetail record = new DrawBakeTaskDetail();
        record.setBakeFailCount(1);
        record.setGmtModified(new Date());
        record.setId(fixCupboard.getTaskDetailId());
        record.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_FAIL);
        record.setMessage("Bake Client IP => " + bakeIpAddress + "\n" + fixCupboard.getMessage());

        log.warn("烘焙任务失败处理 {}(taskDetailId), {}(bakeIpAddress), message ==> {}", fixCupboard.getTaskDetailId(), bakeIpAddress, fixCupboard.getMessage());

        // add failed record
        drawBakeTaskDetailService.updateByPrimaryKeySelective(record);
    }

    private DrawBakeTaskDetail getDrawBakeTaskDetailByValid(FixCupboardCallbackBO fixCupboard) {
        if (fixCupboard.getTaskDetailId() == null) {
            throw new BizException(("taskDetailId参数为空"));
        }

        DrawBakeTaskDetail subTask = drawBakeTaskDetailService.getSubTask(fixCupboard.getTaskDetailId());
        if (subTask == null) {
            throw new BizException((fixCupboard.getTaskDetailId() + "任务不存在"));
        }

        Long drawSpaceId = subTask.getSpaceId();
        if (drawSpaceId == null) {
            throw new BizException(("drawSpaceId为空"));
        }

        return subTask;
    }

    /**
     * 更新挂节点/模型文件等信息
     *
     * @param fixCupboard
     */
    private void handlerBakeResource(FixCupboardCallbackBO fixCupboard, DrawBakeTaskDetail subTask) {
        // 处理资源
        DrawDesignTemplet drawDesignTemplet = drawBaseHouseService.updateResource(fixCupboard.getDrawDesignTempletDTO(), subTask.getSpaceId());
        drawBaseHouseService.updateProduct(fixCupboard.getDrawDesignTempletDTO(), drawDesignTemplet);
    }

    /**
     * 转化烘焙的户型相关的数据
     *
     * @param subTask
     * @param callback
     */
    private void handlerTransform(DrawBakeTaskDetail subTask, BakeTransformCallback callback) {
        DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.get(subTask.getSpaceId());
        if (drawSpaceCommon == null || drawSpaceCommon.getId() == null) {
            log.error("通过{}(spaceId)未检索到空间信息或被删除", subTask.getSpaceId());
            throw new DrawBusinessException(false, ResponseEnum.SPACE_NOT_FOUND);
        }

        // 样板房
        DrawDesignTemplet drawDesignTemplet = drawDesignTempletService.getDrawDesignTempletBySpaceCommonId(subTask.getSpaceId());
        if (drawDesignTemplet == null || drawDesignTemplet.getId() == null) {
            log.error("通过{}(spaceId)未检索到样板房信息或被删除", drawSpaceCommon.getId());
            throw new DrawBusinessException(false, ResponseEnum.DESIGN_TEMPLET_NOT_FOUND);
        }

        // 户型
        DrawBaseHouse drawHouse = drawBaseHouseService.get(drawSpaceCommon.getDrawBaseHouseId());
        if (drawHouse == null || drawHouse.getId() == null) {
            log.error("通过{}(drawBaseHouseId)未检索到户型信息或被删除", drawSpaceCommon.getDrawBaseHouseId());
            throw new DrawBusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // clear cupboard by space
        this.clearCupboardSpaceByCallback(drawHouse, subTask.getTaskId());

        // 转换户型
        this.transformBaseHouse(drawHouse, subTask);

        // 户型的资源同步
        // this.transformHouseResource(drawHouse, subTask.getTaskId());

        // handler space common
        this.transformSpaceCommon(drawSpaceCommon, drawHouse.getBaseHouseId());

        // handler design templet
        this.transformDesignTemplet(drawDesignTemplet, drawSpaceCommon.getSpaceCommonId());

        // 产品信息
        List<DrawBaseProduct> drawProducts = drawBaseProductService.findAllByDrawDesignTempletId(drawDesignTemplet.getId());
        if (drawProducts == null || drawProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        // 硬装产品
        // 剔除软装白膜(因为正是表中已经存在了软装白膜产品数据, 所以不需要重复新增)
        List<DrawBaseProduct> hardProducts = drawProducts.stream().filter(p -> !DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(p.getCreateProductStatus())).collect(Collectors.toList());
        // handler hard product
        this.transformBaseProduct(hardProducts);

        // handler design templet product
        List<DrawDesignTempletProduct> drawDesignTempletProducts = drawDesignTempletProductService.findAllByDesignTempletId(drawDesignTemplet.getId());
        this.transformDesignTempletProduct(drawDesignTempletProducts, drawDesignTemplet.getDesignTempletId(), hardProducts);

        // 处理户型的状态和资源
        // created by songjianming@sanduspace.cn on 2018/2/6
        drawBaseHouseService.handlerHouseStatus(drawHouse, subTask.getId());

        // 回调执行
        if (callback != null) {
            callback.apply(drawHouse, drawSpaceCommon, drawDesignTemplet, drawProducts, drawDesignTempletProducts);
        }
    }

    private void transformBaseHouse(DrawBaseHouse drawHouse, DrawBakeTaskDetail subTask) {
        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
            log.info("户型 {}(drawBaseHouseId)已经被转换过, 不需要再转化", drawHouse.getId());
        } else {
            drawBaseHouseService.transformToBaseHouse(drawHouse);
        }

        // 户型的资源同步
        this.transformHouseResource(drawHouse, subTask.getTaskId());
    }

    /**
     * 处理硬装产品
     *
     * @param hardProducts
     */
    private void transformBaseProduct(List<DrawBaseProduct> hardProducts) {
        if (hardProducts == null || hardProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        // get transform draw products
        List<TransformProductBO> transformDrawProducts = this.getTransformDrawProducts(hardProducts);
        if (transformDrawProducts == null || transformDrawProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        // 处理产品的model资源
        this.handlerBaseProductModel(transformDrawProducts);

        // save
        this.handlerBaseProduct(transformDrawProducts);
    }

    /**
     * 处理产品的model资源
     *
     * @param transformProducts
     */
    private void handlerBaseProductModel(List<TransformProductBO> transformProducts) {
        List<DrawResFile> drawResFiles = transformProducts.stream().filter(Objects::nonNull).map(TransformProductBO::getDrawResFile).collect(Collectors.toList());

        // add batch model file
        Map<Long, ResModel> resModelMap = drawResFileService.addBatchModelFile(drawResFiles, SystemConfigEnum.BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), true);

        for (TransformProductBO transformProduct : transformProducts) {
            if (transformProduct.getDrawResFile() == null) {
                continue;
            }

            ResModel resModel = resModelMap.get(transformProduct.getDrawResFile().getId());
            if (resModel != null) {
                transformProduct.getDrawBaseProduct().setWindowsU3dmodelId(resModel.getId().intValue());
                transformProduct.setResModel(resModel);
            }
        }

        // 清理资源文件
        // drawResFileService.clearDrawResFileResource(Lists.newArrayList(resModelMap.keySet()));
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

    private void transformSpaceCommon(DrawSpaceCommon drawSpace, Long baseHouseId) {
        log.info("开时转化空间信息数据, {}(spaceId)", drawSpace.getId());

        // 空间灯光文件转换
        this.handlerTransformSpaceCommonModel(drawSpace);

        // 处理空间图片
        drawSpaceCommonService.handlerSpacePic(drawSpace);

        // 转换空间数据
        drawSpace.setOrigin(SpaceCommonStatusConstant.ORIGIN_DRAW);
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
        drawSpace.setDaylightU3dModelId(null);
        drawSpace.setDusklightU3dModelId(null);
        drawSpace.setDaylightU3dModelId(null);
        drawSpace.setStatus(null);
        drawSpace.setPicId(null);

        drawSpaceCommonService.update(drawSpace);
    }

    /**
     * 空间灯光文件转换
     *
     * @param drawSpace
     */
    private void handlerTransformSpaceCommonModel(DrawSpaceCommon drawSpace) {
        // 白天
        DrawResFile daylightModel = drawResFileService.get(Long.valueOf(drawSpace.getDaylightU3dModelId() + ""));
        Long daylightPcU3dModelId = resModelService.createByDrawResFile(daylightModel, SystemConfigEnum.SPACE_MODEL_DAYLIGHT_UPLOAD.getFileKey(), true);
        if (daylightPcU3dModelId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawSpace.setDaylightU3dModelId(Integer.valueOf(daylightPcU3dModelId + ""));

        // 黄昏
        DrawResFile duskLightModel = drawResFileService.get(Long.valueOf(drawSpace.getDusklightU3dModelId() + ""));
        Long duskLightU3dModelId = resModelService.createByDrawResFile(duskLightModel, SystemConfigEnum.SPACE_MODEL_DUSKLIGHT_UPLOAD.getFileKey(), true);
        if (duskLightU3dModelId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawSpace.setDusklightU3dModelId(Integer.valueOf(duskLightU3dModelId + ""));

        // 晚上
        DrawResFile nightlightModel = drawResFileService.get(Long.valueOf(drawSpace.getNightlightU3dModelId() + ""));
        Long nightlightU3dModelId = resModelService.createByDrawResFile(nightlightModel, SystemConfigEnum.SPACE_MODEL_NIGHTLIGHT_UPLOAD.getFileKey(), true);
        if (nightlightU3dModelId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawSpace.setNightlightU3dModelId(Integer.valueOf(nightlightU3dModelId + ""));

        // 清理资源文件
        // drawResFileService.clearDrawResFileResource(Lists.newArrayList(daylightModel.getId(), duskLightModel.getId(), nightlightModel.getId()));
    }

    private void transformDesignTemplet(DrawDesignTemplet drawDesignTemplet, Long spaceCommonId) {
        if (spaceCommonId == null || spaceCommonId <= 0) {
            throw new BusinessException(false, ResponseEnum.SPACE_NOT_FOUND);
        }

        log.info("开发转化样板房信息数据, {}(drawDesignTempletId)", drawDesignTemplet.getId());

        // 处理样板房的配置文件、模型文件
        this.handlerTransformDesignTempletResource(drawDesignTemplet);

        // 处理样板房图片
        drawDesignTempletService.handlerDesigmTempletPic(drawDesignTemplet);

        // set space common
        drawDesignTemplet.setSpaceCommonId(spaceCommonId.intValue());

        /**
         * 标识绘制数据
         * @see com.sandu.common.constant.DesignTempletStatusConstant
         */
        drawDesignTemplet.setOrigin(DesignTempletStatusConstant.ORIGIN_DRAW);

        // 处理样板房
        drawDesignTempletService.handlerDesignTemplet(drawDesignTemplet);

        // 处理转化后的资源回填
        this.handlerDesignTempletResourceCallback(drawDesignTemplet);

        log.info("转化样板房信息数据结束, {}(drawDesignTempletId)", drawDesignTemplet.getId());
    }

    private void handlerDesignTempletResourceCallback(DrawDesignTemplet drawDesignTemplet) {
        List<Integer> resFileIdList = new ArrayList<>();
        resFileIdList.add(Integer.valueOf(drawDesignTemplet.getConfigFileId() + ""));
        resFileIdList.add(Integer.valueOf(drawDesignTemplet.getPcModelU3dId() + ""));
        resFileService.updateBusinessId(resFileIdList, Integer.valueOf(drawDesignTemplet.getDesignTempletId() + ""));

        drawDesignTemplet.setSpaceCommonId(null);
        drawDesignTemplet.setConfigFileId(null);
        drawDesignTemplet.setPcModelU3dId(null);
        drawDesignTemplet.setPutawayState(null);

        // update design_templet_id
        drawDesignTempletService.update(drawDesignTemplet);
    }

    /**
     * 处理样板房的配置文件、模型文件
     */
    private void handlerTransformDesignTempletResource(DrawDesignTemplet drawDesignTemplet) {
        Utils.requireGreaterZero(drawDesignTemplet.getConfigFileId(), "样板房" + drawDesignTemplet.getId() + "的配置文件不能为空");
        DrawResFile drawResFileConfig = drawResFileService.get(Long.valueOf(drawDesignTemplet.getConfigFileId() + ""));
        Long configFileId = resFileService.createByDrawResFile(drawResFileConfig, SystemConfigEnum.DESIGNTEMPLET_CONFIGFILE_UPLOAD.getFileKey(), true);
        if (configFileId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawDesignTemplet.setConfigFileId(Integer.valueOf(configFileId + ""));

        Utils.requireGreaterZero(drawDesignTemplet.getPcModelU3dId(), "样板房" + drawDesignTemplet.getId() + "的PC模型文件不能为空");
        DrawResFile drawResFileModel = drawResFileService.get(Long.valueOf(drawDesignTemplet.getPcModelU3dId() + ""));
        Long pcModelU3dId = resModelService.createByDrawResFile(drawResFileModel, SystemConfigEnum.DESIGNTEMPLET_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), true);
        if (pcModelU3dId == null) {
            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }
        drawDesignTemplet.setPcModelU3dId(Integer.valueOf(pcModelU3dId + ""));

        // 清除资源文件
        // drawResFileService.clearDrawResFileResource(Lists.newArrayList(drawResFileConfig.getId(), drawResFileModel.getId()));
    }

    private void transformDesignTempletProduct(List<DrawDesignTempletProduct> drawDesignTempletProducts, Long designTempletId, List<DrawBaseProduct> hardProducts) {
        if (drawDesignTempletProducts == null || drawDesignTempletProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        log.info("开始转化样板房信息数据, {}(designTempletId)", designTempletId);

        // hard product maps<id, productId>
        Map<Long, DrawBaseProduct> hardProductMaps = hardProducts.stream().collect(Collectors.toMap(DrawBaseProduct::getId, bp -> bp, (p, n) -> n));

        for (DrawDesignTempletProduct dtp : drawDesignTempletProducts) {
            // 非软装产品
            if (!DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(dtp.getCreateProductStatus())) {
                if (dtp.getProductId() == null) {
                    throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
                }

                DrawBaseProduct drawProduct = hardProductMaps.get(dtp.getProductId().longValue());
                if (drawProduct == null || drawProduct.getBaseProductId() == null) {
                    throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
                }

                dtp.setProductId(drawProduct.getBaseProductId().intValue());
                dtp.setBindParentProductid(this.getBindParentProduct(dtp.getBindParentProductid(), hardProductMaps));
            } else {
                log.info("跳过软装白膜产品处理{}(dtpId), {}(productId)", dtp.getId(), dtp.getProductId());
            }

            dtp.setDesignTempletId(designTempletId.intValue());
        }

        // 批量转化样板房产品
        drawDesignTempletProductMapper.batchTransformDesignTempletProduct(drawDesignTempletProducts);

        // 资源回填更新dtpId
        this.handlerDrawDesignTempletProductCallback(drawDesignTempletProducts);

        log.info("转化样板房产品信息数据结束, {}(designTempletId)", designTempletId);
    }

    /**
     * 资源回填更新dtpId
     *
     * @param drawDesignTempletProducts
     */
    private void handlerDrawDesignTempletProductCallback(List<DrawDesignTempletProduct> drawDesignTempletProducts) {
        if (drawDesignTempletProducts == null || drawDesignTempletProducts.isEmpty()) {
            return;
        }

        if (drawDesignTempletProducts.size() <= DEFAULTS_UPDATE_COUNT) {
            drawDesignTempletProductMapper.batchUpdateDrawDesignTempletProductByCallback(drawDesignTempletProducts);
        }

        // > 10
        List<DrawDesignTempletProduct> updates = Lists.newArrayList();
        for (DrawDesignTempletProduct dtp : drawDesignTempletProducts) {
            updates.add(dtp);
            if (updates.size() < DEFAULTS_UPDATE_COUNT) {
                continue;
            }

            drawDesignTempletProductMapper.batchUpdateDrawDesignTempletProductByCallback(updates);
            // clear
            updates.clear();
        }

        if (!updates.isEmpty()) {
            drawDesignTempletProductMapper.batchUpdateDrawDesignTempletProductByCallback(updates);
        }
    }


    /**
     * 处理绑定的产品
     *
     * @param drawBindParentProducts
     * @param hardProductMaps
     * @return
     */
    private String getBindParentProduct(String drawBindParentProducts, Map<Long, DrawBaseProduct> hardProductMaps) {
        if (StringUtils.isEmpty(drawBindParentProducts)) {
            return null;
        }

        if (hardProductMaps == null || hardProductMaps.isEmpty()) {
            return null;
        }

        List<Long> bindParentProducts = Utils.getLongListByStr(drawBindParentProducts);
        if (bindParentProducts == null || bindParentProducts.isEmpty()) {
            return null;
        }

        List<Long> bindParentProductIdList = new ArrayList<>();
        for (Long productId : bindParentProducts) {
            DrawBaseProduct drawProduct = hardProductMaps.get(productId);
            if (drawProduct == null || drawProduct.getBaseProductId() == null) {
                continue;
            }

            bindParentProductIdList.add(drawProduct.getBaseProductId());
        }

        return Utils.listToStr(bindParentProductIdList);
    }

    /**
     * 户型的资源同步
     *
     * @param drawHouse
     * @param taskId
     */
    private void transformHouseResource(DrawBaseHouse drawHouse, Long taskId) {
        drawBaseHouseService.transformHouseResource(drawHouse, taskId);
    }

    private void clearCupboardSpaceByCallback(DrawBaseHouse drawHouse, Long taskId) {
        // 是否第一个空间烘焙
        int taskCount = drawBakeTaskService.getBakeSuccessTask(taskId);
        if (taskCount != DrawBaseHouseConstant.EMPTY_DATA_INIT_FLAG) {
            log.debug("已清空过正式表数据，不需要清空数据");
            return;
        }

        log.info("开始删除正式老数据户型数据，{}(houseId)，{}(taskId)", taskId);
        if (drawHouse.getBaseHouseId() == null || drawHouse.getBaseHouseId() <= 0) {
            return;
        }

        List<Long> emptySpaces = drawSpaceCommonMapper.getClearCupboardSpaceByCallback(drawHouse.getBaseHouseId());
//        List<Long> emptySpaces = drawSpaceCommonService.getEmptyDealSpaceCommon(drawHouse.getBaseHouseId());
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return;
        }

        log.info("需要删除正式户型老数据, emptySpaces ==> {}", emptySpaces);

        // 空间
        drawSpaceCommonService.emptySpaceCommon(DrawBaseHouseConstant.IS_DELETED, emptySpaces);

        // 户型、空间关系
        drawSpaceCommonService.emptyHouseSpaceRelation(DrawBaseHouseConstant.IS_DELETED, emptySpaces);

        // 样板房硬装产品和软装产品
        // 这个查询要在删除design_templet之前执行
        List<Long> designTemplets = drawDesignTempletService.getEmptyDesignTemplet(emptySpaces);
        if (designTemplets != null && !designTemplets.isEmpty()) {
            drawDesignTempletProductService.emptyDesignTempletProduct(DrawBaseHouseConstant.IS_DELETED, designTemplets);
        }

        // 样板房
        drawDesignTempletService.emptyDesignTemplet(DrawBaseHouseConstant.IS_DELETED, emptySpaces);

        // 硬装产品
        List<Long> baseProducts = drawBaseProductService.getEmptyBaseProduct(emptySpaces);
        if (baseProducts != null && !baseProducts.isEmpty()) {
            drawBaseProductService.emptyBaseProduct(DrawBaseHouseConstant.IS_DELETED, baseProducts);
        }

        // 清除draw_* 表相关的obj文件
        // draw_space_common bakeBeforeFileId

        // draw_base_product BakeBeforeFileId

        // 清除obj资源文件
        // drawResFileService.clearDrawResFileResource(Lists.newArrayList());

        log.info("删除正式表的老数据户型数据完成，{}(houseId)", drawHouse.getId());
    }
}
