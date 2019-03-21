package com.sandu.service.user.dao;

import com.sandu.api.user.model.AuthorizedConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface AuthorizedConfigDao {
    AuthorizedConfig getByUserId(@Param("userId") int id);

    List<Integer> listCompanyUserIds(@Param("companyId") Integer companyId);
}
