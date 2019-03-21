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

import com.nork.aes.util.FileEncrypt;
import com.nork.common.model.FileModel;
import com.nork.common.util.ClassReflectionUtils;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.OptimizePlanService;
import com.nork.system.dao.ResDesignMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResDesignRenderScene;
import com.nork.system.service.ResDesignService;


@Service("resDesignService")
public class ResDesignServiceImpl implements ResDesignService{
	
	Logger logger = LoggerFactory.getLogger(ResDesignServiceImpl.class);
	
	@Autowired
	ResDesignMapper resDesignMapper;

	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	private OptimizePlanService optimizePlanService;
	
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
					resDesign.setGmtCreate(new Date());
					resDesign.setGmtModified(new Date());
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
		
		Map <String, String>map = FileUploadUtils.getMap(file, "design.designPlan.u3dconfig.upload.path", false);
		boolean flag = this.saveResDesign(designPlan, map);
		return null;
	}*/

}
