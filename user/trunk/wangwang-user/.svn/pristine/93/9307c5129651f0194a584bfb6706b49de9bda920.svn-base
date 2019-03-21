package com.sandu.service.operatorLog.dao;

import com.sandu.api.operatorLog.model.SysUserOperatorLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/7/27  14:41
 */
@Component
public interface SysUserOperatorLogDao {
    /**
     * 新增操作日志
     * @param operatorLog 新增入参
     * @return int 新增结果(>0新增成功)
     */
    int insert(SysUserOperatorLog operatorLog);

    /**
     * 通过用户id查询日志集合
     * @param userId 用户Id
     * @return list 查询结果
     */
    List<SysUserOperatorLog> selectByUserId(@Param("userId") Long userId);

    int updateByUserId(Long userId);
}
