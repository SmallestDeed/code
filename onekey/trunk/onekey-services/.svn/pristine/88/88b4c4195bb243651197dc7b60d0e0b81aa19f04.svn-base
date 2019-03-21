package com.nork.onekeydesign.service.impl;

import com.nork.onekeydesign.dao.BaseWaterjetTemplateMapper;
import com.nork.onekeydesign.model.BaseWaterjetTemplate;
import com.nork.onekeydesign.service.BaseWaterjetTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: BaseWaterjetTemplateServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-水刀模版ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-06-04 14:30:27
 * @version V1.0   
 */
@Service("baseWaterjetTemplateService")
@Transactional
public class BaseWaterjetTemplateServiceImpl implements BaseWaterjetTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(BaseWaterjetTemplateServiceImpl.class);

	@Autowired
	private BaseWaterjetTemplateMapper baseWaterjetTemplateMapper;

	@Override
	public BaseWaterjetTemplate getMoreInfoById(Integer id) {
		// 参数验证 ->start
		if(id == null) {
			logger.error("id = null");
			return null;
		}
		// 参数验证 ->end

		return baseWaterjetTemplateMapper.getMoreInfoById(id);
	}
}