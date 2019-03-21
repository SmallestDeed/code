package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.model.DesignPlanOperationLog;
import com.nork.onekeydesign.model.search.DesignPlanOperationLogSearch;


/**   
 * @Title: DesignPlanOperationLogService.java 
 * @Package com.nork.onekeydesign.service
 * @Description:设计方案-设计方案操作日志Service
 * @createAuthor pandajun 
 * @CreateDate 2017-06-27 14:41:51
 * @version V1.0   
 */
@SuppressWarnings("rawtypes")
public interface DesignPlanOperationLogService {

	
	/**
	 * 保存 设计方案操作日志
	 * 
	 * @param style
	 * @param designPlanOperationLog
	 * @return
	 */
	public ResponseEnvelope save(String style,DesignPlanOperationLog designPlanOperationLog);
	
	/**
	 * 获取 设计方案操作日志详情
	 * 
	 * @param style
	 * @param designPlanOperationLog
	 * @param id
	 * @return
	 */
	public ResponseEnvelope get(String style,String msgId, DesignPlanOperationLog designPlanOperationLog,Integer id);
	
	/**
	 * 删除设计方案操作日志,支持批量删除，传递ids=1,2,3格式即可
	 * @param style
	 * @param msgId
	 * @param designPlanOperationLog
	 * @param ids
	 * @return
	 */
	public ResponseEnvelope del(String style,String msgId,DesignPlanOperationLog designPlanOperationLog,String ids);
	
	/**
	 * 设计方案操作日志列表
	 * @param style
	 * @param designPlanOperationLogSearch
	 * @return
	 */
	public ResponseEnvelope list(String style,DesignPlanOperationLogSearch designPlanOperationLogSearch);
	

	/**
	 *  设计方案操作日志全部列表
	 * @param style
	 * @param designPlanOperationLogSearch
	 * @return
	 */
	public ResponseEnvelope listAll(String style, DesignPlanOperationLogSearch designPlanOperationLogSearch);
	
	/**
	 * 获取 设计方案操作日志详情---jsp
	 * @param id
	 * @return
	 */
	public ResponseEnvelope jspget(Integer id);
			

	/**
	 * 设计方案操作日志列表---jsp
	 * @param designPlanOperationLogSearch
	 * @return
	 */
	public ResponseEnvelope jsplist(DesignPlanOperationLogSearch designPlanOperationLogSearch,List<DesignPlanOperationLog> list);

    /**
     *    获取数据数量
     *
     * @param  T
     * @return   int
     */
    public abstract int selectCount(DesignPlanOperationLog t);

    /**
     *    分页获取数据
     *
     * @param  T
     * @return   List
     */
    public abstract List selectPaginatedList(DesignPlanOperationLog t);
    public void  insertSelective (DesignPlanOperationLog t);
			
}
