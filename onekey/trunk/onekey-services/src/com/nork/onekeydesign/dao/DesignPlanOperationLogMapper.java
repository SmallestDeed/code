package com.nork.onekeydesign.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.DesignPlanOperationLog;
import com.nork.product.dao.BaseMapper;

/**   
 * @Title: DesignPlanOperationLogMapper.java 
 * @Package com.nork.onekeydesign.dao
 * @Description:设计方案-设计方案操作日志Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-06-27 14:41:51
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanOperationLogMapper extends BaseMapper<DesignPlanOperationLog> {
    
	/**
	 * 其他
	 * 
	 */
}
