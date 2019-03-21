package com.sandu.service.res.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sandu.api.house.input.*;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.api.house.service.DrawResHousePicService;
import com.sandu.api.res.service.ResHandlerErrorService;
import com.sandu.api.v2.house.dto.DrawBaseHouseCallbackDTO;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.house.SystemConstant;
import com.sandu.exception.BusinessException;
import com.sandu.util.FileUtils;
import com.sandu.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/13
 */

@Slf4j
@Service
public class ResHandlerErrorServiceImpl implements ResHandlerErrorService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    DrawResHousePicService drawResHousePicService;

    /**
     * 保存户型异常，删除上传的文件资源，
     * 这个方法不参与saveDrawHouseV2方法的事务，另起事务Propagation.REQUIRES_NEW
     * @param houseNew
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerErrorWithSave(DrawBaseHouseNew houseNew) {
        if (houseNew == null || houseNew.getDatas() == null || houseNew.getDatas().isEmpty()) {
            return;
        }

        // 保存户型的主要有截图、户型图片(.png)、户型的还原文件(.sdkj)三个资源文件，异常时删除这三个文件即可
        log.info("保存户型异常，删除处理的资源.begin");
        List<String> files = houseNew.getDatas().stream().filter(f -> !StringUtils.isEmpty(f.getFilePath()))
                .map(DrawFileDataNew::getFilePath).collect(Collectors.toList());
        if (files != null && !files.isEmpty()) {
            FileUtils.delete(files.toArray(new String[]{}));
        }
        log.info("保存户型异常，删除处理的资源.begin");
    }

    /**
     * 保存户型异常，删除上传的文件资源，
     * 这个方法不参与saveDrawHouseV2方法的事务，另起事务Propagation.REQUIRES_NEW
     *
     * @param data
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerErrorWithSave(String data) {
        DrawBaseHouseNew houseNew;
        try {
            houseNew = new ObjectMapper().readValue(data, DrawBaseHouseNew.class);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }

        this.handlerErrorWithSave(houseNew);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerErrorWithSubmit(DrawBaseHouseSubmitDTO dtoNew, String data) {
        dtoNew = this.parseJSON(dtoNew, data, DrawBaseHouseSubmitDTO.class);
        if (dtoNew == null) {
            log.error("提交户型时，删除资源文件异常，JSON参数data格式不准确");
            return;
        }

        log.info("提交户型异常，删除处理的资源.begin");

        List<DrawSpaceCommonDTO> drawSpaces = dtoNew.getSpaceCommonDTOList();
        if (drawSpaces == null || drawSpaces.isEmpty()) {
            return;
        }

        for (DrawSpaceCommonDTO drawSpace : drawSpaces) {
            if (drawSpace.getDrawBaseProductDTOList() == null || drawSpace.getDrawBaseProductDTOList().isEmpty()) {
                continue;
            }

            // 烘焙的.obj文件
            List<Long> productFileIds = drawSpace.getDrawBaseProductDTOList().stream().filter(p -> p.getProductFileId() != null)
                    .map(DrawBaseProductDTO::getProductFileId).collect(Collectors.toList());

            // 空间烘焙文件.txt文件
            productFileIds.add(drawSpace.getSpaceCommonFileId());
            if (!productFileIds.isEmpty()) {
                // 删除obj.txt
                log.info("提交户型异常：删除.obj, txt资源");
                drawResFileService.clearDrawResFileResourceWithError(productFileIds);
            }

            // 空间图片、样板房图片
            Integer spacePicId = drawSpace.getSpacePicId();
            if (spacePicId != null) {
                log.info("提交户型异常：删除图片资源");
                drawResHousePicService.clearDrawResHousePicResourceWithError(Lists.newArrayList(spacePicId.longValue()));
            }
        }

        log.info("提交户型异常，删除处理的资源.end");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerErrorWithCallback(DrawBaseHouseCallbackDTO dtoNew, String data) {
        dtoNew = this.parseJSON(dtoNew, data, DrawBaseHouseCallbackDTO.class);
        if (dtoNew == null) {
            log.error("烘焙户型时，删除资源文件异常，JSON参数data格式不准确");
            return;
        }

        log.info("烘焙处理完成，删除上传的资源.begin");
        // 烘焙接口处理完成时，把本次上传的灯光文件，pc模型文件，配置文件、assetbundle文件做删除处理
        DrawDesignTempletDTO drawDesignTempletDTO = dtoNew.getDrawDesignTempletDTO();
        if (drawDesignTempletDTO != null) {
            List<Long> filedIds = new ArrayList<>();

            // 样板房灯光文件，pc模型文件，配置文件
            filedIds.add(drawDesignTempletDTO.getDaylightPcU3dModelId());
            filedIds.add(drawDesignTempletDTO.getDusklightPcU3dModelId());
            filedIds.add(drawDesignTempletDTO.getNightlightPcU3dModelId());
            filedIds.add(drawDesignTempletDTO.getDesignTempletConfigFileId());
            filedIds.add(drawDesignTempletDTO.getDesignTempletPcModelU3dId());

            // 产品的产品assetbundle文件（未加密的assetbundle文件）
            List<DrawBaseProductDTO> drawBaseProductDTOList = drawDesignTempletDTO.getDrawBaseProductDTOList();
            if (drawBaseProductDTOList != null && !drawBaseProductDTOList.isEmpty()) {
                List<Long> windowsU3dFiles = drawBaseProductDTOList.stream().filter(p -> p.getWindowsU3dmodelId() != null).map(DrawBaseProductDTO::getWindowsU3dmodelId).collect(Collectors.toList());
                filedIds.addAll(windowsU3dFiles);
            }

            drawResFileService.clearDrawResFileResourceWithError(filedIds);
        }

        log.info("烘焙处理完成，删除上传的资源.end");
    }

    @Override
    public void handlerErrorWithCallback(List<String> bakeFiles) {
        if (bakeFiles == null || bakeFiles.isEmpty()) {
            return;
        }

        log.info("烘焙户型处理结束，删除Asset bundle处理的资源.begin");
        FileUtils.delete(SystemConstant.UPLOAD_ROOT, bakeFiles.toArray(new String[]{}));
        log.info("烘焙户型处理结束，删除Asset bundle处理的资源.end");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerErrorWithUpload(List<UploadFileVO> files) {
        log.error("上传文件异常，删除已存储的资源");
        if (files == null || files.isEmpty()) {
            log.info("上传文件异常，没有需要删除的资源");
            return;
        }

        List<String> filePaths = files.stream().filter(f -> !StringUtils.isEmpty(f.getFilePath()))
                .map(UploadFileVO::getFilePath).collect(Collectors.toList());
        if (filePaths != null && !filePaths.isEmpty()) {
            FileUtils.delete(filePaths.toArray(new String[]{}));
        }
    }

    /**
     * 解析JSON
     * @param target
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T parseJSON(T target, String data, Class<T> clazz) {
        if (target == null && !StringUtils.isEmpty(data)) {
            return JSON.parse(data, clazz);
        }

        return target;
    }
}
