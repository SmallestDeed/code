package com.nork.onekeydesign.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.properties.ResProperties;
import com.nork.common.util.EntityCopyUtils;
import com.nork.common.util.FileUtils;
import com.nork.common.util.Utils;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanTemplate;
import com.nork.onekeydesign.service.DesignPlanTemplateService;
import com.nork.onekeydesign.service.ResDesignOnekeyTemplateService;
import com.nork.system.dao.ResDesignOnekeyTemplateMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResDesignOnekeyTemplate;
import com.nork.system.service.ResDesignService;

@Service("resDesignOnekeyTemplateService")
public class ResDesignOnekeyTemplateServiceImpl implements ResDesignOnekeyTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResDesignOnekeyTemplateServiceImpl.class);
	
	private static final String LOGPREFIX = "[一键装修生成方案副本配置文件模块]:";
	
	@Autowired
	private ResDesignService resDesignService;
	
	@Autowired
	private ResDesignOnekeyTemplateMapper resDesignOnekeyTemplateMapper;
	
	@Autowired
	private DesignPlanTemplateService designPlanTemplateService;
	
	@Override
	public Integer copyFromResDesign(Integer configFileId, Integer designPlanTemplateId) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "创建一键装修生成方案副本配置文件失败";
		
		// 参数验证 ->start
		if(configFileId == null) {
			LOGGER.error(LOGPREFIX + "configFileId = null");
			throw new IntelligenceDecorationException(exceptionLogPrefix + "configFileId = null");
		}
		if(designPlanTemplateId == null) {
			LOGGER.error(LOGPREFIX + "designPlanTemplateId = null");
			throw new IntelligenceDecorationException(exceptionLogPrefix + "designPlanTemplateId = null");
		}
		ResDesign resDesign = resDesignService.get(configFileId);
		if(resDesign == null) {
			LOGGER.error(LOGPREFIX + "resDesign = null; resDesignId = {}", configFileId);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "resDesign = null; resDesignId = " + configFileId);
		}
		// 参数验证 ->end
		
		// copy物理文件 ->start
		String fileKey = ResProperties.DESIGN_DESIGNPLANTEMPLATE_U3DCONFIG_FILEKEY;
		// copy后文件的相对路径(直接存数据库的路径)
		String filePath = FileUtils.copyFile(resDesign.getFilePath(), fileKey);
		// copy物理文件 ->end
		
		// 检查配置文件物理文件是否存在 ->start
		// 如果不存在,做标记,但不进行其他处理(进入副本的时候会处理,此操作只为方便定位问题)
		File file = new File(Utils.getAbsolutePath(filePath, null));
		if(!file.exists()) {
			DesignPlanTemplate designPlanTemplateForUpdate = new DesignPlanTemplate();
			designPlanTemplateForUpdate.setId(designPlanTemplateId.longValue());
			designPlanTemplateForUpdate.setRemark(
					(designPlanTemplateForUpdate.getRemark() == null ? "" : designPlanTemplateForUpdate.getRemark()) + 
					";[" + Utils.getFormatDate(new Date()) + ":(保存副本动作)配置文件物理文件不存在]"
					);
			designPlanTemplateService.update(designPlanTemplateForUpdate);
		}
		// 检查配置文件物理文件是否存在 ->end
		
		// transform ResDesign to ResDesignOnekeyTemplate ->start
		ResDesignOnekeyTemplate resDesignOnekeyTemplate = EntityCopyUtils.copyData(resDesign, ResDesignOnekeyTemplate.class);
		resDesignOnekeyTemplate.setId(null);
		resDesignOnekeyTemplate.setFileKey(fileKey);
		resDesignOnekeyTemplate.setFilePath(filePath);
		resDesignOnekeyTemplate.setBusinessId(designPlanTemplateId);
		return this.add(resDesignOnekeyTemplate);
		// transform ResDesign to ResDesignOnekeyTemplate ->end
	}

	@Override
	public Integer add(ResDesignOnekeyTemplate resDesignOnekeyTemplate) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "创建一键装修生成方案副本配置文件失败";
		
		// 参数验证 ->start
		if(resDesignOnekeyTemplate == null) {
			LOGGER.error(LOGPREFIX + "resDesignOnekeyTemplate == null");
			throw new IntelligenceDecorationException(exceptionLogPrefix + "resDesignOnekeyTemplate = null");
		}
		// 参数验证 ->end
		
		Date now = new Date();
		resDesignOnekeyTemplate.setGmtCreate(now);
		resDesignOnekeyTemplate.setGmtModified(now);
		resDesignOnekeyTemplateMapper.insertSelective(resDesignOnekeyTemplate);
		return resDesignOnekeyTemplate.getId() == null ? null : resDesignOnekeyTemplate.getId().intValue();
	}

	@Override
	public ResDesignOnekeyTemplate get(Integer resDesignOnekeyTemplateId) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "获取副本配置文件失败";
		
		// 参数验证 ->start
		if(resDesignOnekeyTemplateId == null) {
			String log = "params error: resDesignOnekeyTemplateId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + log);
		}
		// 参数验证 ->end
		
		return resDesignOnekeyTemplateMapper.selectByPrimaryKey(resDesignOnekeyTemplateId.longValue());
	}

}
