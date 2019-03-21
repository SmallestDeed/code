package com.nork.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.aes.util.FileEncrypt;
import com.nork.common.util.ClassReflectionUtils;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.system.dao.ResDesignRenderSceneMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResDesignRenderScene;
import com.nork.system.service.ResDesignRenderSceneService;
import com.nork.system.service.ResDesignService;

@Service("resDesignRenderSceneService")
public class ResDesignRenderSceneServiceImpl implements ResDesignRenderSceneService{

	Logger logger = LoggerFactory.getLogger(ResDesignRenderSceneServiceImpl.class);
	
	@Autowired
	private  ResDesignRenderSceneService resDesignRenderSceneService;
	
	@Autowired
	private ResDesignService resDesignService;
	
	@Autowired
	private ResDesignRenderSceneMapper resDesignRenderSceneMapper;
	
	@Override
		
	public long copyFromResDesign(ResDesign resDesign, long businessId) {
		if(resDesign == null){
			logger.error("------resDesign = null");
			return 0;
		}
		
		if(StringUtils.isEmpty(resDesign.getFilePath())){
			logger.error("------StringUtils.isEmpty(resDesign.getFilePath()) = true");
			return 0;
		}
		
		// 拷贝一份物理文件 ->start
		/*String oldPath = FileEncrypt.defaultUploadRoot + resDesign.getFilePath();*/
		String oldPath = Utils.getAbsolutePath(resDesign.getFilePath(), null);
		File oldFile = new File(oldPath);
		if(!oldFile.exists()){
			logger.error("------file.exists() = false,path:" + oldFile.getPath());
			return 0;
		}
		String newFileName = 
				Utils.generateRandomDigitString(6) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS) 
				+ "_1" + oldPath.substring(oldPath.lastIndexOf("."));
		String newFilePath = oldPath.substring(0, oldPath.lastIndexOf("/") + 1) + newFileName;
		File newFile = new File(newFilePath);
		try {
			FileUtils.copyFile(oldFile, newFile);
		} catch (IOException e) {
			logger.error(e.toString());
			return 0;
		}
		// 拷贝一份物理文件 ->end
		
		// 新建配置文件数据 ->start
		ResDesignRenderScene resDesignRenderScene = new ResDesignRenderScene();
		try {
			ClassReflectionUtils.reflectionAttr(resDesign, resDesignRenderScene);
		} catch (Exception e) {
			logger.error(e.toString());
			
			if(newFile.exists()){
				newFile.delete();
			}
			
			return 0;
		}
		
		// 设置resDesignEctype属性
		resDesignRenderScene.setId(null);
		resDesignRenderScene.setFilePath(resDesign.getFilePath().substring(0, resDesign.getFilePath().lastIndexOf("/") + 1) + newFileName);
		if(businessId > 0){
			resDesignRenderScene.setBusinessId((int)businessId);
		}
		Date now = new Date();
		resDesignRenderScene.setGmtCreate(now);
		resDesignRenderScene.setGmtModified(now);
		
		long resDesignRenderSceneId = this.add(resDesignRenderScene);
		// 新建配置文件数据 ->end
		
		return resDesignRenderSceneId;
	}

	@Override
	public long add(ResDesignRenderScene resDesignRenderScene) {
		resDesignRenderSceneMapper.insert(resDesignRenderScene);
		return resDesignRenderScene.getId();
	}

	@Override
	public ResDesignRenderScene get(Integer id) {
		return resDesignRenderSceneMapper.get(id);
	}

	@Override
	public void delete(Integer id) {
		resDesignRenderSceneMapper.delete(id);
	}

	/**
	 * 将拼花信息拷贝到 效果图中
	 * @param spellingFlowerFileId
	 * @return
	 */
	@Override
	public long copySpellingFlowerFile(Integer spellingFlowerFileId,DesignPlanRenderScene designPlanRenderScene) {
		long  sceneSpellingFlowerFileId = 0;
		if(spellingFlowerFileId ==null || spellingFlowerFileId.intValue() <= 0 ) {
			return sceneSpellingFlowerFileId;
		}
		ResDesign resDesign  = resDesignService.get(spellingFlowerFileId);
		if(resDesign == null) {
			return sceneSpellingFlowerFileId;
		}
		ResDesignRenderScene resDesignRenderScene = resDesign.resDesignRenderSceneCopy();
		String resFilePath = resDesignRenderScene.getFilePath();
		String fileKey = "design.designPlanRenderScene.spellingFlowerFile";
		
		if (StringUtils.isNotEmpty(resFilePath)) {
			String srcPath = Utils.getAbsolutePath(resFilePath, Utils.getAbsolutePathType.encrypt);
			File srcresourcesFile = new File(srcPath);
			if (!srcresourcesFile.getParentFile().exists()) {
				srcresourcesFile.getParentFile().mkdirs();
			}
			String resourcesName = resFilePath.substring(resFilePath.replace("/", "\\").lastIndexOf("\\") + 1,resFilePath.length());
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				resourcesName = resFilePath.substring(resFilePath.lastIndexOf("/") + 1, resFilePath.length());
			}
			String newPath = Utils.getPropertyName("config/res", "design.designPlanRenderScene.spellingFlowerFile","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlanRenderScene/spellingFlowerFile/");
			newPath = Utils.replaceDate(newPath);

			//去除在名称后缀追加时间戳逻辑，因为此逻辑文件名称会无限变长，所以改为重新生成名称
//			String tarName = resourcesName.substring(resourcesName.lastIndexOf("/") + 1,resourcesName.lastIndexOf("_")) + "_" + Utils.getCurrentDateTime(Utils.DATETIMESSS)
//					+ resourcesName.substring(resourcesName.indexOf("."));
			String tarName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";

			String targetName = newPath + tarName;
			String local_targetPath = Utils.getAbsolutePath(targetName.replace("/", "\\"), Utils.getAbsolutePathType.encrypt);

			File local_targetFile = new File(local_targetPath);
			if (!local_targetFile.getParentFile().exists()) {
				local_targetFile.getParentFile().mkdirs();
			}
			boolean flag = false;
			String resPath = resFilePath.substring(0, resFilePath.lastIndexOf("/") + 1);
			String dbFilePath = Utils.getAbsolutePath(newPath + tarName, Utils.getAbsolutePathType.encrypt);
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 1) {
				if (srcresourcesFile.isFile() && srcresourcesFile.exists()) { /* 判断文件是否存在*/
					flag = FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile);
				} else {
					logger.error("srcresourcesFile is not  exists !srcresourcesFile="+srcresourcesFile);
				}
			} else if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
				
				flag = FtpUploadUtils.downFile(resPath, resourcesName);/* 下载到本地*/
				if (FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
					if (flag){ 
						flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);/* 上传ftp服务器*/
						if(flag){
							FileUploadUtils.deleteFile(newPath + tarName);	/* 删除本地*/
						}else{
							return sceneSpellingFlowerFileId;
						}
					}else{
						return sceneSpellingFlowerFileId;
					}
				}else{
					logger.error("copy file is error");
					return -1;
				}
			}else{
				/* 3 本地和ftp同时上传(默认是本地上传)*/
				/* resPath：FTP服务器上的相对路径，resourcesName：要下载的文件名，newPath+tarName：下载到本地文件路径+文件名称*/
				flag = FtpUploadUtils.downFile(resPath, resourcesName);/* 下载到本地 */
				if (!flag || FileUploadUtils.fileCopy(srcresourcesFile, local_targetFile)) {
					logger.error("copy file is error");
					return -1;
				}
				if (flag) {
					/* tarName:文件名称，dbFilePath:本地文件路径，newPath:ftp服务器存放文件路径*/
					flag = FtpUploadUtils.uploadFile(tarName, dbFilePath, newPath);/* 上传ftp服务器*/
					if(!flag){
						return sceneSpellingFlowerFileId;
					}
				}else{
					return sceneSpellingFlowerFileId;
				}
			}
			resDesignRenderScene.setSysCode(designPlanRenderScene.getPlanCode());
			resDesignRenderScene.setFilePath(targetName);
			resDesignRenderScene.setFileKey(fileKey);
			resDesignRenderScene.setBusinessId(designPlanRenderScene.getId());
			resDesignRenderScene.setFileCode(designPlanRenderScene.getPlanCode());
			sceneSpellingFlowerFileId =   resDesignRenderSceneService.add(resDesignRenderScene);
		}

		return sceneSpellingFlowerFileId;
	}
	
}
