package com.sandu.pay.user.dao;

import com.sandu.user.model.CompanyFranchiserGroup;


/**
 * 企业经销商账号组表对应的mapper
 *
 * @Author yzw
 * @Date 2018/2/5 16:20
 */
public interface CompanyFranchiserGroupMapper {

    /**
     * 删除
     *
     * @param id 企业经销商账号组id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加
     *
     * @param record
     * @return
     */
    int insertSelective(CompanyFranchiserGroup record);


    /**
     * 获取
     *
     * @param id 企业经销商账号组id
     * @return
     */
    CompanyFranchiserGroup selectByPrimaryKey(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CompanyFranchiserGroup record);
}
