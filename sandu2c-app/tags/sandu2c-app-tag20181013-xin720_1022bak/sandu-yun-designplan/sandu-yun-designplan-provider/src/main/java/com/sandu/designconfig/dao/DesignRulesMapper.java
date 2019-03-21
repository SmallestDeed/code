package com.sandu.designconfig.dao;

import com.sandu.designconfig.model.DesignRules;
import com.sandu.designconfig.model.search.DesignRulesSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignRulesMapper.java
 * @Package com.sandu.designconfig.dao
 * @Description:设计配置-设计规则Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-03-23 19:56:47
 */
@Repository
public interface DesignRulesMapper {
    int insertSelective(DesignRules record);

    int updateByPrimaryKeySelective(DesignRules record);

    int deleteByPrimaryKey(Integer id);

    DesignRules selectByPrimaryKey(Integer id);

    int selectCount(DesignRulesSearch designRulesSearch);

    List<DesignRules> selectPaginatedList(DesignRulesSearch designRulesSearch);

    List<DesignRules> selectList(DesignRules designRules);
}
