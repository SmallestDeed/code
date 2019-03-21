package com.sandu.pay.user.service.impl;

import com.sandu.common.constant.UserConstants;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.pay.user.dao.UserJurisdictionMapper;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserJurisdiction;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserJurisdictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户权限详情表对应的serviceImpl
 *
 * @Author yzw
 * @Date 2018/3/20 11:23
 */
@Service("userJurisdictionService")
@Transactional
public class UserJurisdictionServiceImpl implements UserJurisdictionService {
    private static final Logger logger = LoggerFactory.getLogger(UserJurisdictionServiceImpl.class);

    @Autowired
    private UserJurisdictionMapper userJurisdictionMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 新增
     *
     * @param record
     * @return
     */
    public UserJurisdiction add(UserJurisdiction record) {
        if (this.userJurisdictionMapper.insertSelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public boolean delete(Integer id) {
        return this.userJurisdictionMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    public UserJurisdiction update(UserJurisdiction record) {
        if (this.userJurisdictionMapper.updateByPrimaryKeySelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 获取
     *
     * @param id
     * @return
     */
    public UserJurisdiction get(Integer id) {
        return this.userJurisdictionMapper.selectByPrimaryKey(id);
    }

    /**
     * 用户开通移动端（2b）
     *
     * @param userId     用户id
     * @param platformId 平台id
     */
    @Override
    public void addMobileLogin(Integer userId, Integer platformId) {
        SysUser sysUser = sysUserService.get(userId);
        if (null != sysUser) {
            UserJurisdiction userJurisdiction = this.getByUserIdAndPlatformId(userId, platformId);
            if (null != userJurisdiction) {
                Date date = new Date();
                userJurisdiction.setJurisdictionStatus(UserConstants.JURISDICTION_STATUS_OPEN);
                userJurisdiction.setJurisdictionEffectiveTime(date);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.YEAR, 1);
                Date jurisdictionFailureTime = calendar.getTime();
                userJurisdiction.setJurisdictionFailureTime(jurisdictionFailureTime);
                userJurisdiction.setModifierUserId(userId);
                userJurisdiction.setGmtModified(new Date());
                userJurisdiction.setModifier(sysUser.getMobile());
                if (StringUtils.isEmpty(userJurisdiction.getSysCode())) {
                    userJurisdiction.setSysCode( Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
                int i = this.userJurisdictionMapper.updateByPrimaryKeySelective(userJurisdiction);
                if (i != 1) {
                    logger.info("用户id：userId:{}" + userId + "开通移动端失败");
                } else {
                    logger.info("用户id：userId:{}" + userId + "开通移动端成功");
                }
            } else {
                userJurisdiction = new UserJurisdiction();
                Date date = new Date();
                userJurisdiction.setUserId(userId);
                userJurisdiction.setPlatformId(platformId);
                userJurisdiction.setJurisdictionStatus(UserConstants.JURISDICTION_STATUS_OPEN);
                userJurisdiction.setJurisdictionEffectiveTime(date);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.YEAR, 1);
                Date jurisdictionFailureTime = calendar.getTime();
                userJurisdiction.setJurisdictionFailureTime(jurisdictionFailureTime);
                userJurisdiction.setModifierUserId(userId);
                userJurisdiction.setSysCode( Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                userJurisdiction.setCreator(sysUser.getMobile());
                userJurisdiction.setGmtCreate(new Date());
                userJurisdiction.setGmtModified(new Date());
                userJurisdiction.setModifier(sysUser.getMobile());
                userJurisdiction.setIsDeleted(0);
                int i = this.userJurisdictionMapper.insertSelective(userJurisdiction);
                if (i != 1) {
                    logger.info("用户id：userId:{}" + userId + "开通移动端失败");
                } else {
                    logger.info("用户id：userId:{}" + userId + "开通移动端成功");
                }
            }
        }
    }

    /**
     * 获取开通移动端信息
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public UserJurisdiction getByUserIdAndPlatformId(Integer userId, Integer platformId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("platformId", platformId);
        return this.userJurisdictionMapper.getByUserIdAndPlatformId(map);
    }

    /**
     * 获取开通移动端信息
     *
     * @param userId     用户id
     * @param platformId 用户id
     * @return 如果用户为开通或者已开通并且已过期，则返回null
     */
    @Override
    public UserJurisdiction getMobile2bUserJurisdiction(Integer userId, Integer platformId) {
        SysUser sysUser = sysUserService.get(userId);
        if (null != sysUser) {
            UserJurisdiction userJurisdiction = this.getByUserIdAndPlatformId(userId, platformId);
            if (null != userJurisdiction) {
                if (UserConstants.JURISDICTION_STATUS_OPEN == userJurisdiction.getJurisdictionStatus()) {
//                    if (null != userJurisdiction.getJurisdictionFailureTime() && new Date().before(userJurisdiction.getJurisdictionFailureTime())) {
//                        // 判断当前时间是否在权限到期时间之前
//                            return userJurisdiction;
//                    }
                    return userJurisdiction;
                }
            }
        }
        return null;
    }
}
