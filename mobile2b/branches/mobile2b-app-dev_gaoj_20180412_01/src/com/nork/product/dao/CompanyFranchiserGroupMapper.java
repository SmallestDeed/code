package com.nork.product.dao;


import com.nork.product.model.CompanyFranchiserGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CompanyFranchiserGroupMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);
    int delBySysUserId(@Param("id") Integer id);
    int deleteLogicByPrimaryKey(Integer id);
    /**
     *
     * @mbggenerated
     */
    int insertSelective(CompanyFranchiserGroup record);

    /**
     *
     * @mbggenerated
     */
    CompanyFranchiserGroup selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CompanyFranchiserGroup record);

    /**
     *
     * @mbggenerated
     */


    List<CompanyFranchiserGroup> selectListSelective(CompanyFranchiserGroup record);
}