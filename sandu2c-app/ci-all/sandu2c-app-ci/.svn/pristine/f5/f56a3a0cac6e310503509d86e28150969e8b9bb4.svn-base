package com.sandu.product.dao;

import com.sandu.product.model.AuthorizedConfig;
import com.sandu.product.model.search.AuthorizedConfigSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: AuthorizedConfigMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品-授权配置Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-04-27 14:07:34
 */
@Repository
public interface AuthorizedConfigMapper {
    int insertSelective(AuthorizedConfig record);

    int updateByPrimaryKeySelective(AuthorizedConfig record);

    int deleteByPrimaryKey(Integer id);

    AuthorizedConfig selectByPrimaryKey(Integer id);

    int selectCount(AuthorizedConfigSearch authorizedConfigSearch);

    List<AuthorizedConfig> selectPaginatedList(
            AuthorizedConfigSearch authorizedConfigSearch);

    List<AuthorizedConfig> selectList(AuthorizedConfig authorizedConfig);

}
