package com.sandu.service.base.dao;


import com.sandu.api.base.model.BaseBrand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseBrand record);

    int insertSelective(BaseBrand record);

    BaseBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrand record);

    int updateByPrimaryKey(BaseBrand record);

    List<BaseBrand> selectByCompanyId(Long id);
}