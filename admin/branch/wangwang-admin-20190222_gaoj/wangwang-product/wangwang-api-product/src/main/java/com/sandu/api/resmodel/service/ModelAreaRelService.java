package com.sandu.api.resmodel.service;

import com.sandu.api.resmodel.model.ModelAreaRel;

import java.util.List;

/**
 * @author Sandu
 */
public interface ModelAreaRelService {
    /**
     * 新增模型区域信息
     * @param list
     * @return
     */
    int saveModelAreaRelList(List<ModelAreaRel> list);

    /**
     * 根据模型ID获取模型所有区域
     * @param modelId
     * @return
     */
    List<ModelAreaRel> listByModelId(Integer modelId);

    /**
     * 模型ids获取 区域
     * @param ids ids
     * @return list
     */
    List<ModelAreaRel> listByModelIds(List<Long> ids);

    void deleteAreaByModelId(Integer modelId);
    
    int updateModelAreaRel(ModelAreaRel rel);
}
