package com.sandu.pay.user.service.impl;

import com.google.gson.Gson;
import com.sandu.common.constant.UserConstants;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.user.dao.CompanyFranchiserGroupMapper;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业经销商账号组表对应的serviceImpl
 *
 * @Author yzw
 * @Date 2018/2/5 16:19
 */
@Service("companyFranchiserGroupService")
public class CompanyFranchiserGroupServiceImpl implements CompanyFranchiserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyFranchiserGroupServiceImpl.class);
    private final static Gson gson = new Gson();
    @Autowired
    private CompanyFranchiserGroupMapper companyFranchiserGroupMapper;
    @Resource
    private SysUserService sysUserService;

    /**
     * 添加
     *
     * @param record
     * @return
     */
    @Override
    public CompanyFranchiserGroup add(CompanyFranchiserGroup record) {
        if (this.companyFranchiserGroupMapper.insertSelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 删除
     *
     * @param id 企业经销商账号组id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        return this.companyFranchiserGroupMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Override
    public CompanyFranchiserGroup update(CompanyFranchiserGroup record) {
        if (this.companyFranchiserGroupMapper.updateByPrimaryKeySelective(record) == 1)
            return record;
        return null;
    }


    /**
     * 获取
     *
     * @param id 企业经销商账号组id
     * @return
     */
    @Override
    public CompanyFranchiserGroup get(Integer id) {
        return this.companyFranchiserGroupMapper.selectByPrimaryKey(id);
    }

    /**
     * 判断用户是否有“共享度币”的权限
     *
     * @param userId
     * @return
     */
    @Override
    public ResultMessage checkUserShare(Integer userId) {
        ResultMessage message = new ResultMessage();
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空");
            message.setMessage("用户信息为空");
            return message;
        }
        CompanyFranchiserGroup companyFranchiserGroup = this.getCompanyFranchiserGroupByUserId(userId);
        if (null == companyFranchiserGroup) {
            logger.info("用户不具备度币共享的权限，用户id：userId：{}" + userId);
            message.setMessage("用户不具备度币共享的权限，用户id：userId：{}" + userId);
            return message;
        }
        message.setMessage("当前用户具备度币共享的权限");
        message.setSuccess(true);
        message.setObj(sysUser.getFranchiserAccountType()); //1(主账号)、2(子账号)
        return message;
    }

    /**
     * 根据用户id获取企业经销商账号组信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public CompanyFranchiserGroup getCompanyFranchiserGroupByUserId(Integer userId) {
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空");
            return null;
        }
        if (null == sysUser.getUserType() || sysUser.getUserType().intValue() != UserConstants.FRANCHISER_USER_TYPE) {
            logger.info("用户类型不属于经销商，用户id：userId：{}" + userId);
            return null;
        }
        if (null == sysUser.getFranchiserAccountType() || (sysUser.getFranchiserAccountType() != UserConstants.MAIN_FRANCHISER_ACCOUNT_TYPE
                && sysUser.getFranchiserAccountType() != UserConstants.ORDER_FRANCHISER_ACCOUNT_TYPE)) {
            logger.info("用户账号类型不属于主账号或者子账号，用户id：userId：{}" + userId + "gson:sysyUser:{}" + gson.toJson(sysUser));
            return null;
        }
        if (null == sysUser.getFranchiserGroupId()) {
            logger.info("企业经销商账号组表信息为空，用户id：userId：{}" + userId);
            return null;
        }
        CompanyFranchiserGroup companyFranchiserGroup = this.get(sysUser.getFranchiserGroupId());
        if (null == companyFranchiserGroup) {
            logger.info("企业经销商账号组表信息为空，用户id：userId：{}" + userId);
            return null;
        }
        return companyFranchiserGroup;
    }

    /**
     * 企业经销商账号组信息表的消费度币和使用度币(减少公共度币，添加消费度币)
     *
     * @param userId 用户id
     * @param totalFee 总费用
     * @return
     */
    @Override
    public boolean updateReduceIntegral(Integer userId,Integer totalFee) {
        CompanyFranchiserGroup companyFranchiserGroup = this.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            companyFranchiserGroup.setGmtModified(new Date());
            BigDecimal totalFee1 = new BigDecimal(String.valueOf(totalFee));
            companyFranchiserGroup.setCommonalityIntegral(null != companyFranchiserGroup.getCommonalityIntegral() ?
                    companyFranchiserGroup.getCommonalityIntegral().subtract(totalFee1) : new BigDecimal("0")); // 剩下的公共度币
            companyFranchiserGroup.setConsumeIntegral(null != companyFranchiserGroup.getConsumeIntegral() ?
                    companyFranchiserGroup.getConsumeIntegral().add(totalFee1) : totalFee1); // 消费的度币
            return null != this.update(companyFranchiserGroup);
        }
        return false;
    }

    /**
     * 企业经销商账号组信息表的消费度币和使用度币(添加公共度币，减少消费度币)
     *
     * @param userId 用户id
     * @param totalFee 总费用
     * @return
     */
    @Override
    public boolean updateAddIntegral(Integer userId, Integer totalFee) {
        CompanyFranchiserGroup companyFranchiserGroup = this.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            companyFranchiserGroup.setGmtModified(new Date());
            BigDecimal totalFee1 = new BigDecimal(String.valueOf(totalFee));
            companyFranchiserGroup.setCommonalityIntegral(null != companyFranchiserGroup.getCommonalityIntegral() ?
                    companyFranchiserGroup.getCommonalityIntegral().add(totalFee1) : new BigDecimal("0")); // 剩下的公共度币
            companyFranchiserGroup.setConsumeIntegral(null != companyFranchiserGroup.getConsumeIntegral() ?
                    companyFranchiserGroup.getConsumeIntegral().subtract(totalFee1) : totalFee1); // 消费的度币
            return null != this.update(companyFranchiserGroup);
        }
        return false;
    }

    /**
     * 企业经销商账号组信息表的消费度币和使用度币(添加公共度币，消费度币不作处理)
     *
     * @param userId 用户id
     * @param totalFee 总费用
     * @return
     */
    @Override
    public boolean updateRechargeIntegral(Integer userId, Integer totalFee) {
        CompanyFranchiserGroup companyFranchiserGroup = this.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            companyFranchiserGroup.setGmtModified(new Date());
            BigDecimal totalFee1 = new BigDecimal(String.valueOf(totalFee));
            companyFranchiserGroup.setCommonalityIntegral(null != companyFranchiserGroup.getCommonalityIntegral() ?
                    companyFranchiserGroup.getCommonalityIntegral().add(totalFee1) : new BigDecimal("0")); // 剩下的公共度币
            return null != this.update(companyFranchiserGroup);
        }
        return false;
    }

    /**
     * 添加消费度币，公共度币不作处理
     *
     * @param userId   用户id
     * @param totalFee 总费用
     * @return
     */
    @Override
    public boolean updateConsumeIntegral(Integer userId, Integer totalFee) {
        CompanyFranchiserGroup companyFranchiserGroup = this.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            companyFranchiserGroup.setGmtModified(new Date());
            BigDecimal totalFee1 = new BigDecimal(String.valueOf(totalFee));
            companyFranchiserGroup.setConsumeIntegral(null != companyFranchiserGroup.getConsumeIntegral() ?
                    companyFranchiserGroup.getConsumeIntegral().add(totalFee1) : totalFee1); // 消费的度币
            return null != this.update(companyFranchiserGroup);
        }
        return false;
    }
}
