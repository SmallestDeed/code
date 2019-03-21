package com.nork.product.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.product.dao.BaseWaterjetTemplateMapper;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;
import com.nork.product.service.BaseWaterjetTemplateService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResPicService;

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
	
	private String logPrefix = "customLog:";
	
	@Autowired
	private BaseWaterjetTemplateMapper baseWaterjetTemplateMapper;
	
	/**
	 * 新增数据
	 *
	 * @param baseWaterjetTemplate
	 * @return  int 
	 */
	@Override
	public int add(BaseWaterjetTemplate baseWaterjetTemplate) {
		baseWaterjetTemplateMapper.insertSelective(baseWaterjetTemplate);
		return baseWaterjetTemplate.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseWaterjetTemplate
	 * @return  int 
	 */
	@Override
	public int update(BaseWaterjetTemplate baseWaterjetTemplate) {
		return baseWaterjetTemplateMapper
				.updateByPrimaryKeySelective(baseWaterjetTemplate);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseWaterjetTemplateMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseWaterjetTemplate 
	 */
	@Override
	public BaseWaterjetTemplate get(Integer id) {
		return baseWaterjetTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseWaterjetTemplate
	 * @return   List<BaseWaterjetTemplate>
	 */
	@Override
	public List<BaseWaterjetTemplate> getList(BaseWaterjetTemplate baseWaterjetTemplate) {
	    return baseWaterjetTemplateMapper.selectList(baseWaterjetTemplate);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseWaterjetTemplate
	 * @return   int
	 */
	@Override
	public int getCount(BaseWaterjetTemplateSearch baseWaterjetTemplateSearch){
		return  baseWaterjetTemplateMapper.selectCount(baseWaterjetTemplateSearch);	
    }
	
	/**
	 *    分页获取数据
	 *
	 * @param  baseWaterjetTemplate
	 * @return   List<BaseWaterjetTemplate>
	 */
	@Override
	public List<BaseWaterjetTemplate> getPaginatedList(
			BaseWaterjetTemplateSearch baseWaterjetTemplateSearch) {
		return baseWaterjetTemplateMapper.selectPaginatedList(baseWaterjetTemplateSearch);
	}

	/**
	 * 自动存储系统字段
	 */
	@Override
	public void sysSave(BaseWaterjetTemplate model, LoginUser loginUser) {
		if (model != null) {
			/*LoginUser loginUser = new LoginUser();
			if (request.getSession() == null || request.getSession().getAttribute("loginUser") == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
			}*/

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public List<BaseWaterjetTemplate> findAllBySearch(BaseWaterjetTemplateSearch search) {
		// 参数验证/处理 ->start
		if(search == null) {
			logger.error(logPrefix + "search = null");
			return null;
		}
		/*if(search.getIsDeleted() == null) {
			search.setIsDeleted(0);
		}*/
		// 参数验证/处理 ->end
		
		return baseWaterjetTemplateMapper.findAllBySearch(search);
	}

	@Override
	public int getCountBySearch(BaseWaterjetTemplateSearch search) {
		// 参数验证/处理 ->start
		if(search == null) {
			logger.error(logPrefix + "search = null");
			return 0;
		}
		// 参数验证/处理 ->end
		
		return baseWaterjetTemplateMapper.getCountBySearch(search);
	}

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
