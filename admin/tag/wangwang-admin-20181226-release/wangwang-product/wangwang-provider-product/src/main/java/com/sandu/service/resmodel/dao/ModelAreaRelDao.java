package com.sandu.service.resmodel.dao;

import com.sandu.api.resmodel.model.ModelAreaRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface ModelAreaRelDao {


    int deleteByPrimaryKey(Integer id);

    int insert(ModelAreaRel record);

    int insertSelective(ModelAreaRel record);

    ModelAreaRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModelAreaRel record);

    int updateByPrimaryKey(ModelAreaRel record);

    int insertList(List<ModelAreaRel> list);

    /**
     * 通过id获取模型区域
     *
     * @param modelId modelid
     * @return list
     */
    List<ModelAreaRel> listByModelId(Integer modelId);

    /**
     * 通过ids获取模型区域
     *
     * @param ids ids
     * @return list
     */
    List<ModelAreaRel> listByModelIds(@Param("ids") List<Long> ids);

    void deleteAreaByModelId(@Param("modelId") Integer modelId);
}