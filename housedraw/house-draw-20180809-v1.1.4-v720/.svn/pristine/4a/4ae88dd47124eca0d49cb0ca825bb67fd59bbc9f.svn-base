package com.sandu.api.res.service;

import com.sandu.api.house.input.DrawBaseHouseNew;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.v2.house.dto.DrawBaseHouseCallbackDTO;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/13
 */
public interface ResHandlerErrorService {
    /**
     * 保存户型记录时，处理资源的异常
     */
    void handlerErrorWithSave(String data);
    void handlerErrorWithSave(DrawBaseHouseNew houseNew);

    /**
     * 提交户型时，处理资源的异常
     */
    void handlerErrorWithSubmit(DrawBaseHouseSubmitDTO dtoNew, String data);

    /**
     * 烘焙回调时，处理资源的异常
     *
     * @param dtoNew
     * @param data
     */
    void handlerErrorWithCallback(DrawBaseHouseCallbackDTO dtoNew, String data);

    /**
     * 烘焙异常时，删除文件资源
     *
     * @param bakeFiles
     */
    void handlerErrorWithCallback(List<String> bakeFiles);

    /**
     * 上传文件时，处理资源的异常
     */
    void handlerErrorWithUpload(List<UploadFileVO> files);
}
