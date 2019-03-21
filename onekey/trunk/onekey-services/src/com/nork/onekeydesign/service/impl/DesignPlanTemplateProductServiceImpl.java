package com.nork.onekeydesign.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.EntityCopyUtils;
import com.nork.onekeydesign.dao.DesignPlanTemplateProductMapper;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanProduct;
import com.nork.onekeydesign.model.DesignPlanTemplateProduct;
import com.nork.onekeydesign.service.DesignPlanProductService;
import com.nork.onekeydesign.service.DesignPlanTemplateProductService;

@Service("designPlanTemplateProductService")
public class DesignPlanTemplateProductServiceImpl implements DesignPlanTemplateProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesignPlanTemplateProductServiceImpl.class);
	
	private static final String LOGPREFIX = "[一键装修生成方案副本产品表模块]:";
	
	@Autowired
	private DesignPlanProductService designPlanProductService;
	
	@Autowired
	private DesignPlanTemplateProductMapper designPlanTemplateProductMapper;
	
	@Override
	public void copyByDesignPlanId(Integer planId, Integer designPlanTemplateId) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "拷贝设计方案产品数据失败";
		
		// 参数验证 ->start
		if(planId == null) {
			LOGGER.error("planId = null");
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(planId = null)");
		}
		if(designPlanTemplateId == null) {
			LOGGER.error("designPlanTemplateId = null");
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(designPlanTemplateId = null)");
		}
		// 参数验证 ->end
		
		List<DesignPlanProduct> designPlanProductList = designPlanProductService.getListByPlanId(planId);
		for(DesignPlanProduct designPlanProduct : designPlanProductList) {
			DesignPlanTemplateProduct designPlanTemplateProduct = EntityCopyUtils.copyData(designPlanProduct, DesignPlanTemplateProduct.class);
			designPlanTemplateProduct.setId(null);
			designPlanTemplateProduct.setPlanId(designPlanTemplateId);
			this.add(designPlanTemplateProduct);
		}
	}

	@Override
	public Integer add(DesignPlanTemplateProduct designPlanTemplateProduct) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "新增一键装修生成方案副本产品数据失败";
		
		// 参数验证 ->start
		if(designPlanTemplateProduct == null) {
			LOGGER.error(LOGPREFIX + "designPlanTemplateProduct = null"); 
			throw new IntelligenceDecorationException(exceptionLogPrefix + "designPlanTemplateProduct = null");
		}
		// 参数验证 ->end
		
		Date now = new Date();
		designPlanTemplateProduct.setGmtCreate(now);
		designPlanTemplateProduct.setGmtModified(now);
		designPlanTemplateProductMapper.insertSelective(designPlanTemplateProduct);
		return designPlanTemplateProduct.getId() == null ? null : designPlanTemplateProduct.getId().intValue();
	}

	@Override
	public List<DesignPlanTemplateProduct> getListByPlanId(Integer designPlanTemplateId) throws IntelligenceDecorationException {
		String exceptionLogPrefix = "查找副本产品列表失败";
		
		// 参数验证 ->start
		if(designPlanTemplateId == null) {
			String log = "prarms error: designPlanTemplateId = null";
			LOGGER.error(LOGPREFIX + log);
			throw new IntelligenceDecorationException(exceptionLogPrefix + "(" + log + ")");
		}
		// 参数验证 ->end
		
		return designPlanTemplateProductMapper.selectByPlanId(designPlanTemplateId, 0);
	}
	
}
