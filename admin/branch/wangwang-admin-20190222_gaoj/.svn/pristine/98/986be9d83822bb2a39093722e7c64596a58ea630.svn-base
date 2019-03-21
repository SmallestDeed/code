package com.sandu.api.resmodel.service;

import com.sandu.api.resmodel.model.ModelCopyLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by bvvy
 * @date 2018/4/13
 */
public interface ModelCopyLogService {
    /**
     * 添加模型copy记录
     *
     * @param modelCopyLog record
     */
    void addModelCopyLog(ModelCopyLog modelCopyLog);

    /**
     * 根据来源id删除
     *
     * @param modelId modelid
     */
    void deleteBySourceId( Long modelId);

    /**
     * 删除
     *
     * @param companyId 公司
     * @param modelId   模型
     */
    void deleteByCompanyIdAndSourceId( Long companyId, Long modelId);

    /**
     * 获取原模型交付后变成的模型
     *
     * @param modelId   源模型
     * @param companyId 已经交付的公司
     * @return 交付后的模型
     */
    List<Long> listDeliveredModelIds(Long modelId,Long companyId);

}
