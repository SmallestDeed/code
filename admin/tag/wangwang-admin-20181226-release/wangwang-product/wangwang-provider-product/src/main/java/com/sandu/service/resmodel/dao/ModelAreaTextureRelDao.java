package com.sandu.service.resmodel.dao;

import com.sandu.api.resmodel.model.ModelAreaTextureRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface ModelAreaTextureRelDao {

    int deleteByPrimaryKey(Integer id);

    int insert(ModelAreaTextureRel record);

    int insertSelective(ModelAreaTextureRel record);

    ModelAreaTextureRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModelAreaTextureRel record);

    int updateByPrimaryKey(ModelAreaTextureRel record);

    int insertList(List<ModelAreaTextureRel> list);

    int deleteByAreaIds(List<Integer> areaIds);

    List<Integer> getIdsByAreaIds(@Param("ids") List<Integer> ids);

    int deleteByIds(@Param("ids") List<Integer> ids);

	List<Integer> getTextureIdsById(@Param("ids")List<Integer> idList);

	List<ModelAreaTextureRel> getModelAreaTextureRelList(@Param("ids")List<Integer> areaIds);
}