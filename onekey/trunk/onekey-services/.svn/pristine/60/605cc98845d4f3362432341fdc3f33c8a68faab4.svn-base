package com.nork.onekeydesign.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.dao.DesignPlanOperationLogMapper;
import com.nork.onekeydesign.model.DesignPlanOperationLog;
import com.nork.onekeydesign.model.search.DesignPlanOperationLogSearch;
import com.nork.onekeydesign.model.small.DesignPlanOperationLogSmall;
import com.nork.onekeydesign.service.DesignPlanOperationLogService;

/**
 * @Title: DesignPlanOperationLogServiceImpl.java
 * @Package com.nork.onekeydesign.service.impl
 * @Description:设计方案-设计方案操作日志ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-06-27 14:41:51
 * @version V1.0
 */
@Service("designPlanOperationLogService")
public class DesignPlanOperationLogServiceImpl implements
		DesignPlanOperationLogService {

	private static Logger logger = Logger
			.getLogger(DesignPlanOperationLogServiceImpl.class);
	private final JsonDataServiceImpl<DesignPlanOperationLog> JsonUtil = new JsonDataServiceImpl<DesignPlanOperationLog>();
	@Autowired
	private DesignPlanOperationLogMapper designPlanOperationLogMapper;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 保存 设计方案操作日志
	 * 
	 * @param style
	 * @param designPlanOperationLog
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope save(String style,
			DesignPlanOperationLog designPlanOperationLog) {

		if (designPlanOperationLog.getId() == null) {
			int id = designPlanOperationLogMapper
					.insertSelective(designPlanOperationLog);
			logger.info("add:id=" + id);
			designPlanOperationLog.setId(id);
		} else {
			int id = designPlanOperationLogMapper
					.updateByPrimaryKeySelective(designPlanOperationLog);
			logger.info("update:id=" + id);
		}

		if ("small".equals(style)) {
			String designPlanOperationLogJson = JsonUtil
					.getBeanToJsonData(designPlanOperationLog);
			DesignPlanOperationLogSmall designPlanOperationLogSmall = new JsonDataServiceImpl<DesignPlanOperationLogSmall>()
					.getJsonToBean(designPlanOperationLogJson,
							DesignPlanOperationLogSmall.class);

			return new ResponseEnvelope<DesignPlanOperationLogSmall>(
					designPlanOperationLogSmall,
					designPlanOperationLog.getMsgId(), true);
		}

		return new ResponseEnvelope<DesignPlanOperationLog>(
				designPlanOperationLog, designPlanOperationLog.getMsgId(), true);
	}

	/**
	 * 获取 设计方案操作日志详情
	 * 
	 * @param style
	 * @param designPlanOperationLog
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope get(String style, String msgId,
			DesignPlanOperationLog designPlanOperationLog, Integer id) {

		designPlanOperationLog = designPlanOperationLogMapper
				.selectByPrimaryKey(id);

		if ("small".equals(style) && designPlanOperationLog != null) {
			String designPlanOperationLogJson = JsonUtil
					.getBeanToJsonData(designPlanOperationLog);
			DesignPlanOperationLogSmall designPlanOperationLogSmall = new JsonDataServiceImpl<DesignPlanOperationLogSmall>()
					.getJsonToBean(designPlanOperationLogJson,
							DesignPlanOperationLogSmall.class);

			return new ResponseEnvelope<DesignPlanOperationLogSmall>(
					designPlanOperationLogSmall, msgId, true);
		}
		return new ResponseEnvelope<DesignPlanOperationLog>(
				designPlanOperationLog, msgId, true);
	}

	/**
	 * 删除设计方案操作日志,支持批量删除，传递ids=1,2,3格式即可
	 * 
	 * @param style
	 * @param msgId
	 * @param designPlanOperationLog
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope del(String style, String msgId,
			DesignPlanOperationLog designPlanOperationLog, String ids) {

		int i = 0;
		try {
			if (ids != null) {
				if (ids.contains(",")) {
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = designPlanOperationLogMapper.deleteByPrimaryKey(id);
						logger.info("delete:id=" + id);
					}
				} else {
					Integer id = new Integer(ids);
					i = designPlanOperationLogMapper.deleteByPrimaryKey(id);
					logger.info("delete:id=" + id);
				}
			}

			if (i == 0) {
				return new ResponseEnvelope<DesignPlanOperationLog>(false,
						"记录不存在!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanOperationLog>(false, "删除失败!",
					msgId);
		}

		return new ResponseEnvelope<DesignPlanOperationLog>(true, msgId, true);
	}

	/**
	 * 设计方案操作日志列表
	 * 
	 * @param style
	 * @param designPlanOperationLogSearch
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEnvelope list(String style,
			DesignPlanOperationLogSearch designPlanOperationLogSearch) {
		List<DesignPlanOperationLog> list = new ArrayList<DesignPlanOperationLog>();
		int total = 0;
		try {
			total = designPlanOperationLogMapper
					.selectCount(designPlanOperationLogSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = designPlanOperationLogMapper
						.selectPaginatedList(designPlanOperationLogSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String designPlanOperationLogJsonList = JsonUtil
						.getListToJsonData(list);
				List<DesignPlanOperationLogSmall> smallList = new JsonDataServiceImpl<DesignPlanOperationLogSmall>()
						.getJsonToBeanList(designPlanOperationLogJsonList,
								DesignPlanOperationLogSmall.class);
				return new ResponseEnvelope<DesignPlanOperationLogSmall>(total,
						smallList, designPlanOperationLogSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanOperationLog>(false, "数据异常!",
					designPlanOperationLogSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanOperationLog>(total, list,
				designPlanOperationLogSearch.getMsgId());
	}

	/**
	 * 设计方案操作日志全部列表
	 * 
	 * @param style
	 * @param designPlanOperationLogSearch
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEnvelope listAll(String style,
			DesignPlanOperationLogSearch designPlanOperationLogSearch) {
		List<DesignPlanOperationLog> list = new ArrayList<DesignPlanOperationLog>();
		int total = 0;
		try {
			total = designPlanOperationLogMapper
					.selectCount(designPlanOperationLogSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = designPlanOperationLogMapper
						.selectList(designPlanOperationLogSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String designPlanOperationLogJsonList = JsonUtil
						.getListToJsonData(list);
				List<DesignPlanOperationLogSmall> smallList = new JsonDataServiceImpl<DesignPlanOperationLogSmall>()
						.getJsonToBeanList(designPlanOperationLogJsonList,
								DesignPlanOperationLogSmall.class);
				return new ResponseEnvelope<DesignPlanOperationLogSmall>(total,
						smallList, designPlanOperationLogSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanOperationLog>(false, "数据异常!",
					designPlanOperationLogSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanOperationLog>(total, list,
				designPlanOperationLogSearch.getMsgId());
	}

	/**
	 * 获取 设计方案操作日志详情---jsp
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEnvelope jspget(Integer id) {
		DesignPlanOperationLog designPlanOperationLog = null;
		try {
			designPlanOperationLog = designPlanOperationLogMapper
					.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanOperationLog>(false, "数据异常!");
		}
		return new ResponseEnvelope<DesignPlanOperationLog>(
				designPlanOperationLog);
	}

	/**
	 * 设计方案操作日志列表---jsp
	 * 
	 * @param designPlanOperationLogSearch
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEnvelope jsplist(
			DesignPlanOperationLogSearch designPlanOperationLogSearch,
			List<DesignPlanOperationLog> list) {
		// List<DesignPlanOperationLog> list = new
		// ArrayList<DesignPlanOperationLog> ();
		int total = 0;
		try {
			total = designPlanOperationLogMapper
					.selectCount(designPlanOperationLogSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = designPlanOperationLogMapper
						.selectPaginatedList(designPlanOperationLogSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanOperationLog>(false, "数据异常!");
		}

		return new ResponseEnvelope<DesignPlanOperationLog>(total, list);
	}

    /* (non-Javadoc)    
     * @see com.nork.onekeydesign.service.DesignPlanOperationLogService#selectCount(org.apache.poi.ss.formula.functions.T)
     */
    @Override
    public int selectCount(DesignPlanOperationLog t) {
        return designPlanOperationLogMapper.selectCount(t);
    }

    /* (non-Javadoc)    
     * @see com.nork.onekeydesign.service.DesignPlanOperationLogService#selectPaginatedList(org.apache.poi.ss.formula.functions.T)
     */
    @Override
    public List selectPaginatedList(DesignPlanOperationLog t) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)    
     * @see com.nork.onekeydesign.service.DesignPlanOperationLogService#insertSelective(com.nork.onekeydesign.model.DesignPlanOperationLog)
     */
    @Override
    public void insertSelective(DesignPlanOperationLog t) {
        designPlanOperationLogMapper.insertSelective(t);
        
    }

	/**
	 * 
	 */

}
