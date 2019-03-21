package com.sandu.service.file.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResModelService;
import com.sandu.api.space.bo.DrawSpaceFileBO;
import com.sandu.common.constant.house.SystemConstant;
import com.sandu.common.mysql.MysqlExecutor;
import com.sandu.util.FileUtils;
import com.sandu.util.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.common.constant.FileKey;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.service.file.dao.DrawResFileMapper;
import com.sandu.util.Utils;

@Slf4j
@Service
public class DrawResFileServiceImpl implements DrawResFileService {

    @Autowired
    private DrawResFileMapper drawResFileMapper;

    @Autowired
    ResModelService resModelService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long createByUploadFileVO(UploadFileVO fileVO) {
        if (fileVO == null) {
            return null;
        }

        DrawResFile drawResFile = getDrawFile(fileVO);

        return this.add(drawResFile);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long add(DrawResFile drawResFile) {
        if (drawResFile == null) {
            return null;
        }

        drawResFileMapper.saveDrawResFile(drawResFile);
        return drawResFile.getId() == null ? null : Long.valueOf(drawResFile.getId() + "");
    }

    @Override
    public DrawResFile get(Long id) {
        if (id == null) {
            return null;
        }

        return drawResFileMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<DrawResFile> addBatchFile(List<UploadFileVO> files) {
        final List<DrawResFile> list = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (UploadFileVO file : files) {
                list.add(getDrawFile(file));
            }

            drawResFileMapper.addBatchFile(list);
        }

        return list;
    }

    @Override
    public List<DrawResFile> listDrawResFileById(List<Integer> u3dOfWindows) {
        if (u3dOfWindows == null || u3dOfWindows.isEmpty()) {
            log.warn("参数u3dOfWindow为空");
            return null;
        }

        return drawResFileMapper.listDrawResFileById(u3dOfWindows);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<Long, ResModel> addBatchModelFile(List<DrawResFile> drawResFiles, String fileKey, boolean isEncrypt, boolean isAADir) {
        if (drawResFiles == null || drawResFiles.isEmpty()) {
            log.warn("参数 drawResFiles 为空");
            return new HashMap<>();
        }

        Map<Long, ResModel> resModels = new HashMap<>();
        for (DrawResFile drawResFile : drawResFiles) {
            if (drawResFile == null) {
                log.warn("drawResFile 为空");
                continue;
            }

            String resourcesPath = drawResFile.getFilePath().replace(SystemConstant.UPLOAD_ROOT, "");
            String newFileName = Utils.getNewFileName(resourcesPath);
            String targetPath = isAADir ? Utils.getValueWithReplaceable(fileKey, Utils.RES_DIR_AA_PREFIX) : Utils.getValue(fileKey);

            if (isEncrypt) {
                FileUtils.encryptFile(Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null),
                        Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
            } else {
                UploadUtils.copyfile(Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null),
                        Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
            }

            ResModel resModel = this.getResModel(drawResFile, newFileName, targetPath, fileKey);
            resModels.put(drawResFile.getId(), resModel);
        }

        if (!resModels.isEmpty()) {
            resModelService.addBatchModelFile(Lists.newArrayList(resModels.values()));
        }

        return resModels;
    }

    private ResModel getResModel(DrawResFile drawResFile, String newFileName,
                                 String targetPath, String fileKey) {
        String randomNumStr = Utils.generateRandomDigitString(6);
        String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
        Date now = new Date();

        ResModel resModel = new ResModel();
        resModel.setModelCode(sysCode);
        resModel.setModelName(newFileName);
        resModel.setModelFileName(newFileName);
        resModel.setModelType("无");
        resModel.setModelSize(drawResFile.getFileSize());
        resModel.setModelSuffix(newFileName.substring(newFileName.lastIndexOf("."), newFileName.length()));
        resModel.setModelLevel("0");
        resModel.setModelPath(targetPath + newFileName);
        resModel.setModelOrdering(0);
        resModel.setSysCode(sysCode);
        resModel.setCreator(drawResFile.getCreator());
        resModel.setGmtCreate(now);
        resModel.setFileKey(resModelService.getFileKey(fileKey));
        resModel.setModifier(drawResFile.getModifier());
        resModel.setGmtModified(now);
        resModel.setIsDeleted(0);

        return resModel;
    }

    private DrawResFile getDrawFile(UploadFileVO file) {
        DrawResFile drawResFile = new DrawResFile();
        String randomNumStr = Utils.generateRandomDigitString(6);
        String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;

        drawResFile.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
        drawResFile.setBusinessId(file.getBusinessId() == null ? null : file.getBusinessId() + "");
        drawResFile.setFileOriginalName(file.getFileOriginalName());
        drawResFile.setFileName(file.getFileName());
        drawResFile.setFilePath(Utils.dealWithPath(file.getFilePath().replace(Utils.getValue(SystemConfigEnum.UPLOAD_ROOT_DIR.getKey()), ""), null));
        drawResFile.setFileSize(file.getFileSize());
        drawResFile.setFileSuffix(file.getFileSuffix());
        drawResFile.setFileType(file.getFileType());
        drawResFile.setSysCode(sysCode);
        drawResFile.setFileCode(sysCode);
        drawResFile.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
        drawResFile.setCreator("draw-house");
        drawResFile.setModifier("draw-house");
//		drawResFile.setFileKey();
        drawResFile.setGmtCreate(Utils.parseDate(Utils.getCurrentDate(), "yyyy-MM-dd"));
        drawResFile.setFileKey(FileKey.HOUSE_DRAW_FILE.getFileKey());

        return drawResFile;
    }

    /**
     * 删除draw_res_file 表的数据
     *
     * @param emptySpaces
     * @param isModel     null清理obj文件、不为null清理assblue*文件
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer clearDrawResFileResource(List<Long> emptySpaces, Integer isModel) {
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            return -1;
        }

        // 根据空间id查询需要清除的产品资源
        List<DrawSpaceFileBO> drawSpaceFileBOS = drawResFileMapper.listDrawResFileByDrawSpaceId(emptySpaces, isModel);
        if (drawSpaceFileBOS == null || drawSpaceFileBOS.isEmpty()) {
            log.info("没有需要清除的空间资源，emptySpaces =>", emptySpaces);
            return -1;
        }

        List<String> filePaths = drawSpaceFileBOS.stream().filter(f -> !StringUtils.isEmpty(f.getFilePath()))
                .map(DrawSpaceFileBO::getFilePath).collect(Collectors.toList());
        if (filePaths != null && !filePaths.isEmpty()) {
            FileUtils.delete(filePaths.toArray(new String[]{}));
        }

        // 需要删除的文件id
        List<Long> fileIds = drawSpaceFileBOS.stream().filter(f -> f.getFileId() != null).map(DrawSpaceFileBO::getFileId).collect(Collectors.toList());
        return this.batchDeleteDrawResFile(fileIds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer clearDrawResFileResourceWithError(List<Long> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return null;
        }

        // 根据文件id从数据中批量的检索出需要文件的路径
        List<DrawSpaceFileBO> drawSpaceFileBOS = drawResFileMapper.listDrawResFileByFileIds(fileIds);
        List<String> filePaths = drawSpaceFileBOS.stream().filter(f -> !StringUtils.isEmpty(f.getFilePath()))
                .map(DrawSpaceFileBO::getFilePath).collect(Collectors.toList());
        if (filePaths != null && !filePaths.isEmpty()) {
            FileUtils.delete(filePaths.toArray(new String[]{}));
        }

        // 删除文件在表里的记录
        return this.batchDeleteDrawResFile(fileIds);
    }

    /**
     * 批量删除数据库的资源表数据，默认每次删除100条数据
     *
     * @param fileIds
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer batchDeleteDrawResFile(List<Long> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return -1;
        }

        log.info("删除空间资源数据库记录，fileIds => {}", fileIds);

        // 如果需要删除的数据，小于默认值（100）直接执行删除动作
        if (fileIds.size() <= MysqlExecutor.BATCH.DELETE.getCount()) {
            return drawResFileMapper.clearDrawResFileResource(fileIds);
        }

        Integer updateCount = 0;
        List<Long> deleteFiles = new ArrayList<>();

        for (Long file : fileIds) {
            deleteFiles.add(file);
            // 每一百条数据删除一次
            if (deleteFiles.size() >= MysqlExecutor.BATCH.DELETE.getCount()) {
                updateCount += drawResFileMapper.clearDrawResFileResource(deleteFiles);
                deleteFiles.clear();
            }
        }

        // 最后不足的100的数据删除
        if (!deleteFiles.isEmpty()) {
            updateCount += drawResFileMapper.clearDrawResFileResource(deleteFiles);
        }

        return updateCount;
    }

    /**
     * 批量删除数据库的资源表数据，默认每次删除100条数据
     *
     * @param drawSpaceFiles
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer batchDeleteDrawResFile2(List<DrawSpaceFileBO> drawSpaceFiles) {
        if (drawSpaceFiles == null || drawSpaceFiles.isEmpty()) {
            return -1;
        }

        // log.info("删除空间资源数据库记录，fileIds => {}", drawSpaceFiles);

        // 如果需要删除的数据，小于默认值（100）直接执行删除动作
//        if (drawSpaceFiles.size() <= MysqlExecutor.BATCH.DELETE.getCount()) {
//            return drawResFileMapper.clearDrawResFileResource2(drawSpaceFiles);
//        }

        Integer updateCount = 0;
        List<Long> deleteFiles = new ArrayList<>();

        for (DrawSpaceFileBO file : drawSpaceFiles) {
            deleteFiles.add(file.getFileId());
            // 每一百条数据删除一次
            if (deleteFiles.size() >= MysqlExecutor.BATCH.DELETE.getCount()) {
                updateCount += drawResFileMapper.clearDrawResFileResource(deleteFiles);
                deleteFiles.clear();
            }
        }

        // 最后不足的100的数据删除
        if (!deleteFiles.isEmpty()) {
            updateCount += drawResFileMapper.clearDrawResFileResource(deleteFiles);
        }

        return updateCount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrawSpaceFileBO> listTaskCleanDrawResFile(Integer limit, Long minFileId, Long maxFileId) {
        return drawResFileMapper.listTaskCleanDrawResFile(limit, minFileId, maxFileId);
    }
}
