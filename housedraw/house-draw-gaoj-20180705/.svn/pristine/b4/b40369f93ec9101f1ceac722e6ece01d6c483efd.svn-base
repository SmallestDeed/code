package com.sandu.service.model.impl;

import java.util.*;

import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.common.constant.FileKey;
import com.sandu.common.constant.SystemConfigEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.res.model.ResModel;
import com.sandu.api.res.service.ResModelService;
import com.sandu.common.constant.house.SystemConstant;
import com.sandu.service.model.dao.ResModelMapper;
import com.sandu.util.FileUtils;
import com.sandu.util.UploadUtils;
import com.sandu.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("resModelService")
public class ResModelServiceImpl implements ResModelService {
	
	@Autowired
	private ResModelMapper resModelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long createByDrawResFile(DrawResFile drawResFile, String fileKey, boolean needEncrypt) {
		String logMes = "function:ResModelServiceImpl.createByDrawResFile->";
		
		// 参数验证 ->start
		if (drawResFile == null) {
			log.error("参数 drawResFile 为空");
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
			log.error("{}拷贝文件失败,{} to {}", logMes, Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null),
					Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
			return null;
		}
		// 复制物理文件 ->end
		
		// 转换数据 ->start
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
		resModel.setFileKey(getFileKey(fileKey));
		resModel.setModifier(drawResFile.getModifier());
		resModel.setGmtModified(now);
		resModel.setIsDeleted(0);
		// 转换数据 ->end

		return this.add(resModel);
	}

    @Override
    public String getFileKey(String key) {
        // 产品Windos模型文件
        if (SystemConfigEnum.BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey().equals(key)) {
            return FileKey.BASE_PRODUCT_U3DMODEL_WINDOWSPC.getFileKey();
        } else if (SystemConfigEnum.SPACE_MODEL_DAYLIGHT_UPLOAD.getFileKey().equals(key)) {
            // 白天灯光文件
            return FileKey.SPACECOMMON_U3DMODEL_DAYLIGHT.getFileKey();
        } else if (SystemConfigEnum.SPACE_MODEL_DUSKLIGHT_UPLOAD.getFileKey().equals(key)) {
            // 黄昏灯光文件
            return FileKey.SPACECOMMON_U3DMODEL_DUSKLIGHT.getFileKey();
        } else if (SystemConfigEnum.SPACE_MODEL_NIGHTLIGHT_UPLOAD.getFileKey().equals(key)) {
            // 晚上灯光文件
            return FileKey.SPACECOMMON_U3DMODEL_NIGHTLIGHT.getFileKey();
        } else if (SystemConfigEnum.DESIGNTEMPLET_CONFIGFILE_UPLOAD.getFileKey().equals(key)) {
            // 样板房配置文件
            return FileKey.DESIGN_TEMPLET_CONFIG_FILE.getFileKey();
        } else if (SystemConfigEnum.DESIGNTEMPLET_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey().equals(key)) {
            // 样板房模型文件
            return FileKey.DESIGN_DESIGNTEMPLET_U3DMODEL_WINDOWSPC.getFileKey();
        }

        log.warn("资源FileKey == upload key => {}", key);
        return key == null ? "" : key;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer addBatchModelFile(List<ResModel> resModels) {
		if (resModels.isEmpty()) {
			log.warn("烘焙的产品模型资源为空");
			return -1;
		}

		return resModelMapper.addBatchModelFile(resModels);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateBatchBusinessId(List<DrawBaseProduct> drawProducts) {
		if (drawProducts == null || drawProducts.isEmpty()) {
			log.warn("参数drawProducts为空");
			return -1;
		}

		return resModelMapper.updateBatchBusinessId(drawProducts);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long add(ResModel resModel) {
		// 参数验证 ->start
		if (resModel == null) {
			return null;
		}
		// 参数验证 ->end

		resModelMapper.insertSelective(resModel);

		return resModel.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBusinessId(List<Integer> resFileIdList, Integer businessId) {
		// 参数验证 ->start
		if (resFileIdList == null || resFileIdList.size() == 0) {
			return;
		}
		if (businessId == null) {
			return;
		}
		// 参数验证 ->end

		resModelMapper.updateBusinessId(resFileIdList, businessId);
	}

	@Override
	public Map<String, ResModel> getResModelMap(Set<Long> args) {
		final Map<String, ResModel> map = new HashMap<>();
		if (args == null || args.isEmpty()) {
			return map;
		}

		List<ResModel> models = resModelMapper.listResModelById(args);
		if (models != null && !models.isEmpty()) {
			models.forEach(model -> map.put(model.getId().toString(), model));
		}

		return map;
	}

	@Override
	public ResModel selectByPrimaryKey(Integer id) {
		if (Objects.isNull(id) || id <= 0) {
			log.warn("参数 id 为空");
			return null;
		}
		return resModelMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long copyModel(ResModel source, String fileKey, boolean needEncrypt) {
		if (source == null) {
			log.warn("参数 source 为空");
			return null;
		}

		if (StringUtils.isEmpty(source.getModelPath())) {
			log.warn("参数 modelPath 值为空");
			return null;
		}

		// 复制物理文件 ->start
		String resourcesPath = source.getModelPath().replace(SystemConstant.UPLOAD_ROOT, "");
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
			log.error("{}拷贝文件失败,{} to {}", "", Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + resourcesPath, null),
					Utils.dealWithPath(SystemConstant.UPLOAD_ROOT + targetPath + newFileName, null));
			return null;
		}
		// 复制物理文件 ->end

		// 转换数据 ->start
		String randomNumStr = Utils.generateRandomDigitString(6);
		String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
		Date now = new Date();

		ResModel resModel = new ResModel();
		resModel.setModelCode(sysCode);
		resModel.setModelName(newFileName);
		resModel.setModelFileName(newFileName);
		resModel.setModelType("无");
		resModel.setModelSize(source.getModelSize());
		resModel.setModelSuffix(newFileName.substring(newFileName.lastIndexOf("."), newFileName.length()));
		resModel.setModelLevel("0");
		resModel.setModelPath(targetPath + newFileName);
		resModel.setModelOrdering(0);
		resModel.setSysCode(sysCode);
		resModel.setCreator(source.getCreator());
		resModel.setGmtCreate(now);
		resModel.setModifier(source.getModifier());
		resModel.setGmtModified(now);
		resModel.setIsDeleted(0);
		// 转换数据 ->end

		return this.add(resModel);
	}
}
