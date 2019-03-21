package com.sandu.service.file.impl;

import java.util.*;

import com.sandu.api.res.service.ResModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.res.model.ResFile;
import com.sandu.api.res.service.ResFileService;
import com.sandu.common.constant.house.SystemConstant;
import com.sandu.service.file.dao.ResFileMapper;
import com.sandu.util.FileUtils;
import com.sandu.util.UploadUtils;
import com.sandu.util.Utils;

@Service("resFileService")
public class ResFileServiceImpl implements ResFileService {

	private static final Logger LOG = LoggerFactory.getLogger(ResFileServiceImpl.class);
	
	@Autowired
	private ResFileMapper resFileMapper;

	@Autowired
	private ResModelService resModelService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long createByDrawResFile(DrawResFile drawResFile, String fileKey, boolean needEncrypt) {
		String logMes = "function:ResFileServiceImpl.createByDrawResFile->";

		// 参数验证 ->start
		if (drawResFile == null) {
			LOG.warn("参数drawResFile为空");
			return null;
		}
		// 参数验证 ->end

		// 复制物理文件 ->start
		String resourcesPath = drawResFile.getFilePath().replace(SystemConstant.UPLOAD_ROOT, "");
		String newFileName = Utils.getNewFileName(resourcesPath);
		String targetPath = Utils.getValue(fileKey);

		boolean success;
		if (needEncrypt) {
			FileUtils.encryptFile(Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null), 
					Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
			success = true;
		} else {
			success = UploadUtils.copyfile(Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null), 
					Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
		}

		if (!success) {
			LOG.error("{}拷贝文件失败,{} to {}", logMes, Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null),
					Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
			return null;
		}
		// 复制物理文件 ->end

		// 转换数据 ->start
		String randomNumStr = Utils.generateRandomDigitString(6);
		String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
		Date now = new Date();

		ResFile resFile = new ResFile();
		resFile.setFileCode(sysCode);
		resFile.setFileName(newFileName);
		resFile.setFileOriginalName(newFileName);
		resFile.setFileType("无");
		resFile.setFileSize(drawResFile.getFileSize());
		resFile.setFileSuffix(newFileName.substring(newFileName.lastIndexOf("."), newFileName.length()));
		resFile.setFileLevel("0");
		resFile.setFilePath(targetPath + newFileName);
		resFile.setFileOrdering(0);
		resFile.setSysCode(sysCode);
		resFile.setCreator(drawResFile.getCreator());
		resFile.setGmtCreate(now);
		resFile.setModifier(drawResFile.getModifier());
		resFile.setGmtModified(now);
		resFile.setIsDeleted(0);
		resFile.setFileKey(resModelService.getFileKey(fileKey));
		// 注释原因:等待和回填
		/*
		 * resFile.setBusinessIds(businessIds); 
		 * resFile.setBusinessId(businessId);
		 */
		// 转换数据 ->end

		return this.add(resFile);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long add(ResFile resFile) {
		// 参数验证 ->start
		if (resFile == null) {
			return null;
		}
		// 参数验证 ->end

		resFileMapper.insertSelective(resFile);
		return resFile.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBusinessId(List<Integer> resModelIdList, Integer businessId) {
		// 参数验证 ->start
		if (resModelIdList == null || resModelIdList.size() == 0) {
			return;
		}
		if (businessId == null) {
			return;
		}
		// 参数验证 ->end

		resFileMapper.updateBusinessId(resModelIdList, businessId);
	}

	@Override
	public Map<String, ResFile> getResFileMap(Set<Long> args) {
		Map<String, ResFile> map = new HashMap<>();
		if (args == null || args.isEmpty()) {
			return map;
		}

		List<ResFile> files = resFileMapper.listResFileById(args);
		if (files != null && !files.isEmpty()) {
			files.forEach(f -> map.put(Objects.toString(f.getId()), f));
		}

		return map;
	}

}