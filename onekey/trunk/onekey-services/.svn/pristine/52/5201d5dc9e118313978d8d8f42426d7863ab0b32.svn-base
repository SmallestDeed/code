package com.nork.onekeydesign.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.EntityCopyUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.intelligence.constant.IntelligenceDecorationConstant;
import com.nork.onekeydesign.dao.DesignPlanTemplateMapper;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlan;
import com.nork.onekeydesign.model.DesignPlanTemplate;
import com.nork.onekeydesign.model.UnityDesignPlan;
import com.nork.onekeydesign.model.constant.DesignPlanTemplateConstant;
import com.nork.onekeydesign.model.search.DesignPlanTemplateSearch;
import com.nork.onekeydesign.service.DesignPlanService;
import com.nork.onekeydesign.service.DesignPlanTemplateProductService;
import com.nork.onekeydesign.service.DesignPlanTemplateService;
import com.nork.onekeydesign.service.ResDesignOnekeyTemplateService;
import com.nork.system.model.ResDesignOnekeyTemplate;

@Service("designPlanTemplateService")
public class DesignPlanTemplateServiceImpl implements DesignPlanTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesignPlanTemplateServiceImpl.class);
	
	private static final String LOGPREFIX = "[一键装修生成方案副本模块]:";
	
	@Autowired
	private DesignPlanService designPlanService;
	
	@Autowired
	private DesignPlanTemplateProductService designPlanTemplateProductService;
	
	@Autowired
	private ResDesignOnekeyTemplateService resDesignOnekeyTemplateService;
	
	@Autowired
	private DesignPlanTemplateMapper designPlanTemplateMapper;
	
	@Override
	@Transactional
	public void saveTemplate(Integer recommendedPlanId, Integer templetId, Integer planId, Integer matchType) throws IntelligenceDecorationException {
		// 参数验证 ->start
		if(recommendedPlanId == null) {
			LOGGER.error("recommendedPlanId = null");
			throw new IntelligenceDecorationException("参数recommendedPlanId不能为空");
		}
		if(templetId == null) {
			LOGGER.error("templetId = null");
			throw new IntelligenceDecorationException("参数templetId不能为空");
		}
		if(planId == null) {
			LOGGER.error("planId = null");
			throw new IntelligenceDecorationException("参数planId不能为空");
		}
		if(matchType == null) {
			LOGGER.error("matchType = null");
			throw new IntelligenceDecorationException("参数matchType不能为空");
		}
		// 参数验证 ->end
		
		DesignPlan designPlan = designPlanService.get(planId);
		if(designPlan == null) {
			LOGGER.error("designPlan == null; planId = {}", planId);
			throw new IntelligenceDecorationException("保存一键装修方案副本失败(designPlan not found: planId = " + planId + ")");
		}
		
		// 保存副本(DesignPlanTemplate) ->start
		Integer designPlanTemplateId = this.saveTemplate(designPlan);
		// 保存副本(DesignPlanTemplate) ->end
		
		// 保存副本产品表(DesignPlanTemplateProduct) ->start
		designPlanTemplateProductService.copyByDesignPlanId(designPlan.getId(), designPlanTemplateId);
		// 保存副本产品表(DesignPlanTemplateProduct) ->end
		
		// 保存副本配置文件(ResDesignOnekeyTemplate) ->start
		Integer resDesignOnekeyTemplateId = resDesignOnekeyTemplateService.copyFromResDesign(designPlan.getConfigFileId(), designPlanTemplateId);
		// 保存副本配置文件(ResDesignOnekeyTemplate) ->end
		
		// 回填一些数据update ->start
		DesignPlanTemplate designPlanTemplate = new DesignPlanTemplate();
		designPlanTemplate.setId(designPlanTemplateId.longValue());
		designPlanTemplate.setRecommendedPlanId(recommendedPlanId.longValue());
		designPlanTemplate.setDesignTemplateId(templetId + "");
		designPlanTemplate.setReplaceType(this.matchTypeToReplaceType(matchType));
		designPlanTemplate.setConfigFileId(resDesignOnekeyTemplateId);
		designPlanTemplate.setUseState(DesignPlanTemplateConstant.USESTATE_INUSE);
		this.update(designPlanTemplate);
		// 回填一些数据update ->end
	}

	@Override
	public Integer matchTypeToReplaceType(Integer matchType) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "matchType转换失败";
		
		// 参数验证 ->start
		if(matchType == null) {
			String log = "matchType = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix +"(" + log + ")");
		}
		// 参数验证 ->end
		
		if(IntelligenceDecorationConstant.MATCHTYPE_ALL.intValue() == matchType.intValue()) {
			return IntelligenceDecorationConstant.REPLACETYPE_ALL;
		}else if(IntelligenceDecorationConstant.MATCHTYPE_HARD.intValue() == matchType.intValue()) {
			return IntelligenceDecorationConstant.REPLACETYPE_HARD;
		}else {
			String log = "matchType未知数据; matchType = " + matchType;
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix +"(" + log + ")");
		}
	}

	@Override
	public void update(DesignPlanTemplate designPlanTemplate) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "更新一键装修生成方案副本失败";
		
		// 参数验证 ->start
		if(designPlanTemplate == null) {
			String log = "designPlanTemplate = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix +"(" + log + ")");
		}
		if(designPlanTemplate.getId() == null) {
			String log = "designPlanTemplate.getId() = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix +"(" + log + ")");
		}
		// 参数验证 ->end
		
		designPlanTemplateMapper.updateByPrimaryKeySelective(designPlanTemplate);
	}

	/**
	 * 新建一键装修方案副本(copy from DesignPlan)
	 * 
	 * @author huangsongbo
	 * @param designPlan
	 * @param recommendedPlanId
	 * @param templetId
	 * @param matchType
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private Integer saveTemplate(DesignPlan designPlan) throws IntelligenceDecorationException {
		// 参数验证 ->start
		if(designPlan == null) {
			LOGGER.error(LOGPREFIX + "designPlan = null");
			throw new IntelligenceDecorationException("保存一键装修方案副本失败(designPlan = null)");
		}
		// 参数验证 ->end
		
		DesignPlanTemplate designPlanTemplate = this.copyFromDesignPlan(designPlan);
		
		// 暂时设置为废弃状态,等所有的数据都add好,就改为启用状态
		designPlanTemplate.setUseState(DesignPlanTemplateConstant.USESTATE_DEPRECATED);
		
		return this.add(designPlanTemplate);
	}

	@Override
	public Integer add(DesignPlanTemplate designPlanTemplate) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "创建一键装修设计方案副本数据失败";
		
		// 参数验证 ->start
		if(designPlanTemplate == null) {
			String log = "designPlanTemplate = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		Date now = new Date();
		designPlanTemplate.setGmtCreate(now);
		designPlanTemplate.setGmtModified(now);
		designPlanTemplateMapper.insertSelective(designPlanTemplate);
		return designPlanTemplate.getId() == null ? null : designPlanTemplate.getId().intValue();
	}

	/**
	 * copy DesignPlan to DesignPlanTemplate
	 * 
	 * @author huangsongbo
	 * @param designPlan
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private DesignPlanTemplate copyFromDesignPlan(DesignPlan designPlan) throws IntelligenceDecorationException {
		// 参数验证 ->start
		if(designPlan == null) {
			LOGGER.error("designPlan = null");
			throw new IntelligenceDecorationException("拷贝设计方案到一键装修方案副本失败(designPlan = null)");
		}
		// 参数验证 ->end
		
		DesignPlanTemplate designPlanTemplate = EntityCopyUtils.copyData(designPlan, DesignPlanTemplate.class);
		designPlanTemplate.setId(null);
		return designPlanTemplate;
	}

	@Override
	public boolean checkIsStock(Integer recommendedPlanId, Integer templetId, Integer matchType) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "检测一键装修对应副本失败";
		
		// 参数验证 ->start
		if(recommendedPlanId == null) {
			String log = "recommendedPlanId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(templetId == null) {
			String log = "templetId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(matchType == null) {
			String log = "matchType = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		/*DesignPlanTemplateSearch designPlanTemplateSearch = new DesignPlanTemplateSearch();
		designPlanTemplateSearch.setRecommendedPlanId(recommendedPlanId.longValue());
		designPlanTemplateSearch.setDesignTemplateId(templetId + "");
		designPlanTemplateSearch.setReplaceType(this.matchTypeToReplaceType(matchType));
		designPlanTemplateSearch.setIsDeleted(0);
		designPlanTemplateSearch.setUseState(DesignPlanTemplateConstant.USESTATE_INUSE);
		int total = this.getTotal(designPlanTemplateSearch);
		
		if(total > 0) {
			return true;
		}else {
			return false;
		}*/
		
		DesignPlanTemplate designPlanTemplate = this.get(recommendedPlanId, templetId, matchType);
		
		if(designPlanTemplate == null) {
			return false;
		}
		
		// 检查配置文件物理文件是否存在,如果不存在,则作废该副本 ->start
		if(designPlanTemplate.getConfigFileId() != null) {
			ResDesignOnekeyTemplate resDesignOnekeyTemplate = resDesignOnekeyTemplateService.get(designPlanTemplate.getConfigFileId());
			if(resDesignOnekeyTemplate != null) {
				String filePath = Utils.getAbsolutePath(resDesignOnekeyTemplate.getFilePath(), null);
				File file = new File(filePath);
				if(file.exists()) {
					return true;
				}
			}
		}
		// 检查配置文件物理文件是否存在,如果不存在,则作废该副本 ->end
		
		// 配置文件物理文件不存在,作废该副本 ->start
		DesignPlanTemplate designPlanTemplateForUpdate = new DesignPlanTemplate();
		designPlanTemplateForUpdate.setId(designPlanTemplate.getId());
		designPlanTemplateForUpdate.setUseState(DesignPlanTemplateConstant.USESTATE_DEPRECATED);
		designPlanTemplateForUpdate.setRemark(
				designPlanTemplateForUpdate.getRemark() + 
				";[" + Utils.getFormatDate(new Date()) + ":(进入副本动作)配置文件物理文件不存在,作废该副本]"
				);
		this.update(designPlanTemplateForUpdate);
		// 配置文件物理文件不存在,作废该副本 ->end
		
		return false;
	}

	@Override
	public int getTotal(DesignPlanTemplateSearch designPlanTemplateSearch) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "查询对应副本数量失败";
		
		// 参数验证 ->start
		if(designPlanTemplateSearch == null) {
			String log = "designPlanTemplateSearch = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		return designPlanTemplateMapper.selectCount(designPlanTemplateSearch);
	}

	@Override
	public UnityDesignPlan getUnityDesignPlan(
			Integer recommendedPlanId, 
			Integer templetId, 
			Integer matchType,
			Integer opType,
			LoginUser loginUser
			) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "转换副本失败/进入设计方案失败";
		
		// 参数验证 ->start
		if(recommendedPlanId == null) {
			String log = "recommendedPlanId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(templetId == null) {
			String log = "templetId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(matchType == null) {
			String log = "matchType = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		DesignPlanTemplate designPlanTemplate = this.get(recommendedPlanId, templetId, matchType);
		if(designPlanTemplate == null) {
			String log = "designPlanTemplate not found";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		
		// 转换副本至designPlan ->start
		Integer designPlanId = designPlanService.copyFromDesignPlanTemplateInfo(designPlanTemplate, opType, loginUser.getId());
		// 转换副本至designPlan ->end
		
		// 进入设计方案 ->start
		UnityDesignPlan unityDesignPlan = designPlanService.getUnityDesignPlan(designPlanId, opType, loginUser, templetId);
		// 进入设计方案 ->end
		
		return unityDesignPlan;
	}

	/**
	 * 根据参数查找DesignPlanTemplate,参数见方法参数
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId
	 * @param templetId
	 * @param matchType
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	private DesignPlanTemplate get(Integer recommendedPlanId, Integer templetId, Integer matchType) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "查找对应一键装修副本失败";
		
		// 参数验证 ->start
		if(recommendedPlanId == null) {
			String log = "recommendedPlanId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(templetId == null) {
			String log = "templetId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		if(matchType == null) {
			String log = "matchType = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		// 设置查询条件 ->start
		DesignPlanTemplateSearch designPlanTemplateSearch = new DesignPlanTemplateSearch();
		designPlanTemplateSearch.setStart(0);
		designPlanTemplateSearch.setLimit(1);
		designPlanTemplateSearch.setRecommendedPlanId(recommendedPlanId.longValue());
		designPlanTemplateSearch.setDesignTemplateId(templetId + "");
		designPlanTemplateSearch.setReplaceType(this.matchTypeToReplaceType(matchType));
		designPlanTemplateSearch.setIsDeleted(0);
		designPlanTemplateSearch.setUseState(DesignPlanTemplateConstant.USESTATE_INUSE);
		// 设置查询条件 ->end
		
		List<DesignPlanTemplate> designPlanTemplateList = this.getList(designPlanTemplateSearch);
		if(Lists.isNotEmpty(designPlanTemplateList)) {
			return designPlanTemplateList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<DesignPlanTemplate> getList(DesignPlanTemplateSearch designPlanTemplateSearch) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "查询对应副本数量失败";
		
		// 参数验证 ->start
		if(designPlanTemplateSearch == null) {
			String log = "designPlanTemplateSearch = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		return designPlanTemplateMapper.selectList(designPlanTemplateSearch);
	}
	
}
