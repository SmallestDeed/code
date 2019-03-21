package com.sandu.user.service;

import com.sandu.pay.order.model.ResultMessage;
import com.sandu.user.model.CompanyFranchiserGroup;

/**
 * 企业经销商账号组表对应的service
 *
 * @Author yzw
 * @Date 2018/2/5 16:18
 */
public interface CompanyFranchiserGroupService {
    /**
     * 添加
     *
     * @param record
     * @return
     */
    CompanyFranchiserGroup add(CompanyFranchiserGroup record);

    /**
     * 删除
     *
     * @param id 企业经销商账号组id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    CompanyFranchiserGroup update(CompanyFranchiserGroup record);

    /**
     * 获取
     *
     * @param id 企业经销商账号组id
     * @return
     */
    CompanyFranchiserGroup get(Integer id);

    /**
     * 判断用户是否有“共享度币”的权限
     *
     * @param userId
     * @return
     */
    public ResultMessage checkUserShare(Integer userId);

    /**
     * 根据用户id获取企业经销商账号组信息
     *
     * @param userId 用户id
     * @return
     */
    public CompanyFranchiserGroup getCompanyFranchiserGroupByUserId(Integer userId);

    /**
     * 企业经销商账号组信息表的消费度币和使用度币(减少公共度币，添加消费度币)
     *
     * @param userId   用户id
     * @param totalFee 总费用
     * @return
     */
    public boolean updateReduceIntegral(Integer userId, Integer totalFee);

    /**
     * 企业经销商账号组信息表的消费度币和使用度币(添加公共度币，减少消费度币)
     *
     * @param userId   用户id
     * @param totalFee 总费用
     * @return
     */
    public boolean updateAddIntegral(Integer userId, Integer totalFee);

    /**
     * 企业经销商账号组信息表的消费度币和使用度币(添加公共度币，消费度币不作处理)
     *
     * @param userId   用户id
     * @param totalFee 总费用
     * @return
     */
    public boolean updateRechargeIntegral(Integer userId, Integer totalFee);

    /**
     * 添加消费度币，公共度币不作处理
     *
     * @param userId   用户id
     * @param totalFee 总费用
     * @return
     */
    public boolean updateConsumeIntegral(Integer userId, Integer totalFee);
}
