package com.nork.product.dao;


import com.nork.product.model.BaseCompanyMiniProgramConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BaseCompanyMiniProgramConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseCompanyMiniProgramConfig record);

    int insertSelective(BaseCompanyMiniProgramConfig record);

    BaseCompanyMiniProgramConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseCompanyMiniProgramConfig record);

    int updateByPrimaryKey(BaseCompanyMiniProgramConfig record);

    List<BaseCompanyMiniProgramConfig> selectList(BaseCompanyMiniProgramConfig config);
}