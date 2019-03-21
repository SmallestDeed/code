package com.sandu.api.house.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.space.bo.DrawSpaceFileBO;

public interface DrawResFileService {

    /**
     * 用法如方法名
     *
     * @param fileVO
     * @return
     * @author huangsongbo
     */
    Long createByUploadFileVO(UploadFileVO fileVO);

    Long add(DrawResFile drawResFile);

    DrawResFile get(Long id);

    List<DrawResFile> addBatchFile(List<UploadFileVO> files);

    List<DrawResFile> listDrawResFileById(List<Integer> u3dOfWindows);

    Map<Long, ResModel> addBatchModelFile(List<DrawResFile> drawResFiles, String fileKey, boolean isEncrypt, boolean isAADir);

    Integer clearDrawResFileResource(List<Long> emptySpaces, Integer isModel);

    Integer clearDrawResFileResourceWithError(List<Long> fileIds);

    Integer batchDeleteDrawResFile(List<Long> fileIds);
    Integer batchDeleteDrawResFile2(List<DrawSpaceFileBO> drawSpaceFiles);

    List<DrawSpaceFileBO> listTaskCleanDrawResFile(Integer limit, Long minFileId, Long maxFileId);
}
