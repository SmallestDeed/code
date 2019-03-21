package com.sandu.service.area.dao;

import com.sandu.api.area.model.BaseArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseAreaMapper {
    int insertSelective(BaseArea record);

    BaseArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseArea record);

    List<BaseArea> listAreaByName(@Param("province")String province,
                                  @Param("city")String city,
                                  @Param("dist")String dist);
}