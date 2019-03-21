package com.nork.system.dao;

import com.nork.system.model.ModelAreaTextureRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 模型贴图区域-贴图关联 DAO
 * @Date 2018/8/7 0007 10:06
 * @Modified By
 */
@Repository
public interface ModelAreaTextureRelMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ModelAreaTextureRel record);

    ModelAreaTextureRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModelAreaTextureRel record);

    /**
     * 根据模型-贴图区域IdList 获取 集合
     * @author chenqiang
     * @param areaIdList 模型-贴图区域IdList
     * @return ModelAreaTextureRel 集合
     * @date 2018/8/7 0007 11:05
     */
    List<ModelAreaTextureRel> selectListByModelAreaIdList(@Param("areaIdList") List<Integer> areaIdList, @Param("isDefault") String isDefault);
}