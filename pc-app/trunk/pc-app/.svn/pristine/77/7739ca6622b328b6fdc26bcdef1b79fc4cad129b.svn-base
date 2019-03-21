package com.nork.repair.dao;

import com.nork.repair.model.ModelAreaRel;
import com.nork.repair.model.ModelAreaTextureRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RepairMapper {

    List<ModelAreaRel> getModelAreaRelList(@Param("modelId") Integer modelId);

    Integer insertModelAreaRelSelective(ModelAreaRel modelAreaRel);

    Integer insertModelAreaTextureRelSelective(ModelAreaTextureRel modelAreaTextureRel);

    List<ModelAreaTextureRel> getAreaTextureList(@Param("areaId") Integer areaId);

}
