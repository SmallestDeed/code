package com.nork.system.dao;


import com.nork.system.model.ModelAreaRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 模型-贴图区域关联 DAO
 * @Date 2018/8/7 0007 10:06
 * @Modified By
 */
@Repository
public interface ModelAreaRelMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ModelAreaRel record);

    ModelAreaRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModelAreaRel record);

    /**
     * 根据模型 查询贴图 集合
     * @author chenqiang
     * @param modelId 模型id
     * @return ModelAreaRel集合
     * @date 2018/8/7 0007 10:30
     */
    List<ModelAreaRel> selectListByModel(@Param("modelId") Integer modelId);
}