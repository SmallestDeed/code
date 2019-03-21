package com.sandu.service.base.dao;

import com.sandu.api.base.model.BaseProductStyle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseProductStyleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseProductStyle record);

    int insertSelective(BaseProductStyle record);

    BaseProductStyle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseProductStyle record);

    int updateByPrimaryKey(BaseProductStyle record);

    List<BaseProductStyle> selectListByParentCode(@Param("code") String code);
}