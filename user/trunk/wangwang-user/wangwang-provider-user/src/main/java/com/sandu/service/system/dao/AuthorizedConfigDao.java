package com.sandu.service.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.system.input.AuthorizedConfigSearch;
import com.sandu.api.system.model.AuthorizedConfig;

@Repository
public interface AuthorizedConfigDao {

    int selectCount(AuthorizedConfigSearch authorizedConfigSearch);

    AuthorizedConfig selectByUserId(@Param("userId") Long userId);
    
    List<AuthorizedConfig> selectList(AuthorizedConfig authorizedConfig);
}
