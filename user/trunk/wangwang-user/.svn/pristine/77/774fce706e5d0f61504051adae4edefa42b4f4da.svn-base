package com.sandu.api.operatorLog.service;

import com.sandu.api.operatorLog.model.SysUserOperatorLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/7/27  14:33
 */
@Repository
public interface SysUserOperatorLogService {

    /**
     * 新增操作日志
     * @param operatorLog 新增入参
     * @return int 新增结果(>0新增成功)
     */
    int addLog(SysUserOperatorLog operatorLog);

    /**
     * 通过用户id查询日志集合
     * @param userId 用户Id
     * @return list 查询结果
     */
    List<SysUserOperatorLog> getLogByUserId(Long userId);


    int updateByUserId(Long id);
}
