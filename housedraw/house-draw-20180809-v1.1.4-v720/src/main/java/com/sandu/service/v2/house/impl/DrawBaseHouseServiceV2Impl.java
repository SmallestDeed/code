package com.sandu.service.v2.house.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sandu.api.fix.model.FixCupboardSubmitBO;
import com.sandu.api.fix.service.BakeTransformCallback;
import com.sandu.api.fix.service.DrawSpaceSubmitFilter;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawDesignTempletDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.model.*;
import com.sandu.api.house.service.*;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.res.service.ResHandlerErrorService;
import com.sandu.api.v2.house.bo.CallbackTransformBO;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.dto.DrawBaseHouseCallbackDTO;
import com.sandu.api.v2.house.service.*;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.bake.BakeCallbackStatus;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.DrawDesignTempletProductConstant;
import com.sandu.common.constant.house.FixType;
import com.sandu.exception.BizException;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.util.Regex;
import com.sandu.util.http.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DrawBaseHouseServiceV2Impl implements DrawBaseHouseServiceV2 {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DrawFlowGeneralService drawSpaceCommonServiceV2;

    @Autowired
    DrawFlowGeneralService drawDesignTempletServiceV2;

    @Autowired
    DrawFlowGeneralService drawBaseProductServiceV2;

    @Autowired
    DrawFlowGeneralService drawDesignTempletProductServiceV2;

    @Autowired
    DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    DrawBakeTaskDetailService drawBakeTaskDetailService;

    @Autowired
    DrawBaseHouseService drawBaseHouseService;

    @Autowired
    DesignTempletJumpPositionRelService designTempletJumpPositionRelService;

    @Autowired
    DrawSpaceCommonService drawSpaceCommonService;

    @Autowired
    DrawDesignTempletService drawDesignTempletService;

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    DrawDesignTempletProductService drawDesignTempletProductService;

    @Autowired
    CleanDrawHouseHandler generalBakeCallbackCleanHandler;

    @Autowired
    ResHandlerErrorService resHandlerErrorService;

    @Autowired
    @Qualifier("generalBakeTransformCallbackImpl")
    BakeTransformCallback generalTransformCallback;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void submit(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginBO) {
        // handler draw space
        List<DrawSpaceBO> drawSpaceBOS = this.getDrawSpaceBOS(dtoNew, loginBO);
        this.handlerSave(drawSpaceBOS, dtoNew.getHouseId().longValue());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<DrawSpaceBO> submitByFixCupboard(UserLoginBO loginBO,
                                                 FixCupboardSubmitBO fixCupboard,
                                                 DrawSpaceSubmitFilter filter) {
        DrawBaseHouseSubmitDTO newSubmit = new DrawBaseHouseSubmitDTO();
        newSubmit.setHouseId(fixCupboard.getHouseId().intValue());
        newSubmit.setSpaceCommonDTOList(fixCupboard.getSpaceCommonDTOList());

        // handler draw space
        List<DrawSpaceBO> drawSpaceBOS = this.getDrawSpaceBOS(newSubmit, loginBO, fixCupboard.getDrawHouse(), filter);

        // 标记修复类型，1、修复橱柜
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            DrawSpaceCommon drawSpaceCommon = drawSpaceBO.getDrawSpaceCommon();
            if (drawSpaceCommon != null) {
                drawSpaceCommon.setFixType(FixType.FIX_CUPBOARD.getStatus());
            }

            DrawDesignTemplet drawDesignTemplet = drawSpaceBO.getDrawDesignTemplet();
            if (drawDesignTemplet != null) {
                drawDesignTemplet.setFixType(FixType.FIX_CUPBOARD.getStatus());
            }
        }

        // save
        this.handlerSave(drawSpaceBOS);

        return drawSpaceBOS;
    }

    /**
     * 处理提交的空间、样板房、产品
     *
     * @param dtoNew
     * @param loginBO
     * @return
     */
    private List<DrawSpaceBO> getDrawSpaceBOS(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginBO) {
        return this.getDrawSpaceBOS(dtoNew, loginBO, dtoNew.getDrawHouse(), null);
    }

    /**
     * 处理提交的空间、样板房、产品
     *
     * @param dtoNew
     * @param loginBO
     * @param drawHouse
     * @param drawSpaceFilter
     * @return
     */
    private List<DrawSpaceBO> getDrawSpaceBOS(DrawBaseHouseSubmitDTO dtoNew,
                                              UserLoginBO loginBO, DrawBaseHouse drawHouse,
                                              DrawSpaceSubmitFilter drawSpaceFilter) {
        DrawHouseSubmitBO houseSubmitBO = new DrawHouseSubmitBO();
        houseSubmitBO.setLoginBO(loginBO);
        houseSubmitBO.setDrawHouse(drawHouse);

        List<DrawSpaceBO> drawSpaceBOS = new ArrayList<>();
        for (DrawSpaceCommonDTO drawSpace : dtoNew.getSpaceCommonDTOList()) {
            DrawSpaceBO drawSpaceBO = new DrawSpaceBO();
            drawSpaceBO.setDrawSpaceCommonDTO(drawSpace);
            houseSubmitBO.setDrawSpaceBO(drawSpaceBO);

            // 处理空间
            DrawSpaceCommon drawSpaceCommon = drawSpaceCommonServiceV2.handlerHouseSubmit(houseSubmitBO);
            drawSpaceBO.setDrawSpaceCommon(drawSpaceCommon);

            // filter space
            if (drawSpaceFilter != null && drawSpaceFilter.test(drawSpaceCommon)) {
                continue;
            }

            // 处理样板房
            DrawDesignTemplet drawDesignTemplet = drawDesignTempletServiceV2.handlerHouseSubmit(houseSubmitBO);
            drawSpaceBO.setDrawDesignTemplet(drawDesignTemplet);
            // 处理 base product
            // 处理 design templet product
            drawBaseProductServiceV2.handlerHouseSubmit(houseSubmitBO);

            // add draw space bo
            drawSpaceBOS.add(drawSpaceBO);
        }

        return drawSpaceBOS;
    }

    /**
     * 保存处理
     *
     * @param drawSpaceBOS
     * @param houseId
     * @return
     */
    private Integer handlerSave(List<DrawSpaceBO> drawSpaceBOS, Long houseId) {
        int updateCount = this.handlerSave(drawSpaceBOS);
        // 户型的总面积处理
        updateCount += handlerHouseArea(drawSpaceBOS, houseId);

        return updateCount;
    }

    /**
     * 修复橱柜save by fix cupboard submit
     *
     * @param drawSpaceBOS
     * @return
     */
    private Integer handlerSave(List<DrawSpaceBO> drawSpaceBOS) {
        // 空间
        int updateCount = drawSpaceCommonServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);
        // 样板房
        updateCount += drawDesignTempletServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);
        // 产品
        updateCount += drawBaseProductServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);
        // 样板房产品
        updateCount += drawDesignTempletProductServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);

        return updateCount;
    }

    /**
     * 户型的总面积处理
     *
     * @param drawSpaceBOS
     * @param houseId
     * @return
     */
    private int handlerHouseArea(List<DrawSpaceBO> drawSpaceBOS, Long houseId) {
        // 户型的总面积处理
        BigDecimal totalArea = new BigDecimal(0);
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            // 累计空间面积
            totalArea = this.getSpaceTotalArea(totalArea, drawSpaceBO.getDrawSpaceCommonDTO().getSpaceCommonArea());
        }

        // 更新户型的总面积
        DrawBaseHouse drawHouse = new DrawBaseHouse();
        drawHouse.setTotalArea(Math.round(totalArea.doubleValue()) + "");
        drawHouse.setId(houseId);
        return drawBaseHouseMapper.updateDrawBaseHouse(drawHouse);
    }

    /**
     * 累计空间面积
     *
     * @param totalArea
     * @param spaceTotalArea
     * @return
     */
    private BigDecimal getSpaceTotalArea(BigDecimal totalArea, String spaceTotalArea) {
        return (org.apache.commons.lang3.StringUtils.isNoneBlank(spaceTotalArea) && spaceTotalArea.matches(Regex.DOUBLE_NUMBER.getValue()))
                ? totalArea.add(new BigDecimal(spaceTotalArea)) : totalArea;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String callback(DrawBaseHouseCallbackDTO dtoNew) {
        return callback(dtoNew, generalBakeCallbackCleanHandler);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String callback(DrawBaseHouseCallbackDTO dtoNew,
                           CleanDrawHouseHandler handler,
                           BakeTransformCallback... transformCallbacks) {
        // 处理过程中生成的文件，如果整个接口处理异常，对这些资源进行删除处理
        List<String> bakeFiles = new ArrayList<>();
        try {
            // 回调
            List<BakeTransformCallback> callbacks = Lists.newArrayList(generalTransformCallback);
            if (transformCallbacks != null && transformCallbacks.length > 0) {
                callbacks.addAll(Lists.newArrayList(transformCallbacks));
            }

            return this.handlerCallback(dtoNew, bakeFiles, handler, callbacks.toArray(new BakeTransformCallback[]{}));
        } catch (Exception e) {
            // TODO 下版本功能
            resHandlerErrorService.handlerErrorWithCallback(bakeFiles);

            throw e;
        } finally {
            // 处理成功时，删除上传的未加密资源文件
            resHandlerErrorService.handlerErrorWithCallback(dtoNew, null);
        }
    }

    /**
     * @param dtoNew
     * @param bakeFiles
     * @param handler
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String handlerCallback(DrawBaseHouseCallbackDTO dtoNew,
                                  List<String> bakeFiles,
                                  CleanDrawHouseHandler handler,
                                  BakeTransformCallback... transformCallbacks) {
        log.info("正在处理烘焙任务{}(taskDetailId)", dtoNew.getTaskDetailId());

        // 获取烘焙的子任务
        DrawBakeTaskDetail subTask = this.getDrawBakeTaskDetailByValid(dtoNew);

        // 任务被删除
        if (Objects.equals(subTask.getIsDeleted(), DrawBaseHouseConstant.IS_DELETED)) {
            return dtoNew.getTaskDetailId() + "任务不存在或被删除了";
        }

        // 已被烘焙的任务
        if (Objects.equals(subTask.getStatus(), DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS)) {
            return dtoNew.getTaskDetailId() + "任务已烘焙过，不能重复烘焙";
        }

        // 烘焙机处理任务失败时（status = 1），后台记录错误日志，并更新bake_fail_count次数，
        // 同时删除本次上传的assetbundle资源文件
        if (Objects.equals(dtoNew.getStatus(), BakeCallbackStatus.FAILURE.getStatus())) {
            log.warn("{}(taskDetailId)烘焙任务处理异常", dtoNew.getTaskDetailId());
            // failed => 后台记录错误日志，并更新bake_fail_count次数
            this.handlerCallbackError(dtoNew, null);

            // 删除本次上传的assetbundle资源文件
            resHandlerErrorService.handlerErrorWithCallback(dtoNew, null);

            return BakeCallbackStatus.FAILURE.getMessage();
        } else {
            DrawDesignTempletDTO drawDesignTempletDTO = dtoNew.getDrawDesignTempletDTO();
            if (drawDesignTempletDTO == null) {
                throw new BizException(("参数drawDesignTempletDTO错误！"));
            }

            // 处理烘焙任务的数据异常
            if (isErrorTask(subTask)) {
                return BakeCallbackStatus.SUCCEED.getMessage();
            }

            // 更新产品的挂节点/模型文件、灯光文件、pc模型文件、配置文件等烘焙完成的资源信息
            this.handlerBakeCallbackResource(dtoNew, subTask);

            // 处理烘焙任务
            // base_house、base_product 、space_common
            // design_templet 、design_templet_product
            this.handlerTransform(subTask, bakeFiles, handler, transformCallbacks);

            log.info("{}(taskDetailId)烘焙任务处理完成", dtoNew.getTaskDetailId());

            return BakeCallbackStatus.SUCCEED.getMessage();
        }
    }

    /**
     * 处理烘焙任务的数据异常
     */
    private boolean isErrorTask(DrawBakeTaskDetail subTask) {
        DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.getSpaceCommon2(subTask.getSpaceId());
        if (drawSpaceCommon == null) {
            log.warn("{}(spaceId)未找到空间或被删除", subTask.getSpaceId());
            drawBakeTaskDetailService.updateTaskDetailStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS, subTask.getId());
            return true;
        }

        DrawBaseHouse drawHouse = drawBaseHouseService.get(drawSpaceCommon.getDrawBaseHouseId());
        if (Objects.equals(DrawBaseHouseConstant.IS_DELETED, drawSpaceCommon.getIsDeleted())) {
            log.warn("{}(spaceId)空间被删除", subTask.getSpaceId());
            drawBaseHouseService.handlerHouseStatus(drawHouse, subTask.getId());
            return true;
        }

        List<DrawDesignTemplet> drawDesignTemplets = drawDesignTempletService.findAllBySpaceCommonId2(subTask.getSpaceId());
        if (drawDesignTemplets == null || drawDesignTemplets.isEmpty()) {
            log.warn("通过{}(spaceId)未找到样板房信息或被删除", subTask.getSpaceId());
            drawBaseHouseService.handlerHouseStatus(drawHouse, subTask.getId());
            return true;
        }

        DrawDesignTemplet drawDesignTemplet = drawDesignTemplets.get(0);
        if (drawDesignTemplet == null || Objects.equals(DrawBaseHouseConstant.IS_DELETED, drawDesignTemplet.getIsDeleted())) {
            log.warn("通过{}(spaceId)未找到样板房信息或被删除", subTask.getSpaceId());
            drawBaseHouseService.handlerHouseStatus(drawHouse, subTask.getId());
            return true;
        }

        return false;
    }

    @Override
    public DrawBakeTaskDetail getDrawBakeTaskDetailByValid(DrawBaseHouseCallbackDTO dtoNew) {
        if (dtoNew.getTaskDetailId() == null) {
            throw new BizException(("taskDetailId参数为空"));
        }

        DrawBakeTaskDetail subTask = drawBakeTaskDetailService.getSubTask(dtoNew.getTaskDetailId());
        if (subTask == null) {
            throw new BizException((dtoNew.getTaskDetailId() + "任务不存在"));
        }

        Long drawSpaceId = subTask.getSpaceId();
        if (drawSpaceId == null) {
            throw new BizException(("drawSpaceId为空"));
        }

        return subTask;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerCallbackError(DrawBaseHouseCallbackDTO dtoNew, String data) {
        if (dtoNew == null) {
            if (StringUtils.isEmpty(data)) {
                throw new BusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
            }

            try {
                dtoNew = objectMapper.readValue(data, DrawBaseHouseCallbackDTO.class);
            } catch (IOException e) {
                throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
            }

            if (dtoNew == null) {
                throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
            }
        }

        String bakeIpAddress = HttpContextUtils.getIpAddress();
        DrawBakeTaskDetail record = new DrawBakeTaskDetail();
        record.setBakeFailCount(1);
        record.setGmtModified(new Date());
        record.setId(dtoNew.getTaskDetailId());
        record.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_FAIL);
        record.setMessage("Bake Client IP => " + bakeIpAddress + "\n" + dtoNew.getMessage());

        log.warn("烘焙任务失败处理 {}(taskDetailId), {}(bakeIpAddress), message ==> {}", dtoNew.getTaskDetailId(), bakeIpAddress, dtoNew.getMessage());

        // add failed record
        drawBakeTaskDetailService.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handlerBakeCallbackResource(DrawBaseHouseCallbackDTO dtoNew, DrawBakeTaskDetail subTask) {
        // 处理资源
        DrawDesignTemplet drawDesignTemplet = drawBaseHouseService.updateResource(dtoNew.getDrawDesignTempletDTO(), subTask.getSpaceId());
        drawBaseHouseService.updateProduct(dtoNew.getDrawDesignTempletDTO(), drawDesignTemplet);
    }

    /**
     * 转化烘焙的户型相关的数据
     *
     * @param subTask
     * @param callback
     */
    private void handlerTransform(DrawBakeTaskDetail subTask, List<String> bakeFiles,
                                  CleanDrawHouseHandler handler, BakeTransformCallback... callback) {
        DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.get(subTask.getSpaceId());
        if (drawSpaceCommon == null || drawSpaceCommon.getId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.SPACE_NOT_FOUND);
        }

        // 样板房
        DrawDesignTemplet drawDesignTemplet = drawDesignTempletService.getDrawDesignTempletBySpaceCommonId(subTask.getSpaceId());
        if (drawDesignTemplet == null || drawDesignTemplet.getId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.DESIGN_TEMPLET_NOT_FOUND);
        }

        // 户型
        DrawBaseHouse drawHouse = drawBaseHouseService.get(drawSpaceCommon.getDrawBaseHouseId());
        if (drawHouse == null || drawHouse.getId() == null) {
            throw new DrawBusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        // 处理旧的户型数据
        handler.cleanOldDrawHouse(drawHouse, subTask);

        // 转换户型
        this.transformBaseHouse(drawHouse, subTask);

        CallbackTransformBO transformBO = new CallbackTransformBO();
        transformBO.setDrawHouse(drawHouse);
        transformBO.setDrawSpace(drawSpaceCommon);
        transformBO.setDrawDesignTemplet(drawDesignTemplet);
        transformBO.setBakeFiles(bakeFiles == null ? Lists.newArrayList() : bakeFiles);

        // 处理空间转换、图片、配置等
        drawSpaceCommonServiceV2.handlerCallbackTransform(transformBO);

        // 处理样板房转换、图片、配置等
        drawDesignTempletServiceV2.handlerCallbackTransform(transformBO);

        // 样板房产品列表
        List<DrawBaseProduct> drawProducts = drawBaseProductService.findAllByDrawDesignTempletId(drawDesignTemplet.getId());
        if (drawProducts == null || drawProducts.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PRODUCT_NOT_FOUND);
        }

        // 处理硬装产品
        // 剔除软装白膜(因为正是表中已经存在了软装白膜产品数据, 所以不需要重复新增)
        // create_product_status = 0 时表示软装
        List<DrawBaseProduct> hardProducts = drawProducts.stream().filter(p -> !DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(p.getCreateProductStatus())).collect(Collectors.toList());
        transformBO.setHardProducts(hardProducts);
        drawBaseProductServiceV2.handlerCallbackTransform(transformBO);

        // 处理样板房产品
        List<DrawDesignTempletProduct> drawDesignTempletProducts = drawDesignTempletProductService.findAllByDesignTempletId(drawDesignTemplet.getId());
        transformBO.setDrawDesignTempletProducts(drawDesignTempletProducts);
        drawDesignTempletProductServiceV2.handlerCallbackTransform(transformBO);

        // 处理户型的状态和资源
        // created by songjianming@sanduspace.cn on 2018/2/6
        drawBaseHouseService.handlerHouseStatus(drawHouse, subTask.getId());

        // 回调执行，v720门穿越、全文检索mq推送产品处理
        if (callback != null && callback.length > 0) {
            Arrays.stream(callback).forEach(t -> t.apply(transformBO));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transformBaseHouse(DrawBaseHouse drawHouse, DrawBakeTaskDetail subTask) {
        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
            log.info("户型 {}(drawBaseHouseId)已经被转换过, 不需要再转化", drawHouse.getId());
        } else {
            drawBaseHouseService.transformToBaseHouse(drawHouse);
        }

        // 户型的资源同步
        drawBaseHouseService.transformHouseResource(drawHouse, subTask.getTaskId());
    }
}
