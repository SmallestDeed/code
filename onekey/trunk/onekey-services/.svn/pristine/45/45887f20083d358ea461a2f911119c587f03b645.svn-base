package com.nork.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.FileModel;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.EntityCopyUtils;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Utils;
import com.nork.onekeydesign.common.DesignPlanConstants;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlan;
import com.nork.onekeydesign.service.DesignPlanService;
import com.nork.onekeydesign.service.OptimizePlanService;
import com.nork.onekeydesign.service.ResDesignOnekeyTemplateService;
import com.nork.system.dao.ResDesignMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResDesignOnekeyTemplate;
import com.nork.system.service.ResDesignService;


@Service("resDesignService")
public class ResDesignServiceImpl implements ResDesignService{
	
	private static final Logger logger = LoggerFactory.getLogger(ResDesignServiceImpl.class);
	
	private static final String LOGPREFIX = "[一键装修配置文件模块]:";
	
	@Autowired
	ResDesignMapper resDesignMapper;

	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	private OptimizePlanService optimizePlanService;
	
	@Autowired
	private ResDesignOnekeyTemplateService resDesignOnekeyTemplateService;
	
	@Override
	public int add(ResDesign resDesign) {
		resDesignMapper.insertSelective(resDesign);
		return resDesign.getId();
	}

	@Override
	public int update(ResDesign resDesign) {
		return resDesignMapper.updateByPrimaryKeySelective(resDesign);
	}
	
	@Override
	public int delete(Integer id) {
		return resDesignMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResDesign get(Integer id) {
		return resDesignMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ResDesign> getList(ResDesign resDesign) {
		return resDesignMapper.selectList(resDesign);
	}

	@Override
	public int getCount(ResDesign resDesign) {
		return resDesignMapper.selectCount(resDesign);
	}

	@Override
	public ResDesign getByOldId(Integer OldId) {
		return resDesignMapper.getByOldId(OldId);
	}

	@Override
	public int filePathCount(String filePath) {
		// TODO Auto-generated method stub
		return resDesignMapper.filePathCount(filePath);
	}

	/**
	 * 将设计方案配置文件信息记录到数据库中
	 */
	@Override
	public boolean saveResDesign(DesignPlan designPlan, Map map){
		if( map != null && map.size() > 0 ){
			ResDesign resDesign = new ResDesign();
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resDesign.setSysCode(designPlan.getPlanCode());
			resDesign.setFileCode(designPlan.getPlanCode());
			resDesign.setGmtCreate(new Date());
			resDesign.setGmtModified(new Date());
			resDesign.setCreator(designPlan.getCreator());
			resDesign.setModifier(designPlan.getCreator());
			resDesign.setIsDeleted(0);
			resDesign.setFileName(fileName);
			resDesign.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resDesign.setBusinessId(Integer.valueOf(designPlan.getId()));
			resDesign.setFileOriginalName(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			resDesign.setFilePath(dbFilePath);
			resDesign.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resDesign.setFileLevel("0");
			resDesign.setFileType("设计方案配置文件");
			Integer id = this.add(resDesign);
			if( id > 0 ) {
				DesignPlan newDesginPlan = new DesignPlan();
				newDesginPlan.setId(designPlan.getId());
				newDesginPlan.setConfigFileId(id);
				designPlanService.update(newDesginPlan);
			}else{
				return false;
			}
		}else{
			return false;
		}
		return  true;
	}
	
	
	
	
	/**
	 * 将拼花文件信息保存到数据库中
	 * @author zhaobl
	 * @param designPlan
	 * @param map
	 * @return
	 */
	@Override
	public boolean saveSpellingFlowerFile(DesignPlan designPlan, Map map){
		if( map != null && map.size() > 0 ){
			ResDesign resDesign = new ResDesign();
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resDesign.setSysCode(designPlan.getPlanCode());
			resDesign.setFileCode(designPlan.getPlanCode());
			resDesign.setGmtCreate(new Date());
			resDesign.setGmtModified(new Date());
			resDesign.setCreator(designPlan.getCreator());
			resDesign.setModifier(designPlan.getCreator());
			resDesign.setIsDeleted(0);
			resDesign.setFileName(fileName);
			resDesign.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resDesign.setBusinessId(Integer.valueOf(designPlan.getId()));
			resDesign.setFileOriginalName(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			resDesign.setFilePath(dbFilePath);
			resDesign.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resDesign.setFileLevel("0");
			resDesign.setFileType("设计方案拼花文件");
			Integer id = 0;
			if(designPlan.getSpellingFlowerFileId()!=null && designPlan.getSpellingFlowerFileId().intValue() > 0) {
				ResDesign resDesign_ = this.get(designPlan.getSpellingFlowerFileId());
				if(resDesign_ != null) {
					resDesign.setId(designPlan.getSpellingFlowerFileId());
					this.update(resDesign);
				}else {
					id = this.add(resDesign);
				}
			}else {
				id = this.add(resDesign);
			}
			if( id > 0 ) {
				DesignPlan newDesginPlan = new DesignPlan();
				newDesginPlan.setId(designPlan.getId());
				newDesginPlan.setSpellingFlowerFileId(id);
				designPlanService.update(newDesginPlan);
			}
		}else{
			return false;
		}
		return  true;
	}

	@Override
	public long createFileAndAddDate(ResDesign resDesign, long businessId) {
		
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
		
		// 设置resDesignEctype属性
		resDesign.setId(null);
		resDesign.setFilePath(resDesign.getFilePath().substring(0, resDesign.getFilePath().lastIndexOf("/") + 1) + newFileName);
		if(businessId > 0){
			resDesign.setBusinessId((int)businessId);
		}
		Date now = new Date();
		resDesign.setGmtCreate(now);
		resDesign.setGmtModified(now);
		
		long resDesignId = this.add(resDesign);
		// 新建配置文件数据 ->end
		
		return resDesignId;
	}

    @Override
    public void batchDelTempDesignConfig(List<Integer> delConfigList) {
		if(delConfigList != null && delConfigList.size() > 0){
			resDesignMapper.batchDelTempDesignConfig(delConfigList);
		}
    }

	@Override
	public Integer createResDesignByFile(File file, String fileKey, String code, String username, Integer businessId, Integer opType) {
		// *参数验证 ->start
		if(file == null || !file.exists()) {
			logger.error("------function:ResDesignServiceImpl.createResDesignByFile(....)->\n文件没有找到 or file = null");
			return null;
		}
		if(StringUtils.isEmpty(fileKey)) {
			logger.error("------function:ResDesignServiceImpl.createResDesignByFile(....)->\n参数fileKey:StringUtils.isEmpty(fileKey) = true");
			return null;
		}
		if(StringUtils.isEmpty(code)) {
			logger.error("------function:ResDesignServiceImpl.createResDesignByFile(....)->\n参数code:StringUtils.isEmpty(code) = true");
			return null;
		}
		if(StringUtils.isEmpty(username)) {
			logger.error("------function:ResDesignServiceImpl.createResDesignByFile(....)->\n参数username:StringUtils.isEmpty(username) = true");
			return null;
		}
		if(businessId == null) {
			logger.error("------function:ResDesignServiceImpl.createResDesignByFile(....)->\n参数businessId:businessId = null");
			return null;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证 ->end
		
		Map <String, String>map = FileUploadUtils.getMap(file, "design.designPlan.u3dconfig.upload.path", false);
		return this.saveResDesign(map, code, username, businessId, opType);
	}

	private Integer saveResDesign(Map<String, String> map, String code, String username, Integer businessId, Integer opType) {
		
		// *参数验证 ->start
		if(map == null) {
			logger.error("------function:ResDesignServiceImpl.saveResDesign(....)->\n参数map = null");
			return null;
		}
		if(StringUtils.isEmpty(code)) {
			logger.error("------function:ResDesignServiceImpl.saveResDesign(....)->\n参数code:StringUtils.isEmpty(code) = true");
			return null;
		}
		if(StringUtils.isEmpty(username)) {
			logger.error("------function:ResDesignServiceImpl.saveResDesign(....)->\n参数username:StringUtils.isEmpty(username) = true");
			return null;
		}
		if(businessId == null) {
			logger.error("------function:ResDesignServiceImpl.saveResDesign(....)->\n参数businessId:businessId = null");
			return null;
		}
		if (opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// *参数验证 ->end
		
		ResDesign resDesign = new ResDesign();
		String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
		String fileName = map.get(FileModel.FILE_NAME).toString();
		resDesign.setSysCode(code);
		resDesign.setFileCode(code);
		Date now = new Date();
		resDesign.setGmtCreate(now);
		resDesign.setGmtModified(now);
		resDesign.setCreator(username);
		resDesign.setModifier(username);
		resDesign.setIsDeleted(0);
		resDesign.setFileName(fileName);
		resDesign.setFileSize(map.get(FileModel.FILE_SIZE).toString());
		resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		resDesign.setBusinessId(businessId);
		resDesign.setFileOriginalName(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		resDesign.setFilePath(dbFilePath);
		resDesign.setFileKey(map.get(FileModel.FILE_KEY).toString());
		resDesign.setFileLevel("0");
		resDesign.setFileType("设计方案配置文件");
		
		Integer id = null;
		if(DesignPlanConstants.AUTO_RENDER != opType) {
			id = this.add(resDesign);
		}else {
			// 自动渲染
			id = optimizePlanService.add(resDesign);
		}
		if( id > 0 ) {
			return id;
		}else{
			return null;
		}
	}

	@Override
	public long copySpellingFlowerFile(Integer spellingFlowerFileId, Integer planId, String planCode) {
		long  sceneSpellingFlowerFileId = 0;
		if(spellingFlowerFileId ==null || spellingFlowerFileId.intValue() <= 0 ) {
			return sceneSpellingFlowerFileId;
		}
		ResDesign resDesign  = this.get(spellingFlowerFileId);
		if(resDesign == null) {
			return sceneSpellingFlowerFileId;
		}
		String resFilePath = resDesign.getFilePath();
		String fileKey = "onekeydesign.designPlan.spellingFlowerFile";
		
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
			String newPath = Utils.getPropertyName("config/res", "onekeydesign.designPlan.spellingFlowerFile","/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/onekeydesign/designPlan/spellingFlowerFile/");
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
			resDesign.setSysCode(planCode);
			resDesign.setFilePath(targetName);
			resDesign.setFileKey(fileKey);
			resDesign.setBusinessId(planId);
			resDesign.setFileCode(planCode);
			sceneSpellingFlowerFileId =   this.add(resDesign);
		}

		return sceneSpellingFlowerFileId;
	}

	@Override
	public Integer copyFromResDesignOnekeyTemplate(Integer resDesignOnekeyTemplateId, Integer designPlanId, Integer opType) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "拷贝一键装修副本配置文件到设计方案配置文件失败";
		
		// 参数验证 ->start
		if(resDesignOnekeyTemplateId == null) {
			String log = "params error: resDesignOnekeyTemplateId = null";
			logger.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + log);
		}
		if(designPlanId == null) {
			String log = "params error: designPlanId = null";
			logger.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + log);
		}
		if(opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		ResDesignOnekeyTemplate resDesignOnekeyTemplate = resDesignOnekeyTemplateService.get(resDesignOnekeyTemplateId);
		if(resDesignOnekeyTemplate == null) {
			String log = "resDesignOnekeyTemplate not found; resDesignOnekeyTemplateId = " + resDesignOnekeyTemplateId;
			logger.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + log);
		}
		// 参数验证 ->end
		
		// copy 物理文件 ->start
		String fileKey = ResProperties.DESIGNPLAN_U3DCONFIG_FILEKEY;
		// copy后文件的相对路径(直接存数据库的路径)
		String filePath = com.nork.common.util.FileUtils.copyFile(resDesignOnekeyTemplate.getFilePath(), fileKey);
		// copy 物理文件 ->end
		
		// 新建ResDesign数据 ->start
		ResDesign resDesign = EntityCopyUtils.copyData(resDesignOnekeyTemplate, ResDesign.class);
		resDesignOnekeyTemplate.setId(null);
		resDesignOnekeyTemplate.setFileKey(fileKey);
		resDesignOnekeyTemplate.setFilePath(filePath);
		resDesignOnekeyTemplate.setBusinessId(designPlanId);
		return this.add(resDesign, opType);
		// 新建ResDesign数据 ->end
	}

	@Override
	public Integer add(ResDesign resDesign, Integer opType) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "保存设计方案配置文件失败";
		
		// 参数验证 ->start
		if(resDesign == null) {
			String log = "params error: resDesign = null";
			logger.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(opType == null) {
			opType = DesignPlanConstants.USER_RENDER;
		}
		// 参数验证 ->end
		
		if(DesignPlanConstants.USER_RENDER.intValue() == opType.intValue()) {
			// 通用版本一键装修
			return this.add(resDesign);
		}else {
			// 渲染机一键装修
			return optimizePlanService.add(resDesign);
		}
	}
	
	/*@Override
	public Integer createResDesignByFile(File file, String fileKey, DesignPlan designPlan) {
		
		// *参数验证 ->start
		if(!file.exists()) {
			return null;
		}
		if(StringUtils.isEmpty(fileKey)) {
			return null;
		}
		if(designPlan == null) {
			return null;
		}
		// *参数验证 ->end
		
		Map <String, String>map = FileUploadUtils.getMap(file, "onekeydesign.designPlan.u3dconfig.upload.path", false);
		boolean flag = this.saveResDesign(designPlan, map);
		return null;
	}*/

}
