package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.product.dao.BaseWaterjetTemplateMapper;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;
import com.nork.product.service.BaseWaterjetTemplateService;
import com.nork.system.model.ResPic;
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

	@Autowired
	private ResFileService resFileService;
	
	@Autowired
	private ResPicService resPicService;
	
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

	@Override
	public boolean save(BaseWaterjetTemplate baseWaterjetTemplate, LoginUser loginUser) {
		// 参数验证 ->start
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "baseWaterjetTemplate == null");
			return false;
		}
		// 参数验证 ->end
		
		this.sysSave(baseWaterjetTemplate, loginUser);
		
		if(baseWaterjetTemplate.getId() == null || baseWaterjetTemplate.getId() <= 0) {
			// create
			// 新增方法参数验证 ->start
			boolean flag = this.checkParamsForAdd(baseWaterjetTemplate);
			if(!flag) {
				return false;
			}
			// 新增方法参数验证 ->end
			
			this.add(baseWaterjetTemplate);
		}else {
			// update
			int updateCount = this.update(baseWaterjetTemplate);
			if(updateCount < 0) {
				logger.error(logPrefix + "update failed,id = {} 的数据没有找到", baseWaterjetTemplate.getId());
				return false;
			}
		}
		
		// 回填文件businessId ->start
		this.updateFileBusinessId(baseWaterjetTemplate);
		// 回填文件businessId ->end
		
		return true;
	}

	/**
	 * 回填文件(模型文件/展示图片)businessId
	 * 
	 * @author huangsongbo
	 * @param baseWaterjetTemplate
	 */
	private void updateFileBusinessId(BaseWaterjetTemplate baseWaterjetTemplate) {
		// 参数验证 ->start
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "baseWaterjetTemplate = null");
			return;
		}
		if(baseWaterjetTemplate.getId() == null) {
			logger.error(logPrefix + "baseWaterjetTemplate.getId() = null");
			return;
		}
		// 参数验证 ->end
		
		// templateFileId ->start
		Integer templateFileId = baseWaterjetTemplate.getTemplateFileId();
		resFileService.backfill(templateFileId, baseWaterjetTemplate.getId());
		// templateFileId ->end
		
		// templatePicId ->start
		Integer templatePicId = baseWaterjetTemplate.getTemplatePicId();
		resPicService.backfill(templatePicId, baseWaterjetTemplate.getId());
		// templatePicId ->end
		
	}

	@Override
	public boolean checkParamsForAdd(BaseWaterjetTemplate baseWaterjetTemplate) {
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "baseWaterjetTemplate = nulll");
			return false;
		}
		if(baseWaterjetTemplate.getTemplateLength() == null) {
			logger.error(logPrefix + "baseWaterjetTemplate.getTemplateLength() = null");
			return false;
		}
		if(baseWaterjetTemplate.getTemplateWidth() == null) {
			logger.error(logPrefix + "baseWaterjetTemplate.getTemplateWidth() = null");
			return false;
		}
		if(StringUtils.isEmpty(baseWaterjetTemplate.getTemplateName())) {
			logger.error(logPrefix + "StringUtils.isEmpty(baseWaterjetTemplate.getTemplateName()) = true");
			return false;
		}
		if(baseWaterjetTemplate.getTemplateFileId() == null || baseWaterjetTemplate.getTemplateFileId() <= 0) {
			logger.error(logPrefix + "(baseWaterjetTemplate.getTemplateFileId() == null || baseWaterjetTemplate.getTemplateFileId() <= 0) = true");
			return false;
		}
		if(baseWaterjetTemplate.getTemplatePicId() == null || baseWaterjetTemplate.getTemplatePicId() <= 0) {
			logger.error(logPrefix + "(baseWaterjetTemplate.getTemplatePicId() == null || baseWaterjetTemplate.getTemplatePicId() <= 0) = true");
			return false;
		}
		return true;
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(BaseWaterjetTemplate model, LoginUser loginUser) {
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
	public List<BaseWaterjetTemplate> setMoreInfoForfindAll(List<BaseWaterjetTemplate> list) {
		// 参数验证 ->start
		if(Lists.isEmpty(list)) {
			logger.error(logPrefix + "Lists.isEmpty(list) = true");
			return list;
		}
		// 参数验证 ->end
		
		// 获取ipad端缩略图 ->start
		List<Integer> smallPicIdList = new ArrayList<Integer>();
		list.forEach(item -> {
			if(StringUtils.isNotEmpty(item.getTemplateSmallPicInfo())) {
				// web:6092535;ipad:6092534;
				Map<String, String> smallPicInfoMap = Utils.getMapFromStr(item.getTemplateSmallPicInfo());
				try {
					item.setTemplateIpadSmallPicId(Integer.valueOf(smallPicInfoMap.get("ipad")));
				}catch (Exception e) {
					logger.error("String转换Integer失败; templateIpadSmallPicId = {};", smallPicInfoMap.get("ipad"));
				}
				if(item.getTemplateIpadSmallPicId() != null) {
					smallPicIdList.add(item.getTemplateIpadSmallPicId());
				}
			}
		});
		
		// key:picId;value:picPath
		Map<Integer, String> picPathMap = new HashMap<Integer, String>();
		if(Lists.isNotEmpty(smallPicIdList)) {
			List<ResPic> resPicList = resPicService.getPicPathByIdList(smallPicIdList);
			if(Lists.isNotEmpty(resPicList)) {
				resPicList.forEach(item -> {
					picPathMap.put(item.getId(), item.getPicPath());
				});
			}
		}
		
		list.forEach(item -> {
			if(item.getTemplateIpadSmallPicId() != null) {
				item.setTemplatePicUrl(picPathMap.get(item.getTemplateIpadSmallPicId()));
			}
		});
		// 获取ipad端缩略图 ->end
		
		return list;
	}
	
}
