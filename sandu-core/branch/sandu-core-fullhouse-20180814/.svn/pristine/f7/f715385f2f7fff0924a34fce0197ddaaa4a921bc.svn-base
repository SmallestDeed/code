package com.sandu.api.base.service;

import com.sandu.api.base.model.BaseProductStyle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseProductStyleService {
    int deleteByPrimaryKey(Long id);

    int insert(BaseProductStyle record);

    int insertSelective(BaseProductStyle record);

    BaseProductStyle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseProductStyle record);

    int updateByPrimaryKey(BaseProductStyle record);

    List<BaseProductStyle> selectListByParentCode(String code);
}
