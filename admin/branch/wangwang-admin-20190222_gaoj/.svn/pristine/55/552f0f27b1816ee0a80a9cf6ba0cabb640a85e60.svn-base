package com.sandu.web.storage.controller;

import com.sandu.api.storage.model.ResModel;
import com.sandu.api.storage.output.FileVO;
import com.sandu.api.storage.service.ResModelService;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.TransStatus;
import com.sandu.common.exception.StorageException;
import com.sandu.common.uploader.Uploader;
import com.sandu.common.uploader.factory.UploaderFactory;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

import static com.sandu.common.util.file.FilePathUtil.*;
import static com.sandu.constant.Punctuation.SLASH;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.web.storage.controller.StorageController.BASE_MAPPING;
import static org.apache.commons.io.FilenameUtils.getBaseName;

/**
 * creator by bvvy
 */
@SuppressWarnings("unchecked")
@RestController
@Api(tags = "U3dModelUpload", description = "模型上传")
@RequestMapping("/v1/model/u3d/")
public class U3dUploadController {

    @Resource
    private ResModelService resModelService;


    @GetMapping("/queue")
    @ApiOperation(value = "获取没有转化处理的模型")
    public ReturnData<FileVO> queue() {
        ResModel resModel = resModelService.getQueue();
        if (isEmpty(resModel)) {
            return ReturnData.builder().code(ResponseEnum.NOT_CONTENT);
        }
        return ReturnData.builder()
                .code(ResponseEnum.SUCCESS)
                .data(
                        FileVO.builder()
                                .url(formatPath(serverPath(), BASE_MAPPING) + removeStartSlash(resModel.getModelPath()))
                                .resId(resModel.getId())
                                .build()
                );
    }

    @ApiOperation(value = "转化后模型上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "同过 queue获取到的模型id ", paramType = "query", dataType = "string", required = true),
    })
    @PostMapping("/upload")
    public ReturnData<FileVO> u3dUpload(
            @RequestPart("file") MultipartFile file,
            @RequestParam Long modelId) {
        LocalDateTime now = LocalDateTime.now();
        String absolutePath = absolutePath("windowPc", "model", "model", now, file.getOriginalFilename());
        Uploader uploader = UploaderFactory.createUploader("model", file, absolutePath);
        uploader.upload();
        FileVO fileVO = uploader.getFileVO();
        String returnPath = formatPath(serverPath(), BASE_MAPPING) + removeStartSlash(relativePath(absolutePath));
        fileVO.setUrl(returnPath);
        /* 修改数据库*/
        updateModel(modelId, TransStatus.SUCCESS.getCode(), absolutePath, file);
        return ReturnData.builder().code(ResponseEnum.SUCCESS).data(fileVO);
    }

    private void updateModel(Long modelId, String status, String path, MultipartFile file) {
        resModelService.getResModelDetail(modelId).ifPresent(resModel -> {
            resModel.setModelSize(String.valueOf(file.getSize()));
            resModel.setModelFileName(getBaseName(path));
            resModel.setModelName(getBaseName(path));
            resModel.setModelSuffix(getSuffix(path));
            resModel.setModelPath(SLASH + relativePath(path));
            resModel.setTransStatus(status);
            resModel.setIsDeleted(0);
            resModel.setGmtModified(new Date());
            resModelService.saveResModel(resModel);
        });
    }

    @PostMapping("/trans/fail")
    @ApiOperation(value = "转化失败后调用接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "同过 queue获取到的res_id ", paramType = "query", dataType = "string", required = true),
    })
    public ReturnData transFail(@RequestParam Long modelId) {
        resModelService.getResModelDetail(modelId).ifPresent(resModel -> {
            resModel.setTransStatus(TransStatus.FAIL.getCode());
            resModel.setIsDeleted(0);
            resModelService.saveResModel(resModel);
        });
        return ReturnData.builder().code(ResponseEnum.SUCCESS);
    }


    /**
     * 异常
     * controller级别异常
     *
     * @param ex 异常
     * @return 信息
     */
    @ExceptionHandler({StorageException.class})
    public ReturnData handlerStorageException(StorageException ex) {
        return ReturnData.builder().code(ResponseEnum.ERROR).data(ex.getMessage());
    }
}
