package com.sandu.api.base.service;

import com.sandu.api.base.model.BaseBrand;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseBrandService {
    int deleteByPrimaryKey(Long id);

    int insert(BaseBrand record);

    int insertSelective(BaseBrand record);

    BaseBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrand record);

    int updateByPrimaryKey(BaseBrand record);

    List<BaseBrand> selectByCompanyId(Long id);
}
