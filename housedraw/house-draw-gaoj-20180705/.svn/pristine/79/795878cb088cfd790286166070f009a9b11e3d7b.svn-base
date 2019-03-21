package com.sandu.service.file.impl;

import java.util.*;

import com.google.common.collect.Lists;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResModelService;
import com.sandu.common.constant.house.SystemConstant;
import com.sandu.util.FileUtils;
import com.sandu.util.UploadUtils;
import lombok.extern.slf4j.Slf4j;
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
public class DrawResFileServiceImpl implements DrawResFileService{

	@Autowired
	private DrawResFileMapper drawResFileMapper;

	@Autowired
    ResModelService resModelService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long createByUploadFileVO(UploadFileVO fileVO) {
		// 参数验证 ->start
		if(fileVO == null){
			return null;
		}
		// 参数验证 ->end
		DrawResFile drawResFile = getDrawFile(fileVO);

		return this.add(drawResFile);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long add(DrawResFile drawResFile) {
		// 参数验证 ->start
		if(drawResFile == null) {
			return null;
		}
		// 参数验证 ->end

		drawResFileMapper.saveDrawResFile(drawResFile);
		return drawResFile.getId() == null ? null : Long.valueOf(drawResFile.getId() + "");
	}

	@Override
	public DrawResFile get(Long id) {
		// 参数验证 ->start
		if (id == null) {
			return null;
		}
		// 参数验证 ->end
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
    public Map<Long, ResModel> addBatchModelFile(List<DrawResFile> drawResFiles, String fileKey, boolean useEncrypt) {
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
            String targetPath = Utils.getValue(fileKey);

            if (useEncrypt) {
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
		String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ randomNumStr;

		drawResFile.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
		drawResFile.setBusinessId(file.getBusinessId() == null? null : file.getBusinessId() + "");
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
     * @param files
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer clearDrawResFileResource(List<Long> files) {
        // draw_res_file
        if (files == null || files.isEmpty()) {
            return -1;
        }

        return drawResFileMapper.clearDrawResFileResource(files);
    }
}
