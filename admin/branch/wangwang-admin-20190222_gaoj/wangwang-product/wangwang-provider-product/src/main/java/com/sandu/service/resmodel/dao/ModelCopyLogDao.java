package com.sandu.service.resmodel.dao;

import com.sandu.api.resmodel.model.ModelCopyLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author by bvvy
 * @date 2018/4/13
 */
public interface ModelCopyLogDao {

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
    void deleteBySourceId(@Param("modelId") Long modelId);

    /**
     * 删除
     *
     * @param companyId 公司
     * @param modelId   模型
     */
    void deleteByCompanyIdAndSourceId(@Param("companyId") Long companyId, @Param("modelId") Long modelId);

    /**
     * 获取原模型交付后变成的模型
     *
     * @param modelId   源模型
     * @param companyId 被交付的公司id
     * @return 交付后的模型
     */
    @Select("select target_id from res_model_copy_log where source_id = #{modelId} and target_company_id = #{companyId}")
    List<Long> listDeliveredModelIds(@Param("modelId") Long modelId, @Param("companyId") Long companyId);
}
