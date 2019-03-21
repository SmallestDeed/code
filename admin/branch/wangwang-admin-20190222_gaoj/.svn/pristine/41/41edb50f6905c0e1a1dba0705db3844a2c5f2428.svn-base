package com.sandu.service.company.dao;

import com.sandu.api.company.model.CompanyCategoryRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface CompanyCategoryRelDao {
    /**
     *  批量插入
     * @param lists
     * @return
     */
    int saveCompanyCategoryList(@Param("lists") List<CompanyCategoryRel> lists);

    int save(CompanyCategoryRel companyCategoryRel);

    void update(CompanyCategoryRel companyCategoryRel);
}
